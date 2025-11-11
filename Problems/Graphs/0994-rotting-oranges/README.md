# 994. Rotting Oranges

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Breadth-First Search, Matrix  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/rotting-oranges/)

You are given an `m x n` `grid` where each cell can have one of three values:
- `0` representing an empty cell,
- `1` representing a fresh orange, or
- `2` representing a rotten orange.

Every minute, any fresh orange that is **4-directionally adjacent** to a rotten orange becomes rotten.

Return *the minimum number of minutes that must elapse until no cell has a fresh orange*. If this is impossible, return `-1`.

**Example 1:**

Input: grid = [[2,1,1],[1,1,0],[0,1,1]]

Output: 4

Explanation:

Minute 0: [[2,1,1],[1,1,0],[0,1,1]]

Minute 1: [[2,2,1],[2,1,0],[0,1,1]]

Minute 2: [[2,2,2],[2,2,0],[0,1,1]]

Minute 3: [[2,2,2],[2,2,0],[0,2,1]]

Minute 4: [[2,2,2],[2,2,0],[0,2,2]]


**Example 2:**

Input: grid = [[2,1,1],[0,1,1],[1,0,1]]

Output: -1

Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten.


**Example 3:**

Input: grid = [[0,2]]

Output: 0

Explanation: Since there are no fresh oranges at minute 0, the answer is just 0.


**Constraints:**
- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 10`
- `grid[i][j]` is `0`, `1`, or `2`.

## 🧠 Thought Process

### Initial Thoughts
- Need to simulate the rotting process minute by minute
- Fresh oranges adjacent to rotten oranges become rotten each minute
- Similar to multi-source BFS problem
- Need to track if all fresh oranges can be rotten

### Key Insights
1. **Multi-source BFS**: Start BFS from all rotten oranges simultaneously
2. **Time Tracking**: Track minutes using level-by-level BFS
3. **Fresh Orange Count**: Count fresh oranges initially to check if all get rotten
4. **Impossible Case**: If fresh oranges remain after BFS, return -1

### Approach Selection
**Chosen Approach:** Multi-source BFS  
**Why this approach?** 
- O(m × n) time complexity - visit each cell once
- O(m × n) space complexity for queue
- Naturally simulates the minute-by-minute rotting process
- Efficient and intuitive

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n) - Each cell processed at most once
- **Space Complexity:** O(m × n) - Queue size in worst case

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use BFS starting from all rotten oranges

Track minutes using level-by-level processing

Count fresh oranges initially and decrement as they rot

Return -1 if fresh oranges remain after BFS

Return 0 if no fresh oranges initially

🔗 Related Problems
Walls and Gates

01 Matrix

Shortest Distance from All Buildings

Shortest Path in Binary Matrix

As Far from Land as Possible
