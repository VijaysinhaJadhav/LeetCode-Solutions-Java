
## Solution.java

```java
/**
 * 323. Number of Connected Components in an Undirected Graph
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given n nodes and undirected edges, count the number of connected components.
 * 
 * Key Insights:
 * 1. Multiple approaches: Union Find, DFS, BFS
 * 2. Union Find efficiently tracks connected components
 * 3. DFS/BFS traverse each connected component
 * 4. All isolated nodes count as separate components
 * 
 * Approach (Union Find):
 * 1. Initialize each node as its own parent (n components)
 * 2. For each edge, union the two nodes
 * 3. Count distinct roots in parent array
 * 
 * Time Complexity: O(n + m α(n)) with path compression & union by rank
 * Space Complexity: O(n) for parent and rank arrays
 * 
 * Tags: Graph, DFS, BFS, Union Find
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Union Find (Disjoint Set Union) - RECOMMENDED
     * O(n + m α(n)) time, O(n) space - Optimal for connectivity
     */
    public int countComponents(int n, int[][] edges) {
        // Initialize Union Find structure
        int[] parent = new int[n];
        int[] rank = new int[n];
        
        // Each node starts as its own parent
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        
        int components = n; // Start with n separate components
        
        // Process each edge
        for (int[] edge : edges) {
            int node1 = edge[0];
            int node2 = edge[1];
            
            if (union(node1, node2, parent, rank)) {
                components--; // Components merged
            }
        }
        
        return components;
    }
    
    private int find(int node, int[] parent) {
        // Path compression
        if (parent[node] != node) {
            parent[node] = find(parent[node], parent);
        }
        return parent[node];
    }
    
    private boolean union(int node1, int node2, int[] parent, int[] rank) {
        int root1 = find(node1, parent);
        int root2 = find(node2, parent);
        
        if (root1 == root2) {
            return false; // Already connected
        }
        
        // Union by rank
        if (rank[root1] < rank[root2]) {
            parent[root1] = root2;
        } else if (rank[root1] > rank[root2]) {
            parent[root2] = root1;
        } else {
            parent[root2] = root1;
            rank[root1]++;
        }
        
        return true;
    }
    
    /**
     * Approach 2: Depth-First Search (DFS)
     * O(n + m) time, O(n + m) space - Clean recursive approach
     */
    public int countComponentsDFS(int n, int[][] edges) {
        // Build adjacency list
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u); // Undirected graph
        }
        
        boolean[] visited = new boolean[n];
        int components = 0;
        
        // Traverse all nodes
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                components++;
                dfs(i, graph, visited);
            }
        }
        
        return components;
    }
    
    private void dfs(int node, List<Integer>[] graph, boolean[] visited) {
        visited[node] = true;
        
        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }
    
    /**
     * Approach 3: Breadth-First Search (BFS)
     * O(n + m) time, O(n + m) space - Iterative level-order traversal
     */
    public int countComponentsBFS(int n, int[][] edges) {
        // Build adjacency list
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u); // Undirected graph
        }
        
        boolean[] visited = new boolean[n];
        int components = 0;
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                components++;
                bfs(i, graph, visited);
            }
        }
        
        return components;
    }
    
    private void bfs(int start, List<Integer>[] graph, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            
            for (int neighbor : graph[node]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
    }
    
    /**
     * Approach 4: Union Find without rank (Simpler version)
     * O(n + m log n) time, O(n) space - Simpler but slightly less efficient
     */
    public int countComponentsUnionFindSimple(int n, int[][] edges) {
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        int components = n;
        
        for (int[] edge : edges) {
            int root1 = findSimple(edge[0], parent);
            int root2 = findSimple(edge[1], parent);
            
            if (root1 != root2) {
                parent[root1] = root2;
                components--;
            }
        }
        
        return components;
    }
    
    private int findSimple(int node, int[] parent) {
        while (parent[node] != node) {
            parent[node] = parent[parent[node]]; // Path compression
            node = parent[node];
        }
        return node;
    }
    
    /**
     * Approach 5: DFS Iterative (using stack)
     * O(n + m) time, O(n + m) space - Avoids recursion stack overflow
     */
    public int countComponentsDFSIterative(int n, int[][] edges) {
        // Build adjacency list
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        
        boolean[] visited = new boolean[n];
        int components = 0;
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                components++;
                dfsIterative(i, graph, visited);
            }
        }
        
        return components;
    }
    
    private void dfsIterative(int start, List<Integer>[] graph, boolean[] visited) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        visited[start] = true;
        
        while (!stack.isEmpty()) {
            int node = stack.pop();
            
            for (int neighbor : graph[node]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    stack.push(neighbor);
                }
            }
        }
    }
    
    /**
     * Helper method to visualize the graph and components
     */
    private void visualizeGraph(int n, int[][] edges) {
        System.out.println("\nGraph Visualization:");
        System.out.println("Number of nodes: " + n);
        System.out.println("Number of edges: " + edges.length);
        
        // Build adjacency list for visualization
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        
        System.out.println("\nAdjacency List:");
        for (int i = 0; i < n; i++) {
            System.out.println("Node " + i + " -> " + graph[i]);
        }
        
        // Find and display connected components
        boolean[] visited = new boolean[n];
        List<List<Integer>> components = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfsForVisualization(i, graph, visited, component);
                components.add(component);
            }
        }
        
        System.out.println("\nConnected Components:");
        for (int i = 0; i < components.size(); i++) {
            System.out.println("Component " + (i + 1) + ": " + components.get(i));
        }
        
        System.out.println("Total components: " + components.size());
    }
    
    private void dfsForVisualization(int node, List<Integer>[] graph, boolean[] visited, List<Integer> component) {
        visited[node] = true;
        component.add(node);
        
        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                dfsForVisualization(neighbor, graph, visited, component);
            }
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Number of Connected Components:");
        System.out.println("=======================================");
        
        // Test case 1: Basic example with 2 components
        System.out.println("\nTest 1: Basic example with 2 components");
        int n1 = 5;
        int[][] edges1 = {{0, 1}, {1, 2}, {3, 4}};
        int expected1 = 2;
        
        long startTime = System.nanoTime();
        int result1a = solution.countComponents(n1, edges1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.countComponentsDFS(n1, edges1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.countComponentsBFS(n1, edges1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.countComponentsUnionFindSimple(n1, edges1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.countComponentsDFSIterative(n1, edges1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("Union Find: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DFS: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Union Find Simple: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("DFS Iterative: " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the graph
        solution.visualizeGraph(n1, edges1);
        
        // Test case 2: Single connected component
        System.out.println("\nTest 2: Single connected component");
        int n2 = 5;
        int[][] edges2 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        int expected2 = 1;
        
        int result2a = solution.countComponents(n2, edges2);
        System.out.println("Single component: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: No edges (all isolated nodes)
        System.out.println("\nTest 3: No edges (all isolated nodes)");
        int n3 = 4;
        int[][] edges3 = {};
        int expected3 = 4;
        
        int result3a = solution.countComponents(n3, edges3);
        System.out.println("No edges: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Star configuration
        System.out.println("\nTest 4: Star configuration");
        int n4 = 5;
        int[][] edges4 = {{0, 1}, {0, 2}, {0, 3}, {0, 4}};
        int expected4 = 1;
        
        int result4a = solution.countComponents(n4, edges4);
        System.out.println("Star configuration: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Complex graph with multiple components
        System.out.println("\nTest 5: Complex graph with multiple components");
        int n5 = 8;
        int[][] edges5 = {{0, 1}, {1, 2}, {3, 4}, {4, 5}, {6, 7}};
        int expected5 = 3;
        
        int result5a = solution.countComponents(n5, edges5);
        System.out.println("Complex graph: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Single node
        System.out.println("\nTest 6: Single node");
        int n6 = 1;
        int[][] edges6 = {};
        int expected6 = 1;
        
        int result6a = solution.countComponents(n6, edges6);
        System.out.println("Single node: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("Basic example performance:");
        System.out.println("  Union Find: " + time1a + " ns");
        System.out.println("  DFS: " + time1b + " ns");
        System.out.println("  BFS: " + time1c + " ns");
        System.out.println("  Union Find Simple: " + time1d + " ns");
        System.out.println("  DFS Iterative: " + time1e + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 8: Larger input performance");
        int n8 = 1000;
        int[][] edges8 = generateRandomEdges(n8, 2000);
        
        startTime = System.nanoTime();
        int result8a = solution.countComponents(n8, edges8);
        long time8a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result8b = solution.countComponentsDFS(n8, edges8);
        long time8b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result8c = solution.countComponentsBFS(n8, edges8);
        long time8c = System.nanoTime() - startTime;
        
        System.out.println("Larger input (1000 nodes, 2000 edges):");
        System.out.println("  Union Find: " + time8a + " ns, Components: " + result8a);
        System.out.println("  DFS: " + time8b + " ns, Components: " + result8b);
        System.out.println("  BFS: " + time8c + " ns, Components: " + result8c);
        
        // Verify all approaches produce the same result
        boolean allEqual = (result8a == result8b) && (result8a == result8c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("UNION FIND ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Union Find (Disjoint Set Union) efficiently tracks connected components");
        System.out.println("by maintaining a forest of trees where each tree represents a component.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize: Each node is its own parent (n components)");
        System.out.println("2. For each edge (u, v):");
        System.out.println("   - Find root of u and root of v");
        System.out.println("   - If roots are different, union them (decrease component count)");
        System.out.println("3. Return the final component count");
        
        System.out.println("\nOptimizations:");
        System.out.println("1. Path Compression: Flatten tree during find operations");
        System.out.println("2. Union by Rank: Attach smaller tree to larger tree");
        System.out.println("3. These optimizations give O(α(n)) amortized time per operation");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Union Find (RECOMMENDED):");
        System.out.println("   Time: O(n + m α(n)) - Optimal for connectivity");
        System.out.println("   Space: O(n) - Parent and rank arrays");
        System.out.println("   How it works:");
        System.out.println("     - Maintain parent pointers for each node");
        System.out.println("     - Use path compression and union by rank");
        System.out.println("     - Track component count during union operations");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Naturally counts components");
        System.out.println("     - Efficient for dynamic connectivity");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Requires understanding of DSU");
        System.out.println("   Best for: Connectivity problems, dynamic graphs");
        
        System.out.println("\n2. Depth-First Search (DFS):");
        System.out.println("   Time: O(n + m) - Visit each node and edge once");
        System.out.println("   Space: O(n + m) - Adjacency list and visited array");
        System.out.println("   How it works:");
        System.out.println("     - Build adjacency list from edges");
        System.out.println("     - Use DFS to traverse each connected component");
        System.out.println("     - Count components during traversal");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and easy to understand");
        System.out.println("     - Standard graph traversal approach");
        System.out.println("     - Works well for most cases");
        System.out.println("   Cons:");
        System.out.println("     - Requires building graph representation");
        System.out.println("     - Recursion depth for large components");
        System.out.println("   Best for: Learning, small to medium graphs");
        
        System.out.println("\n3. Breadth-First Search (BFS):");
        System.out.println("   Time: O(n + m) - Visit each node and edge once");
        System.out.println("   Space: O(n + m) - Adjacency list, visited array, and queue");
        System.out.println("   How it works:");
        System.out.println("     - Build adjacency list from edges");
        System.out.println("     - Use BFS to traverse each connected component");
        System.out.println("     - Count components during traversal");
        System.out.println("   Pros:");
        System.out.println("     - Avoids recursion stack overflow");
        System.out.println("     - Level-order traversal");
        System.out.println("     - Standard graph traversal approach");
        System.out.println("   Cons:");
        System.out.println("     - Requires building graph representation");
        System.out.println("     - Queue operations overhead");
        System.out.println("   Best for: Large components, avoiding recursion");
        
        System.out.println("\n4. Union Find (Simple version):");
        System.out.println("   Time: O(n + m log n) - Without union by rank");
        System.out.println("   Space: O(n) - Parent array only");
        System.out.println("   How it works:");
        System.out.println("     - Basic union find without optimizations");
        System.out.println("     - Simple path compression");
        System.out.println("   Pros:");
        System.out.println("     - Simpler implementation");
        System.out.println("     - Good enough for many cases");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Slightly less efficient");
        System.out.println("     - No union by rank optimization");
        System.out.println("   Best for: Learning Union Find, small inputs");
        
        System.out.println("\n5. DFS Iterative:");
        System.out.println("   Time: O(n + m) - Same as recursive DFS");
        System.out.println("   Space: O(n + m) - Adjacency list, visited array, and stack");
        System.out.println("   How it works:");
        System.out.println("     - Use explicit stack instead of recursion");
        System.out.println("     - Avoids recursion depth limits");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack overflow risk");
        System.out.println("     - Explicit control over traversal");
        System.out.println("   Cons:");
        System.out.println("     - More code than recursive version");
        System.out.println("     - Stack operations overhead");
        System.out.println("   Best for: Very large components, stack-limited environments");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Maximum edges in connected graph: n(n-1)/2");
        System.out.println("2. Minimum edges for connectivity: n-1");
        System.out.println("3. Inverse Ackermann function α(n) < 5 for all practical n");
        System.out.println("4. Union Find complexity is essentially linear");
        System.out.println("5. For n=2000, m=5000: O(n + m) ≈ 7000 operations");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Union Find - mention optimizations (path compression, union by rank)");
        System.out.println("2. Explain how it naturally counts components");
        System.out.println("3. Discuss alternative approaches (DFS/BFS) and their trade-offs");
        System.out.println("4. Handle edge cases: no edges, single node, fully connected");
        System.out.println("5. Consider time/space complexity for each approach");
        System.out.println("6. Mention real-world applications (social networks, computer networks)");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper methods for test generation
     */
    private static int[][] generateRandomEdges(int n, int count) {
        Random random = new Random(42);
        Set<String> seen = new HashSet<>();
        List<int[]> result = new ArrayList<>();
        
        while (result.size() < count && result.size() < n * (n - 1) / 2) {
            int a = random.nextInt(n);
            int b = random.nextInt(n);
            if (a != b) {
                String key = Math.min(a, b) + "," + Math.max(a, b);
                if (!seen.contains(key)) {
                    seen.add(key);
                    result.add(new int[]{a, b});
                }
            }
        }
        
        return result.toArray(new int[0][]);
    }
}
