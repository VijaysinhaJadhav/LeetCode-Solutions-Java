# 743. Network Delay Time

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Graph, Heap (Priority Queue), Depth-First Search, Breadth-First Search, Shortest Path  
**Companies:** Amazon, Google, Microsoft, Facebook, Uber, Apple  

[LeetCode Link](https://leetcode.com/problems/network-delay-time/)

You are given a network of `n` nodes, labeled from `1` to `n`. You are also given `times`, a list of travel times as directed edges `times[i] = (uᵢ, vᵢ, wᵢ)`, where:
- `uᵢ` is the source node
- `vᵢ` is the target node
- `wᵢ` is the time it takes for a signal to travel from source to target

We send a signal from a given node `k`. Return the **minimum time** it takes for all `n` nodes to receive the signal. If it is impossible for all nodes to receive the signal, return `-1`.

**Example 1:**

![Example 1](https://assets.leetcode.com/uploads/2019/05/23/931_example_1.png)

Input: times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
Output: 2
Explanation: The signal starts at node 2.

At time 1, it reaches node 1 and node 3.

At time 2, it reaches node 4.
All nodes receive the signal by time 2.


**Example 2:**

Input: times = [[1,2,1]], n = 2, k = 1
Output: 1
Explanation: The signal starts at node 1 and reaches node 2 at time 1.


**Example 3:**

Input: times = [[1,2,1]], n = 2, k = 2
Output: -1
Explanation: The signal starts at node 2, but it cannot reach node 1.


**Constraints:**
- `1 <= k <= n <= 100`
- `1 <= times.length <= 6000`
- `times[i].length == 3`
- `1 <= uᵢ, vᵢ <= n`
- `uᵢ != vᵢ`
- `0 <= wᵢ <= 100`
- All the pairs `(uᵢ, vᵢ)` are unique (i.e., no multiple edges)

## 🧠 Thought Process

### Problem Understanding
We need to find the shortest time for a signal to reach all nodes in a weighted directed graph from a starting node `k`. If some node cannot be reached, return -1.

### Key Insights
1. **Shortest Path Problem**: This is a classic single-source shortest path problem
2. **Graph Algorithms**: Multiple algorithms can solve this (Dijkstra, Bellman-Ford, Floyd-Warshall)
3. **Network Connectivity**: Need to check if all nodes are reachable from source
4. **Answer Calculation**: The answer is the maximum shortest distance from source to any node

### Approach Selection
**Chosen Approach:** Dijkstra's Algorithm  
**Why this approach?**
- Efficient O(E log V) for sparse graphs
- Handles non-negative weights (constraints guarantee wᵢ >= 0)
- Greedy approach ensures correctness

**Alternative Approaches:**
- **Bellman-Ford**: O(V·E), handles negative weights (not needed here)
- **Floyd-Warshall**: O(V³), overkill for single source
- **BFS**: Only works for unweighted graphs

## ⚡ Complexity Analysis
- **Time Complexity:** O(E log V) where V = n, E = times.length
- **Space Complexity:** O(V + E) for adjacency list and distance array

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Convert node labels from 1-based to 0-based for array indexing

Build adjacency list for efficient graph traversal

Use PriorityQueue (min-heap) for Dijkstra's algorithm

Track distances to all nodes, return max if all reachable, else -1

🔗 Related Problems
Network Delay Time (this problem)
Cheapest Flights Within K Stops
Find the City With the Smallest Number of Neighbors
Path with Maximum Probability
Path With Minimum Effort
