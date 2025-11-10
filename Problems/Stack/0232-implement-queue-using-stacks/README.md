# 232. Implement Queue using Stacks

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Stack, Design, Queue  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/implement-queue-using-stacks/)

Implement a first-in-first-out (FIFO) queue using only two stacks. The implemented queue should support all the functions of a normal queue (`push`, `peek`, `pop`, and `empty`).

Implement the `MyQueue` class:
- `void push(int x)` Pushes element x to the back of the queue.
- `int pop()` Removes the element from the front of the queue and returns it.
- `int peek()` Returns the element at the front of the queue.
- `boolean empty()` Returns `true` if the queue is empty, `false` otherwise.

**Notes:**
- You must use **only** standard operations of a stack, which means:
  - `push to top`
  - `peek/pop from top`
  - `size`
  - `is empty`
- Depending on your language, the stack may not be supported natively. You may simulate a stack using a list or deque (double-ended queue) as long as you use only a stack's standard operations.

**Example 1:**

Input:

["MyQueue", "push", "push", "peek", "pop", "empty"]

[[], [1], [2], [], [], []]

Output:

[null, null, null, 1, 1, false]

Explanation:

MyQueue myQueue = new MyQueue();

myQueue.push(1); // queue is: [1]

myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)

myQueue.peek(); // return 1

myQueue.pop(); // return 1, queue is [2]

myQueue.empty(); // return false


**Constraints:**
- `1 <= x <= 9`
- At most `100` calls will be made to `push`, `pop`, `peek`, and `empty`.
- All the calls to `pop` and `peek` are valid.

**Follow-up:** Can you implement the queue such that each operation is **amortized** O(1) time complexity?

## 🧠 Thought Process

### Initial Thoughts
- Need to implement FIFO behavior using LIFO stacks
- Multiple approaches with different trade-offs
- The challenge is to make stack operations simulate queue behavior

### Key Insights
1. **Two Stacks Approach**: Use one stack for input, another for output
2. **Amortized O(1)**: Transfer elements only when output stack is empty
3. **Lazy Evaluation**: Delay expensive operations until necessary
4. **The key insight**: Use two stacks to reverse element order when needed

### Approach Selection
**Chosen Approach:** Two Stacks with Amortized O(1)  
**Why this approach?** 
- Amortized O(1) for all operations
- Efficient and practical
- Meets follow-up requirement
- Simple and elegant implementation

## ⚡ Complexity Analysis
- **Push:** O(1) - Direct push to input stack
- **Pop:** Amortized O(1) - Transfer elements only when output is empty
- **Peek:** Amortized O(1) - Similar to pop
- **Empty:** O(1) - Check both stacks
- **Space:** O(n) - Store all elements across two stacks

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The two stacks approach provides amortized O(1) operations

Transfer happens only when output stack is empty

Each element is pushed and popped exactly twice

This is the most efficient and practical solution

🔗 Related Problems
Implement Stack using Queues

Min Stack

Max Stack

Design Circular Queue

Design Circular Deque

