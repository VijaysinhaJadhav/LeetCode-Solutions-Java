
## Solution.java

```java
/**
 * 104. Maximum Depth of Binary Tree
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given the root of a binary tree, return its maximum depth.
 * Maximum depth is the number of nodes along the longest path from root to farthest leaf.
 * 
 * Key Insights:
 * 1. Maximum depth = height of the tree
 * 2. Multiple approaches: recursive DFS, iterative BFS, iterative DFS
 * 3. Recursive: max depth = 1 + max(left depth, right depth)
 * 4. BFS: count levels using queue
 * 5. DFS: use stack to track nodes and depths
 * 
 * Approaches:
 * 1. Recursive DFS - Most intuitive and concise
 * 2. Iterative BFS - Natural level counting
 * 3. Iterative DFS - Stack-based approach
 * 4. Post-order DFS - Alternative recursive approach
 * 
 * Time Complexity: O(n) for all approaches
 * Space Complexity: O(h) for DFS, O(w) for BFS where h=height, w=width
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
     * Approach 1: Recursive DFS - RECOMMENDED for interviews
     * Time: O(n), Space: O(h) where h is tree height
     * Algorithm:
     * 1. Base case: if node is null, return 0
     * 2. Recursively find depth of left subtree
     * 3. Recursively find depth of right subtree
     * 4. Return 1 + max(leftDepth, rightDepth)
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        return 1 + Math.max(leftDepth, rightDepth);
    }
    
    /**
     * Approach 2: Recursive DFS (One-liner)
     * Compact version of the recursive approach
     */
    public int maxDepthOneLiner(TreeNode root) {
        return root == null ? 0 : 1 + Math.max(maxDepthOneLiner(root.left), maxDepthOneLiner(root.right));
    }
    
    /**
     * Approach 3: Iterative BFS (Level Order) - RECOMMENDED for interviews
     * Time: O(n), Space: O(w) where w is maximum level width
     * Algorithm:
     * 1. Use queue for level order traversal
     * 2. For each level, increment depth counter
     * 3. Process all nodes at current level before moving to next
     */
    public int maxDepthBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            depth++;
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        
        return depth;
    }
    
    /**
     * Approach 4: Iterative DFS using Stack
     * Time: O(n), Space: O(h) where h is tree height
     * Algorithm:
     * 1. Use stack to store nodes and their depths
     * 2. Track maximum depth encountered
     * 3. Push children with increased depth
     */
    public int maxDepthDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        nodeStack.push(root);
        depthStack.push(1);
        int maxDepth = 0;
        
        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            int currentDepth = depthStack.pop();
            maxDepth = Math.max(maxDepth, currentDepth);
            
            if (node.left != null) {
                nodeStack.push(node.left);
                depthStack.push(currentDepth + 1);
            }
            if (node.right != null) {
                nodeStack.push(node.right);
                depthStack.push(currentDepth + 1);
            }
        }
        
        return maxDepth;
    }
    
    /**
     * Approach 5: Iterative DFS with Pair Class
     * More object-oriented approach using custom pair class
     */
    public int maxDepthDFSPair(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair(root, 1));
        int maxDepth = 0;
        
        while (!stack.isEmpty()) {
            Pair current = stack.pop();
            TreeNode node = current.node;
            int currentDepth = current.depth;
            
            maxDepth = Math.max(maxDepth, currentDepth);
            
            if (node.left != null) {
                stack.push(new Pair(node.left, currentDepth + 1));
            }
            if (node.right != null) {
                stack.push(new Pair(node.right, currentDepth + 1));
            }
        }
        
        return maxDepth;
    }
    
    /**
     * Helper class for DFS with Pair approach
     */
    private class Pair {
        TreeNode node;
        int depth;
        
        Pair(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
    
    /**
     * Approach 6: Post-order DFS using Single Stack
     * Alternative iterative approach mimicking post-order traversal
     */
    public int maxDepthPostorder(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        int maxDepth = 0;
        int currentDepth = 0;
        
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                currentDepth++;
                maxDepth = Math.max(maxDepth, currentDepth);
                current = current.left;
            } else {
                TreeNode peekNode = stack.peek();
                if (peekNode.right != null && peekNode.right != lastVisited) {
                    current = peekNode.right;
                } else {
                    lastVisited = stack.pop();
                    currentDepth--;
                }
            }
        }
        
        return maxDepth;
    }
    
    /**
     * Approach 7: Global Variable Recursive
     * Alternative recursive approach using global variable
     */
    private int maxDepthGlobal = 0;
    
    public int maxDepthGlobal(TreeNode root) {
        maxDepthGlobal = 0;
        maxDepthHelper(root, 1);
        return maxDepthGlobal;
    }
    
    private void maxDepthHelper(TreeNode node, int currentDepth) {
        if (node == null) {
            return;
        }
        
        maxDepthGlobal = Math.max(maxDepthGlobal, currentDepth);
        
        maxDepthHelper(node.left, currentDepth + 1);
        maxDepthHelper(node.right, currentDepth + 1);
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
     * Visualize depth calculation process
     */
    public void visualizeDepthCalculation(TreeNode root, String approach) {
        System.out.println("\n" + approach + " Depth Calculation:");
        System.out.println("Tree Structure:");
        printTree(root, 0);
        
        int depth;
        switch (approach) {
            case "Recursive":
                depth = maxDepth(root);
                break;
            case "BFS":
                depth = maxDepthBFS(root);
                break;
            case "DFS":
                depth = maxDepthDFS(root);
                break;
            default:
                depth = maxDepth(root);
        }
        
        System.out.println("Maximum Depth: " + depth);
        System.out.println("Longest Path: " + findLongestPath(root));
        
        if (approach.equals("BFS")) {
            visualizeBFSProcess(root);
        } else if (approach.equals("Recursive")) {
            visualizeRecursiveProcess(root);
        }
    }
    
    private void printTree(TreeNode root, int depth) {
        if (root == null) {
            printIndent(depth);
            System.out.println("null");
            return;
        }
        
        printIndent(depth);
        System.out.println(root.val + " (depth: " + (depth + 1) + ")");
        
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
     * Find and return the longest path from root to leaf
     */
    private List<Integer> findLongestPath(TreeNode root) {
        List<Integer> path = new ArrayList<>();
        findLongestPathHelper(root, new ArrayList<>(), path);
        return path;
    }
    
    private void findLongestPathHelper(TreeNode node, List<Integer> currentPath, List<Integer> longestPath) {
        if (node == null) {
            return;
        }
        
        currentPath.add(node.val);
        
        if (node.left == null && node.right == null) {
            // Leaf node
            if (currentPath.size() > longestPath.size()) {
                longestPath.clear();
                longestPath.addAll(currentPath);
            }
        } else {
            findLongestPathHelper(node.left, currentPath, longestPath);
            findLongestPathHelper(node.right, currentPath, longestPath);
        }
        
        currentPath.remove(currentPath.size() - 1);
    }
    
    /**
     * Visualize BFS level-by-level process
     */
    private void visualizeBFSProcess(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        System.out.println("\nBFS Level-by-Level Process:");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            depth++;
            
            System.out.println("Level " + depth + " (" + levelSize + " nodes):");
            List<Integer> levelNodes = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelNodes.add(node.val);
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            System.out.println("  Nodes: " + levelNodes);
        }
        
        System.out.println("Total Levels (Depth): " + depth);
    }
    
    /**
     * Visualize recursive depth calculation
     */
    private void visualizeRecursiveProcess(TreeNode root) {
        System.out.println("\nRecursive Depth Calculation Process:");
        visualizeRecursiveHelper(root, 1);
    }
    
    private int visualizeRecursiveHelper(TreeNode node, int depth) {
        if (node == null) {
            System.out.println("  ".repeat(depth - 1) + "Node: null → Depth: 0");
            return 0;
        }
        
        System.out.println("  ".repeat(depth - 1) + "Node: " + node.val + " (current depth: " + depth + ")");
        
        int leftDepth = visualizeRecursiveHelper(node.left, depth + 1);
        int rightDepth = visualizeRecursiveHelper(node.right, depth + 1);
        
        int maxChildDepth = Math.max(leftDepth, rightDepth);
        int result = 1 + maxChildDepth;
        
        System.out.println("  ".repeat(depth - 1) + "Node: " + node.val + 
                          " → max(" + leftDepth + ", " + rightDepth + ") + 1 = " + result);
        
        return result;
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Maximum Depth of Binary Tree");
        System.out.println("====================================\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test 1: Complete binary tree [3,9,20,null,null,15,7]");
        Integer[] nodes1 = {3,9,20,null,null,15,7};
        TreeNode root1 = solution.buildTree(nodes1);
        
        int result1a = solution.maxDepth(root1);
        int result1b = solution.maxDepthBFS(root1);
        int result1c = solution.maxDepthDFS(root1);
        int expected1 = 3;
        
        System.out.println("Recursive: " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("BFS:       " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("DFS:       " + result1c + " - " + 
                         (result1c == expected1 ? "PASSED" : "FAILED"));
        
        solution.visualizeDepthCalculation(root1, "Recursive");
        
        // Test Case 2: Empty tree
        System.out.println("\nTest 2: Empty tree");
        TreeNode root2 = null;
        int result2 = solution.maxDepth(root2);
        System.out.println("Empty tree: " + result2 + " - " + 
                         (result2 == 0 ? "PASSED" : "FAILED"));
        
        // Test Case 3: Single node
        System.out.println("\nTest 3: Single node [1]");
        Integer[] nodes3 = {1};
        TreeNode root3 = solution.buildTree(nodes3);
        int result3 = solution.maxDepth(root3);
        System.out.println("Single node: " + result3 + " - " + 
                         (result3 == 1 ? "PASSED" : "FAILED"));
        
        // Test Case 4: Left-skewed tree
        System.out.println("\nTest 4: Left-skewed tree [1,2,null,3,null,4]");
        Integer[] nodes4 = {1,2,null,3,null,4};
        TreeNode root4 = solution.buildTree(nodes4);
        int result4 = solution.maxDepth(root4);
        System.out.println("Left-skewed: " + result4 + " - " + 
                         (result4 == 4 ? "PASSED" : "FAILED"));
        
        // Test Case 5: Right-skewed tree
        System.out.println("\nTest 5: Right-skewed tree [1,null,2,null,3,null,4]");
        Integer[] nodes5 = {1,null,2,null,3,null,4};
        TreeNode root5 = solution.buildTree(nodes5);
        int result5 = solution.maxDepth(root5);
        System.out.println("Right-skewed: " + result5 + " - " + 
                         (result5 == 4 ? "PASSED" : "FAILED"));
        
        // Test Case 6: Balanced tree
        System.out.println("\nTest 6: Perfect binary tree [1,2,3,4,5,6,7]");
        Integer[] nodes6 = {1,2,3,4,5,6,7};
        TreeNode root6 = solution.buildTree(nodes6);
        int result6 = solution.maxDepth(root6);
        System.out.println("Balanced tree: " + result6 + " - " + 
                         (result6 == 3 ? "PASSED" : "FAILED"));
        
        solution.visualizeDepthCalculation(root6, "BFS");
        
        // Test Case 7: Complex tree
        System.out.println("\nTest 7: Complex tree [1,2,3,4,null,null,5,6,null,7]");
        Integer[] nodes7 = {1,2,3,4,null,null,5,6,null,7};
        TreeNode root7 = solution.buildTree(nodes7);
        int result7 = solution.maxDepth(root7);
        System.out.println("Complex tree: " + result7 + " - " + 
                         (result7 == 4 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MAXIMUM DEPTH ALGORITHM EXPLANATION");
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
        TreeNode largeTree = createBalancedTree(10000);
        
        // Warm up
        solution.maxDepth(largeTree);
        solution.maxDepthBFS(largeTree);
        solution.maxDepthDFS(largeTree);
        
        // Test recursive
        long startTime = System.nanoTime();
        int result1 = solution.maxDepth(largeTree);
        long time1 = System.nanoTime() - startTime;
        
        // Test BFS
        startTime = System.nanoTime();
        int result2 = solution.maxDepthBFS(largeTree);
        long time2 = System.nanoTime() - startTime;
        
        // Test DFS
        startTime = System.nanoTime();
        int result3 = solution.maxDepthDFS(largeTree);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Performance Results (10000 nodes):");
        System.out.println("Recursive: " + time1 + " ns → Depth: " + result1);
        System.out.println("BFS:       " + time2 + " ns → Depth: " + result2);
        System.out.println("DFS:       " + time3 + " ns → Depth: " + result3);
        
        // Verify all produce same result
        boolean allEqual = result1 == result2 && result1 == result3;
        System.out.println("All approaches consistent: " + allEqual);
        
        // Test edge cases performance
        System.out.println("\nEdge Cases Performance:");
        
        // Skewed tree
        TreeNode skewedTree = createSkewedTree(1000, true);
        startTime = System.nanoTime();
        solution.maxDepth(skewedTree);
        long skewedTime = System.nanoTime() - startTime;
        System.out.println("Skewed tree (1000 nodes): " + skewedTime + " ns");
        
        // Wide tree
        TreeNode wideTree = createWideTree(5); // 2^5 - 1 = 31 nodes but wide
        startTime = System.nanoTime();
        solution.maxDepthBFS(wideTree);
        long wideTime = System.nanoTime() - startTime;
        System.out.println("Wide tree (31 nodes): " + wideTime + " ns");
    }
    
    /**
     * Create a balanced binary tree for testing
     */
    private static TreeNode createBalancedTree(int size) {
        if (size <= 0) return null;
        return createBalancedTreeHelper(1, size);
    }
    
    private static TreeNode createBalancedTreeHelper(int start, int end) {
        if (start > end) return null;
        
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(mid);
        root.left = createBalancedTreeHelper(start, mid - 1);
        root.right = createBalancedTreeHelper(mid + 1, end);
        return root;
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
     * Create a wide tree for testing
     */
    private static TreeNode createWideTree(int levels) {
        if (levels <= 0) return null;
        
        TreeNode root = new TreeNode(1);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int value = 2;
        
        for (int level = 1; level < levels; level++) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                node.left = new TreeNode(value++);
                node.right = new TreeNode(value++);
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        
        return root;
    }
    
    /**
     * Detailed algorithm explanations
     */
    private static void explainAlgorithms() {
        System.out.println("\n1. RECURSIVE DFS APPROACH (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     maxDepth(node):");
        System.out.println("       if node is null: return 0");
        System.out.println("       leftDepth = maxDepth(node.left)");
        System.out.println("       rightDepth = maxDepth(node.right)");
        System.out.println("       return 1 + max(leftDepth, rightDepth)");
        System.out.println("   Time: O(n) - Visit each node once");
        System.out.println("   Space: O(h) - Recursion stack, h = tree height");
        System.out.println("   Pros: Simple, elegant, intuitive");
        System.out.println("   Cons: Recursion limits for very deep trees");
        
        System.out.println("\n2. ITERATIVE BFS APPROACH (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     queue = [root], depth = 0");
        System.out.println("     while queue not empty:");
        System.out.println("       levelSize = queue.size");
        System.out.println("       depth++");
        System.out.println("       for i = 0 to levelSize:");
        System.out.println("         node = queue.dequeue()");
        System.out.println("         enqueue node.left and node.right if not null");
        System.out.println("   Time: O(n) - Visit each node once");
        System.out.println("   Space: O(w) - Queue, w = maximum level width");
        System.out.println("   Pros: Natural level counting, no recursion");
        System.out.println("   Cons: More memory for wide trees");
        
        System.out.println("\n3. ITERATIVE DFS APPROACH:");
        System.out.println("   Algorithm:");
        System.out.println("     stack = [(root, 1)], maxDepth = 0");
        System.out.println("     while stack not empty:");
        System.out.println("       (node, depth) = stack.pop()");
        System.out.println("       maxDepth = max(maxDepth, depth)");
        System.out.println("       push children with depth + 1");
        System.out.println("   Time: O(n) - Visit each node once");
        System.out.println("   Space: O(h) - Stack, h = tree height");
        System.out.println("   Pros: Similar to recursive but iterative");
        System.out.println("   Cons: More complex implementation");
        
        System.out.println("\n4. KEY INSIGHTS:");
        System.out.println("   - Maximum depth = height of the tree");
        System.out.println("   - Leaf nodes contribute to depth measurement");
        System.out.println("   - Empty tree has depth 0");
        System.out.println("   - Single node tree has depth 1");
        System.out.println("   - All approaches have O(n) time complexity");
        
        System.out.println("\n5. REAL-WORLD APPLICATIONS:");
        System.out.println("   - File system directory depth analysis");
        System.out.println("   - Organizational hierarchy depth");
        System.out.println("   - Game tree analysis (chess, AI)");
        System.out.println("   - Database index height optimization");
        System.out.println("   - Network routing path length");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR MAXIMUM DEPTH:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with recursive: most intuitive and concise");
        System.out.println("   - Then BFS iterative: demonstrates level-order thinking");
        System.out.println("   - Mention DFS iterative: shows stack-based approach");
        System.out.println("   - Recommended order: Recursive → BFS → DFS");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Time complexity: O(n) for all approaches");
        System.out.println("   - Space complexity: O(h) vs O(w) trade-offs");
        System.out.println("   - Definition: maximum depth = longest root-to-leaf path");
        System.out.println("   - Edge cases: empty tree, single node, skewed trees");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Returning 1 for empty tree (should be 0)");
        System.out.println("   - Not handling null children properly");
        System.out.println("   - Confusing depth with number of edges vs nodes");
        System.out.println("   - Infinite recursion for cyclic structures");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - Minimum depth of binary tree (LeetCode 111)");
        System.out.println("   - Check if tree is balanced (LeetCode 110)");
        System.out.println("   - Diameter of binary tree (LeetCode 543)");
        System.out.println("   - Average of levels in binary tree");
        System.out.println("   - Maximum depth of N-ary tree");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - File system directory structure analysis");
        System.out.println("   - Organizational chart depth analysis");
        System.out.println("   - Game decision tree analysis");
        System.out.println("   - Database B-tree height optimization");
        System.out.println("   - Network topology depth measurement");
        
        System.out.println("\n6. Practice Problems:");
        System.out.println("   - 111. Minimum Depth of Binary Tree");
        System.out.println("   - 110. Balanced Binary Tree");
        System.out.println("   - 543. Diameter of Binary Tree");
        System.out.println("   - 559. Maximum Depth of N-ary Tree");
        System.out.println("   - 687. Longest Univalue Path");
        
        System.out.println("\n7. Code Patterns to Remember:");
        System.out.println("   Recursive:");
        System.out.println("     if (root == null) return 0;");
        System.out.println("     int left = maxDepth(root.left);");
        System.out.println("     int right = maxDepth(root.right);");
        System.out.println("     return 1 + Math.max(left, right);");
        System.out.println("   BFS:");
        System.out.println("     Queue<TreeNode> queue = new LinkedList<>();");
        System.out.println("     queue.offer(root);");
        System.out.println("     int depth = 0;");
        System.out.println("     while (!queue.isEmpty()) {");
        System.out.println("       int size = queue.size();");
        System.out.println("       depth++;");
        System.out.println("       for (int i = 0; i < size; i++) {");
        System.out.println("         TreeNode node = queue.poll();");
        System.out.println("         if (node.left != null) queue.offer(node.left);");
        System.out.println("         if (node.right != null) queue.offer(node.right);");
        System.out.println("       }");
        System.out.println("     }");
    }
}
