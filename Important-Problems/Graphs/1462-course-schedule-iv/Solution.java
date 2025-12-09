
## Solution.java

```java
/**
 * 1462. Course Schedule IV
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given numCourses courses and prerequisite pairs, answer queries about whether
 * one course is a prerequisite of another course.
 * 
 * Key Insights:
 * 1. Model as directed graph where edge u->v means u is prerequisite for v
 * 2. Need to find transitive closure (all reachable prerequisites)
 * 3. Multiple approaches: Floyd-Warshall, Topological Sort, DFS/BFS
 * 4. Floyd-Warshall is suitable given n ≤ 100 constraint
 * 
 * Approach (Floyd-Warshall):
 * 1. Build adjacency matrix for direct prerequisites
 * 2. Use Floyd-Warshall to compute transitive closure
 * 3. Answer queries in O(1) using precomputed matrix
 * 
 * Time Complexity: O(n^3) for preprocessing, O(1) per query
 * Space Complexity: O(n^2) for reachability matrix
 * 
 * Tags: Graph, Topological Sort, Dynamic Programming, DFS, BFS
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Floyd-Warshall Algorithm for Transitive Closure - RECOMMENDED
     * O(n^3) time, O(n^2) space - Efficient for n ≤ 100
     */
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        List<Boolean> result = new ArrayList<>();
        
        // Create reachability matrix
        boolean[][] reachable = new boolean[numCourses][numCourses];
        
        // Initialize with direct prerequisites
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];
            reachable[prereq][course] = true; // prereq -> course
        }
        
        // Floyd-Warshall algorithm for transitive closure
        for (int k = 0; k < numCourses; k++) {
            for (int i = 0; i < numCourses; i++) {
                for (int j = 0; j < numCourses; j++) {
                    // If i->k and k->j are reachable, then i->j is reachable
                    if (reachable[i][k] && reachable[k][j]) {
                        reachable[i][j] = true;
                    }
                }
            }
        }
        
        // Answer queries
        for (int[] query : queries) {
            int u = query[0]; // potential prerequisite
            int v = query[1]; // course
            result.add(reachable[u][v]);
        }
        
        return result;
    }
    
    /**
     * Approach 2: Topological Sort with BFS and Precomputation
     * O(n^2 + n*m) time, O(n^2) space - Good alternative
     */
    public List<Boolean> checkIfPrerequisiteTopological(int numCourses, int[][] prerequisites, int[][] queries) {
        List<Boolean> result = new ArrayList<>();
        
        // Build graph and indegree array
        List<Integer>[] graph = new ArrayList[numCourses];
        int[] indegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];
            graph[prereq].add(course);
            indegree[course]++;
        }
        
        // Precompute all prerequisites for each course
        Set<Integer>[] allPrereqs = new HashSet[numCourses];
        for (int i = 0; i < numCourses; i++) {
            allPrereqs[i] = new HashSet<>();
        }
        
        // Topological sort using BFS
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            for (int neighbor : graph[current]) {
                // Add all prerequisites of current to neighbor's prerequisites
                allPrereqs[neighbor].addAll(allPrereqs[current]);
                allPrereqs[neighbor].add(current);
                
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // Answer queries
        for (int[] query : queries) {
            int u = query[0]; // potential prerequisite
            int v = query[1]; // course
            result.add(allPrereqs[v].contains(u));
        }
        
        return result;
    }
    
    /**
     * Approach 3: DFS with Memoization
     * O(n^2 + q) time, O(n^2) space - Clean recursive approach
     */
    public List<Boolean> checkIfPrerequisiteDFS(int numCourses, int[][] prerequisites, int[][] queries) {
        List<Boolean> result = new ArrayList<>();
        
        // Build graph
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];
            graph[prereq].add(course);
        }
        
        // Memoization table for DFS results
        Boolean[][] memo = new Boolean[numCourses][numCourses];
        
        // Answer queries using DFS
        for (int[] query : queries) {
            int u = query[0]; // potential prerequisite
            int v = query[1]; // course
            result.add(dfs(u, v, graph, memo));
        }
        
        return result;
    }
    
    private boolean dfs(int start, int target, List<Integer>[] graph, Boolean[][] memo) {
        if (memo[start][target] != null) {
            return memo[start][target];
        }
        
        if (start == target) {
            return true;
        }
        
        for (int neighbor : graph[start]) {
            if (dfs(neighbor, target, graph, memo)) {
                memo[start][target] = true;
                return true;
            }
        }
        
        memo[start][target] = false;
        return false;
    }
    
    /**
     * Approach 4: BFS for each query (Naive)
     * O(q * (n + m)) time, O(n + m) space - Simple but inefficient for many queries
     */
    public List<Boolean> checkIfPrerequisiteBFS(int numCourses, int[][] prerequisites, int[][] queries) {
        List<Boolean> result = new ArrayList<>();
        
        // Build graph
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];
            graph[prereq].add(course);
        }
        
        // Answer each query with BFS
        for (int[] query : queries) {
            int u = query[0]; // potential prerequisite
            int v = query[1]; // course
            result.add(bfs(u, v, graph));
        }
        
        return result;
    }
    
    private boolean bfs(int start, int target, List<Integer>[] graph) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[graph.length];
        queue.offer(start);
        visited[start] = true;
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == target) {
                return true;
            }
            
            for (int neighbor : graph[current]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
        
        return false;
    }
    
    /**
     * Helper method to visualize the graph and reachability
     */
    private void visualizeGraph(int numCourses, int[][] prerequisites, int[][] queries) {
        System.out.println("\nCourse Schedule IV Visualization:");
        System.out.println("Number of courses: " + numCourses);
        
        // Build graph for visualization
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];
            graph[prereq].add(course);
        }
        
        System.out.println("\nPrerequisite Graph:");
        for (int i = 0; i < numCourses; i++) {
            System.out.println("Course " + i + " -> " + graph[i]);
        }
        
        System.out.println("\nQueries:");
        for (int i = 0; i < queries.length; i++) {
            System.out.println("Query " + i + ": Is " + queries[i][0] + " a prerequisite of " + queries[i][1] + "?");
        }
        
        // Compute and display reachability matrix
        boolean[][] reachable = new boolean[numCourses][numCourses];
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];
            reachable[prereq][course] = true;
        }
        
        // Floyd-Warshall
        for (int k = 0; k < numCourses; k++) {
            for (int i = 0; i < numCourses; i++) {
                for (int j = 0; j < numCourses; j++) {
                    if (reachable[i][k] && reachable[k][j]) {
                        reachable[i][j] = true;
                    }
                }
            }
        }
        
        System.out.println("\nReachability Matrix:");
        System.out.print("    ");
        for (int i = 0; i < numCourses; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();
        
        for (int i = 0; i < numCourses; i++) {
            System.out.printf("%2d: ", i);
            for (int j = 0; j < numCourses; j++) {
                System.out.print(reachable[i][j] ? " 1 " : " 0 ");
            }
            System.out.println();
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Course Schedule IV Solution:");
        System.out.println("=====================================");
        
        // Test case 1: Basic example
        System.out.println("\nTest 1: Basic example");
        int numCourses1 = 2;
        int[][] prerequisites1 = {{1, 0}};
        int[][] queries1 = {{0, 1}, {1, 0}};
        List<Boolean> expected1 = Arrays.asList(false, true);
        
        long startTime = System.nanoTime();
        List<Boolean> result1a = solution.checkIfPrerequisite(numCourses1, prerequisites1, queries1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Boolean> result1b = solution.checkIfPrerequisiteTopological(numCourses1, prerequisites1, queries1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Boolean> result1c = solution.checkIfPrerequisiteDFS(numCourses1, prerequisites1, queries1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Boolean> result1d = solution.checkIfPrerequisiteBFS(numCourses1, prerequisites1, queries1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = result1a.equals(expected1);
        boolean test1b = result1b.equals(expected1);
        boolean test1c = result1c.equals(expected1);
        boolean test1d = result1d.equals(expected1);
        
        System.out.println("Floyd-Warshall: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Topological: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("DFS: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the graph
        solution.visualizeGraph(numCourses1, prerequisites1, queries1);
        
        // Test case 2: No prerequisites
        System.out.println("\nTest 2: No prerequisites");
        int numCourses2 = 2;
        int[][] prerequisites2 = {};
        int[][] queries2 = {{1, 0}, {0, 1}};
        List<Boolean> expected2 = Arrays.asList(false, false);
        
        List<Boolean> result2a = solution.checkIfPrerequisite(numCourses2, prerequisites2, queries2);
        System.out.println("No prerequisites: " + result2a + " - " + 
                         (result2a.equals(expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Multiple prerequisites
        System.out.println("\nTest 3: Multiple prerequisites");
        int numCourses3 = 3;
        int[][] prerequisites3 = {{1, 2}, {1, 0}, {2, 0}};
        int[][] queries3 = {{1, 0}, {1, 2}};
        List<Boolean> expected3 = Arrays.asList(true, true);
        
        List<Boolean> result3a = solution.checkIfPrerequisite(numCourses3, prerequisites3, queries3);
        System.out.println("Multiple prerequisites: " + result3a + " - " + 
                         (result3a.equals(expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Transitive prerequisites
        System.out.println("\nTest 4: Transitive prerequisites");
        int numCourses4 = 4;
        int[][] prerequisites4 = {{2, 1}, {3, 2}, {1, 0}};
        int[][] queries4 = {{0, 3}, {1, 3}, {2, 3}, {3, 0}};
        List<Boolean> expected4 = Arrays.asList(true, true, true, false);
        
        List<Boolean> result4a = solution.checkIfPrerequisite(numCourses4, prerequisites4, queries4);
        System.out.println("Transitive prerequisites: " + result4a + " - " + 
                         (result4a.equals(expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Complex graph
        System.out.println("\nTest 5: Complex graph");
        int numCourses5 = 5;
        int[][] prerequisites5 = {{1, 0}, {2, 0}, {3, 1}, {3, 2}, {4, 3}};
        int[][] queries5 = {{0, 4}, {1, 4}, {2, 4}, {3, 4}, {4, 0}};
        List<Boolean> expected5 = Arrays.asList(true, true, true, true, false);
        
        List<Boolean> result5a = solution.checkIfPrerequisite(numCourses5, prerequisites5, queries5);
        System.out.println("Complex graph: " + result5a + " - " + 
                         (result5a.equals(expected5) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Basic example performance:");
        System.out.println("  Floyd-Warshall: " + time1a + " ns");
        System.out.println("  Topological: " + time1b + " ns");
        System.out.println("  DFS: " + time1c + " ns");
        System.out.println("  BFS: " + time1d + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        int numCourses7 = 50;
        int[][] prerequisites7 = generateRandomPrerequisites(numCourses7, 100);
        int[][] queries7 = generateRandomQueries(numCourses7, 100);
        
        startTime = System.nanoTime();
        List<Boolean> result7a = solution.checkIfPrerequisite(numCourses7, prerequisites7, queries7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Boolean> result7b = solution.checkIfPrerequisiteTopological(numCourses7, prerequisites7, queries7);
        long time7b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Boolean> result7c = solution.checkIfPrerequisiteDFS(numCourses7, prerequisites7, queries7);
        long time7c = System.nanoTime() - startTime;
        
        System.out.println("Larger input (50 courses, 100 prerequisites, 100 queries):");
        System.out.println("  Floyd-Warshall: " + time7a + " ns");
        System.out.println("  Topological: " + time7b + " ns");
        System.out.println("  DFS: " + time7c + " ns");
        
        // Verify all approaches produce the same result
        boolean allEqual = result7a.equals(result7b) && result7a.equals(result7c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("FLOYD-WARSHALL ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We need to compute transitive closure - if u is prerequisite of v,");
        System.out.println("either directly or through intermediate courses.");
        System.out.println("Floyd-Warshall efficiently computes all-pairs reachability.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize reachability matrix with direct prerequisites");
        System.out.println("2. For each intermediate node k:");
        System.out.println("   - For each pair (i, j):");
        System.out.println("     - If i->k and k->j are reachable, then i->j is reachable");
        System.out.println("3. Answer queries using the precomputed matrix");
        
        System.out.println("\nWhy it works for this problem:");
        System.out.println("1. Graph is a DAG (guaranteed by problem constraints)");
        System.out.println("2. n ≤ 100 makes O(n^3) feasible (1,000,000 operations)");
        System.out.println("3. Precomputation allows O(1) query answering");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Floyd-Warshall (RECOMMENDED):");
        System.out.println("   Time: O(n^3) for preprocessing, O(1) per query");
        System.out.println("   Space: O(n^2) for reachability matrix");
        System.out.println("   How it works:");
        System.out.println("     - Build adjacency matrix for direct edges");
        System.out.println("     - Use triple loop to compute transitive closure");
        System.out.println("     - Answer queries using precomputed matrix");
        System.out.println("   Pros:");
        System.out.println("     - Simple implementation");
        System.out.println("     - O(1) query time after preprocessing");
        System.out.println("     - Well-suited for n ≤ 100 constraint");
        System.out.println("   Cons:");
        System.out.println("     - O(n^3) doesn't scale well for larger n");
        System.out.println("     - Uses O(n^2) space");
        System.out.println("   Best for: Small graphs (n ≤ 100), many queries");
        
        System.out.println("\n2. Topological Sort with BFS:");
        System.out.println("   Time: O(n^2 + n*m) for preprocessing, O(1) per query");
        System.out.println("   Space: O(n^2) for storing all prerequisites");
        System.out.println("   How it works:");
        System.out.println("     - Build graph and compute indegrees");
        System.out.println("     - Use BFS for topological sort");
        System.out.println("     - Propagate prerequisites during traversal");
        System.out.println("     - Store all prerequisites for each course");
        System.out.println("   Pros:");
        System.out.println("     - More efficient for sparse graphs");
        System.out.println("     - O(1) query time after preprocessing");
        System.out.println("     - Natural approach for DAGs");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Still uses O(n^2) space");
        System.out.println("   Best for: Sparse graphs, when topological order is useful");
        
        System.out.println("\n3. DFS with Memoization:");
        System.out.println("   Time: O(n^2 + q) overall, O(n) per query worst-case");
        System.out.println("   Space: O(n^2) for memoization table");
        System.out.println("   How it works:");
        System.out.println("     - Build graph");
        System.out.println("     - For each query, use DFS to check reachability");
        System.out.println("     - Memoize results to avoid recomputation");
        System.out.println("   Pros:");
        System.out.println("     - Simple recursive implementation");
        System.out.println("     - Good for few queries");
        System.out.println("     - Memory efficient for sparse memoization");
        System.out.println("   Cons:");
        System.out.println("     - O(n) per query worst-case");
        System.out.println("     - Not optimal for many queries");
        System.out.println("   Best for: Few queries, when simplicity is prioritized");
        
        System.out.println("\n4. BFS for each query (Naive):");
        System.out.println("   Time: O(q * (n + m)) overall");
        System.out.println("   Space: O(n + m) for graph representation");
        System.out.println("   How it works:");
        System.out.println("     - Build graph");
        System.out.println("     - For each query, run BFS to check reachability");
        System.out.println("   Pros:");
        System.out.println("     - Very simple implementation");
        System.out.println("     - Memory efficient");
        System.out.println("     - No preprocessing overhead");
        System.out.println("   Cons:");
        System.out.println("     - O(n + m) per query - too slow for many queries");
        System.out.println("     - Repeated work for similar queries");
        System.out.println("   Best for: Very few queries, learning purposes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Transitive closure: If A->B and B->C, then A->C");
        System.out.println("2. Maximum edges in DAG: n*(n-1)/2");
        System.out.println("3. Floyd-Warshall computes reachability in O(n^3)");
        System.out.println("4. For n=100, Floyd-Warshall does ~1,000,000 operations");
        System.out.println("5. Problem guarantees no cycles (DAG property)");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Floyd-Warshall - mention it's suitable for n ≤ 100");
        System.out.println("2. Explain transitive closure concept clearly");
        System.out.println("3. Discuss time/space complexity trade-offs");
        System.out.println("4. Mention alternative approaches (Topological, DFS)");
        System.out.println("5. Handle edge cases: no prerequisites, single course");
        System.out.println("6. Consider discussing how you'd handle larger n");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper methods for test generation
     */
    private static int[][] generateRandomPrerequisites(int numCourses, int count) {
        Random random = new Random(42);
        Set<String> seen = new HashSet<>();
        List<int[]> result = new ArrayList<>();
        
        while (result.size() < count && result.size() < numCourses * (numCourses - 1) / 2) {
            int a = random.nextInt(numCourses);
            int b = random.nextInt(numCourses);
            if (a != b) {
                String key = a + "," + b;
                if (!seen.contains(key)) {
                    seen.add(key);
                    result.add(new int[]{a, b});
                }
            }
        }
        
        return result.toArray(new int[0][]);
    }
    
    private static int[][] generateRandomQueries(int numCourses, int count) {
        Random random = new Random(42);
        int[][] result = new int[count][2];
        
        for (int i = 0; i < count; i++) {
            int u = random.nextInt(numCourses);
            int v = random.nextInt(numCourses);
            while (u == v) {
                v = random.nextInt(numCourses);
            }
            result[i] = new int[]{u, v};
        }
        
        return result;
    }
}
