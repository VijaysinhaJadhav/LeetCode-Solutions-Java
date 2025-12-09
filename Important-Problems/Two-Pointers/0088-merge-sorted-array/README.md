# 88. Merge Sorted Array

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Two Pointers, Sorting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/merge-sorted-array/)

You are given two integer arrays `nums1` and `nums2`, sorted in **non-decreasing order**, and two integers `m` and `n`, representing the number of elements in `nums1` and `nums2` respectively.

Merge `nums1` and `nums2` into a single array sorted in **non-decreasing order**.

The final sorted array should not be returned by the function, but instead be stored inside the array `nums1`. To accommodate this, `nums1` has a length of `m + n`, where the first `m` elements denote the elements that should be merged, and the last `n` elements are set to `0` and should be ignored. `nums2` has a length of `n`.

**Example 1:**

Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3

Output: [1,2,2,3,5,6]

Explanation: The arrays we are merging are [1,2,3] and [2,5,6].

The result of the merge is [1,2,2,3,5,6].


**Example 2:**

Input: nums1 = [1], m = 1, nums2 = [], n = 0

Output: [1]

Explanation: The arrays we are merging are [1] and [].

The result of the merge is [1].


**Example 3:**

Input: nums1 = [0], m = 0, nums2 = [1], n = 1

Output: [1]

Explanation: The arrays we are merging are [] and [1].

The result of the merge is [1].


**Constraints:**
- `nums1.length == m + n`
- `nums2.length == n`
- `0 <= m, n <= 200`
- `1 <= m + n <= 200`
- `-10^9 <= nums1[i], nums2[j] <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- Need to merge two sorted arrays into nums1 in-place
- nums1 has extra space at the end to accommodate nums2 elements
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Three Pointers from End**: Most efficient approach - O(m + n) time, O(1) space
2. **Merge and Sort**: Simple but O((m + n) log(m + n)) time
3. **Two Pointers from Start**: Requires O(m) extra space
4. **The key insight**: Work backwards from the end to avoid overwriting elements

### Approach Selection
**Chosen Approach:** Three Pointers from End  
**Why this approach?** 
- O(m + n) time complexity
- O(1) space complexity
- In-place modification without extra space
- Most efficient and elegant solution

## ⚡ Complexity Analysis
- **Time Complexity:** O(m + n) - Single pass through both arrays
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The algorithm uses three pointers: one for nums1's real elements, one for nums2, and one for the end of nums1

By working backwards, we avoid overwriting elements in nums1 that haven't been processed yet

The merge happens in-place without requiring extra space

🔗 Related Problems
Merge Two Sorted Lists

Merge k Sorted Lists

Squares of a Sorted Array

Interval List Intersections

Intersection of Two Arrays


