
# Solution.java

```java
import java.util.*;

/**
 * 55. Jump Game
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Determine if you can reach the last index of the array.
 * Each element represents the maximum jump length at that position.
 * 
 * Key Insights:
 * 1. Greedy approach: track farthest reachable index
 * 2. If farthest reaches or exceeds last index → return true
 * 3. If current index > farthest → unreachable
 */
class Solution {
    
    /**
     * Approach 1: Greedy (Reachability) - RECOMMENDED
     * Time: O(n), Space: O(1)
     * 
     * Steps:
     * 1. Initialize farthest = 0
     * 2. Iterate through array up to farthest (or end)
     * 3. At each index, update farthest = max(farthest, i + nums[i])
     * 4. If farthest >= last index → return true
     * 5. If loop completes without reaching end → return false
     */
    public boolean canJump(int[] nums) {
        int farthest = 0;
        int n = nums.length;
        
        for (int i = 0; i <= farthest && i < n; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (farthest >= n - 1) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 2: Greedy with Early Exit
     * Time: O(n), Space: O(1)
     * 
     * Similar but stops when farthest reaches end
     */
    public boolean canJumpEarlyExit(int[] nums) {
        int farthest = 0;
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            if (i > farthest) {
                return false;
            }
            farthest = Math.max(farthest, i + nums[i]);
            if (farthest >= n - 1) {
                return true;
            }
        }
        
        return farthest >= n - 1;
    }
    
    /**
     * Approach 3: Dynamic Programming (Bottom-Up)
     * Time: O(n²), Space: O(n)
     * 
     * Less efficient but shows DP approach
     */
    public boolean canJumpDP(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[0] = true;
        
        for (int i = 0; i < n; i++) {
            if (dp[i]) {
                int maxJump = Math.min(n - 1, i + nums[i]);
                for (int j = i + 1; j <= maxJump; j++) {
                    dp[j] = true;
                }
            }
        }
        
        return dp[n - 1];
    }
    
    /**
     * Approach 4: Dynamic Programming with Optimization
     * Time: O(n²), Space: O(n)
     * 
     * Optimized DP using reachable array
     */
    public boolean canJumpDPOptimized(int[] nums) {
        int n = nums.length;
        boolean[] reachable = new boolean[n];
        reachable[0] = true;
        
        for (int i = 0; i < n; i++) {
            if (reachable[i]) {
                for (int j = 1; j <= nums[i] && i + j < n; j++) {
                    reachable[i + j] = true;
                    if (i + j == n - 1) return true;
                }
            }
        }
        
        return reachable[n - 1];
    }
    
    /**
     * Approach 5: Backward Greedy
     * Time: O(n), Space: O(1)
     * 
     * Work backwards from last index
     */
    public boolean canJumpBackward(int[] nums) {
        int lastGoodIndex = nums.length - 1;
        
        for (int i = nums.length - 1; i >= 0; i--) {
            if (i + nums[i] >= lastGoodIndex) {
                lastGoodIndex = i;
            }
        }
        
        return lastGoodIndex == 0;
    }
    
    /**
     * Approach 6: BFS (Level Order Traversal)
     * Time: O(n²), Space: O(n)
     * 
     * BFS approach to explore reachable indices
     */
    public boolean canJumpBFS(int[] nums) {
        int n = nums.length;
        if (n == 1) return true;
        
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            int maxJump = Math.min(n - 1, curr + nums[curr]);
            
            for (int next = curr + 1; next <= maxJump; next++) {
                if (next == n - 1) return true;
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
        
        return false;
    }
    
    /**
     * Helper: Visualize the jumping process
     */
    public void visualizeJumpGame(int[] nums) {
        System.out.println("\nJump Game Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nArray: " + Arrays.toString(nums));
        System.out.println("Length: " + nums.length);
        
        System.out.println("\nGreedy approach (tracking farthest reachable index):");
        int farthest = 0;
        int n = nums.length;
        
        System.out.println("\nIndex | Value | Farthest | Can Reach?");
        System.out.println("------|-------|----------|-----------");
        
        for (int i = 0; i < n; i++) {
            String canReach = i <= farthest ? "✓" : "✗";
            System.out.printf("%5d | %5d | %8d | %9s%n", i, nums[i], farthest, canReach);
            
            if (i > farthest) {
                System.out.println("\nCannot proceed beyond index " + i);
                System.out.println("Result: false");
                return;
            }
            
            farthest = Math.max(farthest, i + nums[i]);
            
            if (farthest >= n - 1) {
                System.out.printf("%5d | %5d | %8d | %9s%n", i, nums[i], farthest, "✓");
                System.out.println("\nReached last index! Result: true");
                return;
            }
        }
        
        System.out.println("\nResult: " + (farthest >= n - 1));
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            {2, 3, 1, 1, 4},           // true
            {3, 2, 1, 0, 4},           // false
            {0},                        // true (single element)
            {2, 0, 0},                  // true
            {2, 5, 0, 0},               // true
            {1, 1, 1, 0},               // true
            {1, 0, 1, 0},               // false
            {1, 2, 3, 4, 5},            // true
            {5, 4, 3, 2, 1},            // true
            {1, 0, 0, 0},               // false
            {0, 1, 0, 0},               // false (can't start)
            {3, 0, 8, 2, 0, 0, 1}       // true
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][] testCases = generateTestCases();
        boolean[] expected = {true, false, true, true, true, true, false, true, true, false, false, true};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.printf("\nTest %d: %s%n", i + 1, Arrays.toString(nums));
            
            boolean result1 = canJump(nums.clone());
            boolean result2 = canJumpEarlyExit(nums.clone());
            boolean result3 = canJumpDP(nums.clone());
            boolean result4 = canJumpDPOptimized(nums.clone());
            boolean result5 = canJumpBackward(nums.clone());
            boolean result6 = canJumpBFS(nums.clone());
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i] && result6 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Can jump: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1 (Greedy): " + result1);
                System.out.println("  Method 2 (Early): " + result2);
                System.out.println("  Method 3 (DP): " + result3);
                System.out.println("  Method 4 (DP Opt): " + result4);
                System.out.println("  Method 5 (Backward): " + result5);
                System.out.println("  Method 6 (BFS): " + result6);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeJumpGame(nums);
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
        
        // Generate large test case
        int n = 10000;
        int[] nums = new int[n];
        for (int i = 0; i < n - 1; i++) {
            nums[i] = 1;
        }
        nums[n - 1] = 0;
        
        System.out.println("Test Setup: " + n + " elements (all 1's)");
        
        long[] times = new long[6];
        boolean[] results = new boolean[6];
        
        // Method 1: Greedy
        int[] nums1 = nums.clone();
        long start = System.currentTimeMillis();
        results[0] = canJump(nums1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Greedy Early Exit
        int[] nums2 = nums.clone();
        start = System.currentTimeMillis();
        results[1] = canJumpEarlyExit(nums2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: DP
        int[] nums3 = nums.clone();
        start = System.currentTimeMillis();
        results[2] = canJumpDP(nums3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: DP Optimized
        int[] nums4 = nums.clone();
        start = System.currentTimeMillis();
        results[3] = canJumpDPOptimized(nums4);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Backward Greedy
        int[] nums5 = nums.clone();
        start = System.currentTimeMillis();
        results[4] = canJumpBackward(nums5);
        times[4] = System.currentTimeMillis() - start;
        
        // Method 6: BFS
        int[] nums6 = nums.clone();
        start = System.currentTimeMillis();
        results[5] = canJumpBFS(nums6);
        times[5] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Greedy                  | %9d | %6b%n", times[0], results[0]);
        System.out.printf("2. Greedy (Early Exit)     | %9d | %6b%n", times[1], results[1]);
        System.out.printf("3. DP (Bottom-Up)          | %9d | %6b%n", times[2], results[2]);
        System.out.printf("4. DP (Optimized)          | %9d | %6b%n", times[3], results[3]);
        System.out.printf("5. Backward Greedy         | %9d | %6b%n", times[4], results[4]);
        System.out.printf("6. BFS                     | %9d | %6b%n", times[5], results[5]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3] && results[3] == results[4] &&
                          results[4] == results[5];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Greedy approaches are fastest (O(n))");
        System.out.println("2. DP approaches are O(n²) and slower for large n");
        System.out.println("3. BFS is also O(n²) and slower");
        System.out.println("4. Backward greedy is equally efficient");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single element:");
        int[] nums1 = {0};
        System.out.println("   Input: [0]");
        System.out.println("   Output: " + canJump(nums1));
        
        System.out.println("\n2. Two elements, can jump:");
        int[] nums2 = {1, 0};
        System.out.println("   Input: [1,0]");
        System.out.println("   Output: " + canJump(nums2));
        
        System.out.println("\n3. Two elements, cannot jump:");
        int[] nums3 = {0, 1};
        System.out.println("   Input: [0,1]");
        System.out.println("   Output: " + canJump(nums3));
        
        System.out.println("\n4. Zero at start:");
        int[] nums4 = {0, 2, 3};
        System.out.println("   Input: [0,2,3]");
        System.out.println("   Output: " + canJump(nums4));
        
        System.out.println("\n5. Large jump from start:");
        int[] nums5 = {10, 0, 0, 0, 0, 0};
        System.out.println("   Input: [10,0,0,0,0,0]");
        System.out.println("   Output: " + canJump(nums5));
        
        System.out.println("\n6. Maximum values:");
        int[] nums6 = {100000, 0, 0, 0, 0};
        System.out.println("   Input: [100000,0,0,0,0]");
        System.out.println("   Output: " + canJump(nums6));
    }
    
    /**
     * Helper: Explain the greedy algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nGreedy Algorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nProblem: Can you reach the last index starting from index 0?");
        
        System.out.println("\nKey Insight:");
        System.out.println("We don't need to track the actual path, just the farthest index we can reach.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize farthest = 0 (farthest index reachable so far)");
        System.out.println("2. Iterate i from 0 to n-1:");
        System.out.println("   - If i > farthest → cannot reach this index → return false");
        System.out.println("   - Update farthest = max(farthest, i + nums[i])");
        System.out.println("   - If farthest >= n-1 → return true");
        System.out.println("3. Return true if farthest >= n-1");
        
        System.out.println("\nExample: nums = [2, 3, 1, 1, 4]");
        System.out.println("  i=0: farthest = max(0, 0+2)=2");
        System.out.println("  i=1: farthest = max(2, 1+3)=4 → reaches last index! → return true");
        
        System.out.println("\nExample: nums = [3, 2, 1, 0, 4]");
        System.out.println("  i=0: farthest = max(0, 0+3)=3");
        System.out.println("  i=1: farthest = max(3, 1+2)=3");
        System.out.println("  i=2: farthest = max(3, 2+1)=3");
        System.out.println("  i=3: farthest = max(3, 3+0)=3");
        System.out.println("  i=4: i=4 > farthest=3 → cannot proceed → return false");
        
        System.out.println("\nWhy Greedy Works:");
        System.out.println("- At each step, we only care about the maximum reachable index");
        System.out.println("- If we can reach index i, we can also reach all indices before i");
        System.out.println("- Any path that reaches farther is better, so greedy choice is optimal");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What does nums[i] represent? (Maximum jump length)");
        System.out.println("   - Can we jump less than the maximum? (Yes)");
        System.out.println("   - What if array length is 1? (Already at last index → true)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Try all possible paths (exponential)");
        System.out.println("   - Acknowledge it's too slow");
        
        System.out.println("\n3. Propose greedy solution:");
        System.out.println("   - Track farthest reachable index");
        System.out.println("   - Explain why greedy works");
        System.out.println("   - Walk through example");
        
        System.out.println("\n4. Discuss alternative approaches:");
        System.out.println("   - DP: O(n²) time, O(n) space");
        System.out.println("   - BFS: O(n²) time, O(n) space");
        System.out.println("   - Backward greedy: equally efficient");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(n) - single pass");
        System.out.println("   - Space: O(1) - constant space");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - Single element");
        System.out.println("   - Zero at start");
        System.out.println("   - Large jumps");
        System.out.println("   - Array with zeros blocking the path");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Forgetting that i > farthest means unreachable");
        System.out.println("   - Using O(n²) DP when O(n) solution exists");
        System.out.println("   - Not handling single-element array");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("55. Jump Game");
        System.out.println("=============");
        
        // Explain algorithm
        solution.explainAlgorithm();
        
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
        
        System.out.println("\nRecommended Implementation (Greedy):");
        System.out.println("""
class Solution {
    public boolean canJump(int[] nums) {
        int farthest = 0;
        int n = nums.length;
        
        for (int i = 0; i <= farthest && i < n; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (farthest >= n - 1) {
                return true;
            }
        }
        
        return false;
    }
}
            """);
        
        System.out.println("\nAlternative (Backward Greedy):");
        System.out.println("""
class Solution {
    public boolean canJump(int[] nums) {
        int lastGoodIndex = nums.length - 1;
        
        for (int i = nums.length - 1; i >= 0; i--) {
            if (i + nums[i] >= lastGoodIndex) {
                lastGoodIndex = i;
            }
        }
        
        return lastGoodIndex == 0;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Greedy approach is optimal: O(n) time, O(1) space");
        System.out.println("2. Track the farthest reachable index at each step");
        System.out.println("3. If current index exceeds farthest → unreachable");
        System.out.println("4. If farthest reaches or exceeds last index → success");
        System.out.println("5. Works because we only need to know reachability, not the path");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - single pass through array");
        System.out.println("- Space: O(1) - constant extra space");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you find the minimum number of jumps? (Jump Game II)");
        System.out.println("2. What if you could also move backwards?");
        System.out.println("3. How would you return the actual path?");
        System.out.println("4. What if the array contains negative numbers?");
        System.out.println("5. How would you solve it with BFS?");
    }
}
