# 118. Pascal's Triangle

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Dynamic Programming  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/pascals-triangle/)

Given an integer `numRows`, return the first `numRows` of **Pascal's triangle**.

In Pascal's triangle, each number is the sum of the two numbers directly above it.

**Example 1:**

Input: numRows = 5
Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]


**Example 2:**

Input: numRows = 1
Output: [[1]]


**Constraints:**
- `1 <= numRows <= 30`

## 🧠 Thought Process

### Problem Understanding
We need to generate Pascal's triangle up to `numRows`. Each row starts and ends with 1, and each inner element is the sum of the two elements above it.

### Key Insights
1. **First Row**: Always `[1]`
2. **Row Properties**: Row `i` has `i + 1` elements
3. **Recurrence**: `triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j]`
4. **Boundary Elements**: First and last elements of each row are always 1

### Approach Selection
**Chosen Approach:** Dynamic Programming (Iterative)  
**Why this approach?**
- O(numRows²) time complexity
- O(numRows²) space for storing result
- Simple and intuitive

**Alternative Approaches:**
- **Combinatorial Formula**: C(n, k) = n! / (k! * (n-k)!)
- **Recursive Construction**: Build row by row

## ⚡ Complexity Analysis
- **Time Complexity:** O(numRows²) where numRows = number of rows
- **Space Complexity:** O(numRows²) for storing the triangle

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Each row can be built from the previous row

The first and last elements are always 1

Inner elements are sum of two elements from previous row

The number of elements in row i is i + 1

🔗 Related Problems
Pascal's Triangle II
Triangle
Pascal's Triangle (this problem)
Pascal's Triangle II (get single row)
