# 881. Boats to Save People

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Greedy, Sorting  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/boats-to-save-people/)

You are given an array `people` where `people[i]` is the weight of the `i-th` person, and an infinite number of boats where each boat can carry a maximum weight of `limit`. Each boat carries at most two people at the same time, provided the sum of the weight of those people is at most `limit`.

Return the minimum number of boats to carry every given person.

**Example 1:**

Input: people = [1,2], limit = 3

Output: 1

Explanation: 1 boat (1, 2)


**Example 2:**

Input: people = [3,2,2,1], limit = 3

Output: 3

Explanation: 3 boats (1, 2), (2) and (3)


**Example 3:**

Input: people = [3,5,3,4], limit = 5

Output: 4

Explanation: 4 boats (3), (3), (4), (5)


**Constraints:**
- `1 <= people.length <= 5 * 10^4`
- `1 <= people[i] <= limit <= 3 * 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to minimize number of boats to carry all people
- Each boat can carry at most 2 people
- Boat weight capacity cannot exceed limit
- Multiple approaches with different time complexities

### Key Insights
1. **Sorting + Two Pointers**: Most efficient approach - O(n log n) time
2. **Greedy Strategy**: Pair heaviest with lightest if possible
3. **The key insight**: Sort people and try to pair lightest with heaviest
4. **If pairing not possible**: Heaviest person goes alone

### Approach Selection
**Chosen Approach:** Sorting + Two Pointers  
**Why this approach?** 
- O(n log n) time complexity (optimal for this problem)
- O(1) or O(n) space depending on sorting
- Greedy approach ensures minimum boats
- Most efficient and elegant

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Dominated by sorting
- **Space Complexity:** O(1) or O(n) depending on sorting algorithm

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort the people array in ascending order

Use two pointers: lightest and heaviest

Try to pair lightest with heaviest if sum <= limit

If not possible, heaviest goes alone

Count boats as we process people

🔗 Related Problems
Assign Cookies

Array Partition

Maximum Units on a Truck

Merge Nodes in Between Zeros
