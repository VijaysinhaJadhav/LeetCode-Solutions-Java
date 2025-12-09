
## Solution.java

```java
/**
 * 5. Longest Palindromic Substring
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a string s, return the longest palindromic substring in s.
 * 
 * Key Insights:
 * 1. Multiple approaches: Expand around center, Dynamic Programming, Manacher's Algorithm
 * 2. Expand around center is O(n²) time and O(1) space
 * 3. Need to handle both odd and even length palindromes
 * 4. The challenge is to find the solution efficiently
 * 
 * Approach (Expand Around Center):
 * 1. Iterate through each character as potential center
 * 2. Expand around center for both odd and even length palindromes
 * 3. Keep track of the longest palindrome found
 * 4. Return the substring when done
 * 
 * Time Complexity: O(n²) where n is string length
 * Space Complexity: O(1) for expand around center approach
 * 
 * Tags: String, Dynamic Programming, Two Pointers
 */

public class Solution {
    
    /**
     * Approach 1: Expand Around Center - RECOMMENDED
     * O(n²) time, O(1) space - Simple and efficient
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        
        int start = 0, end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // Check for odd length palindromes (single character center)
            int len1 = expandAroundCenter(s, i, i);
            // Check for even length palindromes (two character center)
            int len2 = expandAroundCenter(s, i, i + 1);
            
            int len = Math.max(len1, len2);
            
            // Update longest palindrome indices
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        
        return s.substring(start, end + 1);
    }
    
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // Return length of palindrome (right - left - 1)
        return right - left - 1;
    }
    
    /**
     * Approach 2: Dynamic Programming
     * O(n²) time, O(n²) space - Uses DP table
     */
    public String longestPalindromeDP(String s) {
        if (s == null || s.length() < 1) return "";
        
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int maxLength = 1;
        int start = 0;
        
        // All single characters are palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        // Check for 2-character palindromes
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }
        
        // Check for lengths 3 to n
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                // Check if substring is palindrome
                if (dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = true;
                    
                    if (len > maxLength) {
                        start = i;
                        maxLength = len;
                    }
                }
            }
        }
        
        return s.substring(start, start + maxLength);
    }
    
    /**
     * Approach 3: Brute Force (For comparison - NOT RECOMMENDED)
     * O(n³) time, O(1) space - Only for educational purposes
     */
    public String longestPalindromeBruteForce(String s) {
        if (s == null || s.length() < 1) return "";
        
        int n = s.length();
        String longest = "";
        
        // Check all possible substrings
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                String substr = s.substring(i, j + 1);
                if (isPalindrome(substr) && substr.length() > longest.length()) {
                    longest = substr;
                }
            }
        }
        
        return longest;
    }
    
    private boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
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
     * Approach 4: Manacher's Algorithm (Optimized)
     * O(n) time, O(n) space - Most efficient but complex
     */
    public String longestPalindromeManacher(String s) {
        if (s == null || s.length() < 1) return "";
        
        // Preprocess string with special characters
        String T = preprocess(s);
        int n = T.length();
        int[] P = new int[n]; // Palindrome lengths
        int C = 0, R = 0; // Center and right boundary
        
        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * C - i; // Mirror of i around center C
            
            if (i < R) {
                P[i] = Math.min(R - i, P[mirror]);
            }
            
            // Expand around center i
            while (T.charAt(i + (1 + P[i])) == T.charAt(i - (1 + P[i]))) {
                P[i]++;
            }
            
            // Update center and right boundary if needed
            if (i + P[i] > R) {
                C = i;
                R = i + P[i];
            }
        }
        
        // Find the maximum element in P
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (P[i] > maxLen) {
                maxLen = P[i];
                centerIndex = i;
            }
        }
        
        int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }
    
    private String preprocess(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append('^');
        for (int i = 0; i < s.length(); i++) {
            sb.append('#');
            sb.append(s.charAt(i));
        }
        sb.append("#$");
        return sb.toString();
    }
    
    /**
     * Approach 5: Expand Around Center with Early Termination
     * O(n²) time, O(1) space - Optimized version of approach 1
     */
    public String longestPalindromeOptimized(String s) {
        if (s == null || s.length() < 1) return "";
        
        int n = s.length();
        int start = 0, maxLength = 1;
        
        for (int i = 0; i < n; i++) {
            // Early termination: if remaining characters can't form longer palindrome
            if (n - i <= maxLength / 2) break;
            
            // Expand for odd length
            int left = i, right = i;
            while (right < n - 1 && s.charAt(right) == s.charAt(right + 1)) {
                right++; // Skip duplicates for even length handling
            }
            
            // Expand around center
            while (left > 0 && right < n - 1 && s.charAt(left - 1) == s.charAt(right + 1)) {
                left--;
                right++;
            }
            
            int length = right - left + 1;
            if (length > maxLength) {
                maxLength = length;
                start = left;
            }
        }
        
        return s.substring(start, start + maxLength);
    }
    
    /**
     * Helper method to visualize the expand around center process
     */
    public void visualizeExpandAroundCenter(String s) {
        System.out.println("\nExpand Around Center Visualization:");
        System.out.println("String: " + s);
        System.out.println();
        
        int start = 0, end = 0;
        
        System.out.println("Step | Center | Type  | Left | Right | Palindrome | Max So Far");
        System.out.println("-----|--------|-------|------|-------|------------|-----------");
        
        int step = 1;
        for (int i = 0; i < s.length(); i++) {
            // Odd length expansion
            int leftOdd = i, rightOdd = i;
            String oddPalindrome = expandAndVisualize(s, leftOdd, rightOdd, step++, "Odd  ");
            int lenOdd = oddPalindrome.length();
            
            if (lenOdd > end - start + 1) {
                start = i - lenOdd / 2;
                end = i + lenOdd / 2;
                System.out.printf("%67s | %s%n", oddPalindrome, oddPalindrome);
            }
            
            // Even length expansion
            int leftEven = i, rightEven = i + 1;
            if (rightEven < s.length()) {
                String evenPalindrome = expandAndVisualize(s, leftEven, rightEven, step++, "Even ");
                int lenEven = evenPalindrome.length();
                
                if (lenEven > end - start + 1) {
                    start = i - (lenEven - 2) / 2;
                    end = i + 1 + (lenEven - 2) / 2;
                    System.out.printf("%67s | %s%n", evenPalindrome, evenPalindrome);
                }
            }
        }
        
        String result = s.substring(start, end + 1);
        System.out.println("\nFinal Result: \"" + result + "\" (length: " + result.length() + ")");
    }
    
    private String expandAndVisualize(String s, int left, int right, int step, String type) {
        int originalLeft = left, originalRight = right;
        
        // Expand while palindrome condition holds
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        
        // The actual palindrome is from left+1 to right-1
        int palindromeStart = left + 1;
        int palindromeEnd = right - 1;
        String palindrome = s.substring(palindromeStart, palindromeEnd + 1);
        
        System.out.printf("%4d | %6d | %5s | %4d | %5d | %-10s |%n",
            step, originalLeft, type, originalLeft, originalRight, 
            palindrome.isEmpty() ? "-" : palindrome);
        
        return palindrome;
    }
    
    /**
     * Helper method to visualize Dynamic Programming approach
     */
    public void visualizeDP(String s) {
        System.out.println("\nDynamic Programming Visualization:");
        System.out.println("String: " + s);
        System.out.println();
        
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int maxLength = 1;
        int start = 0;
        
        // Initialize diagonal (single characters)
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        System.out.println("Step | Length | (i,j) | Characters | Palindrome | Max So Far");
        System.out.println("-----|--------|-------|------------|------------|-----------");
        
        int step = 1;
        
        // Check for length 2 palindromes
        for (int i = 0; i < n - 1; i++) {
            int j = i + 1;
            if (s.charAt(i) == s.charAt(j)) {
                dp[i][j] = true;
                start = i;
                maxLength = 2;
                System.out.printf("%4d | %6d | (%d,%d) | %c%c%8s | %-10s | %s%n",
                    step++, 2, i, j, s.charAt(i), s.charAt(j), "", "YES", s.substring(i, j + 1));
            }
        }
        
        // Check for lengths 3 to n
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                if (dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = true;
                    
                    if (len > maxLength) {
                        start = i;
                        maxLength = len;
                        System.out.printf("%4d | %6d | (%d,%d) | %s%6s | %-10s | %s%n",
                            step++, len, i, j, s.substring(i, j + 1), "", "YES", s.substring(i, j + 1));
                    }
                }
            }
        }
        
        String result = s.substring(start, start + maxLength);
        System.out.println("\nFinal Result: \"" + result + "\" (length: " + result.length() + ")");
    }
    
    /**
     * Helper method to compare all approaches
     */
    public void compareApproaches(String s) {
        System.out.println("\nComparing All Approaches:");
        System.out.println("s = \"" + s + "\"");
        System.out.println("=".repeat(70));
        
        long startTime, endTime;
        String result;
        
        // Approach 1: Expand Around Center
        startTime = System.nanoTime();
        result = longestPalindrome(s);
        endTime = System.nanoTime();
        System.out.println("1. Expand Around Center:");
        System.out.println("   Result: \"" + result + "\"");
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 2: Dynamic Programming
        startTime = System.nanoTime();
        result = longestPalindromeDP(s);
        endTime = System.nanoTime();
        System.out.println("2. Dynamic Programming:");
        System.out.println("   Result: \"" + result + "\"");
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 3: Manacher's Algorithm
        startTime = System.nanoTime();
        result = longestPalindromeManacher(s);
        endTime = System.nanoTime();
        System.out.println("3. Manacher's Algorithm:");
        System.out.println("   Result: \"" + result + "\"");
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 4: Optimized Expand
        startTime = System.nanoTime();
        result = longestPalindromeOptimized(s);
        endTime = System.nanoTime();
        System.out.println("4. Optimized Expand:");
        System.out.println("   Result: \"" + result + "\"");
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 5: Brute Force (only for small inputs)
        if (s.length() <= 100) {
            startTime = System.nanoTime();
            result = longestPalindromeBruteForce(s);
            endTime = System.nanoTime();
            System.out.println("5. Brute Force:");
            System.out.println("   Result: \"" + result + "\"");
            System.out.println("   Time: " + (endTime - startTime) + " ns");
        }
        
        // Verify all approaches produce same result
        String result1 = longestPalindrome(s);
        String result2 = longestPalindromeDP(s);
        String result3 = longestPalindromeManacher(s);
        String result4 = longestPalindromeOptimized(s);
        boolean allEqual = result1.equals(result2) && result1.equals(result3) && result1.equals(result4);
        System.out.println("All approaches produce same result: " + (allEqual ? "✓" : "✗"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Longest Palindromic Substring:");
        System.out.println("======================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        String s1 = "babad";
        String expected1a = "bab";
        String expected1b = "aba";
        
        String result1 = solution.longestPalindrome(s1);
        System.out.println("Input: \"" + s1 + "\"");
        System.out.println("Expected: \"" + expected1a + "\" or \"" + expected1b + "\"");
        System.out.println("Result: \"" + result1 + "\"");
        System.out.println("Test 1: " + 
            (result1.equals(expected1a) || result1.equals(expected1b) ? "✓ PASSED" : "✗ FAILED"));
        
        // Visualize the expand around center process
        solution.visualizeExpandAroundCenter(s1);
        
        // Test case 2: Even length palindrome
        System.out.println("\nTest 2: Even length palindrome");
        String s2 = "cbbd";
        String expected2 = "bb";
        String result2 = solution.longestPalindrome(s2);
        System.out.println("Even length: " + (expected2.equals(result2) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 3: Single character
        System.out.println("\nTest 3: Single character");
        String s3 = "a";
        String expected3 = "a";
        String result3 = solution.longestPalindrome(s3);
        System.out.println("Single character: " + (expected3.equals(result3) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 4: Entire string is palindrome
        System.out.println("\nTest 4: Entire string is palindrome");
        String s4 = "racecar";
        String expected4 = "racecar";
        String result4 = solution.longestPalindrome(s4);
        System.out.println("Entire palindrome: " + (expected4.equals(result4) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 5: No palindrome longer than 1
        System.out.println("\nTest 5: No palindrome longer than 1");
        String s5 = "abc";
        String expected5 = "a"; // or "b" or "c"
        String result5 = solution.longestPalindrome(s5);
        System.out.println("No long palindrome: " + 
            (result5.length() == 1 && s5.contains(result5) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 6: Multiple same characters
        System.out.println("\nTest 6: Multiple same characters");
        String s6 = "aaa";
        String expected6 = "aaa";
        String result6 = solution.longestPalindrome(s6);
        System.out.println("Multiple same: " + (expected6.equals(result6) ? "✓ PASSED" : "✗ FAILED"));
        
        // Compare all approaches
        System.out.println("\nPerformance Comparison:");
        solution.compareApproaches(s1);
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        String s7 = generateTestString(1000);
        long startTime = System.nanoTime();
        String result7 = solution.longestPalindrome(s7);
        long endTime = System.nanoTime();
        System.out.println("Large input (1000 chars): " + (endTime - startTime) + " ns");
        System.out.println("Result length: " + result7.length());
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EXPAND AROUND CENTER ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Every palindrome has a center. For odd-length palindromes, the center");
        System.out.println("is a single character. For even-length palindromes, the center is between");
        System.out.println("two characters. We can expand around each possible center to find the");
        System.out.println("longest palindrome.");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. Covers all possible palindrome centers");
        System.out.println("2. Handles both odd and even length palindromes");
        System.out.println("3. Each expansion takes O(n) time in worst case");
        System.out.println("4. Total time complexity is O(n²)");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. For each character in the string:");
        System.out.println("   a. Expand for odd-length palindrome (center at current character)");
        System.out.println("   b. Expand for even-length palindrome (center between current and next)");
        System.out.println("2. For each expansion, move outwards while characters match");
        System.out.println("3. Keep track of the longest palindrome found");
        System.out.println("4. Return the longest palindrome substring");
        
        System.out.println("\nTime Complexity: O(n²)");
        System.out.println("- n centers to check");
        System.out.println("- Each expansion can take O(n) time in worst case");
        System.out.println("- Total: O(n²)");
        
        System.out.println("\nSpace Complexity: O(1)");
        System.out.println("- Only uses a few variables to track indices");
        System.out.println("- No additional data structures needed");
        
        // Comparison with other approaches
        System.out.println("\n" + "=".repeat(70));
        System.out.println("APPROACH COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Expand Around Center (RECOMMENDED):");
        System.out.println("   Time: O(n²) - Good for n ≤ 1000");
        System.out.println("   Space: O(1) - Most efficient");
        System.out.println("   Pros: Simple, intuitive, good performance");
        System.out.println("   Cons: Not optimal for very large strings");
        
        System.out.println("\n2. Dynamic Programming:");
        System.out.println("   Time: O(n²) - Same as expand");
        System.out.println("   Space: O(n²) - Uses DP table");
        System.out.println("   Pros: Systematic, builds solution bottom-up");
        System.out.println("   Cons: More memory, slightly slower");
        
        System.out.println("\n3. Manacher's Algorithm:");
        System.out.println("   Time: O(n) - Optimal");
        System.out.println("   Space: O(n) - Linear space");
        System.out.println("   Pros: Fastest for large inputs");
        System.out.println("   Cons: Complex implementation, harder to understand");
        
        System.out.println("\n4. Brute Force:");
        System.out.println("   Time: O(n³) - Too slow");
        System.out.println("   Space: O(1) - Minimal memory");
        System.out.println("   Pros: Very simple");
        System.out.println("   Cons: Only for very small inputs");
        
        // Edge cases and handling
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Empty string: Return empty string");
        System.out.println("2. Single character: Return the character itself");
        System.out.println("3. All same characters: Return entire string");
        System.out.println("4. No palindromes longer than 1: Return any single character");
        System.out.println("5. Multiple palindromes of same length: Return any valid one");
        System.out.println("6. Even vs odd length: Algorithm handles both");
        System.out.println("7. String with special characters: Handled naturally");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with brute force discussion (for completeness)");
        System.out.println("2. Explain why O(n³) is insufficient");
        System.out.println("3. Propose expand around center approach");
        System.out.println("4. Demonstrate with examples for both odd and even cases");
        System.out.println("5. Discuss time/space complexity");
        System.out.println("6. Mention DP approach as alternative");
        System.out.println("7. For follow-up, discuss Manacher's algorithm");
        System.out.println("8. Handle all edge cases explicitly");
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to generate test strings
     */
    private static String generateTestString(int length) {
        Random random = new Random(42);
        StringBuilder sb = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return sb.toString();
    }
}
