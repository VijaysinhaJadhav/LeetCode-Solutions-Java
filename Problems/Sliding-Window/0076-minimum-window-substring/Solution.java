
## Solution.java

```java
/**
 * 76. Minimum Window Substring
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given two strings s and t, return the minimum window substring of s such that
 * every character in t (including duplicates) is included in the window.
 * 
 * Key Insights:
 * 1. Sliding window approach with two pointers
 * 2. Use frequency arrays to track character counts
 * 3. Expand window to include required characters, then shrink to find minimum
 * 4. Maintain counter to track how many characters from t are satisfied
 * 
 * Approach (Sliding Window):
 * 1. Count characters in t using frequency array
 * 2. Use two pointers (left, right) to form sliding window
 * 3. Expand right pointer until window contains all characters from t
 * 4. Then shrink left pointer to find minimum valid window
 * 5. Keep track of the minimum window found
 * 
 * Time Complexity: O(m + n) where m = s.length(), n = t.length()
 * Space Complexity: O(1) since we use fixed-size arrays for ASCII
 * 
 * Tags: Hash Table, String, Sliding Window, Two Pointers
 */

import java.util.*;

public class Solution {
    
    /**
     * Approach 1: Sliding Window with Frequency Arrays - RECOMMENDED
     * O(m + n) time, O(1) space - Optimal solution
     */
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length()) {
            return "";
        }
        
        // Frequency arrays for characters (assuming ASCII)
        int[] targetCount = new int[128]; // Characters needed from t
        int[] windowCount = new int[128]; // Characters currently in window
        
        // Count characters in t
        for (char c : t.toCharArray()) {
            targetCount[c]++;
        }
        
        // Sliding window pointers
        int left = 0, right = 0;
        int minLeft = 0; // Start of minimum window
        int minLength = Integer.MAX_VALUE;
        int required = t.length(); // Total characters needed
        int formed = 0; // Characters currently satisfied in window
        
        // Expand window with right pointer
        while (right < s.length()) {
            char rightChar = s.charAt(right);
            
            // Add character to window
            windowCount[rightChar]++;
            
            // If this character is needed and we have enough in window
            if (windowCount[rightChar] <= targetCount[rightChar]) {
                formed++;
            }
            
            // Try to shrink window from left while window is valid
            while (formed == required && left <= right) {
                // Update minimum window
                int currentLength = right - left + 1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                    minLeft = left;
                }
                
                // Remove left character from window
                char leftChar = s.charAt(left);
                windowCount[leftChar]--;
                
                // If we removed a needed character
                if (windowCount[leftChar] < targetCount[leftChar]) {
                    formed--;
                }
                
                left++;
            }
            
            right++;
        }
        
        return minLength == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLength);
    }
    
    /**
     * Approach 2: Sliding Window with HashMap
     * O(m + n) time, O(n) space - More general for Unicode
     */
    public String minWindowHashMap(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length()) {
            return "";
        }
        
        // Count characters in t
        Map<Character, Integer> targetMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetMap.put(c, targetMap.getOrDefault(c, 0) + 1);
        }
        
        // Sliding window
        int left = 0, right = 0;
        int minLeft = 0;
        int minLength = Integer.MAX_VALUE;
        int required = targetMap.size(); // Distinct characters needed
        int formed = 0; // Distinct characters satisfied
        
        Map<Character, Integer> windowMap = new HashMap<>();
        
        while (right < s.length()) {
            char rightChar = s.charAt(right);
            windowMap.put(rightChar, windowMap.getOrDefault(rightChar, 0) + 1);
            
            // Check if this character requirement is satisfied
            if (targetMap.containsKey(rightChar) && 
                windowMap.get(rightChar).intValue() == targetMap.get(rightChar).intValue()) {
                formed++;
            }
            
            // Try to shrink window
            while (formed == required && left <= right) {
                // Update minimum window
                int currentLength = right - left + 1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                    minLeft = left;
                }
                
                // Remove left character
                char leftChar = s.charAt(left);
                windowMap.put(leftChar, windowMap.get(leftChar) - 1);
                
                // Check if we broke a requirement
                if (targetMap.containsKey(leftChar) && 
                    windowMap.get(leftChar).intValue() < targetMap.get(leftChar).intValue()) {
                    formed--;
                }
                
                left++;
            }
            
            right++;
        }
        
        return minLength == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLength);
    }
    
    /**
     * Approach 3: Optimized Sliding Window with Filtered S
     * O(m + n) time, O(m) space - Better when s has many characters not in t
     */
    public String minWindowOptimized(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length()) {
            return "";
        }
        
        // Count characters in t
        int[] targetCount = new int[128];
        for (char c : t.toCharArray()) {
            targetCount[c]++;
        }
        
        // Create filtered list of indices for characters that are in t
        List<Pair> filtered = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (targetCount[c] > 0) {
                filtered.add(new Pair(i, c));
            }
        }
        
        int left = 0, right = 0;
        int minLeft = 0;
        int minLength = Integer.MAX_VALUE;
        int required = t.length();
        int formed = 0;
        int[] windowCount = new int[128];
        
        // Slide through filtered indices
        while (right < filtered.size()) {
            char rightChar = filtered.get(right).character;
            windowCount[rightChar]++;
            
            if (windowCount[rightChar] <= targetCount[rightChar]) {
                formed++;
            }
            
            // Shrink window
            while (formed == required && left <= right) {
                int start = filtered.get(left).index;
                int end = filtered.get(right).index;
                int currentLength = end - start + 1;
                
                if (currentLength < minLength) {
                    minLength = currentLength;
                    minLeft = start;
                }
                
                char leftChar = filtered.get(left).character;
                windowCount[leftChar]--;
                
                if (windowCount[leftChar] < targetCount[leftChar]) {
                    formed--;
                }
                
                left++;
            }
            
            right++;
        }
        
        return minLength == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLength);
    }
    
    private static class Pair {
        int index;
        char character;
        
        Pair(int index, char character) {
            this.index = index;
            this.character = character;
        }
    }
    
    /**
     * Approach 4: Two Pointers with Array (Alternative implementation)
     * O(m + n) time, O(1) space - Similar to approach 1 with different structure
     */
    public String minWindowTwoPointers(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length()) {
            return "";
        }
        
        int[] target = new int[128];
        for (char c : t.toCharArray()) {
            target[c]++;
        }
        
        int start = 0, end = 0;
        int minStart = 0;
        int minLen = Integer.MAX_VALUE;
        int counter = t.length();
        
        while (end < s.length()) {
            // If character exists in target, decrease counter
            if (target[s.charAt(end)] > 0) {
                counter--;
            }
            // Decrease target count and move end forward
            target[s.charAt(end)]--;
            end++;
            
            // When counter is 0, we have valid window
            while (counter == 0) {
                // Update minimum window
                if (end - start < minLen) {
                    minLen = end - start;
                    minStart = start;
                }
                
                // Increase target count for start character
                target[s.charAt(start)]++;
                
                // If character was in target and we increased it above 0, increase counter
                if (target[s.charAt(start)] > 0) {
                    counter++;
                }
                
                start++;
            }
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }
    
    /**
     * Approach 5: Brute Force (For comparison - NOT RECOMMENDED)
     * O(m^2 * n) time, O(n) space - Only for educational purposes
     */
    public String minWindowBruteForce(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length()) {
            return "";
        }
        
        int minLength = Integer.MAX_VALUE;
        String result = "";
        
        // Check all possible substrings
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + t.length(); j <= s.length(); j++) {
                String substring = s.substring(i, j);
                if (containsAllCharacters(substring, t)) {
                    if (substring.length() < minLength) {
                        minLength = substring.length();
                        result = substring;
                    }
                }
            }
        }
        
        return result;
    }
    
    private boolean containsAllCharacters(String s, String t) {
        int[] count = new int[128];
        for (char c : s.toCharArray()) {
            count[c]++;
        }
        
        for (char c : t.toCharArray()) {
            count[c]--;
            if (count[c] < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Helper method to visualize sliding window process
     */
    public void visualizeSlidingWindow(String s, String t) {
        System.out.println("\nSliding Window Visualization:");
        System.out.println("String s: " + s);
        System.out.println("String t: " + t);
        System.out.println();
        
        int[] targetCount = new int[128];
        for (char c : t.toCharArray()) {
            targetCount[c]++;
        }
        
        int[] windowCount = new int[128];
        int left = 0, right = 0;
        int required = t.length();
        int formed = 0;
        int minLeft = 0;
        int minLength = Integer.MAX_VALUE;
        
        System.out.println("Step | Left | Right | Window | Formed | Required | Action");
        System.out.println("-----|------|-------|--------|--------|----------|--------");
        
        int step = 1;
        while (right < s.length()) {
            char rightChar = s.charAt(right);
            windowCount[rightChar]++;
            
            String action = "Expand right to " + right;
            if (windowCount[rightChar] <= targetCount[rightChar]) {
                formed++;
                action += " - formed++ (" + formed + "/" + required + ")";
            }
            
            System.out.printf("%4d | %4d | %5d | %-6s | %6d | %8d | %s%n",
                step, left, right, s.substring(left, right + 1), formed, required, action);
            
            // Shrink window while valid
            while (formed == required && left <= right) {
                int currentLength = right - left + 1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                    minLeft = left;
                    action = "NEW MIN: \"" + s.substring(left, right + 1) + "\" (length: " + currentLength + ")";
                } else {
                    action = "Shrink left - current valid";
                }
                
                System.out.printf("%4s | %4d | %5d | %-6s | %6d | %8d | %s%n",
                    "", left, right, s.substring(left, right + 1), formed, required, action);
                
                char leftChar = s.charAt(left);
                windowCount[leftChar]--;
                if (windowCount[leftChar] < targetCount[leftChar]) {
                    formed--;
                    action = "Shrink left - formed-- (" + formed + "/" + required + ")";
                }
                
                left++;
            }
            
            right++;
            step++;
        }
        
        String result = minLength == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLength);
        System.out.println("\nFinal Result: \"" + result + "\"");
    }
    
    /**
     * Helper method to compare all approaches
     */
    public void compareApproaches(String s, String t) {
        System.out.println("\nComparing All Approaches:");
        System.out.println("s = \"" + s + "\", t = \"" + t + "\"");
        System.out.println("=".repeat(60));
        
        long startTime, endTime;
        String result;
        
        // Approach 1: Sliding Window with Arrays
        startTime = System.nanoTime();
        result = minWindow(s, t);
        endTime = System.nanoTime();
        System.out.println("1. Sliding Window (Arrays):");
        System.out.println("   Result: \"" + result + "\"");
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 2: Sliding Window with HashMap
        startTime = System.nanoTime();
        result = minWindowHashMap(s, t);
        endTime = System.nanoTime();
        System.out.println("2. Sliding Window (HashMap):");
        System.out.println("   Result: \"" + result + "\"");
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 3: Optimized Sliding Window
        startTime = System.nanoTime();
        result = minWindowOptimized(s, t);
        endTime = System.nanoTime();
        System.out.println("3. Optimized Sliding Window:");
        System.out.println("   Result: \"" + result + "\"");
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 4: Two Pointers
        startTime = System.nanoTime();
        result = minWindowTwoPointers(s, t);
        endTime = System.nanoTime();
        System.out.println("4. Two Pointers:");
        System.out.println("   Result: \"" + result + "\"");
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 5: Brute Force (only for small inputs)
        if (s.length() <= 100) {
            startTime = System.nanoTime();
            result = minWindowBruteForce(s, t);
            endTime = System.nanoTime();
            System.out.println("5. Brute Force:");
            System.out.println("   Result: \"" + result + "\"");
            System.out.println("   Time: " + (endTime - startTime) + " ns");
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Minimum Window Substring:");
        System.out.println("==================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        String s1 = "ADOBECODEBANC";
        String t1 = "ABC";
        String expected1 = "BANC";
        
        String result1 = solution.minWindow(s1, t1);
        System.out.println("Input: s = \"" + s1 + "\", t = \"" + t1 + "\"");
        System.out.println("Expected: \"" + expected1 + "\"");
        System.out.println("Result: \"" + result1 + "\"");
        System.out.println("Test 1: " + (expected1.equals(result1) ? "✓ PASSED" : "✗ FAILED"));
        
        // Visualize the sliding window process
        solution.visualizeSlidingWindow(s1, t1);
        
        // Test case 2: Single character
        System.out.println("\nTest 2: Single character");
        String s2 = "a";
        String t2 = "a";
        String expected2 = "a";
        String result2 = solution.minWindow(s2, t2);
        System.out.println("Single character: " + (expected2.equals(result2) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 3: No solution
        System.out.println("\nTest 3: No solution");
        String s3 = "a";
        String t3 = "aa";
        String expected3 = "";
        String result3 = solution.minWindow(s3, t3);
        System.out.println("No solution: " + (expected3.equals(result3) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 4: Multiple duplicates in t
        System.out.println("\nTest 4: Multiple duplicates");
        String s4 = "bba";
        String t4 = "ab";
        String expected4 = "ba";
        String result4 = solution.minWindow(s4, t4);
        System.out.println("Multiple duplicates: " + (expected4.equals(result4) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 5: Entire string is solution
        System.out.println("\nTest 5: Entire string is solution");
        String s5 = "abc";
        String t5 = "abc";
        String expected5 = "abc";
        String result5 = solution.minWindow(s5, t5);
        System.out.println("Entire string: " + (expected5.equals(result5) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 6: Case sensitivity
        System.out.println("\nTest 6: Case sensitivity");
        String s6 = "aBcDefGBc";
        String t6 = "BcG";
        String expected6 = "BcDefG";
        String result6 = solution.minWindow(s6, t6);
        System.out.println("Case sensitivity: " + (expected6.equals(result6) ? "✓ PASSED" : "✗ FAILED"));
        
        // Compare all approaches
        System.out.println("\nPerformance Comparison:");
        solution.compareApproaches(s1, t1);
        
        // Test with larger input
        System.out.println("\nTest 7: Larger input");
        String s7 = generateTestString(1000);
        String t7 = "abc";
        long startTime = System.nanoTime();
        String result7 = solution.minWindow(s7, t7);
        long endTime = System.nanoTime();
        System.out.println("Larger input (1000 chars): " + (endTime - startTime) + " ns");
        System.out.println("Result length: " + (result7.isEmpty() ? 0 : result7.length()));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SLIDING WINDOW ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We maintain a window [left, right] that slides through the string s.");
        System.out.println("We expand the window to include required characters from t,");
        System.out.println("then shrink it from the left to find the minimum valid window.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Count characters in t using frequency array");
        System.out.println("2. Initialize two pointers (left, right) at start of s");
        System.out.println("3. Expand right pointer:");
        System.out.println("   - Add s[right] to window count");
        System.out.println("   - If this character is needed, increment formed counter");
        System.out.println("4. When window has all required characters (formed == required):");
        System.out.println("   - Update minimum window if current is smaller");
        System.out.println("   - Shrink from left: remove s[left] from window count");
        System.out.println("   - If removed character was needed, decrement formed");
        System.out.println("   - Repeat shrinking while window remains valid");
        System.out.println("5. Continue until right reaches end of s");
        
        System.out.println("\nTime Complexity: O(m + n)");
        System.out.println("- Each character in s is processed at most twice (by left and right)");
        System.out.println("- Counting characters in t takes O(n)");
        System.out.println("- Total: O(2m + n) = O(m + n)");
        
        System.out.println("\nSpace Complexity: O(1)");
        System.out.println("- We use fixed-size arrays (128 for ASCII)");
        System.out.println("- No dependency on input size");
        
        // Edge cases and handling
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Empty strings: Return empty string immediately");
        System.out.println("2. s shorter than t: No possible solution, return empty string");
        System.out.println("3. No valid window: Return empty string when minLength remains MAX_VALUE");
        System.out.println("4. Duplicate characters in t: Handle with frequency counts");
        System.out.println("5. Case sensitivity: Treat 'A' and 'a' as different characters");
        System.out.println("6. Characters not in t: Ignore them in the counting logic");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with brute force discussion (for completeness)");
        System.out.println("2. Explain why sliding window is optimal");
        System.out.println("3. Use frequency arrays for efficiency");
        System.out.println("4. Clearly explain the formed/required counter logic");
        System.out.println("5. Handle all edge cases explicitly");
        System.out.println("6. Discuss time/space complexity thoroughly");
        System.out.println("7. Consider mentioning optimization for sparse strings");
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to generate test strings
     */
    private static String generateTestString(int length) {
        Random random = new Random(42);
        StringBuilder sb = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return sb.toString();
    }
}
