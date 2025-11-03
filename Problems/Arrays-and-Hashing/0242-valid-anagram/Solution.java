/**
 * 242. Valid Anagram
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 * An Anagram is a word or phrase formed by rearranging the letters of a different word 
 * or phrase, typically using all the original letters exactly once.
 * 
 * Key Insights:
 * 1. Anagrams have the same characters with the same frequencies
 * 2. If lengths differ, they cannot be anagrams
 * 3. For lowercase English letters, we can use a fixed-size array (26 elements)
 * 4. Increment counts for s, decrement counts for t, check if all counts are zero
 * 
 * Approach:
 * 1. Check if lengths are equal - if not, return false
 * 2. Create frequency array of size 26 (for a-z)
 * 3. Iterate through both strings simultaneously
 * 4. For each char in s, increment frequency count
 * 5. For each char in t, decrement frequency count
 * 6. Check if all frequencies are zero
 * 
 * Time Complexity: O(n) where n is string length
 * Space Complexity: O(1) - fixed size array
 * 
 * Tags: Hash Table, String, Sorting
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

class Solution {
    /**
     * Frequency array approach - optimal for lowercase English letters
     * 
     * @param s first string
     * @param t second string
     * @return true if t is an anagram of s, false otherwise
     */
    public boolean isAnagram(String s, String t) {
        // Different lengths cannot be anagrams
        if (s.length() != t.length()) {
            return false;
        }
        
        // Frequency array for 26 lowercase letters
        int[] frequency = new int[26];
        
        // Count characters in both strings
        for (int i = 0; i < s.length(); i++) {
            frequency[s.charAt(i) - 'a']++;  // Increment for s
            frequency[t.charAt(i) - 'a']--;  // Decrement for t
        }
        
        // Check if all frequencies are zero
        for (int count : frequency) {
            if (count != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Alternative approach: Two-pass frequency counter
     * More intuitive but requires two loops
     */
    public boolean isAnagramTwoPass(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        int[] frequency = new int[26];
        
        // First pass: count characters in s
        for (char c : s.toCharArray()) {
            frequency[c - 'a']++;
        }
        
        // Second pass: decrement counts for t
        for (char c : t.toCharArray()) {
            frequency[c - 'a']--;
            // Early exit if count becomes negative
            if (frequency[c - 'a'] < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * HashMap approach - works for any Unicode characters
     * More general but slightly slower
     */
    public boolean isAnagramHashMap(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        Map<Character, Integer> countMap = new HashMap<>();
        
        // Count characters in s
        for (char c : s.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
        
        // Decrement counts for t
        for (char c : t.toCharArray()) {
            if (!countMap.containsKey(c)) {
                return false;
            }
            countMap.put(c, countMap.get(c) - 1);
            if (countMap.get(c) == 0) {
                countMap.remove(c);
            }
        }
        
        return countMap.isEmpty();
    }
    
    /**
     * Sorting approach - simple but less efficient
     * Good for small strings or when simplicity is preferred
     */
    public boolean isAnagramSorting(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        
        Arrays.sort(sArray);
        Arrays.sort(tArray);
        
        return Arrays.equals(sArray, tArray);
    }
    
    /**
     * Helper method to print test results
     */
    private static void printTestResult(String testName, String s, String t, boolean expected, boolean actual) {
        System.out.println(testName + ": " + (expected == actual ? "PASSED" : "FAILED"));
        if (expected != actual) {
            System.out.println("  Input: s=\"" + s + "\", t=\"" + t + "\"");
            System.out.println("  Expected: " + expected + ", Actual: " + actual);
        }
    }
    
    /**
     * Performance comparison helper
     */
    private static void printPerformanceResult(String approach, long timeNs) {
        System.out.println("  " + approach + " time: " + timeNs + " ns");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Valid Anagram Solution:");
        System.out.println("================================");
        
        // Test case 1: Valid anagram
        System.out.println("\nTest 1: Valid anagram");
        String s1 = "anagram", t1 = "nagaram";
        boolean expected1 = true;
        
        long startTime = System.nanoTime();
        boolean result1a = solution.isAnagram(s1, t1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.isAnagramTwoPass(s1, t1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1c = solution.isAnagramHashMap(s1, t1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1d = solution.isAnagramSorting(s1, t1);
        long time1d = System.nanoTime() - startTime;
        
        printTestResult("Single-pass array", s1, t1, expected1, result1a);
        printTestResult("Two-pass array", s1, t1, expected1, result1b);
        printTestResult("HashMap", s1, t1, expected1, result1c);
        printTestResult("Sorting", s1, t1, expected1, result1d);
        
        System.out.println("Performance for Test 1:");
        printPerformanceResult("Single-pass array", time1a);
        printPerformanceResult("Two-pass array", time1b);
        printPerformanceResult("HashMap", time1c);
        printPerformanceResult("Sorting", time1d);
        
        // Test case 2: Not anagrams
        System.out.println("\nTest 2: Not anagrams");
        String s2 = "rat", t2 = "car";
        boolean expected2 = false;
        
        boolean result2a = solution.isAnagram(s2, t2);
        boolean result2b = solution.isAnagramTwoPass(s2, t2);
        boolean result2c = solution.isAnagramHashMap(s2, t2);
        boolean result2d = solution.isAnagramSorting(s2, t2);
        
        printTestResult("Single-pass array", s2, t2, expected2, result2a);
        printTestResult("Two-pass array", s2, t2, expected2, result2b);
        printTestResult("HashMap", s2, t2, expected2, result2c);
        printTestResult("Sorting", s2, t2, expected2, result2d);
        
        // Test case 3: Different lengths
        System.out.println("\nTest 3: Different lengths");
        String s3 = "abc", t3 = "abcd";
        boolean expected3 = false;
        
        boolean result3a = solution.isAnagram(s3, t3);
        boolean result3b = solution.isAnagramTwoPass(s3, t3);
        boolean result3c = solution.isAnagramHashMap(s3, t3);
        boolean result3d = solution.isAnagramSorting(s3, t3);
        
        printTestResult("Single-pass array", s3, t3, expected3, result3a);
        printTestResult("Two-pass array", s3, t3, expected3, result3b);
        printTestResult("HashMap", s3, t3, expected3, result3c);
        printTestResult("Sorting", s3, t3, expected3, result3d);
        
        // Test case 4: Empty strings
        System.out.println("\nTest 4: Empty strings");
        String s4 = "", t4 = "";
        boolean expected4 = true;
        
        boolean result4a = solution.isAnagram(s4, t4);
        boolean result4b = solution.isAnagramTwoPass(s4, t4);
        boolean result4c = solution.isAnagramHashMap(s4, t4);
        boolean result4d = solution.isAnagramSorting(s4, t4);
        
        printTestResult("Single-pass array", s4, t4, expected4, result4a);
        printTestResult("Two-pass array", s4, t4, expected4, result4b);
        printTestResult("HashMap", s4, t4, expected4, result4c);
        printTestResult("Sorting", s4, t4, expected4, result4d);
        
        // Test case 5: Same string
        System.out.println("\nTest 5: Same string");
        String s5 = "hello", t5 = "hello";
        boolean expected5 = true;
        
        boolean result5a = solution.isAnagram(s5, t5);
        boolean result5b = solution.isAnagramTwoPass(s5, t5);
        boolean result5c = solution.isAnagramHashMap(s5, t5);
        boolean result5d = solution.isAnagramSorting(s5, t5);
        
        printTestResult("Single-pass array", s5, t5, expected5, result5a);
        printTestResult("Two-pass array", s5, t5, expected5, result5b);
        printTestResult("HashMap", s5, t5, expected5, result5c);
        printTestResult("Sorting", s5, t5, expected5, result5d);
        
        // Test case 6: Large input performance test
        System.out.println("\nTest 6: Large input performance");
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        
        // Create two large anagram strings
        for (int i = 0; i < 10000; i++) {
            sb1.append((char)('a' + (i % 26)));
            sb2.append((char)('a' + ((i + 13) % 26))); // Different order but same characters
        }
        
        String s6 = sb1.toString();
        // Create anagram by shuffling (in this case, we're creating a different arrangement)
        String t6 = sb2.toString();
        
        startTime = System.nanoTime();
        boolean result6a = solution.isAnagram(s6, t6);
        long largeTime1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result6b = solution.isAnagramSorting(s6, t6);
        long largeTime2 = System.nanoTime() - startTime;
        
        System.out.println("Large input test:");
        printPerformanceResult("Array approach", largeTime1);
        printPerformanceResult("Sorting approach", largeTime2);
        System.out.println("  Sorting is " + (largeTime2 / Math.max(largeTime1, 1)) + "x slower");
        
        // Complexity analysis summary
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPLEXITY ANALYSIS SUMMARY:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nSingle-pass Array Approach (Recommended):");
        System.out.println("  Time: O(n) - One pass through both strings");
        System.out.println("  Space: O(1) - Fixed 26-element array");
        System.out.println("  Best for: Lowercase English letters");
        System.out.println("  Advantages: Fastest, most memory efficient");
        
        System.out.println("\nTwo-pass Array Approach:");
        System.out.println("  Time: O(n) - Two passes but same complexity");
        System.out.println("  Space: O(1) - Fixed 26-element array");
        System.out.println("  Advantage: Early termination possible");
        
        System.out.println("\nHashMap Approach:");
        System.out.println("  Time: O(n) - Average case");
        System.out.println("  Space: O(k) - Where k is unique character count");
        System.out.println("  Best for: Unicode characters, any character set");
        System.out.println("  Advantage: Works for any characters");
        
        System.out.println("\nSorting Approach:");
        System.out.println("  Time: O(n log n) - Due to sorting");
        System.out.println("  Space: O(n) - Or O(1) if sorting in-place (but Java strings are immutable)");
        System.out.println("  Best for: Small strings or when simplicity is key");
        System.out.println("  Advantage: Very simple implementation");
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FINAL RECOMMENDATION:");
        System.out.println("For lowercase English letters: Use single-pass array approach");
        System.out.println("For Unicode characters: Use HashMap approach");
        System.out.println("For interview discussions: Know all approaches and their trade-offs");
        System.out.println("=".repeat(60));
        
        System.out.println("\nAll tests completed!");
    }
}
