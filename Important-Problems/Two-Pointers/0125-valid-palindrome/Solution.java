/**
 * 125. Valid Palindrome
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters 
 * and removing all non-alphanumeric characters, it reads the same forward and backward.
 * Given a string s, return true if it is a palindrome, or false otherwise.
 * 
 * Key Insights:
 * 1. Use two pointers starting from both ends
 * 2. Skip non-alphanumeric characters efficiently
 * 3. Compare characters case-insensitively
 * 4. Handle edge cases: empty string, single character, all non-alphanumeric
 * 
 * Approach (Two Pointers - RECOMMENDED):
 * 1. Initialize left = 0, right = n-1
 * 2. While left < right, skip non-alphanumeric characters
 * 3. Compare characters at left and right (case-insensitive)
 * 4. If mismatch found, return false
 * 5. Move pointers towards center and continue
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: String, Two Pointers
 */

class Solution {
    /**
     * Approach 1: Two Pointers with Built-in Methods - RECOMMENDED
     * O(n) time, O(1) space - Most efficient and readable
     */
    public boolean isPalindrome(String s) {
        if (s == null) return false;
        
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            // Skip non-alphanumeric characters from left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            
            // Skip non-alphanumeric characters from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            // Compare characters (case-insensitive)
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Approach 2: Two Pointers with Custom Character Validation
     * O(n) time, O(1) space - Manual character checking
     */
    public boolean isPalindromeCustomValidation(String s) {
        if (s == null) return false;
        
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);
            
            // Skip non-alphanumeric from left
            if (!isAlphanumeric(leftChar)) {
                left++;
                continue;
            }
            
            // Skip non-alphanumeric from right
            if (!isAlphanumeric(rightChar)) {
                right--;
                continue;
            }
            
            // Compare characters (case-insensitive)
            if (Character.toLowerCase(leftChar) != Character.toLowerCase(rightChar)) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Helper method for custom character validation
     */
    private boolean isAlphanumeric(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
               (c >= '0' && c <= '9');
    }
    
    /**
     * Approach 3: Preprocessing with String Building
     * O(n) time, O(n) space - Simple but uses extra space
     */
    public boolean isPalindromeStringBuilder(String s) {
        if (s == null) return false;
        
        // Build cleaned string
        StringBuilder cleaned = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }
        
        // Check if cleaned string is palindrome
        String cleanedStr = cleaned.toString();
        int left = 0, right = cleanedStr.length() - 1;
        while (left < right) {
            if (cleanedStr.charAt(left) != cleanedStr.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Approach 4: Preprocessing with Reverse Comparison
     * O(n) time, O(n) space - Very simple but uses most space
     */
    public boolean isPalindromeReverse(String s) {
        if (s == null) return false;
        
        // Build cleaned string
        StringBuilder cleaned = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }
        
        // Check by comparing with reverse
        String cleanedStr = cleaned.toString();
        String reversedStr = cleaned.reverse().toString();
        return cleanedStr.equals(reversedStr);
    }
    
    /**
     * Approach 5: Recursive Solution
     * O(n) time, O(n) stack space - Elegant but inefficient for large inputs
     */
    public boolean isPalindromeRecursive(String s) {
        if (s == null) return false;
        return isPalindromeHelper(s, 0, s.length() - 1);
    }
    
    private boolean isPalindromeHelper(String s, int left, int right) {
        // Base case: pointers have crossed
        if (left >= right) {
            return true;
        }
        
        // Skip non-alphanumeric from left
        if (!Character.isLetterOrDigit(s.charAt(left))) {
            return isPalindromeHelper(s, left + 1, right);
        }
        
        // Skip non-alphanumeric from right
        if (!Character.isLetterOrDigit(s.charAt(right))) {
            return isPalindromeHelper(s, left, right - 1);
        }
        
        // Compare characters
        if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
            return false;
        }
        
        // Recursive call for next pair
        return isPalindromeHelper(s, left + 1, right - 1);
    }
    
    /**
     * Approach 6: Single Pass with Filter and Two Pointers
     * O(n) time, O(n) space - Combines filtering with two pointers
     */
    public boolean isPalindromeFilter(String s) {
        if (s == null) return false;
        
        // Filter and convert to lowercase in one pass
        char[] chars = s.toCharArray();
        int length = 0; // Track length of filtered array
        
        for (char c : chars) {
            if (Character.isLetterOrDigit(c)) {
                chars[length++] = Character.toLowerCase(c);
            }
        }
        
        // Use two pointers on filtered array
        int left = 0, right = length - 1;
        while (left < right) {
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Helper method to visualize the palindrome checking process
     */
    private void visualizePalindromeCheck(String s, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Input: \"" + s + "\"");
        
        int left = 0;
        int right = s.length() - 1;
        int step = 1;
        
        System.out.println("Step | Left | Right | LeftChar | RightChar | Action");
        System.out.println("-----|------|-------|----------|-----------|--------");
        
        while (left < right) {
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);
            
            // Skip non-alphanumeric from left
            while (left < right && !Character.isLetterOrDigit(leftChar)) {
                System.out.printf("%4d | %4d | %5d | %8c | %9c | Skip left (non-alphanumeric)%n",
                                step, left, right, leftChar, rightChar);
                left++;
                leftChar = s.charAt(left);
                step++;
            }
            
            // Skip non-alphanumeric from right
            while (left < right && !Character.isLetterOrDigit(rightChar)) {
                System.out.printf("%4d | %4d | %5d | %8c | %9c | Skip right (non-alphanumeric)%n",
                                step, left, right, leftChar, rightChar);
                right--;
                rightChar = s.charAt(right);
                step++;
            }
            
            // Compare characters
            char leftLower = Character.toLowerCase(leftChar);
            char rightLower = Character.toLowerCase(rightChar);
            
            if (leftLower != rightLower) {
                System.out.printf("%4d | %4d | %5d | %8c | %9c | MISMATCH: '%c' != '%c'%n",
                                step, left, right, leftChar, rightChar, leftLower, rightLower);
                System.out.println("Result: false (not a palindrome)");
                return;
            } else {
                System.out.printf("%4d | %4d | %5d | %8c | %9c | Match: '%c' == '%c'%n",
                                step, left, right, leftChar, rightChar, leftLower, rightLower);
            }
            
            left++;
            right--;
            step++;
        }
        
        System.out.println("Result: true (is a palindrome)");
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(String s, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        long startTime = System.nanoTime();
        boolean result1 = isPalindrome(s);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result2 = isPalindromeCustomValidation(s);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result3 = isPalindromeStringBuilder(s);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result4 = isPalindromeReverse(s);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result5 = isPalindromeRecursive(s);
        long time5 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result6 = isPalindromeFilter(s);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("Two Pointers (Built-in): %d ns%n", time1);
        System.out.printf("Two Pointers (Custom): %d ns%n", time2);
        System.out.printf("String Builder: %d ns%n", time3);
        System.out.printf("Reverse Compare: %d ns%n", time4);
        System.out.printf("Recursive: %d ns%n", time5);
        System.out.printf("Filter + Two Pointers: %d ns%n", time6);
        
        // Verify all produce same result
        boolean allEqual = result1 == result2 && result1 == result3 && 
                          result1 == result4 && result1 == result5 && result1 == result6;
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Valid Palindrome Solution:");
        System.out.println("===================================");
        
        // Test case 1: Valid palindrome with punctuation
        System.out.println("\nTest 1: Valid palindrome with punctuation");
        String s1 = "A man, a plan, a canal: Panama";
        boolean expected1 = true;
        
        boolean result1a = solution.isPalindrome(s1);
        boolean result1b = solution.isPalindromeCustomValidation(s1);
        boolean result1c = solution.isPalindromeStringBuilder(s1);
        boolean result1d = solution.isPalindromeReverse(s1);
        boolean result1e = solution.isPalindromeRecursive(s1);
        boolean result1f = solution.isPalindromeFilter(s1);
        
        System.out.println("Two Pointers (Built-in): " + result1a + " - " + (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Two Pointers (Custom): " + result1b + " - " + (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("String Builder: " + result1c + " - " + (result1c == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Reverse Compare: " + result1d + " - " + (result1d == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Recursive: " + result1e + " - " + (result1e == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Filter + Two Pointers: " + result1f + " - " + (result1f == expected1 ? "PASSED" : "FAILED"));
        
        // Visualize the palindrome checking process
        solution.visualizePalindromeCheck(s1, "Test 1 - Valid Palindrome");
        
        // Test case 2: Invalid palindrome
        System.out.println("\nTest 2: Invalid palindrome");
        String s2 = "race a car";
        boolean expected2 = false;
        
        boolean result2a = solution.isPalindrome(s2);
        System.out.println("Invalid palindrome: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Empty string
        System.out.println("\nTest 3: Empty string");
        String s3 = " ";
        boolean expected3 = true;
        
        boolean result3a = solution.isPalindrome(s3);
        System.out.println("Empty string: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single character
        System.out.println("\nTest 4: Single character");
        String s4 = "a";
        boolean expected4 = true;
        
        boolean result4a = solution.isPalindrome(s4);
        System.out.println("Single character: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: All non-alphanumeric characters
        System.out.println("\nTest 5: All non-alphanumeric characters");
        String s5 = "!@#$%^&*()";
        boolean expected5 = true;
        
        boolean result5a = solution.isPalindrome(s5);
        System.out.println("All non-alphanumeric: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Mixed case palindrome
        System.out.println("\nTest 6: Mixed case palindrome");
        String s6 = "aBcDcBa";
        boolean expected6 = true;
        
        boolean result6a = solution.isPalindrome(s6);
        System.out.println("Mixed case: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Palindrome with numbers
        System.out.println("\nTest 7: Palindrome with numbers");
        String s7 = "0P0";
        boolean expected7 = true;
        
        boolean result7a = solution.isPalindrome(s7);
        System.out.println("With numbers: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Very long palindrome
        System.out.println("\nTest 8: Very long palindrome");
        String s8 = "a".repeat(100000) + "b" + "a".repeat(100000);
        boolean result8a = solution.isPalindrome(s8);
        System.out.println("Very long: " + result8a + " - " + 
                         (!result8a ? "PASSED" : "FAILED")); // Should be false
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(s1, "Small Input (30 chars)");
        
        // Medium input performance
        String medium = "A man, a plan, a canal: Panama! ".repeat(10);
        solution.comparePerformance(medium, "Medium Input (300 chars)");
        
        // Large input performance
        String large = "abc123".repeat(10000) + "XYZ" + "321cba".repeat(10000);
        solution.comparePerformance(large, "Large Input (120,000 chars)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Two Pointers (Built-in) - RECOMMENDED:");
        System.out.println("   Time: O(n) - Single pass through string");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Start with left=0 and right=n-1");
        System.out.println("     - Skip non-alphanumeric characters using Character.isLetterOrDigit()");
        System.out.println("     - Compare characters using Character.toLowerCase()");
        System.out.println("     - Return false on mismatch, true if all match");
        System.out.println("   Pros:");
        System.out.println("     - True O(1) space complexity");
        System.out.println("     - Most efficient for large inputs");
        System.out.println("     - Uses standard Java methods");
        System.out.println("     - Handles all edge cases gracefully");
        System.out.println("   Cons:");
        System.out.println("     - None significant for this problem");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Two Pointers (Custom Validation):");
        System.out.println("   Time: O(n) - Same as built-in version");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Same algorithm but with manual character validation");
        System.out.println("     - Uses custom isAlphanumeric() method");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates understanding of character ranges");
        System.out.println("     - No reliance on Character class methods");
        System.out.println("   Cons:");
        System.out.println("     - More code to maintain");
        System.out.println("     - Easy to make mistakes in character ranges");
        System.out.println("   Best for: Demonstrating character manipulation knowledge");
        
        System.out.println("\n3. String Builder Approach:");
        System.out.println("   Time: O(n) - Two passes (filtering + checking)");
        System.out.println("   Space: O(n) - New string storage");
        System.out.println("   How it works:");
        System.out.println("     - Build new string with only alphanumeric characters");
        System.out.println("     - Convert to lowercase during building");
        System.out.println("     - Check if cleaned string is palindrome");
        System.out.println("   Pros:");
        System.out.println("     - Very simple and readable");
        System.out.println("     - Separates cleaning from palindrome check");
        System.out.println("   Cons:");
        System.out.println("     - O(n) extra space violates optimal requirement");
        System.out.println("     - Less efficient for large inputs");
        System.out.println("   Best for: Small inputs, when readability is priority");
        
        System.out.println("\n4. Reverse Comparison:");
        System.out.println("   Time: O(n) - Filtering + reversing + comparison");
        System.out.println("   Space: O(n) - Two new strings (cleaned and reversed)");
        System.out.println("   How it works:");
        System.out.println("     - Build cleaned string");
        System.out.println("     - Compare with its reverse");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple implementation");
        System.out.println("     - Easy to understand and verify");
        System.out.println("   Cons:");
        System.out.println("     - Uses most space among all approaches");
        System.out.println("     - Creates multiple string copies");
        System.out.println("   Best for: Quick prototyping, very small inputs");
        
        System.out.println("\n5. Recursive Approach:");
        System.out.println("   Time: O(n) - Makes n/2 recursive calls in worst case");
        System.out.println("   Space: O(n) - Stack space for recursion");
        System.out.println("   How it works:");
        System.out.println("     - Base case: pointers cross (return true)");
        System.out.println("     - Skip non-alphanumeric with recursive calls");
        System.out.println("     - Compare and recurse for next pair");
        System.out.println("   Pros:");
        System.out.println("     - Elegant recursive solution");
        System.out.println("     - Good for learning recursion");
        System.out.println("   Cons:");
        System.out.println("     - O(n) stack space risk of overflow");
        System.out.println("     - Less efficient than iterative");
        System.out.println("     - Harder to understand for some");
        System.out.println("   Best for: Educational purposes, small inputs");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CHARACTER VALIDATION METHODS:");
        System.out.println("1. Character.isLetterOrDigit(c) - Recommended");
        System.out.println("2. Manual check: (a-z) || (A-Z) || (0-9)");
        System.out.println("3. Regular expressions - Overkill for this problem");
        System.out.println("4. ASCII range checking - Most efficient but error-prone");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HOW TO HANDLE THEM:");
        System.out.println("1. Empty string: \"\" -> true (empty reads same forward/backward)");
        System.out.println("2. All non-alphanumeric: \"!@#\" -> true (empty after cleaning)");
        System.out.println("3. Single character: \"a\" -> true");
        System.out.println("4. Mixed case: \"Aa\" -> true (case-insensitive)");
        System.out.println("5. Numbers: \"0P0\" -> true (numbers are alphanumeric)");
        System.out.println("6. Very long strings: Handle efficiently with O(1) space");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Two Pointers - it's the expected optimal solution");
        System.out.println("2. Clarify: case sensitivity, alphanumeric definition, empty strings");
        System.out.println("3. Explain the algorithm: pointers, skipping, comparing");
        System.out.println("4. Mention time/space complexity: O(n)/O(1)");
        System.out.println("5. Discuss alternative approaches and trade-offs");
        System.out.println("6. Handle edge cases explicitly in code");
        System.out.println("7. Write clean, readable code with good variable names");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Forgetting to handle non-alphanumeric characters");
        System.out.println("2. Not making the comparison case-insensitive");
        System.out.println("3. Using O(n) space when O(1) is possible");
        System.out.println("4. Incorrect pointer movement (infinite loops)");
        System.out.println("5. Not handling empty or single-character strings");
        System.out.println("6. Using complex regex when simple checks suffice");
        System.out.println("=".repeat(70));
        
        // Additional examples and patterns
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PATTERN RECOGNITION:");
        System.out.println("1. Palindrome problems often use two pointers");
        System.out.println("2. String cleaning/filtering is common preprocessing");
        System.out.println("3. Case-insensitive comparison is frequently required");
        System.out.println("4. Edge cases: empty, single char, all non-alphanumeric");
        System.out.println("5. Space optimization is often the key challenge");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
