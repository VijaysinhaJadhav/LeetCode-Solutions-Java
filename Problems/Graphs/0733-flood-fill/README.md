# 733. Flood Fill

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Depth-First Search, Breadth-First Search, Matrix  
**Companies:** Amazon, Google, Microsoft, Apple, Facebook, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/flood-fill/)

An image is represented by an `m x n` integer grid `image` where `image[i][j]` represents the pixel value of the image.

You are also given three integers:
- `sr`: starting row
- `sc`: starting column  
- `color`: new color

Perform a **flood fill** on the image starting from the pixel `image[sr][sc]`.

A flood fill:
1. Starts at the starting pixel
2. Changes the color of the starting pixel and all connected pixels of the same color to the new color
3. "Connected" means 4-directionally adjacent (up, down, left, right)

**Example 1:**

![Flood Fill Example](https://assets.leetcode.com/uploads/2021/06/01/flood1-grid.jpg)

Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2

Output: [[2,2,2],[2,2,0],[2,0,1]]

Explanation: From the center of the image (position (sr, sc) = (1, 1)), all pixels connected

by the same color as the starting pixel are colored with the new color.

Note the bottom corner is not colored 2, because it is not 4-directionally connected to the starting pixel.


**Example 2:**

Input: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, color = 0

Output: [[0,0,0],[0,0,0]]

Explanation: The starting pixel is already colored with the new color, so no changes are made.


**Constraints:**
- `m == image.length`
- `n == image[i].length`
- `1 <= m, n <= 50`
- `0 <= image[i][j], color < 2^16`
- `0 <= sr < m`
- `0 <= sc < n`

## 🧠 Thought Process

### Initial Thoughts
- Need to perform a region fill starting from (sr, sc)
- Only fill pixels that are connected and have the same original color
- Can use DFS (recursive or iterative) or BFS
- Important: If new color equals original color, return original image (avoid infinite recursion)

### Key Insights
1. **DFS Recursive**: Simple and intuitive, but may cause stack overflow for large images
2. **DFS Iterative**: Uses stack, avoids recursion depth issues
3. **BFS Iterative**: Uses queue, explores level by level
4. **Boundary Check**: Always check if coordinates are within bounds
5. **Same Color Check**: Only process pixels with the same original color as starting pixel

### Approach Selection
**Chosen Approach:** DFS Recursive  
**Why this approach?** 
- Simple and intuitive to implement
- Easy to understand the flood fill concept
- Works well within constraints (max 50x50 grid)
- Naturally expresses the recursive nature of flood fill

## ⚡ Complexity Analysis
- **Time Complexity:** O(m × n) - In worst case, we might visit every pixel
- **Space Complexity:** O(m × n) - For recursion stack in worst case

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Check if new color equals original color to avoid infinite recursion

Use 4-directional movement: up, down, left, right

Always check boundaries before accessing array elements

The algorithm is similar to finding connected components

🔗 Related Problems
Number of Islands

Surrounded Regions

Word Search

Island Perimeter

Max Area of Island
