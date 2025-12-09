
## Solution.java

```java
/**
 * 64. Minimum Path Sum
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a m x n grid filled with non-negative numbers, find a path from top left 
 * to bottom right, which minimizes the sum of all numbers along its path.
 * 
 * Note: You can only move either down or right at any point in time.
 * 
 * Key Insights:
 * 1. Use dynamic programming to compute minimum path sum to each cell
 * 2. Minimum path to (i,j) = grid[i][j] + min(path from top, path from left)
 * 3. First row and first column have only one possible path
 * 4. Can optimize space using single array
 * 
 * Approach (Dynamic Programming with Space Optimization):
 * 1. Use 1D DP array to store minimum path sums for current row
 * 2. Initialize first element with grid[0][0]
 * 3. Initialize first row with cumulative sums
 * 4. For each subsequent row, update DP array considering minimum of top and left
 * 5. Return last element of DP array as result
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
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        
        // DP array for current row
        int[] dp = new int[n];
        dp[0] = grid[0][0]; // Starting point
        
        // Initialize first row (can only come from left)
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j-1] + grid[0][j];
        }
        
        // Process remaining rows
        for (int i = 1; i < m; i++) {
            // Update first column of current row (can only come from top)
            dp[0] = dp[0] + grid[i][0];
            
            for (int j = 1; j < n; j++) {
                // Minimum path = current value + min(path from top, path from left)
                dp[j] = grid[i][j] + Math.min(dp[j], dp[j-1]);
            }
        }
        
        return dp[n-1];
    }
    
    /**
     * Approach 2: Standard 2D Dynamic Programming
     * O(m × n) time, O(m × n) space
     */
    public int minPathSum2D(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        
        // DP table
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        
        // Initialize first row
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }
        
        // Initialize first column
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        
        // Fill DP table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
            }
        }
        
        return dp[m-1][n-1];
    }
    
    /**
     * Approach 3: In-place Modification (Modifies Input)
     * O(m × n) time, O(1) extra space
     */
    public int minPathSumInPlace(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        
        // Initialize first row
        for (int j = 1; j < n; j++) {
            grid[0][j] += grid[0][j-1];
        }
        
        // Initialize first column
        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i-1][0];
        }
        
        // Fill the grid
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.min(grid[i-1][j], grid[i][j-1]);
            }
        }
        
        return grid[m-1][n-1];
    }
    
    /**
     * Approach 4: DFS with Memoization (Top-down DP)
     * O(m × n) time, O(m × n) space
     */
    public int minPathSumDFS(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        
        int[][] memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        
        return dfs(grid, memo, m-1, n-1);
    }
    
    private int dfs(int[][] grid, int[][] memo, int i, int j) {
        // Base case: starting point
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        
        // Check boundaries
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }
        
        // Check memo
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        
        // Recursive cases: min path from top or left
        int fromTop = dfs(grid, memo, i-1, j);
        int fromLeft = dfs(grid, memo, i, j-1);
        
        int minPath = grid[i][j] + Math.min(fromTop, fromLeft);
        memo[i][j] = minPath;
        
        return minPath;
    }
    
    /**
     * Approach 5: BFS with DP (Dijkstra-like)
     * O(m × n) time, O(m × n) space
     */
    public int minPathSumBFS(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = grid[0][0];
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        
        int[][] directions = {{0, 1}, {1, 0}}; // right, down
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int i = cell[0], j = cell[1];
            
            for (int[] dir : directions) {
                int ni = i + dir[0];
                int nj = j + dir[1];
                
                if (ni >= 0 && ni < m && nj >= 0 && nj < n) {
                    int newCost = dp[i][j] + grid[ni][nj];
                    if (newCost < dp[ni][nj]) {
                        dp[ni][nj] = newCost;
                        queue.offer(new int[]{ni, nj});
                    }
                }
            }
        }
        
        return dp[m-1][n-1];
    }
    
    /**
     * Approach 6: Priority Queue (Dijkstra Algorithm)
     * O(m × n log(m × n)) time, O(m × n) space
     */
    public int minPathSumDijkstra(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = grid[0][0];
        
        // min-heap based on distance
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.offer(new int[]{0, 0, grid[0][0]});
        
        int[][] directions = {{0, 1}, {1, 0}};
        
        while (!pq.isEmpty()) {
            int[] cell = pq.poll();
            int i = cell[0], j = cell[1], cost = cell[2];
            
            // If we found a better path already, skip
            if (cost > dist[i][j]) {
                continue;
            }
            
            for (int[] dir : directions) {
                int ni = i + dir[0];
                int nj = j + dir[1];
                
                if (ni >= 0 && ni < m && nj >= 0 && nj < n) {
                    int newCost = cost + grid[ni][nj];
                    if (newCost < dist[ni][nj]) {
                        dist[ni][nj] = newCost;
                        pq.offer(new int[]{ni, nj, newCost});
                    }
                }
            }
        }
        
        return dist[m-1][n-1];
    }
    
    /**
     * Helper method to visualize the DP process
     */
    public void visualizeMinPath(int[][] grid) {
        System.out.println("\nMinimum Path Sum Visualization:");
        System.out.println("Original Grid:");
        printGrid(grid);
        
        int m = grid.length;
        int n = grid[0].length;
        
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        
        System.out.println("\nDP Table Construction:");
        System.out.println("Step 0 - Initial state:");
        printDPTable(dp);
        
        // Initialize first row
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
            System.out.println("Step " + j + " - After initializing first row, column " + j + ":");
            printDPTable(dp);
        }
        
        // Initialize first column
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
            System.out.println("Step " + (n + i - 1) + " - After initializing first column, row " + i + ":");
            printDPTable(dp);
        }
        
        // Fill the rest
        int step = m + n - 1;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                step++;
                dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
                System.out.println("Step " + step + " - After processing cell (" + i + "," + j + "):");
                System.out.println("  Value: " + grid[i][j] + " + min(" + dp[i-1][j] + ", " + dp[i][j-1] + ") = " + dp[i][j]);
                printDPTable(dp);
            }
        }
        
        System.out.println("Minimum path sum: " + dp[m-1][n-1]);
        
        // Show the actual path
        showPath(grid, dp);
    }
    
    private void showPath(int[][] grid, int[][] dp) {
        System.out.println("\nOptimal Path Reconstruction:");
        List<int[]> path = new ArrayList<>();
        int i = grid.length - 1;
        int j = grid[0].length - 1;
        
        // Reconstruct path from end to start
        path.add(new int[]{i, j});
        
        while (i > 0 || j > 0) {
            if (i == 0) {
                j--; // Can only go left
            } else if (j == 0) {
                i--; // Can only go up
            } else {
                if (dp[i-1][j] < dp[i][j-1]) {
                    i--; // Came from top
                } else {
                    j--; // Came from left
                }
            }
            path.add(0, new int[]{i, j}); // Add to beginning
        }
        
        // Print path
        System.out.print("Path: ");
        int total = 0;
        for (int k = 0; k < path.size(); k++) {
            int[] cell = path.get(k);
            int value = grid[cell[0]][cell[1]];
            total += value;
            System.out.print(value);
            if (k < path.size() - 1) {
                System.out.print(" → ");
            }
        }
        System.out.println("\nTotal: " + total);
        
        // Visualize path on grid
        System.out.println("\nPath Visualization:");
        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[0].length; j++) {
                boolean inPath = false;
                for (int[] cell : path) {
                    if (cell[0] == i && cell[1] == j) {
                        inPath = true;
                        break;
                    }
                }
                if (inPath) {
                    System.out.print("[" + grid[i][j] + "] ");
                } else {
                    System.out.print(" " + grid[i][j] + "  ");
                }
            }
            System.out.println();
        }
    }
    
    private void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(String.format("%2d", grid[i][j]) + " ");
            }
            System.out.println();
        }
    }
    
    private void printDPTable(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                System.out.print(String.format("%3d", dp[i][j]) + " ");
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
        
        System.out.println("Testing Minimum Path Sum Solution:");
        System.out.println("==================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[][] grid1 = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };
        int expected1 = 7;
        
        long startTime = System.nanoTime();
        int result1a = solution.minPathSum(grid1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.minPathSum2D(grid1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        // Create fresh copy for in-place modification
        int[][] grid1Copy = deepCopy(grid1);
        int result1c = solution.minPathSumInPlace(grid1Copy);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.minPathSumDFS(grid1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.minPathSumBFS(grid1);
        long time1e = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1f = solution.minPathSumDijkstra(grid1);
        long time1f = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        boolean test1f = result1f == expected1;
        
        System.out.println("Space Optimized DP: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("2D DP:              " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("In-place:           " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("DFS + Memo:         " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("BFS:                " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        System.out.println("Dijkstra:           " + result1f + " - " + (test1f ? "PASSED" : "FAILED"));
        
        // Visualize the DP process
        solution.visualizeMinPath(grid1);
        
        // Test case 2: Single row
        System.out.println("\nTest 2: Single row");
        int[][] grid2 = {
            {1, 2, 3}
        };
        int result2a = solution.minPathSum(grid2);
        System.out.println("Single row: " + result2a + " - " + 
                         (result2a == 6 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single column
        System.out.println("\nTest 3: Single column");
        int[][] grid3 = {
            {1},
            {2},
            {3}
        };
        int result3a = solution.minPathSum(grid3);
        System.out.println("Single column: " + result3a + " - " + 
                         (result3a == 6 ? "PASSED" : "FAILED"));
        
        // Test case 4: 2x2 grid
        System.out.println("\nTest 4: 2x2 grid");
        int[][] grid4 = {
            {1, 2},
            {3, 4}
        };
        int result4a = solution.minPathSum(grid4);
        System.out.println("2x2 grid: " + result4a + " - " + 
                         (result4a == 7 ? "PASSED" : "FAILED"));
        
        // Test case 5: All same values
        System.out.println("\nTest 5: All same values");
        int[][] grid5 = {
            {2, 2, 2},
            {2, 2, 2},
            {2, 2, 2}
        };
        int result5a = solution.minPathSum(grid5);
        System.out.println("All same: " + result5a + " - " + 
                         (result5a == 10 ? "PASSED" : "FAILED"));
        
        // Test case 6: Large values
        System.out.println("\nTest 6: Large values with clear path");
        int[][] grid6 = {
            {1, 100, 100},
            {1, 1, 100},
            {100, 1, 1}
        };
        int result6a = solution.minPathSum(grid6);
        System.out.println("Clear path: " + result6a + " - " + 
                         (result6a == 5 ? "PASSED" : "FAILED"));
        
        // Test case 7: One cell
        System.out.println("\nTest 7: One cell grid");
        int[][] grid7 = {
            {5}
        };
        int result7a = solution.minPathSum(grid7);
        System.out.println("One cell: " + result7a + " - " + 
                         (result7a == 5 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison");
        System.out.println("Space Optimized DP: " + time1a + " ns");
        System.out.println("2D DP:              " + time1b + " ns");
        System.out.println("In-place:           " + time1c + " ns");
        System.out.println("DFS + Memo:         " + time1d + " ns");
        System.out.println("BFS:                " + time1e + " ns");
        System.out.println("Dijkstra:           " + time1f + " ns");
        
        // Test all approaches produce same results
        System.out.println("\nTest 9: All approaches consistency");
        boolean allConsistent = result1a == result1b && 
                              result1a == result1c && 
                              result1a == result1d &&
                              result1a == result1e &&
                              result1a == result1f;
        System.out.println("All approaches produce same results: " + allConsistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DYNAMIC PROGRAMMING EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The minimum path sum to cell (i,j) equals:");
        System.out.println("grid[i][j] + min(path from top, path from left)");
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])");
        
        System.out.println("\nBase Cases:");
        System.out.println("1. dp[0][0] = grid[0][0]");
        System.out.println("2. First row: dp[0][j] = grid[0][j] + dp[0][j-1]");
        System.out.println("3. First column: dp[i][0] = grid[i][0] + dp[i-1][0]");
        
        System.out.println("\nSpace Optimization:");
        System.out.println("We only need the previous row to compute current row.");
        System.out.println("So we can use a single array and update it row by row.");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Space Optimized DP (RECOMMENDED):");
        System.out.println("   Time: O(m × n) - Process each cell once");
        System.out.println("   Space: O(n) - Single array for current row");
        System.out.println("   How it works:");
        System.out.println("     - Use 1D array to store min path sums for current row");
        System.out.println("     - Update array left to right for each row");
        System.out.println("     - dp[j] = grid[i][j] + min(dp[j], dp[j-1])");
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
        
        System.out.println("\n3. In-place Modification:");
        System.out.println("   Time: O(m × n) - Process each cell once");
        System.out.println("   Space: O(1) - Modify input grid directly");
        System.out.println("   How it works:");
        System.out.println("     - Update grid cells with cumulative min path sums");
        System.out.println("     - No extra space used");
        System.out.println("   Pros:");
        System.out.println("     - Constant extra space");
        System.out.println("     - Memory efficient");
        System.out.println("   Cons:");
        System.out.println("     - Modifies input (may not be acceptable)");
        System.out.println("     - Original grid data is lost");
        System.out.println("   Best for: When input modification is allowed");
        
        System.out.println("\n4. DFS with Memoization:");
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
        
        System.out.println("\n5. Dijkstra Algorithm:");
        System.out.println("   Time: O(m × n log(m × n)) - Priority queue operations");
        System.out.println("   Space: O(m × n) - Distance array and priority queue");
        System.out.println("   How it works:");
        System.out.println("     - Treat grid as graph with weighted edges");
        System.out.println("     - Use priority queue to always expand minimum cost path");
        System.out.println("     - Dijkstra's algorithm for shortest path");
        System.out.println("   Pros:");
        System.out.println("     - Can handle more complex movement patterns");
        System.out.println("     - Guarantees optimal solution");
        System.out.println("   Cons:");
        System.out.println("     - Overkill for this problem");
        System.out.println("     - Higher time complexity");
        System.out.println("   Best for: Graphs with arbitrary movement");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PATH RECONSTRUCTION:");
        System.out.println("To find the actual path that gives minimum sum:");
        System.out.println("1. Start from destination (m-1, n-1)");
        System.out.println("2. At each step, move to the neighbor with smaller dp value");
        System.out.println("3. Continue until reaching start (0, 0)");
        System.out.println("4. Reverse the path to get start to end direction");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with space optimized DP - it's the expected solution");
        System.out.println("2. Explain the recurrence relation clearly");
        System.out.println("3. Handle edge cases (single row/column)");
        System.out.println("4. Discuss time/space complexity");
        System.out.println("5. Mention path reconstruction if asked");
        System.out.println("6. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        // Additional examples with path visualization
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ADDITIONAL EXAMPLES WITH PATH VISUALIZATION:");
        System.out.println("=".repeat(70));
        
        int[][][] testGrids = {
            {{1, 2, 3}, {4, 5, 6}}, // Expected: 12
            {{1, 2}, {1, 1}},        // Expected: 3
            {{1, 2, 5}, {3, 2, 1}}   // Expected: 6
        };
        
        int[] expectedResults = {12, 3, 6};
        
        for (int i = 0; i < testGrids.length; i++) {
            System.out.println("\nExample " + (i + 1) + ":");
            int result = solution.minPathSum(testGrids[i]);
            System.out.printf("Result: %d, Expected: %d - %s%n", 
                            result, expectedResults[i],
                            result == expectedResults[i] ? "PASSED" : "FAILED");
            
            // Show path for the first example
            if (i == 0) {
                solution.visualizeMinPath(testGrids[i]);
            }
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
