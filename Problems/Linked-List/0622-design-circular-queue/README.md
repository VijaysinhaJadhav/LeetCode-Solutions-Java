# 622. Design Circular Queue

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Linked List, Design, Queue  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Uber

[LeetCode Link](https://leetcode.com/problems/design-circular-queue/)

Design your implementation of the circular queue. The circular queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle and the last position is connected back to the first position to make a circle. It is also called "Ring Buffer".

One of the benefits of the circular queue is that we can make use of the spaces in front of the queue. In a normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of the queue. But using the circular queue, we can use the space to store new values.

Implementation the `MyCircularQueue` class:

- `MyCircularQueue(k)` Initializes the object with the size of the queue to be `k`.
- `int Front()` Gets the front item from the queue. If the queue is empty, return `-1`.
- `int Rear()` Gets the last item from the queue. If the queue is empty, return `-1`.
- `boolean enQueue(int value)` Inserts an element into the circular queue. Return `true` if the operation is successful.
- `boolean deQueue()` Deletes an element from the circular queue. Return `true` if the operation is successful.
- `boolean isEmpty()` Checks whether the circular queue is empty or not.
- `boolean isFull()` Checks whether the circular queue is full or not.

You must solve the problem without using the built-in queue data structure in your programming language. 

**Example 1:**

Input

["MyCircularQueue", "enQueue", "enQueue", "enQueue", "enQueue", "Rear", "isFull", "deQueue", "enQueue", "Rear"]

[[3], [1], [2], [3], [4], [], [], [], [4], []]

Output

[null, true, true, true, false, 3, true, true, true, 4]

Explanation

MyCircularQueue myCircularQueue = new MyCircularQueue(3);

myCircularQueue.enQueue(1); // return True

myCircularQueue.enQueue(2); // return True

myCircularQueue.enQueue(3); // return True

myCircularQueue.enQueue(4); // return False

myCircularQueue.Rear(); // return 3

myCircularQueue.isFull(); // return True

myCircularQueue.deQueue(); // return True

myCircularQueue.enQueue(4); // return True

myCircularQueue.Rear(); // return 4


**Constraints:**
- `1 <= k <= 1000`
- `0 <= value <= 1000`
- At most `3000` calls will be made to `enQueue`, `deQueue`, `Front`, `Rear`, `isEmpty`, and `isFull`.

## 🧠 Thought Process

### Initial Thoughts
- Need to implement a circular queue (ring buffer) with fixed size
- Multiple approaches: array-based, linked list-based
- Must handle wrap-around when reaching the end of the buffer
- Need to track front, rear, and count/size of elements

### Key Insights
1. **Array Approach**: Use fixed-size array with front and rear pointers
2. **Linked List Approach**: Use circular linked list with head and tail
3. **Empty/Full Detection**: Can use count variable or leave one empty slot
4. **Wrap-around**: Use modulo arithmetic for circular behavior

### Approach Selection
**Chosen Approach:** Array with Count Variable  
**Why this approach?** 
- O(1) time complexity for all operations
- O(k) space complexity
- Simple and efficient
- Easy to implement and understand

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) for all operations
- **Space Complexity:** O(k) where k is the queue size

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use an array of size k to store elements

Track front index, rear index, and count of elements

Use modulo arithmetic for circular behavior

Empty when count == 0, full when count == k

Front and Rear operations return -1 when empty

🔗 Related Problems
Design Circular Deque

Implement Queue using Stacks

Implement Stack using Queues

Design Snake Game
