/**
 * 88. Merge Sorted Array
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, 
 * and two integers m and n, representing the number of elements in nums1 and nums2 respectively.
 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
 * The final sorted array should be stored inside the array nums1.
 * 
 * Key Insights:
 * 1. Use three pointers starting from the end of both arrays
 * 2. Compare elements from the end and place larger elements at the end of nums1
 * 3. This avoids overwriting elements in nums1 that haven't been processed
 * 4. Handle remaining elements in nums2 if any
 * 
 * Approach (Three Pointers from End - RECOMMENDED):
 * 1. Initialize pointers: i = m-1, j = n-1, k = m+n-1
 * 2. While both arrays have elements, compare and place larger element at k
 * 3. If nums2 has remaining elements, copy them to nums1
 * 
 * Time Complexity: O(m + n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Two Pointers, Sorting
 */

import java.util.Arrays;

class Solution {
    /**
     * Approach 1: Three Pointers from End - RECOMMENDED
     * O(m + n) time, O(1) space - Most efficient and elegant
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1; // Pointer for nums1 (real elements)
        int j = n - 1; // Pointer for nums2
        int k = m + n - 1; // Pointer for end of nums1
        
        // Merge from the end by comparing elements
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }
        
        // Copy remaining elements from nums2 if any
        while (j >= 0) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }
    }
    
    /**
     * Approach 2: Two Pointers from Start with Extra Space
     * O(m + n) time, O(m) space - Simple but uses extra space
     */
    public void mergeWithExtraSpace(int[] nums1, int m, int[] nums2, int n) {
        // Create a copy of nums1's real elements
        int[] nums1Copy = new int[m];
        for (int i = 0; i < m; i++) {
            nums1Copy[i] = nums1[i];
        }
        
        int i = 0, j = 0, k = 0;
        
        // Merge using three pointers from start
        while (i < m && j < n) {
            if (nums1Copy[i] <= nums2[j]) {
                nums1[k] = nums1Copy[i];
                i++;
            } else {
                nums1[k] = nums2[j];
                j++;
            }
            k++;
        }
        
        // Copy remaining elements from nums1Copy
        while (i < m) {
            nums1[k] = nums1Copy[i];
            i++;
            k++;
        }
        
        // Copy remaining elements from nums2
        while (j < n) {
            nums1[k] = nums2[j];
            j++;
            k++;
        }
    }
    
    /**
     * Approach 3: Merge and Sort
     * O((m + n) log(m + n)) time, O(1) space - Simple but inefficient
     */
    public void mergeAndSort(int[] nums1, int m, int[] nums2, int n) {
        // Copy nums2 into the end of nums1
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        
        // Sort the entire array
        Arrays.sort(nums1);
    }
    
    /**
     * Approach 4: Two Pointers with Early Termination
     * O(m + n) time, O(1) space - Optimized version
     */
    public void mergeOptimized(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        
        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }
    }
    
    /**
     * Approach 5: Recursive Solution
     * O(m + n) time, O(m + n) stack space - Elegant but uses stack space
     */
    public void mergeRecursive(int[] nums1, int m, int[] nums2, int n) {
        mergeHelper(nums1, m, nums2, n, m - 1, n - 1, m + n - 1);
    }
    
    private void mergeHelper(int[] nums1, int m, int[] nums2, int n, int i, int j, int k) {
        // Base case: both arrays exhausted
        if (i < 0 && j < 0) {
            return;
        }
        
        // If nums1 exhausted, copy from nums2
        if (i < 0) {
            nums1[k] = nums2[j];
            mergeHelper(nums1, m, nums2, n, i, j - 1, k - 1);
            return;
        }
        
        // If nums2 exhausted, copy from nums1
        if (j < 0) {
            nums1[k] = nums1[i];
            mergeHelper(nums1, m, nums2, n, i - 1, j, k - 1);
            return;
        }
        
        // Compare and place larger element
        if (nums1[i] > nums2[j]) {
            nums1[k] = nums1[i];
            mergeHelper(nums1, m, nums2, n, i - 1, j, k - 1);
        } else {
            nums1[k] = nums2[j];
            mergeHelper(nums1, m, nums2, n, i, j - 1, k - 1);
        }
    }
    
    /**
     * Approach 6: Gap Method (for educational purposes)
     * O((m + n) log(m + n)) time, O(1) space - Alternative approach
     */
    public void mergeGapMethod(int[] nums1, int m, int[] nums2, int n) {
        // First, copy nums2 to the end of nums1
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        
        // Use gap method for in-place merge
        int total = m + n;
        int gap = nextGap(total);
        
        while (gap > 0) {
            int i = 0;
            while (i + gap < total) {
                if (nums1[i] > nums1[i + gap]) {
                    // Swap elements
                    int temp = nums1[i];
                    nums1[i] = nums1[i + gap];
                    nums1[i + gap] = temp;
                }
                i++;
            }
            gap = nextGap(gap);
        }
    }
    
    private int nextGap(int gap) {
        if (gap <= 1) {
            return 0;
        }
        return (gap / 2) + (gap % 2);
    }
    
    /**
     * Helper method to visualize the merging process
     */
    private void visualizeMerge(int[] nums1, int m, int[] nums2, int n, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Initial nums1: " + arrayToString(nums1, m + n));
        System.out.println("Real elements in nums1: " + arrayToString(nums1, m));
        System.out.println("nums2: " + arrayToString(nums2, n));
        
        int[] nums1Copy = nums1.clone();
        int[] nums2Copy = nums2.clone();
        
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        int step = 1;
        
        System.out.println("\nStep | i | j | k | nums1[i] | nums2[j] | Action | nums1 State");
        System.out.println("-----|---|----|---|----------|----------|--------|------------");
        
        while (i >= 0 && j >= 0) {
            String action;
            if (nums1Copy[i] > nums2Copy[j]) {
                nums1Copy[k] = nums1Copy[i];
                action = "Take from nums1[" + i + "] = " + nums1Copy[i];
                i--;
            } else {
                nums1Copy[k] = nums2Copy[j];
                action = "Take from nums2[" + j + "] = " + nums2Copy[j];
                j--;
            }
            
            System.out.printf("%4d | %d | %2d | %d | %8d | %8d | %s | %s%n",
                            step, i + 1, j + 1, k, 
                            i >= 0 ? nums1Copy[i + 1] : -1, 
                            j >= 0 ? nums2Copy[j + 1] : -1,
                            action, arrayToString(nums1Copy, m + n));
            k--;
            step++;
        }
        
        // Handle remaining elements in nums2
        while (j >= 0) {
            nums1Copy[k] = nums2Copy[j];
            System.out.printf("%4d | %d | %2d | %d | %8s | %8d | Take from nums2[%d] = %d | %s%n",
                            step, -1, j, k, "-", nums2Copy[j], j, nums2Copy[j], 
                            arrayToString(nums1Copy, m + n));
            j--;
            k--;
            step++;
        }
        
        System.out.println("Final Result: " + arrayToString(nums1Copy, m + n));
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
    private void comparePerformance(int[] nums1, int m, int[] nums2, int n, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        int[] nums1Copy1 = nums1.clone();
        long startTime = System.nanoTime();
        merge(nums1Copy1, m, nums2, n);
        long time1 = System.nanoTime() - startTime;
        
        int[] nums1Copy2 = nums1.clone();
        startTime = System.nanoTime();
        mergeWithExtraSpace(nums1Copy2, m, nums2, n);
        long time2 = System.nanoTime() - startTime;
        
        int[] nums1Copy3 = nums1.clone();
        startTime = System.nanoTime();
        mergeAndSort(nums1Copy3, m, nums2, n);
        long time3 = System.nanoTime() - startTime;
        
        int[] nums1Copy4 = nums1.clone();
        startTime = System.nanoTime();
        mergeOptimized(nums1Copy4, m, nums2, n);
        long time4 = System.nanoTime() - startTime;
        
        int[] nums1Copy5 = nums1.clone();
        startTime = System.nanoTime();
        mergeRecursive(nums1Copy5, m, nums2, n);
        long time5 = System.nanoTime() - startTime;
        
        int[] nums1Copy6 = nums1.clone();
        startTime = System.nanoTime();
        mergeGapMethod(nums1Copy6, m, nums2, n);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("Three Pointers (End): %d ns%n", time1);
        System.out.printf("Two Pointers (Extra Space): %d ns%n", time2);
        System.out.printf("Merge and Sort: %d ns%n", time3);
        System.out.printf("Optimized: %d ns%n", time4);
        System.out.printf("Recursive: %d ns%n", time5);
        System.out.printf("Gap Method: %d ns%n", time6);
        
        // Verify all produce same result
        boolean allEqual = Arrays.equals(nums1Copy1, nums1Copy2) && 
                          Arrays.equals(nums1Copy1, nums1Copy3) && 
                          Arrays.equals(nums1Copy1, nums1Copy4) && 
                          Arrays.equals(nums1Copy1, nums1Copy5) && 
                          Arrays.equals(nums1Copy1, nums1Copy6);
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Merge Sorted Array Solution:");
        System.out.println("=====================================");
        
        // Test case 1: Standard case
        System.out.println("\nTest 1: Standard case");
        int[] nums1_1 = {1, 2, 3, 0, 0, 0};
        int m1 = 3;
        int[] nums2_1 = {2, 5, 6};
        int n1 = 3;
        int[] expected1 = {1, 2, 2, 3, 5, 6};
        
        int[] nums1Copy1a = nums1_1.clone();
        solution.merge(nums1Copy1a, m1, nums2_1, n1);
        boolean test1a = Arrays.equals(nums1Copy1a, expected1);
        
        int[] nums1Copy1b = nums1_1.clone();
        solution.mergeWithExtraSpace(nums1Copy1b, m1, nums2_1, n1);
        boolean test1b = Arrays.equals(nums1Copy1b, expected1);
        
        int[] nums1Copy1c = nums1_1.clone();
        solution.mergeAndSort(nums1Copy1c, m1, nums2_1, n1);
        boolean test1c = Arrays.equals(nums1Copy1c, expected1);
        
        System.out.println("Three Pointers: " + Arrays.toString(nums1Copy1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Extra Space: " + Arrays.toString(nums1Copy1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Merge & Sort: " + Arrays.toString(nums1Copy1c) + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the merging process
        solution.visualizeMerge(nums1_1, m1, nums2_1, n1, "Test 1 - Standard Case");
        
        // Test case 2: nums1 empty
        System.out.println("\nTest 2: nums1 empty");
        int[] nums1_2 = {0};
        int m2 = 0;
        int[] nums2_2 = {1};
        int n2 = 1;
        int[] expected2 = {1};
        
        int[] nums1Copy2a = nums1_2.clone();
        solution.merge(nums1Copy2a, m2, nums2_2, n2);
        System.out.println("nums1 empty: " + Arrays.toString(nums1Copy2a) + " - " + 
                         (Arrays.equals(nums1Copy2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: nums2 empty
        System.out.println("\nTest 3: nums2 empty");
        int[] nums1_3 = {1};
        int m3 = 1;
        int[] nums2_3 = {};
        int n3 = 0;
        int[] expected3 = {1};
        
        int[] nums1Copy3a = nums1_3.clone();
        solution.merge(nums1Copy3a, m3, nums2_3, n3);
        System.out.println("nums2 empty: " + Arrays.toString(nums1Copy3a) + " - " + 
                         (Arrays.equals(nums1Copy3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: All elements in nums1 are smaller
        System.out.println("\nTest 4: All elements in nums1 are smaller");
        int[] nums1_4 = {1, 2, 3, 0, 0, 0};
        int m4 = 3;
        int[] nums2_4 = {4, 5, 6};
        int n4 = 3;
        int[] expected4 = {1, 2, 3, 4, 5, 6};
        
        int[] nums1Copy4a = nums1_4.clone();
        solution.merge(nums1Copy4a, m4, nums2_4, n4);
        System.out.println("nums1 all smaller: " + Arrays.toString(nums1Copy4a) + " - " + 
                         (Arrays.equals(nums1Copy4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: All elements in nums2 are smaller
        System.out.println("\nTest 5: All elements in nums2 are smaller");
        int[] nums1_5 = {4, 5, 6, 0, 0, 0};
        int m5 = 3;
        int[] nums2_5 = {1, 2, 3};
        int n5 = 3;
        int[] expected5 = {1, 2, 3, 4, 5, 6};
        
        int[] nums1Copy5a = nums1_5.clone();
        solution.merge(nums1Copy5a, m5, nums2_5, n5);
        System.out.println("nums2 all smaller: " + Arrays.toString(nums1Copy5a) + " - " + 
                         (Arrays.equals(nums1Copy5a, expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Mixed elements with duplicates
        System.out.println("\nTest 6: Mixed elements with duplicates");
        int[] nums1_6 = {1, 3, 5, 0, 0, 0};
        int m6 = 3;
        int[] nums2_6 = {2, 3, 4};
        int n6 = 3;
        int[] expected6 = {1, 2, 3, 3, 4, 5};
        
        int[] nums1Copy6a = nums1_6.clone();
        solution.merge(nums1Copy6a, m6, nums2_6, n6);
        System.out.println("Mixed with duplicates: " + Arrays.toString(nums1Copy6a) + " - " + 
                         (Arrays.equals(nums1Copy6a, expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Single elements
        System.out.println("\nTest 7: Single elements");
        int[] nums1_7 = {2, 0};
        int m7 = 1;
        int[] nums2_7 = {1};
        int n7 = 1;
        int[] expected7 = {1, 2};
        
        int[] nums1Copy7a = nums1_7.clone();
        solution.merge(nums1Copy7a, m7, nums2_7, n7);
        System.out.println("Single elements: " + Arrays.toString(nums1Copy7a) + " - " + 
                         (Arrays.equals(nums1Copy7a, expected7) ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(nums1_1, m1, nums2_1, n1, "Small Input (3+3 elements)");
        
        // Medium input performance
        int[] mediumNums1 = new int[100];
        int[] mediumNums2 = new int[50];
        Arrays.fill(mediumNums1, 0, 50, 1);
        Arrays.fill(mediumNums2, 2);
        solution.comparePerformance(mediumNums1, 50, mediumNums2, 50, "Medium Input (50+50 elements)");
        
        // Large input performance
        int[] largeNums1 = new int[200];
        int[] largeNums2 = new int[100];
        Arrays.fill(largeNums1, 0, 100, 1);
        Arrays.fill(largeNums2, 2);
        solution.comparePerformance(largeNums1, 100, largeNums2, 100, "Large Input (100+100 elements)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Three Pointers from End - RECOMMENDED:");
        System.out.println("   Time: O(m + n) - Single pass through both arrays");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Start pointers from the end of both arrays");
        System.out.println("     - Compare elements and place larger at the end");
        System.out.println("     - Copy remaining elements from nums2 if any");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - In-place modification");
        System.out.println("     - No extra memory required");
        System.out.println("     - Most efficient solution");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of working backwards");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Two Pointers with Extra Space:");
        System.out.println("   Time: O(m + n) - Single pass through both arrays");
        System.out.println("   Space: O(m) - Copy of nums1's real elements");
        System.out.println("   How it works:");
        System.out.println("     - Create copy of nums1's real elements");
        System.out.println("     - Merge using three pointers from start");
        System.out.println("     - Copy remaining elements from either array");
        System.out.println("   Pros:");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(m) extra space");
        System.out.println("     - Not truly in-place");
        System.out.println("   Best for: Learning the merge concept");
        
        System.out.println("\n3. Merge and Sort:");
        System.out.println("   Time: O((m + n) log(m + n)) - Dominated by sorting");
        System.out.println("   Space: O(1) or O(log(m + n)) - Depending on sort algorithm");
        System.out.println("   How it works:");
        System.out.println("     - Copy nums2 to the end of nums1");
        System.out.println("     - Sort the entire nums1 array");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple implementation");
        System.out.println("     - Only 2 lines of code");
        System.out.println("   Cons:");
        System.out.println("     - Inefficient for large inputs");
        System.out.println("     - Doesn't leverage the sorted property");
        System.out.println("   Best for: Quick prototyping, very small inputs");
        
        System.out.println("\n4. Optimized Version:");
        System.out.println("   Time: O(m + n) - Single pass through both arrays");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Similar to three pointers but optimized loop");
        System.out.println("     - Only check nums2 elements in main loop");
        System.out.println("   Pros:");
        System.out.println("     - Slightly more efficient");
        System.out.println("     - Cleaner loop condition");
        System.out.println("   Cons:");
        System.out.println("     - Slightly less intuitive");
        System.out.println("   Best for: Performance-critical code");
        
        System.out.println("\n5. Recursive Solution:");
        System.out.println("   Time: O(m + n) - Recursive calls for each element");
        System.out.println("   Space: O(m + n) - Stack space for recursion");
        System.out.println("   How it works:");
        System.out.println("     - Recursive function with three pointers");
        System.out.println("     - Base case: both arrays exhausted");
        System.out.println("     - Recursive case: compare and place elements");
        System.out.println("   Pros:");
        System.out.println("     - Elegant recursive formulation");
        System.out.println("     - Good for learning recursion");
        System.out.println("   Cons:");
        System.out.println("     - Stack space overhead");
        System.out.println("     - Risk of stack overflow");
        System.out.println("     - Less efficient than iterative");
        System.out.println("   Best for: Educational purposes, small inputs");
        
        System.out.println("\n6. Gap Method:");
        System.out.println("   Time: O((m + n) log(m + n)) - Multiple passes with decreasing gap");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Copy nums2 to nums1");
        System.out.println("     - Use gap method for in-place merge sort");
        System.out.println("     - Compare and swap elements with gap distance");
        System.out.println("   Pros:");
        System.out.println("     - True in-place merge");
        System.out.println("     - No extra space required");
        System.out.println("   Cons:");
        System.out.println("     - Less efficient than three pointers");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: Learning alternative merge algorithms");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY WORKING BACKWARDS IS CRUCIAL:");
        System.out.println("1. nums1 has extra space at the END, not at the beginning");
        System.out.println("2. If we start from the beginning, we overwrite elements in nums1");
        System.out.println("3. By starting from the end, we use the extra space first");
        System.out.println("4. This ensures we never overwrite unprocessed elements");
        System.out.println("5. The algorithm is both time and space optimal");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. nums1 empty (m=0): copy all elements from nums2");
        System.out.println("2. nums2 empty (n=0): nums1 remains unchanged");
        System.out.println("3. Single elements: handle comparison correctly");
        System.out.println("4. All elements in one array are smaller: copy remaining");
        System.out.println("5. Duplicate elements: maintain stability if required");
        System.out.println("6. Maximum constraints: handle 200 elements efficiently");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Three Pointers from End approach");
        System.out.println("2. Explain WHY working backwards is necessary");
        System.out.println("3. Mention time/space complexity: O(m + n)/O(1)");
        System.out.println("4. Handle edge cases explicitly in code");
        System.out.println("5. Discuss alternative approaches and trade-offs");
        System.out.println("6. Write clean, readable code with good variable names");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Starting from the beginning (overwrites elements)");
        System.out.println("2. Using O(m) or O(n) extra space unnecessarily");
        System.out.println("3. Not handling empty array cases");
        System.out.println("4. Incorrect pointer management");
        System.out.println("5. Forgetting to copy remaining elements from nums2");
        System.out.println("6. Using inefficient sorting when linear merge is possible");
        System.out.println("=".repeat(70));
        
        // Pattern recognition and related problems
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PATTERN RECOGNITION:");
        System.out.println("1. Two/Three Pointers: When merging sorted sequences");
        System.out.println("2. Working Backwards: When you have space at the end");
        System.out.println("3. In-place Modification: Common constraint in array problems");
        System.out.println("4. Merge Algorithm: Fundamental computer science concept");
        System.out.println("5. Sorted Arrays: Leverage sorted property for efficiency");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
