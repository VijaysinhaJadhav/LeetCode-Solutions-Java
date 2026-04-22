
# Solution.java

```java
import java.util.*;

/**
 * 6. Zigzag Conversion
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Convert string to zigzag pattern and read row by row.
 * 
 * Key Insights:
 * 1. Create numRows StringBuilder objects
 * 2. Simulate moving down and up through rows
 * 3. Append each character to the current row
 * 4. Concatenate all rows at the end
 */
class Solution {
    
    /**
     * Approach 1: Row-by-Row Simulation (Recommended)
     * Time: O(n), Space: O(n)
     * 
     * Steps:
     * 1. Handle edge case: numRows == 1
     * 2. Create array of StringBuilders for each row
     * 3. Use direction variable to track movement (down or up)
     * 4. For each character, append to current row
     * 5. Change direction at top/bottom rows
     * 6. Concatenate all rows
     */
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        
        int row = 0;
        boolean goingDown = true;
        
        for (char c : s.toCharArray()) {
            rows[row].append(c);
            
            if (goingDown) {
                row++;
                if (row == numRows - 1) {
                    goingDown = false;
                }
            } else {
                row--;
                if (row == 0) {
                    goingDown = true;
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rows) {
            result.append(sb);
        }
        
        return result.toString();
    }
    
    /**
     * Approach 2: Mathematical Index Calculation
     * Time: O(n), Space: O(n)
     * 
     * Uses cycle length to determine row for each character
     */
    public String convertMath(String s, int numRows) {
        if (numRows == 1) return s;
        
        int n = s.length();
        int cycleLen = 2 * numRows - 2;
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j + i < n; j += cycleLen) {
                result.append(s.charAt(j + i));
                if (i != 0 && i != numRows - 1 && j + cycleLen - i < n) {
                    result.append(s.charAt(j + cycleLen - i));
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 3: 2D Grid Simulation
     * Time: O(n × numRows), Space: O(n × numRows)
     * 
     * Less efficient but shows the pattern clearly
     */
    public String convertGrid(String s, int numRows) {
        if (numRows == 1) return s;
        
        int n = s.length();
        int cols = n; // Max possible columns
        char[][] grid = new char[numRows][cols];
        
        int row = 0, col = 0;
        boolean goingDown = true;
        
        for (char c : s.toCharArray()) {
            grid[row][col] = c;
            
            if (goingDown) {
                row++;
                if (row == numRows - 1) {
                    goingDown = false;
                }
            } else {
                row--;
                col++;
                if (row == 0) {
                    goingDown = true;
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != 0) {
                    result.append(grid[i][j]);
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 4: Using List of Strings (No StringBuilder)
     * Time: O(n), Space: O(n)
     * 
     * Simpler but less efficient due to string concatenation
     */
    public String convertList(String s, int numRows) {
        if (numRows == 1) return s;
        
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add("");
        }
        
        int row = 0;
        boolean goingDown = true;
        
        for (char c : s.toCharArray()) {
            rows.set(row, rows.get(row) + c);
            
            if (goingDown) {
                row++;
                if (row == numRows - 1) {
                    goingDown = false;
                }
            } else {
                row--;
                if (row == 0) {
                    goingDown = true;
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (String r : rows) {
            result.append(r);
        }
        
        return result.toString();
    }
    
    /**
     * Approach 5: Direct Index Calculation (Compact)
     * Time: O(n), Space: O(n)
     * 
     * Most efficient mathematical approach
     */
    public String convertCompact(String s, int numRows) {
        if (numRows == 1) return s;
        
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) rows[i] = new StringBuilder();
        
        int row = 0;
        int step = 1; // 1 for down, -1 for up
        
        for (char c : s.toCharArray()) {
            rows[row].append(c);
            row += step;
            if (row == 0 || row == numRows - 1) {
                step = -step;
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rows) result.append(sb);
        
        return result.toString();
    }
    
    /**
     * Helper: Visualize the zigzag pattern
     */
    public void visualizeZigzag(String s, int numRows) {
        System.out.println("\nZigzag Conversion Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.printf("\nString: \"%s\"%n", s);
        System.out.println("NumRows: " + numRows);
        
        if (numRows == 1) {
            System.out.println("\nResult: \"" + s + "\"");
            return;
        }
        
        int n = s.length();
        int cols = n;
        char[][] grid = new char[numRows][cols];
        
        int row = 0, col = 0;
        boolean goingDown = true;
        
        System.out.println("\nZigzag grid:");
        
        for (char c : s.toCharArray()) {
            grid[row][col] = c;
            
            if (goingDown) {
                row++;
                if (row == numRows - 1) {
                    goingDown = false;
                }
            } else {
                row--;
                col++;
                if (row == 0) {
                    goingDown = true;
                }
            }
        }
        
        // Print grid
        for (int i = 0; i < numRows; i++) {
            System.out.print("  ");
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != 0) {
                    System.out.print(grid[i][j]);
                } else {
                    System.out.print(" ");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        
        // Show rows
        System.out.println("\nRows content:");
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) rows[i] = new StringBuilder();
        
        row = 0;
        goingDown = true;
        for (char c : s.toCharArray()) {
            rows[row].append(c);
            
            if (goingDown) {
                row++;
                if (row == numRows - 1) goingDown = false;
            } else {
                row--;
                if (row == 0) goingDown = true;
            }
        }
        
        for (int i = 0; i < numRows; i++) {
            System.out.printf("  Row %d: \"%s\"%n", i, rows[i]);
        }
        
        String result = convert(s, numRows);
        System.out.println("\nFinal result: \"" + result + "\"");
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            {"PAYPALISHIRING", 3, "PAHNAPLSIIGYIR"},
            {"PAYPALISHIRING", 4, "PINALSIGYAHRPI"},
            {"A", 1, "A"},
            {"AB", 1, "AB"},
            {"ABC", 2, "ACB"},
            {"ABCD", 2, "ACBD"},
            {"ABCDE", 4, "ABCED"},
            {"ABCDEFGHIJKLMNOPQRSTUVWXYZ", 5, "AGMSYBFHLNRTXZCEIKOQUWDJPV"}
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        Object[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String s = (String) testCases[i][0];
            int numRows = (int) testCases[i][1];
            String expected = (String) testCases[i][2];
            
            System.out.printf("\nTest %d: s=\"%s\", numRows=%d%n", i + 1, s, numRows);
            
            String result1 = convert(s, numRows);
            String result2 = convertMath(s, numRows);
            String result3 = convertGrid(s, numRows);
            String result4 = convertList(s, numRows);
            String result5 = convertCompact(s, numRows);
            
            boolean allMatch = result1.equals(expected) && result2.equals(expected) &&
                              result3.equals(expected) && result4.equals(expected) &&
                              result5.equals(expected);
            
            if (allMatch) {
                System.out.println("✓ PASS - Result: \"" + result1 + "\"");
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: \"" + expected + "\"");
                System.out.println("  Method 1: \"" + result1 + "\"");
                System.out.println("  Method 2: \"" + result2 + "\"");
                System.out.println("  Method 3: \"" + result3 + "\"");
                System.out.println("  Method 4: \"" + result4 + "\"");
                System.out.println("  Method 5: \"" + result5 + "\"");
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeZigzag(s, numRows);
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
        
        // Generate large test case
        int n = 100000;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append((char) ('A' + i % 26));
        }
        String s = sb.toString();
        int numRows = 100;
        
        System.out.println("Test Setup: string length = " + n + ", numRows = " + numRows);
        
        long[] times = new long[5];
        String[] results = new String[5];
        
        // Method 1: Row-by-Row Simulation
        long start = System.currentTimeMillis();
        results[0] = convert(s, numRows);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Mathematical Index Calculation
        start = System.currentTimeMillis();
        results[1] = convertMath(s, numRows);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: 2D Grid (skip for large n)
        times[2] = -1;
        results[2] = null;
        
        // Method 4: List of Strings
        start = System.currentTimeMillis();
        results[3] = convertList(s, numRows);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Compact
        start = System.currentTimeMillis();
        results[4] = convertCompact(s, numRows);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. Row-by-Row Simulation  | %9d%n", times[0]);
        System.out.printf("2. Mathematical          | %9d%n", times[1]);
        System.out.printf("3. 2D Grid               | %9s%n", "N/A (O(n²))");
        System.out.printf("4. List of Strings       | %9d%n", times[3]);
        System.out.printf("5. Compact               | %9d%n", times[4]);
        
        boolean allMatch = results[0].equals(results[1]) && results[1].equals(results[3]) &&
                          results[3].equals(results[4]);
        System.out.println("\nAll O(n) methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Row-by-row simulation is simple and efficient");
        System.out.println("2. Mathematical approach has similar performance");
        System.out.println("3. 2D grid is too slow for large inputs");
        System.out.println("4. All O(n) methods scale linearly");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. numRows = 1:");
        System.out.println("   Input: s=\"abc\", numRows=1");
        System.out.println("   Output: \"" + convert("abc", 1) + "\"");
        
        System.out.println("\n2. numRows >= string length:");
        System.out.println("   Input: s=\"abc\", numRows=5");
        System.out.println("   Output: \"" + convert("abc", 5) + "\"");
        
        System.out.println("\n3. Single character:");
        System.out.println("   Input: s=\"A\", numRows=3");
        System.out.println("   Output: \"" + convert("A", 3) + "\"");
        
        System.out.println("\n4. All same characters:");
        System.out.println("   Input: s=\"AAAAA\", numRows=3");
        System.out.println("   Output: \"" + convert("AAAAA", 3) + "\"");
        
        System.out.println("\n5. Very large string:");
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".repeat(100);
        long start = System.currentTimeMillis();
        String result = convert(s, 10);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 2600 characters, numRows=10");
        System.out.println("   Output length: " + result.length());
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nZigzag Pattern:");
        System.out.println("The string is written in a zigzag pattern going down and then diagonally up.");
        
        System.out.println("\nExample: s = \"PAYPALISHIRING\", numRows = 3");
        System.out.println("  Row 0: P   A   H   N");
        System.out.println("  Row 1: A P L S I I G");
        System.out.println("  Row 2: Y   I   R");
        System.out.println("  Result: \"PAHNAPLSIIGYIR\"");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Create StringBuilder for each row");
        System.out.println("2. Initialize current row = 0, direction = down");
        System.out.println("3. For each character in string:");
        System.out.println("   - Append character to current row");
        System.out.println("   - If direction is down:");
        System.out.println("       row++");
        System.out.println("       if row == numRows - 1: direction = up");
        System.out.println("   - Else (direction is up):");
        System.out.println("       row--");
        System.out.println("       if row == 0: direction = down");
        System.out.println("4. Concatenate all rows");
        
        System.out.println("\nTime Complexity: O(n)");
        System.out.println("Space Complexity: O(n)");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What is the zigzag pattern? (Down then diagonally up)");
        System.out.println("   - How to handle numRows = 1? (Return original string)");
        System.out.println("   - What about empty string? (Return empty)");
        
        System.out.println("\n2. Start with simulation approach:");
        System.out.println("   - Most intuitive solution");
        System.out.println("   - Walk through example");
        
        System.out.println("\n3. Discuss optimization:");
        System.out.println("   - Use StringBuilder for each row");
        System.out.println("   - Avoid 2D grid (space inefficient)");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - Time: O(n) - each character processed once");
        System.out.println("   - Space: O(n) - storing result");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - numRows = 1");
        System.out.println("   - numRows >= string length");
        System.out.println("   - Single character");
        System.out.println("   - Empty string");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Forgetting to handle numRows = 1");
        System.out.println("   - Off-by-one in row indices");
        System.out.println("   - Using String instead of StringBuilder");
        System.out.println("   - Not resetting direction correctly");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("6. Zigzag Conversion");
        System.out.println("====================");
        
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
        
        System.out.println("\nRecommended Implementation (Row-by-Row Simulation):");
        System.out.println("""
class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        
        int row = 0;
        boolean goingDown = true;
        
        for (char c : s.toCharArray()) {
            rows[row].append(c);
            
            if (goingDown) {
                row++;
                if (row == numRows - 1) {
                    goingDown = false;
                }
            } else {
                row--;
                if (row == 0) {
                    goingDown = true;
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rows) {
            result.append(sb);
        }
        
        return result.toString();
    }
}
            """);
        
        System.out.println("\nAlternative (Mathematical):");
        System.out.println("""
class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        
        StringBuilder result = new StringBuilder();
        int cycleLen = 2 * numRows - 2;
        
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j + i < s.length(); j += cycleLen) {
                result.append(s.charAt(j + i));
                if (i != 0 && i != numRows - 1 && j + cycleLen - i < s.length()) {
                    result.append(s.charAt(j + cycleLen - i));
                }
            }
        }
        
        return result.toString();
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Create StringBuilder for each row");
        System.out.println("2. Simulate movement down and up");
        System.out.println("3. Handle numRows = 1 as special case");
        System.out.println("4. Time complexity: O(n)");
        System.out.println("5. Space complexity: O(n)");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - single pass through string");
        System.out.println("- Space: O(n) - storing result");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle very large strings?");
        System.out.println("2. How would you optimize for memory?");
        System.out.println("3. How would you implement the reverse operation?");
        System.out.println("4. How would you handle Unicode characters?");
        System.out.println("5. How would you modify for different patterns?");
    }
}
