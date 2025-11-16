# 300. Longest Increasing Subsequence

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search, Dynamic Programming  
**Companies:** Amazon, Microsoft, Google, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/longest-increasing-subsequence/)

Given an integer array `nums`, return the length of the longest strictly increasing subsequence.

**Example 1:**

Input: nums = [10,9,2,5,3,7,101,18]

Output: 4

Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.


**Example 2:**

Input: nums = [0,1,0,3,2,3]

Output: 4


**Example 3:**

Input: nums = [7,7,7,7,7,7,7]

Output: 1


**Constraints:**
- `1 <= nums.length <= 2500`
- `-10^4 <= nums[i] <= 10^4`

**Follow up:** Can you come up with an algorithm that runs in O(n log n) time complexity?

## 🧠 Thought Process

### Initial Thoughts
- Need to find the longest subsequence (not necessarily contiguous) where elements are strictly increasing
- Classic dynamic programming problem with multiple approaches
- Can solve with O(n²) DP or optimize to O(n log n) with binary search

### Key Insights
1. **Dynamic Programming**: For each element, find longest increasing subsequence ending at that element
2. **Binary Search Approach**: Maintain active lists and use binary search to find insertion positions
3. **Patience Sorting**: The O(n log n) solution is based on patience sorting algorithm

### Approach Selection
**Chosen Approach:** Binary Search with Active Lists  
**Why this approach?** 
- O(n log n) time complexity
- O(n) space complexity
- Meets follow-up requirement
- Elegant and efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - n elements with log n binary search each
- **Space Complexity:** O(n) - Storage for active lists

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The binary search approach maintains the smallest possible tail values for all increasing subsequences

When a new number is larger than all tails, extend the longest subsequence

Otherwise, replace the first tail that is >= current number

This approach finds the length but not the actual subsequence

🔗 Related Problems
Increasing Triplet Subsequence

Russian Doll Envelopes

Number of Longest Increasing Subsequence

Maximum Length of Pair Chain

Non-decreasing Subsequences
