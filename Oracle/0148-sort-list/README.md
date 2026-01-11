# 148. Sort List

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Linked List, Two Pointers, Divide and Conquer, Sorting, Merge Sort  
**Companies:** Amazon, Microsoft, Google, Facebook, Apple, Bloomberg, Adobe, Uber  

[LeetCode Link](https://leetcode.com/problems/sort-list/)

Given the `head` of a linked list, return *the list after sorting it in ascending order*.

**Example 1:**

Input: head = [4,2,1,3]
Output: [1,2,3,4]


**Example 2:**

Input: head = [-1,5,3,4,0]
Output: [-1,0,3,4,5]


**Example 3:**

Input: head = []
Output: []


**Constraints:**
- The number of nodes in the list is in the range `[0, 5 * 10^4]`.
- `-10^5 <= Node.val <= 10^5`

**Follow up:** Can you sort the linked list in `O(n log n)` time and `O(1)` memory (i.e., constant space)?

## 🧠 Thought Process

### Problem Understanding
We need to sort a singly linked list in ascending order. The constraints allow up to 50,000 nodes, so we need an efficient algorithm.

### Key Insights
1. **Time Complexity**: Need O(n log n) for large lists
2. **Space Complexity**: Follow-up asks for O(1) space (excluding recursion stack)
3. **Linked List Challenges**: 
   - No random access (can't use quicksort efficiently)
   - Need to handle pointers carefully
   - Merge sort is natural for linked lists
4. **Merge Sort Advantages**:
   - Stable O(n log n) time
   - Can be implemented with O(1) space for linked lists
   - Naturally works with linked structure

### Approach Selection
**Chosen Approach**: Bottom-up Merge Sort  
**Why this approach?**
- O(n log n) time complexity
- O(1) space complexity (iterative, not recursive)
- Avoids recursion stack overflow for large lists
- Follows the follow-up requirement

**Alternative Approaches**:
- Top-down recursive merge sort (O(log n) recursion stack space)
- Convert to array, sort, rebuild (O(n) space)

## ⚡ Complexity Analysis
- **Time Complexity**: O(n log n) for all approaches
- **Space Complexity**:
  - Recursive: O(log n) for recursion stack
  - Iterative: O(1) constant space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Merge sort is ideal for linked lists because:

Splitting is O(n) time (find middle with slow/fast pointers)

Merging is O(n) time and O(1) space

Total O(n log n) time

Bottom-up approach avoids recursion overhead

Handle edge cases: empty list, single node list

🔗 Related Problems
Merge Two Sorted Lists

Merge k Sorted Lists

Sort Colors

Insertion Sort List

Reverse Linked List

Middle of the Linked List
