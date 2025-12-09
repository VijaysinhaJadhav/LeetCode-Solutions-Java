# 77. Combinations

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Backtracking, Array  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/combinations/)

Given two integers `n` and `k`, return all possible combinations of `k` numbers chosen from the range `[1, n]`.

You may return the answer in **any order**.

**Example 1:**

Input: n = 4, k = 2

Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]

Explanation: There are 4 choose 2 = 6 total combinations.


**Example 2:**

Input: n = 1, k = 1

Output: [[1]]


**Constraints:**
- `1 <= n <= 20`
- `1 <= k <= n`

## 🧠 Thought Process

### Initial Thoughts
- Need to generate all combinations of k elements from n elements
- Order doesn't matter in combinations (unlike permutations)
- Multiple approaches: backtracking, iterative, mathematical

### Key Insights
1. **Backtracking**: Build combinations incrementally, one element at a time
2. **Mathematical**: There are C(n,k) = n!/(k!(n-k)!) total combinations
3. **The key insight**: At each step, we choose an element and then recursively choose k-1 elements from the remaining
4. **Pruning**: We can stop early when we don't have enough elements left to form a k-length combination

### Approach Selection
**Chosen Approach:** Backtracking (DFS)  
**Why this approach?** 
- O(C(n,k) * k) time complexity (optimal for output size)
- O(k) space complexity for recursion stack
- Intuitive and widely used
- Easy to understand and implement

## ⚡ Complexity Analysis
- **Time Complexity:** O(C(n,k) * k) - There are C(n,k) combinations and each takes O(k) to build
- **Space Complexity:** O(k) - For recursion stack (excluding output)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

implementation
📝 Notes
Combinations are different from permutations - order doesn't matter

We always move forward in the number sequence to avoid duplicates

The number of combinations follows the binomial coefficient formula

Can be optimized with pruning when remaining elements < needed elements

🔗 Related Problems
Permutations

Subsets

Combination Sum

Combination Sum II

Combination Sum III

Subsets II


