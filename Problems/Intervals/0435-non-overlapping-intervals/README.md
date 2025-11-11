# 435. Non-overlapping Intervals

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming, Greedy, Sorting, Intervals  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/non-overlapping-intervals/)

Given an array of intervals `intervals` where `intervals[i] = [starti, endi]`, return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

**Note:** You may assume the interval's end point is always greater than its start point. Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.

**Example 1:**

Input: intervals = [[1,2],[2,3],[3,4],[1,3]]

Output: 1

Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.


**Example 2:**

Input: intervals = [[1,2],[1,2],[1,2]]

Output: 2

Explanation: You need to remove two [1,2] to make the rest of the intervals non-overlapping.


**Example 3:**

Input: intervals = [[1,2],[2,3]]

Output: 0

Explanation: You don't need to remove any of the intervals since they're already non-overlapping.


**Constraints:**
- `1 <= intervals.length <= 10^5`
- `intervals[i].length == 2`
- `-5 * 10^4 <= starti < endi <= 5 * 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to find minimum intervals to remove = maximum intervals we can keep
- This is equivalent to finding the maximum set of non-overlapping intervals
- Multiple approaches: greedy by end time, dynamic programming, etc.

### Key Insights
1. **Greedy by end time**: Sort by end time and always pick the interval that ends earliest
2. **Maximum non-overlapping intervals**: The problem reduces to finding the maximum number of non-overlapping intervals
3. **Overlap condition**: Two intervals overlap if `current.start < previous.end`
4. **Greedy choice**: Always pick the interval that ends earliest to maximize remaining space

### Approach Selection
**Chosen Approach:** Greedy - Sort by End Time  
**Why this approach?** 
- O(n log n) time complexity
- O(1) extra space (excluding sorting)
- Optimal greedy solution
- Clear and efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Dominated by sorting
- **Space Complexity:** O(log n) - For sorting algorithm (O(1) extra space)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The problem is equivalent to finding the maximum number of non-overlapping intervals

Minimum removals = Total intervals - Maximum non-overlapping intervals

Sorting by end time ensures we always pick the interval that frees up space earliest

This is a classic interval scheduling problem

🔗 Related Problems
Merge Intervals

Insert Interval

Meeting Rooms

Meeting Rooms II

Minimum Number of Arrows to Burst Balloons

Maximum Length of Pair Chain

Set Intersection Size At Least Two
