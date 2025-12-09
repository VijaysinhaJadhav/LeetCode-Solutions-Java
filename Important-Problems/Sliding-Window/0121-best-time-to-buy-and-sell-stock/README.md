# 121. Best Time to Buy and Sell Stock

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Dynamic Programming, Sliding Window  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)

You are given an array `prices` where `prices[i]` is the price of a given stock on the `i-th` day.

You want to maximize your profit by choosing a **single day** to buy one stock and choosing a **different day in the future** to sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return `0`.

**Example 1:**

Input: prices = [7,1,5,3,6,4]

Output: 5

Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.

Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.


**Example 2:**

Input: prices = [7,6,4,3,1]

Output: 0

Explanation: In this case, no transactions are done and the max profit = 0.


**Constraints:**
- `1 <= prices.length <= 10^5`
- `0 <= prices[i] <= 10^4`

**Follow-up:** Can you come up with a solution that runs in O(n) time?

## 🧠 Thought Process

### Initial Thoughts
- Need to find the maximum difference between two prices where the buying price comes before the selling price
- Multiple approaches with different time/space trade-offs
- The challenge is to find the optimal buy-sell pair in linear time

### Key Insights
1. **One Pass Solution**: Track the minimum price seen so far and calculate potential profit
2. **Kadane's Algorithm**: Can be adapted for this problem by calculating differences
3. **Dynamic Programming**: Maintain state for minimum price and maximum profit
4. **The key insight**: For each day, the maximum profit is the current price minus the minimum price seen so far

### Approach Selection
**Chosen Approach:** One Pass with Minimum Tracking  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Simple and intuitive
- Meets follow-up requirement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The one pass solution is optimal for this problem

We only need to track the minimum price and maximum profit

The algorithm ensures we always buy before selling

If no profit is possible, we return 0

🔗 Related Problems
Best Time to Buy and Sell Stock II

Best Time to Buy and Sell Stock III

Best Time to Buy and Sell Stock IV

Best Time to Buy and Sell Stock with Cooldown

Best Time to Buy and Sell Stock with Transaction Fee
