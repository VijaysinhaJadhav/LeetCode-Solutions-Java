# 42. Trapping Rain Water

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Two Pointers, Dynamic Programming, Stack, Monotonic Stack  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe, Oracle

[LeetCode Link](https://leetcode.com/problems/trapping-rain-water/)

Given `n` non-negative integers representing an elevation map where the width of each bar is `1`, compute how much water it can trap after raining.

**Example 1:**

![Rain Water Trapped](https://assets.leetcode.com/uploads/2018/10/22/rainwatertrap.png)

Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]

Output: 6

Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.


**Example 2:**

Input: height = [4,2,0,3,2,5]

Output: 9


**Constraints:**
- `n == height.length`
- `1 <= n <= 2 * 10^4`
- `0 <= height[i] <= 10^5`

## 🧠 Thought Process

### Initial Thoughts
- Need to calculate trapped water between elevation bars
- Water trapped at any position depends on the maximum heights to its left and right
- The water level at position i is determined by min(leftMax, rightMax)
- Actual trapped water = min(leftMax, rightMax) - height[i] (if positive)

### Key Insights
1. **Two Pointer Approach**: Most efficient O(n) time, O(1) space solution
2. **Dynamic Programming**: Precompute left and right max arrays
3. **Stack Approach**: Use monotonic stack to track decreasing sequences
4. **Brute Force**: For each position, find left and right max (O(n²) time)

### Approach Selection
**Chosen Approach:** Two Pointers  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Elegant and efficient
- Most optimal for interviews

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Water trapped at position i = min(max_left, max_right) - height[i]

Two pointers move towards each other, tracking max from both ends

We always process the side with smaller current max height

The approach ensures we never miss any trapped water

🔗 Related Problems
Container With Most Water

Trapping Rain Water II

Product of Array Except Self

Largest Rectangle in Histogram
