# 895. Maximum Frequency Stack

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Hash Table, Stack, Design, Ordered Set  
**Companies:** Amazon, Google, Microsoft, Uber, Bloomberg, Apple, Adobe

[LeetCode Link](https://leetcode.com/problems/maximum-frequency-stack/)

Design a stack-like data structure that supports push and pop operations and can efficiently return the most frequent element. If there is a tie for the most frequent element, return the element that was pushed most recently.

Implement the `FreqStack` class:

- `FreqStack()` initializes the empty frequency stack.
- `void push(int val)` pushes an integer `val` onto the top of the stack.
- `int pop()` removes and returns the most frequent element in the stack. If there is a tie, return the element closest to the top.

**Example 1:**

Input

["FreqStack", "push", "push", "push", "push", "push", "push", "pop", "pop", "pop", "pop"]

[[], [5], [7], [5], [7], [4], [5], [], [], [], []]

Output

[null, null, null, null, null, null, null, 5, 7, 5, 4]

Explanation

FreqStack freqStack = new FreqStack();

freqStack.push(5); // The stack is [5]

freqStack.push(7); // The stack is [5,7]

freqStack.push(5); // The stack is [5,7,5]

freqStack.push(7); // The stack is [5,7,5,7]

freqStack.push(4); // The stack is [5,7,5,7,4]

freqStack.push(5); // The stack is [5,7,5,7,4,5]

freqStack.pop(); // return 5, as 5 is the most frequent. The stack becomes [5,7,5,7,4].

freqStack.pop(); // return 7, as 5 and 7 is the most frequent, but 7 is closest to the top. The stack becomes [5,7,5,4].

freqStack.pop(); // return 5, The stack becomes [5,7,4].

freqStack.pop(); // return 4, The stack becomes [5,7].


**Constraints:**
- `0 <= val <= 10^9`
- At most `2 * 10^4` calls will be made to `push` and `pop`.
- It is guaranteed that there will be at least one element in the stack before calling `pop`.

## 🧠 Thought Process

### Initial Thoughts
- Need to track frequency of each element
- Need to maintain order of elements with same frequency (LIFO behavior)
- When popping, return most frequent element, breaking ties by most recent push

### Key Insights
1. **Two HashMap Approach**: 
   - One map for frequency counts
   - One map for stacks of elements at each frequency level
2. **Stack of Stacks**: Maintain stacks for each frequency level
3. **Max Frequency Tracking**: Keep track of current maximum frequency
4. **LIFO within Frequency**: Elements with same frequency behave like a stack

### Approach Selection
**Chosen Approach:** Two HashMaps with Frequency Stacks  
**Why this approach?** 
- O(1) time for both push and pop operations
- Efficient frequency tracking
- Natural handling of tie-breaks (LIFO)
- Scalable and easy to understand

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) for both push and pop operations
- **Space Complexity:** O(n) where n is the number of elements pushed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is maintaining stacks for each frequency level

When frequency increases, element moves to higher frequency stack

Pop always removes from the highest frequency stack

Ties are automatically handled by stack LIFO behavior

🔗 Related Problems
LFU Cache

LRU Cache

Design Twitter

Insert Delete GetRandom O(1)
