# 912. Sort an Array

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Divide and Conquer, Sorting, Heap, Merge Sort, Quick Sort  
**Companies:** Amazon, Google, Microsoft, Apple, Facebook, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/sort-an-array/)

Given an array of integers `nums`, sort the array in ascending order and return it.

You must solve the problem without using any built-in functions in `O(n log(n))` time complexity and with the smallest space complexity possible.

**Example 1:**

Input: nums = [5,2,3,1]

Output: [1,2,3,5]

Explanation: After sorting the array, the positions of some numbers are not changed (for example, 2 and 3), while the positions of other numbers are changed (for example, 1 and 5).


**Example 2:**

Input: nums = [5,1,1,2,0,0]

Output: [0,0,1,1,2,5]

Explanation: Note that the values of nums are not necessarily unique.


**Constraints:**
- `1 <= nums.length <= 5 * 10^4`
- `-5 * 10^4 <= nums[i] <= 5 * 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to implement sorting algorithms from scratch
- Must achieve O(n log n) time complexity
- Should minimize space complexity
- Multiple sorting algorithms meet these requirements

### Key Insights
1. **Merge Sort**: Stable, O(n log n) time, O(n) space
2. **Quick Sort**: O(n log n) average, O(n²) worst case, O(log n) space
3. **Heap Sort**: O(n log n) time, O(1) space but not stable
4. **Counting Sort**: O(n + k) time but requires known range
5. **Tim Sort**: Hybrid algorithm used in Python/Java

### Approach Selection
**Chosen Approach:** Merge Sort  
**Why this approach?** 
- Guaranteed O(n log n) time complexity
- Stable sorting algorithm
- Reasonable O(n) space complexity
- Good for linked lists and external sorting
- Demonstrates divide and conquer principles

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Consistent performance
- **Space Complexity:** O(n) - Temporary array for merging

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Merge Sort is preferred for its consistent O(n log n) performance

Quick Sort can be faster in practice but has worst-case O(n²)

Heap Sort has O(1) space but is not stable

For this problem constraints, all O(n log n) algorithms are acceptable

The choice depends on stability requirements and space constraints

🔗 Related Problems
88. Merge Sorted Array

148. Sort List

75. Sort Colors

215. Kth Largest Element in an Array
