# 189. Rotate Array

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Math, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/rotate-array/)

Given an integer array `nums`, rotate the array to the right by `k` steps, where `k` is non-negative.

**Example 1:**

Input: nums = [1,2,3,4,5,6,7], k = 3

Output: [5,6,7,1,2,3,4]

Explanation:

rotate 1 steps to the right: [7,1,2,3,4,5,6]

rotate 2 steps to the right: [6,7,1,2,3,4,5]

rotate 3 steps to the right: [5,6,7,1,2,3,4]


**Example 2:**

Input: nums = [-1,-100,3,99], k = 2

Output: [3,99,-1,-100]

Explanation:

rotate 1 steps to the right: [99,-1,-100,3]

rotate 2 steps to the right: [3,99,-1,-100]


**Constraints:**
- `1 <= nums.length <= 10^5`
- `-2^31 <= nums[i] <= 2^31 - 1`
- `0 <= k <= 10^5`

## 🧠 Thought Process

### Initial Thoughts
- Need to rotate array to the right by k positions
- k can be larger than array length (need modulo operation)
- Multiple approaches with different time/space complexities
- Must do it in-place with O(1) extra space

### Key Insights
1. **Reverse Method**: Most elegant approach - O(n) time, O(1) space
2. **Cyclic Replacements**: In-place rotation using GCD
3. **Extra Array**: Simple but uses O(n) space
4. **The key insight**: k = k % n to handle k > n cases

### Approach Selection
**Chosen Approach:** Reverse Method  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- In-place modification
- Elegant and efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Three passes through the array
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
First normalize k using modulo: k = k % n

Reverse the entire array

Reverse first k elements

Reverse remaining n-k elements

The algorithm works by strategically reversing segments

🔗 Related Problems
Rotate List

Reverse Words in a String II

Rotate Function

Reverse Words in a String

Reverse String
