# 142. Linked List Cycle II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, Linked List, Two Pointers  
**Companies:** Amazon, Microsoft, Google, Facebook, Apple, Bloomberg, Uber, Oracle

[LeetCode Link](https://leetcode.com/problems/linked-list-cycle-ii/)

Given the `head` of a linked list, return the node where the cycle begins. If there is no cycle, return `null`.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the `next` pointer. Internally, `pos` is used to denote the index of the node that tail's `next` pointer is connected to (0-indexed). It is `-1` if there is no cycle.

**Note:** Do not modify the linked list.

**Example 1:**

![Cycle Linked List](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist.png)

Input: head = [3,2,0,-4], pos = 1

Output: tail connects to node index 1

Explanation: There is a cycle in the linked list, where tail connects to the second node.


**Example 2:**

![Cycle Linked List 2](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test2.png)

Input: head = [1,2], pos = 0

Output: tail connects to node index 0

Explanation: There is a cycle in the linked list, where tail connects to the first node.


**Example 3:**

![No Cycle Linked List](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test3.png)

Input: head = [1], pos = -1

Output: no cycle

Explanation: There is no cycle in the linked list.


**Constraints:**
- The number of the nodes in the list is in the range `[0, 10^4]`.
- `-10^5 <= Node.val <= 10^5`
- `pos` is `-1` or a valid index in the linked-list.

**Follow up:** Can you solve it using `O(1)` (i.e. constant) memory?

## 🧠 Thought Process

### Initial Thoughts
- Need to detect if there's a cycle and find where it starts
- Floyd's Cycle Detection Algorithm (Tortoise and Hare) is optimal
- Mathematical proof shows where to find cycle start

### Key Insights
1. **Floyd's Algorithm**: Use slow and fast pointers to detect cycle
2. **Cycle Start Detection**: After detecting cycle, reset one pointer to head
3. **Mathematical Relationship**: Distance from head to cycle start = distance from meeting point to cycle start
4. **Hash Table Approach**: Store visited nodes, but uses O(n) space

### Approach Selection
**Chosen Approach:** Floyd's Cycle Detection Algorithm  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Elegant mathematical solution
- Meets follow-up requirement

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Linear time where n is number of nodes
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Slow pointer moves 1 step, fast pointer moves 2 steps

If they meet, there's a cycle

To find cycle start: reset slow to head, move both 1 step until they meet

The meeting point is the cycle start

🔗 Related Problems
Linked List Cycle

Intersection of Two Linked Lists

Happy Number

Find the Duplicate Number

