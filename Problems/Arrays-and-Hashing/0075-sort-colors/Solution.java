/**
 * 75. Sort Colors
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array nums with n objects colored red, white, or blue, sort them in-place
 * so that objects of the same color are adjacent, with colors in order red, white, blue.
 * We use integers 0 (red), 1 (white), and 2 (blue). Must solve without library sort.
 * 
 * Key Insights:
 * 1. Dutch National Flag Algorithm: Three pointers partitioning into 0s, 1s, 2s
 * 2. Counting Sort: Count occurrences then overwrite - simple but two passes
 * 3. Two-pointer swap: Move 0s to front, 2s to back
 * 4. Since only 3 distinct values, we can use specialized algorithms
 * 
 * Approach (Dutch National Flag):
 * 1. Use three pointers: low=0, mid=0, high=n-1
 * 2. Iterate while mid <= high
 * 3. If nums[mid] == 0, swap with low, increment both low and mid
 * 4. If nums[mid] == 1, just increment mid
 * 5. If nums[mid] == 2, swap with high, decrement high
 * 
 * Time Complexity: O(n) - Single pass
 * Space Complexity: O(1) - Constant extra space
 * 
 * Tags: Array, Two Pointers, Sorting
 */

import java.util.Arrays;
import java.util.Random;

class Solution {
    /**
     * Approach 1: Dutch National Flag Algorithm (RECOMMENDED)
     * One-pass, O(1) space - Solves the follow-up challenge
     */
    public void sortColors(int[] nums) {
        int low = 0;          // Pointer for 0s (red)
        int mid = 0;          // Pointer for 1s (white) - current element
        int high = nums.length - 1; // Pointer for 2s (blue)
        
        while (mid <= high) {
            if (nums[mid] == 0) {
                // Current element is 0, swap with low pointer
                swap(nums, low, mid);
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                // Current element is 1, just move mid pointer
                mid++;
            } else { // nums[mid] == 2
                // Current element is 2, swap with high pointer
                swap(nums, mid, high);
                high--;
                // Don't increment mid here because we need to check the swapped element
            }
        }
    }
    
    /**
     * Approach 2: Two-pass Counting Sort
     * Simple and intuitive but requires two passes
     */
    public void sortColorsCounting(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0;
        
        // First pass: count occurrences
        for (int num : nums) {
            if (num == 0) count0++;
            else if (num == 1) count1++;
            else count2++;
        }
        
        // Second pass: overwrite array
        int index = 0;
        for (int i = 0; i < count0; i++) nums[index++] = 0;
        for (int i = 0; i < count1; i++) nums[index++] = 1;
        for (int i = 0; i < count2; i++) nums[index++] = 2;
    }
    
    /**
     * Approach 3: Two-pointer for 0s and 2s
     * Move 0s to front, 2s to back, 1s will be in middle automatically
     */
    public void sortColorsTwoPointers(int[] nums) {
        int zeroPtr = 0;           // Pointer for next position for 0
        int twoPtr = nums.length - 1; // Pointer for next position for 2
        int i = 0;
        
        while (i <= twoPtr) {
            if (nums[i] == 0) {
                // Move 0 to the front
                swap(nums, zeroPtr, i);
                zeroPtr++;
                i++;
            } else if (nums[i] == 2) {
                // Move 2 to the end
                swap(nums, i, twoPtr);
                twoPtr--;
                // Don't increment i - need to check the swapped element
            } else {
                // It's 1, just move forward
                i++;
            }
        }
    }
    
    /**
     * Approach 4: Modified Dutch Flag with different pointer logic
     * Alternative implementation for educational purposes
     */
    public void sortColorsDutchAlternative(int[] nums) {
        int i = 0, j = 0, k = nums.length - 1;
        
        while (j <= k) {
            switch (nums[j]) {
                case 0:
                    swap(nums, i, j);
                    i++;
                    j++;
                    break;
                case 1:
                    j++;
                    break;
                case 2:
                    swap(nums, j, k);
                    k--;
                    break;
            }
        }
    }
    
    /**
     * Utility method to swap elements in array
     */
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Helper method to verify if array is sorted according to colors
     */
    private boolean isSortedColors(int[] nums) {
        int state = 0; // 0: expecting 0s, 1: expecting 1s, 2: expecting 2s
        
        for (int num : nums) {
            if (num == 0) {
                if (state > 0) return false;
            } else if (num == 1) {
                if (state > 1) return false;
                if (state == 0) state = 1;
            } else { // num == 2
                if (state < 2) state = 2;
            }
        }
        return true;
    }
    
    /**
     * Helper method to print array with color coding
     */
    private void printColors(int[] nums, String label) {
        System.out.print(label + ": [");
        for (int i = 0; i < nums.length; i++) {
            String color;
            switch (nums[i]) {
                case 0: color = "RED"; break;
                case 1: color = "WHITE"; break;
                case 2: color = "BLUE"; break;
                default: color = "UNKNOWN";
            }
            System.out.print(color + "(" + nums[i] + ")");
            if (i < nums.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    
    /**
     * Visualization helper for Dutch National Flag algorithm
     */
    private void visualizeDutchNationalFlag(int[] nums) {
        System.out.println("\nDutch National Flag Algorithm Visualization:");
        System.out.println("low=0 (0s), mid=0 (current), high=" + (nums.length-1) + " (2s)");
        
        int low = 0, mid = 0, high = nums.length - 1;
        int step = 1;
        
        while (mid <= high) {
            System.out.println("\nStep " + step++ + ":");
            System.out.print("Array: [");
            for (int i = 0; i < nums.length; i++) {
                if (i == low) System.out.print("L");
                if (i == mid) System.out.print("M");
                if (i == high) System.out.print("H");
                System.out.print(nums[i]);
                if (i < nums.length - 1) System.out.print(", ");
            }
            System.out.println("]");
            
            if (nums[mid] == 0) {
                System.out.println("nums[M] == 0 → swap(L,M), L++, M++");
                swap(nums, low, mid);
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                System.out.println("nums[M] == 1 → M++");
                mid++;
            } else {
                System.out.println("nums[M] == 2 → swap(M,H), H--");
                swap(nums, mid, high);
                high--;
            }
        }
        
        System.out.println("\nFinal sorted array:");
        printColors(nums, "Result");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Sort Colors Solution:");
        System.out.println("==============================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] nums1 = {2, 0, 2, 1, 1, 0};
        int[] expected1 = {0, 0, 1, 1, 2, 2};
        
        int[] nums1a = nums1.clone();
        int[] nums1b = nums1.clone();
        int[] nums1c = nums1.clone();
        int[] nums1d = nums1.clone();
        
        System.out.println("Input: " + Arrays.toString(nums1));
        
        long startTime = System.nanoTime();
        solution.sortColors(nums1a);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.sortColorsCounting(nums1b);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.sortColorsTwoPointers(nums1c);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.sortColorsDutchAlternative(nums1d);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = Arrays.equals(nums1a, expected1);
        boolean test1b = Arrays.equals(nums1b, expected1);
        boolean test1c = Arrays.equals(nums1c, expected1);
        boolean test1d = Arrays.equals(nums1d, expected1);
        
        System.out.println("Dutch National Flag: " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Counting Sort: " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Two Pointers: " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Dutch Alternative: " + (test1d ? "PASSED" : "FAILED"));
        
        // Test case 2: Already sorted
        System.out.println("\nTest 2: Already sorted");
        int[] nums2 = {0, 0, 1, 1, 2, 2};
        int[] expected2 = {0, 0, 1, 1, 2, 2};
        
        int[] nums2a = nums2.clone();
        solution.sortColors(nums2a);
        boolean test2a = Arrays.equals(nums2a, expected2);
        System.out.println("Already sorted - Dutch National Flag: " + (test2a ? "PASSED" : "FAILED"));
        
        // Test case 3: Reverse order
        System.out.println("\nTest 3: Reverse order");
        int[] nums3 = {2, 2, 1, 1, 0, 0};
        int[] expected3 = {0, 0, 1, 1, 2, 2};
        
        int[] nums3a = nums3.clone();
        solution.sortColors(nums3a);
        boolean test3a = Arrays.equals(nums3a, expected3);
        System.out.println("Reverse order - Dutch National Flag: " + (test3a ? "PASSED" : "FAILED"));
        
        // Test case 4: All same color
        System.out.println("\nTest 4: All zeros");
        int[] nums4 = {0, 0, 0, 0, 0};
        int[] expected4 = {0, 0, 0, 0, 0};
        
        int[] nums4a = nums4.clone();
        solution.sortColors(nums4a);
        boolean test4a = Arrays.equals(nums4a, expected4);
        System.out.println("All zeros - Dutch National Flag: " + (test4a ? "PASSED" : "FAILED"));
        
        System.out.println("\nTest 5: All ones");
        int[] nums5 = {1, 1, 1, 1, 1};
        int[] expected5 = {1, 1, 1, 1, 1};
        
        int[] nums5a = nums5.clone();
        solution.sortColors(nums5a);
        boolean test5a = Arrays.equals(nums5a, expected5);
        System.out.println("All ones - Dutch National Flag: " + (test5a ? "PASSED" : "FAILED"));
        
        System.out.println("\nTest 6: All twos");
        int[] nums6 = {2, 2, 2, 2, 2};
        int[] expected6 = {2, 2, 2, 2, 2};
        
        int[] nums6a = nums6.clone();
        solution.sortColors(nums6a);
        boolean test6a = Arrays.equals(nums6a, expected6);
        System.out.println("All twos - Dutch National Flag: " + (test6a ? "PASSED" : "FAILED"));
        
        // Test case 7: Single element
        System.out.println("\nTest 7: Single element");
        int[] nums7 = {1};
        int[] expected7 = {1};
        
        int[] nums7a = nums7.clone();
        solution.sortColors(nums7a);
        boolean test7a = Arrays.equals(nums7a, expected7);
        System.out.println("Single element - Dutch National Flag: " + (test7a ? "PASSED" : "FAILED"));
        
        // Test case 8: Mixed with no zeros
        System.out.println("\nTest 8: Mixed with no zeros");
        int[] nums8 = {2, 1, 2, 1, 1, 2};
        int[] expected8 = {1, 1, 1, 2, 2, 2};
        
        int[] nums8a = nums8.clone();
        solution.sortColors(nums8a);
        boolean test8a = Arrays.equals(nums8a, expected8);
        System.out.println("No zeros - Dutch National Flag: " + (test8a ? "PASSED" : "FAILED"));
        
        // Test case 9: Mixed with no ones
        System.out.println("\nTest 9: Mixed with no ones");
        int[] nums9 = {2, 0, 2, 0, 0, 2};
        int[] expected9 = {0, 0, 0, 2, 2, 2};
        
        int[] nums9a = nums9.clone();
        solution.sortColors(nums9a);
        boolean test9a = Arrays.equals(nums9a, expected9);
        System.out.println("No ones - Dutch National Flag: " + (test9a ? "PASSED" : "FAILED"));
        
        // Test case 10: Mixed with no twos
        System.out.println("\nTest 10: Mixed with no twos");
        int[] nums10 = {0, 1, 0, 1, 1, 0};
        int[] expected10 = {0, 0, 0, 1, 1, 1};
        
        int[] nums10a = nums10.clone();
        solution.sortColors(nums10a);
        boolean test10a = Arrays.equals(nums10a, expected10);
        System.out.println("No twos - Dutch National Flag: " + (test10a ? "PASSED" : "FAILED"));
        
        // Performance comparison for large array
        System.out.println("\nTest 11: Performance comparison (large array)");
        int[] nums11 = new int[1000];
        Random random = new Random(42);
        for (int i = 0; i < nums11.length; i++) {
            nums11[i] = random.nextInt(3); // Only 0, 1, or 2
        }
        
        int[] nums11a = nums11.clone();
        int[] nums11b = nums11.clone();
        int[] nums11c = nums11.clone();
        int[] nums11d = nums11.clone();
        
        startTime = System.nanoTime();
        solution.sortColors(nums11a);
        long time11a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.sortColorsCounting(nums11b);
        long time11b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.sortColorsTwoPointers(nums11c);
        long time11c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.sortColorsDutchAlternative(nums11d);
        long time11d = System.nanoTime() - startTime;
        
        System.out.println("Large array (1000 elements):");
        System.out.println("  Dutch National Flag: " + time11a + " ns " + 
                         (solution.isSortedColors(nums11a) ? "✓" : "✗"));
        System.out.println("  Counting Sort: " + time11b + " ns " + 
                         (solution.isSortedColors(nums11b) ? "✓" : "✗"));
        System.out.println("  Two Pointers: " + time11c + " ns " + 
                         (solution.isSortedColors(nums11c) ? "✓" : "✗"));
        System.out.println("  Dutch Alternative: " + time11d + " ns " + 
                         (solution.isSortedColors(nums11d) ? "✓" : "✗"));
        
        // Verify all approaches produce the same result
        boolean allEqual = Arrays.equals(nums11a, nums11b) &&
                          Arrays.equals(nums11a, nums11c) &&
                          Arrays.equals(nums11a, nums11d);
        System.out.println("All approaches produce same result: " + allEqual);
        
        // Visualization of Dutch National Flag algorithm
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DUTCH NATIONAL FLAG ALGORITHM VISUALIZATION:");
        System.out.println("=".repeat(70));
        
        int[] demoNums = {2, 0, 2, 1, 1, 0};
        System.out.println("Initial array: " + Arrays.toString(demoNums));
        solution.visualizeDutchNationalFlag(demoNums);
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Dutch National Flag Algorithm (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only three pointers");
        System.out.println("   How it works:");
        System.out.println("     - Maintain three pointers: low(0s), mid(current), high(2s)");
        System.out.println("     - Partition array into three regions: [0..low-1]=0s, [low..mid-1]=1s, [high+1..end]=2s");
        System.out.println("     - Swap elements to move them to correct regions");
        System.out.println("   Pros:");
        System.out.println("     - One-pass algorithm (solves follow-up)");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Elegant and efficient");
        System.out.println("   Cons:");
        System.out.println("     - More complex to understand initially");
        System.out.println("   Best for: Interview settings, optimal solution");
        
        System.out.println("\n2. Counting Sort:");
        System.out.println("   Time: O(n) - Two passes through array");
        System.out.println("   Space: O(1) - Only three counters");
        System.out.println("   How it works:");
        System.out.println("     - First pass: count occurrences of 0, 1, 2");
        System.out.println("     - Second pass: overwrite array with counts");
        System.out.println("   Pros:");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to implement and understand");
        System.out.println("   Cons:");
        System.out.println("     - Requires two passes");
        System.out.println("     - Not in-place in the algorithmic sense (overwrites)");
        System.out.println("   Best for: When simplicity is prioritized");
        
        System.out.println("\n3. Two Pointers (0s and 2s):");
        System.out.println("   Time: O(n) - Single pass");
        System.out.println("   Space: O(1) - Two pointers");
        System.out.println("   How it works:");
        System.out.println("     - Move 0s to front, 2s to back");
        System.out.println("     - 1s automatically end up in middle");
        System.out.println("   Pros:");
        System.out.println("     - One-pass algorithm");
        System.out.println("     - Simpler than Dutch National Flag");
        System.out.println("   Cons:");
        System.out.println("     - Still requires careful pointer management");
        System.out.println("   Best for: Alternative one-pass solution");
        
        System.out.println("\n4. Dutch National Flag (Alternative):");
        System.out.println("   Time: O(n) - Single pass");
        System.out.println("   Space: O(1) - Three pointers");
        System.out.println("   How it works:");
        System.out.println("     - Similar to main approach with switch statement");
        System.out.println("     - Different pointer increment logic");
        System.out.println("   Pros:");
        System.out.println("     - Same benefits as main approach");
        System.out.println("     - Some find switch statement more readable");
        System.out.println("   Cons:");
        System.out.println("     - Essentially same complexity");
        System.out.println("   Best for: Personal preference");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY DUTCH NATIONAL FLAG WORKS:");
        System.out.println("The algorithm maintains these invariants:");
        System.out.println("1. nums[0..low-1] = 0 (all reds)");
        System.out.println("2. nums[low..mid-1] = 1 (all whites)");
        System.out.println("3. nums[mid..high] = unknown (to be processed)");
        System.out.println("4. nums[high+1..end] = 2 (all blues)");
        System.out.println("As we process each element, we maintain these regions.");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Dutch National Flag - it's the expected optimal solution");
        System.out.println("2. Explain the three pointers and their meanings clearly");
        System.out.println("3. Mention Counting Sort as simpler alternative (but two-pass)");
        System.out.println("4. Practice drawing the algorithm on whiteboard");
        System.out.println("5. Be prepared to explain WHY it works (the invariants)");
        System.out.println("6. Handle edge cases: all same color, already sorted, single element");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
