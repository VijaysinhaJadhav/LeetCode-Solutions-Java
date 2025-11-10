# 150. Evaluate Reverse Polish Notation

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Math, Stack  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/evaluate-reverse-polish-notation/)

You are given an array of strings `tokens` that represents an arithmetic expression in Reverse Polish Notation (RPN).

Evaluate the expression and return an integer that represents the value of the expression.

**Note that:**
- The valid operators are `'+'`, `'-'`, `'*'`, and `'/'`.
- Each operand may be an integer or another expression.
- The division between two integers always truncates toward zero.
- There will not be any division by zero.
- The input represents a valid arithmetic expression in RPN.
- The answer and all the intermediate calculations can be represented in a 32-bit integer.

**Example 1:**

Input: tokens = ["2","1","+","3","*"]

Output: 9

Explanation: ((2 + 1) * 3) = 9


**Example 2:**

Input: tokens = ["4","13","5","/","+"]

Output: 6

Explanation: (4 + (13 / 5)) = 6


**Example 3:**

Input: tokens = ["10","6","9","3","+","-11","","/","","17","+","5","+"]

Output: 22

Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5

= ((10 * (6 / (12 * -11))) + 17) + 5

= ((10 * (6 / -132)) + 17) + 5

= ((10 * 0) + 17) + 5

= (0 + 17) + 5

= 17 + 5

= 22


**Constraints:**
- `1 <= tokens.length <= 10^4`
- `tokens[i]` is either an operator: `"+"`, `"-"`, `"*"`, `"/"`, or an integer in the range `[-200, 200]`.

## 🧠 Thought Process

### Initial Thoughts
- Reverse Polish Notation (RPN) eliminates the need for parentheses
- Stack is the natural data structure for evaluation
- Process tokens left to right, push operands, apply operators to top two operands

### Key Insights
1. **Stack Evaluation**: Push numbers, pop and compute when encountering operators
2. **Operator Precedence**: RPN handles precedence automatically
3. **Integer Division**: Truncate toward zero (not floor division)
4. **The key insight**: Stack maintains intermediate results for binary operations

### Approach Selection
**Chosen Approach:** Stack Evaluation  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Intuitive and matches RPN evaluation rules
- Handles all operators and edge cases

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Process each token once
- **Space Complexity:** O(n) - Stack storage for operands

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Stack is perfect for RPN evaluation

Handle integer division with truncation toward zero

Process tokens sequentially without worrying about operator precedence

The algorithm naturally handles nested expressions

🔗 Related Problems
Basic Calculator

Basic Calculator II

Basic Calculator III

Flatten Nested List Iterator

Exclusive Time of Functions
