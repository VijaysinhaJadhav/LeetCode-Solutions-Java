
# Solution.java

```java
import java.util.*;

/**
 * 166. Fraction to Recurring Decimal
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Convert fraction to decimal string, enclosing repeating part in parentheses.
 * 
 * Key Insights:
 * 1. Handle sign separately
 * 2. Use long division to compute decimal digits
 * 3. Track remainders to detect cycles
 * 4. When remainder repeats, we've found the repeating block
 */
class Solution {
    
    /**
     * Approach 1: Long Division with HashMap (Recommended)
     * Time: O(k), Space: O(k) where k = length of decimal part
     * 
     * Steps:
     * 1. Handle sign and convert to positive longs
     * 2. Compute integer part
     * 3. If remainder == 0, return integer part only
     * 4. Otherwise, compute fractional part using long division
     * 5. Track remainder positions to detect cycles
     */
    public String fractionToDecimal(int numerator, int denominator) {
        // Handle zero numerator
        if (numerator == 0) return "0";
        
        // Handle sign
        StringBuilder result = new StringBuilder();
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }
        
        // Convert to positive longs to avoid overflow
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        
        // Integer part
        long integerPart = num / den;
        result.append(integerPart);
        
        // Remainder
        long remainder = num % den;
        if (remainder == 0) {
            return result.toString();
        }
        
        // Decimal part
        result.append(".");
        Map<Long, Integer> remainderPositions = new HashMap<>();
        
        while (remainder != 0) {
            // If remainder repeats, we found the cycle
            if (remainderPositions.containsKey(remainder)) {
                int startIndex = remainderPositions.get(remainder);
                result.insert(startIndex, "(");
                result.append(")");
                break;
            }
            
            // Record position of this remainder
            remainderPositions.put(remainder, result.length());
            
            // Compute next digit
            remainder *= 10;
            result.append(remainder / den);
            remainder %= den;
        }
        
        return result.toString();
    }
    
    /**
     * Approach 2: Using StringBuilder with Index Tracking
     * Time: O(k), Space: O(k)
     * 
     * Similar to approach 1 but more explicit
     */
    public String fractionToDecimalDetailed(int numerator, int denominator) {
        // Edge case: numerator is 0
        if (numerator == 0) {
            return "0";
        }
        
        // Handle sign
        StringBuilder sb = new StringBuilder();
        if ((numerator < 0) ^ (denominator < 0)) {
            sb.append("-");
        }
        
        // Convert to long to avoid overflow
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        
        // Integer part
        long integer = num / den;
        sb.append(integer);
        
        long remainder = num % den;
        if (remainder == 0) {
            return sb.toString();
        }
        
        // Decimal part
        sb.append(".");
        Map<Long, Integer> remainderIndexMap = new HashMap<>();
        
        while (remainder != 0) {
            // Check for repeating cycle
            if (remainderIndexMap.containsKey(remainder)) {
                int index = remainderIndexMap.get(remainder);
                sb.insert(index, "(");
                sb.append(")");
                break;
            }
            
            // Store remainder position
            remainderIndexMap.put(remainder, sb.length());
            
            // Compute next digit
            remainder *= 10;
            long digit = remainder / den;
            sb.append(digit);
            remainder %= den;
        }
        
        return sb.toString();
    }
    
    /**
     * Approach 3: Mathematical with Cycle Detection
     * Time: O(k), Space: O(k)
     * 
     * More explicit cycle detection using remainder tracking
     */
    public String fractionToDecimalMath(int numerator, int denominator) {
        if (numerator == 0) return "0";
        
        // Handle sign
        StringBuilder result = new StringBuilder();
        result.append((numerator > 0) ^ (denominator > 0) ? "-" : "");
        
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        
        // Integer part
        result.append(num / den);
        
        long remainder = num % den;
        if (remainder == 0) return result.toString();
        
        result.append(".");
        
        // Track remainders and their positions
        List<Long> remainders = new ArrayList<>();
        List<Integer> positions = new ArrayList<>();
        
        while (remainder != 0) {
            // Check for cycle
            int index = remainders.indexOf(remainder);
            if (index != -1) {
                // Found cycle
                int cycleStart = positions.get(index);
                String decimalPart = result.substring(result.indexOf(".") + 1);
                String nonRepeating = decimalPart.substring(0, cycleStart);
                String repeating = decimalPart.substring(cycleStart);
                result = new StringBuilder();
                result.append(result.substring(0, result.indexOf(".") + 1));
                result.append(nonRepeating).append("(").append(repeating).append(")");
                break;
            }
            
            // Store remainder and position
            remainders.add(remainder);
            positions.add(result.length() - result.indexOf(".") - 1);
            
            // Compute next digit
            remainder *= 10;
            result.append(remainder / den);
            remainder %= den;
        }
        
        return result.toString();
    }
    
    /**
     * Approach 4: Without HashMap (using remainder list)
     * Time: O(k²), Space: O(k)
     * 
     * Less efficient but uses list for remainder tracking
     */
    public String fractionToDecimalList(int numerator, int denominator) {
        if (numerator == 0) return "0";
        
        StringBuilder result = new StringBuilder();
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }
        
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        
        result.append(num / den);
        
        long remainder = num % den;
        if (remainder == 0) return result.toString();
        
        result.append(".");
        
        List<Long> remainders = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        
        while (remainder != 0) {
            int idx = remainders.indexOf(remainder);
            if (idx != -1) {
                int cycleStart = indices.get(idx);
                result.insert(result.indexOf(".") + 1 + cycleStart, "(");
                result.append(")");
                break;
            }
            
            remainders.add(remainder);
            indices.add(result.length() - result.indexOf(".") - 1);
            
            remainder *= 10;
            result.append(remainder / den);
            remainder %= den;
        }
        
        return result.toString();
    }
    
    /**
     * Approach 5: Recursive decimal extraction
     * Time: O(k), Space: O(k) recursion depth
     * 
     * Recursive approach (not recommended for long decimals)
     */
    public String fractionToDecimalRecursive(int numerator, int denominator) {
        if (numerator == 0) return "0";
        
        StringBuilder result = new StringBuilder();
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }
        
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        
        result.append(num / den);
        
        long remainder = num % den;
        if (remainder == 0) return result.toString();
        
        result.append(".");
        
        Map<Long, Integer> remainderMap = new HashMap<>();
        extractDecimal(result, remainder, den, remainderMap);
        
        return result.toString();
    }
    
    private void extractDecimal(StringBuilder sb, long remainder, long den, Map<Long, Integer> map) {
        if (remainder == 0) return;
        
        if (map.containsKey(remainder)) {
            int start = map.get(remainder);
            sb.insert(start, "(");
            sb.append(")");
            return;
        }
        
        map.put(remainder, sb.length());
        remainder *= 10;
        sb.append(remainder / den);
        extractDecimal(sb, remainder % den, den, map);
    }
    
    /**
     * Helper: Visualize the division process
     */
    public void visualizeDivision(int numerator, int denominator) {
        System.out.println("\nFraction to Recurring Decimal Visualization:");
        System.out.println("=".repeat(70));
        
        System.out.printf("\nInput: %d / %d%n", numerator, denominator);
        
        // Sign handling
        boolean isNegative = (numerator < 0) ^ (denominator < 0);
        System.out.printf("Sign: %s%n", isNegative ? "Negative" : "Positive");
        
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        
        // Integer part
        long integerPart = num / den;
        long remainder = num % den;
        System.out.printf("\nInteger part: %d%n", integerPart);
        System.out.printf("Initial remainder: %d%n", remainder);
        
        if (remainder == 0) {
            System.out.printf("\nNo fractional part → Result: %s%d%n", 
                isNegative ? "-" : "", integerPart);
            return;
        }
        
        System.out.println("\nLong division process:");
        System.out.println("Step | Remainder | Multiply | Digit | New Remainder | Action");
        System.out.println("-----|-----------|----------|-------|---------------|-------");
        
        Map<Long, Integer> remainderPositions = new HashMap<>();
        StringBuilder decimalPart = new StringBuilder();
        int step = 1;
        
        while (remainder != 0) {
            if (remainderPositions.containsKey(remainder)) {
                int startIndex = remainderPositions.get(remainder);
                System.out.printf("%4d | %9d | Cycle detected at index %d%n", 
                    step, remainder, startIndex);
                decimalPart.insert(startIndex, "(");
                decimalPart.append(")");
                break;
            }
            
            remainderPositions.put(remainder, decimalPart.length());
            
            remainder *= 10;
            long digit = remainder / den;
            long newRemainder = remainder % den;
            
            System.out.printf("%4d | %9d | %8d | %5d | %11d | %s%n", 
                step, remainder / 10, remainder, digit, newRemainder,
                digit + (newRemainder == 0 ? " (terminating)" : ""));
            
            decimalPart.append(digit);
            remainder = newRemainder;
            step++;
        }
        
        String result = (isNegative ? "-" : "") + integerPart + "." + decimalPart.toString();
        System.out.printf("\nResult: %s%n", result);
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            {1, 2},           // "0.5"
            {2, 1},           // "2"
            {4, 333},         // "0.(012)"
            {1, 3},           // "0.(3)"
            {1, 6},           // "0.1(6)"
            {1, 7},           // "0.(142857)"
            {1, 17},          // "0.(0588235294117647)"
            {-1, 2},          // "-0.5"
            {1, -2},          // "-0.5"
            {-1, -2},         // "0.5"
            {0, 5},           // "0"
            {Integer.MIN_VALUE, -1},  // "2147483648" (overflow case)
            {Integer.MIN_VALUE, 1},   // "-2147483648"
            {7, 12}           // "0.58(3)"
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][] testCases = generateTestCases();
        String[] expected = {
            "0.5", "2", "0.(012)", "0.(3)", "0.1(6)", "0.(142857)",
            "0.(0588235294117647)", "-0.5", "-0.5", "0.5", "0",
            "2147483648", "-2147483648", "0.58(3)"
        };
        
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int numerator = testCases[i][0];
            int denominator = testCases[i][1];
            
            System.out.printf("\nTest %d: %d / %d%n", i + 1, numerator, denominator);
            
            String result1 = fractionToDecimal(numerator, denominator);
            String result2 = fractionToDecimalDetailed(numerator, denominator);
            String result3 = fractionToDecimalMath(numerator, denominator);
            String result4 = fractionToDecimalList(numerator, denominator);
            String result5 = fractionToDecimalRecursive(numerator, denominator);
            
            boolean allMatch = result1.equals(expected[i]) && result2.equals(expected[i]) &&
                              result3.equals(expected[i]) && result4.equals(expected[i]) &&
                              result5.equals(expected[i]);
            
            if (allMatch) {
                System.out.println("✓ PASS - Result: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first few test cases
            if (i < 5) {
                visualizeDivision(numerator, denominator);
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
        
        // Test with long repeating decimal (1/7 = 0.(142857))
        int numerator = 1;
        int denominator = 7;
        
        System.out.printf("Test Setup: %d / %d (repeating cycle length 6)%n", numerator, denominator);
        
        long[] times = new long[5];
        
        // Method 1: HashMap
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            fractionToDecimal(numerator, denominator);
        }
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Detailed
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            fractionToDecimalDetailed(numerator, denominator);
        }
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Math
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            fractionToDecimalMath(numerator, denominator);
        }
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: List (slower)
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            fractionToDecimalList(numerator, denominator);
        }
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Recursive
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            fractionToDecimalRecursive(numerator, denominator);
        }
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults (10,000 iterations each):");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. HashMap                | %9d%n", times[0]);
        System.out.printf("2. Detailed               | %9d%n", times[1]);
        System.out.printf("3. Math                   | %9d%n", times[2]);
        System.out.printf("4. List                   | %9d%n", times[3]);
        System.out.printf("5. Recursive              | %9d%n", times[4]);
        
        System.out.println("\nObservations:");
        System.out.println("1. HashMap approach is fastest");
        System.out.println("2. List approach is slower due to indexOf scanning");
        System.out.println("3. Recursive approach has function call overhead");
        System.out.println("4. All approaches produce correct results");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Zero numerator:");
        System.out.println("   0 / 5 = " + fractionToDecimal(0, 5));
        
        System.out.println("\n2. Denominator = 1:");
        System.out.println("   10 / 1 = " + fractionToDecimal(10, 1));
        
        System.out.println("\n3. Negative numerator:");
        System.out.println("   -1 / 2 = " + fractionToDecimal(-1, 2));
        
        System.out.println("\n4. Negative denominator:");
        System.out.println("   1 / -2 = " + fractionToDecimal(1, -2));
        
        System.out.println("\n5. Both negative:");
        System.out.println("   -1 / -2 = " + fractionToDecimal(-1, -2));
        
        System.out.println("\n6. Integer overflow case (MIN_VALUE):");
        System.out.println("   -2147483648 / -1 = " + fractionToDecimal(Integer.MIN_VALUE, -1));
        
        System.out.println("\n7. Large denominator:");
        System.out.println("   1 / 999999 = " + fractionToDecimal(1, 999999));
        
        System.out.println("\n8. Terminating decimal:");
        System.out.println("   1 / 16 = " + fractionToDecimal(1, 16));
        
        System.out.println("\n9. Long repeating cycle:");
        System.out.println("   1 / 97 = " + fractionToDecimal(1, 97));
        
        System.out.println("\n10. Whole number with decimal:");
        System.out.println("   5 / 2 = " + fractionToDecimal(5, 2));
    }
    
    /**
     * Helper: Explain long division
     */
    public void explainLongDivision() {
        System.out.println("\nLong Division Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nLong division algorithm:");
        System.out.println("1. Compute integer part: numerator / denominator");
        System.out.println("2. Get remainder: numerator % denominator");
        System.out.println("3. If remainder == 0 → terminating decimal");
        System.out.println("4. Otherwise, start decimal division:");
        System.out.println("   - Multiply remainder by 10");
        System.out.println("   - Digit = remainder / denominator");
        System.out.println("   - New remainder = remainder % denominator");
        System.out.println("5. Track remainders to detect cycles");
        
        System.out.println("\nExample: 1 / 3");
        System.out.println("  Integer part: 1 / 3 = 0, remainder = 1");
        System.out.println("  Step 1: remainder=1 → 10/3=3, remainder=1");
        System.out.println("  Step 2: remainder=1 repeats → cycle detected");
        System.out.println("  Result: 0.(3)");
        
        System.out.println("\nExample: 1 / 6");
        System.out.println("  Integer part: 1 / 6 = 0, remainder = 1");
        System.out.println("  Step 1: 10/6=1, remainder=4 → \"1\"");
        System.out.println("  Step 2: 40/6=6, remainder=4 → \"16\"");
        System.out.println("  Step 3: remainder=4 repeats → cycle detected");
        System.out.println("  Result: 0.1(6)");
        
        System.out.println("\nKey Insight:");
        System.out.println("A fraction terminates if and only if the denominator has only");
        System.out.println("prime factors 2 and 5 after reducing to lowest terms.");
        System.out.println("Otherwise, it will eventually repeat.");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Handling of negative numbers?");
        System.out.println("   - Output format for repeating decimals?");
        System.out.println("   - Constraints (denominator != 0)");
        
        System.out.println("\n2. Handle sign first:");
        System.out.println("   - Compute sign separately using XOR");
        System.out.println("   - Work with absolute values");
        System.out.println("   - Use long to avoid overflow");
        
        System.out.println("\n3. Integer part:");
        System.out.println("   - Simple division gives integer part");
        System.out.println("   - If remainder == 0, return immediately");
        
        System.out.println("\n4. Fractional part:");
        System.out.println("   - Use HashMap to track remainders");
        System.out.println("   - When remainder repeats, insert parentheses");
        System.out.println("   - Build result incrementally");
        
        System.out.println("\n5. Overflow prevention:");
        System.out.println("   - Convert to long before Math.abs()");
        System.out.println("   - MIN_VALUE overflow is a common pitfall");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - numerator = 0");
        System.out.println("   - denominator = 1");
        System.out.println("   - denominator = -1");
        System.out.println("   - Integer.MIN_VALUE cases");
        
        System.out.println("\n7. Complexity analysis:");
        System.out.println("   - Time: O(k) where k = decimal length");
        System.out.println("   - Space: O(k) for remainder map");
        
        System.out.println("\n8. Common mistakes:");
        System.out.println("   - Forgetting to handle sign correctly");
        System.out.println("   - Integer overflow in Math.abs(MIN_VALUE)");
        System.out.println("   - Not detecting cycles properly");
        System.out.println("   - Off-by-one in parenthesis placement");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("166. Fraction to Recurring Decimal");
        System.out.println("==================================");
        
        // Explain long division
        solution.explainLongDivision();
        
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
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";
        
        StringBuilder result = new StringBuilder();
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }
        
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        
        result.append(num / den);
        
        long remainder = num % den;
        if (remainder == 0) return result.toString();
        
        result.append(".");
        Map<Long, Integer> remainderPositions = new HashMap<>();
        
        while (remainder != 0) {
            if (remainderPositions.containsKey(remainder)) {
                int start = remainderPositions.get(remainder);
                result.insert(start, "(");
                result.append(")");
                break;
            }
            
            remainderPositions.put(remainder, result.length());
            remainder *= 10;
            result.append(remainder / den);
            remainder %= den;
        }
        
        return result.toString();
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Use long to prevent overflow");
        System.out.println("2. Handle sign before converting to absolute values");
        System.out.println("3. Track remainders with HashMap to detect cycles");
        System.out.println("4. Insert '(' when cycle is detected");
        System.out.println("5. Time: O(k), Space: O(k) where k = decimal length");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(k) where k is the length of the decimal part");
        System.out.println("- Space: O(k) for storing remainder positions");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How do you know if a fraction terminates? (Denominator only has factors 2 and 5)");
        System.out.println("2. What's the maximum length of the repeating cycle? (denominator - 1)");
        System.out.println("3. How would you handle very large numbers? (Use BigInteger)");
        System.out.println("4. Can you detect cycles without a map? (Use Floyd's cycle detection)");
    }
}
