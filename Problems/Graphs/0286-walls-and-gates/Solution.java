
## Solution.java

```java
/**
 * 286. Walls and Gates
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are given an m x n grid rooms with:
 * - -1: wall or obstacle
 * - 0: gate  
 * - INF: empty room (2147483647)
 * Fill each empty room with the distance to its nearest gate.
 * 
 * Key Insights:
 * 1. Multi-source BFS starting from all gates
 * 2. BFS naturally finds shortest paths in unweighted grid
 * 3. Only update rooms if we find a shorter distance
 * 4. Walls block movement, gates are starting points
 * 
 * Approach (Multi-source BFS):
 * 1. Find all gates and add them to queue with distance 0
 * 2. Perform BFS, updating empty rooms with current distance
 * 3. Only process rooms that can get smaller distances
 * 4. Stop when queue is empty
 * 
 * Time Complexity: O(m × n)
 * Space Complexity: O(m × n)
 * 
 * Tags: BFS, Matrix, Graph
 */

import java.util.*;

class Solution {
    // Directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private static final int INF = Integer.MAX_VALUE;
    
    /**
     * Approach 1: Multi-source BFS - RECOMMENDED
     * O(m × n) time, O(m × n) space
     */
    public void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0 || rooms[0].length == 0) {
            return;
        }
        
        int m = rooms.length;
        int n = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        
        // Add all gates to the queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        
        // Perform BFS from all gates
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // Check if new position is valid and can be updated
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                    && rooms[newRow][newCol] == INF) {
                    // Update distance and add to queue
                    rooms[newRow][newCol] = rooms[row][col] + 1;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
    }
    
    /**
     * Approach 2: DFS from each gate (Less efficient)
     * O(m² × n²) time in worst case, O(m × n) space
     */
    public void wallsAndGatesDFS(int[][] rooms) {
        if (rooms == null || rooms.length == 0 || rooms[0].length == 0) {
            return;
        }
        
        int m = rooms.length;
        int n = rooms[0].length;
        
        // Perform DFS from each gate
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    dfs(rooms, i, j, 0);
                }
            }
        }
    }
    
    private void dfs(int[][] rooms, int i, int j, int distance) {
        int m = rooms.length;
        int n = rooms[0].length;
        
        // Check bounds and if we found a shorter distance
        if (i < 0 || i >= m || j < 0 || j >= n || rooms[i][j] < distance) {
            return;
        }
        
        // Update current cell if we found a shorter path
        rooms[i][j] = distance;
        
        // Explore all four directions
        for (int[] dir : DIRECTIONS) {
            dfs(rooms, i + dir[0], j + dir[1], distance + 1);
        }
    }
    
    /**
     * Approach 3: BFS with Distance Tracking
     * O(m × n) time, O(m × n) space
     * Explicitly tracks distance in queue
     */
    public void wallsAndGatesWithDistance(int[][] rooms) {
        if (rooms == null || rooms.length == 0 || rooms[0].length == 0) {
            return;
        }
        
        int m = rooms.length;
        int n = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        
        // Add all gates to queue with distance 0
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j, 0});
                }
            }
        }
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            int distance = cell[2];
            
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                    && rooms[newRow][newCol] == INF) {
                    rooms[newRow][newCol] = distance + 1;
                    queue.offer(new int[]{newRow, newCol, distance + 1});
                }
            }
        }
    }
    
    /**
     * Approach 4: BFS with Level-by-Level Processing
     * O(m × n) time, O(m × n) space
     * Processes each level separately
     */
    public void wallsAndGatesLevelBFS(int[][] rooms) {
        if (rooms == null || rooms.length == 0 || rooms[0].length == 0) {
            return;
        }
        
        int m = rooms.length;
        int n = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        
        // Add all gates to queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        
        int distance = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            distance++;
            
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];
                
                for (int[] dir : DIRECTIONS) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                        && rooms[newRow][newCol] == INF) {
                        rooms[newRow][newCol] = distance;
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
        }
    }
    
    /**
     * Approach 5: BFS with Debug Visualization
     * O(m × n) time, O(m × n) space
     * Includes step-by-step visualization
     */
    public void wallsAndGatesDebug(int[][] rooms) {
        if (rooms == null || rooms.length == 0 || rooms[0].length == 0) {
            System.out.println("Empty grid, nothing to process");
            return;
        }
        
        int m = rooms.length;
        int n = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        
        System.out.println("Initial grid:");
        printGrid(rooms);
        
        // Count gates and add to queue
        int gateCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    gateCount++;
                }
            }
        }
        
        System.out.println("Found " + gateCount + " gates");
        
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            System.out.printf("\nStep %d: Processing %d cells%n", step, size);
            
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];
                int currentDistance = rooms[row][col];
                
                System.out.printf("  Processing cell [%d,%d] with distance %d%n", 
                                row, col, currentDistance);
                
                for (int[] dir : DIRECTIONS) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n 
                        && rooms[newRow][newCol] == INF) {
                        rooms[newRow][newCol] = currentDistance + 1;
                        queue.offer(new int[]{newRow, newCol});
                        System.out.printf("    Updated [%d,%d] to distance %d%n", 
                                        newRow, newCol, currentDistance + 1);
                    }
                }
            }
            step++;
            
            if (step <= 3) { // Limit output for large grids
                System.out.println("Grid after step " + step + ":");
                printGrid(rooms);
            }
        }
        
        System.out.println("\nFinal grid:");
        printGrid(rooms);
    }
    
    /**
     * Helper method to print grid for visualization
     */
    private void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == INF) {
                    System.out.print("INF ");
                } else if (grid[i][j] == -1) {
                    System.out.print(" -1 ");
                } else {
                    System.out.printf("%3d ", grid[i][j]);
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Helper method to create test grid
     */
    public int[][] createTestGrid() {
        return new int[][] {
            {INF, -1, 0, INF},
            {INF, INF, INF, -1},
            {INF, -1, INF, -1},
            {0, -1, INF, INF}
        };
    }
    
    /**
     * Helper method to validate solution
     */
    public boolean validateSolution(int[][] rooms, int[][] expected) {
        if (rooms.length != expected.length || rooms[0].length != expected[0].length) {
            return false;
        }
        
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                if (rooms[i][j] != expected[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Walls and Gates:");
        System.out.println("========================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        int[][] rooms1 = solution.createTestGrid();
        int[][] expected1 = {
            {3, -1, 0, 1},
            {2, 2, 1, -1},
            {1, -1, 2, -1},
            {0, -1, 3, 4}
        };
        
        long startTime = System.nanoTime();
        solution.wallsAndGates(rooms1);
        long time1 = System.nanoTime() - startTime;
        
        boolean test1 = solution.validateSolution(rooms1, expected1);
        System.out.println("Multi-source BFS: " + (test1 ? "PASSED" : "FAILED"));
        
        // Test case 2: Single wall
        System.out.println("\nTest 2: Single wall");
        int[][] rooms2 = {{-1}};
        int[][] expected2 = {{-1}};
        solution.wallsAndGates(rooms2);
        boolean test2 = solution.validateSolution(rooms2, expected2);
        System.out.println("Single wall: " + (test2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single gate
        System.out.println("\nTest 3: Single gate");
        int[][] rooms3 = {{0}};
        int[][] expected3 = {{0}};
        solution.wallsAndGates(rooms3);
        boolean test3 = solution.validateSolution(rooms3, expected3);
        System.out.println("Single gate: " + (test3 ? "PASSED" : "FAILED"));
        
        // Test case 4: All empty rooms
        System.out.println("\nTest 4: All empty rooms with one gate");
        int[][] rooms4 = {
            {INF, INF, INF},
            {INF, 0, INF},
            {INF, INF, INF}
        };
        int[][] expected4 = {
            {2, 1, 2},
            {1, 0, 1},
            {2, 1, 2}
        };
        solution.wallsAndGates(rooms4);
        boolean test4 = solution.validateSolution(rooms4, expected4);
        System.out.println("All empty rooms: " + (test4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Multiple gates
        System.out.println("\nTest 5: Multiple gates");
        int[][] rooms5 = {
            {0, INF, INF, -1, 0},
            {INF, -1, INF, -1, INF},
            {INF, INF, 0, INF, INF},
            {-1, -1, INF, -1, INF},
            {0, INF, INF, INF, 0}
        };
        solution.wallsAndGates(rooms5);
        System.out.println("Multiple gates: Processed (visual verification needed)");
        
        // Performance test
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
        int[][] debugRooms = solution.createTestGrid();
        solution.wallsAndGatesDebug(debugRooms);
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Compare different algorithmic approaches
     */
    private static void compareApproaches(Solution solution) {
        int[][] testGrid = solution.createTestGrid();
        
        // Test BFS approach
        int[][] roomsBFS = copyGrid(testGrid);
        long startTime = System.nanoTime();
        solution.wallsAndGates(roomsBFS);
        long bfsTime = System.nanoTime() - startTime;
        
        // Test BFS with distance tracking
        int[][] roomsBFSDist = copyGrid(testGrid);
        startTime = System.nanoTime();
        solution.wallsAndGatesWithDistance(roomsBFSDist);
        long bfsDistTime = System.nanoTime() - startTime;
        
        // Test Level BFS
        int[][] roomsLevelBFS = copyGrid(testGrid);
        startTime = System.nanoTime();
        solution.wallsAndGatesLevelBFS(roomsLevelBFS);
        long levelBFSTime = System.nanoTime() - startTime;
        
        System.out.println("Performance Comparison:");
        System.out.println("  Multi-source BFS: " + bfsTime + " ns");
        System.out.println("  BFS with Distance: " + bfsDistTime + " ns");
        System.out.println("  Level BFS: " + levelBFSTime + " ns");
        
        // Verify all produce same results
        boolean allEqual = solution.validateSolution(roomsBFS, roomsBFSDist) 
                         && solution.validateSolution(roomsBFS, roomsLevelBFS);
        System.out.println("All approaches produce same result: " + (allEqual ? "YES" : "NO"));
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
 * Additional utility class for grid operations
 */
class GridUtils {
    /**
     * Count the number of gates in grid
     */
    public static int countGates(int[][] rooms) {
        int count = 0;
        for (int[] row : rooms) {
            for (int cell : row) {
                if (cell == 0) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Count the number of walls in grid
     */
    public static int countWalls(int[][] rooms) {
        int count = 0;
        for (int[] row : rooms) {
            for (int cell : row) {
                if (cell == -1) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Count the number of empty rooms in grid
     */
    public static int countEmptyRooms(int[][] rooms) {
        int count = 0;
        for (int[] row : rooms) {
            for (int cell : row) {
                if (cell == Integer.MAX_VALUE) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Check if all empty rooms are reachable from gates
     */
    public static boolean allRoomsReachable(int[][] rooms) {
        for (int[] row : rooms) {
            for (int cell : row) {
                if (cell == Integer.MAX_VALUE) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Find maximum distance in the grid
     */
    public static int findMaxDistance(int[][] rooms) {
        int max = 0;
        for (int[] row : rooms) {
            for (int cell : row) {
                if (cell != -1 && cell != Integer.MAX_VALUE && cell > max) {
                    max = cell;
                }
            }
        }
        return max;
    }
}
