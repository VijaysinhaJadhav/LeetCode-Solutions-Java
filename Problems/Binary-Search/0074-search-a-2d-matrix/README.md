# 74. Search a 2D Matrix

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search, Matrix  
**Companies:** Amazon, Google, Microsoft, Apple, Bloomberg, Facebook, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/search-a-2d-matrix/)

You are given an `m x n` integer matrix `matrix` with the following two properties:
- Each row is sorted in non-decreasing order.
- The first integer of each row is greater than the last integer of the previous row.

Given an integer `target`, return `true` if `target` is in `matrix` or `false` otherwise.

You must write a solution in `O(log(m * n))` time complexity.

**Example 1:**

Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3

Output: true


**Example 2:**

Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13

Output: false


**Constraints:**
- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 100`
- `-10^4 <= matrix[i][j], target <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Matrix has two key properties that make binary search possible:
  1. Each row is sorted
  2. First element of each row > last element of previous row
- This means the entire matrix can be treated as one sorted array
- O(log(m * n)) requirement suggests binary search

### Key Insights
1. **Flattened View**: The matrix can be treated as a 1D sorted array of size m*n
2. **Coordinate Mapping**: Convert 1D index to 2D coordinates using division and modulus
3. **Two Binary Searches**: Alternatively, search row first, then search within row
4. **Efficient Search**: Single binary search is more elegant and efficient

### Approach Selection
**Chosen Approach:** Single Binary Search (Flattened Matrix)  
**Why this approach?** 
- O(log(m * n)) time complexity meets requirements
- Simple and elegant implementation
- Leverages the sorted properties directly
- Single search is more efficient than two searches

## ⚡ Complexity Analysis
- **Time Complexity:** O(log(m * n)) - Single binary search over all elements
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key insight is that the matrix properties allow treating it as one sorted array

Coordinate mapping: row = mid / n, col = mid % n

Alternative two-search approach: first find correct row, then search within that row

🔗 Related Problems
Search a 2D Matrix II

Search in Rotated Sorted Array

Find First and Last Position of Element in Sorted Array

Search Insert Position
