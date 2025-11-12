# 40. Combination Sum II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Backtracking  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn, Adobe

[LeetCode Link](https://leetcode.com/problems/combination-sum-ii/)

Given a collection of candidate numbers (`candidates`) and a target number (`target`), find all unique combinations in `candidates` where the candidate numbers sum to `target`.

Each number in `candidates` may only be used **once** in the combination.

**Note:** The solution set must not contain duplicate combinations.

**Example 1:**

Input: candidates = [10,1,2,7,6,1,5], target = 8

Output:

[

[1,1,6],

[1,2,5],

[1,7],

[2,6]

]


**Example 2:**

Input: candidates = [2,5,2,1,2], target = 5

Output:

[

[1,2,2],

[5]

]


**Constraints:**
- `1 <= candidates.length <= 100`
- `1 <= candidates[i] <= 50`
- `1 <= target <= 30`

## 🧠 Thought Process

### Initial Thoughts
- Need to find combinations that sum to target
- Each element can be used only once (bounded knapsack)
- Array may contain duplicates, but output must have unique combinations
- Multiple approaches: backtracking with careful duplicate handling

### Key Insights
1. **Backtracking**: Build combinations incrementally, use each element once
2. **Sorting**: Essential to handle duplicates efficiently
3. **Duplicate Handling**: Skip duplicates at the same level to avoid duplicate combinations
4. **The key insight**: When we skip a duplicate element, we must skip all its duplicates at that level

### Approach Selection
**Chosen Approach:** Backtracking with Sorting and Duplicate Skipping  
**Why this approach?** 
- O(2^n) time complexity in worst case
- O(n) space complexity for recursion stack
- Efficient duplicate handling through sorting and skipping
- Most intuitive and widely used approach

## ⚡ Complexity Analysis
- **Time Complexity:** O(2^n) - In worst case, explore all subsets
- **Space Complexity:** O(n) - For recursion stack (excluding output)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sorting enables efficient duplicate detection and pruning

We skip duplicates at the same level to avoid duplicate combinations

Each element can be used only once, so we always move to next index

The problem is similar to bounded knapsack with duplicate handling

🔗 Related Problems
Combination Sum

Combination Sum III

Combination Sum IV

Subsets II

Subsets

Permutations

Permutations II
