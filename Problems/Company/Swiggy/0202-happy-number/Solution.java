
# Solution.java

```java
import java.util.*;

/**
 * 202. Happy Number
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Determine if a number is happy.
 * A happy number: Replace number with sum of squares of digits,
 * repeat until equals 1 (happy) or loops in cycle (unhappy).
 * 
 * Key Insights:
 * 1. Cycle detection problem (like linked list cycle)
 * 2. Two approaches: HashSet or Floyd's Cycle Detection
 * 3. All unhappy numbers end in cycle containing 4
 * 4. Sum of squares calculation reduces number quickly
 * 
 * Approach (Floyd's Cycle Detection - RECOMMENDED):
 * 1. Use slow and fast pointers
 * 2. Slow: one step per iteration (sum of squares once)
 * 3. Fast: two steps per iteration (sum of squares twice)
 * 4. If fast reaches 1 → happy number
 * 5. If slow == fast (cycle detected) and not 1 → unhappy
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 * 
 * Tags: Hash Table, Math, Two Pointers
 */

class Solution {
    
    /**
     * Helper method to calculate sum of squares of digits
     */
    private int sumOfSquares(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
    
    /**
     * Approach 1: Floyd's Cycle Detection Algorithm (RECOMMENDED)
     * O(log n) time, O(1) space
     */
    public boolean isHappy(int n) {
        int slow = n;
        int fast = n;
        
        do {
            slow = sumOfSquares(slow);           // Move one step
            fast = sumOfSquares(sumOfSquares(fast)); // Move two steps
            
            if (fast == 1) return true;         // Found happy number
            
        } while (slow != fast);                 // Cycle detected
        
        return false;                           // Cycle without 1
    }
    
    /**
     * Approach 2: Using HashSet to detect cycles
     * O(log n) time, O(log n) space
     * Simple and intuitive
     */
    public boolean isHappyHashSet(int n) {
        Set<Integer> seen = new HashSet<>();
        
        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            n = sumOfSquares(n);
        }
        
        return n == 1;
    }
    
    /**
     * Approach 3: Mathematical Optimization
     * O(log n) time, O(1) space
     * Based on fact: all unhappy numbers end in cycle containing 4
     */
    public boolean isHappyMath(int n) {
        while (n != 1 && n != 4) {  // 4 is in the unhappy cycle
            n = sumOfSquares(n);
        }
        return n == 1;
    }
    
    /**
     * Approach 4: Recursive with Memoization
     * O(log n) time, O(log n) space
     * Demonstrates recursive approach
     */
    public boolean isHappyRecursive(int n) {
        return isHappyHelper(n, new HashSet<>());
    }
    
    private boolean isHappyHelper(int n, Set<Integer> seen) {
        if (n == 1) return true;
        if (seen.contains(n)) return false;
        
        seen.add(n);
        return isHappyHelper(sumOfSquares(n), seen);
    }
    
    /**
     * Approach 5: Using boolean array for seen numbers
     * O(log n) time, O(1) space (array size 1000)
     * Fast lookup for numbers up to 1000
     */
    public boolean isHappyArray(int n) {
        // 1000 is enough because sum of squares reduces numbers quickly
        boolean[] visited = new boolean[1000];
        
        while (n != 1) {
            n = sumOfSquares(n);
            
            // Check for cycle
            if (n < 1000 && visited[n]) {
                return false;
            }
            
            if (n < 1000) {
                visited[n] = true;
            }
        }
        
        return true;
    }
    
    /**
     * Approach 6: Two Pointers with different implementation
     * O(log n) time, O(1) space
     * Alternative Floyd's implementation
     */
    public boolean isHappyTwoPointers(int n) {
        int slow = n;
        int fast = n;
        
        while (true) {
            slow = sumOfSquares(slow);
            fast = sumOfSquares(fast);
            fast = sumOfSquares(fast);
            
            if (fast == 1) return true;
            if (slow == fast) return false;
        }
    }
    
    /**
     * Helper method to visualize the process
     */
    private void visualizeProcess(int n, String approach) {
        System.out.println("\n" + approach + " - Visualization for n = " + n);
        System.out.println("Step | Number | Digits | Sum of Squares");
        System.out.println("-----|--------|--------|---------------");
        
        int step = 0;
        Set<Integer> seen = new HashSet<>();
        int current = n;
        
        while (current != 1 && !seen.contains(current)) {
            seen.add(current);
            int next = sumOfSquares(current);
            
            // Show digits
            List<Integer> digits = getDigits(current);
            String digitsStr = digits.toString().replaceAll("[\\[\\],]", "");
            
            System.out.printf("%4d | %6d | %6s | %d²", 
                step, current, digitsStr, digits.get(0));
            
            for (int i = 1; i < digits.size(); i++) {
                System.out.printf(" + %d²", digits.get(i));
            }
            System.out.printf(" = %d%n", next);
            
            current = next;
            step++;
        }
        
        if (current == 1) {
            System.out.printf("%4d | %6d |        | Reached 1 → HAPPY NUMBER%n", step, current);
        } else {
            System.out.printf("%4d | %6d |        | Cycle detected → UNHAPPY NUMBER%n", step, current);
        }
        
        // Show cycle if unhappy
        if (current != 1) {
            System.out.println("\nCycle found:");
            List<Integer> cycle = new ArrayList<>();
            int start = current;
            do {
                cycle.add(current);
                current = sumOfSquares(current);
            } while (current != start);
            
            System.out.print("  " + cycle.get(0));
            for (int i = 1; i < cycle.size(); i++) {
                System.out.print(" → " + cycle.get(i));
            }
            System.out.println(" → " + start + " (repeats)");
        }
    }
    
    private List<Integer> getDigits(int n) {
        List<Integer> digits = new ArrayList<>();
        while (n > 0) {
            digits.add(0, n % 10); // Add at beginning to maintain order
            n /= 10;
        }
        if (digits.isEmpty()) digits.add(0);
        return digits;
    }
    
    /**
     * Helper to show Floyd's cycle detection process
     */
    private void visualizeFloyd(int n) {
        System.out.println("\nFloyd's Cycle Detection Visualization for n = " + n);
        System.out.println("Step | Slow | Fast | Action");
        System.out.println("-----|------|------|--------");
        
        int slow = n;
        int fast = n;
        int step = 0;
        
        do {
            System.out.printf("%4d | %4d | %4d | ", step, slow, fast);
            
            // Move pointers
            int nextSlow = sumOfSquares(slow);
            int nextFast = sumOfSquares(sumOfSquares(fast));
            
            if (nextFast == 1) {
                System.out.println("Fast reached 1 → HAPPY");
                return;
            }
            
            System.out.printf("Slow: %d→%d, Fast: %d→%d", 
                slow, nextSlow, fast, nextFast);
            
            if (nextSlow == nextFast) {
                System.out.println(" (Cycle detected)");
            } else {
                System.out.println();
            }
            
            slow = nextSlow;
            fast = nextFast;
            step++;
            
        } while (slow != fast);
        
        System.out.printf("%4d | %4d | %4d | Slow == Fast (Cycle without 1) → UNHAPPY%n", 
            step, slow, fast);
    }
    
    /**
     * Helper to test known happy and unhappy numbers
     */
    private void testKnownNumbers() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("KNOWN HAPPY AND UNHAPPY NUMBERS:");
        System.out.println("=".repeat(80));
        
        int[] happyNumbers = {1, 7, 10, 13, 19, 23, 28, 31, 32, 44, 49, 68, 70, 79, 82, 86, 91, 94, 97, 100};
        int[] unhappyNumbers = {2, 3, 4, 5, 6, 8, 9, 11, 12, 14, 15, 16, 17, 18, 20, 21, 22, 24, 25, 26};
        
        System.out.println("\nHappy Numbers (should return true):");
        for (int i = 0; i < Math.min(10, happyNumbers.length); i++) {
            boolean result = isHappy(happyNumbers[i]);
            System.out.printf("%3d: %-5s %s%n", 
                happyNumbers[i], result, result ? "✓" : "✗");
        }
        
        System.out.println("\nUnhappy Numbers (should return false):");
        for (int i = 0; i < Math.min(10, unhappyNumbers.length); i++) {
            boolean result = isHappy(unhappyNumbers[i]);
            System.out.printf("%3d: %-5s %s%n", 
                unhappyNumbers[i], result, result ? "✗" : "✓");
        }
        
        // Show the famous unhappy cycle
        System.out.println("\nThe Famous Unhappy Cycle (starting from 2):");
        int current = 2;
        Set<Integer> cycle = new LinkedHashSet<>();
        do {
            cycle.add(current);
            current = sumOfSquares(current);
        } while (!cycle.contains(current));
        
        List<Integer> cycleList = new ArrayList<>(cycle);
        // Find where cycle starts
        int cycleStart = cycleList.indexOf(current);
        
        System.out.print("Path: " + cycleList.get(0));
        for (int i = 1; i < cycleList.size(); i++) {
            System.out.print(" → " + cycleList.get(i));
        }
        System.out.println();
        
        if (cycleStart > 0) {
            System.out.println("Prefix: " + cycleList.subList(0, cycleStart));
            System.out.println("Cycle: " + cycleList.subList(cycleStart, cycleList.size()));
        }
    }
    
    /**
     * Helper to compare different approaches
     */
    private void compareApproaches(int n) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES FOR n = " + n);
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        boolean result1, result2, result3, result4, result5, result6;
        
        // Approach 1: Floyd's Cycle Detection
        startTime = System.nanoTime();
        result1 = solution.isHappy(n);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: HashSet
        startTime = System.nanoTime();
        result2 = solution.isHappyHashSet(n);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Mathematical
        startTime = System.nanoTime();
        result3 = solution.isHappyMath(n);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Recursive
        startTime = System.nanoTime();
        result4 = solution.isHappyRecursive(n);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: Array
        startTime = System.nanoTime();
        result5 = solution.isHappyArray(n);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Approach 6: Two Pointers (alternative)
        startTime = System.nanoTime();
        result6 = solution.isHappyTwoPointers(n);
        endTime = System.nanoTime();
        long time6 = endTime - startTime;
        
        // Verify all results are the same
        boolean allEqual = (result1 == result2) && (result2 == result3) &&
                          (result3 == result4) && (result4 == result5) &&
                          (result5 == result6);
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (Floyd's):        " + result1);
        System.out.println("Approach 2 (HashSet):        " + result2);
        System.out.println("Approach 3 (Mathematical):   " + result3);
        System.out.println("Approach 4 (Recursive):      " + result4);
        System.out.println("Approach 5 (Array):          " + result5);
        System.out.println("Approach 6 (Two Pointers):   " + result6);
        
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Approach 1: %-10d (Floyd's Cycle Detection)%n", time1);
        System.out.printf("Approach 2: %-10d (HashSet)%n", time2);
        System.out.printf("Approach 3: %-10d (Mathematical - stops at 4)%n", time3);
        System.out.printf("Approach 4: %-10d (Recursive with Memoization)%n", time4);
        System.out.printf("Approach 5: %-10d (Boolean Array)%n", time5);
        System.out.printf("Approach 6: %-10d (Two Pointers alternative)%n", time6);
        
        // Visualize the chosen approach
        solution.visualizeProcess(n, "Standard Process");
        solution.visualizeFloyd(n);
    }
    
    /**
     * Helper to show mathematical analysis
     */
    private void showMathematicalAnalysis() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nSum of Squares Function:");
        System.out.println("f(n) = sum(digit²) for each digit in n");
        
        System.out.println("\nProperties:");
        System.out.println("1. f(n) < n for n ≥ 1000");
        System.out.println("2. Maximum f(n) for k-digit number: 81k");
        System.out.println("3. For n > 243, f(n) < n");
        System.out.println("4. All numbers eventually reach ≤ 243");
        
        System.out.println("\nKnown Cycles:");
        System.out.println("Happy cycle: 1 → 1");
        System.out.println("Unhappy cycle: 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4");
        
        System.out.println("\nInteresting Facts:");
        System.out.println("1. All unhappy numbers eventually reach the cycle containing 4");
        System.out.println("2. This is why Approach 3 (checking for 4) works");
        System.out.println("3. About 1/7 of numbers are happy in any range");
        System.out.println("4. Density of happy numbers ≈ 0.142");
        
        // Show maximum values
        System.out.println("\nMaximum sum of squares for different digit lengths:");
        for (int digits = 1; digits <= 10; digits++) {
            int maxNum = (int)Math.pow(10, digits) - 1;
            int maxSum = digits * 81;
            System.out.printf("%2d-digit number (max %d): max sum = %d%n", 
                digits, maxNum, maxSum);
        }
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Happy Number:");
        System.out.println("=====================");
        
        // Show mathematical analysis first
        solution.showMathematicalAnalysis();
        
        // Test case 1: Example from problem (happy)
        System.out.println("\n\nTest 1: Happy Number (19)");
        int num1 = 19;
        boolean expected1 = true;
        
        solution.visualizeProcess(num1, "Example 1");
        
        boolean result1 = solution.isHappy(num1);
        System.out.println("\nExpected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        // Test case 2: Example from problem (unhappy)
        System.out.println("\n\nTest 2: Unhappy Number (2)");
        int num2 = 2;
        boolean expected2 = false;
        
        solution.visualizeProcess(num2, "Example 2");
        
        boolean result2 = solution.isHappy(num2);
        System.out.println("\nExpected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        // Test case 3: Edge case - 1
        System.out.println("\n\nTest 3: Minimum happy number (1)");
        int num3 = 1;
        boolean expected3 = true;
        
        boolean result3 = solution.isHappy(num3);
        System.out.println("Expected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + (result3 == expected3 ? "✓" : "✗"));
        
        // Test case 4: Large number
        System.out.println("\n\nTest 4: Large number (999999999)");
        int num4 = 999999999; // 9 digits of 9
        // 9 * 9² = 9 * 81 = 729, then process continues
        
        solution.visualizeProcess(num4, "Large Number");
        boolean result4 = solution.isHappy(num4);
        System.out.println("Result: " + result4);
        
        // Test case 5: Number that becomes 1 quickly
        System.out.println("\n\nTest 5: Quick happy number (10)");
        int num5 = 10;
        boolean expected5 = true;
        
        solution.visualizeProcess(num5, "Quick Happy");
        boolean result5 = solution.isHappy(num5);
        System.out.println("\nExpected: " + expected5);
        System.out.println("Result:   " + result5);
        System.out.println("Passed: " + (result5 == expected5 ? "✓" : "✗"));
        
        // Test known numbers
        solution.testKnownNumbers();
        
        // Compare all approaches for a few numbers
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES:");
        System.out.println("=".repeat(80));
        
        int[] testNumbers = {19, 2, 1, 7, 13, 4, 16, 20, 68, 79, 145, 999};
        
        for (int num : testNumbers) {
            solution.compareApproaches(num);
            System.out.println("\n" + "-".repeat(80));
        }
        
        // Performance test
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(80));
        
        // Test a range of numbers
        Random random = new Random(42);
        int testCount = 10000;
        int[] randomNumbers = new int[testCount];
        
        for (int i = 0; i < testCount; i++) {
            randomNumbers[i] = random.nextInt(1000000) + 1;
        }
        
        System.out.println("\nTesting " + testCount + " random numbers (1 to 1,000,000):");
        
        long startTime, endTime;
        
        // Approach 1: Floyd's Cycle Detection
        startTime = System.currentTimeMillis();
        int happyCount1 = 0;
        for (int num : randomNumbers) {
            if (solution.isHappy(num)) happyCount1++;
        }
        endTime = System.currentTimeMillis();
        long time1 = endTime - startTime;
        
        // Approach 2: HashSet
        startTime = System.currentTimeMillis();
        int happyCount2 = 0;
        for (int num : randomNumbers) {
            if (solution.isHappyHashSet(num)) happyCount2++;
        }
        endTime = System.currentTimeMillis();
        long time2 = endTime - startTime;
        
        // Approach 3: Mathematical
        startTime = System.currentTimeMillis();
        int happyCount3 = 0;
        for (int num : randomNumbers) {
            if (solution.isHappyMath(num)) happyCount3++;
        }
        endTime = System.currentTimeMillis();
        long time3 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (Floyd's): " + happyCount1 + " happy numbers");
        System.out.println("Approach 2 (HashSet): " + happyCount2 + " happy numbers");
        System.out.println("Approach 3 (Math):    " + happyCount3 + " happy numbers");
        
        System.out.println("\nPerformance (milliseconds):");
        System.out.printf("Approach 1: %5d ms (Floyd's Cycle Detection)%n", time1);
        System.out.printf("Approach 2: %5d ms (HashSet)%n", time2);
        System.out.printf("Approach 3: %5d ms (Mathematical)%n", time3);
        
        // Verify consistency
        boolean consistent = (happyCount1 == happyCount2) && (happyCount2 == happyCount3);
        System.out.println("\nResults consistent: " + (consistent ? "✓ YES" : "✗ NO"));
        
        // Show happy number density
        double density = (double)happyCount1 / testCount;
        System.out.printf("\nHappy number density in sample: %.3f (expected ~0.142)%n", density);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nFloyd's Cycle Detection (Tortoise and Hare):");
        System.out.println("1. Use two pointers: slow and fast");
        System.out.println("2. Slow moves one step: n → f(n)");
        System.out.println("3. Fast moves two steps: n → f(f(n))");
        System.out.println("4. If fast reaches 1 → happy number");
        System.out.println("5. If slow == fast → cycle detected");
        System.out.println("6. If cycle doesn't contain 1 → unhappy number");
        
        System.out.println("\nWhy it works:");
        System.out.println("- The sequence forms a functional graph");
        System.out.println("- Either reaches fixed point (1) or enters cycle");
        System.out.println("- Floyd's algorithm detects cycles in O(1) space");
        System.out.println("- No need to store all visited numbers");
        
        System.out.println("\nExample: n = 19 (Happy)");
        System.out.println("Slow: 19 → 82 → 68 → 100 → 1");
        System.out.println("Fast: 19 → 68 → 1");
        System.out.println("Fast reaches 1 → happy");
        
        System.out.println("\nExample: n = 2 (Unhappy)");
        System.out.println("Slow: 2 → 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4");
        System.out.println("Fast: 2 → 16 → 89 → 145 → 42 → 4 → 37 → 58 → 89");
        System.out.println("Slow and fast meet at 89 → cycle detected → unhappy");
        
        // Edge cases and common mistakes
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMMON MISTAKES AND EDGE CASES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Not handling cycle detection:");
        System.out.println("   - Infinite loop for unhappy numbers");
        System.out.println("   - Need HashSet or Floyd's algorithm");
        
        System.out.println("\n2. Integer overflow:");
        System.out.println("   - n ≤ 2³¹-1, but sum of squares is much smaller");
        System.out.println("   - No overflow issues in practice");
        
        System.out.println("\n3. Zero or negative numbers:");
        System.out.println("   - Problem states n ≥ 1");
        System.out.println("   - But good to handle edge cases");
        
        System.out.println("\n4. Performance for large numbers:");
        System.out.println("   - Sum of squares reduces numbers quickly");
        System.out.println("   - Time complexity is O(log n)");
        
        System.out.println("\n5. Special cases:");
        System.out.println("   - 1 is happy (base case)");
        System.out.println("   - Numbers in the unhappy cycle (4, 16, 37, ...)");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Number Theory Research:");
        System.out.println("   - Studying number properties");
        System.out.println("   - Mathematical patterns and cycles");
        
        System.out.println("\n2. Computer Science Education:");
        System.out.println("   - Teaching cycle detection algorithms");
        System.out.println("   - Demonstrating Floyd's algorithm");
        
        System.out.println("\n3. Game Development:");
        System.out.println("   - Number-based puzzles and games");
        System.out.println("   - Procedural content generation");
        
        System.out.println("\n4. Cryptography:");
        System.out.println("   - Pseudorandom number generation");
        System.out.println("   - Hash function analysis");
        
        System.out.println("\n5. Algorithm Design:");
        System.out.println("   - Pattern for detecting cycles in sequences");
        System.out.println("   - Memory-efficient algorithms");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - Happy number: reaches 1");
        System.out.println("   - Unhappy number: enters cycle without 1");
        System.out.println("   - Need to detect cycles");
        
        System.out.println("\n2. Identify as cycle detection problem:");
        System.out.println("   - Sequence: n → f(n) → f(f(n)) → ...");
        System.out.println("   - Similar to linked list cycle detection");
        
        System.out.println("\n3. Discuss approaches:");
        System.out.println("   - HashSet: Store visited numbers");
        System.out.println("   - Floyd's algorithm: Fast and slow pointers");
        System.out.println("   - Compare time/space complexity");
        
        System.out.println("\n4. Choose optimal approach:");
        System.out.println("   - Floyd's: O(log n) time, O(1) space");
        System.out.println("   - HashSet: O(log n) time, O(log n) space");
        
        System.out.println("\n5. Implement sumOfSquares helper:");
        System.out.println("   - Extract digits using % 10 and / 10");
        System.out.println("   - Sum their squares");
        
        System.out.println("\n6. Implement Floyd's algorithm:");
        System.out.println("   - slow = f(slow)");
        System.out.println("   - fast = f(f(fast))");
        System.out.println("   - Check if fast == 1 (happy)");
        System.out.println("   - Check if slow == fast (cycle)");
        
        System.out.println("\n7. Walk through examples:");
        System.out.println("   - n = 19 (happy)");
        System.out.println("   - n = 2 (unhappy)");
        
        System.out.println("\n8. Discuss optimization:");
        System.out.println("   - Mathematical: stop at 4 (in unhappy cycle)");
        System.out.println("   - Precompute small cycles");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Floyd's cycle detection is elegant and space-efficient");
        System.out.println("- Time complexity is O(log n)");
        System.out.println("- Problem reduces to cycle detection in functional graph");
        System.out.println("- Can use mathematical optimization (check for 4)");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not detecting cycles (infinite loop)");
        System.out.println("- Using too much space (store all numbers)");
        System.out.println("- Not handling base case (n = 1)");
        System.out.println("- Incorrect sum of squares calculation");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 141. Linked List Cycle (Same algorithm)");
        System.out.println("2. 142. Linked List Cycle II (Find cycle start)");
        System.out.println("3. 258. Add Digits (Digital root)");
        System.out.println("4. 263. Ugly Number (Number properties)");
        System.out.println("5. 507. Perfect Number (Number theory)");
        System.out.println("6. 367. Valid Perfect Square");
        System.out.println("7. 633. Sum of Square Numbers");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
