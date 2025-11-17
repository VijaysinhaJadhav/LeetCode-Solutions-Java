
## Solution.java

```java
/**
 * 1137. N-th Tribonacci Number
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * The Tribonacci sequence Tn is defined as follows: 
 * T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.
 * Given n, return the value of Tn.
 * 
 * Key Insights:
 * 1. Similar to Fibonacci but with three terms instead of two
 * 2. Can use dynamic programming with space optimization
 * 3. Only need to store last three values
 * 4. Base cases: n=0 -> 0, n=1 -> 1, n=2 -> 1
 * 
 * Approach (Iterative DP with Space Optimization):
 * 1. Handle base cases explicitly
 * 2. Use three variables to store Tn-3, Tn-2, Tn-1
 * 3. Iteratively compute Tn using the recurrence relation
 * 4. Update variables for next iteration
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Math, Dynamic Programming, Memoization
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Iterative DP with Space Optimization - RECOMMENDED
     * O(n) time, O(1) space
     */
    public int tribonacci(int n) {
        // Base cases
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        
        // Initialize first three Tribonacci numbers
        int a = 0, b = 1, c = 1;
        
        // Compute Tribonacci numbers iteratively
        for (int i = 3; i <= n; i++) {
            int next = a + b + c;
            a = b;
            b = c;
            c = next;
        }
        
        return c;
    }
    
    /**
     * Approach 2: DP Array (More Readable)
     * O(n) time, O(n) space
     */
    public int tribonacciDP(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }
        
        return dp[n];
    }
    
    /**
     * Approach 3: Recursive with Memoization
     * O(n) time, O(n) space
     */
    public int tribonacciRecursive(int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return tribonacciHelper(n, memo);
    }
    
    private int tribonacciHelper(int n, int[] memo) {
        // Base cases
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        
        // Check if already computed
        if (memo[n] != -1) {
            return memo[n];
        }
        
        // Recursive case
        memo[n] = tribonacciHelper(n - 1, memo) + 
                  tribonacciHelper(n - 2, memo) + 
                  tribonacciHelper(n - 3, memo);
        return memo[n];
    }
    
    /**
     * Approach 4: Matrix Exponentiation (O(log n) time)
     * O(log n) time, O(1) space
     */
    public int tribonacciMatrix(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        
        // Transformation matrix for Tribonacci
        // [T(n)]   = [1 1 1] [T(n-1)]
        // [T(n-1)]   [1 0 0] [T(n-2)]
        // [T(n-2)]   [0 1 0] [T(n-3)]
        long[][] matrix = {
            {1, 1, 1},
            {1, 0, 0},
            {0, 1, 0}
        };
        
        // Raise matrix to power (n-2)
        long[][] result = matrixPower(matrix, n - 2);
        
        // T(n) = result[0][0]*T(2) + result[0][1]*T(1) + result[0][2]*T(0)
        return (int)(result[0][0] * 1 + result[0][1] * 1 + result[0][2] * 0);
    }
    
    private long[][] matrixPower(long[][] matrix, int power) {
        long[][] result = {
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
        
        while (power > 0) {
            if (power % 2 == 1) {
                result = matrixMultiply(result, matrix);
            }
            matrix = matrixMultiply(matrix, matrix);
            power /= 2;
        }
        
        return result;
    }
    
    private long[][] matrixMultiply(long[][] a, long[][] b) {
        long[][] result = new long[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
    
    /**
     * Approach 5: Using Queue (Alternative iterative)
     * O(n) time, O(1) space
     */
    public int tribonacciQueue(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        queue.offer(1);
        queue.offer(1);
        
        for (int i = 3; i <= n; i++) {
            int a = queue.poll();
            int b = queue.peek(); // Don't remove yet
            int c = getLastElement(queue);
            int next = a + b + c;
            queue.offer(next);
        }
        
        // The last element in queue is Tn
        return getLastElement(queue);
    }
    
    private int getLastElement(Queue<Integer> queue) {
        int last = 0;
        for (int num : queue) {
            last = num;
        }
        return last;
    }
    
    /**
     * Approach 6: Precomputation (Since n <= 37)
     * O(1) time, O(1) space after precomputation
     */
    private static final int[] TRIBONACCI_SEQUENCE = precomputeTribonacci();
    
    private static int[] precomputeTribonacci() {
        int[] trib = new int[38]; // n <= 37
        trib[0] = 0;
        trib[1] = 1;
        trib[2] = 1;
        
        for (int i = 3; i < 38; i++) {
            trib[i] = trib[i - 1] + trib[i - 2] + trib[i - 3];
        }
        
        return trib;
    }
    
    public int tribonacciPrecomputed(int n) {
        return TRIBONACCI_SEQUENCE[n];
    }
    
    /**
     * Helper method to visualize the Tribonacci sequence
     */
    public void visualizeTribonacci(int n) {
        System.out.println("\nTribonacci Sequence Visualization:");
        System.out.println("Computing T(" + n + ")");
        
        if (n == 0) {
            System.out.println("T(0) = 0");
            return;
        }
        if (n == 1) {
            System.out.println("T(1) = 1");
            return;
        }
        if (n == 2) {
            System.out.println("T(2) = 1");
            return;
        }
        
        System.out.println("Base cases:");
        System.out.println("T(0) = 0");
        System.out.println("T(1) = 1");
        System.out.println("T(2) = 1");
        
        int a = 0, b = 1, c = 1;
        System.out.println("\nIterative computation:");
        
        for (int i = 3; i <= n; i++) {
            int next = a + b + c;
            System.out.printf("T(%d) = T(%d) + T(%d) + T(%d) = %d + %d + %d = %d%n",
                            i, i-1, i-2, i-3, c, b, a, next);
            a = b;
            b = c;
            c = next;
        }
        
        System.out.println("\nResult: T(" + n + ") = " + c);
        
        // Show the entire sequence up to n
        printTribonacciSequence(n);
    }
    
    private void printTribonacciSequence(int n) {
        System.out.println("\nTribonacci sequence from T(0) to T(" + n + "):");
        if (n == 0) {
            System.out.println("T(0) = 0");
            return;
        }
        
        int a = 0, b = 1, c = 1;
        System.out.print("T(0) = 0");
        if (n >= 1) System.out.print(", T(1) = 1");
        if (n >= 2) System.out.print(", T(2) = 1");
        
        for (int i = 3; i <= n; i++) {
            int next = a + b + c;
            System.out.print(", T(" + i + ") = " + next);
            a = b;
            b = c;
            c = next;
        }
        System.out.println();
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing N-th Tribonacci Number:");
        System.out.println("===============================");
        
        // Test case 1: n = 4
        System.out.println("\nTest 1: n = 4");
        int n1 = 4;
        int expected1 = 4;
        
        long startTime = System.nanoTime();
        int result1a = solution.tribonacci(n1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.tribonacciDP(n1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.tribonacciRecursive(n1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.tribonacciMatrix(n1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.tribonacciQueue(n1);
        long time1e = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1f = solution.tribonacciPrecomputed(n1);
        long time1f = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        boolean test1f = result1f == expected1;
        
        System.out.println("Iterative:      " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("DP Array:       " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Recursive:      " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Matrix:         " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Queue:          " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        System.out.println("Precomputed:    " + result1f + " - " + (test1f ? "PASSED" : "FAILED"));
        
        // Visualize the computation
        solution.visualizeTribonacci(n1);
        
        // Test case 2: n = 0
        System.out.println("\nTest 2: n = 0");
        int n2 = 0;
        int result2a = solution.tribonacci(n2);
        System.out.println("n = 0: " + result2a + " - " + 
                         (result2a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 3: n = 1
        System.out.println("\nTest 3: n = 1");
        int n3 = 1;
        int result3a = solution.tribonacci(n3);
        System.out.println("n = 1: " + result3a + " - " + 
                         (result3a == 1 ? "PASSED" : "FAILED"));
        
        // Test case 4: n = 2
        System.out.println("\nTest 4: n = 2");
        int n4 = 2;
        int result4a = solution.tribonacci(n4);
        System.out.println("n = 2: " + result4a + " - " + 
                         (result4a == 1 ? "PASSED" : "FAILED"));
        
        // Test case 5: n = 25
        System.out.println("\nTest 5: n = 25");
        int n5 = 25;
        int expected5 = 1389537;
        int result5a = solution.tribonacci(n5);
        System.out.println("n = 25: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: n = 37 (maximum)
        System.out.println("\nTest 6: n = 37");
        int n6 = 37;
        int expected6 = 2082876103;
        int result6a = solution.tribonacci(n6);
        System.out.println("n = 37: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison (n = 4)");
        System.out.println("Iterative:   " + time1a + " ns");
        System.out.println("DP Array:    " + time1b + " ns");
        System.out.println("Recursive:   " + time1c + " ns");
        System.out.println("Matrix:      " + time1d + " ns");
        System.out.println("Queue:       " + time1e + " ns");
        System.out.println("Precomputed: " + time1f + " ns");
        
        // Test all approaches produce same results
        System.out.println("\nTest 8: All approaches consistency");
        boolean allConsistent = result1a == result1b && 
                              result1a == result1c && 
                              result1a == result1d &&
                              result1a == result1e &&
                              result1a == result1f;
        System.out.println("All approaches produce same results: " + allConsistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("TRIBONACCI SEQUENCE EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nDefinition:");
        System.out.println("T₀ = 0");
        System.out.println("T₁ = 1");
        System.out.println("T₂ = 1");
        System.out.println("Tₙ = Tₙ₋₁ + Tₙ₋₂ + Tₙ₋₃ for n ≥ 3");
        
        System.out.println("\nFirst 10 Tribonacci numbers:");
        for (int i = 0; i <= 10; i++) {
            System.out.println("T(" + i + ") = " + solution.tribonacci(i));
        }
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Iterative DP (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass up to n");
        System.out.println("   Space: O(1) - Only three variables");
        System.out.println("   How it works:");
        System.out.println("     - Use three variables to store Tn-3, Tn-2, Tn-1");
        System.out.println("     - Iteratively compute next value using recurrence");
        System.out.println("     - Update variables for next iteration");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to implement");
        System.out.println("   Cons:");
        System.out.println("     - None for this problem");
        System.out.println("   Best for: Most use cases, interview settings");
        
        System.out.println("\n2. DP Array:");
        System.out.println("   Time: O(n) - Single pass");
        System.out.println("   Space: O(n) - Array to store all values");
        System.out.println("   How it works:");
        System.out.println("     - Store entire sequence in array");
        System.out.println("     - Fill array using recurrence relation");
        System.out.println("   Pros:");
        System.out.println("     - Easy to understand and debug");
        System.out.println("     - Can access any Tribonacci number");
        System.out.println("   Cons:");
        System.out.println("     - Higher space complexity");
        System.out.println("   Best for: When multiple values needed");
        
        System.out.println("\n3. Recursive with Memoization:");
        System.out.println("   Time: O(n) - Each value computed once");
        System.out.println("   Space: O(n) - Memoization table + recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Top-down recursive approach");
        System.out.println("     - Memoize computed results");
        System.out.println("     - Natural recursive definition");
        System.out.println("   Pros:");
        System.out.println("     - Follows mathematical definition closely");
        System.out.println("     - Easy to understand recurrence");
        System.out.println("   Cons:");
        System.out.println("     - Recursion stack overhead");
        System.out.println("     - Higher constant factors");
        System.out.println("   Best for: Learning, small n");
        
        System.out.println("\n4. Matrix Exponentiation:");
        System.out.println("   Time: O(log n) - Matrix exponentiation");
        System.out.println("   Space: O(1) - Constant space for matrices");
        System.out.println("   How it works:");
        System.out.println("     - Use matrix transformation");
        System.out.println("     - Raise matrix to power using exponentiation by squaring");
        System.out.println("     - Extract result from transformed matrix");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity for large n");
        System.out.println("     - Mathematical elegance");
        System.out.println("   Cons:");
        System.out.println("     - Complex implementation");
        System.out.println("     - Overkill for small n");
        System.out.println("   Best for: Very large n, mathematical applications");
        
        System.out.println("\n5. Precomputation:");
        System.out.println("   Time: O(1) - Direct array lookup");
        System.out.println("   Space: O(1) - Constant array (since n ≤ 37)");
        System.out.println("   How it works:");
        System.out.println("     - Precompute all possible values");
        System.out.println("     - Store in static array");
        System.out.println("     - Direct lookup for any n");
        System.out.println("   Pros:");
        System.out.println("     - Instant results");
        System.out.println("     - Zero computation at runtime");
        System.out.println("   Cons:");
        System.out.println("     - Only works for bounded n");
        System.out.println("     - Precomputation overhead");
        System.out.println("   Best for: Production with known bounds");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL PROPERTIES:");
        System.out.println("1. Similar to Fibonacci but with three terms");
        System.out.println("2. Grows faster than Fibonacci sequence");
        System.out.println("3. Ratio approaches constant (~1.839) as n → ∞");
        System.out.println("4. Can be computed using generating functions");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with iterative DP - it's the expected solution");
        System.out.println("2. Handle base cases explicitly");
        System.out.println("3. Explain the space optimization");
        System.out.println("4. Discuss time/space complexity");
        System.out.println("5. Mention alternative approaches if asked");
        System.out.println("6. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        // Additional examples showing growth pattern
        System.out.println("\n" + "=".repeat(70));
        System.out.println("TRIBONACCI SEQUENCE GROWTH:");
        System.out.println("=".repeat(70));
        
        System.out.println("n  | T(n)");
        System.out.println("---+-------------");
        for (int i = 0; i <= 10; i++) {
            int result = solution.tribonacci(i);
            System.out.printf("%2d | %d%n", i, result);
        }
        System.out.println("...");
        for (int i = 20; i <= 30; i += 5) {
            int result = solution.tribonacci(i);
            System.out.printf("%2d | %d%n", i, result);
        }
        
        System.out.println("\nAll tests completed!");
    }
}
