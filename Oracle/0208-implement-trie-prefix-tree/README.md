# 208. Implement Trie (Prefix Tree)

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, String, Design, Trie  
**Companies:** Amazon, Microsoft, Google, Apple, Facebook, Uber

[LeetCode Link](https://leetcode.com/problems/implement-trie-prefix-tree/)

A **trie** (pronounced as "try") or **prefix tree** is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.

Implement the Trie class:

- `Trie()` Initializes the trie object.
- `void insert(String word)` Inserts the string `word` into the trie.
- `boolean search(String word)` Returns `true` if the string `word` is in the trie (i.e., was inserted before), and `false` otherwise.
- `boolean startsWith(String prefix)` Returns `true` if there is a previously inserted string `word` that has the prefix `prefix`, and `false` otherwise.

**Example 1:**

Input
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
Output
[null, null, true, false, true, null, true]

Explanation
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple"); // return True
trie.search("app"); // return False
trie.startsWith("app"); // return True
trie.insert("app");
trie.search("app"); // return True


**Constraints:**
- `1 <= word.length, prefix.length <= 2000`
- `word` and `prefix` consist only of lowercase English letters.
- At most `3 * 10^4` calls **in total** will be made to `insert`, `search`, and `startsWith`.

## 🧠 Thought Process

### What is a Trie?
A trie is a tree-like data structure that stores strings character by character. Each node in the trie represents a single character of a string, and the path from the root to any node forms a prefix of some string stored in the trie.

### Key Insights
1. **Node Structure:** Each node needs:
   - An array or map of children (26 for lowercase English letters)
   - A boolean flag to mark the end of a word
2. **Insert Operation:** Traverse through each character of the word, creating nodes as needed, and mark the final node as end of word
3. **Search Operation:** Traverse through each character, return false if any character is missing, return true only if final node is marked as end of word
4. **StartsWith Operation:** Similar to search but doesn't require the final node to be marked as end of word

### Approach Selection
**Chosen Approach:** Array-based Trie Node  
**Why this approach?**
- O(L) time complexity for all operations (L = length of word/prefix)
- O(26 * N) space complexity where N is total nodes
- Simple and efficient for lowercase English letters

**Alternative:** Map-based Trie Node  
**Why this approach?**
- More memory efficient when alphabet is large or sparse
- Can handle any character set

## ⚡ Complexity Analysis
- **Time Complexity:** 
  - `insert()`: O(L) where L is length of word
  - `search()`: O(L) where L is length of word
  - `startsWith()`: O(P) where P is length of prefix
- **Space Complexity:** O(N * M) where N is number of nodes and M is alphabet size (26)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Trie is particularly useful for prefix-based operations like autocomplete

The boolean isEnd is crucial to distinguish between prefix and complete word

Can be extended to support deletion, counting words with prefix, etc.

🔗 Related Problems
Design Add and Search Words Data Structure

Word Search II

Map Sum Pairs

Longest Word in Dictionary

Search Suggestions System
