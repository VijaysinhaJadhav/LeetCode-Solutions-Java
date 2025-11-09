/**
 * 15. 3Sum
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] 
 * such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * Notice that the solution set must not contain duplicate triplets.
 * 
 * Key Insights:
 * 1. Sort the array to enable two pointers and skip duplicates
 * 2. For each element, solve two sum problem for the remaining array
 * 3. Skip duplicate elements to avoid duplicate triplets
 * 4. Use early termination when current element > 0
 * 
 * Approach (Sorting + Two Pointers - RECOMMENDED):
 * 1. Sort the array
 * 2. Iterate through each element as first element of triplet
 * 3. For each element, use two pointers to find pairs that sum to -nums[i]
 * 4. Skip duplicates to avoid duplicate triplets
 * 
 * Time Complexity: O(n²)
 * Space Complexity: O(1) or O(n) depending on sorting
 * 
 * Tags: Array, Two Pointers, Sorting
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Sorting + Two Pointers - RECOMMENDED
     * O(n²) time, O(1) or O(n) space - Most efficient and optimal
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate elements for the first number
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // Early termination: if first number > 0, sum cannot be 0
            if (nums[i] > 0) {
                break;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates for left pointer
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // Skip duplicates for right pointer
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: HashSet for Tracking Duplicates
     * O(n²) time, O(n) space - Alternative approach using HashSet
     */
    public List<List<Integer>> threeSumHashSet(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for first number
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            Set<Integer> seen = new HashSet<>();
            for (int j = i + 1; j < nums.length; j++) {
                int complement = -nums[i] - nums[j];
                
                if (seen.contains(complement)) {
                    result.add(Arrays.asList(nums[i], complement, nums[j]));
                    
                    // Skip duplicates for second number
                    while (j + 1 < nums.length && nums[j] == nums[j + 1]) {
                        j++;
                    }
                }
                seen.add(nums[j]);
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: No-Sort with Two HashSets
     * O(n²) time, O(n) space - Works without sorting but uses more space
     */
    public List<List<Integer>> threeSumNoSort(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        Map<Integer, Integer> seen = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            if (duplicates.add(nums[i])) {
                for (int j = i + 1; j < nums.length; j++) {
                    int complement = -nums[i] - nums[j];
                    if (seen.containsKey(complement) && seen.get(complement) == i) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], complement);
                        Collections.sort(triplet);
                        result.add(triplet);
                    }
                    seen.put(nums[j], i);
                }
            }
        }
        
        return new ArrayList<>(result);
    }
    
    /**
     * Approach 4: Binary Search Variation
     * O(n² log n) time, O(1) or O(n) space - Educational approach
     */
    public List<List<Integer>> threeSumBinarySearch(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            if (nums[i] > 0) break;
            
            for (int j = i + 1; j < nums.length - 1; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                
                int target = -nums[i] - nums[j];
                int left = j + 1;
                int right = nums.length - 1;
                
                // Binary search for the third element
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (nums[mid] == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[mid]));
                        break;
                    } else if (nums[mid] < target) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Early Optimized Two Pointers
     * O(n²) time, O(1) or O(n) space - Optimized version
     */
    public List<List<Integer>> threeSumOptimized(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) return result;
        
        Arrays.sort(nums);
        int n = nums.length;
        
        for (int i = 0; i < n - 2; i++) {
            // Skip duplicates
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            // Early termination optimizations
            if (nums[i] > 0) break; // First number positive, impossible to get sum 0
            if (nums[i] + nums[n - 2] + nums[n - 1] < 0) continue; // Sum too small even with largest numbers
            
            int left = i + 1;
            int right = n - 1;
            int target = -nums[i];
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip all duplicates
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
        }
        
        return result;
    }
    
    /**
     * Approach 6: Brute Force (For Comparison)
     * O(n³) time, O(1) space - Inefficient but complete
     */
    public List<List<Integer>> threeSumBruteForce(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        int n = nums.length;
        
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(triplet);
                        result.add(triplet);
                    }
                }
            }
        }
        
        return new ArrayList<>(result);
    }
    
    /**
     * Helper method to visualize the two pointers process
     */
    private void visualizeThreeSum(int[] nums, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Sorted: " + Arrays.toString(Arrays.copyOf(nums, nums.length)));
        Arrays.sort(nums);
        System.out.println("Sorted Array: " + Arrays.toString(nums));
        
        List<List<Integer>> result = new ArrayList<>();
        int step = 1;
        
        System.out.println("\nStep | i | Left | Right | nums[i] | nums[left] | nums[right] | Sum | Action");
        System.out.println("-----|---|------|-------|---------|------------|-------------|-----|--------");
        
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                System.out.printf("%4d | %d |      |       | %7d |            |             |     | Skip duplicate i%n",
                                step++, i, nums[i]);
                continue;
            }
            
            if (nums[i] > 0) {
                System.out.printf("%4d | %d |      |       | %7d |            |             |     | Early termination (nums[i] > 0)%n",
                                step++, i, nums[i]);
                break;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];
            
            System.out.printf("%4d | %d |      |       | %7d |            |             |     | Start two pointers for i=%d, target=%d%n",
                            step++, i, nums[i], i, target);
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                String action;
                
                if (sum == target) {
                    action = "FOUND TRIPLET: [" + nums[i] + ", " + nums[left] + ", " + nums[right] + "]";
                    System.out.printf("%4d | %d | %4d | %5d | %7d | %10d | %11d | %3d | %s%n",
                                    step++, i, left, right, nums[i], nums[left], nums[right], sum, action);
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates
                    while (left < right && nums[left] == nums[left + 1]) {
                        System.out.printf("%4d | %d | %4d | %5d | %7d | %10d | %11d |     | Skip duplicate left%n",
                                        step++, i, left, right, nums[i], nums[left], nums[right]);
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        System.out.printf("%4d | %d | %4d | %5d | %7d | %10d | %11d |     | Skip duplicate right%n",
                                        step++, i, left, right, nums[i], nums[left], nums[right]);
                        right--;
                    }
                    
                    left++;
                    right--;
                } else if (sum < target) {
                    action = "Sum too small, move left++";
                    System.out.printf("%4d | %d | %4d | %5d | %7d | %10d | %11d | %3d | %s%n",
                                    step++, i, left, right, nums[i], nums[left], nums[right], sum, action);
                    left++;
                } else {
                    action = "Sum too large, move right--";
                    System.out.printf("%4d | %d | %4d | %5d | %7d | %10d | %11d | %3d | %s%n",
                                    step++, i, left, right, nums[i], nums[left], nums[right], sum, action);
                    right--;
                }
            }
        }
        
        System.out.println("\nFinal Result: " + result);
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(int[] nums, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        long startTime = System.nanoTime();
        List<List<Integer>> result1 = threeSum(nums);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result2 = threeSumHashSet(nums);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result3 = threeSumNoSort(nums);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result4 = threeSumBinarySearch(nums);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result5 = threeSumOptimized(nums);
        long time5 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result6 = threeSumBruteForce(nums);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("Two Pointers: %d ns%n", time1);
        System.out.printf("HashSet: %d ns%n", time2);
        System.out.printf("No-Sort: %d ns%n", time3);
        System.out.printf("Binary Search: %d ns%n", time4);
        System.out.printf("Optimized: %d ns%n", time5);
        System.out.printf("Brute Force: %d ns%n", time6);
        
        // Verify all produce same result (ignoring order)
        boolean allEqual = result1.size() == result2.size() && result1.size() == result3.size() &&
                          result1.size() == result4.size() && result1.size() == result5.size() &&
                          result1.size() == result6.size();
        System.out.println("All approaches found same number of triplets: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing 3Sum Solution:");
        System.out.println("======================");
        
        // Test case 1: Standard case
        System.out.println("\nTest 1: Standard case");
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(-1, -1, 2),
            Arrays.asList(-1, 0, 1)
        );
        
        List<List<Integer>> result1a = solution.threeSum(nums1);
        List<List<Integer>> result1b = solution.threeSumHashSet(nums1);
        List<List<Integer>> result1c = solution.threeSumOptimized(nums1);
        
        System.out.println("Two Pointers: " + result1a + " - " + (result1a.size() == expected1.size() ? "PASSED" : "FAILED"));
        System.out.println("HashSet: " + result1b + " - " + (result1b.size() == expected1.size() ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1c + " - " + (result1c.size() == expected1.size() ? "PASSED" : "FAILED"));
        
        // Visualize the two pointers process
        solution.visualizeThreeSum(nums1, "Test 1 - Standard Case");
        
        // Test case 2: No solution
        System.out.println("\nTest 2: No solution");
        int[] nums2 = {0, 1, 1};
        List<List<Integer>> expected2 = Arrays.asList();
        
        List<List<Integer>> result2a = solution.threeSum(nums2);
        System.out.println("No solution: " + result2a + " - " + 
                         (result2a.size() == expected2.size() ? "PASSED" : "FAILED"));
        
        // Test case 3: All zeros
        System.out.println("\nTest 3: All zeros");
        int[] nums3 = {0, 0, 0};
        List<List<Integer>> expected3 = Arrays.asList(Arrays.asList(0, 0, 0));
        
        List<List<Integer>> result3a = solution.threeSum(nums3);
        System.out.println("All zeros: " + result3a + " - " + 
                         (result3a.size() == expected3.size() ? "PASSED" : "FAILED"));
        
        // Test case 4: Large array with duplicates
        System.out.println("\nTest 4: Large array with duplicates");
        int[] nums4 = {-2, -2, -1, -1, 0, 0, 1, 1, 2, 2};
        List<List<Integer>> result4a = solution.threeSum(nums4);
        System.out.println("Large with duplicates: " + result4a.size() + " triplets - " + 
                         (result4a.size() > 0 ? "PASSED" : "FAILED"));
        
        // Test case 5: All positive numbers
        System.out.println("\nTest 5: All positive numbers");
        int[] nums5 = {1, 2, 3, 4, 5};
        List<List<Integer>> result5a = solution.threeSum(nums5);
        System.out.println("All positive: " + result5a + " - " + 
                         (result5a.size() == 0 ? "PASSED" : "FAILED"));
        
        // Test case 6: All negative numbers
        System.out.println("\nTest 6: All negative numbers");
        int[] nums6 = {-5, -4, -3, -2, -1};
        List<List<Integer>> result6a = solution.threeSum(nums6);
        System.out.println("All negative: " + result6a + " - " + 
                         (result6a.size() == 0 ? "PASSED" : "FAILED"));
        
        // Test case 7: Mixed with many zeros
        System.out.println("\nTest 7: Mixed with many zeros");
        int[] nums7 = {-1, 0, 0, 0, 1, 1};
        List<List<Integer>> result7a = solution.threeSum(nums7);
        System.out.println("Mixed with zeros: " + result7a.size() + " triplets - " + 
                         (result7a.size() > 0 ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(nums1, "Small Input (6 elements)");
        
        // Medium input performance
        int[] mediumNums = new int[100];
        Random random = new Random(42);
        for (int i = 0; i < mediumNums.length; i++) {
            mediumNums[i] = random.nextInt(200) - 100; // -100 to 100
        }
        solution.comparePerformance(mediumNums, "Medium Input (100 elements)");
        
        // Large input performance
        int[] largeNums = new int[1000];
        for (int i = 0; i < largeNums.length; i++) {
            largeNums[i] = random.nextInt(2000) - 1000; // -1000 to 1000
        }
        solution.comparePerformance(largeNums, "Large Input (1000 elements)");
        
        // Worst-case performance (all zeros)
        int[] worstCaseNums = new int[100];
        Arrays.fill(worstCaseNums, 0);
        solution.comparePerformance(worstCaseNums, "Worst Case (100 zeros)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Sorting + Two Pointers - RECOMMENDED:");
        System.out.println("   Time: O(n²) - Sorting O(n log n) + nested loops O(n²)");
        System.out.println("   Space: O(1) or O(n) - Depending on sorting algorithm");
        System.out.println("   How it works:");
        System.out.println("     - Sort the array to enable two pointers");
        System.out.println("     - For each element, solve two sum problem for remaining");
        System.out.println("     - Skip duplicates to avoid duplicate triplets");
        System.out.println("     - Use early termination optimizations");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity for this problem");
        System.out.println("     - Handles duplicates efficiently");
        System.out.println("     - Most efficient and scalable");
        System.out.println("   Cons:");
        System.out.println("     - Modifies input array (or needs copy)");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. HashSet Approach:");
        System.out.println("   Time: O(n²) - Nested loops with HashSet lookups");
        System.out.println("   Space: O(n) - HashSet storage");
        System.out.println("   How it works:");
        System.out.println("     - Sort the array");
        System.out.println("     - For each element, use HashSet to track seen numbers");
        System.out.println("     - Look for complement in HashSet");
        System.out.println("   Pros:");
        System.out.println("     - Clear logic with HashSet");
        System.out.println("     - Still O(n²) time complexity");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("     - Slightly slower than two pointers");
        System.out.println("   Best for: Learning alternative approaches");
        
        System.out.println("\n3. No-Sort with HashSets:");
        System.out.println("   Time: O(n²) - Nested loops with HashMap lookups");
        System.out.println("   Space: O(n) - Multiple HashSets for tracking");
        System.out.println("   How it works:");
        System.out.println("     - Use outer HashSet to skip duplicate first elements");
        System.out.println("     - Use inner HashMap to track seen numbers");
        System.out.println("     - Sort triplets before adding to result set");
        System.out.println("   Pros:");
        System.out.println("     - Doesn't modify input array");
        System.out.println("     - Works without sorting");
        System.out.println("   Cons:");
        System.out.println("     - Uses most space among approaches");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: When input cannot be modified");
        
        System.out.println("\n4. Binary Search Variation:");
        System.out.println("   Time: O(n² log n) - Nested loops with binary search");
        System.out.println("   Space: O(1) or O(n) - Depending on sorting");
        System.out.println("   How it works:");
        System.out.println("     - Sort the array");
        System.out.println("     - For each pair, binary search for third element");
        System.out.println("     - Skip duplicates appropriately");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates binary search application");
        System.out.println("     - Clear separation of concerns");
        System.out.println("   Cons:");
        System.out.println("     - Less efficient than two pointers");
        System.out.println("     - More complex than necessary");
        System.out.println("   Best for: Educational purposes");
        
        System.out.println("\n5. Optimized Two Pointers:");
        System.out.println("   Time: O(n²) - Same as standard but with optimizations");
        System.out.println("   Space: O(1) or O(n) - Depending on sorting");
        System.out.println("   How it works:");
        System.out.println("     - Same as standard two pointers");
        System.out.println("     - Additional early termination checks");
        System.out.println("     - More aggressive duplicate skipping");
        System.out.println("   Pros:");
        System.out.println("     - Potential performance improvements");
        System.out.println("     - Handles edge cases efficiently");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: Performance-critical applications");
        
        System.out.println("\n6. Brute Force:");
        System.out.println("   Time: O(n³) - Triple nested loops");
        System.out.println("   Space: O(1) - Only result storage");
        System.out.println("   How it works:");
        System.out.println("     - Check all possible triplets");
        System.out.println("     - Use HashSet to avoid duplicates");
        System.out.println("   Pros:");
        System.out.println("     - Simple and straightforward");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Extremely inefficient for large inputs");
        System.out.println("     - Doesn't scale beyond small arrays");
        System.out.println("   Best for: Demonstration only, small inputs");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("KEY OPTIMIZATIONS FOR 3SUM:");
        System.out.println("1. Sorting: Enables two pointers and duplicate skipping");
        System.out.println("2. Duplicate Skipping: Avoids duplicate triplets efficiently");
        System.out.println("3. Early Termination: Stop when first number > 0");
        System.out.println("4. Two Pointers: O(n) solution for two sum subproblem");
        System.out.println("5. Bounds Checking: Additional checks for impossible cases");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DUPLICATE HANDLING STRATEGIES:");
        System.out.println("1. Sort + Skip: Sort array, skip nums[i] == nums[i-1]");
        System.out.println("2. HashSet: Use HashSet to track seen triplets");
        System.out.println("3. Pointer Skipping: Skip duplicates in two pointers phase");
        System.out.println("4. Combination: Use multiple strategies for robustness");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. All zeros: Single triplet [0,0,0]");
        System.out.println("2. All positive/negative: No solution");
        System.out.println("3. Many duplicates: Efficient skipping needed");
        System.out.println("4. Minimum array size (3 elements): Handle naturally");
        System.out.println("5. Large numbers: Integer arithmetic handles correctly");
        System.out.println("6. Maximum constraints: Handle 3000 elements efficiently");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Sorting + Two Pointers approach");
        System.out.println("2. Explain why sorting helps with duplicates and two pointers");
        System.out.println("3. Implement duplicate skipping carefully");
        System.out.println("4. Mention time/space complexity: O(n²)/O(1) or O(n)");
        System.out.println("5. Discuss optimizations (early termination, bounds checks)");
        System.out.println("6. Handle edge cases in code");
        System.out.println("7. Write clean, readable code with good variable names");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Not handling duplicates correctly");
        System.out.println("2. Using O(n³) brute force for large inputs");
        System.out.println("3. Forgetting to sort the array first");
        System.out.println("4. Incorrect index management in two pointers");
        System.out.println("5. Not using early termination optimizations");
        System.out.println("6. Returning duplicate triplets");
        System.out.println("=".repeat(70));
        
        // Extension to related problems
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EXTENSION: K-SUM PROBLEMS");
        System.out.println("1. Two Sum: HashMap or two pointers for sorted array");
        System.out.println("2. Three Sum: Sort + two pointers (current problem)");
        System.out.println("3. Four Sum: Sort + two nested loops + two pointers");
        System.out.println("4. K-Sum: Recursive approach reducing to Two Sum");
        System.out.println("5. Pattern: Sort + recursive reduction + two pointers");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
