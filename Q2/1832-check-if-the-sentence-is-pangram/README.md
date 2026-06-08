# 1832. Check if the Sentence Is Pangram

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Hash Table, String  
**Companies:** Amazon, Microsoft, Google  

[LeetCode Link](https://leetcode.com/problems/check-if-the-sentence-is-pangram/)

A **pangram** is a sentence where every letter of the English alphabet appears at least once.

Given a string `sentence` containing only lowercase English letters, return `true` if it is a pangram, or `false` otherwise.

**Example 1:**

Input: sentence = "thequickbrownfoxjumpsoverthelazydog"
Output: true
Explanation: The sentence contains every letter at least once.


**Example 2:**

Input: sentence = "leetcode"
Output: false


**Constraints:**
- `1 <= sentence.length <= 1000`
- `sentence` consists of lowercase English letters.

## 🧠 Thought Process

### Problem Understanding
We need to check if the string contains all 26 letters of the English alphabet.

### Key Insights
1. **Hash Set**: Insert all characters into a set, check size == 26.
2. **Boolean Array**: Use a boolean array of size 26, mark seen letters, then verify all true.
3. **Bitmask**: Use an integer as a bitmask (26 bits) to track seen letters; at the end check if all 26 bits are set (mask == (1 << 26) - 1).

### Approach Selection
**Chosen Approach:** Boolean Array (O(n) time, O(1) space)  
**Why this approach?**
- Simple and efficient.
- No extra overhead of hash set.
- Works well within constraints (n ≤ 1000).

**Alternative Approaches:**
- **HashSet**: Also O(n) but uses more memory.
- **Bitmask**: Most space-efficient (int) but slightly less readable.

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of sentence.
- **Space Complexity:** O(1) – fixed array of size 26.

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Only lowercase English letters are guaranteed.

We can break early if all letters are found.

Bitmask approach is elegant and uses only one integer.

🔗 Related Problems
Check if the Sentence Is Pangram (this problem)
Detect Capital
To Lower Case
Unique Morse Code Words
