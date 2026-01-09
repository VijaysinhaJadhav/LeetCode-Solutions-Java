
# Solution.java

```java
import java.util.*;

/**
 * 73. Set Matrix Zeroes
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given m x n matrix, if element is 0, set entire row and column to 0.
 * Must do it in place with O(1) space.
 * 
 * Key Insights:
 * 1. Cannot modify while scanning (lose information)
 * 2. Use first row and column as markers
 * 3. Handle first row/column separately
 * 4. Process from bottom-right to avoid overwriting markers
 * 
 * Approach 1: O(1) Space using First Row/Column (RECOMMENDED)
 * O(m*n) time, O(1) space
 */

class Solution {
    
    /**
     * Approach 1: O(1) Space using First Row/Column (RECOMMENDED)
     * Time: O(m*n), Space: O(1)
     * Uses first row and first column as markers
     */
    public void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Flags for first row and first column
        boolean firstRowZero = false;
        boolean firstColZero = false;
        
        // Check if first row has zero
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }
        
        // Check if first column has zero
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }
        
        // Use first row and column as markers
        // For cells (i,j) where i>0 and j>0, if matrix[i][j] == 0,
        // mark corresponding position in first row and first column
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0; // Mark row in first column
                    matrix[0][j] = 0; // Mark column in first row
                }
            }
        }
        
        // Set zeros for rows (except first row)
        for (int i = 1; i < m; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < n; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // Set zeros for columns (except first column)
        for (int j = 1; j < n; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < m; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // Handle first row if needed
        if (firstRowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        
        // Handle first column if needed
        if (firstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
    
    /**
     * Approach 2: O(m+n) Space Solution
     * Time: O(m*n), Space: O(m+n)
     * Uses separate arrays to track zero rows and columns
     */
    public void setZeroesWithExtraSpace(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        boolean[] zeroRows = new boolean[m];
        boolean[] zeroCols = new boolean[n];
        
        // First pass: mark which rows and columns have zeros
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    zeroRows[i] = true;
                    zeroCols[j] = true;
                }
            }
        }
        
        // Second pass: set zeros
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (zeroRows[i] || zeroCols[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
    
    /**
     * Approach 3: Brute Force with O(m*n) Space
     * Time: O(m*n), Space: O(m*n)
     * Makes a copy of the matrix (not in-place)
     */
    public void setZeroesBruteForce(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Create copy
        int[][] copy = new int[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, n);
        }
        
        // Scan copy and modify original
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (copy[i][j] == 0) {
                    // Set entire row to zero
                    for (int col = 0; col < n; col++) {
                        matrix[i][col] = 0;
                    }
                    // Set entire column to zero
                    for (int row = 0; row < m; row++) {
                        matrix[row][j] = 0;
                    }
                }
            }
        }
    }
    
    /**
     * Approach 4: Bit Manipulation (for small m,n)
     * Time: O(m*n), Space: O(1) but limited by bit length
     * Uses long bitmask for rows/columns (works for up to 64)
     */
    public void setZeroesBitMask(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // For m,n <= 64, we can use bitmask
        if (m > 64 || n > 64) {
            setZeroes(matrix); // Fall back to regular method
            return;
        }
        
        long rowMask = 0;
        long colMask = 0;
        
        // Mark rows and columns that need to be zeroed
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    rowMask |= (1L << i);
                    colMask |= (1L << j);
                }
            }
        }
        
        // Set rows to zero
        for (int i = 0; i < m; i++) {
            if ((rowMask & (1L << i)) != 0) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // Set columns to zero
        for (int j = 0; j < n; j++) {
            if ((colMask & (1L << j)) != 0) {
                for (int i = 0; i < m; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
    
    /**
     * Approach 5: Optimized Marker with Special Value
     * Time: O(m*n), Space: O(1)
     * Uses special value to mark cells (risky with integer range)
     */
    public void setZeroesSpecialMarker(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Use a special marker value not in matrix
        // This is risky because we don't know the value range
        final int MARKER = Integer.MIN_VALUE;
        
        // First pass: mark zeros
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    // Mark entire row (except zeros)
                    for (int col = 0; col < n; col++) {
                        if (matrix[i][col] != 0) {
                            matrix[i][col] = MARKER;
                        }
                    }
                    // Mark entire column (except zeros)
                    for (int row = 0; row < m; row++) {
                        if (matrix[row][j] != 0) {
                            matrix[row][j] = MARKER;
                        }
                    }
                }
            }
        }
        
        // Second pass: convert markers to zeros
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == MARKER) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
    
    /**
     * Approach 6: Two-Pass with Reverse Processing
     * Time: O(m*n), Space: O(1)
     * Processes from bottom-right to top-left
     */
    public void setZeroesReverse(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        boolean firstRowZero = false;
        boolean firstColZero = false;
        
        // Check first row and column
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }
        
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }
        
        // Mark zeros on first row and column
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        
        // Process from bottom-right to avoid overwriting markers
        for (int i = m - 1; i >= 1; i--) {
            for (int j = n - 1; j >= 1; j--) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // Handle first row and column
        if (firstRowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        
        if (firstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
    
    /**
     * Helper: Print matrix
     */
    public void printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            System.out.println("Empty matrix");
            return;
        }
        
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.printf("%3d ", num);
            }
            System.out.println();
        }
    }
    
    /**
     * Helper: Visualize the algorithm step by step
     */
    public void visualizeSetZeroes(int[][] matrix) {
        System.out.println("\nSet Matrix Zeroes Visualization:");
        System.out.println("Original Matrix " + matrix.length + "x" + matrix[0].length + ":");
        printMatrix(matrix);
        
        // Make a copy for visualization
        int[][] matrixCopy = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, matrixCopy[i], 0, matrix[i].length);
        }
        
        int m = matrixCopy.length;
        int n = matrixCopy[0].length;
        
        System.out.println("\nStep 1: Check first row and column for zeros");
        boolean firstRowZero = false;
        boolean firstColZero = false;
        
        for (int j = 0; j < n; j++) {
            if (matrixCopy[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }
        
        for (int i = 0; i < m; i++) {
            if (matrixCopy[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }
        
        System.out.println("First row has zero: " + firstRowZero);
        System.out.println("First column has zero: " + firstColZero);
        
        System.out.println("\nStep 2: Use first row and column as markers");
        System.out.println("For cells (i,j) where i>0 and j>0:");
        System.out.println("If matrix[i][j] == 0, set matrix[i][0] = 0 and matrix[0][j] = 0");
        
        int[][] markers = new int[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(matrixCopy[i], 0, markers[i], 0, n);
        }
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrixCopy[i][j] == 0) {
                    markers[i][0] = -1; // Mark row
                    markers[0][j] = -1; // Mark column
                }
            }
        }
        
        System.out.println("Matrix with markers (marked with -1):");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (markers[i][j] == -1) {
                    System.out.print("  M ");
                } else {
                    System.out.printf("%3d ", markers[i][j]);
                }
            }
            System.out.println();
        }
        
        System.out.println("\nStep 3: Set zeros based on markers (rows)");
        for (int i = 1; i < m; i++) {
            if (markers[i][0] == -1) {
                System.out.println("Row " + i + " needs to be zeroed");
                for (int j = 1; j < n; j++) {
                    matrixCopy[i][j] = 0;
                }
            }
        }
        
        System.out.println("\nStep 4: Set zeros based on markers (columns)");
        for (int j = 1; j < n; j++) {
            if (markers[0][j] == -1) {
                System.out.println("Column " + j + " needs to be zeroed");
                for (int i = 1; i < m; i++) {
                    matrixCopy[i][j] = 0;
                }
            }
        }
        
        System.out.println("\nStep 5: Handle first row if needed");
        if (firstRowZero) {
            System.out.println("First row needs to be zeroed");
            for (int j = 0; j < n; j++) {
                matrixCopy[0][j] = 0;
            }
        }
        
        System.out.println("\nStep 6: Handle first column if needed");
        if (firstColZero) {
            System.out.println("First column needs to be zeroed");
            for (int i = 0; i < m; i++) {
                matrixCopy[i][0] = 0;
            }
        }
        
        System.out.println("\nFinal Matrix:");
        printMatrix(matrixCopy);
        
        // Apply actual algorithm to original
        setZeroes(matrix);
        System.out.println("\nAlgorithm result on original:");
        printMatrix(matrix);
    }
    
    /**
     * Helper: Explain the O(1) space algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALGORITHM EXPLANATION (O(1) Space Solution):");
        System.out.println("=".repeat(80));
        
        System.out.println("\nProblem Constraints:");
        System.out.println("- Must modify matrix in place");
        System.out.println("- Should use O(1) extra space (follow-up requirement)");
        System.out.println("- Cannot use O(mn) or O(m+n) space");
        
        System.out.println("\nKey Insight:");
        System.out.println("Use the first row and first column as markers to store");
        System.out.println("which rows and columns need to be zeroed.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Check if first row has any zeros (store in firstRowZero)");
        System.out.println("2. Check if first column has any zeros (store in firstColZero)");
        
        System.out.println("\n3. For each cell (i,j) where i>0 and j>0:");
        System.out.println("   If matrix[i][j] == 0:");
        System.out.println("     - Set matrix[i][0] = 0 (mark row in first column)");
        System.out.println("     - Set matrix[0][j] = 0 (mark column in first row)");
        
        System.out.println("\n4. For each row i (1..m-1):");
        System.out.println("   If matrix[i][0] == 0, set entire row i to zero");
        
        System.out.println("\n5. For each column j (1..n-1):");
        System.out.println("   If matrix[0][j] == 0, set entire column j to zero");
        
        System.out.println("\n6. Handle first row:");
        System.out.println("   If firstRowZero is true, set entire first row to zero");
        
        System.out.println("\n7. Handle first column:");
        System.out.println("   If firstColZero is true, set entire first column to zero");
        
        System.out.println("\nWhy it works:");
        System.out.println("- First row/column store which rows/columns need zeros");
        System.out.println("- We check them first to avoid losing information");
        System.out.println("- We process inner matrix first, then handle first row/column");
        System.out.println("- This ensures we don't overwrite markers prematurely");
        
        System.out.println("\nExample: matrix = [[1,1,1],[1,0,1],[1,1,1]]");
        System.out.println("\nStep 1: firstRowZero = false, firstColZero = false");
        System.out.println("Step 2: matrix[1][1] = 0, so:");
        System.out.println("        matrix[1][0] = 0 (mark row 1)");
        System.out.println("        matrix[0][1] = 0 (mark column 1)");
        System.out.println("Step 3: Row 1 marked → zero row 1");
        System.out.println("Step 4: Column 1 marked → zero column 1");
        System.out.println("Result: [[1,0,1],[0,0,0],[1,0,1]]");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES TESTING:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. Single element matrix (0):");
        int[][] case1 = {{0}};
        System.out.println("   Original: [[0]]");
        solution.setZeroes(case1);
        System.out.println("   Result: " + Arrays.deepToString(case1) + " (should be [[0]])");
        
        System.out.println("\n2. Single element matrix (non-zero):");
        int[][] case2 = {{5}};
        System.out.println("   Original: [[5]]");
        solution.setZeroes(case2);
        System.out.println("   Result: " + Arrays.deepToString(case2) + " (should be [[5]])");
        
        System.out.println("\n3. Single row with zero:");
        int[][] case3 = {{1,0,2,3}};
        System.out.println("   Original: [[1,0,2,3]]");
        solution.setZeroes(case3);
        System.out.println("   Result: " + Arrays.deepToString(case3) + " (should be [[0,0,0,0]])");
        
        System.out.println("\n4. Single column with zero:");
        int[][] case4 = {{1},{0},{2},{3}};
        System.out.println("   Original: [[1],[0],[2],[3]]");
        solution.setZeroes(case4);
        System.out.println("   Result: " + Arrays.deepToString(case4) + " (should be [[0],[0],[0],[0]])");
        
        System.out.println("\n5. First row has zero:");
        int[][] case5 = {{0,1,2},{3,4,5},{6,7,8}};
        System.out.println("   Original: [[0,1,2],[3,4,5],[6,7,8]]");
        solution.setZeroes(case5);
        System.out.println("   Result: " + Arrays.deepToString(case5) + 
                         " (should be [[0,0,0],[0,4,5],[0,7,8]])");
        
        System.out.println("\n6. First column has zero:");
        int[][] case6 = {{1,2,3},{0,4,5},{6,7,8}};
        System.out.println("   Original: [[1,2,3],[0,4,5],[6,7,8]]");
        solution.setZeroes(case6);
        System.out.println("   Result: " + Arrays.deepToString(case6) + 
                         " (should be [[0,2,3],[0,0,0],[0,7,8]])");
        
        System.out.println("\n7. Zero at (0,0):");
        int[][] case7 = {{0,1,1},{1,1,1},{1,1,1}};
        System.out.println("   Original: [[0,1,1],[1,1,1],[1,1,1]]");
        solution.setZeroes(case7);
        System.out.println("   Result: " + Arrays.deepToString(case7) + 
                         " (should be [[0,0,0],[0,1,1],[0,1,1]])");
        
        System.out.println("\n8. All zeros matrix:");
        int[][] case8 = {{0,0,0},{0,0,0},{0,0,0}};
        System.out.println("   Original: all zeros");
        solution.setZeroes(case8);
        System.out.println("   Result: all zeros (correct)");
        
        System.out.println("\n9. No zeros matrix:");
        int[][] case9 = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println("   Original: [[1,2,3],[4,5,6],[7,8,9]]");
        solution.setZeroes(case9);
        System.out.println("   Result: " + Arrays.deepToString(case9) + 
                         " (should be unchanged)");
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(int[][] matrix) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nOriginal Matrix " + matrix.length + "x" + matrix[0].length + ":");
        printMatrix(matrix);
        
        Solution solution = new Solution();
        
        // Create copies for each approach
        int[][] matrix1 = copyMatrix(matrix);
        int[][] matrix2 = copyMatrix(matrix);
        int[][] matrix3 = copyMatrix(matrix);
        int[][] matrix4 = copyMatrix(matrix);
        int[][] matrix5 = copyMatrix(matrix);
        int[][] matrix6 = copyMatrix(matrix);
        
        long startTime, endTime;
        
        // Approach 1: O(1) Space
        startTime = System.nanoTime();
        solution.setZeroes(matrix1);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: O(m+n) Space
        startTime = System.nanoTime();
        solution.setZeroesWithExtraSpace(matrix2);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Brute Force
        startTime = System.nanoTime();
        solution.setZeroesBruteForce(matrix3);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Bit Mask
        startTime = System.nanoTime();
        solution.setZeroesBitMask(matrix4);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: Special Marker
        startTime = System.nanoTime();
        solution.setZeroesSpecialMarker(matrix5);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Approach 6: Reverse Processing
        startTime = System.nanoTime();
        solution.setZeroesReverse(matrix6);
        endTime = System.nanoTime();
        long time6 = endTime - startTime;
        
        System.out.println("\nResults (all should be identical):");
        boolean allEqual = true;
        for (int i = 0; i < matrix.length; i++) {
            if (!Arrays.equals(matrix1[i], matrix2[i]) ||
                !Arrays.equals(matrix2[i], matrix3[i]) ||
                !Arrays.equals(matrix3[i], matrix4[i]) ||
                !Arrays.equals(matrix4[i], matrix5[i]) ||
                !Arrays.equals(matrix5[i], matrix6[i])) {
                allEqual = false;
                break;
            }
        }
        
        System.out.println("All results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        if (!allEqual) {
            System.out.println("\nDifferences found:");
            System.out.println("Approach 1 (O(1)): " + Arrays.deepToString(matrix1));
            System.out.println("Approach 2 (O(m+n)): " + Arrays.deepToString(matrix2));
            System.out.println("Approach 3 (Brute): " + Arrays.deepToString(matrix3));
            System.out.println("Approach 4 (Bit): " + Arrays.deepToString(matrix4));
            System.out.println("Approach 5 (Marker): " + Arrays.deepToString(matrix5));
            System.out.println("Approach 6 (Reverse): " + Arrays.deepToString(matrix6));
        } else {
            System.out.println("\nResult Matrix:");
            printMatrix(matrix1);
        }
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Approach 1: %-10d (O(1) Space)%n", time1);
        System.out.printf("Approach 2: %-10d (O(m+n) Space)%n", time2);
        System.out.printf("Approach 3: %-10d (Brute Force)%n", time3);
        System.out.printf("Approach 4: %-10d (Bit Mask)%n", time4);
        System.out.printf("Approach 5: %-10d (Special Marker)%n", time5);
        System.out.printf("Approach 6: %-10d (Reverse Processing)%n", time6);
        
        // Visualize
        if (matrix.length <= 5 && matrix[0].length <= 5) {
            System.out.println("\n" + "-".repeat(80));
            solution.visualizeSetZeroes(copyMatrix(matrix));
        }
    }
    
    private int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length);
        }
        return copy;
    }
    
    /**
     * Helper: Analyze complexity
     */
    public void analyzeComplexity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Time Complexity:");
        System.out.println("   All approaches: O(m*n)");
        System.out.println("   - Must examine each cell at least once");
        System.out.println("   - Some approaches have multiple passes but still O(m*n)");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   a. O(1) Space: O(1)");
        System.out.println("      - Only few boolean variables");
        System.out.println("      - Uses first row/column as markers");
        System.out.println("   b. O(m+n) Space: O(m+n)");
        System.out.println("      - Boolean arrays for rows and columns");
        System.out.println("   c. Brute Force: O(m*n)");
        System.out.println("      - Copy of entire matrix");
        System.out.println("   d. Bit Mask: O(1) but limited");
        System.out.println("      - Uses long integers (64 bits)");
        System.out.println("      - Only works for m,n ≤ 64");
        System.out.println("   e. Special Marker: O(1) but risky");
        System.out.println("      - Uses special value that might be in matrix");
        
        System.out.println("\n3. Trade-offs:");
        System.out.println("   - O(1) space is optimal but more complex");
        System.out.println("   - O(m+n) space is simpler but uses extra memory");
        System.out.println("   - Brute force is simplest but violates follow-up");
        System.out.println("   - For interviews, O(1) space is expected");
        
        System.out.println("\n4. Constraints Analysis:");
        System.out.println("   m, n ≤ 200");
        System.out.println("   - O(m+n) = 400 booleans = negligible");
        System.out.println("   - O(m*n) = 40,000 elements, copy uses 160KB");
        System.out.println("   - O(1) space is still best for principle");
    }
    
    /**
     * Helper: Show related problems
     */
    public void showRelatedProblems() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 289. Game of Life:");
        System.out.println("   - Similar in-place modification challenge");
        System.out.println("   - Uses bit manipulation to store two states");
        System.out.println("   - Also requires O(1) space solution");
        
        System.out.println("\n2. 48. Rotate Image:");
        System.out.println("   - In-place matrix transformation");
        System.out.println("   - Similar layer-by-layer approach");
        
        System.out.println("\n3. 54. Spiral Matrix:");
        System.out.println("   - Matrix traversal pattern");
        System.out.println("   - Different but related matrix manipulation");
        
        System.out.println("\n4. 498. Diagonal Traverse:");
        System.out.println("   - Another matrix traversal problem");
        
        System.out.println("\n5. 566. Reshape the Matrix:");
        System.out.println("   - Matrix transformation");
        
        System.out.println("\n6. 74. Search a 2D Matrix:");
        System.out.println("   - Matrix search algorithm");
        
        System.out.println("\n7. 240. Search a 2D Matrix II:");
        System.out.println("   - More efficient matrix search");
        
        System.out.println("\nCommon Pattern:");
        System.out.println("All involve matrix manipulation with space constraints");
        System.out.println("Key techniques:");
        System.out.println("- In-place modification");
        System.out.println("- Using matrix itself for storage");
        System.out.println("- Multiple passes with different purposes");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void showApplications() {
        System.out.println("\
