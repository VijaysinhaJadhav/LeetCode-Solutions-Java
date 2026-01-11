# 1353. Maximum Number of Events That Can Be Attended

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Greedy, Sorting, Heap (Priority Queue)  
**Companies:** Google, Amazon, Microsoft, Facebook, Bloomberg, Oracle

[LeetCode Link](https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/)

You are given an array of `events` where `events[i] = [startDayᵢ, endDayᵢ]`. Every event `i` starts at `startDayᵢ` and ends at `endDayᵢ`.

You can attend an event `i` at any day `d` where `startTimeᵢ <= d <= endTimeᵢ`. Notice that you can only attend one event at any time `d`.

Return the **maximum number of events** you can attend.

**Example 1:**

![Example 1](https://assets.leetcode.com/uploads/2020/02/05/e1.png)

Input: events = [[1,2],[2,3],[3,4]]
Output: 3
Explanation: You can attend all the three events.
One way to attend them all is as shown.
Attend the first event on day 1.
Attend the second event on day 2.
Attend the third event on day 3.


**Example 2:**

Input: events = [[1,2],[2,3],[3,4],[1,2]]
Output: 4


**Constraints:**
- `1 <= events.length <= 10^5`
- `events[i].length == 2`
- `1 <= startDayᵢ <= endDayᵢ <= 10^5`

## 🧠 Thought Process

### Problem Understanding
We have events with start and end days. We can attend an event on any day within its interval, but only one event per day. We need to maximize the number of events attended.

### Key Insights
1. **Greedy Approach:** At each day, attend the event that ends earliest among available events
2. **Day-by-Day Processing:** Process days from min to max, adding events as they become available
3. **Priority Queue:** Use a min-heap to always pick the event with earliest end day
4. **Sorting:** Sort events by start day to efficiently find available events

### Approach Selection
**Chosen Approach:** Greedy with Min-Heap and Sorting  
**Why this approach?**
- O(n log n) time complexity
- Handles up to 10^5 events efficiently
- Greedy is optimal for this problem

**Alternative:** Could use interval scheduling with variations, but greedy with heap is most efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) where n is number of events
  - Sorting: O(n log n)
  - Heap operations: O(n log n)
- **Space Complexity:** O(n) for storing events and heap

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Greedy works because attending the event that ends earliest leaves more days for other events

We need to remove events from heap if they've already expired (end day < current day)

Multiple events can start on the same day, so we need to add all of them to heap

🔗 Related Problems
Minimum Number of Arrows to Burst Balloons

Non-overlapping Intervals

Merge Intervals

Meeting Rooms II

Course Schedule III
