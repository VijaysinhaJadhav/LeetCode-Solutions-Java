# 297. Serialize and Deserialize Binary Tree

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Tree, Depth-First Search, Breadth-First Search, Design, Binary Tree, String  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn, Oracle

[LeetCode Link](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/)

Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

**Clarification:** The input/output format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.

**Example 1:**

Input: root = [1,2,3,null,null,4,5]

Output: [1,2,3,null,null,4,5]


**Example 2:**

Input: root = []

Output: []


**Constraints:**
- The number of nodes in the tree is in the range `[0, 10^4]`.
- `-1000 <= Node.val <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Need to convert binary tree to string and back
- Multiple serialization formats: Preorder, Level-order, etc.
- Must handle null nodes to preserve tree structure
- The challenge is to design a robust two-way conversion

### Key Insights
1. **Preorder DFS with Null Markers**: Simple and efficient recursive approach
2. **Level-order BFS**: More intuitive, similar to LeetCode format
3. **Format Choice**: Need delimiter and null representation
4. **Recursive vs Iterative**: Both work, recursive is more elegant for DFS

### Approach Selection
**Chosen Approach:** Preorder DFS with Null Markers  
**Why this approach?** 
- O(n) time complexity for both operations
- O(n) space complexity for recursion stack
- Clean recursive implementation
- Handles all tree structures correctly

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) for both serialization and deserialization
- **Space Complexity:** O(n) for recursion stack and string storage

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use special marker (like "X") for null nodes

Comma-separated values work well as format

Preorder traversal naturally reconstructs tree structure

Handle edge cases: empty tree, single node, skewed trees

🔗 Related Problems
Serialize and Deserialize BST

Serialize and Deserialize N-ary Tree

Find Duplicate Subtrees

Construct String from Binary Tree
