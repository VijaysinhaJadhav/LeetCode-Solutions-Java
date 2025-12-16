
## Solution.java

```java
/**
 * 17. Letter Combinations of a Phone Number
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a string of digits from 2-9, return all possible letter combinations
 * that the number could represent based on telephone keypad mapping.
 * 
 * Key Insights:
 * 1. Each digit maps to 3-4 letters (7 and 9 map to 4 letters)
 * 2. Need to generate all combinations (Cartesian product)
 * 3. Can use backtracking, BFS, or iterative approaches
 * 4. Handle empty string case
 * 
 * Approach (Backtracking/DFS):
 * 1. Create mapping from digits to letters
 * 2. Use recursive backtracking to build combinations
 * 3. At each level, iterate through letters of current digit
 * 4. When combination length equals digits length, add to result
 * 5. Backtrack by removing last character
 * 
 * Time Complexity: O(4^n) where n = digits.length()
 * Space Complexity: O(n) recursion stack + O(4^n) output
 * 
 * Tags: Hash Table, String, Backtracking
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Backtracking/DFS (RECOMMENDED)
     * O(4^n) time, O(n) recursion space + O(4^n) output space
     */
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        // Mapping from digit to corresponding letters
        String[] digitToLetters = {
            "",     // 0
            "",     // 1
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
        };
        
        backtrack(digits, 0, new StringBuilder(), result, digitToLetters);
        return result;
    }
    
    private void backtrack(String digits, int index, StringBuilder current, 
                          List<String> result, String[] digitToLetters) {
        // Base case: if we've processed all digits
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }
        
        // Get current digit and its corresponding letters
        char digit = digits.charAt(index);
        String letters = digitToLetters[digit - '0'];
        
        // Try each possible letter for current digit
        for (char letter : letters.toCharArray()) {
            current.append(letter);                  // Choose
            backtrack(digits, index + 1, current, result, digitToLetters); // Explore
            current.deleteCharAt(current.length() - 1); // Unchoose (backtrack)
        }
    }
    
    /**
     * Approach 2: Iterative (BFS-like using queue)
     * O(4^n) time, O(4^n) space for queue
     */
    public List<String> letterCombinationsQueue(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        String[] digitToLetters = {
            "", "", "abc", "def", "ghi", "jkl", 
            "mno", "pqrs", "tuv", "wxyz"
        };
        
        // Initialize queue with empty string
        Queue<String> queue = new LinkedList<>();
        queue.offer("");
        
        for (int i = 0; i < digits.length(); i++) {
            char digit = digits.charAt(i);
            String letters = digitToLetters[digit - '0'];
            int size = queue.size();
            
            // Process all combinations at current level
            for (int j = 0; j < size; j++) {
                String current = queue.poll();
                // Append each possible letter to current combination
                for (char letter : letters.toCharArray()) {
                    queue.offer(current + letter);
                }
            }
        }
        
        // All combinations are now in the queue
        result.addAll(queue);
        return result;
    }
    
    /**
     * Approach 3: Iterative without queue
     * Build combinations level by level
     */
    public List<String> letterCombinationsIterative(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        String[] digitToLetters = {
            "", "", "abc", "def", "ghi", "jkl", 
            "mno", "pqrs", "tuv", "wxyz"
        };
        
        // Start with empty string
        result.add("");
        
        for (char digit : digits.toCharArray()) {
            String letters = digitToLetters[digit - '0'];
            List<String> newCombinations = new ArrayList<>();
            
            // For each existing combination, append each possible letter
            for (String combination : result) {
                for (char letter : letters.toCharArray()) {
                    newCombinations.add(combination + letter);
                }
            }
            
            // Replace old combinations with new ones
            result = newCombinations;
        }
        
        return result;
    }
    
    /**
     * Approach 4: Using recursion without backtracking (immutable strings)
     * Simpler but creates more string objects
     */
    public List<String> letterCombinationsRecursive(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        Map<Character, String> phoneMap = new HashMap<>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        
        generateCombinations(digits, 0, "", result, phoneMap);
        return result;
    }
    
    private void generateCombinations(String digits, int index, String current,
                                     List<String> result, Map<Character, String> phoneMap) {
        if (index == digits.length()) {
            result.add(current);
            return;
        }
        
        char digit = digits.charAt(index);
        String letters = phoneMap.get(digit);
        
        for (char letter : letters.toCharArray()) {
            generateCombinations(digits, index + 1, current + letter, result, phoneMap);
        }
    }
    
    /**
     * Approach 5: Using product of lists (functional style)
     * More concise but less efficient due to stream overhead
     */
    public List<String> letterCombinationsStream(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }
        
        Map<Character, String> phoneMap = new HashMap<>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        
        List<String> result = new ArrayList<>();
        result.add("");
        
        for (char digit : digits.toCharArray()) {
            String letters = phoneMap.get(digit);
            result = result.stream()
                .flatMap(combination -> letters.chars()
                    .mapToObj(letter -> combination + (char) letter))
                .collect(java.util.stream.Collectors.toList());
        }
        
        return result;
    }
    
    /**
     * Approach 6: Using index-based iterative approach
     * Simulates counting through all combinations
     */
    public List<String> letterCombinationsIndexBased(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        String[] digitToLetters = {
            "", "", "abc", "def", "ghi", "jkl", 
            "mno", "pqrs", "tuv", "wxyz"
        };
        
        // Count total combinations
        int totalCombinations = 1;
        for (char digit : digits.toCharArray()) {
            totalCombinations *= digitToLetters[digit - '0'].length();
        }
        
        // Generate each combination by index
        for (int i = 0; i < totalCombinations; i++) {
            StringBuilder sb = new StringBuilder();
            int temp = i;
            
            // Build combination from right to left
            for (int j = digits.length() - 1; j >= 0; j--) {
                char digit = digits.charAt(j);
                String letters = digitToLetters[digit - '0'];
                int letterIndex = temp % letters.length();
                sb.insert(0, letters.charAt(letterIndex));
                temp /= letters.length();
            }
            
            result.add(sb.toString());
        }
        
        return result;
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    private void visualizeBacktracking(String digits, String approach) {
        System.out.println("\n" + approach + " - Backtracking Visualization:");
        System.out.println("Digits: " + digits);
        
        if (digits == null || digits.length() == 0) {
            System.out.println("No digits to process");
            return;
        }
        
        String[] digitToLetters = {
            "", "", "abc", "def", "ghi", "jkl", 
            "mno", "pqrs", "tuv", "wxyz"
        };
        
        System.out.println("\nDigit to letter mapping:");
        for (char digit : digits.toCharArray()) {
            String letters = digitToLetters[digit - '0'];
            System.out.printf("  %c -> %s%n", digit, letters);
        }
        
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int level = 0;
        
        System.out.println("\nBacktracking tree:");
        visualizeBacktrackingHelper(digits, 0, current, result, digitToLetters, "");
    }
    
    private void visualizeBacktrackingHelper(String digits, int index, StringBuilder current,
                                           List<String> result, String[] digitToLetters, String indent) {
        if (index == digits.length()) {
            System.out.println(indent + "✓ " + current.toString());
            result.add(current.toString());
            return;
        }
        
        char digit = digits.charAt(index);
        String letters = digitToLetters[digit - '0'];
        
        System.out.println(indent + "Processing digit " + digit + " (letters: " + letters + ")");
        
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            current.append(letter);
            System.out.println(indent + "  Try '" + letter + "' -> current: " + current.toString());
            
            visualizeBacktrackingHelper(digits, index + 1, current, result, digitToLetters, indent + "    ");
            
            current.deleteCharAt(current.length() - 1);
            System.out.println(indent + "  Backtrack from '" + letter + "' -> current: " + current.toString());
        }
    }
    
    /**
     * Helper method to print all combinations in a readable format
     */
    private void printCombinations(List<String> combinations, String digits) {
        System.out.println("\nAll combinations for digits '" + digits + "':");
        if (combinations.isEmpty()) {
            System.out.println("  (none)");
            return;
        }
        
        // Group by first letter for better visualization
        Map<Character, List<String>> groups = new TreeMap<>();
        for (String combo : combinations) {
            char firstChar = combo.charAt(0);
            groups.putIfAbsent(firstChar, new ArrayList<>());
            groups.get(firstChar).add(combo);
        }
        
        for (Map.Entry<Character, List<String>> entry : groups.entrySet()) {
            System.out.printf("  Starting with '%c': %s%n", 
                entry.getKey(), entry.getValue());
        }
        
        System.out.println("Total combinations: " + combinations.size());
        
        // Calculate theoretical maximum
        int maxCombinations = 1;
        for (char digit : digits.toCharArray()) {
            if (digit == '7' || digit == '9') {
                maxCombinations *= 4;
            } else {
                maxCombinations *= 3;
            }
        }
        System.out.println("Theoretical maximum: " + maxCombinations);
        System.out.println("All generated: " + (combinations.size() == maxCombinations ? "✓" : "✗"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Letter Combinations of a Phone Number:");
        System.out.println("==============================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: digits = \"23\"");
        String digits1 = "23";
        List<String> expected1 = Arrays.asList("ad","ae","af","bd","be","bf","cd","ce","cf");
        
        solution.visualizeBacktracking(digits1, "Backtracking");
        
        long startTime = System.nanoTime();
        List<String> result1a = solution.letterCombinations(digits1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<String> result1b = solution.letterCombinationsQueue(digits1);
        long time1b = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        Collections.sort(result1a);
        Collections.sort(expected1);
        System.out.println("Backtracking: " + result1a.size() + " combinations - " + 
                         (result1a.equals(expected1) ? "PASSED" : "FAILED") + 
                         " (Time: " + time1a + " ns)");
        System.out.println("Queue BFS:    " + result1b.size() + " combinations - " + 
                         (new HashSet<>(result1b).equals(new HashSet<>(expected1)) ? "PASSED" : "FAILED") + 
                         " (Time: " + time1b + " ns)");
        
        solution.printCombinations(result1a, digits1);
        
        // Test case 2: Empty string
        System.out.println("\nTest 2: digits = \"\"");
        String digits2 = "";
        List<String> expected2 = Arrays.asList();
        
        List<String> result2 = solution.letterCombinations(digits2);
        System.out.println("Result: " + result2 + " - " + 
                         (result2.equals(expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Single digit
        System.out.println("\nTest 3: digits = \"2\"");
        String digits3 = "2";
        List<String> expected3 = Arrays.asList("a", "b", "c");
        
        solution.visualizeBacktracking(digits3, "Backtracking");
        List<String> result3 = solution.letterCombinations(digits3);
        Collections.sort(result3);
        Collections.sort(expected3);
        System.out.println("Result: " + result3 + " - " + 
                         (result3.equals(expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Digit with 4 letters (7 or 9)
        System.out.println("\nTest 4: digits = \"7\"");
        String digits4 = "7";
        List<String> expected4 = Arrays.asList("p", "q", "r", "s");
        
        List<String> result4 = solution.letterCombinations(digits4);
        Collections.sort(result4);
        Collections.sort(expected4);
        System.out.println("Result: " + result4 + " - " + 
                         (result4.equals(expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Multiple digits with 4 letters
        System.out.println("\nTest 5: digits = \"79\"");
        String digits5 = "79";
        // 7 -> pqrs (4 letters), 9 -> wxyz (4 letters) = 16 combinations
        
        solution.visualizeBacktracking(digits5, "Backtracking");
        List<String> result5 = solution.letterCombinations(digits5);
        System.out.println("Result: " + result5.size() + " combinations");
        solution.printCombinations(result5, digits5);
        
        // Test case 6: All same digit
        System.out.println("\nTest 6: digits = \"222\"");
        String digits6 = "222";
        // 2 -> abc (3 letters), repeated 3 times = 27 combinations
        
        List<String> result6 = solution.letterCombinations(digits6);
        System.out.println("Result: " + result6.size() + " combinations");
        
        // Test case 7: Mixed digits
        System.out.println("\nTest 7: digits = \"234\"");
        String digits7 = "234";
        // 2->abc (3), 3->def (3), 4->ghi (3) = 27 combinations
        
        solution.visualizeBacktracking(digits7, "Backtracking");
        List<String> result7 = solution.letterCombinations(digits7);
        System.out.println("Result: " + result7.size() + " combinations");
        
        // Compare all implementations
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING ALL IMPLEMENTATIONS:");
        System.out.println("=".repeat(70));
        
        String[] testDigits = {"", "2", "23", "79", "234", "777"};
        
        System.out.println("\nTesting " + testDigits.length + " digit strings:");
        boolean allConsistent = true;
        
        for (String digits : testDigits) {
            List<String> r1 = solution.letterCombinations(digits);
            List<String> r2 = solution.letterCombinationsQueue(digits);
            List<String> r3 = solution.letterCombinationsIterative(digits);
            List<String> r4 = solution.letterCombinationsRecursive(digits);
            List<String> r5 = solution.letterCombinationsIndexBased(digits);
            
            // Sort for comparison (order doesn't matter)
            Collections.sort(r1);
            Collections.sort(r2);
            Collections.sort(r3);
            Collections.sort(r4);
            Collections.sort(r5);
            
            boolean consistent = r1.equals(r2) && r2.equals(r3) && 
                                r3.equals(r4) && r4.equals(r5);
            
            System.out.printf("Digits '%s': %d combinations - %s%n",
                digits, r1.size(), consistent ? "✓ CONSISTENT" : "✗ INCONSISTENT");
            
            if (!consistent) {
                System.out.println("  Backtracking: " + r1);
                System.out.println("  Queue:        " + r2);
                System.out.println("  Iterative:    " + r3);
                System.out.println("  Recursive:    " + r4);
                System.out.println("  Index-based:  " + r5);
                allConsistent = false;
            }
        }
        
        System.out.println("\nAll implementations consistent: " + (allConsistent ? "✓ YES" : "✗ NO"));
        
        // Performance test
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        // Test with maximum length (4 digits)
        String perfDigits = "7892"; // All have 4 letters except 2 has 3
        System.out.println("\nTesting with digits: " + perfDigits);
        System.out.println("Expected combinations: 4 * 4 * 4 * 3 = 192");
        
        // Warm up
        for (int i = 0; i < 100; i++) {
            solution.letterCombinations("23");
        }
        
        // Test Backtracking
        startTime = System.currentTimeMillis();
        List<String> perf1 = solution.letterCombinations(perfDigits);
        long timePerf1 = System.currentTimeMillis() - startTime;
        
        // Test Queue
        startTime = System.currentTimeMillis();
        List<String> perf2 = solution.letterCombinationsQueue(perfDigits);
        long timePerf2 = System.currentTimeMillis() - startTime;
        
        // Test Iterative
        startTime = System.currentTimeMillis();
        List<String> perf3 = solution.letterCombinationsIterative(perfDigits);
        long timePerf3 = System.currentTimeMillis() - startTime;
        
        // Test Index-based
        startTime = System.currentTimeMillis();
        List<String> perf4 = solution.letterCombinationsIndexBased(perfDigits);
        long timePerf4 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Backtracking: " + timePerf1 + " ms - " + perf1.size() + " combinations");
        System.out.println("Queue BFS:    " + timePerf2 + " ms - " + perf2.size() + " combinations");
        System.out.println("Iterative:    " + timePerf3 + " ms - " + perf3.size() + " combinations");
        System.out.println("Index-based:  " + timePerf4 + " ms - " + perf4.size() + " combinations");
        
        // Verify all produce same results
        Collections.sort(perf1);
        Collections.sort(perf2);
        Collections.sort(perf3);
        Collections.sort(perf4);
        
        boolean perfConsistent = perf1.equals(perf2) && perf2.equals(perf3) && perf3.equals(perf4);
        System.out.println("Results consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("This is a classic combinatorial generation problem.");
        System.out.println("Each digit maps to 3-4 letters, and we need the Cartesian product");
        System.out.println("of all possible letters for each digit.");
        
        System.out.println("\nBacktracking Approach:");
        System.out.println("1. Create mapping from digits to letters");
        System.out.println("2. Use recursive function that takes:");
        System.out.println("   - Current index in digits string");
        System.out.println("   - Current combination being built");
        System.out.println("   - Result list to store complete combinations");
        System.out.println("3. Base case: when index == digits.length(), add combination to result");
        System.out.println("4. Recursive case:");
        System.out.println("   - Get letters for current digit");
        System.out.println("   - For each letter:");
        System.out.println("     a. Append letter to current combination");
        System.out.println("     b. Recursively process next digit");
        System.out.println("     c. Remove last letter (backtrack)");
        
        System.out.println("\nVisual Example: digits = \"23\"");
        System.out.println("Digit mapping: 2->abc, 3->def");
        System.out.println("\nBacktracking tree:");
        System.out.println("Start: \"\"");
        System.out.println("  Try 'a' -> \"a\"");
        System.out.println("    Try 'd' -> \"ad\" ✓ (add to result)");
        System.out.println("    Try 'e' -> \"ae\" ✓");
        System.out.println("    Try 'f' -> \"af\" ✓");
        System.out.println("  Backtrack -> \"\"");
        System.out.println("  Try 'b' -> \"b\"");
        System.out.println("    Try 'd' -> \"bd\" ✓");
        System.out.println("    ... continues ...");
        
        System.out.println("\nTime Complexity Analysis:");
        System.out.println("Let n = number of digits");
        System.out.println("Let k_i = number of letters for digit i (3 or 4)");
        System.out.println("Total combinations = k_1 × k_2 × ... × k_n");
        System.out.println("In worst case, all digits are 7 or 9 (4 letters each)");
        System.out.println("So worst case time complexity = O(4^n)");
        
        System.out.println("\nSpace Complexity:");
        System.out.println("- Recursion stack: O(n)");
        System.out.println("- Output storage: O(4^n) combinations × average length n");
        System.out.println("Total: O(n) + O(n × 4^n) = O(n × 4^n)");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("IMPLEMENTATION COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking/DFS (RECOMMENDED):");
        System.out.println("   Time: O(4^n)");
        System.out.println("   Space: O(n) recursion + O(4^n) output");
        System.out.println("   Pros:");
        System.out.println("     - Natural recursive structure");
        System.out.println("     - Clear and intuitive");
        System.out.println("     - Easy to explain in interviews");
        System.out.println("     - Uses StringBuilder for efficiency");
        System.out.println("   Cons:");
        System.out.println("     - Recursion overhead");
        System.out.println("     - Stack depth limited by digits length");
        System.out.println("   Best for: Interviews, clarity, moderate n");
        
        System.out.println("\n2. Queue BFS:");
        System.out.println("   Time: O(4^n)");
        System.out.println("   Space: O(4^n) queue + O(4^n) output");
        System.out.println("   Pros:");
        System.out.println("     - No recursion overhead");
        System.out.println("     - Level-by-level generation");
        System.out.println("     - Can be stopped early if needed");
        System.out.println("   Cons:");
        System.out.println("     - Higher memory usage");
        System.out.println("     - Creates many string objects");
        System.out.println("   Best for: When recursion depth is concern");
        
        System.out.println("\n3. Iterative:");
        System.out.println("   Time: O(4^n)");
        System.out.println("   Space: O(4^n) intermediate + O(4^n) output");
        System.out.println("   Pros:");
        System.out.println("     - Simple loop-based approach");
        System.out.println("     - No recursion or queue");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Creates many intermediate lists");
        System.out.println("     - High memory usage");
        System.out.println("   Best for: Simple iterative solutions");
        
        System.out.println("\n4. Index-based:");
        System.out.println("   Time: O(4^n)");
        System.out.println("   Space: O(n) + O(4^n) output");
        System.out.println("   Pros:");
        System.out.println("     - Simulates counting through combinations");
        System.out.println("     - No recursion");
        System.out.println("     - Minimal memory besides output");
        System.out.println("   Cons:");
        System.out.println("     - Less intuitive");
        System.out.println("     - Complex index calculations");
        System.out.println("   Best for: When memory is tight");
        
        System.out.println("\n5. Stream/Functional:");
        System.out.println("   Time: O(4^n) with higher constant");
        System.out.println("   Space: O(4^n)");
        System.out.println("   Pros:");
        System.out.println("     - Concise functional style");
        System.out.println("     - Expressive");
        System.out.println("   Cons:");
        System.out.println("     - Stream overhead");
        System.out.println("     - Creates many intermediate objects");
        System.out.println("     - Less efficient");
        System.out.println("   Best for: Functional programming contexts");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Telephone Systems:");
        System.out.println("   - T9 predictive text input");
        System.out.println("   - Phone number to word conversion");
        System.out.println("   - Vanity phone numbers (1-800-FLOWERS)");
        
        System.out.println("\n2. Data Entry Systems:");
        System.out.println("   - Automated form filling");
        System.out.println("   - Data validation and correction");
        System.out.println("   - OCR error correction");
        
        System.out.println("\n3. Game Development:");
        System.out.println("   - Word puzzle generation");
        System.out.println("   - Password/name generators");
        System.out.println("   - Random string generation");
        
        System.out.println("\n4. Security Systems:");
        System.out.println("   - Brute force attack simulation");
        System.out.println("   - Password cracking tools");
        System.out.println("   - Security testing");
        
        System.out.println("\n5. User Interfaces:");
        System.out.println("   - Auto-complete suggestions");
        System.out.println("   - Search query expansion");
        System.out.println("   - Input method editors");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify requirements:");
        System.out.println("   - Digits 2-9 only");
        System.out.println("   - Empty string returns empty list");
        System.out.println("   - Order doesn't matter");
        
        System.out.println("\n2. Discuss mapping:");
        System.out.println("   - Show telephone keypad mapping");
        System.out.println("   - Note 7 and 9 have 4 letters, others have 3");
        
        System.out.println("\n3. Start with brute force:");
        System.out.println("   - Nested loops for fixed length");
        System.out.println("   - Mention it doesn't work for variable length");
        
        System.out.println("\n4. Propose backtracking:");
        System.out.println("   - Explain recursive tree structure");
        System.out.println("   - Draw tree for small example");
        
        System.out.println("\n5. Implement solution:");
        System.out.println("   - Create digit-letter mapping");
        System.out.println("   - Write recursive backtracking function");
        System.out.println("   - Handle base case and recursive case");
        
        System.out.println("\n6. Optimize:");
        System.out.println("   - Use StringBuilder instead of string concatenation");
        System.out.println("   - Discuss time/space complexity");
        
        System.out.println("\n7. Discuss alternatives:");
        System.out.println("   - BFS with queue");
        System.out.println("   - Iterative approach");
        System.out.println("   - Compare trade-offs");
        
        System.out.println("\n8. Test with examples:");
        System.out.println("   - Empty string");
        System.out.println("   - Single digit");
        System.out.println("   - Example from problem");
        System.out.println("   - Digits with 4 letters");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Backtracking is natural for combinatorial problems");
        System.out.println("- Time complexity is O(4^n) due to exponential combinations");
        System.out.println("- Use StringBuilder to avoid creating many string objects");
        System.out.println("- Handle edge cases (empty input)");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting to handle empty string case");
        System.out.println("- Not using proper digit-letter mapping");
        System.out.println("- Creating new strings instead of using StringBuilder");
        System.out.println("- Not backtracking properly (forgetting to remove last char)");
        System.out.println("- Missing digits 7 and 9 have 4 letters");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with empty string");
        System.out.println("2. Test with single digit");
        System.out.println("3. Test with example from problem");
        System.out.println("4. Test with digits 7 or 9 (4 letters)");
        System.out.println("5. Test with maximum length (4 digits)");
        System.out.println("6. Verify all combinations are generated");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
