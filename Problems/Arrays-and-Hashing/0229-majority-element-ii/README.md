# 229. Majority Element II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Sorting, Counting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/majority-element-ii/)

Given an integer array of size `n`, find all elements that appear more than `⌊ n/3 ⌋` times.

**Example 1:**

Input: nums = [3,2,3]

Output: [3]


**Example 2:**

Input: nums = [1]

Output: [1]


**Example 3:**

Input: nums = [1,2]

Output: [1,2]



**Constraints:**
- `1 <= nums.length <= 5 * 10^4`
- `-10^9 <= nums[i] <= 10^9`

**Follow-up:** Could you solve the problem in linear time and in O(1) space?

## 🧠 Thought Process

### Initial Thoughts
- Need to find elements that appear more than n/3 times
- At most 2 such elements can exist (since 2*(n/3 + 1) > n)
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Boyer-Moore Voting Algorithm**: Extended version for n/3 case
2. **HashMap Counting**: Simple but O(n) space
3. **Sorting and Counting**: O(n log n) time, O(1) space
4. **The key insight**: Use two candidates and counters, similar to original Boyer-Moore

### Approach Selection
**Chosen Approach:** Boyer-Moore Voting Algorithm (Extended)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Meets follow-up requirement
- Elegant extension of the classic algorithm

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Two passes through the array
- **Space Complexity:** O(1) - Only constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The Boyer-Moore algorithm can be extended for n/k case by maintaining k-1 candidates

We need a second pass to verify the candidates actually appear more than n/3 times

At most 2 elements can satisfy the condition, so we track two candidates

The algorithm works by canceling out groups of three distinct elements

🔗 Related Problems
169. Majority Element

1150. Check If a Number Is Majority Element in a Sorted Array

1157. Online Majority Element In Subarray


