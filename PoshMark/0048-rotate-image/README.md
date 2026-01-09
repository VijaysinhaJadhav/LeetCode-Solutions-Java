# 48. Rotate Image

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Math, Matrix  
**Companies:** Amazon, Microsoft, Facebook, Apple, Google, Bloomberg, Adobe, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/rotate-image/)

You are given an `n x n` 2D `matrix` representing an image. Rotate the image by **90 degrees (clockwise)**.

You have to rotate the image **in-place**, which means you have to modify the input 2D matrix directly. **DO NOT** allocate another 2D matrix and do the rotation.

**Example 1:**

Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [[7,4,1],[8,5,2],[9,6,3]]
Explanation:
1 2 3 7 4 1
4 5 6 → 8 5 2
7 8 9 9 6 3


**Example 2:**

Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]


**Constraints:**
- `n == matrix.length == matrix[i].length`
- `1 <= n <= 20`
- `-1000 <= matrix[i][j] <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Need to rotate n x n matrix 90° clockwise in-place
- Cannot use extra matrix, must modify in place
- Rotation can be decomposed into operations
- Key observation: rotation = transpose + reverse

### Key Insights
1. **Mathematical Relation:**
   - 90° clockwise rotation: `(i, j) → (j, n-1-i)`
   - Can rotate in groups of 4 cells at once
2. **Layer-by-Layer Rotation:**
   - Rotate concentric layers from outer to inner
   - For layer i, rotate elements at positions: top, right, bottom, left
3. **Transpose + Reverse Method:**
   - Transpose matrix (swap rows and columns)
   - Reverse each row
   - This gives 90° clockwise rotation
4. **Reverse + Transpose Method:**
   - Reverse rows
   - Transpose matrix
   - This also gives 90° clockwise rotation

### Approach Selection
**Chosen Approach:** Transpose then Reverse Rows  
**Why this approach?** 
- O(n²) time complexity (optimal)
- O(1) space complexity (in-place)
- Simple and elegant
- Easy to understand and implement

**Alternative:** Layer-by-Layer Rotation  
**Why this approach?**
- Also O(n²) time, O(1) space
- More explicit about rotation process
- Good for understanding the mechanics

## ⚡ Complexity Analysis
- **Time Complexity:** O(n²) - Must visit each element
- **Space Complexity:** O(1) - In-place modification

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
90° clockwise = transpose + reverse rows

90° counter-clockwise = reverse rows + transpose

180° = reverse rows + reverse columns

Works only for square matrices

🔗 Related Problems
54. Spiral Matrix

73. Set Matrix Zeroes

867. Transpose Matrix

1886. Determine Whether Matrix Can Be Obtained By Rotation

566. Reshape the Matrix
