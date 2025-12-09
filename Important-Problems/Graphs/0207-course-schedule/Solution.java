
## Problems/Arrays-Hashing/0207-course-schedule/Solution.java

```java
/**
 * 207. Course Schedule
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
 * You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that
 * you must take course bi first if you want to take course ai.
 * Return true if you can finish all courses. Otherwise, return false.
 * 
 * Key Insights:
 * 1. This is a cycle detection problem in a directed graph
 * 2. Courses are nodes, prerequisites are directed edges (bi -> ai)
 * 3. If there's a cycle in the graph, it's impossible to finish all courses
 * 4. Can be solved with DFS (cycle detection) or BFS (topological sort)
 * 
 * Approach (DFS with Cycle Detection):
 * 1. Build adjacency list representing the graph
 * 2. Use three states for each node: unvisited, visiting, visited
 * 3. Perform DFS from each unvisited node
 * 4. If during DFS we encounter a node that is currently visiting, cycle detected
 * 5. Return false if cycle detected, true otherwise
 * 
 * Time Complexity: O(V + E) where V = numCourses, E = prerequisites.length
 * Space Complexity: O(V + E) for adjacency list and recursion stack
 * 
 * Tags: DFS, BFS, Graph, Topological Sort, Cycle Detection
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: DFS with Cycle Detection - RECOMMENDED
     * O(V + E) time, O(V + E) space
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            graph.get(prerequisite).add(course);
        }
        
        // 0 = unvisited, 1 = visiting, 2 = visited
        int[] visited = new int[numCourses];
        
        // Perform DFS from each unvisited node
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (hasCycleDFS(graph, visited, i)) {
                    return false; // Cycle detected
                }
            }
        }
        
        return true; // No cycles found
    }
    
    private boolean hasCycleDFS(List<List<Integer>> graph, int[] visited, int node) {
        visited[node] = 1; // Mark as visiting
        
        for (int neighbor : graph.get(node)) {
            if (visited[neighbor] == 0) {
                // Unvisited node, recursively check
                if (hasCycleDFS(graph, visited, neighbor)) {
                    return true;
                }
            } else if (visited[neighbor] == 1) {
                // Visiting node encountered - cycle detected!
                return true;
            }
            // If visited[neighbor] == 2, skip (already fully processed)
        }
        
        visited[node] = 2; // Mark as fully visited
        return false;
    }
    
    /**
     * Approach 2: BFS (Kahn's Algorithm) - Topological Sort
     * O(V + E) time, O(V + E) space - Using indegree for topological sort
     */
    public boolean canFinishBFS(int numCourses, int[][] prerequisites) {
        // Build adjacency list and indegree array
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            graph.get(prerequisite).add(course);
            indegree[course]++;
        }
        
        // Add all nodes with indegree 0 to queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int count = 0; // Count of processed nodes
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            count++;
            
            // Reduce indegree of neighbors
            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // If count == numCourses, no cycle (topological sort exists)
        return count == numCourses;
    }
    
    /**
     * Approach 3: DFS with Backtracking (Alternative)
     * O(V + E) time, O(V + E) space - Using recursion stack array
     */
    public boolean canFinishDFSBacktrack(int numCourses, int[][] prerequisites) {
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
        }
        
        boolean[] visited = new boolean[numCourses];
        boolean[] recursionStack = new boolean[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            if (!visited[i]) {
                if (hasCycleBacktrack(graph, visited, recursionStack, i)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private boolean hasCycleBacktrack(List<List<Integer>> graph, 
                                     boolean[] visited, 
                                     boolean[] recursionStack, 
                                     int node) {
        if (recursionStack[node]) {
            return true; // Cycle detected
        }
        
        if (visited[node]) {
            return false; // Already processed, no cycle from here
        }
        
        visited[node] = true;
        recursionStack[node] = true;
        
        for (int neighbor : graph.get(node)) {
            if (hasCycleBacktrack(graph, visited, recursionStack, neighbor)) {
                return true;
            }
        }
        
        recursionStack[node] = false; // Backtrack
        return false;
    }
    
    /**
     * Approach 4: Iterative DFS with Stack
     * O(V + E) time, O(V + E) space - Non-recursive DFS
     */
    public boolean canFinishIterativeDFS(int numCourses, int[][] prerequisites) {
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
        }
        
        int[] visited = new int[numCourses]; // 0=unvisited, 1=visiting, 2=visited
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                stack.push(i);
                visited[i] = 1; // Mark as visiting
                
                while (!stack.isEmpty()) {
                    int current = stack.peek();
                    boolean hasUnvisitedNeighbor = false;
                    
                    for (int neighbor : graph.get(current)) {
                        if (visited[neighbor] == 1) {
                            return false; // Cycle detected
                        }
                        if (visited[neighbor] == 0) {
                            stack.push(neighbor);
                            visited[neighbor] = 1;
                            hasUnvisitedNeighbor = true;
                            break;
                        }
                    }
                    
                    if (!hasUnvisitedNeighbor) {
                        stack.pop();
                        visited[current] = 2; // Mark as visited
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Approach 5: BFS with Layer-by-Layer Processing
     * O(V + E) time, O(V + E) space - Alternative BFS implementation
     */
    public boolean canFinishBFSLayers(int numCourses, int[][] prerequisites) {
        // Build graph and indegree
        List<Set<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new HashSet<>());
        }
        
        for (int[] prereq : prerequisites) {
            int from = prereq[1];
            int to = prereq[0];
            if (graph.get(from).add(to)) {
                indegree[to]++;
            }
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);
            
            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        return result.size() == numCourses;
    }
    
    /**
     * Helper method to visualize the graph and DFS process
     */
    private void visualizeDFS(int numCourses, int[][] prerequisites) {
        System.out.println("\nCourse Schedule DFS Visualization:");
        System.out.println("Number of courses: " + numCourses);
        System.out.println("Prerequisites: " + Arrays.deepToString(prerequisites));
        
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
        }
        
        System.out.println("\nGraph Representation:");
        for (int i = 0; i < numCourses; i++) {
            System.out.println("Course " + i + " -> " + graph.get(i));
        }
        
        int[] visited = new int[numCourses]; // 0=unvisited, 1=visiting, 2=visited
        System.out.println("\nDFS Traversal:");
        System.out.println("Node | State | Action");
        System.out.println("-----|-------|--------");
        
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (visualizeDFSHelper(graph, visited, i, 0)) {
                    System.out.println("\nCYCLE DETECTED! Cannot finish all courses.");
                    return;
                }
            }
        }
        
        System.out.println("\nNo cycles detected. Can finish all courses.");
    }
    
    private boolean visualizeDFSHelper(List<List<Integer>> graph, int[] visited, 
                                     int node, int depth) {
        String indent = "  ".repeat(depth);
        System.out.printf("%s%4d | %6s | Start DFS%n", indent, node, "UNVISITED");
        
        visited[node] = 1;
        System.out.printf("%s%4d | %6s | Mark as VISITING%n", indent, node, "VISITING");
        
        for (int neighbor : graph.get(node)) {
            if (visited[neighbor] == 0) {
                System.out.printf("%s%4d | %6s | Check neighbor %d (unvisited)%n", 
                                indent, node, "VISITING", neighbor);
                if (visualizeDFSHelper(graph, visited, neighbor, depth + 1)) {
                    return true;
                }
            } else if (visited[neighbor] == 1) {
                System.out.printf("%s%4d | %6s | CYCLE DETECTED at neighbor %d (visiting)%n", 
                                indent, node, "VISITING", neighbor);
                return true;
            } else {
                System.out.printf("%s%4d | %6s | Check neighbor %d (visited)%n", 
                                indent, node, "VISITING", neighbor);
            }
        }
        
        visited[node] = 2;
        System.out.printf("%s%4d | %6s | Mark as VISITED (backtrack)%n", indent, node, "VISITED");
        return false;
    }
    
    /**
     * Helper method to visualize BFS (Kahn's algorithm)
     */
    private void visualizeBFS(int numCourses, int[][] prerequisites) {
        System.out.println("\nCourse Schedule BFS (Kahn's Algorithm) Visualization:");
        System.out.println("Number of courses: " + numCourses);
        System.out.println("Prerequisites: " + Arrays.deepToString(prerequisites));
        
        // Build graph and indegree
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
            indegree[prereq[0]]++;
        }
        
        System.out.println("\nInitial Indegree:");
        for (int i = 0; i < numCourses; i++) {
            System.out.println("Course " + i + ": indegree = " + indegree[i]);
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        System.out.println("\nInitial queue: " + queue);
        System.out.println("\nBFS Process:");
        System.out.println("Step | Current | Updated Indegree | Queue");
        System.out.println("-----|---------|-----------------|------");
        
        int step = 0;
        int processed = 0;
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            processed++;
            
            System.out.printf("%4d | %7d | ", step++, current);
            
            // Update indegree of neighbors
            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
            
            // Print updated indegree (only changed ones for clarity)
            StringBuilder indegreeStr = new StringBuilder();
            for (int i = 0; i < numCourses; i++) {
                if (graph.get(current).contains(i)) {
                    indegreeStr.append(i).append(":").append(indegree[i]).append(" ");
                }
            }
            
            System.out.printf("%-15s | %s%n", indegreeStr.toString().trim(), queue);
        }
        
        System.out.println("\nProcessed " + processed + " out of " + numCourses + " courses");
        if (processed == numCourses) {
            System.out.println("SUCCESS: All courses can be finished (no cycles)");
        } else {
            System.out.println("FAILURE: Cycle detected, cannot finish all courses");
        }
    }
    
    /**
     * Helper method to analyze graph properties
     */
    private void analyzeGraph(int numCourses, int[][] prerequisites) {
        System.out.println("\nGraph Analysis:");
        System.out.println("Number of nodes (courses): " + numCourses);
        System.out.println("Number of edges (prerequisites): " + prerequisites.length);
        
        // Build graph
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        int[] outdegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
            indegree[prereq[0]]++;
            outdegree[prereq[1]]++;
        }
        
        // Calculate statistics
        int sources = 0, sinks = 0;
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) sources++;
            if (outdegree[i] == 0) sinks++;
        }
        
        System.out.println("Sources (indegree 0): " + sources);
        System.out.println("Sinks (outdegree 0): " + sinks);
        System.out.println("Average indegree: " + (double)prerequisites.length / numCourses);
        System.out.println("Average outdegree: " + (double)prerequisites.length / numCourses);
        
        // Check for obvious cycles
        boolean hasSelfLoop = false;
        for (int[] prereq : prerequisites) {
            if (prereq[0] == prereq[1]) {
                hasSelfLoop = true;
                break;
            }
        }
        
        if (hasSelfLoop) {
            System.out.println("WARNING: Graph contains self-loops (impossible prerequisite)");
        }
        
        if (sources == 0 && prerequisites.length > 0) {
            System.out.println("WARNING: No sources found - graph likely contains cycles");
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Course Schedule Solution:");
        System.out.println("==================================");
        
        // Test case 1: Simple valid case
        System.out.println("\nTest 1: Simple valid case");
        int numCourses1 = 2;
        int[][] prerequisites1 = {{1, 0}};
        boolean expected1 = true;
        
        long startTime = System.nanoTime();
        boolean result1a = solution.canFinish(numCourses1, prerequisites1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.canFinishBFS(numCourses1, prerequisites1);
        long time1b = System.nanoTime() - startTime;
        
        boolean test1a = (result1a == expected1);
        boolean test1b = (result1b == expected1);
        
        System.out.println("DFS: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        
        // Visualize the processes
        solution.visualizeDFS(numCourses1, prerequisites1);
        solution.visualizeBFS(numCourses1, prerequisites1);
        solution.analyzeGraph(numCourses1, prerequisites1);
        
        // Test case 2: Cycle detected
        System.out.println("\nTest 2: Cycle detected");
        int numCourses2 = 2;
        int[][] prerequisites2 = {{1, 0}, {0, 1}};
        boolean expected2 = false;
        
        boolean result2a = solution.canFinish(numCourses2, prerequisites2);
        System.out.println("Cycle case: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: No prerequisites
        System.out.println("\nTest 3: No prerequisites");
        int numCourses3 = 3;
        int[][] prerequisites3 = {};
        boolean expected3 = true;
        
        boolean result3a = solution.canFinish(numCourses3, prerequisites3);
        System.out.println("No prerequisites: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Complex valid case
        System.out.println("\nTest 4: Complex valid case");
        int numCourses4 = 4;
        int[][] prerequisites4 = {{1, 0}, {2, 1}, {3, 2}};
        boolean expected4 = true;
        
        boolean result4a = solution.canFinish(numCourses4, prerequisites4);
        System.out.println("Complex valid: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Complex cycle case
        System.out.println("\nTest 5: Complex cycle case");
        int numCourses5 = 4;
        int[][] prerequisites5 = {{1, 0}, {2, 1}, {3, 2}, {1, 3}};
        boolean expected5 = false;
        
        boolean result5a = solution.canFinish(numCourses5, prerequisites5);
        System.out.println("Complex cycle: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Simple case performance:");
        System.out.println("  DFS: " + time1a + " ns");
        System.out.println("  BFS: " + time1b + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        int numCourses7 = 100;
        int[][] prerequisites7 = generateLargePrerequisites(numCourses7, 200);
        
        startTime = System.nanoTime();
        boolean result7a = solution.canFinish(numCourses7, prerequisites7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result7b = solution.canFinishBFS(numCourses7, prerequisites7);
        long time7b = System.nanoTime() - startTime;
        
        System.out.println("Large input (100 courses, 200 prerequisites):");
        System.out.println("  DFS: " + time7a + " ns, Result: " + result7a);
        System.out.println("  BFS: " + time7b + " ns, Result: " + result7b);
        
        // Compare all approaches for consistency
        System.out.println("\nTest 8: Consistency Check");
        int testNumCourses = 5;
        int[][] testPrereqs = {{1,0}, {2,1}, {3,2}, {4,3}};
        boolean r1 = solution.canFinish(testNumCourses, testPrereqs);
        boolean r2 = solution.canFinishBFS(testNumCourses, testPrereqs);
        boolean r3 = solution.canFinishDFSBacktrack(testNumCourses, testPrereqs);
        boolean r4 = solution.canFinishIterativeDFS(testNumCourses, testPrereqs);
        boolean r5 = solution.canFinishBFSLayers(testNumCourses, testPrereqs);
        
        boolean consistent = (r1 == r2) && (r1 == r3) && (r1 == r4) && (r1 == r5);
        System.out.println("All approaches consistent: " + consistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DFS CYCLE DETECTION EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The problem reduces to detecting cycles in a directed graph.");
        System.out.println("Courses are nodes, prerequisites are directed edges (bi -> ai).");
        System.out.println("If there's a cycle, we cannot complete all courses.");
        
        System.out.println("\nThree States for DFS:");
        System.out.println("  0 - UNVISITED: Node not yet processed");
        System.out.println("  1 - VISITING: Node is currently in recursion stack");
        System.out.println("  2 - VISITED: Node has been fully processed");
        
        System.out.println("\nCycle Detection Logic:");
        System.out.println("  - If we encounter a node with state VISITING during DFS,");
        System.out.println("    it means we've found a back edge → CYCLE DETECTED");
        System.out.println("  - This works because in DFS, VISITING nodes are ancestors");
        System.out.println("    in the current recursion stack");
        
        System.out.println("\nVisual Example (Cycle):");
        System.out.println("Prerequisites: [[1,0], [0,1]]");
        System.out.println("Graph: 0 -> 1, 1 -> 0");
        System.out.println("DFS from 0: 0(VISITING) -> 1(VISITING) -> 0(VISITING) → CYCLE!");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. DFS with Cycle Detection (RECOMMENDED):");
        System.out.println("   Time: O(V + E) - Visit each node and edge once");
        System.out.println("   Space: O(V + E) - Adjacency list + recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Use three states for cycle detection");
        System.out.println("     - Perform DFS from each unvisited node");
        System.out.println("     - Return false if cycle detected");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive for cycle detection");
        System.out.println("     - Efficient and widely used");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Recursion depth for large graphs");
        System.out.println("   Best for: Interview settings, cycle detection");
        
        System.out.println("\n2. BFS (Kahn's Algorithm):");
        System.out.println("   Time: O(V + E) - Process each node and edge");
        System.out.println("   Space: O(V + E) - Adjacency list + queue + indegree");
        System.out.println("   How it works:");
        System.out.println("     - Calculate indegree for each node");
        System.out.println("     - Start with nodes having indegree 0");
        System.out.println("     - Process nodes, reduce indegree of neighbors");
        System.out.println("     - If all nodes processed, no cycle");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack overflow");
        System.out.println("     - Naturally produces topological order");
        System.out.println("     - Easy to extend for Course Schedule II");
        System.out.println("   Cons:");
        System.out.println("     - Requires extra indegree calculation");
        System.out.println("   Best for: When topological order is needed");
        
        System.out.println("\n3. Iterative DFS:");
        System.out.println("   Time: O(V + E) - Same as recursive DFS");
        System.out.println("   Space: O(V + E) - Uses stack instead of recursion");
        System.out.println("   How it works:");
        System.out.println("     - Use explicit stack for DFS");
        System.out.println("     - Simulate recursion with stack");
        System.out.println("     - Same cycle detection logic");
        System.out.println("   Pros:");
        System.out.println("     - No recursion depth limits");
        System.out.println("     - More control over traversal");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Less intuitive than recursive DFS");
        System.out.println("   Best for: Very large graphs, avoiding recursion");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("GRAPH THEORY INSIGHTS:");
        System.out.println("1. The graph is a DAG (Directed Acyclic Graph) iff topological sort exists");
        System.out.println("2. A topological ordering exists if and only if the graph has no cycles");
        System.out.println("3. Kahn's algorithm produces a topological ordering");
        System.out.println("4. DFS can detect cycles using recursion stack tracking");
        System.out.println("5. The problem is equivalent to checking if the graph is a DAG");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with DFS cycle detection - it's the most intuitive");
        System.out.println("2. Explain the three-state system clearly");
        System.out.println("3. Mention BFS (Kahn's) as an alternative approach");
        System.out.println("4. Discuss time/space complexity (O(V + E))");
        System.out.println("5. Handle edge cases: no prerequisites, self-cycles");
        System.out.println("6. Draw examples to illustrate cycle detection");
        System.out.println("7. Write clean graph building code");
        System.out.println("8. Test with provided examples");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to generate large test cases
     */
    private static int[][] generateLargePrerequisites(int numCourses, int numPrerequisites) {
        int[][] prerequisites = new int[numPrerequisites][2];
        Random random = new Random(42);
        
        for (int i = 0; i < numPrerequisites; i++) {
            int from = random.nextInt(numCourses);
            int to = random.nextInt(numCourses);
            // Avoid self-loops for valid test case
            while (from == to) {
                to = random.nextInt(numCourses);
            }
            prerequisites[i][0] = to;
            prerequisites[i][1] = from;
        }
        
        return prerequisites;
    }
}
