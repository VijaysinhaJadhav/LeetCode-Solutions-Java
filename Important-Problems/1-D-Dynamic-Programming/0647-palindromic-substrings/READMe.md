# 647. Palindromic Substrings

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Dynamic Programming, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/palindromic-substrings/)

Given a string `s`, return *the number of **palindromic substrings** in it*.

A string is a **palindrome** when it reads the same backward as forward.

A **substring** is a contiguous sequence of characters within the string.

**Example 1:**

Input: s = "abc"
Output: 3
Explanation: Three palindromic strings: "a", "b", "c".


**Example 2:**

Input: s = "aaa"
Output: 6
Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".


**Constraints:**
- `1 <= s.length <= 1000`
- `s` consists of lowercase English letters.

## 🧠 Thought Process

### Problem Understanding
Count all substrings that are palindromes. A substring of length 1 is always a palindrome.

### Key Insights
1. **Expand Around Center**: Each palindrome has a center (1 or 2 characters)
2. **Dynamic Programming**: `dp[i][j]` = true if `s[i..j]` is palindrome
3. **Manacher's Algorithm**: O(n) time, but complex
4. **Two Pointers**: Expand from each center

### Approach Selection
**Chosen Approach:** Expand Around Center  
**Why this approach?**
- O(n²) time complexity
- O(1) space complexity
- Simple and intuitive

**Alternative Approaches:**
- **Dynamic Programming**: O(n²) time, O(n²) space
- **Manacher's Algorithm**: O(n) time, O(n) space (complex)

## ⚡ Complexity Analysis
- **Time Complexity:** O(n²) where n = length of string
- **Space Complexity:** O(1) for expand approach, O(n²) for DP

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Count single characters first (each is a palindrome)

Expand from each character (odd length) and between characters (even length)

Use two pointers to expand outward while characters match

Increment count for each valid expansion

🔗 Related Problems
Longest Palindromic Substring
Longest Palindromic Subsequence
Palindromic Substrings (this problem)
Palindrome Partitioning II
Shortest Palindrome
