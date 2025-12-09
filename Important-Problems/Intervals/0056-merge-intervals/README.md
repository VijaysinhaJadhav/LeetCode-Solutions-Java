# 56. Merge Intervals

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Sorting, Intervals  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn, Oracle, Twitter

[LeetCode Link](https://leetcode.com/problems/merge-intervals/)

Given an array of `intervals` where `intervals[i] = [starti, endi]`, merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.

**Example 1:**

Input: intervals = [[1,3],[2,6],[8,10],[15,18]]

Output: [[1,6],[8,10],[15,18]]

Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].


**Example 2:**

Input: intervals = [[1,4],[4,5]]

Output: [[1,5]]

Explanation: Intervals [1,4] and [4,5] are considered overlapping.


**Constraints:**
- `1 <= intervals.length <= 10^4`
- `intervals[i].length == 2`
- `0 <= starti <= endi <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to merge overlapping intervals into consolidated intervals
- Intervals may not be sorted initially
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Sorting first**: Sort intervals by start time to make merging easier
2. **Merge condition**: Two intervals overlap if `current.start <= previous.end`
3. **Merge strategy**: When overlapping, take `min(start)` and `max(end)`
4. **Greedy approach**: Process intervals in sorted order and merge as we go

### Approach Selection
**Chosen Approach:** Sort and Linear Merge  
**Why this approach?** 
- O(n log n) time complexity (due to sorting)
- O(n) space complexity (for result)
- Clear and intuitive logic
- Handles all cases efficiently

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Dominated by sorting
- **Space Complexity:** O(n) - For the result list (O(log n) for sorting algorithm)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sorting by start time ensures we can merge in a single pass

Two intervals [a,b] and [c,d] overlap if a <= d and c <= b

When merging, we extend the end time to the maximum of both intervals

The greedy approach works because we always merge with the last interval in result

🔗 Related Problems
Insert Interval

Meeting Rooms

Meeting Rooms II

Teemo Attacking

Add Bold Tag in String

Range Module

Employee Free Time

Partition Labels
