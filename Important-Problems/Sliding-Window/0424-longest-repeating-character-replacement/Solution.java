/**
 * 424. Longest Repeating Character Replacement
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are given a string s and an integer k. You can choose any character of the string 
 * and change it to any other uppercase English character. You can perform this operation at most k times.
 * Return the length of the longest substring containing the same letter you can get after performing the above operations.
 * 
 * Key Insights:
 * 1. Use sliding window technique with two pointers
 * 2. Track frequency of characters in current window
 * 3. Track maximum frequency character in current window
 * 4. Window is valid if (window length - max frequency) <= k
 * 5. Only shrink window when it becomes invalid
 * 
 * Approach (Sliding Window with Frequency Array):
 * 1. Initialize frequency array, maxFrequency, maxLength, and left pointer
 * 2. Iterate right pointer through the string
 * 3. Update frequency of current character
 * 4. Update maxFrequency if current character frequency is higher
 * 5. While window is invalid, shrink from left and update frequency
 * 6. Update maxLength with current window size
 * 7. Return maxLength
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1) - Fixed size array for 26 letters
 * 
 * Tags: Hash Table, String, Sliding Window
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Sliding Window with Frequency Array - RECOMMENDED
     * O(n) time, O(1) space - Optimal solution
     */
    public int characterReplacement(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int[] freq = new int[26];
        int maxLength = 0;
        int maxFreq = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            freq[currentChar - 'A']++;
            
            // Update max frequency in current window
            maxFreq = Math.max(maxFreq, freq[currentChar - 'A']);
            
            // Check if current window is valid
            // Window is valid if (window length - max frequency) <= k
            // If invalid, shrink window from left
            while ((right - left + 1) - maxFreq > k) {
                char leftChar = s.charAt(left);
                freq[leftChar - 'A']--;
                left++;
                // Note: We don't need to update maxFreq here because:
                // 1. maxFreq might decrease, but that's okay for our condition
                // 2. We only care about the maximum we've seen so far
            }
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Approach 2: Sliding Window with Optimized Max Frequency Update
     * O(n) time, O(1) space - More efficient max frequency tracking
     */
    public int characterReplacementOptimized(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int[] freq = new int[26];
        int maxLength = 0;
        int maxFreq = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            freq[currentChar - 'A']++;
            
            // Update max frequency - we can optimize by only updating when necessary
            // Since we're adding a new character, maxFreq can only increase
            maxFreq = Math.max(maxFreq, freq[currentChar - 'A']);
            
            // Current window size
            int windowSize = right - left + 1;
            
            // If window is invalid, shrink from left
            if (windowSize - maxFreq > k) {
                char leftChar = s.charAt(left);
                freq[leftChar - 'A']--;
                left++;
                // Window size decreases, but we don't update maxFreq
                // This is the key optimization - we don't need the exact maxFreq
                // We only care if we found a larger maxFreq
            }
            
            // Update maximum length
            // Note: windowSize might be smaller after shrinking, so recalculate
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Approach 3: Sliding Window with HashMap
     * O(n) time, O(1) space - Uses HashMap for frequency tracking
     */
    public int characterReplacementHashMap(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        Map<Character, Integer> freqMap = new HashMap<>();
        int maxLength = 0;
        int maxFreq = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            freqMap.put(currentChar, freqMap.getOrDefault(currentChar, 0) + 1);
            
            // Update max frequency
            maxFreq = Math.max(maxFreq, freqMap.get(currentChar));
            
            // Check if current window is valid
            while ((right - left + 1) - maxFreq > k) {
                char leftChar = s.charAt(left);
                freqMap.put(leftChar, freqMap.get(leftChar) - 1);
                left++;
                // Update maxFreq after removing left character
                maxFreq = getMaxFrequency(freqMap);
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    private int getMaxFrequency(Map<Character, Integer> freqMap) {
        int max = 0;
        for (int freq : freqMap.values()) {
            max = Math.max(max, freq);
        }
        return max;
    }
    
    /**
     * Approach 4: Binary Search on Answer
     * O(n log n) time, O(1) space - Alternative approach
     */
    public int characterReplacementBinarySearch(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int left = 1; // Minimum possible length
        int right = s.length(); // Maximum possible length
        int answer = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (isValid(s, k, mid)) {
                answer = mid;
                left = mid + 1; // Try for larger length
            } else {
                right = mid - 1; // Try for smaller length
            }
        }
        
        return answer;
    }
    
    private boolean isValid(String s, int k, int length) {
        int[] freq = new int[26];
        int maxFreq = 0;
        
        // Initialize first window
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            freq[c - 'A']++;
            maxFreq = Math.max(maxFreq, freq[c - 'A']);
        }
        
        // Check if first window is valid
        if (length - maxFreq <= k) {
            return true;
        }
        
        // Slide the window
        for (int i = length; i < s.length(); i++) {
            char newChar = s.charAt(i);
            char oldChar = s.charAt(i - length);
            
            freq[newChar - 'A']++;
            freq[oldChar - 'A']--;
            
            // Recalculate max frequency for current window
            maxFreq = 0;
            for (int count : freq) {
                maxFreq = Math.max(maxFreq, count);
            }
            
            if (length - maxFreq <= k) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 5: Brute Force (For Comparison)
     * O(n^2) time, O(1) space - Not recommended for large inputs
     */
    public int characterReplacementBruteForce(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int maxLength = 0;
        
        for (int i = 0; i < s.length(); i++) {
            int[] freq = new int[26];
            int maxFreq = 0;
            
            for (int j = i; j < s.length(); j++) {
                char c = s.charAt(j);
                freq[c - 'A']++;
                maxFreq = Math.max(maxFreq, freq[c - 'A']);
                
                int windowSize = j - i + 1;
                if (windowSize - maxFreq <= k) {
                    maxLength = Math.max(maxLength, windowSize);
                } else {
                    break; // No need to check larger windows from this starting point
                }
            }
        }
        
        return maxLength;
    }
    
    /**
     * Helper method to visualize the sliding window algorithm
     */
    private void visualizeSlidingWindow(String s, int k) {
        System.out.println("\nSliding Window Algorithm Visualization:");
        System.out.println("Input: \"" + s + "\", k = " + k);
        
        int[] freq = new int[26];
        int maxLength = 0;
        int maxFreq = 0;
        int left = 0;
        
        System.out.println("\nStep | Right | Char | Left | Window | MaxFreq | Replacements | Valid? | Max Length | Action");
        System.out.println("-----|-------|------|------|--------|---------|--------------|--------|------------|--------");
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            freq[currentChar - 'A']++;
            
            // Update max frequency
            maxFreq = Math.max(maxFreq, freq[currentChar - 'A']);
            
            int windowSize = right - left + 1;
            int replacementsNeeded = windowSize - maxFreq;
            boolean isValid = replacementsNeeded <= k;
            String action = "Expand window";
            
            // Check if current window is valid
            if (!isValid) {
                char leftChar = s.charAt(left);
                freq[leftChar - 'A']--;
                left++;
                windowSize = right - left + 1;
                action = "Shrink window (replacements: " + replacementsNeeded + " > k: " + k + ")";
            }
            
            // Update maximum length
            if (windowSize > maxLength) {
                maxLength = windowSize;
                if (isValid) {
                    action += " - New max!";
                }
            }
            
            String window = s.substring(left, right + 1);
            System.out.printf("%4d | %5d | %4c | %4d | %-6s | %7d | %12d | %6s | %10d | %s%n",
                            right + 1, right, currentChar, left, window, maxFreq, 
                            windowSize - maxFreq, isValid ? "Yes" : "No", maxLength, action);
        }
        
        System.out.println("\nFinal Result: Maximum Length = " + maxLength);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Longest Repeating Character Replacement:");
        System.out.println("=================================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        String s1 = "ABAB";
        int k1 = 2;
        int expected1 = 4;
        
        long startTime = System.nanoTime();
        int result1a = solution.characterReplacement(s1, k1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.characterReplacementOptimized(s1, k1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.characterReplacementHashMap(s1, k1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.characterReplacementBinarySearch(s1, k1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.characterReplacementBruteForce(s1, k1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("Frequency Array: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Binary Search: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Brute Force: " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the sliding window algorithm
        solution.visualizeSlidingWindow(s1, k1);
        
        // Test case 2: Mixed characters with k=1
        System.out.println("\nTest 2: Mixed characters with k=1");
        String s2 = "AABABBA";
        int k2 = 1;
        int expected2 = 4;
        
        int result2a = solution.characterReplacement(s2, k2);
        System.out.println("Mixed characters k=1: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: All same characters
        System.out.println("\nTest 3: All same characters");
        String s3 = "AAAAA";
        int k3 = 2;
        int expected3 = 5;
        
        int result3a = solution.characterReplacement(s3, k3);
        System.out.println("All same characters: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Empty string
        System.out.println("\nTest 4: Empty string");
        String s4 = "";
        int k4 = 2;
        int expected4 = 0;
        
        int result4a = solution.characterReplacement(s4, k4);
        System.out.println("Empty string: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: k = 0
        System.out.println("\nTest 5: k = 0");
        String s5 = "AABABA";
        int k5 = 0;
        int expected5 = 2; // Longest consecutive same characters
        
        int result5a = solution.characterReplacement(s5, k5);
        System.out.println("k = 0: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: k >= string length
        System.out.println("\nTest 6: k >= string length");
        String s6 = "ABC";
        int k6 = 5;
        int expected6 = 3; // Can replace all characters to make them same
        
        int result6a = solution.characterReplacement(s6, k6);
        System.out.println("k >= length: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Complex pattern
        System.out.println("\nTest 7: Complex pattern");
        String s7 = "ABBBBAAB";
        int k7 = 2;
        int expected7 = 6; // Can get "BBBBBB"
        
        int result7a = solution.characterReplacement(s7, k7);
        System.out.println("Complex pattern: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Single character
        System.out.println("\nTest 8: Single character");
        String s8 = "A";
        int k8 = 1;
        int expected8 = 1;
        
        int result8a = solution.characterReplacement(s8, k8);
        System.out.println("Single character: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Frequency Array: " + time1a + " ns");
        System.out.println("  Optimized: " + time1b + " ns");
        System.out.println("  HashMap: " + time1c + " ns");
        System.out.println("  Binary Search: " + time1d + " ns");
        System.out.println("  Brute Force: " + time1e + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        StringBuilder largeString = new StringBuilder();
        Random random = new Random(42);
        for (int i = 0; i < 100000; i++) {
            largeString.append((char) ('A' + random.nextInt(3))); // Only A, B, C for more replacements
        }
        String s10 = largeString.toString();
        int k10 = 1000;
        
        startTime = System.nanoTime();
        int result10a = solution.characterReplacement(s10, k10);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10b = solution.characterReplacementOptimized(s10, k10);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10c = solution.characterReplacementHashMap(s10, k10);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (100,000 characters, k=1000):");
        System.out.println("  Frequency Array: " + time10a + " ns, Result: " + result10a);
        System.out.println("  Optimized: " + time10b + " ns, Result: " + result10b);
        System.out.println("  HashMap: " + time10c + " ns, Result: " + result10c);
        
        // Verify all approaches produce the same result
        boolean allEqual = result10a == result10b && result10a == result10c;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SLIDING WINDOW ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nKey Insight:");
        System.out.println("For a window to be valid: (window length - max frequency count) <= k");
        System.out.println("This means we can replace the non-max characters to make all characters the same");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. We maintain a window [left, right] of characters");
        System.out.println("2. We track the frequency of each character in the window");
        System.out.println("3. We track the maximum frequency of any character in the window");
        System.out.println("4. If (window size - max frequency) <= k, the window is valid");
        System.out.println("5. We only shrink the window when it becomes invalid");
        System.out.println("6. We track the maximum valid window size encountered");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Sliding Window with Frequency Array (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through string");
        System.out.println("   Space: O(1) - Fixed size array (26 elements)");
        System.out.println("   How it works:");
        System.out.println("     - Use frequency array to track character counts");
        System.out.println("     - Track max frequency in current window");
        System.out.println("     - Shrink window when (window size - max frequency) > k");
        System.out.println("     - Don't update max frequency when shrinking (key optimization)");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time complexity");
        System.out.println("     - O(1) space complexity");
        System.out.println("     - Efficient and simple");
        System.out.println("   Cons:");
        System.out.println("     - maxFrequency might not be exact after shrinking");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Sliding Window with Optimized Max Frequency:");
        System.out.println("   Time: O(n) - Single pass through string");
        System.out.println("   Space: O(1) - Fixed size array (26 elements)");
        System.out.println("   How it works:");
        System.out.println("     - Same as basic approach but with better max frequency handling");
        System.out.println("     - Only update max frequency when adding new characters");
        System.out.println("     - Don't recalculate max frequency when removing characters");
        System.out.println("   Pros:");
        System.out.println("     - More efficient than recalculating max frequency");
        System.out.println("     - Still maintains correctness");
        System.out.println("   Cons:");
        System.out.println("     - maxFrequency might be larger than actual after shrinking");
        System.out.println("   Best for: Performance-critical applications");
        
        System.out.println("\n3. Sliding Window with HashMap:");
        System.out.println("   Time: O(n) - Single pass through string");
        System.out.println("   Space: O(1) - HashMap with at most 26 entries");
        System.out.println("   How it works:");
        System.out.println("     - Use HashMap instead of array for frequency tracking");
        System.out.println("     - Recalculate max frequency when window changes");
        System.out.println("   Pros:");
        System.out.println("     - More flexible for different character sets");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Slower due to HashMap operations");
        System.out.println("     - Needs to recalculate max frequency");
        System.out.println("   Best for: Learning purposes, non-ASCII characters");
        
        System.out.println("\n4. Binary Search on Answer:");
        System.out.println("   Time: O(n log n) - Binary search with O(n) validation");
        System.out.println("   Space: O(1) - Fixed size array");
        System.out.println("   How it works:");
        System.out.println("     - Binary search on possible answer lengths");
        System.out.println("     - For each length, check if valid substring exists");
        System.out.println("     - Use sliding window to check validity for each length");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive approach");
        System.out.println("     - Guaranteed to find optimal solution");
        System.out.println("   Cons:");
        System.out.println("     - O(n log n) time, not optimal");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: Educational purposes, when O(n) is not required");
        
        System.out.println("\n5. Brute Force:");
        System.out.println("   Time: O(n^2) - Check all possible substrings");
        System.out.println("   Space: O(1) - Fixed size array");
        System.out.println("   How it works:");
        System.out.println("     - Check all possible starting points");
        System.out.println("     - For each start, expand until window becomes invalid");
        System.out.println("     - Track maximum valid window size");
        System.out.println("   Pros:");
        System.out.println("     - Simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large inputs (n > 1000)");
        System.out.println("     - Doesn't meet O(n) requirement");
        System.out.println("   Best for: Small inputs, educational purposes");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Maximum possible answer is min(n, max_consecutive + k)");
        System.out.println("2. The condition (window size - max frequency) <= k ensures we can make all characters same");
        System.out.println("3. We don't need to know which character to replace - just the count matters");
        System.out.println("4. The optimization works because we only care about finding a larger maxFrequency");
        System.out.println("5. If we find a larger maxFrequency, it will allow a larger valid window");
        System.out.println("=".repeat(80));
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Sliding Window + Frequency Array - it's the expected optimal solution");
        System.out.println("2. Explain the key condition: (window size - max frequency) <= k");
        System.out.println("3. Mention the optimization of not updating maxFrequency when shrinking");
        System.out.println("4. Handle edge cases: empty string, k=0, k>=n, single character");
        System.out.println("5. Discuss time and space complexity (O(n), O(1))");
        System.out.println("6. Consider mentioning related problems (Max Consecutive Ones III)");
        System.out.println("=".repeat(80));
        
        System.out.println("\nAll tests completed!");
    }
}
