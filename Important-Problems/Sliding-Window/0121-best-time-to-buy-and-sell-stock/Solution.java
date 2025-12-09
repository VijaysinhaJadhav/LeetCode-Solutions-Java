/**
 * 121. Best Time to Buy and Sell Stock
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * You are given an array prices where prices[i] is the price of a given stock on the i-th day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing 
 * a different day in the future to sell that stock.
 * Return the maximum profit you can achieve from this transaction. 
 * If you cannot achieve any profit, return 0.
 * 
 * Key Insights:
 * 1. We need to find the maximum difference between two prices where buying price comes before selling price
 * 2. Track the minimum price seen so far and calculate potential profit for each day
 * 3. Update maximum profit whenever we find a better profit opportunity
 * 4. If no profit is possible, return 0
 * 
 * Approach (One Pass):
 * 1. Initialize minPrice to first price and maxProfit to 0
 * 2. Iterate through prices array
 * 3. For each price, update minPrice if current price is lower
 * 4. Calculate potential profit as current price - minPrice
 * 5. Update maxProfit if potential profit is greater
 * 6. Return maxProfit
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Dynamic Programming, Sliding Window
 */

class Solution {
    /**
     * Approach 1: One Pass with Minimum Tracking - RECOMMENDED
     * O(n) time, O(1) space - Optimal solution
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        
        for (int price : prices) {
            // Update minimum price if current price is lower
            if (price < minPrice) {
                minPrice = price;
            }
            // Calculate profit and update maxProfit if better
            else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }
        
        return maxProfit;
    }
    
    /**
     * Approach 2: Kadane's Algorithm Style
     * O(n) time, O(1) space - Alternative approach using maximum subarray concept
     */
    public int maxProfitKadane(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int maxProfit = 0;
        int currentProfit = 0;
        
        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - prices[i - 1];
            currentProfit = Math.max(0, currentProfit + profit);
            maxProfit = Math.max(maxProfit, currentProfit);
        }
        
        return maxProfit;
    }
    
    /**
     * Approach 3: Dynamic Programming (Two Variables)
     * O(n) time, O(1) space - DP state tracking
     */
    public int maxProfitDP(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int minPrice = prices[0];
        int maxProfit = 0;
        
        for (int i = 1; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
        }
        
        return maxProfit;
    }
    
    /**
     * Approach 4: Brute Force (For Comparison)
     * O(n^2) time, O(1) space - Not recommended for large inputs
     */
    public int maxProfitBruteForce(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int maxProfit = 0;
        
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                int profit = prices[j] - prices[i];
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }
        
        return maxProfit;
    }
    
    /**
     * Approach 5: Two Pointers (Sliding Window)
     * O(n) time, O(1) space - Alternative two-pointer approach
     */
    public int maxProfitTwoPointers(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int left = 0; // buy pointer
        int right = 1; // sell pointer
        int maxProfit = 0;
        
        while (right < prices.length) {
            // If profitable, calculate profit and update max
            if (prices[left] < prices[right]) {
                int profit = prices[right] - prices[left];
                maxProfit = Math.max(maxProfit, profit);
            } else {
                // Move left pointer to current right (found new minimum)
                left = right;
            }
            right++;
        }
        
        return maxProfit;
    }
    
    /**
     * Helper method to visualize the one pass algorithm
     */
    private void visualizeOnePass(int[] prices) {
        System.out.println("\nOne Pass Algorithm Visualization:");
        System.out.println("Input: " + java.util.Arrays.toString(prices));
        
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        
        System.out.println("\nDay | Price | Min Price | Potential Profit | Max Profit | Action");
        System.out.println("----|-------|-----------|------------------|------------|--------");
        
        for (int i = 0; i < prices.length; i++) {
            int price = prices[i];
            String action = "";
            
            if (price < minPrice) {
                minPrice = price;
                action = "Update minPrice";
            } else {
                int potentialProfit = price - minPrice;
                if (potentialProfit > maxProfit) {
                    maxProfit = potentialProfit;
                    action = "Update maxProfit";
                } else {
                    action = "No update";
                }
            }
            
            System.out.printf("%3d | %5d | %9d | %16d | %10d | %s%n",
                            i + 1, price, minPrice, price - minPrice, maxProfit, action);
        }
        
        System.out.println("\nFinal Result: Maximum Profit = " + maxProfit);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Best Time to Buy and Sell Stock Solution:");
        System.out.println("=================================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        int expected1 = 5;
        
        long startTime = System.nanoTime();
        int result1a = solution.maxProfit(prices1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.maxProfitKadane(prices1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.maxProfitDP(prices1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.maxProfitBruteForce(prices1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.maxProfitTwoPointers(prices1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("One Pass: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Kadane: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("DP: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Brute Force: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Two Pointers: " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the one pass algorithm
        solution.visualizeOnePass(prices1);
        
        // Test case 2: Decreasing prices (no profit)
        System.out.println("\nTest 2: Decreasing prices");
        int[] prices2 = {7, 6, 4, 3, 1};
        int expected2 = 0;
        
        int result2a = solution.maxProfit(prices2);
        System.out.println("Decreasing prices: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single price
        System.out.println("\nTest 3: Single price");
        int[] prices3 = {5};
        int expected3 = 0;
        
        int result3a = solution.maxProfit(prices3);
        System.out.println("Single price: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Two prices with profit
        System.out.println("\nTest 4: Two prices with profit");
        int[] prices4 = {1, 2};
        int expected4 = 1;
        
        int result4a = solution.maxProfit(prices4);
        System.out.println("Two prices with profit: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Two prices without profit
        System.out.println("\nTest 5: Two prices without profit");
        int[] prices5 = {2, 1};
        int expected5 = 0;
        
        int result5a = solution.maxProfit(prices5);
        System.out.println("Two prices without profit: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: All same prices
        System.out.println("\nTest 6: All same prices");
        int[] prices6 = {3, 3, 3, 3, 3};
        int expected6 = 0;
        
        int result6a = solution.maxProfit(prices6);
        System.out.println("All same prices: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large profit at end
        System.out.println("\nTest 7: Large profit at end");
        int[] prices7 = {3, 2, 1, 1, 10};
        int expected7 = 9;
        
        int result7a = solution.maxProfit(prices7);
        System.out.println("Large profit at end: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Multiple peaks and valleys
        System.out.println("\nTest 8: Multiple peaks and valleys");
        int[] prices8 = {2, 4, 1, 7, 5, 0, 6, 3, 8};
        int expected8 = 7; // Buy at 0, sell at 8
        
        int result8a = solution.maxProfit(prices8);
        System.out.println("Multiple peaks and valleys: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  One Pass: " + time1a + " ns");
        System.out.println("  Kadane: " + time1b + " ns");
        System.out.println("  DP: " + time1c + " ns");
        System.out.println("  Brute Force: " + time1d + " ns");
        System.out.println("  Two Pointers: " + time1e + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[] largePrices = new int[10000];
        java.util.Random random = new java.util.Random(42);
        for (int i = 0; i < largePrices.length; i++) {
            largePrices[i] = random.nextInt(1000) + 1; // Prices between 1-1000
        }
        
        startTime = System.nanoTime();
        int result10a = solution.maxProfit(largePrices);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10b = solution.maxProfitKadane(largePrices);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10c = solution.maxProfitBruteForce(largePrices);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (10,000 elements):");
        System.out.println("  One Pass: " + time10a + " ns, Result: " + result10a);
        System.out.println("  Kadane: " + time10b + " ns, Result: " + result10b);
        System.out.println("  Brute Force: " + time10c + " ns, Result: " + result10c);
        
        // Verify all approaches produce the same result
        boolean allEqual = result10a == result10b && result10a == result10c;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ONE PASS ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("For each day, the maximum profit we can get by selling on that day");
        System.out.println("is the current price minus the minimum price seen so far.");
        System.out.println("We track the minimum price and update maximum profit accordingly.");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. We ensure we always buy before selling (minPrice is always from earlier days)");
        System.out.println("2. By tracking minPrice, we always have the best buying opportunity");
        System.out.println("3. We consider every day as a potential selling day");
        System.out.println("4. The algorithm finds the global maximum profit in one pass");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. One Pass with Minimum Tracking (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Track minimum price seen so far");
        System.out.println("     - For each price, calculate potential profit");
        System.out.println("     - Update maximum profit if current profit is better");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time and O(1) space");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to implement and understand");
        System.out.println("   Cons:");
        System.out.println("     - None for this problem");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Kadane's Algorithm Style:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Calculate daily price differences");
        System.out.println("     - Use maximum subarray sum approach");
        System.out.println("     - Reset to 0 when profit becomes negative");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates understanding of Kadane's algorithm");
        System.out.println("     - Useful for related problems with transaction fees");
        System.out.println("   Cons:");
        System.out.println("     - Less intuitive than one pass approach");
        System.out.println("     - More complex to understand");
        System.out.println("   Best for: Learning algorithmic patterns");
        
        System.out.println("\n3. Dynamic Programming (Two Variables):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Maintain state for minimum price and maximum profit");
        System.out.println("     - Update both states at each step");
        System.out.println("   Pros:");
        System.out.println("     - Clear DP formulation");
        System.out.println("     - Easy to extend to more complex problems");
        System.out.println("   Cons:");
        System.out.println("     - Essentially same as one pass approach");
        System.out.println("     - Overkill for this simple problem");
        System.out.println("   Best for: Learning DP concepts for stock problems");
        
        System.out.println("\n4. Brute Force:");
        System.out.println("   Time: O(n^2) - Check all pairs");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Check every possible buy-sell pair");
        System.out.println("     - Track maximum profit found");
        System.out.println("   Pros:");
        System.out.println("     - Simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large inputs (n > 10,000)");
        System.out.println("     - Doesn't meet O(n) requirement");
        System.out.println("   Best for: Small inputs, educational purposes");
        
        System.out.println("\n5. Two Pointers (Sliding Window):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Use left pointer for buying, right pointer for selling");
        System.out.println("     - Move left pointer when finding new minimum");
        System.out.println("     - Move right pointer to explore all selling opportunities");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates sliding window technique");
        System.out.println("     - Intuitive two-pointer approach");
        System.out.println("   Cons:");
        System.out.println("     - More complex than simple one pass");
        System.out.println("     - Essentially same algorithm different implementation");
        System.out.println("   Best for: Learning two-pointer techniques");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. The problem reduces to finding max(prices[j] - prices[i]) where j > i");
        System.out.println("2. At most one transaction is allowed");
        System.out.println("3. If prices are strictly decreasing, maximum profit is 0");
        System.out.println("4. The optimal solution always buys at a global minimum");
        System.out.println("5. The selling day must be after the buying day");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with One Pass approach - it's the expected optimal solution");
        System.out.println("2. Explain the intuition clearly: track min price, calculate max profit");
        System.out.println("3. Mention time and space complexity (O(n), O(1))");
        System.out.println("4. Handle edge cases: empty array, single element, no profit scenarios");
        System.out.println("5. Discuss alternative approaches if time permits");
        System.out.println("6. Relate to real-world stock trading scenarios");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
