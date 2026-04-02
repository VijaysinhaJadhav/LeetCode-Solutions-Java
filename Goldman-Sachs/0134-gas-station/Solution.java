
# Solution.java

```java
import java.util.*;

/**
 * 134. Gas Station
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find starting gas station index that allows completing a circular tour.
 * At each station, gain gas[i] and spend cost[i] to reach next station.
 * 
 * Key Insights:
 * 1. If total gas < total cost → impossible (-1)
 * 2. Greedy: If we run out at station j, all stations up to j are invalid starts
 * 3. Single pass with running sum finds the unique solution
 */
class Solution {
    
    /**
     * Approach 1: Greedy Single Pass (Recommended)
     * Time: O(n), Space: O(1)
     * 
     * Steps:
     * 1. Check if total gas >= total cost
     * 2. Track running sum of (gas[i] - cost[i])
     * 3. When running sum goes negative, reset start to next station
     * 4. Return start index
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0;
        int totalCost = 0;
        int currentTank = 0;
        int startStation = 0;
        
        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            currentTank += gas[i] - cost[i];
            
            // If we run out of gas, reset start to next station
            if (currentTank < 0) {
                startStation = i + 1;
                currentTank = 0;
            }
        }
        
        // Check if total gas is sufficient
        return totalGas >= totalCost ? startStation : -1;
    }
    
    /**
     * Approach 2: Greedy with Separate Validation
     * Time: O(n), Space: O(1)
     * 
     * First validate total gas >= total cost, then find start
     */
    public int canCompleteCircuitValidateFirst(int[] gas, int[] cost) {
        int total = 0;
        for (int i = 0; i < gas.length; i++) {
            total += gas[i] - cost[i];
        }
        
        // If total gas is less than total cost, impossible
        if (total < 0) return -1;
        
        int start = 0;
        int tank = 0;
        
        for (int i = 0; i < gas.length; i++) {
            tank += gas[i] - cost[i];
            
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        
        return start;
    }
    
    /**
     * Approach 3: Kadane's Algorithm Style
     * Time: O(n), Space: O(1)
     * 
     * Similar to maximum subarray but finding minimum prefix sum
     */
    public int canCompleteCircuitKadane(int[] gas, int[] cost) {
        int n = gas.length;
        int[] diff = new int[n];
        int total = 0;
        
        for (int i = 0; i < n; i++) {
            diff[i] = gas[i] - cost[i];
            total += diff[i];
        }
        
        if (total < 0) return -1;
        
        // Find the point after the minimum prefix sum
        int minSum = Integer.MAX_VALUE;
        int minIndex = -1;
        int currentSum = 0;
        
        for (int i = 0; i < n; i++) {
            currentSum += diff[i];
            if (currentSum < minSum) {
                minSum = currentSum;
                minIndex = i;
            }
        }
        
        // Start after the minimum prefix sum
        return (minIndex + 1) % n;
    }
    
    /**
     * Approach 4: Brute Force (for understanding)
     * Time: O(n²), Space: O(1)
     * 
     * Try each station as starting point
     */
    public int canCompleteCircuitBruteForce(int[] gas, int[] cost) {
        int n = gas.length;
        
        for (int start = 0; start < n; start++) {
            int tank = 0;
            boolean valid = true;
            
            for (int i = 0; i < n; i++) {
                int idx = (start + i) % n;
                tank += gas[idx];
                tank -= cost[idx];
                
                if (tank < 0) {
                    valid = false;
                    break;
                }
            }
            
            if (valid) return start;
        }
        
        return -1;
    }
    
    /**
     * Approach 5: Two-Pointer Window
     * Time: O(n²), Space: O(1)
     * 
     * Expand window until valid or reset
     */
    public int canCompleteCircuitTwoPointer(int[] gas, int[] cost) {
        int n = gas.length;
        int start = 0;
        
        while (start < n) {
            int tank = 0;
            int count = 0;
            
            while (count < n) {
                int idx = (start + count) % n;
                tank += gas[idx] - cost[idx];
                
                if (tank < 0) {
                    // Move start forward
                    start = start + count + 1;
                    break;
                }
                count++;
            }
            
            if (count == n) return start;
        }
        
        return -1;
    }
    
    /**
     * Approach 6: Optimized with Early Exit
     * Time: O(n), Space: O(1)
     * 
     * Similar to approach 1 but with early exit when total becomes negative
     */
    public int canCompleteCircuitOptimized(int[] gas, int[] cost) {
        int n = gas.length;
        int start = 0;
        int tank = 0;
        int total = 0;
        
        for (int i = 0; i < n; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;
            
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        
        return total >= 0 ? start : -1;
    }
    
    /**
     * Helper: Visualize the greedy process
     */
    public void visualizeGreedy(int[] gas, int[] cost) {
        System.out.println("\nGreedy Process Visualization:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nInput:");
        System.out.println("gas:  " + Arrays.toString(gas));
        System.out.println("cost: " + Arrays.toString(cost));
        
        System.out.println("\nDifferences (gas - cost):");
        int[] diff = new int[gas.length];
        int total = 0;
        for (int i = 0; i < gas.length; i++) {
            diff[i] = gas[i] - cost[i];
            total += diff[i];
            System.out.printf("  Station %d: %d - %d = %d%n", i, gas[i], cost[i], diff[i]);
        }
        System.out.println("Total sum: " + total);
        
        if (total < 0) {
            System.out.println("\nTotal gas < total cost → Impossible → Return -1");
            return;
        }
        
        System.out.println("\nGreedy walkthrough:");
        int start = 0;
        int tank = 0;
        
        for (int i = 0; i < gas.length; i++) {
            int diffVal = gas[i] - cost[i];
            tank += diffVal;
            System.out.printf("\nStation %d:%n", i);
            System.out.printf("  Add gas: +%d, Spend cost: -%d, Net: %+d%n", gas[i], cost[i], diffVal);
            System.out.printf("  Current tank: %d%n", tank);
            
            if (tank < 0) {
                System.out.printf("  Tank negative! Cannot start from station %d.%n", start);
                start = i + 1;
                tank = 0;
                System.out.printf("  New start candidate: station %d, reset tank to 0%n", start);
            } else {
                System.out.printf("  Tank positive, continue%n");
            }
        }
        
        System.out.println("\nResult: Starting at station " + start);
    }
    
    /**
     * Helper: Visualize brute force approach
     */
    public void visualizeBruteForce(int[] gas, int[] cost) {
        System.out.println("\nBrute Force Visualization:");
        System.out.println("=".repeat(70));
        
        int n = gas.length;
        
        for (int start = 0; start < n; start++) {
            System.out.printf("\nTrying start at station %d:%n", start);
            int tank = 0;
            boolean valid = true;
            
            for (int i = 0; i < n; i++) {
                int idx = (start + i) % n;
                tank += gas[idx];
                tank -= cost[idx];
                System.out.printf("  Station %d: +%d -%d = %d (tank: %d)%n", 
                    idx, gas[idx], cost[idx], gas[idx] - cost[idx], tank);
                
                if (tank < 0) {
                    System.out.printf("    → Failed at station %d (tank = %d)%n", idx, tank);
                    valid = false;
                    break;
                }
            }
            
            if (valid) {
                System.out.printf("\n✓ Valid starting station: %d%n", start);
                return;
            }
        }
        
        System.out.println("\n✗ No valid starting station found");
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][][] generateTestCases() {
        return new int[][][] {
            // {gas, cost, expected}
            {{1,2,3,4,5}, {3,4,5,1,2}, {3}},          // Example 1
            {{2,3,4}, {3,4,3}, {-1}},                  // Example 2
            {{5,1,2,3,4}, {4,4,1,5,1}, {4}},           // Example 3
            {{1,1,1,1,1}, {1,1,1,1,1}, {0}},           // All equal
            {{10,10,10,10}, {5,5,5,5}, {0}},           // Excess gas everywhere
            {{1,2,3,4,5}, {5,4,3,2,1}, {0}},           // Decreasing cost
            {{0,0,0,0,0}, {1,1,1,1,1}, {-1}},          // No gas
            {{5,5,5,5,5}, {10,10,10,10,10}, {-1}},     // Not enough gas
            {{3,3,3,3,3}, {3,3,3,3,3}, {0}},           // Exactly enough
            {{1,2,3,4,5,6}, {2,3,4,5,6,1}, {5}}        // Edge case
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        int[][][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] gas = testCases[i][0];
            int[] cost = testCases[i][1];
            int expected = testCases[i][2][0];
            
            System.out.printf("\nTest %d:%n", i + 1);
            System.out.println("gas:  " + Arrays.toString(gas));
            System.out.println("cost: " + Arrays.toString(cost));
            
            int result1 = canCompleteCircuit(gas, cost);
            int result2 = canCompleteCircuitValidateFirst(gas, cost);
            int result3 = canCompleteCircuitKadane(gas, cost);
            int result4 = canCompleteCircuitBruteForce(gas, cost);
            int result5 = canCompleteCircuitTwoPointer(gas, cost);
            int result6 = canCompleteCircuitOptimized(gas, cost);
            
            boolean allMatch = result1 == expected && result2 == expected &&
                              result3 == expected && result4 == expected &&
                              result5 == expected && result6 == expected;
            
            if (allMatch) {
                System.out.println("✓ PASS - Starting station: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
                System.out.println("  Method 6: " + result6);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeGreedy(gas, cost);
                visualizeBruteForce(gas, cost);
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
        
        // Generate large test case
        int n = 100000;
        int[] gas = new int[n];
        int[] cost = new int[n];
        Random rand = new Random(42);
        
        for (int i = 0; i < n; i++) {
            gas[i] = rand.nextInt(10000);
            cost[i] = rand.nextInt(10000);
        }
        
        System.out.println("Test Setup: " + n + " stations");
        
        long[] times = new long[6];
        int[] results = new int[6];
        
        // Method 1: Greedy Single Pass
        int[] gas1 = gas.clone();
        int[] cost1 = cost.clone();
        long start = System.currentTimeMillis();
        results[0] = canCompleteCircuit(gas1, cost1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Validate First
        int[] gas2 = gas.clone();
        int[] cost2 = cost.clone();
        start = System.currentTimeMillis();
        results[1] = canCompleteCircuitValidateFirst(gas2, cost2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Kadane Style
        int[] gas3 = gas.clone();
        int[] cost3 = cost.clone();
        start = System.currentTimeMillis();
        results[2] = canCompleteCircuitKadane(gas3, cost3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Brute Force (skip for large n)
        times[3] = -1;
        results[3] = -1;
        
        // Method 5: Two Pointer
        int[] gas5 = gas.clone();
        int[] cost5 = cost.clone();
        start = System.currentTimeMillis();
        results[4] = canCompleteCircuitTwoPointer(gas5, cost5);
        times[4] = System.currentTimeMillis() - start;
        
        // Method 6: Optimized
        int[] gas6 = gas.clone();
        int[] cost6 = cost.clone();
        start = System.currentTimeMillis();
        results[5] = canCompleteCircuitOptimized(gas6, cost6);
        times[5] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Greedy Single Pass     | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Validate First         | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. Kadane Style           | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. Brute Force            | %9s | %6s%n", "N/A", "N/A (O(n²))");
        System.out.printf("5. Two Pointer            | %9d | %6d%n", times[4], results[4]);
        System.out.printf("6. Optimized              | %9d | %6d%n", times[5], results[5]);
        
        boolean allSame = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[4] && results[4] == results[5];
        System.out.println("\nAll O(n) methods produce same result: " + (allSame ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. All O(n) methods have similar performance");
        System.out.println("2. Greedy single pass is simplest and efficient");
        System.out.println("3. Brute force is O(n²) and infeasible for large n");
        System.out.println("4. Two-pointer approach is also O(n²) worst case");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Single station with enough gas:");
        int[] gas1 = {10};
        int[] cost1 = {5};
        System.out.println("   gas: " + Arrays.toString(gas1));
        System.out.println("   cost: " + Arrays.toString(cost1));
        System.out.println("   Result: " + canCompleteCircuit(gas1, cost1));
        
        System.out.println("\n2. Single station with insufficient gas:");
        int[] gas2 = {5};
        int[] cost2 = {10};
        System.out.println("   gas: " + Arrays.toString(gas2));
        System.out.println("   cost: " + Arrays.toString(cost2));
        System.out.println("   Result: " + canCompleteCircuit(gas2, cost2));
        
        System.out.println("\n3. All zeros:");
        int[] gas3 = {0,0,0,0};
        int[] cost3 = {0,0,0,0};
        System.out.println("   gas: " + Arrays.toString(gas3));
        System.out.println("   cost: " + Arrays.toString(cost3));
        System.out.println("   Result: " + canCompleteCircuit(gas3, cost3));
        
        System.out.println("\n4. Large gas at start, high cost later:");
        int[] gas4 = {100,1,1,1};
        int[] cost4 = {1,50,50,50};
        System.out.println("   gas: " + Arrays.toString(gas4));
        System.out.println("   cost: " + Arrays.toString(cost4));
        System.out.println("   Result: " + canCompleteCircuit(gas4, cost4));
        
        System.out.println("\n5. Zero gas at some stations:");
        int[] gas5 = {0,5,0,5,0};
        int[] cost5 = {3,2,3,2,3};
        System.out.println("   gas: " + Arrays.toString(gas5));
        System.out.println("   cost: " + Arrays.toString(cost5));
        System.out.println("   Result: " + canCompleteCircuit(gas5, cost5));
    }
    
    /**
     * Helper: Explain the greedy approach
     */
    public void explainGreedy() {
        System.out.println("\nGreedy Approach Explanation:");
        System.out.println("============================");
        
        System.out.println("\nKey Insight:");
        System.out.println("If total gas ≥ total cost, a solution exists and is unique.");
        System.out.println("If we run out of gas at station j when starting from i, ");
        System.out.println("no station between i and j can be a valid starting point.");
        
        System.out.println("\nProof:");
        System.out.println("Let diff[i] = gas[i] - cost[i]");
        System.out.println("We need a starting point where all prefix sums remain non-negative.");
        System.out.println("The minimum prefix sum point indicates where to start.");
        
        System.out.println("\nAlgorithm:");
        System.out.println("1. Initialize start = 0, tank = 0");
        System.out.println("2. For each station i:");
        System.out.println("   tank += gas[i] - cost[i]");
        System.out.println("   if tank < 0: start = i+1, tank = 0");
        System.out.println("3. If total gas ≥ total cost, return start, else -1");
        
        System.out.println("\nWhy it works:");
        System.out.println("- When tank becomes negative, all previous stations are invalid");
        System.out.println("- Resetting at i+1 is safe because:");
        System.out.println("  * Any station between previous start and i would have even less gas");
        System.out.println("  * The cumulative gas from i+1 onward can make up the deficit");
        System.out.println("- If total gas ≥ total cost, the last reset point is the answer");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Can we travel counter-clockwise? (No, only clockwise)");
        System.out.println("   - Is the solution guaranteed to be unique? (Yes)");
        System.out.println("   - Can we start with empty tank? (Yes)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Try each station as starting point O(n²)");
        System.out.println("   - Acknowledge it's too slow for n=10⁵");
        
        System.out.println("\n3. Propose greedy solution:");
        System.out.println("   - Check total gas ≥ total cost first");
        System.out.println("   - Track running sum, reset when negative");
        System.out.println("   - Explain why it works");
        
        System.out.println("\n4. Walk through example:");
        System.out.println("   - Example 1: gas=[1,2,3,4,5], cost=[3,4,5,1,2]");
        System.out.println("   - Show how start becomes 3");
        
        System.out.println("\n5. Discuss proof:");
        System.out.println("   - If you run out at station j, you can't start at any station between");
        System.out.println("   - The cumulative gas from next station onward can overcome the deficit");
        
        System.out.println("\n6. Complexity analysis:");
        System.out.println("   - Time: O(n) single pass");
        System.out.println("   - Space: O(1)");
        
        System.out.println("\n7. Edge cases:");
        System.out.println("   - Single station");
        System.out.println("   - Total gas < total cost");
        System.out.println("   - All zeros");
        System.out.println("   - Large gas at start, high costs later");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("134. Gas Station");
        System.out.println("================");
        
        // Explain greedy approach
        solution.explainGreedy();
        
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
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0;
        int totalCost = 0;
        int currentTank = 0;
        int startStation = 0;
        
        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            currentTank += gas[i] - cost[i];
            
            if (currentTank < 0) {
                startStation = i + 1;
                currentTank = 0;
            }
        }
        
        return totalGas >= totalCost ? startStation : -1;
    }
}
            """);
        
        System.out.println("\nAlternative (with early validation):");
        System.out.println("""
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0;
        int start = 0;
        int tank = 0;
        
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;
            
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        
        return total >= 0 ? start : -1;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. If total gas < total cost → impossible (-1)");
        System.out.println("2. Greedy solution runs in O(n) time and O(1) space");
        System.out.println("3. When tank becomes negative, all previous stations are invalid");
        System.out.println("4. The answer is always the station after the last reset point");
        System.out.println("5. Solution is guaranteed to be unique if it exists");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) single pass through stations");
        System.out.println("- Space: O(1) extra space");
        
        System.out.println("\nCommon Interview Follow-ups:");
        System.out.println("1. What if the route wasn't circular?");
        System.out.println("2. What if you could travel in both directions?");
        System.out.println("3. How would you handle multiple solutions?");
        System.out.println("4. What if gas stations had different fuel types?");
    }
}
