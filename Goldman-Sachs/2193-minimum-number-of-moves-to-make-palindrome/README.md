# 2193. Minimum Number of Moves to Make Palindrome

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** String, Two Pointers, Greedy  
**Companies:** Amazon, Google, Microsoft, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/minimum-number-of-moves-to-make-palindrome/)

You are given a string `s` consisting of lowercase English letters.

In one move, you can swap **any two adjacent characters** of the string.

Return the **minimum number of moves** required to make `s` a palindrome.

**Note:** The input string is guaranteed to be rearranged into a palindrome (i.e., at most one character has odd frequency).

**Example 1:**

Input: s = "aabb"
Output: 2
Explanation: We can obtain "abba" in 2 moves: swap 'b' with 'a' at positions 2 and 3 → "abab", then swap 'b' with 'a' → "abba".
Alternatively, we can obtain "baab" in 2 moves.


**Example 2:**

Input: s = "letelt"
Output: 2
Explanation: One of the palindromes we can obtain is "lettel" in 2 moves.


**Constraints:**
- `1 <= s.length <= 2000`
- `s` consists of lowercase English letters.
- `s` can be rearranged to form a palindrome.

## 🧠 Thought Process

### Problem Understanding
We need to transform the given string into a palindrome by swapping adjacent characters. Each swap costs 1 move. We want the minimum number of swaps.

### Key Insights
1. **Greedy Approach**: Work from the outermost characters inward
2. **Two Pointers**: Use left and right pointers to build palindrome from ends
3. **Character Matching**: For each left character, find the matching character from the right
4. **Swap Counting**: Each swap moves characters inward, counting the distance

### Approach Selection
**Chosen Approach:** Two Pointers with Greedy Matching  
**Why this approach?**
- O(n²) time complexity (acceptable for n ≤ 2000)
- O(n) or O(1) extra space
- Intuitive: build palindrome from ends inward
- Guaranteed to find optimal solution

**Alternative Approaches:**
- **BFS/DFS**: Exponential, too slow
- **DP**: O(n²) but more complex

## ⚡ Complexity Analysis
- **Time Complexity:** O(n²) where n = length of string
- **Space Complexity:** O(n) for converting to char array, O(1) extra

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Convert string to char array for efficient manipulation

Use two pointers: left from start, right from end

For each left position, find matching character from right side

When found, swap it inward and count swaps

For odd-length strings, the middle character doesn't need a match

🔗 Related Problems
Minimum Swaps to Make Strings Equal
Minimum Insertion Steps to Make a String Palindrome
Valid Palindrome II
Shortest Palindrome
Longest Palindromic Substring
