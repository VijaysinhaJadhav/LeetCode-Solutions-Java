# 2. Add Two Numbers

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Linked List, Math, Recursion  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/add-two-numbers/)

You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

**Example 1:**

Input: l1 = [2,4,3], l2 = [5,6,4]

Output: [7,0,8]

Explanation: 342 + 465 = 807.


**Example 2:**

Input: l1 = [0], l2 = [0]

Output: [0]


**Example 3:**

Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]

Output: [8,9,9,9,0,0,0,1]


**Constraints:**
- The number of nodes in each linked list is in the range `[1, 100]`.
- `0 <= Node.val <= 9`
- It is guaranteed that the list represents a number that does not have leading zeros.

## 🧠 Thought Process

### Initial Thoughts
- Need to add two numbers represented as linked lists in reverse order
- Similar to elementary school addition, but with linked lists
- Must handle different list lengths and carry-over
- Multiple approaches: iterative, recursive, and using stack

### Key Insights
1. **Elementary Addition**: Process digits from least significant to most significant
2. **Carry Handling**: Track carry-over between digit additions
3. **Different Lengths**: Handle when one list is longer than the other
4. **Final Carry**: Don't forget the final carry if it exists

### Approach Selection
**Chosen Approach:** Iterative with Dummy Node  
**Why this approach?** 
- O(max(m, n)) time complexity
- O(max(m, n)) space complexity for the result
- Simple and intuitive
- Handles all edge cases gracefully

## ⚡ Complexity Analysis
- **Time Complexity:** O(max(m, n)) where m and n are lengths of the two lists
- **Space Complexity:** O(max(m, n)) for the result list

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a dummy node to simplify head management

Process both lists until both are exhausted and carry is 0

Calculate sum = digit1 + digit2 + carry

Create new node with sum % 10, update carry = sum / 10

Handle different list lengths by treating null as 0

🔗 Related Problems
Add Two Numbers II

Add Binary

Add Strings

Plus One Linked List
