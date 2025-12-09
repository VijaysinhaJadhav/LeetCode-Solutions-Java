
## Solution.java

```java
/**
 * 79. Word Search
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an m x n grid of characters board and a string word, 
 * return true if word exists in the grid.
 * 
 * Key Insights:
 * 1. Use DFS/backtracking to explore all possible paths
 * 2. Mark cells as visited during current path exploration
 * 3. Explore all four directions (up, down, left, right)
 * 4. Backtrack by unmarking visited cells
 * 5. Start search from every cell in the grid
 * 
 * Approach (Backtracking/DFS):
 * 1. Iterate through every cell as starting point
 * 2. For each starting cell, perform DFS to find the word
 * 3. Mark cell as visited, explore neighbors, then unmark
 * 4. Return true if word is found, false otherwise
 * 
 * Time Complexity: O(m × n × 4^L) where L is word length
 * Space Complexity: O(L) for recursion stack
 * 
 * Tags: Array, Backtracking, Matrix, Depth-First Search
 */

import java.util.*;

class Solution {
    // Direction vectors: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    /**
     * Approach 1: Backtracking/DFS with visited matrix - RECOMMENDED
     * O(m × n × 4^L) time, O(m × n) space for visited matrix
     */
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || word == null || word.length() == 0) {
            return false;
        }
        
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        
        // Try every cell as starting point
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, visited, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] board, String word, boolean[][] visited, int i, int j, int index) {
        // Base case: all characters matched
        if (index == word.length()) {
            return true;
        }
        
        // Check boundaries and visited
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j]) {
            return false;
        }
        
        // Check character match
        if (board[i][j] != word.charAt(index)) {
            return false;
        }
        
        // Mark current cell as visited
        visited[i][j] = true;
        
        // Explore all four directions
        for (int[] dir : DIRECTIONS) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            
            if (dfs(board, word, visited, newI, newJ, index + 1)) {
                return true;
            }
        }
        
        // Backtrack: unmark current cell
        visited[i][j] = false;
        return false;
    }
    
    /**
     * Approach 2: Backtracking with in-place modification (space optimized)
     * O(m × n × 4^L) time, O(L) space (no visited matrix)
     */
    public boolean existInPlace(char[][] board, String word) {
        if (board == null || board.length == 0 || word == null || word.length() == 0) {
            return false;
        }
        
        int m = board.length;
        int n = board[0].length;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfsInPlace(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean dfsInPlace(char[][] board, String word, int i, int j, int index) {
        if (index == word.length()) {
            return true;
        }
        
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] == '#') {
            return false;
        }
        
        if (board[i][j] != word.charAt(index)) {
            return false;
        }
        
        // Temporarily mark cell as visited
        char temp = board[i][j];
        board[i][j] = '#';  // Mark as visited
        
        for (int[] dir : DIRECTIONS) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            
            if (dfsInPlace(board, word, newI, newJ, index + 1)) {
                return true;
            }
        }
        
        // Backtrack: restore original character
        board[i][j] = temp;
        return false;
    }
    
    /**
     * Approach 3: Iterative DFS using Stack
     * O(m × n × 4^L) time, O(m × n × L) space for stack
     */
    public boolean existIterative(char[][] board, String word) {
        if (board == null || board.length == 0 || word == null || word.length() == 0) {
            return false;
        }
        
        int m = board.length;
        int n = board[0].length;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfsIterative(board, word, i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean dfsIterative(char[][] board, String word, int startI, int startJ) {
        int m = board.length;
        int n = board[0].length;
        int wordLen = word.length();
        
        // Stack stores [i, j, index, direction]
        Stack<int[]> stack = new Stack<>();
        boolean[][] visited = new boolean[m][n];
        
        stack.push(new int[]{startI, startJ, 0, -1});
        visited[startI][startJ] = true;
        
        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int i = current[0];
            int j = current[1];
            int index = current[2];
            int dir = current[3];
            
            // Check if we found the word
            if (index == wordLen - 1) {
                return true;
            }
            
            // Try next direction
            dir++;
            if (dir < 4) {
                stack.pop();
                stack.push(new int[]{i, j, index, dir});
                
                int newI = i + DIRECTIONS[dir][0];
                int newJ = j + DIRECTIONS[dir][1];
                
                if (newI >= 0 && newI < m && newJ >= 0 && newJ < n && 
                    !visited[newI][newJ] && board[newI][newJ] == word.charAt(index + 1)) {
                    stack.push(new int[]{newI, newJ, index + 1, -1});
                    visited[newI][newJ] = true;
                }
            } else {
                // Backtrack
                stack.pop();
                visited[i][j] = false;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 4: BFS (not optimal but for demonstration)
     * Note: BFS is not ideal for path exploration with state
     */
    public boolean existBFS(char[][] board, String word) {
        if (board == null || board.length == 0 || word == null || word.length() == 0) {
            return false;
        }
        
        int m = board.length;
        int n = board[0].length;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (bfs(board, word, i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean bfs(char[][] board, String word, int startI, int startJ) {
        int m = board.length;
        int n = board[0].length;
        
        // Queue stores [i, j, index, visited_mask] - but this gets complex
        // For simplicity, we'll use a simplified version that's not optimal
        
        // This approach is not recommended for Word Search due to path state complexity
        // Using DFS approach 1 instead
        return exist(board, word);
    }
    
    /**
     * Approach 5: Optimized with early pruning and frequency check
     * O(m × n × 4^L) time, but with optimizations
     */
    public boolean existOptimized(char[][] board, String word) {
        if (board == null || board.length == 0 || word == null || word.length() == 0) {
            return false;
        }
        
        int m = board.length;
        int n = board[0].length;
        
        // Optimization 1: Check if board has enough characters
        if (word.length() > m * n) {
            return false;
        }
        
        // Optimization 2: Frequency check
        if (!hasSufficientCharacters(board, word)) {
            return false;
        }
        
        // Optimization 3: Reverse word if it's more efficient to search backwards
        String searchWord = shouldReverseWord(board, word) ? new StringBuilder(word).reverse().toString() : word;
        
        boolean[][] visited = new boolean[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == searchWord.charAt(0)) {
                    if (dfsOptimized(board, searchWord, visited, i, j, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean dfsOptimized(char[][] board, String word, boolean[][] visited, int i, int j, int index) {
        if (index == word.length()) {
            return true;
        }
        
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || 
            visited[i][j] || board[i][j] != word.charAt(index)) {
            return false;
        }
        
        visited[i][j] = true;
        
        // Explore in specific order (heuristic: try most promising directions first)
        // Right, Down, Left, Up (common order)
        int[][] customDirections = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] dir : customDirections) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            
            if (dfsOptimized(board, word, visited, newI, newJ, index + 1)) {
                return true;
            }
        }
        
        visited[i][j] = false;
        return false;
    }
    
    private boolean hasSufficientCharacters(char[][] board, String word) {
        int[] boardCount = new int[256]; // ASCII characters
        int[] wordCount = new int[256];
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                boardCount[board[i][j]]++;
            }
        }
        
        for (char c : word.toCharArray()) {
            wordCount[c]++;
        }
        
        for (int i = 0; i < 256; i++) {
            if (wordCount[i] > boardCount[i]) {
                return false;
            }
        }
        return true;
    }
    
    private boolean shouldReverseWord(char[][] board, String word) {
        // Simple heuristic: if first character is rarer than last character, reverse
        char first = word.charAt(0);
        char last = word.charAt(word.length() - 1);
        
        int firstCount = 0, lastCount = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == first) firstCount++;
                if (board[i][j] == last) lastCount++;
            }
        }
        
        return firstCount > lastCount;
    }
    
    /**
     * Helper method to visualize the DFS search process
     */
    public void visualizeSearch(char[][] board, String word) {
        System.out.println("\nWord Search Visualization:");
        System.out.println("Board:");
        printBoard(board);
        System.out.println("Word: " + word);
        System.out.println("Search Process:");
        
        boolean[][] visited = new boolean[board.length][board[0].length];
        boolean found = visualizeDFS(board, word, visited, 0, 0, 0, new ArrayList<>());
        
        if (!found) {
            System.out.println("Word not found in the board.");
        }
    }
    
    private boolean visualizeDFS(char[][] board, String word, boolean[][] visited, 
                               int i, int j, int index, List<int[]> path) {
        if (index == word.length()) {
            System.out.println("SUCCESS! Path found:");
            printBoardWithPath(board, path);
            return true;
        }
        
        // Only start from (0,0) for visualization simplicity
        if (index == 0) {
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[0].length; y++) {
                    if (!visited[x][y] && board[x][y] == word.charAt(index)) {
                        System.out.printf("Starting from (%d, %d) = '%c'%n", x, y, board[x][y]);
                        
                        visited[x][y] = true;
                        path.add(new int[]{x, y});
                        
                        if (visualizeDFS(board, word, visited, x, y, index + 1, path)) {
                            return true;
                        }
                        
                        path.remove(path.size() - 1);
                        visited[x][y] = false;
                    }
                }
            }
            return false;
        }
        
        for (int[] dir : DIRECTIONS) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            
            if (newI >= 0 && newI < board.length && newJ >= 0 && newJ < board[0].length && 
                !visited[newI][newJ] && board[newI][newJ] == word.charAt(index)) {
                
                System.out.printf("  Moving to (%d, %d) = '%c' (index %d)%n", 
                                newI, newJ, board[newI][newJ], index);
                
                visited[newI][newJ] = true;
                path.add(new int[]{newI, newJ});
                
                if (visualizeDFS(board, word, visited, newI, newJ, index + 1, path)) {
                    return true;
                }
                
                System.out.printf("  Backtracking from (%d, %d)%n", newI, newJ);
                path.remove(path.size() - 1);
                visited[newI][newJ] = false;
            }
        }
        
        return false;
    }
    
    private void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    private void printBoardWithPath(char[][] board, List<int[]> path) {
        char[][] display = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                display[i][j] = '.';
            }
        }
        
        for (int k = 0; k < path.size(); k++) {
            int[] cell = path.get(k);
            display[cell[0]][cell[1]] = (char) ('0' + k);
        }
        
        for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display[0].length; j++) {
                System.out.print(display[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Word Search Solution:");
        System.out.println("=============================");
        
        // Test case 1: Standard example (ABCCED)
        System.out.println("\nTest 1: Standard example (ABCCED)");
        char[][] board1 = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };
        String word1 = "ABCCED";
        boolean expected1 = true;
        
        long startTime = System.nanoTime();
        boolean result1a = solution.exist(board1, word1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.existInPlace(board1, word1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1c = solution.existIterative(board1, word1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1d = solution.existOptimized(board1, word1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        
        System.out.println("Backtracking: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("In-Place: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Iterative: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the search process
        solution.visualizeSearch(board1, word1);
        
        // Test case 2: Word "SEE"
        System.out.println("\nTest 2: Word 'SEE'");
        char[][] board2 = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };
        String word2 = "SEE";
        boolean result2a = solution.exist(board2, word2);
        System.out.println("SEE: " + result2a + " - " + (result2a ? "PASSED" : "FAILED"));
        
        // Test case 3: Word "ABCB" (should not exist)
        System.out.println("\nTest 3: Word 'ABCB' (should not exist)");
        char[][] board3 = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };
        String word3 = "ABCB";
        boolean result3a = solution.exist(board3, word3);
        System.out.println("ABCB: " + result3a + " - " + (!result3a ? "PASSED" : "FAILED"));
        
        // Test case 4: Single character
        System.out.println("\nTest 4: Single character 'A'");
        String word4 = "A";
        boolean result4a = solution.exist(board1, word4);
        System.out.println("Single char: " + result4a + " - " + (result4a ? "PASSED" : "FAILED"));
        
        // Test case 5: Empty board
        System.out.println("\nTest 5: Empty board");
        char[][] board5 = {};
        String word5 = "A";
        boolean result5a = solution.exist(board5, word5);
        System.out.println("Empty board: " + result5a + " - " + (!result5a ? "PASSED" : "FAILED"));
        
        // Test case 6: Empty word
        System.out.println("\nTest 6: Empty word");
        String word6 = "";
        boolean result6a = solution.exist(board1, word6);
        System.out.println("Empty word: " + result6a + " - " + (!result6a ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("Backtracking: " + time1a + " ns");
        System.out.println("In-Place: " + time1b + " ns");
        System.out.println("Iterative: " + time1c + " ns");
        System.out.println("Optimized: " + time1d + " ns");
        
        // Test all approaches produce same results
        System.out.println("\nTest 8: All approaches consistency");
        boolean allConsistent = (result1a == result1b) && (result1a == result1c) && (result1a == result1d);
        System.out.println("All approaches produce same results: " + allConsistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BACKTRACKING/DFS ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Use DFS to explore all possible paths from each starting cell,");
        System.out.println("backtracking when a path doesn't lead to the solution.");
        
        System.out.println("\nThe Algorithm Steps:");
        System.out.println("1. For each cell in the grid that matches the first character:");
        System.out.println("2. Mark cell as visited");
        System.out.println("3. Recursively search in all 4 directions for next character");
        System.out.println("4. If found, return true immediately");
        System.out.println("5. If not found, unmark cell (backtrack) and continue");
        
        System.out.println("\nWhy it works:");
        System.out.println("- Explores all possible paths systematically");
        System.out.println("- Backtracking ensures we don't get stuck in dead ends");
        System.out.println("- Visited tracking prevents reusing the same cell");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking with Visited Matrix (RECOMMENDED):");
        System.out.println("   Time: O(m × n × 4^L) - L is word length");
        System.out.println("   Space: O(m × n) for visited matrix + O(L) recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Use boolean matrix to track visited cells");
        System.out.println("     - DFS from each starting position");
        System.out.println("     - Backtrack by unmarking visited cells");
        System.out.println("   Pros:");
        System.out.println("     - Clear and intuitive");
        System.out.println("     - Doesn't modify original board");
        System.out.println("     - Easy to understand and debug");
        System.out.println("   Cons:");
        System.out.println("     - Uses extra O(m×n) space");
        System.out.println("   Best for: Interview settings, most use cases");
        
        System.out.println("\n2. In-Place Modification (Space Optimized):");
        System.out.println("   Time: O(m × n × 4^L) - Same time complexity");
        System.out.println("   Space: O(L) - Only recursion stack, no extra matrix");
        System.out.println("   How it works:");
        System.out.println("     - Temporarily modify board to mark visited cells");
        System.out.println("     - Use special character (like '#') to mark visited");
        System.out.println("     - Restore original character when backtracking");
        System.out.println("   Pros:");
        System.out.println("     - O(1) extra space (excluding recursion stack)");
        System.out.println("     - More memory efficient");
        System.out.println("   Cons:");
        System.out.println("     - Modifies original board (may not be acceptable)");
        System.out.println("     - Harder to debug with modified board");
        System.out.println("   Best for: When memory is critical and board can be modified");
        
        System.out.println("\n3. Iterative DFS:");
        System.out.println("   Time: O(m × n × 4^L) - Same complexity");
        System.out.println("   Space: O(m × n × L) - Stack storage");
        System.out.println("   How it works:");
        System.out.println("     - Use stack instead of recursion");
        System.out.println("     - Store state (i, j, index, direction)");
        System.out.println("     - Manual backtracking with stack operations");
        System.out.println("   Pros:");
        System.out.println("     - Avoids recursion stack limits");
        System.out.println("     - More control over traversal");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Higher memory usage");
        System.out.println("   Best for: Very large boards where recursion depth is concern");
        
        System.out.println("\n4. Optimized with Pruning:");
        System.out.println("   Time: O(m × n × 4^L) - But with practical optimizations");
        System.out.println("   Space: O(m × n) - Visited matrix");
        System.out.println("   How it works:");
        System.out.println("     - Add frequency check to prune early");
        System.out.println("     - Reverse word if beneficial");
        System.out.println("     - Custom direction order for heuristics");
        System.out.println("   Pros:");
        System.out.println("     - Practical performance improvements");
        System.out.println("     - Handles edge cases better");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Optimizations may not always help");
        System.out.println("   Best for: Production code, competitive programming");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS DETAILS:");
        System.out.println("1. Time Complexity: O(m × n × 4^L)");
        System.out.println("   - m × n starting positions");
        System.out.println("   - 4 directions at each step");
        System.out.println("   - L steps (word length)");
        System.out.println("   - In practice, pruning makes it faster");
        System.out.println("2. Space Complexity: O(m × n) or O(L)");
        System.out.println("   - Visited matrix: O(m × n)");
        System.out.println("   - Recursion stack: O(L)");
        System.out.println("   - Total: O(m × n + L)");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PRACTICAL OPTIMIZATIONS:");
        System.out.println("1. Frequency Check: Ensure board has enough characters");
        System.out.println("2. Word Reversal: Search from rare to common characters");
        System.out.println("3. Early Stopping: Return immediately when found");
        System.out.println("4. Direction Order: Try most promising directions first");
        System.out.println("5. Boundary Check: Check boundaries before recursion");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with backtracking/DFS - it's the expected solution");
        System.out.println("2. Clearly explain the visited tracking mechanism");
        System.out.println("3. Discuss time/space complexity honestly");
        System.out.println("4. Mention optimizations (frequency check, etc.)");
        System.out.println("5. Handle edge cases: empty board, empty word, single char");
        System.out.println("6. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
