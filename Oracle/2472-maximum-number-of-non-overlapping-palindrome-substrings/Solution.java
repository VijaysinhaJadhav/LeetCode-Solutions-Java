
# Solution.java

```java
import java.util.*;

/**
 * 2472. Maximum Number of Non-overlapping Palindrome Substrings
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given string s and integer k, find max non-overlapping palindrome substrings
 * where each substring has length ≥ k.
 * 
 * Key Insights:
 * 1. Precompute all palindrome substrings of length ≥ k
 * 2. Problem reduces to interval scheduling: max non-overlapping intervals
 * 3. Can solve with DP: dp[i] = max palindromes in s[0:i]
 * 4. Alternative: greedy - always take shortest palindrome starting at position
 * 
 * Approach 1: DP with Palindrome Preprocessing (RECOMMENDED)
 * O(n²) time, O(n²) space
 */

class Solution {
    
    /**
     * Approach 1: DP with Palindrome Preprocessing (RECOMMENDED)
     * Time: O(n²), Space: O(n²)
     * 1. Precompute palindrome table using DP
     * 2. DP to find max non-overlapping palindromes
     */
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        if (n < k) return 0;
        
        // Step 1: Precompute palindrome table
        boolean[][] isPal = precomputePalindromes(s);
        
        // Step 2: DP to find max non-overlapping palindromes
        int[] dp = new int[n + 1]; // dp[i] = max palindromes in first i characters
        
        for (int i = 1; i <= n; i++) {
            // Option 1: Don't take any palindrome ending at i-1
            dp[i] = dp[i - 1];
            
            // Option 2: Take a palindrome ending at i-1
            // Check all possible starting positions j where s[j..i-1] is palindrome
            // and length (i-j) >= k
            for (int j = 0; j < i; j++) {
                if (i - j >= k && isPal[j][i - 1]) {
                    dp[i] = Math.max(dp[i], (j == 0 ? 0 : dp[j]) + 1);
                }
            }
        }
        
        return dp[n];
    }
    
    /**
     * Precompute palindrome table using DP
     * isPal[i][j] = true if s[i..j] is palindrome
     */
    private boolean[][] precomputePalindromes(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];
        
        // All single characters are palindromes
        for (int i = 0; i < n; i++) {
            isPal[i][i] = true;
        }
        
        // Check palindromes of length 2
        for (int i = 0; i < n - 1; i++) {
            isPal[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }
        
        // Check palindromes of length >= 3
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                isPal[i][j] = (s.charAt(i) == s.charAt(j)) && isPal[i + 1][j - 1];
            }
        }
        
        return isPal;
    }
    
    /**
     * Approach 2: Expand Around Center with Interval DP
     * Time: O(n²), Space: O(n)
     * Find all palindrome intervals, then DP over intervals
     */
    public int maxPalindromesExpand(String s, int k) {
        int n = s.length();
        if (n < k) return 0;
        
        // Step 1: Find all palindrome intervals of length >= k
        List<int[]> intervals = new ArrayList<>();
        
        // Expand around center for odd-length palindromes
        for (int center = 0; center < n; center++) {
            expand(s, center, center, k, intervals);
        }
        
        // Expand around center for even-length palindromes
        for (int center = 0; center < n - 1; center++) {
            expand(s, center, center + 1, k, intervals);
        }
        
        // Step 2: Sort intervals by end time
        intervals.sort((a, b) -> a[1] - b[1]);
        
        // Step 3: DP over intervals
        // dp[i] = max palindromes considering first i intervals
        if (intervals.isEmpty()) return 0;
        
        int m = intervals.size();
        int[] dp = new int[m + 1];
        
        for (int i = 1; i <= m; i++) {
            int[] current = intervals.get(i - 1);
            
            // Find last non-overlapping interval
            int prev = binarySearch(intervals, current[0] - 1);
            
            // Option 1: Take current interval
            int take = dp[prev + 1] + 1;
            
            // Option 2: Skip current interval
            int skip = dp[i - 1];
            
            dp[i] = Math.max(take, skip);
        }
        
        return dp[m];
    }
    
    private void expand(String s, int left, int right, int k, List<int[]> intervals) {
        int n = s.length();
        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
            int length = right - left + 1;
            if (length >= k) {
                intervals.add(new int[]{left, right});
            }
            left--;
            right++;
        }
    }
    
    private int binarySearch(List<int[]> intervals, int targetEnd) {
        int left = 0, right = intervals.size() - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (intervals.get(mid)[1] <= targetEnd) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Greedy with Expand Around Center
     * Time: O(n²), Space: O(1)
     * Always take the shortest valid palindrome starting at current position
     */
    public int maxPalindromesGreedy(String s, int k) {
        int n = s.length();
        if (n < k) return 0;
        
        int count = 0;
        int i = 0;
        
        while (i <= n - k) {
            // Find the shortest palindrome of length >= k starting at i
            int bestEnd = n; // track the earliest ending position
            
            // Check odd-length palindromes
            for (int center = i; center < n; center++) {
                // Expand as much as possible
                int left = center, right = center;
                while (left >= i && right < n && s.charAt(left) == s.charAt(right)) {
                    if (right - left + 1 >= k) {
                        // Found a valid palindrome
                        if (right < bestEnd) {
                            bestEnd = right;
                        }
                        break; // Found shortest for this center
                    }
                    left--;
                    right++;
                }
            }
            
            // Check even-length palindromes
            for (int center = i; center < n - 1; center++) {
                int left = center, right = center + 1;
                while (left >= i && right < n && s.charAt(left) == s.charAt(right)) {
                    if (right - left + 1 >= k) {
                        if (right < bestEnd) {
                            bestEnd = right;
                        }
                        break;
                    }
                    left--;
                    right++;
                }
            }
            
            if (bestEnd < n) {
                // Found a palindrome
                count++;
                i = bestEnd + 1; // Move to next non-overlapping position
            } else {
                i++;
            }
        }
        
        return count;
    }
    
    /**
     * Approach 4: DP with Manacher's Algorithm (Optimized)
     * Time: O(n²), Space: O(n)
     * Uses Manacher to find all palindromes efficiently
     */
    public int maxPalindromesManacher(String s, int k) {
        int n = s.length();
        if (n < k) return 0;
        
        // Step 1: Precompute all palindrome intervals using Manacher
        List<int[]> intervals = getAllPalindromes(s, k);
        
        // Step 2: Sort by ending position
        intervals.sort((a, b) -> a[1] - b[1]);
        
        // Step 3: Interval scheduling DP
        if (intervals.isEmpty()) return 0;
        
        int m = intervals.size();
        int[] dp = new int[m + 1];
        
        for (int i = 1; i <= m; i++) {
            int[] current = intervals.get(i - 1);
            int start = current[0];
            
            // Binary search for last non-overlapping interval
            int left = 0, right = i - 2;
            int prev = 0;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (intervals.get(mid)[1] < start) {
                    prev = mid + 1;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            dp[i] = Math.max(dp[i - 1], dp[prev] + 1);
        }
        
        return dp[m];
    }
    
    private List<int[]> getAllPalindromes(String s, int k) {
        List<int[]> intervals = new ArrayList<>();
        int n = s.length();
        
        // Manacher's algorithm for odd-length palindromes
        int[] d1 = manacherOdd(s);
        for (int i = 0; i < n; i++) {
            int radius = d1[i];
            for (int r = k % 2 == 1 ? (k - 1) / 2 : k / 2; r <= radius; r++) {
                int length = 2 * r + 1;
                if (length >= k) {
                    intervals.add(new int[]{i - r, i + r});
                }
            }
        }
        
        // Manacher's algorithm for even-length palindromes
        int[] d2 = manacherEven(s);
        for (int i = 0; i < n; i++) {
            int radius = d2[i];
            for (int r = k / 2; r <= radius; r++) {
                int length = 2 * r;
                if (length >= k) {
                    intervals.add(new int[]{i - r, i + r - 1});
                }
            }
        }
        
        return intervals;
    }
    
    private int[] manacherOdd(String s) {
        int n = s.length();
        int[] d = new int[n];
        int l = 0, r = -1;
        
        for (int i = 0; i < n; i++) {
            int k = (i > r) ? 1 : Math.min(d[l + r - i], r - i + 1);
            while (i - k >= 0 && i + k < n && s.charAt(i - k) == s.charAt(i + k)) {
                k++;
            }
            d[i] = k--;
            if (i + k > r) {
                l = i - k;
                r = i + k;
            }
        }
        return d;
    }
    
    private int[] manacherEven(String s) {
        int n = s.length();
        int[] d = new int[n];
        int l = 0, r = -1;
        
        for (int i = 0; i < n; i++) {
            int k = (i > r) ? 0 : Math.min(d[l + r - i + 1], r - i + 1);
            while (i - k - 1 >= 0 && i + k < n && s.charAt(i - k - 1) == s.charAt(i + k)) {
                k++;
            }
            d[i] = k--;
            if (i + k > r) {
                l = i - k - 1;
                r = i + k;
            }
        }
        return d;
    }
    
    /**
     * Approach 5: DP with Optimized Palindrome Check
     * Time: O(n²), Space: O(n)
     * Build palindrome table on the fly
     */
    public int maxPalindromesOptimized(String s, int k) {
        int n = s.length();
        if (n < k) return 0;
        
        int[] dp = new int[n + 1];
        boolean[] isPal = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            dp[i + 1] = dp[i];
            
            // Check odd-length palindromes ending at i
            for (int j = 0; j <= i; j++) {
                if (s.charAt(j) == s.charAt(i)) {
                    if (i - j <= 1) {
                        isPal[j] = true;
                    } else {
                        isPal[j] = isPal[j + 1];
                    }
                    
                    if (isPal[j] && i - j + 1 >= k) {
                        dp[i + 1] = Math.max(dp[i + 1], (j == 0 ? 0 : dp[j]) + 1);
                    }
                } else {
                    isPal[j] = false;
                }
            }
        }
        
        return dp[n];
    }
    
    /**
     * Helper: Find all palindrome substrings of length >= k
     */
    public List<String> findAllPalindromeSubstrings(String s, int k) {
        List<String> result = new ArrayList<>();
        int n = s.length();
        
        boolean[][] isPal = precomputePalindromes(s);
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (j - i + 1 >= k && isPal[i][j]) {
                    result.add(s.substring(i, j + 1));
                }
            }
        }
        
        return result;
    }
    
    /**
     * Helper: Visualize the DP solution
     */
    public void visualizeSolution(String s, int k) {
        System.out.println("\nSolution Visualization:");
        System.out.println("s = \"" + s + "\", k = " + k);
        System.out.println("Length: " + s.length());
        
        // Step 1: Find all palindrome substrings of length >= k
        List<String> allPalindromes = findAllPalindromeSubstrings(s, k);
        System.out.println("\nAll palindrome substrings of length >= " + k + ":");
        for (String pal : allPalindromes) {
            System.out.println("  \"" + pal + "\" (length " + pal.length() + ")");
        }
        
        // Step 2: Show intervals
        System.out.println("\nPalindrome intervals (start, end, length):");
        boolean[][] isPal = precomputePalindromes(s);
        List<int[]> intervals = new ArrayList<>();
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (j - i + 1 >= k && isPal[i][j]) {
                    System.out.printf("  [%d, %d] = \"%s\" (length %d)%n",
                        i, j, s.substring(i, j + 1), j - i + 1);
                    intervals.add(new int[]{i, j});
                }
            }
        }
        
        // Step 3: Show DP table
        System.out.println("\nDP table calculation:");
        int n = s.length();
        int[] dp = new int[n + 1];
        String[] choices = new String[n + 1];
        choices[0] = "";
        
        System.out.printf("%-5s %-10s %-30s %-20s%n", 
            "i", "dp[i]", "s[0:i-1]", "Choice");
        System.out.println("-".repeat(65));
        System.out.printf("%-5d %-10d %-30s %-20s%n", 
            0, dp[0], "", choices[0]);
        
        for (int i = 1; i <= n; i++) {
            // Initialize with dp[i-1]
            dp[i] = dp[i - 1];
            choices[i] = choices[i - 1];
            
            // Check all possible palindromes ending at i-1
            for (int j = 0; j < i; j++) {
                if (i - j >= k && isPal[j][i - 1]) {
                    int newVal = (j == 0 ? 0 : dp[j]) + 1;
                    if (newVal > dp[i]) {
                        dp[i] = newVal;
                        String palindrome = s.substring(j, i);
                        choices[i] = (j == 0 ? "" : choices[j] + ", ") + "\"" + palindrome + "\"";
                    }
                }
            }
            
            System.out.printf("%-5d %-10d %-30s %-20s%n", 
                i, dp[i], s.substring(0, i), choices[i]);
        }
        
        // Step 4: Show optimal solution
        System.out.println("\nOptimal solution: " + dp[n] + " palindrome(s)");
        System.out.println("Selected palindromes: " + choices[n]);
        
        // Show non-overlapping property
        if (dp[n] > 0) {
            System.out.println("\nVerifying non-overlapping property:");
            String[] selected = choices[n].split(", ");
            int lastEnd = -1;
            for (String pal : selected) {
                if (!pal.isEmpty()) {
                    String clean = pal.replace("\"", "").trim();
                    int start = s.indexOf(clean, lastEnd + 1);
                    int end = start + clean.length() - 1;
                    System.out.printf("  \"%s\" at [%d, %d]%n", clean, start, end);
                    if (start <= lastEnd) {
                        System.out.println("  WARNING: Overlap detected!");
                    }
                    lastEnd = end;
                }
            }
        }
    }
    
    /**
     * Helper: Explain the algorithm in detail
     */
    public void explainAlgorithm() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nProblem Restatement:");
        System.out.println("Given string s and integer k, find maximum number of");
        System.out.println("non-overlapping palindrome substrings, each of length ≥ k.");
        
        System.out.println("\nKey Challenges:");
        System.out.println("1. Need to find palindrome substrings efficiently");
        System.out.println("2. Need to avoid overlaps between selected palindromes");
        System.out.println("3. Need to maximize count, not total length");
        
        System.out.println("\nSolution Approach (DP with Palindrome Preprocessing):");
        System.out.println("\nStep 1: Precompute Palindrome Table");
        System.out.println("  Use DP to compute isPal[i][j] = true if s[i..j] is palindrome");
        System.out.println("  Base cases:");
        System.out.println("    - Single character: isPal[i][i] = true");
        System.out.println("    - Two characters: isPal[i][i+1] = (s[i] == s[i+1])");
        System.out.println("  Recursive case:");
        System.out.println("    isPal[i][j] = (s[i] == s[j]) && isPal[i+1][j-1]");
        System.out.println("  Time: O(n²), Space: O(n²)");
        
        System.out.println("\nStep 2: Dynamic Programming for Non-overlapping Selection");
        System.out.println("  Let dp[i] = max palindromes in first i characters (s[0..i-1])");
        System.out.println("  Base: dp[0] = 0");
        System.out.println("  Transition:");
        System.out.println("    Option 1: Don't use s[i-1] → dp[i] = dp[i-1]");
        System.out.println("    Option 2: Use palindrome ending at i-1");
        System.out.println("      For all j where s[j..i-1] is palindrome and length ≥ k:");
        System.out.println("        dp[i] = max(dp[i], dp[j] + 1)");
        System.out.println("  Answer: dp[n]");
        
        System.out.println("\nTime Complexity Analysis:");
        System.out.println("  - Palindrome preprocessing: O(n²)");
        System.out.println("  - DP: O(n²) (for each i, check all j < i)");
        System.out.println("  - Total: O(n²)");
        
        System.out.println("\nSpace Complexity:");
        System.out.println("  - Palindrome table: O(n²)");
        System.out.println("  - DP array: O(n)");
        System.out.println("  - Total: O(n²)");
        
        System.out.println("\nExample: s = \"abaccdbbd\", k = 3");
        System.out.println("Palindrome substrings of length ≥ 3:");
        System.out.println("  \"aba\" (0-2), \"acca\" (1-4), \"dbbd\" (6-9)");
        System.out.println("Optimal selection:");
        System.out.println("  Take \"aba\" (0-2) and \"dbbd\" (6-9) → 2 palindromes");
        System.out.println("  Cannot take \"acca\" because it overlaps with \"aba\"");
        
        System.out.println("\nAlternative Approaches:");
        System.out.println("1. Greedy: Always take shortest palindrome starting at current position");
        System.out.println("2. Interval Scheduling: Treat palindromes as intervals, find max non-overlapping");
        System.out.println("3. Manacher's Algorithm: O(n) palindrome finding, then interval DP");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES TESTING:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. Empty string (impossible due to constraints, but handle):");
        // Note: constraints say s.length >= 1, but test anyway
        int result1 = solution.maxPalindromes("", 1);
        System.out.println("   Result: " + result1 + " (should be 0)");
        
        System.out.println("\n2. Single character, k=1:");
        int result2 = solution.maxPalindromes("a", 1);
        System.out.println("   Result: " + result2 + " (should be 1: [\"a\"])");
        
        System.out.println("\n3. Single character, k=2:");
        int result3 = solution.maxPalindromes("a", 2);
        System.out.println("   Result: " + result3 + " (should be 0)");
        
        System.out.println("\n4. All same characters:");
        int result4 = solution.maxPalindromes("aaaaa", 2);
        System.out.println("   Result: " + result4 + " (should be 2: can take \"aa\" and \"aa\")");
        
        System.out.println("\n5. No palindromes of length ≥ k:");
        int result5 = solution.maxPalindromes("abcde", 3);
        System.out.println("   Result: " + result5 + " (should be 0)");
        
        System.out.println("\n6. Overlapping palindromes:");
        int result6 = solution.maxPalindromes("ababa", 3);
        System.out.println("   Result: " + result6 + " (should be 1: can only take one of \"aba\", \"bab\", \"aba\")");
        
        System.out.println("\n7. Example 1 from problem:");
        int result7 = solution.maxPalindromes("abaccdbbd", 3);
        System.out.println("   Result: " + result7 + " (should be 2)");
        
        System.out.println("\n8. Example 2 from problem:");
        int result8 = solution.maxPalindromes("adbcda", 2);
        System.out.println("   Result: " + result8 + " (should be 0)");
        
        System.out.println("\n9. k equals string length:");
        int result9 = solution.maxPalindromes("racecar", 7);
        System.out.println("   Result: " + result9 + " (should be 1: whole string is palindrome)");
        
        System.out.println("\n10. Multiple choices:");
        int result10 = solution.maxPalindromes("aaabbbaaa", 3);
        System.out.println("   Result: " + result10 + " (several valid selections)");
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(String s, int k) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES FOR s = \"" + s + "\", k = " + k + ":");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5;
        
        // Approach 1: DP with Palindrome Preprocessing
        startTime = System.nanoTime();
        result1 = solution.maxPalindromes(s, k);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Expand with Interval DP
        startTime = System.nanoTime();
        result2 = solution.maxPalindromesExpand(s, k);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Greedy
        startTime = System.nanoTime();
        result3 = solution.maxPalindromesGreedy(s, k);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Manacher
        startTime = System.nanoTime();
        result4 = solution.maxPalindromesManacher(s, k);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: Optimized DP
        startTime = System.nanoTime();
        result5 = solution.maxPalindromesOptimized(s, k);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("DP with Preprocessing:  " + result1);
        System.out.println("Expand with Interval:   " + result2);
        System.out.println("Greedy:                " + result3);
        System.out.println("Manacher:              " + result4);
        System.out.println("Optimized DP:          " + result5);
        
        // Check if all results are the same (greedy might be suboptimal)
        boolean dpEqual = (result1 == result2) && (result2 == result4) && (result4 == result5);
        System.out.println("\nDP-based approaches agree: " + (dpEqual ? "✓ YES" : "✗ NO"));
        System.out.println("Greedy matches DP: " + (result3 == result1 ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("DP with Preprocessing:  %-10d (O(n²) time, O(n²) space)%n", time1);
        System.out.printf("Expand with Interval:   %-10d (O(n²) time, O(n) space)%n", time2);
        System.out.printf("Greedy:                %-10d (O(n²) time, O(1) space)%n", time3);
        System.out.printf("Manacher:              %-10d (O(n²) time, O(n) space)%n", time4);
        System.out.printf("Optimized DP:          %-10d (O(n²) time, O(n) space)%n", time5);
        
        // Visualize for small strings
        if (s.length() <= 20) {
            System.out.println("\n" + "-".repeat(80));
            solution.visualizeSolution(s, k);
        }
    }
    
    /**
     * Helper: Analyze complexity and trade-offs
     */
    public void analyzeComplexity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS AND TRADE-OFFS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Time Complexity Comparison:");
        System.out.println("   Approach                  | Time      | Space     | Notes");
        System.out.println("   --------------------------|-----------|-----------|------------------");
        System.out.println("   DP with Preprocessing     | O(n²)     | O(n²)     | Recommended, clear");
        System.out.println("   Expand with Interval DP   | O(n²)     | O(n)      | Good space usage");
        System.out.println("   Greedy                   | O(n²)     | O(1)      | May be suboptimal");
        System.out.println("   Manacher + Interval DP   | O(n²)     | O(n)      | Fast palindrome");
        System.out.println("   Optimized DP             | O(n²)     | O(n)      | Space efficient");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   - DP with Preprocessing: O(n²) for palindrome table");
        System.out.println("   - Others: O(n) for DP array or interval list");
        System.out.println("   - For n ≤ 2000: O(n²) = 4MB (acceptable)");
        System.out.println("   - For memory constraints: use O(n) approaches");
        
        System.out.println("\n3. When to use each approach:");
        System.out.println("   - Interview: DP with Preprocessing (most explainable)");
        System.out.println("   - Production: Optimized DP or Manacher (space efficient)");
        System.out.println("   - Quick solution: Greedy (simple but may be suboptimal)");
        System.out.println("   - Memory constrained: Greedy or Optimized DP");
        
        System.out.println("\n4. Optimality Guarantee:");
        System.out.println("   - DP approaches: Guaranteed optimal (dynamic programming)");
        System.out.println("   - Greedy: May be suboptimal (depends on specific cases)");
        System.out.println("   - Interval scheduling: Optimal when intervals sorted by end");
        
        System.out.println("\n5. Constraints Analysis:");
        System.out.println("   n ≤ 2000");
        System.out.println("   - O(n²) = 4M operations (fast in modern computers)");
        System.out.println("   - O(n²) space = 4MB (acceptable)");
        System.out.println("   - All O(n²) approaches are acceptable");
    }
    
    /**
     * Helper: Show related problems
     */
    public void showRelatedProblems() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 5. Longest Palindromic Substring:");
        System.out.println("   - Find the longest palindrome substring");
        System.out.println("   - Uses similar palindrome detection techniques");
        
        System.out.println("\n2. 647. Palindromic Substrings:");
        System.out.println("   - Count all palindrome substrings");
        System.out.println("   - Foundation for this problem");
        
        System.out.println("\n3. 132. Palindrome Partitioning II:");
        System.out.println("   - Minimum cuts to partition into palindromes");
        System.out.println("   - Similar DP approach");
        
        System.out.println("\n4. 516. Longest Palindromic Subsequence:");
        System.out.println("   - Longest palindrome subsequence (not substring)");
        System.out.println("   - Different problem but related concept");
        
        System.out.println("\n5. 1278. Palindrome Partitioning III:");
        System.out.println("   - Partition into k palindrome substrings");
        System.out.println("   - More complex version
