# 2707. Extra Characters in a String

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, String, Dynamic Programming, Trie  
**Companies:** Google, Amazon, Microsoft, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/extra-characters-in-a-string/)

You are given a **0-indexed** string `s` and a dictionary of words `dictionary`. You have to break `s` into one or more **non-overlapping** substrings such that each substring is present in `dictionary`. There may be some **extra characters** in `s` which are not present in any of the substrings.

Return *the **minimum** number of extra characters left over if you break up* `s` *optimally.*

**Example 1:**

Input: s = "leetscode", dictionary = ["leet","code","leetcode"]
Output: 1
Explanation: We can break s in two substrings: "leet" from index 0 to 3 and "code" from index 5 to 8. There is only 1 unused character (at index 4), so we return 1.


**Example 2:**

Input: s = "sayhelloworld", dictionary = ["hello","world"]
Output: 3
Explanation: We can break s in two substrings: "hello" from index 3 to 7 and "world" from index 8 to 12. The characters at indices 0, 1, 2 are not used in any substring and thus are considered as extra characters. Hence, we return 3.


**Constraints:**
- `1 <= s.length <= 50`
- `1 <= dictionary.length <= 50`
- `1 <= dictionary[i].length <= 50`
- `dictionary[i]` and `s` consist of only lowercase English letters.
- `dictionary` contains distinct words.

## 🧠 Thought Process

### Problem Understanding
We need to partition the string into valid dictionary words (non-overlapping) to minimize the number of leftover characters. Each dictionary word can be reused multiple times.

### Key Insights
1. **Dynamic Programming Nature**: At each position, we decide whether to match a word or skip the character
2. **Optimal Substructure**: The solution from position i depends on solutions from later positions [citation:1]
3. **Dictionary Lookup**: Need efficient O(1) word checking (HashSet) or prefix matching (Trie)
4. **Small Constraints**: s.length ≤ 50 allows O(n³) solutions

### Approach Selection
**Chosen Approach:** Dynamic Programming (Bottom-Up) with HashSet  
**Why this approach?**
- O(n³) time complexity is acceptable given constraints
- Simple and intuitive implementation
- No recursion overhead

**Alternative Approaches:**
- Top-down DP with memoization [citation:5]
- Trie + DP for optimized prefix matching [citation:1]
- Iterating over dictionary words instead of all substrings [citation:5]

## ⚡ Complexity Analysis
- **Time Complexity:** O(n³) where n = s.length [citation:1][citation:5]
  - n² substrings × O(n) substring operations
- **Space Complexity:** O(n + m·k) where m = dictionary size, k = average word length [citation:1]
  - O(n) for DP array + dictionary storage

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Always convert dictionary to HashSet for O(1) lookups

Process string from right to left in bottom-up DP 

The DP state dp[i] represents minimum extra characters from index i to end

Base case: dp[n] = 0 (no characters left)

🔗 Related Problems
Word Break
Word Break II
Concatenated Words
Implement Trie (Prefix Tree)
Design Add and Search Words Data Structure
