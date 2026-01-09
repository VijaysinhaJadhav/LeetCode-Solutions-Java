# 28. Find the Index of the First Occurrence in a String

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Two Pointers, String, String Matching  
**Companies:** Microsoft, Amazon, Apple, Google, Meta, Adobe, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/)

Given two strings `needle` and `haystack`, return the index of the first occurrence of `needle` in `haystack`, or `-1` if `needle` is not part of `haystack`.

**Example 1:**

Input: haystack = "sadbutsad", needle = "sad"
Output: 0
Explanation: "sad" occurs at index 0 and 6.
The first occurrence is at index 0, so we return 0.


**Example 2:**

Input: haystack = "leetcode", needle = "leeto"
Output: -1
Explanation: "leeto" did not occur in "leetcode", so we return -1.


**Constraints:**
- `1 <= haystack.length, needle.length <= 10^4`
- `haystack` and `needle` consist of only lowercase English characters.

## 🧠 Thought Process

### Initial Thoughts
- Classic string searching problem
- Need to find first occurrence of substring in string
- Multiple approaches with different trade-offs

### Key Insights
1. **Brute Force (Naive):**
   - Check all possible starting positions
   - O(n*m) time complexity
   - Simple but inefficient for large strings
2. **Built-in Methods:**
   - Most languages have `indexOf()` or `find()`
   - Easy one-liner but not interview-appropriate
3. **KMP Algorithm:**
   - Knuth-Morris-Pratt algorithm
   - O(n+m) time complexity
   - Uses prefix function to avoid redundant comparisons
4. **Rabin-Karp Algorithm:**
   - Rolling hash technique
   - O(n+m) average time, O(n*m) worst case
   - Useful for pattern matching with hash

### Approach Selection
**Primary Approach:** KMP Algorithm (for interviews)  
**Why this approach?** 
- O(n+m) optimal time complexity
- Demonstrates algorithmic knowledge
- Handles all edge cases
- Standard interview question for string matching

**Practical Approach:** Built-in `indexOf()` (for real code)  
**Why this approach?**
- One line of code
- Highly optimized in most languages
- Production-ready

## ⚡ Complexity Analysis

### KMP Algorithm
- **Time Complexity:** O(n+m) where n = haystack length, m = needle length
- **Space Complexity:** O(m) for prefix/lps array

### Built-in Method
- **Time Complexity:** Typically O(n+m) (implementation dependent)
- **Space Complexity:** O(1)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
KMP is the standard interview solution

Understand the prefix function/lps array

Edge cases: empty needle, needle longer than haystack

Practice deriving the prefix function

🔗 Related Problems
459. Repeated Substring Pattern

214. Shortest Palindrome

686. Repeated String Match

1392. Longest Happy Prefix

796. Rotate String

28. Implement strStr() (same problem)
