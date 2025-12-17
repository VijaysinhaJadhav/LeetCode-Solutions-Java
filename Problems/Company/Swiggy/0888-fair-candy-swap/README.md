# 888. Fair Candy Swap

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Binary Search, Sorting  
**Companies:** Google, Amazon, Microsoft, Bloomberg

[LeetCode Link](https://leetcode.com/problems/fair-candy-swap/)

Alice and Bob have a different total number of candies. You are given two integer arrays `aliceSizes` and `bobSizes` where `aliceSizes[i]` is the number of candies of the `i`-th box of candy that Alice has and `bobSizes[j]` is the number of candies of the `j`-th box of candy that Bob has.

Since they are friends, they would like to exchange one candy box each so that after the exchange, they both have the same total amount of candy. The total amount of candy a person has is the sum of the number of candies in each box they have.

Return *an integer array* `answer` *where* `answer[0]` *is the number of candies in the box that Alice must exchange, and* `answer[1]` *is the number of candies in the box that Bob must exchange*. If there are multiple answers, you may return any one of them. It is guaranteed an answer exists.

**Example 1:**

Input: aliceSizes = [1,1], bobSizes = [2,2]

Output: [1,2]

Explanation:

Alice exchanges her 1 candy box for Bob's 2 candy box.

Alice's total: 1 + 1 + 2 - 1 = 3

Bob's total: 2 + 2 + 1 - 2 = 3


**Example 2:**

Input: aliceSizes = [1,2], bobSizes = [2,3]

Output: [1,2]

Explanation:

Alice exchanges her 1 candy box for Bob's 2 candy box.

Alice's total: 1 + 2 + 2 - 1 = 4

Bob's total: 2 + 3 + 1 - 2 = 4


**Example 3:**

Input: aliceSizes = [2], bobSizes = [1,3]

Output: [2,3]


**Constraints:**
- `1 <= aliceSizes.length, bobSizes.length <= 10^4`
- `1 <= aliceSizes[i], bobSizes[j] <= 10^5`
- It is guaranteed that Alice and Bob have different total amounts of candy.
- It is guaranteed that there exists an answer.

## 🧠 Thought Process

### Initial Thoughts
- Need to find one candy box from Alice and one from Bob to exchange
- After exchange, both should have equal total candy
- Need to handle potentially large arrays (up to 10^4 elements)

### Key Insights
1. **Mathematical Relationship**:
   - Let `sumA` = total candy Alice has, `sumB` = total candy Bob has
   - Let `x` = candy box Alice gives, `y` = candy box Bob gives
   - After exchange: `sumA - x + y = sumB - y + x`
   - Simplify: `sumA - sumB = 2x - 2y`
   - Therefore: `x - y = (sumA - sumB) / 2`
   - Or: `y = x - delta` where `delta = (sumA - sumB) / 2`

2. **Problem Reduction**:
   - Find `x` in Alice's array and `y` in Bob's array such that `y = x - delta`
   - `delta = (sumA - sumB) / 2` is constant

3. **Efficient Lookup**:
   - Store Bob's candies in a HashSet for O(1) lookup
   - For each candy `x` in Alice's array, check if `x - delta` exists in Bob's set

### Approach Selection
**Chosen Approach:** HashSet with Mathematical Formula  
**Why this approach?** 
- O(n + m) time complexity
- O(m) space complexity for HashSet
- Simple and efficient
- Handles all cases optimally

## ⚡ Complexity Analysis
- **Time Complexity:** O(n + m) where n = aliceSizes.length, m = bobSizes.length
- **Space Complexity:** O(m) for storing Bob's candies in HashSet

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Calculate the difference delta = (sumA - sumB) / 2

Alice gives x, Bob gives y = x - delta

Use HashSet for O(1) lookups of Bob's candies

Guaranteed solution exists by problem constraints

🔗 Related Problems
Two Sum

Two Sum II - Input Array Is Sorted

Intersection of Two Arrays

Intersection of Two Arrays II

K-diff Pairs in an Array
