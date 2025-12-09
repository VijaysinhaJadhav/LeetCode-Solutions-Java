# 144. Binary Tree Preorder Traversal

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Stack, Tree, Depth-First Search, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/binary-tree-preorder-traversal/)

Given the `root` of a binary tree, return the *preorder traversal* of its nodes' values.

**Example 1:**  

Input: root = [1,null,2,3]

Output: [1,2,3]

Explanation:

1

2
/
3


**Example 2:**

Input: root = []

Output: []


**Example 3:**

Input: root = [1]

Output: [1]


**Constraints:**
- The number of nodes in the tree is in the range `[0, 100]`.
- `-100 <= Node.val <= 100`

**Follow up:** Recursive solution is trivial, could you do it iteratively?

## 🧠 Thought Process

### Initial Thoughts
- Preorder traversal: root → left subtree → right subtree
- Multiple approaches: recursive, iterative, Morris traversal
- Need to handle edge cases: empty tree, single node

### Key Insights
1. **Recursive Approach**: Simple and intuitive, follows definition directly
2. **Iterative Approach**: Uses stack to simulate recursion, avoids stack overflow
3. **Morris Traversal**: O(1) space complexity, modifies tree temporarily
4. **Preorder Order**: Process root first, then left child, then right child

### Approach Selection
**Recommended Approaches:**
1. **Iterative with Stack** - Most practical for interviews
2. **Recursive** - Simplest to implement
3. **Morris Traversal** - For follow-up O(1) space requirement

## ⚡ Complexity Analysis

### Recursive Approach:
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(h) - Recursion stack, where h is tree height

### Iterative Approach:
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(h) - Stack size, where h is tree height

### Morris Traversal:
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(1) - No extra space (except output)

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
Preorder traversal is useful for copying trees and serialization

Iterative approach is preferred in interviews to avoid stack overflow concerns

Morris traversal is advanced but demonstrates deep understanding

All approaches have O(n) time complexity, differ in space usage

🔗 Related Problems
Binary Tree Inorder Traversal

Binary Tree Postorder Traversal

Binary Tree Level Order Traversal

N-ary Tree Preorder Traversal

Construct Binary Tree from Preorder and Inorder Traversal
