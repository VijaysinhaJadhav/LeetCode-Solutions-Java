# 2375. Construct Smallest Number From DI String

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Backtracking, Stack, Greedy  
**Companies:** Google, Amazon, Microsoft, Apple  

[LeetCode Link](https://leetcode.com/problems/construct-smallest-number-from-di-string/)

You are given a **0-indexed** string `pattern` of length `n` consisting of the characters `'I'` (meaning "increasing") and `'D'` (meaning "decreasing").

You need to construct a **permutation** of the numbers from `1` to `n + 1` that follows the given pattern:

- If `pattern[i] == 'I'`, then `result[i] < result[i + 1]`.
- If `pattern[i] == 'D'`, then `result[i] > result[i + 1]`.

Return the **lexicographically smallest** possible permutation that satisfies the pattern.

**Example 1:**

Input: pattern = "IDID"
Output: "13254"
Explanation: The pattern is I D I D:

1 < 3

3 > 2

2 < 5

5 > 4


**Example 2:**

Input: pattern = "III"
Output: "1234"


**Example 3:**

Input: pattern = "DDI"
Output: "3214"


**Constraints:**
- `1 <= pattern.length <= 8`
- `pattern[i]` is either `'I'` or `'D'`.

## 🧠 Thought Process

### Problem Understanding
We need to generate the smallest number (as a string) of length `n+1` using digits 1..n+1 exactly once, such that the relative ordering between consecutive digits follows the pattern ('I' = increasing, 'D' = decreasing).

### Key Insights
1. **Greedy Stack Approach**: Process pattern from left to right, use stack to handle decreasing sequences
2. **Backtracking**: Try digits 1..n+1 in order, validate against pattern
3. **Observation**: For a run of consecutive 'D's, we need a decreasing sequence

### Approach Selection
**Chosen Approach:** Stack-based Greedy  
**Why this approach?**
- O(n) time complexity
- O(n) space complexity
- Produces lexicographically smallest result
- Simple and efficient

**Alternative Approaches:**
- **Backtracking**: O((n+1)!) time - too slow
- **Two Pointers**: O(n) but more complex

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of pattern
- **Space Complexity:** O(n) for the stack/result

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a stack to handle 'D' sequences: push numbers, then pop on 'I' or at end

Start with number = 1, increment after each push

When encountering 'I', pop all from stack to result

After processing pattern, pop remaining numbers from stack

Result is built in correct order

🔗 Related Problems
Find Permutation
DI String Match
Next Permutation
Permutations
Permutation Sequence
