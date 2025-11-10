# 704. Binary Search

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Binary Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/binary-search/)

Given an array of integers `nums` which is sorted in ascending order, and an integer `target`, write a function to search `target` in `nums`. If `target` exists, then return its index. Otherwise, return `-1`.

You must write an algorithm with `O(log n)` runtime complexity.

**Example 1:**

Input: nums = [-1,0,3,5,9,12], target = 9

Output: 4

Explanation: 9 exists in nums and its index is 4


**Example 2:**

Input: nums = [-1,0,3,5,9,12], target = 2

Output: -1

Explanation: 2 does not exist in nums so return -1


**Constraints:**
- `1 <= nums.length <= 10^4`
- `-10^4 < nums[i], target < 10^4`
- All the integers in `nums` are unique.
- `nums` is sorted in ascending order.

## 🧠 Thought Process

### Initial Thoughts
- Array is sorted - perfect for binary search
- Need O(log n) time complexity
- Multiple implementations with different styles
- The challenge is handling edge cases and avoiding off-by-one errors

### Key Insights
1. **Classic Algorithm**: Divide and conquer approach
2. **Loop Invariant**: Maintain search space with left and right pointers
3. **Mid Calculation**: Avoid overflow with `left + (right - left) / 2`
4. **The key insight**: Halve search space each iteration

### Approach Selection
**Chosen Approach:** Standard Binary Search  
**Why this approach?** 
- O(log n) time complexity
- O(1) space complexity
- Simple and efficient
- Well-understood and widely used

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) - Halve search space each iteration
- **Space Complexity:** O(1) - Only constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Calculate mid safely to avoid integer overflow

Maintain loop invariant: search space is [left, right]

Handle empty array and single element cases

The algorithm is fundamental and appears in many variations

🔗 Related Problems
Find First and Last Position of Element in Sorted Array

Search Insert Position

Sqrt(x)

First Bad Version

Guess Number Higher or Lower
