# 394. Decode String

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Stack, Recursion  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/decode-string/)

Given an encoded string, return its decoded string.

The encoding rule is: `k[encoded_string]`, where the `encoded_string` inside the square brackets is being repeated exactly `k` times. Note that `k` is guaranteed to be a positive integer.

You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, `k`. For example, there will not be input like `3a` or `2[4]`.

**Example 1:**

Input: s = "3[a]2[bc]"

Output: "aaabcbc"


**Example 2:**

Input: s = "3[a2[c]]"

Output: "accaccacc"


**Example 3:**

Input: s = "2[abc]3[cd]ef"

Output: "abcabccdcdcdef"


**Constraints:**
- `1 <= s.length <= 30`
- `s` consists of lowercase English letters, digits, and square brackets `'[]'`.
- `s` is guaranteed to be a valid input.
- All the integers in `s` are in the range `[1, 300]`.

## 🧠 Thought Process

### Initial Thoughts
- Need to handle nested encoded strings with repetition
- Multiple approaches with different trade-offs
- The challenge is handling nested brackets and numbers correctly

### Key Insights
1. **Stack Approach**: Use stacks to track numbers and strings for nested decoding
2. **Recursive Approach**: Use recursion to handle nested structures naturally
3. **Two Stacks**: One for counts, one for strings
4. **The key insight**: Process characters and use stacks to handle nesting

### Approach Selection
**Chosen Approach:** Two Stacks  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Intuitive and handles nesting well
- Efficient for the constraint sizes

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Process each character once
- **Space Complexity:** O(n) - Stack storage for numbers and strings

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use two stacks: one for repetition counts, one for intermediate strings

Build current string while processing

When encountering [, push current string and count to stacks

When encountering ], pop and build decoded string

Handle digits by parsing multi-digit numbers

🔗 Related Problems
Flatten Nested List Iterator

Mini Parser

Ternary Expression Parser

Number of Atoms

Brace Expansion
