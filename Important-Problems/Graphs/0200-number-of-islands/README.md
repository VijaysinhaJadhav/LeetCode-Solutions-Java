# 200. Number of Islands

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Depth-First Search, Breadth-First Search, Union Find, Matrix  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Uber

[LeetCode Link](https://leetcode.com/problems/number-of-islands/)

Given an `m x n` 2D binary grid `grid` which represents a map of `'1'`s (land) and `'0'`s (water), return *the number of islands*.

An **island** is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are surrounded by water.

**Example 1:**

Input: grid = [

["1","1","1","1","0"],

["1","1","0","1","0"],

["1","1","0","0","0"],

["0","0","0","0","0"]

]

Output: 1


**Example 2:**

Input: grid = [

["1","1","0","0","0"],

["1","1","0","0","0"],

["0","0","1","0","0"],

["0","0","0","1","1"]

]

Output: 3


**Constraints:**
- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 300`
- `grid[i][j]` is `'0'` or `'1'`.

## 🧠 Thought Process

### Initial Thoughts
- Need to count connected components of '1's (islands)
- Islands are connected horizontally or vertically (4-directional)
- Multiple traversal approaches: DFS, BFS, Union Find
- Need to mark visited cells to avoid double counting

### Key Insights
1. **DFS Approach**: Recursively explore connected land cells and mark them as visited
2. **BFS Approach**: Use queue to explore connected land cells level by level
3. **Union Find**: Connect adjacent land cells and count distinct components
4. **In-place Modification**: Can mark visited cells by changing '1' to '0' or use visited array

### Approach Selection
**Chosen Approach:** DFS (Depth-First Search)  
**Why this approach?** 
- O(m × n) time complexity - visit each cell once
- O(m × n) space complexity in worst case (recursion stack)
- Simple and intuitive implementation
- Easy to understand and modify

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n) - Each cell is visited once
- **Space Complexity:** O(m × n) in worst case for recursion stack, O(min(m, n)) for BFS queue

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use DFS to explore each island completely when found

Mark visited cells by changing '1' to '0' (destructive) or use visited array (non-destructive)

Check all four directions: up, down, left, right

Only increment count when finding a new unvisited '1'

🔗 Related Problems
Island Perimeter

Max Area of Island

Surrounded Regions

Number of Distinct Islands

Number of Closed Islands

Count Sub Islands
