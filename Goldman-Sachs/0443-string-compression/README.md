# 443. String Compression

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Two Pointers, String Manipulation  
**Companies:** Amazon, Microsoft, Google, Facebook, Bloomberg, Apple  

[LeetCode Link](https://leetcode.com/problems/string-compression/)

Given an array of characters `chars`, compress it using the following algorithm:

Begin with an empty string `s`. For each group of **consecutive repeating characters** in `chars`:
- If the group's length is `1`, append the character to `s`.
- Otherwise, append the character followed by the group's length.

The compressed string `s` **should not be returned separately**, but instead, be stored in the input character array `chars`. Note that group lengths that are `10` or longer will be split into multiple characters in `chars`.

After you are done modifying the input array, return the **new length** of the array.

**Example 1:**

Input: chars = ["a","a","b","b","c","c","c"]
Output: Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]
Explanation: The groups are "aa", "bb", "ccc". This compresses to "a2b2c3".


**Example 2:**

Input: chars = ["a"]
Output: Return 1, and the first character of the input array should be: ["a"]
Explanation: The only group is "a", which remains uncompressed since it's a single character.


**Example 3:**

Input: chars = ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
Output: Return 4, and the first 4 characters of the input array should be: ["a","b","1","2"]
Explanation: The groups are "a" and "bbbbbbbbbbbb". This compresses to "ab12".



**Constraints:**
- `1 <= chars.length <= 2000`
- `chars[i]` is a lowercase English letter, uppercase English letter, digit, or symbol.

**Follow-up:** Could you solve it using only `O(1)` extra space?

## 🧠 Thought Process

### Problem Understanding
We need to compress consecutive repeating characters in-place:
- Single character: keep as is
- Multiple characters: `char` + `count` (count as string)
- Modify the original array and return the new length

### Key Insights
1. **Two Pointers**: One pointer for reading (scanning groups), one for writing (building compressed result)
2. **Group Processing**: Identify each group of consecutive identical characters
3. **Count Conversion**: Convert count to string characters and write each digit
4. **In-Place Modification**: Overwrite the original array starting from index 0

### Approach Selection
**Chosen Approach:** Two-Pointer with Group Processing  
**Why this approach?**
- O(n) time complexity
- O(1) extra space (in-place)
- Handles counts with multiple digits

**Alternative Approaches:**
- **StringBuilder then rebuild**: Simple but O(n) extra space
- **Split into groups**: Extra space for grouping

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n is the length of chars
- **Space Complexity:** O(1) extra space (in-place modification)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Write count as string characters, not as a single integer

Handle counts >= 10 by writing each digit separately

Keep track of read pointer and write pointer separately

The input array is modified in-place

🔗 Related Problems
Encode and Decode Strings
Count and Say
Decode String
String Compression II
Design Compressed String Iterator

