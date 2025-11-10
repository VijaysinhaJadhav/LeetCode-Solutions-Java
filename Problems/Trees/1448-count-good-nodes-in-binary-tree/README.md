# 1448. Count Good Nodes in Binary Tree

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Tree, Depth-First Search, Breadth-First Search, Binary Tree  
**Companies:** Amazon, Microsoft, Google, Facebook, Bloomberg

[LeetCode Link](https://leetcode.com/problems/count-good-nodes-in-binary-tree/)

Given a binary tree `root`, a node `X` in the tree is named **good** if in the path from root to `X` there are no nodes with a value greater than `X`.

Return the number of **good** nodes in the binary tree.

**Example 1:**

Input: root = [3,1,4,3,null,1,5]

Output: 4

Explanation:

Root (3) is always a good node.

Node 4 -> (3,4) is the maximum value in the path.

Node 5 -> (3,4,5) is the maximum value in the path.

Node 3 -> (3,1,3) is the maximum value in the path.


**Example 2:**

Input: root = [3,3,null,4,2]

Output: 3

Explanation:

Root (3) is always a good node.

Node 3 -> (3,3) is the maximum value in the path.

Node 4 -> (3,3,4) is the maximum value in the path.


**Example 3:**

Input: root = [1]

Output: 1

Explanation: Root is always a good node.


**Constraints:**
- The number of nodes in the binary tree is in the range `[1, 10^5]`.
- Each node's value is between `[-10^4, 10^4]`.

## 🧠 Thought Process

### Initial Thoughts
- Need to traverse the binary tree and track the maximum value encountered so far in each path
- A node is "good" if its value is greater than or equal to the maximum value in the path from root to that node
- The root is always a good node since there are no nodes before it

### Key Insights
1. **DFS Approach**: Depth-First Search is natural for tracking path information
2. **Track Maximum**: Maintain the current maximum value while traversing
3. **Comparison**: At each node, compare node value with current maximum
4. **Update Maximum**: Update the maximum when visiting child nodes

### Approach Selection
**Chosen Approach:** Depth-First Search (DFS) with Maximum Tracking  
**Why this approach?** 
- O(n) time complexity - visits each node once
- O(h) space complexity - recursion stack height
- Natural for tracking path-specific information
- Clean and intuitive implementation

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each node is visited exactly once
- **Space Complexity:** O(h) - Where h is the height of the tree (recursion stack)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The root is always counted as a good node

We need to track the maximum value along each path from root to current node

DFS allows us to naturally maintain path-specific state

When moving to child nodes, we pass the updated maximum value

🔗 Related Problems
Validate Binary Search Tree

Maximum Depth of Binary Tree

Path Sum

Sum Root to Leaf Numbers

Sum of Root To Leaf Binary Numbers
