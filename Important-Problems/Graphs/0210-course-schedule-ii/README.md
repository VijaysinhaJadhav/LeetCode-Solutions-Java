# 210. Course Schedule II

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Depth-First Search, Breadth-First Search, Graph, Topological Sort  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/course-schedule-ii/)

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [ai, bi]` indicates that you must take course `bi` first if you want to take course `ai`.

- For example, the pair `[0, 1]`, indicates that to take course `0` you have to first take course `1`.

Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.

**Example 1:**

Input: numCourses = 2, prerequisites = [[1,0]]

Output: [0,1]

Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1].


**Example 2:**

Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]

Output: [0,2,1,3]

Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. So one correct course order is [0,2,1,3]. Another correct ordering is [0,1,2,3].


**Example 3:**

Input: numCourses = 1, prerequisites = []

Output: [0]


**Constraints:**
- `1 <= numCourses <= 2000`
- `0 <= prerequisites.length <= 5000`
- `prerequisites[i].length == 2`
- `0 <= ai, bi < numCourses`
- All the pairs prerequisites[i] are **unique**.

## 🧠 Thought Process

### Initial Thoughts
- This is an extension of Course Schedule I that requires the actual ordering
- Need to find a topological ordering of the courses
- Multiple approaches: DFS with post-order, BFS with Kahn's algorithm
- If cycle exists, return empty array

### Key Insights
1. **Topological Sort**: Find a linear ordering where for every edge u→v, u comes before v
2. **Kahn's Algorithm**: BFS-based approach using indegree
3. **DFS Approach**: Post-order traversal gives reverse topological order
4. **The key insight**: This is a topological sort problem on a directed graph

### Approach Selection
**Chosen Approach:** BFS (Kahn's Algorithm)  
**Why this approach?** 
- O(V + E) time complexity
- O(V + E) space complexity
- Naturally produces topological order
- Easy to detect cycles

## ⚡ Complexity Analysis
- **Time Complexity:** O(V + E) where V = numCourses, E = prerequisites.length
- **Space Complexity:** O(V + E) for adjacency list and data structures

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Kahn's algorithm naturally produces a topological ordering

If the number of processed nodes < numCourses, cycle exists

Multiple valid orderings are possible

The problem is essentially finding a topological sort of a DAG

🔗 Related Problems
Course Schedule

Course Schedule III

Course Schedule IV

Alien Dictionary

Minimum Height Trees

Find Eventual Safe States
