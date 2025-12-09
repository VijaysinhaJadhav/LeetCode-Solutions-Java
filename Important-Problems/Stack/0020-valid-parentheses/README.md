# 20. Valid Parentheses

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** String, Stack  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle, Adobe

[LeetCode Link](https://leetcode.com/problems/valid-parentheses/)

Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.

An input string is valid if:
1. Open brackets must be closed by the same type of brackets.
2. Open brackets must be closed in the correct order.
3. Every close bracket has a corresponding open bracket of the same type.

**Example 1:**

Input: s = "()"

Output: true


**Example 2:**

Input: s = "()[]{}"

Output: true


**Example 3:**

Input: s = "(]"

Output: false


**Example 4:**

Input: s = "([)]"

Output: false


**Example 5:**

Input: s = "{[]}"

Output: true


**Constraints:**
- `1 <= s.length <= 10^4`
- `s` consists of parentheses only `'()[]{}'`.

## 🧠 Thought Process

### Initial Thoughts
- Need to check if parentheses are properly matched and nested
- Stack is the natural data structure for tracking opening brackets
- Multiple approaches with different implementations

### Key Insights
1. **Stack Operations**: Push opening brackets, pop and check when encountering closing brackets
2. **Early Termination**: If stack is empty when encountering closing bracket, it's invalid
3. **Final Check**: Stack must be empty at the end for valid string
4. **The key insight**: Use stack to ensure proper nesting and matching

### Approach Selection
**Chosen Approach:** Stack with HashMap  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Clean and readable
- Easy to extend for additional bracket types

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Process each character once
- **Space Complexity:** O(n) - Stack storage in worst case

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Stack is the ideal data structure for matching parentheses

Use HashMap for O(1) lookup of matching brackets

Handle edge cases: empty string, single character, unmatched brackets

The problem is classic but tests fundamental stack understanding

🔗 Related Problems
Generate Parentheses

Longest Valid Parentheses

Valid Parenthesis String

Number of Atoms

Remove All Adjacent Duplicates In String
