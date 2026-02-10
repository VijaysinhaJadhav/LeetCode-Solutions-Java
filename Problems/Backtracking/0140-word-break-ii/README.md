# 140. Word Break II

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Hash Table, String, Dynamic Programming, Backtracking, Trie, Memoization  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber  

[LeetCode Link](https://leetcode.com/problems/word-break-ii/)

Given a string `s` and a dictionary of strings `wordDict`, add spaces in `s` to construct a sentence where each word is a valid dictionary word. Return all such possible sentences in **any order**.

Note that the same word in the dictionary may be reused multiple times in the segmentation.

**Example 1:**

Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
Output: ["cats and dog","cat sand dog"]


**Example 2:**

Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
Explanation: Note that you are allowed to reuse a dictionary word.


**Example 3:**

Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: []


**Constraints:**
- `1 <= s.length <= 20`
- `1 <= wordDict.length <= 1000`
- `1 <= wordDict[i].length <= 10`
- `s` and `wordDict[i]` consist of only lowercase English letters.
- All the strings of `wordDict` are **unique**.

## 🧠 Thought Process

### Problem Understanding
We need to find ALL ways to break a string into dictionary words, not just check if it's possible.

### Key Insights
1. **Backtracking with Memoization**: Need to explore all possibilities, memoization prevents recomputation
2. **Trie Optimization**: For efficient dictionary lookups
3. **Word Break I First**: Check if segmentation is possible before trying to find all solutions
4. **Early Pruning**: Stop exploring paths that can't lead to valid solutions

### Approach Selection
**Chosen Approach**: DFS + Memoization (Top-down DP)  
**Why this approach?**
- Naturally explores all possibilities
- Memoization prevents exponential blow-up
- Clear and intuitive implementation
- Handles the constraints efficiently

**Alternative Approaches**:
- BFS with memoization
- Bottom-up DP with reconstruction
- Trie + Backtracking

## ⚡ Complexity Analysis
- **Time Complexity**: O(n × 2^n) worst-case, but significantly reduced by memoization
- **Space Complexity**: O(n × 2^n) for storing results, O(n) for recursion depth

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The problem asks for ALL solutions, not just existence

Memoization is crucial to avoid exponential time

Use HashSet for O(1) dictionary lookups

Build sentences from right to left or left to right

Handle the case where no solution exists

🔗 Related Problems
Word Break
Concatenated Words
Word Ladder II
Word Search II
Word Squares
