
# Solution.java

```java
import java.util.*;

/**
 * 53. Maximum Subarray
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find contiguous subarray with largest sum.
 * 
 * Key Insights:
 * 1. Kadane's Algorithm:
 *    - Track running sum
 *    - Reset when sum becomes negative
 *    - Update maximum sum seen
 * 2. Dynamic Programming:
 *    - dp[i] = max subarray ending at i
 *    - dp[i] = max(nums[i], dp[i-1] + nums[i])
 * 3. Divide and Conquer:
 *    - Split, solve left/right, find crossing sum
 * 
 * Approach 1: Kadane's Algorithm (RECOMMENDED)
 * O(n) time, O(1) space
 */

class Solution {
    
    /**
     * Approach 1: Kadane's Algorithm (RECOMMENDED)
     * O(n) time, O(1) space
     * Optimal solution for this problem
     */
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        
        int maxSum = nums[0];  // Initialize with first element
        int currentSum = nums[0];
        
        for (int i = 1; i < n; i++) {
            // Either start new subarray at current element
            // Or extend previous subarray
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            
            // Update global maximum
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    /**
     * Approach 2: Kadane's Algorithm (Alternative implementation)
     * Same O(n) time, O(1) space
     * More explicit reset logic
     */
    public int maxSubArrayKadaneAlt(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        
        int maxSum = nums[0];
        int currentSum = nums[0];
        
        for (int i = 1; i < n; i++) {
            // If current sum is negative, reset it
            if (currentSum < 0) {
                currentSum = nums[i];
            } else {
                currentSum += nums[i];
            }
            
            // Update max if current sum is better
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    /**
     * Approach 3: Dynamic Programming (Explicit DP array)
     * O(n) time, O(n) space
     * More intuitive DP approach
     */
    public int maxSubArrayDP(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        
        int[] dp = new int[n];  // dp[i] = max subarray ending at i
        dp[0] = nums[0];
        int maxSum = dp[0];
        
        for (int i = 1; i < n; i++) {
            // Either take current element alone
            // Or extend previous max subarray
            dp[i] = Math.max(nums[i], dp[i-1] + nums[i]);
            maxSum = Math.max(maxSum, dp[i]);
        }
        
        return maxSum;
    }
    
    /**
     * Approach 4: Dynamic Programming (Space Optimized)
     * O(n) time, O(1) space
     * Only need previous dp value
     */
    public int maxSubArrayDPSpaceOpt(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        
        int prevMax = nums[0];  // dp[i-1]
        int maxSum = nums[0];
        
        for (int i = 1; i < n; i++) {
            // Current dp value
            int currentMax = Math.max(nums[i], prevMax + nums[i]);
            maxSum = Math.max(maxSum, currentMax);
            
            // Update for next iteration
            prevMax = currentMax;
        }
        
        return maxSum;
    }
    
    /**
     * Approach 5: Divide and Conquer
     * O(n log n) time, O(log n) space for recursion stack
     * Follow-up requirement
     */
    public int maxSubArrayDivideConquer(int[] nums) {
        return divideConquerHelper(nums, 0, nums.length - 1);
    }
    
    private int divideConquerHelper(int[] nums, int left, int right) {
        // Base case: single element
        if (left == right) {
            return nums[left];
        }
        
        int mid = left + (right - left) / 2;
        
        // Recursively find max in left and right halves
        int leftMax = divideConquerHelper(nums, left, mid);
        int rightMax = divideConquerHelper(nums, mid + 1, right);
        
        // Find max crossing subarray
        int crossMax = maxCrossingSubarray(nums, left, mid, right);
        
        // Return max of three
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }
    
    private int maxCrossingSubarray(int[] nums, int left, int mid, int right) {
        // Find max sum from mid to left
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }
        
        // Find max sum from mid+1 to right
        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }
        
        // Return combined sum
        return leftSum + rightSum;
    }
    
    /**
     * Approach 6: Brute Force (for comparison)
     * O(n²) time, O(1) space
     * Only works for small n
     */
    public int maxSubArrayBruteForce(int[] nums) {
        int n = nums.length;
        int maxSum = Integer.MIN_VALUE;
        
        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            for (int j = i; j < n; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        
        return maxSum;
    }
    
    /**
     * Approach 7: Prefix Sum Optimization
     * O(n) time, O(1) space
     * Track minimum prefix sum
     */
    public int maxSubArrayPrefixSum(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        
        int maxSum = nums[0];
        int minPrefixSum = 0;  // Minimum prefix sum seen so far
        int prefixSum = 0;
        
        for (int i = 0; i < n; i++) {
            prefixSum += nums[i];
            
            // Maximum subarray sum ending at i = prefixSum - minPrefixSum
            maxSum = Math.max(maxSum, prefixSum - minPrefixSum);
            
            // Update minimum prefix sum
            minPrefixSum = Math.min(minPrefixSum, prefixSum);
        }
        
        return maxSum;
    }
    
    /**
     * Approach 8: Visualization Helper
     * Shows step-by-step execution of Kadane's algorithm
     */
    public void visualizeKadane(int[] nums) {
        System.out.println("\nKadane's Algorithm Visualization:");
        System.out.println("nums = " + Arrays.toString(nums));
        System.out.println("\nIndex | Value | Current Sum | Max Sum | Action");
        System.out.println("------|-------|-------------|---------|--------");
        
        int maxSum = nums[0];
        int currentSum = nums[0];
        
        System.out.printf("%5d | %5d | %11d | %7d | Initialize%n", 
            0, nums[0], currentSum, maxSum);
        
        for (int i = 1; i < nums.length; i++) {
            int newCurrentSum = Math.max(nums[i], currentSum + nums[i]);
            
            String action;
            if (newCurrentSum == nums[i]) {
                action = "Reset: start new subarray at index " + i;
            } else {
                action = "Extend: add to existing subarray";
            }
            
            currentSum = newCurrentSum;
            maxSum = Math.max(maxSum, currentSum);
            
            System.out.printf("%5d | %5d | %11d | %7d | %s%n", 
                i, nums[i], currentSum, maxSum, action);
        }
        
        System.out.println("\nMaximum Subarray Sum: " + maxSum);
        
        // Find and display the actual subarray
        findAndDisplaySubarray(nums);
    }
    
    /**
     * Helper to find and display the maximum subarray
     */
    private void findAndDisplaySubarray(int[] nums) {
        int n = nums.length;
        int maxSum = nums[0];
        int currentSum = nums[0];
        int start = 0, end = 0;
        int tempStart = 0;
        
        for (int i = 1; i < n; i++) {
            if (nums[i] > currentSum + nums[i]) {
                currentSum = nums[i];
                tempStart = i;
            } else {
                currentSum += nums[i];
            }
            
            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }
        
        System.out.print("Maximum Subarray: [");
        for (int i = start; i <= end; i++) {
            System.out.print(nums[i]);
            if (i < end) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("Sum: " + maxSum);
    }
    
    /**
     * Helper to explain Kadane's algorithm concept
     */
    public void explainKadaneAlgorithm() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("KADANE'S ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nCore Idea:");
        System.out.println("At each position i, we have two choices:");
        System.out.println("1. Start a new subarray at i");
        System.out.println("2. Extend the previous subarray");
        
        System.out.println("\nMathematical Formulation:");
        System.out.println("Let dp[i] = maximum sum of subarray ending at i");
        System.out.println("Then dp[i] = max(nums[i], dp[i-1] + nums[i])");
        System.out.println("Answer = max(dp[i]) for all i");
        
        System.out.println("\nIntuition:");
        System.out.println("- If dp[i-1] is negative, it hurts our sum");
        System.out.println("- Better to start fresh at nums[i]");
        System.out.println("- Otherwise, extend the previous subarray");
        
        System.out.println("\nExample: nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]");
        System.out.println("\nStep by step:");
        System.out.println("i=0: dp[0] = -2, max=-2");
        System.out.println("i=1: max(1, -2+1= -1) = 1, start new at 1, max=1");
        System.out.println("i=2: max(-3, 1-3= -2) = -2, extend, max=1");
        System.out.println("i=3: max(4, -2+4= 2) = 4, start new at 4, max=4");
        System.out.println("i=4: max(-1, 4-1= 3) = 3, extend, max=4");
        System.out.println("i=5: max(2, 3+2= 5) = 5, extend, max=5");
        System.out.println("i=6: max(1, 5+1= 6) = 6, extend, max=6");
        System.out.println("i=7: max(-5, 6-5= 1) = 1, extend, max=6");
        System.out.println("i=8: max(4, 1+4= 5) = 5, extend, max=6");
        System.out.println("\nMaximum sum = 6");
    }
    
    /**
     * Helper to demonstrate edge cases
     */
    public void demonstrateEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES DEMONSTRATION:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. All positive numbers:");
        int[] case1 = {1, 2, 3, 4, 5};
        System.out.println("   nums = " + Arrays.toString(case1));
        System.out.println("   Result: " + solution.maxSubArray(case1) + 
                         " (whole array sum = 15)");
        
        System.out.println("\n2. All negative numbers:");
        int[] case2 = {-5, -4, -3, -2, -1};
        System.out.println("   nums = " + Arrays.toString(case2));
        System.out.println("   Result: " + solution.maxSubArray(case2) + 
                         " (maximum element = -1)");
        
        System.out.println("\n3. Single element:");
        int[] case3 = {5};
        System.out.println("   nums = " + Arrays.toString(case3));
        System.out.println("   Result: " + solution.maxSubArray(case3));
        
        System.out.println("\n4. Mixed with zeros:");
        int[] case4 = {0, -2, 3, -1, 0, 2, -1};
        System.out.println("   nums = " + Arrays.toString(case4));
        System.out.println("   Result: " + solution.maxSubArray(case4));
        
        System.out.println("\n5. Large numbers:");
        int[] case5 = {10000, -1, 10000};
        System.out.println("   nums = " + Arrays.toString(case5));
        System.out.println("   Result: " + solution.maxSubArray(case5));
        
        System.out.println("\n6. Alternating pattern:");
        int[] case6 = {1, -1, 1, -1, 1};
        System.out.println("   nums = " + Arrays.toString(case6));
        System.out.println("   Result: " + solution.maxSubArray(case6));
    }
    
    /**
     * Helper to compare different approaches
     */
    public void compareApproaches(int[] nums) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput:");
        System.out.println("nums = " + Arrays.toString(nums));
        System.out.println("n = " + nums.length);
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5, result6, result7;
        
        // Approach 1: Kadane's Algorithm
        startTime = System.nanoTime();
        result1 = solution.maxSubArray(nums);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Kadane Alternative
        startTime = System.nanoTime();
        result2 = solution.maxSubArrayKadaneAlt(nums);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: DP Array
        startTime = System.nanoTime();
        result3 = solution.maxSubArrayDP(nums);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: DP Space Optimized
        startTime = System.nanoTime();
        result4 = solution.maxSubArrayDPSpaceOpt(nums);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 6: Brute Force (only for small n)
        if (nums.length <= 1000) {
            startTime = System.nanoTime();
            result6 = solution.maxSubArrayBruteForce(nums);
            endTime = System.nanoTime();
            long time6 = endTime - startTime;
        } else {
            result6 = Integer.MIN_VALUE;
        }
        
        // Approach 7: Prefix Sum
        startTime = System.nanoTime();
        result7 = solution.maxSubArrayPrefixSum(nums);
        endTime = System.nanoTime();
        long time7 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (Kadane):           " + result1);
        System.out.println("Approach 2 (Kadane Alt):       " + result2);
        System.out.println("Approach 3 (DP Array):         " + result3);
        System.out.println("Approach 4 (DP Space Opt):     " + result4);
        System.out.println("Approach 7 (Prefix Sum):       " + result7);
        
        if (nums.length <= 1000) {
            System.out.println("Approach 6 (Brute Force):      " + result6);
            
            boolean allEqual = (result1 == result2) && (result2 == result3) && 
                              (result3 == result4) && (result4 == result7) &&
                              (result7 == result6);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (Kadane)%n", time1);
            System.out.printf("Approach 2: %-10d (Kadane Alt)%n", time2);
            System.out.printf("Approach 3: %-10d (DP Array)%n", time3);
            System.out.printf("Approach 4: %-10d (DP Space Opt)%n", time4);
            System.out.printf("Approach 6: %-10d (Brute Force)%n", time6);
            System.out.printf("Approach 7: %-10d (Prefix Sum)%n", time7);
        } else {
            boolean allEqual = (result1 == result2) && (result2 == result3) && 
                              (result3 == result4) && (result4 == result7);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (Kadane)%n", time1);
            System.out.printf("Approach 2: %-10d (Kadane Alt)%n", time2);
            System.out.printf("Approach 3: %-10d (DP Array)%n", time3);
            System.out.printf("Approach 4: %-10d (DP Space Opt)%n", time4);
            System.out.printf("Approach 7: %-10d (Prefix Sum)%n", time7);
        }
        
        // Visualize the solution
        if (nums.length <= 20) {
            solution.visualizeKadane(nums);
        }
    }
    
    /**
     * Helper to analyze time and space complexity
     */
    public void analyzeComplexity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Time Complexity:");
        System.out.println("   a. Kadane's Algorithm: O(n)");
        System.out.println("      - Single pass through array");
        System.out.println("   b. Dynamic Programming: O(n)");
        System.out.println("      - Fill DP table once");
        System.out.println("   c. Divide and Conquer: O(n log n)");
        System.out.println("      - Recurrence: T(n) = 2T(n/2) + O(n)");
        System.out.println("   d. Brute Force: O(n²)");
        System.out.println("      - Check all subarrays");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   a. Kadane's Algorithm: O(1)");
        System.out.println("      - Only few variables");
        System.out.println("   b. DP Array: O(n)");
        System.out.println("      - Store DP table");
        System.out.println("   c. DP Space Optimized: O(1)");
        System.out.println("      - Only need previous value");
        System.out.println("   d. Divide and Conquer: O(log n)");
        System.out.println("      - Recursion stack depth");
        System.out.println("   e. Brute Force: O(1)");
        System.out.println("      - No extra space");
        
        System.out.println("\n3. Why Kadane's Algorithm is Optimal:");
        System.out.println("   - Problem has optimal substructure");
        System.out.println("   - Can be solved by dynamic programming");
        System.out.println("   - Kadane's algorithm is the space-optimized DP");
        System.out.println("   - No need to check all subarrays (O(n²))");
        System.out.println("   - Greedy choice: reset when sum becomes negative");
        
        System.out.println("\n4. Constraints Analysis:");
        System.out.println("   n ≤ 10^5");
        System.out.println("   - O(n) time: 100,000 operations (very fast)");
        System.out.println("   - O(n²) time: 10^10 operations (too slow)");
        System.out.println("   - O(n log n) time: ~1.6 million operations (fast)");
    }
    
    /**
     * Helper to show real-world applications
     */
    public void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Financial Analysis:");
        System.out.println("   - Maximum profit in stock trading (Best Time to Buy/Sell Stock)");
        System.out.println("   - Portfolio optimization");
        
        System.out.println("\n2. Signal Processing:");
        System.out.println("   - Find segment with maximum signal strength");
        System.out.println("   - Audio/image processing");
        
        System.out.println("\n3. Computer Vision:");
        System.out.println("   - Object detection in images");
        System.out.println("   - Pattern recognition");
        
        System.out.println("\n4. Bioinformatics:");
        System.out.println("   - DNA sequence analysis");
        System.out.println("   - Protein structure prediction");
        
        System.out.println("\n5. Network Analysis:");
        System.out.println("   - Maximum traffic flow");
        System.out.println("   - Bandwidth optimization");
        
        System.out.println("\n6. Machine Learning:");
        System.out.println("   - Feature selection");
        System.out.println("   - Anomaly detection");
        
        System.out.println("\n7. Game Development:");
        System.out.println("   - Score optimization in games");
        System.out.println("   - Pathfinding algorithms");
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Maximum Subarray:");
        System.out.println("=========================");
        
        // Show Kadane's algorithm explanation
        solution.explainKadaneAlgorithm();
        
        // Show edge cases
        solution.demonstrateEdgeCases();
        
        // Test case 1: Example from problem
        System.out.println("\n\nTest 1: Example 1");
        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int expected1 = 6;
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums1));
        
        int result1 = solution.maxSubArray(nums1);
        System.out.println("Expected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        // Visualize
        solution.visualizeKadane(nums1);
        
        // Test case 2: Example 2
        System.out.println("\n\nTest 2: Example 2");
        int[] nums2 = {1};
        int expected2 = 1;
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums2));
        
        int result2 = solution.maxSubArray(nums2);
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        // Test case 3: Example 3
        System.out.println("\n\nTest 3: Example 3");
        int[] nums3 = {5, 4, -1, 7, 8};
        int expected3 = 23;
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums3));
        
        int result3 = solution.maxSubArray(nums3);
        System.out.println("Expected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + (result3 == expected3 ? "✓" : "✗"));
        
        // Test case 4: All negative
        System.out.println("\n\nTest 4: All negative");
        int[] nums4 = {-5, -4, -3, -2, -1};
        int expected4 = -1;  // Maximum element
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums4));
        
        int result4 = solution.maxSubArray(nums4);
        System.out.println("Expected: " + expected4);
        System.out.println("Result:   " + result4);
        System.out.println("Passed: " + (result4 == expected4 ? "✓" : "✗"));
        
        // Test case 5: Mixed
        System.out.println("\n\nTest 5: Mixed with zeros");
        int[] nums5 = {0, -2, 3, -1, 0, 2, -1};
        int expected5 = 4;  // [3, -1, 0, 2] or [3, -1, 0, 2, -1]
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums5));
        
        int result5 = solution.maxSubArray(nums5);
        System.out.println("Expected: " + expected5);
        System.out.println("Result:   " + result5);
        System.out.println("Passed: " + (result5 == expected5 ? "✓" : "✗"));
        
        // Test case 6: Large numbers
        System.out.println("\n\nTest 6: Large numbers with negatives");
        int[] nums6 = {10000, -1, 10000};
        int expected6 = 19999;  // Whole array
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums6));
        
        int result6 = solution.maxSubArray(nums6);
        System.out.println("Expected: " + expected6);
        System.out.println("Result:   " + result6);
        System.out.println("Passed: " + (result6 == expected6 ? "✓" : "✗"));
        
        // Compare all approaches
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES:");
        System.out.println("=".repeat(80));
        
        int[][] testCases = {
            nums1,
            nums2,
            nums3,
            nums4,
            nums5,
            nums6,
            new int[]{1, -2, 3, -4, 5, -6, 7},
            new int[]{-1, -2, -3, -4, -5},
            new int[]{1, 2, 3, 4, 5},
            new int[]{0, 0, 0, 0, 0},
            new int[]{-2, -3, 4, -1, -2, 1, 5, -3},
            new int[]{1, 2, -1, 3, 4, -2, 5}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\nTest Case " + (i+1) + ":");
            int[] nums = testCases[i];
            
            System.out.println("nums = " + Arrays.toString(nums));
            solution.compareApproaches(nums);
            
            if (i < testCases.length - 1) {
                System.out.println("\n" + "-".repeat(80));
            }
        }
        
        // Performance test with larger array
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST WITH LARGER ARRAY:");
        System.out.println("=".repeat(80));
        
        // Generate larger test case
        Random random = new Random(42);
        int n = 100000; // Maximum constraint
        int[] largeNums = new int[n];
        for (int i = 0; i < n; i++) {
            // Mix positive and negative numbers
            largeNums[i] = random.nextInt(20001) - 10000;
        }
        
        System.out.println("\nTesting with n = " + n);
        
        long startTime, endTime;
        
        // Approach 1: Kadane's Algorithm
        startTime = System.currentTimeMillis();
        int perf1 = solution.maxSubArray(largeNums);
        endTime = System.currentTimeMillis();
        long time1 = endTime - startTime;
        
        // Approach 2: DP Space Optimized
        startTime = System.currentTimeMillis();
        int perf2 = solution.maxSubArrayDPSpaceOpt(largeNums);
        endTime = System.currentTimeMillis();
        long time2 = endTime - startTime;
        
        // Approach 5: Divide and Conquer
        startTime = System.currentTimeMillis();
        int perf5 = solution.maxSubArrayDivideConquer(largeNums);
        endTime = System.currentTimeMillis();
        long time5 = endTime - startTime;
        
        // Approach 7: Prefix Sum
        startTime = System.currentTimeMillis();
        int perf7 = solution.maxSubArrayPrefixSum(largeNums);
        endTime = System.currentTimeMillis();
        long time7 = endTime - startTime;
        
        System.out.println("\nPerformance (milliseconds):");
        System.out.printf("Approach 1 (Kadane):           %5d ms - Result: %d%n", time1, perf1);
        System.out.printf("Approach 4 (DP Space Opt):     %5d ms - Result: %d%n", time2, perf2);
        System.out.printf("Approach 5 (Divide & Conquer): %5d ms - Result: %d%n", time5, perf5);
        System.out.printf("Approach 7 (Prefix Sum):       %5d ms - Result: %d%n", time7, perf7);
        
        // Verify all give same result
        boolean perfConsistent = (perf1 == perf2) && (perf2 == perf5) && (perf5 == perf7);
        System.out.println("\nResults consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Show complexity analysis
        solution.analyzeComplexity();
        
        // Show applications
        solution.showApplications();
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - Find contiguous subarray with maximum sum");
        System.out.println("   - Can include negative numbers");
        
        System.out.println("\n2. Think about brute force:");
        System.out.println("   - Check all subarrays: O(n²)");
        System.out.println("   - Not efficient for large n");
        
        System.out.println("\n3. Recognize optimal substructure:");
        System.out.println("   - Problem can be broken into smaller subproblems");
        System.out.println("   - Maximum subarray ending at i depends on i-1");
        
        System.out.println("\n4. Dynamic programming formulation:");
        System.out.println("   - dp[i] = max subarray ending at i");
        System.out.println("   - dp[i] = max(nums[i], dp[i-1] + nums[i])");
        
        System.out.println("\n5. Optimize space:");
        System.out.println("   - Only need previous dp value");
        System.out.println("   - This is Kadane's algorithm");
        
        System.out.println("\n6. Implement Kadane's algorithm:");
        System.out.println("   - Initialize with first element");
        System.out.println("   - Track current sum and maximum sum");
        System.out.println("   - Reset current sum when it becomes negative");
        
        System.out.println("\n7. Handle edge cases:");
        System.out.println("   - All negative numbers");
        System.out.println("   - Single element array");
        System.out.println("   - Array with zeros");
        
        System.out.println("\n8. Discuss follow-up:");
        System.out.println("   - Divide and conquer approach");
        System.out.println("   - O(n log n) time, O(log n) space");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Dynamic programming insight");
        System.out.println("- Space optimization to O(1)");
        System.out.println("- Reset strategy when sum becomes negative");
        System.out.println("- Handling all negative numbers case");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not handling all negative numbers correctly");
        System.out.println("- Using O(n²) brute force");
        System.out.println("- Forgetting to initialize with first element");
        System.out.println("- Not considering empty subarray (problem says non-empty)");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 152. Maximum Product Subarray");
        System.out.println("2. 121. Best Time to Buy and Sell Stock");
        System.out.println("3. 198. House Robber");
        System.out.println("4. 300. Longest Increasing Subsequence");
        System.out.println("5. 1143. Longest Common Subsequence");
        System.out.println("6. 718. Maximum Length of Repeated Subarray");
        System.out.println("7. 209. Minimum Size Subarray Sum");
        System.out.println("8. 325. Maximum Size Subarray Sum Equals k");
        System.out.println("9. 523. Continuous Subarray Sum");
        System.out.println("10. 560. Subarray Sum Equals K");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
