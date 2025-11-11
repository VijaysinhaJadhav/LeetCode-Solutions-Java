# 417. Pacific Atlantic Water Flow

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Depth-First Search, Breadth-First Search, Matrix  
**Companies:** Google, Amazon, Microsoft, Facebook, Bloomberg, Apple

[LeetCode Link](https://leetcode.com/problems/pacific-atlantic-water-flow/)

There is an `m x n` rectangular island that borders both the **Pacific Ocean** and **Atlantic Ocean**. The Pacific Ocean touches the island's left and top edges, and the Atlantic Ocean touches the island's right and bottom edges.

The island is partitioned into a grid of square cells. You are given an `m x n` integer matrix `heights` where `heights[r][c]` represents the **height above sea level** of the cell at coordinate `(r, c)`.

The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south, east, and west if the neighboring cell's height is **less than or equal to** the current cell's height. Water can flow from any cell adjacent to an ocean into the ocean.

Return *a 2D list of grid coordinates* `result` *where* `result[i] = [ri, ci]` *denotes that rain water can flow from cell* `(ri, ci)` *to both the Pacific and Atlantic oceans*.

**Example 1:**

Input: heights = [

[1,2,2,3,5],

[3,2,3,4,4],

[2,4,5,3,1],

[6,7,1,4,5],

[5,1,1,2,4]

]

Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]

Explanation: The following cells can flow to both Pacific and Atlantic oceans.


**Example 2:**

Input: heights = [[1]]

Output: [[0,0]]

Explanation: Water can flow from the only cell to both oceans.


**Constraints:**
- `m == heights.length`
- `n == heights[i].length`
- `1 <= m, n <= 200`
- `0 <= heights[i][j] <= 10^5`

## 🧠 Thought Process

### Initial Thoughts
- Water flows from higher to lower (or equal) elevations
- Need to find cells that can reach both Pacific (left/top) and Atlantic (right/bottom)
- Can use DFS/BFS starting from ocean edges and work backwards
- Two separate traversals: one from Pacific, one from Atlantic

### Key Insights
1. **Reverse Flow**: Instead of checking each cell, start from oceans and flow uphill
2. **Two Separate Searches**: One for Pacific-reachable, one for Atlantic-reachable
3. **Intersection**: Cells reachable from both oceans are the answer
4. **DFS/BFS**: Both work, DFS is more intuitive for this problem

### Approach Selection
**Chosen Approach:** DFS from Ocean Edges  
**Why this approach?** 
- O(m × n) time complexity - visit each cell twice
- O(m × n) space complexity for visited sets
- Intuitive reverse flow approach
- Efficient and handles constraints well

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n) - Each cell processed for both oceans
- **Space Complexity:** O(m × n) - For visited matrices and recursion stack

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Start DFS from Pacific edges (left and top) and mark reachable cells

Start DFS from Atlantic edges (right and bottom) and mark reachable cells

Find intersection of both reachable sets

Water flows uphill in reverse (from ocean to higher elevations)

🔗 Related Problems
Surrounded Regions

Number of Islands

Walls and Gates

01 Matrix

Max Area of Island
