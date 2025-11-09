# 15. 3Sum

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Sorting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/3sum/)

Given an integer array `nums`, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

Notice that the solution set must **not contain duplicate triplets**.

**Example 1:**

Input: nums = [-1,0,1,2,-1,-4]

Output: [[-1,-1,2],[-1,0,1]]

Explanation:

nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.

nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.

nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.

The distinct triplets are [-1,0,1] and [-1,-1,2].

Notice that the order of the output and the order of the triplets does not matter.


**Example 2:**

Input: nums = [0,1,1]

Output: []

Explanation: The only possible triplet does not sum up to 0.


**Example 3:**

Input: nums = [0,0,0]

Output: [[0,0,0]]

Explanation: The only possible triplet sums to 0.


**Constraints:**
- `3 <= nums.length <= 3000`
- `-10^5 <= nums[i] <= 10^5`

## 🧠 Thought Process

### Initial Thoughts
- Need to find all unique triplets that sum to zero
- Must avoid duplicate triplets in the result
- Array can have negative, positive, and zero values
- Multiple approaches with different time complexities

### Key Insights
1. **Sorting + Two Pointers**: Most efficient approach - O(n²) time
2. **HashSet for Duplicates**: Handle duplicate triplets efficiently
3. **Fixed First Element**: For each element, solve two sum problem for the remaining
4. **Early Termination**: Optimize by skipping impossible cases

### Approach Selection
**Chosen Approach:** Sorting + Two Pointers  
**Why this approach?** 
- O(n²) time complexity (optimal for this problem)
- O(1) or O(n) space depending on sorting
- Handles duplicates efficiently
- Most efficient and scalable

## ⚡ Complexity Analysis
- **Time Complexity:** O(n²) - Sorting O(n log n) + nested loops O(n²)
- **Space Complexity:** O(1) or O(n) depending on sorting algorithm

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort the array first to enable two pointers and skip duplicates

For each element, solve two sum problem for the remaining array

Skip duplicate elements to avoid duplicate triplets

Use early termination when current element > 0 (since array is sorted)

🔗 Related Problems
Two Sum

3Sum Closest

4Sum

3Sum Smaller

Two Sum II - Input Array Is Sorted
