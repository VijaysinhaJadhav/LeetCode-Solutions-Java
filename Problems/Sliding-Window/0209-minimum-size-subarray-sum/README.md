# 209. Minimum Size Subarray Sum

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search, Sliding Window, Prefix Sum  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle, Adobe

[LeetCode Link](https://leetcode.com/problems/minimum-size-subarray-sum/)

Given an array of positive integers `nums` and a positive integer `target`, return the minimal length of a **contiguous subarray** of which the sum is greater than or equal to `target`. If there is no such subarray, return `0` instead.

**Example 1:**

Input: target = 7, nums = [2,3,1,2,4,3]

Output: 2

Explanation: The subarray [4,3] has the minimal length under the problem constraint.


**Example 2:**

Input: target = 4, nums = [1,4,4]

Output: 1


**Example 3:**

Input: target = 11, nums = [1,1,1,1,1,1,1,1]

Output: 0


**Constraints:**
- `1 <= target <= 10^9`
- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^4`

**Follow-up:** If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log n).

## 🧠 Thought Process

### Initial Thoughts
- Need to find the smallest contiguous subarray with sum ≥ target
- Multiple approaches with different time/space trade-offs
- The challenge is to efficiently find the minimal window

### Key Insights
1. **Sliding Window**: Expand window until sum ≥ target, then shrink from left to find minimal
2. **Binary Search + Prefix Sum**: For each starting point, binary search for minimal ending point
3. **Two Pointers**: Use left and right pointers to maintain valid window
4. **The key insight**: When sum ≥ target, we can try to shrink the window from left to find minimal length

### Approach Selection
**Chosen Approach:** Sliding Window (Two Pointers)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Intuitive and efficient
- Handles all edge cases well

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each element visited at most twice
- **Space Complexity:** O(1) - Only constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The sliding window technique is optimal for this problem

We expand the window until sum ≥ target, then shrink from left to minimize

Handle the case where no subarray satisfies the condition

All elements are positive, which guarantees the sliding window works

🔗 Related Problems
Longest Substring Without Repeating Characters

Minimum Window Substring

Maximum Size Subarray Sum Equals k

Subarray Sum Equals K

Subarray Product Less Than K
