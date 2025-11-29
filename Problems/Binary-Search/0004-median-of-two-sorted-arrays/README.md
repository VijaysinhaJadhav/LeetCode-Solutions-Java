# 4. Median of Two Sorted Arrays

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Binary Search, Divide and Conquer  
**Companies:** Google, Microsoft, Amazon, Facebook, Apple, Bloomberg, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/median-of-two-sorted-arrays/)

Given two sorted arrays `nums1` and `nums2` of size `m` and `n` respectively, return **the median** of the two sorted arrays.

The overall run time complexity should be `O(log (m+n))`.

**Example 1:**

Input: nums1 = [1,3], nums2 = [2]

Output: 2.00000

Explanation: merged array = [1,2,3] and median is 2.


**Example 2:**

Input: nums1 = [1,2], nums2 = [3,4]

Output: 2.50000

Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.


**Constraints:**
- `nums1.length == m`
- `nums2.length == n`
- `0 <= m <= 1000`
- `0 <= n <= 1000`
- `1 <= m + n <= 2000`
- `-10^6 <= nums1[i], nums2[i] <= 10^6`

## 🧠 Thought Process

### Initial Thoughts
- We need to find median of two sorted arrays without merging them
- Brute force: merge arrays and find median → O(m+n) time, O(m+n) space
- But we need O(log(m+n)) time complexity
- This suggests binary search approach

### Key Insights
1. **Median Definition**: 
   - For odd total length: middle element
   - For even total length: average of two middle elements

2. **Binary Search Insight**:
   - We can partition both arrays such that left half contains smaller elements
   - The partition should satisfy: all elements in left <= all elements in right
   - We only need to find correct partition point using binary search

3. **Partition Strategy**:
   - Search in smaller array for correct partition
   - Ensure left partition size = right partition size (or left has one more)
   - Check boundary conditions for valid partition

### Approach Selection
**Chosen Approach:** Binary Search on Smaller Array  
**Why this approach?** 
- O(log(min(m,n))) time complexity
- O(1) space complexity
- Meets the required time complexity
- Elegant and efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(log(min(m,n))) - Binary search on smaller array
- **Space Complexity:** O(1) - Only using constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to find partition points that divide both arrays into left and right halves

All elements in left halves should be <= all elements in right halves

We binary search on the smaller array to find the correct partition

Handle edge cases carefully (empty arrays, single element arrays)

🔗 Related Problems
Find Median from Data Stream

Sliding Window Median

Kth Smallest Element in a Sorted Matrix
