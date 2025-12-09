
## Problems/Arrays-Hashing/1863-sum-of-all-subset-xor-totals/Solution.java

```java
/**
 * 1863. Sum of All Subset XOR Totals
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * The XOR total of an array is defined as the bitwise XOR of all its elements, or 0 if empty.
 * Given an array nums, return the sum of all XOR totals for every subset of nums.
 * 
 * Key Insights:
 * 1. Each element appears in exactly 2^(n-1) subsets
 * 2. The total sum is (OR of all numbers) * 2^(n-1)
 * 3. Each bit contributes independently to the final sum
 * 4. For bit i, if k numbers have this bit set, then 2^(n-1) subsets have this bit in XOR
 * 
 * Approach (Mathematical Bit Analysis):
 * 1. Compute the bitwise OR of all numbers
 * 2. Multiply by 2^(n-1) where n = nums.length
 * 3. This gives the sum of all subset XOR totals
 * 
 * Time Complexity: O(n) - Single pass through array
 * Space Complexity: O(1) - Constant extra space
 * 
 * Tags: Array, Math, Backtracking, Bit Manipulation, Combinatorics
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Mathematical Bit Analysis - RECOMMENDED
     * O(n) time, O(1) space - Most efficient
     */
    public int subsetXORSum(int[] nums) {
        int n = nums.length;
        int bitwiseOR = 0;
        
        // Compute bitwise OR of all numbers
        for (int num : nums) {
            bitwiseOR |= num;
        }
        
        // Each bit that is set in bitwiseOR appears in 2^(n-1) subsets
        return bitwiseOR * (1 << (n - 1));
    }
    
    /**
     * Approach 2: Backtracking (DFS)
     * O(2^n) time, O(n) space - Generate all subsets
     */
    public int subsetXORSumBacktracking(int[] nums) {
        return backtrack(nums, 0, 0);
    }
    
    private int backtrack(int[] nums, int index, int currentXOR) {
        if (index == nums.length) {
            return currentXOR;
        }
        
        // Include current element
        int withElement = backtrack(nums, index + 1, currentXOR ^ nums[index]);
        
        // Exclude current element
        int withoutElement = backtrack(nums, index + 1, currentXOR);
        
        return withElement + withoutElement;
    }
    
    /**
     * Approach 3: Bit Mask Enumeration
     * O(2^n * n) time, O(1) space - Enumerate all subsets using bitmask
     */
    public int subsetXORSumBitmask(int[] nums) {
        int n = nums.length;
        int total = 0;
        int subsetCount = 1 << n; // 2^n subsets
        
        // Iterate through all possible subsets
        for (int mask = 0; mask < subsetCount; mask++) {
            int xor = 0;
            // Compute XOR for current subset
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    xor ^= nums[i];
                }
            }
            total += xor;
        }
        
        return total;
    }
    
    /**
     * Approach 4: Iterative DP
     * O(2^n) time, O(2^n) space - Dynamic programming approach
     */
    public int subsetXORSumDP(int[] nums) {
        // This approach builds the solution iteratively
        // Not the most efficient but demonstrates DP thinking
        
        int total = 0;
        List<Integer> dp = new ArrayList<>();
        dp.add(0); // Start with empty subset (XOR = 0)
        
        for (int num : nums) {
            int size = dp.size();
            for (int i = 0; i < size; i++) {
                int newXOR = dp.get(i) ^ num;
                dp.add(newXOR);
                total += newXOR;
            }
        }
        
        return total;
    }
    
    /**
     * Approach 5: Mathematical - Bit by Bit Contribution
     * O(n) time, O(1) space - Alternative mathematical approach
     */
    public int subsetXORSumBitContribution(int[] nums) {
        int n = nums.length;
        int total = 0;
        
        // Consider each bit position independently
        for (int bit = 0; bit < 32; bit++) {
            int count = 0;
            
            // Count how many numbers have this bit set
            for (int num : nums) {
                if ((num & (1 << bit)) != 0) {
                    count++;
                }
            }
            
            // If count > 0, this bit contributes to the sum
            if (count > 0) {
                // Number of subsets where this bit appears in XOR result
                // is 2^(n-1) when count >= 1
                total += (1 << bit) * (1 << (n - 1));
            }
        }
        
        return total;
    }
    
    /**
     * Approach 6: Optimized Backtracking with Memoization
     * O(2^n) time, O(2^n) space - For educational purposes
     */
    public int subsetXORSumMemo(int[] nums) {
        // Since n <= 12, memoization is feasible but not necessary
        // This demonstrates the concept
        return backtrackMemo(nums, 0, 0, new HashMap<>());
    }
    
    private int backtrackMemo(int[] nums, int index, int currentXOR, Map<String, Integer> memo) {
        if (index == nums.length) {
            return currentXOR;
        }
        
        String key = index + "," + currentXOR;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        int withElement = backtrackMemo(nums, index + 1, currentXOR ^ nums[index], memo);
        int withoutElement = backtrackMemo(nums, index + 1, currentXOR, memo);
        
        int result = withElement + withoutElement;
        memo.put(key, result);
        return result;
    }
    
    /**
     * Helper method to visualize all subsets and their XOR values
     */
    private void visualizeSubsets(int[] nums) {
        System.out.println("\nSubset XOR Visualization:");
        System.out.println("Input: " + Arrays.toString(nums));
        
        int n = nums.length;
        int subsetCount = 1 << n;
        int total = 0;
        
        System.out.println("\nAll Subsets and Their XOR Values:");
        System.out.println("Subset | Elements | XOR Value");
        System.out.println("-------|----------|----------");
        
        for (int mask = 0; mask < subsetCount; mask++) {
            List<Integer> subset = new ArrayList<>();
            int xor = 0;
            
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                    xor ^= nums[i];
                }
            }
            
            total += xor;
            System.out.printf("%6d | %8s | %9d%n", 
                            mask, subset.toString(), xor);
        }
        
        System.out.println("Total sum: " + total);
        
        // Show mathematical calculation
        System.out.println("\nMathematical Calculation:");
        int bitwiseOR = 0;
        for (int num : nums) {
            bitwiseOR |= num;
        }
        System.out.println("Bitwise OR of all numbers: " + Integer.toBinaryString(bitwiseOR));
        System.out.println("2^(n-1) = 2^(" + n + "-1) = " + (1 << (n - 1)));
        System.out.println("Total = " + bitwiseOR + " * " + (1 << (n - 1)) + " = " + (bitwiseOR * (1 << (n - 1))));
    }
    
    /**
     * Helper method to show bit contribution analysis
     */
    private void analyzeBitContribution(int[] nums) {
        System.out.println("\nBit Contribution Analysis:");
        System.out.println("Number | Binary");
        System.out.println("-------|--------");
        
        for (int num : nums) {
            System.out.printf("%6d | %s%n", num, 
                            String.format("%5s", Integer.toBinaryString(num)).replace(' ', '0'));
        }
        
        int n = nums.length;
        System.out.println("\nBit Position Analysis:");
        System.out.println("Bit | Count Set | Contribution");
        System.out.println("----|-----------|-------------");
        
        int total = 0;
        for (int bit = 0; bit < 32; bit++) {
            int count = 0;
            for (int num : nums) {
                if ((num & (1 << bit)) != 0) {
                    count++;
                }
            }
            
            if (count > 0) {
                int contribution = (1 << bit) * (1 << (n - 1));
                total += contribution;
                System.out.printf("%3d | %9d | %11d%n", bit, count, contribution);
            }
        }
        
        System.out.println("Total: " + total);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Sum of All Subset XOR Totals:");
        System.out.println("======================================");
        
        // Test case 1: Simple example
        System.out.println("\nTest 1: Simple example");
        int[] nums1 = {1, 3};
        int expected1 = 6;
        
        long startTime = System.nanoTime();
        int result1a = solution.subsetXORSum(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.subsetXORSumBacktracking(nums1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.subsetXORSumBitmask(nums1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = (result1a == expected1);
        boolean test1b = (result1b == expected1);
        boolean test1c = (result1c == expected1);
        
        System.out.println("Mathematical: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Backtracking: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Bitmask: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the subsets
        solution.visualizeSubsets(nums1);
        solution.analyzeBitContribution(nums1);
        
        // Test case 2: Three elements
        System.out.println("\nTest 2: Three elements");
        int[] nums2 = {5, 1, 6};
        int expected2 = 28;
        
        int result2a = solution.subsetXORSum(nums2);
        System.out.println("Three elements: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single element
        System.out.println("\nTest 3: Single element");
        int[] nums3 = {7};
        int expected3 = 7;
        
        int result3a = solution.subsetXORSum(nums3);
        System.out.println("Single element: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: All zeros
        System.out.println("\nTest 4: All zeros");
        int[] nums4 = {0, 0, 0};
        int expected4 = 0;
        
        int result4a = solution.subsetXORSum(nums4);
        System.out.println("All zeros: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Maximum constraint
        System.out.println("\nTest 5: Maximum constraint");
        int[] nums5 = {3, 4, 5, 6, 7, 8};
        int result5a = solution.subsetXORSum(nums5);
        System.out.println("Maximum case: " + result5a + " (expected: 480)");
        
        // Test case 6: Powers of two
        System.out.println("\nTest 6: Powers of two");
        int[] nums6 = {1, 2, 4, 8};
        int result6a = solution.subsetXORSum(nums6);
        System.out.println("Powers of two: " + result6a);
        
        // Test case 7: All same numbers
        System.out.println("\nTest 7: All same numbers");
        int[] nums7 = {3, 3, 3, 3};
        int result7a = solution.subsetXORSum(nums7);
        System.out.println("All same: " + result7a);
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison");
        System.out.println("Simple example performance:");
        System.out.println("  Mathematical: " + time1a + " ns");
        System.out.println("  Backtracking: " + time1b + " ns");
        System.out.println("  Bitmask: " + time1c + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 9: Larger input performance");
        int[] nums9 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}; // Maximum size
        startTime = System.nanoTime();
        int result9a = solution.subsetXORSum(nums9);
        long time9a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result9b = solution.subsetXORSumBacktracking(nums9);
        long time9b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result9c = solution.subsetXORSumBitmask(nums9);
        long time9c = System.nanoTime() - startTime;
        
        System.out.println("Large input (12 elements):");
        System.out.println("  Mathematical: " + time9a + " ns, Result: " + result9a);
        System.out.println("  Backtracking: " + time9b + " ns, Result: " + result9b);
        System.out.println("  Bitmask: " + time9c + " ns, Result: " + result9c);
        
        // Compare all approaches for consistency
        System.out.println("\nTest 10: Consistency Check");
        int[] testNums = {2, 4, 6};
        int r1 = solution.subsetXORSum(testNums);
        int r2 = solution.subsetXORSumBacktracking(testNums);
        int r3 = solution.subsetXORSumBitmask(testNums);
        int r4 = solution.subsetXORSumDP(testNums);
        int r5 = solution.subsetXORSumBitContribution(testNums);
        
        System.out.println("All approaches consistent: " + 
                         (r1 == r2 && r1 == r3 && r1 == r4 && r1 == r5));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL BIT ANALYSIS EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Each element appears in exactly 2^(n-1) subsets.");
        System.out.println("Therefore, the sum of all subset XOR totals is:");
        System.out.println("  (bitwise OR of all numbers) * 2^(n-1)");
        
        System.out.println("\nWhy this works:");
        System.out.println("1. XOR is linear and bits are independent");
        System.out.println("2. For each bit position:");
        System.out.println("   - If no number has this bit set: contributes 0");
        System.out.println("   - If at least one number has this bit set:");
        System.out.println("     * The bit appears in exactly 2^(n-1) subset XORs");
        System.out.println("     * Contribution = (2^bit) * 2^(n-1)");
        System.out.println("3. Summing over all bits gives the total");
        
        System.out.println("\nProof Sketch:");
        System.out.println("Consider a single bit position.");
        System.out.println("Let k be the number of elements with this bit set.");
        System.out.println("The number of subsets where XOR has this bit set = 2^(n-1)");
        System.out.println("This is because:");
        System.out.println("  - We can pair subsets that differ only in one element");
        System.out.println("  - Exactly half of these pairs will have the bit set in XOR");
        System.out.println("Thus, total contribution = sum_over_bits(2^bit * 2^(n-1))");
        System.out.println("                      = (OR of all numbers) * 2^(n-1)");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Mathematical Bit Analysis (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Compute bitwise OR of all numbers");
        System.out.println("     - Multiply by 2^(n-1)");
        System.out.println("     - Return the result");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time complexity");
        System.out.println("     - O(1) space complexity");
        System.out.println("     - Elegant mathematical solution");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of combinatorial properties");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Backtracking (DFS):");
        System.out.println("   Time: O(2^n) - Generate all subsets");
        System.out.println("   Space: O(n) - Recursion stack depth");
        System.out.println("   How it works:");
        System.out.println("     - Recursively generate all subsets");
        System.out.println("     - Compute XOR for each subset");
        System.out.println("     - Sum all XOR values");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and straightforward");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Exponential time complexity");
        System.out.println("     - Limited to small n (n ≤ ~20)");
        System.out.println("   Best for: Small inputs, educational purposes");
        
        System.out.println("\n3. Bit Mask Enumeration:");
        System.out.println("   Time: O(2^n * n) - Enumerate all 2^n subsets");
        System.out.println("   Space: O(1) - No extra space needed");
        System.out.println("   How it works:");
        System.out.println("     - Use bitmask to represent subsets");
        System.out.println("     - For each mask, compute subset XOR");
        System.out.println("     - Sum all XOR values");
        System.out.println("   Pros:");
        System.out.println("     - No recursion overhead");
        System.out.println("     - Clear bit manipulation practice");
        System.out.println("   Cons:");
        System.out.println("     - Still exponential time");
        System.out.println("     - Less intuitive than backtracking");
        System.out.println("   Best for: Bit manipulation practice");
        
        System.out.println("\n4. Dynamic Programming:");
        System.out.println("   Time: O(2^n) - Build solution iteratively");
        System.out.println("   Space: O(2^n) - Store intermediate results");
        System.out.println("   How it works:");
        System.out.println("     - Start with empty subset (XOR = 0)");
        System.out.println("     - For each element, extend existing subsets");
        System.out.println("     - Track sum of XOR values");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates DP thinking");
        System.out.println("     - Iterative approach");
        System.out.println("   Cons:");
        System.out.println("     - Exponential time and space");
        System.out.println("     - Overkill for this problem");
        System.out.println("   Best for: Learning DP techniques");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL DERIVATION:");
        System.out.println("Let S be the sum of all subset XOR totals.");
        System.out.println("Let n be the number of elements.");
        System.out.println("For each bit position i:");
        System.out.println("  Let k_i be the number of elements with bit i set.");
        System.out.println("  The number of subsets where XOR has bit i set = 2^(n-1) if k_i ≥ 1");
        System.out.println("  Contribution from bit i = (2^i) * 2^(n-1) if k_i ≥ 1");
        System.out.println("Therefore:");
        System.out.println("  S = Σ [2^i * 2^(n-1) for all i where k_i ≥ 1]");
        System.out.println("    = 2^(n-1) * Σ 2^i for all i where k_i ≥ 1");
        System.out.println("    = 2^(n-1) * (bitwise OR of all numbers)");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Mathematical approach - it's the most efficient");
        System.out.println("2. Explain the combinatorial insight clearly");
        System.out.println("3. Provide the formula: result = (OR of nums) * 2^(n-1)");
        System.out.println("4. Mention alternative approaches (backtracking, bitmask)");
        System.out.println("5. Discuss time/space complexity trade-offs");
        System.out.println("6. Handle edge cases: single element, all zeros");
        System.out.println("7. Verify understanding with examples");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
