# 166. Fraction to Recurring Decimal

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, Math, String, Simulation  
**Companies:** Google, Amazon, Microsoft, Facebook, Bloomberg, Apple  

[LeetCode Link](https://leetcode.com/problems/fraction-to-recurring-decimal/)

Given two integers representing a `numerator` and a `denominator`, return the fraction in string format.

If the fractional part is repeating, enclose the repeating part in parentheses.

If multiple answers are possible, return any of them.

It is guaranteed that the length of the answer string is less than `10^4` for all the given inputs.

**Example 1:**

Input: numerator = 1, denominator = 2
Output: "0.5"


**Example 2:**

Input: numerator = 2, denominator = 1
Output: "2"


**Example 3:**

Input: numerator = 4, denominator = 333
Output: "0.(012)"


**Constraints:**
- `-2^31 <= numerator, denominator <= 2^31 - 1`
- `denominator != 0`

## 🧠 Thought Process

### Problem Understanding
We need to convert a fraction to its decimal representation, handling:
- Integer part (whole number)
- Non-repeating decimal part
- Repeating decimal part (enclose in parentheses)

### Key Insights
1. **Sign Handling**: Determine sign of result first
2. **Integer Part**: `numerator / denominator` using long division
3. **Fractional Part**: Use long division with remainder tracking
4. **Recurrence Detection**: When a remainder repeats, we've found the cycle
5. **Remainder Map**: Map remainder to index in result string to insert parentheses

### Approach Selection
**Chosen Approach:** Long Division with HashMap  
**Why this approach?**
- O(k) time where k = length of decimal part
- O(k) space for remainder map
- Detects cycles efficiently
- Handles large numbers with 64-bit integers

### Alternative Approaches
- **Mathematical Analysis**: Pre-compute cycle length (more complex)
- **Fraction to Decimal Library**: Not allowed in interviews

## ⚡ Complexity Analysis
- **Time Complexity:** O(k) where k = length of decimal part
- **Space Complexity:** O(k) for storing remainder positions

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use long to avoid integer overflow for large numbers

Track remainder positions to detect cycles

Handle negative numbers by computing absolute values after sign determination

Edge cases: denominator = 1, numerator = 0, negative zero

🔗 Related Problems
Fraction to Recurring Decimal (this problem)
Multiply Strings
Pow(x, n)
Max Points on a Line
Excel Sheet Column Title
