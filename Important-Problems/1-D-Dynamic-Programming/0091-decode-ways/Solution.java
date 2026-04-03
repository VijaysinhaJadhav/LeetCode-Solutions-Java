
# Solution.java

```java
import java.util.*;

/**
 * 91. Decode Ways
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Count number of ways to decode a string of digits into letters.
 * Mapping: A=1, B=2, ..., Z=26.
 * 
 * Key Insights:
 * 1. Use DP where dp[i] = ways to decode first i characters
 * 2. Transition: dp[i] = dp[i-1] (if single digit valid) + dp[i-2] (if two digits valid)
 * 3. Handle '0' carefully - it can only be part of "10" or "20"
 */
class Solution {
    
    /**
     * Approach 1: Dynamic Programming with Array (Bottom-Up)
     * Time: O(n), Space: O(n)
     * 
     * Steps:
     * 1. Create dp array of size n+1
     * 2. dp[0] = 1 (empty string has 1 way)
     * 3. For i from 1 to n:
     *    - Check single digit at i-1
     *    - Check two digits at i-2,i-1
     * 4. Return dp[n]
     */
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) return 0;
        if (s.charAt(0) == '0') return 0;
        
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            // Single digit (current character)
            int singleDigit = Integer.parseInt(s.substring(i - 1, i));
            if (singleDigit >= 1 && singleDigit <= 9) {
                dp[i] += dp[i - 1];
            }
            
            // Two digits (previous and current)
            int twoDigits = Integer.parseInt(s.substring(i - 2, i));
            if (twoDigits >= 10 && twoDigits <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        
        return dp[n];
    }
    
    /**
     * Approach 2: Dynamic Programming with Space Optimization
     * Time: O(n), Space: O(1)
     * 
     * Only need previous two values, not entire array
     */
    public int numDecodingsOptimized(String s) {
        if (s == null || s.length() == 0) return 0;
        if (s.charAt(0) == '0') return 0;
        
        int prev2 = 1;  // dp[i-2]
        int prev1 = 1;  // dp[i-1]
        
        for (int i = 2; i <= s.length(); i++) {
            int current = 0;
            
            // Single digit
            int singleDigit = Integer.parseInt(s.substring(i - 1, i));
            if (singleDigit >= 1 && singleDigit <= 9) {
                current += prev1;
            }
            
            // Two digits
            int twoDigits = Integer.parseInt(s.substring(i - 2, i));
            if (twoDigits >= 10 && twoDigits <= 26) {
                current += prev2;
            }
            
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    /**
     * Approach 3: Top-Down DP (Memoization)
     * Time: O(n), Space: O(n)
     * 
     * Recursive approach with memoization
     */
    public int numDecodingsMemo(String s) {
        if (s == null || s.length() == 0) return 0;
        if (s.charAt(0) == '0') return 0;
        
        int[] memo = new int[s.length() + 1];
        Arrays.fill(memo, -1);
        memo[s.length()] = 1;
        
        return dfs(s, 0, memo);
    }
    
    private int dfs(String s, int index, int[] memo) {
        if (memo[index] != -1) return memo[index];
        
        int ways = 0;
        
        // Single digit
        if (index < s.length()) {
            int singleDigit = Integer.parseInt(s.substring(index, index + 1));
            if (singleDigit >= 1 && singleDigit <= 9) {
                ways += dfs(s, index + 1, memo);
            }
        }
        
        // Two digits
        if (index + 1 < s.length()) {
            int twoDigits = Integer.parseInt(s.substring(index, index + 2));
            if (twoDigits >= 10 && twoDigits <= 26) {
                ways += dfs(s, index + 2, memo);
            }
        }
        
        memo[index] = ways;
        return ways;
    }
    
    /**
     * Approach 4: Iterative with Character Access (No substring)
     * Time: O(n), Space: O(1)
     * 
     * More efficient by using char arithmetic instead of substring
     */
    public int numDecodingsChar(String s) {
        if (s == null || s.length() == 0) return 0;
        if (s.charAt(0) == '0') return 0;
        
        int n = s.length();
        int prev2 = 1;  // dp[i-2]
        int prev1 = 1;  // dp[i-1]
        
        for (int i = 1; i < n; i++) {
            int current = 0;
            
            // Single digit
            char currentChar = s.charAt(i);
            if (currentChar != '0') {
                current += prev1;
            }
            
            // Two digits
            char prevChar = s.charAt(i - 1);
            int twoDigits = (prevChar - '0') * 10 + (currentChar - '0');
            if (twoDigits >= 10 && twoDigits <= 26) {
                current += prev2;
            }
            
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    /**
     * Approach 5: DP with Detailed Comments (Educational)
     * Time: O(n), Space: O(n)
     * 
     * Most explicit version with comments
     */
    public int numDecodingsDetailed(String s) {
        int n = s.length();
        
        // Edge case: empty string or leading zero
        if (n == 0 || s.charAt(0) == '0') {
            return 0;
        }
        
        // dp[i] = number of ways to decode s[0..i-1]
        int[] dp = new int[n + 1];
        dp[0] = 1;  // Empty string: 1 way
        dp[1] = 1;  // First character is non-zero
        
        for (int i = 2; i <= n; i++) {
            // Check single digit at position i-1 (1-indexed)
            char currentChar = s.charAt(i - 1);
            if (currentChar != '0') {
                dp[i] += dp[i - 1];
            }
            
            // Check two digits at positions i-2 and i-1
            char prevChar = s.charAt(i - 2);
            int twoDigits = (prevChar - '0') * 10 + (currentChar - '0');
            if (prevChar != '0' && twoDigits >= 10 && twoDigits <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        
        return dp[n];
    }
    
    /**
     * Helper: Visualize the DP process
     */
    public void visualizeDP(String s) {
        System.out.println("\nDecode Ways Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nInput: \"" + s + "\"");
        
        if (s.length() == 0) {
            System.out.println("Empty string → 0 ways");
            return;
        }
        
        if (s.charAt(0) == '0') {
            System.out.println("Leading zero → 0 ways");
            return;
        }
        
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        
        System.out.println("\nDP Array:");
        System.out.println("dp[0] = 1 (base case)");
        System.out.println("dp[1] = 1 (first character '" + s.charAt(0) + "' is valid)");
        
        for (int i = 2; i <= n; i++) {
            char prevChar = s.charAt(i - 2);
            char currentChar = s.charAt(i - 1);
            
            System.out.printf("\ni = %d (characters: '%c', '%c')%n", i, prevChar, currentChar);
            
            // Single digit
            if (currentChar != '0') {
                dp[i] += dp[i - 1];
                System.out.printf("  Single digit '%c' valid → add dp[%d] = %d%n", 
                    currentChar, i - 1, dp[i - 1]);
            } else {
                System.out.printf("  Single digit '%c' invalid (zero)%n", currentChar);
            }
            
            // Two digits
            int twoDigits = (prevChar - '0') * 10 + (currentChar - '0');
            if (prevChar != '0' && twoDigits >= 10 && twoDigits <= 26) {
                dp[i] += dp[i - 2];
                System.out.printf("  Two digits '%c%c' = %d valid → add dp[%d] = %d%n", 
                    prevChar, currentChar, twoDigits, i - 2, dp[i - 2]);
            } else {
                System.out.printf("  Two digits '%c%c' = %d invalid%n", 
                    prevChar, currentChar, twoDigits);
            }
            
            System.out.printf("  dp[%d] = %d%n", i, dp[i]);
        }
        
        System.out.println("\nResult: " + dp[n] + " ways");
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[] {
            "12",           // Example 1 → 2
            "226",          // Example 2 → 3
            "06",           // Example 3 → 0
            "0",            // Leading zero → 0
            "1",            // Single digit → 1
            "10",           // "10" → 1
            "101",          // "101" → 1
            "111",          // "111" → 3 (1,1,1; 11,1; 1,11)
            "1111",         // "1111" → 5
            "27",           // "27" → 1 (only 2,7)
            "26",           // "26" → 2 (2,6 and 26)
            "2101",         // "2101" → 1
            "1201234",      // Complex
            "100",          // "100" → 0
            "110",          // "110" → 1
            "230",          // "230" → 0
            "301",          // "301" → 0
            "999",          // "999" → 1
            "11111111111111111111"  // Long string (20 ones) → 10946 (Fibonacci)
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[] testCases = generateTestCases();
        int[] expected = {2, 3, 0, 0, 1, 1, 1, 3, 5, 1, 2, 1, 0, 0, 1, 0, 0, 1, 10946};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.printf("\nTest %d: \"%s\"%n", i + 1, s);
            
            int result1 = numDecodings(s);
            int result2 = numDecodingsOptimized(s);
            int result3 = numDecodingsMemo(s);
            int result4 = numDecodingsChar(s);
            int result5 = numDecodingsDetailed(s);
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Ways: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first few test cases
            if (i < 3) {
                visualizeDP(s);
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
        
        // Create large test case
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("1111111111");  // 10 ones
        }
        String largeString = sb.toString();  // 1000 characters of '1'
        
        System.out.println("Test Setup: " + largeString.length() + " characters of '1'");
        System.out.println("This will produce Fibonacci-like growth in results");
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: DP with Array
        long start = System.currentTimeMillis();
        results[0] = numDecodings(largeString);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: DP Optimized
        start = System.currentTimeMillis();
        results[1] = numDecodingsOptimized(largeString);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Memoization
        start = System.currentTimeMillis();
        results[2] = numDecodingsMemo(largeString);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Char Arithmetic
        start = System.currentTimeMillis();
        results[3] = numDecodingsChar(largeString);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Detailed
        start = System.currentTimeMillis();
        results[4] = numDecodingsDetailed(largeString);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. DP with Array          | %9d%n", times[0]);
        System.out.printf("2. DP Optimized (O(1))    | %9d%n", times[1]);
        System.out.printf("3. Top-Down Memo          | %9d%n", times[2]);
        System.out.printf("4. Char Arithmetic        | %9d%n", times[3]);
        System.out.printf("5. Detailed               | %9d%n", times[4]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3] && results[3] == results[4];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        System.out.println("Result (first 10 digits): " + results[0]);
        
        System.out.println("\nObservations:");
        System.out.println("1. Space-optimized version is slightly faster");
        System.out.println("2. Char arithmetic avoids substring overhead");
        System.out.println("3. Top-down memoization has recursion overhead");
        System.out.println("4. All O(n) methods scale linearly");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Empty string:");
        System.out.println("   Input: \"\"");
        System.out.println("   Output: " + numDecodings(""));
        
        System.out.println("\n2. Single zero:");
        System.out.println("   Input: \"0\"");
        System.out.println("   Output: " + numDecodings("0"));
        
        System.out.println("\n3. Single valid digit:");
        System.out.println("   Input: \"5\"");
        System.out.println("   Output: " + numDecodings("5"));
        
        System.out.println("\n4. Leading zero:");
        System.out.println("   Input: \"06\"");
        System.out.println("   Output: " + numDecodings("06"));
        
        System.out.println("\n5. Zero in middle (invalid two-digit):");
        System.out.println("   Input: \"101\"");
        System.out.println("   Output: " + numDecodings("101"));
        
        System.out.println("\n6. Valid two-digit with zero:");
        System.out.println("   Input: \"10\"");
        System.out.println("   Output: " + numDecodings("10"));
        
        System.out.println("\n7. All zeros:");
        System.out.println("   Input: \"000\"");
        System.out.println("   Output: " + numDecodings("000"));
        
        System.out.println("\n8. Maximum length (100 digits):");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("1");
        }
        String longString = sb.toString();
        long start = System.currentTimeMillis();
        int result = numDecodings(longString);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 100 ones");
        System.out.println("   Output: " + result + " (Fibonacci number)");
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain recurrence relation
     */
    public void explainRecurrence() {
        System.out.println("\nRecurrence Relation Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nLet dp[i] = number of ways to decode first i characters.");
        System.out.println("Base cases:");
        System.out.println("  dp[0] = 1 (empty string, 1 way)");
        System.out.println("  dp[1] = 1 if s[0] != '0' else 0");
        
        System.out.println("\nTransition:");
        System.out.println("  dp[i] = dp[i-1] (if s[i-1] != '0')");
        System.out.println("        + dp[i-2] (if 10 ≤ s[i-2:i] ≤ 26)");
        
        System.out.println("\nWhy?");
        System.out.println("- If s[i-1] is a valid single digit (1-9), we can decode it");
        System.out.println("  and add the ways for the first i-1 characters.");
        System.out.println("- If s[i-2:i] is a valid two-digit number (10-26),");
        System.out.println("  we can decode it and add the ways for the first i-2 characters.");
        
        System.out.println("\nExample: s = \"226\"");
        System.out.println("  dp[0] = 1");
        System.out.println("  dp[1] = 1 (digit '2')");
        System.out.println("  i=2: s[1]='2' valid single → add dp[1]=1");
        System.out.println("       s[0:2]=\"22\" valid (10-26) → add dp[0]=1");
        System.out.println("       dp[2] = 2");
        System.out.println("  i=3: s[2]='6' valid single → add dp[2]=2");
        System.out.println("       s[1:3]=\"26\" valid (10-26) → add dp[1]=1");
        System.out.println("       dp[3] = 3");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What's the mapping? (1-26 to A-Z)");
        System.out.println("   - Can there be leading zeros? (Yes)");
        System.out.println("   - What about '0' alone? (Invalid)");
        
        System.out.println("\n2. Recognize pattern:");
        System.out.println("   - This is a DP problem similar to climbing stairs");
        System.out.println("   - Current state depends on previous 1 or 2 steps");
        
        System.out.println("\n3. Handle invalid cases:");
        System.out.println("   - Empty string → 0");
        System.out.println("   - Leading '0' → 0");
        System.out.println("   - '0' after a digit only valid if two-digit is 10 or 20");
        
        System.out.println("\n4. Explain recurrence:");
        System.out.println("   - Draw the decision tree");
        System.out.println("   - Show how dp[i] relates to dp[i-1] and dp[i-2]");
        
        System.out.println("\n5. Optimize space:");
        System.out.println("   - Only need two previous values");
        System.out.println("   - Can reduce space to O(1)");
        
        System.out.println("\n6. Test with examples:");
        System.out.println("   - \"12\" → 2");
        System.out.println("   - \"226\" → 3");
        System.out.println("   - \"06\" → 0");
        
        System.out.println("\n7. Complexity analysis:");
        System.out.println("   - Time: O(n) single pass");
        System.out.println("   - Space: O(1) with optimization");
        
        System.out.println("\n8. Common mistakes:");
        System.out.println("   - Forgetting that '0' cannot stand alone");
        System.out.println("   - Forgetting that '0x' for x > 0 is invalid");
        System.out.println("   - Off-by-one errors in indexing");
        System.out.println("   - Not handling empty string");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("91. Decode Ways");
        System.out.println("===============");
        
        // Explain recurrence
        solution.explainRecurrence();
        
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
        
        System.out.println("\nRecommended Implementation (Space Optimized):");
        System.out.println("""
class Solution {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') return 0;
        
        int prev2 = 1;  // dp[i-2]
        int prev1 = 1;  // dp[i-1]
        
        for (int i = 2; i <= s.length(); i++) {
            int current = 0;
            
            // Single digit
            if (s.charAt(i - 1) != '0') {
                current += prev1;
            }
            
            // Two digits
            int twoDigits = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
            if (twoDigits >= 10 && twoDigits <= 26) {
                current += prev2;
            }
            
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
}
            """);
        
        System.out.println("\nAlternative (DP with Array for clarity):");
        System.out.println("""
class Solution {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') return 0;
        
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            char current = s.charAt(i - 1);
            char prev = s.charAt(i - 2);
            
            if (current != '0') {
                dp[i] += dp[i - 1];
            }
            
            int twoDigits = (prev - '0') * 10 + (current - '0');
            if (prev != '0' && twoDigits >= 10 && twoDigits <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        
        return dp[n];
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. DP recurrence: dp[i] = dp[i-1] (single digit) + dp[i-2] (two digits)");
        System.out.println("2. Handle '0' carefully - cannot decode alone");
        System.out.println("3. Time: O(n), Space: O(1) with optimization");
        System.out.println("4. This is essentially a Fibonacci-like sequence with constraints");
        System.out.println("5. Leading zeros always make decoding impossible");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - single pass through string");
        System.out.println("- Space: O(1) - only two variables for DP state");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle '*' wildcard (Decode Ways II)?");
        System.out.println("2. How would you return the actual decoded strings?");
        System.out.println("3. What if the mapping was different (e.g., A=0)?");
        System.out.println("4. How would you handle very large numbers (BigInteger)?");
        System.out.println("5. How would you detect if a solution exists?");
    }
}
