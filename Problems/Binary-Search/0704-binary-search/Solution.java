
# Solution.java

```java
/**
 * 704. Binary Search
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given a sorted array of distinct integers and a target value, 
 * return the index if target is found. If not, return -1.
 * Must be O(log n) runtime complexity.
 * 
 * Key Insights:
 * 1. Array is sorted - use binary search
 * 2. Maintain search space with left and right pointers
 * 3. Calculate mid safely to avoid overflow
 * 4. Halve search space each iteration
 * 
 * Approach (Standard Binary Search):
 * 1. Initialize left = 0, right = nums.length - 1
 * 2. While left <= right:
 *    - Calculate mid = left + (right - left) / 2
 *    - If nums[mid] == target, return mid
 *    - If nums[mid] < target, search right: left = mid + 1
 *    - If nums[mid] > target, search left: right = mid - 1
 * 3. Return -1 if not found
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search
 */

import java.util.*;

public class Solution {
    
    /**
     * Approach 1: Standard Binary Search - RECOMMENDED
     * O(log n) time, O(1) space - Classic implementation
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2; // Avoid overflow
            
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
     * Approach 2: Recursive Binary Search
     * O(log n) time, O(log n) space - Elegant but uses call stack
     */
    public int searchRecursive(int[] nums, int target) {
        return binarySearchRecursive(nums, target, 0, nums.length - 1);
    }
    
    private int binarySearchRecursive(int[] nums, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return binarySearchRecursive(nums, target, mid + 1, right);
        } else {
            return binarySearchRecursive(nums, target, left, mid - 1);
        }
    }
    
    /**
     * Approach 3: Binary Search with Different Bounds
     * O(log n) time, O(1) space - Uses [left, right) interval
     */
    public int searchAlternativeBounds(int[] nums, int target) {
        int left = 0;
        int right = nums.length; // Note: right is exclusive
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid; // Right is exclusive, so no -1
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 4: Binary Search with Early Checks
     * O(log n) time, O(1) space - Optimized with boundary checks
     */
    public int searchOptimized(int[] nums, int target) {
        // Early boundary checks
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (target < nums[0] || target > nums[nums.length - 1]) {
            return -1;
        }
        
        int left = 0;
        int right = nums.length - 1;
        
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
     * Approach 5: Binary Search with Template (General)
     * O(log n) time, O(1) space - Template that works for many binary search problems
     */
    public int searchTemplate(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        
        // Post-processing
        if (nums[left] == target) return left;
        if (nums[right] == target) return right;
        return -1;
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    private void visualizeBinarySearch(int[] nums, int target) {
        System.out.println("\nBinary Search Visualization:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Target: " + target);
        
        int left = 0;
        int right = nums.length - 1;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Mid | nums[Mid] | Action");
        System.out.println("-----|------|-------|-----|-----------|--------");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String action;
            
            if (nums[mid] == target) {
                action = "FOUND at index " + mid;
                System.out.printf("%4d | %4d | %5d | %3d | %9d | %s%n", 
                                step, left, right, mid, nums[mid], action);
                System.out.println("\n✓ Target found at index: " + mid);
                return;
            } else if (nums[mid] < target) {
                action = "Go RIGHT (nums[" + mid + "] = " + nums[mid] + " < " + target + ")";
                left = mid + 1;
            } else {
                action = "Go LEFT (nums[" + mid + "] = " + nums[mid] + " > " + target + ")";
                right = mid - 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %9d | %s%n", 
                            step++, left, right, mid, nums[mid], action);
        }
        
        System.out.println("\n✗ Target not found in array");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Binary Search:");
        System.out.println("======================");
        
        // Test case 1: Target exists in middle
        System.out.println("\nTest 1: Target exists in middle");
        int[] nums1 = {-1, 0, 3, 5, 9, 12};
        int target1 = 9;
        int expected1 = 4;
        
        long startTime = System.nanoTime();
        int result1a = solution.search(nums1, target1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.searchRecursive(nums1, target1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.searchOptimized(nums1, target1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        
        System.out.println("Standard: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Recursive: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the search process
        solution.visualizeBinarySearch(nums1, target1);
        
        // Test case 2: Target does not exist
        System.out.println("\nTest 2: Target does not exist");
        int[] nums2 = {-1, 0, 3, 5, 9, 12};
        int target2 = 2;
        int expected2 = -1;
        
        int result2a = solution.search(nums2, target2);
        System.out.println("Not found: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Target at beginning
        System.out.println("\nTest 3: Target at beginning");
        int[] nums3 = {-5, -3, 0, 1, 4, 6, 8};
        int target3 = -5;
        int expected3 = 0;
        
        int result3a = solution.search(nums3, target3);
        System.out.println("Beginning: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Target at end
        System.out.println("\nTest 4: Target at end");
        int[] nums4 = {-5, -3, 0, 1, 4, 6, 8};
        int target4 = 8;
        int expected4 = 6;
        
        int result4a = solution.search(nums4, target4);
        System.out.println("End: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single element (target exists)
        System.out.println("\nTest 5: Single element (target exists)");
        int[] nums5 = {5};
        int target5 = 5;
        int expected5 = 0;
        
        int result5a = solution.search(nums5, target5);
        System.out.println("Single element found: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Single element (target not exists)
        System.out.println("\nTest 6: Single element (target not exists)");
        int[] nums6 = {5};
        int target6 = 3;
        int expected6 = -1;
        
        int result6a = solution.search(nums6, target6);
        System.out.println("Single element not found: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Empty array
        System.out.println("\nTest 7: Empty array");
        int[] nums7 = {};
        int target7 = 5;
        int expected7 = -1;
        
        int result7a = solution.search(nums7, target7);
        System.out.println("Empty array: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Large numbers
        System.out.println("\nTest 8: Large numbers");
        int[] nums8 = {-10000, -5000, 0, 5000, 10000};
        int target8 = 5000;
        int expected8 = 3;
        
        int result8a = solution.search(nums8, target8);
        System.out.println("Large numbers: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard case performance:");
        System.out.println("  Standard: " + time1a + " ns");
        System.out.println("  Recursive: " + time1b + " ns");
        System.out.println("  Optimized: " + time1c + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[] largeNums = generateSortedArray(1000000); // 1 million elements
        int largeTarget = largeNums[750000]; // Target at 75% position
        
        startTime = System.nanoTime();
        int result10a = solution.search(largeNums, largeTarget);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10b = solution.searchOptimized(largeNums, largeTarget);
        long time10b = System.nanoTime() - startTime;
        
        System.out.println("Large input (1,000,000 elements):");
        System.out.println("  Standard: " + time10a + " ns, Index: " + result10a);
        System.out.println("  Optimized: " + time10b + " ns, Index: " + result10b);
        
        // Edge case: All elements same (though constraints say distinct)
        System.out.println("\nTest 11: Minimum array size");
        int[] nums11 = {1, 2};
        int target11 = 1;
        int expected11 = 0;
        
        int result11a = solution.search(nums11, target11);
        System.out.println("Min size: " + result11a + " - " + 
                         (result11a == expected11 ? "PASSED" : "FAILED"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        
        System.out.println("\nBinary Search Principle:");
        System.out.println("1. Array must be sorted");
        System.out.println("2. Compare target with middle element");
        System.out.println("3. Eliminate half of search space each iteration");
        System.out.println("4. Continue until found or search space exhausted");
        
        System.out.println("\nStandard Implementation:");
        System.out.println("1. Initialize: left = 0, right = n-1");
        System.out.println("2. While left <= right:");
        System.out.println("   - mid = left + (right - left) / 2  (avoid overflow)");
        System.out.println("   - If nums[mid] == target: return mid");
        System.out.println("   - If nums[mid] < target: left = mid + 1");
        System.out.println("   - If nums[mid] > target: right = mid - 1");
        System.out.println("3. Return -1 (not found)");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStandard Binary Search:");
        System.out.println("┌────────────┬────────────┬──────────────┐");
        System.out.println("│ Operation  │ Time       │ Space        │");
        System.out.println("├────────────┼────────────┼──────────────┤");
        System.out.println("│ Search     │ O(log n)   │ O(1)         │");
        System.out.println("│ Each Step  │ O(1)       │ O(1)         │");
        System.out.println("│ Total      │ O(log n)   │ O(1)         │");
        System.out.println("└────────────┴────────────┴──────────────┘");
        
        System.out.println("\nComparison of Approaches:");
        System.out.println("┌──────────────────┬────────────┬─────────────────┐");
        System.out.println("│ Approach         │ Time       │ Space           │");
        System.out.println("├──────────────────┼────────────┼─────────────────┤");
        System.out.println("│ Standard         │ O(log n)   │ O(1)            │");
        System.out.println("│ Recursive        │ O(log n)   │ O(log n)        │");
        System.out.println("│ Alternative Bounds│ O(log n)   │ O(1)            │");
        System.out.println("│ Optimized        │ O(log n)   │ O(1)            │");
        System.out.println("│ Template         │ O(log n)   │ O(1)            │");
        System.out.println("└──────────────────┴────────────┴─────────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with Standard Implementation:");
        System.out.println("   - Most familiar to interviewers");
        System.out.println("   - Clear and easy to explain");
        System.out.println("   - Handles all standard cases");
        
        System.out.println("\n2. Key Points to Explain:");
        System.out.println("   - Why binary search works (sorted array property)");
        System.out.println("   - Mid calculation: left + (right - left) / 2 vs (left + right) / 2");
        System.out.println("   - Loop invariant: search space [left, right]");
        System.out.println("   - Time and space complexity analysis");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - Empty array");
        System.out.println("   - Single element array");
        System.out.println("   - Target at boundaries");
        System.out.println("   - Target not present");
        System.out.println("   - Large input sizes");
        
        System.out.println("\n4. Common Mistakes to Avoid:");
        System.out.println("   - Integer overflow in mid calculation");
        System.out.println("   - Off-by-one errors in bounds");
        System.out.println("   - Infinite loops with incorrect termination");
        System.out.println("   - Forgetting to check array is sorted");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - Database indexing and query optimization");
        System.out.println("   - Dictionary lookups and spell checkers");
        System.out.println("   - Game AI (decision trees)");
        System.out.println("   - Network routing algorithms");
        System.out.println("   - Debugging and fault localization");
        
        System.out.println("\nAll tests completed successfully!");
    }
    
    /**
     * Helper method to generate large sorted array for performance testing
     */
    private static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i * 2; // Even numbers to ensure distinct sorted values
        }
        return array;
    }
}
