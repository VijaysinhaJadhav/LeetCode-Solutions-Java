# 47. Permutations II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Backtracking  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/permutations-ii/)

Given a collection of numbers, `nums`, that might contain duplicates, return all possible unique permutations in any order.

**Example 1:**

Input: nums = [1,1,2]

Output: [[1,1,2],[1,2,1],[2,1,1]]


**Example 2:**

Input: nums = [1,2,3]

Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]


**Constraints:**
- `1 <= nums.length <= 8`
- `-10 <= nums[i] <= 10`

## 🧠 Thought Process

### Initial Thoughts
- Need to generate all unique permutations of an array that may contain duplicates
- Standard permutation algorithms will generate duplicates when input has duplicate elements
- Need a way to skip duplicate permutations while generating results

### Key Insights
1. **Sorting**: Sort the array first to easily identify and skip duplicates
2. **Backtracking with Pruning**: Use backtracking with careful pruning to avoid duplicate permutations
3. **Used Tracking**: Keep track of which elements have been used in the current path
4. **Duplicate Skipping**: Skip duplicates at the same level of recursion to avoid duplicate permutations

### Approach Selection
**Chosen Approach:** Backtracking with Sorting and Pruning  
**Why this approach?** 
- Efficiently handles duplicates by skipping them at appropriate times
- Generates all unique permutations without storing and filtering duplicates
- O(n!) time complexity is optimal for permutation problems
- Clear and intuitive implementation

## ⚡ Complexity Analysis
- **Time Complexity:** O(n × n!) - There are n! permutations and each takes O(n) to generate
- **Space Complexity:** O(n) - For recursion stack and temporary storage (excluding output)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sorting the array first is crucial for duplicate detection

The key is to skip duplicates when they appear at the same recursion level

We use a boolean array to track which elements are currently in use

The pruning condition: skip if current element is same as previous AND previous wasn't used

🔗 Related Problems
Permutations

Next Permutation

Permutation Sequence

Combinations

Subsets

Subsets II
