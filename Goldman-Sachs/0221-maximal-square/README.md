# 221. Maximal Square

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming, Matrix  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/maximal-square/)

Given an `m x n` binary matrix filled with `0`'s and `1`'s, find the **largest square** containing only `1`'s and return its **area**.

**Example 1:**

![Example 1](https://assets.leetcode.com/uploads/2020/11/26/max1grid.jpg)

Input: matrix = [["1","0","1","0","0"],
["1","0","1","1","1"],
["1","1","1","1","1"],
["1","0","0","1","0"]]
Output: 4


**Example 2:**

Input: matrix = [["0","1"],["1","0"]]
Output: 1


**Example 3:**

Input: matrix = [["0"]]
Output: 0


**Constraints:**
- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 300`
- `matrix[i][j]` is `'0'` or `'1'`.

## 🧠 Thought Process

### Problem Understanding
We need to find the largest square submatrix consisting entirely of `1`s.

### Key Insights
1. **DP State**: `dp[i][j]` = side length of the largest square ending at `(i, j)`
2. **Transition**: `dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1` if `matrix[i][j] == '1'`
3. **Base Case**: First row and first column: `dp[i][j] = 1` if `matrix[i][j] == '1'`
4. **Result**: Track maximum side length seen, area = max_side²

### Approach Selection
**Chosen Approach:** Dynamic Programming  
**Why this approach?**
- O(m × n) time complexity
- O(m × n) or O(n) space complexity
- Clear recurrence relation

**Alternative Approaches:**
- **Brute Force**: Check all squares O(m² × n²) - too slow
- **Prefix Sum + Binary Search**: O(m × n × log(min(m,n))) - more complex

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n) where m = rows, n = columns
- **Space Complexity:** O(m × n) for full DP, O(n) for optimized

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use dp[i][j] to store the side length of the largest square ending at (i, j)

Transition formula: dp[i][j] = min(up, left, up-left) + 1

Track maximum side length during iteration

Convert chars to ints for easier computation

🔗 Related Problems
Maximal Rectangle
Count Square Submatrices with All Ones
Count Submatrices With All Ones
Largest 1-Bordered Square
Maximal Square (this problem)
