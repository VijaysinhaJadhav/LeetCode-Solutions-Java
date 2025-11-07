/**
 * 238. Product of Array Except Self
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an integer array nums, return an array answer such that answer[i] is 
 * equal to the product of all the elements of nums except nums[i].
 * Must run in O(n) time without using division operation.
 * 
 * Key Insights:
 * 1. Product except self = product of left elements * product of right elements
 * 2. Can compute left products and right products separately
 * 3. For O(1) space: use output array to store intermediate results
 * 4. Handle zeros naturally without special cases
 * 
 * Approach (Prefix & Suffix Products):
 * 1. Initialize result array with 1s
 * 2. First pass: compute left products and store in result
 * 3. Second pass: compute right products and multiply with left products
 * 4. Return the final result
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1) excluding output array
 * 
 * Tags: Array, Prefix Sum
 */

import java.util.Arrays;

class Solution {
    /**
     * Approach 1: Prefix & Suffix Products with O(1) Space (RECOMMENDED)
     * Meets follow-up requirement
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        // Initialize result array with left products
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }
        
        // Multiply with right products
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] = result[i] * rightProduct;
            rightProduct *= nums[i];
        }
        
        return result;
    }
    
    /**
     * Approach 2: Separate Left and Right Products (More Intuitive)
     * O(n) time, O(n) space - easier to understand
     */
    public int[] productExceptSelfSeparate(int[] nums) {
        int n = nums.length;
        int[] leftProducts = new int[n];
        int[] rightProducts = new int[n];
        int[] result = new int[n];
        
        // Compute left products
        leftProducts[0] = 1;
        for (int i = 1; i < n; i++) {
            leftProducts[i] = leftProducts[i - 1] * nums[i - 1];
        }
        
        // Compute right products
        rightProducts[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            rightProducts[i] = rightProducts[i + 1] * nums[i + 1];
        }
        
        // Combine left and right products
        for (int i = 0; i < n; i++) {
            result[i] = leftProducts[i] * rightProducts[i];
        }
        
        return result;
    }
    
    /**
     * Approach 3: Single Pass with Two Pointers (Advanced)
     * Single pass solution, O(1) space
     */
    public int[] productExceptSelfSinglePass(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        // Initialize all elements to 1
        Arrays.fill(result, 1);
        
        int leftProduct = 1;
        int rightProduct = 1;
        
        for (int i = 0; i < n; i++) {
            // Multiply result[i] with left product
            result[i] *= leftProduct;
            leftProduct *= nums[i];
            
            // Multiply result[n-1-i] with right product
            result[n - 1 - i] *= rightProduct;
            rightProduct *= nums[n - 1 - i];
        }
        
        return result;
    }
    
    /**
     * Approach 4: Using Division (For Comparison - Violates Constraints)
     * Simple but doesn't meet problem requirements
     */
    public int[] productExceptSelfDivision(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        int totalProduct = 1;
        int zeroCount = 0;
        int zeroIndex = -1;
        
        // Calculate total product and count zeros
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zeroCount++;
                zeroIndex = i;
            } else {
                totalProduct *= nums[i];
            }
        }
        
        // Handle cases with zeros
        if (zeroCount > 1) {
            // If more than one zero, all products are zero
            Arrays.fill(result, 0);
        } else if (zeroCount == 1) {
            // If exactly one zero, only that position has non-zero product
            Arrays.fill(result, 0);
            result[zeroIndex] = totalProduct;
        } else {
            // No zeros, use division
            for (int i = 0; i < n; i++) {
                result[i] = totalProduct / nums[i];
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Logarithmic Approach (Theoretical - Limited by Precision)
     * Uses logarithms to convert multiplication to addition
     * Not practical due to precision issues with integers
     */
    public int[] productExceptSelfLogarithm(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        // This approach has precision issues with integers
        // and doesn't handle zeros and negative numbers well
        // Included for educational purposes only
        
        double logSum = 0.0;
        for (int num : nums) {
            if (num == 0) {
                // Cannot take log of zero
                // This approach fails with zeros
                return productExceptSelf(nums); // Fallback to main approach
            }
            logSum += Math.log(num);
        }
        
        for (int i = 0; i < n; i++) {
            result[i] = (int) Math.round(Math.exp(logSum - Math.log(nums[i])));
        }
        
        return result;
    }
    
    /**
     * Helper method to verify result
     */
    private boolean verifyProduct(int[] nums, int[] result) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int expected = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    expected *= nums[j];
                }
            }
            if (result[i] != expected) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Helper method to print array with product calculation
     */
    private void printProductCalculation(int[] nums, int[] result) {
        System.out.println("Input:  " + Arrays.toString(nums));
        System.out.println("Output: " + Arrays.toString(result));
        System.out.println("Verification:");
        for (int i = 0; i < nums.length; i++) {
            System.out.print("  result[" + i + "] = ");
            boolean first = true;
            for (int j = 0; j < nums.length; j++) {
                if (i != j) {
                    if (!first) System.out.print(" * ");
                    System.out.print(nums[j]);
                    first = false;
                }
            }
            System.out.println(" = " + result[i]);
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Product of Array Except Self Solution:");
        System.out.println("===============================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] nums1 = {1, 2, 3, 4};
        int[] expected1 = {24, 12, 8, 6};
        
        long startTime = System.nanoTime();
        int[] result1a = solution.productExceptSelf(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1b = solution.productExceptSelfSeparate(nums1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1c = solution.productExceptSelfSinglePass(nums1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = Arrays.equals(result1a, expected1);
        boolean test1b = Arrays.equals(result1b, expected1);
        boolean test1c = Arrays.equals(result1c, expected1);
        
        System.out.println("O(1) Space: " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Separate Arrays: " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Single Pass: " + (test1c ? "PASSED" : "FAILED"));
        
        solution.printProductCalculation(nums1, result1a);
        
        // Test case 2: Array with zeros
        System.out.println("\nTest 2: Array with zeros");
        int[] nums2 = {-1, 1, 0, -3, 3};
        int[] expected2 = {0, 0, 9, 0, 0};
        
        int[] result2a = solution.productExceptSelf(nums2);
        int[] result2b = solution.productExceptSelfSeparate(nums2);
        int[] result2c = solution.productExceptSelfSinglePass(nums2);
        
        boolean test2a = Arrays.equals(result2a, expected2);
        boolean test2b = Arrays.equals(result2b, expected2);
        boolean test2c = Arrays.equals(result2c, expected2);
        
        System.out.println("O(1) Space: " + (test2a ? "PASSED" : "FAILED"));
        System.out.println("Separate Arrays: " + (test2b ? "PASSED" : "FAILED"));
        System.out.println("Single Pass: " + (test2c ? "PASSED" : "FAILED"));
        
        solution.printProductCalculation(nums2, result2a);
        
        // Test case 3: Array with one zero
        System.out.println("\nTest 3: Array with one zero");
        int[] nums3 = {1, 2, 0, 4};
        int[] expected3 = {0, 0, 8, 0};
        
        int[] result3a = solution.productExceptSelf(nums3);
        boolean test3a = Arrays.equals(result3a, expected3);
        System.out.println("One zero: " + (test3a ? "PASSED" : "FAILED"));
        solution.printProductCalculation(nums3, result3a);
        
        // Test case 4: Array with all zeros
        System.out.println("\nTest 4: Array with all zeros");
        int[] nums4 = {0, 0, 0, 0};
        int[] expected4 = {0, 0, 0, 0};
        
        int[] result4a = solution.productExceptSelf(nums4);
        boolean test4a = Arrays.equals(result4a, expected4);
        System.out.println("All zeros: " + (test4a ? "PASSED" : "FAILED"));
        
        // Test case 5: Array with negative numbers
        System.out.println("\nTest 5: Array with negative numbers");
        int[] nums5 = {-1, -2, -3, -4};
        int[] result5a = solution.productExceptSelf(nums5);
        boolean test5a = solution.verifyProduct(nums5, result5a);
        System.out.println("Negative numbers: " + (test5a ? "PASSED" : "FAILED"));
        solution.printProductCalculation(nums5, result5a);
        
        // Test case 6: Two elements
        System.out.println("\nTest 6: Two elements");
        int[] nums6 = {2, 3};
        int[] expected6 = {3, 2};
        
        int[] result6a = solution.productExceptSelf(nums6);
        boolean test6a = Arrays.equals(result6a, expected6);
        System.out.println("Two elements: " + (test6a ? "PASSED" : "FAILED"));
        
        // Test case 7: Large numbers (within 32-bit constraint)
        System.out.println("\nTest 7: Large numbers");
        int[] nums7 = {10, 20, 30, 40};
        int[] result7a = solution.productExceptSelf(nums7);
        boolean test7a = solution.verifyProduct(nums7, result7a);
        System.out.println("Large numbers: " + (test7a ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 8: Performance comparison");
        int[] largeNums = new int[100000];
        Arrays.fill(largeNums, 2); // All 2s for simple calculation
        
        startTime = System.nanoTime();
        int[] result8a = solution.productExceptSelf(largeNums);
        long time8a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result8b = solution.productExceptSelfSeparate(largeNums);
        long time8b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result8c = solution.productExceptSelfSinglePass(largeNums);
        long time8c = System.nanoTime() - startTime;
        
        System.out.println("Large array (100,000 elements):");
        System.out.println("  O(1) Space: " + time8a + " ns");
        System.out.println("  Separate Arrays: " + time8b + " ns");
        System.out.println("  Single Pass: " + time8c + " ns");
        
        // Verify all approaches produce same results
        boolean allEqual = Arrays.equals(result8a, result8b) && 
                          Arrays.equals(result8a, result8c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm visualization for understanding
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM VISUALIZATION:");
        System.out.println("=".repeat(70));
        
        int[] demoNums = {1, 2, 3, 4};
        System.out.println("Input array: " + Arrays.toString(demoNums));
        System.out.println("\nStep 1: Compute left products (store in result)");
        
        int[] leftProducts = new int[demoNums.length];
        leftProducts[0] = 1;
        for (int i = 1; i < demoNums.length; i++) {
            leftProducts[i] = leftProducts[i - 1] * demoNums[i - 1];
        }
        System.out.println("Left products: " + Arrays.toString(leftProducts));
        
        System.out.println("\nStep 2: Compute right products and multiply");
        int[] result = leftProducts.clone();
        int rightProduct = 1;
        System.out.println("Initial result: " + Arrays.toString(result));
        
        for (int i = demoNums.length - 1; i >= 0; i--) {
            System.out.println("i=" + i + ", rightProduct=" + rightProduct);
            System.out.println("  result[" + i + "] = " + result[i] + " * " + rightProduct + " = " + (result[i] * rightProduct));
            result[i] = result[i] * rightProduct;
            rightProduct *= demoNums[i];
            System.out.println("  Updated result: " + Arrays.toString(result));
            System.out.println("  Updated rightProduct: " + rightProduct);
        }
        
        System.out.println("\nFinal result: " + Arrays.toString(result));
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Prefix & Suffix Products with O(1) Space (RECOMMENDED):");
        System.out.println("   Time: O(n) - Two passes through array");
        System.out.println("   Space: O(1) - Only constant extra space (output doesn't count)");
        System.out.println("   How it works:");
        System.out.println("     - First pass: compute left products → result[i] = product of nums[0..i-1]");
        System.out.println("     - Second pass: multiply with right products → result[i] *= product of nums[i+1..n-1]");
        System.out.println("   Pros:");
        System.out.println("     - Meets all constraints including follow-up");
        System.out.println("     - Efficient and elegant");
        System.out.println("     - Handles zeros naturally");
        System.out.println("   Cons:");
        System.out.println("     - Slightly less intuitive than separate arrays");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Separate Left and Right Products:");
        System.out.println("   Time: O(n) - Three passes through array");
        System.out.println("   Space: O(n) - Two additional arrays");
        System.out.println("   How it works:");
        System.out.println("     - Compute left products in one array");
        System.out.println("     - Compute right products in another array");
        System.out.println("     - Multiply corresponding elements");
        System.out.println("   Pros:");
        System.out.println("     - Very intuitive and easy to understand");
        System.out.println("     - Clear separation of concerns");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("     - Doesn't meet follow-up requirement");
        System.out.println("   Best for: Learning, when clarity is prioritized");
        
        System.out.println("\n3. Single Pass with Two Pointers:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Maintain left and right running products");
        System.out.println("     - Update result from both ends simultaneously");
        System.out.println("   Pros:");
        System.out.println("     - Single pass algorithm");
        System.out.println("     - Meets follow-up requirement");
        System.out.println("   Cons:");
        System.out.println("     - More complex to implement and understand");
        System.out.println("     - Can be error-prone");
        System.out.println("   Best for: When single pass is absolutely required");
        
        System.out.println("\n4. Division Approach (Violates Constraints):");
        System.out.println("   Time: O(n) - Two passes");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Compute total product of all elements");
        System.out.println("     - For each element, result[i] = totalProduct / nums[i]");
        System.out.println("     - Special handling for zeros");
        System.out.println("   Pros:");
        System.out.println("     - Very simple and intuitive");
        System.out.println("     - Efficient");
        System.out.println("   Cons:");
        System.out.println("     - Violates problem constraint (no division)");
        System.out.println("     - Doesn't work if multiple zeros exist");
        System.out.println("   Best for: Understanding the problem concept only");
        
        System.out.println("\n5. Logarithmic Approach (Theoretical):");
        System.out.println("   Time: O(n) - Two passes");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Use logarithms to convert multiplication to addition");
        System.out.println("     - result[i] = exp(totalLogSum - log(nums[i]))");
        System.out.println("   Pros:");
        System.out.println("     - Mathematical elegance");
        System.out.println("     - Avoids division");
        System.out.println("   Cons:");
        System.out.println("     - Precision issues with integers");
        System.out.println("     - Doesn't handle zeros or negative numbers well");
        System.out.println("     - Not practical for this problem");
        System.out.println("   Best for: Theoretical discussion only");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("KEY INSIGHTS:");
        System.out.println("1. The product except self can be broken into left and right products");
        System.out.println("2. We can compute these products in separate passes");
        System.out.println("3. Using the output array for intermediate results saves space");
        System.out.println("4. Zeros are handled naturally - if nums[i] = 0, result[i] = product of all others");
        System.out.println("5. The algorithm works for any input within 32-bit constraints");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with O(1) space approach - it's the expected solution");
        System.out.println("2. Explain the left product/right product concept clearly");
        System.out.println("3. Draw the array and show how products are built");
        System.out.println("4. Mention the division approach but explain why it's not allowed");
        System.out.println("5. Handle edge cases: zeros, negative numbers, two elements");
        System.out.println("6. Practice the two-pass algorithm until it's intuitive");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
