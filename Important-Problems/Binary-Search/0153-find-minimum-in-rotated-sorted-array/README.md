# 153. Find Minimum in Rotated Sorted Array

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)

Suppose an array of length `n` sorted in ascending order is rotated between `1` and `n` times. For example, the array `nums = [0,1,2,4,5,6,7]` might become:
- `[4,5,6,7,0,1,2]` if it was rotated 4 times.
- `[0,1,2,4,5,6,7]` if it was rotated 7 times.

Given the sorted rotated array `nums` of unique elements, return the minimum element of this array.

You must write an algorithm that runs in O(log n) time.

**Example 1:**

Input: nums = [3,4,5,1,2]

Output: 1

Explanation: The original array was [1,2,3,4,5] rotated 3 times.


**Example 2:**

Input: nums = [4,5,6,7,0,1,2]

Output: 0

Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.


**Example 3:**

Input: nums = [11,13,15,17]

Output: 11

Explanation: The array was not rotated (or rotated n times).


**Constraints:**
- `n == nums.length`
- `1 <= n <= 5000`
- `-5000 <= nums[i] <= 5000`
- All the integers of `nums` are unique.
- `nums` is sorted and rotated between 1 and n times.

## 🧠 Thought Process

### Initial Thoughts
- Array was originally sorted but then rotated
- Need to find minimum element in O(log n) time
- Binary search is the natural choice for sorted/rotated arrays
- The minimum element is the only element that is smaller than both its neighbors

### Key Insights
1. **Rotation Point**: The array has two sorted portions with a discontinuity where rotation occurred
2. **Binary Search**: Compare middle element with the rightmost element to determine which half contains the minimum
3. **Pattern Recognition**: 
   - If nums[mid] > nums[right], minimum is in right half
   - If nums[mid] < nums[right], minimum is in left half (including mid)
4. **No Rotation Case**: If array is not rotated, first element is minimum

### Approach Selection
**Chosen Approach:** Modified Binary Search  
**Why this approach?** 
- O(log n) time complexity meets requirements
- Handles both rotated and non-rotated cases
- Simple and elegant implementation
- Leverages the properties of rotated sorted arrays

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) - Binary search halves search space each iteration
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key insight is that in a rotated sorted array, the minimum element is the only element that is smaller than both its neighbors

Compare middle element with rightmost element to determine search direction

The algorithm converges to the minimum element

🔗 Related Problems
Search in Rotated Sorted Array

Search in Rotated Sorted Array II

Find Minimum in Rotated Sorted Array II

Find Peak Element
