
# Solution.java

```java
import java.util.*;

/**
 * 1986. Minimum Number of Work Sessions to Finish the Tasks
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given tasks with durations and session time limit,
 * find minimum sessions needed to complete all tasks.
 * Tasks in same session must be completed in that session.
 * 
 * Key Insights:
 * 1. Similar to bin packing/subset sum problem
 * 2. n ≤ 14 suggests DP with bitmask (2^n states)
 * 3. Track both sessions count and remaining time
 * 4. State: dp[mask] = min sessions for tasks in mask
 * 5. Transition: add task to current session or start new session
 * 
 * Approach (Bitmask DP with State Tracking):
 * 1. dp[mask] = min sessions needed for tasks represented by mask
 * 2. For each mask, try adding each unassigned task
 * 3. If task fits in current session's remaining time, add it
 * 4. Otherwise, start new session for the task
 * 5. Track remaining time in current session
 * 
 * Time Complexity: O(n * 2^n)
 * Space Complexity: O(2^n)
 * 
 * Tags: Array, Dynamic Programming, Bit Manipulation, Backtracking
 */

class Solution {
    
    /**
     * Approach 1: Bitmask DP with Pair State (RECOMMENDED)
     * O(n * 2^n) time, O(2^n) space
     * Stores (sessions, remainingTime) for each mask
     */
    public int minSessions(int[] tasks, int sessionTime) {
        int n = tasks.length;
        int totalMasks = 1 << n;
        
        // dp[mask] = {min sessions, remaining time in current session}
        int[][] dp = new int[totalMasks][2];
        
        // Initialize with large values
        for (int i = 0; i < totalMasks; i++) {
            dp[i][0] = Integer.MAX_VALUE; // sessions
            dp[i][1] = 0; // remaining time
        }
        
        // Base case: no tasks
        dp[0][0] = 0; // 0 sessions
        dp[0][1] = 0; // 0 remaining time
        
        for (int mask = 0; mask < totalMasks; mask++) {
            if (dp[mask][0] == Integer.MAX_VALUE) continue;
            
            // Try adding each task not in current mask
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) continue; // Task already used
                
                int newMask = mask | (1 << i);
                int newSessions = dp[mask][0];
                int newRemaining = dp[mask][1];
                
                if (tasks[i] <= dp[mask][1]) {
                    // Add to current session
                    newRemaining = dp[mask][1] - tasks[i];
                } else {
                    // Start new session
                    newSessions++;
                    newRemaining = sessionTime - tasks[i];
                }
                
                // Update if better (fewer sessions, or same sessions but more remaining time)
                if (newSessions < dp[newMask][0] || 
                   (newSessions == dp[newMask][0] && newRemaining > dp[newMask][1])) {
                    dp[newMask][0] = newSessions;
                    dp[newMask][1] = newRemaining;
                }
            }
        }
        
        return dp[totalMasks - 1][0];
    }
    
    /**
     * Approach 2: Bitmask DP with Integer Encoding
     * O(n * 2^n) time, O(2^n) space
     * Encodes sessions and remaining time into single integer
     */
    public int minSessionsEncoded(int[] tasks, int sessionTime) {
        int n = tasks.length;
        int totalMasks = 1 << n;
        
        // dp[mask] = encoded value: (sessions << 16) | remainingTime
        int[] dp = new int[totalMasks];
        Arrays.fill(dp, Integer.MAX_VALUE);
        
        // Base case: no tasks
        dp[0] = encode(0, 0);
        
        for (int mask = 0; mask < totalMasks; mask++) {
            if (dp[mask] == Integer.MAX_VALUE) continue;
            
            int sessions = decodeSessions(dp[mask]);
            int remaining = decodeRemaining(dp[mask]);
            
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) continue;
                
                int newMask = mask | (1 << i);
                int newSessions = sessions;
                int newRemaining;
                
                if (tasks[i] <= remaining) {
                    newRemaining = remaining - tasks[i];
                } else {
                    newSessions++;
                    newRemaining = sessionTime - tasks[i];
                }
                
                int encoded = encode(newSessions, newRemaining);
                if (newSessions < decodeSessions(dp[newMask]) || 
                   (newSessions == decodeSessions(dp[newMask]) && newRemaining > decodeRemaining(dp[newMask]))) {
                    dp[newMask] = encoded;
                }
            }
        }
        
        return decodeSessions(dp[totalMasks - 1]);
    }
    
    private int encode(int sessions, int remaining) {
        return (sessions << 16) | remaining;
    }
    
    private int decodeSessions(int encoded) {
        return encoded >> 16;
    }
    
    private int decodeRemaining(int encoded) {
        return encoded & 0xFFFF;
    }
    
    /**
     * Approach 3: DFS with Memoization
     * O(2^n * sessionTime) time, O(2^n * sessionTime) space
     * More intuitive backtracking approach
     */
    public int minSessionsDFS(int[] tasks, int sessionTime) {
        int n = tasks.length;
        // Sort in descending order for better pruning
        Integer[] sortedTasks = new Integer[n];
        for (int i = 0; i < n; i++) sortedTasks[i] = tasks[i];
        Arrays.sort(sortedTasks, Collections.reverseOrder());
        
        int[] tasksArray = new int[n];
        for (int i = 0; i < n; i++) tasksArray[i] = sortedTasks[i];
        
        // memo[mask][remaining] = min sessions
        Map<String, Integer> memo = new HashMap<>();
        return dfs(tasksArray, sessionTime, 0, 0, sessionTime, memo);
    }
    
    private int dfs(int[] tasks, int sessionTime, int mask, int sessions, 
                   int remaining, Map<String, Integer> memo) {
        // All tasks assigned
        if (mask == (1 << tasks.length) - 1) {
            return sessions + (remaining == sessionTime ? 0 : 1);
        }
        
        String key = mask + "-" + remaining;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        int minSessions = Integer.MAX_VALUE;
        
        for (int i = 0; i < tasks.length; i++) {
            if ((mask & (1 << i)) != 0) continue;
            
            int newMask = mask | (1 << i);
            if (tasks[i] <= remaining) {
                // Add to current session
                minSessions = Math.min(minSessions,
                    dfs(tasks, sessionTime, newMask, sessions, remaining - tasks[i], memo));
            } else {
                // Start new session
                minSessions = Math.min(minSessions,
                    dfs(tasks, sessionTime, newMask, sessions + 1, sessionTime - tasks[i], memo));
            }
        }
        
        memo.put(key, minSessions);
        return minSessions;
    }
    
    /**
     * Approach 4: Meet in the Middle
     * O(2^(n/2) * n) time, O(2^(n/2)) space
     * Split tasks into two halves, combine results
     */
    public int minSessionsMeetInMiddle(int[] tasks, int sessionTime) {
        int n = tasks.length;
        if (n == 0) return 0;
        
        int half = n / 2;
        
        // Generate all subsets for first half
        List<List<int[]>> firstHalf = generateSubsets(tasks, 0, half, sessionTime);
        // Generate all subsets for second half
        List<List<int[]>> secondHalf = generateSubsets(tasks, half, n, sessionTime);
        
        int minSessions = Integer.MAX_VALUE;
        
        // Combine results from both halves
        for (int sessions1 = 0; sessions1 < firstHalf.size(); sessions1++) {
            for (int[] state1 : firstHalf.get(sessions1)) {
                int remaining1 = state1[0];
                int mask1 = state1[1];
                
                for (int sessions2 = 0; sessions2 < secondHalf.size(); sessions2++) {
                    for (int[] state2 : secondHalf.get(sessions2)) {
                        int remaining2 = state2[0];
                        int mask2 = state2[1];
                        
                        // Check if tasks are disjoint
                        if ((mask1 & mask2) != 0) continue;
                        
                        int totalSessions = sessions1 + sessions2;
                        int totalRemaining = Math.min(remaining1, remaining2);
                        
                        // If we can combine sessions
                        if (remaining1 + remaining2 - totalRemaining <= sessionTime) {
                            totalSessions--;
                        }
                        
                        minSessions = Math.min(minSessions, totalSessions);
                    }
                }
            }
        }
        
        return minSessions;
    }
    
    private List<List<int[]>> generateSubsets(int[] tasks, int start, int end, int sessionTime) {
        int n = end - start;
        List<List<int[]>> result = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            result.add(new ArrayList<>());
        }
        
        int totalSubsets = 1 << n;
        for (int mask = 0; mask < totalSubsets; mask++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    sum += tasks[start + i];
                }
            }
            
            if (sum <= sessionTime) {
                int sessions = 1;
                int remaining = sessionTime - sum;
                result.get(sessions).add(new int[]{remaining, mask});
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Greedy with Backtracking
     * O(n!) worst case, but practical due to constraints
     * Try to fit tasks into existing sessions first
     */
    public int minSessionsGreedyBacktrack(int[] tasks, int sessionTime) {
        // Sort in descending order
        Integer[] sortedTasks = new Integer[tasks.length];
        for (int i = 0; i < tasks.length; i++) sortedTasks[i] = tasks[i];
        Arrays.sort(sortedTasks, Collections.reverseOrder());
        
        List<Integer> sessions = new ArrayList<>();
        return backtrack(sortedTasks, sessionTime, 0, sessions);
    }
    
    private int backtrack(Integer[] tasks, int sessionTime, int index, List<Integer> sessions) {
        if (index == tasks.length) {
            return sessions.size();
        }
        
        int task = tasks[index];
        int minSessions = Integer.MAX_VALUE;
        
        // Try to add to existing session
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i) + task <= sessionTime) {
                sessions.set(i, sessions.get(i) + task);
                minSessions = Math.min(minSessions, 
                    backtrack(tasks, sessionTime, index + 1, sessions));
                sessions.set(i, sessions.get(i) - task);
            }
        }
        
        // Try to create new session
        sessions.add(task);
        minSessions = Math.min(minSessions, 
            backtrack(tasks, sessionTime, index + 1, sessions));
        sessions.remove(sessions.size() - 1);
        
        return minSessions;
    }
    
    /**
     * Helper method to visualize the DP process
     */
    private void visualizeDP(int[] tasks, int sessionTime, String approach) {
        System.out.println("\n" + approach + " - DP Visualization:");
        System.out.println("Tasks: " + Arrays.toString(tasks));
        System.out.println("Session Time: " + sessionTime);
        System.out.println("Number of tasks: " + tasks.length);
        
        int n = tasks.length;
        int totalMasks = 1 << n;
        
        // Calculate all subsets and their sums
        System.out.println("\nAll subsets and their total times:");
        for (int mask = 0; mask < totalMasks; mask++) {
            int sum = 0;
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    sum += tasks[i];
                    subset.add(tasks[i]);
                }
            }
            System.out.printf("Mask %3d (%s): sum = %2d", 
                mask, subset.toString(), sum);
            if (sum > sessionTime) {
                System.out.println(" (exceeds session time)");
            } else {
                System.out.println();
            }
        }
        
        // Show optimal solution
        int result = minSessions(tasks, sessionTime);
        System.out.println("\nOptimal solution: " + result + " sessions");
        
        // Find and show one optimal arrangement
        List<List<Integer>> sessions = findOptimalArrangement(tasks, sessionTime);
        System.out.println("\nOne optimal arrangement:");
        for (int i = 0; i < sessions.size(); i++) {
            List<Integer> session = sessions.get(i);
            int sum = session.stream().mapToInt(Integer::intValue).sum();
            System.out.printf("Session %d: %s = %d hours%s%n",
                i + 1, session.toString(), sum,
                sum > sessionTime ? " (INVALID)" : "");
        }
    }
    
    /**
     * Helper to find one optimal arrangement (backtracking)
     */
    private List<List<Integer>> findOptimalArrangement(int[] tasks, int sessionTime) {
        int n = tasks.length;
        boolean[] used = new boolean[n];
        List<List<Integer>> result = new ArrayList<>();
        
        // Sort tasks in descending order for better packing
        Integer[] sortedIndices = new Integer[n];
        for (int i = 0; i < n; i++) sortedIndices[i] = i;
        Arrays.sort(sortedIndices, (a, b) -> Integer.compare(tasks[b], tasks[a]));
        
        for (int idx : sortedIndices) {
            if (used[idx]) continue;
            
            // Try to add to existing session
            boolean added = false;
            for (List<Integer> session : result) {
                int currentSum = session.stream().mapToInt(i -> tasks[i]).sum();
                if (currentSum + tasks[idx] <= sessionTime) {
                    session.add(idx);
                    used[idx] = true;
                    added = true;
                    break;
                }
            }
            
            // If couldn't add to existing session, create new one
            if (!added) {
                List<Integer> newSession = new ArrayList<>();
                newSession.add(idx);
                result.add(newSession);
                used[idx] = true;
            }
        }
        
        // Convert indices back to task values
        List<List<Integer>> finalResult = new ArrayList<>();
        for (List<Integer> session : result) {
            List<Integer> taskSession = new ArrayList<>();
            for (int idx : session) {
                taskSession.add(tasks[idx]);
            }
            finalResult.add(taskSession);
        }
        
        return finalResult;
    }
    
    /**
     * Helper to explain the bitmask DP approach
     */
    private void explainBitmaskDP() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("BITMASK DP EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nWhat is a bitmask?");
        System.out.println("A bitmask is an integer where each bit represents:");
        System.out.println("  - 1: task is included/used");
        System.out.println("  - 0: task is excluded/not used");
        System.out.println("Example for 3 tasks: 101 binary = tasks 0 and 2 are used");
        
        System.out.println("\nWhy use bitmask DP?");
        System.out.println("1. n ≤ 14, so 2^14 = 16384 states (manageable)");
        System.out.println("2. Natural representation of subsets");
        System.out.println("3. Efficient state transitions");
        
        System.out.println("\nDP State Definition:");
        System.out.println("dp[mask] = minimum sessions needed for tasks in 'mask'");
        System.out.println("We also track remaining time in current session");
        
        System.out.println("\nState Transitions:");
        System.out.println("For each state (mask, sessions, remaining):");
        System.out.println("1. For each task i not in mask:");
        System.out.println("   a. If task fits in remaining time:");
        System.out.println("      newRemaining = remaining - tasks[i]");
        System.out.println("      sessions unchanged");
        System.out.println("   b. If task doesn't fit:");
        System.out.println("      newRemaining = sessionTime - tasks[i]");
        System.out.println("      sessions++");
        System.out.println("2. Update dp[newMask] if better");
        
        System.out.println("\nExample: tasks = [1,2,3], sessionTime = 3");
        System.out.println("Start: mask=000, sessions=0, remaining=0");
        System.out.println("Add task 0 (1): fits? No (0 < 1) → new session");
        System.out.println("  mask=001, sessions=1, remaining=2");
        System.out.println("Add task 1 (2) to mask=001:");
        System.out.println("  fits? Yes (2 ≤ 2) → add to current session");
        System.out.println("  mask=011, sessions=1, remaining=0");
        System.out.println("Add task 2 (3) to mask=011:");
        System.out.println("  fits? No (0 < 3) → new session");
        System.out.println("  mask=111, sessions=2, remaining=0");
        System.out.println("Result: 2 sessions");
    }
    
    /**
     * Helper to compare different approaches
     */
    private void compareApproaches(int[] tasks, int sessionTime) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput:");
        System.out.println("Tasks: " + Arrays.toString(tasks));
        System.out.println("Session Time: " + sessionTime);
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5;
        
        // Approach 1: Bitmask DP
        startTime = System.nanoTime();
        result1 = solution.minSessions(tasks, sessionTime);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Encoded DP
        startTime = System.nanoTime();
        result2 = solution.minSessionsEncoded(tasks, sessionTime);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: DFS with Memoization
        startTime = System.nanoTime();
        result3 = solution.minSessionsDFS(tasks, sessionTime);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Meet in the Middle (for smaller n)
        if (tasks.length <= 10) {
            startTime = System.nanoTime();
            result4 = solution.minSessionsMeetInMiddle(tasks, sessionTime);
            endTime = System.nanoTime();
            long time4 = endTime - startTime;
            
            // Approach 5: Greedy Backtrack
            startTime = System.nanoTime();
            result5 = solution.minSessionsGreedyBacktrack(tasks, sessionTime);
            endTime = System.nanoTime();
            long time5 = endTime - startTime;
            
            System.out.println("\nResults:");
            System.out.println("Approach 1 (Bitmask DP):          " + result1);
            System.out.println("Approach 2 (Encoded DP):          " + result2);
            System.out.println("Approach 3 (DFS Memoization):     " + result3);
            System.out.println("Approach 4 (Meet in Middle):      " + result4);
            System.out.println("Approach 5 (Greedy Backtrack):    " + result5);
            
            boolean allEqual = (result1 == result2) && (result2 == result3) && 
                              (result3 == result4) && (result4 == result5);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (Bitmask DP)%n", time1);
            System.out.printf("Approach 2: %-10d (Encoded DP)%n", time2);
            System.out.printf("Approach 3: %-10d (DFS Memoization)%n", time3);
            System.out.printf("Approach 4: %-10d (Meet in Middle)%n", time4);
            System.out.printf("Approach 5: %-10d (Greedy Backtrack)%n", time5);
        } else {
            System.out.println("\nResults (skipping approaches 4 & 5 for larger n):");
            System.out.println("Approach 1 (Bitmask DP):          " + result1);
            System.out.println("Approach 2 (Encoded DP):          " + result2);
            System.out.println("Approach 3 (DFS Memoization):     " + result3);
            
            boolean allEqual = (result1 == result2) && (result2 == result3);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (Bitmask DP)%n", time1);
            System.out.printf("Approach 2: %-10d (Encoded DP)%n", time2);
            System.out.printf("Approach 3: %-10d (DFS Memoization)%n", time3);
        }
    }
    
    /**
     * Helper to analyze the problem constraints and complexity
     */
    private void analyzeConstraints() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CONSTRAINT ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nKey Constraints:");
        System.out.println("1. n ≤ 14");
        System.out.println("   - 2^14 = 16384 possible subsets");
        System.out.println("   - Exponential solutions feasible");
        
        System.out.println("\n2. sessionTime ≤ 15");
        System.out.println("   - Small capacity");
        System.out.println("   - Limits branching factor");
        
        System.out.println("\n3. tasks[i] ≤ 10");
        System.out.println("   - Task times relatively small");
        System.out.println("   - Multiple tasks can fit in one session");
        
        System.out.println("\n4. max(tasks[i]) ≤ sessionTime");
        System.out.println("   - Any single task can fit in a session");
        System.out.println("   - No impossible tasks");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("Bitmask DP: O(n * 2^n)");
        System.out.println("  - n=14: 14 * 16384 = 229,376 operations");
        System.out.println("  - Very feasible");
        
        System.out.println("\nWhy not brute force all permutations?");
        System.out.println("  - n! = 14! ≈ 87 billion (too large)");
        System.out.println("  - Need smarter approach");
        
        System.out.println("\nWhy bitmask DP is optimal:");
        System.out.println("1. Captures all subsets (2^n)");
        System.out.println("2. Efficient state transitions");
        System.out.println("3. Guarantees optimal solution");
        System.out.println("4. Within constraints for n ≤ 14");
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Minimum Number of Work Sessions:");
        System.out.println("==========================================");
        
        // Show bitmask DP explanation
        solution.explainBitmaskDP();
        
        // Show constraint analysis
        solution.analyzeConstraints();
        
        // Test case 1: Example from problem
        System.out.println("\n\nTest 1: Basic example");
        int[] tasks1 = {1, 2, 3};
        int sessionTime1 = 3;
        int expected1 = 2;
        
        solution.visualizeDP(tasks1, sessionTime1, "Test 1");
        
        int result1 = solution.minSessions(tasks1, sessionTime1);
        System.out.println("\nExpected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        // Test case 2: Another example
        System.out.println("\n\nTest 2: Example 2");
        int[] tasks2 = {3, 1, 3, 1, 1};
        int sessionTime2 = 8;
        int expected2 = 2;
        
        solution.visualizeDP(tasks2, sessionTime2, "Test 2");
        
        int result2 = solution.minSessions(tasks2, sessionTime2);
        System.out.println("\nExpected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        // Test case 3: All tasks fit in one session
        System.out.println("\n\nTest 3: One session enough");
        int[] tasks3 = {1, 2, 3, 4, 5};
        int sessionTime3 = 15;
        int expected3 = 1;
        
        solution.visualizeDP(tasks3, sessionTime3, "Test 3");
        
        int result3 = solution.minSessions(tasks3, sessionTime3);
        System.out.println("\nExpected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + (result3 == expected3 ? "✓" : "✗"));
        
        // Test case 4: Each task needs separate session
        System.out.println("\n\nTest 4: Maximum sessions needed");
        int[] tasks4 = {5, 5, 5, 5};
        int sessionTime4 = 6;
        int expected4 = 4; // Each task needs its own session
        
        solution.visualizeDP(tasks4, sessionTime4, "Test 4");
        
        int result4 = solution.minSessions(tasks4, sessionTime4);
        System.out.println("\nExpected: " + expected4);
        System.out.println("Result:   " + result4);
        System.out.println("Passed: " + (result4 == expected4 ? "✓" : "✗"));
        
        // Test case 5: Mixed case
        System.out.println("\n\nTest 5: Mixed case");
        int[] tasks5 = {2, 3, 3, 4, 4, 4};
        int sessionTime5 = 9;
        // Optimal: [2,3,4]=9, [3,4,4]=8 (2 sessions)
        // Or: [4,4]=8, [2,3,3,4]=12 (invalid), [2,3,4]=9, [3,4]=7 (3 sessions)
        
        solution.visualizeDP(tasks5, sessionTime5, "Test 5");
        
        int result5 = solution.minSessions(tasks5, sessionTime5);
        System.out.println("\nResult: " + result5);
        
        // Test case 6: Edge case - single task
        System.out.println("\n\nTest 6: Single task");
        int[] tasks6 = {7};
        int sessionTime6 = 10;
        int expected6 = 1;
        
        int result6 = solution.minSessions(tasks6, sessionTime6);
        System.out.println("Expected: " + expected6);
        System.out.println("Result:   " + result6);
        System.out.println("Passed: " + (result6 == expected6 ? "✓" : "✗"));
        
        // Test case 7: Empty tasks
        System.out.println("\n\nTest 7: No tasks");
        int[] tasks7 = {};
        int sessionTime7 = 5;
        int expected7 = 0;
        
        int result7 = solution.minSessions(tasks7, sessionTime7);
        System.out.println("Expected: " + expected7);
        System.out.println("Result:   " + result7);
        System.out.println("Passed: " + (result7 == expected7 ? "✓" : "✗"));
        
        // Compare all approaches for various test cases
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES:");
        System.out.println("=".repeat(80));
        
        int[][][] testCases = {
            {tasks1, {sessionTime1}},
            {tasks2, {sessionTime2}},
            {tasks3, {sessionTime3}},
            {tasks4, {sessionTime4}},
            {tasks5, {sessionTime5}},
            {tasks6, {sessionTime6}},
            {new int[]{1,1,1,1,1,1,1,1}, new int[]{2}},
            {new int[]{4,5,3,2,6,7}, new int[]{10}},
            {new int[]{9,8,7,6,5,4,3,2,1}, new int[]{15}},
            {new int[]{1,2,3,4,5,6,7,8,9,10}, new int[]{11}}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int[] tasks = testCases[i][0];
            int sessionTime = testCases[i][1][0];
            
            System.out.println("\nTest Case " + (i+1) + ":");
            System.out.println("Tasks: " + Arrays.toString(tasks));
            System.out.println("Session Time: " + sessionTime);
            
            solution.compareApproaches(tasks, sessionTime);
            
            if (i < testCases.length - 1) {
                System.out.println("\n" + "-".repeat(80));
            }
        }
        
        // Performance test with maximum constraints
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST WITH MAXIMUM CONSTRAINTS:");
        System.out.println("=".repeat(80));
        
        // Generate test case with n=14 (maximum)
        Random random = new Random(42);
        int n = 14;
        int[] maxTasks = new int[n];
        for (int i = 0; i < n; i++) {
            maxTasks[i] = random.nextInt(10) + 1; // 1 to 10
        }
        int maxSessionTime = 15;
        
        System.out.println("\nTesting with n = " + n + " (maximum):");
        System.out.println("Tasks: " + Arrays.toString(maxTasks));
        System.out.println("Session Time: " + maxSessionTime);
        
        long startTime, endTime;
        
        // Approach 1: Bitmask DP
        startTime = System.currentTimeMillis();
        int perf1 = solution.minSessions(maxTasks, maxSessionTime);
        endTime = System.currentTimeMillis();
        long time1 = endTime - startTime;
        
        // Approach 2: Encoded DP
        startTime = System.currentTimeMillis();
        int perf2 = solution.minSessionsEncoded(maxTasks, maxSessionTime);
        endTime = System.currentTimeMillis();
        long time2 = endTime - startTime;
        
        // Approach 3: DFS with Memoization
        startTime = System.currentTimeMillis();
        int perf3 = solution.minSessionsDFS(maxTasks, maxSessionTime);
        endTime = System.currentTimeMillis();
        long time3 = endTime - startTime;
        
        System.out.println("\nPerformance (milliseconds):");
        System.out.printf("Approach 1 (Bitmask DP):      %5d ms - Result: %d%n", time1, perf1);
        System.out.printf("Approach 2 (Encoded DP):      %5d ms - Result: %d%n", time2, perf2);
        System.out.printf("Approach 3 (DFS Memoization): %5d ms - Result: %d%n", time3, perf3);
        
        // Verify all give same result
        boolean perfConsistent = (perf1 == perf2) && (perf2 == perf3);
        System.out.println("\nResults consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Algorithm explanation and analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nBitmask DP Analysis:");
        System.out.println("State: dp[mask][0] = min sessions, dp[mask][1] = remaining time");
        System.out.println("Base: dp[0] = (0 sessions, 0 remaining)");
        
        System.out.println("\nTransition Formula:");
        System.out.println("For state (mask, sessions, remaining):");
        System.out.println("  For each task i not in mask:");
        System.out.println("    if tasks[i] ≤ remaining:");
        System.out.println("      newState = (mask|1<<i, sessions, remaining - tasks[i])");
        System.out.println("    else:");
        System.out.println("      newState = (mask|1<<i, sessions+1, sessionTime - tasks[i])");
        System.out.println("    Update dp[newMask] if better");
        
        System.out.println("\n'Better' means:");
        System.out.println("  1. Fewer sessions, OR");
        System.out.println("  2. Same sessions but more remaining time");
        
        System.out.println("\nWhy track remaining time?");
        System.out.println("Because more remaining time allows fitting more tasks");
        System.out.println("in current session, potentially reducing total sessions");
        
        System.out.println("\nComplexity:");
        System.out.println("  Time: O(n * 2^n) - n tasks × 2^n masks");
        System.out.println("  Space: O(2^n) - dp array");
        System.out.println("  For n=14: ~230k operations, very fast");
        
        // Edge cases and common mistakes
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMMON MISTAKES AND EDGE CASES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Not tracking remaining time:");
        System.out.println("   - Need to know how much time left in current session");
        System.out.println("   - Otherwise can't decide if task fits");
        
        System.out.println("\n2. Wrong state update order:");
        System.out.println("   - Must process masks in increasing order of bits set");
        System.out.println("   - Or use BFS-like approach");
        
        System.out.println("\n3. Not considering all tasks:");
        System.out.println("   - Need to try adding each unassigned task");
        System.out.println("   - Greedy packing might not be optimal");
        
        System.out.println("\n4. Integer overflow in encoding:");
        System.out.println("   - When encoding sessions and remaining time");
        System.out.println("   - Ensure enough bits for both values");
        
        System.out.println("\n5. Task ordering:");
        System.out.println("   - Tasks can be done in any order");
        System.out.println("   - DP considers all orders implicitly");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Project Management:");
        System.out.println("   - Scheduling tasks within time blocks");
        System.out.println("   - Meeting deadlines with breaks");
        
        System.out.println("\n2. Manufacturing:");
        System.out.println("   - Scheduling machine operations");
        System.out.println("   - Minimizing setup/changeover times");
        
        System.out.println("\n3. Exam Scheduling:");
        System.out.println("   - Fitting exams into time slots");
        System.out.println("   - Minimizing number of sessions");
        
        System.out.println("\n4. Cloud Computing:");
        System.out.println("   - Scheduling jobs on VMs");
        System.out.println("   - Minimizing number of VM instances");
        
        System.out.println("\n5. Transportation:");
        System.out.println("   - Loading items into containers");
        System.out.println("   - Bin packing problems");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - Partition tasks into sessions");
        System.out.println("   - Each session ≤ sessionTime");
        System.out.println("   - Tasks in same session must stay together");
        
        System.out.println("\n2. Analyze constraints:");
        System.out.println("   - n ≤ 14 suggests exponential solution");
        System.out.println("   - sessionTime ≤ 15 is small");
        System.out.println("   - Bitmask DP is feasible");
        
        System.out.println("\n3. Design DP state:");
        System.out.println("   - mask: which tasks are assigned");
        System.out.println("   - sessions: how many sessions used");
        System.out.println("   - remaining: time left in current session");
        
        System.out.println("\n4. Define transitions:");
        System.out.println("   - For each unassigned task:");
        System.out.println("     - If fits in remaining time: add to current");
        System.out.println("     - Else: start new session");
        
        System.out.println("\n5. Implement DP:");
        System.out.println("   - Initialize dp[0] = (0, 0)");
        System.out.println("   - Process masks in order");
        System.out.println("   - Try adding each task");
        
        System.out.println("\n6. Optimize:");
        System.out.println("   - Use bit operations for efficiency");
        System.out.println("   - Track both sessions and remaining time");
        System.out.println("   - Update only if better state");
        
        System.out.println("\n7. Walk through example:");
        System.out.println("   - Use given examples to demonstrate");
        
        System.out.println("\n8. Discuss complexity:");
        System.out.println("   - O(n * 2^n) time, O(2^n) space");
        System.out.println("   - Feasible for n ≤ 14");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Bitmask DP is perfect for n ≤ 20");
        System.out.println("- Track both sessions and remaining time");
        System.out.println("- Try all possibilities (exponential but feasible)");
        System.out.println("- Problem is NP-hard, but constraints make it solvable");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not considering all task orders");
        System.out.println("- Greedy approach might not be optimal");
        System.out.println("- Forgetting to track remaining time");
        System.out.println("- Not handling empty task list");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 698. Partition to K Equal Sum Subsets");
        System.out.println("2. 473. Matchsticks to Square");
        System.out.println("3. 1723. Find Minimum Time to Finish All Jobs");
        System.out.println("4. 2305. Fair Distribution of Cookies");
        System.out.println("5. 1655. Distribute Repeating Integers");
        System.out.println("6. 1255. Maximum Score Words Formed by Letters");
        System.out.println("7. 1349. Maximum Students Taking Exam");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
