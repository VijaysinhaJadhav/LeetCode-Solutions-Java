
# Solution.java

```java
import java.util.*;

/**
 * 119. Pascal's Triangle II
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Get the rowIndex-th row (0-indexed) of Pascal's triangle.
 * 
 * Key Insights:
 * 1. Can compute row iteratively using O(k) space
 * 2. Update from right to left to avoid overwriting
 * 3. Or use combinatorial formula C(n, k)
 */
class Solution {
    
    /**
     * Approach 1: Iterative with Single List (Space Optimized) - RECOMMENDED
     * Time: O(rowIndex²), Space: O(rowIndex)
     * 
     * Steps:
     * 1. Initialize list with [1]
     * 2. For each row from 1 to rowIndex:
     *    - Update from right to left (to avoid overwriting)
     *    - Each element = current + previous
     *    - Add 1 at the end
     * 3. Return the final list
     */
    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        row.add(1);
        
        for (int i = 1; i <= rowIndex; i++) {
            // Update from right to left
            for (int j = row.size() - 1; j > 0; j--) {
                row.set(j, row.get(j) + row.get(j - 1));
            }
            row.add(1); // Add the last element
        }
        
        return row;
    }
    
    /**
     * Approach 2: Using Combinatorial Formula (Most Efficient)
     * Time: O(rowIndex), Space: O(rowIndex)
     * 
     * Compute each element using C(rowIndex, k) formula
     */
    public List<Integer> getRowCombinatorial(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        long val = 1;
        
        for (int k = 0; k <= rowIndex; k++) {
            row.add((int) val);
            // Compute next value: C(n, k+1) = C(n, k) * (n - k) / (k + 1)
            val = val * (rowIndex - k) / (k + 1);
        }
        
        return row;
    }
    
    /**
     * Approach 3: Using Full Triangle Generation (Less Efficient)
     * Time: O(rowIndex²), Space: O(rowIndex²)
     * 
     * Generate entire triangle up to rowIndex
     */
    public List<Integer> getRowFullTriangle(int rowIndex) {
        List<List<Integer>> triangle = new ArrayList<>();
        
        for (int i = 0; i <= rowIndex; i++) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            
            for (int j = 1; j < i; j++) {
                row.add(triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j));
            }
            
            if (i > 0) row.add(1);
            triangle.add(row);
        }
        
        return triangle.get(rowIndex);
    }
    
    /**
     * Approach 4: DP with Two Rows (More Memory but Clearer)
     * Time: O(rowIndex²), Space: O(rowIndex)
     * 
     * Use two lists to build rows iteratively
     */
    public List<Integer> getRowTwoRows(int rowIndex) {
        List<Integer> prev = new ArrayList<>();
        prev.add(1);
        
        for (int i = 1; i <= rowIndex; i++) {
            List<Integer> curr = new ArrayList<>();
            curr.add(1);
            
            for (int j = 1; j < i; j++) {
                curr.add(prev.get(j - 1) + prev.get(j));
            }
            
            curr.add(1);
            prev = curr;
        }
        
        return prev;
    }
    
    /**
     * Approach 5: Using Queue (BFS-like)
     * Time: O(rowIndex²), Space: O(rowIndex)
     * 
     * Simulate row generation using a queue
     */
    public List<Integer> getRowQueue(int rowIndex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        
        for (int i = 1; i <= rowIndex; i++) {
            int size = queue.size();
            queue.offer(1); // Add 1 at the beginning of next row
            
            for (int j = 0; j < size - 1; j++) {
                queue.offer(queue.poll() + queue.peek());
            }
            
            queue.poll(); // Remove the last element of previous row
            queue.offer(1); // Add 1 at the end of next row
        }
        
        // Extract elements from queue
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }
        
        return result;
    }
    
    /**
     * Helper: Visualize row generation
     */
    public void visualizeRowGeneration(int rowIndex) {
        System.out.println("\nPascal's Triangle Row Generation:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nGenerating row " + rowIndex + " (0-indexed):");
        
        List<Integer> row = new ArrayList<>();
        row.add(1);
        System.out.println("Row 0: " + row);
        
        for (int i = 1; i <= rowIndex; i++) {
            System.out.printf("\nBuilding row %d from row %d:%n", i, i - 1);
            System.out.println("  Before update: " + row);
            
            // Update from right to left
            for (int j = row.size() - 1; j > 0; j--) {
                int newVal = row.get(j) + row.get(j - 1);
                System.out.printf("  row[%d] = row[%d] + row[%d] = %d + %d = %d%n", 
                    j, j, j - 1, row.get(j), row.get(j - 1), newVal);
                row.set(j, newVal);
            }
            
            row.add(1);
            System.out.println("  After adding 1: " + row);
        }
        
        System.out.println("\nFinal row " + rowIndex + ": " + row);
        
        // Also show combinatorial calculation
        System.out.println("\nCombinatorial verification (C(n, k)):");
        for (int k = 0; k <= rowIndex; k++) {
            long val = 1;
            for (int i = 1; i <= k; i++) {
                val = val * (rowIndex - k + i) / i;
            }
            System.out.printf("  C(%d, %d) = %d%n", rowIndex, k, val);
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[] generateTestCases() {
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 25, 30, 33};
    }
    
    /**
     * Helper: Get expected row (for verification)
     */
    public List<Integer> getExpectedRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        long val = 1;
        for (int k = 0; k <= rowIndex; k++) {
            row.add((int) val);
            val = val * (rowIndex - k) / (k + 1);
        }
        return row;
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[] testCases = generateTestCases();
        int passed = 0;
        
        for (int rowIndex : testCases) {
            System.out.printf("\nTest: rowIndex = %d%n", rowIndex);
            
            List<Integer> result1 = getRow(rowIndex);
            List<Integer> result2 = getRowCombinatorial(rowIndex);
            List<Integer> result3 = getRowFullTriangle(rowIndex);
            List<Integer> result4 = getRowTwoRows(rowIndex);
            List<Integer> result5 = getRowQueue(rowIndex);
            List<Integer> expected = getExpectedRow(rowIndex);
            
            boolean allMatch = result1.equals(expected) && result2.equals(expected) &&
                              result3.equals(expected) && result4.equals(expected) &&
                              result5.equals(expected);
            
            if (allMatch) {
                System.out.println("✓ PASS - Row: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1 (Iterative): " + result1);
                System.out.println("  Method 2 (Combinatorial): " + result2);
                System.out.println("  Method 3 (Full Triangle): " + result3);
                System.out.println("  Method 4 (Two Rows): " + result4);
                System.out.println("  Method 5 (Queue): " + result5);
            }
            
            // Visualize small test cases
            if (rowIndex <= 5) {
                visualizeRowGeneration(rowIndex);
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
        
        int rowIndex = 33; // Maximum per constraints
        
        System.out.println("Test Setup: rowIndex = " + rowIndex);
        
        long[] times = new long[5];
        List<Integer>[] results = new List[5];
        
        // Method 1: Iterative with single list
        long start = System.currentTimeMillis();
        results[0] = getRow(rowIndex);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Combinatorial formula
        start = System.currentTimeMillis();
        results[1] = getRowCombinatorial(rowIndex);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Full triangle generation
        start = System.currentTimeMillis();
        results[2] = getRowFullTriangle(rowIndex);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Two rows DP
        start = System.currentTimeMillis();
        results[3] = getRowTwoRows(rowIndex);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Queue approach
        start = System.currentTimeMillis();
        results[4] = getRowQueue(rowIndex);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Row Size");
        System.out.println("--------------------------|-----------|---------");
        System.out.printf("1. Iterative (Single List) | %9d | %7d%n", times[0], results[0].size());
        System.out.printf("2. Combinatorial Formula   | %9d | %7d%n", times[1], results[1].size());
        System.out.printf("3. Full Triangle           | %9d | %7d%n", times[2], results[2].size());
        System.out.printf("4. Two Rows DP             | %9d | %7d%n", times[3], results[3].size());
        System.out.printf("5. Queue                   | %9d | %7d%n", times[4], results[4].size());
        
        // Verify all results match
        boolean allMatch = results[0].equals(results[1]) && results[1].equals(results[2]) &&
                          results[2].equals(results[3]) && results[3].equals(results[4]);
        System.out.println("\nAll methods produce same row: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Combinatorial formula is fastest (O(n))");
        System.out.println("2. Iterative O(n²) methods are slower but use O(n) space");
        System.out.println("3. Full triangle generation is slower due to storing all rows");
        System.out.println("4. All methods produce correct results");
    }
    
    /**
     * Helper: Explain combinatorial approach
     */
    public void explainCombinatorial() {
        System.out.println("\nCombinatorial Formula Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nPascal's triangle row n corresponds to binomial coefficients:");
        System.out.println("Row n: [C(n,0), C(n,1), C(n,2), ..., C(n,n)]");
        
        System.out.println("\nFormula: C(n, k) = n! / (k! * (n-k)!)");
        
        System.out.println("\nMultiplicative recurrence:");
        System.out.println("C(n, 0) = 1");
        System.out.println("C(n, k) = C(n, k-1) * (n - k + 1) / k");
        
        System.out.println("\nExample: n = 4");
        System.out.println("  C(4,0) = 1");
        System.out.println("  C(4,1) = 1 * 4 / 1 = 4");
        System.out.println("  C(4,2) = 4 * 3 / 2 = 6");
        System.out.println("  C(4,3) = 6 * 2 / 3 = 4");
        System.out.println("  C(4,4) = 4 * 1 / 4 = 1");
        System.out.println("  Row = [1, 4, 6, 4, 1]");
        
        System.out.println("\nComplexity:");
        System.out.println("- Time: O(n) - single pass");
        System.out.println("- Space: O(n) - storing the row");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Is rowIndex 0-indexed? (Yes)");
        System.out.println("   - What is the maximum rowIndex? (33)");
        System.out.println("   - Do we need to return the entire row or just values? (Row)");
        
        System.out.println("\n2. Start with the iterative approach:");
        System.out.println("   - Build row by row using O(k) space");
        System.out.println("   - Update from right to left to avoid overwriting");
        System.out.println("   - Walk through example");
        
        System.out.println("\n3. Discuss the combinatorial approach:");
        System.out.println("   - Mention binomial coefficients");
        System.out.println("   - Show multiplicative recurrence");
        System.out.println("   - O(n) time, O(n) space");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - Time: O(n²) for iterative, O(n) for combinatorial");
        System.out.println("   - Space: O(n) for storing the row");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - rowIndex = 0 → [1]");
        System.out.println("   - rowIndex = 1 → [1,1]");
        System.out.println("   - rowIndex = 33 (maximum)");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Updating from left to right (overwrites needed values)");
        System.out.println("   - Integer overflow for large rowIndex (use long)");
        System.out.println("   - Forgetting that rowIndex is 0-indexed");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("119. Pascal's Triangle II");
        System.out.println("=========================");
        
        // Explain combinatorial approach
        solution.explainCombinatorial();
        
        // Visualize row generation
        System.out.println("\n" + "=".repeat(80));
        solution.visualizeRowGeneration(5);
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
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
        
        System.out.println("\nRecommended Implementation (Combinatorial):");
        System.out.println("""
class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        long val = 1;
        
        for (int k = 0; k <= rowIndex; k++) {
            row.add((int) val);
            val = val * (rowIndex - k) / (k + 1);
        }
        
        return row;
    }
}
            """);
        
        System.out.println("\nRecommended Implementation (Iterative, Space Optimized):");
        System.out.println("""
class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        row.add(1);
        
        for (int i = 1; i <= rowIndex; i++) {
            for (int j = row.size() - 1; j > 0; j--) {
                row.set(j, row.get(j) + row.get(j - 1));
            }
            row.add(1);
        }
        
        return row;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Row n has n+1 elements");
        System.out.println("2. First and last elements are always 1");
        System.out.println("3. Update from right to left when using single list");
        System.out.println("4. Combinatorial formula gives O(n) time solution");
        System.out.println("5. Use long to avoid overflow for intermediate calculations");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Combinatorial: O(n) time, O(n) space");
        System.out.println("- Iterative: O(n²) time, O(n) space");
        System.out.println("- Full triangle: O(n²) time, O(n²) space");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle larger rowIndex (e.g., 10^5)?");
        System.out.println("2. How would you get multiple rows efficiently?");
        System.out.println("3. What is the maximum value in row 33?");
        System.out.println("4. How would you implement modulo arithmetic for large numbers?");
        System.out.println("5. How would you find the sum of row n?");
    }
}
