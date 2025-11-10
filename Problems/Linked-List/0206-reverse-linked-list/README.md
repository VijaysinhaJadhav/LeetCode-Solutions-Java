# 206. Reverse Linked List

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Linked List, Recursion  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/reverse-linked-list/)

Given the `head` of a singly linked list, reverse the list, and return the reversed list.

**Example 1:**

Input: head = [1,2,3,4,5]

Output: [5,4,3,2,1]


**Example 2:**

Input: head = [1,2]

Output: [2,1]


**Example 3:**

Input: head = []

Output: []


**Constraints:**
- The number of nodes in the list is in the range `[0, 5000]`.
- `-5000 <= Node.val <= 5000`

## 🧠 Thought Process

### Initial Thoughts
- Need to reverse a singly linked list in-place
- Multiple approaches: iterative, recursive, and using stack
- Must handle edge cases: empty list, single node list
- Need to carefully manage pointer reassignments

### Key Insights
1. **Iterative Approach**: Use three pointers (prev, current, next) to reverse links
2. **Recursive Approach**: Reverse the rest of the list and adjust current node's pointers
3. **Stack Approach**: Push nodes to stack and pop to create reversed list (uses extra space)
4. **In-place Reversal**: Most efficient with O(1) space complexity

### Approach Selection
**Chosen Approach:** Iterative Pointer Reversal  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Simple and intuitive
- Easy to understand and implement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the list
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to maintain three pointers: previous, current, and next

At each step, reverse the link between current and previous

Move all pointers forward until the end of the list

Return the new head (which was the last node)

🔗 Related Problems
Reverse Linked List II

Palindrome Linked List

Swap Nodes in Pairs

Reverse Nodes in k-Group
