# 49. Group Anagrams

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, String, Sorting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/group-anagrams/)

Given an array of strings `strs`, group the **anagrams** together. You can return the answer in **any order**.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

**Example 1:**

Input: strs = ["eat","tea","tan","ate","nat","bat"]

Output: [["bat"],["nat","tan"],["ate","eat","tea"]]


**Example 2:**

Input: strs = [""]

Output: [[""]]


**Example 3:**

Input: strs = ["a"]

Output: [["a"]]


**Constraints:**
- `1 <= strs.length <= 10^4`
- `0 <= strs[i].length <= 100`
- `strs[i]` consists of lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- Anagrams have the same characters with the same frequencies
- Need to group strings that are anagrams of each other
- Each group should contain all strings that are anagrams

### Key Insights
1. **Sorted Key Approach**: Sort each string and use the sorted version as a key in a hash map
2. **Frequency Count Key**: Use character frequency counts as a key instead of sorting
3. **Prime Product Key**: Use prime number multiplication for each character to create unique keys
4. **HashMap Structure**: Map<String, List<String>> where key is the normalized form

### Approach Selection
**Chosen Approach:** Sorted String Key  
**Why this approach?** 
- Simple and intuitive implementation
- Easy to understand and explain
- Works well for the given constraints
- Most common approach in interviews

## ⚡ Complexity Analysis
- **Time Complexity:** O(n * k log k) where n is number of strings, k is maximum string length
- **Space Complexity:** O(n * k) for storing the grouped anagrams

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The sorted key approach is the most straightforward and commonly used

For better performance with long strings, consider frequency count approach

The prime product approach avoids sorting but has risk of integer overflow

Always handle edge cases: empty strings, single character strings

🔗 Related Problems
242. Valid Anagram

438. Find All Anagrams in a String

249. Group Shifted Strings

760. Find Anagram Mappings
