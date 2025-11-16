# 34. Find First and Last Position of Element in Sorted Array

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search  
**Companies:** Amazon, Facebook, Google, Microsoft, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

Given an array of integers `nums` sorted in non-decreasing order, find the starting and ending position of a given `target` value.

If `target` is not found in the array, return `[-1, -1]`.

You must write an algorithm with `O(log n)` runtime complexity.

**Example 1:**

Input: nums = [5,7,7,8,8,10], target = 8

Output: [3,4]


**Example 2:**

Input: nums = [5,7,7,8,8,10], target = 6

Output: [-1,-1]


**Example 3:**

Input: nums = [], target = 0

Output: [-1,-1]


**Constraints:**
- `0 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`
- `nums` is a non-decreasing array.
- `-10^9 <= target <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- Array is sorted, so binary search is natural choice
- Need to find both first and last occurrence of target
- Can modify binary search to find boundaries
- Two separate binary searches: one for left boundary, one for right boundary

### Key Insights
1. **Modified Binary Search**: Regular binary search finds any occurrence, we need boundaries
2. **Left Boundary Search**: When nums[mid] == target, don't return; continue searching left
3. **Right Boundary Search**: When nums[mid] == target, don't return; continue searching right
4. **Template Approach**: Use binary search templates that handle boundaries correctly

### Approach Selection
**Chosen Approach:** Two Binary Searches  
**Why this approach?** 
- O(log n) time complexity
- Clear separation of concerns
- Easy to understand and implement
- Handles all edge cases

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) - Two binary searches
- **Space Complexity:** O(1) - Only constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
First binary search finds left boundary (first occurrence)

Second binary search finds right boundary (last occurrence)

When nums[mid] == target, we don't return immediately

For left boundary: continue searching left half

For right boundary: continue searching right half

🔗 Related Problems
Search Insert Position

First Bad Version

Binary Search

Time Based Key-Value Store

Search in a Sorted Array of Unknown Size
