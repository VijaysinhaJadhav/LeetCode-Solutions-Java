# 71. Simplify Path

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Stack  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/simplify-path/)

Given a string `path`, which is an absolute path (starting with a slash `'/'`) to a file or directory in a Unix-style file system, convert it to the simplified canonical path.

In a Unix-style file system, a period `'.'` refers to the current directory, a double period `'..'` refers to the directory up a level, and any multiple consecutive slashes `'/'` are treated as a single slash `'/'`. For this problem, any other format of periods such as `'...'` are treated as file/directory names.

The canonical path should have the following format:
- The path starts with a single slash `'/'`.
- Any two directories are separated by a single slash `'/'`.
- The path does not end with a trailing `'/'`.
- The path only contains the directories on the path from the root directory to the target file or directory (i.e., no period `'.'` or double period `'..'`).

Return the simplified canonical path.

**Example 1:**

Input: path = "/home/"

Output: "/home"

Explanation: Note that there is no trailing slash after the last directory name.


**Example 2:**

Input: path = "/../"

Output: "/"

Explanation: Going one level up from the root directory is a no-op, as the root level is the highest level you can go.


**Example 3:**

Input: path = "/home//foo/"

Output: "/home/foo"

Explanation: In the canonical path, multiple consecutive slashes are replaced by a single one.


**Constraints:**
- `1 <= path.length <= 3000`
- `path` consists of English letters, digits, period `'.'`, slash `'/'` or `'_'`.
- `path` is a valid absolute Unix path.

## 🧠 Thought Process

### Initial Thoughts
- Need to process Unix-style path and simplify it
- Stack is natural for handling directory navigation with `..`
- Multiple approaches with different implementations
- The challenge is handling edge cases and special characters correctly

### Key Insights
1. **Stack for Directory Navigation**: Push directories, pop when encountering `..`
2. **Split by Slash**: Process path components between slashes
3. **Ignore Current Directory**: Skip `.` components
4. **The key insight**: Use stack to maintain current directory hierarchy

### Approach Selection
**Chosen Approach:** Stack with Split  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Clean and intuitive
- Handles all edge cases efficiently

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Process each character once
- **Space Complexity:** O(n) - Stack storage for directories

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Stack perfectly handles directory navigation with ..

Split path by / and process each component

Ignore empty strings and . components

Handle .. by popping from stack if not empty

Join stack contents with / for final path

🔗 Related Problems
Longest Absolute File Path

Decode String

Baseball Game

Remove All Adjacent Duplicates In String

Remove All Adjacent Duplicates in String II
