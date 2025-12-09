# 11. Container With Most Water

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Greedy  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/container-with-most-water/)

You are given an integer array `height` of length `n`. There are `n` vertical lines drawn such that the two endpoints of the `i-th` line are `(i, 0)` and `(i, height[i])`.

Find two lines that together with the x-axis form a container, such that the container contains the most water.

Return the maximum amount of water a container can store.

**Notice** that you may not slant the container.

**Example 1:**

Input: height = [1,8,6,2,5,4,8,3,7]

Output: 49

Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7].

In this case, the max area of water (blue section) the container can contain is 49.


**Example 2:**

Input: height = [1,1]

Output: 1


**Constraints:**
- `n == height.length`
- `2 <= n <= 10^5`
- `0 <= height[i] <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to find two lines that form the largest container
- Area = min(height[i], height[j]) * (j - i)
- Multiple approaches with different time complexities
- Must handle large input sizes efficiently

### Key Insights
1. **Two Pointers Technique**: Most efficient approach - O(n) time
2. **Greedy Strategy**: Move pointer with smaller height inward
3. **Brute Force**: Check all pairs - O(n²) time (too slow)
4. **The key insight**: Always move the shorter pointer to potentially find taller lines

### Approach Selection
**Chosen Approach:** Two Pointers (Greedy)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Optimal for large input constraints
- Elegant greedy strategy

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation


📝 Notes
Start with pointers at both ends of the array

Calculate area and update max area

Move the pointer with smaller height inward

Continue until pointers meet

The greedy approach works because moving the shorter pointer might find taller lines

🔗 Related Problems
Trapping Rain Water

Largest Rectangle in Histogram

Trapping Rain Water II

3Sum

Two Sum II - Input Array Is Sorted
