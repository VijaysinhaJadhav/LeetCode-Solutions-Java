# 2484. Count Palindromic Subsequences

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** String, Dynamic Programming, Counting  
**Companies:** Google, Amazon, Microsoft  

[LeetCode Link](https://leetcode.com/problems/count-palindromic-subsequences/)

Given a string `s`, return the **number of distinct palindromic subsequences** of length exactly 4.

Since the answer can be very large, return it **modulo** `10^9 + 7`.

**Note:** Two sequences are considered different if they differ in at least one character, even if they come from the same indices.

**Example 1:**

Input: s = "103301"
Output: 2
Explanation: The distinct palindromic subsequences of length 4 are:

"1001" (indices 0, 1, 4, 5)

"1331" (indices 0, 2, 3, 5)


**Example 2:**

Input: s = "0000000"
Output: 1
Explanation: Only "0000" is a palindromic subsequence of length 4.


**Example 3:**

Input: s = "1111111"
Output: 1


**Constraints:**
- `4 <= s.length <= 2000`
- `s` consists of only digits `'0'` to `'9'`.

## 🧠 Thought Process

### Problem Understanding
Count distinct palindromic subsequences of length exactly 4. A palindrome of length 4 has the form `a b b a` (or `a b c a` if different middle characters). Since length is 4, the structure is fixed: positions i < j < k < l such that `s[i] == s[l]` and `s[j] == s[k]`.

### Key Insights
1. **Structure**: For a 4-length palindrome `a b b a` or `a b c a` (if b != c), we need `s[i] == s[l]` and `s[j] == s[k]`.
2. **Fix Outer Characters**: Iterate over all pairs of positions (i, l) where `s[i] == s[l]` and `l - i >= 3`.
3. **Count Middle Pairs**: Count number of distinct pairs (j, k) with i < j < k < l and `s[j] == s[k]`.
4. **Deduplication**: Use sets to ensure distinct subsequences.

### Approach Selection
**Chosen Approach:** Fix Outer Characters + Count Middle Pairs  
**Why this approach?**
- O(n² × 10) time complexity (n=2000 → ~40M operations)
- O(1) extra space for counting
- Clear and manageable

**Alternative Approaches:**
- **Dynamic Programming**: O(n² × 10) but more complex
- **Precompute Prefix/Suffix Counts**: O(n²) with O(1) queries

## ⚡ Complexity Analysis
- **Time Complexity:** O(10 × n²) ≈ O(n²)
- **Space Complexity:** O(1) extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use digits 0-9 as characters

For each outer pair (i, l) with same digit, count distinct middle pairs (j, k)

Middle pairs require s[j] == s[k]

Use a set to track seen middle digit pairs

Add result modulo 10⁹+7

🔗 Related Problems
Count Different Palindromic Subsequences
Longest Palindromic Subsequence
Longest Palindromic Substring
Palindromic Substrings
Count Palindromic Subsequences (this problem)
