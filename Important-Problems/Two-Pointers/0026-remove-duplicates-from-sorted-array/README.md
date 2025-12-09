# 26. Remove Duplicates from Sorted Array

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)

Given an integer array `nums` sorted in **non-decreasing order**, remove the duplicates **in-place** such that each unique element appears only **once**. The **relative order** of the elements should be kept the **same**.

Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the **first part** of the array `nums`. More formally, if there are `k` elements after removing the duplicates, then the first `k` elements of `nums` should hold the final result. It does not matter what you leave beyond the first `k` elements.

Return `k` after placing the final result in the first `k` slots of `nums`.

**Do not** allocate extra space for another array. You must do this by **modifying the input array in-place** with O(1) extra memory.

**Example 1:**

Input: nums = [1,1,2]

Output: 2, nums = [1,2,_]

Explanation: Your function should return k = 2, with the first two elements of nums being 1 and 2 respectively.

It does not matter what you leave beyond the returned k (hence they are underscores).


**Example 2:**

Input: nums = [0,0,1,1,1,2,2,3,3,4]

Output: 5, nums = [0,1,2,3,4,,,,,_]

Explanation: Your function should return k = 5, with the first five elements of nums being 0, 1, 2, 3, and 4 respectively.

It does not matter what you leave beyond the returned k (hence they are underscores).


**Constraints:**
- `1 <= nums.length <= 3 * 10^4`
- `-100 <= nums[i] <= 100`
- `nums` is sorted in non-decreasing order.

## 🧠 Thought Process

### Initial Thoughts
- Need to remove duplicates in-place from a sorted array
- Must maintain relative order of elements
- Multiple approaches with same time complexity but different implementations
- Need to return the count of unique elements

### Key Insights
1. **Two Pointers Technique**: Most efficient approach - slow and fast pointers
2. **The key insight**: Since array is sorted, duplicates are adjacent
3. **In-place Modification**: Use slow pointer to track position for next unique element
4. **Fast Pointer**: Iterates through array to find next unique element

### Approach Selection
**Chosen Approach:** Two Pointers (Slow and Fast)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- In-place modification
- Simple and intuitive

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The slow pointer tracks the position for the next unique element

The fast pointer finds the next unique element by skipping duplicates

Since the array is sorted, duplicates are adjacent, making the algorithm efficient

The solution modifies the array in-place without extra space

🔗 Related Problems
Remove Duplicates from Sorted Array II

Remove Element

Remove Duplicates from Sorted List

Move Zeroes

Duplicate Zeros
