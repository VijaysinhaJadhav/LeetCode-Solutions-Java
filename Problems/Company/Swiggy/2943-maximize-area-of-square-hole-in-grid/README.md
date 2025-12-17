# 2943. Maximize Area of Square Hole in Grid

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Sorting, Greedy  
**Companies:** Google, Meta, Amazon

[LeetCode Link](https://leetcode.com/problems/maximize-area-of-square-hole-in-grid/)

There is a grid with `n + 2` horizontal bars and `m + 2` vertical bars, forming `(n + 1) x (m + 1)` 1 x 1 unit cells.

You are given two arrays:
- `hBars`: positions of horizontal bars to remove (0-indexed)
- `vBars`: positions of vertical bars to remove (0-indexed)

After removing the specified bars, return the **maximum area** of a square hole that can be achieved in the grid.

**Example 1:**

Input: n = 2, m = 1, hBars = [2,3], vBars = [2]

Output: 9

Explanation:

Remove horizontal bars at positions 2 and 3: creates a 3x3 hole

Remove vertical bar at position 2

Maximum square hole area is 3x3 = 9


**Example 2:**

Input: n = 1, m = 1, hBars = [2], vBars = [2]

Output: 4

Explanation:

Remove horizontal bar at position 2: creates 2 consecutive gaps

Remove vertical bar at position 2

Maximum square hole area is 2x2 = 4


**Example 3:**

Input: n = 2, m = 3, hBars = [2,3], vBars = [2,3,4]

Output: 9

Explanation:

Remove horizontal bars at positions 2 and 3

Remove vertical bars at positions 2, 3, 4

Maximum square hole area is 3x3 = 9


**Constraints:**
- `1 <= n, m <= 10^9`
- `1 <= hBars.length <= 10^5`
- `1 <= vBars.length <= 10^5`
- `1 <= hBars[i] <= n + 1`
- `1 <= vBars[i] <= m + 1`
- All values in `hBars` and `vBars` are unique

## 🧠 Thought Process

### Initial Thoughts
- We have a grid with horizontal and vertical bars
- Removing bars creates gaps (consecutive removed bars create larger gaps)
- Need to find maximum square hole area
- Square hole requires equal gap lengths in both horizontal and vertical directions
- The area is side_length²

### Key Insights
1. **Consecutive Removed Bars**: Consecutive removed bars create larger gaps
2. **Gap Calculation**: 
   - For horizontal: gap = consecutive horizontal bars removed + 1
   - For vertical: gap = consecutive vertical bars removed + 1
3. **Maximum Square**: We need the minimum of maximum horizontal gap and maximum vertical gap
4. **Area Calculation**: Area = min(maxHGap, maxVGap)²

### Approach Selection
**Chosen Approach:** Sorting and Counting Consecutive Elements  
**Why this approach?** 
- O(k log k) time where k = hBars.length + vBars.length
- O(1) space (excluding sorting space)
- Simple and efficient
- Handles up to 10^5 bars

## ⚡ Complexity Analysis
- **Time Complexity:** O(h log h + v log v) - sorting both arrays
- **Space Complexity:** O(1) - excluding sorting space (O(log n) for Java's sort)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort both arrays to find consecutive sequences

Count longest consecutive sequence in each array

Square side = min(longest horizontal consecutive, longest vertical consecutive) + 1

Area = side²

🔗 Related Problems
Longest Consecutive Sequence

Longest Increasing Subsequence

Number of Sub-arrays With Odd Sum

Maximum Erasure Value
