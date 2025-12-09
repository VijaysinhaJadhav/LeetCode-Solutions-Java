# 3. Longest Substring Without Repeating Characters

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, String, Sliding Window  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle, Adobe

[LeetCode Link](https://leetcode.com/problems/longest-substring-without-repeating-characters/)

Given a string `s`, find the length of the **longest substring** without repeating characters.

**Example 1:**

Input: s = "abcabcbb"

Output: 3

Explanation: The answer is "abc", with the length of 3.


**Example 2:**

Input: s = "bbbbb"

Output: 1

Explanation: The answer is "b", with the length of 1.


**Example 3:**

Input: s = "pwwkew"

Output: 3

Explanation: The answer is "wke", with the length of 3.

Note that the answer must be a substring, "pwke" is a subsequence and not a substring.


**Constraints:**
- `0 <= s.length <= 5 * 10^4`
- `s` consists of English letters, digits, symbols and spaces.

**Follow-up:** Can you come up with an algorithm that runs in O(n) time?

## 🧠 Thought Process

### Initial Thoughts
- Need to find the longest contiguous substring with all unique characters
- Multiple approaches with different time/space trade-offs
- The challenge is to efficiently track characters and their positions

### Key Insights
1. **Sliding Window**: Maintain a window of unique characters using two pointers
2. **Hash Map**: Store the most recent index of each character for O(1) lookups
3. **Character Set**: Use boolean array or set to track characters in current window
4. **The key insight**: When we find a duplicate, move the left pointer to position after the duplicate's last occurrence

### Approach Selection
**Chosen Approach:** Sliding Window with Hash Map  
**Why this approach?** 
- O(n) time complexity
- O(min(m, n)) space complexity (m is character set size)
- Handles all edge cases efficiently
- Meets follow-up requirement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the string
- **Space Complexity:** O(min(m, n)) - Where m is the character set size (128 for ASCII)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The sliding window technique is optimal for this problem

We use a hash map to store the most recent index of each character

When a duplicate is found, we jump the left pointer to avoid unnecessary iterations

The algorithm ensures we always maintain a window of unique characters

🔗 Related Problems
Longest Substring with At Most Two Distinct Characters

Longest Substring with At Most K Distinct Characters

Longest Substring with At Least K Repeating Characters

Longest Repeating Character Replacement

Subarrays with K Different Integers
