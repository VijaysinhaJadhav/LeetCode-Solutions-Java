/**
 * 36. Valid Sudoku
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Determine if a 9x9 Sudoku board is valid according to Sudoku rules:
 * 1. Each row must contain digits 1-9 without repetition
 * 2. Each column must contain digits 1-9 without repetition  
 * 3. Each 3x3 sub-box must contain digits 1-9 without repetition
 * Only filled cells need to be validated.
 * 
 * Key Insights:
 * 1. Use separate validation arrays for rows, columns, and boxes
 * 2. Box index = (row / 3) * 3 + (col / 3)
 * 3. Only process filled cells (skip '.')
 * 4. Can check all three rules in a single pass
 * 
 * Approach (Array Validation):
 * 1. Create 9x9 boolean arrays for rows, columns, and boxes
 * 2. Iterate through each cell in the board
 * 3. For filled cells, check if digit already exists in row/col/box
 * 4. If duplicate found, return false; otherwise mark as seen
 * 5. If all checks pass, return true
 * 
 * Time Complexity: O(1) - Fixed 9x9 board
 * Space Complexity: O(1) - Fixed size arrays
 * 
 * Tags: Array, Hash Table, Matrix
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Array Validation (RECOMMENDED)
     * Uses boolean arrays for efficient validation
     */
    public boolean isValidSudoku(char[][] board) {
        // 9 rows, 9 columns, 9 boxes, each with 9 possible digits (1-9)
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                
                // Skip empty cells
                if (c == '.') {
                    continue;
                }
                
                // Convert char to digit index (0-8)
                int digit = c - '1'; // '1' -> 0, '2' -> 1, ..., '9' -> 8
                
                // Calculate box index: (row / 3) * 3 + (col / 3)
                int boxIndex = (i / 3) * 3 + (j / 3);
                
                // Check if digit already exists in current row, column, or box
                if (rows[i][digit] || cols[j][digit] || boxes[boxIndex][digit]) {
                    return false;
                }
                
                // Mark digit as seen in current row, column, and box
                rows[i][digit] = true;
                cols[j][digit] = true;
                boxes[boxIndex][digit] = true;
            }
        }
        
        return true;
    }
    
    /**
     * Approach 2: HashSet Validation
     * More intuitive but slightly less efficient
     */
    public boolean isValidSudokuHashSet(char[][] board) {
        Set<String> seen = new HashSet<>();
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                
                if (c == '.') {
                    continue;
                }
                
                // Create unique identifiers for each constraint
                String rowKey = "row-" + i + "-" + c;
                String colKey = "col-" + j + "-" + c;
                String boxKey = "box-" + (i / 3) + "-" + (j / 3) + "-" + c;
                
                // If any identifier already exists, board is invalid
                if (!seen.add(rowKey) || !seen.add(colKey) || !seen.add(boxKey)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Approach 3: Bit Masking (Space Optimized)
     * Uses integers as bit masks for validation
     */
    public boolean isValidSudokuBitMask(char[][] board) {
        int[] rows = new int[9];
        int[] cols = new int[9];
        int[] boxes = new int[9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                
                if (c == '.') {
                    continue;
                }
                
                int digit = c - '1'; // 0-8
                int mask = 1 << digit; // Set the bit for this digit
                
                int boxIndex = (i / 3) * 3 + (j / 3);
                
                // Check if bit is already set in any of the masks
                if ((rows[i] & mask) != 0 || 
                    (cols[j] & mask) != 0 || 
                    (boxes[boxIndex] & mask) != 0) {
                    return false;
                }
                
                // Set the bit in all three masks
                rows[i] |= mask;
                cols[j] |= mask;
                boxes[boxIndex] |= mask;
            }
        }
        
        return true;
    }
    
    /**
     * Approach 4: Separate Validation Passes
     * Validates rows, then columns, then boxes separately
     * Less efficient but more modular
     */
    public boolean isValidSudokuSeparate(char[][] board) {
        // Validate rows
        for (int i = 0; i < 9; i++) {
            boolean[] seen = new boolean[9];
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') continue;
                int digit = c - '1';
                if (seen[digit]) return false;
                seen[digit] = true;
            }
        }
        
        // Validate columns
        for (int j = 0; j < 9; j++) {
            boolean[] seen = new boolean[9];
            for (int i = 0; i < 9; i++) {
                char c = board[i][j];
                if (c == '.') continue;
                int digit = c - '1';
                if (seen[digit]) return false;
                seen[digit] = true;
            }
        }
        
        // Validate 3x3 boxes
        for (int box = 0; box < 9; box++) {
            boolean[] seen = new boolean[9];
            int startRow = (box / 3) * 3;
            int startCol = (box % 3) * 3;
            for (int i = startRow; i < startRow + 3; i++) {
                for (int j = startCol; j < startCol + 3; j++) {
                    char c = board[i][j];
                    if (c == '.') continue;
                    int digit = c - '1';
                    if (seen[digit]) return false;
                    seen[digit] = true;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Helper method to print the Sudoku board
     */
    private void printBoard(char[][] board, String title) {
        System.out.println(title);
        System.out.println("┌─────────┬─────────┬─────────┐");
        for (int i = 0; i < 9; i++) {
            if (i > 0 && i % 3 == 0) {
                System.out.println("├─────────┼─────────┼─────────┤");
            }
            System.out.print("│ ");
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] == '.' ? "·" : board[i][j]);
                System.out.print(" ");
                if ((j + 1) % 3 == 0) {
                    System.out.print("│ ");
                }
            }
            System.out.println();
        }
        System.out.println("└─────────┴─────────┴─────────┘");
    }
    
    /**
     * Helper method to visualize validation process
     */
    private void visualizeValidation(char[][] board, String approach) {
        System.out.println("\n" + approach + " Validation:");
        System.out.println("Checking: Rows → Columns → 3x3 Boxes");
        
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];
        boolean isValid = true;
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') continue;
                
                int digit = c - '1';
                int boxIndex = (i / 3) * 3 + (j / 3);
                
                System.out.printf("Cell [%d,%d] = '%c': ", i, j, c);
                
                boolean rowConflict = rows[i][digit];
                boolean colConflict = cols[j][digit];
                boolean boxConflict = boxes[boxIndex][digit];
                
                if (rowConflict || colConflict || boxConflict) {
                    System.out.print("CONFLICT! - ");
                    if (rowConflict) System.out.print("Row ");
                    if (colConflict) System.out.print("Column ");
                    if (boxConflict) System.out.print("Box ");
                    System.out.println();
                    isValid = false;
                    break;
                } else {
                    rows[i][digit] = true;
                    cols[j][digit] = true;
                    boxes[boxIndex][digit] = true;
                    System.out.println("OK");
                }
            }
            if (!isValid) break;
        }
        
        System.out.println("Result: " + (isValid ? "VALID" : "INVALID"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Valid Sudoku Solution:");
        System.out.println("===============================");
        
        // Test case 1: Valid board (from example)
        System.out.println("\nTest 1: Valid Sudoku Board");
        char[][] board1 = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        
        solution.printBoard(board1, "Valid Sudoku Board:");
        
        long startTime = System.nanoTime();
        boolean result1a = solution.isValidSudoku(board1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.isValidSudokuHashSet(board1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1c = solution.isValidSudokuBitMask(board1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1d = solution.isValidSudokuSeparate(board1);
        long time1d = System.nanoTime() - startTime;
        
        System.out.println("Array Approach: " + (result1a ? "VALID" : "INVALID"));
        System.out.println("HashSet Approach: " + (result1b ? "VALID" : "INVALID"));
        System.out.println("Bit Mask Approach: " + (result1c ? "VALID" : "INVALID"));
        System.out.println("Separate Passes: " + (result1d ? "VALID" : "INVALID"));
        
        // Test case 2: Invalid board (duplicate in top-left 3x3 box)
        System.out.println("\nTest 2: Invalid Sudoku Board");
        char[][] board2 = {
            {'8','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        
        solution.printBoard(board2, "Invalid Sudoku Board (duplicate '8' in top-left box):");
        
        boolean result2a = solution.isValidSudoku(board2);
        System.out.println("Array Approach: " + (result2a ? "VALID" : "INVALID"));
        
        // Visualize the validation for the invalid board
        solution.visualizeValidation(board2, "Array Validation");
        
        // Test case 3: Invalid row
        System.out.println("\nTest 3: Invalid Row");
        char[][] board3 = {
            {'5','3','5','.','7','.','.','.','.'}, // Duplicate 5 in first row
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        
        boolean result3a = solution.isValidSudoku(board3);
        System.out.println("Invalid Row: " + (result3a ? "VALID" : "INVALID"));
        
        // Test case 4: Invalid column
        System.out.println("\nTest 4: Invalid Column");
        char[][] board4 = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'5','9','8','.','.','.','.','6','.'}, // Duplicate 5 in first column
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        
        boolean result4a = solution.isValidSudoku(board4);
        System.out.println("Invalid Column: " + (result4a ? "VALID" : "INVALID"));
        
        // Test case 5: Empty board (valid)
        System.out.println("\nTest 5: Empty Board");
        char[][] board5 = {
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'}
        };
        
        boolean result5a = solution.isValidSudoku(board5);
        System.out.println("Empty Board: " + (result5a ? "VALID" : "INVALID"));
        
        // Test case 6: Single element board
        System.out.println("\nTest 6: Single Element Board");
        char[][] board6 = {
            {'1','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.'}
        };
        
        boolean result6a = solution.isValidSudoku(board6);
        System.out.println("Single Element: " + (result6a ? "VALID" : "INVALID"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("Valid board performance:");
        System.out.println("  Array Approach: " + time1a + " ns");
        System.out.println("  HashSet Approach: " + time1b + " ns");
        System.out.println("  Bit Mask Approach: " + time1c + " ns");
        System.out.println("  Separate Passes: " + time1d + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SUDOKU VALIDATION ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Concepts:");
        System.out.println("1. Three Validation Rules:");
        System.out.println("   - Rows: Each row must have unique digits 1-9");
        System.out.println("   - Columns: Each column must have unique digits 1-9");
        System.out.println("   - Boxes: Each 3x3 sub-box must have unique digits 1-9");
        
        System.out.println("\n2. Box Index Calculation:");
        System.out.println("   For cell at (row, col):");
        System.out.println("   boxRow = row / 3  (0, 1, or 2)");
        System.out.println("   boxCol = col / 3  (0, 1, or 2)");
        System.out.println("   boxIndex = boxRow * 3 + boxCol");
        System.out.println("   Example: Cell (4, 5) → boxRow=1, boxCol=1 → boxIndex=4");
        
        System.out.println("\n3. Box Layout:");
        System.out.println("   Box Indices:");
        System.out.println("   0 1 2");
        System.out.println("   3 4 5");
        System.out.println("   6 7 8");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Array Validation (RECOMMENDED):");
        System.out.println("   Data Structure: Three 9x9 boolean arrays");
        System.out.println("   Time: O(1) - Fixed 81 cells");
        System.out.println("   Space: O(1) - Fixed 3*9*9 = 243 booleans");
        System.out.println("   How it works:");
        System.out.println("     - rows[i][d]: digit d seen in row i");
        System.out.println("     - cols[j][d]: digit d seen in column j");
        System.out.println("     - boxes[k][d]: digit d seen in box k");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient for fixed-size problem");
        System.out.println("     - Simple and straightforward");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Fixed to 9x9 board size");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. HashSet Validation:");
        System.out.println("   Data Structure: Single HashSet<String>");
        System.out.println("   Time: O(1) - Fixed 81 cells");
        System.out.println("   Space: O(1) - Up to 243 strings");
        System.out.println("   How it works:");
        System.out.println("     - Store unique identifiers: \"row-i-digit\", \"col-j-digit\", \"box-k-digit\"");
        System.out.println("     - If add fails, duplicate found");
        System.out.println("   Pros:");
        System.out.println("     - Very intuitive and readable");
        System.out.println("     - Easy to extend to variable sizes");
        System.out.println("   Cons:");
        System.out.println("     - String operations have overhead");
        System.out.println("     - More memory than arrays");
        System.out.println("   Best for: Learning, when readability is priority");
        
        System.out.println("\n3. Bit Masking:");
        System.out.println("   Data Structure: Three integer arrays (bit masks)");
        System.out.println("   Time: O(1) - Fixed 81 cells");
        System.out.println("   Space: O(1) - 3*9 integers = 108 bytes");
        System.out.println("   How it works:");
        System.out.println("     - Each integer represents seen digits as bits");
        System.out.println("     - Bit d set = digit d+1 seen");
        System.out.println("     - Use bitwise OR to mark, AND to check");
        System.out.println("   Pros:");
        System.out.println("     - Most space efficient");
        System.out.println("     - Very fast bitwise operations");
        System.out.println("   Cons:");
        System.out.println("     - Less intuitive for beginners");
        System.out.println("     - Limited to 32 unique values (fits 1-9)");
        System.out.println("   Best for: Space-constrained environments, bit manipulation practice");
        
        System.out.println("\n4. Separate Validation Passes:");
        System.out.println("   Data Structure: Temporary boolean arrays");
        System.out.println("   Time: O(1) - Three passes over 81 cells");
        System.out.println("   Space: O(1) - Temporary arrays");
        System.out.println("   How it works:");
        System.out.println("     - First pass: validate all rows");
        System.out.println("     - Second pass: validate all columns");
        System.out.println("     - Third pass: validate all boxes");
        System.out.println("   Pros:");
        System.out.println("     - Very modular and clear separation");
        System.out.println("     - Easy to debug and test individually");
        System.out.println("   Cons:");
        System.out.println("     - Three passes instead of one");
        System.out.println("     - Less efficient");
        System.out.println("   Best for: Educational purposes, when modularity is key");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Array Approach - it's the most expected solution");
        System.out.println("2. Explain the three validation rules clearly");
        System.out.println("3. Demonstrate box index calculation with examples");
        System.out.println("4. Mention alternative approaches and their trade-offs");
        System.out.println("5. Handle edge cases: empty board, single element, early termination");
        System.out.println("6. Practice drawing the 9x9 grid and box boundaries");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
