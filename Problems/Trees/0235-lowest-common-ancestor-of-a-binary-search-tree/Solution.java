
## Solution.java

```java
/**
 * 235. Lowest Common Ancestor of a Binary Search Tree
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
 * The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has 
 * both p and q as descendants (where we allow a node to be a descendant of itself).
 * 
 * Key Insights:
 * 1. BST Property: left subtree < root < right subtree
 * 2. LCA is where p and q split into different subtrees
 * 3. If both p and q are less than current node, LCA is in left subtree
 * 4. If both p and q are greater than current node, LCA is in right subtree
 * 5. If one is less and one is greater, current node is LCA
 * 6. If current node equals p or q, it's the LCA (node is descendant of itself)
 * 
 * Approaches:
 * 1. Iterative BST Traversal - RECOMMENDED for interviews
 * 2. Recursive BST Traversal - Clean recursive version
 * 3. General Binary Tree LCA - Works for any binary tree
 * 4. Path Comparison - Find paths and compare
 * 
 * Time Complexity: O(h) where h is tree height
 * Space Complexity: O(1) for iterative, O(h) for recursive
 * 
 * Tags: Tree, Depth-First Search, Binary Search Tree, Binary Tree
 */

import java.util.*;

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class Solution {
    
    /**
     * Approach 1: Iterative BST Traversal - RECOMMENDED for interviews
     * Time: O(h) where h is tree height, Space: O(1)
     * Algorithm:
     * 1. Start from root
     * 2. If both p and q are less than current node, move to left child
     * 3. If both p and q are greater than current node, move to right child
     * 4. Otherwise, current node is LCA (p and q split or current equals one of them)
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;
        
        while (current != null) {
            // Both nodes are in left subtree
            if (p.val < current.val && q.val < current.val) {
                current = current.left;
            }
            // Both nodes are in right subtree
            else if (p.val > current.val && q.val > current.val) {
                current = current.right;
            }
            // Found the split point (LCA)
            else {
                return current;
            }
        }
        
        return null; // Should never reach here given constraints
    }
    
    /**
     * Approach 2: Recursive BST Traversal
     * Time: O(h), Space: O(h) for recursion stack
     * Same logic as iterative but recursive implementation
     */
    public TreeNode lowestCommonAncestorRecursive(TreeNode root, TreeNode p, TreeNode q) {
        // Both in left subtree
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestorRecursive(root.left, p, q);
        }
        // Both in right subtree
        else if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestorRecursive(root.right, p, q);
        }
        // Found LCA (split point or current is one of the nodes)
        else {
            return root;
        }
    }
    
    /**
     * Approach 3: General Binary Tree LCA (Works for any binary tree)
     * Time: O(n), Space: O(h)
     * This approach works for any binary tree, not just BST
     * Less efficient but more general
     */
    public TreeNode lowestCommonAncestorGeneral(TreeNode root, TreeNode p, TreeNode q) {
        // Base cases
        if (root == null) return null;
        if (root == p || root == q) return root;
        
        // Search in left and right subtrees
        TreeNode left = lowestCommonAncestorGeneral(root.left, p, q);
        TreeNode right = lowestCommonAncestorGeneral(root.right, p, q);
        
        // If both sides found something, current is LCA
        if (left != null && right != null) return root;
        
        // Otherwise return non-null side
        return left != null ? left : right;
    }
    
    /**
     * Approach 4: Path Comparison Method
     * Time: O(h), Space: O(h)
     * Find paths from root to p and q, then find last common node
     */
    public TreeNode lowestCommonAncestorPath(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathToP = findPath(root, p);
        List<TreeNode> pathToQ = findPath(root, q);
        
        // Find last common node in both paths
        TreeNode lca = null;
        int minLength = Math.min(pathToP.size(), pathToQ.size());
        
        for (int i = 0; i < minLength; i++) {
            if (pathToP.get(i) == pathToQ.get(i)) {
                lca = pathToP.get(i);
            } else {
                break;
            }
        }
        
        return lca;
    }
    
    /**
     * Helper method to find path from root to target node in BST
     */
    private List<TreeNode> findPath(TreeNode root, TreeNode target) {
        List<TreeNode> path = new ArrayList<>();
        TreeNode current = root;
        
        while (current != null) {
            path.add(current);
            
            if (target.val == current.val) {
                break;
            } else if (target.val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        return path;
    }
    
    /**
     * Approach 5: Enhanced Iterative with Early Termination
     * Time: O(h), Space: O(1)
     * Adds additional checks for when current equals p or q
     */
    public TreeNode lowestCommonAncestorEnhanced(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;
        
        while (current != null) {
            // If current is one of the nodes, it's the LCA
            if (current == p || current == q) {
                return current;
            }
            
            // Both in left subtree
            if (p.val < current.val && q.val < current.val) {
                current = current.left;
            }
            // Both in right subtree
            else if (p.val > current.val && q.val > current.val) {
                current = current.right;
            }
            // Split point found
            else {
                return current;
            }
        }
        
        return null;
    }
    
    /**
     * Approach 6: Iterative with Path Tracking (for visualization)
     * Time: O(h), Space: O(h)
     * Tracks the path to help visualize the LCA finding process
     */
    public LCAResult lowestCommonAncestorWithPath(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> path = new ArrayList<>();
        TreeNode current = root;
        TreeNode lca = null;
        
        while (current != null) {
            path.add(current);
            
            if (p.val < current.val && q.val < current.val) {
                current = current.left;
            } else if (p.val > current.val && q.val > current.val) {
                current = current.right;
            } else {
                lca = current;
                break;
            }
        }
        
        return new LCAResult(lca, path);
    }
    
    /**
     * Helper class for Approach 6
     */
    private class LCAResult {
        TreeNode lca;
        List<TreeNode> path;
        
        LCAResult(TreeNode lca, List<TreeNode> path) {
            this.lca = lca;
            this.path = path;
        }
    }
    
    /**
     * Helper method to build a BST from array (Level-order)
     * Note: This builds a BST by inserting nodes in level order
     * For proper BST construction, we should use sorted array approach
     */
    public TreeNode buildBST(Integer[] nodes) {
        if (nodes == null || nodes.length == 0 || nodes[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(nodes[0]);
        for (int i = 1; i < nodes.length; i++) {
            if (nodes[i] != null) {
                insertIntoBST(root, nodes[i]);
            }
        }
        return root;
    }
    
    private void insertIntoBST(TreeNode root, int val) {
        TreeNode current = root;
        TreeNode parent = null;
        
        while (current != null) {
            parent = current;
            if (val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        if (val < parent.val) {
            parent.left = new TreeNode(val);
        } else {
            parent.right = new TreeNode(val);
        }
    }
    
    /**
     * Helper method to find a node in BST by value
     */
    public TreeNode findNode(TreeNode root, int val) {
        TreeNode current = root;
        while (current != null) {
            if (val == current.val) {
                return current;
            } else if (val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }
    
    /**
     * Visualize LCA finding process
     */
    public void visualizeLCA(TreeNode root, TreeNode p, TreeNode q, String approach) {
        System.out.println("\n" + approach + " LCA Finding Process:");
        System.out.println("BST Structure:");
        printBST(root, 0);
        System.out.println("Nodes: p = " + p.val + ", q = " + q.val);
        
        TreeNode lca;
        switch (approach) {
            case "Iterative":
                lca = lowestCommonAncestor(root, p, q);
                break;
            case "Recursive":
                lca = lowestCommonAncestorRecursive(root, p, q);
                break;
            case "Path":
                LCAResult result = lowestCommonAncestorWithPath(root, p, q);
                lca = result.lca;
                System.out.println("Search Path: " + getPathValues(result.path));
                break;
            default:
                lca = lowestCommonAncestor(root, p, q);
        }
        
        System.out.println("LCA: " + (lca != null ? lca.val : "null"));
        
        if (approach.equals("Iterative")) {
            System.out.println("\nStep-by-Step Process:");
            visualizeIterativeProcess(root, p, q);
        }
    }
    
    private void printBST(TreeNode root, int depth) {
        if (root == null) {
            printIndent(depth);
            System.out.println("null");
            return;
        }
        
        printIndent(depth);
        System.out.println(root.val);
        
        if (root.left != null || root.right != null) {
            printBST(root.left, depth + 1);
            printBST(root.right, depth + 1);
        }
    }
    
    private void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
    }
    
    private List<Integer> getPathValues(List<TreeNode> path) {
        List<Integer> values = new ArrayList<>();
        for (TreeNode node : path) {
            values.add(node.val);
        }
        return values;
    }
    
    /**
     * Visualize iterative LCA finding process step by step
     */
    private void visualizeIterativeProcess(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;
        int step = 1;
        
        System.out.println("Step | Current | p=" + p.val + " vs Current | q=" + q.val + " vs Current | Action");
        System.out.println("-----|---------|-------------------|-------------------|--------");
        
        while (current != null) {
            String pComparison = p.val + " " + getComparison(p.val, current.val);
            String qComparison = q.val + " " + getComparison(q.val, current.val);
            String action;
            
            if (p.val < current.val && q.val < current.val) {
                action = "Both smaller → go LEFT";
                System.out.printf("%4d | %7d | %17s | %17s | %s%n", 
                    step++, current.val, pComparison, qComparison, action);
                current = current.left;
            } else if (p.val > current.val && q.val > current.val) {
                action = "Both larger → go RIGHT";
                System.out.printf("%4d | %7d | %17s | %17s | %s%n", 
                    step++, current.val, pComparison, qComparison, action);
                current = current.right;
            } else {
                action = "SPLIT POINT → FOUND LCA!";
                System.out.printf("%4d | %7d | %17s | %17s | %s%n", 
                    step++, current.val, pComparison, qComparison, action);
                break;
            }
        }
    }
    
    private String getComparison(int a, int b) {
        if (a < b) return "<";
        if (a > b) return ">";
        return "=";
    }
    
    /**
     * Analyze BST properties and LCA scenarios
     */
    public void analyzeLCAScenarios(TreeNode root, TreeNode p, TreeNode q) {
        System.out.println("\nLCA Scenario Analysis:");
        
        int minVal = Math.min(p.val, q.val);
        int maxVal = Math.max(p.val, q.val);
        
        System.out.println("Node values: p=" + p.val + ", q=" + q.val);
        System.out.println("Value range: [" + minVal + ", " + maxVal + "]");
        
        TreeNode lca = lowestCommonAncestor(root, p, q);
        System.out.println("LCA value: " + lca.val);
        
        // Check LCA properties
        System.out.println("\nLCA Properties:");
        System.out.println("Is LCA between p and q? " + 
                          (lca.val >= minVal && lca.val <= maxVal));
        System.out.println("Is LCA an ancestor of p? " + isAncestor(lca, p));
        System.out.println("Is LCA an ancestor of q? " + isAncestor(lca, q));
        
        // Check if LCA is one of the nodes
        if (lca == p || lca == q) {
            System.out.println("LCA is one of the input nodes (self-descendant case)");
        }
        
        // Find depth of LCA
        int lcaDepth = getDepth(root, lca);
        System.out.println("LCA depth from root: " + lcaDepth);
    }
    
    private boolean isAncestor(TreeNode ancestor, TreeNode descendant) {
        TreeNode current = descendant;
        while (current != null) {
            if (current == ancestor) return true;
            if (descendant.val < ancestor.val) {
                current = current.right; // Go up (reverse)
            } else {
                current = current.left; // Go up (reverse)
            }
        }
        return false;
    }
    
    private int getDepth(TreeNode root, TreeNode target) {
        int depth = 0;
        TreeNode current = root;
        
        while (current != null && current != target) {
            depth++;
            if (target.val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        return current == target ? depth : -1;
    }
    
    /**
     * Find all nodes on path between p and q (inclusive)
     */
    public void findPathBetweenNodes(TreeNode root, TreeNode p, TreeNode q) {
        System.out.println("\nPath between " + p.val + " and " + q.val + ":");
        
        TreeNode lca = lowestCommonAncestor(root, p, q);
        List<TreeNode> pathP = findPathFromNode(lca, p);
        List<TreeNode> pathQ = findPathFromNode(lca, q);
        
        // Combine paths (LCA to p + reverse LCA to q without duplicating LCA)
        List<TreeNode> fullPath = new ArrayList<>();
        
        // Add path from LCA to p
        fullPath.addAll(pathP);
        
        // Add path from q to LCA (reverse, excluding LCA)
        for (int i = pathQ.size() - 2; i >= 0; i--) {
            fullPath.add(pathQ.get(i));
        }
        
        System.out.println("Full path: " + getPathValues(fullPath));
    }
    
    private List<TreeNode> findPathFromNode(TreeNode from, TreeNode to) {
        List<TreeNode> path = new ArrayList<>();
        TreeNode current = from;
        
        while (current != null) {
            path.add(current);
            if (current == to) break;
            
            if (to.val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        return path;
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Lowest Common Ancestor of BST");
        System.out.println("=====================================\n");
        
        // Build the example BST from problem
        Integer[] nodes = {6,2,8,0,4,7,9,null,null,3,5};
        TreeNode root = solution.buildBST(nodes);
        
        // Test Case 1: Example 1 - p=2, q=8
        System.out.println("Test 1: p=2, q=8 (LCA should be 6)");
        TreeNode p1 = solution.findNode(root, 2);
        TreeNode q1 = solution.findNode(root, 8);
        
        TreeNode result1a = solution.lowestCommonAncestor(root, p1, q1);
        TreeNode result1b = solution.lowestCommonAncestorRecursive(root, p1, q1);
        TreeNode result1c = solution.lowestCommonAncestorGeneral(root, p1, q1);
        
        System.out.println("Iterative:  " + result1a.val + " - " + 
                         (result1a.val == 6 ? "PASSED" : "FAILED"));
        System.out.println("Recursive:  " + result1b.val + " - " + 
                         (result1b.val == 6 ? "PASSED" : "FAILED"));
        System.out.println("General:    " + result1c.val + " - " + 
                         (result1c.val == 6 ? "PASSED" : "FAILED"));
        
        solution.visualizeLCA(root, p1, q1, "Iterative");
        solution.analyzeLCAScenarios(root, p1, q1);
        
        // Test Case 2: Example 2 - p=2, q=4
        System.out.println("\nTest 2: p=2, q=4 (LCA should be 2 - self descendant)");
        TreeNode p2 = solution.findNode(root, 2);
        TreeNode q2 = solution.findNode(root, 4);
        
        TreeNode result2 = solution.lowestCommonAncestor(root, p2, q2);
        System.out.println("LCA: " + result2.val + " - " + 
                         (result2.val == 2 ? "PASSED" : "FAILED"));
        
        solution.visualizeLCA(root, p2, q2, "Path");
        solution.findPathBetweenNodes(root, p2, q2);
        
        // Test Case 3: Both in left subtree
        System.out.println("\nTest 3: p=0, q=3 (both in left subtree)");
        TreeNode p3 = solution.findNode(root, 0);
        TreeNode q3 = solution.findNode(root, 3);
        
        TreeNode result3 = solution.lowestCommonAncestor(root, p3, q3);
        System.out.println("LCA: " + result3.val + " - " + 
                         (result3.val == 2 ? "PASSED" : "FAILED"));
        
        // Test Case 4: Both in right subtree
        System.out.println("\nTest 4: p=7, q=9 (both in right subtree)");
        TreeNode p4 = solution.findNode(root, 7);
        TreeNode q4 = solution.findNode(root, 9);
        
        TreeNode result4 = solution.lowestCommonAncestor(root, p4, q4);
        System.out.println("LCA: " + result4.val + " - " + 
                         (result4.val == 8 ? "PASSED" : "FAILED"));
        
        // Test Case 5: One is root
        System.out.println("\nTest 5: p=6, q=3 (root is LCA)");
        TreeNode p5 = solution.findNode(root, 6);
        TreeNode q5 = solution.findNode(root, 3);
        
        TreeNode result5 = solution.lowestCommonAncestor(root, p5, q5);
        System.out.println("LCA: " + result5.val + " - " + 
                         (result5.val == 6 ? "PASSED" : "FAILED"));
        
        // Test Case 6: Deep nodes
        System.out.println("\nTest 6: p=3, q=5 (deep nodes)");
        TreeNode p6 = solution.findNode(root, 3);
        TreeNode q6 = solution.findNode(root, 5);
        
        TreeNode result6 = solution.lowestCommonAncestor(root, p6, q6);
        System.out.println("LCA: " + result6.val + " - " + 
                         (result6.val == 4 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("LOWEST COMMON ANCESTOR OF BST ALGORITHM EXPLANATION");
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
        System.out.println("Creating large BST for performance testing...");
        
        // Create a large balanced BST
        TreeNode largeBST = createLargeBST(100000);
        TreeNode deepP = findDeepestLeft(largeBST);
        TreeNode deepQ = findDeepestRight(largeBST);
        
        // Test with deep nodes
        System.out.println("\nDeep Nodes Performance:");
        
        long startTime = System.nanoTime();
        TreeNode result1 = solution.lowestCommonAncestor(largeBST, deepP, deepQ);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result2 = solution.lowestCommonAncestorRecursive(largeBST, deepP, deepQ);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result3 = solution.lowestCommonAncestorGeneral(largeBST, deepP, deepQ);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Iterative:  " + time1 + " ns → LCA: " + result1.val);
        System.out.println("Recursive:  " + time2 + " ns → LCA: " + result2.val);
        System.out.println("General:    " + time3 + " ns → LCA: " + result3.val);
        
        // Test with root as LCA
        TreeNode rootP = findDeepestLeft(largeBST);
        TreeNode rootQ = findDeepestRight(largeBST);
        
        System.out.println("\nRoot as LCA Performance:");
        
        startTime = System.nanoTime();
        solution.lowestCommonAncestor(largeBST, rootP, rootQ);
        long time4 = System.nanoTime() - startTime;
        
        System.out.println("Iterative:  " + time4 + " ns (found at root)");
    }
    
    /**
     * Create a large balanced BST for testing
     */
    private static TreeNode createLargeBST(int size) {
        int[] sortedArray = new int[size];
        for (int i = 0; i < size; i++) {
            sortedArray[i] = i + 1;
        }
        return createBSTFromSortedArray(sortedArray, 0, size - 1);
    }
    
    private static TreeNode createBSTFromSortedArray(int[] arr, int start, int end) {
        if (start > end) return null;
        
        int mid = start + (end - start) / 2;
        TreeNode node = new TreeNode(arr[mid]);
        node.left = createBSTFromSortedArray(arr, start, mid - 1);
        node.right = createBSTFromSortedArray(arr, mid + 1, end);
        return node;
    }
    
    private static TreeNode findDeepestLeft(TreeNode root) {
        TreeNode current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    
    private static TreeNode findDeepestRight(TreeNode root) {
        TreeNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }
    
    /**
     * Detailed algorithm explanations
     */
    private static void explainAlgorithms() {
        System.out.println("\n1. ITERATIVE BST TRAVERSAL (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     current = root");
        System.out.println("     while current != null:");
        System.out.println("       if p < current AND q < current: current = current.left");
        System.out.println("       else if p > current AND q > current: current = current.right");
        System.out.println("       else: return current  // Found LCA");
        System.out.println("   Why it works:");
        System.out.println("     - BST property ensures nodes are ordered");
        System.println("     - LCA is where search paths for p and q diverge");
        System.out.println("     - If both smaller/bigger, they're in same subtree");
        System.out.println("     - Otherwise, we found the divergence point");
        System.out.println("   Time: O(h), Space: O(1)");
        
        System.out.println("\n2. KEY BST PROPERTIES USED:");
        System.out.println("   - Left subtree < Root < Right subtree");
        System.out.println("   - Search path is deterministic based on values");
        System.out.println("   - LCA is the node where p and q would take different paths");
        System.out.println("   - A node is considered a descendant of itself");
        
        System.out.println("\n3. DIFFERENT SCENARIOS:");
        System.out.println("   Case 1: Both in left subtree → LCA in left");
        System.out.println("   Case 2: Both in right subtree → LCA in right");
        System.out.println("   Case 3: One in left, one in right → Current is LCA");
        System.out.println("   Case 4: Current equals p or q → Current is LCA");
        
        System.out.println("\n4. WHY BST LCA IS EASIER THAN GENERAL TREE LCA:");
        System.out.println("   - BST has ordering property we can leverage");
        System.out.println("   - No need to search entire tree");
        System.out.println("   - Can find LCA in O(h) time vs O(n) for general tree");
        System.out.println("   - Iterative solution uses O(1) space vs O(h) for recursive");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR BST LCA:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with iterative BST traversal (most efficient)");
        System.out.println("   - Mention recursive version as alternative");
        System.out.println("   - Discuss general tree LCA for comparison");
        System.out.println("   - Recommended order: Iterative → Recursive → General");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Leverage BST ordering property");
        System.out.println("   - LCA is where search paths diverge");
        System.out.println("   - A node is its own descendant");
        System.out.println("   - Time complexity: O(h) vs O(n) for general tree");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Not using BST properties (treating it as general tree)");
        System.out.println("   - Forgetting that node is descendant of itself");
        System.out.println("   - Using == instead of comparing values");
        System.out.println("   - Not handling the case where p or q is the LCA");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - What if BST has duplicate values? (constraints say unique)");
        System.out.println("   - How to handle LCA in general binary tree? (LeetCode 236)");
        System.out.println("   - What if p or q doesn't exist in tree?");
        System.out.println("   - How to find k-th smallest/largest element in BST?");
        
        System.out.println("\n5. Related Problems:");
        System.out.println("   - 236. Lowest Common Ancestor of a Binary Tree");
        System.out.println("   - 1650. Lowest Common Ancestor of a Binary Tree III");
        System.out.println("   - 1644. Lowest Common Ancestor of a Binary Tree II");
        System.out.println("   - 1676. Lowest Common Ancestor of a Binary Tree IV");
        
        System.out.println("\n6. Code Pattern to Remember:");
        System.out.println("   public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {");
        System.out.println("     TreeNode current = root;");
        System.out.println("     while (current != null) {");
        System.out.println("       if (p.val < current.val && q.val < current.val) {");
        System.out.println("         current = current.left;");
        System.out.println("       } else if (p.val > current.val && q.val > current.val) {");
        System.out.println("         current = current.right;");
        System.out.println("       } else {");
        System.out.println("         return current;");
        System.out.println("       }");
        System.out.println("     }");
        System.out.println("     return null;");
        System.out.println("   }");
        
        System.out.println("\n7. Real-world Applications:");
        System.out.println("   - File system directory structure (finding common parent)");
        System.out.println("   - Organizational hierarchy (finding common manager)");
        System.out.println("   - Version control systems (finding common ancestor commit)");
        System.out.println("   - Database indexing (range queries)");
        System.out.println("   - Network routing (finding common network node)");
    }
}
