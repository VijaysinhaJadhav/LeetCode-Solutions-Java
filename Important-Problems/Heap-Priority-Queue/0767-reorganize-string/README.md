# 767. Reorganize String

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, String, Greedy, Sorting, Heap (Priority Queue), Counting  
**Companies:** Google, Amazon, Microsoft, Meta, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/reorganize-string/)

Given a string `s`, rearrange the characters of `s` so that any two adjacent characters are not the same.

Return *any possible rearrangement of* `s` *or return* `""` *if not possible*.

**Example 1:**

Input: s = "aab"

Output: "aba"

**Example 2:**

Input: s = "aaab"

Output: ""


**Constraints:**
- `1 <= s.length <= 500`
- `s` consists of lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- Need to rearrange string so no same characters are adjacent
- If a character appears more than half of length + 1, impossible
- Greedy approach: place most frequent characters first
- Use max heap to always pick most frequent available character

### Key Insights
1. **Feasibility Check**: If any character frequency > (n+1)/2, impossible
2. **Greedy Approach**: Always pick most frequent character (except last used)
3. **Heap Solution**: Use max heap ordered by frequency
4. **Alternate Placement**: Interleave most frequent with others

### Approach Selection
**Chosen Approach:** Max Heap with Frequency Tracking  
**Why this approach?** 
- O(n log k) time complexity where k is number of unique characters
- Guarantees valid arrangement if possible
- Intuitive greedy approach
- Handles all edge cases

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log k) where k ≤ 26 (constant for lowercase letters)
- **Space Complexity:** O(k) for frequency map and heap

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
First check feasibility using frequency counts

Use max heap to always get most frequent character

Keep track of last used character to avoid repetition

If heap becomes empty before finishing, return empty string

🔗 Related Problems
Task Scheduler

Rearrange String k Distance Apart

Distant Barcodes

Last Stone Weight II


