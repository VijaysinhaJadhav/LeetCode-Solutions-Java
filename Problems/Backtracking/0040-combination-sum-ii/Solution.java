
## Problems/Arrays-Hashing/0040-combination-sum-ii/Solution.java

```java
/**
 * 40. Combination Sum II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a collection of candidate numbers (candidates) and a target number (target), 
 * find all unique combinations in candidates where the candidate numbers sum to target.
 * Each number in candidates may only be used once in the combination.
 * Note: The solution set must not contain duplicate combinations.
 * 
 * Key Insights:
 * 1. Each element can be used only once (bounded)
 * 2. Array may contain duplicates, but output combinations must be unique
 * 3. Sorting is essential for efficient duplicate handling
 * 4. Skip duplicates at the same level to avoid duplicate combinations
 * 5. Always move to next index since each element can be used only once
 * 
 * Approach (Backtracking with Sorting and Duplicate Skipping):
 * 1. Sort candidates to enable duplicate detection and pruning
 * 2. Use backtracking to build combinations
 * 3. For each candidate, skip if it's a duplicate at the same level
 * 4. Always move to next index (i+1) since elements can be used only once
 * 5. Prune when remaining target < candidate
 * 
 * Time Complexity: O(2^n) - Explore all subsets in worst case
 * Space Complexity: O(n) - Recursion stack depth
 * 
 * Tags: Array, Backtracking, DFS, Bounded Knapsack, Duplicate Handling
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Backtracking with Sorting and Duplicate Skipping - RECOMMENDED
     * O(2^n) time, O(n) space
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // Essential for duplicate handling
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
            // Skip duplicates at the same level
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            
            // Prune if candidate is larger than remaining
            if (candidates[i] > remaining) {
                break;
            }
            
            // Include current candidate
            current.add(candidates[i]);
            // Move to next index (i+1) since each element can be used only once
            backtrack(candidates, remaining - candidates[i], i + 1, current, result);
            // Backtrack
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Approach 2: Backtracking with Frequency Count
     * O(2^n) time, O(n) space - Alternative duplicate handling
     */
    public List<List<Integer>> combinationSum2Frequency(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        
        // Count frequencies of each number
        List<int[]> freqList = new ArrayList<>();
        for (int num : candidates) {
            if (freqList.isEmpty() || freqList.get(freqList.size() - 1)[0] != num) {
                freqList.add(new int[]{num, 1});
            } else {
                freqList.get(freqList.size() - 1)[1]++;
            }
        }
        
        backtrackFrequency(freqList, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrackFrequency(List<int[]> freqList, int remaining, int index,
                                   List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        if (index == freqList.size() || remaining < 0) {
            return;
        }
        
        int[] freq = freqList.get(index);
        int num = freq[0];
        int count = freq[1];
        
        // Try using 0 to count occurrences of current number
        for (int i = 0; i <= count; i++) {
            if (i * num > remaining) {
                break;
            }
            
            // Add i copies of current number
            for (int j = 0; j < i; j++) {
                current.add(num);
            }
            
            backtrackFrequency(freqList, remaining - i * num, index + 1, current, result);
            
            // Remove i copies of current number
            for (int j = 0; j < i; j++) {
                current.remove(current.size() - 1);
            }
        }
    }
    
    /**
     * Approach 3: Iterative DFS with Stack
     * O(2^n) time, O(n) space - Non-recursive approach
     */
    public List<List<Integer>> combinationSum2Iterative(int[] candidates, int target) {
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
                // Skip duplicates at the same level
                if (i > start && candidates[i] == candidates[i - 1]) {
                    continue;
                }
                
                if (candidates[i] > remaining) {
                    break; // Prune due to sorting
                }
                
                List<Integer> newCombination = new ArrayList<>(current);
                newCombination.add(candidates[i]);
                
                indexStack.push(i + 1); // Move to next index (no reuse)
                remainingStack.push(remaining - candidates[i]);
                combinationStack.push(newCombination);
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: Backtracking with Choose/Skip Pattern
     * O(2^n) time, O(n) space - Alternative backtracking style
     */
    public List<List<Integer>> combinationSum2ChooseSkip(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void dfs(int[] candidates, int remaining, int index,
                    List<Integer> path, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        if (index == candidates.length || remaining < 0) {
            return;
        }
        
        // Include current element
        path.add(candidates[index]);
        dfs(candidates, remaining - candidates[index], index + 1, path, result);
        path.remove(path.size() - 1);
        
        // Skip current element and all its duplicates
        int nextIndex = index + 1;
        while (nextIndex < candidates.length && candidates[nextIndex] == candidates[index]) {
            nextIndex++;
        }
        dfs(candidates, remaining, nextIndex, path, result);
    }
    
    /**
     * Approach 5: Dynamic Programming (for smaller targets)
     * O(n * target) time, O(target) space - But doesn't handle duplicates well
     * NOTE: This approach is less suitable due to duplicate handling complexity
     */
    public List<List<Integer>> combinationSum2DP(int[] candidates, int target) {
        // DP approach is complex for this problem due to duplicate handling
        // Backtracking is generally preferred
        return combinationSum2(candidates, target); // Fallback to backtracking
    }
    
    /**
     * Approach 6: BFS (Level by Level)
     * O(2^n) time, O(2^n) space - Breadth-first approach
     */
    public List<List<Integer>> combinationSum2BFS(int[] candidates, int target) {
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
                // Skip duplicates at the same level
                if (i > current.index && candidates[i] == candidates[i - 1]) {
                    continue;
                }
                
                if (candidates[i] > current.remaining) {
                    break; // Prune
                }
                
                List<Integer> newCombination = new ArrayList<>(current.combination);
                newCombination.add(candidates[i]);
                queue.offer(new State(i + 1, current.remaining - candidates[i], newCombination));
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
     * Helper method to visualize the backtracking process with duplicate handling
     */
    private void visualizeBacktracking(int[] candidates, int target) {
        System.out.println("\nCombination Sum II Backtracking Visualization:");
        System.out.println("Candidates: " + Arrays.toString(candidates));
        System.out.println("Target: " + target);
        Arrays.sort(candidates);
        System.out.println("Sorted: " + Arrays.toString(candidates));
        
        System.out.println("\nBacktracking Tree (with duplicate handling):");
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
            // Show duplicate skipping
            if (i > start && candidates[i] == candidates[i - 1]) {
                System.out.printf("%s%9d | %-19s | SKIP DUPLICATE (candidate[%d] = %d)%n", 
                                indent, remaining, current.toString(), i, candidates[i]);
                continue;
            }
            
            if (candidates[i] > remaining) {
                System.out.printf("%s%9d | %-19s | PRUNE (candidate %d > remaining %d)%n", 
                                indent, remaining, current.toString(), candidates[i], remaining);
                break;
            }
            
            System.out.printf("%s%9d | %-19s | Include %d (move to index %d)%n", 
                            indent, remaining, current.toString(), candidates[i], i + 1);
            current.add(candidates[i]);
            visualizeDFS(candidates, remaining - candidates[i], i + 1, current, depth + 1);
            System.out.printf("%s%9d | %-19s | Backtrack (remove %d)%n", 
                            indent, remaining, current.toString(), candidates[i]);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Helper method to demonstrate duplicate handling strategies
     */
    private void demonstrateDuplicateHandling(int[] candidates, int target) {
        System.out.println("\nDuplicate Handling Strategies:");
        System.out.println("Candidates: " + Arrays.toString(candidates));
        System.out.println("Target: " + target);
        Arrays.sort(candidates);
        System.out.println("Sorted: " + Arrays.toString(candidates));
        
        System.out.println("\nStrategy 1: Skip duplicates at same level");
        System.out.println("  for (int i = start; i < candidates.length; i++) {");
        System.out.println("      if (i > start && candidates[i] == candidates[i-1]) continue;");
        System.out.println("      ...");
        System.out.println("  }");
        
        System.out.println("\nWhy this works:");
        System.out.println("  - At level 0: [1,1,2,5,6,7,10]");
        System.out.println("    Use first 1, skip second 1 at same level");
        System.out.println("    This prevents duplicate combinations like [1,2,5] and [1,2,5]");
        
        System.out.println("\nStrategy 2: Frequency counting");
        System.out.println("  Convert [1,1,2,5,6,7,10] to [(1,2), (2,1), (5,1), (6,1), (7,1), (10,1)]");
        System.out.println("  For each number, try using 0 to count occurrences");
        
        System.out.println("\nComparison:");
        System.out.println("  Strategy 1: Simpler, more intuitive");
        System.out.println("  Strategy 2: More general, handles any frequency");
    }
    
    /**
     * Helper method to compare with Combination Sum I
     */
    private void compareWithCombinationSumI() {
        System.out.println("\nComparison with Combination Sum I:");
        System.out.println("Feature           | Combination Sum I | Combination Sum II");
        System.out.println("------------------|-------------------|-------------------");
        System.out.println("Element reuse     | Unlimited         | Once only");
        System.out.println("Duplicates in input| No duplicates    | May have duplicates");
        System.out.println("Duplicate handling| Not needed        | Essential");
        System.out.println("Backtracking call | backtrack(..., i) | backtrack(..., i+1)");
        System.out.println("Sorting benefit   | Pruning only      | Pruning + duplicate handling");
        
        System.out.println("\nKey differences in code:");
        System.out.println("Combination Sum I:");
        System.out.println("  backtrack(candidates, remaining - candidates[i], i, current, result);");
        System.out.println("  // Note: 'i' allows reuse");
        
        System.out.println("Combination Sum II:");
        System.out.println("  backtrack(candidates, remaining - candidates[i], i + 1, current, result);");
        System.out.println("  // Note: 'i + 1' prevents reuse");
        System.out.println("  if (i > start && candidates[i] == candidates[i-1]) continue;");
        System.out.println("  // Skip duplicates at same level");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Combination Sum II Solution:");
        System.out.println("=====================================");
        
        // Test case 1: Standard example with duplicates
        System.out.println("\nTest 1: Standard example with duplicates");
        int[] candidates1 = {10, 1, 2, 7, 6, 1, 5};
        int target1 = 8;
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(1, 1, 6),
            Arrays.asList(1, 2, 5),
            Arrays.asList(1, 7),
            Arrays.asList(2, 6)
        );
        
        long startTime = System.nanoTime();
        List<List<Integer>> result1a = solution.combinationSum2(candidates1, target1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1b = solution.combinationSum2Frequency(candidates1, target1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1c = solution.combinationSum2Iterative(candidates1, target1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = areCombinationsEqual(result1a, expected1);
        boolean test1b = areCombinationsEqual(result1b, expected1);
        boolean test1c = areCombinationsEqual(result1c, expected1);
        
        System.out.println("Backtracking: " + result1a.size() + " combinations - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Frequency: " + result1b.size() + " combinations - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Iterative: " + result1c.size() + " combinations - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the processes
        solution.visualizeBacktracking(candidates1, target1);
        solution.demonstrateDuplicateHandling(candidates1, target1);
        solution.compareWithCombinationSumI();
        
        // Test case 2: All duplicates
        System.out.println("\nTest 2: All duplicates");
        int[] candidates2 = {2, 5, 2, 1, 2};
        int target2 = 5;
        List<List<Integer>> expected2 = Arrays.asList(
            Arrays.asList(1, 2, 2),
            Arrays.asList(5)
        );
        
        List<List<Integer>> result2a = solution.combinationSum2(candidates2, target2);
        System.out.println("All duplicates: " + result2a.size() + " - " + 
                         (areCombinationsEqual(result2a, expected2) ? "PASSED" : "FAILED"));
        System.out.println("Combinations: " + result2a);
        
        // Test case 3: No duplicates in input
        System.out.println("\nTest 3: No duplicates in input");
        int[] candidates3 = {2, 3, 5};
        int target3 = 8;
        List<List<Integer>> result3a = solution.combinationSum2(candidates3, target3);
        System.out.println("No duplicates: " + result3a.size() + " combinations");
        System.out.println("Combinations: " + result3a);
        
        // Test case 4: No solution
        System.out.println("\nTest 4: No solution");
        int[] candidates4 = {2, 3, 5};
        int target4 = 1;
        List<List<Integer>> expected4 = Arrays.asList();
        
        List<List<Integer>> result4a = solution.combinationSum2(candidates4, target4);
        System.out.println("No solution: " + result4a.size() + " - " + 
                         (areCombinationsEqual(result4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Single element matches target
        System.out.println("\nTest 5: Single element matches target");
        int[] candidates5 = {1, 2, 3, 4};
        int target5 = 3;
        List<List<Integer>> result5a = solution.combinationSum2(candidates5, target5);
        System.out.println("Single match: " + result5a.size() + " combinations");
        System.out.println("Combinations: " + result5a);
        
        // Test case 6: Multiple duplicates, complex case
        System.out.println("\nTest 6: Multiple duplicates");
        int[] candidates6 = {1, 1, 1, 2, 2, 3};
        int target6 = 4;
        List<List<Integer>> result6a = solution.combinationSum2(candidates6, target6);
        System.out.println("Multiple duplicates: " + result6a.size() + " combinations");
        System.out.println("Combinations: " + result6a);
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Backtracking: " + time1a + " ns");
        System.out.println("  Frequency: " + time1b + " ns");
        System.out.println("  Iterative: " + time1c + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 8: Larger input performance");
        int[] candidates8 = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5};
        int target8 = 10;
        
        startTime = System.nanoTime();
        List<List<Integer>> result8a = solution.combinationSum2(candidates8, target8);
        long time8a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result8b = solution.combinationSum2Frequency(candidates8, target8);
        long time8b = System.nanoTime() - startTime;
        
        System.out.println("Larger input:");
        System.out.println("  Backtracking: " + time8a + " ns, Combinations: " + result8a.size());
        System.out.println("  Frequency: " + time8b + " ns, Combinations: " + result8b.size());
        
        // Compare all approaches for consistency
        System.out.println("\nTest 9: Consistency Check");
        int[] testCandidates = {1, 1, 2, 3};
        int testTarget = 4;
        List<List<Integer>> r1 = solution.combinationSum2(testCandidates, testTarget);
        List<List<Integer>> r2 = solution.combinationSum2Frequency(testCandidates, testTarget);
        List<List<Integer>> r3 = solution.combinationSum2Iterative(testCandidates, testTarget);
        List<List<Integer>> r4 = solution.combinationSum2ChooseSkip(testCandidates, testTarget);
        List<List<Integer>> r5 = solution.combinationSum2BFS(testCandidates, testTarget);
        
        boolean consistent = areCombinationsEqual(r1, r2) && areCombinationsEqual(r1, r3) && 
                           areCombinationsEqual(r1, r4) && areCombinationsEqual(r1, r5);
        System.out.println("All approaches consistent: " + consistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BACKTRACKING WITH DUPLICATE HANDLING EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The challenge is to generate unique combinations when input has duplicates.");
        System.out.println("We use two key techniques:");
        System.out.println("  1. Sorting: Groups duplicates together");
        System.out.println("  2. Level-based skipping: Skip duplicates at the same recursion level");
        
        System.out.println("\nWhy this duplicate handling works:");
        System.out.println("Consider candidates = [1,1,2], target = 3");
        System.out.println("Without skipping: [1,2] (from first 1), [1,2] (from second 1) -> DUPLICATES");
        System.out.println("With skipping: Only generate [1,2] once from first 1");
        System.out.println("The rule: At each level, if we've already used a number,");
        System.out.println("skip all subsequent duplicates at that same level.");
        
        System.out.println("\nVisual Example (candidates = [1,1,2,5,6,7,10], target = 8):");
        System.out.println("Level 0: Start with []");
        System.out.println("  Include first 1: [1], remaining=7");
        System.out.println("    Level 1: From [1]");
        System.out.println("      Include second 1: [1,1], remaining=6");
        System.out.println("      Include 2: [1,2], remaining=5");
        System.out.println("      Skip second 1 at level 1 (duplicate of first 1)");
        System.out.println("  Include second 1 at level 0: SKIP (duplicate)");
        System.out.println("  Include 2: [2], remaining=6");
        System.out.println("    ...");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking with Sorting (RECOMMENDED):");
        System.out.println("   Time: O(2^n) - Explore all subsets in worst case");
        System.out.println("   Space: O(n) - Recursion stack depth");
        System.out.println("   How it works:");
        System.out.println("     - Sort candidates to group duplicates");
        System.out.println("     - Use backtracking to build combinations");
        System.out.println("     - Skip duplicates at the same level");
        System.out.println("     - Always move to next index (no reuse)");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and widely used");
        System.out.println("     - Efficient duplicate handling");
        System.out.println("     - Clear and easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Exponential time in worst case");
        System.out.println("   Best for: Interview settings, general use");
        
        System.out.println("\n2. Frequency Counting:");
        System.out.println("   Time: O(2^n) - Same as backtracking");
        System.out.println("   Space: O(n) - Frequency list storage");
        System.out.println("   How it works:");
        System.out.println("     - Count frequencies of each unique number");
        System.out.println("     - For each number, try using 0 to max occurrences");
        System.out.println("     - Build combinations systematically");
        System.out.println("   Pros:");
        System.out.println("     - More general approach");
        System.out.println("     - Explicit frequency control");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Additional preprocessing step");
        System.out.println("   Best for: When explicit frequency control is needed");
        
        System.out.println("\n3. Iterative/BFS Approaches:");
        System.out.println("   Time: O(2^n) - Same as backtracking");
        System.out.println("   Space: O(2^n) - Queue/stack storage");
        System.out.println("   How it works:");
        System.out.println("     - Use stack/queue instead of recursion");
        System.out.println("     - Same logic but iterative");
        System.out.println("     - Handle duplicates with same rules");
        System.out.println("   Pros:");
        System.out.println("     - No recursion depth limits");
        System.out.println("     - Clear state management");
        System.out.println("   Cons:");
        System.out.println("     - More memory usage");
        System.out.println("     - More complex state tracking");
        System.out.println("   Best for: Avoiding recursion, educational purposes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DUPLICATE HANDLING STRATEGIES COMPARISON:");
        System.out.println("1. Level-based skipping (RECOMMENDED):");
        System.out.println("   if (i > start && candidates[i] == candidates[i-1]) continue;");
        System.out.println("   - Simple and efficient");
        System.out.println("   - Easy to understand and implement");
        System.out.println("2. Frequency counting:");
        System.out.println("   - Precompute frequencies");
        System.out.println("   - Try all possible counts for each number");
        System.out.println("   - More general but complex");
        System.out.println("3. Choose/Skip with jump:");
        System.out.println("   - When skipping, jump over all duplicates");
        System.out.println("   - Alternative recursive style");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Backtracking + Sorting + Duplicate skipping");
        System.out.println("2. Explain why sorting is essential for duplicate handling");
        System.out.println("3. Clearly articulate the duplicate skipping rule");
        System.out.println("4. Contrast with Combination Sum I (reuse vs no reuse)");
        System.out.println("5. Discuss time/space complexity honestly");
        System.out.println("6. Handle edge cases: all duplicates, no solution");
        System.out.println("7. Mention alternative approaches if time permits");
        System.out.println("8. Connect to related problems (Subsets II, Permutations II)");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to check if two lists of combinations are equal (order doesn't matter)
     */
    private static boolean areCombinationsEqual(List<List<Integer>> list1, List<List<Integer>> list2) {
        if (list1.size() != list2.size()) return false;
        
        // Convert to set of multisets for comparison (order doesn't matter, frequency matters)
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
