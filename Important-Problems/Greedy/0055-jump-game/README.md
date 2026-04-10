# 55. Jump Game

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Dynamic Programming, Greedy  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/jump-game/)

You are given an integer array `nums`. You are initially positioned at the array's **first index**, and each element in the array represents your **maximum jump length** at that position.

Return `true` if you can reach the **last index**, or `false` otherwise.

**Example 1:**

Input: nums = [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.


**Example 2:**

Input: nums = [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.


**Constraints:**
- `1 <= nums.length <= 10^4`
- `0 <= nums[i] <= 10^5`

## 🧠 Thought Process

### Problem Understanding
We need to determine if we can reach the last index starting from the first index, where each element represents the maximum number of steps we can jump forward.

### Key Insights
1. **Greedy Approach**: Keep track of the farthest reachable index
2. **Early Termination**: If current index is beyond the farthest reachable, return false
3. **Reachability**: At each index, update the maximum reachable position
4. **DP Alternative**: Can also solve with DP, but greedy is O(n)

### Approach Selection
**Chosen Approach:** Greedy (Reachability)  
**Why this approach?**
- O(n) time complexity
- O(1) space complexity
- Simple and intuitive

**Alternative Approaches:**
- **Dynamic Programming**: O(n²) time, O(n) space
- **BFS/DFS**: O(n²) time, O(n) space

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of array
- **Space Complexity:** O(1) extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Track the farthest index we can reach as we iterate

If at any point we can't move forward (current index > farthest), return false

If farthest reaches or exceeds last index, return true

Greedy works because we only care about reachability, not the path

🔗 Related Problems
Jump Game II
Jump Game III
Jump Game IV
Jump Game VI
Jump Game VII
