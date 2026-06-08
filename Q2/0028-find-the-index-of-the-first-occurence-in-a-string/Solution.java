
## Solution.java

```java
/**
 * 28. Find the Index of the First Occurrence in a String
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Implement strStr() – return first index of needle in haystack, or -1.
 * 
 * Key Insights:
 * 1. Sliding window compares each possible start position.
 * 2. KMP algorithm for linear time.
 * 3. Edge cases: needle longer than haystack, empty needle.
 */
class Solution {
    
    /**
     * Approach 1: Sliding Window (Brute Force) – Simple and enough for constraints.
     * Time: O((n - m) * m), Space: O(1)
     */
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        
        if (m == 0) return 0; // convention: empty needle matches at index 0
        if (m > n) return -1;
        
        for (int i = 0; i <= n - m; i++) {
            if (haystack.substring(i, i + m).equals(needle)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Approach 2: Sliding Window with char-by-char comparison (avoids substring creation)
     * Time: O(n*m), Space: O(1)
     */
    public int strStrManual(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m == 0) return 0;
        if (m > n) return -1;
        
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            if (j == m) return i;
        }
        return -1;
    }
    
    /**
     * Approach 3: KMP Algorithm – O(n + m) time, O(m) space.
     */
    public int strStrKMP(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m == 0) return 0;
        if (m > n) return -1;
        
        // Build LPS (longest prefix suffix) array for needle
        int[] lps = computeLPS(needle);
        
        int i = 0; // index for haystack
        int j = 0; // index for needle
        
        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    return i - m; // match found
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return -1;
    }
    
    private int[] computeLPS(String needle) {
        int m = needle.length();
        int[] lps = new int[m];
        int len = 0; // length of previous longest prefix suffix
        int i = 1;
        while (i < m) {
            if (needle.charAt(i) == needle.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
    
    /**
     * Helper: Visualize the sliding window process
     */
    public void visualizeSearch(String haystack, String needle) {
        System.out.println("\nSearch Visualization:");
        System.out.println("=".repeat(60));
        System.out.println("Haystack: \"" + haystack + "\"");
        System.out.println("Needle:   \"" + needle + "\"");
        
        int n = haystack.length();
        int m = needle.length();
        if (m > n) {
            System.out.println("Needle longer than haystack → -1");
            return;
        }
        
        for (int i = 0; i <= n - m; i++) {
            String sub = haystack.substring(i, i + m);
            System.out.printf("Position %d: \"%s\" → %s%n", i, sub, sub.equals(needle) ? "MATCH" : "");
            if (sub.equals(needle)) {
                System.out.println("First occurrence at index " + i);
                return;
            }
        }
        System.out.println("Not found → -1");
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[][] tests = {
            {"sadbutsad", "sad", "0"},
            {"leetcode", "leeto", "-1"},
            {"hello", "ll", "2"},
            {"aaaaa", "bba", "-1"},
            {"", "", "0"},
            {"a", "a", "0"},
            {"abc", "", "0"}
        };
        
        int passed = 0;
        for (int i = 0; i < tests.length; i++) {
            String hay = tests[i][0];
            String needle = tests[i][1];
            int expected = Integer.parseInt(tests[i][2]);
            
            int res1 = strStr(hay, needle);
            int res2 = strStrManual(hay, needle);
            int res3 = strStrKMP(hay, needle);
            
            boolean ok = (res1 == expected && res2 == expected && res3 == expected);
            System.out.printf("Test %d: \"%s\" , \"%s\" → %d (expected %d) %s%n",
                i+1, hay, needle, res1, expected, ok ? "✓" : "✗");
            if (ok) passed++;
        }
        System.out.println("\nPassed: " + passed + "/" + tests.length);
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println("28. Find the Index of the First Occurrence in a String");
        System.out.println("====================================================");
        
        sol.visualizeSearch("sadbutsad", "sad");
        sol.runTestCases();
    }
}
