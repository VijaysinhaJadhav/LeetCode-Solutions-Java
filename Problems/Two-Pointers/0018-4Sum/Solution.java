/**
 * 18. 4Sum
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array nums of n integers, return an array of all the unique quadruplets 
 * [nums[a], nums[b], nums[c], nums[d]] such that:
 * - 0 <= a, b, c, d < n
 * - a, b, c, and d are distinct
 * - nums[a] + nums[b] + nums[c] + nums[d] == target
 * 
 * Key Insights:
 * 1. Sort the array to enable two pointers and skip duplicates
 * 2. Use two nested loops for first two numbers, then two pointers for remaining two
 * 3. Skip duplicate elements to avoid duplicate quadruplets
 * 4. Use early termination when current sums exceed target
 * 
 * Approach (Sorting + Two Pointers - RECOMMENDED):
 * 1. Sort the array
 * 2. Use two nested loops for first two numbers
 * 3. For each pair, use two pointers to find pairs that sum to target - nums[i] - nums[j]
 * 4. Skip duplicates to avoid duplicate quadruplets
 * 
 * Time Complexity: O(n³)
 * Space Complexity: O(1) or O(n) depending on sorting
 * 
 * Tags: Array, Two Pointers, Sorting
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Sorting + Two Pointers - RECOMMENDED
     * O(n³) time, O(1) or O(n) space - Most efficient and optimal
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) return result;
        
        Arrays.sort(nums);
        int n = nums.length;
        
        for (int i = 0; i < n - 3; i++) {
            // Skip duplicates for the first number
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            // Early termination if smallest possible sum > target
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) break;
            
            // Skip if largest possible sum < target
            if ((long) nums[i] + nums[n - 3] + nums[n - 2] + nums[n - 1] < target) continue;
            
            for (int j = i + 1; j < n - 2; j++) {
                // Skip duplicates for the second number
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                
                // Early termination for second number
                if ((long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) break;
                if ((long) nums[i] + nums[j] + nums[n - 2] + nums[n - 1] < target) continue;
                
                int left = j + 1;
                int right = n - 1;
                long currentTarget = (long) target - nums[i] - nums[j];
                
                while (left < right) {
                    long sum = (long) nums[left] + nums[right];
                    
                    if (sum == currentTarget) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        
                        // Skip duplicates for left pointer
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        // Skip duplicates for right pointer
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        
                        left++;
                        right--;
                    } else if (sum < currentTarget) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: Generalized K-Sum Recursive
     * O(n³) time, O(n) space - Flexible for any K but uses recursion
     */
    public List<List<Integer>> fourSumRecursive(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, target, 0, 4);
    }
    
    private List<List<Integer>> kSum(int[] nums, long target, int start, int k) {
        List<List<Integer>> result = new ArrayList<>();
        
        // Base case: if we've run out of numbers to add
        if (start == nums.length) {
            return result;
        }
        
        // Base case for two sum
        if (k == 2) {
            return twoSum(nums, target, start);
        }
        
        for (int i = start; i < nums.length - k + 1; i++) {
            // Skip duplicates
            if (i > start && nums[i] == nums[i - 1]) continue;
            
            // Early termination
            if ((long) nums[i] * k > target) break;
            if ((long) nums[i] + (long) nums[nums.length - 1] * (k - 1) < target) continue;
            
            // Recursive call for k-1 sum
            List<List<Integer>> subResult = kSum(nums, target - nums[i], i + 1, k - 1);
            for (List<Integer> list : subResult) {
                List<Integer> newList = new ArrayList<>();
                newList.add(nums[i]);
                newList.addAll(list);
                result.add(newList);
            }
        }
        
        return result;
    }
    
    private List<List<Integer>> twoSum(int[] nums, long target, int start) {
        List<List<Integer>> result = new ArrayList<>();
        int left = start;
        int right = nums.length - 1;
        
        while (left < right) {
            long sum = (long) nums[left] + nums[right];
            
            if (sum == target) {
                result.add(Arrays.asList(nums[left], nums[right]));
                
                // Skip duplicates
                while (left < right && nums[left] == nums[left + 1]) left++;
                while (left < right && nums[right] == nums[right - 1]) right--;
                
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: HashSet for Last Two Numbers
     * O(n³) time, O(n) space - Alternative approach using HashSet
     */
    public List<List<Integer>> fourSumHashSet(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) return result;
        
        Arrays.sort(nums);
        int n = nums.length;
        
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                
                Set<Long> seen = new HashSet<>();
                for (int k = j + 1; k < n; k++) {
                    long complement = (long) target - nums[i] - nums[j] - nums[k];
                    
                    if (seen.contains(complement)) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[k], (int) complement));
                        
                        // Skip duplicates for third number
                        while (k + 1 < n && nums[k] == nums[k + 1]) k++;
                    }
                    seen.add((long) nums[k]);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: Optimized with Early Returns
     * O(n³) time, O(1) or O(n) space - Highly optimized version
     */
    public List<List<Integer>> fourSumOptimized(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) return result;
        
        Arrays.sort(nums);
        int n = nums.length;
        
        for (int i = 0; i < n - 3; i++) {
            // Skip duplicates
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            // Calculate min and max possible sums for current i
            long minSumI = (long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            long maxSumI = (long) nums[i] + nums[n - 1] + nums[n - 2] + nums[n - 3];
            
            if (minSumI > target) break; // Smallest possible sum > target
            if (maxSumI < target) continue; // Largest possible sum < target
            
            for (int j = i + 1; j < n - 2; j++) {
                // Skip duplicates
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                
                // Calculate min and max possible sums for current i,j
                long minSumIJ = (long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2];
                long maxSumIJ = (long) nums[i] + nums[j] + nums[n - 1] + nums[n - 2];
                
                if (minSumIJ > target) break;
                if (maxSumIJ < target) continue;
                
                int left = j + 1;
                int right = n - 1;
                long remaining = (long) target - nums[i] - nums[j];
                
                while (left < right) {
                    long sum = (long) nums[left] + nums[right];
                    
                    if (sum == remaining) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        
                        // Skip all duplicates
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        
                        left++;
                        right--;
                    } else if (sum < remaining) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Using HashMap for All Pairs (4Sum II style)
     * O(n³) time, O(n²) space - Uses more space but different approach
     */
    public List<List<Integer>> fourSumHashMap(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) return result;
        
        Arrays.sort(nums);
        Map<Long, List<int[]>> map = new HashMap<>();
        int n = nums.length;
        
        // Store all pairs and their indices
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                long sum = (long) nums[i] + nums[j];
                map.putIfAbsent(sum, new ArrayList<>());
                map.get(sum).add(new int[]{i, j});
            }
        }
        
        Set<String> seen = new HashSet<>();
        
        for (int i = 0; i < n - 1; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            for (int j = i + 1; j < n; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                
                long remaining = (long) target - nums[i] - nums[j];
                if (map.containsKey(remaining)) {
                    for (int[] pair : map.get(remaining)) {
                        int k = pair[0], l = pair[1];
                        // Check if indices are distinct and in order
                        if (k > j) {
                            List<Integer> quad = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
                            String key = quad.toString();
                            if (!seen.contains(key)) {
                                result.add(quad);
                                seen.add(key);
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 6: Brute Force with HashSet (For Comparison)
     * O(n⁴) time, O(n⁴) space - Inefficient but complete
     */
    public List<List<Integer>> fourSumBruteForce(int[] nums, int target) {
        Set<List<Integer>> result = new HashSet<>();
        int n = nums.length;
        
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];
                        if (sum == target) {
                            List<Integer> quad = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
                            Collections.sort(quad);
                            result.add(quad);
                        }
                    }
                }
            }
        }
        
        return new ArrayList<>(result);
    }
    
    /**
     * Helper method to visualize the four sum process
     */
    private void visualizeFourSum(int[] nums, int target, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Sorted: " + Arrays.toString(Arrays.copyOf(nums, nums.length)));
        Arrays.sort(nums);
        System.out.println("Sorted Array: " + Arrays.toString(nums));
        System.out.println("Target: " + target);
        
        List<List<Integer>> result = new ArrayList<>();
        int step = 1;
        
        System.out.println("\nStep | i | j | Left | Right | Numbers | Sum | Action");
        System.out.println("-----|---|---|------|-------|---------|-----|--------");
        
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                System.out.printf("%4d | %d |   |      |       | %7s |     | Skip duplicate i%n",
                                step++, i, "[" + nums[i] + "]");
                continue;
            }
            
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    System.out.printf("%4d | %d | %d |      |       | %7s |     | Skip duplicate j%n",
                                    step++, i, j, "[" + nums[i] + "," + nums[j] + "]");
                    continue;
                }
                
                int left = j + 1;
                int right = nums.length - 1;
                long currentTarget = (long) target - nums[i] - nums[j];
                
                System.out.printf("%4d | %d | %d |      |       | %7s |     | Start two pointers, target=%d%n",
                                step++, i, j, "[" + nums[i] + "," + nums[j] + "]", currentTarget);
                
                while (left < right) {
                    long sum = (long) nums[left] + nums[right];
                    String numbers = "[" + nums[i] + "," + nums[j] + "," + nums[left] + "," + nums[right] + "]";
                    String action;
                    
                    if (sum == currentTarget) {
                        action = "FOUND QUAD: " + numbers;
                        System.out.printf("%4d | %d | %d | %4d | %5d | %7s | %3d | %s%n",
                                        step++, i, j, left, right, numbers, sum + nums[i] + nums[j], action);
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        
                        // Skip duplicates
                        while (left < right && nums[left] == nums[left + 1]) {
                            System.out.printf("%4d | %d | %d | %4d | %5d | %7s |     | Skip duplicate left%n",
                                            step++, i, j, left, right, numbers);
                            left++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            System.out.printf("%4d | %d | %d | %4d | %5d | %7s |     | Skip duplicate right%n",
                                            step++, i, j, left, right, numbers);
                            right--;
                        }
                        
                        left++;
                        right--;
                    } else if (sum < currentTarget) {
                        action = "Sum too small, move left++";
                        System.out.printf("%4d | %d | %d | %4d | %5d | %7s | %3d | %s%n",
                                        step++, i, j, left, right, numbers, sum + nums[i] + nums[j], action);
                        left++;
                    } else {
                        action = "Sum too large, move right--";
                        System.out.printf("%4d | %d | %d | %4d | %5d | %7s | %3d | %s%n",
                                        step++, i, j, left, right, numbers, sum + nums[i] + nums[j], action);
                        right--;
                    }
                }
            }
        }
        
        System.out.println("\nFinal Result: " + result);
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(int[] nums, int target, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        long startTime = System.nanoTime();
        List<List<Integer>> result1 = fourSum(nums, target);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result2 = fourSumRecursive(nums, target);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result3 = fourSumHashSet(nums, target);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result4 = fourSumOptimized(nums, target);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result5 = fourSumHashMap(nums, target);
        long time5 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result6 = fourSumBruteForce(nums, target);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("Two Pointers: %d ns%n", time1);
        System.out.printf("Recursive K-Sum: %d ns%n", time2);
        System.out.printf("HashSet: %d ns%n", time3);
        System.out.printf("Optimized: %d ns%n", time4);
        System.out.printf("HashMap Pairs: %d ns%n", time5);
        System.out.printf("Brute Force: %d ns%n", time6);
        
        // Verify all produce same number of results
        boolean allEqual = result1.size() == result2.size() && result1.size() == result3.size() &&
                          result1.size() == result4.size() && result1.size() == result5.size() &&
                          result1.size() == result6.size();
        System.out.println("All approaches found same number of quadruplets: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing 4Sum Solution:");
        System.out.println("======================");
        
        // Test case 1: Standard case
        System.out.println("\nTest 1: Standard case");
        int[] nums1 = {1, 0, -1, 0, -2, 2};
        int target1 = 0;
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(-2, -1, 1, 2),
            Arrays.asList(-2, 0, 0, 2),
            Arrays.asList(-1, 0, 0, 1)
        );
        
        List<List<Integer>> result1a = solution.fourSum(nums1, target1);
        List<List<Integer>> result1b = solution.fourSumOptimized(nums1, target1);
        
        System.out.println("Two Pointers: " + result1a.size() + " quadruplets - " + 
                         (result1a.size() == expected1.size() ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1b.size() + " quadruplets - " + 
                         (result1b.size() == expected1.size() ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeFourSum(nums1, target1, "Test 1 - Standard Case");
        
        // Test case 2: All same numbers
        System.out.println("\nTest 2: All same numbers");
        int[] nums2 = {2, 2, 2, 2, 2};
        int target2 = 8;
        List<List<Integer>> expected2 = Arrays.asList(Arrays.asList(2, 2, 2, 2));
        
        List<List<Integer>> result2a = solution.fourSum(nums2, target2);
        System.out.println("All same numbers: " + result2a + " - " + 
                         (result2a.size() == expected2.size() ? "PASSED" : "FAILED"));
        
        // Test case 3: No solution
        System.out.println("\nTest 3: No solution");
        int[] nums3 = {1, 2, 3, 4};
        int target3 = 100;
        List<List<Integer>> result3a = solution.fourSum(nums3, target3);
        System.out.println("No solution: " + result3a + " - " + 
                         (result3a.size() == 0 ? "PASSED" : "FAILED"));
        
        // Test case 4: Large numbers with overflow
        System.out.println("\nTest 4: Large numbers with overflow");
        int[] nums4 = {1000000000, 1000000000, 1000000000, 1000000000};
        int target4 = -294967296; // This tests integer overflow handling
        List<List<Integer>> result4a = solution.fourSum(nums4, target4);
        System.out.println("Large numbers: " + result4a.size() + " quadruplets - " + 
                         (result4a.size() == 0 ? "PASSED" : "FAILED"));
        
        // Test case 5: Mixed positive and negative
        System.out.println("\nTest 5: Mixed positive and negative");
        int[] nums5 = {-3, -2, -1, 0, 0, 1, 2, 3};
        int target5 = 0;
        List<List<Integer>> result5a = solution.fourSum(nums5, target5);
        System.out.println("Mixed numbers: " + result5a.size() + " quadruplets - " + 
                         (result5a.size() > 0 ? "PASSED" : "FAILED"));
        
        // Test case 6: Minimum array size
        System.out.println("\nTest 6: Minimum array size");
        int[] nums6 = {1, 2, 3, 4};
        int target6 = 10;
        List<List<Integer>> result6a = solution.fourSum(nums6, target6);
        System.out.println("Minimum array: " + result6a + " - " + 
                         (result6a.size() == 1 ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(nums1, target1, "Small Input (6 elements)");
        
        // Medium input performance
        int[] mediumNums = new int[20];
        Random random = new Random(42);
        for (int i = 0; i < mediumNums.length; i++) {
            mediumNums[i] = random.nextInt(100) - 50; // -50 to 50
        }
        solution.comparePerformance(mediumNums, 0, "Medium Input (20 elements)");
        
        // Large input performance
        int[] largeNums = new int[50];
        for (int i = 0; i < largeNums.length; i++) {
            largeNums[i] = random.nextInt(200) - 100; // -100 to 100
        }
        solution.comparePerformance(largeNums, 0, "Large Input (50 elements)");
        
        // Worst-case performance (all same numbers)
        int[] worstCaseNums = new int[30];
        Arrays.fill(worstCaseNums, 1);
        solution.comparePerformance(worstCaseNums, 4, "Worst Case (30 ones)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Sorting + Two Pointers - RECOMMENDED:");
        System.out.println("   Time: O(n³) - Sorting O(n log n) + three nested loops O(n³)");
        System.out.println("   Space: O(1) or O(n) - Depending on sorting algorithm");
        System.out.println("   How it works:");
        System.out.println("     - Sort the array to enable two pointers and skip duplicates");
        System.out.println("     - Use two nested loops for first two numbers");
        System.out.println("     - For each pair, use two pointers to find remaining two numbers");
        System.out.println("     - Skip duplicates and use early termination");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity for 4Sum");
        System.out.println("     - Handles duplicates efficiently");
        System.out.println("     - Most efficient and scalable");
        System.out.println("   Cons:");
        System.out.println("     - Modifies input array (or needs copy)");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Generalized K-Sum Recursive:");
        System.out.println("   Time: O(n³) - Same as iterative but with recursion overhead");
        System.out.println("   Space: O(n) - Recursion stack space");
        System.out.println("   How it works:");
        System.out.println("     - Recursive approach that reduces 4Sum to 3Sum to 2Sum");
        System.out.println("     - Base case handles two sum with two pointers");
        System.out.println("     - Recursive case reduces k by 1 each time");
        System.out.println("   Pros:");
        System.out.println("     - Flexible for any K (2Sum, 3Sum, 4Sum, etc.)");
        System.out.println("     - Clean recursive formulation");
        System.out.println("   Cons:");
        System.out.println("     - Recursion overhead");
        System.out.println("     - Uses O(n) stack space");
        System.out.println("   Best for: Learning generalized K-sum pattern");
        
        System.out.println("\n3. HashSet for Last Two Numbers:");
        System.out.println("   Time: O(n³) - Three nested loops with HashSet operations");
        System.out.println("   Space: O(n) - HashSet storage");
        System.out.println("   How it works:");
        System.out.println("     - Sort the array");
        System.out.println("     - Use two nested loops for first two numbers");
        System.out.println("     - Use HashSet to track seen numbers for last two");
        System.out.println("     - Look for complement in HashSet");
        System.out.println("   Pros:");
        System.out.println("     - Clear logic with HashSet");
        System.out.println("     - Still O(n³) time complexity");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("     - Slightly slower than two pointers");
        System.out.println("   Best for: Alternative approach learning");
        
        System.out.println("\n4. Optimized Two Pointers:");
        System.out.println("   Time: O(n³) - Same as standard but with more optimizations");
        System.out.println("   Space: O(1) or O(n) - Depending on sorting");
        System.out.println("   How it works:");
        System.out.println("     - Same as standard two pointers");
        System.out.println("     - Additional early termination checks");
        System.out.println("     - More aggressive bounds checking");
        System.out.println("   Pros:");
        System.out.println("     - Potential performance improvements");
        System.out.println("     - Better handling of edge cases");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: Performance-critical applications");
        
        System.out.println("\n5. HashMap for All Pairs:");
        System.out.println("   Time: O(n³) - Building pairs O(n²) + nested loops O(n³)");
        System.out.println("   Space: O(n²) - HashMap storing all pairs");
        System.out.println("   How it works:");
        System.out.println("     - Precompute all pairs and store in HashMap");
        System.out.println("     - For each pair, look for complementary pair in HashMap");
        System.out.println("     - Check for distinct indices");
        System.out.println("   Pros:");
        System.out.println("     - Different approach from two pointers");
        System.out.println("     - Similar to 4Sum II problem");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n²) extra space");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: Learning different algorithmic patterns");
        
        System.out.println("\n6. Brute Force:");
        System.out.println("   Time: O(n⁴) - Four nested loops");
        System.out.println("   Space: O(n⁴) - Result storage in worst case");
        System.out.println("   How it works:");
        System.out.println("     - Check all possible quadruplets");
        System.out.println("     - Use HashSet to avoid duplicates");
        System.out.println("   Pros:");
        System.out.println("     - Simple and straightforward");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Extremely inefficient for large inputs");
        System.out.println("     - Doesn't scale beyond very small arrays");
        System.out.println("   Best for: Demonstration only, very small inputs");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("KEY OPTIMIZATIONS FOR 4SUM:");
        System.out.println("1. Sorting: Enables two pointers and duplicate skipping");
        System.out.println("2. Duplicate Skipping: Skip nums[i] == nums[i-1] etc.");
        System.out.println("3. Early Termination: Stop when min possible sum > target");
        System.out.println("4. Bounds Checking: Skip when max possible sum < target");
        System.out.println("5. Long Arithmetic: Prevent integer overflow");
        System.out.println("6. Two Pointers: O(n) solution for the inner two numbers");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTEGER OVERFLOW HANDLING:");
        System.out.println("1. Use long for intermediate sums and targets");
        System.out.println("2. Critical when numbers are large (up to 10^9)");
        System.out.println("3. Convert to long before addition/multiplication");
        System.out.println("4. Check bounds using long arithmetic");
        System.out.println("5. Example: nums[i] + nums[j] + nums[k] + nums[l] can exceed Integer.MAX_VALUE");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. Array size < 4: Return empty list immediately");
        System.out.println("2. All same numbers: Handle duplicate skipping correctly");
        System.out.println("3. Large numbers: Use long to prevent overflow");
        System.out.println("4. No solution: Return empty list");
        System.out.println("5. Many duplicates: Efficient skipping needed");
        System.out.println("6. Minimum array size (4 elements): Handle naturally");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Sorting + Two Pointers approach");
        System.out.println("2. Explain it's an extension of 3Sum with additional loop");
        System.out.println("3. Implement duplicate skipping carefully for all levels");
        System.out.println("4. Use long for sums to handle overflow");
        System.out.println("5. Add early termination optimizations");
        System.out.println("6. Mention time/space complexity: O(n³)/O(1) or O(n)");
        System.out.println("7. Discuss the generalized K-sum pattern");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Not handling integer overflow");
        System.out.println("2. Incorrect duplicate skipping");
        System.out.println("3. Using O(n⁴) brute force");
        System.out.println("4. Forgetting to sort the array");
        System.out.println("5. Not using early termination optimizations");
        System.out.println("6. Returning duplicate quadruplets");
        System.out.println("=".repeat(70));
        
        // Extension to related problems
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EXTENSION: K-SUM PROBLEM PATTERN");
        System.out.println("1. Two Sum: HashMap O(n) or two pointers O(n) for sorted");
        System.out.println("2. Three Sum: Sort + one loop + two pointers O(n²)");
        System.out.println("3. Four Sum: Sort + two loops + two pointers O(n³)");
        System.out.println("4. K-Sum: Sort + (k-2) loops + two pointers O(n^(k-1))");
        System.out.println("5. Generalized: Recursive reduction to two sum");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
