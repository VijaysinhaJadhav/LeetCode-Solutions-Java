# 100. Same Tree

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Tree, Depth-First Search, Breadth-First Search, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/same-tree/)

Given the roots of two binary trees `p` and `q`, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.

**Example 1:**  

Input: p = [1,2,3], q = [1,2,3]

Output: true

1         1

/ \ /

2 3 2 3

**Example 2:**

Input: p = [1,2], q = [1,null,2]

Output: false

1         1

/

2 2

**Example 3:**

Input: p = [1,2,1], q = [1,1,2]

Output: false

1         1

/ \ /

2 1 1 2


**Constraints:**
- The number of nodes in both trees is in the range `[0, 100]`.
- `-10^4 <= Node.val <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to check if two trees have identical structure and values
- Multiple approaches: recursive DFS, iterative DFS, BFS
- Handle edge cases: both null, one null, different structures

### Key Insights
1. **Recursive DFS**: Check current nodes, then recursively check left and right subtrees
2. **Iterative DFS**: Use stack to simulate recursion
3. **BFS**: Use queue for level-order comparison
4. **All approaches**: O(n) time complexity

### Approach Selection
**Recommended Approaches:**
1. **Recursive DFS** - Most intuitive and concise
2. **Iterative BFS** - Natural level-by-level comparison
3. **Iterative DFS** - Alternative iterative approach

## ⚡ Complexity Analysis

### All Approaches:
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** 
  - Recursive: O(h) - Call stack, where h is tree height
  - Iterative: O(h) - Stack/queue, where h is tree height
  - Worst case: O(n) for skewed tree
  - Best case: O(log n) for balanced tree

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
Two trees are same if they have identical structure AND values

Empty trees (both null) are considered the same

If one tree is null and other isn't, they're different

Order of node visits matters (preorder works well)

🔗 Related Problems
Symmetric Tree

Subtree of Another Tree

Invert Binary Tree

Maximum Depth of Binary Tree

Balanced Binary Tree




