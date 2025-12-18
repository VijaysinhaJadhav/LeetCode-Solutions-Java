# 371. Sum of Two Integers

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Math, Bit Manipulation  
**Companies:** Google, Amazon, Microsoft, Apple, Facebook, Bloomberg

[LeetCode Link](https://leetcode.com/problems/sum-of-two-integers/)

Given two integers `a` and `b`, return *the sum of the two integers without using the operators `+` and `-`*.

**Example 1:**

Input: a = 1, b = 2

Output: 3


**Example 2:**

Input: a = 2, b = 3

Output: 5


**Constraints:**
- `-1000 <= a, b <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Cannot use + or - operators
- Must use bit manipulation
- This is essentially implementing addition at the hardware level
- Need to handle both positive and negative numbers

### Key Insights
1. **Bitwise Operations**:
   - XOR (^) performs addition without carry
   - AND (&) finds carry bits
   - Left shift (<<) moves carry to correct position
2. **Addition Process**:
   - Add two numbers: `sum = a ^ b` (addition without carry)
   - Calculate carry: `carry = (a & b) << 1`
   - Repeat until no carry
3. **Handling Negative Numbers**:
   - Two's complement representation works naturally with bitwise operations
   - Python/Java handle negative numbers differently due to infinite bits

### Approach Selection
**Chosen Approach:** Bit Manipulation with Loop  
**Why this approach?** 
- O(1) time complexity (at most 32/64 iterations)
- O(1) space complexity
- Mimics hardware adder logic
- Handles both positive and negative numbers

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) - At most 32 iterations for 32-bit integers
- **Space Complexity:** O(1) - Only a few variables needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
XOR gives sum without carry

AND gives carry bits

Left shift moves carry to correct position

Continue until no carry

Works for negative numbers in two's complement

🔗 Related Problems
Divide Two Integers

Add Binary

Reverse Bits

Number of 1 Bits

Bitwise AND of Numbers Range

Single Number
