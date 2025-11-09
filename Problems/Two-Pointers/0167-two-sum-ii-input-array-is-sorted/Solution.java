/**
 * 167. Two Sum II - Input Array Is Sorted
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order, 
 * find two numbers such that they add up to a specific target number. 
 * Return the indices of the two numbers, index1 and index2, added by one as an integer array [index1, index2].
 * 
 * Key Insights:
 * 1. Use two pointers starting from both ends of the array
 * 2. If sum is less than target, move left pointer right (increase sum)
 * 3. If sum is greater than target, move right pointer left (decrease sum)
 * 4. Leverage sorted property for efficient search
 * 5. Return 1-based indices
 * 
 * Approach (Two Pointers - RECOMMENDED):
 * 1. Initialize left = 0, right = n-1
 * 2. While left < right, calculate current sum
 * 3. If sum == target, return [left+1, right+1]
 * 4. If sum < target, left++
 * 5. If sum > target, right--
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Two Pointers, Binary Search
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Two Pointers - RECOMMENDED
     * O(n) time, O(1) space - Most efficient and elegant
     */
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++; // Need larger sum, move left pointer right
            } else {
                right--; // Need smaller sum, move right pointer left
            }
        }
        
        // According to problem, exactly one solution exists
        return new int[]{-1, -1};
    }
    
    /**
     * Approach 2: Binary Search for Each Element
     * O(n log n) time, O(1) space - Slower but educational
     */
    public int[] twoSumBinarySearch(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            int complement = target - numbers[i];
            int left = i + 1;
            int right = numbers.length - 1;
            
            // Binary search for complement
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (numbers[mid] == complement) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] < complement) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return new int[]{-1, -1};
    }
    
    /**
     * Approach 3: HashMap (Violates Space Constraint)
     * O(n) time, O(n) space - For comparison with original Two Sum
     */
    public int[] twoSumHashMap(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < numbers.length; i++) {
            int complement = target - numbers[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement) + 1, i + 1};
            }
            map.put(numbers[i], i);
        }
        
        return new int[]{-1, -1};
    }
    
    /**
     * Approach 4: Two Pointers with Early Optimization
     * O(n) time, O(1) space - Optimized version
     */
    public int[] twoSumOptimized(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
            // Early optimization: skip duplicates from left
            while (left > 0 && left < right && numbers[left] == numbers[left - 1]) {
                left++;
            }
            // Early optimization: skip duplicates from right
            while (right < numbers.length - 1 && left < right && numbers[right] == numbers[right + 1]) {
                right--;
            }
            
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return new int[]{-1, -1};
    }
    
    /**
     * Approach 5: Fixed Left with Binary Search
     * O(n log n) time, O(1) space - Alternative binary search approach
     */
    public int[] twoSumFixedLeft(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            // For each element, search for complement in the rest of the array
            int complement = target - numbers[i];
            int result = binarySearch(numbers, complement, i + 1, numbers.length - 1);
            if (result != -1) {
                return new int[]{i + 1, result + 1};
            }
        }
        return new int[]{-1, -1};
    }
    
    private int binarySearch(int[] numbers, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (numbers[mid] == target) {
                return mid;
            } else if (numbers[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    /**
     * Approach 6: Recursive Two Pointers
     * O(n) time, O(n) stack space - Elegant but uses stack space
     */
    public int[] twoSumRecursive(int[] numbers, int target) {
        int[] result = new int[2];
        twoSumHelper(numbers, target, 0, numbers.length - 1, result);
        return result;
    }
    
    private void twoSumHelper(int[] numbers, int target, int left, int right, int[] result) {
        if (left >= right) {
            return;
        }
        
        int sum = numbers[left] + numbers[right];
        
        if (sum == target) {
            result[0] = left + 1;
            result[1] = right + 1;
            return;
        } else if (sum < target) {
            twoSumHelper(numbers, target, left + 1, right, result);
        } else {
            twoSumHelper(numbers, target, left, right - 1, result);
        }
    }
    
    /**
     * Helper method to visualize the two pointers process
     */
    private void visualizeTwoPointers(int[] numbers, int target, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Array: " + java.util.Arrays.toString(numbers));
        System.out.println("Target: " + target);
        
        int left = 0;
        int right = numbers.length - 1;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | numbers[left] | numbers[right] | Sum | Action");
        System.out.println("-----|------|-------|---------------|----------------|-----|--------");
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            String action;
            
            if (sum == target) {
                action = "FOUND! Return [" + (left + 1) + ", " + (right + 1) + "]";
                System.out.printf("%4d | %4d | %5d | %13d | %14d | %3d | %s%n",
                                step, left, right, numbers[left], numbers[right], sum, action);
                return;
            } else if (sum < target) {
                action = "Sum too small, move left++";
                System.out.printf("%4d | %4d | %5d | %13d | %14d | %3d | %s%n",
                                step, left, right, numbers[left], numbers[right], sum, action);
                left++;
            } else {
                action = "Sum too large, move right--";
                System.out.printf("%4d | %4d | %5d | %13d | %14d | %3d | %s%n",
                                step, left, right, numbers[left], numbers[right], sum, action);
                right--;
            }
            step++;
        }
        
        System.out.println("No solution found (should not happen)");
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(int[] numbers, int target, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        long startTime = System.nanoTime();
        int[] result1 = twoSum(numbers, target);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result2 = twoSumBinarySearch(numbers, target);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result3 = twoSumHashMap(numbers, target);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result4 = twoSumOptimized(numbers, target);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result5 = twoSumFixedLeft(numbers, target);
        long time5 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result6 = twoSumRecursive(numbers, target);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("Two Pointers: %d ns%n", time1);
        System.out.printf("Binary Search: %d ns%n", time2);
        System.out.printf("HashMap: %d ns%n", time3);
        System.out.printf("Optimized: %d ns%n", time4);
        System.out.printf("Fixed Left: %d ns%n", time5);
        System.out.printf("Recursive: %d ns%n", time6);
        
        // Verify all produce same result
        boolean allEqual = Arrays.equals(result1, result2) && Arrays.equals(result1, result3) && 
                          Arrays.equals(result1, result4) && Arrays.equals(result1, result5) && 
                          Arrays.equals(result1, result6);
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Two Sum II - Input Array Is Sorted Solution:");
        System.out.println("====================================================");
        
        // Test case 1: Standard case
        System.out.println("\nTest 1: Standard case");
        int[] numbers1 = {2, 7, 11, 15};
        int target1 = 9;
        int[] expected1 = {1, 2};
        
        int[] result1a = solution.twoSum(numbers1, target1);
        int[] result1b = solution.twoSumBinarySearch(numbers1, target1);
        int[] result1c = solution.twoSumHashMap(numbers1, target1);
        
        System.out.println("Two Pointers: " + Arrays.toString(result1a) + " - " + (Arrays.equals(result1a, expected1) ? "PASSED" : "FAILED"));
        System.out.println("Binary Search: " + Arrays.toString(result1b) + " - " + (Arrays.equals(result1b, expected1) ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + Arrays.toString(result1c) + " - " + (Arrays.equals(result1c, expected1) ? "PASSED" : "FAILED"));
        
        // Visualize the two pointers process
        solution.visualizeTwoPointers(numbers1, target1, "Test 1 - Standard Case");
        
        // Test case 2: Numbers at ends
        System.out.println("\nTest 2: Numbers at ends");
        int[] numbers2 = {2, 3, 4};
        int target2 = 6;
        int[] expected2 = {1, 3};
        
        int[] result2a = solution.twoSum(numbers2, target2);
        System.out.println("Numbers at ends: " + Arrays.toString(result2a) + " - " + 
                         (Arrays.equals(result2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Negative numbers
        System.out.println("\nTest 3: Negative numbers");
        int[] numbers3 = {-1, 0};
        int target3 = -1;
        int[] expected3 = {1, 2};
        
        int[] result3a = solution.twoSum(numbers3, target3);
        System.out.println("Negative numbers: " + Arrays.toString(result3a) + " - " + 
                         (Arrays.equals(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Duplicate numbers
        System.out.println("\nTest 4: Duplicate numbers");
        int[] numbers4 = {1, 2, 2, 4, 5};
        int target4 = 4;
        int[] expected4 = {2, 3};
        
        int[] result4a = solution.twoSum(numbers4, target4);
        System.out.println("Duplicate numbers: " + Arrays.toString(result4a) + " - " + 
                         (Arrays.equals(result4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Large numbers
        System.out.println("\nTest 5: Large numbers");
        int[] numbers5 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target5 = 19;
        int[] expected5 = {9, 10};
        
        int[] result5a = solution.twoSum(numbers5, target5);
        System.out.println("Large numbers: " + Arrays.toString(result5a) + " - " + 
                         (Arrays.equals(result5a, expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: All same numbers
        System.out.println("\nTest 6: All same numbers");
        int[] numbers6 = {5, 5, 5, 5, 5};
        int target6 = 10;
        int[] expected6 = {1, 2};
        
        int[] result6a = solution.twoSum(numbers6, target6);
        System.out.println("All same numbers: " + Arrays.toString(result6a) + " - " + 
                         (Arrays.equals(result6a, expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Minimum array size
        System.out.println("\nTest 7: Minimum array size");
        int[] numbers7 = {1, 2};
        int target7 = 3;
        int[] expected7 = {1, 2};
        
        int[] result7a = solution.twoSum(numbers7, target7);
        System.out.println("Minimum array: " + Arrays.toString(result7a) + " - " + 
                         (Arrays.equals(result7a, expected7) ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(numbers1, target1, "Small Input (4 elements)");
        
        // Medium input performance
        int[] mediumNumbers = new int[1000];
        for (int i = 0; i < mediumNumbers.length; i++) {
            mediumNumbers[i] = i + 1;
        }
        int mediumTarget = 1999; // 1000 + 999
        solution.comparePerformance(mediumNumbers, mediumTarget, "Medium Input (1000 elements)");
        
        // Large input performance
        int[] largeNumbers = new int[10000];
        for (int i = 0; i < largeNumbers.length; i++) {
            largeNumbers[i] = i + 1;
        }
        int largeTarget = 19999; // 10000 + 9999
        solution.comparePerformance(largeNumbers, largeTarget, "Large Input (10000 elements)");
        
        // Worst-case performance (elements at very ends)
        int[] worstCaseNumbers = new int[10000];
        for (int i = 0; i < worstCaseNumbers.length; i++) {
            worstCaseNumbers[i] = i + 1;
        }
        int worstCaseTarget = 3; // 1 + 2
        solution.comparePerformance(worstCaseNumbers, worstCaseTarget, "Worst Case (elements at ends)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Two Pointers - RECOMMENDED:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Start with left=0 and right=n-1");
        System.out.println("     - Calculate sum = numbers[left] + numbers[right]");
        System.out.println("     - If sum == target: return indices (1-based)");
        System.out.println("     - If sum < target: left++ (need larger sum)");
        System.out.println("     - If sum > target: right-- (need smaller sum)");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Leverages sorted array property");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Meets constant space requirement");
        System.out.println("   Cons:");
        System.out.println("     - Only works for sorted arrays");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Binary Search Approach:");
        System.out.println("   Time: O(n log n) - Binary search for each element");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - For each element, search for complement using binary search");
        System.out.println("     - Search range is from i+1 to end of array");
        System.out.println("     - Return 1-based indices when found");
        System.out.println("   Pros:");
        System.out.println("     - Still O(1) space complexity");
        System.out.println("     - Demonstrates binary search application");
        System.out.println("     - Works for sorted arrays");
        System.out.println("   Cons:");
        System.out.println("     - Slower than two pointers (O(n log n) vs O(n))");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: Learning binary search applications");
        
        System.out.println("\n3. HashMap Approach:");
        System.out.println("   Time: O(n) - Single pass with HashMap lookups");
        System.out.println("   Space: O(n) - HashMap storage");
        System.out.println("   How it works:");
        System.out.println("     - Same as original Two Sum problem");
        System.out.println("     - Store numbers and their indices in HashMap");
        System.out.println("     - For each number, check if complement exists");
        System.out.println("   Pros:");
        System.out.println("     - Works for unsorted arrays");
        System.out.println("     - Simple implementation");
        System.out.println("     - O(n) time complexity");
        System.out.println("   Cons:");
        System.out.println("     - Violates O(1) space requirement");
        System.out.println("     - Doesn't leverage sorted property");
        System.out.println("   Best for: Comparison with original Two Sum");
        
        System.out.println("\n4. Optimized Two Pointers:");
        System.out.println("   Time: O(n) - Single pass with duplicate skipping");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Same as standard two pointers");
        System.out.println("     - Additional optimization to skip duplicates");
        System.out.println("     - Can be faster for arrays with many duplicates");
        System.out.println("   Pros:");
        System.out.println("     - Potential performance improvement");
        System.out.println("     - Handles duplicate cases efficiently");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("     - Marginal improvement in practice");
        System.out.println("   Best for: Arrays with many duplicates");
        
        System.out.println("\n5. Fixed Left with Binary Search:");
        System.out.println("   Time: O(n log n) - Binary search for each fixed left");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Fix left element, binary search for complement");
        System.out.println("     - Uses helper binary search function");
        System.out.println("   Pros:");
        System.out.println("     - Clear separation of concerns");
        System.out.println("     - Reusable binary search function");
        System.out.println("   Cons:");
        System.out.println("     - Less efficient than two pointers");
        System.out.println("     - More code complexity");
        System.out.println("   Best for: Educational purposes");
        
        System.out.println("\n6. Recursive Two Pointers:");
        System.out.println("   Time: O(n) - Recursive calls equivalent to iteration");
        System.out.println("   Space: O(n) - Stack space for recursion");
        System.out.println("   How it works:");
        System.out.println("     - Recursive version of two pointers");
        System.out.println("     - Base case: left >= right");
        System.out.println("     - Recursive case: move left or right pointer");
        System.out.println("   Pros:");
        System.out.println("     - Elegant recursive formulation");
        System.out.println("     - Good for learning recursion");
        System.out.println("   Cons:");
        System.out.println("     - Stack space overhead");
        System.out.println("     - Risk of stack overflow for large inputs");
        System.out.println("     - Less efficient than iterative");
        System.out.println("   Best for: Educational purposes, small inputs");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY TWO POINTERS WORKS FOR SORTED ARRAYS:");
        System.out.println("1. Array is sorted in non-decreasing order");
        System.out.println("2. Moving left pointer right increases the sum");
        System.out.println("3. Moving right pointer left decreases the sum");
        System.out.println("4. This allows us to efficiently narrow down the search space");
        System.out.println("5. The algorithm is guaranteed to find the solution");
        System.out.println("6. Time complexity is optimal O(n)");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARISON WITH ORIGINAL TWO SUM:");
        System.out.println("1. Original Two Sum: Unsorted array, use HashMap for O(n) time, O(n) space");
        System.out.println("2. Two Sum II: Sorted array, use two pointers for O(n) time, O(1) space");
        System.out.println("3. Key difference: Sorted property enables space optimization");
        System.out.println("4. Two Sum II has stricter space constraints (O(1) only)");
        System.out.println("5. Both have exactly one solution guaranteed");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. Minimum array size (2 elements): handled naturally");
        System.out.println("2. Negative numbers: comparison works correctly");
        System.out.println("3. Duplicate numbers: algorithm finds first valid pair");
        System.out.println("4. Large numbers: integer arithmetic handles correctly");
        System.out.println("5. Target at extremes: algorithm adjusts pointers correctly");
        System.out.println("6. Exactly one solution: guaranteed by problem constraints");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Two Pointers approach (it's the expected solution)");
        System.out.println("2. Explain WHY it works for sorted arrays");
        System.out.println("3. Mention time/space complexity: O(n)/O(1)");
        System.out.println("4. Discuss alternative approaches and their trade-offs");
        System.out.println("5. Handle edge cases in code (negative numbers, duplicates)");
        System.out.println("6. Compare with original Two Sum problem");
        System.out.println("7. Write clean code with good variable names");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Using HashMap (violates O(1) space constraint)");
        System.out.println("2. Forgetting to return 1-based indices");
        System.out.println("3. Not leveraging sorted array property");
        System.out.println("4. Using inefficient O(n²) brute force");
        System.out.println("5. Incorrect pointer movement logic");
        System.out.println("6. Not handling negative numbers correctly");
        System.out.println("=".repeat(70));
        
        // Extension to related problems
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EXTENSION: THREE SUM AND VARIATIONS");
        System.out.println("1. Three Sum: Fix one element, then use two pointers for remaining");
        System.out.println("2. Three Sum Closest: Similar but track closest sum");
        System.out.println("3. Four Sum: Extend with additional loop or divide into two Two Sum problems");
        System.out.println("4. Pattern: Sorted arrays enable efficient multi-pointer solutions");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
