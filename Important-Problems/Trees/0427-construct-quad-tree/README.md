# 427. Construct Quad Tree

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Divide and Conquer, Tree, Matrix  
**Companies:** Google, Amazon, Microsoft, Bloomberg, Apple

[LeetCode Link](https://leetcode.com/problems/construct-quad-tree/)

Given a `n * n` matrix `grid` of `0's` and `1's` only. We want to represent the `grid` with a Quad-Tree.

Return the root of the Quad-Tree representing the `grid`.

Notice that you can assign the value of a node to **True** or **False** when `isLeaf` is **False**, and both are **accepted** in the answer.

A Quad-Tree is a tree data structure in which each internal node has exactly four children. Besides, each node has two attributes:

- `val`: True if the node represents a grid of 1's or False if the node represents a grid of 0's. 
- `isLeaf`: True if the node is leaf node on the tree or False if the node has the four children.

We can construct a Quad-Tree from a two-dimensional area using the following steps:

1. If the current grid has the same value (i.e., all `1's` or all `0's`) set `isLeaf` True and set `val` to the value of the grid and set the four children to Null and stop.
2. If the current grid has different values, set `isLeaf` to False and set `val` to any value and divide the current grid into four sub-grids as shown in the photo.
3. Recurse for each of the children with the proper sub-grid.

**Quad-Tree format:**

The output represents the serialized format of a Quad-Tree using level order traversal, where `null` signifies a path terminator where no node exists below.

It is very similar to the serialization of the binary tree. The only difference is that the node is represented as a list `[isLeaf, val]`.

If the value of `isLeaf` or `val` is True we represent it as **1** in the list `[isLeaf, val]` and if the value of `isLeaf` or `val` is False we represent it as **0**.

**Example 1:**  

Input: grid = [[0,1],[1,0]]

Output: [[0,1],[1,0],[1,1],[1,1],[1,0]]

Explanation: The explanation of this example is shown below:

Notice that 0 represents False and 1 represents True in the photo representing the Quad-Tree.


**Example 2:**

Input: grid = [[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0],[1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1],[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0]]

Output: [[0,1],[1,1],[0,1],[1,1],[1,0],null,null,null,null,[1,0],[1,0],[1,1],[1,1]]

Explanation: All values in the grid are not the same. We divide the grid into four sub-grids.

The topLeft, bottomLeft and bottomRight each has the same value.

The topRight have different values so we divide it into 4 sub-grids where each has the same value.


**Constraints:**
- `n == grid.length == grid[i].length`
- `n == 2^x` where `0 <= x <= 6`

## 🧠 Thought Process

### Initial Thoughts
- Quad Tree is a tree where each internal node has exactly 4 children
- Need to recursively divide the grid into 4 quadrants
- Base case: when all values in current subgrid are same
- Recursive case: when values differ, create 4 children for each quadrant

### Key Insights
1. **Divide and Conquer**: Split grid into 4 equal quadrants recursively
2. **Base Case**: When all values in subgrid are same, create leaf node
3. **Recursive Case**: When values differ, create internal node and recurse
4. **Grid Division**: Top-left, top-right, bottom-left, bottom-right quadrants

### Approach Selection
**Recommended Approaches:**
1. **Recursive Divide and Conquer** - Most intuitive and clean
2. **Iterative with Stack** - Alternative iterative approach
3. **Optimized Recursive** - With bounds checking and early termination

## ⚡ Complexity Analysis

### Recursive Approach:
- **Time Complexity:** O(n²) - Each cell visited once
- **Space Complexity:** O(log n) - Recursion stack depth
- **Worst Case:** O(n²) when tree is fully decomposed

## 🔍 Solution Code

```java
// See Solution.java for complete implementations

📝 Notes
Quad Tree nodes have exactly 0 or 4 children

Leaf nodes represent uniform regions (all 0s or all 1s)

Internal nodes represent mixed regions

Grid size is always power of 2

Output uses level-order serialization

🔗 Related Problems
Logical OR of Two Binary Grids Represented as Quad-Trees

Quad Tree Intersection

N-ary Tree Level Order Traversal

Invert Binary Tree

