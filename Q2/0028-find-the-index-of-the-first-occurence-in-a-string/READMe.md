# 28. Find the Index of the First Occurrence in a String

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** String, Two Pointers, String Matching  
**Companies:** Amazon, Microsoft, Google, Facebook, Apple  

[LeetCode Link](https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/)

Given two strings `needle` and `haystack`, return the index of the first occurrence of `needle` in `haystack`, or `-1` if `needle` is not part of `haystack`.

**Example 1:**

Input: haystack = "sadbutsad", needle = "sad"
Output: 0
Explanation: "sad" occurs at index 0 and 6. The first occurrence is at index 0.


**Example 2:**

Input: haystack = "leetcode", needle = "leeto"
Output: -1


**Constraints:**
- `1 <= haystack.length, needle.length <= 10^4`
- `haystack` and `needle` consist of only lowercase English characters.

## 🧠 Thought Process

### Problem Understanding
We need to find the first index where `needle` appears as a substring in `haystack`. If not found, return -1.

### Key Insights
1. **Sliding Window**: Compare each substring of length `needle.length()` with `needle`.
2. **KMP (Knuth–Morris–Pratt)**: O(m + n) time, O(m) space, optimal for large inputs.
3. **Built-in Methods**: Use `indexOf` (not allowed in some interview contexts).
4. **Rabin–Karp**: Rolling hash for O(m + n) average.

### Approach Selection
**Chosen Approach:** Sliding Window (Brute Force) – simple and efficient enough for constraints (n ≤ 10^4).  
**Why this approach?**
- O(n·m) worst case, but average case fast.
- No extra preprocessing, easy to implement.
- For an interview, can mention KMP as optimization.

**Alternative Approaches:**
- **KMP**: O(n + m) time, O(m) space – optimal.
- **Built-in `indexOf`**: Not allowed if implementing from scratch.

## ⚡ Complexity Analysis
- **Sliding Window:** O((n - m) * m) time, O(1) space.
- **KMP:** O(n + m) time, O(m) space.

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Edge case: needle.length() > haystack.length() → return -1.

Empty needle? Not in constraints, but many solutions return 0.

For KMP, build LPS (longest proper prefix which is also suffix) array.

🔗 Related Problems
Find the Index of the First Occurrence in a String (this problem)
Shortest Palindrome
Repeated Substring Pattern
Longest Happy Prefix
Repeated String Match
