# 701. Insert into a Binary Search Tree

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Tree, Binary Search Tree, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/insert-into-a-binary-search-tree/)

You are given the `root` node of a binary search tree (BST) and a `value` to insert into the tree. Return the root node of the BST after the insertion. It is **guaranteed** that the new value does not exist in the original BST.

**Notice** that there may exist multiple valid ways for the insertion, as long as the tree remains a BST after insertion. You can return **any of them**.

**Example 1:**  

Input: root = [4,2,7,1,3], val = 5

Output: [4,2,7,1,3,5]

Explanation:

4 4

/ \ /

2 7 → 2 7

/ \ / \ /

1 3 1 3 5


**Example 2:**

Input: root = [40,20,60,10,30,50,70], val = 25

Output: [40,20,60,10,30,50,70,null,null,25]

    40               40
   /  \            /  \
  20   60   →     20   60
 / \  / \        / \  / \
10 30 50 70     10 30 50 70
    /           /
   25          25


**Example 3:**

Input: root = [4,2,7,1,3,null,null,null,null,null,null], val = 5

Output: [4,2,7,1,3,5]


**Constraints:**
- The number of nodes in the tree will be in the range `[0, 10^4]`.
- `-10^8 <= Node.val <= 10^8`
- All the values `Node.val` are **unique**.
- `-10^8 <= val <= 10^8`
- It's **guaranteed** that `val` does not exist in the original BST.

## 🧠 Thought Process

### Initial Thoughts
- Need to insert a new value while maintaining BST properties
- BST property: left subtree < root < right subtree
- Multiple valid insertion positions exist
- Need to handle empty tree case

### Key Insights
1. **Iterative Approach**: Traverse tree to find insertion point
2. **Recursive Approach**: Recursively find insertion position
3. **BST Property**: If val < current, go left; if val > current, go right
4. **Insertion Point**: Always insert as a leaf node in standard approach

### Approach Selection
**Recommended Approaches:**
1. **Iterative Approach** - Most efficient and intuitive
2. **Recursive Approach** - Clean recursive implementation
3. **Balanced Insertion** - Try to maintain balance during insertion

## ⚡ Complexity Analysis

### All Approaches:
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
Multiple valid insertion positions exist

Always insert as leaf node in standard approach

Value is guaranteed to not exist in original BST

Empty tree case: new node becomes root

Can choose left or right when equal (but values are unique)

🔗 Related Problems
Search in a Binary Search Tree

Delete Node in a BST

Validate Binary Search Tree

Recover Binary Search Tree
