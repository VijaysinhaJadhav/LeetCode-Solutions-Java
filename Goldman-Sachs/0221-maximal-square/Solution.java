
# Solution.java

```java
import java.util.*;

/**
 * 221. Maximal Square
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find the largest square submatrix of 1's in a binary matrix.
 * 
 * Key Insights:
 * 1. dp[i][j] = side length of largest square ending at (i, j)
 * 2. Transition: dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
 * 3. Track maximum side length
 * 4. Area = max_side²
 */
class Solution {
    
    /**
     * Approach 1: 2D DP Array (Recommended)
     * Time: O(m × n), Space: O(m × n)
     * 
     * Steps:
     * 1. Create dp array with same dimensions
     * 2. For first row/col, dp[i][j] = 1 if matrix[i][j] == '1'
     * 3. For other cells, if matrix[i][j] == '1':
     *    dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
     * 4. Track max_side
     * 5. Return max_side * max_side
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int maxSide = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        
        return maxSide * maxSide;
    }
    
    /**
     * Approach 2: Space Optimized DP (1D Array)
     * Time: O(m × n), Space: O(n)
     * 
     * Only need previous row and current row
     */
    public int maximalSquareOptimized(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        
        int m = matrix.length;
        int n = matrix[0].length;
        int[] dp = new int[n + 1];
        int maxSide = 0;
        int prev = 0; // dp[i-1][j-1]
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int temp = dp[j + 1]; // store for next iteration (becomes prev)
                
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[j + 1] = 1;
                    } else {
                        dp[j + 1] = Math.min(Math.min(dp[j], dp[j + 1]), prev) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[j + 1]);
                } else {
                    dp[j + 1] = 0;
                }
                
                prev = temp;
            }
        }
        
        return maxSide * maxSide;
    }
    
    /**
     * Approach 3: In-place DP (Modify Original Matrix)
     * Time: O(m × n), Space: O(1)
     * 
     * Use original matrix as DP array (modifies input)
     */
    public int maximalSquareInPlace(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        
        int m = matrix.length;
        int n = matrix[0].length;
        int maxSide = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        matrix[i][j] = '1';
                    } else {
                        int min = Math.min(matrix[i-1][j] - '0', 
                                          Math.min(matrix[i][j-1] - '0', 
                                                   matrix[i-1][j-1] - '0'));
                        matrix[i][j] = (char) (min + 1 + '0');
                    }
                    maxSide = Math.max(maxSide, matrix[i][j] - '0');
                }
            }
        }
        
        return maxSide * maxSide;
    }
    
    /**
     * Approach 4: Brute Force (for small matrices)
     * Time: O(m² × n²), Space: O(1)
     * 
     * Check all possible squares
     */
    public int maximalSquareBruteForce(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        
        int m = matrix.length;
        int n = matrix[0].length;
        int maxSide = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    int side = 1;
                    boolean valid = true;
                    
                    while (i + side < m && j + side < n && valid) {
                        // Check the new row and column
                        for (int k = 0; k <= side; k++) {
                            if (matrix[i + side][j + k] == '0' || matrix[i + k][j + side] == '0') {
                                valid = false;
                                break;
                            }
                        }
                        if (valid) side++;
                    }
                    maxSide = Math.max(maxSide, side);
                }
            }
        }
        
        return maxSide * maxSide;
    }
    
    /**
     * Approach 5: Using Prefix Sum + Binary Search
     * Time: O(m × n × log(min(m,n))), Space: O(m × n)
     * 
     * Precompute prefix sums, then binary search for max square size
     */
    public int maximalSquarePrefixSum(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Convert to int matrix
        int[][] intMatrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                intMatrix[i][j] = matrix[i][j] - '0';
            }
        }
        
        // Build prefix sum
        int[][] prefix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = intMatrix[i - 1][j - 1] 
                             + prefix[i - 1][j] 
                             + prefix[i][j - 1] 
                             - prefix[i - 1][j - 1];
            }
        }
        
        // Binary search on square size
        int left = 0;
        int right = Math.min(m, n);
        int maxSide = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canFormSquare(prefix, mid, m, n)) {
                maxSide = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return maxSide * maxSide;
    }
    
    private boolean canFormSquare(int[][] prefix, int size, int m, int n) {
        if (size == 0) return true;
        
        for (int i = size; i <= m; i++) {
            for (int j = size; j <= n; j++) {
                int sum = prefix[i][j] - prefix[i - size][j] 
                        - prefix[i][j - size] + prefix[i - size][j - size];
                if (sum == size * size) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Helper: Visualize DP process
     */
    public void visualizeDP(char[][] matrix) {
        System.out.println("\nMaximal Square Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nOriginal Matrix:");
        for (char[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int maxSide = 0;
        
        System.out.println("\nDP Table (side lengths):");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        
        for (int i = 0; i < m; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        
        System.out.println("\nLargest square side length: " + maxSide);
        System.out.println("Area: " + (maxSide * maxSide));
    }
    
    /**
     * Helper: Generate test cases
     */
    public char[][][] generateTestCases() {
        return new char[][][] {
            {
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}
            },
            {
                {'0','1'},
                {'1','0'}
            },
            {{'0'}},
            {{'1'}},
            {
                {'1','1','1','1'},
                {'1','1','1','1'},
                {'1','1','1','1'}
            },
            {
                {'1','0','1'},
                {'0','1','0'},
                {'1','0','1'}
            },
            {
                {'1','1','0','1'},
                {'1','1','0','1'},
                {'1','1','1','1'}
            }
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        char[][][] testCases = generateTestCases();
        int[] expected = {4, 1, 0, 1, 16, 1, 4};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            char[][] matrix = testCases[i];
            System.out.printf("\nTest %d: %dx%d matrix%n", i + 1, matrix.length, matrix[0].length);
            
            int result1 = maximalSquare(matrix.clone());
            int result2 = maximalSquareOptimized(matrix.clone());
            int result3 = maximalSquareInPlace(matrix.clone());
            int result4 = maximalSquareBruteForce(matrix.clone());
            int result5 = maximalSquarePrefixSum(matrix.clone());
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Max area: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeDP(matrix);
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
        
        int m = 300;
        int n = 300;
        char[][] matrix = new char[m][n];
        Random rand = new Random(42);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rand.nextBoolean() ? '1' : '0';
            }
        }
        
        System.out.println("Test Setup: " + m + "×" + n + " matrix");
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: 2D DP
        char[][] m1 = copyMatrix(matrix);
        long start = System.currentTimeMillis();
        results[0] = maximalSquare(m1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Space Optimized
        char[][] m2 = copyMatrix(matrix);
        start = System.currentTimeMillis();
        results[1] = maximalSquareOptimized(m2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: In-place
        char[][] m3 = copyMatrix(matrix);
        start = System.currentTimeMillis();
        results[2] = maximalSquareInPlace(m3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Brute Force (skip for large matrix)
        times[3] = -1;
        results[3] = -1;
        
        // Method 5: Prefix Sum + Binary Search
        char[][] m5 = copyMatrix(matrix);
        start = System.currentTimeMillis();
        results[4] = maximalSquarePrefixSum(m5);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Area");
        System.out.println("--------------------------|-----------|------");
        System.out.printf("1. 2D DP                  | %9d | %5d%n", times[0], results[0]);
        System.out.printf("2. Space Optimized        | %9d | %5d%n", times[1], results[1]);
        System.out.printf("3. In-place               | %9d | %5d%n", times[2], results[2]);
        System.out.printf("4. Brute Force            | %9s | %5s%n", "N/A", "N/A");
        System.out.printf("5. Prefix Sum + Binary    | %9d | %5d%n", times[4], results[4]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[4];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Space optimized DP is slightly faster");
        System.out.println("2. In-place DP uses O(1) extra space");
        System.out.println("3. Prefix sum + binary search has extra log factor");
        System.out.println("4. Brute force is too slow for 300×300");
    }
    
    private char[][] copyMatrix(char[][] matrix) {
        char[][] copy = new char[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single row matrix:");
        char[][] m1 = {{'1','1','1'}};
        System.out.println("   Input: [[1,1,1]]");
        System.out.println("   Output: " + maximalSquare(m1));
        
        System.out.println("\n2. Single column matrix:");
        char[][] m2 = {{'1'},{'1'},{'1'}};
        System.out.println("   Input: [[1],[1],[1]]");
        System.out.println("   Output: " + maximalSquare(m2));
        
        System.out.println("\n3. All zeros:");
        char[][] m3 = {{'0','0'},{'0','0'}};
        System.out.println("   Input: [[0,0],[0,0]]");
        System.out.println("   Output: " + maximalSquare(m3));
        
        System.out.println("\n4. All ones:");
        char[][] m4 = {{'1','1'},{'1','1'}};
        System.out.println("   Input: [[1,1],[1,1]]");
        System.out.println("   Output: " + maximalSquare(m4));
        
        System.out.println("\n5. Single cell:");
        char[][] m5 = {{'1'}};
        System.out.println("   Input: [[1]]");
        System.out.println("   Output: " + maximalSquare(m5));
        
        System.out.println("\n6. Large matrix with diagonal pattern:");
        char[][] m6 = new char[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                m6[i][j] = (i == j) ? '1' : '0';
            }
        }
        long start = System.currentTimeMillis();
        int result = maximalSquare(m6);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 100×100 diagonal matrix");
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain DP recurrence
     */
    public void explainDP() {
        System.out.println("\nDP Recurrence Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nState Definition:");
        System.out.println("dp[i][j] = side length of the largest square ending at (i, j)");
        
        System.out.println("\nBase Cases:");
        System.out.println("  dp[0][j] = 1 if matrix[0][j] == '1'");
        System.out.println("  dp[i][0] = 1 if matrix[i][0] == '1'");
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("  if matrix[i][j] == '1':");
        System.out.println("    dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1");
        System.out.println("  else:");
        System.out.println("    dp[i][j] = 0");
        
        System.out.println("\nIntuition:");
        System.out.println("  - If (i,j) is the bottom-right corner of a square of size k,");
        System.out.println("    then (i-1,j), (i,j-1), and (i-1,j-1) must each be corners");
        System.out.println("    of squares of size at least k-1.");
        System.out.println("  - Taking the minimum ensures the square is contiguous.");
        
        System.out.println("\nExample:");
        System.out.println("  Matrix:");
        System.out.println("    [1, 1]");
        System.out.println("    [1, 1]");
        System.out.println("  dp[1][1] = min(dp[0][1], dp[1][0], dp[0][0]) + 1");
        System.out.println("           = min(1, 1, 1) + 1 = 2");
        System.out.println("  Max side = 2, area = 4");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What counts as a square? (All cells must be '1')");
        System.out.println("   - What's the output? (Area, not side length)");
        System.out.println("   - Can the matrix be empty? (No, m,n ≥ 1)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Check all possible squares O(m²×n²)");
        System.out.println("   - Acknowledge it's too slow for constraints");
        
        System.out.println("\n3. Propose DP solution:");
        System.out.println("   - State: dp[i][j] = side length of largest square ending at (i,j)");
        System.out.println("   - Transition: min of three neighbors + 1");
        System.out.println("   - Track max side length");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - Time: O(m × n)");
        System.out.println("   - Space: O(m × n) or O(n) with optimization");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - Single row/column matrix");
        System.out.println("   - All zeros");
        System.out.println("   - All ones");
        System.out.println("   - Matrix with 1's on diagonal only");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Forgetting to convert char to int");
        System.out.println("   - Off-by-one in DP indices");
        System.out.println("   - Not handling first row/col correctly");
        System.out.println("   - Returning side length instead of area");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("221. Maximal Square");
        System.out.println("===================");
        
        // Explain DP recurrence
        solution.explainDP();
        
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
        
        System.out.println("\nRecommended Implementation (Space Optimized):");
        System.out.println("""
class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        
        int m = matrix.length;
        int n = matrix[0].length;
        int[] dp = new int[n + 1];
        int maxSide = 0;
        int prev = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int temp = dp[j + 1];
                
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[j + 1] = 1;
                    } else {
                        dp[j + 1] = Math.min(Math.min(dp[j], dp[j + 1]), prev) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[j + 1]);
                } else {
                    dp[j + 1] = 0;
                }
                
                prev = temp;
            }
        }
        
        return maxSide * maxSide;
    }
}
            """);
        
        System.out.println("\nAlternative (2D DP for clarity):");
        System.out.println("""
class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int maxSide = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        
        return maxSide * maxSide;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. dp[i][j] represents side length of largest square ending at (i,j)");
        System.out.println("2. Transition: min of three neighbors + 1");
        System.out.println("3. Space can be optimized to O(n) using 1D array");
        System.out.println("4. Time complexity: O(m × n)");
        System.out.println("5. Return area = max_side², not side length");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(m × n) - single pass through matrix");
        System.out.println("- Space: O(n) with optimization, O(m × n) for full DP");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you find the maximal rectangle?");
        System.out.println("2. How would you count all square submatrices?");
        System.out.println("3. What if the matrix is too large to fit in memory?");
        System.out.println("4. How would you handle '0' and '1' as integers instead of chars?");
        System.out.println("5. How would you modify for 3D cubes?");
    }
}
