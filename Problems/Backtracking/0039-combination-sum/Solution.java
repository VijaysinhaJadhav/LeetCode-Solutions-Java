
## Problems/Arrays-Hashing/0039-combination-sum/Solution.java

```java
/**
 * 39. Combination Sum
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of distinct integers candidates and a target integer target, 
 * return a list of all unique combinations of candidates where the chosen numbers sum to target.
 * The same number may be chosen from candidates an unlimited number of times.
 * 
 * Key Insights:
 * 1. Backtracking: Build combinations incrementally
 * 2. Can use elements multiple times (unbounded)
 * 3. Sort candidates to enable pruning
 * 4. At each step: include current candidate or move to next candidate
 * 5. Prune when remaining target < 0 or current candidate > remaining target
 * 
 * Approach (Backtracking with Sorting and Pruning):
 * 1. Sort candidates to enable efficient pruning
 * 2. Use backtracking to build combinations
 * 3. For each candidate, try including it multiple times
 * 4. When including, don't increment index (allow reuse)
 * 5. When excluding, move to next candidate
 * 6. Prune when remaining target < candidate
 * 
 * Time Complexity: O(N^(T/M + 1)) where N = candidates length, T = target, M = min candidate
 * Space Complexity: O(T/M) for recursion stack
 * 
 * Tags: Array, Backtracking, DFS, Unbounded Knapsack
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Backtracking with Sorting and Pruning - RECOMMENDED
     * O(N^(T/M + 1)) time, O(T/M) space
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // Sort to enable pruning
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] candidates, int remaining, int start, 
                          List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            // Prune: if candidate is larger than remaining, skip (and all following due to sorting)
            if (candidates[i] > remaining) {
                break;
            }
            
            // Include current candidate
            current.add(candidates[i]);
            // Note: we pass 'i' (not i+1) to allow reuse of same element
            backtrack(candidates, remaining - candidates[i], i, current, result);
            // Backtrack
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Approach 2: Backtracking without Sorting (with additional checks)
     * O(N^(T/M + 1)) time, O(T/M) space - Alternative implementation
     */
    public List<List<Integer>> combinationSumNoSort(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackNoSort(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrackNoSort(int[] candidates, int remaining, int start,
                               List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        if (remaining < 0 || start >= candidates.length) {
            return;
        }
        
        // Include current candidate (allow reuse)
        current.add(candidates[start]);
        backtrackNoSort(candidates, remaining - candidates[start], start, current, result);
        current.remove(current.size() - 1);
        
        // Exclude current candidate (move to next)
        backtrackNoSort(candidates, remaining, start + 1, current, result);
    }
    
    /**
     * Approach 3: Dynamic Programming (Bottom-up)
     * O(N * T^2) time, O(T^2) space - For smaller targets
     */
    public List<List<Integer>> combinationSumDP(int[] candidates, int target) {
        // DP[i] stores all combinations that sum to i
        List<List<Integer>>[] dp = new List[target + 1];
        
        for (int i = 0; i <= target; i++) {
            dp[i] = new ArrayList<>();
        }
        
        dp[0].add(new ArrayList<>()); // Base case: empty combination for sum 0
        
        for (int candidate : candidates) {
            for (int sum = candidate; sum <= target; sum++) {
                for (List<Integer> combination : dp[sum - candidate]) {
                    List<Integer> newCombination = new ArrayList<>(combination);
                    newCombination.add(candidate);
                    dp[sum].add(newCombination);
                }
            }
        }
        
        return dp[target];
    }
    
    /**
     * Approach 4: Iterative DFS with Stack
     * O(N^(T/M + 1)) time, O(T/M) space - Non-recursive approach
     */
    public List<List<Integer>> combinationSumIterative(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        
        // Stack element: (start index, remaining target, current combination)
        Stack<int[]> indexStack = new Stack<>();
        Stack<Integer> remainingStack = new Stack<>();
        Stack<List<Integer>> combinationStack = new Stack<>();
        
        indexStack.push(0);
        remainingStack.push(target);
        combinationStack.push(new ArrayList<>());
        
        while (!indexStack.isEmpty()) {
            int start = indexStack.pop();
            int remaining = remainingStack.pop();
            List<Integer> current = combinationStack.pop();
            
            if (remaining == 0) {
                result.add(new ArrayList<>(current));
                continue;
            }
            
            for (int i = start; i < candidates.length; i++) {
                if (candidates[i] > remaining) {
                    break; // Prune due to sorting
                }
                
                List<Integer> newCombination = new ArrayList<>(current);
                newCombination.add(candidates[i]);
                
                indexStack.push(i); // Allow reuse of same element
                remainingStack.push(remaining - candidates[i]);
                combinationStack.push(newCombination);
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Backtracking with Path Reconstruction
     * O(N^(T/M + 1)) time, O(T/M) space - Tracks path explicitly
     */
    public List<List<Integer>> combinationSumPath(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void dfs(int[] candidates, int remaining, int index,
                    List<Integer> path, List<List<Integer>> result) {
        if (index == candidates.length) {
            if (remaining == 0) {
                result.add(new ArrayList<>(path));
            }
            return;
        }
        
        if (remaining == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        // Skip current candidate
        dfs(candidates, remaining, index + 1, path, result);
        
        // Take current candidate (if possible)
        if (candidates[index] <= remaining) {
            path.add(candidates[index]);
            // Note: index remains same to allow reuse
            dfs(candidates, remaining - candidates[index], index, path, result);
            path.remove(path.size() - 1);
        }
    }
    
    /**
     * Approach 6: BFS (Level by Level)
     * O(N^(T/M + 1)) time, O(N^(T/M + 1)) space - Breadth-first approach
     */
    public List<List<Integer>> combinationSumBFS(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(0, target, new ArrayList<>()));
        
        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            if (current.remaining == 0) {
                result.add(current.combination);
                continue;
            }
            
            for (int i = current.index; i < candidates.length; i++) {
                if (candidates[i] > current.remaining) {
                    break; // Prune
                }
                
                List<Integer> newCombination = new ArrayList<>(current.combination);
                newCombination.add(candidates[i]);
                queue.offer(new State(i, current.remaining - candidates[i], newCombination));
            }
        }
        
        return result;
    }
    
    private class State {
        int index;
        int remaining;
        List<Integer> combination;
        
        State(int index, int remaining, List<Integer> combination) {
            this.index = index;
            this.remaining = remaining;
            this.combination = combination;
        }
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    private void visualizeBacktracking(int[] candidates, int target) {
        System.out.println("\nCombination Sum Backtracking Visualization:");
        System.out.println("Candidates: " + Arrays.toString(candidates));
        System.out.println("Target: " + target);
        Arrays.sort(candidates);
        System.out.println("Sorted: " + Arrays.toString(candidates));
        
        System.out.println("\nBacktracking Tree:");
        System.out.println("Remaining | Current Combination | Action");
        System.out.println("----------|---------------------|--------");
        
        visualizeDFS(candidates, target, 0, new ArrayList<>(), 0);
    }
    
    private void visualizeDFS(int[] candidates, int remaining, int start, 
                             List<Integer> current, int depth) {
        String indent = "  ".repeat(depth);
        if (remaining == 0) {
            System.out.printf("%s%9d | %-19s | FOUND SOLUTION%n", indent, remaining, current.toString());
            return;
        }
        
        System.out.printf("%s%9d | %-19s | Start from index %d%n", indent, remaining, current.toString(), start);
        
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remaining) {
                System.out.printf("%s%9d | %-19s | PRUNE (candidate %d > remaining %d)%n", 
                                indent, remaining, current.toString(), candidates[i], remaining);
                break;
            }
            
            System.out.printf("%s%9d | %-19s | Include %d%n", indent, remaining, current.toString(), candidates[i]);
            current.add(candidates[i]);
            visualizeDFS(candidates, remaining - candidates[i], i, current, depth + 1);
            System.out.printf("%s%9d | %-19s | Backtrack (remove %d)%n", 
                            indent, remaining, current.toString(), candidates[i]);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Helper method to show DP table construction
     */
    private void visualizeDP(int[] candidates, int target) {
        System.out.println("\nDynamic Programming Visualization:");
        System.out.println("Candidates: " + Arrays.toString(candidates));
        System.out.println("Target: " + target);
        
        List<List<Integer>>[] dp = new List[target + 1];
        for (int i = 0; i <= target; i++) {
            dp[i] = new ArrayList<>();
        }
        dp[0].add(new ArrayList<>());
        
        System.out.println("\nDP Table Construction:");
        System.out.println("Sum | Combinations");
        System.out.println("----|-------------");
        System.out.println(" 0  | " + dp[0]);
        
        for (int candidate : candidates) {
            System.out.println("\nProcessing candidate: " + candidate);
            for (int sum = candidate; sum <= target; sum++) {
                if (!dp[sum - candidate].isEmpty()) {
                    System.out.printf("Sum %d: combinations from sum %d + candidate %d%n", 
                                    sum, sum - candidate, candidate);
                    for (List<Integer> comb : dp[sum - candidate]) {
                        List<Integer> newComb = new ArrayList<>(comb);
                        newComb.add(candidate);
                        dp[sum].add(newComb);
                        System.out.println("  -> " + newComb);
                    }
                }
            }
        }
        
        System.out.println("\nFinal result for target " + target + ": " + dp[target]);
    }
    
    /**
     * Helper method to analyze time complexity
     */
    private void analyzeComplexity(int[] candidates, int target) {
        System.out.println("\nComplexity Analysis:");
        System.out.println("Candidates: " + Arrays.toString(candidates));
        System.out.println("Target: " + target);
        
        int n = candidates.length;
        int minCandidate = Arrays.stream(candidates).min().getAsInt();
        int maxDepth = target / minCandidate;
        
        System.out.println("Number of candidates (n): " + n);
        System.out.println("Minimum candidate: " + minCandidate);
        System.out.println("Maximum depth (T/M): " + maxDepth);
        System.out.println("Worst-case time complexity: O(n^(T/M + 1)) = O(" + n + "^(" + target + "/" + minCandidate + " + 1))");
        System.out.println("Worst-case space complexity: O(T/M) = O(" + target + "/" + minCandidate + ")");
        
        // Estimate actual combinations
        List<List<Integer>> result = combinationSum(candidates, target);
        System.out.println("Actual combinations found: " + result.size());
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Combination Sum Solution:");
        System.out.println("==================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] candidates1 = {2, 3, 6, 7};
        int target1 = 7;
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(2, 2, 3),
            Arrays.asList(7)
        );
        
        long startTime = System.nanoTime();
        List<List<Integer>> result1a = solution.combinationSum(candidates1, target1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1b = solution.combinationSumNoSort(candidates1, target1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1c = solution.combinationSumDP(candidates1, target1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = areCombinationsEqual(result1a, expected1);
        boolean test1b = areCombinationsEqual(result1b, expected1);
        boolean test1c = areCombinationsEqual(result1c, expected1);
        
        System.out.println("Backtracking: " + result1a.size() + " combinations - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("No Sort: " + result1b.size() + " combinations - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("DP: " + result1c.size() + " combinations - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the processes
        solution.visualizeBacktracking(candidates1, target1);
        solution.visualizeDP(candidates1, target1);
        solution.analyzeComplexity(candidates1, target1);
        
        // Test case 2: Multiple combinations
        System.out.println("\nTest 2: Multiple combinations");
        int[] candidates2 = {2, 3, 5};
        int target2 = 8;
        List<List<Integer>> expected2 = Arrays.asList(
            Arrays.asList(2, 2, 2, 2),
            Arrays.asList(2, 3, 3),
            Arrays.asList(3, 5)
        );
        
        List<List<Integer>> result2a = solution.combinationSum(candidates2, target2);
        System.out.println("Multiple combinations: " + result2a.size() + " - " + 
                         (areCombinationsEqual(result2a, expected2) ? "PASSED" : "FAILED"));
        System.out.println("Combinations: " + result2a);
        
        // Test case 3: No solution
        System.out.println("\nTest 3: No solution");
        int[] candidates3 = {2};
        int target3 = 1;
        List<List<Integer>> expected3 = Arrays.asList();
        
        List<List<Integer>> result3a = solution.combinationSum(candidates3, target3);
        System.out.println("No solution: " + result3a.size() + " - " + 
                         (areCombinationsEqual(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Single element solution
        System.out.println("\nTest 4: Single element solution");
        int[] candidates4 = {2, 4, 6};
        int target4 = 6;
        List<List<Integer>> result4a = solution.combinationSum(candidates4, target4);
        System.out.println("Single element: " + result4a.size() + " combinations");
        System.out.println("Combinations: " + result4a);
        
        // Test case 5: Large target
        System.out.println("\nTest 5: Large target");
        int[] candidates5 = {2, 3, 5};
        int target5 = 15;
        List<List<Integer>> result5a = solution.combinationSum(candidates5, target5);
        System.out.println("Large target: " + result5a.size() + " combinations");
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Backtracking: " + time1a + " ns");
        System.out.println("  No Sort: " + time1b + " ns");
        System.out.println("  DP: " + time1c + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        int[] candidates7 = {2, 3, 5, 7};
        int target7 = 20;
        
        startTime = System.nanoTime();
        List<List<Integer>> result7a = solution.combinationSum(candidates7, target7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result7b = solution.combinationSumDP(candidates7, target7);
        long time7b = System.nanoTime() - startTime;
        
        System.out.println("Larger input:");
        System.out.println("  Backtracking: " + time7a + " ns, Combinations: " + result7a.size());
        System.out.println("  DP: " + time7b + " ns, Combinations: " + result7b.size());
        
        // Compare all approaches for consistency
        System.out.println("\nTest 8: Consistency Check");
        int[] testCandidates = {2, 3, 6};
        int testTarget = 7;
        List<List<Integer>> r1 = solution.combinationSum(testCandidates, testTarget);
        List<List<Integer>> r2 = solution.combinationSumNoSort(testCandidates, testTarget);
        List<List<Integer>> r3 = solution.combinationSumDP(testCandidates, testTarget);
        List<List<Integer>> r4 = solution.combinationSumIterative(testCandidates, testTarget);
        List<List<Integer>> r5 = solution.combinationSumBFS(testCandidates, testTarget);
        
        boolean consistent = areCombinationsEqual(r1, r2) && areCombinationsEqual(r1, r3) && 
                           areCombinationsEqual(r1, r4) && areCombinationsEqual(r1, r5);
        System.out.println("All approaches consistent: " + consistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BACKTRACKING ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We build combinations incrementally, trying to include each candidate");
        System.out.println("multiple times (unbounded). The key decisions at each step are:");
        System.out.println("  1. Include current candidate (and allow reuse)");
        System.out.println("  2. Move to next candidate (exclude current)");
        System.out.println("  3. Prune when current candidate > remaining target");
        
        System.out.println("\nWhy Backtracking works:");
        System.out.println("1. Completeness: Explores all possible combinations");
        System.out.println("2. No Duplicates: Processes candidates in order");
        System.out.println("3. Efficiency: Pruning reduces unnecessary exploration");
        System.out.println("4. Unbounded Nature: Allows element reuse naturally");
        
        System.out.println("\nVisual Example (candidates = [2,3,6,7], target = 7):");
        System.out.println("Start: remaining=7, combination=[]");
        System.out.println("  Include 2: remaining=5, combination=[2]");
        System.out.println("    Include 2: remaining=3, combination=[2,2]");
        System.out.println("      Include 2: remaining=1, combination=[2,2,2] -> Prune (2 > 1)");
        System.out.println("      Include 3: remaining=0, combination=[2,2,3] -> FOUND");
        System.out.println("    Include 3: remaining=2, combination=[2,3]");
        System.out.println("      Include 2: remaining=0, combination=[2,3,2] -> FOUND (but duplicate)");
        System.out.println("  Include 3: remaining=4, combination=[3]");
        System.out.println("    ...");
        System.out.println("  Include 7: remaining=0, combination=[7] -> FOUND");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking with Sorting (RECOMMENDED):");
        System.out.println("   Time: O(N^(T/M + 1)) - Exponential but practical");
        System.out.println("   Space: O(T/M) - Recursion stack depth");
        System.out.println("   How it works:");
        System.out.println("     - Sort candidates to enable pruning");
        System.out.println("     - Build combinations incrementally using DFS");
        System.out.println("     - Allow element reuse by not incrementing index");
        System.out.println("     - Prune when candidate > remaining target");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and widely used");
        System.out.println("     - Efficient with pruning");
        System.out.println("     - Handles unbounded nature naturally");
        System.out.println("   Cons:");
        System.out.println("     - Exponential time in worst case");
        System.out.println("   Best for: Interview settings, general use");
        
        System.out.println("\n2. Dynamic Programming:");
        System.out.println("   Time: O(N * T^2) - Polynomial for smaller targets");
        System.out.println("   Space: O(T^2) - DP table storage");
        System.out.println("   How it works:");
        System.out.println("     - Build solutions for all sums from 0 to target");
        System.out.println("     - For each candidate, extend existing combinations");
        System.out.println("     - Store all combinations for each sum");
        System.out.println("   Pros:");
        System.out.println("     - Guaranteed polynomial time for fixed target");
        System.out.println("     - No recursion overhead");
        System.out.println("   Cons:");
        System.out.println("     - High memory usage for larger targets");
        System.out.println("     - More complex to implement");
        System.out.println("   Best for: Smaller targets, when memory is not concern");
        
        System.out.println("\n3. Iterative/BFS Approaches:");
        System.out.println("   Time: O(N^(T/M + 1)) - Same as backtracking");
        System.out.println("   Space: O(N^(T/M + 1)) - Queue/stack storage");
        System.out.println("   How it works:");
        System.out.println("     - Use stack/queue instead of recursion");
        System.out.println("     - Process states level by level");
        System.out.println("     - Same logic as backtracking but iterative");
        System.out.println("   Pros:");
        System.out.println("     - No recursion depth limits");
        System.out.println("     - Clear state management");
        System.out.println("   Cons:");
        System.out.println("     - More memory usage");
        System.out.println("     - More complex state tracking");
        System.out.println("   Best for: Avoiding recursion, educational purposes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("OPTIMIZATION STRATEGIES:");
        System.out.println("1. Sorting: Enables pruning when candidate > remaining");
        System.out.println("2. Early Termination: Stop when remaining < 0");
        System.out.println("3. Index Management: Pass current index to avoid duplicates");
        System.out.println("4. Path Reconstruction: Build combinations incrementally");
        System.out.println("5. Memoization: Not needed here due to distinct candidates");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Backtracking - it's the most expected solution");
        System.out.println("2. Explain the include/exclude decisions clearly");
        System.out.println("3. Mention why sorting helps with pruning");
        System.out.println("4. Discuss time/space complexity honestly");
        System.out.println("5. Handle edge cases: no solution, single element");
        System.out.println("6. Mention alternative approaches (DP, iterative)");
        System.out.println("7. Connect to related problems (Coin Change, Subsets)");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to check if two lists of combinations are equal (order doesn't matter)
     */
    private static boolean areCombinationsEqual(List<List<Integer>> list1, List<List<Integer>> list2) {
        if (list1.size() != list2.size()) return false;
        
        // Convert to set of multisets for comparison
        Set<Map<Integer, Integer>> set1 = new HashSet<>();
        Set<Map<Integer, Integer>> set2 = new HashSet<>();
        
        for (List<Integer> combination : list1) {
            set1.add(getFrequencyMap(combination));
        }
        
        for (List<Integer> combination : list2) {
            set2.add(getFrequencyMap(combination));
        }
        
        return set1.equals(set2);
    }
    
    private static Map<Integer, Integer> getFrequencyMap(List<Integer> list) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : list) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        return freq;
    }
}
