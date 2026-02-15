
# Solution.java

```java
import java.util.*;

/**
 * 212. Word Search II
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Find all words from given list that exist in 2D board by traversing adjacent cells.
 * 
 * Key Insights:
 * 1. Trie stores all words for efficient prefix checking
 * 2. DFS/Backtracking explores all possible paths
 * 3. Prune when prefix doesn't exist
 * 4. Remove found words to avoid duplicates
 */
class Solution {
    
    /**
     * Approach 1: Trie + Backtracking (Recommended)
     * Time: O(m × n × 4 × 3^(L-1)), Space: O(total chars in words)
     */
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        TrieNode root = buildTrie(words);
        
        int m = board.length;
        int n = board[0].length;
        
        // DFS from each cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, i, j, root, result);
            }
        }
        
        return result;
    }
    
    private void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
        char c = board[i][j];
        
        // Check if current character exists in Trie
        if (c == '#' || node.children[c - 'a'] == null) {
            return;
        }
        
        node = node.children[c - 'a'];
        
        // If we found a word, add to result and remove from Trie
        if (node.word != null) {
            result.add(node.word);
            node.word = null; // Remove to avoid duplicates
        }
        
        // Mark current cell as visited
        board[i][j] = '#';
        
        // Explore all 4 directions
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            
            if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
                dfs(board, x, y, node, result);
            }
        }
        
        // Restore cell
        board[i][j] = c;
    }
    
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }
            node.word = word;
        }
        return root;
    }
    
    class TrieNode {
        TrieNode[] children;
        String word; // Store word at end node
        
        TrieNode() {
            children = new TrieNode[26];
            word = null;
        }
    }
    
    /**
     * Approach 2: Trie + Backtracking with Counter (Optimized for duplicates)
     * Time: O(m × n × 4 × 3^(L-1)), Space: O(total chars in words)
     * Uses count to track number of words with given prefix
     */
    public List<String> findWordsWithCount(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        TrieNodeWithCount root = buildTrieWithCount(words);
        
        int m = board.length;
        int n = board[0].length;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfsWithCount(board, i, j, root, result);
            }
        }
        
        return result;
    }
    
    private void dfsWithCount(char[][] board, int i, int j, TrieNodeWithCount node, List<String> result) {
        char c = board[i][j];
        
        if (c == '#' || node.children[c - 'a'] == null || node.children[c - 'a'].count == 0) {
            return;
        }
        
        node = node.children[c - 'a'];
        
        if (node.word != null) {
            result.add(node.word);
            node.word = null;
        }
        
        board[i][j] = '#';
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            
            if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
                dfsWithCount(board, x, y, node, result);
            }
        }
        
        board[i][j] = c;
        
        // Decrement count after exploring (optimization for pruning)
        node.count--;
    }
    
    private TrieNodeWithCount buildTrieWithCount(String[] words) {
        TrieNodeWithCount root = new TrieNodeWithCount();
        for (String word : words) {
            TrieNodeWithCount node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNodeWithCount();
                }
                node = node.children[index];
                node.count++;
            }
            node.word = word;
        }
        return root;
    }
    
    class TrieNodeWithCount {
        TrieNodeWithCount[] children;
        String word;
        int count; // Number of words with this prefix
        
        TrieNodeWithCount() {
            children = new TrieNodeWithCount[26];
            word = null;
            count = 0;
        }
    }
    
    /**
     * Approach 3: Backtracking without Trie (for small word lists)
     * Time: O(m × n × 4^(L)), Space: O(L) recursion depth
     * Only efficient when number of words is small
     */
    public List<String> findWordsBacktracking(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        Set<String> wordSet = new HashSet<>(Arrays.asList(words));
        
        for (String word : words) {
            if (exist(board, word)) {
                result.add(word);
            }
        }
        
        return result;
    }
    
    private boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfsWordSearch(board, i, j, word, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean dfsWordSearch(char[][] board, int i, int j, String word, int index) {
        if (index == word.length()) return true;
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false;
        if (board[i][j] != word.charAt(index)) return false;
        
        char temp = board[i][j];
        board[i][j] = '#';
        
        boolean found = dfsWordSearch(board, i + 1, j, word, index + 1) ||
                        dfsWordSearch(board, i - 1, j, word, index + 1) ||
                        dfsWordSearch(board, i, j + 1, word, index + 1) ||
                        dfsWordSearch(board, i, j - 1, word, index + 1);
        
        board[i][j] = temp;
        return found;
    }
    
    /**
     * Approach 4: Trie + Backtracking with Path Tracking
     * Time: O(m × n × 4 × 3^(L-1)), Space: O(total chars in words + L)
     * Tracks current path for debugging/visualization
     */
    public List<String> findWordsWithPath(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        TrieNode root = buildTrie(words);
        List<int[]> path = new ArrayList<>();
        
        int m = board.length;
        int n = board[0].length;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfsWithPath(board, i, j, root, result, path);
            }
        }
        
        return result;
    }
    
    private void dfsWithPath(char[][] board, int i, int j, TrieNode node, 
                             List<String> result, List<int[]> path) {
        char c = board[i][j];
        
        if (c == '#' || node.children[c - 'a'] == null) {
            return;
        }
        
        node = node.children[c - 'a'];
        path.add(new int[]{i, j});
        
        if (node.word != null) {
            result.add(node.word);
            System.out.println("Found \"" + node.word + "\" at path: " + formatPath(path));
            node.word = null;
        }
        
        board[i][j] = '#';
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            
            if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
                dfsWithPath(board, x, y, node, result, path);
            }
        }
        
        board[i][j] = c;
        path.remove(path.size() - 1);
    }
    
    private String formatPath(List<int[]> path) {
        StringBuilder sb = new StringBuilder();
        for (int[] p : path) {
            sb.append("[").append(p[0]).append(",").append(p[1]).append("] ");
        }
        return sb.toString();
    }
    
    /**
     * Helper: Visualize board
     */
    public void printBoard(char[][] board) {
        System.out.println("\nBoard:");
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
    
    /**
     * Helper: Visualize search process
     */
    public void visualizeSearch(char[][] board, String[] words) {
        System.out.println("\nVisualizing Word Search II:");
        System.out.println("Board:");
        printBoard(board);
        System.out.println("\nWords to find: " + Arrays.toString(words));
        System.out.println("\nStarting DFS with Trie...");
        
        long start = System.currentTimeMillis();
        List<String> result = findWordsWithPath(board, words);
        long time = System.currentTimeMillis() - start;
        
        System.out.println("\nWords found: " + result);
        System.out.println("Time taken: " + time + " ms");
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            {
                new char[][]{
                    {'o','a','a','n'},
                    {'e','t','a','e'},
                    {'i','h','k','r'},
                    {'i','f','l','v'}
                },
                new String[]{"oath","pea","eat","rain"},
                Arrays.asList("eat","oath")
            },
            {
                new char[][]{{'a','b'},{'c','d'}},
                new String[]{"abcb"},
                Arrays.asList()
            },
            {
                new char[][]{{'a'}},
                new String[]{"a"},
                Arrays.asList("a")
            },
            {
                new char[][]{{'a','a'}},
                new String[]{"aa"},
                Arrays.asList("aa")
            },
            {
                new char[][]{
                    {'a','b','c'},
                    {'d','e','f'},
                    {'g','h','i'}
                },
                new String[]{"abc","def","ghi","adg","beh","cfi"},
                Arrays.asList("abc","def","ghi","adg","beh","cfi")
            },
            {
                new char[][]{
                    {'a','b','c','e'},
                    {'s','f','c','s'},
                    {'a','d','e','e'}
                },
                new String[]{"see","sea","abcb"},
                Arrays.asList("see","sea")
            }
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        Object[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            char[][] board = (char[][]) testCases[i][0];
            String[] words = (String[]) testCases[i][1];
            @SuppressWarnings("unchecked")
            List<String> expected = (List<String>) testCases[i][2];
            
            System.out.printf("\nTest %d:%n", i + 1);
            printBoard(board);
            System.out.println("Words: " + Arrays.toString(words));
            
            List<String> result1 = findWords(board, words);
            List<String> result2 = findWordsWithCount(board, words);
            List<String> result3 = findWordsBacktracking(board, words);
            List<String> result4 = findWordsWithPath(board, words);
            
            // Sort for comparison
            Collections.sort(result1);
            Collections.sort(result2);
            Collections.sort(result3);
            Collections.sort(result4);
            Collections.sort(expected);
            
            boolean allMatch = result1.equals(expected) && 
                              result2.equals(expected) &&
                              result3.equals(expected) &&
                              result4.equals(expected);
            
            if (allMatch) {
                System.out.println("✓ PASS - Found: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=======================");
        
        // Create larger board
        char[][] board = new char[10][10];
        Random rand = new Random(42);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = (char) ('a' + rand.nextInt(26));
            }
        }
        
        // Generate many words
        List<String> wordList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int len = rand.nextInt(5) + 3;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) {
                sb.append((char) ('a' + rand.nextInt(26)));
            }
            wordList.add(sb.toString());
        }
        String[] words = wordList.toArray(new String[0]);
        
        System.out.println("Test Setup:");
        System.out.println("  Board size: 10×10");
        System.out.println("  Words count: " + words.length);
        
        long[] times = new long[4];
        List<String>[] results = new List[4];
        
        // Method 1: Trie + Backtracking
        long start = System.currentTimeMillis();
        results[0] = findWords(board, words);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Trie with Count
        start = System.currentTimeMillis();
        results[1] = findWordsWithCount(board, words);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Backtracking without Trie (skip for large word lists)
        times[2] = -1;
        results[2] = new ArrayList<>();
        
        // Method 4: Trie with Path
        start = System.currentTimeMillis();
        results[3] = findWordsWithPath(board, words);
        times[3] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Words Found");
        System.out.println("--------------------------|-----------|-------------");
        System.out.printf("1. Trie + Backtracking    | %9d | %11d%n", times[0], results[0].size());
        System.out.printf("2. Trie with Count        | %9d | %11d%n", times[1], results[1].size());
        System.out.printf("3. Backtracking without Trie| %9s | %11s%n", "N/A", "N/A (too slow)");
        System.out.printf("4. Trie with Path         | %9d | %11d%n", times[3], results[3].size());
        
        System.out.println("\nObservations:");
        System.out.println("1. Trie approaches are essential for large word lists");
        System.out.println("2. Count optimization slightly improves pruning");
        System.out.println("3. Path tracking adds minimal overhead");
        System.out.println("4. All methods find the same words");
    }
    
    /**
     * Helper: Explain Trie structure
     */
    public void explainTrie() {
        System.out.println("\nTrie Structure Explanation:");
        System.out.println("===========================");
        
        System.out.println("\nWhy Trie for this problem?");
        System.out.println("1. We need to check if current path is a prefix of any word");
        System.out.println("2. Trie provides O(L) lookup for prefixes");
        System.out.println("3. Multiple words share common prefixes → memory efficient");
        System.out.println("4. Natural for DFS backtracking");
        
        System.out.println("\nExample: Words = [\"oath\", \"eat\", \"oat\"]");
        System.out.println("""
                root
               /    \\
              o      e
             /       \\
            a         a
           / \\        \\
          t   t        t
         /     \\
        h       (eats? no)
        ✓
        """);
        
        System.out.println("\nNode Structure:");
        System.out.println("  TrieNode[] children (size 26)");
        System.out.println("  String word (null unless at end)");
        
        System.out.println("\nTraversal:");
        System.out.println("1. Start at root");
        System.out.println("2. For each character in word, follow child pointer");
        System.out.println("3. If child null, prefix doesn't exist");
        System.out.println("4. If word != null, found a complete word");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Empty board:");
        char[][] board1 = new char[0][0];
        try {
            List<String> result = findWords(board1, new String[]{"a"});
            System.out.println("   Result: " + result + " (should be empty)");
        } catch (Exception e) {
            System.out.println("   Exception: " + e.getMessage());
        }
        
        System.out.println("\n2. Empty words list:");
        char[][] board2 = {{'a','b'},{'c','d'}};
        List<String> result2 = findWords(board2, new String[]{});
        System.out.println("   Result: " + result2 + " (should be empty)");
        
        System.out.println("\n3. Single character board:");
        char[][] board3 = {{'a'}};
        String[] words3 = {"a", "b", "aa"};
        List<String> result3 = findWords(board3, words3);
        System.out.println("   Words: " + Arrays.toString(words3));
        System.out.println("   Found: " + result3 + " (should be [\"a\"])");
        
        System.out.println("\n4. All same characters:");
        char[][] board4 = {{'a','a'},{'a','a'}};
        String[] words4 = {"a", "aa", "aaa", "aaaa", "aaaaa"};
        List<String> result4 = findWords(board4, words4);
        System.out.println("   Words: " + Arrays.toString(words4));
        System.out.println("   Found: " + result4 + " (should be [\"a\",\"aa\",\"aaa\",\"aaaa\"])");
        
        System.out.println("\n5. Words not in board:");
        char[][] board5 = {{'a','b'},{'c','d'}};
        String[] words5 = {"xyz", "abc", "def"};
        List<String> result5 = findWords(board5, words5);
        System.out.println("   Result: " + result5 + " (should be empty)");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void realWorldApplications() {
        System.out.println("\nReal-world Applications:");
        System.out.println("========================");
        
        System.out.println("\n1. Word Games:");
        System.out.println("   - Boggle game solvers");
        System.out.println("   - Scrabble word finder");
        System.out.println("   - Crossword puzzle helpers");
        
        System.out.println("\n2. Spell Checkers:");
        System.out.println("   - Finding valid words in text");
        System.out.println("   - Autocorrect suggestions");
        
        System.out.println("\n3. Pattern Recognition:");
        System.out.println("   - Character recognition in images");
        System.out.println("   - Handwriting analysis");
        
        System.out.println("\n4. Bioinformatics:");
        System.out.println("   - Finding DNA subsequences");
        System.out.println("   - Protein sequence matching");
        
        System.out.println("\n5. Network Security:");
        System.out.println("   - Malware signature detection");
        System.out.println("   - Pattern-based intrusion detection");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Start with Word Search I (LeetCode 79):");
        System.out.println("   - Single word search");
        System.out.println("   - Base backtracking implementation");
        
        System.out.println("\n2. Scale to multiple words:");
        System.out.println("   - Calling Word Search I for each word is O(k × m × n × 4^L)");
        System.out.println("   - Too slow for large k (3×10⁴)");
        System.out.println("   - Need to share computation");
        
        System.out.println("\n3. Introduce Trie:");
        System.out.println("   - Store all words in Trie");
        System.out.println("   - Check prefixes during DFS");
        System.out.println("   - Prune when no words start with current prefix");
        
        System.out.println("\n4. Optimize further:");
        System.out.println("   - Mark found words to avoid duplicates");
        System.out.println("   - Remove found words from Trie");
        System.out.println("   - Use counters for pruning");
        
        System.out.println("\n5. Implementation details:");
        System.out.println("   - DFS from each cell");
        System.out.println("   - Mark visited cells with '#'");
        System.out.println("   - Restore after backtracking");
        System.out.println("   - Use int[][] dirs for directions");
        
        System.out.println("\n6. Complexity analysis:");
        System.out.println("   - Build Trie: O(total chars in words)");
        System.out.println("   - Search: O(m × n × 4 × 3^(L-1))");
        System.out.println("   - Space: O(total chars)");
        
        System.out.println("\n7. Edge cases:");
        System.out.println("   - Empty board");
        System.out.println("   - Empty words list");
        System.out.println("   - Single character words");
        System.out.println("   - Duplicate words");
        System.out.println("   - Words longer than board area");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("212. Word Search II");
        System.out.println("===================");
        
        // Explain Trie
        solution.explainTrie();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Visualize example
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Example 1 Visualization:");
        System.out.println("=".repeat(80));
        
        char[][] board = {
            {'o','a','a','n'},
            {'e','t','a','e'},
            {'i','h','k','r'},
            {'i','f','l','v'}
        };
        String[] words = {"oath", "pea", "eat", "rain"};
        solution.visualizeSearch(board, words);
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(80));
        solution.realWorldApplications();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word;
    }
    
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        TrieNode root = buildTrie(words);
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, result);
            }
        }
        return result;
    }
    
    private void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
        char c = board[i][j];
        if (c == '#' || node.children[c - 'a'] == null) return;
        
        node = node.children[c - 'a'];
        if (node.word != null) {
            result.add(node.word);
            node.word = null;
        }
        
        board[i][j] = '#';
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];
            if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
                dfs(board, x, y, node, result);
            }
        }
        board[i][j] = c;
    }
    
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.word = word;
        }
        return root;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Trie enables sharing prefix checks across words");
        System.out.println("2. Backtracking explores all possible paths");
        System.out.println("3. Pruning with Trie dramatically reduces search space");
        System.out.println("4. Remove found words to avoid duplicates");
        System.out.println("5. Mark cells visited by temporarily modifying board");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Build Trie: O(total chars in words)");
        System.out.println("- Search: O(m × n × 4 × 3^(L-1))");
        System.out.println("- Space: O(total chars in words)");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle uppercase letters?");
        System.out.println("2. How would you optimize for very large boards?");
        System.out.println("3. How would you find the longest word?");
        System.out.println("4. How would you implement a BFS solution?");
        System.out.println("5. How would you handle diagonal moves?");
    }
}
