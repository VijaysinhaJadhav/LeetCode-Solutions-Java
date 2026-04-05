# 457. Circular Array Loop

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Two Pointers, Graph  
**Companies:** Google, Amazon, Microsoft, Apple, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/circular-array-loop/)

You are playing a game involving a **circular array** of non-zero integers `nums`. Each `nums[i]` denotes the number of indices forward/backward you must move if you are at index `i`:
- If `nums[i]` is positive, move `nums[i]` steps **forward**.
- If `nums[i]` is negative, move `nums[i]` steps **backward**.

Since the array is **circular**, moving forward from the last element takes you to the first element, and moving backward from the first element takes you to the last element.

A **cycle** in the array is a sequence of indices `i1, i2, ..., ik` where:
1. All indices are **unique**.
2. The movement direction (forward/backward) is the **same** for all elements in the cycle.
3. The cycle length is **at least 2**.

Return `true` if there is a **cycle** in the array, or `false` otherwise.

**Example 1:**

Input: nums = [2,-1,1,2,2]
Output: true
Explanation: There is a cycle from index 0 -> 2 -> 3 -> 0.


**Example 2:**

Input: nums = [-1,2]
Output: false
Explanation: The movement from index 1 -> 1 forms a cycle of length 1, but a cycle must have length at least 2.


**Example 3:**

Input: nums = [-2,1,-1,-2,-2]
Output: false
Explanation: No cycle of length >= 2 exists.


**Constraints:**
- `1 <= nums.length <= 5000`
- `-1000 <= nums[i] <= 1000`
- `nums[i] != 0`

## 🧠 Thought Process

### Problem Understanding
We need to detect a cycle in a functional graph where each node points to `(i + nums[i]) mod n`. The cycle must:
- Have length ≥ 2
- Have all nodes moving in the same direction (all positive or all negative steps)

### Key Insights
1. **Graph Interpretation**: Each index points to the next index based on `nums[i]`
2. **Direction Consistency**: A valid cycle cannot mix positive and negative moves
3. **Cycle Detection**: Use visited markers to avoid revisiting nodes
4. **Path Tracking**: When exploring a path, mark nodes along the way to detect cycles

### Approach Selection
**Chosen Approach:** DFS with Visited States  
**Why this approach?**
- O(n) time complexity
- O(n) space for visited array
- Clear state tracking: unvisited, visiting, visited

**Alternative Approaches:**
- **Floyd's Cycle Detection**: Two pointers, O(n) time, O(1) space
- **Graph Coloring**: 0=unvisited, 1=visiting, 2=visited

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n = length of array
- **Space Complexity:** O(n) for visited array, O(1) for Floyd's algorithm

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use modulo operation for circular movement

Check direction consistency: all steps must be same sign

Cycle length must be ≥ 2 (single-element self-loop is invalid)

Mark nodes as visited to avoid re-processing

🔗 Related Problems
Happy Number (Floyd's cycle detection)
Linked List Cycle
Linked List Cycle II
Find the Duplicate Number
Array Nesting
