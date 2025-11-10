# 141. Linked List Cycle

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Hash Table, Linked List, Two Pointers  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Adobe

[LeetCode Link](https://leetcode.com/problems/linked-list-cycle/)

Given `head`, the head of a linked list, determine if the linked list has a cycle in it.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the `next` pointer. Internally, `pos` is used to denote the index of the node that tail's `next` pointer is connected to. Note that `pos` is not passed as a parameter.

Return `true` if there is a cycle in the linked list. Otherwise, return `false`.

**Example 1:**

Input: head = [3,2,0,-4], pos = 1

Output: true

Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).


**Example 2:**

Input: head = [1,2], pos = 0

Output: true

Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.


**Example 3:**

Input: head = [1], pos = -1

Output: false

Explanation: There is no cycle in the linked list.


**Constraints:**
- The number of nodes in the list is in the range `[0, 10^4]`.
- `-10^5 <= Node.val <= 10^5`
- `pos` is `-1` or a valid index in the linked-list.

## 🧠 Thought Process

### Initial Thoughts
- Need to detect if a linked list has a cycle (loop)
- Multiple approaches: hash table, Floyd's Cycle-Finding algorithm (two pointers)
- Must handle edge cases: empty list, single node, no cycle
- Floyd's algorithm is most efficient with O(1) space

### Key Insights
1. **Hash Table Approach**: Store visited nodes, if we see a node again, there's a cycle
2. **Floyd's Cycle-Finding**: Use slow and fast pointers, if they meet, there's a cycle
3. **Mathematical Proof**: If there's a cycle, fast pointer will eventually meet slow pointer
4. **Efficiency**: Floyd's algorithm uses O(1) space vs O(n) for hash table

### Approach Selection
**Chosen Approach:** Floyd's Cycle-Finding Algorithm (Two Pointers)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Elegant and efficient
- Classic algorithm for cycle detection

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - In worst case, fast pointer traverses list twice
- **Space Complexity:** O(1) - Only two pointers used regardless of list size

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Floyd's algorithm: slow moves 1 step, fast moves 2 steps per iteration

If fast reaches null, no cycle exists

If fast meets slow, cycle exists

The algorithm is also called "tortoise and hare" algorithm

🔗 Related Problems
Linked List Cycle II (Find cycle start node)

Happy Number

Find the Duplicate Number

Middle of the Linked List
