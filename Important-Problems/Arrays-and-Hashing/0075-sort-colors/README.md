# 75. Sort Colors

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Sorting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/sort-colors/)

Given an array `nums` with `n` objects colored red, white, or blue, sort them **in-place** so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

We will use the integers `0`, `1`, and `2` to represent the color red, white, and blue, respectively.

You must solve this problem without using the library's sort function.

**Example 1:**

Input: nums = [2,0,2,1,1,0]

Output: [0,0,1,1,2,2]


**Example 2:**

Input: nums = [2,0,1]

Output: [0,1,2]


**Constraints:**
- `n == nums.length`
- `1 <= n <= 300`
- `nums[i]` is either `0`, `1`, or `2`.

**Follow up:** Could you come up with a one-pass algorithm using only constant extra space?

## 🧠 Thought Process

### Initial Thoughts
- Need to sort array containing only 0s, 1s, and 2s
- Must do it in-place without using built-in sort
- Multiple approaches with different trade-offs

### Key Insights
1. **Counting Sort**: Count 0s, 1s, 2s then overwrite array - O(n) time, O(1) space but two passes
2. **Dutch National Flag Algorithm**: One-pass three-pointer approach - O(n) time, O(1) space
3. **Two-Pointer Swap**: Move 0s to front, 2s to back - O(n) time, O(1) space

### Approach Selection
**Chosen Approach:** Dutch National Flag Algorithm  
**Why this approach?** 
- Optimal O(n) time complexity with single pass
- O(1) space complexity
- Elegant solution to the classic problem
- Solves the follow-up challenge

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only using three pointers

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The Dutch National Flag algorithm partitions the array into three sections

Maintains three pointers: low (for 0s), mid (for 1s), high (for 2s)

The algorithm is a variation of the partition process in Quick Sort

Works in a single pass through the array

🔗 Related Problems
283. Move Zeroes

215. Kth Largest Element in an Array

88. Merge Sorted Array

280. Wiggle Sort
