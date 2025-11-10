# 739. Daily Temperatures

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Stack, Monotonic Stack  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/daily-temperatures/)

Given an array of integers `temperatures` represents the daily temperatures, return an array `answer` such that `answer[i]` is the number of days you have to wait after the `i-th` day to get a warmer temperature. If there is no future day for which this is possible, put `0` instead.

**Example 1:**

Input: temperatures = [73,74,75,71,69,72,76,73]

Output: [1,1,4,2,1,1,0,0]


**Example 2:**

Input: temperatures = [30,40,50,60]

Output: [1,1,1,0]


**Example 3:**

Input: temperatures = [30,60,90]

Output: [1,1,0]


**Constraints:**
- `1 <= temperatures.length <= 10^5`
- `30 <= temperatures[i] <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Need to find next greater element for each temperature
- Multiple approaches with different time/space trade-offs
- The challenge is to efficiently find warmer days without O(n²) complexity

### Key Insights
1. **Monotonic Stack**: Maintain decreasing temperature indices in stack
2. **Next Greater Element**: Classic problem with stack solution
3. **Backward Processing**: Process from end to beginning for efficient solution
4. **The key insight**: Use stack to track temperatures waiting for warmer days

### Approach Selection
**Chosen Approach:** Monotonic Stack  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Elegant and efficient
- Handles all cases optimally

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each temperature pushed and popped once
- **Space Complexity:** O(n) - Stack storage for indices

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Monotonic stack maintains temperatures in decreasing order

When warmer temperature found, calculate days difference

Each index pushed and popped exactly once

The algorithm efficiently finds next warmer day for each temperature

🔗 Related Problems
Next Greater Element I

Next Greater Element II

Online Stock Span

Largest Rectangle in Histogram

Trapping Rain Water
