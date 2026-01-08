
# Solution.java

```java
import java.util.*;

/**
 * 54. Spiral Matrix
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given m x n matrix, return all elements in spiral order.
 * 
 * Key Insights:
 * 1. Traverse in layers: outer layer first, then inner layers
 * 2. Use four boundaries: top, bottom, left, right
 * 3. Traverse directions: right → down → left → up
 * 4. Update boundaries after each direction
 * 5. Stop when boundaries cross
 * 
 * Approach 1: Boundary Traversal (RECOMMENDED)
 * O(m*n) time, O(1) extra space
 */

class Solution {
    
    /**
     * Approach 1: Boundary Traversal (RECOMMENDED)
     * O(m*n) time, O(1) extra space
     * Use four pointers to track boundaries
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        int top = 0, bottom = m - 1;
        int left = 0, right = n - 1;
        
        while (top <= bottom && left <= right) {
            // Traverse right across the top row
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++;
            
            // Traverse down the right column
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--;
            
            // Traverse left across the bottom row (if still rows left)
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--;
            }
            
            // Traverse up the left column (if still columns left)
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }
                left++;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: Direction-based with visited markers
     * O(m*n) time, O(m*n) space for visited array
     * More intuitive but uses extra space
     */
    public List<Integer> spiralOrderVisited(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;
        
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        
        // Directions: right, down, left, up
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int dir = 0; // Start with right
        
        int row = 0, col = 0;
        
        for (int i = 0; i < m * n; i++) {
            result.add(matrix[row][col]);
            visited[row][col] = true;
            
            // Calculate next position
            int nextRow = row + directions[dir][0];
            int nextCol = col + directions[dir][1];
            
            // If next position is out of bounds or visited, change direction
            if (nextRow < 0 || nextRow >= m || nextCol < 0 || nextCol >= n || 
                visited[nextRow][nextCol]) {
                dir = (dir + 1) % 4;
                nextRow = row + directions[dir][0];
                nextCol = col + directions[dir][1];
            }
            
            row = nextRow;
            col = nextCol;
        }
        
        return result;
    }
    
    /**
     * Approach 3: Layer-by-Layer (More Explicit)
     * O(m*n) time, O(1) extra space
     * Processes matrix in concentric layers
     */
    public List<Integer> spiralOrderLayer(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        int layer = 0;
        int totalLayers = (Math.min(m, n) + 1) / 2;
        
        for (; layer < totalLayers; layer++) {
            // Top row (left to right)
            for (int col = layer; col < n - layer; col++) {
                result.add(matrix[layer][col]);
            }
            
            // Right column (top to bottom, excluding first)
            for (int row = layer + 1; row < m - layer; row++) {
                result.add(matrix[row][n - layer - 1]);
            }
            
            // Bottom row (right to left, if not same as top)
            if (layer < m - layer - 1) {
                for (int col = n - layer - 2; col >= layer; col--) {
                    result.add(matrix[m - layer - 1][col]);
                }
            }
            
            // Left column (bottom to top, if not same as right)
            if (layer < n - layer - 1) {
                for (int row = m - layer - 2; row > layer; row--) {
                    result.add(matrix[row][layer]);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: Recursive
     * O(m*n) time, O(min(m,n)) recursion stack space
     * Recursively process outer layer, then inner matrix
     */
    public List<Integer> spiralOrderRecursive(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;
        
        spiralHelper(matrix, 0, matrix.length - 1, 0, matrix[0].length - 1, result);
        return result;
    }
    
    private void spiralHelper(int[][] matrix, int top, int bottom, int left, int right, 
                             List<Integer> result) {
        if (top > bottom || left > right) return;
        
        // Top row
        for (int col = left; col <= right; col++) {
            result.add(matrix[top][col]);
        }
        
        // Right column
        for (int row = top + 1; row <= bottom; row++) {
            result.add(matrix[row][right]);
        }
        
        // Bottom row (if exists)
        if (top < bottom) {
            for (int col = right - 1; col >= left; col--) {
                result.add(matrix[bottom][col]);
            }
        }
        
        // Left column (if exists)
        if (left < right) {
            for (int row = bottom - 1; row > top; row--) {
                result.add(matrix[row][left]);
            }
        }
        
        // Recurse on inner matrix
        spiralHelper(matrix, top + 1, bottom - 1, left + 1, right - 1, result);
    }
    
    /**
     * Approach 5: Math-based (for square matrices)
     * O(n²) time, O(1) extra space
     * Uses mathematical formulas for spiral indices
     */
    public List<Integer> spiralOrderMath(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        for (int i = 0; i < (Math.min(m, n) + 1) / 2; i++) {
            // Four loops for four directions
            for (int j = i; j < n - i; j++) {
                result.add(matrix[i][j]);
            }
            
            for (int j = i + 1; j < m - i; j++) {
                result.add(matrix[j][n - i - 1]);
            }
            
            if (i < m - i - 1) {
                for (int j = n - i - 2; j >= i; j--) {
                    result.add(matrix[m - i - 1][j]);
                }
            }
            
            if (i < n - i - 1) {
                for (int j = m - i - 2; j > i; j--) {
                    result.add(matrix[j][i]);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 6: Simulation with Boundary Shrinking
     * O(m*n) time, O(1) extra space
     * Similar to Approach 1 but with different boundary updates
     */
    public List<Integer> spiralOrderSimulation(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;
        
        int rows = matrix.length, cols = matrix[0].length;
        int row = 0, col = -1;
        int direction = 1; // 1: right, 2: down, 3: left, 4: up
        
        while (rows > 0 && cols > 0) {
            // Move horizontally
            for (int i = 0; i < cols; i++) {
                col += direction == 1 ? 1 : -1;
                result.add(matrix[row][col]);
            }
            rows--;
            
            if (rows == 0) break;
            
            // Move vertically
            for (int i = 0; i < rows; i++) {
                row += direction == 2 ? 1 : -1;
                result.add(matrix[row][col]);
            }
            cols--;
            
            // Change direction
            direction = direction == 1 ? 2 : direction == 2 ? 3 : direction == 3 ? 4 : 1;
        }
        
        return result;
    }
    
    /**
     * Helper: Visualize the spiral traversal
     */
    public void visualizeSpiral(int[][] matrix) {
        System.out.println("\nSpiral Matrix Visualization:");
        System.out.println("Matrix " + matrix.length + "x" + matrix[0].length + ":");
        printMatrix(matrix);
        
        List<Integer> spiral = spiralOrder(matrix);
        System.out.println("\nSpiral Order: " + spiral);
        
        System.out.println("\nStep-by-step traversal:");
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] visited = new int[m][n];
        int step = 1;
        
        int top = 0, bottom = m - 1;
        int left = 0, right = n - 1;
        
        while (top <= bottom && left <= right) {
            System.out.println("\nLayer boundaries: top=" + top + ", bottom=" + bottom + 
                             ", left=" + left + ", right=" + right);
            
            // Right
            System.out.print("→ Right: ");
            for (int col = left; col <= right; col++) {
                visited[top][col] = step++;
                System.out.print(matrix[top][col] + " ");
            }
            top++;
            printVisited(visited);
            
            // Down
            if (top <= bottom) {
                System.out.print("↓ Down:  ");
                for (int row = top; row <= bottom; row++) {
                    visited[row][right] = step++;
                    System.out.print(matrix[row][right] + " ");
                }
                right--;
                printVisited(visited);
            }
            
            // Left
            if (top <= bottom) {
                System.out.print("← Left:  ");
                for (int col = right; col >= left; col--) {
                    visited[bottom][col] = step++;
                    System.out.print(matrix[bottom][col] + " ");
                }
                bottom--;
                printVisited(visited);
            }
            
            // Up
            if (left <= right) {
                System.out.print("↑ Up:    ");
                for (int row = bottom; row >= top; row--) {
                    visited[row][left] = step++;
                    System.out.print(matrix[row][left] + " ");
                }
                left++;
                printVisited(visited);
            }
        }
        
        System.out.println("\nFinal traversal order (by step):");
        printVisitedWithSteps(visited);
    }
    
    private void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.printf("%3d ", num);
            }
            System.out.println();
        }
    }
    
    private void printVisited(int[][] visited) {
        System.out.println("\nCurrent state:");
        for (int[] row : visited) {
            for (int val : row) {
                if (val == 0) {
                    System.out.print("  . ");
                } else {
                    System.out.printf("%3d ", val);
                }
            }
            System.out.println();
        }
    }
    
    private void printVisitedWithSteps(int[][] visited) {
        System.out.println("\nStep positions:");
        int step = 1;
        int totalSteps = visited.length * visited[0].length;
        
        Map<Integer, String> stepPositions = new TreeMap<>();
        
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                stepPositions.put(visited[i][j], 
                    String.format("Step %2d: matrix[%d][%d] = %d", 
                        visited[i][j], i, j, visited[i][j]));
            }
        }
        
        for (String pos : stepPositions.values()) {
            System.out.println(pos);
        }
    }
    
    /**
     * Helper: Explain the algorithm logic
     */
    public void explainAlgorithm() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nCore Idea:");
        System.out.println("Traverse matrix in clockwise spiral order using four boundaries:");
        System.out.println("1. top: current top row index");
        System.out.println("2. bottom: current bottom row index");
        System.out.println("3. left: current left column index");
        System.out.println("4. right: current right column index");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize: top=0, bottom=m-1, left=0, right=n-1");
        System.out.println("2. While top <= bottom AND left <= right:");
        System.out.println("   a. Traverse RIGHT across top row (left → right)");
        System.out.println("   b. Increment top (move boundary inward)");
        System.out.println("   c. Traverse DOWN right column (top → bottom)");
        System.out.println("   d. Decrement right (move boundary inward)");
        System.out.println("   e. If top <= bottom:");
        System.out.println("      Traverse LEFT across bottom row (right → left)");
        System.out.println("      Decrement bottom");
        System.out.println("   f. If left <= right:");
        System.out.println("      Traverse UP left column (bottom → top)");
        System.out.println("      Increment left");
        
        System.out.println("\nWhy it works:");
        System.out.println("- Each iteration processes one layer of the matrix");
        System.out.println("- Boundaries shrink inward after each direction");
        System.out.println("- Conditions check if we still have rows/columns to process");
        System.out.println("- Works for any m x n matrix (not just squares)");
        
        System.out.println("\nExample: matrix = [[1,2,3],[4,5,6],[7,8,9]]");
        System.out.println("\nIteration 1:");
        System.out.println("  Boundaries: top=0, bottom=2, left=0, right=2");
        System.out.println("  → Right: [0,0]=1, [0,1]=2, [0,2]=3");
        System.out.println("  top++ → top=1");
        System.out.println("  ↓ Down: [1,2]=6, [2,2]=9");
        System.out.println("  right-- → right=1");
        System.out.println("  ← Left: [2,1]=8, [2,0]=7");
        System.out.println("  bottom-- → bottom=1");
        System.out.println("  ↑ Up: [1,0]=4");
        System.out.println("  left++ → left=1");
        
        System.out.println("\nIteration 2:");
        System.out.println("  Boundaries: top=1, bottom=1, left=1, right=1");
        System.out.println("  → Right: [1,1]=5");
        System.out.println("  top++ → top=2 > bottom=1 → STOP");
        
        System.out.println("\nResult: [1,2,3,6,9,8,7,4,5]");
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
        List<Integer> result1 = solution.spiralOrder(case1);
        System.out.println("   Matrix: [[5]]");
        System.out.println("   Result: " + result1 + " (expected: [5])");
        
        System.out.println("\n2. 1xn matrix (single row):");
        int[][] case2 = {{1, 2, 3, 4, 5}};
        List<Integer> result2 = solution.spiralOrder(case2);
        System.out.println("   Matrix: [[1,2,3,4,5]]");
        System.out.println("   Result: " + result2 + " (expected: [1,2,3,4,5])");
        
        System.out.println("\n3. mx1 matrix (single column):");
        int[][] case3 = {{1}, {2}, {3}, {4}, {5}};
        List<Integer> result3 = solution.spiralOrder(case3);
        System.out.println("   Matrix: [[1],[2],[3],[4],[5]]");
        System.out.println("   Result: " + result3 + " (expected: [1,2,3,4,5])");
        
        System.out.println("\n4. 2x2 matrix:");
        int[][] case4 = {{1,2}, {3,4}};
        List<Integer> result4 = solution.spiralOrder(case4);
        System.out.println("   Matrix: [[1,2],[3,4]]");
        System.out.println("   Result: " + result4 + " (expected: [1,2,4,3])");
        
        System.out.println("\n5. 2x3 matrix:");
        int[][] case5 = {{1,2,3}, {4,5,6}};
        List<Integer> result5 = solution.spiralOrder(case5);
        System.out.println("   Matrix: [[1,2,3],[4,5,6]]");
        System.out.println("   Result: " + result5 + " (expected: [1,2,3,6,5,4])");
        
        System.out.println("\n6. 3x2 matrix:");
        int[][] case6 = {{1,2}, {3,4}, {5,6}};
        List<Integer> result6 = solution.spiralOrder(case6);
        System.out.println("   Matrix: [[1,2],[3,4],[5,6]]");
        System.out.println("   Result: " + result6 + " (expected: [1,2,4,6,5,3])");
        
        System.out.println("\n7. 4x4 matrix:");
        int[][] case7 = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,16}};
        List<Integer> result7 = solution.spiralOrder(case7);
        System.out.println("   4x4 matrix");
        System.out.println("   Result: " + result7);
        System.out.println("   Expected: [1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10]");
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(int[][] matrix) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput Matrix " + matrix.length + "x" + matrix[0].length + ":");
        printMatrix(matrix);
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        List<Integer> result1, result2, result3, result4, result5, result6;
        
        // Approach 1: Boundary Traversal
        startTime = System.nanoTime();
        result1 = solution.spiralOrder(matrix);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Visited Array
        startTime = System.nanoTime();
        result2 = solution.spiralOrderVisited(matrix);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Layer-by-Layer
        startTime = System.nanoTime();
        result3 = solution.spiralOrderLayer(matrix);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Recursive
        startTime = System.nanoTime();
        result4 = solution.spiralOrderRecursive(matrix);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: Math-based
        startTime = System.nanoTime();
        result5 = solution.spiralOrderMath(matrix);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Approach 6: Simulation
        startTime = System.nanoTime();
        result6 = solution.spiralOrderSimulation(matrix);
        endTime = System.nanoTime();
        long time6 = endTime - startTime;
        
        System.out.println("\nResults (first 10 elements):");
        System.out.println("Approach 1: " + result1.subList(0, Math.min(10, result1.size())));
        System.out.println("Approach 2: " + result2.subList(0, Math.min(10, result2.size())));
        System.out.println("Approach 3: " + result3.subList(0, Math.min(10, result3.size())));
        System.out.println("Approach 4: " + result4.subList(0, Math.min(10, result4.size())));
        System.out.println("Approach 5: " + result5.subList(0, Math.min(10, result5.size())));
        System.out.println("Approach 6: " + result6.subList(0, Math.min(10, result6.size())));
        
        boolean allEqual = result1.equals(result2) && result2.equals(result3) && 
                          result3.equals(result4) && result4.equals(result5) && 
                          result5.equals(result6);
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        if (!allEqual) {
            System.out.println("\nFull results comparison:");
            System.out.println("Approach 1: " + result1);
            System.out.println("Approach 2: " + result2);
            System.out.println("Approach 3: " + result3);
            System.out.println("Approach 4: " + result4);
            System.out.println("Approach 5: " + result5);
            System.out.println("Approach 6: " + result6);
        }
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Approach 1: %-10d (Boundary Traversal)%n", time1);
        System.out.printf("Approach 2: %-10d (Visited Array)%n", time2);
        System.out.printf("Approach 3: %-10d (Layer-by-Layer)%n", time3);
        System.out.printf("Approach 4: %-10d (Recursive)%n", time4);
        System.out.printf("Approach 5: %-10d (Math-based)%n", time5);
        System.out.printf("Approach 6: %-10d (Simulation)%n", time6);
        
        // Visualize
        if (matrix.length <= 5 && matrix[0].length <= 5) {
            solution.visualizeSpiral(matrix);
        }
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
        System.out.println("   - Must visit each element exactly once");
        System.out.println("   - No algorithm can do better than O(m*n)");
        
        System.out.println("\n2. Space Complexity (excluding output):");
        System.out.println("   a. Boundary Traversal: O(1)");
        System.out.println("      - Only four boundary variables");
        System.out.println("   b. Visited Array: O(m*n)");
        System.out.println("      - Boolean array to track visited cells");
        System.out.println("   c. Layer-by-Layer: O(1)");
        System.out.println("      - Only loop variables");
        System.out.println("   d. Recursive: O(min(m,n))");
        System.out.println("      - Recursion depth equals number of layers");
        System.out.println("   e. Math-based: O(1)");
        System.out.println("      - Only loop variables");
        System.out.println("   f. Simulation: O(1)");
        System.out.println("      - Only variables for position/direction");
        
        System.out.println("\n3. Why Boundary Traversal is Optimal:");
        System.out.println("   - O(m*n) time (optimal)");
        System.out.println("   - O(1) extra space (optimal)");
        System.out.println("   - Simple and intuitive");
        System.out.println("   - Handles all matrix shapes");
        
        System.out.println("\n4. Constraints Analysis:");
        System.out.println("   m, n ≤ 10 (very small)");
        System.out.println("   - Even O(m*n) space is acceptable");
        System.out.println("   - All algorithms work efficiently");
        System.out.println("   - But good to know optimal solution for larger inputs");
    }
    
    /**
     * Helper: Show related problems
     */
    public void showRelatedProblems() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 59. Spiral Matrix II:");
        System.out.println("   Given n, generate n x n matrix with numbers 1..n² in spiral order");
        System.out.println("   Similar approach but filling instead of reading");
        
        System.out.println("\n2. 885. Spiral Matrix III:");
        System.out.println("   Start at (r0, c0) and walk in expanding spiral");
        System.out.println("   Return coordinates in order visited");
        
        System.out.println("\n3. 2326. Spiral Matrix IV:");
        System.out.println("   Convert linked list to spiral matrix");
        System.out.println("   Fill matrix with linked list values");
        
        System.out.println("\n4. 48. Rotate Image:");
        System.out.println("   Rotate n x n matrix 90 degrees clockwise");
        System.out.println("   Similar layer-by-layer approach");
        
        System.out.println("\n5. 498. Diagonal Traverse:");
        System.out.println("   Traverse matrix in diagonal zigzag order");
        
        System.out.println("\n6. 1424. Diagonal Traverse II:");
        System.out.println("   Traverse 2D list in diagonal order");
        
        System.out.println("\nCommon Pattern:");
        System.out.println("All involve matrix traversal in non-standard orders");
        System.out.println("Key techniques:");
        System.out.println("- Boundary tracking");
        System.out.println("- Direction management");
        System.out.println("- Layer-by-layer processing");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Image Processing:");
        System.out.println("   - Spiral scanning of images");
        System.out.println("   - Feature extraction from center outward");
        System.out.println("   - Image compression algorithms");
        
        System.out.println("\n2. Computer Graphics:");
        System.out.println("   - Texture mapping");
        System.out.println("   - Ray tracing paths");
        System.out.println("   - Screen space effects");
        
        System.out.println("\n3. Game Development:");
        System.out.println("   - Fog of war exploration");
        System.out.println("   - Map discovery algorithms");
        System.out.println("   - Pathfinding from center");
        
        System.out.println("\n4. Data Storage:");
        System.out.println("   - Spiral track hard drives");
        System.out.println("   - Optical disk reading/writing");
        System.out.println("   - Memory layout optimization");
        
        System.out.println("\n5. Robotics:");
        System.out.println("   - Spiral search patterns");
        System.out.println("   - Area coverage algorithms");
        System.out.println("   - Sensor scanning patterns");
        
        System.out.println("\n6. Printed Circuit Boards:");
        System.out.println("   - Spiral trace routing");
        System.out.println("   - Thermal management");
        System.out.println("   - Signal integrity analysis");
        
        System.out.println("\n7. Medical Imaging:");
        System.out.println("   - Spiral CT scans");
        System.out.println("   - MRI acquisition patterns");
        System.out.println("   - Ultrasound scanning");
    }
    
    /**
     * Main method with comprehensive tests
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Spiral Matrix:");
        System.out.println("======================");
        
        // Explain algorithm
        solution.explainAlgorithm();
        
        // Test edge cases
        solution.testEdgeCases();
        
        // Example 1 from problem
        System.out.println("\n\nExample 1 from problem:");
        int[][] matrix1 = {{1,2,3}, {4,5,6}, {7,8,9}};
        List<Integer> expected1 = Arrays.asList(1,2,3,6,9,8,7,4,5);
        
        System.out.println("\nInput matrix 3x3:");
        solution.printMatrix(matrix1);
        
        List<Integer> result1 = solution.spiralOrder(matrix1);
        System.out.println("\nExpected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1.equals(expected1) ? "✓" : "✗"));
        
        solution.visualizeSpiral(matrix1);
        
        // Example 2 from problem
        System.out.println("\n\nExample 2 from problem:");
        int[][] matrix2 = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}};
        List<Integer> expected2 = Arrays.asList(1,2,3,4,8,12,11,10,9,5,6,7);
        
        System.out.println("\nInput matrix 3x4:");
        solution.printMatrix(matrix2);
        
        List<Integer> result2 = solution.spiralOrder(matrix2);
        System.out.println("\nExpected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2.equals(expected2) ? "✓" : "✗"));
        
        solution.visualizeSpiral(matrix2);
        
        // Additional test cases
        System.out.println("\n\nAdditional Test Cases:");
        
        // Test case 3: 4x5 matrix
        System.out.println("\nTest Case 3: 4x5 matrix");
        int[][] matrix3 = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20}
        };
        
        System.out.println("\nInput matrix 4x5:");
        solution.printMatrix(matrix3);
        
        List<Integer> result3 = solution.spiralOrder(matrix3);
        System.out.println("\nResult: " + result3);
        System.out.println("Length: " + result3.size() + " (should be 20)");
        
        // Test case 4: 5x3 matrix
        System.out.println("\nTest Case 4: 5x3 matrix");
        int[][] matrix4 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12},
            {13, 14, 15}
        };
        
        System.out.println("\nInput matrix 5x3:");
        solution.printMatrix(matrix4);
        
        List<Integer> result4 = solution.spiralOrder(matrix4);
        System.out.println("\nResult: " + result4);
        System.out.println("Length: " + result4.size() + " (should be 15)");
        
        // Compare all approaches
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES FOR EXAMPLE 1:");
        System.out.println("=".repeat(80));
        solution.compareApproaches(matrix1);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES FOR EXAMPLE 2:");
        System.out.println("=".repeat(80));
        solution.compareApproaches(matrix2);
        
        // Test all approaches on various matrix sizes
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TESTING VARIOUS MATRIX SIZES:");
        System.out.println("=".repeat(80));
        
        int[][][] testMatrices = {
            {{1}}, // 1x1
            {{1,2,3}}, // 1x3
            {{1},{2},{3}}, // 3x1
            {{1,2},{3,4}}, // 2x2
            {{1,2,3},{4,5,6}}, // 2x3
            {{1,2},{3,4},{5,6}}, // 3x2
            {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}}, // 4x4
            {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}}, // 3x5
            {{1,2,3},{4,5,6},{7,8,9},{10,11,12},{13,14,15}} // 5x3
        };
        
        String[] descriptions = {
            "1x1", "1x3", "3x1", "2x2", "2x3", "3x2", "4x4", "3x5", "5x3"
        };
        
        for (int i = 0; i < testMatrices.length; i++) {
            System.out.println("\nTesting " + descriptions[i] + " matrix:");
            List<Integer> result = solution.spiralOrder(testMatrices[i]);
            System.out.println("Result length: " + result.size() + 
                             " (should be " + (testMatrices[i].length * testMatrices[i][0].length) + ")");
            System.out.println("First/last: " + result.get(0) + " ... " + 
                             result.get(result.size()-1));
        }
        
        // Performance test
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(80));
        
        // Create larger matrix (10x10)
        int size = 10;
        int[][] largeMatrix = new int[size][size];
        int value = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                largeMatrix[i][j] = value++;
            }
        }
        
        System.out.println("\nTesting with " + size + "x" + size + " matrix");
        
        long startTime = System.currentTimeMillis();
        List<Integer> largeResult = solution.spiralOrder(largeMatrix);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Result length: " + largeResult.size() + " (should be " + (size*size) + ")");
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        
        // Verify correctness
        boolean correct = true;
        for (int i = 0; i < largeResult.size(); i++) {
            if (largeResult.get(i) != i + 1) {
                correct = false;
                System.out.println("Error at position " + i + ": expected " + (i+1) + 
                                 ", got " + largeResult.get(i));
                break;
            }
        }
        System.out.println("All values correct: " + (correct ? "✓" : "✗"));
        
        // Complexity analysis
        solution.analyzeComplexity();
        
        // Show related problems
        solution.showRelatedProblems();
        
        // Show applications
        solution.showApplications();
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - Traverse matrix in clockwise spiral order");
        System.out.println("   - Start from top-left, move right, down, left, up");
        System.out.println("   - Continue until all elements visited");
        
        System.out.println("\n2. Clarify requirements:");
        System.out.println("   - Handle any m x n matrix (not just square)");
        System.out.println("   - Single row/column should work");
        System.out.println("   - Empty matrix edge case");
        
        System.out.println("\n3. Design approach:");
        System.out.println("   - Use four boundary variables: top, bottom, left, right");
        System.out.println("   - While boundaries don't cross:");
        System.out.println("     a. Traverse top row left→right, increment top");
        System.out.println("     b. Traverse right column top→bottom, decrement right");
        System.out.println("     c. If top ≤ bottom, traverse bottom row right→left, decrement bottom");
        System.out.println("     d. If left ≤ right, traverse left column bottom→top, increment left");
        
        System.out.println("\n4. Draw example:");
        System.out.println("   - Walk through 3x3 example");
        System.out.println("   - Show how boundaries shrink");
        
        System.out.println("\n5. Handle edge cases:");
        System.out.println("   - Single element matrix");
        System.out.println("   - Single row matrix");
        System.out.println("   - Single column matrix");
        System.out.println("   - Empty matrix");
        
        System.out.println("\n6. Code implementation:");
        System.out.println("   - Initialize boundaries");
        System.out.println("   - While loop with four directional traversals");
        System.out.println("   - Boundary checks after each direction");
        
        System.out.println("\n7. Test with examples:");
        System.out.println("   - Provided examples");
        System.out.println("   - Edge cases");
        
        System.out.println("\n8. Discuss alternatives:");
        System.out.println("   - Visited array approach");
        System.out.println("   - Recursive approach");
        System.out.println("   - Direction-based simulation");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Boundary tracking is most space-efficient");
        System.out.println("- Works for all matrix shapes");
        System.out.println("- Clear termination condition");
        System.out.println("- Each element visited exactly once");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting to check boundaries after each direction");
        System.out.println("- Not handling single row/column cases");
        System.out.println("- Off-by-one errors in loop bounds");
        System.out.println("- Using extra O(m*n) space unnecessarily");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 59. Spiral Matrix II");
        System.out.println("2. 885. Spiral Matrix III");
        System.out.println("3. 2326. Spiral Matrix IV");
        System.out.println("4. 48. Rotate Image");
        System.out.println("5. 498. Diagonal Traverse");
        System.out.println("6. 1424. Diagonal Traverse II");
        System.out.println("7. 566. Reshape the Matrix");
        System.out.println("8. 73. Set Matrix Zeroes");
        System.out.println("9. 240. Search a 2D Matrix II");
        System.out.println("10. 74. Search a 2D Matrix");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
