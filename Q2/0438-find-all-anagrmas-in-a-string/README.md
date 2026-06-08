# 438. Find All Anagrams in a String

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, String, Sliding Window  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/find-all-anagrams-in-a-string/)

Given two strings `s` and `p`, return an array of all the start indices of `p`'s anagrams in `s`. You may return the answer in any order.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

**Example 1:**

Input: s = "cbaebabacd", p = "abc"
Output: [0,6]
Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".


**Example 2:**

Input: s = "abab", p = "ab"
Output: [0,1,2]
Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".


**Constraints:**
- `1 <= s.length, p.length <= 3 * 10^4`
- `s` and `p` consist of lowercase English letters.

## 🧠 Thought Process

### Problem Understanding
We need to find all starting indices in `s` where a substring of length `len(p)` is an anagram of `p`. Anagrams have the same character frequencies.

### Key Insights
1. **Sliding Window**: Maintain a window of length `len(p)` over `s`.
2. **Frequency Comparison**: Compare frequency arrays of the window and `p`.
3. **Optimization**: Use a `diff` count to avoid full array comparison each time.

### Approach Selection
**Chosen Approach:** Sliding Window with Frequency Arrays  
**Why this approach?**
- O(n) time (n = length of s)
- O(1) space (fixed 26 letters)

**Alternative Approaches:**
- **Sorting each substring**: O(n·m log m) too slow.
- **Hash Map with character counts**: Similar to frequency array but more overhead.

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = len(s). Each character is processed once.
- **Space Complexity:** O(1) – fixed size arrays (26).

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use an integer array of size 26 to store character counts.

Maintain a diff count of how many characters have matching frequencies.

When diff == 26, the current window is an anagram.

Edge case: if p.length() > s.length(), return empty list.

🔗 Related Problems
Valid Anagram
Permutation in String
Minimum Window Substring
Longest Substring Without Repeating Characters
