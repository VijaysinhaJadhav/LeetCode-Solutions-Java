# 46. Permutations

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Backtracking  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn, Adobe

[LeetCode Link](https://leetcode.com/problems/permutations/)

Given an array `nums` of distinct integers, return all the possible permutations. You can return the answer in **any order**.

**Example 1:**

Input: nums = [1,2,3]

Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]


**Example 2:**

Input: nums = [0,1]

Output: [[0,1],[1,0]]


**Example 3:**

Input: nums = [1]

Output: [[1]]


**Constraints:**
- `1 <= nums.length <= 6`
- `-10 <= nums[i] <= 10`
- All the integers of `nums` are **unique**.

## 🧠 Thought Process

### Initial Thoughts
- Need to generate all possible arrangements of distinct elements
- For n elements, there are n! permutations
- Multiple approaches: backtracking, iterative, recursive swapping

### Key Insights
1. **Backtracking**: Build permutations incrementally by adding unused elements
2. **Swap-based**: Generate permutations by swapping elements in-place
3. **The key insight**: At each position, we can choose any unused element
4. **Since elements are distinct**, no need to handle duplicates

### Approach Selection
**Chosen Approach:** Backtracking with Used Array  
**Why this approach?** 
- O(n * n!) time complexity (optimal for output size)
- O(n) space complexity for recursion stack and used array
- Intuitive and widely used
- Easy to understand and implement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n * n!) - There are n! permutations and each takes O(n) to build
- **Space Complexity:** O(n) - For recursion stack and used array (excluding output)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

 Notes
Permutations are different from combinations - order matters

Since all elements are distinct, we don't need duplicate handling

The number of permutations follows the factorial formula

Can be optimized with swap-based approach to avoid extra space

🔗 Related Problems
Permutations II

Subsets

Combinations

Next Permutation

Permutation Sequence

Letter Case Permutation
