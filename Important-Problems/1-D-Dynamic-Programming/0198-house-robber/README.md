# 198. House Robber

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/house-robber/)

You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given an integer array `nums` representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.

**Example 1:**

Input: nums = [1,2,3,1]

Output: 4

Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).

Total amount you can rob = 1 + 3 = 4.


**Example 2:**

Input: nums = [2,7,9,3,1]

Output: 12

Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).

Total amount you can rob = 2 + 9 + 1 = 12.


**Constraints:**
- `1 <= nums.length <= 100`
- `0 <= nums[i] <= 400`

## 🧠 Thought Process

### Initial Thoughts
- Cannot rob adjacent houses
- Need to maximize total loot
- At each house, choose to rob or skip
- Decision depends on previous houses' choices

### Key Insights
1. **Dynamic Programming Approach**: Use DP to store maximum loot up to each house
2. **State Transition**: 
   - Rob current house: loot = nums[i] + dp[i-2]
   - Skip current house: loot = dp[i-1]
3. **Space Optimization**: Only need last two values, not entire DP array
4. **Base Cases**: Handle empty array, single house, two houses

### Approach Selection
**Chosen Approach:** Dynamic Programming with Space Optimization  
**Why this approach?** 
- O(n) time complexity is optimal
- O(1) space complexity is efficient
- Clear and intuitive implementation
- Handles all edge cases properly

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through houses
- **Space Complexity:** O(1) - Only two variables needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
At each house, choose maximum between robbing current + i-2 or skipping current

Initialize with rob1 = 0, rob2 = 0 for clean state transitions

Can be extended to circular arrangement (House Robber II)

Similar to maximum sum of non-adjacent elements

🔗 Related Problems
House Robber II

House Robber III

Paint House

Best Time to Buy and Sell Stock

Climbing Stairs
