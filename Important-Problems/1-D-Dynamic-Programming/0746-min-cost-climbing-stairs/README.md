# 746. Min Cost Climbing Stairs

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Dynamic Programming  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/min-cost-climbing-stairs/)

You are given an integer array `cost` where `cost[i]` is the cost of `i-th` step on a staircase. Once you pay the cost, you can either climb one or two steps.

You can either start from the step with index `0`, or the step with index `1`.

Return the minimum cost to reach the top of the floor (beyond the last step).

**Example 1:**

Input: cost = [10,15,20]

Output: 15

Explanation: You will start at index 1.

Pay 15 and climb two steps to reach the top.

The total cost is 15.


**Example 2:**

Input: cost = [1,100,1,1,1,100,1,1,100,1]

Output: 6

Explanation: You will start at index 0.

Pay 1 and climb two steps to reach index 2.

Pay 1 and climb two steps to reach index 4.

Pay 1 and climb two steps to reach index 6.

Pay 1 and climb one step to reach index 7.

Pay 1 and climb two steps to reach index 9.

Pay 1 and climb one step to reach the top.

The total cost is 6.


**Constraints:**
- `2 <= cost.length <= 1000`
- `0 <= cost[i] <= 999`

## 🧠 Thought Process

### Initial Thoughts
- This is a variation of the climbing stairs problem with costs
- Need to find minimum cost path to reach beyond the last step
- Multiple approaches: DP, recursion with memoization

### Key Insights
1. **Dynamic Programming**: The minimum cost to reach step i = min(cost[i-1] + dp[i-1], cost[i-2] + dp[i-2])
2. **The key insight**: At each step, we can come from step i-1 or step i-2, and we pay the cost of the step we're coming from
3. **Base cases**: dp[0] = 0, dp[1] = 0 (we can start from step 0 or 1 without paying)
4. **Goal**: Reach beyond the last step, so we need dp[cost.length]

### Approach Selection
**Chosen Approach:** Dynamic Programming (Bottom-up)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Efficient and simple
- Most practical for this problem

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the cost array
- **Space Complexity:** O(1) - Only constant extra space needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
We can start from step 0 or step 1 without paying their cost initially

The cost is paid when we leave a step, not when we arrive

The goal is to reach beyond the last step (index = cost.length)

This is similar to the climbing stairs problem but with optimization for minimum cost

🔗 Related Problems
Climbing Stairs

House Robber

Paint House

Coin Change

Fibonacci Number
