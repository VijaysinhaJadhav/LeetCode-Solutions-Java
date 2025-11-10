# 226. Invert Binary Tree

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Tree, Depth-First Search, Breadth-First Search, Binary Tree  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/invert-binary-tree/)

Given the `root` of a binary tree, invert the tree, and return its root.

**Example 1:**  

Input: root = [4,2,7,1,3,6,9]

Output: [4,7,2,9,6,3,1]

Explanation:

Original Tree: Inverted Tree:

4 4

/ \ /

2 7 7 2

/ \ / \ / \ /

1 3 6 9 9 6 3 1


**Example 2:**

Input: root = [2,1,3]

Output: [2,3,1]


**Example 3:**

Input: root = []

Output: []


**Constraints:**
- The number of nodes in the tree is in the range `[0, 100]`.
- `-100 <= Node.val <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Invert means swap left and right children for every node
- Multiple approaches: recursive DFS, iterative DFS, BFS
- Need to handle edge cases: empty tree, single node

### Key Insights
1. **Recursive DFS**: Simple and elegant, follows divide and conquer
2. **Iterative DFS**: Uses stack to simulate recursion
3. **BFS (Level Order)**: Uses queue, processes level by level
4. **All approaches**: O(n) time complexity, differ in space usage

### Approach Selection
**Recommended Approaches:**
1. **Recursive DFS** - Most intuitive and concise
2. **Iterative BFS** - Easy to understand and visualize
3. **Iterative DFS** - Alternative iterative approach

## ⚡ Complexity Analysis

### All Approaches:
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(h) - Where h is tree height
  - Recursive: O(h) for call stack
  - Iterative: O(h) for stack/queue
  - Worst case: O(n) for skewed tree
  - Best case: O(log n) for balanced tree

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
Inverting a binary tree is a fundamental tree operation

The problem is famously associated with Google interviews

All approaches are valid and have similar time complexity

Choice depends on specific constraints and preferences

🔗 Related Problems
Same Tree

Symmetric Tree

Maximum Depth of Binary Tree

Balanced Binary Tree

Minimum Depth of Binary Tree
