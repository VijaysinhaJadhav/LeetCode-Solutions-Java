# 1834. Single-Threaded CPU

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Sorting, Heap (Priority Queue), Simulation  
**Companies:** Google, Amazon, Microsoft, Meta, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/single-threaded-cpu/)

You are given `n` tasks labeled from `0` to `n - 1` represented by a 2D integer array `tasks`, where `tasks[i] = [enqueueTimei, processingTimei]` means that the `i-th` task will be available to process at `enqueueTimei` and will take `processingTimei` to finish processing.

You have a single-threaded CPU that can process **at most one** task at a time and will act in the following way:

- If the CPU is idle and there are no available tasks to process, the CPU remains idle.
- If the CPU is idle and there are available tasks, the CPU will choose the one with the **shortest processing time**. If multiple tasks have the same shortest processing time, it will choose the task with the smallest index.
- Once a task is started, the CPU will **process the entire task** without stopping.
- The CPU can finish a task then start a new one immediately.

Return the order in which the CPU will process the tasks.

**Example 1:**

Input: tasks = [[1,2],[2,4],[3,2],[4,1]]

Output: [0,2,3,1]

Explanation:

At time = 1, task 0 is available to process. Available tasks = {0}.

Also at time = 1, the idle CPU starts processing task 0.

At time = 3, task 0 finishes processing. Available tasks = {1, 2}.

Also at time = 3, the CPU starts processing task 2 (shortest processing time).

At time = 5, task 2 finishes processing. Available tasks = {1, 3}.

Also at time = 5, the CPU starts processing task 3 (shortest processing time).

At time = 6, task 3 finishes processing. Available tasks = {1}.

At time = 6, the CPU starts processing task 1.

At time = 10, task 1 finishes processing.


**Example 2:**

Input: tasks = [[7,10],[7,12],[7,5],[7,4],[7,2]]

Output: [4,3,2,0,1]

Explanation: The tasks are available at time = 7 and the CPU processes them in order of shortest processing time.


**Constraints:**
- `tasks.length == n`
- `1 <= n <= 10^5`
- `1 <= enqueueTimei, processingTimei <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- Need to simulate CPU task processing with specific rules
- Tasks become available at their enqueue time
- CPU chooses shortest processing time first (with index tiebreaker)
- Need efficient data structures for task selection

### Key Insights
1. **Two-Phase Processing**:
   - First, sort tasks by enqueue time
   - Use min-heap for available tasks ordered by (processing time, index)
   
2. **Time Simulation**:
   - Track current time
   - Add tasks that become available at or before current time
   - If no tasks available, jump to next enqueue time
   
3. **Efficiency**:
   - O(n log n) time complexity
   - O(n) space complexity
   - Handle large n (up to 10^5)

### Approach Selection
**Chosen Approach:** Sorting + Min-Heap Simulation  
**Why this approach?** 
- O(n log n) time complexity
- Efficient task selection using heap
- Handles all edge cases
- Intuitive simulation

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Sorting + heap operations
- **Space Complexity:** O(n) - Heap and result storage

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort tasks by enqueue time but preserve original indices

Min-heap ordered by (processing time, index)

Handle idle CPU time by jumping to next available task's enqueue time

Process until all tasks are completed

🔗 Related Problems
Task Scheduler

Reorganize String

Distant Barcodes

Rearrange String k Distance Apart
