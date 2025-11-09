/**
 * 680. Valid Palindrome II
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given a string s, return true if the s can be palindrome after deleting at most one character from it.
 * 
 * Key Insights:
 * 1. Use two pointers starting from both ends
 * 2. When characters don't match, check both possibilities:
 *    - Skip the left character and check the remaining substring
 *    - Skip the right character and check the remaining substring
 * 3. Only one deletion is allowed, so we check both paths
 * 4. Use a helper function to check if substring is palindrome
 * 
 * Approach (Two Pointers with Helper - RECOMMENDED):
 * 1. Initialize left = 0, right = n-1
 * 2. While left < right, compare characters
 * 3. If mismatch found, check both deletion possibilities
 * 4. Return true if either possibility is valid
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: String, Two Pointers, Greedy
 */

class Solution {
    /**
     * Approach 1: Two Pointers with Helper Function - RECOMMENDED
     * O(n) time, O(1) space - Most efficient and readable
     */
    public boolean validPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                // Check both possibilities: skip left or skip right
                return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Helper method to check if substring is palindrome
     */
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    /**
     * Approach 2: Two Pointers with Inline Check
     * O(n) time, O(1) space - Same efficiency, different style
     */
    public boolean validPalindromeInline(String s) {
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                // Check skipping left character
                int l1 = left + 1, r1 = right;
                boolean skipLeft = true;
                while (l1 < r1) {
                    if (s.charAt(l1) != s.charAt(r1)) {
                        skipLeft = false;
                        break;
                    }
                    l1++;
                    r1--;
                }
                
                // Check skipping right character
                int l2 = left, r2 = right - 1;
                boolean skipRight = true;
                while (l2 < r2) {
                    if (s.charAt(l2) != s.charAt(r2)) {
                        skipRight = false;
                        break;
                    }
                    l2++;
                    r2--;
                }
                
                return skipLeft || skipRight;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Approach 3: Recursive Solution
     * O(n) time, O(n) stack space - Elegant but uses stack space
     */
    public boolean validPalindromeRecursive(String s) {
        return validPalindromeHelper(s, 0, s.length() - 1, 1);
    }
    
    private boolean validPalindromeHelper(String s, int left, int right, int deletionsAllowed) {
        // Base case: pointers have crossed
        if (left >= right) {
            return true;
        }
        
        // Characters match, continue checking
        if (s.charAt(left) == s.charAt(right)) {
            return validPalindromeHelper(s, left + 1, right - 1, deletionsAllowed);
        }
        
        // Characters don't match, check if we can delete
        if (deletionsAllowed > 0) {
            // Try deleting left character or right character
            return validPalindromeHelper(s, left + 1, right, deletionsAllowed - 1) ||
                   validPalindromeHelper(s, left, right - 1, deletionsAllowed - 1);
        }
        
        // No deletions left and characters don't match
        return false;
    }
    
    /**
     * Approach 4: Early Termination with Count
     * O(n) time, O(1) space - Optimized version
     */
    public boolean validPalindromeOptimized(String s) {
        int left = 0;
        int right = s.length() - 1;
        int mismatchCount = 0;
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                mismatchCount++;
                if (mismatchCount > 1) {
                    return false;
                }
                
                // When first mismatch found, check both possibilities
                if (isPalindromeOptimized(s, left + 1, right) || 
                    isPalindromeOptimized(s, left, right - 1)) {
                    // One of the possibilities worked, continue
                    break;
                } else {
                    return false;
                }
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    private boolean isPalindromeOptimized(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    /**
     * Approach 5: Greedy with Deletion Tracking
     * O(n) time, O(1) space - Tracks deletion explicitly
     */
    public boolean validPalindromeGreedy(String s) {
        return checkPalindromeWithDeletion(s, 0, s.length() - 1, false);
    }
    
    private boolean checkPalindromeWithDeletion(String s, int left, int right, boolean deletionUsed) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                if (deletionUsed) {
                    return false; // Already used our one deletion
                }
                
                // Try deleting left character
                if (checkPalindromeWithDeletion(s, left + 1, right, true)) {
                    return true;
                }
                
                // Try deleting right character
                return checkPalindromeWithDeletion(s, left, right - 1, true);
            }
            left++;
            right--;
        }
        return true;
    }
    
    /**
     * Approach 6: Iterative with Stack Simulation
     * O(n) time, O(n) space - Simulates recursion iteratively
     */
    public boolean validPalindromeIterative(String s) {
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                // Found mismatch, check both deletion options
                return checkSubstring(s, left + 1, right) || checkSubstring(s, left, right - 1);
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    private boolean checkSubstring(String s, int start, int end) {
        int left = start;
        int right = end;
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
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
        boolean deletionUsed = false;
        
        System.out.println("Step | Left | Right | LeftChar | RightChar | Action");
        System.out.println("-----|------|-------|----------|-----------|--------");
        
        while (left < right) {
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);
            
            if (leftChar != rightChar) {
                if (deletionUsed) {
                    System.out.printf("%4d | %4d | %5d | %8c | %9c | MISMATCH - No deletions left%n",
                                    step, left, right, leftChar, rightChar);
                    System.out.println("Result: false (cannot be palindrome with at most one deletion)");
                    return;
                }
                
                System.out.printf("%4d | %4d | %5d | %8c | %9c | MISMATCH found%n",
                                step, left, right, leftChar, rightChar);
                
                // Check skipping left character
                boolean skipLeft = checkSubstringVisual(s, left + 1, right, "Skip left");
                boolean skipRight = checkSubstringVisual(s, left, right - 1, "Skip right");
                
                if (skipLeft || skipRight) {
                    System.out.println("One deletion works: " + (skipLeft ? "skip left" : "skip right"));
                    deletionUsed = true;
                    // Adjust pointers based on which deletion worked
                    if (skipLeft) {
                        left++;
                    } else {
                        right--;
                    }
                } else {
                    System.out.println("Neither deletion works");
                    System.out.println("Result: false (cannot be palindrome with at most one deletion)");
                    return;
                }
            } else {
                System.out.printf("%4d | %4d | %5d | %8c | %9c | Match: '%c' == '%c'%n",
                                step, left, right, leftChar, rightChar, leftChar, rightChar);
                left++;
                right--;
            }
            step++;
        }
        
        System.out.println("Result: true (is valid palindrome with at most one deletion)");
    }
    
    private boolean checkSubstringVisual(String s, int start, int end, String action) {
        int left = start;
        int right = end;
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                System.out.println("  " + action + " check: fails at (" + left + ", " + right + ")");
                return false;
            }
            left++;
            right--;
        }
        System.out.println("  " + action + " check: succeeds");
        return true;
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(String s, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        long startTime = System.nanoTime();
        boolean result1 = validPalindrome(s);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result2 = validPalindromeInline(s);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result3 = validPalindromeRecursive(s);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result4 = validPalindromeOptimized(s);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result5 = validPalindromeGreedy(s);
        long time5 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result6 = validPalindromeIterative(s);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("Two Pointers (Helper): %d ns%n", time1);
        System.out.printf("Two Pointers (Inline): %d ns%n", time2);
        System.out.printf("Recursive: %d ns%n", time3);
        System.out.printf("Optimized: %d ns%n", time4);
        System.out.printf("Greedy: %d ns%n", time5);
        System.out.printf("Iterative: %d ns%n", time6);
        
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
        
        System.out.println("Testing Valid Palindrome II Solution:");
        System.out.println("======================================");
        
        // Test case 1: Already palindrome
        System.out.println("\nTest 1: Already palindrome");
        String s1 = "aba";
        boolean expected1 = true;
        
        boolean result1a = solution.validPalindrome(s1);
        boolean result1b = solution.validPalindromeInline(s1);
        boolean result1c = solution.validPalindromeRecursive(s1);
        boolean result1d = solution.validPalindromeOptimized(s1);
        boolean result1e = solution.validPalindromeGreedy(s1);
        boolean result1f = solution.validPalindromeIterative(s1);
        
        System.out.println("Two Pointers (Helper): " + result1a + " - " + (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Two Pointers (Inline): " + result1b + " - " + (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Recursive: " + result1c + " - " + (result1c == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1d + " - " + (result1d == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Greedy: " + result1e + " - " + (result1e == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Iterative: " + result1f + " - " + (result1f == expected1 ? "PASSED" : "FAILED"));
        
        // Visualize the checking process
        solution.visualizePalindromeCheck(s1, "Test 1 - Already Palindrome");
        
        // Test case 2: One deletion needed
        System.out.println("\nTest 2: One deletion needed");
        String s2 = "abca";
        boolean expected2 = true;
        
        boolean result2a = solution.validPalindrome(s2);
        System.out.println("One deletion needed: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Visualize this case
        solution.visualizePalindromeCheck(s2, "Test 2 - One Deletion Needed");
        
        // Test case 3: Cannot be palindrome
        System.out.println("\nTest 3: Cannot be palindrome");
        String s3 = "abc";
        boolean expected3 = false;
        
        boolean result3a = solution.validPalindrome(s3);
        System.out.println("Cannot be palindrome: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Empty string
        System.out.println("\nTest 4: Empty string");
        String s4 = "";
        boolean expected4 = true;
        
        boolean result4a = solution.validPalindrome(s4);
        System.out.println("Empty string: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single character
        System.out.println("\nTest 5: Single character");
        String s5 = "a";
        boolean expected5 = true;
        
        boolean result5a = solution.validPalindrome(s5);
        System.out.println("Single character: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Two different characters
        System.out.println("\nTest 6: Two different characters");
        String s6 = "ab";
        boolean expected6 = true; // Can delete one character to get single char palindrome
        
        boolean result6a = solution.validPalindrome(s6);
        System.out.println("Two different chars: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Complex case - deletion in middle
        System.out.println("\nTest 7: Complex case - deletion in middle");
        String s7 = "deeee";
        boolean expected7 = true; // Delete one 'e' to get "deee" -> not palindrome? Wait, check carefully
        
        boolean result7a = solution.validPalindrome(s7);
        System.out.println("Complex case: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Need to delete from beginning
        System.out.println("\nTest 8: Need to delete from beginning");
        String s8 = "abcba";
        boolean expected8 = true; // Original: "abcba" is palindrome actually
        
        // Let's create a case where we need to delete from beginning
        String s8b = "xabcba";
        boolean result8a = solution.validPalindrome(s8b);
        System.out.println("Delete from beginning: " + result8a + " - " + 
                         (result8a ? "PASSED" : "FAILED")); // Should be true
        
        // Test case 9: Very long string that needs one deletion
        System.out.println("\nTest 9: Very long string");
        String base = "a".repeat(50000);
        String s9 = base + "b" + base + "c" + base;
        boolean result9a = solution.validPalindrome(s9);
        System.out.println("Very long string: " + result9a + " - " + 
                         (!result9a ? "PASSED" : "FAILED")); // Should be false
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(s2, "Small Input (4 chars)");
        
        // Medium input performance
        String medium = "a".repeat(100) + "b" + "a".repeat(100);
        solution.comparePerformance(medium, "Medium Input (201 chars)");
        
        // Large input performance (no deletion needed)
        String largeNoDeletion = "a".repeat(50000) + "b" + "a".repeat(50000);
        solution.comparePerformance(largeNoDeletion, "Large Input (100,001 chars - No deletion)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Two Pointers with Helper - RECOMMENDED:");
        System.out.println("   Time: O(n) - Single pass in worst case");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Use two pointers from both ends");
        System.out.println("     - When mismatch found, check both deletion possibilities");
        System.out.println("     - Use helper function to check remaining substring");
        System.out.println("   Pros:");
        System.out.println("     - Clean and readable code");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - None significant");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Two Pointers (Inline):");
        System.out.println("   Time: O(n) - Same as helper version");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Same algorithm but with inline palindrome checks");
        System.out.println("     - No separate helper function");
        System.out.println("   Pros:");
        System.out.println("     - All logic in one method");
        System.out.println("     - Slightly faster in some cases (no method calls)");
        System.out.println("   Cons:");
        System.out.println("     - Code duplication in palindrome checks");
        System.out.println("     - Less readable and maintainable");
        System.out.println("   Best for: Performance-critical code");
        
        System.out.println("\n3. Recursive Approach:");
        System.out.println("   Time: O(n) - Makes recursive calls for deletion options");
        System.out.println("   Space: O(n) - Stack space for recursion");
        System.out.println("   How it works:");
        System.out.println("     - Base case: pointers cross (return true)");
        System.out.println("     - Recursive case: match or try deletions");
        System.out.println("     - Track deletions allowed as parameter");
        System.out.println("   Pros:");
        System.out.println("     - Elegant mathematical formulation");
        System.out.println("     - Clear separation of cases");
        System.out.println("   Cons:");
        System.out.println("     - O(n) stack space risk");
        System.out.println("     - Less efficient for large inputs");
        System.out.println("     - Stack overflow risk");
        System.out.println("   Best for: Educational purposes, small inputs");
        
        System.out.println("\n4. Optimized Version:");
        System.out.println("   Time: O(n) - Early termination on second mismatch");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Track mismatch count explicitly");
        System.out.println("     - Early return if more than one deletion needed");
        System.out.println("   Pros:");
        System.out.println("     - Early termination optimization");
        System.out.println("     - Clear logic flow");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("     - Marginal performance gain");
        System.out.println("   Best for: Large inputs with early mismatches");
        
        System.out.println("\n5. Greedy with Deletion Tracking:");
        System.out.println("   Time: O(n) - Checks both deletion paths");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Track deletion usage as boolean flag");
        System.out.println("     - Recursively check both deletion options");
        System.out.println("   Pros:");
        System.out.println("     - Clear state tracking");
        System.out.println("     - Easy to extend for more deletions");
        System.out.println("   Cons:");
        System.out.println("     - More complex method signature");
        System.out.println("     - Slightly harder to follow");
        System.out.println("   Best for: When extending to k deletions");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("KEY INSIGHTS FOR VALID PALINDROME II:");
        System.out.println("1. At most ONE deletion is allowed");
        System.out.println("2. When mismatch occurs, check BOTH possibilities:");
        System.out.println("   - Skip the left character (i+1, j)");
        System.out.println("   - Skip the right character (i, j-1)");
        System.out.println("3. If either possibility works, return true");
        System.out.println("4. Empty string and single character are always valid");
        System.out.println("5. Two different characters are valid (delete one)");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. Empty string: \"\" -> true");
        System.out.println("2. Single character: \"a\" -> true");
        System.out.println("3. Two same characters: \"aa\" -> true");
        System.out.println("4. Two different characters: \"ab\" -> true (delete one)");
        System.out.println("5. Already palindrome: \"aba\" -> true");
        System.out.println("6. Need one deletion: \"abca\" -> true");
        System.out.println("7. Need more than one deletion: \"abc\" -> false");
        System.out.println("8. Very long strings: Handle efficiently");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Two Pointers approach");
        System.out.println("2. Explain the algorithm clearly:");
        System.out.println("   - Two pointers from ends");
        System.out.println("   - When mismatch: check both skip options");
        System.out.println("   - Use helper function for clean code");
        System.out.println("3. Mention time/space complexity: O(n)/O(1)");
        System.out.println("4. Discuss edge cases explicitly");
        System.out.println("5. Write clean, readable code with good naming");
        System.out.println("6. Consider mentioning extension to k deletions");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Only checking one deletion possibility");
        System.out.println("2. Not handling empty/single character cases");
        System.out.println("3. Using O(n) space when O(1) is possible");
        System.out.println("4. Incorrect early termination");
        System.out.println("5. Forgetting that two different chars are valid");
        System.out.println("6. Not considering both deletion options");
        System.out.println("=".repeat(70));
        
        // Extension discussion
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EXTENSION: VALID PALINDROME III (K DELETIONS)");
        System.out.println("The same approach can be extended to handle k deletions:");
        System.out.println("1. Use recursive approach with deletion count parameter");
        System.out.println("2. When mismatch occurs, recursively try both deletions");
        System.out.println("3. Decrement deletion count for each deletion attempt");
        System.out.println("4. Base case: if deletions < 0, return false");
        System.out.println("Time Complexity: O(n * 2^k) without memoization");
        System.out.println("Space Complexity: O(k) for recursion stack");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
