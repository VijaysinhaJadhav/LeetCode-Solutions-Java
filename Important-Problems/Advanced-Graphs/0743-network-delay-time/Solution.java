
# Solution.java

```java
import java.util.*;

/**
 * 743. Network Delay Time
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find the minimum time for a signal to reach all nodes from source node k.
 * Return -1 if some nodes are unreachable.
 * 
 * Key Insights:
 * 1. This is a single-source shortest path problem
 * 2. Dijkstra's algorithm is optimal for non-negative weights
 * 3. Answer is max(dist) among all reachable nodes
 * 4. If any node unreachable, return -1
 */
class Solution {
    
    /**
     * Approach 1: Dijkstra's Algorithm (Recommended)
     * Time: O(E log V), Space: O(V + E)
     * 
     * Standard Dijkstra with min-heap priority queue
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        // Build adjacency list
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            int u = time[0];
            int v = time[1];
            int w = time[2];
            graph.computeIfAbsent(u, x -> new ArrayList<>()).add(new int[]{v, w});
        }
        
        // Distance array (1-based to 0-based conversion)
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        
        // Min-heap: [time, node]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, k});
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int time = curr[0];
            int node = curr[1];
            
            // Skip if we found a better path already
            if (time > dist[node]) continue;
            
            // Process neighbors
            if (graph.containsKey(node)) {
                for (int[] neighbor : graph.get(node)) {
                    int nextNode = neighbor[0];
                    int travelTime = neighbor[1];
                    int newTime = time + travelTime;
                    
                    if (newTime < dist[nextNode]) {
                        dist[nextNode] = newTime;
                        pq.offer(new int[]{newTime, nextNode});
                    }
                }
            }
        }
        
        // Find maximum time among all nodes
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                return -1; // Unreachable node
            }
            maxTime = Math.max(maxTime, dist[i]);
        }
        
        return maxTime;
    }
    
    /**
     * Approach 2: Bellman-Ford Algorithm
     * Time: O(V·E), Space: O(V)
     * 
     * Dynamic programming approach - relax all edges V-1 times
     * Can handle negative weights (though not needed here)
     */
    public int networkDelayTimeBellmanFord(int[][] times, int n, int k) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        
        // Relax edges n-1 times
        for (int i = 1; i < n; i++) {
            boolean updated = false;
            for (int[] time : times) {
                int u = time[0];
                int v = time[1];
                int w = time[2];
                
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    updated = true;
                }
            }
            if (!updated) break; // Early termination
        }
        
        // Find maximum time
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                return -1;
            }
            maxTime = Math.max(maxTime, dist[i]);
        }
        
        return maxTime;
    }
    
    /**
     * Approach 3: Floyd-Warshall Algorithm
     * Time: O(V³), Space: O(V²)
     * 
     * All-pairs shortest path - overkill for single source but works
     * Good for dense graphs
     */
    public int networkDelayTimeFloydWarshall(int[][] times, int n, int k) {
        // Initialize distance matrix
        int[][] dist = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
        }
        
        // Fill edges
        for (int[] time : times) {
            int u = time[0];
            int v = time[1];
            int w = time[2];
            dist[u][v] = w;
        }
        
        // Floyd-Warshall: find shortest paths between all pairs
        for (int m = 1; m <= n; m++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (dist[i][m] != Integer.MAX_VALUE && dist[m][j] != Integer.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][m] + dist[m][j]);
                    }
                }
            }
        }
        
        // Find maximum distance from k to any node
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[k][i] == Integer.MAX_VALUE) {
                return -1;
            }
            maxTime = Math.max(maxTime, dist[k][i]);
        }
        
        return maxTime;
    }
    
    /**
     * Approach 4: BFS with Priority Queue (Dijkstra but without distance array optimization)
     * Time: O(V² log V) worst, Space: O(V + E)
     * 
     * Simpler implementation but may revisit nodes multiple times
     */
    public int networkDelayTimeBFS(int[][] times, int n, int k) {
        // Build adjacency list
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            graph.computeIfAbsent(time[0], x -> new ArrayList<>()).add(
                new int[]{time[1], time[2]});
        }
        
        // Min-heap for BFS
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{k, 0});
        
        // Track visited nodes with their times
        Map<Integer, Integer> visited = new HashMap<>();
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int time = curr[1];
            
            if (visited.containsKey(node)) continue;
            visited.put(node, time);
            
            if (graph.containsKey(node)) {
                for (int[] neighbor : graph.get(node)) {
                    int nextNode = neighbor[0];
                    int travelTime = neighbor[1];
                    if (!visited.containsKey(nextNode)) {
                        pq.offer(new int[]{nextNode, time + travelTime});
                    }
                }
            }
        }
        
        // Check if all nodes reached
        if (visited.size() != n) return -1;
        
        // Find maximum time
        int maxTime = 0;
        for (int t : visited.values()) {
            maxTime = Math.max(maxTime, t);
        }
        return maxTime;
    }
    
    /**
     * Approach 5: DFS with Relaxation (not optimal)
     * Time: O(V!?) worst, Space: O(V)
     * 
     * For completeness only - not recommended
     */
    public int networkDelayTimeDFS(int[][] times, int n, int k) {
        // Build adjacency list
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            graph.computeIfAbsent(time[0], x -> new ArrayList<>()).add(
                new int[]{time[1], time[2]});
        }
        
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        
        dfs(graph, dist, k, 0);
        
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            maxTime = Math.max(maxTime, dist[i]);
        }
        return maxTime;
    }
    
    private void dfs(Map<Integer, List<int[]>> graph, int[] dist, int node, int currentTime) {
        if (currentTime > dist[node]) return;
        dist[node] = currentTime;
        
        if (graph.containsKey(node)) {
            for (int[] neighbor : graph.get(node)) {
                int nextNode = neighbor[0];
                int travelTime = neighbor[1];
                dfs(graph, dist, nextNode, currentTime + travelTime);
            }
        }
    }
    
    /**
     * Helper: Visualize the graph and signal propagation
     */
    public void visualizeGraph(int[][] times, int n, int k) {
        System.out.println("\nNetwork Delay Time Visualization:");
        System.out.println("==================================");
        
        System.out.println("\nGraph structure:");
        System.out.println("Nodes: 1 to " + n);
        System.out.println("Source node: " + k);
        
        // Build adjacency list for display
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            int u = time[0];
            int v = time[1];
            int w = time[2];
            graph.computeIfAbsent(u, x -> new ArrayList<>()).add(new int[]{v, w});
            System.out.printf("  Edge: %d -> %d (time: %d)%n", u, v, w);
        }
        
        // Run Dijkstra
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{k, 0});
        
        System.out.println("\nSignal propagation (Dijkstra):");
        int step = 1;
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int time = curr[1];
            
            if (time > dist[node]) continue;
            
            System.out.printf("Step %d: Node %d reached at time %d%n", step++, node, time);
            
            if (graph.containsKey(node)) {
                for (int[] neighbor : graph.get(node)) {
                    int nextNode = neighbor[0];
                    int travelTime = neighbor[1];
                    int newTime = time + travelTime;
                    
                    if (newTime < dist[nextNode]) {
                        dist[nextNode] = newTime;
                        pq.offer(new int[]{nextNode, newTime});
                        System.out.printf("  → Propagating to node %d (time: %d + %d = %d)%n",
                            nextNode, time, travelTime, newTime);
                    }
                }
            }
        }
        
        System.out.println("\nFinal arrival times:");
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.printf("  Node %d: unreachable%n", i);
            } else {
                System.out.printf("  Node %d: time %d%n", i, dist[i]);
            }
        }
        
        int maxTime = 0;
        boolean allReachable = true;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                allReachable = false;
                break;
            }
            maxTime = Math.max(maxTime, dist[i]);
        }
        
        if (allReachable) {
            System.out.printf("\nResult: All nodes reachable in %d time units%n", maxTime);
        } else {
            System.out.println("\nResult: -1 (some nodes unreachable)");
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            // Example 1
            {
                new int[][]{{2,1,1}, {2,3,1}, {3,4,1}},
                4, 2, 2
            },
            // Example 2
            {
                new int[][]{{1,2,1}},
                2, 1, 1
            },
            // Example 3
            {
                new int[][]{{1,2,1}},
                2, 2, -1
            },
            // Disconnected graph
            {
                new int[][]{{1,2,1}, {3,4,2}},
                4, 1, -1
            },
            // Multiple paths
            {
                new int[][]{{1,2,2}, {1,3,4}, {2,3,1}, {3,4,3}},
                4, 1, 6
            },
            // Single node
            {
                new int[][]{},
                1, 1, 0
            },
            // All nodes connected directly to source
            {
                new int[][]{{1,2,3}, {1,3,5}, {1,4,2}},
                4, 1, 5
            },
            // Complex graph
            {
                new int[][]{
                    {1,2,2}, {1,3,1}, {2,4,3}, {3,4,4}, {2,5,2},
                    {5,6,1}, {4,6,2}, {3,7,5}, {6,7,2}
                },
                7, 1, 7
            }
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        Object[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[][] times = (int[][]) testCases[i][0];
            int n = (int) testCases[i][1];
            int k = (int) testCases[i][2];
            int expected = (int) testCases[i][3];
            
            System.out.printf("\nTest %d:%n", i + 1);
            System.out.println("  Graph: " + times.length + " edges, " + n + " nodes, source = " + k);
            
            int result1 = networkDelayTime(times, n, k);
            int result2 = networkDelayTimeBellmanFord(times, n, k);
            int result3 = networkDelayTimeFloydWarshall(times, n, k);
            int result4 = networkDelayTimeBFS(times, n, k);
            
            boolean allMatch = result1 == expected && result2 == expected &&
                              result3 == expected && result4 == expected;
            
            if (allMatch) {
                System.out.println("✓ PASS - Result: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Dijkstra: " + result1);
                System.out.println("  Bellman-Ford: " + result2);
                System.out.println("  Floyd-Warshall: " + result3);
                System.out.println("  BFS: " + result4);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeGraph(times, n, k);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=======================");
        
        // Generate a larger test case
        int n = 100;
        int m = 6000; // Max edges per constraints
        Random rand = new Random(42);
        
        int[][] times = new int[m][3];
        for (int i = 0; i < m; i++) {
            int u = rand.nextInt(n) + 1;
            int v = rand.nextInt(n) + 1;
            while (v == u) v = rand.nextInt(n) + 1;
            int w = rand.nextInt(101);
            times[i] = new int[]{u, v, w};
        }
        int k = rand.nextInt(n) + 1;
        
        System.out.println("Test Setup:");
        System.out.println("  Nodes: " + n);
        System.out.println("  Edges: " + m);
        System.out.println("  Source: " + k);
        
        long[] times_ms = new long[5];
        int[] results = new int[5];
        
        // Method 1: Dijkstra
        long start = System.currentTimeMillis();
        results[0] = networkDelayTime(times, n, k);
        times_ms[0] = System.currentTimeMillis() - start;
        
        // Method 2: Bellman-Ford
        start = System.currentTimeMillis();
        results[1] = networkDelayTimeBellmanFord(times, n, k);
        times_ms[1] = System.currentTimeMillis() - start;
        
        // Method 3: Floyd-Warshall
        start = System.currentTimeMillis();
        results[2] = networkDelayTimeFloydWarshall(times, n, k);
        times_ms[2] = System.currentTimeMillis() - start;
        
        // Method 4: BFS with PQ
        start = System.currentTimeMillis();
        results[3] = networkDelayTimeBFS(times, n, k);
        times_ms[3] = System.currentTimeMillis() - start;
        
        // Method 5: DFS (only for very small n - skip here)
        times_ms[4] = -1;
        results[4] = results[0];
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Dijkstra               | %9d | %6d%n", times_ms[0], results[0]);
        System.out.printf("2. Bellman-Ford           | %9d | %6d%n", times_ms[1], results[1]);
        System.out.printf("3. Floyd-Warshall         | %9d | %6d%n", times_ms[2], results[2]);
        System.out.printf("4. BFS with PQ            | %9d | %6d%n", times_ms[3], results[3]);
        System.out.printf("5. DFS                    | %9s | %6s%n", "N/A", "N/A");
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] && results[2] == results[3];
        System.out.println("\nAll methods match: " + allMatch);
        
        System.out.println("\nObservations:");
        System.out.println("1. Dijkstra is fastest for sparse graphs");
        System.out.println("2. Bellman-Ford is slower but handles negative weights");
        System.out.println("3. Floyd-Warshall is overkill for single source");
        System.out.println("4. BFS with PQ is similar to Dijkstra");
        System.out.println("5. DFS is exponential and impractical");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Single node:");
        int[][] times1 = {};
        int result1 = networkDelayTime(times1, 1, 1);
        System.out.println("   Graph: no edges, 1 node, source=1");
        System.out.println("   Result: " + result1 + " (expected: 0)");
        
        System.out.println("\n2. Source not in graph (but still a node):");
        int[][] times2 = {{1,2,5}};
        int result2 = networkDelayTime(times2, 3, 3);
        System.out.println("   Graph: [1->2], nodes=3, source=3");
        System.out.println("   Result: " + result2 + " (expected: -1, node 1 unreachable)");
        
        System.out.println("\n3. Graph with zero-weight edges:");
        int[][] times3 = {{1,2,0}, {2,3,0}};
        int result3 = networkDelayTime(times3, 3, 1);
        System.out.println("   Graph: 1->2 (0), 2->3 (0)");
        System.out.println("   Result: " + result3 + " (expected: 0)");
        
        System.out.println("\n4. Disconnected graph:");
        int[][] times4 = {{1,2,1}, {3,4,2}};
        int result4 = networkDelayTime(times4, 4, 1);
        System.out.println("   Graph: 1->2, 3->4, source=1");
        System.out.println("   Result: " + result4 + " (expected: -1, nodes 3,4 unreachable)");
        
        System.out.println("\n5. Graph with multiple paths:");
        int[][] times5 = {{1,2,5}, {1,3,10}, {2,3,2}};
        int result5 = networkDelayTime(times5, 3, 1);
        System.out.println("   Graph: 1->2(5), 1->3(10), 2->3(2)");
        System.out.println("   Result: " + result5 + " (expected: 7, via 1->2->3)");
    }
    
    /**
     * Helper: Explain Dijkstra's algorithm
     */
    public void explainDijkstra() {
        System.out.println("\nDijkstra's Algorithm Explanation:");
        System.out.println("==================================");
        
        System.out.println("\nWhat is Dijkstra's Algorithm?");
        System.out.println("- Greedy algorithm for finding shortest paths from source");
        System.out.println("- Works on graphs with non-negative edge weights");
        System.out.println("- Uses priority queue to always process the node with smallest distance");
        
        System.out.println("\nSteps:");
        System.out.println("1. Initialize distance[source] = 0, all others = infinity");
        System.out.println("2. Add source to min-heap with distance 0");
        System.out.println("3. While heap not empty:");
        System.out.println("   a. Pop node u with smallest distance");
        System.out.println("   b. If distance is outdated, skip");
        System.out.println("   c. For each neighbor v of u:");
        System.out.println("        newDist = dist[u] + weight(u,v)");
        System.out.println("        if newDist < dist[v]: update and push to heap");
        
        System.out.println("\nWhy it works:");
        System.out.println("- When we pop node u, we have found the shortest path to u");
        System.out.println("- All remaining nodes have distance ≥ dist[u]");
        System.out.println("- Greedy choice is optimal due to non-negative weights");
        
        System.out.println("\nTime Complexity: O(E log V) with binary heap");
        System.out.println("Space Complexity: O(V + E)");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify the problem:");
        System.out.println("   - Directed graph? (yes)");
        System.out.println("   - Edge weights? (non-negative)");
        System.out.println("   - What to return? (max shortest path or -1)");
        
        System.out.println("\n2. Recognize the pattern:");
        System.out.println("   - Single-source shortest path problem");
        System.out.println("   - Multiple algorithms possible");
        
        System.out.println("\n3. Start with brute force:");
        System.out.println("   - DFS from source, try all paths? Exponential!");
        System.out.println("   - Need optimized approach");
        
        System.out.println("\n4. Propose Dijkstra:");
        System.out.println("   - Explain why it's suitable (non-negative weights)");
        System.out.println("   - Walk through example");
        System.out.println("   - Mention time/space complexity");
        
        System.out.println("\n5. Discuss alternatives:");
        System.out.println("   - Bellman-Ford: handles negative weights, slower");
        System.out.println("   - Floyd-Warshall: all-pairs, overkill");
        System.out.println("   - BFS: only for unweighted graphs");
        
        System.out.println("\n6. Implementation details:");
        System.out.println("   - Use adjacency list for graph representation");
        System.out.println("   - PriorityQueue for min-heap");
        System.out.println("   - Distance array for tracking");
        System.out.println("   - Handle unreachable nodes");
        
        System.out.println("\n7. Edge cases:");
        System.out.println("   - Single node");
        System.out.println("   - Disconnected graph");
        System.out.println("   - Multiple paths");
        System.out.println("   - Zero-weight edges");
        
        System.out.println("\n8. Complexity analysis:");
        System.out.println("   - Time: O(E log V) for Dijkstra");
        System.out.println("   - Space: O(V + E)");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("743. Network Delay Time");
        System.out.println("=======================");
        
        // Explain Dijkstra
        solution.explainDijkstra();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation (Dijkstra):");
        System.out.println("""
class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // Build adjacency list
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            graph.computeIfAbsent(time[0], x -> new ArrayList<>())
                 .add(new int[]{time[1], time[2]});
        }
        
        // Distance array
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        
        // Min-heap: [time, node]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, k});
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int time = curr[0];
            int node = curr[1];
            
            if (time > dist[node]) continue;
            
            if (graph.containsKey(node)) {
                for (int[] neighbor : graph.get(node)) {
                    int next = neighbor[0];
                    int travel = neighbor[1];
                    int newTime = time + travel;
                    
                    if (newTime < dist[next]) {
                        dist[next] = newTime;
                        pq.offer(new int[]{newTime, next});
                    }
                }
            }
        }
        
        // Find max distance
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            maxTime = Math.max(maxTime, dist[i]);
        }
        
        return maxTime;
    }
}
            """);
        
        System.out.println("\nAlternative Implementation (Bellman-Ford):");
        System.out.println("""
class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        
        // Relax edges n-1 times
        for (int i = 1; i < n; i++) {
            boolean updated = false;
            for (int[] time : times) {
                int u = time[0];
                int v = time[1];
                int w = time[2];
                
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    updated = true;
                }
            }
            if (!updated) break;
        }
        
        // Find max distance
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            maxTime = Math.max(maxTime, dist[i]);
        }
        
        return maxTime;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Dijkstra is optimal for this problem");
        System.out.println("2. Build adjacency list for efficient graph traversal");
        System.out.println("3. Use PriorityQueue for min-heap implementation");
        System.out.println("4. Track distances to all nodes, return max if all reachable");
        System.out.println("5. If any node unreachable, return -1");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(E log V) with Dijkstra");
        System.out.println("- Space: O(V + E) for graph representation");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle negative weights? (Bellman-Ford)");
        System.out.println("2. How to find actual path, not just time? (track predecessors)");
        System.out.println("3. How to handle multiple signal sources? (multi-source Dijkstra)");
        System.out.println("4. How would you parallelize the solution?");
        System.out.println("5. What if edges are bidirectional? (undirected graph)");
    }
}
