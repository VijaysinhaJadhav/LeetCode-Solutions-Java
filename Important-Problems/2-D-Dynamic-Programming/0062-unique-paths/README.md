# 62. Unique Paths

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Math, Dynamic Programming, Combinatorics  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/unique-paths/)

There is a robot on an `m x n` grid. The robot is initially located at the **top-left corner** (i.e., `grid[0][0]`). The robot tries to move to the **bottom-right corner** (i.e., `grid[m - 1][n - 1]`). The robot can only move either down or right at any point in time.

Given the two integers `m` and `n`, return the number of possible unique paths that the robot can take to reach the bottom-right corner.

**Example 1:**

![Robot Maze](https://assets.leetcode.com/uploads/2018/10/22/robot_maze.png)

Input: m = 3, n = 7

Output: 28


**Example 2:**

Input: m = 3, n = 2

Output: 3

Explanation: From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:

Right -> Down -> Down

Down -> Down -> Right

Down -> Right -> Down


**Constraints:**
- `1 <= m, n <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Robot can only move right or down
- Need to count all possible paths from top-left to bottom-right
- Can use dynamic programming or combinatorial mathematics

### Key Insights
1. **Dynamic Programming**: Each cell's paths = paths from left + paths from above
2. **Combinatorics**: Total moves = (m-1) downs + (n-1) rights, so C(m+n-2, m-1)
3. **Space Optimization**: Can use single array instead of full 2D table

### Approach Selection
**Chosen Approach:** Dynamic Programming with Space Optimization  
**Why this approach?** 
- O(m×n) time complexity
- O(n) space complexity
- Intuitive and easy to understand
- Handles all cases efficiently

## ⚡ Complexity Analysis
- **Time Complexity:** O(m×n)
- **Space Complexity:** O(n)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
First row and first column always have only 1 path (straight line)

Each cell's value = value from left + value from above

Can be solved mathematically using combinations: C(m+n-2, m-1)

🔗 Related Problems
Unique Paths II

Minimum Path Sum

Triangle

Dungeon Game

Unique Paths III
