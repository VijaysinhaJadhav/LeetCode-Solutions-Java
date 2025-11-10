# 104. Maximum Depth of Binary Tree

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Tree, Depth-First Search, Breadth-First Search, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/maximum-depth-of-binary-tree/)

Given the `root` of a binary tree, return its maximum depth.

The **maximum depth** is the number of nodes along the longest path from the root node down to the farthest leaf node.

**Example 1:**  

Input: root = [3,9,20,null,null,15,7]

Output: 3

Explanation:

3

/

9 20

/

15 7

Maximum depth: 3 (path: 3 → 20 → 15 or 3 → 20 → 7)


**Example 2:**

Input: root = [1,null,2]

Output: 2


**Example 3:**

Input: root = []

Output: 0


**Constraints:**
- The number of nodes in the tree is in the range `[0, 10^4]`.
- `-100 <= Node.val <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Maximum depth = height of the tree
- Multiple approaches: recursive DFS, iterative DFS, BFS
- Need to handle edge cases: empty tree, single node

### Key Insights
1. **Recursive DFS**: Simple divide and conquer - max depth = 1 + max(left depth, right depth)
2. **Iterative BFS**: Count levels using queue - each level = depth + 1
3. **Iterative DFS**: Use stack to track nodes and their depths
4. **All approaches**: O(n) time complexity, differ in space usage

### Approach Selection
**Recommended Approaches:**
1. **Recursive DFS** - Most intuitive and concise
2. **Iterative BFS** - Natural level-counting approach
3. **Iterative DFS** - Alternative iterative approach

## ⚡ Complexity Analysis

### All Approaches:
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** 
  - Recursive: O(h) - Call stack, where h is tree height
  - BFS: O(w) - Queue, where w is maximum level width
  - DFS: O(h) - Stack, where h is tree height
  - Worst case: O(n) for skewed tree
  - Best case: O(log n) for balanced tree

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
Maximum depth = height of the tree = longest path from root to leaf

Leaf node: node with no children (both left and right are null)

Empty tree has depth 0, single node tree has depth 1

Fundamental problem for understanding tree traversal and recursion

🔗 Related Problems
Balanced Binary Tree

Minimum Depth of Binary Tree

Diameter of Binary Tree

Maximum Depth of N-ary Tree

Longest Univalue Path
