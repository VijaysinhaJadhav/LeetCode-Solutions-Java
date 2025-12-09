
## Solution.java

```java
/**
 * 417. Pacific Atlantic Water Flow
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an m x n matrix of heights, find cells that can flow to both Pacific and Atlantic oceans.
 * Pacific touches left/top edges, Atlantic touches right/bottom edges.
 * Water flows to equal or lower heights.
 * 
 * Key Insights:
 * 1. Reverse flow: start from oceans and flow uphill
 * 2. Two separate DFS/BFS: one from Pacific, one from Atlantic  
 * 3. Find intersection of cells reachable from both oceans
 * 4. Water can flow to neighboring cells with equal or lower height
 * 
 * Approach (DFS from Ocean Edges):
 * 1. Create two boolean matrices for Pacific and Atlantic reachability
 * 2. DFS from all Pacific edges (first row, first column)
 * 3. DFS from all Atlantic edges (last row, last column)
 * 4. Find cells that are reachable from both oceans
 * 
 * Time Complexity: O(m × n)
 * Space Complexity: O(m × n)
 * 
 * Tags: DFS, BFS, Matrix, Graph
 */

import java.util.*;

class Solution {
    // Directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    /**
     * Approach 1: DFS from Ocean Edges - RECOMMENDED
     * O(m × n) time, O(m × n) space
     */
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return result;
        }
        
        int m = heights.length;
        int n = heights[0].length;
        
        // Matrices to track reachability
        boolean[][] pacificReachable = new boolean[m][n];
        boolean[][] atlanticReachable = new boolean[m][n];
        
        // DFS from Pacific edges (first row and first column)
        for (int i = 0; i < m; i++) {
            dfs(heights, pacificReachable, i, 0, Integer.MIN_VALUE); // first column
        }
        for (int j = 0; j < n; j++) {
            dfs(heights, pacificReachable, 0, j, Integer.MIN_VALUE); // first row
        }
        
        // DFS from Atlantic edges (last row and last column)
        for (int i = 0; i < m; i++) {
            dfs(heights, atlanticReachable, i, n - 1, Integer.MIN_VALUE); // last column
        }
        for (int j = 0; j < n; j++) {
            dfs(heights, atlanticReachable, m - 1, j, Integer.MIN_VALUE); // last row
        }
        
        // Find cells reachable from both oceans
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacificReachable[i][j] && atlanticReachable[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        
        return result;
    }
    
    private void dfs(int[][] heights, boolean[][] reachable, int i, int j, int prevHeight) {
        int m = heights.length;
        int n = heights[0].length;
        
        // Check bounds, if already visited, or if current height is lower than previous
        // (since we're flowing uphill from ocean)
        if (i < 0 || i >= m || j < 0 || j >= n || reachable[i][j] || heights[i][j] < prevHeight) {
            return;
        }
        
        // Mark as reachable
        reachable[i][j] = true;
        
        // Explore all four directions
        for (int[] dir : DIRECTIONS) {
            dfs(heights, reachable, i + dir[0], j + dir[1], heights[i][j]);
        }
    }
    
    /**
     * Approach 2: BFS from Ocean Edges
     * O(m × n) time, O(m × n) space
     */
    public List<List<Integer>> pacificAtlanticBFS(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return result;
        }
        
        int m = heights.length;
        int n = heights[0].length;
        
        boolean[][] pacificReachable = new boolean[m][n];
        boolean[][] atlanticReachable = new boolean[m][n];
        
        Queue<int[]> pacificQueue = new LinkedList<>();
        Queue<int[]> atlanticQueue = new LinkedList<>();
        
        // Add Pacific edges to queue
        for (int i = 0; i < m; i++) {
            pacificQueue.offer(new int[]{i, 0});
            pacificReachable[i][0] = true;
        }
        for (int j = 0; j < n; j++) {
            pacificQueue.offer(new int[]{0, j});
            pacificReachable[0][j] = true;
        }
        
        // Add Atlantic edges to queue
        for (int i = 0; i < m; i++) {
            atlanticQueue.offer(new int[]{i, n - 1});
            atlanticReachable[i][n - 1] = true;
        }
        for (int j = 0; j < n; j++) {
            atlanticQueue.offer(new int[]{m - 1, j});
            atlanticReachable[m - 1][j] = true;
        }
        
        // BFS for Pacific
        bfs(heights, pacificQueue, pacificReachable);
        
        // BFS for Atlantic
        bfs(heights, atlanticQueue, atlanticReachable);
        
        // Find intersection
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacificReachable[i][j] && atlanticReachable[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        
        return result;
    }
    
    private void bfs(int[][] heights, Queue<int[]> queue, boolean[][] reachable) {
        int m = heights.length;
        int n = heights[0].length;
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int i = cell[0];
            int j = cell[1];
            int currentHeight = heights[i][j];
            
            for (int[] dir : DIRECTIONS) {
                int newI = i + dir[0];
                int newJ = j + dir[1];
                
                if (newI >= 0 && newI < m && newJ >= 0 && newJ < n 
                    && !reachable[newI][newJ] && heights[newI][newJ] >= currentHeight) {
                    reachable[newI][newJ] = true;
                    queue.offer(new int[]{newI, newJ});
                }
            }
        }
    }
    
    /**
     * Approach 3: DFS with Single Matrix (Space Optimized)
     * O(m × n) time, O(m × n) space
     * Uses integer matrix to track both oceans
     */
    public List<List<Integer>> pacificAtlanticSingleMatrix(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return result;
        }
        
        int m = heights.length;
        int n = heights[0].length;
        
        // 0: unreachable, 1: Pacific, 2: Atlantic, 3: Both
        int[][] reachable = new int[m][n];
        
        // DFS from Pacific edges
        for (int i = 0; i < m; i++) {
            dfsSingle(heights, reachable, i, 0, Integer.MIN_VALUE, 1);
        }
        for (int j = 0; j < n; j++) {
            dfsSingle(heights, reachable, 0, j, Integer.MIN_VALUE, 1);
        }
        
        // DFS from Atlantic edges
        for (int i = 0; i < m; i++) {
            dfsSingle(heights, reachable, i, n - 1, Integer.MIN_VALUE, 2);
        }
        for (int j = 0; j < n; j++) {
            dfsSingle(heights, reachable, m - 1, j, Integer.MIN_VALUE, 2);
        }
        
        // Find cells marked as reachable by both (value 3)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (reachable[i][j] == 3) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        
        return result;
    }
    
    private void dfsSingle(int[][] heights, int[][] reachable, int i, int j, int prevHeight, int ocean) {
        int m = heights.length;
        int n = heights[0].length;
        
        if (i < 0 || i >= m || j < 0 || j >= n || heights[i][j] < prevHeight) {
            return;
        }
        
        // If already visited for this ocean, return
        if ((reachable[i][j] & ocean) == ocean) {
            return;
        }
        
        // Mark as reachable for this ocean
        reachable[i][j] |= ocean;
        
        for (int[] dir : DIRECTIONS) {
            dfsSingle(heights, reachable, i + dir[0], j + dir[1], heights[i][j], ocean);
        }
    }
    
    /**
     * Approach 4: DFS with Debug Visualization
     * O(m × n) time, O(m × n) space
     * Includes step-by-step visualization
     */
    public List<List<Integer>> pacificAtlanticDebug(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            System.out.println("Empty grid, returning empty list");
            return result;
        }
        
        int m = heights.length;
        int n = heights[0].length;
        
        System.out.println("Grid size: " + m + " x " + n);
        System.out.println("Initial heights:");
        printGrid(heights);
        
        boolean[][] pacificReachable = new boolean[m][n];
        boolean[][] atlanticReachable = new boolean[m][n];
        
        System.out.println("\n=== PACIFIC OCEAN REACHABILITY ===");
        System.out.println("Starting DFS from Pacific edges (first row and first column):");
        
        // Pacific DFS
        for (int i = 0; i < m; i++) {
            System.out.printf("  Starting from [%d,0] (height=%d)%n", i, heights[i][0]);
            dfsDebug(heights, pacificReachable, i, 0, Integer.MIN_VALUE, "Pacific");
        }
        for (int j = 0; j < n; j++) {
            System.out.printf("  Starting from [0,%d] (height=%d)%n", j, heights[0][j]);
            dfsDebug(heights, pacificReachable, 0, j, Integer.MIN_VALUE, "Pacific");
        }
        
        System.out.println("\nPacific reachable cells:");
        printBooleanGrid(pacificReachable);
        
        System.out.println("\n=== ATLANTIC OCEAN REACHABILITY ===");
        System.out.println("Starting DFS from Atlantic edges (last row and last column):");
        
        // Atlantic DFS
        for (int i = 0; i < m; i++) {
            System.out.printf("  Starting from [%d,%d] (height=%d)%n", i, n-1, heights[i][n-1]);
            dfsDebug(heights, atlanticReachable, i, n - 1, Integer.MIN_VALUE, "Atlantic");
        }
        for (int j = 0; j < n; j++) {
            System.out.printf("  Starting from [%d,%d] (height=%d)%n", m-1, j, heights[m-1][j]);
            dfsDebug(heights, atlanticReachable, m - 1, j, Integer.MIN_VALUE, "Atlantic");
        }
        
        System.out.println("\nAtlantic reachable cells:");
        printBooleanGrid(atlanticReachable);
        
        // Find intersection
        System.out.println("\n=== FINAL RESULT ===");
        System.out.println("Cells reachable from both oceans:");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacificReachable[i][j] && atlanticReachable[i][j]) {
                    result.add(Arrays.asList(i, j));
                    System.out.printf("  [%d,%d] (height=%d)%n", i, j, heights[i][j]);
                }
            }
        }
        
        return result;
    }
    
    private void dfsDebug(int[][] heights, boolean[][] reachable, int i, int j, int prevHeight, String ocean) {
        int m = heights.length;
        int n = heights[0].length;
        
        if (i < 0 || i >= m || j < 0 || j >= n || reachable[i][j] || heights[i][j] < prevHeight) {
            if (i >= 0 && i < m && j >= 0 && j < n) {
                if (reachable[i][j]) {
                    System.out.printf("    [%d,%d] already visited for %s%n", i, j, ocean);
                } else {
                    System.out.printf("    [%d,%d] height %d < previous %d, stopping%n", 
                                    i, j, heights[i][j], prevHeight);
                }
            }
            return;
        }
        
        System.out.printf("    Visiting [%d,%d] for %s (height=%d, prev=%d)%n", 
                        i, j, ocean, heights[i][j], prevHeight);
        
        reachable[i][j] = true;
        
        for (int[] dir : DIRECTIONS) {
            dfsDebug(heights, reachable, i + dir[0], j + dir[1], heights[i][j], ocean);
        }
    }
    
    /**
     * Helper method to print grid
     */
    private void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.printf("%3d ", grid[i][j]);
            }
            System.out.println();
        }
    }
    
    /**
     * Helper method to print boolean grid
     */
    private void printBooleanGrid(boolean[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] ? " T " : " . ");
            }
            System.out.println();
        }
    }
    
    /**
     * Helper method to create test grid from example
     */
    public int[][] createTestGrid1() {
        return new int[][] {
            {1, 2, 2, 3, 5},
            {3, 2, 3, 4, 4},
            {2, 4, 5, 3, 1},
            {6, 7, 1, 4, 5},
            {5, 1, 1, 2, 4}
        };
    }
    
    /**
     * Helper method to create single cell grid
     */
    public int[][] createTestGrid2() {
        return new int[][] {
            {1}
        };
    }
    
    /**
     * Helper method to create simple 2x2 grid
     */
    public int[][] createTestGrid3() {
        return new int[][] {
            {1, 2},
            {3, 4}
        };
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Pacific Atlantic Water Flow:");
        System.out.println("====================================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        int[][] grid1 = solution.createTestGrid1();
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(0, 4), Arrays.asList(1, 3), Arrays.asList(1, 4),
            Arrays.asList(2, 2), Arrays.asList(3, 0), Arrays.asList(3, 1),
            Arrays.asList(4, 0)
        );
        
        long startTime = System.nanoTime();
        List<List<Integer>> result1 = solution.pacificAtlantic(grid1);
        long time1 = System.nanoTime() - startTime;
        
        boolean test1 = areResultEqual(result1, expected1);
        System.out.println("DFS from Ocean Edges: " + (test1 ? "PASSED" : "FAILED"));
        System.out.println("Result: " + result1);
        
        // Test case 2: Single cell
        System.out.println("\nTest 2: Single cell");
        int[][] grid2 = solution.createTestGrid2();
        List<List<Integer>> result2 = solution.pacificAtlantic(grid2);
        boolean test2 = result2.size() == 1 && result2.get(0).get(0) == 0 && result2.get(0).get(1) == 0;
        System.out.println("Single cell: " + (test2 ? "PASSED" : "FAILED"));
        
        // Test case 3: 2x2 grid
        System.out.println("\nTest 3: 2x2 grid");
        int[][] grid3 = solution.createTestGrid3();
        List<List<Integer>> result3 = solution.pacificAtlantic(grid3);
        System.out.println("2x2 grid result: " + result3);
        
        // Performance
        System.out.println("\nPerformance: " + time1 + " ns");
        
        // Compare different approaches
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING DIFFERENT APPROACHES:");
        System.out.println("=".repeat(70));
        
        compareApproaches(solution);
        
        // Debug version
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DEBUG VERSION OUTPUT:");
        System.out.println("=".repeat(70));
        int[][] debugGrid = solution.createTestGrid1();
        solution.pacificAtlanticDebug(debugGrid);
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Compare different algorithmic approaches
     */
    private static void compareApproaches(Solution solution) {
        int[][] testGrid = solution.createTestGrid1();
        
        // Test DFS approach
        int[][] grid1 = copyGrid(testGrid);
        long startTime = System.nanoTime();
        List<List<Integer>> result1 = solution.pacificAtlantic(grid1);
        long dfsTime = System.nanoTime() - startTime;
        
        // Test BFS approach
        int[][] grid2 = copyGrid(testGrid);
        startTime = System.nanoTime();
        List<List<Integer>> result2 = solution.pacificAtlanticBFS(grid2);
        long bfsTime = System.nanoTime() - startTime;
        
        // Test single matrix approach
        int[][] grid3 = copyGrid(testGrid);
        startTime = System.nanoTime();
        List<List<Integer>> result3 = solution.pacificAtlanticSingleMatrix(grid3);
        long singleTime = System.nanoTime() - startTime;
        
        System.out.println("Performance Comparison:");
        System.out.println("  DFS: " + dfsTime + " ns -> " + result1.size() + " cells");
        System.out.println("  BFS: " + bfsTime + " ns -> " + result2.size() + " cells");
        System.out.println("  Single Matrix: " + singleTime + " ns -> " + result3.size() + " cells");
        
        // Verify all produce same results
        boolean allEqual = areResultEqual(result1, result2) && areResultEqual(result1, result3);
        System.out.println("All approaches produce same result: " + (allEqual ? "YES" : "NO"));
    }
    
    /**
     * Check if two results are equal (order doesn't matter)
     */
    private static boolean areResultEqual(List<List<Integer>> result1, List<List<Integer>> result2) {
        if (result1.size() != result2.size()) {
            return false;
        }
        
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        
        for (List<Integer> cell : result1) {
            set1.add(cell.get(0) + "," + cell.get(1));
        }
        for (List<Integer> cell : result2) {
            set2.add(cell.get(0) + "," + cell.get(1));
        }
        
        return set1.equals(set2);
    }
    
    /**
     * Create a deep copy of grid
     */
    private static int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }
}

/**
 * Additional utility class for water flow operations
 */
class WaterFlowUtils {
    /**
     * Check if a cell can flow to Pacific Ocean
     */
    public static boolean canFlowToPacific(int[][] heights, int i, int j) {
        int m = heights.length;
        int n = heights[0].length;
        boolean[][] visited = new boolean[m][n];
        return dfsToOcean(heights, visited, i, j, heights[i][j], true);
    }
    
    /**
     * Check if a cell can flow to Atlantic Ocean
     */
    public static boolean canFlowToAtlantic(int[][] heights, int i, int j) {
        int m = heights.length;
        int n = heights[0].length;
        boolean[][] visited = new boolean[m][n];
        return dfsToOcean(heights, visited, i, j, heights[i][j], false);
    }
    
    private static boolean dfsToOcean(int[][] heights, boolean[][] visited, int i, int j, int prevHeight, boolean isPacific) {
        int m = heights.length;
        int n = heights[0].length;
        
        // Check if reached ocean
        if ((isPacific && (i == 0 || j == 0)) || (!isPacific && (i == m - 1 || j == n - 1))) {
            return true;
        }
        
        if (visited[i][j]) {
            return false;
        }
        
        visited[i][j] = true;
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        for (int[] dir : directions) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            
            if (newI >= 0 && newI < m && newJ >= 0 && newJ < n 
                && !visited[newI][newJ] && heights[newI][newJ] <= prevHeight) {
                if (dfsToOcean(heights, visited, newI, newJ, heights[newI][newJ], isPacific)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Count cells reachable from Pacific Ocean
     */
    public static int countPacificReachable(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        boolean[][] reachable = new boolean[m][n];
        
        for (int i = 0; i < m; i++) {
            dfs(heights, reachable, i, 0, Integer.MIN_VALUE);
        }
        for (int j = 0; j < n; j++) {
            dfs(heights, reachable, 0, j, Integer.MIN_VALUE);
        }
        
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (reachable[i][j]) count++;
            }
        }
        return count;
    }
    
    /**
     * Count cells reachable from Atlantic Ocean
     */
    public static int countAtlanticReachable(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        boolean[][] reachable = new boolean[m][n];
        
        for (int i = 0; i < m; i++) {
            dfs(heights, reachable, i, n - 1, Integer.MIN_VALUE);
        }
        for (int j = 0; j < n; j++) {
            dfs(heights, reachable, m - 1, j, Integer.MIN_VALUE);
        }
        
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (reachable[i][j]) count++;
            }
        }
        return count;
    }
    
    private static void dfs(int[][] heights, boolean[][] reachable, int i, int j, int prevHeight) {
        int m = heights.length;
        int n = heights[0].length;
        
        if (i < 0 || i >= m || j < 0 || j >= n || reachable[i][j] || heights[i][j] < prevHeight) {
            return;
        }
        
        reachable[i][j] = true;
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        for (int[] dir : directions) {
            dfs(heights, reachable, i + dir[0], j + dir[1], heights[i][j]);
        }
    }
}
