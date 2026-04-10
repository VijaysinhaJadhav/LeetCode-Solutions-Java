# 135. Candy

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Greedy  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/candy/)

There are `n` children standing in a line. Each child is assigned a rating value given in the integer array `ratings`.

You are giving candies to these children subjected to the following requirements:
- Each child must have at least one candy.
- Children with a higher rating get more candies than their neighbors.

Return the **minimum number of candies** you need to have to distribute the candies to the children.

**Example 1:**

Input: ratings = [1,0,2]
Output: 5
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.


**Example 2:**

Input: ratings = [1,2,2]
Output: 4
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
The third child gets 1 candy because it satisfies the above two conditions.


**Constraints:**
- `n == ratings.length`
- `1 <= n <= 2 * 10^4`
- `0 <= ratings[i] <= 2 * 10^4`

## 🧠 Thought Process

### Problem Understanding
We need to assign candies to children such that:
1. Every child gets at least 1 candy
2. If a child has a higher rating than a neighbor, they get more candies than that neighbor

### Key Insights
1. **Two-Pass Greedy**: Left-to-right pass, then right-to-left pass
2. **Local Minimums**: Children with lower ratings can have 1 candy
3. **Candy Assignment**: Each child's candy count must satisfy both left and right neighbor constraints

### Approach Selection
**Chosen Approach:** Two-Pass Greedy  
**Why this approach?**
- O(n) time complexity
- O(n) space complexity
- Simple and intuitive
- Guarantees optimal solution

**Alternative Approaches:**
- **Single Pass with Slope**: Track increasing/decreasing slopes
- **DP**: More complex, O(n) time but O(n) space

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = number of children
- **Space Complexity:** O(n) for candy array (can be O(1) with optimization)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Initialize all children with 1 candy

First pass: left to right, if rating[i] > rating[i-1], candies[i] = candies[i-1] + 1

Second pass: right to left, if rating[i] > rating[i+1], candies[i] = max(candies[i], candies[i+1] + 1)

Sum all candies for result

🔗 Related Problems
Trapping Rain Water (similar two-pass technique)
Product of Array Except Self
Increasing Triplet Subsequence
Shortest Unsorted Continuous Subarray
Candy (this problem)
