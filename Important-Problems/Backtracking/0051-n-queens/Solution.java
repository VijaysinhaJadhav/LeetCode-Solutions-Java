
## Solution.java

```java
/**
 * 51. N-Queens
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * The n-queens puzzle is the problem of placing n queens on an n x n chessboard 
 * such that no two queens attack each other.
 * 
 * Key Insights:
 * 1. Place one queen per row to reduce search space
 * 2. Use backtracking to explore all valid placements
 * 3. Track occupied columns and diagonals for O(1) conflict checking
 * 4. Main diagonal: row - column = constant
 * 5. Anti-diagonal: row + column = constant
 * 
 * Approach (Backtracking with Optimized Validation):
 * 1. Start from row 0, try placing queen in each column
 * 2. Check if current position conflicts with existing queens
 * 3. If valid, place queen and recurse to next row
 * 4. Backtrack if no valid placement in current row
 * 5. Add to result when all queens are placed
 * 
 * Time Complexity: O(n!)
 * Space Complexity: O(n)
 * 
 * Tags: Array, Backtracking, Depth-First Search
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Backtracking with Optimized Validation - RECOMMENDED
     * O(n!) time, O(n) space
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        
        // Initialize chessboard
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        // Track occupied columns and diagonals
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1]; // main diagonals: row - col + (n-1)
        boolean[] diag2 = new boolean[2 * n - 1]; // anti-diagonals: row + col
        
        backtrack(board, 0, cols, diag1, diag2, result);
        return result;
    }
    
    private void backtrack(char[][] board, int row, boolean[] cols, 
                          boolean[] diag1, boolean[] diag2, List<List<String>> result) {
        int n = board.length;
        
        // Base case: all queens placed
        if (row == n) {
            result.add(constructSolution(board));
            return;
        }
        
        // Try placing queen in each column of current row
        for (int col = 0; col < n; col++) {
            // Check if current position is valid
            if (isValidPosition(row, col, cols, diag1, diag2, n)) {
                // Place queen
                board[row][col] = 'Q';
                cols[col] = true;
                diag1[row - col + n - 1] = true; // main diagonal
                diag2[row + col] = true;         // anti-diagonal
                
                // Recurse to next row
                backtrack(board, row + 1, cols, diag1, diag2, result);
                
                // Backtrack
                board[row][col] = '.';
                cols[col] = false;
                diag1[row - col + n - 1] = false;
                diag2[row + col] = false;
            }
        }
    }
    
    private boolean isValidPosition(int row, int col, boolean[] cols, 
                                  boolean[] diag1, boolean[] diag2, int n) {
        // Check column
        if (cols[col]) return false;
        // Check main diagonal (row - col = constant)
        if (diag1[row - col + n - 1]) return false;
        // Check anti-diagonal (row + col = constant)
        if (diag2[row + col]) return false;
        return true;
    }
    
    private List<String> constructSolution(char[][] board) {
        List<String> solution = new ArrayList<>();
        for (char[] row : board) {
            solution.add(new String(row));
        }
        return solution;
    }
    
    /**
     * Approach 2: Backtracking with Direct Validation (No Extra Arrays)
     * O(n! × n) time, O(n²) space for board
     */
    public List<List<String>> solveNQueensDirect(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        backtrackDirect(board, 0, result);
        return result;
    }
    
    private void backtrackDirect(char[][] board, int row, List<List<String>> result) {
        int n = board.length;
        
        if (row == n) {
            result.add(constructSolution(board));
            return;
        }
        
        for (int col = 0; col < n; col++) {
            if (isValidDirect(board, row, col)) {
                board[row][col] = 'Q';
                backtrackDirect(board, row + 1, result);
                board[row][col] = '.';
            }
        }
    }
    
    private boolean isValidDirect(char[][] board, int row, int col) {
        int n = board.length;
        
        // Check column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }
        
        // Check main diagonal (top-left)
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }
        
        // Check anti-diagonal (top-right)
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }
        
        return true;
    }
    
    /**
     * Approach 3: Using Bit Manipulation (Most Efficient)
     * O(n!) time, O(n) space
     */
    public List<List<String>> solveNQueensBit(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        backtrackBit(board, 0, 0, 0, 0, result);
        return result;
    }
    
    private void backtrackBit(char[][] board, int row, int cols, int diag1, int diag2, 
                            List<List<String>> result) {
        int n = board.length;
        
        if (row == n) {
            result.add(constructSolution(board));
            return;
        }
        
        // Get available positions using bitmask
        int available = ((1 << n) - 1) & ~(cols | diag1 | diag2);
        
        while (available != 0) {
            // Get the rightmost available position
            int position = available & -available;
            // Get column index from position
            int col = Integer.bitCount(position - 1);
            
            // Place queen
            board[row][col] = 'Q';
            
            // Recurse with updated constraints
            backtrackBit(board, row + 1, 
                        cols | position, 
                        (diag1 | position) << 1, 
                        (diag2 | position) >> 1, 
                        result);
            
            // Backtrack
            board[row][col] = '.';
            
            // Remove this position from available
            available &= available - 1;
        }
    }
    
    /**
     * Approach 4: Iterative Backtracking
     * O(n!) time, O(n) space
     */
    public List<List<String>> solveNQueensIterative(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        int[] queens = new int[n]; // queens[i] = column of queen in row i
        Arrays.fill(queens, -1);
        
        int row = 0, col = 0;
        while (row >= 0) {
            // Find next valid column in current row
            while (col < n) {
                if (isValidIterative(queens, row, col)) {
                    queens[row] = col;
                    board[row][col] = 'Q';
                    col = 0;
                    break;
                } else {
                    col++;
                }
            }
            
            if (queens[row] == -1) {
                // No valid column found, backtrack
                row--;
                if (row >= 0) {
                    col = queens[row] + 1;
                    board[row][queens[row]] = '.';
                    queens[row] = -1;
                }
            } else {
                // Valid column found
                if (row == n - 1) {
                    // Solution found
                    result.add(constructSolution(board));
                    // Backtrack to find next solution
                    board[row][queens[row]] = '.';
                    queens[row] = -1;
                    row--;
                    if (row >= 0) {
                        col = queens[row] + 1;
                        board[row][queens[row]] = '.';
                        queens[row] = -1;
                    }
                } else {
                    // Move to next row
                    row++;
                }
            }
        }
        
        return result;
    }
    
    private boolean isValidIterative(int[] queens, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col || 
                Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Approach 5: Using Set for Tracking (Easier to Understand)
     * O(n!) time, O(n) space
     */
    public List<List<String>> solveNQueensSet(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        Set<Integer> cols = new HashSet<>();
        Set<Integer> diag1 = new HashSet<>(); // row - col
        Set<Integer> diag2 = new HashSet<>(); // row + col
        
        backtrackSet(board, 0, cols, diag1, diag2, result);
        return result;
    }
    
    private void backtrackSet(char[][] board, int row, Set<Integer> cols,
                            Set<Integer> diag1, Set<Integer> diag2, List<List<String>> result) {
        int n = board.length;
        
        if (row == n) {
            result.add(constructSolution(board));
            return;
        }
        
        for (int col = 0; col < n; col++) {
            int currDiag1 = row - col;
            int currDiag2 = row + col;
            
            if (!cols.contains(col) && !diag1.contains(currDiag1) && !diag2.contains(currDiag2)) {
                // Place queen
                board[row][col] = 'Q';
                cols.add(col);
                diag1.add(currDiag1);
                diag2.add(currDiag2);
                
                // Recurse
                backtrackSet(board, row + 1, cols, diag1, diag2, result);
                
                // Backtrack
                board[row][col] = '.';
                cols.remove(col);
                diag1.remove(currDiag1);
                diag2.remove(currDiag2);
            }
        }
    }
    
    /**
     * Approach 6: Optimized with Column Array
     * O(n!) time, O(n) space
     */
    public List<List<String>> solveNQueensColumnArray(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        
        int[] queens = new int[n]; // queens[row] = column
        Arrays.fill(queens, -1);
        
        backtrackColumnArray(queens, 0, result);
        return result;
    }
    
    private void backtrackColumnArray(int[] queens, int row, List<List<String>> result) {
        int n = queens.length;
        
        if (row == n) {
            result.add(constructSolutionFromArray(queens));
            return;
        }
        
        for (int col = 0; col < n; col++) {
            if (isValidColumnArray(queens, row, col)) {
                queens[row] = col;
                backtrackColumnArray(queens, row + 1, result);
                queens[row] = -1;
            }
        }
    }
    
    private boolean isValidColumnArray(int[] queens, int row, int col) {
        for (int i = 0; i < row; i++) {
            // Check same column or same diagonal
            if (queens[i] == col || Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }
    
    private List<String> constructSolutionFromArray(int[] queens) {
        int n = queens.length;
        List<String> solution = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            solution.add(new String(row));
        }
        
        return solution;
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    public void visualizeNQueens(int n) {
        System.out.println("\nN-Queens Visualization (n = " + n + "):");
        System.out.println("Expected number of solutions: " + getExpectedSolutionCount(n));
        
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];
        boolean[] diag2 = new boolean[2 * n - 1];
        
        System.out.println("\nBacktracking Process:");
        System.out.println("Row | Board State | Action");
        System.out.println("----|-------------|--------");
        
        visualizeBacktrack(board, 0, cols, diag1, diag2, result, 0);
        
        System.out.println("\nTotal solutions found: " + result.size());
    }
    
    private void visualizeBacktrack(char[][] board, int row, boolean[] cols,
                                  boolean[] diag1, boolean[] diag2, 
                                  List<List<String>> result, int depth) {
        int n = board.length;
        
        if (row == n) {
            System.out.printf("%3d | %11s | SOLUTION FOUND%n", row, "Complete");
            result.add(constructSolution(board));
            return;
        }
        
        for (int col = 0; col < n; col++) {
            if (isValidPosition(row, col, cols, diag1, diag2, n)) {
                // Show current attempt
                String boardState = getBoardState(board, row, col);
                System.out.printf("%3d | %11s | Try column %d%n", row, boardState, col);
                
                // Place queen
                board[row][col] = 'Q';
                cols[col] = true;
                diag1[row - col + n - 1] = true;
                diag2[row + col] = true;
                
                // Recurse
                visualizeBacktrack(board, row + 1, cols, diag1, diag2, result, depth + 1);
                
                // Backtrack
                board[row][col] = '.';
                cols[col] = false;
                diag1[row - col + n - 1] = false;
                diag2[row + col] = false;
                
                System.out.printf("%3d | %11s | Backtrack from column %d%n", row, getBoardState(board, row, col), col);
            } else {
                String boardState = getBoardState(board, row, col);
                System.out.printf("%3d | %11s | Skip column %d (conflict)%n", row, boardState, col);
            }
        }
    }
    
    private String getBoardState(char[][] board, int currentRow, int currentCol) {
        int n = board.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i < currentRow) {
                    sb.append(board[i][j] == 'Q' ? 'Q' : '.');
                } else if (i == currentRow && j == currentCol) {
                    sb.append('*'); // Mark current attempt
                } else {
                    sb.append('.');
                }
            }
            if (i < n - 1) sb.append('|');
        }
        return sb.toString();
    }
    
    private int getExpectedSolutionCount(int n) {
        int[] expected = {1, 0, 0, 0, 2, 10, 4, 40, 92, 352};
        return n >= 1 && n <= 9 ? expected[n - 1] : 0;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing N-Queens Solution:");
        System.out.println("==========================");
        
        // Test case 1: n = 4
        System.out.println("\nTest 1: n = 4");
        int n1 = 4;
        int expectedCount1 = 2;
        
        long startTime = System.nanoTime();
        List<List<String>> result1a = solution.solveNQueens(n1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1b = solution.solveNQueensDirect(n1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1c = solution.solveNQueensBit(n1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1d = solution.solveNQueensIterative(n1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1e = solution.solveNQueensSet(n1);
        long time1e = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1f = solution.solveNQueensColumnArray(n1);
        long time1f = System.nanoTime() - startTime;
        
        boolean test1a = result1a.size() == expectedCount1;
        boolean test1b = result1b.size() == expectedCount1;
        boolean test1c = result1c.size() == expectedCount1;
        boolean test1d = result1d.size() == expectedCount1;
        boolean test1e = result1e.size() == expectedCount1;
        boolean test1f = result1f.size() == expectedCount1;
        
        System.out.println("Backtracking+Arrays: " + result1a.size() + " solutions - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Direct Validation: " + result1b.size() + " solutions - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Bit Manipulation: " + result1c.size() + " solutions - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Iterative: " + result1d.size() + " solutions - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Set Tracking: " + result1e.size() + " solutions - " + (test1e ? "PASSED" : "FAILED"));
        System.out.println("Column Array: " + result1f.size() + " solutions - " + (test1f ? "PASSED" : "FAILED"));
        
        // Visualize the process for n=4
        solution.visualizeNQueens(n1);
        
        // Display solutions for n=4
        System.out.println("\nSolutions for n=4:");
        for (int i = 0; i < result1a.size(); i++) {
            System.out.println("Solution " + (i + 1) + ":");
            for (String row : result1a.get(i)) {
                System.out.println("  " + row);
            }
            System.out.println();
        }
        
        // Test case 2: n = 1
        System.out.println("\nTest 2: n = 1");
        int n2 = 1;
        List<List<String>> result2a = solution.solveNQueens(n2);
        System.out.println("n = 1: " + result2a.size() + " solutions - " + 
                         (result2a.size() == 1 ? "PASSED" : "FAILED"));
        
        // Test case 3: n = 2 (no solutions)
        System.out.println("\nTest 3: n = 2");
        int n3 = 2;
        List<List<String>> result3a = solution.solveNQueens(n3);
        System.out.println("n = 2: " + result3a.size() + " solutions - " + 
                         (result3a.size() == 0 ? "PASSED" : "FAILED"));
        
        // Test case 4: n = 3 (no solutions)
        System.out.println("\nTest 4: n = 3");
        int n4 = 3;
        List<List<String>> result4a = solution.solveNQueens(n4);
        System.out.println("n = 3: " + result4a.size() + " solutions - " + 
                         (result4a.size() == 0 ? "PASSED" : "FAILED"));
        
        // Test case 5: n = 5
        System.out.println("\nTest 5: n = 5");
        int n5 = 5;
        List<List<String>> result5a = solution.solveNQueens(n5);
        System.out.println("n = 5: " + result5a.size() + " solutions - " + 
                         (result5a.size() == 10 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison (n = 4)");
        System.out.println("Backtracking+Arrays: " + time1a + " ns");
        System.out.println("Direct Validation: " + time1b + " ns");
        System.out.println("Bit Manipulation: " + time1c + " ns");
        System.out.println("Iterative: " + time1d + " ns");
        System.out.println("Set Tracking: " + time1e + " ns");
        System.out.println("Column Array: " + time1f + " ns");
        
        // Test all approaches produce same number of solutions
        System.out.println("\nTest 7: All approaches consistency");
        boolean allConsistent = result1a.size() == result1b.size() && 
                              result1a.size() == result1c.size() && 
                              result1a.size() == result1d.size() &&
                              result1a.size() == result1e.size() &&
                              result1a.size() == result1f.size();
        System.out.println("All approaches produce same number of solutions: " + allConsistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BACKTRACKING WITH OPTIMIZED VALIDATION EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Place one queen per row and use arrays to track conflicts");
        System.out.println("in columns and diagonals for O(1) validation checks.");
        
        System.out.println("\nDiagonal Properties:");
        System.out.println("1. Main Diagonal (\\): row - column = constant");
        System.out.println("   - Range: -(n-1) to (n-1) -> shift by (n-1) for array index");
        System.out.println("2. Anti-Diagonal (/): row + column = constant");
        System.out.println("   - Range: 0 to 2n-2");
        
        System.out.println("\nBacktracking Process:");
        System.out.println("1. Start from row 0");
        System.out.println("2. For each column, check if position is valid");
        System.out.println("3. If valid, place queen and update conflict arrays");
        System.out.println("4. Recurse to next row");
        System.out.println("5. Backtrack by removing queen and updating arrays");
        System.out.println("6. Add to result when all queens placed");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking with Arrays (RECOMMENDED):");
        System.out.println("   Time: O(n!) - Pruned backtracking");
        System.out.println("   Space: O(n) - Arrays for tracking conflicts");
        System.out.println("   How it works:");
        System.out.println("     - Use boolean arrays for columns and diagonals");
        System.out.println("     - O(1) conflict checking");
        System.out.println("     - Systematic row-by-row placement");
        System.out.println("   Pros:");
        System.out.println("     - Fast conflict checking");
        System.out.println("     - Memory efficient");
        System.out.println("     - Clear implementation");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding diagonal properties");
        System.out.println("   Best for: Interview settings, most use cases");
        
        System.out.println("\n2. Direct Validation:");
        System.out.println("   Time: O(n! × n) - O(n) validation per placement");
        System.out.println("   Space: O(n²) - Board storage");
        System.out.println("   How it works:");
        System.out.println("     - Check conflicts by scanning previous rows");
        System.out.println("     - No extra arrays for tracking");
        System.out.println("   Pros:");
        System.out.println("     - Simple to understand");
        System.out.println("     - No extra memory for tracking");
        System.out.println("   Cons:");
        System.out.println("     - Slower due to repeated scanning");
        System.out.println("   Best for: Learning, small n");
        
        System.out.println("\n3. Bit Manipulation:");
        System.out.println("   Time: O(n!) - Most efficient");
        System.out.println("   Space: O(n) - Only integers for tracking");
        System.out.println("   How it works:");
        System.out.println("     - Use bit masks to represent conflicts");
        System.out.println("     - Bit operations for validation");
        System.out.println("   Pros:");
        System.out.println("     - Fastest implementation");
        System.out.println("     - Minimal memory usage");
        System.out.println("   Cons:");
        System.out.println("     - Complex bit manipulation");
        System.out.println("     - Hard to understand and debug");
        System.out.println("   Best for: Competitive programming, large n");
        
        System.out.println("\n4. Iterative Backtracking:");
        System.out.println("   Time: O(n!) - Same complexity");
        System.out.println("   Space: O(n) - Queen positions array");
        System.out.println("   How it works:");
        System.out.println("     - Use while loop instead of recursion");
        System.out.println("     - Manual stack management");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack overflow");
        System.out.println("     - More control over process");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Harder to understand");
        System.out.println("   Best for: When recursion depth is concern");
        
        System.out.println("\n5. Set Tracking:");
        System.out.println("   Time: O(n!) - Same complexity");
        System.out.println("   Space: O(n) - Sets for tracking");
        System.out.println("   How it works:");
        System.out.println("     - Use HashSet to track conflicts");
        System.out.println("     - More intuitive than arrays");
        System.out.println("   Pros:");
        System.out.println("     - Easy to understand");
        System.out.println("     - Clear conflict tracking");
        System.out.println("   Cons:");
        System.out.println("     - Slightly slower than arrays");
        System.out.println("     - Higher constant factors");
        System.out.println("   Best for: When readability is important");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Number of solutions follows sequence: 1, 0, 0, 2, 10, 4, 40, 92, 352...");
        System.out.println("2. No solutions exist for n = 2, 3");
        System.out.println("3. Time complexity is O(n!) due to backtracking with pruning");
        System.out.println("4. The problem is NP-hard for general n");
        System.out.println("5. For n > 1, solution exists only for n ≠ 2, 3");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("OPTIMIZATION STRATEGIES:");
        System.out.println("1. Conflict Tracking: Use arrays for O(1) checks");
        System.out.println("2. Bit Manipulation: Most efficient for large n");
        System.out.println("3. Early Pruning: Stop when conflict detected");
        System.out.println("4. Row-wise Placement: Reduce search space");
        System.out.println("5. Symmetry Breaking: Exploit board symmetry");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with backtracking + arrays approach");
        System.out.println("2. Explain diagonal properties clearly");
        System.out.println("3. Discuss time/space complexity honestly");
        System.out.println("4. Mention alternative approaches briefly");
        System.out.println("5. Handle edge cases: n=1, n=2, n=3");
        System.out.println("6. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        // Display solution counts for different n
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SOLUTION COUNTS FOR DIFFERENT n:");
        System.out.println("=".repeat(70));
        
        for (int i = 1; i <= 8; i++) {
            List<List<String>> solutions = solution.solveNQueens(i);
            System.out.printf("n = %d: %d solutions%n", i, solutions.size());
        }
        
        System.out.println("\nAll tests completed!");
    }
}
