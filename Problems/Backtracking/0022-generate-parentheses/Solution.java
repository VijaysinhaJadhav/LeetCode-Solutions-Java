
## Solution.java

```java
/**
 * 22. Generate Parentheses
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given n pairs of parentheses, write a function to generate all combinations 
 * of well-formed parentheses.
 * 
 * Key Insights:
 * 1. Use backtracking/DFS to build valid combinations
 * 2. Track counts of open and close parentheses
 * 3. Only add ')' when close count < open count
 * 4. Only add '(' when open count < n
 * 5. Base case: when string length reaches 2n
 * 
 * Approach (Backtracking/DFS):
 * 1. Start with empty string, open=0, close=0
 * 2. Recursively build valid combinations
 * 3. Add '(' if open < n
 * 4. Add ')' if close < open
 * 5. Add to result when length = 2n
 * 
 * Time Complexity: O(4^n/√n) - nth Catalan number
 * Space Complexity: O(4^n/√n) - for output storage
 * 
 * Tags: String, Dynamic Programming, Backtracking, Recursion
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Backtracking/DFS - RECOMMENDED
     * O(4^n/√n) time, O(4^n/√n) space
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        backtrack(result, "", 0, 0, n);
        return result;
    }
    
    private void backtrack(List<String> result, String current, int open, int close, int n) {
        // Base case: when current string has full length
        if (current.length() == 2 * n) {
            result.add(current);
            return;
        }
        
        // Add open parenthesis if we haven't used all n
        if (open < n) {
            backtrack(result, current + "(", open + 1, close, n);
        }
        
        // Add close parenthesis if we have more opens than closes
        if (close < open) {
            backtrack(result, current + ")", open, close + 1, n);
        }
    }
    
    /**
     * Approach 2: Iterative DFS using Stack
     * O(4^n/√n) time, O(4^n/√n) space
     */
    public List<String> generateParenthesisDFS(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        
        // Use a stack for DFS: each element is [currentString, openCount, closeCount]
        Stack<Object[]> stack = new Stack<>();
        stack.push(new Object[]{"", 0, 0});
        
        while (!stack.isEmpty()) {
            Object[] node = stack.pop();
            String current = (String) node[0];
            int open = (Integer) node[1];
            int close = (Integer) node[2];
            
            if (current.length() == 2 * n) {
                result.add(current);
                continue;
            }
            
            if (open < n) {
                stack.push(new Object[]{current + "(", open + 1, close});
            }
            
            if (close < open) {
                stack.push(new Object[]{current + ")", open, close + 1});
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Dynamic Programming
     * O(4^n/√n) time, O(4^n/√n) space
     * Build solutions for n from solutions for smaller n
     */
    public List<String> generateParenthesisDP(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }
        
        // dp[i] stores all combinations for i pairs
        List<List<String>> dp = new ArrayList<>();
        
        // Base case: 0 pairs
        dp.add(new ArrayList<>(Arrays.asList("")));
        
        // Build solutions for 1 to n
        for (int i = 1; i <= n; i++) {
            List<String> current = new ArrayList<>();
            
            // For each possible split: (left)right
            for (int j = 0; j < i; j++) {
                List<String> left = dp.get(j);        // j pairs inside
                List<String> right = dp.get(i - 1 - j); // i-1-j pairs outside
                
                // Combine all possibilities
                for (String l : left) {
                    for (String r : right) {
                        current.add("(" + l + ")" + r);
                    }
                }
            }
            dp.add(current);
        }
        
        return dp.get(n);
    }
    
    /**
     * Approach 4: BFS (Level Order Generation)
     * O(4^n/√n) time, O(4^n/√n) space
     */
    public List<String> generateParenthesisBFS(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        
        Queue<ParenthesisNode> queue = new LinkedList<>();
        queue.offer(new ParenthesisNode("", 0, 0));
        
        while (!queue.isEmpty()) {
            ParenthesisNode node = queue.poll();
            
            if (node.current.length() == 2 * n) {
                result.add(node.current);
                continue;
            }
            
            if (node.open < n) {
                queue.offer(new ParenthesisNode(node.current + "(", node.open + 1, node.close));
            }
            
            if (node.close < node.open) {
                queue.offer(new ParenthesisNode(node.current + ")", node.open, node.close + 1));
            }
        }
        
        return result;
    }
    
    private static class ParenthesisNode {
        String current;
        int open;
        int close;
        
        ParenthesisNode(String current, int open, int close) {
            this.current = current;
            this.open = open;
            this.close = close;
        }
    }
    
    /**
     * Approach 5: Closure Number (Mathematical)
     * O(4^n/√n) time, O(4^n/√n) space
     * Based on Catalan number recurrence
     */
    public List<String> generateParenthesisClosure(int n) {
        List<String> result = new ArrayList<>();
        if (n == 0) {
            result.add("");
        } else {
            for (int c = 0; c < n; c++) {
                for (String left : generateParenthesisClosure(c)) {
                    for (String right : generateParenthesisClosure(n - 1 - c)) {
                        result.add("(" + left + ")" + right);
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * Approach 6: Backtracking with StringBuilder (More Efficient)
     * O(4^n/√n) time, O(n) auxiliary space for recursion
     */
    public List<String> generateParenthesisStringBuilder(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        backtrackStringBuilder(result, new StringBuilder(), 0, 0, n);
        return result;
    }
    
    private void backtrackStringBuilder(List<String> result, StringBuilder sb, int open, int close, int n) {
        if (sb.length() == 2 * n) {
            result.add(sb.toString());
            return;
        }
        
        if (open < n) {
            sb.append('(');
            backtrackStringBuilder(result, sb, open + 1, close, n);
            sb.deleteCharAt(sb.length() - 1); // backtrack
        }
        
        if (close < open) {
            sb.append(')');
            backtrackStringBuilder(result, sb, open, close + 1, n);
            sb.deleteCharAt(sb.length() - 1); // backtrack
        }
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    private void visualizeBacktracking(int n) {
        System.out.println("\nBacktracking Algorithm Visualization:");
        System.out.println("n = " + n);
        System.out.println("Expected number of combinations: " + catalanNumber(n));
        
        List<String> result = new ArrayList<>();
        System.out.println("\nBacktracking Steps:");
        System.out.println("Depth | Current String | Open | Close | Action");
        System.out.println("------|----------------|------|-------|--------");
        
        visualizeBacktrackHelper(result, "", 0, 0, n, 0);
        
        System.out.println("\nFinal Result: " + result);
        System.out.println("Total combinations generated: " + result.size());
    }
    
    private void visualizeBacktrackHelper(List<String> result, String current, int open, int close, int n, int depth) {
        if (current.length() == 2 * n) {
            System.out.printf("%5d | %14s | %4d | %5d | COMPLETE: %s%n", 
                            depth, current, open, close, current);
            result.add(current);
            return;
        }
        
        if (open < n) {
            System.out.printf("%5d | %14s | %4d | %5d | ADD '('%n", 
                            depth, current, open, close);
            visualizeBacktrackHelper(result, current + "(", open + 1, close, n, depth + 1);
        }
        
        if (close < open) {
            System.out.printf("%5d | %14s | %4d | %5d | ADD ')'%n", 
                            depth, current, open, close);
            visualizeBacktrackHelper(result, current + ")", open, close + 1, n, depth + 1);
        }
    }
    
    /**
     * Calculate nth Catalan number
     */
    private int catalanNumber(int n) {
        // C(n) = (2n)! / (n! * (n+1)!)
        long result = 1;
        for (int i = 0; i < n; i++) {
            result = result * (2L * n - i) / (i + 1);
        }
        result /= (n + 1);
        return (int) result;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Generate Parentheses Solution:");
        System.out.println("======================================");
        
        // Test case 1: n = 3
        System.out.println("\nTest 1: n = 3");
        int n1 = 3;
        List<String> expected1 = Arrays.asList(
            "((()))", "(()())", "(())()", "()(())", "()()()"
        );
        
        long startTime = System.nanoTime();
        List<String> result1a = solution.generateParenthesis(n1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<String> result1b = solution.generateParenthesisDFS(n1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<String> result1c = solution.generateParenthesisDP(n1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<String> result1d = solution.generateParenthesisBFS(n1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<String> result1e = solution.generateParenthesisStringBuilder(n1);
        long time1e = System.nanoTime() - startTime;
        
        // Sort results for comparison
        Collections.sort(result1a);
        Collections.sort(result1b);
        Collections.sort(result1c);
        Collections.sort(result1d);
        Collections.sort(result1e);
        Collections.sort(expected1);
        
        boolean test1a = result1a.equals(expected1);
        boolean test1b = result1b.equals(expected1);
        boolean test1c = result1c.equals(expected1);
        boolean test1d = result1d.equals(expected1);
        boolean test1e = result1e.equals(expected1);
        
        System.out.println("Backtracking: " + result1a.size() + " combinations - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DFS Stack: " + result1b.size() + " combinations - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Dynamic Programming: " + result1c.size() + " combinations - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + result1d.size() + " combinations - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("StringBuilder: " + result1e.size() + " combinations - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the backtracking process
        solution.visualizeBacktracking(n1);
        
        // Test case 2: n = 1
        System.out.println("\nTest 2: n = 1");
        int n2 = 1;
        List<String> result2a = solution.generateParenthesis(n2);
        System.out.println("n = 1: " + result2a + " - " + 
                         (result2a.size() == 1 && result2a.get(0).equals("()") ? "PASSED" : "FAILED"));
        
        // Test case 3: n = 2
        System.out.println("\nTest 3: n = 2");
        int n3 = 2;
        List<String> result3a = solution.generateParenthesis(n3);
        System.out.println("n = 2: " + result3a.size() + " combinations - " + 
                         (result3a.size() == 2 ? "PASSED" : "FAILED"));
        
        // Test case 4: n = 0
        System.out.println("\nTest 4: n = 0");
        int n4 = 0;
        List<String> result4a = solution.generateParenthesis(n4);
        System.out.println("n = 0: " + result4a + " - " + 
                         (result4a.isEmpty() ? "PASSED" : "FAILED"));
        
        // Test case 5: n = 4 (verify Catalan number)
        System.out.println("\nTest 5: n = 4");
        int n5 = 4;
        List<String> result5a = solution.generateParenthesis(n5);
        int catalan5 = solution.catalanNumber(n5);
        System.out.println("n = 4: " + result5a.size() + " combinations - " + 
                         (result5a.size() == catalan5 ? "PASSED" : "FAILED") + 
                         " (Catalan C4 = " + catalan5 + ")");
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison (n = 3)");
        System.out.println("Backtracking: " + time1a + " ns");
        System.out.println("DFS Stack: " + time1b + " ns");
        System.out.println("Dynamic Programming: " + time1c + " ns");
        System.out.println("BFS: " + time1d + " ns");
        System.out.println("StringBuilder: " + time1e + " ns");
        
        // Test all generate the same results
        System.out.println("\nTest 7: All approaches consistency");
        boolean allConsistent = result1a.equals(result1b) && 
                              result1a.equals(result1c) && 
                              result1a.equals(result1d) &&
                              result1a.equals(result1e);
        System.out.println("All approaches produce same results: " + allConsistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BACKTRACKING ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We can only add a closing parenthesis if there are more");
        System.out.println("open parentheses than closing ones at that point.");
        
        System.out.println("\nThe Algorithm Rules:");
        System.out.println("1. Add '(' if: open count < n");
        System.out.println("2. Add ')' if: close count < open count");
        System.out.println("3. Complete when: string length = 2 × n");
        
        System.out.println("\nWhy it works:");
        System.out.println("- Ensures we never have more closing than opening parentheses");
        System.out.println("- Explores all valid combinations systematically");
        System.out.println("- Naturally handles the balancing constraint");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking/DFS (RECOMMENDED):");
        System.out.println("   Time: O(4^n/√n) - nth Catalan number behavior");
        System.out.println("   Space: O(4^n/√n) - For storing output combinations");
        System.out.println("   How it works:");
        System.out.println("     - Recursively build strings character by character");
        System.out.println("     - Track open and close counts");
        System.out.println("     - Apply constraints at each step");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and easy to understand");
        System.out.println("     - Naturally handles constraints");
        System.out.println("     - Standard interview solution");
        System.out.println("   Cons:");
        System.out.println("     - Exponential time complexity (inherent to problem)");
        System.out.println("   Best for: Interview settings, most use cases");
        
        System.out.println("\n2. Dynamic Programming:");
        System.out.println("   Time: O(4^n/√n) - Same Catalan number complexity");
        System.out.println("   Space: O(4^n/√n) - Store all intermediate results");
        System.out.println("   How it works:");
        System.out.println("     - Build solutions bottom-up");
        System.out.println("     - dp[i] = all combinations for i pairs");
        System.out.println("     - dp[i] = ['(' + left + ')' + right for all splits]");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates DP thinking");
        System.out.println("     - Builds on smaller solutions");
        System.out.println("   Cons:");
        System.out.println("     - More complex to understand");
        System.out.println("     - Stores all intermediate results");
        System.out.println("   Best for: Learning DP applications");
        
        System.out.println("\n3. BFS (Level Order):");
        System.out.println("   Time: O(4^n/√n) - Same complexity");
        System.out.println("   Space: O(4^n/√n) - Queue storage");
        System.out.println("   How it works:");
        System.out.println("     - Generate combinations level by level");
        System.out.println("     - Use queue to process nodes");
        System.out.println("     - Same constraints as backtracking");
        System.out.println("   Pros:");
        System.out.println("     - Level-wise generation");
        System.out.println("     - Avoids recursion stack");
        System.out.println("   Cons:");
        System.out.println("     - Higher memory usage");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: When iterative solution preferred");
        
        System.out.println("\n4. StringBuilder Backtracking:");
        System.out.println("   Time: O(4^n/√n) - Same complexity");
        System.out.println("   Space: O(n) - Recursion stack (excluding output)");
        System.out.println("   How it works:");
        System.out.println("     - Same as backtracking but uses StringBuilder");
        System.out.println("     - More efficient string manipulation");
        System.out.println("     - Manual backtracking with deleteCharAt");
        System.out.println("   Pros:");
        System.out.println("     - More memory efficient");
        System.out.println("     - Faster string operations");
        System.out.println("   Cons:");
        System.out.println("     - More verbose backtracking");
        System.out.println("     - Easy to make mistakes");
        System.out.println("   Best for: Production code, large n");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Number of valid combinations = nth Catalan number");
        System.out.println("2. Catalan numbers: C(n) = (2n)! / (n! × (n+1)!)");
        System.out.println("3. First few Catalan numbers:");
        System.out.println("   C0 = 1, C1 = 1, C2 = 2, C3 = 5, C4 = 14, C5 = 42");
        System.out.println("4. The sequence grows as O(4^n/√n)");
        System.out.println("5. This is the number of valid parentheses combinations");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("VALIDITY CONDITIONS EXPLAINED:");
        System.out.println("At any point in building the string:");
        System.out.println("1. open >= close (never more closing than opening)");
        System.out.println("2. open <= n (never more than n opening parentheses)");
        System.out.println("3. Final string: open = close = n");
        System.out.println("These ensure all generated strings are valid");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with backtracking - it's the expected solution");
        System.out.println("2. Clearly explain the constraints and why they work");
        System.out.println("3. Mention the Catalan number connection");
        System.out.println("4. Discuss time/space complexity honestly");
        System.out.println("5. Consider mentioning alternative approaches");
        System.out.println("6. Handle edge cases: n=0, n=1");
        System.out.println("7. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        // Generate and display results for different n
        System.out.println("\n" + "=".repeat(70));
        System.out.println("RESULTS FOR DIFFERENT n VALUES:");
        System.out.println("=".repeat(70));
        
        for (int i = 0; i <= 4; i++) {
            List<String> combinations = solution.generateParenthesis(i);
            System.out.printf("\nn = %d: %d combinations (Catalan C%d = %d)%n", 
                            i, combinations.size(), i, solution.catalanNumber(i));
            if (i <= 3) {
                System.out.println("Combinations: " + combinations);
            } else {
                System.out.println("Combinations: [too many to display]");
            }
        }
        
        System.out.println("\nAll tests completed!");
    }
}
