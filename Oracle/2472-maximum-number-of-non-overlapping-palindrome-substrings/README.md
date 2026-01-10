# 2472. Maximum Number of Non-overlapping Palindrome Substrings

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** String, Dynamic Programming, Greedy  
**Companies:** Google, Amazon, Microsoft, Meta, Bloomberg

[LeetCode Link](https://leetcode.com/problems/maximum-number-of-non-overlapping-palindrome-substrings/)

You are given a string `s` and a positive integer `k`.

Return *the maximum number of **non-overlapping** substrings* you can obtain from `s` such that:
1. Each substring is a **palindrome**.
2. The length of each substring is **at least** `k`.
3. Substrings do **not** overlap.

A **substring** is a contiguous sequence of characters within a string.

**Example 1:**

Input: s = "abaccdbbd", k = 3
Output: 2
Explanation:
We can select "aba" and "dbbd" as non-overlapping palindrome substrings.
Other valid substrings: "acca", "bb", but they give fewer substrings.


**Example 2:**

Input: s = "adbcda", k = 2
Output: 0
Explanation:
There is no palindrome substring of length at least 2.


**Constraints:**
- `1 <= k <= s.length <= 2000`
- `s` consists only of lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- Need to find maximum non-overlapping palindrome substrings of length ≥ k
- Overlapping not allowed, so need to choose wisely
- Multiple approaches: DP, greedy, interval scheduling
- Key challenge: balance between finding palindromes and avoiding overlaps

### Key Insights
1. **Palindrome Preprocessing:**
   - Precompute all palindrome substrings of length ≥ k
   - Use DP or expand around center
   - Store as intervals [start, end]
2. **Interval Scheduling:**
   - Problem reduces to: given intervals (palindromes), find max non-overlapping
   - Classic greedy: sort by end time, pick earliest ending that doesn't overlap
   - But intervals have value = 1 (each palindrome counts as 1)
3. **Dynamic Programming:**
   - dp[i] = max palindromes in s[0:i]
   - For each position i, either skip or end palindrome at i
   - Need to know palindrome starting positions
4. **Greedy Optimization:**
   - For each position, find shortest palindrome starting there
   - Take it, move to next non-overlapping position
   - Works because shorter palindrome leaves more room for others
5. **Manacher's Algorithm:**
   - O(n) palindrome preprocessing
   - Find all palindromes efficiently

### Approach Selection
**Primary Approach:** Dynamic Programming with Palindrome Preprocessing  
**Why this approach?** 
- O(n²) time complexity (acceptable for n ≤ 2000)
- O(n²) space for palindrome table
- Guarantees optimal solution
- Clear and systematic

**Alternative:** Greedy with Expand Around Center  
**Why this approach?**
- Also O(n²) time
- Might be simpler
- Uses interval scheduling idea

## ⚡ Complexity Analysis
- **Time Complexity:** O(n²) - Precompute palindromes + DP
- **Space Complexity:** O(n²) for palindrome table, O(n) for DP

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Precompute palindrome table using DP: isPal[i][j] = true if s[i..j] is palindrome

DP transition: dp[i] = max(dp[i-1], max over j where isPal[j][i] and length ≥ k: dp[j-1] + 1)

Alternatively: treat as interval scheduling problem

Can optimize space using 1D DP

🔗 Related Problems
5. Longest Palindromic Substring

647. Palindromic Substrings

132. Palindrome Partitioning II

516. Longest Palindromic Subsequence

1278. Palindrome Partitioning III
