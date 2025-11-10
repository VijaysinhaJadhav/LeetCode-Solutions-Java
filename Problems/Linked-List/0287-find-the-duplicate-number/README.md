
# 287. Find the Duplicate Number

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Binary Search, Bit Manipulation  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/find-the-duplicate-number/)

Given an array of integers `nums` containing `n + 1` integers where each integer is in the range `[1, n]` inclusive.

There is only one repeated number in `nums`, return this repeated number.

You must solve the problem without modifying the array `nums` and use only constant extra space.

**Example 1:**

Input: nums = [1,3,4,2,2]

Output: 2


**Example 2:**

Input: nums = [3,1,3,4,2]

Output: 3


**Constraints:**
- `1 <= n <= 10^5`
- `nums.length == n + 1`
- `1 <= nums[i] <= n`
- All the integers in `nums` appear only once except for precisely one integer which appears two or more times.

## 🧠 Thought Process

### Initial Thoughts
- Array has n+1 elements with values in [1, n], so at least one duplicate exists
- Cannot modify array and must use constant space
- Multiple approaches: Floyd's cycle detection, binary search, bit manipulation
- The problem can be transformed into a linked list cycle problem

### Key Insights
1. **Floyd's Cycle Detection**: Treat array as linked list where nums[i] points to next node
2. **Binary Search**: Count numbers ≤ mid to find duplicate in specific range
3. **Bit Manipulation**: Use XOR to find the duplicate
4. **Array as Linked List**: The duplicate creates a cycle in the implicit linked list

### Approach Selection
**Chosen Approach:** Floyd's Tortoise and Hare (Cycle Detection)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Meets all constraints (no modification, constant space)
- Elegant mathematical solution

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Linear time with two passes
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Treat the array as a linked list where index → value → index

The duplicate creates a cycle in this linked list

Use slow and fast pointers to detect cycle

Find cycle entrance to locate the duplicate number

Similar to Linked List Cycle II problem

🔗 Related Problems
Linked List Cycle II

Missing Number

First Missing Positive

Single Number
