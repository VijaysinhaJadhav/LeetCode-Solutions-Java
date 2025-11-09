# 125. Valid Palindrome

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** String, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/valid-palindrome/)

A phrase is a **palindrome** if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, it reads the same forward and backward. Alphanumeric characters include letters and numbers.

Given a string `s`, return `true` if it is a **palindrome**, or `false` otherwise.

**Example 1:**

Input: s = "A man, a plan, a canal: Panama"

Output: true

Explanation: "amanaplanacanalpanama" is a palindrome.


**Example 2:**

Input: s = "race a car"

Output: false

Explanation: "raceacar" is not a palindrome.


**Example 3:**

Input: s = " "

Output: true

Explanation: s is an empty string "" after removing non-alphanumeric characters.

Since an empty string reads the same forward and backward, it is a palindrome.


**Constraints:**
- `1 <= s.length <= 2 * 10^5`
- `s` consists only of printable ASCII characters.

## 🧠 Thought Process

### Initial Thoughts
- Need to check if string is palindrome after cleaning (lowercase + alphanumeric only)
- Multiple approaches with different time/space trade-offs
- Must handle edge cases: empty string, single character, all non-alphanumeric

### Key Insights
1. **Two Pointers Technique**: Most efficient approach with O(1) space
2. **Built-in Methods**: Simple but may create new strings
3. **Character-by-Character Processing**: Manual validation for learning purposes
4. **The key challenge**: Efficiently skip non-alphanumeric characters

### Approach Selection
**Chosen Approach:** Two Pointers with Character Validation  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity (in-place processing)
- Most efficient for large inputs
- Handles all edge cases gracefully

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the string
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The two pointers approach compares characters from both ends while skipping non-alphanumeric characters

Character validation can be done using Character.isLetterOrDigit() or manual checks

Empty strings and single characters are always palindromes

The algorithm is case-insensitive after conversion

🔗 Related Problems
Valid Palindrome II

Palindrome Number

Palindrome Linked List

Palindrome Permutation

Longest Palindrome
