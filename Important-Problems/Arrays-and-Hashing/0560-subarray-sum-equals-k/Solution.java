/**
 * 560. Subarray Sum Equals K
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of integers nums and an integer k, return the total number of 
 * contiguous subarrays whose sum equals to k.
 * 
 * Key Insights:
 * 1. Use prefix sum technique with HashMap for optimal solution
 * 2. If prefixSum[i] - prefixSum[j] = k, then sum from j+1 to i equals k
 * 3. Store prefix sums and their frequencies in HashMap
 * 4. Initialize with (0, 1) to handle subarrays starting from index 0
 * 
 * Approach (Prefix Sum with HashMap):
 * 1. Maintain running sum and HashMap of prefix sums
 * 2. For each element, check if (currentSum - k) exists in map
 * 3. If exists, add its frequency to count
 * 4. Update frequency of current prefix sum in map
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 * 
 * Tags: Array, Hash Table, Prefix Sum
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Prefix Sum with HashMap - RECOMMENDED
     * O(n) time, O(n) space - Optimal solution
     */
    public int subarraySum(int[] nums, int k) {
        // Map to store prefix sums and their frequencies
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        
        // Initialize with prefix sum 0 having count 1
        // This handles the case when subarray starts from index 0
        prefixSumCount.put(0, 1);
        
        int count = 0;
        int currentSum = 0;
        
        for (int num : nums) {
            // Calculate current prefix sum
            currentSum += num;
            
            // If (currentSum - k) exists in map, it means we found subarrays ending at current index
            // with sum equal to k
            if (prefixSumCount.containsKey(currentSum - k)) {
                count += prefixSumCount.get(currentSum - k);
            }
            
            // Update the frequency of current prefix sum
            prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * Approach 2: Brute Force - Check all subarrays
     * O(n²) time, O(1) space - Simple but inefficient for large inputs
     */
    public int subarraySumBruteForce(int[] nums, int k) {
        int count = 0;
        int n = nums.length;
        
        for (int start = 0; start < n; start++) {
            int sum = 0;
            for (int end = start; end < n; end++) {
                sum += nums[end];
                if (sum == k) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 3: Prefix Sum Array
     * O(n²) time, O(n) space - Better than brute force but still quadratic
     */
    public int subarraySumPrefixArray(int[] nums, int k) {
        int n = nums.length;
        int[] prefixSum = new int[n + 1];
        
        // Build prefix sum array
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        int count = 0;
        
        // Check all subarray sums using prefix sums
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                // sum from start to end = prefixSum[end+1] - prefixSum[start]
                if (prefixSum[end + 1] - prefixSum[start] == k) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 4: Sliding Window (Limited Use Case)
     * Only works for positive numbers, not suitable for this problem due to negative numbers
     */
    public int subarraySumSlidingWindow(int[] nums, int k) {
        // Note: This approach only works for arrays with positive numbers
        // Since the problem can have negative numbers, this is not a general solution
        // Included for educational purposes only
        
        int count = 0;
        int currentSum = 0;
        int left = 0;
        
        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];
            
            while (currentSum > k && left <= right) {
                currentSum -= nums[left];
                left++;
            }
            
            if (currentSum == k) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Approach 5: Enhanced HashMap with Visualization
     * Same time complexity but with detailed step-by-step visualization
     */
    public int subarraySumWithVisualization(int[] nums, int k) {
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1);
        
        int count = 0;
        int currentSum = 0;
        
        System.out.println("Prefix Sum HashMap Approach Visualization:");
        System.out.println("nums = " + Arrays.toString(nums) + ", k = " + k);
        System.out.println("Step | Num | CurrentSum | CurrentSum-k | Count | HashMap State");
        System.out.println("-----|-----|------------|--------------|-------|--------------");
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            currentSum += num;
            int complement = currentSum - k;
            
            int complementCount = prefixSumCount.getOrDefault(complement, 0);
            count += complementCount;
            
            // Update HashMap
            prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);
            
            System.out.printf("%4d | %3d | %10d | %12d | %5d | %s%n",
                            i + 1, num, currentSum, complement, count, prefixSumCount);
        }
        
        return count;
    }
    
    /**
     * Helper method to understand the mathematical principle
     */
    private void explainPrinciple(int[] nums, int k) {
        System.out.println("\nMathematical Principle Explanation:");
        System.out.println("====================================");
        System.out.println("We want: sum[i..j] = k");
        System.out.println("But sum[i..j] = prefixSum[j] - prefixSum[i-1]");
        System.out.println("So: prefixSum[j] - prefixSum[i-1] = k");
        System.out.println("Therefore: prefixSum[j] - k = prefixSum[i-1]");
        System.out.println("If prefixSum[i-1] exists in our map, we found a valid subarray!");
        System.out.println("We store prefix sums in HashMap for O(1) lookups.");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Subarray Sum Equals K Solution:");
        System.out.println("========================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] nums1 = {1, 1, 1};
        int k1 = 2;
        int expected1 = 2;
        
        long startTime = System.nanoTime();
        int result1a = solution.subarraySum(nums1, k1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.subarraySumBruteForce(nums1, k1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.subarraySumPrefixArray(nums1, k1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        
        System.out.println("HashMap: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Brute Force: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Prefix Array: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the HashMap approach
        System.out.println("\nVisualization for Test 1:");
        solution.subarraySumWithVisualization(nums1, k1);
        
        // Test case 2: Example with different numbers
        System.out.println("\nTest 2: Example with different numbers");
        int[] nums2 = {1, 2, 3};
        int k2 = 3;
        int expected2 = 2;
        
        int result2a = solution.subarraySum(nums2, k2);
        System.out.println("Different numbers: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Negative numbers
        System.out.println("\nTest 3: Negative numbers");
        int[] nums3 = {1, -1, 1, -1, 1};
        int k3 = 0;
        int expected3 = 6;
        
        int result3a = solution.subarraySum(nums3, k3);
        System.out.println("Negative numbers: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single element
        System.out.println("\nTest 4: Single element");
        int[] nums4 = {1};
        int k4 = 1;
        int expected4 = 1;
        
        int result4a = solution.subarraySum(nums4, k4);
        System.out.println("Single element: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: All zeros
        System.out.println("\nTest 5: All zeros");
        int[] nums5 = {0, 0, 0, 0};
        int k5 = 0;
        int expected5 = 10; // n*(n+1)/2 = 4*5/2 = 10
        
        int result5a = solution.subarraySum(nums5, k5);
        System.out.println("All zeros: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: No subarray with sum k
        System.out.println("\nTest 6: No subarray with sum k");
        int[] nums6 = {1, 2, 3};
        int k6 = 7;
        int expected6 = 0;
        
        int result6a = solution.subarraySum(nums6, k6);
        System.out.println("No subarray: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large k value
        System.out.println("\nTest 7: Large k value");
        int[] nums7 = {1, 2, 3};
        int k7 = 6;
        int expected7 = 1;
        
        int result7a = solution.subarraySum(nums7, k7);
        System.out.println("Large k: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Mixed positive and negative
        System.out.println("\nTest 8: Mixed positive and negative");
        int[] nums8 = {3, 4, 7, 2, -3, 1, 4, 2};
        int k8 = 7;
        int expected8 = 4;
        
        int result8a = solution.subarraySum(nums8, k8);
        System.out.println("Mixed numbers: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  HashMap: " + time1a + " ns");
        System.out.println("  Brute Force: " + time1b + " ns");
        System.out.println("  Prefix Array: " + time1c + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[] largeNums = new int[1000];
        Arrays.fill(largeNums, 1); // All ones to create many subarrays
        
        startTime = System.nanoTime();
        int result10a = solution.subarraySum(largeNums, 5);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10b = solution.subarraySumBruteForce(largeNums, 5);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10c = solution.subarraySumPrefixArray(largeNums, 5);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (1000 elements):");
        System.out.println("  HashMap: " + time10a + " ns, Result: " + result10a);
        System.out.println("  Brute Force: " + time10b + " ns, Result: " + result10b);
        System.out.println("  Prefix Array: " + time10c + " ns, Result: " + result10c);
        
        // Verify all approaches produce the same result
        boolean allEqual = result10a == result10b && result10a == result10c;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Mathematical explanation
        solution.explainPrinciple(nums1, k1);
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. HashMap with Prefix Sum (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(n) - HashMap storage");
        System.out.println("   How it works:");
        System.out.println("     - Maintain running sum and HashMap of prefix sums");
        System.out.println("     - For each element, check if (currentSum - k) exists");
        System.out.println("     - If exists, add its frequency to count");
        System.out.println("     - Update frequency of current prefix sum");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time complexity");
        System.out.println("     - Handles negative numbers correctly");
        System.out.println("     - Most efficient for large inputs");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("     - Requires understanding of prefix sum concept");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Brute Force:");
        System.out.println("   Time: O(n²) - Check all possible subarrays");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - For each starting index, check all ending indices");
        System.out.println("     - Calculate running sum for each subarray");
        System.out.println("     - Count subarrays with sum equal to k");
        System.out.println("   Pros:");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to implement and understand");
        System.out.println("     - No extra space required");
        System.out.println("   Cons:");
        System.out.println("     - O(n²) time doesn't scale for large inputs");
        System.out.println("     - Too slow for maximum constraints (n=20000)");
        System.out.println("   Best for: Small inputs, understanding the problem");
        
        System.out.println("\n3. Prefix Sum Array:");
        System.out.println("   Time: O(n²) - Still quadratic but better constant factors");
        System.out.println("   Space: O(n) - Prefix sum array storage");
        System.out.println("   How it works:");
        System.out.println("     - Build prefix sum array in O(n) time");
        System.out.println("     - Use prefix sums to calculate subarray sums in O(1)");
        System.out.println("     - Still need O(n²) to check all subarrays");
        System.out.println("   Pros:");
        System.out.println("     - Better than brute force for repeated calculations");
        System.out.println("     - Demonstrates prefix sum concept clearly");
        System.out.println("   Cons:");
        System.out.println("     - Still O(n²) time complexity");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("   Best for: Educational purposes, small to medium inputs");
        
        System.out.println("\n4. Sliding Window (Limited Use):");
        System.out.println("   Time: O(n) - Single pass with two pointers");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Maintain window with sum <= k");
        System.out.println("     - Expand right pointer, shrink left if sum > k");
        System.out.println("     - Count windows with sum exactly k");
        System.out.println("   Pros:");
        System.out.println("     - O(n) time and O(1) space for positive numbers");
        System.out.println("     - Very efficient for the right use case");
        System.out.println("   Cons:");
        System.out.println("     - ONLY works for arrays with positive numbers");
        System.out.println("     - Cannot handle negative numbers (fails for general case)");
        System.out.println("   Best for: Arrays with only positive numbers");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY HASHMAP APPROACH WORKS WITH NEGATIVE NUMBERS:");
        System.out.println("1. Negative numbers can create multiple paths to the same sum");
        System.out.println("2. HashMap tracks all possible prefix sums and their frequencies");
        System.out.println("3. The algorithm finds ALL subarrays, not just contiguous increasing ones");
        System.out.println("4. This makes it robust for any input including negatives and zeros");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Brute Force - show understanding of the problem");
        System.out.println("2. Optimize with Prefix Sum concept - demonstrate algorithmic thinking");
        System.out.println("3. Introduce HashMap optimization - provide optimal solution");
        System.out.println("4. Explain why Sliding Window doesn't work - show depth of understanding");
        System.out.println("5. Handle edge cases: negative numbers, zeros, single element");
        System.out.println("6. Discuss time/space trade-offs clearly");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
