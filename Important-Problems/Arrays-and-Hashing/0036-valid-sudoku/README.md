# 36. Valid Sudoku

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Matrix  
**Companies:** Google, Amazon, Microsoft, Apple, Facebook, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/valid-sudoku/)

Determine if a `9 x 9` Sudoku board is valid. Only the filled cells need to be validated according to the following rules:

1. Each row must contain the digits `1-9` without repetition.
2. Each column must contain the digits `1-9` without repetition.
3. Each of the nine `3 x 3` sub-boxes of the grid must contain the digits `1-9` without repetition.

**Note:**
- A Sudoku board (partially filled) could be valid but is not necessarily solvable.
- Only the filled cells need to be validated according to the mentioned rules.

**Example 1:**

![Valid Sudoku](https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png)

Input: board =

[["5","3",".",".","7",".",".",".","."]

,["6",".",".","1","9","5",".",".","."]

,[".","9","8",".",".",".",".","6","."]

,["8",".",".",".","6",".",".",".","3"]

,["4",".",".","8",".","3",".",".","1"]

,["7",".",".",".","2",".",".",".","6"]

,[".","6",".",".",".",".","2","8","."]

,[".",".",".","4","1","9",".",".","5"]

,[".",".",".",".","8",".",".","7","9"]]

Output: true


**Example 2:**

Input: board =

[["8","3",".",".","7",".",".",".","."]

,["6",".",".","1","9","5",".",".","."]

,[".","9","8",".",".",".",".","6","."]

,["8",".",".",".","6",".",".",".","3"]

,["4",".",".","8",".","3",".",".","1"]

,["7",".",".",".","2",".",".",".","6"]

,[".","6",".",".",".",".","2","8","."]

,[".",".",".","4","1","9",".",".","5"]

,[".",".",".",".","8",".",".","7","9"]]

Output: false

Explanation: Same as Example 1, except with the 5 in the top left corner being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.


**Constraints:**
- `board.length == 9`
- `board[i].length == 9`
- `board[i][j]` is a digit `1-9` or `'.'`.

## 🧠 Thought Process

### Initial Thoughts
- Need to validate three constraints: rows, columns, and 3x3 sub-boxes
- Only check filled cells (ignore '.')
- Multiple approaches with different space/time trade-offs
- The challenge is efficiently checking all constraints

### Key Insights
1. **Three Sets Approach**: Use separate sets for rows, columns, and boxes
2. **Single Pass**: Check all three constraints in one pass through the board
3. **Bit Masking**: Use bit manipulation for space efficiency
4. **Array Approach**: Use boolean arrays instead of sets for better performance

### Approach Selection
**Chosen Approach:** Array Validation with Single Pass  
**Why this approach?** 
- O(1) time per cell, O(1) space overall (fixed size)
- Efficient and straightforward
- Easy to understand and implement
- Optimal for the fixed 9x9 board size

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) - Constant time since board is always 9x9
- **Space Complexity:** O(1) - Fixed size arrays for validation

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to generate unique identifiers for each constraint

For 3x3 boxes: boxIndex = (row / 3) * 3 + (col / 3)

We only process filled cells (skip '.')

The solution validates all three rules in a single pass

🔗 Related Problems
37. Sudoku Solver

1275. Find Winner on a Tic Tac Toe Game

36. Valid Sudoku

73. Set Matrix Zeroes

