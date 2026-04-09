# 31. Next Permutation

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/next-permutation/)

Implement **next permutation**, which rearranges numbers into the lexicographically next greater permutation of numbers.

If such arrangement is not possible, it must rearrange it as the lowest possible order (i.e., sorted in ascending order).

The replacement must be **[in-place](http://en.wikipedia.org/wiki/In-place_algorithm)** and use only constant extra memory.

**Example 1:**

Input: nums = [1,2,3]
Output: [1,3,2]


**Example 2:**

Input: nums = [3,2,1]
Output: [1,2,3]


**Example 3:**

Input: nums = [1,1,5]
Output: [1,5,1]


**Constraints:**
- `1 <= nums.length <= 100`
- `0 <= nums[i] <= 100`

## 🧠 Thought Process

### Problem Understanding
We need to find the next lexicographically greater permutation of the given array. If no greater permutation exists, return the smallest permutation (sorted ascending).

### Key Insights
1. **Lexicographic Order**: Like dictionary order for numbers
2. **Pattern**: Find the rightmost peak, then swap with next greater element, then reverse suffix
3. **Algorithm Steps**:
   - Find the largest index `i` such that `nums[i] < nums[i+1]` (the "pivot")
   - If no such index exists, reverse the whole array (last permutation)
   - Find the largest index `j > i` such that `nums[j] > nums[i]`
   - Swap `nums[i]` and `nums[j]`
   - Reverse the suffix starting from `i+1`

### Approach Selection
**Chosen Approach:** Single-pass with Two Pointers  
**Why this approach?**
- O(n) time complexity
- O(1) space complexity
- In-place modification

**Alternative Approaches:**
- **Brute Force**: Generate all permutations (O(n!)) - too slow
- **Next Permutation Library**: Not allowed in implementation

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of array
- **Space Complexity:** O(1) extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The algorithm finds the next lexicographically greater permutation

If the array is in descending order, it's the last permutation → wrap around to ascending

Works with duplicate numbers

In-place modification with constant extra space

🔗 Related Problems
Permutations
Permutations II
Permutation Sequence
Next Greater Element III
Previous Permutation With One Swap
