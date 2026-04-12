
# Solution.java

```java
import java.util.*;

/**
 * 326. Power of Three
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Determine if an integer is a power of three.
 * 
 * Key Insights:
 * 1. Negative numbers and zero cannot be powers of three
 * 2. Loop: repeatedly divide by 3 until remainder != 0
 * 3. Recursion: base case n == 1
 * 4. Precomputed: 3^19 is the largest power within int range
 */
class Solution {
    
    /**
     * Approach 1: Iterative Division (Loop) - RECOMMENDED
     * Time: O(log₃ n), Space: O(1)
     * 
     * Steps:
     * 1. If n <= 0, return false
     * 2. While n % 3 == 0, divide n by 3
     * 3. Return n == 1
     */
    public boolean isPowerOfThree(int n) {
        if (n <= 0) return false;
        
        while (n % 3 == 0) {
            n /= 3;
        }
        
        return n == 1;
    }
    
    /**
     * Approach 2: Recursive Division
     * Time: O(log₃ n), Space: O(log₃ n) recursion stack
     * 
     * Elegant but uses recursion
     */
    public boolean isPowerOfThreeRecursive(int n) {
        if (n <= 0) return false;
        if (n == 1) return true;
        if (n % 3 != 0) return false;
        return isPowerOfThreeRecursive(n / 3);
    }
    
    /**
     * Approach 3: Logarithm (Math.log10)
     * Time: O(1), Space: O(1)
     * 
     * Uses floating-point arithmetic, potential precision issues
     */
    public boolean isPowerOfThreeLog(int n) {
        if (n <= 0) return false;
        
        double log = Math.log10(n) / Math.log10(3);
        return Math.abs(log - Math.round(log)) < 1e-10;
    }
    
    /**
     * Approach 4: Integer Limitation (Precomputed)
     * Time: O(1), Space: O(1)
     * 
     * Since 3^19 is the largest power within int range,
     * any power of three must divide 3^19
     */
    public boolean isPowerOfThreeMaxInt(int n) {
        // 3^19 = 1162261467 is the largest power of three within 32-bit int
        return n > 0 && 1162261467 % n == 0;
    }
    
    /**
     * Approach 5: Precomputed Set of Powers
     * Time: O(1), Space: O(1)
     * 
     * Store all 20 powers of three in a HashSet
     */
    public boolean isPowerOfThreeSet(int n) {
        Set<Integer> powers = new HashSet<>(Arrays.asList(
            1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049,
            177147, 531441, 1594323, 4782969, 14348907, 43046721,
            129140163, 387420489, 1162261467
        ));
        return powers.contains(n);
    }
    
    /**
     * Approach 6: Bit Manipulation (Not efficient for power of three)
     * Time: O(log₃ n), Space: O(1)
     * 
     * Included for completeness - not optimal
     */
    public boolean isPowerOfThreeBit(int n) {
        if (n <= 0) return false;
        
        // Convert to base 3 and check if all digits are 0 except leading 1
        // But this requires conversion, so not better than division
        while (n > 1) {
            if (n % 3 != 0) return false;
            n /= 3;
        }
        return true;
    }
    
    /**
     * Helper: Generate all powers of three within int range
     */
    public static void printAllPowers() {
        System.out.println("\nAll powers of three within 32-bit integer range:");
        long power = 1;
        int exponent = 0;
        while (power <= Integer.MAX_VALUE) {
            System.out.printf("3^%d = %d%n", exponent, power);
            power *= 3;
            exponent++;
        }
        System.out.printf("3^%d = %d (exceeds int range)%n", exponent, power);
    }
    
    /**
     * Helper: Visualize division process
     */
    public void visualizeDivision(int n) {
        System.out.println("\nPower of Three Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.printf("\nInput: %d%n", n);
        
        if (n <= 0) {
            System.out.println("n <= 0 → false (negative or zero cannot be power of three)");
            return;
        }
        
        int original = n;
        System.out.println("\nDivision process:");
        
        while (n % 3 == 0) {
            System.out.printf("  %d %% 3 == 0 → %d / 3 = %d%n", n, n, n / 3);
            n /= 3;
        }
        
        System.out.printf("  %d %% 3 = %d ≠ 0 → stop%n", n, n % 3);
        System.out.printf("\nResult: %d == 1? %b%n", n, n == 1);
        System.out.printf("%d is %s power of three%n", original, n == 1 ? "a" : "not a");
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[] generateTestCases() {
        return new int[] {
            1,      // true (3^0)
            3,      // true (3^1)
            9,      // true (3^2)
            27,     // true (3^3)
            81,     // true (3^4)
            243,    // true (3^5)
            729,    // true (3^6)
            0,      // false
            -1,     // false
            -27,    // false
            2,      // false
            4,      // false
            6,      // false
            8,      // false
            10,     // false
            45,     // false
            1162261467  // true (3^19, max power)
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[] testCases = generateTestCases();
        boolean[] expected = {
            true, true, true, true, true, true, true,
            false, false, false, false, false, false, false, false, false, true
        };
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i];
            System.out.printf("\nTest %d: n = %d%n", i + 1, n);
            
            boolean result1 = isPowerOfThree(n);
            boolean result2 = isPowerOfThreeRecursive(n);
            boolean result3 = isPowerOfThreeLog(n);
            boolean result4 = isPowerOfThreeMaxInt(n);
            boolean result5 = isPowerOfThreeSet(n);
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Result: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1 (Loop): " + result1);
                System.out.println("  Method 2 (Recursive): " + result2);
                System.out.println("  Method 3 (Log): " + result3);
                System.out.println("  Method 4 (MaxInt): " + result4);
                System.out.println("  Method 5 (Set): " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeDivision(n);
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
        
        int[] testNumbers = {1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 
                             59049, 177147, 531441, 1594323, 4782969, 14348907, 
                             43046721, 129140163, 387420489, 1162261467};
        
        System.out.println("Test Setup: " + testNumbers.length + " numbers (all powers of three)");
        
        long[] times = new long[5];
        
        // Method 1: Iterative Division
        long start = System.currentTimeMillis();
        for (int n : testNumbers) {
            isPowerOfThree(n);
        }
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Recursive Division
        start = System.currentTimeMillis();
        for (int n : testNumbers) {
            isPowerOfThreeRecursive(n);
        }
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Logarithm
        start = System.currentTimeMillis();
        for (int n : testNumbers) {
            isPowerOfThreeLog(n);
        }
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: MaxInt Modulo
        start = System.currentTimeMillis();
        for (int n : testNumbers) {
            isPowerOfThreeMaxInt(n);
        }
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: HashSet
        start = System.currentTimeMillis();
        for (int n : testNumbers) {
            isPowerOfThreeSet(n);
        }
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. Iterative Division     | %9d%n", times[0]);
        System.out.printf("2. Recursive Division     | %9d%n", times[1]);
        System.out.printf("3. Logarithm              | %9d%n", times[2]);
        System.out.printf("4. MaxInt Modulo          | %9d%n", times[3]);
        System.out.printf("5. HashSet                | %9d%n", times[4]);
        
        System.out.println("\nObservations:");
        System.out.println("1. MaxInt modulo is fastest (O(1))");
        System.out.println("2. HashSet is also O(1) but has setup overhead");
        System.out.println("3. Iterative and recursive have O(log n) time");
        System.out.println("4. Logarithm has floating-point precision concerns");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. n = 0:");
        System.out.println("   Input: 0");
        System.out.println("   Output: " + isPowerOfThree(0));
        
        System.out.println("\n2. n = 1 (3^0):");
        System.out.println("   Input: 1");
        System.out.println("   Output: " + isPowerOfThree(1));
        
        System.out.println("\n3. n = -27:");
        System.out.println("   Input: -27");
        System.out.println("   Output: " + isPowerOfThree(-27));
        
        System.out.println("\n4. n = Integer.MAX_VALUE:");
        System.out.println("   Input: 2147483647");
        System.out.println("   Output: " + isPowerOfThree(Integer.MAX_VALUE));
        
        System.out.println("\n5. n = Integer.MIN_VALUE:");
        System.out.println("   Input: -2147483648");
        System.out.println("   Output: " + isPowerOfThree(Integer.MIN_VALUE));
        
        System.out.println("\n6. Largest power of three (3^19):");
        System.out.println("   Input: 1162261467");
        System.out.println("   Output: " + isPowerOfThree(1162261467));
        
        System.out.println("\n7. 3^19 + 1:");
        System.out.println("   Input: 1162261468");
        System.out.println("   Output: " + isPowerOfThree(1162261468));
    }
    
    /**
     * Helper: Explain approaches
     */
    public void explainApproaches() {
        System.out.println("\nApproach Explanations:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Iterative Division (Loop):");
        System.out.println("   - Repeatedly divide by 3 while divisible");
        System.out.println("   - If final number is 1 → power of three");
        System.out.println("   - Time: O(log₃ n), Space: O(1)");
        
        System.out.println("\n2. Recursive Division:");
        System.out.println("   - Base cases: n == 1 → true, n % 3 != 0 → false");
        System.out.println("   - Recursive call: isPowerOfThree(n / 3)");
        System.out.println("   - Time: O(log₃ n), Space: O(log₃ n) for recursion stack");
        
        System.out.println("\n3. Logarithm:");
        System.out.println("   - Compute log10(n) / log10(3)");
        System.out.println("   - If result is integer (within precision) → true");
        System.out.println("   - Time: O(1), but floating-point precision issues");
        
        System.out.println("\n4. Integer Limitation (MaxInt Modulo):");
        System.out.println("   - 3^19 = 1162261467 is largest power within int range");
        System.out.println("   - Any power of three divides 3^19");
        System.out.println("   - Check: n > 0 && 1162261467 % n == 0");
        System.out.println("   - Time: O(1), Space: O(1)");
        
        System.out.println("\n5. Precomputed Set:");
        System.out.println("   - Precompute all 20 powers of three");
        System.out.println("   - Check if n is in the set");
        System.out.println("   - Time: O(1), Space: O(20) ≈ O(1)");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What about negative numbers? (Return false)");
        System.out.println("   - What about zero? (Return false)");
        System.out.println("   - What's the range of n? (32-bit integer)");
        
        System.out.println("\n2. Start with loop approach:");
        System.out.println("   - Most intuitive solution");
        System.out.println("   - Explain division process");
        
        System.out.println("\n3. Discuss follow-up: 'without loops/recursion':");
        System.out.println("   - MaxInt modulo trick");
        System.out.println("   - Precomputed set");
        System.out.println("   - Logarithm (mention precision concerns)");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - Loop: O(log₃ n) time, O(1) space");
        System.out.println("   - MaxInt: O(1) time, O(1) space");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - n = 0, n = 1");
        System.out.println("   - Negative numbers");
        System.out.println("   - Large numbers (3^19, 3^19+1)");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Forgetting to handle n <= 0");
        System.out.println("   - Integer overflow in multiplication");
        System.out.println("   - Floating-point precision in logarithm method");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("326. Power of Three");
        System.out.println("==================");
        
        // Print all powers
        printAllPowers();
        
        // Explain approaches
        solution.explainApproaches();
        
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
        
        System.out.println("\nRecommended Implementation (Loop):");
        System.out.println("""
class Solution {
    public boolean isPowerOfThree(int n) {
        if (n <= 0) return false;
        
        while (n % 3 == 0) {
            n /= 3;
        }
        
        return n == 1;
    }
}
            """);
        
        System.out.println("\nRecommended for Follow-up (MaxInt Modulo):");
        System.out.println("""
class Solution {
    public boolean isPowerOfThree(int n) {
        // 3^19 = 1162261467 is the largest power of three within 32-bit int
        return n > 0 && 1162261467 % n == 0;
    }
}
            """);
        
        System.out.println("\nAlternative (Precomputed Set):");
        System.out.println("""
class Solution {
    private static final Set<Integer> POWERS = Set.of(
        1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049,
        177147, 531441, 1594323, 4782969, 14348907, 43046721,
        129140163, 387420489, 1162261467
    );
    
    public boolean isPowerOfThree(int n) {
        return POWERS.contains(n);
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Loop approach is simplest: O(log₃ n) time");
        System.out.println("2. Follow-up can be solved with O(1) using modulo trick");
        System.out.println("3. Only 20 powers of three within 32-bit int range");
        System.out.println("4. Negative numbers and zero are never powers");
        System.out.println("5. 3^0 = 1 is a power of three");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Loop/Recursion: O(log₃ n) time, O(1) space");
        System.out.println("- MaxInt Modulo: O(1) time, O(1) space");
        System.out.println("- Set: O(1) time, O(1) space (20 entries)");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you check for power of two?");
        System.out.println("2. How would you check for power of four?");
        System.out.println("3. How would you find the exponent without loops?");
        System.out.println("4. How would you handle very large numbers (BigInteger)?");
        System.out.println("5. How would you check if a number is a perfect cube?");
    }
}
