# 105. Construct Binary Tree from Preorder and Inorder Traversal

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Divide and Conquer, Tree, Binary Tree  
**Companies:** Amazon, Microsoft, Google, Facebook, Bloomberg, Uber, Apple, Adobe

[LeetCode Link](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)

Given two integer arrays `preorder` and `inorder` where `preorder` is the preorder traversal of a binary tree and `inorder` is the inorder traversal of the same tree, construct and return *the binary tree*.

**Example 1:**

Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]

Output: [3,9,20,null,null,15,7]

Explanation:

3

/

9 20

/

15 7


**Example 2:**

Input: preorder = [-1], inorder = [-1]

Output: [-1]


**Constraints:**
- `1 <= preorder.length <= 3000`
- `inorder.length == preorder.length`
- `-3000 <= preorder[i], inorder[i] <= 3000`
- `preorder` and `inorder` consist of **unique** values.
- Each value of `inorder` also appears in `preorder`.
- `preorder` is **guaranteed** to be the preorder traversal of the tree.
- `inorder` is **guaranteed** to be the inorder traversal of the tree.

## 🧠 Thought Process

### Initial Thoughts
- Preorder: Root → Left → Right
- Inorder: Left → Root → Right
- The first element in preorder is always the root
- In inorder, elements left of root form left subtree, right of root form right subtree
- Can use divide and conquer approach recursively

### Key Insights
1. **Root Identification**: First element in preorder is the root
2. **Partitioning**: Find root in inorder to split into left and right subtrees
3. **Recursive Construction**: Build left and right subtrees recursively
4. **HashMap Optimization**: Use HashMap for O(1) inorder index lookups

### Approach Selection
**Chosen Approach:** Recursive with HashMap  
**Why this approach?** 
- O(n) time complexity - each node processed once
- O(n) space complexity - HashMap storage and recursion stack
- Clean divide and conquer implementation
- Handles all edge cases effectively

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each node is processed exactly once
- **Space Complexity:** O(n) - HashMap O(n) + recursion stack O(h) ≈ O(n)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Preorder traversal gives us the roots of subtrees

Inorder traversal helps us determine the size of left and right subtrees

Use HashMap to avoid linear searches in inorder array

Handle base cases: empty arrays or single element arrays

The solution leverages the unique values constraint

🔗 Related Problems
Construct Binary Tree from Inorder and Postorder Traversal

Construct Binary Tree from Preorder and Postorder Traversal

Construct Binary Search Tree from Preorder Traversal

Binary Tree Preorder Traversal

Binary Tree Inorder Traversal
