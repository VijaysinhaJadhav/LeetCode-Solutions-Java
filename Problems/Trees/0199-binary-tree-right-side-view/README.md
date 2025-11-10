# 199. Binary Tree Right Side View

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Tree, Depth-First Search, Breadth-First Search, Binary Tree  
**Companies:** Amazon, Google, Facebook, Microsoft, Bloomberg, Apple, Uber

[LeetCode Link](https://leetcode.com/problems/binary-tree-right-side-view/)

Given the `root` of a binary tree, imagine yourself standing on the **right side** of it, return the values of the nodes you can see ordered from top to bottom.

**Example 1:**  

Input: root = [1,2,3,null,5,null,4]

Output: [1,3,4]

Explanation:

1 <--- (right side: 1)

/

2 3 <--- (right side: 3)

\

5 4 <--- (right side: 4)


**Example 2:**

Input: root = [1,null,3]

Output: [1,3]


**Example 3:**

Input: root = []

Output: []


**Constraints:**
- The number of nodes in the tree is in the range `[0, 100]`.
- `-100 <= Node.val <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Right side view shows the last node at each level
- Multiple approaches: BFS (level order), DFS (pre-order with right-first)
- Need to track the rightmost node for each level
- Can be solved by modifying level order traversal

### Key Insights
1. **BFS Approach**: The last node in each level gives the right side view
2. **DFS Approach**: Traverse right subtree first, record first node at each depth
3. **Reverse Level Order**: Process levels from right to left
4. **All approaches**: O(n) time complexity

### Approach Selection
**Recommended Approaches:**
1. **BFS Level Order** - Most intuitive, modify level order traversal
2. **DFS Right-First** - Efficient recursive approach
3. **BFS with Queue** - Standard level order with right-side tracking

## ⚡ Complexity Analysis

### BFS Approach:
- **Time Complexity:** O(n) - Each node visited once
- **Space Complexity:** O(w) - Where w is maximum level width

### DFS Approach:
- **Time Complexity:** O(n) - Each node visited once
- **Space Complexity:** O(h) - Recursion stack, where h is tree height

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
Right side view = last node at each level in level order traversal

Can also be solved by traversing right subtree first in DFS

Empty tree returns empty list

Multiple valid approaches with same time complexity

🔗 Related Problems
Binary Tree Level Order Traversal

Binary Tree Zigzag Level Order Traversal

Binary Tree Level Order Traversal II

Populating Next Right Pointers in Each Node

Boundary of Binary Tree

