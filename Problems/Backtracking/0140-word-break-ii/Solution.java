
# Solution.java

```java
import java.util.*;

/**
 * 140. Word Break II
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given string s and dictionary wordDict, return all possible sentences
 * formed by breaking s into dictionary words.
 * 
 * Key Insights:
 * 1. DFS/Backtracking to explore all possibilities
 * 2. Memoization to avoid recomputation
 * 3. Word Break I check for early termination
 * 4. Trie for efficient dictionary lookups
 */
class Solution {
    
    /**
     * Approach 1: DFS + Memoization (Recommended)
     * Time: O(n × 2^n), Space: O(n × 2^n) for results
     * Most intuitive and efficient for this problem
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        Map<Integer, List<String>> memo = new HashMap<>();
        return dfs(s, 0, dict, memo);
    }
    
    private List<String> dfs(String s, int start, Set<String> dict, 
                             Map<Integer, List<String>> memo) {
        // Check memo
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        
        List<String> result = new ArrayList<>();
        
        // Base case: reached end of string
        if (start == s.length()) {
            result.add("");
            return result;
        }
        
        // Try all possible end positions
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (dict.contains(word)) {
                // Get all sentences from the rest of the string
                List<String> subSentences = dfs(s, end, dict, memo);
                
                // Combine current word with each subsentence
                for (String subSentence : subSentences) {
                    if (subSentence.isEmpty()) {
                        result.add(word);
                    } else {
                        result.add(word + " " + subSentence);
                    }
                }
            }
        }
        
        // Memoize and return
        memo.put(start, result);
        return result;
    }
    
    /**
     * Approach 2: DFS with Word Break I optimization
     * Time: O(n × 2^n), Space: O(n × 2^n)
     * First checks if word break is possible using DP
     */
    public List<String> wordBreak2(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        
        // First check if word break is possible (Word Break I)
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        
        // If no solution possible, return empty list
        if (!dp[s.length()]) {
            return new ArrayList<>();
        }
        
        // Now find all solutions
        Map<Integer, List<String>> memo = new HashMap<>();
        return dfs2(s, 0, dict, memo);
    }
    
    private List<String> dfs2(String s, int start, Set<String> dict, 
                              Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        
        List<String> result = new ArrayList<>();
        
        if (start == s.length()) {
            result.add("");
            return result;
        }
        
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (dict.contains(word)) {
                List<String> subSentences = dfs2(s, end, dict, memo);
                for (String sub : subSentences) {
                    result.add(word + (sub.isEmpty() ? "" : " " + sub));
                }
            }
        }
        
        memo.put(start, result);
        return result;
    }
    
    /**
     * Approach 3: BFS with memoization
     * Time: O(n × 2^n), Space: O(n × 2^n)
     * Breadth-first approach, explores level by level
     */
    public List<String> wordBreak3(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        Map<Integer, List<List<String>>> dp = new HashMap<>();
        
        // Initialize DP
        dp.put(0, new ArrayList<>());
        dp.get(0).add(new ArrayList<>()); // Empty list for position 0
        
        for (int i = 1; i <= s.length(); i++) {
            dp.put(i, new ArrayList<>());
            
            for (int j = 0; j < i; j++) {
                String word = s.substring(j, i);
                if (dict.contains(word) && !dp.get(j).isEmpty()) {
                    // Combine words from position j with current word
                    for (List<String> prefix : dp.get(j)) {
                        List<String> newSentence = new ArrayList<>(prefix);
                        newSentence.add(word);
                        dp.get(i).add(newSentence);
                    }
                }
            }
        }
        
        // Convert to required format
        List<String> result = new ArrayList<>();
        for (List<String> sentence : dp.get(s.length())) {
            result.add(String.join(" ", sentence));
        }
        return result;
    }
    
    /**
     * Approach 4: Trie + Backtracking
     * Time: O(n × 2^n), Space: O(n × 2^n + T) where T is trie size
     * Uses trie for efficient dictionary lookups
     */
    public List<String> wordBreak4(String s, List<String> wordDict) {
        // Build trie
        TrieNode root = buildTrie(wordDict);
        
        // DFS with memoization
        Map<Integer, List<String>> memo = new HashMap<>();
        return dfsWithTrie(s, 0, root, memo);
    }
    
    private List<String> dfsWithTrie(String s, int start, TrieNode root, 
                                     Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        
        List<String> result = new ArrayList<>();
        
        if (start == s.length()) {
            result.add("");
            return result;
        }
        
        TrieNode node = root;
        for (int end = start; end < s.length(); end++) {
            char c = s.charAt(end);
            if (node.children[c - 'a'] == null) {
                break;
            }
            
            node = node.children[c - 'a'];
            if (node.isWord) {
                String word = s.substring(start, end + 1);
                List<String> subSentences = dfsWithTrie(s, end + 1, root, memo);
                for (String sub : subSentences) {
                    result.add(word + (sub.isEmpty() ? "" : " " + sub));
                }
            }
        }
        
        memo.put(start, result);
        return result;
    }
    
    private TrieNode buildTrie(List<String> words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }
            node.isWord = true;
        }
        return root;
    }
    
    class TrieNode {
        TrieNode[] children;
        boolean isWord;
        
        TrieNode() {
            children = new TrieNode[26];
            isWord = false;
        }
    }
    
    /**
     * Approach 5: Optimized with max word length
     * Time: O(n × 2^n), Space: O(n × 2^n)
     * Only tries word lengths up to max dictionary word length
     */
    public List<String> wordBreak5(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        int maxLen = 0;
        for (String word : wordDict) {
            maxLen = Math.max(maxLen, word.length());
        }
        
        Map<Integer, List<String>> memo = new HashMap<>();
        return dfsOptimized(s, 0, dict, maxLen, memo);
    }
    
    private List<String> dfsOptimized(String s, int start, Set<String> dict, 
                                      int maxLen, Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        
        List<String> result = new ArrayList<>();
        
        if (start == s.length()) {
            result.add("");
            return result;
        }
        
        // Only try up to max word length
        for (int end = start + 1; end <= s.length() && end - start <= maxLen; end++) {
            String word = s.substring(start, end);
            if (dict.contains(word)) {
                List<String> subSentences = dfsOptimized(s, end, dict, maxLen, memo);
                for (String sub : subSentences) {
                    result.add(word + (sub.isEmpty() ? "" : " " + sub));
                }
            }
        }
        
        memo.put(start, result);
        return result;
    }
    
    /**
     * Helper: Visualize the DFS/memoization process
     */
    public void visualizeWordBreak(String s, List<String> wordDict) {
        System.out.println("\nVisualizing Word Break II:");
        System.out.println("String: \"" + s + "\"");
        System.out.println("Dictionary: " + wordDict);
        System.out.println("\nDFS Exploration:");
        
        Set<String> dict = new HashSet<>(wordDict);
        Map<Integer, List<String>> memo = new HashMap<>();
        List<String> result = dfsVisualized(s, 0, dict, memo, 0);
        
        System.out.println("\nFinal Results:");
        if (result.isEmpty()) {
            System.out.println("No valid word breaks found");
        } else {
            for (int i = 0; i < result.size(); i++) {
                System.out.println((i + 1) + ". \"" + result.get(i) + "\"");
            }
        }
    }
    
    private List<String> dfsVisualized(String s, int start, Set<String> dict, 
                                       Map<Integer, List<String>> memo, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "dfs(" + start + "): exploring from position " + start);
        
        if (memo.containsKey(start)) {
            System.out.println(indent + "  memo hit! returning cached result for position " + start);
            return memo.get(start);
        }
        
        List<String> result = new ArrayList<>();
        
        if (start == s.length()) {
            System.out.println(indent + "  reached end of string, returning empty sentence");
            result.add("");
            return result;
        }
        
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            System.out.println(indent + "  trying word: \"" + word + "\" [" + start + "," + end + ")");
            
            if (dict.contains(word)) {
                System.out.println(indent + "    \"" + word + "\" is in dictionary");
                List<String> subSentences = dfsVisualized(s, end, dict, memo, depth + 1);
                
                for (String subSentence : subSentences) {
                    String newSentence;
                    if (subSentence.isEmpty()) {
                        newSentence = word;
                    } else {
                        newSentence = word + " " + subSentence;
                    }
                    System.out.println(indent + "    formed: \"" + newSentence + "\"");
                    result.add(newSentence);
                }
            } else {
                System.out.println(indent + "    \"" + word + "\" NOT in dictionary");
            }
        }
        
        System.out.println(indent + "  memoizing result for position " + start + ": " + result);
        memo.put(start, result);
        return result;
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            // {s, wordDict, expectedCount}
            {"catsanddog", 
             Arrays.asList("cat","cats","and","sand","dog"),
             2},  // ["cats and dog","cat sand dog"]
            
            {"pineapplepenapple", 
             Arrays.asList("apple","pen","applepen","pine","pineapple"),
             3},  // ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
            
            {"catsandog", 
             Arrays.asList("cats","dog","sand","and","cat"),
             0},  // []
            
            {"aaaa", 
             Arrays.asList("a","aa","aaa","aaaa"),
             8},  // All combinations
            
            {"test", 
             Arrays.asList("test"),
             1},  // ["test"]
            
            {"", 
             Arrays.asList("a"),
             0},  // Empty string case
            
            {"leetcode", 
             Arrays.asList("leet","code"),
             1},  // ["leet code"]
            
            {"aaaaaaaaaaaaaaaaaaaa", 
             Arrays.asList("a","aa","aaa","aaaa","aaaaa"),
             -1}, // Large number of solutions
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
            @SuppressWarnings("unchecked")
            List<String> wordDict = (List<String>) testCases[i][1];
            int expectedCount = (int) testCases[i][2];
            
            System.out.printf("\nTest %d: s=\"%s\", dict=%s%n", 
                i + 1, s, wordDict);
            
            List<String> result1 = wordBreak(s, wordDict);
            List<String> result2 = wordBreak2(s, wordDict);
            List<String> result3 = wordBreak3(s, wordDict);
            List<String> result4 = wordBreak4(s, wordDict);
            List<String> result5 = wordBreak5(s, wordDict);
            
            // Sort for comparison
            Collections.sort(result1);
            Collections.sort(result2);
            Collections.sort(result3);
            Collections.sort(result4);
            Collections.sort(result5);
            
            boolean allMatch = result1.equals(result2) && 
                              result2.equals(result3) &&
                              result3.equals(result4) &&
                              result4.equals(result5);
            
            if (expectedCount >= 0 && result1.size() == expectedCount && allMatch) {
                System.out.println("✓ PASS - Found " + result1.size() + " solutions");
                passed++;
                
                if (result1.size() <= 5) {
                    System.out.println("  Solutions: " + result1);
                } else {
                    System.out.println("  First 5 solutions: " + result1.subList(0, 5));
                }
            } else if (expectedCount == -1 && allMatch) {
                // For large test cases, just check that all methods agree
                System.out.println("✓ PASS - All methods agree, found " + result1.size() + " solutions");
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected " + expectedCount + " solutions");
                System.out.println("  Method 1: " + result1.size() + " solutions " + 
                    (result1.size() <= 10 ? result1 : ""));
                System.out.println("  Method 2: " + result2.size() + " solutions");
                System.out.println("  Method 3: " + result3.size() + " solutions");
                System.out.println("  Method 4: " + result4.size() + " solutions");
                System.out.println("  Method 5: " + result5.size() + " solutions");
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
        
        // Test case with moderate complexity
        String s = "aaaaaaaa";
        List<String> wordDict = Arrays.asList("a", "aa", "aaa", "aaaa");
        
        System.out.println("Test: s=\"" + s + "\", dict=" + wordDict);
        System.out.println("Number of solutions: " + (int)Math.pow(2, s.length() - 1));
        
        long[] times = new long[5];
        List<String>[] results = new List[5];
        
        // Method 1: DFS + Memoization
        long start = System.currentTimeMillis();
        results[0] = wordBreak(s, wordDict);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: DFS with Word Break I optimization
        start = System.currentTimeMillis();
        results[1] = wordBreak2(s, wordDict);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: BFS with memoization
        start = System.currentTimeMillis();
        results[2] = wordBreak3(s, wordDict);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Trie + Backtracking
        start = System.currentTimeMillis();
        results[3] = wordBreak4(s, wordDict);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Optimized with max word length
        start = System.currentTimeMillis();
        results[4] = wordBreak5(s, wordDict);
        times[4] = System.currentTimeMillis() - start;
        
        // Verify all results are the same
        boolean allSame = true;
        for (int i = 1; i < 5; i++) {
            Collections.sort(results[0]);
            Collections.sort(results[i]);
            if (!results[0].equals(results[i])) {
                allSame = false;
                break;
            }
        }
        
        System.out.println("\nResults:");
        System.out.println("Method                     | Time (ms) | Solutions | Correct?");
        System.out.println("---------------------------|-----------|-----------|---------");
        System.out.printf("1. DFS + Memoization       | %9d | %9d | %s%n",
            times[0], results[0].size(), "✓ (baseline)");
        System.out.printf("2. DFS with Word Break I   | %9d | %9d | %s%n",
            times[1], results[1].size(), results[1].size() == results[0].size() ? "✓" : "✗");
        System.out.printf("3. BFS with memoization    | %9d | %9d | %s%n",
            times[2], results[2].size(), results[2].size() == results[0].size() ? "✓" : "✗");
        System.out.printf("4. Trie + Backtracking     | %9d | %9d | %s%n",
            times[3], results[3].size(), results[3].size() == results[0].size() ? "✓" : "✗");
        System.out.printf("5. Optimized max word len  | %9d | %9d | %s%n",
            times[4], results[4].size(), results[4].size() == results[0].size() ? "✓" : "✗");
        
        System.out.println("\nObservations:");
        System.out.println("1. All methods find the same number of solutions: " + allSame);
        System.out.println("2. DFS + Memoization is usually most efficient");
        System.out.println("3. Trie helps when dictionary is large");
        System.out.println("4. Word Break I optimization helps prune impossible cases early");
        System.out.println("5. Max word length optimization reduces unnecessary checks");
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("======================");
        
        System.out.println("\nProblem: Find ALL ways to break string into dictionary words");
        
        System.out.println("\nKey Insight:");
        System.out.println("If we know all ways to break suffix s[i:],");
        System.out.println("then for prefix s[0:i] that's a dictionary word,");
        System.out.println("we can combine it with each suffix break.");
        
        System.out.println("\nRecursive Formulation:");
        System.out.println("wordBreak(s, i) = {");
        System.out.println("  if i == n: return [\"\"]");
        System.out.println("  result = []");
        System.out.println("  for j = i+1 to n:");
        System.out.println("    word = s[i:j]");
        System.out.println("    if word in dict:");
        System.out.println("      for each suffix in wordBreak(s, j):");
        System.out.println("        if suffix == \"\": result.add(word)");
        System.out.println("        else: result.add(word + \" \" + suffix)");
        System.out.println("  return result");
        System.out.println("}");
        
        System.out.println("\nMemoization:");
        System.out.println("Store results for each starting position i");
        System.out.println("Avoid recomputing same subproblems");
        System.out.println("Map<Integer, List<String>> memo");
        
        System.out.println("\nExample: s = \"catsanddog\", dict = [\"cat\",\"cats\",\"and\",\"sand\",\"dog\"]");
        System.out.println("\nStep-by-step:");
        System.out.println("1. Start at position 0:");
        System.out.println("   Try \"cat\" (in dict) -> recurse on \"sanddog\"");
        System.out.println("   Try \"cats\" (in dict) -> recurse on \"anddog\"");
        System.out.println("2. Position 3 (\"sanddog\"):");
        System.out.println("   Try \"sand\" -> recurse on \"dog\"");
        System.out.println("3. Position 7 (\"dog\"):");
        System.out.println("   Try \"dog\" -> reaches end, returns [\"\"]");
        System.out.println("   Combine: \"dog\" + \"\" = \"dog\"");
        System.out.println("4. Backtrack: \"sand\" + \" dog\" = \"sand dog\"");
        System.out.println("5. Backtrack: \"cat\" + \" sand dog\" = \"cat sand dog\"");
        System.out.println("6. Similarly for \"cats and dog\"");
    }
    
    /**
     * Helper: Edge cases testing
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Empty string:");
        List<String> result1 = wordBreak("", Arrays.asList("a", "b"));
        System.out.println("   Input: \"\", dict=[\"a\",\"b\"]");
        System.out.println("   Output: " + result1 + " (should be empty list)");
        
        System.out.println("\n2. Single character string that's in dictionary:");
        List<String> result2 = wordBreak("a", Arrays.asList("a", "b"));
        System.out.println("   Input: \"a\", dict=[\"a\",\"b\"]");
        System.out.println("   Output: " + result2 + " (should be [\"a\"])");
        
        System.out.println("\n3. String with no possible breaks:");
        List<String> result3 = wordBreak("abc", Arrays.asList("def", "ghi"));
        System.out.println("   Input: \"abc\", dict=[\"def\",\"ghi\"]");
        System.out.println("   Output: " + result3 + " (should be [])");
        
        System.out.println("\n4. String that matches entire dictionary word:");
        List<String> result4 = wordBreak("hello", Arrays.asList("hello", "world"));
        System.out.println("   Input: \"hello\", dict=[\"hello\",\"world\"]");
        System.out.println("   Output: " + result4 + " (should be [\"hello\"])");
        
        System.out.println("\n5. String with multiple overlapping words:");
        List<String> result5 = wordBreak("aaa", Arrays.asList("a", "aa", "aaa"));
        System.out.println("   Input: \"aaa\", dict=[\"a\",\"aa\",\"aaa\"]");
        System.out.println("   Output: " + result5.size() + " solutions: " + 
            (result5.size() <= 10 ? result5 : "first 10: " + result5.subList(0, 10)));
        System.out.println("   Expected: 3 solutions: [\"a a a\", \"a aa\", \"aaa\"]");
        
        System.out.println("\n6. Dictionary with words that are prefixes of each other:");
        List<String> result6 = wordBreak("programming", 
            Arrays.asList("pro", "gram", "program", "programming", "ming"));
        System.out.println("   Input: \"programming\", dict=[\"pro\",\"gram\",\"program\",\"programming\",\"ming\"]");
        System.out.println("   Output: " + result6);
    }
    
    /**
     * Helper: Optimization techniques
     */
    public void explainOptimizations() {
        System.out.println("\nOptimization Techniques:");
        System.out.println("========================");
        
        System.out.println("\n1. Memoization (Most Important):");
        System.out.println("   - Cache results for each starting position");
        System.out.println("   - Avoid exponential recomputation");
        System.out.println("   - Turns O(2^n) into more manageable complexity");
        
        System.out.println("\n2. Word Break I Check:");
        System.out.println("   - First check if ANY break is possible");
        System.out.println("   - Use DP from Word Break problem");
        System.out.println("   - Early return if no solution exists");
        
        System.out.println("\n3. Trie for Dictionary Lookup:");
        System.out.println("   - Efficient prefix checking");
        System.out.println("   - Stop early when no words match current prefix");
        System.out.println("   - Especially helpful for large dictionaries");
        
        System.out.println("\n4. Max Word Length Pruning:");
        System.out.println("   - Track maximum word length in dictionary");
        System.out.println("   - Don't try substrings longer than max length");
        System.out.println("   - Reduces number of substring checks");
        
        System.out.println("\n5. BFS vs DFS:");
        System.out.println("   - DFS: More memory efficient for recursion");
        System.out.println("   - BFS: Can find shortest breaks first");
        System.out.println("   - Choose based on specific needs");
        
        System.out.println("\n6. String Building Optimization:");
        System.out.println("   - Use StringBuilder for concatenation");
        System.out.println("   - Build sentences from end to start");
        System.out.println("   - Avoid creating intermediate strings");
        
        System.out.println("\n7. Early Exit for Impossible Prefixes:");
        System.out.println("   - Track positions that lead to dead ends");
        System.out.println("   - Similar to memoization but for failure cases");
        System.out.println("   - Can use boolean array for impossible positions");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void realWorldApplications() {
        System.out.println("\nReal-world Applications:");
        System.out.println("========================");
        
        System.out.println("\n1. Text Segmentation:");
        System.out.println("   - Word segmentation in languages without spaces (Chinese, Japanese)");
        System.out.println("   - Tokenization in natural language processing");
        System.out.println("   - URL segmentation (camelCase to words)");
        
        System.out.println("\n2. Compiler Design:");
        System.out.println("   - Lexical analysis (tokenization)");
        System.out.println("   - Identifying keywords and identifiers");
        System.out.println("   - Syntax highlighting");
        
        System.out.println("\n3. Search Engines:");
        System.out.println("   - Query segmentation");
        System.out.println("   - Phrase matching");
        System.out.println("   - Autocomplete suggestions");
        
        System.out.println("\n4. Bioinformatics:");
        System.out.println("   - DNA/RNA sequence segmentation");
        System.out.println("   - Protein sequence analysis");
        System.out.println("   - Pattern matching in genetic codes");
        
        System.out.println("\n5. Data Compression:");
        System.out.println("   - Dictionary-based compression (LZW)");
        System.out.println("   - Finding optimal segmentation for compression");
        System.out.println("   - Text encoding optimization");
        
        System.out.println("\n6. Machine Learning:");
        System.out.println("   - Feature extraction from text");
        System.out.println("   - Word embedding generation");
        System.out.println("   - Text classification preprocessing");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Understand the difference from Word Break I:");
        System.out.println("   - Word Break I: Check IF possible (boolean)");
        System.out.println("   - Word Break II: Find ALL ways (list of strings)");
        System.out.println("   - Much harder due to exponential possibilities");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Mention exponential backtracking approach");
        System.out.println("   - Acknowledge it's too slow for constraints");
        System.out.println("   - Use as basis for optimization");
        
        System.out.println("\n3. Propose memoization:");
        System.out.println("   - Key insight for this problem");
        System.out.println("   - Cache results by starting position");
        System.out.println("   - Dramatically reduces time complexity");
        
        System.out.println("\n4. Consider Word Break I optimization:");
        System.out.println("   - First check if any solution exists");
        System.out.println("   - Early return for impossible cases");
        System.out.println("   - Reduces wasted computation");
        
        System.out.println("\n5. Discuss dictionary representation:");
        System.out.println("   - HashSet for O(1) lookups");
        System.out.println("   - Trie for prefix checking");
        System.out.println("   - Consider max word length for pruning");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - Empty string");
        System.out.println("   - No possible breaks");
        System.out.println("   - String equals dictionary word");
        System.out.println("   - Overlapping dictionary words");
        
        System.out.println("\n7. Optimize implementation:");
        System.out.println("   - Use StringBuilder for string building");
        System.out.println("   - Build sentences from end to start");
        System.out.println("   - Avoid creating unnecessary intermediate lists");
        
        System.out.println("\n8. Discuss complexity:");
        System.out.println("   - Time: O(n × 2^n) worst case");
        System.out.println("   - Space: O(n × 2^n) for storing results");
        System.out.println("   - Explain why memoization helps");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("140. Word Break II");
        System.out.println("==================");
        
        // Explain algorithm
        solution.explainAlgorithm();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Visualize example
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Visualizing Example:");
        System.out.println("=".repeat(80));
        
        solution.visualizeWordBreak("catsanddog", 
            Arrays.asList("cat", "cats", "and", "sand", "dog"));
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Optimization techniques
        System.out.println("\n" + "=".repeat(80));
        solution.explainOptimizations();
        
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
        
        System.out.println("\nRecommended Implementation (DFS + Memoization):");
        System.out.println("""
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        Map<Integer, List<String>> memo = new HashMap<>();
        return dfs(s, 0, dict, memo);
    }
    
    private List<String> dfs(String s, int start, Set<String> dict, 
                             Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        
        List<String> result = new ArrayList<>();
        
        if (start == s.length()) {
            result.add("");
            return result;
        }
        
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (dict.contains(word)) {
                List<String> subSentences = dfs(s, end, dict, memo);
                for (String sub : subSentences) {
                    result.add(word + (sub.isEmpty() ? "" : " " + sub));
                }
            }
        }
        
        memo.put(start, result);
        return result;
    }
}
            """);
        
        System.out.println("\nOptimized Implementation (with Word Break I check):");
        System.out.println("""
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        
        // First check if word break is possible
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        
        if (!dp[s.length()]) {
            return new ArrayList<>();
        }
        
        // Find all solutions
        Map<Integer, List<String>> memo = new HashMap<>();
        return dfs(s, 0, dict, memo);
    }
    
    private List<String> dfs(String s, int start, Set<String> dict, 
                             Map<Integer, List<String>> memo) {
        // ... same as above ...
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Memoization is crucial for performance");
        System.out.println("2. Word Break I check provides early exit for impossible cases");
        System.out.println("3. HashSet provides O(1) dictionary lookups");
        System.out.println("4. Build sentences from right to left for efficiency");
        System.out.println("5. Handle empty string and no solution cases");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n × 2^n) worst case, but memoization helps significantly");
        System.out.println("- Space: O(n × 2^n) for storing all solutions");
        System.out.println("- Without memoization: O(2^n) exponential");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you optimize for a very large dictionary?");
        System.out.println("2. How would you handle very long input strings?");
        System.out.println("3. How would you modify to return only the shortest break?");
        System.out.println("4. How would you make it case-insensitive?");
        System.out.println("5. How would you handle dictionary words with spaces?");
    }
}
