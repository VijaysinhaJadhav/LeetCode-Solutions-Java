/**
 * 3. Longest Substring Without Repeating Characters
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a string s, find the length of the longest substring without repeating characters.
 * 
 * Key Insights:
 * 1. Use sliding window technique with two pointers (left and right)
 * 2. Maintain a hash map to store the most recent index of each character
 * 3. When duplicate is found, move left pointer to max(left, last occurrence + 1)
 * 4. Update maximum length at each step
 * 5. Handle empty string and single character cases
 * 
 * Approach (Sliding Window with HashMap):
 * 1. Initialize left pointer, maxLength, and character map
 * 2. Iterate right pointer through the string
 * 3. If character exists in map and its index >= left, move left pointer
 * 4. Update character's latest index in map
 * 5. Update maxLength with current window size
 * 6. Return maxLength
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(min(m, n)) where m is character set size
 * 
 * Tags: Hash Table, String, Sliding Window
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Sliding Window with HashMap - RECOMMENDED
     * O(n) time, O(min(m, n)) space - Optimal solution
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If character exists in current window, move left pointer
            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= left) {
                left = charIndexMap.get(currentChar) + 1;
            }
            
            // Update character's latest index
            charIndexMap.put(currentChar, right);
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Approach 2: Sliding Window with Character Set
     * O(n) time, O(min(m, n)) space - Alternative using HashSet
     */
    public int lengthOfLongestSubstringHashSet(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        Set<Character> charSet = new HashSet<>();
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // Remove characters from left until no duplicate
            while (charSet.contains(currentChar)) {
                charSet.remove(s.charAt(left));
                left++;
            }
            
            // Add current character to set
            charSet.add(currentChar);
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Approach 3: Sliding Window with Integer Array (Optimized for ASCII)
     * O(n) time, O(1) space - Uses fixed size array for ASCII characters
     */
    public int lengthOfLongestSubstringArray(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        // Assuming extended ASCII (256 characters)
        int[] charIndex = new int[256];
        Arrays.fill(charIndex, -1);
        
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If character exists in current window, move left pointer
            if (charIndex[currentChar] >= left) {
                left = charIndex[currentChar] + 1;
            }
            
            // Update character's latest index
            charIndex[currentChar] = right;
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Approach 4: Brute Force (For Comparison)
     * O(n^3) time, O(min(m, n)) space - Not recommended for large inputs
     */
    public int lengthOfLongestSubstringBruteForce(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int maxLength = 0;
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (allUnique(s, i, j)) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        
        return maxLength;
    }
    
    private boolean allUnique(String s, int start, int end) {
        Set<Character> charSet = new HashSet<>();
        for (int i = start; i <= end; i++) {
            char currentChar = s.charAt(i);
            if (charSet.contains(currentChar)) {
                return false;
            }
            charSet.add(currentChar);
        }
        return true;
    }
    
    /**
     * Approach 5: Sliding Window with LinkedHashMap (Maintains Order)
     * O(n) time, O(min(m, n)) space - Maintains insertion order
     */
    public int lengthOfLongestSubstringLinkedHashMap(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        Map<Character, Integer> charIndexMap = new LinkedHashMap<>();
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If character exists, remove all characters up to and including the duplicate
            if (charIndexMap.containsKey(currentChar)) {
                int duplicateIndex = charIndexMap.get(currentChar);
                
                // Remove all characters before and including the duplicate
                Iterator<Map.Entry<Character, Integer>> iterator = charIndexMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Character, Integer> entry = iterator.next();
                    if (entry.getValue() <= duplicateIndex) {
                        iterator.remove();
                    } else {
                        break;
                    }
                }
                
                left = duplicateIndex + 1;
            }
            
            // Add current character
            charIndexMap.put(currentChar, right);
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Helper method to visualize the sliding window algorithm
     */
    private void visualizeSlidingWindow(String s) {
        System.out.println("\nSliding Window Algorithm Visualization:");
        System.out.println("Input: \"" + s + "\"");
        
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0;
        int left = 0;
        
        System.out.println("\nStep | Right | Char | Left | Window | Max Length | Action");
        System.out.println("-----|-------|------|------|--------|------------|--------");
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            String action = "Add character";
            String window = s.substring(left, right + 1);
            
            // If character exists in current window, move left pointer
            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= left) {
                left = charIndexMap.get(currentChar) + 1;
                action = "Move left to " + left + " (duplicate '" + currentChar + "')";
                window = s.substring(left, right + 1);
            }
            
            // Update character's latest index
            charIndexMap.put(currentChar, right);
            
            // Update maximum length
            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
                action += " - New max!";
            }
            
            System.out.printf("%4d | %5d | %4c | %4d | %-6s | %10d | %s%n",
                            right + 1, right, currentChar, left, window, maxLength, action);
        }
        
        System.out.println("\nFinal Result: Maximum Length = " + maxLength);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Longest Substring Without Repeating Characters:");
        System.out.println("======================================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        String s1 = "abcabcbb";
        int expected1 = 3;
        
        long startTime = System.nanoTime();
        int result1a = solution.lengthOfLongestSubstring(s1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.lengthOfLongestSubstringHashSet(s1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.lengthOfLongestSubstringArray(s1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.lengthOfLongestSubstringBruteForce(s1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.lengthOfLongestSubstringLinkedHashMap(s1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        boolean test1e = result1e == expected1;
        
        System.out.println("HashMap: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("HashSet: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Array: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Brute Force: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("LinkedHashMap: " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the sliding window algorithm
        solution.visualizeSlidingWindow(s1);
        
        // Test case 2: All same characters
        System.out.println("\nTest 2: All same characters");
        String s2 = "bbbbb";
        int expected2 = 1;
        
        int result2a = solution.lengthOfLongestSubstring(s2);
        System.out.println("All same characters: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Mixed characters with duplicates
        System.out.println("\nTest 3: Mixed characters with duplicates");
        String s3 = "pwwkew";
        int expected3 = 3;
        
        int result3a = solution.lengthOfLongestSubstring(s3);
        System.out.println("Mixed characters: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Empty string
        System.out.println("\nTest 4: Empty string");
        String s4 = "";
        int expected4 = 0;
        
        int result4a = solution.lengthOfLongestSubstring(s4);
        System.out.println("Empty string: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single character
        System.out.println("\nTest 5: Single character");
        String s5 = "a";
        int expected5 = 1;
        
        int result5a = solution.lengthOfLongestSubstring(s5);
        System.out.println("Single character: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: No duplicates
        System.out.println("\nTest 6: No duplicates");
        String s6 = "abcdef";
        int expected6 = 6;
        
        int result6a = solution.lengthOfLongestSubstring(s6);
        System.out.println("No duplicates: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Substring in middle
        System.out.println("\nTest 7: Substring in middle");
        String s7 = "dvdf";
        int expected7 = 3;
        
        int result7a = solution.lengthOfLongestSubstring(s7);
        System.out.println("Substring in middle: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Complex pattern
        System.out.println("\nTest 8: Complex pattern");
        String s8 = "abcdeafbdgcbb";
        int expected8 = 7; // "eafbdgc"
        
        int result8a = solution.lengthOfLongestSubstring(s8);
        System.out.println("Complex pattern: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  HashMap: " + time1a + " ns");
        System.out.println("  HashSet: " + time1b + " ns");
        System.out.println("  Array: " + time1c + " ns");
        System.out.println("  Brute Force: " + time1d + " ns");
        System.out.println("  LinkedHashMap: " + time1e + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        StringBuilder largeString = new StringBuilder();
        Random random = new Random(42);
        for (int i = 0; i < 10000; i++) {
            largeString.append((char) ('a' + random.nextInt(26)));
        }
        String s10 = largeString.toString();
        
        startTime = System.nanoTime();
        int result10a = solution.lengthOfLongestSubstring(s10);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10b = solution.lengthOfLongestSubstringHashSet(s10);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10c = solution.lengthOfLongestSubstringArray(s10);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (10,000 characters):");
        System.out.println("  HashMap: " + time10a + " ns, Result: " + result10a);
        System.out.println("  HashSet: " + time10b + " ns, Result: " + result10b);
        System.out.println("  Array: " + time10c + " ns, Result: " + result10c);
        
        // Verify all approaches produce the same result
        boolean allEqual = result10a == result10b && result10a == result10c;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SLIDING WINDOW ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Maintain a window [left, right] of unique characters.");
        System.out.println("When we encounter a duplicate character, we move the left pointer");
        System.out.println("to the position after the last occurrence of that character.");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. We expand the window by moving the right pointer");
        System.out.println("2. When we find a duplicate, we contract the window from the left");
        System.out.println("3. The hash map stores the most recent index of each character");
        System.out.println("4. This ensures we always have a window of unique characters");
        System.out.println("5. We track the maximum window size encountered");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Sliding Window with HashMap (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through string");
        System.out.println("   Space: O(min(m, n)) - Where m is character set size");
        System.out.println("   How it works:");
        System.out.println("     - Use two pointers (left and right) to define window");
        System.out.println("     - Store character indices in HashMap for O(1) lookups");
        System.out.println("     - When duplicate found, jump left pointer to last occurrence + 1");
        System.out.println("     - Track maximum window size");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time complexity");
        System.out.println("     - Handles all character types");
        System.out.println("     - Efficient duplicate handling with direct jumps");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(min(m, n)) extra space");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Sliding Window with HashSet:");
        System.out.println("   Time: O(2n) = O(n) - Each character visited at most twice");
        System.out.println("   Space: O(min(m, n)) - HashSet storage");
        System.out.println("   How it works:");
        System.out.println("     - Use HashSet to track characters in current window");
        System.out.println("     - When duplicate found, remove characters from left until no duplicate");
        System.out.println("     - Expand window from right, contract from left");
        System.out.println("   Pros:");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - May require multiple left pointer moves for each duplicate");
        System.out.println("     - Less efficient than HashMap for some cases");
        System.out.println("   Best for: Learning sliding window concept");
        
        System.out.println("\n3. Sliding Window with Array (Optimized for ASCII):");
        System.out.println("   Time: O(n) - Single pass through string");
        System.out.println("   Space: O(1) - Fixed size array (128 or 256)");
        System.out.println("   How it works:");
        System.out.println("     - Use integer array instead of HashMap for ASCII characters");
        System.out.println("     - Array index represents character ASCII value");
        System.out.println("     - Same logic as HashMap approach but with array");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient for ASCII characters");
        System.out.println("     - O(1) space for fixed character set");
        System.out.println("     - Faster than HashMap for small character sets");
        System.out.println("   Cons:");
        System.out.println("     - Only works for limited character sets (ASCII)");
        System.out.println("     - Wastes space for Unicode strings");
        System.out.println("   Best for: ASCII-only strings, performance-critical code");
        
        System.out.println("\n4. Brute Force:");
        System.out.println("   Time: O(n^3) - Check all substrings for uniqueness");
        System.out.println("   Space: O(min(m, n)) - HashSet for checking uniqueness");
        System.out.println("   How it works:");
        System.out.println("     - Check all possible substrings");
        System.out.println("     - For each substring, check if all characters are unique");
        System.out.println("     - Track maximum length of unique substrings");
        System.out.println("   Pros:");
        System.out.println("     - Simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large inputs (n > 100)");
        System.out.println("     - Doesn't meet O(n) requirement");
        System.out.println("   Best for: Educational purposes, small inputs");
        
        System.out.println("\n5. Sliding Window with LinkedHashMap:");
        System.out.println("   Time: O(n) - Single pass through string");
        System.out.println("   Space: O(min(m, n)) - LinkedHashMap storage");
        System.out.println("   How it works:");
        System.out.println("     - Use LinkedHashMap to maintain insertion order");
        System.out.println("     - When duplicate found, remove all characters up to duplicate");
        System.out.println("     - Maintains order of characters in current window");
        System.out.println("   Pros:");
        System.out.println("     - Maintains character order in window");
        System.out.println("     - Useful for related problems needing order");
        System.out.println("   Cons:");
        System.out.println("     - More complex than regular HashMap");
        System.out.println("     - Slightly slower due to maintenance of order");
        System.out.println("   Best for: Problems requiring ordered character sequences");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Maximum possible length is min(n, m) where m is character set size");
        System.out.println("2. For ASCII, maximum length is 128 (extended ASCII: 256)");
        System.out.println("3. For Unicode, maximum length depends on the specific characters");
        System.out.println("4. The algorithm ensures optimal substructure property");
        System.out.println("5. Each character is processed at most twice (amortized)");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Sliding Window + HashMap - it's the expected optimal solution");
        System.out.println("2. Explain the two-pointer approach and window maintenance clearly");
        System.out.println("3. Mention time and space complexity (O(n), O(min(m, n)))");
        System.out.println("4. Handle edge cases: empty string, single character, all duplicates");
        System.out.println("5. Discuss alternative approaches (HashSet, Array) if time permits");
        System.out.println("6. Consider mentioning the array optimization for ASCII strings");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
