# 212. Word Search II

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, String, Backtracking, Trie, Matrix  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Uber, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/word-search-ii/)

Given an `m x n` board of characters and a list of strings `words`, return *all words on the board*.

Each word must be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

**Example 1:**

![Example 1](https://assets.leetcode.com/uploads/2020/11/07/search1.jpg)

Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]],
words = ["oath","pea","eat","rain"]
Output: ["eat","oath"]


**Example 2:**

![Example 2](https://assets.leetcode.com/uploads/2020/11/07/search2.jpg)

Input: board = [["a","b"],["c","d"]], words = ["abcb"]
Output: []


**Constraints:**
- `m == board.length`
- `n == board[i].length`
- `1 <= m, n <= 12`
- `1 <= words.length <= 3 * 10^4`
- `1 <= words[i].length <= 10`
- `board[i][j]` and `words[i]` consist of lowercase English letters.
- All the strings of `words` are unique.

## 🧠 Thought Process

### Problem Understanding
We need to find all words from a given list that can be formed by traversing adjacent cells (up, down, left, right) in a 2D board, without reusing cells within a single word.

### Key Insights
1. **Trie for Prefix Lookup**: Store all words in a Trie for efficient prefix checking during DFS
2. **Backtracking/DFS**: Explore all possible paths from each cell
3. **Pruning**: Stop exploration when current path doesn't match any prefix in Trie
4. **Board Modification**: Mark visited cells to avoid reuse within a word
5. **Optimization**: Remove found words from Trie to avoid duplicates

### Approach Selection
**Chosen Approach:** Trie + Backtracking  
**Why this approach?**
- Trie provides O(L) lookup for prefixes (L = word length)
- Backtracking explores all possibilities efficiently
- Early pruning significantly reduces search space
- Handles up to 3×10⁴ words efficiently

**Alternative Approaches:**
- HashSet + Backtracking: Too slow for large word lists
- Building all possible strings: Exponential, infeasible

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n × 4 × 3^(L-1)) where L is average word length
- **Space Complexity:** O(total characters in words) for Trie

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Build Trie from words for efficient prefix checking

Use DFS/backtracking from each cell

Mark visited cells by temporarily modifying the board

Remove words from Trie after finding to avoid duplicates

Prune search when no words start with current prefix

🔗 Related Problems
Word Search
Implement Trie (Prefix Tree)
Design Add and Search Words Data Structure
Word Break II
Word Ladder II

