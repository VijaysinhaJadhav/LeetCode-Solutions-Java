# 215. Kth Largest Element in an Array

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Divide and Conquer, Sorting, Heap (Priority Queue), Quickselect  
**Companies:** Amazon, Microsoft, Google, Facebook, Apple, Bloomberg, Uber, Oracle, Adobe

[LeetCode Link](https://leetcode.com/problems/kth-largest-element-in-an-array/)

Given an integer array `nums` and an integer `k`, return the `kth` largest element in the array.

Note that it is the `kth` largest element in the sorted order, not the `kth` distinct element.

Can you solve it without sorting?

**Example 1:**

Input: nums = [3,2,1,5,6,4], k = 2

Output: 5


**Example 2:**

Input: nums = [3,2,3,1,2,4,5,5,6], k = 4

Output: 4


**Constraints:**
- `1 <= k <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- The kth largest element means the element that would be at position (n-k) in sorted array
- Multiple approaches with different time/space trade-offs
- Can use sorting (simplest), heaps, or quickselect algorithm

### Key Insights
1. **Sorting**: O(n log n) time, O(1) space - Simple but not optimal
2. **Min-Heap**: O(n log k) time, O(k) space - Good for large n, small k
3. **Max-Heap**: O(n log n) time, O(n) space - Less efficient
4. **Quickselect**: O(n) average, O(n²) worst case, O(1) space - Most optimal

### Approach Selection
**Chosen Approach:** Quickselect Algorithm  
**Why this approach?** 
- O(n) average time complexity
- O(1) space complexity (in-place)
- Meets the "without sorting" challenge
- Optimal for this problem

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) average case, O(n²) worst case
- **Space Complexity:** O(1) - In-place partitioning

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Quickselect is like quicksort but only recurses into one partition

Use random pivot selection to avoid worst-case performance

kth largest = (n-k)th smallest when array is 0-indexed

Handle duplicates properly in partitioning

🔗 Related Problems
Top K Frequent Elements

K Closest Points to Origin

Kth Largest Element in a Stream

Kth Smallest Element in a Sorted Matrix

Median of Two Sorted Arrays
