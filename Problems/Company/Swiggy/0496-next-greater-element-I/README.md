# 496. Next Greater Element I

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Stack, Monotonic Stack  
**Companies:** Amazon, Google, Microsoft, Meta, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/next-greater-element-i/)

The **next greater element** of some element `x` in an array is the **first greater** element that is **to the right** of `x` in the same array.

You are given two **distinct 0-indexed** integer arrays `nums1` and `nums2`, where `nums1` is a subset of `nums2`.

For each `0 <= i < nums1.length`, find the index `j` such that `nums1[i] == nums2[j]` and determine the **next greater element** of `nums2[j]` in `nums2`. If there is no next greater element, then the answer for this query is `-1`.

Return *an array* `ans` *of length* `nums1.length` *such that* `ans[i]` *is the **next greater element** as described above.*

**Example 1:**

Input: nums1 = [4,1,2], nums2 = [1,3,4,2]

Output: [-1,3,-1]

Explanation:

For 4: nums2 = [1,3,4,2] → no next greater element → -1

For 1: nums2 = [1,3,4,2] → next greater is 3 → 3

For 2: nums2 = [1,3,4,2] → no next greater element → -1


**Example 2:**

Input: nums1 = [2,4], nums2 = [1,2,3,4]

Output: [3,-1]

Explanation:

For 2: nums2 = [1,2,3,4] → next greater is 3 → 3

For 4: nums2 = [1,2,3,4] → no next greater element → -1


**Constraints:**
- `1 <= nums1.length <= nums2.length <= 1000`
- `0 <= nums1[i], nums2[i] <= 10^4`
- All integers in `nums1` and `nums2` are **unique**.
- All the integers of `nums1` also appear in `nums2`.

## 🧠 Thought Process

### Initial Thoughts
- Need to find the next greater element in nums2 for each element in nums1
- nums1 is a subset of nums2
- Need efficient lookup and next greater element finding

### Key Insights
1. **Brute Force Approach**: For each element in nums1, find it in nums2, then scan right to find next greater
   - Time: O(n*m) where n = nums1.length, m = nums2.length
   - Space: O(1)
   
2. **Hash Map + Stack (Monotonic Decreasing Stack)**:
   - Preprocess nums2 to find next greater for ALL elements
   - Store results in hash map for O(1) lookup
   - Use monotonic decreasing stack to efficiently find next greater elements
   
3. **Monotonic Stack Principle**:
   - Process nums2 from right to left
   - Maintain stack of elements in decreasing order
   - For each element, pop smaller elements from stack until finding larger element

### Approach Selection
**Chosen Approach:** Hash Map + Monotonic Stack  
**Why this approach?** 
- O(n + m) time complexity
- O(m) space complexity
- Efficient one-pass solution
- Handles all cases optimally

## ⚡ Complexity Analysis
- **Time Complexity:** O(n + m) - One pass through nums2 to build nextGreater map, then one pass through nums1 to build answer
- **Space Complexity:** O(m) - For the hash map storing next greater elements for all elements in nums2

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Monotonic decreasing stack helps find next greater element efficiently

Process from right to left for natural stack operations

Store results in hash map for O(1) lookup

Handle case when stack is empty (no greater element)

🔗 Related Problems
Next Greater Element II

Next Greater Element III

Daily Temperatures

Sum of Subarray Minimums

Next Greater Node In Linked List
