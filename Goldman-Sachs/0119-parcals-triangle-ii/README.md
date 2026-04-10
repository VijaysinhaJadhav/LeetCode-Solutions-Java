# 119. Pascal's Triangle II

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Dynamic Programming  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/pascals-triangle-ii/)

Given an integer `rowIndex`, return the `rowIndex`-th (**0-indexed**) row of Pascal's triangle.

In Pascal's triangle, each number is the sum of the two numbers directly above it.

**Example 1:**

Input: rowIndex = 3
Output: [1,3,3,1]


**Example 2:**

Input: rowIndex = 0
Output: [1]


**Example 3:**

Input: rowIndex = 1
Output: [1,1]


**Constraints:**
- `0 <= rowIndex <= 33`

**Follow-up:** Could you optimize your algorithm to use only `O(rowIndex)` extra space?

## 🧠 Thought Process

### Problem Understanding
We need to generate only the `rowIndex`-th row of Pascal's triangle (0-indexed) without generating all previous rows.

### Key Insights
1. **Combinatorial Formula**: `C(rowIndex, k)` gives the k-th element (0-indexed)
2. **Space Optimization**: Can compute row iteratively using O(k) space
3. **Recurrence Relation**: Each element can be computed from previous row
4. **Symmetric Property**: Row is symmetric: `row[k] = row[n-k]`

### Approach Selection
**Chosen Approach:** Iterative with Single List (Space Optimized)  
**Why this approach?**
- O(rowIndex²) time, O(rowIndex) space
- Follows the follow-up requirement
- Builds row from previous row in-place

**Alternative Approaches:**
- **Combinatorial Formula**: O(rowIndex) time, O(1) space using multiplicative formula
- **Full Triangle Generation**: O(rowIndex²) time, O(rowIndex²) space

## ⚡ Complexity Analysis
- **Time Complexity:** O(rowIndex²) for iterative approach, O(rowIndex) for combinatorial
- **Space Complexity:** O(rowIndex) for storing the row

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a single list and update it from right to left to avoid overwriting needed values

First and last elements are always 1

For combinatorial approach, compute using multiplicative formula: C(n, k) = C(n, k-1) * (n - k + 1) / k

🔗 Related Problems
Pascal's Triangle
Pascal's Triangle (this problem's parent)
Pascal's Triangle II (this problem)
