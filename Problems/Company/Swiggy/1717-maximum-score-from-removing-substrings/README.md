# 1717. Maximum Score From Removing Substrings

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Stack, Greedy  
**Companies:** Google, Microsoft, Amazon

[LeetCode Link](https://leetcode.com/problems/maximum-score-from-removing-substrings/)

You are given a string `s` and two integers `x` and `y`. You can perform two types of removal operations on the string:

1. Remove **substring "ab"** and gain `x` points.
   - For example, removing "ab" from "cabxbae" results in "cxbae".
   
2. Remove **substring "ba"** and gain `y` points.
   - For example, removing "ba" from "cabxbae" results in "caxbae".

Return *the maximum points you can gain* by applying the above operations **any number of times**.

**Example 1:**

Input: s = "cdbcbbaaabab", x = 4, y = 5

Output: 19

Explanation:

Remove "ba" to get "cdbcbbaaab" and earn 5 points.

Remove "ba" to get "cdbcbbaa" and earn 5 points.

Remove "ba" to get "cdbcba" and earn 5 points.

Remove "ab" to get "cdbc" and earn 4 points.

Total score = 5 + 5 + 5 + 4 = 19.


**Example 2:**

Input: s = "aabbaaxybbaabb", x = 5, y = 4

Output: 20


**Constraints:**
- `1 <= s.length <= 10^5`
- `1 <= x, y <= 10^4`
- `s` consists of lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- We need to maximize points by removing substrings "ab" and "ba"
- Can perform operations in any order
- Removing one substring may create new opportunities for other removals
- Similar to parenthesis matching problems

### Key Insights
1. **Stack-Based Approach**: Similar to valid parentheses
2. **Greedy Strategy**: Always remove the higher-scoring substring first
3. **Two-Pass Process**:
   - First pass: Remove all occurrences of higher-scoring pattern
   - Second pass: Remove remaining occurrences of lower-scoring pattern
4. **Optimal Removal Order**: Since operations can create new patterns, order matters

### Approach Selection
**Chosen Approach:** Two-Pass Stack with Greedy Order  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity for stack
- Handles creation of new patterns optimally
- Simple and efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Two passes through the string
- **Space Complexity:** O(n) - Stack in worst case

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Determine which pattern has higher score: "ab" or "ba"

Use stack to efficiently remove patterns

First remove all higher-scoring patterns

Then remove remaining lower-scoring patterns

Total score = (count_high * high_score) + (count_low * low_score)

🔗 Related Problems
Remove All Adjacent Duplicates In String

Remove All Adjacent Duplicates in String II

Minimum Remove to Make Valid Parentheses

Make The String Great
