
## Solution.java

```java
/**
 * 463. Island Perimeter
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * You are given a row x col grid representing a map where grid[i][j] = 1 is land 
 * and grid[i][j] = 0 is water. Determine the perimeter of the island.
 * 
 * Key Insights:
 * 1. Each land cell contributes 4 to the perimeter initially
 * 2. Each adjacent land cell reduces perimeter by 1 (shared edge)
 * 3. Only need to check four directions: up, down, left, right
 * 4. Can count contributions or traverse the island
 * 
 * Approach (Counting Neighbors):
 * 1. Iterate through each cell in grid
 * 2. For each land cell, add 4 to perimeter
 * 3. For each adjacent land cell, subtract 1 from perimeter
 * 4. Return total perimeter
 * 
 * Time Complexity: O(m × n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Matrix, Counting, DFS
 */

import java.util.*;

class Solution {
    // Directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    /**
     * Approach 1: Counting Neighbors - RECOMMENDED
     * O(m × n) time, O(1) space
     */
    public int islandPerimeter(int[][] grid) {
        int perimeter = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    // Each land cell contributes 4 edges initially
                    perimeter += 4;
                    
                    // Check all four neighbors
                    // For each adjacent land cell, subtract 1 (shared edge)
                    for (int[] dir : DIRECTIONS) {
                        int ni = i + dir[0];
                        int nj = j + dir[1];
                        
                        // If neighbor is within bounds and is land, subtract 1
                        if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && grid[ni][nj] == 1) {
                            perimeter--;
                        }
                    }
                }
            }
        }
        
        return perimeter;
    }
    
    /**
     * Approach 2: DFS Traversal
     * O(m × n) time, O(m × n) space for recursion stack
     */
    public int islandPerimeterDFS(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        
        // Find the first land cell to start DFS
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    return dfs(grid, visited, i, j);
                }
            }
        }
        
        return 0; // No land found
    }
    
    private int dfs(int[][] grid, boolean[][] visited, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Base cases: out of bounds or water
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] == 0) {
            return 1; // This is a perimeter edge
        }
        
        // If already visited, return 0 (no additional perimeter)
        if (visited[i][j]) {
            return 0;
        }
        
        visited[i][j] = true;
        int perimeter = 0;
        
        // Explore all four directions
        for (int[] dir : DIRECTIONS) {
            perimeter += dfs(grid, visited, i + dir[0], j + dir[1]);
        }
        
        return perimeter;
    }
    
    /**
     * Approach 3: BFS Traversal
     * O(m × n) time, O(m × n) space for queue
     */
    public int islandPerimeterBFS(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        
        // Find first land cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    return bfs(grid, visited, i, j);
                }
            }
        }
        
        return 0;
    }
    
    private int bfs(int[][] grid, boolean[][] visited, int startI, int startJ) {
        int perimeter = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startI, startJ});
        visited[startI][startJ] = true;
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int i = cell[0], j = cell[1];
            
            // Check all four directions from current cell
            for (int[] dir : DIRECTIONS) {
                int ni = i + dir[0];
                int nj = j + dir[1];
                
                // If out of bounds or water, this is a perimeter edge
                if (ni < 0 || ni >= rows || nj < 0 || nj >= cols || grid[ni][nj] == 0) {
                    perimeter++;
                } else if (!visited[ni][nj]) {
                    // If it's unvisited land, add to queue
                    visited[ni][nj] = true;
                    queue.offer(new int[]{ni, nj});
                }
            }
        }
        
        return perimeter;
    }
    
    /**
     * Approach 4: Mathematical Counting (Two-pass)
     * O(m × n) time, O(1) space
     */
    public int islandPerimeterMath(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int perimeter = 0;
        
        // Count horizontal edges
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    // Left edge: if first column or left neighbor is water
                    if (j == 0 || grid[i][j - 1] == 0) {
                        perimeter++;
                    }
                    // Right edge: if last column or right neighbor is water
                    if (j == cols - 1 || grid[i][j + 1] == 0) {
                        perimeter++;
                    }
                }
            }
        }
        
        // Count vertical edges
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (grid[i][j] == 1) {
                    // Top edge: if first row or top neighbor is water
                    if (i == 0 || grid[i - 1][j] == 0) {
                        perimeter++;
                    }
                    // Bottom edge: if last row or bottom neighbor is water
                    if (i == rows - 1 || grid[i + 1][j] == 0) {
                        perimeter++;
                    }
                }
            }
        }
        
        return perimeter;
    }
    
    /**
     * Approach 5: Optimized Counting (One-pass with neighbor check)
     * O(m × n) time, O(1) space
     */
    public int islandPerimeterOptimized(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int perimeter = 0;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    // Check left neighbor
                    if (j == 0 || grid[i][j - 1] == 0) perimeter++;
                    // Check right neighbor  
                    if (j == cols - 1 || grid[i][j + 1] == 0) perimeter++;
                    // Check top neighbor
                    if (i == 0 || grid[i - 1][j] == 0) perimeter++;
                    // Check bottom neighbor
                    if (i == rows - 1 || grid[i + 1][j] == 0) perimeter++;
                }
            }
        }
        
        return perimeter;
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
     * Helper method to visualize perimeter calculation
     */
    private void visualizePerimeter(int[][] grid) {
        System.out.println("Perimeter Calculation Visualization:");
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    int cellPerimeter = 4;
                    StringBuilder neighbors = new StringBuilder();
                    
                    for (int[] dir : DIRECTIONS) {
                        int ni = i + dir[0];
                        int nj = j + dir[1];
                        if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && grid[ni][nj] == 1) {
                            cellPerimeter--;
                            neighbors.append(String.format(" [%d,%d]", ni, nj));
                        }
                    }
                    
                    System.out.printf("Cell [%d,%d]: 4 - %d neighbors%s = %d%n", 
                                    i, j, 4 - cellPerimeter, neighbors.toString(), cellPerimeter);
                }
            }
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Island Perimeter:");
        System.out.println("=========================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        int[][] grid1 = {
            {0, 1, 0, 0},
            {1, 1, 1, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0}
        };
        int expected1 = 16;
        
        long startTime = System.nanoTime();
        int result1a = solution.islandPerimeter(grid1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.islandPerimeterDFS(grid1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.islandPerimeterBFS(grid1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.islandPerimeterMath(grid1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.islandPerimeterOptimized(grid1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("Neighbor Counting: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DFS: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Math Counting: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the calculation
        solution.printGrid(grid1);
        solution.visualizePerimeter(grid1);
        
        // Test case 2: Single cell
        System.out.println("\nTest 2: Single cell");
        int[][] grid2 = {{1}};
        int result2 = solution.islandPerimeter(grid2);
        System.out.println("Single cell: " + result2 + " - " + 
                         (result2 == 4 ? "PASSED" : "FAILED"));
        
        // Test case 3: 1x2 grid
        System.out.println("\nTest 3: 1x2 grid");
        int[][] grid3 = {{1, 0}};
        int result3 = solution.islandPerimeter(grid3);
        System.out.println("1x2 grid: " + result3 + " - " + 
                         (result3 == 4 ? "PASSED" : "FAILED"));
        
        // Test case 4: 2x1 grid
        System.out.println("\nTest 4: 2x1 grid");
        int[][] grid4 = {{1}, {0}};
        int result4 = solution.islandPerimeter(grid4);
        System.out.println("2x1 grid: " + result4 + " - " + 
                         (result4 == 4 ? "PASSED" : "FAILED"));
        
        // Test case 5: 2x2 square
        System.out.println("\nTest 5: 2x2 square");
        int[][] grid5 = {
            {1, 1},
            {1, 1}
        };
        int result5 = solution.islandPerimeter(grid5);
        System.out.println("2x2 square: " + result5 + " - " + 
                         (result5 == 8 ? "PASSED" : "FAILED"));
        
        // Test case 6: Straight line
        System.out.println("\nTest 6: Straight line");
        int[][] grid6 = {
            {1, 1, 1}
        };
        int result6 = solution.islandPerimeter(grid6);
        System.out.println("Straight line: " + result6 + " - " + 
                         (result6 == 8 ? "PASSED" : "FAILED"));
        
        // Test case 7: L-shape
        System.out.println("\nTest 7: L-shape");
        int[][] grid7 = {
            {1, 0},
            {1, 1}
        };
        int result7 = solution.islandPerimeter(grid7);
        System.out.println("L-shape: " + result7 + " - " + 
                         (result7 == 8 ? "PASSED" : "FAILED"));
        
        // Test case 8: Complex shape
        System.out.println("\nTest 8: Complex shape");
        int[][] grid8 = {
            {0, 1, 1, 0},
            {1, 1, 0, 0},
            {0, 1, 0, 0}
        };
        int result8 = solution.islandPerimeter(grid8);
        System.out.println("Complex shape: " + result8 + " - " + 
                         (result8 == 12 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  Neighbor Counting: " + time1a + " ns");
        System.out.println("  DFS: " + time1b + " ns");
        System.out.println("  BFS: " + time1c + " ns");
        System.out.println("  Math Counting: " + time1d + " ns");
        System.out.println("  Optimized: " + time1e + " ns");
        
        // Performance test with larger grid
        System.out.println("\nPerformance Test with 100x100 grid:");
        int[][] largeGrid = generateLargeGrid(100, 100);
        
        startTime = System.nanoTime();
        int largeResult = solution.islandPerimeter(largeGrid);
        long largeTime = System.nanoTime() - startTime;
        System.out.println("100x100 grid result: " + largeResult + ", time: " + largeTime + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("NEIGHBOR COUNTING ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        solution.explainNeighborCounting();
        
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
        
        // Create a single connected island
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Create a roughly circular island in the center
                double centerI = rows / 2.0;
                double centerJ = cols / 2.0;
                double distance = Math.sqrt(Math.pow(i - centerI, 2) + Math.pow(j - centerJ, 2));
                
                if (distance < Math.min(rows, cols) / 3.0) {
                    grid[i][j] = 1;
                } else {
                    grid[i][j] = 0;
                }
            }
        }
        
        return grid;
    }
    
    /**
     * Detailed explanation of neighbor counting approach
     */
    private void explainNeighborCounting() {
        System.out.println("\nNeighbor Counting Approach:");
        System.out.println("1. Each land cell starts with 4 perimeter units (4 sides)");
        System.out.println("2. For each adjacent land cell (up, down, left, right):");
        System.out.println("   - Subtract 1 from perimeter (shared edge)");
        System.out.println("3. This automatically handles boundaries (water around edges)");
        
        System.out.println("\nExample Calculation:");
        System.out.println("For a single cell: 4 - 0 = 4");
        System.out.println("For two adjacent cells:");
        System.out.println("  Cell 1: 4 - 1 (right neighbor) = 3");
        System.out.println("  Cell 2: 4 - 1 (left neighbor) = 3");
        System.out.println("  Total: 3 + 3 = 6");
        
        System.out.println("\nWhy This Works:");
        System.out.println("- Each internal edge is counted twice (once from each side)");
        System.out.println("- By subtracting 1 for each neighbor, we remove both counts");
        System.out.println("- External edges (facing water) are not subtracted");
        System.out.println("- The math works out perfectly for any shape");
        
        System.out.println("\nStep-by-step for Example 1:");
        int[][] example = {
            {0, 1, 0, 0},
            {1, 1, 1, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0}
        };
        
        System.out.println("Let's calculate cell by cell:");
        int total = 0;
        for (int i = 0; i < example.length; i++) {
            for (int j = 0; j < example[i].length; j++) {
                if (example[i][j] == 1) {
                    int cellPerim = 4;
                    int neighbors = 0;
                    
                    // Check neighbors
                    if (i > 0 && example[i-1][j] == 1) { neighbors++; }
                    if (i < example.length-1 && example[i+1][j] == 1) { neighbors++; }
                    if (j > 0 && example[i][j-1] == 1) { neighbors++; }
                    if (j < example[i].length-1 && example[i][j+1] == 1) { neighbors++; }
                    
                    cellPerim -= neighbors;
                    total += cellPerim;
                    System.out.printf("  Cell [%d,%d]: 4 - %d = %d%n", i, j, neighbors, cellPerim);
                }
            }
        }
        System.out.println("Total perimeter: " + total);
    }
    
    /**
     * Compare different approaches
     */
    private void compareApproaches() {
        System.out.println("\n1. Neighbor Counting (RECOMMENDED):");
        System.out.println("   Time: O(m × n)");
        System.out.println("   Space: O(1)");
        System.out.println("   Pros: Simple, efficient, constant space");
        System.out.println("   Cons: None significant");
        System.out.println("   Best for: General case, interviews");
        
        System.out.println("\n2. DFS Traversal:");
        System.out.println("   Time: O(m × n)");
        System.out.println("   Space: O(m × n) for recursion stack");
        System.out.println("   Pros: Natural for connected components");
        System.out.println("   Cons: Stack overflow risk for large grids");
        System.out.println("   Best for: When you need to process connected components");
        
        System.out.println("\n3. BFS Traversal:");
        System.out.println("   Time: O(m × n)");
        System.out.println("   Space: O(m × n) for queue");
        System.out.println("   Pros: No recursion stack issues");
        System.out.println("   Cons: More complex than counting");
        System.out.println("   Best for: Very large grids (avoid recursion)");
        
        System.out.println("\n4. Mathematical Counting:");
        System.out.println("   Time: O(m × n)");
        System.out.println("   Space: O(1)");
        System.out.println("   Pros: Explicit edge counting");
        System.out.println("   Cons: Two passes through grid");
        System.out.println("   Best for: When you want explicit edge calculation");
        
        System.out.println("\n5. Optimized Counting:");
        System.out.println("   Time: O(m × n)");
        System.out.println("   Space: O(1)");
        System.out.println("   Pros: One-pass, explicit edge checks");
        System.out.println("   Cons: Slightly more complex than neighbor counting");
        System.out.println("   Best for: When you want fine control over edge detection");
        
        System.out.println("\nRecommendation:");
        System.out.println("- For interviews: Neighbor Counting (most expected)");
        System.out.println("- For production: Neighbor Counting (simplest and efficient)");
        System.out.println("- For learning: Try all approaches to understand different perspectives");
    }
}

/**
 * Additional utility class for grid operations
 */
class GridUtils {
    /**
     * Check if a grid position is valid
     */
    public static boolean isValid(int i, int j, int rows, int cols) {
        return i >= 0 && i < rows && j >= 0 && j < cols;
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
     * Create a deep copy of a grid
     */
    public static int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }
}
