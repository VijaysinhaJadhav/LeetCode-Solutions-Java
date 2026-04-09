# 8. String to Integer (atoi)

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Math  
**Companies:** Amazon, Microsoft, Google, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/string-to-integer-atoi/)

Implement the `myAtoi(string s)` function, which converts a string to a 32-bit signed integer (similar to C/C++'s `atoi` function).

The algorithm for `myAtoi(string s)` is as follows:

1. **Read in** and ignore any leading whitespace.
2. Check if the next character (if not already at the end of the string) is `'-'` or `'+'`. Read this character in if it is either. This determines if the final result is negative or positive respectively. Assume the result is positive if neither is present.
3. **Read in** the next characters until the next non-digit character or the end of the input is reached. The rest of the string is ignored.
4. Convert these digits into an integer (i.e., `"123" -> 123`, `"0032" -> 32`). If no digits were read, then the integer is `0`. Change the sign as necessary (from step 2).
5. If the integer is out of the 32-bit signed integer range `[-2^31, 2^31 - 1]`, then clamp the integer so that it remains in the range. Specifically, integers less than `-2^31` should be clamped to `-2^31`, and integers greater than `2^31 - 1` should be clamped to `2^31 - 1`.
6. Return the integer as the final result.

**Note:**
- Only the space character `' '` is considered a whitespace character.
- **Do not ignore** any characters other than the leading whitespace or the rest of the string after the digits.

**Example 1:**

Input: s = "42"
Output: 42


**Example 2:**

Input: s = " -42"
Output: -42
Explanation: The first non-whitespace character is '-', which is the minus sign. Then take as many numerical digits as possible, which gives 42.


**Example 3:**

Input: s = "4193 with words"
Output: 4193
Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.


**Example 4:**

Input: s = "words and 987"
Output: 0
Explanation: The first non-whitespace character is 'w', which is not a numerical digit or a +/- sign. Therefore, no valid conversion could be performed.


**Example 5:**

Input: s = "-91283472332"
Output: -2147483648
Explanation: The number "-91283472332" is less than the lower bound of the 32-bit signed integer range. Therefore, the result is clamped to -2^31.


**Constraints:**
- `0 <= s.length <= 200`
- `s` consists of English letters (lower-case and upper-case), digits (`0-9`), `' '`, `'+'`, `'-'`, and `'.'`.

## 🧠 Thought Process

### Problem Understanding
Implement a string-to-integer conversion similar to C's `atoi` with specific rules:
- Skip leading whitespace
- Handle optional sign
- Read digits until non-digit
- Handle overflow by clamping to 32-bit integer range

### Key Insights
1. **Whitespace**: Only space `' '` is considered whitespace (not tabs or newlines)
2. **Sign**: Only one sign character allowed; subsequent signs invalidate conversion
3. **Digits**: Only `0-9` are valid; stop at first non-digit
4. **Overflow**: Check before multiplication to avoid overflow
5. **No digits**: Return 0 if no digits found after sign

### Approach Selection
**Chosen Approach:** Step-by-Step State Machine  
**Why this approach?**
- O(n) time complexity
- O(1) space complexity
- Clear separation of parsing stages
- Handles all edge cases

**Alternative Approaches:**
- **Regular Expression**: `^\s*[+-]?\d+` but not as educational
- **Built-in parsing**: Not allowed for implementation problem

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of string
- **Space Complexity:** O(1) extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use long to detect overflow before clamping

Check for overflow before multiplying by 10

Handle empty string and strings with no digits

Sign can only appear before digits, not after

🔗 Related Problems
Reverse Integer
Palindrome Number
Valid Number
Integer to Roman
Roman to Integer
