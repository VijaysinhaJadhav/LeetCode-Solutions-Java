/**
 * 209. Minimum Size Subarray Sum
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of positive integers nums and a positive integer target, 
 * return the minimal length of a contiguous subarray of which the sum is 
 * greater than or equal to target. If there is no such subarray, return 0 instead.
 * 
 * Key Insights:
 * 1. Use sliding window technique with two pointers
 * 2. Expand window until sum >= target
 * 3. Then shrink window from left to find minimal length
 * 4. Track the minimum window length that satisfies the condition
 * 5. Handle case where no subarray meets the target
 * 
 * Approach (Sliding Window):
 * 1. Initialize left pointer, current sum, and minLength
 * 2. Iterate right pointer through the array
 * 3. Add current element to sum
 * 4. While sum >= target, update minLength and shrink window from left
 * 5. Return minLength (or 0 if no valid subarray found)
 * 
 * Time Complexity: O(n) - Each element visited at most twice
 * Space Complexity: O(1) - Only constant extra space
 * 
 * Tags: Array, Binary Search, Sliding Window, Prefix Sum
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Sliding Window (Two Pointers) - RECOMMENDED
     * O(n) time, O(1) space - Optimal solution
     */
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int left = 0;
        int currentSum = 0;
        int minLength = Integer.MAX_VALUE;
        
        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];
            
            // Shrink window from left while condition is satisfied
            while (currentSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                currentSum -= nums[left];
                left++;
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    /**
     * Approach 2: Binary Search with Prefix Sum
     * O(n log n) time, O(n) space - Follow-up solution
     */
    public int minSubArrayLenBinarySearch(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        int[] prefixSum = new int[n + 1];
        
        // Build prefix sum array
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        int minLength = Integer.MAX_VALUE;
        
        // For each starting index, find minimal ending index using binary search
        for (int i = 0; i < n; i++) {
            int left = i, right = n;
            
            while (left < right) {
                int mid = left + (right - left) / 2;
                int sum = prefixSum[mid + 1] - prefixSum[i];
                
                if (sum >= target) {
                    minLength = Math.min(minLength, mid - i + 1);
                    right = mid; // Try smaller ending index
                } else {
                    left = mid + 1; // Need larger ending index
                }
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    /**
     * Approach 3: Optimized Sliding Window with Early Termination
     * O(n) time, O(1) space - Optimized version
     */
    public int minSubArrayLenOptimized(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int left = 0;
        int currentSum = 0;
        int minLength = Integer.MAX_VALUE;
        
        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];
            
            // Early termination if single element satisfies condition
            if (nums[right] >= target) {
                return 1;
            }
            
            // Only try to shrink if we have a valid window
            if (currentSum >= target) {
                // Shrink window from left to find minimal length
                while (currentSum - nums[left] >= target) {
                    currentSum -= nums[left];
                    left++;
                }
                minLength = Math.min(minLength, right - left + 1);
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    /**
     * Approach 4: Separate While Loop for Shrinking
     * O(n) time, O(1) space - Alternative implementation
     */
    public int minSubArrayLenSeparateWhile(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int left = 0;
        int currentSum = 0;
        int minLength = Integer.MAX_VALUE;
        int right = 0;
        
        while (right < nums.length) {
            currentSum += nums[right];
            
            // Once we have a valid window, try to shrink it
            while (currentSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                currentSum -= nums[left];
                left++;
            }
            
            right++;
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    /**
     * Approach 5: Brute Force (For Comparison)
     * O(n^2) time, O(1) space - Not recommended for large inputs
     */
    public int minSubArrayLenBruteForce(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int minLength = Integer.MAX_VALUE;
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            for (int j = i; j < n; j++) {
                currentSum += nums[j];
                if (currentSum >= target) {
                    minLength = Math.min(minLength, j - i + 1);
                    break; // No need to check longer subarrays from this start
                }
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    /**
     * Helper method to visualize the sliding window algorithm
     */
    private void visualizeSlidingWindow(int target, int[] nums) {
        System.out.println("\nSliding Window Algorithm Visualization:");
        System.out.println("Target: " + target + ", Array: " + Arrays.toString(nums));
        
        int left = 0;
        int currentSum = 0;
        int minLength = Integer.MAX_VALUE;
        
        System.out.println("\nStep | Right | Value | Current Sum | Left | Window | Sum >= Target? | Min Length | Action");
        System.out.println("-----|-------|-------|-------------|------|--------|----------------|------------|--------");
        
        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];
            String window = Arrays.toString(Arrays.copyOfRange(nums, left, right + 1));
            boolean satisfiesCondition = currentSum >= target;
            String action = "Add element " + nums[right];
            
            // Check if we can shrink the window
            while (currentSum >= target) {
                int currentLength = right - left + 1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                    action = "Shrink - New min length: " + minLength;
                } else {
                    action = "Shrink window";
                }
                
                window = Arrays.toString(Arrays.copyOfRange(nums, left, right + 1));
                System.out.printf("%4d | %5d | %5d | %11d | %4d | %-15s | %13s | %10d | %s%n",
                                right + 1, right, nums[right], currentSum, left, 
                                window, "YES", minLength, action);
                
                currentSum -= nums[left];
                left++;
                
                // Update window after shrinking
                if (left <= right) {
                    window = Arrays.toString(Arrays.copyOfRange(nums, left, right + 1));
                    satisfiesCondition = currentSum >= target;
                } else {
                    window = "[]";
                    satisfiesCondition = false;
                }
            }
            
            if (!satisfiesCondition) {
                System.out.printf("%4d | %5d | %5d | %11d | %4d | %-15s | %13s | %10d | %s%n",
                                right + 1, right, nums[right], currentSum, left, 
                                window, "NO", 
                                minLength == Integer.MAX_VALUE ? 0 : minLength, action);
            }
        }
        
        System.out.println("\nFinal Result: Minimum Length = " + 
                          (minLength == Integer.MAX_VALUE ? 0 : minLength));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Minimum Size Subarray Sum:");
        System.out.println("===================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int target1 = 7;
        int[] nums1 = {2, 3, 1, 2, 4, 3};
        int expected1 = 2;
        
        long startTime = System.nanoTime();
        int result1a = solution.minSubArrayLen(target1, nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.minSubArrayLenBinarySearch(target1, nums1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.minSubArrayLenOptimized(target1, nums1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.minSubArrayLenSeparateWhile(target1, nums1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.minSubArrayLenBruteForce(target1, nums1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("Sliding Window: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Binary Search: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Separate While: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Brute Force: " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the sliding window algorithm
        solution.visualizeSlidingWindow(target1, nums1);
        
        // Test case 2: Single element satisfies
        System.out.println("\nTest 2: Single element satisfies");
        int target2 = 4;
        int[] nums2 = {1, 4, 4};
        int expected2 = 1;
        
        int result2a = solution.minSubArrayLen(target2, nums2);
        System.out.println("Single element satisfies: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: No subarray satisfies
        System.out.println("\nTest 3: No subarray satisfies");
        int target3 = 11;
        int[] nums3 = {1, 1, 1, 1, 1, 1, 1, 1};
        int expected3 = 0;
        
        int result3a = solution.minSubArrayLen(target3, nums3);
        System.out.println("No subarray satisfies: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: First element satisfies
        System.out.println("\nTest 4: First element satisfies");
        int target4 = 5;
        int[] nums4 = {5, 1, 1, 1};
        int expected4 = 1;
        
        int result4a = solution.minSubArrayLen(target4, nums4);
        System.out.println("First element satisfies: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Entire array needed
        System.out.println("\nTest 5: Entire array needed");
        int target5 = 10;
        int[] nums5 = {1, 2, 3, 4};
        int expected5 = 4;
        
        int result5a = solution.minSubArrayLen(target5, nums5);
        System.out.println("Entire array needed: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Multiple valid windows
        System.out.println("\nTest 6: Multiple valid windows");
        int target6 = 6;
        int[] nums6 = {1, 2, 3, 4, 5};
        int expected6 = 2; // [3,4] or [2,4] etc.
        
        int result6a = solution.minSubArrayLen(target6, nums6);
        System.out.println("Multiple valid windows: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large numbers
        System.out.println("\nTest 7: Large numbers");
        int target7 = 100;
        int[] nums7 = {10, 20, 30, 40, 50};
        int expected7 = 3; // [30,40,50] or [20,30,50]
        
        int result7a = solution.minSubArrayLen(target7, nums7);
        System.out.println("Large numbers: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Single element array
        System.out.println("\nTest 8: Single element array");
        int target8 = 5;
        int[] nums8 = {5};
        int expected8 = 1;
        
        int result8a = solution.minSubArrayLen(target8, nums8);
        System.out.println("Single element array: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Test case 9: Single element array no match
        System.out.println("\nTest 9: Single element array no match");
        int target9 = 6;
        int[] nums9 = {5};
        int expected9 = 0;
        
        int result9a = solution.minSubArrayLen(target9, nums9);
        System.out.println("Single element no match: " + result9a + " - " + 
                         (result9a == expected9 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 10: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Sliding Window: " + time1a + " ns");
        System.out.println("  Binary Search: " + time1b + " ns");
        System.out.println("  Optimized: " + time1c + " ns");
        System.out.println("  Separate While: " + time1d + " ns");
        System.out.println("  Brute Force: " + time1e + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 11: Large input performance");
        int[] largeNums = new int[100000];
        Random random = new Random(42);
        for (int i = 0; i < largeNums.length; i++) {
            largeNums[i] = random.nextInt(100) + 1; // Numbers between 1-100
        }
        int target11 = 50000; // Reasonable target
        
        startTime = System.nanoTime();
        int result11a = solution.minSubArrayLen(target11, largeNums);
        long time11a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result11b = solution.minSubArrayLenBinarySearch(target11, largeNums);
        long time11b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result11c = solution.minSubArrayLenOptimized(target11, largeNums);
        long time11c = System.nanoTime() - startTime;
        
        System.out.println("Large input (100,000 elements):");
        System.out.println("  Sliding Window: " + time11a + " ns, Result: " + result11a);
        System.out.println("  Binary Search: " + time11b + " ns, Result: " + result11b);
        System.out.println("  Optimized: " + time11c + " ns, Result: " + result11c);
        
        // Verify all approaches produce the same result
        boolean allEqual = result11a == result11b && result11a == result11c;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Edge case: Very large target (no solution)
        System.out.println("\nTest 12: Very large target (no solution)");
        int target12 = 10000000;
        int[] nums12 = {1, 2, 3, 4, 5};
        int expected12 = 0;
        
        int result12a = solution.minSubArrayLen(target12, nums12);
        System.out.println("Very large target: " + result12a + " - " + 
                         (result12a == expected12 ? "PASSED" : "FAILED"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SLIDING WINDOW ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nKey Insight:");
        System.out.println("Maintain a window [left, right] and expand it until the sum >= target.");
        System.out.println("Then shrink it from the left to find the minimal valid window.");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. We expand the window by moving the right pointer");
        System.out.println("2. When sum >= target, we have a candidate solution");
        System.out.println("3. We shrink from left to find the minimal valid window");
        System.out.println("4. Since all numbers are positive, shrinking always decreases the sum");
        System.out.println("5. We track the minimum window length encountered");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Sliding Window (Two Pointers) - RECOMMENDED:");
        System.out.println("   Time: O(n) - Each element visited at most twice");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Use two pointers: left and right");
        System.out.println("     - Expand window by moving right pointer");
        System.out.println("     - When sum >= target, shrink from left to minimize");
        System.out.println("     - Track minimum valid window length");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time complexity");
        System.out.println("     - O(1) space complexity");
        System.out.println("     - Intuitive and easy to implement");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of sliding window technique");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Binary Search with Prefix Sum:");
        System.out.println("   Time: O(n log n) - Binary search for each starting point");
        System.out.println("   Space: O(n) - Prefix sum array storage");
        System.out.println("   How it works:");
        System.out.println("     - Build prefix sum array");
        System.out.println("     - For each starting index, binary search for minimal ending index");
        System.out.println("     - Use prefix sums to calculate subarray sums efficiently");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates binary search application");
        System.out.println("     - Good follow-up solution");
        System.out.println("     - Works for any array (not just positive numbers)");
        System.out.println("   Cons:");
        System.out.println("     - O(n log n) time, not optimal");
        System.out.println("     - O(n) extra space");
        System.out.println("   Best for: Follow-up questions, learning purposes");
        
        System.out.println("\n3. Optimized Sliding Window:");
        System.out.println("   Time: O(n) - Single pass with optimizations");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Same as basic sliding window with early termination");
        System.out.println("     - Check if single element satisfies condition");
        System.out.println("     - More efficient shrinking logic");
        System.out.println("   Pros:");
        System.out.println("     - Potentially faster with early termination");
        System.out.println("     - More efficient for certain cases");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("     - Same asymptotic complexity");
        System.out.println("   Best for: Performance-critical applications");
        
        System.out.println("\n4. Separate While Loop Implementation:");
        System.out.println("   Time: O(n) - Each element visited at most twice");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Alternative implementation with separate while loop");
        System.out.println("     - Same algorithm, different code structure");
        System.out.println("   Pros:");
        System.out.println("     - Some find this structure clearer");
        System.out.println("     - Explicit right pointer increment");
        System.out.println("   Cons:");
        System.out.println("     - Essentially same as basic sliding window");
        System.out.println("   Best for: Personal preference, learning alternatives");
        
        System.out.println("\n5. Brute Force:");
        System.out.println("   Time: O(n^2) - Check all possible subarrays");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Check all possible starting points");
        System.out.println("     - For each start, find minimal ending that satisfies condition");
        System.out.println("     - Track minimum length");
        System.out.println("   Pros:");
        System.out.println("     - Simple to implement and understand");
        System.out.println("     - No complex algorithms needed");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large inputs (n = 10^5)");
        System.out.println("     - O(n^2) vs O(n) for optimal approaches");
        System.out.println("   Best for: Small inputs, educational purposes");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. All elements are positive, guaranteeing monotonic sum increase");
        System.out.println("2. This property enables the sliding window technique");
        System.out.println("3. For arrays with negative numbers, different approaches are needed");
        System.out.println("4. The minimal length can range from 1 to n (array length)");
        System.out.println("5. If total array sum < target, no solution exists");
        System.out.println("=".repeat(80));
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Sliding Window - it's the expected optimal solution");
        System.out.println("2. Explain the two-pointer approach clearly");
        System.out.println("3. Mention that positive numbers guarantee the algorithm works");
        System.out.println("4. Handle edge cases: no solution, single element, first element satisfies");
        System.out.println("5. Discuss time and space complexity (O(n), O(1))");
        System.out.println("6. For follow-up, mention binary search + prefix sum approach");
        System.out.println("7. Consider starting with brute force, then optimize to sliding window");
        System.out.println("=".repeat(80));
        
        System.out.println("\nAll tests completed!");
    }
}
