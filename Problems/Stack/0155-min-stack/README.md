# 155. Min Stack

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Stack, Design  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/min-stack/)

Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

Implement the `MinStack` class:
- `MinStack()` initializes the stack object.
- `void push(int val)` pushes the element `val` onto the stack.
- `void pop()` removes the element on the top of the stack.
- `int top()` gets the top element of the stack.
- `int getMin()` retrieves the minimum element in the stack.

You must implement a solution with `O(1)` time complexity for each function.

**Example 1:**

Input

["MinStack","push","push","push","getMin","pop","top","getMin"]

[[],[-2],[0],[-3],[],[],[],[]]

Output

[null,null,null,null,-3,null,0,-2]

Explanation

MinStack minStack = new MinStack();

minStack.push(-2);

minStack.push(0);

minStack.push(-3);

minStack.getMin(); // return -3

minStack.pop();

minStack.top(); // return 0

minStack.getMin(); // return -2


**Constraints:**
- `-2^31 <= val <= 2^31 - 1`
- Methods `pop`, `top` and `getMin` will always be called on **non-empty** stacks.
- At most `3 * 10^4` calls will be made to `push`, `pop`, `top`, and `getMin`.

## 🧠 Thought Process

### Initial Thoughts
- Need to support regular stack operations plus getMin in O(1) time
- The challenge is maintaining minimum efficiently with each operation
- Multiple approaches with different trade-offs

### Key Insights
1. **Two Stacks Approach**: One for values, one for minimums
2. **Single Stack with Pairs**: Store value and current minimum together
3. **Mathematical Approach**: Store differences (advanced, handles overflow)
4. **The key insight**: Track minimum at each stack level

### Approach Selection
**Chosen Approach:** Two Stacks  
**Why this approach?** 
- O(1) for all operations
- Simple and intuitive
- Easy to implement and understand
- Handles all edge cases well

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) for all operations
- **Space Complexity:** O(n) - Additional stack for minimums
- **Worst Case:** O(2n) = O(n) space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The two stacks approach is most straightforward

Minimum stack only stores new minimums or duplicates of current minimum

All operations maintain O(1) time complexity

The problem tests understanding of stack properties and efficient minimum tracking

🔗 Related Problems
Implement Queue using Stacks

Implement Stack using Queues

Max Stack

Maximum Frequency Stack

Sum of Subarray Minimums


