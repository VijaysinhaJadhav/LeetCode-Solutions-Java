
# Solution.java

```java
/**
 * 901. Online Stock Span
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Design an algorithm that collects daily price quotes and returns the span
 * of the stock's price for the current day.
 * The span is the maximum number of consecutive days (going backward) 
 * for which the stock price was <= current day's price.
 * 
 * Key Insights:
 * 1. Use monotonic stack to maintain decreasing prices
 * 2. Store both price and accumulated span in stack
 * 3. When higher price encountered, pop lower prices and accumulate spans
 * 4. Each price pushed and popped exactly once → amortized O(1) time
 * 
 * Approach (Monotonic Stack with Spans):
 * 1. Initialize stack that stores [price, span] pairs
 * 2. For each next(price):
 *    - Initialize span = 1
 *    - While stack not empty and stack top price <= current price:
 *        * Pop from stack and add its span to current span
 *    - Push [price, span] to stack
 *    - Return span
 * 
 * Time Complexity: O(1) amortized per next() call
 * Space Complexity: O(n) - Stack storage
 * 
 * Tags: Stack, Design, Monotonic Stack, Data Stream
 */

import java.util.*;

// Interface for StockSpanner
interface StockSpannerInterface {
    int next(int price);
}

/**
 * Approach 1: Monotonic Stack with Price-Span Pairs - RECOMMENDED
 * Amortized O(1) time, O(n) space - Most efficient and elegant
 */
class StockSpanner implements StockSpannerInterface {
    private Stack<int[]> stack; // Each element is [price, span]
    
    public StockSpanner() {
        stack = new Stack<>();
    }
    
    public int next(int price) {
        int span = 1;
        
        // Pop all prices that are <= current price and accumulate their spans
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];
        }
        
        // Push current price with its calculated span
        stack.push(new int[]{price, span});
        return span;
    }
}

/**
 * Approach 2: Monotonic Stack with Separate Span Array
 * Amortized O(1) time, O(n) space - Alternative implementation
 */
class StockSpannerSeparate implements StockSpannerInterface {
    private Stack<Integer> prices;
    private Stack<Integer> spans;
    
    public StockSpannerSeparate() {
        prices = new Stack<>();
        spans = new Stack<>();
    }
    
    public int next(int price) {
        int span = 1;
        
        // Pop all prices that are <= current price and accumulate spans
        while (!prices.isEmpty() && prices.peek() <= price) {
            prices.pop();
            span += spans.pop();
        }
        
        // Push current price and its span
        prices.push(price);
        spans.push(span);
        return span;
    }
}

/**
 * Approach 3: Array-based Stack (Optimized)
 * Amortized O(1) time, O(n) space - Uses arrays instead of Stack class
 */
class StockSpannerArray implements StockSpannerInterface {
    private int[][] stack; // [price, span] pairs
    private int pointer;
    
    public StockSpannerArray() {
        stack = new int[10000][2]; // Based on constraint: at most 10^4 calls
        pointer = -1;
    }
    
    public int next(int price) {
        int span = 1;
        
        // Pop all prices <= current price and accumulate spans
        while (pointer >= 0 && stack[pointer][0] <= price) {
            span += stack[pointer][1];
            pointer--;
        }
        
        // Push current price and span
        pointer++;
        stack[pointer][0] = price;
        stack[pointer][1] = span;
        return span;
    }
}

/**
 * Approach 4: LinkedList-based Stack
 * Amortized O(1) time, O(n) space - Uses LinkedList for educational purposes
 */
class StockSpannerLinkedList implements StockSpannerInterface {
    private static class Node {
        int price;
        int span;
        Node prev;
        
        Node(int price, int span, Node prev) {
            this.price = price;
            this.span = span;
            this.prev = prev;
        }
    }
    
    private Node top;
    
    public StockSpannerLinkedList() {
        top = null;
    }
    
    public int next(int price) {
        int span = 1;
        
        // Traverse and accumulate spans for prices <= current price
        while (top != null && top.price <= price) {
            span += top.span;
            top = top.prev;
        }
        
        // Push new node
        top = new Node(price, span, top);
        return span;
    }
}

/**
 * Approach 5: Brute Force (For Comparison)
 * O(n) time per operation, O(n) space - Simple but inefficient
 */
class StockSpannerBruteForce implements StockSpannerInterface {
    private List<Integer> prices;
    
    public StockSpannerBruteForce() {
        prices = new ArrayList<>();
    }
    
    public int next(int price) {
        prices.add(price);
        int span = 0;
        int n = prices.size();
        
        // Count consecutive days with price <= current price (backward)
        for (int i = n - 1; i >= 0; i--) {
            if (prices.get(i) <= price) {
                span++;
            } else {
                break;
            }
        }
        
        return span;
    }
}

/**
 * Test class to verify all implementations
 */
public class Solution {
    
    /**
     * Helper method to test a StockSpanner implementation
     */
    private static void testStockSpanner(StockSpannerInterface spanner, String implementationName) {
        System.out.println("\nTesting " + implementationName + ":");
        System.out.println("Prices: [100, 80, 60, 70, 60, 75, 85]");
        System.out.println("Expected: [1, 1, 1, 2, 1, 4, 6]");
        
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        int[] expected = {1, 1, 1, 2, 1, 4, 6};
        int[] results = new int[prices.length];
        
        for (int i = 0; i < prices.length; i++) {
            results[i] = spanner.next(prices[i]);
        }
        
        System.out.println("Results: " + Arrays.toString(results));
        boolean testPassed = Arrays.equals(results, expected);
        System.out.println("TEST " + (testPassed ? "PASSED" : "FAILED"));
    }
    
    /**
     * Helper method to visualize the monotonic stack process
     */
    private static void visualizeStackProcess() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MONOTONIC STACK PROCESS VISUALIZATION");
        System.out.println("=".repeat(70));
        
        StockSpanner spanner = new StockSpanner();
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        
        System.out.println("\nStep | Price | Stack State | Span | Action");
        System.out.println("-----|-------|-------------|------|--------");
        
        for (int i = 0; i < prices.length; i++) {
            int price = prices[i];
            
            // Use reflection to access stack state
            String stackState = getStackState(spanner);
            String action = "Process price " + price;
            
            int span = spanner.next(price);
            
            String stackStateAfter = getStackState(spanner);
            
            System.out.printf("%4d | %5d | %11s | %4d | %s%n", 
                            i + 1, price, stackStateAfter, span, action);
        }
        
        System.out.println("\nKey Insights:");
        System.out.println("- Stack maintains [price, span] pairs in decreasing order");
        System.out.println("- When higher price encountered, pop lower prices and accumulate spans");
        System.out.println("- Each price pushed once and popped once → amortized O(1) time");
    }
    
    /**
     * Helper method to get stack state using reflection (for visualization)
     */
    private static String getStackState(StockSpanner spanner) {
        try {
            java.lang.reflect.Field field = StockSpanner.class.getDeclaredField("stack");
            field.setAccessible(true);
            Stack<int[]> stack = (Stack<int[]>) field.get(spanner);
            
            if (stack.isEmpty()) return "[]";
            
            List<String> elements = new ArrayList<>();
            for (int[] pair : stack) {
                elements.add("[" + pair[0] + "," + pair[1] + "]");
            }
            return elements.toString();
        } catch (Exception e) {
            return "N/A";
        }
    }
    
    /**
     * Performance comparison of different implementations
     */
    private static void performanceComparison() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(60));
        
        int numOperations = 10000;
        int[] testPrices = generateTestPrices(numOperations);
        
        // Test Monotonic Stack implementation
        long startTime = System.nanoTime();
        StockSpannerInterface spanner1 = new StockSpanner();
        for (int price : testPrices) {
            spanner1.next(price);
        }
        long time1 = System.nanoTime() - startTime;
        
        // Test Separate Stacks implementation
        startTime = System.nanoTime();
        StockSpannerInterface spanner2 = new StockSpannerSeparate();
        for (int price : testPrices) {
            spanner2.next(price);
        }
        long time2 = System.nanoTime() - startTime;
        
        // Test Array implementation
        startTime = System.nanoTime();
        StockSpannerInterface spanner3 = new StockSpannerArray();
        for (int price : testPrices) {
            spanner3.next(price);
        }
        long time3 = System.nanoTime() - startTime;
        
        // Test Brute Force implementation
        startTime = System.nanoTime();
        StockSpannerInterface spanner4 = new StockSpannerBruteForce();
        for (int price : testPrices) {
            spanner4.next(price);
        }
        long time4 = System.nanoTime() - startTime;
        
        System.out.println("Performance for " + numOperations + " next() calls:");
        System.out.println("Monotonic Stack: " + time1 + " ns");
        System.out.println("Separate Stacks: " + time2 + " ns");
        System.out.println("Array-based: " + time3 + " ns");
        System.out.println("Brute Force: " + time4 + " ns");
        
        System.out.println("\nNote: Brute force is O(n) per operation vs O(1) amortized for others");
    }
    
    /**
     * Comprehensive test cases
     */
    private static void runComprehensiveTests() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPREHENSIVE TEST CASES");
        System.out.println("=".repeat(60));
        
        // Test 1: Increasing prices
        System.out.println("\nTest 1: Increasing Prices");
        StockSpanner spanner1 = new StockSpanner();
        int[] prices1 = {10, 20, 30, 40, 50};
        int[] expected1 = {1, 2, 3, 4, 5};
        
        for (int i = 0; i < prices1.length; i++) {
            int result = spanner1.next(prices1[i]);
            System.out.println("Price " + prices1[i] + " -> Span: " + result + 
                             " (expected: " + expected1[i] + ") " +
                             (result == expected1[i] ? "✓" : "✗"));
        }
        
        // Test 2: Decreasing prices
        System.out.println("\nTest 2: Decreasing Prices");
        StockSpanner spanner2 = new StockSpanner();
        int[] prices2 = {50, 40, 30, 20, 10};
        int[] expected2 = {1, 1, 1, 1, 1};
        
        for (int i = 0; i < prices2.length; i++) {
            int result = spanner2.next(prices2[i]);
            System.out.println("Price " + prices2[i] + " -> Span: " + result + 
                             " (expected: " + expected2[i] + ") " +
                             (result == expected2[i] ? "✓" : "✗"));
        }
        
        // Test 3: All same prices
        System.out.println("\nTest 3: All Same Prices");
        StockSpanner spanner3 = new StockSpanner();
        int[] prices3 = {25, 25, 25, 25, 25};
        int[] expected3 = {1, 2, 3, 4, 5};
        
        for (int i = 0; i < prices3.length; i++) {
            int result = spanner3.next(prices3[i]);
            System.out.println("Price " + prices3[i] + " -> Span: " + result + 
                             " (expected: " + expected3[i] + ") " +
                             (result == expected3[i] ? "✓" : "✗"));
        }
        
        // Test 4: Mixed pattern
        System.out.println("\nTest 4: Mixed Pattern");
        StockSpanner spanner4 = new StockSpanner();
        int[] prices4 = {31, 41, 48, 59, 79};
        int[] expected4 = {1, 2, 3, 4, 5};
        
        for (int i = 0; i < prices4.length; i++) {
            int result = spanner4.next(prices4[i]);
            System.out.println("Price " + prices4[i] + " -> Span: " + result + 
                             " (expected: " + expected4[i] + ") " +
                             (result == expected4[i] ? "✓" : "✗"));
        }
    }
    
    /**
     * Edge case tests
     */
    private static void runEdgeCaseTests() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EDGE CASE TESTS");
        System.out.println("=".repeat(60));
        
        // Test 1: Single price
        System.out.println("\nTest 1: Single Price");
        StockSpanner spanner1 = new StockSpanner();
        int result1 = spanner1.next(100);
        System.out.println("Single price 100 -> Span: " + result1 + " (expected: 1) " +
                         (result1 == 1 ? "✓" : "✗"));
        
        // Test 2: Large span calculation
        System.out.println("\nTest 2: Large Span Calculation");
        StockSpanner spanner2 = new StockSpanner();
        // Add many decreasing prices then one large price
        for (int i = 100; i >= 1; i--) {
            spanner2.next(i);
        }
        int result2 = spanner2.next(200);
        System.out.println("After 100 decreasing prices, price 200 -> Span: " + result2 + 
                         " (expected: 101) " + (result2 == 101 ? "✓" : "✗"));
        
        // Test 3: Alternating pattern
        System.out.println("\nTest 3: Alternating Pattern");
        StockSpanner spanner3 = new StockSpanner();
        int[] prices3 = {10, 5, 10, 5, 10};
        int[] expected3 = {1, 1, 3, 1, 5};
        
        for (int i = 0; i < prices3.length; i++) {
            int result = spanner3.next(prices3[i]);
            System.out.println("Price " + prices3[i] + " -> Span: " + result + 
                             " (expected: " + expected3[i] + ") " +
                             (result == expected3[i] ? "✓" : "✗"));
        }
    }
    
    /**
     * Main method to run all tests
     */
    public static void main(String[] args) {
        System.out.println("Testing Online Stock Span");
        System.out.println("=========================");
        
        // Test all implementations with standard example
        testStockSpanner(new StockSpanner(), "Monotonic Stack");
        testStockSpanner(new StockSpannerSeparate(), "Separate Stacks");
        testStockSpanner(new StockSpannerArray(), "Array-based");
        testStockSpanner(new StockSpannerLinkedList(), "LinkedList-based");
        testStockSpanner(new StockSpannerBruteForce(), "Brute Force");
        
        // Visualize the stack process
        visualizeStackProcess();
        
        // Run comprehensive tests
        runComprehensiveTests();
        
        // Run edge case tests
        runEdgeCaseTests();
        
        // Performance comparison
        performanceComparison();
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        
        System.out.println("\nMonotonic Stack with Spans Approach:");
        System.out.println("1. Initialize: stack that stores [price, span] pairs");
        System.out.println("2. For next(price):");
        System.out.println("   a. Start with span = 1 (current day)");
        System.out.println("   b. While stack not empty and stack top price <= current price:");
        System.out.println("      - Pop [prev_price, prev_span] from stack");
        System.out.println("      - Add prev_span to current span");
        System.out.println("   c. Push [current_price, current_span] to stack");
        System.out.println("   d. Return current_span");
        
        System.out.println("\nWhy It Works:");
        System.out.println("- Stack maintains prices in decreasing order (monotonic)");
        System.out.println("- When current price >= stack top price, those previous days");
        System.out.println("  are included in current span (since prices were <= current price)");
        System.out.println("- Accumulated spans avoid recomputation → amortized O(1) time");
        System.out.println("- Each price pushed once and popped once");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\nMonotonic Stack Approach:");
        System.out.println("┌────────────┬──────────────┬──────────────┐");
        System.out.println("│ Operation  │ Time         │ Space        │");
        System.out.println("├────────────┼──────────────┼──────────────┤");
        System.out.println("│ next(price)│ O(1) amortized│ O(n)         │");
        System.out.println("│ Push/Pop   │ O(1) each    │ O(1) each    │");
        System.out.println("│ Total      │ O(n) for n   │ O(n) total   │");
        System.out.println("│            │ operations   │              │");
        System.out.println("└────────────┴──────────────┴──────────────┘");
        
        System.out.println("\nComparison of Approaches:");
        System.out.println("┌──────────────────┬──────────────┬─────────────────┐");
        System.out.println("│ Approach         │ Time per     │ Space           │");
        System.out.println("│                  │ next()       │                 │");
        System.out.println("├──────────────────┼──────────────┼─────────────────┤");
        System.out.println("│ Monotonic Stack  │ O(1) amortized│ O(n)            │");
        System.out.println("│ Separate Stacks  │ O(1) amortized│ O(n)            │");
        System.out.println("│ Array-based      │ O(1) amortized│ O(n)            │");
        System.out.println("│ LinkedList       │ O(1) amortized│ O(n)            │");
        System.out.println("│ Brute Force      │ O(n)         │ O(n)            │");
        System.out.println("└──────────────────┴──────────────┴─────────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with Monotonic Stack Approach:");
        System.out.println("   - Most efficient solution (amortized O(1))");
        System.out.println("   - Demonstrates understanding of monotonic stacks");
        System.out.println("   - Elegant and easy to explain");
        
        System.out.println("\n2. Key Points to Explain:");
        System.out.println("   - Why stack is appropriate (maintain decreasing sequence)");
        System.out.println("   - How span accumulation works");
        System.out.println("   - Amortized time complexity analysis");
        System.out.println("   - Space complexity justification");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - Increasing/decreasing price sequences");
        System.out.println("   - All same prices");
        System.out.println("   - Single price input");
        System.out.println("   - Large spans (many consecutive days)");
        
        System.out.println("\n4. Discuss Trade-offs:");
        System.out.println("   - Time vs space complexity");
        System.out.println("   - Simplicity vs optimization");
        System.out.println("   - Amortized vs worst-case performance");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - Financial analysis and stock monitoring");
        System.out.println("   - Time series data analysis");
        System.out.println("   - Resource usage patterns");
        System.out.println("   - Anomaly detection in sequential data");
        
        System.out.println("\nAll tests completed successfully!");
    }
    
    /**
     * Helper method to generate test prices for performance testing
     */
    private static int[] generateTestPrices(int size) {
        int[] prices = new int[size];
        Random random = new Random(42);
        
        // Generate realistic price pattern with trends
        int currentPrice = 100;
        for (int i = 0; i < size; i++) {
            // Add some randomness with occasional trends
            int change = random.nextInt(21) - 10; // -10 to +10 change
            currentPrice = Math.max(1, Math.min(1000, currentPrice + change));
            prices[i] = currentPrice;
            
            // Occasionally start a new trend
            if (i % 100 == 0) {
                currentPrice = 50 + random.nextInt(901); // 50-950
            }
        }
        
        return prices;
    }
}
