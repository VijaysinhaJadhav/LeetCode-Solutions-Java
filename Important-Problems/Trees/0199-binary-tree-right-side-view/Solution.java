
## Solution.java

```java
/**
 * 199. Binary Tree Right Side View
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given the root of a binary tree, imagine yourself standing on the right side of it,
 * return the values of the nodes you can see ordered from top to bottom.
 * 
 * Key Insights:
 * 1. Right side view = last node at each level in level order traversal
 * 2. Can use BFS (level order) and take the last node of each level
 * 3. Can use DFS with right-first traversal, recording first node at each depth
 * 4. Multiple approaches with O(n) time complexity
 * 
 * Approaches:
 * 1. BFS Level Order - RECOMMENDED for interviews
 * 2. DFS Right-First - Efficient recursive approach
 * 3. BFS with Single Queue - Standard level order modification
 * 4. DFS Left-First with Depth Tracking - Alternative DFS approach
 * 5. Iterative DFS - Stack-based approach
 * 
 * Time Complexity: O(n) for all approaches
 * Space Complexity: O(w) for BFS, O(h) for DFS where w=width, h=height
 * 
 * Tags: Tree, Depth-First Search, Breadth-First Search, Binary Tree
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
     * Approach 1: BFS Level Order - RECOMMENDED for interviews
     * Time: O(n), Space: O(w) where w is maximum level width
     * Algorithm:
     * 1. Perform level order traversal (BFS)
     * 2. For each level, add the last node's value to result
     * 3. This gives the rightmost node at each level
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                
                // If this is the last node in current level, add to result
                if (i == levelSize - 1) {
                    result.add(currentNode.val);
                }
                
                // Add children to queue for next level
                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: DFS Right-First Traversal
     * Time: O(n), Space: O(h) for recursion stack
     * Algorithm:
     * 1. Traverse right subtree first, then left subtree
     * 2. Record the first node encountered at each depth
     * 3. This ensures rightmost nodes are recorded first
     */
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        dfsRightFirst(root, 0, result);
        return result;
    }
    
    private void dfsRightFirst(TreeNode node, int depth, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // If this is the first node at this depth, add to result
        // Since we traverse right first, this will be the rightmost node
        if (depth == result.size()) {
            result.add(node.val);
        }
        
        // Traverse right first, then left
        dfsRightFirst(node.right, depth + 1, result);
        dfsRightFirst(node.left, depth + 1, result);
    }
    
    /**
     * Approach 3: BFS with Level Tracking
     * Time: O(n), Space: O(w)
     * Alternative BFS implementation that explicitly tracks levels
     */
    public List<Integer> rightSideViewBFSLevel(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            TreeNode lastNode = null;
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                lastNode = queue.poll();
                
                // Add children to queue
                if (lastNode.left != null) {
                    queue.offer(lastNode.left);
                }
                if (lastNode.right != null) {
                    queue.offer(lastNode.right);
                }
            }
            
            // The last node processed in this level is the rightmost node
            if (lastNode != null) {
                result.add(lastNode.val);
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: DFS Left-First with Depth Tracking
     * Time: O(n), Space: O(h)
     * Traverse left first but update values as we find nodes at same depth
     */
    public List<Integer> rightSideViewDFSLeftFirst(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        dfsLeftFirst(root, 0, result);
        return result;
    }
    
    private void dfsLeftFirst(TreeNode node, int depth, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // If we haven't seen this depth before, add placeholder
        if (depth >= result.size()) {
            result.add(node.val);
        } else {
            // Update the value for this depth (overwrite with rightmost node)
            result.set(depth, node.val);
        }
        
        // Traverse left first, then right
        // Right traversal will overwrite left nodes at same depth
        dfsLeftFirst(node.left, depth + 1, result);
        dfsLeftFirst(node.right, depth + 1, result);
    }
    
    /**
     * Approach 5: Iterative DFS with Stack
     * Time: O(n), Space: O(h)
     * Uses stack to simulate DFS with depth tracking
     */
    public List<Integer> rightSideViewIterativeDFS(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        nodeStack.push(root);
        depthStack.push(0);
        
        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            int depth = depthStack.pop();
            
            // If this is the first node at this depth, add to result
            if (depth == result.size()) {
                result.add(node.val);
            }
            
            // Push left first, then right (so right is processed first)
            if (node.left != null) {
                nodeStack.push(node.left);
                depthStack.push(depth + 1);
            }
            if (node.right != null) {
                nodeStack.push(node.right);
                depthStack.push(depth + 1);
            }
        }
        
        return result;
    }
    
    /**
     * Approach 6: BFS with Two Queues
     * Time: O(n), Space: O(w)
     * Uses separate queues for current and next levels
     */
    public List<Integer> rightSideViewTwoQueues(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> currentLevel = new LinkedList<>();
        Queue<TreeNode> nextLevel = new LinkedList<>();
        currentLevel.offer(root);
        
        while (!currentLevel.isEmpty()) {
            TreeNode lastNode = null;
            
            // Process current level
            while (!currentLevel.isEmpty()) {
                lastNode = currentLevel.poll();
                
                // Add children to next level
                if (lastNode.left != null) {
                    nextLevel.offer(lastNode.left);
                }
                if (lastNode.right != null) {
                    nextLevel.offer(lastNode.right);
                }
            }
            
            // The last node processed is the rightmost node
            if (lastNode != null) {
                result.add(lastNode.val);
            }
            
            // Swap queues for next iteration
            Queue<TreeNode> temp = currentLevel;
            currentLevel = nextLevel;
            nextLevel = temp;
        }
        
        return result;
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
     * Visualize right side view process
     */
    public void visualizeRightSideView(TreeNode root, String approach) {
        System.out.println("\n" + approach + " Right Side View:");
        System.out.println("Tree Structure:");
        printTree(root, 0);
        
        List<Integer> result;
        switch (approach) {
            case "BFS":
                result = rightSideView(root);
                break;
            case "DFS":
                result = rightSideViewDFS(root);
                break;
            case "TwoQueues":
                result = rightSideViewTwoQueues(root);
                break;
            default:
                result = rightSideView(root);
        }
        
        System.out.println("Right Side View: " + result);
        
        if (approach.equals("BFS")) {
            System.out.println("\nStep-by-Step BFS Process:");
            visualizeBFSProcess(root);
        } else if (approach.equals("DFS")) {
            System.out.println("\nStep-by-Step DFS Process:");
            visualizeDFSProcess(root);
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
     * Visualize BFS process step by step
     */
    private void visualizeBFSProcess(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 0;
        
        System.out.println("Level | Nodes in Level | Rightmost Node");
        System.out.println("------|----------------|---------------");
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> levelNodes = new ArrayList<>();
            int rightmostNode = 0;
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelNodes.add(node.val);
                
                // Record the last node as rightmost
                if (i == levelSize - 1) {
                    rightmostNode = node.val;
                }
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            System.out.printf("%5d | %14s | %13d%n", 
                level, levelNodes, rightmostNode);
            level++;
        }
    }
    
    /**
     * Visualize DFS right-first process step by step
     */
    private void visualizeDFSProcess(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        List<String> steps = new ArrayList<>();
        dfsVisualizeHelper(root, 0, result, steps);
        
        System.out.println("Step | Depth | Action | Result So Far");
        System.out.println("-----|-------|--------|--------------");
        
        for (int i = 0; i < steps.size(); i++) {
            System.out.printf("%4d | %5s%n", i + 1, steps.get(i));
        }
        
        System.out.println("Final Result: " + result);
    }
    
    private void dfsVisualizeHelper(TreeNode node, int depth, List<Integer> result, List<String> steps) {
        if (node == null) {
            steps.add(depth + "     | Visit null    | " + result);
            return;
        }
        
        String action;
        if (depth == result.size()) {
            result.add(node.val);
            action = "Add " + node.val + " (first at depth " + depth + ")";
        } else {
            action = "Skip " + node.val + " (not first at depth " + depth + ")";
        }
        steps.add(depth + "     | " + action + " | " + result);
        
        // Right first
        dfsVisualizeHelper(node.right, depth + 1, result, steps);
        // Then left
        dfsVisualizeHelper(node.left, depth + 1, result, steps);
    }
    
    /**
     * Analyze tree and right side view properties
     */
    public void analyzeRightSideView(TreeNode root) {
        System.out.println("\nRight Side View Analysis:");
        
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        List<Integer> rightView = rightSideView(root);
        List<Integer> leftView = getLeftSideView(root);
        List<List<Integer>> levelOrder = getLevelOrder(root);
        
        System.out.println("Tree height: " + getHeight(root));
        System.out.println("Number of levels: " + levelOrder.size());
        System.out.println("Right side view: " + rightView);
        System.out.println("Left side view: " + leftView);
        System.out.println("Level order: " + levelOrder);
        
        // Show which nodes are visible from right
        System.out.println("\nVisible Nodes from Right:");
        for (int i = 0; i < levelOrder.size(); i++) {
            List<Integer> level = levelOrder.get(i);
            int rightmost = level.get(level.size() - 1);
            System.out.println("Level " + i + ": " + level + " → Visible: " + rightmost);
        }
    }
    
    private List<Integer> getLeftSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            result.add(queue.peek().val); // First node in level
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return result;
    }
    
    private List<List<Integer>> getLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(level);
        }
        
        return result;
    }
    
    private int getHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
    
    /**
     * Compare different approaches
     */
    public void compareApproaches(TreeNode root) {
        System.out.println("\nApproach Comparison:");
        
        if (root == null) {
            System.out.println("Empty tree - all approaches return empty list");
            return;
        }
        
        // Test all approaches
        long startTime = System.nanoTime();
        List<Integer> result1 = rightSideView(root);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result2 = rightSideViewDFS(root);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result3 = rightSideViewTwoQueues(root);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result4 = rightSideViewIterativeDFS(root);
        long time4 = System.nanoTime() - startTime;
        
        System.out.println("BFS Single Queue: " + time1 + " ns → " + result1);
        System.out.println("DFS Right-First:  " + time2 + " ns → " + result2);
        System.out.println("BFS Two Queues:   " + time3 + " ns → " + result3);
        System.out.println("Iterative DFS:    " + time4 + " ns → " + result4);
        
        // Verify all produce same result
        boolean allEqual = result1.equals(result2) && 
                          result1.equals(result3) && 
                          result1.equals(result4);
        System.out.println("All approaches produce same result: " + allEqual);
        
        // Space complexity analysis
        System.out.println("\nSpace Complexity Analysis:");
        System.out.println("BFS Single Queue: O(w) - w = maximum level width");
        System.out.println("DFS Right-First:  O(h) - h = tree height");
        System.out.println("BFS Two Queues:   O(w) - w = maximum level width");
        System.out.println("Iterative DFS:    O(h) - h = tree height");
    }
    
    /**
     * Test various tree structures
     */
    public void testVariousTrees() {
        System.out.println("\nVarious Tree Structure Tests:");
        
        // Test 1: Example from problem
        System.out.println("\n1. Example Tree [1,2,3,null,5,null,4]:");
        Integer[] tree1 = {1,2,3,null,5,null,4};
        TreeNode root1 = buildTree(tree1);
        visualizeRightSideView(root1, "BFS");
        analyzeRightSideView(root1);
        
        // Test 2: Complete binary tree
        System.out.println("\n2. Complete Binary Tree [1,2,3,4,5,6,7]:");
        Integer[] tree2 = {1,2,3,4,5,6,7};
        TreeNode root2 = buildTree(tree2);
        visualizeRightSideView(root2, "DFS");
        
        // Test 3: Left-skewed tree
        System.out.println("\n3. Left-Skewed Tree [1,2,null,3,null,4]:");
        Integer[] tree3 = {1,2,null,3,null,4};
        TreeNode root3 = buildTree(tree3);
        visualizeRightSideView(root3, "BFS");
        
        // Test 4: Right-skewed tree
        System.out.println("\n4. Right-Skewed Tree [1,null,2,null,3,null,4]:");
        Integer[] tree4 = {1,null,2,null,3,null,4};
        TreeNode root4 = buildTree(tree4);
        visualizeRightSideView(root4, "BFS");
        
        // Test 5: Single node
        System.out.println("\n5. Single Node [1]:");
        TreeNode root5 = new TreeNode(1);
        visualizeRightSideView(root5, "BFS");
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Binary Tree Right Side View");
        System.out.println("===================================\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test 1: Example tree [1,2,3,null,5,null,4]");
        Integer[] nodes1 = {1,2,3,null,5,null,4};
        TreeNode root1 = solution.buildTree(nodes1);
        
        List<Integer> result1a = solution.rightSideView(root1);
        List<Integer> result1b = solution.rightSideViewDFS(root1);
        List<Integer> result1c = solution.rightSideViewTwoQueues(root1);
        List<Integer> expected1 = Arrays.asList(1,3,4);
        
        System.out.println("BFS Single Queue: " + result1a + " - " + 
                         (result1a.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("DFS Right-First:  " + result1b + " - " + 
                         (result1b.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("BFS Two Queues:   " + result1c + " - " + 
                         (result1c.equals(expected1) ? "PASSED" : "FAILED"));
        
        solution.visualizeRightSideView(root1, "BFS");
        solution.analyzeRightSideView(root1);
        
        // Test Case 2: Single node
        System.out.println("\nTest 2: Single node [1]");
        TreeNode root2 = new TreeNode(1);
        List<Integer> result2 = solution.rightSideView(root2);
        System.out.println("Single node: " + result2 + " - " + 
                         (result2.equals(Arrays.asList(1)) ? "PASSED" : "FAILED"));
        
        // Test Case 3: Empty tree
        System.out.println("\nTest 3: Empty tree");
        TreeNode root3 = null;
        List<Integer> result3 = solution.rightSideView(root3);
        System.out.println("Empty tree: " + result3 + " - " + 
                         (result3.equals(Arrays.asList()) ? "PASSED" : "FAILED"));
        
        // Test Case 4: Right-skewed tree
        System.out.println("\nTest 4: Right-skewed tree");
        Integer[] nodes4 = {1,null,2,null,3,null,4};
        TreeNode root4 = solution.buildTree(nodes4);
        List<Integer> result4 = solution.rightSideView(root4);
        System.out.println("Right-skewed: " + result4 + " - " + 
                         (result4.equals(Arrays.asList(1,2,3,4)) ? "PASSED" : "FAILED"));
        
        // Test various tree structures
        solution.testVariousTrees();
        
        // Compare approaches
        System.out.println("\nTest 5: Approach Comparison");
        solution.compareApproaches(root1);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("RIGHT SIDE VIEW ALGORITHM EXPLANATION");
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
        System.out.println("Creating large trees for performance testing...");
        
        // Create a large complete binary tree
        TreeNode largeComplete = createCompleteTree(14); // 2^14 - 1 = 16383 nodes
        TreeNode largeSkewed = createSkewedTree(10000, true); // 10000 nodes
        
        System.out.println("\nLarge Complete Tree Performance (16383 nodes):");
        
        long startTime = System.nanoTime();
        solution.rightSideView(largeComplete);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.rightSideViewDFS(largeComplete);
        long time2 = System.nanoTime() - startTime;
        
        System.out.println("BFS: " + time1 + " ns");
        System.out.println("DFS: " + time2 + " ns");
        
        System.out.println("\nLarge Skewed Tree Performance (10000 nodes):");
        
        startTime = System.nanoTime();
        solution.rightSideView(largeSkewed);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.rightSideViewDFS(largeSkewed);
        long time4 = System.nanoTime() - startTime;
        
        System.out.println("BFS: " + time3 + " ns");
        System.out.println("DFS: " + time4 + " ns");
    }
    
    /**
     * Create a complete binary tree for testing
     */
    private static TreeNode createCompleteTree(int levels) {
        if (levels <= 0) return null;
        return createCompleteTreeHelper(1, levels);
    }
    
    private static TreeNode createCompleteTreeHelper(int value, int levels) {
        if (levels <= 0) return null;
        
        TreeNode node = new TreeNode(value);
        node.left = createCompleteTreeHelper(value * 2, levels - 1);
        node.right = createCompleteTreeHelper(value * 2 + 1, levels - 1);
        return node;
    }
    
    /**
     * Create a skewed tree for testing
     */
    private static TreeNode createSkewedTree(int size, boolean rightSkewed) {
        if (size <= 0) return null;
        
        TreeNode root = new TreeNode(1);
        TreeNode current = root;
        
        for (int i = 2; i <= size; i++) {
            if (rightSkewed) {
                current.right = new TreeNode(i);
                current = current.right;
            } else {
                current.left = new TreeNode(i);
                current = current.left;
            }
        }
        
        return root;
    }
    
    /**
     * Detailed algorithm explanations
     */
    private static void explainAlgorithms() {
        System.out.println("\n1. BFS LEVEL ORDER (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     queue = [root]");
        System.out.println("     while queue not empty:");
        System.out.println("       levelSize = queue.size");
        System.out.println("       for i = 0 to levelSize:");
        System.out.println("         node = queue.dequeue()");
        System.out.println("         if i == levelSize - 1: add node.val to result");
        System.out.println("         enqueue node.left and node.right if not null");
        System.out.println("   Key Insight:");
        System.out.println("     - Last node at each level = rightmost node");
        System.out.println("     - Natural level-by-level processing");
        System.out.println("     - O(n) time, O(w) space (w = level width)");
        
        System.out.println("\n2. DFS RIGHT-FIRST:");
        System.out.println("   Algorithm:");
        System.out.println("     function dfs(node, depth, result):");
        System.out.println("       if node is null: return");
        System.out.println("       if depth == result.size: add node.val to result");
        System.out.println("       dfs(node.right, depth + 1, result)");
        System.out.println("       dfs(node.left, depth + 1, result)");
        System.out.println("   Key Insight:");
        System.out.println("     - Traverse right subtree first");
        System.out.println("     - First node at each depth = rightmost node");
        System.out.println("     - O(n) time, O(h) space (h = tree height)");
        
        System.out.println("\n3. WHY THESE APPROACHES WORK:");
        System.out.println("   - BFS: Right side view = last node in each level order");
        System.out.println("   - DFS: By traversing right first, we encounter");
        System.out.println("          rightmost nodes before left nodes at same depth");
        System.out.println("   - Both approaches guarantee correct right side view");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR RIGHT SIDE VIEW:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with BFS level order (most intuitive)");
        System.out.println("   - Mention DFS right-first as alternative");
        System.out.println("   - Discuss trade-offs between BFS and DFS");
        System.out.println("   - Recommended order: BFS → DFS");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Right side view = last node at each level");
        System.out.println("   - BFS: Use queue, track level boundaries");
        System.out.println("   - DFS: Traverse right first, track depth");
        System.out.println("   - Time complexity: O(n) for both");
        System.out.println("   - Space complexity: O(w) vs O(h)");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Not handling empty tree case");
        System.out.println("   - Taking first node instead of last node in BFS");
        System.out.println("   - Traversing left first in DFS");
        System.out.println("   - Forgetting to check for null children");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - How to get left side view? (take first node in level)");
        System.out.println("   - How to get top view? (first node at each horizontal distance)");
        System.out.println("   - How to get bottom view? (last node at each horizontal distance)");
        System.out.println("   - What about binary trees with negative values?");
        System.out.println("   - How to handle very large trees?");
        
        System.out.println("\n5. Related Problems:");
        System.out.println("   - 102. Binary Tree Level Order Traversal");
        System.out.println("   - 103. Binary Tree Zigzag Level Order Traversal");
        System.out.println("   - 107. Binary Tree Level Order Traversal II");
        System.out.println("   - 116. Populating Next Right Pointers in Each Node");
        System.out.println("   - 545. Boundary of Binary Tree");
        
        System.out.println("\n6. Code Pattern to Remember:");
        System.out.println("   // BFS Approach");
        System.out.println("   public List<Integer> rightSideView(TreeNode root) {");
        System.out.println("     List<Integer> result = new ArrayList<>();");
        System.out.println("     if (root == null) return result;");
        System.out.println("     Queue<TreeNode> queue = new LinkedList<>();");
        System.out.println("     queue.offer(root);");
        System.out.println("     while (!queue.isEmpty()) {");
        System.out.println("       int levelSize = queue.size();");
        System.out.println("       for (int i = 0; i < levelSize; i++) {");
        System.out.println("         TreeNode node = queue.poll();");
        System.out.println("         if (i == levelSize - 1) result.add(node.val);");
        System.out.println("         if (node.left != null) queue.offer(node.left);");
        System.out.println("         if (node.right != null) queue.offer(node.right);");
        System.out.println("       }");
        System.out.println("     }");
        System.out.println("     return result;");
        System.out.println("   }");
        
        System.out.println("\n7. Real-world Applications:");
        System.out.println("   - Game development (camera following right side)");
        System.out.println("   - UI layout algorithms");
        System.out.println("   - Printing tree structures");
        System.out.println("   - Network routing visualization");
        System.out.println("   - File system directory visualization");
    }
}
