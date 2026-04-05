# 657. Robot Return to Origin

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** String, Simulation, Counting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/robot-return-to-origin/)

There is a robot starting at the position `(0, 0)`, the origin, on a 2D plane. Given a sequence of its moves, judge if this robot **ends up at `(0, 0)`** after it completes its moves.

The move sequence is represented by a string, and the character `moves[i]` represents its `i`-th move. Valid moves are `'R'` (right), `'L'` (left), `'U'` (up), and `'D'` (down).

Return `true` if the robot returns to the origin after finishing all of its moves, or `false` otherwise.

**Note:** The way that the robot is "facing" is irrelevant. `'R'` will always make the robot move to the right, `'L'` to the left, `'U'` to the up, and `'D'` to the down.

**Example 1:**

Input: moves = "UD"
Output: true
Explanation: The robot moves up once, then down once. It ends at the origin.


**Example 2:**

Input: moves = "LL"
Output: false
Explanation: The robot moves left twice. It ends at (-2, 0).


**Example 3:**

Input: moves = "RRDD"
Output: false
Explanation: The robot moves right twice, then down twice. It ends at (2, -2).


**Example 4:**

Input: moves = "LDRRLRUULR"
Output: false


**Constraints:**
- `1 <= moves.length <= 2 * 10^4`
- `moves` only contains the characters `'U'`, `'D'`, `'L'`, and `'R'`.

## 🧠 Thought Process

### Problem Understanding
We need to simulate robot movement and check if it returns to the starting point `(0, 0)`.

### Key Insights
1. **Counting Approach**: Count net displacement in each axis
2. **Simulation**: Track current position
3. **Simple Math**: For every 'U', need a 'D'; for every 'L', need an 'R'
4. **One Pass**: O(n) solution is optimal

### Approach Selection
**Chosen Approach:** Counting (Net Displacement)  
**Why this approach?**
- O(n) time complexity
- O(1) space complexity
- Simple and efficient

**Alternative Approaches:**
- **Simulation**: Track actual (x, y) position
- **HashMap Count**: Count frequencies of each move

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of moves string
- **Space Complexity:** O(1)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The robot's orientation doesn't matter, only net displacement

Count 'U' vs 'D' and 'L' vs 'R'

Return true if counts are equal in both axes

🔗 Related Problems
Robot Bounded In Circle
Walking Robot Simulation
Count Vowel Substrings of a String
Find Nearest Point That Has the Same X or Y Coordinate
Minimum Time Visiting All Points
