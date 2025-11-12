# 323. Number of Connected Components in an Undirected Graph

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Graph, Depth-First Search, Breadth-First Search, Union Find  
**Companies:** Google, Amazon, Facebook, Microsoft, LinkedIn, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/)

You have a graph of `n` nodes. You are given an integer `n` and an array `edges` where `edges[i] = [a_i, b_i]` indicates that there is an undirected edge between nodes `a_i` and `b_i`.

Return the number of connected components in the graph.

**Example 1:**

Input: n = 5, edges = [[0,1],[1,2],[3,4]]

Output: 2

Explanation: There are two connected components: [0,1,2] and [3,4]


**Example 2:**

Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]

Output: 1

Explanation: All nodes are connected forming one component.


**Constraints:**
- `1 <= n <= 2000`
- `1 <= edges.length <= 5000`
- `edges[i].length == 2`
- `0 <= a_i, b_i < n`
- `a_i != b_i`
- There are no repeated edges.

## 🧠 Thought Process

### Initial Thoughts
- Need to count disconnected groups of nodes in an undirected graph
- Multiple standard graph traversal approaches available
- The problem is about finding connected components

### Key Insights
1. **Graph Representation**: Can use adjacency list or union find data structure
2. **Multiple Approaches**:
   - Depth-First Search (DFS)
   - Breadth-First Search (BFS) 
   - Union Find (Disjoint Set Union)
3. **All approaches are valid** with different trade-offs

### Approach Selection
**Chosen Approach:** Union Find (Disjoint Set Union)  
**Why this approach?** 
- O(n) time complexity with path compression and union by rank
- Simple and efficient implementation
- Naturally counts connected components
- Well-suited for connectivity problems

## ⚡ Complexity Analysis
- **Time Complexity:** O(n + m α(n)) where α is inverse Ackermann function (nearly constant)
- **Space Complexity:** O(n) for parent and rank arrays

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Union Find with path compression and union by rank provides optimal performance

DFS/BFS approaches also work well and may be more intuitive

The count starts at n (each node separate) and decreases with each union operation

All approaches should handle isolated nodes correctly

🔗 Related Problems
Number of Provinces

Graph Valid Tree

Redundant Connection

Number of Operations to Make Network Connected
