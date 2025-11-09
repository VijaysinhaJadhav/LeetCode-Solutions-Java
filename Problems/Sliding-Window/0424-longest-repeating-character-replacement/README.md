# 424. Longest Repeating Character Replacement

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, String, Sliding Window  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/longest-repeating-character-replacement/)

You are given a string `s` and an integer `k`. You can choose any character of the string and change it to any other uppercase English character. You can perform this operation at most `k` times.

Return the length of the longest substring containing the same letter you can get after performing the above operations.

**Example 1:**

Input: s = "ABAB", k = 2

Output: 4

Explanation: Replace the two 'A's with two 'B's or vice versa.


**Example 2:**

Input: s = "AABABBA", k = 1

Output: 4

Explanation: Replace the one 'A' in the middle with 'B' to form "AABBBBA".

The substring "BBBB" has length 4, which is the longest.


**Constraints:**
- `1 <= s.length <= 10^5`
- `s` consists of only uppercase English letters.
- `0 <= k <= s.length`

**Follow-up:** Can you solve it in O(n) time?

## 🧠 Thought Process

### Initial Thoughts
- Need to find the longest substring where we can make all characters the same with at most k replacements
- The key insight: (window length - max frequency count) <= k
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Sliding Window**: Maintain a window that satisfies the replacement condition
2. **Frequency Counting**: Track character frequencies within the current window
3. **Max Frequency**: Track the maximum frequency character in the current window
4. **The key insight**: A window is valid if (window length - max frequency) <= k

### Approach Selection
**Chosen Approach:** Sliding Window with Frequency Array  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity (fixed size array for 26 letters)
- Efficient frequency tracking
- Meets follow-up requirement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the string
- **Space Complexity:** O(1) - Fixed size frequency array (26 elements)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The sliding window technique is optimal for this problem

We track the maximum frequency character in the current window

The window is valid when (window length - max frequency) <= k

We only shrink the window when it becomes invalid

🔗 Related Problems
Longest Substring Without Repeating Characters

Longest Substring with At Most Two Distinct Characters

Longest Substring with At Most K Distinct Characters

Max Consecutive Ones III

Longest Subarray of 1's After Deleting One Element
