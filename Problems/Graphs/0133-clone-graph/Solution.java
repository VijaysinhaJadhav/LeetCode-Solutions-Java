
## Solution.java

```java
/**
 * 133. Clone Graph
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a reference of a node in a connected undirected graph, return a deep copy (clone) of the graph.
 * 
 * Key Insights:
 * 1. Use HashMap to map original nodes to cloned nodes
 * 2. DFS/BFS to traverse and clone all nodes
 * 3. If node already cloned, return from HashMap to handle cycles
 * 4. Recursively clone all neighbors
 * 
 * Approach (DFS with HashMap):
 * 1. Create HashMap to store original -> cloned node mapping
 * 2. Use DFS to traverse graph
 * 3. For each node, create clone if not exists
 * 4. Recursively clone all neighbors
 * 5. Return cloned graph
 * 
 * Time Complexity: O(n + e) where n is nodes, e is edges
 * Space Complexity: O(n) for HashMap and recursion stack
 * 
 * Tags: Graph, DFS, BFS, Hash Table
 */

import java.util.*;

// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}

class Solution {
    /**
     * Approach 1: DFS Recursive with HashMap - RECOMMENDED
     * O(n + e) time, O(n) space
     */
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        
        // HashMap to store mapping from original nodes to cloned nodes
        Map<Node, Node> visited = new HashMap<>();
        return dfsClone(node, visited);
    }
    
    private Node dfsClone(Node node, Map<Node, Node> visited) {
        // If node is already cloned, return the cloned version
        if (visited.containsKey(node)) {
            return visited.get(node);
        }
        
        // Create clone of current node
        Node clone = new Node(node.val);
        visited.put(node, clone);
        
        // Recursively clone all neighbors
        for (Node neighbor : node.neighbors) {
            Node clonedNeighbor = dfsClone(neighbor, visited);
            clone.neighbors.add(clonedNeighbor);
        }
        
        return clone;
    }
    
    /**
     * Approach 2: BFS Iterative with HashMap
     * O(n + e) time, O(n) space
     */
    public Node cloneGraphBFS(Node node) {
        if (node == null) {
            return null;
        }
        
        Map<Node, Node> visited = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        
        // Create clone of starting node
        Node cloneStart = new Node(node.val);
        visited.put(node, cloneStart);
        queue.offer(node);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            Node currentClone = visited.get(current);
            
            // Process all neighbors
            for (Node neighbor : current.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    // Create clone of neighbor if not exists
                    Node neighborClone = new Node(neighbor.val);
                    visited.put(neighbor, neighborClone);
                    queue.offer(neighbor);
                }
                // Add cloned neighbor to current clone's neighbors
                currentClone.neighbors.add(visited.get(neighbor));
            }
        }
        
        return cloneStart;
    }
    
    /**
     * Approach 3: DFS Iterative with Stack
     * O(n + e) time, O(n) space
     */
    public Node cloneGraphDFSIterative(Node node) {
        if (node == null) {
            return null;
        }
        
        Map<Node, Node> visited = new HashMap<>();
        Stack<Node> stack = new Stack<>();
        
        // Create clone of starting node
        Node cloneStart = new Node(node.val);
        visited.put(node, cloneStart);
        stack.push(node);
        
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            Node currentClone = visited.get(current);
            
            // Process all neighbors
            for (Node neighbor : current.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    // Create clone of neighbor if not exists
                    Node neighborClone = new Node(neighbor.val);
                    visited.put(neighbor, neighborClone);
                    stack.push(neighbor);
                }
                // Add cloned neighbor to current clone's neighbors
                currentClone.neighbors.add(visited.get(neighbor));
            }
        }
        
        return cloneStart;
    }
    
    /**
     * Approach 4: DFS with Array (Optimized for constraints)
     * O(n + e) time, O(n) space
     * Since node values are unique and 1 <= val <= 100, we can use array
     */
    public Node cloneGraphArray(Node node) {
        if (node == null) {
            return null;
        }
        
        // Since 1 <= Node.val <= 100 and values are unique
        Node[] visited = new Node[101]; // index 0 unused, 1-100 for node values
        return dfsCloneArray(node, visited);
    }
    
    private Node dfsCloneArray(Node node, Node[] visited) {
        // If node is already cloned, return the cloned version
        if (visited[node.val] != null) {
            return visited[node.val];
        }
        
        // Create clone of current node
        Node clone = new Node(node.val);
        visited[node.val] = clone;
        
        // Recursively clone all neighbors
        for (Node neighbor : node.neighbors) {
            Node clonedNeighbor = dfsCloneArray(neighbor, visited);
            clone.neighbors.add(clonedNeighbor);
        }
        
        return clone;
    }
    
    /**
     * Approach 5: BFS with Detailed Debugging
     * O(n + e) time, O(n) space
     * Includes visualization of the cloning process
     */
    public Node cloneGraphDebug(Node node) {
        if (node == null) {
            System.out.println("Input node is null, returning null");
            return null;
        }
        
        Map<Node, Node> visited = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        
        Node cloneStart = new Node(node.val);
        visited.put(node, cloneStart);
        queue.offer(node);
        
        System.out.println("Starting BFS cloning process...");
        System.out.println("Original graph has " + countNodes(node) + " nodes");
        
        int step = 1;
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            Node currentClone = visited.get(current);
            
            System.out.printf("Step %d: Processing node %d%n", step++, current.val);
            System.out.printf("  Original node %d has %d neighbors: ", 
                            current.val, current.neighbors.size());
            
            // Print neighbors
            for (Node neighbor : current.neighbors) {
                System.out.print(neighbor.val + " ");
            }
            System.out.println();
            
            for (Node neighbor : current.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    Node neighborClone = new Node(neighbor.val);
                    visited.put(neighbor, neighborClone);
                    queue.offer(neighbor);
                    System.out.printf("  Created new clone for node %d%n", neighbor.val);
                } else {
                    System.out.printf("  Reusing existing clone for node %d%n", neighbor.val);
                }
                currentClone.neighbors.add(visited.get(neighbor));
            }
            
            System.out.printf("  Clone node %d now has %d neighbors%n", 
                            currentClone.val, currentClone.neighbors.size());
        }
        
        System.out.println("Cloning completed. Cloned graph has " + countNodes(cloneStart) + " nodes");
        return cloneStart;
    }
    
    /**
     * Helper method to count nodes in graph using BFS
     */
    private int countNodes(Node node) {
        if (node == null) return 0;
        
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        visited.add(node);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (Node neighbor : current.neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        
        return visited.size();
    }
    
    /**
     * Helper method to create test graph from adjacency list
     */
    public Node createGraph(int[][] adjList) {
        if (adjList == null || adjList.length == 0) {
            return null;
        }
        
        // Create all nodes first
        Node[] nodes = new Node[adjList.length + 1]; // 1-indexed
        for (int i = 1; i <= adjList.length; i++) {
            nodes[i] = new Node(i);
        }
        
        // Connect neighbors
        for (int i = 0; i < adjList.length; i++) {
            int nodeIndex = i + 1; // 1-indexed
            for (int neighborVal : adjList[i]) {
                nodes[nodeIndex].neighbors.add(nodes[neighborVal]);
            }
        }
        
        return nodes[1]; // Return node with value 1
    }
    
    /**
     * Helper method to convert graph to adjacency list for printing
     */
    public List<List<Integer>> graphToAdjList(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }
        
        List<List<Integer>> adjList = new ArrayList<>();
        Map<Node, Integer> visited = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        
        // Assign indices to nodes based on BFS order
        queue.offer(node);
        visited.put(node, visited.size());
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int currentIndex = visited.get(current);
            
            // Ensure adjList has enough entries
            while (adjList.size() <= currentIndex) {
                adjList.add(new ArrayList<>());
            }
            
            // Add neighbors to adjacency list
            List<Integer> neighborsList = new ArrayList<>();
            for (Node neighbor : current.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    visited.put(neighbor, visited.size());
                    queue.offer(neighbor);
                }
                neighborsList.add(neighbor.val);
            }
            adjList.set(currentIndex, neighborsList);
        }
        
        return adjList;
    }
    
    /**
     * Helper method to visualize graph structure
     */
    public void visualizeGraph(Node node) {
        if (node == null) {
            System.out.println("Graph is empty");
            return;
        }
        
        System.out.println("Graph Visualization:");
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        visited.add(node);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.printf("Node %d -> neighbors: ", current.val);
            
            for (Node neighbor : current.neighbors) {
                System.out.print(neighbor.val + " ");
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Clone Graph:");
        System.out.println("====================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        int[][] adjList1 = {{2,4}, {1,3}, {2,4}, {1,3}};
        Node graph1 = solution.createGraph(adjList1);
        
        long startTime = System.nanoTime();
        Node result1a = solution.cloneGraph(graph1);
        long time1a = System.nanoTime() - startTime;
        
        Node graph1b = solution.createGraph(adjList1);
        startTime = System.nanoTime();
        Node result1b = solution.cloneGraphBFS(graph1b);
        long time1b = System.nanoTime() - startTime;
        
        Node graph1c = solution.createGraph(adjList1);
        startTime = System.nanoTime();
        Node result1c = solution.cloneGraphArray(graph1c);
        long time1c = System.nanoTime() - startTime;
        
        // Verify by converting back to adjacency list
        List<List<Integer>> originalAdj = solution.graphToAdjList(graph1);
        List<List<Integer>> clonedAdj1 = solution.graphToAdjList(result1a);
        List<List<Integer>> clonedAdj2 = solution.graphToAdjList(result1b);
        List<List<Integer>> clonedAdj3 = solution.graphToAdjList(result1c);
        
        boolean test1a = originalAdj.equals(clonedAdj1);
        boolean test1b = originalAdj.equals(clonedAdj2);
        boolean test1c = originalAdj.equals(clonedAdj3);
        
        System.out.println("DFS Recursive: " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Array DFS: " + (test1c ? "PASSED" : "FAILED"));
        
        System.out.println("Original graph:");
        solution.visualizeGraph(graph1);
        System.out.println("Cloned graph:");
        solution.visualizeGraph(result1a);
        
        // Test case 2: Single node
        System.out.println("\nTest 2: Single node");
        int[][] adjList2 = {{}};
        Node graph2 = solution.createGraph(adjList2);
        Node result2 = solution.cloneGraph(graph2);
        boolean test2 = (result2 != null) && (result2.val == 1) && result2.neighbors.isEmpty();
        System.out.println("Single node: " + (test2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Empty graph
        System.out.println("\nTest 3: Empty graph");
        Node result3 = solution.cloneGraph(null);
        System.out.println("Empty graph: " + (result3 == null ? "PASSED" : "FAILED"));
        
        // Test case 4: Linear graph 1-2-3
        System.out.println("\nTest 4: Linear graph");
        int[][] adjList4 = {{2}, {1,3}, {2}};
        Node graph4 = solution.createGraph(adjList4);
        Node result4 = solution.cloneGraph(graph4);
        List<List<Integer>> original4 = solution.graphToAdjList(graph4);
        List<List<Integer>> cloned4 = solution.graphToAdjList(result4);
        boolean test4 = original4.equals(cloned4);
        System.out.println("Linear graph: " + (test4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Star graph (node 1 connected to all others)
        System.out.println("\nTest 5: Star graph");
        int[][] adjList5 = {{2,3,4}, {1}, {1}, {1}};
        Node graph5 = solution.createGraph(adjList5);
        Node result5 = solution.cloneGraph(graph5);
        List<List<Integer>> original5 = solution.graphToAdjList(graph5);
        List<List<Integer>> cloned5 = solution.graphToAdjList(result5);
        boolean test5 = original5.equals(cloned5);
        System.out.println("Star graph: " + (test5 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  DFS Recursive: " + time1a + " ns");
        System.out.println("  BFS: " + time1b + " ns");
        System.out.println("  Array DFS: " + time1c + " ns");
        
        // Debug version with detailed output
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DEBUG VERSION OUTPUT:");
        System.out.println("=".repeat(70));
        Node graphDebug = solution.createGraph(adjList1);
        solution.cloneGraphDebug(graphDebug);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        solution.explainAlgorithm();
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Detailed explanation of the algorithm
     */
    private void explainAlgorithm() {
        System.out.println("\nKey Insights for Graph Cloning:");
        System.out.println("1. We need a deep copy - new Node objects, not references to original");
        System.out.println("2. Must handle cycles in the graph to avoid infinite recursion");
        System.out.println("3. Need to maintain mapping between original and cloned nodes");
        System.out.println("4. Both DFS and BFS work well for graph traversal");
        
        System.out.println("\nDFS Recursive Approach:");
        System.out.println("1. Create HashMap to store original -> cloned node mapping");
        System.out.println("2. For each node:");
        System.out.println("   - If already in HashMap, return the cloned node");
        System.out.println("   - Otherwise, create new Node with same value");
        System.out.println("   - Add to HashMap");
        System.out.println("   - Recursively clone all neighbors");
        System.out.println("   - Add cloned neighbors to current clone's neighbors list");
        
        System.out.println("\nExample Walkthrough:");
        System.out.println("Graph: 1-2, 1-3, 2-3 (triangle)");
        System.out.println("Step 1: Start with node 1");
        System.out.println("  Create clone1, add to map {1->clone1}");
        System.out.println("  Process neighbors [2,3]");
        System.out.println("Step 2: Process neighbor 2");
        System.out.println("  Create clone2, add to map {1->clone1, 2->clone2}");
        System.out.println("  Process neighbors [1,3]");
        System.out.println("Step 3: Process neighbor 1 (already in map)");
        System.out.println("  Return clone1, add to clone2's neighbors");
        System.out.println("Step 4: Process neighbor 3");
        System.out.println("  Create clone3, add to map {1->clone1, 2->clone2, 3->clone3}");
        System.out.println("  Process neighbors [1,2]");
        System.out.println("Step 5: Both neighbors 1 and 2 are in map");
        System.out.println("  Add clone1 and clone2 to clone3's neighbors");
        
        System.out.println("\nWhy HashMap Prevents Cycles:");
        System.out.println("- When we encounter a node we've already cloned,");
        System.out.println("  we return the existing clone from HashMap");
        System.out.println("- This prevents infinite recursion on cycles");
        
        System.out.println("\nTime Complexity: O(n + e)");
        System.out.println("- n: number of nodes");
        System.out.println("- e: number of edges");
        System.out.println("- We visit each node and each edge once");
        
        System.out.println("\nSpace Complexity: O(n)");
        System.out.println("- HashMap stores n node mappings");
        System.out.println("- Recursion stack depth: O(n) in worst case");
    }
}

/**
 * Additional utility class for graph operations
 */
class GraphUtils {
    /**
     * Check if two graphs are identical (same structure)
     */
    public static boolean areGraphsIdentical(Node graph1, Node graph2) {
        if (graph1 == null && graph2 == null) return true;
        if (graph1 == null || graph2 == null) return false;
        if (graph1.val != graph2.val) return false;
        
        Set<Node> visited1 = new HashSet<>();
        Set<Node> visited2 = new HashSet<>();
        return dfsCompare(graph1, graph2, visited1, visited2);
    }
    
    private static boolean dfsCompare(Node node1, Node node2, 
                                    Set<Node> visited1, Set<Node> visited2) {
        if (visited1.contains(node1) && visited2.contains(node2)) {
            return true;
        }
        if (visited1.contains(node1) || visited2.contains(node2)) {
            return false;
        }
        
        visited1.add(node1);
        visited2.add(node2);
        
        if (node1.neighbors.size() != node2.neighbors.size()) {
            return false;
        }
        
        // Sort neighbors by value for comparison
        List<Node> neighbors1 = new ArrayList<>(node1.neighbors);
        List<Node> neighbors2 = new ArrayList<>(node2.neighbors);
        
        neighbors1.sort((a, b) -> Integer.compare(a.val, b.val));
        neighbors2.sort((a, b) -> Integer.compare(a.val, b.val));
        
        for (int i = 0; i < neighbors1.size(); i++) {
            if (neighbors1.get(i).val != neighbors2.get(i).val) {
                return false;
            }
            if (!dfsCompare(neighbors1.get(i), neighbors2.get(i), visited1, visited2)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Get all node values in graph using BFS
     */
    public static List<Integer> getAllNodeValues(Node node) {
        if (node == null) return new ArrayList<>();
        
        List<Integer> values = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        
        queue.offer(node);
        visited.add(node);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            values.add(current.val);
            
            for (Node neighbor : current.neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        
        Collections.sort(values);
        return values;
    }
}
