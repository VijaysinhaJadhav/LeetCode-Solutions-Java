# 79. Word Search

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Backtracking, Matrix, Depth-First Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle, TikTok

[LeetCode Link](https://leetcode.com/problems/word-search/)

Given an `m x n` grid of characters `board` and a string `word`, return `true` if `word` exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

**Example 1:**

Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"

Output: true


**Example 2:**

Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"

Output: true


**Example 3:**

Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"

Output: false


**Constraints:**
- `m == board.length`
- `n == board[i].length`
- `1 <= m, n <= 6`
- `1 <= word.length <= 15`
- `board` and `word` consist of only uppercase and lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- Need to search for a word in a 2D grid
- Letters must be adjacent (up, down, left, right)
- Cannot reuse the same cell in a single path
- Need to explore all possible starting positions

### Key Insights
1. **Backtracking/DFS**: Explore all paths from each starting position
2. **Pruning**: Stop early when current path cannot form the word
3. **Visited Tracking**: Mark cells as visited during current path exploration
4. **Direction Vectors**: Use arrays to represent 4 possible movement directions

### Approach Selection
**Chosen Approach:** Backtracking with DFS  
**Why this approach?** 
- Naturally handles the path exploration with constraints
- Efficient pruning of invalid paths
- Clear implementation with recursion
- O(m × n × 4^L) time complexity is acceptable given constraints

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n × 4^L) where L is word length
- **Space Complexity:** O(L) for recursion stack and visited tracking

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a visited matrix or in-place modification to track used cells

Explore all four directions at each step

Backtrack by unmarking visited cells

Start search from every cell in the grid

Prune early when characters don't match

🔗 Related Problems
Word Search II

Unique Paths II

Surrounded Regions

Number of Islands

Unique Paths III
