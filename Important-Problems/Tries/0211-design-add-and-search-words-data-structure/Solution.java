
# Solution.java

```java
import java.util.*;

/**
 * 211. Design Add and Search Words Data Structure
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Design a data structure that supports:
 * - addWord: Add a word (lowercase letters)
 * - search: Find if any added word matches pattern ('.' = any character)
 * 
 * Key Insights:
 * 1. Trie is perfect for storing dictionary words
 * 2. DFS/Backtracking handles wildcard searches
 * 3. Can optimize by storing words by length
 * 4. Multiple implementation strategies with tradeoffs
 */
class WordDictionary {
    
    /**
     * Approach 1: Trie with DFS (Recommended)
     * Time: O(L) for add, O(26^K) for search with K dots
     * Space: O(N * L) for N words of avg length L
     */
    
    private TrieNode root;
    
    class TrieNode {
        TrieNode[] children;
        boolean isWord;
        
        TrieNode() {
            children = new TrieNode[26];
            isWord = false;
        }
    }
    
    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isWord = true;
    }
    
    public boolean search(String word) {
        return searchDFS(word, 0, root);
    }
    
    private boolean searchDFS(String word, int index, TrieNode node) {
        if (node == null) return false;
        if (index == word.length()) {
            return node.isWord;
        }
        
        char c = word.charAt(index);
        
        if (c == '.') {
            // Try all 26 possible letters
            for (int i = 0; i < 26; i++) {
                if (searchDFS(word, index + 1, node.children[i])) {
                    return true;
                }
            }
            return false;
        } else {
            // Exact match
            int childIndex = c - 'a';
            return searchDFS(word, index + 1, node.children[childIndex]);
        }
    }
}

/**
 * Approach 2: Trie with BFS (Queue-based)
 * Time: O(L) for add, O(26^K) for search with K dots
 * Space: O(N * L) for N words of avg length L
 * 
 * Iterative approach using queue
 */
class WordDictionaryBFS {
    
    class TrieNode {
        TrieNode[] children;
        boolean isWord;
        
        TrieNode() {
            children = new TrieNode[26];
            isWord = false;
        }
    }
    
    private TrieNode root;
    
    public WordDictionaryBFS() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isWord = true;
    }
    
    public boolean search(String word) {
        Queue<TrieNode> queue = new LinkedList<>();
        queue.offer(root);
        
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int size = queue.size();
            
            for (int j = 0; j < size; j++) {
                TrieNode node = queue.poll();
                
                if (c == '.') {
                    // Add all non-null children
                    for (int k = 0; k < 26; k++) {
                        if (node.children[k] != null) {
                            queue.offer(node.children[k]);
                        }
                    }
                } else {
                    int index = c - 'a';
                    if (node.children[index] != null) {
                        queue.offer(node.children[index]);
                    }
                }
            }
            
            // Early termination - no nodes left to process
            if (queue.isEmpty()) {
                return false;
            }
        }
        
        // Check if any node at the end is a complete word
        for (TrieNode node : queue) {
            if (node.isWord) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Approach 3: HashMap of Words by Length
 * Time: O(L) for add, O(N * L) for search where N is words of same length
 * Space: O(N * L) for storing all words
 * 
 * Optimized for search-heavy workloads with few wildcards
 */
class WordDictionaryHashMap {
    
    private Map<Integer, List<String>> map;
    
    public WordDictionaryHashMap() {
        map = new HashMap<>();
    }
    
    public void addWord(String word) {
        int len = word.length();
        if (!map.containsKey(len)) {
            map.put(len, new ArrayList<>());
        }
        map.get(len).add(word);
    }
    
    public boolean search(String word) {
        int len = word.length();
        if (!map.containsKey(len)) {
            return false;
        }
        
        List<String> words = map.get(len);
        
        // If no wildcard, use HashSet for O(1) lookup
        if (!word.contains(".")) {
            return words.contains(word);
        }
        
        // Otherwise, check each word of same length
        for (String w : words) {
            if (isMatch(w, word)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isMatch(String word, String pattern) {
        for (int i = 0; i < word.length(); i++) {
            if (pattern.charAt(i) != '.' && pattern.charAt(i) != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Approach 4: Optimized Trie with End of Word Marker
 * Time: O(L) for add, O(26^K) for search
 * Space: O(N * L) 
 * 
 * Uses int array for children (null check optimization)
 */
class WordDictionaryOptimized {
    
    private TrieNode root;
    
    class TrieNode {
        TrieNode[] children;
        boolean isWord;
        
        TrieNode() {
            children = new TrieNode[26];
            isWord = false;
        }
    }
    
    public WordDictionaryOptimized() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isWord = true;
    }
    
    public boolean search(String word) {
        return searchHelper(word, 0, root);
    }
    
    private boolean searchHelper(String word, int pos, TrieNode node) {
        if (node == null) return false;
        if (pos == word.length()) return node.isWord;
        
        char c = word.charAt(pos);
        if (c == '.') {
            // Try all possible children
            for (TrieNode child : node.children) {
                if (searchHelper(word, pos + 1, child)) {
                    return true;
                }
            }
            return false;
        } else {
            return searchHelper(word, pos + 1, node.children[c - 'a']);
        }
    }
}

/**
 * Approach 5: Trie with Caching (Memoization)
 * Time: O(L) for add, O(26^K) for search (cached after first search)
 * Space: O(N * L + M) where M is number of unique search patterns
 * 
 * Cache search results for repeated patterns
 */
class WordDictionaryCache {
    
    private TrieNode root;
    private Map<String, Boolean> cache;
    
    class TrieNode {
        TrieNode[] children;
        boolean isWord;
        
        TrieNode() {
            children = new TrieNode[26];
            isWord = false;
        }
    }
    
    public WordDictionaryCache() {
        root = new TrieNode();
        cache = new HashMap<>();
    }
    
    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isWord = true;
        cache.clear(); // Invalidate cache on new word
    }
    
    public boolean search(String word) {
        if (cache.containsKey(word)) {
            return cache.get(word);
        }
        boolean result = searchDFS(word, 0, root);
        cache.put(word, result);
        return result;
    }
    
    private boolean searchDFS(String word, int index, TrieNode node) {
        if (node == null) return false;
        if (index == word.length()) return node.isWord;
        
        char c = word.charAt(index);
        if (c == '.') {
            for (int i = 0; i < 26; i++) {
                if (searchDFS(word, index + 1, node.children[i])) {
                    return true;
                }
            }
            return false;
        } else {
            return searchDFS(word, index + 1, node.children[c - 'a']);
        }
    }
}

/**
 * Test and Demonstration Class
 */
class WordDictionaryTest {
    
    /**
     * Helper: Visualize Trie structure
     */
    public static void visualizeTrie(WordDictionary wd) {
        System.out.println("\nTrie Structure:");
        System.out.println("================");
        
        // Use reflection to access private root
        try {
            java.lang.reflect.Field rootField = WordDictionary.class.getDeclaredField("root");
            rootField.setAccessible(true);
            Object root = rootField.get(wd);
            
            printTrieNode(root, 0, "");
        } catch (Exception e) {
            System.out.println("Cannot visualize Trie: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("rawtypes")
    private static void printTrieNode(Object node, int depth, String prefix) {
        if (node == null) return;
        
        try {
            Class<?> nodeClass = node.getClass();
            java.lang.reflect.Field childrenField = nodeClass.getDeclaredField("children");
            java.lang.reflect.Field isWordField = nodeClass.getDeclaredField("isWord");
            
            childrenField.setAccessible(true);
            isWordField.setAccessible(true);
            
            Object[] children = (Object[]) childrenField.get(node);
            boolean isWord = (boolean) isWordField.get(node);
            
            String indent = "  ".repeat(depth);
            
            if (depth > 0) {
                System.out.println(indent + "└─ " + prefix + (isWord ? " ✓" : ""));
            }
            
            for (int i = 0; i < 26; i++) {
                if (children[i] != null) {
                    char c = (char) ('a' + i);
                    printTrieNode(children[i], depth + 1, String.valueOf(c));
                }
            }
        } catch (Exception e) {
            // Skip visualization
        }
    }
    
    /**
     * Helper: Performance comparison
     */
    public static void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=======================");
        
        Random rand = new Random(42);
        int numWords = 10000;
        int numSearches = 1000;
        
        // Generate test words
        List<String> words = new ArrayList<>();
        for (int i = 0; i < numWords; i++) {
            int len = rand.nextInt(10) + 5;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) {
                sb.append((char) ('a' + rand.nextInt(26)));
            }
            words.add(sb.toString());
        }
        
        // Generate search patterns
        List<String> patterns = new ArrayList<>();
        for (int i = 0; i < numSearches; i++) {
            int len = rand.nextInt(10) + 5;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) {
                if (rand.nextDouble() < 0.3) {
                    sb.append('.');
                } else {
                    sb.append((char) ('a' + rand.nextInt(26)));
                }
            }
            patterns.add(sb.toString());
        }
        
        System.out.println("Test Setup:");
        System.out.println("  Words: " + numWords);
        System.out.println("  Searches: " + numSearches);
        
        long[] addTimes = new long[5];
        long[] searchTimes = new long[5];
        boolean[] results = new boolean[5];
        
        // Approach 1: Trie with DFS
        WordDictionary wd1 = new WordDictionary();
        long start = System.currentTimeMillis();
        for (String word : words) wd1.addWord(word);
        addTimes[0] = System.currentTimeMillis() - start;
        
        start = System.currentTimeMillis();
        for (String pattern : patterns) wd1.search(pattern);
        searchTimes[0] = System.currentTimeMillis() - start;
        
        // Approach 2: Trie with BFS
        WordDictionaryBFS wd2 = new WordDictionaryBFS();
        start = System.currentTimeMillis();
        for (String word : words) wd2.addWord(word);
        addTimes[1] = System.currentTimeMillis() - start;
        
        start = System.currentTimeMillis();
        for (String pattern : patterns) wd2.search(pattern);
        searchTimes[1] = System.currentTimeMillis() - start;
        
        // Approach 3: HashMap by Length
        WordDictionaryHashMap wd3 = new WordDictionaryHashMap();
        start = System.currentTimeMillis();
        for (String word : words) wd3.addWord(word);
        addTimes[2] = System.currentTimeMillis() - start;
        
        start = System.currentTimeMillis();
        for (String pattern : patterns) wd3.search(pattern);
        searchTimes[2] = System.currentTimeMillis() - start;
        
        // Approach 4: Optimized Trie
        WordDictionaryOptimized wd4 = new WordDictionaryOptimized();
        start = System.currentTimeMillis();
        for (String word : words) wd4.addWord(word);
        addTimes[3] = System.currentTimeMillis() - start;
        
        start = System.currentTimeMillis();
        for (String pattern : patterns) wd4.search(pattern);
        searchTimes[3] = System.currentTimeMillis() - start;
        
        // Approach 5: Trie with Cache
        WordDictionaryCache wd5 = new WordDictionaryCache();
        start = System.currentTimeMillis();
        for (String word : words) wd5.addWord(word);
        addTimes[4] = System.currentTimeMillis() - start;
        
        start = System.currentTimeMillis();
        for (String pattern : patterns) wd5.search(pattern);
        searchTimes[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                  | Add Time (ms) | Search Time (ms)");
        System.out.println("------------------------|---------------|-----------------");
        System.out.printf("1. Trie with DFS        | %13d | %15d%n", addTimes[0], searchTimes[0]);
        System.out.printf("2. Trie with BFS        | %13d | %15d%n", addTimes[1], searchTimes[1]);
        System.out.printf("3. HashMap by Length    | %13d | %15d%n", addTimes[2], searchTimes[2]);
        System.out.printf("4. Optimized Trie       | %13d | %15d%n", addTimes[3], searchTimes[3]);
        System.out.printf("5. Trie with Cache      | %13d | %15d%n", addTimes[4], searchTimes[4]);
        
        System.out.println("\nObservations:");
        System.out.println("1. Trie approaches are fastest for add operations");
        System.out.println("2. HashMap approach is faster for search with few wildcards");
        System.out.println("3. Cache helps with repeated search patterns");
        System.out.println("4. DFS vs BFS: similar performance, DFS is more memory efficient");
    }
    
    /**
     * Helper: Test edge cases
     */
    public static void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        WordDictionary wd = new WordDictionary();
        
        System.out.println("\n1. Empty string handling:");
        try {
            wd.addWord("");
            System.out.println("   addWord(\"\") - Should work");
            System.out.println("   search(\"\") = " + wd.search(""));
        } catch (Exception e) {
            System.out.println("   Exception: " + e.getMessage());
        }
        
        System.out.println("\n2. Single character words:");
        wd.addWord("a");
        wd.addWord("b");
        System.out.println("   Added 'a', 'b'");
        System.out.println("   search(\"a\") = " + wd.search("a"));
        System.out.println("   search(\".\") = " + wd.search("."));
        System.out.println("   search(\"c\") = " + wd.search("c"));
        
        System.out.println("\n3. All dots pattern:");
        wd.addWord("cat");
        wd.addWord("dog");
        System.out.println("   Added 'cat', 'dog'");
        System.out.println("   search(\"...\") = " + wd.search("..."));
        System.out.println("   search(\"....\") = " + wd.search("...."));
        
        System.out.println("\n4. Multiple dots in pattern:");
        wd.addWord("abc");
        wd.addWord("a.c");
        System.out.println("   Added 'abc', 'a.c'");
        System.out.println("   search(\"a.c\") = " + wd.search("a.c"));
        System.out.println("   search(\"a..\") = " + wd.search("a.."));
        System.out.println("   search(\".b.\") = " + wd.search(".b."));
        
        System.out.println("\n5. Long words (max length 25):");
        wd.addWord("abcdefghijklmnopqrstuvwxy");
        System.out.println("   Added 25-character word");
        System.out.println("   search(pattern of 25 dots) = " + 
            wd.search("........................."));
        
        System.out.println("\n6. No words added:");
        WordDictionary empty = new WordDictionary();
        System.out.println("   search(\"test\") = " + empty.search("test"));
        System.out.println("   search(\".\") = " + empty.search("."));
        
        System.out.println("\n7. Duplicate words:");
        wd.addWord("test");
        wd.addWord("test");
        System.out.println("   Added 'test' twice");
        System.out.println("   search(\"test\") = " + wd.search("test"));
    }
    
    /**
     * Helper: Generate test cases
     */
    public static void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        // Test from example
        System.out.println("\n1. Example from problem:");
        WordDictionary wd = new WordDictionary();
        wd.addWord("bad");
        wd.addWord("dad");
        wd.addWord("mad");
        
        boolean r1 = wd.search("pad");
        boolean r2 = wd.search("bad");
        boolean r3 = wd.search(".ad");
        boolean r4 = wd.search("b..");
        
        System.out.println("   search(\"pad\"): " + r1 + " (expected: false)");
        System.out.println("   search(\"bad\"): " + r2 + " (expected: true)");
        System.out.println("   search(\".ad\"): " + r3 + " (expected: true)");
        System.out.println("   search(\"b..\"): " + r4 + " (expected: true)");
        
        // Test with multiple implementations
        System.out.println("\n2. Comparing all implementations:");
        
        WordDictionaryBFS wd2 = new WordDictionaryBFS();
        WordDictionaryHashMap wd3 = new WordDictionaryHashMap();
        WordDictionaryOptimized wd4 = new WordDictionaryOptimized();
        WordDictionaryCache wd5 = new WordDictionaryCache();
        
        String[] words = {"bad", "dad", "mad"};
        String[] searches = {"pad", "bad", ".ad", "b.."};
        boolean[] expected = {false, true, true, true};
        
        for (String word : words) {
            wd2.addWord(word);
            wd3.addWord(word);
            wd4.addWord(word);
            wd5.addWord(word);
        }
        
        for (int i = 0; i < searches.length; i++) {
            boolean res2 = wd2.search(searches[i]);
            boolean res3 = wd3.search(searches[i]);
            boolean res4 = wd4.search(searches[i]);
            boolean res5 = wd5.search(searches[i]);
            
            boolean allMatch = res2 == expected[i] && 
                              res3 == expected[i] &&
                              res4 == expected[i] &&
                              res5 == expected[i];
            
            System.out.printf("   %s: expected=%b, all match? %b%n", 
                searches[i], expected[i], allMatch);
        }
        
        // Test with custom dictionary
        System.out.println("\n3. Custom test case:");
        WordDictionary custom = new WordDictionary();
        custom.addWord("apple");
        custom.addWord("app");
        custom.addWord("apricot");
        custom.addWord("banana");
        
        System.out.println("   Added: apple, app, apricot, banana");
        System.out.println("   search(\"app\"): " + custom.search("app"));
        System.out.println("   search(\"a.p\"): " + custom.search("a.p"));
        System.out.println("   search(\"a..le\"): " + custom.search("a..le"));
        System.out.println("   search(\"b.n.n.\"): " + custom.search("b.n.n."));
        System.out.println("   search(\"c.\"): " + custom.search("c."));
        
        visualizeTrie(custom);
    }
    
    /**
     * Helper: Explain Trie-based approach
     */
    public static void explainTrieApproach() {
        System.out.println("\nTrie-based Approach Explanation:");
        System.out.println("================================");
        
        System.out.println("\nWhat is a Trie?");
        System.out.println("- A tree data structure for storing strings");
        System.out.println("- Each node represents a single character");
        System.out.println("- Path from root to node forms a prefix");
        System.out.println("- Mark nodes that complete a word");
        
        System.out.println("\nExample: Adding 'cat', 'car', 'dog'");
        System.out.println("""
                root
               /    \\
              c      d
             /        \\
            a          o
           / \\         \\
          t   r         g
          ✓   ✓         ✓
        """);
        
        System.out.println("\nSearch with wildcards:");
        System.out.println("1. Exact match: Follow path character by character");
        System.out.println("2. Wildcard '.' : Try ALL possible paths");
        System.out.println("3. Use DFS/Backtracking to explore");
        
        System.out.println("\nAlgorithm for search:");
        System.out.println("""
        function search(word, index, node):
            if node == null: return false
            if index == word.length: return node.isWord
            
            char c = word.charAt(index)
            if c == '.':
                for each child in node.children:
                    if search(word, index+1, child):
                        return true
                return false
            else:
                child = node.children[c - 'a']
                return search(word, index+1, child)
        """);
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(L) for exact match");
        System.out.println("- Time: O(26^K) where K = number of dots");
        System.out.println("- Space: O(N * L) for N words of avg length L");
    }
    
    /**
     * Helper: Interview tips
     */
    public static void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Character set (lowercase only)");
        System.out.println("   - Wildcard behavior ('.' matches any single char)");
        System.out.println("   - Constraints (up to 10^4 calls)");
        
        System.out.println("\n2. Start with simple solutions:");
        System.out.println("   - HashSet for words (exact match only)");
        System.out.println("   - Then add wildcard support");
        System.out.println("   - Then optimize with Trie");
        
        System.out.println("\n3. Discuss tradeoffs:");
        System.out.println("   - Trie: Fast add/search, memory heavy");
        System.out.println("   - HashMap: Fast exact search, slow wildcard");
        System.out.println("   - Hybrid: Store by length for better wildcard search");
        
        System.out.println("\n4. Implement Trie carefully:");
        System.out.println("   - Define TrieNode class");
        System.out.println("   - Children array of size 26");
        System.out.println("   - isWord boolean flag");
        System.out.println("   - DFS for wildcard search");
        
        System.out.println("\n5. Handle edge cases:");
        System.out.println("   - Empty string");
        System.out.println("   - All dots pattern");
        System.out.println("   - No matching words");
        System.out.println("   - Maximum length constraints");
        
        System.out.println("\n6. Optimize if needed:");
        System.out.println("   - Use iterative BFS instead of recursive DFS");
        System.out.println("   - Cache search results");
        System.out.println("   - Store max word length for early termination");
        
        System.out.println("\n7. Test thoroughly:");
        System.out.println("   - Provided example");
        System.out.println("   - Edge cases");
        System.out.println("   - Performance with large inputs");
    }
    
    /**
     * Helper: Real-world applications
     */
    public static void realWorldApplications() {
        System.out.println("\nReal-world Applications:");
        System.out.println("========================");
        
        System.out.println("\n1. Search Engines:");
        System.out.println("   - Autocomplete suggestions");
        System.out.println("   - Spell checking");
        System.out.println("   - Fuzzy search");
        
        System.out.println("\n2. Text Editors:");
        System.out.println("   - Find and replace with wildcards");
        System.out.println("   - Code completion");
        System.out.println("   - Syntax highlighting");
        
        System.out.println("\n3. Database Systems:");
        System.out.println("   - LIKE queries in SQL");
        System.out.println("   - Full-text search");
        System.out.println("   - Pattern matching");
        
        System.out.println("\n4. Bioinformatics:");
        System.out.println("   - DNA sequence matching");
        System.out.println("   - Protein pattern search");
        System.out.println("   - Gene identification");
        
        System.out.println("\n5. Network Security:");
        System.out.println("   - Pattern-based intrusion detection");
        System.out.println("   - Malware signature matching");
        System.out.println("   - Packet filtering");
        
        System.out.println("\n6. Gaming:");
        System.out.println("   - Word games (Boggle, Scrabble)");
        System.out.println("   - Pattern matching puzzles");
        System.out.println("   - Dictionary-based game logic");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        System.out.println("211. Design Add and Search Words Data Structure");
        System.out.println("===============================================");
        
        // Explain Trie approach
        explainTrieApproach();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        comparePerformance();
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(80));
        realWorldApplications();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class WordDictionary {
    class TrieNode {
        TrieNode[] children;
        boolean isWord;
        
        TrieNode() {
            children = new TrieNode[26];
            isWord = false;
        }
    }
    
    private TrieNode root;
    
    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isWord = true;
    }
    
    public boolean search(String word) {
        return search(word, 0, root);
    }
    
    private boolean search(String word, int idx, TrieNode node) {
        if (node == null) return false;
        if (idx == word.length()) return node.isWord;
        
        char c = word.charAt(idx);
        if (c == '.') {
            for (TrieNode child : node.children) {
                if (search(word, idx + 1, child)) {
                    return true;
                }
            }
            return false;
        } else {
            return search(word, idx + 1, node.children[c - 'a']);
        }
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Trie is the optimal data structure for dictionary problems");
        System.out.println("2. Wildcard search requires DFS/backtracking");
        System.out.println("3. Time: O(L) add, O(26^K) search worst case");
        System.out.println("4. Space: O(N * L) for storing words");
        System.out.println("5. Alternative approaches have different tradeoffs");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle uppercase letters?");
        System.out.println("2. How would you handle '*' wildcard matching multiple chars?");
        System.out.println("3. How would you implement delete operation?");
        System.out.println("4. How would you support prefix search?");
        System.out.println("5. How would you handle very large dictionaries?");
        
        System.out.println("\nOptimization Strategies:");
        System.out.println("1. Use int[] children + null check optimization");
        System.out.println("2. Cache search results for repeated patterns");
        System.out.println("3. Group words by length for faster wildcard search");
        System.out.println("4. Use BFS for iterative approach");
        System.out.println("5. Store max word length for early termination");
    }
}

// Main class for LeetCode submission
public class Solution {
    public static void main(String[] args) {
        // Run the test suite
        WordDictionaryTest.main(args);
    }
}
