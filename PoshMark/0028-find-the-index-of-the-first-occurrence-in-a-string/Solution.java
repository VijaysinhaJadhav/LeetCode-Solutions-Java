
# Solution.java

```java
import java.util.*;

/**
 * 28. Find the Index of the First Occurrence in a String
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Find first occurrence of needle in haystack, return index or -1.
 * 
 * Key Insights:
 * 1. Multiple approaches: brute force, built-in, KMP, Rabin-Karp
 * 2. KMP algorithm provides optimal O(n+m) time
 * 3. Built-in methods are practical but not interview-appropriate
 * 4. Understand prefix function for KMP
 * 
 * Approach 1: KMP Algorithm (RECOMMENDED for interviews)
 * O(n+m) time, O(m) space
 */

class Solution {
    
    /**
     * Approach 1: KMP Algorithm (RECOMMENDED for interviews)
     * Time: O(n+m), Space: O(m)
     * Knuth-Morris-Pratt string matching algorithm
     */
    public int strStr(String haystack, String needle) {
        if (needle.isEmpty()) return 0;
        if (haystack.length() < needle.length()) return -1;
        
        int n = haystack.length();
        int m = needle.length();
        
        // Compute Longest Prefix Suffix (LPS) array for needle
        int[] lps = computeLPS(needle);
        
        int i = 0; // index for haystack
        int j = 0; // index for needle
        
        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                
                if (j == m) {
                    // Found full match
                    return i - j;
                }
            } else {
                if (j != 0) {
                    // Use LPS to skip unnecessary comparisons
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Compute Longest Prefix Suffix array for KMP
     * lps[i] = longest proper prefix of needle[0..i] 
     *          which is also a suffix of needle[0..i]
     */
    private int[] computeLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        
        int len = 0; // length of previous longest prefix suffix
        int i = 1;
        
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    // Fall back to previous prefix
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
     * Approach 2: Built-in method (PRACTICAL for real code)
     * Time: O(n+m) typically, Space: O(1)
     * One-liner but not interview-appropriate
     */
    public int strStrBuiltIn(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
    
    /**
     * Approach 3: Brute Force (Naive)
     * Time: O(n*m), Space: O(1)
     * Check all possible starting positions
     */
    public int strStrBruteForce(String haystack, String needle) {
        if (needle.isEmpty()) return 0;
        if (haystack.length() < needle.length()) return -1;
        
        int n = haystack.length();
        int m = needle.length();
        
        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 4: Rabin-Karp Algorithm
     * Time: O(n+m) average, O(n*m) worst, Space: O(1)
     * Rolling hash technique
     */
    public int strStrRabinKarp(String haystack, String needle) {
        if (needle.isEmpty()) return 0;
        if (haystack.length() < needle.length()) return -1;
        
        int n = haystack.length();
        int m = needle.length();
        
        // Prime number for hash calculation
        int prime = 101;
        // Base for polynomial rolling hash
        int base = 256;
        
        // Calculate hash of needle and first window of haystack
        int needleHash = 0;
        int windowHash = 0;
        int h = 1; // pow(base, m-1) % prime
        
        // Calculate h = base^(m-1) % prime
        for (int i = 0; i < m - 1; i++) {
            h = (h * base) % prime;
        }
        
        // Calculate initial hashes
        for (int i = 0; i < m; i++) {
            needleHash = (base * needleHash + needle.charAt(i)) % prime;
            windowHash = (base * windowHash + haystack.charAt(i)) % prime;
        }
        
        // Slide the window
        for (int i = 0; i <= n - m; i++) {
            // Check hash match first, then verify characters
            if (needleHash == windowHash) {
                // Verify character by character
                int j;
                for (j = 0; j < m; j++) {
                    if (haystack.charAt(i + j) != needle.charAt(j)) {
                        break;
                    }
                }
                if (j == m) {
                    return i;
                }
            }
            
            // Calculate hash for next window
            if (i < n - m) {
                windowHash = (base * (windowHash - haystack.charAt(i) * h) 
                            + haystack.charAt(i + m)) % prime;
                
                // Make sure hash is positive
                if (windowHash < 0) {
                    windowHash += prime;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 5: Two Pointers with Optimization
     * Time: O(n*m) worst, O(n) best, Space: O(1)
     * Optimized brute force with early exit
     */
    public int strStrTwoPointers(String haystack, String needle) {
        if (needle.isEmpty()) return 0;
        if (haystack.length() < needle.length()) return -1;
        
        int n = haystack.length();
        int m = needle.length();
        
        for (int i = 0; i <= n - m; i++) {
            // Quick check first character
            if (haystack.charAt(i) != needle.charAt(0)) {
                continue;
            }
            
            // Quick check last character
            if (haystack.charAt(i + m - 1) != needle.charAt(m - 1)) {
                continue;
            }
            
            // Full comparison
            int j;
            for (j = 0; j < m; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 6: Boyer-Moore-Horspool (Simplified)
     * Time: O(n*m) worst, O(n/m) best, Space: O(256)
     * Bad-character heuristic
     */
    public int strStrBMH(String haystack, String needle) {
        if (needle.isEmpty()) return 0;
        if (haystack.length() < needle.length()) return -1;
        
        int n = haystack.length();
        int m = needle.length();
        
        // Preprocess bad-character shift table
        int[] badChar = new int[256];
        Arrays.fill(badChar, m);
        
        for (int i = 0; i < m - 1; i++) {
            badChar[needle.charAt(i)] = m - 1 - i;
        }
        
        // Search
        int i = 0;
        while (i <= n - m) {
            int j = m - 1;
            
            // Compare from right to left
            while (j >= 0 && haystack.charAt(i + j) == needle.charAt(j)) {
                j--;
            }
            
            if (j < 0) {
                // Match found
                return i;
            } else {
                // Shift by bad character rule
                i += Math.max(1, badChar[haystack.charAt(i + m - 1)] - (m - 1 - j));
            }
        }
        
        return -1;
    }
    
    /**
     * Helper: Visualize KMP algorithm
     */
    public void visualizeKMP(String haystack, String needle) {
        System.out.println("\nKMP Algorithm Visualization:");
        System.out.println("haystack = \"" + haystack + "\"");
        System.out.println("needle = \"" + needle + "\"");
        
        if (needle.isEmpty()) {
            System.out.println("Empty needle, return 0");
            return;
        }
        
        if (haystack.length() < needle.length()) {
            System.out.println("Needle longer than haystack, return -1");
            return;
        }
        
        // Compute LPS array
        int[] lps = computeLPS(needle);
        System.out.println("\nLPS array for needle \"" + needle + "\":");
        System.out.print("Index:  ");
        for (int i = 0; i < needle.length(); i++) {
            System.out.printf("%3d ", i);
        }
        System.out.print("\nChar:   ");
        for (int i = 0; i < needle.length(); i++) {
            System.out.printf("  %c ", needle.charAt(i));
        }
        System.out.print("\nLPS:    ");
        for (int val : lps) {
            System.out.printf("%3d ", val);
        }
        System.out.println();
        
        // Explain LPS
        System.out.println("\nLPS explanation:");
        for (int i = 0; i < needle.length(); i++) {
            String prefix = needle.substring(0, i + 1);
            System.out.printf("LPS[%d] = %d: longest proper prefix-suffix of \"%s\"\n",
                i, lps[i], prefix);
        }
        
        // Simulate KMP search
        System.out.println("\nKMP Search Process:");
        System.out.println("i = haystack index, j = needle index");
        
        int i = 0, j = 0;
        int n = haystack.length();
        int m = needle.length();
        int step = 1;
        
        System.out.println("\n" + "=".repeat(60));
        System.out.printf("%-6s %-15s %-15s %-20s\n", 
            "Step", "i,j", "Characters", "Action");
        System.out.println("=".repeat(60));
        
        while (i < n) {
            System.out.printf("%-6d i=%-3d j=%-3d ", step++, i, j);
            
            if (i < n && j < m) {
                System.out.printf("h[%d]='%c' vs n[%d]='%c' ", 
                    i, haystack.charAt(i), j, needle.charAt(j));
            }
            
            if (j < m && i < n && haystack.charAt(i) == needle.charAt(j)) {
                System.out.printf("Match → i++, j++");
                i++;
                j++;
                
                if (j == m) {
                    System.out.printf(" ✓ FOUND at index %d", i - j);
                    break;
                }
            } else {
                if (j != 0) {
                    System.out.printf("Mismatch → j = LPS[%d] = %d", j-1, lps[j-1]);
                    j = lps[j - 1];
                } else {
                    System.out.printf("Mismatch → i++");
                    i++;
                }
            }
            System.out.println();
        }
        
        if (j == m) {
            System.out.println("\n\nFound at index: " + (i - j));
        } else {
            System.out.println("\n\nNot found, return -1");
        }
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(String haystack, String needle) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING STRING SEARCH APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nSearching for \"" + needle + "\" in \"" + haystack + "\"");
        System.out.println("haystack length: " + haystack.length());
        System.out.println("needle length: " + needle.length());
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5, result6;
        
        // Warm up
        solution.strStr(haystack, needle);
        
        // Approach 1: KMP
        startTime = System.nanoTime();
        result1 = solution.strStr(haystack, needle);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Built-in
        startTime = System.nanoTime();
        result2 = solution.strStrBuiltIn(haystack, needle);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Brute Force
        startTime = System.nanoTime();
        result3 = solution.strStrBruteForce(haystack, needle);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Rabin-Karp
        startTime = System.nanoTime();
        result4 = solution.strStrRabinKarp(haystack, needle);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: Two Pointers
        startTime = System.nanoTime();
        result5 = solution.strStrTwoPointers(haystack, needle);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Approach 6: Boyer-Moore-Horspool
        startTime = System.nanoTime();
        result6 = solution.strStrBMH(haystack, needle);
        endTime = System.nanoTime();
        long time6 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("KMP:               " + result1);
        System.out.println("Built-in:          " + result2);
        System.out.println("Brute Force:       " + result3);
        System.out.println("Rabin-Karp:        " + result4);
        System.out.println("Two Pointers:      " + result5);
        System.out.println("Boyer-Moore-Hors:  " + result6);
        
        boolean allEqual = (result1 == result2) && (result2 == result3) && 
                          (result3 == result4) && (result4 == result5) && 
                          (result5 == result6);
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("KMP:               %-10d (O(n+m))%n", time1);
        System.out.printf("Built-in:          %-10d (O(n+m) optimized)%n", time2);
        System.out.printf("Brute Force:       %-10d (O(n*m))%n", time3);
        System.out.printf("Rabin-Karp:        %-10d (O(n+m) avg)%n", time4);
        System.out.printf("Two Pointers:      %-10d (O(n*m) worst)%n", time5);
        System.out.printf("Boyer-Moore-Hors:  %-10d (O(n/m) best)%n", time6);
        
        // Visualize KMP for small examples
        if (haystack.length() <= 20 && needle.length() <= 10) {
            System.out.println("\n" + "-".repeat(80));
            solution.visualizeKMP(haystack, needle);
        }
    }
    
    /**
     * Helper: Explain KMP algorithm in detail
     */
    public void explainKMPAlgorithm() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("KMP ALGORITHM DETAILED EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nProblem with Naive Approach:");
        System.out.println("In naive string matching, when mismatch occurs,");
        System.out.println("we go back to next position and start over.");
        System.out.println("Example: haystack = \"AAAAABAAABA\", needle = \"AAAA\"");
        System.out.println("After matching \"AAAA\", mismatch at position 4,");
        System.out.println("but we know next 3 chars are \"AAA\" from previous match.");
        
        System.out.println("\nKMP Key Insight:");
        System.out.println("When mismatch occurs, some characters in haystack");
        System.out.println("are already known from previous match.");
        System.out.println("We can skip ahead using precomputed LPS array.");
        
        System.out.println("\nLPS Array (Longest Prefix Suffix):");
        System.out.println("For each position i in needle, LPS[i] = length of");
        System.out.println("longest proper prefix of needle[0..i] that is also");
        System.out.println("a suffix of needle[0..i].");
        
        System.out.println("\nExample: needle = \"ABABCABAB\"");
        System.out.println("Index: 0 1 2 3 4 5 6 7 8");
        System.out.println("Char:  A B A B C A B A B");
        System.out.println("LPS:   0 0 1 2 0 1 2 3 4");
        System.out.println("\nExplanation:");
        System.out.println("LPS[2]=1: \"ABA\" has prefix \"A\" = suffix \"A\"");
        System.out.println("LPS[3]=2: \"ABAB\" has prefix \"AB\" = suffix \"AB\"");
        System.out.println("LPS[7]=3: \"ABABCABAB\" prefix \"ABA\" = suffix \"ABA\"");
        
        System.out.println("\nKMP Algorithm Steps:");
        System.out.println("1. Preprocess needle to compute LPS array");
        System.out.println("2. Use two pointers: i for haystack, j for needle");
        System.out.println("3. While i < haystack.length:");
        System.out.println("   a. If chars match: i++, j++");
        System.out.println("   b. If j == needle.length: found match at i-j");
        System.out.println("   c. If mismatch and j != 0: j = LPS[j-1]");
        System.out.println("   d. If mismatch and j == 0: i++");
        
        System.out.println("\nWhy LPS works:");
        System.out.println("LPS tells us how much of current match can be reused.");
        System.out.println("When mismatch at needle[j], we know needle[0..j-1] matched.");
        System.out.println("LPS[j-1] tells us longest prefix of matched part");
        System.out.println("that is also a suffix, so we can continue from there.");
        
        System.out.println("\nTime Complexity:");
        System.out.println("- LPS computation: O(m) where m = needle length");
        System.out.println("- Search: O(n) where n = haystack length");
        System.out.println("- Total: O(n+m)");
        
        System.out.println("\nSpace Complexity: O(m) for LPS array");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES TESTING:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. Empty needle:");
        String h1 = "hello";
        String n1 = "";
        int r1 = solution.strStr(h1, n1);
        System.out.println("   haystack = \"" + h1 + "\", needle = \"" + n1 + "\"");
        System.out.println("   Result: " + r1 + " (should be 0)");
        
        System.out.println("\n2. Needle longer than haystack:");
        String h2 = "abc";
        String n2 = "abcd";
        int r2 = solution.strStr(h2, n2);
        System.out.println("   haystack = \"" + h2 + "\", needle = \"" + n2 + "\"");
        System.out.println("   Result: " + r2 + " (should be -1)");
        
        System.out.println("\n3. Exact match:");
        String h3 = "hello";
        String n3 = "hello";
        int r3 = solution.strStr(h3, n3);
        System.out.println("   haystack = \"" + h3 + "\", needle = \"" + n3 + "\"");
        System.out.println("   Result: " + r3 + " (should be 0)");
        
        System.out.println("\n4. Needle at beginning:");
        String h4 = "hello world";
        String n4 = "hello";
        int r4 = solution.strStr(h4, n4);
        System.out.println("   haystack = \"" + h4 + "\", needle = \"" + n4 + "\"");
        System.out.println("   Result: " + r4 + " (should be 0)");
        
        System.out.println("\n5. Needle at end:");
        String h5 = "hello world";
        String n5 = "world";
        int r5 = solution.strStr(h5, n5);
        System.out.println("   haystack = \"" + h5 + "\", needle = \"" + n5 + "\"");
        System.out.println("   Result: " + r5 + " (should be 6)");
        
        System.out.println("\n6. Needle in middle:");
        String h6 = "hello beautiful world";
        String n6 = "beautiful";
        int r6 = solution.strStr(h6, n6);
        System.out.println("   haystack = \"" + h6 + "\", needle = \"" + n6 + "\"");
        System.out.println("   Result: " + r6 + " (should be 6)");
        
        System.out.println("\n7. Multiple occurrences:");
        String h7 = "abababab";
        String n7 = "ab";
        int r7 = solution.strStr(h7, n7);
        System.out.println("   haystack = \"" + h7 + "\", needle = \"" + n7 + "\"");
        System.out.println("   Result: " + r7 + " (should be 0, first occurrence)");
        
        System.out.println("\n8. No match:");
        String h8 = "hello";
        String n8 = "xyz";
        int r8 = solution.strStr(h8, n8);
        System.out.println("   haystack = \"" + h8 + "\", needle = \"" + n8 + "\"");
        System.out.println("   Result: " + r8 + " (should be -1)");
        
        System.out.println("\n9. Single character haystack:");
        String h9 = "a";
        String n9 = "a";
        int r9 = solution.strStr(h9, n9);
        System.out.println("   haystack = \"" + h9 + "\", needle = \"" + n9 + "\"");
        System.out.println("   Result: " + r9 + " (should be 0)");
        
        System.out.println("\n10. Single character no match:");
        String h10 = "a";
        String n10 = "b";
        int r10 = solution.strStr(h10, n10);
        System.out.println("   haystack = \"" + h10 + "\", needle = \"" + n10 + "\"");
        System.out.println("   Result: " + r10 + " (should be -1)");
    }
    
    /**
     * Helper: Analyze complexity and trade-offs
     */
    public void analyzeComplexity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS AND TRADE-OFFS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Time Complexity Comparison:");
        System.out.println("   Algorithm        | Best  | Average | Worst  | Space");
        System.out.println("   -----------------|-------|---------|--------|-------");
        System.out.println("   Brute Force      | O(n)  | O(n*m)  | O(n*m) | O(1)");
        System.out.println("   KMP              | O(n+m)| O(n+m)  | O(n+m) | O(m)");
        System.out.println("   Rabin-Karp       | O(n+m)| O(n+m)  | O(n*m) | O(1)");
        System.out.println("   Boyer-Moore      | O(n/m)| O(n/m)  | O(n*m) | O(m)");
        System.out.println("   Built-in (Java)  | O(n)  | O(n+m)  | O(n*m) | O(1)");
        
        System.out.println("\n2. When to use each algorithm:");
        System.out.println("   - Brute Force: Simple, small strings, interview warm-up");
        System.out.println("   - KMP: Interview standard, guarantees O(n+m)");
        System.out.println("   - Rabin-Karp: Multiple pattern search, streaming");
        System.out.println("   - Boyer-Moore: Long patterns, English text");
        System.out.println("   - Built-in: Production code (highly optimized)");
        
        System.out.println("\n3. KMP Advantages:");
        System.out.println("   - Guaranteed O(n+m) time");
        System.out.println("   - No worst-case backtracking");
        System.out.println("   - Good for repetitive patterns");
        System.out.println("   - Standard interview algorithm");
        
        System.out.println("\n4. KMP Disadvantages:");
        System.out.println("   - More complex to implement");
        System.out.println("   - O(m) extra space");
        System.out.println("   - Overhead for small strings");
        
        System.out.println("\n5. Real-world Considerations:");
        System.out.println("   - Built-in methods are highly optimized");
        System.out.println("   - Modern CPUs have hardware string instructions");
        System.out.println("   - For most cases, built-in is sufficient");
        System.out.println("   - Specialized algorithms for specific use cases");
    }
    
    /**
     * Helper: Show related problems
     */
    public void showRelatedProblems() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 459. Repeated Substring Pattern:");
        System.out.println("   - Check if string can be constructed by repeating substring");
        System.out.println("   - Uses KMP/LPS array concept");
        
        System.out.println("\n2. 214. Shortest Palindrome:");
        System.out.println("   - Add characters in front to make palindrome");
        System.out.println("   - Uses KMP on string + reverse");
        
        System.out.println("\n3. 686. Repeated String Match:");
        System.out.println("   - Minimum times to repeat A so B is substring");
        System.out.println("   - String matching with repetition");
        
        System.out.println("\n4. 1392. Longest Happy Prefix:");
        System.out.println("   - Longest prefix which is also suffix");
        System.out.println("   - Direct LPS array application");
        
        System.out.println("\n5. 796. Rotate String:");
        System.out.println("   - Check if B is rotation of A");
        System.out.println("   - Can be solved with string matching");
        
        System.out.println("\n6. 3. Longest Substring Without Repeating Characters:");
        System.out.println("   - Different but related string problem");
        
        System.out.println("\n7. 5. Longest Palindromic Substring:");
        System.out.println("   - Another classic string problem");
        
        System.out.println("\nCommon Pattern:");
        System.out.println("All involve string manipulation and pattern matching");
        System.out.println("KMP/LPS concept appears in many string problems");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Text Editors and IDEs:");
        System.out.println("   - Find/replace functionality");
        System.out.println("   - Syntax highlighting");
        System.out.println("   - Code search in large codebases");
        
        System.out.println("\n2. Search Engines:");
        System.out.println("   - Web page content indexing");
        System.out.println("   - Query matching in documents");
        System.out.println("   - Autocomplete suggestions");
        
        System.out.println("\n3. Database Systems:");
        System.out.println("   - LIKE operator in SQL");
        System.out.println("   - Full-text search");
        System.out.println("   - Pattern matching in queries");
        
        System.out.println("\n4. Bioinformatics:");
        System.out.println("   - DNA sequence matching");
        System.out.println("   - Protein pattern recognition");
        System.out.println("   - Genome alignment");
        
        System.out.println("\n5. Network Security:");
        System.out.println("   - Intrusion detection patterns");
        System.out.println("   - Virus signature matching");
        System.out.println("   - Packet inspection");
        
        System.out.println("\n6. Plagiarism Detection:");
        System.out.println("   - Document comparison");
        System.out.println("   - Source code plagiarism check");
        System.out.println("   - Text similarity analysis");
        
        System.out.println("\n7. Compilers and Interpreters:");
        System.out.println("   - Lexical analysis (tokenization)");
        System.out.println("   - Pattern matching in parsing");
        System.out.println("   - String literal processing");
    }
    
    /**
     * Main method with comprehensive tests
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Find the Index of the First Occurrence in a String:");
        System.out.println("===================================================");
        
        // Explain KMP algorithm
        solution.explainKMPAlgorithm();
        
        // Test edge cases
        solution.testEdgeCases();
        
        // Example 1 from problem
        System.out.println("\n\nExample 1 from problem:");
        String haystack1 = "sadbutsad";
        String needle1 = "sad";
        int expected1 = 0;
        
        System.out.println("\nSearching for \"" + needle1 + "\" in \"" + haystack1 + "\"");
        
        int result1 = solution.strStr(haystack1, needle1);
        System.out.println("Expected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        // Example 2 from problem
        System.out.println("\n\nExample 2 from problem:");
        String haystack2 = "leetcode";
        String needle2 = "leeto";
        int expected2 = -1;
        
        System.out.println("\nSearching for \"" + needle2 + "\" in \"" + haystack2 + "\"");
        
        int result2 = solution.strStr(haystack2, needle2);
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        // Additional test cases
        System.out.println("\n\nAdditional Test Cases:");
        
        // Test case 3: Multiple occurrences
        System.out.println("\nTest Case 3: Multiple occurrences");
        String haystack3 = "mississippi";
        String needle3 = "iss";
        int expected3 = 1;
        
        int result3 = solution.strStr(haystack3, needle3);
        System.out.println("Searching for \"" + needle3 + "\" in \"" + haystack3 + "\"");
        System.out.println("Expected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + (result3 == expected3 ? "✓" : "✗"));
        
        // Test case 4: Needle at end
        System.out.println("\nTest Case 4: Needle at end");
        String haystack4 = "hello world";
        String needle4 = "world";
        int expected4 = 6;
        
        int result4 = solution.strStr(haystack4,
