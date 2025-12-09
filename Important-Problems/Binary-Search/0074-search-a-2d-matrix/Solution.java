
## Solution.java

```java
/**
 * 74. Search a 2D Matrix
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Write an efficient algorithm that searches for a target value in an m x n matrix.
 * The matrix has the following properties:
 * 1. Integers in each row are sorted in non-decreasing order.
 * 2. The first integer of each row is greater than the last integer of the previous row.
 * 
 * Key Insights:
 * 1. The matrix can be treated as one sorted 1D array of size m*n
 * 2. Use binary search with coordinate mapping: row = mid/n, col = mid%n
 * 3. Alternative: first binary search to find row, then binary search within row
 * 4. O(log(m*n)) time complexity required
 * 
 * Approach (Single Binary Search):
 * 1. Treat matrix as 1D array [0, m*n-1]
 * 2. While left <= right, calculate mid
 * 3. Convert mid to 2D coordinates: row = mid/n, col = mid%n
 * 4. Compare matrix[row][col] with target
 * 5. Adjust search range based on comparison
 * 
 * Time Complexity: O(log(m*n))
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search, Matrix
 */

class Solution {
    /**
     * Approach 1: Single Binary Search (Flattened Matrix) - RECOMMENDED
     * O(log(m*n)) time, O(1) space - Optimal solution
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0;
        int right = m * n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // Convert 1D index to 2D coordinates
            int row = mid / n;
            int col = mid % n;
            int midValue = matrix[row][col];
            
            if (midValue == target) {
                return true;
            } else if (midValue < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 2: Two Binary Searches (Row then Column)
     * O(log m + log n) = O(log(m*n)) time, O(1) space
     */
    public boolean searchMatrixTwoSearches(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // First binary search: find the correct row
        int top = 0;
        int bottom = m - 1;
        int targetRow = -1;
        
        while (top <= bottom) {
            int midRow = top + (bottom - top) / 2;
            if (matrix[midRow][0] <= target && target <= matrix[midRow][n - 1]) {
                targetRow = midRow;
                break;
            } else if (matrix[midRow][0] > target) {
                bottom = midRow - 1;
            } else {
                top = midRow + 1;
            }
        }
        
        if (targetRow == -1) {
            return false;
        }
        
        // Second binary search: search within the target row
        int left = 0;
        int right = n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (matrix[targetRow][mid] == target) {
                return true;
            } else if (matrix[targetRow][mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 3: Two Binary Searches (Optimized Row Search)
     * More efficient row search using last element comparison
     */
    public boolean searchMatrixOptimizedTwoSearches(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Find the row where target could be
        int top = 0;
        int bottom = m - 1;
        while (top <= bottom) {
            int mid = top + (bottom - top) / 2;
            if (matrix[mid][0] > target) {
                bottom = mid - 1;
            } else if (matrix[mid][n - 1] < target) {
                top = mid + 1;
            } else {
                // Found the potential row
                return binarySearchInRow(matrix[mid], target);
            }
        }
        
        return false;
    }
    
    private boolean binarySearchInRow(int[] row, int target) {
        int left = 0;
        int right = row.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (row[mid] == target) {
                return true;
            } else if (row[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 4: Search from Top-Right Corner
     * O(m + n) time - Not optimal but interesting alternative
     */
    public boolean searchMatrixTopRight(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        int row = 0;
        int col = n - 1;
        
        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--; // Move left
            } else {
                row++; // Move down
            }
        }
        
        return false;
    }
    
    /**
     * Approach 5: Linear Search (Not Recommended - for comparison only)
     * O(m*n) time - Too slow for large matrices
     */
    public boolean searchMatrixLinear(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Approach 6: Recursive Binary Search
     * O(log(m*n)) time, O(log(m*n)) space due to recursion
     */
    public boolean searchMatrixRecursive(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        return binarySearchRecursive(matrix, target, 0, m * n - 1, n);
    }
    
    private boolean binarySearchRecursive(int[][] matrix, int target, int left, int right, int cols) {
        if (left > right) {
            return false;
        }
        
        int mid = left + (right - left) / 2;
        int row = mid / cols;
        int col = mid % cols;
        int midValue = matrix[row][col];
        
        if (midValue == target) {
            return true;
        } else if (midValue < target) {
            return binarySearchRecursive(matrix, target, mid + 1, right, cols);
        } else {
            return binarySearchRecursive(matrix, target, left, mid - 1, cols);
        }
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    private void visualizeBinarySearch(int[][] matrix, int target) {
        System.out.println("\nBinary Search Visualization:");
        System.out.println("Matrix: " + java.util.Arrays.deepToString(matrix));
        System.out.println("Target: " + target);
        System.out.println("Dimensions: " + matrix.length + " x " + matrix[0].length);
        System.out.println("Total elements: " + (matrix.length * matrix[0].length));
        
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0;
        int right = m * n - 1;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Mid | (Row,Col) | Value | Comparison | Action");
        System.out.println("-----|------|-------|-----|-----------|-------|------------|--------");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int row = mid / n;
            int col = mid % n;
            int midValue = matrix[row][col];
            String comparison = midValue + " ? " + target;
            String action;
            
            if (midValue == target) {
                action = "FOUND at (" + row + "," + col + ")";
                System.out.printf("%4d | %4d | %5d | %3d | (%2d,%2d) | %5d | %10s | %s%n", 
                                step, left, right, mid, row, col, midValue, comparison, action);
                return;
            } else if (midValue < target) {
                action = "Value < target, search RIGHT";
                left = mid + 1;
            } else {
                action = "Value > target, search LEFT";
                right = mid - 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | (%2d,%2d) | %5d | %10s | %s%n", 
                            step, left, right, mid, row, col, midValue, comparison, action);
            step++;
        }
        
        System.out.println("\nTarget " + target + " not found in matrix");
    }
    
    /**
     * Helper method to visualize two-search approach
     */
    private void visualizeTwoSearches(int[][] matrix, int target) {
        System.out.println("\nTwo-Search Approach Visualization:");
        System.out.println("Matrix: " + java.util.Arrays.deepToString(matrix));
        System.out.println("Target: " + target);
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        System.out.println("\nStep 1: Find correct row");
        System.out.println("-----------------------");
        
        int top = 0;
        int bottom = m - 1;
        int targetRow = -1;
        int step = 1;
        
        System.out.println("Step | Top | Bottom | Mid | First Element | Last Element | Action");
        System.out.println("-----|-----|--------|-----|---------------|--------------|--------");
        
        while (top <= bottom) {
            int mid = top + (bottom - top) / 2;
            int first = matrix[mid][0];
            int last = matrix[mid][n - 1];
            String action;
            
            if (first <= target && target <= last) {
                targetRow = mid;
                action = "FOUND row " + mid + " [" + first + "..." + last + "]";
                System.out.printf("%4d | %3d | %6d | %3d | %13d | %12d | %s%n", 
                                step, top, bottom, mid, first, last, action);
                break;
            } else if (first > target) {
                action = "First > target, search UP";
                bottom = mid - 1;
            } else {
                action = "Last < target, search DOWN";
                top = mid + 1;
            }
            
            System.out.printf("%4d | %3d | %6d | %3d | %13d | %12d | %s%n", 
                            step, top, bottom, mid, first, last, action);
            step++;
        }
        
        if (targetRow == -1) {
            System.out.println("No suitable row found for target " + target);
            return;
        }
        
        System.out.println("\nStep 2: Search within row " + targetRow);
        System.out.println("Row: " + java.util.Arrays.toString(matrix[targetRow]));
        System.out.println("-----------------------------------");
        
        int left = 0;
        int right = n - 1;
        step = 1;
        
        System.out.println("Step | Left | Right | Mid | Value | Comparison | Action");
        System.out.println("-----|------|-------|-----|-------|------------|--------");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int value = matrix[targetRow][mid];
            String comparison = value + " ? " + target;
            String action;
            
            if (value == target) {
                action = "FOUND at column " + mid;
                System.out.printf("%4d | %4d | %5d | %3d | %5d | %10s | %s%n", 
                                step, left, right, mid, value, comparison, action);
                return;
            } else if (value < target) {
                action = "Value < target, search RIGHT";
                left = mid + 1;
            } else {
                action = "Value > target, search LEFT";
                right = mid - 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %5d | %10s | %s%n", 
                            step, left, right, mid, value, comparison, action);
            step++;
        }
        
        System.out.println("Target " + target + " not found in row " + targetRow);
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[][] matrix, int target) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Matrix: " + matrix.length + " x " + matrix[0].length);
        System.out.println("Target: " + target);
        System.out.println("=================================");
        
        long startTime, endTime;
        boolean result;
        
        // Single Binary Search
        startTime = System.nanoTime();
        result = searchMatrix(matrix, target);
        endTime = System.nanoTime();
        System.out.printf("Single Binary:    %8d ns, Result: %b%n", (endTime - startTime), result);
        
        // Two Binary Searches
        startTime = System.nanoTime();
        result = searchMatrixTwoSearches(matrix, target);
        endTime = System.nanoTime();
        System.out.printf("Two Searches:     %8d ns, Result: %b%n", (endTime - startTime), result);
        
        // Optimized Two Searches
        startTime = System.nanoTime();
        result = searchMatrixOptimizedTwoSearches(matrix, target);
        endTime = System.nanoTime();
        System.out.printf("Optimized Two:    %8d ns, Result: %b%n", (endTime - startTime), result);
        
        // Top-Right Search
        startTime = System.nanoTime();
        result = searchMatrixTopRight(matrix, target);
        endTime = System.nanoTime();
        System.out.printf("Top-Right:        %8d ns, Result: %b%n", (endTime - startTime), result);
        
        // Recursive
        startTime = System.nanoTime();
        result = searchMatrixRecursive(matrix, target);
        endTime = System.nanoTime();
        System.out.printf("Recursive:        %8d ns, Result: %b%n", (endTime - startTime), result);
        
        // Linear Search (only for small matrices)
        if (matrix.length * matrix[0].length <= 1000) {
            startTime = System.nanoTime();
            result = searchMatrixLinear(matrix, target);
            endTime = System.nanoTime();
            System.out.printf("Linear Search:    %8d ns, Result: %b%n", (endTime - startTime), result);
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Search a 2D Matrix:");
        System.out.println("============================");
        
        // Test case 1: Target exists
        System.out.println("\nTest 1: Target exists in matrix");
        int[][] matrix1 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target1 = 3;
        boolean result1 = solution.searchMatrix(matrix1, target1);
        System.out.println("Result: " + result1 + " - " + (result1 ? "PASSED" : "FAILED"));
        solution.visualizeBinarySearch(matrix1, target1);
        
        // Test case 2: Target doesn't exist
        System.out.println("\nTest 2: Target doesn't exist");
        int[][] matrix2 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target2 = 13;
        boolean result2 = solution.searchMatrix(matrix2, target2);
        System.out.println("Result: " + result2 + " - " + (!result2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single element matrix, target exists
        System.out.println("\nTest 3: Single element matrix, target exists");
        int[][] matrix3 = {{5}};
        int target3 = 5;
        boolean result3 = solution.searchMatrix(matrix3, target3);
        System.out.println("Result: " + result3 + " - " + (result3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single element matrix, target doesn't exist
        System.out.println("\nTest 4: Single element matrix, target doesn't exist");
        int[][] matrix4 = {{5}};
        int target4 = 3;
        boolean result4 = solution.searchMatrix(matrix4, target4);
        System.out.println("Result: " + result4 + " - " + (!result4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single row matrix
        System.out.println("\nTest 5: Single row matrix");
        int[][] matrix5 = {{1, 3, 5, 7, 9}};
        int target5 = 5;
        boolean result5 = solution.searchMatrix(matrix5, target5);
        System.out.println("Result: " + result5 + " - " + (result5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Single column matrix
        System.out.println("\nTest 6: Single column matrix");
        int[][] matrix6 = {{1}, {3}, {5}, {7}, {9}};
        int target6 = 5;
        boolean result6 = solution.searchMatrix(matrix6, target6);
        System.out.println("Result: " + result6 + " - " + (result6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Target at beginning
        System.out.println("\nTest 7: Target at beginning");
        int[][] matrix7 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target7 = 1;
        boolean result7 = solution.searchMatrix(matrix7, target7);
        System.out.println("Result: " + result7 + " - " + (result7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Target at end
        System.out.println("\nTest 8: Target at end");
        int[][] matrix8 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target8 = 60;
        boolean result8 = solution.searchMatrix(matrix8, target8);
        System.out.println("Result: " + result8 + " - " + (result8 ? "PASSED" : "FAILED"));
        
        // Test case 9: Large matrix
        System.out.println("\nTest 9: Large matrix");
        int[][] matrix9 = new int[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                matrix9[i][j] = i * 100 + j;
            }
        }
        int target9 = 5432;
        boolean result9 = solution.searchMatrix(matrix9, target9);
        System.out.println("Result: " + result9 + " - " + (result9 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 10: Performance Comparison - Small Matrix");
        solution.compareApproaches(matrix1, 16);
        
        System.out.println("\nTest 11: Performance Comparison - Large Matrix");
        solution.compareApproaches(matrix9, 5432);
        
        // Two-search visualization
        System.out.println("\nTest 12: Two-Search Visualization");
        solution.visualizeTwoSearches(matrix1, 16);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The matrix properties allow us to treat it as one sorted array:");
        System.out.println("1. Each row is sorted → elements increase left to right");
        System.out.println("2. First element of row > last element of previous row → elements increase top to bottom");
        System.out.println("This means if we flatten the matrix row by row, we get a completely sorted array.");
        
        System.out.println("\nCoordinate Mapping:");
        System.out.println("For a matrix with n columns:");
        System.out.println("  row = index / n");
        System.out.println("  col = index % n");
        System.out.println("Example: In a 3x4 matrix, index 7 maps to (1,3) because 7/4=1, 7%4=3");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Single Binary Search (RECOMMENDED):");
        System.out.println("   Time: O(log(m*n)) - Single search over all elements");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Treat matrix as 1D array [0, m*n-1]");
        System.out.println("     - Convert 1D index to 2D coordinates for access");
        System.out.println("     - Standard binary search with coordinate mapping");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Simple and elegant implementation");
        System.out.println("     - Single search is efficient");
        System.out.println("   Cons:");
        System.out.println("     - Requires coordinate mapping");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Two Binary Searches:");
        System.out.println("   Time: O(log m + log n) = O(log(m*n))");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - First search: Find correct row using first column");
        System.out.println("     - Second search: Search within the identified row");
        System.out.println("   Pros:");
        System.out.println("     - More intuitive for some people");
        System.out.println("     - Clear separation of concerns");
        System.out.println("   Cons:");
        System.out.println("     - Two searches instead of one");
        System.out.println("     - Slightly more code");
        System.out.println("   Best for: When clarity is prioritized over minimal code");
        
        System.out.println("\n3. Top-Right Corner Search:");
        System.out.println("   Time: O(m + n) - Worst case traverse row and column");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Start from top-right corner");
        System.out.println("     - If current > target, move left");
        System.out.println("     - If current < target, move down");
        System.out.println("   Pros:");
        System.out.println("     - No coordinate mapping needed");
        System.out.println("     - Works for different matrix properties");
        System.out.println("   Cons:");
        System.out.println("     - O(m+n) doesn't meet O(log(m*n)) requirement");
        System.out.println("     - Not optimal for this specific problem");
        System.out.println("   Best for: Matrix with different properties (like Problem 240)");
        
        System.out.println("\n4. Linear Search (NOT RECOMMENDED):");
        System.out.println("   Time: O(m*n) - Scan all elements");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Iterate through all rows and columns");
        System.out.println("     - Check each element against target");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(m*n) is too slow for large matrices");
        System.out.println("     - Doesn't leverage sorted properties");
        System.out.println("   Best for: Very small matrices, educational purposes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Total elements: m * n");
        System.out.println("2. Binary search iterations: ⌊log₂(m*n)⌋ + 1");
        System.out.println("3. For 100x100 matrix: 10,000 elements → 14 iterations max");
        System.out.println("4. Linear search would require up to 10,000 comparisons");
        System.out.println("5. Coordinate mapping formula: (i,j) = (index/n, index%n)");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Immediately identify the sorted matrix properties");
        System.out.println("2. Explain that it can be treated as one sorted array");
        System.out.println("3. Implement single binary search with coordinate mapping");
        System.out.println("4. Mention the two-search alternative and its trade-offs");
        System.out.println("5. Handle edge cases: empty matrix, single element");
        System.out.println("6. Discuss time/space complexity clearly");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
