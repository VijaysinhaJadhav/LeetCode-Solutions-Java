/**
 * 567. Permutation in String
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
 * In other words, return true if one of s1's permutations is the substring of s2.
 * 
 * Key Insights:
 * 1. Use sliding window technique with window size equal to s1.length
 * 2. Track character frequencies using arrays for O(1) access
 * 3. Use matches count to efficiently check if frequencies match
 * 4. Slide the window through s2 and check for frequency matches
 * 
 * Approach (Sliding Window with Frequency Array):
 * 1. Create frequency arrays for s1 and initial window of s2
 * 2. Count how many characters have matching frequencies
 * 3. Slide the window through s2, updating frequencies and matches count
 * 4. Return true if matches count reaches 26 (all characters match)
 * 5. Return false if no window matches
 * 
 * Time Complexity: O(n) where n is the length of s2
 * Space Complexity: O(1) - Fixed size arrays for 26 letters
 * 
 * Tags: Hash Table, Two Pointers, String, Sliding Window
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Sliding Window with Frequency Array and Matches Count - RECOMMENDED
     * O(n) time, O(1) space - Optimal solution
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        int[] s1Freq = new int[26];
        int[] s2Freq = new int[26];
        
        // Initialize frequency arrays for first window
        for (int i = 0; i < s1.length(); i++) {
            s1Freq[s1.charAt(i) - 'a']++;
            s2Freq[s2.charAt(i) - 'a']++;
        }
        
        // Count initial matches
        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (s1Freq[i] == s2Freq[i]) {
                matches++;
            }
        }
        
        // If all 26 characters match, we found a permutation
        if (matches == 26) {
            return true;
        }
        
        // Slide the window through s2
        for (int i = s1.length(); i < s2.length(); i++) {
            int leftChar = s2.charAt(i - s1.length()) - 'a';
            int rightChar = s2.charAt(i) - 'a';
            
            // Update frequency for the new character entering the window
            s2Freq[rightChar]++;
            if (s2Freq[rightChar] == s1Freq[rightChar]) {
                matches++;
            } else if (s2Freq[rightChar] == s1Freq[rightChar] + 1) {
                matches--;
            }
            
            // Update frequency for the character leaving the window
            s2Freq[leftChar]--;
            if (s2Freq[leftChar] == s1Freq[leftChar]) {
                matches++;
            } else if (s2Freq[leftChar] == s1Freq[leftChar] - 1) {
                matches--;
            }
            
            // Check if we found a permutation
            if (matches == 26) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 2: Sliding Window with Frequency Array (Simple Comparison)
     * O(26n) time, O(1) space - Simple but slightly less efficient
     */
    public boolean checkInclusionSimple(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        int[] s1Freq = new int[26];
        int[] s2Freq = new int[26];
        
        // Initialize frequency arrays
        for (int i = 0; i < s1.length(); i++) {
            s1Freq[s1.charAt(i) - 'a']++;
            s2Freq[s2.charAt(i) - 'a']++;
        }
        
        // Check first window
        if (Arrays.equals(s1Freq, s2Freq)) {
            return true;
        }
        
        // Slide the window
        for (int i = s1.length(); i < s2.length(); i++) {
            // Add new character to the window
            s2Freq[s2.charAt(i) - 'a']++;
            // Remove leftmost character from the window
            s2Freq[s2.charAt(i - s1.length()) - 'a']--;
            
            // Check if current window matches s1 frequency
            if (Arrays.equals(s1Freq, s2Freq)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 3: Sliding Window with HashMap
     * O(n) time, O(1) space - Uses HashMap for frequency tracking
     */
    public boolean checkInclusionHashMap(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        Map<Character, Integer> s1Freq = new HashMap<>();
        Map<Character, Integer> windowFreq = new HashMap<>();
        
        // Initialize s1 frequency map
        for (char c : s1.toCharArray()) {
            s1Freq.put(c, s1Freq.getOrDefault(c, 0) + 1);
        }
        
        // Initialize first window frequency map
        for (int i = 0; i < s1.length(); i++) {
            char c = s2.charAt(i);
            windowFreq.put(c, windowFreq.getOrDefault(c, 0) + 1);
        }
        
        // Check first window
        if (s1Freq.equals(windowFreq)) {
            return true;
        }
        
        // Slide the window
        for (int i = s1.length(); i < s2.length(); i++) {
            char leftChar = s2.charAt(i - s1.length());
            char rightChar = s2.charAt(i);
            
            // Update window frequency map
            windowFreq.put(rightChar, windowFreq.getOrDefault(rightChar, 0) + 1);
            
            // Remove left character
            windowFreq.put(leftChar, windowFreq.get(leftChar) - 1);
            if (windowFreq.get(leftChar) == 0) {
                windowFreq.remove(leftChar);
            }
            
            // Check if current window matches s1 frequency
            if (s1Freq.equals(windowFreq)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 4: Optimized Single Array Approach
     * O(n) time, O(1) space - Uses single array and character count
     */
    public boolean checkInclusionOptimized(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        int[] freq = new int[26];
        
        // Initialize frequency array with s1 counts (positive) and initial window counts (negative)
        for (int i = 0; i < s1.length(); i++) {
            freq[s1.charAt(i) - 'a']++;
            freq[s2.charAt(i) - 'a']--;
        }
        
        // Check if first window is valid (all zeros means match)
        if (allZero(freq)) {
            return true;
        }
        
        // Slide the window
        for (int i = s1.length(); i < s2.length(); i++) {
            // Remove left character from window (add back to frequency)
            freq[s2.charAt(i - s1.length()) - 'a']++;
            // Add new character to window (subtract from frequency)
            freq[s2.charAt(i) - 'a']--;
            
            if (allZero(freq)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean allZero(int[] freq) {
        for (int count : freq) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Approach 5: Brute Force (For Comparison)
     * O(n^2) time, O(1) space - Not recommended for large inputs
     */
    public boolean checkInclusionBruteForce(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        int[] s1Freq = new int[26];
        for (char c : s1.toCharArray()) {
            s1Freq[c - 'a']++;
        }
        
        // Check all possible substrings of length s1.length
        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            int[] s2Freq = new int[26];
            for (int j = 0; j < s1.length(); j++) {
                s2Freq[s2.charAt(i + j) - 'a']++;
            }
            
            if (Arrays.equals(s1Freq, s2Freq)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Helper method to visualize the sliding window algorithm
     */
    private void visualizeSlidingWindow(String s1, String s2) {
        System.out.println("\nSliding Window Algorithm Visualization:");
        System.out.println("s1: \"" + s1 + "\", s2: \"" + s2 + "\"");
        System.out.println("Window size: " + s1.length());
        
        if (s1.length() > s2.length()) {
            System.out.println("s1 is longer than s2, no permutation possible");
            return;
        }
        
        int[] s1Freq = new int[26];
        int[] s2Freq = new int[26];
        
        // Initialize frequency arrays
        for (int i = 0; i < s1.length(); i++) {
            s1Freq[s1.charAt(i) - 'a']++;
            s2Freq[s2.charAt(i) - 'a']++;
        }
        
        System.out.println("\nStep | Window | s1 Frequency | Window Frequency | Matches? | Action");
        System.out.println("-----|--------|--------------|------------------|----------|--------");
        
        // Check first window
        boolean firstWindowMatch = Arrays.equals(s1Freq, s2Freq);
        String firstWindow = s2.substring(0, s1.length());
        System.out.printf("%4d | %-6s | %12s | %16s | %8s | %s%n",
                        1, firstWindow, arrayToString(s1Freq), 
                        arrayToString(s2Freq), firstWindowMatch ? "YES" : "NO",
                        firstWindowMatch ? "FOUND PERMUTATION!" : "Continue");
        
        if (firstWindowMatch) {
            System.out.println("\nPermutation found in first window!");
            return;
        }
        
        // Slide the window
        for (int i = s1.length(); i < s2.length(); i++) {
            char leftChar = s2.charAt(i - s1.length());
            char rightChar = s2.charAt(i);
            
            // Update frequencies
            s2Freq[rightChar - 'a']++;
            s2Freq[leftChar - 'a']--;
            
            String window = s2.substring(i - s1.length() + 1, i + 1);
            boolean match = Arrays.equals(s1Freq, s2Freq);
            
            System.out.printf("%4d | %-6s | %12s | %16s | %8s | %s%n",
                            i - s1.length() + 2, window, arrayToString(s1Freq), 
                            arrayToString(s2Freq), match ? "YES" : "NO",
                            match ? "FOUND PERMUTATION!" : 
                            "Slide: remove '" + leftChar + "', add '" + rightChar + "'");
            
            if (match) {
                System.out.println("\nPermutation found at position " + (i - s1.length() + 1));
                return;
            }
        }
        
        System.out.println("\nNo permutation found in s2");
    }
    
    private String arrayToString(int[] freq) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0) {
                sb.append((char)('a' + i)).append(":").append(freq[i]).append(" ");
            }
        }
        return sb.toString().trim();
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Permutation in String:");
        System.out.println("===============================");
        
        // Test case 1: Standard example (permutation exists)
        System.out.println("\nTest 1: Standard example (permutation exists)");
        String s1_1 = "ab";
        String s2_1 = "eidbaooo";
        boolean expected1 = true;
        
        long startTime = System.nanoTime();
        boolean result1a = solution.checkInclusion(s1_1, s2_1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.checkInclusionSimple(s1_1, s2_1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1c = solution.checkInclusionHashMap(s1_1, s2_1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1d = solution.checkInclusionOptimized(s1_1, s2_1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1e = solution.checkInclusionBruteForce(s1_1, s2_1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("Matches Count: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Simple Array: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Brute Force: " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the sliding window algorithm
        solution.visualizeSlidingWindow(s1_1, s2_1);
        
        // Test case 2: No permutation exists
        System.out.println("\nTest 2: No permutation exists");
        String s1_2 = "ab";
        String s2_2 = "eidboaoo";
        boolean expected2 = false;
        
        boolean result2a = solution.checkInclusion(s1_2, s2_2);
        System.out.println("No permutation: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: s1 longer than s2
        System.out.println("\nTest 3: s1 longer than s2");
        String s1_3 = "abc";
        String s2_3 = "ab";
        boolean expected3 = false;
        
        boolean result3a = solution.checkInclusion(s1_3, s2_3);
        System.out.println("s1 longer: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Same strings
        System.out.println("\nTest 4: Same strings");
        String s1_4 = "abc";
        String s2_4 = "abc";
        boolean expected4 = true;
        
        boolean result4a = solution.checkInclusion(s1_4, s2_4);
        System.out.println("Same strings: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single character match
        System.out.println("\nTest 5: Single character match");
        String s1_5 = "a";
        String s2_5 = "ab";
        boolean expected5 = true;
        
        boolean result5a = solution.checkInclusion(s1_5, s2_5);
        System.out.println("Single char match: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Single character no match
        System.out.println("\nTest 6: Single character no match");
        String s1_6 = "c";
        String s2_6 = "ab";
        boolean expected6 = false;
        
        boolean result6a = solution.checkInclusion(s1_6, s2_6);
        System.out.println("Single char no match: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Empty s1 (constraint says length >= 1, but testing robustness)
        System.out.println("\nTest 7: Complex permutation");
        String s1_7 = "adc";
        String s2_7 = "dcda";
        boolean expected7 = true;
        
        boolean result7a = solution.checkInclusion(s1_7, s2_7);
        System.out.println("Complex permutation: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: All same characters
        System.out.println("\nTest 8: All same characters");
        String s1_8 = "aaa";
        String s2_8 = "bbbaaabbb";
        boolean expected8 = true;
        
        boolean result8a = solution.checkInclusion(s1_8, s2_8);
        System.out.println("All same chars: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Matches Count: " + time1a + " ns");
        System.out.println("  Simple Array: " + time1b + " ns");
        System.out.println("  HashMap: " + time1c + " ns");
        System.out.println("  Optimized: " + time1d + " ns");
        System.out.println("  Brute Force: " + time1e + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        StringBuilder largeS1 = new StringBuilder();
        StringBuilder largeS2 = new StringBuilder();
        Random random = new Random(42);
        
        // Create s1 with 100 random characters
        for (int i = 0; i < 100; i++) {
            largeS1.append((char) ('a' + random.nextInt(5))); // Limited alphabet
        }
        
        // Create s2 with 10000 random characters
        for (int i = 0; i < 10000; i++) {
            largeS2.append((char) ('a' + random.nextInt(5)));
        }
        
        // Insert s1 permutation at random position
        String s1Permutation = largeS1.toString();
        int insertPos = random.nextInt(9000);
        largeS2.replace(insertPos, insertPos + 100, s1Permutation);
        
        String s1_10 = largeS1.toString();
        String s2_10 = largeS2.toString();
        
        startTime = System.nanoTime();
        boolean result10a = solution.checkInclusion(s1_10, s2_10);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result10b = solution.checkInclusionSimple(s1_10, s2_10);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result10c = solution.checkInclusionOptimized(s1_10, s2_10);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (s1: 100 chars, s2: 10,000 chars):");
        System.out.println("  Matches Count: " + time10a + " ns, Result: " + result10a);
        System.out.println("  Simple Array: " + time10b + " ns, Result: " + result10b);
        System.out.println("  Optimized: " + time10c + " ns, Result: " + result10c);
        
        // Verify all approaches produce the same result
        boolean allEqual = result10a == result10b && result10a == result10c;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SLIDING WINDOW ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nKey Insight:");
        System.out.println("A permutation of s1 exists in s2 if there's a substring in s2");
        System.out.println("that has exactly the same character frequencies as s1.");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. We maintain a sliding window of size s1.length in s2");
        System.out.println("2. We track character frequencies in the current window");
        System.out.println("3. We compare these frequencies with s1's frequencies");
        System.out.println("4. If frequencies match, we found a permutation");
        System.out.println("5. We slide the window one character at a time, updating frequencies");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Sliding Window with Matches Count (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through s2");
        System.out.println("   Space: O(1) - Fixed size arrays (26 elements)");
        System.out.println("   How it works:");
        System.out.println("     - Track frequencies of s1 and current window");
        System.out.println("     - Maintain matches count (number of characters with equal frequency)");
        System.out.println("     - Update matches count efficiently when sliding window");
        System.out.println("     - Return true when matches count reaches 26");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient O(n) time complexity");
        System.out.println("     - Avoids comparing full frequency arrays each time");
        System.out.println("     - Optimal for large inputs");
        System.out.println("   Cons:");
        System.out.println("     - More complex to implement initially");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Sliding Window with Simple Array Comparison:");
        System.out.println("   Time: O(26n) = O(n) - But with higher constant factor");
        System.out.println("   Space: O(1) - Fixed size arrays (26 elements)");
        System.out.println("   How it works:");
        System.out.println("     - Track frequencies of s1 and current window");
        System.out.println("     - Use Arrays.equals() to compare frequencies each time");
        System.out.println("     - Slide window and update frequencies");
        System.out.println("   Pros:");
        System.out.println("     - Simple and easy to understand");
        System.out.println("     - Less code to write");
        System.out.println("   Cons:");
        System.out.println("     - Slower due to full array comparison each time");
        System.out.println("     - O(26n) vs O(n) for matches count approach");
        System.out.println("   Best for: Learning purposes, small inputs");
        
        System.out.println("\n3. Sliding Window with HashMap:");
        System.out.println("   Time: O(n) - Single pass through s2");
        System.out.println("   Space: O(1) - HashMap with at most 26 entries");
        System.out.println("   How it works:");
        System.out.println("     - Use HashMap to track frequencies");
        System.out.println("     - Compare HashMaps using equals() method");
        System.out.println("     - Update frequencies when sliding window");
        System.out.println("   Pros:");
        System.out.println("     - More flexible for different character sets");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Slower due to HashMap operations");
        System.out.println("     - More overhead than array-based approaches");
        System.out.println("   Best for: Non-ASCII characters, learning purposes");
        
        System.out.println("\n4. Optimized Single Array Approach:");
        System.out.println("   Time: O(n) - Single pass through s2");
        System.out.println("   Space: O(1) - Single fixed size array");
        System.out.println("   How it works:");
        System.out.println("     - Use single array: positive for s1, negative for window");
        System.out.println("     - Check if all array elements are zero");
        System.out.println("     - Update array when sliding window");
        System.out.println("   Pros:");
        System.out.println("     - Uses only one frequency array");
        System.out.println("     - Elegant mathematical approach");
        System.out.println("   Cons:");
        System.out.println("     - Still needs to check all 26 elements each time");
        System.out.println("     - Less intuitive than two-array approach");
        System.out.println("   Best for: Code golf, elegant solutions");
        
        System.out.println("\n5. Brute Force:");
        System.out.println("   Time: O(n * m) where m is s1.length");
        System.out.println("   Space: O(1) - Fixed size arrays");
        System.out.println("   How it works:");
        System.out.println("     - Check every possible substring of length s1.length");
        System.out.println("     - For each substring, build frequency array and compare");
        System.out.println("     - Return true if any substring matches");
        System.out.println("   Pros:");
        System.out.println("     - Simple to implement and understand");
        System.out.println("     - No complex sliding window logic");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large inputs (n = 10^4)");
        System.out.println("     - O(n * m) vs O(n) for optimal approaches");
        System.out.println("   Best for: Small inputs, educational purposes");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Two strings are permutations if they have identical character frequencies");
        System.out.println("2. The problem reduces to finding a substring with specific frequency distribution");
        System.out.println("3. Window size is fixed to s1.length, making it a fixed-size sliding window");
        System.out.println("4. The matches count optimization leverages the fact that only 2 characters");
        System.out.println("   change frequency when the window slides");
        System.out.println("5. We only need to track 26 possible characters (lowercase English letters)");
        System.out.println("=".repeat(80));
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Sliding Window + Frequency Array - it's the expected approach");
        System.out.println("2. Mention the matches count optimization for better performance");
        System.out.println("3. Handle edge cases: s1 longer than s2, empty strings, single characters");
        System.out.println("4. Discuss time and space complexity (O(n), O(1))");
        System.out.println("5. Mention related problems (Find All Anagrams, Valid Anagram)");
        System.out.println("6. Consider starting with simple array comparison, then optimize to matches count");
        System.out.println("=".repeat(80));
        
        System.out.println("\nAll tests completed!");
    }
}
