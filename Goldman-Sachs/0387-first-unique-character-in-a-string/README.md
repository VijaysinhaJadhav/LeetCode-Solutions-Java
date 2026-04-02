# 387. First Unique Character in a String

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Hash Table, String, Queue, Counting  
**Companies:** Amazon, Microsoft, Google, Facebook, Apple, Bloomberg, Uber  

[LeetCode Link](https://leetcode.com/problems/first-unique-character-in-a-string/)

Given a string `s`, find the **first non-repeating character** in it and return its **index**. If it does not exist, return `-1`.

**Example 1:**

Input: s = "leetcode"
Output: 0
Explanation: The first non-repeating character is 'l' at index 0.


**Example 2:**

Input: s = "loveleetcode"
Output: 2
Explanation: The first non-repeating character is 'v' at index 2.


**Example 3:**

Input: s = "aabb"
Output: -1
Explanation: All characters repeat, so return -1.


**Constraints:**
- `1 <= s.length <= 10^5`
- `s` consists of only lowercase English letters.

## 🧠 Thought Process

### Problem Understanding
We need to find the first character in the string that appears exactly once. The string contains only lowercase letters.

### Key Insights
1. **Character Frequency**: Count occurrences of each character first
2. **Order Matters**: After counting, scan again to find first character with count = 1
3. **Optimization**: Can use array of size 26 for constant-time operations

### Approach Selection
**Chosen Approach:** Two-Pass with Frequency Array  
**Why this approach?**
- O(n) time complexity
- O(1) space complexity (fixed array size 26)
- Simple and efficient

**Alternative Approaches:**
- **HashMap**: More flexible but slightly slower
- **Queue with Frequency Map**: One-pass solution
- **Last Index Tracking**: Track first and last occurrence

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of string
- **Space Complexity:** O(1) (constant size for alphabet)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Using an integer array of size 26 is most efficient for lowercase letters

First pass counts frequencies, second pass finds first unique

Can also solve with indexOf and lastIndexOf (less efficient)

Edge case: empty string (not possible per constraints)

🔗 Related Problems
Sort Characters By Frequency
Group Anagrams
Valid Anagram
Ransom Note
Isomorphic Strings
