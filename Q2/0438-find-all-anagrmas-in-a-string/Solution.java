
## Solution.java

```java
import java.util.*;

/**
 * 438. Find All Anagrams in a String
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find all start indices where a substring of s is an anagram of p.
 * 
 * Key Insights:
 * 1. Use sliding window of fixed size (p.length()).
 * 2. Maintain frequency arrays for the window and for p.
 * 3. Use a counter to track how many characters have matching counts.
 */
class Solution {
    
    /**
     * Approach 1: Sliding Window with Frequency Arrays (Recommended)
     * Time: O(n), Space: O(1)
     * 
     * Steps:
     * 1. If p.length() > s.length() → return empty list.
     * 2. Build frequency array for p and for the first window of s.
     * 3. Count how many characters have matching frequencies (diff).
     * 4. Slide the window: remove left char, add right char, update diff.
     * 5. If diff == 26, add left index to result.
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int ns = s.length(), np = p.length();
        if (np > ns) return result;
        
        int[] sCount = new int[26];
        int[] pCount = new int[26];
        
        // Count frequencies for p and first window of s
        for (int i = 0; i < np; i++) {
            pCount[p.charAt(i) - 'a']++;
            sCount[s.charAt(i) - 'a']++;
        }
        
        int diff = 0;
        for (int i = 0; i < 26; i++) {
            if (sCount[i] == pCount[i]) diff++;
        }
        
        // Slide the window
        for (int i = 0; i <= ns - np; i++) {
            if (diff == 26) result.add(i);
            
            if (i + np < ns) {
                // Remove left character
                int left = s.charAt(i) - 'a';
                int right = s.charAt(i + np) - 'a';
                
                // Update diff for left character
                if (sCount[left] == pCount[left]) diff--;
                sCount[left]--;
                if (sCount[left] == pCount[left]) diff++;
                
                // Update diff for right character
                if (sCount[right] == pCount[right]) diff--;
                sCount[right]++;
                if (sCount[right] == pCount[right]) diff++;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: Sliding Window with Direct Comparison (Simpler)
     * Time: O(26 * n) ≈ O(n), Space: O(1)
     */
    public List<Integer> findAnagramsCompare(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int ns = s.length(), np = p.length();
        if (np > ns) return result;
        
        int[] pCount = new int[26];
        for (char c : p.toCharArray()) pCount[c - 'a']++;
        
        int[] window = new int[26];
        for (int i = 0; i < np; i++) {
            window[s.charAt(i) - 'a']++;
        }
        
        if (Arrays.equals(window, pCount)) result.add(0);
        
        for (int i = np; i < ns; i++) {
            window[s.charAt(i - np) - 'a']--;
            window[s.charAt(i) - 'a']++;
            if (Arrays.equals(window, pCount)) result.add(i - np + 1);
        }
        
        return result;
    }
    
    /**
     * Approach 3: Using HashMap (General for any character set)
     * Time: O(n), Space: O(k) where k = unique chars
     */
    public List<Integer> findAnagramsHashMap(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int ns = s.length(), np = p.length();
        if (np > ns) return result;
        
        Map<Character, Integer> pMap = new HashMap<>();
        for (char c : p.toCharArray()) pMap.put(c, pMap.getOrDefault(c, 0) + 1);
        
        Map<Character, Integer> window = new HashMap<>();
        for (int i = 0; i < np; i++) {
            char c = s.charAt(i);
            window.put(c, window.getOrDefault(c, 0) + 1);
        }
        
        if (window.equals(pMap)) result.add(0);
        
        for (int i = np; i < ns; i++) {
            char left = s.charAt(i - np);
            char right = s.charAt(i);
            
            window.put(left, window.get(left) - 1);
            if (window.get(left) == 0) window.remove(left);
            
            window.put(right, window.getOrDefault(right, 0) + 1);
            
            if (window.equals(pMap)) result.add(i - np + 1);
        }
        
        return result;
    }
    
    /**
     * Helper: Visualize sliding window
     */
    public void visualizeAnagrams(String s, String p) {
        System.out.println("\nFind All Anagrams Visualization:");
        System.out.println("=".repeat(60));
        System.out.printf("s = \"%s\", p = \"%s\"%n", s, p);
        
        int ns = s.length(), np = p.length();
        if (np > ns) {
            System.out.println("p longer than s → no anagrams");
            return;
        }
        
        int[] pCount = new int[26];
        for (char c : p.toCharArray()) pCount[c - 'a']++;
        
        int[] window = new int[26];
        for (int i = 0; i < np; i++) window[s.charAt(i) - 'a']++;
        
        System.out.printf("Window [0,%d): \"%s\"\n", np, s.substring(0, np));
        
        for (int i = 0; i <= ns - np; i++) {
            boolean match = Arrays.equals(window, pCount);
            System.out.printf("Index %d: substring \"%s\" → anagram? %b%n", 
                i, s.substring(i, i + np), match);
            if (match && i + np < ns) {
                System.out.println("  → Adding to result: " + i);
            }
            
            if (i + np < ns) {
                window[s.charAt(i) - 'a']--;
                window[s.charAt(i + np) - 'a']++;
                System.out.printf("  Slide to [%d,%d): \"%s\"%n", 
                    i+1, i+1+np, s.substring(i+1, i+1+np));
            }
        }
    }
    
    /**
     * Helper: Run test cases
     */
    public void runTestCases() {
        System.out.println("\nRunning Test Cases:");
        System.out.println("=".repeat(50));
        
        String[][] tests = {
            {"cbaebabacd", "abc"},
            {"abab", "ab"},
            {"aa", "aa"},
            {"aaaa", "a"},
            {"abc", "abcd"}
        };
        String[][] expected = {
            {"0", "6"},
            {"0", "1", "2"},
            {"0"},
            {"0","1","2"},
            {}
        };
        
        for (int i = 0; i < tests.length; i++) {
            String s = tests[i][0];
            String p = tests[i][1];
            List<Integer> result = findAnagrams(s, p);
            System.out.printf("Test %d: s=\"%s\", p=\"%s\" → %s%n", 
                i+1, s, p, result);
        }
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("438. Find All Anagrams in a String");
        System.out.println("==================================");
        
        solution.visualizeAnagrams("cbaebabacd", "abc");
        solution.runTestCases();
    }
}
