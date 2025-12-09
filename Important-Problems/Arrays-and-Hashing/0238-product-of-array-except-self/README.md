# 238. Product of Array Except Self

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Prefix Sum  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/product-of-array-except-self/)

Given an integer array `nums`, return an array `answer` such that `answer[i]` is equal to the product of all the elements of `nums` except `nums[i]`.

The product of any prefix or suffix of `nums` is **guaranteed** to fit in a **32-bit** integer.

You must write an algorithm that runs in `O(n)` time and without using the division operation.

**Example 1:**

Input: nums = [1,2,3,4]

Output: [24,12,8,6]


**Example 2:**

Input: nums = [-1,1,0,-3,3]

Output: [0,0,9,0,0]


**Constraints:**
- `2 <= nums.length <= 10^5`
- `-30 <= nums[i] <= 30`
- The product of any prefix or suffix of `nums` is guaranteed to fit in a 32-bit integer.

**Follow up:** Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)

## 🧠 Thought Process

### Initial Thoughts
- Cannot use division operation (explicit constraint)
- Need O(n) time complexity
- Multiple approaches with different space complexities
- Need to handle zeros carefully since they make products zero

### Key Insights
1. **Prefix and Suffix Products**: Calculate left products and right products, then combine
2. **Single Pass Optimization**: Compute result in single pass using running products
3. **Space Optimization**: Use output array to store intermediate results
4. **Zero Handling**: Special handling when zeros are present

### Approach Selection
**Chosen Approach:** Prefix and Suffix Products with O(1) Space  
**Why this approach?** 
- O(n) time complexity
- O(1) extra space (excluding output array)
- Meets follow-up requirement
- Elegant and efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Two passes through the array
- **Space Complexity:** O(1) - Only using constant extra space (output array doesn't count)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key insight is that product except self = left product * right product

We can compute left products in one pass and right products in another pass

For O(1) space, we use the output array to store intermediate results

Zeros require special attention but are handled naturally by this approach

🔗 Related Problems
42. Trapping Rain Water

152. Maximum Product Subarray

53. Maximum Subarray

325. Maximum Size Subarray Sum Equals k


