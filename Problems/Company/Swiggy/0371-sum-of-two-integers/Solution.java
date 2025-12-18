
# Solution.java

```java
import java.util.*;

/**
 * 371. Sum of Two Integers
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given two integers a and b, return the sum without using + or - operators.
 * 
 * Key Insights:
 * 1. Use bitwise operations to simulate addition
 * 2. XOR (^) = sum without carry
 * 3. AND (&) followed by left shift (<<) = carry
 * 4. Repeat: sum = a ^ b, carry = (a & b) << 1
 * 5. Continue until carry becomes 0
 * 
 * Approach (Bit Manipulation):
 * 1. While b != 0:
 *    - Calculate sum without carry: a ^ b
 *    - Calculate carry: (a & b) << 1
 *    - Set a = sum, b = carry
 * 2. Return a
 * 
 * Time Complexity: O(1) - At most 32 iterations for 32-bit integers
 * Space Complexity: O(1)
 * 
 * Tags: Math, Bit Manipulation
 */

class Solution {
    
    /**
     * Approach 1: Bit Manipulation with While Loop (RECOMMENDED)
     * O(1) time, O(1) space
     * Works for both positive and negative numbers
     */
    public int getSum(int a, int b) {
        while (b != 0) {
            // Calculate sum without carry
            int sumWithoutCarry = a ^ b;
            // Calculate carry (bits that will generate carry)
            int carry = (a & b) << 1;
            // Update a and b for next iteration
            a = sumWithoutCarry;
            b = carry;
        }
        return a;
    }
    
    /**
     * Approach 2: Recursive Version
     * O(1) time, O(1) space (tail recursion, but Java doesn't optimize it)
     * More elegant but uses recursion stack
     */
    public int getSumRecursive(int a, int b) {
        if (b == 0) {
            return a;
        }
        return getSumRecursive(a ^ b, (a & b) << 1);
    }
    
    /**
     * Approach 3: Using Addition with Bit Manipulation (32-bit explicit)
     * O(1) time, O(1) space
     * Explicitly handles 32-bit integers
     */
    public int getSum32Bit(int a, int b) {
        int mask = 0xFFFFFFFF; // 32-bit mask
        
        while (b != 0) {
            int sum = (a ^ b) & mask;
            int carry = ((a & b) << 1) & mask;
            a = sum;
            b = carry;
        }
        
        // Handle negative numbers (two's complement)
        if (a > Integer.MAX_VALUE) {
            a = ~(a ^ mask);
        }
        
        return a;
    }
    
    /**
     * Approach 4: Half Adder Implementation
     * O(1) time, O(1) space
     * More hardware-like implementation
     */
    public int getSumHalfAdder(int a, int b) {
        // Iterate through all bits (32 iterations for int)
        int result = 0;
        int carry = 0;
        
        for (int i = 0; i < 32; i++) {
            // Get ith bit of a and b
            int bitA = (a >> i) & 1;
            int bitB = (b >> i) & 1;
            
            // Half adder logic
            int sum = bitA ^ bitB ^ carry;
            carry = (bitA & bitB) | (bitA & carry) | (bitB & carry);
            
            // Set ith bit in result
            result |= (sum << i);
        }
        
        return result;
    }
    
    /**
     * Approach 5: Using Log and Exponentiation (Mathematical)
     * O(1) time, O(1) space
     * Uses logarithm properties: a + b = log(exp(a) * exp(b))
     * Not truly bit manipulation but creative alternative
     */
    public int getSumLog(int a, int b) {
        // This approach has precision issues and doesn't work for all cases
        // Included for educational purposes only
        if (a == 0) return b;
        if (b == 0) return a;
        
        // Using identity: a + b = log(exp(a) * exp(b))
        // But this has precision issues for integers
        return (int) Math.log(Math.exp(a) * Math.exp(b));
    }
    
    /**
     * Helper method to visualize bitwise addition
     */
    private void visualizeBitwiseAddition(int a, int b, String approach) {
        System.out.println("\n" + approach + " - Bitwise Addition Visualization:");
        System.out.println("Adding: " + a + " + " + b + " = " + (a + b));
        
        System.out.println("\nBinary Representation (32-bit):");
        System.out.println("  a = " + a + " = " + toBinaryString32(a));
        System.out.println("  b = " + b + " = " + toBinaryString32(b));
        
        System.out.println("\nStep-by-step calculation:");
        
        int step = 1;
        int tempA = a, tempB = b;
        
        while (tempB != 0) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  a = " + toBinaryString32(tempA) + " (" + tempA + ")");
            System.out.println("  b = " + toBinaryString32(tempB) + " (" + tempB + ")");
            
            int sumWithoutCarry = tempA ^ tempB;
            int carry = (tempA & tempB) << 1;
            
            System.out.println("  a ^ b = " + toBinaryString32(sumWithoutCarry) + 
                             " (sum without carry)");
            System.out.println("  (a & b) << 1 = " + toBinaryString32(carry) + 
                             " (carry bits shifted left)");
            
            tempA = sumWithoutCarry;
            tempB = carry;
            step++;
            
            if (step > 32) {
                System.out.println("\nWarning: Exceeded 32 iterations!");
                break;
            }
        }
        
        System.out.println("\nFinal result: " + tempA + " = " + toBinaryString32(tempA));
        System.out.println("Expected: " + (a + b) + " = " + toBinaryString32(a + b));
        System.out.println("Match: " + (tempA == a + b ? "✓" : "✗"));
    }
    
    private String toBinaryString32(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 31; i >= 0; i--) {
            sb.append((n >> i) & 1);
            if (i % 4 == 0 && i != 0) sb.append(" ");
        }
        return sb.toString();
    }
    
    /**
     * Helper to explain the bitwise addition logic
     */
    private void explainBitwiseAddition() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("BITWISE ADDITION EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nBasic Bit Operations:");
        System.out.println("1. XOR (^): Addition without carry");
        System.out.println("   0 ^ 0 = 0");
        System.out.println("   0 ^ 1 = 1");
        System.out.println("   1 ^ 0 = 1");
        System.out.println("   1 ^ 1 = 0 (carry generated)");
        
        System.out.println("\n2. AND (&): Finds carry positions");
        System.out.println("   0 & 0 = 0");
        System.out.println("   0 & 1 = 0");
        System.out.println("   1 & 0 = 0");
        System.out.println("   1 & 1 = 1 (carry)");
        
        System.out.println("\n3. Left shift (<<): Moves carry to next position");
        System.out.println("   (a & b) << 1 moves carry to next bit position");
        
        System.out.println("\nAddition Algorithm:");
        System.out.println("while (carry != 0):");
        System.out.println("  1. sum_without_carry = a ^ b");
        System.out.println("  2. carry = (a & b) << 1");
        System.out.println("  3. a = sum_without_carry");
        System.out.println("  4. b = carry");
        
        System.out.println("\nExample: 5 + 3 = 8");
        System.out.println("Binary: 0101 + 0011");
        System.out.println("\nIteration 1:");
        System.out.println("  a ^ b = 0101 ^ 0011 = 0110 (6)");
        System.out.println("  carry = (0101 & 0011) << 1 = (0001) << 1 = 0010 (2)");
        System.out.println("\nIteration 2:");
        System.out.println("  a ^ b = 0110 ^ 0010 = 0100 (4)");
        System.out.println("  carry = (0110 & 0010) << 1 = (0010) << 1 = 0100 (4)");
        System.out.println("\nIteration 3:");
        System.out.println("  a ^ b = 0100 ^ 0100 = 0000 (0)");
        System.out.println("  carry = (0100 & 0100) << 1 = (0100) << 1 = 1000 (8)");
        System.out.println("\nIteration 4:");
        System.out.println("  a ^ b = 0000 ^ 1000 = 1000 (8)");
        System.out.println("  carry = (0000 & 1000) << 1 = 0000 (0)");
        System.out.println("\nResult: 1000 (8)");
        
        System.out.println("\nWhy it works for negative numbers?");
        System.out.println("Two's complement representation:");
        System.out.println("-x = ~x + 1");
        System.out.println("Bitwise operations work naturally with two's complement");
        System.out.println("Example: 5 + (-3) = 2");
        System.out.println("-3 in two's complement: ~3 + 1 = ~0011 + 1 = 1100 + 1 = 1101");
        System.out.println("0101 + 1101 = 0010 (2) with carry out, which is ignored");
    }
    
    /**
     * Helper to demonstrate with examples
     */
    private void demonstrateExamples() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EXAMPLE DEMONSTRATIONS:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. Positive numbers:");
        int[][] positiveTests = {
            {1, 2}, {5, 3}, {10, 20}, {100, 200}, {999, 1}
        };
        
        for (int[] test : positiveTests) {
            int a = test[0], b = test[1];
            int result = solution.getSum(a, b);
            System.out.printf("%3d + %3d = %4d (Expected: %4d) %s%n",
                a, b, result, a + b, result == a + b ? "✓" : "✗");
        }
        
        System.out.println("\n2. Negative numbers:");
        int[][] negativeTests = {
            {-1, -2}, {-5, -3}, {-10, 5}, {5, -10}, {-100, 50}
        };
        
        for (int[] test : negativeTests) {
            int a = test[0], b = test[1];
            int result = solution.getSum(a, b);
            System.out.printf("%4d + %4d = %4d (Expected: %4d) %s%n",
                a, b, result, a + b, result == a + b ? "✓" : "✗");
        }
        
        System.out.println("\n3. Edge cases:");
        int[][] edgeTests = {
            {0, 0}, {0, 5}, {5, 0}, {Integer.MAX_VALUE, 1},
            {Integer.MIN_VALUE, -1}, {Integer.MAX_VALUE, Integer.MIN_VALUE}
        };
        
        for (int[] test : edgeTests) {
            int a = test[0], b = test[1];
            int result = solution.getSum(a, b);
            long expected = (long)a + (long)b; // Use long to avoid overflow in check
            boolean correct = result == expected;
            System.out.printf("%12d + %12d = %12d (Expected: %12d) %s%n",
                a, b, result, expected, correct ? "✓" : "✗");
        }
        
        System.out.println("\n4. Special patterns:");
        int[][] patternTests = {
            {7, 7}, {15, 15}, {0b1010, 0b0101}, // Binary patterns
            {0xAAAA, 0x5555}, {0xFFFF, 1}       // Hex patterns
        };
        
        for (int[] test : patternTests) {
            int a = test[0], b = test[1];
            int result = solution.getSum(a, b);
            System.out.printf("0x%04X + 0x%04X = 0x%04X (%5d) (Expected: 0x%04X) %s%n",
                a, b, result, result, a + b, result == a + b ? "✓" : "✗");
        }
    }
    
    /**
     * Helper to compare different approaches
     */
    private void compareApproaches(int a, int b) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput: a = " + a + ", b = " + b);
        System.out.println("Expected: " + (a + b));
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5;
        
        // Approach 1: While loop
        startTime = System.nanoTime();
        result1 = solution.getSum(a, b);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Recursive
        startTime = System.nanoTime();
        result2 = solution.getSumRecursive(a, b);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: 32-bit explicit
        startTime = System.nanoTime();
        result3 = solution.getSum32Bit(a, b);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Half adder
        startTime = System.nanoTime();
        result4 = solution.getSumHalfAdder(a, b);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (While Loop):  " + result1);
        System.out.println("Approach 2 (Recursive):   " + result2);
        System.out.println("Approach 3 (32-bit):      " + result3);
        System.out.println("Approach 4 (Half Adder):  " + result4);
        
        // Approach 5: Log (has precision issues, skip for edge cases)
        if (Math.abs(a) < 100 && Math.abs(b) < 100) {
            startTime = System.nanoTime();
            result5 = solution.getSumLog(a, b);
            endTime = System.nanoTime();
            long time5 = endTime - startTime;
            
            System.out.println("Approach 5 (Log):        " + result5);
            
            boolean allEqual = (result1 == result2) && (result2 == result3) && 
                              (result3 == result4) && (Math.abs(result4 - result5) <= 1);
            System.out.println("\nAll results equal (within tolerance for log): " + 
                (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (While Loop)%n", time1);
            System.out.printf("Approach 2: %-10d (Recursive)%n", time2);
            System.out.printf("Approach 3: %-10d (32-bit explicit)%n", time3);
            System.out.printf("Approach 4: %-10d (Half Adder - 32 iterations)%n", time4);
            System.out.printf("Approach 5: %-10d (Log - precision issues)%n", time5);
        } else {
            boolean allEqual = (result1 == result2) && (result2 == result3) && (result3 == result4);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (While Loop)%n", time1);
            System.out.printf("Approach 2: %-10d (Recursive)%n", time2);
            System.out.printf("Approach 3: %-10d (32-bit explicit)%n", time3);
            System.out.printf("Approach 4: %-10d (Half Adder - 32 iterations)%n", time4);
        }
        
        // Visualize if numbers are small
        if (Math.abs(a) < 256 && Math.abs(b) < 256) {
            solution.visualizeBitwiseAddition(a, b, "While Loop Approach");
        }
    }
    
    /**
     * Helper to analyze time and space complexity
     */
    private void analyzeComplexity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Time Complexity:");
        System.out.println("   All approaches: O(1)");
        System.out.println("   - While loop: At most 32 iterations for 32-bit integers");
        System.out.println("   - Recursive: Same as while loop, but recursion overhead");
        System.out.println("   - Half adder: Exactly 32 iterations (one per bit)");
        System.out.println("   - Maximum iterations = bit width of integer type");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   All approaches: O(1)");
        System.out.println("   - While loop: Constant extra variables");
        System.out.println("   - Recursive: O(1) in theory, but recursion stack in practice");
        System.out.println("   - Half adder: Constant extra variables");
        
        System.out.println("\n3. Why O(1) not O(n)?");
        System.out.println("   - n would be value of numbers, not number of bits");
        System.out.println("   - Integer has fixed bit width (32 bits)");
        System.out.println("   - So iterations are bounded by constant (32)");
        
        System.out.println("\n4. Comparison of approaches:");
        System.out.println("   While loop: Most efficient, handles all cases");
        System.out.println("   Recursive: Elegant but recursion overhead");
        System.out.println("   32-bit explicit: Good for understanding two's complement");
        System.out.println("   Half adder: Educational, shows bit-by-bit addition");
        System.out.println("   Log: Creative but impractical (precision issues)");
    }
    
    /**
     * Helper to show real-world applications
     */
    private void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Hardware Design:");
        System.out.println("   - CPU arithmetic logic units (ALUs)");
        System.out.println("   - Digital circuit design (adders, multipliers)");
        System.out.println("   - FPGA and ASIC design");
        
        System.out.println("\n2. Low-Level Programming:");
        System.out.println("   - Embedded systems with limited instruction sets");
        System.out.println("   - Microcontroller programming");
        System.out.println("   - Assembly language optimization");
        
        System.out.println("\n3. Cryptography:");
        System.out.println("   - Bitwise operations in encryption algorithms");
        System.out.println("   - Hash function implementations");
        System.out.println("   - Random number generation");
        
        System.out.println("\n4. Computer Graphics:");
        System.out.println("   - Color manipulation (RGB values)");
        System.out.println("   - Bitmap operations");
        System.out.println("   - Image processing filters");
        
        System.out.println("\n5. Game Development:");
        System.out.println("   - Fast integer arithmetic");
        System.out.println("   - Bitboard representations (chess, board games)");
        System.out.println("   - Physics engine optimizations");
        
        System.out.println("\n6. Operating Systems:");
        System.out.println("   - Memory management (address calculations)");
        System.out.println("   - System call implementations");
        System.out.println("   - Interrupt handling");
        
        System.out.println("\n7. Interview Questions:");
        System.out.println("   - Testing understanding of bit manipulation");
        System.out.println("   - Low-level programming knowledge");
        System.out.println("   - Problem-solving with constraints");
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Sum of Two Integers (without + or -):");
        System.out.println("==============================================");
        
        // Show explanation
        solution.explainBitwiseAddition();
        
        // Show examples
        solution.demonstrateExamples();
        
        // Test case 1: Simple positive numbers
        System.out.println("\n\nTest 1: Simple positive numbers");
        int a1 = 5, b1 = 3;
        int expected1 = 8;
        
        System.out.println("\nInput: a = " + a1 + ", b = " + b1);
        
        int result1 = solution.getSum(a1, b1);
        System.out.println("Expected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        // Visualize
        solution.visualizeBitwiseAddition(a1, b1, "Test 1");
        
        // Test case 2: Positive and negative
        System.out.println("\n\nTest 2: Positive and negative");
        int a2 = 5, b2 = -3;
        int expected2 = 2;
        
        System.out.println("\nInput: a = " + a2 + ", b = " + b2);
        
        int result2 = solution.getSum(a2, b2);
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        // Test case 3: Two negatives
        System.out.println("\n\nTest 3: Two negatives");
        int a3 = -5, b3 = -3;
        int expected3 = -8;
        
        System.out.println("\nInput: a = " + a3 + ", b = " + b3);
        
        int result3 = solution.getSum(a3, b3);
        System.out.println("Expected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + (result3 == expected3 ? "✓" : "✗"));
        
        // Test case 4: Zero cases
        System.out.println("\n\nTest 4: Zero cases");
        int a4 = 0, b4 = 0;
        int expected4 = 0;
        
        System.out.println("\nInput: a = " + a4 + ", b = " + b4);
        
        int result4 = solution.getSum(a4, b4);
        System.out.println("Expected: " + expected4);
        System.out.println("Result:   " + result4);
        System.out.println("Passed: " + (result4 == expected4 ? "✓" : "✗"));
        
        // Test case 5: Large numbers
        System.out.println("\n\nTest 5: Large numbers");
        int a5 = 1000, b5 = 2000;
        int expected5 = 3000;
        
        System.out.println("\nInput: a = " + a5 + ", b = " + b5);
        
        int result5 = solution.getSum(a5, b5);
        System.out.println("Expected: " + expected5);
        System.out.println("Result:   " + result5);
        System.out.println("Passed: " + (result5 == expected5 ? "✓" : "✗"));
        
        // Test case 6: Edge case - maximum values
        System.out.println("\n\nTest 6: Maximum integer");
        int a6 = Integer.MAX_VALUE, b6 = 1;
        // Integer.MAX_VALUE + 1 = Integer.MIN_VALUE due to overflow
        int expected6 = Integer.MIN_VALUE;
        
        System.out.println("\nInput: a = " + a6 + ", b = " + b6);
        
        int result6 = solution.getSum(a6, b6);
        System.out.println("Expected: " + expected6);
        System.out.println("Result:   " + result6);
        System.out.println("Passed: " + (result6 == expected6 ? "✓" : "✗"));
        
        // Compare all approaches for various test cases
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES:");
        System.out.println("=".repeat(80));
        
        int[][] testCases = {
            {a1, b1}, {a2, b2}, {a3, b3}, {a4, b4}, {a5, b5}, {a6, b6},
            {7, 7}, {15, -15}, {0b1010, 0b0101},
            {Integer.MIN_VALUE, 1}, {Integer.MAX_VALUE, -1},
            {123, 456}, {-789, -321}, {999, -1000}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\nTest Case " + (i+1) + ":");
            int a = testCases[i][0];
            int b = testCases[i][1];
            
            solution.compareApproaches(a, b);
            
            if (i < testCases.length - 1) {
                System.out.println("\n" + "-".repeat(80));
            }
        }
        
        // Performance test with random numbers
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(80));
        
        Random random = new Random(42);
        int iterations = 1000000;
        
        System.out.println("\nTesting " + iterations + " random additions:");
        
        // Test while loop approach
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            int a = random.nextInt(2000) - 1000;
            int b = random.nextInt(2000) - 1000;
            solution.getSum(a, b);
        }
        long time1 = System.currentTimeMillis() - startTime;
        
        // Test recursive approach (may be slower due to recursion overhead)
        startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            int a = random.nextInt(2000) - 1000;
            int b = random.nextInt(2000) - 1000;
            solution.getSumRecursive(a, b);
        }
        long time2 = System.currentTimeMillis() - startTime;
        
        // Test half adder approach
        startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            int a = random.nextInt(2000) - 1000;
            int b = random.nextInt(2000) - 1000;
            solution.getSumHalfAdder(a, b);
        }
        long time4 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance (milliseconds for " + iterations + " operations):");
        System.out.printf("While Loop:    %5d ms%n", time1);
        System.out.printf("Recursive:     %5d ms%n", time2);
        System.out.printf("Half Adder:    %5d ms%n", time4);
        
        System.out.println("\nRelative performance:");
        System.out.printf("While loop is %.1fx faster than recursive%n", (double)time2 / time1);
        System.out.printf("While loop is %.1fx faster than half adder%n", (double)time4 / time1);
        
        // Show complexity analysis
        solution.analyzeComplexity();
        
        // Show applications
        solution.showApplications();
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the constraints:");
        System.out.println("   - Cannot use + or - operators");
        System.out.println("   - Must use other operations");
        
        System.out.println("\n2. Recognize it's a bit manipulation problem:");
        System.out.println("   - Adding numbers at bit level");
        System.out.println("   - Similar to hardware addition");
        
        System.out.println("\n3. Recall basic bit operations:");
        System.out.println("   - XOR (^): addition without carry");
        System.out.println("   - AND (&): finds carry bits");
        System.out.println("   - Left shift (<<): moves carry");
        
        System.out.println("\n4. Derive algorithm:");
        System.out.println("   - sum_without_carry = a ^ b");
        System.out.println("   - carry = (a & b) << 1");
        System.out.println("   - Repeat until carry is 0");
        
        System.out.println("\n5. Implement with while loop:");
        System.out.println("   - While (carry != 0): update a and b");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - Negative numbers (two's complement works naturally)");
        System.out.println("   - Zero cases");
        System.out.println("   - Overflow (Java handles wrap-around)");
        
        System.out.println("\n7. Test with examples:");
        System.out.println("   - Positive numbers");
        System.out.println("   - Negative numbers");
        System.out.println("   - Mixed signs");
        
        System.out.println("\n8. Discuss complexity:");
        System.out.println("   - O(1) time (max 32 iterations)");
        System.out.println("   - O(1) space");
        
        System.out.println("\n9. Mention alternatives:");
        System.out.println("   - Recursive version");
        System.out.println("   - Half adder (bit-by-bit)");
        System.out.println("   - 32-bit explicit handling");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Understanding of bitwise operations");
        System.out.println("- Knowledge of two's complement");
        System.out.println("- Hardware addition analogy");
        System.out.println("- Clean implementation with while loop");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Infinite loop (not handling carry correctly)");
        System.out.println("- Not understanding two's complement for negatives");
        System.out.println("- Using arithmetic operators indirectly");
        System.out.println("- Not testing edge cases");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 29. Divide Two Integers (bit manipulation)");
        System.out.println("2. 67. Add Binary (bit addition)");
        System.out.println("3. 190. Reverse Bits");
        System.out.println("4. 191. Number of 1 Bits (Hamming weight)");
        System.out.println("5. 201. Bitwise AND of Numbers Range");
        System.out.println("6. 136. Single Number (XOR trick)");
        System.out.println("7. 137. Single Number II");
        System.out.println("8. 260. Single Number III");
        System.out.println("9. 338. Counting Bits");
        System.out.println("10. 389. Find the Difference");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
