/**
 * 1929. Concatenation of Array
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given an integer array nums of length n, create an array ans of length 2n
 * where ans[i] == nums[i] and ans[i + n] == nums[i] for 0 <= i < n.
 * Essentially, ans is the concatenation of two nums arrays.
 * 
 * Key Insights:
 * 1. The output array is exactly twice the size of input array
 * 2. First half is identical to input array
 * 3. Second half is also identical to input array
 * 4. Can be solved by copying the array twice
 * 
 * Approach:
 * 1. Create result array of length 2 * nums.length
 * 2. Copy elements from nums to first half of result
 * 3. Copy elements from nums to second half of result
 * 4. Return the result array
 * 
 * Time Complexity: O(n) - We iterate through the array of n elements
 * Space Complexity: O(n) - We create output array of size 2n
 * 
 * Tags: Array, Simulation
 */

class Solution {
    /**
     * Concatenates the input array with itself
     * 
     * @param nums the input integer array
     * @return concatenated array of length 2 * nums.length
     */
    public int[] getConcatenation(int[] nums) {
        int n = nums.length;
        // Create result array of twice the size
        int[] ans = new int[2 * n];
        
        // Copy elements to first half
        for (int i = 0; i < n; i++) {
            ans[i] = nums[i];
        }
        
        // Copy elements to second half
        for (int i = 0; i < n; i++) {
            ans[i + n] = nums[i];
        }
        
        return ans;
    }
    
    /**
     * Alternative implementation using single loop
     * More concise but same time complexity
     */
    public int[] getConcatenationSingleLoop(int[] nums) {
        int n = nums.length;
        int[] ans = new int[2 * n];
        
        for (int i = 0; i < n; i++) {
            // Copy to first half and second half simultaneously
            ans[i] = nums[i];
            ans[i + n] = nums[i];
        }
        
        return ans;
    }
    
    /**
     * Helper method to print array in readable format
     */
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
        
        System.out.println("Testing Concatenation of Array Solution:");
        System.out.println("========================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int[] nums1 = {1, 2, 1};
        int[] result1 = solution.getConcatenation(nums1);
        System.out.print("Input: [1, 2, 1] | ");
        System.out.print("Output: ");
        printArray(result1);
        System.out.println("Expected: [1, 2, 1, 1, 2, 1]");
        
        // Test case 2: Another example
        System.out.println("\nTest 2: Four elements");
        int[] nums2 = {1, 3, 2, 1};
        int[] result2 = solution.getConcatenation(nums2);
        System.out.print("Input: [1, 3, 2, 1] | ");
        System.out.print("Output: ");
        printArray(result2);
        System.out.println("Expected: [1, 3, 2, 1, 1, 3, 2, 1]");
        
        // Test case 3: Single element
        System.out.println("\nTest 3: Single element");
        int[] nums3 = {5};
        int[] result3 = solution.getConcatenation(nums3);
        System.out.print("Input: [5] | ");
        System.out.print("Output: ");
        printArray(result3);
        System.out.println("Expected: [5, 5]");
        
        // Test case 4: Empty array
        System.out.println("\nTest 4: Empty array");
        int[] nums4 = {};
        int[] result4 = solution.getConcatenation(nums4);
        System.out.print("Input: [] | ");
        System.out.print("Output: ");
        printArray(result4);
        System.out.println("Expected: []");
        
        // Test case 5: Using alternative single loop method
        System.out.println("\nTest 5: Alternative method (single loop)");
        int[] nums5 = {1, 2, 3, 4};
        int[] result5 = solution.getConcatenationSingleLoop(nums5);
        System.out.print("Input: [1, 2, 3, 4] | ");
        System.out.print("Output: ");
        printArray(result5);
        System.out.println("Expected: [1, 2, 3, 4, 1, 2, 3, 4]");
        
        // Verify both methods produce same result
        System.out.println("\nVerifying both methods produce identical results:");
        int[] testNums = {1, 2, 3};
        int[] resultA = solution.getConcatenation(testNums);
        int[] resultB = solution.getConcatenationSingleLoop(testNums);
        System.out.print("Two-loop method: ");
        printArray(resultA);
        System.out.print("Single-loop method: ");
        printArray(resultB);
        
        System.out.println("\nAll test cases completed!");
    }
}
