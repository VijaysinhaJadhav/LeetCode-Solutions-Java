# 1235. Maximum Profit in Job Scheduling

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Binary Search, Dynamic Programming, Sorting  
**Companies:** Google, Amazon, Microsoft, Meta, Bloomberg, Uber, Apple

[LeetCode Link](https://leetcode.com/problems/maximum-profit-in-job-scheduling/)

We have `n` jobs, where every job is scheduled to be done from `startTime[i]` to `endTime[i]`, obtaining a profit of `profit[i]`.

You're given the `startTime`, `endTime`, and `profit` arrays, you need to output the maximum profit you can take such that there are no two jobs in the subset with overlapping time range.

If you choose a job that ends at time `X` you will be able to start another job that starts at time `X`.

**Example 1:**

Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]

Output: 120

Explanation: The subset chosen is the first and fourth job.

Time range [1,3] + [3,6] , we get profit of 120 = 50 + 70.


**Example 2:**

Input: startTime = [1,2,3,4,6], endTime = [3,5,10,6,9], profit = [20,20,100,70,60]

Output: 150

Explanation: The subset chosen is the first, fourth and fifth job.

Profit obtained 150 = 20 + 70 + 60.


**Example 3:**

Input: startTime = [1,1,1], endTime = [2,3,4], profit = [5,6,4]

Output: 6


**Constraints:**
- `1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4`
- `1 <= startTime[i] < endTime[i] <= 10^9`
- `1 <= profit[i] <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to select non-overlapping jobs to maximize total profit
- Similar to weighted interval scheduling problem
- Each job has start time, end time, and profit weight
- Can't select two jobs that overlap in time

### Key Insights
1. **Sorting by End Time**: Sort all jobs by their end time to enable efficient DP
2. **Dynamic Programming Approach**:
   - `dp[i]`: maximum profit considering jobs 0 to i
   - Two choices for each job i:
     - Skip job i: `dp[i-1]`
     - Take job i: `profit[i] + dp[prev]` where `prev` is the last non-overlapping job before i
3. **Binary Search for Efficiency**: Since jobs are sorted by end time, we can use binary search to find the last non-overlapping job efficiently
4. **State Transition**:
   - For each job i, find the largest index j where `endTime[j] <= startTime[i]`
   - `dp[i] = max(dp[i-1], profit[i] + (j >= 0 ? dp[j] : 0))`

### Approach Selection
**Chosen Approach:** Dynamic Programming with Binary Search  
**Why this approach?** 
- O(n log n) time complexity (sorting + DP with binary search)
- O(n) space complexity
- Optimal solution for this weighted interval scheduling problem
- Efficiently handles up to 50,000 jobs

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Sorting O(n log n) + DP with binary search O(n log n)
- **Space Complexity:** O(n) - For DP array and storing sorted jobs

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort jobs by end time to enable greedy-like DP

Use binary search to efficiently find non-overlapping previous job

Similar to classic "Weighted Interval Scheduling" problem

Can be optimized to O(n) space by reusing DP array

🔗 Related Problems
Non-overlapping Intervals

Minimum Number of Arrows to Burst Balloons

Maximum Length of Pair Chain

Maximum Number of Events That Can Be Attended II
