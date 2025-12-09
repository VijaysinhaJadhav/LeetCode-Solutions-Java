# 84. Largest Rectangle in Histogram

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Stack, Monotonic Stack  
**Companies:** Amazon, Google, Microsoft, Apple, Facebook, Bloomberg, Uber, Adobe, Oracle

[LeetCode Link](https://leetcode.com/problems/largest-rectangle-in-histogram/)

Given an array of integers `heights` representing the histogram's bar height where the width of each bar is `1`, return the area of the largest rectangle in the histogram.

**Example 1:**

![Histogram](https://assets.leetcode.com/uploads/2021/01/04/histogram.jpg)

Input: heights = [2,1,5,6,2,3]

Output: 10

Explanation: The above is a histogram where width of each bar is 1.

The largest rectangle is shown in the red area, which has an area = 10 units.


**Example 2:**

![Histogram2](https://assets.leetcode.com/uploads/2021/01/04/histogram-1.jpg)

Input: heights = [2,4]

Output: 4


**Constraints:**
- `1 <= heights.length <= 10^5`
- `0 <= heights[i] <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to find the maximum rectangular area that can be formed in a histogram
- The area for bar i = height[i] * width, where width depends on how many consecutive bars have height >= height[i]
- Brute force would be O(n²) - for each bar, find left and right boundaries

### Key Insights
1. **Monotonic Stack**: Maintain a stack of indices with increasing heights
2. **Left and Right Boundaries**: For each bar, find the first smaller bar on left and right
3. **Area Calculation**: width = right_boundary - left_boundary - 1
4. **Sentinel Values**: Add 0-height bars at boundaries to handle edge cases

### Approach Selection
**Chosen Approach:** Monotonic Stack  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Elegant solution using stack properties
- Handles all edge cases efficiently

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each element is pushed and popped at most once
- **Space Complexity:** O(n) - Stack storage

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a stack to store indices in increasing height order

When a smaller height is encountered, it defines the right boundary for previous bars

Calculate area when popping from stack

Add sentinel values (0-height) at both ends to ensure all bars get processed

🔗 Related Problems
Maximal Rectangle

Trapping Rain Water

Daily Temperatures

Sum of Subarray Minimums

Container With Most Water


