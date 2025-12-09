# 35. Search Insert Position

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Binary Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe, Uber

[LeetCode Link](https://leetcode.com/problems/search-insert-position/)

Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You must write an algorithm with O(log n) runtime complexity.

**Example 1:**

Input: nums = [1,3,5,6], target = 5

Output: 2


**Example 2:**

Input: nums = [1,3,5,6], target = 2

Output: 1


**Example 3:**

Input: nums = [1,3,5,6], target = 7

Output: 4


**Constraints:**
- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` contains distinct values sorted in ascending order.
- `-10^4 <= target <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Array is sorted and contains distinct integers
- Need to find target or insertion position
- O(log n) requirement suggests binary search
- Insertion position is the first index where element >= target

### Key Insights
1. **Binary Search**: Perfect for sorted arrays with O(log n) complexity
2. **Insertion Position**: The index where target should be inserted to maintain sorted order
3. **Left Boundary**: When target not found, left pointer indicates insertion position
4. **Edge Cases**: Target smaller than all, larger than all, or equal to existing element

### Approach Selection
**Chosen Approach:** Binary Search  
**Why this approach?** 
- O(log n) time complexity meets requirements
- Simple and efficient implementation
- Naturally handles both search and insertion position
- Well-established algorithm for sorted arrays

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) - Binary search halves search space each iteration
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to understand that when binary search ends, the left pointer indicates the insertion position

Standard binary search with careful handling of the insertion position calculation

Works for both existing elements and insertion positions

🔗 Related Problems
Binary Search

Find First and Last Position of Element in Sorted Array

First Bad Version

Guess Number Higher or Lower
