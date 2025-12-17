# 202. Happy Number

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Hash Table, Math, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Meta, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/happy-number/)

Write an algorithm to determine if a number `n` is happy.

A **happy number** is a number defined by the following process:
- Starting with any positive integer, replace the number by the sum of the squares of its digits.
- Repeat the process until the number equals 1 (where it will stay), or it **loops endlessly in a cycle** which does not include 1.
- Those numbers for which this process **ends in 1** are happy.

Return `true` if `n` is a happy number, and `false` if not.

**Example 1:**

Input: n = 19

Output: true

Explanation:

1² + 9² = 82

8² + 2² = 68

6² + 8² = 100

1² + 0² + 0² = 1


**Example 2:**

Input: n = 2

Output: false

Explanation: (Cycle: 2 → 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4)


**Constraints:**
- `1 <= n <= 2^31 - 1`

## 🧠 Thought Process

### Initial Thoughts
- Need to repeatedly calculate sum of squares of digits
- Need to detect cycles (when numbers repeat)
- If we reach 1, it's a happy number
- If we enter a cycle that doesn't include 1, it's not a happy number

### Key Insights
1. **Cycle Detection Problem**: Similar to detecting cycles in linked lists
2. **Two Approaches**:
   - Hash Set: Store seen numbers to detect cycles
   - Floyd's Cycle Detection: Fast and slow pointers
3. **Sum of Squares Calculation**: Extract digits using modulo and division
4. **Mathematical Insight**: All unhappy numbers end in cycle containing 4

### Approach Selection
**Chosen Approach:** Floyd's Cycle Detection Algorithm  
**Why this approach?** 
- O(log n) time complexity
- O(1) space complexity (no extra storage needed)
- Elegant and efficient
- Common pattern for cycle detection

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) - Each step reduces the number significantly
- **Space Complexity:** O(1) - Only a few variables needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use Floyd's cycle detection (fast and slow pointers)

Alternative: Use HashSet to detect cycles

Mathematical fact: All unhappy numbers eventually reach 4

Can precompute small cycles for optimization

🔗 Related Problems
Linked List Cycle

Linked List Cycle II

Add Digits

Ugly Number

Perfect Number
