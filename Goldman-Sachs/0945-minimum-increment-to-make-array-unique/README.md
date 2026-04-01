# 945. Minimum Increment to Make Array Unique

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Greedy, Sorting, Counting  
**Companies:** Amazon, Google, Microsoft, Apple, Uber  

[LeetCode Link](https://leetcode.com/problems/minimum-increment-to-make-array-unique/)

You are given an integer array `nums`. In one move, you can pick an index `i` where `0 <= i < nums.length` and increment `nums[i]` by `1`.

Return *the minimum number of moves to make every value in `nums` **unique**.*

**Example 1:**

Input: nums = [1,2,2]
Output: 1
Explanation: After 1 move, the array could be [1, 2, 3].


**Example 2:**

Input: nums = [3,2,1,2,1,7]
Output: 6
Explanation: After 6 moves, the array could be [3, 4, 1, 2, 5, 7].
It can be shown that it is impossible with less than 6 moves.


**Constraints:**
- `1 <= nums.length <= 10^5`
- `0 <= nums[i] <= 10^5`

## 🧠 Thought Process

### Problem Understanding
We need to make all array elements unique by only incrementing values. Each increment counts as one move. We want the minimum total increments.

### Key Insights
1. **Greedy Approach**: Sort the array, then process elements in order
2. **Counting Approach**: Count frequencies and "fill gaps" using a counting array
3. **Increment Tracking**: If current element <= previous element, we need to increment it to `previous + 1`

### Approach Selection
**Chosen Approach:** Sort + Greedy  
**Why this approach?**
- O(n log n) time complexity
- O(1) extra space (excluding sorting)
- Intuitive and easy to implement

**Alternative Approaches:**
- **Counting Array**: O(n + max) time, O(max) space
- **Frequency Map + Sorting**: Similar to counting approach

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) for sorting, or O(n + max) for counting approach
- **Space Complexity:** O(1) for sorting approach, O(max) for counting approach

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sorting helps process elements in increasing order

Keep track of the next available unique number

When encountering a duplicate, increment to next available value

The counting approach is more efficient for large value ranges

🔗 Related Problems
Contains Duplicate II
Contains Duplicate
Remove Duplicates from Sorted Array
Remove Duplicates from Sorted Array II
Maximum Gap
