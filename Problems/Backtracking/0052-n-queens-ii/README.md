# 52. N-Queens II

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Backtracking, Depth-First Search, Bit Manipulation  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/n-queens-ii/)

The n-queens puzzle is the problem of placing `n` queens on an `n x n` chessboard such that no two queens attack each other.

Given an integer `n`, return the number of distinct solutions to the n-queens puzzle.

**Example 1:**

Input: n = 4

Output: 2

Explanation: There are two distinct solutions to the 4-queens puzzle as shown.


**Example 2:**

Input: n = 1

Output: 1


**Constraints:**
- `1 <= n <= 9`

## 🧠 Thought Process

### Initial Thoughts
- Similar to N-Queens I, but we only need to count solutions, not generate them
- Can use the same backtracking approaches but optimized for counting
- Bit manipulation can be particularly efficient for counting
- No need to store or construct board configurations

### Key Insights
1. **Backtracking Approach**: Same as N-Queens I but count solutions instead of storing boards
2. **Bit Manipulation**: Most efficient for counting-only version
3. **Optimized Validation**: Use mathematical properties for diagonal checking
4. **Early Pruning**: Stop exploring invalid paths immediately

### Approach Selection
**Chosen Approach:** Backtracking with Bit Manipulation  
**Why this approach?** 
- Most efficient for counting problems
- O(1) conflict checking using bit operations
- Minimal memory usage
- Optimal for the given constraints

## ⚡ Complexity Analysis
- **Time Complexity:** O(n!) - In practice much better due to pruning
- **Space Complexity:** O(n) - For recursion stack

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Same core logic as N-Queens I, but optimized for counting

Bit manipulation provides the best performance

No need to construct or store board configurations

Can reuse validation logic from N-Queens I

🔗 Related Problems
N-Queens

Sudoku Solver

Permutations

Combinations
