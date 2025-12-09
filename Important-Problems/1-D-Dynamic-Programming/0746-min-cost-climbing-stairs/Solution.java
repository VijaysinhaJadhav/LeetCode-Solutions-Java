
## Problems/Arrays-Hashing/0746-min-cost-climbing-stairs/Solution.java

```java
/**
 * 746. Min Cost Climbing Stairs
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * You are given an integer array cost where cost[i] is the cost of i-th step on a staircase.
 * Once you pay the cost, you can either climb one or two steps.
 * You can either start from the step with index 0, or the step with index 1.
 * Return the minimum cost to reach the top of the floor (beyond the last step).
 * 
 * Key Insights:
 * 1. DP: min cost to reach step i = min(cost[i-1] + dp[i-1], cost[i-2] + dp[i-2])
 * 2. Base cases: dp[0] = 0, dp[1] = 0 (can start from step 0 or 1 without paying)
 * 3. Goal: Reach beyond last step, so need dp[cost.length]
 * 4. The cost is paid when leaving a step, not when arriving
 * 
 * Approach (Dynamic Programming - Bottom Up):
 * 1. Initialize two variables for dp[i-2] and dp[i-1]
 * 2. Iterate from step 2 to cost.length
 * 3. At each step, compute min cost and update variables
 * 4. Return the final result for dp[cost.length]
 * 
 * Time Complexity: O(n) - Single pass through cost array
 * Space Complexity: O(1) - Only constant extra space
 * 
 * Tags: Array, Dynamic Programming, Optimization
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Dynamic Programming (Bottom-up) - RECOMMENDED
     * O(n) time, O(1) space
     */
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        
        // Base cases: we can start from step 0 or 1 without paying initially
        int prev2 = 0; // dp[i-2]
        int prev1 = 0; // dp[i-1]
        
        for (int i = 2; i <= n; i++) {
            int current = Math.min(prev1 + cost[i - 1], prev2 + cost[i - 2]);
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    /**
     * Approach 2: Dynamic Programming with Array
     * O(n) time, O(n) space - More intuitive but uses extra space
     */
    public int minCostClimbingStairsDP(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 1];
        
        // Base cases: starting points have 0 cost
        dp[0] = 0;
        dp[1] = 0;
        
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        
        return dp[n];
    }
    
    /**
     * Approach 3: Recursion with Memoization
     * O(n) time, O(n) space - Top-down approach
     */
    public int minCostClimbingStairsMemo(int[] cost) {
        int n = cost.length;
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return minCostHelper(cost, n, memo);
    }
    
    private int minCostHelper(int[] cost, int i, int[] memo) {
        if (i <= 1) return 0; // Base cases
        if (memo[i] != -1) return memo[i];
        
        memo[i] = Math.min(
            minCostHelper(cost, i - 1, memo) + cost[i - 1],
            minCostHelper(cost, i - 2, memo) + cost[i - 2]
        );
        return memo[i];
    }
    
    /**
     * Approach 4: Reverse DP (From top to bottom thinking)
     * O(n) time, O(1) space - Alternative perspective
     */
    public int minCostClimbingStairsReverse(int[] cost) {
        int n = cost.length;
        
        // Start from the end and work backwards
        for (int i = n - 3; i >= 0; i--) {
            cost[i] += Math.min(cost[i + 1], cost[i + 2]);
        }
        
        return Math.min(cost[0], cost[1]);
    }
    
    /**
     * Approach 5: Two Variables with Different Interpretation
     * O(n) time, O(1) space - Alternative variable naming
     */
    public int minCostClimbingStairsTwoVars(int[] cost) {
        int n = cost.length;
        
        // costToReach[i] represents minimum cost to reach step i
        int costToReachPrevPrev = 0; // step i-2
        int costToReachPrev = 0;     // step i-1
        
        for (int i = 2; i <= n; i++) {
            int costToReachCurrent = Math.min(
                costToReachPrev + cost[i - 1],
                costToReachPrevPrev + cost[i - 2]
            );
            costToReachPrevPrev = costToReachPrev;
            costToReachPrev = costToReachCurrent;
        }
        
        return costToReachPrev;
    }
    
    /**
     * Approach 6: DP with Cost Paid on Arrival (Alternative Interpretation)
     * O(n) time, O(1) space - Different cost interpretation
     */
    public int minCostClimbingStairsArrival(int[] cost) {
        int n = cost.length;
        
        // In this interpretation, we pay cost when we arrive at a step
        // So starting steps have their costs
        int prev2 = cost[0]; // cost to reach step 0
        int prev1 = cost[1]; // cost to reach step 1
        
        for (int i = 2; i < n; i++) {
            int current = cost[i] + Math.min(prev1, prev2);
            prev2 = prev1;
            prev1 = current;
        }
        
        // We can reach top from either last or second last step
        return Math.min(prev1, prev2);
    }
    
    /**
     * Helper method to visualize the DP process
     */
    private void visualizeDP(int[] cost) {
        System.out.println("\nMin Cost Climbing Stairs DP Visualization:");
        System.out.println("Cost array: " + Arrays.toString(cost));
        System.out.println("Goal: Reach beyond step " + cost.length);
        
        int n = cost.length;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 0;
        
        System.out.println("\nDP Table Construction:");
        System.out.println("Step | Min Cost | Calculation");
        System.out.println("-----|----------|------------");
        System.out.println("  0  |    0     | Start point (free)");
        System.out.println("  1  |    0     | Start point (free)");
        
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
            System.out.printf("  %d  |    %2d    | min(dp[%d] + cost[%d], dp[%d] + cost[%d]) = min(%d + %d, %d + %d)%n", 
                            i, dp[i], i-1, i-1, i-2, i-2, 
                            dp[i-1], cost[i-1], dp[i-2], cost[i-2]);
        }
        
        System.out.println("\nResult: " + dp[n] + " minimum cost to climb " + n + " stairs");
    }
    
    /**
     * Helper method to show the optimal path
     */
    private void showOptimalPath(int[] cost) {
        System.out.println("\nOptimal Path Analysis:");
        System.out.println("Cost array: " + Arrays.toString(cost));
        
        int n = cost.length;
        int[] dp = new int[n + 1];
        int[] path = new int[n + 1]; // To reconstruct path
        
        dp[0] = 0;
        dp[1] = 0;
        path[0] = -1; // Start
        path[1] = -1; // Start
        
        for (int i = 2; i <= n; i++) {
            int costFromPrev1 = dp[i - 1] + cost[i - 1];
            int costFromPrev2 = dp[i - 2] + cost[i - 2];
            
            if (costFromPrev1 <= costFromPrev2) {
                dp[i] = costFromPrev1;
                path[i] = i - 1;
            } else {
                dp[i] = costFromPrev2;
                path[i] = i - 2;
            }
        }
        
        // Reconstruct path
        List<Integer> optimalPath = new ArrayList<>();
        int current = n;
        while (current > 1) { // Stop when we reach start points (0 or 1)
            optimalPath.add(0, path[current]);
            current = path[current];
        }
        
        System.out.println("Optimal path indices: " + optimalPath);
        System.out.println("Total cost: " + dp[n]);
        
        // Show step-by-step movement
        System.out.println("\nStep-by-step movement:");
        int totalCost = 0;
        int position = optimalPath.isEmpty() ? (cost[0] <= cost[1] ? 0 : 1) : optimalPath.get(0);
        
        if (position == 0 || position == 1) {
            System.out.println("Start at step " + position + " (free)");
        }
        
        for (int i = 0; i < optimalPath.size(); i++) {
            int from = optimalPath.get(i);
            int to = (i < optimalPath.size() - 1) ? optimalPath.get(i + 1) : n;
            int steps = to - from;
            int stepCost = cost[from];
            totalCost += stepCost;
            System.out.printf("From step %d to step %d (%d step%s) - Pay cost %d (Total: %d)%n", 
                            from, to, steps, steps > 1 ? "s" : "", stepCost, totalCost);
        }
    }
    
    /**
     * Helper method to compare with regular climbing stairs
     */
    private void compareWithClimbingStairs(int[] cost) {
        System.out.println("\nComparison with Climbing Stairs (Problem 70):");
        System.out.println("Feature           | Climbing Stairs | Min Cost Climbing Stairs");
        System.out.println("------------------|-----------------|-------------------------");
        System.out.println("Goal              | Count ways      | Find minimum cost");
        System.out.println("Recurrence        | dp[i]=dp[i-1]+dp[i-2] | dp[i]=min(dp[i-1]+cost[i-1], dp[i-2]+cost[i-2])");
        System.out.println("Base cases        | dp[1]=1, dp[2]=2 | dp[0]=0, dp[1]=0");
        System.out.println("Start positions   | Fixed at step 1 | Can start at step 0 or 1");
        System.out.println("Cost consideration| No cost         | Each step has cost");
        
        System.out.println("\nKey Differences:");
        System.out.println("1. Climbing Stairs counts paths, Min Cost finds optimal path");
        System.out.println("2. Min Cost has cost optimization, Climbing Stairs has combinatorial counting");
        System.out.println("3. Both use DP but with different recurrence relations");
    }
    
    /**
     * Helper method to analyze different cost patterns
     */
    private void analyzeCostPatterns() {
        System.out.println("\nCost Pattern Analysis:");
        
        int[][] testCases = {
            {10, 15, 20}, // Example 1
            {1, 100, 1, 1, 1, 100, 1, 1, 100, 1}, // Example 2
            {0, 0, 0, 0}, // All zero cost
            {1, 2, 3, 4, 5}, // Increasing cost
            {5, 4, 3, 2, 1}, // Decreasing cost
            {1, 2, 1, 2, 1} // Alternating cost
        };
        
        System.out.println("Cost Pattern | Result | Explanation");
        System.out.println("-------------|--------|------------");
        
        for (int[] cost : testCases) {
            int result = minCostClimbingStairs(cost);
            String explanation = getPatternExplanation(cost, result);
            System.out.printf("%-12s | %6d | %s%n", 
                            Arrays.toString(cost).length() > 12 ? 
                            Arrays.toString(cost).substring(0, 9) + "..." : 
                            Arrays.toString(cost), 
                            result, explanation);
        }
    }
    
    private String getPatternExplanation(int[] cost, int result) {
        if (Arrays.stream(cost).allMatch(c -> c == 0)) {
            return "All steps free - cost is 0";
        } else if (isIncreasing(cost)) {
            return "Increasing cost - prefer starting from step 0";
        } else if (isDecreasing(cost)) {
            return "Decreasing cost - prefer starting from step 1";
        } else {
            return "Mixed pattern - DP finds optimal path";
        }
    }
    
    private boolean isIncreasing(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }
    
    private boolean isDecreasing(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1]) return false;
        }
        return true;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Min Cost Climbing Stairs Solution:");
        System.out.println("===========================================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example 1");
        int[] cost1 = {10, 15, 20};
        int expected1 = 15;
        
        long startTime = System.nanoTime();
        int result1a = solution.minCostClimbingStairs(cost1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.minCostClimbingStairsDP(cost1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.minCostClimbingStairsMemo(cost1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = (result1a == expected1);
        boolean test1b = (result1b == expected1);
        boolean test1c = (result1c == expected1);
        
        System.out.println("DP Bottom-up: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DP Array: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Memoization: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeDP(cost1);
        solution.showOptimalPath(cost1);
        
        // Test case 2: Example 2
        System.out.println("\nTest 2: Example 2");
        int[] cost2 = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        int expected2 = 6;
        
        int result2a = solution.minCostClimbingStairs(cost2);
        System.out.println("Example 2: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        solution.showOptimalPath(cost2);
        
        // Test case 3: Two steps
        System.out.println("\nTest 3: Two steps");
        int[] cost3 = {10, 15};
        int expected3 = 10; // Start from step 0 (cost 10) is cheaper than step 1 (cost 15)
        
        int result3a = solution.minCostClimbingStairs(cost3);
        System.out.println("Two steps: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: All zero cost
        System.out.println("\nTest 4: All zero cost");
        int[] cost4 = {0, 0, 0, 0};
        int expected4 = 0;
        
        int result4a = solution.minCostClimbingStairs(cost4);
        System.out.println("All zero: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Large cost difference
        System.out.println("\nTest 5: Large cost difference");
        int[] cost5 = {1, 1000, 1, 1000, 1};
        int result5a = solution.minCostClimbingStairs(cost5);
        System.out.println("Large difference: " + result5a + " (expected: 3)");
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Example 1 performance:");
        System.out.println("  DP Bottom-up: " + time1a + " ns");
        System.out.println("  DP Array: " + time1b + " ns");
        System.out.println("  Memoization: " + time1c + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        int[] cost7 = new int[1000];
        Arrays.fill(cost7, 1); // All ones
        
        startTime = System.nanoTime();
        int result7a = solution.minCostClimbingStairs(cost7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result7b = solution.minCostClimbingStairsReverse(cost7);
        long time7b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result7c = solution.minCostClimbingStairsArrival(cost7);
        long time7c = System.nanoTime() - startTime;
        
        System.out.println("Large input (1000 steps):");
        System.out.println("  DP Bottom-up: " + time7a + " ns, Result: " + result7a);
        System.out.println("  Reverse DP: " + time7b + " ns, Result: " + result7b);
        System.out.println("  Arrival DP: " + time7c + " ns, Result: " + result7c);
        
        // Compare all approaches for consistency
        System.out.println("\nTest 8: Consistency Check");
        int[] testCost = {1, 2, 3, 4, 5};
        int r1 = solution.minCostClimbingStairs(testCost);
        int r2 = solution.minCostClimbingStairsDP(testCost);
        int r3 = solution.minCostClimbingStairsMemo(testCost);
        int r4 = solution.minCostClimbingStairsReverse(testCost);
        int r5 = solution.minCostClimbingStairsTwoVars(testCost);
        int r6 = solution.minCostClimbingStairsArrival(testCost);
        
        boolean consistent = (r1 == r2) && (r1 == r3) && (r1 == r4) && (r1 == r5) && (r1 == r6);
        System.out.println("All approaches consistent: " + consistent);
        
        // Additional analysis
        solution.compareWithClimbingStairs(cost1);
        solution.analyzeCostPatterns();
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DYNAMIC PROGRAMMING EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The minimum cost to reach step i is the minimum of:");
        System.out.println("  - Cost to reach step i-1 + cost of step i-1 (come from i-1)");
        System.out.println("  - Cost to reach step i-2 + cost of step i-2 (come from i-2)");
        System.out.println("This gives us the recurrence relation:");
        System.out.println("  dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])");
        
        System.out.println("\nBase Cases:");
        System.out.println("  dp[0] = 0  (can start from step 0 without paying)");
        System.out.println("  dp[1] = 0  (can start from step 1 without paying)");
        
        System.out.println("\nWhy this works:");
        System.out.println("1. Optimal Substructure: Optimal solution for step i depends on i-1 and i-2");
        System.out.println("2. Overlapping Subproblems: Same subproblems solved multiple times");
        System.out.println("3. We pay cost when leaving a step, not when arriving");
        System.out.println("4. The goal is to reach beyond the last step (index = cost.length)");
        
        System.out.println("\nVisual Example (cost = [10, 15, 20]):");
        System.out.println("dp[0] = 0");
        System.out.println("dp[1] = 0");
        System.out.println("dp[2] = min(dp[1] + cost[1], dp[0] + cost[0]) = min(0 + 15, 0 + 10) = 10");
        System.out.println("dp[3] = min(dp[2] + cost[2], dp[1] + cost[1]) = min(10 + 20, 0 + 15) = 15");
        System.out.println("Result: dp[3] = 15");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Dynamic Programming (Bottom-up) - RECOMMENDED:");
        System.out.println("   Time: O(n) - Single pass through cost array");
        System.out.println("   Space: O(1) - Only two variables needed");
        System.out.println("   How it works:");
        System.out.println("     - Use two variables to store dp[i-2] and dp[i-1]");
        System.out.println("     - Iteratively compute current from previous two");
        System.out.println("     - Return dp[cost.length]");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Simple and efficient");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - None for this problem");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Dynamic Programming with Array:");
        System.out.println("   Time: O(n) - Single pass through cost array");
        System.out.println("   Space: O(n) - Array storage");
        System.out.println("   How it works:");
        System.out.println("     - Store all intermediate results in array");
        System.out.println("     - Build solution from bottom up");
        System.out.println("   Pros:");
        System.out.println("     - More intuitive for beginners");
        System.out.println("     - Easy to visualize and debug");
        System.out.println("     - Can reconstruct optimal path");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("   Best for: Learning DP concepts, path reconstruction");
        
        System.out.println("\n3. Recursion with Memoization:");
        System.out.println("   Time: O(n) - With memoization");
        System.out.println("   Space: O(n) - Recursion stack and memo storage");
        System.out.println("   How it works:");
        System.out.println("     - Top-down recursive approach");
        System.out.println("     - Store computed results to avoid recomputation");
        System.out.println("   Pros:");
        System.out.println("     - Natural recursive thinking");
        System.out.println("     - Only computes needed subproblems");
        System.out.println("   Cons:");
        System.out.println("     - Recursion overhead");
        System.out.println("     - Stack overflow risk for large n");
        System.out.println("   Best for: When prefer recursive thinking");
        
        System.out.println("\n4. Reverse DP:");
        System.out.println("   Time: O(n) - Single pass from end to start");
        System.out.println("   Space: O(1) - Modifies input array");
        System.out.println("   How it works:");
        System.out.println("     - Work backwards from the end");
        System.out.println("     - Update cost[i] to represent min cost from i to end");
        System.out.println("   Pros:");
        System.out.println("     - In-place modification");
        System.out.println("     - Different perspective on the problem");
        System.out.println("   Cons:");
        System.out.println("     - Modifies input array");
        System.out.println("     - Less intuitive for some");
        System.out.println("   Best for: When input modification is acceptable");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("OPTIMIZATION STRATEGIES:");
        System.out.println("1. Space Optimization: Use two variables instead of array");
        System.out.println("2. Early Analysis: Check if starting from step 0 or 1 is better");
        System.out.println("3. Pattern Recognition: Identify cost patterns for optimization");
        System.out.println("4. Path Reconstruction: Store previous steps for optimal path");
        System.out.println("5. Boundary Handling: Proper base case initialization");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with DP Bottom-up - it's the expected solution");
        System.out.println("2. Explain the recurrence relation clearly");
        System.out.println("3. Clarify that cost is paid when leaving a step");
        System.out.println("4. Mention the two free starting points (step 0 and 1)");
        System.out.println("5. Discuss time/space complexity");
        System.out.println("6. Handle edge cases: two steps, all zero cost");
        System.out.println("7. Mention alternative approaches if time permits");
        System.out.println("8. Write clean, efficient code with proper variable naming");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
