# 845. Longest Mountain in Array

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Dynamic Programming  
**Companies:** Google, Microsoft, Amazon, Facebook, Bloomberg, Apple  

[LeetCode Link](https://leetcode.com/problems/longest-mountain-in-array/)

You may recall that an array `arr` is a **mountain array** if and only if:

- `arr.length >= 3`
- There exists some index `i` (`0 < i < arr.length - 1`) such that:
  - `arr[0] < arr[1] < ... < arr[i - 1] < arr[i]`
  - `arr[i] > arr[i + 1] > ... > arr[arr.length - 1]`

Given an integer array `arr`, return *the length of the longest subarray that is a mountain array*.

Return `0` if there is no mountain subarray.

**Example 1:**

Input: arr = [2,1,4,7,3,2,5]
Output: 5
Explanation: The largest mountain is [1,4,7,3,2] which has length 5.


**Example 2:**

Input: arr = [2,2,2]
Output: 0
Explanation: There is no mountain.


**Constraints:**
- `1 <= arr.length <= 10^4`
- `0 <= arr[i] <= 10^4`

## 🧠 Thought Process

### Problem Understanding
We need to find the longest contiguous subarray that forms a mountain shape: strictly increasing to a peak, then strictly decreasing.

### Key Insights
1. **Mountain Definition**: Strictly increasing then strictly decreasing
2. **Peak Requirement**: Peak cannot be at edges (0 or n-1)
3. **Strictly Monotonic**: No equal adjacent elements allowed
4. **Contiguous**: Must be a contiguous subarray
5. **Minimum Length**: At least 3 elements

### Approach Selection
**Chosen Approach**: Two Pass - Count Up and Down Slopes  
**Why this approach?**
- O(n) time complexity
- O(n) or O(1) space complexity
- Intuitive: track increasing and decreasing sequences
- Handles all edge cases

**Alternative Approaches**:
- Two Pointers: Expand from each potential peak
- Dynamic Programming: Separate arrays for up and down counts

## ⚡ Complexity Analysis
- **Time Complexity**: O(n) - one or two passes through array
- **Space Complexity**: O(1) with optimization, O(n) for DP approach

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
A mountain must have both increasing and decreasing parts

Equal adjacent elements break the monotonic sequence

Need to check both sides of each potential peak

Can optimize space by counting on the fly

🔗 Related Problems
Peak Index in a Mountain Array

Valid Mountain Array

Find in Mountain Array

Minimum Number of Removals to Make Mountain Array

Map of Highest Peak
