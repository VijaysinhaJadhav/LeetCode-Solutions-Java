
## Solution.java

```java
/**
 * 110. Balanced Binary Tree
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given a binary tree, determine if it is height-balanced.
 * A height-balanced binary tree: left and right subtrees of every node differ in height by no more than 1.
 * 
 * Key Insights:
 * 1. For each node: |left height - right height| ≤ 1
 * 2. ALL nodes in the tree must satisfy this condition
 * 3. Use DFS to calculate heights and check balance simultaneously
 * 4. Early termination: return immediately if any subtree is unbalanced
 * 5. Use sentinel value (-1) to indicate unbalanced state
 * 
 * Approaches:
 * 1. DFS with Early Termination - RECOMMENDED
 * 2. Two-Pass DFS - Calculate height then check balance (less efficient)
 * 3. Iterative DFS - Stack-based approach
 * 4. Return Object - More explicit state tracking
 * 
 * Time Complexity: O(n) for optimal approaches
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
    
    /**
     * Approach 1: DFS with Early Termination - RECOMMENDED for interviews
     * Time: O(n), Space: O(h) where h is tree height
     * Algorithm:
     * 1. Use DFS to calculate height of each node
     * 2. If any subtree is unbalanced, return -1 immediately
     * 3. Check balance condition: |left height - right height| ≤ 1
     * 4. Return height if balanced, -1 if unbalanced
     */
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }
    
    private int checkHeight(TreeNode node) {
        if (node == null) {
            return 0;  // Height of empty tree is 0
        }
        
        // Check left subtree
        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) {
            return -1;  // Left subtree is unbalanced
        }
        
        // Check right subtree
        int rightHeight = checkHeight(node.right);
        if (rightHeight == -1) {
            return -1;  // Right subtree is unbalanced
        }
        
        // Check current node's balance
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;  // Current node is unbalanced
        }
        
        // Return height of current node
        return 1 + Math.max(leftHeight, rightHeight);
    }
    
    /**
     * Approach 2: Two-Pass DFS - Less efficient but educational
     * Time: O(n²) in worst case, Space: O(h)
     * First pass: calculate height for each node
     * Second pass: check balance for each node
     */
    public boolean isBalancedTwoPass(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        // Check current node
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }
        
        // Recursively check left and right subtrees
        return isBalancedTwoPass(root.left) && isBalancedTwoPass(root.right);
    }
    
    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    
    /**
     * Approach 3: DFS with Return Object - More explicit state tracking
     * Time: O(n), Space: O(h)
     * Uses custom return type to track both height and balance status
     */
    public boolean isBalancedWithObject(TreeNode root) {
        TreeInfo result = checkBalanced(root);
        return result.isBalanced;
    }
    
    private TreeInfo checkBalanced(TreeNode node) {
        if (node == null) {
            return new TreeInfo(0, true);
        }
        
        TreeInfo leftInfo = checkBalanced(node.left);
        TreeInfo rightInfo = checkBalanced(node.right);
        
        // If any subtree is unbalanced, entire tree is unbalanced
        if (!leftInfo.isBalanced || !rightInfo.isBalanced) {
            return new TreeInfo(0, false);
        }
        
        // Check current node's balance
        if (Math.abs(leftInfo.height - rightInfo.height) > 1) {
            return new TreeInfo(0, false);
        }
        
        // Calculate current height
        int currentHeight = 1 + Math.max(leftInfo.height, rightInfo.height);
        return new TreeInfo(currentHeight, true);
    }
    
    /**
     * Helper class for Approach 3
     */
    private class TreeInfo {
        int height;
        boolean isBalanced;
        
        TreeInfo(int height, boolean isBalanced) {
            this.height = height;
            this.isBalanced = isBalanced;
        }
    }
    
    /**
     * Approach 4: Iterative DFS using Stack
     * Time: O(n), Space: O(h)
     * Simulates recursion using stack and HashMap to store heights
     */
    public boolean isBalancedIterative(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        Map<TreeNode, Integer> heights = new HashMap<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            
            // Check if we can process this node (both children processed or null)
            boolean leftProcessed = node.left == null || heights.containsKey(node.left);
            boolean rightProcessed = node.right == null || heights.containsKey(node.right);
            
            if (leftProcessed && rightProcessed) {
                stack.pop();
                
                int leftHeight = heights.getOrDefault(node.left, 0);
                int rightHeight = heights.getOrDefault(node.right, 0);
                
                // Check balance condition
                if (Math.abs(leftHeight - rightHeight) > 1) {
                    return false;
                }
                
                // Store height of current node
                heights.put(node, 1 + Math.max(leftHeight, rightHeight));
                
            } else {
                // Push unprocessed children
                if (node.right != null && !heights.containsKey(node.right)) {
                    stack.push(node.right);
                }
                if (node.left != null && !heights.containsKey(node.left)) {
                    stack.push(node.left);
                }
            }
        }
        
        return true;
    }
    
    /**
     * Approach 5: Enhanced DFS with Detailed Reporting
     * Provides detailed information about which nodes are unbalanced
     */
    public BalanceResult isBalancedDetailed(TreeNode root) {
        return checkBalanceDetailed(root);
    }
    
    private BalanceResult checkBalanceDetailed(TreeNode node) {
        if (node == null) {
            return new BalanceResult(0, true, "Empty tree is balanced");
        }
        
        BalanceResult leftResult = checkBalanceDetailed(node.left);
        BalanceResult rightResult = checkBalanceDetailed(node.right);
        
        // If any subtree is unbalanced, propagate the failure
        if (!leftResult.isBalanced) {
            return new BalanceResult(0, false, 
                "Left subtree unbalanced: " + leftResult.message);
        }
        if (!rightResult.isBalanced) {
            return new BalanceResult(0, false, 
                "Right subtree unbalanced: " + rightResult.message);
        }
        
        // Check current node's balance
        int heightDiff = Math.abs(leftResult.height - rightResult.height);
        if (heightDiff > 1) {
            String message = String.format(
                "Node %d unbalanced: left height=%d, right height=%d, difference=%d",
                node.val, leftResult.height, rightResult.height, heightDiff);
            return new BalanceResult(0, false, message);
        }
        
        // Current node is balanced
        int currentHeight = 1 + Math.max(leftResult.height, rightResult.height);
        String message = String.format(
            "Node %d balanced: left height=%d, right height=%d, difference=%d",
            node.val, leftResult.height, rightResult.height, heightDiff);
        return new BalanceResult(currentHeight, true, message);
    }
    
    /**
     * Helper class for Approach 5
     */
    private class BalanceResult {
        int height;
        boolean isBalanced;
        String message;
        
        BalanceResult(int height, boolean isBalanced, String message) {
            this.height = height;
            this.isBalanced = isBalanced;
            this.message = message;
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
     * Visualize balance checking process
     */
    public void visualizeBalanceCheck(TreeNode root) {
        System.out.println("\nBalance Check Process:");
        System.out.println("Tree Structure:");
        printTree(root, 0);
        
        boolean isBalanced = isBalanced(root);
        System.out.println("\nOverall Result: " + (isBalanced ? "BALANCED" : "NOT BALANCED"));
        
        // Show detailed analysis
        System.out.println("\nDetailed DFS Analysis:");
        visualizeDFSProcess(root);
        
        // Show which nodes are problematic if unbalanced
        if (!isBalanced) {
            System.out.println("\nUnbalanced Node Analysis:");
            findUnbalancedNodes(root);
        }
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
        int result = visualizeDFSHelper(root, 1);
        System.out.println("Final Result: " + (result != -1 ? "BALANCED" : "NOT BALANCED"));
    }
    
    private int visualizeDFSHelper(TreeNode node, int depth) {
        if (node == null) {
            System.out.println("  ".repeat(depth - 1) + "Node: null → Height: 0");
            return 0;
        }
        
        System.out.println("  ".repeat(depth - 1) + "Processing node: " + node.val);
        
        int leftHeight = visualizeDFSHelper(node.left, depth + 1);
        if (leftHeight == -1) {
            System.out.println("  ".repeat(depth - 1) + "Node: " + node.val + " → Left subtree UNBALANCED");
            return -1;
        }
        
        int rightHeight = visualizeDFSHelper(node.right, depth + 1);
        if (rightHeight == -1) {
            System.out.println("  ".repeat(depth - 1) + "Node: " + node.val + " → Right subtree UNBALANCED");
            return -1;
        }
        
        int heightDiff = Math.abs(leftHeight - rightHeight);
        boolean isBalanced = heightDiff <= 1;
        
        System.out.println("  ".repeat(depth - 1) + "Node: " + node.val + 
                          " → leftHeight: " + leftHeight + 
                          ", rightHeight: " + rightHeight +
                          ", difference: " + heightDiff +
                          ", balanced: " + isBalanced);
        
        if (!isBalanced) {
            System.out.println("  ".repeat(depth - 1) + "*** NODE " + node.val + " IS UNBALANCED ***");
            return -1;
        }
        
        int currentHeight = 1 + Math.max(leftHeight, rightHeight);
        System.out.println("  ".repeat(depth - 1) + "Node: " + node.val + " → Height: " + currentHeight);
        
        return currentHeight;
    }
    
    /**
     * Find and report all unbalanced nodes in the tree
     */
    private void findUnbalancedNodes(TreeNode root) {
        BalanceResult result = isBalancedDetailed(root);
        if (result.isBalanced) {
            System.out.println("All nodes are balanced!");
        } else {
            System.out.println("Unbalanced: " + result.message);
        }
    }
    
    /**
     * Analyze tree balance characteristics
     */
    public void analyzeTreeBalance(TreeNode root) {
        System.out.println("\nTree Balance Analysis:");
        
        if (root == null) {
            System.out.println("Empty tree - Balanced by definition");
            return;
        }
        
        int leftHeight = calculateHeight(root.left);
        int rightHeight = calculateHeight(root.right);
        int heightDiff = Math.abs(leftHeight - rightHeight);
        
        System.out.println("Root: left height = " + leftHeight + ", right height = " + rightHeight);
        System.out.println("Height difference at root: " + heightDiff);
        System.out.println("Root balanced: " + (heightDiff <= 1));
        
        // Check if tree is perfectly balanced
        boolean isPerfect = isPerfectBalanced(root);
        System.out.println("Perfectly balanced: " + isPerfect);
        
        // Calculate balance factor for root
        int balanceFactor = leftHeight - rightHeight;
        System.out.println("Balance factor (left - right): " + balanceFactor);
        
        if (balanceFactor > 0) {
            System.out.println("Tree is left-heavy at root");
        } else if (balanceFactor < 0) {
            System.out.println("Tree is right-heavy at root");
        } else {
            System.out.println("Tree is perfectly balanced at root");
        }
    }
    
    private int calculateHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }
    
    private boolean isPerfectBalanced(TreeNode root) {
        if (root == null) return true;
        
        int leftHeight = calculateHeight(root.left);
        int rightHeight = calculateHeight(root.right);
        
        if (Math.abs(leftHeight - rightHeight) > 0) {
            return false;
        }
        
        return isPerfectBalanced(root.left) && isPerfectBalanced(root.right);
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Balanced Binary Tree");
        System.out.println("============================\n");
        
        // Test Case 1: Example from problem (balanced)
        System.out.println("Test 1: Balanced tree [3,9,20,null,null,15,7]");
        Integer[] nodes1 = {3,9,20,null,null,15,7};
        TreeNode root1 = solution.buildTree(nodes1);
        
        boolean result1a = solution.isBalanced(root1);
        boolean result1b = solution.isBalancedWithObject(root1);
        boolean result1c = solution.isBalancedIterative(root1);
        boolean expected1 = true;
        
        System.out.println("DFS Early Term: " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("DFS With Object: " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Iterative: " + result1c + " - " + 
                         (result1c == expected1 ? "PASSED" : "FAILED"));
        
        solution.visualizeBalanceCheck(root1);
        solution.analyzeTreeBalance(root1);
        
        // Test Case 2: Example from problem (unbalanced)
        System.out.println("\nTest 2: Unbalanced tree [1,2,2,3,3,null,null,4,4]");
        Integer[] nodes2 = {1,2,2,3,3,null,null,4,4};
        TreeNode root2 = solution.buildTree(nodes2);
        
        boolean result2 = solution.isBalanced(root2);
        System.out.println("Unbalanced tree: " + result2 + " - " + 
                         (result2 == false ? "PASSED" : "FAILED"));
        
        solution.visualizeBalanceCheck(root2);
        
        // Test Case 3: Empty tree
        System.out.println("\nTest 3: Empty tree");
        TreeNode root3 = null;
        boolean result3 = solution.isBalanced(root3);
        System.out.println("Empty tree: " + result3 + " - " + 
                         (result3 == true ? "PASSED" : "FAILED"));
        
        // Test Case 4: Single node
        System.out.println("\nTest 4: Single node [1]");
        Integer[] nodes4 = {1};
        TreeNode root4 = solution.buildTree(nodes4);
        boolean result4 = solution.isBalanced(root4);
        System.out.println("Single node: " + result4 + " - " + 
                         (result4 == true ? "PASSED" : "FAILED"));
        
        // Test Case 5: Left-skewed but balanced
        System.out.println("\nTest 5: Left-skewed balanced tree [1,2,null,3]");
        Integer[] nodes5 = {1,2,null,3};
        TreeNode root5 = solution.buildTree(nodes5);
        boolean result5 = solution.isBalanced(root5);
        System.out.println("Left-skewed balanced: " + result5 + " - " + 
                         (result5 == false ? "PASSED" : "FAILED")); // Should be false
        
        // Test Case 6: Right-skewed but balanced  
        System.out.println("\nTest 6: Right-skewed balanced tree [1,null,2,null,3]");
        Integer[] nodes6 = {1,null,2,null,3};
        TreeNode root6 = solution.buildTree(nodes6);
        boolean result6 = solution.isBalanced(root6);
        System.out.println("Right-skewed balanced: " + result6 + " - " + 
                         (result6 == false ? "PASSED" : "FAILED")); // Should be false
        
        // Test Case 7: Perfectly balanced tree
        System.out.println("\nTest 7: Perfectly balanced tree [1,2,3,4,5,6,7]");
        Integer[] nodes7 = {1,2,3,4,5,6,7};
        TreeNode root7 = solution.buildTree(nodes7);
        boolean result7 = solution.isBalanced(root7);
        System.out.println("Perfectly balanced: " + result7 + " - " + 
                         (result7 == true ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BALANCED BINARY TREE ALGORITHM EXPLANATION");
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
        System.out.println("Creating test trees for performance testing...");
        
        // Create a large balanced tree
        TreeNode balancedTree = createBalancedTree(12); // ~4000 nodes
        
        // Create a large unbalanced tree (left-skewed)
        TreeNode unbalancedTree = createSkewedTree(2000, true);
        
        // Test balanced tree
        System.out.println("\nBalanced Tree Performance (~4000 nodes):");
        
        long startTime = System.nanoTime();
        boolean result1 = solution.isBalanced(balancedTree);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result2 = solution.isBalancedTwoPass(balancedTree);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result3 = solution.isBalancedIterative(balancedTree);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("DFS Early Term: " + time1 + " ns → " + result1);
        System.out.println("Two-Pass DFS:   " + time2 + " ns → " + result2);
        System.out.println("Iterative:      " + time3 + " ns → " + result3);
        
        // Test unbalanced tree (early termination should be faster)
        System.out.println("\nUnbalanced Tree Performance (2000 nodes, early termination):");
        
        startTime = System.nanoTime();
        boolean result4 = solution.isBalanced(unbalancedTree);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result5 = solution.isBalancedTwoPass(unbalancedTree);
        long time5 = System.nanoTime() - startTime;
        
        System.out.println("DFS Early Term: " + time4 + " ns → " + result4);
        System.out.println("Two-Pass DFS:   " + time5 + " ns → " + result5);
        System.out.println("Speedup factor: " + (double)time5/time4);
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
     * Create a skewed tree for testing
     */
    private static TreeNode createSkewedTree(int size, boolean leftSkewed) {
        if (size <= 0) return null;
        
        TreeNode root = new TreeNode(1);
        TreeNode current = root;
        
        for (int i = 2; i <= size; i++) {
            if (leftSkewed) {
                current.left = new TreeNode(i);
                current = current.left;
            } else {
                current.right = new TreeNode(i);
                current = current.right;
            }
        }
        
        return root;
    }
    
    /**
     * Detailed algorithm explanations
     */
    private static void explainAlgorithms() {
        System.out.println("\n1. DFS WITH EARLY TERMINATION (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     function checkHeight(node):");
        System.out.println("       if node is null: return 0");
        System.out.println("       leftHeight = checkHeight(node.left)");
        System.out.println("       if leftHeight == -1: return -1");
        System.out.println("       rightHeight = checkHeight(node.right)");
        System.out.println("       if rightHeight == -1: return -1");
        System.out.println("       if |leftHeight - rightHeight| > 1: return -1");
        System.out.println("       return 1 + max(leftHeight, rightHeight)");
        System.out.println("   Key Features:");
        System.out.println("     - Single pass through the tree");
        System.out.println("     - Early termination when imbalance detected");
        System.out.println("     - Combines height calculation and balance check");
        System.out.println("     - Time: O(n), Space: O(h)");
        
        System.out.println("\n2. WHY EARLY TERMINATION MATTERS:");
        System.out.println("   - For unbalanced trees, we don't need to check all nodes");
        System.out.println("   - As soon as we find one unbalanced node, we can stop");
        System.out.println("   - This improves performance for unbalanced cases");
        System.out.println("   - Worst-case still O(n) but average case can be better");
        
        System.out.println("\n3. BALANCE DEFINITION:");
        System.out.println("   - For EVERY node: |left height - right height| ≤ 1");
        System.out.println("   - Height: longest path from node to leaf (in edges)");
        System.out.println("   - Empty tree: height = 0, balanced = true");
        System.out.println("   - Single node: height = 1, balanced = true");
        
        System.out.println("\n4. COMMON MISCONCEPTIONS:");
        System.out.println("   - Balanced ≠ Complete: Complete trees have all levels filled except last");
        System.out.println("   - Balanced ≠ Perfect: Perfect trees have all leaves at same level");
        System.out.println("   - Balanced ≠ Full: Full trees have 0 or 2 children per node");
        System.out.println("   - A tree can be balanced but not complete/perfect/full");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR BALANCED BINARY TREE:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Always start with DFS + early termination approach");
        System.out.println("   - Most efficient (O(n) time, O(h) space)");
        System.out.println("   - Mention two-pass approach but explain why it's inefficient");
        System.out.println("   - Be prepared to discuss time/space complexity");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Definition: every node must have |left height - right height| ≤ 1");
        System.out.println("   - Empty tree is balanced by definition");
        System.out.println("   - Early termination improves performance");
        System.out.println("   - Use -1 as sentinel value for unbalanced state");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Forgetting that ALL nodes must be balanced, not just root");
        System.out.println("   - Confusing height (edges) with depth (levels)");
        System.out.println("   - Not handling the empty tree case");
        System.out.println("   - Using O(n²) approach unnecessarily");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - How would you fix an unbalanced tree? (AVL tree rotation)");
        System.out.println("   - What's the minimum/maximum number of nodes in balanced tree of height h?");
        System.out.println("   - How to check if tree is complete/perfect/full?");
        System.out.println("   - How to balance an existing binary search tree?");
        
        System.out.println("\n5. Related Problems:");
        System.out.println("   - 104. Maximum Depth of Binary Tree");
        System.out.println("   - 111. Minimum Depth of Binary Tree");
        System.out.println("   - 543. Diameter of Binary Tree");
        System.out.println("   - 124. Binary Tree Maximum Path Sum");
        System.out.println("   - 687. Longest Univalue Path");
        
        System.out.println("\n6. Code Pattern to Remember:");
        System.out.println("   public boolean isBalanced(TreeNode root) {");
        System.out.println("     return checkHeight(root) != -1;");
        System.out.println("   }");
        System.out.println("   private int checkHeight(TreeNode node) {");
        System.out.println("     if (node == null) return 0;");
        System.out.println("     int left = checkHeight(node.left);");
        System.out.println("     if (left == -1) return -1;");
        System.out.println("     int right = checkHeight(node.right);");
        System.out.println("     if (right == -1) return -1;");
        System.out.println("     if (Math.abs(left - right) > 1) return -1;");
        System.out.println("     return 1 + Math.max(left, right);");
        System.out.println("   }");
        
        System.out.println("\n7. Real-world Applications:");
        System.out.println("   - AVL trees (self-balancing binary search trees)");
        System.out.println("   - Database indexing (B-trees, Red-Black trees)");
        System.out.println("   - File system organization");
        System.out.println("   - Game decision trees");
        System.out.println("   - Compiler syntax trees");
    }
}
