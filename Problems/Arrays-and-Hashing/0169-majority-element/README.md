# 169. Majority Element

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Divide and Conquer, Sorting, Counting  
**Companies:** Amazon, Google, Microsoft, Apple, Facebook, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/majority-element/)

Given an array `nums` of size `n`, return the majority element.

The majority element is the element that appears more than `⌊n / 2⌋` times. You may assume that the majority element always exists in the array.

**Example 1:**

Input: nums = [3,2,3]

Output: 3


**Example 2:**

Input: nums = [2,2,1,1,1,2,2]

Output: 2


**Constraints:**
- `n == nums.length`
- `1 <= n <= 5 * 10^4`
- `-10^9 <= nums[i] <= 10^9`

**Follow-up:** Could you solve the problem in linear time and in O(1) space?

## 🧠 Thought Process

### Initial Thoughts
- The majority element appears more than n/2 times
- We need to find this element efficiently
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Boyer-Moore Voting Algorithm**: Optimal O(n) time, O(1) space solution
2. **HashMap Approach**: Count frequencies, O(n) time, O(n) space
3. **Sorting Approach**: Middle element after sorting is majority, O(n log n) time, O(1) space
4. **Divide and Conquer**: Recursively find majority in halves, O(n log n) time, O(log n) space

### Approach Selection
**Chosen Approach:** Boyer-Moore Voting Algorithm  
**Why this approach?** 
- Optimal O(n) time complexity
- O(1) space complexity
- Elegant and efficient
- Solves the follow-up challenge

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only using constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The Boyer-Moore algorithm works because the majority element appears more than n/2 times

The algorithm maintains a candidate and count, canceling out non-majority elements

Always verify the solution works given the constraint that majority element always exists

For cases without guaranteed majority, we'd need a second pass to verify

🔗 Related Problems
229. Majority Element II

1150. Check If a Number Is Majority Element in a Sorted Array

1157. Online Majority Element In Subarray
