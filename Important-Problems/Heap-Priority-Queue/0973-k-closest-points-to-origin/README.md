# 973. K Closest Points to Origin

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Math, Divide and Conquer, Geometry, Sorting, Heap (Priority Queue), Quickselect  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Uber

[LeetCode Link](https://leetcode.com/problems/k-closest-points-to-origin/)

Given an array of `points` where `points[i] = [xi, yi]` represents a point on the **X-Y** plane and an integer `k`, return the `k` **closest** points to the origin `(0, 0)`.

The distance between two points on the **X-Y** plane is the Euclidean distance (i.e., `√(x₁ - x₂)² + (y₁ - y₂)²)`).

You may return the answer in **any order**. The answer is **guaranteed** to be **unique** (except for the order that it is in).

**Example 1:**

Input: points = [[1,3],[-2,2]], k = 1

Output: [[-2,2]]

Explanation:

The distance between (1,3) and origin is √10.

The distance between (-2,2) and origin is √8.

Since √8 < √10, (-2,2) is closer to the origin.

We only want the closest k = 1 points, so the answer is just [[-2,2]].


**Example 2:**

Input: points = [[3,3],[5,-1],[-2,4]], k = 2

Output: [[3,3],[-2,4]]

Explanation: The answer [[-2,4],[3,3]] would also be accepted.


**Constraints:**
- `1 <= k <= points.length <= 10^4`
- `-10^4 <= xi, yi <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Need to find k points with smallest distance to origin (0,0)
- Multiple approaches with different time/space trade-offs
- Can avoid computing actual square root by comparing squared distances
- Key decision: whether to sort all points or use more efficient selection

### Key Insights
1. **Distance Calculation**: Use squared distance to avoid expensive sqrt operation
2. **Max-Heap Approach**: Maintain heap of size k with largest distances at top
3. **QuickSelect**: O(n) average time by partially sorting around kth element
4. **Sorting**: Simple but O(n log n) time

### Approach Selection
**Chosen Approach:** Max-Heap (Priority Queue)  
**Why this approach?** 
- O(n log k) time complexity - efficient when k is small
- O(k) space complexity - only store k elements
- Simple implementation
- Well-suited for large n, small k scenarios

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log k) - Process n points with heap operations O(log k)
- **Space Complexity:** O(k) - Only store k points in heap

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use squared distance to avoid expensive sqrt calculations

Max-heap keeps k smallest elements by removing largest when size exceeds k

Can also use quickselect for O(n) average time

For small k, heap approach is very efficient

🔗 Related Problems
Kth Largest Element in an Array

Top K Frequent Elements

Kth Largest Element in a Stream

Last Stone Weight

Find K Closest Elements
