# 1631. Path With Minimum Effort

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search, Depth-First Search, Breadth-First Search, Union Find, Heap (Priority Queue), Matrix  
**Companies:** Google, Amazon, Microsoft, Uber, Bloomberg, Apple  

[LeetCode Link](https://leetcode.com/problems/path-with-minimum-effort/)

You are given a `rows x cols` rectangular map of integers called `heights` where `heights[row][col]` represents the height of the cell `(row, col)`. You are situated in the top-left cell, `(0, 0)`, and you want to travel to the bottom-right cell, `(rows-1, cols-1)`.

You can move up, down, left, or right, and you wish to find a route that requires the **minimum effort**.

A route's **effort** is the **maximum absolute difference** in heights between two consecutive cells of the route.

Return *the minimum effort required to travel from the top-left cell to the bottom-right cell.*

**Example 1:**

![Example 1](https://assets.leetcode.com/uploads/2020/10/04/ex1.png)

Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
Output: 2
Explanation: The route [1,3,5,3,5] has a maximum absolute difference of 2 between consecutive cells.
This is better than the route [1,2,2,2,5] where the maximum absolute difference is 3.


**Example 2:**

![Example 2](https://assets.leetcode.com/uploads/2020/10/04/ex2.png)

Input: heights = [[1,2,3],[3,8,4],[5,3,5]]
Output: 1
Explanation: The route [1,2,3,4,5] has a maximum absolute difference of 1 between consecutive cells.


**Example 3:**

![Example 3](https://assets.leetcode.com/uploads/2020/10/04/ex3.png)

Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
Output: 0
Explanation: This route does not require any effort as the path goes through cells with the same height.


**Constraints:**
- `rows == heights.length`
- `cols == heights[i].length`
- `1 <= rows, cols <= 100`
- `1 <= heights[i][j] <= 10^6`

## 🧠 Thought Process

### Problem Understanding
We need to find a path from top-left to bottom-right where the **maximum height difference** between consecutive cells is minimized. This is different from shortest path problems – we're minimizing the worst step, not the total sum.

### Key Insights
1. **Min-Max Path Problem**: We want to minimize the maximum edge weight along the path
2. **Binary Search Approach**: Binary search on the maximum allowed effort, check if path exists with BFS/DFS
3. **Dijkstra's Algorithm**: Modified to track maximum difference instead of sum
4. **Union-Find**: Sort edges by effort, connect cells until start and end are connected
5. **Graph Representation**: Each cell is a node, edges connect adjacent cells with weight = |height difference|

### Approach Selection
**Chosen Approach:** Dijkstra's Algorithm (Modified)  
**Why this approach?**
- Classic approach for min-max path problems
- Guarantees optimal solution
- Efficient O(rows × cols × log(rows × cols)) complexity
- Intuitive: always expand the cell with smallest current max effort

**Alternative Approaches:**
- **Binary Search + BFS/DFS**: O(log(MAX) × rows × cols)
- **Union-Find with sorted edges**: O(rows × cols × log(rows × cols))
- **Dynamic Programming**: Not directly applicable due to cycle potential

## ⚡ Complexity Analysis
- **Time Complexity:** O(rows × cols × log(rows × cols)) for Dijkstra
- **Space Complexity:** O(rows × cols) for visited array and priority queue

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
This is a min-max path problem, not a shortest path problem

Dijkstra's algorithm can be adapted by using max(currentDiff, edgeDiff) instead of sum(current, edge)

Binary search approach: guess the maximum effort, check feasibility with BFS

All cells can be visited multiple times if we find a better max effort

🔗 Related Problems
Swim in Rising Water (similar min-max path problem)
Path With Maximum Minimum Value
Path with Maximum Probability
Path With Minimum Effort (this problem)
The Maze II (shortest path in grid)
