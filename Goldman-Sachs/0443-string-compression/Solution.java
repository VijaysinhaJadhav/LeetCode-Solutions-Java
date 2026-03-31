
# Solution.java

```java
import java.util.*;

/**
 * 443. String Compression
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Compress consecutive repeating characters in-place.
 * Single character: keep as is
 * Multiple characters: char + count (count as string)
 * Return new length after compression.
 * 
 * Key Insights:
 * 1. Use two pointers: read pointer (i) and write pointer (write)
 * 2. Find groups of consecutive same characters
 * 3. For each group: write character, then write count if > 1
 * 4. Convert count to string and write each digit
 */
class Solution {
    
    /**
     * Approach 1: Two-Pointer with Group Processing (Recommended)
     * Time: O(n), Space: O(1)
     * 
     * Steps:
     * 1. Initialize write pointer at 0
     * 2. Iterate through array with read pointer
     * 3. For each group, find its length
     * 4. Write character at write position
     * 5. If length > 1, convert to string and write each digit
     * 6. Return write pointer as new length
     */
    public int compress(char[] chars) {
        int write = 0; // Pointer for writing compressed result
        int i = 0; // Pointer for reading input
        
        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 0;
            
            // Count consecutive occurrences
            while (i < chars.length && chars[i] == currentChar) {
                i++;
                count++;
            }
            
            // Write the character
            chars[write] = currentChar;
            write++;
            
            // Write the count if greater than 1
            if (count > 1) {
                // Convert count to string and write each digit
                String countStr = Integer.toString(count);
                for (char c : countStr.toCharArray()) {
                    chars[write] = c;
                    write++;
                }
            }
        }
        
        return write;
    }
    
    /**
     * Approach 2: Two-Pointer with Manual Digit Writing
     * Time: O(n), Space: O(1)
     * 
     * Similar to approach 1, but writes digits manually
     * Avoids creating count string (more efficient)
     */
    public int compressManualDigits(char[] chars) {
        int write = 0;
        int i = 0;
        
        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 0;
            
            while (i < chars.length && chars[i] == currentChar) {
                i++;
                count++;
            }
            
            // Write character
            chars[write] = currentChar;
            write++;
            
            // Write count digits manually
            if (count > 1) {
                int digits = (int) Math.log10(count) + 1;
                // Write digits in reverse order
                for (int j = digits; j > 0; j--) {
                    int divisor = (int) Math.pow(10, j - 1);
                    int digit = count / divisor;
                    chars[write] = (char) ('0' + digit);
                    write++;
                    count %= divisor;
                }
            }
        }
        
        return write;
    }
    
    /**
     * Approach 3: Two-Pointer with StringBuilder (Extra Space)
     * Time: O(n), Space: O(n)
     * 
     * Simpler but uses extra space - not meeting in-place requirement
     */
    public int compressWithStringBuilder(char[] chars) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        
        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 0;
            
            while (i < chars.length && chars[i] == currentChar) {
                i++;
                count++;
            }
            
            result.append(currentChar);
            if (count > 1) {
                result.append(count);
            }
        }
        
        // Copy back to original array
        for (int j = 0; j < result.length(); j++) {
            chars[j] = result.charAt(j);
        }
        
        return result.length();
    }
    
    /**
     * Approach 4: Write digits using stack (alternative method)
     * Time: O(n), Space: O(1)
     * 
     * Uses stack to handle digit writing in correct order
     */
    public int compressWithStack(char[] chars) {
        int write = 0;
        int i = 0;
        
        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 0;
            
            while (i < chars.length && chars[i] == currentChar) {
                i++;
                count++;
            }
            
            chars[write++] = currentChar;
            
            if (count > 1) {
                // Use stack to reverse digits
                Stack<Character> stack = new Stack<>();
                while (count > 0) {
                    stack.push((char) ('0' + (count % 10)));
                    count /= 10;
                }
                while (!stack.isEmpty()) {
                    chars[write++] = stack.pop();
                }
            }
        }
        
        return write;
    }
    
    /**
     * Approach 5: Optimized with char array for digits
     * Time: O(n), Space: O(1)
     * 
     * Pre-allocate digit array for writing
     */
    public int compressOptimized(char[] chars) {
        int write = 0;
        int i = 0;
        
        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 0;
            
            while (i < chars.length && chars[i] == currentChar) {
                i++;
                count++;
            }
            
            chars[write++] = currentChar;
            
            if (count > 1) {
                char[] digits = Integer.toString(count).toCharArray();
                for (char digit : digits) {
                    chars[write++] = digit;
                }
            }
        }
        
        return write;
    }
    
    /**
     * Helper: Visualize the compression process
     */
    public void visualizeCompression(char[] chars) {
        System.out.println("\nString Compression Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nOriginal array: " + Arrays.toString(chars));
        System.out.println("Length: " + chars.length);
        
        System.out.println("\nCompression process:");
        int write = 0;
        int i = 0;
        int step = 1;
        
        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 0;
            
            // Find group
            System.out.printf("\nStep %d: Scanning group starting at index %d%n", step++, i);
            System.out.printf("  Current char: '%c'%n", currentChar);
            
            while (i < chars.length && chars[i] == currentChar) {
                count++;
                i++;
            }
            System.out.printf("  Group length: %d%n", count);
            
            // Write character
            System.out.printf("  Writing '%c' at index %d%n", currentChar, write);
            chars[write] = currentChar;
            write++;
            
            // Write count if > 1
            if (count > 1) {
                String countStr = Integer.toString(count);
                System.out.printf("  Writing count '%s'", countStr);
                for (char c : countStr.toCharArray()) {
                    System.out.printf(" -> writing '%c' at index %d%n", c, write);
                    chars[write] = c;
                    write++;
                }
            } else {
                System.out.println("  Count is 1, skipping count writing");
            }
            
            System.out.print("  Current array state: [");
            for (int j = 0; j < write; j++) {
                System.out.print(chars[j]);
                if (j < write - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
        
        System.out.println("\nFinal compressed array: " + Arrays.toString(Arrays.copyOf(chars, write)));
        System.out.println("New length: " + write);
    }
    
    /**
     * Helper: Generate test cases
     */
    public char[][] generateTestCases() {
        return new char[][] {
            {'a','a','b','b','c','c','c'},           // Example 1
            {'a'},                                    // Example 2
            {'a','b','b','b','b','b','b','b','b','b','b','b','b'}, // Example 3
            {'a','b','c'},                            // All unique
            {'a','a','a','a','a','a','a','a','a','a'}, // 10 a's
            {'a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a'}, // 20 a's
            {'a','a','a','b','b','b','c','c','c'},    // Multiple groups
            {'a','a','b','b','b','b','b','c'},        // Mixed lengths
            {'a','a','a','a','a','b','b','b','b','c','c','d','d','d'}, // Complex
            {'x','x','x','x','x','x','x','x','x','x','y','y'} // 10 x's, 2 y's
        };
    }
    
    /**
     * Helper: Get expected result for test cases
     */
    public String getExpected(char[] chars) {
        StringBuilder expected = new StringBuilder();
        int i = 0;
        
        while (i < chars.length) {
            char current = chars[i];
            int count = 0;
            while (i < chars.length && chars[i] == current) {
                count++;
                i++;
            }
            expected.append(current);
            if (count > 1) {
                expected.append(count);
            }
        }
        
        return expected.toString();
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        char[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            char[] original = testCases[i].clone();
            char[] chars1 = testCases[i].clone();
            char[] chars2 = testCases[i].clone();
            char[] chars3 = testCases[i].clone();
            char[] chars4 = testCases[i].clone();
            char[] chars5 = testCases[i].clone();
            
            System.out.printf("\nTest %d: %s%n", i + 1, Arrays.toString(original));
            
            int len1 = compress(chars1);
            int len2 = compressManualDigits(chars2);
            int len3 = compressWithStringBuilder(chars3);
            int len4 = compressWithStack(chars4);
            int len5 = compressOptimized(chars5);
            
            String expected = getExpected(original);
            String result1 = new String(chars1, 0, len1);
            String result2 = new String(chars2, 0, len2);
            String result3 = new String(chars3, 0, len3);
            String result4 = new String(chars4, 0, len4);
            String result5 = new String(chars5, 0, len5);
            
            boolean allMatch = result1.equals(expected) && result2.equals(expected) &&
                              result3.equals(expected) && result4.equals(expected) &&
                              result5.equals(expected);
            
            if (allMatch) {
                System.out.println("✓ PASS - Compressed: \"" + result1 + "\"");
                System.out.println("  New length: " + len1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: \"" + expected + "\"");
                System.out.println("  Method 1: \"" + result1 + "\" (len: " + len1 + ")");
                System.out.println("  Method 2: \"" + result2 + "\" (len: " + len2 + ")");
                System.out.println("  Method 3: \"" + result3 + "\" (len: " + len3 + ")");
                System.out.println("  Method 4: \"" + result4 + "\" (len: " + len4 + ")");
                System.out.println("  Method 5: \"" + result5 + "\" (len: " + len5 + ")");
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeCompression(original);
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
        System.out.println("=======================");
        
        // Create a large test case
        char[] largeArray = new char[2000];
        Arrays.fill(largeArray, 0, 500, 'a');
        Arrays.fill(largeArray, 500, 1000, 'b');
        Arrays.fill(largeArray, 1000, 1500, 'c');
        Arrays.fill(largeArray, 1500, 2000, 'd');
        
        System.out.println("Test Setup: " + largeArray.length + " characters");
        
        long[] times = new long[5];
        int[] lengths = new int[5];
        
        // Method 1: Two-Pointer with count string
        char[] copy1 = largeArray.clone();
        long start = System.currentTimeMillis();
        lengths[0] = compress(copy1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Manual digits
        char[] copy2 = largeArray.clone();
        start = System.currentTimeMillis();
        lengths[1] = compressManualDigits(copy2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: StringBuilder (extra space)
        char[] copy3 = largeArray.clone();
        start = System.currentTimeMillis();
        lengths[2] = compressWithStringBuilder(copy3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Stack
        char[] copy4 = largeArray.clone();
        start = System.currentTimeMillis();
        lengths[3] = compressWithStack(copy4);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Optimized
        char[] copy5 = largeArray.clone();
        start = System.currentTimeMillis();
        lengths[4] = compressOptimized(copy5);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | New Length");
        System.out.println("--------------------------|-----------|------------");
        System.out.printf("1. Two-Pointer + String   | %9d | %10d%n", times[0], lengths[0]);
        System.out.printf("2. Manual Digits          | %9d | %10d%n", times[1], lengths[1]);
        System.out.printf("3. StringBuilder          | %9d | %10d%n", times[2], lengths[2]);
        System.out.printf("4. Stack                  | %9d | %10d%n", times[3], lengths[3]);
        System.out.printf("5. Optimized              | %9d | %10d%n", times[4], lengths[4]);
        
        boolean allSame = lengths[0] == lengths[1] && lengths[1] == lengths[2] &&
                          lengths[2] == lengths[3] && lengths[3] == lengths[4];
        System.out.println("\nAll methods produce same length: " + (allSame ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. All methods are O(n) time");
        System.out.println("2. In-place methods (1,2,4,5) use O(1) extra space");
        System.out.println("3. StringBuilder uses O(n) extra space");
        System.out.println("4. Manual digits method avoids string conversion");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Single character:");
        char[] test1 = {'a'};
        int len1 = compress(test1.clone());
        System.out.println("   Input: [a]");
        System.out.println("   Output: " + new String(test1, 0, len1) + " (len: " + len1 + ")");
        
        System.out.println("\n2. All unique characters:");
        char[] test2 = {'a','b','c','d','e'};
        int len2 = compress(test2.clone());
        System.out.println("   Input: [a,b,c,d,e]");
        System.out.println("   Output: " + new String(test2, 0, len2) + " (len: " + len2 + ")");
        
        System.out.println("\n3. All same characters (10):");
        char[] test3 = {'a','a','a','a','a','a','a','a','a','a'};
        int len3 = compress(test3.clone());
        System.out.println("   Input: 10 a's");
        System.out.println("   Output: " + new String(test3, 0, len3) + " (len: " + len3 + ")");
        
        System.out.println("\n4. All same characters (20):");
        char[] test4 = new char[20];
        Arrays.fill(test4, 'b');
        int len4 = compress(test4.clone());
        System.out.println("   Input: 20 b's");
        System.out.println("   Output: " + new String(test4, 0, len4) + " (len: " + len4 + ")");
        
        System.out.println("\n5. Mixed with digits and letters:");
        char[] test5 = {'1','1','1','a','a','b','b','b'};
        int len5 = compress(test5.clone());
        System.out.println("   Input: [1,1,1,a,a,b,b,b]");
        System.out.println("   Output: " + new String(test5, 0, len5) + " (len: " + len5 + ")");
        
        System.out.println("\n6. Already compressed:");
        char[] test6 = {'a','1','2','b','1'};
        int len6 = compress(test6.clone());
        System.out.println("   Input: [a,1,2,b,1]");
        System.out.println("   Output: " + new String(test6, 0, len6) + " (len: " + len6 + ")");
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=====================");
        
        System.out.println("\nProblem: Compress consecutive repeating characters in-place.");
        
        System.out.println("\nKey Insight:");
        System.out.println("- Use two pointers: read pointer (i) to scan groups, write pointer (write) to build result");
        System.out.println("- For each group, write the character, then write the count if > 1");
        System.out.println("- Count > 9 requires writing multiple digits");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize write = 0, i = 0");
        System.out.println("2. While i < chars.length:");
        System.out.println("   a. Let currentChar = chars[i]");
        System.out.println("   b. Count consecutive occurrences of currentChar");
        System.out.println("   c. Write currentChar at chars[write] and increment write");
        System.out.println("   d. If count > 1:");
        System.out.println("      - Convert count to string");
        System.out.println("      - Write each digit to chars[write] and increment write");
        System.out.println("3. Return write (new length)");
        
        System.out.println("\nExample: chars = ['a','a','b','b','c','c','c']");
        System.out.println("  Group 1: 'a' appears 2 times → write 'a', then '2'");
        System.out.println("  Group 2: 'b' appears 2 times → write 'b', then '2'");
        System.out.println("  Group 3: 'c' appears 3 times → write 'c', then '3'");
        System.out.println("  Result: ['a','2','b','2','c','3']");
        System.out.println("  New length: 6");
        
        System.out.println("\nComplexity:");
        System.out.println("- Time: O(n) - single pass through array");
        System.out.println("- Space: O(1) - in-place modification");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What characters can appear? (letters, digits, symbols)");
        System.out.println("   - Should we compress if length > 1? (yes)");
        System.out.println("   - In-place means modifying the original array");
        
        System.out.println("\n2. Start with example:");
        System.out.println("   - Walk through the given examples");
        System.out.println("   - Show how groups are identified");
        
        System.out.println("\n3. Explain the two-pointer approach:");
        System.out.println("   - Read pointer: scans for groups");
        System.out.println("   - Write pointer: writes compressed result");
        System.out.println("   - Why this works: write pointer never exceeds read pointer");
        
        System.out.println("\n4. Handle counts > 9:");
        System.out.println("   - Convert to string to get digits");
        System.out.println("   - Write each digit individually");
        System.out.println("   - Example: 12 becomes ['1','2']");
        
        System.out.println("\n5. Discuss edge cases:");
        System.out.println("   - Single character");
        System.out.println("   - All unique characters");
        System.out.println("   - All same characters (long runs)");
        System.out.println("   - Empty array (constraints guarantee at least 1 element)");
        
        System.out.println("\n6. Complexity analysis:");
        System.out.println("   - Time: O(n) - each character processed once");
        System.out.println("   - Space: O(1) - in-place, no extra arrays");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Forgetting to handle counts >= 10");
        System.out.println("   - Writing count before character");
        System.out.println("   - Off-by-one errors in pointers");
        System.out.println("   - Not updating write pointer correctly");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("443. String Compression");
        System.out.println("=======================");
        
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
    public int compress(char[] chars) {
        int write = 0;
        int i = 0;
        
        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 0;
            
            // Count consecutive occurrences
            while (i < chars.length && chars[i] == currentChar) {
                i++;
                count++;
            }
            
            // Write character
            chars[write++] = currentChar;
            
            // Write count if > 1
            if (count > 1) {
                String countStr = Integer.toString(count);
                for (char c : countStr.toCharArray()) {
                    chars[write++] = c;
                }
            }
        }
        
        return write;
    }
}
            """);
        
        System.out.println("\nAlternative (Manual Digits):");
        System.out.println("""
class Solution {
    public int compress(char[] chars) {
        int write = 0;
        int i = 0;
        
        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 0;
            
            while (i < chars.length && chars[i] == currentChar) {
                i++;
                count++;
            }
            
            chars[write++] = currentChar;
            
            if (count > 1) {
                // Write digits manually
                if (count >= 100) {
                    chars[write++] = (char) ('0' + count / 100);
                    count %= 100;
                }
                if (count >= 10) {
                    chars[write++] = (char) ('0' + count / 10);
                    count %= 10;
                }
                chars[write++] = (char) ('0' + count);
            }
        }
        
        return write;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Two-pointer approach is optimal (O(n) time, O(1) space)");
        System.out.println("2. Write pointer never exceeds read pointer (safe in-place)");
        System.out.println("3. Handle counts > 9 by writing each digit separately");
        System.out.println("4. Single characters remain unchanged");
        System.out.println("5. Return the new length after compression");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - single pass through array");
        System.out.println("- Space: O(1) - in-place modification");
        
        System.out.println("\nCommon Interview Variations:");
        System.out.println("1. Compress a string with different compression rules");
        System.out.println("2. Decompress a compressed string");
        System.out.println("3. Count and Say sequence");
        System.out.println("4. String Compression with threshold (min length for compression)");
    }
}
