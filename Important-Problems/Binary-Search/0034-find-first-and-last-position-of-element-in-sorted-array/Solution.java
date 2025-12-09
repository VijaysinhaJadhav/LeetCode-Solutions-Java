
## Solution.java

```java
/**
 * 34. Find First and Last Position of Element in Sorted Array
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of integers nums sorted in non-decreasing order, find the starting 
 * and ending position of a given target value. Must be O(log n) runtime.
 * 
 * Key Insights:
 * 1. Use modified binary search to find left boundary (first occurrence)
 * 2. Use modified binary search to find right boundary (last occurrence)
 * 3. When nums[mid] == target, continue searching in the appropriate direction
 * 4. Handle edge cases: empty array, target not found, single element
 * 
 * Approach (Two Binary Searches):
 * 1. findLeftBoundary: binary search that continues left when target found
 * 2. findRightBoundary: binary search that continues right when target found
 * 3. Return [left, right] or [-1, -1] if not found
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Two Binary Searches (Standard) - RECOMMENDED
     * O(log n) time, O(1) space
     */
    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // Find left boundary (first occurrence)
        int left = findLeftBoundary(nums, target);
        if (left == -1) {
            return result; // Target not found
        }
        
        // Find right boundary (last occurrence)
        int right = findRightBoundary(nums, target);
        
        result[0] = left;
        result[1] = right;
        return result;
    }
    
    private int findLeftBoundary(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int index = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                index = mid; // Potential left boundary
                right = mid - 1; // Continue searching left
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return index;
    }
    
    private int findRightBoundary(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int index = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                index = mid; // Potential right boundary
                left = mid + 1; // Continue searching right
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return index;
    }
    
    /**
     * Approach 2: Single Binary Search with Helper Method
     * O(log n) time, O(1) space
     * More concise but slightly less readable
     */
    public int[] searchRangeSinglePass(int[] nums, int target) {
        int[] result = {-1, -1};
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        int first = findBoundary(nums, target, true);
        if (first == -1) {
            return result;
        }
        
        int last = findBoundary(nums, target, false);
        return new int[]{first, last};
    }
    
    private int findBoundary(int[] nums, int target, boolean isFirst) {
        int left = 0;
        int right = nums.length - 1;
        int index = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                index = mid;
                if (isFirst) {
                    right = mid - 1; // Search left for first occurrence
                } else {
                    left = mid + 1;  // Search right for last occurrence
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return index;
    }
    
    /**
     * Approach 3: Binary Search with Built-in Methods
     * O(log n) time, O(1) space
     * Uses Arrays.binarySearch and manual boundary expansion
     */
    public int[] searchRangeBuiltIn(int[] nums, int target) {
        int[] result = {-1, -1};
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // Find any occurrence of target
        int index = Arrays.binarySearch(nums, target);
        if (index < 0) {
            return result; // Target not found
        }
        
        // Expand left to find first occurrence
        int left = index;
        while (left > 0 && nums[left - 1] == target) {
            left--;
        }
        
        // Expand right to find last occurrence
        int right = index;
        while (right < nums.length - 1 && nums[right + 1] == target) {
            right++;
        }
        
        result[0] = left;
        result[1] = right;
        return result;
    }
    
    /**
     * Approach 4: Binary Search with Custom Comparator
     * O(log n) time, O(1) space
     * Uses binary search to find insertion points
     */
    public int[] searchRangeInsertionPoints(int[] nums, int target) {
        int[] result = {-1, -1};
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // Find first position where target appears
        int first = findInsertionPoint(nums, target);
        
        // Find first position where target+1 appears (exclusive bound)
        int last = findInsertionPoint(nums, target + 1) - 1;
        
        if (first < nums.length && nums[first] == target) {
            result[0] = first;
            result[1] = last;
        }
        
        return result;
    }
    
    private int findInsertionPoint(int[] nums, int target) {
        int left = 0;
        int right = nums.length;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Approach 5: Linear Scan (Violates O(log n) but simple)
     * O(n) time, O(1) space
     * Simple but inefficient for large arrays
     */
    public int[] searchRangeLinear(int[] nums, int target) {
        int[] result = {-1, -1};
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                if (result[0] == -1) {
                    result[0] = i; // First occurrence
                }
                result[1] = i; // Last occurrence (so far)
            }
        }
        
        return result;
    }
    
    /**
     * Approach 6: Optimized Linear Scan
     * O(n) time but more efficient in best case
     * Scans from both ends
     */
    public int[] searchRangeOptimizedLinear(int[] nums, int target) {
        int[] result = {-1, -1};
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // Scan from left for first occurrence
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                result[0] = i;
                break;
            }
        }
        
        // If first occurrence not found, return
        if (result[0] == -1) {
            return result;
        }
        
        // Scan from right for last occurrence
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == target) {
                result[1] = i;
                break;
            }
        }
        
        return result;
    }
    
    /**
     * Helper method to visualize binary search process
     */
    private void visualizeBinarySearch(int[] nums, int target, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Target: " + target);
        System.out.println();
        
        if ("Two Binary Searches".equals(approach)) {
            visualizeTwoBinarySearches(nums, target);
        } else if ("Single Pass".equals(approach)) {
            visualizeSinglePass(nums, target);
        }
    }
    
    private void visualizeTwoBinarySearches(int[] nums, int target) {
        System.out.println("Left Boundary Search:");
        System.out.println("Step | Left | Right | Mid | nums[Mid] | Action");
        System.out.println("-----|------|-------|-----|-----------|--------");
        
        int left = 0;
        int right = nums.length - 1;
        int leftBoundary = -1;
        int step = 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String action;
            
            if (nums[mid] == target) {
                leftBoundary = mid;
                action = "Found target, search LEFT (right = mid - 1)";
                right = mid - 1;
            } else if (nums[mid] < target) {
                action = "Too small, search RIGHT (left = mid + 1)";
                left = mid + 1;
            } else {
                action = "Too large, search LEFT (right = mid - 1)";
                right = mid - 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %9d | %s%n", 
                step++, left, right, mid, nums[mid], action);
        }
        
        System.out.println("Left Boundary: " + leftBoundary);
        
        if (leftBoundary == -1) {
            System.out.println("Target not found, returning [-1, -1]");
            return;
        }
        
        System.out.println("\nRight Boundary Search:");
        System.out.println("Step | Left | Right | Mid | nums[Mid] | Action");
        System.out.println("-----|------|-------|-----|-----------|--------");
        
        left = 0;
        right = nums.length - 1;
        int rightBoundary = -1;
        step = 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String action;
            
            if (nums[mid] == target) {
                rightBoundary = mid;
                action = "Found target, search RIGHT (left = mid + 1)";
                left = mid + 1;
            } else if (nums[mid] < target) {
                action = "Too small, search RIGHT (left = mid + 1)";
                left = mid + 1;
            } else {
                action = "Too large, search LEFT (right = mid - 1)";
                right = mid - 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %9d | %s%n", 
                step++, left, right, mid, nums[mid], action);
        }
        
        System.out.println("Right Boundary: " + rightBoundary);
        System.out.println("Final Result: [" + leftBoundary + ", " + rightBoundary + "]");
    }
    
    private void visualizeSinglePass(int[] nums, int target) {
        System.out.println("Finding First Occurrence:");
        visualizeBoundarySearch(nums, target, true);
        
        System.out.println("\nFinding Last Occurrence:");
        visualizeBoundarySearch(nums, target, false);
    }
    
    private void visualizeBoundarySearch(int[] nums, int target, boolean isFirst) {
        String boundaryType = isFirst ? "First" : "Last";
        System.out.println("Step | Left | Right | Mid | nums[Mid] | Action");
        System.out.println("-----|------|-------|-----|-----------|--------");
        
        int left = 0;
        int right = nums.length - 1;
        int boundary = -1;
        int step = 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String action;
            
            if (nums[mid] == target) {
                boundary = mid;
                if (isFirst) {
                    action = "Found target, search LEFT for " + boundaryType;
                    right = mid - 1;
                } else {
                    action = "Found target, search RIGHT for " + boundaryType;
                    left = mid + 1;
                }
            } else if (nums[mid] < target) {
                action = "Too small, search RIGHT";
                left = mid + 1;
            } else {
                action = "Too large, search LEFT";
                right = mid - 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %9d | %s%n", 
                step++, left, right, mid, nums[mid], action);
        }
        
        System.out.println(boundaryType + " Boundary: " + boundary);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Find First and Last Position:");
        System.out.println("====================================\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: Standard example [5,7,7,8,8,10], target=8");
        int[] nums1 = {5,7,7,8,8,10};
        int target1 = 8;
        int[] expected1 = {3,4};
        testImplementation(solution, nums1, target1, expected1, "Two Binary Searches");
        
        // Test case 2: Target not found
        System.out.println("\nTest 2: Target not found [5,7,7,8,8,10], target=6");
        int[] nums2 = {5,7,7,8,8,10};
        int target2 = 6;
        int[] expected2 = {-1,-1};
        testImplementation(solution, nums2, target2, expected2, "Two Binary Searches");
        
        // Test case 3: Empty array
        System.out.println("\nTest 3: Empty array [], target=0");
        int[] nums3 = {};
        int target3 = 0;
        int[] expected3 = {-1,-1};
        testImplementation(solution, nums3, target3, expected3, "Two Binary Searches");
        
        // Test case 4: Single element found
        System.out.println("\nTest 4: Single element found [5], target=5");
        int[] nums4 = {5};
        int target4 = 5;
        int[] expected4 = {0,0};
        testImplementation(solution, nums4, target4, expected4, "Two Binary Searches");
        
        // Test case 5: Single element not found
        System.out.println("\nTest 5: Single element not found [5], target=3");
        int[] nums5 = {5};
        int target5 = 3;
        int[] expected5 = {-1,-1};
        testImplementation(solution, nums5, target5, expected5, "Two Binary Searches");
        
        // Test case 6: All elements same
        System.out.println("\nTest 6: All elements same [1,1,1,1], target=1");
        int[] nums6 = {1,1,1,1};
        int target6 = 1;
        int[] expected6 = {0,3};
        testImplementation(solution, nums6, target6, expected6, "Two Binary Searches");
        
        // Test case 7: Target at beginning
        System.out.println("\nTest 7: Target at beginning [1,2,3,4,5], target=1");
        int[] nums7 = {1,2,3,4,5};
        int target7 = 1;
        int[] expected7 = {0,0};
        testImplementation(solution, nums7, target7, expected7, "Two Binary Searches");
        
        // Test case 8: Target at end
        System.out.println("\nTest 8: Target at end [1,2,3,4,5], target=5");
        int[] nums8 = {1,2,3,4,5};
        int target8 = 5;
        int[] expected8 = {4,4};
        testImplementation(solution, nums8, target8, expected8, "Two Binary Searches");
        
        // Test case 9: Large array
        System.out.println("\nTest 9: Large array performance");
        int[] nums9 = generateLargeArray(1000, 42);
        int target9 = 500;
        long startTime = System.nanoTime();
        int[] result9 = solution.searchRange(nums9, target9);
        long time9 = System.nanoTime() - startTime;
        System.out.println("Large array (1000 elements): " + Arrays.toString(result9) + 
                         ", Time: " + time9 + " ns");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: TWO BINARY SEARCHES");
        System.out.println("=".repeat(70));
        
        explainTwoBinarySearches(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, int[] nums, int target, 
                                         int[] expected, String approach) {
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
        
        long startTime = System.nanoTime();
        int[] result = null;
        switch (approach) {
            case "Two Binary Searches":
                result = solution.searchRange(numsCopy, target);
                break;
            case "Single Pass":
                result = solution.searchRangeSinglePass(numsCopy, target);
                break;
            case "Built-in":
                result = solution.searchRangeBuiltIn(numsCopy, target);
                break;
            case "Linear":
                result = solution.searchRangeLinear(numsCopy, target);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        boolean passed = Arrays.equals(result, expected);
        System.out.printf("%s: Expected=%s, Got=%s, Time=%,d ns - %s%n",
                approach, Arrays.toString(expected), Arrays.toString(result), 
                time, (passed ? "PASSED" : "FAILED"));
        
        if (!passed) {
            System.out.println("  Input: nums=" + Arrays.toString(nums) + ", target=" + target);
        }
        
        // Visualization for interesting cases
        if (passed && nums.length <= 10) {
            solution.visualizeBinarySearch(nums, target, approach);
        }
    }
    
    private static void comparePerformance(Solution solution) {
        int size = 10000;
        int[] testArray = generateLargeArray(size, 42);
        int target = size / 2;
        
        System.out.println("\nPerformance test with " + size + " elements:");
        
        // Test Two Binary Searches
        int[] arr1 = Arrays.copyOf(testArray, testArray.length);
        long startTime = System.nanoTime();
        solution.searchRange(arr1, target);
        long time1 = System.nanoTime() - startTime;
        
        // Test Single Pass
        int[] arr2 = Arrays.copyOf(testArray, testArray.length);
        startTime = System.nanoTime();
        solution.searchRangeSinglePass(arr2, target);
        long time2 = System.nanoTime() - startTime;
        
        // Test Built-in
        int[] arr3 = Arrays.copyOf(testArray, testArray.length);
        startTime = System.nanoTime();
        solution.searchRangeBuiltIn(arr3, target);
        long time3 = System.nanoTime() - startTime;
        
        // Test Linear (skip for large arrays)
        long time4 = 0;
        if (size <= 1000) {
            int[] arr4 = Arrays.copyOf(testArray, testArray.length);
            startTime = System.nanoTime();
            solution.searchRangeLinear(arr4, target);
            time4 = System.nanoTime() - startTime;
        }
        
        System.out.printf("Two Binary Searches: %,12d ns%n", time1);
        System.out.printf("Single Pass:         %,12d ns%n", time2);
        System.out.printf("Built-in:            %,12d ns%n", time3);
        if (size <= 1000) {
            System.out.printf("Linear:              %,12d ns%n", time4);
        } else {
            System.out.println("Linear:              (skipped - too slow)");
        }
    }
    
    private static int[] generateLargeArray(int size, int seed) {
        int[] arr = new int[size];
        Random random = new Random(seed);
        
        // Generate sorted array with some duplicates
        int current = 0;
        for (int i = 0; i < size; i++) {
            arr[i] = current;
            // Occasionally add duplicates
            if (random.nextDouble() < 0.3) {
                current += random.nextInt(3);
            } else {
                current += 1;
            }
        }
        
        return arr;
    }
    
    private static void explainTwoBinarySearches(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("We need to find both the first and last occurrence of target.");
        System.out.println("Regular binary search finds any occurrence, but we need boundaries.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Find Left Boundary:");
        System.out.println("   - When nums[mid] == target, don't return immediately");
        System.out.println("   - Instead, continue searching LEFT (right = mid - 1)");
        System.out.println("   - This finds the first occurrence");
        
        System.out.println("2. Find Right Boundary:");
        System.out.println("   - When nums[mid] == target, don't return immediately");
        System.out.println("   - Instead, continue searching RIGHT (left = mid + 1)");
        System.out.println("   - This finds the last occurrence");
        
        System.out.println("3. Return [leftBoundary, rightBoundary] or [-1, -1] if not found");
        
        System.out.println("\nWhy it works:");
        System.out.println("- By continuing the search when we find the target,");
        System.out.println("  we effectively 'push' the boundary in the desired direction");
        System.out.println("- Left search pushes the boundary leftward until we can't find target anymore");
        System.out.println("- Right search pushes the boundary rightward until we can't find target anymore");
        
        System.out.println("\nExample Walkthrough: [5,7,7,8,8,10], target=8");
        int[] example = {5,7,7,8,8,10};
        solution.visualizeBinarySearch(example, 8, "Two Binary Searches");
        
        System.out.println("\nTime Complexity: O(log n) - Two binary searches");
        System.out.println("Space Complexity: O(1) - Constant extra space");
    }
    
    private static void checkAllImplementations(Solution solution) {
        int[][][] testCases = {
            {{5,7,7,8,8,10}, {8}}, // Standard
            {{5,7,7,8,8,10}, {6}}, // Not found
            {{}, {0}},              // Empty
            {{5}, {5}},             // Single found
            {{5}, {3}},             // Single not found
            {{1,1,1,1}, {1}},      // All same
            {{1,2,3,4,5}, {1}},    // Beginning
            {{1,2,3,4,5}, {5}}     // End
        };
        
        int[][] expected = {
            {3,4}, {-1,-1}, {-1,-1}, {0,0}, {-1,-1}, {0,3}, {0,0}, {4,4}
        };
        
        String[] methods = {
            "Two Binary Searches",
            "Single Pass", 
            "Built-in",
            "Insertion Points",
            "Linear",
            "Optimized Linear"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i][0];
            int target = testCases[i][1][0];
            
            System.out.printf("\nTest case %d: nums=%s, target=%d (expected: %s)%n",
                    i + 1, Arrays.toString(nums), target, Arrays.toString(expected[i]));
            
            int[][] results = new int[methods.length][2];
            results[0] = solution.searchRange(nums.clone(), target);
            results[1] = solution.searchRangeSinglePass(nums.clone(), target);
            results[2] = solution.searchRangeBuiltIn(nums.clone(), target);
            results[3] = solution.searchRangeInsertionPoints(nums.clone(), target);
            results[4] = solution.searchRangeLinear(nums.clone(), target);
            results[5] = solution.searchRangeOptimizedLinear(nums.clone(), target);
            
            boolean caseConsistent = true;
            for (int j = 0; j < methods.length; j++) {
                boolean correct = Arrays.equals(results[j], expected[i]);
                System.out.printf("  %-20s: %s %s%n", methods[j], Arrays.toString(results[j]),
                        correct ? "✓" : "✗ (expected " + Arrays.toString(expected[i]) + ")");
                if (!correct) {
                    caseConsistent = false;
                    allConsistent = false;
                }
            }
            
            if (!caseConsistent) {
                System.out.println("  INCONSISTENT RESULTS!");
            }
        }
        
        System.out.println("\nOverall consistency: " + (allConsistent ? "ALL PASSED ✓" : "SOME FAILED ✗"));
        
        // Algorithm comparison table
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON SUMMARY");
        System.out.println("=".repeat(70));
        
        printAlgorithmComparison();
    }
    
    private static void printAlgorithmComparison() {
        System.out.println("\n1. TWO BINARY SEARCHES (RECOMMENDED):");
        System.out.println("   Time: O(log n) - Two binary searches");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Clear and intuitive");
        System.out.println("     - Easy to understand and implement");
        System.out.println("     - Handles all edge cases well");
        System.out.println("   Cons:");
        System.out.println("     - Two passes instead of one");
        System.out.println("   Use when: Interview setting, clarity is important");
        
        System.out.println("\n2. SINGLE PASS WITH HELPER:");
        System.out.println("   Time: O(log n) - Two binary searches in one method");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - More concise code");
        System.out.println("     - Reuses logic for both searches");
        System.out.println("   Cons:");
        System.out.println("     - Slightly less readable");
        System.out.println("   Use when: Code golf or minimizing code duplication");
        
        System.out.println("\n3. BUILT-IN BINARY SEARCH:");
        System.out.println("   Time: O(log n) for search + O(k) for expansion");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Leverages standard library");
        System.out.println("     - Simple expansion logic");
        System.out.println("   Cons:");
        System.out.println("     - Worst case O(n) if many duplicates");
        System.out.println("   Use when: Quick implementation, few duplicates expected");
        
        System.out.println("\n4. INSERTION POINTS:");
        System.out.println("   Time: O(log n) - Two binary searches");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Elegant mathematical approach");
        System.out.println("     - Useful for related problems");
        System.out.println("   Cons:");
        System.out.println("     - Less intuitive");
        System.out.println("   Use when: Learning binary search variations");
        
        System.out.println("\n5. LINEAR SCAN:");
        System.out.println("   Time: O(n) - Linear time");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Very simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large arrays");
        System.out.println("     - Violates O(log n) requirement");
        System.out.println("   Use when: Understanding the problem or small arrays");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start by clarifying requirements (sorted array, O(log n))");
        System.out.println("2. Mention the two binary searches approach first");
        System.out.println("3. Explain why regular binary search isn't sufficient");
        System.out.println("4. Walk through an example to demonstrate the algorithm");
        System.out.println("5. Discuss edge cases (empty array, not found, duplicates)");
        System.out.println("6. Implement the solution with clear variable names");
        System.out.println("7. Discuss alternative approaches and their trade-offs");
        System.out.println("=".repeat(70));
    }
}
