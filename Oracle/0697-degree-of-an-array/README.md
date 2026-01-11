# 697. Degree of an Array

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table  
**Companies:** Amazon, Google, Microsoft, Adobe, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/degree-of-an-array/)

Given a non-empty array of non-negative integers `nums`, the **degree** of this array is defined as the maximum frequency of any one of its elements.

Your task is to find the smallest possible length of a (contiguous) subarray of `nums`, that has the same degree as `nums`.

**Example 1:**

Input: nums = [1,2,2,3,1]
Output: 2
Explanation:
The input array has a degree of 2 because both elements 1 and 2 appear twice.
Of the subarrays that have the same degree:
[1, 2, 2, 3, 1] has length 5.
[1, 2, 2, 3] has length 4.
[2, 2, 3, 1] has length 4.
[1, 2, 2] has length 3.
[2, 2, 3] has length 3.
[2, 2] has length 2.
So the smallest length is 2.


**Example 2:**

Input: nums = [1,2,2,3,1,4,2]
Output: 6
Explanation:
The degree is 3 because the element 2 is repeated three times.
So [2,2,3,1,4,2] is the shortest subarray, therefore return 6.


**Constraints:**
- `nums.length` will be between 1 and 50,000.
- `nums[i]` will be an integer between 0 and 49,999.

## 🧠 Thought Process

### Problem Understanding
We need to find:
1. The degree of the array (maximum frequency of any element)
2. The minimum length of a contiguous subarray that contains all occurrences of at least one element that achieves this maximum frequency

### Key Insights
1. **Degree Calculation**: The degree is simply the maximum frequency of any element in the array
2. **Subarray Requirement**: To have the same degree, a subarray must contain all occurrences of some element that has the maximum frequency
3. **Minimum Length**: For each element with maximum frequency, the shortest subarray containing all its occurrences is from its first to last occurrence
4. **Multiple Elements**: Multiple elements can have the same maximum frequency; we need the minimum length among all such elements

### Approach Selection
**Chosen Approach**: One-pass with Hash Maps  
**Why this approach?**
- O(n) time complexity
- O(n) space complexity
- Single pass through the array
- Tracks first occurrence, last occurrence, and frequency

**Alternative Approaches**:
- Two-pass: First calculate frequencies, then find min subarray length
- Using arrays instead of maps for fixed range (0-49,999)

## ⚡ Complexity Analysis
- **Time Complexity**: O(n) where n is length of nums
- **Space Complexity**: O(n) for storing element information

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to track three pieces of information for each number: frequency, first occurrence, last occurrence

For elements with maximum frequency, calculate subarray length as lastOccurrence - firstOccurrence + 1

Multiple elements can share the maximum frequency, so keep track of the minimum such length

🔗 Related Problems
Majority Element

Contains Duplicate II

Top K Frequent Elements

Find All Anagrams in a String

Subarray Sum Equals K
