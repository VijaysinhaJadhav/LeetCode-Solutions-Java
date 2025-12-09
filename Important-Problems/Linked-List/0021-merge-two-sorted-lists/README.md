# 21. Merge Two Sorted Lists

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Linked List, Recursion  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/merge-two-sorted-lists/)

You are given the heads of two sorted linked lists `list1` and `list2`.

Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.

Return the head of the merged linked list.

**Example 1:**

Input: list1 = [1,2,4], list2 = [1,3,4]

Output: [1,1,2,3,4,4]


**Example 2:**

Input: list1 = [], list2 = []

Output: []


**Example 3:**

Input: list1 = [], list2 = [0]

Output: [0]


**Constraints:**
- The number of nodes in both lists is in the range `[0, 50]`.
- `-100 <= Node.val <= 100`
- Both `list1` and `list2` are sorted in non-decreasing order.

## 🧠 Thought Process

### Initial Thoughts
- Need to merge two sorted linked lists into one sorted list
- Similar to the merge step in merge sort
- Multiple approaches: iterative, recursive, and in-place
- Must handle edge cases: empty lists, different list lengths

### Key Insights
1. **Iterative Approach**: Use a dummy node and compare nodes from both lists
2. **Recursive Approach**: Compare current nodes and recursively merge the rest
3. **In-place Merging**: Can merge without creating new nodes by rearranging pointers
4. **Dummy Node**: Helps simplify edge cases and avoid special handling for head

### Approach Selection
**Chosen Approach:** Iterative with Dummy Node  
**Why this approach?** 
- O(n + m) time complexity
- O(1) space complexity (excluding input lists)
- Simple and intuitive
- Handles all edge cases gracefully

## ⚡ Complexity Analysis
- **Time Complexity:** O(n + m) where n and m are lengths of the two lists
- **Space Complexity:** O(1) - Only constant extra space used (excluding input lists)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Using a dummy node simplifies the code by avoiding special cases for the head

The algorithm is similar to the merge step in merge sort

Always attach the smaller node to the current merged list

After one list is exhausted, attach the remaining nodes from the other list

🔗 Related Problems
Merge k Sorted Lists

Merge Sorted Array

Sort List

Merge Two Sorted Lists (Premium)


