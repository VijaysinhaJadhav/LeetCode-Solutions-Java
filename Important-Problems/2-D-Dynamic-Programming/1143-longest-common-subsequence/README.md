# 1143. Longest Common Subsequence

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Dynamic Programming  
**Companies:** Amazon, Microsoft, Google, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/longest-common-subsequence/)

Given two strings `text1` and `text2`, return the length of their longest common subsequence. If there is no common subsequence, return `0`.

A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.

- For example, `"ace"` is a subsequence of `"abcde"`.

A common subsequence of two strings is a subsequence that is common to both strings.

**Example 1:**

Input: text1 = "abcde", text2 = "ace"

Output: 3

Explanation: The longest common subsequence is "ace" and its length is 3.


**Example 2:**

Input: text1 = "abc", text2 = "abc"

Output: 3

Explanation: The longest common subsequence is "abc" and its length is 3.


**Example 3:**

Input: text1 = "abc", text2 = "def"

Output: 0

Explanation: There is no such common subsequence, so the result is 0.


**Constraints:**
- `1 <= text1.length, text2.length <= 1000`
- `text1` and `text2` consist of only lowercase English characters.

## 🧠 Thought Process

### Initial Thoughts
- Need to find the longest subsequence common to both strings
- Characters don't need to be contiguous, but order must be preserved
- Classic dynamic programming problem with well-defined recurrence relation

### Key Insights
1. **Dynamic Programming**: Use a 2D DP table where dp[i][j] = LCS of text1[0..i-1] and text2[0..j-1]
2. **Recurrence Relation**:
   - If characters match: dp[i][j] = 1 + dp[i-1][j-1]
   - If characters don't match: dp[i][j] = max(dp[i-1][j], dp[i][j-1])
3. **Space Optimization**: Can reduce space from O(m×n) to O(min(m,n))

### Approach Selection
**Chosen Approach:** Dynamic Programming with 2D Table  
**Why this approach?** 
- O(m×n) time complexity
- Clear and intuitive recurrence relation
- Easy to understand and implement
- Can be extended to reconstruct the actual subsequence

## ⚡ Complexity Analysis
- **Time Complexity:** O(m×n) where m and n are lengths of text1 and text2
- **Space Complexity:** O(m×n) for DP table, O(min(m,n)) with optimization

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Initialize DP table with extra row and column for base cases

The recurrence relation captures all possibilities when characters match or don't match

The bottom-right cell of DP table contains the final answer

Can reconstruct the actual LCS by backtracking through the DP table

🔗 Related Problems
Edit Distance

Distinct Subsequences

Delete Operation for Two Strings

Minimum ASCII Delete Sum for Two Strings

Shortest Common Supersequence
