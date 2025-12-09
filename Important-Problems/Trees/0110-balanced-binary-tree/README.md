# 110. Balanced Binary Tree

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Tree, Depth-First Search, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/balanced-binary-tree/)

Given a binary tree, determine if it is **height-balanced**.

A **height-balanced** binary tree is defined as:
> A binary tree in which the left and right subtrees of every node differ in height by no more than 1.

**Example 1:**  

Input: root = [3,9,20,null,null,15,7]

Output: true

Explanation:

3

/

9 20

/

15 7

Heights: left=1, right=2 → difference=1 (balanced)


**Example 2:**

Input: root = [1,2,2,3,3,null,null,4,4]

Output: false

Explanation:

1

/

2 2

/

3 3

/

4 4

Node 2: left height=2, right height=1 → difference=1 (balanced)

Node 3: left height=1, right height=0 → difference=1 (balanced)

But node 1: left height=3, right height=1 → difference=2 (NOT balanced)


**Example 3:**

Input: root = []

Output: true


**Constraints:**
- The number of nodes in the tree is in the range `[0, 5000]`.
- `-10^4 <= Node.val <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to check if EVERY node in the tree is balanced
- Balanced means: |left height - right height| ≤ 1
- Need to calculate heights efficiently
- Early termination if any subtree is unbalanced

### Key Insights
1. **Bottom-up DFS**: Calculate heights and check balance simultaneously
2. **Early termination**: Return immediately if any subtree is unbalanced
3. **Height calculation**: Standard DFS height calculation with balance check
4. **Return special value**: Use -1 to indicate unbalanced subtree

### Approach Selection
**Recommended Approach:** DFS with Height Calculation and Early Termination
- **Time Complexity:** O(n) - Visit each node once
- **Space Complexity:** O(h) - Recursion stack, where h is tree height

## ⚡ Complexity Analysis

### DFS Approach:
- **Time Complexity:** O(n) - Each node is visited once
- **Space Complexity:** O(h) - Recursion stack, where h is tree height
  - Worst case: O(n) for skewed tree
  - Best case: O(log n) for balanced tree

## 🔍 Solution Code

```java
// See Solution.java for complete implementation

📝 Notes
A tree is balanced if ALL nodes are balanced

Empty tree is considered balanced

Early termination improves efficiency for unbalanced trees

Use sentinel value (-1) to propagate unbalanced state

🔗 Related Problems
Maximum Depth of Binary Tree

Minimum Depth of Binary Tree

Diameter of Binary Tree

Binary Tree Maximum Path Sum

Longest Univalue Path
