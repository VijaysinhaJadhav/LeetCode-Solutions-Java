# 25. Reverse Nodes in k-Group

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Linked List, Recursion  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/reverse-nodes-in-k-group/)

Given the `head` of a linked list, reverse the nodes of the list `k` at a time, and return the modified list.

`k` is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of `k` then left-out nodes, in the end, should remain as they are.

You may not alter the values in the list's nodes, only nodes themselves may be changed.

**Example 1:**

Input: head = [1,2,3,4,5], k = 2

Output: [2,1,4,3,5]

Explanation:


**Example 2:**

Input: head = [1,2,3,4,5], k = 3

Output: [3,2,1,4,5]

Explanation:


**Constraints:**
- The number of nodes in the list is `n`.
- `1 <= k <= n <= 5000`
- `0 <= Node.val <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Need to reverse nodes in groups of k
- If remaining nodes < k, leave them as is
- Need to handle connections between reversed groups
- Can solve iteratively or recursively

### Key Insights
1. **Group Detection**: Need to check if there are k nodes before reversing
2. **Reversal Technique**: Standard linked list reversal but for k nodes
3. **Connection Handling**: Connect end of previous group to start of next group
4. **Edge Cases**: k=1 (no change), k = list length (reverse entire list)

### Approach Selection
**Chosen Approach:** Iterative with Dummy Node  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Clear and intuitive
- Handles all edge cases well

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each node is visited once
- **Space Complexity:** O(1) - Only using constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a dummy node to simplify edge cases

Count nodes to check if group has k nodes

Reverse nodes using standard 3-pointer technique

Update connections between groups carefully

🔗 Related Problems
Swap Nodes in Pairs

Reverse Linked List

Reverse Linked List II

Rotate List

Reorder List
