# 2257. Count Unguarded Cells in the Grid

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Matrix, Simulation  
**Companies:** Google, Amazon, Microsoft, Bloomberg, TikTok

[LeetCode Link](https://leetcode.com/problems/count-unguarded-cells-in-the-grid/)

You are given two integers `m` and `n` representing a **0-indexed** `m x n` grid. You are also given two 2D integer arrays `guards` and `walls` where:
- `guards[i] = [row_i, col_i]` represents the position of the `i-th` guard
- `walls[j] = [row_j, col_j]` represents the position of the `j-th` wall

A guard can see every cell in the four cardinal directions (north, east, south, west) starting from their position until a wall or another guard is reached. Guards cannot see through walls or other guards.

Return the *number of unguarded cells in the grid*.

**Note:**
- A guard cell is considered guarded by itself.
- A wall cell cannot be guarded.
- Cells containing guards or walls block the line of sight.

**Example 1:**

Input: m = 4, n = 6, guards = [[0,0],[1,1],[2,3]], walls = [[0,1],[2,2],[1,4]]
Output: 7
Explanation:
The guarded and unguarded cells are:

All guard cells are guarded.

All wall cells are unguarded.

Cells with "G" are guards, "W" are walls, "X" are guarded cells, "." are unguarded.
Grid visualization:
G W . . . .
. G . . W .
. . W G . .
. . . . . .
Unguarded cells: 7


**Example 2:**

Input: m = 3, n = 3, guards = [[1,1]], walls = [[0,1],[1,0],[2,1],[1,2]]
Output: 4
Explanation:
Grid:
. W .
W G W
. W .
Only the guard cell itself is guarded.
Unguarded cells: 4


**Constraints:**
- `1 <= m, n <= 10^5`
- `1 <= m * n <= 10^5`
- `0 <= guards.length, walls.length <= 5 * 10^4`
- `2 <= guards.length + walls.length <= m * n`
- `guards[i].length == walls[j].length == 2`
- `0 <= row_i, row_j < m`
- `0 <= col_i, col_j < n`
- All guards and walls are unique.

## 🧠 Thought Process

### Initial Thoughts
- Need to simulate guard vision in four directions until blocked
- Brute force: For each guard, mark all cells in four directions until wall/guard
- Challenge: m*n can be up to 10^5, but m and n individually can be up to 10^5
- Need efficient marking of cells

### Key Insights
1. **Matrix Representation Options**:
   - Use 2D array if m*n ≤ 10^5 (constraint ensures this)
   - Could use bitmask or status array
2. **Cell States**:
   - UNGUARDED (0): Not visited, not guarded
   - GUARDED (1): Visible to guard
   - GUARD (2): Guard position
   - WALL (3): Wall position
3. **Direction Simulation**:
   - Four directions: up, right, down, left
   - Stop when hitting wall, guard, or grid boundary
4. **Optimization**:
   - Mark visited directions to avoid re-processing
   - Could use BFS/DFS but simpler ray casting works

### Approach Selection
**Chosen Approach:** Matrix Simulation with Status Tracking  
**Why this approach?** 
- Direct simulation is straightforward
- O(m*n + guards*(m+n)) worst case but optimized
- Handles all edge cases
- Clear cell state management

## ⚡ Complexity Analysis
- **Time Complexity:** O(m*n + G*(m+n)) where G is number of guards
  - In worst case, each guard scans entire row and column
  - But constraints limit total cells to 10^5
- **Space Complexity:** O(m*n) for the grid representation

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use integer array to track cell states

Mark guards and walls first

For each guard, ray cast in four directions

Stop when hitting wall, guard, or boundary

Count cells with UNGUARDED state

🔗 Related Problems
200. Number of Islands

130. Surrounded Regions

733. Flood Fill

994. Rotting Oranges

1162. As Far from Land as Possible

1730. Shortest Path to Get Food

1926. Nearest Exit from Entrance in Maze
