# 261. Graph Valid Tree

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Depth-First Search, Breadth-First Search, Union Find, Graph  
**Companies:** Google, Amazon, Microsoft, Facebook, LinkedIn, Apple

[LeetCode Link](https://leetcode.com/problems/graph-valid-tree/)

You have a graph of `n` nodes labeled from `0` to `n - 1`. You are given an integer `n` and a list of `edges` where `edges[i] = [ai, bi]` indicates that there is an undirected edge between nodes `ai` and `bi`.

Return `true` if the edges of the given graph make up a valid tree, and `false` otherwise.

**Example 1:**

Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]

Output: true


**Example 2:**

Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]

Output: false


**Constraints:**
- `1 <= n <= 2000`
- `0 <= edges.length <= 5000`
- `edges[i].length == 2`
- `0 <= ai, bi < n`
- `ai != bi`
- There are no self-loops or repeated edges.

## 🧠 Thought Process

### Initial Thoughts
- A valid tree must satisfy two conditions:
  1. The graph is connected (all nodes reachable from any node)
  2. The graph has exactly n-1 edges (no cycles)
- Multiple approaches: Union Find, DFS, BFS

### Key Insights
1. **Tree Properties**: For n nodes, a tree must have exactly n-1 edges and be connected
2. **Cycle Detection**: If edges >= n, cycle must exist; if edges < n-1, graph is disconnected
3. **Union Find**: Efficient for cycle detection and connectivity check
4. **DFS/BFS**: Can detect cycles and check connectivity

### Approach Selection
**Chosen Approach:** Union Find  
**Why this approach?** 
- O(n α(n)) time complexity (almost linear)
- O(n) space complexity
- Efficient for dynamic connectivity
- Naturally handles cycle detection

## ⚡ Complexity Analysis
- **Time Complexity:** O(n α(n)) where α is the inverse Ackermann function (almost constant)
- **Space Complexity:** O(n) for Union Find data structures

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
A valid tree must have exactly n-1 edges

The graph must be connected (all nodes in one component)

Union Find efficiently checks both conditions

DFS/BFS can also solve but may be less efficient for large graphs

🔗 Related Problems
Number of Connected Components in an Undirected Graph

Number of Provinces

Redundant Connection

Redundant Connection II

All Paths From Source to Target
