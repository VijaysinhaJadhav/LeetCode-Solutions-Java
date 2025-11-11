# 133. Clone Graph

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, Depth-First Search, Breadth-First Search, Graph  
**Companies:** Facebook, Google, Amazon, Microsoft, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/clone-graph/)

Given a reference of a node in a **connected undirected graph**.

Return a **deep copy** (clone) of the graph.

Each node in the graph contains a value (`int`) and a list (`List[Node]`) of its neighbors.

class Node {

public int val;

public List<Node> neighbors;

}


**Test case format:**

For simplicity, each node's value is the same as the node's index (1-indexed). For example, the first node with `val == 1`, the second node with `val == 2`, and so on. The graph is represented in the test case using an adjacency list.

**An adjacency list** is a collection of unordered **lists** used to represent a finite graph. Each list describes the set of neighbors of a node in the graph.

The given node will always be the first node with `val = 1`. You must return the **copy of the given node** as a reference to the cloned graph.

**Example 1:**

Input: adjList = [[2,4],[1,3],[2,4],[1,3]]

Output: [[2,4],[1,3],[2,4],[1,3]]

Explanation: There are 4 nodes in the graph.

1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).

2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).

3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).

4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).


**Example 2:**

Input: adjList = [[]]

Output: [[]]

Explanation: Note that the input contains one empty list. The graph has only one node with val = 1 and it has no neighbors.


**Example 3:**

Input: adjList = []

Output: []


**Constraints:**
- The number of nodes in the graph is in the range `[0, 100]`.
- `1 <= Node.val <= 100`
- `Node.val` is unique for each node.
- There are no repeated edges and no self-loops in the graph.
- The graph is connected and undirected.

## 🧠 Thought Process

### Initial Thoughts
- Need to create a deep copy of an undirected graph
- Graph nodes have neighbors that reference other nodes
- Must handle cycles and ensure all nodes are copied exactly once
- Can use DFS or BFS with a mapping from original nodes to cloned nodes

### Key Insights
1. **HashMap Mapping**: Use HashMap to map original nodes to cloned nodes
2. **DFS Approach**: Recursively clone nodes and their neighbors
3. **BFS Approach**: Use queue to process nodes level by level
4. **Cycle Handling**: The mapping prevents infinite recursion on cycles

### Approach Selection
**Chosen Approach:** DFS with HashMap  
**Why this approach?** 
- O(n) time complexity - visit each node once
- O(n) space complexity - for HashMap and recursion stack
- Intuitive recursive implementation
- Naturally handles graph cycles

## ⚡ Complexity Analysis
- **Time Complexity:** O(n + e) where n is number of nodes, e is number of edges
- **Space Complexity:** O(n) for HashMap and recursion stack

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use HashMap to store mapping from original nodes to cloned nodes

If node is already cloned, return the cloned version from HashMap

Recursively clone all neighbors

Handle edge cases: null input, single node, empty graph

🔗 Related Problems
Copy List with Random Pointer

Clone Binary Tree With Random Pointer

Clone N-ary Tree

Construct Quad Tree

Flatten a Multilevel Doubly Linked List


