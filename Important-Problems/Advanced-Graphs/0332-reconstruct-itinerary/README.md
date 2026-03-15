# 332. Reconstruct Itinerary

## 📋 Problem Statement
**Difficulty:** Medium (often considered Hard)  
**Topics:** Graph, Depth-First Search, Eulerian Path, Backtracking  
**Companies:** Uber, Facebook, Amazon, Google, Twilio, DoorDash  

[LeetCode Link](https://leetcode.com/problems/reconstruct-itinerary/)

You are given a list of airline `tickets` where `tickets[i] = [from_i, to_i]` represent the departure and arrival airports of one flight. Reconstruct the itinerary in order and return it.

All of the tickets belong to a man who departs from `"JFK"`. Thus, the itinerary must begin with `"JFK"`. If there are multiple valid itineraries, you should return the itinerary that has the smallest **lexical order** when read as a single string.

- For example, the itinerary `["JFK", "LGA"]` has a smaller lexical order than `["JFK", "LGB"]`.

You may assume all tickets form at least one valid itinerary. You must use all the tickets **once and only once**.

**Example 1:**

![Example 1](https://assets.leetcode.com/uploads/2021/03/14/itinerary1-graph.jpg)

Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
Output: ["JFK","MUC","LHR","SFO","SJC"]


**Example 2:**

![Example 2](https://assets.leetcode.com/uploads/2021/03/14/itinerary2-graph.jpg)

Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical order.


**Constraints:**
- `1 <= tickets.length <= 300`
- `tickets[i].length == 2`
- `from_i.length == 3`
- `to_i.length == 3`
- `from_i` and `to_i` consist of uppercase English letters.
- `from_i != to_i`

## 🧠 Thought Process

### Problem Understanding
We have a list of flight tickets (directed edges) between airports (nodes). We need to find a path that:
1. Starts at `"JFK"`
2. Uses **every ticket exactly once**
3. Returns the **lexicographically smallest** valid path

### Key Insights
1. **Eulerian Path**: This is a classic Eulerian path problem—a path that uses every edge exactly once in a directed graph [citation:1][citation:3]
2. **Semi-Eulerian Graph**: For a directed graph to have an Eulerian path:
   - At most one node has `out-degree = in-degree + 1` (start node)
   - At most one node has `in-degree = out-degree + 1` (end node)
   - All other nodes have equal in-degree and out-degree
   - All nodes with non-zero degree belong to the same strongly connected component
3. **Hierholzer's Algorithm**: The standard algorithm for finding an Eulerian path [citation:3]
4. **Lexicographic Order**: Sort destinations to ensure the smallest lexical order when multiple choices exist

### Approach Selection
**Chosen Approach:** Hierholzer's Algorithm (Post-order DFS)  
**Why this approach?**
- Guarantees using every edge exactly once
- Optimal `O(E log E)` time complexity [citation:3]
- Elegant and concise implementation

**Alternative Approaches:**
- **Backtracking DFS**: Try all paths in lexical order (slower, `O(E²)` worst case)
- **Iterative Stack**: Non-recursive version of Hierholzer's algorithm

## ⚡ Complexity Analysis
- **Time Complexity:** `O(E log E)` where E is the number of tickets (edges)
  - Sorting destinations per node takes `O(E log E)`
  - DFS traversal visits each edge exactly once `O(E)`
- **Space Complexity:** `O(V + E)` for adjacency list and recursion stack

## 🔍 Solution Code

```java

// See Solution.java for the complete implementation
📝 Notes
The graph is guaranteed to have at least one valid Eulerian path

We must use all tickets exactly once

Lexicographic order requires sorting destinations, but careful—we need to process in reverse order when using a stack/pop approach

This is considered a challenging problem because it combines graph theory with lexical ordering constraints 

🔗 Related Problems
Cracking the Safe (Eulerian path on de Bruijn graph)
Valid Arrangement of Pairs (similar Eulerian path problem)
Flower Planting With No Adjacent
All Paths From Source to Target
