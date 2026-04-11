# 162. Find Peak Element

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/find-peak-element/)

A peak element is an element that is **strictly greater** than its neighbors.

Given a **0-indexed** integer array `nums`, find a peak element, and return its index. If the array contains multiple peaks, return the index to **any of the peaks**.

You may imagine that `nums[-1] = nums[n] = -∞`. In other words, an element is always considered to be greater than a neighbor that is outside the array.

You must write an algorithm that runs in **O(log n)** time.

**Example 1:**

Input: nums = [1,2,3,1]
Output: 2
Explanation: 3 is a peak element and your function should return the index number 2.


**Example 2:**

Input: nums = [1,2,1,3,5,6,4]
Output: 5
Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.


**Constraints:**
- `1 <= nums.length <= 1000`
- `-2^31 <= nums[i] <= 2^31 - 1`
- `nums[i] != nums[i+1]` for all valid `i` (no adjacent equal elements)

## 🧠 Thought Process

### Problem Understanding
We need to find any index where the element is greater than both its neighbors. The array has no adjacent equal elements.

### Key Insights
1. **Binary Search Applicability**: The array is not sorted, but we can still use binary search because of the peak property
2. **Monotonic Property**: If `nums[mid] < nums[mid+1]`, a peak exists to the right; otherwise, a peak exists to the left
3. **Guaranteed Peak**: Since boundaries are considered -∞, a peak always exists

### Approach Selection
**Chosen Approach:** Binary Search  
**Why this approach?**
- O(log n) time complexity
- O(1) space complexity
- Elegant and efficient

**Alternative Approaches:**
- **Linear Scan**: O(n) time, simple but slower
- **Recursive Binary Search**: Same complexity, more intuitive

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) where n = length of array
- **Space Complexity:** O(1) for iterative, O(log n) for recursive

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Compare nums[mid] with nums[mid+1] to decide which side to search

If nums[mid] < nums[mid+1], peak is in the right half

Otherwise, peak is in the left half (including mid)

The array always has at least one peak due to boundary conditions

🔗 Related Problems
Peak Index in a Mountain Array
Find in Mountain Array
Find a Peak Element II (2D version)
Find Peak Element (this problem)
