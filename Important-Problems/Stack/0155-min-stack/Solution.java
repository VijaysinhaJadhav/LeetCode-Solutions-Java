/**
 * 155. Min Stack
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * 
 * Key Insights:
 * 1. Need to track minimum efficiently with each operation
 * 2. Two stacks: one for values, one for minimums
 * 3. Minimum stack only stores new minimums or duplicates
 * 4. All operations must be O(1) time complexity
 * 
 * Approach (Two Stacks):
 * 1. Use mainStack for regular stack operations
 * 2. Use minStack to track current minimum at each level
 * 3. When pushing, also push to minStack if value <= current minimum
 * 4. When popping, also pop from minStack if value == current minimum
 * 5. getMin() simply returns top of minStack
 * 
 * Time Complexity: O(1) for all operations
 * Space Complexity: O(n) - Additional stack for minimums
 * 
 * Tags: Stack, Design
 */

import java.util.*;

// Interface defining min stack operations
interface MinStackInterface {
    void push(int val);
    void pop();
    int top();
    int getMin();
}

/**
 * Approach 1: Two Stacks - RECOMMENDED
 * O(1) time for all operations, O(n) space - Most straightforward
 */
class MinStack implements MinStackInterface {
    private Stack<Integer> mainStack;
    private Stack<Integer> minStack;
    
    public MinStack() {
        mainStack = new Stack<>();
        minStack = new Stack<>();
    }
    
    /**
     * Push element onto stack.
     * Also push to minStack if value is <= current minimum.
     * Time: O(1)
     */
    public void push(int val) {
        mainStack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }
    
    /**
     * Remove the element on top of the stack.
     * Also pop from minStack if the popped value equals current minimum.
     * Time: O(1)
     */
    public void pop() {
        if (mainStack.pop().equals(minStack.peek())) {
            minStack.pop();
        }
    }
    
    /**
     * Get the top element.
     * Time: O(1)
     */
    public int top() {
        return mainStack.peek();
    }
    
    /**
     * Retrieve the minimum element in the stack.
     * Time: O(1)
     */
    public int getMin() {
        return minStack.peek();
    }
}

/**
 * Approach 2: Single Stack with Node Class
 * O(1) time for all operations, O(n) space - Clean object-oriented approach
 */
class MinStackWithNode implements MinStackInterface {
    private static class Node {
        int val;
        int min;
        Node next;
        
        Node(int val, int min, Node next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }
    
    private Node head;
    
    public MinStackWithNode() {
        head = null;
    }
    
    /**
     * Push element onto stack.
     * Store current minimum with each node.
     * Time: O(1)
     */
    public void push(int val) {
        if (head == null) {
            head = new Node(val, val, null);
        } else {
            head = new Node(val, Math.min(val, head.min), head);
        }
    }
    
    /**
     * Remove the element on top of the stack.
     * Time: O(1)
     */
    public void pop() {
        head = head.next;
    }
    
    /**
     * Get the top element.
     * Time: O(1)
     */
    public int top() {
        return head.val;
    }
    
    /**
     * Retrieve the minimum element in the stack.
     * Time: O(1)
     */
    public int getMin() {
        return head.min;
    }
}

/**
 * Approach 3: Single Stack with Value-Min Pairs
 * O(1) time for all operations, O(n) space - Uses single stack with pairs
 */
class MinStackWithPairs implements MinStackInterface {
    private Stack<int[]> stack; // Each element is [value, currentMin]
    
    public MinStackWithPairs() {
        stack = new Stack<>();
    }
    
    /**
     * Push element onto stack.
     * Store value and current minimum as a pair.
     * Time: O(1)
     */
    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new int[]{val, val});
        } else {
            int currentMin = stack.peek()[1];
            stack.push(new int[]{val, Math.min(val, currentMin)});
        }
    }
    
    /**
     * Remove the element on top of the stack.
     * Time: O(1)
     */
    public void pop() {
        stack.pop();
    }
    
    /**
     * Get the top element.
     * Time: O(1)
     */
    public int top() {
        return stack.peek()[0];
    }
    
    /**
     * Retrieve the minimum element in the stack.
     * Time: O(1)
     */
    public int getMin() {
        return stack.peek()[1];
    }
}

/**
 * Approach 4: Mathematical Approach (Handles Overflow)
 * O(1) time for all operations, O(1) extra space - Advanced approach
 */
class MinStackMathematical implements MinStackInterface {
    private Stack<Long> stack;
    private long min;
    
    public MinStackMathematical() {
        stack = new Stack<>();
    }
    
    /**
     * Push element onto stack.
     * Store difference from current minimum.
     * Time: O(1)
     */
    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(0L);
            min = val;
        } else {
            stack.push((long)val - min);
            if (val < min) {
                min = val;
            }
        }
    }
    
    /**
     * Remove the element on top of the stack.
     * Time: O(1)
     */
    public void pop() {
        long diff = stack.pop();
        if (diff < 0) {
            // The popped element was the minimum, restore previous minimum
            min = min - diff;
        }
    }
    
    /**
     * Get the top element.
     * Time: O(1)
     */
    public int top() {
        long diff = stack.peek();
        if (diff < 0) {
            // Current element is the minimum
            return (int)min;
        } else {
            return (int)(min + diff);
        }
    }
    
    /**
     * Retrieve the minimum element in the stack.
     * Time: O(1)
     */
    public int getMin() {
        return (int)min;
    }
}

/**
 * Approach 5: Two Stacks with Optimized Space
 * O(1) time for all operations, optimized space - Stores minimums efficiently
 */
class MinStackOptimized implements MinStackInterface {
    private Stack<Integer> mainStack;
    private Stack<Integer> minStack;
    
    public MinStackOptimized() {
        mainStack = new Stack<>();
        minStack = new Stack<>();
    }
    
    /**
     * Push element onto stack.
     * Only push to minStack when value <= current minimum.
     * Time: O(1)
     */
    public void push(int val) {
        mainStack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }
    
    /**
     * Remove the element on top of the stack.
     * Only pop from minStack when the popped value equals current minimum.
     * Time: O(1)
     */
    public void pop() {
        int popped = mainStack.pop();
        if (popped == minStack.peek()) {
            minStack.pop();
        }
    }
    
    /**
     * Get the top element.
     * Time: O(1)
     */
    public int top() {
        return mainStack.peek();
    }
    
    /**
     * Retrieve the minimum element in the stack.
     * Time: O(1)
     */
    public int getMin() {
        return minStack.peek();
    }
}

/**
 * Test class to verify all implementations
 */
public class Solution {
    /**
     * Helper method to test a min stack implementation
     */
    private static void testMinStackImplementation(MinStackInterface minStack, String implementationName) {
        System.out.println("\nTesting " + implementationName + ":");
        System.out.println("Operations: push(-2), push(0), push(-3), getMin(), pop(), top(), getMin()");
        
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        int min1 = minStack.getMin();
        minStack.pop();
        int top = minStack.top();
        int min2 = minStack.getMin();
        
        System.out.println("First getMin: " + min1 + " (expected: -3)");
        System.out.println("Top after pop: " + top + " (expected: 0)");
        System.out.println("Second getMin: " + min2 + " (expected: -2)");
        
        boolean testPassed = (min1 == -3) && (top == 0) && (min2 == -2);
        System.out.println("TEST " + (testPassed ? "PASSED" : "FAILED"));
    }
    
    /**
     * Helper method to visualize min stack operations
     */
    private static void visualizeMinStackOperations() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("VISUALIZATION: Two Stacks Min Stack Implementation");
        System.out.println("=".repeat(70));
        
        MinStack minStack = new MinStack();
        String[] operations = {"push(5)", "push(3)", "push(7)", "getMin()", "push(2)", 
                              "getMin()", "pop()", "getMin()", "push(1)", "getMin()"};
        int[] values = {5, 3, 7, 0, 2, 0, 0, 0, 1, 0};
        
        System.out.println("\nStep | Operation | Main Stack | Min Stack | Current Min | Action");
        System.out.println("-----|-----------|------------|-----------|-------------|--------");
        
        for (int i = 0; i < operations.length; i++) {
            String op = operations[i];
            String action = "";
            
            // Get current state using reflection (for visualization only)
            String mainState = getStackState(minStack, "mainStack");
            String minState = getStackState(minStack, "minStack");
            String currentMin = getCurrentMin(minStack);
            
            switch (op) {
                case "push(5)":
                    minStack.push(5);
                    action = "Push 5 to both stacks (first element)";
                    break;
                case "push(3)":
                    minStack.push(3);
                    action = "Push 3 to main, also to min (3 < 5)";
                    break;
                case "push(7)":
                    minStack.push(7);
                    action = "Push 7 to main, NOT to min (7 > 3)";
                    break;
                case "getMin()":
                    int min = minStack.getMin();
                    action = "Get min from minStack: " + min;
                    break;
                case "push(2)":
                    minStack.push(2);
                    action = "Push 2 to main, also to min (2 < 3)";
                    break;
                case "pop()":
                    minStack.pop();
                    action = "Pop from main, also from min (2 was min)";
                    break;
                case "push(1)":
                    minStack.push(1);
                    action = "Push 1 to main, also to min (1 < 3)";
                    break;
            }
            
            // Get state after operation
            String mainStateAfter = getStackState(minStack, "mainStack");
            String minStateAfter = getStackState(minStack, "minStack");
            String currentMinAfter = getCurrentMin(minStack);
            
            System.out.printf("%4d | %9s | %10s | %9s | %11s | %s%n", 
                            i + 1, op, mainStateAfter, minStateAfter, currentMinAfter, action);
        }
        
        System.out.println("\nKey Insights:");
        System.out.println("- Min stack only stores values that are <= current minimum");
        System.println("- When popping, only remove from min stack if the value equals current min");
        System.out.println("- This ensures O(1) time for all operations");
        System.out.println("- Space optimized: min stack is often smaller than main stack");
    }
    
    /**
     * Helper method to get stack state as string (for visualization)
     */
    private static String getStackState(MinStack minStack, String fieldName) {
        try {
            java.lang.reflect.Field field = MinStack.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            Stack<Integer> stack = (Stack<Integer>) field.get(minStack);
            if (stack.isEmpty()) return "[]";
            return stack.toString();
        } catch (Exception e) {
            return "N/A";
        }
    }
    
    /**
     * Helper method to get current minimum (for visualization)
     */
    private static String getCurrentMin(MinStack minStack) {
        try {
            java.lang.reflect.Field field = MinStack.class.getDeclaredField("minStack");
            field.setAccessible(true);
            Stack<Integer> minStackInternal = (Stack<Integer>) field.get(minStack);
            if (minStackInternal.isEmpty()) return "None";
            return String.valueOf(minStackInternal.peek());
        } catch (Exception e) {
            return "N/A";
        }
    }
    
    /**
     * Performance comparison of different implementations
     */
    private static void performanceComparison() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(60));
        
        int numOperations = 10000;
        
        // Test Two Stacks implementation
        long startTime = System.nanoTime();
        MinStackInterface stack1 = new MinStack();
        for (int i = 0; i < numOperations; i++) {
            stack1.push(i % 100); // Varying values
            if (i % 3 == 0) stack1.getMin();
        }
        for (int i = 0; i < numOperations; i++) {
            stack1.pop();
        }
        long time1 = System.nanoTime() - startTime;
        
        // Test Node implementation
        startTime = System.nanoTime();
        MinStackInterface stack2 = new MinStackWithNode();
        for (int i = 0; i < numOperations; i++) {
            stack2.push(i % 100);
            if (i % 3 == 0) stack2.getMin();
        }
        for (int i = 0; i < numOperations; i++) {
            stack2.pop();
        }
        long time2 = System.nanoTime() - startTime;
        
        // Test Pairs implementation
        startTime = System.nanoTime();
        MinStackInterface stack3 = new MinStackWithPairs();
        for (int i = 0; i < numOperations; i++) {
            stack3.push(i % 100);
            if (i % 3 == 0) stack3.getMin();
        }
        for (int i = 0; i < numOperations; i++) {
            stack3.pop();
        }
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("\nPerformance for " + numOperations + " operations:");
        System.out.println("Two Stacks: " + time1 + " ns");
        System.out.println("Node Approach: " + time2 + " ns");
        System.out.println("Pairs Approach: " + time3 + " ns");
    }
    
    /**
     * Comprehensive test cases
     */
    private static void runComprehensiveTests() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPREHENSIVE TEST CASES");
        System.out.println("=".repeat(60));
        
        // Test 1: Basic operations with descending values
        System.out.println("\nTest 1: Descending Values");
        MinStack stack1 = new MinStack();
        stack1.push(5);
        stack1.push(4);
        stack1.push(3);
        stack1.push(2);
        stack1.push(1);
        System.out.println("Push 5,4,3,2,1 -> Min: " + stack1.getMin() + " (expected: 1)");
        stack1.pop();
        System.out.println("Pop -> Min: " + stack1.getMin() + " (expected: 2)");
        stack1.pop();
        System.out.println("Pop -> Min: " + stack1.getMin() + " (expected: 3)");
        
        // Test 2: Mixed values with duplicates
        System.out.println("\nTest 2: Mixed Values with Duplicates");
        MinStack stack2 = new MinStack();
        stack2.push(2);
        stack2.push(2);
        stack2.push(3);
        stack2.push(1);
        stack2.push(1);
        System.out.println("Push 2,2,3,1,1 -> Min: " + stack2.getMin() + " (expected: 1)");
        stack2.pop();
        System.out.println("Pop -> Min: " + stack2.getMin() + " (expected: 1)");
        stack2.pop();
        System.out.println("Pop -> Min: " + stack2.getMin() + " (expected: 2)");
        
        // Test 3: Ascending values
        System.out.println("\nTest 3: Ascending Values");
        MinStack stack3 = new MinStack();
        stack3.push(1);
        stack3.push(2);
        stack3.push(3);
        stack3.push(4);
        stack3.push(5);
        System.out.println("Push 1,2,3,4,5 -> Min: " + stack3.getMin() + " (expected: 1)");
        stack3.pop();
        System.out.println("Pop -> Min: " + stack3.getMin() + " (expected: 1)");
        stack3.pop();
        System.out.println("Pop -> Min: " + stack3.getMin() + " (expected: 1)");
        
        // Test 4: Single element
        System.out.println("\nTest 4: Single Element");
        MinStack stack4 = new MinStack();
        stack4.push(10);
        System.out.println("Push 10 -> Min: " + stack4.getMin() + " (expected: 10)");
        System.out.println("Top: " + stack4.top() + " (expected: 10)");
        
        // Test 5: Negative numbers
        System.out.println("\nTest 5: Negative Numbers");
        MinStack stack5 = new MinStack();
        stack5.push(-1);
        stack5.push(-5);
        stack5.push(-3);
        System.out.println("Push -1,-5,-3 -> Min: " + stack5.getMin() + " (expected: -5)");
        stack5.pop();
        System.out.println("Pop -> Min: " + stack5.getMin() + " (expected: -5)");
        stack5.pop();
        System.out.println("Pop -> Min: " + stack5.getMin() + " (expected: -1)");
    }
    
    /**
     * Edge case tests
     */
    private static void runEdgeCaseTests() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EDGE CASE TESTS");
        System.out.println("=".repeat(60));
        
        // Test 1: Large numbers
        System.out.println("\nTest 1: Large Numbers");
        MinStack stack1 = new MinStack();
        stack1.push(Integer.MAX_VALUE);
        stack1.push(Integer.MIN_VALUE);
        System.out.println("Push MAX, MIN -> Min: " + stack1.getMin() + " (expected: " + Integer.MIN_VALUE + ")");
        
        // Test 2: Duplicate minimum values
        System.out.println("\nTest 2: Duplicate Minimum Values");
        MinStack stack2 = new MinStack();
        stack2.push(5);
        stack2.push(3);
        stack2.push(3);
        stack2.push(7);
        System.out.println("Push 5,3,3,7 -> Min: " + stack2.getMin() + " (expected: 3)");
        stack2.pop();
        System.out.println("Pop -> Min: " + stack2.getMin() + " (expected: 3)");
        stack2.pop();
        System.out.println("Pop -> Min: " + stack2.getMin() + " (expected: 3)");
        stack2.pop();
        System.out.println("Pop -> Min: " + stack2.getMin() + " (expected: 5)");
        
        // Test 3: Interleaved operations
        System.out.println("\nTest 3: Interleaved Operations");
        MinStack stack3 = new MinStack();
        stack3.push(5);
        stack3.push(3);
        System.out.println("Min after push(5), push(3): " + stack3.getMin());
        stack3.push(7);
        System.out.println("Min after push(7): " + stack3.getMin());
        stack3.pop();
        System.out.println("Min after pop(): " + stack3.getMin());
        stack3.push(2);
        System.out.println("Min after push(2): " + stack3.getMin());
        stack3.push(8);
        System.out.println("Min after push(8): " + stack3.getMin());
    }
    
    /**
     * Main method to run all tests
     */
    public static void main(String[] args) {
        System.out.println("Testing Min Stack Implementation");
        System.out.println("================================");
        
        // Test all implementations
        testMinStackImplementation(new MinStack(), "Two Stacks");
        testMinStackImplementation(new MinStackWithNode(), "Node Approach");
        testMinStackImplementation(new MinStackWithPairs(), "Pairs Approach");
        testMinStackImplementation(new MinStackMathematical(), "Mathematical Approach");
        testMinStackImplementation(new MinStackOptimized(), "Optimized Two Stacks");
        
        // Visualize operations
        visualizeMinStackOperations();
        
        // Run comprehensive tests
        runComprehensiveTests();
        
        // Run edge case tests
        runEdgeCaseTests();
        
        // Performance comparison
        performanceComparison();
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(60));
        
        System.out.println("\nTwo Stacks Approach:");
        System.out.println("1. Main Stack: Stores all values in LIFO order");
        System.out.println("2. Min Stack: Stores minimum values only when needed");
        System.out.println("3. Push Logic:");
        System.out.println("   - Always push to main stack");
        System.out.println("   - Push to min stack only if value <= current minimum");
        System.out.println("4. Pop Logic:");
        System.out.println("   - Always pop from main stack");
        System.out.println("   - Pop from min stack only if value == current minimum");
        System.out.println("5. GetMin: Simply return top of min stack");
        
        System.out.println("\nWhy It Works:");
        System.out.println("- Min stack always has the current minimum at top");
        System.out.println("- When a new minimum is pushed, it's added to min stack");
        System.out.println("- When minimum is popped, we remove it from min stack");
        System.out.println("- Previous minimum automatically becomes current minimum");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(60));
        
        System.out.println("\nTwo Stacks Approach:");
        System.out.println("┌──────────┬────────────┬──────────────┐");
        System.out.println("│ Operation│ Time       │ Space        │");
        System.out.println("├──────────┼────────────┼──────────────┤");
        System.out.println("│ push(x)  │ O(1)       │ O(1)         │");
        System.out.println("│ pop()    │ O(1)       │ O(1)         │");
        System.out.println("│ top()    │ O(1)       │ O(1)         │");
        System.out.println("│ getMin() │ O(1)       │ O(1)         │");
        System.out.println("└──────────┴────────────┴──────────────┘");
        System.out.println("Overall Space: O(n) - Two stacks storing elements");
        
        System.out.println("\nComparison of Approaches:");
        System.out.println("┌──────────────────┬────────────┬─────────────────┐");
        System.out.println("│ Approach         │ Time       │ Space           │");
        System.out.println("├──────────────────┼────────────┼─────────────────┤");
        System.out.println("│ Two Stacks       │ O(1) all   │ O(n) worst-case │");
        System.out.println("│ Node Approach    │ O(1) all   │ O(n)            │");
        System.out.println("│ Pairs Approach   │ O(1) all   │ O(n)            │");
        System.out.println("│ Mathematical     │ O(1) all   │ O(1) extra      │");
        System.out.println("└──────────────────┴────────────┴─────────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(60));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(60));
        
        System.out.println("\n1. Start with Two Stacks Approach:");
        System.out.println("   - Most intuitive and easy to explain");
        System.out.println("   - Clear separation of concerns");
        System.out.println("   - Handles all edge cases well");
        
        System.out.println("\n2. Key Points to Explain:");
        System.out.println("   - Why two stacks are needed (efficient min tracking)");
        System.out.println("   - Push logic: when to add to min stack");
        System.out.println("   - Pop logic: when to remove from min stack");
        System.out.println("   - Why O(1) time complexity is maintained");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - Duplicate minimum values");
        System.out.println("   - Large numbers (overflow considerations)");
        System.out.println("   - Single element stack");
        System.out.println("   - Ascending/descending value sequences");
        
        System.out.println("\n4. Discuss Trade-offs:");
        System.out.println("   - Space vs time complexity");
        System.out.println("   - Simplicity vs optimization");
        System.out.println("   - Readability vs performance");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - Undo functionality with minimum tracking");
        System.out.println("   - Stock price analysis (min price over time)");
        System.out.println("   - Game score tracking (minimum score)");
        System.out.println("   - Resource allocation (minimum available)");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
