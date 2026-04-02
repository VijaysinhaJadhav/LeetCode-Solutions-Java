
# Solution.java

```java
import java.util.*;

/**
 * 465. Optimal Account Balancing
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given a list of transactions, find the minimum number of new transactions
 * needed to settle all debts.
 * 
 * Key Insights:
 * 1. Calculate net balance for each person
 * 2. Only people with non-zero balance matter
 * 3. Use backtracking to try settling a debtor with each possible creditor
 * 4. Prune when current transactions >= best found so far
 */
class Solution {
    
    /**
     * Approach 1: Backtracking DFS with Pruning (Recommended)
     * Time: O(n!), Space: O(n)
     * 
     * Steps:
     * 1. Calculate net balances for all people
     * 2. Filter out zero balances
     * 3. DFS: settle first person's debt with any opposite sign balance
     * 4. Track minimum transactions
     */
    public int minTransfers(int[][] transactions) {
        // Calculate net balance for each person
        Map<Integer, Integer> balanceMap = new HashMap<>();
        for (int[] t : transactions) {
            int from = t[0];
            int to = t[1];
            int amount = t[2];
            balanceMap.put(from, balanceMap.getOrDefault(from, 0) - amount);
            balanceMap.put(to, balanceMap.getOrDefault(to, 0) + amount);
        }
        
        // Collect non-zero balances
        List<Integer> balances = new ArrayList<>();
        for (int value : balanceMap.values()) {
            if (value != 0) {
                balances.add(value);
            }
        }
        
        // Convert to array for easier indexing
        int[] debt = new int[balances.size()];
        for (int i = 0; i < balances.size(); i++) {
            debt[i] = balances.get(i);
        }
        
        return dfs(debt, 0, 0);
    }
    
    private int dfs(int[] debt, int start, int transactionCount) {
        // Skip settled people
        while (start < debt.length && debt[start] == 0) {
            start++;
        }
        
        // All debts settled
        if (start == debt.length) {
            return transactionCount;
        }
        
        int minTransactions = Integer.MAX_VALUE;
        
        // Try to settle debt[start] with any other person with opposite sign
        for (int i = start + 1; i < debt.length; i++) {
            if (debt[start] * debt[i] < 0) { // Opposite signs
                // Settle debt[start] with debt[i]
                debt[i] += debt[start];
                // Recurse
                minTransactions = Math.min(minTransactions, 
                    dfs(debt, start + 1, transactionCount + 1));
                // Backtrack
                debt[i] -= debt[start];
            }
        }
        
        return minTransactions;
    }
    
    /**
     * Approach 2: Backtracking with Global Variable for Optimization
     * Time: O(n!), Space: O(n)
     * 
     * Uses a class-level variable to track best result for pruning
     */
    private int minTransactions;
    
    public int minTransfersGlobal(int[][] transactions) {
        // Calculate net balance
        Map<Integer, Integer> balanceMap = new HashMap<>();
        for (int[] t : transactions) {
            balanceMap.put(t[0], balanceMap.getOrDefault(t[0], 0) - t[2]);
            balanceMap.put(t[1], balanceMap.getOrDefault(t[1], 0) + t[2]);
        }
        
        // Filter non-zero balances
        List<Integer> debtList = new ArrayList<>();
        for (int value : balanceMap.values()) {
            if (value != 0) {
                debtList.add(value);
            }
        }
        
        int[] debt = debtList.stream().mapToInt(i -> i).toArray();
        minTransactions = Integer.MAX_VALUE;
        
        dfsWithPruning(debt, 0, 0);
        
        return minTransactions;
    }
    
    private void dfsWithPruning(int[] debt, int start, int count) {
        // Prune: if already worse than best found
        if (count >= minTransactions) {
            return;
        }
        
        // Skip settled
        while (start < debt.length && debt[start] == 0) {
            start++;
        }
        
        // All settled
        if (start == debt.length) {
            minTransactions = Math.min(minTransactions, count);
            return;
        }
        
        // Try all opposite sign matches
        for (int i = start + 1; i < debt.length; i++) {
            if (debt[start] * debt[i] < 0) {
                debt[i] += debt[start];
                dfsWithPruning(debt, start + 1, count + 1);
                debt[i] -= debt[start];
            }
        }
    }
    
    /**
     * Approach 3: DP with Bitmask (State Compression)
     * Time: O(3^n), Space: O(2^n)
     * 
     * More advanced solution using dynamic programming on subsets
     */
    public int minTransfersDP(int[][] transactions) {
        // Calculate net balance
        Map<Integer, Integer> balanceMap = new HashMap<>();
        for (int[] t : transactions) {
            balanceMap.put(t[0], balanceMap.getOrDefault(t[0], 0) - t[2]);
            balanceMap.put(t[1], balanceMap.getOrDefault(t[1], 0) + t[2]);
        }
        
        // Collect non-zero balances
        List<Integer> debtList = new ArrayList<>();
        for (int value : balanceMap.values()) {
            if (value != 0) {
                debtList.add(value);
            }
        }
        
        int n = debtList.size();
        int[] debt = new int[n];
        for (int i = 0; i < n; i++) {
            debt[i] = debtList.get(i);
        }
        
        // DP array: dp[mask] = min transactions to settle subset represented by mask
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        // Precompute sum for each subset
        int[] sum = new int[1 << n];
        for (int mask = 1; mask < (1 << n); mask++) {
            int lsb = mask & -mask; // Lowest set bit
            int idx = Integer.numberOfTrailingZeros(lsb);
            int prevMask = mask ^ lsb;
            sum[mask] = sum[prevMask] + debt[idx];
        }
        
        // Fill DP
        for (int mask = 1; mask < (1 << n); mask++) {
            if (sum[mask] == 0) {
                // This subset can be settled internally
                dp[mask] = Integer.bitCount(mask) - 1;
                // Try splitting into smaller subsets
                int subMask = (mask - 1) & mask;
                while (subMask > 0) {
                    if (sum[subMask] == 0) {
                        dp[mask] = Math.min(dp[mask], dp[subMask] + dp[mask ^ subMask]);
                    }
                    subMask = (subMask - 1) & mask;
                }
            }
        }
        
        return dp[(1 << n) - 1];
    }
    
    /**
     * Approach 4: Simplified Recursive Backtracking
     * Time: O(n!), Space: O(n)
     * 
     * Most concise version of backtracking
     */
    public int minTransfersSimple(int[][] transactions) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] t : transactions) {
            map.put(t[0], map.getOrDefault(t[0], 0) - t[2]);
            map.put(t[1], map.getOrDefault(t[1], 0) + t[2]);
        }
        
        List<Integer> list = new ArrayList<>();
        for (int v : map.values()) {
            if (v != 0) list.add(v);
        }
        
        int[] debt = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            debt[i] = list.get(i);
        }
        
        return backtrack(debt, 0);
    }
    
    private int backtrack(int[] debt, int start) {
        while (start < debt.length && debt[start] == 0) {
            start++;
        }
        if (start == debt.length) {
            return 0;
        }
        
        int min = Integer.MAX_VALUE;
        for (int i = start + 1; i < debt.length; i++) {
            if (debt[start] * debt[i] < 0) {
                debt[i] += debt[start];
                min = Math.min(min, 1 + backtrack(debt, start + 1));
                debt[i] -= debt[start];
            }
        }
        return min;
    }
    
    /**
     * Approach 5: Optimized Backtracking with Early Exit
     * Time: O(n!), Space: O(n)
     * 
     * Adds heuristics: prioritize exact matches when possible
     */
    public int minTransfersOptimized(int[][] transactions) {
        Map<Integer, Integer> balanceMap = new HashMap<>();
        for (int[] t : transactions) {
            balanceMap.put(t[0], balanceMap.getOrDefault(t[0], 0) - t[2]);
            balanceMap.put(t[1], balanceMap.getOrDefault(t[1], 0) + t[2]);
        }
        
        List<Integer> debtList = new ArrayList<>();
        for (int value : balanceMap.values()) {
            if (value != 0) {
                debtList.add(value);
            }
        }
        
        int[] debt = new int[debtList.size()];
        for (int i = 0; i < debtList.size(); i++) {
            debt[i] = debtList.get(i);
        }
        
        return dfsOptimized(debt, 0);
    }
    
    private int dfsOptimized(int[] debt, int start) {
        // Skip zeros
        while (start < debt.length && debt[start] == 0) {
            start++;
        }
        if (start == debt.length) {
            return 0;
        }
        
        int min = Integer.MAX_VALUE;
        
        // First try exact matches (debt[start] == -debt[i])
        for (int i = start + 1; i < debt.length; i++) {
            if (debt[start] + debt[i] == 0) {
                debt[i] += debt[start];
                int result = 1 + dfsOptimized(debt, start + 1);
                min = Math.min(min, result);
                debt[i] -= debt[start];
                // Early exit if we found a perfect match
                if (min == 1) return min;
            }
        }
        
        // Then try other opposite sign matches
        for (int i = start + 1; i < debt.length; i++) {
            if (debt[start] * debt[i] < 0 && debt[start] + debt[i] != 0) {
                debt[i] += debt[start];
                min = Math.min(min, 1 + dfsOptimized(debt, start + 1));
                debt[i] -= debt[start];
            }
        }
        
        return min;
    }
    
    /**
     * Helper: Visualize the debt settlement process
     */
    public void visualizeSettlement(int[][] transactions) {
        System.out.println("\nOptimal Account Balancing Visualization:");
        System.out.println("=".repeat(70));
        
        // Calculate net balances
        Map<Integer, Integer> balanceMap = new HashMap<>();
        for (int[] t : transactions) {
            int from = t[0];
            int to = t[1];
            int amount = t[2];
            balanceMap.put(from, balanceMap.getOrDefault(from, 0) - amount);
            balanceMap.put(to, balanceMap.getOrDefault(to, 0) + amount);
            System.out.printf("Transaction: Person %d -> Person %d: $%d%n", from, to, amount);
        }
        
        System.out.println("\nNet balances after all transactions:");
        List<Integer> debts = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : balanceMap.entrySet()) {
            System.out.printf("  Person %d: %s$%d%n", 
                entry.getKey(), 
                entry.getValue() > 0 ? "+" : "", 
                entry.getValue());
            if (entry.getValue() != 0) {
                debts.add(entry.getValue());
            }
        }
        
        System.out.println("\nNon-zero balances: " + debts);
        System.out.println("Total debt sum: " + debts.stream().mapToInt(Integer::intValue).sum() + " (should be 0)");
        
        // Convert to array
        int[] debt = new int[debts.size()];
        for (int i = 0; i < debts.size(); i++) {
            debt[i] = debts.get(i);
        }
        
        System.out.println("\nBacktracking settlement process:");
        simulateBacktracking(debt.clone(), 0, 0);
        
        int result = minTransfers(transactions);
        System.out.println("\nMinimum transactions needed: " + result);
    }
    
    private void simulateBacktracking(int[] debt, int start, int depth) {
        String indent = "  ".repeat(depth);
        
        while (start < debt.length && debt[start] == 0) {
            start++;
        }
        
        if (start == debt.length) {
            System.out.println(indent + "All debts settled!");
            return;
        }
        
        System.out.println(indent + "Settling person with balance: " + debt[start]);
        
        for (int i = start + 1; i < debt.length; i++) {
            if (debt[start] * debt[i] < 0) {
                System.out.println(indent + "  Try settling with balance: " + debt[i]);
                debt[i] += debt[start];
                System.out.println(indent + "    After settlement: debt[" + i + "] = " + debt[i]);
                simulateBacktracking(debt, start + 1, depth + 1);
                debt[i] -= debt[start];
                System.out.println(indent + "    Backtrack: debt[" + i + "] = " + debt[i]);
            }
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][][] generateTestCases() {
        return new int[][][] {
            // Example 1
            {{{0,1,10}, {2,0,5}}, {2}},
            // Example 2
            {{{0,1,10}, {1,0,1}, {1,2,5}, {2,0,5}}, {1}},
            // Single transaction
            {{{0,1,10}}, {1}},
            // Circular debt
            {{{0,1,10}, {1,2,10}, {2,0,10}}, {0}},
            // Complex
            {{{0,1,5}, {1,2,10}, {2,3,5}, {3,0,10}}, {2}},
            // Multiple people
            {{{0,1,20}, {1,2,30}, {2,3,10}, {3,0,40}}, {2}}
        };
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
            int[][] transactions = testCases[i][0];
            int expected = testCases[i][1][0];
            
            System.out.printf("\nTest %d:%n", i + 1);
            System.out.println("Transactions: " + Arrays.deepToString(transactions));
            
            int result1 = minTransfers(transactions);
            int result2 = minTransfersGlobal(transactions);
            int result3 = minTransfersDP(transactions);
            int result4 = minTransfersSimple(transactions);
            int result5 = minTransfersOptimized(transactions);
            
            boolean allMatch = result1 == expected && result2 == expected &&
                              result3 == expected && result4 == expected &&
                              result5 == expected;
            
            if (allMatch) {
                System.out.println("✓ PASS - Minimum transactions: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeSettlement(transactions);
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
        
        // Generate random test case
        Random rand = new Random(42);
        int numTransactions = 8;
        int[][] transactions = new int[numTransactions][3];
        for (int i = 0; i < numTransactions; i++) {
            transactions[i][0] = rand.nextInt(12);
            transactions[i][1] = rand.nextInt(12);
            while (transactions[i][1] == transactions[i][0]) {
                transactions[i][1] = rand.nextInt(12);
            }
            transactions[i][2] = rand.nextInt(100) + 1;
        }
        
        System.out.println("Test Setup: " + numTransactions + " transactions");
        
        long[] times = new long[5];
        
        // Method 1: Basic Backtracking
        long start = System.currentTimeMillis();
        minTransfers(transactions);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Global Variable Backtracking
        start = System.currentTimeMillis();
        minTransfersGlobal(transactions);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: DP with Bitmask
        start = System.currentTimeMillis();
        minTransfersDP(transactions);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Simple Backtracking
        start = System.currentTimeMillis();
        minTransfersSimple(transactions);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Optimized Backtracking
        start = System.currentTimeMillis();
        minTransfersOptimized(transactions);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                          | Time (ms)");
        System.out.println("--------------------------------|-----------");
        System.out.printf("1. Basic Backtracking          | %9d%n", times[0]);
        System.out.printf("2. Global Variable Backtracking| %9d%n", times[1]);
        System.out.printf("3. DP with Bitmask             | %9d%n", times[2]);
        System.out.printf("4. Simple Backtracking         | %9d%n", times[3]);
        System.out.printf("5. Optimized Backtracking      | %9d%n", times[4]);
        
        System.out.println("\nObservations:");
        System.out.println("1. Backtracking approaches are fast enough for constraints (n ≤ 16)");
        System.out.println("2. DP with bitmask is more efficient for larger n");
        System.out.println("3. Pruning significantly improves performance");
        System.out.println("4. Exact match prioritization helps in some cases");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. No transactions:");
        int[][] empty = {};
        System.out.println("   Input: []");
        System.out.println("   Output: " + minTransfers(empty));
        
        System.out.println("\n2. Single transaction:");
        int[][] single = {{0,1,50}};
        System.out.println("   Input: [[0,1,50]]");
        System.out.println("   Output: " + minTransfers(single));
        
        System.out.println("\n3. Already settled:");
        int[][] settled = {{0,1,10}, {1,0,10}};
        System.out.println("   Input: [[0,1,10], [1,0,10]]");
        System.out.println("   Output: " + minTransfers(settled));
        
        System.out.println("\n4. Circular debt:");
        int[][] circular = {{0,1,10}, {1,2,10}, {2,0,10}};
        System.out.println("   Input: [[0,1,10], [1,2,10], [2,0,10]]");
        System.out.println("   Output: " + minTransfers(circular));
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nKey Insight:");
        System.out.println("After all transactions, only the net balance of each person matters.");
        System.out.println("People with zero balance are already settled and can be ignored.");
        
        System.out.println("\nWhy Backtracking Works:");
        System.out.println("1. At each step, pick the first person with non-zero balance");
        System.out.println("2. Try settling their debt with any other person with opposite sign");
        System.out.println("3. Recurse to settle remaining debts");
        System.out.println("4. Track the minimum number of transactions");
        
        System.out.println("\nOptimization Techniques:");
        System.out.println("- Pruning: Skip if current count >= best found");
        System.out.println("- Exact match: Prioritize settling exact opposite balances");
        System.out.println("- Skip zeros: Ignore already settled people");
        
        System.out.println("\nExample Walkthrough ([[0,1,10], [2,0,5]]):");
        System.out.println("  Step 1: Calculate net balances");
        System.out.println("    Person 0: -10 + 5 = -5 (owes $5)");
        System.out.println("    Person 1: +10 (owed $10)");
        System.out.println("    Person 2: -5 (owes $5)");
        System.out.println("  Step 2: Non-zero balances: [-5, 10, -5]");
        System.out.println("  Step 3: Try settling -5 with 10 → new balances: [0, 5, -5]");
        System.out.println("  Step 4: Try settling -5 with 5 → all zeros");
        System.out.println("  Result: 2 transactions");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify the problem:");
        System.out.println("   - Do we need to output the actual transactions? (No, just count)");
        System.out.println("   - Can IDs be non-consecutive? (Yes)");
        System.out.println("   - What's the maximum number of people? (Up to 12)");
        
        System.out.println("\n2. Start with net balance calculation:");
        System.out.println("   - Show how to compute net balance for each person");
        System.out.println("   - Explain that original transaction order doesn't matter");
        
        System.out.println("\n3. Propose backtracking solution:");
        System.out.println("   - Pick first non-zero balance");
        System.out.println("   - Try settling with any opposite sign balance");
        System.out.println("   - Recurse and track minimum");
        
        System.out.println("\n4. Discuss complexity:");
        System.out.println("   - Time: O(n!) where n is number of non-zero balances");
        System.out.println("   - With constraints (n ≤ 16), this is acceptable");
        
        System.out.println("\n5. Mention optimization techniques:");
        System.out.println("   - Pruning with global best");
        System.out.println("   - Prioritizing exact matches");
        System.out.println("   - DP with bitmask for larger inputs");
        
        System.out.println("\n6. Edge cases to consider:");
        System.out.println("   - Empty transactions");
        System.out.println("   - Already settled debts");
        System.out.println("   - Circular debts (sum to zero)");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("465. Optimal Account Balancing");
        System.out.println("===============================");
        
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
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public int minTransfers(int[][] transactions) {
        // Calculate net balance
        Map<Integer, Integer> balanceMap = new HashMap<>();
        for (int[] t : transactions) {
            balanceMap.put(t[0], balanceMap.getOrDefault(t[0], 0) - t[2]);
            balanceMap.put(t[1], balanceMap.getOrDefault(t[1], 0) + t[2]);
        }
        
        // Collect non-zero balances
        List<Integer> debtList = new ArrayList<>();
        for (int value : balanceMap.values()) {
            if (value != 0) debtList.add(value);
        }
        
        int[] debt = new int[debtList.size()];
        for (int i = 0; i < debtList.size(); i++) {
            debt[i] = debtList.get(i);
        }
        
        return dfs(debt, 0);
    }
    
    private int dfs(int[] debt, int start) {
        while (start < debt.length && debt[start] == 0) start++;
        if (start == debt.length) return 0;
        
        int min = Integer.MAX_VALUE;
        for (int i = start + 1; i < debt.length; i++) {
            if (debt[start] * debt[i] < 0) {
                debt[i] += debt[start];
                min = Math.min(min, 1 + dfs(debt, start + 1));
                debt[i] -= debt[start];
            }
        }
        return min;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Calculate net balances first");
        System.out.println("2. Only people with non-zero balances matter");
        System.out.println("3. Backtracking explores all valid settlements");
        System.out.println("4. Pruning optimizes the search");
        System.out.println("5. Time complexity: O(n!) where n ≤ 16");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle larger constraints? (Use DP with bitmask)");
        System.out.println("2. What if you need to output the actual transactions?");
        System.out.println("3. How would you minimize the total amount transferred?");
        System.out.println("4. What if transactions can be fractional?");
    }
}
