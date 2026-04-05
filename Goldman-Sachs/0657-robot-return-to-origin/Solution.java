
# Solution.java

```java
import java.util.*;

/**
 * 657. Robot Return to Origin
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Determine if robot returns to origin after sequence of moves.
 * Moves: U (up), D (down), L (left), R (right)
 * 
 * Key Insights:
 * 1. Count U vs D and L vs R
 * 2. Return true if U count == D count and L count == R count
 * 3. One pass O(n) solution is optimal
 */
class Solution {
    
    /**
     * Approach 1: Counting (Net Displacement) - RECOMMENDED
     * Time: O(n), Space: O(1)
     * 
     * Steps:
     * 1. Count number of U, D, L, R
     * 2. Check if U == D and L == R
     */
    public boolean judgeCircle(String moves) {
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        
        for (char c : moves.toCharArray()) {
            switch (c) {
                case 'U': up++; break;
                case 'D': down++; break;
                case 'L': left++; break;
                case 'R': right++; break;
            }
        }
        
        return up == down && left == right;
    }
    
    /**
     * Approach 2: Net Displacement with Two Variables
     * Time: O(n), Space: O(1)
     * 
     * Track x and y coordinates directly
     */
    public boolean judgeCircleTwoVar(String moves) {
        int x = 0;
        int y = 0;
        
        for (char c : moves.toCharArray()) {
            switch (c) {
                case 'U': y++; break;
                case 'D': y--; break;
                case 'L': x--; break;
                case 'R': x++; break;
            }
        }
        
        return x == 0 && y == 0;
    }
    
    /**
     * Approach 3: Using HashMap
     * Time: O(n), Space: O(1) (only 4 keys)
     * 
     * More scalable for different character sets
     */
    public boolean judgeCircleHashMap(String moves) {
        Map<Character, Integer> count = new HashMap<>();
        
        for (char c : moves.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }
        
        return count.getOrDefault('U', 0) == count.getOrDefault('D', 0) &&
               count.getOrDefault('L', 0) == count.getOrDefault('R', 0);
    }
    
    /**
     * Approach 4: Using Array for Frequency
     * Time: O(n), Space: O(1)
     * 
     * Efficient for fixed character set
     */
    public boolean judgeCircleArray(String moves) {
        int[] freq = new int[128]; // ASCII size
        for (char c : moves.toCharArray()) {
            freq[c]++;
        }
        return freq['U'] == freq['D'] && freq['L'] == freq['R'];
    }
    
    /**
     * Approach 5: Stream API (Functional)
     * Time: O(n), Space: O(1)
     * 
     * Java 8+ functional approach
     */
    public boolean judgeCircleStream(String moves) {
        long up = moves.chars().filter(c -> c == 'U').count();
        long down = moves.chars().filter(c -> c == 'D').count();
        long left = moves.chars().filter(c -> c == 'L').count();
        long right = moves.chars().filter(c -> c == 'R').count();
        
        return up == down && left == right;
    }
    
    /**
     * Helper: Visualize the robot's path
     */
    public void visualizePath(String moves) {
        System.out.println("\nRobot Path Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nMoves: \"" + moves + "\"");
        System.out.println("Length: " + moves.length());
        
        int x = 0;
        int y = 0;
        
        System.out.println("\nStep-by-step simulation:");
        System.out.println("Step | Move | Position (x, y)");
        System.out.println("-----|------|----------------");
        System.out.printf("%4d | %4s | (%2d, %2d)%n", 0, "start", x, y);
        
        for (int i = 0; i < moves.length(); i++) {
            char c = moves.charAt(i);
            switch (c) {
                case 'U': y++; break;
                case 'D': y--; break;
                case 'L': x--; break;
                case 'R': x++; break;
            }
            System.out.printf("%4d | %4c | (%2d, %2d)%n", i + 1, c, x, y);
        }
        
        System.out.println("\nFinal position: (" + x + ", " + y + ")");
        System.out.println("Returns to origin: " + (x == 0 && y == 0));
        
        // ASCII visualization for short strings
        if (moves.length() <= 20) {
            drawPathGrid(moves);
        }
    }
    
    /**
     * Helper: Draw the robot's path on a grid
     */
    private void drawPathGrid(String moves) {
        // Find bounds
        int x = 0, y = 0;
        int minX = 0, maxX = 0, minY = 0, maxY = 0;
        
        for (char c : moves.toCharArray()) {
            switch (c) {
                case 'U': y++; break;
                case 'D': y--; break;
                case 'L': x--; break;
                case 'R': x++; break;
            }
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }
        
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        char[][] grid = new char[height][width];
        for (char[] row : grid) {
            Arrays.fill(row, '.');
        }
        
        // Mark origin
        int originX = -minX;
        int originY = maxY;
        grid[originY][originX] = 'O';
        
        // Mark path
        x = 0;
        y = 0;
        int step = 1;
        for (char c : moves.toCharArray()) {
            int oldX = x;
            int oldY = y;
            switch (c) {
                case 'U': y++; break;
                case 'D': y--; break;
                case 'L': x--; break;
                case 'R': x++; break;
            }
            
            int gridX = x - minX;
            int gridY = maxY - y;
            
            if (grid[gridY][gridX] == '.' || grid[gridY][gridX] == 'O') {
                char marker = step == moves.length() ? 'E' : (char) ('0' + (step % 10));
                grid[gridY][gridX] = marker;
            }
            step++;
        }
        
        System.out.println("\nPath Grid:");
        System.out.println("  O = start, E = end, . = empty, # = path");
        System.out.println("  Numbers show step order (last digit only)");
        System.out.println();
        
        for (int i = 0; i < height; i++) {
            System.out.print("  ");
            for (int j = 0; j < width; j++) {
                char cell = grid[i][j];
                if (cell == 'O' || cell == 'E' || (cell >= '0' && cell <= '9')) {
                    System.out.print(cell);
                } else {
                    System.out.print('.');
                }
                System.out.print(' ');
            }
            System.out.println();
        }
        
        // Add axis labels
        System.out.print("    ");
        for (int j = 0; j < width; j++) {
            int xCoord = minX + j;
            if (xCoord == 0) System.out.print("0 ");
            else System.out.print("  ");
        }
        System.out.println();
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[] {
            "UD",           // true
            "LL",           // false
            "RRDD",         // false
            "LDRRLRUULR",   // false
            "",             // empty string (should be true)
            "U",            // false
            "RL",           // true
            "UDLR",         // true
            "URDL",         // true
            "UUDDLLRR",     // true
            "UUDDLLRRU",    // false
            "RRRLLL",       // true
            "UUUDDD",       // true
            "RRRLLLUD",     // true
            "RRRLLLUUDD"    // true
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[] testCases = generateTestCases();
        boolean[] expected = {true, false, false, false, true, false, true, true, true, true, false, true, true, true, true};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String moves = testCases[i];
            System.out.printf("\nTest %d: \"%s\"%n", i + 1, moves);
            
            boolean result1 = judgeCircle(moves);
            boolean result2 = judgeCircleTwoVar(moves);
            boolean result3 = judgeCircleHashMap(moves);
            boolean result4 = judgeCircleArray(moves);
            boolean result5 = judgeCircleStream(moves);
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Returns to origin: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first few test cases
            if (i < 5) {
                visualizePath(moves);
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
        for (int i = 0; i < 10000; i++) {
            sb.append("UDRL");
        }
        String largeString = sb.toString(); // 40,000 characters
        
        System.out.println("Test Setup: " + largeString.length() + " moves");
        
        long[] times = new long[5];
        boolean[] results = new boolean[5];
        
        // Method 1: Counting
        long start = System.currentTimeMillis();
        results[0] = judgeCircle(largeString);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Two Variables
        start = System.currentTimeMillis();
        results[1] = judgeCircleTwoVar(largeString);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: HashMap
        start = System.currentTimeMillis();
        results[2] = judgeCircleHashMap(largeString);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Array Frequency
        start = System.currentTimeMillis();
        results[3] = judgeCircleArray(largeString);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Stream
        start = System.currentTimeMillis();
        results[4] = judgeCircleStream(largeString);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Counting               | %9d | %6b%n", times[0], results[0]);
        System.out.printf("2. Two Variables          | %9d | %6b%n", times[1], results[1]);
        System.out.printf("3. HashMap                | %9d | %6b%n", times[2], results[2]);
        System.out.printf("4. Array Frequency        | %9d | %6b%n", times[3], results[3]);
        System.out.printf("5. Stream                 | %9d | %6b%n", times[4], results[4]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3] && results[3] == results[4];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. All O(n) methods have similar performance");
        System.out.println("2. Two-variable approach is slightly fastest");
        System.out.println("3. HashMap has overhead for boxed objects");
        System.out.println("4. Stream API has functional overhead");
        System.out.println("5. For large inputs, simple counting is best");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Empty string:");
        System.out.println("   Input: \"\"");
        System.out.println("   Output: " + judgeCircle(""));
        
        System.out.println("\n2. Single move:");
        System.out.println("   Input: \"U\"");
        System.out.println("   Output: " + judgeCircle("U"));
        
        System.out.println("\n3. Single move each direction:");
        System.out.println("   Input: \"URDL\"");
        System.out.println("   Output: " + judgeCircle("URDL"));
        
        System.out.println("\n4. Large string (balanced):");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append("U");
            sb.append("D");
        }
        System.out.println("   Input: 20,000 moves (balanced)");
        long start = System.currentTimeMillis();
        boolean result = judgeCircle(sb.toString());
        long time = System.currentTimeMillis() - start;
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
        
        System.out.println("\n5. Large string (unbalanced):");
        sb = new StringBuilder();
        for (int i = 0; i < 20000; i++) {
            sb.append("U");
        }
        System.out.println("   Input: 20,000 U moves");
        start = System.currentTimeMillis();
        result = judgeCircle(sb.toString());
        time = System.currentTimeMillis() - start;
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nProblem: Determine if robot returns to origin after a sequence of moves.");
        
        System.out.println("\nKey Insight:");
        System.out.println("For the robot to return to origin, the number of 'U' moves must equal");
        System.out.println("the number of 'D' moves, and the number of 'L' moves must equal");
        System.out.println("the number of 'R' moves.");
        
        System.out.println("\nWhy?");
        System.out.println("  - Each 'U' increases y by 1, each 'D' decreases y by 1");
        System.out.println("  - Net vertical displacement = U - D");
        System.out.println("  - Net horizontal displacement = R - L");
        System.out.println("  - To be at origin: U - D = 0 and R - L = 0");
        System.out.println("  - Therefore: U = D and R = L");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize counters for U, D, L, R to 0");
        System.out.println("2. Iterate through each move character:");
        System.out.println("   - If 'U': increment up counter");
        System.out.println("   - If 'D': increment down counter");
        System.out.println("   - If 'L': increment left counter");
        System.out.println("   - If 'R': increment right counter");
        System.out.println("3. Return true if up == down AND left == right");
        
        System.out.println("\nExample: moves = \"UD\"");
        System.out.println("  U: up=1");
        System.out.println("  D: down=1");
        System.out.println("  up == down? true");
        System.out.println("  left == right? true (both 0)");
        System.out.println("  → Returns true");
        
        System.out.println("\nComplexity:");
        System.out.println("- Time: O(n) - single pass through string");
        System.out.println("- Space: O(1) - only 4 counters");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What coordinate system? (2D plane)");
        System.out.println("   - What moves are valid? (U, D, L, R)");
        System.out.println("   - Does robot orientation matter? (No)");
        
        System.out.println("\n2. Start with simple simulation:");
        System.out.println("   - Track x and y coordinates");
        System.out.println("   - Update on each move");
        System.out.println("   - Check if x == 0 && y == 0");
        
        System.out.println("\n3. Optimize with counting:");
        System.out.println("   - Realize that order doesn't matter");
        System.out.println("   - Count frequencies of each move");
        System.out.println("   - Compare counts");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - Time: O(n) - must examine each move");
        System.out.println("   - Space: O(1) - constant space");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - Empty string (trivially returns to origin)");
        System.out.println("   - Single move (cannot return to origin)");
        System.out.println("   - Very long strings (up to 20,000)");
        System.out.println("   - Alternating moves (U D U D)");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Forgetting to handle empty string");
        System.out.println("   - Using separate variables but comparing wrong pairs");
        System.out.println("   - Overcomplicating with path tracking when not needed");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("657. Robot Return to Origin");
        System.out.println("===========================");
        
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
    public boolean judgeCircle(String moves) {
        int up = 0, down = 0, left = 0, right = 0;
        
        for (char c : moves.toCharArray()) {
            switch (c) {
                case 'U': up++; break;
                case 'D': down++; break;
                case 'L': left++; break;
                case 'R': right++; break;
            }
        }
        
        return up == down && left == right;
    }
}
            """);
        
        System.out.println("\nAlternative (Two Variables):");
        System.out.println("""
class Solution {
    public boolean judgeCircle(String moves) {
        int x = 0, y = 0;
        
        for (char c : moves.toCharArray()) {
            switch (c) {
                case 'U': y++; break;
                case 'D': y--; break;
                case 'L': x--; break;
                case 'R': x++; break;
            }
        }
        
        return x == 0 && y == 0;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. The problem reduces to counting U/D and L/R pairs");
        System.out.println("2. Order of moves doesn't matter - only counts matter");
        System.out.println("3. Time complexity: O(n) - must examine each character");
        System.out.println("4. Space complexity: O(1) - only counters needed");
        System.out.println("5. This is a classic counting/simulation problem");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) where n = moves.length()");
        System.out.println("- Space: O(1) for counters");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle additional move types?");
        System.out.println("2. What if the robot could face different directions?");
        System.out.println("3. How would you track the actual path?");
        System.out.println("4. What if you needed to return the final position?");
        System.out.println("5. How would you find if the path crosses itself?");
    }
}
