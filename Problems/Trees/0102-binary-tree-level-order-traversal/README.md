# 102. Binary Tree Level Order Traversal

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Tree, Breadth-First Search, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/binary-tree-level-order-traversal/)

Given the `root` of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).

**Example 1:**  

Input: root = [3,9,20,null,null,15,7]

Output: [[3],[9,20],[15,7]]

Explanation:

3

/

9 20

/

15 7

Level 0: [3]

Level 1: [9, 20]

Level 2: [15, 7]

**Example 2:**

Input: root = [1]

Output: [[1]]

**Example 3:**

Input: root = []

Output: []


**Constraints:**
- The number of nodes in the tree is in the range `[0, 2000]`.
- `-1000 <= Node.val <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Level order traversal = Breadth-First Search (BFS)
- Process nodes level by level from top to bottom
- Need to track which level each node belongs to
- Multiple approaches: BFS with queue, DFS with level tracking

### Key Insights
1. **BFS with Queue**: Natural approach for level-order traversal
2. **Level Tracking**: Use queue size to determine level boundaries
3. **DFS with Level**: Alternative approach using depth-first search with level tracking
4. **Multiple Implementations**: Different ways to handle level separation

### Approach Selection
**Recommended Approaches:**
1. **BFS with Queue** - Most intuitive and efficient
2. **DFS with Level Tracking** - Alternative recursive approach
3. **Two-Queue BFS** - Explicit level separation
4. **Single Queue with Sentinel** - Using markers for level separation

## ⚡ Complexity Analysis

### BFS Approach:
- **Time Complexity:** O(n) - Each node visited once
- **Space Complexity:** O(w) - Where w is maximum level width
  - Worst case: O(n) for complete binary tree
  - Best case: O(1) for empty tree

### DFS Approach:
- **Time Complexity:** O(n) - Each node visited once
- **Space Complexity:** O(h) - Recursion stack, where h is tree height

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
Level order traversal is fundamental for tree problems

BFS naturally processes nodes level by level

Can be extended to zigzag traversal, bottom-up traversal, etc.

Empty tree returns empty list

🔗 Related Problems
Binary Tree Level Order Traversal II

Binary Tree Zigzag Level Order Traversal

Binary Tree Right Side View

Average of Levels in Binary Tree

N-ary Tree Level Order Traversal


