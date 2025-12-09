/**
 * 14. Longest Common Prefix
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string "".
 * 
 * Key Insights:
 * 1. The common prefix cannot be longer than the shortest string
 * 2. We can compare characters at the same position across all strings
 * 3. Stop at the first position where characters don't match
 * 4. Multiple approaches: vertical scanning, horizontal scanning, divide and conquer, trie
 * 
 * Approach:
 * 1. Check edge cases: empty array, empty strings
 * 2. Use the first string as reference
 * 3. For each character position, check if all strings have the same character
 * 4. If mismatch found or reached end of shortest string, return prefix so far
 * 
 * Time Complexity: O(S) where S is total characters in all strings
 * Space Complexity: O(1) - constant extra space
 * 
 * Tags: String, Trie, Divide-and-Conquer
 */

class Solution {
    /**
     * Vertical Scanning Approach - Recommended
     * Compare all strings character by character at each position
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        // Use first string as reference
        for (int i = 0; i < strs[0].length(); i++) {
            char currentChar = strs[0].charAt(i);
            
            // Check this character in all other strings
            for (int j = 1; j < strs.length; j++) {
                // If current string is shorter or characters don't match
                if (i >= strs[j].length() || strs[j].charAt(i) != currentChar) {
                    return strs[0].substring(0, i);
                }
            }
        }
        
        // First string is the common prefix
        return strs[0];
    }
    
    /**
     * Horizontal Scanning Approach
     * Compare strings sequentially: LCP(strs[0], strs[1]), then with strs[2], etc.
     */
    public String longestCommonPrefixHorizontal(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        String prefix = strs[0];
        
        for (int i = 1; i < strs.length; i++) {
            // Reduce prefix until it matches current string
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        
        return prefix;
    }
    
    /**
     * Divide and Conquer Approach
     * Split the array and find LCP of halves, then combine results
     */
    public String longestCommonPrefixDivideConquer(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        return longestCommonPrefix(strs, 0, strs.length - 1);
    }
    
    private String longestCommonPrefix(String[] strs, int left, int right) {
        if (left == right) {
            return strs[left];
        }
        
        int mid = (left + right) / 2;
        String lcpLeft = longestCommonPrefix(strs, left, mid);
        String lcpRight = longestCommonPrefix(strs, mid + 1, right);
        
        return commonPrefix(lcpLeft, lcpRight);
    }
    
    private String commonPrefix(String left, String right) {
        int minLength = Math.min(left.length(), right.length());
        for (int i = 0; i < minLength; i++) {
            if (left.charAt(i) != right.charAt(i)) {
                return left.substring(0, i);
            }
        }
        return left.substring(0, minLength);
    }
    
    /**
     * Trie-based Approach
     * Build a trie and find the deepest node with count equal to array size
     */
    public String longestCommonPrefixTrie(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        if (strs.length == 1) {
            return strs[0];
        }
        
        // Build trie
        Trie trie = new Trie();
        for (String str : strs) {
            trie.insert(str);
        }
        
        // Find longest common prefix
        return trie.searchLongestCommonPrefix();
    }
    
    /**
     * Trie Node class for trie-based approach
     */
    class TrieNode {
        TrieNode[] children;
        boolean isEnd;
        int count; // Number of words that pass through this node
        
        public TrieNode() {
            children = new TrieNode[26];
            isEnd = false;
            count = 0;
        }
    }
    
    /**
     * Trie class for trie-based approach
     */
    class Trie {
        private TrieNode root;
        
        public Trie() {
            root = new TrieNode();
        }
        
        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
                node.count++;
            }
            node.isEnd = true;
        }
        
        public String searchLongestCommonPrefix() {
            TrieNode node = root;
            StringBuilder prefix = new StringBuilder();
            
            // Traverse until we find a node with count less than total words
            // or a node with multiple children
            while (node != null) {
                TrieNode nextNode = null;
                char nextChar = ' ';
                
                // Find the child that has count equal to total inserted words
                for (int i = 0; i < 26; i++) {
                    if (node.children[i] != null) {
                        if (nextNode != null) {
                            // Multiple children found, stop here
                            return prefix.toString();
                        }
                        nextNode = node.children[i];
                        nextChar = (char) ('a' + i);
                    }
                }
                
                if (nextNode == null) {
                    break;
                }
                
                prefix.append(nextChar);
                node = nextNode;
            }
            
            return prefix.toString();
        }
    }
    
    /**
     * Helper method to print test results
     */
    private static void printTestResult(String testName, String[] input, String expected, String actual) {
        boolean passed = expected.equals(actual);
        System.out.println(testName + ": " + (passed ? "PASSED" : "FAILED"));
        if (!passed) {
            System.out.println("  Input: " + java.util.Arrays.toString(input));
            System.out.println("  Expected: \"" + expected + "\", Actual: \"" + actual + "\"");
        }
    }
    
    /**
     * Performance comparison helper
     */
    private static void printPerformanceResult(String approach, long timeNs, String result) {
        System.out.println("  " + approach + ": " + timeNs + " ns, Result: \"" + result + "\"");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Longest Common Prefix Solution:");
        System.out.println("========================================");
        
        // Test case 1: Common prefix exists
        System.out.println("\nTest 1: Common prefix exists");
        String[] strs1 = {"flower", "flow", "flight"};
        String expected1 = "fl";
        
        long startTime = System.nanoTime();
        String result1a = solution.longestCommonPrefix(strs1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result1b = solution.longestCommonPrefixHorizontal(strs1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result1c = solution.longestCommonPrefixDivideConquer(strs1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result1d = solution.longestCommonPrefixTrie(strs1);
        long time1d = System.nanoTime() - startTime;
        
        printTestResult("Vertical scanning", strs1, expected1, result1a);
        printTestResult("Horizontal scanning", strs1, expected1, result1b);
        printTestResult("Divide and conquer", strs1, expected1, result1c);
        printTestResult("Trie approach", strs1, expected1, result1d);
        
        System.out.println("Performance for Test 1:");
        printPerformanceResult("Vertical", time1a, result1a);
        printPerformanceResult("Horizontal", time1b, result1b);
        printPerformanceResult("Divide & Conquer", time1c, result1c);
        printPerformanceResult("Trie", time1d, result1d);
        
        // Test case 2: No common prefix
        System.out.println("\nTest 2: No common prefix");
        String[] strs2 = {"dog", "racecar", "car"};
        String expected2 = "";
        
        String result2a = solution.longestCommonPrefix(strs2);
        String result2b = solution.longestCommonPrefixHorizontal(strs2);
        String result2c = solution.longestCommonPrefixDivideConquer(strs2);
        String result2d = solution.longestCommonPrefixTrie(strs2);
        
        printTestResult("Vertical scanning", strs2, expected2, result2a);
        printTestResult("Horizontal scanning", strs2, expected2, result2b);
        printTestResult("Divide and conquer", strs2, expected2, result2c);
        printTestResult("Trie approach", strs2, expected2, result2d);
        
        // Test case 3: Empty array
        System.out.println("\nTest 3: Empty array");
        String[] strs3 = {};
        String expected3 = "";
        
        String result3a = solution.longestCommonPrefix(strs3);
        String result3b = solution.longestCommonPrefixHorizontal(strs3);
        String result3c = solution.longestCommonPrefixDivideConquer(strs3);
        
        printTestResult("Vertical scanning", strs3, expected3, result3a);
        printTestResult("Horizontal scanning", strs3, expected3, result3b);
        printTestResult("Divide and conquer", strs3, expected3, result3c);
        
        // Test case 4: Single string
        System.out.println("\nTest 4: Single string");
        String[] strs4 = {"alone"};
        String expected4 = "alone";
        
        String result4a = solution.longestCommonPrefix(strs4);
        String result4b = solution.longestCommonPrefixHorizontal(strs4);
        String result4c = solution.longestCommonPrefixDivideConquer(strs4);
        String result4d = solution.longestCommonPrefixTrie(strs4);
        
        printTestResult("Vertical scanning", strs4, expected4, result4a);
        printTestResult("Horizontal scanning", strs4, expected4, result4b);
        printTestResult("Divide and conquer", strs4, expected4, result4c);
        printTestResult("Trie approach", strs4, expected4, result4d);
        
        // Test case 5: Empty strings in array
        System.out.println("\nTest 5: Array with empty string");
        String[] strs5 = {"flower", "flow", ""};
        String expected5 = "";
        
        String result5a = solution.longestCommonPrefix(strs5);
        String result5b = solution.longestCommonPrefixHorizontal(strs5);
        String result5c = solution.longestCommonPrefixDivideConquer(strs5);
        String result5d = solution.longestCommonPrefixTrie(strs5);
        
        printTestResult("Vertical scanning", strs5, expected5, result5a);
        printTestResult("Horizontal scanning", strs5, expected5, result5b);
        printTestResult("Divide and conquer", strs5, expected5, result5c);
        printTestResult("Trie approach", strs5, expected5, result5d);
        
        // Test case 6: All strings identical
        System.out.println("\nTest 6: All strings identical");
        String[] strs6 = {"abc", "abc", "abc"};
        String expected6 = "abc";
        
        String result6a = solution.longestCommonPrefix(strs6);
        String result6b = solution.longestCommonPrefixHorizontal(strs6);
        String result6c = solution.longestCommonPrefixDivideConquer(strs6);
        String result6d = solution.longestCommonPrefixTrie(strs6);
        
        printTestResult("Vertical scanning", strs6, expected6, result6a);
        printTestResult("Horizontal scanning", strs6, expected6, result6b);
        printTestResult("Divide and conquer", strs6, expected6, result6c);
        printTestResult("Trie approach", strs6, expected6, result6d);
        
        // Test case 7: Large input performance test
        System.out.println("\nTest 7: Large input performance comparison");
        String[] strs7 = new String[100];
        // Create array with long common prefix
        for (int i = 0; i < 100; i++) {
            strs7[i] = "abcdefghijklmnopqrstuvwxyz" + i; // Long common prefix
        }
        
        startTime = System.nanoTime();
        String result7a = solution.longestCommonPrefix(strs7);
        long largeTime1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result7b = solution.longestCommonPrefixHorizontal(strs7);
        long largeTime2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result7c = solution.longestCommonPrefixDivideConquer(strs7);
        long largeTime3 = System.nanoTime() - startTime;
        
        System.out.println("Large input performance:");
        printPerformanceResult("Vertical scanning", largeTime1, result7a);
        printPerformanceResult("Horizontal scanning", largeTime2, result7b);
        printPerformanceResult("Divide & Conquer", largeTime3, result7c);
        
        // Complexity analysis summary
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS & APPROACH COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Vertical Scanning (Recommended for interviews):");
        System.out.println("   Time: O(S) where S is total characters in all strings");
        System.out.println("   Space: O(1) - constant extra space");
        System.out.println("   Advantages:");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Good worst-case performance");
        System.out.println("     - Early termination on mismatch");
        System.out.println("   Best for: General case, interview settings");
        
        System.out.println("\n2. Horizontal Scanning:");
        System.out.println("   Time: O(S) - same as vertical but worse constant factors");
        System.out.println("   Space: O(1) - constant extra space");
        System.out.println("   Advantages:");
        System.out.println("     - Conceptually simple");
        System.out.println("   Disadvantages:");
        System.out.println("     - Poor performance when first string is long but prefix is short");
        System.out.println("     - Worst case: O(n*m) where n is array size, m is string length");
        
        System.out.println("\n3. Divide and Conquer:");
        System.out.println("   Time: O(S) - same complexity but more operations");
        System.out.println("   Space: O(m*log n) - recursion stack for n strings");
        System.out.println("   Advantages:");
        System.out.println("     - Good for parallel processing");
        System.out.println("     - Demonstrates algorithmic thinking");
        System.out.println("   Disadvantages:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Overhead of recursion");
        
        System.out.println("\n4. Trie Approach:");
        System.out.println("   Time: O(S) - building trie takes O(S)");
        System.out.println("   Space: O(S) - storing trie nodes");
        System.out.println("   Advantages:");
        System.out.println("     - Good for multiple LCP queries on same dataset");
        System.out.println("     - Demonstrates advanced data structure knowledge");
        System.out.println("   Disadvantages:");
        System.out.println("     - Overkill for single query");
        System.out.println("     - More complex implementation");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Vertical Scanning (easiest to implement and explain)");
        System.out.println("2. Mention other approaches to demonstrate breadth of knowledge");
        System.out.println("3. Discuss trade-offs between time and space complexity");
        System.out.println("4. Handle edge cases: empty array, empty strings, single string");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
