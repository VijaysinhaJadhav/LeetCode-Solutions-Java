# 543. Diameter of Binary Tree

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Tree, Depth-First Search, Binary Tree  
**Companies:** Amazon, Google, Facebook, Microsoft, Bloomberg, Apple, Uber

[LeetCode Link](https://leetcode.com/problems/diameter-of-binary-tree/)

Given the `root` of a binary tree, return the length of the **diameter** of the tree.

The **diameter** of a binary tree is the **length** of the longest path between any two nodes in a tree. This path may or may not pass through the root.

The **length** of a path between two nodes is represented by the number of edges between them.

**Example 1:**  

Input: root = [1,2,3,4,5]

Output: 3

Explanation: 3 is the length of the path [4,2,1,3] or [5,2,1,3].

  1

  / \

 2   3

/ \

4 5


**Example 2:**

Input: root = [1,2]

Output: 1


**Constraints:**
- The number of nodes in the tree is in the range `[1, 10^4]`.
- `-100 <= Node.val <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Diameter = longest path between any two nodes in the tree
- Path may or may not pass through the root
- Length is measured in number of edges (not nodes)
- Need to consider paths that go through each node

### Key Insights
1. **For any node**: Longest path through that node = left height + right height
2. **Global maximum**: Track maximum diameter encountered during traversal
3. **DFS approach**: Calculate height of each node and update diameter
4. **Post-order traversal**: Process children before parent to get heights

### Approach Selection
**Recommended Approach:** DFS with Height Calculation
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(h) - Recursion stack, where h is tree height

## ⚡ Complexity Analysis

### DFS Approach:
- **Time Complexity:** O(n) - Each node is visited once
- **Space Complexity:** O(h) - Recursion stack, where h is tree height
  - Worst case: O(n) for skewed tree
  - Best case: O(log n) for balanced tree

## 🔍 Solution Code

```java
// See Solution.java for complete implementation

📝 Notes
Diameter is the number of edges in the longest path

Path doesn't necessarily go through the root

Key insight: diameter through a node = left height + right height

Use post-order traversal to calculate heights bottom-up

🔗 Related Problems
Binary Tree Maximum Path Sum

Longest Univalue Path

Binary Tree Longest Consecutive Sequence

Maximum Depth of Binary Tree

Balanced Binary Tree
