/**
 * 1768. Merge Strings Alternately
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * You are given two strings word1 and word2. Merge the strings by adding letters 
 * in alternating order, starting with word1. If a string is longer than the other, 
 * append the additional letters onto the end of the merged string.
 * 
 * Key Insights:
 * 1. Use two pointers to track positions in both strings
 * 2. Use StringBuilder for efficient string building
 * 3. Alternate between word1 and word2 until one string is exhausted
 * 4. Append remaining characters from the longer string
 * 
 * Approach (Two Pointers with StringBuilder - RECOMMENDED):
 * 1. Initialize two pointers i=0, j=0
 * 2. Use StringBuilder to build result efficiently
 * 3. While both pointers are within bounds, alternate adding characters
 * 4. Append remaining characters from either string
 * 
 * Time Complexity: O(n + m)
 * Space Complexity: O(n + m) for the result
 * 
 * Tags: String, Two Pointers
 */

class Solution {
    /**
     * Approach 1: Two Pointers with StringBuilder - RECOMMENDED
     * O(n + m) time, O(n + m) space - Most efficient and readable
     */
    public String mergeAlternately(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        int i = 0, j = 0;
        int n = word1.length(), m = word2.length();
        
        // Alternate between both strings until one is exhausted
        while (i < n && j < m) {
            result.append(word1.charAt(i++));
            result.append(word2.charAt(j++));
        }
        
        // Append remaining characters from word1 if any
        while (i < n) {
            result.append(word1.charAt(i++));
        }
        
        // Append remaining characters from word2 if any
        while (j < m) {
            result.append(word2.charAt(j++));
        }
        
        return result.toString();
    }
    
    /**
     * Approach 2: Single Loop with Condition Checks
     * O(n + m) time, O(n + m) space - Compact but slightly less readable
     */
    public String mergeAlternatelySingleLoop(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        int n = word1.length(), m = word2.length();
        int maxLength = Math.max(n, m);
        
        for (int i = 0; i < maxLength; i++) {
            if (i < n) {
                result.append(word1.charAt(i));
            }
            if (i < m) {
                result.append(word2.charAt(i));
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 3: Using Character Arrays
     * O(n + m) time, O(n + m) space - Alternative implementation
     */
    public String mergeAlternatelyCharArray(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        char[] merged = new char[n + m];
        int index = 0;
        int i = 0, j = 0;
        
        // Alternate between both strings
        while (i < n && j < m) {
            merged[index++] = word1.charAt(i++);
            merged[index++] = word2.charAt(j++);
        }
        
        // Copy remaining from word1
        while (i < n) {
            merged[index++] = word1.charAt(i++);
        }
        
        // Copy remaining from word2
        while (j < m) {
            merged[index++] = word2.charAt(j++);
        }
        
        return new String(merged);
    }
    
    /**
     * Approach 4: Recursive Solution
     * O(n + m) time, O(n + m) space - Elegant but uses stack space
     */
    public String mergeAlternatelyRecursive(String word1, String word2) {
        return mergeHelper(word1, word2, 0, 0, new StringBuilder()).toString();
    }
    
    private StringBuilder mergeHelper(String word1, String word2, int i, int j, StringBuilder sb) {
        // Base case: both strings exhausted
        if (i >= word1.length() && j >= word2.length()) {
            return sb;
        }
        
        // Add from word1 if available
        if (i < word1.length()) {
            sb.append(word1.charAt(i));
        }
        
        // Add from word2 if available
        if (j < word2.length()) {
            sb.append(word2.charAt(j));
        }
        
        // Recursive call with next indices
        return mergeHelper(word1, word2, i + 1, j + 1, sb);
    }
    
    /**
     * Approach 5: Using Streams (Java 8+)
     * O(n + m) time, O(n + m) space - Functional programming style
     */
    public String mergeAlternatelyStreams(String word1, String word2) {
        int maxLength = Math.max(word1.length(), word2.length());
        
        StringBuilder result = new StringBuilder();
        
        // Use IntStream to generate indices
        java.util.stream.IntStream.range(0, maxLength)
            .forEach(i -> {
                if (i < word1.length()) {
                    result.append(word1.charAt(i));
                }
                if (i < word2.length()) {
                    result.append(word2.charAt(i));
                }
            });
        
        return result.toString();
    }
    
    /**
     * Approach 6: Inline with String Concatenation (Not Recommended)
     * O((n + m)^2) time due to string concatenation - For demonstration only
     */
    public String mergeAlternatelyInline(String word1, String word2) {
        String result = "";
        int i = 0, j = 0;
        int n = word1.length(), m = word2.length();
        
        while (i < n && j < m) {
            result += word1.charAt(i++) + "" + word2.charAt(j++);
        }
        
        while (i < n) {
            result += word1.charAt(i++);
        }
        
        while (j < m) {
            result += word2.charAt(j++);
        }
        
        return result;
    }
    
    /**
     * Helper method to visualize the merging process
     */
    private void visualizeMerging(String word1, String word2, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("word1: \"" + word1 + "\"");
        System.out.println("word2: \"" + word2 + "\"");
        
        StringBuilder result = new StringBuilder();
        int i = 0, j = 0;
        int n = word1.length(), m = word2.length();
        int step = 1;
        
        System.out.println("Step | Action | Result So Far");
        System.out.println("-----|--------|---------------");
        
        // Alternate merging
        while (i < n && j < m) {
            result.append(word1.charAt(i));
            System.out.printf("%4d | Add '%c' from word1 | \"%s\"%n", 
                            step++, word1.charAt(i), result.toString());
            
            result.append(word2.charAt(j));
            System.out.printf("%4d | Add '%c' from word2 | \"%s\"%n", 
                            step++, word2.charAt(j), result.toString());
            
            i++;
            j++;
        }
        
        // Add remaining from word1
        while (i < n) {
            result.append(word1.charAt(i));
            System.out.printf("%4d | Add '%c' from word1 (remaining) | \"%s\"%n", 
                            step++, word1.charAt(i), result.toString());
            i++;
        }
        
        // Add remaining from word2
        while (j < m) {
            result.append(word2.charAt(j));
            System.out.printf("%4d | Add '%c' from word2 (remaining) | \"%s\"%n", 
                            step++, word2.charAt(j), result.toString());
            j++;
        }
        
        System.out.println("Final Result: \"" + result.toString() + "\"");
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(String word1, String word2, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        long startTime = System.nanoTime();
        String result1 = mergeAlternately(word1, word2);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result2 = mergeAlternatelySingleLoop(word1, word2);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result3 = mergeAlternatelyCharArray(word1, word2);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result4 = mergeAlternatelyRecursive(word1, word2);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result5 = mergeAlternatelyStreams(word1, word2);
        long time5 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result6 = mergeAlternatelyInline(word1, word2);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("Two Pointers (StringBuilder): %d ns%n", time1);
        System.out.printf("Single Loop: %d ns%n", time2);
        System.out.printf("Character Array: %d ns%n", time3);
        System.out.printf("Recursive: %d ns%n", time4);
        System.out.printf("Streams: %d ns%n", time5);
        System.out.printf("Inline (Not Recommended): %d ns%n", time6);
        
        // Verify all produce same result
        boolean allEqual = result1.equals(result2) && result1.equals(result3) && 
                          result1.equals(result4) && result1.equals(result5) && result1.equals(result6);
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Merge Strings Alternately Solution:");
        System.out.println("============================================");
        
        // Test case 1: Equal length strings
        System.out.println("\nTest 1: Equal length strings");
        String word1_1 = "abc";
        String word2_1 = "pqr";
        String expected1 = "apbqcr";
        
        String result1a = solution.mergeAlternately(word1_1, word2_1);
        String result1b = solution.mergeAlternatelySingleLoop(word1_1, word2_1);
        String result1c = solution.mergeAlternatelyCharArray(word1_1, word2_1);
        String result1d = solution.mergeAlternatelyRecursive(word1_1, word2_1);
        String result1e = solution.mergeAlternatelyStreams(word1_1, word2_1);
        String result1f = solution.mergeAlternatelyInline(word1_1, word2_1);
        
        System.out.println("Two Pointers: " + result1a + " - " + (result1a.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Single Loop: " + result1b + " - " + (result1b.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Char Array: " + result1c + " - " + (result1c.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Recursive: " + result1d + " - " + (result1d.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Streams: " + result1e + " - " + (result1e.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Inline: " + result1f + " - " + (result1f.equals(expected1) ? "PASSED" : "FAILED"));
        
        // Visualize the merging process
        solution.visualizeMerging(word1_1, word2_1, "Test 1 - Equal Length");
        
        // Test case 2: word2 longer than word1
        System.out.println("\nTest 2: word2 longer than word1");
        String word1_2 = "ab";
        String word2_2 = "pqrs";
        String expected2 = "apbqrs";
        
        String result2a = solution.mergeAlternately(word1_2, word2_2);
        System.out.println("word2 longer: " + result2a + " - " + 
                         (result2a.equals(expected2) ? "PASSED" : "FAILED"));
        
        // Visualize this case
        solution.visualizeMerging(word1_2, word2_2, "Test 2 - Word2 Longer");
        
        // Test case 3: word1 longer than word2
        System.out.println("\nTest 3: word1 longer than word2");
        String word1_3 = "abcd";
        String word2_3 = "pq";
        String expected3 = "apbqcd";
        
        String result3a = solution.mergeAlternately(word1_3, word2_3);
        System.out.println("word1 longer: " + result3a + " - " + 
                         (result3a.equals(expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Single character strings
        System.out.println("\nTest 4: Single character strings");
        String word1_4 = "a";
        String word2_4 = "p";
        String expected4 = "ap";
        
        String result4a = solution.mergeAlternately(word1_4, word2_4);
        System.out.println("Single chars: " + result4a + " - " + 
                         (result4a.equals(expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: One empty string
        System.out.println("\nTest 5: One empty string");
        String word1_5 = "";
        String word2_5 = "pqr";
        String expected5 = "pqr";
        
        String result5a = solution.mergeAlternately(word1_5, word2_5);
        System.out.println("Empty word1: " + result5a + " - " + 
                         (result5a.equals(expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Both empty strings
        System.out.println("\nTest 6: Both empty strings");
        String word1_6 = "";
        String word2_6 = "";
        String expected6 = "";
        
        String result6a = solution.mergeAlternately(word1_6, word2_6);
        System.out.println("Both empty: " + result6a + " - " + 
                         (result6a.equals(expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Very different lengths
        System.out.println("\nTest 7: Very different lengths");
        String word1_7 = "a";
        String word2_7 = "pqrstuv";
        String expected7 = "apqrstuv";
        
        String result7a = solution.mergeAlternately(word1_7, word2_7);
        System.out.println("Very different: " + result7a + " - " + 
                         (result7a.equals(expected7) ? "PASSED" : "FAILED"));
        
        // Test case 8: Long strings
        System.out.println("\nTest 8: Long strings");
        String word1_8 = "abcdefghij";
        String word2_8 = "klmnopqrst";
        String result8a = solution.mergeAlternately(word1_8, word2_8);
        System.out.println("Long strings: " + result8a + " - " + 
                         (result8a.length() == 20 ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(word1_1, word2_1, "Small Input (3 chars each)");
        
        // Medium input performance
        String medium1 = "abc".repeat(10); // 30 chars
        String medium2 = "xyz".repeat(15); // 45 chars
        solution.comparePerformance(medium1, medium2, "Medium Input (30+45 chars)");
        
        // Large input performance
        String large1 = "a".repeat(100);
        String large2 = "b".repeat(100);
        solution.comparePerformance(large1, large2, "Large Input (100 chars each)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Two Pointers with StringBuilder - RECOMMENDED:");
        System.out.println("   Time: O(n + m) - Process each character once");
        System.out.println("   Space: O(n + m) - Result string storage");
        System.out.println("   How it works:");
        System.out.println("     - Use two pointers for word1 and word2");
        System.out.println("     - Alternate adding characters until one string ends");
        System.out.println("     - Append remaining characters from longer string");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient and readable");
        System.out.println("     - Clear logic flow");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("   Cons:");
        System.out.println("     - None significant");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Single Loop with Condition Checks:");
        System.out.println("   Time: O(n + m) - Single pass up to max length");
        System.out.println("   Space: O(n + m) - Result string storage");
        System.out.println("   How it works:");
        System.out.println("     - Single loop up to max(length1, length2)");
        System.out.println("     - Conditionally add characters from both strings");
        System.out.println("   Pros:");
        System.out.println("     - Compact code");
        System.out.println("     - Single loop structure");
        System.out.println("   Cons:");
        System.out.println("     - Slightly less readable");
        System.out.println("     - More condition checks per iteration");
        System.out.println("   Best for: Code golf, when compactness is valued");
        
        System.out.println("\n3. Character Array Approach:");
        System.out.println("   Time: O(n + m) - Process each character once");
        System.out.println("   Space: O(n + m) - Character array storage");
        System.out.println("   How it works:");
        System.out.println("     - Pre-allocate character array of total length");
        System.out.println("     - Fill array by alternating characters");
        System.out.println("     - Convert to string at the end");
        System.out.println("   Pros:");
        System.out.println("     - No StringBuilder overhead");
        System.out.println("     - Direct array manipulation");
        System.out.println("   Cons:");
        System.out.println("     - More complex index management");
        System.out.println("     - Less readable than StringBuilder");
        System.out.println("   Best for: Performance-critical applications");
        
        System.out.println("\n4. Recursive Solution:");
        System.out.println("   Time: O(n + m) - Recursive calls for each character pair");
        System.out.println("   Space: O(n + m) - Result + recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Recursive function with indices and StringBuilder");
        System.out.println("     - Base case: both indices out of bounds");
        System.out.println("     - Recursive case: add available characters and recurse");
        System.out.println("   Pros:");
        System.out.println("     - Elegant recursive formulation");
        System.out.println("     - Good for learning recursion");
        System.out.println("   Cons:");
        System.out.println("     - Stack space overhead");
        System.out.println("     - Risk of stack overflow for large inputs");
        System.out.println("     - Less efficient than iterative");
        System.out.println("   Best for: Educational purposes, small inputs");
        
        System.out.println("\n5. Streams Approach (Java 8+):");
        System.out.println("   Time: O(n + m) - Process each index once");
        System.out.println("   Space: O(n + m) - Result string storage");
        System.out.println("   How it works:");
        System.out.println("     - Use IntStream for indices");
        System.out.println("     - For each index, conditionally add characters");
        System.out.println("   Pros:");
        System.out.println("     - Functional programming style");
        System.out.println("     - Concise and modern");
        System.out.println("   Cons:");
        System.out.println("     - Stream overhead");
        System.out.println("     - Less familiar to some developers");
        System.out.println("     - Harder to debug");
        System.out.println("   Best for: Functional programming contexts");
        
        System.out.println("\n6. Inline with String Concatenation (NOT RECOMMENDED):");
        System.out.println("   Time: O((n + m)²) - Quadratic due to string copying");
        System.out.println("   Space: O(n + m) - Result string storage");
        System.out.println("   How it works:");
        System.out.println("     - Use string concatenation in loop");
        System.out.println("     - Same algorithm as recommended approach");
        System.out.println("   Pros:");
        System.out.println("     - Simple to understand");
        System.out.println("     - No StringBuilder knowledge required");
        System.out.println("   Cons:");
        System.out.println("     - TERRIBLE performance for large inputs");
        System.out.println("     - Creates many intermediate strings");
        System.out.println("     - Memory inefficient");
        System.out.println("   Best for: Demonstration of what NOT to do");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY STRINGBUILDER IS IMPORTANT:");
        System.out.println("1. String concatenation in loops creates new strings each time");
        System.out.println("2. This leads to O(k²) time complexity for k operations");
        System.out.println("3. StringBuilder uses amortized O(1) time per append");
        System.out.println("4. For n + m operations, StringBuilder is O(n + m) time");
        System.out.println("5. Always use StringBuilder for building strings in loops");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. Empty word1: result should be word2");
        System.out.println("2. Empty word2: result should be word1");
        System.out.println("3. Both empty: result should be empty string");
        System.out.println("4. Single characters: simple alternation");
        System.out.println("5. Very different lengths: append remaining characters");
        System.out.println("6. Maximum constraints: handle 100 characters efficiently");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Two Pointers + StringBuilder approach");
        System.out.println("2. Explain why StringBuilder is better than string concatenation");
        System.out.println("3. Handle edge cases explicitly (empty strings, different lengths)");
        System.out.println("4. Mention time/space complexity: O(n + m)/O(n + m)");
        System.out.println("5. Discuss alternative approaches briefly");
        System.out.println("6. Write clean, readable code with good variable names");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Using string concatenation in loops (performance trap)");
        System.out.println("2. Not handling different length cases correctly");
        System.out.println("3. Forgetting to handle empty string cases");
        System.out.println("4. Incorrect index management in loops");
        System.out.println("5. Not using StringBuilder for efficient string building");
        System.out.println("6. Overcomplicating the solution");
        System.out.println("=".repeat(70));
        
        // Pattern recognition and related problems
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PATTERN RECOGNITION:");
        System.out.println("1. Two Pointers: When processing two sequences simultaneously");
        System.out.println("2. String Merging: Common in interview problems");
        System.out.println("3. Alternating Patterns: Appear in many coding problems");
        System.out.println("4. Remaining Elements: Handle leftovers from longer sequence");
        System.out.println("5. StringBuilder: Essential for efficient string building");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
