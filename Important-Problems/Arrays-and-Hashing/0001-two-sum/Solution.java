/**
 * 1. Two Sum
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given an array of integers nums and an integer target, return indices of the two numbers
 * such that they add up to target. Each input has exactly one solution, and you may not
 * use the same element twice.
 * 
 * Key Insights:
 * 1. Instead of checking all pairs (O(n²)), use complement approach
 * 2. Store numbers and their indices in a hash map for O(1) lookups
 * 3. Check complement before adding current number to handle duplicates
 * 
 * Approach:
 * 1. Create a hash map to store number -> index
 * 2. Iterate through the array
 * 3. For each number, calculate complement = target - current number
 * 4. If complement exists in map, return [map.get(complement), current index]
 * 5. Otherwise, add current number and index to map
 * 
 * Time Complexity: O(n) - Single pass through array
 * Space Complexity: O(n) - HashMap storage
 * 
 * Tags: Array, Hash Table
 */

import java.util.HashMap;
import java.util.Map;

class Solution {
    /**
     * Finds two indices such that the corresponding numbers sum to target
     * 
     * @param nums array of integers
     * @param target target sum
     * @return array of two indices [i, j] where nums[i] + nums[j] == target
     * @throws IllegalArgumentException if no solution found
     */
    public int[] twoSum(int[] nums, int target) {
        // Create a hash map to store numbers and their indices
        Map<Integer, Integer> numMap = new HashMap<>();
        
        // Iterate through each number in the array
        for (int i = 0; i < nums.length; i++) {
            // Calculate the complement needed to reach target
            int complement = target - nums[i];
            
            // Check if complement exists in our map
            if (numMap.containsKey(complement)) {
                // Return the indices of complement and current number
                return new int[] { numMap.get(complement), i };
            }
            
            // Add current number to map AFTER checking complement
            // This ensures we don't use the same element twice
            numMap.put(nums[i], i);
        }
        
        // According to problem constraints, exactly one solution exists
        // So this exception should never be thrown with valid input
        throw new IllegalArgumentException("No two sum solution found");
    }
    
    // Helper method to print array (useful for testing)
    private static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * Test cases to verify the solution
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Two Sum Solution:");
        System.out.println("=========================");
        
        // Test case 1: Basic example from problem
        System.out.println("\nTest 1: Basic example");
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        int[] result1 = solution.twoSum(nums1, target1);
        System.out.print("Input: nums = [2,7,11,15], target = 9 | ");
        System.out.print("Output: ");
        printArray(result1);
        System.out.println("Expected: [0, 1]");
        
        // Test case 2: Numbers not at beginning
        System.out.println("\nTest 2: Numbers not at beginning");
        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        int[] result2 = solution.twoSum(nums2, target2);
        System.out.print("Input: nums = [3,2,4], target = 6 | ");
        System.out.print("Output: ");
        printArray(result2);
        System.out.println("Expected: [1, 2]");
        
        // Test case 3: Duplicate numbers
        System.out.println("\nTest 3: Duplicate numbers");
        int[] nums3 = {3, 3};
        int target3 = 6;
        int[] result3 = solution.twoSum(nums3, target3);
        System.out.print("Input: nums = [3,3], target = 6 | ");
        System.out.print("Output: ");
        printArray(result3);
        System.out.println("Expected: [0, 1]");
        
        // Test case 4: Negative numbers
        System.out.println("\nTest 4: Negative numbers");
        int[] nums4 = {-3, 4, 3, 90};
        int target4 = 0;
        int[] result4 = solution.twoSum(nums4, target4);
        System.out.print("Input: nums = [-3,4,3,90], target = 0 | ");
        System.out.print("Output: ");
        printArray(result4);
        System.out.println("Expected: [0, 2]");
        
        System.out.println("\nAll test cases completed!");
    }
}
