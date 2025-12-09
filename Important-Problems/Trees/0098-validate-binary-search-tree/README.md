# 98. Validate Binary Search Tree

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Tree, Depth-First Search, Binary Search Tree, Binary Tree  
**Companies:** Amazon, Microsoft, Google, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/validate-binary-search-tree/)

Given the `root` of a binary tree, *determine if it is a valid binary search tree (BST)*.

A **valid BST** is defined as follows:
- The left subtree of a node contains only nodes with keys **less than** the node's key.
- The right subtree of a node contains only nodes with keys **greater than** the node's key.
- Both the left and right subtrees must also be binary search trees.

**Example 1:**

Input: root = [2,1,3]

Output: true

Explanation:

2

/

1 3

The tree satisfies all BST properties.


**Example 2:**

Input: root = [5,1,4,null,null,3,6]

Output: false

Explanation:

5

/

1 4

/

3 6

The root node's value is 5 but its right child's value is 4, which violates BST property.


**Constraints:**
- The number of nodes in the tree is in the range `[1, 10^4]`.
- `-2^31 <= Node.val <= 2^31 - 1`

## 🧠 Thought Process

### Initial Thoughts
- Need to verify BST properties for every node
- Key insight: In a BST, inorder traversal should yield sorted values
- Each node must have a value within a specific range based on its ancestors

### Key Insights
1. **Range-based Approach**: Track valid range (min, max) for each node
2. **Inorder Traversal**: BST should produce sorted array in inorder traversal
3. **Recursive Validation**: Validate left and right subtrees recursively with updated bounds
4. **Integer Overflow**: Handle edge cases with Integer.MIN_VALUE and Integer.MAX_VALUE

### Approach Selection
**Chosen Approach:** DFS with Range Validation  
**Why this approach?** 
- O(n) time complexity - visits each node once
- O(h) space complexity - recursion stack height
- Intuitive and directly implements BST definition
- Handles edge cases effectively

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each node is visited exactly once
- **Space Complexity:** O(h) - Where h is the height of the tree (recursion stack)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
A common mistake is only checking immediate children instead of entire subtree

Use Long instead of Integer to handle edge cases with Integer.MIN_VALUE and Integer.MAX_VALUE

Inorder traversal should produce strictly increasing sequence (no duplicates in BST definition)

The range-based approach naturally enforces BST properties

🔗 Related Problems
Binary Tree Inorder Traversal

Same Tree

Symmetric Tree

Convert Sorted Array to Binary Search Tree

Kth Smallest Element in a BST

Search in a Binary Search Tree
