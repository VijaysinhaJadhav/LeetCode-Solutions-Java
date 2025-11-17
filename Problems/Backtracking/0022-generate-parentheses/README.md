# 22. Generate Parentheses

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Dynamic Programming, Backtracking, Recursion  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe, Oracle

[LeetCode Link](https://leetcode.com/problems/generate-parentheses/)

Given `n` pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

**Example 1:**

Input: n = 3

Output: ["((()))","(()())","(())()","()(())","()()()"]


**Example 2:**

Input: n = 1

Output: ["()"]


**Constraints:**
- `1 <= n <= 8`

## 🧠 Thought Process

### Initial Thoughts
- Need to generate all valid combinations of n pairs of parentheses
- A valid combination must have balanced parentheses
- At any point, number of closing parentheses cannot exceed opening parentheses
- Total length will be 2n characters

### Key Insights
1. **Backtracking Approach**: Build strings character by character, tracking open and close counts
2. **Validity Conditions**: 
   - Can add '(' if open count < n
   - Can add ')' if close count < open count
3. **Dynamic Programming**: Build solutions for n from solutions for smaller n
4. **Catalan Numbers**: The number of valid combinations follows Catalan number sequence

### Approach Selection
**Chosen Approach:** Backtracking/DFS  
**Why this approach?** 
- Intuitive and easy to understand
- Efficiently generates all valid combinations
- Naturally handles the validity constraints
- O(4^n/√n) time complexity is optimal for this problem

## ⚡ Complexity Analysis
- **Time Complexity:** O(4^n/√n) - nth Catalan number behavior
- **Space Complexity:** O(4^n/√n) - For storing the output combinations

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to track the number of open and close parentheses

Only add ')' when there are more open parentheses to close

Backtracking naturally explores all possibilities while maintaining validity

The number of results is the nth Catalan number: C(2n,n)/(n+1)

🔗 Related Problems
Valid Parentheses

Letter Combinations of a Phone Number

Combination Sum

Permutations

Remove Invalid Parentheses
