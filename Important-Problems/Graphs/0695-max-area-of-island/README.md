# 695. Max Area of Island

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Depth-First Search, Breadth-First Search, Union Find, Matrix  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple

[LeetCode Link](https://leetcode.com/problems/max-area-of-island/)

You are given an `m x n` binary matrix `grid`. An island is a group of `1`'s (representing land) connected **4-directionally** (horizontal or vertical). You may assume all four edges of the grid are surrounded by water.

The **area** of an island is the number of cells with value `1` in the island.

Return *the maximum **area** of an island in* `grid`. If there is no island, return `0`.

**Example 1:**

Input: grid = [

[0,0,1,0,0,0,0,1,0,0,0,0,0],

[0,0,0,0,0,0,0,1,1,1,0,0,0],

[0,1,1,0,1,0,0,0,0,0,0,0,0],

[0,1,0,0,1,1,0,0,1,0,1,0,0],

[0,1,0,0,1,1,0,0,1,1,1,0,0],

[0,0,0,0,0,0,0,0,0,0,1,0,0],

[0,0,0,0,0,0,0,1,1,1,0,0,0],

[0,0,0,0,0,0,0,1,1,0,0,0,0]

]

Output: 6

Explanation: The answer is not 11, because the island must be connected 4-directionally.


**Example 2:**

Input: grid = [[0,0,0,0,0,0,0,0]]

Output: 0


**Constraints:**
- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 50`
- `grid[i][j]` is either `0` or `1`.

## 🧠 Thought Process

### Initial Thoughts
- Similar to "Number of Islands" but need to track area instead of count
- Need to find connected components of 1's and calculate their areas
- Return the maximum area among all islands
- Can use DFS, BFS, or Union Find

### Key Insights
1. **DFS Approach**: Recursively explore connected land cells and count them
2. **Area Tracking**: Maintain current area during traversal and update global maximum
3. **Visited Marking**: Mark visited cells to avoid double counting
4. **Early Optimization**: Can stop if remaining cells cannot exceed current max

### Approach Selection
**Chosen Approach:** DFS Recursive  
**Why this approach?** 
- O(m × n) time complexity - visit each cell once
- O(m × n) space complexity for recursion stack
- Simple and intuitive implementation
- Easy to track area during traversal

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n) - Each cell is visited once
- **Space Complexity:** O(m × n) in worst case for recursion stack

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use DFS to explore each island and count its area

Mark visited cells by changing 1 to 0 or using visited array

Track maximum area encountered

Only explore unvisited land cells

🔗 Related Problems
Number of Islands

Island Perimeter

Surrounded Regions

Number of Distinct Islands

Number of Closed Islands

Count Sub Islands
