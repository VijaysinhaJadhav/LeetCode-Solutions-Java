/**
 * 304. Range Sum Query 2D - Immutable
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a 2D matrix, handle multiple queries to calculate the sum of elements 
 * inside a rectangle defined by its upper left and lower right corners.
 * 
 * Key Insights:
 * 1. 2D Prefix Sum: Precompute cumulative sums to answer queries in O(1) time
 * 2. Inclusion-Exclusion: Use the principle to compute rectangle sums efficiently
 * 3. DP Relation: dp[i][j] = matrix[i-1][j-1] + dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1]
 * 4. Query Formula: sum = dp[r2+1][c2+1] - dp[r1][c2+1] - dp[r2+1][c1] + dp[r1][c1]
 * 
 * Approach:
 * 1. Precompute a prefix sum matrix with dimensions (m+1) x (n+1)
 * 2. Each cell dp[i][j] represents sum of rectangle from (0,0) to (i-1,j-1)
 * 3. For queries, use inclusion-exclusion to compute the required sum
 * 
 * Time Complexity: O(m*n) precomputation, O(1) per query
 * Space Complexity: O(m*n) for prefix sum matrix
 * 
 * Tags: Array, Design, Matrix, Prefix Sum
 */

import java.util.Random;

class NumMatrix {
    private int[][] dp; // Prefix sum matrix

    /**
     * Approach 1: Standard 2D Prefix Sum (RECOMMENDED)
     * Optimal for multiple queries
     */
    public NumMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            dp = new int[0][0];
            return;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        dp = new int[m + 1][n + 1];
        
        // Build prefix sum matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = matrix[i - 1][j - 1] + dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        // Use inclusion-exclusion principle
        return dp[row2 + 1][col2 + 1] - dp[row1][col2 + 1] - dp[row2 + 1][col1] + dp[row1][col1];
    }
}

/**
 * Alternative Implementation 2: Row-wise Prefix Sum
 * O(m) per query, O(m*n) space - Good when m is small
 */
class NumMatrixRowWise {
    private int[][] rowSums; // rowSums[i][j] = sum of row i from col 0 to j-1

    public NumMatrixRowWise(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            rowSums = new int[0][0];
            return;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        rowSums = new int[m][n + 1];
        
        // Build row-wise prefix sums
        for (int i = 0; i < m; i++) {
            for (int j = 1; j <= n; j++) {
                rowSums[i][j] = rowSums[i][j - 1] + matrix[i][j - 1];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int sum = 0;
        for (int i = row1; i <= row2; i++) {
            sum += rowSums[i][col2 + 1] - rowSums[i][col1];
        }
        return sum;
    }
}

/**
 * Alternative Implementation 3: Column-wise Prefix Sum
 * O(n) per query, O(m*n) space - Good when n is small
 */
class NumMatrixColWise {
    private int[][] colSums; // colSums[i][j] = sum of col j from row 0 to i-1

    public NumMatrixColWise(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            colSums = new int[0][0];
            return;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        colSums = new int[m + 1][n];
        
        // Build column-wise prefix sums
        for (int j = 0; j < n; j++) {
            for (int i = 1; i <= m; i++) {
                colSums[i][j] = colSums[i - 1][j] + matrix[i - 1][j];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int sum = 0;
        for (int j = col1; j <= col2; j++) {
            sum += colSums[row2 + 1][j] - colSums[row1][j];
        }
        return sum;
    }
}

/**
 * Alternative Implementation 4: Brute Force (for comparison)
 * O(m*n) per query, O(1) space - Only for small matrices or few queries
 */
class NumMatrixBruteForce {
    private int[][] matrix;

    public NumMatrixBruteForce(int[][] matrix) {
        this.matrix = matrix;
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int sum = 0;
        for (int i = row1; i <= row2; i++) {
            for (int j = col1; j <= col2; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
}

/**
 * Test class to verify all implementations
 */
public class Solution {
    /**
     * Helper method to print matrix
     */
    private static void printMatrix(int[][] matrix, String label) {
        System.out.println(label + ":");
        if (matrix == null || matrix.length == 0) {
            System.out.println("  Empty matrix");
            return;
        }
        for (int[] row : matrix) {
            System.out.print("  [");
            for (int j = 0; j < row.length; j++) {
                System.out.printf("%3d", row[j]);
                if (j < row.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
    
    /**
     * Helper method to print prefix sum matrix
     */
    private static void printPrefixMatrix(int[][] dp, String label) {
        System.out.println(label + ":");
        if (dp == null || dp.length == 0) {
            System.out.println("  Empty matrix");
            return;
        }
        for (int i = 0; i < dp.length; i++) {
            System.out.print("  [");
            for (int j = 0; j < dp[0].length; j++) {
                System.out.printf("%3d", dp[i][j]);
                if (j < dp[0].length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
    
    /**
     * Helper method to verify results
     */
    private static void verifyTest(String testName, int expected, int actual, boolean showDetails) {
        boolean passed = (expected == actual);
        System.out.println(testName + ": " + (passed ? "PASSED" : "FAILED"));
        if (!passed || showDetails) {
            System.out.println("  Expected: " + expected + ", Actual: " + actual);
        }
    }
    
    /**
     * Performance test helper
     */
    private static void printPerformanceResult(String approach, long constructionTime, long queryTime, int queryCount) {
        System.out.println("  " + approach + ":");
        System.out.println("    Construction: " + constructionTime + " ns");
        System.out.println("    Query (avg):  " + (queryTime / queryCount) + " ns");
        System.out.println("    Total:        " + (constructionTime + queryTime) + " ns");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        System.out.println("Testing Range Sum Query 2D - Immutable Solution:");
        System.out.println("=================================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Standard example");
        int[][] matrix1 = {
            {3, 0, 1, 4, 2},
            {5, 6, 3, 2, 1},
            {1, 2, 0, 1, 5},
            {4, 1, 0, 1, 7},
            {1, 0, 3, 0, 5}
        };
        
        printMatrix(matrix1, "Original Matrix");
        
        // Test all implementations
        long startTime = System.nanoTime();
        NumMatrix numMatrix1 = new NumMatrix(matrix1);
        long constTime1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        NumMatrixRowWise numMatrixRow1 = new NumMatrixRowWise(matrix1);
        long constTime2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        NumMatrixColWise numMatrixCol1 = new NumMatrixColWise(matrix1);
        long constTime3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        NumMatrixBruteForce numMatrixBrute1 = new NumMatrixBruteForce(matrix1);
        long constTime4 = System.nanoTime() - startTime;
        
        // Test queries
        int[][] queries1 = {
            {2, 1, 4, 3}, // Expected: 8
            {1, 1, 2, 2}, // Expected: 11
            {1, 2, 2, 4}  // Expected: 12
        };
        int[] expected1 = {8, 11, 12};
        
        long queryTime1 = 0, queryTime2 = 0, queryTime3 = 0, queryTime4 = 0;
        
        System.out.println("\nQuery Results:");
        for (int i = 0; i < queries1.length; i++) {
            int[] query = queries1[i];
            int expected = expected1[i];
            
            startTime = System.nanoTime();
            int result1 = numMatrix1.sumRegion(query[0], query[1], query[2], query[3]);
            queryTime1 += System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            int result2 = numMatrixRow1.sumRegion(query[0], query[1], query[2], query[3]);
            queryTime2 += System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            int result3 = numMatrixCol1.sumRegion(query[0], query[1], query[2], query[3]);
            queryTime3 += System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            int result4 = numMatrixBrute1.sumRegion(query[0], query[1], query[2], query[3]);
            queryTime4 += System.nanoTime() - startTime;
            
            System.out.println("Query " + (i + 1) + " [" + query[0] + "," + query[1] + "] to [" + 
                             query[2] + "," + query[3] + "]:");
            verifyTest("  2D Prefix Sum", expected, result1, true);
            verifyTest("  Row-wise", expected, result2, false);
            verifyTest("  Column-wise", expected, result3, false);
            verifyTest("  Brute Force", expected, result4, false);
        }
        
        // Test case 2: Single element matrix
        System.out.println("\nTest 2: Single element matrix");
        int[][] matrix2 = {{5}};
        NumMatrix numMatrix2 = new NumMatrix(matrix2);
        int result2 = numMatrix2.sumRegion(0, 0, 0, 0);
        verifyTest("Single element", 5, result2, true);
        
        // Test case 3: Single row matrix
        System.out.println("\nTest 3: Single row matrix");
        int[][] matrix3 = {{1, 2, 3, 4, 5}};
        NumMatrix numMatrix3 = new NumMatrix(matrix3);
        int result3 = numMatrix3.sumRegion(0, 1, 0, 3);
        verifyTest("Single row", 9, result3, true);
        
        // Test case 4: Single column matrix
        System.out.println("\nTest 4: Single column matrix");
        int[][] matrix4 = {{1}, {2}, {3}, {4}, {5}};
        NumMatrix numMatrix4 = new NumMatrix(matrix4);
        int result4 = numMatrix4.sumRegion(1, 0, 3, 0);
        verifyTest("Single column", 9, result4, true);
        
        // Test case 5: Empty region (should be 0)
        System.out.println("\nTest 5: Empty region (same point)");
        int[][] matrix5 = {{1, 2}, {3, 4}};
        NumMatrix numMatrix5 = new NumMatrix(matrix5);
        int result5 = numMatrix5.sumRegion(1, 1, 1, 1);
        verifyTest("Single point", 4, result5, true);
        
        // Test case 6: Entire matrix
        System.out.println("\nTest 6: Entire matrix");
        int[][] matrix6 = {{1, 2}, {3, 4}};
        NumMatrix numMatrix6 = new NumMatrix(matrix6);
        int result6 = numMatrix6.sumRegion(0, 0, 1, 1);
        verifyTest("Entire matrix", 10, result6, true);
        
        // Test case 7: Negative numbers
        System.out.println("\nTest 7: Negative numbers");
        int[][] matrix7 = {{-1, 2}, {3, -4}};
        NumMatrix numMatrix7 = new NumMatrix(matrix7);
        int result7 = numMatrix7.sumRegion(0, 0, 1, 1);
        verifyTest("Negative numbers", 0, result7, true);
        
        // Performance comparison for large matrix
        System.out.println("\nTest 8: Performance comparison (large matrix)");
        int size = 200; // Maximum constraint size
        int[][] largeMatrix = new int[size][size];
        Random random = new Random(42);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                largeMatrix[i][j] = random.nextInt(20001) - 10000; // -10^4 to 10^4
            }
        }
        
        // Generate random queries
        int queryCount = 1000;
        int[][] largeQueries = new int[queryCount][4];
        for (int i = 0; i < queryCount; i++) {
            int row1 = random.nextInt(size);
            int col1 = random.nextInt(size);
            int row2 = random.nextInt(size - row1) + row1;
            int col2 = random.nextInt(size - col1) + col1;
            largeQueries[i] = new int[]{row1, col1, row2, col2};
        }
        
        // Test 2D Prefix Sum
        startTime = System.nanoTime();
        NumMatrix numMatrixLarge = new NumMatrix(largeMatrix);
        long constTimeLarge1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int[] query : largeQueries) {
            numMatrixLarge.sumRegion(query[0], query[1], query[2], query[3]);
        }
        long queryTimeLarge1 = System.nanoTime() - startTime;
        
        // Test Row-wise
        startTime = System.nanoTime();
        NumMatrixRowWise numMatrixRowLarge = new NumMatrixRowWise(largeMatrix);
        long constTimeLarge2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int[] query : largeQueries) {
            numMatrixRowLarge.sumRegion(query[0], query[1], query[2], query[3]);
        }
        long queryTimeLarge2 = System.nanoTime() - startTime;
        
        // Test Column-wise
        startTime = System.nanoTime();
        NumMatrixColWise numMatrixColLarge = new NumMatrixColWise(largeMatrix);
        long constTimeLarge3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int[] query : largeQueries) {
            numMatrixColLarge.sumRegion(query[0], query[1], query[2], query[3]);
        }
        long queryTimeLarge3 = System.nanoTime() - startTime;
        
        // Test Brute Force (only for small subset due to performance)
        int smallQueryCount = 10;
        startTime = System.nanoTime();
        NumMatrixBruteForce numMatrixBruteLarge = new NumMatrixBruteForce(largeMatrix);
        long constTimeLarge4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int i = 0; i < smallQueryCount; i++) {
            int[] query = largeQueries[i];
            numMatrixBruteLarge.sumRegion(query[0], query[1], query[2], query[3]);
        }
        long queryTimeLarge4 = System.nanoTime() - startTime;
        
        System.out.println("Performance for " + size + "x" + size + " matrix with " + queryCount + " queries:");
        printPerformanceResult("2D Prefix Sum", constTimeLarge1, queryTimeLarge1, queryCount);
        printPerformanceResult("Row-wise", constTimeLarge2, queryTimeLarge2, queryCount);
        printPerformanceResult("Column-wise", constTimeLarge3, queryTimeLarge3, queryCount);
        printPerformanceResult("Brute Force*", constTimeLarge4, queryTimeLarge4 * (queryCount / smallQueryCount), queryCount);
        System.out.println("  *Brute Force extrapolated from " + smallQueryCount + " queries");
        
        // Verify all approaches give same results
        boolean allConsistent = true;
        for (int i = 0; i < Math.min(10, queryCount); i++) {
            int[] query = largeQueries[i];
            int resultA = numMatrixLarge.sumRegion(query[0], query[1], query[2], query[3]);
            int resultB = numMatrixRowLarge.sumRegion(query[0], query[1], query[2], query[3]);
            int resultC = numMatrixColLarge.sumRegion(query[0], query[1], query[2], query[3]);
            int resultD = numMatrixBruteLarge.sumRegion(query[0], query[1], query[2], query[3]);
            
            if (resultA != resultB || resultA != resultC || resultA != resultD) {
                allConsistent = false;
                break;
            }
        }
        System.out.println("All approaches consistent: " + allConsistent);
        
        // Algorithm explanation and visualization
        System.out.println("\n" + "=".repeat(80));
        System.out.println("2D PREFIX SUM ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        int[][] demoMatrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println("Demo Matrix:");
        printMatrix(demoMatrix, "Original");
        
        NumMatrix demoNumMatrix = new NumMatrix(demoMatrix);
        System.out.println("\nPrefix Sum Matrix (dp):");
        System.out.println("dp[i][j] = sum of rectangle from (0,0) to (i-1,j-1)");
        System.out.println("Construction formula:");
        System.out.println("  dp[i][j] = matrix[i-1][j-1] + dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1]");
        
        System.out.println("\nQuery Formula (sumRegion(row1, col1, row2, col2)):");
        System.out.println("  sum = dp[row2+1][col2+1] - dp[row1][col2+1] - dp[row2+1][col1] + dp[row1][col1]");
        
        // Demonstrate a query
        int demoRow1 = 1, demoCol1 = 1, demoRow2 = 2, demoCol2 = 2;
        int demoResult = demoNumMatrix.sumRegion(demoRow1, demoCol1, demoRow2, demoCol2);
        System.out.println("\nExample Query: sumRegion(" + demoRow1 + ", " + demoCol1 + ", " + 
                         demoRow2 + ", " + demoCol2 + ")");
        System.out.println("Region: [" + demoRow1 + "," + demoCol1 + "] to [" + demoRow2 + "," + demoCol2 + "]");
        System.out.println("Expected sum: 5 + 6 + 8 + 9 = 28");
        System.out.println("Actual result: " + demoResult);
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 2D Prefix Sum (RECOMMENDED):");
        System.out.println("   Construction: O(m*n) time, O(m*n) space");
        System.out.println("   Query: O(1) time");
        System.out.println("   Pros:");
        System.out.println("     - Optimal for multiple queries");
        System.out.println("     - Simple and elegant mathematical formulation");
        System.out.println("     - Well-established pattern");
        System.out.println("   Cons:");
        System.out.println("     - Requires O(m*n) extra space");
        System.out.println("     - Slightly more complex construction");
        System.out.println("   Best for: Most cases, especially with many queries");
        
        System.out.println("\n2. Row-wise Prefix Sum:");
        System.out.println("   Construction: O(m*n) time, O(m*n) space");
        System.out.println("   Query: O(m) time");
        System.out.println("   Pros:");
        System.out.println("     - Simpler construction logic");
        System.out.println("     - Good when m is small");
        System.out.println("   Cons:");
        System.out.println("     - O(m) per query can be slow for large m");
        System.out.println("   Best for: When matrix has few rows");
        
        System.out.println("\n3. Column-wise Prefix Sum:");
        System.out.println("   Construction: O(m*n) time, O(m*n) space");
        System.out.println("   Query: O(n) time");
        System.out.println("   Pros:");
        System.out.println("     - Simpler construction logic");
        System.out.println("     - Good when n is small");
        System.out.println("   Cons:");
        System.out.println("     - O(n) per query can be slow for large n");
        System.out.println("   Best for: When matrix has few columns");
        
        System.out.println("\n4. Brute Force:");
        System.out.println("   Construction: O(1) time, O(1) space");
        System.out.println("   Query: O(m*n) time");
        System.out.println("   Pros:");
        System.out.println("     - No extra space required");
        System.out.println("     - Simple to implement");
        System.out.println("   Cons:");
        System.out.println("     - Impractical for large matrices or many queries");
        System.out.println("   Best for: Small matrices or very few queries");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL INSIGHT:");
        System.out.println("The 2D prefix sum uses the inclusion-exclusion principle:");
        System.out.println("sum(ABCD) = sum(OD) - sum(OB) - sum(OC) + sum(OA)");
        System.out.println("Where:");
        System.out.println("  A = region from (0,0) to (r1-1,c1-1)");
        System.out.println("  B = region from (0,0) to (r1-1,c2)");
        System.out.println("  C = region from (0,0) to (r2,c1-1)");
        System.out.println("  D = region from (0,0) to (r2,c2)");
        System.out.println("This ensures each subregion is counted exactly once.");
        System.out.println("=".repeat(80));
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with 2D Prefix Sum - it's the expected optimal solution");
        System.out.println("2. Explain the inclusion-exclusion principle clearly");
        System.out.println("3. Draw the matrix and regions to visualize the formula");
        System.out.println("4. Mention alternative approaches and their trade-offs");
        System.out.println("5. Handle edge cases: empty matrix, single element, entire matrix");
        System.out.println("6. Practice the construction and query formulas until they're intuitive");
        System.out.println("=".repeat(80));
        
        System.out.println("\nAll tests completed!");
    }
}
