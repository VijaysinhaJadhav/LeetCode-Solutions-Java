# 2097. Valid Arrangement of Pairs

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Graph, Eulerian Path, Hierholzer's Algorithm, Depth-First Search  
**Companies:** Google, Amazon, Microsoft, Uber, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/valid-arrangement-of-pairs/)

You are given a 0-indexed 2D integer array `pairs` where `pairs[i] = [start_i, end_i]`. A valid arrangement of pairs is an arrangement where for every index `i` where `1 <= i < pairs.length`, we have `end_{i-1} == start_i`.

Return **any valid arrangement** of the pairs.

**Note:** The inputs are generated such that there exists a valid arrangement of pairs.

**Example 1:**

Input: pairs = [[5,1],[4,5],[11,9],[9,4]]
Output: [[11,9],[9,4],[4,5],[5,1]]
Explanation: This is a valid arrangement since end_{i-1} equals start_i for all i.


**Example 2:**

Input: pairs = [[1,3],[3,2],[2,1]]
Output: [[1,3],[3,2],[2,1]]
Explanation: This is a valid arrangement.


**Example 3:**

Input: pairs = [[1,2],[1,3],[2,1]]
Output: [[1,2],[2,1],[1,3]]
Explanation: This is a valid arrangement.


**Constraints:**
- `1 <= pairs.length <= 10^5`
- `pairs[i].length == 2`
- `0 <= start_i, end_i <= 10^9`
- The input is generated such that a valid arrangement exists.

## 🧠 Thought Process

### Problem Understanding
We need to arrange the pairs in a sequence where the end of one pair matches the start of the next pair. This is essentially finding an **Eulerian path** in a directed graph where:
- Each pair `[start, end]` is a directed edge
- We need to use every edge exactly once

### Key Insights
1. **Graph Representation**: Each number is a node, each pair is a directed edge
2. **Eulerian Path Conditions**: 
   - At most one node has `out-degree = in-degree + 1` (start node)
   - At most one node has `in-degree = out-degree + 1` (end node)
   - All other nodes have equal in-degree and out-degree
   - All edges belong to a single connected component
3. **Hierholzer's Algorithm**: Standard algorithm to find Eulerian path
4. **Path Reconstruction**: Build path using DFS, adding nodes post-order

### Approach Selection
**Chosen Approach:** Hierholzer's Algorithm (Eulerian Path)  
**Why this approach?**
- O(E) time complexity
- O(V + E) space complexity
- Guarantees a valid arrangement
- Handles large constraints (10^5 edges)

**Alternative Approaches:**
- **Backtracking DFS**: Exponential, too slow
- **Cycle Detection + Merge**: More complex

## ⚡ Complexity Analysis
- **Time Complexity:** O(E) where E = number of pairs
- **Space Complexity:** O(V + E) for graph representation

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Build adjacency list with Map<Integer, List<Integer>>

Track in-degree and out-degree for each node

Find start node (out-degree > in-degree, or any node if all equal)

Use recursion or iterative stack for DFS

Build path in reverse (post-order)

🔗 Related Problems
Reconstruct Itinerary (similar Eulerian path problem)
Cracking the Safe (Eulerian path on de Bruijn graph)
Valid Arrangement of Pairs (this problem)
