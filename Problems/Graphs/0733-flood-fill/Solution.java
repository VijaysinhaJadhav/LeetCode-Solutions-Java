
## Solution.java

```java
/**
 * 733. Flood Fill
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Perform flood fill on an image starting from pixel (sr, sc), changing all 
 * connected pixels of the same color to the new color.
 * 
 * Key Insights:
 * 1. Only fill pixels that are 4-directionally connected and have same original color
 * 2. If new color equals original color, return immediately (avoid infinite recursion)
 * 3. Use DFS (recursive/iterative) or BFS to explore connected components
 * 4. Always check array bounds before accessing elements
 * 
 * Approach (DFS Recursive):
 * 1. Check if current pixel already has new color (base case)
 * 2. Store original color and change current pixel to new color
 * 3. Recursively call on 4-directional neighbors with same original color
 * 
 * Time Complexity: O(m × n)
 * Space Complexity: O(m × n) for recursion stack
 * 
 * Tags: Array, DFS, BFS, Matrix
 */

import java.util.*;

class Solution {
    // Directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    /**
     * Approach 1: DFS Recursive (Recommended)
     * O(m × n) time, O(m × n) space
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        // If starting pixel already has the new color, no need to do anything
        if (image[sr][sc] == color) {
            return image;
        }
        
        int originalColor = image[sr][sc];
        dfs(image, sr, sc, originalColor, color);
        return image;
    }
    
    private void dfs(int[][] image, int row, int col, int originalColor, int newColor) {
        // Check bounds and color condition
        if (row < 0 || row >= image.length || col < 0 || col >= image[0].length 
            || image[row][col] != originalColor) {
            return;
        }
        
        // Change color
        image[row][col] = newColor;
        
        // Explore 4-directional neighbors
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            dfs(image, newRow, newCol, originalColor, newColor);
        }
    }
    
    /**
     * Approach 2: DFS Iterative using Stack
     * O(m × n) time, O(m × n) space
     * Avoids recursion stack overflow for large inputs
     */
    public int[][] floodFillDFSIterative(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) {
            return image;
        }
        
        int originalColor = image[sr][sc];
        int rows = image.length;
        int cols = image[0].length;
        
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{sr, sc});
        
        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int row = current[0];
            int col = current[1];
            
            // Change color
            image[row][col] = color;
            
            // Add valid neighbors to stack
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols 
                    && image[newRow][newCol] == originalColor) {
                    stack.push(new int[]{newRow, newCol});
                }
            }
        }
        
        return image;
    }
    
    /**
     * Approach 3: BFS using Queue
     * O(m × n) time, O(m × n) space
     * Explores level by level
     */
    public int[][] floodFillBFS(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) {
            return image;
        }
        
        int originalColor = image[sr][sc];
        int rows = image.length;
        int cols = image[0].length;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            
            // Change color
            image[row][col] = color;
            
            // Add valid neighbors to queue
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols 
                    && image[newRow][newCol] == originalColor) {
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
        
        return image;
    }
    
    /**
     * Approach 4: DFS with visited set (Alternative)
     * O(m × n) time, O(m × n) space
     * Explicitly tracks visited pixels
     */
    public int[][] floodFillWithVisited(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) {
            return image;
        }
        
        int originalColor = image[sr][sc];
        boolean[][] visited = new boolean[image.length][image[0].length];
        dfsWithVisited(image, sr, sc, originalColor, color, visited);
        return image;
    }
    
    private void dfsWithVisited(int[][] image, int row, int col, int originalColor, 
                               int newColor, boolean[][] visited) {
        if (row < 0 || row >= image.length || col < 0 || col >= image[0].length 
            || visited[row][col] || image[row][col] != originalColor) {
            return;
        }
        
        visited[row][col] = true;
        image[row][col] = newColor;
        
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            dfsWithVisited(image, newRow, newCol, originalColor, newColor, visited);
        }
    }
    
    /**
     * Approach 5: In-place without extra space (if we can modify original color temporarily)
     * O(m × n) time, O(m × n) space for recursion
     * Uses a marker value to avoid visited set
     */
    public int[][] floodFillMarker(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) {
            return image;
        }
        
        int originalColor = image[sr][sc];
        // Use a temporary marker that's not in the color range
        int marker = -1; // Assuming colors are non-negative
        
        dfsMarker(image, sr, sc, originalColor, color, marker);
        return image;
    }
    
    private void dfsMarker(int[][] image, int row, int col, int originalColor, 
                          int newColor, int marker) {
        if (row < 0 || row >= image.length || col < 0 || col >= image[0].length 
            || image[row][col] != originalColor) {
            return;
        }
        
        // Mark current cell temporarily
        image[row][col] = marker;
        
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            dfsMarker(image, newRow, newCol, originalColor, newColor, marker);
        }
        
        // Change to final color after processing neighbors
        image[row][col] = newColor;
    }
    
    /**
     * Helper method to print image grid
     */
    public void printImage(int[][] image) {
        for (int[] row : image) {
            for (int pixel : row) {
                System.out.print(pixel + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Helper method to visualize the flood fill process
     */
    public void visualizeFloodFill(int[][] image, int sr, int sc, int color, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Starting from: (" + sr + ", " + sc + ")");
        System.out.println("Original color: " + image[sr][sc] + ", New color: " + color);
        System.out.println("\nOriginal Image:");
        printImage(image);
        
        int[][] imageCopy = copyImage(image);
        
        if ("DFS Recursive".equals(approach)) {
            visualizeDFSRecursive(imageCopy, sr, sc, color);
        } else if ("BFS".equals(approach)) {
            visualizeBFS(imageCopy, sr, sc, color);
        }
        
        System.out.println("\nFinal Image:");
        printImage(imageCopy);
    }
    
    private void visualizeDFSRecursive(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) {
            System.out.println("No changes needed - already target color");
            return;
        }
        
        int originalColor = image[sr][sc];
        System.out.println("\nDFS Recursive Process:");
        visualizeDFSRecursiveHelper(image, sr, sc, originalColor, color, 0);
    }
    
    private void visualizeDFSRecursiveHelper(int[][] image, int row, int col, 
                                           int originalColor, int newColor, int depth) {
        if (row < 0 || row >= image.length || col < 0 || col >= image[0].length 
            || image[row][col] != originalColor) {
            return;
        }
        
        // Print current step
        String indent = "  ".repeat(depth);
        System.out.println(indent + "Filling (" + row + ", " + col + ") with color " + newColor);
        
        // Change color
        image[row][col] = newColor;
        
        // Explore neighbors
        String[] directionNames = {"UP", "RIGHT", "DOWN", "LEFT"};
        for (int i = 0; i < DIRECTIONS.length; i++) {
            int newRow = row + DIRECTIONS[i][0];
            int newCol = col + DIRECTIONS[i][1];
            System.out.println(indent + "→ Checking " + directionNames[i] + ": (" + newRow + ", " + newCol + ")");
            visualizeDFSRecursiveHelper(image, newRow, newCol, originalColor, newColor, depth + 1);
        }
    }
    
    private void visualizeBFS(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) {
            System.out.println("No changes needed - already target color");
            return;
        }
        
        int originalColor = image[sr][sc];
        int rows = image.length;
        int cols = image[0].length;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        int step = 1;
        
        System.out.println("\nBFS Process:");
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            
            System.out.println("Step " + step++ + ": Filling (" + row + ", " + col + ")");
            
            // Change color
            image[row][col] = color;
            
            // Add valid neighbors to queue
            String[] directionNames = {"UP", "RIGHT", "DOWN", "LEFT"};
            for (int i = 0; i < DIRECTIONS.length; i++) {
                int newRow = row + DIRECTIONS[i][0];
                int newCol = col + DIRECTIONS[i][1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols 
                    && image[newRow][newCol] == originalColor) {
                    queue.offer(new int[]{newRow, newCol});
                    System.out.println("  → Queuing " + directionNames[i] + ": (" + newRow + ", " + newCol + ")");
                }
            }
        }
    }
    
    private int[][] copyImage(int[][] image) {
        int[][] copy = new int[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
            copy[i] = Arrays.copyOf(image[i], image[i].length);
        }
        return copy;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Flood Fill:");
        System.out.println("===================\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: Standard example");
        int[][] image1 = {{1,1,1},{1,1,0},{1,0,1}};
        int sr1 = 1, sc1 = 1, color1 = 2;
        int[][] expected1 = {{2,2,2},{2,2,0},{2,0,1}};
        testImplementation(solution, image1, sr1, sc1, color1, expected1, "DFS Recursive");
        
        // Test case 2: Same color (no changes)
        System.out.println("\nTest 2: Same color (no changes)");
        int[][] image2 = {{0,0,0},{0,0,0}};
        int sr2 = 0, sc2 = 0, color2 = 0;
        int[][] expected2 = {{0,0,0},{0,0,0}};
        testImplementation(solution, image2, sr2, sc2, color2, expected2, "DFS Recursive");
        
        // Test case 3: Single pixel
        System.out.println("\nTest 3: Single pixel");
        int[][] image3 = {{5}};
        int sr3 = 0, sc3 = 0, color3 = 9;
        int[][] expected3 = {{9}};
        testImplementation(solution, image3, sr3, sc3, color3, expected3, "DFS Recursive");
        
        // Test case 4: Corner case
        System.out.println("\nTest 4: Corner case");
        int[][] image4 = {{1,2,3},{4,5,6},{7,8,9}};
        int sr4 = 0, sc4 = 0, color4 = 0;
        int[][] expected4 = {{0,2,3},{4,5,6},{7,8,9}};
        testImplementation(solution, image4, sr4, sc4, color4, expected4, "DFS Recursive");
        
        // Test case 5: Complex shape
        System.out.println("\nTest 5: Complex shape");
        int[][] image5 = {
            {1,1,1,2,2},
            {1,1,1,2,2},
            {1,1,2,2,2},
            {3,3,3,3,3}
        };
        int sr5 = 1, sc5 = 1, color5 = 4;
        int[][] expected5 = {
            {4,4,4,2,2},
            {4,4,4,2,2},
            {4,4,2,2,2},
            {3,3,3,3,3}
        };
        testImplementation(solution, image5, sr5, sc5, color5, expected5, "DFS Recursive");
        
        // Test case 6: Large color value
        System.out.println("\nTest 6: Large color value");
        int[][] image6 = {{255,255,255},{255,255,255}};
        int sr6 = 0, sc6 = 0, color6 = 65535;
        int[][] expected6 = {{65535,65535,65535},{65535,65535,65535}};
        testImplementation(solution, image6, sr6, sc6, color6, expected6, "DFS Recursive");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: DFS RECURSIVE APPROACH");
        System.out.println("=".repeat(70));
        
        explainDFSRecursive(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, int[][] image, int sr, int sc, 
                                         int color, int[][] expected, String approach) {
        int[][] imageCopy = copyImage(image);
        
        long startTime = System.nanoTime();
        int[][] result = null;
        switch (approach) {
            case "DFS Recursive":
                result = solution.floodFill(imageCopy, sr, sc, color);
                break;
            case "DFS Iterative":
                result = solution.floodFillDFSIterative(imageCopy, sr, sc, color);
                break;
            case "BFS":
                result = solution.floodFillBFS(imageCopy, sr, sc, color);
                break;
            case "With Visited":
                result = solution.floodFillWithVisited(imageCopy, sr, sc, color);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        boolean passed = Arrays.deepEquals(result, expected);
        System.out.printf("%s: Time=%,d ns - %s%n",
                approach, time, (passed ? "PASSED" : "FAILED"));
        
        if (!passed) {
            System.out.println("  Expected: " + Arrays.deepToString(expected));
            System.out.println("  Got: " + Arrays.deepToString(result));
        }
        
        // Visualization for interesting cases
        if (passed && image.length <= 4 && image[0].length <= 5) {
            solution.visualizeFloodFill(image, sr, sc, color, approach);
        }
    }
    
    private static int[][] copyImage(int[][] image) {
        int[][] copy = new int[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
            copy[i] = Arrays.copyOf(image[i], image[i].length);
        }
        return copy;
    }
    
    private static void comparePerformance(Solution solution) {
        // Create a large image for performance testing
        int[][] largeImage = createLargeImage(50, 50, 5);
        int sr = 25, sc = 25, color = 10;
        
        System.out.println("Performance test with 50x50 image:");
        
        // Test DFS Recursive
        int[][] img1 = copyImage(largeImage);
        long startTime = System.nanoTime();
        solution.floodFill(img1, sr, sc, color);
        long time1 = System.nanoTime() - startTime;
        
        // Test DFS Iterative
        int[][] img2 = copyImage(largeImage);
        startTime = System.nanoTime();
        solution.floodFillDFSIterative(img2, sr, sc, color);
        long time2 = System.nanoTime() - startTime;
        
        // Test BFS
        int[][] img3 = copyImage(largeImage);
        startTime = System.nanoTime();
        solution.floodFillBFS(img3, sr, sc, color);
        long time3 = System.nanoTime() - startTime;
        
        // Test With Visited
        int[][] img4 = copyImage(largeImage);
        startTime = System.nanoTime();
        solution.floodFillWithVisited(img4, sr, sc, color);
        long time4 = System.nanoTime() - startTime;
        
        System.out.printf("DFS Recursive:  %,12d ns%n", time1);
        System.out.printf("DFS Iterative:  %,12d ns%n", time2);
        System.out.printf("BFS:            %,12d ns%n", time3);
        System.out.printf("With Visited:   %,12d ns%n", time4);
    }
    
    private static int[][] createLargeImage(int rows, int cols, int patternSize) {
        int[][] image = new int[rows][cols];
        Random random = new Random(42);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Create some patterns for realistic flood fill
                if ((i / patternSize + j / patternSize) % 2 == 0) {
                    image[i][j] = 1;
                } else {
                    image[i][j] = 2;
                }
            }
        }
        
        return image;
    }
    
    private static void explainDFSRecursive(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("Flood fill is like pouring paint that spreads to all connected areas");
        System.out.println("of the same color. We start from (sr, sc) and recursively fill all");
        System.out.println("4-directionally connected pixels with the same original color.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Check if current pixel already has new color → return immediately");
        System.out.println("2. Store original color and change current pixel to new color");
        System.out.println("3. For each of the 4 directions (up, right, down, left):");
        System.out.println("   - Calculate new coordinates");
        System.out.println("   - Check if new coordinates are within bounds");
        System.out.println("   - Check if pixel has the original color");
        System.out.println("   - If valid, recursively call flood fill on that pixel");
        
        System.out.println("\nWhy it works:");
        System.out.println("- DFS naturally explores all connected components");
        System.out.println("- The recursion handles the 'spreading' behavior of flood fill");
        System.out.println("- Boundary checks prevent array index errors");
        System.out.println("- Color check ensures we only fill the intended region");
        
        System.out.println("\nExample Walkthrough: [[1,1,1],[1,1,0],[1,0,1]] starting at (1,1)");
        int[][] example = {{1,1,1},{1,1,0},{1,0,1}};
        solution.visualizeFloodFill(example, 1, 1, 2, "DFS Recursive");
        
        System.out.println("\nTime Complexity: O(m × n) - May visit every pixel in worst case");
        System.out.println("Space Complexity: O(m × n) - Recursion stack depth");
        System.out.println("  - For m × n grid, worst-case recursion depth is m × n");
        System.out.println("  - For iterative approaches, space is still O(m × n) for stack/queue");
    }
    
    private static void checkAllImplementations(Solution solution) {
        Object[][][] testCases = {
            {{{1,1,1},{1,1,0},{1,0,1}}, {1,1,2}},  // Standard
            {{{0,0,0},{0,0,0}}, {0,0,0}},           // Same color
            {{{5}}, {0,0,9}},                       // Single pixel
            {{{1,2,3},{4,5,6},{7,8,9}}, {0,0,0}},  // Corner
            {{{1,1,2},{1,1,2},{2,2,2}}, {1,1,3}}   // Complex
        };
        
        int[][][] expected = {
            {{2,2,2},{2,2,0},{2,0,1}},
            {{0,0,0},{0,0,0}},
            {{9}},
            {{0,2,3},{4,5,6},{7,8,9}},
            {{3,3,2},{3,3,2},{2,2,2}}
        };
        
        String[] methods = {
            "DFS Recursive",
            "DFS Iterative", 
            "BFS",
            "With Visited",
            "Marker"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            int[][] image = (int[][]) testCases[i][0];
            int[] params = (int[]) testCases[i][1];
            int sr = params[0], sc = params[1], color = params[2];
            
            System.out.printf("\nTest case %d: start=(%d,%d), color=%d%n",
                    i + 1, sr, sc, color);
            
            int[][][] results = new int[methods.length][][];
            results[0] = solution.floodFill(copyImage(image), sr, sc, color);
            results[1] = solution.floodFillDFSIterative(copyImage(image), sr, sc, color);
            results[2] = solution.floodFillBFS(copyImage(image), sr, sc, color);
            results[3] = solution.floodFillWithVisited(copyImage(image), sr, sc, color);
            results[4] = solution.floodFillMarker(copyImage(image), sr, sc, color);
            
            boolean caseConsistent = true;
            for (int j = 0; j < methods.length; j++) {
                boolean correct = Arrays.deepEquals(results[j], expected[i]);
                System.out.printf("  %-15s: %s%n", methods[j],
                        correct ? "✓" : "✗ (inconsistent)");
                if (!correct) {
                    caseConsistent = false;
                    allConsistent = false;
                }
            }
            
            if (!caseConsistent) {
                System.out.println("  INCONSISTENT RESULTS!");
            }
        }
        
        System.out.println("\nOverall consistency: " + (allConsistent ? "ALL PASSED ✓" : "SOME FAILED ✗"));
        
        // Algorithm comparison table
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON SUMMARY");
        System.out.println("=".repeat(70));
        
        printAlgorithmComparison();
    }
    
    private static void printAlgorithmComparison() {
        System.out.println("\n1. DFS RECURSIVE (RECOMMENDED):");
        System.out.println("   Time: O(m × n) - Visit connected component");
        System.out.println("   Space: O(m × n) - Recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and easy to understand");
        System.out.println("     - Naturally expresses the recursive nature");
        System.out.println("     - Minimal code");
        System.out.println("   Cons:");
        System.out.println("     - Stack overflow risk for very large connected components");
        System.out.println("   Use when: Small to medium grids, interview settings");
        
        System.out.println("\n2. DFS ITERATIVE (STACK):");
        System.out.println("   Time: O(m × n) - Visit connected component");
        System.out.println("   Space: O(m × n) - Stack storage");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack overflow");
        System.out.println("     - Same DFS behavior as recursive");
        System.out.println("   Cons:");
        System.out.println("     - More code than recursive version");
        System.out.println("   Use when: Large connected components, avoiding recursion");
        
        System.out.println("\n3. BFS (QUEUE):");
        System.out.println("   Time: O(m × n) - Visit connected component");
        System.out.println("   Space: O(m × n) - Queue storage");
        System.out.println("   Pros:");
        System.out.println("     - Explores level by level");
        System.out.println("     - No recursion stack overflow");
        System.out.println("   Cons:");
        System.out.println("     - May use more memory for wide components");
        System.out.println("   Use when: Want level-order exploration");
        
        System.out.println("\n4. WITH VISITED SET:");
        System.out.println("   Time: O(m × n) - Visit connected component");
        System.out.println("   Space: O(m × n) - Visited array");
        System.out.println("   Pros:");
        System.out.println("     - Explicit tracking of visited nodes");
        System.out.println("     - Clear separation of state");
        System.out.println("   Cons:");
        System.out.println("     - Extra O(m × n) space for visited array");
        System.out.println("   Use when: Learning, or when explicit state tracking is needed");
        
        System.out.println("\n5. MARKER APPROACH:");
        System.out.println("   Time: O(m × n) - Visit connected component");
        System.out.println("   Space: O(m × n) - Recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - No extra visited array needed");
        System.out.println("     - Clever use of temporary values");
        System.out.println("   Cons:");
        System.out.println("     - Requires available marker value");
        System.out.println("     - More complex logic");
        System.out.println("   Use when: Memory constrained and marker value available");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with recursive DFS - it's the most intuitive");
        System.out.println("2. Mention the important check: if new color == original color");
        System.out.println("3. Explain the 4-directional movement clearly");
        System.out.println("4. Handle boundary checks properly");
        System.out.println("5. Discuss alternative approaches (BFS, iterative DFS)");
        System.out.println("6. Consider edge cases: single pixel, same color, corners");
        System.out.println("=".repeat(70));
    }
}
