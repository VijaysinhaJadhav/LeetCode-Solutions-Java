# 410. Split Array Largest Sum

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Binary Search, Dynamic Programming, Greedy  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/split-array-largest-sum/)

Given an integer array `nums` and an integer `k`, split `nums` into `k` non-empty subarrays such that the largest sum of any subarray is minimized.

Return the minimized largest sum of the split.

**Example 1:**

Input: nums = [7,2,5,10,8], k = 2

Output: 18

Explanation:

There are four ways to split nums into two subarrays.

The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two subarrays is 18.


**Example 2:**

Input: nums = [1,2,3,4,5], k = 2

Output: 9


**Example 3:**

Input: nums = [1,4,4], k = 3

Output: 4


**Constraints:**
- `1 <= nums.length <= 1000`
- `0 <= nums[i] <= 10^6`
- `1 <= k <= min(50, nums.length)`

## 🧠 Thought Process

### Initial Thoughts
- Need to split array into k subarrays with minimum maximum subarray sum
- This is a classic minimization problem
- Multiple approaches: binary search, dynamic programming, greedy

### Key Insights
1. **Binary Search Approach**: Search for the minimum possible maximum sum
2. **Search Space**: Lower bound = max element, upper bound = total sum
3. **Feasibility Check**: Use greedy approach to check if a given sum is feasible
4. **Dynamic Programming**: DP[i][j] = min max sum splitting first i elements into j parts

### Approach Selection
**Chosen Approach:** Binary Search with Greedy Validation  
**Why this approach?** 
- O(n log S) time complexity is efficient (S = total sum)
- O(1) space complexity
- Intuitive and optimal for given constraints
- Easier to implement than DP

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log S) where S is total sum of array
- **Space Complexity:** O(1) - Only a few variables needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Lower bound for search is max element (each subarray must contain at least one element)

Upper bound for search is total sum (all elements in one subarray)

Greedy validation tries to fit as many elements as possible in each subarray

Can also solve with dynamic programming for smaller constraints

🔗 Related Problems
Capacity To Ship Packages Within D Days

Koko Eating Bananas

Divide Chocolate

The Earliest Moment When Everyone Become Friends
