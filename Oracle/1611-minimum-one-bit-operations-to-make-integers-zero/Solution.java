
# Solution.java

```java
import java.util.*;

/**
 * 1611. Minimum One Bit Operations to Make Integers Zero
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Find minimum operations to make integer n zero using specific bit operations.
 * Operations exactly match Tower of Hanoi rules.
 * 
 * Key Insights:
 * 1. This is Tower of Hanoi problem in binary form
 * 2. Operations correspond to moving disks
 * 3. Minimum operations = Gray code transformation
 * 4. Formula: result = n XOR (n >> 1) XOR (n >> 2) XOR ... until 0
 * 
 * Approach 1: Mathematical Formula (Gray Code) - RECOMMENDED
 * O(log n) time, O(1) space
 */

class Solution {
    
    /**
     * Approach 1: Mathematical Formula (Gray Code) - RECOMMENDED
     * Time: O(log n), Space: O(1)
     * Based on pattern: minimum operations = Gray code transformation
     */
    public int minimumOneBitOperations(int n) {
        int result = 0;
        while (n > 0) {
            result ^= n;
            n >>= 1;
        }
        return result;
    }
    
    /**
     * Approach 2: Recursive Tower of Hanoi
     * Time: O(log n), Space: O(log n) recursion depth
     * Direct implementation of Tower of Hanoi recurrence
     */
    public int minimumOneBitOperationsRecursive(int n) {
        if (n == 0) return 0;
        
        // Find highest bit position (1-indexed from right)
        int highestBit = 31 - Integer.numberOfLeadingZeros(n);
        int mask = (1 << highestBit) - 1;
        
        // Recurrence: f(n) = 2^highestBit + f((1 << (highestBit-1)) ^ (n & mask))
        return (1 << (highestBit + 1)) - 1 - minimumOneBitOperationsRecursive(n ^ (1 << highestBit));
    }
    
    /**
     * Approach 3: Iterative Bit Manipulation
     * Time: O(log n), Space: O(1)
     * Simulates the recursive process iteratively
     */
    public int minimumOneBitOperationsIterative(int n) {
        int result = 0;
        int sign = 1; // Tracks alternating signs in the formula
        
        while (n > 0) {
            result += sign * n;
            sign = -sign;
            n &= n - 1; // Remove lowest set bit
        }
        
        return Math.abs(result);
    }
    
    /**
     * Approach 4: Direct Gray Code Calculation
     * Time: O(log n), Space: O(1)
     * Using known Gray code formula: gray(n) = n XOR (n >> 1)
     * But we need inverse Gray code
     */
    public int minimumOneBitOperationsGrayCode(int n) {
        // The operations sequence generates Gray code
        // Minimum operations to reach 0 = Gray code value
        int gray = 0;
        int bit = 0;
        
        while (n > 0) {
            gray ^= n;
            n >>= 1;
        }
        
        return gray;
    }
    
    /**
     * Approach 5: Bit by Bit Calculation
     * Time: O(log n), Space: O(1)
     * Calculates operations needed for each bit
     */
    public int minimumOneBitOperationsBitByBit(int n) {
        int result = 0;
        int k = 0; // Current bit position
        
        while (n > 0) {
            // If current bit is set
            if ((n & 1) == 1) {
                // Operations needed to clear this bit = 2^(k+1) - 1 - result_so_far
                // But actually we need to use formula
                result = (1 << (k + 1)) - 1 - result;
            }
            n >>= 1;
            k++;
        }
        
        return result;
    }
    
    /**
     * Approach 6: DP with Memoization (for understanding)
     * Time: O(log n), Space: O(log n)
     * Not efficient for large n but shows the recurrence clearly
     */
    public int minimumOneBitOperationsDP(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        return dpHelper(n, memo);
    }
    
    private int dpHelper(int n, Map<Integer, Integer> memo) {
        if (n == 0) return 0;
        if (memo.containsKey(n)) return memo.get(n);
        
        int highestBit = 31 - Integer.numberOfLeadingZeros(n);
        int mask = (1 << highestBit) - 1;
        int next = n ^ (1 << highestBit);
        
        int result = (1 << (highestBit + 1)) - 1 - dpHelper(next & mask, memo);
        memo.put(n, result);
        return result;
    }
    
    /**
     * Helper: Convert number to binary string with leading zeros
     */
    private String toBinaryString(int n, int bits) {
        String binary = Integer.toBinaryString(n);
        return String.format("%" + bits + "s", binary).replace(' ', '0');
    }
    
    /**
     * Helper: Visualize the Tower of Hanoi analogy
     */
    public void visualizeTowerOfHanoi(int n) {
        System.out.println("\nTower of Hanoi Visualization:");
        System.out.println("n = " + n + " (binary: " + Integer.toBinaryString(n) + ")");
        
        if (n == 0) {
            System.out.println("Already at 0, 0 operations needed");
            return;
        }
        
        // Show bits as disks
        System.out.println("\nBit positions as disks (0 = smallest disk):");
        for (int i = 31; i >= 0; i--) {
            if ((n >> i) & 1) == 1) {
                System.out.println("  Disk " + i + ": ███ (size " + i + ")");
            }
        }
        
        // Show the recursive process
        System.out.println("\nRecursive clearing process:");
        visualizeRecursive(n, 0);
        
        // Calculate result
        int result = minimumOneBitOperations(n);
        System.out.println("\nMinimum operations: " + result);
        
        // Show Gray code connection
        System.out.println("\nGray Code Connection:");
        System.out.println("Operations generate Gray code sequence");
        System.out.println("n XOR (n>>1) XOR (n>>2) XOR ... = " + result);
        
        // Verify with iterative calculation
        System.out.println("\nVerification by iterative calculation:");
        int calc = 0;
        int temp = n;
        int step = 0;
        while (temp > 0) {
            calc ^= temp;
            System.out.printf("Step %d: result ^= %d (%s) -> result = %d%n",
                step++, temp, toBinaryString(temp, 8), calc);
            temp >>= 1;
        }
        System.out.println("Final result: " + calc);
    }
    
    private void visualizeRecursive(int n, int depth) {
        if (n == 0) return;
        
        String indent = "  ".repeat(depth);
        int highestBit = 31 - Integer.numberOfLeadingZeros(n);
        int mask = (1 << highestBit) - 1;
        int next = n ^ (1 << highestBit);
        
        System.out.printf("%sClear highest bit (bit %d):%n", indent, highestBit);
        System.out.printf("%s  Need to clear bits 0..%d to pattern 10..0 first%n", 
            indent, highestBit-1);
        System.out.printf("%s  Then flip bit %d%n", indent, highestBit);
        System.out.printf("%s  Then restore bits 0..%d from 10..0 to %s%n",
            indent, highestBit-1, toBinaryString(next & mask, highestBit));
        System.out.printf("%s  Total for this level: 2^%d + f(%s) operations%n",
            indent, highestBit, toBinaryString(next & mask, highestBit));
        
        visualizeRecursive(next & mask, depth + 1);
    }
    
    /**
     * Helper: Show step-by-step operations for small n
     */
    public void showStepByStepOperations(int n) {
        System.out.println("\nStep-by-step Operations for n = " + n + 
                         " (binary: " + Integer.toBinaryString(n) + "):");
        
        if (n == 0) {
            System.out.println("Already at 0, no operations needed");
            return;
        }
        
        List<String> steps = new ArrayList<>();
        int current = n;
        int operations = 0;
        
        // Generate optimal sequence (simplified)
        // For small n, we can show the actual operations
        if (n <= 7) {
            // For n <= 7, show complete sequence
            while (current != 0) {
                String binary = toBinaryString(current, 3);
                steps.add(String.format("Step %d: %s", operations++, binary));
                
                // Find which operation to apply
                // Simplified logic for demonstration
                if (current == 1) {
                    current = 0; // Operation 1 on bit 0
                } else if (current == 3) {
                    current = 1; // Operation 1 on bit 0
                } else if (current == 2) {
                    current = 3; // Operation 1 on bit 0
                } else if (current == 6) {
                    current = 2; // Operation 2 on bit 2
                }
                // ... more cases for complete demonstration
            }
            steps.add(String.format("Step %d: %s (DONE)", operations, toBinaryString(current, 3)));
            
            for (String step : steps) {
                System.out.println("  " + step);
            }
        }
        
        System.out.println("\nTotal operations: " + minimumOneBitOperations(n));
        System.out.println("\nNote: The actual operation sequence follows Gray code pattern.");
        System.out.println("For example, for n=3:");
        System.out.println("  11 -> 01 (operation 1 on bit 0)");
        System.out.println("  01 -> 00 (operation 1 on bit 0)");
    }
    
    /**
     * Helper: Explain the mathematical reasoning
     */
    public void explainMathematicalReasoning() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL REASONING:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Tower of Hanoi Analogy:");
        System.out.println("   Each bit position represents a disk size");
        System.out.println("   Bit i set = disk of size i exists");
        System.out.println("   Operation 1: Move smallest disk (bit 0)");
        System.out.println("   Operation 2: Move disk i when bits 0..i-1 are 0 and bit i-1 is 1");
        System.out.println("   This exactly matches Tower of Hanoi rules!");
        
        System.out.println("\n2. Known Result:");
        System.out.println("   Minimum moves for Tower of Hanoi with k disks: 2^k - 1");
        System.out.println("   To move k disks from source to target:");
        System.out.println("     1. Move k-1 disks to auxiliary (2^{k-1} - 1 moves)");
        System.out.println("     2. Move largest disk to target (1 move)");
        System.out.println("     3. Move k-1 disks to target (2^{k-1} - 1 moves)");
        System.out.println("     Total: 2^k - 1 moves");
        
        System.out.println("\n3. Recurrence Relation:");
        System.out.println("   Let f(n) = minimum operations to make n zero");
        System.out.println("   Let k = position of highest set bit in n");
        System.out.println("   Then:");
        System.out.println("     f(n) = 2^k + f((1 << (k-1)) ⊕ (n & ((1 << k) - 1)))");
        System.out.println("   Explanation:");
        System.out.println("     To clear bit k, we need to first make bits 0..k-1 = 100..0");
        System.out.println("     This takes 2^{k-1} operations (move k-1 disks)");
        System.out.println("     Then flip bit k (1 operation)");
        System.out.println("     Then restore bits 0..k-1 to original pattern with bit k cleared");
        
        System.out.println("\n4. Closed Form Solution:");
        System.out.println("   f(n) = n ⊕ (n >> 1) ⊕ (n >> 2) ⊕ ... until 0");
        System.out.println("   This is equivalent to Gray code transformation");
        System.out.println("   Proof by induction or observation");
        
        System.out.println("\n5. Example: n = 6 (binary 110)");
        System.out.println("   Highest bit: k = 2");
        System.out.println("   f(6) = 2^2 + f((1 << 1) ⊕ (6 & 3))");
        System.out.println("        = 4 + f(2 ⊕ 2)");
        System.out.println("        = 4 + f(0)");
        System.out.println("        = 4");
        System.out.println("   Using formula: 6 ⊕ (6>>1) ⊕ (6>>2) ⊕ ...");
        System.out.println("        = 110 ⊕ 011 ⊕ 001 = 100 = 4");
        
        System.out.println("\n6. Gray Code Connection:");
        System.out.println("   The sequence of operations generates Gray code");
        System.out.println("   Gray code: each successive value differs by one bit");
        System.out.println("   Standard Gray code: g(n) = n ⊕ (n >> 1)");
        System.out.println("   Our formula is similar but with extra terms");
        System.out.println("   It generates the sequence to reach 0 from n");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES TESTING:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. n = 0:");
        int result1 = solution.minimumOneBitOperations(0);
        System.out.println("   Result: " + result1 + " (should be 0)");
        
        System.out.println("\n2. n = 1:");
        int result2 = solution.minimumOneBitOperations(1);
        System.out.println("   Result: " + result2 + " (should be 1)");
        
        System.out.println("\n3. n = 2 (binary 10):");
        int result3 = solution.minimumOneBitOperations(2);
        System.out.println("   Result: " + result3 + " (should be 3)");
        System.out.println("   Explanation: 10 -> 11 -> 01 -> 00");
        
        System.out.println("\n4. n = 3 (Example 1):");
        int result4 = solution.minimumOneBitOperations(3);
        System.out.println("   Result: " + result4 + " (should be 2)");
        
        System.out.println("\n5. n = 4 (binary 100):");
        int result5 = solution.minimumOneBitOperations(4);
        System.out.println("   Result: " + result5 + " (should be 7)");
        System.out.println("   Explanation: Need to move 'disk 2' (Tower of Hanoi)");
        
        System.out.println("\n6. n = 6 (Example 2):");
        int result6 = solution.minimumOneBitOperations(6);
        System.out.println("   Result: " + result6 + " (should be 4)");
        
        System.out.println("\n7. n = 7 (binary 111):");
        int result7 = solution.minimumOneBitOperations(7);
        System.out.println("   Result: " + result7 + " (should be 5)");
        
        System.out.println("\n8. n = 8 (binary 1000):");
        int result8 = solution.minimumOneBitOperations(8);
        System.out.println("   Result: " + result8 + " (should be 15)");
        
        System.out.println("\n9. n = 15 (binary 1111):");
        int result9 = solution.minimumOneBitOperations(15);
        System.out.println("   Result: " + result9 + " (should be 10)");
        
        System.out.println("\n10. n = 16 (binary 10000):");
        int result10 = solution.minimumOneBitOperations(16);
        System.out.println("   Result: " + result10 + " (should be 31)");
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(int n) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES FOR n = " + n + 
                         " (binary: " + Integer.toBinaryString(n) + "):");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5, result6;
        
        // Approach 1: Mathematical Formula
        startTime = System.nanoTime();
        result1 = solution.minimumOneBitOperations(n);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Recursive
        startTime = System.nanoTime();
        result2 = solution.minimumOneBitOperationsRecursive(n);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Iterative
        startTime = System.nanoTime();
        result3 = solution.minimumOneBitOperationsIterative(n);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Gray Code
        startTime = System.nanoTime();
        result4 = solution.minimumOneBitOperationsGrayCode(n);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: Bit by Bit
        startTime = System.nanoTime();
        result5 = solution.minimumOneBitOperationsBitByBit(n);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Approach 6: DP
        if (n <= 1000) { // DP only for small n
            startTime = System.nanoTime();
            result6 = solution.minimumOneBitOperationsDP(n);
            endTime = System.nanoTime();
            long time6 = endTime - startTime;
        } else {
            result6 = result1;
        }
        
        System.out.println("\nResults:");
        System.out.println("Mathematical Formula: " + result1);
        System.out.println("Recursive:           " + result2);
        System.out.println("Iterative:           " + result3);
        System.out.println("Gray Code:           " + result4);
        System.out.println("Bit by Bit:          " + result5);
        if (n <= 1000) {
            System.out.println("DP:                  " + result6);
        }
        
        boolean allEqual = (result1 == result2) && (result2 == result3) &&
                          (result3 == result4) && (result4 == result5);
        if (n <= 1000) {
            allEqual = allEqual && (result5 == result6);
        }
        
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Mathematical Formula: %-10d (O(log n) - Recommended)%n", time1);
        System.out.printf("Recursive:           %-10d (O(log n) recursion)%n", time2);
        System.out.printf("Iterative:           %-10d (O(log n) iterative)%n", time3);
        System.out.printf("Gray Code:           %-10d (O(log n))%n", time4);
        System.out.printf("Bit by Bit:          %-10d (O(log n))%n", time5);
        if (n <= 1000) {
            System.out.printf("DP:                  %-10d (O(log n) with memo)%n", time6);
        }
        
        // Visualize for small n
        if (n <= 15) {
            System.out.println("\n" + "-".repeat(80));
            solution.visualizeTowerOfHanoi(n);
            
            System.out.println("\n" + "-".repeat(80));
            solution.showStepByStepOperations(n);
        }
    }
    
    /**
     * Helper: Analyze complexity and patterns
     */
    public void analyzePatterns() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PATTERN ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nFirst 16 values (n and f(n)):");
        System.out.println("n  | binary | f(n) | Pattern");
        System.out.println("---|--------|------|--------");
        
        Solution solution = new Solution();
        for (int n = 0; n < 16; n++) {
            int result = solution.minimumOneBitOperations(n);
            System.out.printf("%2d | %6s | %4d |", 
                n, Integer.toBinaryString(n), result);
            
            // Show pattern
            if (n == 0) System.out.print(" Base case");
            else if ((n & (n - 1)) == 0) System.out.print(" Power of 2: 2^k - 1");
            else if (n == 3 || n == 7 || n == 15) System.out.print(" All 1's: special pattern");
            
            System.out.println();
        }
        
        System.out.println("\nObservations:");
        System.out.println("1. f(2^k) = 2^{k+1} - 1 (Tower of Hanoi for k+1 disks)");
        System.out.println("2. f(2^k - 1) = ? (depends on k)");
        System.out.println("3. f(n) can be computed by clearing highest bit recursively");
        
        System.out.println("\nPattern for powers of 2:");
        for (int k = 0; k <= 5; k++) {
            int n = 1 << k;
            int result = solution.minimumOneBitOperations(n);
            System.out.printf("2^%d = %d: f(%d) = %d = 2^%d - 1%n",
                k, n, n, result, k+1);
        }
        
        System.out.println("\nRecurrence verification:");
        System.out.println("For n = 6 (110):");
        System.out.println("  Highest bit k = 2");
        System.out.println("  f(6) = 2^2 + f(2 ⊕ 2) = 4 + f(0) = 4 ✓");
        
        System.out.println("\nFor n = 13 (1101):");
        System.out.println("  Highest bit k = 3");
        System.out.println("  f(13) = 2^3 + f(4 ⊕ 5) = 8 + f(1) = 8 + 1 = 9");
        int actual = solution.minimumOneBitOperations(13);
        System.out.println("  Actual: " + actual + " ✓");
    }
    
    /**
     * Main method with comprehensive tests
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Minimum One Bit Operations to Make Integers Zero:");
        System.out.println("=================================================");
        
        // Explain mathematical reasoning
        solution.explainMathematicalReasoning();
        
        // Test edge cases
        solution.testEdgeCases();
        
        // Example 1 from problem
        System.out.println("\n\nExample 1 from problem:");
        int n1 = 3;
        int expected1 = 2;
        
        System.out.println("\nn = " + n1 + " (binary: " + Integer.toBinaryString(n1) + ")");
        
        int result1 = solution.minimumOneBitOperations(n1);
        System.out.println("Expected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1));
        
        // Example 2 from problem
        System.out.println("\n\nExample 2 from problem:");
        int n2 = 6;
        int expected2 = 4;
        
        System.out.println("\nn = " + n2 + " (binary: " + Integer.toBinaryString(n2) + ")");
        
        int result2 = solution.minimumOneBitOperations(n2);
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2));
        
        // Compare approaches for examples
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES FOR EXAMPLE 1 (n=3):");
        System.out.println("=".repeat(80));
        solution.compareApproaches(3);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES FOR EXAMPLE 2 (n=6):");
        System.out.println("=".repeat(80));
        solution.compareApproaches(6);
        
        // Test various values
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TESTING VARIOUS VALUES:");
        System.out.println("=".repeat(80));
        
        int[] testValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 16, 31, 32, 63, 100, 255, 256, 511};
        
        for (int n : testValues) {
            int result = solution.minimumOneBitOperations(n);
            System.out.printf("n = %3d (binary: %9s): f(n) = %4d%n",
                n, Integer.toBinaryString(n), result);
            
            // Verify with recursive approach for small n
            if (n <= 1000) {
                int verify = solution.minimumOneBitOperationsRecursive(n);
                if (result != verify) {
                    System.out.printf("  ERROR: Recursive gives %d!%n", verify);
                }
            }
        }
        
        // Analyze patterns
        solution.analyzePatterns();
        
        // Performance test
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(80));
        
        int[] largeValues = {1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
        
        for (int n : largeValues) {
            System.out.println("\nTesting n = " + n + " (binary length: " + 
                             (32 - Integer.numberOfLeadingZeros(n)) + " bits)");
            
            long startTime = System.nanoTime();
            int result = solution.minimumOneBitOperations(n);
            long endTime = System.nanoTime();
            
            System.out.println("Result: " + result);
            System.out.println("Time: " + (endTime - startTime) + " ns");
            
            // Verify with iterative approach
            startTime = System.nanoTime();
            int verify = solution.minimumOneBitOperationsIterative(n);
            endTime = System.nanoTime();
            
            System.out.println("Verification: " + (result == verify ? "✓" : "✗"));
            System.out.println("Verification time: " + (endTime - startTime) + " ns");
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. This is Tower of Hanoi in Disguise:");
        System.out.println("   - Each bit = disk, operation rules = Tower of Hanoi rules");
        System.out.println("   - Known minimum moves: 2^k - 1 for k disks");
        
        System.out.println("\n2. Mathematical Formula is Optimal:");
        System.out.println("   - f(n) = n ⊕ (n >> 1) ⊕ (n >> 2) ⊕ ...");
        System.out.println("   - O(log n) time, O(1) space");
        System.out.println("   - Simple implementation");
        
        System.out.println("\n3. Recursive Understanding:");
        System.out.println("   - To clear highest bit k:");
        System.out.println("     1. Clear lower bits to 100..0 pattern");
        System.out.println("     2. Flip bit k");
        System.out.println("     3. Restore lower bits from 100..0 to target");
        
        System.out.println("\n4. Gray Code Connection:");
        System.out.println("   - Operation sequence generates Gray code");
        System.out.println("   - Each step changes exactly one bit");
        System.out.println("   - Formula similar to Gray code transformation");
        
        System.out.println("\n5. For Interviews:");
        System.out.println("   - Recognize Tower of Hanoi pattern");
        System.out.println("   - Derive recurrence relation");
        System.out.println("   - Show closed form solution");
        System.out.println("   - Implement efficient O(log n) solution");
    }
}
