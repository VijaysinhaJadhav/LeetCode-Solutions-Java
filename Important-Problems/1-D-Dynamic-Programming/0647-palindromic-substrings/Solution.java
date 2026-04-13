
# Solution.java

```java
import java.util.*;

/**
 * 647. Palindromic Substrings
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Count the number of palindromic substrings in a given string.
 * 
 * Key Insights:
 * 1. Each single character is a palindrome
 * 2. Expand around center to find palindromes
 * 3. Two types of centers: odd length (character) and even length (between characters)
 */
class Solution {
    
    /**
     * Approach 1: Expand Around Center (Recommended)
     * Time: O(n²), Space: O(1)
     * 
     * Steps:
     * 1. For each center (2n-1 centers: n for odd, n-1 for even)
     * 2. Expand outward while characters match
     * 3. Count each valid expansion
     */
    public int countSubstrings(String s) {
        int n = s.length();
        int count = 0;
        
        // For each possible center
        for (int center = 0; center < 2 * n - 1; center++) {
            int left = center / 2;
            int right = left + center % 2;
            
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                count++;
                left--;
                right++;
            }
        }
        
        return count;
    }
    
    /**
     * Approach 2: Expand Around Center (Explicit)
     * Time: O(n²), Space: O(1)
     * 
     * Separate handling for odd and even length palindromes
     */
    public int countSubstringsExplicit(String s) {
        int n = s.length();
        int count = 0;
        
        // Odd length palindromes (center at each character)
        for (int i = 0; i < n; i++) {
            count += expand(s, i, i);
        }
        
        // Even length palindromes (center between characters)
        for (int i = 0; i < n - 1; i++) {
            count += expand(s, i, i + 1);
        }
        
        return count;
    }
    
    private int expand(String s, int left, int right) {
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;
            left--;
            right++;
        }
        return count;
    }
    
    /**
     * Approach 3: Dynamic Programming
     * Time: O(n²), Space: O(n²)
     * 
     * dp[i][j] = true if s[i..j] is palindrome
     */
    public int countSubstringsDP(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int count = 0;
        
        // Single character palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
            count++;
        }
        
        // Two character palindromes
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                count++;
            }
        }
        
        // Longer palindromes
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 4: Manacher's Algorithm (Most Efficient)
     * Time: O(n), Space: O(n)
     * 
     * Finds all palindromic substrings in linear time
     */
    public int countSubstringsManacher(String s) {
        // Transform string to avoid odd/even length handling
        // e.g., "abc" -> "^#a#b#c#$"
        StringBuilder t = new StringBuilder("^");
        for (char c : s.toCharArray()) {
            t.append('#').append(c);
        }
        t.append("#$");
        
        int n = t.length();
        int[] p = new int[n]; // palindrome radii
        int center = 0, right = 0;
        int count = 0;
        
        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i;
            
            if (i < right) {
                p[i] = Math.min(right - i, p[mirror]);
            }
            
            // Expand around center i
            while (t.charAt(i + p[i] + 1) == t.charAt(i - p[i] - 1)) {
                p[i]++;
            }
            
            // Update center and right boundary
            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
            
            // Each p[i] represents number of palindromes centered at i
            // For transformed string, each p[i] corresponds to (p[i] + 1) / 2 palindromes
            count += (p[i] + 1) / 2;
        }
        
        return count;
    }
    
    /**
     * Approach 5: Brute Force (for small strings)
     * Time: O(n³), Space: O(1)
     * 
     * Check every substring
     */
    public int countSubstringsBruteForce(String s) {
        int n = s.length();
        int count = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalindrome(s, i, j)) {
                    count++;
                }
            }
        }
        
        return count;
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
     * Helper: Visualize palindrome expansion
     */
    public void visualizeExpansion(String s) {
        System.out.println("\nPalindromic Substrings Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nString: \"" + s + "\"");
        System.out.println("Length: " + s.length());
        
        int n = s.length();
        int count = 0;
        List<String> palindromes = new ArrayList<>();
        
        System.out.println("\nExpand Around Center:");
        System.out.println("Center Type | Center | Left | Right | Found Palindromes");
        System.out.println("------------|--------|------|-------|------------------");
        
        // Odd length palindromes
        for (int i = 0; i < n; i++) {
            int left = i, right = i;
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                String palindrome = s.substring(left, right + 1);
                palindromes.add(palindrome);
                System.out.printf("Odd         | %d      | %d    | %d     | \"%s\"%n", i, left, right, palindrome);
                count++;
                left--;
                right++;
            }
        }
        
        // Even length palindromes
        for (int i = 0; i < n - 1; i++) {
            int left = i, right = i + 1;
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                String palindrome = s.substring(left, right + 1);
                palindromes.add(palindrome);
                System.out.printf("Even        | %d-%d    | %d    | %d     | \"%s\"%n", i, i + 1, left, right, palindrome);
                count++;
                left--;
                right++;
            }
        }
        
        System.out.println("\nAll palindromic substrings: " + palindromes);
        System.out.println("Total count: " + count);
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[]{
            "abc",      // 3
            "aaa",      // 6
            "a",        // 1
            "ab",       // 2
            "aba",      // 4
            "aaaa",     // 10
            "abba",     // 6
            "racecar"   // 10
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[] testCases = generateTestCases();
        int[] expected = {3, 6, 1, 2, 4, 10, 6, 10};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.printf("\nTest %d: \"%s\"%n", i + 1, s);
            
            int result1 = countSubstrings(s);
            int result2 = countSubstringsExplicit(s);
            int result3 = countSubstringsDP(s);
            int result4 = countSubstringsManacher(s);
            int result5 = countSubstringsBruteForce(s);
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Count: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Expand: " + result1);
                System.out.println("  Explicit: " + result2);
                System.out.println("  DP: " + result3);
                System.out.println("  Manacher: " + result4);
                System.out.println("  Brute Force: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeExpansion(s);
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
        int n = 1000;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            sb.append((char) ('a' + rand.nextInt(26)));
        }
        String s = sb.toString();
        
        System.out.println("Test Setup: string length = " + n);
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: Expand Around Center
        long start = System.currentTimeMillis();
        results[0] = countSubstrings(s);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Explicit Expand
        start = System.currentTimeMillis();
        results[1] = countSubstringsExplicit(s);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: DP
        start = System.currentTimeMillis();
        results[2] = countSubstringsDP(s);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Manacher
        start = System.currentTimeMillis();
        results[3] = countSubstringsManacher(s);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Brute Force (skip for large n)
        times[4] = -1;
        results[4] = -1;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Expand Around Center   | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Explicit Expand        | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. DP                     | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. Manacher               | %9d | %6d%n", times[3], results[3]);
        System.out.printf("5. Brute Force            | %9s | %6s%n", "N/A", "N/A");
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3];
        System.out.println("\nAll O(n²) methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Expand around center is fastest O(n²) method");
        System.out.println("2. DP uses more memory (O(n²)) but similar time");
        System.out.println("3. Manacher is O(n) but more complex to implement");
        System.out.println("4. Brute force O(n³) is too slow for n=1000");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single character:");
        System.out.println("   Input: \"a\"");
        System.out.println("   Output: " + countSubstrings("a"));
        
        System.out.println("\n2. Two different characters:");
        System.out.println("   Input: \"ab\"");
        System.out.println("   Output: " + countSubstrings("ab"));
        
        System.out.println("\n3. Two same characters:");
        System.out.println("   Input: \"aa\"");
        System.out.println("   Output: " + countSubstrings("aa"));
        
        System.out.println("\n4. All same characters (n=1000):");
        String s = "a".repeat(1000);
        long start = System.currentTimeMillis();
        int result = countSubstrings(s);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 1000 'a's");
        System.out.println("   Output: " + result + " (should be 500500)");
        System.out.println("   Time: " + time + "ms");
        
        System.out.println("\n5. Palindrome string:");
        System.out.println("   Input: \"racecar\"");
        System.out.println("   Output: " + countSubstrings("racecar"));
    }
    
    /**
     * Helper: Explain expand around center
     */
    public void explainExpandAroundCenter() {
        System.out.println("\nExpand Around Center Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nConcept:");
        System.out.println("Every palindrome has a center (either a character or between characters)");
        System.out.println("We can expand outward from each center while characters match.");
        
        System.out.println("\nTypes of centers:");
        System.out.println("- Odd length: center at each character (n centers)");
        System.out.println("- Even length: center between each pair of characters (n-1 centers)");
        
        System.out.println("\nExample: s = \"aaa\"");
        System.out.println("  Odd centers:");
        System.out.println("    Center 0: expand → \"a\", \"aa\", \"aaa\"");
        System.out.println("    Center 1: expand → \"a\", \"aa\"");
        System.out.println("    Center 2: expand → \"a\"");
        System.out.println("  Even centers:");
        System.out.println("    Between 0-1: expand → \"aa\", \"aaa\"");
        System.out.println("    Between 1-2: expand → \"aa\"");
        System.out.println("  Total: 3 + 2 + 1 + 2 + 1 = 9? Wait, we need to count carefully...");
        System.out.println("  Actually each expansion gives a NEW palindrome substring.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize count = 0");
        System.out.println("2. For each center (2n-1 centers):");
        System.out.println("   - left = center / 2");
        System.out.println("   - right = left + center % 2");
        System.out.println("   - While left >= 0 and right < n and s[left] == s[right]:");
        System.out.println("       count++");
        System.out.println("       left--, right++");
        System.out.println("3. Return count");
        
        System.out.println("\nComplexity: O(n²) time, O(1) space");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Substrings vs subsequences? (Substrings are contiguous)");
        System.out.println("   - Single characters count? (Yes)");
        System.out.println("   - Overlapping palindromes count separately? (Yes)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - O(n³) checking every substring");
        System.out.println("   - Acknowledge it's too slow for n=1000");
        
        System.out.println("\n3. Propose expand around center:");
        System.out.println("   - O(n²) time, O(1) space");
        System.out.println("   - Explain the two types of centers");
        System.out.println("   - Walk through example");
        
        System.out.println("\n4. Discuss DP approach:");
        System.out.println("   - O(n²) time, O(n²) space");
        System.out.println("   - Trade-off: more memory, easier to understand");
        
        System.out.println("\n5. Mention Manacher's algorithm:");
        System.out.println("   - O(n) time, O(n) space");
        System.out.println("   - Complex but optimal");
        
        System.out.println("\n6. Complexity analysis:");
        System.out.println("   - Expand: O(n²) time, O(1) space");
        System.out.println("   - DP: O(n²) time, O(n²) space");
        System.out.println("   - Manacher: O(n) time, O(n) space");
        
        System.out.println("\n7. Edge cases:");
        System.out.println("   - Single character");
        System.out.println("   - All same characters");
        System.out.println("   - No palindromes (all distinct)");
        System.out.println("   - Very long string (1000 chars)");
        
        System.out.println("\n8. Common mistakes:");
        System.out.println("   - Double counting palindromes");
        System.out.println("   - Off-by-one errors in center calculation");
        System.out.println("   - Forgetting that single characters are palindromes");
        System.out.println("   - Using O(n³) brute force in interview");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("647. Palindromic Substrings");
        System.out.println("===========================");
        
        // Explain expand around center
        solution.explainExpandAroundCenter();
        
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
        
        System.out.println("\nRecommended Implementation (Expand Around Center):");
        System.out.println("""
class Solution {
    public int countSubstrings(String s) {
        int n = s.length();
        int count = 0;
        
        for (int center = 0; center < 2 * n - 1; center++) {
            int left = center / 2;
            int right = left + center % 2;
            
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                count++;
                left--;
                right++;
            }
        }
        
        return count;
    }
}
            """);
        
        System.out.println("\nAlternative (Explicit Expand):");
        System.out.println("""
class Solution {
    public int countSubstrings(String s) {
        int count = 0;
        
        for (int i = 0; i < s.length(); i++) {
            count += expand(s, i, i);     // odd length
            count += expand(s, i, i + 1); // even length
        }
        
        return count;
    }
    
    private int expand(String s, int left, int right) {
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;
            left--;
            right++;
        }
        return count;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Expand around center is O(n²) time, O(1) space");
        System.out.println("2. Two types of centers: odd (character) and even (between characters)");
        System.out.println("3. Single characters are always palindromes");
        System.out.println("4. DP approach uses O(n²) space but is easier to understand");
        System.out.println("5. Manacher's algorithm is O(n) but complex to implement");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Expand: O(n²) time, O(1) space");
        System.out.println("- DP: O(n²) time, O(n²) space");
        System.out.println("- Manacher: O(n) time, O(n) space");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you find the longest palindromic substring?");
        System.out.println("2. How would you count palindromic subsequences?");
        System.out.println("3. How would you handle uppercase letters?");
        System.out.println("4. How would you find all palindrome substrings?");
        System.out.println("5. How would you solve if the string was very long (10^6)?");
    }
}
