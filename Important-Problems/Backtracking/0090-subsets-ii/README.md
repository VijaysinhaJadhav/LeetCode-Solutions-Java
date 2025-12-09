# 90. Subsets II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Backtracking, Bit Manipulation  
**Companies:** Amazon, Facebook, Google, Microsoft, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/subsets-ii/)

Given an integer array `nums` that may contain duplicates, return all possible subsets (the power set).

The solution set must not contain duplicate subsets. Return the solution in any order.

**Example 1:**

Input: nums = [1,2,2]

Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]


**Example 2:**

Input: nums = [0]

Output: [[],[0]]


**Constraints:**
- `1 <= nums.length <= 10`
- `-10 <= nums[i] <= 10`

## 🧠 Thought Process

### Initial Thoughts
- Need to generate all subsets while handling duplicates
- Similar to Subsets I, but with duplicate elements
- Must avoid duplicate subsets in the result
- Can use backtracking, bit manipulation, or iterative approaches

### Key Insights
1. **Sorting**: Sort the array first to handle duplicates easily
2. **Backtracking**: Generate subsets while skipping duplicates at same level
3. **Iterative**: Build subsets level by level, handling duplicates carefully
4. **Bit Manipulation**: Use bitmasks but need to handle duplicates with sets

### Approach Selection
**Chosen Approach:** Backtracking with Sorting  
**Why this approach?** 
- O(2^n) time complexity (optimal)
- Handles duplicates elegantly
- Easy to understand and implement
- Naturally generates subsets without duplicates

## ⚡ Complexity Analysis
- **Time Complexity:** O(n × 2^n) - In worst case, 2^n subsets each taking O(n) to generate
- **Space Complexity:** O(n) - Backtracking recursion depth and temporary list

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort the array first to group duplicates together

When skipping elements, skip duplicates at the same decision level

Use index to track current position and avoid going backwards

The "skip duplicates" condition: i > start && nums[i] == nums[i-1]

🔗 Related Problems
Subsets

Combination Sum II

Permutations II

Combinations
