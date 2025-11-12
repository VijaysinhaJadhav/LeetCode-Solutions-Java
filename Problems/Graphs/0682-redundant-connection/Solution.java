
## Solution.java

```java
/**
 * 684. Redundant Connection
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given edges of an undirected graph that formed a tree with one extra edge,
 * find the edge that can be removed to restore the tree structure.
 * If multiple answers exist, return the one that appears last.
 * 
 * Key Insights:
 * 1. A tree has n-1 edges and no cycles
 * 2. With n edges, there's exactly one cycle
 * 3. Union Find naturally detects cycles during union operations
 * 4. The last edge that connects already-connected nodes is the answer
 * 
 * Approach (Union Find):
 * 1. Initialize Union Find structure
 * 2. Process edges in order
 * 3. If union operation fails (nodes already connected), that edge creates a cycle
 * 4. Return the last such edge
 * 
 * Time Complexity: O(n α(n)) with path compression & union by rank
 * Space Complexity: O(n) for parent and rank arrays
 * 
 * Tags: Graph, Union Find, DFS
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Union Find (Disjoint Set Union) - RECOMMENDED
     * O(n α(n)) time, O(n) space - Optimal for cycle detection
     */
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1]; // Nodes are 1-indexed
        int[] rank = new int[n + 1];
        
        // Initialize Union Find
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        
        // Process each edge
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            
            if (!union(u, v, parent, rank)) {
                // If union fails, these nodes are already connected
                // This edge creates a cycle - return it
                return edge;
            }
        }
        
        // Should not reach here given problem constraints
        return new int[0];
    }
    
    private int find(int node, int[] parent) {
        // Path compression
        if (parent[node] != node) {
            parent[node] = find(parent[node], parent);
        }
        return parent[node];
    }
    
    private boolean union(int u, int v, int[] parent, int[] rank) {
        int rootU = find(u, parent);
        int rootV = find(v, parent);
        
        if (rootU == rootV) {
            return false; // Already connected - cycle detected
        }
        
        // Union by rank
        if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }
        
        return true;
    }
    
    /**
     * Approach 2: DFS Cycle Detection
     * O(n^2) time, O(n + m) space - Clean but less efficient
     */
    public int[] findRedundantConnectionDFS(int[][] edges) {
        int n = edges.length;
        
        // Build adjacency list
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        // Process edges and check for cycles
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            
            // Add the edge temporarily
            graph[u].add(v);
            graph[v].add(u);
            
            // Check if this edge creates a cycle
            if (hasCycle(u, -1, graph, new boolean[n + 1])) {
                return edge;
            }
        }
        
        return new int[0];
    }
    
    private boolean hasCycle(int node, int parent, List<Integer>[] graph, boolean[] visited) {
        visited[node] = true;
        
        for (int neighbor : graph[node]) {
            if (neighbor == parent) {
                continue; // Skip the edge we came from
            }
            
            if (visited[neighbor] || hasCycle(neighbor, node, graph, visited)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 3: Union Find without rank (Simpler version)
     * O(n log n) time, O(n) space - Simpler but slightly less efficient
     */
    public int[] findRedundantConnectionUnionFindSimple(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];
        
        // Initialize
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            
            int rootU = findSimple(u, parent);
            int rootV = findSimple(v, parent);
            
            if (rootU == rootV) {
                return edge; // Cycle detected
            }
            
            parent[rootU] = rootV;
        }
        
        return new int[0];
    }
    
    private int findSimple(int node, int[] parent) {
        while (parent[node] != node) {
            parent[node] = parent[parent[node]]; // Path compression
            node = parent[node];
        }
        return node;
    }
    
    /**
     * Approach 4: DFS with early termination
     * O(n^2) time, O(n + m) space - Optimized DFS version
     */
    public int[] findRedundantConnectionDFS2(int[][] edges) {
        int n = edges.length;
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            
            // Check if u and v are already connected (would create cycle)
            if (isConnected(u, v, graph, new boolean[n + 1])) {
                return edge;
            }
            
            // Add the edge
            graph[u].add(v);
            graph[v].add(u);
        }
        
        return new int[0];
    }
    
    private boolean isConnected(int u, int v, List<Integer>[] graph, boolean[] visited) {
        if (u == v) return true;
        
        visited[u] = true;
        for (int neighbor : graph[u]) {
            if (!visited[neighbor] && isConnected(neighbor, v, graph, visited)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Approach 5: BFS Cycle Detection
     * O(n^2) time, O(n + m) space - Alternative to DFS
     */
    public int[] findRedundantConnectionBFS(int[][] edges) {
        int n = edges.length;
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            
            // Check connectivity using BFS
            if (bfs(u, v, graph)) {
                return edge;
            }
            
            graph[u].add(v);
            graph[v].add(u);
        }
        
        return new int[0];
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
     * Helper method to visualize the graph and cycle detection process
     */
    private void visualizeGraph(int[][] edges) {
        System.out.println("\nGraph Visualization:");
        System.out.println("Number of edges: " + edges.length);
        System.out.println("Number of nodes: " + edges.length); // n edges for n nodes
        
        // Build adjacency list for visualization
        int n = edges.length;
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        System.out.println("\nEdges in order:");
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            System.out.println("Edge " + (i + 1) + ": " + u + " - " + v);
            
            graph[u].add(v);
            graph[v].add(u);
        }
        
        System.out.println("\nFinal Adjacency List:");
        for (int i = 1; i <= n; i++) {
            System.out.println("Node " + i + " -> " + graph[i]);
        }
        
        // Find and display the cycle
        int[] cycleEdge = findRedundantConnection(edges);
        System.out.println("\nRedundant edge (creates cycle): [" + cycleEdge[0] + ", " + cycleEdge[1] + "]");
        
        // Show what the tree would look like without this edge
        System.out.println("\nTree without redundant edge:");
        List<int[]> treeEdges = new ArrayList<>();
        for (int[] edge : edges) {
            if (!(edge[0] == cycleEdge[0] && edge[1] == cycleEdge[1])) {
                treeEdges.add(edge);
            }
        }
        
        for (int[] edge : treeEdges) {
            System.out.println("  " + edge[0] + " - " + edge[1]);
        }
    }
    
    /**
     * Helper method to visualize Union Find process
     */
    private void visualizeUnionFind(int[][] edges) {
        System.out.println("\nUnion Find Process Visualization:");
        int n = edges.length;
        int[] parent = new int[n + 1];
        int[] rank = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        
        System.out.println("Step | Edge  | Parent Array (1-" + n + ") | Action");
        System.out.println("-----|-------|--------------------------|--------");
        
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            int u = edge[0];
            int v = edge[1];
            
            int rootU = find(u, parent);
            int rootV = find(v, parent);
            
            String action;
            if (rootU == rootV) {
                action = "CYCLE DETECTED - Return this edge";
            } else {
                // Perform union
                if (rank[rootU] < rank[rootV]) {
                    parent[rootU] = rootV;
                    action = "Union: " + rootU + " -> " + rootV;
                } else if (rank[rootU] > rank[rootV]) {
                    parent[rootV] = rootU;
                    action = "Union: " + rootV + " -> " + rootU;
                } else {
                    parent[rootV] = rootU;
                    rank[rootU]++;
                    action = "Union: " + rootV + " -> " + rootU + " (rank++)";
                }
            }
            
            // Print current state
            System.out.printf("%4d | [%d,%d] | ", i + 1, u, v);
            for (int j = 1; j <= n; j++) {
                System.out.print(parent[j] + " ");
            }
            System.out.println("| " + action);
            
            if (rootU == rootV) {
                System.out.println("\nRESULT: Redundant connection is [" + u + ", " + v + "]");
                break;
            }
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Redundant Connection:");
        System.out.println("==============================");
        
        // Test case 1: Basic triangle cycle
        System.out.println("\nTest 1: Basic triangle cycle");
        int[][] edges1 = {{1, 2}, {1, 3}, {2, 3}};
        int[] expected1 = {2, 3};
        
        long startTime = System.nanoTime();
        int[] result1a = solution.findRedundantConnection(edges1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1b = solution.findRedundantConnectionDFS(edges1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1c = solution.findRedundantConnectionUnionFindSimple(edges1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1d = solution.findRedundantConnectionDFS2(edges1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1e = solution.findRedundantConnectionBFS(edges1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = Arrays.equals(result1a, expected1);
        boolean test1b = Arrays.equals(result1b, expected1);
        boolean test1c = Arrays.equals(result1c, expected1);
        boolean test1d = Arrays.equals(result1d, expected1);
        boolean test1e = Arrays.equals(result1e, expected1);
        
        System.out.println("Union Find: " + Arrays.toString(result1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DFS: " + Arrays.toString(result1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Union Find Simple: " + Arrays.toString(result1c) + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("DFS2: " + Arrays.toString(result1d) + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + Arrays.toString(result1e) + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the graph and Union Find process
        solution.visualizeGraph(edges1);
        solution.visualizeUnionFind(edges1);
        
        // Test case 2: Square with diagonal
        System.out.println("\nTest 2: Square with diagonal");
        int[][] edges2 = {{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}};
        int[] expected2 = {1, 4};
        
        int[] result2a = solution.findRedundantConnection(edges2);
        System.out.println("Square with diagonal: " + Arrays.toString(result2a) + " - " + 
                         (Arrays.equals(result2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Multiple possible answers, return last
        System.out.println("\nTest 3: Multiple cycles, return last");
        int[][] edges3 = {{1, 2}, {2, 3}, {3, 4}, {1, 4}, {2, 4}};
        int[] expected3 = {2, 4}; // Last edge that creates a cycle
        
        int[] result3a = solution.findRedundantConnection(edges3);
        System.out.println("Multiple cycles: " + Arrays.toString(result3a) + " - " + 
                         (Arrays.equals(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Larger cycle
        System.out.println("\nTest 4: Larger cycle");
        int[][] edges4 = {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 1}};
        int[] expected4 = {5, 1};
        
        int[] result4a = solution.findRedundantConnection(edges4);
        System.out.println("Larger cycle: " + Arrays.toString(result4a) + " - " + 
                         (Arrays.equals(result4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Chain with loop at end
        System.out.println("\nTest 5: Chain with loop at end");
        int[][] edges5 = {{1, 2}, {2, 3}, {3, 4}, {4, 2}};
        int[] expected5 = {4, 2};
        
        int[] result5a = solution.findRedundantConnection(edges5);
        System.out.println("Chain with loop: " + Arrays.toString(result5a) + " - " + 
                         (Arrays.equals(result5a, expected5) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Basic example performance:");
        System.out.println("  Union Find: " + time1a + " ns");
        System.out.println("  DFS: " + time1b + " ns");
        System.out.println("  Union Find Simple: " + time1c + " ns");
        System.out.println("  DFS2: " + time1d + " ns");
        System.out.println("  BFS: " + time1e + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        int[][] edges7 = generateLargerTest(100);
        
        startTime = System.nanoTime();
        int[] result7a = solution.findRedundantConnection(edges7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7b = solution.findRedundantConnectionDFS(edges7);
        long time7b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7c = solution.findRedundantConnectionUnionFindSimple(edges7);
        long time7c = System.nanoTime() - startTime;
        
        System.out.println("Larger input (100 nodes):");
        System.out.println("  Union Find: " + time7a + " ns, Result: " + Arrays.toString(result7a));
        System.out.println("  DFS: " + time7b + " ns, Result: " + Arrays.toString(result7b));
        System.out.println("  Union Find Simple: " + time7c + " ns, Result: " + Arrays.toString(result7c));
        
        // Verify all approaches produce the same result
        boolean allEqual = Arrays.equals(result7a, result7b) && Arrays.equals(result7a, result7c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("UNION FIND ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("A tree has exactly n-1 edges and no cycles.");
        System.out.println("With n edges, there's exactly one cycle.");
        System.out.println("Union Find naturally detects when we try to connect already-connected nodes.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize each node as its own component");
        System.out.println("2. Process edges in the given order");
        System.out.println("3. For each edge (u, v):");
        System.out.println("   - Find roots of u and v");
        System.out.println("   - If roots are same: CYCLE DETECTED - return this edge");
        System.out.println("   - Else: Union the components");
        System.out.println("4. Return the last edge that caused a cycle");
        
        System.out.println("\nWhy it returns the correct edge:");
        System.out.println("- The problem asks for the LAST edge in input that creates a cycle");
        System.out.println("- Union Find processes edges in order");
        System.out.println("- The first cycle-causing edge we encounter (in order) is returned");
        System.out.println("- This automatically satisfies the 'last occurrence' requirement");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Union Find (RECOMMENDED):");
        System.out.println("   Time: O(n α(n)) - Optimal for cycle detection");
        System.out.println("   Space: O(n) - Parent and rank arrays");
        System.out.println("   How it works:");
        System.out.println("     - Track connected components using parent pointers");
        System.out.println("     - Detect cycle when union operation fails");
        System.out.println("     - Return the edge that caused the cycle");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Simple implementation");
        System.out.println("     - Naturally handles the 'last occurrence' requirement");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of DSU");
        System.out.println("   Best for: Cycle detection in undirected graphs");
        
        System.out.println("\n2. Depth-First Search (DFS):");
        System.out.println("   Time: O(n^2) - For each edge, potentially traverse entire graph");
        System.out.println("   Space: O(n + m) - Adjacency list and visited array");
        System.out.println("   How it works:");
        System.out.println("     - Build graph incrementally");
        System.out.println("     - For each new edge, check if nodes are already connected");
        System.out.println("     - If connected, this edge creates a cycle");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive cycle detection");
        System.out.println("     - Standard graph approach");
        System.out.println("   Cons:");
        System.out.println("     - O(n^2) time complexity");
        System.out.println("     - Less efficient for larger graphs");
        System.out.println("   Best for: Learning, small graphs");
        
        System.out.println("\n3. Union Find (Simple version):");
        System.out.println("   Time: O(n log n) - Without union by rank");
        System.out.println("   Space: O(n) - Parent array only");
        System.out.println("   How it works:");
        System.out.println("     - Basic union find without rank optimization");
        System.out.println("     - Still uses path compression");
        System.out.println("   Pros:");
        System.out.println("     - Simpler implementation");
        System.out.println("     - Good enough for most cases");
        System.out.println("   Cons:");
        System.out.println("     - Slightly less efficient");
        System.out.println("   Best for: Learning Union Find concepts");
        
        System.out.println("\n4. DFS with Early Check:");
        System.out.println("   Time: O(n^2) - Check connectivity for each new edge");
        System.out.println("   Space: O(n + m) - Graph representation");
        System.out.println("   How it works:");
        System.out.println("     - Before adding edge, check if nodes are connected");
        System.out.println("     - If connected, edge creates cycle");
        System.out.println("   Pros:");
        System.out.println("     - Clear logic");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Inefficient for large graphs");
        System.out.println("   Best for: Conceptual understanding");
        
        System.out.println("\n5. BFS Cycle Detection:");
        System.out.println("   Time: O(n^2) - BFS for each new edge");
        System.out.println("   Space: O(n + m) - Graph and queue");
        System.out.println("   How it works:");
        System.out.println("     - Similar to DFS but uses queue");
        System.out.println("     - Check connectivity before adding edge");
        System.out.println("   Pros:");
        System.out.println("     - Avoids recursion depth issues");
        System.out.println("     - Level-order traversal");
        System.out.println("   Cons:");
        System.out.println("     - Same inefficiency as DFS");
        System.out.println("   Best for: When avoiding recursion is important");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Tree properties: n nodes, n-1 edges, no cycles");
        System.out.println("2. With n edges: exactly one cycle exists");
        System.out.println("3. The cycle contains the redundant edge");
        System.out.println("4. Union Find amortized complexity: O(α(n)) per operation");
        System.out.println("5. For n=1000: α(n) < 5, making Union Find very efficient");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Union Find - it's the optimal solution");
        System.out.println("2. Explain why it naturally detects cycles");
        System.out.println("3. Mention path compression and union by rank optimizations");
        System.out.println("4. Discuss how it handles the 'last occurrence' requirement");
        System.out.println("5. Consider alternative approaches and their trade-offs");
        System.out.println("6. Handle edge cases: smallest graph (3 nodes), multiple cycles");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to generate larger test cases
     */
    private static int[][] generateLargerTest(int n) {
        List<int[]> edges = new ArrayList<>();
        Random random = new Random(42);
        
        // Create a tree first (n-1 edges)
        for (int i = 2; i <= n; i++) {
            int parent = random.nextInt(i - 1) + 1;
            edges.add(new int[]{parent, i});
        }
        
        // Add one extra edge to create a cycle
        int u = random.nextInt(n) + 1;
        int v = random.nextInt(n) + 1;
        while (u == v) {
            v = random.nextInt(n) + 1;
        }
        edges.add(new int[]{u, v});
        
        // Shuffle the edges
        Collections.shuffle(edges, new Random(42));
        
        return edges.toArray(new int[0][]);
    }
}
