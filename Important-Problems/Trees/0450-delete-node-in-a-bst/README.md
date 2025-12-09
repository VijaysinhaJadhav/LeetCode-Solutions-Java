# 450. Delete Node in a BST

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Tree, Binary Search Tree, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/delete-node-in-a-bst/)

Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.

Basically, the deletion can be divided into two stages:
1. Search for a node to remove.
2. If the node is found, delete the node.

**Example 1:**  

Input: root = [5,3,6,2,4,null,7], key = 3

Output: [5,4,6,2,null,null,7]

Explanation:

Given key to delete is 3. So we find the node with value 3 and delete it.

One valid answer is [5,4,6,2,null,null,7].

5             5

/ \ /

3 6 → 4 6

/ \ \ /

2 4 7 2 7


**Example 2:**

Input: root = [5,3,6,2,4,null,7], key = 0

Output: [5,3,6,2,4,null,7]

Explanation: The tree does not contain a node with value 0.


**Example 3:**

Input: root = [], key = 0

Output: []


**Constraints:**
- The number of nodes in the tree is in the range `[0, 10^4]`.
- `-10^5 <= Node.val <= 10^5`
- Each node has a **unique** value.
- `root` is a valid binary search tree.
- `-10^5 <= key <= 10^5`

## 🧠 Thought Process

### Initial Thoughts
- BST deletion is more complex than insertion
- Three cases to handle:
  1. Node to delete has no children (leaf node)
  2. Node to delete has one child
  3. Node to delete has two children
- Need to maintain BST property after deletion

### Key Insights
1. **Case 1 (No children)**: Simply remove the node
2. **Case 2 (One child)**: Replace node with its child
3. **Case 3 (Two children)**: 
   - Find inorder successor (smallest in right subtree) or predecessor (largest in left subtree)
   - Replace node value with successor/predecessor value
   - Recursively delete the successor/predecessor

### Approach Selection
**Recommended Approaches:**
1. **Recursive Approach** - Most intuitive and clean
2. **Iterative Approach** - More efficient, avoids recursion stack
3. **Successor-based Deletion** - Uses inorder successor for two-child case
4. **Predecessor-based Deletion** - Uses inorder predecessor for two-child case

## ⚡ Complexity Analysis

### All Approaches:
- **Time Complexity:** O(h) - Where h is tree height
- **Space Complexity:** 
  - Recursive: O(h) - Recursion stack
  - Iterative: O(1) - Constant space
- **Best case:** O(log n) for balanced BST
- **Worst case:** O(n) for skewed BST

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
BST deletion maintains the binary search tree property

For two-child case, can use either inorder successor or predecessor

The tree structure may change significantly after deletion

If key not found, return original tree unchanged

🔗 Related Problems
Insert into a Binary Search Tree

Search in a Binary Search Tree

Validate Binary Search Tree

Recover Binary Search Tree

Closest Binary Search Tree Value
