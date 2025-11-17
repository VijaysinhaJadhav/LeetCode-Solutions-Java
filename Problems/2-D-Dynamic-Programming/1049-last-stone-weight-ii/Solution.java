
## Solution.java

```java
/**
 * 1049. Last Stone Weight II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are given an array of integers stones where stones[i] is the weight of the ith stone.
 * We play a game where we smash stones together. Return the smallest possible weight of the left stone.
 * 
 * Key Insights:
 * 1. The problem reduces to partitioning stones into two groups with minimum difference
 * 2. When stones from different groups are smashed, the result is the difference
 * 3. The final stone weight = |sum(group1) - sum(group2)|
 * 4. We want to minimize this difference
 * 5. This is equivalent to finding a subset with sum close to total_sum/2
 * 
 * Mathematical Insight:
 * Let S = total sum of stones
 * Let P = sum of one partition
 * Then the difference = |P - (S - P)| = |2P - S|
 * We want to minimize |2P - S|, which means we want P as close to S/2 as possible
 * 
 * Approach (0/1 Knapsack DP):
 * 1. Calculate total sum of stones
 * 2. Find the maximum subset sum ≤ total_sum/2 using knapsack DP
 * 3. Answer = total_sum - 2 * max_subset_sum
 * 
 * Time Complexity: O(n × S) where S is total sum
 * Space Complexity: O(S) for DP array
 * 
 * Tags: Array, Dynamic Programming, 0/1 Knapsack
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Dynamic Programming (0/1 Knapsack) - RECOMMENDED
     * O(n × S) time, O(S) space
     */
    public int lastStoneWeightII(int[] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }
        
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }
        
        int target = totalSum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true; // Base case: empty subset has sum 0
        
        // 0/1 Knapsack: for each stone, update what sums we can achieve
        for (int stone : stones) {
            // Traverse backwards to avoid reusing the same stone
            for (int j = target; j >= stone; j--) {
                if (dp[j - stone]) {
                    dp[j] = true;
                }
            }
        }
        
        // Find the maximum achievable sum ≤ target
        int maxSubsetSum = 0;
        for (int j = target; j >= 0; j--) {
            if (dp[j]) {
                maxSubsetSum = j;
                break;
            }
        }
        
        // The minimum possible difference = totalSum - 2 * maxSubsetSum
        return totalSum - 2 * maxSubsetSum;
    }
    
    /**
     * Approach 2: 2D Dynamic Programming (More Intuitive)
     * O(n × S) time, O(n × S) space
     */
    public int lastStoneWeightII2D(int[] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }
        
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }
        
        int target = totalSum / 2;
        int n = stones.length;
        boolean[][] dp = new boolean[n + 1][target + 1];
        
        // Base case: empty subset can achieve sum 0
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        
        // Fill DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                if (j < stones[i - 1]) {
                    // Cannot include current stone
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // Can either include or exclude current stone
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - stones[i - 1]];
                }
            }
        }
        
        // Find the maximum achievable sum ≤ target
        int maxSubsetSum = 0;
        for (int j = target; j >= 0; j--) {
            if (dp[n][j]) {
                maxSubsetSum = j;
                break;
            }
        }
        
        return totalSum - 2 * maxSubsetSum;
    }
    
    /**
     * Approach 3: DFS with Memoization (Top-down DP)
     * O(n × S) time, O(n × S) space
     */
    public int lastStoneWeightIIDFS(int[] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }
        
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }
        
        int target = totalSum / 2;
        Boolean[][] memo = new Boolean[stones.length][target + 1];
        
        // Find maximum subset sum ≤ target
        for (int j = target; j >= 0; j--) {
            if (dfs(stones, 0, j, memo)) {
                return totalSum - 2 * j;
            }
        }
        
        return totalSum; // Should never reach here
    }
    
    private boolean dfs(int[] stones, int index, int target, Boolean[][] memo) {
        if (target == 0) {
            return true;
        }
        if (index >= stones.length || target < 0) {
            return false;
        }
        if (memo[index][target] != null) {
            return memo[index][target];
        }
        
        // Include current stone or skip it
        boolean include = dfs(stones, index + 1, target - stones[index], memo);
        boolean skip = dfs(stones, index + 1, target, memo);
        
        memo[index][target] = include || skip;
        return memo[index][target];
    }
    
    /**
     * Approach 4: Bit Manipulation DP
     * O(n × S) time, O(S) space with bit operations
     */
    public int lastStoneWeightIIBit(int[] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }
        
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }
        
        int target = totalSum / 2;
        
        // Use bitset to represent achievable sums
        BitSet dp = new BitSet(target + 1);
        dp.set(0); // sum 0 is achievable
        
        for (int stone : stones) {
            // Create a copy to avoid modifying while iterating
            BitSet newDp = (BitSet) dp.clone();
            for (int j = dp.nextSetBit(0); j >= 0; j = dp.nextSetBit(j + 1)) {
                int newSum = j + stone;
                if (newSum <= target) {
                    newDp.set(newSum);
                }
            }
            dp = newDp;
        }
        
        // Find maximum achievable sum
        int maxSubsetSum = dp.previousSetBit(target);
        return totalSum - 2 * maxSubsetSum;
    }
    
    /**
     * Approach 5: Optimization with Early Stopping
     * O(n × S) time, O(S) space with optimizations
     */
    public int lastStoneWeightIIOpt(int[] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }
        
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }
        
        int target = totalSum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        
        int maxAchievable = 0;
        
        for (int stone : stones) {
            // Early stopping if we've already reached target
            if (maxAchievable == target) break;
            
            for (int j = target; j >= stone; j--) {
                if (dp[j - stone] && !dp[j]) {
                    dp[j] = true;
                    maxAchievable = Math.max(maxAchievable, j);
                    if (maxAchievable == target) break;
                }
            }
        }
        
        return totalSum - 2 * maxAchievable;
    }
    
    /**
     * Approach 6: Meet in the Middle (For larger constraints)
     * O(n × 2^(n/2)) time, O(2^(n/2)) space
     */
    public int lastStoneWeightIIMeetInMiddle(int[] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }
        
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }
        
        int target = totalSum / 2;
        int n = stones.length;
        
        // Split stones into two halves
        int leftSize = n / 2;
        int rightSize = n - leftSize;
        
        // Generate all subset sums for left half
        Set<Integer> leftSums = new HashSet<>();
        generateSubsetSums(stones, 0, leftSize, 0, leftSums);
        
        // Generate all subset sums for right half
        Set<Integer> rightSums = new HashSet<>();
        generateSubsetSums(stones, leftSize, n, 0, rightSums);
        
        // Find the combination that gives sum closest to target
        int maxSubsetSum = 0;
        for (int leftSum : leftSums) {
            int needed = target - leftSum;
            // Find the largest rightSum ≤ needed
            Integer floor = null;
            for (int rightSum : rightSums) {
                if (rightSum <= needed) {
                    if (floor == null || rightSum > floor) {
                        floor = rightSum;
                    }
                }
            }
            if (floor != null) {
                maxSubsetSum = Math.max(maxSubsetSum, leftSum + floor);
            }
        }
        
        return totalSum - 2 * maxSubsetSum;
    }
    
    private void generateSubsetSums(int[] stones, int start, int end, int currentSum, Set<Integer> sums) {
        if (start == end) {
            sums.add(currentSum);
            return;
        }
        // Include current stone
        generateSubsetSums(stones, start + 1, end, currentSum + stones[start], sums);
        // Exclude current stone
        generateSubsetSums(stones, start + 1, end, currentSum, sums);
    }
    
    /**
     * Helper method to visualize the DP process
     */
    public void visualizePartition(int[] stones) {
        System.out.println("\nLast Stone Weight II Visualization:");
        System.out.println("Stones: " + Arrays.toString(stones));
        
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }
        System.out.println("Total sum: " + totalSum);
        System.out.println("Target (totalSum/2): " + totalSum / 2);
        
        int target = totalSum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        
        System.out.println("\nDP Table Construction:");
        System.out.println("Initial: dp[0] = true (empty subset)");
        printDPArray(dp, "Initial");
        
        for (int i = 0; i < stones.length; i++) {
            int stone = stones[i];
            System.out.println("\nProcessing stone " + (i + 1) + ": weight = " + stone);
            
            // Update DP array backwards
            for (int j = target; j >= stone; j--) {
                if (dp[j - stone] && !dp[j]) {
                    dp[j] = true;
                    System.out.println("  Can achieve sum " + j + " by including stone " + stone + 
                                     " (from sum " + (j - stone) + ")");
                }
            }
            printDPArray(dp, "After stone " + (i + 1));
        }
        
        // Find maximum achievable sum
        int maxSubsetSum = 0;
        for (int j = target; j >= 0; j--) {
            if (dp[j]) {
                maxSubsetSum = j;
                break;
            }
        }
        
        System.out.println("\nMaximum subset sum ≤ " + target + ": " + maxSubsetSum);
        System.out.println("Minimum possible last stone weight: " + (totalSum - 2 * maxSubsetSum));
        
        // Show the partition
        showPartition(stones, dp, maxSubsetSum);
    }
    
    private void printDPArray(boolean[] dp, String label) {
        System.out.print(label + ": [");
        for (int i = 0; i < dp.length; i++) {
            if (dp[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println("]");
    }
    
    private void showPartition(int[] stones, boolean[] dp, int targetSum) {
        System.out.println("\nPartition Reconstruction:");
        
        // Reconstruct the subset that achieves targetSum
        List<Integer> subset = new ArrayList<>();
        int currentSum = targetSum;
        boolean[] used = new boolean[stones.length];
        
        // Backtrack to find which stones were used
        for (int i = stones.length - 1; i >= 0 && currentSum > 0; i--) {
            if (currentSum >= stones[i] && 
                (i == 0 && currentSum == stones[i] || 
                 i > 0 && dp[currentSum - stones[i]])) {
                subset.add(stones[i]);
                currentSum -= stones[i];
                used[i] = true;
            }
        }
        
        // Create the two partitions
        List<Integer> group1 = new ArrayList<>(subset);
        List<Integer> group2 = new ArrayList<>();
        for (int i = 0; i < stones.length; i++) {
            if (!used[i]) {
                group2.add(stones[i]);
            }
        }
        
        int sum1 = group1.stream().mapToInt(Integer::intValue).sum();
        int sum2 = group2.stream().mapToInt(Integer::intValue).sum();
        
        System.out.println("Group 1: " + group1 + " (sum = " + sum1 + ")");
        System.out.println("Group 2: " + group2 + " (sum = " + sum2 + ")");
        System.out.println("Difference: |" + sum1 + " - " + sum2 + "| = " + Math.abs(sum1 - sum2));
        
        // Simulate the smashing process
        simulateSmashing(new ArrayList<>(group1), new ArrayList<>(group2));
    }
    
    private void simulateSmashing(List<Integer> group1, List<Integer> group2) {
        System.out.println("\nSmashing Simulation:");
        
        // Combine both groups
        List<Integer> allStones = new ArrayList<>();
        allStones.addAll(group1);
        allStones.addAll(group2);
        Collections.sort(allStones);
        
        System.out.println("Initial stones: " + allStones);
        
        // Simulate smashing until one stone remains
        while (allStones.size() > 1) {
            // Always smash the two largest stones
            Collections.sort(allStones);
            int y = allStones.remove(allStones.size() - 1);
            int x = allStones.remove(allStones.size() - 1);
            
            if (x == y) {
                System.out.println("Smash " + x + " and " + y + " → both destroyed");
            } else {
                int result = y - x;
                allStones.add(result);
                System.out.println("Smash " + x + " and " + y + " → " + result + " remains");
            }
            System.out.println("Remaining stones: " + allStones);
        }
        
        if (allStones.isEmpty()) {
            System.out.println("Final result: 0 (no stones left)");
        } else {
            System.out.println("Final result: " + allStones.get(0));
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Last Stone Weight II Solution:");
        System.out.println("======================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example [2,7,4,1,8,1]");
        int[] stones1 = {2, 7, 4, 1, 8, 1};
        int expected1 = 1;
        
        long startTime = System.nanoTime();
        int result1a = solution.lastStoneWeightII(stones1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.lastStoneWeightII2D(stones1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.lastStoneWeightIIDFS(stones1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.lastStoneWeightIIBit(stones1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.lastStoneWeightIIOpt(stones1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("Knapsack DP:    " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("2D DP:          " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("DFS + Memo:     " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Bit Manipulation: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Optimized:      " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the partition process
        solution.visualizePartition(stones1);
        
        // Test case 2: All stones can be paired perfectly
        System.out.println("\nTest 2: All stones can be paired [2,2,2,2]");
        int[] stones2 = {2, 2, 2, 2};
        int result2a = solution.lastStoneWeightII(stones2);
        System.out.println("All paired: " + result2a + " - " + 
                         (result2a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single stone
        System.out.println("\nTest 3: Single stone [5]");
        int[] stones3 = {5};
        int result3a = solution.lastStoneWeightII(stones3);
        System.out.println("Single stone: " + result3a + " - " + 
                         (result3a == 5 ? "PASSED" : "FAILED"));
        
        // Test case 4: Example from problem
        System.out.println("\nTest 4: [31,26,33,21,40]");
        int[] stones4 = {31, 26, 33, 21, 40};
        int expected4 = 5;
        int result4a = solution.lastStoneWeightII(stones4);
        System.out.println("Complex case: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Two stones with difference
        System.out.println("\nTest 5: Two stones [3,7]");
        int[] stones5 = {3, 7};
        int result5a = solution.lastStoneWeightII(stones5);
        System.out.println("Two stones: " + result5a + " - " + 
                         (result5a == 4 ? "PASSED" : "FAILED"));
        
        // Test case 6: All stones same weight but odd count
        System.out.println("\nTest 6: Odd count same weight [3,3,3]");
        int[] stones6 = {3, 3, 3};
        int result6a = solution.lastStoneWeightII(stones6);
        System.out.println("Odd same weight: " + result6a + " - " + 
                         (result6a == 3 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("Knapsack DP:    " + time1a + " ns");
        System.out.println("2D DP:          " + time1b + " ns");
        System.out.println("DFS + Memo:     " + time1c + " ns");
        System.out.println("Bit Manipulation: " + time1d + " ns");
        System.out.println("Optimized:      " + time1e + " ns");
        
        // Test all approaches produce same results
        System.out.println("\nTest 8: All approaches consistency");
        boolean allConsistent = result1a == result1b && 
                              result1a == result1c && 
                              result1a == result1d &&
                              result1a == result1e;
        System.out.println("All approaches produce same results: " + allConsistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PROBLEM TRANSFORMATION EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The problem is equivalent to partitioning stones into two groups");
        System.out.println("such that the absolute difference between their sums is minimized.");
        
        System.out.println("\nMathematical Formulation:");
        System.out.println("Let S = total sum of all stones");
        System.out.println("Let P = sum of one partition");
        System.out.println("Then the other partition has sum S - P");
        System.out.println("The difference = |P - (S - P)| = |2P - S|");
        System.out.println("We want to minimize |2P - S|");
        System.out.println("This means we want P as close to S/2 as possible");
        
        System.out.println("\nDP Solution:");
        System.out.println("We use 0/1 knapsack to find all achievable sums ≤ S/2");
        System.out.println("Then we take the maximum achievable sum P");
        System.out.println("Answer = S - 2P");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Knapsack DP (RECOMMENDED):");
        System.out.println("   Time: O(n × S) - n stones, S = total sum");
        System.out.println("   Space: O(S) - Boolean array for achievable sums");
        System.out.println("   How it works:");
        System.out.println("     - Use boolean array to track achievable sums");
        System.out.println("     - For each stone, update array backwards");
        System.out.println("     - Find maximum achievable sum ≤ S/2");
        System.out.println("   Pros:");
        System.out.println("     - Optimal for given constraints");
        System.out.println("     - Memory efficient");
        System.out.println("     - Easy to implement");
        System.out.println("   Cons:");
        System.out.println("     - S can be large (but constraints help)");
        System.out.println("   Best for: Most use cases");
        
        System.out.println("\n2. 2D Dynamic Programming:");
        System.out.println("   Time: O(n × S) - Same complexity");
        System.out.println("   Space: O(n × S) - Full DP table");
        System.out.println("   How it works:");
        System.out.println("     - dp[i][j] = can we achieve sum j using first i stones");
        System.out.println("     - More intuitive but uses more space");
        System.out.println("   Pros:");
        System.out.println("     - Easy to understand and visualize");
        System.out.println("     - Clear recurrence relation");
        System.out.println("   Cons:");
        System.out.println("     - Higher space complexity");
        System.out.println("   Best for: Learning, small inputs");
        
        System.out.println("\n3. DFS with Memoization:");
        System.out.println("   Time: O(n × S) - Each state computed once");
        System.out.println("   Space: O(n × S) - Memoization table");
        System.out.println("   How it works:");
        System.out.println("     - Recursive top-down approach");
        System.out.println("     - Memoize computed results");
        System.out.println("     - Natural recursive thinking");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive recursive solution");
        System.out.println("     - Easy to understand recurrence");
        System.out.println("   Cons:");
        System.out.println("     - Recursion stack overhead");
        System.out.println("     - Higher constant factors");
        System.out.println("   Best for: When top-down thinking is preferred");
        
        System.out.println("\n4. Bit Manipulation:");
        System.out.println("   Time: O(n × S) - Same complexity");
        System.out.println("   Space: O(S) - Bitset for achievable sums");
        System.out.println("   How it works:");
        System.out.println("     - Use BitSet to represent achievable sums");
        System.out.println("     - Bit operations for efficient updates");
        System.out.println("   Pros:");
        System.out.println("     - Memory efficient (bits instead of booleans)");
        System.out.println("     - Fast bit operations");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Harder to debug");
        System.out.println("   Best for: Memory-constrained environments");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY THIS PROBLEM IS TRICKY:");
        System.out.println("1. It looks like a greedy problem but requires DP");
        System.out.println("2. The optimal strategy is not obvious");
        System.out.println("3. The problem reduces to partition problem");
        System.out.println("4. The connection to 0/1 knapsack is not immediately clear");
        System.out.println("5. The smashing process hides the underlying partition");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with the partition insight - it's the key");
        System.out.println("2. Explain the mathematical formulation clearly");
        System.out.println("3. Use knapsack DP for implementation");
        System.out.println("4. Discuss time/space complexity");
        System.out.println("5. Handle edge cases (single stone, all same)");
        System.out.println("6. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        // Additional examples with different scenarios
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ADDITIONAL TEST SCENARIOS:");
        System.out.println("=".repeat(70));
        
        int[][][] testCases = {
            {{1, 1, 2, 3, 5, 8, 13, 21}, 1}, // Fibonacci sequence
            {{10, 10, 10, 10, 10}, 0},        // All same, even count
            {{10, 10, 10, 10}, 0},            // All same, even count
            {{10, 10, 10}, 10},               // All same, odd count
            {{100, 50, 25, 25}, 0},           // Can achieve perfect partition
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int[] stones = testCases[i][0];
            int expected = testCases[i][1];
            int result = solution.lastStoneWeightII(stones);
            System.out.printf("Test %d: %s -> %d (expected: %d) - %s%n",
                            i + 1, Arrays.toString(stones), result, expected,
                            result == expected ? "PASSED" : "FAILED");
        }
        
        System.out.println("\nAll tests completed!");
    }
}
