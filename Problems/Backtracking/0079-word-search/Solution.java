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
