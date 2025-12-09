# 39. Combination Sum

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Backtracking  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn, Adobe

[LeetCode Link](https://leetcode.com/problems/combination-sum/)

Given an array of distinct integers `candidates` and a target integer `target`, return a list of all unique combinations of `candidates` where the chosen numbers sum to `target`. You may return the combinations in any order.

The same number may be chosen from `candidates` an unlimited number of times. Two combinations are unique if the frequency of at least one of the chosen numbers is different.

The test cases are generated such that the number of unique combinations that sum up to `target` is less than `150` combinations.

**Example 1:**

Input: candidates = [2,3,6,7], target = 7

Output: [[2,2,3],[7]]

Explanation:

2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.

7 is a candidate, and 7 = 7.

These are the only two combinations.


**Example 2:**

Input: candidates = [2,3,5], target = 8

Output: [[2,2,2,2],[2,3,3],[3,5]]


**Example 3:**

Input: candidates = [2], target = 1

Output: []


**Constraints:**
- `1 <= candidates.length <= 30`
- `2 <= candidates[i] <= 40`
- All elements of `candidates` are distinct.
- `1 <= target <= 500`

## 🧠 Thought Process

### Initial Thoughts
- Need to find combinations that sum to target
- Can use elements multiple times (unbounded knapsack)
- Since elements are distinct, no duplicate combinations to worry about
- Multiple approaches: backtracking, dynamic programming

### Key Insights
1. **Backtracking**: Build combinations incrementally, try including each candidate multiple times
2. **Sorting**: Sort candidates to enable pruning when remaining target < candidate
3. **The key insight**: At each step, we can either include current candidate or move to next candidate
4. **Pruning**: Stop early when remaining target < 0 or when current candidate > remaining target

### Approach Selection
**Chosen Approach:** Backtracking with Sorting and Pruning  
**Why this approach?** 
- O(2^target) time complexity in worst case, but practical due to constraints
- O(target) space complexity for recursion stack
- Intuitive and efficient with pruning
- Handles the unbounded nature naturally

## ⚡ Complexity Analysis
- **Time Complexity:** O(N^(T/M + 1)) where N = candidates length, T = target, M = min candidate
- **Space Complexity:** O(T/M) for recursion stack depth

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Since we can use elements multiple times, we don't increment index when including an element

Sorting enables efficient pruning - we can stop when current candidate > remaining target

We avoid duplicate combinations by processing candidates in sorted order and not revisiting previous candidates

The problem is similar to unbounded knapsack or coin change problem

🔗 Related Problems
Combination Sum II

Combination Sum III

Combination Sum IV

Coin Change

Coin Change II

Combinations

Subsets
