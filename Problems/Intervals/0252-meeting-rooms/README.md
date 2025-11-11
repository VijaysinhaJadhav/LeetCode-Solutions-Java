# 252. Meeting Rooms

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Sorting, Intervals  
**Companies:** Facebook, Google, Microsoft, Amazon, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/meeting-rooms/)

Given an array of meeting time intervals `intervals` where `intervals[i] = [starti, endi]`, determine if a person could attend all meetings.

**Example 1:**

Input: intervals = [[0,30],[5,10],[15,20]]

Output: false

Explanation: The person cannot attend all meetings because there are overlapping meetings.


**Example 2:**

Input: intervals = [[7,10],[2,4]]

Output: true

Explanation: The person can attend all meetings because no meetings overlap.


**Constraints:**
- `0 <= intervals.length <= 10^4`
- `intervals[i].length == 2`
- `0 <= starti < endi <= 10^6`

## 🧠 Thought Process

### Initial Thoughts
- Need to check if any two meetings overlap
- If any meeting overlaps with another, return false
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Sorting approach**: Sort by start time and check adjacent intervals
2. **Overlap condition**: Two intervals overlap if `current.start < previous.end`
3. **Brute force**: Compare every pair of intervals (O(n²) time)
4. **The key insight**: After sorting, we only need to check adjacent intervals

### Approach Selection
**Chosen Approach:** Sort and Check Adjacent Intervals  
**Why this approach?** 
- O(n log n) time complexity
- O(1) extra space (excluding sorting)
- Simple and efficient
- Easy to understand and implement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Dominated by sorting
- **Space Complexity:** O(log n) - For sorting algorithm (O(1) extra space)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The problem reduces to checking if any two intervals overlap

After sorting by start time, we only need to check if intervals[i].start < intervals[i-1].end

This works because if interval i doesn't overlap with i-1, it cannot overlap with any interval before i-1

Meetings that end exactly when another starts are considered non-overlapping

🔗 Related Problems
Merge Intervals

Insert Interval

Meeting Rooms II

Non-overlapping Intervals

Minimum Number of Arrows to Burst Balloons

Maximum Length of Pair Chain

My Calendar I
