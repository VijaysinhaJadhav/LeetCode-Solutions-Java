# 17. Letter Combinations of a Phone Number

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, String, Backtracking  
**Companies:** Amazon, Google, Microsoft, Meta, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

Given a string containing digits from `2-9` inclusive, return all possible letter combinations that the number could represent. Return the answer in **any order**.

A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

![Telephone Keypad](https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/Telephone-keypad2.svg/200px-Telephone-keypad2.svg.png)

**Example 1:**

Input: digits = "23"

Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]


**Example 2:**

Input: digits = ""

Output: []


**Example 3:**

Input: digits = "2"

Output: ["a","b","c"]


**Constraints:**
- `0 <= digits.length <= 4`
- `digits[i]` is a digit in the range `['2', '9']`.

## 🧠 Thought Process

### Initial Thoughts
- Classic backtracking/DFS problem
- Each digit maps to 3-4 letters (except 7 and 9 map to 4 letters)
- Need to generate all combinations
- Can solve with recursion, iteration, or BFS/DFS

### Key Insights
1. **Backtracking Approach**: Build combinations recursively, backtrack when complete
2. **Iterative Approach**: Build combinations level by level
3. **Queue/BFS Approach**: Process digits one by one, expand current combinations
4. **Time Complexity**: O(4^n) where n is number of digits (worst case)

### Approach Selection
**Chosen Approach:** Backtracking (DFS)  
**Why this approach?** 
- Natural recursive structure
- Clear and intuitive implementation
- Easy to understand and explain
- Handles all cases including empty input

## ⚡ Complexity Analysis
- **Time Complexity:** O(4^n) where n = digits.length()
- **Space Complexity:** O(n) for recursion stack + O(4^n) for output

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use HashMap or array to store digit-letter mappings

Handle empty string case

Use StringBuilder for efficient string building

Backtrack by removing last character

🔗 Related Problems
Generate Parentheses

Combination Sum

Permutations

Subsets

Binary Watch
