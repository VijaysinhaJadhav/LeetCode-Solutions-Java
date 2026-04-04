# 611. Valid Triangle Number

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Binary Search, Sorting, Greedy  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/valid-triangle-number/)

Given an integer array `nums`, return *the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.*

**Triangle Inequality Theorem:** The sum of the lengths of any two sides must be greater than the length of the third side.

For a triangle with sides `a ≤ b ≤ c`, the condition reduces to: `a + b > c`.

**Example 1:**

Input: nums = [2,2,3,4]
Output: 3
Explanation: Valid combinations are:

2,3,4

2,3,4 (using the other 2)

2,2,3


**Example 2:**

Input: nums = [4,2,3,4]
Output: 4


**Constraints:**
- `1 <= nums.length <= 1000`
- `0 <= nums[i] <= 1000`

## 🧠 Thought Process

### Problem Understanding
We need to count all unique triplets `(i, j, k)` where `i < j < k` and `nums[i] + nums[j] > nums[k]` (after sorting, the largest side is `nums[k]`).

### Key Insights
1. **Sorting**: Sort the array first to fix the largest side as the rightmost element
2. **Two Pointers**: For each largest side `c`, use two pointers to find valid `a` and `b`
3. **Triangle Condition**: For sorted array, `a + b > c` is sufficient (since `a ≤ b ≤ c`)
4. **Optimization**: Once we find a valid `a` and `b`, all pairs between them are valid

### Approach Selection
**Chosen Approach:** Sort + Two Pointers  
**Why this approach?**
- O(n²) time complexity
- O(1) extra space
- More efficient than O(n³) brute force
- Uses the two-pointer technique effectively

**Alternative Approaches:**
- **Brute Force**: Check all O(n³) triplets
- **Binary Search**: For each pair, binary search for valid `c`
- **Counting Sort**: Since values ≤ 1000, we can use frequency array

## ⚡ Complexity Analysis
- **Time Complexity:** O(n²) where n = length of array
- **Space Complexity:** O(1) extra space (excluding sorting)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sorting simplifies the triangle condition

For each largest side, use two pointers from start and just before largest

When nums[left] + nums[right] > nums[i], all pairs between left and right are valid

Skip zeros as they can't form valid triangles

🔗 Related Problems
3Sum
3Sum Closest
4Sum
3Sum Smaller
3Sum With Multiplicity
