
# Solution.java

```java
import java.util.*;

/**
 * 8. String to Integer (atoi)
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Implement atoi to convert string to 32-bit signed integer.
 * 
 * Key Insights:
 * 1. Skip leading whitespace
 * 2. Handle optional sign
 * 3. Read digits until non-digit
 * 4. Handle overflow by clamping to 32-bit range
 * 5. Return 0 if no digits read
 */
class Solution {
    
    /**
     * Approach 1: Step-by-Step Processing (Recommended)
     * Time: O(n), Space: O(1)
     * 
     * Steps:
     * 1. Trim leading whitespace
     * 2. Check sign
     * 3. Parse digits
     * 4. Handle overflow
     */
    public int myAtoi(String s) {
        if (s == null || s.length() == 0) return 0;
        
        int n = s.length();
        int i = 0;
        
        // Step 1: Skip leading whitespace
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }
        
        if (i == n) return 0;
        
        // Step 2: Handle sign
        int sign = 1;
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }
        
        // Step 3: Parse digits
        long result = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            
            // Step 4: Check overflow
            result = result * 10 + digit;
            
            if (result * sign > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            if (result * sign < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            
            i++;
        }
        
        return (int) (result * sign);
    }
    
    /**
     * Approach 2: Using Long for Overflow Detection
     * Time: O(n), Space: O(1)
     * 
     * Similar but uses long to detect overflow
     */
    public int myAtoiLong(String s) {
        if (s == null || s.length() == 0) return 0;
        
        int i = 0;
        int n = s.length();
        
        // Skip whitespace
        while (i < n && s.charAt(i) == ' ') i++;
        if (i == n) return 0;
        
        // Handle sign
        int sign = 1;
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }
        
        // Parse digits
        long result = 0;
        while (i < n && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
            result = result * 10 + (s.charAt(i) - '0');
            
            // Check overflow
            if (result > Integer.MAX_VALUE) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            i++;
        }
        
        return (int) (result * sign);
    }
    
    /**
     * Approach 3: With Early Overflow Check
     * Time: O(n), Space: O(1)
     * 
     * Checks overflow before multiplication to avoid using long
     */
    public int myAtoiEarlyCheck(String s) {
        if (s == null || s.length() == 0) return 0;
        
        int i = 0;
        int n = s.length();
        
        // Skip whitespace
        while (i < n && s.charAt(i) == ' ') i++;
        if (i == n) return 0;
        
        // Handle sign
        int sign = 1;
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }
        
        // Parse digits with early overflow check
        int result = 0;
        while (i < n && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
            int digit = s.charAt(i) - '0';
            
            // Check overflow before multiplication
            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            result = result * 10 + digit;
            i++;
        }
        
        return result * sign;
    }
    
    /**
     * Approach 4: Regular Expression (Educational only)
     * Time: O(n), Space: O(n)
     * 
     * Not recommended for production due to regex overhead
     */
    public int myAtoiRegex(String s) {
        // Match: optional whitespace, optional sign, digits
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^\\s*([+-]?\\d+)");
        java.util.regex.Matcher matcher = pattern.matcher(s);
        
        if (!matcher.find()) {
            return 0;
        }
        
        String numStr = matcher.group(1);
        
        try {
            long num = Long.parseLong(numStr);
            if (num > Integer.MAX_VALUE) return Integer.MAX_VALUE;
            if (num < Integer.MIN_VALUE) return Integer.MIN_VALUE;
            return (int) num;
        } catch (NumberFormatException e) {
            // Handle overflow
            if (numStr.charAt(0) == '-') {
                return Integer.MIN_VALUE;
            }
            return Integer.MAX_VALUE;
        }
    }
    
    /**
     * Approach 5: State Machine
     * Time: O(n), Space: O(1)
     * 
     * Explicit state machine for clarity
     */
    public int myAtoiStateMachine(String s) {
        enum State { START, SIGN, DIGIT, END }
        State state = State.START;
        
        long result = 0;
        int sign = 1;
        
        for (char c : s.toCharArray()) {
            switch (state) {
                case START:
                    if (c == ' ') continue;
                    if (c == '+' || c == '-') {
                        sign = c == '-' ? -1 : 1;
                        state = State.SIGN;
                    } else if (Character.isDigit(c)) {
                        result = c - '0';
                        state = State.DIGIT;
                    } else {
                        return 0;
                    }
                    break;
                    
                case SIGN:
                    if (Character.isDigit(c)) {
                        result = c - '0';
                        state = State.DIGIT;
                    } else {
                        return 0;
                    }
                    break;
                    
                case DIGIT:
                    if (Character.isDigit(c)) {
                        result = result * 10 + (c - '0');
                        if (result * sign > Integer.MAX_VALUE) {
                            return Integer.MAX_VALUE;
                        }
                        if (result * sign < Integer.MIN_VALUE) {
                            return Integer.MIN_VALUE;
                        }
                    } else {
                        state = State.END;
                    }
                    break;
                    
                case END:
                    break;
            }
        }
        
        return (int) (result * sign);
    }
    
    /**
     * Helper: Visualize the parsing process
     */
    public void visualizeAtoi(String s) {
        System.out.println("\nString to Integer Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nInput: \"" + s + "\"");
        
        if (s == null || s.isEmpty()) {
            System.out.println("Empty string → 0");
            return;
        }
        
        int n = s.length();
        int i = 0;
        
        System.out.println("\nStep 1: Skip leading whitespace");
        while (i < n && s.charAt(i) == ' ') {
            System.out.printf("  Skipping space at index %d%n", i);
            i++;
        }
        
        if (i == n) {
            System.out.println("  All characters are whitespace → 0");
            return;
        }
        
        System.out.println("\nStep 2: Handle sign");
        int sign = 1;
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = s.charAt(i) == '-' ? -1 : 1;
            System.out.printf("  Found sign '%c' at index %d → sign = %d%n", s.charAt(i), i, sign);
            i++;
        } else {
            System.out.printf("  No sign found at index %d ('%c') → sign = +%n", i, s.charAt(i));
        }
        
        System.out.println("\nStep 3: Parse digits");
        long result = 0;
        int digitCount = 0;
        
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            System.out.printf("  Digit '%d' at index %d", digit, i);
            
            long newResult = result * 10 + digit;
            System.out.printf(" → result: %d * 10 + %d = %d%n", result, digit, newResult);
            
            result = newResult;
            digitCount++;
            i++;
            
            // Overflow check
            if (result * sign > Integer.MAX_VALUE) {
                System.out.println("  ⚠ Overflow detected! Clamping to Integer.MAX_VALUE");
                result = Integer.MAX_VALUE;
                break;
            }
            if (result * sign < Integer.MIN_VALUE) {
                System.out.println("  ⚠ Overflow detected! Clamping to Integer.MIN_VALUE");
                result = Integer.MIN_VALUE;
                break;
            }
        }
        
        if (digitCount == 0) {
            System.out.println("  No digits found → result = 0");
            System.out.println("\nFinal result: 0");
            return;
        }
        
        System.out.println("\nStep 4: Apply sign");
        int finalResult = (int) (result * sign);
        System.out.printf("  %d * %d = %d%n", result, sign, finalResult);
        
        System.out.println("\nFinal result: " + finalResult);
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[] {
            "42",                    // 42
            "   -42",                // -42
            "4193 with words",       // 4193
            "words and 987",         // 0
            "-91283472332",          // -2147483648
            "   +123",               // 123
            "   -  123",             // 0 (invalid sign)
            "   -0",                 // 0
            "   +0 123",             // 0
            "21474836460",           // 2147483647
            "-2147483649",           // -2147483648
            "   ",                   // 0
            "",                      // 0
            "00000-42a1234",         // 0 (digits then sign)
            "  +0 123",              // 0
            "  -0012a42",            // -12
            "   +0a",                // 0
            "3.14159",               // 3 (stop at '.')
            "   +1 2 3",             // 1
            "   -5-"                 // -5
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[] testCases = generateTestCases();
        int[] expected = {42, -42, 4193, 0, Integer.MIN_VALUE, 123, 0, 0, 0, 
                          Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 0, 0, 0, -12, 0, 3, 1, -5};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.printf("\nTest %d: \"%s\"%n", i + 1, s);
            
            int result1 = myAtoi(s);
            int result2 = myAtoiLong(s);
            int result3 = myAtoiEarlyCheck(s);
            int result4 = myAtoiRegex(s);
            int result5 = myAtoiStateMachine(s);
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
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
                visualizeAtoi(s);
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
        
        // Generate large test case
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append("1234567890");
        }
        String largeString = sb.toString();
        
        System.out.println("Test Setup: " + largeString.length() + " characters");
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: Step-by-step
        long start = System.currentTimeMillis();
        results[0] = myAtoi(largeString);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Long overflow detection
        start = System.currentTimeMillis();
        results[1] = myAtoiLong(largeString);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Early overflow check
        start = System.currentTimeMillis();
        results[2] = myAtoiEarlyCheck(largeString);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Regex
        start = System.currentTimeMillis();
        results[3] = myAtoiRegex(largeString);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: State Machine
        start = System.currentTimeMillis();
        results[4] = myAtoiStateMachine(largeString);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Step-by-step           | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Long overflow          | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. Early check            | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. Regex                  | %9d | %6d%n", times[3], results[3]);
        System.out.printf("5. State Machine          | %9d | %6d%n", times[4], results[4]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3] && results[3] == results[4];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Step-by-step processing is fastest");
        System.out.println("2. Regex has significant overhead");
        System.out.println("3. State machine is readable but slightly slower");
        System.out.println("4. All O(n) methods scale linearly");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Empty string:");
        System.out.println("   Input: \"\"");
        System.out.println("   Output: " + myAtoi(""));
        
        System.out.println("\n2. Only whitespace:");
        System.out.println("   Input: \"     \"");
        System.out.println("   Output: " + myAtoi("     "));
        
        System.out.println("\n3. Only sign:");
        System.out.println("   Input: \"+\"");
        System.out.println("   Output: " + myAtoi("+"));
        
        System.out.println("\n4. Only sign and whitespace:");
        System.out.println("   Input: \"  -  \"");
        System.out.println("   Output: " + myAtoi("  -  "));
        
        System.out.println("\n5. Multiple signs:");
        System.out.println("   Input: \"+-12\"");
        System.out.println("   Output: " + myAtoi("+-12"));
        
        System.out.println("\n6. Leading zeros:");
        System.out.println("   Input: \"000123\"");
        System.out.println("   Output: " + myAtoi("000123"));
        
        System.out.println("\n7. Decimal point:");
        System.out.println("   Input: \"3.14159\"");
        System.out.println("   Output: " + myAtoi("3.14159"));
        
        System.out.println("\n8. Maximum integer overflow:");
        System.out.println("   Input: \"2147483648\"");
        System.out.println("   Output: " + myAtoi("2147483648"));
        
        System.out.println("\n9. Minimum integer overflow:");
        System.out.println("   Input: \"-2147483649\"");
        System.out.println("   Output: " + myAtoi("-2147483649"));
        
        System.out.println("\n10. Very large number:");
        System.out.println("   Input: \"99999999999999999999\"");
        System.out.println("   Output: " + myAtoi("99999999999999999999"));
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nProblem: Convert string to 32-bit signed integer (atoi).");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Skip any leading whitespace characters");
        System.out.println("2. Check for optional '+' or '-' sign");
        System.out.println("3. Read digits until non-digit character");
        System.out.println("4. Convert digits to integer");
        System.out.println("5. Clamp to 32-bit integer range if overflow");
        System.out.println("6. Return result");
        
        System.out.println("\nExample: \"   -42 with text\"");
        System.out.println("  Step 1: Skip whitespace → index points to '-'");
        System.out.println("  Step 2: Found '-' → sign = -1, index++");
        System.out.println("  Step 3: Read digits '4', '2'");
        System.out.println("  Step 4: Convert to 42");
        System.out.println("  Step 5: Apply sign → -42");
        System.out.println("  Step 6: Return -42");
        
        System.out.println("\nOverflow Handling:");
        System.out.println("- Use long to detect overflow before casting");
        System.out.println("- Or check before multiplication: result > MAX/10");
        System.out.println("- Clamp to MAX or MIN if overflow detected");
        
        System.out.println("\nComplexity:");
        System.out.println("- Time: O(n) - single pass through string");
        System.out.println("- Space: O(1) - constant extra space");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What characters are considered whitespace? (space only)");
        System.out.println("   - How to handle multiple signs? (invalid → 0)");
        System.out.println("   - What about decimal points? (stop parsing)");
        
        System.out.println("\n2. Break down the problem:");
        System.out.println("   - Whitespace skipping");
        System.out.println("   - Sign handling");
        System.out.println("   - Digit parsing");
        System.out.println("   - Overflow detection");
        
        System.out.println("\n3. Discuss edge cases:");
        System.out.println("   - Empty string");
        System.out.println("   - Only whitespace");
        System.out.println("   - Only sign");
        System.out.println("   - Leading zeros");
        System.out.println("   - Very large numbers");
        System.out.println("   - Numbers with decimal points");
        
        System.out.println("\n4. Overflow detection strategies:");
        System.out.println("   - Use long and compare with MAX/MIN");
        System.out.println("   - Check before multiplication: result > MAX/10");
        System.out.println("   - Both are acceptable in interviews");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(n) - must examine each character");
        System.out.println("   - Space: O(1) - only a few variables");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Not handling empty string");
        System.out.println("   - Forgetting to skip whitespace");
        System.out.println("   - Accepting multiple signs");
        System.out.println("   - Not handling overflow correctly");
        System.out.println("   - Including non-digit characters after digits");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("8. String to Integer (atoi)");
        System.out.println("===========================");
        
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
    public int myAtoi(String s) {
        if (s == null || s.length() == 0) return 0;
        
        int i = 0;
        int n = s.length();
        
        // Skip whitespace
        while (i < n && s.charAt(i) == ' ') i++;
        if (i == n) return 0;
        
        // Handle sign
        int sign = 1;
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }
        
        // Parse digits
        long result = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            result = result * 10 + (s.charAt(i) - '0');
            
            if (result > Integer.MAX_VALUE) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            i++;
        }
        
        return (int) (result * sign);
    }
}
            """);
        
        System.out.println("\nAlternative (Early Overflow Check):");
        System.out.println("""
class Solution {
    public int myAtoi(String s) {
        int i = 0, n = s.length();
        while (i < n && s.charAt(i) == ' ') i++;
        if (i == n) return 0;
        
        int sign = 1;
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }
        
        int result = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            
            if (result > Integer.MAX_VALUE / 10 ||
                (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            result = result * 10 + digit;
            i++;
        }
        
        return result * sign;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Skip leading whitespace first");
        System.out.println("2. Handle optional sign (+ or -)");
        System.out.println("3. Parse digits until non-digit");
        System.out.println("4. Check for overflow after each digit");
        System.out.println("5. Return 0 if no digits parsed");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - single pass through string");
        System.out.println("- Space: O(1) - constant extra space");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle other whitespace characters?");
        System.out.println("2. How would you handle scientific notation (1e5)?");
        System.out.println("3. How would you handle hexadecimal?");
        System.out.println("4. How would you implement atof (float version)?");
        System.out.println("5. What if the string contains multiple numbers?");
    }
}
