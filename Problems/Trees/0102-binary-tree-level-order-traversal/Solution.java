
## Solution.java

```java
/**
 * 102. Binary Tree Level Order Traversal
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given the root of a binary tree, return the level order traversal of its nodes' values.
 * (i.e., from left to right, level by level).
 * 
 * Key Insights:
 * 1. Level order traversal = Breadth-First Search (BFS)
 * 2. Use queue to process nodes level by level
 * 3. Track level boundaries using queue size
 * 4. Multiple approaches: BFS, DFS with level tracking, two queues, etc.
 * 
 * Approaches:
 * 1. BFS with Single Queue - RECOMMENDED for interviews
 * 2. BFS with Two Queues - Explicit level separation
 * 3. DFS with Level Tracking - Recursive approach
 * 4. BFS with Level Size Tracking - Most common implementation
 * 5. BFS with Sentinel Nodes - Using null markers
 * 
 * Time Complexity: O(n) for all approaches
 * Space Complexity: O(w) for BFS, O(h) for DFS where w=width, h=height
 * 
 * Tags: Tree, Breadth-First Search, Binary Tree
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
     * Approach 1: BFS with Single Queue and Level Size Tracking - RECOMMENDED
     * Time: O(n), Space: O(w) where w is maximum level width
     * Algorithm:
     * 1. Use queue for BFS traversal
     * 2. For each level, record queue size (number of nodes at that level)
     * 3. Process all nodes at current level, add their values to level list
     * 4. Add children to queue for next level
     * 5. Add level list to result
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                currentLevel.add(currentNode.val);
                
                // Add children to queue for next level
                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
            
            result.add(currentLevel);
        }
        
        return result;
    }
    
    /**
     * Approach 2: BFS with Two Queues
     * Time: O(n), Space: O(w)
     * Uses separate queues for current and next levels
     */
    public List<List<Integer>> levelOrderTwoQueues(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> currentLevel = new LinkedList<>();
        Queue<TreeNode> nextLevel = new LinkedList<>();
        currentLevel.offer(root);
        
        while (!currentLevel.isEmpty()) {
            List<Integer> levelValues = new ArrayList<>();
            
            // Process current level
            while (!currentLevel.isEmpty()) {
                TreeNode node = currentLevel.poll();
                levelValues.add(node.val);
                
                // Add children to next level
                if (node.left != null) {
                    nextLevel.offer(node.left);
                }
                if (node.right != null) {
                    nextLevel.offer(node.right);
                }
            }
            
            result.add(levelValues);
            
            // Swap queues for next iteration
            Queue<TreeNode> temp = currentLevel;
            currentLevel = nextLevel;
            nextLevel = temp;
        }
        
        return result;
    }
    
    /**
     * Approach 3: DFS with Level Tracking
     * Time: O(n), Space: O(h) for recursion stack
     * Uses pre-order DFS but tracks level information
     */
    public List<List<Integer>> levelOrderDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        dfsHelper(root, 0, result);
        return result;
    }
    
    private void dfsHelper(TreeNode node, int level, List<List<Integer>> result) {
        if (node == null) {
            return;
        }
        
        // Create new level list if needed
        if (level >= result.size()) {
            result.add(new ArrayList<>());
        }
        
        // Add current node to its level
        result.get(level).add(node.val);
        
        // Recursively process children with increased level
        dfsHelper(node.left, level + 1, result);
        dfsHelper(node.right, level + 1, result);
    }
    
    /**
     * Approach 4: BFS with Sentinel Nodes
     * Time: O(n), Space: O(w)
     * Uses null markers to separate levels
     */
    public List<List<Integer>> levelOrderSentinel(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(null); // Sentinel for level separation
        
        List<Integer> currentLevel = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.poll();
            
            if (currentNode == null) {
                // End of current level
                result.add(currentLevel);
                currentLevel = new ArrayList<>();
                
                // Add new sentinel if there are more nodes
                if (!queue.isEmpty()) {
                    queue.offer(null);
                }
            } else {
                currentLevel.add(currentNode.val);
                
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
     * Approach 5: BFS with Pair Class (Node + Level)
     * Time: O(n), Space: O(w)
     * Stores level information with each node
     */
    public List<List<Integer>> levelOrderWithPair(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 0));
        
        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            TreeNode node = current.node;
            int level = current.level;
            
            // Create new level list if needed
            if (level >= result.size()) {
                result.add(new ArrayList<>());
            }
            
            // Add node to its level
            result.get(level).add(node.val);
            
            // Add children with increased level
            if (node.left != null) {
                queue.offer(new Pair(node.left, level + 1));
            }
            if (node.right != null) {
                queue.offer(new Pair(node.right, level + 1));
            }
        }
        
        return result;
    }
    
    /**
     * Helper class for Approach 5
     */
    private class Pair {
        TreeNode node;
        int level;
        
        Pair(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }
    
    /**
     * Approach 6: Iterative DFS with Stack
     * Time: O(n), Space: O(h)
     * Uses stack to simulate DFS with level tracking
     */
    public List<List<Integer>> levelOrderIterativeDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> levelStack = new Stack<>();
        nodeStack.push(root);
        levelStack.push(0);
        
        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            int level = levelStack.pop();
            
            // Create new level list if needed
            if (level >= result.size()) {
                result.add(new ArrayList<>());
            }
            
            // Add node to its level
            result.get(level).add(node.val);
            
            // Push right first, then left (for correct left-to-right order)
            if (node.right != null) {
                nodeStack.push(node.right);
                levelStack.push(level + 1);
            }
            if (node.left != null) {
                nodeStack.push(node.left);
                levelStack.push(level + 1);
            }
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
     * Visualize level order traversal process
     */
    public void visualizeTraversal(TreeNode root, String approach) {
        System.out.println("\n" + approach + " Level Order Traversal:");
        System.out.println("Tree Structure:");
        printTree(root, 0);
        
        List<List<Integer>> result;
        switch (approach) {
            case "BFS":
                result = levelOrder(root);
                break;
            case "DFS":
                result = levelOrderDFS(root);
                break;
            case "TwoQueues":
                result = levelOrderTwoQueues(root);
                break;
            default:
                result = levelOrder(root);
        }
        
        System.out.println("Level Order Result: " + result);
        
        if (approach.equals("BFS")) {
            System.out.println("\nStep-by-Step BFS Process:");
            visualizeBFSProcess(root);
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
        
        System.out.println("Step | Action | Queue Contents | Current Level");
        System.out.println("-----|--------|----------------|--------------");
        
        int step = 1;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevelNodes = new ArrayList<>();
            
            System.out.println("Level " + level + " (" + levelSize + " nodes):");
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevelNodes.add(node.val);
                
                System.out.printf("%4d | Process %-3d | %-14s | %s%n",
                    step++, node.val, queueToString(queue), currentLevelNodes);
                
                if (node.left != null) {
                    queue.offer(node.left);
                    System.out.printf("%4d | Enqueue %-2d | %-14s | %s%n",
                        step++, node.left.val, queueToString(queue), currentLevelNodes);
                }
                
                if (node.right != null) {
                    queue.offer(node.right);
                    System.out.printf("%4d | Enqueue %-2d | %-14s | %s%n",
                        step++, node.right.val, queueToString(queue), currentLevelNodes);
                }
            }
            
            level++;
        }
    }
    
    private String queueToString(Queue<TreeNode> queue) {
        if (queue.isEmpty()) return "[]";
        List<String> elements = new ArrayList<>();
        for (TreeNode node : queue) {
            elements.add(String.valueOf(node.val));
        }
        return elements.toString();
    }
    
    /**
     * Analyze tree properties
     */
    public void analyzeTreeProperties(TreeNode root) {
        System.out.println("\nTree Properties Analysis:");
        
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        int height = getHeight(root);
        int totalNodes = countNodes(root);
        List<List<Integer>> levels = levelOrder(root);
        int maxWidth = getMaxWidth(levels);
        
        System.out.println("Height: " + height);
        System.out.println("Total nodes: " + totalNodes);
        System.out.println("Number of levels: " + levels.size());
        System.out.println("Maximum level width: " + maxWidth);
        System.out.println("Level sizes: " + getLevelSizes(levels));
        
        // Check if tree is complete
        boolean isComplete = isCompleteTree(root);
        System.out.println("Is complete binary tree: " + isComplete);
        
        // Check if tree is perfect
        boolean isPerfect = isPerfectTree(root);
        System.out.println("Is perfect binary tree: " + isPerfect);
    }
    
    private int getHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
    
    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    
    private int getMaxWidth(List<List<Integer>> levels) {
        int maxWidth = 0;
        for (List<Integer> level : levels) {
            maxWidth = Math.max(maxWidth, level.size());
        }
        return maxWidth;
    }
    
    private List<Integer> getLevelSizes(List<List<Integer>> levels) {
        List<Integer> sizes = new ArrayList<>();
        for (List<Integer> level : levels) {
            sizes.add(level.size());
        }
        return sizes;
    }
    
    private boolean isCompleteTree(TreeNode root) {
        if (root == null) return true;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean foundNull = false;
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (node == null) {
                foundNull = true;
            } else {
                if (foundNull) return false;
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        
        return true;
    }
    
    private boolean isPerfectTree(TreeNode root) {
        if (root == null) return true;
        
        int height = getHeight(root);
        int expectedNodes = (1 << height) - 1; // 2^height - 1
        int actualNodes = countNodes(root);
        
        return actualNodes == expectedNodes;
    }
    
    /**
     * Compare different traversal approaches
     */
    public void compareApproaches(TreeNode root) {
        System.out.println("\nApproach Comparison:");
        
        if (root == null) {
            System.out.println("Empty tree - all approaches return empty list");
            return;
        }
        
        // Test all approaches
        long startTime = System.nanoTime();
        List<List<Integer>> result1 = levelOrder(root);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result2 = levelOrderTwoQueues(root);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result3 = levelOrderDFS(root);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result4 = levelOrderSentinel(root);
        long time4 = System.nanoTime() - startTime;
        
        System.out.println("BFS Single Queue: " + time1 + " ns");
        System.out.println("BFS Two Queues:   " + time2 + " ns");
        System.out.println("DFS Recursive:    " + time3 + " ns");
        System.out.println("BFS Sentinel:     " + time4 + " ns");
        
        // Verify all produce same result
        boolean allEqual = result1.equals(result2) && 
                          result1.equals(result3) && 
                          result1.equals(result4);
        System.out.println("All approaches produce same result: " + allEqual);
        
        // Memory usage analysis (theoretical)
        System.out.println("\nSpace Complexity Analysis:");
        System.out.println("BFS Single Queue: O(w) - w = maximum level width");
        System.out.println("BFS Two Queues:   O(w) - w = maximum level width");
        System.out.println("DFS Recursive:    O(h) - h = tree height");
        System.out.println("BFS Sentinel:     O(w) - w = maximum level width");
    }
    
    /**
     * Test various tree structures
     */
    public void testVariousTrees() {
        System.out.println("\nVarious Tree Structure Tests:");
        
        // Test 1: Complete binary tree
        System.out.println("\n1. Complete Binary Tree:");
        Integer[] completeTree = {1,2,3,4,5,6,7};
        TreeNode root1 = buildTree(completeTree);
        visualizeTraversal(root1, "BFS");
        analyzeTreeProperties(root1);
        
        // Test 2: Skewed tree (left)
        System.out.println("\n2. Left-Skewed Tree:");
        Integer[] leftSkewed = {1,2,null,3,null,4};
        TreeNode root2 = buildTree(leftSkewed);
        visualizeTraversal(root2, "BFS");
        analyzeTreeProperties(root2);
        
        // Test 3: Skewed tree (right)
        System.out.println("\n3. Right-Skewed Tree:");
        Integer[] rightSkewed = {1,null,2,null,3,null,4};
        TreeNode root3 = buildTree(rightSkewed);
        visualizeTraversal(root3, "BFS");
        analyzeTreeProperties(root3);
        
        // Test 4: Balanced but not complete
        System.out.println("\n4. Balanced Tree:");
        Integer[] balanced = {1,2,3,4,5,null,6,7};
        TreeNode root4 = buildTree(balanced);
        visualizeTraversal(root4, "BFS");
        analyzeTreeProperties(root4);
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Binary Tree Level Order Traversal");
        System.out.println("=========================================\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test 1: Example tree [3,9,20,null,null,15,7]");
        Integer[] nodes1 = {3,9,20,null,null,15,7};
        TreeNode root1 = solution.buildTree(nodes1);
        
        List<List<Integer>> result1a = solution.levelOrder(root1);
        List<List<Integer>> result1b = solution.levelOrderDFS(root1);
        List<List<Integer>> result1c = solution.levelOrderTwoQueues(root1);
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(3),
            Arrays.asList(9, 20),
            Arrays.asList(15, 7)
        );
        
        System.out.println("BFS Single Queue: " + result1a + " - " + 
                         (result1a.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("DFS Recursive:    " + result1b + " - " + 
                         (result1b.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("BFS Two Queues:   " + result1c + " - " + 
                         (result1c.equals(expected1) ? "PASSED" : "FAILED"));
        
        solution.visualizeTraversal(root1, "BFS");
        solution.analyzeTreeProperties(root1);
        
        // Test Case 2: Single node
        System.out.println("\nTest 2: Single node [1]");
        TreeNode root2 = new TreeNode(1);
        List<List<Integer>> result2 = solution.levelOrder(root2);
        System.out.println("Single node: " + result2 + " - " + 
                         (result2.equals(Arrays.asList(Arrays.asList(1))) ? "PASSED" : "FAILED"));
        
        // Test Case 3: Empty tree
        System.out.println("\nTest 3: Empty tree");
        TreeNode root3 = null;
        List<List<Integer>> result3 = solution.levelOrder(root3);
        System.out.println("Empty tree: " + result3 + " - " + 
                         (result3.equals(Arrays.asList()) ? "PASSED" : "FAILED"));
        
        // Test Case 4: Larger tree
        System.out.println("\nTest 4: Larger complete tree");
        Integer[] nodes4 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        TreeNode root4 = solution.buildTree(nodes4);
        List<List<Integer>> result4 = solution.levelOrder(root4);
        System.out.println("Larger tree levels: " + result4.size());
        System.out.println("Level sizes: " + solution.getLevelSizes(result4));
        
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
        System.out.println("LEVEL ORDER TRAVERSAL ALGORITHM EXPLANATION");
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
        solution.levelOrder(largeComplete);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.levelOrderDFS(largeComplete);
        long time2 = System.nanoTime() - startTime;
        
        System.out.println("BFS: " + time1 + " ns");
        System.out.println("DFS: " + time2 + " ns");
        
        System.out.println("\nLarge Skewed Tree Performance (10000 nodes):");
        
        startTime = System.nanoTime();
        solution.levelOrder(largeSkewed);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.levelOrderDFS(largeSkewed);
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
        System.out.println("\n1. BFS WITH SINGLE QUEUE (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     queue = [root]");
        System.out.println("     while queue not empty:");
        System.out.println("       levelSize = queue.size");
        System.out.println("       for i = 0 to levelSize:");
        System.out.println("         node = queue.dequeue()");
        System.out.println("         add node to current level");
        System.out.println("         enqueue node.left and node.right if not null");
        System.out.println("       add current level to result");
        System.out.println("   Key Features:");
        System.out.println("     - Natural level-by-level processing");
        System.out.println("     - O(n) time complexity");
        System.out.println("     - O(w) space complexity (w = maximum level width)");
        System.out.println("     - Most intuitive and efficient");
        
        System.out.println("\n2. DFS WITH LEVEL TRACKING:");
        System.out.println("   Algorithm:");
        System.out.println("     function dfs(node, level, result):");
        System.out.println("       if node is null: return");
        System.out.println("       if level >= result.size: add new level list");
        System.out.println("       result[level].add(node.val)");
        System.out.println("       dfs(node.left, level + 1, result)");
        System.out.println("       dfs(node.right, level + 1, result)");
        System.out.println("   Key Features:");
        System.out.println("     - Uses pre-order traversal");
        System.out.println("     - O(n) time complexity");
        System.out.println("     - O(h) space complexity (h = tree height)");
        System.out.println("     - Good for memory-constrained environments");
        
        System.out.println("\n3. KEY INSIGHTS:");
        System.out.println("   - BFS naturally processes level by level");
        System.out.println("   - Queue size at start of level = number of nodes at that level");
        System.out.println("   - Multiple valid implementations exist");
        System.out.println("   - Choice depends on specific constraints");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR LEVEL ORDER TRAVERSAL:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with BFS single queue approach (most common)");
        System.out.println("   - Mention DFS approach as alternative");
        System.out.println("   - Discuss trade-offs between BFS and DFS");
        System.out.println("   - Recommended order: BFS → DFS");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Level order = BFS traversal");
        System.out.println("   - Use queue for BFS implementation");
        System.out.println("   - Track level boundaries using queue size");
        System.out.println("   - Time complexity: O(n)");
        System.out.println("   - Space complexity: O(w) for BFS, O(h) for DFS");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Not handling empty tree case");
        System.out.println("   - Forgetting to check for null children");
        System.out.println("   - Incorrect level separation");
        System.out.println("   - Using stack instead of queue for BFS");
        System.out.println("   - Not maintaining left-to-right order");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - How to do bottom-up level order? (LeetCode 107)");
        System.out.println("   - How to do zigzag level order? (LeetCode 103)");
        System.out.println("   - How to get right side view? (LeetCode 199)");
        System.out.println("   - How to calculate level averages? (LeetCode 637)");
        System.out.println("   - How to handle N-ary trees? (LeetCode 429)");
        
        System.out.println("\n5. Related Problems:");
        System.out.println("   - 107. Binary Tree Level Order Traversal II");
        System.out.println("   - 103. Binary Tree Zigzag Level Order Traversal");
        System.out.println("   - 199. Binary Tree Right Side View");
        System.out.println("   - 637. Average of Levels in Binary Tree");
        System.out.println("   - 429. N-ary Tree Level Order Traversal");
        
        System.out.println("\n6. Code Pattern to Remember:");
        System.out.println("   public List<List<Integer>> levelOrder(TreeNode root) {");
        System.out.println("     List<List<Integer>> result = new ArrayList<>();");
        System.out.println("     if (root == null) return result;");
        System.out.println("     Queue<TreeNode> queue = new LinkedList<>();");
        System.out.println("     queue.offer(root);");
        System.out.println("     while (!queue.isEmpty()) {");
        System.out.println("       int levelSize = queue.size();");
        System.out.println("       List<Integer> level = new ArrayList<>();");
        System.out.println("       for (int i = 0; i < levelSize; i++) {");
        System.out.println("         TreeNode node = queue.poll();");
        System.out.println("         level.add(node.val);");
        System.out.println("         if (node.left != null) queue.offer(node.left);");
        System.out.println("         if (node.right != null) queue.offer(node.right);");
        System.out.println("       }");
        System.out.println("       result.add(level);");
        System.out.println("     }");
        System.out.println("     return result;");
        System.out.println("   }");
        
        System.out.println("\n7. Real-world Applications:");
        System.out.println("   - Printing organizational charts");
        System.out.println("   - File system directory listing");
        System.out.println("   - Network broadcasting (level by level)");
        System.out.println("   - Game tree exploration (e.g., chess)");
        System.out.println("   - Social network friend recommendations");
    }
}
