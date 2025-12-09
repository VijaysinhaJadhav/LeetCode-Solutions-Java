
## Solution.java

```java
/**
 * 100. Same Tree
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given the roots of two binary trees p and q, check if they are the same.
 * Two binary trees are the same if they are structurally identical and nodes have the same values.
 * 
 * Key Insights:
 * 1. Check if both trees are null → same
 * 2. Check if one tree is null and other isn't → different
 * 3. Check if current node values are different → different
 * 4. Recursively check left and right subtrees
 * 
 * Approaches:
 * 1. Recursive DFS - RECOMMENDED for interviews
 * 2. Iterative DFS - Stack-based approach
 * 3. Iterative BFS - Level-order comparison
 * 4. Morris Traversal - O(1) space (complex)
 * 
 * Time Complexity: O(n) for all approaches
 * Space Complexity: O(h) where h is tree height
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
     * 1. If both nodes are null → return true
     * 2. If one node is null and other isn't → return false
     * 3. If values are different → return false
     * 4. Recursively check left and right subtrees
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Both trees are empty
        if (p == null && q == null) {
            return true;
        }
        
        // One tree is empty, other isn't
        if (p == null || q == null) {
            return false;
        }
        
        // Values are different
        if (p.val != q.val) {
            return false;
        }
        
        // Recursively check left and right subtrees
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    
    /**
     * Approach 2: Recursive DFS (One-liner)
     * Compact version of the recursive approach
     */
    public boolean isSameTreeOneLiner(TreeNode p, TreeNode q) {
        return (p == null && q == null) || 
               (p != null && q != null && 
                p.val == q.val && 
                isSameTreeOneLiner(p.left, q.left) && 
                isSameTreeOneLiner(p.right, q.right));
    }
    
    /**
     * Approach 3: Iterative DFS using Stack
     * Time: O(n), Space: O(h) where h is tree height
     * Algorithm:
     * 1. Use stack to store pairs of nodes to compare
     * 2. Push root pairs to stack
     * 3. While stack not empty, pop pairs and compare
     * 4. Push child pairs for further comparison
     */
    public boolean isSameTreeDFS(TreeNode p, TreeNode q) {
        Stack<TreeNode[]> stack = new Stack<>();
        stack.push(new TreeNode[]{p, q});
        
        while (!stack.isEmpty()) {
            TreeNode[] nodes = stack.pop();
            TreeNode node1 = nodes[0];
            TreeNode node2 = nodes[1];
            
            // Both null - continue
            if (node1 == null && node2 == null) {
                continue;
            }
            
            // One null, other not null
            if (node1 == null || node2 == null) {
                return false;
            }
            
            // Values different
            if (node1.val != node2.val) {
                return false;
            }
            
            // Push children in pairs
            stack.push(new TreeNode[]{node1.right, node2.right});
            stack.push(new TreeNode[]{node1.left, node2.left});
        }
        
        return true;
    }
    
    /**
     * Approach 4: Iterative BFS using Queue
     * Time: O(n), Space: O(w) where w is maximum level width
     * Algorithm:
     * 1. Use queue to store pairs of nodes to compare
     * 2. Enqueue root pairs
     * 3. While queue not empty, dequeue pairs and compare
     * 4. Enqueue child pairs for further comparison
     */
    public boolean isSameTreeBFS(TreeNode p, TreeNode q) {
        Queue<TreeNode[]> queue = new LinkedList<>();
        queue.offer(new TreeNode[]{p, q});
        
        while (!queue.isEmpty()) {
            TreeNode[] nodes = queue.poll();
            TreeNode node1 = nodes[0];
            TreeNode node2 = nodes[1];
            
            // Both null - continue
            if (node1 == null && node2 == null) {
                continue;
            }
            
            // One null, other not null
            if (node1 == null || node2 == null) {
                return false;
            }
            
            // Values different
            if (node1.val != node2.val) {
                return false;
            }
            
            // Enqueue children in pairs
            queue.offer(new TreeNode[]{node1.left, node2.left});
            queue.offer(new TreeNode[]{node1.right, node2.right});
        }
        
        return true;
    }
    
    /**
     * Approach 5: Iterative DFS with Deque
     * Alternative iterative approach using Deque
     */
    public boolean isSameTreeDeque(TreeNode p, TreeNode q) {
        Deque<TreeNode[]> deque = new LinkedList<>();
        deque.addLast(new TreeNode[]{p, q});
        
        while (!deque.isEmpty()) {
            TreeNode[] nodes = deque.removeFirst();
            TreeNode node1 = nodes[0];
            TreeNode node2 = nodes[1];
            
            if (node1 == null && node2 == null) {
                continue;
            }
            if (node1 == null || node2 == null) {
                return false;
            }
            if (node1.val != node2.val) {
                return false;
            }
            
            deque.addLast(new TreeNode[]{node1.left, node2.left});
            deque.addLast(new TreeNode[]{node1.right, node2.right});
        }
        
        return true;
    }
    
    /**
     * Approach 6: Morris Traversal - O(1) space
     * Time: O(n), Space: O(1)
     * Most complex approach, modifies trees temporarily
     * Compares trees using Morris inorder traversal
     */
    public boolean isSameTreeMorris(TreeNode p, TreeNode q) {
        TreeNode current1 = p, current2 = q;
        
        while (current1 != null && current2 != null) {
            if (current1.val != current2.val) {
                return false;
            }
            
            // Handle left children using Morris traversal
            if (current1.left == null && current2.left == null) {
                // Both have no left children, move to right
                current1 = current1.right;
                current2 = current2.right;
            } else if (current1.left != null && current2.left != null) {
                // Both have left children, find predecessors
                TreeNode pred1 = current1.left;
                TreeNode pred2 = current2.left;
                
                while (pred1.right != null && pred1.right != current1 && 
                       pred2.right != null && pred2.right != current2) {
                    pred1 = pred1.right;
                    pred2 = pred2.right;
                }
                
                if (pred1.right == null && pred2.right == null) {
                    // Create threads and move left
                    pred1.right = current1;
                    pred2.right = current2;
                    current1 = current1.left;
                    current2 = current2.left;
                } else if (pred1.right == current1 && pred2.right == current2) {
                    // Remove threads and move right
                    pred1.right = null;
                    pred2.right = null;
                    current1 = current1.right;
                    current2 = current2.right;
                } else {
                    // Structure mismatch
                    return false;
                }
            } else {
                // One has left child, other doesn't
                return false;
            }
        }
        
        // Check if both reached end simultaneously
        return current1 == null && current2 == null;
    }
    
    /**
     * Approach 7: Enhanced DFS with Detailed Reporting
     * Provides detailed information about where trees differ
     */
    public SameTreeResult isSameTreeDetailed(TreeNode p, TreeNode q) {
        return checkSameTreeDetailed(p, q, "root");
    }
    
    private SameTreeResult checkSameTreeDetailed(TreeNode p, TreeNode q, String position) {
        // Both trees are empty
        if (p == null && q == null) {
            return new SameTreeResult(true, position + ": Both null - SAME");
        }
        
        // One tree is empty, other isn't
        if (p == null || q == null) {
            String message = position + ": Structure different - " +
                           (p == null ? "First tree null, second tree has node" : 
                            "Second tree null, first tree has node");
            return new SameTreeResult(false, message);
        }
        
        // Values are different
        if (p.val != q.val) {
            String message = position + ": Values different - " +
                           p.val + " vs " + q.val;
            return new SameTreeResult(false, message);
        }
        
        // Check left subtree
        SameTreeResult leftResult = checkSameTreeDetailed(p.left, q.left, position + ".left");
        if (!leftResult.isSame) {
            return leftResult;
        }
        
        // Check right subtree
        SameTreeResult rightResult = checkSameTreeDetailed(p.right, q.right, position + ".right");
        if (!rightResult.isSame) {
            return rightResult;
        }
        
        return new SameTreeResult(true, position + ": All checks passed - SAME");
    }
    
    /**
     * Helper class for Approach 7
     */
    private class SameTreeResult {
        boolean isSame;
        String message;
        
        SameTreeResult(boolean isSame, String message) {
            this.isSame = isSame;
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
     * Visualize tree comparison process
     */
    public void visualizeComparison(TreeNode p, TreeNode q, String approach) {
        System.out.println("\n" + approach + " Tree Comparison:");
        System.out.println("Tree 1:");
        printTree(p, 0);
        System.out.println("Tree 2:");
        printTree(q, 0);
        
        boolean result;
        switch (approach) {
            case "Recursive":
                result = isSameTree(p, q);
                break;
            case "DFS":
                result = isSameTreeDFS(p, q);
                break;
            case "BFS":
                result = isSameTreeBFS(p, q);
                break;
            default:
                result = isSameTree(p, q);
        }
        
        System.out.println("Result: " + (result ? "SAME" : "DIFFERENT"));
        
        if (approach.equals("Recursive")) {
            System.out.println("\nDetailed Comparison Process:");
            visualizeRecursiveProcess(p, q, "root");
        } else if (approach.equals("BFS")) {
            System.out.println("\nBFS Level-by-Level Comparison:");
            visualizeBFSProcess(p, q);
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
     * Visualize recursive comparison process step by step
     */
    private boolean visualizeRecursiveProcess(TreeNode p, TreeNode q, String position) {
        System.out.println("Checking " + position + ":");
        
        if (p == null && q == null) {
            System.out.println("  Both null → SAME");
            return true;
        }
        
        if (p == null || q == null) {
            System.out.println("  Structure different:");
            System.out.println("    Tree1: " + (p == null ? "null" : p.val));
            System.out.println("    Tree2: " + (q == null ? "null" : q.val));
            System.out.println("  → DIFFERENT");
            return false;
        }
        
        System.out.println("  Values: " + p.val + " vs " + q.val);
        
        if (p.val != q.val) {
            System.out.println("  Values different → DIFFERENT");
            return false;
        }
        
        System.out.println("  Values same, checking children...");
        
        boolean leftSame = visualizeRecursiveProcess(p.left, q.left, position + ".left");
        boolean rightSame = visualizeRecursiveProcess(p.right, q.right, position + ".right");
        
        boolean result = leftSame && rightSame;
        System.out.println(position + " final result: " + (result ? "SAME" : "DIFFERENT"));
        
        return result;
    }
    
    /**
     * Visualize BFS level-by-level comparison
     */
    private void visualizeBFSProcess(TreeNode p, TreeNode q) {
        Queue<TreeNode[]> queue = new LinkedList<>();
        queue.offer(new TreeNode[]{p, q});
        int level = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.println("Level " + level + ":");
            level++;
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode[] nodes = queue.poll();
                TreeNode node1 = nodes[0];
                TreeNode node2 = nodes[1];
                
                String node1Str = node1 == null ? "null" : String.valueOf(node1.val);
                String node2Str = node2 == null ? "null" : String.valueOf(node2.val);
                
                System.out.println("  Compare: " + node1Str + " vs " + node2Str + 
                                 " → " + (node1Str.equals(node2Str) ? "SAME" : "DIFFERENT"));
                
                if (node1 != null && node2 != null) {
                    queue.offer(new TreeNode[]{node1.left, node2.left});
                    queue.offer(new TreeNode[]{node1.right, node2.right});
                }
            }
        }
    }
    
    /**
     * Find and report differences between trees
     */
    public void findDifferences(TreeNode p, TreeNode q) {
        System.out.println("\nFinding Differences:");
        SameTreeResult result = isSameTreeDetailed(p, q);
        
        if (result.isSame) {
            System.out.println("No differences found - trees are identical");
        } else {
            System.out.println("First difference found:");
            System.out.println("  " + result.message);
        }
    }
    
    /**
     * Compare tree properties
     */
    public void compareTreeProperties(TreeNode p, TreeNode q) {
        System.out.println("\nTree Properties Comparison:");
        
        int height1 = calculateHeight(p);
        int height2 = calculateHeight(q);
        int nodes1 = countNodes(p);
        int nodes2 = countNodes(q);
        
        System.out.println("Tree 1 - Height: " + height1 + ", Nodes: " + nodes1);
        System.out.println("Tree 2 - Height: " + height2 + ", Nodes: " + nodes2);
        System.out.println("Height same: " + (height1 == height2));
        System.out.println("Node count same: " + (nodes1 == nodes2));
        
        if (height1 != height2) {
            System.out.println("Height difference: " + Math.abs(height1 - height2));
        }
        if (nodes1 != nodes2) {
            System.out.println("Node count difference: " + Math.abs(nodes1 - nodes2));
        }
    }
    
    private int calculateHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }
    
    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Same Tree");
        System.out.println("=================\n");
        
        // Test Case 1: Identical trees
        System.out.println("Test 1: Identical trees [1,2,3]");
        Integer[] nodes1 = {1,2,3};
        Integer[] nodes2 = {1,2,3};
        TreeNode p1 = solution.buildTree(nodes1);
        TreeNode q1 = solution.buildTree(nodes2);
        
        boolean result1a = solution.isSameTree(p1, q1);
        boolean result1b = solution.isSameTreeDFS(p1, q1);
        boolean result1c = solution.isSameTreeBFS(p1, q1);
        boolean expected1 = true;
        
        System.out.println("Recursive: " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("DFS:       " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("BFS:       " + result1c + " - " + 
                         (result1c == expected1 ? "PASSED" : "FAILED"));
        
        solution.visualizeComparison(p1, q1, "Recursive");
        
        // Test Case 2: Different structure
        System.out.println("\nTest 2: Different structure [1,2] vs [1,null,2]");
        Integer[] nodes3 = {1,2};
        Integer[] nodes4 = {1,null,2};
        TreeNode p2 = solution.buildTree(nodes3);
        TreeNode q2 = solution.buildTree(nodes4);
        
        boolean result2 = solution.isSameTree(p2, q2);
        System.out.println("Different structure: " + result2 + " - " + 
                         (result2 == false ? "PASSED" : "FAILED"));
        
        solution.visualizeComparison(p2, q2, "BFS");
        solution.findDifferences(p2, q2);
        
        // Test Case 3: Different values
        System.out.println("\nTest 3: Different values [1,2,1] vs [1,1,2]");
        Integer[] nodes5 = {1,2,1};
        Integer[] nodes6 = {1,1,2};
        TreeNode p3 = solution.buildTree(nodes5);
        TreeNode q3 = solution.buildTree(nodes6);
        
        boolean result3 = solution.isSameTree(p3, q3);
        System.out.println("Different values: " + result3 + " - " + 
                         (result3 == false ? "PASSED" : "FAILED"));
        
        solution.findDifferences(p3, q3);
        
        // Test Case 4: Empty trees
        System.out.println("\nTest 4: Both trees empty");
        TreeNode p4 = null;
        TreeNode q4 = null;
        boolean result4 = solution.isSameTree(p4, q4);
        System.out.println("Both empty: " + result4 + " - " + 
                         (result4 == true ? "PASSED" : "FAILED"));
        
        // Test Case 5: One tree empty
        System.out.println("\nTest 5: One tree empty, other not");
        Integer[] nodes7 = {1};
        TreeNode p5 = solution.buildTree(nodes7);
        TreeNode q5 = null;
        boolean result5 = solution.isSameTree(p5, q5);
        System.out.println("One empty: " + result5 + " - " + 
                         (result5 == false ? "PASSED" : "FAILED"));
        
        // Test Case 6: Complex identical trees
        System.out.println("\nTest 6: Complex identical trees");
        Integer[] nodes8 = {1,2,3,4,5,6,7,8,9,10};
        Integer[] nodes9 = {1,2,3,4,5,6,7,8,9,10};
        TreeNode p6 = solution.buildTree(nodes8);
        TreeNode q6 = solution.buildTree(nodes9);
        
        boolean result6 = solution.isSameTree(p6, q6);
        System.out.println("Complex identical: " + result6 + " - " + 
                         (result6 == true ? "PASSED" : "FAILED"));
        
        solution.compareTreeProperties(p6, q6);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SAME TREE ALGORITHM EXPLANATION");
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
        System.out.println("Creating large identical trees for performance testing...");
        
        // Create large identical trees
        TreeNode largeTree1 = createBalancedTree(12); // ~4000 nodes
        TreeNode largeTree2 = createBalancedTree(12); // ~4000 nodes
        
        // Create trees that differ early
        TreeNode diffEarly1 = createBalancedTree(10);
        TreeNode diffEarly2 = createBalancedTree(10);
        // Modify root value to differ immediately
        if (diffEarly2 != null) diffEarly2.val = -1;
        
        // Test identical trees
        System.out.println("\nIdentical Trees Performance (~4000 nodes):");
        
        long startTime = System.nanoTime();
        boolean result1 = solution.isSameTree(largeTree1, largeTree2);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result2 = solution.isSameTreeDFS(largeTree1, largeTree2);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result3 = solution.isSameTreeBFS(largeTree1, largeTree2);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Recursive: " + time1 + " ns → " + result1);
        System.out.println("DFS:       " + time2 + " ns → " + result2);
        System.out.println("BFS:       " + time3 + " ns → " + result3);
        
        // Test early difference detection
        System.out.println("\nEarly Difference Detection (~1000 nodes, differ at root):");
        
        startTime = System.nanoTime();
        boolean result4 = solution.isSameTree(diffEarly1, diffEarly2);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result5 = solution.isSameTreeDFS(diffEarly1, diffEarly2);
        long time5 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result6 = solution.isSameTreeBFS(diffEarly1, diffEarly2);
        long time6 = System.nanoTime() - startTime;
        
        System.out.println("Recursive: " + time4 + " ns → " + result4);
        System.out.println("DFS:       " + time5 + " ns → " + result5);
        System.out.println("BFS:       " + time6 + " ns → " + result6);
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
        System.out.println("\n1. RECURSIVE DFS (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     isSameTree(p, q):");
        System.out.println("       if p == null AND q == null: return true");
        System.out.println("       if p == null OR q == null: return false");
        System.out.println("       if p.val != q.val: return false");
        System.out.println("       return isSameTree(p.left, q.left) AND isSameTree(p.right, q.right)");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(h) - Recursion stack, h = tree height");
        System.out.println("   Pros: Simple, intuitive, elegant");
        System.out.println("   Cons: Recursion limits for very deep trees");
        
        System.out.println("\n2. ITERATIVE DFS:");
        System.out.println("   Algorithm:");
        System.out.println("     stack = [(p, q)]");
        System.out.println("     while stack not empty:");
        System.out.println("       (node1, node2) = stack.pop()");
        System.out.println("       if both null: continue");
        System.out.println("       if one null: return false");
        System.out.println("       if values different: return false");
        System.out.println("       push (node1.right, node2.right)");
        System.out.println("       push (node1.left, node2.left)");
        System.out.println("   Time: O(n), Space: O(h)");
        System.out.println("   Pros: No recursion limits");
        System.out.println("   Cons: More complex implementation");
        
        System.out.println("\n3. ITERATIVE BFS:");
        System.out.println("   Algorithm:");
        System.out.println("     queue = [(p, q)]");
        System.out.println("     while queue not empty:");
        System.out.println("       (node1, node2) = queue.dequeue()");
        System.out.println("       if both null: continue");
        System.out.println("       if one null: return false");
        System.out.println("       if values different: return false");
        System.out.println("       enqueue (node1.left, node2.left)");
        System.out.println("       enqueue (node1.right, node2.right)");
        System.out.println("   Time: O(n), Space: O(w) where w = level width");
        System.out.println("   Pros: Level-by-level comparison");
        System.out.println("   Cons: More memory for wide trees");
        
        System.out.println("\n4. KEY INSIGHTS:");
        System.out.println("   - Two trees are same if they have identical STRUCTURE and VALUES");
        System.out.println("   - Empty trees (both null) are considered the same");
        System.out.println("   - Order of comparison matters (preorder works well)");
        System.out.println("   - Early termination when first difference found");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR SAME TREE:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with recursive DFS: most intuitive");
        System.out.println("   - Then mention iterative approaches if asked");
        System.out.println("   - Discuss trade-offs between different methods");
        System.out.println("   - Recommended order: Recursive → DFS → BFS");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Definition: identical structure AND values");
        System.out.println("   - Empty trees are the same");
        System.out.println("   - Early termination improves efficiency");
        System.out.println("   - Time complexity: O(n) for all optimal approaches");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Forgetting to check if both trees are null");
        System.out.println("   - Not handling the case where one tree is null");
        System.out.println("   - Comparing structure but forgetting values");
        System.out.println("   - Using == instead of .equals for value comparison");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - How to check if one tree is subtree of another? (LeetCode 572)");
        System.out.println("   - How to check if tree is symmetric? (LeetCode 101)");
        System.out.println("   - How to serialize/deserialize trees for comparison?");
        System.out.println("   - What about trees with duplicate values?");
        
        System.out.println("\n5. Related Problems:");
        System.out.println("   - 101. Symmetric Tree");
        System.out.println("   - 572. Subtree of Another Tree");
        System.out.println("   - 226. Invert Binary Tree");
        System.out.println("   - 104. Maximum Depth of Binary Tree");
        System.out.println("   - 110. Balanced Binary Tree");
        
        System.out.println("\n6. Code Pattern to Remember:");
        System.out.println("   public boolean isSameTree(TreeNode p, TreeNode q) {");
        System.out.println("     if (p == null && q == null) return true;");
        System.out.println("     if (p == null || q == null) return false;");
        System.out.println("     if (p.val != q.val) return false;");
        System.out.println("     return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);");
        System.out.println("   }");
        
        System.out.println("\n7. Real-world Applications:");
        System.out.println("   - File system comparison");
        System.out.println("   - Database index validation");
        System.out.println("   - Version control system file tree comparison");
        System.out.println("   - Configuration management");
        System.out.println("   - Test case validation for tree algorithms");
    }
}
