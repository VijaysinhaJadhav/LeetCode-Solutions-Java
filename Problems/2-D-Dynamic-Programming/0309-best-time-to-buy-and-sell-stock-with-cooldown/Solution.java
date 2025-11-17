
## Solution.java

```java
/**
 * 309. Best Time to Buy and Sell Stock with Cooldown
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * Find the maximum profit you can achieve with cooldown constraint: after selling, cannot buy next day.
 * 
 * Key Insights:
 * 1. Use state machine DP with three states: hold, sold, rest
 * 2. hold[i] = max(hold[i-1], rest[i-1] - prices[i])
 * 3. sold[i] = hold[i-1] + prices[i]
 * 4. rest[i] = max(rest[i-1], sold[i-1])
 * 5. Final answer = max(sold[n-1], rest[n-1])
 * 
 * Approach (State Machine DP):
 * 1. Initialize three state variables
 * 2. Update states for each day based on transitions
 * 3. Return maximum of sold and rest states
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Dynamic Programming, State Machine
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: State Machine DP with Space Optimization - RECOMMENDED
     * O(n) time, O(1) space
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        int n = prices.length;
        
        // State variables
        int hold = -prices[0];  // Maximum profit when holding stock
        int sold = 0;           // Maximum profit just after selling
        int rest = 0;           // Maximum profit when in cooldown/rest
        
        for (int i = 1; i < n; i++) {
            int prevHold = hold;
            int prevSold = sold;
            int prevRest = rest;
            
            // State transitions:
            // hold: max(keep holding, buy from rest state)
            hold = Math.max(prevHold, prevRest - prices[i]);
            
            // sold: sell from hold state
            sold = prevHold + prices[i];
            
            // rest: max(keep resting, come from sold state)
            rest = Math.max(prevRest, prevSold);
        }
        
        // Final profit is max of sold or rest (cannot end with holding stock)
        return Math.max(sold, rest);
    }
    
    /**
     * Approach 2: Explicit State Machine DP Arrays
     * O(n) time, O(n) space
     */
    public int maxProfitArrays(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        int n = prices.length;
        int[] hold = new int[n];  // Maximum profit ending with holding stock
        int[] sold = new int[n];  // Maximum profit ending with just sold
        int[] rest = new int[n];  // Maximum profit ending with cooldown
        
        // Base cases
        hold[0] = -prices[0];
        sold[0] = 0;
        rest[0] = 0;
        
        for (int i = 1; i < n; i++) {
            // State transitions
            hold[i] = Math.max(hold[i-1], rest[i-1] - prices[i]);
            sold[i] = hold[i-1] + prices[i];
            rest[i] = Math.max(rest[i-1], sold[i-1]);
        }
        
        return Math.max(sold[n-1], rest[n-1]);
    }
    
    /**
     * Approach 3: DP with Two States (Alternative formulation)
     * O(n) time, O(n) space
     */
    public int maxProfitTwoStates(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        int n = prices.length;
        int[] buy = new int[n];  // Max profit ending with buy
        int[] sell = new int[n]; // Max profit ending with sell
        
        buy[0] = -prices[0];
        sell[0] = 0;
        
        for (int i = 1; i < n; i++) {
            // Can buy only if we didn't sell yesterday (cooldown)
            buy[i] = Math.max(buy[i-1], (i >= 2 ? sell[i-2] : 0) - prices[i]);
            sell[i] = Math.max(sell[i-1], buy[i-1] + prices[i]);
        }
        
        return sell[n-1];
    }
    
    /**
     * Approach 4: Recursive with Memoization
     * O(n) time, O(n) space
     */
    public int maxProfitRecursive(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        int n = prices.length;
        int[][] memo = new int[n][2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        
        return dfs(prices, 0, 0, memo); // Start at day 0, not holding
    }
    
    private int dfs(int[] prices, int day, int holding, int[][] memo) {
        if (day >= prices.length) {
            return 0;
        }
        
        if (memo[day][holding] != -1) {
            return memo[day][holding];
        }
        
        // Option 1: Do nothing today (cooldown or wait)
        int doNothing = dfs(prices, day + 1, holding, memo);
        
        int result;
        if (holding == 1) {
            // If holding stock, can sell
            int sell = prices[day] + dfs(prices, day + 2, 0, memo); // Cooldown after sell
            result = Math.max(doNothing, sell);
        } else {
            // If not holding, can buy
            int buy = -prices[day] + dfs(prices, day + 1, 1, memo);
            result = Math.max(doNothing, buy);
        }
        
        memo[day][holding] = result;
        return result;
    }
    
    /**
     * Approach 5: Iterative DP with Cooldown Period
     * O(n) time, O(n) space
     */
    public int maxProfitIterative(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        int n = prices.length;
        int[] dp = new int[n + 1]; // dp[i] = max profit up to day i
        
        for (int i = 1; i < n; i++) {
            // Option 1: Do nothing today
            dp[i + 1] = Math.max(dp[i + 1], dp[i]);
            
            // Try buying at day j and selling at day i
            for (int j = 0; j < i; j++) {
                int profit = prices[i] - prices[j];
                if (profit > 0) {
                    // If we sell at day i, we had cooldown until day j-2
                    int prevProfit = (j >= 2) ? dp[j - 2] : 0;
                    dp[i + 1] = Math.max(dp[i + 1], prevProfit + profit);
                }
            }
        }
        
        return dp[n];
    }
    
    /**
     * Approach 6: Optimized Iterative DP
     * O(n) time, O(1) space - Most efficient iterative
     */
    public int maxProfitOptimized(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        int n = prices.length;
        int buy = -prices[0];
        int sell = 0;
        int prevSell = 0;
        int prevBuy;
        
        for (int i = 1; i < n; i++) {
            prevBuy = buy;
            // Can buy only if we didn't sell yesterday (cooldown)
            buy = Math.max(prevBuy, prevSell - prices[i]);
            prevSell = sell;
            sell = Math.max(prevSell, prevBuy + prices[i]);
        }
        
        return sell;
    }
    
    /**
     * Helper method to visualize the state transitions
     */
    public void visualizeStateMachine(int[] prices) {
        System.out.println("\nBest Time to Buy/Sell with Cooldown Visualization:");
        System.out.println("Prices: " + Arrays.toString(prices));
        
        if (prices.length <= 1) {
            System.out.println("Maximum profit: 0");
            return;
        }
        
        int n = prices.length;
        int[] hold = new int[n];
        int[] sold = new int[n];
        int[] rest = new int[n];
        
        // Initialize
        hold[0] = -prices[0];
        sold[0] = 0;
        rest[0] = 0;
        
        System.out.println("\nDay 0:");
        System.out.println("  hold: " + hold[0] + " (buy first stock)");
        System.out.println("  sold: " + sold[0] + " (cannot sell)");
        System.out.println("  rest: " + rest[0] + " (initial state)");
        
        for (int i = 1; i < n; i++) {
            int prevHold = hold[i-1];
            int prevSold = sold[i-1];
            int prevRest = rest[i-1];
            
            hold[i] = Math.max(prevHold, prevRest - prices[i]);
            sold[i] = prevHold + prices[i];
            rest[i] = Math.max(prevRest, prevSold);
            
            System.out.println("\nDay " + i + " (Price: " + prices[i] + "):");
            System.out.println("  hold: " + hold[i] + 
                " = max(keep holding: " + prevHold + 
                ", buy from rest: " + (prevRest - prices[i]) + ")");
            System.out.println("  sold: " + sold[i] + 
                " = sell from hold: " + (prevHold + prices[i]));
            System.out.println("  rest: " + rest[i] + 
                " = max(keep resting: " + prevRest + 
                ", from sold: " + prevSold + ")");
        }
        
        int maxProfit = Math.max(sold[n-1], rest[n-1]);
        System.out.println("\nMaximum profit: " + maxProfit);
        
        // Show optimal transaction sequence
        showOptimalTransactions(prices, hold, sold, rest);
    }
    
    private void showOptimalTransactions(int[] prices, int[] hold, int[] sold, int[] rest) {
        System.out.println("\nOptimal Transaction Sequence:");
        List<String> transactions = new ArrayList<>();
        int state = sold[prices.length-1] >= rest[prices.length-1] ? 2 : 0; // 2=sold, 0=rest
        
        for (int i = prices.length-1; i >= 0; i--) {
            if (state == 2) { // sold state
                transactions.add(0, "SELL at " + prices[i]);
                // Came from hold state
                state = 1; // move to hold state
            } else if (state == 0) { // rest state
                transactions.add(0, "REST");
                // Could come from rest or sold
                if (i > 0 && rest[i] == sold[i-1]) {
                    state = 2; // came from sold
                } else {
                    state = 0; // came from rest
                }
            } else if (state == 1) { // hold state
                transactions.add(0, "BUY at " + prices[i]);
                // Could come from hold or rest
                if (i > 0 && hold[i] == rest[i-1] - prices[i]) {
                    state = 0; // came from rest
                } else {
                    state = 1; // came from hold
                }
            }
        }
        
        // Print transactions
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println("Day " + i + ": " + transactions.get(i));
        }
        
        // Calculate profit from transactions
        int calculatedProfit = calculateProfit(transactions, prices);
        System.out.println("Calculated profit: " + calculatedProfit);
    }
    
    private int calculateProfit(List<String> transactions, int[] prices) {
        int profit = 0;
        int buyPrice = -1;
        
        for (int i = 0; i < transactions.size(); i++) {
            String action = transactions.get(i);
            if (action.startsWith("BUY")) {
                buyPrice = prices[i];
            } else if (action.startsWith("SELL")) {
                if (buyPrice != -1) {
                    profit += prices[i] - buyPrice;
                    buyPrice = -1;
                }
            }
        }
        
        return profit;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Best Time to Buy/Sell Stock with Cooldown:");
        System.out.println("=================================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example [1,2,3,0,2]");
        int[] prices1 = {1, 2, 3, 0, 2};
        int expected1 = 3;
        
        long startTime = System.nanoTime();
        int result1a = solution.maxProfit(prices1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.maxProfitArrays(prices1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.maxProfitTwoStates(prices1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.maxProfitRecursive(prices1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.maxProfitOptimized(prices1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("State Machine DP: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DP Arrays:        " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Two States:       " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Recursive:        " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Optimized:        " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the state machine
        solution.visualizeStateMachine(prices1);
        
        // Test case 2: Single price
        System.out.println("\nTest 2: Single price [1]");
        int[] prices2 = {1};
        int result2a = solution.maxProfit(prices2);
        System.out.println("Single price: " + result2a + " - " + 
                         (result2a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 3: Decreasing prices
        System.out.println("\nTest 3: Decreasing prices [3,2,1]");
        int[] prices3 = {3, 2, 1};
        int result3a = solution.maxProfit(prices3);
        System.out.println("Decreasing: " + result3a + " - " + 
                         (result3a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 4: All same prices
        System.out.println("\nTest 4: All same prices [2,2,2,2]");
        int[] prices4 = {2, 2, 2, 2};
        int result4a = solution.maxProfit(prices4);
        System.out.println("All same: " + result4a + " - " + 
                         (result4a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 5: Complex case
        System.out.println("\nTest 5: Complex case [6,1,3,2,4,7]");
        int[] prices5 = {6, 1, 3, 2, 4, 7};
        int result5a = solution.maxProfit(prices5);
        System.out.println("Complex: " + result5a + " - " + 
                         (result5a == 6 ? "PASSED" : "FAILED")); // buy@1, sell@3, cooldown, buy@2, sell@7
        
        // Test case 6: Two days only
        System.out.println("\nTest 6: Two days [1,2]");
        int[] prices6 = {1, 2};
        int result6a = solution.maxProfit(prices6);
        System.out.println("Two days: " + result6a + " - " + 
                         (result6a == 1 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("State Machine DP: " + time1a + " ns");
        System.out.println("DP Arrays:        " + time1b + " ns");
        System.out.println("Two States:       " + time1c + " ns");
        System.out.println("Recursive:        " + time1d + " ns");
        System.out.println("Optimized:        " + time1e + " ns");
        
        // Test all approaches produce same results
        System.out.println("\nTest 8: All approaches consistency");
        boolean allConsistent = result1a == result1b && 
                              result1a == result1c && 
                              result1a == result1d &&
                              result1a == result1e;
        System.out.println("All approaches produce same results: " + allConsistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("STATE MACHINE EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nThree States:");
        System.out.println("1. HOLD: Currently holding stock (bought, not sold yet)");
        System.out.println("2. SOLD: Just sold stock (cannot buy next day)");
        System.out.println("3. REST: No stock, can buy (cooldown period or waiting)");
        
        System.out.println("\nState Transitions:");
        System.out.println("HOLD → HOLD: Keep holding the stock");
        System.out.println("HOLD → SOLD: Sell the stock (profit = hold + price)");
        System.out.println("SOLD → REST: Must cooldown after selling");
        System.out.println("REST → REST: Continue waiting");
        System.out.println("REST → HOLD: Buy stock (profit = rest - price)");
        
        System.out.println("\nWhy this works:");
        System.out.println("- The state machine perfectly models the cooldown constraint");
        System.out.println("- Each state tracks maximum profit achievable in that state");
        System.out.println("- Transitions enforce the business rules");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. State Machine DP (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through prices");
        System.out.println("   Space: O(1) - Three variables for states");
        System.out.println("   How it works:");
        System.out.println("     - Track three states: hold, sold, rest");
        System.out.println("     - Update states based on valid transitions");
        System.out.println("     - Final answer = max(sold, rest)");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Clear modeling of problem constraints");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding state transitions");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. DP Arrays:");
        System.out.println("   Time: O(n) - Single pass");
        System.out.println("   Space: O(n) - Three arrays for states");
        System.out.println("   How it works:");
        System.out.println("     - Same logic but using arrays instead of variables");
        System.out.println("     - Easier to debug and visualize");
        System.out.println("   Pros:");
        System.out.println("     - Easy to understand and debug");
        System.out.println("     - Clear state progression");
        System.out.println("   Cons:");
        System.out.println("     - Higher space complexity");
        System.out.println("   Best for: Learning, debugging");
        
        System.out.println("\n3. Recursive with Memoization:");
        System.out.println("   Time: O(n) - Each state computed once");
        System.out.println("   Space: O(n) - Memoization table");
        System.out.println("   How it works:");
        System.out.println("     - Top-down recursive approach");
        System.out.println("     - Memoize computed results");
        System.out.println("     - Natural recursive thinking");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive recursive solution");
        System.out.println("     - Easy to understand transitions");
        System.out.println("   Cons:");
        System.out.println("     - Recursion stack overhead");
        System.out.println("     - Higher constant factors");
        System.out.println("   Best for: When recursive thinking is preferred");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY COOLDOWN MAKES IT DIFFERENT:");
        System.out.println("1. Without cooldown: greedy approach works (buy low, sell high)");
        System.out.println("2. With cooldown: need to consider future opportunities");
        System.out.println("3. The constraint breaks the greedy property");
        System.out.println("4. State machine naturally handles the timing constraint");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with state machine explanation - it's the key insight");
        System.out.println("2. Clearly define the three states and transitions");
        System.out.println("3. Implement the state machine DP with O(1) space");
        System.out.println("4. Discuss time/space complexity");
        System.out.println("5. Handle edge cases (single day, decreasing prices)");
        System.out.println("6. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        // Additional examples with different patterns
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ADDITIONAL TEST PATTERNS:");
        System.out.println("=".repeat(70));
        
        int[][][] testCases = {
            {{1, 2, 4}, 3},           // Simple increasing
            {{4, 2, 1}, 0},           // Strictly decreasing
            {{1, 3, 2, 4}, 3},        // Buy@1, sell@3, buy@2, sell@4
            {{1, 4, 2}, 3},           // Single transaction optimal
            {{2, 1, 4}, 3},           // Buy@1, sell@4
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int[] prices = testCases[i][0];
            int expected = testCases[i][1];
            int result = solution.maxProfit(prices);
            System.out.printf("Test %d: %s -> %d (expected: %d) - %s%n",
                            i + 1, Arrays.toString(prices), result, expected,
                            result == expected ? "PASSED" : "FAILED");
        }
        
        System.out.println("\nAll tests completed!");
    }
}
