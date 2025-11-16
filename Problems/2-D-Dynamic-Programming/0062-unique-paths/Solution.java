
## Solution.java

```java
/**
 * 62. Unique Paths
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * There is a robot on an m x n grid. The robot starts at the top-left corner and
 * tries to reach the bottom-right corner. The robot can only move either down or right.
 * Return the number of possible unique paths.
 * 
 * Key Insights:
 * 1. Dynamic Programming: dp[i][j] = dp[i-1][j] + dp[i][j-1]
 * 2. Combinatorics: C(m+n-2, m-1) = (m+n-2)! / ((m-1)! * (n-1)!)
 * 3. Space Optimization: Use single array instead of 2D table
 * 
 * Approach (DP with Space Optimization):
 * 1. Initialize dp array of size n with 1s (first row)
 * 2. For each subsequent row, update dp[j] += dp[j-1]
 * 3. Return dp[n-1]
 * 
 * Time Complexity: O(m×n)
 * Space Complexity: O(n)
 * 
 * Tags: Math, Dynamic Programming, Combinatorics
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Dynamic Programming with Space Optimization (Recommended)
     * O(m×n) time, O(n) space
     */
    public int uniquePaths(int m, int n) {
        // Use single array to store current row
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // First row always has 1 path to each cell
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // dp[j] represents paths from above, dp[j-1] represents paths from left
                dp[j] += dp[j - 1];
            }
        }
        
        return dp[n - 1];
    }
    
    /**
     * Approach 2: Standard Dynamic Programming
     * O(m×n) time, O(m×n) space
     * More intuitive but uses more space
     */
    public int uniquePathsDP(int m, int n) {
        int[][] dp = new int[m][n];
        
        // Initialize first row and first column to 1
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }
        
        // Fill DP table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        
        return dp[m - 1][n - 1];
    }
    
    /**
     * Approach 3: Combinatorics (Mathematical)
     * O(min(m,n)) time, O(1) space
     * Uses combination formula: C(m+n-2, m-1)
     */
    public int uniquePathsMath(int m, int n) {
        // Total moves needed: (m-1) downs + (n-1) rights = m+n-2 moves
        // We need to choose (m-1) down moves from (m+n-2) total moves
        // Or equivalently, choose (n-1) right moves
        int totalMoves = m + n - 2;
        int k = Math.min(m - 1, n - 1); // Choose smaller for efficiency
        
        // Calculate combination: C(totalMoves, k)
        long result = 1;
        
        // Using multiplicative formula: C(n,k) = product_{i=1 to k} (n - k + i) / i
        for (int i = 1; i <= k; i++) {
            result = result * (totalMoves - k + i) / i;
        }
        
        return (int) result;
    }
    
    /**
     * Approach 4: Recursive with Memoization
     * O(m×n) time, O(m×n) space
     * Top-down approach
     */
    public int uniquePathsRecursive(int m, int n) {
        int[][] memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return pathsHelper(0, 0, m, n, memo);
    }
    
    private int pathsHelper(int i, int j, int m, int n, int[][] memo) {
        // Base case: reached destination
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        
        // Base case: out of bounds
        if (i >= m || j >= n) {
            return 0;
        }
        
        // Check memo
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        
        // Recursive case: move right + move down
        int rightPaths = pathsHelper(i, j + 1, m, n, memo);
        int downPaths = pathsHelper(i + 1, j, m, n, memo);
        
        memo[i][j] = rightPaths + downPaths;
        return memo[i][j];
    }
    
    /**
     * Approach 5: Iterative with Two Rows (Alternative Space Optimization)
     * O(m×n) time, O(n) space
     * Uses two arrays instead of one for clarity
     */
    public int uniquePathsTwoRows(int m, int n) {
        int[] prev = new int[n];
        int[] curr = new int[n];
        Arrays.fill(prev, 1);
        Arrays.fill(curr, 1);
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                curr[j] = prev[j] + curr[j - 1];
            }
            // Swap arrays for next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        
        return prev[n - 1];
    }
    
    /**
     * Approach 6: DFS with Memoization (Alternative Recursive)
     * O(m×n) time, O(m×n) space
     * Different recursive formulation
     */
    public int uniquePathsDFS(int m, int n) {
        int[][] memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(0, 0, m, n, memo);
    }
    
    private int dfs(int i, int j, int m, int n, int[][] memo) {
        // Base case: reached destination
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        
        // Check memo
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        
        int paths = 0;
        
        // Move right if possible
        if (j < n - 1) {
            paths += dfs(i, j + 1, m, n, memo);
        }
        
        // Move down if possible
        if (i < m - 1) {
            paths += dfs(i + 1, j, m, n, memo);
        }
        
        memo[i][j] = paths;
        return paths;
    }
    
    /**
     * Helper method to visualize the DP table
     */
    public void visualizeDP(int m, int n, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Grid: " + m + " x " + n);
        
        if ("Standard DP".equals(approach)) {
            visualizeStandardDP(m, n);
        } else if ("Space Optimized".equals(approach)) {
            visualizeSpaceOptimizedDP(m, n);
        } else if ("Combinatorics".equals(approach)) {
            visualizeCombinatorics(m, n);
        }
    }
    
    private void visualizeStandardDP(int m, int n) {
        int[][] dp = new int[m][n];
        
        System.out.println("\nDP Table Construction:");
        
        // Initialize and print first row
        System.out.print("    ");
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
            System.out.printf("%3d ", j);
        }
        System.out.println();
        
        for (int i = 0; i < m; i++) {
            System.out.printf("%2d: ", i);
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
                System.out.printf("%3d ", dp[i][j]);
            }
            System.out.println();
        }
        
        System.out.println("Final unique paths: " + dp[m - 1][n - 1]);
        
        // Show some sample paths for small grids
        if (m <= 3 && n <= 3) {
            showSamplePaths(m, n);
        }
    }
    
    private void visualizeSpaceOptimizedDP(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        
        System.out.println("\nSpace Optimized DP Process:");
        System.out.print("Initial: ");
        printArray(dp);
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j - 1];
            }
            System.out.printf("Row %2d: ", i);
            printArray(dp);
        }
        
        System.out.println("Final unique paths: " + dp[n - 1]);
    }
    
    private void visualizeCombinatorics(int m, int n) {
        System.out.println("\nCombinatorics Approach:");
        System.out.println("Total moves needed: (" + (m-1) + " downs + " + (n-1) + " rights) = " + (m+n-2) + " moves");
        System.out.println("We need to choose " + (m-1) + " down positions from " + (m+n-2) + " total moves");
        
        long result = 1;
        int total = m + n - 2;
        int k = Math.min(m - 1, n - 1);
        
        System.out.println("Calculation: C(" + total + ", " + k + ")");
        System.out.print("Formula: ");
        for (int i = 1; i <= k; i++) {
            if (i > 1) System.out.print(" × ");
            System.out.print("(" + (total - k + i) + "/" + i + ")");
            result = result * (total - k + i) / i;
        }
        System.out.println("\nResult: " + result);
    }
    
    private void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    private void showSamplePaths(int m, int n) {
        System.out.println("\nSample Paths for " + m + "x" + n + " grid:");
        
        if (m == 3 && n == 2) {
            System.out.println("1. Right -> Down -> Down");
            System.out.println("2. Down -> Down -> Right");
            System.out.println("3. Down -> Right -> Down");
        } else if (m == 2 && n == 2) {
            System.out.println("1. Right -> Down");
            System.out.println("2. Down -> Right");
        } else if (m == 1 && n == 1) {
            System.out.println("1. (Already at destination)");
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Unique Paths:");
        System.out.println("====================\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: 3x7 grid");
        int m1 = 3, n1 = 7;
        int expected1 = 28;
        testImplementation(solution, m1, n1, expected1, "Space Optimized");
        
        // Test case 2: Small grid
        System.out.println("\nTest 2: 3x2 grid");
        int m2 = 3, n2 = 2;
        int expected2 = 3;
        testImplementation(solution, m2, n2, expected2, "Space Optimized");
        
        // Test case 3: 1x1 grid
        System.out.println("\nTest 3: 1x1 grid");
        int m3 = 1, n3 = 1;
        int expected3 = 1;
        testImplementation(solution, m3, n3, expected3, "Space Optimized");
        
        // Test case 4: 1xN grid
        System.out.println("\nTest 4: 1x5 grid");
        int m4 = 1, n4 = 5;
        int expected4 = 1;
        testImplementation(solution, m4, n4, expected4, "Space Optimized");
        
        // Test case 5: Mx1 grid
        System.out.println("\nTest 5: 5x1 grid");
        int m5 = 5, n5 = 1;
        int expected5 = 1;
        testImplementation(solution, m5, n5, expected5, "Space Optimized");
        
        // Test case 6: 2x2 grid
        System.out.println("\nTest 6: 2x2 grid");
        int m6 = 2, n6 = 2;
        int expected6 = 2;
        testImplementation(solution, m6, n6, expected6, "Space Optimized");
        
        // Test case 7: Large grid
        System.out.println("\nTest 7: 10x10 grid");
        int m7 = 10, n7 = 10;
        int expected7 = 48620; // C(18,9) = 48620
        testImplementation(solution, m7, n7, expected7, "Space Optimized");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: DYNAMIC PROGRAMMING");
        System.out.println("=".repeat(70));
        
        explainDPApproach(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, int m, int n, 
                                         int expected, String approach) {
        long startTime = System.nanoTime();
        int result = 0;
        switch (approach) {
            case "Space Optimized":
                result = solution.uniquePaths(m, n);
                break;
            case "Standard DP":
                result = solution.uniquePathsDP(m, n);
                break;
            case "Combinatorics":
                result = solution.uniquePathsMath(m, n);
                break;
            case "Recursive":
                result = solution.uniquePathsRecursive(m, n);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        boolean passed = (result == expected);
        System.out.printf("%s: Expected=%d, Got=%d, Time=%,d ns - %s%n",
                approach, expected, result, time, (passed ? "PASSED" : "FAILED"));
        
        if (!passed) {
            System.out.println("  Input: m=" + m + ", n=" + n);
        }
        
        // Visualization for small grids
        if (passed && m <= 5 && n <= 5) {
            solution.visualizeDP(m, n, approach);
        }
    }
    
    private static void comparePerformance(Solution solution) {
        int m = 50, n = 50;
        
        System.out.println("Performance test with " + m + "x" + n + " grid:");
        
        // Test Space Optimized DP
        long startTime = System.nanoTime();
        solution.uniquePaths(m, n);
        long time1 = System.nanoTime() - startTime;
        
        // Test Standard DP
        startTime = System.nanoTime();
        solution.uniquePathsDP(m, n);
        long time2 = System.nanoTime() - startTime;
        
        // Test Combinatorics
        startTime = System.nanoTime();
        solution.uniquePathsMath(m, n);
        long time3 = System.nanoTime() - startTime;
        
        // Test Two Rows
        startTime = System.nanoTime();
        solution.uniquePathsTwoRows(m, n);
        long time4 = System.nanoTime() - startTime;
        
        // Skip recursive for large grid (stack overflow risk)
        
        System.out.printf("Space Optimized:  %,12d ns%n", time1);
        System.out.printf("Standard DP:      %,12d ns%n", time2);
        System.out.printf("Combinatorics:    %,12d ns%n", time3);
        System.out.printf("Two Rows:         %,12d ns%n", time4);
        System.out.println("Recursive:        (skipped - risk of stack overflow)");
    }
    
    private static void explainDPApproach(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("Each cell can only be reached from the cell above it or the cell to its left.");
        System.out.println("Therefore, the number of paths to cell (i,j) is the sum of:");
        System.out.println("  - Paths to cell (i-1,j) [from above]");
        System.out.println("  - Paths to cell (i,j-1) [from left]");
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("dp[i][j] = dp[i-1][j] + dp[i][j-1]");
        
        System.out.println("\nBase Cases:");
        System.out.println("dp[0][j] = 1 for all j (only way is moving right)");
        System.out.println("dp[i][0] = 1 for all i (only way is moving down)");
        
        System.out.println("\nWhy it works:");
        System.out.println("- The first row and column have only 1 path (straight line)");
        System.out.println("- For any other cell, we sum the paths from both possible directions");
        System.out.println("- This systematically counts all possible paths without duplication");
        
        System.out.println("\nSpace Optimization:");
        System.out.println("- We only need the previous row to compute the current row");
        System.out.println("- Can use a single array that gets updated from left to right");
        System.out.println("- dp[j] represents paths to current row, j-th column");
        System.out.println("- Update: dp[j] = dp[j] (from above) + dp[j-1] (from left)");
        
        System.out.println("\nExample Walkthrough: 3x2 grid");
        solution.visualizeDP(3, 2, "Standard DP");
        
        System.out.println("\nMathematical Insight (Combinatorics):");
        System.out.println("To reach from (0,0) to (m-1,n-1), we need:");
        System.out.println("  - (m-1) down moves");
        System.out.println("  - (n-1) right moves");
        System.out.println("Total moves: (m+n-2)");
        System.out.println("We need to choose positions for down moves (or right moves)");
        System.out.println("Number of unique paths = C(m+n-2, m-1) = C(m+n-2, n-1)");
        
        System.out.println("\nTime Complexity: O(m×n) for DP, O(min(m,n)) for combinatorics");
        System.out.println("Space Complexity: O(n) for optimized DP, O(1) for combinatorics");
    }
    
    private static void checkAllImplementations(Solution solution) {
        Object[][][] testCases = {
            {{3, 7}, {28}},    // Standard
            {{3, 2}, {3}},     // Small
            {{1, 1}, {1}},     // 1x1
            {{1, 5}, {1}},     // 1xN
            {{5, 1}, {1}},     // Mx1
            {{2, 2}, {2}},     // 2x2
            {{10, 10}, {48620}} // Large
        };
        
        int[] expected = {28, 3, 1, 1, 1, 2, 48620};
        
        String[] methods = {
            "Space Optimized",
            "Standard DP", 
            "Combinatorics",
            "Two Rows",
            "Recursive",
            "DFS"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            int m = (int) testCases[i][0][0];
            int n = (int) testCases[i][0][1];
            int expectedVal = ((int[]) testCases[i][1])[0];
            
            System.out.printf("\nTest case %d: %dx%d grid (expected: %d)%n",
                    i + 1, m, n, expectedVal);
            
            int[] results = new int[methods.length];
            results[0] = solution.uniquePaths(m, n);
            results[1] = solution.uniquePathsDP(m, n);
            results[2] = solution.uniquePathsMath(m, n);
            results[3] = solution.uniquePathsTwoRows(m, n);
            results[4] = solution.uniquePathsRecursive(m, n);
            results[5] = solution.uniquePathsDFS(m, n);
            
            boolean caseConsistent = true;
            for (int j = 0; j < methods.length; j++) {
                boolean correct = (results[j] == expectedVal);
                System.out.printf("  %-15s: %d %s%n", methods[j], results[j],
                        correct ? "✓" : "✗ (expected " + expectedVal + ")");
                if (!correct) {
                    caseConsistent = false;
                    allConsistent = false;
                }
            }
            
            if (!caseConsistent) {
                System.out.println("  INCONSISTENT RESULTS!");
            }
        }
        
        System.out.println("\nOverall consistency: " + (allConsistent ? "ALL PASSED ✓" : "SOME FAILED ✗"));
        
        // Algorithm comparison table
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON SUMMARY");
        System.out.println("=".repeat(70));
        
        printAlgorithmComparison();
    }
    
    private static void printAlgorithmComparison() {
        System.out.println("\n1. SPACE OPTIMIZED DP (RECOMMENDED):");
        System.out.println("   Time: O(m×n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Good balance of efficiency and simplicity");
        System.out.println("     - Space efficient");
        System.out.println("     - Easy to implement");
        System.out.println("   Cons:");
        System.out.println("     - Not as fast as combinatorics for very large grids");
        System.out.println("   Use when: General case, good performance needed");
        
        System.out.println("\n2. STANDARD DYNAMIC PROGRAMMING:");
        System.out.println("   Time: O(m×n)");
        System.out.println("   Space: O(m×n)");
        System.out.println("   Pros:");
        System.out.println("     - Most intuitive and easy to understand");
        System.out.println("     - Clear visualization of the DP table");
        System.out.println("   Cons:");
        System.out.println("     - Uses more memory than optimized versions");
        System.out.println("   Use when: Learning, small grids, need clarity");
        
        System.out.println("\n3. COMBINATORICS (MATHEMATICAL):");
        System.out.println("   Time: O(min(m,n))");
        System.out.println("   Space: O(1)");
        System.out.println("   Pros:");
        System.out.println("     - Fastest for large grids");
        System.out.println("     - Constant space complexity");
        System.out.println("     - Elegant mathematical solution");
        System.out.println("   Cons:");
        System.out.println("     - Risk of integer overflow for very large grids");
        System.out.println("     - Less intuitive than DP");
        System.out.println("   Use when: Large grids, optimal performance needed");
        
        System.out.println("\n4. TWO ROWS DP:");
        System.out.println("   Time: O(m×n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Clear separation of previous and current rows");
        System.out.println("     - Easy to understand and debug");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex than single array");
        System.out.println("   Use when: Prefer clarity over minimalism");
        
        System.out.println("\n5. RECURSIVE WITH MEMOIZATION:");
        System.out.println("   Time: O(m×n)");
        System.out.println("   Space: O(m×n)");
        System.out.println("   Pros:");
        System.out.println("     - Top-down approach is natural");
        System.out.println("     - Easy to understand recurrence");
        System.out.println("   Cons:");
        System.out.println("     - Risk of stack overflow for large grids");
        System.out.println("     - More overhead than iterative DP");
        System.out.println("   Use when: Teaching recursive thinking, small grids");
        
        System.out.println("\n6. DFS WITH MEMOIZATION:");
        System.out.println("   Time: O(m×n)");
        System.out.println("   Space: O(m×n)");
        System.out.println("   Pros:");
        System.out.println("     - Different perspective on the problem");
        System.out.println("     - Can be extended for path reconstruction");
        System.out.println("   Cons:");
        System.out.println("     - Same disadvantages as recursive approach");
        System.out.println("   Use when: Exploring different algorithmic approaches");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with standard DP - it's the most intuitive");
        System.out.println("2. Explain the recurrence relation clearly");
        System.out.println("3. Mention space optimization as an improvement");
        System.out.println("4. Discuss the combinatorial approach for large grids");
        System.out.println("5. Handle edge cases: 1x1, 1xN, Mx1 grids");
        System.out.println("6. Discuss time/space complexity trade-offs");
        System.out.println("=".repeat(70));
    }
}
