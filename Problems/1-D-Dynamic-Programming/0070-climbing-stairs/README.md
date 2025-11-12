# 70. Climbing Stairs

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Math, Dynamic Programming, Memoization  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/climbing-stairs/)

You are climbing a staircase. It takes `n` steps to reach the top.

Each time you can either climb `1` or `2` steps. In how many distinct ways can you climb to the top?

**Example 1:**

Input: n = 2

Output: 2

Explanation: There are two ways to climb to the top.

1 step + 1 step

2 steps


**Example 2:**

Input: n = 3

Output: 3

Explanation: There are three ways to climb to the top.

1 step + 1 step + 1 step

1 step + 2 steps

2 steps + 1 step


**Constraints:**
- `1 <= n <= 45`

## 🧠 Thought Process

### Initial Thoughts
- This is a classic dynamic programming problem
- Similar to Fibonacci sequence
- Multiple approaches: DP, recursion with memoization, mathematical

### Key Insights
1. **Dynamic Programming**: The number of ways to reach step n = ways(n-1) + ways(n-2)
2. **Fibonacci Sequence**: The solution follows Fibonacci pattern
3. **The key insight**: At each step, you can come from step n-1 (by taking 1 step) or from step n-2 (by taking 2 steps)
4. **Base cases**: ways(1) = 1, ways(2) = 2

### Approach Selection
**Chosen Approach:** Dynamic Programming (Bottom-up)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Efficient and simple
- Most practical for this problem

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through steps 3 to n
- **Space Complexity:** O(1) - Only constant extra space needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
This is essentially the Fibonacci sequence shifted by one position

The recurrence relation is: dp[i] = dp[i-1] + dp[i-2]

Can be solved with constant space using two variables

Mathematical formula exists using Binet's formula but may have precision issues

🔗 Related Problems
Fibonacci Number

Min Cost Climbing Stairs

N-th Tribonacci Number

House Robber

Coin Change
