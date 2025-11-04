# 347. Top K Frequent Elements

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, Heap, Sorting, Bucket Sort, Counting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/top-k-frequent-elements/)

Given an integer array `nums` and an integer `k`, return the `k` most frequent elements. You may return the answer in **any order**.

**Example 1:**

Input: nums = [1,1,1,2,2,3], k = 2

Output: [1,2]


**Example 2:**

Input: nums = [1], k = 1

Output: [1]


**Constraints:**
- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`
- `k` is in the range `[1, the number of unique elements in the array]`.
- It is **guaranteed** that the answer is **unique**.

**Follow-up:** Your algorithm's time complexity must be better than `O(n log n)`, where n is the array's size.

## 🧠 Thought Process

### Initial Thoughts
- Need to find k most frequent elements in an array
- Multiple approaches with different time/space trade-offs
- Must handle large input size (up to 10^5 elements)

### Key Insights
1. **HashMap + Min-Heap**: Count frequencies, then use heap to get top k - O(n log k) time
2. **HashMap + Bucket Sort**: Count frequencies, then use buckets by frequency - O(n) time
3. **HashMap + QuickSelect**: Count frequencies, then use QuickSelect for top k - O(n) average time
4. **HashMap + Sorting**: Simple but O(n log n) time, doesn't meet follow-up

### Approach Selection
**Chosen Approach:** HashMap + Bucket Sort  
**Why this approach?** 
- Optimal O(n) time complexity
- O(n) space complexity
- Meets the follow-up requirement
- Elegant and efficient for this problem

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Two passes through the array
- **Space Complexity:** O(n) - For frequency map and buckets

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Bucket sort works well because frequencies are bounded by array length

The bucket index represents frequency, values in bucket are numbers with that frequency

We iterate from highest frequency bucket to get top k elements

The guarantee that answer is unique simplifies the problem

🔗 Related Problems
215. Kth Largest Element in an Array

692. Top K Frequent Words

451. Sort Characters By Frequency

973. K Closest Points to Origin

