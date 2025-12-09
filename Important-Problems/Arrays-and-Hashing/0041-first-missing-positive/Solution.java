
## Solution.java

```java
/**
 * 41. First Missing Positive
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given an unsorted integer array nums, return the smallest missing positive integer.
 * Must implement an algorithm that runs in O(n) time and uses O(1) auxiliary space.
 * 
 * Key Insights:
 * 1. The answer must be in the range [1, n+1] where n is the array length
 * 2. We can use the array itself as a hash table by placing numbers in their correct positions
 * 3. After reorganization, the first index where nums[i] != i+1 gives the missing positive
 * 4. Ignore numbers that are out of range (<= 0 or > n)
 * 
 * Approach (Index Mapping):
 * 1. Iterate through the array
 * 2. For each number, if it's in range [1, n], place it in its correct position
 * 3. Use swapping to place numbers in correct positions
 * 4. After reorganization, scan to find first missing positive
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Hash Table
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Index Mapping (Cycle Sort) - RECOMMENDED
     * O(n) time, O(1) space
     */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        
        // Place each number in its correct position if possible
        for (int i = 0; i < n; i++) {
            // While the current number is in range [1, n] and not in correct position
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                // Swap nums[i] with nums[nums[i] - 1]
                swap(nums, i, nums[i] - 1);
            }
        }
        
        // Find the first position where the number is incorrect
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        
        // If all positions are correct, the answer is n+1
        return n + 1;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Approach 2: Boolean Marking (Modifies array values)
     * O(n) time, O(1) space
     * Uses negative marking to indicate presence of numbers
     */
    public int firstMissingPositiveBooleanMarking(int[] nums) {
        int n = nums.length;
        
        // First pass: Mark non-positive numbers with a value that won't interfere
        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0) {
                nums[i] = n + 1; // Mark as out of range
            }
        }
        
        // Second pass: Mark positions as negative to indicate presence
        for (int i = 0; i < n; i++) {
            int num = Math.abs(nums[i]);
            if (num <= n) {
                nums[num - 1] = -Math.abs(nums[num - 1]);
            }
        }
        
        // Third pass: Find first positive value
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        
        return n + 1;
    }
    
    /**
     * Approach 3: Separate Index Array (Violates O(1) space but good for understanding)
     * O(n) time, O(n) space
     */
    public int firstMissingPositiveExtraSpace(int[] nums) {
        int n = nums.length;
        boolean[] present = new boolean[n + 1];
        
        // Mark which numbers are present
        for (int num : nums) {
            if (num > 0 && num <= n) {
                present[num] = true;
            }
        }
        
        // Find the first missing positive
        for (int i = 1; i <= n; i++) {
            if (!present[i]) {
                return i;
            }
        }
        
        return n + 1;
    }
    
    /**
     * Approach 4: Sorting (Violates O(n) time but simple)
     * O(n log n) time, O(1) space if sorting in-place
     */
    public int firstMissingPositiveSorting(int[] nums) {
        Arrays.sort(nums);
        int expected = 1;
        
        for (int num : nums) {
            if (num <= 0) {
                continue; // Skip non-positive numbers
            }
            
            // Skip duplicates
            if (num > 0 && num == expected - 1) {
                continue;
            }
            
            if (num != expected) {
                return expected;
            }
            expected++;
        }
        
        return expected;
    }
    
    /**
     * Approach 5: HashSet (Violates O(1) space but very clear)
     * O(n) time, O(n) space
     */
    public int firstMissingPositiveHashSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        
        // Add all positive numbers to set
        for (int num : nums) {
            if (num > 0) {
                set.add(num);
            }
        }
        
        // Find the first missing positive
        for (int i = 1; i <= nums.length + 1; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        
        return 1; // Should never reach here
    }
    
    /**
     * Approach 6: Bit Manipulation (Theoretical for fixed range)
     * O(n) time, O(1) space but limited by integer size
     */
    public int firstMissingPositiveBitManipulation(int[] nums) {
        long bitmask = 0;
        int n = nums.length;
        
        // Set bits for numbers in range [1, n]
        for (int num : nums) {
            if (num > 0 && num <= n) {
                bitmask |= (1L << (num - 1));
            }
        }
        
        // Find first unset bit
        for (int i = 0; i < n; i++) {
            if ((bitmask & (1L << i)) == 0) {
                return i + 1;
            }
        }
        
        return n + 1;
    }
    
    /**
     * Helper method to visualize the array reorganization process
     */
    private void visualizeReorganization(int[] nums, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Initial array: " + Arrays.toString(nums));
        
        int n = nums.length;
        int[] original = Arrays.copyOf(nums, n);
        
        if ("Index Mapping".equals(approach)) {
            for (int i = 0; i < n; i++) {
                System.out.printf("Step %d: ", i + 1);
                System.out.println(Arrays.toString(nums));
                
                while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                    System.out.printf("  Swap nums[%d]=%d with nums[%d]=%d -> ",
                            i, nums[i], nums[i] - 1, nums[nums[i] - 1]);
                    swap(nums, i, nums[i] - 1);
                    System.out.println(Arrays.toString(nums));
                }
            }
        } else if ("Boolean Marking".equals(approach)) {
            // Mark non-positive numbers
            for (int i = 0; i < n; i++) {
                if (nums[i] <= 0) {
                    nums[i] = n + 1;
                }
            }
            System.out.println("After marking non-positive: " + Arrays.toString(nums));
            
            // Mark presence with negatives
            for (int i = 0; i < n; i++) {
                int num = Math.abs(nums[i]);
                if (num <= n) {
                    nums[num - 1] = -Math.abs(nums[num - 1]);
                    System.out.printf("Mark position %d for number %d -> %s%n",
                            num - 1, num, Arrays.toString(nums));
                }
            }
        }
        
        // Find result
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                System.out.println("First mismatch at index " + i + ": expected " + (i + 1) + ", found " + nums[i]);
                System.out.println("Result: " + (i + 1));
                return;
            }
        }
        System.out.println("All positions correct, result: " + (n + 1));
        
        // Restore original
        System.arraycopy(original, 0, nums, 0, n);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing First Missing Positive Solution:");
        System.out.println("=========================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example [1,2,0]");
        int[] nums1 = {1, 2, 0};
        int expected1 = 3;
        testImplementation(solution, nums1, expected1, "Index Mapping");
        
        // Test case 2: Missing number in middle
        System.out.println("\nTest 2: Missing number in middle [3,4,-1,1]");
        int[] nums2 = {3, 4, -1, 1};
        int expected2 = 2;
        testImplementation(solution, nums2, expected2, "Index Mapping");
        
        // Test case 3: All numbers too large
        System.out.println("\nTest 3: All numbers too large [7,8,9,11,12]");
        int[] nums3 = {7, 8, 9, 11, 12};
        int expected3 = 1;
        testImplementation(solution, nums3, expected3, "Index Mapping");
        
        // Test case 4: Single element
        System.out.println("\nTest 4: Single element [1]");
        int[] nums4 = {1};
        int expected4 = 2;
        testImplementation(solution, nums4, expected4, "Index Mapping");
        
        // Test case 5: Empty array (edge case)
        System.out.println("\nTest 5: Single element [0]");
        int[] nums5 = {0};
        int expected5 = 1;
        testImplementation(solution, nums5, expected5, "Index Mapping");
        
        // Test case 6: Consecutive numbers
        System.out.println("\nTest 6: Consecutive numbers [1,2,3,4]");
        int[] nums6 = {1, 2, 3, 4};
        int expected6 = 5;
        testImplementation(solution, nums6, expected6, "Index Mapping");
        
        // Test case 7: With duplicates
        System.out.println("\nTest 7: With duplicates [1,1,2,2,3]");
        int[] nums7 = {1, 1, 2, 2, 3};
        int expected7 = 4;
        testImplementation(solution, nums7, expected7, "Index Mapping");
        
        // Test case 8: Large array with missing number
        System.out.println("\nTest 8: Large array with missing number");
        int[] nums8 = new int[100];
        Arrays.fill(nums8, 1);
        for (int i = 1; i < 100; i++) {
            nums8[i] = i + 1; // 1, 2, 3, ..., 100
        }
        nums8[50] = 1; // Remove number 52
        int expected8 = 52;
        testImplementation(solution, nums8, expected8, "Index Mapping");
        
        // Test case 9: Negative numbers only
        System.out.println("\nTest 9: Negative numbers only [-1,-2,-3]");
        int[] nums9 = {-1, -2, -3};
        int expected9 = 1;
        testImplementation(solution, nums9, expected9, "Index Mapping");
        
        // Test case 10: Mixed with zeros
        System.out.println("\nTest 10: Mixed with zeros [0,-1,2,1]");
        int[] nums10 = {0, -1, 2, 1};
        int expected10 = 3;
        testImplementation(solution, nums10, expected10, "Index Mapping");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: INDEX MAPPING APPROACH");
        System.out.println("=".repeat(70));
        
        explainIndexMappingApproach(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, int[] nums, int expected, String approach) {
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
        
        long startTime = System.nanoTime();
        int result = solution.firstMissingPositive(numsCopy);
        long time = System.nanoTime() - startTime;
        
        boolean passed = (result == expected);
        System.out.printf("%s: Expected=%d, Got=%d, Time=%,d ns - %s%n",
                approach, expected, result, time, (passed ? "PASSED" : "FAILED"));
        
        if (!passed) {
            System.out.println("  Input: " + Arrays.toString(nums));
        }
        
        // Restore array for visualization
        System.arraycopy(nums, 0, numsCopy, 0, nums.length);
    }
    
    private static void comparePerformance(Solution solution) {
        int size = 10000;
        int[] testArray = generateTestArray(size);
        
        System.out.println("\nPerformance test with " + size + " elements:");
        
        // Test Index Mapping
        int[] arr1 = Arrays.copyOf(testArray, testArray.length);
        long startTime = System.nanoTime();
        solution.firstMissingPositive(arr1);
        long time1 = System.nanoTime() - startTime;
        
        // Test Boolean Marking
        int[] arr2 = Arrays.copyOf(testArray, testArray.length);
        startTime = System.nanoTime();
        solution.firstMissingPositiveBooleanMarking(arr2);
        long time2 = System.nanoTime() - startTime;
        
        // Test Extra Space
        startTime = System.nanoTime();
        solution.firstMissingPositiveExtraSpace(testArray);
        long time3 = System.nanoTime() - startTime;
        
        // Test Sorting
        int[] arr4 = Arrays.copyOf(testArray, testArray.length);
        startTime = System.nanoTime();
        solution.firstMissingPositiveSorting(arr4);
        long time4 = System.nanoTime() - startTime;
        
        // Test HashSet
        startTime = System.nanoTime();
        solution.firstMissingPositiveHashSet(testArray);
        long time5 = System.nanoTime() - startTime;
        
        System.out.printf("Index Mapping:    %,12d ns%n", time1);
        System.out.printf("Boolean Marking:  %,12d ns%n", time2);
        System.out.printf("Extra Space:      %,12d ns%n", time3);
        System.out.printf("Sorting:          %,12d ns%n", time4);
        System.out.printf("HashSet:          %,12d ns%n", time5);
    }
    
    private static int[] generateTestArray(int size) {
        int[] arr = new int[size];
        Random random = new Random(42);
        
        // Fill with random numbers, some positive, some negative
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size * 2) - size / 2;
        }
        
        // Ensure at least one missing positive
        arr[random.nextInt(size)] = -1; // Create a gap
        
        return arr;
    }
    
    private static void explainIndexMappingApproach(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("The first missing positive must be in the range [1, n+1] where n is array length.");
        System.out.println("We can reorganize the array so that number x is at position x-1 (if 1 <= x <= n).");
        
        System.out.println("\nStep-by-step process:");
        System.out.println("1. For each index i from 0 to n-1:");
        System.out.println("   - While nums[i] is between 1 and n, and nums[i] is not at its correct position:");
        System.out.println("   - Swap nums[i] with nums[nums[i] - 1]");
        System.out.println("2. After reorganization, scan the array:");
        System.out.println("   - The first index i where nums[i] != i+1 gives the answer i+1");
        System.out.println("   - If all positions are correct, answer is n+1");
        
        System.out.println("\nExample: [3, 4, -1, 1]");
        int[] example = {3, 4, -1, 1};
        solution.visualizeReorganization(example, "Index Mapping");
        
        System.out.println("\nWhy it works:");
        System.out.println("- We ignore numbers <= 0 and > n because they don't affect the answer in range [1, n]");
        System.out.println("- By placing each number in its correct position, we create a direct mapping");
        System.out.println("- The first incorrect position reveals the missing number");
        System.out.println("- Time complexity is O(n) because each swap places a number in its final position");
    }
    
    private static void checkAllImplementations(Solution solution) {
        int[][] testCases = {
            {1, 2, 0},
            {3, 4, -1, 1},
            {7, 8, 9, 11, 12},
            {1},
            {0},
            {1, 1},
            {2, 1},
            {-1, -2, -3}
        };
        
        int[] expected = {3, 2, 1, 2, 1, 2, 3, 1};
        
        String[] methods = {
            "Index Mapping",
            "Boolean Marking", 
            "Extra Space",
            "Sorting",
            "HashSet",
            "Bit Manipulation"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.printf("\nTest case %d: %s (expected: %d)%n",
                    i + 1, Arrays.toString(testCases[i]), expected[i]);
            
            int[] results = new int[methods.length];
            results[0] = solution.firstMissingPositive(testCases[i].clone());
            results[1] = solution.firstMissingPositiveBooleanMarking(testCases[i].clone());
            results[2] = solution.firstMissingPositiveExtraSpace(testCases[i].clone());
            results[3] = solution.firstMissingPositiveSorting(testCases[i].clone());
            results[4] = solution.firstMissingPositiveHashSet(testCases[i].clone());
            results[5] = solution.firstMissingPositiveBitManipulation(testCases[i].clone());
            
            boolean caseConsistent = true;
            for (int j = 0; j < methods.length; j++) {
                boolean correct = (results[j] == expected[i]);
                System.out.printf("  %-15s: %d %s%n", methods[j], results[j],
                        correct ? "✓" : "✗ (expected " + expected[i] + ")");
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
    }
}
