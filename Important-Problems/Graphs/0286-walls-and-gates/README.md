# 286. Walls and Gates

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Breadth-First Search, Matrix  
**Companies:** Google, Amazon, Microsoft, Facebook, Bloomberg, Uber

[LeetCode Premium Link](https://leetcode.com/problems/walls-and-gates/)

You are given an `m x n` grid `rooms` initialized with these three possible values:
- `-1` - A wall or an obstacle.
- `0` - A gate.
- `INF` - Infinity means an empty room. We use the value `2^31 - 1 = 2147483647` to represent INF.

Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should remain as `INF`.

**Example 1:**

Input: rooms = [

[2147483647,-1,0,2147483647],

[2147483647,2147483647,2147483647,-1],

[2147483647,-1,2147483647,-1],

[0,-1,2147483647,2147483647]

]

Output: [

[3,-1,0,1],

[2,2,1,-1],

[1,-1,2,-1],

[0,-1,3,4]

]


**Example 2:**

Input: rooms = [[-1]]

Output: [[-1]]


**Constraints:**
- `m == rooms.length`
- `n == rooms[i].length`
- `1 <= m, n <= 250`
- `rooms[i][j]` is `-1`, `0`, or `2^31 - 1`.

## 🧠 Thought Process

### Initial Thoughts
- Need to find shortest distance from each empty room to nearest gate
- Multiple gates, so need multi-source BFS
- Walls block movement, gates are starting points
- BFS naturally finds shortest paths in unweighted grid

### Key Insights
1. **Multi-source BFS**: Start BFS from all gates simultaneously
2. **Distance Propagation**: Update distances as we explore from gates
3. **Optimization**: Only process rooms that can get smaller distances
4. **Direction Handling**: Move in 4 directions (up, down, left, right)

### Approach Selection
**Chosen Approach:** Multi-source BFS  
**Why this approach?** 
- O(m × n) time complexity - visit each cell once
- O(m × n) space complexity for queue in worst case
- Naturally finds shortest paths from multiple sources
- Efficient and intuitive

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n) - Each cell processed at most once
- **Space Complexity:** O(m × n) - Queue size in worst case

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Start BFS from all gates simultaneously

Only update rooms if new distance is smaller

Walls (-1) are not processed

Use direction arrays for 4-way movement

BFS ensures shortest path finding

🔗 Related Problems
Shortest Distance from All Buildings

01 Matrix

Rotting Oranges

Shortest Path in Binary Matrix

As Far from Land as Possible
