
# Solution.java

```java
import java.util.*;

/**
 * 48. Rotate Image
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Rotate n x n matrix 90 degrees clockwise in-place.
 * 
 * Key Insights:
 * 1. 90° clockwise rotation: (i,j) → (j, n-1-i)
 * 2. Can rotate in layers from outer to inner
 * 3. Transpose + reverse rows = 90° clockwise
 * 4. Reverse rows + transpose = 90° counter-clockwise
 * 
 * Approach 1: Transpose then Reverse (RECOMMENDED)
 * O(n²) time, O(1) space
 */

class Solution {
    
    /**
     * Approach 1: Transpose then Reverse Rows (RECOMMENDED)
     * Time: O(n²), Space: O(1)
     * Most elegant solution
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        
        // 1. Transpose matrix (swap rows and columns)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Swap matrix[i][j] and matrix[j][i]
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // 2. Reverse each row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                // Swap matrix[i][j] and matrix[i][n-1-j]
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }
    
    /**
     * Approach 2: Layer-by-Layer Rotation
     * Time: O(n²), Space: O(1)
     * Rotate concentric layers from outer to inner
     */
    public void rotateLayerByLayer(int[][] matrix) {
        int n = matrix.length;
        
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            
            for (int i = first; i < last; i++) {
                int offset = i - first;
                
                // Save top element
                int top = matrix[first][i];
                
                // Move left to top
                matrix[first][i] = matrix[last - offset][first];
                
                // Move bottom to left
                matrix[last - offset][first] = matrix[last][last - offset];
                
                // Move right to bottom
                matrix[last][last - offset] = matrix[i][last];
                
                // Move top to right
                matrix[i][last] = top;
            }
        }
    }
    
    /**
     * Approach 3: Direct 4-element Rotation
     * Time: O(n²), Space: O(1)
     * Rotate groups of 4 elements at once
     */
    public void rotateFourElements(int[][] matrix) {
        int n = matrix.length;
        
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - i - 1; j++) {
                // Save top-left
                int temp = matrix[i][j];
                
                // Move bottom-left to top-left
                matrix[i][j] = matrix[n - 1 - j][i];
                
                // Move bottom-right to bottom-left
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                
                // Move top-right to bottom-right
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                
                // Move saved top-left to top-right
                matrix[j][n - 1 - i] = temp;
            }
        }
    }
    
    /**
     * Approach 4: Reverse Rows then Transpose (counter-clockwise alternative)
     * Time: O(n²), Space: O(1)
     * For completeness - this gives 90° counter-clockwise
     */
    public void rotateCounterClockwise(int[][] matrix) {
        int n = matrix.length;
        
        // Reverse rows (for clockwise, we reverse columns)
        for (int i = 0; i < n / 2; i++) {
            int[] temp = matrix[i];
            matrix[i] = matrix[n - 1 - i];
            matrix[n - 1 - i] = temp;
        }
        
        // Transpose
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
    
    /**
     * Approach 5: Create New Matrix (NOT in-place, for comparison)
     * Time: O(n²), Space: O(n²)
     * Simple but violates problem requirement
     */
    public void rotateWithExtraSpace(int[][] matrix) {
        int n = matrix.length;
        int[][] result = new int[n][n];
        
        // Create rotated matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[j][n - 1 - i] = matrix[i][j];
            }
        }
        
        // Copy back to original
        for (int i = 0; i < n; i++) {
            System.arraycopy(result[i], 0, matrix[i], 0, n);
        }
    }
    
    /**
     * Approach 6: Mathematical Index Mapping
     * Time: O(n²), Space: O(1)
     * Direct formula: newMatrix[i][j] = matrix[n-1-j][i]
     */
    public void rotateMath(int[][] matrix) {
        int n = matrix.length;
        
        // We need to rotate in place, so we rotate 4 elements at a time
        for (int i = 0; i < (n + 1) / 2; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = matrix[i][j];
                matrix[i][j] = temp;
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
        
        int n = matrix.length;
        System.out.println(n + "x" + n + " matrix:");
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.printf("%3d ", num);
            }
            System.out.println();
        }
    }
    
    /**
     * Helper: Visualize the rotation process
     */
    public void visualizeRotation(int[][] matrix, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Original Matrix:");
        printMatrix(matrix);
        
        // Make a copy for visualization
        int n = matrix.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, n);
        }
        
        if ("Transpose then Reverse".equals(approach)) {
            System.out.println("\nStep 1: Transpose (swap rows and columns)");
            System.out.println("Swap matrix[i][j] with matrix[j][i] for i < j");
            
            // Show transposition
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    System.out.printf("  Swap (%d,%d)=%d with (%d,%d)=%d%n",
                        i, j, copy[i][j], j, i, copy[j][i]);
                    int temp = copy[i][j];
                    copy[i][j] = copy[j][i];
                    copy[j][i] = temp;
                }
            }
            System.out.println("\nAfter transpose:");
            printMatrix(copy);
            
            System.out.println("\nStep 2: Reverse each row");
            System.out.println("Swap matrix[i][j] with matrix[i][n-1-j]");
            
            // Show reversal
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n / 2; j++) {
                    System.out.printf("  Row %d: swap col %d=%d with col %d=%d%n",
                        i, j, copy[i][j], n-1-j, copy[i][n-1-j]);
                    int temp = copy[i][j];
                    copy[i][j] = copy[i][n - 1 - j];
                    copy[i][n - 1 - j] = temp;
                }
            }
            
        } else if ("Layer-by-Layer".equals(approach)) {
            System.out.println("\nRotating layers from outer to inner:");
            
            for (int layer = 0; layer < n / 2; layer++) {
                System.out.printf("\nLayer %d (first=%d, last=%d):%n",
                    layer, layer, n-1-layer);
                
                int first = layer;
                int last = n - 1 - layer;
                
                for (int i = first; i < last; i++) {
                    int offset = i - first;
                    
                    System.out.printf("  Position i=%d (offset=%d):%n", i, offset);
                    System.out.printf("    Top[%d][%d] = %d saved%n",
                        first, i, copy[first][i]);
                    System.out.printf("    Left[%d][%d] = %d → Top[%d][%d]%n",
                        last-offset, first, copy[last-offset][first], first, i);
                    System.out.printf("    Bottom[%d][%d] = %d → Left[%d][%d]%n",
                        last, last-offset, copy[last][last-offset], last-offset, first);
                    System.out.printf("    Right[%d][%d] = %d → Bottom[%d][%d]%n",
                        i, last, copy[i][last], last, last-offset);
                    System.out.printf("    Saved top = %d → Right[%d][%d]%n",
                        copy[first][i], i, last);
                    
                    // Perform rotation
                    int top = copy[first][i];
                    copy[first][i] = copy[last - offset][first];
                    copy[last - offset][first] = copy[last][last - offset];
                    copy[last][last - offset] = copy[i][last];
                    copy[i][last] = top;
                }
            }
        }
        
        System.out.println("\nFinal rotated matrix:");
        printMatrix(copy);
        
        // Apply actual rotation to original
        if ("Transpose then Reverse".equals(approach)) {
            rotate(matrix);
        } else if ("Layer-by-Layer".equals(approach)) {
            rotateLayerByLayer(matrix);
        }
        
        System.out.println("\nAlgorithm result on original:");
        printMatrix(matrix);
    }
    
    /**
     * Helper: Explain the mathematics of rotation
     */
    public void explainRotationMath() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICS OF MATRIX ROTATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 90° Clockwise Rotation:");
        System.out.println("   For element at position (i, j) in n x n matrix:");
        System.out.println("   New position = (j, n-1-i)");
        System.out.println("\n   Example (3x3 matrix):");
        System.out.println("   (0,0)=1 → (0,2)=7");
        System.out.println("   (0,1)=2 → (1,2)=8");
        System.out.println("   (0,2)=3 → (2,2)=9");
        System.out.println("   (1,0)=4 → (0,1)=4");
        System.out.println("   etc.");
        
        System.out.println("\n2. Why Transpose + Reverse works:");
        System.out.println("   Let's trace an element (i, j):");
        System.out.println("   Step 1 - Transpose: (i, j) → (j, i)");
        System.out.println("   Step 2 - Reverse row: (j, i) → (j, n-1-i)");
        System.out.println("   Result: (i, j) → (j, n-1-i) ✓");
        
        System.out.println("\n3. Matrix operations for different rotations:");
        System.out.println("   a. 90° clockwise: transpose + reverse rows");
        System.out.println("   b. 90° counter-clockwise: reverse rows + transpose");
        System.out.println("   c. 180°: reverse rows + reverse columns");
        System.out.println("   d. 270° clockwise: reverse rows + transpose (same as 90° CCW)");
        
        System.out.println("\n4. Layer-by-Layer rotation formula:");
        System.out.println("   For layer with boundaries [first, last]:");
        System.out.println("   For i from first to last-1:");
        System.out.println("     offset = i - first");
        System.out.println("     temp = top[first][i]");
        System.out.println("     top[first][i] = left[last-offset][first]");
        System.out.println("     left[last-offset][first] = bottom[last][last-offset]");
        System.out.println("     bottom[last][last-offset] = right[i][last]");
        System.out.println("     right[i][last] = temp");
        
        System.out.println("\n5. Four-element rotation pattern:");
        System.out.println("   Four positions that rotate together:");
        System.out.println("   A = (i, j)");
        System.out.println("   B = (j, n-1-i)");
        System.out.println("   C = (n-1-i, n-1-j)");
        System.out.println("   D = (n-1-j, i)");
        System.out.println("   Rotation: A ← D ← C ← B ← A");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES TESTING:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. 1x1 matrix:");
        int[][] case1 = {{5}};
        int[][] expected1 = {{5}};
        System.out.println("   Original: [[5]]");
        solution.rotate(case1);
        System.out.println("   Result: " + Arrays.deepToString(case1));
        System.out.println("   Correct: " + Arrays.deepEquals(case1, expected1));
        
        System.out.println("\n2. 2x2 matrix:");
        int[][] case2 = {{1,2},{3,4}};
        int[][] expected2 = {{3,1},{4,2}};
        System.out.println("   Original: [[1,2],[3,4]]");
        solution.rotate(case2);
        System.out.println("   Result: " + Arrays.deepToString(case2));
        System.out.println("   Expected: [[3,1],[4,2]]");
        System.out.println("   Correct: " + Arrays.deepEquals(case2, expected2));
        
        System.out.println("\n3. 3x3 matrix (Example 1):");
        int[][] case3 = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] expected3 = {{7,4,1},{8,5,2},{9,6,3}};
        System.out.println("   Original: [[1,2,3],[4,5,6],[7,8,9]]");
        solution.rotate(case3);
        System.out.println("   Result: " + Arrays.deepToString(case3));
        System.out.println("   Expected: [[7,4,1],[8,5,2],[9,6,3]]");
        System.out.println("   Correct: " + Arrays.deepEquals(case3, expected3));
        
        System.out.println("\n4. 4x4 matrix (Example 2):");
        int[][] case4 = {{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}};
        int[][] expected4 = {{15,13,2,5},{14,3,4,1},{12,6,8,9},{16,7,10,11}};
        System.out.println("   4x4 matrix from example");
        solution.rotate(case4);
        System.out.println("   Result matches expected: " + Arrays.deepEquals(case4, expected4));
        
        System.out.println("\n5. 5x5 matrix:");
        int[][] case5 = {
            {1,2,3,4,5},
            {6,7,8,9,10},
            {11,12,13,14,15},
            {16,17,18,19,20},
            {21,22,23,24,25}
        };
        System.out.println("   5x5 matrix");
        solution.rotate(case5);
        System.out.println("   Rotated successfully");
        
        // Verify by rotating 4 times (should return to original)
        solution.rotate(case5);
        solution.rotate(case5);
        solution.rotate(case5);
        int[][] rotated4Times = {
            {1,2,3,4,5},
            {6,7,8,9,10},
            {11,12,13,14,15},
            {16,17,18,19,20},
            {21,22,23,24,25}
        };
        System.out.println("   4 rotations returns to original: " + 
            Arrays.deepEquals(case5, rotated4Times));
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(int[][] matrix) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ROTATION APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nOriginal " + matrix.length + "x" + matrix.length + " matrix:");
        printMatrix(matrix);
        
        Solution solution = new Solution();
        
        // Create copies for each approach
        int[][] matrix1 = copyMatrix(matrix);
        int[][] matrix2 = copyMatrix(matrix);
        int[][] matrix3 = copyMatrix(matrix);
        int[][] matrix4 = copyMatrix(matrix); // For counter-clockwise
        int[][] matrix5 = copyMatrix(matrix);
        int[][] matrix6 = copyMatrix(matrix);
        
        long startTime, endTime;
        
        // Approach 1: Transpose + Reverse
        startTime = System.nanoTime();
        solution.rotate(matrix1);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Layer-by-Layer
        startTime = System.nanoTime();
        solution.rotateLayerByLayer(matrix2);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Four Elements
        startTime = System.nanoTime();
        solution.rotateFourElements(matrix3);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 5: Extra Space (for comparison)
        startTime = System.nanoTime();
        solution.rotateWithExtraSpace(matrix5);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Approach 6: Math
        startTime = System.nanoTime();
        solution.rotateMath(matrix6);
        endTime = System.nanoTime();
        long time6 = endTime - startTime;
        
        System.out.println("\nResults (all clockwise approaches should be identical):");
        boolean allEqual = Arrays.deepEquals(matrix1, matrix2) &&
                          Arrays.deepEquals(matrix2, matrix3) &&
                          Arrays.deepEquals(matrix3, matrix5) &&
                          Arrays.deepEquals(matrix5, matrix6);
        
        System.out.println("All clockwise results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        if (!allEqual) {
            System.out.println("\nDifferences found!");
            System.out.println("Approach 1 (Transpose+Reverse):");
            printMatrix(matrix1);
            System.out.println("\nApproach 2 (Layer-by-Layer):");
            printMatrix(matrix2);
            System.out.println("\nApproach 3 (Four Elements):");
            printMatrix(matrix3);
        } else {
            System.out.println("\nRotated Matrix:");
            printMatrix(matrix1);
        }
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Transpose+Reverse:    %-10d (Recommended)%n", time1);
        System.out.printf("Layer-by-Layer:       %-10d (Explicit)%n", time2);
        System.out.printf("Four Elements:        %-10d (Direct)%n", time3);
        System.out.printf("With Extra Space:     %-10d (For comparison)%n", time5);
        System.out.printf("Math:                 %-10d (Formula-based)%n", time6);
        
        // Test counter-clockwise
        solution.rotateCounterClockwise(matrix4);
        System.out.println("\nCounter-clockwise rotation (Approach 4):");
        printMatrix(matrix4);
        
        // Verify counter-clockwise is inverse of clockwise
        solution.rotate(matrix4); // Rotate clockwise
        System.out.println("\nAfter clockwise rotation of CCW result (should be original):");
        printMatrix(matrix4);
        System.out.println("Matches original: " + Arrays.deepEquals(matrix4, matrix));
        
        // Visualize
        if (matrix.length <= 5) {
            System.out.println("\n" + "-".repeat(80));
            solution.visualizeRotation(copyMatrix(matrix), "Transpose then Reverse");
            
            System.out.println("\n" + "-".repeat(80));
            solution.visualizeRotation(copyMatrix(matrix), "Layer-by-Layer");
        }
    }
    
    private int[][] copyMatrix(int[][] matrix) {
        int n = matrix.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, n);
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
        System.out.println("   All in-place approaches: O(n²)");
        System.out.println("   - Must visit each element at least once");
        System.out.println("   - Transpose + Reverse: two passes = O(n²) + O(n²) = O(n²)");
        System.out.println("   - Layer-by-Layer: single pass but 4 operations per element = O(n²)");
        System.out.println("   - With extra space: O(n²) for copy + O(n²) for copy back = O(n²)");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   a. Transpose + Reverse: O(1)");
        System.out.println("      - Only temporary variable for swaps");
        System.out.println("   b. Layer-by-Layer: O(1)");
        System.out.println("      - Only few variables for indices");
        System.out.println("   c. Four Elements: O(1)");
        System.out.println("      - Single temp variable");
        System.out.println("   d. With extra space: O(n²)");
        System.out.println("      - Complete copy of matrix");
        
        System.out.println("\n3. Why O(n²) is optimal:");
        System.out.println("   - Must write new value to each of n² positions");
        System.out.println("   - Cannot do better than O(n²)");
        System.out.println("   - Even with extra space, still O(n²) time");
        
        System.out.println("\n4. Real-world performance considerations:");
        System.out.println("   - Transpose + Reverse has good cache locality");
        System.out.println("   - Layer-by-Layer might be more cache-friendly");
        System.out.println("   - For n ≤ 20 (constraint), all are efficient");
        System.out.println("   - Built-in methods often use optimized routines");
    }
    
    /**
     * Helper: Show related problems
     */
    public void showRelatedProblems() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 54. Spiral Matrix:");
        System.out.println("   - Matrix traversal pattern");
        System.out.println("   - Similar layer-by-layer processing");
        
        System.out.println("\n2. 73. Set Matrix Zeroes:");
        System.out.println("   - In-place matrix modification");
        System.out.println("   - Similar space constraints");
        
        System.out.println("\n3. 867. Transpose Matrix:");
        System.out.println("   - Part of rotation solution");
        System.out.println("   - Simpler matrix operation");
        
        System.out.println("\n4. 1886. Determine Whether Matrix Can Be Obtained By Rotation:");
        System.out.println("   - Extension of rotation problem");
        System.out.println("   - Check if rotation produces target");
        
        System.out.println("\n5. 566. Reshape the Matrix:");
        System.out.println("   - Matrix transformation");
        
        System.out.println("\n6. 861. Score After Flipping Matrix:");
        System.out.println("   - Matrix manipulation with optimization");
        
        System.out.println("\n7. 1337. The K Weakest Rows in a Matrix:");
        System.out.println("   - Matrix analysis problem");
        
        System.out.println("\nCommon Pattern:");
        System.out.println("All involve matrix manipulation with in-place constraints");
        System.out.println("Key techniques:");
        System.out.println("- Index manipulation mathematics");
        System.out.println("- Layer-by-layer processing");
        System.out.println("- Swapping elements in place");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Image Processing:");
        System.out.println("   - Image rotation in photo editors");
        System.out.println("   - Computer vision transformations");
        System.out.println("   - Medical imaging (MRI, CT scan rotation)");
        
        System.out.println("\n2. Computer Graphics:");
        System.out.println("   - 2D sprite rotation in games");
        System.out.println("   - Texture mapping transformations");
        System.out.println("   - GUI element rotation");
        
        System.out.println("\n3. Game Development:");
        System.out.println("   - Tile-based game map rotation");
        System.out.println("   - Puzzle game mechanics (e.g., rotating pieces)");
        System.out.println("   - Character/object orientation");
        
        System.out.println("\n4. Robotics:");
        System.out.println("   - Sensor data transformation");
        System.out.println("   - Coordinate system rotation");
        System.out.println("   - Path planning with orientation");
        
        System.out.println("\n5. Data Visualization:");
        System.out.println("   - Chart axis rotation");
        System.out.println("   - Graph layout optimization");
        System.out.println("   - 3D plot rotation");
        
        System.out.println("\n6. Mobile Applications:");
        System.out.println("   - Screen rotation handling");
        System.out.println("   - Touch gesture recognition");
        System.out.println("   - Animation transformations");
        
        System.out.println("\n7. Computer Vision:");
        System.out.println("   - Image registration");
        System.out.println("   - Feature alignment");
        System.out.println("   - Pattern recognition with rotation invariance");
    }
    
    /**
     * Main method with comprehensive tests
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Rotate Image (48. Rotate Image):");
        System.out.println("=================================");
        
        // Explain the mathematics
        solution.explainRotationMath();
        
        // Test edge cases
        solution.testEdgeCases();
        
        // Example 1 from problem
        System.out.println("\n\nExample 1 from problem:");
        int[][] matrix1 = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] expected1 = {{7,4,1},{8,5,2},{9,6,3}};
        
        System.out.println("\nOriginal 3x3 matrix:");
        solution.printMatrix(matrix1);
        
        // Make a copy for rotation
        int[][] matrix1Copy = solution.copyMatrix(matrix1);
        solution.rotate(matrix1Copy);
        
        System.out.println("\nAfter 90° clockwise rotation:");
        solution.printMatrix(matrix1Copy);
        System.out.println("Expected:");
        solution.printMatrix(expected1);
        System.out.println("Correct: " + Arrays.deepEquals(matrix1Copy, expected1));
        
        // Example 2 from problem
        System.out.println("\n\nExample 2 from problem:");
        int[][] matrix2 = {
            {5,1,9,11},
            {2,4,8,10},
            {13,3,6,7},
            {15,14,12,16}
        };
        int[][] expected2 = {
            {15,13,2,5},
            {14,3,4,1},
            {12,6,8,9},
            {16,7,10,11}
        };
        
        System.out.println("\nOriginal 4x4 matrix:");
        solution.printMatrix(matrix2);
        
        int[][] matrix2Copy = solution.copyMatrix(matrix2);
        solution.rotate(matrix2Copy);
        
        System.out.println("\nAfter 90° clockwise rotation:");
        solution.printMatrix(matrix2Copy);
        System.out.println("Correct: " + Arrays.deepEquals(matrix2Copy, expected2));
        
        // Additional test: Rotate 4 times returns to original
        System.out.println("\n\nTest: 4 rotations return to original");
        int[][] testMatrix = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] original = solution.copyMatrix(testMatrix);
        
        for (int i = 0; i < 4; i++) {
            solution.rotate(testMatrix);
        }
        
        System.out.println("After 4 x 90° rotations:");
        solution.printMatrix(testMatrix);
        System.out.println("Back to original: " + Arrays.deepEquals(testMatrix, original));
        
        // Compare all approaches
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES FOR EXAMPLE 1:");
        System.out.println("=".repeat(80));
        solution.compareApproaches(solution.copyMatrix(matrix1));
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES FOR EXAMPLE 2:");
        System.out.println("=".repeat(80));
        solution.compareApproaches(solution.copyMatrix(matrix2));
        
        // Test various matrix sizes
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TESTING VARIOUS MATRIX SIZES:");
        System.out.println("=".repeat(80));
        
        // Generate and test matrices of sizes 1 through 6
        for (int size = 1; size <= 6; size++) {
            System.out.println("\nTesting " + size + "x" + size + " matrix:");
            
            // Create matrix with sequential numbers
            int[][] matrix = new int[size][size];
            int value = 1;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = value++;
                }
            }
            
            // Test rotation
            int[][] rotated = solution.copyMatrix(matrix);
            solution.rotate(rotated
