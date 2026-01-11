# 588. Design In-Memory File System

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Hash Table, String, Design, Trie, Data Stream  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/design-in-memory-file-system/)

Design an in-memory file system to simulate the following functions:

**ls**: Given a path in string format. If it is a file path, return a list that only contains this file's name. If it is a directory path, return the list of file and directory names in this directory. Your output (file and directory names together) should be in lexicographic order.

**mkdir**: Given a directory path that does not exist, you should make a new directory according to the path. If the middle directories in the path don't exist, you should create them as well.

**addContentToFile**: Given a file path and file content in string format. If the file doesn't exist, you need to create that file containing given content. If the file already exists, you need to append given content to original content.

**readContentFromFile**: Given a file path, return the content in the file.

**Example 1:**

Input:
["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
[[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]

Output:
[null,[],null,null,["a"],"hello"]

Explanation:

FileSystem fs = new FileSystem();

fs.ls("/"); // return []

fs.mkdir("/a/b/c"); // create directory /a/b/c

fs.addContentToFile("/a/b/c/d", "hello"); // create file /a/b/c/d with content "hello"

fs.ls("/"); // return ["a"] (only directory "a" in root)

fs.readContentFromFile("/a/b/c/d"); // return "hello"


**Constraints:**
- `1 <= path.length, filePath.length <= 100`
- `1 <= content.length <= 50`
- You may assume all file/directory names consist of lowercase English letters, digits, and/or `'.'`.
- You may assume all directory names and file names are valid.
- You may assume all operations will be passed valid parameters.
- At most `300` calls will be made to `ls`, `mkdir`, `addContentToFile`, and `readContentFromFile`.

## 🧠 Thought Process

### Problem Understanding
We need to design an in-memory file system with:
1. **ls**: List directory contents or file name
2. **mkdir**: Create directories (including parent directories)
3. **addContentToFile**: Create or append to files
4. **readContentFromFile**: Read file content

### Key Insights
1. **Tree Structure**: File system is naturally a tree with directories as nodes
2. **Trie-like Structure**: Paths can be represented as a prefix tree
3. **Node Types**: Need to distinguish between files and directories
4. **Path Parsing**: Need to parse Unix-style paths (`/a/b/c`)
5. **Lexicographic Order**: Output of `ls` must be sorted

### Approach Selection
**Chosen Approach**: Trie/Node-based Tree  
**Why this approach?**
- Natural representation of hierarchical file system
- Efficient path traversal
- Clear separation of files and directories
- Easy to implement all operations

**Alternative Approaches**:
- HashMap with path as key (less efficient for hierarchical operations)
- Actual tree nodes with parent/children relationships

## ⚡ Complexity Analysis
- **Time Complexity**:
  - `ls`: O(n log n) where n is number of entries (for sorting)
  - `mkdir`: O(L) where L is path depth
  - `addContentToFile`: O(L) where L is path depth
  - `readContentFromFile`: O(L) where L is path depth
- **Space Complexity**: O(N) where N is total number of files and directories

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Root directory is represented by "/"

Directories contain references to child nodes

Files contain content strings

Need to handle path parsing carefully (empty segments, trailing slashes)

Lexicographic sorting required for ls output

🔗 Related Problems
Design Add and Search Words Data Structure

Implement Trie (Prefix Tree)

Design Search Autocomplete System

Design Phone Directory

Design Twitter
