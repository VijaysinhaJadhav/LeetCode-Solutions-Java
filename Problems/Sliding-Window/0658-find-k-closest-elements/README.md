# 658. Find K Closest Elements

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Binary Search, Sliding Window, Sorting, Heap (Priority Queue)  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/find-k-closest-elements/)

Given a **sorted** integer array `arr`, two integers `k` and `x`, return the `k` closest integers to `x` in the array. The result should also be sorted in ascending order.

An integer `a` is closer to `x` than an integer `b` if:
- `|a - x| < |b - x|`, or
- `|a - x| == |b - x|` and `a < b`

**Example 1:**

Input: arr = [1,2,3,4,5], k = 4, x = 3

Output: [1,2,3,4]


**Example 2:**

Input: arr = [1,2,3,4,5], k = 4, x = -1

Output: [1,2,3,4]


**Constraints:**
- `1 <= k <= arr.length`
- `1 <= arr.length <= 10^4`
- `arr` is sorted in **ascending order**.
- `-10^4 <= arr[i], x <= 10^4`

**Follow-up:** Can you solve it in O(log n + k) time?

## 🧠 Thought Process

### Initial Thoughts
- Need to find k closest elements to x in a sorted array
- Multiple approaches with different time/space trade-offs
- The sorted property enables efficient searching

### Key Insights
1. **Binary Search + Two Pointers**: Find the closest element, then expand left and right
2. **Sliding Window**: Maintain a window of size k and slide it to find optimal position
3. **Binary Search for Left Bound**: Find the left boundary of the result window
4. **Heap Approach**: Use max heap to maintain k closest elements

### Approach Selection
**Chosen Approach:** Binary Search for Left Bound  
**Why this approach?** 
- O(log n + k) time complexity
- O(1) space complexity (excluding output)
- Efficient and elegant
- Meets follow-up requirement

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n + k) - Binary search + building result
- **Space Complexity:** O(1) - Only constant extra space (excluding output)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The array is sorted, which enables binary search

We need to handle ties where distances are equal (choose smaller element)

The result must be sorted in ascending order

Multiple efficient approaches exist

🔗 Related Problems
Find First and Last Position of Element in Sorted Array

Search Insert Position

Binary Search

K Closest Points to Origin

The k Strongest Values in an Array

