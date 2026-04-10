
# Solution.java

```java
import java.util.*;

/**
 * 2097. Valid Arrangement of Pairs
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Arrange pairs so that end of one pair equals start of next pair.
 * This is equivalent to finding an Eulerian path in a directed graph.
 * 
 * Key Insights:
 * 1. Treat each pair as a directed edge (start -> end)
 * 2. Use Hierholzer's algorithm to find Eulerian path
 * 3. Track in-degree and out-degree to find start node
 * 4. DFS post-order builds path in reverse
 */
class Solution {
    
    /**
     * Approach 1: Hierholzer's Algorithm (Recursive) - RECOMMENDED
     * Time: O(E), Space: O(V + E)
     * 
     * Steps:
     * 1. Build graph: Map<start, List<end>>
     * 2. Track in-degree and out-degree for each node
     * 3. Find start node (where out-degree > in-degree, or any node)
     * 4. DFS to build path (post-order)
     * 5. Reverse path to get correct order
     */
    public int[][] validArrangement(int[][] pairs) {
        // Build graph and track degrees
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Integer> outDegree = new HashMap<>();
        
        for (int[] pair : pairs) {
            int u = pair[0];
            int v = pair[1];
            
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            outDegree.put(u, outDegree.getOrDefault(u, 0) + 1);
            inDegree.put(v, inDegree.getOrDefault(v, 0) + 1);
        }
        
        // Find start node
        int start = pairs[0][0]; // Default
        for (int node : graph.keySet()) {
            int out = outDegree.getOrDefault(node, 0);
            int in = inDegree.getOrDefault(node, 0);
            if (out - in == 1) {
                start = node;
                break;
            }
        }
        
        // DFS to find Eulerian path
        List<Integer> path = new ArrayList<>();
        dfs(start, graph, path);
        
        // Reverse path to get correct order
        Collections.reverse(path);
        
        // Convert to pairs
        int[][] result = new int[path.size() - 1][2];
        for (int i = 0; i < path.size() - 1; i++) {
            result[i][0] = path.get(i);
            result[i][1] = path.get(i + 1);
        }
        
        return result;
    }
    
    private void dfs(int node, Map<Integer, List<Integer>> graph, List<Integer> path) {
        List<Integer> neighbors = graph.get(node);
        while (neighbors != null && !neighbors.isEmpty()) {
            int next = neighbors.remove(neighbors.size() - 1); // Remove last edge
            dfs(next, graph, path);
        }
        path.add(node);
    }
    
    /**
     * Approach 2: Iterative Stack (Non-Recursive)
     * Time: O(E), Space: O(V + E)
     * 
     * Avoids recursion stack overflow for large graphs
     */
    public int[][] validArrangementIterative(int[][] pairs) {
        // Build graph and track degrees
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Integer> outDegree = new HashMap<>();
        
        for (int[] pair : pairs) {
            int u = pair[0];
            int v = pair[1];
            
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            outDegree.put(u, outDegree.getOrDefault(u, 0) + 1);
            inDegree.put(v, inDegree.getOrDefault(v, 0) + 1);
        }
        
        // Find start node
        int start = pairs[0][0];
        for (int node : graph.keySet()) {
            int out = outDegree.getOrDefault(node, 0);
            int in = inDegree.getOrDefault(node, 0);
            if (out - in == 1) {
                start = node;
                break;
            }
        }
        
        // Iterative DFS using stack
        Deque<Integer> stack = new ArrayDeque<>();
        List<Integer> path = new ArrayList<>();
        stack.push(start);
        
        while (!stack.isEmpty()) {
            int node = stack.peek();
            List<Integer> neighbors = graph.get(node);
            
            if (neighbors != null && !neighbors.isEmpty()) {
                int next = neighbors.remove(neighbors.size() - 1);
                stack.push(next);
            } else {
                path.add(stack.pop());
            }
        }
        
        // Reverse path
        Collections.reverse(path);
        
        // Convert to pairs
        int[][] result = new int[path.size() - 1][2];
        for (int i = 0; i < path.size() - 1; i++) {
            result[i][0] = path.get(i);
            result[i][1] = path.get(i + 1);
        }
        
        return result;
    }
    
    /**
     * Approach 3: Using Custom Graph Node Class
     * Time: O(E), Space: O(V + E)
     * 
     * More object-oriented approach
     */
    class GraphNode {
        int val;
        List<Integer> neighbors;
        
        GraphNode(int val) {
            this.val = val;
            this.neighbors = new ArrayList<>();
        }
    }
    
    public int[][] validArrangementOOP(int[][] pairs) {
        Map<Integer, GraphNode> nodes = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Integer> outDegree = new HashMap<>();
        
        for (int[] pair : pairs) {
            int u = pair[0];
            int v = pair[1];
            
            nodes.putIfAbsent(u, new GraphNode(u));
            nodes.putIfAbsent(v, new GraphNode(v));
            nodes.get(u).neighbors.add(v);
            
            outDegree.put(u, outDegree.getOrDefault(u, 0) + 1);
            inDegree.put(v, inDegree.getOrDefault(v, 0) + 1);
        }
        
        // Find start
        int start = pairs[0][0];
        for (int node : nodes.keySet()) {
            int out = outDegree.getOrDefault(node, 0);
            int in = inDegree.getOrDefault(node, 0);
            if (out - in == 1) {
                start = node;
                break;
            }
        }
        
        // DFS
        List<Integer> path = new ArrayList<>();
        dfsOOP(start, nodes, path);
        
        Collections.reverse(path);
        
        int[][] result = new int[path.size() - 1][2];
        for (int i = 0; i < path.size() - 1; i++) {
            result[i][0] = path.get(i);
            result[i][1] = path.get(i + 1);
        }
        
        return result;
    }
    
    private void dfsOOP(int node, Map<Integer, GraphNode> nodes, List<Integer> path) {
        GraphNode gn = nodes.get(node);
        while (gn != null && !gn.neighbors.isEmpty()) {
            int next = gn.neighbors.remove(gn.neighbors.size() - 1);
            dfsOOP(next, nodes, path);
        }
        path.add(node);
    }
    
    /**
     * Approach 4: Using PriorityQueue for Neighbors (Alternative)
     * Time: O(E log E), Space: O(V + E)
     * 
     * Uses PriorityQueue for automatic sorting (not needed but optional)
     */
    public int[][] validArrangementPQ(int[][] pairs) {
        Map<Integer, PriorityQueue<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Integer> outDegree = new HashMap<>();
        
        for (int[] pair : pairs) {
            int u = pair[0];
            int v = pair[1];
            
            graph.computeIfAbsent(u, k -> new PriorityQueue<>()).add(v);
            outDegree.put(u, outDegree.getOrDefault(u, 0) + 1);
            inDegree.put(v, inDegree.getOrDefault(v, 0) + 1);
        }
        
        // Find start
        int start = pairs[0][0];
        for (int node : graph.keySet()) {
            int out = outDegree.getOrDefault(node, 0);
            int in = inDegree.getOrDefault(node, 0);
            if (out - in == 1) {
                start = node;
                break;
            }
        }
        
        // DFS
        List<Integer> path = new ArrayList<>();
        dfsPQ(start, graph, path);
        
        Collections.reverse(path);
        
        int[][] result = new int[path.size() - 1][2];
        for (int i = 0; i < path.size() - 1; i++) {
            result[i][0] = path.get(i);
            result[i][1] = path.get(i + 1);
        }
        
        return result;
    }
    
    private void dfsPQ(int node, Map<Integer, PriorityQueue<Integer>> graph, List<Integer> path) {
        PriorityQueue<Integer> neighbors = graph.get(node);
        while (neighbors != null && !neighbors.isEmpty()) {
            int next = neighbors.poll();
            dfsPQ(next, graph, path);
        }
        path.add(node);
    }
    
    /**
     * Helper: Visualize graph and Eulerian path
     */
    public void visualizeGraph(int[][] pairs) {
        System.out.println("\nGraph Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nInput pairs: " + Arrays.deepToString(pairs));
        
        // Build graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Integer> outDegree = new HashMap<>();
        
        for (int[] pair : pairs) {
            int u = pair[0];
            int v = pair[1];
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            outDegree.put(u, outDegree.getOrDefault(u, 0) + 1);
            inDegree.put(v, inDegree.getOrDefault(v, 0) + 1);
        }
        
        System.out.println("\nGraph edges:");
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            System.out.printf("  %d -> %s%n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("\nDegrees:");
        for (int node : graph.keySet()) {
            int out = outDegree.getOrDefault(node, 0);
            int in = inDegree.getOrDefault(node, 0);
            System.out.printf("  Node %d: out=%d, in=%d, diff=%d%n", node, out, in, out - in);
        }
        
        // Find start node
        int start = pairs[0][0];
        for (int node : graph.keySet()) {
            int out = outDegree.getOrDefault(node, 0);
            int in = inDegree.getOrDefault(node, 0);
            if (out - in == 1) {
                start = node;
                break;
            }
        }
        System.out.println("\nStart node: " + start);
        
        // Find Eulerian path
        System.out.println("\nBuilding Eulerian path using Hierholzer's algorithm:");
        
        // Clone graph for visualization
        Map<Integer, List<Integer>> graphCopy = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            graphCopy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        
        List<Integer> path = new ArrayList<>();
        dfs(start, graphCopy, path);
        Collections.reverse(path);
        
        System.out.println("\nPath: " + path);
        System.out.println("\nArrangement:");
        for (int i = 0; i < path.size() - 1; i++) {
            System.out.printf("  [%d, %d]%n", path.get(i), path.get(i + 1));
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][][] generateTestCases() {
        return new int[][][] {
            {{5,1},{4,5},{11,9},{9,4}},
            {{1,3},{3,2},{2,1}},
            {{1,2},{1,3},{2,1}},
            {{1,2},{2,3},{3,1}},
            {{1,2},{2,1},{1,3},{3,4},{4,1}},
            {{0,1},{1,2},{2,0},{0,3},{3,4},{4,0}}
        };
    }
    
    /**
     * Helper: Verify arrangement validity
     */
    private boolean isValidArrangement(int[][] pairs, int[][] result) {
        if (result.length != pairs.length) return false;
        for (int i = 1; i < result.length; i++) {
            if (result[i - 1][1] != result[i][0]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Helper: Check if result contains all original pairs
     */
    private boolean containsAllPairs(int[][] pairs, int[][] result) {
        // Create frequency map of original pairs
        Map<String, Integer> originalFreq = new HashMap<>();
        for (int[] pair : pairs) {
            String key = pair[0] + "," + pair[1];
            originalFreq.put(key, originalFreq.getOrDefault(key, 0) + 1);
        }
        
        // Check each result pair
        for (int[] pair : result) {
            String key = pair[0] + "," + pair[1];
            if (!originalFreq.containsKey(key) || originalFreq.get(key) == 0) {
                return false;
            }
            originalFreq.put(key, originalFreq.get(key) - 1);
        }
        
        return true;
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[][] pairs = testCases[i];
            System.out.printf("\nTest %d: %s%n", i + 1, Arrays.deepToString(pairs));
            
            int[][] result1 = validArrangement(pairs.clone());
            int[][] result2 = validArrangementIterative(pairs.clone());
            int[][] result3 = validArrangementOOP(pairs.clone());
            int[][] result4 = validArrangementPQ(pairs.clone());
            
            boolean valid1 = isValidArrangement(pairs, result1);
            boolean valid2 = isValidArrangement(pairs, result2);
            boolean valid3 = isValidArrangement(pairs, result3);
            boolean valid4 = isValidArrangement(pairs, result4);
            
            boolean contains1 = containsAllPairs(pairs, result1);
            boolean contains2 = containsAllPairs(pairs, result2);
            boolean contains3 = containsAllPairs(pairs, result3);
            boolean contains4 = containsAllPairs(pairs, result4);
            
            boolean allValid = valid1 && valid2 && valid3 && valid4;
            boolean allContains = contains1 && contains2 && contains3 && contains4;
            
            if (allValid && allContains) {
                System.out.println("✓ PASS - Valid arrangement found");
                passed++;
            } else {
                System.out.println("✗ FAIL - Invalid arrangement");
                if (!valid1) System.out.println("  Method 1 invalid");
                if (!valid2) System.out.println("  Method 2 invalid");
                if (!valid3) System.out.println("  Method 3 invalid");
                if (!valid4) System.out.println("  Method 4 invalid");
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeGraph(pairs);
                System.out.println("\nResult: " + Arrays.deepToString(result1));
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
        System.out.println("=".repeat(50));
        
        // Generate large test case
        int n = 100000;
        int[][] pairs = new int[n][2];
        Random rand = new Random(42);
        for (int i = 0; i < n - 1; i++) {
            pairs[i][0] = i;
            pairs[i][1] = i + 1;
        }
        pairs[n - 1][0] = n - 1;
        pairs[n - 1][1] = 0; // Form a cycle
        
        System.out.println("Test Setup: " + n + " pairs (path + back edge)");
        
        long[] times = new long[4];
        
        // Method 1: Recursive DFS
        int[][] p1 = copyPairs(pairs);
        long start = System.currentTimeMillis();
        validArrangement(p1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Iterative Stack
        int[][] p2 = copyPairs(pairs);
        start = System.currentTimeMillis();
        validArrangementIterative(p2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: OOP Approach
        int[][] p3 = copyPairs(pairs);
        start = System.currentTimeMillis();
        validArrangementOOP(p3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: PriorityQueue
        int[][] p4 = copyPairs(pairs);
        start = System.currentTimeMillis();
        validArrangementPQ(p4);
        times[3] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. Recursive DFS          | %9d%n", times[0]);
        System.out.printf("2. Iterative Stack        | %9d%n", times[1]);
        System.out.printf("3. OOP Approach           | %9d%n", times[2]);
        System.out.printf("4. PriorityQueue          | %9d%n", times[3]);
        
        System.out.println("\nObservations:");
        System.out.println("1. Iterative stack avoids recursion depth issues");
        System.out.println("2. PriorityQueue adds O(log n) overhead");
        System.out.println("3. All methods produce valid arrangements");
        System.out.println("4. O(E) time complexity is optimal");
    }
    
    private int[][] copyPairs(int[][] pairs) {
        int[][] copy = new int[pairs.length][2];
        for (int i = 0; i < pairs.length; i++) {
            copy[i][0] = pairs[i][0];
            copy[i][1] = pairs[i][1];
        }
        return copy;
    }
    
    /**
     * Helper: Explain Eulerian path
     */
    public void explainEulerianPath() {
        System.out.println("\nEulerian Path Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nWhat is an Eulerian Path?");
        System.out.println("A path that visits every edge exactly once in a directed graph.");
        
        System.out.println("\nConditions for a directed graph to have an Eulerian path:");
        System.out.println("1. All vertices with non-zero degree belong to a single strongly connected component");
        System.out.println("2. At most one vertex has out-degree - in-degree = 1 (start vertex)");
        System.out.println("3. At most one vertex has in-degree - out-degree = 1 (end vertex)");
        System.out.println("4. All other vertices have equal in-degree and out-degree");
        
        System.out.println("\nHierholzer's Algorithm:");
        System.out.println("1. Start from the vertex with out-degree > in-degree (or any vertex)");
        System.out.println("2. Follow edges until stuck, removing used edges");
        System.out.println("3. When stuck, backtrack and add vertices to result");
        System.out.println("4. Reverse result to get Eulerian path");
        
        System.out.println("\nExample: pairs = [[5,1],[4,5],[11,9],[9,4]]");
        System.out.println("  Graph: 5→1, 4→5, 11→9, 9→4");
        System.out.println("  Degrees:");
        System.out.println("    1: in=1, out=0 → diff=-1");
        System.out.println("    4: in=0, out=1 → diff=+1");
        System.out.println("    5: in=1, out=1 → diff=0");
        System.out.println("    9: in=1, out=1 → diff=0");
        System.out.println("    11: in=0, out=1 → diff=+1");
        System.out.println("  Start node: 4 or 11 → choose 11");
        System.out.println("  Path: 11→9→4→5→1");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Is there guaranteed to be a valid arrangement? (Yes)");
        System.out.println("   - What about duplicate edges? (Possible)");
        System.out.println("   - Need to return any valid arrangement? (Yes)");
        
        System.out.println("\n2. Recognize the pattern:");
        System.out.println("   - This is an Eulerian path problem");
        System.out.println("   - Similar to 'Reconstruct Itinerary' (LeetCode 332)");
        
        System.out.println("\n3. Propose Hierholzer's algorithm:");
        System.out.println("   - Build graph and track degrees");
        System.out.println("   - Find start node using degree conditions");
        System.out.println("   - DFS to find Eulerian path");
        
        System.out.println("\n4. Discuss data structures:");
        System.out.println("   - HashMap for adjacency list");
        System.out.println("   - List or Stack for path");
        System.out.println("   - Remove edges as we traverse");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(E) - each edge processed once");
        System.out.println("   - Space: O(V + E) - graph storage");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - Single pair");
        System.out.println("   - Cycle (all degrees equal)");
        System.out.println("   - Path (one start, one end)");
        System.out.println("   - Multiple edges between same nodes");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Forgetting to handle degree conditions");
        System.out.println("   - Not removing edges after use");
        System.out.println("   - Stack overflow in recursive DFS for large graphs");
        System.out.println("   - Not reversing the path");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("2097. Valid Arrangement of Pairs");
        System.out.println("================================");
        
        // Explain Eulerian path
        solution.explainEulerianPath();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
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
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public int[][] validArrangement(int[][] pairs) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Integer> outDegree = new HashMap<>();
        
        for (int[] pair : pairs) {
            int u = pair[0], v = pair[1];
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            outDegree.put(u, outDegree.getOrDefault(u, 0) + 1);
            inDegree.put(v, inDegree.getOrDefault(v, 0) + 1);
        }
        
        // Find start node
        int start = pairs[0][0];
        for (int node : graph.keySet()) {
            if (outDegree.getOrDefault(node, 0) - inDegree.getOrDefault(node, 0) == 1) {
                start = node;
                break;
            }
        }
        
        // DFS to find Eulerian path
        List<Integer> path = new ArrayList<>();
        dfs(start, graph, path);
        Collections.reverse(path);
        
        // Convert to pairs
        int[][] result = new int[path.size() - 1][2];
        for (int i = 0; i < path.size() - 1; i++) {
            result[i][0] = path.get(i);
            result[i][1] = path.get(i + 1);
        }
        return result;
    }
    
    private void dfs(int node, Map<Integer, List<Integer>> graph, List<Integer> path) {
        List<Integer> neighbors = graph.get(node);
        while (neighbors != null && !neighbors.isEmpty()) {
            int next = neighbors.remove(neighbors.size() - 1);
            dfs(next, graph, path);
        }
        path.add(node);
    }
}
            """);
        
        System.out.println("\nAlternative (Iterative):");
        System.out.println("""
class Solution {
    public int[][] validArrangement(int[][] pairs) {
        // Build graph and degree maps...
        
        // Iterative DFS
        Deque<Integer> stack = new ArrayDeque<>();
        List<Integer> path = new ArrayList<>();
        stack.push(start);
        
        while (!stack.isEmpty()) {
            int node = stack.peek();
            List<Integer> neighbors = graph.get(node);
            if (neighbors != null && !neighbors.isEmpty()) {
                stack.push(neighbors.remove(neighbors.size() - 1));
            } else {
                path.add(stack.pop());
            }
        }
        
        Collections.reverse(path);
        // Convert to pairs...
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. This is an Eulerian path problem");
        System.out.println("2. Use Hierholzer's algorithm for O(E) solution");
        System.out.println("3. Track in-degree and out-degree to find start node");
        System.out.println("4. Build path in reverse using post-order DFS");
        System.out.println("5. Iterative approach avoids recursion depth issues");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(E) where E = number of pairs");
        System.out.println("- Space: O(V + E) for graph representation");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you find the Eulerian circuit (closed path)?");
        System.out.println("2. What if the graph is undirected?");
        System.out.println("3. How would you handle multiple valid arrangements?");
        System.out.println("4. How would you verify if a valid arrangement exists?");
        System.out.println("5. How would you find the lexicographically smallest arrangement?");
    }
}
