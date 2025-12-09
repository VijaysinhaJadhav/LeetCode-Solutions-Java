
## Solution.java

```java
/**
 * 994. Rotting Oranges
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a grid of oranges (0=empty, 1=fresh, 2=rotten), return minimum minutes 
 * until no fresh oranges remain. Return -1 if impossible.
 * 
 * Key Insights:
 * 1. Multi-source BFS starting from all rotten oranges
 * 2. Track minutes using level-by-level BFS
 * 3. Count fresh oranges to check if all get rotten
 * 4. Fresh oranges adjacent to rotten become rotten each minute
 * 
 * Approach (Multi-source BFS):
 * 1. Count fresh oranges and add all rotten oranges to queue
 * 2. Perform level-by-level BFS
 * 3. Each level represents one minute
 * 4. Convert adjacent fresh oranges to rotten
 * 5. Return minutes if no fresh oranges left, else -1
 * 
 * Time Complexity: O(m × n)
 * Space Complexity: O(m × n)
 * 
 * Tags: BFS, Matrix, Simulation
 */

import java.util.*;

class Solution {
    // Directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    /**
     * Approach 1: Multi-source BFS - RECOMMENDED
     * O(m × n) time, O(m × n) space
     */
    public int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;
        
        // Count fresh oranges and add rotten oranges to queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    freshCount++;
                } else if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        
        // If no fresh oranges initially, return 0
        if (freshCount == 0) {
            return 0;
        }
        
        int minutes = 0;
        
        // Perform BFS level by level
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean converted = false;
            
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];
                
                // Check all four directions
                for (int[] dir : DIRECTIONS) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    
                    // If adjacent cell is fresh orange, convert to rotten
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                        && grid[newRow][newCol] == 1) {
                        grid[newRow][newCol] = 2;
                        freshCount--;
                        queue.offer(new int[]{newRow, newCol});
                        converted = true;
                    }
                }
            }
            
            // Only increment minutes if we converted any oranges
            if (converted) {
                minutes++;
            }
        }
        
        // If fresh oranges remain, return -1
        return freshCount == 0 ? minutes : -1;
    }
    
    /**
     * Approach 2: BFS with Minutes Tracking in Queue
     * O(m × n) time, O(m × n) space
     * Stores minutes directly in queue
     */
    public int orangesRottingWithMinutes(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>(); // [row, col, minutes]
        int freshCount = 0;
        int maxMinutes = 0;
        
        // Count fresh oranges and add rotten oranges to queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    freshCount++;
                } else if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j, 0});
                }
            }
        }
        
        if (freshCount == 0) {
            return 0;
        }
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            int minutes = cell[2];
            maxMinutes = Math.max(maxMinutes, minutes);
            
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                    && grid[newRow][newCol] == 1) {
                    grid[newRow][newCol] = 2;
                    freshCount--;
                    queue.offer(new int[]{newRow, newCol, minutes + 1});
                }
            }
        }
        
        return freshCount == 0 ? maxMinutes : -1;
    }
    
    /**
     * Approach 3: BFS with Separate Fresh Tracking
     * O(m × n) time, O(m × n) space
     * Uses visited set for fresh oranges
     */
    public int orangesRottingSeparateTracking(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        Set<String> fresh = new HashSet<>();
        
        // Identify all rotten and fresh oranges
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    fresh.add(i + "," + j);
                }
            }
        }
        
        if (fresh.isEmpty()) {
            return 0;
        }
        
        int minutes = 0;
        while (!queue.isEmpty() && !fresh.isEmpty()) {
            int size = queue.size();
            boolean converted = false;
            
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];
                
                for (int[] dir : DIRECTIONS) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    String key = newRow + "," + newCol;
                    
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                        && fresh.contains(key)) {
                        fresh.remove(key);
                        queue.offer(new int[]{newRow, newCol});
                        converted = true;
                    }
                }
            }
            
            if (converted) {
                minutes++;
            }
        }
        
        return fresh.isEmpty() ? minutes : -1;
    }
    
    /**
     * Approach 4: BFS with Debug Visualization
     * O(m × n) time, O(m × n) space
     * Includes step-by-step visualization
     */
    public int orangesRottingDebug(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            System.out.println("Empty grid, returning 0");
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;
        
        System.out.println("Initial grid:");
        printGrid(grid);
        
        // Count fresh oranges and add rotten to queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    freshCount++;
                } else if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        
        System.out.println("Initial fresh count: " + freshCount);
        System.out.println("Rotten oranges in queue: " + queue.size());
        
        if (freshCount == 0) {
            System.out.println("No fresh oranges initially, returning 0");
            return 0;
        }
        
        int minutes = 0;
        int step = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean converted = false;
            step++;
            
            System.out.printf("\nMinute %d: Processing %d rotten oranges%n", minutes, size);
            
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];
                
                System.out.printf("  Processing rotten orange at [%d,%d]%n", row, col);
                
                for (int[] dir : DIRECTIONS) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                        && grid[newRow][newCol] == 1) {
                        System.out.printf("    Converting fresh orange at [%d,%d] to rotten%n", 
                                        newRow, newCol);
                        grid[newRow][newCol] = 2;
                        freshCount--;
                        queue.offer(new int[]{newRow, newCol});
                        converted = true;
                    }
                }
            }
            
            if (converted) {
                minutes++;
                System.out.printf("Minute %d completed. Fresh oranges remaining: %d%n", 
                                minutes, freshCount);
                if (step <= 3) { // Limit output for large grids
                    System.out.println("Grid after minute " + minutes + ":");
                    printGrid(grid);
                }
            }
        }
        
        if (freshCount == 0) {
            System.out.println("\nAll oranges rotten! Total minutes: " + minutes);
            return minutes;
        } else {
            System.out.println("\n" + freshCount + " fresh oranges remain unreachable");
            return -1;
        }
    }
    
    /**
     * Approach 5: In-place BFS without Extra Space (Almost)
     * O(m × n) time, O(m × n) space for queue
     * Uses grid itself to track state
     */
    public int orangesRottingInPlace(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    freshCount++;
                } else if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        
        if (freshCount == 0) return 0;
        
        int minutes = 0;
        while (!queue.isEmpty() && freshCount > 0) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];
                
                for (int[] dir : DIRECTIONS) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                        && grid[newRow][newCol] == 1) {
                        grid[newRow][newCol] = 2;
                        freshCount--;
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
            minutes++;
        }
        
        return freshCount == 0 ? minutes : -1;
    }
    
    /**
     * Helper method to print grid for visualization
     */
    private void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Helper method to create test grid from example
     */
    public int[][] createTestGrid1() {
        return new int[][] {
            {2, 1, 1},
            {1, 1, 0},
            {0, 1, 1}
        };
    }
    
    /**
     * Helper method to create impossible test grid
     */
    public int[][] createTestGrid2() {
        return new int[][] {
            {2, 1, 1},
            {0, 1, 1},
            {1, 0, 1}
        };
    }
    
    /**
     * Helper method to create edge case grid
     */
    public int[][] createTestGrid3() {
        return new int[][] {
            {0, 2}
        };
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Rotting Oranges:");
        System.out.println("========================");
        
        // Test case 1: Example 1 (should return 4)
        System.out.println("\nTest 1: Example from problem (expected: 4)");
        int[][] grid1 = solution.createTestGrid1();
        int expected1 = 4;
        
        long startTime = System.nanoTime();
        int result1 = solution.orangesRotting(grid1);
        long time1 = System.nanoTime() - startTime;
        
        boolean test1 = result1 == expected1;
        System.out.println("Multi-source BFS: " + result1 + " - " + (test1 ? "PASSED" : "FAILED"));
        
        // Test case 2: Example 2 (should return -1)
        System.out.println("\nTest 2: Impossible case (expected: -1)");
        int[][] grid2 = solution.createTestGrid2();
        int result2 = solution.orangesRotting(grid2);
        boolean test2 = result2 == -1;
        System.out.println("Impossible case: " + result2 + " - " + (test2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Example 3 (should return 0)
        System.out.println("\nTest 3: No fresh oranges initially (expected: 0)");
        int[][] grid3 = solution.createTestGrid3();
        int result3 = solution.orangesRotting(grid3);
        boolean test3 = result3 == 0;
        System.out.println("No fresh oranges: " + result3 + " - " + (test3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single rotten orange
        System.out.println("\nTest 4: Single rotten orange");
        int[][] grid4 = {{2}};
        int result4 = solution.orangesRotting(grid4);
        System.out.println("Single rotten: " + result4 + " - " + (result4 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single fresh orange (impossible)
        System.out.println("\nTest 5: Single fresh orange");
        int[][] grid5 = {{1}};
        int result5 = solution.orangesRotting(grid5);
        System.out.println("Single fresh: " + result5 + " - " + (result5 == -1 ? "PASSED" : "FAILED"));
        
        // Test case 6: All rotten initially
        System.out.println("\nTest 6: All rotten initially");
        int[][] grid6 = {{2,2},{2,2}};
        int result6 = solution.orangesRotting(grid6);
        System.out.println("All rotten: " + result6 + " - " + (result6 == 0 ? "PASSED" : "FAILED"));
        
        // Performance
        System.out.println("\nPerformance: " + time1 + " ns");
        
        // Compare different approaches
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING DIFFERENT APPROACHES:");
        System.out.println("=".repeat(70));
        
        compareApproaches(solution);
        
        // Debug version
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DEBUG VERSION OUTPUT:");
        System.out.println("=".repeat(70));
        int[][] debugGrid = solution.createTestGrid1();
        solution.orangesRottingDebug(debugGrid);
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Compare different algorithmic approaches
     */
    private static void compareApproaches(Solution solution) {
        int[][] testGrid = solution.createTestGrid1();
        
        // Test main BFS approach
        int[][] grid1 = copyGrid(testGrid);
        long startTime = System.nanoTime();
        int result1 = solution.orangesRotting(grid1);
        long time1 = System.nanoTime() - startTime;
        
        // Test BFS with minutes tracking
        int[][] grid2 = copyGrid(testGrid);
        startTime = System.nanoTime();
        int result2 = solution.orangesRottingWithMinutes(grid2);
        long time2 = System.nanoTime() - startTime;
        
        // Test in-place BFS
        int[][] grid3 = copyGrid(testGrid);
        startTime = System.nanoTime();
        int result3 = solution.orangesRottingInPlace(grid3);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Performance Comparison:");
        System.out.println("  Multi-source BFS: " + time1 + " ns -> " + result1);
        System.out.println("  BFS with Minutes: " + time2 + " ns -> " + result2);
        System.out.println("  In-place BFS: " + time3 + " ns -> " + result3);
        
        // Verify all produce same results
        boolean allEqual = (result1 == result2) && (result1 == result3);
        System.out.println("All approaches produce same result: " + (allEqual ? "YES" : "NO"));
    }
    
    /**
     * Create a deep copy of grid
     */
    private static int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }
}

/**
 * Additional utility class for orange grid operations
 */
class OrangeUtils {
    /**
     * Count fresh oranges in grid
     */
    public static int countFreshOranges(int[][] grid) {
        int count = 0;
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 1) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Count rotten oranges in grid
     */
    public static int countRottenOranges(int[][] grid) {
        int count = 0;
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 2) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Check if all fresh oranges are reachable from rotten oranges
     */
    public static boolean allFreshReachable(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        
        // Add all rotten oranges to queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }
        
        // Perform BFS
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                    && !visited[newRow][newCol] && grid[newRow][newCol] != 0) {
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
        
        // Check if all fresh oranges were visited
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Simulate one minute of rotting process
     */
    public static int simulateMinute(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int converted = 0;
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        // First pass: identify which fresh oranges will rot
        boolean[][] willRot = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    for (int[] dir : directions) {
                        int newRow = i + dir[0];
                        int newCol = j + dir[1];
                        if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                            && grid[newRow][newCol] == 1) {
                            willRot[newRow][newCol] = true;
                        }
                    }
                }
            }
        }
        
        // Second pass: convert identified fresh oranges to rotten
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (willRot[i][j]) {
                    grid[i][j] = 2;
                    converted++;
                }
            }
        }
        
        return converted;
    }
}
