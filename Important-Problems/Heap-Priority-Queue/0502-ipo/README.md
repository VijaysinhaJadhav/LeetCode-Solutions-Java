# 502. IPO

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Greedy, Sorting, Heap (Priority Queue)  
**Companies:** Google, Amazon, Microsoft, Meta, Bloomberg

[LeetCode Link](https://leetcode.com/problems/ipo/)

Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources, it can only finish at most `k` distinct projects before the IPO. Help LeetCode design the best way to maximize its total capital after finishing at most `k` distinct projects.

You are given `n` projects where the `i-th` project has a pure profit `profits[i]` and a minimum capital of `capital[i]` is needed to start it.

Initially, you have `w` capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital.

Pick a list of **at most** `k` distinct projects from given projects to maximize your final capital, and return the final maximized capital.

The answer is guaranteed to fit in a 32-bit signed integer.

**Example 1:**

Input: k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]

Output: 4

Explanation: Since your initial capital is 0, you can only start the project indexed 0.

After finishing it you will obtain profit 1 and your capital becomes 1.

With capital 1, you can either start the project indexed 1 or the project indexed 2.

Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.

Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.


**Example 2:**

Input: k = 3, w = 0, profits = [1,2,3], capital = [0,1,2]

Output: 6


**Constraints:**
- `1 <= k <= 10^5`
- `0 <= w <= 10^9`
- `n == profits.length`
- `n == capital.length`
- `1 <= n <= 10^5`
- `0 <= profits[i] <= 10^4`
- `0 <= capital[i] <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- Need to select up to k projects to maximize final capital
- Each project requires minimum capital to start and gives profit
- After completing a project, capital increases by profit
- Greedy approach: always pick available project with maximum profit

### Key Insights
1. **Two-Phase Greedy**:
   - Sort projects by required capital
   - Use max heap for available projects (those we can afford)
   - At each step: add all affordable projects to heap, pick most profitable

2. **Efficiency**:
   - Sorting: O(n log n)
   - Heap operations: O(k log n) where k ≤ n
   - Total: O(n log n + k log n)

3. **Optimization**:
   - Only need to process each project once
   - Use two-pointer technique after sorting

### Approach Selection
**Chosen Approach:** Sorting + Max Heap  
**Why this approach?** 
- O(n log n) time complexity
- Intuitive greedy algorithm
- Handles all constraints efficiently
- Optimal greedy choice at each step

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n + k log n) ≈ O((n + k) log n)
- **Space Complexity:** O(n) for storing projects and heap

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort projects by capital requirement

Use max heap to always pick highest profit from affordable projects

After each project, capital increases, making more projects affordable

Continue until k projects done or no affordable projects left

🔗 Related Problems
Course Schedule III

Maximum Number of Events That Can Be Attended

Furthest Building You Can Reach

Minimum Number of Refueling Stops
