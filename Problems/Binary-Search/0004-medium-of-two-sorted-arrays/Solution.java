
## Solution.java

```java
/**
 * 4. Median of Two Sorted Arrays
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given two sorted arrays nums1 and nums2 of size m and n respectively,
 * return the median of the two sorted arrays with O(log(m+n)) time complexity.
 * 
 * Key Insights:
 * 1. Median divides array into two equal halves (or left has one more)
 * 2. We can use binary search to find correct partition points
 * 3. Search on smaller array for efficiency
 * 4. Partition should satisfy: maxLeft <= minRight
 * 
 * Approach (Binary Search):
 * 1. Ensure nums1 is the smaller array (swap if needed)
 * 2. Binary search on nums1 to find partition point
 * 3. Calculate partition for nums2 based on nums1 partition
 * 4. Check if partition is valid (maxLeft <= minRight)
 * 5. Adjust search range based on partition validity
 * 6. Calculate median based on partition boundaries
 * 
 * Time Complexity: O(log(min(m,n)))
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search, Divide and Conquer
 */

class Solution {
    
    /**
     * Approach 1: Binary Search on Smaller Array (RECOMMENDED)
     * O(log(min(m,n))) time, O(1) space - Optimal solution
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array for binary search efficiency
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        int totalLeft = (m + n + 1) / 2; // Size of left partition
        
        // Binary search on nums1 (smaller array)
        int left = 0;
        int right = m;
        
        while (left <= right) {
            // Partition point for nums1
            int partition1 = left + (right - left) / 2;
            // Partition point for nums2 (based on nums1 partition)
            int partition2 = totalLeft - partition1;
            
            // Handle boundary cases for nums1
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];
            
            // Handle boundary cases for nums2
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];
            
            // Check if partition is valid
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // Valid partition found
                if ((m + n) % 2 == 1) {
                    // Odd total length
                    return Math.max(maxLeft1, maxLeft2);
                } else {
                    // Even total length
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                }
            } else if (maxLeft1 > minRight2) {
                // nums1's left partition is too big, move left
                right = partition1 - 1;
            } else {
                // nums1's left partition is too small, move right
                left = partition1 + 1;
            }
        }
        
        throw new IllegalArgumentException("Input arrays are not sorted");
    }
    
    /**
     * Approach 2: Merge and Find Median (Brute Force)
     * O(m+n) time, O(m+n) space - For comparison and understanding
     */
    public double findMedianSortedArraysBruteForce(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] merged = new int[m + n];
        
        int i = 0, j = 0, k = 0;
        
        // Merge the two arrays
        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }
        
        // Copy remaining elements
        while (i < m) {
            merged[k++] = nums1[i++];
        }
        while (j < n) {
            merged[k++] = nums2[j++];
        }
        
        // Find median
        int total = m + n;
        if (total % 2 == 1) {
            return merged[total / 2];
        } else {
            return (merged[total / 2 - 1] + merged[total / 2]) / 2.0;
        }
    }
    
    /**
     * Approach 3: Two Pointers without Extra Space
     * O(m+n) time, O(1) space - Space optimized version of brute force
     */
    public double findMedianSortedArraysTwoPointers(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;
        int median1 = -1, median2 = -1;
        
        int i = 0, j = 0, count = 0;
        
        while (count <= total / 2) {
            median2 = median1; // Store previous value for even case
            
            if (i < m && j < n) {
                if (nums1[i] <= nums2[j]) {
                    median1 = nums1[i++];
                } else {
                    median1 = nums2[j++];
                }
            } else if (i < m) {
                median1 = nums1[i++];
            } else {
                median1 = nums2[j++];
            }
            count++;
        }
        
        if (total % 2 == 1) {
            return median1;
        } else {
            return (median1 + median2) / 2.0;
        }
    }
    
    /**
     * Approach 4: Binary Search for Kth Element
     * O(log(m+n)) time, O(log(m+n)) space - Recursive approach
     */
    public double findMedianSortedArraysKth(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;
        
        if (total % 2 == 1) {
            // Odd total length - find single median element
            return findKthElement(nums1, 0, nums2, 0, total / 2 + 1);
        } else {
            // Even total length - find two median elements
            double left = findKthElement(nums1, 0, nums2, 0, total / 2);
            double right = findKthElement(nums1, 0, nums2, 0, total / 2 + 1);
            return (left + right) / 2.0;
        }
    }
    
    private double findKthElement(int[] nums1, int start1, int[] nums2, int start2, int k) {
        // If nums1 is exhausted, return kth element from nums2
        if (start1 >= nums1.length) {
            return nums2[start2 + k - 1];
        }
        
        // If nums2 is exhausted, return kth element from nums1
        if (start2 >= nums2.length) {
            return nums1[start1 + k - 1];
        }
        
        // If k == 1, return the smaller of first elements
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }
        
        // Calculate mid points for both arrays
        int mid1 = start1 + k / 2 - 1;
        int mid2 = start2 + k / 2 - 1;
        
        int val1 = (mid1 < nums1.length) ? nums1[mid1] : Integer.MAX_VALUE;
        int val2 = (mid2 < nums2.length) ? nums2[mid2] : Integer.MAX_VALUE;
        
        // Eliminate the smaller half
        if (val1 <= val2) {
            return findKthElement(nums1, mid1 + 1, nums2, start2, k - k / 2);
        } else {
            return findKthElement(nums1, start1, nums2, mid2 + 1, k - k / 2);
        }
    }
    
    /**
     * Helper method to visualize the partition
     */
    private void visualizePartition(int[] nums1, int[] nums2, int partition1, int partition2) {
        System.out.println("\nPartition Visualization:");
        System.out.println("nums1: " + java.util.Arrays.toString(nums1));
        System.out.println("nums2: " + java.util.Arrays.toString(nums2));
        System.out.println("Partition1 (nums1): " + partition1);
        System.out.println("Partition2 (nums2): " + partition2);
        
        // Calculate boundary values
        int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
        int minRight1 = (partition1 == nums1.length) ? Integer.MAX_VALUE : nums1[partition1];
        int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
        int minRight2 = (partition2 == nums2.length) ? Integer.MAX_VALUE : nums2[partition2];
        
        System.out.println("Left Partition:");
        System.out.println("  nums1 left: " + ((partition1 == 0) ? "[]" : 
            java.util.Arrays.toString(java.util.Arrays.copyOfRange(nums1, 0, partition1))));
        System.out.println("  nums2 left: " + ((partition2 == 0) ? "[]" : 
            java.util.Arrays.toString(java.util.Arrays.copyOfRange(nums2, 0, partition2))));
        System.out.println("  MaxLeft1: " + maxLeft1 + ", MaxLeft2: " + maxLeft2);
        
        System.out.println("Right Partition:");
        System.out.println("  nums1 right: " + ((partition1 == nums1.length) ? "[]" : 
            java.util.Arrays.toString(java.util.Arrays.copyOfRange(nums1, partition1, nums1.length))));
        System.out.println("  nums2 right: " + ((partition2 == nums2.length) ? "[]" : 
            java.util.Arrays.toString(java.util.Arrays.copyOfRange(nums2, partition2, nums2.length))));
        System.out.println("  MinRight1: " + minRight1 + ", MinRight2: " + minRight2);
        
        boolean isValid = (maxLeft1 <= minRight2) && (maxLeft2 <= minRight1);
        System.out.println("Partition Valid: " + isValid);
        System.out.println("Condition: maxLeft1 <= minRight2 && maxLeft2 <= minRight1");
        System.out.println("Actual: " + maxLeft1 + " <= " + minRight2 + " && " + maxLeft2 + " <= " + minRight1);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Median of Two Sorted Arrays Solution:");
        System.out.println("==============================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int[] nums1_1 = {1, 3};
        int[] nums2_1 = {2};
        double expected1 = 2.0;
        
        long startTime = System.nanoTime();
        double result1a = solution.findMedianSortedArrays(nums1_1, nums2_1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        double result1b = solution.findMedianSortedArraysBruteForce(nums1_1, nums2_1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        double result1c = solution.findMedianSortedArraysTwoPointers(nums1_1, nums2_1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        double result1d = solution.findMedianSortedArraysKth(nums1_1, nums2_1);
        long time1d = System.nanoTime() - startTime;
        
        System.out.println("Binary Search: " + result1a + " - " + 
                         (Math.abs(result1a - expected1) < 1e-9 ? "PASSED" : "FAILED"));
        System.out.println("Brute Force: " + result1b + " - " + 
                         (Math.abs(result1b - expected1) < 1e-9 ? "PASSED" : "FAILED"));
        System.out.println("Two Pointers: " + result1c + " - " + 
                         (Math.abs(result1c - expected1) < 1e-9 ? "PASSED" : "FAILED"));
        System.out.println("Kth Element: " + result1d + " - " + 
                         (Math.abs(result1d - expected1) < 1e-9 ? "PASSED" : "FAILED"));
        
        // Visualize the partition
        solution.visualizePartition(nums1_1, nums2_1, 1, 0);
        
        // Test case 2: Even total length
        System.out.println("\nTest 2: Even total length");
        int[] nums1_2 = {1, 2};
        int[] nums2_2 = {3, 4};
        double expected2 = 2.5;
        
        double result2a = solution.findMedianSortedArrays(nums1_2, nums2_2);
        System.out.println("Even length: " + result2a + " - " + 
                         (Math.abs(result2a - expected2) < 1e-9 ? "PASSED" : "FAILED"));
        solution.visualizePartition(nums1_2, nums2_2, 1, 1);
        
        // Test case 3: First array empty
        System.out.println("\nTest 3: First array empty");
        int[] nums1_3 = {};
        int[] nums2_3 = {1};
        double expected3 = 1.0;
        
        double result3a = solution.findMedianSortedArrays(nums1_3, nums2_3);
        System.out.println("Empty nums1: " + result3a + " - " + 
                         (Math.abs(result3a - expected3) < 1e-9 ? "PASSED" : "FAILED"));
        
        // Test case 4: Second array empty
        System.out.println("\nTest 4: Second array empty");
        int[] nums1_4 = {1, 2, 3};
        int[] nums2_4 = {};
        double expected4 = 2.0;
        
        double result4a = solution.findMedianSortedArrays(nums1_4, nums2_4);
        System.out.println("Empty nums2: " + result4a + " - " + 
                         (Math.abs(result4a - expected4) < 1e-9 ? "PASSED" : "FAILED"));
        
        // Test case 5: Both arrays empty (edge case)
        System.out.println("\nTest 5: Both arrays empty");
        int[] nums1_5 = {};
        int[] nums2_5 = {};
        double expected5 = 0.0;
        
        try {
            double result5a = solution.findMedianSortedArrays(nums1_5, nums2_5);
            System.out.println("Both empty: " + result5a + " - " + 
                             (Math.abs(result5a - expected5) < 1e-9 ? "PASSED" : "FAILED"));
        } catch (Exception e) {
            System.out.println("Both empty: Exception caught - " + e.getMessage());
        }
        
        // Test case 6: Arrays with negative numbers
        System.out.println("\nTest 6: Arrays with negative numbers");
        int[] nums1_6 = {-5, -3, -1};
        int[] nums2_6 = {-2, 0, 2};
        double expected6 = -1.0;
        
        double result6a = solution.findMedianSortedArrays(nums1_6, nums2_6);
        System.out.println("Negative numbers: " + result6a + " - " + 
                         (Math.abs(result6a - expected6) < 1e-9 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large arrays
        System.out.println("\nTest 7: Large arrays");
        int[] nums1_7 = new int[500];
        int[] nums2_7 = new int[500];
        
        // Fill with sorted values
        for (int i = 0; i < 500; i++) {
            nums1_7[i] = 2 * i;        // Even numbers: 0, 2, 4, ...
            nums2_7[i] = 2 * i + 1;    // Odd numbers: 1, 3, 5, ...
        }
        
        startTime = System.nanoTime();
        double result7a = solution.findMedianSortedArrays(nums1_7, nums2_7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        double result7b = solution.findMedianSortedArraysBruteForce(nums1_7, nums2_7);
        long time7b = System.nanoTime() - startTime;
        
        System.out.println("Large arrays - Binary Search: " + result7a + " (" + time7a + " ns)");
        System.out.println("Large arrays - Brute Force: " + result7b + " (" + time7b + " ns)");
        System.out.println("Expected median: 499.5");
        System.out.println("Binary Search faster: " + (time7a < time7b));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("Test 1 (small):");
        System.out.println("  Binary Search: " + time1a + " ns");
        System.out.println("  Brute Force: " + time1b + " ns");
        System.out.println("  Two Pointers: " + time1c + " ns");
        System.out.println("  Kth Element: " + time1d + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nBinary Search Approach:");
        System.out.println("1. We want to partition both arrays into left and right halves");
        System.out.println("2. Left half should contain the first (m+n+1)/2 elements");
        System.out.println("3. We binary search on the smaller array to find partition point");
        System.out.println("4. For valid partition: maxLeft1 <= minRight2 AND maxLeft2 <= minRight1");
        System.out.println("5. If maxLeft1 > minRight2 → move partition left");
        System.out.println("6. If maxLeft2 > minRight1 → move partition right");
        
        System.out.println("\nExample: nums1 = [1,3], nums2 = [2]");
        System.out.println("Total length = 3 (odd), median is 2nd element (index 1)");
        System.out.println("We search for partition where:");
        System.out.println("  left_size = (3+1)/2 = 2 elements");
        System.out.println("  nums1 partition at 1: [1 | 3]");
        System.out.println("  nums2 partition at 1: [2 | ]");
        System.out.println("  maxLeft = max(1,2) = 2, minRight = min(3,∞) = 3");
        System.out.println("  Valid partition: 2 <= 3");
        
        // Mathematical proof
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY BINARY SEARCH WORKS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Observations:");
        System.out.println("1. Median divides the combined array into two equal halves");
        System.out.println("2. All elements in left half <= all elements in right half");
        System.out.println("3. We can find this division using binary search");
        System.out.println("4. Searching on smaller array gives O(log(min(m,n))) complexity");
        
        System.out.println("\nPartition Conditions:");
        System.out.println("Let partition1 = i (nums1 split), partition2 = j (nums2 split)");
        System.out.println("We require: i + j = (m+n+1)/2");
        System.out.println("And: nums1[i-1] <= nums2[j] AND nums2[j-1] <= nums1[i]");
        System.out.println("This ensures all left elements <= all right elements");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Binary Search (RECOMMENDED):");
        System.out.println("   Time: O(log(min(m,n))) - Optimal");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Meets O(log(m+n)) requirement");
        System.out.println("     - Most efficient for large inputs");
        System.out.println("     - Elegant mathematical approach");
        System.out.println("   Cons:");
        System.out.println("     - Complex to implement correctly");
        System.out.println("     - Many edge cases to handle");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Brute Force (Merge Arrays):");
        System.out.println("   Time: O(m+n) - Too slow for requirements");
        System.out.println("   Space: O(m+n) - Extra space for merged array");
        System.out.println("   Pros:");
        System.out.println("     - Simple to implement and understand");
        System.out.println("     - Easy to debug");
        System.out.println("   Cons:");
        System.out.println("     - Doesn't meet time complexity requirement");
        System.out.println("     - Uses extra space");
        System.out.println("   Best for: Understanding the problem, small inputs");
        
        System.out.println("\n3. Two Pointers:");
        System.out.println("   Time: O(m+n) - Still linear time");
        System.out.println("   Space: O(1) - Space optimized");
        System.out.println("   Pros:");
        System.out.println("     - No extra space needed");
        System.out.println("     - Simple iteration");
        System.out.println("   Cons:");
        System.out.println("     - Still O(m+n) time complexity");
        System.out.println("     - Not optimal for large inputs");
        System.out.println("   Best for: Space-constrained environments");
        
        System.out.println("\n4. Kth Element (Recursive):");
        System.out.println("   Time: O(log(m+n)) - Meets requirement");
        System.out.println("   Space: O(log(m+n)) - Recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive recursive approach");
        System.out.println("     - Generalizes to finding kth element");
        System.out.println("   Cons:");
        System.out.println("     - Recursion overhead");
        System.out.println("     - Stack space usage");
        System.out.println("   Best for: Learning recursive thinking");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Start with brute force explanation (if asked)");
        System.out.println("2. Explain why O(m+n) is not optimal");
        System.out.println("3. Introduce binary search intuition");
        System.out.println("4. Draw the partition diagram");
        System.out.println("5. Explain the boundary conditions carefully");
        System.out.println("6. Handle edge cases: empty arrays, single elements");
        System.out.println("7. Test with provided examples");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("1. Forgetting to handle empty arrays");
        System.out.println("2. Incorrect partition index calculations");
        System.out.println("3. Not considering Integer.MIN_VALUE/MAX_VALUE for boundaries");
        System.out.println("4. Off-by-one errors in median calculation");
        System.out.println("5. Not ensuring nums1 is the smaller array");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Check partition validity: maxLeft <= minRight");
        System.out.println("2. Verify total left size = (m+n+1)/2");
        System.out.println("3. Test with odd and even total lengths");
        System.out.println("4. Test with empty arrays and single elements");
        
        System.out.println("\nAll tests completed!");
    }
}
