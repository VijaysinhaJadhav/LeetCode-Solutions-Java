
# Solution.java

```java
import java.util.*;

/**
 * 387. First Unique Character in a String
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Find the first non-repeating character in a string and return its index.
 * Return -1 if none exists.
 * 
 * Key Insights:
 * 1. Count frequency of each character
 * 2. Scan string again to find first character with count = 1
 * 3. Since only lowercase letters, use array of size 26
 */
class Solution {
    
    /**
     * Approach 1: Two-Pass with Frequency Array (Recommended)
     * Time: O(n), Space: O(1)
     * 
     * Steps:
     * 1. Count frequency of each character using array of size 26
     * 2. Scan string again to find first character with count = 1
     * 3. Return its index or -1 if not found
     */
    public int firstUniqChar(String s) {
        int[] freq = new int[26];
        
        // Count frequencies
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Find first character with frequency 1
        for (int i = 0; i < s.length(); i++) {
            if (freq[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 2: Two-Pass with HashMap
     * Time: O(n), Space: O(k) where k = unique characters
     * 
     * More flexible for larger character sets
     */
    public int firstUniqCharHashMap(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        
        // Count frequencies
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        
        // Find first unique
        for (int i = 0; i < s.length(); i++) {
            if (freq.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 3: Using indexOf and lastIndexOf
     * Time: O(n²) worst case, Space: O(1)
     * 
     * Simple but less efficient
     */
    public int firstUniqCharIndexOf(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (s.indexOf(c) == s.lastIndexOf(c)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Approach 4: One-Pass with Queue and Frequency Map
     * Time: O(n), Space: O(k)
     * 
     * Single pass using queue to track order
     */
    public int firstUniqCharQueue(String s) {
        int[] freq = new int[26];
        Queue<Integer> queue = new LinkedList<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int idx = c - 'a';
            freq[idx]++;
            queue.offer(i);
            
            // Remove characters from queue that have frequency > 1
            while (!queue.isEmpty() && freq[s.charAt(queue.peek()) - 'a'] > 1) {
                queue.poll();
            }
        }
        
        return queue.isEmpty() ? -1 : queue.peek();
    }
    
    /**
     * Approach 5: Single Array for First and Last Occurrence
     * Time: O(n), Space: O(1)
     * 
     * Track first occurrence and count using two arrays
     */
    public int firstUniqCharFirstLast(String s) {
        int[] firstIndex = new int[26];
        int[] count = new int[26];
        Arrays.fill(firstIndex, -1);
        
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            count[idx]++;
            if (firstIndex[idx] == -1) {
                firstIndex[idx] = i;
            }
        }
        
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            if (count[i] == 1) {
                result = Math.min(result, firstIndex[i]);
            }
        }
        
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    /**
     * Approach 6: Using Java 8 Streams (Functional)
     * Time: O(n), Space: O(k)
     * 
     * Modern Java approach
     */
    public int firstUniqCharStream(String s) {
        Map<Character, Long> freq = s.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        
        return IntStream.range(0, s.length())
            .filter(i -> freq.get(s.charAt(i)) == 1)
            .findFirst()
            .orElse(-1);
    }
    
    /**
     * Helper: Visualize the process
     */
    public void visualizeProcess(String s) {
        System.out.println("\nFirst Unique Character Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nInput: \"" + s + "\"");
        System.out.println("Length: " + s.length());
        
        // Count frequencies
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        System.out.println("\nFrequency count:");
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                char c = (char) ('a' + i);
                System.out.printf("  '%c' : %d%n", c, freq[i]);
            }
        }
        
        System.out.println("\nScanning for first unique character:");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            System.out.printf("  Index %d: '%c' (frequency: %d) → ", i, c, freq[c - 'a']);
            if (freq[c - 'a'] == 1) {
                System.out.printf("✓ UNIQUE - Returning %d%n", i);
                int result = firstUniqChar(s);
                System.out.println("\nResult: " + result);
                return;
            } else {
                System.out.println("✗ repeating");
            }
        }
        
        System.out.println("\nNo unique character found → Returning -1");
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[] {
            "leetcode",           // Example 1 → 0
            "loveleetcode",       // Example 2 → 2
            "aabb",               // Example 3 → -1
            "a",                  // Single character → 0
            "aa",                 // All repeating → -1
            "ab",                 // Two unique → 0
            "aab",                // First repeating, second unique → 2
            "abca",               // Character repeats later → 1
            "z",                  // Single letter → 0
            "abcde",              // All unique → 0
            "aabbccddeeffg",      // Only last unique → 12
            "aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz" // Many characters
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.printf("\nTest %d: \"%s\"%n", i + 1, s);
            
            int result1 = firstUniqChar(s);
            int result2 = firstUniqCharHashMap(s);
            int result3 = firstUniqCharIndexOf(s);
            int result4 = firstUniqCharQueue(s);
            int result5 = firstUniqCharFirstLast(s);
            int result6 = firstUniqCharStream(s);
            
            boolean allMatch = result1 == result2 && result2 == result3 &&
                              result3 == result4 && result4 == result5 &&
                              result5 == result6;
            
            if (allMatch) {
                System.out.println("✓ PASS - First unique index: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Methods disagree:");
                System.out.println("  Method 1 (Array): " + result1);
                System.out.println("  Method 2 (HashMap): " + result2);
                System.out.println("  Method 3 (indexOf): " + result3);
                System.out.println("  Method 4 (Queue): " + result4);
                System.out.println("  Method 5 (FirstLast): " + result5);
                System.out.println("  Method 6 (Stream): " + result6);
            }
            
            // Visualize first few test cases
            if (i < 3) {
                visualizeProcess(s);
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
        for (int i = 0; i < 50000; i++) {
            sb.append('a');
        }
        sb.append('b');
        String largeString = sb.toString();
        
        System.out.println("Test Setup: " + largeString.length() + " characters");
        
        long[] times = new long[6];
        int[] results = new int[6];
        
        // Method 1: Frequency Array
        long start = System.currentTimeMillis();
        results[0] = firstUniqChar(largeString);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: HashMap
        start = System.currentTimeMillis();
        results[1] = firstUniqCharHashMap(largeString);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: indexOf
        start = System.currentTimeMillis();
        results[2] = firstUniqCharIndexOf(largeString);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Queue
        start = System.currentTimeMillis();
        results[3] = firstUniqCharQueue(largeString);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: First/Last Occurrence
        start = System.currentTimeMillis();
        results[4] = firstUniqCharFirstLast(largeString);
        times[4] = System.currentTimeMillis() - start;
        
        // Method 6: Stream
        start = System.currentTimeMillis();
        results[5] = firstUniqCharStream(largeString);
        times[5] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Frequency Array        | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. HashMap                | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. indexOf/lastIndexOf    | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. Queue                  | %9d | %6d%n", times[3], results[3]);
        System.out.printf("5. First/Last Occurrence  | %9d | %6d%n", times[4], results[4]);
        System.out.printf("6. Stream                 | %9d | %6d%n", times[5], results[5]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3] && results[3] == results[4] &&
                          results[4] == results[5];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Frequency array is fastest due to direct indexing");
        System.out.println("2. HashMap has slight overhead for boxing/unboxing");
        System.out.println("3. indexOf approach is O(n²) and significantly slower");
        System.out.println("4. Queue approach is comparable to two-pass");
        System.out.println("5. Stream approach has functional overhead");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single character:");
        System.out.println("   Input: \"a\"");
        System.out.println("   Output: " + firstUniqChar("a"));
        
        System.out.println("\n2. All repeating:");
        System.out.println("   Input: \"aa\"");
        System.out.println("   Output: " + firstUniqChar("aa"));
        
        System.out.println("\n3. Two unique characters:");
        System.out.println("   Input: \"ab\"");
        System.out.println("   Output: " + firstUniqChar("ab"));
        
        System.out.println("\n4. First character repeats later:");
        System.out.println("   Input: \"abca\"");
        System.out.println("   Output: " + firstUniqChar("abca"));
        
        System.out.println("\n5. Last character is unique:");
        System.out.println("   Input: \"aabbc\"");
        System.out.println("   Output: " + firstUniqChar("aabbc"));
        
        System.out.println("\n6. Long string with unique at end:");
        String longString = "a".repeat(100000) + "b";
        long start = System.currentTimeMillis();
        int result = firstUniqChar(longString);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 'a'×100000 + 'b'");
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nProblem: Find first character that appears exactly once.");
        
        System.out.println("\nKey Insight:");
        System.out.println("We need both frequency information AND order information.");
        System.out.println("Solution: Two-pass approach");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. First pass: Count frequency of each character");
        System.out.println("2. Second pass: Scan string in order");
        System.out.println("3. Return index of first character with frequency = 1");
        
        System.out.println("\nExample: s = \"loveleetcode\"");
        System.out.println("  Step 1: Count frequencies:");
        System.out.println("    l:1, o:2, v:1, e:3, t:1, c:1, d:1");
        System.out.println("  Step 2: Scan from left:");
        System.out.println("    index 0: 'l' (count=1) → FOUND → return 0");
        
        System.out.println("\nComplexity:");
        System.out.println("- Time: O(n) where n = string length");
        System.out.println("- Space: O(1) constant (26 characters)");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Character set? (lowercase English letters)");
        System.out.println("   - Empty string? (not possible, length ≥ 1)");
        System.out.println("   - Return index or character? (index)");
        
        System.out.println("\n2. Start with frequency counting:");
        System.out.println("   - Show first pass to count occurrences");
        System.out.println("   - Explain why two passes are needed");
        
        System.out.println("\n3. Discuss optimization:");
        System.out.println("   - Use array of size 26 for O(1) space");
        System.out.println("   - Single pass with queue (more complex)");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - Time: O(n) - must examine each character");
        System.out.println("   - Space: O(1) - fixed size array");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - Single character");
        System.out.println("   - All characters repeat");
        System.out.println("   - Unique character at end");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Not handling case where no unique character exists");
        System.out.println("   - Using indexOf/lastIndexOf (O(n²))");
        System.out.println("   - Forgetting to convert char to array index");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("387. First Unique Character in a String");
        System.out.println("========================================");
        
        // Explain algorithm
        solution.explainAlgorithm();
        
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
    public int firstUniqChar(String s) {
        int[] freq = new int[26];
        
        // Count frequencies
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Find first unique
        for (int i = 0; i < s.length(); i++) {
            if (freq[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        
        return -1;
    }
}
            """);
        
        System.out.println("\nAlternative (HashMap for any character set):");
        System.out.println("""
class Solution {
    public int firstUniqChar(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        
        for (int i = 0; i < s.length(); i++) {
            if (freq.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        
        return -1;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Two-pass approach is optimal: O(n) time, O(1) space");
        System.out.println("2. Array of size 26 is most efficient for lowercase letters");
        System.out.println("3. HashMap is more flexible for larger character sets");
        System.out.println("4. Must handle case where no unique character exists");
        System.out.println("5. IndexOf/lastIndexOf is O(n²) and should be avoided");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - two passes through string");
        System.out.println("- Space: O(1) - fixed array of size 26");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. What if the string contains Unicode characters? (Use HashMap)");
        System.out.println("2. How would you find the first repeating character?");
        System.out.println("3. How would you find all unique characters?");
        System.out.println("4. How would you find the last unique character?");
    }
}
