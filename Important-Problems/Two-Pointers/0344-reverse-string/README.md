# 344. Reverse String

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Two Pointers, String, Recursion  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/reverse-string/)

Write a function that reverses a string. The input string is given as an array of characters `s`.

You must do this by modifying the input array **in-place** with `O(1)` extra memory.

**Example 1:**

Input: s = ["h","e","l","l","o"]

Output: ["o","l","l","e","h"]


**Example 2:**

Input: s = ["H","a","n","n","a","h"]

Output: ["h","a","n","n","a","H"]


**Constraints:**
- `1 <= s.length <= 10^5`
- `s[i]` is a printable ASCII character.

## 🧠 Thought Process

### Initial Thoughts
- Need to reverse the array of characters in-place
- Multiple approaches with same time complexity but different styles
- Must use O(1) extra memory (constant space)

### Key Insights
1. **Two Pointers Technique**: Most intuitive and efficient approach
2. **Recursive Approach**: Elegant but has O(n) stack space
3. **Bit Manipulation**: Alternative using XOR swaps
4. **Built-in Methods**: Simple but may not be acceptable in interviews

### Approach Selection
**Chosen Approach:** Two Pointers (Iterative)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity (true constant space)
- Most intuitive and interview-friendly
- Easy to implement and understand

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through half the array
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The two pointers approach swaps characters from both ends towards the center

The loop runs until left pointer is less than right pointer

Each swap operation takes constant time

The algorithm is efficient even for the upper constraint (n = 10^5)

🔗 Related Problems
Reverse Vowels of a String

Reverse String II

Reverse Words in a String III

Reverse Words in a String

Rotate Array
