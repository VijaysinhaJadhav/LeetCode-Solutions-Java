# 1405. Longest Happy String

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Greedy, Heap (Priority Queue)  
**Companies:** Google, Amazon, Microsoft, Meta, Bloomberg

[LeetCode Link](https://leetcode.com/problems/longest-happy-string/)

A string is called **happy** if it does not have any of the strings `'aaa'`, `'bbb'`, or `'ccc'` as a substring.

Given three integers `a`, `b`, and `c`, return **any** longest happy string you can create. If there are multiple longest happy strings, return any of them.

If there is no such string, return the empty string `""`.

A **substring** is a contiguous sequence of characters within a string.

**Example 1:**

Input: a = 1, b = 1, c = 7

Output: "ccaccbcc"

Explanation: "ccbccacc" would also be a correct answer.


**Example 2:**

Input: a = 2, b = 2, c = 1

Output: "aabbc"


**Example 3:**

Input: a = 7, b = 1, c = 0

Output: "aabaa"

Explanation: It's the only correct answer in this case.


**Constraints:**
- `0 <= a, b, c <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Need to create longest string with characters 'a', 'b', 'c'
- Cannot have same character appear 3 times consecutively
- Want to maximize length: use all available characters if possible
- Greedy approach: use most frequent character first, but avoid triples

### Key Insights
1. **Greedy with Heap**: Always try to use character with highest remaining count
2. **Avoid Triples**: Keep track of last two characters to prevent 'aaa', 'bbb', 'ccc'
3. **Fallback Strategy**: If can't use most frequent, use second most frequent
4. **Termination**: Stop when no valid character can be added

### Approach Selection
**Chosen Approach:** Max Heap with Character Tracking  
**Why this approach?** 
- O(n log 3) time complexity where n = a + b + c
- Guarantees longest possible string
- Intuitive greedy approach
- Handles all edge cases

## ⚡ Complexity Analysis
- **Time Complexity:** O((a+b+c) log 3) ≈ O(n)
- **Space Complexity:** O(1) for heap (max 3 elements)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use max heap ordered by remaining count

Keep track of last two characters to avoid triples

If most frequent character would create triple, use second most

Continue until no valid characters can be added

🔗 Related Problems
Task Scheduler

Reorganize String

Rearrange String k Distance Apart

Distant Barcodes
