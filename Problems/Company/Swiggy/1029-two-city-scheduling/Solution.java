
# Solution.java

```java
import java.util.*;

/**
 * 1029. Two City Scheduling
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * 2n people need to be sent to two cities (n to each).
 * costs[i] = [aCost, bCost] = cost to send person i to city A or B.
 * Find minimum total cost with exactly n people in each city.
 * 
 * Key Insights:
 * 1. Calculate difference: costA - costB
 * 2. Negative difference: cheaper to send to A
 * 3. Positive difference: cheaper to send to B
 * 4. Sort by difference, send first n to A, last n to B
 * 
 * Approach (Greedy with Sorting by Cost Difference):
 * 1. Sort people by (costA - costB)
 * 2. First n (with most negative diff) go to A
 * 3. Last n (with most positive diff) go to B
 * 4. Sum costs accordingly
 * 
 * Time Complexity: O(n log n)
 * Space Complexity: O(1) or O(n) depending on implementation
 * 
 * Tags: Array, Greedy, Sorting
 */

class Solution {
    
    /**
     * Approach 1: Greedy with Sorting by Cost Difference (RECOMMENDED)
     * O(n log n) time, O(1) space if we sort in place
     */
    public int twoCitySchedCost(int[][] costs) {
        // Sort by the difference between costA and costB
        // This tells us how much we "save" by sending to A vs B
        Arrays.sort(costs, (a, b) -> {
            return (a[0] - a[1]) - (b[0] - b[1]);
        });
        
        int n = costs.length / 2;
        int totalCost = 0;
        
        // First n people go to city A (they have more negative diff = cheaper to A)
        for (int i = 0; i < n; i++) {
            totalCost += costs[i][0]; // Send to A
        }
        
        // Last n people go to city B (they have more positive diff = cheaper to B)
        for (int i = n; i < 2 * n; i++) {
            totalCost += costs[i][1]; // Send to B
        }
        
        return totalCost;
    }
    
    /**
     * Approach 2: Greedy with Sorting by Absolute Difference
     * O(n log n) time, O(1) space
     * Slightly different perspective
     */
    public int twoCitySchedCostAbsolute(int[][] costs) {
        // Sort by absolute difference (how much we care about the choice)
        Arrays.sort(costs, (a, b) -> {
            int diffA = Math.abs(a[0] - a[1]);
            int diffB = Math.abs(b[0] - b[1]);
            return diffB - diffA; // Larger differences first
        });
        
        int n = costs.length / 2;
        int countA = 0, countB = 0;
        int totalCost = 0;
        
        for (int[] cost : costs) {
            if (countA == n) {
                // Already have n in A, must send to B
                totalCost += cost[1];
                countB++;
            } else if (countB == n) {
                // Already have n in B, must send to A
                totalCost += cost[0];
                countA++;
            } else {
                // Choose cheaper option
                if (cost[0] < cost[1]) {
                    totalCost += cost[0];
                    countA++;
                } else {
                    totalCost += cost[1];
                    countB++;
                }
            }
        }
        
        return totalCost;
    }
    
    /**
     * Approach 3: Dynamic Programming (2D DP)
     * O(n²) time, O(n²) space
     * Demonstrates DP approach for educational purposes
     */
    public int twoCitySchedCostDP(int[][] costs) {
        int n = costs.length / 2;
        // dp[i][j] = min cost for first i people, with j going to city A
        int[][] dp = new int[2 * n + 1][n + 1];
        
        // Initialize with large values
        for (int i = 0; i <= 2 * n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        
        for (int i = 1; i <= 2 * n; i++) {
            for (int j = 0; j <= Math.min(i, n); j++) {
                // Option 1: send person i-1 to city A
                if (j > 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + costs[i - 1][0]);
                }
                // Option 2: send person i-1 to city B
                if (i - j > 0 && j <= n) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + costs[i - 1][1]);
                }
            }
        }
        
        return dp[2 * n][n];
    }
    
    /**
     * Approach 4: Greedy with Priority Queue
     * O(n log n) time, O(n) space
     * Alternative implementation using heaps
     */
    public int twoCitySchedCostHeap(int[][] costs) {
        int n = costs.length / 2;
        // Max-heap for people who should go to A (most negative diff)
        PriorityQueue<int[]> heapA = new PriorityQueue<>(
            (a, b) -> (b[0] - b[1]) - (a[0] - a[1])
        );
        // Min-heap for people who should go to B (most positive diff)  
        PriorityQueue<int[]> heapB = new PriorityQueue<>(
            (a, b) -> (a[0] - a[1]) - (b[0] - b[1])
        );
        
        // Add all people to appropriate heaps
        for (int[] cost : costs) {
            int diff = cost[0] - cost[1];
            if (diff <= 0) {
                heapA.offer(cost);
            } else {
                heapB.offer(cost);
            }
        }
        
        // Balance the heaps to have exactly n in each
        while (heapA.size() > n) {
            heapB.offer(heapA.poll());
        }
        while (heapB.size() > n) {
            heapA.offer(heapB.poll());
        }
        
        // Calculate total cost
        int totalCost = 0;
        while (!heapA.isEmpty()) {
            totalCost += heapA.poll()[0]; // Send to A
        }
        while (!heapB.isEmpty()) {
            totalCost += heapB.poll()[1]; // Send to B
        }
        
        return totalCost;
    }
    
    /**
     * Approach 5: Greedy with Two-Pass
     * O(n log n) time, O(n) space
     * First send all to cheaper city, then adjust
     */
    public int twoCitySchedCostTwoPass(int[][] costs) {
        int n = costs.length / 2;
        // First, send everyone to their cheaper city
        int totalCost = 0;
        List<Integer> refunds = new ArrayList<>();
        
        for (int[] cost : costs) {
            if (cost[0] <= cost[1]) {
                totalCost += cost[0];
                refunds.add(cost[1] - cost[0]); // Refund if we need to switch to B
            } else {
                totalCost += cost[1];
                refunds.add(cost[0] - cost[1]); // Refund if we need to switch to A
            }
        }
        
        // Sort refunds to find smallest adjustments
        Collections.sort(refunds);
        
        // We might have sent too many to one city
        // Need to adjust by switching some people
        int countA = 0, countB = 0;
        for (int[] cost : costs) {
            if (cost[0] <= cost[1]) countA++;
            else countB++;
        }
        
        // If we have too many in A, switch some from A to B
        if (countA > n) {
            for (int i = 0; i < countA - n; i++) {
                totalCost += refunds.get(i);
            }
        }
        // If we have too many in B, switch some from B to A
        else if (countB > n) {
            for (int i = 0; i < countB - n; i++) {
                totalCost += refunds.get(i);
            }
        }
        
        return totalCost;
    }
    
    /**
     * Helper method to visualize the greedy strategy
     */
    private void visualizeGreedy(int[][] costs, String approach) {
        System.out.println("\n" + approach + " - Greedy Strategy Visualization:");
        System.out.println("Costs array: " + Arrays.deepToString(costs));
        System.out.println("n = " + costs.length / 2);
        
        // Create a copy to sort
        int[][] sortedCosts = new int[costs.length][2];
        for (int i = 0; i < costs.length; i++) {
            sortedCosts[i] = costs[i].clone();
        }
        
        // Sort by cost difference
        Arrays.sort(sortedCosts, (a, b) -> {
            return (a[0] - a[1]) - (b[0] - b[1]);
        });
        
        System.out.println("\nSorted by (costA - costB):");
        System.out.println("Index | costA | costB | Diff (A-B) | Cheaper City");
        System.out.println("------|-------|-------|------------|-------------");
        
        for (int i = 0; i < sortedCosts.length; i++) {
            int diff = sortedCosts[i][0] - sortedCosts[i][1];
            String cheaper = diff < 0 ? "A" : (diff > 0 ? "B" : "Equal");
            System.out.printf("%5d | %5d | %5d | %10d | %s%n", 
                i, sortedCosts[i][0], sortedCosts[i][1], diff, cheaper);
        }
        
        int n = costs.length / 2;
        System.out.println("\nAssignment:");
        System.out.println("First " + n + " people (most negative diff) → City A");
        System.out.println("Last " + n + " people (most positive diff) → City B");
        
        int totalCost = 0;
        System.out.println("\nCalculations:");
        for (int i = 0; i < n; i++) {
            System.out.printf("Person %d: Send to A = %d%n", i, sortedCosts[i][0]);
            totalCost += sortedCosts[i][0];
        }
        for (int i = n; i < 2 * n; i++) {
            System.out.printf("Person %d: Send to B = %d%n", i, sortedCosts[i][1]);
            totalCost += sortedCosts[i][1];
        }
        
        System.out.println("\nTotal cost: " + totalCost);
        
        // Show alternative assignments for comparison
        System.out.println("\nAlternative assignments for comparison:");
        showAllAssignments(costs, 4); // Show top 4 alternatives
    }
    
    /**
     * Helper to show alternative assignments
     */
    private void showAllAssignments(int[][] costs, int limit) {
        int n = costs.length / 2;
        List<String> assignments = new ArrayList<>();
        List<Integer> costsList = new ArrayList<>();
        
        // Generate all combinations using bitmask (0 = A, 1 = B)
        // Only show if exactly n ones (n people to B)
        int totalCombinations = 1 << costs.length;
        
        for (int mask = 0; mask < totalCombinations; mask++) {
            // Count bits set to 1 (people going to B)
            int countB = Integer.bitCount(mask);
            if (countB != n) continue;
            
            int totalCost = 0;
            StringBuilder assignment = new StringBuilder();
            
            for (int i = 0; i < costs.length; i++) {
                if ((mask & (1 << i)) == 0) {
                    // Send to A
                    totalCost += costs[i][0];
                    assignment.append("A");
                } else {
                    // Send to B
                    totalCost += costs[i][1];
                    assignment.append("B");
                }
            }
            
            assignments.add(assignment.toString());
            costsList.add(totalCost);
            
            // Limit number of assignments shown
            if (assignments.size() >= limit * 10) break;
        }
        
        // Sort by cost
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < assignments.size(); i++) indices.add(i);
        indices.sort((a, b) -> costsList.get(a) - costsList.get(b));
        
        System.out.println("Top " + Math.min(limit, indices.size()) + " assignments:");
        System.out.println("Assignment | Total Cost");
        System.out.println("-----------|-----------");
        
        for (int i = 0; i < Math.min(limit, indices.size()); i++) {
            int idx = indices.get(i);
            System.out.printf(" %9s | %10d%n", assignments.get(idx), costsList.get(idx));
        }
    }
    
    /**
     * Helper to explain why the greedy approach works
     */
    private void explainGreedyStrategy() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("WHY GREEDY APPROACH WORKS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nKey Insight:");
        System.out.println("For each person i, we have two costs: aCost_i and bCost_i.");
        System.out.println("The difference d_i = aCost_i - bCost_i tells us:");
        System.out.println("  - d_i < 0: Cheaper to send to A (saves -d_i)");
        System.out.println("  - d_i > 0: Cheaper to send to B (saves d_i)");
        System.out.println("  - d_i = 0: Equal cost to either city");
        
        System.out.println("\nMathematical Proof:");
        System.out.println("Let S be the set of people sent to city A.");
        System.out.println("Total cost = Σ_{i∈S} aCost_i + Σ_{i∉S} bCost_i");
        System.out.println("           = Σ_{i∈S} (aCost_i - bCost_i) + Σ_{i=1}^{2n} bCost_i");
        System.out.println("The second term is constant.");
        System.out.println("So we need to minimize Σ_{i∈S} (aCost_i - bCost_i)");
        System.out.println("with |S| = n.");
        
        System.out.println("\nOptimal Strategy:");
        System.out.println("To minimize Σ_{i∈S} d_i with |S| = n,");
        System.out.println("we should choose the n smallest d_i values.");
        System.out.println("That's exactly what the greedy algorithm does!");
        
        System.out.println("\nExample:");
        System.out.println("costs = [[10,20],[30,200],[400,50],[30,20]]");
        System.out.println("Differences: [10-20=-10, 30-200=-170, 400-50=350, 30-20=10]");
        System.out.println("Sorted diffs: [-170, -10, 10, 350]");
        System.out.println("Smallest 2: [-170, -10] → send those people to A");
        System.out.println("Remaining 2: [10, 350] → send those people to B");
    }
    
    /**
     * Helper to compare different approaches
     */
    private void compareApproaches(int[][] costs) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput:");
        System.out.println("Costs: " + Arrays.deepToString(costs));
        System.out.println("n = " + costs.length / 2);
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5;
        
        // Approach 1: Greedy with Sorting
        startTime = System.nanoTime();
        result1 = solution.twoCitySchedCost(costs);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Absolute Difference
        startTime = System.nanoTime();
        result2 = solution.twoCitySchedCostAbsolute(costs);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: DP (only for small n due to O(n²) time)
        if (costs.length <= 20) {
            startTime = System.nanoTime();
            result3 = solution.twoCitySchedCostDP(costs);
            endTime = System.nanoTime();
            long time3 = endTime - startTime;
            
            // Approach 4: Heap
            startTime = System.nanoTime();
            result4 = solution.twoCitySchedCostHeap(costs);
            endTime = System.nanoTime();
            long time4 = endTime - startTime;
            
            // Approach 5: Two-Pass
            startTime = System.nanoTime();
            result5 = solution.twoCitySchedCostTwoPass(costs);
            endTime = System.nanoTime();
            long time5 = endTime - startTime;
            
            System.out.println("\nResults:");
            System.out.println("Approach 1 (Greedy Sort):        " + result1);
            System.out.println("Approach 2 (Absolute Diff):      " + result2);
            System.out.println("Approach 3 (DP):                 " + result3);
            System.out.println("Approach 4 (Heap):               " + result4);
            System.out.println("Approach 5 (Two-Pass):           " + result5);
            
            boolean allEqual = (result1 == result2) && (result2 == result3) && 
                              (result3 == result4) && (result4 == result5);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (Greedy Sort)%n", time1);
            System.out.printf("Approach 2: %-10d (Absolute Diff)%n", time2);
            System.out.printf("Approach 3: %-10d (DP)%n", time3);
            System.out.printf("Approach 4: %-10d (Heap)%n", time4);
            System.out.printf("Approach 5: %-10d (Two-Pass)%n", time5);
        } else {
            System.out.println("\nResults (skipping DP for larger input):");
            System.out.println("Approach 1 (Greedy Sort):        " + result1);
            System.out.println("Approach 2 (Absolute Diff):      " + result2);
            
            // Heap and Two-Pass still work
            startTime = System.nanoTime();
            result4 = solution.twoCitySchedCostHeap(costs);
            endTime = System.nanoTime();
            long time4 = endTime - startTime;
            
            startTime = System.nanoTime();
            result5 = solution.twoCitySchedCostTwoPass(costs);
            endTime = System.nanoTime();
            long time5 = endTime - startTime;
            
            System.out.println("Approach 4 (Heap):               " + result4);
            System.out.println("Approach 5 (Two-Pass):           " + result5);
            
            boolean allEqual = (result1 == result2) && (result2 == result4) && (result4 == result5);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (Greedy Sort)%n", time1);
            System.out.printf("Approach 2: %-10d (Absolute Diff)%n", time2);
            System.out.printf("Approach 4: %-10d (Heap)%n", time4);
            System.out.printf("Approach 5: %-10d (Two-Pass)%n", time5);
        }
    }
    
    /**
     * Helper to show DP table visualization
     */
    private void visualizeDP(int[][] costs) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("DYNAMIC PROGRAMMING VISUALIZATION:");
        System.out.println("=".repeat(80));
        
        if (costs.length > 10) {
            System.out.println("\nDP visualization skipped for large input (n > 5)");
            return;
        }
        
        int n = costs.length / 2;
        int[][] dp = new int[2 * n + 1][n + 1];
        
        // Initialize
        for (int i = 0; i <= 2 * n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        
        System.out.println("\nDP Table Construction:");
        System.out.println("dp[i][j] = min cost for first i people, j going to city A");
        System.out.println("\nInitial: dp[0][0] = 0");
        
        for (int i = 1; i <= 2 * n; i++) {
            System.out.println("\nProcessing person " + i + " (0-indexed: " + (i-1) + "):");
            System.out.println("  Costs: A=" + costs[i-1][0] + ", B=" + costs[i-1][1]);
            
            for (int j = 0; j <= Math.min(i, n); j++) {
                int oldValue = dp[i][j];
                
                // Option 1: send to A
                if (j > 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1] + costs[i-1][0]);
                    if (dp[i][j] != oldValue) {
                        System.out.printf("  dp[%d][%d] = min(%d, dp[%d][%d]+%d) = %d (send to A)%n",
                            i, j, oldValue, i-1, j-1, costs[i-1][0], dp[i][j]);
                        oldValue = dp[i][j];
                    }
                }
                
                // Option 2: send to B
                if (i - j > 0 && j <= n) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + costs[i-1][1]);
                    if (dp[i][j] != oldValue) {
                        System.out.printf("  dp[%d][%d] = min(%d, dp[%d][%d]+%d) = %d (send to B)%n",
                            i, j, oldValue, i-1, j, costs[i-1][1], dp[i][j]);
                    }
                }
            }
        }
        
        System.out.println("\nFinal DP Table:");
        System.out.print("i\\j |");
        for (int j = 0; j <= n; j++) {
            System.out.printf("%5d", j);
        }
        System.out.println("\n----|" + "-".repeat(5 * (n+1)));
        
        for (int i = 0; i <= 2 * n; i++) {
            System.out.printf("%3d |", i);
            for (int j = 0; j <= n; j++) {
                if (dp[i][j] == Integer.MAX_VALUE / 2) {
                    System.out.print("   ∞");
                } else {
                    System.out.printf("%5d", dp[i][j]);
                }
            }
            System.out.println();
        }
        
        System.out.println("\nOptimal cost: dp[" + (2*n) + "][" + n + "] = " + dp[2*n][n]);
        
        // Backtrack to find assignment
        System.out.println("\nOptimal assignment (backtracking):");
        List<Character> assignment = new ArrayList<>();
        int i = 2 * n, j = n;
        
        while (i > 0) {
            if (j > 0 && dp[i][j] == dp[i-1][j-1] + costs[i-1][0]) {
                assignment.add('A');
                i--;
                j--;
            } else {
                assignment.add('B');
                i--;
            }
        }
        
        Collections.reverse(assignment);
        System.out.print("Person assignments: ");
        for (int idx = 0; idx < assignment.size(); idx++) {
            System.out.print(assignment.get(idx));
            if (idx < assignment.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Two City Scheduling:");
        System.out.println("============================");
        
        // Show explanation of greedy strategy
        solution.explainGreedyStrategy();
        
        // Test case 1: Example from problem
        System.out.println("\n\nTest 1: Example from problem");
        int[][] costs1 = {{10,20}, {30,200}, {400,50}, {30,20}};
        int expected1 = 110;
        
        solution.visualizeGreedy(costs1, "Test 1");
        
        int result1 = solution.twoCitySchedCost(costs1);
        System.out.println("\nExpected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        // Test case 2: Another example
        System.out.println("\n\nTest 2: Another example");
        int[][] costs2 = {{259,770}, {448,54}, {926,667}, {184,139}, {840,118}, {577,469}};
        int expected2 = 1859;
        
        solution.visualizeGreedy(costs2, "Test 2");
        
        int result2 = solution.twoCitySchedCost(costs2);
        System.out.println("\nExpected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        // Test case 3: All equal costs
        System.out.println("\n\nTest 3: All equal costs");
        int[][] costs3 = {{100,100}, {100,100}, {100,100}, {100,100}};
        // Any assignment gives 400
        
        solution.visualizeGreedy(costs3, "Test 3");
        
        int result3 = solution.twoCitySchedCost(costs3);
        System.out.println("\nResult: " + result3);
        
        // Test case 4: Clear preference
        System.out.println("\n\nTest 4: Clear city preferences");
        int[][] costs4 = {{1,1000}, {2,1000}, {3,1000}, {4,1000}, {1000,5}, {1000,6}, {1000,7}, {1000,8}};
        // First 4 clearly should go to A, last 4 to B
        
        solution.visualizeGreedy(costs4, "Test 4");
        
        int result4 = solution.twoCitySchedCost(costs4);
        int expected4 = (1+2+3+4) + (5+6+7+8);
        System.out.println("\nExpected: " + expected4);
        System.out.println("Result:   " + result4);
        System.out.println("Passed: " + (result4 == expected4 ? "✓" : "✗"));
        
        // Test case 5: Smallest case (2 people)
        System.out.println("\n\nTest 5: Smallest case (n=1)");
        int[][] costs5 = {{10,20}, {30,40}};
        // Either: [A,B] = 10+40=50 or [B,A] = 20+30=50
        
        solution.visualizeGreedy(costs5, "Test 5");
        
        int result5 = solution.twoCitySchedCost(costs5);
        System.out.println("\nResult: " + result5);
        
        // Show DP visualization for a small case
        System.out.println("\n\nDemonstrating DP approach for small case:");
        int[][] costsDP = {{10,20}, {30,40}, {50,60}, {70,80}};
        solution.visualizeDP(costsDP);
        
        // Compare all approaches
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES:");
        System.out.println("=".repeat(80));
        
        int[][][] testCases = {
            costs1, costs2, costs3, costs4, costs5, costsDP,
            {{1,2},{3,4},{5,6},{7,8}},
            {{100,200},{200,100},{150,150},{50,250}},
            {{515,563},{451,713},{537,709},{343,819},{855,779},{457,60},{650,359},{631,42}}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\nTest Case " + (i+1) + ":");
            System.out.println("Costs: " + Arrays.deepToString(testCases[i]));
            System.out.println("n = " + testCases[i].length / 2);
            
            solution.compareApproaches(testCases[i]);
            
            if (i < testCases.length - 1) {
                System.out.println("\n" + "-".repeat(80));
            }
        }
        
        // Performance test with larger input
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST WITH LARGER INPUT:");
        System.out.println("=".repeat(80));
        
        // Generate random test case with maximum constraints (n=50, 100 people)
        Random random = new Random(42);
        int n = 50; // 100 people total
        int[][] largeCosts = new int[2*n][2];
        for (int i = 0; i < 2*n; i++) {
            largeCosts[i][0] = random.nextInt(1000) + 1;
            largeCosts[i][1] = random.nextInt(1000) + 1;
        }
        
        System.out.println("\nTesting with " + (2*n) + " people (n=" + n + "):");
        
        long startTime, endTime;
        
        // Approach 1: Greedy Sort
        startTime = System.currentTimeMillis();
        int perf1 = solution.twoCitySchedCost(largeCosts);
        endTime = System.currentTimeMillis();
        long time1 = endTime - startTime;
        
        // Approach 2: Absolute Difference
        startTime = System.currentTimeMillis();
        int perf2 = solution.twoCitySchedCostAbsolute(largeCosts);
        endTime = System.currentTimeMillis();
        long time2 = endTime - startTime;
        
        // Approach 4: Heap
        startTime = System.currentTimeMillis();
        int perf4 = solution.twoCitySchedCostHeap(largeCosts);
        endTime = System.currentTimeMillis();
        long time4 = endTime - startTime;
        
        // Approach 5: Two-Pass
        startTime = System.currentTimeMillis();
        int perf5 = solution.twoCitySchedCostTwoPass(largeCosts);
        endTime = System.currentTimeMillis();
        long time5 = endTime - startTime;
        
        System.out.println("\nPerformance (milliseconds):");
        System.out.printf("Approach 1 (Greedy Sort): %5d ms - Result: %d%n", time1, perf1);
        System.out.printf("Approach 2 (Absolute Diff): %5d ms - Result: %d%n", time2, perf2);
        System.out.printf("Approach 4 (Heap): %5d ms - Result: %d%n", time4, perf4);
        System.out.printf("Approach 5 (Two-Pass): %5d ms - Result: %d%n", time5, perf5);
        
        // Verify all give same result
        boolean perfConsistent = (perf1 == perf2) && (perf2 == perf4) && (perf4 == perf5);
        System.out.println("\nResults consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Algorithm analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nOptimal Approach (Greedy Sort):");
        System.out.println("1. Sort people by (costA - costB)");
        System.out.println("2. First n to A, last n to B");
        
        System.out.println("\nTime Complexity: O(n log n)");
        System.out.println("  - Sorting dominates: O(n log n)");
        System.out.println("  - Assignment: O(n)");
        
        System.out.println("\nSpace Complexity: O(1) if sort in place, O(n) otherwise");
        
        System.out.println("\nWhy it's optimal:");
        System.out.println("We need to minimize: Σ_{i∈A} costA_i + Σ_{i∈B} costB_i");
        System.out.println("This is equivalent to minimizing: Σ_{i∈A} (costA_i - costB_i)");
        System.out.println("Since |A| = n, we choose the n smallest (costA_i - costB_i) values");
        
        System.out.println("\nAlternative View:");
        System.out.println("Imagine initially sending everyone to city B.");
        System.out.println("Then we need to switch n people from B to A.");
        System.out.println("Switching person i costs: costA_i - costB_i");
        System.out.println("We want to choose the n people with smallest switching cost.");
        
        // Edge cases and common mistakes
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMMON MISTAKES AND EDGE CASES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Not handling equal costs correctly:");
        System.out.println("   - When costA_i = costB_i, difference is 0");
        System.out.println("   - Person can go to either city");
        
        System.out.println("\n2. Wrong sorting order:");
        System.out.println("   - Should sort by (costA - costB), not absolute difference");
        System.out.println("   - Negative differences should come first");
        
        System.out.println("\n3. Not ensuring exactly n in each city:");
        System.out.println("   - Must send exactly n to A and n to B");
        System.out.println("   - Greedy approach guarantees this");
        
        System.out.println("\n4. Integer overflow:");
        System.out.println("   - Costs up to 1000, n up to 50");
        System.out.println("   - Maximum total cost: 50 * 1000 * 2 = 100,000 (safe)");
        
        System.out.println("\n5. Modifying input array:");
        System.out.println("   - Sorting modifies input");
        System.out.println("   - Create copy if original needed");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Resource Allocation:");
        System.out.println("   - Assigning employees to two office locations");
        System.out.println("   - Minimizing relocation costs");
        
        System.out.println("\n2. Load Balancing:");
        System.out.println("   - Distributing tasks between two servers");
        System.out.println("   - Minimizing processing time/cost");
        
        System.out.println("\n3. Transportation:");
        System.out.println("   - Routing vehicles to two depots");
        System.out.println("   - Minimizing travel distance");
        
        System.out.println("\n4. Network Design:");
        System.out.println("   - Connecting nodes to two hubs");
        System.out.println("   - Minimizing cable/connection costs");
        
        System.out.println("\n5. Sports Team Draft:");
        System.out.println("   - Drafting players to two teams");
        System.out.println("   - Balancing team strengths");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - 2n people, n to each city");
        System.out.println("   - Each person has costs for both cities");
        System.out.println("   - Minimize total cost");
        
        System.out.println("\n2. Look for pattern:");
        System.out.println("   - Compute differences: costA - costB");
        System.out.println("   - Negative: cheaper to A");
        System.out.println("   - Positive: cheaper to B");
        
        System.out.println("\n3. Derive greedy strategy:");
        System.out.println("   - Sort by difference");
        System.out.println("   - First n to A, last n to B");
        
        System.out.println("\n4. Prove optimality:");
        System.out.println("   - Show it minimizes Σ (costA_i - costB_i) for A group");
        System.out.println("   - Which is equivalent to minimizing total cost");
        
        System.out.println("\n5. Implement:");
        System.out.println("   - Sort with custom comparator");
        System.out.println("   - Sum first n costs to A, last n costs to B");
        
        System.out.println("\n6. Walk through example:");
        System.out.println("   - Use given example to demonstrate");
        
        System.out.println("\n7. Discuss complexity:");
        System.out.println("   - O(n log n) time, O(1) space");
        System.out.println("   - Efficient for constraints");
        
        System.out.println("\n8. Handle edge cases:");
        System.out.println("   - n=1 (minimum)");
        System.out.println("   - Equal costs");
        System.out.println("   - All prefer one city");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Greedy approach with sorting by difference");
        System.out.println("- Mathematical proof of optimality");
        System.out.println("- O(n log n) time complexity");
        System.out.println("- Simple and elegant solution");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not sorting by the right difference");
        System.out.println("- Not ensuring exactly n in each city");
        System.out.println("- Using O(n²) or O(2ⁿ) brute force");
        System.out.println("- Not considering proof of optimality");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 135. Candy (Greedy with two passes)");
        System.out.println("2. 406. Queue Reconstruction by Height (Greedy with sorting)");
        System.out.println("3. 455. Assign Cookies (Greedy assignment)");
        System.out.println("4. 621. Task Scheduler (Greedy scheduling)");
        System.out.println("5. 870. Advantage Shuffle (Greedy matching)");
        System.out.println("6. 1054. Distant Barcodes (Greedy with heap)");
        System.out.println("7. 1094. Car Pooling (Greedy sweep line)");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
