# 680. Valid Palindrome II

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** String, Two Pointers, Greedy  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/valid-palindrome-ii/)

Given a string `s`, return `true` if the `s` can be palindrome after deleting **at most one** character from it.

**Example 1:**

Input: s = "aba"

Output: true


**Example 2:**

Input: s = "abca"

Output: true

Explanation: You could delete the character 'c'.


**Example 3:**

Input: s = "abc"

Output: false


**Constraints:**
- `1 <= s.length <= 10^5`
- `s` consists of lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- Need to check if string can be palindrome by removing at most one character
- This is an extension of Valid Palindrome problem with one deletion allowed
- Multiple approaches with different implementations

### Key Insights
1. **Two Pointers with Recursive Check**: Most intuitive approach
2. **Greedy Approach**: Check both possibilities when mismatch occurs
3. **The key insight**: When characters don't match, check both possibilities (skip left or skip right)
4. **Early Termination**: Stop when more than one deletion would be needed

### Approach Selection
**Chosen Approach:** Two Pointers with Helper Function  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Clear and intuitive logic
- Handles all edge cases efficiently

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the string in worst case
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The algorithm uses the standard two pointers approach

When mismatch occurs, it checks both possibilities (skip left character or skip right character)

Only one deletion is allowed, so we check both paths

The helper function makes the solution clean and reusable

🔗 Related Problems
Valid Palindrome

Valid Palindrome III

Palindrome Number

Palindrome Linked List

Palindrome Permutation


