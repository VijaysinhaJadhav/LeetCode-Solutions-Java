
## Solution.java

```java
/**
 * 695. Max Area of Island
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a binary matrix grid, find the maximum area of an island.
 * An island is a group of 1's connected 4-directionally (horizontal or vertical).
 * 
 * Key Insights:
 * 1. Similar to "Number of Islands" but need to track area instead of count
 * 2. Use DFS/BFS to explore connected components and calculate area
 * 3. Track maximum area encountered
 * 4. Mark visited cells to avoid double counting
 * 
 * Approach (DFS Recursive):
 * 1. Iterate through each cell in grid
 * 2. When finding unvisited '1', perform DFS to calculate area
 * 3. Update global maximum area
 * 4. Return maximum area
 * 
 * Time Complexity: O(m × n)
 * Space Complexity: O(m × n) in worst case
 * 
 * Tags: Array, DFS, BFS, Union Find, Matrix
 */

import java.util.*;

class Solution {
    // Directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    /**
     * Approach 1: DFS Recursive - RECOMMENDED
     * O(m × n) time, O(m × n) space in worst case
     */
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int maxArea = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    int area = dfs(grid, i, j);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        
        return maxArea;
    }
    
    private int dfs(int[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Check bounds and if current cell is land
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != 1) {
            return 0;
        }
        
        // Mark current cell as visited by changing to 0
        grid[i][j] = 0;
        
        // Start with current cell
        int area = 1;
        
        // Explore all four directions and accumulate area
        for (int[] dir : DIRECTIONS) {
            area += dfs(grid, i + dir[0], j + dir[1]);
        }
        
        return area;
    }
    
    /**
     * Approach 2: BFS Iterative
     * O(m × n) time, O(min(m, n)) space for queue
     */
    public int maxAreaOfIslandBFS(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int maxArea = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    int area = bfs(grid, i, j);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        
        return maxArea;
    }
    
    private int bfs(int[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;
        int area = 0;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        grid[i][j] = 0; // Mark as visited
        area++;
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            
            // Check all four neighbors
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols 
                    && grid[newRow][newCol] == 1) {
                    queue.offer(new int[]{newRow, newCol});
                    grid[newRow][newCol] = 0; // Mark as visited
                    area++;
                }
            }
        }
        
        return area;
    }
    
    /**
     * Approach 3: DFS with Visited Array (Non-destructive)
     * O(m × n) time, O(m × n) space
     */
    public int maxAreaOfIslandNonDestructive(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int maxArea = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    int area = dfsWithVisited(grid, i, j, visited);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        
        return maxArea;
    }
    
    private int dfsWithVisited(int[][] grid, int i, int j, boolean[][] visited) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != 1 || visited[i][j]) {
            return 0;
        }
        
        visited[i][j] = true;
        int area = 1;
        
        for (int[] dir : DIRECTIONS) {
            area += dfsWithVisited(grid, i + dir[0], j + dir[1], visited);
        }
        
        return area;
    }
    
    /**
     * Approach 4: Union Find
     * O(m × n) time, O(m × n) space
     */
    public int maxAreaOfIslandUnionFind(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        UnionFind uf = new UnionFind(grid);
        
        // Connect adjacent land cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    // Check right neighbor
                    if (j + 1 < cols && grid[i][j + 1] == 1) {
                        uf.union(i * cols + j, i * cols + (j + 1));
                    }
                    // Check bottom neighbor
                    if (i + 1 < rows && grid[i + 1][j] == 1) {
                        uf.union(i * cols + j, (i + 1) * cols + j);
                    }
                }
            }
        }
        
        return uf.getMaxArea();
    }
    
    /**
     * Union Find data structure with area tracking
     */
    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int[] area;
        private int maxArea;
        
        public UnionFind(int[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;
            parent = new int[rows * cols];
            rank = new int[rows * cols];
            area = new int[rows * cols];
            maxArea = 0;
            
            // Initialize Union Find
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == 1) {
                        int id = i * cols + j;
                        parent[id] = id;
                        area[id] = 1;
                        maxArea = Math.max(maxArea, 1);
                    }
                }
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX != rootY) {
                // Union by rank
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                    area[rootX] += area[rootY];
                    maxArea = Math.max(maxArea, area[rootX]);
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                    area[rootY] += area[rootX];
                    maxArea = Math.max(maxArea, area[rootY]);
                } else {
                    parent[rootY] = rootX;
                    area[rootX] += area[rootY];
                    maxArea = Math.max(maxArea, area[rootX]);
                    rank[rootX]++;
                }
            }
        }
        
        public int getMaxArea() {
            return maxArea;
        }
    }
    
    /**
     * Approach 5: DFS Iterative with Stack
     * O(m × n) time, O(m × n) space
     */
    public int maxAreaOfIslandDFSIterative(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int maxArea = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    int area = dfsIterative(grid, i, j);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        
        return maxArea;
    }
    
    private int dfsIterative(int[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;
        int area = 0;
        
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{i, j});
        grid[i][j] = 0;
        area++;
        
        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            int row = cell[0];
            int col = cell[1];
            
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols 
                    && grid[newRow][newCol] == 1) {
                    stack.push(new int[]{newRow, newCol});
                    grid[newRow][newCol] = 0;
                    area++;
                }
            }
        }
        
        return area;
    }
    
    /**
     * Helper method to print grid for visualization
     */
    private void printGrid(int[][] grid) {
        System.out.println("Grid:");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Helper method to visualize island exploration
     */
    private void visualizeIsland(int[][] grid, int startI, int startJ) {
        System.out.printf("Exploring island starting at [%d,%d]:%n", startI, startJ);
        
        // Create a copy to avoid modifying original
        int[][] gridCopy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            gridCopy[i] = grid[i].clone();
        }
        
        int area = dfsVisualize(gridCopy, startI, startJ, 0);
        System.out.printf("Total area: %d%n", area);
    }
    
    private int dfsVisualize(int[][] grid, int i, int j, int depth) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != 1) {
            return 0;
        }
        
        // Print current cell with indentation based on depth
        String indent = "  ".repeat(depth);
        System.out.printf("%sVisiting [%d,%d]%n", indent, i, j);
        
        grid[i][j] = 0; // Mark as visited
        int area = 1;
        
        // Explore neighbors
        for (int[] dir : DIRECTIONS) {
            area += dfsVisualize(grid, i + dir[0], j + dir[1], depth + 1);
        }
        
        return area;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Max Area of Island:");
        System.out.println("===========================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        int[][] grid1 = {
            {0,0,1,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,1,1,0,1,0,0,0,0,0,0,0,0},
            {0,1,0,0,1,1,0,0,1,0,1,0,0},
            {0,1,0,0,1,1,0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,0,0,0,0,0,0,1,1,0,0,0,0}
        };
        int expected1 = 6;
        
        long startTime = System.nanoTime();
        int result1a = solution.maxAreaOfIsland(grid1);
        long time1a = System.nanoTime() - startTime;
        
        // Reset grid for next test
        int[][] grid1b = {
            {0,0,1,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,1,1,0,1,0,0,0,0,0,0,0,0},
            {0,1,0,0,1,1,0,0,1,0,1,0,0},
            {0,1,0,0,1,1,0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,0,0,0,0,0,0,1,1,0,0,0,0}
        };
        startTime = System.nanoTime();
        int result1b = solution.maxAreaOfIslandBFS(grid1b);
        long time1b = System.nanoTime() - startTime;
        
        int[][] grid1c = {
            {0,0,1,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,1,1,0,1,0,0,0,0,0,0,0,0},
            {0,1,0,0,1,1,0,0,1,0,1,0,0},
            {0,1,0,0,1,1,0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,0,0,0,0,0,0,1,1,0,0,0,0}
        };
        startTime = System.nanoTime();
        int result1c = solution.maxAreaOfIslandUnionFind(grid1c);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        
        System.out.println("DFS Recursive: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Union Find: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the largest island exploration
        System.out.println("\nExploring largest island:");
        int[][] grid1d = {
            {0,0,1,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,1,1,0,1,0,0,0,0,0,0,0,0},
            {0,1,0,0,1,1,0,0,1,0,1,0,0},
            {0,1,0,0,1,1,0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,0,0,0,0,0,0,1,1,0,0,0,0}
        };
        solution.visualizeIsland(grid1d, 3, 8);
        
        // Test case 2: Example 2 (all water)
        System.out.println("\nTest 2: All water");
        int[][] grid2 = {{0,0,0,0,0,0,0,0}};
        int result2 = solution.maxAreaOfIsland(grid2);
        int expected2 = 0;
        System.out.println("All water: " + result2 + " - " + 
                         (result2 == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single cell land
        System.out.println("\nTest 3: Single cell land");
        int[][] grid3 = {{1}};
        int result3 = solution.maxAreaOfIsland(grid3);
        System.out.println("Single cell: " + result3 + " - " + 
                         (result3 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 4: All land
        System.out.println("\nTest 4: All land");
        int[][] grid4 = {
            {1,1,1},
            {1,1,1},
            {1,1,1}
        };
        int result4 = solution.maxAreaOfIsland(grid4);
        System.out.println("All land: " + result4 + " - " + 
                         (result4 == 9 ? "PASSED" : "FAILED"));
        
        // Test case 5: Multiple islands
        System.out.println("\nTest 5: Multiple islands");
        int[][] grid5 = {
            {1,1,0,0,0},
            {1,1,0,0,0},
            {0,0,1,0,0},
            {0,0,0,1,1}
        };
        int result5 = solution.maxAreaOfIsland(grid5);
        System.out.println("Multiple islands: " + result5 + " - " + 
                         (result5 == 4 ? "PASSED" : "FAILED"));
        
        // Test case 6: Diagonal islands (not connected)
        System.out.println("\nTest 6: Diagonal islands");
        int[][] grid6 = {
            {1,0,1},
            {0,1,0},
            {1,0,1}
        };
        int result6 = solution.maxAreaOfIsland(grid6);
        System.out.println("Diagonal islands: " + result6 + " - " + 
                         (result6 == 1 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  DFS Recursive: " + time1a + " ns");
        System.out.println("  BFS: " + time1b + " ns");
        System.out.println("  Union Find: " + time1c + " ns");
        
        // Performance test with larger grid
        System.out.println("\nPerformance Test with 50x50 grid:");
        int[][] largeGrid = generateLargeGrid(50, 50);
        
        startTime = System.nanoTime();
        int largeResult = solution.maxAreaOfIsland(largeGrid);
        long largeTime = System.nanoTime() - startTime;
        System.out.println("50x50 grid result: " + largeResult + ", time: " + largeTime + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DFS ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        solution.explainDFSAlgorithm();
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON:");
        System.out.println("=".repeat(70));
        
        solution.compareApproaches();
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Generate a large grid for performance testing
     */
    private static int[][] generateLargeGrid(int rows, int cols) {
        int[][] grid = new int[rows][cols];
        Random random = new Random(42);
        
        // Create multiple islands with varying sizes
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Create islands with some probability
                if (random.nextDouble() < 0.3) {
                    grid[i][j] = 1;
                } else {
                    grid[i][j] = 0;
                }
            }
        }
        
        return grid;
    }
    
    /**
     * Detailed explanation of DFS algorithm
     */
    private void explainDFSAlgorithm() {
        System.out.println("\nDFS Approach for Max Area of Island:");
        System.out.println("1. Iterate through each cell in the grid");
        System.out.println("2. When we find an unvisited '1' (land):");
        System.out.println("   - Perform DFS to calculate the area of this island");
        System.out.println("   - Update global maximum area if current area is larger");
        
        System.out.println("\nDFS Area Calculation:");
        System.out.println("1. Mark current cell as visited (change 1 to 0)");
        System.out.println("2. Initialize area = 1 (current cell)");
        System.out.println("3. Recursively visit all four neighbors:");
        System.out.println("   - Add their areas to current area");
        System.out.println("4. Return total area");
        
        System.out.println("\nExample Walkthrough:");
        System.out.println("Grid:");
        System.out.println("1 1 0 0");
        System.out.println("1 1 0 0"); 
        System.out.println("0 0 1 1");
        System.out.println("0 0 1 1");
        
        System.out.println("\nStep 1: Find [0,0] = 1 -> start DFS");
        System.out.println("  DFS from [0,0]:");
        System.out.println("    Mark [0,0], area=1");
        System.out.println("    Visit [0,1] = 1 -> area=2");
        System.out.println("    Visit [1,0] = 1 -> area=3");
        System.out.println("    Visit [1,1] = 1 -> area=4");
        System.out.println("  Max area = 4");
        
        System.out.println("Step 2: Find [2,2] = 1 -> start DFS");
        System.out.println("  DFS from [2,2]:");
        System.out.println("    Mark [2,2], area=1");
        System.out.println("    Visit [2,3] = 1 -> area=2");
        System.out.println("    Visit [3,2] = 1 -> area=3");
        System.out.println("    Visit [3,3] = 1 -> area=4");
        System.out.println("  Max area remains 4");
        
        System.out.println("\nTime Complexity: O(m × n)");
        System.out.println("- Each cell visited once: O(m × n)");
        
        System.out.println("\nSpace Complexity: O(m × n) in worst case");
        System.out.println("- Recursion stack depth in worst case: O(m × n)");
    }
    
    /**
     * Compare different approaches
     */
    private void compareApproaches() {
        System.out.println("\n1. DFS Recursive (RECOMMENDED):");
        System.out.println("   Time: O(m × n)");
        System.out.println("   Space: O(m × n) worst case (recursion stack)");
        System.out.println("   Pros: Simple, intuitive, easy area tracking");
        System.out.println("   Cons: Stack overflow risk for very large grids");
        System.out.println("   Best for: Interviews, most practical cases");
        
        System.out.println("\n2. BFS Iterative:");
        System.out.println("   Time: O(m × n)");
        System.out.println("   Space: O(min(m, n)) for queue");
        System.out.println("   Pros: No recursion stack overflow");
        System.out.println("   Cons: Slightly more complex than DFS");
        System.out.println("   Best for: Very large grids, avoiding recursion");
        
        System.out.println("\n3. Union Find:");
        System.out.println("   Time: O(m × n) with path compression");
        System.out.println("   Space: O(m × n)");
        System.out.println("   Pros: Can handle dynamic connectivity");
        System.out.println("   Cons: More complex implementation");
        System.out.println("   Best for: When you need to handle updates");
        
        System.out.println("\n4. DFS Iterative:");
        System.out.println("   Time: O(m × n)");
        System.out.println("   Space: O(m × n) for stack");
        System.out.println("   Pros: No recursion, explicit stack control");
        System.out.println("   Cons: More code than recursive DFS");
        System.out.println("   Best for: When avoiding recursion is important");
        
        System.out.println("\n5. Non-destructive DFS:");
        System.out.println("   Time: O(m × n)");
        System.out.println("   Space: O(m × n) for visited array");
        System.out.println("   Pros: Preserves original grid");
        System.out.println("   Cons: Extra space for visited array");
        System.out.println("   Best for: When original grid must be preserved");
        
        System.out.println("\nRecommendation:");
        System.out.println("- For interviews: DFS Recursive (most expected)");
        System.out.println("- For production: DFS Recursive or BFS based on grid size");
        System.out.println("- For preserving grid: Non-destructive DFS");
    }
}

/**
 * Additional utility class for grid operations
 */
class GridUtils {
    /**
     * Create a deep copy of a grid
     */
    public static int[][] copyGrid(int[][] grid) {
        if (grid == null) return null;
        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }
    
    /**
     * Count the number of land cells in a grid
     */
    public static int countLandCells(int[][] grid) {
        int count = 0;
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 1) count++;
            }
        }
        return count;
    }
    
    /**
     * Check if a grid position is valid
     */
    public static boolean isValid(int i, int j, int rows, int cols) {
        return i >= 0 && i < rows && j >= 0 && j < cols;
    }
    
    /**
     * Calculate total area of all islands in grid
     */
    public static int calculateTotalArea(int[][] grid) {
        int total = 0;
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 1) total++;
            }
        }
        return total;
    }
}
