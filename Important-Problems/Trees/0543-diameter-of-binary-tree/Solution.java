
## Solution.java

```java
/**
 * 543. Diameter of Binary Tree
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given the root of a binary tree, return the length of the diameter of the tree.
 * The diameter is the length of the longest path between any two nodes in a tree.
 * This path may or may not pass through the root.
 * The length of a path between two nodes is represented by the number of edges between them.
 * 
 * Key Insights:
 * 1. Diameter through a node = left subtree height + right subtree height
 * 2. Global maximum diameter needs to be tracked
 * 3. Use DFS post-order traversal to calculate heights
 * 4. For each node, update diameter = max(diameter, leftHeight + rightHeight)
 * 5. Return height of current node = 1 + max(leftHeight, rightHeight)
 * 
 * Approaches:
 * 1. DFS with Global Variable - RECOMMENDED
 * 2. DFS with Return Object - Alternative without global variable
 * 3. Iterative DFS - Stack-based approach
 * 
 * Time Complexity: O(n) for all approaches
 * Space Complexity: O(h) where h is tree height
 * 
 * Tags: Tree, Depth-First Search, Binary Tree
 */

import java.util.*;

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    
    // Global variable to track maximum diameter
    private int diameter;
    
    /**
     * Approach 1: DFS with Global Variable - RECOMMENDED for interviews
     * Time: O(n), Space: O(h) where h is tree height
     * Algorithm:
     * 1. Use DFS to calculate height of each node
     * 2. For each node, diameter through it = leftHeight + rightHeight
     * 3. Update global diameter if current diameter is larger
     * 4. Return height of current node = 1 + max(leftHeight, rightHeight)
     */
    public int diameterOfBinaryTree(TreeNode root) {
        diameter = 0;
        calculateHeight(root);
        return diameter;
    }
    
    private int calculateHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        // Recursively calculate heights of left and right subtrees
        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);
        
        // Update diameter: path through current node
        diameter = Math.max(diameter, leftHeight + rightHeight);
        
        // Return height of current node
        return 1 + Math.max(leftHeight, rightHeight);
    }
    
    /**
     * Approach 2: DFS with Return Object - No global variable
     * Time: O(n), Space: O(h)
     * Uses custom return type to track both height and diameter
     */
    public int diameterOfBinaryTreeNoGlobal(TreeNode root) {
        TreeInfo result = calculateTreeInfo(root);
        return result.diameter;
    }
    
    private TreeInfo calculateTreeInfo(TreeNode node) {
        if (node == null) {
            return new TreeInfo(0, 0);
        }
        
        TreeInfo leftInfo = calculateTreeInfo(node.left);
        TreeInfo rightInfo = calculateTreeInfo(node.right);
        
        // Current node's height
        int height = 1 + Math.max(leftInfo.height, rightInfo.height);
        
        // Diameter through current node
        int throughCurrent = leftInfo.height + rightInfo.height;
        
        // Maximum diameter so far
        int diameter = Math.max(throughCurrent, 
                               Math.max(leftInfo.diameter, rightInfo.diameter));
        
        return new TreeInfo(height, diameter);
    }
    
    /**
     * Helper class for Approach 2
     */
    private class TreeInfo {
        int height;
        int diameter;
        
        TreeInfo(int height, int diameter) {
            this.height = height;
            this.diameter = diameter;
        }
    }
    
    /**
     * Approach 3: Iterative DFS using Stack
     * Time: O(n), Space: O(h)
     * Simulates recursion using stack
     */
    public int diameterOfBinaryTreeIterative(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Map<TreeNode, Integer> heights = new HashMap<>();
        Stack<TreeNode> stack = new Stack<>();
        int diameter = 0;
        
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            
            // If we've processed both children, process current node
            if ((node.left == null || heights.containsKey(node.left)) &&
                (node.right == null || heights.containsKey(node.right))) {
                
                stack.pop();
                
                int leftHeight = heights.getOrDefault(node.left, 0);
                int rightHeight = heights.getOrDefault(node.right, 0);
                
                // Update diameter
                diameter = Math.max(diameter, leftHeight + rightHeight);
                
                // Store height of current node
                heights.put(node, 1 + Math.max(leftHeight, rightHeight));
                
            } else {
                // Push unprocessed children
                if (node.left != null && !heights.containsKey(node.left)) {
                    stack.push(node.left);
                }
                if (node.right != null && !heights.containsKey(node.right)) {
                    stack.push(node.right);
                }
            }
        }
        
        return diameter;
    }
    
    /**
     * Approach 4: BFS-like approach (less efficient but educational)
     * Calculates diameter by finding longest path for each node
     * Time: O(n^2) in worst case, Space: O(h)
     * Not recommended for large trees
     */
    public int diameterOfBinaryTreeBFSStyle(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        // Diameter through root
        int throughRoot = maxDepth(root.left) + maxDepth(root.right);
        
        // Diameter in left subtree
        int leftDiameter = diameterOfBinaryTreeBFSStyle(root.left);
        
        // Diameter in right subtree
        int rightDiameter = diameterOfBinaryTreeBFSStyle(root.right);
        
        return Math.max(throughRoot, Math.max(leftDiameter, rightDiameter));
    }
    
    private int maxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(node.left), maxDepth(node.right));
    }
    
    /**
     * Approach 5: Enhanced DFS with Path Tracking
     * Also returns the actual longest path for visualization
     */
    public DiameterResult diameterOfBinaryTreeWithPath(TreeNode root) {
        return calculateDiameterWithPath(root);
    }
    
    private DiameterResult calculateDiameterWithPath(TreeNode node) {
        if (node == null) {
            return new DiameterResult(0, 0, new ArrayList<>());
        }
        
        DiameterResult left = calculateDiameterWithPath(node.left);
        DiameterResult right = calculateDiameterWithPath(node.right);
        
        // Current height
        int height = 1 + Math.max(left.height, right.height);
        
        // Diameter through current node
        int throughCurrent = left.height + right.height;
        
        // Determine which diameter is largest
        int maxDiameter = Math.max(throughCurrent, 
                                  Math.max(left.diameter, right.diameter));
        
        // Build the longest path
        List<Integer> longestPath = new ArrayList<>();
        if (maxDiameter == throughCurrent) {
            // Path goes through current node
            longestPath.addAll(left.longestPathFromLeaf);
            longestPath.add(node.val);
            Collections.reverse(right.longestPathFromLeaf);
            longestPath.addAll(right.longestPathFromLeaf);
        } else if (maxDiameter == left.diameter) {
            longestPath = left.longestPath;
        } else {
            longestPath = right.longestPath;
        }
        
        // Build path from leaf to current node (for height calculation)
        List<Integer> pathFromLeaf = new ArrayList<>();
        if (left.height >= right.height) {
            pathFromLeaf.addAll(left.longestPathFromLeaf);
        } else {
            pathFromLeaf.addAll(right.longestPathFromLeaf);
        }
        pathFromLeaf.add(node.val);
        
        return new DiameterResult(height, maxDiameter, longestPath, pathFromLeaf);
    }
    
    /**
     * Helper class for Approach 5
     */
    private class DiameterResult {
        int height;
        int diameter;
        List<Integer> longestPath;
        List<Integer> longestPathFromLeaf;
        
        DiameterResult(int height, int diameter, List<Integer> longestPath) {
            this.height = height;
            this.diameter = diameter;
            this.longestPath = longestPath;
            this.longestPathFromLeaf = new ArrayList<>(longestPath);
        }
        
        DiameterResult(int height, int diameter, List<Integer> longestPath, List<Integer> longestPathFromLeaf) {
            this.height = height;
            this.diameter = diameter;
            this.longestPath = longestPath;
            this.longestPathFromLeaf = longestPathFromLeaf;
        }
    }
    
    /**
     * Helper method to build a binary tree from array (Level-order)
     */
    public TreeNode buildTree(Integer[] nodes) {
        if (nodes == null || nodes.length == 0 || nodes[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(nodes[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < nodes.length) {
            TreeNode current = queue.poll();
            
            // Left child
            if (i < nodes.length && nodes[i] != null) {
                current.left = new TreeNode(nodes[i]);
                queue.offer(current.left);
            }
            i++;
            
            // Right child
            if (i < nodes.length && nodes[i] != null) {
                current.right = new TreeNode(nodes[i]);
                queue.offer(current.right);
            }
            i++;
        }
        
        return root;
    }
    
    /**
     * Visualize diameter calculation process
     */
    public void visualizeDiameterCalculation(TreeNode root) {
        System.out.println("\nDiameter Calculation Process:");
        System.out.println("Tree Structure:");
        printTree(root, 0);
        
        int diameter = diameterOfBinaryTree(root);
        System.out.println("Calculated Diameter: " + diameter);
        
        // Show the calculation process
        System.out.println("\nDFS Calculation Steps:");
        visualizeDFSProcess(root);
        
        // Find and display the longest path
        DiameterResult result = diameterOfBinaryTreeWithPath(root);
        System.out.println("Longest Path (" + result.diameter + " edges): " + result.longestPath);
    }
    
    private void printTree(TreeNode root, int depth) {
        if (root == null) {
            printIndent(depth);
            System.out.println("null");
            return;
        }
        
        printIndent(depth);
        System.out.println(root.val);
        
        if (root.left != null || root.right != null) {
            printTree(root.left, depth + 1);
            printTree(root.right, depth + 1);
        }
    }
    
    private void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
    }
    
    /**
     * Visualize the DFS calculation process step by step
     */
    private void visualizeDFSProcess(TreeNode root) {
        diameter = 0;
        visualizeDFSHelper(root, 1);
    }
    
    private int visualizeDFSHelper(TreeNode node, int depth) {
        if (node == null) {
            System.out.println("  ".repeat(depth - 1) + "Node: null → Height: 0");
            return 0;
        }
        
        System.out.println("  ".repeat(depth - 1) + "Processing node: " + node.val);
        
        int leftHeight = visualizeDFSHelper(node.left, depth + 1);
        int rightHeight = visualizeDFSHelper(node.right, depth + 1);
        
        int currentDiameter = leftHeight + rightHeight;
        int currentHeight = 1 + Math.max(leftHeight, rightHeight);
        
        System.out.println("  ".repeat(depth - 1) + "Node: " + node.val + 
                          " → leftHeight: " + leftHeight + 
                          ", rightHeight: " + rightHeight +
                          ", diameterThroughNode: " + currentDiameter +
                          ", currentHeight: " + currentHeight);
        
        if (currentDiameter > diameter) {
            diameter = currentDiameter;
            System.out.println("  ".repeat(depth - 1) + "*** NEW MAX DIAMETER: " + diameter + " ***");
        }
        
        return currentHeight;
    }
    
    /**
     * Analyze tree structure and predict diameter
     */
    public void analyzeTreeStructure(TreeNode root) {
        System.out.println("\nTree Structure Analysis:");
        
        if (root == null) {
            System.out.println("Empty tree - Diameter: 0");
            return;
        }
        
        int leftHeight = maxDepth(root.left);
        int rightHeight = maxDepth(root.right);
        
        System.out.println("Left subtree height: " + leftHeight);
        System.out.println("Right subtree height: " + rightHeight);
        System.out.println("Diameter through root: " + (leftHeight + rightHeight));
        
        // Check if diameter likely goes through root
        int throughRoot = leftHeight + rightHeight;
        int actualDiameter = diameterOfBinaryTree(root);
        
        System.out.println("Actual diameter: " + actualDiameter);
        
        if (throughRoot == actualDiameter) {
            System.out.println("Longest path goes through the root");
        } else {
            System.out.println("Longest path does NOT go through the root");
        }
    }
    
    private int maxDepth(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(maxDepth(node.left), maxDepth(node.right));
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Diameter of Binary Tree");
        System.out.println("===============================\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test 1: Example [1,2,3,4,5]");
        Integer[] nodes1 = {1,2,3,4,5};
        TreeNode root1 = solution.buildTree(nodes1);
        
        int result1a = solution.diameterOfBinaryTree(root1);
        int result1b = solution.diameterOfBinaryTreeNoGlobal(root1);
        int result1c = solution.diameterOfBinaryTreeIterative(root1);
        int expected1 = 3;
        
        System.out.println("DFS Global:    " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("DFS No Global: " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Iterative:     " + result1c + " - " + 
                         (result1c == expected1 ? "PASSED" : "FAILED"));
        
        solution.visualizeDiameterCalculation(root1);
        solution.analyzeTreeStructure(root1);
        
        // Test Case 2: Simple tree
        System.out.println("\nTest 2: Simple tree [1,2]");
        Integer[] nodes2 = {1,2};
        TreeNode root2 = solution.buildTree(nodes2);
        int result2 = solution.diameterOfBinaryTree(root2);
        System.out.println("Simple tree: " + result2 + " - " + 
                         (result2 == 1 ? "PASSED" : "FAILED"));
        
        // Test Case 3: Single node
        System.out.println("\nTest 3: Single node [1]");
        Integer[] nodes3 = {1};
        TreeNode root3 = solution.buildTree(nodes3);
        int result3 = solution.diameterOfBinaryTree(root3);
        System.out.println("Single node: " + result3 + " - " + 
                         (result3 == 0 ? "PASSED" : "FAILED"));
        
        // Test Case 4: Left-skewed tree
        System.out.println("\nTest 4: Left-skewed tree [1,2,null,3,null,4]");
        Integer[] nodes4 = {1,2,null,3,null,4};
        TreeNode root4 = solution.buildTree(nodes4);
        int result4 = solution.diameterOfBinaryTree(root4);
        System.out.println("Left-skewed: " + result4 + " - " + 
                         (result4 == 3 ? "PASSED" : "FAILED"));
        
        // Test Case 5: Right-skewed tree
        System.out.println("\nTest 5: Right-skewed tree [1,null,2,null,3,null,4]");
        Integer[] nodes5 = {1,null,2,null,3,null,4};
        TreeNode root5 = solution.buildTree(nodes5);
        int result5 = solution.diameterOfBinaryTree(root5);
        System.out.println("Right-skewed: " + result5 + " - " + 
                         (result5 == 3 ? "PASSED" : "FAILED"));
        
        // Test Case 6: Balanced tree with diameter not through root
        System.out.println("\nTest 6: Complex tree [1,2,3,4,5,null,null,6,7,8,9]");
        Integer[] nodes6 = {1,2,3,4,5,null,null,6,7,8,9};
        TreeNode root6 = solution.buildTree(nodes6);
        int result6 = solution.diameterOfBinaryTree(root6);
        System.out.println("Complex tree: " + result6);
        solution.analyzeTreeStructure(root6);
        
        // Test Case 7: Large balanced tree
        System.out.println("\nTest 7: Large balanced tree");
        TreeNode root7 = createBalancedTree(4); // 15 nodes
        int result7 = solution.diameterOfBinaryTree(root7);
        System.out.println("Large balanced: " + result7 + " (expected: 6 for perfect tree of height 4)");
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DIAMETER OF BINARY TREE ALGORITHM EXPLANATION");
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
        System.out.println("Creating a large tree for performance testing...");
        
        // Create a balanced tree with 10000 nodes
        TreeNode largeTree = createBalancedTree(14); // ~16000 nodes
        
        // Warm up
        solution.diameterOfBinaryTree(largeTree);
        solution.diameterOfBinaryTreeNoGlobal(largeTree);
        solution.diameterOfBinaryTreeIterative(largeTree);
        
        // Test DFS with global
        long startTime = System.nanoTime();
        int result1 = solution.diameterOfBinaryTree(largeTree);
        long time1 = System.nanoTime() - startTime;
        
        // Test DFS no global
        startTime = System.nanoTime();
        int result2 = solution.diameterOfBinaryTreeNoGlobal(largeTree);
        long time2 = System.nanoTime() - startTime;
        
        // Test iterative
        startTime = System.nanoTime();
        int result3 = solution.diameterOfBinaryTreeIterative(largeTree);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Performance Results (~16000 nodes):");
        System.out.println("DFS Global:    " + time1 + " ns → Diameter: " + result1);
        System.out.println("DFS No Global: " + time2 + " ns → Diameter: " + result2);
        System.out.println("Iterative:     " + time3 + " ns → Diameter: " + result3);
        
        // Verify all produce same result
        boolean allEqual = result1 == result2 && result1 == result3;
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Create a balanced binary tree for testing
     */
    private static TreeNode createBalancedTree(int levels) {
        if (levels <= 0) return null;
        return createBalancedTreeHelper(1, levels);
    }
    
    private static TreeNode createBalancedTreeHelper(int currentLevel, int maxLevel) {
        if (currentLevel > maxLevel) return null;
        
        TreeNode node = new TreeNode(currentLevel);
        node.left = createBalancedTreeHelper(currentLevel + 1, maxLevel);
        node.right = createBalancedTreeHelper(currentLevel + 1, maxLevel);
        return node;
    }
    
    /**
     * Detailed algorithm explanations
     */
    private static void explainAlgorithms() {
        System.out.println("\n1. DFS WITH GLOBAL VARIABLE (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     diameter = 0");
        System.out.println("     function calculateHeight(node):");
        System.out.println("       if node is null: return 0");
        System.out.println("       leftHeight = calculateHeight(node.left)");
        System.out.println("       rightHeight = calculateHeight(node.right)");
        System.out.println("       diameter = max(diameter, leftHeight + rightHeight)");
        System.out.println("       return 1 + max(leftHeight, rightHeight)");
        System.out.println("   Key Insight: Diameter through a node = sum of heights of its subtrees");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(h) - Recursion stack");
        
        System.out.println("\n2. WHY IT WORKS:");
        System.out.println("   - For any node, the longest path through it =");
        System.out.println("     left subtree height + right subtree height");
        System.out.println("   - We calculate this for every node and track the maximum");
        System.out.println("   - The path doesn't have to go through the root");
        System.out.println("   - Post-order traversal ensures we process children first");
        
        System.out.println("\n3. KEY OBSERVATIONS:");
        System.out.println("   - Diameter is measured in number of EDGES, not nodes");
        System.out.println("   - Single node tree has diameter 0 (no edges)");
        System.out.println("   - The longest path is between two leaf nodes");
        System.out.println("   - In skewed trees, diameter = height - 1");
        
        System.out.println("\n4. ALTERNATIVE APPROACHES:");
        System.out.println("   - Return Object: Avoid global variable but more complex");
        System.out.println("   - Iterative DFS: Simulates recursion with stack");
        System.out.println("   - BFS Style: O(n²) time, educational but inefficient");
        
        System.out.println("\n5. EDGE CASES:");
        System.out.println("   - Empty tree: diameter = 0");
        System.out.println("   - Single node: diameter = 0");
        System.out.println("   - Two nodes: diameter = 1");
        System.out.println("   - Skewed tree: diameter = n - 1");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR DIAMETER OF BINARY TREE:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Always start with DFS + global variable approach");
        System.out.println("   - Most intuitive and efficient (O(n) time, O(h) space)");
        System.out.println("   - Mention alternative approaches if asked");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Diameter = longest path between any two nodes");
        System.out.println("   - Path may or may not pass through root");
        System.out.println("   - Length measured in edges, not nodes");
        System.out.println("   - Key insight: diameter through node = leftHeight + rightHeight");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Confusing edges vs nodes in diameter calculation");
        System.out.println("   - Forgetting that path doesn't have to go through root");
        System.out.println("   - Not handling null cases properly");
        System.out.println("   - Using O(n²) approach for large trees");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - What if we want the actual path, not just length?");
        System.out.println("   - How to find diameter in N-ary tree?");
        System.out.println("   - What about weighted edges?");
        System.out.println("   - How to find all diameters of length k?");
        
        System.out.println("\n5. Related Problems:");
        System.out.println("   - 124. Binary Tree Maximum Path Sum");
        System.out.println("   - 687. Longest Univalue Path");
        System.out.println("   - 298. Binary Tree Longest Consecutive Sequence");
        System.out.println("   - 104. Maximum Depth of Binary Tree");
        System.out.println("   - 110. Balanced Binary Tree");
        
        System.out.println("\n6. Code Pattern to Remember:");
        System.out.println("   class Solution {");
        System.out.println("     int diameter = 0;");
        System.out.println("     public int diameterOfBinaryTree(TreeNode root) {");
        System.out.println("         calculateHeight(root);");
        System.out.println("         return diameter;");
        System.out.println("     }");
        System.out.println("     private int calculateHeight(TreeNode node) {");
        System.out.println("         if (node == null) return 0;");
        System.out.println("         int left = calculateHeight(node.left);");
        System.out.println("         int right = calculateHeight(node.right);");
        System.out.println("         diameter = Math.max(diameter, left + right);");
        System.out.println("         return 1 + Math.max(left, right);");
        System.out.println("     }");
        System.out.println("   }");
        
        System.out.println("\n7. Real-world Applications:");
        System.out.println("   - Network diameter in computer networks");
        System.out.println("   - Organizational chart analysis");
        System.out.println("   - File system directory structure");
        System.out.println("   - Game tree analysis");
        System.out.println("   - Social network analysis");
    }
}
