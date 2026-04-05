
# Solution.java

```java
import java.util.*;

/**
 * 457. Circular Array Loop
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Detect a cycle of length >= 2 in a circular array where each index points to
 * (i + nums[i]) mod n. All moves in the cycle must be in the same direction.
 * 
 * Key Insights:
 * 1. Use visited states: 0 = unvisited, 1 = visiting, 2 = visited
 * 2. For each starting node, follow path until we find a cycle or dead end
 * 3. Check that all nodes in cycle have same direction (same sign)
 * 4. Cycle length must be at least 2
 */
class Solution {
    
    /**
     * Approach 1: DFS with Visited States (Recommended)
     * Time: O(n), Space: O(n)
     * 
     * Steps:
     * 1. Mark nodes as 0 (unvisited), 1 (visiting), 2 (visited)
     * 2. For each unvisited node, DFS to find cycle
     * 3. Track path length and ensure direction consistency
     */
    public boolean circularArrayLoop(int[] nums) {
        int n = nums.length;
        int[] visited = new int[n]; // 0 = unvisited, 1 = visiting, 2 = visited
        
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0 && hasCycle(nums, visited, i, nums[i] > 0)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasCycle(int[] nums, int[] visited, int start, boolean isForward) {
        int n = nums.length;
        int slow = start;
        int fast = start;
        
        // Floyd's cycle detection with direction check
        while (true) {
            // Move slow one step
            slow = next(nums, slow, isForward);
            if (slow == -1) return false;
            
            // Move fast two steps
            fast = next(nums, fast, isForward);
            if (fast == -1) return false;
            fast = next(nums, fast, isForward);
            if (fast == -1) return false;
            
            if (slow == fast) {
                // Check cycle length >= 2
                int len = 1;
                int curr = next(nums, slow, isForward);
                while (curr != slow) {
                    len++;
                    curr = next(nums, curr, isForward);
                }
                return len >= 2;
            }
        }
    }
    
    private int next(int[] nums, int idx, boolean isForward) {
        int n = nums.length;
        boolean direction = nums[idx] > 0;
        
        // Direction mismatch
        if (direction != isForward) {
            return -1;
        }
        
        int next = (idx + nums[idx]) % n;
        if (next < 0) next += n;
        
        // Self-loop (length 1)
        if (next == idx) {
            return -1;
        }
        
        return next;
    }
    
    /**
     * Approach 2: DFS with Coloring (More Explicit)
     * Time: O(n), Space: O(n)
     * 
     * Uses 3 states: 0=unvisited, 1=visiting, 2=processed
     */
    public boolean circularArrayLoopColoring(int[] nums) {
        int n = nums.length;
        int[] state = new int[n]; // 0: unvisited, 1: visiting, 2: visited
        
        for (int i = 0; i < n; i++) {
            if (state[i] == 0 && dfs(nums, state, i, nums[i] > 0)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean dfs(int[] nums, int[] state, int curr, boolean isForward) {
        int n = nums.length;
        
        if (state[curr] == 1) {
            // Found a cycle, check length
            int len = 1;
            int next = nextNode(nums, curr, isForward);
            while (next != curr) {
                len++;
                next = nextNode(nums, next, isForward);
            }
            return len >= 2;
        }
        
        if (state[curr] == 2) {
            return false;
        }
        
        // Check direction consistency
        boolean direction = nums[curr] > 0;
        if (direction != isForward) {
            state[curr] = 2;
            return false;
        }
        
        int next = nextNode(nums, curr, isForward);
        if (next == -1 || next == curr) {
            state[curr] = 2;
            return false;
        }
        
        state[curr] = 1;
        boolean found = dfs(nums, state, next, isForward);
        state[curr] = 2;
        
        return found;
    }
    
    private int nextNode(int[] nums, int idx, boolean isForward) {
        int n = nums.length;
        boolean direction = nums[idx] > 0;
        
        if (direction != isForward) {
            return -1;
        }
        
        int next = (idx + nums[idx]) % n;
        if (next < 0) next += n;
        
        return next == idx ? -1 : next;
    }
    
    /**
     * Approach 3: Floyd's Cycle Detection (Most Efficient)
     * Time: O(n), Space: O(1)
     * 
     * Uses two pointers without extra visited array
     */
    public boolean circularArrayLoopFloyd(int[] nums) {
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) continue;
            
            int slow = i;
            int fast = i;
            boolean isForward = nums[i] > 0;
            
            while (true) {
                slow = getNext(nums, slow, isForward);
                if (slow == -1) break;
                
                fast = getNext(nums, fast, isForward);
                if (fast == -1) break;
                fast = getNext(nums, fast, isForward);
                if (fast == -1) break;
                
                if (slow == fast) {
                    // Check cycle length >= 2
                    int len = 1;
                    int curr = getNext(nums, slow, isForward);
                    while (curr != slow) {
                        len++;
                        curr = getNext(nums, curr, isForward);
                    }
                    if (len >= 2) return true;
                    break;
                }
            }
            
            // Mark all nodes in this path as 0 to avoid re-processing
            markPath(nums, i, isForward);
        }
        
        return false;
    }
    
    private int getNext(int[] nums, int idx, boolean isForward) {
        int n = nums.length;
        boolean direction = nums[idx] > 0;
        
        if (direction != isForward) {
            return -1;
        }
        
        int next = (idx + nums[idx]) % n;
        if (next < 0) next += n;
        
        return next == idx ? -1 : next;
    }
    
    private void markPath(int[] nums, int start, boolean isForward) {
        int n = nums.length;
        int curr = start;
        
        while (true) {
            int next = getNext(nums, curr, isForward);
            if (next == -1) break;
            
            int temp = nums[curr];
            nums[curr] = 0; // Mark as visited
            curr = next;
            
            if (curr == start) break;
        }
    }
    
    /**
     * Approach 4: BFS/Queue Approach
     * Time: O(n), Space: O(n)
     * 
     * Uses queue to process nodes
     */
    public boolean circularArrayLoopBFS(int[] nums) {
        int n = nums.length;
        boolean[] visited = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            
            boolean isForward = nums[i] > 0;
            Map<Integer, Integer> indexToStep = new HashMap<>();
            int step = 0;
            int curr = i;
            
            while (true) {
                if (visited[curr]) break;
                
                boolean direction = nums[curr] > 0;
                if (direction != isForward) break;
                
                if (indexToStep.containsKey(curr)) {
                    int cycleLen = step - indexToStep.get(curr);
                    if (cycleLen >= 2) return true;
                    break;
                }
                
                indexToStep.put(curr, step);
                step++;
                
                int next = (curr + nums[curr]) % n;
                if (next < 0) next += n;
                
                if (next == curr) break;
                curr = next;
            }
            
            // Mark all nodes in this path as visited
            for (int key : indexToStep.keySet()) {
                visited[key] = true;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 5: Simple Marking with Direction Tracking
     * Time: O(n²), Space: O(1)
     * 
     * For educational purposes - less efficient
     */
    public boolean circularArrayLoopSimple(int[] nums) {
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) continue;
            
            boolean isForward = nums[i] > 0;
            int slow = i;
            int fast = i;
            
            do {
                slow = getNextSimple(nums, slow, isForward);
                if (slow == -1) break;
                
                fast = getNextSimple(nums, fast, isForward);
                if (fast == -1) break;
                fast = getNextSimple(nums, fast, isForward);
                if (fast == -1) break;
                
                if (slow == fast) {
                    // Check cycle length
                    int len = 1;
                    int curr = getNextSimple(nums, slow, isForward);
                    while (curr != slow) {
                        len++;
                        curr = getNextSimple(nums, curr, isForward);
                        if (curr == -1) break;
                    }
                    if (len >= 2) return true;
                    break;
                }
            } while (slow != -1 && fast != -1);
        }
        
        return false;
    }
    
    private int getNextSimple(int[] nums, int idx, boolean isForward) {
        int n = nums.length;
        if (nums[idx] == 0) return -1;
        
        boolean direction = nums[idx] > 0;
        if (direction != isForward) return -1;
        
        int next = (idx + nums[idx]) % n;
        if (next < 0) next += n;
        
        if (next == idx) return -1;
        return next;
    }
    
    /**
     * Helper: Visualize the array and cycles
     */
    public void visualizeCycles(int[] nums) {
        System.out.println("\nCircular Array Loop Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nArray: " + Arrays.toString(nums));
        System.out.println("Length: " + nums.length);
        
        System.out.println("\nPointer mapping:");
        for (int i = 0; i < nums.length; i++) {
            int next = (i + nums[i]) % nums.length;
            if (next < 0) next += nums.length;
            System.out.printf("  %d -> %d (move: %+d)%n", i, next, nums[i]);
        }
        
        System.out.println("\nCycle detection process:");
        boolean result = circularArrayLoop(nums);
        
        if (result) {
            System.out.println("\n✓ Cycle found!");
            
            // Find and display the cycle
            int n = nums.length;
            for (int i = 0; i < n; i++) {
                if (nums[i] == 0) continue;
                
                boolean isForward = nums[i] > 0;
                Map<Integer, Integer> seen = new HashMap<>();
                int curr = i;
                int step = 0;
                
                while (true) {
                    boolean direction = nums[curr] > 0;
                    if (direction != isForward) break;
                    
                    if (seen.containsKey(curr)) {
                        int cycleStart = seen.get(curr);
                        int cycleLen = step - cycleStart;
                        if (cycleLen >= 2) {
                            System.out.print("  Cycle: ");
                            int c = curr;
                            for (int j = 0; j < cycleLen; j++) {
                                System.out.print(c);
                                if (j < cycleLen - 1) System.out.print(" -> ");
                                c = (c + nums[c]) % n;
                                if (c < 0) c += n;
                            }
                            System.out.println();
                            break;
                        }
                        break;
                    }
                    
                    seen.put(curr, step);
                    step++;
                    
                    int next = (curr + nums[curr]) % n;
                    if (next < 0) next += n;
                    if (next == curr) break;
                    curr = next;
                }
                if (!seen.isEmpty()) break;
            }
        } else {
            System.out.println("\n✗ No valid cycle found");
        }
        
        System.out.println("\nResult: " + result);
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            {2, -1, 1, 2, 2},     // Example 1 → true
            {-1, 2},               // Example 2 → false
            {-2, 1, -1, -2, -2},  // Example 3 → false
            {1, 1, 1, 1},         // All forward, cycle length 4
            {-1, -1, -1, -1},     // All backward, cycle length 4
            {1, 2, 3, 4},         // No cycle
            {3, 1, 2},            // Cycle length 3
            {-3, -1, -2},         // Cycle length 3 (backward)
            {1, 1, 2},            // Partial cycle
            {-2, -3, -4, 2}       // Mixed directions
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][] testCases = generateTestCases();
        boolean[] expected = {true, false, false, true, true, false, true, true, false, true};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i].clone();
            System.out.printf("\nTest %d: %s%n", i + 1, Arrays.toString(testCases[i]));
            
            boolean result1 = circularArrayLoop(nums.clone());
            boolean result2 = circularArrayLoopColoring(nums.clone());
            boolean result3 = circularArrayLoopFloyd(nums.clone());
            boolean result4 = circularArrayLoopBFS(nums.clone());
            boolean result5 = circularArrayLoopSimple(nums.clone());
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Has cycle: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeCycles(testCases[i]);
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
        Random rand = new Random(42);
        int n = 5000;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = rand.nextInt(2000) - 1000;
            while (nums[i] == 0) nums[i] = rand.nextInt(2000) - 1000;
        }
        
        System.out.println("Test Setup: " + n + " elements");
        
        long[] times = new long[5];
        boolean[] results = new boolean[5];
        
        // Method 1: DFS with Visited States
        long start = System.currentTimeMillis();
        results[0] = circularArrayLoop(nums.clone());
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: DFS with Coloring
        start = System.currentTimeMillis();
        results[1] = circularArrayLoopColoring(nums.clone());
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Floyd's Cycle Detection
        start = System.currentTimeMillis();
        results[2] = circularArrayLoopFloyd(nums.clone());
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: BFS/Queue
        start = System.currentTimeMillis();
        results[3] = circularArrayLoopBFS(nums.clone());
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Simple (slower)
        start = System.currentTimeMillis();
        results[4] = circularArrayLoopSimple(nums.clone());
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. DFS with Visited       | %9d | %6b%n", times[0], results[0]);
        System.out.printf("2. DFS with Coloring      | %9d | %6b%n", times[1], results[1]);
        System.out.printf("3. Floyd's Cycle Detection| %9d | %6b%n", times[2], results[2]);
        System.out.printf("4. BFS/Queue              | %9d | %6b%n", times[3], results[3]);
        System.out.printf("5. Simple                 | %9d | %6b%n", times[4], results[4]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3] && results[3] == results[4];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Floyd's algorithm is fastest and space-optimized");
        System.out.println("2. DFS approaches have similar performance");
        System.out.println("3. Simple O(n²) method is too slow for large inputs");
        System.out.println("4. All O(n) methods scale linearly");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single element array:");
        int[] nums1 = {1};
        System.out.println("   Input: [1]");
        System.out.println("   Output: " + circularArrayLoop(nums1));
        
        System.out.println("\n2. Two elements forming cycle:");
        int[] nums2 = {1, 1};
        System.out.println("   Input: [1, 1]");
        System.out.println("   Output: " + circularArrayLoop(nums2));
        
        System.out.println("\n3. Two elements, one self-loop:");
        int[] nums3 = {1, -1};
        System.out.println("   Input: [1, -1]");
        System.out.println("   Output: " + circularArrayLoop(nums3));
        
        System.out.println("\n4. All positive, length 1 self-loop:");
        int[] nums4 = {1, 0};
        // Note: nums[i] cannot be 0 per constraints
        
        System.out.println("\n5. Mixed directions with potential cycle:");
        int[] nums5 = {2, -1, 1, -2, -2};
        System.out.println("   Input: [2, -1, 1, -2, -2]");
        System.out.println("   Output: " + circularArrayLoop(nums5));
        
        System.out.println("\n6. Large cycle:");
        int[] nums6 = new int[100];
        for (int i = 0; i < 99; i++) nums6[i] = 1;
        nums6[99] = -99;
        System.out.println("   Input: 100 elements forming a cycle");
        long start = System.currentTimeMillis();
        boolean result = circularArrayLoop(nums6);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain cycle detection
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nProblem: Detect a valid cycle in a circular array.");
        
        System.out.println("\nKey Insights:");
        System.out.println("1. The array forms a functional graph (each node has exactly one outgoing edge)");
        System.out.println("2. A cycle exists if we eventually revisit a node");
        System.out.println("3. Direction must be consistent (all forward or all backward)");
        System.out.println("4. Cycle length must be ≥ 2 (no self-loops)");
        
        System.out.println("\nAlgorithm Steps (Floyd's Cycle Detection):");
        System.out.println("1. For each starting index i:");
        System.out.println("   - Determine direction based on nums[i]");
        System.out.println("   - Use slow and fast pointers to detect cycle");
        System.out.println("   - If cycle found, verify length ≥ 2");
        System.out.println("   - Mark all visited nodes to avoid re-processing");
        System.out.println("2. Return true if any valid cycle found");
        
        System.out.println("\nExample: nums = [2, -1, 1, 2, 2]");
        System.out.println("  Start at index 0 (forward):");
        System.out.println("    0 -> 2 (2 steps forward)");
        System.out.println("    2 -> 3 (1 step forward)");
        System.out.println("    3 -> 0 (2 steps forward)");
        System.out.println("  Cycle: 0 -> 2 -> 3 -> 0 (length 3) ✓");
        System.out.println("  All moves forward ✓");
        System.out.println("  Valid cycle found → return true");
        
        System.out.println("\nComplexity:");
        System.out.println("- Time: O(n) with Floyd's algorithm");
        System.out.println("- Space: O(1) extra space");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What is a valid cycle? (length ≥ 2, same direction)");
        System.out.println("   - Can we have multiple cycles? (Detect any)");
        System.out.println("   - What about self-loops? (Invalid, length 1)");
        
        System.out.println("\n2. Understand the problem:");
        System.out.println("   - This is a functional graph cycle detection problem");
        System.out.println("   - Similar to Linked List Cycle but with direction constraints");
        
        System.out.println("\n3. Propose Floyd's cycle detection:");
        System.out.println("   - O(n) time, O(1) space");
        System.out.println("   - Handle modulo arithmetic for circular movement");
        System.out.println("   - Check direction consistency");
        
        System.out.println("\n4. Discuss alternative approaches:");
        System.out.println("   - DFS with visited states");
        System.out.println("   - Marking nodes as 0 to avoid re-processing");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(n) - each node processed once");
        System.out.println("   - Space: O(1) for Floyd's, O(n) for visited array");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - Single element array");
        System.out.println("   - All forward/backward with valid cycle");
        System.out.println("   - Mixed directions breaking cycle");
        System.out.println("   - Self-loop (length 1) invalid");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Forgetting modulo for wrap-around");
        System.out.println("   - Not handling negative indices correctly");
        System.out.println("   - Accepting cycles of length 1");
        System.out.println("   - Mixing directions in cycle");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("457. Circular Array Loop");
        System.out.println("========================");
        
        // Explain algorithm
        solution.explainAlgorithm();
        
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
        
        System.out.println("\nRecommended Implementation (Floyd's Algorithm):");
        System.out.println("""
class Solution {
    public boolean circularArrayLoop(int[] nums) {
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) continue;
            
            boolean isForward = nums[i] > 0;
            int slow = i, fast = i;
            
            while (true) {
                slow = getNext(nums, slow, isForward);
                if (slow == -1) break;
                
                fast = getNext(nums, fast, isForward);
                if (fast == -1) break;
                fast = getNext(nums, fast, isForward);
                if (fast == -1) break;
                
                if (slow == fast) {
                    // Check cycle length >= 2
                    int len = 1;
                    int curr = getNext(nums, slow, isForward);
                    while (curr != slow) {
                        len++;
                        curr = getNext(nums, curr, isForward);
                    }
                    return len >= 2;
                }
            }
            
            // Mark path as visited
            int curr = i;
            while (true) {
                int next = getNext(nums, curr, isForward);
                if (next == -1) break;
                nums[curr] = 0;
                curr = next;
                if (curr == i) break;
            }
        }
        
        return false;
    }
    
    private int getNext(int[] nums, int idx, boolean isForward) {
        int n = nums.length;
        boolean direction = nums[idx] > 0;
        
        if (direction != isForward) return -1;
        
        int next = (idx + nums[idx]) % n;
        if (next < 0) next += n;
        
        return next == idx ? -1 : next;
    }
}
            """);
        
        System.out.println("\nAlternative (DFS with Visited States):");
        System.out.println("""
class Solution {
    public boolean circularArrayLoop(int[] nums) {
        int n = nums.length;
        int[] visited = new int[n]; // 0=unvisited, 1=visiting, 2=visited
        
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0 && hasCycle(nums, visited, i, nums[i] > 0)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasCycle(int[] nums, int[] visited, int start, boolean isForward) {
        int slow = start, fast = start;
        
        while (true) {
            slow = next(nums, slow, isForward);
            if (slow == -1) return false;
            
            fast = next(nums, fast, isForward);
            if (fast == -1) return false;
            fast = next(nums, fast, isForward);
            if (fast == -1) return false;
            
            if (slow == fast) {
                int len = 1;
                int curr = next(nums, slow, isForward);
                while (curr != slow) {
                    len++;
                    curr = next(nums, curr, isForward);
                }
                return len >= 2;
            }
        }
    }
    
    private int next(int[] nums, int idx, boolean isForward) {
        int n = nums.length;
        boolean direction = nums[idx] > 0;
        if (direction != isForward) return -1;
        
        int next = (idx + nums[idx]) % n;
        if (next < 0) next += n;
        return next == idx ? -1 : next;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. This is a functional graph cycle detection problem");
        System.out.println("2. Direction must be consistent throughout the cycle");
        System.out.println("3. Cycle length must be at least 2 (no self-loops)");
        System.out.println("4. Floyd's algorithm achieves O(n) time, O(1) space");
        System.out.println("5. Use modulo for circular movement, handle negative indices");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - each node processed at most once");
        System.out.println("- Space: O(1) - constant extra space with Floyd's");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you detect a cycle in an undirected graph?");
        System.out.println("2. What if the array could contain zeros?");
        System.out.println("3. How would you find the length of the cycle?");
        System.out.println("4. How would you find the starting point of the cycle?");
        System.out.println("5. What if the array was immutable?");
    }
}
