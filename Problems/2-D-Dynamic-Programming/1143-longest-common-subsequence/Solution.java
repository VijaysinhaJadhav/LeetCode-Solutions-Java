
## Solution.java

```java
/**
 * 1143. Longest Common Subsequence
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given two strings text1 and text2, return the length of their longest common subsequence.
 * 
 * Key Insights:
 * 1. Use dynamic programming with 2D table
 * 2. dp[i][j] = LCS of text1[0..i-1] and text2[0..j-1]
 * 3. Recurrence relation:
 *    - If chars match: dp[i][j] = 1 + dp[i-1][j-1]
 *    - Else: dp[i][j] = max(dp[i-1][j], dp[i][j-1])
 * 4. Can optimize space to O(min(m,n))
 * 
 * Approach (Dynamic Programming):
 * 1. Create DP table of size (m+1) x (n+1)
 * 2. Initialize first row and column to 0 (base cases)
 * 3. Fill table using recurrence relation
 * 4. Return dp[m][n]
 * 
 * Time Complexity: O(m×n)
 * Space Complexity: O(m×n) or O(min(m,n)) with optimization
 * 
 * Tags: String, Dynamic Programming
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Standard Dynamic Programming (Recommended)
     * O(m×n) time, O(m×n) space
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        // Create DP table with extra row and column for base cases
        int[][] dp = new int[m + 1][n + 1];
        
        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    // Characters match - extend LCS from previous subsequences
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // Characters don't match - take maximum from skipping char in text1 or text2
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * Approach 2: Space Optimized DP
     * O(m×n) time, O(min(m,n)) space
     * Uses only two rows instead of full table
     */
    public int longestCommonSubsequenceSpaceOptimized(String text1, String text2) {
        // Ensure text1 is the shorter string for space optimization
        if (text1.length() < text2.length()) {
            return longestCommonSubsequenceSpaceOptimized(text2, text1);
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // Use only two rows - current and previous
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            // Swap rows for next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
            // Reset current row for next iteration
            Arrays.fill(curr, 0);
        }
        
        return prev[n];
    }
    
    /**
     * Approach 3: Further Space Optimization with Single Array
     * O(m×n) time, O(min(m,n)) space
     * Uses single array with careful updating
     */
    public int longestCommonSubsequenceSingleArray(String text1, String text2) {
        if (text1.length() < text2.length()) {
            return longestCommonSubsequenceSingleArray(text2, text1);
        }
        
        int m = text1.length();
        int n = text2.length();
        
        int[] dp = new int[n + 1];
        
        for (int i = 1; i <= m; i++) {
            int prev = 0; // dp[i-1][j-1] from previous row
            for (int j = 1; j <= n; j++) {
                int temp = dp[j]; // Store current before updating (becomes prev for next iteration)
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[j] = 1 + prev;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                prev = temp; // Update prev for next iteration
            }
        }
        
        return dp[n];
    }
    
    /**
     * Approach 4: Recursive with Memoization
     * O(m×n) time, O(m×n) space
     * Top-down approach
     */
    public int longestCommonSubsequenceRecursive(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return lcsHelper(text1, text2, 0, 0, memo);
    }
    
    private int lcsHelper(String text1, String text2, int i, int j, int[][] memo) {
        // Base case: reached end of either string
        if (i == text1.length() || j == text2.length()) {
            return 0;
        }
        
        // Check memo
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        
        int result;
        if (text1.charAt(i) == text2.charAt(j)) {
            // Characters match - include in LCS and move both pointers
            result = 1 + lcsHelper(text1, text2, i + 1, j + 1, memo);
        } else {
            // Characters don't match - try skipping char from text1 or text2
            int skip1 = lcsHelper(text1, text2, i + 1, j, memo);
            int skip2 = lcsHelper(text1, text2, i, j + 1, memo);
            result = Math.max(skip1, skip2);
        }
        
        memo[i][j] = result;
        return result;
    }
    
    /**
     * Approach 5: DP with Reconstruction
     * O(m×n) time, O(m×n) space
     * Also reconstructs the actual LCS string
     */
    public int longestCommonSubsequenceWithReconstruction(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        int[][] dp = new int[m + 1][n + 1];
        int[][] direction = new int[m + 1][n + 1]; // 0: diagonal, 1: up, 2: left
        
        // Fill DP table and track directions
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    direction[i][j] = 0; // Diagonal (match)
                } else {
                    if (dp[i - 1][j] >= dp[i][j - 1]) {
                        dp[i][j] = dp[i - 1][j];
                        direction[i][j] = 1; // Up
                    } else {
                        dp[i][j] = dp[i][j - 1];
                        direction[i][j] = 2; // Left
                    }
                }
            }
        }
        
        // Reconstruct LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (direction[i][j] == 0) {
                // Characters match - add to LCS (in reverse order)
                lcs.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (direction[i][j] == 1) {
                // Move up
                i--;
            } else {
                // Move left
                j--;
            }
        }
        
        String lcsString = lcs.reverse().toString();
        System.out.println("Longest Common Subsequence: \"" + lcsString + "\"");
        
        return dp[m][n];
    }
    
    /**
     * Approach 6: Iterative with Early Termination
     * O(m×n) time, O(min(m,n)) space
     * Can terminate early if remaining characters can't beat current max
     */
    public int longestCommonSubsequenceEarlyTermination(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        // Swap to ensure text1 is shorter for space optimization
        if (m < n) {
            return longestCommonSubsequenceEarlyTermination(text2, text1);
        }
        
        int[] dp = new int[n + 1];
        
        for (int i = 1; i <= m; i++) {
            int prev = 0;
            for (int j = 1; j <= n; j++) {
                int temp = dp[j];
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[j] = 1 + prev;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                prev = temp;
                
                // Early termination: if we've reached maximum possible LCS
                if (dp[j] == n) {
                    return n;
                }
            }
        }
        
        return dp[n];
    }
    
    /**
     * Helper method to visualize the DP table
     */
    public void visualizeDPTable(String text1, String text2, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Text1: \"" + text1 + "\"");
        System.out.println("Text2: \"" + text2 + "\"");
        
        if ("Standard DP".equals(approach)) {
            visualizeStandardDP(text1, text2);
        } else if ("Space Optimized".equals(approach)) {
            visualizeSpaceOptimizedDP(text1, text2);
        }
    }
    
    private void visualizeStandardDP(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        System.out.println("\nDP Table Construction:");
        System.out.print("    ");
        for (char c : text2.toCharArray()) {
            System.out.print(c + " ");
        }
        System.out.println();
        
        for (int i = 1; i <= m; i++) {
            System.out.print(text1.charAt(i - 1) + " ");
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    System.out.print(dp[i][j] + " ");
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    System.out.print(dp[i][j] + " ");
                }
            }
            System.out.println();
        }
        
        System.out.println("Final LCS length: " + dp[m][n]);
        
        // Show reconstruction path
        visualizeReconstruction(text1, text2, dp);
    }
    
    private void visualizeSpaceOptimizedDP(String text1, String text2) {
        if (text1.length() < text2.length()) {
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }
        
        int m = text1.length();
        int n = text2.length();
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];
        
        System.out.println("\nSpace Optimized DP (showing current row at each step):");
        System.out.print("  ");
        for (char c : text2.toCharArray()) {
            System.out.print(c + " ");
        }
        System.out.println();
        
        for (int i = 1; i <= m; i++) {
            System.out.print(text1.charAt(i - 1) + " ");
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
                System.out.print(curr[j] + " ");
            }
            System.out.println();
            
            // Swap for next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
            Arrays.fill(curr, 0);
        }
        
        System.out.println("Final LCS length: " + prev[n]);
    }
    
    private void visualizeReconstruction(String text1, String text2, int[][] dp) {
        System.out.println("\nLCS Reconstruction:");
        StringBuilder lcs = new StringBuilder();
        int i = text1.length(), j = text2.length();
        
        System.out.println("Backtracking from dp[" + i + "][" + j + "] = " + dp[i][j]);
        
        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                lcs.append(text1.charAt(i - 1));
                System.out.println("Match: '" + text1.charAt(i - 1) + "' at positions " + 
                                 (i-1) + " and " + (j-1) + " → Add to LCS");
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                System.out.println("Move up: dp[" + (i-1) + "][" + j + "] = " + 
                                 dp[i-1][j] + " > dp[" + i + "][" + (j-1) + "] = " + dp[i][j-1]);
                i--;
            } else {
                System.out.println("Move left: dp[" + i + "][" + (j-1) + "] = " + 
                                 dp[i][j-1] + " >= dp[" + (i-1) + "][" + j + "] = " + dp[i-1][j]);
                j--;
            }
        }
        
        String result = lcs.reverse().toString();
        System.out.println("Reconstructed LCS: \"" + result + "\"");
    }
    
    /**
     * Helper method to generate test cases
     */
    public String[] generateTestCase(String type) {
        switch (type) {
            case "simple":
                return new String[]{"abcde", "ace"};
            case "same":
                return new String[]{"abc", "abc"};
            case "no_common":
                return new String[]{"abc", "def"};
            case "empty1":
                return new String[]{"", "abc"};
            case "empty2":
                return new String[]{"abc", ""};
            case "long":
                return new String[]{"abcdefghij", "acEGi"};
            case "duplicates":
                return new String[]{"aabbcc", "abcabc"};
            default:
                return new String[]{"abc", "def"};
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Longest Common Subsequence:");
        System.out.println("===================================\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: Standard example \"abcde\" and \"ace\"");
        String text1_1 = "abcde";
        String text2_1 = "ace";
        int expected1 = 3;
        testImplementation(solution, text1_1, text2_1, expected1, "Standard DP");
        
        // Test case 2: Same strings
        System.out.println("\nTest 2: Same strings \"abc\" and \"abc\"");
        String text1_2 = "abc";
        String text2_2 = "abc";
        int expected2 = 3;
        testImplementation(solution, text1_2, text2_2, expected2, "Standard DP");
        
        // Test case 3: No common subsequence
        System.out.println("\nTest 3: No common subsequence \"abc\" and \"def\"");
        String text1_3 = "abc";
        String text2_3 = "def";
        int expected3 = 0;
        testImplementation(solution, text1_3, text2_3, expected3, "Standard DP");
        
        // Test case 4: One empty string
        System.out.println("\nTest 4: One empty string \"\" and \"abc\"");
        String text1_4 = "";
        String text2_4 = "abc";
        int expected4 = 0;
        testImplementation(solution, text1_4, text2_4, expected4, "Standard DP");
        
        // Test case 5: Both empty strings
        System.out.println("\nTest 5: Both empty strings \"\" and \"\"");
        String text1_5 = "";
        String text2_5 = "";
        int expected5 = 0;
        testImplementation(solution, text1_5, text2_5, expected5, "Standard DP");
        
        // Test case 6: With duplicates
        System.out.println("\nTest 6: With duplicates \"aabbcc\" and \"abcabc\"");
        String text1_6 = "aabbcc";
        String text2_6 = "abcabc";
        int expected6 = 4; // "abcc" or "abbc" etc.
        testImplementation(solution, text1_6, text2_6, expected6, "Standard DP");
        
        // Test case 7: Long strings
        System.out.println("\nTest 7: Long strings \"abcdefghij\" and \"acEGi\"");
        String text1_7 = "abcdefghij";
        String text2_7 = "acEGi";
        int expected7 = 3; // "aci"
        testImplementation(solution, text1_7, text2_7, expected7, "Standard DP");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: DYNAMIC PROGRAMMING");
        System.out.println("=".repeat(70));
        
        explainDPApproach(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, String text1, String text2, 
                                         int expected, String approach) {
        long startTime = System.nanoTime();
        int result = 0;
        switch (approach) {
            case "Standard DP":
                result = solution.longestCommonSubsequence(text1, text2);
                break;
            case "Space Optimized":
                result = solution.longestCommonSubsequenceSpaceOptimized(text1, text2);
                break;
            case "Single Array":
                result = solution.longestCommonSubsequenceSingleArray(text1, text2);
                break;
            case "Recursive":
                result = solution.longestCommonSubsequenceRecursive(text1, text2);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        boolean passed = (result == expected);
        System.out.printf("%s: Expected=%d, Got=%d, Time=%,d ns - %s%n",
                approach, expected, result, time, (passed ? "PASSED" : "FAILED"));
        
        if (!passed) {
            System.out.println("  Input: text1=\"" + text1 + "\", text2=\"" + text2 + "\"");
        }
        
        // Visualization for interesting cases
        if (passed && text1.length() <= 10 && text2.length() <= 10) {
            solution.visualizeDPTable(text1, text2, approach);
        }
    }
    
    private static void comparePerformance(Solution solution) {
        // Generate longer strings for performance testing
        String text1 = generateLongString(100, 1);
        String text2 = generateLongString(100, 2);
        
        System.out.println("Performance test with 100-character strings:");
        
        // Test Standard DP
        long startTime = System.nanoTime();
        solution.longestCommonSubsequence(text1, text2);
        long time1 = System.nanoTime() - startTime;
        
        // Test Space Optimized
        startTime = System.nanoTime();
        solution.longestCommonSubsequenceSpaceOptimized(text1, text2);
        long time2 = System.nanoTime() - startTime;
        
        // Test Single Array
        startTime = System.nanoTime();
        solution.longestCommonSubsequenceSingleArray(text1, text2);
        long time3 = System.nanoTime() - startTime;
        
        // Test Recursive (skip for long strings - stack overflow risk)
        long time4 = 0;
        if (text1.length() <= 50 && text2.length() <= 50) {
            startTime = System.nanoTime();
            solution.longestCommonSubsequenceRecursive(text1, text2);
            time4 = System.nanoTime() - startTime;
        }
        
        System.out.printf("Standard DP:       %,12d ns%n", time1);
        System.out.printf("Space Optimized:   %,12d ns%n", time2);
        System.out.printf("Single Array:      %,12d ns%n", time3);
        if (time4 > 0) {
            System.out.printf("Recursive:         %,12d ns%n", time4);
        } else {
            System.out.println("Recursive:         (skipped - risk of stack overflow)");
        }
    }
    
    private static String generateLongString(int length, int seed) {
        Random random = new Random(seed);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char)('a' + random.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }
    
    private static void explainDPApproach(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("The LCS problem has optimal substructure - the solution can be");
        System.out.println("constructed from solutions to smaller subproblems.");
        System.out.println();
        System.out.println("Let dp[i][j] = length of LCS of text1[0..i-1] and text2[0..j-1]");
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("1. If text1[i-1] == text2[j-1]:");
        System.out.println("   dp[i][j] = 1 + dp[i-1][j-1]");
        System.out.println("   (We extend the LCS by including the matching character)");
        System.out.println("2. Else:");
        System.out.println("   dp[i][j] = max(dp[i-1][j], dp[i][j-1])");
        System.out.println("   (We take the best LCS by skipping a character from either string)");
        
        System.out.println("\nBase Cases:");
        System.out.println("dp[0][j] = 0 for all j (empty text1)");
        System.out.println("dp[i][0] = 0 for all i (empty text2)");
        
        System.out.println("\nWhy it works:");
        System.out.println("- The recurrence considers all possibilities at each step");
        System.out.println("- When characters match, we extend the common subsequence");
        System.out.println("- When they don't match, we consider the best option from");
        System.out.println("  skipping a character from either string");
        System.out.println("- The DP table systematically builds up solutions to all subproblems");
        
        System.out.println("\nExample Walkthrough: \"abcde\" and \"ace\"");
        solution.visualizeDPTable("abcde", "ace", "Standard DP");
        
        System.out.println("\nTime Complexity: O(m×n)");
        System.out.println("  - We fill a table of size (m+1) × (n+1)");
        System.out.println("Space Complexity: O(m×n) for standard DP, O(min(m,n)) optimized");
    }
    
    private static void checkAllImplementations(Solution solution) {
        Object[][][] testCases = {
            {{"abcde", "ace"}, {3}},        // Standard
            {{"abc", "abc"}, {3}},          // Same
            {{"abc", "def"}, {0}},          // No common
            {{"", "abc"}, {0}},             // Empty1
            {{"abc", ""}, {0}},             // Empty2
            {{"aabbcc", "abcabc"}, {4}},    // Duplicates
            {{"abc", "acb"}, {2}}          // Different order
        };
        
        int[] expected = {3, 3, 0, 0, 0, 4, 2};
        
        String[] methods = {
            "Standard DP",
            "Space Optimized", 
            "Single Array",
            "Recursive",
            "With Reconstruction",
            "Early Termination"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            String text1 = (String) testCases[i][0][0];
            String text2 = (String) testCases[i][0][1];
            int expectedVal = ((int[]) testCases[i][1])[0];
            
            System.out.printf("\nTest case %d: \"%s\" and \"%s\" (expected: %d)%n",
                    i + 1, text1, text2, expectedVal);
            
            int[] results = new int[methods.length];
            results[0] = solution.longestCommonSubsequence(text1, text2);
            results[1] = solution.longestCommonSubsequenceSpaceOptimized(text1, text2);
            results[2] = solution.longestCommonSubsequenceSingleArray(text1, text2);
            results[3] = solution.longestCommonSubsequenceRecursive(text1, text2);
            results[4] = solution.longestCommonSubsequenceWithReconstruction(text1, text2);
            results[5] = solution.longestCommonSubsequenceEarlyTermination(text1, text2);
            
            boolean caseConsistent = true;
            for (int j = 0; j < methods.length; j++) {
                boolean correct = (results[j] == expectedVal);
                System.out.printf("  %-20s: %d %s%n", methods[j], results[j],
                        correct ? "✓" : "✗ (expected " + expectedVal + ")");
                if (!correct) {
                    caseConsistent = false;
                    allConsistent = false;
                }
            }
            
            if (!caseConsistent) {
                System.out.println("  INCONSISTENT RESULTS!");
            }
        }
        
        System.out.println("\nOverall consistency: " + (allConsistent ? "ALL PASSED ✓" : "SOME FAILED ✗"));
        
        // Algorithm comparison table
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON SUMMARY");
        System.out.println("=".repeat(70));
        
        printAlgorithmComparison();
    }
    
    private static void printAlgorithmComparison() {
        System.out.println("\n1. STANDARD DYNAMIC PROGRAMMING (RECOMMENDED):");
        System.out.println("   Time: O(m×n) - Optimal");
        System.out.println("   Space: O(m×n) - Full DP table");
        System.out.println("   Pros:");
        System.out.println("     - Clear and intuitive");
        System.out.println("     - Easy to understand and implement");
        System.out.println("     - Can be extended for reconstruction");
        System.out.println("   Cons:");
        System.out.println("     - Uses more space than optimized versions");
        System.out.println("   Use when: Learning, small inputs, need reconstruction");
        
        System.out.println("\n2. SPACE OPTIMIZED DP:");
        System.out.println("   Time: O(m×n) - Same as standard");
        System.out.println("   Space: O(min(m,n)) - Two rows only");
        System.out.println("   Pros:");
        System.out.println("     - Much better space complexity");
        System.out.println("     - Same time complexity as standard");
        System.out.println("   Cons:");
        System.out.println("     - Harder to reconstruct actual LCS");
        System.out.println("     - Slightly more complex implementation");
        System.out.println("   Use when: Large inputs, space-constrained environments");
        
        System.out.println("\n3. SINGLE ARRAY OPTIMIZATION:");
        System.out.println("   Time: O(m×n) - Same as standard");
        System.out.println("   Space: O(min(m,n)) - Single array");
        System.out.println("   Pros:");
        System.out.println("     - Most space-efficient DP solution");
        System.out.println("     - Good cache performance");
        System.out.println("   Cons:");
        System.out.println("     - Most complex to implement correctly");
        System.out.println("     - Cannot reconstruct LCS");
        System.out.println("   Use when: Maximum space efficiency needed");
        
        System.out.println("\n4. RECURSIVE WITH MEMOIZATION:");
        System.out.println("   Time: O(m×n) - Same as DP");
        System.out.println("   Space: O(m×n) - Memoization table + recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - Top-down approach is natural");
        System.out.println("     - Easy to understand recurrence");
        System.out.println("   Cons:");
        System.out.println("     - Risk of stack overflow for large inputs");
        System.out.println("     - More overhead than iterative DP");
        System.out.println("   Use when: Teaching recursive thinking, small inputs");
        
        System.out.println("\n5. WITH RECONSTRUCTION:");
        System.out.println("   Time: O(m×n) - Same as standard DP");
        System.out.println("   Space: O(m×n) - Additional direction array");
        System.out.println("   Pros:");
        System.out.println("     - Returns actual LCS string");
        System.out.println("     - Useful for debugging and visualization");
        System.out.println("   Cons:");
        System.out.println("     - Extra space for direction tracking");
        System.out.println("     - More complex implementation");
        System.out.println("   Use when: Need the actual subsequence, not just length");
        
        System.out.println("\n6. EARLY TERMINATION:");
        System.out.println("   Time: O(m×n) worst case, potentially faster");
        System.out.println("   Space: O(min(m,n)) - Optimized");
        System.out.println("   Pros:");
        System.out.println("     - Can terminate early in some cases");
        System.out.println("     - Space efficient");
        System.out.println("   Cons:");
        System.out.println("     - Early termination doesn't help in worst case");
        System.out.println("     - More complex logic");
        System.out.println("   Use when: Expecting short LCS relative to string lengths");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with standard DP solution - it's the most intuitive");
        System.out.println("2. Explain the recurrence relation clearly");
        System.out.println("3. Draw the DP table for a small example");
        System.out.println("4. Mention space optimization as an improvement");
        System.out.println("5. Discuss time/space complexity trade-offs");
        System.out.println("6. Handle edge cases: empty strings, no common subsequence");
        System.out.println("=".repeat(70));
    }
}
