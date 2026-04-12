# 326. Power of Three

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Math, Recursion  
**Companies:** Amazon, Google, Microsoft, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/power-of-three/)

Given an integer `n`, return `true` if it is a power of three. Otherwise, return `false`.

An integer `n` is a power of three if there exists an integer `x` such that `n == 3^x`.

**Example 1:**

Input: n = 27
Output: true
Explanation: 27 = 3^3


**Example 2:**

Input: n = 0
Output: false
Explanation: There is no x where 3^x = 0.


**Example 3:**

Input: n = -1
Output: false
Explanation: Negative numbers cannot be powers of three.


**Example 4:**

Input: n = 45
Output: false


**Constraints:**
- `-2^31 <= n <= 2^31 - 1`

**Follow-up:** Could you solve it without using loops/recursion?

## 🧠 Thought Process

### Problem Understanding
Determine if a given integer is a power of three (3^0 = 1, 3^1 = 3, 3^2 = 9, 3^3 = 27, ...).

### Key Insights
1. **Loop Approach**: Continuously divide by 3 until remainder != 0
2. **Recursion**: Base case n == 1 → true, n % 3 != 0 → false
3. **Logarithm**: `log10(n) / log10(3)` is integer
4. **Integer Limitation**: Since 3^19 < 2^31 < 3^20, max power is 3^19 = 1,162,261,467

### Approach Selection
**Chosen Approach:** Loop (Iterative Division)  
**Why this approach?**
- O(log n) time complexity
- O(1) space complexity
- Simple and intuitive

**Alternative Approaches:**
- **Recursion**: Same complexity, elegant but uses stack
- **Logarithm**: O(1) but floating-point precision issues
- **Precomputed Set**: O(1) with max 20 numbers

## ⚡ Complexity Analysis
- **Time Complexity:** O(log₃ n) for loop/recursion, O(1) for logarithm/precomputed
- **Space Complexity:** O(1) for all approaches (O(log n) for recursion stack)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Handle negative numbers and zero first (return false)

Use n % 3 to check divisibility

The largest power of three within 32-bit integer range is 3^19 = 1,162,261,467

Can also use integer division: n > 0 && 1162261467 % n == 0

🔗 Related Problems
Power of Two
Power of Three (this problem)
Power of Four
Power of Four (similar pattern)
Pow(x, n)
