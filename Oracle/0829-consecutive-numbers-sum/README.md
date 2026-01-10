# 829. Consecutive Numbers Sum

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Math, Enumeration  
**Companies:** Google, Amazon, Microsoft, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/consecutive-numbers-sum/)

Given an integer `n`, return *the number of ways you can write `n` as the sum of consecutive positive integers*.

**Example 1:**

Input: n = 5
Output: 2
Explanation: 5 = 2 + 3 = 5


**Example 2:**

Input: n = 9
Output: 3
Explanation: 9 = 4 + 5 = 2 + 3 + 4 = 9


**Example 3:**

Input: n = 15
Output: 4
Explanation: 15 = 7 + 8 = 4 + 5 + 6 = 1 + 2 + 3 + 4 + 5 = 15


**Constraints:**
- `1 <= n <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- Need to find all sequences of consecutive positive integers that sum to n
- Brute force: try all starting points and lengths → O(n²) too slow for n up to 10^9
- Need mathematical insight

### Key Insights
1. **Mathematical Representation:**
   - Sequence of k consecutive numbers starting at a: a, a+1, a+2, ..., a+(k-1)
   - Sum = k*a + (0 + 1 + 2 + ... + (k-1)) = k*a + k*(k-1)/2
   - Equation: n = k*a + k*(k-1)/2
2. **Rearranging:**
   - k*a = n - k*(k-1)/2
   - a = [n - k*(k-1)/2] / k
   - For valid sequence: a must be positive integer
3. **Constraints on k:**
   - k*(k-1)/2 ≤ n (since n must be positive)
   - k ≤ √(2n) approximately
   - Actually k*(k+1)/2 ≤ n gives k ≤ √(2n)
4. **Checking Conditions:**
   - n - k*(k-1)/2 must be divisible by k
   - Result must be positive
5. **Alternative View:**
   - n = sum of k consecutive numbers starting at a
   - Can be written as n = k*m where m is average
   - For odd k: m is integer, sequence centered at m
   - For even k: m is half-integer

### Approach Selection
**Chosen Approach:** Mathematical Enumeration  
**Why this approach?** 
- O(√n) time complexity (optimal)
- O(1) space complexity
- Based on mathematical derivation
- Handles large n up to 10^9 efficiently

## ⚡ Complexity Analysis
- **Time Complexity:** O(√n) - Iterate k from 1 while k*(k-1)/2 < n
- **Space Complexity:** O(1) - Only a few variables

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Key formula: n = ka + k(k-1)/2

Check if (n - k*(k-1)/2) is divisible by k and positive

Maximum k when k*(k-1)/2 = n → k ≈ √(2n)

Always count k=1 (n itself)

🔗 Related Problems
413. Arithmetic Slices

446. Arithmetic Slices II - Subsequence

829. Consecutive Numbers Sum (this problem)

1502. Can Make Arithmetic Progression From Sequence

1630. Arithmetic Subarrays
