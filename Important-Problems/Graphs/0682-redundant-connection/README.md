# 684. Redundant Connection

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Graph, Union Find, Depth-First Search  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/redundant-connection/)

In this problem, a tree is an undirected graph that is connected and has no cycles.

You are given a graph that started as a tree with `n` nodes labeled from `1` to `n`, with one additional edge added. The added edge has two different vertices chosen from `1` to `n`, and was not an edge that already existed.

The graph is represented as an array `edges` of length `n` where `edges[i] = [a_i, b_i]` indicates that there is an edge between nodes `a_i` and `b_i` in the graph.

Return an edge that can be removed so that the resulting graph is a tree of `n` nodes. If there are multiple answers, return the answer that occurs last in the input.

**Example 1:**

Input: edges = [[1,2],[1,3],[2,3]]

Output: [2,3]

Explanation: The given edges form a tree with an extra edge [2,3].

Removing [2,3] restores the tree structure.


**Example 2:**

Input: edges = [[1,2],[2,3],[3,4],[1,4],[1,5]]

Output: [1,4]

Explanation: The given edges form a tree with an extra edge [1,4].

Removing [1,4] restores the tree structure.


**Constraints:**
- `n == edges.length`
- `3 <= n <= 1000`
- `edges[i].length == 2`
- `1 <= a_i < b_i <= n`
- `a_i != b_i`
- There are no repeated edges.
- The given graph is connected.

## 🧠 Thought Process

### Initial Thoughts
- We need to find the edge that creates a cycle in what should be a tree
- A tree has exactly n-1 edges and no cycles
- With n edges, there's exactly one extra edge creating one cycle
- Multiple approaches: Union Find, DFS

### Key Insights
1. **Union Find Approach**: Naturally detects cycles during union operations
2. **DFS Approach**: Can detect cycles during traversal
3. **Last Occurrence**: Problem requires returning the last edge that creates a cycle
4. **Tree Properties**: n nodes, n-1 edges for a tree; we have n edges

### Approach Selection
**Chosen Approach:** Union Find (Disjoint Set Union)  
**Why this approach?** 
- O(n α(n)) time complexity - very efficient
- Naturally detects cycles during union operations
- Simple implementation
- Directly identifies the problematic edge

## ⚡ Complexity Analysis
- **Time Complexity:** O(n α(n)) where α is inverse Ackermann function
- **Space Complexity:** O(n) for parent and rank arrays

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Union Find efficiently detects the first edge that connects already-connected components

The last such edge in the input is the answer

The graph is guaranteed to have exactly one cycle

All approaches should handle the "last occurrence" requirement correctly

🔗 Related Problems
Graph Valid Tree

Number of Connected Components in an Undirected Graph

Redundant Connection II

Number of Operations to Make Network Connected
