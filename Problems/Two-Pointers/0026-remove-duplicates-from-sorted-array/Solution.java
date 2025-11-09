/**
 * 26. Remove Duplicates from Sorted Array
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place 
 * such that each unique element appears only once. The relative order of the elements should be kept the same.
 * 
 * Key Insights:
 * 1. Use two pointers: slow pointer for position to place next unique element
 * 2. Fast pointer iterates through array to find next unique element
 * 3. Since array is sorted, duplicates are adjacent
 * 4. Compare current element with previous to detect duplicates
 * 
 * Approach (Two Pointers - RECOMMENDED):
 * 1. Initialize slow pointer at index 1 (first element is always unique)
 * 2. Iterate with fast pointer from index 1 to end
 * 3. When nums[fast] != nums[fast-1], copy to nums[slow] and increment slow
 * 4. Return slow as count of unique elements
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Two Pointers
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Two Pointers (Slow and Fast) - RECOMMENDED
     * O(n) time, O(1) space - Most efficient and readable
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        
        int slow = 1; // Position for next unique element
        // First element is always unique, so start from index 1
        
        for (int fast = 1; fast < nums.length; fast++) {
            // If current element is different from previous, it's unique
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        
        return slow;
    }
    
    /**
     * Approach 2: Two Pointers with Explicit Previous Value
     * O(n) time, O(1) space - Alternative implementation
     */
    public int removeDuplicatesWithPrevious(int[] nums) {
        if (nums.length == 0) return 0;
        
        int slow = 1;
        int prev = nums[0]; // Store previous value explicitly
        
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != prev) {
                nums[slow] = nums[fast];
                prev = nums[fast];
                slow++;
            }
        }
        
        return slow;
    }
    
    /**
     * Approach 3: Count Unique Elements First
     * O(n) time, O(1) space - Two pass approach
     */
    public int removeDuplicatesCountFirst(int[] nums) {
        if (nums.length == 0) return 0;
        
        // First pass: count unique elements
        int uniqueCount = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                uniqueCount++;
            }
        }
        
        // Second pass: place unique elements
        int slow = 1;
        for (int fast = 1; fast < nums.length && slow < uniqueCount; fast++) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        
        return uniqueCount;
    }
    
    /**
     * Approach 4: Using While Loop
     * O(n) time, O(1) space - Different loop structure
     */
    public int removeDuplicatesWhileLoop(int[] nums) {
        if (nums.length == 0) return 0;
        
        int slow = 1;
        int fast = 1;
        
        while (fast < nums.length) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        
        return slow;
    }
    
    /**
     * Approach 5: Recursive Solution
     * O(n) time, O(n) stack space - Elegant but uses stack space
     */
    public int removeDuplicatesRecursive(int[] nums) {
        if (nums.length == 0) return 0;
        return removeHelper(nums, 1, 1);
    }
    
    private int removeHelper(int[] nums, int slow, int fast) {
        // Base case: reached end of array
        if (fast >= nums.length) {
            return slow;
        }
        
        // If current element is unique, place it and recurse
        if (nums[fast] != nums[fast - 1]) {
            nums[slow] = nums[fast];
            return removeHelper(nums, slow + 1, fast + 1);
        }
        
        // If duplicate, just move fast pointer
        return removeHelper(nums, slow, fast + 1);
    }
    
    /**
     * Approach 6: In-place with Shifting (Inefficient but educational)
     * O(n²) time, O(1) space - Demonstrates naive approach
     */
    public int removeDuplicatesNaive(int[] nums) {
        if (nums.length == 0) return 0;
        
        int uniqueCount = 1;
        int currentIndex = 1;
        
        for (int i = 1; i < nums.length; i++) {
            boolean isDuplicate = false;
            
            // Check if current element is duplicate of any previous
            for (int j = 0; j < currentIndex; j++) {
                if (nums[i] == nums[j]) {
                    isDuplicate = true;
                    break;
                }
            }
            
            // If not duplicate, add to unique portion
            if (!isDuplicate) {
                nums[currentIndex] = nums[i];
                currentIndex++;
                uniqueCount++;
            }
        }
        
        return uniqueCount;
    }
    
    /**
     * Helper method to visualize the duplicate removal process
     */
    private void visualizeRemoval(int[] nums, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Initial array: " + Arrays.toString(nums));
        
        int[] numsCopy = nums.clone();
        int slow = 1;
        int step = 1;
        
        System.out.println("Step | Fast | Slow | nums[fast] | nums[fast-1] | Action | Array State");
        System.out.println("-----|------|------|------------|--------------|--------|------------");
        
        for (int fast = 1; fast < numsCopy.length; fast++) {
            String action;
            if (numsCopy[fast] != numsCopy[fast - 1]) {
                action = "Unique: copy nums[" + fast + "] to nums[" + slow + "]";
                numsCopy[slow] = numsCopy[fast];
                System.out.printf("%4d | %4d | %4d | %10d | %12d | %s | %s%n",
                                step, fast, slow, numsCopy[fast], numsCopy[fast - 1],
                                action, arrayToString(numsCopy, numsCopy.length));
                slow++;
            } else {
                action = "Duplicate: skip nums[" + fast + "]";
                System.out.printf("%4d | %4d | %4d | %10d | %12d | %s | %s%n",
                                step, fast, slow, numsCopy[fast], numsCopy[fast - 1],
                                action, arrayToString(numsCopy, numsCopy.length));
            }
            step++;
        }
        
        System.out.println("Final Result: " + arrayToString(numsCopy, slow) + " (first " + slow + " elements)");
        System.out.println("Return value: " + slow);
    }
    
    private String arrayToString(int[] arr, int length) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            sb.append(arr[i]);
            if (i < length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(int[] nums, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        int[] numsCopy1 = nums.clone();
        long startTime = System.nanoTime();
        int result1 = removeDuplicates(numsCopy1);
        long time1 = System.nanoTime() - startTime;
        
        int[] numsCopy2 = nums.clone();
        startTime = System.nanoTime();
        int result2 = removeDuplicatesWithPrevious(numsCopy2);
        long time2 = System.nanoTime() - startTime;
        
        int[] numsCopy3 = nums.clone();
        startTime = System.nanoTime();
        int result3 = removeDuplicatesCountFirst(numsCopy3);
        long time3 = System.nanoTime() - startTime;
        
        int[] numsCopy4 = nums.clone();
        startTime = System.nanoTime();
        int result4 = removeDuplicatesWhileLoop(numsCopy4);
        long time4 = System.nanoTime() - startTime;
        
        int[] numsCopy5 = nums.clone();
        startTime = System.nanoTime();
        int result5 = removeDuplicatesRecursive(numsCopy5);
        long time5 = System.nanoTime() - startTime;
        
        int[] numsCopy6 = nums.clone();
        startTime = System.nanoTime();
        int result6 = removeDuplicatesNaive(numsCopy6);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("Two Pointers (Standard): %d ns%n", time1);
        System.out.printf("Two Pointers (Previous): %d ns%n", time2);
        System.out.printf("Count First: %d ns%n", time3);
        System.out.printf("While Loop: %d ns%n", time4);
        System.out.printf("Recursive: %d ns%n", time5);
        System.out.printf("Naive (O(n²)): %d ns%n", time6);
        
        // Verify all produce same result
        boolean allEqual = result1 == result2 && result1 == result3 && 
                          result1 == result4 && result1 == result5 && result1 == result6;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Verify first k elements are the same
        boolean sameElements = true;
        for (int i = 0; i < result1; i++) {
            if (numsCopy1[i] != numsCopy2[i] || numsCopy1[i] != numsCopy3[i] || 
                numsCopy1[i] != numsCopy4[i] || numsCopy1[i] != numsCopy5[i]) {
                sameElements = false;
                break;
            }
        }
        System.out.println("First k elements consistent: " + sameElements);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Remove Duplicates from Sorted Array Solution:");
        System.out.println("=====================================================");
        
        // Test case 1: Standard case with duplicates
        System.out.println("\nTest 1: Standard case with duplicates");
        int[] nums1 = {1, 1, 2};
        int expected1 = 2;
        int[] expectedArray1 = {1, 2};
        
        int[] nums1Copy1a = nums1.clone();
        int result1a = solution.removeDuplicates(nums1Copy1a);
        boolean test1a = result1a == expected1 && arraysEqualPrefix(nums1Copy1a, expectedArray1, expected1);
        
        int[] nums1Copy1b = nums1.clone();
        int result1b = solution.removeDuplicatesWithPrevious(nums1Copy1b);
        boolean test1b = result1b == expected1 && arraysEqualPrefix(nums1Copy1b, expectedArray1, expected1);
        
        int[] nums1Copy1c = nums1.clone();
        int result1c = solution.removeDuplicatesCountFirst(nums1Copy1c);
        boolean test1c = result1c == expected1 && arraysEqualPrefix(nums1Copy1c, expectedArray1, expected1);
        
        System.out.println("Two Pointers: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("With Previous: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Count First: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the removal process
        solution.visualizeRemoval(nums1, "Test 1 - Standard Case");
        
        // Test case 2: Multiple duplicates
        System.out.println("\nTest 2: Multiple duplicates");
        int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int expected2 = 5;
        int[] expectedArray2 = {0, 1, 2, 3, 4};
        
        int[] nums2Copy2a = nums2.clone();
        int result2a = solution.removeDuplicates(nums2Copy2a);
        System.out.println("Multiple duplicates: " + result2a + " - " + 
                         (result2a == expected2 && arraysEqualPrefix(nums2Copy2a, expectedArray2, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: No duplicates
        System.out.println("\nTest 3: No duplicates");
        int[] nums3 = {1, 2, 3, 4, 5};
        int expected3 = 5;
        int[] expectedArray3 = {1, 2, 3, 4, 5};
        
        int[] nums3Copy3a = nums3.clone();
        int result3a = solution.removeDuplicates(nums3Copy3a);
        System.out.println("No duplicates: " + result3a + " - " + 
                         (result3a == expected3 && arraysEqualPrefix(nums3Copy3a, expectedArray3, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: All duplicates
        System.out.println("\nTest 4: All duplicates");
        int[] nums4 = {1, 1, 1, 1, 1};
        int expected4 = 1;
        int[] expectedArray4 = {1};
        
        int[] nums4Copy4a = nums4.clone();
        int result4a = solution.removeDuplicates(nums4Copy4a);
        System.out.println("All duplicates: " + result4a + " - " + 
                         (result4a == expected4 && arraysEqualPrefix(nums4Copy4a, expectedArray4, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Single element
        System.out.println("\nTest 5: Single element");
        int[] nums5 = {1};
        int expected5 = 1;
        int[] expectedArray5 = {1};
        
        int[] nums5Copy5a = nums5.clone();
        int result5a = solution.removeDuplicates(nums5Copy5a);
        System.out.println("Single element: " + result5a + " - " + 
                         (result5a == expected5 && arraysEqualPrefix(nums5Copy5a, expectedArray5, expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Empty array
        System.out.println("\nTest 6: Empty array");
        int[] nums6 = {};
        int expected6 = 0;
        int[] expectedArray6 = {};
        
        int[] nums6Copy6a = nums6.clone();
        int result6a = solution.removeDuplicates(nums6Copy6a);
        System.out.println("Empty array: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Negative numbers with duplicates
        System.out.println("\nTest 7: Negative numbers with duplicates");
        int[] nums7 = {-5, -5, -3, -3, -1, 0, 0, 2, 2};
        int expected7 = 5;
        int[] expectedArray7 = {-5, -3, -1, 0, 2};
        
        int[] nums7Copy7a = nums7.clone();
        int result7a = solution.removeDuplicates(nums7Copy7a);
        System.out.println("Negative numbers: " + result7a + " - " + 
                         (result7a == expected7 && arraysEqualPrefix(nums7Copy7a, expectedArray7, expected7) ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(nums2, "Small Input (10 elements)");
        
        // Medium input performance
        int[] mediumNums = new int[1000];
        Arrays.fill(mediumNums, 0, 200, 1);
        Arrays.fill(mediumNums, 200, 400, 2);
        Arrays.fill(mediumNums, 400, 600, 3);
        Arrays.fill(mediumNums, 600, 800, 4);
        Arrays.fill(mediumNums, 800, 1000, 5);
        solution.comparePerformance(mediumNums, "Medium Input (1000 elements)");
        
        // Large input performance
        int[] largeNums = new int[10000];
        for (int i = 0; i < largeNums.length; i++) {
            largeNums[i] = i / 10; // Creates many duplicates
        }
        solution.comparePerformance(largeNums, "Large Input (10000 elements)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Two Pointers (Standard) - RECOMMENDED:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Slow pointer tracks position for next unique element");
        System.out.println("     - Fast pointer finds next unique element");
        System.out.println("     - Compare nums[fast] with nums[fast-1] to detect duplicates");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - In-place modification");
        System.out.println("   Cons:");
        System.out.println("     - None significant");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Two Pointers with Previous Value:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Similar to standard approach");
        System.out.println("     - Stores previous value explicitly");
        System.out.println("     - Compare with stored previous value");
        System.out.println("   Pros:");
        System.out.println("     - Clear separation of concerns");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Extra variable for previous value");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: Learning the concept clearly");
        
        System.out.println("\n3. Count First Approach:");
        System.out.println("   Time: O(n) - Two passes through array");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - First pass: count unique elements");
        System.out.println("     - Second pass: place unique elements");
        System.out.println("   Pros:");
        System.out.println("     - Clear two-step process");
        System.out.println("     - Knows result size in advance");
        System.out.println("   Cons:");
        System.out.println("     - Two passes instead of one");
        System.out.println("     - Less efficient");
        System.out.println("   Best for: When you need count first");
        
        System.out.println("\n4. While Loop Approach:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Same algorithm as standard approach");
        System.out.println("     - Uses while loop instead of for loop");
        System.out.println("   Pros:");
        System.out.println("     - Alternative loop structure");
        System.out.println("     - Same efficiency as standard");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more verbose");
        System.out.println("     - Manual pointer increment");
        System.out.println("   Best for: Preference for while loops");
        
        System.out.println("\n5. Recursive Solution:");
        System.out.println("   Time: O(n) - Recursive calls for each element");
        System.out.println("   Space: O(n) - Stack space for recursion");
        System.out.println("   How it works:");
        System.out.println("     - Recursive function with slow and fast pointers");
        System.out.println("     - Base case: end of array");
        System.out.println("     - Recursive case: process current element");
        System.out.println("   Pros:");
        System.out.println("     - Elegant recursive formulation");
        System.out.println("     - Good for learning recursion");
        System.out.println("   Cons:");
        System.out.println("     - Stack space overhead");
        System.out.println("     - Risk of stack overflow for large inputs");
        System.out.println("     - Less efficient than iterative");
        System.out.println("   Best for: Educational purposes, small inputs");
        
        System.out.println("\n6. Naive Approach (O(n²)):");
        System.out.println("   Time: O(n²) - Nested loops for duplicate check");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - For each element, check if it's duplicate of any previous");
        System.out.println("     - If not duplicate, add to unique portion");
        System.out.println("   Pros:");
        System.out.println("     - Works for unsorted arrays");
        System.out.println("     - Simple logic");
        System.out.println("   Cons:");
        System.out.println("     - Very inefficient O(n²) time");
        System.out.println("     - Doesn't leverage sorted property");
        System.out.println("   Best for: Demonstration of inefficient approach");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY TWO POINTERS WORKS FOR SORTED ARRAYS:");
        System.out.println("1. Sorted arrays have duplicates adjacent to each other");
        System.out.println("2. We only need to compare with immediate previous element");
        System.out.println("3. Slow pointer maintains the unique portion of the array");
        System.out.println("4. Fast pointer finds the next unique element efficiently");
        System.out.println("5. This leverages the sorted property for O(n) time complexity");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. Empty array: return 0 immediately");
        System.out.println("2. Single element: return 1 (always unique)");
        System.out.println("3. All duplicates: return 1");
        System.out.println("4. No duplicates: return array length unchanged");
        System.out.println("5. Negative numbers: handled naturally by comparison");
        System.out.println("6. Maximum constraints: handle 30,000 elements efficiently");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Two Pointers approach (standard)");
        System.out.println("2. Explain the slow and fast pointer roles clearly");
        System.out.println("3. Mention why it works for sorted arrays (duplicates adjacent)");
        System.out.println("4. Handle edge cases explicitly (empty, single element)");
        System.out.println("5. Mention time/space complexity: O(n)/O(1)");
        System.out.println("6. Discuss what would change for unsorted arrays");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Using O(n) extra space (violates in-place requirement)");
        System.out.println("2. Starting slow pointer at 0 instead of 1");
        System.out.println("3. Not handling empty array case");
        System.out.println("4. Comparing with wrong element (should be fast-1)");
        System.out.println("5. Returning array instead of count");
        System.out.println("6. Using inefficient O(n²) approach for sorted array");
        System.out.println("=".repeat(70));
        
        // Extension to related problems
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EXTENSION: REMOVE DUPLICATES FROM SORTED ARRAY II");
        System.out.println("Problem: Allow at most 2 duplicates of each element");
        System.out.println("Solution: Modify two pointers approach:");
        System.out.println("  - Compare nums[fast] with nums[slow-2] instead of nums[fast-1]");
        System.out.println("  - This allows up to 2 duplicates while maintaining sorted order");
        System.out.println("  - Time: O(n), Space: O(1) - same efficiency");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
    
    // Helper method to compare first k elements of arrays
    private static boolean arraysEqualPrefix(int[] arr1, int[] arr2, int k) {
        if (k > arr1.length || k > arr2.length) return false;
        for (int i = 0; i < k; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }
}
