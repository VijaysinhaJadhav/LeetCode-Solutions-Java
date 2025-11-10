
## Solution.java

```java
/**
 * 622. Design Circular Queue
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Design a circular queue implementation with fixed size.
 * 
 * Key Insights:
 * 1. Array approach: Use fixed array with front and rear pointers
 * 2. Linked list approach: Use circular linked list
 * 3. Need to handle wrap-around using modulo arithmetic
 * 4. Track count or use one empty slot to distinguish empty/full
 * 
 * Approach (Array with Count - RECOMMENDED):
 * 1. Use array of size k
 * 2. Track front index, rear index, and count
 * 3. Empty: count == 0, Full: count == k
 * 4. Use modulo arithmetic for circular behavior
 * 
 * Time Complexity: O(1) for all operations
 * Space Complexity: O(k)
 * 
 * Tags: Array, Linked List, Design, Queue
 */

/**
 * Approach 1: Array with Count Variable - RECOMMENDED
 * Simple and efficient array-based implementation
 */
class MyCircularQueue {
    private int[] queue;
    private int front;
    private int rear;
    private int count;
    private int capacity;

    public MyCircularQueue(int k) {
        this.capacity = k;
        this.queue = new int[k];
        this.front = 0;
        this.rear = -1; // Start with rear at -1 for empty queue
        this.count = 0;
    }
    
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        
        // Calculate new rear position with wrap-around
        rear = (rear + 1) % capacity;
        queue[rear] = value;
        count++;
        return true;
    }
    
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        
        // Move front forward with wrap-around
        front = (front + 1) % capacity;
        count--;
        return true;
    }
    
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return queue[front];
    }
    
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return queue[rear];
    }
    
    public boolean isEmpty() {
        return count == 0;
    }
    
    public boolean isFull() {
        return count == capacity;
    }
}

/**
 * Approach 2: Array without Count (One Empty Slot)
 * Uses one empty slot to distinguish between empty and full states
 */
class MyCircularQueueNoCount {
    private int[] queue;
    private int front;
    private int rear;
    private int capacity;

    public MyCircularQueueNoCount(int k) {
        this.capacity = k + 1; // One extra slot for empty/full distinction
        this.queue = new int[capacity];
        this.front = 0;
        this.rear = 0;
    }
    
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        
        queue[rear] = value;
        rear = (rear + 1) % capacity;
        return true;
    }
    
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        
        front = (front + 1) % capacity;
        return true;
    }
    
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return queue[front];
    }
    
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        // Rear points to next empty slot, so get previous position
        int lastIndex = (rear - 1 + capacity) % capacity;
        return queue[lastIndex];
    }
    
    public boolean isEmpty() {
        return front == rear;
    }
    
    public boolean isFull() {
        return (rear + 1) % capacity == front;
    }
}

/**
 * Approach 3: Linked List Implementation
 * More flexible but slightly more complex
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

class MyCircularQueueLinkedList {
    private ListNode head;
    private ListNode tail;
    private int count;
    private int capacity;

    public MyCircularQueueLinkedList(int k) {
        this.capacity = k;
        this.count = 0;
        this.head = null;
        this.tail = null;
    }
    
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        
        ListNode newNode = new ListNode(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        count++;
        return true;
    }
    
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        
        head = head.next;
        count--;
        
        // If queue becomes empty, reset tail
        if (isEmpty()) {
            tail = null;
        }
        return true;
    }
    
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return head.val;
    }
    
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return tail.val;
    }
    
    public boolean isEmpty() {
        return count == 0;
    }
    
    public boolean isFull() {
        return count == capacity;
    }
}

/**
 * Approach 4: Doubly Linked List with Circular Behavior
 * True circular linked list implementation
 */
class MyCircularQueueCircularLinkedList {
    private class Node {
        int value;
        Node next;
        Node prev;
        
        Node(int value) {
            this.value = value;
        }
    }
    
    private Node head, tail;
    private int count;
    private int capacity;

    public MyCircularQueueCircularLinkedList(int k) {
        this.capacity = k;
        this.count = 0;
        this.head = null;
        this.tail = null;
    }
    
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        
        Node newNode = new Node(value);
        if (isEmpty()) {
            head = tail = newNode;
            // Make it circular
            head.next = head;
            head.prev = head;
        } else {
            newNode.prev = tail;
            newNode.next = head;
            tail.next = newNode;
            head.prev = newNode;
            tail = newNode;
        }
        count++;
        return true;
    }
    
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        
        if (count == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = tail;
            tail.next = head;
        }
        count--;
        return true;
    }
    
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return head.value;
    }
    
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return tail.value;
    }
    
    public boolean isEmpty() {
        return count == 0;
    }
    
    public boolean isFull() {
        return count == capacity;
    }
}

/**
 * Approach 5: Array with Visualization
 * Same as Approach 1 but with visualization capabilities
 */
class MyCircularQueueVisual {
    private int[] queue;
    private int front;
    private int rear;
    private int count;
    private int capacity;
    private int operationCount;

    public MyCircularQueueVisual(int k) {
        this.capacity = k;
        this.queue = new int[k];
        this.front = 0;
        this.rear = -1;
        this.count = 0;
        this.operationCount = 0;
        System.out.println("Initialized Circular Queue with capacity: " + k);
        visualize("Initialization");
    }
    
    public boolean enQueue(int value) {
        operationCount++;
        System.out.println("\nOperation " + operationCount + ": enQueue(" + value + ")");
        
        if (isFull()) {
            System.out.println("Queue is full. Cannot enqueue " + value);
            visualize("Failed enQueue");
            return false;
        }
        
        rear = (rear + 1) % capacity;
        queue[rear] = value;
        count++;
        System.out.println("Successfully enqueued " + value + " at position " + rear);
        visualize("After enQueue");
        return true;
    }
    
    public boolean deQueue() {
        operationCount++;
        System.out.println("\nOperation " + operationCount + ": deQueue()");
        
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot dequeue");
            visualize("Failed deQueue");
            return false;
        }
        
        int removedValue = queue[front];
        front = (front + 1) % capacity;
        count--;
        System.out.println("Successfully dequeued " + removedValue + " from position " + 
                         ((front - 1 + capacity) % capacity));
        visualize("After deQueue");
        return true;
    }
    
    public int Front() {
        operationCount++;
        System.out.println("\nOperation " + operationCount + ": Front()");
        int result = isEmpty() ? -1 : queue[front];
        System.out.println("Front element: " + result);
        return result;
    }
    
    public int Rear() {
        operationCount++;
        System.out.println("\nOperation " + operationCount + ": Rear()");
        int result = isEmpty() ? -1 : queue[rear];
        System.out.println("Rear element: " + result);
        return result;
    }
    
    public boolean isEmpty() {
        boolean result = count == 0;
        System.out.println("isEmpty(): " + result);
        return result;
    }
    
    public boolean isFull() {
        boolean result = count == capacity;
        System.out.println("isFull(): " + result);
        return result;
    }
    
    private void visualize(String operation) {
        System.out.println("Queue State (" + operation + "):");
        System.out.println("  Capacity: " + capacity + ", Count: " + count);
        System.out.println("  Front: " + front + ", Rear: " + rear);
        
        System.out.print("  Array: [");
        for (int i = 0; i < capacity; i++) {
            if (i > 0) System.out.print(", ");
            
            // Highlight current position
            if (i == front && i == rear && !isEmpty()) {
                System.out.print("F/R:" + queue[i]);
            } else if (i == front && !isEmpty()) {
                System.out.print("F:" + queue[i]);
            } else if (i == rear && !isEmpty()) {
                System.out.print("R:" + queue[i]);
            } else {
                System.out.print(queue[i]);
            }
        }
        System.out.println("]");
        
        // Show logical order
        if (!isEmpty()) {
            System.out.print("  Logical order: ");
            int current = front;
            for (int i = 0; i < count; i++) {
                if (i > 0) System.out.print(" -> ");
                System.out.print(queue[current]);
                current = (current + 1) % capacity;
            }
            System.out.println();
        }
    }
}

/**
 * Test class to verify all implementations
 */
public class Solution {
    /**
     * Helper method to test a circular queue implementation
     */
    private static void testImplementation(String implName, Runnable test) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Testing " + implName);
        System.out.println("=".repeat(60));
        test.run();
    }
    
    /**
     * Performance comparison for different implementations
     */
    private static void compareImplementations(int capacity, int numOperations) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Capacity: " + capacity + ", Operations: " + numOperations);
        System.out.println("=================================");
        
        long startTime, endTime;
        
        // Array with count
        startTime = System.nanoTime();
        MyCircularQueue queue1 = new MyCircularQueue(capacity);
        for (int i = 0; i < numOperations; i++) {
            queue1.enQueue(i);
            if (i % 3 == 0) queue1.deQueue();
            if (i % 5 == 0) queue1.Front();
            if (i % 7 == 0) queue1.Rear();
        }
        endTime = System.nanoTime();
        System.out.printf("Array with Count:    %8d ns%n", (endTime - startTime));
        
        // Array without count
        startTime = System.nanoTime();
        MyCircularQueueNoCount queue2 = new MyCircularQueueNoCount(capacity);
        for (int i = 0; i < numOperations; i++) {
            queue2.enQueue(i);
            if (i % 3 == 0) queue2.deQueue();
            if (i % 5 == 0) queue2.Front();
            if (i % 7 == 0) queue2.Rear();
        }
        endTime = System.nanoTime();
        System.out.printf("Array No Count:      %8d ns%n", (endTime - startTime));
        
        // Linked list
        startTime = System.nanoTime();
        MyCircularQueueLinkedList queue3 = new MyCircularQueueLinkedList(capacity);
        for (int i = 0; i < numOperations; i++) {
            queue3.enQueue(i);
            if (i % 3 == 0) queue3.deQueue();
            if (i % 5 == 0) queue3.Front();
            if (i % 7 == 0) queue3.Rear();
        }
        endTime = System.nanoTime();
        System.out.printf("Linked List:         %8d ns%n", (endTime - startTime));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        System.out.println("Testing Design Circular Queue:");
        System.out.println("==============================");
        
        // Test case 1: Basic operations from example
        testImplementation("Array with Count - Basic Example", () -> {
            MyCircularQueue queue = new MyCircularQueue(3);
            System.out.println("enQueue(1): " + queue.enQueue(1)); // true
            System.out.println("enQueue(2): " + queue.enQueue(2)); // true
            System.out.println("enQueue(3): " + queue.enQueue(3)); // true
            System.out.println("enQueue(4): " + queue.enQueue(4)); // false
            System.out.println("Rear(): " + queue.Rear());         // 3
            System.out.println("isFull(): " + queue.isFull());     // true
            System.out.println("deQueue(): " + queue.deQueue());   // true
            System.out.println("enQueue(4): " + queue.enQueue(4)); // true
            System.out.println("Rear(): " + queue.Rear());         // 4
        });
        
        // Test case 2: Empty queue operations
        testImplementation("Array with Count - Empty Queue", () -> {
            MyCircularQueue queue = new MyCircularQueue(2);
            System.out.println("isEmpty(): " + queue.isEmpty());   // true
            System.out.println("Front(): " + queue.Front());       // -1
            System.out.println("Rear(): " + queue.Rear());         // -1
            System.out.println("deQueue(): " + queue.deQueue());   // false
        });
        
        // Test case 3: Full cycle test
        testImplementation("Array with Count - Full Cycle", () -> {
            MyCircularQueue queue = new MyCircularQueue(3);
            // Fill the queue
            for (int i = 1; i <= 3; i++) {
                System.out.println("enQueue(" + i + "): " + queue.enQueue(i));
            }
            System.out.println("isFull(): " + queue.isFull()); // true
            
            // Remove all elements
            for (int i = 1; i <= 3; i++) {
                System.out.println("deQueue(): " + queue.deQueue());
            }
            System.out.println("isEmpty(): " + queue.isEmpty()); // true
            
            // Add again to test wrap-around
            for (int i = 4; i <= 6; i++) {
                System.out.println("enQueue(" + i + "): " + queue.enQueue(i));
            }
            System.out.println("Front(): " + queue.Front()); // 4
            System.out.println("Rear(): " + queue.Rear());   // 6
        });
        
        // Test case 4: Single element queue
        testImplementation("Array with Count - Single Element", () -> {
            MyCircularQueue queue = new MyCircularQueue(1);
            System.out.println("enQueue(1): " + queue.enQueue(1)); // true
            System.out.println("isFull(): " + queue.isFull());     // true
            System.out.println("Front(): " + queue.Front());       // 1
            System.out.println("Rear(): " + queue.Rear());         // 1
            System.out.println("enQueue(2): " + queue.enQueue(2)); // false
            System.out.println("deQueue(): " + queue.deQueue());   // true
            System.out.println("isEmpty(): " + queue.isEmpty());   // true
        });
        
        // Test case 5: Compare all implementations produce same results
        testImplementation("Implementation Comparison", () -> {
            int capacity = 3;
            MyCircularQueue q1 = new MyCircularQueue(capacity);
            MyCircularQueueNoCount q2 = new MyCircularQueueNoCount(capacity);
            MyCircularQueueLinkedList q3 = new MyCircularQueueLinkedList(capacity);
            
            // Test sequence
            int[] operations = {1, 2, 3, 4}; // enQueue values
            
            boolean allSame = true;
            for (int i = 0; i < operations.length; i++) {
                boolean r1 = q1.enQueue(operations[i]);
                boolean r2 = q2.enQueue(operations[i]);
                boolean r3 = q3.enQueue(operations[i]);
                
                if (r1 != r2 || r1 != r3) {
                    allSame = false;
                    break;
                }
            }
            
            // Check Front and Rear
            int f1 = q1.Front();
            int f2 = q2.Front();
            int f3 = q3.Front();
            
            int r1 = q1.Rear();
            int r2 = q2.Rear();
            int r3 = q3.Rear();
            
            allSame = allSame && (f1 == f2) && (f1 == f3) && (r1 == r2) && (r1 == r3);
            
            System.out.println("All implementations produce same results: " + 
                             (allSame ? "PASSED" : "FAILED"));
        });
        
        // Visualization test
        testImplementation("Visualization Example", () -> {
            MyCircularQueueVisual queue = new MyCircularQueueVisual(4);
            queue.enQueue(10);
            queue.enQueue(20);
            queue.enQueue(30);
            queue.deQueue();
            queue.enQueue(40);
            queue.enQueue(50);
            queue.Front();
            queue.Rear();
            queue.deQueue();
            queue.deQueue();
            queue.enQueue(60);
        });
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Performance Analysis");
        System.out.println("=".repeat(60));
        
        compareImplementations(100, 1000);
        compareImplementations(1000, 10000);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nArray with Count Approach (RECOMMENDED):");
        System.out.println("Key Components:");
        System.out.println("1. Array: Fixed-size array to store elements");
        System.out.println("2. Front: Index of the first element");
        System.out.println("3. Rear: Index of the last element");
        System.out.println("4. Count: Number of elements currently in queue");
        System.out.println("5. Capacity: Maximum size of the queue");
        
        System.out.println("\nOperations:");
        System.out.println("enQueue(value):");
        System.out.println("  - If full, return false");
        System.out.println("  - rear = (rear + 1) % capacity");
        System.out.println("  - queue[rear] = value");
        System.out.println("  - count++");
        
        System.out.println("deQueue():");
        System.out.println("  - If empty, return false");
        System.out.println("  - front = (front + 1) % capacity");
        System.out.println("  - count--");
        
        System.out.println("\nWhy modulo arithmetic works:");
        System.out.println("When rear reaches capacity-1, next enQueue:");
        System.out.println("  rear = (capacity-1 + 1) % capacity = 0");
        System.out.println("This creates the circular behavior");
        
        System.out.println("\nEmpty/Full Detection:");
        System.out.println("Empty: count == 0");
        System.out.println("Full: count == capacity");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nArray with Count Approach:");
        System.out.println("Time Complexity: O(1) for all operations");
        System.out.println("  - enQueue: O(1) - constant time insertion");
        System.out.println("  - deQueue: O(1) - constant time removal");
        System.out.println("  - Front/Rear: O(1) - direct array access");
        System.out.println("  - isEmpty/isFull: O(1) - count comparison");
        System.out.println("Space Complexity: O(k)");
        System.out.println("  - Array of size k");
        System.out.println("  - Constant extra space for pointers and count");
        
        System.out.println("\nLinked List Approach:");
        System.out.println("Time Complexity: O(1) for all operations");
        System.out.println("  - All operations are constant time");
        System.out.println("Space Complexity: O(k)");
        System.out.println("  - k nodes, each with value and pointers");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with array-based approach (most common)");
        System.out.println("2. Explain the key components clearly:");
        System.out.println("   - Array storage");
        System.out.println("   - Front and rear pointers");
        System.out.println("   - Count variable for empty/full detection");
        System.out.println("3. Demonstrate circular behavior with modulo arithmetic");
        System.out.println("4. Handle edge cases:");
        System.out.println("   - Empty queue (Front/Rear return -1)");
        System.out.println("   - Full queue (enQueue returns false)");
        System.out.println("   - Single element queue");
        System.out.println("5. Mention alternative approaches");
        System.out.println("6. Discuss time and space complexity");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("- Not handling empty/full states correctly");
        System.out.println("- Incorrect modulo arithmetic for wrap-around");
        System.out.println("- Forgetting to update count variable");
        System.out.println("- Not initializing rear to -1 for empty queue");
        System.out.println("- Confusing array indices with queue positions");
        
        System.out.println("\nAll tests completed!");
    }
}
