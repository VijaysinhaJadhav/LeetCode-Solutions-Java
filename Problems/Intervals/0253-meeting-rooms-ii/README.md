# 253. Meeting Rooms II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Greedy, Sorting, Heap (Priority Queue), Prefix Sum, Intervals  
**Companies:** Facebook, Google, Microsoft, Amazon, Apple, Bloomberg, Uber, LinkedIn, Twitter

[LeetCode Link](https://leetcode.com/problems/meeting-rooms-ii/)

Given an array of meeting time intervals `intervals` where `intervals[i] = [starti, endi]`, return the minimum number of conference rooms required.

**Example 1:**

Input: intervals = [[0,30],[5,10],[15,20]]

Output: 2

Explanation: We need two meeting rooms:

Room 1: [0,30]

Room 2: [5,10],[15,20]


**Example 2:**

Input: intervals = [[7,10],[2,4]]

Output: 1

Explanation: Only one meeting room is needed.


**Constraints:**
- `1 <= intervals.length <= 10^4`
- `0 <= starti < endi <= 10^6`

## 🧠 Thought Process

### Initial Thoughts
- Need to find the maximum number of overlapping meetings at any time
- This determines the minimum number of rooms required
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Chronological Ordering**: Process start and end times separately
2. **Min Heap**: Track ongoing meetings and assign rooms greedily
3. **Sweep Line Algorithm**: Count room usage as we scan through time
4. **The key insight**: The maximum overlap occurs at some meeting start time

### Approach Selection
**Chosen Approach:** Min Heap (Priority Queue)  
**Why this approach?** 
- O(n log n) time complexity
- O(n) space complexity
- Intuitive and efficient
- Most commonly expected in interviews

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Sorting + heap operations
- **Space Complexity:** O(n) - For the heap in worst case

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The problem is equivalent to finding the maximum number of overlapping intervals

Min heap tracks the end times of currently ongoing meetings

When a new meeting starts, if earliest ending meeting has finished, we can reuse that room

Otherwise, we need a new room

🔗 Related Problems
Merge Intervals

Insert Interval

Meeting Rooms

Non-overlapping Intervals

Minimum Number of Arrows to Burst Balloons

My Calendar I

My Calendar II

My Calendar III




