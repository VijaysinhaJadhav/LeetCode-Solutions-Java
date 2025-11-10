# 143. Reorder List

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Linked List, Two Pointers, Stack, Recursion  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Uber

[LeetCode Link](https://leetcode.com/problems/reorder-list/)

You are given the head of a singly linked list. The list can be represented as:

L₀ → L₁ → … → Lₙ₋₁ → Lₙ

Reorder the list to be in the following form:

L₀ → Lₙ → L₁ → Lₙ₋₁ → L₂ → Lₙ₋₂ → …

You may not modify the values in the list's nodes. Only the node pointers may be changed.

**Example 1:**

Input: head = [1,2,3,4]

Output: [1,4,2,3]


**Example 2:**

Input: head = [1,2,3,4,5]

Output: [1,5,2,4,3]


**Constraints:**
- The number of nodes in the list is in the range `[1, 5 * 10^4]`.
- `1 <= Node.val <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Need to reorder list by alternating between first and last nodes
- Cannot modify node values, only pointers
- Multiple approaches: find middle, reverse second half, and merge
- Must handle even and odd length lists

### Key Insights
1. **Three-Step Approach**: 
   - Find middle of list (slow/fast pointers)
   - Reverse second half of list
   - Merge two halves alternately
2. **Stack Approach**: Push all nodes to stack and pop to get reverse order
3. **Recursive Approach**: Recursively process from both ends (less efficient)
4. **In-place**: Most efficient solution uses O(1) space

### Approach Selection
**Chosen Approach:** Three-Step In-place Reordering  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- In-place modification
- Handles all cases efficiently

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Three passes through the list
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to find the middle, reverse the second half, then merge

Use slow and fast pointers to find middle efficiently

Reverse linked list in-place

Merge by alternating nodes from both halves

Handle even and odd length lists correctly

🔗 Related Problems
Reverse Linked List

Middle of the Linked List

Merge Two Sorted Lists

Palindrome Linked List
