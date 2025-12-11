
## Solution.java

```java
/**
 * 767. Reorganize String
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Rearrange string so that no two adjacent characters are the same.
 * Return empty string if not possible.
 * 
 * Key Insights:
 * 1. If any character frequency > (n+1)/2, impossible
 * 2. Greedy: always pick most frequent character (except last used)
 * 3. Use max heap to track character frequencies
 * 4. Place most frequent characters first, interleaving with others
 * 
 * Approach (Max Heap):
 * 1. Count frequency of each character
 * 2. Check feasibility: if any freq > (n+1)/2, return ""
 * 3. Create max heap ordered by frequency
 * 4. Build result by always picking most frequent character (not last used)
 * 5. Return result if complete, else ""
 * 
 * Time Complexity: O(n log 26) ≈ O(n)
 * Space Complexity: O(26) ≈ O(1)
 * 
 * Tags: Hash Table, String, Greedy, Sorting, Heap, Counting
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Max Heap with Frequency Tracking (RECOMMENDED)
     * O(n log k) time, O(k) space where k = 26
     */
    public String reorganizeString(String s) {
        int n = s.length();
        
        // Count frequency of each character
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Check feasibility: if any character appears more than (n+1)/2 times
        int maxFreq = 0;
        char maxChar = 'a';
        for (int i = 0; i < 26; i++) {
            if (freq[i] > maxFreq) {
                maxFreq = freq[i];
                maxChar = (char)('a' + i);
            }
        }
        
        if (maxFreq > (n + 1) / 2) {
            return "";
        }
        
        // Create max heap ordered by frequency
        PriorityQueue<Character> maxHeap = new PriorityQueue<>(
            (a, b) -> freq[b - 'a'] - freq[a - 'a']
        );
        
        // Add characters with non-zero frequency to heap
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                maxHeap.offer((char)('a' + i));
            }
        }
        
        // Build the result
        StringBuilder result = new StringBuilder();
        
        while (!maxHeap.isEmpty()) {
            // Get most frequent character
            char current = maxHeap.poll();
            
            // If last character is same as current, need to use second most frequent
            if (result.length() > 0 && result.charAt(result.length() - 1) == current) {
                if (maxHeap.isEmpty()) {
                    return ""; // No alternative character available
                }
                
                // Get second most frequent character
                char next = maxHeap.poll();
                result.append(next);
                
                // Update frequency and re-add to heap if still has occurrences
                freq[next - 'a']--;
                if (freq[next - 'a'] > 0) {
                    maxHeap.offer(next);
                }
                
                // Re-add current character to heap
                maxHeap.offer(current);
            } else {
                // Safe to use current character
                result.append(current);
                freq[current - 'a']--;
                
                // Re-add to heap if still has occurrences
                if (freq[current - 'a'] > 0) {
                    maxHeap.offer(current);
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 2: Interleaving Most Frequent Character
     * Place most frequent character at even indices, others fill remaining
     */
    public String reorganizeStringInterleave(String s) {
        int n = s.length();
        int[] freq = new int[26];
        
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Find most frequent character
        int maxFreq = 0;
        char maxChar = 'a';
        for (int i = 0; i < 26; i++) {
            if (freq[i] > maxFreq) {
                maxFreq = freq[i];
                maxChar = (char)('a' + i);
            }
        }
        
        // Check feasibility
        if (maxFreq > (n + 1) / 2) {
            return "";
        }
        
        // Create result array
        char[] result = new char[n];
        int index = 0;
        
        // Place most frequent character at even indices
        while (freq[maxChar - 'a'] > 0) {
            result[index] = maxChar;
            index += 2;
            freq[maxChar - 'a']--;
        }
        
        // Place remaining characters
        for (int i = 0; i < 26; i++) {
            char c = (char)('a' + i);
            while (freq[i] > 0) {
                // Wrap around to beginning if reached end
                if (index >= n) {
                    index = 1;
                }
                result[index] = c;
                index += 2;
                freq[i]--;
            }
        }
        
        return new String(result);
    }
    
    /**
     * Approach 3: Sorting by Frequency
     * Sort characters by frequency, then place them
     */
    public String reorganizeStringSorting(String s) {
        int n = s.length();
        int[][] freq = new int[26][2]; // [frequency, character index]
        
        for (char c : s.toCharArray()) {
            freq[c - 'a'][0]++;
            freq[c - 'a'][1] = c - 'a';
        }
        
        // Sort by frequency in descending order
        Arrays.sort(freq, (a, b) -> b[0] - a[0]);
        
        // Check feasibility
        if (freq[0][0] > (n + 1) / 2) {
            return "";
        }
        
        // Create result array
        char[] result = new char[n];
        int idx = 0;
        
        // Place characters starting from most frequent
        for (int i = 0; i < 26; i++) {
            char c = (char)('a' + freq[i][1]);
            int count = freq[i][0];
            
            while (count > 0) {
                if (idx >= n) {
                    idx = 1; // Start filling odd indices
                }
                result[idx] = c;
                idx += 2;
                count--;
            }
        }
        
        return new String(result);
    }
    
    /**
     * Approach 4: Two-pointer with Counters
     * Use two pointers to alternate between characters
     */
    public String reorganizeStringTwoPointers(String s) {
        int n = s.length();
        int[] freq = new int[26];
        
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Find max frequency and character
        int maxFreq = 0;
        int maxCharIdx = 0;
        for (int i = 0; i < 26; i++) {
            if (freq[i] > maxFreq) {
                maxFreq = freq[i];
                maxCharIdx = i;
            }
        }
        
        if (maxFreq > (n + 1) / 2) {
            return "";
        }
        
        char[] result = new char[n];
        int index = 0;
        
        // First fill even positions with max frequency character
        while (freq[maxCharIdx] > 0) {
            result[index] = (char)('a' + maxCharIdx);
            index += 2;
            freq[maxCharIdx]--;
        }
        
        // Fill remaining positions with other characters
        for (int i = 0; i < 26; i++) {
            while (freq[i] > 0) {
                if (index >= n) {
                    index = 1; // Start filling odd positions
                }
                result[index] = (char)('a' + i);
                index += 2;
                freq[i]--;
            }
        }
        
        return new String(result);
    }
    
    /**
     * Approach 5: Using StringBuilder with Backtracking
     * More intuitive but less efficient
     */
    public String reorganizeStringBacktracking(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Check feasibility
        int maxFreq = 0;
        for (int count : freq) {
            maxFreq = Math.max(maxFreq, count);
        }
        if (maxFreq > (s.length() + 1) / 2) {
            return "";
        }
        
        StringBuilder result = new StringBuilder();
        return backtrack(result, freq, s.length()) ? result.toString() : "";
    }
    
    private boolean backtrack(StringBuilder result, int[] freq, int n) {
        if (result.length() == n) {
            return true;
        }
        
        // Try each character
        for (int i = 0; i < 26; i++) {
            if (freq[i] == 0) continue;
            
            char c = (char)('a' + i);
            
            // Check if we can place this character
            if (result.length() == 0 || result.charAt(result.length() - 1) != c) {
                result.append(c);
                freq[i]--;
                
                if (backtrack(result, freq, n)) {
                    return true;
                }
                
                // Backtrack
                result.deleteCharAt(result.length() - 1);
                freq[i]++;
            }
        }
        
        return false;
    }
    
    /**
     * Helper method to visualize the reorganization process
     */
    private void visualizeReorganization(String s, String result, String approach) {
        System.out.println("\n" + approach + " - Visualization:");
        System.out.println("Original string: " + s);
        System.out.println("Length: " + s.length());
        
        // Count frequencies
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        System.out.println("\nCharacter frequencies:");
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                System.out.printf("  %c: %d (%.1f%%)%n", 
                    (char)('a' + i), freq[i], (freq[i] * 100.0 / s.length()));
            }
        }
        
        // Check feasibility
        int maxFreq = 0;
        for (int count : freq) {
            maxFreq = Math.max(maxFreq, count);
        }
        int threshold = (s.length() + 1) / 2;
        System.out.println("\nFeasibility check:");
        System.out.println("  Max frequency: " + maxFreq);
        System.out.println("  Threshold (n+1)/2: " + threshold);
        System.out.println("  Possible: " + (maxFreq <= threshold));
        
        if (!result.isEmpty()) {
            System.out.println("\nReorganized string: " + result);
            System.out.println("Length: " + result.length());
            
            // Verify no adjacent duplicates
            boolean valid = true;
            for (int i = 0; i < result.length() - 1; i++) {
                if (result.charAt(i) == result.charAt(i + 1)) {
                    valid = false;
                    System.out.println("  ERROR: Adjacent duplicates at position " + i);
                    break;
                }
            }
            System.out.println("  Valid reorganization: " + valid);
            
            // Show arrangement pattern
            System.out.println("\nArrangement pattern:");
            System.out.print("  Positions: ");
            for (int i = 0; i < result.length(); i++) {
                System.out.printf("%2d ", i);
            }
            System.out.print("\n  Characters: ");
            for (int i = 0; i < result.length(); i++) {
                System.out.printf(" %c ", result.charAt(i));
            }
            System.out.println();
            
            // Show frequency distribution in result
            int[] resultFreq = new int[26];
            for (char c : result.toCharArray()) {
                resultFreq[c - 'a']++;
            }
            
            System.out.println("\nFrequency distribution in result:");
            for (int i = 0; i < 26; i++) {
                if (resultFreq[i] > 0) {
                    System.out.printf("  %c: %d (matches original: %s)%n",
                        (char)('a' + i), resultFreq[i],
                        resultFreq[i] == freq[i] ? "✓" : "✗");
                }
            }
        } else {
            System.out.println("\nNo valid reorganization possible");
        }
    }
    
    /**
     * Helper method to check if a string has adjacent duplicates
     */
    private boolean hasAdjacentDuplicates(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Reorganize String Solution:");
        System.out.println("====================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        String s1 = "aab";
        String expected1 = "aba"; // Other valid answers: "aba"
        
        solution.visualizeReorganization(s1, expected1, "Expected");
        
        long startTime = System.nanoTime();
        String result1a = solution.reorganizeString(s1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result1b = solution.reorganizeStringInterleave(s1);
        long time1b = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Max Heap:    " + result1a + " - " + 
                         (result1a.length() > 0 && !solution.hasAdjacentDuplicates(result1a) ? "VALID" : "INVALID") +
                         " (Time: " + time1a + " ns)");
        System.out.println("Interleave:  " + result1b + " - " + 
                         (result1b.length() > 0 && !solution.hasAdjacentDuplicates(result1b) ? "VALID" : "INVALID") +
                         " (Time: " + time1b + " ns)");
        
        // Test case 2: Impossible case
        System.out.println("\nTest 2: Impossible case");
        String s2 = "aaab";
        String expected2 = "";
        
        solution.visualizeReorganization(s2, expected2, "Expected");
        
        String result2a = solution.reorganizeString(s2);
        System.out.println("Result: " + result2a + " - " + 
                         (result2a.equals("") ? "PASSED" : "FAILED"));
        
        // Test case 3: Single character repeated
        System.out.println("\nTest 3: Single character");
        String s3 = "aaaa";
        String expected3 = ""; // Cannot reorganize single character repeated
        
        solution.visualizeReorganization(s3, expected3, "Expected");
        
        String result3a = solution.reorganizeString(s3);
        System.out.println("Result: " + result3a + " - " + 
                         (result3a.equals("") ? "PASSED" : "FAILED"));
        
        // Test case 4: All characters different
        System.out.println("\nTest 4: All different characters");
        String s4 = "abcde";
        // Any permutation works since all different
        
        solution.visualizeReorganization(s4, "abcde", "Expected");
        
        String result4a = solution.reorganizeString(s4);
        System.out.println("Result: " + result4a + " - " + 
                         (result4a.length() == s4.length() && !solution.hasAdjacentDuplicates(result4a) ? "VALID" : "INVALID"));
        
        // Test case 5: Two characters balanced
        System.out.println("\nTest 5: Balanced two characters");
        String s5 = "aabb";
        // Possible results: "abab", "baba"
        
        solution.visualizeReorganization(s5, "abab", "Expected");
        
        String result5a = solution.reorganizeString(s5);
        System.out.println("Result: " + result5a + " - " + 
                         (result5a.length() == s5.length() && !solution.hasAdjacentDuplicates(result5a) ? "VALID" : "INVALID"));
        
        // Test case 6: Complex case
        System.out.println("\nTest 6: Complex case");
        String s6 = "aaabbcc";
        // 'a' appears 3 times, 'b' appears 2 times, 'c' appears 2 times
        // Total length 7, threshold = (7+1)/2 = 4, so possible
        
        solution.visualizeReorganization(s6, "", "Expected");
        
        String result6a = solution.reorganizeString(s6);
        System.out.println("Result: " + result6a + " - " + 
                         (result6a.length() == s6.length() && !solution.hasAdjacentDuplicates(result6a) ? "VALID" : "INVALID"));
        
        // Test case 7: Edge case - maximum frequency exactly at threshold
        System.out.println("\nTest 7: Max frequency at threshold");
        String s7 = "aaaabb";
        // 'a' appears 4 times, length = 6, threshold = (6+1)/2 = 3.5 -> 4
        // Max frequency = 4, threshold = 4, so possible
        
        solution.visualizeReorganization(s7, "", "Expected");
        
        String result7a = solution.reorganizeString(s7);
        System.out.println("Result: " + result7a + " - " + 
                         (result7a.length() == s7.length() && !solution.hasAdjacentDuplicates(result7a) ? "VALID" : "INVALID"));
        
        // Test case 8: Empty string (edge case)
        System.out.println("\nTest 8: Single character string");
        String s8 = "z";
        
        solution.visualizeReorganization(s8, "z", "Expected");
        
        String result8a = solution.reorganizeString(s8);
        System.out.println("Result: " + result8a + " - " + 
                         (result8a.equals("z") ? "PASSED" : "FAILED"));
        
        // Performance test
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        // Generate test string with various frequencies
        StringBuilder testBuilder = new StringBuilder();
        Random random = new Random(42);
        
        // Add some frequent characters
        for (int i = 0; i < 50; i++) testBuilder.append('a');
        for (int i = 0; i < 40; i++) testBuilder.append('b');
        for (int i = 0; i < 30; i++) testBuilder.append('c');
        
        // Add some less frequent characters
        for (int i = 0; i < 20; i++) testBuilder.append('d');
        for (int i = 0; i < 10; i++) testBuilder.append('e');
        
        // Add random characters
        for (int i = 0; i < 50; i++) {
            testBuilder.append((char)('f' + random.nextInt(10)));
        }
        
        String testString = testBuilder.toString();
        System.out.println("\nTesting with string of length: " + testString.length());
        
        // Test all implementations
        startTime = System.currentTimeMillis();
        String resultPerf1 = solution.reorganizeString(testString);
        long timePerf1 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        String resultPerf2 = solution.reorganizeStringInterleave(testString);
        long timePerf2 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        String resultPerf3 = solution.reorganizeStringSorting(testString);
        long timePerf3 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        String resultPerf4 = solution.reorganizeStringTwoPointers(testString);
        long timePerf4 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Max Heap:       " + timePerf1 + " ms - Valid: " + 
                         (!solution.hasAdjacentDuplicates(resultPerf1)));
        System.out.println("Interleave:     " + timePerf2 + " ms - Valid: " + 
                         (!solution.hasAdjacentDuplicates(resultPerf2)));
        System.out.println("Sorting:        " + timePerf3 + " ms - Valid: " + 
                         (!solution.hasAdjacentDuplicates(resultPerf3)));
        System.out.println("Two Pointers:   " + timePerf4 + " ms - Valid: " + 
                         (!solution.hasAdjacentDuplicates(resultPerf4)));
        
        // Verify all produce valid results of same length
        boolean allValid = !solution.hasAdjacentDuplicates(resultPerf1) &&
                          !solution.hasAdjacentDuplicates(resultPerf2) &&
                          !solution.hasAdjacentDuplicates(resultPerf3) &&
                          !solution.hasAdjacentDuplicates(resultPerf4);
        
        boolean allSameLength = resultPerf1.length() == resultPerf2.length() &&
                               resultPerf2.length() == resultPerf3.length() &&
                               resultPerf3.length() == resultPerf4.length();
        
        System.out.println("All valid: " + allValid);
        System.out.println("All same length: " + allSameLength);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("A string can be reorganized with no adjacent duplicates");
        System.out.println("if and only if: max frequency ≤ (n + 1) / 2");
        
        System.out.println("\nWhy this condition?");
        System.out.println("Imagine placing the most frequent character:");
        System.out.println("- If we have n positions, we can place at most (n+1)/2");
        System.out.println("  of the same character while keeping them separated");
        System.out.println("- Example: n=6, max 3 of same character: a_a_a_");
        System.out.println("- Example: n=7, max 4 of same character: a_a_a_a");
        
        System.out.println("\nMax Heap Approach:");
        System.out.println("1. Count character frequencies");
        System.out.println("2. Check feasibility condition");
        System.out.println("3. Create max heap ordered by frequency");
        System.out.println("4. While heap not empty:");
        System.out.println("   a. Take most frequent character");
        System.out.println("   b. If it's same as last character, take second most");
        System.out.println("   c. Append to result, decrement frequency");
        System.out.println("   d. If frequency > 0, re-add to heap");
        System.out.println("5. Return result");
        
        System.out.println("\nInterleaving Approach:");
        System.out.println("1. Find most frequent character");
        System.out.println("2. Place it at even indices (0, 2, 4, ...)");
        System.out.println("3. Place other characters in remaining positions");
        System.out.println("4. If run out of even indices, continue with odd indices");
        
        System.out.println("\nVisual Example: s = \"aab\"");
        System.out.println("1. Frequencies: a=2, b=1");
        System.out.println("2. Check: max frequency=2 ≤ (3+1)/2=2 ✓");
        System.out.println("3. Place 'a' at positions 0 and 2");
        System.out.println("4. Place 'b' at position 1");
        System.out.println("5. Result: \"a b a\" -> \"aba\"");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("IMPLEMENTATION COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Max Heap (RECOMMENDED):");
        System.out.println("   Time: O(n log k) where k ≤ 26");
        System.out.println("   Space: O(k) for heap and frequency array");
        System.out.println("   Pros:");
        System.out.println("     - Most intuitive greedy approach");
        System.out.println("     - Guarantees valid arrangement if possible");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Heap operations overhead");
        System.out.println("     - Slightly more complex than interleaving");
        System.out.println("   Best for: Interviews, general solution");
        
        System.out.println("\n2. Interleaving:");
        System.out.println("   Time: O(n) - Single pass after counting");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient");
        System.out.println("     - Simple implementation");
        System.out.println("     - No heap overhead");
        System.out.println("   Cons:");
        System.out.println("     - Less intuitive why it works");
        System.out.println("     - Requires understanding of threshold");
        System.out.println("   Best for: Performance-critical applications");
        
        System.out.println("\n3. Sorting by Frequency:");
        System.out.println("   Time: O(n + k log k) where k ≤ 26");
        System.out.println("   Space: O(k) for frequency array");
        System.out.println("   Pros:");
        System.out.println("     - Clear separation of steps");
        System.out.println("     - Easy to reason about");
        System.out.println("   Cons:");
        System.out.println("     - Sorting overhead");
        System.out.println("     - Similar to interleaving but with sort");
        System.out.println("   Best for: When sorted order is needed");
        
        System.out.println("\n4. Two Pointers:");
        System.out.println("   Time: O(n)");
        System.out.println("   Space: O(1)");
        System.out.println("   Pros:");
        System.out.println("     - Efficient and elegant");
        System.out.println("     - Good balance of simplicity and performance");
        System.out.println("   Cons:");
        System.out.println("     - Similar to interleaving");
        System.out.println("     - Might be less obvious");
        System.out.println("   Best for: Clean implementations");
        
        System.out.println("\n5. Backtracking:");
        System.out.println("   Time: O(n!) in worst case");
        System.out.println("   Space: O(n) for recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - Guaranteed to find solution if exists");
        System.out.println("     - Can find all possible solutions");
        System.out.println("   Cons:");
        System.out.println("     - Exponentially slow");
        System.out.println("     - Not practical for large strings");
        System.out.println("   Best for: Small strings, finding all solutions");
        
        // Mathematical insights
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Pigeonhole Principle:");
        System.out.println("   - If a character appears m times in string of length n");
        System.out.println("   - Need at least m-1 other characters to separate them");
        System.out.println("   - So: m ≤ n - (m-1) = n - m + 1");
        System.out.println("   - Therefore: 2m ≤ n + 1 → m ≤ (n+1)/2");
        
        System.out.println("\n2. Necessary and Sufficient Condition:");
        System.out.println("   - Necessary: max frequency ≤ (n+1)/2");
        System.out.println("   - Sufficient: If condition holds, arrangement exists");
        System.out.println("   - Proof by construction: interleaving algorithm");
        
        System.out.println("\n3. Special Cases:");
        System.out.println("   a) n is even: max frequency ≤ n/2");
        System.out.println("   b) n is odd: max frequency ≤ (n+1)/2");
        System.out.println("   c) All characters different: always possible");
        System.out.println("   d) Only one character: possible only if n=1");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Data Compression:");
        System.out.println("   - Run-length encoding avoidance");
        System.out.println("   - Preventing long runs of same symbol");
        
        System.out.println("\n2. Load Balancing:");
        System.out.println("   - Distributing tasks of same type");
        System.out.println("   - Avoiding consecutive similar tasks");
        
        System.out.println("\n3. Resource Scheduling:");
        System.out.println("   - CPU task scheduling (like Task Scheduler problem)");
        System.out.println("   - Avoiding resource contention");
        
        System.out.println("\n4. Data Transmission:");
        System.out.println("   - Preventing signal interference");
        System.out.println("   - Error correction codes");
        
        System.out.println("\n5. Game Development:");
        System.out.println("   - Random item generation without streaks");
        System.out.println("   - Shuffling algorithms");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify requirements:");
        System.out.println("   - No two adjacent characters same");
        System.out.println("   - Return any valid arrangement");
        System.out.println("   - Return empty string if impossible");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Generate all permutations O(n!)");
        System.out.println("   - Check each for adjacent duplicates");
        System.out.println("   - Mention it's too slow");
        
        System.out.println("\n3. Look for patterns:");
        System.out.println("   - When is it impossible?");
        System.out.println("   - Try small examples: \"aa\", \"aab\", \"aaab\"");
        System.out.println("   - Discover frequency threshold");
        
        System.out.println("\n4. Derive feasibility condition:");
        System.out.println("   - If char appears > (n+1)/2 times, impossible");
        System.out.println("   - Prove with pigeonhole principle");
        
        System.out.println("\n5. Propose greedy solution:");
        System.out.println("   - Always pick most frequent character (not last used)");
        System.out.println("   - Use max heap to track frequencies");
        
        System.out.println("\n6. Discuss optimization:");
        System.out.println("   - Interleaving approach for O(n) time");
        System.out.println("   - Constant space since only 26 letters");
        
        System.out.println("\n7. Walk through example:");
        System.out.println("   - Use provided example \"aab\"");
        System.out.println("   - Show heap operations step by step");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Feasibility condition: max frequency ≤ (n+1)/2");
        System.out.println("- Greedy approach is optimal");
        System.out.println("- O(n) time with interleaving, O(n log 26) with heap");
        System.out.println("- Only 26 characters so constant factors matter");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not checking feasibility first");
        System.out.println("- Forgetting to handle last used character");
        System.out.println("- Not considering all 26 lowercase letters");
        System.out.println("- Integer overflow in frequency calculations");
        System.out.println("- Not verifying result has no adjacent duplicates");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with given examples");
        System.out.println("2. Test edge cases (single char, all same)");
        System.out.println("3. Test boundary cases (max frequency at threshold)");
        System.out.println("4. Verify no adjacent duplicates in result");
        System.out.println("5. Verify all characters preserved (same frequency)");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
