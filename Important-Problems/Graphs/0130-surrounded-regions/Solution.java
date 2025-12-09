
## Solution.java

```java
/**
 * 130. Surrounded Regions
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an m x n matrix board containing 'X' and 'O', capture all regions 
 * that are 4-directionally surrounded by 'X'.
 * Flip all 'O's into 'X's in surrounded regions.
 * 
 * Key Insights:
 * 1. Reverse approach: find non-surrounded regions first
 * 2. 'O's on borders cannot be surrounded
 * 3. DFS/BFS from border 'O's to mark connected non-surrounded regions
 * 4. Two-pass: mark safe regions, then capture surrounded regions
 * 
 * Approach (DFS from Borders):
 * 1. DFS from all border 'O's and mark them as temporary character
 * 2. Flip all remaining 'O's to 'X' (these are surrounded)
 * 3. Restore temporary markers back to 'O'
 * 
 * Time Complexity: O(m × n)
 * Space Complexity: O(m × n) for recursion stack
 * 
 * Tags: DFS, BFS, Matrix, Union Find
 */

import java.util.*;

class Solution {
    // Directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    /**
     * Approach 1: DFS from Borders - RECOMMENDED
     * O(m × n) time, O(m × n) space
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        
        int m = board.length;
        int n = board[0].length;
        
        // Mark non-capturable regions starting from borders
        // First and last columns
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                dfs(board, i, 0);
            }
            if (board[i][n - 1] == 'O') {
                dfs(board, i, n - 1);
            }
        }
        
        // First and last rows
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                dfs(board, 0, j);
            }
            if (board[m - 1][j] == 'O') {
                dfs(board, m - 1, j);
            }
        }
        
        // Process the board: flip surrounded 'O' to 'X', restore marked 'O'
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X'; // Surrounded region
                } else if (board[i][j] == 'T') {
                    board[i][j] = 'O'; // Restore non-surrounded region
                }
            }
        }
    }
    
    private void dfs(char[][] board, int i, int j) {
        int m = board.length;
        int n = board[0].length;
        
        // Check bounds and if current cell is 'O'
        if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] != 'O') {
            return;
        }
        
        // Mark as temporary (non-capturable)
        board[i][j] = 'T';
        
        // Explore all four directions
        for (int[] dir : DIRECTIONS) {
            dfs(board, i + dir[0], j + dir[1]);
        }
    }
    
    /**
     * Approach 2: BFS from Borders
     * O(m × n) time, O(m × n) space
     */
    public void solveBFS(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        
        int m = board.length;
        int n = board[0].length;
        Queue<int[]> queue = new LinkedList<>();
        
        // Add border 'O's to queue
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                queue.offer(new int[]{i, 0});
                board[i][0] = 'T';
            }
            if (board[i][n - 1] == 'O') {
                queue.offer(new int[]{i, n - 1});
                board[i][n - 1] = 'T';
            }
        }
        
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                queue.offer(new int[]{0, j});
                board[0][j] = 'T';
            }
            if (board[m - 1][j] == 'O') {
                queue.offer(new int[]{m - 1, j});
                board[m - 1][j] = 'T';
            }
        }
        
        // BFS to mark all connected 'O's
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int i = cell[0];
            int j = cell[1];
            
            for (int[] dir : DIRECTIONS) {
                int newI = i + dir[0];
                int newJ = j + dir[1];
                
                if (newI >= 0 && newI < m && newJ >= 0 && newJ < n 
                    && board[newI][newJ] == 'O') {
                    board[newI][newJ] = 'T';
                    queue.offer(new int[]{newI, newJ});
                }
            }
        }
        
        // Process the board
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'T') {
                    board[i][j] = 'O';
                }
            }
        }
    }
    
    /**
     * Approach 3: Union Find
     * O(m × n) time, O(m × n) space
     */
    public void solveUnionFind(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        
        int m = board.length;
        int n = board[0].length;
        UnionFind uf = new UnionFind(m * n + 1); // +1 for dummy border node
        int borderNode = m * n;
        
        // Connect border 'O's to dummy border node
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    int index = i * n + j;
                    // If on border, connect to border node
                    if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                        uf.union(index, borderNode);
                    }
                    // Connect to adjacent 'O's
                    if (i > 0 && board[i - 1][j] == 'O') {
                        uf.union(index, (i - 1) * n + j);
                    }
                    if (j > 0 && board[i][j - 1] == 'O') {
                        uf.union(index, i * n + (j - 1));
                    }
                }
            }
        }
        
        // Flip surrounded 'O's to 'X'
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O' && !uf.isConnected(i * n + j, borderNode)) {
                    board[i][j] = 'X';
                }
            }
        }
    }
    
    class UnionFind {
        private int[] parent;
        private int[] rank;
        
        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
        
        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }
    
    /**
     * Approach 4: DFS with Debug Visualization
     * O(m × n) time, O(m × n) space
     */
    public void solveDebug(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            System.out.println("Empty board, nothing to process");
            return;
        }
        
        int m = board.length;
        int n = board[0].length;
        
        System.out.println("Initial board (" + m + "x" + n + "):");
        printBoard(board);
        
        System.out.println("\n=== MARKING NON-SURROUNDED REGIONS ===");
        
        // Mark non-capturable regions from borders
        int markedCount = 0;
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                System.out.printf("Marking from left border [%d,0]%n", i);
                markedCount += dfsDebug(board, i, 0, 1);
            }
            if (board[i][n - 1] == 'O') {
                System.out.printf("Marking from right border [%d,%d]%n", i, n-1);
                markedCount += dfsDebug(board, i, n - 1, 1);
            }
        }
        
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                System.out.printf("Marking from top border [0,%d]%n", j);
                markedCount += dfsDebug(board, 0, j, 1);
            }
            if (board[m - 1][j] == 'O') {
                System.out.printf("Marking from bottom border [%d,%d]%n", m-1, j);
                markedCount += dfsDebug(board, m - 1, j, 1);
            }
        }
        
        System.out.println("\nBoard after marking non-surrounded regions:");
        printBoard(board);
        System.out.println("Marked " + markedCount + " non-surrounded cells");
        
        System.out.println("\n=== CAPTURING SURROUNDED REGIONS ===");
        int capturedCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                    capturedCount++;
                    System.out.printf("Capturing surrounded region at [%d,%d]%n", i, j);
                } else if (board[i][j] == 'T') {
                    board[i][j] = 'O';
                }
            }
        }
        
        System.out.println("\nFinal board:");
        printBoard(board);
        System.out.println("Captured " + capturedCount + " surrounded regions");
    }
    
    private int dfsDebug(char[][] board, int i, int j, int depth) {
        int m = board.length;
        int n = board[0].length;
        
        if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] != 'O') {
            return 0;
        }
        
        String indent = "  ".repeat(depth);
        System.out.printf("%sMarking [%d,%d]%n", indent, i, j);
        
        board[i][j] = 'T';
        int count = 1;
        
        for (int[] dir : DIRECTIONS) {
            count += dfsDebug(board, i + dir[0], j + dir[1], depth + 1);
        }
        
        return count;
    }
    
    /**
     * Helper method to print board
     */
    private void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Helper method to create test board from example
     */
    public char[][] createTestBoard1() {
        return new char[][] {
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'X'},
            {'X', 'X', 'O', 'X'},
            {'X', 'O', 'X', 'X'}
        };
    }
    
    /**
     * Helper method to create single cell board
     */
    public char[][] createTestBoard2() {
        return new char[][] {
            {'X'}
        };
    }
    
    /**
     * Helper method to create all 'O' board
     */
    public char[][] createTestBoard3() {
        return new char[][] {
            {'O', 'O', 'O'},
            {'O', 'O', 'O'},
            {'O', 'O', 'O'}
        };
    }
    
    /**
     * Helper method to create all 'X' board
     */
    public char[][] createTestBoard4() {
        return new char[][] {
            {'X', 'X', 'X'},
            {'X', 'X', 'X'},
            {'X', 'X', 'X'}
        };
    }
    
    /**
     * Helper method to validate solution
     */
    public boolean validateSolution(char[][] board, char[][] expected) {
        if (board.length != expected.length || board[0].length != expected[0].length) {
            return false;
        }
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != expected[i][j]) {
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
        
        System.out.println("Testing Surrounded Regions:");
        System.out.println("===========================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        char[][] board1 = solution.createTestBoard1();
        char[][] expected1 = {
            {'X', 'X', 'X', 'X'},
            {'X', 'X', 'X', 'X'},
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'X', 'X'}
        };
        
        long startTime = System.nanoTime();
        solution.solve(board1);
        long time1 = System.nanoTime() - startTime;
        
        boolean test1 = solution.validateSolution(board1, expected1);
        System.out.println("DFS from Borders: " + (test1 ? "PASSED" : "FAILED"));
        
        // Test case 2: Single cell 'X'
        System.out.println("\nTest 2: Single cell 'X'");
        char[][] board2 = solution.createTestBoard2();
        char[][] expected2 = {{'X'}};
        solution.solve(board2);
        boolean test2 = solution.validateSolution(board2, expected2);
        System.out.println("Single cell 'X': " + (test2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single cell 'O' (should remain 'O' as it's on border)
        System.out.println("\nTest 3: Single cell 'O'");
        char[][] board3 = {{'O'}};
        char[][] expected3 = {{'O'}};
        solution.solve(board3);
        boolean test3 = solution.validateSolution(board3, expected3);
        System.out.println("Single cell 'O': " + (test3 ? "PASSED" : "FAILED"));
        
        // Test case 4: All 'O' board (all should remain 'O' as they're connected to border)
        System.out.println("\nTest 4: All 'O' board");
        char[][] board4 = solution.createTestBoard3();
        char[][] expected4 = {
            {'O', 'O', 'O'},
            {'O', 'O', 'O'},
            {'O', 'O', 'O'}
        };
        solution.solve(board4);
        boolean test4 = solution.validateSolution(board4, expected4);
        System.out.println("All 'O' board: " + (test4 ? "PASSED" : "FAILED"));
        
        // Test case 5: All 'X' board
        System.out.println("\nTest 5: All 'X' board");
        char[][] board5 = solution.createTestBoard4();
        char[][] expected5 = {
            {'X', 'X', 'X'},
            {'X', 'X', 'X'},
            {'X', 'X', 'X'}
        };
        solution.solve(board5);
        boolean test5 = solution.validateSolution(board5, expected5);
        System.out.println("All 'X' board: " + (test5 ? "PASSED" : "FAILED"));
        
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
        char[][] debugBoard = solution.createTestBoard1();
        solution.solveDebug(debugBoard);
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Compare different algorithmic approaches
     */
    private static void compareApproaches(Solution solution) {
        char[][] testBoard = solution.createTestBoard1();
        
        // Test DFS approach
        char[][] board1 = copyBoard(testBoard);
        long startTime = System.nanoTime();
        solution.solve(board1);
        long dfsTime = System.nanoTime() - startTime;
        
        // Test BFS approach
        char[][] board2 = copyBoard(testBoard);
        startTime = System.nanoTime();
        solution.solveBFS(board2);
        long bfsTime = System.nanoTime() - startTime;
        
        // Test Union Find approach
        char[][] board3 = copyBoard(testBoard);
        startTime = System.nanoTime();
        solution.solveUnionFind(board3);
        long ufTime = System.nanoTime() - startTime;
        
        System.out.println("Performance Comparison:");
        System.out.println("  DFS: " + dfsTime + " ns");
        System.out.println("  BFS: " + bfsTime + " ns");
        System.out.println("  Union Find: " + ufTime + " ns");
        
        // Verify all produce same results
        char[][] expected = {
            {'X', 'X', 'X', 'X'},
            {'X', 'X', 'X', 'X'},
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'X', 'X'}
        };
        
        boolean dfsValid = solution.validateSolution(board1, expected);
        boolean bfsValid = solution.validateSolution(board2, expected);
        boolean ufValid = solution.validateSolution(board3, expected);
        
        System.out.println("DFS produces correct result: " + (dfsValid ? "YES" : "NO"));
        System.out.println("BFS produces correct result: " + (bfsValid ? "YES" : "NO"));
        System.out.println("Union Find produces correct result: " + (ufValid ? "YES" : "NO"));
    }
    
    /**
     * Create a deep copy of board
     */
    private static char[][] copyBoard(char[][] board) {
        char[][] copy = new char[board.length][];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }
}

/**
 * Additional utility class for board operations
 */
class BoardUtils {
    /**
     * Count number of 'O's in board
     */
    public static int countO(char[][] board) {
        int count = 0;
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == 'O') {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Count number of 'X's in board
     */
    public static int countX(char[][] board) {
        int count = 0;
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == 'X') {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Check if a cell is on border
     */
    public static boolean isBorderCell(int i, int j, int m, int n) {
        return i == 0 || i == m - 1 || j == 0 || j == n - 1;
    }
    
    /**
     * Check if a region is completely surrounded
     */
    public static boolean isRegionSurrounded(char[][] board, int startI, int startJ) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        
        queue.offer(new int[]{startI, startJ});
        visited[startI][startJ] = true;
        
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int i = cell[0];
            int j = cell[1];
            
            // If any cell in the region is on border, the region is not surrounded
            if (isBorderCell(i, j, m, n)) {
                return false;
            }
            
            for (int[] dir : directions) {
                int newI = i + dir[0];
                int newJ = j + dir[1];
                
                if (newI >= 0 && newI < m && newJ >= 0 && newJ < n 
                    && board[newI][newJ] == 'O' && !visited[newI][newJ]) {
                    visited[newI][newJ] = true;
                    queue.offer(new int[]{newI, newJ});
                }
            }
        }
        
        return true;
    }
    
    /**
     * Get all border 'O' positions
     */
    public static List<int[]> getBorderO(char[][] board) {
        List<int[]> result = new ArrayList<>();
        int m = board.length;
        int n = board[0].length;
        
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                result.add(new int[]{i, 0});
            }
            if (board[i][n - 1] == 'O') {
                result.add(new int[]{i, n - 1});
            }
        }
        
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                result.add(new int[]{0, j});
            }
            if (board[m - 1][j] == 'O') {
                result.add(new int[]{m - 1, j});
            }
        }
        
        return result;
    }
}
