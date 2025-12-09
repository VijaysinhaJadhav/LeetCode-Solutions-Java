# 57. Insert Interval

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Intervals, Sorting  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/insert-interval/)

You are given an array of non-overlapping intervals `intervals` where `intervals[i] = [starti, endi]` represent the start and end of the ith interval and `intervals` is sorted in ascending order by `starti`. You are also given an interval `newInterval = [start, end]` that represents the start and end of another interval.

Insert `newInterval` into `intervals` such that `intervals` is still sorted in ascending order by `starti` and `intervals` still does not have any overlapping intervals (merge overlapping intervals if necessary).

**Example 1:**

Input: intervals = [[1,3],[6,9]], newInterval = [2,5]

Output: [[1,5],[6,9]]


**Example 2:**

Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]

Output: [[1,2],[3,10],[12,16]]

Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].


**Constraints:**
- `0 <= intervals.length <= 10^4`
- `intervals[i].length == 2`
- `0 <= starti <= endi <= 10^5`
- `intervals` is sorted by `starti` in ascending order.
- `newInterval.length == 2`
- `0 <= start <= end <= 10^5`

## 🧠 Thought Process

### Initial Thoughts
- We need to insert a new interval into a sorted list of non-overlapping intervals
- The result must maintain the sorted order and merge any overlapping intervals
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Three-phase approach**: 
   - Add all intervals that end before new interval starts
   - Merge all overlapping intervals with new interval
   - Add all intervals that start after new interval ends

2. **Binary Search**: Can find insertion point efficiently
3. **The key insight**: Only intervals that overlap with new interval need to be merged

### Approach Selection
**Chosen Approach:** Three-phase linear scan  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity (for result)
- Clear and intuitive logic
- Handles all edge cases well

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(n) - For the result list (O(1) extra space excluding output)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The intervals are already sorted, so we can process them in order

Only intervals that overlap with new interval need merging

An interval overlaps if its start <= newInterval's end AND its end >= newInterval's start

The merged interval takes min(start) and max(end) of all overlapping intervals

🔗 Related Problems
Merge Intervals

Meeting Rooms

Meeting Rooms II

Teemo Attacking

Add Bold Tag in String

Range Module

Employee Free Time
