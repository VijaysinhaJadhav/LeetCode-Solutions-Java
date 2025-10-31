# 1929. Concatenation of Array

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array  
**Companies:** Google, Amazon, Microsoft (common in beginner interviews)

[LeetCode Link](https://leetcode.com/problems/concatenation-of-array/)

Given an integer array `nums` of length `n`, you want to create an array `ans` of length `2n` where:
- `ans[i] == nums[i]`
- `ans[i + n] == nums[i]`

for `0 <= i < n` (0-indexed).

Specifically, `ans` is the concatenation of two `nums` arrays.

Return the array `ans`.

**Example 1:**

Input: nums = [1,2,1]

Output: [1,2,1,1,2,1]

Explanation: The array ans is formed as follows:

ans = [nums[0], nums[1], nums[2], nums[0], nums[1], nums[2]]

ans = [1,2,1,1,2,1]


**Example 2:**

Input: nums = [1,3,2,1]

Output: [1,3,2,1,1,3,2,1]

Explanation: The array ans is formed as follows:

ans = [nums[0], nums[1], nums[2], nums[3], nums[0], nums[1], nums[2], nums[3]]

ans = [1,3,2,1,1,3,2,1]


## 🧠 Thought Process

### Initial Thoughts
- We need to create a new array that's twice the length of the input array
- The first half should be identical to the input array
- The second half should also be identical to the input array
- This is essentially duplicating the array and appending the copy

### Key Insights
1. The result array length is exactly `2 * nums.length`
2. For indices `0` to `n-1`, we copy `nums[i]` to `ans[i]`
3. For indices `n` to `2n-1`, we copy `nums[i - n]` to `ans[i]`
4. Alternatively, we can think of it as two identical copies of the original array

### Approach Selection
**Chosen Approach:** Simple Array Iteration  
**Why this approach?** 
- Straightforward and intuitive
- O(n) time complexity is optimal
- O(n) space complexity is required for the output
- Easy to understand and implement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - We iterate through the array twice (but in separate loops or one loop)
- **Space Complexity:** O(n) - We create a new array of size 2n (output space doesn't count against complexity in most interviews, but we're using O(n) extra space)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
This is a great beginner problem to understand array manipulation

The solution demonstrates basic array copying techniques

Can be solved with a single loop or two separate loops

No edge cases beyond empty array, which is handled naturally

🔗 Related Problems
88. Merge Sorted Array

1089. Duplicate Zeros

1480. Running Sum of 1d Array
