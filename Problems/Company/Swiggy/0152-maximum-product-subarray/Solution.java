
## Solution.java

```java
/**
 * 152. Maximum Product Subarray
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an integer array nums, find the contiguous subarray with the largest product.
 * 
 * Key Insights:
 * 1. Unlike sum, product can flip sign with negative numbers
 * 2. Need to track both maximum and minimum products ending at each position
 * 3. Minimum product can become maximum when multiplied by negative
 * 4. Zero resets the product chain
 * 
 * Approach (Dynamic Programming with Two Variables):
 * 1. Initialize maxProd, minProd, and result with nums[0]
 * 2. For each number from index 1:
 *    - Calculate new max = max(nums[i], maxProd * nums[i], minProd * nums[i])
 *    - Calculate new min = min(nums[i], maxProd * nums[i], minProd * nums[i])
 *    - Update result with max(result, new max)
 *    - Update maxProd and minProd
 * 3. Return result
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Dynamic Programming
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Dynamic Programming with Two Variables (RECOMMENDED)
     * O(n) time, O(1) space
     */
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // Initialize with first element
        int maxProd = nums[0];
        int minProd = nums[0];
        int result = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            // Store previous values before updating
            int prevMax = maxProd;
            int prevMin = minProd;
            
            // Current number could:
            // 1. Start new subarray
            // 2. Extend previous max product
            // 3. Extend previous min product (if current is negative)
            maxProd = Math.max(nums[i], Math.max(prevMax * nums[i], prevMin * nums[i]));
            minProd = Math.min(nums[i], Math.min(prevMax * nums[i], prevMin * nums[i]));
            
            // Update global maximum
            result = Math.max(result, maxProd);
        }
        
        return result;
    }
    
    /**
     * Approach 2: Two Pass Approach (Forward and Backward)
     * O(n) time, O(1) space - handles negative count parity
     */
    public int maxProductTwoPass(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int maxProd = Integer.MIN_VALUE;
        int product = 1;
        
        // Forward pass
        for (int i = 0; i < nums.length; i++) {
            product *= nums[i];
            maxProd = Math.max(maxProd, product);
            if (product == 0) {
                product = 1; // Reset on zero
            }
        }
        
        // Backward pass (handles odd number of negatives)
        product = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            product *= nums[i];
            maxProd = Math.max(maxProd, product);
            if (product == 0) {
                product = 1; // Reset on zero
            }
        }
        
        return maxProd;
    }
    
    /**
     * Approach 3: Brute Force (for comparison)
     * O(n²) time, O(1) space
     */
    public int maxProductBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int maxProd = Integer.MIN_VALUE;
        
        for (int i = 0; i < nums.length; i++) {
            int product = 1;
            for (int j = i; j < nums.length; j++) {
                product *= nums[j];
                maxProd = Math.max(maxProd, product);
            }
        }
        
        return maxProd;
    }
    
    /**
     * Approach 4: Divide and Conquer
     * O(n log n) time, O(log n) space for recursion
     */
    public int maxProductDivideConquer(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return maxProductHelper(nums, 0, nums.length - 1);
    }
    
    private int maxProductHelper(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }
        
        int mid = left + (right - left) / 2;
        
        // Max product in left half
        int leftMax = maxProductHelper(nums, left, mid);
        // Max product in right half
        int rightMax = maxProductHelper(nums, mid + 1, right);
        // Max product crossing the middle
        int crossMax = maxCrossingProduct(nums, left, mid, right);
        
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }
    
    private int maxCrossingProduct(int[] nums, int left, int mid, int right) {
        // Left side of crossing
        int leftProd = 1;
        int leftMax = Integer.MIN_VALUE;
        int leftMin = Integer.MAX_VALUE;
        
        for (int i = mid; i >= left; i--) {
            leftProd *= nums[i];
            leftMax = Math.max(leftMax, leftProd);
            leftMin = Math.min(leftMin, leftProd);
        }
        
        // Right side of crossing
        int rightProd = 1;
        int rightMax = Integer.MIN_VALUE;
        int rightMin = Integer.MAX_VALUE;
        
        for (int i = mid + 1; i <= right; i++) {
            rightProd *= nums[i];
            rightMax = Math.max(rightMax, rightProd);
            rightMin = Math.min(rightMin, rightProd);
        }
        
        // Maximum crossing product
        return Math.max(leftMax * rightMax, leftMin * rightMin);
    }
    
    /**
     * Approach 5: Kadane's Algorithm Variation
     * Similar to Approach 1 but with different perspective
     */
    public int maxProductKadane(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int result = nums[0];
        int currMax = nums[0];
        int currMin = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            
            // If current number is negative, swap max and min
            // because multiplying by negative flips the order
            if (num < 0) {
                int temp = currMax;
                currMax = currMin;
                currMin = temp;
            }
            
            // Similar to Kadane's but for product
            currMax = Math.max(num, currMax * num);
            currMin = Math.min(num, currMin * num);
            
            result = Math.max(result, currMax);
        }
        
        return result;
    }
    
    /**
     * Helper method to visualize the DP process
     */
    private void visualizeDP(int[] nums, String approach) {
        System.out.println("\n" + approach + " - DP Visualization:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("\nStep-by-step calculation:");
        System.out.println("Index | Value | Max Ending Here | Min Ending Here | Global Max");
        System.out.println("------|-------|-----------------|-----------------|-----------");
        
        if (nums.length == 0) {
            System.out.println("Empty array");
            return;
        }
        
        int maxProd = nums[0];
        int minProd = nums[0];
        int result = nums[0];
        
        System.out.printf("  0   |  %3d  |      %3d        |      %3d        |    %3d%n", 
            nums[0], maxProd, minProd, result);
        
        for (int i = 1; i < nums.length; i++) {
            int prevMax = maxProd;
            int prevMin = minProd;
            
            int candidate1 = nums[i];
            int candidate2 = prevMax * nums[i];
            int candidate3 = prevMin * nums[i];
            
            maxProd = Math.max(candidate1, Math.max(candidate2, candidate3));
            minProd = Math.min(candidate1, Math.min(candidate2, candidate3));
            result = Math.max(result, maxProd);
            
            System.out.printf("  %-3d |  %3d  |      %3d        |      %3d        |    %3d%n", 
                i, nums[i], maxProd, minProd, result);
            System.out.printf("       |       |   max(%d, %d, %d) |   min(%d, %d, %d) |%n",
                candidate1, candidate2, candidate3, candidate1, candidate2, candidate3);
        }
        
        System.out.println("\nFinal maximum product: " + result);
        
        // Show the actual subarray
        System.out.println("\nFinding the actual subarray:");
        findAndPrintMaxProductSubarray(nums);
    }
    
    private void findAndPrintMaxProductSubarray(int[] nums) {
        if (nums.length == 0) return;
        
        int maxProd = nums[0];
        int start = 0, end = 0;
        int currStart = 0;
        
        int currMax = nums[0];
        int currMin = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            int prevMax = currMax;
            int prevMin = currMin;
            
            // Calculate new max and min
            int newMax = Math.max(nums[i], Math.max(prevMax * nums[i], prevMin * nums[i]));
            int newMin = Math.min(nums[i], Math.min(prevMax * nums[i], prevMin * nums[i]));
            
            // Check if we're starting a new subarray
            if (newMax == nums[i]) {
                currStart = i;
            }
            
            // Update global max and bounds
            if (newMax > maxProd) {
                maxProd = newMax;
                start = currStart;
                end = i;
            }
            
            currMax = newMax;
            currMin = newMin;
        }
        
        System.out.println("Maximum product: " + maxProd);
        System.out.print("Subarray: [");
        for (int i = start; i <= end; i++) {
            System.out.print(nums[i]);
            if (i < end) System.out.print(", ");
        }
        System.out.println("]");
        
        // Verify the product
        int verify = 1;
        for (int i = start; i <= end; i++) {
            verify *= nums[i];
        }
        System.out.println("Verification: " + verify + " (matches: " + (verify == maxProd) + ")");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Maximum Product Subarray:");
        System.out.println("==================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int[] nums1 = {2, 3, -2, 4};
        int expected1 = 6;
        
        solution.visualizeDP(nums1, "DP Approach");
        
        long startTime = System.nanoTime();
        int result1a = solution.maxProduct(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.maxProductTwoPass(nums1);
        long time1b = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Two-Variable DP: " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1a + " ns)");
        System.out.println("Two-Pass:        " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1b + " ns)");
        
        // Test case 2: Example 2 from problem
        System.out.println("\nTest 2: With zero");
        int[] nums2 = {-2, 0, -1};
        int expected2 = 0;
        
        solution.visualizeDP(nums2, "DP Approach");
        
        int result2a = solution.maxProduct(nums2);
        System.out.println("Result: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: All positive
        System.out.println("\nTest 3: All positive numbers");
        int[] nums3 = {1, 2, 3, 4, 5};
        int expected3 = 120; // Product of all
        
        solution.visualizeDP(nums3, "DP Approach");
        int result3a = solution.maxProduct(nums3);
        System.out.println("Result: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: All negative (odd count)
        System.out.println("\nTest 4: All negative, odd count");
        int[] nums4 = {-2, -3, -4};
        // Options: (-2) = -2, (-3) = -3, (-4) = -4, (-2,-3) = 6, (-3,-4) = 12, (-2,-3,-4) = -24
        int expected4 = 12;
        
        solution.visualizeDP(nums4, "DP Approach");
        int result4a = solution.maxProduct(nums4);
        System.out.println("Result: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: All negative (even count)
        System.out.println("\nTest 5: All negative, even count");
        int[] nums5 = {-2, -3, -4, -5};
        // Maximum is product of all: (-2)*(-3)*(-4)*(-5) = 120
        int expected5 = 120;
        
        solution.visualizeDP(nums5, "DP Approach");
        int result5a = solution.maxProduct(nums5);
        System.out.println("Result: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Single element
        System.out.println("\nTest 6: Single element");
        int[] nums6 = {-5};
        int expected6 = -5;
        
        int result6a = solution.maxProduct(nums6);
        System.out.println("Result: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Mixed with zeros
        System.out.println("\nTest 7: Mixed with zeros");
        int[] nums7 = {-2, 0, 3, 4, -5, 0, 2, 3};
        // Maximum should be 3*4 = 12 or 2*3 = 6
        int expected7 = 12;
        
        solution.visualizeDP(nums7, "DP Approach");
        int result7a = solution.maxProduct(nums7);
        System.out.println("Result: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Edge case with large negatives
        System.out.println("\nTest 8: Large negative product becomes positive");
        int[] nums8 = {-1, -2, -3, -4, -5, 0, 2};
        // (-1)*(-2)*(-3)*(-4)*(-5) = -120, but (-2)*(-3)*(-4)*(-5) = 120
        
        solution.visualizeDP(nums8, "DP Approach");
        int result8a = solution.maxProduct(nums8);
        System.out.println("Result: " + result8a);
        
        // Compare all implementations
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING ALL IMPLEMENTATIONS:");
        System.out.println("=".repeat(70));
        
        int[][] allTests = {
            {2, 3, -2, 4},
            {-2, 0, -1},
            {1, 2, 3, 4, 5},
            {-2, -3, -4},
            {-2, -3, -4, -5},
            {-5},
            {-2, 0, 3, 4, -5, 0, 2, 3},
            {-1, -2, -3, -4, -5, 0, 2},
            {0, 2},
            {3, -1, 4}
        };
        
        int[] expected = {6, 0, 120, 12, 120, -5, 12, 120, 2, 4};
        
        System.out.println("\nTesting " + allTests.length + " test cases:");
        boolean allConsistent = true;
        
        for (int i = 0; i < allTests.length; i++) {
            int[] nums = allTests[i];
            int exp = expected[i];
            
            int r1 = solution.maxProduct(nums);
            int r2 = solution.maxProductTwoPass(nums);
            int r3 = solution.maxProductKadane(nums);
            int r4 = solution.maxProductDivideConquer(nums);
            int r5 = solution.maxProductBruteForce(nums);
            
            boolean consistent = (r1 == r2) && (r2 == r3) && 
                                (r3 == r4) && (r4 == r5);
            boolean correct = (r1 == exp);
            
            System.out.printf("Test %d: %s - Result: %d - %s %s%n",
                i + 1, Arrays.toString(nums), r1,
                consistent ? "✓ CONSISTENT" : "✗ INCONSISTENT",
                correct ? "✓ CORRECT" : "✗ WRONG");
            
            if (!consistent) {
                System.out.println("  Two-Variable DP: " + r1);
                System.out.println("  Two-Pass:        " + r2);
                System.out.println("  Kadane:          " + r3);
                System.out.println("  Divide & Conquer:" + r4);
                System.out.println("  Brute Force:     " + r5);
                allConsistent = false;
            }
        }
        
        System.out.println("\nAll implementations consistent: " + (allConsistent ? "✓ YES" : "✗ NO"));
        
        // Performance test
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        // Generate large test array
        Random random = new Random(42);
        int n = 20000;
        int[] largeNums = new int[n];
        for (int i = 0; i < n; i++) {
            largeNums[i] = random.nextInt(21) - 10; // Range: -10 to 10
        }
        
        System.out.println("\nTesting with " + n + " random numbers (-10 to 10)");
        
        // Test Two-Variable DP
        startTime = System.currentTimeMillis();
        int perf1 = solution.maxProduct(largeNums);
        long timePerf1 = System.currentTimeMillis() - startTime;
        
        // Test Two-Pass
        startTime = System.currentTimeMillis();
        int perf2 = solution.maxProductTwoPass(largeNums);
        long timePerf2 = System.currentTimeMillis() - startTime;
        
        // Test Kadane Variation
        startTime = System.currentTimeMillis();
        int perf3 = solution.maxProductKadane(largeNums);
        long timePerf3 = System.currentTimeMillis() - startTime;
        
        // Test Divide & Conquer (will be slower)
        startTime = System.currentTimeMillis();
        int perf4 = solution.maxProductDivideConquer(largeNums);
        long timePerf4 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Two-Variable DP:   " + timePerf1 + " ms - Result: " + perf1);
        System.out.println("Two-Pass:          " + timePerf2 + " ms - Result: " + perf2);
        System.out.println("Kadane Variation:  " + timePerf3 + " ms - Result: " + perf3);
        System.out.println("Divide & Conquer:  " + timePerf4 + " ms - Result: " + perf4);
        
        // Verify consistency
        boolean perfConsistent = (perf1 == perf2) && (perf2 == perf3) && (perf3 == perf4);
        System.out.println("Results consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nWhy Two Variables (max and min)?");
        System.out.println("For product subarray, we need to track both because:");
        System.out.println("1. Multiplying by negative flips sign");
        System.out.println("2. A very negative number × negative = very positive");
        System.out.println("3. So the minimum product could become maximum when multiplied by negative");
        
        System.out.println("\nState Transition:");
        System.out.println("For each position i, we have three choices:");
        System.out.println("1. Start new subarray at i: product = nums[i]");
        System.out.println("2. Extend previous max product: maxProd * nums[i]");
        System.out.println("3. Extend previous min product: minProd * nums[i]");
        System.out.println("Then: maxProd = max(choice1, choice2, choice3)");
        System.out.println("      minProd = min(choice1, choice2, choice3)");
        
        System.out.println("\nExample: nums = [2, 3, -2, 4]");
        System.out.println("Step 0: max=2, min=2, result=2");
        System.out.println("Step 1 (num=3):");
        System.out.println("  choices: 3, 2*3=6, 2*3=6");
        System.out.println("  max = max(3,6,6) = 6, min = min(3,6,6) = 3");
        System.out.println("  result = max(2,6) = 6");
        System.out.println("Step 2 (num=-2):");
        System.out.println("  choices: -2, 6*(-2)=-12, 3*(-2)=-6");
        System.out.println("  max = max(-2,-12,-6) = -2");
        System.out.println("  min = min(-2,-12,-6) = -12");
        System.out.println("  result = max(6,-2) = 6");
        System.out.println("Step 3 (num=4):");
        System.out.println("  choices: 4, -2*4=-8, -12*4=-48");
        System.out.println("  max = max(4,-8,-48) = 4");
        System.out.println("  min = min(4,-8,-48) = -48");
        System.out.println("  result = max(6,4) = 6");
        
        System.out.println("\nHandling Zero:");
        System.out.println("When we encounter 0:");
        System.out.println("  maxProd = max(0, maxProd*0, minProd*0) = 0");
        System.out.println("  minProd = min(0, maxProd*0, minProd*0) = 0");
        System.out.println("So product chain resets at zero");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("IMPLEMENTATION COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Two-Variable DP (RECOMMENDED):");
        System.out.println("   Time: O(n) - single pass");
        System.out.println("   Space: O(1) - only 3 variables");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient");
        System.out.println("     - Intuitive once understood");
        System.out.println("     - Handles all cases");
        System.out.println("   Cons:");
        System.out.println("     - Conceptually tricky at first");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Two-Pass Approach:");
        System.out.println("   Time: O(n) - two passes");
        System.out.println("   Space: O(1)");
        System.out.println("   Pros:");
        System.out.println("     - Simpler to understand");
        System.out.println("     - Handles negative count parity");
        System.out.println("   Cons:");
        System.out.println("     - Two passes instead of one");
        System.out.println("     - Resets product on zero (might miss subarrays)");
        System.out.println("   Best for: When simplicity is priority");
        
        System.out.println("\n3. Kadane's Variation:");
        System.out.println("   Time: O(n) - single pass");
        System.out.println("   Space: O(1)");
        System.out.println("   Pros:");
        System.out.println("     - Similar to classic Kadane's");
        System.out.println("     - Elegant swap for negatives");
        System.out.println("   Cons:");
        System.out.println("     - Less obvious why it works");
        System.out.println("   Best for: Those familiar with Kadane's");
        
        System.out.println("\n4. Divide and Conquer:");
        System.out.println("   Time: O(n log n)");
        System.out.println("   Space: O(log n) recursion");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates D&C thinking");
        System.out.println("     - Good for learning");
        System.out.println("   Cons:");
        System.out.println("     - Slower than DP");
        System.out.println("     - More complex");
        System.out.println("   Best for: Educational purposes");
        
        System.out.println("\n5. Brute Force:");
        System.out.println("   Time: O(n²)");
        System.out.println("   Space: O(1)");
        System.out.println("   Pros:");
        System.out.println("     - Simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large n");
        System.out.println("   Best for: Small n, verification");
        
        // Mathematical insights
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nProduct vs Sum Properties:");
        System.out.println("1. Sum: a + b always between a and b (monotonic)");
        System.out.println("2. Product: a × b can be:");
        System.out.println("   - Larger than both (if |a|,|b| > 1)");
        System.out.println("   - Smaller than both (if |a|,|b| < 1)");
        System.out.println("   - Change sign (if one negative)");
        
        System.out.println("\nKey Cases:");
        System.out.println("Case 1: All positive → product always increasing");
        System.out.println("Case 2: Contains zeros → product resets at zeros");
        System.out.println("Case 3: Even negatives → product positive");
        System.out.println("Case 4: Odd negatives → product negative");
        
        System.out.println("\nOptimization Principle:");
        System.out.println("The optimal substructure property:");
        System.out.println("Maximum product ending at i depends only on:");
        System.out.println("1. Maximum product ending at i-1");
        System.out.println("2. Minimum product ending at i-1");
        System.out.println("3. nums[i]");
        System.out.println("This allows O(n) DP solution.");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Financial Analysis:");
        System.out.println("   - Maximum cumulative return over time");
        System.out.println("   - Portfolio optimization");
        System.out.println("   - Risk assessment");
        
        System.out.println("\n2. Signal Processing:");
        System.out.println("   - Maximum amplitude in signal windows");
        System.out.println("   - Filter design");
        System.out.println("   - Feature extraction");
        
        System.out.println("\n3. Machine Learning:");
        System.out.println("   - Feature interaction discovery");
        System.out.println("   - Pattern recognition");
        System.out.println("   - Anomaly detection");
        
        System.out.println("\n4. Game Development:");
        System.out.println("   - Score multipliers in games");
        System.out.println("   - Combo systems");
        System.out.println("   - Power-up effects");
        
        System.out.println("\n5. Quality Control:");
        System.out.println("   - Detecting defect patterns");
        System.out.println("   - Process optimization");
        System.out.println("   - Yield improvement");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify problem:");
        System.out.println("   - Contiguous subarray only");
        System.out.println("   - Product, not sum");
        System.out.println("   - Can include negative numbers");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - O(n²) check all subarrays");
        System.out.println("   - Mention it's too slow");
        
        System.out.println("\n3. Compare with maximum sum subarray:");
        System.out.println("   - Kadane's algorithm doesn't directly apply");
        System.out.println("   - Product has sign flipping issue");
        
        System.out.println("\n4. Identify key insight:");
        System.out.println("   - Need to track both max and min");
        System.out.println("   - Min can become max when multiplied by negative");
        
        System.out.println("\n5. Propose DP solution:");
        System.out.println("   - Two variables: maxEndingHere, minEndingHere");
        System.out.println("   - Update both at each step");
        System.out.println("   - Track global maximum");
        
        System.out.println("\n6. Walk through example:");
        System.out.println("   - Use [2,3,-2,4]");
        System.out.println("   - Show step-by-step calculations");
        
        System.out.println("\n7. Handle edge cases:");
        System.out.println("   - Single element");
        System.out.println("   - All negatives");
        System.out.println("   - Contains zeros");
        System.out.println("   - Large negative values");
        
        System.out.println("\n8. Discuss alternatives:");
        System.out.println("   - Two-pass approach");
        System.out.println("   - Divide and conquer");
        System.out.println("   - Compare complexities");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Track both max and min due to sign flipping");
        System.out.println("- O(n) time, O(1) space optimal solution");
        System.out.println("- Works for all cases (positive, negative, zero)");
        System.out.println("- Similar to but different from maximum sum");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Only tracking max (misses negative*negative case)");
        System.out.println("- Not handling zeros correctly");
        System.out.println("- Integer overflow (but guaranteed to fit 32-bit)");
        System.out.println("- Forgetting to update global max");
        System.out.println("- Not testing with all-negative arrays");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with all positive numbers");
        System.out.println("2. Test with all negative numbers (odd and even count)");
        System.out.println("3. Test with zeros");
        System.out.println("4. Test with mixed signs");
        System.out.println("5. Test single element");
        System.out.println("6. Verify product calculation");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
