# 152. Maximum Product Subarray

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/maximum-product-subarray/)

Given an integer array `nums`, find a contiguous non-empty subarray within the array that has the largest product, and return *the product*.

The test cases are generated so that the answer will fit in a **32-bit** integer.

**Example 1:**

Input: nums = [2,3,-2,4]

Output: 6

Explanation: [2,3] has the largest product 6.


**Example 2:**

Input: nums = [-2,0,-1]

Output: 0

Explanation: The result cannot be 2, because [-2,-1] is not a subarray.


**Constraints:**
- `1 <= nums.length <= 2 * 10^4`
- `-10 <= nums[i] <= 10`

## 🧠 Thought Process

### Initial Thoughts
- Need to find maximum product of contiguous subarray
- Similar to maximum sum subarray (Kadane's algorithm) but more complex
- Negative numbers can flip sign and turn minimum into maximum
- Zero resets the product
- Need to track both maximum and minimum products

### Key Insights
1. **Two DP States**: Need to track both max and min products ending at each position
2. **Negative Handling**: When encountering negative number, swap max and min
3. **Zero Handling**: Zero resets both max and min to 0 (or skip)
4. **Kadane-like Algorithm**: Similar to max sum subarray but with two variables

### Approach Selection
**Chosen Approach:** Dynamic Programming with Two Variables  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Handles all cases (negatives, zeros, positives)
- Elegant and efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - single pass through array
- **Space Complexity:** O(1) - only a few variables needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Track both maxSoFar and minSoFar at each position

When encountering negative number, swap max and min

Compare with current number to handle zeros

Update global maximum at each step

🔗 Related Problems
Maximum Subarray (Kadane's Algorithm)

Maximum Product of Three Numbers

Subarray Product Less Than K

Product of Array Except Self
