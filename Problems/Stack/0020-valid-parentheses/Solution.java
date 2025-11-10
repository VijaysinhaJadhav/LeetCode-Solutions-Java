/**
 * 20. Valid Parentheses
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', 
 * determine if the input string is valid.
 * 
 * Key Insights:
 * 1. Use stack to track opening brackets
 * 2. When encountering closing bracket, check if it matches top of stack
 * 3. Use HashMap for efficient bracket matching lookup
 * 4. Stack must be empty at the end for valid string
 * 
 * Approach (Stack with HashMap):
 * 1. Initialize stack and mapping of closing to opening brackets
 * 2. Iterate through each character in string
 * 3. If opening bracket, push to stack
 * 4. If closing bracket, check if stack is empty or top doesn't match
 * 5. Return true only if stack is empty after processing all characters
 * 
 * Time Complexity: O(n) - Process each character once
 * Space Complexity: O(n) - Stack storage in worst case
 * 
 * Tags: String, Stack
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Stack with HashMap - RECOMMENDED
     * O(n) time, O(n) space - Clean and efficient
     */
    public boolean isValid(String s) {
        // Map closing brackets to their corresponding opening brackets
        Map<Character, Character> bracketMap = new HashMap<>();
        bracketMap.put(')', '(');
        bracketMap.put('}', '{');
        bracketMap.put(']', '[');
        
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (bracketMap.containsValue(c)) {
                // Opening bracket - push to stack
                stack.push(c);
            } else if (bracketMap.containsKey(c)) {
                // Closing bracket - check if matches top of stack
                if (stack.isEmpty() || stack.pop() != bracketMap.get(c)) {
                    return false;
                }
            }
            // Note: Input only contains brackets, so no else needed
        }
        
        // Valid if stack is empty (all brackets matched)
        return stack.isEmpty();
    }
    
    /**
     * Approach 2: Stack with Switch Statement
     * O(n) time, O(n) space - Alternative implementation
     */
    public boolean isValidSwitch(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                // Opening bracket - push to stack
                stack.push(c);
            } else {
                // Closing bracket - check if stack is empty
                if (stack.isEmpty()) {
                    return false;
                }
                
                char top = stack.pop();
                // Check if closing bracket matches opening bracket
                if ((c == ')' && top != '(') ||
                    (c == '}' && top != '{') ||
                    (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }
    
    /**
     * Approach 3: Array as Stack (Optimized)
     * O(n) time, O(n) space - Uses array instead of Stack class
     */
    public boolean isValidArray(String s) {
        char[] stack = new char[s.length()];
        int pointer = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                // Opening bracket - push to stack
                stack[pointer++] = c;
            } else {
                // Closing bracket - check if stack is empty
                if (pointer == 0) {
                    return false;
                }
                
                char top = stack[--pointer];
                // Check if closing bracket matches opening bracket
                if ((c == ')' && top != '(') ||
                    (c == '}' && top != '{') ||
                    (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        
        return pointer == 0;
    }
    
    /**
     * Approach 4: Early Termination with Length Check
     * O(n) time, O(n) space - Optimized with early checks
     */
    public boolean isValidOptimized(String s) {
        // Early termination for odd length strings
        if (s.length() % 2 != 0) {
            return false;
        }
        
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else {
                // Closing bracket - check if stack is empty or doesn't match
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }
    
    /**
     * Approach 5: Recursive Approach (For Learning)
     * O(n) time, O(n) space - Demonstrates recursive thinking
     */
    public boolean isValidRecursive(String s) {
        return isValidRecursiveHelper(s, 0, new Stack<>());
    }
    
    private boolean isValidRecursiveHelper(String s, int index, Stack<Character> stack) {
        if (index == s.length()) {
            return stack.isEmpty();
        }
        
        char c = s.charAt(index);
        
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
            return isValidRecursiveHelper(s, index + 1, stack);
        } else {
            if (stack.isEmpty()) {
                return false;
            }
            
            char top = stack.pop();
            if ((c == ')' && top != '(') ||
                (c == '}' && top != '{') ||
                (c == ']' && top != '[')) {
                return false;
            }
            
            return isValidRecursiveHelper(s, index + 1, stack);
        }
    }
    
    /**
     * Helper method to visualize the stack operations
     */
    private void visualizeStackOperations(String s) {
        System.out.println("\nStack Operations Visualization:");
        System.out.println("Input: \"" + s + "\"");
        
        Stack<Character> stack = new Stack<>();
        boolean isValid = true;
        
        System.out.println("\nStep | Char | Stack State | Action");
        System.out.println("-----|------|-------------|--------");
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            String action = "";
            String stackState = stack.toString();
            
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
                action = "Push '" + c + "'";
            } else {
                if (stack.isEmpty()) {
                    action = "ERROR: Stack empty for closing '" + c + "'";
                    isValid = false;
                } else {
                    char top = stack.pop();
                    if ((c == ')' && top == '(') ||
                        (c == '}' && top == '{') ||
                        (c == ']' && top == '[')) {
                        action = "Pop '" + top + "' - matches '" + c + "'";
                    } else {
                        action = "ERROR: '" + top + "' doesn't match '" + c + "'";
                        isValid = false;
                    }
                }
            }
            
            System.out.printf("%4d | %4c | %11s | %s%n", 
                            i + 1, c, stackState, action);
            
            if (!isValid) {
                break;
            }
        }
        
        if (isValid && !stack.isEmpty()) {
            System.out.println("ERROR: Stack not empty at end: " + stack);
            isValid = false;
        }
        
        System.out.println("\nResult: " + (isValid ? "VALID" : "INVALID"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Valid Parentheses:");
        System.out.println("==========================");
        
        // Test case 1: Simple valid parentheses
        System.out.println("\nTest 1: Simple valid parentheses");
        String s1 = "()";
        boolean expected1 = true;
        
        long startTime = System.nanoTime();
        boolean result1a = solution.isValid(s1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.isValidSwitch(s1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1c = solution.isValidArray(s1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1d = solution.isValidOptimized(s1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1e = solution.isValidRecursive(s1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("HashMap Stack: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Switch Stack: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Array Stack: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Recursive: " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the stack operations
        solution.visualizeStackOperations(s1);
        
        // Test case 2: Multiple bracket types
        System.out.println("\nTest 2: Multiple bracket types");
        String s2 = "()[]{}";
        boolean expected2 = true;
        
        boolean result2a = solution.isValid(s2);
        System.out.println("Multiple types: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Mismatched brackets
        System.out.println("\nTest 3: Mismatched brackets");
        String s3 = "(]";
        boolean expected3 = false;
        
        boolean result3a = solution.isValid(s3);
        System.out.println("Mismatched: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Wrong nesting order
        System.out.println("\nTest 4: Wrong nesting order");
        String s4 = "([)]";
        boolean expected4 = false;
        
        boolean result4a = solution.isValid(s4);
        System.out.println("Wrong nesting: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Properly nested
        System.out.println("\nTest 5: Properly nested");
        String s5 = "{[]}";
        boolean expected5 = true;
        
        boolean result5a = solution.isValid(s5);
        System.out.println("Properly nested: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Single opening bracket
        System.out.println("\nTest 6: Single opening bracket");
        String s6 = "(";
        boolean expected6 = false;
        
        boolean result6a = solution.isValid(s6);
        System.out.println("Single opening: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Single closing bracket
        System.out.println("\nTest 7: Single closing bracket");
        String s7 = ")";
        boolean expected7 = false;
        
        boolean result7a = solution.isValid(s7);
        System.out.println("Single closing: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Empty string
        System.out.println("\nTest 8: Empty string");
        String s8 = "";
        boolean expected8 = true;
        
        boolean result8a = solution.isValid(s8);
        System.out.println("Empty string: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Test case 9: Complex valid sequence
        System.out.println("\nTest 9: Complex valid sequence");
        String s9 = "(([]){})";
        boolean expected9 = true;
        
        boolean result9a = solution.isValid(s9);
        System.out.println("Complex valid: " + result9a + " - " + 
                         (result9a == expected9 ? "PASSED" : "FAILED"));
        
        // Test case 10: Complex invalid sequence
        System.out.println("\nTest 10: Complex invalid sequence");
        String s10 = "(([]){})]";
        boolean expected10 = false;
        
        boolean result10a = solution.isValid(s10);
        System.out.println("Complex invalid: " + result10a + " - " + 
                         (result10a == expected10 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 11: Performance Comparison");
        System.out.println("Simple example performance:");
        System.out.println("  HashMap Stack: " + time1a + " ns");
        System.out.println("  Switch Stack: " + time1b + " ns");
        System.out.println("  Array Stack: " + time1c + " ns");
        System.out.println("  Optimized: " + time1d + " ns");
        System.out.println("  Recursive: " + time1e + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 12: Large input performance");
        StringBuilder largeValid = new StringBuilder();
        // Create a large valid parentheses string
        for (int i = 0; i < 5000; i++) {
            largeValid.append("()[]{}");
        }
        String s12 = largeValid.toString();
        
        startTime = System.nanoTime();
        boolean result12a = solution.isValid(s12);
        long time12a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result12b = solution.isValidArray(s12);
        long time12b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result12c = solution.isValidOptimized(s12);
        long time12c = System.nanoTime() - startTime;
        
        System.out.println("Large input (30,000 characters):");
        System.out.println("  HashMap Stack: " + time12a + " ns, Result: " + result12a);
        System.out.println("  Array Stack: " + time12b + " ns, Result: " + result12b);
        System.out.println("  Optimized: " + time12c + " ns, Result: " + result12c);
        
        // Verify all approaches produce the same result
        boolean allEqual = result12a == result12b && result12a == result12c;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Edge case: Only opening brackets
        System.out.println("\nTest 13: Only opening brackets");
        String s13 = "(((([[[{{{{";
        boolean expected13 = false;
        
        boolean result13a = solution.isValid(s13);
        System.out.println("Only opening: " + result13a + " - " + 
                         (result13a == expected13 ? "PASSED" : "FAILED"));
        
        // Edge case: Only closing brackets
        System.out.println("\nTest 14: Only closing brackets");
        String s14 = "))]]}}";
        boolean expected14 = false;
        
        boolean result14a = solution.isValid(s14);
        System.out.println("Only closing: " + result14a + " - " + 
                         (result14a == expected14 ? "PASSED" : "FAILED"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("STACK ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Use a stack to track opening brackets. When we encounter a closing bracket,");
        System.out.println("it must match the most recent opening bracket (LIFO principle).");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. Push opening brackets onto the stack");
        System.out.println("2. For closing brackets:");
        System.out.println("   - If stack is empty → INVALID (no matching opening)");
        System.out.println("   - If top doesn't match → INVALID (wrong type)");
        System.out.println("   - If top matches → pop and continue");
        System.out.println("3. After processing all characters:");
        System.out.println("   - If stack empty → VALID (all brackets matched)");
        System.out.println("   - If stack not empty → INVALID (unmatched openings)");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Stack with HashMap (RECOMMENDED):");
        System.out.println("   Time: O(n) - Process each character once");
        System.out.println("   Space: O(n) - Stack storage + constant HashMap");
        System.out.println("   How it works:");
        System.out.println("     - Use HashMap for O(1) bracket matching lookup");
        System.out.println("     - Push opening brackets, pop and check for closing");
        System.out.println("     - Clean separation of bracket mapping logic");
        System.out.println("   Pros:");
        System.out.println("     - Clean and readable code");
        System.out.println("     - Easy to extend for new bracket types");
        System.out.println("     - Efficient O(1) bracket matching");
        System.out.println("   Cons:");
        System.out.println("     - Slight HashMap overhead");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Stack with Switch Statement:");
        System.out.println("   Time: O(n) - Process each character once");
        System.out.println("   Space: O(n) - Stack storage");
        System.out.println("   How it works:");
        System.out.println("     - Use switch/if-else for bracket matching");
        System.out.println("     - Direct comparison without HashMap");
        System.out.println("     - Same stack logic, different matching approach");
        System.out.println("   Pros:");
        System.out.println("     - No HashMap overhead");
        System.out.println("     - Familiar to most developers");
        System.out.println("   Cons:");
        System.out.println("     - Harder to extend for new bracket types");
        System.out.println("     - More verbose code");
        System.out.println("   Best for: When bracket types are fixed");
        
        System.out.println("\n3. Array as Stack (Optimized):");
        System.out.println("   Time: O(n) - Process each character once");
        System.out.println("   Space: O(n) - Array storage");
        System.out.println("   How it works:");
        System.out.println("     - Use array instead of Stack class");
        System.out.println("     - Manual pointer management");
        System.out.println("     - Avoids Stack class overhead");
        System.out.println("   Pros:");
        System.out.println("     - Most memory efficient");
        System.out.println("     - Fast array operations");
        System.out.println("     - No object creation overhead");
        System.out.println("   Cons:");
        System.out.println("     - More complex pointer management");
        System.out.println("     - Fixed size (though input length known)");
        System.out.println("   Best for: Performance-critical applications");
        
        System.out.println("\n4. Early Termination Optimized:");
        System.out.println("   Time: O(n) - Process each character once");
        System.out.println("   Space: O(n) - Stack storage");
        System.out.println("   How it works:");
        System.out.println("     - Early check for odd length strings");
        System.out.println("     - Push corresponding closing bracket for openings");
        System.out.println("     - Direct comparison for closing brackets");
        System.out.println("   Pros:");
        System.out.println("     - Early termination for obvious cases");
        System.out.println("     - Clean comparison logic");
        System.out.println("   Cons:");
        System.out.println("     - Slightly different mental model");
        System.out.println("   Best for: Competitive programming, optimized solutions");
        
        System.out.println("\n5. Recursive Approach:");
        System.out.println("   Time: O(n) - Process each character once");
        System.out.println("   Space: O(n) - Call stack + stack storage");
        System.out.println("   How it works:");
        System.out.println("     - Recursively process each character");
        System.out.println("     - Pass stack state through recursion");
        System.out.println("     - Base case: check if stack empty at end");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates recursive thinking");
        System.out.println("     - Functional programming style");
        System.out.println("   Cons:");
        System.out.println("     - Stack overflow risk for large inputs");
        System.out.println("     - Less efficient due to function call overhead");
        System.out.println("   Best for: Learning recursion, small inputs");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("VALIDATION RULES:");
        System.out.println("1. Every opening bracket must have matching closing bracket");
        System.out.println("2. Brackets must be properly nested");
        System.out.println("3. Closing bracket must match most recent opening bracket");
        System.out.println("4. No unmatched brackets at the end");
        System.out.println("5. No closing bracket without corresponding opening bracket");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Stack + HashMap approach - it's the cleanest");
        System.out.println("2. Explain why stack is appropriate (LIFO for nested structures)");
        System.out.println("3. Handle edge cases: empty string, single char, unmatched brackets");
        System.out.println("4. Mention time/space complexity (O(n)/O(n))");
        System.out.println("5. Walk through examples to demonstrate algorithm");
        System.out.println("6. Discuss alternative implementations if time permits");
        System.out.println("7. Consider mentioning real-world applications (compilers, IDEs)");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
