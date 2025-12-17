
# Solution.java

```java
import java.util.*;

/**
 * 12. Integer to Roman
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Convert an integer to a Roman numeral.
 * Roman numerals are represented by seven symbols with values:
 * I=1, V=5, X=10, L=50, C=100, D=500, M=1000.
 * Special subtraction cases: IV(4), IX(9), XL(40), XC(90), CD(400), CM(900)
 * 
 * Key Insights:
 * 1. Greedy approach: Use largest possible symbol value first
 * 2. Include subtraction cases in symbol-value mapping
 * 3. Process from largest value (1000) to smallest (1)
 * 4. Maximum number is 3999 (MMMCMXCIX)
 * 
 * Approach (Greedy with Predefined Arrays):
 * 1. Create arrays of values and corresponding symbols
 * 2. For each value from largest to smallest:
 *    - While num >= value, append symbol and subtract value
 * 3. Return built string
 * 
 * Time Complexity: O(1) - Maximum 13 iterations
 * Space Complexity: O(1) - Fixed size arrays
 * 
 * Tags: Hash Table, Math, String
 */

class Solution {
    
    /**
     * Approach 1: Greedy with Arrays (RECOMMENDED)
     * O(1) time, O(1) space
     */
    public String intToRoman(int num) {
        // Define all Roman numeral values and their symbols
        // Include subtraction cases (4, 9, 40, 90, 400, 900)
        int[] values = {
            1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
        };
        String[] symbols = {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
        };
        
        StringBuilder result = new StringBuilder();
        
        // Greedy algorithm: use largest possible value first
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                result.append(symbols[i]);
                num -= values[i];
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 2: Division and Modulo Approach
     * O(1) time, O(1) space
     * Handle each digit position separately
     */
    public String intToRomanDivision(int num) {
        // Arrays for thousands, hundreds, tens, ones
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        
        return thousands[num / 1000] + 
               hundreds[(num % 1000) / 100] + 
               tens[(num % 100) / 10] + 
               ones[num % 10];
    }
    
    /**
     * Approach 3: Using TreeMap (Sorted Map)
     * O(1) time, O(1) space
     * Demonstrates TreeMap usage
     */
    public String intToRomanTreeMap(int num) {
        // Use TreeMap to store values in descending order
        TreeMap<Integer, String> romanMap = new TreeMap<>(Collections.reverseOrder());
        romanMap.put(1000, "M");
        romanMap.put(900, "CM");
        romanMap.put(500, "D");
        romanMap.put(400, "CD");
        romanMap.put(100, "C");
        romanMap.put(90, "XC");
        romanMap.put(50, "L");
        romanMap.put(40, "XL");
        romanMap.put(10, "X");
        romanMap.put(9, "IX");
        romanMap.put(5, "V");
        romanMap.put(4, "IV");
        romanMap.put(1, "I");
        
        StringBuilder result = new StringBuilder();
        
        for (Map.Entry<Integer, String> entry : romanMap.entrySet()) {
            int value = entry.getKey();
            String symbol = entry.getValue();
            
            while (num >= value) {
                result.append(symbol);
                num -= value;
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 4: Recursive Solution
     * O(1) time, O(1) space for recursion depth
     * Demonstrates recursive thinking
     */
    public String intToRomanRecursive(int num) {
        if (num >= 1000) return "M" + intToRomanRecursive(num - 1000);
        if (num >= 900) return "CM" + intToRomanRecursive(num - 900);
        if (num >= 500) return "D" + intToRomanRecursive(num - 500);
        if (num >= 400) return "CD" + intToRomanRecursive(num - 400);
        if (num >= 100) return "C" + intToRomanRecursive(num - 100);
        if (num >= 90) return "XC" + intToRomanRecursive(num - 90);
        if (num >= 50) return "L" + intToRomanRecursive(num - 50);
        if (num >= 40) return "XL" + intToRomanRecursive(num - 40);
        if (num >= 10) return "X" + intToRomanRecursive(num - 10);
        if (num >= 9) return "IX" + intToRomanRecursive(num - 9);
        if (num >= 5) return "V" + intToRomanRecursive(num - 5);
        if (num >= 4) return "IV" + intToRomanRecursive(num - 4);
        if (num >= 1) return "I" + intToRomanRecursive(num - 1);
        return "";
    }
    
    /**
     * Approach 5: Switch Case for Each Digit
     * O(1) time, O(1) space
     * Very explicit and easy to understand
     */
    public String intToRomanSwitch(int num) {
        StringBuilder result = new StringBuilder();
        
        // Handle thousands
        int thousands = num / 1000;
        result.append("M".repeat(thousands));
        num %= 1000;
        
        // Handle hundreds
        int hundreds = num / 100;
        switch (hundreds) {
            case 9: result.append("CM"); break;
            case 8: result.append("DCCC"); break;
            case 7: result.append("DCC"); break;
            case 6: result.append("DC"); break;
            case 5: result.append("D"); break;
            case 4: result.append("CD"); break;
            case 3: result.append("CCC"); break;
            case 2: result.append("CC"); break;
            case 1: result.append("C"); break;
        }
        num %= 100;
        
        // Handle tens
        int tens = num / 10;
        switch (tens) {
            case 9: result.append("XC"); break;
            case 8: result.append("LXXX"); break;
            case 7: result.append("LXX"); break;
            case 6: result.append("LX"); break;
            case 5: result.append("L"); break;
            case 4: result.append("XL"); break;
            case 3: result.append("XXX"); break;
            case 2: result.append("XX"); break;
            case 1: result.append("X"); break;
        }
        num %= 10;
        
        // Handle ones
        switch (num) {
            case 9: result.append("IX"); break;
            case 8: result.append("VIII"); break;
            case 7: result.append("VII"); break;
            case 6: result.append("VI"); break;
            case 5: result.append("V"); break;
            case 4: result.append("IV"); break;
            case 3: result.append("III"); break;
            case 2: result.append("II"); break;
            case 1: result.append("I"); break;
        }
        
        return result.toString();
    }
    
    /**
     * Helper method to visualize the conversion process
     */
    private void visualizeConversion(int num, String approach) {
        System.out.println("\n" + approach + " - Conversion Visualization:");
        System.out.println("Converting: " + num);
        
        if (num < 1 || num > 3999) {
            System.out.println("Error: Roman numerals only valid for 1-3999");
            return;
        }
        
        // Use the greedy approach for visualization
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        String[] descriptions = {
            "One thousand",
            "Nine hundred (CM = 1000-100)",
            "Five hundred",
            "Four hundred (CD = 500-100)",
            "One hundred",
            "Ninety (XC = 100-10)",
            "Fifty",
            "Forty (XL = 50-10)",
            "Ten",
            "Nine (IX = 10-1)",
            "Five",
            "Four (IV = 5-1)",
            "One"
        };
        
        System.out.println("\nStep-by-step conversion:");
        System.out.println("Value | Symbol | Description                | Remaining | Action");
        System.out.println("------|--------|----------------------------|-----------|--------");
        
        StringBuilder result = new StringBuilder();
        int remaining = num;
        
        for (int i = 0; i < values.length; i++) {
            int count = 0;
            while (remaining >= values[i]) {
                count++;
                result.append(symbols[i]);
                remaining -= values[i];
            }
            
            if (count > 0) {
                System.out.printf("%5d | %-6s | %-26s | %9d | Append '%s' %d time(s)%n",
                    values[i], symbols[i], descriptions[i], remaining,
                    symbols[i], count);
            }
        }
        
        System.out.println("\nFinal Result: " + result.toString());
        
        // Show the breakdown
        System.out.println("\nBreakdown:");
        int temp = num;
        for (int i = 0; i < values.length; i++) {
            int count = 0;
            while (temp >= values[i]) {
                count++;
                temp -= values[i];
            }
            if (count > 0) {
                System.out.printf("  %d × %s (%s)", count, symbols[i], descriptions[i]);
                if (i < values.length - 1) System.out.println();
            }
        }
        
        // Validate
        System.out.println("\n\nValidation:");
        System.out.println("Sum of values: " + sumRoman(result.toString()));
        System.out.println("Original number: " + num);
        System.out.println("Match: " + (sumRoman(result.toString()) == num ? "✓" : "✗"));
    }
    
    /**
     * Helper to convert Roman back to integer for validation
     */
    private int sumRoman(String roman) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        
        int total = 0;
        int prev = 0;
        
        for (int i = roman.length() - 1; i >= 0; i--) {
            int current = map.get(roman.charAt(i));
            if (current < prev) {
                total -= current;
            } else {
                total += current;
            }
            prev = current;
        }
        
        return total;
    }
    
    /**
     * Helper to show Roman numeral rules
     */
    private void showRomanRules() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ROMAN NUMERAL RULES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nBasic Symbols:");
        System.out.println("Symbol  Value");
        System.out.println("------  -----");
        System.out.println("   I       1");
        System.out.println("   V       5");
        System.out.println("   X      10");
        System.out.println("   L      50");
        System.out.println("   C     100");
        System.out.println("   D     500");
        System.out.println("   M    1000");
        
        System.out.println("\nSubtraction Rules (Special Cases):");
        System.out.println("Number  Roman  Rule");
        System.out.println("------  -----  --------------------");
        System.out.println("    4     IV    I before V (5-1)");
        System.out.println("    9     IX    I before X (10-1)");
        System.out.println("   40     XL    X before L (50-10)");
        System.out.println("   90     XC    X before C (100-10)");
        System.out.println("  400     CD    C before D (500-100)");
        System.out.println("  900     CM    C before M (1000-100)");
        
        System.out.println("\nGeneral Rules:");
        System.out.println("1. Symbols are written from largest to smallest");
        System.out.println("2. No symbol appears more than 3 times in a row");
        System.out.println("3. Subtraction only for the 6 cases above");
        System.out.println("4. Maximum number: 3999 (MMMCMXCIX)");
        
        System.out.println("\nExamples:");
        System.out.println("Number  Roman        Breakdown");
        System.out.println("------  -----------  -------------------------");
        System.out.println("    3   III          I + I + I");
        System.out.println("    8   VIII         V + I + I + I");
        System.out.println("   14   XIV          X + IV (not XIIII)");
        System.out.println("   49   XLIX         XL + IX (not IL)");
        System.out.println("   99   XCIX         XC + IX (not IC)");
        System.out.println("  499   CDXCIX       CD + XC + IX");
        System.out.println(" 1994   MCMXCIV      M + CM + XC + IV");
    }
    
    /**
     * Helper to compare different approaches
     */
    private void compareApproaches(int num) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING DIFFERENT APPROACHES FOR: " + num);
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        String result1, result2, result3, result4, result5;
        
        // Approach 1: Greedy with Arrays
        startTime = System.nanoTime();
        result1 = solution.intToRoman(num);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Division and Modulo
        startTime = System.nanoTime();
        result2 = solution.intToRomanDivision(num);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: TreeMap
        startTime = System.nanoTime();
        result3 = solution.intToRomanTreeMap(num);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Recursive
        startTime = System.nanoTime();
        result4 = solution.intToRomanRecursive(num);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: Switch Case
        startTime = System.nanoTime();
        result5 = solution.intToRomanSwitch(num);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Verify all results are the same
        boolean allEqual = result1.equals(result2) &&
                          result2.equals(result3) &&
                          result3.equals(result4) &&
                          result4.equals(result5);
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (Greedy Arrays):  " + result1);
        System.out.println("Approach 2 (Division):       " + result2);
        System.out.println("Approach 3 (TreeMap):        " + result3);
        System.out.println("Approach 4 (Recursive):      " + result4);
        System.out.println("Approach 5 (Switch):         " + result5);
        
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Approach 1: %-10d (Greedy with Arrays)%n", time1);
        System.out.printf("Approach 2: %-10d (Division and Modulo)%n", time2);
        System.out.printf("Approach 3: %-10d (TreeMap)%n", time3);
        System.out.printf("Approach 4: %-10d (Recursive)%n", time4);
        System.out.printf("Approach 5: %-10d (Switch Case)%n", time5);
        
        // Validate conversion
        System.out.println("\nValidation (Roman → Integer):");
        int convertedBack = sumRoman(result1);
        System.out.println("Original: " + num);
        System.out.println("Converted back: " + convertedBack);
        System.out.println("Match: " + (num == convertedBack ? "✓" : "✗"));
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Integer to Roman Conversion:");
        System.out.println("=====================================");
        
        // Show Roman numeral rules first
        solution.showRomanRules();
        
        // Test case 1: Example from problem
        System.out.println("\n\nTest 1: Basic example (3)");
        int num1 = 3;
        String expected1 = "III";
        
        solution.visualizeConversion(num1, "Test 1");
        
        String result1 = solution.intToRoman(num1);
        System.out.println("\nExpected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + result1.equals(expected1) ? "✓" : "✗");
        
        // Test case 2: Another example
        System.out.println("\n\nTest 2: Example 2 (58)");
        int num2 = 58;
        String expected2 = "LVIII";
        
        solution.visualizeConversion(num2, "Test 2");
        
        String result2 = solution.intToRoman(num2);
        System.out.println("\nExpected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + result2.equals(expected2) ? "✓" : "✗");
        
        // Test case 3: Example with subtraction
        System.out.println("\n\nTest 3: Example 3 (1994)");
        int num3 = 1994;
        String expected3 = "MCMXCIV";
        
        solution.visualizeConversion(num3, "Test 3");
        
        String result3 = solution.intToRoman(num3);
        System.out.println("\nExpected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + result3.equals(expected3) ? "✓" : "✗");
        
        // Test case 4: Edge case - 1
        System.out.println("\n\nTest 4: Minimum value (1)");
        int num4 = 1;
        String expected4 = "I";
        
        solution.visualizeConversion(num4, "Test 4");
        
        String result4 = solution.intToRoman(num4);
        System.out.println("\nExpected: " + expected4);
        System.out.println("Result:   " + result4);
        System.out.println("Passed: " + result4.equals(expected4) ? "✓" : "✗");
        
        // Test case 5: Edge case - 3999 (maximum)
        System.out.println("\n\nTest 5: Maximum value (3999)");
        int num5 = 3999;
        String expected5 = "MMMCMXCIX";
        
        solution.visualizeConversion(num5, "Test 5");
        
        String result5 = solution.intToRoman(num5);
        System.out.println("\nExpected: " + expected5);
        System.out.println("Result:   " + result5);
        System.out.println("Passed: " + result5.equals(expected5) ? "✓" : "✗");
        
        // Test case 6: Number with multiple subtractions
        System.out.println("\n\nTest 6: Complex number (949)");
        int num6 = 949; // CMXLIX
        String expected6 = "CMXLIX";
        
        solution.visualizeConversion(num6, "Test 6");
        
        String result6 = solution.intToRoman(num6);
        System.out.println("\nExpected: " + expected6);
        System.out.println("Result:   " + result6);
        System.out.println("Passed: " + result6.equals(expected6) ? "✓" : "✗");
        
        // Test case 7: Number ending with 4 or 9
        System.out.println("\n\nTest 7: Numbers ending with 4 or 9");
        int[] test7 = {4, 9, 14, 19, 24, 29, 34, 39, 44, 49};
        String[] expected7 = {"IV", "IX", "XIV", "XIX", "XXIV", "XXIX", "XXXIV", "XXXIX", "XLIV", "XLIX"};
        
        for (int i = 0; i < test7.length; i++) {
            String res = solution.intToRoman(test7[i]);
            System.out.printf("%3d → %-6s (Expected: %-6s) %s%n",
                test7[i], res, expected7[i],
                res.equals(expected7[i]) ? "✓" : "✗");
        }
        
        // Compare all approaches for a few numbers
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES:");
        System.out.println("=".repeat(80));
        
        int[] compareNums = {3, 58, 1994, 3999, 949, 777, 123, 888, 2345};
        
        for (int num : compareNums) {
            solution.compareApproaches(num);
        }
        
        // Performance test
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(80));
        
        // Test all numbers from 1 to 1000
        System.out.println("\nTesting numbers 1 to 1000 with different approaches:");
        
        Solution sol = new Solution();
        Random random = new Random(42);
        int testCount = 1000;
        int[] testNumbers = new int[testCount];
        for (int i = 0; i < testCount; i++) {
            testNumbers[i] = random.nextInt(3999) + 1;
        }
        
        // Approach 1: Greedy with Arrays
        long startTime = System.currentTimeMillis();
        for (int n : testNumbers) {
            sol.intToRoman(n);
        }
        long time1 = System.currentTimeMillis() - startTime;
        
        // Approach 2: Division and Modulo
        startTime = System.currentTimeMillis();
        for (int n : testNumbers) {
            sol.intToRomanDivision(n);
        }
        long time2 = System.currentTimeMillis() - startTime;
        
        // Approach 3: TreeMap (slowest due to TreeMap overhead)
        startTime = System.currentTimeMillis();
        for (int n : testNumbers) {
            sol.intToRomanTreeMap(n);
        }
        long time3 = System.currentTimeMillis() - startTime;
        
        // Approach 4: Recursive
        startTime = System.currentTimeMillis();
        for (int n : testNumbers) {
            sol.intToRomanRecursive(n);
        }
        long time4 = System.currentTimeMillis() - startTime;
        
        // Approach 5: Switch Case
        startTime = System.currentTimeMillis();
        for (int n : testNumbers) {
            sol.intToRomanSwitch(n);
        }
        long time5 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance for " + testCount + " conversions (milliseconds):");
        System.out.printf("Approach 1 (Greedy Arrays):  %5d ms%n", time1);
        System.out.printf("Approach 2 (Division):       %5d ms%n", time2);
        System.out.printf("Approach 3 (TreeMap):        %5d ms%n", time3);
        System.out.printf("Approach 4 (Recursive):      %5d ms%n", time4);
        System.out.printf("Approach 5 (Switch):         %5d ms%n", time5);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nGreedy Algorithm Principle:");
        System.out.println("1. Start with largest Roman numeral value (1000 = M)");
        System.out.println("2. If number >= value, append symbol and subtract value");
        System.out.println("3. Repeat until number < value");
        System.out.println("4. Move to next smaller value");
        System.out.println("5. Continue until number becomes 0");
        
        System.out.println("\nWhy Greedy Works:");
        System.out.println("- Roman numerals are additive");
        System.out.println("- Largest possible symbol should be used first");
        System.out.println("- This matches the 'largest to smallest' writing rule");
        System.out.println("- Subtraction cases are included as separate values");
        
        System.out.println("\nExample: Convert 1994 to Roman");
        System.out.println("Step 1: 1994 >= 1000? Yes → Append 'M', subtract 1000 → 994");
        System.out.println("Step 2: 994 >= 900? Yes → Append 'CM', subtract 900 → 94");
        System.out.println("Step 3: 94 >= 90? Yes → Append 'XC', subtract 90 → 4");
        System.out.println("Step 4: 4 >= 4? Yes → Append 'IV', subtract 4 → 0");
        System.out.println("Result: M + CM + XC + IV = MCMXCIV");
        
        // Edge cases and common mistakes
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMMON MISTAKES AND EDGE CASES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Incorrect subtraction handling:");
        System.out.println("   Wrong: 4 = IIII (should be IV)");
        System.out.println("   Wrong: 40 = XXXX (should be XL)");
        System.out.println("   Wrong: 90 = LXXXX (should be XC)");
        
        System.out.println("\n2. Incorrect ordering:");
        System.out.println("   Wrong: 19 = IXX (should be XIX)");
        System.out.println("   Wrong: 99 = IC (should be XCIX)");
        System.out.println("   Wrong: 499 = ID (should be CDXCIX)");
        
        System.out.println("\n3. Boundary values:");
        System.out.println("   Minimum: 1 = I");
        System.out.println("   Maximum: 3999 = MMMCMXCIX");
        System.out.println("   Zero: Not represented in Roman numerals");
        
        System.out.println("\n4. Common test cases:");
        System.out.println("   4, 9, 40, 90, 400, 900 → Subtraction cases");
        System.out.println("   8, 18, 28, 38 → All use additive notation");
        System.out.println("   49, 94, 99, 449, 494, 949 → Multiple subtractions");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Historical Documents:");
        System.out.println("   - Reading ancient Roman texts");
        System.out.println("   - Historical date representation");
        System.out.println("   - Monument inscriptions");
        
        System.out.println("\n2. Clock Faces:");
        System.out.println("   - Traditional clock Roman numerals");
        System.out.println("   - Watch design");
        System.out.println("   - Decorative elements");
        
        System.out.println("\n3. Book Chapters and Outlines:");
        System.out.println("   - Chapter numbering");
        System.out.println("   - Legal document sections");
        System.out.println("   - Academic paper organization");
        
        System.out.println("\n4. Movie and TV Production:");
        System.out.println("   - Copyright year display");
        System.out.println("   - Film sequel numbering");
        System.out.println("   - Title sequences");
        
        System.out.println("\n5. Education:");
        System.out.println("   - Teaching number systems");
        System.out.println("   - Math history lessons");
        System.out.println("   - Cross-cultural studies");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand Roman numeral rules:");
        System.out.println("   - Basic symbols and values");
        System.out.println("   - Subtraction rules (IV, IX, XL, XC, CD, CM)");
        System.out.println("   - Maximum number is 3999");
        
        System.out.println("\n2. Clarify problem constraints:");
        System.out.println("   - Input range: 1 to 3999");
        System.out.println("   - No need to validate input");
        
        System.out.println("\n3. Think about approaches:");
        System.out.println("   - Brute force: Hardcode all numbers (not feasible)");
        System.out.println("   - Greedy: Use largest possible values first");
        System.out.println("   - Digit-based: Handle thousands, hundreds, tens, ones separately");
        
        System.out.println("\n4. Choose optimal approach:");
        System.out.println("   - Greedy with arrays is simplest and efficient");
        System.out.println("   - O(1) time and space");
        
        System.out.println("\n5. Explain algorithm:");
        System.out.println("   - Create arrays of values and symbols");
        System.out.println("   - Include subtraction cases");
        System.out.println("   - Iterate and greedily subtract largest values");
        
        System.out.println("\n6. Walk through example:");
        System.out.println("   - Use 1994 as example");
        System.out.println("   - Show step-by-step conversion");
        
        System.out.println("\n7. Handle edge cases:");
        System.out.println("   - Minimum (1) and maximum (3999)");
        System.out.println("   - Numbers with multiple subtractions");
        System.out.println("   - Round numbers (10, 100, 1000)");
        
        System.out.println("\n8. Discuss alternatives:");
        System.out.println("   - Digit-based approach (division/modulo)");
        System.out.println("   - Recursive approach");
        System.out.println("   - TreeMap approach");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Greedy algorithm is optimal for this problem");
        System.out.println("- Include subtraction cases in value-symbol mapping");
        System.out.println("- O(1) time complexity (bounded by constant number of symbols)");
        System.out.println("- Simple, readable, and efficient");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting subtraction cases");
        System.out.println("- Incorrect subtraction combinations (e.g., IL for 49)");
        System.out.println("- Not handling maximum value correctly");
        System.out.println("- Using inefficient brute force approach");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 13. Roman to Integer (Reverse problem)");
        System.out.println("2. 273. Integer to English Words (Similar concept)");
        System.out.println("3. 166. Fraction to Recurring Decimal (Number conversion)");
        System.out.println("4. 168. Excel Sheet Column Title (Base-26 conversion)");
        System.out.println("5. 171. Excel Sheet Column Number (Reverse of 168)");
        System.out.println("6. 504. Base 7 (Number base conversion)");
        System.out.println("7. 405. Convert a Number to Hexadecimal");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
