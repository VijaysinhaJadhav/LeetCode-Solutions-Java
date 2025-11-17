# 64. Minimum Path Sum

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming, Matrix  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/minimum-path-sum/)

Given a `m x n` grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.

**Note:** You can only move either down or right at any point in time.

**Example 1:**

Input: grid = [[1,3,1],[1,5,1],[4,2,1]]

Output: 7

Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.


**Example 2:**

Input: grid = [[1,2,3],[4,5,6]]

Output: 12


**Constraints:**
- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 200`
- `0 <= grid[i][j] <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Need to find the minimum cost path from top-left to bottom-right
- Can only move down or right
- Similar to Unique Paths problems but with costs instead of counts
- Can use dynamic programming to build solution efficiently

### Key Insights
1. **Dynamic Programming Approach**: Build solution using DP table where dp[i][j] = min path sum to (i,j)
2. **State Transition**: dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])
3. **Space Optimization**: Can use 1D DP array or modify grid in-place
4. **Base Cases**: First row and first column have only one possible path

### Approach Selection
**Chosen Approach:** Dynamic Programming with Space Optimization  
**Why this approach?** 
- O(m × n) time complexity is optimal
- O(n) space complexity is efficient
- Clear and intuitive implementation
- Handles all edge cases properly

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n) - Process each cell once
- **Space Complexity:** O(n) - Using single array for DP

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Initialize first row and column with cumulative sums

For other cells, take minimum of top and left plus current cell value

Can optimize space by using only one array

Can also solve with DFS + memoization for alternative approach

🔗 Related Problems
Unique Paths

Unique Paths II

Triangle

Dungeon Game

Minimum Falling Path Sum
