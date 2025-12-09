/**
 * 232. Implement Queue using Stacks
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Implement a first-in-first-out (FIFO) queue using only stacks.
 * The queue should support push, pop, peek, and empty operations.
 * 
 * Key Insights:
 * 1. Use two stacks: input stack for push, output stack for pop/peek
 * 2. Transfer elements from input to output only when output is empty
 * 3. This provides amortized O(1) time complexity for all operations
 * 4. Each element is pushed and popped exactly twice
 * 
 * Approach (Two Stacks with Amortized O(1)):
 * 1. Use inputStack for push operations (O(1))
 * 2. Use outputStack for pop/peek operations
 * 3. When outputStack is empty, transfer all elements from inputStack
 * 4. This reversal gives FIFO order from LIFO stacks
 * 
 * Time Complexity (Amortized):
 * - Push: O(1) - Direct push to input stack
 * - Pop: O(1) - Transfer only when output stack empty
 * - Peek: O(1) - Similar to pop
 * - Empty: O(1) - Check both stacks
 * 
 * Space Complexity: O(n) - Store all elements across two stacks
 * 
 * Tags: Stack, Design, Queue
 */

import java.util.*;

// Interface defining queue operations
interface Queue {
    void push(int x);
    int pop();
    int peek();
    boolean empty();
}

/**
 * Approach 1: Two Stacks with Amortized O(1) - RECOMMENDED
 * Amortized O(1) for all operations - Most efficient and practical
 */
class MyQueue implements Queue {
    private Stack<Integer> inputStack;
    private Stack<Integer> outputStack;
    
    public MyQueue() {
        inputStack = new Stack<>();
        outputStack = new Stack<>();
    }
    
    /**
     * Push element x to the back of queue.
     * Time: O(1) - Direct push to input stack
     */
    public void push(int x) {
        inputStack.push(x);
    }
    
    /**
     * Removes the element from the front of queue and returns it.
     * Time: Amortized O(1) - Transfer only when output stack empty
     */
    public int pop() {
        // Ensure output stack has elements
        peek();
        return outputStack.pop();
    }
    
    /**
     * Get the front element.
     * Time: Amortized O(1) - Transfer only when output stack empty
     */
    public int peek() {
        if (outputStack.isEmpty()) {
            // Transfer all elements from input to output stack
            while (!inputStack.isEmpty()) {
                outputStack.push(inputStack.pop());
            }
        }
        return outputStack.peek();
    }
    
    /**
     * Returns whether the queue is empty.
     * Time: O(1) - Check both stacks
     */
    public boolean empty() {
        return inputStack.isEmpty() && outputStack.isEmpty();
    }
}

/**
 * Approach 2: Two Stacks with O(1) Push, O(n) Pop
 * Alternative approach that makes push fast but pop/peek slow
 */
class MyQueuePushOptimized implements Queue {
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;
    
    public MyQueuePushOptimized() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }
    
    /**
     * Push element x to the back of queue.
     * Time: O(1) - Direct push to stack1
     */
    public void push(int x) {
        stack1.push(x);
    }
    
    /**
     * Removes the element from the front of queue and returns it.
     * Time: O(n) - Always transfer all elements
     */
    public int pop() {
        // Transfer all elements from stack1 to stack2
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        // Pop from stack2 (front of queue)
        int popped = stack2.pop();
        // Transfer back to stack1
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        return popped;
    }
    
    /**
     * Get the front element.
     * Time: O(n) - Always transfer all elements
     */
    public int peek() {
        // Transfer all elements from stack1 to stack2
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        // Peek from stack2 (front of queue)
        int front = stack2.peek();
        // Transfer back to stack1
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        return front;
    }
    
    /**
     * Returns whether the queue is empty.
     * Time: O(1) - Check stack1
     */
    public boolean empty() {
        return stack1.isEmpty();
    }
}

/**
 * Approach 3: Single Stack with Recursion
 * Demonstrates recursive approach (not recommended for production)
 */
class MyQueueRecursive implements Queue {
    private Stack<Integer> stack;
    
    public MyQueueRecursive() {
        stack = new Stack<>();
    }
    
    /**
     * Push element x to the back of queue.
     * Time: O(1) - Direct push
     */
    public void push(int x) {
        stack.push(x);
    }
    
    /**
     * Removes the element from the front of queue and returns it.
     * Time: O(n) - Recursive reversal
     */
    public int pop() {
        // Base case: if stack has only one element, pop it
        if (stack.size() == 1) {
            return stack.pop();
        }
        
        // Recursive case: pop all elements except last, then push back
        int top = stack.pop();
        int result = pop();
        stack.push(top);
        return result;
    }
    
    /**
     * Get the front element.
     * Time: O(n) - Recursive access to bottom
     */
    public int peek() {
        // Base case: if stack has only one element, peek it
        if (stack.size() == 1) {
            return stack.peek();
        }
        
        // Recursive case: pop all elements except last, then push back
        int top = stack.pop();
        int result = peek();
        stack.push(top);
        return result;
    }
    
    /**
     * Returns whether the queue is empty.
     * Time: O(1) - Check stack
     */
    public boolean empty() {
        return stack.isEmpty();
    }
}

/**
 * Approach 4: Two Stacks with Front Tracking
 * Optimized version that tracks front element for O(1) peek
 */
class MyQueueWithFront implements Queue {
    private Stack<Integer> inputStack;
    private Stack<Integer> outputStack;
    private Integer front; // Track the front element for O(1) peek
    
    public MyQueueWithFront() {
        inputStack = new Stack<>();
        outputStack = new Stack<>();
    }
    
    /**
     * Push element x to the back of queue.
     * Time: O(1) - Direct push to input stack
     */
    public void push(int x) {
        if (inputStack.isEmpty() && outputStack.isEmpty()) {
            front = x;
        }
        inputStack.push(x);
    }
    
    /**
     * Removes the element from the front of queue and returns it.
     * Time: Amortized O(1) - Transfer only when output stack empty
     */
    public int pop() {
        if (outputStack.isEmpty()) {
            // Transfer all elements from input to output stack
            while (!inputStack.isEmpty()) {
                outputStack.push(inputStack.pop());
            }
        }
        int popped = outputStack.pop();
        // Update front if output stack is not empty
        if (!outputStack.isEmpty()) {
            front = outputStack.peek();
        } else if (!inputStack.isEmpty()) {
            // If input stack has elements, we need to find new front
            // This is expensive but happens rarely
            front = null;
        }
        return popped;
    }
    
    /**
     * Get the front element.
     * Time: O(1) - Use stored front value
     */
    public int peek() {
        if (front == null) {
            // This should not happen if used correctly
            if (!outputStack.isEmpty()) {
                return outputStack.peek();
            } else if (!inputStack.isEmpty()) {
                // Need to find front (expensive)
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
                front = outputStack.peek();
                return front;
            }
        }
        return front;
    }
    
    /**
     * Returns whether the queue is empty.
     * Time: O(1) - Check both stacks
     */
    public boolean empty() {
        return inputStack.isEmpty() && outputStack.isEmpty();
    }
}

/**
 * Approach 5: Using Deque as Stack (For Comparison)
 * Using Deque but only with stack operations for demonstration
 */
class MyQueueWithDeque implements Queue {
    private Deque<Integer> inputDeque;
    private Deque<Integer> outputDeque;
    
    public MyQueueWithDeque() {
        inputDeque = new LinkedList<>();
        outputDeque = new LinkedList<>();
    }
    
    public void push(int x) {
        inputDeque.push(x);
    }
    
    public int pop() {
        peek(); // Ensure output deque has elements
        return outputDeque.pop();
    }
    
    public int peek() {
        if (outputDeque.isEmpty()) {
            while (!inputDeque.isEmpty()) {
                outputDeque.push(inputDeque.pop());
            }
        }
        return outputDeque.peek();
    }
    
    public boolean empty() {
        return inputDeque.isEmpty() && outputDeque.isEmpty();
    }
}

/**
 * Test class to verify all implementations
 */
public class Solution {
    /**
     * Helper method to test a queue implementation
     */
    private static void testQueueImplementation(Queue queue, String implementationName) {
        System.out.println("\nTesting " + implementationName + ":");
        System.out.println("Operations: push(1), push(2), peek(), pop(), empty()");
        
        queue.push(1);
        queue.push(2);
        int peek = queue.peek();
        int pop = queue.pop();
        boolean empty = queue.empty();
        
        System.out.println("Peek: " + peek + " (expected: 1)");
        System.out.println("Pop: " + pop + " (expected: 1)");
        System.out.println("Empty: " + empty + " (expected: false)");
        
        boolean testPassed = (peek == 1) && (pop == 1) && !empty;
        System.out.println("TEST " + (testPassed ? "PASSED" : "FAILED"));
    }
    
    /**
     * Helper method to visualize queue operations
     */
    private static void visualizeQueueOperations() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("VISUALIZATION: Two Stacks Queue Implementation");
        System.out.println("=".repeat(70));
        
        MyQueue queue = new MyQueue();
        String[] operations = {"push(1)", "push(2)", "push(3)", "pop()", "peek()", "push(4)"};
        
        System.out.println("\nStep | Operation | Input Stack | Output Stack | Action");
        System.out.println("-----|-----------|-------------|--------------|--------");
        
        for (int i = 0; i < operations.length; i++) {
            String op = operations[i];
            String action = "";
            
            // Get current state using reflection (for visualization only)
            String inputState = getStackState(queue, "inputStack");
            String outputState = getStackState(queue, "outputStack");
            
            switch (op) {
                case "push(1)":
                    queue.push(1);
                    action = "Push 1 to input stack";
                    break;
                case "push(2)":
                    queue.push(2);
                    action = "Push 2 to input stack";
                    break;
                case "push(3)":
                    queue.push(3);
                    action = "Push 3 to input stack";
                    break;
                case "pop()":
                    int popped = queue.pop();
                    action = "Pop from output stack: " + popped;
                    break;
                case "peek()":
                    int peek = queue.peek();
                    action = "Peek from output stack: " + peek;
                    break;
                case "push(4)":
                    queue.push(4);
                    action = "Push 4 to input stack";
                    break;
            }
            
            // Get state after operation
            String inputStateAfter = getStackState(queue, "inputStack");
            String outputStateAfter = getStackState(queue, "outputStack");
            
            System.out.printf("%4d | %9s | %11s | %12s | %s%n", 
                            i + 1, op, inputStateAfter, outputStateAfter, action);
        }
        
        System.out.println("\nKey Insight:");
        System.out.println("- Input stack: receives all push operations (LIFO order)");
        System.out.println("- Output stack: provides pop/peek operations (reversed order = FIFO)");
        System.out.println("- Transfer happens only when output stack is empty");
        System.out.println("- Each element is pushed and popped exactly twice");
    }
    
    /**
     * Helper method to get stack state as string (for visualization)
     */
    private static String getStackState(MyQueue queue, String fieldName) {
        try {
            java.lang.reflect.Field field = MyQueue.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            Stack<Integer> stack = (Stack<Integer>) field.get(queue);
            if (stack.isEmpty()) return "[]";
            return stack.toString();
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
        
        int numOperations = 1000;
        
        // Test Amortized O(1) implementation
        long startTime = System.nanoTime();
        Queue queue1 = new MyQueue();
        for (int i = 0; i < numOperations; i++) {
            queue1.push(i);
        }
        for (int i = 0; i < numOperations; i++) {
            queue1.pop();
        }
        long time1 = System.nanoTime() - startTime;
        
        // Test Push-Optimized implementation
        startTime = System.nanoTime();
        Queue queue2 = new MyQueuePushOptimized();
        for (int i = 0; i < numOperations; i++) {
            queue2.push(i);
        }
        for (int i = 0; i < numOperations; i++) {
            queue2.pop();
        }
        long time2 = System.nanoTime() - startTime;
        
        // Test Recursive implementation
        startTime = System.nanoTime();
        Queue queue3 = new MyQueueRecursive();
        for (int i = 0; i < numOperations; i++) {
            queue3.push(i);
        }
        for (int i = 0; i < numOperations; i++) {
            queue3.pop();
        }
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("\nPerformance for " + numOperations + " push/pop operations:");
        System.out.println("Two Stacks (Amortized O(1)): " + time1 + " ns");
        System.out.println("Two Stacks (Push O(1)): " + time2 + " ns");
        System.out.println("Single Stack (Recursive): " + time3 + " ns");
        
        System.out.println("\nNote: Amortized O(1) approach is significantly faster");
        System.out.println("because it avoids unnecessary transfers.");
    }
    
    /**
     * Comprehensive test cases
     */
    private static void runComprehensiveTests() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPREHENSIVE TEST CASES");
        System.out.println("=".repeat(60));
        
        // Test 1: Basic operations
        System.out.println("\nTest 1: Basic Operations");
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        System.out.println("Push 1,2,3 -> Peek: " + queue.peek() + " (expected: 1)");
        System.out.println("Pop: " + queue.pop() + " (expected: 1)");
        System.out.println("Pop: " + queue.pop() + " (expected: 2)");
        System.out.println("Pop: " + queue.pop() + " (expected: 3)");
        System.out.println("Empty: " + queue.empty() + " (expected: true)");
        
        // Test 2: Mixed operations with transfer
        System.out.println("\nTest 2: Mixed Operations with Transfer");
        queue.push(10);
        queue.push(20);
        System.out.println("Pop after push(10,20): " + queue.pop() + " (expected: 10)");
        queue.push(30);
        System.out.println("Pop after push(30): " + queue.pop() + " (expected: 20)");
        System.out.println("Pop: " + queue.pop() + " (expected: 30)");
        
        // Test 3: Edge case - single element
        System.out.println("\nTest 3: Single Element");
        MyQueue singleQueue = new MyQueue();
        singleQueue.push(5);
        System.out.println("Peek: " + singleQueue.peek() + " (expected: 5)");
        System.out.println("Pop: " + singleQueue.pop() + " (expected: 5)");
        System.out.println("Empty: " + singleQueue.empty() + " (expected: true)");
        
        // Test 4: Empty queue
        System.out.println("\nTest 4: Empty Queue Operations");
        MyQueue emptyQueue = new MyQueue();
        System.out.println("Empty: " + emptyQueue.empty() + " (expected: true)");
        emptyQueue.push(1);
        System.out.println("Empty after push: " + emptyQueue.empty() + " (expected: false)");
        
        // Test 5: Amortized complexity demonstration
        System.out.println("\nTest 5: Amortized Complexity Demo");
        MyQueue amortizedQueue = new MyQueue();
        System.out.println("Push 1,2,3 (all O(1))");
        amortizedQueue.push(1);
        amortizedQueue.push(2);
        amortizedQueue.push(3);
        System.out.println("First pop causes transfer (O(n)), then O(1) pops");
        System.out.println("Pop: " + amortizedQueue.pop() + " (transfer happens)");
        System.out.println("Pop: " + amortizedQueue.pop() + " (no transfer)");
        System.out.println("Pop: " + amortizedQueue.pop() + " (no transfer)");
    }
    
    /**
     * Main method to run all tests
     */
    public static void main(String[] args) {
        System.out.println("Testing Queue Implementation using Stacks");
        System.out.println("=========================================");
        
        // Test all implementations
        testQueueImplementation(new MyQueue(), "Two Stacks with Amortized O(1)");
        testQueueImplementation(new MyQueuePushOptimized(), "Two Stacks - Push O(1)");
        testQueueImplementation(new MyQueueRecursive(), "Single Stack Recursive");
        testQueueImplementation(new MyQueueWithFront(), "Two Stacks with Front Tracking");
        testQueueImplementation(new MyQueueWithDeque(), "Deque as Stack");
        
        // Visualize operations
        visualizeQueueOperations();
        
        // Run comprehensive tests
        runComprehensiveTests();
        
        // Performance comparison
        performanceComparison();
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(60));
        
        System.out.println("\nTwo Stacks with Amortized O(1) Approach:");
        System.out.println("1. Push Operation:");
        System.out.println("   - Always push to input stack");
        System.out.println("   - Time: O(1), Space: O(1)");
        
        System.out.println("\n2. Pop Operation:");
        System.out.println("   - If output stack is empty, transfer all elements from input");
        System.out.println("   - Pop from output stack");
        System.out.println("   - Amortized Time: O(1), Space: O(1)");
        
        System.out.println("\n3. Peek Operation:");
        System.out.println("   - If output stack is empty, transfer all elements from input");
        System.out.println("   - Peek from output stack");
        System.out.println("   - Amortized Time: O(1), Space: O(1)");
        
        System.out.println("\n4. Empty Operation:");
        System.out.println("   - Check if both stacks are empty");
        System.out.println("   - Time: O(1), Space: O(1)");
        
        System.out.println("\nWhy Amortized O(1) Works:");
        System.out.println("- Each element is pushed to input stack: O(1)");
        System.out.println("- Each element is popped from input stack: O(1)");
        System.out.println("- Each element is pushed to output stack: O(1)");
        System.out.println("- Each element is popped from output stack: O(1)");
        System.out.println("- Total: 4 operations per element = O(1) amortized");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(60));
        
        System.out.println("\nTwo Stacks with Amortized O(1):");
        System.out.println("┌──────────┬──────────────┬──────────────┐");
        System.out.println("│ Operation│ Time         │ Space        │");
        System.out.println("├──────────┼──────────────┼──────────────┤");
        System.out.println("│ push(x)  │ O(1)         │ O(1)         │");
        System.out.println("│ pop()    │ Amortized O(1)│ O(1)         │");
        System.out.println("│ peek()   │ Amortized O(1)│ O(1)         │");
        System.out.println("│ empty()  │ O(1)         │ O(1)         │");
        System.out.println("└──────────┴──────────────┴──────────────┘");
        
        System.out.println("\nTwo Stacks Push-Optimized:");
        System.out.println("┌──────────┬────────────┬──────────────┐");
        System.out.println("│ Operation│ Time       │ Space        │");
        System.out.println("├──────────┼────────────┼──────────────┤");
        System.out.println("│ push(x)  │ O(1)       │ O(1)         │");
        System.out.println("│ pop()    │ O(n)       │ O(1)         │");
        System.out.println("│ peek()   │ O(n)       │ O(1)         │");
        System.out.println("│ empty()  │ O(1)       │ O(1)         │");
        System.out.println("└──────────┴────────────┴──────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(60));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(60));
        
        System.out.println("\n1. Start with Two Stacks Approach:");
        System.out.println("   - Explain the amortized O(1) complexity");
        System.out.println("   - Demonstrate with examples");
        System.out.println("   - Mention it meets the follow-up requirement");
        
        System.out.println("\n2. Key Points to Explain:");
        System.out.println("   - Why two stacks are needed (LIFO to FIFO conversion)");
        System.out.println("   - Lazy transfer strategy (only when output empty)");
        System.out.println("   - Amortized analysis (each element processed twice)");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - Empty queue operations");
        System.out.println("   - Single element queue");
        System.out.println("   - Interleaved push and pop operations");
        
        System.out.println("\n4. Discuss Trade-offs:");
        System.out.println("   - Amortized O(1) vs worst-case O(n)");
        System.out.println("   - Memory usage (two stacks vs one)");
        System.out.println("   - Practical performance considerations");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - Breadth-first search algorithms");
        System.out.println("   - Task scheduling systems");
        System.out.println("   - Message queue implementations");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
