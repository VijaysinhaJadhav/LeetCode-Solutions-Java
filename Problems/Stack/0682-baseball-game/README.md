# 682. Baseball Game

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Stack, Simulation  
**Companies:** Amazon, Google, Microsoft, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/baseball-game/)

You are keeping score for a baseball game with strange rules. The game consists of several rounds, where the scores of past rounds may affect future rounds' scores.

At the beginning of the game, you start with an empty record. You are given a list of strings `ops`, where `ops[i]` is the `i-th` operation you must apply to the record and is one of the following:

1. An integer `x` - Record a new score of `x`.
2. `"+"` - Record a new score that is the sum of the previous two scores.
3. `"D"` - Record a new score that is double the previous score.
4. `"C"` - Invalidate the previous score, removing it from the record.

Return the sum of all the scores on the record after applying all the operations.

**Example 1:**

Input: ops = ["5","2","C","D","+"]

Output: 30

Explanation:

"5" - Add 5 to the record, record is now [5].

"2" - Add 2 to the record, record is now [5, 2].

"C" - Invalidate and remove the previous score, record is now [5].

"D" - Add 2 * 5 = 10 to the record, record is now [5, 10].

"+" - Add 5 + 10 = 15 to the record, record is now [5, 10, 15].

The total sum is 5 + 10 + 15 = 30.


**Example 2:**

Input: ops = ["5","-2","4","C","D","9","+","+"]

Output: 27

Explanation:

"5" - Add 5 to the record, record is now [5].

"-2" - Add -2 to the record, record is now [5, -2].

"4" - Add 4 to the record, record is now [5, -2, 4].

"C" - Invalidate and remove the previous score, record is now [5, -2].

"D" - Add 2 * -2 = -4 to the record, record is now [5, -2, -4].

"9" - Add 9 to the record, record is now [5, -2, -4, 9].

"+" - Add -4 + 9 = 5 to the record, record is now [5, -2, -4, 9, 5].

"+" - Add 9 + 5 = 14 to the record, record is now [5, -2, -4, 9, 5, 14].

The total sum is 5 + (-2) + (-4) + 9 + 5 + 14 = 27.


**Example 3:**

Input: ops = ["1"]

Output: 1


**Constraints:**
- `1 <= ops.length <= 1000`
- `ops[i]` is `"C"`, `"D"`, `"+"`, or a string representing an integer in the range `[-3 * 10^4, 3 * 10^4]`.
- For operation `"+"`, there will always be at least two previous scores on the record.
- For operations `"C"` and `"D"`, there will always be at least one previous score on the record.

## 🧠 Thought Process

### Initial Thoughts
- Need to simulate the baseball game operations
- Stack is the natural data structure for tracking scores
- Handle different operations with appropriate logic
- Multiple approaches with different implementations

### Key Insights
1. **Stack Operations**: Perfect for tracking scores with LIFO behavior
2. **Simulation**: Process each operation and update the record accordingly
3. **Edge Cases**: Handle negative numbers, single operations, etc.
4. **The key insight**: Use stack to easily access and modify recent scores

### Approach Selection
**Chosen Approach:** Stack Simulation  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Intuitive and matches the problem's natural structure
- Easy to implement and understand

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Process each operation once
- **Space Complexity:** O(n) - Stack storage for scores

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Stack is the ideal data structure for this problem

Handle each operation type with specific logic

The problem is straightforward but tests simulation skills

All operations guarantee valid states (no underflow)

🔗 Related Problems
Evaluate Reverse Polish Notation

Min Stack

Simplify Path

Backspace String Compare

Remove All Adjacent Duplicates In String
