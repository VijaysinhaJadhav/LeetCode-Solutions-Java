# 953. Verifying an Alien Dictionary

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, String  
**Companies:** Facebook, Google, Amazon, Microsoft, Bloomberg

[LeetCode Link](https://leetcode.com/problems/verifying-an-alien-dictionary/)

In an alien language, surprisingly, they also use English lowercase letters, but possibly in a different order. The order of the alphabet is some permutation of lowercase letters.

Given a sequence of `words` written in the alien language, and the `order` of the alphabet, return `true` if and only if the given `words` are sorted lexicographically in this alien language.

**Example 1:**

Input: words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"

Output: true

Explanation: As 'h' comes before 'l' in this language, then the sequence is sorted.


**Example 2:**

Input: words = ["word","world","row"], order = "worldabcefghijkmnpqstuvxyz"

Output: false

Explanation: As 'd' comes after 'l' in this language, then words[0] > words[1], hence the sequence is unsorted.


**Example 3:**

Input: words = ["apple","app"], order = "abcdefghijklmnopqrstuvwxyz"

Output: false

Explanation: The first three characters "app" match, and the second string is shorter (in lexicographical order), which is unsorted.


**Constraints:**
- `1 <= words.length <= 100`
- `1 <= words[i].length <= 20`
- `order.length == 26`
- All characters in `words[i]` and `order` are English lowercase letters.

## 🧠 Thought Process

### Initial Thoughts
- Need to verify if words are sorted according to custom alphabet order
- Can map each character to its position for O(1) comparison
- Compare adjacent words pairwise
- Handle cases where one word is prefix of another

### Key Insights
1. **Character Mapping**: Create a mapping from character to its position in alien alphabet
2. **Pairwise Comparison**: Check if each word is <= the next word according to alien order
3. **Prefix Handling**: If words share common prefix, shorter word should come first
4. **Early Termination**: Stop at first mismatch

### Approach Selection
**Chosen Approach:** Hash Map with Pairwise Comparison  
**Why this approach?** 
- O(n × m) time complexity where n is number of words, m is average word length
- O(1) space for mapping (fixed 26 characters)
- Simple and intuitive implementation
- Efficient for given constraints

## ⚡ Complexity Analysis
- **Time Complexity:** O(n × m) - Compare each pair of words, each comparison O(m)
- **Space Complexity:** O(1) - Fixed size mapping for 26 characters

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Create a mapping from character to its index in the alien order

Compare adjacent words character by character

If all characters match, shorter word should come first

Return false at first violation, true if all pairs are valid

🔗 Related Problems
Alien Dictionary (Hard)

Is Subsequence

Longest Word in Dictionary through Deleting

Longest Word in Dictionary

Number of Matching Subsequences

