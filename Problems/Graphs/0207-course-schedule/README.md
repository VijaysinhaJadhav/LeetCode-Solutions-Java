# 207. Course Schedule

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Depth-First Search, Breadth-First Search, Graph, Topological Sort  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn

[LeetCode Link](https://leetcode.com/problems/course-schedule/)

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [ai, bi]` indicates that you must take course `bi` first if you want to take course `ai`.

- For example, the pair `[0, 1]`, indicates that to take course `0` you have to first take course `1`.

Return `true` if you can finish all courses. Otherwise, return `false`.

**Example 1:**

Input: numCourses = 2, prerequisites = [[1,0]]

Output: true

Explanation: There are a total of 2 courses to take.

To take course 1 you should have finished course 0. So it is possible.


**Example 2:**

Input: numCourses = 2, prerequisites = [[1,0],[0,1]]

Output: false

Explanation: There are a total of 2 courses to take.

To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. It is impossible.


**Constraints:**
- `1 <= numCourses <= 2000`
- `0 <= prerequisites.length <= 5000`
- `prerequisites[i].length == 2`
- `0 <= ai, bi < numCourses`
- All the pairs prerequisites[i] are **unique**.

## 🧠 Thought Process

### Initial Thoughts
- This is a cycle detection problem in a directed graph
- Courses are nodes, prerequisites are directed edges
- If there's a cycle in the graph, it's impossible to finish all courses
- Multiple approaches: DFS with cycle detection, BFS with topological sort

### Key Insights
1. **Graph Representation**: Represent courses as nodes and prerequisites as directed edges
2. **Cycle Detection**: If there's a cycle in the dependency graph, courses cannot be completed
3. **Topological Sort**: A valid ordering exists if and only if the graph is a DAG (Directed Acyclic Graph)
4. **The key insight**: This reduces to detecting cycles in a directed graph

### Approach Selection
**Chosen Approach:** DFS with Cycle Detection  
**Why this approach?** 
- O(V + E) time complexity
- O(V + E) space complexity
- Intuitive for cycle detection
- Efficient for the given constraints

## ⚡ Complexity Analysis
- **Time Complexity:** O(V + E) where V = numCourses, E = prerequisites.length
- **Space Complexity:** O(V + E) for adjacency list and recursion stack

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The problem is equivalent to checking if the directed graph is acyclic

We can use three states for each node: unvisited, visiting, visited

During DFS, if we encounter a node that is currently being visited, we've found a cycle

Kahn's algorithm (BFS with indegree) is another popular approach

🔗 Related Problems
Course Schedule II

Course Schedule III

Course Schedule IV

Find Eventual Safe States

Keys and Rooms
