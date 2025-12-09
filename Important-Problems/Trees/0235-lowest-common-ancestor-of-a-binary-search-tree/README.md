# 235. Lowest Common Ancestor of a Binary Search Tree

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Tree, Depth-First Search, Binary Search Tree, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/)

Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

According to the definition of LCA on Wikipedia: "The lowest common ancestor is defined between two nodes `p` and `q` as the lowest node in T that has both `p` and `q` as descendants (where we allow **a node to be a descendant of itself**)."

**Example 1:**  

Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8

Output: 6

Explanation: The LCA of nodes 2 and 8 is 6.

    6
   / \
  2   8
 / \ / \
0  4 7  9
  / \
 3   5


**Example 2:**

Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4

Output: 2

Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself.


**Example 3:**

Input: root = [2,1], p = 2, q = 1

Output: 2


**Constraints:**
- The number of nodes in the tree is in the range `[2, 10^5]`.
- `-10^9 <= Node.val <= 10^9`
- All `Node.val` are **unique**.
- `p != q`
- `p` and `q` will exist in the BST.

## 🧠 Thought Process

### Initial Thoughts
- Binary Search Tree property: left subtree < root < right subtree
- LCA is the node where p and q split into different subtrees
- Multiple approaches: recursive, iterative, using BST properties
- Can leverage BST ordering to find LCA efficiently

### Key Insights
1. **BST Property**: If p and q are both less than current node, LCA is in left subtree
2. **BST Property**: If p and q are both greater than current node, LCA is in right subtree  
3. **LCA Found**: If one node is less and other is greater, current node is LCA
4. **Self Descendant**: A node is considered a descendant of itself

### Approach Selection
**Recommended Approaches:**
1. **Iterative BST Traversal** - Most efficient and intuitive
2. **Recursive BST Traversal** - Clean recursive version
3. **General Tree LCA** - Works for any binary tree (less efficient)

## ⚡ Complexity Analysis

### BST Approaches:
- **Time Complexity:** O(h) - Where h is tree height
- **Space Complexity:** 
  - Iterative: O(1) - Constant space
  - Recursive: O(h) - Recursion stack
- **Best case:** O(log n) for balanced BST
- **Worst case:** O(n) for skewed BST

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
Leverage BST ordering property for efficient search

LCA is where p and q diverge into different subtrees

A node is considered its own descendant

All node values are unique and p, q guaranteed to exist

🔗 Related Problems
Lowest Common Ancestor of a Binary Tree

Lowest Common Ancestor of a Binary Tree III

Lowest Common Ancestor of a Binary Tree II

Lowest Common Ancestor of a Binary Tree IV
