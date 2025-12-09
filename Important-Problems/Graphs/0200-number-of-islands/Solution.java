
## Solution.java

```java
/**
 * 200. Number of Islands
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a 2D binary grid of '1's (land) and '0's (water), count the number of islands.
 * An island is surrounded by water and formed by connecting adjacent lands horizontally or vertically.
 * 
 * Key Insights:
 * 1. Need to find connected components of '1's
 * 2. Can use DFS, BFS, or Union Find
 * 3. Mark visited cells to avoid double counting
 * 4. Only check horizontal and vertical neighbors (4 directions)
 * 
 * Approach (DFS):
 * 1. Iterate through each cell in grid
 * 2. When finding unvisited '1', increment count and perform DFS
 * 3. In DFS, mark current cell as visited and recursively visit neighbors
 * 4. Return total count
 * 
 * Time Complexity: O(m × n)
 * Space Complexity: O(m × n) in worst case for recursion stack
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
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }
        
        return count;
    }
    
    private void dfs(char[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Check bounds and if current cell is land
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != '1') {
            return;
        }
        
        // Mark current cell as visited by changing to '0'
        grid[i][j] = '0';
        
        // Explore all four directions
        for (int[] dir : DIRECTIONS) {
            dfs(grid, i + dir[0], j + dir[1]);
        }
    }
    
    /**
     * Approach 2: BFS Iterative
     * O(m × n) time, O(min(m, n)) space for queue
     */
    public int numIslandsBFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    bfs(grid, i, j);
                }
            }
        }
        
        return count;
    }
    
    private void bfs(char[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        grid[i][j] = '0'; // Mark as visited
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            
            // Check all four neighbors
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols 
                    && grid[newRow][newCol] == '1') {
                    queue.offer(new int[]{newRow, newCol});
                    grid[newRow][newCol] = '0'; // Mark as visited
                }
            }
        }
    }
    
    /**
     * Approach 3: Union Find (Disjoint Set Union)
     * O(m × n) time, O(m × n) space
     */
    public int numIslandsUnionFind(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        UnionFind uf = new UnionFind(grid);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    // Check right neighbor
                    if (j + 1 < cols && grid[i][j + 1] == '1') {
                        uf.union(i * cols + j, i * cols + (j + 1));
                    }
                    // Check bottom neighbor
                    if (i + 1 < rows && grid[i + 1][j] == '1') {
                        uf.union(i * cols + j, (i + 1) * cols + j);
                    }
                }
            }
        }
        
        return uf.getCount();
    }
    
    /**
     * Union Find data structure
     */
    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int count;
        
        public UnionFind(char[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;
            parent = new int[rows * cols];
            rank = new int[rows * cols];
            count = 0;
            
            // Initialize Union Find
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == '1') {
                        int id = i * cols + j;
                        parent[id] = id;
                        count++;
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
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                count--;
            }
        }
        
        public int getCount() {
            return count;
        }
    }
    
    /**
     * Approach 4: DFS with Visited Array (Non-destructive)
     * O(m × n) time, O(m × n) space
     */
    public int numIslandsNonDestructive(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    count++;
                    dfsWithVisited(grid, i, j, visited);
                }
            }
        }
        
        return count;
    }
    
    private void dfsWithVisited(char[][] grid, int i, int j, boolean[][] visited) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != '1' || visited[i][j]) {
            return;
        }
        
        visited[i][j] = true;
        
        for (int[] dir : DIRECTIONS) {
            dfsWithVisited(grid, i + dir[0], j + dir[1], visited);
        }
    }
    
    /**
     * Approach 5: DFS Iterative with Stack
     * O(m × n) time, O(m × n) space
     */
    public int numIslandsDFSIterative(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfsIterative(grid, i, j);
                }
            }
        }
        
        return count;
    }
    
    private void dfsIterative(char[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{i, j});
        grid[i][j] = '0';
        
        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            int row = cell[0];
            int col = cell[1];
            
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols 
                    && grid[newRow][newCol] == '1') {
                    stack.push(new int[]{newRow, newCol});
                    grid[newRow][newCol] = '0';
                }
            }
        }
    }
    
    /**
     * Helper method to print grid for visualization
     */
    private void printGrid(char[][] grid) {
        System.out.println("Grid:");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Helper method to visualize DFS traversal
     */
    private void visualizeDFS(char[][] grid, int startI, int startJ) {
        System.out.printf("Starting DFS from cell [%d,%d]:%n", startI, startJ);
        
        // Create a copy to avoid modifying original
        char[][] gridCopy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            gridCopy[i] = grid[i].clone();
        }
        
        dfsVisualize(gridCopy, startI, startJ, 0);
    }
    
    private void dfsVisualize(char[][] grid, int i, int j, int depth) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != '1') {
            return;
        }
        
        // Print current cell with indentation based on depth
        String indent = "  ".repeat(depth);
        System.out.printf("%sVisiting [%d,%d]%n", indent, i, j);
        
        grid[i][j] = 'V'; // Mark as visited
        
        // Explore neighbors
        for (int[] dir : DIRECTIONS) {
            dfsVisualize(grid, i + dir[0], j + dir[1], depth + 1);
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Number of Islands:");
        System.out.println("==========================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        char[][] grid1 = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        int expected1 = 1;
        
        long startTime = System.nanoTime();
        int result1a = solution.numIslands(grid1);
        long time1a = System.nanoTime() - startTime;
        
        // Reset grid for next test
        char[][] grid1b = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        startTime = System.nanoTime();
        int result1b = solution.numIslandsBFS(grid1b);
        long time1b = System.nanoTime() - startTime;
        
        char[][] grid1c = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        startTime = System.nanoTime();
        int result1c = solution.numIslandsUnionFind(grid1c);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        
        System.out.println("DFS Recursive: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Union Find: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the grid and DFS
        System.out.println("\nOriginal Grid:");
        solution.printGrid(grid1);
        solution.visualizeDFS(grid1, 0, 0);
        
        // Test case 2: Example 2
        System.out.println("\nTest 2: Example 2");
        char[][] grid2 = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        int result2 = solution.numIslands(grid2);
        int expected2 = 3;
        System.out.println("Example 2: " + result2 + " - " + 
                         (result2 == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Empty grid
        System.out.println("\nTest 3: Empty grid");
        char[][] grid3 = {};
        int result3 = solution.numIslands(grid3);
        System.out.println("Empty grid: " + result3 + " - " + 
                         (result3 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single cell land
        System.out.println("\nTest 4: Single cell land");
        char[][] grid4 = {{'1'}};
        int result4 = solution.numIslands(grid4);
        System.out.println("Single cell: " + result4 + " - " + 
                         (result4 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single cell water
        System.out.println("\nTest 5: Single cell water");
        char[][] grid5 = {{'0'}};
        int result5 = solution.numIslands(grid5);
        System.out.println("Single water: " + result5 + " - " + 
                         (result5 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 6: All water
        System.out.println("\nTest 6: All water");
        char[][] grid6 = {
            {'0','0','0','0'},
            {'0','0','0','0'},
            {'0','0','0','0'}
        };
        int result6 = solution.numIslands(grid6);
        System.out.println("All water: " + result6 + " - " + 
                         (result6 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 7: All land
        System.out.println("\nTest 7: All land");
        char[][] grid7 = {
            {'1','1','1'},
            {'1','1','1'},
            {'1','1','1'}
        };
        int result7 = solution.numIslands(grid7);
        System.out.println("All land: " + result7 + " - " + 
                         (result7 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 8: Checkerboard pattern
        System.out.println("\nTest 8: Checkerboard pattern");
        char[][] grid8 = {
            {'1','0','1','0'},
            {'0','1','0','1'},
            {'1','0','1','0'}
        };
        int result8 = solution.numIslands(grid8);
        System.out.println("Checkerboard: " + result8 + " - " + 
                         (result8 == 6 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  DFS Recursive: " + time1a + " ns");
        System.out.println("  BFS: " + time1b + " ns");
        System.out.println("  Union Find: " + time1c + " ns");
        
        // Performance test with larger grid
        System.out.println("\nPerformance Test with 100x100 grid:");
        char[][] largeGrid = generateLargeGrid(100, 100);
        
        startTime = System.nanoTime();
        int largeResult = solution.numIslands(largeGrid);
        long largeTime = System.nanoTime() - startTime;
        System.out.println("100x100 grid result: " + largeResult + ", time: " + largeTime + " ns");
        
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
    private static char[][] generateLargeGrid(int rows, int cols) {
        char[][] grid = new char[rows][cols];
        Random random = new Random(42);
        
        // Create multiple islands
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Create islands with some probability
                if (random.nextDouble() < 0.3) {
                    grid[i][j] = '1';
                } else {
                    grid[i][j] = '0';
                }
            }
        }
        
        return grid;
    }
    
    /**
     * Detailed explanation of DFS algorithm
     */
    private void explainDFSAlgorithm() {
        System.out.println("\nDFS Approach:");
        System.out.println("1. Iterate through each cell in the grid");
        System.out.println("2. When we find an unvisited '1' (land):");
        System.out.println("   - Increment island count");
        System.out.println("   - Perform DFS to mark all connected land as visited");
        
        System.out.println("\nDFS Process:");
        System.out.println("1. Mark current cell as visited (change '1' to '0')");
        System.out.println("2. Recursively visit all four neighbors:");
        System.out.println("   - Up: [i-1, j]");
        System.out.println("   - Right: [i, j+1]");
        System.out.println("   - Down: [i+1, j]");
        System.out.println("   - Left: [i, j-1]");
        System.out.println("3. Base cases:");
        System.out.println("   - Out of bounds");
        System.out.println("   - Cell is water ('0')");
        System.out.println("   - Cell already visited");
        
        System.out.println("\nExample Walkthrough:");
        System.out.println("Grid:");
        System.out.println("1 1 0 0 0");
        System.out.println("1 1 0 0 0");
        System.out.println("0 0 1 0 0");
        System.out.println("0 0 0 1 1");
        
        System.out.println("\nStep 1: Find [0,0] = '1' -> new island (count=1)");
        System.out.println("  DFS from [0,0]:");
        System.out.println("    Mark [0,0] visited");
        System.out.println("    Visit [0,1] = '1' -> mark visited");
        System.out.println("    Visit [1,0] = '1' -> mark visited");
        System.out.println("    Visit [1,1] = '1' -> mark visited");
        
        System.out.println("Step 2: Find [2,2] = '1' -> new island (count=2)");
        System.out.println("  DFS from [2,2]: mark visited");
        
        System.out.println("Step 3: Find [3,3] = '1' -> new island (count=3)");
        System.out.println("  DFS from [3,3]:");
        System.out.println("    Mark [3,3] visited");
        System.out.println("    Visit [3,4] = '1' -> mark visited");
        
        System.out.println("Total islands: 3");
        
        System.out.println("\nTime Complexity: O(m × n)");
        System.out.println("- Each cell visited once: O(m × n)");
        
        System.out.println("\nSpace Complexity: O(m × n) in worst case");
        System.out.println("- Recursion stack depth in worst case: O(m × n)");
        System.out.println("- For BFS: O(min(m, n)) for queue");
    }
    
    /**
     * Compare different approaches
     */
    private void compareApproaches() {
        System.out.println("\n1. DFS Recursive (RECOMMENDED):");
        System.out.println("   Time: O(m × n)");
        System.out.println("   Space: O(m × n) worst case (recursion stack)");
        System.out.println("   Pros: Simple, intuitive, easy to implement");
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
        
        System.out.println("\nRecommendation:");
        System.out.println("- For interviews: DFS Recursive (most expected)");
        System.out.println("- For production: DFS Recursive or BFS based on grid size");
        System.out.println("- For learning: Try all approaches to understand trade-offs");
    }
}

/**
 * Additional utility class for grid operations
 */
class GridUtils {
    /**
     * Create a deep copy of a grid
     */
    public static char[][] copyGrid(char[][] grid) {
        if (grid == null) return null;
        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }
    
    /**
     * Count the number of land cells in a grid
     */
    public static int countLandCells(char[][] grid) {
        int count = 0;
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == '1') count++;
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
}
