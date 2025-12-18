# 72. Edit Distance

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** String, Dynamic Programming  
**Companies:** Google, Amazon, Microsoft, Meta, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/edit-distance/)

Given two strings `word1` and `word2`, return *the minimum number of operations required to convert `word1` to `word2`*.

You have the following three operations permitted on a word:
- Insert a character
- Delete a character
- Replace a character

**Example 1:**

Input: word1 = "horse", word2 = "ros"

Output: 3

Explanation:

horse -> rorse (replace 'h' with 'r')

rorse -> rose (remove 'r')

rose -> ros (remove 'e')


**Example 2:**

Input: word1 = "intention", word2 = "execution"

Output: 5

Explanation:

intention -> inention (remove 't')

inention -> enention (replace 'i' with 'e')

enention -> exention (replace 'n' with 'x')

exention -> exection (replace 'n' with 'c')

exection -> execution (insert 'u')


**Constraints:**
- `0 <= word1.length, word2.length <= 500`
- `word1` and `word2` consist of lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- Classic dynamic programming problem (Levenshtein distance)
- Need to find minimum edit operations: insert, delete, replace
- Operations can be applied in any order
- Each operation has equal cost (1 unit)

### Key Insights
1. **DP State Definition**: `dp[i][j]` = min edits to convert first i chars of word1 to first j chars of word2
2. **Base Cases**:
   - `dp[0][j] = j` (insert j characters)
   - `dp[i][0] = i` (delete i characters)
3. **Recurrence Relation**:
   - If `word1[i-1] == word2[j-1]`: `dp[i][j] = dp[i-1][j-1]` (no edit needed)
   - Else: `dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])`
     - `dp[i-1][j]` = delete from word1
     - `dp[i][j-1]` = insert into word1  
     - `dp[i-1][j-1]` = replace character
4. **Space Optimization**: Can use 2 rows instead of full matrix

### Approach Selection
**Chosen Approach:** Dynamic Programming with Space Optimization  
**Why this approach?** 
- O(m*n) time complexity
- O(min(m,n)) space complexity
- Standard solution for edit distance
- Handles all operations optimally

## ⚡ Complexity Analysis
- **Time Complexity:** O(m*n) where m = len(word1), n = len(word2)
- **Space Complexity:** O(min(m,n)) with space optimization

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Levenshtein distance algorithm

DP with 2D table or optimized with 2 rows

Operations: insert, delete, replace (all cost 1)

Can be extended with different operation costs

🔗 Related Problems
Delete Operation for Two Strings

Minimum ASCII Delete Sum for Two Strings

Regular Expression Matching

Wildcard Matching

Interleaving String

Distinct Subsequences
