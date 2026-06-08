# 45. Jump Game II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Greedy, Dynamic Programming  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple  

[LeetCode Link](https://leetcode.com/problems/jump-game-ii/)

You are given a **0-indexed** array of integers `nums` of length `n`. You are initially positioned at `nums[0]`.

Each element `nums[i]` represents the maximum length of a forward jump from index `i`. In other words, if you are at `nums[i]`, you can jump to any `nums[i + j]` where:
- `1 <= j <= nums[i]`
- `i + j < n`

Return the **minimum number of jumps** to reach `nums[n - 1]`. The test cases are generated such that you can always reach the last index.

**Example 1:**

Input: nums = [2,3,1,1,4]
Output: 2
Explanation: The minimum number of jumps to reach the last index is 2.
Jump 1 step from index 0 to 1, then 3 steps to the last index.


**Example 2:**

Input: nums = [2,3,0,1,4]
Output: 2



**Constraints:**
- `1 <= nums.length <= 10^4`
- `0 <= nums[i] <= 1000`
- It is guaranteed that you can reach `nums[n - 1]`.

## 🧠 Thought Process

### Problem Understanding
We need the **minimum** number of jumps to reach the end. Unlike Jump Game I (which only asked if reachable), we must output the smallest number of jumps.

### Key Insights
1. **Greedy Approach** – At each step, choose the jump that maximises the farthest reachable index.
2. **BFS over positions** – Each jump increments the level; we can think of it as a BFS where each level corresponds to one jump.
3. **DP** – `dp[i]` = min jumps to reach i; O(n²) is too slow for n=10⁴.

### Approach Selection
**Chosen Approach:** Greedy BFS (also called "jump game II greedy")
- Maintain `currentEnd` = farthest index reachable with the current number of jumps.
- Maintain `farthest` = farthest index we can reach by taking one more jump from anywhere within the current range.
- Iterate through the array (except last element), updating `farthest`. When we reach `currentEnd`, we must take a new jump → increment jumps and set `currentEnd = farthest`.

**Why this approach?**
- O(n) time, O(1) space.
- Intuitive and efficient.

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) – single pass through the array.
- **Space Complexity:** O(1) – only a few variables.

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Do not iterate through the last element because we don't need to jump from it.

The greedy algorithm works because we always extend the farthest reachable range with the minimal number of jumps.

Edge case: nums.length == 1 → 0 jumps.

🔗 Related Problems
Jump Game
Jump Game III
Jump Game IV
Jump Game VI
