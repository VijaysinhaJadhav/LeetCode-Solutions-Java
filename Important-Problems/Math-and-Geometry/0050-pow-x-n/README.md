# 50. Pow(x, n)

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Math, Recursion, Binary Exponentiation  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/powx-n/)

Implement [pow(x, n)](http://www.cplusplus.com/reference/valarray/pow/), which calculates `x` raised to the power `n` (i.e., `x^n`).

**Example 1:**

Input: x = 2.00000, n = 10
Output: 1024.00000


**Example 2:**

Input: x = 2.10000, n = 3
Output: 9.26100


**Example 3:**

Input: x = 2.00000, n = -2
Output: 0.25000
Explanation: 2^-2 = 1/2^2 = 1/4 = 0.25


**Constraints:**
- `-100.0 < x < 100.0`
- `-2^31 <= n <= 2^31 - 1`
- `n` is an integer.
- `x^n` is within `[-10^4, 10^4]`.

## 🧠 Thought Process

### Problem Understanding
Implement power function efficiently. The naive approach of multiplying `n` times is O(n), which is too slow for large `n` (up to 2^31).

### Key Insights
1. **Binary Exponentiation**: Use the property that `x^n = (x^(n/2))^2` for even `n`
2. **Recursive Approach**: Halve the exponent at each step → O(log n) time
3. **Negative Exponent**: `x^(-n) = 1 / x^n`
4. **Integer Overflow**: Handle `n = Integer.MIN_VALUE` carefully (-2^31)

### Approach Selection
**Chosen Approach:** Binary Exponentiation (Fast Power)  
**Why this approach?**
- O(log n) time complexity
- O(log n) or O(1) space (iterative)
- Handles all edge cases

**Alternative Approaches:**
- **Brute Force**: O(n) - too slow
- **Built-in Math.pow**: Not allowed in implementation problem

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) where n = exponent
- **Space Complexity:** O(log n) for recursion, O(1) for iteration

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation


📝 Notes
Use long for exponent to handle Integer.MIN_VALUE

For even exponents: x^n = (x^(n/2))^2

For odd exponents: x^n = x * (x^(n-1))

Handle negative exponents with reciprocal

🔗 Related Problems
Sqrt(x)
Super Pow
Divide Two Integers
Valid Perfect Square
Sum of Square Numbers
