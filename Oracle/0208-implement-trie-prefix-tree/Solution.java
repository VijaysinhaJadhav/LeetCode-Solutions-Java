
## Solution.java

```java
/**
 * 208. Implement Trie (Prefix Tree)
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Implement a trie (prefix tree) with insert, search, and startsWith methods.
 * 
 * Key Insights:
 * 1. Trie is a tree where each node represents a character
 * 2. Each node has children for possible next characters
 * 3. Mark nodes where a complete word ends
 * 4. Efficient for prefix-based operations
 */

class TrieNode {
    // Each node has 26 possible children for lowercase English letters
    private TrieNode[] children;
    // Marks if this node represents the end of a word
    private boolean isEnd;
    
    public TrieNode() {
        children = new TrieNode[26];
        isEnd = false;
    }
    
    public boolean containsKey(char ch) {
        return children[ch - 'a'] != null;
    }
    
    public TrieNode get(char ch) {
        return children[ch - 'a'];
    }
    
    public void put(char ch, TrieNode node) {
        children[ch - 'a'] = node;
    }
    
    public void setEnd() {
        isEnd = true;
    }
    
    public boolean isEnd() {
        return isEnd;
    }
}

class Trie {
    private TrieNode root;
    
    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }
    
    /** 
     * Inserts a word into the trie.
     * Time Complexity: O(L) where L is length of word
     * Space Complexity: O(L) for new nodes
     */
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (!node.containsKey(currentChar)) {
                node.put(currentChar, new TrieNode());
            }
            node = node.get(currentChar);
        }
        node.setEnd();
    }
    
    /**
     * Returns if the word is in the trie.
     * Time Complexity: O(L) where L is length of word
     */
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd();
    }
    
    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     * Time Complexity: O(P) where P is length of prefix
     */
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }
    
    /**
     * Helper method to search for a prefix in the trie.
     * Returns the node where the prefix ends, or null if prefix doesn't exist.
     */
    private TrieNode searchPrefix(String prefix) {
        TrieNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            char currentChar = prefix.charAt(i);
            if (node.containsKey(currentChar)) {
                node = node.get(currentChar);
            } else {
                return null;
            }
        }
        return node;
    }
    
    /**
     * Alternative: Map-based implementation (more flexible for any character set)
     */
    static class MapTrieNode {
        private java.util.Map<Character, MapTrieNode> children;
        private boolean isEnd;
        
        public MapTrieNode() {
            children = new java.util.HashMap<>();
            isEnd = false;
        }
    }
    
    /**
     * Additional method: Delete a word from trie (optional)
     */
    public void delete(String word) {
        delete(root, word, 0);
    }
    
    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            // If we reach the end of word
            if (!current.isEnd()) {
                return false; // Word doesn't exist
            }
            current.isEnd = false; // Unmark as end
            // Return true if node has no children
            return isEmpty(current);
        }
        
        char ch = word.charAt(index);
        TrieNode node = current.get(ch);
        if (node == null) {
            return false; // Word doesn't exist
        }
        
        boolean shouldDeleteCurrentNode = delete(node, word, index + 1);
        
        if (shouldDeleteCurrentNode) {
            current.children[ch - 'a'] = null;
            // Return true if no children left and not end of another word
            return isEmpty(current) && !current.isEnd();
        }
        
        return false;
    }
    
    private boolean isEmpty(TrieNode node) {
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Additional method: Count words with given prefix
     */
    public int countWordsWithPrefix(String prefix) {
        TrieNode node = searchPrefix(prefix);
        if (node == null) return 0;
        return countWordsFromNode(node);
    }
    
    private int countWordsFromNode(TrieNode node) {
        int count = 0;
        if (node.isEnd()) {
            count++;
        }
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                count += countWordsFromNode(node.children[i]);
            }
        }
        return count;
    }
    
    /**
     * Additional method: Get all words in trie
     */
    public java.util.List<String> getAllWords() {
        java.util.List<String> words = new java.util.ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        getAllWordsFromNode(root, currentWord, words);
        return words;
    }
    
    private void getAllWordsFromNode(TrieNode node, StringBuilder currentWord, java.util.List<String> words) {
        if (node.isEnd()) {
            words.add(currentWord.toString());
        }
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                currentWord.append((char)('a' + i));
                getAllWordsFromNode(node.children[i], currentWord, words);
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }
    
    /**
     * Helper: Visualize the trie structure
     */
    public void visualize() {
        System.out.println("\nTrie Visualization:");
        visualize(root, "", 0);
    }
    
    private void visualize(TrieNode node, String prefix, int level) {
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char ch = (char)('a' + i);
                String nodeStr = prefix + ch;
                String indent = "  ".repeat(level);
                System.out.println(indent + ch + (node.children[i].isEnd() ? " ✓" : ""));
                visualize(node.children[i], nodeStr, level + 1);
            }
        }
    }
    
    /**
     * Main method with tests
     */
    public static void main(String[] args) {
        System.out.println("Testing Trie Implementation");
        System.out.println("===========================\n");
        
        // Test 1: Basic operations
        System.out.println("Test 1: Basic Operations");
        Trie trie1 = new Trie();
        
        trie1.insert("apple");
        System.out.println("Insert: apple");
        
        boolean search1 = trie1.search("apple");
        System.out.println("Search 'apple': " + search1 + " (expected: true)");
        
        boolean search2 = trie1.search("app");
        System.out.println("Search 'app': " + search2 + " (expected: false)");
        
        boolean startsWith1 = trie1.startsWith("app");
        System.out.println("StartsWith 'app': " + startsWith1 + " (expected: true)");
        
        trie1.insert("app");
        System.out.println("Insert: app");
        
        boolean search3 = trie1.search("app");
        System.out.println("Search 'app': " + search3 + " (expected: true)");
        
        // Test 2: Multiple words
        System.out.println("\nTest 2: Multiple Words");
        Trie trie2 = new Trie();
        
        String[] words = {"cat", "car", "card", "care", "cart", "dog", "door", "dot"};
        for (String word : words) {
            trie2.insert(word);
            System.out.println("Insert: " + word);
        }
        
        System.out.println("\nSearch tests:");
        System.out.println("Search 'cat': " + trie2.search("cat") + " (expected: true)");
        System.out.println("Search 'car': " + trie2.search("car") + " (expected: true)");
        System.out.println("Search 'ca': " + trie2.search("ca") + " (expected: false)");
        System.out.println("Search 'dog': " + trie2.search("dog") + " (expected: true)");
        System.out.println("Search 'do': " + trie2.search("do") + " (expected: false)");
        
        System.out.println("\nStartsWith tests:");
        System.out.println("StartsWith 'ca': " + trie2.startsWith("ca") + " (expected: true)");
        System.out.println("StartsWith 'car': " + trie2.startsWith("car") + " (expected: true)");
        System.out.println("StartsWith 'do': " + trie2.startsWith("do") + " (expected: true)");
        System.out.println("StartsWith 'da': " + trie2.startsWith("da") + " (expected: false)");
        
        // Test 3: Additional features
        System.out.println("\nTest 3: Additional Features");
        trie2.visualize();
        
        System.out.println("\nAll words in trie:");
        java.util.List<String> allWords = trie2.getAllWords();
        System.out.println(allWords);
        
        System.out.println("\nCount words with prefix 'ca': " + trie2.countWordsWithPrefix("ca"));
        System.out.println("Count words with prefix 'car': " + trie2.countWordsWithPrefix("car"));
        System.out.println("Count words with prefix 'do': " + trie2.countWordsWithPrefix("do"));
        
        // Test 4: Delete operation
        System.out.println("\nTest 4: Delete Operation");
        trie2.delete("car");
        System.out.println("Deleted 'car'");
        System.out.println("Search 'car' after deletion: " + trie2.search("car") + " (expected: false)");
        System.out.println("Search 'card' after deletion: " + trie2.search("card") + " (expected: true)");
        System.out.println("StartsWith 'car' after deletion: " + trie2.startsWith("car") + " (expected: true)");
        
        // Test 5: Edge cases
        System.out.println("\nTest 5: Edge Cases");
        Trie trie3 = new Trie();
        
        System.out.println("Search empty string: " + trie3.search("") + " (expected: false)");
        System.out.println("StartsWith empty string: " + trie3.startsWith("") + " (expected: true)");
        
        trie3.insert("");
        System.out.println("Inserted empty string");
        System.out.println("Search empty string after insert: " + trie3.search("") + " (expected: true)");
        
        // Performance test
        System.out.println("\nPerformance Test:");
        Trie performanceTrie = new Trie();
        long startTime = System.currentTimeMillis();
        
        // Insert 10000 words
        for (int i = 0; i < 10000; i++) {
            performanceTrie.insert("word" + i);
        }
        long insertTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        // Search 10000 words
        for (int i = 0; i < 10000; i++) {
            performanceTrie.search("word" + i);
        }
        long searchTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        // Prefix search 10000 times
        for (int i = 0; i < 10000; i++) {
            performanceTrie.startsWith("wor");
        }
        long prefixTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Insert 10000 words: " + insertTime + "ms");
        System.out.println("Search 10000 words: " + searchTime + "ms");
        System.out.println("Prefix search 10000 times: " + prefixTime + "ms");
    }
}
