# 217. Contains Duplicate

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Sorting  
**Companies:** Amazon, Google, Microsoft, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/contains-duplicate/)

Given an integer array `nums`, return `true` if any value appears at least twice in the array, and return `false` if every element is distinct.

**Example 1:**

Input: nums = [1,2,3,1]

Output: true

Explanation: The value 1 appears at index 0 and index 3.


**Example 2:**

Input: nums = [1,2,3,4]

Output: false

Explanation: All values are distinct.


**Example 3:**

Input: nums = [1,1,1,3,3,4,3,2,4,2]

Output: true


**Constraints:**
- `1 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- Need to check if any element appears more than once
- Brute force: Compare every element with every other element (O(n²) time)
- Need a more efficient solution for large inputs (up to 10^5 elements)

### Key Insights
1. **Hash Set Approach**: Use a HashSet to track seen elements. If we encounter an element already in the set, we found a duplicate.
2. **Sorting Approach**: Sort the array first, then check adjacent elements. If any two adjacent elements are equal, we found a duplicate.
3. **Trade-offs**: HashSet uses O(n) extra space but O(n) time. Sorting uses O(1) extra space (if in-place) but O(n log n) time.

### Approach Selection
**Chosen Approach:** HashSet  
**Why this approach?** 
- Optimal time complexity O(n) for large inputs
- Simple and intuitive implementation
- Handles all edge cases including negative numbers

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(n) - HashSet stores up to n elements in worst case

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The HashSet approach is optimal for time complexity

For memory-constrained environments, sorting approach might be preferred

The problem guarantees at least 1 element, so no need for empty array check

Large input constraints (10^5 elements) make O(n²) solutions impractical

🔗 Related Problems
219. Contains Duplicate II

220. Contains Duplicate III

268. Missing Number

448. Find All Numbers Disappeared in an Array
