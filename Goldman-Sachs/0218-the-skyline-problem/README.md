# 218. The Skyline Problem

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Divide and Conquer, Heap (Priority Queue), Segment Tree, Sweep Line  
**Companies:** Google, Facebook, Microsoft, Amazon, Apple, Uber  

[LeetCode Link](https://leetcode.com/problems/the-skyline-problem/)

A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Given the locations and heights of all the buildings, return the skyline formed by these buildings collectively.

Each building is represented by a triple `[left, right, height]` where:
- `left` is the x-coordinate of the building's left edge
- `right` is the x-coordinate of the building's right edge
- `height` is the height of the building

The skyline should be returned as a list of `[x, y]` points representing the **key points** of the skyline. A key point is the left endpoint of a horizontal segment. The output list must be sorted by x-coordinate.

**Example 1:**

![Skyline Example](https://assets.leetcode.com/uploads/2020/12/01/merged.jpg)

Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
Explanation:

Building 1 (2,9,10): starts at x=2 with height 10, ends at x=9

Building 2 (3,7,15): starts at x=3 with height 15, ends at x=7

Building 3 (5,12,12): starts at x=5 with height 12, ends at x=12

Building 4 (15,20,10): starts at x=15 with height 10, ends at x=20

Building 5 (19,24,8): starts at x=19 with height 8, ends at x=24
The skyline: (2,10) → (3,15) → (7,12) → (12,0) → (15,10) → (20,8) → (24,0)


**Example 2:**

Input: buildings = [[0,2,3],[2,5,3]]
Output: [[0,3],[5,0]]


**Constraints:**
- `1 <= buildings.length <= 10^4`
- `0 <= left < right <= 2^31 - 1`
- `1 <= height <= 2^31 - 1`
- `buildings` is sorted by `left` in ascending order.

## 🧠 Thought Process

### Problem Understanding
We need to find the silhouette formed by overlapping rectangles. The skyline changes only at building edges.

### Key Insights
1. **Sweep Line**: Process events sorted by x-coordinate
2. **Event Types**: 
   - Start event: `(x, -height)` - add height to active set
   - End event: `(x, height)` - remove height from active set
3. **Max Height**: At any x, the skyline height is the maximum height of active buildings
4. **Key Points**: When max height changes, record a point

### Approach Selection
**Chosen Approach:** Sweep Line with Max-Heap  
**Why this approach?**
- O(n log n) time complexity
- Clear event-based processing
- Efficient height tracking with priority queue

**Alternative Approaches:**
- **Divide and Conquer**: Merge skylines recursively
- **Segment Tree**: Range maximum updates and queries

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) where n = number of buildings
- **Space Complexity:** O(n) for events and heap

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use negative height for start events to prioritize higher buildings

Use a max-heap (PriorityQueue with reverse order) to track active heights

Lazy removal: track heights to remove with a separate map

Record point when max height changes

🔗 Related Problems
Merge Intervals
Insert Interval
Meeting Rooms II
Employee Free Time
Interval List Intersections


