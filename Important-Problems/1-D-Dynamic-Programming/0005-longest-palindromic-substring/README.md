# 5. Longest Palindromic Substring

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Dynamic Programming, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn, Oracle

[LeetCode Link](https://leetcode.com/problems/longest-palindromic-substring/)

Given a string `s`, return the longest palindromic substring in `s`.

A string is called a palindrome if it reads the same backward as forward.

**Example 1:**

Input: s = "babad"

Output: "bab"

Explanation: "aba" is also a valid answer.


**Example 2:**

Input: s = "cbbd"

Output: "bb"


**Constraints:**
- `1 <= s.length <= 1000`
- `s` consist of only digits and English letters.

## 🧠 Thought Process

### Initial Thoughts
- Need to find the longest substring that is a palindrome
- Multiple approaches with different time/space trade-offs
- The challenge is to do it efficiently for strings up to length 1000

### Key Insights
1. **Expand Around Center**: Consider each character as center and expand outwards
2. **Dynamic Programming**: Use DP table to store palindrome information
3. **Manacher's Algorithm**: O(n) solution but more complex
4. **All approaches must handle both odd and even length palindromes**

### Approach Selection
**Chosen Approach:** Expand Around Center  
**Why this approach?** 
- O(n²) time complexity - acceptable for n ≤ 1000
- O(1) space complexity
- Simple and intuitive implementation
- Handles both odd and even length palindromes naturally

## ⚡ Complexity Analysis
- **Time Complexity:** O(n²) where n is the length of the string
- **Space Complexity:** O(1) for the expand around center approach

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Consider each position as potential center of palindrome

Expand outwards to find longest palindrome from that center

Handle both odd-length (single center) and even-length (two centers) cases

Keep track of the longest palindrome found

🔗 Related Problems
Longest Palindromic Subsequence

Palindromic Substrings

Shortest Palindrome

Palindrome Pairs

Longest Palindrome

Valid Palindrome

Valid Palindrome II
