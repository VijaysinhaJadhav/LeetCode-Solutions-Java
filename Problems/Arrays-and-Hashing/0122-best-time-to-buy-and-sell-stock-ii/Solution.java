/**
 * 122. Best Time to Buy and Sell Stock II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array prices where prices[i] is the price of a stock on the i-th day,
 * find the maximum profit you can achieve. You may complete as many transactions 
 * as you like (buy one and sell one share multiple times) but you can only hold 
 * at most one share at a time.
 * 
 * Key Insights:
 * 1. Greedy Approach: Sum all positive differences between consecutive days
 * 2. Peak-Valley: Buy at valleys, sell at peaks
 * 3. Dynamic Programming: Track states with state machine
 * 4. The optimal strategy is to capture every price increase
 * 
 * Approach (Greedy):
 * 1. Iterate through prices from day 1 to end
 * 2. If today's price > yesterday's price, add the difference to profit
 * 3. Return total accumulated profit
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Dynamic Programming, Greedy
 */

class Solution {
    /**
     * Approach 1: Greedy One Pass (RECOMMENDED)
     * O(n) time, O(1) space - Simple and optimal
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        
        for (int i = 1; i < prices.length; i++) {
            // If price increased from yesterday, add the gain to profit
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }
        
        return maxProfit;
    }
    
    /**
     * Approach 2: Peak-Valley Approach
     * O(n) time, O(1) space - More intuitive for some
     */
    public int maxProfitPeakValley(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int i = 0;
        int valley = prices[0];
        int peak = prices[0];
        int maxProfit = 0;
        int n = prices.length;
        
        while (i < n - 1) {
            // Find the next valley (local minimum)
            while (i < n - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            valley = prices[i];
            
            // Find the next peak (local maximum)
            while (i < n - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            peak = prices[i];
            
            // Add the profit from this transaction
            maxProfit += peak - valley;
        }
        
        return maxProfit;
    }
    
    /**
     * Approach 3: Dynamic Programming with State Machine
     * O(n) time, O(1) space - Demonstrates DP thinking
     */
    public int maxProfitDP(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int n = prices.length;
        int hold = -prices[0];  // Maximum profit when holding stock
        int cash = 0;           // Maximum profit when not holding stock
        
        for (int i = 1; i < n; i++) {
            // Either continue holding or buy today
            int prevHold = hold;
            hold = Math.max(hold, cash - prices[i]);
            
            // Either continue holding cash or sell today
            cash = Math.max(cash, prevHold + prices[i]);
        }
        
        return cash; // Final state should be not holding any stock
    }
    
    /**
     * Approach 4: Dynamic Programming with Array (More Explicit)
     * O(n) time, O(n) space - Easier to understand but uses more space
     */
    public int maxProfitDPArray(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int n = prices.length;
        int[] hold = new int[n];   // Max profit when holding stock on day i
        int[] cash = new int[n];   // Max profit when not holding stock on day i
        
        // Base cases
        hold[0] = -prices[0];  // Buy on day 0
        cash[0] = 0;           // Do nothing on day 0
        
        for (int i = 1; i < n; i++) {
            // On day i, if we hold stock: either we held from yesterday or bought today
            hold[i] = Math.max(hold[i - 1], cash[i - 1] - prices[i]);
            
            // On day i, if we don't hold stock: either we didn't hold yesterday or sold today
            cash[i] = Math.max(cash[i - 1], hold[i - 1] + prices[i]);
        }
        
        return cash[n - 1]; // Final profit when not holding stock
    }
    
    /**
     * Approach 5: Recursive with Memoization (For Educational Purpose)
     * O(n) time, O(n) space - Demonstrates recursive thinking
     */
    public int maxProfitRecursive(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int n = prices.length;
        Integer[][] memo = new Integer[n][2];
        return dfs(prices, 0, 0, memo); // Start with state 0 (not holding)
    }
    
    private int dfs(int[] prices, int day, int state, Integer[][] memo) {
        if (day >= prices.length) {
            return 0;
        }
        
        if (memo[day][state] != null) {
            return memo[day][state];
        }
        
        // Two choices: do nothing or take action
        int doNothing = dfs(prices, day + 1, state, memo);
        int takeAction;
        
        if (state == 0) {
            // Not holding: can buy
            takeAction = -prices[day] + dfs(prices, day + 1, 1, memo);
        } else {
            // Holding: can sell
            takeAction = prices[day] + dfs(prices, day + 1, 0, memo);
        }
        
        memo[day][state] = Math.max(doNothing, takeAction);
        return memo[day][state];
    }
    
    /**
     * Helper method to visualize the trading strategy
     */
    private void visualizeTrading(int[] prices, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Price Chart: " + java.util.Arrays.toString(prices));
        
        if (prices.length == 0) {
            System.out.println("No trading possible");
            return;
        }
        
        int totalProfit = 0;
        int buyDay = -1;
        
        System.out.println("Trading Strategy:");
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                if (buyDay == -1) {
                    // Found a buying opportunity
                    buyDay = i - 1;
                    System.out.printf("  Buy on day %d at price %d%n", buyDay + 1, prices[buyDay]);
                }
            } else if (prices[i] < prices[i - 1] && buyDay != -1) {
                // Found a selling opportunity
                int sellDay = i - 1;
                int profit = prices[sellDay] - prices[buyDay];
                totalProfit += profit;
                System.out.printf("  Sell on day %d at price %d, Profit: %d%n", 
                                 sellDay + 1, prices[sellDay], profit);
                buyDay = -1;
            }
        }
        
        // Sell on last day if still holding
        if (buyDay != -1) {
            int profit = prices[prices.length - 1] - prices[buyDay];
            totalProfit += profit;
            System.out.printf("  Sell on day %d at price %d, Profit: %d%n", 
                             prices.length, prices[prices.length - 1], profit);
        }
        
        System.out.println("Total Profit: " + totalProfit);
        
        // Compare with greedy approach
        int greedyProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                greedyProfit += prices[i] - prices[i - 1];
            }
        }
        
        System.out.println("Greedy Approach Profit: " + greedyProfit);
        System.out.println("Strategies Match: " + (totalProfit == greedyProfit));
    }
    
    /**
     * Helper method to calculate profit with specific buy/sell points
     */
    private int calculateProfit(int[] prices, int[] buyDays, int[] sellDays) {
        int profit = 0;
        for (int i = 0; i < buyDays.length; i++) {
            profit += prices[sellDays[i]] - prices[buyDays[i]];
        }
        return profit;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Best Time to Buy and Sell Stock II Solution:");
        System.out.println("====================================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        int expected1 = 7;
        
        long startTime = System.nanoTime();
        int result1a = solution.maxProfit(prices1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.maxProfitPeakValley(prices1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.maxProfitDP(prices1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.maxProfitDPArray(prices1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.maxProfitRecursive(prices1);
        long time1e = System.nanoTime() - startTime;
        
        System.out.println("Greedy One Pass: " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Peak-Valley: " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("DP State Machine: " + result1c + " - " + 
                         (result1c == expected1 ? "PASSED" : "FAILED"));
        System.out.println("DP Array: " + result1d + " - " + 
                         (result1d == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Recursive Memo: " + result1e + " - " + 
                         (result1e == expected1 ? "PASSED" : "FAILED"));
        
        // Visualize the trading strategy
        solution.visualizeTrading(prices1, "Standard Example");
        
        // Test case 2: Continuously increasing prices
        System.out.println("\nTest 2: Continuously increasing prices");
        int[] prices2 = {1, 2, 3, 4, 5};
        int expected2 = 4;
        
        int result2a = solution.maxProfit(prices2);
        System.out.println("Increasing prices: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        solution.visualizeTrading(prices2, "Increasing Prices");
        
        // Test case 3: Continuously decreasing prices
        System.out.println("\nTest 3: Continuously decreasing prices");
        int[] prices3 = {7, 6, 4, 3, 1};
        int expected3 = 0;
        
        int result3a = solution.maxProfit(prices3);
        System.out.println("Decreasing prices: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single day
        System.out.println("\nTest 4: Single day");
        int[] prices4 = {5};
        int expected4 = 0;
        
        int result4a = solution.maxProfit(prices4);
        System.out.println("Single day: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Empty array
        System.out.println("\nTest 5: Empty array");
        int[] prices5 = {};
        int expected5 = 0;
        
        int result5a = solution.maxProfit(prices5);
        System.out.println("Empty array: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Volatile prices
        System.out.println("\nTest 6: Volatile prices");
        int[] prices6 = {1, 5, 2, 8, 3, 10};
        int result6a = solution.maxProfit(prices6);
        System.out.println("Volatile prices: " + result6a);
        solution.visualizeTrading(prices6, "Volatile Prices");
        
        // Test case 7: Flat prices
        System.out.println("\nTest 7: Flat prices");
        int[] prices7 = {5, 5, 5, 5, 5};
        int expected7 = 0;
        
        int result7a = solution.maxProfit(prices7);
        System.out.println("Flat prices: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Greedy One Pass: " + time1a + " ns");
        System.out.println("  Peak-Valley: " + time1b + " ns");
        System.out.println("  DP State Machine: " + time1c + " ns");
        System.out.println("  DP Array: " + time1d + " ns");
        System.out.println("  Recursive Memo: " + time1e + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 9: Large input performance");
        int[] largePrices = new int[10000];
        java.util.Random random = new java.util.Random(42);
        for (int i = 0; i < largePrices.length; i++) {
            largePrices[i] = random.nextInt(1000) + 1;
        }
        
        startTime = System.nanoTime();
        int result9a = solution.maxProfit(largePrices);
        long time9a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result9b = solution.maxProfitDP(largePrices);
        long time9b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result9c = solution.maxProfitRecursive(largePrices);
        long time9c = System.nanoTime() - startTime;
        
        System.out.println("Large input (10,000 elements):");
        System.out.println("  Greedy One Pass: " + time9a + " ns, Profit: " + result9a);
        System.out.println("  DP State Machine: " + time9b + " ns, Profit: " + result9b);
        System.out.println("  Recursive Memo: " + time9c + " ns, Profit: " + result9c);
        System.out.println("  All approaches consistent: " + 
                         (result9a == result9b && result9a == result9c));
        
        // Mathematical explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nWhy the Greedy Approach Works:");
        System.out.println("For any price sequence, the total profit can be expressed as:");
        System.out.println("Profit = Σ (peak_i - valley_i) for all i");
        System.out.println("But this is equivalent to:");
        System.out.println("Profit = Σ max(0, prices[i] - prices[i-1]) for all i");
        System.out.println("Because every upward movement contributes to profit.");
        
        System.out.println("\nExample: [1, 5, 2, 8, 3, 10]");
        System.out.println("Price differences: +4, -3, +6, -5, +7");
        System.out.println("Positive differences: 4 + 6 + 7 = 17");
        System.out.println("This equals buying at 1(sell at 5) + buy at 2(sell at 8) + buy at 3(sell at 10)");
        System.out.println("Profit: (5-1) + (8-2) + (10-3) = 4 + 6 + 7 = 17");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Greedy One Pass (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Iterate through prices from day 1 to end");
        System.out.println("     - Add price increase to profit if today > yesterday");
        System.out.println("     - Return accumulated profit");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time and O(1) space");
        System.out.println("     - Simple, elegant, and intuitive");
        System.out.println("     - Easy to implement and understand");
        System.out.println("   Cons:");
        System.out.println("     - May seem too simple (interviewers might probe deeper)");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Peak-Valley Approach:");
        System.out.println("   Time: O(n) - Single pass finding peaks and valleys");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Find local minima (valleys) to buy");
        System.out.println("     - Find local maxima (peaks) to sell");
        System.out.println("     - Accumulate profit from each valley-peak pair");
        System.out.println("   Pros:");
        System.out.println("     - More intuitive trading strategy");
        System.out.println("     - Explicit buy/sell points");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex implementation");
        System.out.println("     - Same complexity as greedy approach");
        System.out.println("   Best for: When explicit trading strategy is needed");
        
        System.out.println("\n3. Dynamic Programming (State Machine):");
        System.out.println("   Time: O(n) - Single pass with state transitions");
        System.out.println("   Space: O(1) - Only two variables for states");
        System.out.println("   How it works:");
        System.out.println("     - Track two states: hold stock or not hold stock");
        System.out.println("     - Update states based on buy/sell decisions");
        System.out.println("     - Final profit is cash state (not holding)");
        System.out.println("   Pros:");
        System.out.println("     - Generalizes to more complex stock problems");
        System.out.println("     - Demonstrates DP thinking");
        System.out.println("   Cons:");
        System.out.println("     - More complex than greedy approach");
        System.out.println("     - Overkill for this problem");
        System.out.println("   Best for: Learning DP, preparing for harder stock problems");
        
        System.out.println("\n4. Dynamic Programming (Array):");
        System.out.println("   Time: O(n) - Single pass with arrays");
        System.out.println("   Space: O(n) - Two arrays for DP states");
        System.out.println("   How it works:");
        System.out.println("     - Maintain arrays for hold and cash states");
        System.out.println("     - Update states using DP recurrence");
        System.out.println("     - Return final cash state");
        System.out.println("   Pros:");
        System.out.println("     - Very explicit state transitions");
        System.out.println("     - Easy to understand and debug");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("     - Not space optimal");
        System.out.println("   Best for: Learning DP concepts, debugging");
        
        System.out.println("\n5. Recursive with Memoization:");
        System.out.println("   Time: O(n) - With memoization");
        System.out.println("   Space: O(n) - Recursion stack and memo table");
        System.out.println("   How it works:");
        System.out.println("     - Recursively explore buy/sell decisions");
        System.out.println("     - Use memoization to avoid recomputation");
        System.out.println("     - Return maximum profit from current state");
        System.out.println("   Pros:");
        System.out.println("     - Natural recursive thinking");
        System.out.println("     - Easy to understand conceptually");
        System.out.println("   Cons:");
        System.out.println("     - Recursion overhead");
        System.out.println("     - Not optimal for this problem");
        System.out.println("   Best for: Learning recursive approaches, small inputs");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY GREEDY WORKS - MATHEMATICAL PROOF:");
        System.out.println("Let the optimal solution have transactions: (b1,s1), (b2,s2), ..., (bk,sk)");
        System.out.println("Total profit = (s1-b1) + (s2-b2) + ... + (sk-bk)");
        System.out.println("This can be rewritten as:");
        System.out.println("(s1-b1) + (s2-b2) + ... + (sk-bk) = (s1-b2) + (b2-b1) + (s2-b3) + ...");
        System.out.println("But notice this equals the sum of all positive daily differences!");
        System.out.println("Therefore, capturing every upward movement is optimal.");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Greedy approach - it's the expected optimal solution");
        System.out.println("2. Explain why it works (capturing all upward movements)");
        System.out.println("3. Mention Peak-Valley as an alternative intuitive approach");
        System.out.println("4. Discuss DP for harder variants (cooldown, transaction fees)");
        System.out.println("5. Handle edge cases: empty array, single day, decreasing prices");
        System.out.println("6. Practice drawing price charts to visualize the strategy");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
