
## Problems/Arrays-Hashing/0261-graph-valid-tree/Solution.java

```java
/**
 * 261. Graph Valid Tree
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given n nodes labeled from 0 to n-1 and a list of undirected edges,
 * write a function to check whether these edges make up a valid tree.
 * 
 * Key Insights:
 * 1. A valid tree must satisfy two conditions:
 *    - The graph is connected (all nodes reachable)
 *    - The graph has exactly n-1 edges (no cycles)
 * 2. If edges >= n, cycle must exist
 * 3. If edges < n-1, graph is disconnected
 * 4. Can be solved with Union Find, DFS, or BFS
 * 
 * Approach (Union Find):
 * 1. Check if number of edges equals n-1
 * 2. Initialize Union Find structure
 * 3. For each edge, check if nodes are already connected
 * 4. If connected during union, cycle detected
 * 5. Finally check if all nodes are connected
 * 
 * Time Complexity: O(n α(n)) where α is inverse Ackermann function
 * Space Complexity: O(n) for Union Find arrays
 * 
 * Tags: Union Find, DFS, BFS, Graph, Tree
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Union Find - RECOMMENDED
     * O(n α(n)) time, O(n) space
     */
    public boolean validTree(int n, int[][] edges) {
        // Check edge count condition
        if (edges.length != n - 1) {
            return false;
        }
        
        UnionFind uf = new UnionFind(n);
        
        for (int[] edge : edges) {
            if (!uf.union(edge[0], edge[1])) {
                return false; // Cycle detected
            }
        }
        
        return uf.getCount() == 1; // Check if all nodes are connected
    }
    
    /**
     * Union Find data structure
     */
    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int count;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;
            
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }
        
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX == rootY) {
                return false; // Already connected, cycle detected
            }
            
            // Union by rank
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            
            count--;
            return true;
        }
        
        public int getCount() {
            return count;
        }
    }
    
    /**
     * Approach 2: DFS with Cycle Detection
     * O(V + E) time, O(V + E) space
     */
    public boolean validTreeDFS(int n, int[][] edges) {
        // Check edge count condition
        if (edges.length != n - 1) {
            return false;
        }
        
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        boolean[] visited = new boolean[n];
        
        // Check for cycles and connectivity
        if (hasCycleDFS(graph, visited, 0, -1)) {
            return false;
        }
        
        // Check if all nodes are visited (graph is connected)
        for (boolean v : visited) {
            if (!v) return false;
        }
        
        return true;
    }
    
    private boolean hasCycleDFS(List<List<Integer>> graph, boolean[] visited, 
                               int node, int parent) {
        visited[node] = true;
        
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                if (hasCycleDFS(graph, visited, neighbor, node)) {
                    return true;
                }
            } else if (neighbor != parent) {
                // If neighbor is visited and not parent, cycle detected
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 3: BFS with Cycle Detection
     * O(V + E) time, O(V + E) space
     */
    public boolean validTreeBFS(int n, int[][] edges) {
        // Check edge count condition
        if (edges.length != n - 1) {
            return false;
        }
        
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            for (int neighbor : graph.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                    queue.offer(neighbor);
                } else if (neighbor != parent[current]) {
                    // Cycle detected
                    return false;
                }
            }
        }
        
        // Check if all nodes are visited
        for (boolean v : visited) {
            if (!v) return false;
        }
        
        return true;
    }
    
    /**
     * Approach 4: Simple Union Find (without rank)
     * O(n log n) time, O(n) space - Simpler but less efficient
     */
    public boolean validTreeSimpleUF(int n, int[][] edges) {
        if (edges.length != n - 1) {
            return false;
        }
        
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        for (int[] edge : edges) {
            int root1 = findSimple(parent, edge[0]);
            int root2 = findSimple(parent, edge[1]);
            
            if (root1 == root2) {
                return false; // Cycle detected
            }
            
            parent[root1] = root2;
        }
        
        return true;
    }
    
    private int findSimple(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]]; // Simple path compression
            x = parent[x];
        }
        return x;
    }
    
    /**
     * Approach 5: DFS with Stack (Iterative)
     * O(V + E) time, O(V + E) space - Non-recursive DFS
     */
    public boolean validTreeIterativeDFS(int n, int[][] edges) {
        if (edges.length != n - 1) {
            return false;
        }
        
        // Build adjacency list
        List<Set<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new HashSet<>());
        }
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        visited[0] = true;
        
        while (!stack.isEmpty()) {
            int current = stack.pop();
            
            for (int neighbor : graph.get(current)) {
                if (visited[neighbor]) {
                    // Remove the edge to avoid false cycle detection
                    graph.get(neighbor).remove(current);
                    continue;
                }
                
                visited[neighbor] = true;
                stack.push(neighbor);
                graph.get(neighbor).remove(current); // Remove reverse edge
            }
        }
        
        // Check if all nodes are visited
        for (boolean v : visited) {
            if (!v) return false;
        }
        
        return true;
    }
    
    /**
     * Helper method to visualize the Union Find process
     */
    private void visualizeUnionFind(int n, int[][] edges) {
        System.out.println("\nGraph Valid Tree - Union Find Visualization:");
        System.out.println("Number of nodes: " + n);
        System.out.println("Edges: " + Arrays.deepToString(edges));
        System.out.println("Edge count check: " + edges.length + " == " + (n-1) + " ? " + 
                         (edges.length == n-1));
        
        if (edges.length != n - 1) {
            System.out.println("FAIL: Invalid edge count for tree");
            return;
        }
        
        UnionFind uf = new UnionFind(n);
        System.out.println("\nInitial state:");
        System.out.println("Parent: " + Arrays.toString(uf.parent));
        System.out.println("Rank: " + Arrays.toString(uf.rank));
        System.out.println("Components: " + uf.getCount());
        
        System.out.println("\nProcessing edges:");
        System.out.println("Edge | Root1 | Root2 | Union? | Parent | Components");
        System.out.println("-----|-------|-------|--------|--------|-----------");
        
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            int root1 = uf.find(edge[0]);
            int root2 = uf.find(edge[1]);
            boolean unionResult = uf.union(edge[0], edge[1]);
            
            System.out.printf("[%d,%d] | %5d | %5d | %6s | %s | %10d%n",
                            edge[0], edge[1], root1, root2, unionResult,
                            Arrays.toString(uf.parent), uf.getCount());
            
            if (!unionResult) {
                System.out.println("CYCLE DETECTED! Not a valid tree.");
                return;
            }
        }
        
        System.out.println("\nFinal check - Components: " + uf.getCount());
        if (uf.getCount() == 1) {
            System.out.println("SUCCESS: Valid tree (connected and acyclic)");
        } else {
            System.out.println("FAIL: Graph is disconnected");
        }
    }
    
    /**
     * Helper method to visualize DFS process
     */
    private void visualizeDFS(int n, int[][] edges) {
        System.out.println("\nGraph Valid Tree - DFS Visualization:");
        System.out.println("Number of nodes: " + n);
        System.out.println("Edges: " + Arrays.deepToString(edges));
        
        if (edges.length != n - 1) {
            System.out.println("FAIL: Invalid edge count for tree");
            return;
        }
        
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        boolean[] visited = new boolean[n];
        System.out.println("\nDFS Traversal:");
        System.out.println("Node | Parent | Action | Visited Nodes");
        System.out.println("-----|--------|--------|---------------");
        
        if (visualizeDFSHelper(graph, visited, 0, -1, 0)) {
            System.out.println("CYCLE DETECTED! Not a valid tree.");
            return;
        }
        
        // Check connectivity
        boolean connected = true;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                connected = false;
                break;
            }
        }
        
        System.out.println("\nConnectivity check: " + connected);
        if (connected) {
            System.out.println("SUCCESS: Valid tree (connected and acyclic)");
        } else {
            System.out.println("FAIL: Graph is disconnected");
        }
    }
    
    private boolean visualizeDFSHelper(List<List<Integer>> graph, boolean[] visited,
                                     int node, int parent, int depth) {
        String indent = "  ".repeat(depth);
        System.out.printf("%s%4d | %6d | Visit  | %s%n", 
                        indent, node, parent, getVisitedString(visited));
        
        visited[node] = true;
        
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                if (visualizeDFSHelper(graph, visited, neighbor, node, depth + 1)) {
                    return true;
                }
            } else if (neighbor != parent) {
                System.out.printf("%s%4d | %6d | CYCLE! | %s%n",
                                indent, node, parent, getVisitedString(visited));
                return true;
            }
        }
        
        System.out.printf("%s%4d | %6d | Return | %s%n",
                        indent, node, parent, getVisitedString(visited));
        return false;
    }
    
    private String getVisitedString(boolean[] visited) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]) {
                sb.append(i).append(" ");
            }
        }
        return sb.toString().trim();
    }
    
    /**
     * Helper method to analyze tree properties
     */
    private void analyzeTreeProperties(int n, int[][] edges) {
        System.out.println("\nTree Properties Analysis:");
        System.out.println("Number of nodes (n): " + n);
        System.out.println("Number of edges: " + edges.length);
        System.out.println("Required edges for tree: n-1 = " + (n-1));
        
        // Check edge count
        if (edges.length != n - 1) {
            if (edges.length > n - 1) {
                System.out.println("TOO MANY EDGES: Must contain cycles");
            } else {
                System.out.println("TOO FEW EDGES: Graph is disconnected");
            }
            return;
        }
        
        // Build graph for connectivity analysis
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        // Check degrees
        System.out.println("\nNode degrees:");
        int sumDegrees = 0;
        for (int i = 0; i < n; i++) {
            int degree = graph.get(i).size();
            sumDegrees += degree;
            System.out.println("  Node " + i + ": degree " + degree);
        }
        
        System.out.println("Sum of degrees: " + sumDegrees + " (should be 2E = " + (2 * edges.length) + ")");
        
        // Check handshaking lemma
        if (sumDegrees != 2 * edges.length) {
            System.out.println("WARNING: Handshaking lemma violated!");
        }
        
        // Check for leaves (nodes with degree 1)
        int leaves = 0;
        for (int i = 0; i < n; i++) {
            if (graph.get(i).size() == 1) {
                leaves++;
            }
        }
        System.out.println("Number of leaves: " + leaves);
        
        if (n > 1 && leaves == 0) {
            System.out.println("WARNING: No leaves found in tree with n > 1");
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Graph Valid Tree Solution:");
        System.out.println("===================================");
        
        // Test case 1: Valid tree
        System.out.println("\nTest 1: Valid tree");
        int n1 = 5;
        int[][] edges1 = {{0,1},{0,2},{0,3},{1,4}};
        boolean expected1 = true;
        
        long startTime = System.nanoTime();
        boolean result1a = solution.validTree(n1, edges1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.validTreeDFS(n1, edges1);
        long time1b = System.nanoTime() - startTime;
        
        boolean test1a = (result1a == expected1);
        boolean test1b = (result1b == expected1);
        
        System.out.println("Union Find: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DFS: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        
        // Visualize the processes
        solution.visualizeUnionFind(n1, edges1);
        solution.visualizeDFS(n1, edges1);
        solution.analyzeTreeProperties(n1, edges1);
        
        // Test case 2: Cycle detected
        System.out.println("\nTest 2: Cycle detected");
        int n2 = 5;
        int[][] edges2 = {{0,1},{1,2},{2,3},{1,3},{1,4}};
        boolean expected2 = false;
        
        boolean result2a = solution.validTree(n2, edges2);
        System.out.println("Cycle case: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Disconnected graph
        System.out.println("\nTest 3: Disconnected graph");
        int n3 = 4;
        int[][] edges3 = {{0,1},{2,3}}; // Two separate components
        boolean expected3 = false;
        
        boolean result3a = solution.validTree(n3, edges3);
        System.out.println("Disconnected: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single node
        System.out.println("\nTest 4: Single node");
        int n4 = 1;
        int[][] edges4 = {};
        boolean expected4 = true;
        
        boolean result4a = solution.validTree(n4, edges4);
        System.out.println("Single node: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Two nodes
        System.out.println("\nTest 5: Two nodes");
        int n5 = 2;
        int[][] edges5 = {{0,1}};
        boolean expected5 = true;
        
        boolean result5a = solution.validTree(n5, edges5);
        System.out.println("Two nodes: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Valid tree performance:");
        System.out.println("  Union Find: " + time1a + " ns");
        System.out.println("  DFS: " + time1b + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        int n7 = 1000;
        int[][] edges7 = generateTreeEdges(n7);
        
        startTime = System.nanoTime();
        boolean result7a = solution.validTree(n7, edges7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result7b = solution.validTreeBFS(n7, edges7);
        long time7b = System.nanoTime() - startTime;
        
        System.out.println("Large input (1000 nodes tree):");
        System.out.println("  Union Find: " + time7a + " ns, Result: " + result7a);
        System.out.println("  BFS: " + time7b + " ns, Result: " + result7b);
        
        // Compare all approaches for consistency
        System.out.println("\nTest 8: Consistency Check");
        int testN = 6;
        int[][] testEdges = {{0,1},{1,2},{2,3},{3,4},{4,5}}; // Linear tree
        boolean r1 = solution.validTree(testN, testEdges);
        boolean r2 = solution.validTreeDFS(testN, testEdges);
        boolean r3 = solution.validTreeBFS(testN, testEdges);
        boolean r4 = solution.validTreeSimpleUF(testN, testEdges);
        boolean r5 = solution.validTreeIterativeDFS(testN, testEdges);
        
        boolean consistent = (r1 == r2) && (r1 == r3) && (r1 == r4) && (r1 == r5);
        System.out.println("All approaches consistent: " + consistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("UNION FIND EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("A valid tree must be:");
        System.out.println("  1. Connected (all nodes in one component)");
        System.out.println("  2. Acyclic (exactly n-1 edges)");
        
        System.out.println("\nUnion Find Approach:");
        System.out.println("  1. Check if edges.length == n-1");
        System.out.println("  2. Initialize Union Find with n components");
        System.out.println("  3. For each edge (u, v):");
        System.out.println("     - If u and v are already connected → CYCLE");
        System.out.println("     - Else union them and reduce component count");
        System.out.println("  4. Check if final component count == 1");
        
        System.out.println("\nWhy it works:");
        System.out.println("  - n-1 edges ensures no extra edges for cycles");
        System.out.println("  - Union Find detects cycles during union operations");
        System.out.println("  - Final component count checks connectivity");
        
        System.out.println("\nVisual Example (n=5, edges=[[0,1],[0,2],[0,3],[1,4]]):");
        System.out.println("Step 1: Union(0,1) → components=4");
        System.out.println("Step 2: Union(0,2) → components=3");
        System.out.println("Step 3: Union(0,3) → components=2");
        System.out.println("Step 4: Union(1,4) → components=1");
        System.out.println("SUCCESS: Connected and acyclic");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Union Find (RECOMMENDED):");
        System.out.println("   Time: O(n α(n)) - Almost linear time");
        System.out.println("   Space: O(n) - Parent and rank arrays");
        System.out.println("   How it works:");
        System.out.println("     - Uses path compression and union by rank");
        System.out.println("     - Efficiently checks connectivity and cycles");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient for this problem");
        System.out.println("     - Handles dynamic connectivity well");
        System.out.println("     - Easy to implement with optimization");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of Union Find");
        System.out.println("   Best for: Interview settings, large graphs");
        
        System.out.println("\n2. DFS with Cycle Detection:");
        System.out.println("   Time: O(V + E) - Visit all nodes and edges");
        System.out.println("   Space: O(V + E) - Adjacency list and visited array");
        System.out.println("   How it works:");
        System.out.println("     - Build adjacency list");
        System.out.println("     - Perform DFS tracking parent nodes");
        System.out.println("     - Detect cycles and check connectivity");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive graph traversal");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Requires building graph structure");
        System.out.println("     - Recursion depth for large graphs");
        System.out.println("   Best for: Small to medium graphs, educational purposes");
        
        System.out.println("\n3. BFS with Cycle Detection:");
        System.out.println("   Time: O(V + E) - Visit all nodes and edges");
        System.out.println("   Space: O(V + E) - Adjacency list and queue");
        System.out.println("   How it works:");
        System.out.println("     - Similar to DFS but uses queue");
        System.out.println("     - Tracks parent nodes to detect cycles");
        System.out.println("   Pros:");
        System.out.println("     - No recursion depth issues");
        System.out.println("     - Level-order traversal");
        System.out.println("   Cons:");
        System.out.println("     - More memory for queue");
        System.out.println("     - Slightly more complex than DFS");
        System.out.println("   Best for: When BFS is preferred");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("TREE PROPERTIES SUMMARY:");
        System.out.println("1. A tree with n nodes has exactly n-1 edges");
        System.out.println("2. A tree is connected (one component)");
        System.out.println("3. A tree is acyclic");
        System.out.println("4. Any two of these properties imply the third");
        System.out.println("5. For undirected graphs: connected + acyclic ⇔ tree");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Union Find - it's the most efficient");
        System.out.println("2. Explain the two tree conditions clearly");
        System.out.println("3. Mention the edge count check first (quick rejection)");
        System.out.println("4. Discuss time/space complexity");
        System.out.println("5. Handle edge cases: n=1, n=2");
        System.out.println("6. Mention DFS/BFS as alternative approaches");
        System.out.println("7. Write clean Union Find implementation");
        System.out.println("8. Test with provided examples");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to generate tree edges for testing
     */
    private static int[][] generateTreeEdges(int n) {
        if (n <= 1) return new int[0][0];
        
        int[][] edges = new int[n-1][2];
        Random random = new Random(42);
        
        // Generate a random tree using sequential connection
        for (int i = 1; i < n; i++) {
            int parent = random.nextInt(i);
            edges[i-1][0] = parent;
            edges[i-1][1] = i;
        }
        
        return edges;
    }
}
