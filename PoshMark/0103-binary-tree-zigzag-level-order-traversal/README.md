# 103. Binary Tree Zigzag Level Order Traversal

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Tree, Breadth-First Search, Binary Tree  
**Companies:** Amazon, Microsoft, Facebook, Apple, Google, Bloomberg, Adobe, Uber, Oracle, TikTok

[LeetCode Link](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/)

Given the `root` of a binary tree, return *the zigzag level order traversal of its nodes' values*. (i.e., from left to right, then right to left for the next level and alternate between).

**Example 1:**

Input: root = [3,9,20,null,null,15,7]
Output: [[3],[20,9],[15,7]]
Explanation:
3
/
9 20
/
15 7
Level 0: left to right → [3]
Level 1: right to left → [20,9]
Level 2: left to right → [15,7]


**Example 2:**

Input: root = [1]
Output: [[1]]


**Example 3:**

Input: root = []
Output: []


**Constraints:**
- The number of nodes in the tree is in the range `[0, 2000]`.
- `-100 <= Node.val <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Need level-order traversal (BFS) but with alternating direction
- Standard BFS uses queue, but need to reverse order for alternate levels
- Can use deque or reverse lists
- Need to track level to know direction

### Key Insights
1. **BFS with Level Tracking:**
   - Use queue for standard level-order traversal
   - Track current level number
   - Reverse level result for odd levels (or even, depending on indexing)
2. **Two Stack Approach:**
   - Use two stacks: one for current level, one for next level
   - Alternate direction each level
   - More natural for zigzag pattern
3. **Deque (Double-ended Queue):**
   - Use deque for efficient addition/removal from both ends
   - Add children based on current direction
4. **DFS with Level Tracking:**
   - Use DFS with level parameter
   - Append to appropriate position in result list
   - Less intuitive but works

### Approach Selection
**Chosen Approach:** BFS with Level Tracking and Reversal  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity (for output and queue)
- Simple and intuitive
- Easy to understand and implement

**Alternative:** Two Stack Approach  
**Why this approach?**
- Also O(n) time and space
- More natural for zigzag pattern
- No need to reverse lists

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each node visited once
- **Space Complexity:** O(n) - Queue can hold up to n/2 nodes, output stores all nodes

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Level 0: left to right (normal)

Level 1: right to left (reversed)

Level 2: left to right (normal)

Pattern repeats: even levels normal, odd levels reversed

Handle empty tree case

🔗 Related Problems
102. Binary Tree Level Order Traversal

107. Binary Tree Level Order Traversal II

199. Binary Tree Right Side View

637. Average of Levels in Binary Tree

515. Find Largest Value in Each Tree Row

116. Populating Next Right Pointers in Each Node
