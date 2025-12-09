# 51. N-Queens

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Backtracking, Depth-First Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe, Oracle

[LeetCode Link](https://leetcode.com/problems/n-queens/)

The n-queens puzzle is the problem of placing `n` queens on an `n x n` chessboard such that no two queens attack each other.

Given an integer `n`, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.

Each solution contains a distinct board configuration of the n-queens' placement, where `'Q'` and `'.'` both indicate a queen and an empty space, respectively.

**Example 1:**

Input: n = 4

Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]

Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above.

**Example 2:**

Input: n = 1

Output: [["Q"]]


**Constraints:**
- `1 <= n <= 9`

## 🧠 Thought Process

### Initial Thoughts
- Need to place n queens on n×n chessboard with no two queens attacking each other
- Queens attack in same row, same column, or same diagonal
- Each row must have exactly one queen
- Can use backtracking to try all possible placements

### Key Insights
1. **Backtracking Approach**: Place queens row by row, backtracking when conflicts occur
2. **Constraint Tracking**: Use sets or arrays to track occupied columns and diagonals
3. **Optimized Validation**: O(1) conflict checking using mathematical properties
4. **Diagonal Properties**: 
   - Main diagonal: row - column = constant
   - Anti-diagonal: row + column = constant

### Approach Selection
**Chosen Approach:** Backtracking with Optimized Validation  
**Why this approach?** 
- Systematically explores all valid placements
- Efficient O(1) conflict detection
- Clear and intuitive implementation
- Handles the exponential nature of the problem optimally

## ⚡ Complexity Analysis
- **Time Complexity:** O(n!) - In practice much better due to pruning
- **Space Complexity:** O(n) - For tracking columns and diagonals

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation
