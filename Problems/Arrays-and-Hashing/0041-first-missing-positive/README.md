# 41. First Missing Positive

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Hash Table  
**Companies:** Amazon, Microsoft, Google, Facebook, Apple, Bloomberg, Adobe, Oracle

[LeetCode Link](https://leetcode.com/problems/first-missing-positive/)

Given an unsorted integer array `nums`, return the smallest missing positive integer.

You must implement an algorithm that runs in **O(n)** time and uses **O(1)** auxiliary space.

**Example 1:**

Input: nums = [1,2,0]

Output: 3

Explanation: The numbers in the range [1,2] are all in the array.


**Example 2:**

Input: nums = [3,4,-1,1]

Output: 2

Explanation: 1 is in the array but 2 is missing.


**Example 3:**

Input: nums = [7,8,9,11,12]

Output: 1

Explanation: The smallest positive integer 1 is missing.


**Constraints:**
- `1 <= nums.length <= 10^5`
- `-2^31 <= nums[i] <= 2^31 - 1`

## 🧠 Thought Process

### Initial Thoughts
- Need to find the smallest positive integer not in the array
- Challenge: O(n) time and O(1) space constraints
- Cannot use traditional sorting (O(n log n)) or hash sets (O(n) space)

### Key Insights
1. **Range of interest**: The answer must be in range [1, n+1] where n is array length
   - If all numbers 1..n are present, answer is n+1
   - Otherwise, answer is the smallest missing number in 1..n
2. **Cycle Sort Approach**: Place each number in its correct position if possible
3. **Index Mapping**: Use the array itself as a hash table by marking positions

### Approach Selection
**Chosen Approach:** Index Mapping with Array Reorganization  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- In-place modification meets space constraints
- Elegant use of the array itself as storage

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each element is processed at most twice
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key insight is that the answer must be between 1 and n+1

We can reorganize the array so that nums[i] = i+1 where possible

After reorganization, the first index where nums[i] != i+1 gives our answer

🔗 Related Problems
Missing Number

Find the Duplicate Number

Find All Numbers Disappeared in an Array

Find All Duplicates in an Array
