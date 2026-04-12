# 268. Missing Number

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Math, Binary Search, Bit Manipulation, Sorting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/missing-number/)

Given an array `nums` containing `n` distinct numbers in the range `[0, n]`, return *the only number in the range that is missing from the array*.

**Example 1:**

Input: nums = [3,0,1]
Output: 2
Explanation: n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 2 is the missing number.


**Example 2:**

Input: nums = [0,1]
Output: 2
Explanation: n = 2 since there are 2 numbers, so all numbers are in the range [0,2]. 2 is the missing number.


**Example 3:**

Input: nums = [9,6,4,2,3,5,7,0,1]
Output: 8


**Constraints:**
- `n == nums.length`
- `1 <= n <= 10^4`
- `0 <= nums[i] <= n`
- All the numbers of `nums` are **unique**.

## 🧠 Thought Process

### Problem Understanding
We have an array of length `n` containing distinct numbers from `0` to `n`. One number is missing. Find it.

### Key Insights
1. **Sum Formula**: Sum of numbers 0 to n = n * (n + 1) / 2. Missing = expected_sum - actual_sum
2. **XOR Property**: a ^ a = 0, a ^ 0 = a. XOR all indices and values → missing number
3. **Sorting**: Sort and find where index != value
4. **Hash Set**: Store all numbers, check which is missing

### Approach Selection
**Chosen Approach:** XOR (Bit Manipulation)  
**Why this approach?**
- O(n) time complexity
- O(1) space complexity
- No overflow issues
- Elegant and efficient

**Alternative Approaches:**
- **Sum Formula**: O(n) time, O(1) space (potential overflow)
- **Sorting**: O(n log n) time, O(1) space
- **Hash Set**: O(n) time, O(n) space

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of array
- **Space Complexity:** O(1) extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
XOR approach: result = n ^ 0 ^ 1 ^ 2 ^ ... ^ n ^ nums[0] ^ nums[1] ^ ... ^ nums[n-1]

Sum formula: missing = n*(n+1)/2 - sum(nums)

Handle edge case: n = 1, nums = [0] → missing = 1

All numbers are unique and in range [0, n]

🔗 Related Problems
First Missing Positive
Find All Numbers Disappeared in an Array
Find the Duplicate Number
Single Number
Missing Number (this problem)
