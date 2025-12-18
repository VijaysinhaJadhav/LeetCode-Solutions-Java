
# Solution.java

```java
import java.util.*;

/**
 * 72. Edit Distance (Levenshtein Distance)
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given two strings word1 and word2, find minimum number of operations
 * (insert, delete, replace) to convert word1 to word2.
 * 
 * Key Insights:
 * 1. Classic dynamic programming problem
 * 2. dp[i][j] = min edits to convert word1[0..i-1] to word2[0..j-1]
 * 3. Base cases: converting to/from empty string
 * 4. Recurrence:
 *    - If chars match: dp[i][j] = dp[i-1][j-1]
 *    - Else: min(delete, insert, replace) + 1
 * 
 * Approach (DP with Space Optimization):
 * 1. Use dp array of size min(m,n)+1
 * 2. Initialize for empty string case
 * 3. Fill DP table bottom-up
 * 4. Return dp[n] (or dp[m] depending on orientation)
 * 
 * Time Complexity: O(m*n)
 * Space Complexity: O(min(m,n))
 * 
 * Tags: String, Dynamic Programming
 */

class Solution {
    
    /**
     * Approach 1: DP with Space Optimization (RECOMMENDED)
     * O(m*n) time, O(min(m,n)) space
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        // Use the shorter dimension for dp array to save space
        if (m < n) {
            return minDistance(word2, word1); // Ensure word1 is longer or equal
        }
        
        // dp[j] = min edits for current row
        int[] dp = new int[n + 1];
        
        // Initialize first row (converting empty string to word2[0..j-1])
        for (int j = 0; j <= n; j++) {
            dp[j] = j; // j inserts
        }
        
        // Fill DP table
        for (int i = 1; i <= m; i++) {
            int prev = dp[0]; // dp[i-1][0]
            dp[0] = i; // i deletes
            
            for (int j = 1; j <= n; j++) {
                int temp = dp[j]; // Store dp[i-1][j] for next iteration
                
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // Characters match, no edit needed
                    dp[j] = prev; // dp[i][j] = dp[i-1][j-1]
                } else {
                    // Take min of three operations and add 1
                    dp[j] = 1 + Math.min(Math.min(dp[j],      // delete from word1 (dp[i-1][j])
                                                   dp[j - 1]), // insert into word1 (dp[i][j-1])
                                                   prev);      // replace (dp[i-1][j-1])
                }
                
                prev = temp; // Update prev for next iteration
            }
        }
        
        return dp[n];
    }
    
    /**
     * Approach 2: Standard 2D DP
     * O(m*n) time, O(m*n) space
     * More intuitive but uses more space
     */
    public int minDistance2D(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        // dp[i][j] = min edits to convert word1[0..i-1] to word2[0..j-1]
        int[][] dp = new int[m + 1][n + 1];
        
        // Initialize base cases
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i; // Delete all characters
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j; // Insert all characters
        }
        
        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // Characters match, no edit needed
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Take min of three operations
                    dp[i][j] = 1 + Math.min(
                        dp[i - 1][j],      // Delete from word1
                        Math.min(
                            dp[i][j - 1],  // Insert into word1
                            dp[i - 1][j - 1] // Replace
                        )
                    );
                }
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * Approach 3: Recursive with Memoization
     * O(m*n) time, O(m*n) space
     * Top-down approach
     */
    public int minDistanceMemo(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] memo = new int[m + 1][n + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(word1, word2, m, n, memo);
    }
    
    private int dfs(String word1, String word2, int i, int j, int[][] memo) {
        if (i == 0) return j; // Insert all remaining characters
        if (j == 0) return i; // Delete all remaining characters
        
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        
        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
            memo[i][j] = dfs(word1, word2, i - 1, j - 1, memo);
        } else {
            int delete = dfs(word1, word2, i - 1, j, memo);
            int insert = dfs(word1, word2, i, j - 1, memo);
            int replace = dfs(word1, word2, i - 1, j - 1, memo);
            memo[i][j] = 1 + Math.min(delete, Math.min(insert, replace));
        }
        
        return memo[i][j];
    }
    
    /**
     * Approach 4: DP with Full Matrix and Visualization
     * O(m*n) time, O(m*n) space
     * Includes visualization for educational purposes
     */
    public int minDistanceVisual(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        int[][] dp = new int[m + 1][n + 1];
        
        // Initialize
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        
        // Fill DP table with visualization
        System.out.println("\nDP Table Construction:");
        printDPTable(word1, word2, dp, 0, 0, "Initial");
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                    printDPTable(word1, word2, dp, i, j, 
                               "Match: '" + word1.charAt(i-1) + "' = '" + word2.charAt(j-1) + "'");
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                    printDPTable(word1, word2, dp, i, j,
                               "No match: min(delete=" + dp[i-1][j] + 
                               ", insert=" + dp[i][j-1] + 
                               ", replace=" + dp[i-1][j-1] + ") + 1");
                }
            }
        }
        
        return dp[m][n];
    }
    
    private void printDPTable(String word1, String word2, int[][] dp, int i, int j, String operation) {
        int m = word1.length();
        int n = word2.length();
        
        System.out.println("\n" + operation);
        System.out.println("Processing: word1[" + (i-1) + "] = " + 
                          (i > 0 ? "'" + word1.charAt(i-1) + "'" : "N/A") + 
                          ", word2[" + (j-1) + "] = " + 
                          (j > 0 ? "'" + word2.charAt(j-1) + "'" : "N/A"));
        
        System.out.print("     ");
        for (int col = 0; col <= n; col++) {
            System.out.printf("%3s ", col > 0 ? word2.charAt(col-1) : "ε");
        }
        System.out.println("\n   +" + "-".repeat(4 * (n+1) + 1));
        
        for (int row = 0; row <= m; row++) {
            System.out.printf("%2s |", row > 0 ? word1.charAt(row-1) : "ε");
            for (int col = 0; col <= n; col++) {
                if (row == i && col == j) {
                    System.out.printf(" [%2d]", dp[row][col]);
                } else {
                    System.out.printf("  %2d ", dp[row][col]);
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Approach 5: Using Two Rows (Alternative Space Optimization)
     * O(m*n) time, O(n) space
     */
    public int minDistanceTwoRows(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];
        
        // Initialize first row
        for (int j = 0; j <= n; j++) {
            prev[j] = j;
        }
        
        // Fill DP table
        for (int i = 1; i <= m; i++) {
            curr[0] = i; // First column
            
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    curr[j] = 1 + Math.min(prev[j], Math.min(curr[j - 1], prev[j - 1]));
                }
            }
            
            // Swap rows for next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        
        return prev[n];
    }
    
    /**
     * Helper method to visualize edit operations
     */
    private void visualizeEditOperations(String word1, String word2) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDIT OPERATIONS VISUALIZATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nConverting \"" + word1 + "\" to \"" + word2 + "\"");
        
        // Get edit distance and reconstruct operations
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        // Initialize
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        
        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        
        // Backtrack to find operations
        List<String> operations = new ArrayList<>();
        int i = m, j = n;
        
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && word1.charAt(i - 1) == word2.charAt(j - 1)) {
                operations.add("Keep '" + word1.charAt(i-1) + "'");
                i--;
                j--;
            } else {
                if (i > 0 && j > 0 && dp[i][j] == dp[i-1][j-1] + 1) {
                    operations.add("Replace '" + word1.charAt(i-1) + "' with '" + word2.charAt(j-1) + "'");
                    i--;
                    j--;
                } else if (i > 0 && dp[i][j] == dp[i-1][j] + 1) {
                    operations.add("Delete '" + word1.charAt(i-1) + "'");
                    i--;
                } else if (j > 0 && dp[i][j] == dp[i][j-1] + 1) {
                    operations.add("Insert '" + word2.charAt(j-1) + "'");
                    j--;
                }
            }
        }
        
        Collections.reverse(operations);
        
        System.out.println("\nMinimum edit distance: " + dp[m][n]);
        System.out.println("\nOptimal sequence of operations:");
        
        String current = word1;
        System.out.println("Start: \"" + current + "\"");
        
        for (int idx = 0; idx < operations.size(); idx++) {
            String op = operations.get(idx);
            System.out.println((idx + 1) + ". " + op);
        }
        System.out.println("Final: \"" + word2 + "\"");
        
        // Show step-by-step transformation
        System.out.println("\nStep-by-step transformation:");
        StringBuilder currentWord = new StringBuilder(word1);
        System.out.println("0: \"" + currentWord + "\"");
        
        int step = 1;
        for (String op : operations) {
            if (op.startsWith("Delete")) {
                int pos = op.indexOf("'");
                char ch = op.charAt(pos + 1);
                int deletePos = currentWord.indexOf(String.valueOf(ch));
                if (deletePos != -1) {
                    currentWord.deleteCharAt(deletePos);
                }
            } else if (op.startsWith("Insert")) {
                int pos = op.lastIndexOf("'");
                char ch = op.charAt(pos - 1);
                int insertPos = step - 1; // Simplified
                if (insertPos <= currentWord.length()) {
                    currentWord.insert(insertPos, ch);
                }
            } else if (op.startsWith("Replace")) {
                int pos1 = op.indexOf("'");
                char oldCh = op.charAt(pos1 + 1);
                int pos2 = op.lastIndexOf("'");
                char newCh = op.charAt(pos2 - 1);
                int replacePos = currentWord.indexOf(String.valueOf(oldCh));
                if (replacePos != -1) {
                    currentWord.setCharAt(replacePos, newCh);
                }
            } else if (op.startsWith("Keep")) {
                // Do nothing
            }
            System.out.println(step + ": \"" + currentWord + "\" (" + op + ")");
            step++;
        }
    }
    
    /**
     * Helper to explain DP recurrence
     */
    private void explainDPRecurrence() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("DP RECURRENCE EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nLet dp[i][j] = minimum edits to convert");
        System.out.println("first i characters of word1 to first j characters of word2");
        
        System.out.println("\nBase Cases:");
        System.out.println("1. dp[0][j] = j (insert j characters)");
        System.out.println("2. dp[i][0] = i (delete i characters)");
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("For dp[i][j] where i > 0 and j > 0:");
        System.out.println("If word1[i-1] == word2[j-1]:");
        System.out.println("  dp[i][j] = dp[i-1][j-1] (characters match, no edit needed)");
        System.out.println("Else:");
        System.out.println("  dp[i][j] = 1 + min(");
        System.out.println("    dp[i-1][j],     // DELETE from word1");
        System.out.println("    dp[i][j-1],     // INSERT into word1");
        System.out.println("    dp[i-1][j-1]    // REPLACE character");
        System.out.println("  )");
        
        System.out.println("\nExample: word1 = \"horse\", word2 = \"ros\"");
        System.out.println("\nDP Table:");
        System.out.println("     ε  r  o  s");
        System.out.println("   +------------");
        System.out.println(" ε | 0  1  2  3  (insert)");
        System.out.println(" h | 1  1  2  3");
        System.out.println(" o | 2  2  1  2");
        System.out.println(" r | 3  2  2  2");
        System.out.println(" s | 4  3  3  2");
        System.out.println(" e | 5  4  4  3 ← answer");
        
        System.out.println("\nWhy this works:");
        System.out.println("1. DELETE: Convert word1[0..i-2] to word2[0..j-1], then delete word1[i-1]");
        System.out.println("2. INSERT: Convert word1[0..i-1] to word2[0..j-2], then insert word2[j-1]");
        System.out.println("3. REPLACE: Convert word1[0..i-2] to word2[0..j-2], then replace word1[i-1] with word2[j-1]");
    }
    
    /**
     * Helper to compare different approaches
     */
    private void compareApproaches(String word1, String word2) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput:");
        System.out.println("word1 = \"" + word1 + "\" (length = " + word1.length() + ")");
        System.out.println("word2 = \"" + word2 + "\" (length = " + word2.length() + ")");
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5;
        
        // Approach 1: Space Optimized DP
        startTime = System.nanoTime();
        result1 = solution.minDistance(word1, word2);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: 2D DP
        startTime = System.nanoTime();
        result2 = solution.minDistance2D(word1, word2);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Memoization
        startTime = System.nanoTime();
        result3 = solution.minDistanceMemo(word1, word2);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Two Rows
        startTime = System.nanoTime();
        result4 = solution.minDistanceTwoRows(word1, word2);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (Space Optimized): " + result1);
        System.out.println("Approach 2 (2D DP):           " + result2);
        System.out.println("Approach 3 (Memoization):     " + result3);
        System.out.println("Approach 4 (Two Rows):        " + result4);
        
        boolean allEqual = (result1 == result2) && (result2 == result3) && (result3 == result4);
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Approach 1: %-10d (Space Optimized DP - O(min(m,n)) space)%n", time1);
        System.out.printf("Approach 2: %-10d (2D DP - O(m*n) space)%n", time2);
        System.out.printf("Approach 3: %-10d (Memoization - O(m*n) space)%n", time3);
        System.out.printf("Approach 4: %-10d (Two Rows - O(n) space)%n", time4);
        
        // Show operations if strings are not too long
        if (word1.length() <= 10 && word2.length() <= 10) {
            solution.visualizeEditOperations(word1, word2);
        }
    }
    
    /**
     * Helper to analyze time and space complexity
     */
    private void analyzeComplexity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Time Complexity:");
        System.out.println("   All DP approaches: O(m*n)");
        System.out.println("   - m = length of word1, n = length of word2");
        System.out.println("   - We fill a table of size (m+1)*(n+1)");
        System.out.println("   - Each cell computation is O(1)");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   a. 2D DP: O(m*n) - full table");
        System.out.println("   b. Two Rows: O(n) - keep only previous and current row");
        System.out.println("   c. Space Optimized: O(min(m,n)) - single array with careful updating");
        System.out.println("   d. Memoization: O(m*n) - recursion stack + memo table");
        
        System.out.println("\n3. Why DP is optimal for this problem:");
        System.out.println("   - Optimal substructure: optimal solution to subproblems");
        System.out.println("   - Overlapping subproblems: same subproblems computed multiple times");
        System.out.println("   - Edit distance satisfies Bellman's principle of optimality");
        
        System.out.println("\n4. Constraints Analysis:");
        System.out.println("   Maximum m,n = 500");
        System.out.println("   - O(m*n) = 500*500 = 250,000 operations (very fast)");
        System.out.println("   - O(m*n) space = 250,000 integers ≈ 1MB (acceptable)");
        System.out.println("   - Space optimized: O(min(m,n)) = 500 integers ≈ 2KB (excellent)");
    }
    
    /**
     * Helper to show real-world applications
     */
    private void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Spell Checking and Correction:");
        System.out.println("   - Find closest dictionary word to misspelled word");
        System.out.println("   - Used in word processors, search engines");
        
        System.out.println("\n2. DNA Sequence Alignment:");
        System.out.println("   - Measure similarity between genetic sequences");
        System.out.println("   - Bioinformatics and genomics research");
        
        System.out.println("\n3. Natural Language Processing:");
        System.out.println("   - Machine translation evaluation (BLEU score)");
        System.out.println("   - Text similarity measurement");
        
        System.out.println("\n4. Plagiarism Detection:");
        System.out.println("   - Measure similarity between documents");
        System.out.println("   - Identify copied content");
        
        System.out.println("\n5. Command Line Interface:");
        System.out.println("   - 'Did you mean?' suggestions");
        System.out.println("   - Used in git, npm, etc.");
        
        System.out.println("\n6. File Comparison:");
        System.out.println("   - diff utility for comparing files");
        System.out.println("   - Version control systems");
        
        System.out.println("\n7. Speech Recognition:");
        System.out.println("   - Align spoken words with text");
        System.out.println("   - Error correction in transcripts");
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Edit Distance (Levenshtein Distance):");
        System.out.println("==============================================");
        
        // Show DP recurrence explanation
        solution.explainDPRecurrence();
        
        // Show complexity analysis
        solution.analyzeComplexity();
        
        // Test case 1: Example from problem
        System.out.println("\n\nTest 1: Basic example");
        String word1_1 = "horse";
        String word2_1 = "ros";
        int expected1 = 3;
        
        System.out.println("\nInput: word1 = \"" + word1_1 + "\", word2 = \"" + word2_1 + "\"");
        
        int result1 = solution.minDistance(word1_1, word2_1);
        System.out.println("Expected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        // Visualize for this example
        solution.visualizeEditOperations(word1_1, word2_1);
        
        // Test case 2: Another example
        System.out.println("\n\nTest 2: Longer example");
        String word1_2 = "intention";
        String word2_2 = "execution";
        int expected2 = 5;
        
        System.out.println("\nInput: word1 = \"" + word1_2 + "\", word2 = \"" + word2_2 + "\"");
        
        int result2 = solution.minDistance(word1_2, word2_2);
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        // Test case 3: Same strings
        System.out.println("\n\nTest 3: Same strings");
        String word1_3 = "algorithm";
        String word2_3 = "algorithm";
        int expected3 = 0;
        
        System.out.println("\nInput: word1 = \"" + word1_3 + "\", word2 = \"" + word2_3 + "\"");
        
        int result3 = solution.minDistance(word1_3, word2_3);
        System.out.println("Expected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + (result3 == expected3 ? "✓" : "✗"));
        
        // Test case 4: One empty string
        System.out.println("\n\nTest 4: One empty string");
        String word1_4 = "";
        String word2_4 = "hello";
        int expected4 = 5; // Insert 5 characters
        
        System.out.println("\nInput: word1 = \"" + word1_4 + "\", word2 = \"" + word2_4 + "\"");
        
        int result4 = solution.minDistance(word1_4, word2_4);
        System.out.println("Expected: " + expected4);
        System.out.println("Result:   " + result4);
        System.out.println("Passed: " + (result4 == expected4 ? "✓" : "✗"));
        
        // Test case 5: Both empty strings
        System.out.println("\n\nTest 5: Both empty strings");
        String word1_5 = "";
        String word2_5 = "";
        int expected5 = 0;
        
        System.out.println("\nInput: word1 = \"" + word1_5 + "\", word2 = \"" + word2_5 + "\"");
        
        int result5 = solution.minDistance(word1_5, word2_5);
        System.out.println("Expected: " + expected5);
        System.out.println("Result:   " + result5);
        System.out.println("Passed: " + (result5 == expected5 ? "✓" : "✗"));
        
        // Test case 6: Completely different strings
        System.out.println("\n\nTest 6: Completely different");
        String word1_6 = "abc";
        String word2_6 = "xyz";
        int expected6 = 3; // Replace all characters
        
        System.out.println("\nInput: word1 = \"" + word1_6 + "\", word2 = \"" + word2_6 + "\"");
        
        int result6 = solution.minDistance(word1_6, word2_6);
        System.out.println("Expected: " + expected6);
        System.out.println("Result:   " + result6);
        System.out.println("Passed: " + (result6 == expected6 ? "✓" : "✗"));
        
        // Compare all approaches for various test cases
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES:");
        System.out.println("=".repeat(80));
        
        String[][] testCases = {
            {word1_1, word2_1},
            {word1_2, word2_2},
            {word1_3, word2_3},
            {word1_4, word2_4},
            {word1_5, word2_5},
            {word1_6, word2_6},
            {"kitten", "sitting"}, // Classic example
            {"flaw", "lawn"},
            {"sunday", "saturday"},
            {"food", "money"},
            {"exponential", "polynomial"}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\nTest Case " + (i+1) + ":");
            System.out.println("word1 = \"" + testCases[i][0] + "\"");
            System.out.println("word2 = \"" + testCases[i][1] + "\"");
            
            solution.compareApproaches(testCases[i][0], testCases[i][1]);
            
            if (i < testCases.length - 1) {
                System.out.println("\n" + "-".repeat(80));
            }
        }
        
        // Performance test with larger strings
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST WITH LARGER STRINGS:");
        System.out.println("=".repeat(80));
        
        // Generate test strings near maximum constraints
        Random random = new Random(42);
        int length1 = 400;
        int length2 = 350;
        
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        
        for (int i = 0; i < length1; i++) {
            sb1.append((char)('a' + random.nextInt(26)));
        }
        for (int i = 0; i < length2; i++) {
            sb2.append((char)('a' + random.nextInt(26)));
        }
        
        String largeWord1 = sb1.toString();
        String largeWord2 = sb2.toString();
        
        System.out.println("\nTesting with:");
        System.out.println("word1 length = " + largeWord1.length());
        System.out.println("word2 length = " + largeWord2.length());
        
        long startTime, endTime;
        
        // Approach 1: Space Optimized DP
        startTime = System.currentTimeMillis();
        int perf1 = solution.minDistance(largeWord1, largeWord2);
        endTime = System.currentTimeMillis();
        long time1 = endTime - startTime;
        
        // Approach 2: 2D DP (might be slower due to memory allocation)
        startTime = System.currentTimeMillis();
        int perf2 = solution.minDistance2D(largeWord1, largeWord2);
        endTime = System.currentTimeMillis();
        long time2 = endTime - startTime;
        
        // Approach 4: Two Rows
        startTime = System.currentTimeMillis();
        int perf4 = solution.minDistanceTwoRows(largeWord1, largeWord2);
        endTime = System.currentTimeMillis();
        long time4 = endTime - startTime;
        
        System.out.println("\nPerformance (milliseconds):");
        System.out.printf("Approach 1 (Space Optimized): %5d ms - Result: %d%n", time1, perf1);
        System.out.printf("Approach 2 (2D DP):           %5d ms - Result: %d%n", time2, perf2);
        System.out.printf("Approach 4 (Two Rows):        %5d ms - Result: %d%n", time4, perf4);
        
        // Verify all give same result
        boolean perfConsistent = (perf1 == perf2) && (perf2 == perf4);
        System.out.println("\nResults consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Show real-world applications
        solution.showApplications();
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - Three operations: insert, delete, replace");
        System.out.println("   - All operations have equal cost (1)");
        System.out.println("   - Need minimum operations to transform word1 to word2");
        
        System.out.println("\n2. Recognize as DP problem:");
        System.out.println("   - Classic edit distance / Levenshtein distance");
        System.out.println("   - Strings + minimization → likely DP");
        
        System.out.println("\n3. Define DP state:");
        System.out.println("   - dp[i][j] = min edits for word1[0..i-1] to word2[0..j-1]");
        System.out.println("   - i, j represent lengths of prefixes");
        
        System.out.println("\n4. Identify base cases:");
        System.out.println("   - dp[0][j] = j (insert j characters)");
        System.out.println("   - dp[i][0] = i (delete i characters)");
        
        System.out.println("\n5. Derive recurrence:");
        System.out.println("   - If last chars match: dp[i][j] = dp[i-1][j-1]");
        System.out.println("   - Else: dp[i][j] = 1 + min(delete, insert, replace)");
        
        System.out.println("\n6. Implement:");
        System.out.println("   - Create DP table (m+1)*(n+1)");
        System.out.println("   - Fill base cases");
        System.out.println("   - Fill table using recurrence");
        System.out.println("   - Return dp[m][n]");
        
        System.out.println("\n7. Optimize space:");
        System.out.println("   - Use 2 rows or single array approach");
        System.out.println("   - O(min(m,n)) space instead of O(m*n)");
        
        System.out.println("\n8. Walk through example:");
        System.out.println("   - Use \"horse\" -> \"ros\" example");
        System.out.println("   - Show DP table construction");
        
        System.out.println("\n9. Discuss complexity:");
        System.out.println("   - Time: O(m*n)");
        System.out.println("   - Space: O(min(m,n)) optimized");
        
        System.out.println("\n10. Handle edge cases:");
        System.out.println("    - Empty strings");
        System.out.println("    - Same strings");
        System.out.println("    - Very different strings");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- DP state definition is crucial");
        System.out.println("- Recurrence covers all three operations");
        System.out.println("- Space optimization shows deep understanding");
        System.out.println("- Can explain real-world applications");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Off-by-one errors in indices");
        System.out.println("- Not handling empty string cases");
        System.out.println("- Using wrong operation costs (all cost 1)");
        System.out.println("- Not being able to explain recurrence logic");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 583. Delete Operation for Two Strings");
        System.out.println("2. 712. Minimum ASCII Delete Sum for Two Strings");
        System.out.println("3. 10. Regular Expression Matching");
        System.out.println("4. 44. Wildcard Matching");
        System.out.println("5. 97. Interleaving String");
        System.out.println("6. 115. Distinct Subsequences");
        System.out.println("7. 1143. Longest Common Subsequence");
        System.out.println("8. 1092. Shortest Common Supersequence");
        System.out.println("9. 161. One Edit Distance");
        System.out.println("10. 392. Is Subsequence");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
