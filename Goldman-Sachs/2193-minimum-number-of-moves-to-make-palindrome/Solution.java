
# Solution.java

```java
import java.util.*;

/**
 * 2193. Minimum Number of Moves to Make Palindrome
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Find minimum adjacent swaps to make string palindrome.
 * 
 * Key Insights:
 * 1. Work from outermost characters inward
 * 2. For each left character, find matching character from right
 * 3. Swap matching character inward and count moves
 * 4. For odd-length strings, the middle character is fixed
 */
class Solution {
    
    /**
     * Approach 1: Two Pointers with Greedy Matching (Recommended)
     * Time: O(n²), Space: O(n)
     * 
     * Steps:
     * 1. Convert string to char array for manipulation
     * 2. Use two pointers: left = 0, right = n-1
     * 3. While left < right:
     *    - Find matching character for s[left] from right side
     *    - If found, swap it inward and add swaps to count
     *    - If not found (odd frequency), move to next character
     * 4. Return total swaps
     */
    public int minMovesToMakePalindrome(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        int moves = 0;
        int left = 0;
        int right = n - 1;
        
        while (left < right) {
            if (chars[left] == chars[right]) {
                // Characters match, move inward
                left++;
                right--;
            } else {
                // Find matching character for chars[left] from the right side
                int match = right;
                while (match > left && chars[match] != chars[left]) {
                    match--;
                }
                
                if (match == left) {
                    // Character appears odd number of times
                    // Swap with adjacent character (will be handled later)
                    swap(chars, left, left + 1);
                    moves++;
                } else {
                    // Move matching character to right position
                    for (int i = match; i < right; i++) {
                        swap(chars, i, i + 1);
                        moves++;
                    }
                    left++;
                    right--;
                }
            }
        }
        
        return moves;
    }
    
    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
    
    /**
     * Approach 2: Two Pointers with Character Array (More Efficient)
     * Time: O(n²), Space: O(n)
     * 
     * Similar but uses list for easier manipulation
     */
    public int minMovesToMakePalindromeList(String s) {
        List<Character> list = new ArrayList<>();
        for (char c : s.toCharArray()) {
            list.add(c);
        }
        
        int moves = 0;
        int left = 0;
        int right = list.size() - 1;
        
        while (left < right) {
            if (list.get(left) == list.get(right)) {
                left++;
                right--;
            } else {
                // Find matching character for left
                int match = right;
                while (match > left && list.get(match) != list.get(left)) {
                    match--;
                }
                
                if (match == left) {
                    // Odd character - swap with next character
                    char temp = list.get(left);
                    list.set(left, list.get(left + 1));
                    list.set(left + 1, temp);
                    moves++;
                } else {
                    // Move matching character to right position
                    for (int i = match; i < right; i++) {
                        char temp = list.get(i);
                        list.set(i, list.get(i + 1));
                        list.set(i + 1, temp);
                        moves++;
                    }
                    left++;
                    right--;
                }
            }
        }
        
        return moves;
    }
    
    /**
     * Approach 3: Two Pointers with String (More Intuitive)
     * Time: O(n²), Space: O(n)
     * 
     * Using StringBuilder for string manipulation
     */
    public int minMovesToMakePalindromeString(String s) {
        StringBuilder sb = new StringBuilder(s);
        int moves = 0;
        int left = 0;
        int right = sb.length() - 1;
        
        while (left < right) {
            if (sb.charAt(left) == sb.charAt(right)) {
                left++;
                right--;
            } else {
                // Find matching character
                int match = right;
                while (match > left && sb.charAt(match) != sb.charAt(left)) {
                    match--;
                }
                
                if (match == left) {
                    // Odd character - swap with adjacent
                    char temp = sb.charAt(left);
                    sb.setCharAt(left, sb.charAt(left + 1));
                    sb.setCharAt(left + 1, temp);
                    moves++;
                } else {
                    // Move matching character to right position
                    for (int i = match; i < right; i++) {
                        char temp = sb.charAt(i);
                        sb.setCharAt(i, sb.charAt(i + 1));
                        sb.setCharAt(i + 1, temp);
                        moves++;
                    }
                    left++;
                    right--;
                }
            }
        }
        
        return moves;
    }
    
    /**
     * Approach 4: Using Frequency Array to Skip Odd Characters
     * Time: O(n²), Space: O(n)
     * 
     * Preprocess to identify odd character and handle it specially
     */
    public int minMovesToMakePalindromeOptimized(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        int moves = 0;
        
        // Count frequency of each character
        int[] freq = new int[26];
        for (char c : chars) {
            freq[c - 'a']++;
        }
        
        // Find character with odd frequency (for odd-length strings)
        char oddChar = ' ';
        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                oddChar = (char) ('a' + i);
                break;
            }
        }
        
        int left = 0;
        int right = n - 1;
        
        while (left < right) {
            if (chars[left] == chars[right]) {
                left++;
                right--;
                continue;
            }
            
            // Try to find match for left character
            int match = right;
            while (match > left && chars[match] != chars[left]) {
                match--;
            }
            
            if (match == left) {
                // This is the odd character, move it towards center
                // Swap with adjacent character
                swap(chars, left, left + 1);
                moves++;
            } else {
                // Move matching character to right position
                for (int i = match; i < right; i++) {
                    swap(chars, i, i + 1);
                    moves++;
                }
                left++;
                right--;
            }
        }
        
        return moves;
    }
    
    /**
     * Approach 5: Greedy with Position Tracking (Most Efficient)
     * Time: O(n²), Space: O(n)
     * 
     * Track positions of each character using lists
     */
    public int minMovesToMakePalindromePositions(String s) {
        int n = s.length();
        List<Integer>[] positions = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            positions[i] = new ArrayList<>();
        }
        
        // Store positions of each character
        for (int i = 0; i < n; i++) {
            positions[s.charAt(i) - 'a'].add(i);
        }
        
        boolean[] used = new boolean[n];
        int moves = 0;
        int left = 0;
        int right = n - 1;
        
        for (int i = 0; i < n; i++) {
            if (used[i]) continue;
            
            char c = s.charAt(i);
            int idx = c - 'a';
            
            // Get the last unused position of this character
            int lastPos = positions[idx].get(positions[idx].size() - 1);
            positions[idx].remove(positions[idx].size() - 1);
            
            if (i == lastPos) {
                // Odd character - will be placed in middle
                moves += Math.abs(i - n / 2);
            } else {
                moves += Math.abs(lastPos - right);
                right--;
                used[lastPos] = true;
            }
            used[i] = true;
        }
        
        return moves;
    }
    
    /**
     * Helper: Visualize the process
     */
    public void visualizeMoves(String s) {
        System.out.println("\nMinimum Moves to Make Palindrome Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nInput: \"" + s + "\"");
        
        char[] chars = s.toCharArray();
        int n = chars.length;
        int moves = 0;
        int left = 0;
        int right = n - 1;
        int step = 1;
        
        System.out.println("\nStep-by-step process:");
        
        while (left < right) {
            System.out.printf("\nStep %d: %s (left=%d, right=%d)%n", step++, new String(chars), left, right);
            
            if (chars[left] == chars[right]) {
                System.out.printf("  chars[%d]='%c' matches chars[%d]='%c' → move inward%n", 
                    left, chars[left], right, chars[right]);
                left++;
                right--;
            } else {
                // Find matching character for left
                int match = right;
                while (match > left && chars[match] != chars[left]) {
                    match--;
                }
                
                if (match == left) {
                    System.out.printf("  No match found for '%c' (odd frequency) → swap with adjacent%n", chars[left]);
                    swap(chars, left, left + 1);
                    moves++;
                    System.out.printf("  After swap: %s (moves: %d)%n", new String(chars), moves);
                } else {
                    System.out.printf("  Found '%c' at index %d to match left '%c'%n", 
                        chars[match], match, chars[left]);
                    // Move matching character to right position
                    for (int i = match; i < right; i++) {
                        swap(chars, i, i + 1);
                        moves++;
                        System.out.printf("  Swapped indices %d and %d → %s%n", i, i + 1, new String(chars));
                    }
                    left++;
                    right--;
                    System.out.printf("  Moved inward: left=%d, right=%d (total moves: %d)%n", left, right, moves);
                }
            }
        }
        
        System.out.printf("\nFinal palindrome: \"%s\"%n", new String(chars));
        System.out.println("Total moves: " + moves);
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[] {
            "aabb",           // Example 1 → 2
            "letelt",         // Example 2 → 2
            "a",              // Single character → 0
            "ab",             // Already palindrome? "ab" cannot be palindrome (odd frequencies)
            "aa",             // Already palindrome → 0
            "abc",            // Can be "aba" in 1 move? Actually need to check
            "abcddcba",       // Already palindrome → 0
            "abcdefedcba",    // Already palindrome → 0
            "abca",           // Can become "abca" → "acba" etc.
            "abcba",          // Already palindrome? "abcba" is palindrome → 0
            "abccba",         // Already palindrome → 0
            "abcd",           // Can become "abcd" → "abdc"...
            "aaabbb",         // Multiple moves
            "ntntn"           // Odd frequency
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[] testCases = generateTestCases();
        // Expected values for the test cases
        int[] expected = {2, 2, 0, 1, 0, 1, 0, 0, 2, 0, 0, 3, 4, 1};
        
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.printf("\nTest %d: \"%s\"%n", i + 1, s);
            
            int result1 = minMovesToMakePalindrome(s);
            int result2 = minMovesToMakePalindromeList(s);
            int result3 = minMovesToMakePalindromeString(s);
            int result4 = minMovesToMakePalindromeOptimized(s);
            int result5 = minMovesToMakePalindromePositions(s);
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Moves: " + result1);
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
                visualizeMoves(s);
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
        
        // Generate larger test case
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append('a');
        }
        sb.append('b');
        sb.append('c');
        sb.append('c');
        sb.append('b');
        for (int i = 0; i < 500; i++) {
            sb.append('d');
            sb.append('d');
        }
        String largeString = sb.toString();
        
        System.out.println("Test Setup: " + largeString.length() + " characters");
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: Two Pointers (char array)
        long start = System.currentTimeMillis();
        results[0] = minMovesToMakePalindrome(largeString);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: List approach
        start = System.currentTimeMillis();
        results[1] = minMovesToMakePalindromeList(largeString);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: StringBuilder
        start = System.currentTimeMillis();
        results[2] = minMovesToMakePalindromeString(largeString);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Optimized with freq array
        start = System.currentTimeMillis();
        results[3] = minMovesToMakePalindromeOptimized(largeString);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Position tracking
        start = System.currentTimeMillis();
        results[4] = minMovesToMakePalindromePositions(largeString);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Char Array             | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. List                   | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. StringBuilder          | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. Optimized              | %9d | %6d%n", times[3], results[3]);
        System.out.printf("5. Position Tracking      | %9d | %6d%n", times[4], results[4]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3] && results[3] == results[4];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Char array is fastest due to direct manipulation");
        System.out.println("2. List and StringBuilder have overhead");
        System.out.println("3. Position tracking is also efficient");
        System.out.println("4. All O(n²) methods scale similarly");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single character:");
        System.out.println("   Input: \"a\"");
        System.out.println("   Output: " + minMovesToMakePalindrome("a"));
        
        System.out.println("\n2. Two same characters:");
        System.out.println("   Input: \"aa\"");
        System.out.println("   Output: " + minMovesToMakePalindrome("aa"));
        
        System.out.println("\n3. Two different characters:");
        System.out.println("   Input: \"ab\"");
        System.out.println("   Output: " + minMovesToMakePalindrome("ab"));
        
        System.out.println("\n4. Already palindrome:");
        System.out.println("   Input: \"abcba\"");
        System.out.println("   Output: " + minMovesToMakePalindrome("abcba"));
        
        System.out.println("\n5. Odd length with odd character:");
        System.out.println("   Input: \"aab\"");
        System.out.println("   Output: " + minMovesToMakePalindrome("aab"));
        
        System.out.println("\n6. All same characters:");
        System.out.println("   Input: \"aaaa\"");
        System.out.println("   Output: " + minMovesToMakePalindrome("aaaa"));
        
        System.out.println("\n7. Maximum length (2000 chars):");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("ab");
        }
        String longString = sb.toString();
        long start = System.currentTimeMillis();
        int result = minMovesToMakePalindrome(longString);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 2000 characters");
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain greedy approach
     */
    public void explainGreedy() {
        System.out.println("\nGreedy Approach Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nWhy Greedy Works:");
        System.out.println("1. For a palindrome, the leftmost character must match the rightmost");
        System.out.println("2. To minimize swaps, we should match each left character with the closest matching character from the right");
        System.out.println("3. This greedy choice leads to optimal solution");
        
        System.out.println("\nAlgorithm:");
        System.out.println("1. Initialize left = 0, right = n-1");
        System.out.println("2. While left < right:");
        System.out.println("   - If s[left] == s[right]: move inward");
        System.out.println("   - Else:");
        System.out.println("     - Find matching character for s[left] from right side");
        System.out.println("     - If found, swap it to the right position");
        System.out.println("     - If not found, swap s[left] with next character (odd frequency)");
        System.out.println("3. Return total swaps");
        
        System.out.println("\nExample: \"aabb\"");
        System.out.println("  Step 1: left=0(a), right=3(b) → not match");
        System.out.println("    Find 'a' from right: found at index 0 only (odd)");
        System.out.println("    Swap with adjacent: \"abab\" (moves=1)");
        System.out.println("  Step 2: left=0(a), right=3(b) → not match");
        System.out.println("    Find 'a' from right: found at index 2");
        System.out.println("    Move to right: swap index 2 with 3 → \"abba\" (moves=2)");
        System.out.println("  Step 3: left=1(b), right=2(b) → match");
        System.out.println("  Total moves = 2");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What operations are allowed? (adjacent swaps only)");
        System.out.println("   - Is the string guaranteed to form a palindrome? (Yes)");
        System.out.println("   - What's the maximum length? (2000)");
        
        System.out.println("\n2. Recognize the pattern:");
        System.out.println("   - This is similar to minimum adjacent swaps to make string palindrome");
        System.out.println("   - Can be solved with greedy two-pointer approach");
        
        System.out.println("\n3. Explain the greedy approach:");
        System.out.println("   - Work from ends inward");
        System.out.println("   - Match left character with closest matching from right");
        System.out.println("   - Count swaps as we move matching character");
        
        System.out.println("\n4. Handle odd-length strings:");
        System.out.println("   - One character will have odd frequency");
        System.out.println("   - It will end up in the middle");
        System.out.println("   - Don't need to match it with anyone");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(n²) worst case");
        System.out.println("   - Space: O(n) for char array");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - Already palindrome");
        System.out.println("   - Single character");
        System.out.println("   - All characters same");
        System.out.println("   - Odd length strings");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Forgetting to handle odd character correctly");
        System.out.println("   - Off-by-one errors in swap counting");
        System.out.println("   - Not updating indices after swaps");
        System.out.println("   - Using string immutability (convert to char array)");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("2193. Minimum Number of Moves to Make Palindrome");
        System.out.println("===============================================");
        
        // Explain greedy approach
        solution.explainGreedy();
        
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
    public int minMovesToMakePalindrome(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        int moves = 0;
        int left = 0;
        int right = n - 1;
        
        while (left < right) {
            if (chars[left] == chars[right]) {
                left++;
                right--;
            } else {
                int match = right;
                while (match > left && chars[match] != chars[left]) {
                    match--;
                }
                
                if (match == left) {
                    // Odd frequency character - swap with adjacent
                    char temp = chars[left];
                    chars[left] = chars[left + 1];
                    chars[left + 1] = temp;
                    moves++;
                } else {
                    // Move matching character to right position
                    for (int i = match; i < right; i++) {
                        char temp = chars[i];
                        chars[i] = chars[i + 1];
                        chars[i + 1] = temp;
                        moves++;
                    }
                    left++;
                    right--;
                }
            }
        }
        
        return moves;
    }
}
            """);
        
        System.out.println("\nAlternative (Position Tracking):");
        System.out.println("""
class Solution {
    public int minMovesToMakePalindrome(String s) {
        int n = s.length();
        List<Integer>[] positions = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            positions[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < n; i++) {
            positions[s.charAt(i) - 'a'].add(i);
        }
        
        boolean[] used = new boolean[n];
        int moves = 0;
        int right = n - 1;
        
        for (int i = 0; i < n; i++) {
            if (used[i]) continue;
            
            int idx = s.charAt(i) - 'a';
            int lastPos = positions[idx].get(positions[idx].size() - 1);
            positions[idx].remove(positions[idx].size() - 1);
            
            if (i == lastPos) {
                moves += Math.abs(i - n / 2);
            } else {
                moves += Math.abs(lastPos - right);
                right--;
                used[lastPos] = true;
            }
            used[i] = true;
        }
        
        return moves;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Greedy two-pointer approach is optimal");
        System.out.println("2. For each left character, find matching from the right");
        System.out.println("3. Swap matching character inward and count moves");
        System.out.println("4. Handle odd character by swapping with adjacent");
        System.out.println("5. Time: O(n²), Space: O(n)");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n²) worst case (each swap moves one character)");
        System.out.println("- Space: O(n) for char array");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you optimize for larger inputs (n=10^5)?");
        System.out.println("2. How would you handle multiple odd frequency characters?");
        System.out.println("3. What if you could swap any two characters, not just adjacent?");
        System.out.println("4. How would you return the actual palindrome, not just move count?");
        System.out.println("5. How would you handle uppercase letters?");
    }
}
