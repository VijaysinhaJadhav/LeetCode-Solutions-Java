# 138. Copy List with Random Pointer

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, Linked List  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/copy-list-with-random-pointer/)

A linked list of length `n` is given such that each node contains an additional random pointer, which could point to any node in the list, or `null`.

Construct a deep copy of the list. The deep copy should consist of exactly `n` brand new nodes, where each new node has its value set to the value of its corresponding original node. Both the `next` and `random` pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the original list.

Return the head of the copied linked list.

**Example 1:**

Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]

Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]


**Example 2:**

Input: head = [[1,1],[2,1]]

Output: [[1,1],[2,1]]


**Example 3:**

Input: head = [[3,null],[3,0],[3,null]]

Output: [[3,null],[3,0],[3,null]]


**Constraints:**
- `0 <= n <= 1000`
- `-10^4 <= Node.val <= 10^4`
- `Node.random` is `null` or points to a node in the linked list.

## 🧠 Thought Process

### Initial Thoughts
- Need to create a deep copy of a linked list with random pointers
- The challenge is handling the random pointers that can point anywhere
- Multiple approaches: hash map, interweaving nodes, recursive
- Must ensure no pointers point to original list

### Key Insights
1. **Hash Map Approach**: Map original nodes to copied nodes for O(1) random pointer lookup
2. **Interweaving Approach**: Insert copied nodes between original nodes, then separate
3. **Recursive Approach**: Use recursion with memoization to handle cycles
4. **Two Passes**: Usually need two passes - one for nodes, one for random pointers

### Approach Selection
**Chosen Approach:** Hash Map with Two Passes  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity for the hash map
- Clear and intuitive
- Handles all cases including cycles

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Two passes through the list
- **Space Complexity:** O(n) - Hash map stores all nodes

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a hash map to maintain mapping between original and copied nodes

First pass: create all new nodes and store mapping

Second pass: assign next and random pointers using the mapping

The hash map enables O(1) lookups for random pointer assignments

🔗 Related Problems
Clone Graph

Clone Binary Tree With Random Pointer

Clone N-ary Tree

Copy List with Random Pointer (LintCode)
