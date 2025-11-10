# 225. Implement Stack using Queues

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Stack, Design, Queue  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/implement-stack-using-queues/)

Implement a last-in-first-out (LIFO) stack using only two queues. The implemented stack should support all the functions of a normal stack (`push`, `pop`, `top`, and `empty`).

Implement the `MyStack` class:
- `void push(int x)` Pushes element x to the top of the stack.
- `int pop()` Removes the element on the top of the stack and returns it.
- `int top()` Returns the element on the top of the stack.
- `boolean empty()` Returns `true` if the stack is empty, `false` otherwise.

**Notes:**
- You must use **only** standard operations of a queue, which means:
  - `push to back`
  - `peek/pop from front`
  - `size`
  - `is empty`
- Depending on your language, the queue may not be supported natively. You may simulate a queue using a list or deque (double-ended queue) as long as you use only a queue's standard operations.

**Example 1:**

Input:

["MyStack", "push", "push", "top", "pop", "empty"]

[[], [1], [2], [], [], []]

Output:

[null, null, null, 2, 2, false]

Explanation:

MyStack myStack = new MyStack();

myStack.push(1);

myStack.push(2);

myStack.top(); // return 2

myStack.pop(); // return 2

myStack.empty(); // return False


**Constraints:**
- `1 <= x <= 9`
- At most `100` calls will be made to `push`, `pop`, `top`, and `empty`.
- All the calls to `pop` and `top` are valid.

**Follow-up:** Can you implement the stack using only one queue?

## 🧠 Thought Process

### Initial Thoughts
- Need to implement LIFO behavior using FIFO queues
- Multiple approaches with different trade-offs
- The challenge is to make queue operations simulate stack behavior

### Key Insights
1. **Two Queues Approach**: Use one main queue and one temporary queue
2. **Single Queue Approach**: Rotate elements after each push to maintain stack order
3. **Push-Optimized vs Pop-Optimized**: Choose based on operation frequency
4. **The key insight**: Rearrange elements during push/pop to maintain LIFO order

### Approach Selection
**Chosen Approach:** Single Queue with Rotation  
**Why this approach?** 
- O(1) pop/top, O(n) push - Good if pop/top are frequent
- Uses only one queue (meets follow-up requirement)
- Simple and elegant implementation

## ⚡ Complexity Analysis
- **Push:** O(n) - Rotate n elements after push
- **Pop:** O(1) - Direct front removal
- **Top:** O(1) - Direct front access
- **Empty:** O(1) - Check queue size
- **Space:** O(n) - Store all elements

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The single queue approach is elegant and meets the follow-up requirement

Push operation becomes O(n) but pop/top become O(1)

Alternative two-queue approach makes push O(1) but pop/top O(n)

Choose based on expected operation patterns

🔗 Related Problems
Implement Queue using Stacks

Min Stack

Max Stack

Design Circular Queue

Design Circular Deque
