# 2006. Count Number of Pairs With Absolute Difference K

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Counting  
**Companies:** Amazon, Google, Microsoft, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/count-number-of-pairs-with-absolute-difference-k/)

Given an integer array `nums` and an integer `k`, return *the number of pairs* `(i, j)` where `i < j` such that `|nums[i] - nums[j]| == k`.

The value of `|x|` is defined as:
- `x` if `x >= 0`
- `-x` if `x < 0`

**Example 1:**

Input: nums = [1,2,2,1], k = 1
Output: 4
Explanation: The pairs with an absolute difference of 1 are:

[1,2,2,1] → (1,2), (1,2), (2,1), (2,1)


**Example 2:**

Input: nums = [1,3], k = 3
Output: 0
Explanation: There are no pairs with an absolute difference of 3.


**Example 3:**

Input: nums = [3,2,1,5,4], k = 2
Output: 3
Explanation: The pairs with an absolute difference of 2 are:

(3,1), (3,5), (2,4)


**Constraints:**
- `1 <= nums.length <= 200`
- `1 <= nums[i] <= 100`
- `1 <= k <= 99`

## 🧠 Thought Process

### Problem Understanding
We need to count all pairs `(i, j)` with `i < j` such that the absolute difference between `nums[i]` and `nums[j]` equals `k`.

### Key Insights
1. **Brute Force**: Check all pairs O(n²) - acceptable for n ≤ 200
2. **Hash Map**: Store frequencies and count complements (x + k and x - k)
3. **Sorting + Two Pointers**: Sort array and use two pointers to find pairs
4. **Frequency Array**: Since values are bounded (1 to 100), use a frequency array

### Approach Selection
**Chosen Approach:** Hash Map (Frequency Counting)  
**Why this approach?**
- O(n) time complexity
- O(n) space complexity
- Simple and efficient

**Alternative Approaches:**
- **Brute Force**: O(n²) time, O(1) space
- **Sorting + Two Pointers**: O(n log n) time, O(1) space
- **Frequency Array**: O(n + maxVal) time, O(maxVal) space

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of array
- **Space Complexity:** O(n) for hash map

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
For each number num, check if num + k and num - k exist in the map

Count pairs by multiplying frequencies when k = 0 (special case)

Use i < j constraint → only count each pair once

Can use frequency array since values are bounded

🔗 Related Problems
K-diff Pairs in an Array
Two Sum
Two Sum II - Input Array Is Sorted
Contains Duplicate II
Contains Duplicate III
