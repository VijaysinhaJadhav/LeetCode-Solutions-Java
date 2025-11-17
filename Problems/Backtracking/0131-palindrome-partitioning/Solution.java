
## Solution.java

```java
/**
 * 131. Palindrome Partitioning
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a string s, partition s such that every substring of the partition is a palindrome. 
 * Return all possible palindrome partitioning of s.
 * 
 * Key Insights:
 * 1. Use backtracking to explore all possible partitions
 * 2. Precompute palindrome information for O(1) checks
 * 3. At each step, try all possible end positions for current partition
 * 4. Only extend partitions that are palindromes
 * 
 * Approach (Backtracking with DP Precomputation):
 * 1. Precompute palindrome table using dynamic programming
 * 2. Use DFS/backtracking to build valid partitions
 * 3. Add current substring to path if it's a palindrome
 * 4. Continue with remaining string
 * 5. Add to result when entire string is partitioned
 * 
 * Time Complexity: O(n × 2^n)
 * Space Complexity: O(n²) for DP table
 * 
 * Tags: String, Dynamic Programming, Backtracking, Depth-First Search
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Backtracking with Palindrome Precomputation - RECOMMENDED
     * O(n × 2^n) time, O(n²) space
     */
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return result;
        }
        
        int n = s.length();
        // Precompute palindrome information
        boolean[][] isPalindrome = precomputePalindromes(s);
        
        backtrack(s, 0, new ArrayList<>(), result, isPalindrome);
        return result;
    }
    
    private void backtrack(String s, int start, List<String> current, 
                         List<List<String>> result, boolean[][] isPalindrome) {
        // Base case: entire string is partitioned
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Try all possible end positions for current partition
        for (int end = start; end < s.length(); end++) {
            // Only proceed if substring s[start..end] is palindrome
            if (isPalindrome[start][end]) {
                // Add current palindrome to path
                current.add(s.substring(start, end + 1));
                // Recurse on remaining string
                backtrack(s, end + 1, current, result, isPalindrome);
                // Backtrack
                current.remove(current.size() - 1);
            }
        }
    }
    
    /**
     * Precompute palindrome information using dynamic programming
     * isPalindrome[i][j] = true if s[i..j] is palindrome
     */
    private boolean[][] precomputePalindromes(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        
        // All single characters are palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        // Check for 2-character palindromes
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }
        
        // Check for longer palindromes
        for (int length = 3; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1];
            }
        }
        
        return dp;
    }
    
    /**
     * Approach 2: Backtracking with In-place Palindrome Check
     * O(n × 2^n) time, O(n) space (no DP table)
     */
    public List<List<String>> partitionInPlace(String s) {
        List<List<String>> result = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return result;
        }
        
        backtrackInPlace(s, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrackInPlace(String s, int start, List<String> current, 
                                List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end)) {
                current.add(s.substring(start, end + 1));
                backtrackInPlace(s, end + 1, current, result);
                current.remove(current.size() - 1);
            }
        }
    }
    
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    /**
     * Approach 3: Dynamic Programming with Memoization
     * O(n × 2^n) time, O(n²) space
     */
    public List<List<String>> partitionDP(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }
        
        int n = s.length();
        boolean[][] isPalindrome = precomputePalindromes(s);
        
        // memo[i] stores all partitions for s[i..n-1]
        List<List<String>>[] memo = new ArrayList[n + 1];
        memo[n] = new ArrayList<>();
        memo[n].add(new ArrayList<>()); // base case: empty partition
        
        return dfsWithMemo(s, 0, isPalindrome, memo);
    }
    
    private List<List<String>> dfsWithMemo(String s, int start, 
                                         boolean[][] isPalindrome, 
                                         List<List<String>>[] memo) {
        if (memo[start] != null) {
            return memo[start];
        }
        
        List<List<String>> result = new ArrayList<>();
        
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome[start][end]) {
                String current = s.substring(start, end + 1);
                List<List<String>> restPartitions = dfsWithMemo(s, end + 1, isPalindrome, memo);
                
                for (List<String> partition : restPartitions) {
                    List<String> newPartition = new ArrayList<>();
                    newPartition.add(current);
                    newPartition.addAll(partition);
                    result.add(newPartition);
                }
            }
        }
        
        memo[start] = result;
        return result;
    }
    
    /**
     * Approach 4: Iterative Dynamic Programming (Bottom-up)
     * O(n × 2^n) time, O(n²) space
     */
    public List<List<String>> partitionIterative(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }
        
        int n = s.length();
        boolean[][] isPalindrome = precomputePalindromes(s);
        
        // dp[i] stores all partitions for s[0..i-1]
        List<List<String>>[] dp = new ArrayList[n + 1];
        dp[0] = new ArrayList<>();
        dp[0].add(new ArrayList<>()); // base case
        
        for (int i = 1; i <= n; i++) {
            dp[i] = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                if (isPalindrome[j][i - 1]) {
                    String current = s.substring(j, i);
                    for (List<String> partition : dp[j]) {
                        List<String> newPartition = new ArrayList<>(partition);
                        newPartition.add(current);
                        dp[i].add(newPartition);
                    }
                }
            }
        }
        
        return dp[n];
    }
    
    /**
     * Approach 5: BFS (Level Order Generation)
     * O(n × 2^n) time, O(n × 2^n) space
     */
    public List<List<String>> partitionBFS(String s) {
        List<List<String>> result = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return result;
        }
        
        int n = s.length();
        boolean[][] isPalindrome = precomputePalindromes(s);
        
        Queue<PartitionNode> queue = new LinkedList<>();
        queue.offer(new PartitionNode(0, new ArrayList<>()));
        
        while (!queue.isEmpty()) {
            PartitionNode node = queue.poll();
            int start = node.startIndex;
            List<String> currentPartition = node.partition;
            
            if (start == n) {
                result.add(new ArrayList<>(currentPartition));
                continue;
            }
            
            for (int end = start; end < n; end++) {
                if (isPalindrome[start][end]) {
                    List<String> newPartition = new ArrayList<>(currentPartition);
                    newPartition.add(s.substring(start, end + 1));
                    queue.offer(new PartitionNode(end + 1, newPartition));
                }
            }
        }
        
        return result;
    }
    
    private static class PartitionNode {
        int startIndex;
        List<String> partition;
        
        PartitionNode(int startIndex, List<String> partition) {
            this.startIndex = startIndex;
            this.partition = partition;
        }
    }
    
    /**
     * Approach 6: Optimized with Early Pruning and Cache
     * O(n × 2^n) time, O(n²) space
     */
    public List<List<String>> partitionOptimized(String s) {
        List<List<String>> result = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return result;
        }
        
        int n = s.length();
        Boolean[][] palindromeCache = new Boolean[n][n];
        
        backtrackOptimized(s, 0, new ArrayList<>(), result, palindromeCache);
        return result;
    }
    
    private void backtrackOptimized(String s, int start, List<String> current,
                                  List<List<String>> result, Boolean[][] cache) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Try longer substrings first (heuristic for better pruning)
        for (int end = s.length() - 1; end >= start; end--) {
            if (isPalindromeCached(s, start, end, cache)) {
                current.add(s.substring(start, end + 1));
                backtrackOptimized(s, end + 1, current, result, cache);
                current.remove(current.size() - 1);
            }
        }
    }
    
    private boolean isPalindromeCached(String s, int left, int right, Boolean[][] cache) {
        if (cache[left][right] != null) {
            return cache[left][right];
        }
        
        if (left >= right) {
            cache[left][right] = true;
            return true;
        }
        
        if (s.charAt(left) != s.charAt(right)) {
            cache[left][right] = false;
            return false;
        }
        
        boolean result = isPalindromeCached(s, left + 1, right - 1, cache);
        cache[left][right] = result;
        return result;
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    public void visualizePartitioning(String s) {
        System.out.println("\nPalindrome Partitioning Visualization:");
        System.out.println("String: " + s);
        System.out.println("Backtracking Process:");
        
        boolean[][] isPalindrome = precomputePalindromes(s);
        List<String> current = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        
        System.out.println("Precomputed Palindrome Table:");
        printPalindromeTable(s, isPalindrome);
        
        System.out.println("\nBacktracking Steps:");
        System.out.println("Depth | Current Partition | Remaining String | Action");
        System.out.println("------|-------------------|------------------|--------");
        
        visualizeBacktrack(s, 0, current, result, isPalindrome, 0);
        
        System.out.println("\nFinal Result: " + result);
    }
    
    private void visualizeBacktrack(String s, int start, List<String> current,
                                  List<List<String>> result, boolean[][] isPalindrome, int depth) {
        if (start == s.length()) {
            System.out.printf("%5d | %17s | %16s | COMPLETE: %s%n", 
                            depth, current, "", current);
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome[start][end]) {
                String substring = s.substring(start, end + 1);
                String remaining = (end + 1 < s.length()) ? s.substring(end + 1) : "";
                
                System.out.printf("%5d | %17s | %16s | ADD: '%s'%n", 
                                depth, current, remaining, substring);
                
                current.add(substring);
                visualizeBacktrack(s, end + 1, current, result, isPalindrome, depth + 1);
                current.remove(current.size() - 1);
                
                System.out.printf("%5d | %17s | %16s | BACKTRACK: remove '%s'%n", 
                                depth, current, remaining, substring);
            } else {
                String substring = s.substring(start, end + 1);
                System.out.printf("%5d | %17s | %16s | SKIP: '%s' (not palindrome)%n", 
                                depth, current, s.substring(end + 1), substring);
            }
        }
    }
    
    private void printPalindromeTable(String s, boolean[][] isPalindrome) {
        int n = s.length();
        System.out.print("    ");
        for (int i = 0; i < n; i++) {
            System.out.print(s.charAt(i) + " ");
        }
        System.out.println();
        
        for (int i = 0; i < n; i++) {
            System.out.print(s.charAt(i) + " ");
            for (int j = 0; j < n; j++) {
                if (j < i) {
                    System.out.print("  ");
                } else {
                    System.out.print(isPalindrome[i][j] ? "T " : "F ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Palindrome Partitioning Solution:");
        System.out.println("=========================================");
        
        // Test case 1: Standard example "aab"
        System.out.println("\nTest 1: String 'aab'");
        String s1 = "aab";
        List<List<String>> expected1 = Arrays.asList(
            Arrays.asList("a", "a", "b"),
            Arrays.asList("aa", "b")
        );
        
        long startTime = System.nanoTime();
        List<List<String>> result1a = solution.partition(s1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1b = solution.partitionInPlace(s1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1c = solution.partitionDP(s1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1d = solution.partitionIterative(s1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1e = solution.partitionBFS(s1);
        long time1e = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1f = solution.partitionOptimized(s1);
        long time1f = System.nanoTime() - startTime;
        
        // Sort results for comparison
        sortResult(result1a);
        sortResult(result1b);
        sortResult(result1c);
        sortResult(result1d);
        sortResult(result1e);
        sortResult(result1f);
        sortResult(expected1);
        
        boolean test1a = result1a.equals(expected1);
        boolean test1b = result1b.equals(expected1);
        boolean test1c = result1c.equals(expected1);
        boolean test1d = result1d.equals(expected1);
        boolean test1e = result1e.equals(expected1);
        boolean test1f = result1f.equals(expected1);
        
        System.out.println("Backtracking+DP: " + result1a.size() + " partitions - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("In-Place Check: " + result1b.size() + " partitions - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("DP with Memo: " + result1c.size() + " partitions - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Iterative DP: " + result1d.size() + " partitions - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + result1e.size() + " partitions - " + (test1e ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1f.size() + " partitions - " + (test1f ? "PASSED" : "FAILED"));
        
        // Visualize the partitioning process
        solution.visualizePartitioning(s1);
        
        // Test case 2: Single character
        System.out.println("\nTest 2: Single character 'a'");
        String s2 = "a";
        List<List<String>> result2a = solution.partition(s2);
        System.out.println("Single char: " + result2a + " - " + 
                         (result2a.size() == 1 ? "PASSED" : "FAILED"));
        
        // Test case 3: All same characters
        System.out.println("\nTest 3: All same characters 'aaa'");
        String s3 = "aaa";
        List<List<String>> result3a = solution.partition(s3);
        System.out.println("All same: " + result3a.size() + " partitions - " + 
                         (result3a.size() == 4 ? "PASSED" : "FAILED"));
        
        // Test case 4: No palindromes except single characters
        System.out.println("\nTest 4: 'abc'");
        String s4 = "abc";
        List<List<String>> result4a = solution.partition(s4);
        System.out.println("No multi-char palindromes: " + result4a.size() + " partitions - " + 
                         (result4a.size() == 1 ? "PASSED" : "FAILED"));
        
        // Test case 5: Empty string
        System.out.println("\nTest 5: Empty string");
        String s5 = "";
        List<List<String>> result5a = solution.partition(s5);
        System.out.println("Empty string: " + result5a + " - " + 
                         (result5a.isEmpty() ? "PASSED" : "FAILED"));
        
        // Test case 6: Longer string
        System.out.println("\nTest 6: 'aabb'");
        String s6 = "aabb";
        List<List<String>> result6a = solution.partition(s6);
        System.out.println("'aabb': " + result6a.size() + " partitions - " + 
                         (result6a.size() == 4 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison (aab)");
        System.out.println("Backtracking+DP: " + time1a + " ns");
        System.out.println("In-Place Check: " + time1b + " ns");
        System.out.println("DP with Memo: " + time1c + " ns");
        System.out.println("Iterative DP: " + time1d + " ns");
        System.out.println("BFS: " + time1e + " ns");
        System.out.println("Optimized: " + time1f + " ns");
        
        // Test all approaches produce same results
        System.out.println("\nTest 8: All approaches consistency");
        boolean allConsistent = result1a.equals(result1b) && 
                              result1a.equals(result1c) && 
                              result1a.equals(result1d) &&
                              result1a.equals(result1e) &&
                              result1a.equals(result1f);
        System.out.println("All approaches produce same results: " + allConsistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BACKTRACKING WITH DP PRECOMPUTATION EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Precompute all palindrome substrings using dynamic programming");
        System.out.println("to enable O(1) palindrome checks during backtracking.");
        
        System.out.println("\nPalindrome Precomputation (DP):");
        System.out.println("isPalindrome[i][j] = true if s[i..j] is palindrome");
        System.out.println("Base cases:");
        System.out.println("  - Single char: isPalindrome[i][i] = true");
        System.out.println("  - Two chars: isPalindrome[i][i+1] = (s[i] == s[i+1])");
        System.out.println("  - Longer: isPalindrome[i][j] = (s[i] == s[j]) && isPalindrome[i+1][j-1]");
        
        System.out.println("\nBacktracking Process:");
        System.out.println("1. Start from index 0");
        System.out.println("2. For each end position, check if s[start..end] is palindrome");
        System.out.println("3. If yes, add to current partition and recurse on remaining string");
        System.out.println("4. Backtrack by removing last added substring");
        System.out.println("5. Add to result when entire string is partitioned");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking with DP Precomputation (RECOMMENDED):");
        System.out.println("   Time: O(n × 2^n) - 2^n possible partitions, n for operations");
        System.out.println("   Space: O(n²) - DP table + O(n) recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Precompute palindrome table with DP");
        System.out.println("     - Backtrack with O(1) palindrome checks");
        System.out.println("     - Build partitions recursively");
        System.out.println("   Pros:");
        System.out.println("     - Fast palindrome checks");
        System.out.println("     - Clear and intuitive");
        System.out.println("     - Optimal for this problem");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n²) extra space");
        System.out.println("   Best for: Interview settings, most use cases");
        
        System.out.println("\n2. Backtracking with In-place Check:");
        System.out.println("   Time: O(n × 2^n) - Same but with O(n) palindrome checks");
        System.out.println("   Space: O(n) - Only recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Check palindromes on the fly with two pointers");
        System.out.println("     - Same backtracking process");
        System.out.println("   Pros:");
        System.out.println("     - O(1) extra space (no DP table)");
        System.out.println("     - Simple implementation");
        System.out.println("   Cons:");
        System.out.println("     - Slower due to repeated palindrome checks");
        System.out.println("   Best for: When memory is critical");
        
        System.out.println("\n3. Dynamic Programming with Memoization:");
        System.out.println("   Time: O(n × 2^n) - Same complexity");
        System.out.println("   Space: O(n²) - DP table + memoization storage");
        System.out.println("   How it works:");
        System.out.println("     - Memoize partitions for each starting index");
        System.out.println("     - Build solutions bottom-up recursively");
        System.out.println("   Pros:");
        System.out.println("     - Avoids recomputation of partitions");
        System.out.println("     - Elegant recursive structure");
        System.out.println("   Cons:");
        System.out.println("     - Higher memory usage");
        System.out.println("     - More complex");
        System.out.println("   Best for: Learning DP concepts");
        
        System.out.println("\n4. Iterative Dynamic Programming:");
        System.out.println("   Time: O(n × 2^n) - Same complexity");
        System.out.println("   Space: O(n²) - DP table + result storage");
        System.out.println("   How it works:");
        System.out.println("     - Build solutions iteratively from left to right");
        System.out.println("     - dp[i] = all partitions for first i characters");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack");
        System.out.println("     - Bottom-up approach");
        System.out.println("   Cons:");
        System.out.println("     - Harder to understand");
        System.out.println("     - Higher memory usage");
        System.out.println("   Best for: When iterative solution preferred");
        
        System.out.println("\n5. BFS (Level Order):");
        System.out.println("   Time: O(n × 2^n) - Same complexity");
        System.out.println("   Space: O(n × 2^n) - Queue storage");
        System.out.println("   How it works:");
        System.out.println("     - Use queue to process partition states");
        System.out.println("     - Level by level exploration");
        System.out.println("   Pros:");
        System.out.println("     - Avoids recursion");
        System.out.println("     - Level-wise generation");
        System.out.println("   Cons:");
        System.out.println("     - High memory usage");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: When BFS traversal is required");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS DETAILS:");
        System.out.println("1. Worst-case: String with all same characters");
        System.out.println("   - Number of partitions: 2^(n-1)");
        System.out.println("   - Example: 'aaa' has 4 partitions = 2^(3-1)");
        System.out.println("2. Time Complexity: O(n × 2^n)");
        System.out.println("   - 2^n possible partition points");
        System.out.println("   - O(n) operations per partition (substring, etc.)");
        System.out.println("3. Space Complexity: O(n²)");
        System.out.println("   - O(n²) for DP table");
        System.out.println("   - O(n) for recursion stack");
        System.out.println("   - O(n × 2^n) for output storage");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("OPTIMIZATION STRATEGIES:");
        System.out.println("1. Palindrome Precomputation: O(1) checks vs O(n) checks");
        System.out.println("2. Early Pruning: Skip invalid partitions immediately");
        System.out.println("3. Memoization: Cache computed partitions");
        System.out.println("4. Iterative DP: Avoid recursion overhead");
        System.out.println("5. BFS: When specific traversal order needed");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with backtracking + DP precomputation");
        System.out.println("2. Explain the palindrome DP table clearly");
        System.out.println("3. Discuss time/space complexity honestly");
        System.out.println("4. Mention alternative approaches and trade-offs");
        System.out.println("5. Handle edge cases: single char, empty string");
        System.out.println("6. Write clean, readable code with comments");
        System.out.println("=".repeat(70));
        
        // Additional test: Display partitions for different strings
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SAMPLE PARTITIONS FOR DIFFERENT STRINGS:");
        System.out.println("=".repeat(70));
        
        String[] testStrings = {"a", "aa", "ab", "aaa", "aab", "aba"};
        for (String testStr : testStrings) {
            List<List<String>> partitions = solution.partition(testStr);
            System.out.printf("\n'%s': %d partitions%n", testStr, partitions.size());
            if (partitions.size() <= 6) {
                for (List<String> partition : partitions) {
                    System.out.println("  " + partition);
                }
            }
        }
        
        System.out.println("\nAll tests completed!");
    }
    
    // Helper method to sort result for comparison
    private static void sortResult(List<List<String>> result) {
        for (List<String> list : result) {
            Collections.sort(list);
        }
        result.sort((a, b) -> {
            String s1 = String.join("", a);
            String s2 = String.join("", b);
            return s1.compareTo(s2);
        });
    }
}
