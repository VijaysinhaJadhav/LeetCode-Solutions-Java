# 130. Surrounded Regions

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Depth-First Search, Breadth-First Search, Union Find, Matrix  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple

[LeetCode Link](https://leetcode.com/problems/surrounded-regions/)

Given an `m x n` matrix `board` containing `'X'` and `'O'`, capture all regions that are 4-directionally surrounded by `'X'`.

A region is **captured** by flipping all `'O'`s into `'X'`s in that surrounded region.

**Example 1:**

Input: board = [

["X","X","X","X"],

["X","O","O","X"],

["X","X","O","X"],

["X","O","X","X"]

]

Output: [

["X","X","X","X"],

["X","X","X","X"],

["X","X","X","X"],

["X","O","X","X"]

]

Explanation: The bottom 'O' is not surrounded because it is on the border.


**Example 2:**

Input: board = [["X"]]

Output: [["X"]]


**Constraints:**
- `m == board.length`
- `n == board[i].length`
- `1 <= m, n <= 200`
- `board[i][j]` is `'X'` or `'O'`.

## 🧠 Thought Process

### Initial Thoughts
- Need to identify 'O's that are completely surrounded by 'X's
- 'O's on the border cannot be surrounded
- Can use DFS/BFS starting from border 'O's to mark non-capturable regions
- Then flip the remaining 'O's to 'X's

### Key Insights
1. **Reverse Thinking**: Instead of finding surrounded regions, find non-surrounded regions first
2. **Border DFS**: Start from border 'O's and mark all connected 'O's as safe
3. **Two-pass Approach**: First mark safe regions, then capture surrounded regions
4. **In-place Modification**: Use temporary marker or modify directly

### Approach Selection
**Chosen Approach:** DFS from Borders  
**Why this approach?** 
- O(m × n) time complexity - visit each cell twice
- O(m × n) space complexity for recursion stack in worst case
- Intuitive and efficient
- Handles all edge cases naturally

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n) - Each cell processed at most twice
- **Space Complexity:** O(m × n) - Recursion stack in worst case

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Start DFS from all border 'O's and mark them as temporary character

After marking all non-capturable regions, flip remaining 'O's to 'X'

Restore temporary markers back to 'O'

Handle edge cases: empty board, single cell

🔗 Related Problems
Number of Islands

Walls and Gates

Pacific Atlantic Water Flow

Max Area of Island

Number of Closed Islands
