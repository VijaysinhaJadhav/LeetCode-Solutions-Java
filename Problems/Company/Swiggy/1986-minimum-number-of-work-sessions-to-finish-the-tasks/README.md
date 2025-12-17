# 1986. Minimum Number of Work Sessions to Finish the Tasks

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming, Backtracking, Bit Manipulation, Bitmask  
**Companies:** Google, Amazon, Meta, Microsoft

[LeetCode Link](https://leetcode.com/problems/minimum-number-of-work-sessions-to-finish-the-tasks/)

There are `n` tasks assigned to you. The task times are represented as an integer array `tasks` of length `n`, where the `i-th` task takes `tasks[i]` hours to finish. A **work session** is when you work for **at most** `sessionTime` consecutive hours and then take a break.

You should finish the given tasks in a way that satisfies the following conditions:
- If you start a task in a work session, you must complete it in the same work session.
- You can start a new task immediately after finishing the previous one.
- You may complete the tasks in **any order**.

Return *the **minimum** number of work sessions needed* to finish all the tasks following the conditions above.

**Example 1:**

Input: tasks = [1,2,3], sessionTime = 3

Output: 2

Explanation: You can finish the tasks in two work sessions.

First work session: finish the first and the second tasks in 1 + 2 = 3 hours.

Second work session: finish the third task in 3 hours.


**Example 2:**

Input: tasks = [3,1,3,1,1], sessionTime = 8

Output: 2

Explanation: You can finish the tasks in two work sessions.

First work session: finish all the tasks except the last one in 3 + 1 + 3 + 1 = 8 hours.

Second work session: finish the last task in 1 hour.


**Example 3:**

Input: tasks = [1,2,3,4,5], sessionTime = 15

Output: 1

Explanation: You can finish all the tasks in one work session.


**Constraints:**
- `n == tasks.length`
- `1 <= n <= 14`
- `1 <= tasks[i] <= 10`
- `max(tasks[i]) <= sessionTime <= 15`

## 🧠 Thought Process

### Initial Thoughts
- Need to partition tasks into sessions where each session sum ≤ sessionTime
- This is similar to bin packing problem or subset sum problem
- n ≤ 14 suggests exponential solution like DP with bitmask is feasible
- sessionTime ≤ 15 is relatively small

### Key Insights
1. **Bitmask DP**: Use bitmask to represent which tasks are completed
2. **State Definition**: dp[mask] = minimum sessions needed for tasks in mask
3. **Transition**: Try adding a task to current session or start new session
4. **Optimization**: Track both minimum sessions and remaining time in current session
5. **Alternative**: DFS/backtracking with pruning since n is small

### Approach Selection
**Chosen Approach:** Bitmask DP with State Tracking  
**Why this approach?** 
- O(n * 2^n) time complexity, feasible for n ≤ 14
- DP ensures optimal solution
- Handles all constraints efficiently
- Clear state transition logic

## ⚡ Complexity Analysis
- **Time Complexity:** O(n * 2^n) where n ≤ 14, so at most 14 * 16384 = ~230k operations
- **Space Complexity:** O(2^n) for DP array

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use bitmask to represent subset of tasks

DP state: minimum sessions for each subset

Try to add tasks to current session if enough time

Otherwise start new session

Similar to partition problem with capacity constraints

🔗 Related Problems
Partition to K Equal Sum Subsets

Matchsticks to Square

Find Minimum Time to Finish All Jobs

Fair Distribution of Cookies

Distribute Repeating Integers
