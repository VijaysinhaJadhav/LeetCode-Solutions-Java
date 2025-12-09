# 131. Palindrome Partitioning

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Dynamic Programming, Backtracking, Depth-First Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, TikTok

[LeetCode Link](https://leetcode.com/problems/palindrome-partitioning/)

Given a string `s`, partition `s` such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of `s`.

**Example 1:**

Input: s = "aab"

Output: [["a","a","b"],["aa","b"]]


**Example 2:**

Input: s = "a"

Output: [["a"]]


**Constraints:**
- `1 <= s.length <= 16`
- `s` consists of only lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- Need to split the string into substrings where each substring is a palindrome
- Return all possible valid partitioning
- Can use backtracking to explore all possible splits
- Need efficient way to check if a substring is a palindrome

### Key Insights
1. **Backtracking Approach**: Try all possible partitions and backtrack when invalid
2. **Palindrome Precomputation**: Use DP to precompute palindrome information for O(1) checks
3. **DFS Exploration**: At each step, try all possible end positions for current partition
4. **Early Pruning**: Skip partitions that are not palindromes

### Approach Selection
**Chosen Approach:** Backtracking with Palindrome Precomputation  
**Why this approach?** 
- Efficiently explores all valid partitions
- O(1) palindrome checks with precomputation
- Clear and intuitive implementation
- O(n × 2^n) time complexity is acceptable given constraints

## ⚡ Complexity Analysis
- **Time Complexity:** O(n × 2^n) - In worst case, 2^n possible partitions
- **Space Complexity:** O(n²) - For DP table and recursion stack

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Precompute palindrome information using dynamic programming

Use backtracking to explore all valid partitions

At each step, extend current partition if it's a palindrome

Add to result when entire string is partitioned

🔗 Related Problems
Palindrome Partitioning II

Longest Palindromic Substring

Palindromic Substrings

Restore IP Addresses

Word Break II
