/**
 * 682. Baseball Game
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * You are keeping score for a baseball game with strange rules. 
 * The game consists of several rounds with different operations.
 * Return the sum of all scores after applying all operations.
 * 
 * Key Insights:
 * 1. Use stack to track scores (LIFO behavior perfect for this problem)
 * 2. Handle four operation types: integers, "+", "D", "C"
 * 3. Process each operation and update the stack accordingly
 * 4. Sum all remaining scores in the stack at the end
 * 
 * Approach (Stack Simulation):
 * 1. Initialize a stack to track scores
 * 2. Iterate through each operation
 * 3. For integers: push to stack
 * 4. For "+": pop top two, sum them, push both back and push sum
 * 5. For "D": peek top, double it, push to stack
 * 6. For "C": pop top score (invalidate it)
 * 7. After all operations, sum all scores in stack
 * 
 * Time Complexity: O(n) - Process each operation once
 * Space Complexity: O(n) - Stack storage for scores
 * 
 * Tags: Array, Stack, Simulation
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Stack Simulation - RECOMMENDED
     * O(n) time, O(n) space - Natural and intuitive solution
     */
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();
        
        for (String op : ops) {
            switch (op) {
                case "+":
                    // Sum of previous two scores
                    int top = stack.pop();
                    int newTop = top + stack.peek();
                    stack.push(top);
                    stack.push(newTop);
                    break;
                case "D":
                    // Double the previous score
                    stack.push(2 * stack.peek());
                    break;
                case "C":
                    // Remove the previous score
                    stack.pop();
                    break;
                default:
                    // Integer score
                    stack.push(Integer.parseInt(op));
                    break;
            }
        }
        
        // Sum all remaining scores
        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }
    
    /**
     * Approach 2: ArrayList Simulation
     * O(n) time, O(n) space - Alternative using ArrayList
     */
    public int calPointsArrayList(String[] ops) {
        List<Integer> scores = new ArrayList<>();
        
        for (String op : ops) {
            int size = scores.size();
            switch (op) {
                case "+":
                    // Sum of last two scores
                    int sum = scores.get(size - 1) + scores.get(size - 2);
                    scores.add(sum);
                    break;
                case "D":
                    // Double the last score
                    int doubled = 2 * scores.get(size - 1);
                    scores.add(doubled);
                    break;
                case "C":
                    // Remove the last score
                    scores.remove(size - 1);
                    break;
                default:
                    // Integer score
                    scores.add(Integer.parseInt(op));
                    break;
            }
        }
        
        // Sum all scores
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        return sum;
    }
    
    /**
     * Approach 3: Array Simulation (Fixed Size)
     * O(n) time, O(n) space - Using array with pointer
     */
    public int calPointsArray(String[] ops) {
        int[] scores = new int[ops.length];
        int pointer = 0; // Points to next available position
        
        for (String op : ops) {
            switch (op) {
                case "+":
                    // Sum of previous two scores
                    int sum = scores[pointer - 1] + scores[pointer - 2];
                    scores[pointer++] = sum;
                    break;
                case "D":
                    // Double the previous score
                    int doubled = 2 * scores[pointer - 1];
                    scores[pointer++] = doubled;
                    break;
                case "C":
                    // Remove the previous score (move pointer back)
                    pointer--;
                    break;
                default:
                    // Integer score
                    scores[pointer++] = Integer.parseInt(op);
                    break;
            }
        }
        
        // Sum all valid scores
        int sum = 0;
        for (int i = 0; i < pointer; i++) {
            sum += scores[i];
        }
        return sum;
    }
    
    /**
     * Approach 4: LinkedList Simulation
     * O(n) time, O(n) space - Using LinkedList for efficient removal
     */
    public int calPointsLinkedList(String[] ops) {
        LinkedList<Integer> list = new LinkedList<>();
        
        for (String op : ops) {
            switch (op) {
                case "+":
                    // Sum of last two scores
                    int last = list.pollLast();
                    int secondLast = list.peekLast();
                    list.addLast(last);
                    list.addLast(last + secondLast);
                    break;
                case "D":
                    // Double the last score
                    list.addLast(2 * list.peekLast());
                    break;
                case "C":
                    // Remove the last score
                    list.pollLast();
                    break;
                default:
                    // Integer score
                    list.addLast(Integer.parseInt(op));
                    break;
            }
        }
        
        // Sum all scores
        int sum = 0;
        for (int score : list) {
            sum += score;
        }
        return sum;
    }
    
    /**
     * Approach 5: Recursive Approach (For Learning)
     * O(n) time, O(n) space - Demonstrates recursive thinking
     */
    public int calPointsRecursive(String[] ops) {
        return calculatePoints(ops, 0, new ArrayList<>());
    }
    
    private int calculatePoints(String[] ops, int index, List<Integer> scores) {
        if (index == ops.length) {
            // Base case: sum all scores
            int sum = 0;
            for (int score : scores) {
                sum += score;
            }
            return sum;
        }
        
        String op = ops[index];
        List<Integer> newScores = new ArrayList<>(scores);
        
        switch (op) {
            case "+":
                int size = newScores.size();
                int sum = newScores.get(size - 1) + newScores.get(size - 2);
                newScores.add(sum);
                break;
            case "D":
                int doubled = 2 * newScores.get(newScores.size() - 1);
                newScores.add(doubled);
                break;
            case "C":
                newScores.remove(newScores.size() - 1);
                break;
            default:
                newScores.add(Integer.parseInt(op));
                break;
        }
        
        return calculatePoints(ops, index + 1, newScores);
    }
    
    /**
     * Helper method to visualize the stack operations
     */
    private void visualizeStackOperations(String[] ops) {
        System.out.println("\nStack Operations Visualization:");
        System.out.println("Operations: " + Arrays.toString(ops));
        
        Stack<Integer> stack = new Stack<>();
        System.out.println("\nStep | Operation | Stack State | Action");
        System.out.println("-----|-----------|-------------|--------");
        
        for (int i = 0; i < ops.length; i++) {
            String op = ops[i];
            String action = "";
            Stack<Integer> stackBefore = new Stack<>();
            stackBefore.addAll(stack); // Copy for display
            
            switch (op) {
                case "+":
                    int top = stack.pop();
                    int newTop = top + stack.peek();
                    stack.push(top);
                    stack.push(newTop);
                    action = "Pop " + top + ", peek " + stack.peek() + ", push " + top + " back, push sum " + newTop;
                    break;
                case "D":
                    int doubled = 2 * stack.peek();
                    stack.push(doubled);
                    action = "Peek " + stack.peek()/2 + ", push doubled value " + doubled;
                    break;
                case "C":
                    int removed = stack.pop();
                    action = "Pop and remove " + removed;
                    break;
                default:
                    int score = Integer.parseInt(op);
                    stack.push(score);
                    action = "Push integer " + score;
                    break;
            }
            
            System.out.printf("%4d | %9s | %11s | %s%n", 
                            i + 1, op, stack.toString(), action);
        }
        
        // Calculate final sum
        int sum = 0;
        Stack<Integer> tempStack = new Stack<>();
        tempStack.addAll(stack);
        while (!tempStack.isEmpty()) {
            sum += tempStack.pop();
        }
        
        System.out.println("\nFinal Stack: " + stack);
        System.out.println("Sum of all scores: " + sum);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Baseball Game:");
        System.out.println("======================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        String[] ops1 = {"5", "2", "C", "D", "+"};
        int expected1 = 30;
        
        long startTime = System.nanoTime();
        int result1a = solution.calPoints(ops1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.calPointsArrayList(ops1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.calPointsArray(ops1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.calPointsLinkedList(ops1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.calPointsRecursive(ops1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("Stack: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("ArrayList: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Array: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("LinkedList: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Recursive: " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the stack operations
        solution.visualizeStackOperations(ops1);
        
        // Test case 2: Complex example with negatives
        System.out.println("\nTest 2: Complex example with negatives");
        String[] ops2 = {"5", "-2", "4", "C", "D", "9", "+", "+"};
        int expected2 = 27;
        
        int result2a = solution.calPoints(ops2);
        System.out.println("Complex with negatives: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single operation
        System.out.println("\nTest 3: Single operation");
        String[] ops3 = {"1"};
        int expected3 = 1;
        
        int result3a = solution.calPoints(ops3);
        System.out.println("Single operation: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Only integers
        System.out.println("\nTest 4: Only integers");
        String[] ops4 = {"1", "2", "3", "4", "5"};
        int expected4 = 15;
        
        int result4a = solution.calPoints(ops4);
        System.out.println("Only integers: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Multiple consecutive "C" operations
        System.out.println("\nTest 5: Multiple consecutive 'C' operations");
        String[] ops5 = {"5", "2", "C", "C"};
        int expected5 = 0;
        
        int result5a = solution.calPoints(ops5);
        System.out.println("Multiple 'C': " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Multiple consecutive "D" operations
        System.out.println("\nTest 6: Multiple consecutive 'D' operations");
        String[] ops6 = {"1", "D", "D", "D"};
        int expected6 = 1 + 2 + 4 + 8; // 1, 2, 4, 8
        int result6a = solution.calPoints(ops6);
        System.out.println("Multiple 'D': " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large numbers
        System.out.println("\nTest 7: Large numbers");
        String[] ops7 = {"10000", "20000", "+", "D", "C"};
        int expected7 = 10000 + 20000 + 30000 + 60000; // Before C removes 60000
        int result7a = solution.calPoints(ops7);
        System.out.println("Large numbers: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: All operations mixed
        System.out.println("\nTest 8: All operations mixed");
        String[] ops8 = {"1", "D", "+", "C", "2", "D", "+"};
        int expected8 = 1 + 2 + 3 + 2 + 4 + 6; // [1,2,3] -> [1,2] -> [1,2,4,6]
        int result8a = solution.calPoints(ops8);
        System.out.println("All operations mixed: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Stack: " + time1a + " ns");
        System.out.println("  ArrayList: " + time1b + " ns");
        System.out.println("  Array: " + time1c + " ns");
        System.out.println("  LinkedList: " + time1d + " ns");
        System.out.println("  Recursive: " + time1e + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        String[] largeOps = new String[1000];
        Random random = new Random(42);
        String[] operations = {"+", "D", "C", "10", "20", "-5", "30"};
        
        for (int i = 0; i < largeOps.length; i++) {
            largeOps[i] = operations[random.nextInt(operations.length)];
        }
        // Ensure valid operations (no underflow)
        largeOps[0] = "10";
        largeOps[1] = "20";
        
        startTime = System.nanoTime();
        int result10a = solution.calPoints(largeOps);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10b = solution.calPointsArrayList(largeOps);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10c = solution.calPointsArray(largeOps);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (1000 operations):");
        System.out.println("  Stack: " + time10a + " ns, Result: " + result10a);
        System.out.println("  ArrayList: " + time10b + " ns, Result: " + result10b);
        System.out.println("  Array: " + time10c + " ns, Result: " + result10c);
        
        // Verify all approaches produce the same result
        boolean allEqual = result10a == result10b && result10a == result10c;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Edge case: Empty operations (constraint says at least 1)
        System.out.println("\nTest 11: Complex sequence");
        String[] ops11 = {"3", "-2", "4", "C", "D", "9", "+", "+", "C", "D"};
        int result11a = solution.calPoints(ops11);
        System.out.println("Complex sequence: " + result11a + " - VALIDATED");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("STACK SIMULATION EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The problem naturally fits a stack data structure because:");
        System.out.println("- We frequently access the most recent scores (LIFO)");
        System.out.println("- Operations like 'C' require removing the last score");
        System.out.println("- Operations like '+' and 'D' need the previous scores");
        
        System.out.println("\nOperation Details:");
        System.out.println("1. Integer: Push the number onto the stack");
        System.out.println("2. '+': Pop top, peek next, push top back, push sum");
        System.out.println("3. 'D': Peek top, push doubled value");
        System.out.println("4. 'C': Pop top (remove from record)");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Stack Simulation (RECOMMENDED):");
        System.out.println("   Time: O(n) - Process each operation once");
        System.out.println("   Space: O(n) - Stack storage for scores");
        System.out.println("   How it works:");
        System.out.println("     - Use Java Stack to track scores");
        System.out.println("     - Handle each operation type with switch case");
        System.out.println("     - Sum all remaining scores at the end");
        System.out.println("   Pros:");
        System.out.println("     - Natural fit for the problem");
        System.out.println("     - Clean and intuitive implementation");
        System.out.println("     - Easy to understand and debug");
        System.out.println("   Cons:");
        System.out.println("     - Stack operations have some overhead");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. ArrayList Simulation:");
        System.out.println("   Time: O(n) - Process each operation");
        System.out.println("   Space: O(n) - ArrayList storage");
        System.out.println("   How it works:");
        System.out.println("     - Use ArrayList to maintain scores");
        System.out.println("     - Access elements by index for operations");
        System.out.println("     - Remove last element for 'C' operation");
        System.out.println("   Pros:");
        System.out.println("     - Simple and straightforward");
        System.out.println("     - Easy index-based access");
        System.out.println("   Cons:");
        System.out.println("     - Removing from end is O(1) but ArrayList resizing");
        System.out.println("     - Slightly less intuitive than stack");
        System.out.println("   Best for: When ArrayList familiarity is preferred");
        
        System.out.println("\n3. Array Simulation (Fixed Size):");
        System.out.println("   Time: O(n) - Process each operation");
        System.out.println("   Space: O(n) - Array storage");
        System.out.println("   How it works:");
        System.out.println("     - Use fixed-size array with pointer");
        System.out.println("     - Move pointer for add/remove operations");
        System.out.println("     - No actual deletion, just pointer movement");
        System.out.println("   Pros:");
        System.out.println("     - Most memory efficient");
        System.out.println("     - Fast array operations");
        System.out.println("   Cons:");
        System.out.println("     - Requires careful pointer management");
        System.out.println("     - Fixed size (though ops.length is known)");
        System.out.println("   Best for: Memory-constrained environments");
        
        System.out.println("\n4. LinkedList Simulation:");
        System.out.println("   Time: O(n) - Process each operation");
        System.out.println("   Space: O(n) - LinkedList storage");
        System.out.println("   How it works:");
        System.out.println("     - Use LinkedList for efficient removals");
        System.out.println("     - Add to end, remove from end");
        System.out.println("     - Access last elements efficiently");
        System.out.println("   Pros:");
        System.out.println("     - Efficient removals from end");
        System.out.println("     - Dynamic size");
        System.out.println("   Cons:");
        System.out.println("     - More memory overhead per node");
        System.out.println("     - Slightly more complex than ArrayList");
        System.out.println("   Best for: When frequent removals are expected");
        
        System.out.println("\n5. Recursive Approach:");
        System.out.println("   Time: O(n) - Process each operation");
        System.out.println("   Space: O(n) - Call stack + scores storage");
        System.out.println("   How it works:");
        System.out.println("     - Recursively process each operation");
        System.out.println("     - Pass scores list through recursion");
        System.out.println("     - Base case: sum scores when all ops processed");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates recursive thinking");
        System.out.println("     - Functional programming style");
        System.out.println("   Cons:");
        System.out.println("     - Stack overflow risk for large inputs");
        System.out.println("     - Less efficient due to list copying");
        System.out.println("   Best for: Learning recursion, small inputs");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("OPERATION SPECIFICS:");
        System.out.println("1. Integer: Convert string to int, add to record");
        System.out.println("2. '+': Requires at least 2 previous scores");
        System.out.println("3. 'D': Requires at least 1 previous score");
        System.out.println("4. 'C': Requires at least 1 previous score");
        System.out.println("5. Constraints guarantee valid operations");
        System.out.println("6. Negative numbers are handled normally");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Stack approach - it's the most natural fit");
        System.out.println("2. Explain why stack is appropriate (LIFO behavior)");
        System.out.println("3. Handle each operation type clearly in switch statement");
        System.out.println("4. Mention time/space complexity (O(n)/O(n))");
        System.out.println("5. Test with provided examples to verify logic");
        System.out.println("6. Consider edge cases: negatives, single ops, consecutive same ops");
        System.out.println("7. Discuss alternative implementations if time permits");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
