# 309. Best Time to Buy and Sell Stock with Cooldown

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming, State Machine  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

You are given an array `prices` where `prices[i]` is the price of a given stock on the `i-th` day.

Find the maximum profit you can achieve. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times) with the following restrictions:
- After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day).

**Note:** You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

**Example 1:**

Input: prices = [1,2,3,0,2]

Output: 3

Explanation: transactions = [buy, sell, cooldown, buy, sell]


**Example 2:**

Input: prices = [1]

Output: 0


**Constraints:**
- `1 <= prices.length <= 5000`
- `0 <= prices[i] <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Need to maximize profit with cooldown constraint
- Cannot buy immediately after selling
- Can use dynamic programming with state tracking
- Three possible states: hold, sold, rest

### Key Insights
1. **State Machine Approach**: Track three states - hold, sold, rest
2. **State Transitions**:
   - hold: max(previous hold, buy from rest state)
   - sold: sell from hold state
   - rest: max(previous rest, previous sold)
3. **Dynamic Programming**: Use DP arrays for each state
4. **Space Optimization**: Can use variables instead of arrays

### Approach Selection
**Chosen Approach:** State Machine DP with Space Optimization  
**Why this approach?** 
- Clearly models the problem constraints
- O(n) time complexity is optimal
- O(1) space complexity is efficient
- Intuitive state transitions

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through prices
- **Space Complexity:** O(1) - Only three variables needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Three states: hold (own stock), sold (just sold), rest (no stock, can buy)

Initialize hold with -prices[0] (buy on first day)

Final answer is max(sold, rest) on last day

Can be extended to other stock trading problems

🔗 Related Problems
Best Time to Buy and Sell Stock

Best Time to Buy and Sell Stock II

Best Time to Buy and Sell Stock III

Best Time to Buy and Sell Stock IV

Best Time to Buy and Sell Stock with Transaction Fee

text

