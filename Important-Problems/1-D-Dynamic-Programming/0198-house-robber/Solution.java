
## Solution.java

```java
/**
 * 198. House Robber
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are a professional robber planning to rob houses along a street. 
 * Each house has a certain amount of money stashed. 
 * The only constraint is that adjacent houses have security systems connected 
 * and will contact police if two adjacent houses are broken into.
 * 
 * Given an integer array nums representing the amount of money of each house, 
 * return the maximum amount of money you can rob tonight without alerting the police.
 * 
 * Key Insights:
 * 1. Cannot rob adjacent houses
 * 2. At each house, we have two choices: rob or skip
 * 3. If we rob current house, we cannot rob previous house
 * 4. If we skip current house, we can take maximum from previous decision
 * 
 * Recurrence Relation:
 * dp[i] = max(dp[i-1], nums[i] + dp[i-2])
 * Where dp[i] represents maximum loot up to house i
 * 
 * Approach (Dynamic Programming with Space Optimization):
 * 1. Use two variables to store dp[i-1] and dp[i-2]
 * 2. For each house, compute current maximum
 * 3. Update variables for next iteration
 * 4. Return final maximum
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Dynamic Programming
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Dynamic Programming with Space Optimization - RECOMMENDED
     * O(n) time, O(1) space
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // rob1 represents dp[i-2], rob2 represents dp[i-1]
        int rob1 = 0, rob2 = 0;
        
        for (int num : nums) {
            // At each house, we have two choices:
            // 1. Rob current house: rob1 + num (since we skip previous house)
            // 2. Skip current house: rob2 (take previous maximum)
            int current = Math.max(rob1 + num, rob2);
            
            // Update for next iteration
            rob1 = rob2;
            rob2 = current;
        }
        
        return rob2;
    }
    
    /**
     * Approach 2: DP Array (More Readable)
     * O(n) time, O(n) space
     */
    public int robDP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i-1], nums[i] + dp[i-2]);
        }
        
        return dp[n-1];
    }
    
    /**
     * Approach 3: Recursive with Memoization
     * O(n) time, O(n) space
     */
    public int robRecursive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int[] memo = new int[nums.length];
        Arrays.fill(memo, -1);
        return robHelper(nums, 0, memo);
    }
    
    private int robHelper(int[] nums, int index, int[] memo) {
        if (index >= nums.length) {
            return 0;
        }
        
        if (memo[index] != -1) {
            return memo[index];
        }
        
        // Two choices:
        // 1. Rob current house and skip next house
        int robCurrent = nums[index] + robHelper(nums, index + 2, memo);
        // 2. Skip current house and consider next house
        int skipCurrent = robHelper(nums, index + 1, memo);
        
        memo[index] = Math.max(robCurrent, skipCurrent);
        return memo[index];
    }
    
    /**
     * Approach 4: Iterative with Two Variables (Alternative)
     * O(n) time, O(1) space
     */
    public int robIterative(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int prevMax = 0;  // Maximum up to i-2
        int currMax = 0;  // Maximum up to i-1
        
        for (int num : nums) {
            int temp = currMax;
            currMax = Math.max(prevMax + num, currMax);
            prevMax = temp;
        }
        
        return currMax;
    }
    
    /**
     * Approach 5: DP with State Variables
     * O(n) time, O(1) space
     */
    public int robState(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int robbed = 0;    // Maximum if we robbed previous house
        int notRobbed = 0; // Maximum if we didn't rob previous house
        
        for (int num : nums) {
            // If we rob current house, we must not have robbed previous house
            int robCurrent = notRobbed + num;
            // If we skip current house, we take maximum of previous decisions
            int skipCurrent = Math.max(robbed, notRobbed);
            
            // Update for next iteration
            robbed = robCurrent;
            notRobbed = skipCurrent;
        }
        
        return Math.max(robbed, notRobbed);
    }
    
    /**
     * Approach 6: Using Pair Class for Clarity
     * O(n) time, O(1) space
     */
    public int robPair(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        Pair pair = new Pair(0, 0); // (rob, notRob)
        
        for (int num : nums) {
            int robCurrent = pair.notRob + num;
            int notRobCurrent = Math.max(pair.rob, pair.notRob);
            
            pair = new Pair(robCurrent, notRobCurrent);
        }
        
        return Math.max(pair.rob, pair.notRob);
    }
    
    private static class Pair {
        int rob;
        int notRob;
        
        Pair(int rob, int notRob) {
            this.rob = rob;
            this.notRob = notRob;
        }
    }
    
    /**
     * Helper method to visualize the DP process
     */
    public void visualizeRobbery(int[] nums) {
        System.out.println("\nHouse Robber Visualization:");
        System.out.println("Houses: " + Arrays.toString(nums));
        
        if (nums.length == 0) {
            System.out.println("No houses to rob. Maximum loot: 0");
            return;
        }
        
        int n = nums.length;
        int[] dp = new int[n];
        boolean[] robbed = new boolean[n]; // Track which houses were robbed
        
        // Initialize DP array
        if (n >= 1) {
            dp[0] = nums[0];
            robbed[0] = true;
        }
        
        System.out.println("\nInitial state:");
        System.out.println("House 0: rob = " + nums[0] + " (rob)");
        
        if (n >= 2) {
            if (nums[0] >= nums[1]) {
                dp[1] = nums[0];
                robbed[1] = false;
                System.out.println("House 1: max(" + nums[0] + ", " + nums[1] + ") = " + nums[0] + " (skip)");
            } else {
                dp[1] = nums[1];
                robbed[1] = true;
                System.out.println("House 1: max(" + nums[0] + ", " + nums[1] + ") = " + nums[1] + " (rob)");
            }
        }
        
        // Fill DP array and track decisions
        for (int i = 2; i < n; i++) {
            int option1 = dp[i-1];          // Skip current house
            int option2 = nums[i] + dp[i-2]; // Rob current house
            
            if (option1 >= option2) {
                dp[i] = option1;
                robbed[i] = false;
                System.out.println("House " + i + ": max(" + option1 + ", " + option2 + ") = " + option1 + " (skip)");
            } else {
                dp[i] = option2;
                robbed[i] = true;
                System.out.println("House " + i + ": max(" + option1 + ", " + option2 + ") = " + option2 + " (rob)");
            }
        }
        
        System.out.println("\nMaximum loot: " + dp[n-1]);
        
        // Show which houses to rob
        showOptimalHouses(nums, robbed, dp);
    }
    
    private void showOptimalHouses(int[] nums, boolean[] robbed, int[] dp) {
        System.out.println("\nOptimal Robbery Plan:");
        List<Integer> housesToRob = new ArrayList<>();
        
        // Reconstruct the solution
        int i = nums.length - 1;
        while (i >= 0) {
            if (robbed[i]) {
                housesToRob.add(0, i); // Add to beginning
                i -= 2; // Skip adjacent house
            } else {
                i -= 1; // Move to previous house
            }
        }
        
        // Calculate total from chosen houses
        int total = 0;
        System.out.print("Rob houses: ");
        for (int j = 0; j < housesToRob.size(); j++) {
            int house = housesToRob.get(j);
            total += nums[house];
            System.out.print(house);
            if (j < housesToRob.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        System.out.println("Total loot: " + total);
        
        // Verify the solution
        if (total != dp[nums.length - 1]) {
            System.out.println("WARNING: Reconstruction doesn't match DP result!");
        }
        
        // Check adjacency constraint
        for (int j = 1; j < housesToRob.size(); j++) {
            if (housesToRob.get(j) - housesToRob.get(j-1) == 1) {
                System.out.println("ERROR: Adjacent houses robbed!");
                break;
            }
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing House Robber Solution:");
        System.out.println("===============================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: [1,2,3,1]");
        int[] nums1 = {1, 2, 3, 1};
        int expected1 = 4;
        
        long startTime = System.nanoTime();
        int result1a = solution.rob(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.robDP(nums1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.robRecursive(nums1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.robIterative(nums1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.robState(nums1);
        long time1e = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1f = solution.robPair(nums1);
        long time1f = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        boolean test1f = result1f == expected1;
        
        System.out.println("Space Optimized: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DP Array:        " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Recursive:       " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Iterative:       " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("State:           " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        System.out.println("Pair:            " + result1f + " - " + (test1f ? "PASSED" : "FAILED"));
        
        // Visualize the robbery plan
        solution.visualizeRobbery(nums1);
        
        // Test case 2: Single house
        System.out.println("\nTest 2: Single house [5]");
        int[] nums2 = {5};
        int result2a = solution.rob(nums2);
        System.out.println("Single house: " + result2a + " - " + 
                         (result2a == 5 ? "PASSED" : "FAILED"));
        
        // Test case 3: Two houses
        System.out.println("\nTest 3: Two houses [2,7]");
        int[] nums3 = {2, 7};
        int result3a = solution.rob(nums3);
        System.out.println("Two houses: " + result3a + " - " + 
                         (result3a == 7 ? "PASSED" : "FAILED"));
        
        // Test case 4: Three houses
        System.out.println("\nTest 4: Three houses [2,7,9]");
        int[] nums4 = {2, 7, 9};
        int result4a = solution.rob(nums4);
        System.out.println("Three houses: " + result4a + " - " + 
                         (result4a == 11 ? "PASSED" : "FAILED")); // 2+9 = 11
        
        // Test case 5: All houses same value
        System.out.println("\nTest 5: All same [3,3,3,3]");
        int[] nums5 = {3, 3, 3, 3};
        int result5a = solution.rob(nums5);
        System.out.println("All same: " + result5a + " - " + 
                         (result5a == 6 ? "PASSED" : "FAILED")); // 3+3 = 6
        
        // Test case 6: Complex case
        System.out.println("\nTest 6: Complex case [2,7,9,3,1]");
        int[] nums6 = {2, 7, 9, 3, 1};
        int expected6 = 12;
        int result6a = solution.rob(nums6);
        System.out.println("Complex: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Empty array
        System.out.println("\nTest 7: Empty array");
        int[] nums7 = {};
        int result7a = solution.rob(nums7);
        System.out.println("Empty: " + result7a + " - " + 
                         (result7a == 0 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison");
        System.out.println("Space Optimized: " + time1a + " ns");
        System.out.println("DP Array:        " + time1b + " ns");
        System.out.println("Recursive:       " + time1c + " ns");
        System.out.println("Iterative:       " + time1d + " ns");
        System.out.println("State:           " + time1e + " ns");
        System.out.println("Pair:            " + time1f + " ns");
        
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
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("dp[i] = max(dp[i-1], nums[i] + dp[i-2])");
        System.out.println("Where:");
        System.out.println("  dp[i-1] represents skipping current house");
        System.out.println("  nums[i] + dp[i-2] represents robbing current house");
        
        System.out.println("\nBase Cases:");
        System.out.println("dp[0] = nums[0]  (only one house)");
        System.out.println("dp[1] = max(nums[0], nums[1])  (choose better of two houses)");
        
        System.out.println("\nWhy it works:");
        System.out.println("At each house, we make the optimal decision based on");
        System.out.println("previous results, ensuring we never rob adjacent houses.");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Space Optimized DP (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through houses");
        System.out.println("   Space: O(1) - Only two variables");
        System.out.println("   How it works:");
        System.out.println("     - Use rob1 and rob2 to store dp[i-2] and dp[i-1]");
        System.out.println("     - For each house, compute current maximum");
        System.out.println("     - Update variables for next iteration");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Simple and elegant");
        System.out.println("     - Easy to implement");
        System.out.println("   Cons:");
        System.out.println("     - Slightly less intuitive than DP array");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. DP Array:");
        System.out.println("   Time: O(n) - Single pass");
        System.out.println("   Space: O(n) - Array to store all results");
        System.out.println("   How it works:");
        System.out.println("     - Store maximum loot for each house in array");
        System.out.println("     - Fill array using recurrence relation");
        System.out.println("   Pros:");
        System.out.println("     - Easy to understand and debug");
        System.out.println("     - Can reconstruct solution easily");
        System.out.println("   Cons:");
        System.out.println("     - Higher space complexity");
        System.out.println("   Best for: Learning, when solution reconstruction needed");
        
        System.out.println("\n3. Recursive with Memoization:");
        System.out.println("   Time: O(n) - Each house computed once");
        System.out.println("   Space: O(n) - Memoization table + recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Top-down recursive approach");
        System.out.println("     - Memoize computed results");
        System.out.println("     - Natural recursive thinking");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive recursive definition");
        System.out.println("     - Easy to understand the choices");
        System.out.println("   Cons:");
        System.out.println("     - Recursion stack overhead");
        System.out.println("     - Higher constant factors");
        System.out.println("   Best for: When recursive thinking is preferred");
        
        System.out.println("\n4. State Machine Approach:");
        System.out.println("   Time: O(n) - Single pass");
        System.out.println("   Space: O(1) - Two state variables");
        System.out.println("   How it works:");
        System.out.println("     - Track robbed and notRobbed states");
        System.out.println("     - Update states based on current decision");
        System.out.println("   Pros:");
        System.out.println("     - Clear state representation");
        System.out.println("     - Easy to understand transitions");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: When state transitions are important");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PROBLEM VARIATIONS:");
        System.out.println("1. House Robber II: Circular arrangement");
        System.out.println("2. House Robber III: Binary tree arrangement");
        System.out.println("3. Non-adjacent maximum sum: General version");
        System.out.println("4. With additional constraints: Time windows, weights");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with space optimized DP - it's the expected solution");
        System.out.println("2. Explain the recurrence relation clearly");
        System.out.println("3. Handle edge cases (empty, single, two houses)");
        System.out.println("4. Discuss time/space complexity");
        System.out.println("5. Mention solution reconstruction if asked");
        System.out.println("6. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        // Additional test patterns
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ADDITIONAL TEST PATTERNS:");
        System.out.println("=".repeat(70));
        
        int[][][] testCases = {
            {{1, 3, 1}, 3},           // Skip middle
            {{4, 1, 2, 7, 5, 3, 1}, 14}, // Complex pattern
            {{6, 7, 1, 3, 8, 2, 4}, 19}, // Alternating pattern
            {{2, 10, 14, 8, 1}, 18},   // Skip expensive adjacent
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i][0];
            int expected = testCases[i][1];
            int result = solution.rob(nums);
            System.out.printf("Test %d: %s -> %d (expected: %d) - %s%n",
                            i + 1, Arrays.toString(nums), result, expected,
                            result == expected ? "PASSED" : "FAILED");
        }
        
        System.out.println("\nAll tests completed!");
    }
}
