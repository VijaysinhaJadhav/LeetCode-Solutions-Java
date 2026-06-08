# 415. Add Strings

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Math, String, Simulation  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/add-strings/)

Given two non-negative integers `num1` and `num2` represented as strings, return the sum of `num1` and `num2` as a string.

You must solve the problem without using any built-in library for handling large integers (such as `BigInteger`). You must also not convert the inputs to integers directly.

**Example 1:**

Input: num1 = "11", num2 = "123"
Output: "134"


**Example 2:**

Input: num1 = "456", num2 = "77"
Output: "533"


**Example 3:**

Input: num1 = "0", num2 = "0"
Output: "0"

**Constraints:**
- `1 <= num1.length, num2.length <= 10^4`
- `num1` and `num2` consist of only digits.
- `num1` and `num2` don't have any leading zeros except for the single digit '0'.

## 🧠 Thought Process

### Problem Understanding
We need to add two large numbers given as strings without converting them to numeric types. The length can be up to 10⁴ digits, so we must simulate manual addition digit by digit.

### Key Insights
1. **Digit-by-Digit Addition**: Start from the least significant digit (rightmost), add corresponding digits plus carry.
2. **Carry Propagation**: If sum >= 10, set carry = 1 and subtract 10 from sum.
3. **Unequal Lengths**: Continue with the longer number when one string runs out.
4. **Final Carry**: If carry remains after processing all digits, prepend '1' to the result.

### Approach Selection
**Chosen Approach:** Two Pointers from the end, StringBuilder for efficiency
**Why this approach?**
- O(max(n, m)) time complexity
- O(max(n, m)) space for result
- No built‑in numeric conversion

**Alternative Approaches:**
- Convert to char array and loop
- Use recursion (not recommended due to stack depth)

## ⚡ Complexity Analysis
- **Time Complexity:** O(max(len1, len2)) — each digit is processed once.
- **Space Complexity:** O(max(len1, len2)) — for the result string.

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use StringBuilder to build the result in reverse order, then reverse it.

Remember to convert character digits to integers (c - '0').

Handle the final carry after the loop.

Edge cases: one number is zero, numbers of different lengths, large carry (e.g., "999" + "1").

🔗 Related Problems
Add Two Numbers (linked list version)
Plus One
Add Binary
Add to Array-Form of Integer
Multiply Strings
