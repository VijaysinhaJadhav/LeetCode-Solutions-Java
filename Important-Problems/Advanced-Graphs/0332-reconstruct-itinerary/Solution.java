
# Solution.java

```java
import java.util.*;

/**
 * 332. Reconstruct Itinerary
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a list of airline tickets (directed edges), reconstruct the itinerary
 * starting from "JFK" that uses every ticket exactly once. If multiple paths exist,
 * return the lexicographically smallest one.
 * 
 * Key Insights:
 * 1. This is an Eulerian path problem (use every edge exactly once in a directed graph)
 * 2. Hierholzer's algorithm finds the Eulerian path in O(E log E) time
 * 3. Sorting destinations ensures lexicographically smallest result
 * 4. Need to process destinations in reverse order when using stack/pop approach
 */
class Solution {
    
    /**
     * Approach 1: Hierholzer's Algorithm (Post-order DFS) - RECOMMENDED
     * Time: O(E log E), Space: O(V + E)
     * 
     * Steps:
     * 1. Build adjacency list with destinations sorted lexicographically
     * 2. Perform DFS from "JFK", always picking the smallest available destination
     * 3. When stuck (no outgoing edges), add airport to result (post-order)
     * 4. Reverse result to get correct itinerary
     */
    public List<String> findItinerary(List<List<String>> tickets) {
        // Build graph: map from airport to min-heap of destinations (automatically sorted)
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            
            graph.computeIfAbsent(from, k -> new PriorityQueue<>()).add(to);
        }
        
        List<String> result = new LinkedList<>(); // Use LinkedList for efficient addFirst
        
        dfs("JFK", graph, result);
        
        return result;
    }
    
    private void dfs(String airport, Map<String, PriorityQueue<String>> graph, List<String> result) {
        PriorityQueue<String> destinations = graph.get(airport);
        
        // Visit all destinations in lexical order
        while (destinations != null && !destinations.isEmpty()) {
            String next = destinations.poll(); // Always pick smallest destination
            dfs(next, graph, result);
        }
        
        // Post-order: add airport after visiting all its destinations
        // This builds the path in reverse
        result.add(0, airport); // Add to front for correct order
    }
    
    /**
     * Approach 2: Hierholzer's Algorithm with Stack (Iterative)
     * Time: O(E log E), Space: O(V + E)
     * 
     * Non-recursive version using explicit stack
     */
    public List<String> findItineraryIterative(List<List<String>> tickets) {
        // Build graph with priority queues (min-heaps)
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            graph.computeIfAbsent(from, k -> new PriorityQueue<>()).add(to);
        }
        
        Deque<String> stack = new ArrayDeque<>();
        List<String> result = new LinkedList<>();
        
        stack.push("JFK");
        
        while (!stack.isEmpty()) {
            String curr = stack.peek();
            
            if (graph.containsKey(curr) && !graph.get(curr).isEmpty()) {
                // If current airport has outgoing tickets, take the smallest one
                String next = graph.get(curr).poll();
                stack.push(next);
            } else {
                // No outgoing tickets - backtrack and add to result
                result.add(0, stack.pop());
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Backtracking DFS (for comparison, not optimal)
     * Time: O(E²) worst case, Space: O(V + E)
     * 
     * Tries all paths in lexical order until finds one that uses all tickets
     */
    public List<String> findItineraryBacktracking(List<List<String>> tickets) {
        // Sort tickets lexicographically first
        tickets.sort((a, b) -> {
            if (a.get(0).equals(b.get(0))) {
                return a.get(1).compareTo(b.get(1));
            }
            return a.get(0).compareTo(b.get(0));
        });
        
        // Build graph with list of destinations (not sorted automatically)
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, boolean[]> used = new HashMap<>(); // Track used tickets
        
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        }
        
        // Initialize used arrays
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            used.put(entry.getKey(), new boolean[entry.getValue().size()]);
        }
        
        List<String> result = new ArrayList<>();
        result.add("JFK");
        
        backtrack(tickets.size(), graph, used, "JFK", result);
        
        return result;
    }
    
    private boolean backtrack(int totalTickets, Map<String, List<String>> graph, 
                              Map<String, boolean[]> used, String curr, List<String> result) {
        if (result.size() == totalTickets + 1) {
            return true; // Used all tickets
        }
        
        if (!graph.containsKey(curr)) {
            return false;
        }
        
        List<String> destinations = graph.get(curr);
        boolean[] usedDest = used.get(curr);
        
        for (int i = 0; i < destinations.size(); i++) {
            if (usedDest[i]) continue;
            
            String next = destinations.get(i);
            usedDest[i] = true;
            result.add(next);
            
            if (backtrack(totalTickets, graph, used, next, result)) {
                return true;
            }
            
            // Backtrack
            result.remove(result.size() - 1);
            usedDest[i] = false;
        }
        
        return false;
    }
    
    /**
     * Helper: Visualize the graph and Eulerian path
     */
    public void visualizeGraph(List<List<String>> tickets) {
        System.out.println("\nGraph Visualization:");
        System.out.println("====================");
        
        // Count in/out degrees for each node
        Map<String, Integer> outDegree = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, List<String>> edges = new HashMap<>();
        
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            
            outDegree.put(from, outDegree.getOrDefault(from, 0) + 1);
            inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
            
            edges.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        }
        
        System.out.println("\nNodes (airports) and degrees:");
        Set<String> allNodes = new HashSet<>();
        allNodes.addAll(outDegree.keySet());
        allNodes.addAll(inDegree.keySet());
        
        String start = null;
        String end = null;
        
        for (String node : allNodes) {
            int out = outDegree.getOrDefault(node, 0);
            int in = inDegree.getOrDefault(node, 0);
            System.out.printf("  %s: out=%d, in=%d, diff=%d%n", node, out, in, out - in);
            
            if (out - in == 1) start = node;
            if (in - out == 1) end = node;
        }
        
        if (start == null) start = "JFK"; // If all equal, start can be JFK
        System.out.println("\n  Eulerian path will start at: " + start + (end != null ? ", end at: " + end : ""));
        
        System.out.println("\nEdges (tickets):");
        for (Map.Entry<String, List<String>> entry : edges.entrySet()) {
            System.out.printf("  %s -> %s%n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("\nFinding Eulerian path using Hierholzer's algorithm...");
        List<String> path = findItinerary(tickets);
        System.out.println("Result path: " + path);
        
        // Verify path
        System.out.println("\nVerification:");
        System.out.println("  Starts with JFK: " + (path.get(0).equals("JFK") ? "✓" : "✗"));
        System.out.println("  Uses all tickets: " + (path.size() == tickets.size() + 1 ? "✓" : "✗"));
        
        // Check edge usage
        Map<String, Integer> ticketCount = new HashMap<>();
        for (List<String> ticket : tickets) {
            String key = ticket.get(0) + "->" + ticket.get(1);
            ticketCount.put(key, ticketCount.getOrDefault(key, 0) + 1);
        }
        
        boolean validPath = true;
        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);
            String key = from + "->" + to;
            
            if (!ticketCount.containsKey(key) || ticketCount.get(key) <= 0) {
                System.out.printf("  ✗ Invalid edge: %s -> %s (not available)%n", from, to);
                validPath = false;
            } else {
                ticketCount.put(key, ticketCount.get(key) - 1);
            }
        }
        
        if (validPath) {
            System.out.println("  ✓ All edges used exactly once");
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            // Example 1
            {
                Arrays.asList(
                    Arrays.asList("MUC", "LHR"),
                    Arrays.asList("JFK", "MUC"),
                    Arrays.asList("SFO", "SJC"),
                    Arrays.asList("LHR", "SFO")
                ),
                Arrays.asList("JFK", "MUC", "LHR", "SFO", "SJC")
            },
            // Example 2
            {
                Arrays.asList(
                    Arrays.asList("JFK", "SFO"),
                    Arrays.asList("JFK", "ATL"),
                    Arrays.asList("SFO", "ATL"),
                    Arrays.asList("ATL", "JFK"),
                    Arrays.asList("ATL", "SFO")
                ),
                Arrays.asList("JFK", "ATL", "JFK", "SFO", "ATL", "SFO")
            },
            // Single ticket
            {
                Arrays.asList(
                    Arrays.asList("JFK", "SFO")
                ),
                Arrays.asList("JFK", "SFO")
            },
            // Multiple tickets from same source
            {
                Arrays.asList(
                    Arrays.asList("JFK", "ATL"),
                    Arrays.asList("JFK", "SFO"),
                    Arrays.asList("SFO", "JFK")
                ),
                Arrays.asList("JFK", "ATL", "JFK", "SFO", "JFK")
            },
            // Complex case with cycle
            {
                Arrays.asList(
                    Arrays.asList("JFK", "KUL"),
                    Arrays.asList("JFK", "NRT"),
                    Arrays.asList("NRT", "JFK")
                ),
                Arrays.asList("JFK", "NRT", "JFK", "KUL")
            },
            // Another complex case
            {
                Arrays.asList(
                    Arrays.asList("JFK", "NRT"),
                    Arrays.asList("JFK", "KUL"),
                    Arrays.asList("KUL", "JFK")
                ),
                Arrays.asList("JFK", "KUL", "JFK", "NRT")
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
            @SuppressWarnings("unchecked")
            List<List<String>> tickets = (List<List<String>>) testCases[i][0];
            @SuppressWarnings("unchecked")
            List<String> expected = (List<String>) testCases[i][1];
            
            System.out.printf("\nTest %d:%n", i + 1);
            System.out.println("Tickets: " + tickets);
            
            List<String> result1 = findItinerary(tickets);
            List<String> result2 = findItineraryIterative(tickets);
            List<String> result3 = findItineraryBacktracking(tickets);
            
            boolean allMatch = result1.equals(expected) && result2.equals(expected) && 
                              result3.equals(expected);
            
            if (allMatch) {
                System.out.println("✓ PASS - Result: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Hierholzer (DFS): " + result1);
                System.out.println("  Hierholzer (Iterative): " + result2);
                System.out.println("  Backtracking: " + result3);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeGraph(tickets);
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
        
        // Generate larger test case
        List<List<String>> tickets = new ArrayList<>();
        Random rand = new Random(42);
        String[] airports = {"JFK", "ATL", "SFO", "LAX", "ORD", "DFW", "DEN", "SEA", "LAS", "MIA"};
        
        // Create 300 tickets (max constraints)
        for (int i = 0; i < 300; i++) {
            String from = airports[rand.nextInt(airports.length)];
            String to = airports[rand.nextInt(airports.length)];
            while (to.equals(from)) {
                to = airports[rand.nextInt(airports.length)];
            }
            tickets.add(Arrays.asList(from, to));
        }
        
        System.out.println("Test Setup: 300 tickets, 10 airports");
        
        long[] times = new long[3];
        
        // Method 1: Hierholzer DFS
        long start = System.currentTimeMillis();
        List<String> result1 = findItinerary(tickets);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Hierholzer Iterative
        start = System.currentTimeMillis();
        List<String> result2 = findItineraryIterative(tickets);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Backtracking (skip for large input - too slow)
        times[2] = -1;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Path Length");
        System.out.println("--------------------------|-----------|-------------");
        System.out.printf("1. Hierholzer (DFS)       | %9d | %11d%n", times[0], result1.size());
        System.out.printf("2. Hierholzer (Iterative) | %9d | %11d%n", times[1], result2.size());
        System.out.printf("3. Backtracking           | %9s | %11s%n", "N/A", "N/A (too slow)");
        
        System.out.println("\nObservations:");
        System.out.println("1. Hierholzer's algorithm is optimal for Eulerian path problems");
        System.out.println("2. Iterative and recursive versions have similar performance");
        System.out.println("3. Backtracking is exponential and impractical for large inputs");
        System.out.println("4. The key is processing destinations in reverse order for lexical order");
    }
    
    /**
     * Helper: Explain Eulerian path concept
     */
    public void explainEulerianPath() {
        System.out.println("\nEulerian Path Explanation:");
        System.out.println("==========================");
        
        System.out.println("\nWhat is an Eulerian Path?");
        System.out.println("- A path in a graph that visits **every edge exactly once**");
        System.out.println("- Named after mathematician Leonhard Euler");
        System.out.println("- Also called an 'Eulerian trail'");
        
        System.out.println("\nConditions for a directed graph to have an Eulerian path [citation:1]:");
        System.out.println("1. At most one vertex has out-degree = in-degree + 1 (start node)");
        System.out.println("2. At most one vertex has in-degree = out-degree + 1 (end node)");
        System.out.println("3. All other vertices have equal in-degree and out-degree");
        System.out.println("4. All vertices with non-zero degree belong to the same strongly connected component");
        
        System.out.println("\nExample:");
        System.out.println("  Node A: out=2, in=1 → out-in = 1 (start)");
        System.out.println("  Node B: out=1, in=2 → in-out = 1 (end)");
        System.out.println("  Node C: out=1, in=1 → equal (middle)");
        
        System.out.println("\nHierholzer's Algorithm [citation:3]:");
        System.out.println("1. Start from the start node (or any node if all degrees equal)");
        System.out.println("2. Follow edges until stuck, removing used edges");
        System.out.println("3. When stuck, backtrack and add nodes to result (post-order)");
        System.out.println("4. Reverse result to get Eulerian path");
        
        System.out.println("\nWhy post-order works:");
        System.out.println("- The node where we get stuck is the end of the path");
        System.out.println("- Adding it first builds the path backwards");
        System.out.println("- Reversing at the end gives correct order");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Single ticket:");
        List<List<String>> tickets1 = Arrays.asList(
            Arrays.asList("JFK", "SFO")
        );
        System.out.println("  Tickets: " + tickets1);
        System.out.println("  Result: " + findItinerary(tickets1));
        
        System.out.println("\n2. Multiple tickets from same source:");
        List<List<String>> tickets2 = Arrays.asList(
            Arrays.asList("JFK", "ATL"),
            Arrays.asList("JFK", "SFO"),
            Arrays.asList("SFO", "JFK")
        );
        System.out.println("  Tickets: " + tickets2);
        System.out.println("  Result: " + findItinerary(tickets2));
        
        System.out.println("\n3. Cycle back to JFK:");
        List<List<String>> tickets3 = Arrays.asList(
            Arrays.asList("JFK", "KUL"),
            Arrays.asList("JFK", "NRT"),
            Arrays.asList("NRT", "JFK")
        );
        System.out.println("  Tickets: " + tickets3);
        System.out.println("  Result: " + findItinerary(tickets3));
        
        System.out.println("\n4. Lexicographic tie-breaking:");
        List<List<String>> tickets4 = Arrays.asList(
            Arrays.asList("JFK", "SEA"),
            Arrays.asList("JFK", "SFO"),
            Arrays.asList("SEA", "JFK"),
            Arrays.asList("SFO", "JFK")
        );
        System.out.println("  Tickets: " + tickets4);
        System.out.println("  Result should pick smaller lexical first (SEA before SFO)");
        System.out.println("  Result: " + findItinerary(tickets4));
        
        System.out.println("\n5. Large lexical difference:");
        List<List<String>> tickets5 = Arrays.asList(
            Arrays.asList("JFK", "AAA"),
            Arrays.asList("JFK", "ZZZ"),
            Arrays.asList("AAA", "JFK"),
            Arrays.asList("ZZZ", "JFK")
        );
        System.out.println("  Tickets: " + tickets5);
        System.out.println("  Result should go to AAA first (lexicographically smaller)");
        System.out.println("  Result: " + findItinerary(tickets5));
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Recognize the problem type:");
        System.out.println("   - Graph (directed)");
        System.out.println("   - Use every edge exactly once → Eulerian path");
        
        System.out.println("\n2. Discuss Eulerian path conditions:");
        System.out.println("   - Explain in-degree/out-degree properties");
        System.out.println("   - Note that problem guarantees at least one valid path");
        
        System.out.println("\n3. Propose Hierholzer's algorithm:");
        System.out.println("   - Post-order DFS traversal");
        System.out.println("   - Always pick smallest lexical destination");
        System.out.println("   - Build path in reverse, then reverse");
        
        System.out.println("\n4. Handle lexical ordering:");
        System.out.println("   - Sort destinations lexicographically");
        System.out.println("   - Use PriorityQueue for automatic sorting");
        System.out.println("   - In C++ multiset works well");
        
        System.out.println("\n5. Watch out for:");
        System.out.println("   - Not removing edges after use (infinite loop) [citation:7]");
        System.out.println("   - Forgetting to reverse the result");
        System.out.println("   - Incorrect sorting order (needs reverse for stack approach)");
        
        System.out.println("\n6. Complexity analysis:");
        System.out.println("   - Time: O(E log E) from sorting");
        System.out.println("   - Space: O(V + E)");
        
        System.out.println("\n7. Edge cases:");
        System.out.println("   - Single ticket");
        System.out.println("   - Multiple tickets from same source");
        System.out.println("   - Lexicographic tie-breaking");
        System.out.println("   - Cycles back to start");
        
        System.out.println("\n8. Common mistakes:");
        System.out.println("   - Starting from wrong node (must be JFK)");
        System.out.println("   - Not using all tickets");
        System.out.println("   - Getting stuck in dead ends");
        System.out.println("   - Forgetting lexical order requirement");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("332. Reconstruct Itinerary");
        System.out.println("==========================");
        
        // Explain Eulerian path
        solution.explainEulerianPath();
        
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
        
        System.out.println("\nRecommended Implementation (Hierholzer's Algorithm):");
        System.out.println("""
class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            graph.computeIfAbsent(from, k -> new PriorityQueue<>()).add(to);
        }
        
        List<String> result = new LinkedList<>();
        dfs("JFK", graph, result);
        return result;
    }
    
    private void dfs(String airport, Map<String, PriorityQueue<String>> graph, List<String> result) {
        PriorityQueue<String> destinations = graph.get(airport);
        
        while (destinations != null && !destinations.isEmpty()) {
            String next = destinations.poll();
            dfs(next, graph, result);
        }
        
        result.add(0, airport); // Post-order add to front
    }
}
            """);
        
        System.out.println("\nAlternative (Iterative):");
        System.out.println("""
class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        for (List<String> ticket : tickets) {
            graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()).add(ticket.get(1));
        }
        
        Deque<String> stack = new ArrayDeque<>();
        List<String> result = new LinkedList<>();
        stack.push("JFK");
        
        while (!stack.isEmpty()) {
            String curr = stack.peek();
            if (graph.containsKey(curr) && !graph.get(curr).isEmpty()) {
                stack.push(graph.get(curr).poll());
            } else {
                result.add(0, stack.pop());
            }
        }
        
        return result;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. This is an Eulerian path problem - use Hierholzer's algorithm");
        System.out.println("2. Always start from JFK and use every ticket exactly once");
        System.out.println("3. Sort destinations lexicographically for smallest lexical order");
        System.out.println("4. Post-order DFS builds path in reverse, then reverse");
        System.out.println("5. Time complexity: O(E log E), Space: O(V + E)");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(E log E) for sorting destinations");
        System.out.println("- Space: O(V + E) for adjacency list and recursion stack");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle multiple edges between same airports?");
        System.out.println("2. What if the graph is undirected?");
        System.out.println("3. How to find if an Eulerian path exists before computing it?");
        System.out.println("4. How would you modify to return all possible Eulerian paths?");
        System.out.println("5. What if the start node is not JFK?");
    }
}
