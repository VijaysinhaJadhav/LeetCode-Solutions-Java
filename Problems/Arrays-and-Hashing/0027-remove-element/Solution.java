/**
 * 27. Remove Element
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given an integer array nums and an integer val, remove all occurrences of val in nums in-place.
 * The order of the elements may be changed. Then return the number of elements in nums which are not equal to val.
 * 
 * Key Insights:
 * 1. Use two pointers: one to iterate through array, one to track position for valid elements
 * 2. When we find an element != val, copy it to the position tracked by the second pointer
 * 3. Alternative: swap-based approach when order doesn't matter (slightly more efficient)
 * 4. The count of valid elements is the position of the second pointer
 * 
 * Approach:
 * 1. Initialize pointer k = 0 to track position for valid elements
 * 2. Iterate through array with pointer i
 * 3. If nums[i] != val, copy nums[i] to nums[k] and increment k
 * 4. Return k as the count of valid elements
 * 
 * Time Complexity: O(n) - Single pass through array
 * Space Complexity: O(1) - Constant extra space
 * 
 * Tags: Array, Two Pointers
 */

import java.util.Arrays;
class Solution {
    /**
     * Approach 1: Two Pointers (Copy Forward) - RECOMMENDED
     * Preserves relative order of non-removed elements
     */
    public int removeElement(int[] nums, int val) {
        int k = 0; // Pointer for position to place next valid element
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }
        
        return k;
    }
    
    /**
     * Approach 2: Two Pointers with Swap (When Order Doesn't Matter)
     * More efficient when few elements to remove (fewer writes)
     * But changes the order of remaining elements
     */
    public int removeElementSwap(int[] nums, int val) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            if (nums[left] == val) {
                // Swap with right element and decrement right
                nums[left] = nums[right];
                right--;
            } else {
                left++;
            }
        }
        
        return left;
    }
    
    /**
     * Approach 3: Simple Counter (Similar to first approach)
     * More explicit variable naming
     */
    public int removeElementCounter(int[] nums, int val) {
        int validCount = 0;
        
        for (int num : nums) {
            if (num != val) {
                nums[validCount++] = num;
            }
        }
        
        return validCount;
    }
    
    /**
     * Helper method to verify the result meets judge requirements
     */
    private boolean verifyResult(int[] nums, int k, int val, int[] expectedNums) {
        if (k != expectedNums.length) {
            return false;
        }
        
        // Sort first k elements for comparison (as judge does)
        int[] firstK = Arrays.copyOf(nums, k);
        Arrays.sort(firstK);
        Arrays.sort(expectedNums);
        
        return Arrays.equals(firstK, expectedNums);
    }
    
    /**
     * Helper method to print array with k elements highlighted
     */
    private void printArrayWithK(int[] nums, int k, String label) {
        System.out.print(label + ": [");
        for (int i = 0; i < nums.length; i++) {
            if (i < k) {
                System.out.print(nums[i]);
            } else {
                System.out.print("_");
            }
            if (i < nums.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("] (k = " + k + ")");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Remove Element Solution:");
        System.out.println("=================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] nums1 = {3, 2, 2, 3};
        int val1 = 3;
        int[] expectedNums1 = {2, 2};
        int expectedK1 = 2;
        
        int[] nums1a = nums1.clone();
        int[] nums1b = nums1.clone();
        int[] nums1c = nums1.clone();
        
        long startTime = System.nanoTime();
        int result1a = solution.removeElement(nums1a, val1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.removeElementSwap(nums1b, val1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.removeElementCounter(nums1c, val1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = solution.verifyResult(nums1a, result1a, val1, expectedNums1);
        boolean test1b = solution.verifyResult(nums1b, result1b, val1, expectedNums1);
        boolean test1c = solution.verifyResult(nums1c, result1c, val1, expectedNums1);
        
        System.out.println("Copy Forward: " + (test1a ? "PASSED" : "FAILED") + " (k=" + result1a + ")");
        System.out.println("Swap Approach: " + (test1b ? "PASSED" : "FAILED") + " (k=" + result1b + ")");
        System.out.println("Counter Approach: " + (test1c ? "PASSED" : "FAILED") + " (k=" + result1c + ")");
        
        solution.printArrayWithK(nums1a, result1a, "Copy Forward Result");
        solution.printArrayWithK(nums1b, result1b, "Swap Approach Result");
        
        // Test case 2: Multiple occurrences
        System.out.println("\nTest 2: Multiple occurrences");
        int[] nums2 = {0, 1, 2, 2, 3, 0, 4, 2};
        int val2 = 2;
        int[] expectedNums2 = {0, 1, 4, 0, 3};
        int expectedK2 = 5;
        
        int[] nums2a = nums2.clone();
        int[] nums2b = nums2.clone();
        int[] nums2c = nums2.clone();
        
        int result2a = solution.removeElement(nums2a, val2);
        int result2b = solution.removeElementSwap(nums2b, val2);
        int result2c = solution.removeElementCounter(nums2c, val2);
        
        boolean test2a = solution.verifyResult(nums2a, result2a, val2, expectedNums2);
        boolean test2b = solution.verifyResult(nums2b, result2b, val2, expectedNums2);
        boolean test2c = solution.verifyResult(nums2c, result2c, val2, expectedNums2);
        
        System.out.println("Copy Forward: " + (test2a ? "PASSED" : "FAILED") + " (k=" + result2a + ")");
        System.out.println("Swap Approach: " + (test2b ? "PASSED" : "FAILED") + " (k=" + result2b + ")");
        System.out.println("Counter Approach: " + (test2c ? "PASSED" : "FAILED") + " (k=" + result2c + ")");
        
        // Test case 3: No elements to remove
        System.out.println("\nTest 3: No elements to remove");
        int[] nums3 = {1, 2, 3, 4, 5};
        int val3 = 6;
        int[] expectedNums3 = {1, 2, 3, 4, 5};
        int expectedK3 = 5;
        
        int[] nums3a = nums3.clone();
        int[] nums3b = nums3.clone();
        int[] nums3c = nums3.clone();
        
        int result3a = solution.removeElement(nums3a, val3);
        int result3b = solution.removeElementSwap(nums3b, val3);
        int result3c = solution.removeElementCounter(nums3c, val3);
        
        boolean test3a = solution.verifyResult(nums3a, result3a, val3, expectedNums3);
        boolean test3b = solution.verifyResult(nums3b, result3b, val3, expectedNums3);
        boolean test3c = solution.verifyResult(nums3c, result3c, val3, expectedNums3);
        
        System.out.println("Copy Forward: " + (test3a ? "PASSED" : "FAILED") + " (k=" + result3a + ")");
        System.out.println("Swap Approach: " + (test3b ? "PASSED" : "FAILED") + " (k=" + result3b + ")");
        System.out.println("Counter Approach: " + (test3c ? "PASSED" : "FAILED") + " (k=" + result3c + ")");
        
        // Test case 4: All elements to remove
        System.out.println("\nTest 4: All elements to remove");
        int[] nums4 = {2, 2, 2, 2, 2};
        int val4 = 2;
        int[] expectedNums4 = {};
        int expectedK4 = 0;
        
        int[] nums4a = nums4.clone();
        int[] nums4b = nums4.clone();
        int[] nums4c = nums4.clone();
        
        int result4a = solution.removeElement(nums4a, val4);
        int result4b = solution.removeElementSwap(nums4b, val4);
        int result4c = solution.removeElementCounter(nums4c, val4);
        
        boolean test4a = solution.verifyResult(nums4a, result4a, val4, expectedNums4);
        boolean test4b = solution.verifyResult(nums4b, result4b, val4, expectedNums4);
        boolean test4c = solution.verifyResult(nums4c, result4c, val4, expectedNums4);
        
        System.out.println("Copy Forward: " + (test4a ? "PASSED" : "FAILED") + " (k=" + result4a + ")");
        System.out.println("Swap Approach: " + (test4b ? "PASSED" : "FAILED") + " (k=" + result4b + ")");
        System.out.println("Counter Approach: " + (test4c ? "PASSED" : "FAILED") + " (k=" + result4c + ")");
        
        // Test case 5: Empty array
        System.out.println("\nTest 5: Empty array");
        int[] nums5 = {};
        int val5 = 1;
        int[] expectedNums5 = {};
        int expectedK5 = 0;
        
        int[] nums5a = nums5.clone();
        int[] nums5b = nums5.clone();
        int[] nums5c = nums5.clone();
        
        int result5a = solution.removeElement(nums5a, val5);
        int result5b = solution.removeElementSwap(nums5b, val5);
        int result5c = solution.removeElementCounter(nums5c, val5);
        
        boolean test5a = solution.verifyResult(nums5a, result5a, val5, expectedNums5);
        boolean test5b = solution.verifyResult(nums5b, result5b, val5, expectedNums5);
        boolean test5c = solution.verifyResult(nums5c, result5c, val5, expectedNums5);
        
        System.out.println("Copy Forward: " + (test5a ? "PASSED" : "FAILED") + " (k=" + result5a + ")");
        System.out.println("Swap Approach: " + (test5b ? "PASSED" : "FAILED") + " (k=" + result5b + ")");
        System.out.println("Counter Approach: " + (test5c ? "PASSED" : "FAILED") + " (k=" + result5c + ")");
        
        // Test case 6: Single element (to keep)
        System.out.println("\nTest 6: Single element to keep");
        int[] nums6 = {5};
        int val6 = 3;
        int[] expectedNums6 = {5};
        int expectedK6 = 1;
        
        int[] nums6a = nums6.clone();
        int result6a = solution.removeElement(nums6a, val6);
        boolean test6a = solution.verifyResult(nums6a, result6a, val6, expectedNums6);
        System.out.println("Single element keep: " + (test6a ? "PASSED" : "FAILED") + " (k=" + result6a + ")");
        
        // Test case 7: Single element (to remove)
        System.out.println("\nTest 7: Single element to remove");
        int[] nums7 = {5};
        int val7 = 5;
        int[] expectedNums7 = {};
        int expectedK7 = 0;
        
        int[] nums7a = nums7.clone();
        int result7a = solution.removeElement(nums7a, val7);
        boolean test7a = solution.verifyResult(nums7a, result7a, val7, expectedNums7);
        System.out.println("Single element remove: " + (test7a ? "PASSED" : "FAILED") + " (k=" + result7a + ")");
        
        // Performance comparison for large array
        System.out.println("\nTest 8: Performance comparison (large array)");
        int[] nums8 = new int[10000];
        Arrays.fill(nums8, 1);
        // Set every 3rd element to be removed
        for (int i = 0; i < nums8.length; i += 3) {
            nums8[i] = 99; // Value to remove
        }
        int val8 = 99;
        
        int[] nums8a = nums8.clone();
        int[] nums8b = nums8.clone();
        int[] nums8c = nums8.clone();
        
        startTime = System.nanoTime();
        int result8a = solution.removeElement(nums8a, val8);
        long time8a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result8b = solution.removeElementSwap(nums8b, val8);
        long time8b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result8c = solution.removeElementCounter(nums8c, val8);
        long time8c = System.nanoTime() - startTime;
        
        System.out.println("Large array (10000 elements, ~33% to remove):");
        System.out.println("  Copy Forward: " + time8a + " ns (k=" + result8a + ")");
        System.out.println("  Swap Approach: " + time8b + " ns (k=" + result8b + ")");
        System.out.println("  Counter Approach: " + time8c + " ns (k=" + result8c + ")");
        
        // Complexity analysis and approach comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("APPROACH COMPARISON & INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Copy Forward Approach (RECOMMENDED for interviews):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   Pros:");
        System.out.println("     - Preserves relative order of remaining elements");
        System.out.println("     - Simple and intuitive to implement");
        System.out.println("     - Easy to explain and understand");
        System.out.println("     - Minimum number of writes (only for valid elements)");
        System.out.println("   Cons:");
        System.out.println("     - Always does n comparisons");
        System.out.println("   Best for: Most cases, especially when order matters");
        
        System.out.println("\n2. Swap Approach (When Order Doesn't Matter):");
        System.out.println("   Time: O(n) - Single pass from both ends");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   Pros:");
        System.out.println("     - Fewer writes when removing few elements");
        System.out.println("     - Can terminate early if few elements to remove");
        System.out.println("   Cons:");
        System.out.println("     - Changes order of remaining elements");
        System.out.println("     - More complex logic with two pointers");
        System.out.println("   Best for: When order doesn't matter and few removals");
        
        System.out.println("\n3. Counter Approach (Variation of Copy Forward):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   Pros:");
        System.out.println("     - Very clean and readable code");
        System.out.println("     - Same benefits as copy forward approach");
        System.out.println("   Cons:");
        System.out.println("     - Essentially same as copy forward");
        System.out.println("   Best for: Code clarity and readability");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW TIPS:");
        System.out.println("1. Start with Copy Forward approach - it's the standard solution");
        System.out.println("2. Mention that order is preserved (important detail)");
        System.out.println("3. Discuss Swap approach as an alternative when order doesn't matter");
        System.out.println("4. Handle all edge cases: empty array, all removals, no removals");
        System.out.println("5. Explain the two pointers concept clearly");
        System.out.println("6. Practice drawing the algorithm on a whiteboard");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
