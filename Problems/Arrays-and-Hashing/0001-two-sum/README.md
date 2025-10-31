# 1. Two Sum

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table  
**Companies:** Amazon, Google, Facebook, Apple, Microsoft

[LeetCode Link](https://leetcode.com/problems/two-sum/)

Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`. You may assume that each input would have exactly one solution, and you may not use the same element twice.

**Example 1:**

Input: nums = [2,7,11,15], target = 9

Output: [0,1]

Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].


**Example 2:**

Input: nums = [3,2,4], target = 6

Output: [1,2]


**Example 3:**

Input: nums = [3,3], target = 6

Output: [0,1]


## 🧠 Thought Process

### Initial Thoughts
- The brute force approach would be to check every possible pair of numbers, which would be O(n²) time complexity
- This would be inefficient for large arrays
- Need to find a way to reduce the time complexity

### Key Insights
1. The problem can be rewritten as: for each number `nums[i]`, find if `target - nums[i]` exists in the array
2. We need quick lookups to check if a number exists - Hash Table is perfect for this
3. We should store numbers we've seen along with their indices for O(1) lookups

### Approach Selection
**Chosen Approach:** One-pass Hash Table  
**Why this approach?** 
- Reduces time complexity from O(n²) to O(n)
- Only requires one iteration through the array
- Handles all edge cases including duplicates

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - We traverse the list containing n elements only once
- **Space Complexity:** O(n) - The hash map can store up to n elements in the worst case

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
We add the number to the hash map AFTER checking for its complement to avoid using the same element twice

The problem guarantees exactly one solution, so we don't need to handle multiple solutions

This approach works with negative numbers and zeros

🔗 Related Problems
167. Two Sum II - Input Array Is Sorted

170. Two Sum III - Data structure design

15. 3Sum
