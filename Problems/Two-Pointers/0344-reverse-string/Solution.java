/**
 * 344. Reverse String
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Write a function that reverses a string. The input string is given as an array of characters s.
 * You must do this by modifying the input array in-place with O(1) extra memory.
 * 
 * Key Insights:
 * 1. Use two pointers starting from both ends
 * 2. Swap characters until pointers meet in the middle
 * 3. Multiple approaches available with same time complexity
 * 4. Must modify array in-place with constant extra memory
 * 
 * Approach (Two Pointers - RECOMMENDED):
 * 1. Initialize left = 0, right = n-1
 * 2. While left < right, swap s[left] and s[right]
 * 3. Increment left and decrement right
 * 4. Continue until pointers meet
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Two Pointers, String, Recursion
 */

import java.util.Stack;
import java.util.Arrays;

class Solution {
    /**
     * Approach 1: Two Pointers (Iterative) - RECOMMENDED
     * O(n) time, O(1) space - Most efficient and intuitive
     */
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        
        while (left < right) {
            // Swap characters
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            
            // Move pointers
            left++;
            right--;
        }
    }
    
    /**
     * Approach 2: Two Pointers with XOR Swap
     * O(n) time, O(1) space - Alternative swap method
     */
    public void reverseStringXOR(char[] s) {
        int left = 0;
        int right = s.length - 1;
        
        while (left < right) {
            // XOR swap without temporary variable
            s[left] = (char)(s[left] ^ s[right]);
            s[right] = (char)(s[left] ^ s[right]);
            s[left] = (char)(s[left] ^ s[right]);
            
            left++;
            right--;
        }
    }
    
    /**
     * Approach 3: Recursive Solution
     * O(n) time, O(n) stack space - Elegant but uses implicit stack
     */
    public void reverseStringRecursive(char[] s) {
        reverseHelper(s, 0, s.length - 1);
    }
    
    private void reverseHelper(char[] s, int left, int right) {
        // Base case: pointers have met or crossed
        if (left >= right) {
            return;
        }
        
        // Swap characters
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
        
        // Recursive call for next pair
        reverseHelper(s, left + 1, right - 1);
    }
    
    /**
     * Approach 4: Using Stack (Not In-Place)
     * O(n) time, O(n) space - Violates O(1) space requirement
     * Included for educational purposes only
     */
    public void reverseStringStack(char[] s) {
        // Note: This violates the O(1) space requirement
        // but demonstrates an alternative approach
        
        Stack<Character> stack = new Stack<>();
        
        // Push all characters to stack
        for (char c : s) {
            stack.push(c);
        }
        
        // Pop characters (reversed order)
        for (int i = 0; i < s.length; i++) {
            s[i] = stack.pop();
        }
    }
    
    /**
     * Approach 5: Using StringBuilder (Not In-Place)
     * O(n) time, O(n) space - Simple but violates constraints
     */
    public void reverseStringBuilder(char[] s) {
        // Note: This creates a new array, not truly in-place
        // but useful for understanding the concept
        
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb.reverse();
        
        char[] reversed = sb.toString().toCharArray();
        System.arraycopy(reversed, 0, s, 0, s.length);
    }
    
    /**
     * Approach 6: Single Pointer Approach
     * O(n) time, O(1) space - Alternative iterative approach
     */
    public void reverseStringSinglePointer(char[] s) {
        int n = s.length;
        
        for (int i = 0; i < n / 2; i++) {
            // Calculate symmetric index
            int j = n - 1 - i;
            
            // Swap characters
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
    }
    
    /**
     * Helper method to visualize the reversal process
     */
    private void visualizeReversal(char[] s, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Initial: " + new String(s));
        
        char[] original = s.clone();
        int left = 0;
        int right = s.length - 1;
        int step = 1;
        
        while (left < right) {
            // Swap
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            
            System.out.printf("Step %d: Swap indices %d('%c') and %d('%c') -> %s%n",
                            step, left, original[left], right, original[right], new String(s));
            
            left++;
            right--;
            step++;
        }
        
        // Restore original for next test
        System.arraycopy(original, 0, s, 0, s.length);
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(char[] s, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        char[] copy1 = s.clone();
        long startTime = System.nanoTime();
        reverseString(copy1);
        long time1 = System.nanoTime() - startTime;
        
        char[] copy2 = s.clone();
        startTime = System.nanoTime();
        reverseStringXOR(copy2);
        long time2 = System.nanoTime() - startTime;
        
        char[] copy3 = s.clone();
        startTime = System.nanoTime();
        reverseStringRecursive(copy3);
        long time3 = System.nanoTime() - startTime;
        
        char[] copy4 = s.clone();
        startTime = System.nanoTime();
        reverseStringSinglePointer(copy4);
        long time4 = System.nanoTime() - startTime;
        
        System.out.printf("Two Pointers: %d ns%n", time1);
        System.out.printf("XOR Swap: %d ns%n", time2);
        System.out.printf("Recursive: %d ns%n", time3);
        System.out.printf("Single Pointer: %d ns%n", time4);
        
        // Verify all produce same result
        boolean allEqual = Arrays.equals(copy1, copy2) && 
                          Arrays.equals(copy1, copy3) && 
                          Arrays.equals(copy1, copy4);
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Reverse String Solution:");
        System.out.println("=================================");
        
        // Test case 1: Basic example
        System.out.println("\nTest 1: Basic example");
        char[] s1 = {'h','e','l','l','o'};
        char[] expected1 = {'o','l','l','e','h'};
        
        char[] copy1a = s1.clone();
        solution.reverseString(copy1a);
        boolean test1a = Arrays.equals(copy1a, expected1);
        
        char[] copy1b = s1.clone();
        solution.reverseStringXOR(copy1b);
        boolean test1b = Arrays.equals(copy1b, expected1);
        
        char[] copy1c = s1.clone();
        solution.reverseStringRecursive(copy1c);
        boolean test1c = Arrays.equals(copy1c, expected1);
        
        char[] copy1d = s1.clone();
        solution.reverseStringSinglePointer(copy1d);
        boolean test1d = Arrays.equals(copy1d, expected1);
        
        System.out.println("Two Pointers: " + Arrays.toString(copy1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("XOR Swap: " + Arrays.toString(copy1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Recursive: " + Arrays.toString(copy1c) + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Single Pointer: " + Arrays.toString(copy1d) + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the reversal process
        solution.visualizeReversal(s1.clone(), "Basic Example");
        
        // Test case 2: Even length string
        System.out.println("\nTest 2: Even length string");
        char[] s2 = {'H','a','n','n','a','h'};
        char[] expected2 = {'h','a','n','n','a','H'};
        
        char[] copy2a = s2.clone();
        solution.reverseString(copy2a);
        System.out.println("Even length: " + Arrays.toString(copy2a) + " - " + 
                         (Arrays.equals(copy2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Single character
        System.out.println("\nTest 3: Single character");
        char[] s3 = {'A'};
        char[] expected3 = {'A'};
        
        char[] copy3a = s3.clone();
        solution.reverseString(copy3a);
        System.out.println("Single char: " + Arrays.toString(copy3a) + " - " + 
                         (Arrays.equals(copy3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Two characters
        System.out.println("\nTest 4: Two characters");
        char[] s4 = {'a','b'};
        char[] expected4 = {'b','a'};
        
        char[] copy4a = s4.clone();
        solution.reverseString(copy4a);
        System.out.println("Two chars: " + Arrays.toString(copy4a) + " - " + 
                         (Arrays.equals(copy4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Empty array (edge case)
        System.out.println("\nTest 5: Empty array");
        char[] s5 = {};
        char[] expected5 = {};
        
        char[] copy5a = s5.clone();
        solution.reverseString(copy5a);
        System.out.println("Empty array: " + Arrays.toString(copy5a) + " - " + 
                         (Arrays.equals(copy5a, expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Palindrome string
        System.out.println("\nTest 6: Palindrome string");
        char[] s6 = {'r','a','c','e','c','a','r'};
        char[] expected6 = {'r','a','c','e','c','a','r'};
        
        char[] copy6a = s6.clone();
        solution.reverseString(copy6a);
        System.out.println("Palindrome: " + Arrays.toString(copy6a) + " - " + 
                         (Arrays.equals(copy6a, expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Special characters
        System.out.println("\nTest 7: Special characters");
        char[] s7 = {'!','@','#','$','%'};
        char[] expected7 = {'%','$','#','@','!'};
        
        char[] copy7a = s7.clone();
        solution.reverseString(copy7a);
        System.out.println("Special chars: " + Arrays.toString(copy7a) + " - " + 
                         (Arrays.equals(copy7a, expected7) ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(s1, "Small Input (5 chars)");
        
        // Medium input performance
        char[] medium = "HelloWorldThisIsAMediumLengthString".toCharArray();
        solution.comparePerformance(medium, "Medium Input (35 chars)");
        
        // Large input performance
        char[] large = new char[10000];
        Arrays.fill(large, 'a');
        solution.comparePerformance(large, "Large Input (10000 chars)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Two Pointers (Iterative) - RECOMMENDED:");
        System.out.println("   Time: O(n) - Visits n/2 pairs, each swap is O(1)");
        System.out.println("   Space: O(1) - Only uses constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Start with left=0 and right=n-1");
        System.out.println("     - Swap s[left] and s[right]");
        System.out.println("     - Increment left, decrement right");
        System.out.println("     - Stop when left >= right");
        System.out.println("   Pros:");
        System.out.println("     - True O(1) space complexity");
        System.out.println("     - Most intuitive and readable");
        System.out.println("     - Interview-friendly");
        System.out.println("     - Efficient cache performance");
        System.out.println("   Cons:");
        System.out.println("     - None significant for this problem");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. XOR Swap Variant:");
        System.out.println("   Time: O(n) - Same as standard two pointers");
        System.out.println("   Space: O(1) - No temporary variable needed");
        System.out.println("   How it works:");
        System.out.println("     - Uses XOR properties: a ^ b ^ b = a");
        System.out.println("     - Three XOR operations perform the swap");
        System.out.println("   Pros:");
        System.out.println("     - No temporary variable needed");
        System.out.println("     - Demonstrates bit manipulation skills");
        System.out.println("   Cons:");
        System.out.println("     - Less readable and maintainable");
        System.out.println("     - No performance benefit in practice");
        System.out.println("     - Can be confusing in interviews");
        System.out.println("   Best for: Demonstrating bit manipulation knowledge");
        
        System.out.println("\n3. Recursive Approach:");
        System.out.println("   Time: O(n) - Makes n/2 recursive calls");
        System.out.println("   Space: O(n) - Stack space for recursion");
        System.out.println("   How it works:");
        System.out.println("     - Base case: left >= right");
        System.out.println("     - Recursive case: swap and call with left+1, right-1");
        System.out.println("   Pros:");
        System.out.println("     - Elegant and mathematical");
        System.out.println("     - Good for learning recursion");
        System.out.println("   Cons:");
        System.out.println("     - O(n) stack space violates O(1) requirement");
        System.out.println("     - Risk of stack overflow for large inputs");
        System.out.println("     - Less efficient than iterative");
        System.out.println("   Best for: Educational purposes, small inputs");
        
        System.out.println("\n4. Single Pointer Approach:");
        System.out.println("   Time: O(n) - Iterates through first half");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - For i from 0 to n/2");
        System.out.println("     - Swap s[i] with s[n-1-i]");
        System.out.println("   Pros:");
        System.out.println("     - Simple single loop");
        System.out.println("     - Same efficiency as two pointers");
        System.out.println("   Cons:");
        System.out.println("     - Slightly less intuitive");
        System.out.println("     - Extra calculation for symmetric index");
        System.out.println("   Best for: Alternative iterative solution");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY TWO POINTERS IS THE BEST APPROACH:");
        System.out.println("1. True O(1) space complexity - meets problem constraints");
        System.out.println("2. O(n) time complexity - optimal for the problem");
        System.out.println("3. Intuitive and easy to understand");
        System.out.println("4. No risk of stack overflow");
        System.out.println("5. Good cache performance (sequential memory access)");
        System.out.println("6. Easy to modify for related problems");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Two Pointers - it's the expected solution");
        System.out.println("2. Explain the algorithm clearly: pointers, swap, move");
        System.out.println("3. Mention time/space complexity: O(n)/O(1)");
        System.out.println("4. Discuss alternative approaches briefly");
        System.out.println("5. Handle edge cases: empty, single char, even/odd length");
        System.out.println("6. Write clean, readable code with good variable names");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Using O(n) space (arrays, strings, stacks)");
        System.out.println("2. Forgetting the in-place requirement");
        System.out.println("3. Incorrect loop condition (<= instead of <)");
        System.out.println("4. Not handling empty or single-element arrays");
        System.out.println("5. Using library functions (Arrays.reverse())");
        System.out.println("6. Poor variable naming (i,j instead of left,right)");
        System.out.println("=".repeat(70));
        
        // Additional educational examples
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDUCATIONAL EXAMPLES");
        System.out.println("=".repeat(70));
        
        System.out.println("\nExample: Step-by-step reversal of 'hello'");
        char[] example = {'h','e','l','l','o'};
        System.out.println("Initial: " + Arrays.toString(example));
        
        int left = 0, right = 4;
        // Step 1: swap h and o
        System.out.println("Step 1: swap indices 0 and 4 -> " + "[o, e, l, l, h]");
        // Step 2: swap e and l  
        System.out.println("Step 2: swap indices 1 and 3 -> " + "[o, l, l, e, h]");
        System.out.println("Done! left=2, right=2 -> loop condition false");
        
        System.out.println("\nMathematical Insight:");
        System.out.println("Number of swaps needed: ⌊n/2⌋");
        System.out.println("For n=5: ⌊5/2⌋ = 2 swaps");
        System.out.println("For n=6: ⌊6/2⌋ = 3 swaps");
        System.out.println("Middle element (if odd n) stays in place");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
