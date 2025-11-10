# 230. Kth Smallest Element in a BST

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Tree, Depth-First Search, Binary Search Tree, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Uber, Apple

[LeetCode Link](https://leetcode.com/problems/kth-smallest-element-in-a-bst/)

Given the `root` of a binary search tree, and an integer `k`, return *the `kth` smallest value (1-indexed) of all the values of the nodes in the tree*.

**Example 1:**

Input: root = [3,1,4,null,2], k = 1

Output: 1

Explanation:

3

/

1 4

2

The 1st smallest element is 1.


**Example 2:**

Input: root = [5,3,6,2,4,null,null,1], k = 3

Output: 3

Explanation:

5

/

3 6

/

2 4

/

1

The 3rd smallest element is 3.


**Constraints:**
- The number of nodes in the tree is `n`.
- `1 <= k <= n <= 10^4`
- `0 <= Node.val <= 10^4`

**Follow-up:** If the BST is modified often (i.e., we can do insert and delete operations) and you need to find the kth smallest frequently, how would you optimize?

## 🧠 Thought Process

### Initial Thoughts
- Binary Search Tree property: inorder traversal gives sorted sequence
- Need to find kth smallest element (1-indexed)
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Inorder Traversal**: Natural approach since BST inorder is sorted
2. **Iterative DFS**: Can stop early when kth element is found
3. **Binary Search**: Leverage BST structure to count nodes
4. **Follow-up**: Augment tree with subtree size information

### Approach Selection
**Chosen Approach:** Iterative Inorder Traversal  
**Why this approach?** 
- O(h + k) time complexity - efficient for small k
- O(h) space complexity - only stack space
- Can stop early when kth element is found
- Intuitive and easy to implement

## ⚡ Complexity Analysis
- **Time Complexity:** O(h + k) where h is tree height
- **Space Complexity:** O(h) for the recursion/stack space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Inorder traversal of BST yields elements in ascending order

Can use iterative DFS to avoid full traversal when k is small

For follow-up: augment tree nodes with left subtree size for O(h) queries

Morris traversal can achieve O(1) space but modifies tree temporarily

🔗 Related Problems
Binary Tree Inorder Traversal

Validate Binary Search Tree

Binary Search Tree Iterator

Inorder Successor in BST

Closest Binary Search Tree Value
