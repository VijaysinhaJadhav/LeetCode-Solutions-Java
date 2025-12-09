# 78. Subsets

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Backtracking, Bit Manipulation  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn, Adobe

[LeetCode Link](https://leetcode.com/problems/subsets/)

Given an integer array `nums` of unique elements, return all possible subsets (the power set).

The solution set must not contain duplicate subsets. Return the solution in any order.

**Example 1:**

Input: nums = [1,2,3]

Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]


**Example 2:**

Input: nums = [0]

Output: [[],[0]]


**Constraints:**
- `1 <= nums.length <= 10`
- `-10 <= nums[i] <= 10`
- All the numbers of `nums` are unique.

## 🧠 Thought Process

### Initial Thoughts
- Need to generate all possible subsets of a set (power set)
- For n elements, there are 2^n possible subsets
- Multiple approaches: backtracking, iterative, bit manipulation

### Key Insights
1. **Backtracking**: Build subsets incrementally by including/excluding elements
2. **Iterative**: Start with empty set and build up by adding each element
3. **Bit Manipulation**: Use bitmask to represent which elements are included
4. **The key insight**: Each element can either be included or excluded

### Approach Selection
**Chosen Approach:** Backtracking (DFS)  
**Why this approach?** 
- O(n * 2^n) time complexity (optimal for output size)
- O(n) space complexity (recursion stack)
- Intuitive and widely used
- Easy to understand and implement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n * 2^n) - There are 2^n subsets and each takes O(n) to build
- **Space Complexity:** O(n) - For recursion stack (excluding output)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The power set always includes the empty set

Since all elements are unique, no need to handle duplicates

Backtracking naturally generates subsets in increasing size order

Can be easily modified for subsets with duplicates (Problem 90)

🔗 Related Problems
Subsets II

Permutations

Combinations

Combination Sum

Combination Sum II

Combination Sum III

Sum of All Subset XOR Totals
