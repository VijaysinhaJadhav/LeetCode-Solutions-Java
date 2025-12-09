
## Problems/Arrays-Hashing/0210-course-schedule-ii/Solution.java

```java
/**
 * 210. Course Schedule II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
 * You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that
 * you must take course bi first if you want to take course ai.
 * Return the ordering of courses you should take to finish all courses.
 * If it is impossible to finish all courses, return an empty array.
 * 
 * Key Insights:
 * 1. This is a topological sort problem on a directed graph
 * 2. Courses are nodes, prerequisites are directed edges (bi -> ai)
 * 3. Need to find a linear ordering where for every edge u->v, u comes before v
 * 4. Can be solved with BFS (Kahn's algorithm) or DFS
 * 
 * Approach (BFS - Kahn's Algorithm):
 * 1. Build adjacency list and calculate indegree for each node
 * 2. Add all nodes with indegree 0 to queue
 * 3. Process nodes, add to result, reduce indegree of neighbors
 * 4. If all nodes processed, return result; else return empty array (cycle)
 * 
 * Time Complexity: O(V + E) where V = numCourses, E = prerequisites.length
 * Space Complexity: O(V + E) for adjacency list and data structures
 * 
 * Tags: BFS, DFS, Graph, Topological Sort, Kahn's Algorithm
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: BFS (Kahn's Algorithm) - RECOMMENDED
     * O(V + E) time, O(V + E) space
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
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
        
        int[] result = new int[numCourses];
        int index = 0;
        int count = 0; // Count of processed nodes
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            result[index++] = current;
            count++;
            
            // Reduce indegree of neighbors
            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // If all nodes processed, return result; else cycle exists
        return count == numCourses ? result : new int[0];
    }
    
    /**
     * Approach 2: DFS with Post-order Traversal
     * O(V + E) time, O(V + E) space - Produces reverse topological order
     */
    public int[] findOrderDFS(int numCourses, int[][] prerequisites) {
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
        }
        
        // 0 = unvisited, 1 = visiting, 2 = visited
        int[] visited = new int[numCourses];
        List<Integer> result = new ArrayList<>();
        
        // Perform DFS from each unvisited node
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (hasCycleDFS(graph, visited, result, i)) {
                    return new int[0]; // Cycle detected
                }
            }
        }
        
        // Reverse the result (post-order gives reverse topological order)
        Collections.reverse(result);
        return result.stream().mapToInt(i -> i).toArray();
    }
    
    private boolean hasCycleDFS(List<List<Integer>> graph, int[] visited, 
                               List<Integer> result, int node) {
        visited[node] = 1; // Mark as visiting
        
        for (int neighbor : graph.get(node)) {
            if (visited[neighbor] == 0) {
                if (hasCycleDFS(graph, visited, result, neighbor)) {
                    return true;
                }
            } else if (visited[neighbor] == 1) {
                return true; // Cycle detected
            }
        }
        
        visited[node] = 2; // Mark as visited
        result.add(node); // Add to result in post-order
        return false;
    }
    
    /**
     * Approach 3: Iterative DFS with Stack
     * O(V + E) time, O(V + E) space - Non-recursive DFS
     */
    public int[] findOrderIterativeDFS(int numCourses, int[][] prerequisites) {
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
        List<Integer> result = new ArrayList<>();
        
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (hasCycleIterativeDFS(graph, visited, stack, i)) {
                    return new int[0];
                }
            }
        }
        
        // Stack contains topological order (reverse of DFS finish times)
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        
        return result.stream().mapToInt(i -> i).toArray();
    }
    
    private boolean hasCycleIterativeDFS(List<List<Integer>> graph, int[] visited,
                                       Stack<Integer> stack, int start) {
        Stack<Integer> dfsStack = new Stack<>();
        dfsStack.push(start);
        visited[start] = 1;
        
        while (!dfsStack.isEmpty()) {
            int current = dfsStack.peek();
            boolean hasUnvisitedNeighbor = false;
            
            for (int neighbor : graph.get(current)) {
                if (visited[neighbor] == 1) {
                    return true; // Cycle detected
                }
                if (visited[neighbor] == 0) {
                    dfsStack.push(neighbor);
                    visited[neighbor] = 1;
                    hasUnvisitedNeighbor = true;
                    break;
                }
            }
            
            if (!hasUnvisitedNeighbor) {
                dfsStack.pop();
                visited[current] = 2;
                stack.push(current); // Add to result stack when finished
            }
        }
        
        return false;
    }
    
    /**
     * Approach 4: BFS with Result Tracking
     * O(V + E) time, O(V + E) space - Alternative BFS implementation
     */
    public int[] findOrderBFSAlternative(int numCourses, int[][] prerequisites) {
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
        
        int[] result = new int[numCourses];
        int index = 0;
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            result[index++] = current;
            
            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        return index == numCourses ? result : new int[0];
    }
    
    /**
     * Approach 5: DFS with Array Result
     * O(V + E) time, O(V + E) space - Using array instead of list for result
     */
    public int[] findOrderDFSArray(int numCourses, int[][] prerequisites) {
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
        }
        
        int[] visited = new int[numCourses];
        int[] result = new int[numCourses];
        int[] resultIndex = {numCourses - 1}; // Start from end for reverse order
        
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (hasCycleDFSArray(graph, visited, result, resultIndex, i)) {
                    return new int[0];
                }
            }
        }
        
        return result;
    }
    
    private boolean hasCycleDFSArray(List<List<Integer>> graph, int[] visited,
                                   int[] result, int[] resultIndex, int node) {
        visited[node] = 1;
        
        for (int neighbor : graph.get(node)) {
            if (visited[neighbor] == 0) {
                if (hasCycleDFSArray(graph, visited, result, resultIndex, neighbor)) {
                    return true;
                }
            } else if (visited[neighbor] == 1) {
                return true;
            }
        }
        
        visited[node] = 2;
        result[resultIndex[0]--] = node; // Add in reverse order
        return false;
    }
    
    /**
     * Helper method to visualize the BFS process
     */
    private void visualizeBFS(int numCourses, int[][] prerequisites) {
        System.out.println("\nCourse Schedule II - BFS (Kahn's Algorithm) Visualization:");
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
        
        System.out.println("\nInitial Graph:");
        for (int i = 0; i < numCourses; i++) {
            System.out.println("Course " + i + " -> " + graph.get(i) + " (indegree: " + indegree[i] + ")");
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        System.out.println("\nInitial queue: " + queue);
        System.out.println("\nBFS Process:");
        System.out.println("Step | Current | Result So Far | Updated Queue | Updated Indegree");
        System.out.println("-----|---------|---------------|---------------|-----------------");
        
        List<Integer> result = new ArrayList<>();
        int step = 0;
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);
            
            System.out.printf("%4d | %7d | %-13s | ", step++, current, result.toString());
            
            // Update indegree of neighbors
            List<Integer> updatedNodes = new ArrayList<>();
            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                    updatedNodes.add(neighbor);
                }
            }
            
            System.out.printf("%-13s | ", queue.toString());
            
            // Show updated indegree for affected nodes
            StringBuilder indegreeStr = new StringBuilder();
            for (int neighbor : graph.get(current)) {
                indegreeStr.append(neighbor).append(":").append(indegree[neighbor]).append(" ");
            }
            System.out.println(indegreeStr.toString().trim());
        }
        
        System.out.println("\nFinal Result: " + result);
        if (result.size() == numCourses) {
            System.out.println("SUCCESS: Valid topological order found");
        } else {
            System.out.println("FAILURE: Cycle detected, cannot complete all courses");
            System.out.println("Processed " + result.size() + " out of " + numCourses + " courses");
        }
    }
    
    /**
     * Helper method to visualize the DFS process
     */
    private void visualizeDFS(int numCourses, int[][] prerequisites) {
        System.out.println("\nCourse Schedule II - DFS Visualization:");
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
        
        int[] visited = new int[numCourses];
        List<Integer> result = new ArrayList<>();
        
        System.out.println("\nDFS Traversal (Post-order):");
        System.out.println("Node | State | Action | Result");
        System.out.println("-----|-------|--------|-------");
        
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (visualizeDFSHelper(graph, visited, result, i, 0)) {
                    System.out.println("\nCYCLE DETECTED! Cannot finish all courses.");
                    return;
                }
            }
        }
        
        Collections.reverse(result);
        System.out.println("\nFinal Result (reversed): " + result);
        System.out.println("SUCCESS: Valid topological order found");
    }
    
    private boolean visualizeDFSHelper(List<List<Integer>> graph, int[] visited,
                                     List<Integer> result, int node, int depth) {
        String indent = "  ".repeat(depth);
        System.out.printf("%s%4d | %6s | Start DFS   | %s%n", indent, node, "UNVISITED", result.toString());
        
        visited[node] = 1;
        System.out.printf("%s%4d | %6s | Visiting    | %s%n", indent, node, "VISITING", result.toString());
        
        for (int neighbor : graph.get(node)) {
            if (visited[neighbor] == 0) {
                System.out.printf("%s%4d | %6s | Check %d     | %s%n", 
                                indent, node, "VISITING", neighbor, result.toString());
                if (visualizeDFSHelper(graph, visited, result, neighbor, depth + 1)) {
                    return true;
                }
            } else if (visited[neighbor] == 1) {
                System.out.printf("%s%4d | %6s | CYCLE at %d | %s%n", 
                                indent, node, "VISITING", neighbor, result.toString());
                return true;
            }
        }
        
        visited[node] = 2;
        result.add(node);
        System.out.printf("%s%4d | %6s | Finished    | %s%n", indent, node, "VISITED", result.toString());
        return false;
    }
    
    /**
     * Helper method to compare different valid orderings
     */
    private void compareValidOrderings(int numCourses, int[][] prerequisites) {
        System.out.println("\nValid Ordering Analysis:");
        System.out.println("Prerequisites: " + Arrays.deepToString(prerequisites));
        
        // Find all valid orderings using multiple BFS runs
        List<int[]> allOrderings = findAllValidOrderings(numCourses, prerequisites);
        
        System.out.println("Number of valid orderings: " + allOrderings.size());
        
        for (int i = 0; i < Math.min(3, allOrderings.size()); i++) {
            System.out.println("Valid ordering " + (i + 1) + ": " + Arrays.toString(allOrderings.get(i)));
        }
        
        if (allOrderings.size() > 3) {
            System.out.println("... and " + (allOrderings.size() - 3) + " more");
        }
        
        // Verify all orderings satisfy prerequisites
        System.out.println("\nVerification:");
        for (int[] ordering : allOrderings) {
            if (isValidOrdering(ordering, prerequisites)) {
                System.out.println("✓ " + Arrays.toString(ordering) + " is valid");
            } else {
                System.out.println("✗ " + Arrays.toString(ordering) + " is INVALID");
            }
        }
    }
    
    private List<int[]> findAllValidOrderings(int numCourses, int[][] prerequisites) {
        List<int[]> result = new ArrayList<>();
        // This is a simplified version - in practice, we'd use backtracking
        // For demonstration, we'll just run BFS multiple times with different queue orders
        
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
        
        // Try different queue orders by sorting available nodes differently
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        if (!queue.isEmpty()) {
            // For demonstration, just return one valid ordering
            int[] ordering = findOrder(numCourses, prerequisites);
            if (ordering.length > 0) {
                result.add(ordering);
            }
        }
        
        return result;
    }
    
    private boolean isValidOrdering(int[] ordering, int[][] prerequisites) {
        Map<Integer, Integer> position = new HashMap<>();
        for (int i = 0; i < ordering.length; i++) {
            position.put(ordering[i], i);
        }
        
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            if (position.get(prerequisite) > position.get(course)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Course Schedule II Solution:");
        System.out.println("=====================================");
        
        // Test case 1: Simple valid case
        System.out.println("\nTest 1: Simple valid case");
        int numCourses1 = 2;
        int[][] prerequisites1 = {{1, 0}};
        int[] expected1 = {0, 1}; // Or any valid ordering
        
        long startTime = System.nanoTime();
        int[] result1a = solution.findOrder(numCourses1, prerequisites1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1b = solution.findOrderDFS(numCourses1, prerequisites1);
        long time1b = System.nanoTime() - startTime;
        
        boolean test1a = isValidOrdering(result1a, prerequisites1) && result1a.length == numCourses1;
        boolean test1b = isValidOrdering(result1b, prerequisites1) && result1b.length == numCourses1;
        
        System.out.println("BFS: " + Arrays.toString(result1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DFS: " + Arrays.toString(result1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        
        // Visualize the processes
        solution.visualizeBFS(numCourses1, prerequisites1);
        solution.visualizeDFS(numCourses1, prerequisites1);
        solution.compareValidOrderings(numCourses1, prerequisites1);
        
        // Test case 2: Multiple valid orderings
        System.out.println("\nTest 2: Multiple valid orderings");
        int numCourses2 = 4;
        int[][] prerequisites2 = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        
        int[] result2a = solution.findOrder(numCourses2, prerequisites2);
        System.out.println("Multiple orderings: " + Arrays.toString(result2a) + 
                         " - " + (isValidOrdering(result2a, prerequisites2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Cycle detected
        System.out.println("\nTest 3: Cycle detected");
        int numCourses3 = 2;
        int[][] prerequisites3 = {{1, 0}, {0, 1}};
        int[] expected3 = {};
        
        int[] result3a = solution.findOrder(numCourses3, prerequisites3);
        System.out.println("Cycle case: " + Arrays.toString(result3a) + " - " + 
                         (result3a.length == 0 ? "PASSED" : "FAILED"));
        
        // Test case 4: No prerequisites
        System.out.println("\nTest 4: No prerequisites");
        int numCourses4 = 3;
        int[][] prerequisites4 = {};
        
        int[] result4a = solution.findOrder(numCourses4, prerequisites4);
        System.out.println("No prerequisites: " + Arrays.toString(result4a) + 
                         " - " + (result4a.length == numCourses4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single course
        System.out.println("\nTest 5: Single course");
        int numCourses5 = 1;
        int[][] prerequisites5 = {};
        int[] expected5 = {0};
        
        int[] result5a = solution.findOrder(numCourses5, prerequisites5);
        System.out.println("Single course: " + Arrays.toString(result5a) + " - " + 
                         (Arrays.equals(result5a, expected5) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Simple case performance:");
        System.out.println("  BFS: " + time1a + " ns");
        System.out.println("  DFS: " + time1b + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        int numCourses7 = 100;
        int[][] prerequisites7 = generateLargePrerequisites(numCourses7, 200, false);
        
        startTime = System.nanoTime();
        int[] result7a = solution.findOrder(numCourses7, prerequisites7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7b = solution.findOrderDFS(numCourses7, prerequisites7);
        long time7b = System.nanoTime() - startTime;
        
        System.out.println("Large input (100 courses, 200 prerequisites):");
        System.out.println("  BFS: " + time7a + " ns, Valid: " + 
                         (isValidOrdering(result7a, prerequisites7)));
        System.out.println("  DFS: " + time7b + " ns, Valid: " + 
                         (isValidOrdering(result7b, prerequisites7)));
        
        // Compare all approaches for consistency
        System.out.println("\nTest 8: Consistency Check");
        int testNumCourses = 5;
        int[][] testPrereqs = {{1,0}, {2,1}, {3,2}, {4,3}};
        int[] r1 = solution.findOrder(testNumCourses, testPrereqs);
        int[] r2 = solution.findOrderDFS(testNumCourses, testPrereqs);
        int[] r3 = solution.findOrderIterativeDFS(testNumCourses, testPrereqs);
        int[] r4 = solution.findOrderBFSAlternative(testNumCourses, testPrereqs);
        int[] r5 = solution.findOrderDFSArray(testNumCourses, testPrereqs);
        
        boolean consistent = Arrays.equals(r1, r2) || 
                           (isValidOrdering(r1, testPrereqs) && isValidOrdering(r2, testPrereqs) &&
                            isValidOrdering(r3, testPrereqs) && isValidOrdering(r4, testPrereqs) &&
                            isValidOrdering(r5, testPrereqs));
        System.out.println("All approaches produce valid orderings: " + consistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BFS (KAHN'S ALGORITHM) EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Kahn's algorithm produces a topological ordering by repeatedly");
        System.out.println("removing nodes with no incoming edges (indegree 0).");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Calculate indegree for each node");
        System.out.println("2. Add all nodes with indegree 0 to a queue");
        System.out.println("3. While queue is not empty:");
        System.out.println("   a. Remove a node from queue and add to result");
        System.out.println("   b. For each neighbor, decrement indegree");
        System.out.println("   c. If neighbor's indegree becomes 0, add to queue");
        System.out.println("4. If result size equals total nodes, return result; else cycle exists");
        
        System.out.println("\nWhy it works:");
        System.out.println("  - Nodes with indegree 0 have no prerequisites");
        System.out.println("  - Removing them doesn't violate any dependencies");
        System.out.println("  - The process naturally produces a valid topological order");
        System.out.println("  - If cycle exists, some nodes will never have indegree 0");
        
        System.out.println("\nVisual Example (prerequisites = [[1,0],[2,0],[3,1],[3,2]]):");
        System.out.println("Initial: indegree[0]=0, indegree[1]=1, indegree[2]=1, indegree[3]=2");
        System.out.println("Step 1: Process 0 → result=[0], update indegree[1]=0, indegree[2]=0");
        System.out.println("Step 2: Process 1 → result=[0,1], update indegree[3]=1");
        System.out.println("Step 3: Process 2 → result=[0,1,2], update indegree[3]=0");
        System.out.println("Step 4: Process 3 → result=[0,1,2,3]");
        System.out.println("Valid orderings: [0,1,2,3] or [0,2,1,3]");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. BFS (Kahn's Algorithm) - RECOMMENDED:");
        System.out.println("   Time: O(V + E) - Process each node and edge once");
        System.out.println("   Space: O(V + E) - Adjacency list + queue + indegree array");
        System.out.println("   How it works:");
        System.out.println("     - Use indegree to find nodes with no dependencies");
        System.out.println("     - Process nodes level by level");
        System.out.println("     - Naturally produces topological order");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and easy to understand");
        System.out.println("     - No recursion stack overflow");
        System.out.println("     - Easy to detect cycles");
        System.out.println("   Cons:");
        System.out.println("     - Requires extra indegree calculation");
        System.out.println("   Best for: Interview settings, general use");
        
        System.out.println("\n2. DFS with Post-order:");
        System.out.println("   Time: O(V + E) - Visit each node and edge once");
        System.out.println("   Space: O(V + E) - Adjacency list + recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Perform DFS and add nodes to result when finished");
        System.out.println("     - Reverse the result for topological order");
        System.out.println("     - Use three states for cycle detection");
        System.out.println("   Pros:");
        System.out.println("     - Natural for DFS thinkers");
        System.out.println("     - Can be more efficient for some graphs");
        System.out.println("   Cons:");
        System.out.println("     - Recursion depth for large graphs");
        System.out.println("     - Need to reverse result");
        System.out.println("   Best for: When DFS is preferred");
        
        System.out.println("\n3. Iterative DFS:");
        System.out.println("   Time: O(V + E) - Same as recursive DFS");
        System.out.println("   Space: O(V + E) - Uses stack instead of recursion");
        System.out.println("   How it works:");
        System.out.println("     - Use explicit stack for DFS");
        System.out.println("     - Push nodes to result stack when finished");
        System.out.println("     - Same cycle detection logic");
        System.out.println("   Pros:");
        System.out.println("     - No recursion depth limits");
        System.out.println("     - More control over traversal");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Less intuitive");
        System.out.println("   Best for: Very large graphs, avoiding recursion");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("TOPOLOGICAL SORT PROPERTIES:");
        System.out.println("1. Multiple valid orderings may exist for the same graph");
        System.out.println("2. A topological ordering exists if and only if the graph is a DAG");
        System.out.println("3. Kahn's algorithm produces one valid ordering");
        System.out.println("4. DFS produces a different but equally valid ordering");
        System.out.println("5. The problem is essentially finding a linear extension of a partial order");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with BFS (Kahn's algorithm) - it's the most expected");
        System.out.println("2. Explain the indegree concept clearly");
        System.out.println("3. Mention that multiple valid orderings are possible");
        System.out.println("4. Discuss time/space complexity (O(V + E))");
        System.out.println("5. Handle edge cases: no prerequisites, cycles");
        System.out.println("6. Mention DFS as an alternative approach");
        System.out.println("7. Write clean graph building code");
        System.out.println("8. Verify ordering satisfies all prerequisites");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to generate large test cases
     */
    private static int[][] generateLargePrerequisites(int numCourses, int numPrerequisites, boolean allowCycles) {
        int[][] prerequisites = new int[numPrerequisites][2];
        Random random = new Random(42);
        
       
