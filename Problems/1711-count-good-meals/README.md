# 1711. Count Good Meals

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Two Pointers  
**Companies:** Google, Amazon, Microsoft, Facebook, Bloomberg

[LeetCode Link](https://leetcode.com/problems/count-good-meals/)

A **good meal** is a meal that contains exactly two different food items with a sum of deliciousness equal to a power of two.

You can pick any two different foods to make a good meal.

Given an array of integers `deliciousness` where `deliciousness[i]` is the deliciousness of the `i-th` item of food, return the number of **different** good meals you can make from this list modulo `10^9 + 7`.

Note that items with different indices are considered different even if they have the same deliciousness value.

**Example 1:**

Input: deliciousness = [1,3,5,7,9]

Output: 4

Explanation: The good meals are (1,3), (1,7), (3,5) and (7,9).

Their respective sums are 4, 8, 8, and 16, all of which are powers of two.


**Example 2:**

Input: deliciousness = [1,1,1,3,3,3,7]

Output: 15

Explanation: The good meals for each tuple are 3 times (1,1), 3 times (1,3), 3 times (1,7), 3 times (3,3), and 3 times (3,7).


**Constraints:**
- `1 <= deliciousness.length <= 10^5`
- `0 <= deliciousness[i] <= 2^20`

## 🧠 Thought Process

### Initial Thoughts
- Need to count pairs (i, j) where deliciousness[i] + deliciousness[j] is a power of two
- Brute force O(n²) is too slow for n up to 10⁵
- Need more efficient approach, likely O(n log MAX_VALUE) or O(n)

### Key Insights
1. **Hash Map Approach**:
   - For each number, check all possible powers of two
   - For power p, look for complement = p - num in hash map
   - Count frequency of complements

2. **Power of Two Range**:
   - Maximum possible sum is 2²¹ (since deliciousness[i] ≤ 2²⁰)
   - Only need to check powers of two up to 2²¹
   - That's just 22 possible powers to check per number

3. **Combinatorics**:
   - When counting pairs, handle same value carefully
   - For value x that appears count times, pairs: count * (count-1) / 2
   - For different values x and y: count_x * count_y

### Approach Selection
**Chosen Approach:** Hash Map with Power of Two Checking  
**Why this approach?** 
- O(n log MAX_VALUE) time complexity
- O(n) space complexity
- Handles large constraints efficiently
- Modular arithmetic for large counts

## ⚡ Complexity Analysis
- **Time Complexity:** O(22n) ≈ O(n) where 22 is number of powers of two to check
- **Space Complexity:** O(n) for the hash map

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to precompute all powers of two up to max possible sum

Use hash map to count frequency of each deliciousness value

For each number, check all powers of two to find complements

Handle modulo arithmetic carefully

Account for pair counting combinatorics

🔗 Related Problems
Two Sum

4Sum II

4Sum

3Sum With Multiplicity
