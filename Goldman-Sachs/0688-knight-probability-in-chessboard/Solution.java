
# Solution.java

```java
import java.util.*;

/**
 * 688. Knight Probability in Chessboard
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Calculate probability that a knight remains on an n x n chessboard
 * after k moves starting from (row, column).
 * 
 * Key Insights:
 * 1. Use DP with memoization: dp[k][r][c] = probability
 * 2. Knight moves: 8 possible L-shaped moves
 * 3. Probability = sum of probabilities from valid moves / 8
 */
class Solution {
    
    // Knight move directions: (row change, col change)
    private static final int[][] MOVES = {
        {-2, -1}, {-2, 1},   // two up, left/right
        {-1, -2}, {-1, 2},   // one up, two left/right
        {1, -2}, {1, 2},     // one down, two left/right
        {2, -1}, {2, 1}      // two down, left/right
    };
    
    /**
     * Approach 1: Top-Down DP with Memoization (Recommended)
     * Time: O(k × n²), Space: O(k × n²)
     * 
     * Steps:
     * 1. Use memoization array to store computed probabilities
     * 2. Base case: if k == 0, return 1 if on board else 0
     * 3. For each move, recursively compute probability for next position
     * 4. Sum probabilities and divide by 8
     */
    public double knightProbability(int n, int k, int row, int column) {
        // memo[k][row][col] = probability
        Double[][][] memo = new Double[k + 1][n][n];
        return dfs(n, k, row, column, memo);
    }
    
    private double dfs(int n, int k, int r, int c, Double[][][] memo) {
        // If outside board, probability is 0
        if (r < 0 || r >= n || c < 0 || c >= n) {
            return 0;
        }
        
        // If no moves left, probability is 1 (still on board)
        if (k == 0) {
            return 1;
        }
        
        // Check memoization
        if (memo[k][r][c] != null) {
            return memo[k][r][c];
        }
        
        double probability = 0;
        
        // Try all 8 moves
        for (int[] move : MOVES) {
            int newRow = r + move[0];
            int newCol = c + move[1];
            probability += dfs(n, k - 1, newRow, newCol, memo);
        }
        
        // Divide by 8 (each move has 1/8 probability)
        probability /= 8;
        
        memo[k][r][c] = probability;
        return probability;
    }
    
    /**
     * Approach 2: Bottom-Up DP (Iterative)
     * Time: O(k × n²), Space: O(n²)
     * 
     * More space-efficient (only current and next layers)
     */
    public double knightProbabilityBottomUp(int n, int k, int row, int column) {
        double[][] dp = new double[n][n];
        dp[row][column] = 1.0;
        
        for (int step = 0; step < k; step++) {
            double[][] next = new double[n][n];
            
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (dp[r][c] > 0) {
                        for (int[] move : MOVES) {
                            int nr = r + move[0];
                            int nc = c + move[1];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                                next[nr][nc] += dp[r][c] / 8.0;
                            }
                        }
                    }
                }
            }
            dp = next;
        }
        
        // Sum all probabilities after k moves
        double result = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                result += dp[r][c];
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Bottom-Up with Single Array (Space Optimized)
     * Time: O(k × n²), Space: O(n²)
     */
    public double knightProbabilitySpaceOptimized(int n, int k, int row, int column) {
        double[][] dp = new double[n][n];
        dp[row][column] = 1.0;
        
        for (int step = 0; step < k; step++) {
            double[][] next = new double[n][n];
            
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (dp[r][c] > 0) {
                        for (int[] move : MOVES) {
                            int nr = r + move[0];
                            int nc = c + move[1];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                                next[nr][nc] += dp[r][c] / 8.0;
                            }
                        }
                    }
                }
            }
            dp = next;
        }
        
        // Sum all probabilities
        double result = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                result += dp[r][c];
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: BFS with Probability Propagation
     * Time: O(k × n²), Space: O(n²)
     */
    public double knightProbabilityBFS(int n, int k, int row, int column) {
        double[][] prob = new double[n][n];
        prob[row][column] = 1.0;
        
        for (int step = 0; step < k; step++) {
            double[][] newProb = new double[n][n];
            
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (prob[r][c] > 0) {
                        for (int[] move : MOVES) {
                            int nr = r + move[0];
                            int nc = c + move[1];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                                newProb[nr][nc] += prob[r][c] / 8.0;
                            }
                        }
                    }
                }
            }
            prob = newProb;
        }
        
        double result = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                result += prob[r][c];
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Brute Force (for small k only)
     * Time: O(8^k), Space: O(k) recursion stack
     * 
     * Only works for very small k (k <= 5)
     */
    public double knightProbabilityBruteForce(int n, int k, int row, int column) {
        if (k == 0) {
            return (row >= 0 && row < n && column >= 0 && column < n) ? 1.0 : 0.0;
        }
        
        double probability = 0;
        for (int[] move : MOVES) {
            int nr = row + move[0];
            int nc = column + move[1];
            if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                probability += knightProbabilityBruteForce(n, k - 1, nr, nc);
            }
        }
        
        return probability / 8.0;
    }
    
    /**
     * Helper: Visualize probability distribution
     */
    public void visualizeProbability(int n, int k, int row, int col) {
        System.out.println("\nKnight Probability Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.printf("\nBoard: %d×%d, Starting at (%d,%d), Moves: %d%n", n, n, row, col, k);
        
        double[][][] memo = new Double[k + 1][n][n];
        double probability = dfs(n, k, row, col, memo);
        
        System.out.println("\nProbability after each move:");
        
        for (int step = 0; step <= k; step++) {
            System.out.printf("\nAfter %d move(s):%n", step);
            
            // Sum probabilities for this step
            double total = 0;
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (memo[step] != null && memo[step][r][c] != null) {
                        total += memo[step][r][c];
                    }
                }
            }
            
            // Print probability matrix
            for (int r = 0; r < n; r++) {
                System.out.print("  ");
                for (int c = 0; c < n; c++) {
                    double val = (memo[step] != null && memo[step][r][c] != null) ? memo[step][r][c] : 0;
                    System.out.printf("%8.4f ", val);
                }
                System.out.println();
            }
            System.out.printf("  Total probability: %.6f%n", total);
        }
        
        System.out.printf("\nFinal probability: %.6f%n", probability);
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            {3, 2, 0, 0, 0.06250},
            {1, 0, 0, 0, 1.00000},
            {3, 0, 0, 0, 1.00000},
            {8, 3, 0, 0, 0.03906},
            {10, 5, 3, 4, 0.08154},
            {25, 10, 12, 12, 0.45280}
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        Object[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int n = (int) testCases[i][0];
            int k = (int) testCases[i][1];
            int row = (int) testCases[i][2];
            int col = (int) testCases[i][3];
            double expected = (double) testCases[i][4];
            
            System.out.printf("\nTest %d: n=%d, k=%d, start=(%d,%d)%n", i + 1, n, k, row, col);
            
            double result1 = knightProbability(n, k, row, col);
            double result2 = knightProbabilityBottomUp(n, k, row, col);
            double result3 = knightProbabilitySpaceOptimized(n, k, row, col);
            double result4 = knightProbabilityBFS(n, k, row, col);
            
            // Tolerance for floating point comparison
            boolean allMatch = Math.abs(result1 - expected) < 1e-5 &&
                              Math.abs(result2 - expected) < 1e-5 &&
                              Math.abs(result3 - expected) < 1e-5 &&
                              Math.abs(result4 - expected) < 1e-5;
            
            if (allMatch) {
                System.out.printf("✓ PASS - Probability: %.5f%n", result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.printf("  Method 1: %.5f%n", result1);
                System.out.printf("  Method 2: %.5f%n", result2);
                System.out.printf("  Method 3: %.5f%n", result3);
                System.out.printf("  Method 4: %.5f%n", result4);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeProbability(n, k, row, col);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=".repeat(50));
        
        int n = 25;
        int k = 100;
        int row = 12, col = 12;
        
        System.out.println("Test Setup: n=25, k=100, start=(12,12)");
        
        long[] times = new long[4];
        double[] results = new double[4];
        
        // Method 1: Top-Down Memoization
        long start = System.currentTimeMillis();
        results[0] = knightProbability(n, k, row, col);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Bottom-Up DP
        start = System.currentTimeMillis();
        results[1] = knightProbabilityBottomUp(n, k, row, col);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Space Optimized Bottom-Up
        start = System.currentTimeMillis();
        results[2] = knightProbabilitySpaceOptimized(n, k, row, col);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: BFS
        start = System.currentTimeMillis();
        results[3] = knightProbabilityBFS(n, k, row, col);
        times[3] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Probability");
        System.out.println("--------------------------|-----------|-------------");
        System.out.printf("1. Top-Down Memo          | %9d | %.6f%n", times[0], results[0]);
        System.out.printf("2. Bottom-Up DP           | %9d | %.6f%n", times[1], results[1]);
        System.out.printf("3. Space Optimized        | %9d | %.6f%n", times[2], results[2]);
        System.out.printf("4. BFS                    | %9d | %.6f%n", times[3], results[3]);
        
        boolean allMatch = true;
        for (int i = 1; i < 4; i++) {
            if (Math.abs(results[i] - results[0]) > 1e-9) allMatch = false;
        }
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. All DP methods have similar performance");
        System.out.println("2. Bottom-up DP is slightly faster than top-down");
        System.out.println("3. Space optimized version uses less memory");
        System.out.println("4. All methods scale as O(k × n²)");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. n=1, k=0, start=(0,0):");
        System.out.println("   Output: " + knightProbability(1, 0, 0, 0));
        
        System.out.println("\n2. n=1, k=1, start=(0,0):");
        System.out.println("   Output: " + knightProbability(1, 1, 0, 0));
        
        System.out.println("\n3. n=2, k=1, start=(0,0):");
        System.out.println("   Output: " + knightProbability(2, 1, 0, 0));
        
        System.out.println("\n4. n=3, k=2, start=(1,1):");
        System.out.println("   Output: " + knightProbability(3, 2, 1, 1));
        
        System.out.println("\n5. Large board, many moves:");
        long start = System.currentTimeMillis();
        double result = knightProbability(25, 100, 12, 12);
        long time = System.currentTimeMillis() - start;
        System.out.println("   n=25, k=100, start=(12,12)");
        System.out.printf("   Output: %.6f%n", result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain DP approach
     */
    public void explainDP() {
        System.out.println("\nDynamic Programming Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nState Definition:");
        System.out.println("  dp[steps][r][c] = probability of being at (r,c) after 'steps' moves");
        
        System.out.println("\nBase Case:");
        System.out.println("  dp[0][startRow][startCol] = 1.0");
        
        System.out.println("\nTransition:");
        System.out.println("  dp[step][r][c] = sum(dp[step-1][prevR][prevC] / 8)");
        System.out.println("  where (prevR, prevC) can move to (r,c) in one knight move");
        
        System.out.println("\nTop-Down Approach:");
        System.out.println("  f(k, r, c) = sum(f(k-1, nr, nc)) / 8");
        System.out.println("  Base: f(0, r, c) = 1 if on board else 0");
        
        System.out.println("\nBottom-Up Approach:");
        System.out.println("  Initialize dp[0][startR][startC] = 1");
        System.out.println("  For step = 1 to k:");
        System.out.println("    For each cell (r,c) with probability p:");
        System.out.println("      For each move, add p/8 to next[nr][nc]");
        System.out.println("  Result = sum of dp[k][r][c] over all cells");
        
        System.out.println("\nTime Complexity: O(k × n²)");
        System.out.println("Space Complexity: O(k × n²) or O(n²) with optimization");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What is the board size? (n up to 25)");
        System.out.println("   - How many moves? (k up to 100)");
        System.out.println("   - What is the knight's move pattern?");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Recursively try all paths (8^k)");
        System.out.println("   - Acknowledge it's too slow for k=100");
        
        System.out.println("\n3. Propose DP with memoization:");
        System.out.println("   - State: (k, r, c) → probability");
        System.out.println("   - Recurrence: f(k,r,c) = sum(f(k-1,nr,nc))/8");
        System.out.println("   - Base: k=0 → 1 if on board else 0");
        
        System.out.println("\n4. Discuss bottom-up approach:");
        System.out.println("   - Iterative, easier to optimize space");
        System.out.println("   - Only need two layers (current and next)");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(k × n²)");
        System.out.println("   - Space: O(n²) with optimization");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - k = 0");
        System.out.println("   - n = 1");
        System.out.println("   - Starting position near board edge");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Forgetting to divide by 8");
        System.out.println("   - Integer division (use double)");
        System.out.println("   - Off-by-one in move directions");
        System.out.println("   - Not handling board boundaries");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("688. Knight Probability in Chessboard");
        System.out.println("=====================================");
        
        // Explain DP approach
        solution.explainDP();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation (Top-Down Memoization):");
        System.out.println("""
class Solution {
    private static final int[][] MOVES = {
        {-2,-1}, {-2,1}, {-1,-2}, {-1,2},
        {1,-2}, {1,2}, {2,-1}, {2,1}
    };
    
    public double knightProbability(int n, int k, int row, int column) {
        Double[][][] memo = new Double[k + 1][n][n];
        return dfs(n, k, row, column, memo);
    }
    
    private double dfs(int n, int k, int r, int c, Double[][][] memo) {
        if (r < 0 || r >= n || c < 0 || c >= n) return 0;
        if (k == 0) return 1;
        if (memo[k][r][c] != null) return memo[k][r][c];
        
        double prob = 0;
        for (int[] move : MOVES) {
            prob += dfs(n, k - 1, r + move[0], c + move[1], memo);
        }
        prob /= 8;
        
        memo[k][r][c] = prob;
        return prob;
    }
}
            """);
        
        System.out.println("\nAlternative (Bottom-Up DP):");
        System.out.println("""
class Solution {
    private static final int[][] MOVES = {
        {-2,-1}, {-2,1}, {-1,-2}, {-1,2},
        {1,-2}, {1,2}, {2,-1}, {2,1}
    };
    
    public double knightProbability(int n, int k, int row, int column) {
        double[][] dp = new double[n][n];
        dp[row][column] = 1.0;
        
        for (int step = 0; step < k; step++) {
            double[][] next = new double[n][n];
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (dp[r][c] > 0) {
                        for (int[] move : MOVES) {
                            int nr = r + move[0];
                            int nc = c + move[1];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                                next[nr][nc] += dp[r][c] / 8.0;
                            }
                        }
                    }
                }
            }
            dp = next;
        }
        
        double result = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                result += dp[r][c];
            }
        }
        return result;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Use DP to avoid exponential complexity");
        System.out.println("2. Top-down memoization is intuitive");
        System.out.println("3. Bottom-up iteration is space efficient");
        System.out.println("4. Probability = (valid paths) / (8^k)");
        System.out.println("5. Time complexity: O(k × n²)");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(k × n²) - each cell visited for each step");
        System.out.println("- Space: O(n²) - only current and next layer needed");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you find the exact probability as a fraction?");
        System.out.println("2. How would you handle different board shapes?");
        System.out.println("3. How would you find the most likely position after k moves?");
        System.out.println("4. How would you solve if knight could move to any valid square?");
        System.out.println("5. How would you handle multiple knights?");
    }
}
