# 12. Integer to Roman

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, Math, String  
**Companies:** Amazon, Google, Microsoft, Meta, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/integer-to-roman/)

Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

Symbol Value

I 1

V 5

X 10

L 50

C 100

D 500

M 1000


For example, `2` is written as `II` in Roman numeral, just two one's added together. `12` is written as `XII`, which is simply `X + II`. The number `27` is written as `XXVII`, which is `XX + V + II`.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not `IIII`. Instead, the number four is written as `IV`. Because the one is before the five we subtract it making four. The same principle applies to nine, which is written as `IX`. There are six instances where subtraction is used:

- `I` can be placed before `V` (5) and `X` (10) to make 4 and 9. 
- `X` can be placed before `L` (50) and `C` (100) to make 40 and 90. 
- `C` can be placed before `D` (500) and `M` (1000) to make 400 and 900.

Given an integer, convert it to a roman numeral.

**Example 1:**

Input: num = 3

Output: "III"

Explanation: 3 = III.


**Example 2:**

Input: num = 58

Output: "LVIII"

Explanation: L = 50, V = 5, III = 3.


**Example 3:**

Input: num = 1994

Output: "MCMXCIV"

Explanation: M = 1000, CM = 900, XC = 90, IV = 4.


**Constraints:**
- `1 <= num <= 3999`

## 🧠 Thought Process

### Initial Thoughts
- Roman numerals have specific symbols for specific values
- Need to handle subtraction cases (4, 9, 40, 90, 400, 900)
- Largest possible number is 3999 (MMMCMXCIX)

### Key Insights
1. **Greedy Approach**: Always use the largest possible Roman numeral value
2. **Subtraction Cases**: Special combinations like IV (4), IX (9), XL (40), etc.
3. **Predefined Mapping**: Can create arrays of values and corresponding symbols
4. **Process from Largest to Smallest**: Start with largest value (1000) and work down

### Approach Selection
**Chosen Approach:** Greedy with Predefined Arrays  
**Why this approach?** 
- O(1) time complexity (bounded by number of symbols)
- O(1) space complexity
- Simple and intuitive
- Handles all cases including subtraction

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) - Maximum 13 iterations (number of symbol-value pairs)
- **Space Complexity:** O(1) - Fixed arrays of size 13

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Roman numerals use additive and subtractive notation

Maximum number is 3999 in Roman numerals (limitation of standard form)

Can also solve using division/modulo approach

Important to handle subtraction cases properly

🔗 Related Problems
Roman to Integer

Integer to English Words

Fraction to Recurring Decimal

Excel Sheet Column Title

Excel Sheet Column Number
