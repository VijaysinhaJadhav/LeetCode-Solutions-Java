# 1190. Reverse Substrings Between Each Pair of Parentheses

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Stack, Recursion  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Oracle  

[LeetCode Link](https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/)

You are given a string `s` that consists of lower case English letters and parentheses.

Reverse the strings in each pair of matching parentheses, starting from the innermost one.

Your result should **not** contain any parentheses.

**Example 1:**

Input: s = "(abcd)"
Output: "dcba"
Explanation: The entire string inside parentheses is reversed.


**Example 2:**

Input: s = "(u(love)i)"
Output: "iloveu"
Explanation:

The substring "love" is reversed to "evol".

Then the whole string becomes "uevoli", which is reversed to "iloveu".


**Example 3:**

Input: s = "(ed(et(oc))el)"
Output: "leetcode"
Explanation:

Reverse "oc" → "co"

"etco" becomes "etco" → "octe"

"edocteel" becomes "leetcode"


**Constraints:**
- `1 <= s.length <= 2000`
- `s` consists of lowercase English letters and parentheses `'('` and `')'`.
- It is guaranteed that all parentheses are balanced.

## 🧠 Thought Process

### Problem Understanding
We need to process nested parentheses from innermost to outermost, reversing the strings inside each pair of parentheses.

### Key Insights
1. **Stack-based Approach**: Use a stack to track parentheses and build result
2. **Two-phase Process**: 
   - First pass: Build result string by processing characters
   - Alternative: Process parentheses by finding matching pairs
3. **Nested Parentheses**: Need to handle multiple levels of nesting
4. **Reversal Strategy**: When encountering ')', pop until '(' and reverse the substring

### Approach Selection
**Chosen Approach**: Stack with StringBuilder  
**Why this approach?**
- O(n) time complexity
- O(n) space complexity  
- Handles nested parentheses naturally
- Clean and efficient implementation

**Alternative Approaches**:
- Recursive DFS approach
- Two-pointer with matching parentheses
- Using deque for bidirectional operations

## ⚡ Complexity Analysis
- **Time Complexity**: O(n²) worst-case (due to string reversal), O(n) average with optimization
- **Space Complexity**: O(n) for stack and string builder

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation


📝 Notes
Process from innermost parentheses outward

Stack helps track nested structure naturally

StringBuilder efficient for string manipulation

Can optimize to O(n) time by preprocessing matching parentheses

🔗 Related Problems
Decode String

Score of Parentheses

Remove Outermost Parentheses

Minimum Remove to Make Valid Parentheses

Minimum Insertions to Balance a Parentheses String
