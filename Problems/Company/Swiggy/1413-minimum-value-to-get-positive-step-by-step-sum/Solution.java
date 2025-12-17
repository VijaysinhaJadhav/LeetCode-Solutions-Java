
# Solution.java

```java
import java.util.*;

/**
 * 1413. Minimum Value to Get Positive Step by Step Sum
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given an array of integers nums, find the minimum positive startValue such that 
 * the step-by-step sum (startValue + prefix sum) never drops below 1.
 * 
 * Key Insights:
 * 1. Track the minimum prefix sum starting from 0
 * 2. The required startValue = max(1, 1 - minPrefixSum)
 * 3. If minPrefixSum is already >= 1, we only need startValue = 1
 * 4. The starting value must always be positive (>= 1)
 * 
 * Approach (One-pass with Prefix Sum):
 * 1. Initialize runningSum = 0 and minSum = 0
 * 2. For each number in nums:
 *    - Add to runningSum
 *    - Update minSum = min(minSum, runningSum)
 * 3. Return max(1, 1 - minSum)
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Prefix Sum
 */

class Solution {
    
    /**
     * Approach 1: One-pass with Prefix Sum Tracking (RECOMMENDED)
     * O(n) time, O(1) space
     */
    public int minStartValue(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1; // By default, minimum start value is 1
        }
        
        int runningSum = 0;
        int minSum = 0; // Track minimum prefix sum
        
        for (int num : nums) {
            runningSum += num;
            minSum = Math.min(minSum, runningSum);
        }
        
        // The required startValue should make the minimum sum at least 1
        // If minSum = -4, we need startValue = 5 (1 - (-4) = 5)
        // If minSum >= 1, we only need startValue = 1
        return Math.max(1, 1 - minSum);
    }
    
    /**
     * Approach 2: Binary Search
     * O(n log M) time, O(1) space where M is the search range
     * Demonstrates binary search approach for educational purposes
     */
    public int minStartValueBinarySearch(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }
        
        // Find reasonable bounds for binary search
        // Minimum possible: 1 (positive start value)
        // Maximum possible: 1 - min possible prefix sum
        // Worst case: all -100, length 100 -> need 100*100 + 1 = 10001
        int left = 1;
        int right = 10001; // Upper bound: 100*100 + 1
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (isValidStartValue(nums, mid)) {
                // Try smaller value
                right = mid;
            } else {
                // Need larger value
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private boolean isValidStartValue(int[] nums, int startValue) {
        int sum = startValue;
        for (int num : nums) {
            sum += num;
            if (sum < 1) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Approach 3: Simulation with Early Exit
     * O(n) time, O(1) space
     * Simulates the process directly
     */
    public int minStartValueSimulation(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }
        
        int startValue = 1;
        
        while (true) {
            int sum = startValue;
            boolean valid = true;
            
            for (int num : nums) {
                sum += num;
                if (sum < 1) {
                    valid = false;
                    break;
                }
            }
            
            if (valid) {
                return startValue;
            }
            
            startValue++;
        }
    }
    
    /**
     * Approach 4: Mathematical Formula
     * O(n) time, O(1) space
     * Direct formula: startValue = max(1, 1 - min(0, prefix sums))
     */
    public int minStartValueFormula(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }
        
        // Calculate minimum prefix sum
        int prefixSum = 0;
        int minPrefixSum = 0;
        
        for (int num : nums) {
            prefixSum += num;
            if (prefixSum < minPrefixSum) {
                minPrefixSum = prefixSum;
            }
        }
        
        // If minPrefixSum >= 0, we need at least 1
        // If minPrefixSum < 0, we need 1 - minPrefixSum
        return Math.max(1, 1 - minPrefixSum);
    }
    
    /**
     * Approach 5: Prefix Sum with Visualization
     * O(n) time, O(1) space
     * Includes step-by-step visualization
     */
    public int minStartValueVisual(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }
        
        System.out.println("\nArray: " + Arrays.toString(nums));
        System.out.println("\nStep-by-step calculation:");
        System.out.println("Index | Value | Running Sum | Min Sum");
        System.out.println("------|-------|-------------|--------");
        
        int runningSum = 0;
        int minSum = 0;
        
        for (int i = 0; i < nums.length; i++) {
            runningSum += nums[i];
            minSum = Math.min(minSum, runningSum);
            
            System.out.printf("  %-3d |  %4d |     %4d    |   %4d%n", 
                i, nums[i], runningSum, minSum);
        }
        
        int result = Math.max(1, 1 - minSum);
        System.out.println("\nMinimum prefix sum: " + minSum);
        System.out.println("Required start value: max(1, 1 - " + minSum + ") = " + result);
        
        return result;
    }
    
    /**
     * Helper method to validate and explain the solution
     */
    private void validateAndExplain(int[] nums, int startValue) {
        System.out.println("\nValidation for startValue = " + startValue + ":");
        System.out.println("Step | Value | Current Sum | Check (>=1)");
        System.out.println("-----|-------|-------------|------------");
        
        int sum = startValue;
        boolean allValid = true;
        
        System.out.printf("  %-3d | Start |     %4d    |    %s%n", 
            0, sum, sum >= 1 ? "✓" : "✗");
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            boolean valid = sum >= 1;
            allValid = allValid && valid;
            
            System.out.printf("  %-3d |  %4d |     %4d    |    %s%n", 
                i + 1, nums[i], sum, valid ? "✓" : "✗");
        }
        
        System.out.println("\nAll steps valid: " + (allValid ? "✓ YES" : "✗ NO"));
        
        if (allValid) {
            System.out.println("✓ startValue = " + startValue + " works!");
        } else {
            System.out.println("✗ startValue = " + startValue + " fails!");
        }
    }
    
    /**
     * Helper method to find the actual step-by-step sums with given start value
     */
    private void showStepByStepSums(int[] nums, int startValue) {
        System.out.println("\nStep-by-step sums with startValue = " + startValue + ":");
        int sum = startValue;
        System.out.println("Initial: " + sum);
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            System.out.println("Step " + (i + 1) + ": " + sum + 
                             " (previous + " + nums[i] + ")");
        }
        
        // Check if all sums >= 1
        sum = startValue;
        boolean allValid = true;
        List<Integer> problematicSteps = new ArrayList<>();
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum < 1) {
                allValid = false;
                problematicSteps.add(i + 1);
            }
        }
        
        if (allValid) {
            System.out.println("✓ All sums are ≥ 1");
        } else {
            System.out.println("✗ Problematic steps: " + problematicSteps);
        }
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Minimum Value to Get Positive Step by Step Sum:");
        System.out.println("========================================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Example from problem");
        int[] nums1 = {-3, 2, -3, 4, 2};
        int expected1 = 5;
        
        solution.minStartValueVisual(nums1);
        
        long startTime = System.nanoTime();
        int result1a = solution.minStartValue(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.minStartValueBinarySearch(nums1);
        long time1b = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Prefix Sum Method: " + result1a + " - " + 
                         (result1a == expected1 ? "✓ PASSED" : "✗ FAILED") + 
                         " (Time: " + time1a + " ns)");
        System.out.println("Binary Search:     " + result1b + " - " + 
                         (result1b == expected1 ? "✓ PASSED" : "✗ FAILED") + 
                         " (Time: " + time1b + " ns)");
        
        // Validate the solution
        System.out.println("\n" + "=".repeat(60));
        System.out.println("VALIDATION FOR TEST 1:");
        System.out.println("=".repeat(60));
        solution.validateAndExplain(nums1, 4); // Should fail
        solution.validateAndExplain(nums1, 5); // Should pass
        solution.validateAndExplain(nums1, 6); // Should pass but not minimum
        
        // Test case 2: Simple case
        System.out.println("\n\nTest 2: Simple positive array");
        int[] nums2 = {1, 2};
        int expected2 = 1;
        
        solution.minStartValueVisual(nums2);
        int result2 = solution.minStartValue(nums2);
        System.out.println("Result: " + result2 + " - " + 
                         (result2 == expected2 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 3: Another example
        System.out.println("\n\nTest 3: Mixed positive and negative");
        int[] nums3 = {1, -2, -3};
        int expected3 = 5;
        
        solution.minStartValueVisual(nums3);
        int result3 = solution.minStartValue(nums3);
        System.out.println("Result: " + result3 + " - " + 
                         (result3 == expected3 ? "✓ PASSED" : "✗ FAILED"));
        
        // Show step-by-step with the solution
        solution.showStepByStepSums(nums3, 4); // Should fail
        solution.showStepByStepSums(nums3, 5); // Should work
        
        // Test case 4: All positive
        System.out.println("\n\nTest 4: All positive numbers");
        int[] nums4 = {1, 2, 3, 4, 5};
        int expected4 = 1;
        
        solution.minStartValueVisual(nums4);
        int result4 = solution.minStartValue(nums4);
        System.out.println("Result: " + result4 + " - " + 
                         (result4 == expected4 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 5: All negative
        System.out.println("\n\nTest 5: All negative numbers");
        int[] nums5 = {-1, -2, -3, -4};
        // Prefix sums: -1, -3, -6, -10
        // Min prefix sum = -10
        // Required: 1 - (-10) = 11
        int expected5 = 11;
        
        solution.minStartValueVisual(nums5);
        int result5 = solution.minStartValue(nums5);
        System.out.println("Result: " + result5 + " - " + 
                         (result5 == expected5 ? "✓ PASSED" : "✗ FAILED"));
        
        // Show step-by-step
        solution.showStepByStepSums(nums5, 10); // Should fail
        solution.showStepByStepSums(nums5, 11); // Should work
        
        // Test case 6: Single element
        System.out.println("\n\nTest 6: Single element");
        int[] nums6 = {-5};
        int expected6 = 6; // 1 - (-5) = 6
        
        int result6 = solution.minStartValue(nums6);
        System.out.println("Array: " + Arrays.toString(nums6));
        System.out.println("Result: " + result6 + " - " + 
                         (result6 == expected6 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 7: Edge case - minimum value
        System.out.println("\n\nTest 7: Large negative at the end");
        int[] nums7 = {2, 3, 5, -10, 4};
        // Prefix sums: 2, 5, 10, 0, 4
        // Min prefix sum = 0
        // Required: max(1, 1-0) = 1
        int expected7 = 1;
        
        solution.minStartValueVisual(nums7);
        int result7 = solution.minStartValue(nums7);
        System.out.println("Result: " + result7 + " - " + 
                         (result7 == expected7 ? "✓ PASSED" : "✗ FAILED"));
        
        // Compare all implementations
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING ALL IMPLEMENTATIONS:");
        System.out.println("=".repeat(70));
        
        int[][] allTests = {
            {-3, 2, -3, 4, 2},
            {1, 2},
            {1, -2, -3},
            {1, 2, 3, 4, 5},
            {-1, -2, -3, -4},
            {-5},
            {2, 3, 5, -10, 4},
            {0, 0, 0, 0},
            {10, -20, 5, -3},
            {-10, 5, -3, 2}
        };
        
        int[] expected = {5, 1, 5, 1, 11, 6, 1, 1, 11, 6};
        
        System.out.println("\nTesting " + allTests.length + " test cases:");
        boolean allConsistent = true;
        
        for (int i = 0; i < allTests.length; i++) {
            int[] nums = allTests[i];
            int exp = expected[i];
            
            int r1 = solution.minStartValue(nums);
            int r2 = solution.minStartValueBinarySearch(nums);
            int r3 = solution.minStartValueSimulation(nums);
            int r4 = solution.minStartValueFormula(nums);
            
            boolean consistent = (r1 == r2) && (r2 == r3) && (r3 == r4);
            boolean correct = (r1 == exp);
            
            System.out.printf("Test %d: %s - Result: %d - %s %s%n",
                i + 1, Arrays.toString(nums), r1,
                consistent ? "✓ CONSISTENT" : "✗ INCONSISTENT",
                correct ? "✓ CORRECT" : "✗ WRONG");
            
            if (!consistent) {
                System.out.println("  Prefix Sum:      " + r1);
                System.out.println("  Binary Search:   " + r2);
                System.out.println("  Simulation:      " + r3);
                System.out.println("  Formula:         " + r4);
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
        int n = 10000;
        int[] largeNums = new int[n];
        for (int i = 0; i < n; i++) {
            largeNums[i] = random.nextInt(201) - 100; // Range: -100 to 100
        }
        
        System.out.println("\nTesting with " + n + " random numbers (-100 to 100)");
        
        // Test Prefix Sum method
        startTime = System.currentTimeMillis();
        int perf1 = solution.minStartValue(largeNums);
        long timePerf1 = System.currentTimeMillis() - startTime;
        
        // Test Binary Search
        startTime = System.currentTimeMillis();
        int perf2 = solution.minStartValueBinarySearch(largeNums);
        long timePerf2 = System.currentTimeMillis() - startTime;
        
        // Test Simulation (will be slow for worst case)
        startTime = System.currentTimeMillis();
        int perf3 = solution.minStartValueSimulation(largeNums);
        long timePerf3 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Prefix Sum Method: " + timePerf1 + " ms - Result: " + perf1);
        System.out.println("Binary Search:     " + timePerf2 + " ms - Result: " + perf2);
        System.out.println("Simulation:        " + timePerf3 + " ms - Result: " + perf3);
        
        // Verify consistency
        boolean perfConsistent = (perf1 == perf2) && (perf2 == perf3);
        System.out.println("Results consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nMathematical Derivation:");
        System.out.println("Let startValue = x");
        System.out.println("After processing nums[0..i], sum = x + prefixSum[i]");
        System.out.println("We need: x + prefixSum[i] ≥ 1 for all i");
        System.out.println("This means: x ≥ 1 - prefixSum[i] for all i");
        System.out.println("So: x ≥ max(1 - prefixSum[0], 1 - prefixSum[1], ..., 1 - prefixSum[n-1])");
        System.out.println("Since x must be positive: x = max(1, 1 - minPrefixSum)");
        
        System.out.println("\nExample Walkthrough:");
        System.out.println("nums = [-3, 2, -3, 4, 2]");
        System.out.println("prefixSums: -3, -1, -4, 0, 2");
        System.out.println("minPrefixSum = -4");
        System.out.println("x = max(1, 1 - (-4)) = max(1, 5) = 5");
        
        System.out.println("\nVisual Proof:");
        System.out.println("With x = 5:");
        System.out.println("Step 0: 5");
        System.out.println("Step 1: 5 + (-3) = 2 ≥ 1 ✓");
        System.out.println("Step 2: 2 + 2 = 4 ≥ 1 ✓");
        System.out.println("Step 3: 4 + (-3) = 1 ≥ 1 ✓");
        System.out.println("Step 4: 1 + 4 = 5 ≥ 1 ✓");
        System.out.println("Step 5: 5 + 2 = 7 ≥ 1 ✓");
        
        // Edge cases explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. All Positive Numbers:");
        System.out.println("   nums = [1, 2, 3]");
        System.out.println("   prefixSums: 1, 3, 6");
        System.out.println("   minPrefixSum = 1");
        System.out.println("   x = max(1, 1 - 1) = max(1, 0) = 1");
        
        System.out.println("\n2. All Negative Numbers:");
        System.out.println("   nums = [-1, -2, -3]");
        System.out.println("   prefixSums: -1, -3, -6");
        System.out.println("   minPrefixSum = -6");
        System.out.println("   x = max(1, 1 - (-6)) = max(1, 7) = 7");
        
        System.out.println("\n3. Mixed with Large Negative at End:");
        System.out.println("   nums = [10, 20, -100]");
        System.out.println("   prefixSums: 10, 30, -70");
        System.out.println("   minPrefixSum = -70");
        System.out.println("   x = max(1, 1 - (-70)) = max(1, 71) = 71");
        
        System.out.println("\n4. Zeros Only:");
        System.out.println("   nums = [0, 0, 0]");
        System.out.println("   prefixSums: 0, 0, 0");
        System.out.println("   minPrefixSum = 0");
        System.out.println("   x = max(1, 1 - 0) = max(1, 1) = 1");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Financial Planning:");
        System.out.println("   - Minimum initial capital to avoid bankruptcy");
        System.out.println("   - Cash flow management with varying income/expenses");
        
        System.out.println("\n2. Game Development:");
        System.out.println("   - Minimum starting health to survive level");
        System.out.println("   - Resource management in strategy games");
        
        System.out.println("\n3. Supply Chain Management:");
        System.out.println("   - Minimum initial inventory to avoid stockouts");
        System.out.println("   - Buffer stock calculation");
        
        System.out.println("\n4. Project Management:");
        System.out.println("   - Minimum starting resources for project");
        System.out.println("   - Resource allocation with varying demands");
        
        System.out.println("\n5. Battery Management:");
        System.out.println("   - Minimum starting charge for device");
        System.out.println("   - Power consumption with varying loads");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - Need minimum positive start value");
        System.out.println("   - Running sum must never drop below 1");
        System.out.println("   - Process array sequentially");
        
        System.out.println("\n2. Try simple examples:");
        System.out.println("   - Work through given examples manually");
        System.out.println("   - Look for patterns");
        
        System.out.println("\n3. Identify key insight:");
        System.out.println("   - Track minimum prefix sum");
        System.out.println("   - Start value compensates for most negative dip");
        
        System.out.println("\n4. Formulate solution:");
        System.out.println("   - One pass to find min prefix sum");
        System.out.println("   - Return max(1, 1 - minPrefixSum)");
        
        System.out.println("\n5. Walk through example:");
        System.out.println("   - Show calculations step by step");
        System.out.println("   - Explain why formula works");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - All positive numbers");
        System.out.println("   - All negative numbers");
        System.out.println("   - Single element");
        System.out.println("   - Zeros");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- O(n) time, O(1) space optimal solution");
        System.out.println("- Simple mathematical derivation");
        System.out.println("- Elegant one-pass algorithm");
        System.out.println("- Handles all edge cases correctly");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Starting with brute force simulation");
        System.out.println("- Not considering the minimum must be positive");
        System.out.println("- Forgetting to handle all-negative case");
        System.out.println("- Overcomplicating with binary search unnecessarily");
        
        System.out.println("\nFollow-up Questions to Consider:");
        System.out.println("1. What if we could change the order of processing?");
        System.out.println("2. What if we need sum to stay above k instead of 1?");
        System.out.println("3. What if we can skip some elements?");
        System.out.println("4. What if we need to find the actual sequence of sums?");
        
        System.out.println("\nAlternative Approaches to Mention:");
        System.out.println("1. Binary search (O(n log M)) - good for understanding");
        System.out.println("2. Simulation (O(n * answer)) - brute force");
        System.out.println("3. Mathematical formula (O(n)) - optimal");
        
        // Advanced variations
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ADVANCED VARIATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nVariation 1: Minimum k such that sum never drops below k");
        System.out.println("   Change 1 to k in the formula: max(k, k - minPrefixSum)");
        
        System.out.println("\nVariation 2: Find the actual minimum sum at each step");
        System.out.println("   Return the sequence of minimum sums encountered");
        
        System.out.println("\nVariation 3: Allow negative starting value");
        System.out.println("   Remove the max(1, ...) condition");
        
        System.out.println("\nVariation 4: Circular array");
        System.out.println("   Need to consider all starting positions");
        
        System.out.println("\nVariation 5: With insert/delete operations");
        System.out.println("   Use segment tree to maintain prefix sums");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
