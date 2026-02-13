# 211. Design Add and Search Words Data Structure

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Depth-First Search, Design, Trie, Backtracking  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber  

[LeetCode Link](https://leetcode.com/problems/design-add-and-search-words-data-structure/)

Design a data structure that supports adding new words and finding if a string matches any previously added string.

Implement the `WordDictionary` class:

- `WordDictionary()` Initializes the object.
- `void addWord(word)` Adds `word` to the data structure, it can be matched later.
- `bool search(word)` Returns `true` if there is any string in the data structure that matches `word` or `false` otherwise. `word` may contain dots `'.'` where dots can be matched with any letter.

**Example 1:**

Input
["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]

Output
[null,null,null,null,false,true,true,true]

Explanation
WordDictionary wordDictionary = new WordDictionary();
wordDictionary.addWord("bad");
wordDictionary.addWord("dad");
wordDictionary.addWord("mad");
wordDictionary.search("pad"); // return False
wordDictionary.search("bad"); // return True
wordDictionary.search(".ad"); // return True
wordDictionary.search("b.."); // return True


**Constraints:**
- `1 <= word.length <= 25`
- `word` in `addWord` consists of lowercase English letters.
- `word` in `search` consists of lowercase English letters and dots `'.'`.
- At most `10^4` calls will be made to `addWord` and `search`.

## 🧠 Thought Process

### Problem Understanding
We need a data structure that:
1. **Add words**: Store words efficiently for later lookup
2. **Search words**: Support exact match and wildcard matching (`.` matches any letter)

### Key Insights
1. **Trie (Prefix Tree)**: Natural fit for storing dictionary words
2. **DFS/Backtracking**: When encountering `.`, need to explore all possible paths
3. **Time-Space Tradeoff**: Multiple approaches with different complexities
4. **Optimization**: For search-heavy workloads, consider precomputing patterns

### Approach Selection
**Chosen Approach**: Trie with DFS Backtracking  
**Why this approach?**
- Efficient for both add (O(L)) and search (O(26^K) where K is number of dots)
- Natural representation of word prefixes
- Handles wildcards elegantly with recursion

**Alternative Approaches**:
- HashSet with pattern generation (fast for search, expensive memory)
- HashMap mapping length to words (filter by length first)
- Trie with BFS/Queue for wildcard search

## ⚡ Complexity Analysis
- **Time Complexity**: 
  - `addWord`: O(L) where L is length of word
  - `search`: O(L) for exact match, O(26^K) for wildcards where K is number of dots
- **Space Complexity**: O(N × L) where N is number of words, L is average length

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Trie nodes store children for 26 letters and a boolean flag for word end

For wildcard search, use DFS/recursion to explore all possible paths

Optimize by early termination when path doesn't exist

Can also use BFS with queue for iterative approach

🔗 Related Problems
Implement Trie (Prefix Tree)
Word Search II
Word Ladder II
Word Search
Longest Word in Dictionary
