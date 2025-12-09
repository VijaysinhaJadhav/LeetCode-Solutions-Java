
## Solution.java

```java
/**
 * 427. Construct Quad Tree
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a n * n matrix grid of 0's and 1's only. We want to represent the grid with a Quad-Tree.
 * Return the root of the Quad-Tree representing the grid.
 * 
 * Key Insights:
 * 1. Quad Tree: Each internal node has exactly 4 children (topLeft, topRight, bottomLeft, bottomRight)
 * 2. Leaf node: When all values in current subgrid are same (all 0s or all 1s)
 * 3. Internal node: When values differ, divide into 4 quadrants and recurse
 * 4. Divide and Conquer: Recursively split grid into smaller subgrids
 * 5. Grid size is always power of 2, making division clean
 * 
 * Approaches:
 * 1. Recursive Divide and Conquer - RECOMMENDED for interviews
 * 2. Iterative with Stack - Alternative iterative approach
 * 3. Optimized Recursive - With bounds checking and early termination
 * 
 * Time Complexity: O(n²) where n is grid dimension
 * Space Complexity: O(log n) for recursion stack
 * 
 * Tags: Array, Divide and Conquer, Tree, Matrix
 */

import java.util.*;

// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;
    
    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }
    
    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }
    
    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}

class Solution {
    
    /**
     * Approach 1: Recursive Divide and Conquer - RECOMMENDED for interviews
     * Time: O(n²), Space: O(log n)
     * Algorithm:
     * 1. Check if current subgrid has uniform values
     * 2. If uniform: create leaf node with the value
     * 3. If not uniform: create internal node and recursively build 4 children
     * 4. Divide grid into 4 quadrants: top-left, top-right, bottom-left, bottom-right
     */
    public Node construct(int[][] grid) {
        return buildQuadTree(grid, 0, 0, grid.length);
    }
    
    private Node buildQuadTree(int[][] grid, int row, int col, int size) {
        // Base case: check if all values in current subgrid are same
        if (isUniform(grid, row, col, size)) {
            return new Node(grid[row][col] == 1, true);
        }
        
        // Recursive case: divide into 4 quadrants
        int newSize = size / 2;
        
        Node topLeft = buildQuadTree(grid, row, col, newSize);
        Node topRight = buildQuadTree(grid, row, col + newSize, newSize);
        Node bottomLeft = buildQuadTree(grid, row + newSize, col, newSize);
        Node bottomRight = buildQuadTree(grid, row + newSize, col + newSize, newSize);
        
        // Create internal node (val can be true or false for internal nodes)
        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }
    
    /**
     * Check if all values in the subgrid are the same
     */
    private boolean isUniform(int[][] grid, int row, int col, int size) {
        int firstValue = grid[row][col];
        for (int i = row; i < row + size; i++) {
            for (int j = col; j < col + size; j++) {
                if (grid[i][j] != firstValue) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Approach 2: Optimized Recursive with Early Termination
     * Time: O(n²), Space: O(log n)
     * Optimizes by checking uniformity while building children
     */
    public Node constructOptimized(int[][] grid) {
        return buildQuadTreeOptimized(grid, 0, 0, grid.length);
    }
    
    private Node buildQuadTreeOptimized(int[][] grid, int row, int col, int size) {
        if (size == 1) {
            // Single cell is always a leaf
            return new Node(grid[row][col] == 1, true);
        }
        
        int newSize = size / 2;
        Node topLeft = buildQuadTreeOptimized(grid, row, col, newSize);
        Node topRight = buildQuadTreeOptimized(grid, row, col + newSize, newSize);
        Node bottomLeft = buildQuadTreeOptimized(grid, row + newSize, col, newSize);
        Node bottomRight = buildQuadTreeOptimized(grid, row + newSize, col + newSize, newSize);
        
        // Check if all children are leaves with same value
        if (topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf &&
            topLeft.val == topRight.val && topRight.val == bottomLeft.val && bottomLeft.val == bottomRight.val) {
            return new Node(topLeft.val, true);
        }
        
        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }
    
    /**
     * Approach 3: Iterative with Stack
     * Time: O(n²), Space: O(n) for stack
     * Uses stack to simulate recursion
     */
    public Node constructIterative(int[][] grid) {
        if (grid.length == 0) return null;
        
        Stack<QuadTreeBuilder> stack = new Stack<>();
        Node root = new Node();
        stack.push(new QuadTreeBuilder(grid, 0, 0, grid.length, root));
        
        while (!stack.isEmpty()) {
            QuadTreeBuilder current = stack.pop();
            int[][] currentGrid = current.grid;
            int row = current.row;
            int col = current.col;
            int size = current.size;
            Node currentNode = current.node;
            
            if (isUniform(currentGrid, row, col, size)) {
                currentNode.val = currentGrid[row][col] == 1;
                currentNode.isLeaf = true;
            } else {
                currentNode.val = false; // arbitrary value for internal nodes
                currentNode.isLeaf = false;
                
                int newSize = size / 2;
                
                // Create children and push to stack
                currentNode.topLeft = new Node();
                currentNode.topRight = new Node();
                currentNode.bottomLeft = new Node();
                currentNode.bottomRight = new Node();
                
                stack.push(new QuadTreeBuilder(grid, row, col, newSize, currentNode.topLeft));
                stack.push(new QuadTreeBuilder(grid, row, col + newSize, newSize, currentNode.topRight));
                stack.push(new QuadTreeBuilder(grid, row + newSize, col, newSize, currentNode.bottomLeft));
                stack.push(new QuadTreeBuilder(grid, row + newSize, col + newSize, newSize, currentNode.bottomRight));
            }
        }
        
        return root;
    }
    
    /**
     * Helper class for iterative approach
     */
    private class QuadTreeBuilder {
        int[][] grid;
        int row;
        int col;
        int size;
        Node node;
        
        QuadTreeBuilder(int[][] grid, int row, int col, int size, Node node) {
            this.grid = grid;
            this.row = row;
            this.col = col;
            this.size = size;
            this.node = node;
        }
    }
    
    /**
     * Approach 4: Recursive with Subgrid Copy
     * Time: O(n²), Space: O(n²) - less efficient but educational
     * Creates subgrid copies for each recursive call
     */
    public Node constructWithSubgrid(int[][] grid) {
        if (grid.length == 0) return null;
        return buildQuadTreeWithSubgrid(grid);
    }
    
    private Node buildQuadTreeWithSubgrid(int[][] grid) {
        int n = grid.length;
        
        // Check if all values are same
        boolean allSame = true;
        int firstVal = grid[0][0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != firstVal) {
                    allSame = false;
                    break;
                }
            }
            if (!allSame) break;
        }
        
        if (allSame) {
            return new Node(firstVal == 1, true);
        }
        
        // Divide into 4 subgrids
        int newSize = n / 2;
        
        int[][] topLeftGrid = createSubgrid(grid, 0, 0, newSize);
        int[][] topRightGrid = createSubgrid(grid, 0, newSize, newSize);
        int[][] bottomLeftGrid = createSubgrid(grid, newSize, 0, newSize);
        int[][] bottomRightGrid = createSubgrid(grid, newSize, newSize, newSize);
        
        Node topLeft = buildQuadTreeWithSubgrid(topLeftGrid);
        Node topRight = buildQuadTreeWithSubgrid(topRightGrid);
        Node bottomLeft = buildQuadTreeWithSubgrid(bottomLeftGrid);
        Node bottomRight = buildQuadTreeWithSubgrid(bottomRightGrid);
        
        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }
    
    private int[][] createSubgrid(int[][] grid, int startRow, int startCol, int size) {
        int[][] subgrid = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                subgrid[i][j] = grid[startRow + i][startCol + j];
            }
        }
        return subgrid;
    }
    
    /**
     * Helper method to print Quad Tree in level order
     */
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                Node node = queue.poll();
                if (node == null) {
                    level.add(null);
                    continue;
                }
                
                // Format: [isLeaf, val] where 1=true, 0=false
                level.add(node.isLeaf ? 1 : 0);
                level.add(node.val ? 1 : 0);
                
                if (!node.isLeaf) {
                    queue.offer(node.topLeft);
                    queue.offer(node.topRight);
                    queue.offer(node.bottomLeft);
                    queue.offer(node.bottomRight);
                }
            }
            
            result.add(level);
        }
        
        return result;
    }
    
    /**
     * Visualize Quad Tree construction process
     */
    public void visualizeConstruction(int[][] grid, String approach) {
        System.out.println("\n" + approach + " Quad Tree Construction:");
        System.out.println("Input Grid (" + grid.length + "x" + grid.length + "):");
        printGrid(grid);
        
        Node root;
        switch (approach) {
            case "Recursive":
                root = construct(grid);
                break;
            case "Optimized":
                root = constructOptimized(grid);
                break;
            case "Iterative":
                root = constructIterative(grid);
                break;
            default:
                root = construct(grid);
        }
        
        System.out.println("Quad Tree Level Order:");
        List<List<Integer>> levelOrder = levelOrder(root);
        for (int i = 0; i < levelOrder.size(); i++) {
            System.out.println("Level " + i + ": " + levelOrder.get(i));
        }
        
        System.out.println("\nTree Structure:");
        printQuadTree(root, 0);
        
        if (approach.equals("Recursive")) {
            System.out.println("\nStep-by-Step Construction Process:");
            visualizeRecursiveProcess(grid, 0, 0, grid.length, "root");
        }
    }
    
    private void printGrid(int[][] grid) {
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
    }
    
    private void printQuadTree(Node node, int depth) {
        if (node == null) {
            printIndent(depth);
            System.out.println("null");
            return;
        }
        
        printIndent(depth);
        System.out.println("Node[isLeaf=" + node.isLeaf + ", val=" + node.val + "]");
        
        if (!node.isLeaf) {
            printQuadTree(node.topLeft, depth + 1);
            printQuadTree(node.topRight, depth + 1);
            printQuadTree(node.bottomLeft, depth + 1);
            printQuadTree(node.bottomRight, depth + 1);
        }
    }
    
    private void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
    }
    
    /**
     * Visualize recursive construction process
     */
    private Node visualizeRecursiveProcess(int[][] grid, int row, int col, int size, String position) {
        System.out.println("Processing " + position + ": grid[" + row + ":" + (row+size) + 
                          "][" + col + ":" + (col+size) + "]");
        
        if (isUniform(grid, row, col, size)) {
            System.out.println("  Uniform region: all " + grid[row][col] + " → Creating LEAF node");
            return new Node(grid[row][col] == 1, true);
        }
        
        System.out.println("  Mixed region → Creating INTERNAL node and dividing into 4 quadrants");
        int newSize = size / 2;
        
        Node topLeft = visualizeRecursiveProcess(grid, row, col, newSize, position + ".topLeft");
        Node topRight = visualizeRecursiveProcess(grid, row, col + newSize, newSize, position + ".topRight");
        Node bottomLeft = visualizeRecursiveProcess(grid, row + newSize, col, newSize, position + ".bottomLeft");
        Node bottomRight = visualizeRecursiveProcess(grid, row + newSize, col + newSize, newSize, position + ".bottomRight");
        
        System.out.println("  Completed " + position + " with 4 children");
        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }
    
    /**
     * Analyze Quad Tree properties
     */
    public void analyzeQuadTree(Node root, int[][] grid) {
        System.out.println("\nQuad Tree Analysis:");
        
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        int totalNodes = countNodes(root);
        int leafNodes = countLeafNodes(root);
        int internalNodes = totalNodes - leafNodes;
        int maxDepth = getMaxDepth(root);
        int gridSize = grid.length;
        
        System.out.println("Grid size: " + gridSize + "x" + gridSize);
        System.out.println("Total nodes: " + totalNodes);
        System.out.println("Leaf nodes: " + leafNodes);
        System.out.println("Internal nodes: " + internalNodes);
        System.out.println("Max depth: " + maxDepth);
        System.out.println("Compression ratio: " + String.format("%.2f", (double)leafNodes/(gridSize*gridSize)));
        
        // Count nodes by value
        int trueLeaves = countNodesByValue(root, true);
        int falseLeaves = countNodesByValue(root, false);
        System.out.println("True leaves: " + trueLeaves);
        System.out.println("False leaves: " + falseLeaves);
    }
    
    private int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.topLeft) + countNodes(node.topRight) + 
               countNodes(node.bottomLeft) + countNodes(node.bottomRight);
    }
    
    private int countLeafNodes(Node node) {
        if (node == null) return 0;
        if (node.isLeaf) return 1;
        return countLeafNodes(node.topLeft) + countLeafNodes(node.topRight) + 
               countLeafNodes(node.bottomLeft) + countLeafNodes(node.bottomRight);
    }
    
    private int getMaxDepth(Node node) {
        if (node == null || node.isLeaf) return 0;
        return 1 + Math.max(Math.max(getMaxDepth(node.topLeft), getMaxDepth(node.topRight)),
                           Math.max(getMaxDepth(node.bottomLeft), getMaxDepth(node.bottomRight)));
    }
    
    private int countNodesByValue(Node node, boolean value) {
        if (node == null) return 0;
        if (node.isLeaf && node.val == value) return 1;
        if (node.isLeaf) return 0;
        return countNodesByValue(node.topLeft, value) + countNodesByValue(node.topRight, value) + 
               countNodesByValue(node.bottomLeft, value) + countNodesByValue(node.bottomRight, value);
    }
    
    /**
     * Compare different construction approaches
     */
    public void compareApproaches(int[][] grid) {
        System.out.println("\nApproach Comparison:");
        
        long startTime = System.nanoTime();
        Node result1 = construct(grid);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        Node result2 = constructOptimized(grid);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        Node result3 = constructIterative(grid);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Recursive:      " + time1 + " ns");
        System.out.println("Optimized:      " + time2 + " ns");
        System.out.println("Iterative:      " + time3 + " ns");
        
        // Verify all produce same level order
        List<List<Integer>> level1 = levelOrder(result1);
        List<List<Integer>> level2 = levelOrder(result2);
        List<List<Integer>> level3 = levelOrder(result3);
        
        System.out.println("All approaches produce same result: " + 
                         (level1.equals(level2) && level2.equals(level3)));
    }
    
    /**
     * Test various grid patterns
     */
    public void testVariousGrids() {
        System.out.println("\nVarious Grid Pattern Tests:");
        
        // Test 1: All zeros
        System.out.println("\n1. All Zeros (4x4):");
        int[][] zeros = {
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0}
        };
        visualizeConstruction(zeros, "Recursive");
        
        // Test 2: All ones
        System.out.println("\n2. All Ones (4x4):");
        int[][] ones = {
            {1,1,1,1},
            {1,1,1,1},
            {1,1,1,1},
            {1,1,1,1}
        };
        visualizeConstruction(ones, "Recursive");
        
        // Test 3: Checkerboard pattern
        System.out.println("\n3. Checkerboard Pattern (4x4):");
        int[][] checkerboard = {
            {0,1,0,1},
            {1,0,1,0},
            {0,1,0,1},
            {1,0,1,0}
        };
        visualizeConstruction(checkerboard, "Recursive");
        
        // Test 4: Mixed pattern (from example)
        System.out.println("\n4. Mixed Pattern (2x2):");
        int[][] mixed = {
            {0,1},
            {1,0}
        };
        visualizeConstruction(mixed, "Recursive");
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Quad Tree Construction");
        System.out.println("==============================\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test 1: Example grid [[0,1],[1,0]]");
        int[][] grid1 = {
            {0,1},
            {1,0}
        };
        
        Node result1a = solution.construct(grid1);
        Node result1b = solution.constructOptimized(grid1);
        
        List<List<Integer>> level1a = solution.levelOrder(result1a);
        List<List<Integer>> level1b = solution.levelOrder(result1b);
        
        System.out.println("Recursive:  " + level1a);
        System.out.println("Optimized:  " + level1b);
        
        solution.visualizeConstruction(grid1, "Recursive");
        solution.analyzeQuadTree(result1a, grid1);
        
        // Test Case 2: Larger uniform grid
        System.out.println("\nTest 2: Uniform 4x4 grid of ones");
        int[][] grid2 = {
            {1,1,1,1},
            {1,1,1,1},
            {1,1,1,1},
            {1,1,1,1}
        };
        Node result2 = solution.construct(grid2);
        System.out.println("Uniform grid result: " + solution.levelOrder(result2));
        
        // Test Case 3: Complex pattern
        System.out.println("\nTest 3: Complex 4x4 pattern");
        int[][] grid3 = {
            {1,1,0,0},
            {1,1,0,0},
            {0,0,1,1},
            {0,0,1,1}
        };
        solution.visualizeConstruction(grid3, "Optimized");
        
        // Test various grid patterns
        solution.testVariousGrids();
        
        // Compare approaches
        System.out.println("\nTest 4: Approach Comparison");
        solution.compareApproaches(grid3);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("QUAD TREE CONSTRUCTION ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        
        explainAlgorithms();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(70));
        
        explainInterviewStrategy();
    }
    
    /**
     * Compare performance of different approaches
     */
    private static void comparePerformance(Solution solution) {
        System.out.println("Creating large grids for performance testing...");
        
        // Create large uniform grid
        int[][] largeUniform = createUniformGrid(256, 1);
        // Create large mixed grid
        int[][] largeMixed = createMixedGrid(256);
        
        System.out.println("\nLarge Uniform Grid Performance (256x256):");
        
        long startTime = System.nanoTime();
        solution.construct(largeUniform);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.constructOptimized(largeUniform);
        long time2 = System.nanoTime() - startTime;
        
        System.out.println("Recursive: " + time1 + " ns");
        System.out.println("Optimized: " + time2 + " ns");
        
        System.out.println("\nLarge Mixed Grid Performance (256x256):");
        
        startTime = System.nanoTime();
        solution.construct(largeMixed);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.constructOptimized(largeMixed);
        long time4 = System.nanoTime() - startTime;
        
        System.out.println("Recursive: " + time3 + " ns");
        System.out.println("Optimized: " + time4 + " ns");
    }
    
    private static int[][] createUniformGrid(int size, int value) {
        int[][] grid = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], value);
        }
        return grid;
    }
    
    private static int[][] createMixedGrid(int size) {
        int[][] grid = new int[size][size];
        Random rand = new Random(42); // Fixed seed for reproducibility
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = rand.nextInt(2);
            }
        }
        return grid;
    }
    
    /**
     * Detailed algorithm explanations
     */
    private static void explainAlgorithms() {
        System.out.println("\n1. RECURSIVE DIVIDE AND CONQUER (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     function buildQuadTree(grid, row, col, size):");
        System.out.println("       if all values in grid[row:row+size][col:col+size] are same:");
        System.out.println("         return new LeafNode(value)");
        System.out.println("       else:");
        System.out.println("         newSize = size / 2");
        System.out.println("         topLeft = buildQuadTree(grid, row, col, newSize)");
        System.out.println("         topRight = buildQuadTree(grid, row, col+newSize, newSize)");
        System.out.println("         bottomLeft = buildQuadTree(grid, row+newSize, col, newSize)");
        System.out.println("         bottomRight = buildQuadTree(grid, row+newSize, col+newSize, newSize)");
        System.out.println("         return new InternalNode(topLeft, topRight, bottomLeft, bottomRight)");
        System.out.println("   Key Features:");
        System.out.println("     - Natural divide and conquer approach");
        System.out.println("     - O(n²) time complexity (each cell visited once)");
        System.out.println("     - O(log n) space complexity (recursion depth)");
        
        System.out.println("\n2. QUAD TREE PROPERTIES:");
        System.out.println("   - Each node has 0 or 4 children");
        System.out.println("   - Leaf nodes represent uniform regions");
        System.out.println("   - Internal nodes represent mixed regions");
        System.out.println("   - Efficient for sparse data compression");
        System.out.println("   - Used in image processing, spatial indexing");
        
        System.out.println("\n3. OPTIMIZATION TECHNIQUES:");
        System.out.println("   - Check child uniformity when building internal nodes");
        System.out.println("   - Use bounds instead of copying subgrids");
        System.out.println("   - Early termination for single cells");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR QUAD TREE CONSTRUCTION:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with recursive divide and conquer");
        System.out.println("   - Mention bounds optimization (avoid subgrid copies)");
        System.out.println("   - Discuss time/space complexity");
        System.out.println("   - Recommended: Recursive with bounds");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Quad Tree structure: 0 or 4 children per node");
        System.out.println("   - Leaf nodes = uniform regions");
        System.out.println("   - Internal nodes = mixed regions");
        System.out.println("   - Divide grid into 4 equal quadrants");
        System.out.println("   - Base case: uniform region or single cell");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Creating subgrid copies (inefficient)");
        System.out.println("   - Forgetting to check all cells for uniformity");
        System.out.println("   - Incorrect quadrant boundaries");
        System.out.println("   - Not handling the single cell case");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - How to serialize/deserialize Quad Tree?");
        System.out.println("   - How to compute intersection of two Quad Trees?");
        System.out.println("   - How to use Quad Tree for image compression?");
        System.out.println("   - What about 3D (Oct-Tree) generalization?");
        System.out.println("   - How to handle non-power-of-2 grid sizes?");
        
        System.out.println("\n5. Related Problems:");
        System.out.println("   - 558. Logical OR of Two Binary Grids Represented as Quad-Trees");
        System.out.println("   - 558. Quad Tree Intersection");
        System.out.println("   - 429. N-ary Tree Level Order Traversal");
        System.out.println("   - 226. Invert Binary Tree");
        
        System.out.println("\n6. Code Pattern to Remember:");
        System.out.println("   public Node construct(int[][] grid) {");
        System.out.println("     return build(grid, 0, 0, grid.length);");
        System.out.println("   }");
        System.out.println("   private Node build(int[][] grid, int r, int c, int s) {");
        System.out.println("     if (allSame(grid, r, c, s)) ");
        System.out.println("       return new Node(grid[r][c]==1, true);");
        System.out.println("     int ns = s/2;");
        System.out.println("     return new Node(false, false,");
        System.out.println("       build(grid, r, c, ns),");
        System.out.println("       build(grid, r, c+ns, ns),");
        System.out.println("       build(grid, r+ns, c, ns),");
        System.out.println("       build(grid, r+ns, c+ns, ns));");
        System.out.println("   }");
        
        System.out.println("\n7. Real-world Applications:");
        System.out.println("   - Image compression (JPEG)");
        System.out.println("   - Spatial indexing databases");
        System.out.println("   - Game development (collision detection)");
        System.out.println("   - Computer graphics (ray tracing)");
        System.out.println("   - Geographic Information Systems (GIS)");
    }
}
