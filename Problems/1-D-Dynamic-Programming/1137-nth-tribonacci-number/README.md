# 1137. N-th Tribonacci Number

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Math, Dynamic Programming, Memoization  
**Companies:** Amazon, Google, Adobe

[LeetCode Link](https://leetcode.com/problems/n-th-tribonacci-number/)

The Tribonacci sequence Tₙ is defined as follows: 

T₀ = 0, T₁ = 1, T₂ = 1, and Tₙ₊₃ = Tₙ + Tₙ₊₁ + Tₙ₊₂ for n >= 0.

Given `n`, return the value of Tₙ.

**Example 1:**

Input: n = 4

Output: 4

Explanation:

T_3 = 0 + 1 + 1 = 2

T_4 = 1 + 1 + 2 = 4


**Example 2:**

Input: n = 25

Output: 1389537


**Constraints:**
- `0 <= n <= 37`
- The answer is guaranteed to fit within a 32-bit integer, i.e. `answer <= 2^31 - 1`.

## 🧠 Thought Process

### Initial Thoughts
- Similar to Fibonacci but with three terms instead of two
- Can use dynamic programming to build solution iteratively
- Multiple approaches: iterative DP, recursive with memoization, matrix exponentiation
- Constraints are small (n ≤ 37), so even recursive solution is feasible

### Key Insights
1. **Dynamic Programming**: Build solution bottom-up using previous three values
2. **Space Optimization**: Only need to store last three values, not entire sequence
3. **Base Cases**: T₀ = 0, T₁ = 1, T₂ = 1
4. **Recurrence Relation**: Tₙ = Tₙ₋₁ + Tₙ₋₂ + Tₙ₋₃ for n ≥ 3

### Approach Selection
**Chosen Approach:** Iterative Dynamic Programming with Space Optimization  
**Why this approach?** 
- O(n) time complexity is optimal
- O(1) space complexity is efficient
- Simple and intuitive implementation
- Handles all edge cases properly

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass up to n
- **Space Complexity:** O(1) - Only three variables needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Handle base cases (n = 0, 1, 2) explicitly

For n ≥ 3, use iterative approach with three variables

Can also solve with matrix exponentiation for O(log n) time

Constraints guarantee no integer overflow

🔗 Related Problems
Fibonacci Number

Climbing Stairs

Min Cost Climbing Stairs

Restore The Array
