
## Problems/Arrays-Hashing/0070-climbing-stairs/Solution.java

```java
/**
 * 70. Climbing Stairs
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 * 
 * Key Insights:
 * 1. This is essentially the Fibonacci sequence
 * 2. Number of ways to reach step n = ways(n-1) + ways(n-2)
 * 3. Base cases: ways(1) = 1, ways(2) = 2
 * 4. Can be solved with dynamic programming, recursion with memoization, or mathematical formula
 * 
 * Approach (Dynamic Programming - Bottom Up):
 * 1. Handle base cases for n = 1 and n = 2
 * 2. Use two variables to store previous two results
 * 3. Iterate from 3 to n, updating the result
 * 4. Return the final result
 * 
 * Time Complexity: O(n) - Single pass from 3 to n
 * Space Complexity: O(1) - Only constant extra space
 * 
 * Tags: Math, Dynamic Programming, Memoization, Fibonacci
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Dynamic Programming (Bottom-up) - RECOMMENDED
     * O(n) time, O(1) space
     */
    public int climbStairs(int n) {
        if (n <= 2) return n;
        
        int prev1 = 1; // ways for n-2
        int prev2 = 2; // ways for n-1
        int current = 0;
        
        for (int i = 3; i <= n; i++) {
            current = prev1 + prev2;
            prev1 = prev2;
            prev2 = current;
        }
        
        return current;
    }
    
    /**
     * Approach 2: Dynamic Programming with Array
     * O(n) time, O(n) space - More intuitive but uses extra space
     */
    public int climbStairsDP(int n) {
        if (n <= 2) return n;
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
    
    /**
     * Approach 3: Recursion with Memoization
     * O(n) time, O(n) space - Top-down approach
     */
    public int climbStairsMemo(int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return climbStairsHelper(n, memo);
    }
    
    private int climbStairsHelper(int n, int[] memo) {
        if (n <= 2) return n;
        if (memo[n] != -1) return memo[n];
        
        memo[n] = climbStairsHelper(n - 1, memo) + climbStairsHelper(n - 2, memo);
        return memo[n];
    }
    
    /**
     * Approach 4: Mathematical (Matrix Exponentiation)
     * O(log n) time, O(1) space - Using matrix exponentiation for Fibonacci
     */
    public int climbStairsMath(int n) {
        if (n <= 2) return n;
        
        // Using matrix exponentiation for Fibonacci
        // [F(n)  ]   = [1 1]^(n-1) [F(1)]
        // [F(n-1)]     [1 0]        [F(0)]
        int[][] base = {{1, 1}, {1, 0}};
        int[][] result = matrixPower(base, n - 1);
        
        return result[0][0]; // F(n) for climbing stairs
    }
    
    private int[][] matrixPower(int[][] matrix, int power) {
        int[][] result = {{1, 0}, {0, 1}}; // Identity matrix
        
        while (power > 0) {
            if ((power & 1) == 1) {
                result = multiplyMatrices(result, matrix);
            }
            matrix = multiplyMatrices(matrix, matrix);
            power >>= 1;
        }
        
        return result;
    }
    
    private int[][] multiplyMatrices(int[][] a, int[][] b) {
        int[][] result = new int[2][2];
        result[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0];
        result[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1];
        result[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0];
        result[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1];
        return result;
    }
    
    /**
     * Approach 5: Binet's Formula (Golden Ratio)
     * O(1) time, O(1) space - Mathematical formula, but may have precision issues
     */
    public int climbStairsBinet(int n) {
        if (n <= 2) return n;
        
        // Binet's formula for Fibonacci: F(n) = (φ^n - ψ^n) / √5
        // where φ = (1 + √5)/2, ψ = (1 - √5)/2
        // For climbing stairs: ways(n) = F(n+1) in standard Fibonacci
        n = n + 1; // Adjust for standard Fibonacci
        
        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;
        double psi = (1 - sqrt5) / 2;
        
        return (int) Math.round((Math.pow(phi, n) - Math.pow(psi, n)) / sqrt5);
    }
    
    /**
     * Approach 6: Iterative with Variables (Alternative)
     * O(n) time, O(1) space - Different variable naming
     */
    public int climbStairsIterative(int n) {
        if (n <= 2) return n;
        
        int first = 1;
        int second = 2;
        
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }
        
        return second;
    }
    
    /**
     * Helper method to visualize the DP process
     */
    private void visualizeDP(int n) {
        System.out.println("\nClimbing Stairs DP Visualization:");
        System.out.println("n = " + n);
        
        if (n <= 2) {
            System.out.println("Base case: " + n + " ways");
            return;
        }
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        System.out.println("\nDP Table Construction:");
        System.out.println("Step | Ways | Calculation");
        System.out.println("-----|------|------------");
        System.out.println("  1  |   1  | Base case");
        System.out.println("  2  |   2  | Base case");
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
            System.out.printf("  %d  |  %2d  | dp[%d] + dp[%d] = %d + %d%n", 
                            i, dp[i], i-1, i-2, dp[i-1], dp[i-2]);
        }
        
        System.out.println("\nResult: " + dp[n] + " ways to climb " + n + " stairs");
    }
    
    /**
     * Helper method to show all possible paths for small n
     */
    private void showAllPaths(int n) {
        System.out.println("\nAll Possible Paths for n = " + n + ":");
        
        if (n == 1) {
            System.out.println("1. 1 step");
            return;
        }
        
        if (n == 2) {
            System.out.println("1. 1 step + 1 step");
            System.out.println("2. 2 steps");
            return;
        }
        
        List<List<String>> paths = generatePaths(n);
        for (int i = 0; i < paths.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, String.join(" + ", paths.get(i)));
        }
    }
    
    private List<List<String>> generatePaths(int n) {
        List<List<String>> result = new ArrayList<>();
        generatePathsHelper(n, new ArrayList<>(), result);
        return result;
    }
    
    private void generatePathsHelper(int remaining, List<String> current, List<List<String>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        if (remaining >= 1) {
            current.add("1 step");
            generatePathsHelper(remaining - 1, current, result);
            current.remove(current.size() - 1);
        }
        
        if (remaining >= 2) {
            current.add("2 steps");
            generatePathsHelper(remaining - 2, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Helper method to demonstrate Fibonacci relationship
     */
    private void demonstrateFibonacciRelationship() {
        System.out.println("\nFibonacci Relationship:");
        System.out.println("Climbing Stairs vs Fibonacci Sequence:");
        System.out.println("n | Climbing Stairs | Fibonacci F(n) | Relationship");
        System.out.println("--|-----------------|----------------|-------------");
        
        for (int n = 1; n <= 10; n++) {
            int climbing = climbStairs(n);
            int fib = fibonacci(n + 1); // climbing(n) = fib(n+1)
            System.out.printf("%d | %15d | %14d | climbing(%d) = fib(%d)%n", 
                            n, climbing, fib, n, n + 1);
        }
    }
    
    private int fibonacci(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }
    
    /**
     * Helper method to analyze time/space complexity
     */
    private void analyzeComplexity(int n) {
        System.out.println("\nComplexity Analysis for n = " + n + ":");
        
        System.out.println("\nApproach 1 (DP Bottom-up):");
        System.out.println("  Time: O(n) = O(" + n + ")");
        System.out.println("  Space: O(1)");
        
        System.out.println("\nApproach 2 (DP Array):");
        System.out.println("  Time: O(n) = O(" + n + ")");
        System.out.println("  Space: O(n) = O(" + n + ")");
        
        System.out.println("\nApproach 3 (Recursion with Memo):");
        System.out.println("  Time: O(n) = O(" + n + ")");
        System.out.println("  Space: O(n) = O(" + n + ")");
        
        System.out.println("\nApproach 4 (Matrix Exponentiation):");
        System.out.println("  Time: O(log n) = O(log " + n + ") = O(" + (int)(Math.log(n)/Math.log(2)) + ")");
        System.out.println("  Space: O(1)");
        
        System.out.println("\nApproach 5 (Binet's Formula):");
        System.out.println("  Time: O(1)");
        System.out.println("  Space: O(1)");
        
        System.out.println("\nRecommended: Approach 1 (DP Bottom-up) - Best balance of efficiency and simplicity");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Climbing Stairs Solution:");
        System.out.println("==================================");
        
        // Test case 1: n = 2
        System.out.println("\nTest 1: n = 2");
        int n1 = 2;
        int expected1 = 2;
        
        long startTime = System.nanoTime();
        int result1a = solution.climbStairs(n1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.climbStairsDP(n1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.climbStairsMemo(n1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = (result1a == expected1);
        boolean test1b = (result1b == expected1);
        boolean test1c = (result1c == expected1);
        
        System.out.println("DP Bottom-up: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DP Array: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Memoization: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeDP(n1);
        solution.showAllPaths(n1);
        
        // Test case 2: n = 3
        System.out.println("\nTest 2: n = 3");
        int n2 = 3;
        int expected2 = 3;
        
        int result2a = solution.climbStairs(n2);
        System.out.println("n = 3: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        solution.showAllPaths(n2);
        
        // Test case 3: n = 1
        System.out.println("\nTest 3: n = 1");
        int n3 = 1;
        int expected3 = 1;
        
        int result3a = solution.climbStairs(n3);
        System.out.println("n = 1: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: n = 4
        System.out.println("\nTest 4: n = 4");
        int n4 = 4;
        int expected4 = 5;
        
        int result4a = solution.climbStairs(n4);
        System.out.println("n = 4: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        solution.showAllPaths(n4);
        
        // Test case 5: n = 5
        System.out.println("\nTest 5: n = 5");
        int n5 = 5;
        int result5a = solution.climbStairs(n5);
        System.out.println("n = 5: " + result5a + " (expected: 8)");
        
        // Test case 6: Maximum constraint
        System.out.println("\nTest 6: Maximum constraint");
        int n6 = 45;
        int result6a = solution.climbStairs(n6);
        System.out.println("n = 45: " + result6a + " (expected: 1836311903)");
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("n = 2 performance:");
        System.out.println("  DP Bottom-up: " + time1a + " ns");
        System.out.println("  DP Array: " + time1b + " ns");
        System.out.println("  Memoization: " + time1c + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 8: Larger input performance");
        int n8 = 30;
        
        startTime = System.nanoTime();
        int result8a = solution.climbStairs(n8);
        long time8a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result8b = solution.climbStairsMath(n8);
        long time8b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result8c = solution.climbStairsBinet(n8);
        long time8c = System.nanoTime() - startTime;
        
        System.out.println("n = 30:");
        System.out.println("  DP Bottom-up: " + time8a + " ns, Result: " + result8a);
        System.out.println("  Matrix Expo: " + time8b + " ns, Result: " + result8b);
        System.out.println("  Binet's Formula: " + time8c + " ns, Result: " + result8c);
        
        // Compare all approaches for consistency
        System.out.println("\nTest 9: Consistency Check");
        int testN = 10;
        int r1 = solution.climbStairs(testN);
        int r2 = solution.climbStairsDP(testN);
        int r3 = solution.climbStairsMemo(testN);
        int r4 = solution.climbStairsMath(testN);
        int r5 = solution.climbStairsBinet(testN);
        int r6 = solution.climbStairsIterative(testN);
        
        boolean consistent = (r1 == r2) && (r1 == r3) && (r1 == r4) && (r1 == r5) && (r1 == r6);
        System.out.println("All approaches consistent: " + consistent);
        
        // Demonstrate mathematical relationships
        solution.demonstrateFibonacciRelationship();
        solution.analyzeComplexity(45);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DYNAMIC PROGRAMMING EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The number of ways to reach step n is the sum of:");
        System.out.println("  - Ways to reach step n-1 (then take 1 step)");
        System.out.println("  - Ways to reach step n-2 (then take 2 steps)");
        System.out.println("This gives us the recurrence relation:");
        System.out.println("  ways(n) = ways(n-1) + ways(n-2)");
        
        System.out.println("\nBase Cases:");
        System.out.println("  ways(1) = 1  (only one way: 1 step)");
        System.out.println("  ways(2) = 2  (two ways: 1+1 or 2)");
        
        System.out.println("\nWhy this works:");
        System.out.println("1. Optimal Substructure: Solution for n depends on n-1 and n-2");
        System.out.println("2. Overlapping Subproblems: Same subproblems solved multiple times");
        System.out.println("3. We use bottom-up DP to avoid recomputation");
        
        System.out.println("\nVisual Example (n = 4):");
        System.out.println("ways(4) = ways(3) + ways(2)");
        System.out.println("ways(3) = ways(2) + ways(1) = 2 + 1 = 3");
        System.out.println("ways(2) = 2");
        System.out.println("ways(4) = 3 + 2 = 5");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Dynamic Programming (Bottom-up) - RECOMMENDED:");
        System.out.println("   Time: O(n) - Single pass through steps");
        System.out.println("   Space: O(1) - Only two variables needed");
        System.out.println("   How it works:");
        System.out.println("     - Use two variables to store previous results");
        System.out.println("     - Iteratively compute current from previous two");
        System.out.println("     - Return the final result");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Simple and efficient");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - None for this problem");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Dynamic Programming with Array:");
        System.out.println("   Time: O(n) - Single pass through steps");
        System.out.println("   Space: O(n) - Array storage");
        System.out.println("   How it works:");
        System.out.println("     - Store all intermediate results in array");
        System.out.println("     - Build solution from bottom up");
        System.out.println("   Pros:");
        System.out.println("     - More intuitive for beginners");
        System.out.println("     - Easy to visualize and debug");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("   Best for: Learning DP concepts");
        
        System.out.println("\n3. Recursion with Memoization:");
        System.out.println("   Time: O(n) - With memoization");
        System.out.println("   Space: O(n) - Recursion stack and memo storage");
        System.out.println("   How it works:");
        System.out.println("     - Top-down recursive approach");
        System.out.println("     - Store computed results to avoid recomputation");
        System.out.println("   Pros:");
        System.out.println("     - Natural recursive thinking");
        System.out.println("     - Only computes needed subproblems");
        System.out.println("   Cons:");
        System.out.println("     - Recursion overhead");
        System.out.println("     - Stack overflow risk for large n");
        System.out.println("   Best for: When prefer recursive thinking");
        
        System.out.println("\n4. Matrix Exponentiation:");
        System.out.println("   Time: O(log n) - Using fast exponentiation");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Use matrix representation of Fibonacci");
        System.out.println("     - Apply fast exponentiation algorithm");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(log n) time complexity");
        System.out.println("     - Mathematical elegance");
        System.out.println("   Cons:");
        System.out.println("     - More complex to implement");
        System.out.println("     - Overkill for this problem");
        System.out.println("   Best for: Very large n, mathematical interest");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. This is the Fibonacci sequence: climbing(n) = fib(n+1)");
        System.out.println("2. The sequence: 1, 2, 3, 5, 8, 13, 21, 34, ...");
        System.out.println("3. Golden ratio φ ≈ 1.618 appears in the ratio of consecutive terms");
        System.out.println("4. Closed-form solution exists using Binet's formula");
        System.out.println("5. For large n, the number of ways grows exponentially");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with DP Bottom-up - it's the expected solution");
        System.out.println("2. Explain the recurrence relation clearly");
        System.out.println("3. Mention the Fibonacci connection");
        System.out.println("4. Discuss time/space complexity");
        System.out.println("5. Handle edge cases: n=1, n=2");
        System.out.println("6. Mention alternative approaches if time permits");
        System.out.println("7. Write clean, efficient code");
        System.out.println("8. Practice explaining the thought process");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
