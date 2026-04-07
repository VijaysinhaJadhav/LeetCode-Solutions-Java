
# Solution.java

```java
import java.util.*;

/**
 * 50. Pow(x, n)
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Implement pow(x, n) to calculate x raised to the power n.
 * 
 * Key Insights:
 * 1. Binary exponentiation reduces time complexity to O(log n)
 * 2. Handle negative exponents with reciprocal
 * 3. Use long to avoid overflow with Integer.MIN_VALUE
 * 4. Iterative approach avoids recursion stack
 */
class Solution {
    
    /**
     * Approach 1: Recursive Binary Exponentiation (Recommended)
     * Time: O(log n), Space: O(log n) recursion stack
     * 
     * Steps:
     * 1. Base case: n == 0 → return 1
     * 2. Handle negative n: return 1 / pow(x, -n)
     * 3. Recursively compute half = pow(x, n/2)
     * 4. If n even: return half * half
     * 5. If n odd: return half * half * x
     */
    public double myPow(double x, int n) {
        // Base case
        if (n == 0) return 1;
        
        // Handle negative exponent using long to avoid overflow
        long exp = n;
        if (exp < 0) {
            x = 1 / x;
            exp = -exp;
        }
        
        return powHelper(x, exp);
    }
    
    private double powHelper(double x, long n) {
        if (n == 0) return 1;
        
        double half = powHelper(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }
    
    /**
     * Approach 2: Iterative Binary Exponentiation (Space Optimized)
     * Time: O(log n), Space: O(1)
     * 
     * Uses bit manipulation to process exponent bits
     */
    public double myPowIterative(double x, int n) {
        long exp = n;
        if (exp < 0) {
            x = 1 / x;
            exp = -exp;
        }
        
        double result = 1.0;
        double currentProduct = x;
        
        while (exp > 0) {
            // If current bit is 1, multiply result by currentProduct
            if ((exp & 1) == 1) {
                result *= currentProduct;
            }
            // Square the base for next bit
            currentProduct *= currentProduct;
            // Shift exponent right
            exp >>= 1;
        }
        
        return result;
    }
    
    /**
     * Approach 3: Fast Power with Long (Most Robust)
     * Time: O(log n), Space: O(1)
     * 
     * Handles all edge cases including Integer.MIN_VALUE
     */
    public double myPowFast(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        
        double result = 1.0;
        double base = x;
        
        while (N > 0) {
            if (N % 2 == 1) {
                result *= base;
            }
            base *= base;
            N /= 2;
        }
        
        return result;
    }
    
    /**
     * Approach 4: Exponentiation by Squaring with While Loop
     * Time: O(log n), Space: O(1)
     * 
     * Alternative implementation
     */
    public double myPowWhile(double x, int n) {
        double result = 1.0;
        long power = Math.abs((long) n);
        
        while (power > 0) {
            if (power % 2 == 1) {
                result *= x;
            }
            x *= x;
            power /= 2;
        }
        
        return n < 0 ? 1 / result : result;
    }
    
    /**
     * Approach 5: Brute Force (for educational purposes)
     * Time: O(n), Space: O(1)
     * 
     * Not recommended for large n
     */
    public double myPowBruteForce(double x, int n) {
        if (n == 0) return 1;
        
        long exp = Math.abs((long) n);
        double result = 1.0;
        
        for (long i = 0; i < exp; i++) {
            result *= x;
        }
        
        return n < 0 ? 1 / result : result;
    }
    
    /**
     * Helper: Visualize the binary exponentiation process
     */
    public void visualizePow(double x, int n) {
        System.out.println("\nPower Calculation Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.printf("\nCalculating: %.4f ^ %d%n", x, n);
        
        long exp = n;
        boolean isNegative = exp < 0;
        
        if (isNegative) {
            x = 1 / x;
            exp = -exp;
            System.out.printf("Negative exponent → using reciprocal: %.10f ^ %d%n", x, exp);
        }
        
        System.out.println("\nBinary exponentiation process:");
        System.out.println("Step | Exponent (binary) | Current Base | Result");
        System.out.println("-----|------------------|--------------|-------");
        
        double result = 1.0;
        double base = x;
        long power = exp;
        int step = 1;
        
        while (power > 0) {
            System.out.printf("%4d | %16s | %12.6f | %.6f%n", 
                step++, Long.toBinaryString(power), base, result);
            
            if ((power & 1) == 1) {
                result *= base;
                System.out.printf("     | Bit is 1 → multiply: result = %.6f%n", result);
            }
            
            base *= base;
            power >>= 1;
        }
        
        System.out.printf("\nFinal result: %.10f%n", result);
        
        // Verify with built-in Math.pow
        double expected = Math.pow(x, exp);
        double diff = Math.abs(result - expected);
        System.out.printf("Verification: Math.pow() = %.10f, difference = %.10e%n", expected, diff);
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            {2.0, 10, 1024.0},           // Positive exponent
            {2.1, 3, 9.261},             // Positive exponent with decimal
            {2.0, -2, 0.25},             // Negative exponent
            {0.0, 5, 0.0},               // Zero base
            {5.0, 0, 1.0},               // Zero exponent
            {1.0, 1000, 1.0},            // Base 1
            {-2.0, 3, -8.0},             // Negative base, odd exponent
            {-2.0, 2, 4.0},              // Negative base, even exponent
            {2.0, Integer.MIN_VALUE, 0.0}, // Min integer exponent (small result)
            {0.5, 10, 0.0009765625},      // Fractional base
            {1.5, 5, 7.59375},            // Fractional base
            {0.1, 10, 1e-10}             // Very small result
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        Object[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            double x = (double) testCases[i][0];
            int n = (int) testCases[i][1];
            double expected = (double) testCases[i][2];
            
            System.out.printf("\nTest %d: %.4f ^ %d%n", i + 1, x, n);
            
            double result1 = myPow(x, n);
            double result2 = myPowIterative(x, n);
            double result3 = myPowFast(x, n);
            double result4 = myPowWhile(x, n);
            double result5 = myPowBruteForce(x, n);
            
            // Compare with tolerance for floating point
            boolean allMatch = Math.abs(result1 - expected) < 1e-9 &&
                              Math.abs(result2 - expected) < 1e-9 &&
                              Math.abs(result3 - expected) < 1e-9 &&
                              Math.abs(result4 - expected) < 1e-9 &&
                              Math.abs(result5 - expected) < 1e-9;
            
            if (allMatch) {
                System.out.println("✓ PASS - Result: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizePow(x, n);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=".repeat(50));
        
        double x = 1.0001;
        int n = 1000000000; // 1e9
        
        System.out.println("Test Setup: " + x + " ^ " + n);
        System.out.println("This will produce a result ~ e^(100000) which overflows, but we measure time only");
        
        long[] times = new long[5];
        double[] results = new double[5];
        
        // Method 1: Recursive
        long start = System.currentTimeMillis();
        results[0] = myPow(x, n);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Iterative
        start = System.currentTimeMillis();
        results[1] = myPowIterative(x, n);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Fast Power
        start = System.currentTimeMillis();
        results[2] = myPowFast(x, n);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: While loop
        start = System.currentTimeMillis();
        results[3] = myPowWhile(x, n);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Brute Force (skip for large n)
        times[4] = -1;
        results[4] = 0;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. Recursive              | %9d%n", times[0]);
        System.out.printf("2. Iterative              | %9d%n", times[1]);
        System.out.printf("3. Fast Power             | %9d%n", times[2]);
        System.out.printf("4. While Loop             | %9d%n", times[3]);
        System.out.printf("5. Brute Force            | %9s%n", "N/A (O(n))");
        
        // Check if results are consistent (within tolerance)
        boolean consistent = true;
        for (int i = 1; i < 4; i++) {
            if (Math.abs(results[i] - results[0]) > 1e-6) {
                consistent = false;
            }
        }
        System.out.println("\nAll O(log n) methods consistent: " + (consistent ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Iterative methods are slightly faster than recursive");
        System.out.println("2. Recursive approach has function call overhead");
        System.out.println("3. All O(log n) methods scale well for large exponents");
        System.out.println("4. Brute force O(n) is impractical for large n");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Zero exponent:");
        System.out.println("   2^0 = " + myPow(2, 0));
        
        System.out.println("\n2. Zero base:");
        System.out.println("   0^5 = " + myPow(0, 5));
        
        System.out.println("\n3. Base = 1:");
        System.out.println("   1^1000 = " + myPow(1, 1000));
        
        System.out.println("\n4. Base = -1, odd exponent:");
        System.out.println("   (-1)^3 = " + myPow(-1, 3));
        
        System.out.println("\n5. Base = -1, even exponent:");
        System.out.println("   (-1)^4 = " + myPow(-1, 4));
        
        System.out.println("\n6. Minimum integer exponent (Integer.MIN_VALUE):");
        long start = System.currentTimeMillis();
        double result = myPow(2.0, Integer.MIN_VALUE);
        long time = System.currentTimeMillis() - start;
        System.out.println("   2^-2147483648 = " + result);
        System.out.println("   Time: " + time + "ms");
        
        System.out.println("\n7. Maximum integer exponent (Integer.MAX_VALUE):");
        start = System.currentTimeMillis();
        result = myPow(1.0000001, Integer.MAX_VALUE);
        time = System.currentTimeMillis() - start;
        System.out.println("   1.0000001^2147483647 = " + result);
        System.out.println("   Time: " + time + "ms");
        
        System.out.println("\n8. Very small base with large exponent:");
        System.out.println("   0.5^1000 = " + myPow(0.5, 1000));
        
        System.out.println("\n9. Very large base with small exponent:");
        System.out.println("   100^5 = " + myPow(100, 5));
    }
    
    /**
     * Helper: Explain binary exponentiation
     */
    public void explainAlgorithm() {
        System.out.println("\nBinary Exponentiation Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nWhat is Binary Exponentiation?");
        System.out.println("Also called 'fast power' or 'exponentiation by squaring'.");
        System.out.println("It computes x^n in O(log n) time by using the binary representation of n.");
        
        System.out.println("\nHow it works:");
        System.out.println("x^n = (x^(n/2))^2 for even n");
        System.out.println("x^n = x * (x^(n-1)) for odd n");
        
        System.out.println("\nExample: x^13");
        System.out.println("  13 in binary: 1101 (8 + 4 + 1)");
        System.out.println("  x^13 = x^8 * x^4 * x^1");
        System.out.println("  Process bits from right to left:");
        System.out.println("    bit 0 (1): multiply result by x^1");
        System.out.println("    bit 1 (0): skip");
        System.out.println("    bit 2 (1): multiply result by x^4");
        System.out.println("    bit 3 (1): multiply result by x^8");
        
        System.out.println("\nAlgorithm steps:");
        System.out.println("1. result = 1, base = x, exponent = n");
        System.out.println("2. While exponent > 0:");
        System.out.println("   - If exponent is odd: result *= base");
        System.out.println("   - base *= base");
        System.out.println("   - exponent /= 2");
        System.out.println("3. Return result");
        
        System.out.println("\nTime Complexity: O(log n)");
        System.out.println("Space Complexity: O(1) iterative, O(log n) recursive");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What are the constraints? (x up to 100, n up to 2^31)");
        System.out.println("   - Should we handle negative exponents? (Yes)");
        System.out.println("   - Precision requirements? (1e-9)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - O(n) multiplication");
        System.out.println("   - Acknowledge it's too slow for large n");
        
        System.out.println("\n3. Propose binary exponentiation:");
        System.out.println("   - Explain using example: 2^13 = 2^8 * 2^4 * 2^1");
        System.out.println("   - Show binary representation of exponent");
        
        System.out.println("\n4. Handle negative exponents:");
        System.out.println("   - Use reciprocal: x^(-n) = 1 / x^n");
        System.out.println("   - Watch for overflow with Integer.MIN_VALUE");
        
        System.out.println("\n5. Implementation details:");
        System.out.println("   - Use long for exponent to avoid overflow");
        System.out.println("   - Handle n = Integer.MIN_VALUE carefully");
        System.out.println("   - Use iterative approach to avoid recursion depth issues");
        
        System.out.println("\n6. Complexity analysis:");
        System.out.println("   - Time: O(log n)");
        System.out.println("   - Space: O(1) iterative, O(log n) recursive");
        
        System.out.println("\n7. Edge cases:");
        System.out.println("   - n = 0 → return 1");
        System.out.println("   - x = 0 → return 0 for n > 0, undefined for n ≤ 0");
        System.out.println("   - x = 1 → return 1 regardless of n");
        System.out.println("   - x = -1 → alternating between 1 and -1");
        
        System.out.println("\n8. Common mistakes:");
        System.out.println("   - Forgetting to handle negative exponents");
        System.out.println("   - Integer overflow with Math.abs(Integer.MIN_VALUE)");
        System.out.println("   - Using recursion for very large n (stack overflow)");
        System.out.println("   - Not using double for multiplication (precision loss)");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("50. Pow(x, n)");
        System.out.println("=============");
        
        // Explain algorithm
        solution.explainAlgorithm();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public double myPow(double x, int n) {
        long exp = n;
        if (exp < 0) {
            x = 1 / x;
            exp = -exp;
        }
        
        double result = 1.0;
        double base = x;
        
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result *= base;
            }
            base *= base;
            exp >>= 1;
        }
        
        return result;
    }
}
            """);
        
        System.out.println("\nAlternative (Recursive):");
        System.out.println("""
class Solution {
    public double myPow(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        return powHelper(x, N);
    }
    
    private double powHelper(double x, long n) {
        if (n == 0) return 1;
        double half = powHelper(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Binary exponentiation achieves O(log n) time");
        System.out.println("2. Use long for exponent to handle Integer.MIN_VALUE");
        System.out.println("3. Handle negative exponents with reciprocal");
        System.out.println("4. Iterative approach avoids recursion stack overhead");
        System.out.println("5. Bit manipulation (exp & 1) checks if exponent is odd");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(log n) - number of bits in exponent");
        System.out.println("- Space: O(1) for iterative, O(log n) for recursive");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle fractional exponents? (Use logarithms)");
        System.out.println("2. What if exponent is very large? (O(log n) handles it)");
        System.out.println("3. How would you handle complex numbers?");
        System.out.println("4. Can you implement exponentiation for matrices?");
        System.out.println("5. How would you detect overflow in result?");
    }
}
