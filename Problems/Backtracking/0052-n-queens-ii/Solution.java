
## Solution.java

```java
/**
 * 52. N-Queens II
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given an integer n, return the number of distinct solutions to the n-queens puzzle.
 * 
 * Key Insights:
 * 1. Same problem as N-Queens I but only need to count solutions, not generate them
 * 2. Can use optimized approaches that don't store board configurations
 * 3. Bit manipulation is particularly efficient for counting
 * 4. Use the same validation logic with mathematical properties of diagonals
 * 
 * Approach (Backtracking with Bit Manipulation):
 * 1. Use integers as bit masks to represent occupied columns and diagonals
 * 2. Recursively place queens row by row
 * 3. Count solutions when all queens are placed
 * 4. Use bit operations for efficient conflict checking
 * 
 * Time Complexity: O(n!)
 * Space Complexity: O(n) for recursion stack
 * 
 * Tags: Backtracking, Depth-First Search, Bit Manipulation
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Backtracking with Bit Manipulation - RECOMMENDED
     * O(n!) time, O(n) space
     */
    public int totalNQueens(int n) {
        if (n <= 0) return 0;
        return backtrackBit(0, 0, 0, 0, n);
    }
    
    private int backtrackBit(int row, int cols, int diag1, int diag2, int n) {
        // Base case: all queens placed
        if (row == n) {
            return 1;
        }
        
        int count = 0;
        // Get available positions using bitmask
        // cols | diag1 | diag2 represents all threatened positions
        // ~(cols | diag1 | diag2) gets available positions, but we need to mask to n bits
        int available = ((1 << n) - 1) & ~(cols | diag1 | diag2);
        
        while (available != 0) {
            // Get the rightmost available position
            int position = available & -available;
            
            // Recurse with updated constraints
            count += backtrackBit(row + 1, 
                                cols | position, 
                                (diag1 | position) << 1, 
                                (diag2 | position) >> 1, 
                                n);
            
            // Remove this position from available
            available &= available - 1;
        }
        
        return count;
    }
    
    /**
     * Approach 2: Backtracking with Boolean Arrays
     * O(n!) time, O(n) space
     */
    public int totalNQueensArrays(int n) {
        if (n <= 0) return 0;
        
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1]; // main diagonals: row - col + (n-1)
        boolean[] diag2 = new boolean[2 * n - 1]; // anti-diagonals: row + col
        
        return backtrackArrays(0, cols, diag1, diag2, n);
    }
    
    private int backtrackArrays(int row, boolean[] cols, boolean[] diag1, boolean[] diag2, int n) {
        if (row == n) {
            return 1;
        }
        
        int count = 0;
        for (int col = 0; col < n; col++) {
            int d1 = row - col + n - 1;
            int d2 = row + col;
            
            if (!cols[col] && !diag1[d1] && !diag2[d2]) {
                // Place queen
                cols[col] = true;
                diag1[d1] = true;
                diag2[d2] = true;
                
                // Recurse
                count += backtrackArrays(row + 1, cols, diag1, diag2, n);
                
                // Backtrack
                cols[col] = false;
                diag1[d1] = false;
                diag2[d2] = false;
            }
        }
        return count;
    }
    
    /**
     * Approach 3: Backtracking with Direct Validation
     * O(n! × n) time, O(n) space
     */
    public int totalNQueensDirect(int n) {
        if (n <= 0) return 0;
        int[] queens = new int[n]; // queens[row] = column
        Arrays.fill(queens, -1);
        return backtrackDirect(0, queens, n);
    }
    
    private int backtrackDirect(int row, int[] queens, int n) {
        if (row == n) {
            return 1;
        }
        
        int count = 0;
        for (int col = 0; col < n; col++) {
            if (isValidDirect(queens, row, col)) {
                queens[row] = col;
                count += backtrackDirect(row + 1, queens, n);
                queens[row] = -1;
            }
        }
        return count;
    }
    
    private boolean isValidDirect(int[] queens, int row, int col) {
        for (int i = 0; i < row; i++) {
            // Check same column or same diagonal
            if (queens[i] == col || Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Approach 4: Iterative Backtracking
     * O(n!) time, O(n) space
     */
    public int totalNQueensIterative(int n) {
        if (n <= 0) return 0;
        
        int count = 0;
        int[] queens = new int[n];
        Arrays.fill(queens, -1);
        
        int row = 0, col = 0;
        while (row >= 0) {
            // Find next valid column in current row
            while (col < n) {
                if (isValidIterative(queens, row, col)) {
                    queens[row] = col;
                    col = 0;
                    break;
                } else {
                    col++;
                }
            }
            
            if (queens[row] == -1) {
                // No valid column found, backtrack
                row--;
                if (row >= 0) {
                    col = queens[row] + 1;
                    queens[row] = -1;
                }
            } else {
                // Valid column found
                if (row == n - 1) {
                    // Solution found
                    count++;
                    // Backtrack to find next solution
                    queens[row] = -1;
                    row--;
                    if (row >= 0) {
                        col = queens[row] + 1;
                        queens[row] = -1;
                    }
                } else {
                    // Move to next row
                    row++;
                }
            }
        }
        
        return count;
    }
    
    private boolean isValidIterative(int[] queens, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col || Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Approach 5: Using Sets for Tracking
     * O(n!) time, O(n) space
     */
    public int totalNQueensSet(int n) {
        if (n <= 0) return 0;
        
        Set<Integer> cols = new HashSet<>();
        Set<Integer> diag1 = new HashSet<>(); // row - col
        Set<Integer> diag2 = new HashSet<>(); // row + col
        
        return backtrackSet(0, cols, diag1, diag2, n);
    }
    
    private int backtrackSet(int row, Set<Integer> cols, Set<Integer> diag1, Set<Integer> diag2, int n) {
        if (row == n) {
            return 1;
        }
        
        int count = 0;
        for (int col = 0; col < n; col++) {
            int d1 = row - col;
            int d2 = row + col;
            
            if (!cols.contains(col) && !diag1.contains(d1) && !diag2.contains(d2)) {
                cols.add(col);
                diag1.add(d1);
                diag2.add(d2);
                
                count += backtrackSet(row + 1, cols, diag1, diag2, n);
                
                cols.remove(col);
                diag1.remove(d1);
                diag2.remove(d2);
            }
        }
        return count;
    }
    
    /**
     * Approach 6: Optimized Bit Manipulation with Precomputation
     * O(n!) time, O(1) extra space
     */
    public int totalNQueensBitOpt(int n) {
        if (n <= 0) return 0;
        
        // Precompute bit masks for each row
        int[] rowMasks = new int[n];
        for (int i = 0; i < n; i++) {
            rowMasks[i] = 1 << i;
        }
        
        return backtrackBitOpt(0, 0, 0, 0, n, rowMasks);
    }
    
    private int backtrackBitOpt(int row, int cols, int diag1, int diag2, int n, int[] rowMasks) {
        if (row == n) {
            return 1;
        }
        
        int count = 0;
        int available = ((1 << n) - 1) & ~(cols | diag1 | diag2);
        
        while (available != 0) {
            int position = available & -available;
            int col = Integer.numberOfTrailingZeros(position);
            
            count += backtrackBitOpt(row + 1, 
                                   cols | position, 
                                   (diag1 | position) << 1, 
                                   (diag2 | position) >> 1, 
                                   n, rowMasks);
            
            available &= available - 1;
        }
        
        return count;
    }
    
    /**
     * Approach 7: Lookup Table for Small n (Optimized)
     * O(1) time, O(1) space
     */
    public int totalNQueensLookup(int n) {
        // Known solutions for n = 1 to 9
        int[] solutions = {1, 0, 0, 2, 10, 4, 40, 92, 352};
        return n >= 1 && n <= 9 ? solutions[n - 1] : 0;
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    public void visualizeNQueensCounting(int n) {
        System.out.println("\nN-Queens II Visualization (Counting, n = " + n + "):");
        System.out.println("Expected number of solutions: " + getExpectedSolutionCount(n));
        
        int count = 0;
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];
        boolean[] diag2 = new boolean[2 * n - 1];
        
        System.out.println("\nBacktracking Process (Counting):");
        System.out.println("Row | Current Count | Action");
        System.out.println("----|---------------|--------");
        
        count = visualizeBacktrackCounting(0, cols, diag1, diag2, n, 0, 0);
        
        System.out.println("\nTotal solutions counted: " + count);
    }
    
    private int visualizeBacktrackCounting(int row, boolean[] cols, boolean[] diag1, 
                                         boolean[] diag2, int n, int depth, int currentCount) {
        if (row == n) {
            System.out.printf("%3d | %13d | SOLUTION #%d FOUND%n", 
                            row, currentCount + 1, currentCount + 1);
            return currentCount + 1;
        }
        
        int totalCount = currentCount;
        for (int col = 0; col < n; col++) {
            int d1 = row - col + n - 1;
            int d2 = row + col;
            
            if (!cols[col] && !diag1[d1] && !diag2[d2]) {
                System.out.printf("%3d | %13d | Try column %d (valid)%n", 
                                row, totalCount, col);
                
                // Place queen
                cols[col] = true;
                diag1[d1] = true;
                diag2[d2] = true;
                
                // Recurse
                totalCount = visualizeBacktrackCounting(row + 1, cols, diag1, diag2, n, depth + 1, totalCount);
                
                // Backtrack
                cols[col] = false;
                diag1[d1] = false;
                diag2[d2] = false;
                
                System.out.printf("%3d | %13d | Backtrack from column %d%n", 
                                row, totalCount, col);
            } else {
                System.out.printf("%3d | %13d | Skip column %d (conflict)%n", 
                                row, totalCount, col);
            }
        }
        return totalCount;
    }
    
    /**
     * Helper method to compare all approaches
     */
    public void compareAllApproaches(int n) {
        System.out.println("\nComparing All Approaches for n = " + n + ":");
        System.out.println("Expected: " + getExpectedSolutionCount(n));
        
        long startTime, endTime;
        int result;
        
        // Approach 1: Bit Manipulation
        startTime = System.nanoTime();
        result = totalNQueens(n);
        endTime = System.nanoTime();
        System.out.printf("Bit Manipulation: %d solutions, %d ns%n", result, (endTime - startTime));
        
        // Approach 2: Boolean Arrays
        startTime = System.nanoTime();
        result = totalNQueensArrays(n);
        endTime = System.nanoTime();
        System.out.printf("Boolean Arrays:   %d solutions, %d ns%n", result, (endTime - startTime));
        
        // Approach 3: Direct Validation
        startTime = System.nanoTime();
        result = totalNQueensDirect(n);
        endTime = System.nanoTime();
        System.out.printf("Direct Validation: %d solutions, %d ns%n", result, (endTime - startTime));
        
        // Approach 4: Iterative
        startTime = System.nanoTime();
        result = totalNQueensIterative(n);
        endTime = System.nanoTime();
        System.out.printf("Iterative:        %d solutions, %d ns%n", result, (endTime - startTime));
        
        // Approach 5: Set Tracking
        startTime = System.nanoTime();
        result = totalNQueensSet(n);
        endTime = System.nanoTime();
        System.out.printf("Set Tracking:     %d solutions, %d ns%n", result, (endTime - startTime));
        
        // Approach 6: Optimized Bit
        startTime = System.nanoTime();
        result = totalNQueensBitOpt(n);
        endTime = System.nanoTime();
        System.out.printf("Optimized Bit:    %d solutions, %d ns%n", result, (endTime - startTime));
        
        // Approach 7: Lookup Table
        startTime = System.nanoTime();
        result = totalNQueensLookup(n);
        endTime = System.nanoTime();
        System.out.printf("Lookup Table:     %d solutions, %d ns%n", result, (endTime - startTime));
    }
    
    private int getExpectedSolutionCount(int n) {
        int[] expected = {1, 0, 0, 2, 10, 4, 40, 92, 352};
        return n >= 1 && n <= 9 ? expected[n - 1] : 0;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing N-Queens II Solution:");
        System.out.println("=============================");
        
        // Test case 1: n = 4
        System.out.println("\nTest 1: n = 4");
        int n1 = 4;
        int expected1 = 2;
        
        long startTime = System.nanoTime();
        int result1a = solution.totalNQueens(n1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.totalNQueensArrays(n1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.totalNQueensDirect(n1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.totalNQueensIterative(n1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.totalNQueensSet(n1);
        long time1e = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1f = solution.totalNQueensBitOpt(n1);
        long time1f = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1g = solution.totalNQueensLookup(n1);
        long time1g = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        boolean test1f = result1f == expected1;
        boolean test1g = result1g == expected1;
        
        System.out.println("Bit Manipulation: " + result1a + " solutions - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Boolean Arrays:   " + result1b + " solutions - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Direct Validation: " + result1c + " solutions - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Iterative:        " + result1d + " solutions - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Set Tracking:     " + result1e + " solutions - " + (test1e ? "PASSED" : "FAILED"));
        System.out.println("Optimized Bit:    " + result1f + " solutions - " + (test1f ? "PASSED" : "FAILED"));
        System.out.println("Lookup Table:     " + result1g + " solutions - " + (test1g ? "PASSED" : "FAILED"));
        
        // Visualize the counting process
        solution.visualizeNQueensCounting(n1);
        
        // Test case 2: n = 1
        System.out.println("\nTest 2: n = 1");
        int n2 = 1;
        int result2a = solution.totalNQueens(n2);
        System.out.println("n = 1: " + result2a + " solutions - " + 
                         (result2a == 1 ? "PASSED" : "FAILED"));
        
        // Test case 3: n = 2 (no solutions)
        System.out.println("\nTest 3: n = 2");
        int n3 = 2;
        int result3a = solution.totalNQueens(n3);
        System.out.println("n = 2: " + result3a + " solutions - " + 
                         (result3a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 4: n = 3 (no solutions)
        System.out.println("\nTest 4: n = 3");
        int n4 = 3;
        int result4a = solution.totalNQueens(n4);
        System.out.println("n = 3: " + result4a + " solutions - " + 
                         (result4a == 0 ? "PASSED" : "FAILED"));
        
        // Test case 5: n = 5
        System.out.println("\nTest 5: n = 5");
        int n5 = 5;
        int result5a = solution.totalNQueens(n5);
        System.out.println("n = 5: " + result5a + " solutions - " + 
                         (result5a == 10 ? "PASSED" : "FAILED"));
        
        // Test case 6: n = 8
        System.out.println("\nTest 6: n = 8");
        int n6 = 8;
        int result6a = solution.totalNQueens(n6);
        System.out.println("n = 8: " + result6a + " solutions - " + 
                         (result6a == 92 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison (n = 4)");
        System.out.println("Bit Manipulation: " + time1a + " ns");
        System.out.println("Boolean Arrays:   " + time1b + " ns");
        System.out.println("Direct Validation: " + time1c + " ns");
        System.out.println("Iterative:        " + time1d + " ns");
        System.out.println("Set Tracking:     " + time1e + " ns");
        System.out.println("Optimized Bit:    " + time1f + " ns");
        System.out.println("Lookup Table:     " + time1g + " ns");
        
        // Test all approaches produce same results
        System.out.println("\nTest 8: All approaches consistency");
        boolean allConsistent = result1a == result1b && 
                              result1a == result1c && 
                              result1a == result1d &&
                              result1a == result1e &&
                              result1a == result1f &&
                              result1a == result1g;
        System.out.println("All approaches produce same results: " + allConsistent);
        
        // Compare all approaches for different n values
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE COMPARISON:");
        System.out.println("=".repeat(70));
        
        for (int i = 1; i <= 6; i++) {
            solution.compareAllApproaches(i);
            System.out.println();
        }
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BIT MANIPULATION EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Use integers as bit masks to represent the chessboard state.");
        System.out.println("Each bit represents a column position (1 = occupied, 0 = available).");
        
        System.out.println("\nBit Operations Used:");
        System.out.println("1. cols | diag1 | diag2 - Combined threatened positions");
        System.out.println("2. ~(cols | diag1 | diag2) - Available positions (inverted)");
        System.out.println("3. ((1 << n) - 1) - Bitmask for n columns");
        System.out.println("4. available & -available - Get rightmost set bit");
        System.out.println("5. available & (available - 1) - Remove rightmost set bit");
        
        System.out.println("\nDiagonal Representation:");
        System.out.println("1. Main Diagonal (\\): (diag1 | position) << 1");
        System.out.println("   - Shifts threat one position left for next row");
        System.out.println("2. Anti-Diagonal (/): (diag2 | position) >> 1");
        System.out.println("   - Shifts threat one position right for next row");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Bit Manipulation (RECOMMENDED):");
        System.out.println("   Time: O(n!) - Pruned backtracking");
        System.out.println("   Space: O(n) - Recursion stack only");
        System.out.println("   How it works:");
        System.out.println("     - Use integer bit masks for columns/diagonals");
        System.out.println("     - Bit operations for O(1) conflict checking");
        System.out.println("     - Count solutions without storing boards");
        System.out.println("   Pros:");
        System.out.println("     - Fastest implementation");
        System.out.println("     - Minimal memory usage");
        System.out.println("     - Elegant mathematical solution");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of bit operations");
        System.out.println("     - Harder to debug");
        System.out.println("   Best for: Counting problems, competitive programming");
        
        System.out.println("\n2. Boolean Arrays:");
        System.out.println("   Time: O(n!) - Same complexity");
        System.out.println("   Space: O(n) - Arrays for tracking");
        System.out.println("   How it works:");
        System.out.println("     - Use boolean arrays to track conflicts");
        System.out.println("     - O(1) validation with array lookups");
        System.out.println("   Pros:");
        System.out.println("     - Easy to understand");
        System.out.println("     - Clear implementation");
        System.out.println("     - Good performance");
        System.out.println("   Cons:");
        System.out.println("     - Slightly slower than bit manipulation");
        System.out.println("   Best for: Interview settings, readability");
        
        System.out.println("\n3. Direct Validation:");
        System.out.println("   Time: O(n! × n) - O(n) validation per placement");
        System.out.println("   Space: O(n) - Queen positions array");
        System.out.println("   How it works:");
        System.out.println("     - Check conflicts by scanning previous rows");
        System.out.println("     - No extra data structures for tracking");
        System.out.println("   Pros:");
        System.out.println("     - Simplest to implement");
        System.out.println("     - No extra memory for tracking");
        System.out.println("   Cons:");
        System.out.println("     - Slowest due to repeated scanning");
        System.out.println("   Best for: Learning, small n");
        
        System.out.println("\n4. Lookup Table:");
        System.out.println("   Time: O(1) - Constant time lookup");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   How it works:");
        System.out.println("     - Precomputed solutions for n = 1 to 9");
        System.out.println("     - Direct array lookup");
        System.out.println("   Pros:");
        System.out.println("     - Instant results");
        System.out.println("     - Zero computation");
        System.out.println("   Cons:");
        System.out.println("     - Only works for n ≤ 9");
        System.out.println("     - Doesn't solve the actual problem");
        System.out.println("   Best for: Production code with n ≤ 9");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE CHARACTERISTICS:");
        System.out.println("1. Bit Manipulation: Fastest, most memory efficient");
        System.out.println("2. Boolean Arrays:   Fast, easy to understand");
        System.out.println("3. Set Tracking:     Moderate, very readable");
        System.out.println("4. Direct Validation: Slowest, simplest");
        System.out.println("5. Lookup Table:     Instant, but limited");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SOLUTION COUNTS FOR n = 1 TO 9:");
        System.out.println("=".repeat(70));
        
        for (int i = 1; i <= 9; i++) {
            int count = solution.totalNQueens(i);
            System.out.printf("n = %d: %d solutions%n", i, count);
        }
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Use bit manipulation for optimal performance");
        System.out.println("2. Explain bit operations clearly if asked");
        System.out.println("3. Have boolean arrays approach as backup");
        System.out.println("4. Mention lookup table for production (if n ≤ 9)");
        System.out.println("5. Discuss time/space complexity honestly");
        System.out.println("6. Handle edge cases: n=1, n=2, n=3");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
