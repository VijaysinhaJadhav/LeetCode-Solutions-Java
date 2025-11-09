# 219. Contains Duplicate II

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Sliding Window  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/contains-duplicate-ii/)

Given an integer array `nums` and an integer `k`, return `true` if there are two distinct indices `i` and `j` in the array such that:

- `nums[i] == nums[j]`
- `abs(i - j) <= k`

**Example 1:**

Input: nums = [1,2,3,1], k = 3

Output: true

Explanation: nums[0] = 1 and nums[3] = 1, abs(0-3) = 3 <= 3


**Example 2:**

Input: nums = [1,0,1,1], k = 1

Output: true

Explanation: nums[2] = 1 and nums[3] = 1, abs(2-3) = 1 <= 1


**Example 3:**

Input: nums = [1,2,3,1,2,3], k = 2

Output: false

Explanation: All duplicates have distance > 2


**Constraints:**
- `1 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`
- `0 <= k <= 10^5`

## 🧠 Thought Process

### Initial Thoughts
- Need to find duplicate elements within distance k
- Multiple approaches with different time/space trade-offs
- Must handle large input sizes efficiently

### Key Insights
1. **HashMap Approach**: Most efficient - O(n) time, O(n) space
2. **Sliding Window**: Alternative approach using HashSet
3. **Brute Force**: Check all pairs - O(n*k) time (too slow)
4. **The key insight**: Store recent indices in HashMap for O(1) lookups

### Approach Selection
**Chosen Approach:** HashMap (Index Tracking)  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Simple and efficient
- Handles all edge cases

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(n) - HashMap storage

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use HashMap to store number and its most recent index

For each number, check if it exists in HashMap and if index difference <= k

Update HashMap with current index

The algorithm ensures we always have the most recent index

🔗 Related Problems
Contains Duplicate

Contains Duplicate III

Two Sum

Intersection of Two Arrays

Intersection of Two Arrays II
