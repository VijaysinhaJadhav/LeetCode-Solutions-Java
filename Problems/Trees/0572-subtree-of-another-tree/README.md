# 572. Subtree of Another Tree

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Tree, Depth-First Search, String Matching, Binary Tree, Hash Function  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/subtree-of-another-tree/)

Given the roots of two binary trees `root` and `subRoot`, return `true` if there is a subtree of `root` with the same structure and node values of `subRoot` and `false` otherwise.

A subtree of a binary tree `tree` is a tree that consists of a node in `tree` and all of this node's descendants. The tree `tree` could also be considered as a subtree of itself.

**Example 1:**  

Input: root = [3,4,5,1,2], subRoot = [4,1,2]

Output: true

 root: 3       subRoot: 4
       / \             / \
      4   5           1   2
     / \
    1   2


**Example 2:**

Input: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]

Output: false

 root: 3       subRoot: 4
       / \             / \
      4   5           1   2
     / \
    1   2
     \
      0


**Constraints:**
- The number of nodes in the `root` tree is in the range `[1, 2000]`.
- The number of nodes in the `subRoot` tree is in the range `[1, 1000]`.
- `-10^4 <= root.val <= 10^4`
- `-10^4 <= subRoot.val <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to check if `subRoot` is a subtree of `root`
- A subtree can start at any node in `root`
- Multiple approaches: recursive DFS, serialization, KMP algorithm
- Need to check both structure and values

### Key Insights
1. **Recursive DFS**: For each node in `root`, check if subtree starting from that node matches `subRoot`
2. **Serialization**: Convert trees to strings and use substring search
3. **KMP Algorithm**: Efficient string matching for serialized trees
4. **All approaches**: O(m×n) worst case, but optimizations possible

### Approach Selection
**Recommended Approaches:**
1. **Recursive DFS** - Most intuitive and straightforward
2. **Serialization with KMP** - More efficient for large trees
3. **Iterative DFS** - Alternative iterative approach

## ⚡ Complexity Analysis

### Recursive DFS:
- **Time Complexity:** O(m×n) where m = nodes in root, n = nodes in subRoot
- **Space Complexity:** O(h) - Recursion stack, where h is tree height

### Serialization with KMP:
- **Time Complexity:** O(m + n) - Linear time string matching
- **Space Complexity:** O(m + n) - Storage for serialized trees

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
A subtree includes the node and all its descendants

The entire tree is considered a subtree of itself

Empty tree cannot be a subtree (constraints guarantee non-empty trees)

Both structure and values must match exactly

🔗 Related Problems
Same Tree

Symmetric Tree

Path Sum III

Find Duplicate Subtrees

Linked List in Binary Tree
