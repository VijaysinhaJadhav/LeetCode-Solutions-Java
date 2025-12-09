# 124. Binary Tree Maximum Path Sum

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Dynamic Programming, Tree, Depth-First Search, Binary Tree  
**Companies:** Amazon, Microsoft, Facebook, Google, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/binary-tree-maximum-path-sum/)

A **path** in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence **at most once**. Note that the path does not need to pass through the root.

The **path sum** of a path is the sum of the node's values in the path.

Given the `root` of a binary tree, return _the maximum **path sum** of any **non-empty** path_.

**Example 1:**

![Example 1](https://assets.leetcode.com/uploads/2020/10/13/exx1.jpg)

Input: root = [1,2,3]

Output: 6

Explanation: The optimal path is 2 → 1 → 3 with a path sum of 2 + 1 + 3 = 6.


**Example 2:**

![Example 2](https://assets.leetcode.com/uploads/2020/10/13/exx2.jpg)

Input: root = [-10,9,20,null,null,15,7]

Output: 42

Explanation: The optimal path is 15 → 20 → 7 with a path sum of 15 + 20 + 7 = 42.


**Constraints:**
- The number of nodes in the tree is in the range `[1, 3 * 10^4]`.
- `-1000 <= Node.val <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Need to find the maximum path sum where a path can start and end anywhere in the tree
- A path can go through any node and can include left and right children
- For each node, we need to consider:
  - Path through the node connecting left and right subtrees
  - Path ending at the node (using either left or right subtree)

### Key Insights
1. **Post-order DFS**: Process children before parent to compute subtree contributions
2. **Node Contribution**: For each node, maximum contribution to parent is `node.val + max(left, right)`
3. **Global Maximum**: Track global maximum that considers path through node: `node.val + left + right`
4. **Negative Values**: Handle negative contributions by comparing with 0

### Approach Selection
**Chosen Approach:** Post-order DFS with Global Maximum  
**Why this approach?** 
- O(n) time complexity
- O(h) space complexity (recursion stack)
- Elegant recursive solution
- Handles all cases including negative values

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(h) - Recursion stack height, where h is tree height
  - Worst case: O(n) for skewed tree
  - Best case: O(log n) for balanced tree

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
A path can be: left subtree → node → right subtree (arching path)

Or: node → parent (linear path for parent's calculation)

Always compare with 0 to handle negative contributions

Update global maximum at each node considering arching path

🔗 Related Problems
Path Sum

Path Sum II

Path Sum III

Diameter of Binary Tree

Longest Univalue Path

