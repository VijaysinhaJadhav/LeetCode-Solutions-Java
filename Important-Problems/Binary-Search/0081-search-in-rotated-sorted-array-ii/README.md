# 81. Search in Rotated Sorted Array II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Uber

[LeetCode Link](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/)

There is an integer array `nums` sorted in non-decreasing order (not necessarily with distinct values).

Prior to being passed to your function, `nums` is rotated at an unknown pivot index `k` (`0 <= k < nums.length`) such that the resulting array is `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]`.

Given the array `nums` after the rotation and an integer `target`, return `true` if `target` is in `nums`, or `false` otherwise.

You must write an algorithm with `O(log n)` runtime complexity on average, but worst-case `O(n)`.

**Example 1:**

Input: nums = [2,5,6,0,0,1,2], target = 0

Output: true


**Example 2:**

Input: nums = [2,5,6,0,0,1,2], target = 3

Output: false


**Constraints:**
- `1 <= nums.length <= 5000`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is guaranteed to be rotated at some pivot.
- `-10^4 <= target <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Similar to Problem 33, but now array can have duplicates
- Duplicates make the problem more challenging
- Worst-case time complexity becomes O(n) due to duplicates
- Need to handle cases where we can't determine which half is sorted

### Key Insights
1. **Duplicate Challenge**: When nums[left] == nums[mid] == nums[right], we can't determine sorted half
2. **Worst-case O(n)**: In case of many duplicates, we might need to search both halves
3. **Modified Binary Search**: Similar to Problem 33 but with additional checks for duplicates
4. **Skip Duplicates**: When uncertain, increment left or decrement right to skip duplicates

### Approach Selection
**Chosen Approach:** Modified Binary Search with Duplicate Handling  
**Why this approach?** 
- O(log n) average case, O(n) worst-case meets requirements
- Handles duplicates gracefully
- Builds upon the solution for Problem 33
- Efficient for most practical cases

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) average case, O(n) worst-case (due to duplicates)
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key difference from Problem 33 is handling duplicates

When nums[left] == nums[mid] == nums[right], we can't determine which half is sorted

In such cases, we skip duplicates by incrementing left or decrementing right

Worst-case occurs when most elements are duplicates

🔗 Related Problems
Search in Rotated Sorted Array

Find Minimum in Rotated Sorted Array

Find Minimum in Rotated Sorted Array II

Find Peak Element
