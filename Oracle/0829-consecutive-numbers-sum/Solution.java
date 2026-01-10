
# Solution.java

```java
import java.util.*;

/**
 * 829. Consecutive Numbers Sum
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Count number of ways to write n as sum of consecutive positive integers.
 * 
 * Key Insights:
 * 1. Mathematical formula: n = k*a + k*(k-1)/2
 *    where k = length of sequence, a = starting number
 * 2. Rearrange: a = [n - k*(k-1)/2] / k
 * 3. Valid if: (n - k*(k-1)/2) > 0 and divisible by k
 * 4. k has upper bound: k*(k-1)/2 < n → k ≈ √(2n)
 * 
 * Approach 1: Mathematical Enumeration (RECOMMENDED)
 * O(√n) time, O(1) space
 */

class Solution {
    
    /**
     * Approach 1: Mathematical Enumeration (RECOMMENDED)
     * Time: O(√n), Space: O(1)
     * Based on formula: n = k*a + k*(k-1)/2
     */
    public int consecutiveNumbersSum(int n) {
        int count = 0;
        
        // For k consecutive numbers: n = k*a + k*(k-1)/2
        // where k = number of terms, a = starting number
        // Rearranged: a = [n - k*(k-1)/2] / k
        // Valid if: (n - k*(k-1)/2) > 0 and divisible by k
        
        // k from 1 up to maximum possible
        // Condition: k*(k-1)/2 < n → k ≈ √(2n)
        for (int k = 1; k * (k - 1) / 2 < n; k++) {
            int numerator = n - k * (k - 1) / 2;
            
            // Check if divisible by k and positive
            if (numerator % k == 0) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Approach 2: Mathematical Enumeration (Optimized)
     * Time: O(√n), Space: O(1)
     * Slightly different condition check
     */
    public int consecutiveNumbersSumOptimized(int n) {
        int count = 0;
        
        // n = k*a + k*(k-1)/2
        // => 2n = k*(2a + k - 1)
        // => 2n/k = 2a + k - 1
        // Since a ≥ 1, 2a + k - 1 ≥ k + 1
        
        // k is odd: 2n divisible by k
        // k is even: n divisible by k/2
        
        // Upper bound: k*(k+1) ≤ 2n
        
        for (int k = 1; k * (k + 1) <= 2 * n; k++) {
            if ((2 * n) % k == 0) {
                int val = (2 * n) / k - k + 1;
                if (val > 0 && val % 2 == 0) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 3: Factor-based Approach
     * Time: O(√n), Space: O(1)
     * Uses factorization of 2n
     */
    public int consecutiveNumbersSumFactors(int n) {
        int count = 0;
        
        // n = k*(2a + k - 1)/2
        // => 2n = k*(2a + k - 1)
        // Let m = 2a + k - 1
        // Then 2n = k*m where m > k and m-k is odd
        
        // For each factor pair (k, m) of 2n where k < m
        // Check if m - k is odd (then a is integer)
        
        int target = 2 * n;
        for (int k = 1; k * k <= target; k++) {
            if (target % k == 0) {
                int m = target / k;
                
                // Check condition m > k and (m - k) is odd
                if (m > k && (m - k) % 2 == 1) {
                    count++;
                }
                
                // Check symmetric pair if k != m/k
                if (k * k != target) {
                    int k2 = target / k;
                    int m2 = k;
                    if (m2 > k2 && (m2 - k2) % 2 == 1) {
                        count++;
                    }
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 4: Brute Force (for small n, verification)
     * Time: O(n²), Space: O(1)
     * Check all possible starting points and lengths
     */
    public int consecutiveNumbersSumBruteForce(int n) {
        int count = 0;
        
        for (int start = 1; start <= n; start++) {
            int sum = 0;
            for (int length = 1; start + length - 1 <= n; length++) {
                sum += start + length - 1;
                if (sum == n) {
                    count++;
                    break;
                }
                if (sum > n) {
                    break;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 5: Prefix Sum with Early Termination
     * Time: O(n), Space: O(1)
     * Better than brute force but still O(n)
     */
    public int consecutiveNumbersSumPrefix(int n) {
        int count = 0;
        
        for (int length = 1; length * (length + 1) / 2 <= n; length++) {
            // Sum of 1..length = length*(length+1)/2
            // We need to find a such that:
            // (a + a+1 + ... + a+length-1) = n
            // => length*a + length*(length-1)/2 = n
            // => a = (n - length*(length-1)/2) / length
            
            long numerator = n - (long)length * (length - 1) / 2;
            if (numerator > 0 && numerator % length == 0) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Approach 6: Mathematical Property
     * Time: O(√n), Space: O(1)
     * Based on property: count = number of odd divisors of n
     */
    public int consecutiveNumbersSumOddDivisors(int n) {
        // Property: Number of ways = number of odd divisors of n
        // This comes from the fact that:
        // If sequence has odd length k, then n = k * middle
        // If sequence has even length k, then n = k/2 * (2*middle+1)
        // In both cases, we're counting odd factors
        
        int count = 0;
        
        // Count odd divisors
        for (int d = 1; d * d <= n; d++) {
            if (n % d == 0) {
                // Check if divisor is odd
                if (d % 2 == 1) {
                    count++;
                }
                // Check symmetric divisor if different
                if (d * d != n && (n / d) % 2 == 1) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Helper: Find all actual sequences
     * Returns list of sequences that sum to n
     */
    public List<List<Integer>> findConsecutiveSequences(int n) {
        List<List<Integer>> sequences = new ArrayList<>();
        
        for (int k = 1; k * (k - 1) / 2 < n; k++) {
            int numerator = n - k * (k - 1) / 2;
            
            if (numerator % k == 0 && numerator > 0) {
                int a = numerator / k; // starting number
                List<Integer> sequence = new ArrayList<>();
                for (int i = 0; i < k; i++) {
                    sequence.add(a + i);
                }
                sequences.add(sequence);
            }
        }
        
        return sequences;
    }
    
    /**
     * Helper: Visualize the mathematical derivation
     */
    public void visualizeSolution(int n) {
        System.out.println("\nConsecutive Numbers Sum Visualization:");
        System.out.println("n = " + n);
        System.out.println("\nMathematical Derivation:");
        System.out.println("For k consecutive numbers starting at a:");
        System.out.println("  Sum = a + (a+1) + ... + (a+k-1)");
        System.out.println("      = k*a + (0+1+...+(k-1))");
        System.out.println("      = k*a + k*(k-1)/2");
        System.out.println("\nEquation: n = k*a + k*(k-1)/2");
        System.out.println("Rearrange: a = [n - k*(k-1)/2] / k");
        System.out.println("\nConditions for valid sequence:");
        System.out.println("1. numerator = n - k*(k-1)/2 > 0");
        System.out.println("2. numerator divisible by k");
        System.out.println("3. a = numerator/k ≥ 1 (automatically if numerator > 0)");
        
        System.out.println("\nIterating k from 1 while k*(k-1)/2 < n:");
        int count = 0;
        
        for (int k = 1; k * (k - 1) / 2 < n; k++) {
            int numerator = n - k * (k - 1) / 2;
            boolean divisible = (numerator % k == 0);
            boolean positive = (numerator > 0);
            boolean valid = divisible && positive;
            
            System.out.printf("\nk = %d:", k);
            System.out.printf("\n  k*(k-1)/2 = %d", k * (k - 1) / 2);
            System.out.printf("\n  numerator = %d - %d = %d", 
                n, k * (k - 1) / 2, numerator);
            System.out.printf("\n  divisible by k? %s", divisible ? "✓" : "✗");
            System.out.printf("\n  positive? %s", positive ? "✓" : "✗");
            
            if (valid) {
                int a = numerator / k;
                System.out.printf("\n  Valid! Start at a = %d", a);
                System.out.printf("\n  Sequence: ");
                for (int i = 0; i < k; i++) {
                    System.out.print((a + i));
                    if (i < k - 1) System.out.print(" + ");
                }
                System.out.printf(" = %d", n);
                count++;
            }
            System.out.println();
        }
        
        System.out.println("\nTotal sequences found: " + count);
        
        // Show all sequences
        List<List<Integer>> sequences = findConsecutiveSequences(n);
        System.out.println("\nAll valid sequences:");
        for (List<Integer> seq : sequences) {
            System.out.print("  " + seq.get(0));
            for (int i = 1; i < seq.size(); i++) {
                System.out.print(" + " + seq.get(i));
            }
            System.out.println(" = " + n);
        }
    }
    
    /**
     * Helper: Explain the mathematical reasoning in detail
     */
    public void explainMathematicalReasoning() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL REASONING:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Problem Restatement:");
        System.out.println("Find all sequences of consecutive positive integers that sum to n.");
        System.out.println("Let sequence be: a, a+1, a+2, ..., a+k-1");
        System.out.println("where: a ≥ 1 (positive), k ≥ 1 (length)");
        
        System.out.println("\n2. Sum Formula:");
        System.out.println("Sum = a + (a+1) + ... + (a+k-1)");
        System.out.println("    = k*a + (1 + 2 + ... + (k-1))");
        System.out.println("    = k*a + k*(k-1)/2  (sum of first k-1 integers)");
        
        System.out.println("\n3. Equation Setup:");
        System.out.println("n = k*a + k*(k-1)/2");
        System.out.println("Rearrange for a:");
        System.out.println("k*a = n - k*(k-1)/2");
        System.out.println("a = [n - k*(k-1)/2] / k");
        
        System.out.println("\n4. Conditions for Valid Sequence:");
        System.out.println("a must be a positive integer, so:");
        System.out.println("(1) numerator = n - k*(k-1)/2 > 0");
        System.out.println("(2) numerator divisible by k");
        
        System.out.println("\n5. Upper Bound for k:");
        System.out.println("From condition (1): n - k*(k-1)/2 > 0");
        System.out.println("=> k*(k-1)/2 < n");
        System.out.println("=> k*(k-1) < 2n");
        System.out.println("=> k² - k - 2n < 0");
        System.out.println("=> k < (1 + √(1+8n))/2 ≈ √(2n)");
        System.out.println("So we only need to check k up to ≈ √(2n)");
        
        System.out.println("\n6. Algorithm:");
        System.out.println("For k = 1 to while k*(k-1)/2 < n:");
        System.out.println("  numerator = n - k*(k-1)/2");
        System.out.println("  if numerator % k == 0:");
        System.out.println("    count++ (valid sequence found)");
        
        System.out.println("\n7. Example: n = 15");
        System.out.println("k=1: numerator=15, divisible ✓ → sequence: [15]");
        System.out.println("k=2: numerator=14, divisible ✓ → sequence: [7,8]");
        System.out.println("k=3: numerator=12, divisible ✓ → sequence: [4,5,6]");
        System.out.println("k=4: numerator=9, not divisible ✗");
        System.out.println("k=5: numerator=5, divisible ✓ → sequence: [1,2,3,4,5]");
        System.out.println("k=6: numerator=0, not positive ✗");
        System.out.println("Total: 4 sequences");
        
        System.out.println("\n8. Time Complexity:");
        System.out.println("Number of iterations: k up to √(2n)");
        System.out.println("Time: O(√n)");
        System.out.println("Space: O(1)");
        
        System.out.println("\n9. Alternative Interpretation:");
        System.out.println("n = k*a + k*(k-1)/2");
        System.out.println("=> 2n = k*(2a + k - 1)");
        System.out.println("So we're looking for factor pairs (k, m) of 2n");
        System.out.println("where m = 2a + k - 1 > k and (m - k) is odd");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES TESTING:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. n = 1:");
        int result1 = solution.consecutiveNumbersSum(1);
        System.out.println("   Result: " + result1 + " (should be 1: [1])");
        
        System.out.println("\n2. n = 2:");
        int result2 = solution.consecutiveNumbersSum(2);
        System.out.println("   Result: " + result2 + " (should be 1: [2])");
        
        System.out.println("\n3. n = 3:");
        int result3 = solution.consecutiveNumbersSum(3);
        System.out.println("   Result: " + result3 + " (should be 2: [3], [1,2])");
        
        System.out.println("\n4. n = 4:");
        int result4 = solution.consecutiveNumbersSum(4);
        System.out.println("   Result: " + result4 + " (should be 1: [4])");
        
        System.out.println("\n5. n = 5 (Example 1):");
        int result5 = solution.consecutiveNumbersSum(5);
        System.out.println("   Result: " + result5 + " (should be 2: [5], [2,3])");
        
        System.out.println("\n6. n = 9 (Example 2):");
        int result6 = solution.consecutiveNumbersSum(9);
        System.out.println("   Result: " + result6 + " (should be 3: [9], [4,5], [2,3,4])");
        
        System.out.println("\n7. n = 15 (Example 3):");
        int result7 = solution.consecutiveNumbersSum(15);
        System.out.println("   Result: " + result7 + " (should be 4)");
        
        System.out.println("\n8. Prime number n = 17:");
        int result8 = solution.consecutiveNumbersSum(17);
        System.out.println("   Result: " + result8 + " (should be 2: [17], [8,9])");
        
        System.out.println("\n9. Power of 2 n = 16:");
        int result9 = solution.consecutiveNumbersSum(16);
        System.out.println("   Result: " + result9 + " (should be 1: [16])");
        
        System.out.println("\n10. Triangular number n = 10 (1+2+3+4):");
        int result10 = solution.consecutiveNumbersSum(10);
        System.out.println("   Result: " + result10 + " (should be 2: [10], [1,2,3,4])");
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(int n) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES FOR n = " + n + ":");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\nFinding all ways to write " + n + " as sum of consecutive integers:");
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5, result6;
        
        // Approach 1: Mathematical Enumeration
        startTime = System.nanoTime();
        result1 = solution.consecutiveNumbersSum(n);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Optimized Mathematical
        startTime = System.nanoTime();
        result2 = solution.consecutiveNumbersSumOptimized(n);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Factor-based
        startTime = System.nanoTime();
        result3 = solution.consecutiveNumbersSumFactors(n);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 5: Prefix Sum (O(n))
        startTime = System.nanoTime();
        result5 = solution.consecutiveNumbersSumPrefix(n);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Approach 6: Odd Divisors
        startTime = System.nanoTime();
        result6 = solution.consecutiveNumbersSumOddDivisors(n);
        endTime = System.nanoTime();
        long time6 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Mathematical Enumeration: " + result1);
        System.out.println("Optimized Mathematical:   " + result2);
        System.out.println("Factor-based:            " + result3);
        System.out.println("Prefix Sum:              " + result5);
        System.out.println("Odd Divisors:            " + result6);
        
        boolean allEqual = (result1 == result2) && (result2 == result3) &&
                          (result3 == result5) && (result5 == result6);
        
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        // Approach 4: Brute Force (only for small n)
        if (n <= 1000) {
            startTime = System.nanoTime();
            result4 = solution.consecutiveNumbersSumBruteForce(n);
            endTime = System.nanoTime();
            long time4 = endTime - startTime;
            
            System.out.println("Brute Force:             " + result4);
            System.out.println("Matches others: " + (result4 == result1 ? "✓" : "✗"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Mathematical Enumeration: %-10d (O(√n) - Recommended)%n", time1);
            System.out.printf("Optimized Mathematical:   %-10d (O(√n))%n", time2);
            System.out.printf("Factor-based:            %-10d (O(√n))%n", time3);
            System.out.printf("Brute Force:             %-10d (O(n²))%n", time4);
            System.out.printf("Prefix Sum:              %-10d (O(n))%n", time5);
            System.out.printf("Odd Divisors:            %-10d (O(√n))%n", time6);
        } else {
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Mathematical Enumeration: %-10d (O(√n) - Recommended)%n", time1);
            System.out.printf("Optimized Mathematical:   %-10d (O(√n))%n", time2);
            System.out.printf("Factor-based:            %-10d (O(√n))%n", time3);
            System.out.printf("Prefix Sum:              %-10d (O(n))%n", time5);
            System.out.printf("Odd Divisors:            %-10d (O(√n))%n", time6);
        }
        
        // Show sequences for small n
        if (n <= 50) {
            System.out.println("\n" + "-".repeat(80));
            solution.visualizeSolution(n);
        }
    }
    
    /**
     * Helper: Analyze patterns and properties
     */
    public void analyzePatterns() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PATTERN ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nObservations:");
        System.out.println("1. Every number n has at least one representation: n itself");
        System.out.println("2. Numbers with multiple representations:");
        System.out.println("   - Triangular numbers: can be written as 1+2+...+k");
        System.out.println("   - Numbers with odd divisors > 1");
        
        System.out.println("\nPatterns for small n:");
        System.out.println("n  | #ways | Sequences");
        System.out.println("---|-------|----------");
        
        Solution solution = new Solution();
        for (int n = 1; n <= 20; n++) {
            int ways = solution.consecutiveNumbersSum(n);
            List<List<Integer>> sequences = solution.findConsecutiveSequences(n);
            
            System.out.printf("%2d | %5d | ", n, ways);
            for (int i = 0; i < sequences.size(); i++) {
                List<Integer> seq = sequences.get(i);
                System.out.print("[");
                for (int j = 0; j < seq.size(); j++) {
                    System.out.print(seq.get(j));
                    if (j < seq.size() - 1) System.out.print(",");
                }
                System.out.print("]");
                if (i < sequences.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
        
        System.out.println("\nInteresting Properties:");
        System.out.println("1. Powers of 2 have only 1 representation: themselves");
        System.out.println("   Because they have no odd divisors > 1");
        System.out.println("2. Prime numbers have exactly 2 representations:");
        System.out.println("   - The number itself");
        System.out.println("   - (p-1)/2 + (p+1)/2 if p is odd prime");
        System.out.println("3. Triangular numbers T_k = k(k+1)/2 have at least 2:");
        System.out.println("   - The number itself");
        System.out.println("   - 1+2+...+k");
        
        System.out.println("\nMathematical Proof Outline:");
        System.out.println("From n = k*a + k(k-1)/2");
        System.out.println("=> 2n = k(2a + k - 1)");
        System.out.println("Let m = 2a + k - 1, then 2n = k*m");
        System.out.println("Conditions: m > k (since a ≥ 1)");
        System.out.println("           m - k is odd (since 2a - 1 is odd)");
        System.out.println("So count = # of factor pairs (k,m) of 2n with m>k and m-k odd");
    }
    
    /**
     * Helper: Show related problems
     */
    public void showRelatedProblems() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 413. Arithmetic Slices:");
        System.out.println("   - Count arithmetic sequences in array");
        System.out.println("   - Similar concept of consecutive sequences");
        
        System.out.println("\n2. 446. Arithmetic Slices II - Subsequence:");
        System.out.println("   - Count arithmetic subsequences");
        System.out.println("   - More general than 413");
        
        System.out.println("\n3. 1502. Can Make Arithmetic Progression From Sequence:");
        System.out.println("   - Check if array can form arithmetic progression");
        System.out.println("   - Related to sequence properties");
        
        System.out.println("\n4. 1630. Arithmetic Subarrays:");
        System.out.println("   - Check subarrays can be rearranged to arithmetic sequence");
        
        System.out.println("\n5. 829. Consecutive Numbers Sum (this problem)");
        
        System.out.println("\n6. 829. Consecutive Numbers Sum (same number, different context)");
        
        System.out.println("\nCommon Themes:");
        System.out.println("- Sequence properties and patterns");
        System.out.println("- Mathematical analysis of sequences");
        System.out.println.- Efficient enumeration techniques");
    }
    
    /**
     * Main method with comprehensive tests
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Consecutive Numbers Sum (829):");
        System.out.println("==============================");
        
        // Explain mathematical reasoning
        solution.explainMathematicalReasoning();
        
        // Test edge cases
        solution.testEdgeCases();
        
        // Example 1 from problem
        System.out.println("\n\nExample 1 from problem:");
        int n1 = 5;
        int expected1 = 2;
        
        System.out.println("\nn = " + n1);
        
        int result1 = solution.consecutiveNumbersSum(n1);
        System.out.println("Expected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        // Show sequences
        List<List<Integer>> sequences1 = solution.findConsecutiveSequences(n1);
        System.out.println("Sequences:");
        for (List<Integer> seq : sequences1) {
            System.out.print("  " + seq.get(0));
            for (int i = 1; i < seq.size(); i++) {
                System.out.print(" + " + seq.get(i));
            }
            System.out.println(" = " + n1);
        }
        
        // Example 2 from problem
        System.out.println("\n\nExample 2 from problem:");
        int n2 = 9;
        int expected2 = 3;
        
        System.out.println("\nn = " + n2);
        
        int result2 = solution.consecutiveNumbersSum(n2);
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        // Example 3 from problem
        System.out.println("\n\nExample 3 from problem:");
        int n3 = 15;
        int expected3 = 4;
        
        System.out.println("\nn = " + n3);
        
        int result3 = solution.consecutiveNumbersSum(n3);
        System.out.println("Expected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + (result3 == expected3 ? "✓" : "✗"));
        
        // Compare approaches for examples
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES FOR EXAMPLE 1 (n=5):");
        System.out.println("=".repeat(80));
        solution.compareApproaches(5);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES FOR EXAMPLE 2 (n=9):");
        System.out.println("=".repeat(80));
        solution.compareApproaches(9);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES FOR EXAMPLE 3 (n=15):");
        System.out.println("=".repeat(80));
        solution.compareApproaches(15);
        
        // Analyze patterns
        solution.analyzePatterns();
        
        // Performance test with larger n
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST WITH LARGER n:");
        System.out.println("=".repeat(80));
        
        int[] testValues = {100, 1000, 10000, 100000, 1000000, 10000000};
        
        for (int n : testValues) {
            System.out.println("\nTesting n = " + n + ":");
            
            long startTime = System.currentTimeMillis();
            int result = solution.consecutiveNumbersSum(n);
            long endTime = System.currentTimeMillis();
            
            System.out.println("Number of ways: " + result);
            System.out.println("Time: " + (endTime - startTime) + " ms");
            
            // Verify with prefix sum approach for n ≤ 100000
            if (n <= 100000) {
                int verify = solution.consecutiveNumbersSumPrefix(n);
                System.out.println("Verification: " + (result == verify ? "✓" : "✗"));
            }
        }
        
        // Show related problems
        solution.showRelatedProblems();
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Mathematical Insight is Key:");
        System.out.println("   - Derive formula: n = k*a + k(k-1)/2");
        System.out.println("   - Transform to: a = [n - k(k-1)/2] / k");
        
        System.out.println("\n2. Efficient Algorithm:");
        System.out.println("   - Iterate k from 1 while k(k-1)/2 < n");
        System.out.println("   - Check if (n - k(k-1)/2) divisible by k");
        System.out.println("   - Time: O(√n), Space: O(1)");
        
        System.out.println("\n3. Important Observations:");
        System.out.println("   - Powers of 2 have only 1 representation");
        System.out.println("   - Primes have exactly 2 representations");
        System.out.println("   - Triangular numbers have at least 2");
        System.out.println("   - Count = number of odd divisors of n");
        
        System.out
