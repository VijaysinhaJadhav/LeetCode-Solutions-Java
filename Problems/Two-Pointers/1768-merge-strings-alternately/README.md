# 1768. Merge Strings Alternately

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** String, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/merge-strings-alternately/)

You are given two strings `word1` and `word2`. Merge the strings by adding letters in alternating order, starting with `word1`. If a string is longer than the other, append the additional letters onto the end of the merged string.

Return the merged string.

**Example 1:**

Input: word1 = "abc", word2 = "pqr"

Output: "apbqcr"

Explanation: The merged string will be merged as so:

word1: a b c

word2: p q r

merged: a p b q c r


**Example 2:**

Input: word1 = "ab", word2 = "pqrs"

Output: "apbqrs"

Explanation: Notice that as word2 is longer, "rs" is appended to the end.

word1: a b

word2: p q r s

merged: a p b q r s


**Example 3:**

Input: word1 = "abcd", word2 = "pq"

Output = "apbqcd"

Explanation: Notice that as word1 is longer, "cd" is appended to the end.

word1: a b c d

word2: p q

merged: a p b q c d


**Constraints:**
- `1 <= word1.length, word2.length <= 100`
- `word1` and `word2` consist of lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- Need to merge two strings by alternating characters
- Handle cases where strings have different lengths
- Multiple approaches with same time complexity but different implementations

### Key Insights
1. **Two Pointers Technique**: Most efficient approach
2. **StringBuilder for Efficiency**: Avoid string concatenation in loops
3. **Single Pass**: Process both strings simultaneously
4. **Handle Remaining Characters**: Append leftover characters from longer string

### Approach Selection
**Chosen Approach:** Two Pointers with StringBuilder  
**Why this approach?** 
- O(n + m) time complexity
- O(n + m) space complexity for result (optimal)
- Clean and efficient string building
- Handles different length cases gracefully

## ⚡ Complexity Analysis
- **Time Complexity:** O(n + m) where n and m are lengths of word1 and word2
- **Space Complexity:** O(n + m) for the result string

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use StringBuilder for efficient string concatenation in loops

The algorithm processes characters until the shorter string is exhausted

Remaining characters from the longer string are appended at the end

The solution is optimal and easy to understand

🔗 Related Problems
Merge Two Sorted Lists

Merge Sorted Array

Add Strings

Add Binary

String Compression
