# 1209. Remove All Adjacent Duplicates in String II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Stack, Simulation  
**Companies:** Facebook, Amazon, Microsoft, Google, Apple, Bloomberg, Adobe, Uber

[LeetCode Link](https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/)

You are given a string `s` and an integer `k`. You need to repeatedly delete `k` adjacent and equal letters from `s` until you cannot delete any more.

Return the *final string* after all such deletions. It is guaranteed that the answer is **unique**.

**Example 1:**

Input: s = "abcd", k = 2
Output: "abcd"
Explanation: There's nothing to delete.


**Example 2:**

Input: s = "deeedbbcccbdaa", k = 3
Output: "aa"
Explanation:
First delete "eee" and "ccc" to get "ddbbbdaa"
Then delete "bbb" to get "dddaa"
Finally delete "ddd" to get "aa"


**Example 3:**

Input: s = "pbbcggttciiippooaais", k = 2
Output: "ps"


**Constraints:**
- `1 <= s.length <= 10^5`
- `2 <= k <= 10^4`
- `s` only contains lowercase English letters.

## 🧠 Thought Process

### Initial Thoughts
- Need to repeatedly remove k consecutive identical characters
- Similar to candy crush or game elimination mechanics
- Naive approach: repeatedly scan for k duplicates → O(n²/k) too slow
- Need efficient data structure to track consecutive counts

### Key Insights
1. **Stack Approach:**
   - Use stack to store characters and their consecutive counts
   - When count reaches k, pop from stack
   - O(n) time, O(n) space
2. **Two-Pointer Approach:**
   - Use array to track counts while building result
   - When count reaches k, move pointer back
   - O(n) time, O(n) space (but could be O(1) extra with careful implementation)
3. **String Builder with Count Array:**
   - Build result incrementally while maintaining count of consecutive chars
   - When count reaches k, delete last k characters
4. **Recursive vs Iterative:**
   - Recursive might cause stack overflow for large n
   - Iterative with stack is safer

### Approach Selection
**Primary Approach:** Stack with Character-Count Pairs  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Clean and intuitive
- Easy to understand and implement

**Alternative:** Two-Pointer with Count Array  
**Why this approach?**
- Also O(n) time
- Potentially less overhead
- More space efficient in some implementations

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the string
- **Space Complexity:** O(n) - Stack in worst case stores all characters

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Stack stores pairs: (character, consecutive_count)

When same character appears, increment count instead of pushing new

When count reaches k, pop from stack

Finally, reconstruct string from stack

🔗 Related Problems
1047. Remove All Adjacent Duplicates In String (k=2 version)

316. Remove Duplicate Letters

1081. Smallest Subsequence of Distinct Characters

2390. Removing Stars From a String
