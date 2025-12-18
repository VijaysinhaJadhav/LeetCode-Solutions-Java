# 53. Maximum Subarray

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Divide and Conquer, Dynamic Programming  
**Companies:** Amazon, Microsoft, Apple, Google, Meta, Bloomberg, Adobe, Uber, LinkedIn, Oracle, Goldman Sachs, TikTok

[LeetCode Link](https://leetcode.com/problems/maximum-subarray/)

Given an integer array `nums`, find the subarray with the largest sum, and return its sum.

**Example 1:**

Input: nums = [-2,1,-3,4,-1,2,1,-5,4]

Output: 6

Explanation: The subarray [4,-1,2,1] has the largest sum 6.


**Example 2:**

Input: nums = [1]

Output: 1

Explanation: The subarray [1] has the largest sum 1.


**Example 3:**

Input: nums = [5,4,-1,7,8]

Output: 23

Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.


**Constraints:**
- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

**Follow up:** If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.

## 🧠 Thought Process

### Initial Thoughts
- Need to find contiguous subarray with maximum sum
- Brute force O(n²) checking all subarrays is too slow (n up to 10^5)
- Need O(n) solution
- Negative numbers make it tricky - might need to "reset" sum when it becomes negative

### Key Insights
1. **Kadane's Algorithm**:
   - Maintain running sum
   - Reset to current element if running sum becomes negative
   - Track maximum sum seen so far
2. **Dynamic Programming Perspective**:
   - `dp[i]` = maximum subarray sum ending at position i
   - `dp[i] = max(nums[i], dp[i-1] + nums[i])`
   - Answer = `max(dp[i])` for all i
3. **Divide and Conquer**:
   - Split array into left and right halves
   - Max subarray could be in left, right, or crossing midpoint
   - Combine results

### Approach Selection
**Primary Approach:** Kadane's Algorithm (Dynamic Programming)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Simple and elegant
- Handles all cases including all negative numbers

## ⚡ Complexity Analysis

### Kadane's Algorithm
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only a few variables needed

### Divide and Conquer
- **Time Complexity:** O(n log n) - Recursive splitting
- **Space Complexity:** O(log n) - Recursion stack depth

## 🔍 Solution Code

```java
// See Solution.java for complete implementation

📝 Notes
Kadane's algorithm is the optimal solution

Initialize with first element for handling all negatives

Track both current sum and maximum sum

Reset current sum when it becomes negative

🔗 Related Problems
152. Maximum Product Subarray

121. Best Time to Buy and Sell Stock

198. House Robber

300. Longest Increasing Subsequence

1143. Longest Common Subsequence
