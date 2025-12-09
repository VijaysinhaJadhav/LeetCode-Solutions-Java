# 69. Sqrt(x)

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Math, Binary Search  
**Companies:** Amazon, Google, Microsoft, Apple, Bloomberg, Facebook, Adobe, Uber

[LeetCode Link](https://leetcode.com/problems/sqrtx/)

Given a non-negative integer `x`, return the square root of `x` rounded down to the nearest integer. The returned integer should be non-negative as well.

You must not use any built-in exponent function or operator like `pow(x, 0.5)` or `x ** 0.5`.

**Example 1:**

Input: x = 4

Output: 2

Explanation: The square root of 4 is 2, so we return 2.


**Example 2:**

Input: x = 8

Output: 2

Explanation: The square root of 8 is 2.82842..., and since we round it down to the nearest integer, 2 is returned.


**Constraints:**
- `0 <= x <= 2^31 - 1`

## 🧠 Thought Process

### Initial Thoughts
- Need to find the integer square root (floor value)
- Cannot use built-in math functions
- Large constraint (up to 2^31-1) requires efficient algorithm
- Binary search is ideal for this problem

### Key Insights
1. **Binary Search Range**: Search space is from 0 to x (or optimized to x/2 + 1)
2. **Integer Square Root**: We're finding the largest integer where num² ≤ x
3. **Avoid Overflow**: Use long for mid calculations or clever comparisons
4. **Edge Cases**: x = 0, x = 1, and large x values

### Approach Selection
**Chosen Approach:** Binary Search  
**Why this approach?** 
- O(log x) time complexity handles large constraints efficiently
- Simple and intuitive implementation
- Naturally fits the problem of searching in a sorted range
- More efficient than linear search O(√x)

## ⚡ Complexity Analysis
- **Time Complexity:** O(log x) - Binary search halves search space each iteration
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to find the largest integer ans such that ans² ≤ x

Handle integer overflow carefully when calculating mid * mid

The problem demonstrates practical application of binary search beyond array searching

🔗 Related Problems
Pow(x, n)

Valid Perfect Square

Arranging Coins

Find Smallest Letter Greater Than Target
