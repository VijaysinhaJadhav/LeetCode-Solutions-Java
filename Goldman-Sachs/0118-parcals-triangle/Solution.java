
# Solution.java

```java
import java.util.*;

/**
 * 118. Pascal's Triangle
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Generate the first numRows of Pascal's triangle.
 * 
 * Key Insights:
 * 1. First row is always [1]
 * 2. Each row starts and ends with 1
 * 3. Inner elements: triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j]
 */
class Solution {
    
    /**
     * Approach 1: Dynamic Programming (Iterative) - RECOMMENDED
     * Time: O(numRows²), Space: O(numRows²)
     * 
     * Steps:
     * 1. Initialize result list
     * 2. For each row from 0 to numRows-1:
     *    - Create new list for current row
     *    - First element is always 1
     *    - For inner elements, sum two elements from previous row
     *    - Last element is always 1
     * 3. Add row to result
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        
        if (numRows == 0) return triangle;
        
        // First row
        triangle.add(new ArrayList<>());
        triangle.get(0).add(1);
        
        // Generate subsequent rows
        for (int i = 1; i < numRows; i++) {
            List<Integer> prevRow = triangle.get(i - 1);
            List<Integer> currentRow = new ArrayList<>();
            
            // First element is always 1
            currentRow.add(1);
            
            // Inner elements
            for (int j = 1; j < i; j++) {
                currentRow.add(prevRow.get(j - 1) + prevRow.get(j));
            }
            
            // Last element is always 1
            currentRow.add(1);
            
            triangle.add(currentRow);
        }
        
        return triangle;
    }
    
    /**
     * Approach 2: Using Combinatorial Formula (n choose k)
     * Time: O(numRows²), Space: O(numRows²)
     * 
     * Each element can be computed as C(row, col) = row! / (col! * (row-col)!)
     */
    public List<List<Integer>> generateCombination(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            long val = 1;
            
            for (int j = 0; j <= i; j++) {
                row.add((int) val);
                // Compute next value using formula: C(n, k+1) = C(n, k) * (n - k) / (k + 1)
                val = val * (i - j) / (j + 1);
            }
            
            triangle.add(row);
        }
        
        return triangle;
    }
    
    /**
     * Approach 3: Dynamic Programming with Single List (Space Optimized)
     * Time: O(numRows²), Space: O(numRows²) for storing result
     * 
     * Build each row using only previous row reference
     */
    public List<List<Integer>> generateOptimized(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j));
                }
            }
            
            triangle.add(row);
        }
        
        return triangle;
    }
    
    /**
     * Approach 4: Recursive Construction
     * Time: O(numRows²), Space: O(numRows²)
     * 
     * Build triangle recursively
     */
    public List<List<Integer>> generateRecursive(int numRows) {
        if (numRows == 0) return new ArrayList<>();
        if (numRows == 1) {
            List<List<Integer>> triangle = new ArrayList<>();
            triangle.add(Arrays.asList(1));
            return triangle;
        }
        
        List<List<Integer>> triangle = generateRecursive(numRows - 1);
        List<Integer> prevRow = triangle.get(triangle.size() - 1);
        List<Integer> currentRow = new ArrayList<>();
        
        currentRow.add(1);
        for (int i = 1; i < numRows - 1; i++) {
            currentRow.add(prevRow.get(i - 1) + prevRow.get(i));
        }
        currentRow.add(1);
        
        triangle.add(currentRow);
        return triangle;
    }
    
    /**
     * Approach 5: Using Arrays (then convert to List)
     * Time: O(numRows²), Space: O(numRows²)
     * 
     * Build using 2D array for clarity
     */
    public List<List<Integer>> generateArray(int numRows) {
        int[][] triangle = new int[numRows][];
        
        for (int i = 0; i < numRows; i++) {
            triangle[i] = new int[i + 1];
            triangle[i][0] = 1;
            triangle[i][i] = 1;
            
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }
        
        // Convert to List<List<Integer>>
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                row.add(triangle[i][j]);
            }
            result.add(row);
        }
        
        return result;
    }
    
    /**
     * Helper: Print Pascal's triangle with formatting
     */
    public void printTriangle(List<List<Integer>> triangle) {
        if (triangle == null || triangle.isEmpty()) return;
        
        int numRows = triangle.size();
        int maxDigits = String.valueOf(triangle.get(numRows - 1).get(numRows / 2)).length() + 1;
        
        for (int i = 0; i < numRows; i++) {
            // Add leading spaces
            int spaces = (numRows - i - 1) * (maxDigits / 2 + 1);
            System.out.print(" ".repeat(spaces));
            
            for (int j = 0; j <= i; j++) {
                System.out.printf("%" + maxDigits + "d", triangle.get(i).get(j));
            }
            System.out.println();
        }
    }
    
    /**
     * Helper: Visualize Pascal's triangle generation
     */
    public void visualizeGeneration(int numRows) {
        System.out.println("\nPascal's Triangle Generation:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nGenerating " + numRows + " rows:");
        
        List<List<Integer>> triangle = new ArrayList<>();
        
        for (int i = 0; i < numRows; i++) {
            System.out.printf("\nRow %d:%n", i);
            
            if (i == 0) {
                triangle.add(Arrays.asList(1));
                System.out.println("  First row: [1]");
                continue;
            }
            
            List<Integer> prevRow = triangle.get(i - 1);
            List<Integer> currentRow = new ArrayList<>();
            
            // First element
            currentRow.add(1);
            System.out.printf("  Start with 1%n");
            
            // Inner elements
            for (int j = 1; j < i; j++) {
                int val = prevRow.get(j - 1) + prevRow.get(j);
                currentRow.add(val);
                System.out.printf("  [%d] = prev[%d] + prev[%d] = %d + %d = %d%n", 
                    j, j - 1, j, prevRow.get(j - 1), prevRow.get(j), val);
            }
            
            // Last element
            currentRow.add(1);
            System.out.printf("  End with 1%n");
            
            triangle.add(currentRow);
            System.out.println("  Row: " + currentRow);
        }
        
        System.out.println("\nFinal Pascal's Triangle:");
        printTriangle(triangle);
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[] testCases = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30};
        
        for (int numRows : testCases) {
            System.out.printf("\nTest: numRows = %d%n", numRows);
            
            List<List<Integer>> result1 = generate(numRows);
            List<List<Integer>> result2 = generateCombination(numRows);
            List<List<Integer>> result3 = generateOptimized(numRows);
            List<List<Integer>> result4 = generateRecursive(numRows);
            List<List<Integer>> result5 = generateArray(numRows);
            
            boolean allMatch = result1.equals(result2) && result2.equals(result3) &&
                              result3.equals(result4) && result4.equals(result5);
            
            if (allMatch) {
                System.out.println("✓ PASS - Generated " + result1.size() + " rows");
                if (numRows <= 5) {
                    System.out.println("  Triangle: " + result1);
                } else {
                    System.out.println("  Last row: " + result1.get(numRows - 1));
                }
            } else {
                System.out.println("✗ FAIL - Methods produce different results");
            }
        }
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=".repeat(50));
        
        int numRows = 30;
        
        long[] times = new long[5];
        List<List<Integer>>[] results = new List[5];
        
        // Method 1: DP Iterative
        long start = System.currentTimeMillis();
        results[0] = generate(numRows);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Combinatorial Formula
        start = System.currentTimeMillis();
        results[1] = generateCombination(numRows);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Optimized DP
        start = System.currentTimeMillis();
        results[2] = generateOptimized(numRows);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Recursive
        start = System.currentTimeMillis();
        results[3] = generateRecursive(numRows);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Array-based
        start = System.currentTimeMillis();
        results[4] = generateArray(numRows);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults for numRows = " + numRows);
        System.out.println("Method                    | Time (ms) | Last Row Size");
        System.out.println("--------------------------|-----------|---------------");
        System.out.printf("1. DP Iterative           | %9d | %13d%n", times[0], results[0].get(numRows - 1).size());
        System.out.printf("2. Combinatorial          | %9d | %13d%n", times[1], results[1].get(numRows - 1).size());
        System.out.printf("3. Optimized DP           | %9d | %13d%n", times[2], results[2].get(numRows - 1).size());
        System.out.printf("4. Recursive              | %9d | %13d%n", times[3], results[3].get(numRows - 1).size());
        System.out.printf("5. Array-based            | %9d | %13d%n", times[4], results[4].get(numRows - 1).size());
        
        System.out.println("\nObservations:");
        System.out.println("1. All methods have similar performance for numRows=30");
        System.out.println("2. DP iterative is most readable");
        System.out.println("3. Combinatorial method avoids storing previous rows");
        System.out.println("4. Recursive method has function call overhead");
    }
    
    /**
     * Helper: Explain Pascal's triangle properties
     */
    public void explainProperties() {
        System.out.println("\nPascal's Triangle Properties:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Construction:");
        System.out.println("   - First and last element of each row is 1");
        System.out.println("   - Each inner element = sum of two elements above");
        
        System.out.println("\n2. Mathematical significance:");
        System.out.println("   - Row n contains coefficients of (x + y)^n");
        System.out.println("   - Element at row n, col k = C(n, k) = n! / (k! * (n-k)!)");
        
        System.out.println("\n3. Patterns:");
        System.out.println("   - Sum of row n = 2^n");
        System.out.println("   - Prime rows have all elements divisible by row number");
        System.out.println("   - Diagonal sums = Fibonacci numbers");
        
        System.out.println("\nExample: Row 5 = [1, 4, 6, 4, 1]");
        System.out.println("  Sum = 1 + 4 + 6 + 4 + 1 = 16 = 2^4");
        System.out.println("  Row 5 corresponds to (x + y)^4 = x^4 + 4x^3y + 6x^2y^2 + 4xy^3 + y^4");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What should be returned for numRows = 0? (empty list)");
        System.out.println("   - Is there a maximum limit? (30)");
        
        System.out.println("\n2. Start with base case:");
        System.out.println("   - First row is always [1]");
        System.out.println("   - Build row by row from previous row");
        
        System.out.println("\n3. Explain the recurrence:");
        System.out.println("   - triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j]");
        System.out.println("   - Walk through example");
        
        System.out.println("\n4. Discuss implementation details:");
        System.out.println("   - Use ArrayList for dynamic sizing");
        System.out.println("   - First and last elements are always 1");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(numRows²) - sum of 1 to numRows");
        System.out.println("   - Space: O(numRows²) - storing the entire triangle");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - numRows = 1");
        System.out.println("   - numRows = 0 (not in constraints but good to handle)");
        System.out.println("   - numRows = 30 (maximum)");
        
        System.out.println("\n7. Follow-up questions:");
        System.out.println("   - How would you get only the nth row? (Pascal's Triangle II)");
        System.out.println("   - How would you implement using O(k) space?");
        System.out.println("   - What about using combinatorial formula?");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("118. Pascal's Triangle");
        System.out.println("======================");
        
        // Explain properties
        solution.explainProperties();
        
        // Visualize generation
        System.out.println("\n" + "=".repeat(80));
        solution.visualizeGeneration(6);
        
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
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        
        if (numRows == 0) return triangle;
        
        triangle.add(new ArrayList<>());
        triangle.get(0).add(1);
        
        for (int i = 1; i < numRows; i++) {
            List<Integer> prevRow = triangle.get(i - 1);
            List<Integer> currentRow = new ArrayList<>();
            
            currentRow.add(1);
            
            for (int j = 1; j < i; j++) {
                currentRow.add(prevRow.get(j - 1) + prevRow.get(j));
            }
            
            currentRow.add(1);
            triangle.add(currentRow);
        }
        
        return triangle;
    }
}
            """);
        
        System.out.println("\nAlternative (Space Optimized for Row Generation):");
        System.out.println("""
class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j));
                }
            }
            
            triangle.add(row);
        }
        
        return triangle;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Each row starts and ends with 1");
        System.out.println("2. Inner elements sum of two elements above");
        System.out.println("3. Row n has n+1 elements");
        System.out.println("4. Time: O(n²), Space: O(n²)");
        System.out.println("5. Can also use combinatorial formula: C(n, k)");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(numRows²) - number of elements in triangle");
        System.out.println("- Space: O(numRows²) - storing all elements");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you generate only the nth row? (Pascal's Triangle II)");
        System.out.println("2. How would you implement using O(k) space?");
        System.out.println("3. What is the sum of numbers in row n? (2^n)");
        System.out.println("4. What is the relationship with binomial coefficients?");
        System.out.println("5. How would you find the maximum element in each row?");
    }
}
