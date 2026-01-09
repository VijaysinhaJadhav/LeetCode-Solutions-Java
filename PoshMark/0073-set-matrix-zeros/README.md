# 73. Set Matrix Zeroes

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Matrix  
**Companies:** Amazon, Microsoft, Facebook, Apple, Google, Bloomberg, Oracle, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/set-matrix-zeroes/)

Given an `m x n` integer matrix `matrix`, if an element is `0`, set its entire row and column to `0`'s.

You must do it **in place** (modify the input matrix directly, no extra space for another matrix).

**Example 1:**

Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
Output: [[1,0,1],[0,0,0],[1,0,1]]
Explanation:
1 1 1 1 0 1
1 0 1 → 0 0 0
1 1 1 1 0 1


**Example 2:**

Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
Explanation:
0 1 2 0 0 0 0 0
3 4 5 2 → 0 4 5 0
1 3 1 5 0 3 1 0


**Constraints:**
- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 200`
- `-2^31 <= matrix[i][j] <= 2^31 - 1`

**Follow up:**
- A straightforward solution using O(mn) space is probably a bad idea.
- A simple improvement uses O(m + n) space, but still not the best solution.
- Could you devise a constant space solution?

## 🧠 Thought Process

### Initial Thoughts
- Need to set entire row and column to zero if any cell contains zero
- Challenge: Doing it in place without extra O(mn) space
- If we set zeros immediately while scanning, we lose original information
- Need to mark which rows/columns need to be zeroed first

### Key Insights
1. **Space Complexity Trade-offs:**
   - O(mn): Copy matrix (not allowed by follow-up)
   - O(m+n): Store zero rows/columns in separate arrays
   - O(1): Use first row and column as markers
2. **Two-Pass Approach:**
   - First pass: Identify which rows/columns have zeros
   - Second pass: Set zeros based on markers
3. **In-Place Marking:**
   - Use first row to mark columns that need zeros
   - Use first column to mark rows that need zeros
   - Handle first row/column separately
4. **Special Cases:**
   - Matrix[0][0] marks both first row and first column
   - Need separate variable for first column

### Approach Selection
**Chosen Approach:** In-Place with First Row/Column Markers  
**Why this approach?** 
- O(m*n) time complexity
- O(1) space complexity (meets follow-up requirement)
- Efficient and elegant
- Handles all edge cases

## ⚡ Complexity Analysis
- **Time Complexity:** O(m*n) - Two passes through the matrix
- **Space Complexity:** O(1) - Only a few extra variables

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use first row to track which columns should be zero

Use first column to track which rows should be zero

Handle first row and column separately

Process from bottom-right to avoid overwriting markers

🔗 Related Problems
289. Game of Life

48. Rotate Image

54. Spiral Matrix

498. Diagonal Traverse

566. Reshape the Matrix
