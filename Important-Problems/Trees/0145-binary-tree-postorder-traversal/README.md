# 145. Binary Tree Postorder Traversal

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Stack, Tree, Depth-First Search, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/binary-tree-postorder-traversal/)

Given the `root` of a binary tree, return the *postorder traversal* of its nodes' values.

**Example 1:**  

Input: root = [1,null,2,3]

Output: [3,2,1]

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
- Postorder traversal: left subtree → right subtree → root
- Most challenging of the three main traversals to implement iteratively
- Multiple approaches: recursive, iterative (two stacks), iterative (one stack), Morris traversal

### Key Insights
1. **Recursive Approach**: Simple and intuitive, follows definition directly
2. **Iterative with Two Stacks**: Reverse preorder approach
3. **Iterative with One Stack**: Track last visited node to determine when to process
4. **Morris Traversal**: O(1) space complexity, most complex to implement

### Approach Selection
**Recommended Approaches:**
1. **Iterative with Two Stacks** - Most intuitive iterative approach
2. **Recursive** - Simplest to implement
3. **Iterative with One Stack** - More space efficient
4. **Morris Traversal** - For follow-up O(1) space requirement

## ⚡ Complexity Analysis

### Recursive Approach:
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(h) - Recursion stack, where h is tree height

### Iterative Two Stacks:
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(n) - Two stacks storage

### Iterative One Stack:
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(h) - Stack size, where h is tree height

### Morris Traversal:
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(1) - No extra space (except output)

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
Postorder traversal is useful for deleting trees and expression evaluation

Iterative approaches are more challenging than preorder/inorder

Two-stack approach is easiest to understand and implement

Morris traversal is most complex but demonstrates deep understanding

🔗 Related Problems
Binary Tree Inorder Traversal

Binary Tree Preorder Traversal

Binary Tree Level Order Traversal

N-ary Tree Postorder Traversal

Construct Binary Tree from Inorder and Postorder Traversal
