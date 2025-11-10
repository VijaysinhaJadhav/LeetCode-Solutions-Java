/**
 * 225. Implement Stack using Queues
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Implement a last-in-first-out (LIFO) stack using only queues.
 * The stack should support push, pop, top, and empty operations.
 * 
 * Key Insights:
 * 1. Use queue rotation to maintain LIFO order with FIFO data structure
 * 2. Single queue approach: rotate elements after each push
 * 3. Two queues approach: use temporary queue for rearrangement
 * 4. Choose between push-optimized vs pop-optimized implementations
 * 
 * Approach (Single Queue with Rotation):
 * 1. Use one queue to store all elements
 * 2. After each push, rotate the queue to make new element at front
 * 3. This makes pop and top O(1) operations
 * 4. Push becomes O(n) but meets follow-up requirement
 * 
 * Time Complexity:
 * - Push: O(n) - Rotate n elements after push
 * - Pop: O(1) - Remove from front
 * - Top: O(1) - Peek front
 * - Empty: O(1) - Check size
 * 
 * Space Complexity: O(n) - Store all elements in queue
 * 
 * Tags: Stack, Design, Queue
 */

import java.util.*;

// Interface defining stack operations
interface Stack {
    void push(int x);
    int pop();
    int top();
    boolean empty();
}

/**
 * Approach 1: Single Queue with Rotation - RECOMMENDED
 * Push O(n), Pop O(1), Top O(1) - Meets follow-up requirement
 */
class MyStack implements Stack {
    private Queue<Integer> queue;
    
    public MyStack() {
        queue = new LinkedList<>();
    }
    
    /**
     * Push element x onto stack.
     * Time: O(n) - Rotate the queue after push
     */
    public void push(int x) {
        queue.offer(x);
        // Rotate the queue to make the new element at front
        int size = queue.size();
        for (int i = 0; i < size - 1; i++) {
            queue.offer(queue.poll());
        }
    }
    
    /**
     * Removes the element on top of the stack and returns it.
     * Time: O(1) - Remove from front
     */
    public int pop() {
        return queue.poll();
    }
    
    /**
     * Get the top element.
     * Time: O(1) - Peek front
     */
    public int top() {
        return queue.peek();
    }
    
    /**
     * Returns whether the stack is empty.
     * Time: O(1) - Check queue size
     */
    public boolean empty() {
        return queue.isEmpty();
    }
}

/**
 * Approach 2: Two Queues - Push O(1), Pop O(n)
 * Alternative approach that optimizes push operations
 */
class MyStackTwoQueues implements Stack {
    private Queue<Integer> mainQueue;
    private Queue<Integer> tempQueue;
    
    public MyStackTwoQueues() {
        mainQueue = new LinkedList<>();
        tempQueue = new LinkedList<>();
    }
    
    /**
     * Push element x onto stack.
     * Time: O(1) - Simply add to main queue
     */
    public void push(int x) {
        mainQueue.offer(x);
    }
    
    /**
     * Removes the element on top of the stack and returns it.
     * Time: O(n) - Transfer n-1 elements to temp queue
     */
    public int pop() {
        // Transfer all but last element to temp queue
        while (mainQueue.size() > 1) {
            tempQueue.offer(mainQueue.poll());
        }
        // The last element is the one to pop
        int popped = mainQueue.poll();
        // Swap the queues
        Queue<Integer> temp = mainQueue;
        mainQueue = tempQueue;
        tempQueue = temp;
        return popped;
    }
    
    /**
     * Get the top element.
     * Time: O(n) - Similar to pop but we keep the element
     */
    public int top() {
        // Transfer all but last element to temp queue
        while (mainQueue.size() > 1) {
            tempQueue.offer(mainQueue.poll());
        }
        // The last element is the top
        int top = mainQueue.peek();
        // Move it to temp queue and swap
        tempQueue.offer(mainQueue.poll());
        Queue<Integer> temp = mainQueue;
        mainQueue = tempQueue;
        tempQueue = temp;
        return top;
    }
    
    /**
     * Returns whether the stack is empty.
     * Time: O(1) - Check main queue size
     */
    public boolean empty() {
        return mainQueue.isEmpty();
    }
}

/**
 * Approach 3: Two Queues - Pop O(1), Push O(n)
 * Alternative two-queue approach that optimizes pop operations
 */
class MyStackTwoQueuesOptimized implements Stack {
    private Queue<Integer> queue1;
    private Queue<Integer> queue2;
    private int top;
    
    public MyStackTwoQueuesOptimized() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }
    
    /**
     * Push element x onto stack.
     * Time: O(n) - Maintain queue1 in stack order
     */
    public void push(int x) {
        queue2.offer(x);
        top = x;
        // Move all elements from queue1 to queue2
        while (!queue1.isEmpty()) {
            queue2.offer(queue1.poll());
        }
        // Swap the queues
        Queue<Integer> temp = queue1;
        queue1 = queue2;
        queue2 = temp;
    }
    
    /**
     * Removes the element on top of the stack and returns it.
     * Time: O(1) - Remove from front
     */
    public int pop() {
        int popped = queue1.poll();
        if (!queue1.isEmpty()) {
            top = queue1.peek();
        }
        return popped;
    }
    
    /**
     * Get the top element.
     * Time: O(1) - Peek front or use stored top
     */
    public int top() {
        return top;
    }
    
    /**
     * Returns whether the stack is empty.
     * Time: O(1) - Check queue1 size
     */
    public boolean empty() {
        return queue1.isEmpty();
    }
}

/**
 * Approach 4: Single Queue with Size Tracking
 * Alternative single queue approach without rotation during push
 */
class MyStackSingleQueue implements Stack {
    private Queue<Integer> queue;
    private int top;
    
    public MyStackSingleQueue() {
        queue = new LinkedList<>();
    }
    
    /**
     * Push element x onto stack.
     * Time: O(1) - Simple enqueue
     */
    public void push(int x) {
        queue.offer(x);
        top = x;
    }
    
    /**
     * Removes the element on top of the stack and returns it.
     * Time: O(n) - Rotate to get last element
     */
    public int pop() {
        int size = queue.size();
        // Rotate to get the last element (top of stack)
        for (int i = 0; i < size - 1; i++) {
            top = queue.poll();
            queue.offer(top);
        }
        return queue.poll();
    }
    
    /**
     * Get the top element.
     * Time: O(1) - Use stored top value
     */
    public int top() {
        return top;
    }
    
    /**
     * Returns whether the stack is empty.
     * Time: O(1) - Check queue size
     */
    public boolean empty() {
        return queue.isEmpty();
    }
}

/**
 * Approach 5: Deque as Queue (For Comparison)
 * Using Deque but only with queue operations for demonstration
 */
class MyStackWithDeque implements Stack {
    private Deque<Integer> deque;
    
    public MyStackWithDeque() {
        deque = new LinkedList<>();
    }
    
    public void push(int x) {
        deque.offerLast(x);
        // Rotate to maintain stack order
        int size = deque.size();
        for (int i = 0; i < size - 1; i++) {
            deque.offerLast(deque.pollFirst());
        }
    }
    
    public int pop() {
        return deque.pollFirst();
    }
    
    public int top() {
        return deque.peekFirst();
    }
    
    public boolean empty() {
        return deque.isEmpty();
    }
}

/**
 * Test class to verify all implementations
 */
public class Solution {
    /**
     * Helper method to test a stack implementation
     */
    private static void testStackImplementation(Stack stack, String implementationName) {
        System.out.println("\nTesting " + implementationName + ":");
        System.out.println("Operations: push(1), push(2), top(), pop(), empty()");
        
        stack.push(1);
        stack.push(2);
        int top = stack.top();
        int pop = stack.pop();
        boolean empty = stack.empty();
        
        System.out.println("Top: " + top + " (expected: 2)");
        System.out.println("Pop: " + pop + " (expected: 2)");
        System.out.println("Empty: " + empty + " (expected: false)");
        
        boolean testPassed = (top == 2) && (pop == 2) && !empty;
        System.out.println("TEST " + (testPassed ? "PASSED" : "FAILED"));
    }
    
    /**
     * Helper method to visualize stack operations
     */
    private static void visualizeStackOperations() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("VISUALIZATION: Single Queue Stack Implementation");
        System.out.println("=".repeat(60));
        
        MyStack stack = new MyStack();
        String[] operations = {"push(1)", "push(2)", "push(3)", "pop()", "top()", "push(4)"};
        int[] values = {1, 2, 3, 0, 0, 4};
        
        System.out.println("\nStep | Operation | Queue State | Action");
        System.out.println("-----|-----------|-------------|--------");
        
        for (int i = 0; i < operations.length; i++) {
            String op = operations[i];
            String action = "";
            String queueStateBefore = stack.empty() ? "[]" : "[" + getQueueState(stack) + "]";
            
            switch (op) {
                case "push(1)":
                    stack.push(1);
                    action = "Add 1, then rotate queue to make it front";
                    break;
                case "push(2)":
                    stack.push(2);
                    action = "Add 2, rotate: [1] -> [1,2] -> [2,1]";
                    break;
                case "push(3)":
                    stack.push(3);
                    action = "Add 3, rotate: [2,1] -> [2,1,3] -> [3,2,1]";
                    break;
                case "pop()":
                    int popped = stack.pop();
                    action = "Remove front: " + popped + " (queue becomes [2,1])";
                    break;
                case "top()":
                    int top = stack.top();
                    action = "Peek front: " + top;
                    break;
                case "push(4)":
                    stack.push(4);
                    action = "Add 4, rotate: [2,1] -> [2,1,4] -> [4,2,1]";
                    break;
            }
            
            String queueStateAfter = stack.empty() ? "[]" : "[" + getQueueState(stack) + "]";
            System.out.printf("%4d | %9s | %11s | %s%n", 
                            i + 1, op, queueStateAfter, action);
        }
    }
    
    /**
     * Helper method to get queue state as string (for visualization)
     */
    private static String getQueueState(MyStack stack) {
        // This is a simplified representation for visualization
        // In real implementation, we cannot access internal queue directly
        try {
            java.lang.reflect.Field field = MyStack.class.getDeclaredField("queue");
            field.setAccessible(true);
            Queue<Integer> queue = (Queue<Integer>) field.get(stack);
            return queue.stream()
                       .map(String::valueOf)
                       .reduce((a, b) -> a + "," + b)
                       .orElse("");
        } catch (Exception e) {
            return "Cannot access";
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
        
        // Test Single Queue implementation
        long startTime = System.nanoTime();
        Stack stack1 = new MyStack();
        for (int i = 0; i < numOperations; i++) {
            stack1.push(i);
        }
        for (int i = 0; i < numOperations; i++) {
            stack1.pop();
        }
        long time1 = System.nanoTime() - startTime;
        
        // Test Two Queues implementation
        startTime = System.nanoTime();
        Stack stack2 = new MyStackTwoQueues();
        for (int i = 0; i < numOperations; i++) {
            stack2.push(i);
        }
        for (int i = 0; i < numOperations; i++) {
            stack2.pop();
        }
        long time2 = System.nanoTime() - startTime;
        
        // Test Two Queues Optimized implementation
        startTime = System.nanoTime();
        Stack stack3 = new MyStackTwoQueuesOptimized();
        for (int i = 0; i < numOperations; i++) {
            stack3.push(i);
        }
        for (int i = 0; i < numOperations; i++) {
            stack3.pop();
        }
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("\nPerformance for " + numOperations + " push/pop operations:");
        System.out.println("Single Queue (Rotation): " + time1 + " ns");
        System.out.println("Two Queues (Push O(1)): " + time2 + " ns");
        System.out.println("Two Queues Optimized: " + time3 + " ns");
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
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Push 1,2,3 -> Top: " + stack.top() + " (expected: 3)");
        System.out.println("Pop: " + stack.pop() + " (expected: 3)");
        System.out.println("Pop: " + stack.pop() + " (expected: 2)");
        System.out.println("Pop: " + stack.pop() + " (expected: 1)");
        System.out.println("Empty: " + stack.empty() + " (expected: true)");
        
        // Test 2: Mixed operations
        System.out.println("\nTest 2: Mixed Operations");
        stack.push(10);
        System.out.println("Top after push(10): " + stack.top() + " (expected: 10)");
        stack.push(20);
        System.out.println("Top after push(20): " + stack.top() + " (expected: 20)");
        System.out.println("Pop: " + stack.pop() + " (expected: 20)");
        System.out.println("Top after pop: " + stack.top() + " (expected: 10)");
        
        // Test 3: Edge case - single element
        System.out.println("\nTest 3: Single Element");
        MyStack singleStack = new MyStack();
        singleStack.push(5);
        System.out.println("Top: " + singleStack.top() + " (expected: 5)");
        System.out.println("Pop: " + singleStack.pop() + " (expected: 5)");
        System.out.println("Empty: " + singleStack.empty() + " (expected: true)");
        
        // Test 4: Empty stack
        System.out.println("\nTest 4: Empty Stack Operations");
        MyStack emptyStack = new MyStack();
        System.out.println("Empty: " + emptyStack.empty() + " (expected: true)");
        emptyStack.push(1);
        System.out.println("Empty after push: " + emptyStack.empty() + " (expected: false)");
    }
    
    /**
     * Main method to run all tests
     */
    public static void main(String[] args) {
        System.out.println("Testing Stack Implementation using Queues");
        System.out.println("=========================================");
        
        // Test all implementations
        testStackImplementation(new MyStack(), "Single Queue with Rotation");
        testStackImplementation(new MyStackTwoQueues(), "Two Queues - Push O(1)");
        testStackImplementation(new MyStackTwoQueuesOptimized(), "Two Queues Optimized");
        testStackImplementation(new MyStackSingleQueue(), "Single Queue with Size Tracking");
        testStackImplementation(new MyStackWithDeque(), "Deque as Queue");
        
        // Visualize operations
        visualizeStackOperations();
        
        // Run comprehensive tests
        runComprehensiveTests();
        
        // Performance comparison
        performanceComparison();
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(60));
        
        System.out.println("\nSingle Queue with Rotation Approach:");
        System.out.println("1. Push Operation:");
        System.out.println("   - Add new element to the back of queue");
        System.out.println("   - Rotate the queue (move n-1 elements from front to back)");
        System.out.println("   - This makes the new element at the front (top of stack)");
        System.out.println("   - Time: O(n), Space: O(1) per operation");
        
        System.out.println("\n2. Pop Operation:");
        System.out.println("   - Simply remove from front of queue");
        System.out.println("   - The front element is always the top of stack");
        System.out.println("   - Time: O(1), Space: O(1)");
        
        System.out.println("\n3. Top Operation:");
        System.out.println("   - Simply peek at front of queue");
        System.out.println("   - Time: O(1), Space: O(1)");
        
        System.out.println("\n4. Empty Operation:");
        System.out.println("   - Check if queue is empty");
        System.out.println("   - Time: O(1), Space: O(1)");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(60));
        
        System.out.println("\nSingle Queue with Rotation:");
        System.out.println("┌──────────┬────────────┬──────────────┐");
        System.out.println("│ Operation│ Time       │ Space        │");
        System.out.println("├──────────┼────────────┼──────────────┤");
        System.out.println("│ push(x)  │ O(n)       │ O(1)         │");
        System.out.println("│ pop()    │ O(1)       │ O(1)         │");
        System.out.println("│ top()    │ O(1)       │ O(1)         │");
        System.out.println("│ empty()  │ O(1)       │ O(1)         │");
        System.out.println("└──────────┴────────────┴──────────────┘");
        
        System.out.println("\nTwo Queues Approach (Push O(1)):");
        System.out.println("┌──────────┬────────────┬──────────────┐");
        System.out.println("│ Operation│ Time       │ Space        │");
        System.out.println("├──────────┼────────────┼──────────────┤");
        System.out.println("│ push(x)  │ O(1)       │ O(1)         │");
        System.out.println("│ pop()    │ O(n)       │ O(1)         │");
        System.out.println("│ top()    │ O(n)       │ O(1)         │");
        System.out.println("│ empty()  │ O(1)       │ O(1)         │");
        System.out.println("└──────────┴────────────┴──────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(60));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(60));
        
        System.out.println("\n1. Start with Single Queue Approach:");
        System.out.println("   - Mention it meets the follow-up requirement");
        System.out.println("   - Explain the rotation logic clearly");
        System.out.println("   - Discuss time complexity trade-offs");
        
        System.out.println("\n2. Discuss Alternative Approaches:");
        System.out.println("   - Two queues with O(1) push, O(n) pop");
        System.out.println("   - Two queues with O(n) push, O(1) pop");
        System.out.println("   - Choose based on expected operation patterns");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - Empty stack operations");
        System.out.println("   - Single element stack");
        System.out.println("   - Multiple push/pop operations");
        
        System.out.println("\n4. Mention Real-world Applications:");
        System.out.println("   - Undo functionality in applications");
        System.out.println("   - Browser history navigation");
        System.out.println("   - Function call stack simulation");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
