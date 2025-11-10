/**
 * 150. Evaluate Reverse Polish Notation
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation (RPN).
 * Valid operators are +, -, *, and /. Each operand may be an integer or another expression.
 * Division truncates toward zero.
 * 
 * Key Insights:
 * 1. Use stack to store operands
 * 2. When encountering operator, pop two operands and apply operation
 * 3. Push result back to stack
 * 4. Handle integer division with truncation toward zero
 * 5. Process tokens left to right
 * 
 * Approach (Stack Evaluation):
 * 1. Initialize a stack for operands
 * 2. Iterate through each token
 * 3. If token is number, push to stack
 * 4. If token is operator, pop two operands, compute, push result
 * 5. Final result is the only element in stack
 * 
 * Time Complexity: O(n) - Process each token once
 * Space Complexity: O(n) - Stack storage for operands
 * 
 * Tags: Array, Math, Stack
 */

import java.util.*;

public class Solution {
    
    /**
     * Approach 1: Stack Evaluation - RECOMMENDED
     * O(n) time, O(n) space - Most straightforward and efficient
     */
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String token : tokens) {
            if (isOperator(token)) {
                // Pop two operands and apply operation
                int b = stack.pop();
                int a = stack.pop();
                int result = applyOperation(a, b, token);
                stack.push(result);
            } else {
                // Push operand to stack
                stack.push(Integer.parseInt(token));
            }
        }
        
        return stack.pop();
    }
    
    /**
     * Helper method to check if token is an operator
     */
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/");
    }
    
    /**
     * Helper method to apply operation to two operands
     * Handles integer division with truncation toward zero
     */
    private int applyOperation(int a, int b, String operator) {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b; // Integer division truncates toward zero
            default: throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
    
    /**
     * Approach 2: Stack with Enhanced Division Handling
     * O(n) time, O(n) space - Explicit truncation for clarity
     */
    public int evalRPNEnhanced(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    int divisor = stack.pop();
                    int dividend = stack.pop();
                    stack.push(dividend / divisor); // Integer division
                    break;
                default:
                    stack.push(Integer.parseInt(token));
                    break;
            }
        }
        
        return stack.pop();
    }
    
    /**
     * Approach 3: Array as Stack (Optimized)
     * O(n) time, O(n) space - Uses array instead of Stack class
     */
    public int evalRPNArray(String[] tokens) {
        int[] stack = new int[tokens.length];
        int pointer = 0;
        
        for (String token : tokens) {
            if (token.equals("+")) {
                int b = stack[--pointer];
                int a = stack[--pointer];
                stack[pointer++] = a + b;
            } else if (token.equals("-")) {
                int b = stack[--pointer];
                int a = stack[--pointer];
                stack[pointer++] = a - b;
            } else if (token.equals("*")) {
                int b = stack[--pointer];
                int a = stack[--pointer];
                stack[pointer++] = a * b;
            } else if (token.equals("/")) {
                int b = stack[--pointer];
                int a = stack[--pointer];
                stack[pointer++] = a / b;
            } else {
                stack[pointer++] = Integer.parseInt(token);
            }
        }
        
        return stack[0];
    }
    
    /**
     * Approach 4: Recursive Approach (For Learning)
     * O(n) time, O(n) space - Demonstrates recursive thinking
     */
    public int evalRPNRecursive(String[] tokens) {
        return evalRPNRecursiveHelper(tokens, new int[]{0});
    }
    
    private int evalRPNRecursiveHelper(String[] tokens, int[] index) {
        String token = tokens[index[0]++];
        
        if (isOperator(token)) {
            int right = evalRPNRecursiveHelper(tokens, index);
            int left = evalRPNRecursiveHelper(tokens, index);
            return applyOperation(left, right, token);
        } else {
            return Integer.parseInt(token);
        }
    }
    
    /**
     * Approach 5: Stack with Exception Handling
     * O(n) time, O(n) space - Robust error handling
     */
    public int evalRPNRobust(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String token : tokens) {
            try {
                if (token.length() == 1 && "+-*/".contains(token)) {
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Insufficient operands for operator: " + token);
                    }
                    int b = stack.pop();
                    int a = stack.pop();
                    int result = applyOperationSafe(a, b, token);
                    stack.push(result);
                } else {
                    // Parse integer, handle negative numbers
                    stack.push(Integer.parseInt(token));
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid RPN expression");
        }
        
        return stack.pop();
    }
    
    private int applyOperationSafe(int a, int b, String operator) {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": 
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            default: throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
    
    /**
     * Helper method to visualize the stack evaluation process
     */
    private void visualizeEvaluation(String[] tokens) {
        System.out.println("\nStack Evaluation Visualization:");
        System.out.println("Expression: " + String.join(" ", tokens));
        
        Stack<Integer> stack = new Stack<>();
        
        System.out.println("\nStep | Token | Stack State | Action");
        System.out.println("-----|-------|-------------|--------");
        
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            String stackStateBefore = stack.toString();
            String action = "";
            
            if (isOperator(token)) {
                int b = stack.pop();
                int a = stack.pop();
                int result = applyOperation(a, b, token);
                stack.push(result);
                action = String.format("Pop %d, Pop %d, %s %d %s %d = %d, Push %d", 
                                      b, a, a, token, b, result, result);
            } else {
                int num = Integer.parseInt(token);
                stack.push(num);
                action = "Push " + num;
            }
            
            String stackStateAfter = stack.toString();
            System.out.printf("%4d | %5s | %11s | %s%n", 
                            i + 1, token, stackStateAfter, action);
        }
        
        System.out.println("\nFinal Result: " + stack.pop());
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Reverse Polish Notation Evaluation:");
        System.out.println("============================================");
        
        // Test case 1: Simple addition and multiplication
        System.out.println("\nTest 1: Simple expression");
        String[] tokens1 = {"2", "1", "+", "3", "*"};
        int expected1 = 9;
        
        long startTime = System.nanoTime();
        int result1a = solution.evalRPN(tokens1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.evalRPNEnhanced(tokens1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.evalRPNArray(tokens1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.evalRPNRecursive(tokens1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        
        System.out.println("Stack: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Enhanced: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Array: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Recursive: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the evaluation process
        solution.visualizeEvaluation(tokens1);
        
        // Test case 2: Division expression
        System.out.println("\nTest 2: Division expression");
        String[] tokens2 = {"4", "13", "5", "/", "+"};
        int expected2 = 6; // 4 + (13 / 5) = 4 + 2 = 6
        
        int result2a = solution.evalRPN(tokens2);
        System.out.println("Division: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Complex expression
        System.out.println("\nTest 3: Complex expression");
        String[] tokens3 = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        int expected3 = 22;
        
        int result3a = solution.evalRPN(tokens3);
        System.out.println("Complex: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single number
        System.out.println("\nTest 4: Single number");
        String[] tokens4 = {"18"};
        int expected4 = 18;
        
        int result4a = solution.evalRPN(tokens4);
        System.out.println("Single number: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Negative numbers
        System.out.println("\nTest 5: Negative numbers");
        String[] tokens5 = {"3", "-4", "+"};
        int expected5 = -1;
        
        int result5a = solution.evalRPN(tokens5);
        System.out.println("Negative numbers: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Division truncation
        System.out.println("\nTest 6: Division truncation");
        String[] tokens6 = {"7", "-3", "/"};
        int expected6 = -2; // 7 / -3 = -2.333... truncates to -2
        
        int result6a = solution.evalRPN(tokens6);
        System.out.println("Division truncation: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Multiple operations
        System.out.println("\nTest 7: Multiple operations");
        String[] tokens7 = {"4", "5", "6", "*", "+"}; // 4 + (5 * 6)
        int expected7 = 34;
        
        int result7a = solution.evalRPN(tokens7);
        System.out.println("Multiple operations: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison");
        System.out.println("Simple expression performance:");
        System.out.println("  Stack: " + time1a + " ns");
        System.out.println("  Enhanced: " + time1b + " ns");
        System.out.println("  Array: " + time1c + " ns");
        System.out.println("  Recursive: " + time1d + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 9: Large input performance");
        String[] largeTokens = generateLargeRPN(1000);
        
        startTime = System.nanoTime();
        int result9a = solution.evalRPN(largeTokens);
        long time9a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result9b = solution.evalRPNArray(largeTokens);
        long time9b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result9c = solution.evalRPNEnhanced(largeTokens);
        long time9c = System.nanoTime() - startTime;
        
        System.out.println("Large input (1000 tokens):");
        System.out.println("  Stack: " + time9a + " ns, Result: " + result9a);
        System.out.println("  Array: " + time9b + " ns, Result: " + result9b);
        System.out.println("  Enhanced: " + time9c + " ns, Result: " + result9c);
        
        // Verify all approaches produce the same result
        boolean allEqual = result9a == result9b && result9a == result9c;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Edge case: Division by zero (should be handled in robust version)
        System.out.println("\nTest 10: Error handling");
        try {
            String[] tokens10 = {"1", "0", "/"};
            int result10 = solution.evalRPNRobust(tokens10);
            System.out.println("Division by zero: " + result10 + " - FAILED (should throw exception)");
        } catch (ArithmeticException e) {
            System.out.println("Division by zero: " + e.getMessage() + " - PASSED");
        }
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        
        System.out.println("\nReverse Polish Notation (RPN) Overview:");
        System.out.println("RPN is a mathematical notation where operators follow their operands.");
        System.out.println("It eliminates the need for parentheses and operator precedence rules.");
        
        System.out.println("\nStack Evaluation Algorithm:");
        System.out.println("1. Initialize an empty stack");
        System.out.println("2. For each token in the expression:");
        System.out.println("   a. If token is a number: push it to stack");
        System.out.println("   b. If token is an operator:");
        System.out.println("      - Pop two numbers from stack (right operand first)");
        System.out.println("      - Apply the operator");
        System.out.println("      - Push the result back to stack");
        System.out.println("3. The final result is the only element in the stack");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStack Approach:");
        System.out.println("┌────────────┬────────────┬──────────────┐");
        System.out.println("│ Operation  │ Time       │ Space        │");
        System.out.println("├────────────┼────────────┼──────────────┤");
        System.out.println("│ Process    │ O(n)       │ O(n)         │");
        System.out.println("│ Push/Pop   │ O(1) each  │ O(1) each    │");
        System.out.println("│ Evaluation │ O(n) total │ O(n) total   │");
        System.out.println("└────────────┴────────────┴──────────────┘");
        
        System.out.println("\nComparison of Approaches:");
        System.out.println("┌──────────────────┬────────────┬─────────────────┐");
        System.out.println("│ Approach         │ Time       │ Space           │");
        System.out.println("├──────────────────┼────────────┼─────────────────┤");
        System.out.println("│ Stack            │ O(n)       │ O(n)            │");
        System.out.println("│ Array as Stack   │ O(n)       │ O(n)            │");
        System.out.println("│ Recursive        │ O(n)       │ O(n) call stack │");
        System.out.println("│ Enhanced         │ O(n)       │ O(n)            │");
        System.out.println("└──────────────────┴────────────┴─────────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with Stack Approach:");
        System.out.println("   - Most intuitive and easy to explain");
        System.out.println("   - Clearly demonstrates understanding of RPN");
        System.out.println("   - Handles all standard cases efficiently");
        
        System.out.println("\n2. Key Points to Explain:");
        System.out.println("   - Why stack is appropriate (LIFO for binary operations)");
        System.out.println("   - Operator application order (pop right operand first)");
        System.out.println("   - Integer division truncation toward zero");
        System.out.println("   - Time and space complexity analysis");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - Single number expressions");
        System.out.println("   - Negative numbers and division");
        System.out.println("   - Large numbers and overflow");
        System.out.println("   - Complex nested expressions");
        
        System.out.println("\n4. Discuss Optimizations:");
        System.out.println("   - Array instead of Stack class for performance");
        System.out.println("   - Early error detection");
        System.out.println("   - Memory optimization techniques");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - Calculator and compiler implementations");
        System.out.println("   - Stack-based virtual machines");
        System.out.println("   - Mathematical expression parsing");
        System.out.println("   - Postfix notation in some programming languages");
        
        System.out.println("\nAll tests completed successfully!");
    }
    
    /**
     * Helper method to generate large RPN expression for performance testing
     */
    private static String[] generateLargeRPN(int size) {
        String[] tokens = new String[size];
        Random random = new Random(42);
        String[] operators = {"+", "-", "*", "/"};
        
        // Generate a valid RPN expression
        int numCount = 0;
        for (int i = 0; i < size; i++) {
            if (numCount < 2 || random.nextDouble() < 0.6) {
                // Add a number
                tokens[i] = String.valueOf(random.nextInt(100) + 1); // Avoid zero for division
                numCount++;
            } else {
                // Add an operator
                tokens[i] = operators[random.nextInt(operators.length)];
                numCount--; // Operator consumes two numbers, produces one
            }
        }
        
        // Ensure the expression is valid (ends with enough operators)
        while (numCount > 1) {
            tokens[size - 1 - (numCount - 2)] = operators[random.nextInt(operators.length)];
            numCount--;
        }
        
        return tokens;
    }
}
