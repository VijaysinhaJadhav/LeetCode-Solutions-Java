# 242. Valid Anagram

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Hash Table, String, Sorting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/valid-anagram/)

Given two strings `s` and `t`, return `true` if `t` is an anagram of `s`, and `false` otherwise.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

**Example 1:**

Input: s = "anagram", t = "nagaram"

Output: true


**Example 2:**

Input: s = "rat", t = "car"

Output: false


**Constraints:**
- `1 <= s.length, t.length <= 5 * 10^4`
- `s` and `t` consist of lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- An anagram means both strings have the same characters with the same frequencies
- If lengths are different, they cannot be anagrams
- Need to compare character counts efficiently

### Key Insights
1. **Frequency Counter**: Use arrays or hash maps to count character frequencies in both strings and compare
2. **Sorting Approach**: Sort both strings and compare if they are equal
3. **Array vs HashMap**: Since strings contain only lowercase English letters, we can use a fixed-size array (26 elements) for better performance

### Approach Selection
**Chosen Approach:** Frequency Counter with Array  
**Why this approach?** 
- Optimal O(n) time complexity
- O(1) space complexity (fixed 26-element array)
- More efficient than sorting for large strings
- Simple and intuitive implementation

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n is the length of the strings
- **Space Complexity:** O(1) - fixed size array regardless of input size

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The array approach is optimal because we know the character set is limited to 26 lowercase letters

For Unicode characters, we would need to use a HashMap instead

Always check length first - different lengths immediately mean not anagrams

The frequency array technique is a common pattern for string manipulation problems

🔗 Related Problems
49. Group Anagrams

438. Find All Anagrams in a String

383. Ransom Note

567. Permutation in String
