# 273. Integer to English Words

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Math, String, Recursion  
**Companies:** Facebook, Microsoft, Amazon, Google, Apple, Bloomberg, Uber  

[LeetCode Link](https://leetcode.com/problems/integer-to-english-words/)

Convert a non-negative integer `num` to its English words representation.

**Example 1:**

Input: num = 123
Output: "One Hundred Twenty Three"


**Example 2:**

Input: num = 12345
Output: "Twelve Thousand Three Hundred Forty Five"


**Example 3:**

Input: num = 1234567
Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"


**Constraints:**
- `0 <= num <= 2^31 - 1` (2,147,483,647)

## 🧠 Thought Process

### Problem Understanding
We need to convert integers to their English word representation, handling:
- Numbers 0-19 (special cases)
- Tens (20, 30, ..., 90)
- Hundreds (100, 200, ..., 900)
- Thousands, millions, billions
- Proper spacing between words
- "Zero" for 0

### Key Insights
1. **Recursive Structure**: Break number into chunks of 3 digits (thousands, millions, billions)
2. **Chunk Processing**: Each 3-digit chunk can be processed similarly
3. **Special Cases**: Numbers 0-19 and tens have unique names
4. **Scale Words**: "Thousand", "Million", "Billion"
5. **Edge Cases**: 0, numbers ending with 0, numbers with multiple zeros

### Approach Selection
**Chosen Approach**: Recursive/Iterative with Chunk Processing  
**Why this approach?**
- Clean separation of concerns
- Handles all scales uniformly
- Easy to understand and maintain
- Handles up to billions

**Alternative Approaches**:
- Pure iterative with if-else chains
- Using arrays for digit mapping

## ⚡ Complexity Analysis
- **Time Complexity**: O(log₁₀ n) - Process each digit of the number
- **Space Complexity**: O(log₁₀ n) - Recursion depth or string storage

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Break the problem into:

Convert 1-19

Convert tens (20-90)

Convert hundreds (100-900)

Combine with scales (thousand, million, billion)

Handle "Zero" specially

Be careful with spaces

🔗 Related Problems
Integer to Roman

Roman to Integer

Excel Sheet Column Title

Excel Sheet Column Number

Fraction to Recurring Decimal
