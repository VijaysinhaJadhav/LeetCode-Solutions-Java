# 463. Island Perimeter

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Depth-First Search, Breadth-First Search, Matrix  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple

[LeetCode Link](https://leetcode.com/problems/island-perimeter/)

You are given `row x col` `grid` representing a map where `grid[i][j] = 1` represents land and `grid[i][j] = 0` represents water.

Grid cells are connected **horizontally/vertically** (not diagonally). The grid is completely surrounded by water, and there is exactly one island (i.e., one or more connected land cells).

The island doesn't have "lakes", meaning the water inside isn't connected to the water around the island. One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100.

Determine the perimeter of the island.

**Example 1:**

Input: grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]

Output: 16

Explanation: The perimeter is the 16 yellow stripes in the image.


**Example 2:**

Input: grid = [[1]]

Output: 4


**Example 3:**

Input: grid = [[1,0]]

Output: 4


**Constraints:**
- `row == grid.length`
- `col == grid[i].length`
- `1 <= row, col <= 100`
- `grid[i][j]` is `0` or `1`.
- There is exactly one island in `grid`.

## 🧠 Thought Process

### Initial Thoughts
- Need to calculate the perimeter of a single connected island
- Each land cell contributes 4 to perimeter initially
- Each adjacent land cell reduces perimeter by 2 (shared edge)
- Can count contributions or use DFS/BFS to traverse island

### Key Insights
1. **Counting Approach**: For each land cell, count its exposed sides
2. **Neighbor Check**: Check four directions (up, down, left, right)
3. **Edge Detection**: Water or boundary counts as perimeter edge
4. **Optimization**: Only need to traverse land cells once

### Approach Selection
**Chosen Approach:** Counting with Neighbor Check  
**Why this approach?** 
- O(m × n) time complexity - visit each cell once
- O(1) space complexity - no extra data structures needed
- Simple and intuitive implementation
- Efficient and easy to understand

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n) - Visit each cell in the grid once
- **Space Complexity:** O(1) - Only use constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Each land cell starts with 4 perimeter units

Each adjacent land cell reduces perimeter by 1 (shared edge)

Check all four directions for neighbors

The grid is guaranteed to have exactly one island

🔗 Related Problems
Number of Islands

Max Area of Island

Surrounded Regions

Number of Closed Islands

Number of Enclosed Islands
