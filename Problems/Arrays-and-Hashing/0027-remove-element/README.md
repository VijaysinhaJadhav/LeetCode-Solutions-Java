# 27. Remove Element

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/remove-element/)

Given an integer array `nums` and an integer `val`, remove all occurrences of `val` in `nums` **in-place**. The order of the elements may be changed. Then return the number of elements in `nums` which are not equal to `val`.

Consider the number of elements in `nums` which are not equal to `val` be `k`, to get accepted, you need to do the following things:

- Change the array `nums` such that the first `k` elements of `nums` contain the elements which are not equal to `val`. The remaining elements of `nums` are not important as well as the size of `nums`.
- Return `k`.

**Custom Judge:**

The judge will test your solution with the following code:


int[] nums = [...]; // Input array

int val = ...; // Value to remove

int[] expectedNums = [...]; // The expected answer with correct length.

// It is sorted with no values equaling val.

int k = removeElement(nums, val); // Calls your implementation

assert k == expectedNums.length;

sort(nums, 0, k); // Sort the first k elements of nums

for (int i = 0; i < actualLength; i++) {

assert nums[i] == expectedNums[i];

}


**Example 1:**

Input: nums = [3,2,2,3], val = 3

Output: 2, nums = [2,2,,]

Explanation: Your function should return k = 2, with the first two elements of nums being 2.

It does not matter what you leave beyond the returned k (hence they are underscores).


**Example 2:**

Input: nums = [0,1,2,2,3,0,4,2], val = 2

Output: 5, nums = [0,1,4,0,3,,,_]

Explanation: Your function should return k = 5, with the first five elements of nums containing 0, 0, 1, 3, and 4.

Note that the five elements can be returned in any order.


**Constraints:**
- `0 <= nums.length <= 100`
- `0 <= nums[i] <= 50`
- `0 <= val <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Need to remove all occurrences of `val` from the array in-place
- The order doesn't matter for the remaining elements
- We need to return the count of elements not equal to `val`
- The array should be modified so first k elements are the valid ones

### Key Insights
1. **Two Pointers Approach**: Use one pointer to iterate and another to track position for valid elements
2. **In-place Modification**: We can overwrite elements to achieve O(1) space complexity
3. **Order Preservation**: If order matters, we need a different approach (but here it doesn't)
4. **Early Termination**: If no elements to remove, return early

### Approach Selection
**Chosen Approach:** Two Pointers (Copy Forward)  
**Why this approach?** 
- Simple and efficient O(n) time complexity
- O(1) space complexity
- Easy to understand and implement
- Preserves relative order of non-removed elements

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only using constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The two pointers approach is optimal for this problem

Since order doesn't matter, we can also use a swap-based approach

Always consider edge cases: empty array, all elements to remove, no elements to remove

The problem requires in-place modification, so we cannot use extra arrays

🔗 Related Problems
26. Remove Duplicates from Sorted Array

283. Move Zeroes

80. Remove Duplicates from Sorted Array II

203. Remove Linked List Elements
