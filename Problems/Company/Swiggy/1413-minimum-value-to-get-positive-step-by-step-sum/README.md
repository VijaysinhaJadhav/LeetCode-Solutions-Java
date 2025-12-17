# 1413. Minimum Value to Get Positive Step by Step Sum

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Prefix Sum  
**Companies:** Bloomberg, Amazon, Google

[LeetCode Link](https://leetcode.com/problems/minimum-value-to-get-positive-step-by-step-sum/)

Given an array of integers `nums`, you start with an initial **positive** value `startValue`.

In each iteration, you calculate the step by step sum of `startValue` plus elements in `nums` (from left to right).

Return the minimum **positive** value of `startValue` such that the step by step sum is never less than 1.

**Example 1:**

Input: nums = [-3,2,-3,4,2]

Output: 5

Explanation: If you choose startValue = 4, you have:

Step 1: startValue = 4, sum = 4 + (-3) = 1

Step 2: sum = 1 + 2 = 3

Step 3: sum = 3 + (-3) = 0 (less than 1)

Step 4: sum = 0 + 4 = 4

Step 5: sum = 4 + 2 = 6

If you choose startValue = 5, you have:

Step 1: startValue = 5, sum = 5 + (-3) = 2

Step 2: sum = 2 + 2 = 4

Step 3: sum = 4 + (-3) = 1

Step 4: sum = 1 + 4 = 5

Step 5: sum = 5 + 2 = 7

So the minimum startValue is 5.


**Example 2:**

Input: nums = [1,2]

Output: 1

Explanation: Minimum start value should be positive.


**Example 3:**

Input: nums = [1,-2,-3]

Output: 5


**Constraints:**
- `1 <= nums.length <= 100`
- `-100 <= nums[i] <= 100`

## 🧠 Thought Process

### Initial Thoughts
- We need to find the smallest positive starting value such that when we add the array elements sequentially, the running sum never drops below 1.
- This is essentially about finding the most negative point in the prefix sum and compensating for it.
- The starting value must be positive (at least 1).

### Key Insights
1. **Prefix Sum Concept**: Track the cumulative sum as we process the array.
2. **Minimum Running Sum**: Find the minimum value of the running sum starting from 0.
3. **Formula**: If the minimum running sum is `minSum`, then the required starting value is `max(1, 1 - minSum)`.
   - If `minSum` is already ≥ 1, we only need startValue = 1
   - If `minSum` is negative, we need enough starting value to bring it up to at least 1
4. **Intuition**: The starting value must be large enough to prevent the running sum from ever dropping below 1.

### Approach Selection
**Chosen Approach:** One-pass with running sum tracking  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Simple and intuitive
- Handles all cases efficiently

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - single pass through the array
- **Space Complexity:** O(1) - only a few variables needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Track the minimum prefix sum encountered

Starting value = max(1, 1 - minPrefixSum)

Edge case: array with all positive numbers (min start value is 1)

The problem ensures answer fits in 32-bit integer

🔗 Related Problems
Maximum Subarray

Maximum Product Subarray

Product of Array Except Self

Find Pivot Index

Running Sum of 1d Array
