# 188. Best Time to Buy and Sell Stock IV

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Dynamic Programming  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/)

You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `i`-th day, and an integer `k`.

Find the **maximum profit** you can achieve. You may complete at most `k` transactions.

**Note:** You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

**Example 1:**

Input: k = 2, prices = [2,4,1]
Output: 2
Explanation: Buy on day 1 (price 2) and sell on day 2 (price 4), profit = 2.


**Example 2:**

Input: k = 2, prices = [3,2,6,5,0,3]
Output: 7
Explanation: Buy on day 2 (price 2) and sell on day 3 (price 6), profit = 4.
Then buy on day 5 (price 0) and sell on day 6 (price 3), profit = 3.
Total profit = 7.


**Constraints:**
- `0 <= k <= 100`
- `0 <= prices.length <= 1000`
- `0 <= prices[i] <= 1000`

## 🧠 Thought Process

### Problem Understanding
We can make at most `k` transactions (buy-sell pairs) to maximize profit. Each transaction must be completed before the next begins.

### Key Insights
1. **DP State**: `dp[t][i]` = max profit with at most `t` transactions using first `i` days
2. **Transition**: Either skip day `i` or sell on day `i` after buying on some earlier day `j`
3. **Optimization**: `dp[t][i] = max(dp[t][i-1], prices[i] + max_diff)` where `max_diff` tracks best buy opportunity
4. **Special Case**: If `k >= n/2`, we can do unlimited transactions (greedy approach)

### Approach Selection
**Chosen Approach:** Dynamic Programming with Optimization  
**Why this approach?**
- O(k × n) time complexity
- O(n) space complexity with optimization
- Handles all constraints efficiently

**Alternative Approaches:**
- **Recursive DP with Memoization**: O(k × n²) time
- **Greedy for k ≥ n/2**: O(n) time

## ⚡ Complexity Analysis
- **Time Complexity:** O(k × n) where n = number of days
- **Space Complexity:** O(n) for optimized DP, O(k × n) for full DP

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
If k >= n/2, we can make unlimited transactions → sum all positive price increases

Use max_diff to track the best profit from previous transactions

Can optimize space to O(n) by reusing arrays

Handle edge cases: k = 0, prices.length = 0

🔗 Related Problems
Best Time to Buy and Sell Stock (k = 1)
Best Time to Buy and Sell Stock II (unlimited)
Best Time to Buy and Sell Stock III (k = 2)
Best Time to Buy and Sell Stock IV (this problem)
Best Time to Buy and Sell Stock with Cooldown
