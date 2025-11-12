# 76. Minimum Window Substring

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Hash Table, String, Sliding Window, Two Pointers  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn, Oracle

[LeetCode Link](https://leetcode.com/problems/minimum-window-substring/)

Given two strings `s` and `t` of lengths `m` and `n` respectively, return the minimum window substring of `s` such that every character in `t` (including duplicates) is included in the window. If there is no such substring, return the empty string `""`.

The testcases will be generated such that the answer is unique.

**Example 1:**

Input: s = "ADOBECODEBANC", t = "ABC"

Output: "BANC"

Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.


**Example 2:**

Input: s = "a", t = "a"

Output: "a"

Explanation: The entire string s is the minimum window.


**Example 3:**

Input: s = "a", t = "aa"

Output: ""

Explanation: Both 'a's from t must be included in the window.

Since the largest window of s only has one 'a', return empty string.


**Constraints:**
- `m == s.length`
- `n == t.length`
- `1 <= m, n <= 10^5`
- `s` and `t` consist of uppercase and lowercase English letters.

**Follow-up:** Could you find an algorithm that runs in O(m + n) time?

## 🧠 Thought Process

### Initial Thoughts
- Need to find smallest substring containing all characters from t
- Characters in t can have duplicates that must be satisfied
- Multiple approaches with sliding window being optimal
- The challenge is to efficiently track character counts

### Key Insights
1. **Sliding Window Approach**: Use two pointers to expand and contract window
2. **Character Counting**: Use frequency maps to track required and current counts
3. **Valid Window Check**: Track when all characters from t are satisfied
4. **Optimization**: Shrink window from left when valid to find minimum

### Approach Selection
**Chosen Approach:** Sliding Window with Character Counts  
**Why this approach?** 
- O(m + n) time complexity - optimal
- O(1) space complexity (fixed size character sets)
- Efficiently finds minimum window
- Handles duplicates correctly

## ⚡ Complexity Analysis
- **Time Complexity:** O(m + n) where m is length of s, n is length of t
- **Space Complexity:** O(1) since we use fixed-size arrays for ASCII characters

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use two frequency arrays: one for target counts, one for current window counts

Maintain a counter to track how many characters from t are satisfied

Expand window to right until valid, then shrink from left to find minimum

Handle edge cases: empty strings, no valid window, single characters

🔗 Related Problems
Longest Substring Without Repeating Characters

Substring with Concatenation of All Words

Longest Substring with At Most Two Distinct Characters

Longest Substring with At Most K Distinct Characters

Longest Repeating Character Replacement

Find All Anagrams in a String

Permutation in String

Smallest Range Covering Elements from K Lists
