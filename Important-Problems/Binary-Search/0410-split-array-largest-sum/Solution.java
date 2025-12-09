
## Solution.java

```java
/**
 * 410. Split Array Largest Sum
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given an integer array nums and an integer k, split nums into k non-empty subarrays 
 * such that the largest sum of any subarray is minimized.
 * Return the minimized largest sum of the split.
 * 
 * Key Insights:
 * 1. Use binary search to find the minimum possible maximum sum
 * 2. Search space: [max_element, total_sum]
 * 3. For each candidate sum, check if we can split into <= k subarrays
 * 4. If feasible, try smaller sum; else try larger sum
 * 
 * Approach (Binary Search with Greedy Validation):
 * 1. Find max element and total sum to define search space
 * 2. Use binary search to find minimum feasible sum
 * 3. For each candidate, use greedy approach to count required subarrays
 * 4. Return the smallest feasible sum
 * 
 * Time Complexity: O(n log S) where S is total sum
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search, Dynamic Programming, Greedy
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Binary Search with Greedy Validation - RECOMMENDED
     * O(n log S) time, O(1) space
     */
    public int splitArray(int[] nums, int k) {
        // Find the maximum element and total sum to define search space
        int maxElement = 0;
        int totalSum = 0;
        for (int num : nums) {
            maxElement = Math.max(maxElement, num);
            totalSum += num;
        }
        
        // Binary search for the minimum possible maximum sum
        int left = maxElement;  // Each subarray must contain at least one element
        int right = totalSum;   // All elements in one subarray
        int result = right;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (isFeasible(nums, k, mid)) {
                result = mid;
                right = mid - 1; // Try for smaller maximum sum
            } else {
                left = mid + 1;  // Need larger maximum sum
            }
        }
        
        return result;
    }
    
    private boolean isFeasible(int[] nums, int k, int maxSum) {
        int subarrays = 1; // Start with one subarray
        int currentSum = 0;
        
        for (int num : nums) {
            // If adding this element would exceed maxSum, start new subarray
            if (currentSum + num > maxSum) {
                subarrays++;
                currentSum = num;
                // If we need more than k subarrays, it's not feasible
                if (subarrays > k) {
                    return false;
                }
            } else {
                currentSum += num;
            }
        }
        
        return true;
    }
    
    /**
     * Approach 2: Dynamic Programming
     * O(n^2 * k) time, O(n * k) space
     */
    public int splitArrayDP(int[] nums, int k) {
        int n = nums.length;
        
        // prefixSum[i] = sum of first i elements
        int[] prefixSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }
        
        // dp[i][j] = minimum largest sum splitting first i elements into j parts
        int[][] dp = new int[n + 1][k + 1];
        
        // Initialize with large values
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        
        // Base cases
        dp[0][0] = 0;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                // Try all possible splits for the last subarray
                for (int p = 0; p < i; p++) {
                    if (dp[p][j - 1] != Integer.MAX_VALUE) {
                        int currentSum = prefixSum[i] - prefixSum[p];
                        dp[i][j] = Math.min(dp[i][j], Math.max(dp[p][j - 1], currentSum));
                    }
                }
            }
        }
        
        return dp[n][k];
    }
    
    /**
     * Approach 3: Optimized Dynamic Programming with Prefix Sum
     * O(n^2 * k) time, O(n * k) space with optimizations
     */
    public int splitArrayDPOptimized(int[] nums, int k) {
        int n = nums.length;
        
        // prefixSum[i] = sum of first i elements
        int[] prefixSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }
        
        // dp[i][j] = minimum largest sum splitting first i elements into j parts
        int[][] dp = new int[n + 1][k + 1];
        
        // Initialize
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                // For j = 1, the sum is just prefixSum[i]
                if (j == 1) {
                    dp[i][j] = prefixSum[i];
                    continue;
                }
                
                // Try all possible splits for the last subarray
                // We can optimize by starting from j-1 since we need at least j-1 elements for j-1 subarrays
                for (int p = j - 1; p < i; p++) {
                    int currentSum = prefixSum[i] - prefixSum[p];
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[p][j - 1], currentSum));
                }
            }
        }
        
        return dp[n][k];
    }
    
    /**
     * Approach 4: DFS with Memoization
     * O(n^2 * k) time, O(n * k) space
     */
    public int splitArrayDFS(int[] nums, int k) {
        int n = nums.length;
        
        // prefixSum[i] = sum of first i elements
        int[] prefixSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }
        
        int[][] memo = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(memo[i], -1);
        }
        
        return dfs(nums, prefixSum, 0, k, memo);
    }
    
    private int dfs(int[] nums, int[] prefixSum, int start, int k, int[][] memo) {
        if (k == 1) {
            return prefixSum[nums.length] - prefixSum[start];
        }
        
        if (memo[start][k] != -1) {
            return memo[start][k];
        }
        
        int result = Integer.MAX_VALUE;
        
        // Try all possible splits
        for (int i = start; i <= nums.length - k; i++) {
            int currentSum = prefixSum[i + 1] - prefixSum[start];
            int maxSum = Math.max(currentSum, dfs(nums, prefixSum, i + 1, k - 1, memo));
            result = Math.min(result, maxSum);
        }
        
        memo[start][k] = result;
        return result;
    }
    
    /**
     * Approach 5: Binary Search with Detailed Validation
     * O(n log S) time, O(1) space with detailed output
     */
    public int splitArrayDetailed(int[] nums, int k) {
        int maxElement = 0;
        int totalSum = 0;
        for (int num : nums) {
            maxElement = Math.max(maxElement, num);
            totalSum += num;
        }
        
        int left = maxElement;
        int right = totalSum;
        int result = right;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (isFeasibleDetailed(nums, k, mid)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    private boolean isFeasibleDetailed(int[] nums, int k, int maxSum) {
        int subarrays = 1;
        int currentSum = 0;
        
        for (int i = 0; i < nums.length; i++) {
            // If current element itself is greater than maxSum, impossible
            if (nums[i] > maxSum) {
                return false;
            }
            
            if (currentSum + nums[i] <= maxSum) {
                currentSum += nums[i];
            } else {
                subarrays++;
                currentSum = nums[i];
                if (subarrays > k) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    public void visualizeSplit(int[] nums, int k) {
        System.out.println("\nSplit Array Largest Sum Visualization:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("k = " + k);
        
        int maxElement = 0;
        int totalSum = 0;
        for (int num : nums) {
            maxElement = Math.max(maxElement, num);
            totalSum += num;
        }
        
        System.out.println("Search space: [" + maxElement + ", " + totalSum + "]");
        System.out.println("Binary Search Process:");
        
        int left = maxElement;
        int right = totalSum;
        int result = right;
        
        int step = 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            boolean feasible = isFeasible(nums, k, mid);
            
            System.out.println("Step " + step + ":");
            System.out.println("  left = " + left + ", right = " + right + ", mid = " + mid);
            System.out.println("  Can split with max sum " + mid + "? " + feasible);
            
            if (feasible) {
                result = mid;
                right = mid - 1;
                System.out.println("  ✓ Feasible, try smaller sum");
            } else {
                left = mid + 1;
                System.out.println("  ✗ Not feasible, try larger sum");
            }
            step++;
        }
        
        System.out.println("\nFinal result: " + result);
        
        // Show the actual split
        showOptimalSplit(nums, k, result);
    }
    
    private void showOptimalSplit(int[] nums, int k, int maxSum) {
        System.out.println("\nOptimal Split (max sum = " + maxSum + "):");
        
        List<List<Integer>> subarrays = new ArrayList<>();
        List<Integer> currentSubarray = new ArrayList<>();
        int currentSum = 0;
        
        for (int num : nums) {
            if (currentSum + num <= maxSum) {
                currentSubarray.add(num);
                currentSum += num;
            } else {
                subarrays.add(new ArrayList<>(currentSubarray));
                currentSubarray = new ArrayList<>();
                currentSubarray.add(num);
                currentSum = num;
            }
        }
        subarrays.add(currentSubarray); // Add the last subarray
        
        // Verify we have exactly k subarrays (or adjust if needed)
        while (subarrays.size() > k) {
            // Merge the smallest adjacent subarrays
            int minMergeIndex = findMinMergeIndex(subarrays);
            List<Integer> merged = new ArrayList<>(subarrays.get(minMergeIndex));
            merged.addAll(subarrays.get(minMergeIndex + 1));
            subarrays.set(minMergeIndex, merged);
            subarrays.remove(minMergeIndex + 1);
        }
        
        int maxSubarraySum = 0;
        for (int i = 0; i < subarrays.size(); i++) {
            int sum = subarrays.get(i).stream().mapToInt(Integer::intValue).sum();
            maxSubarraySum = Math.max(maxSubarraySum, sum);
            System.out.println("Subarray " + (i + 1) + ": " + subarrays.get(i) + " (sum = " + sum + ")");
        }
        
        System.out.println("Maximum subarray sum: " + maxSubarraySum);
        
        // Verify constraints
        if (subarrays.size() != k) {
            System.out.println("WARNING: Expected " + k + " subarrays, got " + subarrays.size());
        }
        if (maxSubarraySum > maxSum) {
            System.out.println("WARNING: Maximum sum exceeds target!");
        }
    }
    
    private int findMinMergeIndex(List<List<Integer>> subarrays) {
        int minSum = Integer.MAX_VALUE;
        int minIndex = 0;
        
        for (int i = 0; i < subarrays.size() - 1; i++) {
            int sum1 = subarrays.get(i).stream().mapToInt(Integer::intValue).sum();
            int sum2 = subarrays.get(i + 1).stream().mapToInt(Integer::intValue).sum();
            if (sum1 + sum2 < minSum) {
                minSum = sum1 + sum2;
                minIndex = i;
            }
        }
        
        return minIndex;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Split Array Largest Sum:");
        System.out.println("================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: [7,2,5,10,8], k = 2");
        int[] nums1 = {7, 2, 5, 10, 8};
        int k1 = 2;
        int expected1 = 18;
        
        long startTime = System.nanoTime();
        int result1a = solution.splitArray(nums1, k1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.splitArrayDP(nums1, k1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.splitArrayDPOptimized(nums1, k1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.splitArrayDFS(nums1, k1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.splitArrayDetailed(nums1, k1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("Binary Search: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DP:            " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("DP Optimized:  " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("DFS:           " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Detailed:      " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the binary search process
        solution.visualizeSplit(nums1, k1);
        
        // Test case 2: All elements same
        System.out.println("\nTest 2: [1,1,1,1], k = 2");
        int[] nums2 = {1, 1, 1, 1};
        int k2 = 2;
        int result2a = solution.splitArray(nums2, k2);
        System.out.println("All same: " + result2a + " - " + 
                         (result2a == 2 ? "PASSED" : "FAILED"));
        
        // Test case 3: k equals array length
        System.out.println("\nTest 3: [1,2,3,4,5], k = 5");
        int[] nums3 = {1, 2, 3, 4, 5};
        int k3 = 5;
        int result3a = solution.splitArray(nums3, k3);
        System.out.println("k = n: " + result3a + " - " + 
                         (result3a == 5 ? "PASSED" : "FAILED"));
        
        // Test case 4: k = 1
        System.out.println("\nTest 4: [1,2,3,4,5], k = 1");
        int[] nums4 = {1, 2, 3, 4, 5};
        int k4 = 1;
        int result4a = solution.splitArray(nums4, k4);
        System.out.println("k = 1: " + result4a + " - " + 
                         (result4a == 15 ? "PASSED" : "FAILED"));
        
        // Test case 5: Complex case
        System.out.println("\nTest 5: [1,4,4], k = 3");
        int[] nums5 = {1, 4, 4};
        int k5 = 3;
        int result5a = solution.splitArray(nums5, k5);
        System.out.println("Complex: " + result5a + " - " + 
                         (result5a == 4 ? "PASSED" : "FAILED"));
        
        // Test case 6: Large numbers
        System.out.println("\nTest 6: [10,20,30,40], k = 2");
        int[] nums6 = {10, 20, 30, 40};
        int k6 = 2;
        int result6a = solution.splitArray(nums6, k6);
        System.out.println("Large numbers: " + result6a + " - " + 
                         (result6a == 60 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("Binary Search: " + time1a + " ns");
        System.out.println("DP:            " + time1b + " ns");
        System.out.println("DP Optimized:  " + time1c + " ns");
        System.out.println("DFS:           " + time1d + " ns");
        System.out.println("Detailed:      " + time1e + " ns");
        
        // Test all approaches produce same results
        System.out.println("\nTest 8: All approaches consistency");
        boolean allConsistent = result1a == result1b && 
                              result1a == result1c && 
                              result1a == result1d &&
                              result1a == result1e;
        System.out.println("All approaches produce same results: " + allConsistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BINARY SEARCH APPROACH EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We can use binary search to find the minimum possible maximum sum.");
        System.out.println("If we can split the array into k subarrays with maximum sum X,");
        System.out.println("then we can also split it with any maximum sum > X.");
        
        System.out.println("\nSearch Space:");
        System.out.println("Lower bound: max element (each subarray must contain at least one element)");
        System.out.println("Upper bound: total sum (all elements in one subarray)");
        
        System.out.println("\nFeasibility Check:");
        System.out.println("For a candidate sum S, we greedily add elements to current subarray");
        System.out.println("until adding the next element would exceed S. Then we start a new subarray.");
        System.out.println("If we need more than k subarrays, S is not feasible.");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Binary Search (RECOMMENDED):");
        System.out.println("   Time: O(n log S) - n elements, S = total sum");
        System.out.println("   Space: O(1) - Only a few variables");
        System.out.println("   How it works:");
        System.out.println("     - Binary search for minimum feasible maximum sum");
        System.out.println("     - Greedy validation to check feasibility");
        System.out.println("     - Optimal for given constraints");
        System.out.println("   Pros:");
        System.out.println("     - Efficient time complexity");
        System.out.println("     - Minimal space usage");
        System.out.println("     - Easy to implement");
        System.out.println("   Cons:");
        System.out.println("     - Doesn't give the actual split (only the value)");
        System.out.println("   Best for: Most use cases, large inputs");
        
        System.out.println("\n2. Dynamic Programming:");
        System.out.println("   Time: O(n² * k) - Three nested loops");
        System.out.println("   Space: O(n * k) - DP table");
        System.out.println("   How it works:");
        System.out.println("     - dp[i][j] = min max sum for first i elements into j parts");
        System.out.println("     - Try all possible splits for last subarray");
        System.out.println("   Pros:");
        System.out.println("     - Gives exact solution");
        System.out.println("     - Can reconstruct the actual split");
        System.out.println("   Cons:");
        System.out.println("     - High time and space complexity");
        System.out.println("     - Not scalable for large n");
        System.out.println("   Best for: Small inputs, when split reconstruction needed");
        
        System.out.println("\n3. DFS with Memoization:");
        System.out.println("   Time: O(n² * k) - With memoization");
        System.out.println("   Space: O(n * k) - Memoization table + recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Top-down recursive approach");
        System.out.println("     - Memoize computed results");
        System.out.println("     - Try all possible first subarray lengths");
        System.out.println("   Pros:");
        System.out.println("     - Natural recursive thinking");
        System.out.println("     - Easy to understand the choices");
        System.out.println("   Cons:");
        System.out.println("     - Recursion overhead");
        System.out.println("     - Not efficient for large inputs");
        System.out.println("   Best for: Learning, small inputs");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY BINARY SEARCH WORKS:");
        System.out.println("1. Monotonicity: If sum S is feasible, all sums > S are feasible");
        System.out.println("2. If sum S is not feasible, all sums < S are not feasible");
        System.out.println("3. This monotonic property enables binary search");
        System.out.println("4. The feasibility check is efficient (O(n))");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with binary search approach - it's the expected solution");
        System.out.println("2. Explain the search space and feasibility check clearly");
        System.out.println("3. Handle edge cases (k=1, k=n, single element)");
        System.out.println("4. Discuss time/space complexity");
        System.out.println("5. Mention DP approach as alternative for smaller constraints");
        System.out.println("6. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        // Additional test patterns
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ADDITIONAL TEST PATTERNS:");
        System.out.println("=".repeat(70));
        
        int[][][] testCases = {
            {{1, 2, 3, 4, 5, 6, 7, 8, 9}, 3}, // Expected: 17
            {{10, 5, 13, 4, 8, 4, 5, 11, 14, 9, 16, 10, 20, 8}, 8}, // Expected: 25
            {{2, 3, 1, 2, 4, 3}, 5}, // Expected: 4
            {{1, 2, 3, 4}, 3}, // Expected: 4
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i][0];
            int k = testCases[i][1];
            int result = solution.splitArray(nums, k);
            System.out.printf("Test %d: k=%d -> %d%n",
                            i + 1, k, result);
        }
        
        System.out.println("\nAll tests completed!");
    }
}
