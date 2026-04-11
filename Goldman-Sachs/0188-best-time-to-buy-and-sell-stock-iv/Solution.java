
# Solution.java

```java
import java.util.*;

/**
 * 188. Best Time to Buy and Sell Stock IV
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Maximum profit with at most k transactions.
 * 
 * Key Insights:
 * 1. DP state: dp[t][i] = max profit with t transactions using first i days
 * 2. Optimization: dp[t][i] = max(dp[t][i-1], prices[i] + max_diff)
 * 3. Where max_diff = max(dp[t-1][j] - prices[j]) for j < i
 * 4. If k >= n/2, use greedy (unlimited transactions)
 */
class Solution {
    
    /**
     * Approach 1: DP with Optimization (Recommended)
     * Time: O(k × n), Space: O(n)
     * 
     * Steps:
     * 1. If k >= n/2, use greedy (unlimited transactions)
     * 2. Initialize dp array of size n+1
     * 3. For each transaction from 1 to k:
     *    - Track max_diff = -prices[0]
     *    - For each day i from 1 to n-1:
     *      - Update dp[i+1] = max(dp[i], prices[i] + max_diff)
     *      - Update max_diff = max(max_diff, dp[i] - prices[i])
     * 4. Return dp[n]
     */
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        
        // If k >= n/2, we can make unlimited transactions
        if (k >= n / 2) {
            return maxProfitUnlimited(prices);
        }
        
        int[] dp = new int[n + 1];
        
        for (int t = 1; t <= k; t++) {
            int maxDiff = -prices[0];
            int[] prev = dp.clone();
            
            for (int i = 1; i < n; i++) {
                dp[i + 1] = Math.max(dp[i], prices[i] + maxDiff);
                maxDiff = Math.max(maxDiff, prev[i] - prices[i]);
            }
        }
        
        return dp[n];
    }
    
    private int maxProfitUnlimited(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }
    
    /**
     * Approach 2: Full DP (2D Array)
     * Time: O(k × n), Space: O(k × n)
     * 
     * More intuitive but uses more space
     */
    public int maxProfitFullDP(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        
        if (k >= n / 2) {
            return maxProfitUnlimited(prices);
        }
        
        int[][] dp = new int[k + 1][n];
        
        for (int t = 1; t <= k; t++) {
            int maxDiff = -prices[0];
            for (int i = 1; i < n; i++) {
                dp[t][i] = Math.max(dp[t][i - 1], prices[i] + maxDiff);
                maxDiff = Math.max(maxDiff, dp[t - 1][i - 1] - prices[i]);
            }
        }
        
        return dp[k][n - 1];
    }
    
    /**
     * Approach 3: DP with Two Arrays (Space Optimized)
     * Time: O(k × n), Space: O(n)
     * 
     * Similar to approach 1 but more explicit
     */
    public int maxProfitTwoArrays(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        
        if (k >= n / 2) {
            return maxProfitUnlimited(prices);
        }
        
        int[] dp = new int[n];
        int[] prev = new int[n];
        
        for (int t = 1; t <= k; t++) {
            int maxDiff = -prices[0];
            for (int i = 1; i < n; i++) {
                dp[i] = Math.max(dp[i - 1], prices[i] + maxDiff);
                maxDiff = Math.max(maxDiff, prev[i - 1] - prices[i]);
            }
            prev = dp.clone();
        }
        
        return dp[n - 1];
    }
    
    /**
     * Approach 4: Recursive DP with Memoization
     * Time: O(k × n²), Space: O(k × n)
     * 
     * Slower but shows recursive structure
     */
    public int maxProfitRecursive(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        
        Integer[][][] memo = new Integer[n][k + 1][2];
        return dfs(prices, 0, k, 0, memo);
    }
    
    private int dfs(int[] prices, int day, int k, int holding, Integer[][][] memo) {
        if (day == prices.length || k == 0) return 0;
        if (memo[day][k][holding] != null) return memo[day][k][holding];
        
        int skip = dfs(prices, day + 1, k, holding, memo);
        int action;
        
        if (holding == 1) {
            // Sell
            action = prices[day] + dfs(prices, day + 1, k - 1, 0, memo);
        } else {
            // Buy
            action = -prices[day] + dfs(prices, day + 1, k, 1, memo);
        }
        
        memo[day][k][holding] = Math.max(skip, action);
        return memo[day][k][holding];
    }
    
    /**
     * Approach 5: Using Local and Global DP Arrays
     * Time: O(k × n), Space: O(k)
     * 
     * Alternative DP formulation
     */
    public int maxProfitLocalGlobal(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        
        if (k >= n / 2) {
            return maxProfitUnlimited(prices);
        }
        
        int[] local = new int[k + 1];
        int[] global = new int[k + 1];
        
        for (int i = 1; i < n; i++) {
            int diff = prices[i] - prices[i - 1];
            for (int j = k; j >= 1; j--) {
                local[j] = Math.max(global[j - 1] + Math.max(diff, 0), local[j] + diff);
                global[j] = Math.max(global[j], local[j]);
            }
        }
        
        return global[k];
    }
    
    /**
     * Helper: Visualize DP process
     */
    public void visualizeDP(int k, int[] prices) {
        System.out.println("\nBest Time to Buy and Sell Stock IV Visualization:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nPrices: " + Arrays.toString(prices));
        System.out.println("k = " + k);
        
        int n = prices.length;
        if (n == 0) {
            System.out.println("Empty prices → 0");
            return;
        }
        
        if (k >= n / 2) {
            System.out.println("\nk >= n/2, using greedy (unlimited transactions)");
            int profit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    int gain = prices[i] - prices[i - 1];
                    profit += gain;
                    System.out.printf("  Buy at %d, sell at %d → profit +%d = %d%n", 
                        prices[i - 1], prices[i], gain, profit);
                }
            }
            System.out.println("\nTotal profit: " + profit);
            return;
        }
        
        System.out.println("\nDP Table (profit after t transactions up to day i):");
        System.out.print("    ");
        for (int i = 0; i < n; i++) {
            System.out.printf("d%-2d ", i);
        }
        System.out.println();
        System.out.print("    ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-3d ", prices[i]);
        }
        System.out.println();
        
        int[][] dp = new int[k + 1][n];
        
        for (int t = 1; t <= k; t++) {
            int maxDiff = -prices[0];
            System.out.printf("t=%d | ", t);
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    dp[t][i] = 0;
                    System.out.printf("%-3d ", dp[t][i]);
                    continue;
                }
                dp[t][i] = Math.max(dp[t][i - 1], prices[i] + maxDiff);
                maxDiff = Math.max(maxDiff, dp[t - 1][i - 1] - prices[i]);
                System.out.printf("%-3d ", dp[t][i]);
            }
            System.out.println();
        }
        
        System.out.println("\nFinal maximum profit: " + dp[k][n - 1]);
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            {2, new int[]{2, 4, 1}, 2},
            {2, new int[]{3, 2, 6, 5, 0, 3}, 7},
            {1, new int[]{7, 1, 5, 3, 6, 4}, 5},
            {3, new int[]{3, 3, 5, 0, 0, 3, 1, 4}, 6},
            {0, new int[]{1, 2, 3}, 0},
            {2, new int[]{}, 0},
            {2, new int[]{1}, 0},
            {100, new int[]{1, 2, 3, 4, 5}, 4},  // k > n/2 → greedy
            {2, new int[]{1, 2, 3, 4, 5}, 4},
            {2, new int[]{5, 4, 3, 2, 1}, 0}
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
            int k = (int) testCases[i][0];
            int[] prices = (int[]) testCases[i][1];
            int expected = (int) testCases[i][2];
            
            System.out.printf("\nTest %d: k=%d, prices=%s%n", i + 1, k, Arrays.toString(prices));
            
            int result1 = maxProfit(k, prices.clone());
            int result2 = maxProfitFullDP(k, prices.clone());
            int result3 = maxProfitTwoArrays(k, prices.clone());
            int result4 = maxProfitRecursive(k, prices.clone());
            int result5 = maxProfitLocalGlobal(k, prices.clone());
            
            boolean allMatch = result1 == expected && result2 == expected &&
                              result3 == expected && result4 == expected &&
                              result5 == expected;
            
            if (allMatch) {
                System.out.println("✓ PASS - Max profit: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeDP(k, prices);
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
        
        int n = 1000;
        int k = 100;
        int[] prices = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            prices[i] = rand.nextInt(1000);
        }
        
        System.out.println("Test Setup: " + n + " days, k=" + k);
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: Optimized DP
        int[] p1 = prices.clone();
        long start = System.currentTimeMillis();
        results[0] = maxProfit(k, p1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Full DP
        int[] p2 = prices.clone();
        start = System.currentTimeMillis();
        results[1] = maxProfitFullDP(k, p2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Two Arrays DP
        int[] p3 = prices.clone();
        start = System.currentTimeMillis();
        results[2] = maxProfitTwoArrays(k, p3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Recursive (skip for large n)
        times[3] = -1;
        results[3] = -1;
        
        // Method 5: Local-Global DP
        int[] p5 = prices.clone();
        start = System.currentTimeMillis();
        results[4] = maxProfitLocalGlobal(k, p5);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Optimized DP           | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Full DP                | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. Two Arrays DP          | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. Recursive              | %9s | %6s%n", "N/A", "N/A");
        System.out.printf("5. Local-Global DP        | %9d | %6d%n", times[4], results[4]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[4];
        System.out.println("\nAll O(k×n) methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Optimized DP is fastest with O(k×n) time");
        System.out.println("2. Full DP uses more memory but similar time");
        System.out.println("3. Recursive is O(k×n²) and too slow for large inputs");
        System.out.println("4. All DP methods scale linearly with k and n");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Empty prices:");
        int[] empty = {};
        System.out.println("   Input: k=2, prices=[]");
        System.out.println("   Output: " + maxProfit(2, empty));
        
        System.out.println("\n2. k = 0:");
        int[] prices1 = {1, 2, 3, 4, 5};
        System.out.println("   Input: k=0, prices=[1,2,3,4,5]");
        System.out.println("   Output: " + maxProfit(0, prices1));
        
        System.out.println("\n3. Single day:");
        int[] prices2 = {100};
        System.out.println("   Input: k=2, prices=[100]");
        System.out.println("   Output: " + maxProfit(2, prices2));
        
        System.out.println("\n4. Decreasing prices:");
        int[] prices3 = {5, 4, 3, 2, 1};
        System.out.println("   Input: k=2, prices=[5,4,3,2,1]");
        System.out.println("   Output: " + maxProfit(2, prices3));
        
        System.out.println("\n5. k >= n/2 (unlimited transactions):");
        int[] prices4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("   Input: k=100, prices=[1..10]");
        System.out.println("   Output: " + maxProfit(100, prices4));
        
        System.out.println("\n6. Large k with fluctuating prices:");
        int[] prices5 = {3, 5, 2, 8, 1, 9, 4, 7, 6, 10};
        System.out.println("   Input: k=5, prices=" + Arrays.toString(prices5));
        System.out.println("   Output: " + maxProfit(5, prices5));
    }
    
    /**
     * Helper: Explain DP recurrence
     */
    public void explainDP() {
        System.out.println("\nDP Recurrence Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nState Definition:");
        System.out.println("dp[t][i] = maximum profit with at most t transactions using first i days");
        
        System.out.println("\nBase Cases:");
        System.out.println("  dp[0][i] = 0 (0 transactions → 0 profit)");
        System.out.println("  dp[t][0] = 0 (0 days → 0 profit)");
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("  dp[t][i] = max(dp[t][i-1], prices[i] + max_diff)");
        System.out.println("  where max_diff = max(dp[t-1][j] - prices[j]) for j < i");
        
        System.out.println("\nIntuition:");
        System.out.println("  - dp[t][i-1]: Skip day i (no transaction on day i)");
        System.out.println("  - prices[i] + max_diff: Sell on day i after buying on best previous day");
        
        System.out.println("\nOptimization:");
        System.out.println("  We can compute max_diff on the fly:");
        System.out.println("  max_diff = max(max_diff, dp[t-1][i-1] - prices[i])");
        
        System.out.println("\nSpecial Case: k >= n/2");
        System.out.println("  When k is large enough, we can make unlimited transactions");
        System.out.println("  Profit = sum of all positive price increases");
        
        System.out.println("\nExample: k=2, prices=[3,2,6,5,0,3]");
        System.out.println("  t=1: dp[1][1]=0, dp[1][2]=4, dp[1][3]=4, dp[1][4]=4, dp[1][5]=4");
        System.out.println("  t=2: dp[2][1]=0, dp[2][2]=4, dp[2][3]=4, dp[2][4]=4, dp[2][5]=7");
        System.out.println("  Result = 7");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What is a transaction? (Buy then sell)");
        System.out.println("   - Can we hold multiple stocks? (No)");
        System.out.println("   - What are the constraints? (k ≤ 100, n ≤ 1000)");
        
        System.out.println("\n2. Start with simpler cases:");
        System.out.println("   - k = 1: One transaction (Stock I)");
        System.out.println("   - k = 2: Two transactions (Stock III)");
        System.out.println("   - k ≥ n/2: Unlimited transactions (Stock II)");
        
        System.out.println("\n3. Propose DP solution:");
        System.out.println("   - State: dp[t][i] = max profit with t transactions up to day i");
        System.out.println("   - Transition: sell on day i or skip");
        System.out.println("   - Optimize with max_diff tracking");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - Time: O(k × n)");
        System.out.println("   - Space: O(n) with optimization");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - k = 0");
        System.out.println("   - Empty prices");
        System.out.println("   - k ≥ n/2 (use greedy)");
        System.out.println("   - Decreasing prices (0 profit)");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Forgetting to handle k >= n/2 case");
        System.out.println("   - Off-by-one errors in DP indices");
        System.out.println("   - Not initializing max_diff correctly");
        System.out.println("   - Using O(k×n²) algorithm");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("188. Best Time to Buy and Sell Stock IV");
        System.out.println("========================================");
        
        // Explain DP recurrence
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
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        
        // If k >= n/2, unlimited transactions (greedy)
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    profit += prices[i] - prices[i - 1];
                }
            }
            return profit;
        }
        
        int[] dp = new int[n + 1];
        
        for (int t = 1; t <= k; t++) {
            int maxDiff = -prices[0];
            int[] prev = dp.clone();
            
            for (int i = 1; i < n; i++) {
                dp[i + 1] = Math.max(dp[i], prices[i] + maxDiff);
                maxDiff = Math.max(maxDiff, prev[i] - prices[i]);
            }
        }
        
        return dp[n];
    }
}
            """);
        
        System.out.println("\nAlternative (Local-Global DP):");
        System.out.println("""
class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
            }
            return profit;
        }
        
        int[] local = new int[k + 1];
        int[] global = new int[k + 1];
        
        for (int i = 1; i < n; i++) {
            int diff = prices[i] - prices[i - 1];
            for (int j = k; j >= 1; j--) {
                local[j] = Math.max(global[j - 1] + Math.max(diff, 0), local[j] + diff);
                global[j] = Math.max(global[j], local[j]);
            }
        }
        
        return global[k];
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. DP with max_diff optimization achieves O(k×n) time");
        System.out.println("2. Special case: if k >= n/2, use greedy (unlimited transactions)");
        System.out.println("3. Space can be optimized to O(n) by reusing arrays");
        System.out.println("4. The recurrence is similar to Stock III but generalized");
        System.out.println("5. Always handle empty prices and k = 0 edge cases");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(k × n) for DP, O(n) for greedy special case");
        System.out.println("- Space: O(n) with optimization");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle transaction fees?");
        System.out.println("2. How would you add a cooldown period?");
        System.out.println("3. What if you can only buy after selling? (Already true)");
        System.out.println("4. How would you find the actual transactions, not just profit?");
        System.out.println("5. How would you handle very large k (e.g., 10^9)?");
    }
}
