# 54. Spiral Matrix

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Matrix, Simulation  
**Companies:** Amazon, Microsoft, Apple, Google, Meta, Bloomberg, Adobe, Uber, Oracle, TikTok

[LeetCode Link](https://leetcode.com/problems/spiral-matrix/)

Given an `m x n` matrix, return all elements of the matrix in **spiral order**.

**Example 1:**

Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]
Explanation:
1 → 2 → 3
↓
4 → 5 6
↑ ↓
7 ← 8 ← 9


**Example 2:**

Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
Explanation:
1 → 2 → 3 → 4
↓
5 → 6 → 7 8
↑ ↓
9 ← 10 ← 11 ← 12


**Constraints:**
- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 10`
- `-100 <= matrix[i][j] <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Need to traverse matrix in clockwise spiral order
- Traverse right → down → left → up → repeat
- Need to track boundaries: top, bottom, left, right
- Stop when boundaries cross

### Key Insights
1. **Layer-by-Layer Approach:**
   - Process matrix as concentric layers/rings
   - Outer layer first, then move inward
2. **Boundary Tracking:**
   - Use four variables: top, bottom, left, right
   - Update boundaries after completing each direction
3. **Direction Sequence:**
   - Right: top row, left → right
   - Down: right column, top → bottom  
   - Left: bottom row, right → left (if still rows left)
   - Up: left column, bottom → top (if still columns left)
4. **Termination Condition:**
   - Stop when top > bottom or left > right
   - All elements processed

### Approach Selection
**Chosen Approach:** Boundary Traversal with Four Pointers  
**Why this approach?** 
- O(m*n) time complexity (optimal)
- O(1) extra space (excluding output)
- Clean and intuitive
- Handles all matrix shapes (not just square)

## ⚡ Complexity Analysis
- **Time Complexity:** O(m*n) - Visit each element exactly once
- **Space Complexity:** O(1) extra space, O(m*n) for output list

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use four boundaries: top, bottom, left, right

Traverse in four directions: right, down, left, up

Check boundaries after each direction

Handle single row/column cases

🔗 Related Problems
59. Spiral Matrix II

885. Spiral Matrix III

2326. Spiral Matrix IV

48. Rotate Image

498. Diagonal Traverse

1424. Diagonal Traverse II
