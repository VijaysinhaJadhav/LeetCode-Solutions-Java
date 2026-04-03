# 91. Decode Ways

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Dynamic Programming  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Uber  

[LeetCode Link](https://leetcode.com/problems/decode-ways/)

A message containing letters from `A` to `Z` can be encoded into numbers using the mapping:

'A' -> "1"
'B' -> "2"
...
'Z' -> "26"


Given a string `s` containing only digits, return the **number of ways** to decode it.

**Example 1:**

Input: s = "12"
Output: 2
Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).


**Example 2:**

Input: s = "226"
Output: 3
Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).


**Example 3:**

Input: s = "06"
Output: 0
Explanation: "06" cannot be decoded because '0' has no mapping and "06" is not a valid two-digit number.


**Constraints:**
- `1 <= s.length <= 100`
- `s` contains only digits and may contain leading zero(s).

## 🧠 Thought Process

### Problem Understanding
We need to count the number of ways to decode a string of digits into letters, where:
- Single digits: '1' to '9' map to 'A' to 'I'
- Two digits: '10' to '26' map to 'J' to 'Z'
- Leading zeros are invalid ('0' alone or '0x' for x > 0)

### Key Insights
1. **DP State**: Let `dp[i]` = number of ways to decode substring `s[0:i]`
2. **Single Digit Decoding**: If `s[i-1] != '0'`, add `dp[i-1]` ways
3. **Two Digit Decoding**: If `10 <= s[i-2:i] <= 26`, add `dp[i-2]` ways
4. **Base Cases**: `dp[0] = 1` (empty string has 1 way), `dp[1]` depends on first character

### Approach Selection
**Chosen Approach:** Dynamic Programming (Bottom-Up)  
**Why this approach?**
- O(n) time complexity
- O(1) space with space optimization
- Clear recurrence relation

### Alternative Approaches
- **Top-Down DP (Memoization)**: More intuitive but O(n) space for recursion stack
- **Recursive Backtracking**: Exponential time without memoization

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of string
- **Space Complexity:** O(1) with space optimization, O(n) with DP array

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Handle leading zeros carefully

Two-digit numbers must be between 10 and 26 inclusive

Single digit '0' is invalid

The recurrence relation is similar to Fibonacci

🔗 Related Problems
Climbing Stairs (similar recurrence pattern)
Word Break (string segmentation)
Decode Ways II (with wildcards)
Decode Ways (this problem)
House Robber (DP on arrays)
