# 151. Reverse Words in a String

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Two Pointers, String Manipulation  
**Companies:** Microsoft, Amazon, Facebook, Google, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/reverse-words-in-a-string/)

Given an input string `s`, reverse the order of the **words**.

A **word** is defined as a sequence of non-space characters. The words in `s` will be separated by at least one space.

Return a string of the words in reverse order concatenated by a single space.

**Note** that `s` may contain leading or trailing spaces or multiple spaces between two words. The returned string should only have a single space separating the words. Do not include any extra spaces.

**Example 1:**

Input: s = "the sky is blue"
Output: "blue is sky the"


**Example 2:**

Input: s = " hello world "
Output: "world hello"
Explanation: Your reversed string should not contain leading or trailing spaces.


**Example 3:**

Input: s = "a good example"
Output: "example good a"
Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.


**Constraints:**
- `1 <= s.length <= 10^4`
- `s` contains English letters (upper-case and lower-case), digits, and spaces `' '`.
- There is **at least one** word in `s`.

**Follow-up:** If the string data type is mutable in your language, can you solve it **in-place** with `O(1)` extra space?

## 🧠 Thought Process

### Problem Understanding
We need to reverse the order of words in a string while handling:
- Leading and trailing spaces
- Multiple spaces between words
- Single-space separation in output

### Key Insights
1. **Split-based Approach**: Split string by spaces, filter empty strings, reverse, join
2. **Two-pointer Approach**: Reverse entire string, then reverse each word
3. **In-place Solution**: For languages with mutable strings (Java strings are immutable)
4. **Space Complexity**: We need to consider whether to use O(n) extra space or O(1)

### Approach Selection
**Chosen Approach:** Two-pointer with String Builder  
**Why this approach?**
- Simple and intuitive
- Handles edge cases gracefully
- O(n) time and O(n) space complexity

**Alternative Approaches:**
- **Split & Reverse**: More concise but uses extra space
- **In-place (for mutable strings)**: O(1) space, O(n) time (requires character array)
- **Stack-based**: Push words to stack, pop for reverse order

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n is the length of the string
- **Space Complexity:** O(n) for the result or O(1) for in-place solution

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Handle multiple spaces by trimming and splitting with regex

For in-place solution, need mutable character array (Java strings are immutable)

Edge cases: empty string, single word, multiple spaces

Reverse the entire string first, then reverse each word

🔗 Related Problems
Reverse Words in a String II (in-place, mutable)
Reverse Words in a String III
Length of Last Word
Reverse String
Reverse String II
