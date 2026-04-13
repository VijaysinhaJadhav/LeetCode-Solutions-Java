
# Solution.java

```java
import java.util.*;

/**
 * 2375. Construct Smallest Number From DI String
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given pattern of 'I' (increase) and 'D' (decrease), construct smallest
 * permutation of 1..n+1 that follows the pattern.
 * 
 * Key Insights:
 * 1. Use stack to handle 'D' sequences
 * 2. When encountering 'I', pop all from stack
 * 3. This ensures decreasing sequences are handled in reverse order
 */
class Solution {
    
    /**
     * Approach 1: Stack-based Greedy (Recommended)
     * Time: O(n), Space: O(n)
     * 
     * Steps:
     * 1. Initialize stack and result StringBuilder
     * 2. For i from 0 to n:
     *    - Push current number (i+1) to stack
     *    - If i == n or pattern[i] == 'I':
     *        - Pop all numbers from stack to result
     * 3. Return result as string
     */
    public String smallestNumber(String pattern) {
        int n = pattern.length();
        StringBuilder result = new StringBuilder();
        Deque<Integer> stack = new ArrayDeque<>();
        
        for (int i = 0; i <= n; i++) {
            stack.push(i + 1);
            
            // If we're at the end or next char is 'I', pop stack
            if (i == n || pattern.charAt(i) == 'I') {
                while (!stack.isEmpty()) {
                    result.append(stack.pop());
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 2: Backtracking (DFS)
     * Time: O((n+1)!), Space: O(n)
     * 
     * Try all permutations, validate pattern
     */
    public String smallestNumberBacktracking(String pattern) {
        int n = pattern.length();
        boolean[] used = new boolean[n + 2];
        StringBuilder result = new StringBuilder();
        
        backtrack(pattern, used, new StringBuilder(), result);
        return result.toString();
    }
    
    private void backtrack(String pattern, boolean[] used, StringBuilder current, StringBuilder result) {
        if (result.length() > 0) return; // Already found smallest
        
        if (current.length() == pattern.length() + 1) {
            if (isValid(current, pattern)) {
                result.append(current);
            }
            return;
        }
        
        for (int num = 1; num <= pattern.length() + 1; num++) {
            if (!used[num]) {
                // Pruning: check if current partial follows pattern
                if (current.length() > 0) {
                    char expected = pattern.charAt(current.length() - 1);
                    int prev = current.charAt(current.length() - 1) - '0';
                    if ((expected == 'I' && prev > num) || (expected == 'D' && prev < num)) {
                        continue;
                    }
                }
                
                used[num] = true;
                current.append(num);
                backtrack(pattern, used, current, result);
                current.deleteCharAt(current.length() - 1);
                used[num] = false;
            }
        }
    }
    
    private boolean isValid(StringBuilder sb, String pattern) {
        for (int i = 0; i < pattern.length(); i++) {
            int a = sb.charAt(i) - '0';
            int b = sb.charAt(i + 1) - '0';
            if ((pattern.charAt(i) == 'I' && a > b) || (pattern.charAt(i) == 'D' && a < b)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Approach 3: Two-Pointer with Reverse
     * Time: O(n), Space: O(n)
     * 
     * For each 'D' run, reverse that segment
     */
    public String smallestNumberReverse(String pattern) {
        int n = pattern.length();
        char[] result = new char[n + 1];
        
        // Initialize with 1,2,3,...,n+1
        for (int i = 0; i <= n; i++) {
            result[i] = (char) ('1' + i);
        }
        
        int i = 0;
        while (i < n) {
            if (pattern.charAt(i) == 'D') {
                int start = i;
                while (i < n && pattern.charAt(i) == 'D') {
                    i++;
                }
                reverse(result, start, i);
            } else {
                i++;
            }
        }
        
        return new String(result);
    }
    
    private void reverse(char[] arr, int start, int end) {
        while (start < end) {
            char temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    
    /**
     * Approach 4: Using List and Reverse
     * Time: O(n), Space: O(n)
     * 
     * Build list of numbers, reverse D segments
     */
    public String smallestNumberList(String pattern) {
        int n = pattern.length();
        List<Integer> result = new ArrayList<>();
        
        for (int i = 0; i <= n; i++) {
            result.add(i + 1);
            
            if (i == n || pattern.charAt(i) == 'I') {
                // Reverse the D segment
                int start = result.size() - 1;
                while (start > 0 && pattern.charAt(start - 1) == 'D') {
                    start--;
                }
                Collections.reverse(result.subList(start, result.size()));
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for (int num : result) {
            sb.append(num);
        }
        return sb.toString();
    }
    
    /**
     * Approach 5: Greedy with Counting D's
     * Time: O(n²), Space: O(n)
     * 
     * For each position, count consecutive D's to determine number
     */
    public String smallestNumberGreedy(String pattern) {
        int n = pattern.length();
        StringBuilder result = new StringBuilder();
        int[] ans = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            ans[i] = i + 1;
        }
        
        int i = 0;
        while (i < n) {
            if (pattern.charAt(i) == 'D') {
                int start = i;
                while (i < n && pattern.charAt(i) == 'D') {
                    i++;
                }
                // Reverse segment
                int left = start;
                int right = i;
                while (left < right) {
                    int temp = ans[left];
                    ans[left] = ans[right];
                    ans[right] = temp;
                    left++;
                    right--;
                }
            } else {
                i++;
            }
        }
        
        for (int num : ans) {
            result.append(num);
        }
        return result.toString();
    }
    
    /**
     * Helper: Visualize the stack process
     */
    public void visualizeStackProcess(String pattern) {
        System.out.println("\nConstruct Smallest Number Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nPattern: \"" + pattern + "\"");
        System.out.println("Length: " + pattern.length());
        
        int n = pattern.length();
        Deque<Integer> stack = new ArrayDeque<>();
        StringBuilder result = new StringBuilder();
        
        System.out.println("\nStep-by-step stack process:");
        System.out.println("Step | Action | Stack | Result");
        System.out.println("-----|--------|-------|--------");
        
        for (int i = 0; i <= n; i++) {
            stack.push(i + 1);
            System.out.printf("%4d | push %d | %s | %s%n", i + 1, i + 1, stack, result);
            
            if (i == n || pattern.charAt(i) == 'I') {
                while (!stack.isEmpty()) {
                    int popped = stack.pop();
                    result.append(popped);
                    System.out.printf("     | pop %d  | %s | %s%n", popped, stack, result);
                }
            }
        }
        
        System.out.println("\nFinal result: " + result.toString());
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[]{
            "IDID",    // "13254"
            "III",     // "1234"
            "DDI",     // "3214"
            "I",       // "12"
            "D",       // "21"
            "II",      // "123"
            "DD",      // "321"
            "ID",      // "132"
            "DI",      // "213"
            "IIDD",    // "126543"
            "DDID",    // "32154"
            "DIDI"     // "21435"
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[] testCases = generateTestCases();
        String[] expected = {
            "13254", "1234", "3214", "12", "21", "123", "321",
            "132", "213", "126543", "32154", "21435"
        };
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String pattern = testCases[i];
            System.out.printf("\nTest %d: pattern=\"%s\"%n", i + 1, pattern);
            
            String result1 = smallestNumber(pattern);
            String result2 = smallestNumberBacktracking(pattern);
            String result3 = smallestNumberReverse(pattern);
            String result4 = smallestNumberList(pattern);
            String result5 = smallestNumberGreedy(pattern);
            
            boolean allMatch = result1.equals(expected[i]) && result2.equals(expected[i]) &&
                              result3.equals(expected[i]) && result4.equals(expected[i]) &&
                              result5.equals(expected[i]);
            
            if (allMatch) {
                System.out.println("✓ PASS - Result: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeStackProcess(pattern);
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
        
        // Max length pattern (n=8)
        String pattern = "DIDIDIDI";
        
        System.out.println("Test Setup: pattern length = " + pattern.length());
        
        long[] times = new long[5];
        String[] results = new String[5];
        
        // Method 1: Stack
        long start = System.currentTimeMillis();
        results[0] = smallestNumber(pattern);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Backtracking
        start = System.currentTimeMillis();
        results[1] = smallestNumberBacktracking(pattern);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Reverse
        start = System.currentTimeMillis();
        results[2] = smallestNumberReverse(pattern);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: List
        start = System.currentTimeMillis();
        results[3] = smallestNumberList(pattern);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Greedy
        start = System.currentTimeMillis();
        results[4] = smallestNumberGreedy(pattern);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. Stack                  | %9d%n", times[0]);
        System.out.printf("2. Backtracking           | %9d%n", times[1]);
        System.out.printf("3. Reverse                | %9d%n", times[2]);
        System.out.printf("4. List + Reverse         | %9d%n", times[3]);
        System.out.printf("5. Greedy                 | %9d%n", times[4]);
        
        boolean allMatch = results[0].equals(results[1]) && results[1].equals(results[2]) &&
                          results[2].equals(results[3]) && results[3].equals(results[4]);
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Stack approach is fastest and most elegant");
        System.out.println("2. Backtracking is exponential and slower for n=8");
        System.out.println("3. Reverse and greedy methods also O(n)");
        System.out.println("4. Stack approach is most readable");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Length 1 pattern 'I':");
        System.out.println("   Input: \"I\"");
        System.out.println("   Output: " + smallestNumber("I"));
        
        System.out.println("\n2. Length 1 pattern 'D':");
        System.out.println("   Input: \"D\"");
        System.out.println("   Output: " + smallestNumber("D"));
        
        System.out.println("\n3. All 'I' (n=8):");
        System.out.println("   Input: \"IIIIIIII\"");
        System.out.println("   Output: " + smallestNumber("IIIIIIII"));
        
        System.out.println("\n4. All 'D' (n=8):");
        System.out.println("   Input: \"DDDDDDDD\"");
        System.out.println("   Output: " + smallestNumber("DDDDDDDD"));
        
        System.out.println("\n5. Alternating pattern (n=8):");
        System.out.println("   Input: \"IDIDIDID\"");
        System.out.println("   Output: " + smallestNumber("IDIDIDID"));
        
        System.out.println("\n6. Maximum length (n=8):");
        String pattern = "DDDDDDDD";
        long start = System.currentTimeMillis();
        String result = smallestNumber(pattern);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: \"DDDDDDDD\"");
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain stack approach
     */
    public void explainStackApproach() {
        System.out.println("\nStack Approach Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nIntuition:");
        System.out.println("When we see a 'D', we need a decreasing sequence.");
        System.out.println("The stack allows us to delay output until we know the full decreasing run.");
        
        System.out.println("\nAlgorithm:");
        System.out.println("1. For i from 0 to n (inclusive):");
        System.out.println("   - Push number i+1 onto stack");
        System.out.println("   - If we're at the end OR pattern[i] == 'I':");
        System.out.println("       Pop all numbers from stack to result");
        System.out.println("2. Return result");
        
        System.out.println("\nWhy it works:");
        System.out.println("- 'I' means the next number should be larger");
        System.out.println("- So we output the current stack (which contains the decreasing sequence in reverse)");
        System.out.println("- This ensures we always pick the smallest possible number for each position");
        
        System.out.println("\nExample: pattern = \"IDID\"");
        System.out.println("  i=0: push 1, pattern[0]='I' → pop 1 → result=1");
        System.out.println("  i=1: push 2, pattern[1]='D' → no pop");
        System.out.println("  i=2: push 3, pattern[2]='I' → pop 3,2 → result=132");
        System.out.println("  i=3: push 4, pattern[3]='D' → no pop");
        System.out.println("  i=4: push 5, end → pop 5,4 → result=13254");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What does 'I' and 'D' mean? (Increase/Decrease)");
        System.out.println("   - What numbers can we use? (1 to n+1)");
        System.out.println("   - Lexicographically smallest? (Yes)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Generate all permutations O((n+1)!)");
        System.out.println("   - Too slow for n=8 (9! = 362,880)");
        
        System.out.println("\n3. Propose stack approach:");
        System.out.println("   - Explain how stack handles 'D' sequences");
        System.out.println("   - Walk through example");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - Time: O(n) - single pass");
        System.out.println("   - Space: O(n) for stack");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - Single character pattern");
        System.out.println("   - All 'I's (returns 1,2,3,...,n+1)");
        System.out.println("   - All 'D's (returns n+1,n,...,1)");
        System.out.println("   - Maximum length (n=8)");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Using numbers 0..n instead of 1..n+1");
        System.out.println("   - Forgetting to handle the last number");
        System.out.println("   - Not understanding why stack works");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("2375. Construct Smallest Number From DI String");
        System.out.println("=============================================");
        
        // Explain stack approach
        solution.explainStackApproach();
        
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
        
        System.out.println("\nRecommended Implementation (Stack):");
        System.out.println("""
class Solution {
    public String smallestNumber(String pattern) {
        int n = pattern.length();
        StringBuilder result = new StringBuilder();
        Deque<Integer> stack = new ArrayDeque<>();
        
        for (int i = 0; i <= n; i++) {
            stack.push(i + 1);
            
            if (i == n || pattern.charAt(i) == 'I') {
                while (!stack.isEmpty()) {
                    result.append(stack.pop());
                }
            }
        }
        
        return result.toString();
    }
}
            """);
        
        System.out.println("\nAlternative (Reverse Segments):");
        System.out.println("""
class Solution {
    public String smallestNumber(String pattern) {
        int n = pattern.length();
        char[] result = new char[n + 1];
        
        for (int i = 0; i <= n; i++) {
            result[i] = (char) ('1' + i);
        }
        
        int i = 0;
        while (i < n) {
            if (pattern.charAt(i) == 'D') {
                int start = i;
                while (i < n && pattern.charAt(i) == 'D') {
                    i++;
                }
                reverse(result, start, i);
            } else {
                i++;
            }
        }
        
        return new String(result);
    }
    
    private void reverse(char[] arr, int start, int end) {
        while (start < end) {
            char temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Stack elegantly handles 'D' sequences");
        System.out.println("2. When encountering 'I', pop all from stack");
        System.out.println("3. Numbers are 1..n+1 (not 0..n)");
        System.out.println("4. Time complexity: O(n)");
        System.out.println("5. Space complexity: O(n)");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - single pass through pattern");
        System.out.println("- Space: O(n) - stack stores up to n elements");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you modify for lexicographically largest?");
        System.out.println("2. How would you handle duplicate numbers?");
        System.out.println("3. How would you verify a given permutation follows the pattern?");
        System.out.println("4. What if numbers could be any digits (not 1..n+1)?");
        System.out.println("5. How would you construct the largest number?");
    }
}
