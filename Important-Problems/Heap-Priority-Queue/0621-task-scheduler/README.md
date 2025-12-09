# 621. Task Scheduler

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Greedy, Sorting, Heap (Priority Queue), Counting  
**Companies:** Google, Microsoft, Amazon, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/task-scheduler/)

You are given an array of CPU `tasks`, each labeled with a letter from `A` to `Z`, and a non-negative integer `n`. Each task takes one unit of time to complete, and there must be at least `n` units of time between two tasks of the same type.

Return the **minimum number of units of time** required to complete all tasks.

**Example 1:**

Input: tasks = ["A","A","A","B","B","B"], n = 2

Output: 8

Explanation:

A -> B -> idle -> A -> B -> idle -> A -> B

There is at least 2 units of time between any two same tasks.


**Example 2:**

Input: tasks = ["A","A","A","B","B","B"], n = 0

Output: 6

Explanation: On this case any permutation of size 6 would work.


**Example 3:**

Input: tasks = ["A","A","A","A","A","B","B","B","B","B","C","C","C","C","C","D","D","D"], n = 2

Output: 16


**Constraints:**
- `1 <= tasks.length <= 10^4`
- `tasks[i]` is an uppercase English letter
- `0 <= n <= 100`

## 🧠 Thought Process

### Initial Thoughts
- Need to schedule tasks with cooldown period between same tasks
- Want to minimize total time
- Idle time may be needed to satisfy cooldown constraint
- The most frequent tasks determine the schedule length

### Key Insights
1. **Mathematical Formula Approach**:
   - Find the maximum frequency task
   - Calculate idle slots based on cooldown
   - Fill idle slots with other tasks
   - Remaining idle slots contribute to total time

2. **Max Heap Approach**:
   - Use max heap to always pick most frequent available task
   - Track cooldown for each task type
   - Simulate the scheduling process

3. **Greedy Intuition**:
   - Always schedule the task with highest frequency that's not in cooldown
   - This minimizes idle time

### Approach Selection
**Chosen Approach:** Mathematical Formula (Most Efficient)  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity (only 26 counters)
- Elegant mathematical solution
- No simulation needed

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n is number of tasks
- **Space Complexity:** O(1) for frequency array (26 elements)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key formula: (maxCount - 1) * (n + 1) + countOfMax

Handle case when idle slots become negative (tasks fill all slots)

Final answer is max of formula result and tasks.length

Works because we arrange tasks in "frames" of size n+1

🔗 Related Problems
Rearrange String k Distance Apart

Reorganize String

Task Scheduler (this problem)

Distant Barcodes
