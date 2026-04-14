# 688. Knight Probability in Chessboard

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Dynamic Programming, Probability, Memoization  
**Companies:** Google, Amazon, Microsoft, Apple  

[LeetCode Link](https://leetcode.com/problems/knight-probability-in-chessboard/)

On an `n x n` chessboard, a knight starts at the cell `(row, column)` and attempts to make exactly `k` moves. The knight moves are randomly chosen from its 8 possible moves with equal probability.

Return the **probability** that the knight remains on the board after `k` moves.

**Example 1:**

Input: n = 3, k = 2, row = 0, column = 0
Output: 0.06250
Explanation: There are two moves (to (1,2), (2,1)) that will keep the knight on the board.
From each of those positions, there are moves that keep the knight on the board.


**Example 2:**

Input: n = 1, k = 0, row = 0, column = 0
Output: 1.00000


**Constraints:**
- `1 <= n <= 25`
- `0 <= k <= 100`
- `0 <= row, column <= n - 1`

## 🧠 Thought Process

### Problem Understanding
A knight moves in an "L" shape: 2 steps in one direction, 1 step perpendicular. We need the probability that after k random moves, the knight is still on the board.

### Key Insights
1. **Probability Calculation**: Probability = (number of valid paths) / (8^k)
2. **DP State**: `dp[steps][r][c]` = probability of being at (r, c) after `steps` moves
3. **Memoization**: Use recursion with memoization for efficiency
4. **Base Case**: When `k = 0`, probability = 1 if on board, else 0

### Approach Selection
**Chosen Approach:** Dynamic Programming with Memoization  
**Why this approach?**
- O(k × n²) time complexity
- O(k × n²) space complexity
- Clear recurrence relation

**Alternative Approaches:**
- **BFS with Probability**: O(k × n²) time, similar complexity
- **Matrix Exponentiation**: O(n³ log k), more complex

## ⚡ Complexity Analysis
- **Time Complexity:** O(k × n²) for DP, O(8^k) for brute force
- **Space Complexity:** O(k × n²) for DP table

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use memoization to avoid redundant calculations

Knight moves: (±2, ±1) and (±1, ±2) — 8 possible moves

Probability = (sum of probabilities from all valid next positions) / 8

Use double for probability to avoid integer division

🔗 Related Problems
Knight Probability in Chessboard (this problem)
Out of Boundary Paths
Knight Dialer
Unique Paths II
Minimum Path Sum
