
## Solution.java

```java
/**
 * 953. Verifying an Alien Dictionary
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * In an alien language, given a sequence of words and the order of the alphabet,
 * return true if and only if the given words are sorted lexicographically in this alien language.
 * 
 * Key Insights:
 * 1. Create mapping from character to its position in alien alphabet
 * 2. Compare adjacent words pairwise according to alien order
 * 3. Handle prefix case: if words share prefix, shorter word should come first
 * 4. Early termination at first violation
 * 
 * Approach (Hash Map + Pairwise Comparison):
 * 1. Create character to index mapping from order string
 * 2. Iterate through adjacent word pairs
 * 3. For each pair, compare character by character
 * 4. If all characters match, check lengths
 * 5. Return false at first violation, true if all valid
 * 
 * Time Complexity: O(n × m) where n is words count, m is average word length
 * Space Complexity: O(1) - fixed mapping for 26 characters
 * 
 * Tags: Array, String, Hash Table, Sorting
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Hash Map with Pairwise Comparison - RECOMMENDED
     * O(n × m) time, O(1) space
     */
    public boolean isAlienSorted(String[] words, String order) {
        // Create mapping from character to its position in alien alphabet
        int[] charMap = new int[26];
        for (int i = 0; i < order.length(); i++) {
            charMap[order.charAt(i) - 'a'] = i;
        }
        
        // Compare each adjacent pair of words
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            
            if (!isOrdered(word1, word2, charMap)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isOrdered(String word1, String word2, int[] charMap) {
        int len1 = word1.length();
        int len2 = word2.length();
        int minLen = Math.min(len1, len2);
        
        // Compare character by character
        for (int j = 0; j < minLen; j++) {
            char c1 = word1.charAt(j);
            char c2 = word2.charAt(j);
            
            if (c1 != c2) {
                // If characters are different, check their order
                return charMap[c1 - 'a'] < charMap[c2 - 'a'];
            }
        }
        
        // If all compared characters are equal, check lengths
        // Shorter word should come first in lexicographical order
        return len1 <= len2;
    }
    
    /**
     * Approach 2: Custom Comparator (Alternative)
     * O(n log n × m) time, O(1) space
     * Demonstrates sorting approach
     */
    public boolean isAlienSortedComparator(String[] words, String order) {
        int[] charMap = new int[26];
        for (int i = 0; i < order.length(); i++) {
            charMap[order.charAt(i) - 'a'] = i;
        }
        
        // Create a copy to avoid modifying original array
        String[] sorted = words.clone();
        
        // Sort using custom comparator
        Arrays.sort(sorted, (a, b) -> {
            int len1 = a.length();
            int len2 = b.length();
            int minLen = Math.min(len1, len2);
            
            for (int i = 0; i < minLen; i++) {
                char c1 = a.charAt(i);
                char c2 = b.charAt(i);
                if (c1 != c2) {
                    return Integer.compare(charMap[c1 - 'a'], charMap[c2 - 'a']);
                }
            }
            
            return Integer.compare(len1, len2);
        });
        
        // Check if sorted array equals original
        return Arrays.equals(words, sorted);
    }
    
    /**
     * Approach 3: HashMap instead of array
     * O(n × m) time, O(1) space
     * Alternative implementation using HashMap
     */
    public boolean isAlienSortedHashMap(String[] words, String order) {
        Map<Character, Integer> orderMap = new HashMap<>();
        for (int i = 0; i < order.length(); i++) {
            orderMap.put(order.charAt(i), i);
        }
        
        for (int i = 0; i < words.length - 1; i++) {
            if (compareWords(words[i], words[i + 1], orderMap) > 0) {
                return false;
            }
        }
        
        return true;
    }
    
    private int compareWords(String word1, String word2, Map<Character, Integer> orderMap) {
        int len1 = word1.length();
        int len2 = word2.length();
        int minLen = Math.min(len1, len2);
        
        for (int i = 0; i < minLen; i++) {
            char c1 = word1.charAt(i);
            char c2 = word2.charAt(i);
            
            if (c1 != c2) {
                return Integer.compare(orderMap.get(c1), orderMap.get(c2));
            }
        }
        
        return Integer.compare(len1, len2);
    }
    
    /**
     * Approach 4: Early termination with detailed debugging
     * O(n × m) time, O(1) space
     * Includes detailed comparison information
     */
    public boolean isAlienSortedDebug(String[] words, String order) {
        int[] charMap = new int[26];
        for (int i = 0; i < order.length(); i++) {
            charMap[order.charAt(i) - 'a'] = i;
        }
        
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            
            System.out.printf("Comparing '%s' and '%s': ", word1, word2);
            
            int comparison = compareWordsDebug(word1, word2, charMap);
            if (comparison > 0) {
                System.out.println("NOT ORDERED");
                return false;
            } else {
                System.out.println("ORDERED");
            }
        }
        
        return true;
    }
    
    private int compareWordsDebug(String word1, String word2, int[] charMap) {
        int len1 = word1.length();
        int len2 = word2.length();
        int minLen = Math.min(len1, len2);
        
        for (int i = 0; i < minLen; i++) {
            char c1 = word1.charAt(i);
            char c2 = word2.charAt(i);
            int pos1 = charMap[c1 - 'a'];
            int pos2 = charMap[c2 - 'a'];
            
            System.out.printf("'%c'(pos=%d) vs '%c'(pos=%d) ", c1, pos1, c2, pos2);
            
            if (c1 != c2) {
                if (pos1 < pos2) {
                    System.out.print("-> word1 < word2 ");
                    return -1;
                } else {
                    System.out.print("-> word1 > word2 ");
                    return 1;
                }
            }
        }
        
        if (len1 <= len2) {
            System.out.print("-> word1 is prefix of word2 ");
            return -1;
        } else {
            System.out.print("-> word2 is prefix of word1 ");
            return 1;
        }
    }
    
    /**
     * Approach 5: One-pass with String transformation
     * O(n × m) time, O(n × m) space
     * Educational approach - transform words then compare
     */
    public boolean isAlienSortedTransform(String[] words, String order) {
        int[] charMap = new int[26];
        for (int i = 0; i < order.length(); i++) {
            charMap[order.charAt(i) - 'a'] = i;
        }
        
        // Transform each word to its "normalized" form
        String[] transformed = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (char c : words[i].toCharArray()) {
                // Map each character to a character in normal alphabetical order
                // based on its position in the alien alphabet
                sb.append((char) ('a' + charMap[c - 'a']));
            }
            transformed[i] = sb.toString();
        }
        
        // Check if transformed words are sorted in normal alphabetical order
        for (int i = 0; i < transformed.length - 1; i++) {
            if (transformed[i].compareTo(transformed[i + 1]) > 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Helper method to print alien alphabet mapping
     */
    private void printAlphabetMapping(String order) {
        System.out.println("Alien Alphabet Mapping:");
        int[] charMap = new int[26];
        for (int i = 0; i < order.length(); i++) {
            charMap[order.charAt(i) - 'a'] = i;
        }
        
        for (char c = 'a'; c <= 'z'; c++) {
            System.out.printf("%c -> %d, ", c, charMap[c - 'a']);
            if ((c - 'a' + 1) % 6 == 0) System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Helper method to visualize word comparison
     */
    private void visualizeComparison(String word1, String word2, String order) {
        int[] charMap = new int[26];
        for (int i = 0; i < order.length(); i++) {
            charMap[order.charAt(i) - 'a'] = i;
        }
        
        System.out.printf("Comparing '%s' and '%s' in alien order:%n", word1, word2);
        System.out.println("Alien order: " + order);
        
        int minLen = Math.min(word1.length(), word2.length());
        boolean ordered = true;
        
        for (int i = 0; i < minLen; i++) {
            char c1 = word1.charAt(i);
            char c2 = word2.charAt(i);
            int pos1 = charMap[c1 - 'a'];
            int pos2 = charMap[c2 - 'a'];
            
            System.out.printf("  Position %d: '%c'(pos=%d) vs '%c'(pos=%d)", 
                            i, c1, pos1, c2, pos2);
            
            if (c1 != c2) {
                if (pos1 < pos2) {
                    System.out.println(" -> word1 < word2 ✓");
                } else {
                    System.out.println(" -> word1 > word2 ✗");
                    ordered = false;
                }
                break;
            } else {
                System.out.println(" -> equal, continue");
            }
            
            if (i == minLen - 1) {
                // Reached end of shorter word
                if (word1.length() <= word2.length()) {
                    System.out.println("  word1 is prefix of word2 -> word1 < word2 ✓");
                } else {
                    System.out.println("  word2 is prefix of word1 -> word1 > word2 ✗");
                    ordered = false;
                }
            }
        }
        
        System.out.println("Result: " + (ordered ? "ORDERED" : "NOT ORDERED"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Verifying an Alien Dictionary:");
        System.out.println("======================================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        String[] words1 = {"hello", "leetcode"};
        String order1 = "hlabcdefgijkmnopqrstuvwxyz";
        boolean expected1 = true;
        
        long startTime = System.nanoTime();
        boolean result1a = solution.isAlienSorted(words1, order1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.isAlienSortedComparator(words1, order1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1c = solution.isAlienSortedHashMap(words1, order1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1d = solution.isAlienSortedTransform(words1, order1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        
        System.out.println("Array Mapping: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Comparator: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Transform: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the comparison
        solution.visualizeComparison("hello", "leetcode", order1);
        
        // Test case 2: Example 2
        System.out.println("\nTest 2: Example 2");
        String[] words2 = {"word", "world", "row"};
        String order2 = "worldabcefghijkmnpqstuvxyz";
        boolean result2 = solution.isAlienSorted(words2, order2);
        boolean expected2 = false;
        System.out.println("Example 2: " + result2 + " - " + 
                         (result2 == expected2 ? "PASSED" : "FAILED"));
        
        solution.visualizeComparison("word", "world", order2);
        
        // Test case 3: Example 3 (prefix case)
        System.out.println("\nTest 3: Prefix case");
        String[] words3 = {"apple", "app"};
        String order3 = "abcdefghijklmnopqrstuvwxyz";
        boolean result3 = solution.isAlienSorted(words3, order3);
        boolean expected3 = false;
        System.out.println("Prefix case: " + result3 + " - " + 
                         (result3 == expected3 ? "PASSED" : "FAILED"));
        
        solution.visualizeComparison("apple", "app", order3);
        
        // Test case 4: Single word
        System.out.println("\nTest 4: Single word");
        String[] words4 = {"hello"};
        String order4 = "abcdefghijklmnopqrstuvwxyz";
        boolean result4 = solution.isAlienSorted(words4, order4);
        System.out.println("Single word: " + result4 + " - " + 
                         (result4 == true ? "PASSED" : "FAILED"));
        
        // Test case 5: Empty array (edge case)
        System.out.println("\nTest 5: Empty array");
        String[] words5 = {};
        String order5 = "abcdefghijklmnopqrstuvwxyz";
        boolean result5 = solution.isAlienSorted(words5, order5);
        System.out.println("Empty array: " + result5 + " - " + 
                         (result5 == true ? "PASSED" : "FAILED"));
        
        // Test case 6: Same words
        System.out.println("\nTest 6: Same words");
        String[] words6 = {"test", "test"};
        String order6 = "abcdefghijklmnopqrstuvwxyz";
        boolean result6 = solution.isAlienSorted(words6, order6);
        System.out.println("Same words: " + result6 + " - " + 
                         (result6 == true ? "PASSED" : "FAILED"));
        
        // Test case 7: Different first character
        System.out.println("\nTest 7: Different first character");
        String[] words7 = {"zoo", "apple"};
        String order7 = "zabcdefghijklmnopqrstuvwxy"; // z comes before a
        boolean result7 = solution.isAlienSorted(words7, order7);
        System.out.println("Different first char: " + result7 + " - " + 
                         (result7 == true ? "PASSED" : "FAILED"));
        
        // Test case 8: Complex alien order
        System.out.println("\nTest 8: Complex alien order");
        String[] words8 = {"kuvp", "q"};
        String order8 = "ngxlkthsjuoqcpavbfdermiywz";
        boolean result8 = solution.isAlienSorted(words8, order8);
        System.out.println("Complex order: " + result8 + " - " + 
                         (result8 == true ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  Array Mapping: " + time1a + " ns");
        System.out.println("  Comparator: " + time1b + " ns");
        System.out.println("  HashMap: " + time1c + " ns");
        System.out.println("  Transform: " + time1d + " ns");
        
        // Performance test with larger input
        System.out.println("\nPerformance Test with 100 words:");
        String[] largeWords = generateLargeWordList(100);
        String largeOrder = "zyxwvutsrqponmlkjihgfedcba"; // Reverse order
        
        startTime = System.nanoTime();
        boolean largeResult = solution.isAlienSorted(largeWords, largeOrder);
        long largeTime = System.nanoTime() - startTime;
        System.out.println("100 words result: " + largeResult + ", time: " + largeTime + " ns");
        
        // Debug version with detailed output
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DEBUG VERSION OUTPUT:");
        System.out.println("=".repeat(70));
        solution.isAlienSortedDebug(words2, order2);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        solution.explainAlgorithm();
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Generate a large word list for performance testing
     */
    private static String[] generateLargeWordList(int size) {
        String[] words = new String[size];
        Random random = new Random(42);
        String characters = "abcdefghijklmnopqrstuvwxyz";
        
        for (int i = 0; i < size; i++) {
            int wordLength = random.nextInt(10) + 1; // 1-10 characters
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < wordLength; j++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
            words[i] = sb.toString();
        }
        
        // Sort the words to ensure they are ordered for testing
        Arrays.sort(words);
        return words;
    }
    
    /**
     * Detailed explanation of the algorithm
     */
    private void explainAlgorithm() {
        System.out.println("\nKey Insights:");
        System.out.println("1. Lexicographical order depends on:");
        System.out.println("   - First differing character between words");
        System.out.println("   - If all characters match, shorter word comes first");
        System.out.println("2. We need to map alien characters to positions for comparison");
        System.out.println("3. Array mapping is most efficient for fixed 26 characters");
        
        System.out.println("\nStep-by-step Algorithm:");
        System.out.println("1. Create character mapping from alien order:");
        System.out.println("   - For each character in order, store its position");
        System.out.println("   - Use array: charMap[char - 'a'] = position");
        
        System.out.println("2. Compare adjacent words pairwise:");
        System.out.println("   - For words[i] and words[i+1]:");
        System.out.println("   - Find first differing character");
        System.out.println("   - Compare positions in alien alphabet");
        System.out.println("   - If word1 char < word2 char → ordered");
        System.out.println("   - If word1 char > word2 char → not ordered");
        
        System.out.println("3. Handle prefix case:");
        System.out.println("   - If all characters match up to min length");
        System.out.println("   - Check if word1.length <= word2.length");
        System.out.println("   - If yes → ordered, if no → not ordered");
        
        System.out.println("\nExample Walkthrough:");
        System.out.println("Words: [\"hello\", \"leetcode\"], Order: \"hlabcdefgijkmnopqrstuvwxyz\"");
        System.out.println("1. Compare 'h' vs 'l':");
        System.out.println("   - 'h' position: 0, 'l' position: 1");
        System.out.println("   - 0 < 1 → ordered ✓");
        
        System.out.println("\nWords: [\"apple\", \"app\"], Order: normal alphabet");
        System.out.println("1. Compare characters:");
        System.out.println("   - 'a'='a', 'p'='p', 'p'='p'");
        System.out.println("2. Check lengths:");
        System.out.println("   - apple.length=5, app.length=3");
        System.out.println("   - 5 > 3 → not ordered ✗");
        
        System.out.println("\nTime Complexity Analysis:");
        System.out.println("- Building character map: O(26) = O(1)");
        System.out.println("- Comparing n-1 word pairs: O(n × m)");
        System.out.println("- Total: O(n × m) where n = words count, m = average word length");
        
        System.out.println("\nSpace Complexity: O(1) - fixed size array for 26 characters");
    }
}

/**
 * Additional utility class for string operations
 */
class StringUtils {
    /**
     * Check if a string array is sorted according to normal alphabetical order
     */
    public static boolean isNormallySorted(String[] words) {
        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].compareTo(words[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Convert alien words to normal words using mapping
     */
    public static String[] convertToNormal(String[] words, String order) {
        int[] charMap = new int[26];
        for (int i = 0; i < order.length(); i++) {
            charMap[order.charAt(i) - 'a'] = i;
        }
        
        String[] normal = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (char c : words[i].toCharArray()) {
                sb.append((char) ('a' + charMap[c - 'a']));
            }
            normal[i] = sb.toString();
        }
        return normal;
    }
}
