
# Solution.java

```java
import java.util.*;

/**
 * 135. Candy
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Distribute candies to children such that:
 * - Each child gets at least 1 candy
 * - Children with higher rating get more candies than neighbors
 * 
 * Key Insights:
 * 1. Two-pass greedy: left to right, then right to left
 * 2. First pass ensures left neighbor constraint
 * 3. Second pass ensures right neighbor constraint
 * 4. Take maximum of both passes
 */
class Solution {
    
    /**
     * Approach 1: Two-Pass Greedy (Recommended)
     * Time: O(n), Space: O(n)
     * 
     * Steps:
     * 1. Initialize candies array with 1 for each child
     * 2. Left-to-right pass: if rating[i] > rating[i-1], candies[i] = candies[i-1] + 1
     * 3. Right-to-left pass: if rating[i] > rating[i+1], candies[i] = max(candies[i], candies[i+1] + 1)
     * 4. Sum all candies
     */
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);
        
        // Left to right pass
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        
        // Right to left pass
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }
        
        // Sum all candies
        int total = 0;
        for (int candy : candies) {
            total += candy;
        }
        
        return total;
    }
    
    /**
     * Approach 2: Single Pass with Slope Tracking (Space Optimized)
     * Time: O(n), Space: O(1)
     * 
     * Track increasing and decreasing slopes to avoid extra array
     */
    public int candyOptimized(int[] ratings) {
        int n = ratings.length;
        int total = 1; // First child gets 1 candy
        int prev = 1; // Candies for previous child
        int down = 0; // Length of current decreasing sequence
        int peak = 0; // Peak value of increasing sequence
        
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                // Increasing: prev + 1
                prev++;
                total += prev;
                down = 0;
                peak = prev;
            } else if (ratings[i] == ratings[i - 1]) {
                // Equal: reset to 1
                prev = 1;
                total += prev;
                down = 0;
                peak = 0;
            } else {
                // Decreasing: need to adjust
                down++;
                if (down >= peak) {
                    down++;
                }
                total += down;
                prev = 1;
            }
        }
        
        return total;
    }
    
    /**
     * Approach 3: Two-Pass with Single Array (Explicit)
     * Time: O(n), Space: O(n)
     * 
     * More explicit version with clear passes
     */
    public int candyExplicit(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        int[] right = new int[n];
        
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);
        
        // Left pass
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            }
        }
        
        // Right pass
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            }
        }
        
        // Combine
        int total = 0;
        for (int i = 0; i < n; i++) {
            total += Math.max(left[i], right[i]);
        }
        
        return total;
    }
    
    /**
     * Approach 4: Using Pair Class (Educational)
     * Time: O(n), Space: O(n)
     * 
     * Track candy count with pair class
     */
    class Child {
        int rating;
        int candies;
        Child(int rating) {
            this.rating = rating;
            this.candies = 1;
        }
    }
    
    public int candyPair(int[] ratings) {
        int n = ratings.length;
        Child[] children = new Child[n];
        for (int i = 0; i < n; i++) {
            children[i] = new Child(ratings[i]);
        }
        
        // Left pass
        for (int i = 1; i < n; i++) {
            if (children[i].rating > children[i - 1].rating) {
                children[i].candies = children[i - 1].candies + 1;
            }
        }
        
        // Right pass
        for (int i = n - 2; i >= 0; i--) {
            if (children[i].rating > children[i + 1].rating) {
                children[i].candies = Math.max(children[i].candies, children[i + 1].candies + 1);
            }
        }
        
        // Sum
        int total = 0;
        for (Child child : children) {
            total += child.candies;
        }
        
        return total;
    }
    
    /**
     * Approach 5: BFS-like Level Assignment (Less Efficient)
     * Time: O(n²), Space: O(n)
     * 
     * Assign candies in levels from lowest ratings upward
     */
    public int candyBFS(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        boolean[] assigned = new boolean[n];
        
        // Find local minima (children with no lower neighbors)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            boolean isLocalMin = true;
            if (i > 0 && ratings[i] > ratings[i - 1]) isLocalMin = false;
            if (i < n - 1 && ratings[i] > ratings[i + 1]) isLocalMin = false;
            if (isLocalMin) {
                candies[i] = 1;
                assigned[i] = true;
                queue.offer(i);
            }
        }
        
        // BFS to assign higher levels
        while (!queue.isEmpty()) {
            int i = queue.poll();
            
            // Check left neighbor
            if (i > 0 && !assigned[i - 1] && ratings[i - 1] > ratings[i]) {
                candies[i - 1] = candies[i] + 1;
                assigned[i - 1] = true;
                queue.offer(i - 1);
            }
            // Check right neighbor
            if (i < n - 1 && !assigned[i + 1] && ratings[i + 1] > ratings[i]) {
                candies[i + 1] = candies[i] + 1;
                assigned[i + 1] = true;
                queue.offer(i + 1);
            }
        }
        
        // Sum
        int total = 0;
        for (int candy : candies) {
            total += candy;
        }
        
        return total;
    }
    
    /**
     * Helper: Visualize candy distribution
     */
    public void visualizeDistribution(int[] ratings) {
        System.out.println("\nCandy Distribution Visualization:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nRatings: " + Arrays.toString(ratings));
        
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);
        
        System.out.println("\nStep 1: Initialize all children with 1 candy");
        System.out.println("Candies: " + Arrays.toString(candies));
        
        System.out.println("\nStep 2: Left to right pass");
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
                System.out.printf("  ratings[%d]=%d > ratings[%d]=%d → candies[%d] = %d + 1 = %d%n",
                    i, ratings[i], i - 1, ratings[i - 1], i, candies[i - 1], candies[i]);
            } else {
                System.out.printf("  ratings[%d]=%d ≤ ratings[%d]=%d → no change%n",
                    i, ratings[i], i - 1, ratings[i - 1]);
            }
        }
        System.out.println("Candies after left pass: " + Arrays.toString(candies));
        
        System.out.println("\nStep 3: Right to left pass");
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                int newVal = candies[i + 1] + 1;
                if (newVal > candies[i]) {
                    System.out.printf("  ratings[%d]=%d > ratings[%d]=%d → candies[%d] = max(%d, %d) = %d%n",
                        i, ratings[i], i + 1, ratings[i + 1], i, candies[i], newVal, newVal);
                    candies[i] = newVal;
                } else {
                    System.out.printf("  ratings[%d]=%d > ratings[%d]=%d but candies[%d]=%d already >= %d → no change%n",
                        i, ratings[i], i + 1, ratings[i + 1], i, candies[i], newVal);
                }
            } else {
                System.out.printf("  ratings[%d]=%d ≤ ratings[%d]=%d → no change%n",
                    i, ratings[i], i + 1, ratings[i + 1]);
            }
        }
        System.out.println("Candies after right pass: " + Arrays.toString(candies));
        
        int total = 0;
        for (int candy : candies) total += candy;
        System.out.println("\nTotal candies needed: " + total);
        
        // Show distribution visually
        System.out.println("\nDistribution:");
        for (int i = 0; i < n; i++) {
            System.out.printf("Child %d: rating=%d → %d candy%s%n", 
                i, ratings[i], candies[i], candies[i] == 1 ? "" : "s");
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            {1, 0, 2},           // Example 1 → 5
            {1, 2, 2},           // Example 2 → 4
            {1, 2, 3, 4, 5},     // Strictly increasing → 15
            {5, 4, 3, 2, 1},     // Strictly decreasing → 15
            {1, 3, 2, 2, 1},     // Mixed → 7
            {1, 2, 3, 4, 3, 2, 1}, // Peak → 16
            {1, 2, 3, 2, 1, 2, 3, 2, 1}, // Multiple peaks → 17
            {1, 1, 1, 1, 1},     // All equal → 5
            {0, 1, 2, 3, 2, 1, 0}, // Symmetric → 16
            {3, 2, 1, 2, 3}      // Valley → 9
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][] testCases = generateTestCases();
        int[] expected = {5, 4, 15, 15, 7, 16, 17, 5, 16, 9};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] ratings = testCases[i];
            System.out.printf("\nTest %d: %s%n", i + 1, Arrays.toString(ratings));
            
            int result1 = candy(ratings.clone());
            int result2 = candyOptimized(ratings.clone());
            int result3 = candyExplicit(ratings.clone());
            int result4 = candyPair(ratings.clone());
            int result5 = candyBFS(ratings.clone());
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Total candies: " + result1);
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
                visualizeDistribution(ratings);
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
        int n = 20000;
        int[] ratings = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            ratings[i] = rand.nextInt(20000);
        }
        
        System.out.println("Test Setup: " + n + " children");
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: Two-Pass Greedy
        int[] r1 = ratings.clone();
        long start = System.currentTimeMillis();
        results[0] = candy(r1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Single Pass Optimized
        int[] r2 = ratings.clone();
        start = System.currentTimeMillis();
        results[1] = candyOptimized(r2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Explicit Two-Pass
        int[] r3 = ratings.clone();
        start = System.currentTimeMillis();
        results[2] = candyExplicit(r3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Pair Class
        int[] r4 = ratings.clone();
        start = System.currentTimeMillis();
        results[3] = candyPair(r4);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: BFS (skip for large n)
        times[4] = -1;
        results[4] = -1;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Two-Pass Greedy        | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Single Pass Optimized  | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. Explicit Two-Pass      | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. Pair Class             | %9d | %6d%n", times[3], results[3]);
        System.out.printf("5. BFS                    | %9s | %6s%n", "N/A", "N/A");
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Two-pass greedy is fast and simple");
        System.out.println("2. Single pass optimized uses O(1) space");
        System.out.println("3. All O(n) methods scale linearly");
        System.out.println("4. BFS approach is O(n²) and too slow for large inputs");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single child:");
        int[] ratings1 = {5};
        System.out.println("   Input: [5]");
        System.out.println("   Output: " + candy(ratings1));
        
        System.out.println("\n2. Two children with increasing ratings:");
        int[] ratings2 = {1, 2};
        System.out.println("   Input: [1,2]");
        System.out.println("   Output: " + candy(ratings2));
        
        System.out.println("\n3. Two children with decreasing ratings:");
        int[] ratings3 = {2, 1};
        System.out.println("   Input: [2,1]");
        System.out.println("   Output: " + candy(ratings3));
        
        System.out.println("\n4. Two children with same ratings:");
        int[] ratings4 = {1, 1};
        System.out.println("   Input: [1,1]");
        System.out.println("   Output: " + candy(ratings4));
        
        System.out.println("\n5. All equal ratings:");
        int[] ratings5 = {3, 3, 3, 3, 3};
        System.out.println("   Input: [3,3,3,3,3]");
        System.out.println("   Output: " + candy(ratings5));
        
        System.out.println("\n6. Zigzag pattern:");
        int[] ratings6 = {1, 2, 1, 2, 1, 2};
        System.out.println("   Input: [1,2,1,2,1,2]");
        System.out.println("   Output: " + candy(ratings6));
        
        System.out.println("\n7. Large decreasing then increasing:");
        int[] ratings7 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        long start = System.currentTimeMillis();
        int result = candy(ratings7);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 19 elements (valley)");
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain the greedy approach
     */
    public void explainGreedy() {
        System.out.println("\nGreedy Algorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nProblem: Distribute minimum candies satisfying neighbor constraints.");
        
        System.out.println("\nKey Insight:");
        System.out.println("The candy count for each child is determined by the longest increasing sequence ending at that child.");
        
        System.out.println("\nTwo-Pass Algorithm:");
        System.out.println("1. Left-to-right pass: ensures left neighbor constraint");
        System.out.println("2. Right-to-left pass: ensures right neighbor constraint");
        System.out.println("3. Take maximum of both passes");
        
        System.out.println("\nProof of Correctness:");
        System.out.println("- The left pass ensures each child has more candies than left neighbor if rating higher");
        System.out.println("- The right pass ensures each child has more candies than right neighbor if rating higher");
        System.out.println("- Taking maximum satisfies both constraints simultaneously");
        System.out.println("- The assignment is minimal because each candy count is the smallest possible");
        
        System.out.println("\nExample: ratings = [1, 0, 2]");
        System.out.println("  Left pass:  [1, 1, 2] (2 gets +1 from 1)");
        System.out.println("  Right pass: [2, 1, 2] (0 gets max(1, 2))");
        System.out.println("  Result: 2 + 1 + 2 = 5");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What is the minimum candy? (1)");
        System.out.println("   - Can neighbors have equal candies if ratings equal? (Yes)");
        System.out.println("   - Is the line circular? (No, linear)");
        
        System.out.println("\n2. Start with simple cases:");
        System.out.println("   - All equal → each gets 1 candy");
        System.out.println("   - Strictly increasing → 1,2,3,...");
        
        System.out.println("\n3. Propose two-pass greedy:");
        System.out.println("   - First pass: left to right");
        System.out.println("   - Second pass: right to left");
        System.out.println("   - Take maximum of both passes");
        
        System.out.println("\n4. Explain why it works:");
        System.out.println("   - Each pass ensures one direction of constraints");
        System.out.println("   - Combining them satisfies both directions");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(n) - two passes through array");
        System.out.println("   - Space: O(n) for candy array (can be O(1) with slope tracking)");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - Single child");
        System.out.println("   - All equal ratings");
        System.out.println("   - Strictly increasing/decreasing");
        System.out.println("   - Valley/peak patterns");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Not resetting properly for equal ratings");
        System.out.println("   - Forgetting to take max in second pass");
        System.out.println("   - Off-by-one errors in loops");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("135. Candy");
        System.out.println("=========");
        
        // Explain greedy approach
        solution.explainGreedy();
        
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
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);
        
        // Left to right
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        
        // Right to left
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }
        
        int total = 0;
        for (int candy : candies) total += candy;
        return total;
    }
}
            """);
        
        System.out.println("\nAlternative (Space Optimized):");
        System.out.println("""
class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int total = 1;
        int prev = 1;
        int down = 0;
        int peak = 0;
        
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                prev++;
                total += prev;
                down = 0;
                peak = prev;
            } else if (ratings[i] == ratings[i - 1]) {
                prev = 1;
                total += prev;
                down = 0;
                peak = 0;
            } else {
                down++;
                if (down >= peak) {
                    down++;
                }
                total += down;
                prev = 1;
            }
        }
        
        return total;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Two-pass greedy is the standard solution");
        System.out.println("2. Each child gets at least 1 candy");
        System.out.println("3. Left pass ensures left neighbor constraint");
        System.out.println("4. Right pass ensures right neighbor constraint");
        System.out.println("5. Take maximum to satisfy both directions");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - two passes through array");
        System.out.println("- Space: O(n) for candy array (O(1) with optimization)");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you modify for circular arrangement?");
        System.out.println("2. What if children can be arranged arbitrarily?");
        System.out.println("3. How would you distribute candies with limited supply?");
        System.out.println("4. How would you handle different cost for candies?");
        System.out.println("5. What if ratings can be updated dynamically?");
    }
}
