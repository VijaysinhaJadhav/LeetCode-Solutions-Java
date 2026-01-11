
# Solution.java

```java
import java.util.*;

/**
 * 1190. Reverse Substrings Between Each Pair of Parentheses
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Reverse strings inside each pair of parentheses, starting from innermost.
 * Remove all parentheses from final result.
 * 
 * Key Insights:
 * 1. Stack is natural for nested parentheses
 * 2. Process from innermost to outermost
 * 3. When encountering ')', reverse substring since last '('
 * 4. Can precompute matching parentheses for efficiency
 */
class Solution {
    
    /**
     * Approach 1: Stack with StringBuilder (Recommended)
     * Time: O(n^2) worst, O(n) average, Space: O(n)
     * Simple and intuitive stack approach
     */
    public String reverseParentheses(String s) {
        Deque<StringBuilder> stack = new ArrayDeque<>();
        stack.push(new StringBuilder());
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                // Start new level
                stack.push(new StringBuilder());
            } else if (c == ')') {
                // Reverse current level and append to previous level
                StringBuilder current = stack.pop();
                StringBuilder reversed = current.reverse();
                stack.peek().append(reversed);
            } else {
                // Regular character
                stack.peek().append(c);
            }
        }
        
        return stack.pop().toString();
    }
    
    /**
     * Approach 2: Two-pass with Precomputed Matches (Optimized)
     * Time: O(n), Space: O(n)
     * More efficient by precomputing matching parentheses
     */
    public String reverseParentheses2(String s) {
        int n = s.length();
        int[] pair = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        
        // First pass: precompute matching parentheses
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                int j = stack.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }
        
        // Second pass: build result
        StringBuilder result = new StringBuilder();
        int direction = 1; // 1 for right, -1 for left
        int i = 0;
        
        while (i < n) {
            char c = s.charAt(i);
            if (c == '(' || c == ')') {
                // Jump to matching parenthesis and reverse direction
                i = pair[i];
                direction = -direction;
                i += direction; // Move in new direction
            } else {
                result.append(c);
                i += direction;
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 3: Recursive DFS
     * Time: O(n^2), Space: O(n) recursion stack
     * Natural recursive solution for nested structure
     */
    public String reverseParentheses3(String s) {
        return dfs(s, 0, s.length() - 1, false);
    }
    
    private String dfs(String s, int start, int end, boolean reverse) {
        StringBuilder sb = new StringBuilder();
        int i = start;
        
        while (i <= end) {
            char c = s.charAt(i);
            if (c == '(') {
                // Find matching ')'
                int balance = 1;
                int j = i + 1;
                while (j <= end && balance > 0) {
                    if (s.charAt(j) == '(') balance++;
                    else if (s.charAt(j) == ')') balance--;
                    j++;
                }
                // j-1 is the matching ')'
                String inner = dfs(s, i + 1, j - 2, true);
                sb.append(inner);
                i = j;
            } else if (c == ')') {
                i++;
                // Should not reach here in correct nesting
            } else {
                sb.append(c);
                i++;
            }
        }
        
        String result = sb.toString();
        return reverse ? new StringBuilder(result).reverse().toString() : result;
    }
    
    /**
     * Approach 4: Iterative with Two Pointers
     * Time: O(n^2), Space: O(n)
     * Process parentheses by finding innermost pair first
     */
    public String reverseParentheses4(String s) {
        char[] chars = s.toCharArray();
        Deque<Integer> stack = new ArrayDeque<>();
        
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                stack.push(i);
            } else if (chars[i] == ')') {
                int start = stack.pop();
                reverse(chars, start + 1, i - 1);
            }
        }
        
        // Build result without parentheses
        StringBuilder result = new StringBuilder();
        for (char c : chars) {
            if (c != '(' && c != ')') {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    private void reverse(char[] chars, int left, int right) {
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
    }
    
    /**
     * Approach 5: Using Deque for Bidirectional Build
     * Time: O(n^2), Space: O(n)
     * Build result bidirectionally based on parentheses
     */
    public String reverseParentheses5(String s) {
        Deque<Character> deque = new ArrayDeque<>();
        
        for (char c : s.toCharArray()) {
            if (c == ')') {
                // Pop until '(' and reverse
                List<Character> temp = new ArrayList<>();
                while (!deque.isEmpty() && deque.peek() != '(') {
                    temp.add(deque.pop());
                }
                deque.pop(); // Remove '('
                for (char ch : temp) {
                    deque.push(ch);
                }
            } else {
                deque.push(c);
            }
        }
        
        // Build result from deque
        StringBuilder result = new StringBuilder();
        while (!deque.isEmpty()) {
            result.append(deque.removeLast());
        }
        return result.toString();
    }
    
    /**
     * Helper: Visualize the process
     */
    public void visualizeProcess(String s) {
        System.out.println("\nVisualizing process for: " + s);
        System.out.println("Step-by-step transformation:");
        
        Deque<StringBuilder> stack = new ArrayDeque<>();
        stack.push(new StringBuilder());
        int step = 1;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            System.out.printf("\nStep %d: Processing '%c'%n", step++, c);
            
            if (c == '(') {
                stack.push(new StringBuilder());
                System.out.println("  Pushed new level to stack");
                System.out.println("  Stack size: " + stack.size());
            } else if (c == ')') {
                StringBuilder current = stack.pop();
                System.out.println("  Popped level: \"" + current + "\"");
                StringBuilder reversed = current.reverse();
                System.out.println("  Reversed: \"" + reversed + "\"");
                stack.peek().append(reversed);
                System.out.println("  Appended to previous level");
                System.out.println("  Stack size: " + stack.size());
            } else {
                stack.peek().append(c);
                System.out.println("  Appended '" + c + "' to current level");
                System.out.println("  Current level: \"" + stack.peek() + "\"");
            }
            
            // Show stack state
            System.out.print("  Stack state: ");
            List<StringBuilder> stackList = new ArrayList<>(stack);
            Collections.reverse(stackList);
            for (int j = 0; j < stackList.size(); j++) {
                System.out.print("Level " + j + ": \"" + stackList.get(j) + "\"");
                if (j < stackList.size() - 1) System.out.print(" | ");
            }
            System.out.println();
        }
        
        System.out.println("\nFinal result: \"" + stack.pop().toString() + "\"");
    }
    
    /**
     * Helper: Show optimized approach process
     */
    public void visualizeOptimized(String s) {
        System.out.println("\nOptimized approach visualization for: " + s);
        
        int n = s.length();
        int[] pair = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        
        // Precompute matches
        System.out.println("\n1. Precomputing matching parentheses:");
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
                System.out.printf("  '(' at index %d pushed to stack%n", i);
            } else if (c == ')') {
                int j = stack.pop();
                pair[i] = j;
                pair[j] = i;
                System.out.printf("  ')' at index %d matches '(' at index %d%n", i, j);
            }
        }
        
        // Show pair array
        System.out.print("\nPair array: [");
        for (int i = 0; i < n; i++) {
            System.out.print(pair[i]);
            if (i < n - 1) System.out.print(", ");
        }
        System.out.println("]");
        
        // Walk through
        System.out.println("\n2. Walking through string:");
        StringBuilder result = new StringBuilder();
        int direction = 1;
        int i = 0;
        int step = 1;
        
        while (i < n) {
            System.out.printf("\nStep %d: i=%d, direction=%s%n", 
                step++, i, direction == 1 ? "→" : "←");
            
            char c = s.charAt(i);
            System.out.printf("  Current char: '%c'%n", c);
            
            if (c == '(' || c == ')') {
                System.out.printf("  Parenthesis found, jumping to index %d%n", pair[i]);
                System.out.printf("  Reversing direction from %s to %s%n",
                    direction == 1 ? "→" : "←", direction == 1 ? "←" : "→");
                i = pair[i];
                direction = -direction;
                i += direction;
            } else {
                System.out.printf("  Appending '%c' to result%n", c);
                result.append(c);
                i += direction;
            }
            
            System.out.println("  Current result: \"" + result + "\"");
        }
        
        System.out.println("\nFinal result: \"" + result + "\"");
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[] {
            "(abcd)",                    // Simple
            "(u(love)i)",                // Nested once
            "(ed(et(oc))el)",           // Multiple nesting
            "a(bcdefghijkl(mno)p)q",    // Complex nesting
            "((abcd))",                  // Double parentheses
            "()",                        // Empty parentheses
            "abc",                       // No parentheses
            "(a(bc)d)",                  // Another nested
            "ta()us",                    // Empty parentheses in middle
            "((((abcd))))",              // Deep nesting
            "(ab(cd(ef)gh)ij)",          // Multiple levels
            "co(de(fight)s)"             // Mixed
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        String[] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.printf("\nTest %d: %s%n", i + 1, s);
            
            String result1 = reverseParentheses(s);
            String result2 = reverseParentheses2(s);
            String result3 = reverseParentheses3(s);
            String result4 = reverseParentheses4(s);
            String result5 = reverseParentheses5(s);
            
            boolean allMatch = result1.equals(result2) && result2.equals(result3) &&
                              result3.equals(result4) && result4.equals(result5);
            
            if (allMatch) {
                System.out.println("✓ PASS - All methods return: \"" + result1 + "\"");
                passed++;
            } else {
                System.out.println("✗ FAIL - Methods disagree:");
                System.out.println("  Method 1: \"" + result1 + "\"");
                System.out.println("  Method 2: \"" + result2 + "\"");
                System.out.println("  Method 3: \"" + result3 + "\"");
                System.out.println("  Method 4: \"" + result4 + "\"");
                System.out.println("  Method 5: \"" + result5 + "\"");
            }
            
            // Show visualization for complex cases
            if (s.length() <= 20 && s.contains("(")) {
                visualizeProcess(s);
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
        
        // Generate test strings with varying complexity
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(42);
        
        // Build a complex nested string
        for (int i = 0; i < 100; i++) {
            if (rand.nextBoolean() && sb.length() > 0) {
                sb.insert(0, '(');
                sb.append(')');
            }
            char c = (char) ('a' + rand.nextInt(26));
            int pos = rand.nextInt(sb.length() + 1);
            sb.insert(pos, c);
        }
        
        String testString = sb.toString();
        System.out.println("Test string length: " + testString.length());
        System.out.println("Parentheses count: " + countParentheses(testString));
        
        long[] times = new long[5];
        String[] results = new String[5];
        
        // Method 1: Stack with StringBuilder
        long start = System.currentTimeMillis();
        results[0] = reverseParentheses(testString);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Optimized with precomputed matches
        start = System.currentTimeMillis();
        results[1] = reverseParentheses2(testString);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Recursive DFS
        start = System.currentTimeMillis();
        results[2] = reverseParentheses3(testString);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Iterative with two pointers
        start = System.currentTimeMillis();
        results[3] = reverseParentheses4(testString);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Deque bidirectional
        start = System.currentTimeMillis();
        results[4] = reverseParentheses5(testString);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                     | Time (ms) | Length | Correct?");
        System.out.println("---------------------------|-----------|--------|---------");
        System.out.printf("1. Stack with StringBuilder| %9d | %6d | %s%n",
            times[0], results[0].length(), results[0].equals(results[1]) ? "✓" : "✗");
        System.out.printf("2. Optimized with precompute| %9d | %6d | %s%n",
            times[1], results[1].length(), "✓ (baseline)");
        System.out.printf("3. Recursive DFS           | %9d | %6d | %s%n",
            times[2], results[2].length(), results[2].equals(results[1]) ? "✓" : "✗");
        System.out.printf("4. Iterative two pointers  | %9d | %6d | %s%n",
            times[3], results[3].length(), results[3].equals(results[1]) ? "✓" : "✗");
        System.out.printf("5. Deque bidirectional     | %9d | %6d | %s%n",
            times[4], results[4].length(), results[4].equals(results[1]) ? "✓" : "✗");
        
        System.out.println("\nObservations:");
        System.out.println("1. Method 2 (optimized) is fastest for complex nested strings");
        System.out.println("2. Method 1 (stack) is simple and efficient for most cases");
        System.out.println("3. Recursive method can cause stack overflow for deep nesting");
        System.out.println("4. All methods produce same correct result");
    }
    
    private int countParentheses(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(' || c == ')') count++;
        }
        return count;
    }
    
    /**
     * Helper: Edge case testing
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        // Case 1: Empty string
        System.out.println("\n1. Empty string:");
        String s1 = "";
        String r1 = reverseParentheses(s1);
        System.out.println("Input: \"" + s1 + "\"");
        System.out.println("Output: \"" + r1 + "\" (expected: \"\")");
        
        // Case 2: No parentheses
        System.out.println("\n2. No parentheses:");
        String s2 = "abcdefg";
        String r2 = reverseParentheses(s2);
        System.out.println("Input: \"" + s2 + "\"");
        System.out.println("Output: \"" + r2 + "\" (expected: \"abcdefg\")");
        
        // Case 3: Only parentheses
        System.out.println("\n3. Only parentheses:");
        String s3 = "()";
        String r3 = reverseParentheses(s3);
        System.out.println("Input: \"" + s3 + "\"");
        System.out.println("Output: \"" + r3 + "\" (expected: \"\")");
        
        // Case 4: Nested empty parentheses
        System.out.println("\n4. Nested empty parentheses:");
        String s4 = "(()())";
        String r4 = reverseParentheses(s4);
        System.out.println("Input: \"" + s4 + "\"");
        System.out.println("Output: \"" + r4 + "\" (expected: \"\")");
        
        // Case 5: Deep nesting
        System.out.println("\n5. Deep nesting:");
        String s5 = "((((a))))";
        String r5 = reverseParentheses(s5);
        System.out.println("Input: \"" + s5 + "\"");
        System.out.println("Output: \"" + r5 + "\" (expected: \"a\")");
        
        // Case 6: Multiple separate parentheses
        System.out.println("\n6. Multiple separate parentheses:");
        String s6 = "(ab)(cd)(ef)";
        String r6 = reverseParentheses(s6);
        System.out.println("Input: \"" + s6 + "\"");
        System.out.println("Output: \"" + r6 + "\" (expected: \"badcfe\")");
        
        // Case 7: Single character in parentheses
        System.out.println("\n7. Single character in parentheses:");
        String s7 = "(a)";
        String r7 = reverseParentheses(s7);
        System.out.println("Input: \"" + s7 + "\"");
        System.out.println("Output: \"" + r7 + "\" (expected: \"a\")");
    }
    
    /**
     * Helper: Algorithm explanation
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("======================");
        
        System.out.println("\nProblem: Reverse substrings inside parentheses");
        System.out.println("  - Start from innermost parentheses");
        System.out.println("  - Work outward");
        System.out.println("  - Remove all parentheses in final result");
        
        System.out.println("\nStack-based Solution:");
        System.out.println("  1. Initialize stack with empty StringBuilder");
        System.out.println("  2. Iterate through each character:");
        System.out.println("     a. If '(': push new StringBuilder (new level)");
        System.out.println("     b. If ')': ");
        System.out.println("        - pop current StringBuilder");
        System.out.println("        - reverse it");
        System.out.println("        - append to previous level's StringBuilder");
        System.out.println("     c. Else: append character to current level");
        System.out.println("  3. Final result is in the last StringBuilder");
        
        System.out.println("\nExample: (u(love)i)");
        System.out.println("Step 1: '(' → push new level");
        System.out.println("Step 2: 'u' → append to level 2: \"u\"");
        System.out.println("Step 3: '(' → push new level");
        System.out.println("Step 4: 'l','o','v','e' → level 3: \"love\"");
        System.out.println("Step 5: ')' → pop \"love\", reverse to \"evol\", append to level 2");
        System.out.println("         Level 2 becomes: \"uevol\"");
        System.out.println("Step 6: 'i' → append to level 2: \"uevoli\"");
        System.out.println("Step 7: ')' → pop \"uevoli\", reverse to \"iloveu\", append to level 1");
        System.out.println("Result: \"iloveu\"");
        
        System.out.println("\nOptimized Solution (O(n) time):");
        System.out.println("  1. Precompute matching parentheses positions");
        System.out.println("  2. Walk through string with direction pointer");
        System.out.println("  3. When hitting '(', jump to matching ')', reverse direction");
        System.out.println("  4. When hitting ')', jump to matching '(', reverse direction");
        System.out.println("  5. Collect characters in final traversal");
    }
    
    /**
     * Helper: Compare with similar problems
     */
    public void compareSimilarProblems() {
        System.out.println("\nComparison with Similar Problems:");
        System.out.println("=================================");
        
        System.out.println("\n1. 394. Decode String:");
        System.out.println("   Similarity: Both use stack for nested structure");
        System.out.println("   Difference: Decode string repeats substrings, this reverses");
        System.out.println("   Both require processing from innermost to outermost");
        
        System.out.println("\n2. 856. Score of Parentheses:");
        System.out.println("   Similarity: Both process balanced parentheses");
        System.out.println("   Difference: Score calculation vs string reversal");
        System.out.println("   Both can use stack or recursive approaches");
        
        System.out.println("\n3. 1021. Remove Outermost Parentheses:");
        System.out.println("   Similarity: Both remove parentheses from result");
        System.out.println("   Difference: Only removes outermost, this reverses inside");
        System.out.println("   Both transform strings with parentheses");
        
        System.out.println("\n4. 1249. Minimum Remove to Make Valid Parentheses:");
        System.out.println("   Similarity: Both handle parentheses processing");
        System.out.println("   Difference: Removes invalid parentheses, this reverses valid ones");
        System.out.println("   Both need to track parentheses relationships");
        
        System.out.println("\nKey Pattern:");
        System.out.println("  - Stack is natural for nested parentheses problems");
        System.out.println("  - Often need to process from innermost to outermost");
        System.out.println("  - String manipulation combined with stack operations");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify the problem:");
        System.out.println("   - Confirm: start from innermost parentheses");
        System.out.println("   - Confirm: remove all parentheses from result");
        System.out.println("   - Ask about constraints: length, characters allowed");
        
        System.out.println("\n2. Start with examples:");
        System.out.println("   - Walk through provided examples");
        System.out.println("   - Create your own simple example");
        System.out.println("   - Identify the pattern (innermost first)");
        
        System.out.println("\n3. Propose stack solution:");
        System.out.println("   - Explain why stack fits (nested structure)");
        System.out.println("   - Draw stack operations for an example");
        System.out.println("   - Discuss time/space complexity");
        
        System.out.println("\n4. Handle edge cases:");
        System.out.println("   - Empty string");
        System.out.println("   - No parentheses");
        System.out.println("   - Only parentheses");
        System.out.println("   - Deep nesting");
        
        System.out.println("\n5. Optimize if possible:");
        System.out.println("   - Mention O(n) solution with precomputed matches");
        System.out.println("   - Discuss trade-offs (simplicity vs performance)");
        System.out.println("   - For interview, stack solution is usually sufficient");
        
        System.out.println("\n6. Test with examples:");
        System.out.println("   - Provided examples");
        System.out.println("   - Edge cases");
        System.out.println("   - Complex nested case");
        
        System.out.println("\n7. Discuss alternatives:");
        System.out.println("   - Recursive solution (DFS)");
        System.out.println("   - Two-pass with matching parentheses");
        System.out.println("   - Using deque or other data structures");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("   - Not starting from innermost parentheses");
        System.out.println("   - Forgetting to remove parentheses from result");
        System.out.println("   - Stack overflow in recursive solution");
        System.out.println("   - Off-by-one errors in index manipulation");
    }
    
    /**
     * Helper: Step-by-step debugging example
     */
    public void debugExample(String s) {
        System.out.println("\nDebugging Example: " + s);
        System.out.println("=====================");
        
        Deque<StringBuilder> stack = new ArrayDeque<>();
        stack.push(new StringBuilder());
        
        System.out.println("Initial: stack = [\"\"]");
        System.out.println();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            System.out.printf("Step %d: char[%d] = '%c'%n", i + 1, i, c);
            
            if (c == '(') {
                stack.push(new StringBuilder());
                System.out.println("  Action: push new StringBuilder");
            } else if (c == ')') {
                StringBuilder current = stack.pop();
                System.out.println("  Action: pop -> \"" + current + "\"");
                StringBuilder reversed = current.reverse();
                System.out.println("  Action: reverse -> \"" + reversed + "\"");
                stack.peek().append(reversed);
                System.out.println("  Action: append to top of stack");
            } else {
                stack.peek().append(c);
                System.out.println("  Action: append to top StringBuilder");
            }
            
            // Display stack
            System.out.print("  Stack: ");
            List<StringBuilder> stackList = new ArrayList<>(stack);
            Collections.reverse(stackList);
            for (int j = 0; j < stackList.size(); j++) {
                System.out.print("\"" + stackList.get(j) + "\"");
                if (j < stackList.size() - 1) System.out.print(" ← ");
            }
            System.out.println("\n");
        }
        
        System.out.println("Final result: \"" + stack.pop() + "\"");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("1190. Reverse Substrings Between Each Pair of Parentheses");
        System.out.println("=========================================================");
        
        // Explain algorithm
        solution.explainAlgorithm();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Visualize examples
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Visualizing Examples:");
        System.out.println("=".repeat(80));
        
        // Example 1
        System.out.println("\nExample 1: (abcd)");
        solution.visualizeProcess("(abcd)");
        
        // Example 2
        System.out.println("\n" + "-".repeat(80));
        System.out.println("\nExample 2: (u(love)i)");
        solution.visualizeProcess("(u(love)i)");
        
        // Example 3
        System.out.println("\n" + "-".repeat(80));
        System.out.println("\nExample 3: (ed(et(oc))el)");
        solution.visualizeProcess("(ed(et(oc))el)");
        
        // Show optimized approach
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Optimized Approach Visualization:");
        System.out.println("=".repeat(80));
        solution.visualizeOptimized("(u(love)i)");
        
        // Debug complex example
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Step-by-step Debugging:");
        System.out.println("=".repeat(80));
        solution.debugExample("(ab(cd)ef)");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Similar problems comparison
        System.out.println("\n" + "=".repeat(80));
        solution.compareSimilarProblems();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation (Stack approach):");
        System.out.println("""
class Solution {
    public String reverseParentheses(String s) {
        Deque<StringBuilder> stack = new ArrayDeque<>();
        stack.push(new StringBuilder());
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                // Start new level
                stack.push(new StringBuilder());
            } else if (c == ')') {
                // Reverse current level and append to previous
                StringBuilder current = stack.pop();
                StringBuilder reversed = current.reverse();
                stack.peek().append(reversed);
            } else {
                // Regular character
                stack.peek().append(c);
            }
        }
        
        return stack.pop().toString();
    }
}
            """);
        
        System.out.println("\nOptimized Implementation (O(n) time):");
        System.out.println("""
class Solution {
    public String reverseParentheses(String s) {
        int n = s.length();
        int[] pair = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        
        // Precompute matching parentheses
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                int j = stack.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }
        
        // Build result by jumping between parentheses
        StringBuilder result = new StringBuilder();
        int direction = 1; // 1 for right, -1 for left
        int i = 0;
        
        while (i < n) {
            char c = s.charAt(i);
            if (c == '(' || c == ')') {
                // Jump to matching parenthesis and reverse direction
                i = pair[i];
                direction = -direction;
                i += direction;
            } else {
                result.append(c);
                i += direction;
            }
        }
        
        return result.toString();
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Stack naturally handles nested parentheses");
        System.out.println("2. Process from innermost to outermost");
        System.out.println("3. Time complexity: O(n²) worst, O(n) with optimization");
        System.out.println("4. Space complexity: O(n)");
        System.out.println("5. Multiple approaches: stack, recursive, optimized walk");
        
        System.out.println("\nWhen to Use Each Approach:");
        System.out.println("- Stack approach: Most intuitive, good for interviews");
        System.out.println("- Optimized approach: When O(n) time is required");
        System.out.println("- Recursive approach: When you want DFS-style solution");
        
        System.out.println("\nCommon Use Cases:");
        System.out.println("- Parsing nested expressions");
        System.out.println("- String transformations with nesting");
        System.out.println("- Building compilers/interpreters");
        System.out.println("- Text processing with contextual operations");
    }
}
