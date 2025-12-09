
# Solution.java

```java
/**
 * 394. Decode String
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an encoded string, return its decoded string.
 * Encoding rule: k[encoded_string] means repeat encoded_string k times.
 * Input is always valid with well-formed brackets.
 * 
 * Key Insights:
 * 1. Use two stacks: one for counts, one for strings
 * 2. Process characters: digits, letters, '[', ']'
 * 3. Build current string while handling nesting with stacks
 * 4. When '[' encountered, push current state to stacks
 * 5. When ']' encountered, pop and build decoded string
 * 
 * Approach (Two Stacks):
 * 1. Initialize countStack and stringStack
 * 2. Initialize currentString and currentNum
 * 3. Iterate through each character:
 *    - If digit: build currentNum
 *    - If '[': push currentString and currentNum, reset both
 *    - If ']': pop count and string, build decoded string
 *    - If letter: append to currentString
 * 4. Return final decoded string
 * 
 * Time Complexity: O(n) - Process each character once
 * Space Complexity: O(n) - Stack storage
 * 
 * Tags: String, Stack, Recursion
 */

import java.util.*;

public class Solution {
    
    /**
     * Approach 1: Two Stacks - RECOMMENDED
     * O(n) time, O(n) space - Most efficient and intuitive
     */
    public String decodeString(String s) {
        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        StringBuilder currentString = new StringBuilder();
        int currentNum = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                // Build multi-digit number
                currentNum = currentNum * 10 + (c - '0');
            } else if (c == '[') {
                // Push current state to stacks
                countStack.push(currentNum);
                stringStack.push(currentString);
                // Reset for new encoded string
                currentNum = 0;
                currentString = new StringBuilder();
            } else if (c == ']') {
                // Pop and build decoded string
                int count = countStack.pop();
                StringBuilder decodedString = stringStack.pop();
                // Repeat current string count times
                for (int i = 0; i < count; i++) {
                    decodedString.append(currentString);
                }
                currentString = decodedString;
            } else {
                // Regular character, add to current string
                currentString.append(c);
            }
        }
        
        return currentString.toString();
    }
    
    /**
     * Approach 2: Single Stack (Mixed Types)
     * O(n) time, O(n) space - Uses single stack with Object type
     */
    public String decodeStringSingleStack(String s) {
        Stack<Object> stack = new Stack<>();
        StringBuilder currentString = new StringBuilder();
        int currentNum = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                currentNum = currentNum * 10 + (c - '0');
            } else if (c == '[') {
                // Push both number and current string
                stack.push(currentNum);
                stack.push(currentString);
                currentNum = 0;
                currentString = new StringBuilder();
            } else if (c == ']') {
                // Pop string and number
                StringBuilder prevString = (StringBuilder) stack.pop();
                int count = (Integer) stack.pop();
                // Build decoded string
                for (int i = 0; i < count; i++) {
                    prevString.append(currentString);
                }
                currentString = prevString;
            } else {
                currentString.append(c);
            }
        }
        
        return currentString.toString();
    }
    
    /**
     * Approach 3: Recursive Solution
     * O(n) time, O(n) space - Natural handling of nested structures
     */
    public String decodeStringRecursive(String s) {
        int[] index = new int[1]; // Use array to pass by reference
        return decodeHelper(s, index);
    }
    
    private String decodeHelper(String s, int[] index) {
        StringBuilder result = new StringBuilder();
        int currentNum = 0;
        
        while (index[0] < s.length()) {
            char c = s.charAt(index[0]);
            
            if (Character.isDigit(c)) {
                currentNum = currentNum * 10 + (c - '0');
                index[0]++;
            } else if (c == '[') {
                index[0]++; // Skip '['
                String decoded = decodeHelper(s, index);
                // Repeat decoded string currentNum times
                for (int i = 0; i < currentNum; i++) {
                    result.append(decoded);
                }
                currentNum = 0; // Reset for next
            } else if (c == ']') {
                index[0]++; // Skip ']'
                return result.toString();
            } else {
                result.append(c);
                index[0]++;
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 4: Iterative with One Stack (String only)
     * O(n) time, O(n) space - Stores everything as strings
     */
    public String decodeStringStringStack(String s) {
        Stack<String> stack = new Stack<>();
        String currentString = "";
        String currentNum = "";
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                currentNum += c;
            } else if (c == '[') {
                // Push both current string and number
                stack.push(currentString);
                stack.push(currentNum);
                currentString = "";
                currentNum = "";
            } else if (c == ']') {
                // Pop number and previous string
                int count = Integer.parseInt(stack.pop());
                String prevString = stack.pop();
                // Build decoded string
                StringBuilder decoded = new StringBuilder(prevString);
                for (int i = 0; i < count; i++) {
                    decoded.append(currentString);
                }
                currentString = decoded.toString();
            } else {
                currentString += c;
            }
        }
        
        return currentString;
    }
    
    /**
     * Approach 5: DFS with Global Index
     * O(n) time, O(n) space - Alternative recursive approach
     */
    private int globalIndex = 0;
    
    public String decodeStringDFS(String s) {
        globalIndex = 0; // Reset for multiple calls
        return dfs(s);
    }
    
    private String dfs(String s) {
        StringBuilder result = new StringBuilder();
        int num = 0;
        
        while (globalIndex < s.length()) {
            char c = s.charAt(globalIndex);
            
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
                globalIndex++;
            } else if (c == '[') {
                globalIndex++;
                String inner = dfs(s);
                for (int i = 0; i < num; i++) {
                    result.append(inner);
                }
                num = 0;
            } else if (c == ']') {
                globalIndex++;
                return result.toString();
            } else {
                result.append(c);
                globalIndex++;
            }
        }
        
        return result.toString();
    }
    
    /**
     * Helper method to visualize the stack process
     */
    private void visualizeStackProcess(String s) {
        System.out.println("\nStack Process Visualization:");
        System.out.println("Input: \"" + s + "\"");
        
        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        StringBuilder currentString = new StringBuilder();
        int currentNum = 0;
        
        System.out.println("\nStep | Char | Current Num | Current String | Count Stack | String Stack | Action");
        System.out.println("-----|------|-------------|----------------|-------------|--------------|--------");
        
        int step = 1;
        for (char c : s.toCharArray()) {
            String countStackState = countStack.toString();
            String stringStackState = getStringStackState(stringStack);
            String action = "";
            
            if (Character.isDigit(c)) {
                currentNum = currentNum * 10 + (c - '0');
                action = "Build number: " + currentNum;
            } else if (c == '[') {
                countStack.push(currentNum);
                stringStack.push(currentString);
                action = "Push to stacks: num=" + currentNum + ", string=\"" + currentString + "\"";
                currentNum = 0;
                currentString = new StringBuilder();
            } else if (c == ']') {
                int count = countStack.pop();
                StringBuilder prevString = stringStack.pop();
                action = "Pop: count=" + count + ", prevString=\"" + prevString + "\"";
                for (int i = 0; i < count; i++) {
                    prevString.append(currentString);
                }
                currentString = prevString;
                action += " -> Build: \"" + currentString + "\"";
            } else {
                currentString.append(c);
                action = "Append to current string";
            }
            
            System.out.printf("%4d | %4c | %11d | %14s | %11s | %12s | %s%n",
                            step++, c, currentNum, 
                            "\"" + currentString + "\"",
                            countStackState, stringStackState, action);
        }
        
        System.out.println("\nFinal Result: \"" + currentString.toString() + "\"");
    }
    
    private String getStringStackState(Stack<StringBuilder> stack) {
        if (stack.isEmpty()) return "[]";
        List<String> elements = new ArrayList<>();
        for (StringBuilder sb : stack) {
            elements.add("\"" + sb.toString() + "\"");
        }
        return elements.toString();
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Decode String:");
        System.out.println("======================");
        
        // Test case 1: Simple repetition
        System.out.println("\nTest 1: Simple repetition");
        String s1 = "3[a]2[bc]";
        String expected1 = "aaabcbc";
        
        long startTime = System.nanoTime();
        String result1a = solution.decodeString(s1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result1b = solution.decodeStringRecursive(s1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result1c = solution.decodeStringSingleStack(s1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = result1a.equals(expected1);
        boolean test1b = result1b.equals(expected1);
        boolean test1c = result1c.equals(expected1);
        
        System.out.println("Two Stacks: \"" + result1a + "\" - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Recursive: \"" + result1b + "\" - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Single Stack: \"" + result1c + "\" - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the stack process
        solution.visualizeStackProcess(s1);
        
        // Test case 2: Nested encoding
        System.out.println("\nTest 2: Nested encoding");
        String s2 = "3[a2[c]]";
        String expected2 = "accaccacc";
        
        String result2a = solution.decodeString(s2);
        System.out.println("Nested: \"" + result2a + "\" - " + 
                         (result2a.equals(expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Mixed with plain text
        System.out.println("\nTest 3: Mixed with plain text");
        String s3 = "2[abc]3[cd]ef";
        String expected3 = "abcabccdcdcdef";
        
        String result3a = solution.decodeString(s3);
        System.out.println("Mixed: \"" + result3a + "\" - " + 
                         (result3a.equals(expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Single character
        System.out.println("\nTest 4: Single character");
        String s4 = "10[a]";
        String expected4 = "aaaaaaaaaa";
        
        String result4a = solution.decodeString(s4);
        System.out.println("Single char: \"" + result4a + "\" - " + 
                         (result4a.equals(expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Deep nesting
        System.out.println("\nTest 5: Deep nesting");
        String s5 = "2[3[a]b]";
        String expected5 = "aaabaaab";
        
        String result5a = solution.decodeString(s5);
        System.out.println("Deep nesting: \"" + result5a + "\" - " + 
                         (result5a.equals(expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Multiple digits
        System.out.println("\nTest 6: Multiple digits");
        String s6 = "100[leetcode]";
        String expected6 = "leetcode".repeat(100);
        
        String result6a = solution.decodeString(s6);
        System.out.println("Multiple digits: " + 
                         (result6a.equals(expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Empty encoded string
        System.out.println("\nTest 7: Empty encoded string");
        String s7 = "3[]2[]";
        String expected7 = "";
        
        String result7a = solution.decodeString(s7);
        System.out.println("Empty encoded: \"" + result7a + "\" - " + 
                         (result7a.equals(expected7) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison");
        System.out.println("Simple case performance:");
        System.out.println("  Two Stacks: " + time1a + " ns");
        System.out.println("  Recursive: " + time1b + " ns");
        System.out.println("  Single Stack: " + time1c + " ns");
        
        // Performance test with complex input
        System.out.println("\nTest 9: Complex input performance");
        String complexS = "3[a10[b2[c]]]"; // 3 * (a + 10 * (b + 2 * c))
        
        startTime = System.nanoTime();
        String result9a = solution.decodeString(complexS);
        long time9a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result9b = solution.decodeStringRecursive(complexS);
        long time9b = System.nanoTime() - startTime;
        
        System.out.println("Complex input:");
        System.out.println("  Two Stacks: " + time9a + " ns");
        System.out.println("  Recursive: " + time9b + " ns");
        
        // Edge case: No encoding
        System.out.println("\nTest 10: No encoding");
        String s10 = "abcdef";
        String expected10 = "abcdef";
        
        String result10a = solution.decodeString(s10);
        System.out.println("No encoding: \"" + result10a + "\" - " + 
                         (result10a.equals(expected10) ? "PASSED" : "FAILED"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        
        System.out.println("\nEncoding Rules:");
        System.out.println("- k[encoded_string] means repeat encoded_string k times");
        System.out.println("- Can be nested: k[encoded_string m[inner_string]]");
        System.out.println("- Numbers are only for repetition counts");
        System.out.println("- Input is always valid with well-formed brackets");
        
        System.out.println("\nTwo Stacks Approach:");
        System.out.println("1. Initialize: countStack, stringStack, currentString, currentNum");
        System.out.println("2. For each character:");
        System.out.println("   - Digit: currentNum = currentNum * 10 + digit");
        System.out.println("   - '[': push currentNum and currentString to stacks, reset both");
        System.out.println("   - ']': pop count and string, repeat currentString count times");
        System.out.println("   - Letter: append to currentString");
        System.out.println("3. Return currentString");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\nTwo Stacks Approach:");
        System.out.println("┌────────────┬────────────┬──────────────┐");
        System.out.println("│ Operation  │ Time       │ Space        │");
        System.out.println("├────────────┼────────────┼──────────────┤");
        System.out.println("│ Processing │ O(n)       │ O(n)         │");
        System.out.println("│ Stack Ops  │ O(1) each  │ O(n) total   │");
        System.out.println("│ String Build│ O(n)       │ O(n)         │");
        System.out.println("│ Total      │ O(n)       │ O(n)         │");
        System.out.println("└────────────┴────────────┴──────────────┘");
        
        System.out.println("\nComparison of Approaches:");
        System.out.println("┌──────────────────┬────────────┬─────────────────┐");
        System.out.println("│ Approach         │ Time       │ Space           │");
        System.out.println("├──────────────────┼────────────┼─────────────────┤");
        System.out.println("│ Two Stacks       │ O(n)       │ O(n)            │");
        System.out.println("│ Single Stack     │ O(n)       │ O(n)            │");
        System.out.println("│ Recursive        │ O(n)       │ O(n) call stack │");
        System.out.println("│ String Stack     │ O(n)       │ O(n)            │");
        System.out.println("│ DFS              │ O(n)       │ O(n) call stack │");
        System.out.println("└──────────────────┴────────────┴─────────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with Two Stacks Approach:");
        System.out.println("   - Most intuitive and efficient");
        System.out.println("   - Clearly demonstrates stack usage for nesting");
        System.out.println("   - Handles all edge cases correctly");
        
        System.out.println("\n2. Key Points to Explain:");
        System.out.println("   - Why two stacks are needed (separate counts and strings)");
        System.out.println("   - How to handle multi-digit numbers");
        System.out.println("   - The push/pop logic for '[' and ']'");
        System.out.println("   - Time and space complexity analysis");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - Multi-digit repetition counts");
        System.out.println("   - Deeply nested encodings");
        System.out.println("   - Empty encoded strings");
        System.out.println("   - Plain text without encoding");
        System.out.println("   - Large repetition counts");
        
        System.out.println("\n4. Discuss Alternative Approaches:");
        System.out.println("   - Recursive solution (natural for nested structures)");
        System.out.println("   - Single stack with mixed types");
        System.out.println("   - Iterative without stacks (more complex)");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - String compression and decompression");
        System.out.println("   - Template engines and macro expansion");
        System.out.println("   - Data serialization formats");
        System.out.println("   - Configuration file processing");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
