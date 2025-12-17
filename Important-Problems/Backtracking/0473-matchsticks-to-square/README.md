# 473. Matchsticks to Square

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming, Backtracking, Bit Manipulation, Bitmask  
**Companies:** Google, Amazon, Microsoft, Meta, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/matchsticks-to-square/)

You are given an integer array `matchsticks` where `matchsticks[i]` is the length of the `i-th` matchstick. You want to use **all the matchsticks** to make one square. You **should not break** any matchstick, but you can link them up, and each matchstick must be used **exactly one time**.

Return `true` if you can make this square and `false` otherwise.

**Example 1:**

Input: matchsticks = [1,1,2,2,2]

Output: true

Explanation: You can form a square of side length 2, one stick of length 1 on each side.


**Example 2:**

Input: matchsticks = [3,3,3,3,4]

Output: false

Explanation: You cannot find a way to form a square.


**Constraints:**
- `1 <= matchsticks.length <= 15`
- `1 <= matchsticks[i] <= 10^8`

## 🧠 Thought Process

### Initial Thoughts
- Need to partition matchsticks into 4 groups with equal sums
- Each group represents one side of the square
- This is essentially a 4-way partition problem
- Small constraints (n ≤ 15) suggest backtracking/DP with bitmask

### Key Insights
1. **Pruning Conditions**:
   - Total sum must be divisible by 4
   - No matchstick can be longer than target side length
   - Sort in descending order for better pruning

2. **Approaches**:
   - Backtracking with pruning
   - Dynamic Programming with bitmask
   - Meet in the middle (for larger n)

3. **Optimizations**:
   - Sort descending to fail early
   - Skip duplicates during backtracking
   - Use memoization to avoid repeated states

### Approach Selection
**Chosen Approach:** Backtracking with Pruning  
**Why this approach?** 
- n ≤ 15 makes backtracking feasible
- Intuitive and easy to implement
- Good pruning opportunities
- Clear logical flow

## ⚡ Complexity Analysis
- **Time Complexity:** O(4^n) worst case, but heavily pruned
- **Space Complexity:** O(n) for recursion stack

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort matchsticks in descending order for better pruning

Check basic conditions first (sum divisible by 4, no stick > target)

Use DFS to try placing each stick in one of 4 sides

Backtrack when side exceeds target

Memoization can further optimize

🔗 Related Problems
Partition to K Equal Sum Subsets

Partition Equal Subset Sum

Find Minimum Time to Finish All Jobs

Fair Distribution of Cookies
