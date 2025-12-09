# 128. Longest Consecutive Sequence

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Union Find  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/longest-consecutive-sequence/)

Given an unsorted array of integers `nums`, return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in `O(n)` time.

**Example 1:**

Input: nums = [100,4,200,1,3,2]

Output: 4

Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.


**Example 2:**

Input: nums = [0,3,7,2,5,8,4,6,0,1]

Output: 9


**Constraints:**
- `0 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- Need to find the longest sequence of consecutive integers
- Array is unsorted and may contain duplicates
- Must achieve O(n) time complexity, so sorting is not optimal
- Multiple approaches with different trade-offs

### Key Insights
1. **HashSet Approach**: Store all numbers in a set, then find sequence starts
2. **Union Find**: Connect consecutive numbers and find largest connected component
3. **Sorting**: Simple but O(n log n) time, doesn't meet requirement
4. **The key insight**: A number is a sequence start if num-1 doesn't exist in the set

### Approach Selection
**Chosen Approach:** HashSet with Sequence Detection  
**Why this approach?** 
- O(n) time complexity on average
- O(n) space complexity
- Simple and elegant
- Meets the O(n) time requirement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each number is processed at most twice
- **Space Complexity:** O(n) - HashSet storage

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The algorithm only starts counting sequences from numbers that are sequence starts

This ensures each sequence is only processed once

Duplicates are automatically handled by the HashSet

The solution works with negative numbers and large ranges

🔗 Related Problems
298. Binary Tree Longest Consecutive Sequence

674. Longest Continuous Increasing Subsequence

485. Max Consecutive Ones

549. Binary Tree Longest Consecutive Sequence II
