# 122. Best Time to Buy and Sell Stock II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming, Greedy  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/)

You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `i-th` day.

On each day, you may decide to buy and/or sell the stock. You can only hold **at most one** share of the stock at any time. However, you can buy it then immediately sell it on the **same day**.

Find and return the **maximum** profit you can achieve.

**Example 1:**

Input: prices = [7,1,5,3,6,4]

Output: 7

Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.

Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.

Total profit is 4 + 3 = 7.


**Example 2:**

Input: prices = [1,2,3,4,5]

Output: 4

Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.

Total profit is 4.


**Example 3:**

Input: prices = [7,6,4,3,1]

Output: 0

Explanation: There is no way to make a positive profit, so we never buy, profit = 0.


**Constraints:**
- `1 <= prices.length <= 3 * 10^4`
- `0 <= prices[i] <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- We can buy and sell multiple times
- We can only hold one share at a time
- We need to maximize total profit across all transactions
- Multiple approaches with different complexities

### Key Insights
1. **Greedy Approach**: Sum all positive price differences between consecutive days
2. **Peak-Valley Approach**: Buy at valleys, sell at peaks
3. **Dynamic Programming**: Track maximum profit with state machine
4. **The key insight**: We can capture all upward movements in the price chart

### Approach Selection
**Chosen Approach:** Greedy (One Pass)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Simple and intuitive
- Optimal for this problem

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only using constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The greedy approach works because we can capture every price increase

We don't need to track actual buy/sell points, just the total profit

The solution automatically handles the case where we should hold through multiple up days

This approach is equivalent to buying at every local minimum and selling at every local maximum

🔗 Related Problems
121. Best Time to Buy and Sell Stock

123. Best Time to Buy and Sell Stock III

188. Best Time to Buy and Sell Stock IV

309. Best Time to Buy and Sell Stock with Cooldown
