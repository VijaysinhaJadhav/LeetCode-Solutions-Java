
## Solution.java

```java
/**
 * 33. Search in Rotated Sorted Array
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a rotated sorted array with distinct values and a target,
 * return the index of target if it exists, otherwise return -1.
 * 
 * Key Insights:
 * 1. The array has two sorted portions separated by a rotation point
 * 2. Use modified binary search to determine which half is sorted
 * 3. Check if target lies within the sorted half's range
 * 4. If yes, search that half; if no, search the other half
 * 5. All elements are unique (important for the algorithm)
 * 
 * Approach (Modified Binary Search):
 * 1. Initialize left = 0, right = n-1
 * 2. While left <= right, calculate mid
 * 3. If nums[mid] == target, return mid
 * 4. Determine which half is sorted by comparing nums[left] and nums[mid]
 * 5. If left half is sorted, check if target is in left half range
 * 6. If right half is sorted, check if target is in right half range
 * 7. Adjust search bounds based on the checks
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search
 */

class Solution {
    /**
     * Approach 1: Modified Binary Search - RECOMMENDED
     * O(log n) time, O(1) space - Optimal solution
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Found target
            if (nums[mid] == target) {
                return mid;
            }
            
            // Check if left half [left, mid] is sorted
            if (nums[left] <= nums[mid]) {
                // Left half is sorted
                if (target >= nums[left] && target < nums[mid]) {
                    // Target is in left sorted half
                    right = mid - 1;
                } else {
                    // Target is in right half (which may not be sorted)
                    left = mid + 1;
                }
            } else {
                // Right half [mid, right] is sorted
                if (target > nums[mid] && target <= nums[right]) {
                    // Target is in right sorted half
                    left = mid + 1;
                } else {
                    // Target is in left half (which may not be sorted)
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 2: Find Pivot Then Binary Search
     * Two-step approach: find rotation point, then search in appropriate half
     */
    public int searchFindPivot(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        // Step 1: Find the rotation point (minimum element)
        int pivot = findPivot(nums);
        
        // Step 2: Determine which half to search and perform binary search
        if (pivot == 0) {
            // Array is not rotated
            return binarySearch(nums, target, 0, nums.length - 1);
        } else if (target >= nums[0]) {
            // Target is in left sorted portion
            return binarySearch(nums, target, 0, pivot - 1);
        } else {
            // Target is in right sorted portion
            return binarySearch(nums, target, pivot, nums.length - 1);
        }
    }
    
    private int findPivot(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
    
    private int binarySearch(int[] nums, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    /**
     * Approach 3: Binary Search Comparing with Right Element
     * Alternative approach using right element for comparison
     */
    public int searchCompareRight(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            }
            
            // Check if right half [mid, right] is sorted
            if (nums[mid] <= nums[right]) {
                // Right half is sorted
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                // Left half [left, mid] is sorted
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 4: Linear Search (Not Recommended - for comparison only)
     * O(n) time - Too slow, but simple to understand
     */
    public int searchLinear(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Approach 5: Recursive Binary Search
     * O(log n) time, O(log n) space due to recursion
     */
    public int searchRecursive(int[] nums, int target) {
        return searchRecursive(nums, target, 0, nums.length - 1);
    }
    
    private int searchRecursive(int[] nums, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        }
        
        // Check if left half is sorted
        if (nums[left] <= nums[mid]) {
            if (target >= nums[left] && target < nums[mid]) {
                return searchRecursive(nums, target, left, mid - 1);
            } else {
                return searchRecursive(nums, target, mid + 1, right);
            }
        } else {
            // Right half is sorted
            if (target > nums[mid] && target <= nums[right]) {
                return searchRecursive(nums, target, mid + 1, right);
            } else {
                return searchRecursive(nums, target, left, mid - 1);
            }
        }
    }
    
    /**
     * Approach 6: One-Pass Binary Search with Detailed Conditions
     * More explicit condition checking
     */
    public int searchDetailed(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            }
            
            // Case 1: Left half [left, mid] is strictly sorted
            if (nums[left] < nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Case 2: Right half [mid, right] is strictly sorted
            else if (nums[mid] < nums[right]) {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            // Case 3: nums[left] == nums[mid] (only happens when left == mid)
            else {
                // In distinct elements array, this only happens when array has 2 elements
                // and left == mid, so we check the right element
                if (mid + 1 <= right && nums[mid + 1] == target) {
                    return mid + 1;
                }
                break;
            }
        }
        
        return -1;
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    private void visualizeBinarySearch(int[] nums, int target) {
        System.out.println("\nBinary Search Visualization:");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Target: " + target);
        System.out.println("Length: " + nums.length);
        
        int left = 0;
        int right = nums.length - 1;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Mid | nums[Mid] | Sorted Half | Target Range Check | Action");
        System.out.println("-----|------|-------|-----|-----------|-------------|-------------------|--------");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String sortedHalf;
            String rangeCheck;
            String action;
            
            if (nums[mid] == target) {
                sortedHalf = "N/A";
                rangeCheck = "FOUND TARGET!";
                action = "Return index " + mid;
                System.out.printf("%4d | %4d | %5d | %3d | %9d | %11s | %17s | %s%n", 
                                step, left, right, mid, nums[mid], sortedHalf, rangeCheck, action);
                return;
            }
            
            if (nums[left] <= nums[mid]) {
                // Left half is sorted
                sortedHalf = "LEFT [" + left + "-" + mid + "]";
                if (target >= nums[left] && target < nums[mid]) {
                    rangeCheck = target + " in [" + nums[left] + "," + nums[mid] + ")";
                    action = "Search LEFT (right = " + (mid-1) + ")";
                    right = mid - 1;
                } else {
                    rangeCheck = target + " NOT in [" + nums[left] + "," + nums[mid] + ")";
                    action = "Search RIGHT (left = " + (mid+1) + ")";
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                sortedHalf = "RIGHT [" + mid + "-" + right + "]";
                if (target > nums[mid] && target <= nums[right]) {
                    rangeCheck = target + " in (" + nums[mid] + "," + nums[right] + "]";
                    action = "Search RIGHT (left = " + (mid+1) + ")";
                    left = mid + 1;
                } else {
                    rangeCheck = target + " NOT in (" + nums[mid] + "," + nums[right] + "]";
                    action = "Search LEFT (right = " + (mid-1) + ")";
                    right = mid - 1;
                }
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %9d | %11s | %17s | %s%n", 
                            step, left, right, mid, nums[mid], sortedHalf, rangeCheck, action);
            step++;
        }
        
        System.out.println("\nTarget " + target + " not found in array");
    }
    
    /**
     * Helper method to analyze array rotation and target position
     */
    private void analyzeArray(int[] nums, int target) {
        System.out.println("\nArray Analysis:");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Target: " + target);
        
        // Find rotation point
        int pivot = findPivot(nums);
        System.out.println("Rotation point (pivot): index " + pivot + " (value: " + nums[pivot] + ")");
        
        if (pivot == 0) {
            System.out.println("Array is not rotated (or rotated n times)");
        } else {
            System.out.println("Left sorted portion: [0, " + (pivot-1) + "] = " + 
                java.util.Arrays.toString(java.util.Arrays.copyOfRange(nums, 0, pivot)));
            System.out.println("Right sorted portion: [" + pivot + ", " + (nums.length-1) + "] = " + 
                java.util.Arrays.toString(java.util.Arrays.copyOfRange(nums, pivot, nums.length)));
        }
        
        // Determine which portion target should be in
        if (pivot == 0) {
            // Array not rotated
            if (target >= nums[0] && target <= nums[nums.length-1]) {
                System.out.println("Target should be in the entire array (not rotated)");
            } else {
                System.out.println("Target is out of array bounds");
            }
        } else {
            if (target >= nums[0] && target <= nums[pivot-1]) {
                System.out.println("Target should be in LEFT sorted portion [0, " + (pivot-1) + "]");
            } else if (target >= nums[pivot] && target <= nums[nums.length-1]) {
                System.out.println("Target should be in RIGHT sorted portion [" + pivot + ", " + (nums.length-1) + "]");
            } else {
                System.out.println("Target is out of array bounds");
            }
        }
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] nums, int target) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Target: " + target);
        System.out.println("Length: " + nums.length);
        System.out.println("=================================");
        
        long startTime, endTime;
        int result;
        
        // Standard Modified Binary Search
        startTime = System.nanoTime();
        result = search(nums, target);
        endTime = System.nanoTime();
        System.out.printf("Standard Binary: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Find Pivot Then Search
        startTime = System.nanoTime();
        result = searchFindPivot(nums, target);
        endTime = System.nanoTime();
        System.out.printf("Pivot Then Search:%8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Compare with Right
        startTime = System.nanoTime();
        result = searchCompareRight(nums, target);
        endTime = System.nanoTime();
        System.out.printf("Compare Right:    %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Recursive
        startTime = System.nanoTime();
        result = searchRecursive(nums, target);
        endTime = System.nanoTime();
        System.out.printf("Recursive:        %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Linear Search (only for small arrays)
        if (nums.length <= 1000) {
            startTime = System.nanoTime();
            result = searchLinear(nums, target);
            endTime = System.nanoTime();
            System.out.printf("Linear Search:    %8d ns, Result: %d%n", (endTime - startTime), result);
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Search in Rotated Sorted Array:");
        System.out.println("========================================");
        
        // Test case 1: Standard rotated array, target exists
        System.out.println("\nTest 1: Standard rotated array, target exists");
        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        int target1 = 0;
        int result1 = solution.search(nums1, target1);
        System.out.println("Result: " + result1 + " - " + (result1 == 4 ? "PASSED" : "FAILED"));
        solution.visualizeBinarySearch(nums1, target1);
        solution.analyzeArray(nums1, target1);
        
        // Test case 2: Target doesn't exist
        System.out.println("\nTest 2: Target doesn't exist");
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        int target2 = 3;
        int result2 = solution.search(nums2, target2);
        System.out.println("Result: " + result2 + " - " + (result2 == -1 ? "PASSED" : "FAILED"));
        
        // Test case 3: Not rotated array
        System.out.println("\nTest 3: Not rotated array");
        int[] nums3 = {1, 2, 3, 4, 5};
        int target3 = 3;
        int result3 = solution.search(nums3, target3);
        System.out.println("Result: " + result3 + " - " + (result3 == 2 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single element, target exists
        System.out.println("\nTest 4: Single element, target exists");
        int[] nums4 = {5};
        int target4 = 5;
        int result4 = solution.search(nums4, target4);
        System.out.println("Result: " + result4 + " - " + (result4 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single element, target doesn't exist
        System.out.println("\nTest 5: Single element, target doesn't exist");
        int[] nums5 = {5};
        int target5 = 0;
        int result5 = solution.search(nums5, target5);
        System.out.println("Result: " + result5 + " - " + (result5 == -1 ? "PASSED" : "FAILED"));
        
        // Test case 6: Two elements, rotated
        System.out.println("\nTest 6: Two elements, rotated");
        int[] nums6 = {2, 1};
        int target6 = 1;
        int result6 = solution.search(nums6, target6);
        System.out.println("Result: " + result6 + " - " + (result6 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 7: Two elements, not rotated
        System.out.println("\nTest 7: Two elements, not rotated");
        int[] nums7 = {1, 2};
        int target7 = 2;
        int result7 = solution.search(nums7, target7);
        System.out.println("Result: " + result7 + " - " + (result7 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 8: Target at rotation point
        System.out.println("\nTest 8: Target at rotation point");
        int[] nums8 = {4, 5, 6, 0, 1, 2, 3};
        int target8 = 0;
        int result8 = solution.search(nums8, target8);
        System.out.println("Result: " + result8 + " - " + (result8 == 3 ? "PASSED" : "FAILED"));
        
        // Test case 9: Large rotated array
        System.out.println("\nTest 9: Large rotated array");
        int[] nums9 = new int[1000];
        for (int i = 0; i < nums9.length; i++) {
            nums9[i] = (i + 300) % 1000; // Rotate by 300
        }
        int target9 = 500;
        int result9 = solution.search(nums9, target9);
        int expected9 = (500 - 300 + 1000) % 1000; // Calculate expected index
        System.out.println("Result: " + result9 + " - " + (result9 == expected9 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 10: Performance Comparison - Small Array");
        solution.compareApproaches(nums1, target1);
        
        System.out.println("\nTest 11: Performance Comparison - Large Array");
        solution.compareApproaches(nums9, target9);
        
        // Additional visualization examples
        System.out.println("\nTest 12: Additional Visualization Examples");
        int[] nums12a = {5, 1, 2, 3, 4};
        int target12a = 1;
        System.out.println("\nExample: [5, 1, 2, 3, 4], target = 1");
        solution.visualizeBinarySearch(nums12a, target12a);
        
        int[] nums12b = {2, 3, 4, 5, 1};
        int target12b = 5;
        System.out.println("\nExample: [2, 3, 4, 5, 1], target = 5");
        solution.visualizeBinarySearch(nums12b, target12b);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("In a rotated sorted array, one half is always sorted.");
        System.out.println("We can determine which half is sorted and check if target lies in that half.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Calculate mid index");
        System.out.println("2. If nums[mid] == target, return mid");
        System.out.println("3. Check if left half [left, mid] is sorted (nums[left] <= nums[mid])");
        System.out.println("4. If left half is sorted:");
        System.out.println("   - If target is in [nums[left], nums[mid]), search left");
        System.out.println("   - Else, search right");
        System.out.println("5. If right half is sorted:");
        System.out.println("   - If target is in (nums[mid], nums[right]], search right");
        System.out.println("   - Else, search left");
        
        System.out.println("\nWhy this works:");
        System.out.println("- In rotated array, one half is always sorted");
        System.out.println("- We can efficiently determine which half is sorted");
        System.out.println("- If target is in sorted half, we can use normal binary search");
        System.out.println("- If not, it must be in the other half");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Modified Binary Search (RECOMMENDED):");
        System.out.println("   Time: O(log n) - Standard binary search");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - One-pass binary search");
        System.out.println("     - Determine sorted half at each step");
        System.out.println("     - Check if target is in sorted half");
        System.out.println("   Pros:");
        System.out.println("     - Single pass, efficient");
        System.out.println("     - Handles all cases correctly");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex than standard binary search");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Find Pivot Then Search:");
        System.out.println("   Time: O(log n) - Two binary searches");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - First find rotation point (minimum element)");
        System.out.println("     - Then determine which half to search");
        System.out.println("     - Perform binary search in appropriate half");
        System.out.println("   Pros:");
        System.out.println("     - Clear separation of concerns");
        System.out.println("     - Reuses standard binary search");
        System.out.println("   Cons:");
        System.out.println("     - Two passes instead of one");
        System.out.println("     - Slightly more code");
        System.out.println("   Best for: Learning, when clarity is important");
        
        System.out.println("\n3. Linear Search (NOT RECOMMENDED):");
        System.out.println("   Time: O(n) - Scan all elements");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Iterate through array to find target");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(n) doesn't meet O(log n) requirement");
        System.out.println("     - Inefficient for large arrays");
        System.out.println("   Best for: Very small arrays, educational purposes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. In rotated array, one half is always sorted");
        System.out.println("2. The rotation point creates exactly one 'descent' in the array");
        System.out.println("3. All elements are unique (important for algorithm correctness)");
        System.out.println("4. Binary search iterations: ⌊log₂(n)⌋");
        System.out.println("5. For n=5000: maximum 13 iterations");
        System.out.println("6. Linear search would require up to 5000 comparisons");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Recognize this as a rotated sorted array search problem");
        System.out.println("2. Explain the key insight: one half is always sorted");
        System.out.println("3. Implement modified binary search with clear condition checks");
        System.out.println("4. Handle edge cases: single element, two elements, not rotated");
        System.out.println("5. Discuss time complexity O(log n) and space complexity O(1)");
        System.out.println("6. Mention that all elements are unique (important assumption)");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
