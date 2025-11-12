# 1462. Course Schedule IV

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Graph, Topological Sort, Dynamic Programming, Depth-First Search, Breadth-First Search  
**Companies:** Google, Amazon, Microsoft, Facebook, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/course-schedule-iv/)

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [a_i, b_i]` indicates that you must take course `b_i` first if you want to take course `a_i`.

- For example, the pair `[0, 1]` indicates that you have to take course `1` before you can take course `0`.

You are also given an array `queries` where `queries[j] = [u_j, v_j]`. For the `jth` query, you must answer whether course `u_j` is a prerequisite of course `v_j`.

Return a boolean array `answer`, where `answer[j]` is the answer to the `jth` query.

**Example 1:**

Input: numCourses = 2, prerequisites = [[1,0]], queries = [[0,1],[1,0]]

Output: [false,true]

Explanation: The pair [1,0] indicates that you have to take course 0 before you can take course 1.

Course 0 is not a prerequisite of course 1, but course 1 is a prerequisite of course 0.


**Example 2:**

Input: numCourses = 2, prerequisites = [], queries = [[1,0],[0,1]]

Output: [false,false]

Explanation: There are no prerequisites, so each course is independent.


**Example 3:**

Input: numCourses = 3, prerequisites = [[1,2],[1,0],[2,0]], queries = [[1,0],[1,2]]

Output: [true,true]


**Constraints:**
- `2 <= numCourses <= 100`
- `0 <= prerequisites.length <= (numCourses * (numCourses - 1) / 2)`
- `prerequisites[i].length == 2`
- `0 <= a_i, b_i <= numCourses - 1`
- `a_i != b_i`
- All the pairs `[a_i, b_i]` are **unique**.
- The prerequisites graph has no cycles.
- `1 <= queries.length <= 10^4`
- `0 <= u_j, v_j <= numCourses - 1`
- `u_j != v_j`

## 🧠 Thought Process

### Initial Thoughts
- We need to determine prerequisite relationships between courses
- The problem can be modeled as a directed graph where edges represent prerequisites
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Graph Representation**: Model courses as nodes and prerequisites as directed edges
2. **Transitive Closure**: Need to find all reachable nodes (transitive prerequisites)
3. **Multiple Approaches**: 
   - Floyd-Warshall for all-pairs reachability
   - Topological Sort with BFS/DFS
   - Dynamic Programming on DAG

### Approach Selection
**Chosen Approach:** Floyd-Warshall Algorithm  
**Why this approach?** 
- O(n³) time complexity is acceptable for n ≤ 100
- Simple implementation for transitive closure
- Precomputes all relationships for fast query answering
- Well-suited for the constraint sizes

## ⚡ Complexity Analysis
- **Time Complexity:** O(n³) for preprocessing, O(1) per query
- **Space Complexity:** O(n²) for the reachability matrix

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The graph is guaranteed to be a DAG (no cycles)

Floyd-Warshall efficiently computes transitive closure

For larger n, topological sort with BFS/DFS would be better

The algorithm finds all indirect prerequisites through transitive relationships

🔗 Related Problems
Course Schedule

Course Schedule II

Course Schedule III

Parallel Courses
