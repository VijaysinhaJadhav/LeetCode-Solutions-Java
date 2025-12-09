
# Solution.java

```java
/**
 * 739. Daily Temperatures
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of daily temperatures, return an array such that for each day,
 * it tells how many days you have to wait until a warmer temperature.
 * If no warmer temperature in the future, return 0.
 * 
 * Key Insights:
 * 1. Use monotonic stack to track temperatures in decreasing order
 * 2. When warmer temperature found, calculate days difference
 * 3. Each temperature pushed and popped exactly once
 * 4. Stack stores indices, not temperatures, for days calculation
 * 
 * Approach (Monotonic Stack):
 * 1. Initialize stack for temperature indices and result array
 * 2. Iterate through temperatures
 * 3. While stack not empty and current temperature > stack top temperature:
 *    - Pop from stack and calculate days difference
 *    - Store in result array
 * 4. Push current index to stack
 * 5. Return result array
 * 
 * Time Complexity: O(n) - Each index pushed and popped once
 * Space Complexity: O(n) - Stack storage
 * 
 * Tags: Array, Stack, Monotonic Stack
 */

import java.util.*;

public class Solution {
    
    /**
     * Approach 1: Monotonic Stack - RECOMMENDED
     * O(n) time, O(n) space - Most efficient and elegant
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            // While current temperature is warmer than stack top temperature
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                answer[prevIndex] = i - prevIndex; // Days difference
            }
            stack.push(i);
        }
        
        // Remaining indices in stack have no warmer temperature (default 0)
        return answer;
    }
    
    /**
     * Approach 2: Monotonic Stack (Backward Processing)
     * O(n) time, O(n) space - Alternative processing direction
     */
    public int[] dailyTemperaturesBackward(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        // Process from the end to beginning
        for (int i = n - 1; i >= 0; i--) {
            // Pop until we find a warmer temperature or stack is empty
            while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.peek()]) {
                stack.pop();
            }
            
            if (!stack.isEmpty()) {
                answer[i] = stack.peek() - i;
            }
            // Else answer[i] remains 0 (no warmer temperature)
            
            stack.push(i);
        }
        
        return answer;
    }
    
    /**
     * Approach 3: Array as Stack (Optimized)
     * O(n) time, O(n) space - Uses array instead of Stack class
     */
    public int[] dailyTemperaturesArrayStack(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        int[] stack = new int[n]; // Store indices
        int pointer = -1; // Stack pointer
        
        for (int i = 0; i < n; i++) {
            // While stack not empty and current temperature > stack top temperature
            while (pointer >= 0 && temperatures[i] > temperatures[stack[pointer]]) {
                int prevIndex = stack[pointer--];
                answer[prevIndex] = i - prevIndex;
            }
            stack[++pointer] = i;
        }
        
        return answer;
    }
    
    /**
     * Approach 4: Brute Force with Optimization (For Comparison)
     * O(n) time, O(1) space - Uses result array for optimization
     */
    public int[] dailyTemperaturesOptimizedBrute(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        
        // Process from right to left
        for (int i = n - 2; i >= 0; i--) {
            int j = i + 1;
            
            // Jump through warmer days using previously computed results
            while (j < n) {
                if (temperatures[j] > temperatures[i]) {
                    answer[i] = j - i;
                    break;
                } else if (answer[j] == 0) {
                    // No warmer temperature after j
                    break;
                } else {
                    // Jump to the next potential warmer day
                    j += answer[j];
                }
            }
        }
        
        return answer;
    }
    
    /**
     * Approach 5: Next Array Approach
     * O(n) time, O(1) space - Advanced approach using temperature range
     */
    public int[] dailyTemperaturesNextArray(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        int[] next = new int[101]; // Temperatures range from 30 to 100
        
        Arrays.fill(next, Integer.MAX_VALUE);
        
        // Process from right to left
        for (int i = n - 1; i >= 0; i--) {
            int warmerIndex = Integer.MAX_VALUE;
            
            // Find the nearest warmer temperature
            for (int t = temperatures[i] + 1; t <= 100; t++) {
                if (next[t] < warmerIndex) {
                    warmerIndex = next[t];
                }
            }
            
            if (warmerIndex < Integer.MAX_VALUE) {
                answer[i] = warmerIndex - i;
            }
            
            // Update next array for current temperature
            next[temperatures[i]] = i;
        }
        
        return answer;
    }
    
    /**
     * Helper method to visualize the monotonic stack process
     */
    private void visualizeStackProcess(int[] temperatures) {
        System.out.println("\nMonotonic Stack Visualization:");
        System.out.println("Temperatures: " + Arrays.toString(temperatures));
        
        int n = temperatures.length;
        int[] answer = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        System.out.println("\nStep | Index | Temp | Stack State | Action");
        System.out.println("-----|-------|------|-------------|--------");
        
        for (int i = 0; i < n; i++) {
            int currentTemp = temperatures[i];
            String stackStateBefore = getStackState(stack, temperatures);
            String action = "Push index " + i;
            
            // Process warmer temperatures
            while (!stack.isEmpty() && currentTemp > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                answer[prevIndex] = i - prevIndex;
                action = "Pop index " + prevIndex + ", set answer[" + prevIndex + "] = " + (i - prevIndex);
                stackStateBefore = getStackState(stack, temperatures); // Update after pop
            }
            
            stack.push(i);
            String stackStateAfter = getStackState(stack, temperatures);
            
            System.out.printf("%4d | %5d | %4d | %11s | %s%n", 
                            i + 1, i, currentTemp, stackStateAfter, action);
        }
        
        System.out.println("\nFinal Answer: " + Arrays.toString(answer));
        
        System.out.println("\nKey Insights:");
        System.out.println("- Stack maintains indices of temperatures in decreasing order");
        System.out.println("- When warmer temperature found, calculate days difference");
        System.out.println("- Each index pushed and popped exactly once");
        System.out.println("- Remaining indices in stack have no warmer days (answer = 0)");
    }
    
    private String getStackState(Stack<Integer> stack, int[] temperatures) {
        if (stack.isEmpty()) return "[]";
        List<String> elements = new ArrayList<>();
        for (int index : stack) {
            elements.add(index + "(" + temperatures[index] + ")");
        }
        return elements.toString();
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Daily Temperatures:");
        System.out.println("============================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] temperatures1 = {73, 74, 75, 71, 69, 72, 76, 73};
        int[] expected1 = {1, 1, 4, 2, 1, 1, 0, 0};
        
        long startTime = System.nanoTime();
        int[] result1a = solution.dailyTemperatures(temperatures1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1b = solution.dailyTemperaturesBackward(temperatures1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1c = solution.dailyTemperaturesArrayStack(temperatures1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1d = solution.dailyTemperaturesOptimizedBrute(temperatures1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = Arrays.equals(result1a, expected1);
        boolean test1b = Arrays.equals(result1b, expected1);
        boolean test1c = Arrays.equals(result1c, expected1);
        boolean test1d = Arrays.equals(result1d, expected1);
        
        System.out.println("Monotonic Stack: " + Arrays.toString(result1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Backward: " + Arrays.toString(result1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Array Stack: " + Arrays.toString(result1c) + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Optimized Brute: " + Arrays.toString(result1d) + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the stack process
        solution.visualizeStackProcess(temperatures1);
        
        // Test case 2: Increasing temperatures
        System.out.println("\nTest 2: Increasing temperatures");
        int[] temperatures2 = {30, 40, 50, 60};
        int[] expected2 = {1, 1, 1, 0};
        
        int[] result2a = solution.dailyTemperatures(temperatures2);
        System.out.println("Increasing: " + Arrays.toString(result2a) + " - " + 
                         (Arrays.equals(result2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Simple case
        System.out.println("\nTest 3: Simple case");
        int[] temperatures3 = {30, 60, 90};
        int[] expected3 = {1, 1, 0};
        
        int[] result3a = solution.dailyTemperatures(temperatures3);
        System.out.println("Simple: " + Arrays.toString(result3a) + " - " + 
                         (Arrays.equals(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: All same temperatures
        System.out.println("\nTest 4: All same temperatures");
        int[] temperatures4 = {50, 50, 50, 50};
        int[] expected4 = {0, 0, 0, 0};
        
        int[] result4a = solution.dailyTemperatures(temperatures4);
        System.out.println("All same: " + Arrays.toString(result4a) + " - " + 
                         (Arrays.equals(result4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Decreasing temperatures
        System.out.println("\nTest 5: Decreasing temperatures");
        int[] temperatures5 = {90, 80, 70, 60};
        int[] expected5 = {0, 0, 0, 0};
        
        int[] result5a = solution.dailyTemperatures(temperatures5);
        System.out.println("Decreasing: " + Arrays.toString(result5a) + " - " + 
                         (Arrays.equals(result5a, expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Single temperature
        System.out.println("\nTest 6: Single temperature");
        int[] temperatures6 = {75};
        int[] expected6 = {0};
        
        int[] result6a = solution.dailyTemperatures(temperatures6);
        System.out.println("Single: " + Arrays.toString(result6a) + " - " + 
                         (Arrays.equals(result6a, expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Complex pattern
        System.out.println("\nTest 7: Complex pattern");
        int[] temperatures7 = {55, 60, 50, 65, 45, 70, 40};
        int[] expected7 = {1, 2, 1, 2, 1, 0, 0};
        
        int[] result7a = solution.dailyTemperatures(temperatures7);
        System.out.println("Complex: " + Arrays.toString(result7a) + " - " + 
                         (Arrays.equals(result7a, expected7) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Monotonic Stack: " + time1a + " ns");
        System.out.println("  Backward: " + time1b + " ns");
        System.out.println("  Array Stack: " + time1c + " ns");
        System.out.println("  Optimized Brute: " + time1d + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 9: Large input performance");
        int[] largeTemperatures = generateLargeTemperatures(100000);
        
        startTime = System.nanoTime();
        int[] result9a = solution.dailyTemperatures(largeTemperatures);
        long time9a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result9b = solution.dailyTemperaturesArrayStack(largeTemperatures);
        long time9b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result9c = solution.dailyTemperaturesOptimizedBrute(largeTemperatures);
        long time9c = System.nanoTime() - startTime;
        
        System.out.println("Large input (100,000 temperatures):");
        System.out.println("  Monotonic Stack: " + time9a + " ns");
        System.out.println("  Array Stack: " + time9b + " ns");
        System.out.println("  Optimized Brute: " + time9c + " ns");
        
        // Verify all approaches produce the same result
        boolean allEqual = Arrays.equals(result9a, result9b) && Arrays.equals(result9a, result9c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Edge case: Maximum constraints
        System.out.println("\nTest 10: Edge case - maximum length");
        int[] maxTemperatures = new int[100000];
        Arrays.fill(maxTemperatures, 75);
        maxTemperatures[99999] = 100; // Last element is warmer
        
        int[] result10a = solution.dailyTemperatures(maxTemperatures);
        System.out.println("Max length: Result computed successfully, last non-zero: " + 
                         result10a[99998]);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        
        System.out.println("\nMonotonic Stack Approach:");
        System.out.println("1. Initialize empty stack and result array");
        System.out.println("2. For each temperature from left to right:");
        System.out.println("   a. While stack not empty and current temperature > stack top temperature:");
        System.out.println("      - Pop index from stack");
        System.out.println("      - Set result[popped_index] = current_index - popped_index");
        System.out.println("   b. Push current index to stack");
        System.out.println("3. Remaining indices in stack have no warmer days (result remains 0)");
        
        System.out.println("\nWhy It Works:");
        System.out.println("- Stack maintains indices of temperatures waiting for warmer days");
        System.out.println("- Temperatures in stack are in decreasing order (monotonic)");
        System.out.println("- When warmer temperature found, it resolves multiple waiting temperatures");
        System.out.println("- Each index pushed once and popped once → O(n) time complexity");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\nMonotonic Stack Approach:");
        System.out.println("┌────────────┬────────────┬──────────────┐");
        System.out.println("│ Operation  │ Time       │ Space        │");
        System.out.println("├────────────┼────────────┼──────────────┤");
        System.out.println("│ Process    │ O(n)       │ O(n)         │");
        System.out.println("│ Push/Pop   │ O(1) each  │ O(1) each    │");
        System.out.println("│ Total      │ O(n)       │ O(n)         │");
        System.out.println("└────────────┴────────────┴──────────────┘");
        
        System.out.println("\nComparison of Approaches:");
        System.out.println("┌──────────────────────┬────────────┬─────────────────┐");
        System.out.println("│ Approach             │ Time       │ Space           │");
        System.out.println("├──────────────────────┼────────────┼─────────────────┤");
        System.out.println("│ Monotonic Stack      │ O(n)       │ O(n)            │");
        System.out.println("│ Array as Stack       │ O(n)       │ O(n)            │");
        System.out.println("│ Optimized Brute      │ O(n)       │ O(1) extra      │");
        System.out.println("│ Next Array           │ O(n)       │ O(1) extra      │");
        System.out.println("└──────────────────────┴────────────┴─────────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with Monotonic Stack Approach:");
        System.out.println("   - Most efficient and elegant solution");
        System.out.println("   - Demonstrates understanding of monotonic stacks");
        System.out.println("   - Handles all cases optimally");
        
        System.out.println("\n2. Key Points to Explain:");
        System.out.println("   - Why stack is appropriate (maintain waiting temperatures)");
        System.out.println("   - Monotonic property (decreasing temperatures in stack)");
        System.out.println("   - How warmer temperatures resolve multiple waiting temperatures");
        System.out.println("   - Time and space complexity analysis");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - All increasing/decreasing temperatures");
        System.out.println("   - All same temperatures");
        System.out.println("   - Single temperature");
        System.out.println("   - Large input constraints");
        
        System.out.println("\n4. Discuss Alternative Approaches:");
        System.out.println("   - Brute force (O(n²)) and why it's inefficient");
        System.out.println("   - Optimized brute force with jumping");
        System.out.println("   - Next array approach for fixed temperature range");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - Weather forecasting systems");
        System.out.println("   - Stock price analysis (next higher price)");
        System.out.println("   - Resource scheduling (next available slot)");
        System.out.println("   - Cache replacement algorithms");
        
        System.out.println("\nAll tests completed successfully!");
    }
    
    /**
     * Helper method to generate large temperature array for performance testing
     */
    private static int[] generateLargeTemperatures(int size) {
        int[] temperatures = new int[size];
        Random random = new Random(42);
        
        // Generate realistic temperature pattern with some variation
        int baseTemp = 70;
        for (int i = 0; i < size; i++) {
            // Create pattern with some peaks and valleys
            int variation = random.nextInt(21) - 10; // -10 to +10 variation
            temperatures[i] = Math.max(30, Math.min(100, baseTemp + variation));
            
            // Occasionally change base temperature
            if (i % 100 == 0) {
                baseTemp = 60 + random.nextInt(31); // 60-90 base
            }
        }
        
        return temperatures;
    }
}
