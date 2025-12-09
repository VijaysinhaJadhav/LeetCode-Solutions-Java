# 304. Range Sum Query 2D - Immutable

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Design, Matrix, Prefix Sum  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/range-sum-query-2d-immutable/)

Given a 2D matrix `matrix`, handle multiple queries of the following type:

- Calculate the **sum** of the elements of `matrix` inside the rectangle defined by its **upper left corner** `(row1, col1)` and **lower right corner** `(row2, col2)`.

Implement the `NumMatrix` class:

- `NumMatrix(int[][] matrix)` Initializes the object with the integer matrix `matrix`.
- `int sumRegion(int row1, int col1, int row2, int col2)` Returns the **sum** of the elements of `matrix` inside the rectangle defined by its **upper left corner** `(row1, col1)` and **lower right corner** `(row2, col2)`.

**Example 1:**

![Range Sum Query 2D](https://assets.leetcode.com/uploads/2021/03/14/sum-grid.jpg)

Input

["NumMatrix", "sumRegion", "sumRegion", "sumRegion"]

[[[[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]], [2, 1, 4, 3], [1, 1, 2, 2], [1, 2, 2, 4]]

Output

[null, 8, 11, 12]

Explanation

NumMatrix numMatrix = new NumMatrix([[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]);

numMatrix.sumRegion(2, 1, 4, 3); // return 8 (i.e sum of the red rectangle)

numMatrix.sumRegion(1, 1, 2, 2); // return 11 (i.e sum of the green rectangle)

numMatrix.sumRegion(1, 2, 2, 4); // return 12 (i.e sum of the blue rectangle)


**Constraints:**

- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 200`
- `-10^4 <= matrix[i][j] <= 10^4`
- `0 <= row1 <= row2 < m`
- `0 <= col1 <= col2 < n`
- At most `10^4` calls will be made to `sumRegion`.

## 🧠 Thought Process

### Initial Thoughts
- Need to efficiently compute sum of any rectangular region in a 2D matrix
- Multiple queries will be made, so precomputation is beneficial
- Brute force approach would be O(m*n) per query, which is inefficient

### Key Insights
1. **Prefix Sum (Cumulative Sum)**: Precompute cumulative sums to answer queries in O(1) time
2. **2D Prefix Sum**: Extend 1D prefix sum to 2D using inclusion-exclusion principle
3. **Dynamic Programming**: Build prefix sum matrix using DP relationship
4. **Space-Time Tradeoff**: O(m*n) precomputation, O(1) per query

### Approach Selection
**Chosen Approach:** 2D Prefix Sum Matrix  
**Why this approach?** 
- Optimal for multiple queries: O(1) per query after O(m*n) precomputation
- Simple and elegant mathematical formulation
- Well-established pattern for range sum problems

## ⚡ Complexity Analysis
- **Constructor Time Complexity:** O(m*n) - Build prefix sum matrix
- **sumRegion Time Complexity:** O(1) - Constant time query
- **Space Complexity:** O(m*n) - Store prefix sum matrix

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key formula: sum = dp[r2+1][c2+1] - dp[r1][c2+1] - dp[r2+1][c1] + dp[r1][c1]

We use a dp matrix with dimensions (m+1) x (n+1) to handle edge cases

The inclusion-exclusion principle ensures we count each region exactly once

This pattern can be extended to higher dimensions

🔗 Related Problems
303. Range Sum Query - Immutable

308. Range Sum Query 2D - Mutable

1314. Matrix Block Sum

1277. Count Square Submatrices with All Ones
