# 167. Two Sum II - Input Array Is Sorted

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Binary Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)

Given a **1-indexed** array of integers `numbers` that is already **sorted in non-decreasing order**, find two numbers such that they add up to a specific `target` number. Let these two numbers be `numbers[index1]` and `numbers[index2]` where `1 <= index1 < index2 <= numbers.length`.

Return the indices of the two numbers, `index1` and `index2`, **added by one** as an integer array `[index1, index2]` of length 2.

Your solution must use only constant extra space.

**Example 1:**

Input: numbers = [2,7,11,15], target = 9

Output: [1,2]

Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].


**Example 2:**

Input: numbers = [2,3,4], target = 6

Output: [1,3]

Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].


**Example 3:**

Input: numbers = [-1,0], target = -1

Output: [1,2]

Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].


**Constraints:**
- `2 <= numbers.length <= 3 * 10^4`
- `-1000 <= numbers[i] <= 1000`
- `numbers` is sorted in non-decreasing order.
- `-1000 <= target <= 1000`
- The tests are generated such that there is **exactly one solution**.

## 🧠 Thought Process

### Initial Thoughts
- Array is sorted in non-decreasing order (ascending)
- Need to find two numbers that sum to target
- Must use constant extra space (O(1))
- Return 1-based indices
- Exactly one solution exists

### Key Insights
1. **Two Pointers Technique**: Most efficient - O(n) time, O(1) space
2. **Binary Search Approach**: O(n log n) time but educational
3. **HashMap Alternative**: O(n) time but O(n) space (violates constraints)
4. **The key advantage**: Sorted property allows efficient two pointers solution

### Approach Selection
**Chosen Approach:** Two Pointers  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Leverages sorted array property
- Most efficient and elegant

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The two pointers approach starts from both ends of the array

If sum is too small, move left pointer right

If sum is too large, move right pointer left

The algorithm terminates when the target sum is found

Return indices are 1-based (add 1 to 0-based indices)

🔗 Related Problems
Two Sum

3Sum

3Sum Closest

4Sum

Two Sum IV - Input is a BST

Two Sum Less Than K
