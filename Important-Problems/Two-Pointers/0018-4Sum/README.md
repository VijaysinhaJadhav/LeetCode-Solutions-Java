# 18. 4Sum

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Sorting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/4sum/)

Given an array `nums` of `n` integers, return an array of all the **unique** quadruplets `[nums[a], nums[b], nums[c], nums[d]]` such that:

- `0 <= a, b, c, d < n`
- `a`, `b`, `c`, and `d` are **distinct**.
- `nums[a] + nums[b] + nums[c] + nums[d] == target`

You may return the answer in **any order**.

**Example 1:**

Input: nums = [1,0,-1,0,-2,2], target = 0

Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]


**Example 2:**

Input: nums = [2,2,2,2,2], target = 8

Output: [[2,2,2,2]]


**Constraints:**
- `1 <= nums.length <= 200`
- `-10^9 <= nums[i] <= 10^9`
- `-10^9 <= target <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- Need to find all unique quadruplets that sum to target
- Extension of 3Sum problem with additional loop
- Must avoid duplicate quadruplets in the result
- Multiple approaches with different time complexities

### Key Insights
1. **Sorting + Two Pointers**: Most efficient approach - O(n³) time
2. **Generalized K-Sum**: Recursive approach for any K
3. **HashSet for Duplicates**: Handle duplicate quadruplets efficiently
4. **Early Termination**: Optimize by skipping impossible cases

### Approach Selection
**Chosen Approach:** Sorting + Two Pointers  
**Why this approach?** 
- O(n³) time complexity (optimal for this problem)
- O(1) or O(n) space depending on sorting
- Handles duplicates efficiently
- Most efficient and scalable

## ⚡ Complexity Analysis
- **Time Complexity:** O(n³) - Sorting O(n log n) + three nested loops O(n³)
- **Space Complexity:** O(1) or O(n) depending on sorting algorithm

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort the array first to enable two pointers and skip duplicates

Use four nested loops: two outer loops + two pointers

Skip duplicate elements to avoid duplicate quadruplets

Use early termination when current sums exceed target

🔗 Related Problems
Two Sum

3Sum

3Sum Closest

4Sum II

3Sum Smaller

