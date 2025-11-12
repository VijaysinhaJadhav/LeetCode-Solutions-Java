
## Problems/Arrays-Hashing/0752-open-the-lock/Solution.java

```java
/**
 * 752. Open the Lock
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You have a lock with 4 circular wheels. Each wheel has 10 slots: '0' to '9'.
 * The wheels can rotate freely and wrap around. Each move consists of turning one wheel one slot.
 * The lock starts at '0000'. You are given a list of deadends and a target.
 * Return the minimum total number of turns required to open the lock, or -1 if impossible.
 * 
 * Key Insights:
 * 1. This is a shortest path problem in an implicit graph
 * 2. Each lock combination is a node, edges represent one wheel turn
 * 3. BFS finds shortest path in unweighted graphs
 * 4. There are 10^4 = 10000 possible states
 * 5. Each state has 8 neighbors (4 wheels × 2 directions)
 * 
 * Approach (BFS):
 * 1. Use BFS to explore states level by level
 * 2. Start from "0000"
 * 3. For each state, generate all possible next states
 * 4. Skip deadends and visited states
 * 5. Return steps when target is found
 * 
 * Time Complexity: O(10^4) = O(10000) possible states
 * Space Complexity: O(10^4) for visited set and queue
 * 
 * Tags: Array, Hash Table, String, BFS, Shortest Path
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: BFS (Breadth-First Search) - RECOMMENDED
     * O(10000) time, O(10000) space
     */
    public int openLock(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        
        // Check if starting point is a deadend
        String start = "0000";
        if (deadSet.contains(start)) {
            return -1;
        }
        
        queue.offer(start);
        visited.add(start);
        int steps = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                String current = queue.poll();
                
                // Check if we reached the target
                if (current.equals(target)) {
                    return steps;
                }
                
                // Generate all possible next states
                for (String next : getNeighbors(current)) {
                    if (!visited.contains(next) && !deadSet.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                    }
                }
            }
            steps++;
        }
        
        return -1; // Target not reachable
    }
    
    /**
     * Generate all possible next states by turning each wheel forward and backward
     */
    private List<String> getNeighbors(String state) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = state.toCharArray();
        
        for (int i = 0; i < 4; i++) {
            char original = chars[i];
            
            // Turn wheel forward
            chars[i] = (char) (original == '9' ? '0' : original + 1);
            neighbors.add(new String(chars));
            
            // Turn wheel backward
            chars[i] = (char) (original == '0' ? '9' : original - 1);
            neighbors.add(new String(chars));
            
            // Restore original character
            chars[i] = original;
        }
        
        return neighbors;
    }
    
    /**
     * Approach 2: Bidirectional BFS
     * O(10000) time, O(10000) space - More efficient for some cases
     */
    public int openLockBidirectional(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        
        String start = "0000";
        if (deadSet.contains(start) || deadSet.contains(target)) {
            return -1;
        }
        
        beginSet.add(start);
        endSet.add(target);
        visited.add(start);
        visited.add(target);
        int steps = 0;
        
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            // Always expand the smaller set first
            if (beginSet.size() > endSet.size()) {
                Set<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
            }
            
            Set<String> nextSet = new HashSet<>();
            
            for (String current : beginSet) {
                if (endSet.contains(current)) {
                    return steps;
                }
                
                for (String neighbor : getNeighbors(current)) {
                    if (!visited.contains(neighbor) && !deadSet.contains(neighbor)) {
                        visited.add(neighbor);
                        nextSet.add(neighbor);
                    }
                }
            }
            
            beginSet = nextSet;
            steps++;
        }
        
        return -1;
    }
    
    /**
     * Approach 3: BFS with Array Representation
     * O(10000) time, O(10000) space - Using integer arrays for efficiency
     */
    public int openLockArray(String[] deadends, String target) {
        boolean[] visited = new boolean[10000]; // 0000 to 9999
        boolean[] dead = new boolean[10000];
        
        // Mark deadends
        for (String deadend : deadends) {
            int num = Integer.parseInt(deadend);
            dead[num] = true;
        }
        
        int start = 0;
        int targetNum = Integer.parseInt(target);
        
        if (dead[start] || dead[targetNum]) {
            return -1;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        int steps = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                int current = queue.poll();
                
                if (current == targetNum) {
                    return steps;
                }
                
                for (int neighbor : getNeighborNumbers(current)) {
                    if (!visited[neighbor] && !dead[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
            steps++;
        }
        
        return -1;
    }
    
    /**
     * Generate neighbor numbers for array-based approach
     */
    private List<Integer> getNeighborNumbers(int num) {
        List<Integer> neighbors = new ArrayList<>();
        int[] digits = new int[4];
        
        // Extract digits
        int temp = num;
        for (int i = 3; i >= 0; i--) {
            digits[i] = temp % 10;
            temp /= 10;
        }
        
        // Generate neighbors by turning each wheel
        for (int i = 0; i < 4; i++) {
            int original = digits[i];
            
            // Turn forward
            digits[i] = (original + 1) % 10;
            neighbors.add(combineDigits(digits));
            
            // Turn backward
            digits[i] = (original + 9) % 10; // equivalent to -1 mod 10
            neighbors.add(combineDigits(digits));
            
            // Restore original
            digits[i] = original;
        }
        
        return neighbors;
    }
    
    private int combineDigits(int[] digits) {
        return digits[0] * 1000 + digits[1] * 100 + digits[2] * 10 + digits[3];
    }
    
    /**
     * Approach 4: BFS with Custom State Class
     * O(10000) time, O(10000) space - More object-oriented approach
     */
    public int openLockCustomClass(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        if (deadSet.contains("0000")) return -1;
        
        Queue<LockState> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(new LockState("0000", 0));
        visited.add("0000");
        
        while (!queue.isEmpty()) {
            LockState current = queue.poll();
            
            if (current.state.equals(target)) {
                return current.steps;
            }
            
            for (String neighbor : getNeighbors(current.state)) {
                if (!visited.contains(neighbor) && !deadSet.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(new LockState(neighbor, current.steps + 1));
                }
            }
        }
        
        return -1;
    }
    
    private static class LockState {
        String state;
        int steps;
        
        LockState(String state, int steps) {
            this.state = state;
            this.steps = steps;
        }
    }
    
    /**
     * Approach 5: BFS with Early Termination
     * O(10000) time, O(10000) space - With optimization checks
     */
    public int openLockOptimized(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        
        String start = "0000";
        if (deadSet.contains(start)) return -1;
        if (start.equals(target)) return 0;
        
        queue.offer(start);
        visited.add(start);
        int steps = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            steps++;
            
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                
                for (String neighbor : getNeighbors(current)) {
                    if (neighbor.equals(target)) {
                        return steps;
                    }
                    
                    if (!visited.contains(neighbor) && !deadSet.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Helper method to visualize the BFS process
     */
    private void visualizeBFS(String[] deadends, String target) {
        System.out.println("\nOpen the Lock BFS Visualization:");
        System.out.println("Deadends: " + Arrays.toString(deadends));
        System.out.println("Target: " + target);
        System.out.println("Start: 0000");
        
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        
        String start = "0000";
        if (deadSet.contains(start)) {
            System.out.println("Start is a deadend! Cannot proceed.");
            return;
        }
        
        queue.offer(start);
        visited.add(start);
        int steps = 0;
        
        System.out.println("\nBFS Level-by-Level Exploration:");
        System.out.println("Level | Current State | Neighbors Generated");
        System.out.println("------|---------------|-------------------");
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.println("\nLevel " + steps + " (" + levelSize + " states):");
            
            for (int i = 0; i < levelSize; i++) {
                String current = queue.poll();
                System.out.printf("      | %-13s | ", current);
                
                if (current.equals(target)) {
                    System.out.println("TARGET FOUND!");
                    System.out.println("\nTotal steps: " + steps);
                    return;
                }
                
                List<String> neighbors = getNeighbors(current);
                List<String> validNeighbors = new ArrayList<>();
                
                for (String neighbor : neighbors) {
                    if (!visited.contains(neighbor) && !deadSet.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                        validNeighbors.add(neighbor);
                    }
                }
                
                System.out.println(validNeighbors);
            }
            steps++;
        }
        
        System.out.println("\nTarget not reachable!");
    }
    
    /**
     * Helper method to show state space analysis
     */
    private void analyzeStateSpace() {
        System.out.println("\nState Space Analysis:");
        System.out.println("Total possible states: 10^4 = 10000");
        System.out.println("Each state has 8 neighbors (4 wheels × 2 directions each)");
        
        System.out.println("\nNeighbor Generation Examples:");
        String[] testStates = {"0000", "1234", "9999"};
        
        for (String state : testStates) {
            System.out.println(state + " -> " + getNeighbors(state));
        }
        
        System.out.println("\nWheel Rotation Rules:");
        System.out.println("Forward: 0->1, 1->2, ..., 8->9, 9->0");
        System.out.println("Backward: 0->9, 1->0, 2->1, ..., 9->8");
    }
    
    /**
     * Helper method to demonstrate deadend impact
     */
    private void demonstrateDeadendImpact(String[] deadends, String target) {
        System.out.println("\nDeadend Impact Analysis:");
        System.out.println("Deadends: " + Arrays.toString(deadends));
        System.out.println("Target: " + target);
        
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        int blockedNeighbors = 0;
        int totalStates = 10000;
        
        // Count how many states are blocked by deadends
        for (String deadend : deadSet) {
            List<String> neighbors = getNeighbors(deadend);
            for (String neighbor : neighbors) {
                if (!deadSet.contains(neighbor)) {
                    blockedNeighbors++;
                }
            }
        }
        
        System.out.println("Number of deadends: " + deadSet.size());
        System.out.println("Approximate blocked connections: " + blockedNeighbors);
        System.out.println("Percentage of state space affected: " + 
                         (deadSet.size() * 100.0 / totalStates) + "%");
        
        // Check if start or target are deadends
        if (deadSet.contains("0000")) {
            System.out.println("CRITICAL: Start state is a deadend!");
        }
        if (deadSet.contains(target)) {
            System.out.println("CRITICAL: Target state is a deadend!");
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Open the Lock Solution:");
        System.out.println("================================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example 1");
        String[] deadends1 = {"0201","0101","0102","1212","2002"};
        String target1 = "0202";
        int expected1 = 6;
        
        long startTime = System.nanoTime();
        int result1a = solution.openLock(deadends1, target1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.openLockBidirectional(deadends1, target1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.openLockArray(deadends1, target1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = (result1a == expected1);
        boolean test1b = (result1b == expected1);
        boolean test1c = (result1c == expected1);
        
        System.out.println("BFS: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Bidirectional BFS: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Array BFS: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeBFS(deadends1, target1);
        solution.analyzeStateSpace();
        solution.demonstrateDeadendImpact(deadends1, target1);
        
        // Test case 2: Example 2
        System.out.println("\nTest 2: Example 2");
        String[] deadends2 = {"8888"};
        String target2 = "0009";
        int expected2 = 1;
        
        int result2a = solution.openLock(deadends2, target2);
        System.out.println("Example 2: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Example 3
        System.out.println("\nTest 3: Example 3");
        String[] deadends3 = {"8887","8889","8878","8898","8788","8988","7888","9888"};
        String target3 = "8888";
        int expected3 = -1;
        
        int result3a = solution.openLock(deadends3, target3);
        System.out.println("Example 3: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: No deadends
        System.out.println("\nTest 4: No deadends");
        String[] deadends4 = {};
        String target4 = "1234";
        int result4a = solution.openLock(deadends4, target4);
        System.out.println("No deadends: " + result4a + " steps");
        
        // Test case 5: Start is deadend
        System.out.println("\nTest 5: Start is deadend");
        String[] deadends5 = {"0000"};
        String target5 = "1234";
        int expected5 = -1;
        
        int result5a = solution.openLock(deadends5, target5);
        System.out.println("Start deadend: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Example 1 performance:");
        System.out.println("  BFS: " + time1a + " ns");
        System.out.println("  Bidirectional BFS: " + time1b + " ns");
        System.out.println("  Array BFS: " + time1c + " ns");
        
        // Performance test with worst-case scenario
        System.out.println("\nTest 7: Worst-case performance");
        String[] deadends7 = {}; // No deadends - explores all states
        String target7 = "9999"; // Farthest from start
        
        startTime = System.nanoTime();
        int result7a = solution.openLock(deadends7, target7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result7b = solution.openLockBidirectional(deadends7, target7);
        long time7b = System.nanoTime() - startTime;
        
        System.out.println("Worst-case (no deadends, target 9999):");
        System.out.println("  BFS: " + time7a + " ns, Steps: " + result7a);
        System.out.println("  Bidirectional BFS: " + time7b + " ns, Steps: " + result7b);
        
        // Compare all approaches for consistency
        System.out.println("\nTest 8: Consistency Check");
        String[] testDeadends = {"0101","0202"};
        String testTarget = "0303";
        int r1 = solution.openLock(testDeadends, testTarget);
        int r2 = solution.openLockBidirectional(testDeadends, testTarget);
        int r3 = solution.openLockArray(testDeadends, testTarget);
        int r4 = solution.openLockCustomClass(testDeadends, testTarget);
        int r5 = solution.openLockOptimized(testDeadends, testTarget);
        
        boolean consistent = (r1 == r2) && (r1 == r3) && (r1 == r4) && (r1 == r5);
        System.out.println("All approaches consistent: " + consistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BFS ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Treat each lock combination as a node in a graph.");
        System.out.println("Edges represent turning one wheel one slot (forward or backward).");
        System.out.println("BFS finds the shortest path from '0000' to target.");
        
        System.out.println("\nWhy BFS works:");
        System.out.println("1. Unweighted Graph: All moves cost 1 step");
        System.out.println("2. Shortest Path: BFS guarantees shortest path in unweighted graphs");
        System.out.println("3. State Space: 10000 states is manageable for BFS");
        System.out.println("4. Deadends: Naturally handled as blocked nodes");
        
        System.out.println("\nState Generation:");
        System.out.println("For state 'ABCD', generate neighbors by:");
        System.out.println("  Turning wheel 1: 'BBCD', '9BCD'");
        System.out.println("  Turning wheel 2: 'AACD', 'A9CD'");
        System.out.println("  Turning wheel 3: 'ABBD', 'AB9D'");
        System.out.println("  Turning wheel 4: 'ABCC', 'ABC9'");
        
        System.out.println("\nVisual Example (from '0000'):");
        System.out.println("Level 0: 0000");
        System.out.println("Level 1: 1000, 9000, 0100, 0900, 0010, 0090, 0001, 0009");
        System.out.println("Level 2: Explore all valid neighbors of level 1 states");
        System.out.println("... and so on until target is found");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. BFS (Breadth-First Search) - RECOMMENDED:");
        System.out.println("   Time: O(10000) - 10000 possible states");
        System.out.println("   Space: O(10000) - Visited set and queue storage");
        System.out.println("   How it works:");
        System.out.println("     - Explore states level by level from start");
        System.out.println("     - Generate all valid neighbors for each state");
        System.out.println("     - Stop when target is found");
        System.out.println("   Pros:");
        System.out.println("     - Guarantees shortest path");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to implement and debug");
        System.out.println("   Cons:");
        System.out.println("     - Explores all states in worst case");
        System.out.println("   Best for: Interview settings, general use");
        
        System.out.println("\n2. Bidirectional BFS:");
        System.out.println("   Time: O(10000) - Same worst case but often faster");
        System.out.println("   Space: O(10000) - Two sets and visited storage");
        System.out.println("   How it works:");
        System.out.println("     - Search from both start and target simultaneously");
        System.out.println("     - Stop when searches meet in the middle");
        System.out.println("   Pros:");
        System.out.println("     - Often faster in practice");
        System.out.println("     - Reduces search space");
        System.out.println("   Cons:");
        System.out.println("     - More complex to implement");
        System.out.println("     - Harder to debug");
        System.out.println("   Best for: Performance optimization");
        
        System.out.println("\n3. Array-based BFS:");
        System.out.println("   Time: O(10000) - Same as string-based");
        System.out.println("   Space: O(10000) - Boolean arrays for visited/dead");
        System.out.println("   How it works:");
        System.out.println("     - Represent states as integers (0-9999)");
        System.out.println("     - Use arrays instead of hash sets");
        System.out.println("   Pros:");
        System.out.println("     - Faster lookups with arrays");
        System.out.println("     - More memory efficient");
        System.out.println("   Cons:");
        System.out.println("     - Less intuitive state representation");
        System.out.println("     - More complex neighbor generation");
        System.out.println("   Best for: Performance-critical applications");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("OPTIMIZATION STRATEGIES:");
        System.out.println("1. Early Termination: Return immediately when target found");
        System.out.println("2. Deadend Preprocessing: Check start and target early");
        System.out.println("3. Bidirectional Search: Search from both ends");
        System.out.println("4. Efficient Data Structures: Use arrays for faster lookups");
        System.out.println("5. State Representation: Choose between strings and integers");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with BFS - it's the most expected solution");
        System.out.println("2. Explain the graph representation clearly");
        System.out.println("3. Demonstrate neighbor generation with examples");
        System.out.println("4. Handle edge cases: start/target in deadends");
        System.out.println("5. Discuss time/space complexity (10000 states)");
        System.out.println("6. Mention alternative approaches if time permits");
        System.out.println("7. Write clean neighbor generation code");
        System.out.println("8. Test with provided examples");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
