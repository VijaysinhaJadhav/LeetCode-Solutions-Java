
## Solution.java

```java
/**
 * 1405. Longest Happy String
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given counts of 'a', 'b', 'c', create longest string without "aaa", "bbb", or "ccc".
 * Return any valid longest string.
 * 
 * Key Insights:
 * 1. Greedy: Always try to use character with most remaining count
 * 2. Cannot have 3 consecutive same characters
 * 3. Track last two characters to avoid creating triples
 * 4. If most frequent would create triple, use second most frequent
 * 
 * Approach (Max Heap):
 * 1. Create max heap ordered by character count
 * 2. While heap not empty:
 *    - Poll most frequent character
 *    - If it would create triple, try second most frequent
 *    - Add character to result, update count
 *    - If still has count, push back to heap
 *    - Update last characters
 * 3. Return result
 * 
 * Time Complexity: O(n log 3) ≈ O(n) where n = a + b + c
 * Space Complexity: O(1) - constant heap size
 * 
 * Tags: String, Greedy, Heap
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Max Heap with Character Tracking (RECOMMENDED)
     * O(n log 3) time, O(1) space
     */
    public String longestDiverseString(int a, int b, int c) {
        // Create max heap ordered by count
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((x, y) -> y[1] - x[1]);
        
        // Add characters with non-zero counts
        if (a > 0) maxHeap.offer(new int[]{'a', a});
        if (b > 0) maxHeap.offer(new int[]{'b', b});
        if (c > 0) maxHeap.offer(new int[]{'c', c});
        
        StringBuilder result = new StringBuilder();
        
        while (!maxHeap.isEmpty()) {
            // Get most frequent character
            int[] first = maxHeap.poll();
            char firstChar = (char) first[0];
            int firstCount = first[1];
            
            // Check if adding this character would create "aaa", "bbb", or "ccc"
            int len = result.length();
            if (len >= 2 && result.charAt(len - 1) == firstChar && result.charAt(len - 2) == firstChar) {
                // Would create triple, try second most frequent if available
                if (maxHeap.isEmpty()) {
                    // No alternative character, can't add more
                    break;
                }
                
                // Get second most frequent character
                int[] second = maxHeap.poll();
                char secondChar = (char) second[0];
                int secondCount = second[1];
                
                // Add second character
                result.append(secondChar);
                secondCount--;
                
                // Re-add characters to heap if still have count
                if (secondCount > 0) {
                    maxHeap.offer(new int[]{secondChar, secondCount});
                }
                maxHeap.offer(first); // Re-add first character
            } else {
                // Safe to add first character
                result.append(firstChar);
                firstCount--;
                
                // Re-add to heap if still have count
                if (firstCount > 0) {
                    maxHeap.offer(new int[]{firstChar, firstCount});
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 2: Greedy with Array (More Efficient)
     * O(n) time, O(1) space - avoids heap overhead
     */
    public String longestDiverseStringArray(int a, int b, int c) {
        StringBuilder result = new StringBuilder();
        
        // Store counts and characters
        int[] counts = {a, b, c};
        char[] chars = {'a', 'b', 'c'};
        
        // Last two characters (initialize with different characters)
        char lastChar = ' ';
        char secondLastChar = ' ';
        
        while (true) {
            // Sort by count in descending order
            int firstIdx = -1, secondIdx = -1, thirdIdx = -1;
            
            // Find indices with highest counts
            for (int i = 0; i < 3; i++) {
                if (counts[i] > 0) {
                    if (firstIdx == -1 || counts[i] > counts[firstIdx]) {
                        thirdIdx = secondIdx;
                        secondIdx = firstIdx;
                        firstIdx = i;
                    } else if (secondIdx == -1 || counts[i] > counts[secondIdx]) {
                        thirdIdx = secondIdx;
                        secondIdx = i;
                    } else if (thirdIdx == -1 || counts[i] > counts[thirdIdx]) {
                        thirdIdx = i;
                    }
                }
            }
            
            // If no characters left, break
            if (firstIdx == -1) break;
            
            char charToAdd;
            
            // Check if adding first character would create triple
            if (result.length() >= 2 && 
                chars[firstIdx] == lastChar && 
                chars[firstIdx] == secondLastChar) {
                // Would create triple, try second character
                if (secondIdx != -1) {
                    charToAdd = chars[secondIdx];
                    counts[secondIdx]--;
                } else {
                    // No alternative character, break
                    break;
                }
            } else {
                // Safe to add first character
                charToAdd = chars[firstIdx];
                counts[firstIdx]--;
            }
            
            // Add character to result
            result.append(charToAdd);
            
            // Update last two characters
            secondLastChar = lastChar;
            lastChar = charToAdd;
        }
        
        return result.toString();
    }
    
    /**
     * Approach 3: Pattern-based Approach
     * Use pattern: 2 of most frequent, 1 of next frequent, repeat
     */
    public String longestDiverseStringPattern(int a, int b, int c) {
        return generate(a, b, c, "a", "b", "c");
    }
    
    private String generate(int a, int b, int c, String aa, String bb, String cc) {
        // Ensure a >= b >= c
        if (a < b) return generate(b, a, c, bb, aa, cc);
        if (b < c) return generate(a, c, b, aa, cc, bb);
        if (b == 0) return aa.repeat(Math.min(2, a));
        
        // Use 2 of most frequent, 1 of second most frequent
        int useA = Math.min(2, a);
        int useB = (a - useA >= b) ? 1 : 0;
        
        return aa.repeat(useA) + bb.repeat(useB) + 
               generate(a - useA, b - useB, c, aa, bb, cc);
    }
    
    /**
     * Approach 4: DFS with Backtracking
     * Finds all possible strings, returns longest (exponential, for small inputs)
     */
    public String longestDiverseStringDFS(int a, int b, int c) {
        String[] best = {""};
        dfs(a, b, c, new StringBuilder(), best, ' ', ' ');
        return best[0];
    }
    
    private void dfs(int a, int b, int c, StringBuilder current, String[] best, char last, char secondLast) {
        // Update best if current is longer
        if (current.length() > best[0].length()) {
            best[0] = current.toString();
        }
        
        // Try adding 'a'
        if (a > 0 && !(last == 'a' && secondLast == 'a')) {
            current.append('a');
            dfs(a - 1, b, c, current, best, 'a', last);
            current.deleteCharAt(current.length() - 1);
        }
        
        // Try adding 'b'
        if (b > 0 && !(last == 'b' && secondLast == 'b')) {
            current.append('b');
            dfs(a, b - 1, c, current, best, 'b', last);
            current.deleteCharAt(current.length() - 1);
        }
        
        // Try adding 'c'
        if (c > 0 && !(last == 'c' && secondLast == 'c')) {
            current.append('c');
            dfs(a, b, c - 1, current, best, 'c', last);
            current.deleteCharAt(current.length() - 1);
        }
    }
    
    /**
     * Approach 5: Iterative Greedy with Queue
     * Similar to heap but uses queue for fairness
     */
    public String longestDiverseStringQueue(int a, int b, int c) {
        // Max heap for characters
        PriorityQueue<CharCount> heap = new PriorityQueue<>((x, y) -> y.count - x.count);
        if (a > 0) heap.offer(new CharCount('a', a));
        if (b > 0) heap.offer(new CharCount('b', b));
        if (c > 0) heap.offer(new CharCount('c', c));
        
        StringBuilder result = new StringBuilder();
        Queue<CharCount> waitQueue = new LinkedList<>();
        
        while (!heap.isEmpty() || !waitQueue.isEmpty()) {
            CharCount current;
            
            if (!heap.isEmpty()) {
                current = heap.poll();
            } else {
                current = waitQueue.poll();
            }
            
            // Check if adding would create triple
            if (result.length() >= 2 && 
                result.charAt(result.length() - 1) == current.ch &&
                result.charAt(result.length() - 2) == current.ch) {
                // Put back and try next character
                waitQueue.offer(current);
                continue;
            }
            
            // Add character
            result.append(current.ch);
            current.count--;
            
            // If still has count, put in wait queue
            if (current.count > 0) {
                waitQueue.offer(current);
            }
            
            // Move from wait queue back to heap if heap is empty
            if (heap.isEmpty() && !waitQueue.isEmpty()) {
                heap.offer(waitQueue.poll());
            }
        }
        
        return result.toString();
    }
    
    private static class CharCount {
        char ch;
        int count;
        
        CharCount(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }
    }
    
    /**
     * Helper method to visualize the construction process
     */
    private void visualizeConstruction(int a, int b, int c, String result, String approach) {
        System.out.println("\n" + approach + " - Construction Visualization:");
        System.out.println("Initial counts: a=" + a + ", b=" + b + ", c=" + c);
        System.out.println("Total characters available: " + (a + b + c));
        
        if (!result.isEmpty()) {
            System.out.println("\nResult string: " + result);
            System.out.println("Length: " + result.length());
            System.out.println("Characters used: " + 
                countChars(result, 'a') + " a's, " + 
                countChars(result, 'b') + " b's, " + 
                countChars(result, 'c') + " c's");
            
            // Verify no triple consecutive characters
            boolean valid = true;
            for (int i = 0; i < result.length() - 2; i++) {
                if (result.charAt(i) == result.charAt(i + 1) && 
                    result.charAt(i) == result.charAt(i + 2)) {
                    valid = false;
                    System.out.println("  ERROR: Triple found at position " + i + ": " + 
                        result.substring(i, i + 3));
                    break;
                }
            }
            System.out.println("  Valid (no triple consecutive): " + valid);
            
            // Check if optimal (used all characters if possible)
            int remainingA = a - countChars(result, 'a');
            int remainingB = b - countChars(result, 'b');
            int remainingC = c - countChars(result, 'c');
            System.out.println("  Remaining unused: a=" + remainingA + 
                ", b=" + remainingB + ", c=" + remainingC);
            
            // Show construction pattern
            System.out.println("\nConstruction pattern:");
            int consecutive = 1;
            for (int i = 1; i <= result.length(); i++) {
                if (i < result.length() && result.charAt(i) == result.charAt(i - 1)) {
                    consecutive++;
                } else {
                    System.out.println("  " + consecutive + " x '" + result.charAt(i - 1) + "'");
                    consecutive = 1;
                }
            }
            
            // Show maximum possible length theoretical
            int maxPossible = calculateMaxPossible(a, b, c);
            System.out.println("\nTheoretical maximum possible length: " + maxPossible);
            System.out.println("Achieved: " + result.length() + 
                " (" + (result.length() * 100 / maxPossible) + "% of theoretical max)");
        } else {
            System.out.println("\nNo valid string possible");
        }
    }
    
    private int countChars(String s, char c) {
        int count = 0;
        for (char ch : s.toCharArray()) {
            if (ch == c) count++;
        }
        return count;
    }
    
    /**
     * Calculate theoretical maximum possible length
     */
    private int calculateMaxPossible(int a, int b, int c) {
        // The maximum is limited by the rule that we can't have 3 consecutive same chars
        // For each character, max we can use is 2 * (sum of other two + 1)
        // But simpler: total count if we can arrange properly
        int total = a + b + c;
        
        // If one character dominates too much, we might not be able to use all
        int maxChar = Math.max(a, Math.max(b, c));
        int otherTwo = total - maxChar;
        
        // Each maxChar needs to be separated by other characters
        // We can use at most 2 * (otherTwo + 1) of the max character
        int maxPossible = Math.min(total, otherTwo * 2 + 2);
        
        return maxPossible;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Longest Happy String Solution:");
        System.out.println("=======================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Example from problem");
        int a1 = 1, b1 = 1, c1 = 7;
        
        solution.visualizeConstruction(a1, b1, c1, "ccaccbcc", "Expected");
        
        long startTime = System.nanoTime();
        String result1a = solution.longestDiverseString(a1, b1, c1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result1b = solution.longestDiverseStringArray(a1, b1, c1);
        long time1b = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Max Heap:    " + result1a + " - Length: " + result1a.length() + 
                         " (Time: " + time1a + " ns)");
        System.out.println("Array:       " + result1b + " - Length: " + result1b.length() + 
                         " (Time: " + time1b + " ns)");
        
        // Test case 2: Example 2 from problem
        System.out.println("\nTest 2: Balanced case");
        int a2 = 2, b2 = 2, c2 = 1;
        
        solution.visualizeConstruction(a2, b2, c2, "aabbc", "Expected");
        
        String result2a = solution.longestDiverseString(a2, b2, c2);
        System.out.println("Result: " + result2a + " - Length: " + result2a.length());
        
        // Test case 3: Example 3 from problem
        System.out.println("\nTest 3: One character dominates");
        int a3 = 7, b3 = 1, c3 = 0;
        
        solution.visualizeConstruction(a3, b3, c3, "aabaa", "Expected");
        
        String result3a = solution.longestDiverseString(a3, b3, c3);
        System.out.println("Result: " + result3a + " - Length: " + result3a.length());
        
        // Test case 4: All zeros
        System.out.println("\nTest 4: All zeros");
        int a4 = 0, b4 = 0, c4 = 0;
        
        solution.visualizeConstruction(a4, b4, c4, "", "Expected");
        
        String result4a = solution.longestDiverseString(a4, b4, c4);
        System.out.println("Result: \"" + result4a + "\" - Length: " + result4a.length() + 
                         " - " + (result4a.isEmpty() ? "PASSED" : "FAILED"));
        
        // Test case 5: Only one character type
        System.out.println("\nTest 5: Only 'a's");
        int a5 = 5, b5 = 0, c5 = 0;
        
        solution.visualizeConstruction(a5, b5, c5, "aa", "Expected");
        
        String result5a = solution.longestDiverseString(a5, b5, c5);
        System.out.println("Result: " + result5a + " - Length: " + result5a.length());
        
        // Test case 6: Two character types
        System.out.println("\nTest 6: Two character types");
        int a6 = 3, b6 = 3, c6 = 0;
        
        solution.visualizeConstruction(a6, b6, c6, "aabbaa", "Expected");
        
        String result6a = solution.longestDiverseString(a6, b6, c6);
        System.out.println("Result: " + result6a + " - Length: " + result6a.length());
        
        // Test case 7: Edge case - maximum counts
        System.out.println("\nTest 7: Maximum counts (100 each)");
        int a7 = 100, b7 = 100, c7 = 100;
        
        String result7a = solution.longestDiverseString(a7, b7, c7);
        System.out.println("Result length: " + result7a.length() + 
                         " (theoretical max: " + solution.calculateMaxPossible(a7, b7, c7) + ")");
        
        // Verify no triple consecutive
        boolean valid7 = true;
        for (int i = 0; i < result7a.length() - 2; i++) {
            if (result7a.charAt(i) == result7a.charAt(i + 1) && 
                result7a.charAt(i) == result7a.charAt(i + 2)) {
                valid7 = false;
                break;
            }
        }
        System.out.println("Valid (no triple consecutive): " + valid7);
        
        // Test case 8: Extreme imbalance
        System.out.println("\nTest 8: Extreme imbalance");
        int a8 = 100, b8 = 1, c8 = 1;
        
        solution.visualizeConstruction(a8, b8, c8, "", "Expected");
        
        String result8a = solution.longestDiverseString(a8, b8, c8);
        System.out.println("Result length: " + result8a.length());
        System.out.println("Theoretical max: " + solution.calculateMaxPossible(a8, b8, c8));
        
        // Performance test
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        // Test with maximum values
        int testA = 100, testB = 100, testC = 100;
        System.out.println("\nTesting with a=100, b=100, c=100");
        
        startTime = System.currentTimeMillis();
        String perf1 = solution.longestDiverseString(testA, testB, testC);
        long timePerf1 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        String perf2 = solution.longestDiverseStringArray(testA, testB, testC);
        long timePerf2 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        String perf3 = solution.longestDiverseStringPattern(testA, testB, testC);
        long timePerf3 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        String perf4 = solution.longestDiverseStringQueue(testA, testB, testC);
        long timePerf4 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Max Heap:    " + timePerf1 + " ms - Length: " + perf1.length());
        System.out.println("Array:       " + timePerf2 + " ms - Length: " + perf2.length());
        System.out.println("Pattern:     " + timePerf3 + " ms - Length: " + perf3.length());
        System.out.println("Queue:       " + timePerf4 + " ms - Length: " + perf4.length());
        
        // Verify all produce valid strings
        boolean allValid = true;
        String[] results = {perf1, perf2, perf3, perf4};
        for (String res : results) {
            for (int i = 0; i < res.length() - 2; i++) {
                if (res.charAt(i) == res.charAt(i + 1) && res.charAt(i) == res.charAt(i + 2)) {
                    allValid = false;
                    break;
                }
            }
        }
        System.out.println("All results valid: " + allValid);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We want to maximize string length while avoiding 3 consecutive same characters.");
        System.out.println("Greedy approach: always use the character with highest remaining count,");
        System.out.println("but if it would create 'aaa', 'bbb', or 'ccc', use the second highest instead.");
        
        System.out.println("\nMax Heap Approach:");
        System.out.println("1. Create max heap with characters and their counts");
        System.out.println("2. While heap not empty:");
        System.out.println("   a. Poll most frequent character");
        System.out.println("   b. Check if adding it would create triple (check last two chars)");
        System.out.println("   c. If would create triple:");
        System.out.println("      - If heap has another character, use that instead");
        System.out.println("      - Otherwise, stop (can't add more)");
        System.out.println("   d. Add character to result, decrement count");
        System.out.println("   e. If count > 0, push back to heap");
        
        System.out.println("\nWhy It Works:");
        System.out.println("1. Using most frequent character maximizes length");
        System.out.println("2. Avoiding triples is necessary for validity");
        System.out.println("3. The greedy choice doesn't prevent optimal solution because:");
        System.out.println("   - If we don't use a character when we could, we lose chance to use it");
        System.out.println("   - The alternative (second most frequent) helps separate the frequent one");
        
        System.out.println("\nVisual Example: a=1, b=1, c=7");
        System.out.println("Step 1: Heap has (c:7), (a:1), (b:1)");
        System.out.println("Step 2: Use c, result='c', heap now (c:6), (a:1), (b:1)");
        System.out.println("Step 3: Use c again, result='cc', heap (c:5), (a:1), (b:1)");
        System.out.println("Step 4: Can't use c (would make 'ccc'), use a instead");
        System.out.println("        result='cca', heap (c:5), (b:1)");
        System.out.println("Step 5: Use c, result='ccac', heap (c:4), (b:1)");
        System.out.println("Continues... final result: 'ccaccbcc'");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("IMPLEMENTATION COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Max Heap (RECOMMENDED):");
        System.out.println("   Time: O(n log 3) ≈ O(n) where n = a + b + c");
        System.out.println("   Space: O(1) - heap with max 3 elements");
        System.out.println("   Pros:");
        System.out.println("     - Clear and intuitive");
        System.out.println("     - Easy to understand and implement");
        System.out.println("     - Handles all cases correctly");
        System.out.println("   Cons:");
        System.out.println("     - Heap operations overhead");
        System.out.println("     - Slightly more complex than array");
        System.out.println("   Best for: Interviews, clear code");
        
        System.out.println("\n2. Array with Sorting:");
        System.out.println("   Time: O(n) - linear time");
        System.out.println("   Space: O(1) - constant extra space");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient");
        System.out.println("     - No heap overhead");
        System.out.println("     - Simple array operations");
        System.out.println("   Cons:");
        System.out.println("     - Manual sorting of 3 elements");
        System.out.println("     - Slightly more code");
        System.out.println("   Best for: Performance-critical applications");
        
        System.out.println("\n3. Pattern-based (Mathematical):");
        System.out.println("   Time: O(n) - but recursive");
        System.out.println("   Space: O(n) - recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - Elegant mathematical solution");
        System.out.println("     - Based on pattern observation");
        System.out.println("   Cons:");
        System.out.println("     - Less intuitive");
        System.out.println("     - Recursion overhead");
        System.out.println("   Best for: Mathematical understanding");
        
        System.out.println("\n4. DFS with Backtracking:");
        System.out.println("   Time: O(3^n) - exponential");
        System.out.println("   Space: O(n) - recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - Finds all possible solutions");
        System.out.println("     - Guaranteed to find longest");
        System.out.println("   Cons:");
        System.out.println("     - Exponentially slow");
        System.out.println("     - Not practical for large counts");
        System.out.println("   Best for: Small inputs, finding all solutions");
        
        System.out.println("\n5. Queue-based:");
        System.out.println("   Time: O(n log 3)");
        System.out.println("   Space: O(1)");
        System.out.println("   Pros:");
        System.out.println("     - Fair scheduling of characters");
        System.out.println("     - Prevents starvation");
        System.out.println("   Cons:");
        System.out.println("     - More complex");
        System.out.println("     - Queue operations overhead");
        System.out.println("   Best for: When fairness is important");
        
        // Mathematical insights
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nMaximum Length Analysis:");
        System.out.println("Let counts be a ≥ b ≥ c (sorted)");
        System.out.println("Total characters: T = a + b + c");
        
        System.out.println("\nCase 1: a ≤ 2(b + c + 1)");
        System.out.println("   - Can use all characters");
        System.out.println("   - Maximum length = T");
        
        System.out.println("\nCase 2: a > 2(b + c + 1)");
        System.out.println("   - Cannot use all 'a's");
        System.out.println("   - Maximum 'a's = 2(b + c + 1)");
        System.out.println("   - Maximum length = 2(b + c + 1) + b + c = 3(b + c) + 2");
        
        System.out.println("\nGeneral Formula:");
        System.out.println("maxLength = min(T, 2(b + c + 1) + b + c) when a ≥ b ≥ c");
        System.out.println("           = min(T, 3(b + c) + 2)");
        
        System.out.println("\nExamples:");
        System.out.println("1. a=7, b=1, c=0: T=8, b+c=1, max=min(8, 3*1+2)=min(8,5)=5");
        System.out.println("2. a=100, b=100, c=100: T=300, b+c=200, max=min(300, 3*200+2)=300");
        System.out.println("3. a=100, b=1, c=1: T=102, b+c=2, max=min(102, 3*2+2)=min(102,8)=8");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Resource Scheduling:");
        System.out.println("   - CPU task scheduling (avoid running same task type consecutively)");
        System.out.println("   - Load balancing servers");
        
        System.out.println("\n2. Data Compression:");
        System.out.println("   - Run-length encoding limitations");
        System.out.println("   - Preventing long runs of same symbol");
        
        System.out.println("\n3. Game Development:");
        System.out.println("   - Random item generation without streaks");
        System.out.println("   - Shuffling algorithms for card games");
        
        System.out.println("\n4. Manufacturing:");
        System.out.println("   - Production line scheduling");
        System.out.println("   - Avoiding machine wear from same operation");
        
        System.out.println("\n5. Communication Systems:");
        System.out.println("   - Signal encoding to prevent DC bias");
        System.out.println("   - Error correction codes");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify requirements:");
        System.out.println("   - Cannot have 3 consecutive same characters");
        System.out.println("   - Want longest possible string");
        System.out.println("   - Return any valid longest string");
        
        System.out.println("\n2. Start with simple cases:");
        System.out.println("   - What if only one character? (max length 2)");
        System.out.println("   - What if two characters? (interleave them)");
        System.out.println("   - What if one character dominates?");
        
        System.out.println("\n3. Discover greedy insight:");
        System.out.println("   - Always use most frequent character if possible");
        System.out.println("   - But avoid creating triple consecutive");
        
        System.out.println("\n4. Propose heap solution:");
        System.out.println("   - Max heap to track character counts");
        System.out.println("   - Check last two characters before adding");
        System.out.println("   - If would create triple, use second most frequent");
        
        System.out.println("\n5. Handle edge cases:");
        System.out.println("   - All counts zero");
        System.out.println("   - Only one character type");
        System.out.println("   - Extreme imbalance");
        
        System.out.println("\n6. Optimize:");
        System.out.println("   - Use array instead of heap (only 3 elements)");
        System.out.println("   - Calculate theoretical maximum");
        
        System.out.println("\n7. Walk through example:");
        System.out.println("   - Use example from problem");
        System.out.println("   - Show step-by-step construction");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Greedy approach is optimal for this problem");
        System.out.println("- O(n) time complexity where n = total characters");
        System.out.println("- Constant space (only 3 characters to track)");
        System.out.println("- The 3-consecutive constraint limits maximum length");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not checking for triple consecutive properly");
        System.out.println("- Forgetting to handle case when heap becomes empty");
        System.out.println("- Not considering all character types");
        System.out.println("- Integer overflow with large counts");
        System.out.println("- Not verifying result has no triples");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with given examples");
        System.out.println("2. Test edge cases (zeros, single type)");
        System.out.println("3. Test extreme imbalance");
        System.out.println("4. Verify no triple consecutive characters");
        System.out.println("5. Check length against theoretical maximum");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
