# 33. Search in Rotated Sorted Array

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/search-in-rotated-sorted-array/)

There is an integer array `nums` sorted in ascending order (with distinct values).

Prior to being passed to your function, `nums` is possibly rotated at an unknown pivot index `k` (`1 <= k < nums.length`) such that the resulting array is `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]`.

Given the array `nums` after the possible rotation and an integer `target`, return the index of `target` if it is in `nums`, or `-1` otherwise.

You must write an algorithm with `O(log n)` runtime complexity.

**Example 1:**

Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4

**Example 2:**

Input: nums = [4,5,6,7,0,1,2], target = 3

Output: -1


**Example 3:**

Input: nums = [1], target = 0

Output: -1


**Constraints:**
- `1 <= nums.length <= 5000`
- `-10^4 <= nums[i] <= 10^4`
- All values of `nums` are unique.
- `nums` is an ascending array that is possibly rotated.
- `-10^4 <= target <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Array was originally sorted but then rotated at some pivot
- Need to search for target in O(log n) time
- Binary search is required, but needs modification for rotated array
- The array has two sorted portions with a rotation point

### Key Insights
1. **Two Sorted Halves**: The rotated array consists of two sorted portions
2. **Modified Binary Search**: Determine which half is sorted and if target lies in that half
3. **Pivot Comparison**: Compare middle element with left/right to determine sorted half
4. **Target Range Check**: Check if target lies within the bounds of the sorted half

### Approach Selection
**Chosen Approach:** Modified Binary Search  
**Why this approach?** 
- O(log n) time complexity meets requirements
- Handles both rotated and non-rotated cases
- Efficiently narrows down search space
- Leverages properties of rotated sorted arrays

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) - Binary search halves search space each iteration
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to determine which half of the array is sorted

Check if target lies within the sorted half's range

If not, search the other half

Similar to finding minimum but with target comparison

🔗 Related Problems
Search in Rotated Sorted Array II

Find Minimum in Rotated Sorted Array

Find Minimum in Rotated Sorted Array II

Find Peak Element
