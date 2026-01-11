# 540. Single Element in a Sorted Array

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber  

[LeetCode Link](https://leetcode.com/problems/single-element-in-a-sorted-array/)

You are given a sorted array consisting of only integers where every element appears exactly twice, except for one element which appears exactly once.

Return the **single element** that appears only once.

Your solution must run in **O(log n)** time and **O(1)** space.

**Example 1:**

Input: nums = [1,1,2,3,3,4,4,8,8]
Output: 2


**Example 2:**

Input: nums = [3,3,7,7,10,11,11]
Output: 10


**Constraints:**
- `1 <= nums.length <= 10^5`
- `0 <= nums[i] <= 10^5`
- The array is sorted in non-decreasing order.

## 🧠 Thought Process

### Problem Understanding
We have a sorted array where:
- All elements appear exactly twice except one
- Need to find the single element
- Must be O(log n) time and O(1) space
- Array length is odd (2k + 1)

### Key Insights
1. **Sorted Array + O(log n)** → Binary Search
2. **Pattern Recognition**: 
   - In a perfectly paired array, pairs are at indices (even, odd): (0,1), (2,3), etc.
   - Single element disrupts this pattern
3. **Binary Search Approach**:
   - Check middle element
   - Compare with neighbors to determine which side has the single element
   - Move to the side where pattern is broken
4. **Index Parity**:
   - Before single element: pairs are at (even, odd)
   - After single element: pairs are at (odd, even)

### Approach Selection
**Chosen Approach**: Modified Binary Search with Parity Check  
**Why this approach?**
- O(log n) time complexity
- O(1) space complexity
- Leverages sorted property
- Elegant pattern matching

**Alternative Approaches**:
- XOR (O(n) time, doesn't use sorted property)
- Linear scan (O(n) time, too slow for constraints)

## ⚡ Complexity Analysis
- **Time Complexity**: O(log n) - binary search halves search space each iteration
- **Space Complexity**: O(1) - only a few variables used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key insight is that before the single element, pairs start at even indices

After the single element, pairs start at odd indices

We can use this pattern to determine which half to search

Edge cases: single element at beginning or end of array

🔗 Related Problems
Single Number

Single Number II

Single Number III

Find the Duplicate Number

Find Minimum in Rotated Sorted Array

Find Peak Element
