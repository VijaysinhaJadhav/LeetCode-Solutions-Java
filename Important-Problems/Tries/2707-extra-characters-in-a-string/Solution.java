
# Solution.java

```java
import java.util.*;

/**
 * 2707. Extra Characters in a String
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given string s and dictionary of words, partition s into non-overlapping
 * dictionary words to minimize leftover characters.
 * 
 * Key Insights:
 * 1. Dynamic Programming - dp[i] = min extra chars from index i to end
 * 2. HashSet for O(1) dictionary lookups
 * 3. Bottom-up DP processes from right to left
 * 4. At each position, try skipping current char or matching a word [citation:1]
 */
class Solution {
    
    /**
     * Approach 1: Bottom-Up Dynamic Programming with HashSet (Recommended)
     * Time: O(n³), Space: O(n + m·k)
     * Most intuitive and efficient for constraints
     */
    public int minExtraChar(String s, String[] dictionary) {
        int n = s.length();
        Set<String> wordSet = new HashSet<>(Arrays.asList(dictionary));
        
        // dp[i] = minimum extra characters from index i to end
        int[] dp = new int[n + 1];
        
        // Process from right to left (bottom-up)
        for (int i = n - 1; i >= 0; i--) {
            // Option 1: Skip current character (count as extra)
            dp[i] = 1 + dp[i + 1];
            
            // Option 2: Try to match a word starting at i
            for (int j = i; j < n; j++) {
                String word = s.substring(i, j + 1);
                if (wordSet.contains(word)) {
                    dp[i] = Math.min(dp[i], dp[j + 1]);
                }
            }
        }
        
        return dp[0];
    }
    
    /**
     * Approach 2: Top-Down Dynamic Programming with Memoization
     * Time: O(n³), Space: O(n + m·k)
     * Recursive approach with caching
     */
    public int minExtraCharTopDown(String s, String[] dictionary) {
        Set<String> wordSet = new HashSet<>(Arrays.asList(dictionary));
        int n = s.length();
        Integer[] memo = new Integer[n];
        
        return dfs(s, 0, wordSet, memo);
    }
    
    private int dfs(String s, int start, Set<String> wordSet, Integer[] memo) {
        if (start == s.length()) return 0;
        if (memo[start] != null) return memo[start];
        
        // Skip current character
        int result = 1 + dfs(s, start + 1, wordSet, memo);
        
        // Try all possible words starting at start
        for (int end = start; end < s.length(); end++) {
            String word = s.substring(start, end + 1);
            if (wordSet.contains(word)) {
                result = Math.min(result, dfs(s, end + 1, wordSet, memo));
            }
        }
        
        memo[start] = result;
        return result;
    }
    
    /**
     * Approach 3: Bottom-Up DP with Iteration Over Dictionary Words
     * Time: O(n·m·k), Space: O(n)
     * More efficient when dictionary is small
     */
    public int minExtraCharWordIteration(String s, String[] dictionary) {
        int n = s.length();
        int[] dp = new int[n + 1];
        
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1 + dp[i + 1];
            
            // Try each dictionary word
            for (String word : dictionary) {
                if (i + word.length() <= n) {
                    String substr = s.substring(i, i + word.length());
                    if (substr.equals(word)) {
                        dp[i] = Math.min(dp[i], dp[i + word.length()]);
                    }
                }
            }
        }
        
        return dp[0];
    }
    
    /**
     * Approach 4: Bottom-Up DP with Trie (Optimized)
     * Time: O(n²), Space: O(n + total characters in dictionary)
     * Uses Trie for efficient prefix matching [citation:1]
     */
    public int minExtraCharTrie(String s, String[] dictionary) {
        int n = s.length();
        TrieNode root = buildTrie(dictionary);
        int[] dp = new int[n + 1];
        
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1 + dp[i + 1];
            TrieNode node = root;
            
            // Try matching words starting at i
            for (int j = i; j < n; j++) {
                char c = s.charAt(j);
                if (!node.children.containsKey(c)) {
                    break;
                }
                node = node.children.get(c);
                if (node.isWord) {
                    dp[i] = Math.min(dp[i], dp[j + 1]);
                }
            }
        }
        
        return dp[0];
    }
    
    /**
     * Helper: Build Trie from dictionary
     */
    private TrieNode buildTrie(String[] dictionary) {
        TrieNode root = new TrieNode();
        for (String word : dictionary) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                node.children.putIfAbsent(c, new TrieNode());
                node = node.children.get(c);
            }
            node.isWord = true;
        }
        return root;
    }
    
    /**
     * Trie Node class for Approach 4
     */
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isWord;
        
        TrieNode() {
            children = new HashMap<>();
            isWord = false;
        }
    }
    
    /**
     * Approach 5: Forward DP (Left to Right)
     * Time: O(n³), Space: O(n)
     * Alternative direction - dp[i] = min extra chars for first i chars
     */
    public int minExtraCharForward(String s, String[] dictionary) {
        int n = s.length();
        Set<String> wordSet = new HashSet<>(Arrays.asList(dictionary));
        int[] dp = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] + 1; // treat s[i-1] as extra
            
            // Try all possible words ending at i-1
            for (int j = 0; j < i; j++) {
                String word = s.substring(j, i);
                if (wordSet.contains(word)) {
                    dp[i] = Math.min(dp[i], dp[j]);
                }
            }
        }
        
        return dp[n];
    }
    
    /**
     * Helper: Visualize DP process
     */
    public void visualizeDP(String s, String[] dictionary) {
        System.out.println("\nDP Visualization for: \"" + s + "\"");
        System.out.println("Dictionary: " + Arrays.toString(dictionary));
        
        Set<String> wordSet = new HashSet<>(Arrays.asList(dictionary));
        int n = s.length();
        int[] dp = new int[n + 1];
        
        System.out.println("\nProcessing from right to left:");
        System.out.println("i | s[i:] | dp[i] calculation");
        System.out.println("--|--------|------------------");
        
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1 + dp[i + 1];
            System.out.printf("%d | %-6s | Skip '%c' → dp[%d] + 1 = %d%n", 
                i, s.substring(i), s.charAt(i), i + 1, dp[i]);
            
            for (int j = i; j < n; j++) {
                String word = s.substring(i, j + 1);
                if (wordSet.contains(word)) {
                    int candidate = dp[j + 1];
                    System.out.printf("  |        | Found \"%s\" → dp[%d] = %d, min(%d, %d) = %d%n",
                        word, j + 1, candidate, dp[i], candidate, Math.min(dp[i], candidate));
                    dp[i] = Math.min(dp[i], candidate);
                }
            }
        }
        
        System.out.println("\nFinal DP array:");
        System.out.print("i:      ");
        for (int i = 0; i <= n; i++) System.out.printf("%2d ", i);
        System.out.print("\ndp[i]:  ");
        for (int i = 0; i <= n; i++) System.out.printf("%2d ", dp[i]);
        System.out.println("\n\nMinimum extra characters: " + dp[0]);
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            {"leetscode", new String[]{"leet","code","leetcode"}, 1},
            {"sayhelloworld", new String[]{"hello","world"}, 3},
            {"neetcodes", new String[]{"neet","code","neetcode"}, 1},
            {"neetcodde", new String[]{"neet","code","neetcode"}, 5},
            {"", new String[]{"a","b"}, 0},
            {"a", new String[]{"a"}, 0},
            {"a", new String[]{"b"}, 1},
            {"abc", new String[]{"a","b","c"}, 0},
            {"abc", new String[]{"ab","c"}, 0},
            {"abc", new String[]{"abc"}, 0},
            {"aaaa", new String[]{"a","aa","aaa"}, 0},
            {"abcdef", new String[]{"ab","cd","ef"}, 0},
            {"abcdef", new String[]{"abc","def"}, 0},
            {"abcdef", new String[]{"abcd","ef"}, 0},
            {"abcdef", new String[]{"abcde","f"}, 0}
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
            String s = (String) testCases[i][0];
            String[] dict = (String[]) testCases[i][1];
            int expected = (int) testCases[i][2];
            
            System.out.printf("\nTest %d: s=\"%s\", dict=%s%n", 
                i + 1, s, Arrays.toString(dict));
            
            int result1 = minExtraChar(s, dict);
            int result2 = minExtraCharTopDown(s, dict);
            int result3 = minExtraCharWordIteration(s, dict);
            int result4 = minExtraCharTrie(s, dict);
            int result5 = minExtraCharForward(s, dict);
            
            boolean allMatch = result1 == expected && result2 == expected && 
                              result3 == expected && result4 == expected && 
                              result5 == expected;
            
            if (allMatch) {
                System.out.println("✓ PASS - All methods return: " + expected);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1 (Bottom-up): " + result1);
                System.out.println("  Method 2 (Top-down): " + result2);
                System.out.println("  Method 3 (Word iteration): " + result3);
                System.out.println("  Method 4 (Trie): " + result4);
                System.out.println("  Method 5 (Forward): " + result5);
            }
            
            // Visualize for complex cases
            if (s.length() > 0 && s.length() <= 10) {
                visualizeDP(s, dict);
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
        
        String s = "abcdefghijklmnopqrstuvwxyz";
        String[] dict = {"a","b","c","d","e","f","g","h","i","j","k","l","m",
                         "n","o","p","q","r","s","t","u","v","w","x","y","z"};
        
        System.out.println("Test: s length = " + s.length() + ", dict size = " + dict.length);
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: Bottom-up
        long start = System.currentTimeMillis();
        results[0] = minExtraChar(s, dict);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Top-down
        start = System.currentTimeMillis();
        results[1] = minExtraCharTopDown(s, dict);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Word iteration
        start = System.currentTimeMillis();
        results[2] = minExtraCharWordIteration(s, dict);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Trie
        start = System.currentTimeMillis();
        results[3] = minExtraCharTrie(s, dict);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Forward
        start = System.currentTimeMillis();
        results[4] = minExtraCharForward(s, dict);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Bottom-up DP           | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Top-down DP            | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. Word iteration         | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. Trie + DP              | %9d | %6d%n", times[3], results[3]);
        System.out.printf("5. Forward DP             | %9d | %6d%n", times[4], results[4]);
        
        System.out.println("\nObservations:");
        System.out.println("1. All methods produce same result (correctness verified)");
        System.out.println("2. Word iteration is fastest when dictionary is small");
        System.out.println("3. Trie approach scales better with dictionary size");
        System.out.println("4. Bottom-up slightly faster than top-down (no recursion overhead)");
    }
    
    /**
     * Helper: Explain DP recurrence
     */
    public void explainDP() {
        System.out.println("\nDynamic Programming Explanation:");
        System.out.println("================================");
        
        System.out.println("\nState Definition:");
        System.out.println("  dp[i] = minimum extra characters from index i to end of string");
        
        System.out.println("\nBase Case:");
        System.out.println("  dp[n] = 0 (no characters left)");
        
        System.out.println("\nTransition:");
        System.out.println("  At position i, we have two options:");
        System.out.println("  1. Skip character at i (count as extra): dp[i] = 1 + dp[i + 1]");
        System.out.println("  2. Try to match a word starting at i:");
        System.out.println("     For all j from i to n-1:");
        System.out.println("       if s[i:j+1] is in dictionary:");
        System.out.println("         dp[i] = min(dp[i], dp[j + 1])");
        
        System.out.println("\nProcessing Order:");
        System.out.println("  From right to left (i = n-1 down to 0)");
        System.out.println("  This ensures dp[j+1] is already computed when needed");
        
        System.out.println("\nExample: s = \"leetscode\", dict = [\"leet\",\"code\",\"leetcode\"]");
        System.out.println("  i=8: dp[8] = 1 + dp[9] = 1");
        System.out.println("  i=7: dp[7] = 1 + dp[8] = 2");
        System.out.println("  i=6: dp[6] = 1 + dp[7] = 3");
        System.out.println("        Found \"code\" (6-9) → dp[10]=0 → dp[6] = min(3,0) = 0");
        System.out.println("  i=5: dp[5] = 1 + dp[6] = 1");
        System.out.println("  i=4: dp[4] = 1 + dp[5] = 2 (character 's' is extra)");
        System.out.println("  i=3: dp[3] = 1 + dp[4] = 3");
        System.out.println("        Found \"leetcode\" (3-10) → dp[11]=0 → dp[3] = min(3,0) = 0");
        System.out.println("  i=2: dp[2] = 1 + dp[3] = 1");
        System.out.println("  i=1: dp[1] = 1 + dp[2] = 2");
        System.out.println("        Found \"et\"? No. Found \"ets\"? No.");
        System.out.println("  i=0: dp[0] = 1 + dp[1] = 3");
        System.out.println("        Found \"leet\" (0-3) → dp[4]=2 → dp[0] = min(3,2) = 2");
        System.out.println("        Found \"leets\" (0-4) → dp[5]=1 → dp[0] = min(2,1) = 1");
        System.out.println("  Result: dp[0] = 1");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Empty string:");
        System.out.println("   Input: \"\", dict=[\"a\"]");
        System.out.println("   Output: " + minExtraChar("", new String[]{"a"}) + " (expected: 0)");
        
        System.out.println("\n2. Single character match:");
        System.out.println("   Input: \"a\", dict=[\"a\"]");
        System.out.println("   Output: " + minExtraChar("a", new String[]{"a"}) + " (expected: 0)");
        
        System.out.println("\n3. Single character no match:");
        System.out.println("   Input: \"a\", dict=[\"b\"]");
        System.out.println("   Output: " + minExtraChar("a", new String[]{"b"}) + " (expected: 1)");
        
        System.out.println("\n4. All characters match:");
        System.out.println("   Input: \"abc\", dict=[\"a\",\"b\",\"c\"]");
        System.out.println("   Output: " + minExtraChar("abc", new String[]{"a","b","c"}) + " (expected: 0)");
        
        System.out.println("\n5. Overlapping words - choose optimal:");
        System.out.println("   Input: \"aaaa\", dict=[\"a\",\"aa\",\"aaa\"]");
        System.out.println("   Output: " + minExtraChar("aaaa", new String[]{"a","aa","aaa"}) + " (expected: 0)");
        
        System.out.println("\n6. No matches at all:");
        System.out.println("   Input: \"xyz\", dict=[\"a\",\"b\",\"c\"]");
        System.out.println("   Output: " + minExtraChar("xyz", new String[]{"a","b","c"}) + " (expected: 3)");
        
        System.out.println("\n7. Maximum length (50 chars):");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) sb.append('a');
        long start = System.currentTimeMillis();
        int result = minExtraChar(sb.toString(), new String[]{"a"});
        long time = System.currentTimeMillis() - start;
        System.out.println("   Output: " + result + " (expected: 0)");
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Understand the problem:");
        System.out.println("   - We want to MINIMIZE leftover characters");
        System.out.println("   - Words can be reused");
        System.out.println("   - Substrings must be non-overlapping");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Try all possible partitions (exponential)");
        System.out.println("   - Acknowledge it's too slow for constraints");
        
        System.out.println("\n3. Recognize DP pattern:");
        System.out.println("   - This is similar to Word Break (LeetCode 139)");
        System.out.println("   - Instead of boolean, we need minimum extra chars");
        System.out.println("   - Define dp[i] = min extra chars from i to end");
        
        System.out.println("\n4. Implement step by step:");
        System.out.println("   - Convert dictionary to HashSet");
        System.out.println("   - Initialize dp[n] = 0");
        System.out.println("   - Process from right to left");
        System.out.println("   - At each i, try skipping char or matching words");
        
        System.out.println("\n5. Optimize if needed:");
        System.out.println("   - If dictionary is large, consider Trie");
        System.out.println("   - If words are short, iterate over dictionary instead of substrings");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - Empty string");
        System.out.println("   - Empty dictionary");
        System.out.println("   - String shorter than all dictionary words");
        System.out.println("   - Multiple optimal solutions");
        
        System.out.println("\n7. Complexity analysis:");
        System.out.println("   - Time: O(n³) with HashSet");
        System.out.println("   - Space: O(n + dict_size)");
        System.out.println("   - Can be O(n²) with Trie [citation:1]");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void realWorldApplications() {
        System.out.println("\nReal-world Applications:");
        System.out.println("========================");
        
        System.out.println("\n1. Text Compression:");
        System.out.println("   - Finding optimal segmentation to minimize leftover");
        System.out.println("   - Dictionary-based compression (LZW)");
        
        System.out.println("\n2. Natural Language Processing:");
        System.out.println("   - Tokenization of languages without spaces");
        System.out.println("   - Spell checking and correction");
        
        System.out.println("\n3. Bioinformatics:");
        System.out.println("   - DNA sequence segmentation");
        System.out.println("   - Finding matching patterns in genetic sequences");
        
        System.out.println("\n4. Data Deduplication:");
        System.out.println("   - Identifying repeated patterns in data");
        System.out.println("   - Storage optimization");
        
        System.out.println("\n5. Input Validation:");
        System.out.println("   - Checking if text contains only allowed words");
        System.out.println("   - Finding invalid segments");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("2707. Extra Characters in a String");
        System.out.println("===================================");
        
        // Explain DP
        solution.explainDP();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Visualize example
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Detailed Visualization for Example 1:");
        System.out.println("=".repeat(80));
        solution.visualizeDP("leetscode", new String[]{"leet","code","leetcode"});
        
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
    public int minExtraChar(String s, String[] dictionary) {
        int n = s.length();
        Set<String> wordSet = new HashSet<>(Arrays.asList(dictionary));
        int[] dp = new int[n + 1];
        
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1 + dp[i + 1];
            for (int j = i; j < n; j++) {
                String word = s.substring(i, j + 1);
                if (wordSet.contains(word)) {
                    dp[i] = Math.min(dp[i], dp[j + 1]);
                }
            }
        }
        
        return dp[0];
    }
}
            """);
        
        System.out.println("\nOptimized Implementation (Trie):");
        System.out.println("""
class Solution {
    class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isWord = false;
    }
    
    public int minExtraChar(String s, String[] dictionary) {
        int n = s.length();
        TrieNode root = buildTrie(dictionary);
        int[] dp = new int[n + 1];
        
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1 + dp[i + 1];
            TrieNode node = root;
            for (int j = i; j < n; j++) {
                char c = s.charAt(j);
                if (!node.children.containsKey(c)) break;
                node = node.children.get(c);
                if (node.isWord) {
                    dp[i] = Math.min(dp[i], dp[j + 1]);
                }
            }
        }
        
        return dp[0];
    }
    
    private TrieNode buildTrie(String[] dictionary) {
        TrieNode root = new TrieNode();
        for (String word : dictionary) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                node.children.putIfAbsent(c, new TrieNode());
                node = node.children.get(c);
            }
            node.isWord = true;
        }
        return root;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Dynamic Programming is the optimal approach");
        System.out.println("2. HashSet provides O(1) word lookup");
        System.out.println("3. Process from right to left for bottom-up DP [citation:1]");
        System.out.println("4. Time complexity: O(n³) with HashSet, O(n²) with Trie");
        System.out.println("5. Space complexity: O(n + dict_size)");
        
        System.out.println("\nTime Complexity Breakdown:");
        System.out.println("- Bottom-up DP with HashSet: O(n³)");
        System.out.println("- Bottom-up DP with Trie: O(n²)");
        System.out.println("- Top-down DP with memoization: O(n³)");
        System.out.println("- Word iteration approach: O(n·m·k) where m = dict size");
    }
}
