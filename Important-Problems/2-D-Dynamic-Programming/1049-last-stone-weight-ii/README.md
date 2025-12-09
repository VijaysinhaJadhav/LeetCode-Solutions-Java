# 1049. Last Stone Weight II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming, 0/1 Knapsack  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/last-stone-weight-ii/)

You are given an array of integers `stones` where `stones[i]` is the weight of the `i-th` stone.

We are playing a game with the stones. On each turn, we choose any two stones and smash them together. Suppose the stones have weights `x` and `y` with `x <= y`. The result of this smash is:
- If `x == y`, both stones are destroyed
- If `x != y`, the stone of weight `x` is destroyed, and the stone of weight `y` has new weight `y - x`

At the end of the game, there is at most one stone left.

Return the smallest possible weight of the left stone. If there are no stones left, return `0`.

**Example 1:**

Input: stones = [2,7,4,1,8,1]

Output: 1

Explanation:

We can combine 2 and 4 to get 2, so the array converts to [2,7,1,8,1],

then we can combine 7 and 8 to get 1, so the array converts to [2,1,1,1],

then we can combine 2 and 1 to get 1, so the array converts to [1,1,1],

then we can combine 1 and 1 to get 0, so the array converts to [1], then that's the answer.


**Example 2:**

Input: stones = [31,26,33,21,40]

Output: 5


**Constraints:**
- `1 <= stones.length <= 30`
- `1 <= stones[i] <= 100`

## 🧠 Thought Process

### Initial Thoughts
- The problem reduces to partitioning stones into two groups with minimum difference
- When we smash stones from different groups, we're effectively taking the difference
- The goal is to minimize the absolute difference between the two groups
- This is similar to the partition problem or 0/1 knapsack

### Key Insights
1. **Problem Transformation**: Find a partition of stones into two subsets with minimum absolute difference
2. **Dynamic Programming**: Use knapsack DP to find if we can achieve certain sums
3. **Mathematical Insight**: The answer is `total_sum - 2 * max_subset_sum` where subset sum ≤ total_sum/2
4. **0/1 Knapsack**: Each stone can be included or excluded from one group

### Approach Selection
**Chosen Approach:** Dynamic Programming (0/1 Knapsack)  
**Why this approach?** 
- Efficiently finds the optimal partition
- O(n × sum) time complexity is feasible given constraints
- Clear and intuitive implementation
- Handles all cases optimally

## ⚡ Complexity Analysis
- **Time Complexity:** O(n × S) where S is the sum of all stones
- **Space Complexity:** O(S) - Using DP array for knapsack

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The problem is equivalent to partitioning array into two subsets with minimum difference

We want to find the maximum subset sum that is ≤ total_sum/2

The answer is total_sum - 2 * max_subset_sum

Can be solved using 0/1 knapsack DP approach

🔗 Related Problems
Partition Equal Subset Sum

Target Sum

Partition to K Equal Sum Subsets

Matchsticks to Square
