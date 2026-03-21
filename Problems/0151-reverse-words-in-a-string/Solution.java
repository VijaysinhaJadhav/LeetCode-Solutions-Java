
# Solution.java

```java
import java.util.*;

/**
 * 151. Reverse Words in a String
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Reverse the order of words in a string, handling multiple spaces.
 * Words are defined as sequences of non-space characters.
 * 
 * Key Insights:
 * 1. Words are separated by at least one space
 * 2. Need to handle leading/trailing spaces
 * 3. Output should have single spaces between words
 * 4. Can approach by splitting, reversing, and joining
 */
class Solution {
    
    /**
     * Approach 1: Split and Reverse (Simplest)
     * Time: O(n), Space: O(n)
     * 
     * Steps:
     * 1. Trim leading/trailing spaces
     * 2. Split by one or more spaces
     * 3. Reverse the array
     * 4. Join with single space
     */
    public String reverseWords(String s) {
        // Trim leading/trailing spaces and split by one or more spaces
        String[] words = s.trim().split("\\s+");
        
        // Reverse the array
        int left = 0;
        int right = words.length - 1;
        while (left < right) {
            String temp = words[left];
            words[left] = words[right];
            words[right] = temp;
            left++;
            right--;
        }
        
        // Join with single space
        return String.join(" ", words);
    }
    
    /**
     * Approach 2: Two-Pointer with StringBuilder
     * Time: O(n), Space: O(n)
     * 
     * Process string from end to beginning, building result word by word
     */
    public String reverseWordsTwoPointer(String s) {
        StringBuilder result = new StringBuilder();
        int i = s.length() - 1;
        
        while (i >= 0) {
            // Skip spaces
            while (i >= 0 && s.charAt(i) == ' ') {
                i--;
            }
            if (i < 0) break;
            
            // Find word boundaries
            int j = i;
            while (j >= 0 && s.charAt(j) != ' ') {
                j--;
            }
            
            // Extract word and add to result
            String word = s.substring(j + 1, i + 1);
            if (result.length() > 0) {
                result.append(' ');
            }
            result.append(word);
            
            // Move to next word
            i = j;
        }
        
        return result.toString();
    }
    
    /**
     * Approach 3: In-place (using char array - for mutable strings)
     * Time: O(n), Space: O(n) for char array (Java strings immutable)
     * 
     * Steps:
     * 1. Convert to char array
     * 2. Reverse entire array
     * 3. Reverse each word
     * 4. Clean up spaces
     * 
     * Note: In languages with mutable strings (C++, Python list), this can be O(1) extra space
     */
    public String reverseWordsInPlace(String s) {
        // Convert to char array for manipulation
        char[] chars = s.toCharArray();
        int n = chars.length;
        
        // Step 1: Reverse entire string
        reverse(chars, 0, n - 1);
        
        // Step 2: Reverse each word
        int start = 0;
        for (int i = 0; i <= n; i++) {
            if (i == n || chars[i] == ' ') {
                reverse(chars, start, i - 1);
                start = i + 1;
            }
        }
        
        // Step 3: Clean up spaces (remove extra spaces)
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < n) {
            // Skip leading spaces
            while (i < n && chars[i] == ' ') {
                i++;
            }
            if (i >= n) break;
            
            // Copy word
            while (i < n && chars[i] != ' ') {
                result.append(chars[i]);
                i++;
            }
            
            // Add space between words
            if (i < n) {
                result.append(' ');
            }
        }
        
        return result.toString().trim();
    }
    
    private void reverse(char[] arr, int left, int right) {
        while (left < right) {
            char temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
    
    /**
     * Approach 4: Stack-based approach
     * Time: O(n), Space: O(n)
     * 
     * Push words onto stack, then pop for reverse order
     */
    public String reverseWordsStack(String s) {
        Deque<String> stack = new ArrayDeque<>();
        
        // Build words and push to stack
        int i = 0;
        int n = s.length();
        
        while (i < n) {
            // Skip spaces
            while (i < n && s.charAt(i) == ' ') {
                i++;
            }
            if (i >= n) break;
            
            // Build word
            StringBuilder word = new StringBuilder();
            while (i < n && s.charAt(i) != ' ') {
                word.append(s.charAt(i));
                i++;
            }
            stack.push(word.toString());
        }
        
        // Pop from stack to build result
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
            if (!stack.isEmpty()) {
                result.append(' ');
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 5: Using ArrayList and Collections.reverse
     * Time: O(n), Space: O(n)
     * 
     * More Java-idiomatic approach
     */
    public String reverseWordsCollection(String s) {
        // Split into words (handles multiple spaces)
        String[] words = s.trim().split("\\s+");
        
        // Convert to list for easy reversal
        List<String> wordList = new ArrayList<>(Arrays.asList(words));
        Collections.reverse(wordList);
        
        // Join with single space
        return String.join(" ", wordList);
    }
    
    /**
     * Approach 6: Regular Expression with Streams
     * Time: O(n), Space: O(n)
     * 
     * Modern Java approach
     */
    public String reverseWordsStream(String s) {
        return Arrays.stream(s.trim().split("\\s+"))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll)
                .stream()
                .reduce((a, b) -> b + " " + a)
                .orElse("");
    }
    
    /**
     * Helper: Visualize the reversal process
     */
    public void visualizeReversal(String s) {
        System.out.println("\nReverse Words Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nOriginal string: \"" + s + "\"");
        System.out.println("Length: " + s.length());
        
        System.out.println("\nApproach 1: Split and Reverse");
        System.out.println("  Step 1: Trim and split");
        String trimmed = s.trim();
        System.out.println("    Trimmed: \"" + trimmed + "\"");
        String[] words = trimmed.split("\\s+");
        System.out.println("    Words array: " + Arrays.toString(words));
        
        System.out.println("  Step 2: Reverse array");
        int left = 0, right = words.length - 1;
        while (left < right) {
            System.out.printf("    Swap index %d (%s) with %d (%s)%n", 
                left, words[left], right, words[right]);
            String temp = words[left];
            words[left] = words[right];
            words[right] = temp;
            left++;
            right--;
        }
        System.out.println("    Reversed: " + Arrays.toString(words));
        
        System.out.println("  Step 3: Join with single space");
        String result = String.join(" ", words);
        System.out.println("    Result: \"" + result + "\"");
        
        System.out.println("\nApproach 2: Two-Pointer (right to left)");
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;
        int step = 1;
        
        System.out.println("  Processing from right to left:");
        while (i >= 0) {
            // Skip spaces
            while (i >= 0 && s.charAt(i) == ' ') {
                System.out.printf("    Step %d: Skipping space at index %d%n", step++, i);
                i--;
            }
            if (i < 0) break;
            
            // Find word
            int j = i;
            while (j >= 0 && s.charAt(j) != ' ') {
                j--;
            }
            String word = s.substring(j + 1, i + 1);
            System.out.printf("    Step %d: Found word \"%s\" at indices [%d, %d]%n", 
                step++, word, j + 1, i);
            
            if (sb.length() > 0) {
                sb.append(' ');
                System.out.printf("      Adding space%n");
            }
            sb.append(word);
            System.out.printf("      Current result: \"%s\"%n", sb);
            
            i = j;
        }
        
        System.out.println("\nFinal result: \"" + sb.toString() + "\"");
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[] {
            "the sky is blue",           // Example 1
            "  hello world  ",           // Example 2
            "a good   example",          // Example 3
            "single",                    // Single word
            "  leading spaces",          // Leading spaces
            "trailing spaces  ",         // Trailing spaces
            "multiple   spaces   between", // Multiple spaces
            "  hello   world  from  java  ", // Complex
            "",                          // Empty string (should handle gracefully)
            "   ",                       // Only spaces
            "a b c d e f g",             // Many words
            "123 456 789",               // Digits
            "UPPER lower Mixed",         // Mixed case
            "   word   "                 // Spaces around
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        String[] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.printf("\nTest %d: \"%s\"%n", i + 1, s);
            
            try {
                String result1 = reverseWords(s);
                String result2 = reverseWordsTwoPointer(s);
                String result3 = reverseWordsInPlace(s);
                String result4 = reverseWordsStack(s);
                String result5 = reverseWordsCollection(s);
                
                boolean allMatch = result1.equals(result2) && result2.equals(result3) &&
                                  result3.equals(result4) && result4.equals(result5);
                
                if (allMatch) {
                    System.out.println("✓ PASS - Result: \"" + result1 + "\"");
                    passed++;
                } else {
                    System.out.println("✗ FAIL - Methods disagree:");
                    System.out.println("  Split: \"" + result1 + "\"");
                    System.out.println("  Two-pointer: \"" + result2 + "\"");
                    System.out.println("  In-place: \"" + result3 + "\"");
                    System.out.println("  Stack: \"" + result4 + "\"");
                    System.out.println("  Collection: \"" + result5 + "\"");
                }
            } catch (Exception e) {
                System.out.println("✗ FAIL - Exception: " + e.getMessage());
            }
            
            // Visualize first few test cases
            if (i < 3) {
                visualizeReversal(s);
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
        System.out.println("=======================");
        
        // Create a large test case
        StringBuilder sb = new StringBuilder();
        String[] words = {"the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};
        
        // Build a large string with 10,000 words
        for (int i = 0; i < 10000; i++) {
            if (i > 0) sb.append(' ');
            sb.append(words[i % words.length]);
        }
        String largeString = sb.toString();
        
        System.out.println("Test Setup: " + largeString.length() + " characters, ~10,000 words");
        
        long[] times = new long[6];
        
        // Method 1: Split and Reverse
        long start = System.currentTimeMillis();
        String result1 = reverseWords(largeString);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Two-Pointer
        start = System.currentTimeMillis();
        String result2 = reverseWordsTwoPointer(largeString);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: In-place (char array)
        start = System.currentTimeMillis();
        String result3 = reverseWordsInPlace(largeString);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Stack
        start = System.currentTimeMillis();
        String result4 = reverseWordsStack(largeString);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Collection
        start = System.currentTimeMillis();
        String result5 = reverseWordsCollection(largeString);
        times[4] = System.currentTimeMillis() - start;
        
        // Method 6: Stream
        start = System.currentTimeMillis();
        String result6 = reverseWordsStream(largeString);
        times[5] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. Split and Reverse      | %9d%n", times[0]);
        System.out.printf("2. Two-Pointer            | %9d%n", times[1]);
        System.out.printf("3. In-place (char array)  | %9d%n", times[2]);
        System.out.printf("4. Stack                  | %9d%n", times[3]);
        System.out.printf("5. Collection             | %9d%n", times[4]);
        System.out.printf("6. Stream                 | %9d%n", times[5]);
        
        // Verify all results are the same
        boolean allMatch = result1.equals(result2) && result2.equals(result3) &&
                          result3.equals(result4) && result4.equals(result5) &&
                          result5.equals(result6);
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Split + Join is most readable and generally fast");
        System.out.println("2. Two-pointer is efficient and doesn't create intermediate arrays");
        System.out.println("3. In-place approach requires O(n) for char array in Java");
        System.out.println("4. Stack approach uses more memory but is straightforward");
        System.out.println("5. Stream API is concise but may have overhead");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Single word:");
        System.out.println("   Input: \"hello\"");
        System.out.println("   Output: \"" + reverseWords("hello") + "\"");
        
        System.out.println("\n2. Only spaces:");
        System.out.println("   Input: \"   \"");
        System.out.println("   Output: \"" + reverseWords("   ") + "\"");
        
        System.out.println("\n3. Empty string:");
        System.out.println("   Input: \"\"");
        System.out.println("   Output: \"" + reverseWords("") + "\"");
        
        System.out.println("\n4. Leading spaces only:");
        System.out.println("   Input: \"  hello\"");
        System.out.println("   Output: \"" + reverseWords("  hello") + "\"");
        
        System.out.println("\n5. Trailing spaces only:");
        System.out.println("   Input: \"hello  \"");
        System.out.println("   Output: \"" + reverseWords("hello  ") + "\"");
        
        System.out.println("\n6. Multiple spaces between words:");
        System.out.println("   Input: \"a  b   c\"");
        System.out.println("   Output: \"" + reverseWords("a  b   c") + "\"");
        
        System.out.println("\n7. Numbers and special characters:");
        System.out.println("   Input: \"123 456 789\"");
        System.out.println("   Output: \"" + reverseWords("123 456 789") + "\"");
        
        System.out.println("\n8. Mixed case:");
        System.out.println("   Input: \"Hello WORLD java\"");
        System.out.println("   Output: \"" + reverseWords("Hello WORLD java") + "\"");
        
        System.out.println("\n9. Very long word:");
        String longWord = "a".repeat(10000);
        System.out.println("   Input: \"" + longWord + "\"");
        String result = reverseWords(longWord);
        System.out.println("   Output length: " + result.length() + " (expected: " + longWord.length() + ")");
    }
    
    /**
     * Helper: Explain the algorithm step-by-step
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=====================");
        
        System.out.println("\nProblem: Reverse the order of words in a string with multiple spaces.");
        
        System.out.println("\nKey Challenges:");
        System.out.println("1. Leading/trailing spaces");
        System.out.println("2. Multiple spaces between words");
        System.out.println("3. Preserving word order reversal");
        
        System.out.println("\nSolution Approaches:");
        
        System.out.println("\n1. Split and Reverse (Simple):");
        System.out.println("   - Trim the string (remove leading/trailing spaces)");
        System.out.println("   - Split by one or more spaces using regex '\\\\s+'");
        System.out.println("   - Reverse the resulting array");
        System.out.println("   - Join with single spaces");
        
        System.out.println("\n2. Two-Pointer (Efficient):");
        System.out.println("   - Start from the end of the string");
        System.out.println("   - Skip spaces, find word boundaries");
        System.out.println("   - Extract each word and build result");
        System.out.println("   - No splitting overhead, handles spaces naturally");
        
        System.out.println("\n3. In-place (For mutable strings):");
        System.out.println("   - Reverse the entire string");
        System.out.println("   - Reverse each word individually");
        System.out.println("   - Clean up spaces (in-place)");
        System.out.println("   - O(1) extra space for mutable strings");
        
        System.out.println("\nExample: \"the sky is blue\"");
        System.out.println("  Split: [\"the\", \"sky\", \"is\", \"blue\"]");
        System.out.println("  Reverse: [\"blue\", \"is\", \"sky\", \"the\"]");
        System.out.println("  Join: \"blue is sky the\"");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What defines a word? (non-space characters)");
        System.out.println("   - How to handle multiple spaces? (reduce to single)");
        System.out.println("   - Should we preserve original case? (yes)");
        
        System.out.println("\n2. Start with simple approach:");
        System.out.println("   - Split and reverse is easiest to explain");
        System.out.println("   - Discuss time/space complexity");
        System.out.println("   - Mention that split uses regex which is O(n)");
        
        System.out.println("\n3. Optimize if needed:");
        System.out.println("   - Two-pointer approach avoids regex overhead");
        System.out.println("   - For mutable strings, in-place solution is O(1) space");
        System.out.println("   - Discuss trade-offs");
        
        System.out.println("\n4. Handle edge cases:");
        System.out.println("   - Empty string");
        System.out.println("   - Single word");
        System.out.println("   - All spaces");
        System.out.println("   - Leading/trailing spaces");
        System.out.println("   - Multiple spaces between words");
        
        System.out.println("\n5. Implementation details:");
        System.out.println("   - Use StringBuilder for efficient string building");
        System.out.println("   - trim() removes leading/trailing spaces");
        System.out.println("   - split('\\\\s+') splits on one or more spaces");
        System.out.println("   - String.join() for concatenation");
        
        System.out.println("\n6. Complexity analysis:");
        System.out.println("   - Time: O(n) for all approaches");
        System.out.println("   - Space: O(n) for most, O(1) for in-place with mutable strings");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Forgetting to trim the string");
        System.out.println("   - Using single space split instead of multiple");
        System.out.println("   - Adding extra spaces in output");
        System.out.println("   - Not handling empty input");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("151. Reverse Words in a String");
        System.out.println("==============================");
        
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
        
        System.out.println("\nRecommended Implementation (Split and Reverse):");
        System.out.println("""
class Solution {
    public String reverseWords(String s) {
        // Trim and split on one or more spaces
        String[] words = s.trim().split("\\\\s+");
        
        // Reverse the array
        int left = 0, right = words.length - 1;
        while (left < right) {
            String temp = words[left];
            words[left] = words[right];
            words[right] = temp;
            left++;
            right--;
        }
        
        // Join with single space
        return String.join(" ", words);
    }
}
            """);
        
        System.out.println("\nOptimized Implementation (Two-Pointer):");
        System.out.println("""
class Solution {
    public String reverseWords(String s) {
        StringBuilder result = new StringBuilder();
        int i = s.length() - 1;
        
        while (i >= 0) {
            // Skip spaces
            while (i >= 0 && s.charAt(i) == ' ') i--;
            if (i < 0) break;
            
            // Find word
            int j = i;
            while (j >= 0 && s.charAt(j) != ' ') j--;
            
            // Add word
            if (result.length() > 0) result.append(' ');
            result.append(s.substring(j + 1, i + 1));
            
            i = j;
        }
        
        return result.toString();
    }
}
            """);
        
        System.out.println("\nIn-Place Implementation (for mutable strings):");
        System.out.println("""
class Solution {
    public String reverseWords(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        
        // Reverse entire string
        reverse(arr, 0, n - 1);
        
        // Reverse each word
        int start = 0;
        for (int i = 0; i <= n; i++) {
            if (i == n || arr[i] == ' ') {
                reverse(arr, start, i - 1);
                start = i + 1;
            }
        }
        
        // Clean up spaces
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (arr[i] != ' ') {
                result.append(arr[i]);
                if (i + 1 < n && arr[i + 1] == ' ') {
                    result.append(' ');
                }
            }
        }
        
        return result.toString();
    }
    
    private void reverse(char[] arr, int l, int r) {
        while (l < r) {
            char temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            l++;
            r--;
        }
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Words are sequences of non-space characters");
        System.out.println("2. Remove leading/trailing spaces and reduce multiple spaces to single");
        System.out.println("3. Split + Reverse is simplest approach");
        System.out.println("4. Two-pointer approach avoids splitting overhead");
        System.out.println("5. For mutable strings, in-place solution achieves O(1) extra space");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) for all approaches");
        System.out.println("- Space: O(n) for split/join, O(1) for in-place with mutable strings");
        
        System.out.println("\nCommon Interview Variations:");
        System.out.println("1. Reverse words in a string in-place (no extra array)");
        System.out.println("2. Reverse words without using split()");
        System.out.println("3. Rotate words in a string (not just reverse)");
        System.out.println("4. Reverse words but preserve punctuation");
    }
}
