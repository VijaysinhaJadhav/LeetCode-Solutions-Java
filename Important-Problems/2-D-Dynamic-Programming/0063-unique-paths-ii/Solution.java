
## Solution.java

```java
/**
 * 63. Unique Paths II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are given an m x n integer array obstacleGrid. There is a robot initially located 
 * at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right 
 * corner (i.e., grid[m-1][n-1]). The robot can only move either down or right at any point in time.
 * 
 * An obstacle and space are marked as 1 or 0 respectively in obstacleGrid. 
 * A path that the robot takes cannot include any square that is an obstacle.
 * 
 * Return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 * 
 * Key Insights:
 * 1. Use dynamic programming to count paths to each cell
 * 2. If a cell has an obstacle, paths through it are 0
 * 3. Paths to cell (i,j) = paths from top + paths from left
 * 4. Handle edge cases: obstacles at start or end
 * 
 * Approach (Dynamic Programming with Space Optimization):
 * 1. Check if start or end has obstacle, return 0 if true
 * 2. Use 1D DP array to store paths for current row
 * 3. Initialize first row considering obstacles
 * 4. For each subsequent row, update DP array
 * 5. Return dp[n-1] as result
 * 
 * Time Complexity: O(m × n)
 * Space Complexity: O(n)
 * 
 * Tags: Array, Dynamic Programming, Matrix
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Dynamic Programming with Space Optimization - RECOMMENDED
     * O(m × n) time, O(n) space
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        // If start or end has obstacle, no path exists
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }
        
        // DP array for current row
        int[] dp = new int[n];
        dp[0] = 1; // Starting point
        
        // Initialize first row
        for (int j = 1; j < n; j++) {
            // If current cell has obstacle, no paths
            // Otherwise, paths = paths from left (since we can only come from left in first row)
            dp[j] = (obstacleGrid[0][j] == 1) ? 0 : dp[j-1];
        }
        
        // Process remaining rows
        for (int i = 1; i < m; i++) {
            // Update first column of current row
            if (obstacleGrid[i][0] == 1) {
                dp[0] = 0; // If obstacle, no paths from top
            }
            // else dp[0] remains same (paths from top)
            
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0; // Obstacle cell, no paths
                } else {
                    // Paths = paths from top (dp[j]) + paths from left (dp[j-1])
                    dp[j] = dp[j] + dp[j-1];
                }
            }
        }
        
        return dp[n-1];
    }
    
    /**
     * Approach 2: Standard 2D Dynamic Programming
     * O(m × n) time, O(m × n) space
     */
    public int uniquePathsWithObstacles2D(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        // If start or end has obstacle, no path exists
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }
        
        // DP table
        int[][] dp = new int[m][n];
        dp[0][0] = 1; // Starting point
        
        // Initialize first row
        for (int j = 1; j < n; j++) {
            dp[0][j] = (obstacleGrid[0][j] == 1) ? 0 : dp[0][j-1];
        }
        
        // Initialize first column
        for (int i = 1; i < m; i++) {
            dp[i][0] = (obstacleGrid[i][0] == 1) ? 0 : dp[i-1][0];
        }
        
        // Fill DP table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        
        return dp[m-1][n-1];
    }
    
    /**
     * Approach 3: DFS with Memoization (Top-down DP)
     * O(m × n) time, O(m × n) space
     */
    public int uniquePathsWithObstaclesDFS(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        // If start or end has obstacle, no path exists
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }
        
        int[][] memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        
        return dfs(obstacleGrid, memo, m-1, n-1);
    }
    
    private int dfs(int[][] grid, int[][] memo, int i, int j) {
        // Base cases
        if (i < 0 || j < 0 || grid[i][j] == 1) {
            return 0;
        }
        if (i == 0 && j == 0) {
            return 1;
        }
        
        // Check memo
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        
        // Recursive cases: paths from top + paths from left
        int paths = dfs(grid, memo, i-1, j) + dfs(grid, memo, i, j-1);
        memo[i][j] = paths;
        
        return paths;
    }
    
    /**
     * Approach 4: In-place Modification (Modifies Input)
     * O(m × n) time, O(1) extra space
     */
    public int uniquePathsWithObstaclesInPlace(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        // If start or end has obstacle, no path exists
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }
        
        // Convert obstacles to -1 for easier processing
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    obstacleGrid[i][j] = -1;
                }
            }
        }
        
        // Initialize start
        obstacleGrid[0][0] = 1;
        
        // Initialize first row
        for (int j = 1; j < n; j++) {
            if (obstacleGrid[0][j] == -1) {
                continue; // Keep as -1 for obstacles
            }
            obstacleGrid[0][j] = (obstacleGrid[0][j-1] == -1) ? 0 : obstacleGrid[0][j-1];
        }
        
        // Initialize first column
        for (int i = 1; i < m; i++) {
            if (obstacleGrid[i][0] == -1) {
                continue;
            }
            obstacleGrid[i][0] = (obstacleGrid[i-1][0] == -1) ? 0 : obstacleGrid[i-1][0];
        }
        
        // Fill the grid
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == -1) {
                    continue;
                }
                
                int fromTop = (obstacleGrid[i-1][j] == -1) ? 0 : obstacleGrid[i-1][j];
                int fromLeft = (obstacleGrid[i][j-1] == -1) ? 0 : obstacleGrid[i][j-1];
                obstacleGrid[i][j] = fromTop + fromLeft;
            }
        }
        
        return obstacleGrid[m-1][n-1];
    }
    
    /**
     * Approach 5: BFS with DP (Alternative approach)
     * O(m × n) time, O(m × n) space
     */
    public int uniquePathsWithObstaclesBFS(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }
        
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        
        int[][] directions = {{0, 1}, {1, 0}}; // right, down
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int i = cell[0], j = cell[1];
            
            for (int[] dir : directions) {
                int ni = i + dir[0];
                int nj = j + dir[1];
                
                if (ni >= 0 && ni < m && nj >= 0 && nj < n && obstacleGrid[ni][nj] == 0) {
                    // If not visited or need to update
                    if (dp[ni][nj] == 0) {
                        queue.offer(new int[]{ni, nj});
                    }
                    dp[ni][nj] += dp[i][j];
                }
            }
        }
        
        return dp[m-1][n-1];
    }
    
    /**
     * Approach 6: Mathematical with Combinatorics (Theoretical)
     * Note: This approach doesn't work well with obstacles but included for completeness
     */
    public int uniquePathsWithObstaclesMath(int[][] obstacleGrid) {
        // For grids without obstacles: C(m+n-2, m-1)
        // With obstacles, it's complex - using DP approach 1 as fallback
        return uniquePathsWithObstacles(obstacleGrid);
    }
    
    /**
     * Helper method to visualize the DP process
     */
    public void visualizeDP(int[][] obstacleGrid) {
        System.out.println("\nUnique Paths II Visualization:");
        System.out.println("Obstacle Grid:");
        printGrid(obstacleGrid);
        
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            System.out.println("Start or end has obstacle. No paths exist.");
            return;
        }
        
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        
        System.out.println("\nDP Table Construction:");
        System.out.println("Step 0 - Initial state:");
        printDPTable(dp, obstacleGrid);
        
        // Initialize first row
        for (int j = 1; j < n; j++) {
            dp[0][j] = (obstacleGrid[0][j] == 1) ? 0 : dp[0][j-1];
            System.out.println("Step " + j + " - After initializing first row, column " + j + ":");
            printDPTable(dp, obstacleGrid);
        }
        
        // Initialize first column
        for (int i = 1; i < m; i++) {
            dp[i][0] = (obstacleGrid[i][0] == 1) ? 0 : dp[i-1][0];
            System.out.println("Step " + (n + i - 1) + " - After initializing first column, row " + i + ":");
            printDPTable(dp, obstacleGrid);
        }
        
        // Fill the rest
        int step = m + n - 1;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                step++;
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
                System.out.println("Step " + step + " - After processing cell (" + i + "," + j + "):");
                printDPTable(dp, obstacleGrid);
            }
        }
        
        System.out.println("Final result: " + dp[m-1][n-1] + " unique paths");
    }
    
    private void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    private void printDPTable(int[][] dp, int[][] obstacles) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (obstacles[i][j] == 1) {
                    System.out.print(" X ");
                } else {
                    System.out.print(String.format("%2d", dp[i][j]) + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Unique Paths II Solution:");
        System.out.println("=================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example with obstacle in middle");
        int[][] grid1 = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        };
        int expected1 = 2;
        
        long startTime = System.nanoTime();
        int result1a = solution.uniquePathsWithObstacles(grid1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.uniquePathsWithObstacles2D(grid1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.uniquePathsWithObstaclesDFS(grid1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        // Create fresh copy for in-place modification
        int[][] grid1Copy = deepCopy(grid1);
        int result1d = solution.uniquePathsWithObstaclesInPlace(grid1Copy);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.uniquePathsWithObstaclesBFS(grid1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("Space Optimized DP: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("2D DP:              " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("DFS + Memo:         " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("In-place:           " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("BFS:                " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the DP process
        solution.visualizeDP(grid1);
        
        // Test case 2: Single obstacle at start
        System.out.println("\nTest 2: Obstacle at start");
        int[][] grid2 = {
            {1, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        int result2a = solution.uniquePathsWithObstacles(grid2);
        System.out.println("Obstacle at start: " + result2a + " - " + 
                         (result2a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single obstacle at end
        System.out.println("\nTest 3: Obstacle at end");
        int[][] grid3 = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 1}
        };
        int result3a = solution.uniquePathsWithObstacles(grid3);
        System.out.println("Obstacle at end: " + result3a + " - " + 
                         (result3a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single row with obstacle
        System.out.println("\nTest 4: Single row with obstacle");
        int[][] grid4 = {
            {0, 1, 0}
        };
        int result4a = solution.uniquePathsWithObstacles(grid4);
        System.out.println("Single row: " + result4a + " - " + 
                         (result4a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single column with obstacle
        System.out.println("\nTest 5: Single column with obstacle");
        int[][] grid5 = {
            {0},
            {1},
            {0}
        };
        int result5a = solution.uniquePathsWithObstacles(grid5);
        System.out.println("Single column: " + result5a + " - " + 
                         (result5a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 6: No obstacles
        System.out.println("\nTest 6: No obstacles (3x3 grid)");
        int[][] grid6 = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        int result6a = solution.uniquePathsWithObstacles(grid6);
        System.out.println("No obstacles: " + result6a + " - " + 
                         (result6a == 6 ? "PASSED" : "FAILED"));
        
        // Test case 7: All obstacles
        System.out.println("\nTest 7: All obstacles");
        int[][] grid7 = {
            {1, 1},
            {1, 1}
        };
        int result7a = solution.uniquePathsWithObstacles(grid7);
        System.out.println("All obstacles: " + result7a + " - " + 
                         (result7a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 8: Large grid with obstacles
        System.out.println("\nTest 8: Large grid with strategic obstacles");
        int[][] grid8 = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        };
        int result8a = solution.uniquePathsWithObstacles(grid8);
        System.out.println("Strategic obstacles: " + result8a + " paths");
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Space Optimized DP: " + time1a + " ns");
        System.out.println("2D DP:              " + time1b + " ns");
        System.out.println("DFS + Memo:         " + time1c + " ns");
        System.out.println("In-place:           " + time1d + " ns");
        System.out.println("BFS:                " + time1e + " ns");
        
        // Test all approaches produce same results
        System.out.println("\nTest 10: All approaches consistency");
        boolean allConsistent = result1a == result1b && 
                              result1a == result1c && 
                              result1a == result1d &&
                              result1a == result1e;
        System.out.println("All approaches produce same results: " + allConsistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DYNAMIC PROGRAMMING EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The number of paths to cell (i,j) equals the sum of:");
        System.out.println("1. Paths from top cell (i-1,j)");
        System.out.println("2. Paths from left cell (i,j-1)");
        System.out.println("If a cell has an obstacle, paths through it are 0.");
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("dp[i][j] = {");
        System.out.println("  0, if obstacleGrid[i][j] == 1");
        System.out.println("  dp[i-1][j] + dp[i][j-1], otherwise");
        System.out.println("}");
        
        System.out.println("\nBase Cases:");
        System.out.println("1. dp[0][0] = 1 (if no obstacle at start)");
        System.out.println("2. First row: dp[0][j] = dp[0][j-1] (if no obstacle)");
        System.out.println("3. First column: dp[i][0] = dp[i-1][0] (if no obstacle)");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Space Optimized DP (RECOMMENDED):");
        System.out.println("   Time: O(m × n) - Process each cell once");
        System.out.println("   Space: O(n) - Single array for current row");
        System.out.println("   How it works:");
        System.out.println("     - Use 1D array to store paths for current row");
        System.out.println("     - Update array left to right for each row");
        System.out.println("     - dp[j] represents paths to current row, column j");
        System.out.println("   Pros:");
        System.out.println("     - Optimal space complexity");
        System.out.println("     - Fast and efficient");
        System.out.println("     - Easy to implement");
        System.out.println("   Cons:");
        System.out.println("     - Slightly harder to understand than 2D DP");
        System.out.println("   Best for: Most use cases, production code");
        
        System.out.println("\n2. 2D Dynamic Programming:");
        System.out.println("   Time: O(m × n) - Process each cell once");
        System.out.println("   Space: O(m × n) - Full DP table");
        System.out.println("   How it works:");
        System.out.println("     - Create m × n DP table");
        System.out.println("     - Fill table row by row, column by column");
        System.out.println("     - Follow recurrence relation");
        System.out.println("   Pros:");
        System.out.println("     - Easy to understand and visualize");
        System.out.println("     - Clear implementation");
        System.out.println("   Cons:");
        System.out.println("     - Higher space complexity");
        System.out.println("   Best for: Learning, small grids");
        
        System.out.println("\n3. DFS with Memoization:");
        System.out.println("   Time: O(m × n) - Each cell computed once");
        System.out.println("   Space: O(m × n) - Memoization table + recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Recursive top-down approach");
        System.out.println("     - Memoize computed results");
        System.out.println("     - Start from destination, recurse to source");
        System.out.println("   Pros:");
        System.out.println("     - Natural recursive thinking");
        System.out.println("     - Easy to understand recurrence");
        System.out.println("   Cons:");
        System.out.println("     - Recursion stack overhead");
        System.out.println("     - Higher constant factors");
        System.out.println("   Best for: When top-down thinking is preferred");
        
        System.out.println("\n4. In-place Modification:");
        System.out.println("   Time: O(m × n) - Process each cell once");
        System.out.println("   Space: O(1) - Modify input grid directly");
        System.out.println("   How it works:");
        System.out.println("     - Convert obstacles to sentinel values");
        System.out.println("     - Update grid cells with path counts");
        System.out.println("     - No extra space used");
        System.out.println("   Pros:");
        System.out.println("     - Constant extra space");
        System.out.println("     - Memory efficient");
        System.out.println("   Cons:");
        System.out.println("     - Modifies input (may not be acceptable)");
        System.out.println("     - Harder to debug");
        System.out.println("   Best for: When input modification is allowed");
        
        System.out.println("\n5. BFS with DP:");
        System.out.println("   Time: O(m × n) - Process each reachable cell");
        System.out.println("   Space: O(m × n) - Queue and DP table");
        System.out.println("   How it works:");
        System.out.println("     - Use BFS to explore reachable cells");
        System.out.println("     - Update path counts when visiting neighbors");
        System.out.println("     - Level-by-level exploration");
        System.out.println("   Pros:");
        System.out.println("     - Explores only reachable cells");
        System.out.println("     - Natural for graph problems");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Higher memory usage");
        System.out.println("   Best for: When BFS traversal is needed");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES HANDLING:");
        System.out.println("1. Start cell has obstacle: return 0 immediately");
        System.out.println("2. End cell has obstacle: return 0 immediately");
        System.out.println("3. Single row/column grids: handle carefully");
        System.out.println("4. All obstacles: return 0");
        System.out.println("5. No obstacles: use standard unique paths formula");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with space optimized DP - it's the expected solution");
        System.out.println("2. Explain the recurrence relation clearly");
        System.out.println("3. Handle edge cases first (start/end obstacles)");
        System.out.println("4. Discuss time/space complexity");
        System.out.println("5. Mention alternative approaches briefly");
        System.out.println("6. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        // Additional examples
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ADDITIONAL EXAMPLES:");
        System.out.println("=".repeat(70));
        
        int[][][] testGrids = {
            {{0, 1}, {0, 0}}, // Expected: 1
            {{0, 0}, {1, 0}, {0, 0}}, // Expected: 1
            {{0, 0, 0}, {1, 1, 0}, {0, 0, 0}} // Expected: 1
        };
        
        int[] expectedResults = {1, 1, 1};
        
        for (int i = 0; i < testGrids.length; i++) {
            int result = solution.uniquePathsWithObstacles(testGrids[i]);
            System.out.printf("Example %d: %d paths - %s%n", 
                            i + 1, result, 
                            result == expectedResults[i] ? "PASSED" : "FAILED");
        }
        
        System.out.println("\nAll tests completed!");
    }
    
    // Helper method to create deep copy of 2D array
    private static int[][] deepCopy(int[][] original) {
        if (original == null) return null;
        
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }
}
