# 560. Subarray Sum Equals K

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Prefix Sum  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Oracle

[LeetCode Link](https://leetcode.com/problems/subarray-sum-equals-k/)

Given an array of integers `nums` and an integer `k`, return the total number of subarrays whose sum equals to `k`.

A subarray is a contiguous non-empty sequence of elements within the array.

**Example 1:**

Input: nums = [1,1,1], k = 2

Output: 2

Explanation: The subarrays [1,1] and [1,1] (starting from indices 0-1 and 1-2 respectively).


**Example 2:**

Input: nums = [1,2,3], k = 3

Output: 2

Explanation: The subarrays [1,2] and [3].


**Constraints:**
- `1 <= nums.length <= 2 * 10^4`
- `-1000 <= nums[i] <= 1000`
- `-10^7 <= k <= 10^7`

## 🧠 Thought Process

### Initial Thoughts
- Need to find contiguous subarrays with sum exactly equal to k
- Multiple approaches with different time/space trade-offs
- Need to handle negative numbers and zeros in the array

### Key Insights
1. **Prefix Sum Technique**: Calculate cumulative sums to find subarray sums efficiently
2. **HashMap Optimization**: Store prefix sums and their frequencies to find subarrays in O(1) time
3. **Mathematical Relationship**: If prefixSum[i] - prefixSum[j] = k, then sum from j+1 to i equals k

### Approach Selection
**Chosen Approach:** Prefix Sum with HashMap  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Handles negative numbers and zeros correctly
- Most efficient for the problem constraints

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(n) - HashMap storage for prefix sums

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key formula: currentSum - k exists in map means we found subarrays ending at current index

Initialize map with (0, 1) to handle subarrays starting from index 0

The algorithm works by maintaining a running sum and checking for complement sums

🔗 Related Problems
Continuous Subarray Sum

Maximum Size Subarray Sum Equals k

Minimum Size Subarray Sum

Binary Subarrays With Sum

Subarray Sums Divisible by K
