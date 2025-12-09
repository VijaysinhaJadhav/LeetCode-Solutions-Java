
## Solution.java

```java
/**
 * 81. Search in Rotated Sorted Array II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a rotated sorted array that may contain duplicates and a target,
 * return true if target exists, otherwise return false.
 * 
 * Key Insights:
 * 1. Similar to Problem 33, but array can have duplicates
 * 2. When nums[left] == nums[mid] == nums[right], we can't determine sorted half
 * 3. In such cases, skip duplicates by incrementing left or decrementing right
 * 4. Worst-case time complexity becomes O(n) due to duplicates
 * 5. Average case remains O(log n)
 * 
 * Approach (Modified Binary Search with Duplicate Handling):
 * 1. Initialize left = 0, right = n-1
 * 2. While left <= right, calculate mid
 * 3. If nums[mid] == target, return true
 * 4. Handle the case where we can't determine sorted half (nums[left] == nums[mid] == nums[right])
 * 5. Otherwise, determine which half is sorted and search accordingly
 * 6. Skip duplicates when necessary
 * 
 * Time Complexity: O(log n) average, O(n) worst-case
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search
 */

class Solution {
    /**
     * Approach 1: Modified Binary Search with Duplicate Handling - RECOMMENDED
     * O(log n) average, O(n) worst-case time, O(1) space
     */
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Found target
            if (nums[mid] == target) {
                return true;
            }
            
            // Handle the case where we can't determine which half is sorted
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                // Skip duplicates from both ends
                left++;
                right--;
            }
            // Left half [left, mid] is sorted
            else if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Right half [mid, right] is sorted
            else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Approach 2: Binary Search with Early Duplicate Skip
     * More aggressive duplicate skipping
     */
    public boolean searchEarlySkip(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return true;
            }
            
            // If left, mid, and right are all equal, we need to skip duplicates
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                // Linear search in this case (worst-case O(n))
                for (int i = left; i <= right; i++) {
                    if (nums[i] == target) {
                        return true;
                    }
                }
                return false;
            }
            
            // Left half is sorted
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Right half is sorted
            else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Approach 3: Find Pivot Then Binary Search
     * Two-step approach with duplicate handling
     */
    public boolean searchFindPivot(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        // Step 1: Find the rotation point (minimum element) with duplicate handling
        int pivot = findPivotWithDuplicates(nums);
        
        // Step 2: Perform binary search in appropriate portion
        if (pivot == 0) {
            return binarySearchWithDuplicates(nums, target, 0, nums.length - 1);
        } else if (target >= nums[0]) {
            return binarySearchWithDuplicates(nums, target, 0, pivot - 1);
        } else {
            return binarySearchWithDuplicates(nums, target, pivot, nums.length - 1);
        }
    }
    
    private int findPivotWithDuplicates(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Handle duplicates
            if (nums[mid] == nums[right]) {
                // Can't determine, need to check linearly
                if (nums[right] < nums[right - 1]) {
                    return right;
                }
                right--;
            } else if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
    
    private boolean binarySearchWithDuplicates(int[] nums, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
    
    /**
     * Approach 4: Linear Search (Fallback for worst-case)
     * O(n) time, O(1) space - Simple but inefficient for large arrays
     */
    public boolean searchLinear(int[] nums, int target) {
        for (int num : nums) {
            if (num == target) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Approach 5: Recursive Binary Search with Duplicate Handling
     * O(log n) average, O(n) worst-case time, O(log n) space due to recursion
     */
    public boolean searchRecursive(int[] nums, int target) {
        return searchRecursive(nums, target, 0, nums.length - 1);
    }
    
    private boolean searchRecursive(int[] nums, int target, int left, int right) {
        if (left > right) {
            return false;
        }
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return true;
        }
        
        // Handle duplicates
        if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
            return searchRecursive(nums, target, left + 1, right - 1);
        }
        
        // Left half is sorted
        if (nums[left] <= nums[mid]) {
            if (target >= nums[left] && target < nums[mid]) {
                return searchRecursive(nums, target, left, mid - 1);
            } else {
                return searchRecursive(nums, target, mid + 1, right);
            }
        }
        // Right half is sorted
        else {
            if (target > nums[mid] && target <= nums[right]) {
                return searchRecursive(nums, target, mid + 1, right);
            } else {
                return searchRecursive(nums, target, left, mid - 1);
            }
        }
    }
    
    /**
     * Approach 6: Iterative with Detailed Duplicate Handling
     * More explicit handling of edge cases
     */
    public boolean searchDetailed(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return true;
            }
            
            // Case 1: Can't determine sorted half due to duplicates
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                // Skip one duplicate from left and right
                left++;
                right--;
            }
            // Case 2: Left half [left, mid] is sorted
            else if (nums[left] <= nums[mid]) {
                // Check if target is in left sorted half
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Case 3: Right half [mid, right] is sorted
            else {
                // Check if target is in right sorted half
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    private void visualizeBinarySearch(int[] nums, int target) {
        System.out.println("\nBinary Search Visualization (with duplicates):");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Target: " + target);
        System.out.println("Length: " + nums.length);
        
        int left = 0;
        int right = nums.length - 1;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Mid | nums[Mid] | Duplicate Case | Action");
        System.out.println("-----|------|-------|-----|-----------|----------------|--------");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String duplicateCase;
            String action;
            
            if (nums[mid] == target) {
                duplicateCase = "FOUND TARGET!";
                action = "Return true";
                System.out.printf("%4d | %4d | %5d | %3d | %9d | %14s | %s%n", 
                                step, left, right, mid, nums[mid], duplicateCase, action);
                return;
            }
            
            // Check for duplicate case
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                duplicateCase = "ALL EQUAL: nums[" + left + "]=" + nums[left] + 
                              ", nums[" + mid + "]=" + nums[mid] + 
                              ", nums[" + right + "]=" + nums[right];
                action = "Skip duplicates (left++, right--)";
                left++;
                right--;
            } else if (nums[left] <= nums[mid]) {
                // Left half is sorted
                duplicateCase = "LEFT SORTED: nums[" + left + "]=" + nums[left] + 
                              " <= nums[" + mid + "]=" + nums[mid];
                if (target >= nums[left] && target < nums[mid]) {
                    action = "Target in left sorted half (right = " + (mid-1) + ")";
                    right = mid - 1;
                } else {
                    action = "Target in right half (left = " + (mid+1) + ")";
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                duplicateCase = "RIGHT SORTED: nums[" + mid + "]=" + nums[mid] + 
                              " <= nums[" + right + "]=" + nums[right];
                if (target > nums[mid] && target <= nums[right]) {
                    action = "Target in right sorted half (left = " + (mid+1) + ")";
                    left = mid + 1;
                } else {
                    action = "Target in left half (right = " + (mid-1) + ")";
                    right = mid - 1;
                }
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %9d | %14s | %s%n", 
                            step, left, right, mid, nums[mid], duplicateCase, action);
            step++;
            
            // Prevent infinite loop in visualization
            if (step > 20) {
                System.out.println("Stopping visualization to prevent infinite loop");
                break;
            }
        }
        
        System.out.println("\nTarget " + target + " not found in array");
    }
    
    /**
     * Helper method to analyze array duplicates and rotation
     */
    private void analyzeArray(int[] nums, int target) {
        System.out.println("\nArray Analysis (with duplicates):");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Target: " + target);
        
        // Count duplicates
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }
        
        System.out.println("Unique values: " + frequency.size());
        System.out.println("Duplicate analysis:");
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println("  Value " + entry.getKey() + " appears " + entry.getValue() + " times");
            }
        }
        
        // Find potential rotation points
        List<Integer> rotationPoints = new ArrayList<>();
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i-1]) {
                rotationPoints.add(i);
            }
        }
        
        if (rotationPoints.isEmpty()) {
            System.out.println("Array appears to be not rotated (or rotated n times)");
        } else {
            System.out.println("Potential rotation points (indices where array decreases): " + rotationPoints);
        }
        
        // Check if target exists (for verification)
        boolean targetExists = false;
        for (int num : nums) {
            if (num == target) {
                targetExists = true;
                break;
            }
        }
        System.out.println("Target exists in array: " + targetExists);
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] nums, int target) {
        System.out.println("\nPerformance Comparison (with duplicates):");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Target: " + target);
        System.out.println("Length: " + nums.length);
        
        // Calculate duplicate ratio
        Set<Integer> unique = new HashSet<>();
        for (int num : nums) {
            unique.add(num);
        }
        double duplicateRatio = 1.0 - (double) unique.size() / nums.length;
        System.out.printf("Duplicate ratio: %.2f%%%n", duplicateRatio * 100);
        System.out.println("=================================");
        
        long startTime, endTime;
        boolean result;
        
        // Standard Modified Binary Search
        startTime = System.nanoTime();
        result = search(nums, target);
        endTime = System.nanoTime();
        System.out.printf("Standard Binary: %8d ns, Result: %b%n", (endTime - startTime), result);
        
        // Early Skip
        startTime = System.nanoTime();
        result = searchEarlySkip(nums, target);
        endTime = System.nanoTime();
        System.out.printf("Early Skip:      %8d ns, Result: %b%n", (endTime - startTime), result);
        
        // Find Pivot Then Search
        startTime = System.nanoTime();
        result = searchFindPivot(nums, target);
        endTime = System.nanoTime();
        System.out.printf("Pivot Then Search:%8d ns, Result: %b%n", (endTime - startTime), result);
        
        // Recursive
        startTime = System.nanoTime();
        result = searchRecursive(nums, target);
        endTime = System.nanoTime();
        System.out.printf("Recursive:       %8d ns, Result: %b%n", (endTime - startTime), result);
        
        // Linear Search
        startTime = System.nanoTime();
        result = searchLinear(nums, target);
        endTime = System.nanoTime();
        System.out.printf("Linear Search:   %8d ns, Result: %b%n", (endTime - startTime), result);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Search in Rotated Sorted Array II (with duplicates):");
        System.out.println("============================================================");
        
        // Test case 1: Standard rotated array with duplicates, target exists
        System.out.println("\nTest 1: Standard rotated array with duplicates, target exists");
        int[] nums1 = {2, 5, 6, 0, 0, 1, 2};
        int target1 = 0;
        boolean result1 = solution.search(nums1, target1);
        System.out.println("Result: " + result1 + " - " + (result1 ? "PASSED" : "FAILED"));
        solution.visualizeBinarySearch(nums1, target1);
        solution.analyzeArray(nums1, target1);
        
        // Test case 2: Target doesn't exist
        System.out.println("\nTest 2: Target doesn't exist");
        int[] nums2 = {2, 5, 6, 0, 0, 1, 2};
        int target2 = 3;
        boolean result2 = solution.search(nums2, target2);
        System.out.println("Result: " + result2 + " - " + (!result2 ? "PASSED" : "FAILED"));
        
        // Test case 3: All elements same, target exists
        System.out.println("\nTest 3: All elements same, target exists");
        int[] nums3 = {1, 1, 1, 1, 1, 1, 1};
        int target3 = 1;
        boolean result3 = solution.search(nums3, target3);
        System.out.println("Result: " + result3 + " - " + (result3 ? "PASSED" : "FAILED"));
        
        // Test case 4: All elements same, target doesn't exist
        System.out.println("\nTest 4: All elements same, target doesn't exist");
        int[] nums4 = {1, 1, 1, 1, 1, 1, 1};
        int target4 = 2;
        boolean result4 = solution.search(nums4, target4);
        System.out.println("Result: " + result4 + " - " + (!result4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Not rotated array with duplicates
        System.out.println("\nTest 5: Not rotated array with duplicates");
        int[] nums5 = {1, 1, 2, 2, 3, 3, 4, 4};
        int target5 = 3;
        boolean result5 = solution.search(nums5, target5);
        System.out.println("Result: " + result5 + " - " + (result5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Single element, target exists
        System.out.println("\nTest 6: Single element, target exists");
        int[] nums6 = {5};
        int target6 = 5;
        boolean result6 = solution.search(nums6, target6);
        System.out.println("Result: " + result6 + " - " + (result6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Worst-case scenario - many duplicates
        System.out.println("\nTest 7: Worst-case scenario - many duplicates");
        int[] nums7 = {1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1};
        int target7 = 2;
        boolean result7 = solution.search(nums7, target7);
        System.out.println("Result: " + result7 + " - " + (result7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Complex duplicate pattern
        System.out.println("\nTest 8: Complex duplicate pattern");
        int[] nums8 = {1, 1, 3, 1, 1, 1, 1};
        int target8 = 3;
        boolean result8 = solution.search(nums8, target8);
        System.out.println("Result: " + result8 + " - " + (result8 ? "PASSED" : "FAILED"));
        
        // Test case 9: No duplicates (should work like Problem 33)
        System.out.println("\nTest 9: No duplicates (should work like Problem 33)");
        int[] nums9 = {4, 5, 6, 7, 0, 1, 2};
        int target9 = 0;
        boolean result9 = solution.search(nums9, target9);
        System.out.println("Result: " + result9 + " - " + (result9 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 10: Performance Comparison - Moderate Duplicates");
        solution.compareApproaches(nums1, target1);
        
        System.out.println("\nTest 11: Performance Comparison - Heavy Duplicates");
        solution.compareApproaches(nums7, target7);
        
        // Additional visualization examples
        System.out.println("\nTest 12: Additional Visualization Examples");
        int[] nums12a = {1, 0, 1, 1, 1};
        int target12a = 0;
        System.out.println("\nExample: [1, 0, 1, 1, 1], target = 0");
        solution.visualizeBinarySearch(nums12a, target12a);
        
        int[] nums12b = {1, 1, 1, 1, 3, 1};
        int target12b = 3;
        System.out.println("\nExample: [1, 1, 1, 1, 3, 1], target = 3");
        solution.visualizeBinarySearch(nums12b, target12b);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION (with duplicates):");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Challenge:");
        System.out.println("Duplicates make it impossible to always determine which half is sorted.");
        System.out.println("When nums[left] == nums[mid] == nums[right], we can't tell which half contains the target.");
        
        System.out.println("\nSolution Strategy:");
        System.out.println("1. If we find the target at mid, return true");
        System.out.println("2. If nums[left] == nums[mid] == nums[right]:");
        System.out.println("   - Skip duplicates by incrementing left and decrementing right");
        System.out.println("   - This handles the worst-case scenario");
        System.out.println("3. Otherwise, proceed like Problem 33:");
        System.out.println("   - Determine which half is sorted");
        System.out.println("   - Check if target is in sorted half");
        System.out.println("   - Search accordingly");
        
        System.out.println("\nWhy worst-case is O(n):");
        System.out.println("In arrays with many duplicates (e.g., all elements same),");
        System.out.println("we might need to skip elements one by one, leading to O(n) time.");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Modified Binary Search with Duplicate Handling (RECOMMENDED):");
        System.out.println("   Time: O(log n) average, O(n) worst-case");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - One-pass binary search with duplicate checks");
        System.out.println("     - Skip duplicates when nums[left] == nums[mid] == nums[right]");
        System.out.println("     - Otherwise, use standard rotated array search");
        System.out.println("   Pros:");
        System.out.println("     - Efficient for most practical cases");
        System.out.println("     - Handles duplicates gracefully");
        System.out.println("     - Simple and elegant");
        System.out.println("   Cons:");
        System.out.println("     - Worst-case O(n) for heavy duplicates");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Early Skip with Linear Search:");
        System.out.println("   Time: O(log n) average, O(n) worst-case");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - When duplicates prevent binary search, fall back to linear search");
        System.out.println("     - More aggressive in switching to linear search");
        System.out.println("   Pros:");
        System.out.println("     - Clear separation between binary and linear search");
        System.out.println("     - Can be faster in some worst-case scenarios");
        System.out.println("   Cons:");
        System.out.println("     - May switch to linear search too early");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: Arrays with known heavy duplicate patterns");
        
        System.out.println("\n3. Linear Search (Fallback):");
        System.out.println("   Time: O(n) - Always linear");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Simple iteration through the array");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple to implement");
        System.out.println("     - Always correct");
        System.out.println("   Cons:");
        System.out.println("     - O(n) doesn't meet O(log n) requirement");
        System.out.println("     - Inefficient for large arrays");
        System.out.println("   Best for: Very small arrays, worst-case fallback");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. With duplicates, binary search can't always halve the search space");
        System.out.println("2. Worst-case occurs when most elements are duplicates");
        System.out.println("3. Average case remains O(log n) for typical inputs");
        System.out.println("4. The duplicate skipping strategy minimizes worst-case impact");
        System.out.println("5. For n=5000: average ~13 iterations, worst-case 5000 iterations");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start by mentioning this is the duplicate version of Problem 33");
        System.out.println("2. Explain the key challenge: can't always determine sorted half");
        System.out.println("3. Implement the duplicate skipping strategy");
        System.out.println("4. Discuss time complexity: O(log n) average, O(n) worst-case");
        System.out.println("5. Handle edge cases: all duplicates, single element, no rotation");
        System.out.println("6. Mention that this is the expected solution for interviews");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
