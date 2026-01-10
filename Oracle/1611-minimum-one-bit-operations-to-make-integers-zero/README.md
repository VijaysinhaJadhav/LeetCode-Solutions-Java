# 1611. Minimum One Bit Operations to Make Integers Zero

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Dynamic Programming, Bit Manipulation, Math, Recursion  
**Companies:** Google, Meta, Amazon, Microsoft, Bloomberg

[LeetCode Link](https://leetcode.com/problems/minimum-one-bit-operations-to-make-integers-zero/)

Given an integer `n`, you must transform it into `0` using the following operations **any number of times**:

1. Change the rightmost (`0th`) bit in the binary representation of `n`.
2. Change the `i-th` bit in the binary representation of `n` if the `(i-1)-th` bit is set to `1` and all bits from `(i-2)` down to `0` are set to `0`.

Return the **minimum number of operations** to transform `n` into `0`.

**Example 1:**

Input: n = 3
Output: 2
Explanation: The binary representation of 3 is "11".
"11" -> "01" with operation #1 on bit 0.
"01" -> "00" with operation #1 on bit 0.


**Example 2:**

Input: n = 6
Output: 4
Explanation: The binary representation of 6 is "110".
"110" -> "010" with operation #2 on bit 2.
"010" -> "011" with operation #1 on bit 0.
"011" -> "001" with operation #1 on bit 1.
"001" -> "000" with operation #1 on bit 0.


**Constraints:**
- `0 <= n <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- This is known as the "Gray code" or "Tower of Hanoi" bit problem
- Operations are exactly like moving disks in Tower of Hanoi
- Need to find minimum operations to clear all bits (make number 0)
- Observations suggest recursive pattern

### Key Insights
1. **Pattern Recognition:**
   - The operations are exactly like moving disks in Tower of Hanoi
   - Each bit position is like a disk, with size = position
   - To clear bit i, we need to clear bits 0 through i-1 first
2. **Mathematical Formula:**
   - Let f(n) = minimum operations to make n zero
   - Base case: f(0) = 0, f(1) = 1
   - For n with highest bit at position k: f(n) = 2^k + f(2^{k-1} ⊕ n)
   - Alternatively: f(n) = n ⊕ (n >> 1) ⊕ (n >> 2) ⊕ ... until 0
3. **Recursive Relationship:**
   - To clear highest bit at position k:
     1. Clear bits 0..k-1 to 100..0 pattern (2^{k-1} operations)
     2. Flip bit k (1 operation)
     3. Set bits 0..k-1 from 100..0 to original pattern but with bit k cleared
   - This gives recurrence: f(1XXXX) = 2^k + f(01XXX ⊕ 1XXXX)
4. **Gray Code Connection:**
   - Operations correspond to generating Gray code sequence
   - Minimum operations = Gray code distance
   - f(n) = n ⊕ (n >> 1) ⊕ (n >> 2) ⊕ ... which equals Gray code of n

### Approach Selection
**Chosen Approach:** Mathematical Formula (Gray Code)  
**Why this approach?** 
- O(log n) time complexity
- O(1) space complexity
- Based on proven mathematical properties
- Simple implementation

**Alternative:** Recursive/Iterative Bit Manipulation  
**Why this approach?**
- More intuitive for understanding the Tower of Hanoi analogy
- Also O(log n) time
- Demonstrates the recursive structure clearly

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) - Process each bit of n
- **Space Complexity:** O(1) - Only a few variables

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
This is exactly the Tower of Hanoi problem in disguise

Operation 1: move smallest disk

Operation 2: move larger disk when smaller is in right position

Result equals Gray code transformation

Can be solved with bitwise XOR operations

🔗 Related Problems
89. Gray Code

191. Number of 1 Bits

338. Counting Bits

461. Hamming Distance

693. Binary Number with Alternating Bits
