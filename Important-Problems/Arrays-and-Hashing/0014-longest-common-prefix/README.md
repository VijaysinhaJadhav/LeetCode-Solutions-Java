# 14. Longest Common Prefix

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** String, Trie, Divide and Conquer  
**Companies:** Amazon, Google, Microsoft, Apple, Facebook, Bloomberg

[LeetCode Link](https://leetcode.com/problems/longest-common-prefix/)

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string `""`.

**Example 1:**

Input: strs = ["flower","flow","flight"]

Output: "fl"


**Example 2:**

Input: strs = ["dog","racecar","car"]

Output: ""

Explanation: There is no common prefix among the input strings.


**Constraints:**
- `1 <= strs.length <= 200`
- `0 <= strs[i].length <= 200`
- `strs[i]` consists of only lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- Need to find the longest string that is a prefix of all strings in the array
- If the array is empty, return empty string
- If any string is empty, common prefix is empty
- The common prefix cannot be longer than the shortest string

### Key Insights
1. **Horizontal Scanning**: Compare the first string with others character by character
2. **Vertical Scanning**: Compare all strings character by character at each position
3. **Divide and Conquer**: Split the array and find LCP of halves, then combine
4. **Trie Approach**: Build a trie and find the deepest node with count equal to array size

### Approach Selection
**Chosen Approach:** Vertical Scanning  
**Why this approach?** 
- Simple and intuitive implementation
- O(S) time complexity where S is total characters in all strings
- Often performs better than horizontal scanning in practice
- Easy to understand and explain

## ⚡ Complexity Analysis
- **Time Complexity:** O(S) where S is the sum of all characters in all strings
- **Space Complexity:** O(1) - only using constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The vertical scanning approach is efficient and easy to implement

Always check for edge cases: empty array, empty strings, single string

The common prefix length is limited by the shortest string in the array

Early termination when a mismatch is found improves performance

🔗 Related Problems
208. Implement Trie (Prefix Tree)

720. Longest Word in Dictionary

648. Replace Words

1392. Longest Happy Prefix
