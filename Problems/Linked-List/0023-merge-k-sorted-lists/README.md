# 23. Merge k Sorted Lists

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Linked List, Divide and Conquer, Heap (Priority Queue), Merge Sort  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn, Oracle

[LeetCode Link](https://leetcode.com/problems/merge-k-sorted-lists/)

You are given an array of `k` linked-lists `lists`, each linked-list is sorted in ascending order.

Merge all the linked-lists into one sorted linked-list and return it.

**Example 1:**

Input: lists = [[1,4,5],[1,3,4],[2,6]]

Output: [1,1,2,3,4,4,5,6]

Explanation: The linked-lists are:

[

1->4->5,

1->3->4,

2->6

]

Merging them into one sorted list:

1->1->2->3->4->4->5->6


**Example 2:**

Input: lists = []

Output: []


**Example 3:**

Input: lists = [[]]

Output: []


**Constraints:**
- `k == lists.length`
- `0 <= k <= 10^4`
- `0 <= lists[i].length <= 500`
- `-10^4 <= lists[i][j] <= 10^4`
- Each `lists[i]` is sorted in ascending order.
- The sum of `lists[i].length` won't exceed `10^4`.

## 🧠 Thought Process

### Initial Thoughts
- Need to merge multiple sorted linked lists into one sorted list
- Multiple approaches with different time/space trade-offs
- The challenge is to efficiently merge k lists

### Key Insights
1. **Priority Queue Approach**: Use min-heap to always get the smallest element
2. **Divide and Conquer**: Merge lists in pairs (like merge sort)
3. **Sequential Merging**: Merge lists one by one (less efficient)
4. **All approaches need to handle empty lists and edge cases**

### Approach Selection
**Chosen Approach:** Priority Queue (Min-Heap)  
**Why this approach?** 
- O(n log k) time complexity - optimal
- O(k) space complexity for the heap
- Intuitive and efficient
- Handles the constraints well

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log k) where n is total number of nodes, k is number of lists
- **Space Complexity:** O(k) for the priority queue

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Priority Queue efficiently gets the smallest element in O(log k) time

Divide and conquer approach also achieves O(n log k) time complexity

Need to handle null lists and empty lists in the input

The solution uses a dummy node to simplify list construction

🔗 Related Problems
Merge Two Sorted Lists

Ugly Number II

Find K Pairs with Smallest Sums

Kth Smallest Element in a Sorted Matrix

Smallest Range Covering Elements from K Lists
