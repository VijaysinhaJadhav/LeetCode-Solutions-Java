/**
 * 217. Contains Duplicate
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given an integer array nums, return true if any value appears at least twice 
 * in the array, and return false if every element is distinct.
 * 
 * Key Insights:
 * 1. HashSet provides O(1) lookups and insertions on average
 * 2. If we encounter an element already in the set, we found a duplicate
 * 3. Alternative: sort and check adjacent elements (O(n log n) time, O(1) space)
 * 4. HashSet approach is optimal for time complexity
 * 
 * Approach:
 * 1. Create a HashSet to store seen elements
 * 2. Iterate through each element in the array
 * 3. For each element, check if it exists in the set
 * 4. If found, return true (duplicate found)
 * 5. If not found, add to set and continue
 * 6. If loop completes, return false (no duplicates)
 * 
 * Time Complexity: O(n) - Single pass through array
 * Space Complexity: O(n) - HashSet storage
 * 
 * Tags: Array, Hash Table, Sorting
 */

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

class Solution {
    /**
     * HashSet approach - Optimal time complexity
     * 
     * @param nums the input array
     * @return true if array contains duplicates, false otherwise
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        
        for (int num : nums) {
            // If we've seen this number before, found duplicate
            if (seen.contains(num)) {
                return true;
            }
            // Add to set for future checks
            seen.add(num);
        }
        
        // No duplicates found
        return false;
    }
    
    /**
     * Alternative approach using sorting
     * Better space complexity, worse time complexity
     * 
     * @param nums the input array  
     * @return true if array contains duplicates, false otherwise
     */
    public boolean containsDuplicateSorting(int[] nums) {
        // Sort the array first
        Arrays.sort(nums);
        
        // Check adjacent elements
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Brute force approach (for comparison)
     * Not efficient for large inputs - O(n²) time
     */
    public boolean containsDuplicateBruteForce(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Helper method to print test results
     */
    private static void printTestResult(String testName, boolean expected, boolean actual) {
        System.out.println(testName + ": " + (expected == actual ? "PASSED" : "FAILED"));
        System.out.println("  Expected: " + expected + ", Actual: " + actual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Contains Duplicate Solution:");
        System.out.println("====================================");
        
        // Test case 1: Array with duplicates
        System.out.println("\nTest 1: Array with duplicates");
        int[] nums1 = {1, 2, 3, 1};
        boolean expected1 = true;
        
        boolean result1a = solution.containsDuplicate(nums1);
        boolean result1b = solution.containsDuplicateSorting(nums1);
        boolean result1c = solution.containsDuplicateBruteForce(nums1);
        
        printTestResult("HashSet approach", expected1, result1a);
        printTestResult("Sorting approach", expected1, result1b);
        printTestResult("Brute force approach", expected1, result1c);
        
        // Test case 2: Array without duplicates
        System.out.println("\nTest 2: Array without duplicates");
        int[] nums2 = {1, 2, 3, 4};
        boolean expected2 = false;
        
        boolean result2a = solution.containsDuplicate(nums2);
        boolean result2b = solution.containsDuplicateSorting(nums2);
        boolean result2c = solution.containsDuplicateBruteForce(nums2);
        
        printTestResult("HashSet approach", expected2, result2a);
        printTestResult("Sorting approach", expected2, result2b);
        printTestResult("Brute force approach", expected2, result2c);
        
        // Test case 3: Large array with duplicates
        System.out.println("\nTest 3: Large array with duplicates at end");
        int[] nums3 = new int[10000];
        for (int i = 0; i < 9999; i++) {
            nums3[i] = i;
        }
        nums3[9999] = 9998; // Duplicate at the end
        boolean expected3 = true;
        
        long startTime = System.nanoTime();
        boolean result3a = solution.containsDuplicate(nums3);
        long hashSetTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result3b = solution.containsDuplicateSorting(nums3);
        long sortingTime = System.nanoTime() - startTime;
        
        // Note: Brute force would be too slow for large input
        
        printTestResult("HashSet approach (large input)", expected3, result3a);
        printTestResult("Sorting approach (large input)", expected3, result3b);
        
        System.out.println("  Performance - HashSet: " + hashSetTime + " ns, Sorting: " + sortingTime + " ns");
        
        // Test case 4: Single element
        System.out.println("\nTest 4: Single element array");
        int[] nums4 = {5};
        boolean expected4 = false;
        
        boolean result4a = solution.containsDuplicate(nums4);
        boolean result4b = solution.containsDuplicateSorting(nums4);
        
        printTestResult("HashSet approach", expected4, result4a);
        printTestResult("Sorting approach", expected4, result4b);
        
        // Test case 5: Negative numbers with duplicates
        System.out.println("\nTest 5: Negative numbers with duplicates");
        int[] nums5 = {-1, 2, -1, 4, 5};
        boolean expected5 = true;
        
        boolean result5a = solution.containsDuplicate(nums5);
        boolean result5b = solution.containsDuplicateSorting(nums5);
        
        printTestResult("HashSet approach", expected5, result5a);
        printTestResult("Sorting approach", expected5, result5b);
        
        // Performance comparison summary
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE COMPARISON SUMMARY:");
        System.out.println("HashSet Approach:");
        System.out.println("  - Time: O(n), Space: O(n)");
        System.out.println("  - Best for: Time-sensitive applications");
        System.out.println("  - Worst for: Memory-constrained environments");
        
        System.out.println("\nSorting Approach:");
        System.out.println("  - Time: O(n log n), Space: O(1) or O(n) depending on sort");
        System.out.println("  - Best for: Memory-constrained environments");
        System.out.println("  - Worst for: Very large inputs where O(n log n) is too slow");
        
        System.out.println("\nBrute Force Approach:");
        System.out.println("  - Time: O(n²), Space: O(1)");
        System.out.println("  - Only suitable for very small inputs (n < 1000)");
        
        System.out.println("\nRecommendation: Use HashSet approach for most cases");
        System.out.println("=".repeat(50));
        
        System.out.println("\nAll tests completed!");
    }
}
