# 901. Online Stock Span

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Stack, Design, Monotonic Stack, Data Stream  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/online-stock-span/)

Design an algorithm that collects daily price quotes for some stock and returns the span of that stock's price for the current day.

The span of the stock's price in one day is the maximum number of consecutive days (starting from that day and going backward) for which the stock price was less than or equal to the price of that day.

- For example, if the prices of the stock in the last four days is `[7,2,1,2]` and the price of the stock today is `2`, then the span of today is `4` because starting from today, the price of the stock was less than or equal `2` for `4` consecutive days.
- Also, if the prices of the stock in the last four days is `[7,34,1,2]` and the price of the stock today is `8`, then the span of today is `3` because starting from today, the price of the stock was less than or equal `8` for `3` consecutive days.

Implement the `StockSpanner` class:
- `StockSpanner()` Initializes the object of the class.
- `int next(int price)` Returns the span of the stock's price given that today's price is `price`.

**Example 1:**

Input

["StockSpanner", "next", "next", "next", "next", "next", "next", "next"]

[[], [100], [80], [60], [70], [60], [75], [85]]

Output

[null, 1, 1, 1, 2, 1, 4, 6]

Explanation

StockSpanner stockSpanner = new StockSpanner();

stockSpanner.next(100); // return 1

stockSpanner.next(80); // return 1

stockSpanner.next(60); // return 1

stockSpanner.next(70); // return 2

stockSpanner.next(60); // return 1

stockSpanner.next(75); // return 4

stockSpanner.next(85); // return 6


**Constraints:**
- `1 <= price <= 10^5`
- At most `10^4` calls will be made to `next`.

## 🧠 Thought Process

### Initial Thoughts
- Need to calculate consecutive days where price <= current price
- Multiple approaches with different time/space trade-offs
- The challenge is to efficiently compute spans without recalculating

### Key Insights
1. **Monotonic Stack**: Maintain decreasing prices with their spans
2. **Accumulated Spans**: Store precomputed spans for efficient calculation
3. **The key insight**: When prices decrease, span is 1; when increasing, accumulate previous spans

### Approach Selection
**Chosen Approach:** Monotonic Stack with Accumulated Spans  
**Why this approach?** 
- O(1) amortized time per operation
- O(n) space complexity
- Elegant and efficient
- Handles all cases optimally

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) amortized per next() call
- **Space Complexity:** O(n) - Stack storage for prices and spans
- **Amortized Analysis:** Each price pushed and popped once

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Monotonic stack maintains prices in decreasing order

Each stack element stores (price, span) for efficient calculation

When higher price encountered, pop and accumulate spans

The algorithm efficiently computes spans without recalculating

🔗 Related Problems
Daily Temperatures

Next Greater Element I

Next Greater Element II

Largest Rectangle in Histogram

Sum of Subarray Minimums
