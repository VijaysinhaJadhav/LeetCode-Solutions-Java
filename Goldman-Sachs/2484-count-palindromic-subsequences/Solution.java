
# Solution.java

```java
import java.util.*;

/**
 * 2484. Count Palindromic Subsequences
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Count distinct palindromic subsequences of length exactly 4.
 * 
 * Key Insights:
 * 1. Palindrome of length 4: positions i < j < k < l
 * 2. Need s[i] == s[l] and s[j] == s[k]
 * 3. Fix outer characters, count distinct middle pairs
 */
class Solution {
    
    private static final int MOD = 1_000_000_007;
    
    /**
     * Approach 1: Fix Outer Characters + Count Middle Pairs (Recommended)
     * Time: O(10 × n²), Space: O(1)
     * 
     * Steps:
     * 1. For each digit 'a' to '9' (10 possible outer characters)
     * 2. Find all pairs (i, l) where s[i] == s[l] == digit and l - i >= 3
     * 3. For each pair, count distinct (s[j], s[k]) where i < j < k < l
     * 4. Use a boolean[10][10] to track seen middle digit pairs
     * 5. Sum up counts
     */
    public int countPalindromicSubsequences(String s) {
        int n = s.length();
        long result = 0;
        
        // For each possible outer character (digit 0-9)
        for (char outer = '0'; outer <= '9'; outer++) {
            // Find first and last occurrence of outer character
            int left = 0;
            int right = n - 1;
            
            while (left < n && s.charAt(left) != outer) left++;
            while (right >= 0 && s.charAt(right) != outer) right--;
            
            if (right - left < 3) continue;
            
            // Track seen middle pairs
            boolean[][] seen = new boolean[10][10];
            
            // For all possible middle positions between left and right
            for (int j = left + 1; j <= right - 2; j++) {
                for (int k = j + 1; k <= right - 1; k++) {
                    if (s.charAt(j) == s.charAt(k)) {
                        int a = s.charAt(j) - '0';
                        int b = s.charAt(k) - '0';
                        if (!seen[a][b]) {
                            seen[a][b] = true;
                            result++;
                        }
                    }
                }
            }
        }
        
        return (int) (result % MOD);
    }
    
    /**
     * Approach 2: Optimized with Prefix/Suffix Counts
     * Time: O(10 × n²), Space: O(10 × n)
     * 
     * Precompute prefix and suffix counts for each digit
     */
    public int countPalindromicSubsequencesOptimized(String s) {
        int n = s.length();
        long result = 0;
        
        // Prefix counts: prefix[i][d] = count of digit d in s[0..i-1]
        int[][] prefix = new int[n + 1][10];
        for (int i = 0; i < n; i++) {
            for (int d = 0; d < 10; d++) {
                prefix[i + 1][d] = prefix[i][d];
            }
            prefix[i + 1][s.charAt(i) - '0']++;
        }
        
        // Suffix counts: suffix[i][d] = count of digit d in s[i..n-1]
        int[][] suffix = new int[n + 1][10];
        for (int i = n - 1; i >= 0; i--) {
            for (int d = 0; d < 10; d++) {
                suffix[i][d] = suffix[i + 1][d];
            }
            suffix[i][s.charAt(i) - '0']++;
        }
        
        // For each possible outer character
        for (char outer = '0'; outer <= '9'; outer++) {
            int od = outer - '0';
            
            // For each possible middle positions
            for (int j = 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    if (s.charAt(j) != s.charAt(k)) continue;
                    int md = s.charAt(j) - '0';
                    
                    // Count how many 'outer' before j and after k
                    long leftCount = prefix[j][od];
                    long rightCount = suffix[k + 1][od];
                    
                    result = (result + leftCount * rightCount) % MOD;
                }
            }
        }
        
        return (int) result;
    }
    
    /**
     * Approach 3: Dynamic Programming with Counting
     * Time: O(10 × n²), Space: O(1)
     * 
     * For each outer character, two-pointer approach
     */
    public int countPalindromicSubsequencesDP(String s) {
        int n = s.length();
        long result = 0;
        
        for (char outer = '0'; outer <= '9'; outer++) {
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == outer) {
                    positions.add(i);
                }
            }
            
            int m = positions.size();
            if (m < 2) continue;
            
            // For each pair of positions
            for (int a = 0; a < m - 1; a++) {
                for (int b = a + 1; b < m; b++) {
                    int left = positions.get(a);
                    int right = positions.get(b);
                    if (right - left < 3) continue;
                    
                    // Count distinct middle pairs
                    boolean[][] seen = new boolean[10][10];
                    for (int j = left + 1; j <= right - 2; j++) {
                        for (int k = j + 1; k <= right - 1; k++) {
                            if (s.charAt(j) == s.charAt(k)) {
                                int d1 = s.charAt(j) - '0';
                                int d2 = s.charAt(k) - '0';
                                if (!seen[d1][d2]) {
                                    seen[d1][d2] = true;
                                    result++;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return (int) (result % MOD);
    }
    
    /**
     * Approach 4: Using Prefix and Suffix Next/Prev Arrays
     * Time: O(10 × n²), Space: O(10 × n)
     * 
     * Precompute next and previous occurrence for each position
     */
    public int countPalindromicSubsequencesNextPrev(String s) {
        int n = s.length();
        long result = 0;
        
        // next[i][d] = next index >= i where char d appears
        int[][] next = new int[n + 1][10];
        for (int d = 0; d < 10; d++) {
            next[n][d] = n;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int d = 0; d < 10; d++) {
                next[i][d] = next[i + 1][d];
            }
            next[i][s.charAt(i) - '0'] = i;
        }
        
        // prev[i][d] = previous index <= i where char d appears
        int[][] prev = new int[n][10];
        for (int d = 0; d < 10; d++) {
            prev[0][d] = -1;
        }
        prev[0][s.charAt(0) - '0'] = 0;
        for (int i = 1; i < n; i++) {
            for (int d = 0; d < 10; d++) {
                prev[i][d] = prev[i - 1][d];
            }
            prev[i][s.charAt(i) - '0'] = i;
        }
        
        // For each middle pair (j, k)
        for (int j = 1; j < n - 2; j++) {
            for (int k = j + 1; k < n - 1; k++) {
                if (s.charAt(j) != s.charAt(k)) continue;
                
                // Count outer pairs
                for (char outer = '0'; outer <= '9'; outer++) {
                    int od = outer - '0';
                    int leftMost = prev[j - 1][od];
                    int rightMost = next[k + 1][od];
                    
                    if (leftMost != -1 && rightMost != n && rightMost > leftMost) {
                        result++;
                    }
                }
            }
        }
        
        return (int) (result % MOD);
    }
    
    /**
     * Approach 5: Brute Force (for small strings)
     * Time: O(n⁴), Space: O(n⁴)
     * 
     * Generate all subsequences of length 4 and check if palindrome
     */
    public int countPalindromicSubsequencesBruteForce(String s) {
        int n = s.length();
        Set<String> palindromes = new HashSet<>();
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (s.charAt(i) == s.charAt(l) && s.charAt(j) == s.charAt(k)) {
                            palindromes.add("" + s.charAt(i) + s.charAt(j) + s.charAt(k) + s.charAt(l));
                        }
                    }
                }
            }
        }
        
        return palindromes.size();
    }
    
    /**
     * Helper: Visualize the counting process
     */
    public void visualizeCount(String s) {
        System.out.println("\nCount Palindromic Subsequences Visualization:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nString: \"" + s + "\"");
        System.out.println("Length: " + s.length());
        
        int n = s.length();
        long result = 0;
        
        System.out.println("\nProcessing each outer character:");
        
        for (char outer = '0'; outer <= '9'; outer++) {
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == outer) {
                    positions.add(i);
                }
            }
            
            if (positions.size() < 2) continue;
            
            System.out.printf("\nOuter character '%c' at positions: %s%n", outer, positions);
            
            int m = positions.size();
            long outerCount = 0;
            
            for (int a = 0; a < m - 1; a++) {
                for (int b = a + 1; b < m; b++) {
                    int left = positions.get(a);
                    int right = positions.get(b);
                    if (right - left < 3) continue;
                    
                    Set<String> middlePairs = new HashSet<>();
                    
                    for (int j = left + 1; j <= right - 2; j++) {
                        for (int k = j + 1; k <= right - 1; k++) {
                            if (s.charAt(j) == s.charAt(k)) {
                                String pair = "" + s.charAt(j) + s.charAt(k);
                                middlePairs.add(pair);
                            }
                        }
                    }
                    
                    outerCount += middlePairs.size();
                    System.out.printf("  Pair (%d, %d): middle pairs = %s → count += %d%n", 
                        left, right, middlePairs, middlePairs.size());
                }
            }
            
            System.out.printf("  Total for '%c': %d%n", outer, outerCount);
            result += outerCount;
        }
        
        System.out.println("\nTotal distinct palindromic subsequences: " + result);
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[]{
            "103301",           // 2
            "0000000",          // 1
            "1111111",          // 1
            "12345678",         // 0
            "1000000001",       // 1 (1001)
            "1020304050",       // 0
            "11223344",         // 4 (1122, 1133, 1144, 2233, etc.)
            "9999999999"        // 1
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[] testCases = generateTestCases();
        int[] expected = {2, 1, 1, 0, 1, 0, 4, 1};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.printf("\nTest %d: \"%s\"%n", i + 1, s);
            
            int result1 = countPalindromicSubsequences(s);
            int result2 = countPalindromicSubsequencesOptimized(s);
            int result3 = countPalindromicSubsequencesDP(s);
            int result4 = countPalindromicSubsequencesNextPrev(s);
            int result5 = s.length() <= 8 ? countPalindromicSubsequencesBruteForce(s) : result1;
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Count: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeCount(s);
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
        System.out.println("=".repeat(50));
        
        // Generate test string
        int n = 2000;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            sb.append((char) ('0' + rand.nextInt(10)));
        }
        String s = sb.toString();
        
        System.out.println("Test Setup: string length = " + n);
        
        long[] times = new long[4];
        int[] results = new int[4];
        
        // Method 1: Fix Outer Characters
        long start = System.currentTimeMillis();
        results[0] = countPalindromicSubsequences(s);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Optimized with Prefix/Suffix
        start = System.currentTimeMillis();
        results[1] = countPalindromicSubsequencesOptimized(s);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: DP with Positions
        start = System.currentTimeMillis();
        results[2] = countPalindromicSubsequencesDP(s);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Next/Prev Arrays
        start = System.currentTimeMillis();
        results[3] = countPalindromicSubsequencesNextPrev(s);
        times[3] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Fix Outer + Count      | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Prefix/Suffix          | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. DP with Positions      | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. Next/Prev Arrays       | %9d | %6d%n", times[3], results[3]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Fix Outer + Count is simplest and efficient");
        System.out.println("2. Prefix/Suffix method is similar complexity");
        System.out.println("3. All O(n²) methods perform similarly");
        System.out.println("4. For n=2000, O(n²) ≈ 4M operations per digit");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Minimum length (n=4):");
        System.out.println("   Input: \"1234\"");
        System.out.println("   Output: " + countPalindromicSubsequences("1234"));
        
        System.out.println("\n2. All same digits (n=4):");
        System.out.println("   Input: \"1111\"");
        System.out.println("   Output: " + countPalindromicSubsequences("1111"));
        
        System.out.println("\n3. Two outer, two inner different:");
        System.out.println("   Input: \"1001\"");
        System.out.println("   Output: " + countPalindromicSubsequences("1001"));
        
        System.out.println("\n4. Two outer, multiple inner pairs:");
        System.out.println("   Input: \"101101\"");
        System.out.println("   Output: " + countPalindromicSubsequences("101101"));
        
        System.out.println("\n5. All distinct digits:");
        System.out.println("   Input: \"0123456789\"");
        System.out.println("   Output: " + countPalindromicSubsequences("0123456789"));
        
        System.out.println("\n6. Large string (n=2000):");
        String large = "0".repeat(2000);
        long start = System.currentTimeMillis();
        int result = countPalindromicSubsequences(large);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 2000 zeros");
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nProblem: Count distinct palindromic subsequences of length 4.");
        
        System.out.println("\nPalindrome Structure (length 4):");
        System.out.println("  positions: i < j < k < l");
        System.out.println("  conditions: s[i] == s[l] and s[j] == s[k]");
        System.out.println("  forms: a b b a (or a b c a if b != c)");
        
        System.out.println("\nKey Insight:");
        System.out.println("  For a fixed outer pair (i, l) with same digit,");
        System.out.println("  any pair (j, k) with i < j < k < l and s[j] == s[k]");
        System.out.println("  forms a valid palindrome.");
        
        System.out.println("\nAlgorithm:");
        System.out.println("1. For each outer digit d from '0' to '9':");
        System.out.println("2. Find all pairs (i, l) where s[i] == s[l] == d and l - i >= 3");
        System.out.println("3. For each pair, count distinct (s[j], s[k]) for i < j < k < l");
        System.out.println("4. Use a boolean[10][10] to track seen middle digit pairs");
        System.out.println("5. Sum up all counts");
        
        System.out.println("\nExample: s = \"103301\"");
        System.out.println("  Outer '1':");
        System.out.println("    Pair (0,5): middle pairs: (0,3) → \"00\", (3,0) → \"00\" → distinct: {\"00\"}");
        System.out.println("    → count += 1");
        System.out.println("  Outer '3':");
        System.out.println("    Pair (2,3): middle pairs: none (no j,k between 2 and 3)");
        System.out.println("    Pair (2,5): middle pairs: (3,4) → \"33\" → distinct: {\"33\"}");
        System.out.println("    → count += 1");
        System.out.println("  Outer '0': no pairs");
        System.out.println("  Total = 2");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What length subsequences? (Exactly 4)");
        System.out.println("   - Distinct by value or by indices? (By value)");
        System.out.println("   - Digits only? (Yes, '0'-'9')");
        
        System.out.println("\n2. Understand the structure:");
        System.out.println("   - For length 4 palindrome, s[i] == s[l] and s[j] == s[k]");
        System.out.println("   - This is a fixed pattern that simplifies counting");
        
        System.out.println("\n3. Propose brute force:");
        System.out.println("   - O(n⁴) enumerating all quadruples");
        System.out.println("   - Too slow for n=2000");
        
        System.out.println("\n4. Optimize with counting:");
        System.out.println("   - Fix outer characters (10 possibilities)");
        System.out.println("   - Count distinct middle pairs using sets");
        System.out.println("   - O(10 × n²) time");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(10 × n²) ≈ 4M operations for n=2000");
        System.out.println("   - Space: O(1) extra space");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - Minimum length (n=4)");
        System.out.println("   - All same digits");
        System.out.println("   - No valid palindromes");
        System.out.println("   - Large n=2000");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Counting same palindrome multiple times");
        System.out.println("   - Not ensuring l - i >= 3 (need at least 2 middle positions)");
        System.out.println("   - Forgetting modulo operation");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("2484. Count Palindromic Subsequences");
        System.out.println("====================================");
        
        // Explain algorithm
        solution.explainAlgorithm();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
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
    private static final int MOD = 1_000_000_007;
    
    public int countPalindromicSubsequences(String s) {
        int n = s.length();
        long result = 0;
        
        for (char outer = '0'; outer <= '9'; outer++) {
            int left = 0, right = n - 1;
            while (left < n && s.charAt(left) != outer) left++;
            while (right >= 0 && s.charAt(right) != outer) right--;
            
            if (right - left < 3) continue;
            
            boolean[][] seen = new boolean[10][10];
            
            for (int j = left + 1; j <= right - 2; j++) {
                for (int k = j + 1; k <= right - 1; k++) {
                    if (s.charAt(j) == s.charAt(k)) {
                        int a = s.charAt(j) - '0';
                        int b = s.charAt(k) - '0';
                        if (!seen[a][b]) {
                            seen[a][b] = true;
                            result++;
                        }
                    }
                }
            }
        }
        
        return (int) (result % MOD);
    }
}
            """);
        
        System.out.println("\nAlternative (Prefix/Suffix Counts):");
        System.out.println("""
class Solution {
    private static final int MOD = 1_000_000_007;
    
    public int countPalindromicSubsequences(String s) {
        int n = s.length();
        long result = 0;
        
        int[][] prefix = new int[n + 1][10];
        for (int i = 0; i < n; i++) {
            for (int d = 0; d < 10; d++) {
                prefix[i + 1][d] = prefix[i][d];
            }
            prefix[i + 1][s.charAt(i) - '0']++;
        }
        
        for (int j = 1; j < n - 2; j++) {
            for (int k = j + 1; k < n - 1; k++) {
                if (s.charAt(j) != s.charAt(k)) continue;
                int md = s.charAt(j) - '0';
                
                for (int od = 0; od < 10; od++) {
                    long leftCount = prefix[j][od];
                    long rightCount = prefix[n][od] - prefix[k + 1][od];
                    result = (result + leftCount * rightCount) % MOD;
                }
            }
        }
        
        return (int) result;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Palindrome of length 4 has fixed structure");
        System.out.println("2. Fix outer characters, count distinct middle pairs");
        System.out.println("3. Use boolean[10][10] to avoid counting duplicates");
        System.out.println("4. Time complexity: O(10 × n²)");
        System.out.println("5. Space complexity: O(1)");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(10 × n²) = O(n²) with constant factor 10");
        System.out.println("- Space: O(1) extra space");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you count palindromic subsequences of length 3?");
        System.out.println("2. How would you count all palindromic subsequences (any length)?");
        System.out.println("3. What if digits could be letters? (Same approach, 26 possibilities)");
        System.out.println("4. How would you return the actual subsequences, not just count?");
        System.out.println("5. How would you handle very large n (e.g., 10^5)?");
    }
}
