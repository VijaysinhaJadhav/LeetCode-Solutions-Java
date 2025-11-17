# 63. Unique Paths II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming, Matrix  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/unique-paths-ii/)

You are given an `m x n` integer array `obstacleGrid`. There is a robot initially located at the top-left corner (i.e., `grid[0][0]`). The robot tries to move to the bottom-right corner (i.e., `grid[m-1][n-1]`). The robot can only move either down or right at any point in time.

An obstacle and space are marked as `1` or `0` respectively in `obstacleGrid`. A path that the robot takes cannot include any square that is an obstacle.

Return the number of possible unique paths that the robot can take to reach the bottom-right corner.

**Example 1:**

Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]

Output: 2

Explanation: There is one obstacle in the middle of the 3x3 grid above.

There are two ways to reach the bottom-right corner:

Right → Right → Down → Down

Down → Down → Right → Right


**Example 2:**

Input: obstacleGrid = [[0,1],[0,0]]

Output: 1


**Constraints:**
- `m == obstacleGrid.length`
- `n == obstacleGrid[i].length`
- `1 <= m, n <= 100`
- `obstacleGrid[i][j]` is `0` or `1`.

## 🧠 Thought Process

### Initial Thoughts
- Similar to Unique Paths I, but with obstacles
- Need to handle obstacles by setting paths through them to 0
- Can use dynamic programming to build solution bottom-up
- Base cases need special handling when start or end has obstacles

### Key Insights
1. **Dynamic Programming Approach**: Build solution using DP table where dp[i][j] = paths to (i,j)
2. **Obstacle Handling**: If cell has obstacle, dp[i][j] = 0
3. **State Transition**: dp[i][j] = dp[i-1][j] + dp[i][j-1] (if no obstacle)
4. **Space Optimization**: Can use 1D DP array to reduce space complexity

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
If start or end cell has obstacle, return 0 immediately

Initialize first row and column carefully around obstacles

Space optimization reduces space from O(m×n) to O(n)

Can also solve with DFS + memoization for alternative approach

🔗 Related Problems
Unique Paths

Minimum Path Sum

Triangle

Dungeon Game

Unique Paths III
