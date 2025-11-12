
## Problems/Arrays-Hashing/0077-combinations/Solution.java

```java
/**
 * 77. Combinations
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given two integers n and k, return all possible combinations of k numbers 
 * chosen from the range [1, n]. You may return the answer in any order.
 * 
 * Key Insights:
 * 1. Need to generate all k-combinations from n elements
 * 2. Order doesn't matter in combinations (unlike permutations)
 * 3. Backtracking builds combinations incrementally
 * 4. Always move forward to avoid duplicate combinations
 * 5. Can prune when not enough elements remain
 * 
 * Approach (Backtracking):
 * 1. Start with empty combination
 * 2. For each number from start to n:
 *    - Add number to current combination
 *    - Recursively build combination with k-1 elements from remaining numbers
 *    - Backtrack (remove number)
 * 3. Add to result when combination size reaches k
 * 
 * Time Complexity: O(C(n,k) * k) - C(n,k) combinations, each taking O(k) to build
 * Space Complexity: O(k) - Recursion stack depth
 * 
 * Tags: Backtracking, DFS, Combinations, Mathematics
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Backtracking (DFS) - RECOMMENDED
     * O(C(n,k) * k) time, O(k) space
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(1, n, k, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int start, int n, int k, 
                          List<Integer> current, List<List<Integer>> result) {
        // Base case: combination is complete
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Generate combinations
        for (int i = start; i <= n; i++) {
            // Prune: if not enough elements left to complete combination
            int remainingElements = n - i + 1;
            int neededElements = k - current.size();
            if (remainingElements < neededElements) {
                break;
            }
            
            current.add(i);
            backtrack(i + 1, n, k, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Approach 2: Backtracking with Different Base Case
     * O(C(n,k) * k) time, O(k) space - Alternative implementation
     */
    public List<List<Integer>> combineAlternative(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(1, n, k, new ArrayList<>(), result);
        return result;
    }
    
    private void dfs(int start, int n, int k, 
                    List<Integer> path, List<List<Integer>> result) {
        if (k == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i <= n; i++) {
            path.add(i);
            dfs(i + 1, n, k - 1, path, result);
            path.remove(path.size() - 1);
        }
    }
    
    /**
     * Approach 3: Iterative (Using Stack)
     * O(C(n,k) * k) time, O(k) space - Non-recursive approach
     */
    public List<List<Integer>> combineIterative(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        int i = 1;
        
        while (true) {
            if (stack.size() == k) {
                result.add(new ArrayList<>(stack));
            }
            
            if (stack.size() == k || i > n) {
                if (stack.isEmpty()) {
                    break;
                }
                i = stack.pop() + 1;
            } else {
                stack.push(i);
                i++;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: Lexicographic (Bitmask) Generation
     * O(C(n,k) * k) time, O(k) space - Using bit manipulation
     */
    public List<List<Integer>> combineBitmask(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        
        // Generate all bitmasks with exactly k bits set
        for (int mask = 0; mask < (1 << n); mask++) {
            if (Integer.bitCount(mask) == k) {
                List<Integer> combination = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) != 0) {
                        combination.add(i + 1);
                    }
                }
                result.add(combination);
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Iterative (Cascading)
     * O(C(n,k) * k) time, O(C(n,k) * k) space - Build combinations level by level
     */
    public List<List<Integer>> combineIterativeCascading(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        
        for (int i = 1; i <= n; i++) {
            int size = result.size();
            for (int j = 0; j < size; j++) {
                List<Integer> combination = new ArrayList<>(result.get(j));
                combination.add(i);
                if (combination.size() == k) {
                    result.add(combination);
                } else if (combination.size() < k) {
                    result.add(combination);
                }
            }
        }
        
        // Filter to only include combinations of size k
        List<List<Integer>> finalResult = new ArrayList<>();
        for (List<Integer> comb : result) {
            if (comb.size() == k) {
                finalResult.add(comb);
            }
        }
        
        return finalResult;
    }
    
    /**
     * Approach 6: Mathematical Recursion (Using Combination Formula)
     * O(C(n,k) * k) time, O(k) space - Based on C(n,k) = C(n-1,k-1) + C(n-1,k)
     */
    public List<List<Integer>> combineMathematical(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (k == 0) {
            result.add(new ArrayList<>());
            return result;
        }
        
        if (k == n) {
            List<Integer> combination = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                combination.add(i);
            }
            result.add(combination);
            return result;
        }
        
        // Combinations that include n
        List<List<Integer>> withN = combineMathematical(n - 1, k - 1);
        for (List<Integer> comb : withN) {
            comb.add(n);
        }
        result.addAll(withN);
        
        // Combinations that don't include n
        List<List<Integer>> withoutN = combineMathematical(n - 1, k);
        result.addAll(withoutN);
        
        return result;
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    private void visualizeBacktracking(int n, int k) {
        System.out.println("\nCombinations Backtracking Visualization:");
        System.out.println("n = " + n + ", k = " + k);
        System.out.println("Total combinations: C(" + n + "," + k + ") = " + combinationsCount(n, k));
        
        System.out.println("\nBacktracking Tree:");
        System.out.println("Level | Current Combination | Action");
        System.out.println("------|---------------------|--------");
        
        visualizeDFS(1, n, k, new ArrayList<>(), 0);
    }
    
    private void visualizeDFS(int start, int n, int k, 
                            List<Integer> current, int depth) {
        String indent = "  ".repeat(depth);
        
        if (current.size() == k) {
            System.out.printf("%s%5d | %-19s | FOUND COMBINATION%n", 
                            indent, depth, current.toString());
            return;
        }
        
        System.out.printf("%s%5d | %-19s | Start from %d%n", 
                        indent, depth, current.toString(), start);
        
        for (int i = start; i <= n; i++) {
            // Check pruning condition
            int remaining = n - i + 1;
            int needed = k - current.size();
            if (remaining < needed) {
                System.out.printf("%s%5d | %-19s | PRUNE (only %d elements left, need %d)%n", 
                                indent, depth, current.toString(), remaining, needed);
                break;
            }
            
            System.out.printf("%s%5d | %-19s | Include %d%n", 
                            indent, depth, current.toString(), i);
            current.add(i);
            visualizeDFS(i + 1, n, k, current, depth + 1);
            System.out.printf("%s%5d | %-19s | Backtrack (remove %d)%n", 
                            indent, depth, current.toString(), i);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Helper method to calculate number of combinations mathematically
     */
    private int combinationsCount(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (k == 0 || k == n) return 1;
        
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - i + 1) / i;
        }
        return (int) result;
    }
    
    /**
     * Helper method to show mathematical properties
     */
    private void demonstrateMathematicalProperties(int n, int k) {
        System.out.println("\nMathematical Properties of Combinations:");
        System.out.println("C(" + n + "," + k + ") = " + combinationsCount(n, k));
        
        System.out.println("\nKey Properties:");
        System.out.println("1. Symmetry: C(n,k) = C(n,n-k)");
        System.out.println("   C(" + n + "," + k + ") = C(" + n + "," + (n-k) + ") = " + 
                         combinationsCount(n, n-k));
        
        System.out.println("2. Pascal's Identity: C(n,k) = C(n-1,k-1) + C(n-1,k)");
        System.out.println("   C(" + n + "," + k + ") = C(" + (n-1) + "," + (k-1) + ") + C(" + 
                         (n-1) + "," + k + ")");
        System.out.println("   " + combinationsCount(n, k) + " = " + combinationsCount(n-1, k-1) + 
                         " + " + combinationsCount(n-1, k));
        
        System.out.println("3. Total subsets: Σ C(n,k) for k=0 to n = 2^n");
        int total = 0;
        for (int i = 0; i <= n; i++) {
            total += combinationsCount(n, i);
        }
        System.out.println("   Σ C(" + n + ",k) = " + total + " = 2^" + n + " = " + (1 << n));
    }
    
    /**
     * Helper method to compare with permutations
     */
    private void compareWithPermutations(int n, int k) {
        System.out.println("\nCombinations vs Permutations:");
        System.out.println("Feature        | Combinations | Permutations");
        System.out.println("---------------|--------------|-------------");
        System.out.println("Order matters  | No           | Yes");
        System.out.println("Formula        | C(n,k)       | P(n,k) = n!/(n-k)!");
        System.out.println("Count for n=" + n + ",k=" + k + " | " + 
                         combinationsCount(n, k) + "          | " + permutationsCount(n, k));
        System.out.println("Generation     | Always move  | Can revisit");
        System.out.println("               | forward      | elements");
        
        System.out.println("\nExample: n=3, k=2");
        System.out.println("Combinations: [1,2], [1,3], [2,3]");
        System.out.println("Permutations: [1,2], [1,3], [2,1], [2,3], [3,1], [3,2]");
    }
    
    private int permutationsCount(int n, int k) {
        int result = 1;
        for (int i = 0; i < k; i++) {
            result *= (n - i);
        }
        return result;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Combinations Solution:");
        System.out.println("==============================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int n1 = 4, k1 = 2;
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(1, 3),
            Arrays.asList(1, 4),
            Arrays.asList(2, 3),
            Arrays.asList(2, 4),
            Arrays.asList(3, 4)
        );
        
        long startTime = System.nanoTime();
        List<List<Integer>> result1a = solution.combine(n1, k1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1b = solution.combineAlternative(n1, k1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1c = solution.combineIterative(n1, k1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = areCombinationsEqual(result1a, expected1);
        boolean test1b = areCombinationsEqual(result1b, expected1);
        boolean test1c = areCombinationsEqual(result1c, expected1);
        
        System.out.println("Backtracking: " + result1a.size() + " combinations - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Alternative: " + result1b.size() + " combinations - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Iterative: " + result1c.size() + " combinations - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the processes
        solution.visualizeBacktracking(n1, k1);
        solution.demonstrateMathematicalProperties(n1, k1);
        solution.compareWithPermutations(n1, k1);
        
        // Test case 2: k = 1 (all single elements)
        System.out.println("\nTest 2: k = 1");
        int n2 = 3, k2 = 1;
        List<List<Integer>> expected2 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2),
            Arrays.asList(3)
        );
        
        List<List<Integer>> result2a = solution.combine(n2, k2);
        System.out.println("k = 1: " + result2a.size() + " combinations - " + 
                         (areCombinationsEqual(result2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: k = n (only one combination)
        System.out.println("\nTest 3: k = n");
        int n3 = 3, k3 = 3;
        List<List<Integer>> expected3 = Arrays.asList(Arrays.asList(1, 2, 3));
        
        List<List<Integer>> result3a = solution.combine(n3, k3);
        System.out.println("k = n: " + result3a.size() + " combinations - " + 
                         (areCombinationsEqual(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Edge case n = 1, k = 1
        System.out.println("\nTest 4: Edge case n=1,k=1");
        int n4 = 1, k4 = 1;
        List<List<Integer>> expected4 = Arrays.asList(Arrays.asList(1));
        
        List<List<Integer>> result4a = solution.combine(n4, k4);
        System.out.println("n=1,k=1: " + result4a.size() + " combinations - " + 
                         (areCombinationsEqual(result4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Larger example
        System.out.println("\nTest 5: Larger example");
        int n5 = 5, k5 = 3;
        List<List<Integer>> result5a = solution.combine(n5, k5);
        System.out.println("n=5,k=3: " + result5a.size() + " combinations (expected: 10)");
        
        // Test case 6: Maximum constraint
        System.out.println("\nTest 6: Maximum constraint");
        int n6 = 20, k6 = 10;
        List<List<Integer>> result6a = solution.combine(n6, k6);
        System.out.println("n=20,k=10: " + result6a.size() + " combinations (expected: 184756)");
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Backtracking: " + time1a + " ns");
        System.out.println("  Alternative: " + time1b + " ns");
        System.out.println("  Iterative: " + time1c + " ns");
        
        // Performance test with medium input
        System.out.println("\nTest 8: Medium input performance");
        int n8 = 10, k8 = 5;
        
        startTime = System.nanoTime();
        List<List<Integer>> result8a = solution.combine(n8, k8);
        long time8a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result8b = solution.combineBitmask(n8, k8);
        long time8b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result8c = solution.combineMathematical(n8, k8);
        long time8c = System.nanoTime() - startTime;
        
        System.out.println("Medium input (n=10,k=5):");
        System.out.println("  Backtracking: " + time8a + " ns, Combinations: " + result8a.size());
        System.out.println("  Bitmask: " + time8b + " ns, Combinations: " + result8b.size());
        System.out.println("  Mathematical: " + time8c + " ns, Combinations: " + result8c.size());
        
        // Compare all approaches for consistency
        System.out.println("\nTest 9: Consistency Check");
        int testN = 4, testK = 2;
        List<List<Integer>> r1 = solution.combine(testN, testK);
        List<List<Integer>> r2 = solution.combineAlternative(testN, testK);
        List<List<Integer>> r3 = solution.combineIterative(testN, testK);
        List<List<Integer>> r4 = solution.combineBitmask(testN, testK);
        List<List<Integer>> r5 = solution.combineMathematical(testN, testK);
        
        boolean consistent = areCombinationsEqual(r1, r2) && areCombinationsEqual(r1, r3) && 
                           areCombinationsEqual(r1, r4) && areCombinationsEqual(r1, r5);
        System.out.println("All approaches consistent: " + consistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BACKTRACKING ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We build combinations incrementally, always choosing numbers in");
        System.out.println("increasing order to avoid duplicates. At each step:");
        System.out.println("  1. Choose a number from the available range");
        System.out.println("  2. Recursively build the rest of the combination");
        System.out.println("  3. Backtrack and try the next number");
        
        System.out.println("\nWhy Backtracking works for combinations:");
        System.out.println("1. Completeness: Generates all C(n,k) combinations");
        System.out.println("2. No Duplicates: Always moves forward in number sequence");
        System.out.println("3. Efficiency: O(C(n,k) * k) is optimal for output size");
        System.out.println("4. Pruning: Stops early when not enough elements remain");
        
        System.out.println("\nVisual Example (n=4, k=2):");
        System.out.println("Start: []");
        System.out.println("  Choose 1: [1]");
        System.out.println("    Choose 2: [1,2] -> FOUND");
        System.out.println("    Choose 3: [1,3] -> FOUND");
        System.out.println("    Choose 4: [1,4] -> FOUND");
        System.out.println("  Choose 2: [2]");
        System.out.println("    Choose 3: [2,3] -> FOUND");
        System.out.println("    Choose 4: [2,4] -> FOUND");
        System.out.println("  Choose 3: [3]");
        System.out.println("    Choose 4: [3,4] -> FOUND");
        System.out.println("  Choose 4: [4] -> PRUNE (not enough elements)");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking (DFS) - RECOMMENDED:");
        System.out.println("   Time: O(C(n,k) * k) - Optimal for output size");
        System.out.println("   Space: O(k) - Recursion stack depth");
        System.out.println("   How it works:");
        System.out.println("     - Build combinations incrementally using DFS");
        System.out.println("     - Always choose numbers in increasing order");
        System.out.println("     - Prune when not enough elements remain");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and widely used");
        System.out.println("     - Efficient with pruning");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Recursion overhead for large k");
        System.out.println("   Best for: Interview settings, general use");
        
        System.out.println("\n2. Iterative (Stack-based):");
        System.out.println("   Time: O(C(n,k) * k) - Same as backtracking");
        System.out.println("   Space: O(k) - Stack storage");
        System.out.println("   How it works:");
        System.out.println("     - Use explicit stack instead of recursion");
        System.out.println("     - Simulate the backtracking process iteratively");
        System.out.println("     - Same logic but without recursion");
        System.out.println("   Pros:");
        System.out.println("     - No recursion depth limits");
        System.out.println("     - Clear state management");
        System.out.println("   Cons:");
        System.out.println("     - More complex to implement");
        System.out.println("     - Less intuitive than recursion");
        System.out.println("   Best for: Avoiding recursion, educational purposes");
        
        System.out.println("\n3. Bitmask Generation:");
        System.out.println("   Time: O(2^n * n) - Less efficient for large n");
        System.out.println("   Space: O(k) - Combination storage");
        System.out.println("   How it works:");
        System.out.println("     - Generate all 2^n bitmasks");
        System.out.println("     - Filter those with exactly k bits set");
        System.out.println("     - Convert bitmask to combination");
        System.out.println("   Pros:");
        System.out.println("     - Elegant mathematical approach");
        System.out.println("     - No recursion needed");
        System.out.println("   Cons:");
        System.out.println("     - Inefficient for large n");
        System.out.println("     - Limited to n ≤ 31");
        System.out.println("   Best for: Small n, bit manipulation practice");
        
        System.out.println("\n4. Mathematical Recursion:");
        System.out.println("   Time: O(C(n,k) * k) - Based on combination formula");
        System.out.println("   Space: O(k) - Recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Use C(n,k) = C(n-1,k-1) + C(n-1,k)");
        System.out.println("     - Recursively generate combinations");
        System.out.println("   Pros:");
        System.out.println("     - Mathematically elegant");
        System.out.println("     - Direct implementation of combination formula");
        System.out.println("   Cons:");
        System.out.println("     - More recursive calls");
        System.out.println("     - Less efficient than direct backtracking");
        System.out.println("   Best for: Educational purposes, mathematical insight");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("OPTIMIZATION STRATEGIES:");
        System.out.println("1. Pruning: Stop when remaining elements < needed elements");
        System.out.println("2. Early Termination: Return when combination size = k");
        System.out.println("3. Forward-only Selection: Always choose larger numbers");
        System.out.println("4. Memory Management: Reuse data structures when possible");
        System.out.println("5. Mathematical Bounds: Use combination count for validation");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Backtracking - it's the most expected solution");
        System.out.println("2. Explain the key insight: always move forward to avoid duplicates");
        System.out.println("3. Mention the pruning optimization");
        System.out.println("4. Discuss time/space complexity in terms of C(n,k)");
        System.out.println("5. Handle edge cases: k=1, k=n, n=1");
        System.out.println("6. Mention alternative approaches if time permits");
        System.out.println("7. Connect to related problems (subsets, permutations)");
        System.out.println("8. Write clean, well-commented code with proper backtracking");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to check if two lists of combinations are equal (order doesn't matter)
     */
    private static boolean areCombinationsEqual(List<List<Integer>> list1, List<List<Integer>> list2) {
        if (list1.size() != list2.size()) return false;
        
        // Convert to set of sets for comparison (order doesn't matter in combinations)
        Set<Set<Integer>> set1 = new HashSet<>();
        Set<Set<Integer>> set2 = new HashSet<>();
        
        for (List<Integer> combination : list1) {
            set1.add(new HashSet<>(combination));
        }
        
        for (List<Integer> combination : list2) {
            set2.add(new HashSet<>(combination));
        }
        
        return set1.equals(set2);
    }
}
