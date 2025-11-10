# 92. Reverse Linked List II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Linked List  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Uber

[LeetCode Link](https://leetcode.com/problems/reverse-linked-list-ii/)

Given the `head` of a singly linked list and two integers `left` and `right` where `left <= right`, reverse the nodes of the list from position `left` to position `right`, and return the reversed list.

**Example 1:**

Input: head = [1,2,3,4,5], left = 2, right = 4

Output: [1,4,3,2,5]


**Example 2:**

Input: head = [5], left = 1, right = 1

Output: [5]


**Constraints:**
- The number of nodes in the list is `n`.
- `1 <= n <= 500`
- `-500 <= Node.val <= 500`
- `1 <= left <= right <= n`

## 🧠 Thought Process

### Initial Thoughts
- Need to reverse a sublist of a linked list between positions left and right
- Must handle edge cases: reversing from head, reversing to tail, single node
- Multiple approaches: iterative with dummy node, recursive, stack-based
- Need to carefully manage pointers before, during, and after the reversal

### Key Insights
1. **Dummy Node**: Helps handle case when reversing starts from head
2. **Four Pointers**: Need to track nodes before, start, end, and after the sublist
3. **Standard Reversal**: Reverse the sublist using standard linked list reversal technique
4. **Pointer Management**: Carefully connect the reversed sublist back to the main list

### Approach Selection
**Chosen Approach:** Iterative with Dummy Node  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Handles all edge cases gracefully
- Clear and intuitive pointer management

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the list
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a dummy node to simplify head reversal case

Find the node before the sublist (left-1 position)

Reverse the sublist using standard three-pointer technique

Connect the reversed sublist back to the main list

Handle edge cases: left = 1, right = n, left = right

🔗 Related Problems
Reverse Linked List

Reverse Nodes in k-Group

Swap Nodes in Pairs

Reorder List
