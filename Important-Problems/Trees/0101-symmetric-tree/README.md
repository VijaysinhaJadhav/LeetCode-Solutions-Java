# 101. Symmetric Tree

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Tree, Depth-First Search, Breadth-First Search, Binary Tree  
**Companies:** Amazon, Microsoft, Google, Apple, Facebook, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/symmetric-tree/)

Given the `root` of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).

**Example 1:**

![Symmetric Tree](https://assets.leetcode.com/uploads/2021/02/19/symtree1.jpg)

Input: root = [1,2,2,3,4,4,3]

Output: true


**Example 2:**

![Asymmetric Tree](https://assets.leetcode.com/uploads/2021/02/19/symtree2.jpg)

Input: root = [1,2,2,null,3,null,3]

Output: false


**Constraints:**
- The number of nodes in the tree is in the range `[1, 1000]`.
- `-100 <= Node.val <= 100`

**Follow up:** Could you solve it both recursively and iteratively?

## 🧠 Thought Process

### Initial Thoughts
- A tree is symmetric if the left subtree is a mirror reflection of the right subtree
- Need to compare corresponding nodes in left and right subtrees
- Both recursive and iterative approaches are possible

### Key Insights
1. **Recursive Approach**: Compare left.left with right.right AND left.right with right.left
2. **Iterative Approach**: Use queue or stack to compare nodes level by level
3. **Base Cases**: 
   - Both nodes null → symmetric
   - One node null → asymmetric
   - Values not equal → asymmetric

### Approach Selection
**Chosen Approach:** Recursive DFS  
**Why this approach?** 
- Intuitive and elegant
- Easy to understand and implement
- Naturally handles the mirror comparison
- O(n) time and O(h) space complexity

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - We visit each node once
- **Space Complexity:** O(h) - Recursion stack height, where h is tree height
  - Worst case: O(n) for skewed tree
  - Best case: O(log n) for balanced tree

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
A tree is symmetric if it's a mirror of itself around the center

Compare outer pairs (left.left vs right.right) and inner pairs (left.right vs right.left)

Handle null cases carefully: both null = symmetric, one null = asymmetric

🔗 Related Problems
Same Tree

Maximum Depth of Binary Tree

Balanced Binary Tree

Invert Binary Tree

Subtree of Another Tree
