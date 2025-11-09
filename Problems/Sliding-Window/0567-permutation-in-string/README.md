# 567. Permutation in String

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, Two Pointers, String, Sliding Window  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle, Adobe

[LeetCode Link](https://leetcode.com/problems/permutation-in-string/)

Given two strings `s1` and `s2`, return `true` if `s2` contains a permutation of `s1`, or `false` otherwise.

In other words, return `true` if one of `s1`'s permutations is the substring of `s2`.

**Example 1:**

Input: s1 = "ab", s2 = "eidbaooo"

Output: true

Explanation: s2 contains one permutation of s1 ("ba").


**Example 2:**

Input: s1 = "ab", s2 = "eidboaoo"

Output: false


**Constraints:**
- `1 <= s1.length, s2.length <= 10^4`
- `s1` and `s2` consist of lowercase English letters.

**Follow-up:** Can you solve it in O(n) time?

## 🧠 Thought Process

### Initial Thoughts
- Need to check if any substring of s2 has the same character frequency as s1
- This is essentially checking for an anagram of s1 in s2
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Sliding Window**: Maintain a window of size s1.length in s2 and compare frequencies
2. **Frequency Counting**: Use arrays or maps to track character frequencies
3. **Optimized Comparison**: Track matches count instead of comparing full frequency arrays
4. **The key insight**: A permutation exists when character frequencies in a window match s1's frequencies

### Approach Selection
**Chosen Approach:** Sliding Window with Frequency Array and Matches Count  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity (fixed size arrays for 26 letters)
- Efficient frequency tracking with optimized comparison
- Meets follow-up requirement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n is the length of s2
- **Space Complexity:** O(1) - Fixed size arrays for 26 letters

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The sliding window technique is optimal for this problem

We track character frequencies using fixed-size arrays

The matches count optimization avoids comparing full arrays each time

Window size is fixed to the length of s1

🔗 Related Problems
Longest Substring Without Repeating Characters

Minimum Window Substring

Find All Anagrams in a String

Permutation in String

Valid Anagram
