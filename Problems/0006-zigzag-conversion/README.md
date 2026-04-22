# 6. Zigzag Conversion

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/zigzag-conversion/)

The string `"PAYPALISHIRING"` is written in a zigzag pattern on a given number of rows like this:

P A H N
A P L S I I G
Y I R


And then read line by line: `"PAHNAPLSIIGYIR"`

Write the code that will take a string and make this conversion given a number of rows.

**Example 1:**

Input: s = "PAYPALISHIRING", numRows = 3
Output: "PAHNAPLSIIGYIR"


**Example 2:**

Input: s = "PAYPALISHIRING", numRows = 4
Output: "PINALSIGYAHRPI"
Explanation:
P I N
A L S I G
Y A H R
P I


**Example 3:**

Input: s = "A", numRows = 1
Output: "A"


**Constraints:**
- `1 <= s.length <= 1000`
- `1 <= numRows <= 1000`

## 🧠 Thought Process

### Problem Understanding
We need to arrange characters in a zigzag pattern (down then diagonally up) across a given number of rows, then read row by row.

### Key Insights
1. **Row-by-Row Construction**: Instead of building a 2D grid, we can append characters to each row string
2. **Cycle Pattern**: The zigzag pattern repeats every `2 * numRows - 2` characters (except when numRows = 1)
3. **Index Calculation**: For a given index `i`, we can determine its row using modulo arithmetic
4. **Simulation**: Simulate moving down and up across rows

### Approach Selection
**Chosen Approach:** Row-by-Row with Cycle Detection  
**Why this approach?**
- O(n) time complexity
- O(n) space complexity
- Simple and efficient

**Alternative Approaches:**
- **2D Grid Simulation**: O(n × numRows) time, O(n × numRows) space
- **Math Formula**: Calculate row for each character using cycle length

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of string
- **Space Complexity:** O(n) for storing result

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Handle numRows = 1 as a special case (return original string)

Use StringBuilder array for each row

Simulate moving down and up through rows

Build final result by concatenating all rows

🔗 Related Problems
Zigzag Conversion (this problem)
Text Justification
Longest Common Prefix
Find the Index of the First Occurrence in a String


