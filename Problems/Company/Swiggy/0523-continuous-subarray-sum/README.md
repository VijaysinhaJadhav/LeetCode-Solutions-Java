# 523. Continuous Subarray Sum

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Math, Prefix Sum  
**Companies:** Google, Amazon, Microsoft, Meta, Bloomberg

[LeetCode Link](https://leetcode.com/problems/continuous-subarray-sum/)

Given an integer array `nums` and an integer `k`, return `true` *if `nums` has a good subarray or `false` otherwise*.

A **good subarray** is a subarray where:
- Its length is **at least two**, and
- The sum of the elements of the subarray is a multiple of `k`.

**Note:**
- A **subarray** is a contiguous **non-empty** sequence of elements within an array.
- An integer `x` is a multiple of `k` if there exists an integer `n` such that `x = n * k`. `0` is **always** a multiple of `k`.

**Example 1:**

Input: nums = [23,2,4,6,7], k = 6

Output: true

Explanation: [2, 4] is a continuous subarray of size 2 whose sum is 6.


**Example 2:**

Input: nums = [23,2,6,4,7], k = 6

Output: true

Explanation: [23, 2, 6, 4, 7] is a continuous subarray of size 5 whose sum is 42.

42 is a multiple of 6 because 42 = 7 * 6.


**Example 3:**

Input: nums = [23,2,6,4,7], k = 13

Output: false


**Constraints:**
- `1 <= nums.length <= 10^5`
- `0 <= nums[i] <= 10^9`
- `0 <= k <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- Need to find subarray of length ≥ 2 with sum divisible by k
- Brute force O(n²) checking all subarrays is too slow (n up to 10^5)
- Need O(n) or O(n log n) solution

### Key Insights
1. **Prefix Sum + Modular Arithmetic**:
   - If sum(nums[i:j]) % k == 0, then (prefix[j+1] - prefix[i]) % k == 0
   - Which means prefix[j+1] % k == prefix[i] % k
2. **Same Remainder Principle**: 
   - If two prefix sums have same remainder modulo k, the subarray between them has sum divisible by k
3. **Hash Map for Remainders**: Store first occurrence of each remainder
4. **Edge Cases**: 
   - Handle k = 0 (check for consecutive zeros or sum == 0)
   - Subarray length ≥ 2 (store index, not just existence)
   - Handle large numbers (use modulo arithmetic)

### Approach Selection
**Chosen Approach:** Prefix Sum with Hash Map (Modulo Arithmetic)  
**Why this approach?** 
- O(n) time complexity
- O(min(n, k)) space complexity
- Handles all edge cases including k = 0
- Based on mathematical insight about remainders

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(min(n, k)) - Hash map storing remainders

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use hash map to store first occurrence of each remainder

Check if current remainder seen before with distance ≥ 2

Special handling for k = 0 (look for two consecutive zeros)

Initialize with remainder 0 at index -1 for edge case

🔗 Related Problems
Subarray Sum Equals K

Subarray Sums Divisible by K

Contiguous Array

Minimum Size Subarray Sum

Subarray Product Less Than K
