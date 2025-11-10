
## Solution.java

```java
/**
 * 153. Find Minimum in Rotated Sorted Array
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you.
 * Find the minimum element. The array may contain unique elements.
 * 
 * Key Insights:
 * 1. The array has two sorted portions with a rotation point (minimum element)
 * 2. Use binary search by comparing middle element with rightmost element
 * 3. If nums[mid] > nums[right], search right half (min is after mid)
 * 4. If nums[mid] < nums[right], search left half including mid (min could be mid or before)
 * 5. The minimum is the only element smaller than both neighbors
 * 
 * Approach (Modified Binary Search):
 * 1. Initialize left = 0, right = n-1
 * 2. While left < right, calculate mid
 * 3. If nums[mid] > nums[right], search right: left = mid + 1
 * 4. Else, search left including mid: right = mid
 * 5. When loop ends, nums[left] is the minimum
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search
 */

class Solution {
    /**
     * Approach 1: Binary Search Comparing with Right Element - RECOMMENDED
     * O(log n) time, O(1) space - Optimal solution
     */
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                // Minimum is in the right half (after mid)
                left = mid + 1;
            } else {
                // Minimum is in the left half (could be mid)
                right = mid;
            }
        }
        
        return nums[left];
    }
    
    /**
     * Approach 2: Binary Search Comparing with Left Element
     * Alternative approach comparing with left element
     */
    public int findMinCompareLeft(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        // If array is not rotated, first element is minimum
        if (nums[left] <= nums[right]) {
            return nums[left];
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] >= nums[0]) {
                // Mid is in left sorted portion, search right
                left = mid + 1;
            } else {
                // Mid is in right sorted portion, search left including mid
                right = mid;
            }
        }
        
        return nums[left];
    }
    
    /**
     * Approach 3: Binary Search with Early Exit
     * Checks if current segment is sorted for early exit
     */
    public int findMinEarlyExit(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            // Early exit if current segment is sorted
            if (nums[left] <= nums[right]) {
                return nums[left];
            }
            
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return nums[left];
    }
    
    /**
     * Approach 4: Linear Scan (Not Recommended - for comparison only)
     * O(n) time - Too slow, but simple to understand
     */
    public int findMinLinear(int[] nums) {
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < min) {
                min = nums[i];
            }
        }
        return min;
    }
    
    /**
     * Approach 5: Binary Search Finding Rotation Point
     * Explicitly finds the rotation point
     */
    public int findMinRotationPoint(int[] nums) {
        int n = nums.length;
        
        // If array is not rotated
        if (nums[0] <= nums[n - 1]) {
            return nums[0];
        }
        
        int left = 0;
        int right = n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Check if mid is the rotation point
            if (mid > 0 && nums[mid] < nums[mid - 1]) {
                return nums[mid];
            }
            if (mid < n - 1 && nums[mid] > nums[mid + 1]) {
                return nums[mid + 1];
            }
            
            if (nums[mid] >= nums[0]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return nums[0];
    }
    
    /**
     * Approach 6: Recursive Binary Search
     * O(log n) time, O(log n) space due to recursion
     */
    public int findMinRecursive(int[] nums) {
        return findMinRecursive(nums, 0, nums.length - 1);
    }
    
    private int findMinRecursive(int[] nums, int left, int right) {
        // Base case: single element or sorted segment
        if (left == right || nums[left] <= nums[right]) {
            return nums[left];
        }
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] > nums[right]) {
            return findMinRecursive(nums, mid + 1, right);
        } else {
            return findMinRecursive(nums, left, mid);
        }
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    private void visualizeBinarySearch(int[] nums) {
        System.out.println("\nBinary Search Visualization:");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Length: " + nums.length);
        
        int left = 0;
        int right = nums.length - 1;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Mid | nums[Mid] | nums[Right] | Comparison | Action");
        System.out.println("-----|------|-------|-----|-----------|-------------|------------|--------");
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            String comparison = nums[mid] + " ? " + nums[right];
            String action;
            
            if (nums[mid] > nums[right]) {
                action = "nums[mid] > nums[right], search RIGHT (left = mid + 1)";
                left = mid + 1;
            } else {
                action = "nums[mid] < nums[right], search LEFT including mid (right = mid)";
                right = mid;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %9d | %11d | %10s | %s%n", 
                            step, left, right, mid, nums[mid], nums[right], comparison, action);
            step++;
        }
        
        System.out.println("\nFinal Result: Minimum element = nums[" + left + "] = " + nums[left]);
        
        // Verify by checking neighbors
        if (left > 0) {
            System.out.println("Left neighbor: nums[" + (left-1) + "] = " + nums[left-1]);
        }
        if (left < nums.length - 1) {
            System.out.println("Right neighbor: nums[" + (left+1) + "] = " + nums[left+1]);
        }
        
        // Check if it's indeed the minimum
        boolean isMinimum = true;
        if (left > 0 && nums[left] > nums[left-1]) {
            isMinimum = false;
        }
        if (left < nums.length - 1 && nums[left] > nums[left+1]) {
            isMinimum = false;
        }
        System.out.println("Verification: " + (isMinimum ? "PASSED" : "FAILED"));
    }
    
    /**
     * Helper method to analyze array properties
     */
    private void analyzeArray(int[] nums) {
        System.out.println("\nArray Analysis:");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        
        int rotationPoint = -1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i-1]) {
                rotationPoint = i;
                break;
            }
        }
        
        if (rotationPoint == -1) {
            System.out.println("Array is not rotated (or rotated n times)");
            System.out.println("Minimum element: " + nums[0] + " at index 0");
        } else {
            System.out.println("Array is rotated at index: " + rotationPoint);
            System.out.println("Minimum element: " + nums[rotationPoint] + " at index " + rotationPoint);
            System.out.println("Left sorted portion: [0, " + (rotationPoint-1) + "]");
            System.out.println("Right sorted portion: [" + rotationPoint + ", " + (nums.length-1) + "]");
        }
        
        // Show the two sorted portions
        if (rotationPoint != -1) {
            System.out.println("Left portion: " + 
                java.util.Arrays.toString(java.util.Arrays.copyOfRange(nums, 0, rotationPoint)));
            System.out.println("Right portion: " + 
                java.util.Arrays.toString(java.util.Arrays.copyOfRange(nums, rotationPoint, nums.length)));
        }
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] nums) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Length: " + nums.length);
        System.out.println("=================================");
        
        long startTime, endTime;
        int result;
        
        // Standard Binary Search
        startTime = System.nanoTime();
        result = findMin(nums);
        endTime = System.nanoTime();
        System.out.printf("Standard Binary: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Compare with Left
        startTime = System.nanoTime();
        result = findMinCompareLeft(nums);
        endTime = System.nanoTime();
        System.out.printf("Compare Left:    %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Early Exit
        startTime = System.nanoTime();
        result = findMinEarlyExit(nums);
        endTime = System.nanoTime();
        System.out.printf("Early Exit:      %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Rotation Point
        startTime = System.nanoTime();
        result = findMinRotationPoint(nums);
        endTime = System.nanoTime();
        System.out.printf("Rotation Point:  %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Recursive
        startTime = System.nanoTime();
        result = findMinRecursive(nums);
        endTime = System.nanoTime();
        System.out.printf("Recursive:       %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Linear Search (only for small arrays)
        if (nums.length <= 1000) {
            startTime = System.nanoTime();
            result = findMinLinear(nums);
            endTime = System.nanoTime();
            System.out.printf("Linear Search:   %8d ns, Result: %d%n", (endTime - startTime), result);
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Find Minimum in Rotated Sorted Array:");
        System.out.println("=============================================");
        
        // Test case 1: Standard rotated array
        System.out.println("\nTest 1: Standard rotated array");
        int[] nums1 = {3, 4, 5, 1, 2};
        int result1 = solution.findMin(nums1);
        System.out.println("Result: " + result1 + " - " + (result1 == 1 ? "PASSED" : "FAILED"));
        solution.visualizeBinarySearch(nums1);
        solution.analyzeArray(nums1);
        
        // Test case 2: Another rotated array
        System.out.println("\nTest 2: Another rotated array");
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        int result2 = solution.findMin(nums2);
        System.out.println("Result: " + result2 + " - " + (result2 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 3: Not rotated array
        System.out.println("\nTest 3: Not rotated array");
        int[] nums3 = {11, 13, 15, 17};
        int result3 = solution.findMin(nums3);
        System.out.println("Result: " + result3 + " - " + (result3 == 11 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single element
        System.out.println("\nTest 4: Single element");
        int[] nums4 = {5};
        int result4 = solution.findMin(nums4);
        System.out.println("Result: " + result4 + " - " + (result4 == 5 ? "PASSED" : "FAILED"));
        
        // Test case 5: Two elements, rotated
        System.out.println("\nTest 5: Two elements, rotated");
        int[] nums5 = {2, 1};
        int result5 = solution.findMin(nums5);
        System.out.println("Result: " + result5 + " - " + (result5 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 6: Two elements, not rotated
        System.out.println("\nTest 6: Two elements, not rotated");
        int[] nums6 = {1, 2};
        int result6 = solution.findMin(nums6);
        System.out.println("Result: " + result6 + " - " + (result6 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 7: Minimum at beginning (not rotated)
        System.out.println("\nTest 7: Minimum at beginning (not rotated)");
        int[] nums7 = {0, 1, 2, 3, 4};
        int result7 = solution.findMin(nums7);
        System.out.println("Result: " + result7 + " - " + (result7 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 8: Minimum at end (rotated n-1 times)
        System.out.println("\nTest 8: Minimum at end (rotated n-1 times)");
        int[] nums8 = {1, 2, 3, 4, 0};
        int result8 = solution.findMin(nums8);
        System.out.println("Result: " + result8 + " - " + (result8 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 9: Large rotated array
        System.out.println("\nTest 9: Large rotated array");
        int[] nums9 = new int[1000];
        for (int i = 0; i < nums9.length; i++) {
            nums9[i] = (i + 300) % 1000; // Rotate by 300
        }
        int result9 = solution.findMin(nums9);
        System.out.println("Result: " + result9 + " - " + (result9 == 0 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 10: Performance Comparison - Small Array");
        solution.compareApproaches(nums1);
        
        System.out.println("\nTest 11: Performance Comparison - Large Array");
        solution.compareApproaches(nums9);
        
        // Additional visualization
        System.out.println("\nTest 12: Additional Visualization Examples");
        int[] nums12a = {5, 1, 2, 3, 4};
        System.out.println("\nExample: [5, 1, 2, 3, 4]");
        solution.visualizeBinarySearch(nums12a);
        
        int[] nums12b = {2, 3, 4, 5, 1};
        System.out.println("\nExample: [2, 3, 4, 5, 1]");
        solution.visualizeBinarySearch(nums12b);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("In a rotated sorted array, there are two sorted portions:");
        System.out.println("1. Left portion: from start to rotation point-1");
        System.out.println("2. Right portion: from rotation point to end");
        System.out.println("The minimum element is at the beginning of the right portion.");
        
        System.out.println("\nWhy compare with right element?");
        System.out.println("If nums[mid] > nums[right]:");
        System.out.println("  - Mid is in left portion, minimum is after mid");
        System.out.println("  - Search right: left = mid + 1");
        System.out.println("If nums[mid] < nums[right]:");
        System.out.println("  - Mid is in right portion, minimum could be mid or before");
        System.out.println("  - Search left including mid: right = mid");
        
        System.out.println("\nVisual Example: [4,5,6,7,0,1,2]");
        System.out.println("Left portion: [4,5,6,7] (all > nums[right]=2)");
        System.out.println("Right portion: [0,1,2] (all <= nums[right]=2)");
        System.out.println("When mid points to 7 (index 3): 7 > 2 → search right");
        System.out.println("When mid points to 0 (index 4): 0 < 2 → search left including mid");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Binary Search Comparing with Right (RECOMMENDED):");
        System.out.println("   Time: O(log n) - Standard binary search");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Compare middle element with rightmost element");
        System.out.println("     - If mid > right: search right half");
        System.out.println("     - Else: search left half including mid");
        System.out.println("   Pros:");
        System.out.println("     - Simple and elegant");
        System.out.println("     - Handles all cases correctly");
        System.out.println("     - Easy to remember and implement");
        System.out.println("   Cons:");
        System.out.println("     - None significant");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Binary Search Comparing with Left:");
        System.out.println("   Time: O(log n) - Same as standard");
        System.out.println("   Space: O(1) - Same as standard");
        System.out.println("   How it works:");
        System.out.println("     - Compare middle element with leftmost element");
        System.out.println("     - If mid >= left: search right half");
        System.out.println("     - Else: search left half including mid");
        System.out.println("   Pros:");
        System.out.println("     - Alternative approach");
        System.out.println("     - Works correctly");
        System.out.println("   Cons:");
        System.out.println("     - Requires checking if array is rotated first");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: Learning alternative approaches");
        
        System.out.println("\n3. Binary Search with Early Exit:");
        System.out.println("   Time: O(log n) - Same as standard");
        System.out.println("   Space: O(1) - Same as standard");
        System.out.println("   How it works:");
        System.out.println("     - Check if current segment is sorted at each step");
        System.out.println("     - If sorted, return left element immediately");
        System.out.println("   Pros:");
        System.out.println("     - Can be faster for nearly sorted arrays");
        System.out.println("     - Early termination optimization");
        System.out.println("   Cons:");
        System.out.println("     - Extra comparison at each step");
        System.out.println("     - Minimal performance gain in practice");
        System.out.println("   Best for: Arrays that are often not rotated");
        
        System.out.println("\n4. Linear Search (NOT RECOMMENDED):");
        System.out.println("   Time: O(n) - Scan all elements");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Iterate through array to find minimum");
        System.out.println("     - Or find the point where nums[i] < nums[i-1]");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(n) doesn't meet O(log n) requirement");
        System.out.println("     - Inefficient for large arrays");
        System.out.println("   Best for: Very small arrays, educational purposes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. The minimum element is the only element smaller than both neighbors");
        System.out.println("2. In rotated array: all elements in left portion > all elements in right portion");
        System.out.println("3. The rotation point creates a 'cliff' where array decreases suddenly");
        System.out.println("4. Binary search iterations: ⌊log₂(n)⌋");
        System.out.println("5. For n=5000: maximum 13 iterations");
        System.out.println("6. Linear search would require up to 5000 comparisons");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Recognize this as a rotated sorted array problem");
        System.out.println("2. Explain the two sorted portions and rotation point");
        System.out.println("3. Implement binary search comparing with right element");
        System.out.println("4. Handle edge cases: single element, two elements, not rotated");
        System.out.println("5. Discuss why comparing with right works (properties of rotated array)");
        System.out.println("6. Mention time complexity O(log n) and space complexity O(1)");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
