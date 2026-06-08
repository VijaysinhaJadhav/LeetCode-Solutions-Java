# 451. Sort Characters By Frequency

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, String, Sorting, Heap (Priority Queue), Bucket Sort  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/sort-characters-by-frequency/)

Given a string `s`, sort it in **decreasing order** based on the **frequency** of the characters. Return the sorted string. If multiple characters have the same frequency, they can appear in any order.

**Example 1:**

Input: s = "tree"
Output: "eert"
Explanation: 'e' appears twice, 'r' and 't' appear once. So 'e' must appear before both 'r' and 't'.


**Example 2:**

Input: s = "cccaaa"
Output: "aaaccc"
Explanation: Both 'c' and 'a' appear three times, so "aaaccc" is valid. "cccaaa" is also valid.


**Example 3:**

Input: s = "Aabb"
Output: "bbAa"
Explanation: 'b' appears twice, 'A' and 'a' appear once. 'b' must appear before 'A' and 'a'.'


**Constraints:**
- `1 <= s.length <= 5 * 10^5`
- `s` consists of uppercase/lowercase English letters and digits.

## 🧠 Thought Process

### Problem Understanding
We need to sort characters by their frequency (highest first). Characters with same frequency can be in any order.

### Key Insights
1. **Count Frequencies**: Use a hash map (or array of size 128 for ASCII) to count occurrences.
2. **Sort by Frequency**: Several ways to achieve O(n) or O(n log n) sorting.
3. **Bucket Sort**: Since frequency is bounded by string length (≤ 5×10⁵), we can use bucket sort → O(n) time.
4. **Max-Heap**: Use a priority queue to pop characters with highest frequency.
5. **Sorting with Custom Comparator**: Collect pairs and sort.

### Approach Selection
**Chosen Approach:** Bucket Sort (Optimal)  
**Why this approach?**
- O(n) time complexity
- O(n) space complexity
- No sorting overhead, ideal for large inputs

**Alternative Approaches:**
- **Priority Queue**: O(n log k) where k = number of unique characters
- **Sorting Map Entries**: O(n log n)
- **Array of 128 ints + Sorting**: Simpler for ASCII

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) for bucket sort, O(n log n) for comparison-based sorting
- **Space Complexity:** O(n) for storing frequencies and buckets

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation


📝 Notes
Use char as key in frequency map; for ASCII, an array of size 128 is faster.

Bucket sort: create an array of lists (or strings) where index = frequency.

Build result by iterating from highest frequency down to 1.

For non-ASCII characters (e.g., Unicode), use HashMap.

🔗 Related Problems
Top K Frequent Elements
Top K Frequent Words
Sort Characters By Frequency (this problem)
Group Anagrams
Rearrange String k Distance Apart
