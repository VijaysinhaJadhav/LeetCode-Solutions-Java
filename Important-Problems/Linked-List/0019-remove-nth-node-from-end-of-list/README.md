# 19. Remove Nth Node From End of List

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Linked List, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)

Given the `head` of a linked list, remove the `n-th` node from the end of the list and return its head.

**Example 1:**

Input: head = [1,2,3,4,5], n = 2

Output: [1,2,3,5]


**Example 2:**

Input: head = [1], n = 1

Output: []


**Example 3:**

Input: head = [1,2], n = 1

Output: [1]


**Constraints:**
- The number of nodes in the list is `sz`.
- `1 <= sz <= 30`
- `0 <= Node.val <= 100`
- `1 <= n <= sz`

## 🧠 Thought Process

### Initial Thoughts
- Need to remove the n-th node from the end of a linked list
- Cannot traverse backwards in singly linked list
- Multiple approaches: two passes, one pass with two pointers, stack
- Must handle edge case: removing head node

### Key Insights
1. **Two Pass Approach**: First pass to find length, second pass to remove (L-n+1)th node
2. **One Pass Two Pointers**: Use fast and slow pointers with n gap
3. **Dummy Node**: Helps handle edge case when removing head node
4. **Stack Approach**: Push all nodes to stack, pop n times to find target

### Approach Selection
**Chosen Approach:** One Pass Two Pointers with Dummy Node  
**Why this approach?** 
- O(n) time complexity with single pass
- O(1) space complexity
- Handles all edge cases gracefully
- Elegant and efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the list
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a dummy node to handle the case when removing the head

Move fast pointer n steps ahead first

Then move both pointers until fast reaches end

Slow pointer will be at (n+1)th node from end

Remove the next node of slow pointer

🔗 Related Problems
Merge Two Sorted Lists

Remove Duplicates from Sorted List

Remove Duplicates from Sorted List II

Remove Linked List Elements
