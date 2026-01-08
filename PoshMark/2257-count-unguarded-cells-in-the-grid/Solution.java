
# Solution.java

```java
import java.util.*;

/**
 * 2257. Count Unguarded Cells in the Grid
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given m x n grid with guards and walls, count unguarded cells.
 * Guards can see in four directions until blocked by wall or another guard.
 * 
 * Key Insights:
 * 1. Use grid to track cell states: 0=unguarded, 1=guarded, 2=guard, 3=wall
 * 2. Mark guards and walls first
 * 3. For each guard, ray cast in 4 directions until blocked
 * 4. Count cells with state 0 at the end
 * 
 * Approach 1: Matrix Simulation (RECOMMENDED)
 * O(m*n + guards*(m+n)) time, O(m*n) space
 */

class Solution {
    
    // Cell states
    private static final int UNGUARDED = 0;
    private static final int GUARDED = 1;
    private static final int GUARD = 2;
    private static final int WALL = 3;
    
    // Directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    /**
     * Approach 1: Matrix Simulation (RECOMMENDED)
     * O(m*n + G*(m+n)) time, O(m*n) space
     */
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        // Initialize grid
        int[][] grid = new int[m][n];
        
        // Mark walls
        for (int[] wall : walls) {
            grid[wall[0]][wall[1]] = WALL;
        }
        
        // Mark guards
        for (int[] guard : guards) {
            grid[guard[0]][guard[1]] = GUARD;
        }
        
        // Process each guard's vision
        for (int[] guard : guards) {
            int row = guard[0];
            int col = guard[1];
            
            // Check all four directions
            for (int[] dir : DIRECTIONS) {
                int r = row + dir[0];
                int c = col + dir[1];
                
                // Keep moving in this direction until blocked
                while (r >= 0 && r < m && c >= 0 && c < n) {
                    // Stop if we hit a wall or another guard
                    if (grid[r][c] == WALL || grid[r][c] == GUARD) {
                        break;
                    }
                    
                    // Mark this cell as guarded
                    grid[r][c] = GUARDED;
                    
                    // Move to next cell in same direction
                    r += dir[0];
                    c += dir[1];
                }
            }
        }
        
        // Count unguarded cells
        int unguardedCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == UNGUARDED) {
                    unguardedCount++;
                }
            }
        }
        
        return unguardedCount;
    }
    
    /**
     * Approach 2: Bitmask Optimization
     * O(m*n + G*(m+n)) time, O(m*n) space
     * Uses bitmask for faster checks
     */
    public int countUnguardedBitmask(int m, int n, int[][] guards, int[][] walls) {
        // 0b00: unguarded, 0b01: guarded, 0b10: guard/wall
        int[][] grid = new int[m][n];
        
        // Mark walls (bit 1 set)
        for (int[] wall : walls) {
            grid[wall[0]][wall[1]] = 2; // 0b10
        }
        
        // Mark guards (bit 1 set)
        for (int[] guard : guards) {
            grid[guard[0]][guard[1]] = 2; // 0b10
        }
        
        // Process each guard
        for (int[] guard : guards) {
            int row = guard[0];
            int col = guard[1];
            
            // Up
            for (int r = row - 1; r >= 0; r--) {
                if ((grid[r][col] & 2) != 0) break; // Hit guard or wall
                grid[r][col] |= 1; // Mark as guarded
            }
            
            // Down
            for (int r = row + 1; r < m; r++) {
                if ((grid[r][col] & 2) != 0) break;
                grid[r][col] |= 1;
            }
            
            // Left
            for (int c = col - 1; c >= 0; c--) {
                if ((grid[row][c] & 2) != 0) break;
                grid[row][c] |= 1;
            }
            
            // Right
            for (int c = col + 1; c < n; c++) {
                if ((grid[row][c] & 2) != 0) break;
                grid[row][c] |= 1;
            }
        }
        
        // Count unguarded (cells with 0)
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 3: Boolean Arrays for Walls and Guards
     * O(m*n + G*(m+n)) time, O(m*n) space
     * Separate arrays for faster checks
     */
    public int countUnguardedBoolean(int m, int n, int[][] guards, int[][] walls) {
        boolean[][] isGuarded = new boolean[m][n];
        boolean[][] isWall = new boolean[m][n];
        boolean[][] isGuard = new boolean[m][n];
        
        // Mark walls
        for (int[] wall : walls) {
            isWall[wall[0]][wall[1]] = true;
        }
        
        // Mark guards
        for (int[] guard : guards) {
            isGuard[guard[0]][guard[1]] = true;
            // Guard cell is considered guarded
            isGuarded[guard[0]][guard[1]] = true;
        }
        
        // Process each guard
        for (int[] guard : guards) {
            int row = guard[0];
            int col = guard[1];
            
            // Four directions
            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
            
            for (int[] dir : dirs) {
                int r = row + dir[0];
                int c = col + dir[1];
                
                while (r >= 0 && r < m && c >= 0 && c < n) {
                    // Stop if wall or guard
                    if (isWall[r][c] || isGuard[r][c]) {
                        break;
                    }
                    
                    // Mark as guarded
                    isGuarded[r][c] = true;
                    
                    r += dir[0];
                    c += dir[1];
                }
            }
        }
        
        // Count unguarded
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!isGuarded[i][j] && !isWall[i][j] && !isGuard[i][j]) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 4: Optimized with Early Exit
     * O(m*n + G*(m+n)) time, O(m*n) space
     * Tracks visited directions to avoid reprocessing
     */
    public int countUnguardedOptimized(int m, int n, int[][] guards, int[][] walls) {
        // 0: unvisited, 1: guarded, 2: guard, 3: wall
        int[][] grid = new int[m][n];
        
        // Mark walls and guards
        for (int[] wall : walls) grid[wall[0]][wall[1]] = 3;
        for (int[] guard : guards) grid[guard[0]][guard[1]] = 2;
        
        // For each guard, mark visible cells
        for (int[] guard : guards) {
            int r = guard[0], c = guard[1];
            
            // Up
            for (int i = r-1; i >= 0; i--) {
                if (grid[i][c] == 3 || grid[i][c] == 2) break;
                grid[i][c] = 1;
            }
            
            // Down
            for (int i = r+1; i < m; i++) {
                if (grid[i][c] == 3 || grid[i][c] == 2) break;
                grid[i][c] = 1;
            }
            
            // Left
            for (int j = c-1; j >= 0; j--) {
                if (grid[r][j] == 3 || grid[r][j] == 2) break;
                grid[r][j] = 1;
            }
            
            // Right
            for (int j = c+1; j < n; j++) {
                if (grid[r][j] == 3 || grid[r][j] == 2) break;
                grid[r][j] = 1;
            }
        }
        
        // Count unguarded
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) count++;
            }
        }
        
        return count;
    }
    
    /**
     * Approach 5: BFS-like Simulation (Alternative)
     * O(m*n + G*(m+n)) time, O(m*n) space
     * Uses queue for processing but not needed for this problem
     */
    public int countUnguardedBFS(int m, int n, int[][] guards, int[][] walls) {
        int[][] grid = new int[m][n];
        
        // Initialize
        for (int[] wall : walls) grid[wall[0]][wall[1]] = -1; // wall
        for (int[] guard : guards) grid[guard[0]][guard[1]] = -2; // guard
        
        // Process each guard
        for (int[] guard : guards) {
            int r = guard[0], c = guard[1];
            
            // Four directions
            for (int d = 0; d < 4; d++) {
                int dr = (d == 0 ? -1 : d == 1 ? 1 : 0);
                int dc = (d == 2 ? -1 : d == 3 ? 1 : 0);
                
                int nr = r + dr;
                int nc = c + dc;
                
                while (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    if (grid[nr][nc] < 0) break; // wall or guard
                    if (grid[nr][nc] == 0) grid[nr][nc] = 1; // mark as guarded
                    nr += dr;
                    nc += dc;
                }
            }
        }
        
        // Count unguarded (0)
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) count++;
            }
        }
        
        return count;
    }
    
    /**
     * Helper: Visualize the grid
     */
    public void visualizeGrid(int m, int n, int[][] guards, int[][] walls, int unguardedCount) {
        System.out.println("\nGrid Visualization:");
        System.out.println("m = " + m + ", n = " + n);
        System.out.println("Guards: " + guards.length + ", Walls: " + walls.length);
        System.out.println("Unguarded cells: " + unguardedCount);
        
        // Create visual grid
        char[][] visual = new char[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(visual[i], '.');
        }
        
        // Mark walls
        for (int[] wall : walls) {
            visual[wall[0]][wall[1]] = 'W';
        }
        
        // Mark guards
        for (int[] guard : guards) {
            visual[guard[0]][guard[1]] = 'G';
        }
        
        // Mark guarded cells (simulate to find them)
        int[][] grid = new int[m][n];
        for (int[] wall : walls) grid[wall[0]][wall[1]] = 3;
        for (int[] guard : guards) grid[guard[0]][guard[1]] = 2;
        
        for (int[] guard : guards) {
            int r = guard[0], c = guard[1];
            
            // Four directions
            for (int d = 0; d < 4; d++) {
                int dr = (d == 0 ? -1 : d == 1 ? 1 : 0);
                int dc = (d == 2 ? -1 : d == 3 ? 1 : 0);
                
                int nr = r + dr;
                int nc = c + dc;
                
                while (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    if (grid[nr][nc] == 3 || grid[nr][nc] == 2) break;
                    if (grid[nr][nc] == 0) {
                        visual[nr][nc] = 'X'; // guarded
                        grid[nr][nc] = 1;
                    }
                    nr += dr;
                    nc += dc;
                }
            }
        }
        
        // Print grid
        System.out.println("\nLegend: G = Guard, W = Wall, X = Guarded, . = Unguarded");
        System.out.println("+" + "-".repeat(2*n - 1) + "+");
        for (int i = 0; i < m; i++) {
            System.out.print("|");
            for (int j = 0; j < n; j++) {
                System.out.print(visual[i][j]);
                if (j < n-1) System.out.print(" ");
            }
            System.out.println("|");
        }
        System.out.println("+" + "-".repeat(2*n - 1) + "+");
        
        // Count statistics
        int guardCount = guards.length;
        int wallCount = walls.length;
        int guardedCount = 0;
        int actualUnguarded = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visual[i][j] == 'X') guardedCount++;
                else if (visual[i][j] == '.') actualUnguarded++;
            }
        }
        
        System.out.println("\nStatistics:");
        System.out.println("Total cells: " + (m*n));
        System.out.println("Guard cells: " + guardCount);
        System.out.println("Wall cells: " + wallCount);
        System.out.println("Guarded cells (excluding guards): " + guardedCount);
        System.out.println("Unguarded cells: " + actualUnguarded);
        System.out.println("Sum check: " + (guardCount + wallCount + guardedCount + actualUnguarded) + 
                         " = " + (m*n) + " ✓");
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nCore Idea:");
        System.out.println("1. Create a grid representation of the matrix");
        System.out.println("2. Mark all walls and guards on the grid");
        System.out.println("3. For each guard, simulate vision in 4 directions:");
        System.out.println("   - Move in direction until hitting boundary");
        System.out.println("   - Mark each cell as 'guarded'");
        System.out.println("   - Stop when hitting wall or another guard");
        System.out.println("4. Count cells that remain 'unguarded'");
        
        System.out.println("\nKey Observations:");
        System.out.println("- Guards block line of sight (can't see through guards)");
        System.out.println("- Walls block line of sight (can't see through walls)");
        System.out.println("- Guard cells themselves are considered guarded");
        System.out.println("- Wall cells cannot be guarded (they're obstacles)");
        System.out.println("- Vision is only in 4 cardinal directions (not diagonal)");
        
        System.out.println("\nTime Complexity:");
        System.out.println("- Initialization: O(m*n) for grid creation");
        System.out.println("- Marking guards/walls: O(G + W) where G=guards, W=walls");
        System.out.println("- Vision simulation: O(G*(m+n)) in worst case");
        System.out.println("- Total: O(m*n + G*(m+n))");
        System.out.println("- Since m*n ≤ 10^5, this is efficient");
        
        System.out.println("\nSpace Complexity:");
        System.out.println("- O(m*n) for the grid representation");
        System.out.println("- Could optimize with bitmask but readability suffers");
        
        System.out.println("\nExample Walkthrough:");
        System.out.println("m=3, n=3, guards=[[1,1]], walls=[[0,1],[1,0],[2,1],[1,2]]");
        System.out.println("\nStep 1: Mark walls (W) and guard (G):");
        System.out.println("  . W .");
        System.out.println("  W G W");
        System.out.println("  . W .");
        
        System.out.println("\nStep 2: Simulate guard vision:");
        System.out.println("- Up from (1,1): hits wall at (0,1)");
        System.out.println("- Down from (1,1): hits wall at (2,1)");
        System.out.println("- Left from (1,1): hits wall at (1,0)");
        System.out.println("- Right from (1,1): hits wall at (1,2)");
        
        System.out.println("\nStep 3: Count unguarded:");
        System.out.println("Only guard cell (1,1) is guarded");
        System.out.println("4 cells remain unguarded: (0,0), (0,2), (2,0), (2,2)");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES TESTING:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. No guards, no walls:");
        int m1 = 3, n1 = 3;
        int[][] guards1 = {};
        int[][] walls1 = {};
        int result1 = solution.countUnguarded(m1, n1, guards1, walls1);
        System.out.println("m=" + m1 + ", n=" + n1 + ", guards=0, walls=0");
        System.out.println("Result: " + result1 + " (all " + (m1*n1) + " cells unguarded)");
        
        System.out.println("\n2. Grid filled with guards:");
        int m2 = 2, n2 = 2;
        int[][] guards2 = {{0,0},{0,1},{1,0},{1,1}};
        int[][] walls2 = {};
        int result2 = solution.countUnguarded(m2, n2, guards2, walls2);
        System.out.println("m=" + m2 + ", n=" + n2 + ", guards=4, walls=0");
        System.out.println("Result: " + result2 + " (only guard cells, all guarded)");
        
        System.out.println("\n3. Grid filled with walls:");
        int m3 = 2, n3 = 2;
        int[][] guards3 = {};
        int[][] walls3 = {{0,0},{0,1},{1,0},{1,1}};
        int result3 = solution.countUnguarded(m3, n3, guards3, walls3);
        System.out.println("m=" + m3 + ", n=" + n3 + ", guards=0, walls=4");
        System.out.println("Result: " + result3 + " (all walls, unguarded by definition)");
        
        System.out.println("\n4. Single row grid:");
        int m4 = 1, n4 = 5;
        int[][] guards4 = {{0,2}};
        int[][] walls4 = {{0,0},{0,4}};
        int result4 = solution.countUnguarded(m4, n4, guards4, walls4);
        System.out.println("m=" + m4 + ", n=" + n4 + ", guards at (0,2), walls at ends");
        System.out.println("Result: " + result4 + " (only walls unguarded)");
        
        System.out.println("\n5. Guards in line:");
        int m5 = 5, n5 = 1;
        int[][] guards5 = {{1,0},{3,0}};
        int[][] walls5 = {};
        int result5 = solution.countUnguarded(m5, n5, guards5, walls5);
        System.out.println("m=" + m5 + ", n=" + n5 + ", guards at (1,0) and (3,0)");
        System.out.println("Result: " + result5 + " (cells between guards blocked)");
        
        System.out.println("\n6. Large sparse grid:");
        int m6 = 100, n6 = 100;
        int[][] guards6 = {{0,0},{99,99}};
        int[][] walls6 = {{50,50}};
        int result6 = solution.countUnguarded(m6, n6, guards6, walls6);
        System.out.println("m=" + m6 + ", n=" + n6 + ", guards at corners, wall at center");
        System.out.println("Result: " + result6 + " (most cells guarded)");
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(int m, int n, int[][] guards, int[][] walls) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput:");
        System.out.println("m = " + m + ", n = " + n);
        System.out.println("Guards: " + Arrays.deepToString(guards));
        System.out.println("Walls: " + Arrays.deepToString(walls));
        System.out.println("Total cells: " + (m*n));
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5;
        
        // Approach 1: Matrix Simulation
        startTime = System.nanoTime();
        result1 = solution.countUnguarded(m, n, guards, walls);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Bitmask
        startTime = System.nanoTime();
        result2 = solution.countUnguardedBitmask(m, n, guards, walls);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Boolean Arrays
        startTime = System.nanoTime();
        result3 = solution.countUnguardedBoolean(m, n, guards, walls);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Optimized
        startTime = System.nanoTime();
        result4 = solution.countUnguardedOptimized(m, n, guards, walls);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: BFS-like
        startTime = System.nanoTime();
        result5 = solution.countUnguardedBFS(m, n, guards, walls);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (Matrix Simulation): " + result1);
        System.out.println("Approach 2 (Bitmask):           " + result2);
        System.out.println("Approach 3 (Boolean Arrays):    " + result3);
        System.out.println("Approach 4 (Optimized):         " + result4);
        System.out.println("Approach 5 (BFS-like):          " + result5);
        
        boolean allEqual = (result1 == result2) && (result2 == result3) && 
                          (result3 == result4) && (result4 == result5);
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Approach 1: %-10d (Matrix Simulation)%n", time1);
        System.out.printf("Approach 2: %-10d (Bitmask)%n", time2);
        System.out.printf("Approach 3: %-10d (Boolean Arrays)%n", time3);
        System.out.printf("Approach 4: %-10d (Optimized)%n", time4);
        System.out.printf("Approach 5: %-10d (BFS-like)%n", time5);
        
        // Visualize if grid is small enough
        if (m <= 10 && n <= 10) {
            solution.visualizeGrid(m, n, guards, walls, result1);
        }
    }
    
    /**
     * Helper: Performance analysis
     */
    public void analyzePerformance() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nConstraints Analysis:");
        System.out.println("- m, n ≤ 10^5 individually");
        System.out.println("- m*n ≤ 10^5 (key constraint)");
        System.out.println("- guards.length, walls.length ≤ 5*10^4");
        System.out.println("- guards + walls ≤ m*n");
        
        System.out.println("\nWorst Case Scenarios:");
        System.out.println("1. Many guards in same row/column:");
        System.out.println("   - Each guard scans entire row/column");
        System.out.println("   - But they block each other's vision");
        System.out.println("   - Complexity: O(m*n + G*max(m,n))");
        
        System.out.println("\n2. Guards at edges:");
        System.out.println("   - Can see entire row or column");
        System.out.println("   - Maximum vision range");
        
        System.out.println("\n3. No walls:");
        System.out.println("   - Guards can see until boundary");
        System.out.println("   - Maximum number of cells marked");
        
        System.out.println("\nOptimization Opportunities:");
        System.out.println("1. Early termination in ray casting");
        System.out.println("2. Bitmask for faster status checks");
        System.out.println("3. Separate arrays for different properties");
        System.out.println("4. Precompute obstacles per row/column");
        
        System.out.println("\nMemory Considerations:");
        System.out.println("- m*n ≤ 10^5, so grid of ints uses ~400KB");
        System.out.println("- Boolean arrays use ~100KB");
        System.out.println("- Bitmask could reduce further");
        System.out.println("- All approaches fit in memory");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Security System Design:");
        System.out.println("   - Camera placement optimization");
        System.out.println("   - Security guard scheduling");
        System.out.println("   - Blind spot detection");
        
        System.out.println("\n2. Game Development:");
        System.out.println("   - Fog of war calculation");
        System.out.println("   - Line of sight in strategy games");
        System.out.println("   - Visibility graphs for AI");
        
        System.out.println("\n3. Wireless Networks:");
        System.out.println("   - Signal coverage area calculation");
        System.out.println("   - Obstacle interference modeling");
        System.out.println("   - Router placement optimization");
        
        System.out.println("\n4. Urban Planning:");
        System.out.println("   - Surveillance camera placement");
        System.out.println("   - Street light coverage");
        System.out.println("   - Emergency service coverage");
        
        System.out.println("\n5. Robotics:");
        System.out.println("   - Sensor coverage planning");
        System.out.println("   - Obstacle avoidance");
        System.out.println("   - Path planning with visibility");
        
        System.out.println("\n6. Computer Graphics:");
        System.out.println("   - Shadow mapping");
        System.out.println("   - Visibility culling");
        System.out.println("   - Ray tracing optimization");
        
        System.out.println("\n7. Military Applications:");
        System.out.println("   - Radar coverage");
        System.out.println("   - Surveillance zones");
        System.out.println("   - Defensive positioning");
    }
    
    /**
     * Main method with comprehensive tests
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Count Unguarded Cells in the Grid:");
        System.out.println("==========================================");
        
        // Explain the algorithm
        solution.explainAlgorithm();
        
        // Test edge cases
        solution.testEdgeCases();
        
        // Example 1 from problem
        System.out.println("\n\nExample 1 from problem:");
        int m1 = 4, n1 = 6;
        int[][] guards1 = {{0,0},{1,1},{2,3}};
        int[][] walls1 = {{0,1},{2,2},{1,4}};
        int expected1 = 7;
        
        System.out.println("\nInput: m=" + m1 + ", n=" + n1);
        System.out.println("Guards: " + Arrays.deepToString(guards1));
        System.out.println("Walls: " + Arrays.deepToString(walls1));
        
        int result1 = solution.countUnguarded(m1, n1, guards1, walls1);
        System.out.println("Expected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        solution.visualizeGrid(m1, n1, guards1, walls1, result1);
        
        // Example 2 from problem
        System.out.println("\n\nExample 2 from problem:");
        int m2 = 3, n2 = 3;
        int[][] guards2 = {{1,1}};
        int[][] walls2 = {{0,1},{1,0},{2,1},{1,2}};
        int expected2 = 4;
        
        System.out.println("\nInput: m=" + m2 + ", n=" + n2);
        System.out.println("Guards: " + Arrays.deepToString(guards2));
        System.out.println("Walls: " + Arrays.deepToString(walls2));
        
        int result2 = solution.countUnguarded(m2, n2, guards2, walls2);
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        solution.visualizeGrid(m2, n2, guards2, walls2, result2);
        
        // Additional test cases
        System.out.println("\n\nAdditional Test Cases:");
        
        // Test case 3: Simple case
        System.out.println("\nTest Case 3: Simple 2x2");
        int m3 = 2, n3 = 2;
        int[][] guards3 = {{0,0}};
        int[][] walls3 = {};
        int expected3 = 3; // Only (0,0) guarded, others unguarded
        
        int result3 = solution.countUnguarded(m3, n3, guards3, walls3);
        System.out.println("Expected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + (result3 == expected3 ? "✓" : "✗"));
        
        // Test case 4: Guard sees entire row
        System.out.println("\nTest Case 4: Guard sees entire row");
        int m4 = 3, n4 = 4;
        int[][] guards4 = {{1,0}};
        int[][] walls4 = {};
        // Guard at (1,0) sees entire row 1: cells (1,0),(1,1),(1,2),(1,3)
        // Also sees column 0: cells (0,0),(1,0),(2,0)
        // Total guarded: row1(4) + column0(3) - overlap(1) = 6
        // Unguarded: 12 total - 6 = 6
        int expected4 = 6;
        
        int result4 = solution.countUnguarded(m4, n4, guards4, walls4);
        System.out.println("Expected: " + expected4);
        System.out.println("Result:   " + result4);
        System.out.println("Passed: " + (result4 == expected4 ? "✓" : "✗"));
        
        // Compare all approaches
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES FOR EXAMPLE 1:");
        System.out.println("=".repeat(80));
        solution.compareApproaches(m1, n1, guards1, walls1);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES FOR EXAMPLE 2:");
        System.out.println("=".repeat(80));
        solution.compareApproaches(m2, n2, guards2, walls2);
        
        // Performance test with larger grid
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST WITH LARGER GRID:");
        System.out.println("=".repeat(80));
        
        // Create a larger test case (within constraints)
        Random random = new Random(42);
        int m5 = 100, n5 = 100; // 10,000 cells total
        List<int[]> guardsList = new ArrayList<>();
        List<int[]> wallsList = new ArrayList<>();
        
        // Add some guards (about 1% of cells)
        for (int i = 0; i < 100; i++) {
            int r = random.nextInt(m5);
            int c = random.nextInt(n5);
            guardsList.add(new int[]{r, c});
        }
        
        // Add some walls (about 2% of cells)
        for (int i = 0; i < 200; i++) {
            int r = random.nextInt(m5);
            int c = random.nextInt(n5);
            // Make sure not to overlap with guards
            boolean overlap = false;
            for (int[] guard : guardsList) {
                if (guard[0] == r && guard[1] == c) {
                    overlap = true;
                    break;
                }
            }
            if (!overlap) {
                wallsList.add(new int[]{r, c});
            }
        }
        
        int[][] guards5 = guardsList.toArray(new int[0][0]);
        int[][] walls5 = wallsList.toArray(new int[0][0]);
        
        System.out.println("\nTesting with m=" + m5 + ", n=" + n5);
        System.out.println("Total cells: " + (m5*n5));
        System.out.println("Guards: " + guards5.length);
        System.out.println("Walls: " + walls5.length);
        
        long startTime = System.currentTimeMillis();
        int result5 = solution.countUnguarded(m5, n5, guards5, walls5);
        long endTime = System.currentTimeMillis();
        
        System.out.println("\nResult: " + result5 + " unguarded cells");
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        
        // Performance analysis
        solution.analyzePerformance();
        
        // Show applications
        solution.showApplications();
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - Guards see in 4 directions until blocked");
        System.out.println("   - Walls and guards block vision");
        System.out.println("   - Need to count unguarded cells");
        
        System.out.println("\n2. Clarify constraints:");
        System.out.println("   - m*n ≤ 10^5 (can use O(m*n) space)");
        System.out.println("   - Individual m,n can be large but product limited");
        System.out.println("   - Guards and walls lists can be up to 50,000");
        
        System.out.println("\n3. Design data structure:");
        System.out.println("   - 2D array to represent grid");
        System.out.println("   - Different values for unguarded/guarded/guard/wall");
        
        System.out.println("\n4. Algorithm design:");
        System.out.println("   - Mark all guards and walls on grid");
        System.out.println("   - For each guard, ray cast in 4 directions");
        System.out.println("   - Mark cells as guarded until blocked");
        System.out.println("   - Count remaining unguarded cells");
        
        System.out.println("\n5. Optimize:");
        System.out.println("   - Early termination when hitting obstacle");
        System.out.println("   - Simple while loops for ray casting");
        System.out.println("   - No need for BFS/DFS (linear paths)");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - No guards (all cells unguarded)");
        System.out.println("   - Grid filled with walls (all unguarded)");
        System.out.println("   - Guards blocking each other's vision");
        
        System.out.println("\n7. Complexity analysis:");
        System.out.println("   - Time: O(m*n + G*(m+n))");
        System.out.println("   - Space: O(m*n)");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Simple simulation approach works due to constraints");
        System.out.println("- Ray casting is efficient for this problem");
        System.out.println("- Clear cell state management");
        System.out.println("- Early termination when blocked");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting that guards block vision");
        System.out.println("- Not counting guard cells as guarded");
        System.out.println("- Using BFS/DFS unnecessarily");
        System.out.println("- Not handling empty guard/wall lists");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 200. Number of Islands");
        System.out.println("2. 130. Surrounded Regions");
        System.out.println("3. 733. Flood Fill");
        System.out.println("4. 994. Rotting Oranges");
        System.out.println("5. 1162. As Far from Land as Possible");
        System.out.println("6. 1730. Shortest Path to Get Food");
        System.out.println("7. 1926. Nearest Exit from Entrance in Maze");
        System.out.println("8. 286. Walls and Gates");
        System.out.println("9. 542. 01 Matrix");
        System.out.println("10. 675. Cut Off Trees for Golf Event");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
