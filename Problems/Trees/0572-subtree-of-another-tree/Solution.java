
## Solution.java

```java
/**
 * 572. Subtree of Another Tree
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given the roots of two binary trees root and subRoot, return true if there is a subtree 
 * of root with the same structure and node values of subRoot and false otherwise.
 * 
 * Key Insights:
 * 1. A subtree of a binary tree is a tree consisting of a node and all of its descendants
 * 2. The entire tree is considered a subtree of itself
 * 3. Need to check if subRoot matches any subtree starting from any node in root
 * 4. Multiple approaches: recursive DFS, serialization, KMP algorithm
 * 
 * Approaches:
 * 1. Recursive DFS - RECOMMENDED for interviews
 * 2. Serialization with String Contains - Simple but less efficient
 * 3. Serialization with KMP - Efficient for large trees
 * 4. Iterative DFS - Stack-based approach
 * 
 * Time Complexity: 
 *   - Recursive DFS: O(m×n) where m = nodes in root, n = nodes in subRoot
 *   - Serialization + KMP: O(m + n)
 * Space Complexity: O(h) where h is tree height
 * 
 * Tags: Tree, Depth-First Search, String Matching, Binary Tree, Hash Function
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
     * Time: O(m×n) where m = nodes in root, n = nodes in subRoot
     * Space: O(h) where h is height of root
     * Algorithm:
     * 1. For each node in root, check if subtree starting from that node matches subRoot
     * 2. Use isSameTree helper to check if two trees are identical
     * 3. Recursively check left and right subtrees
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        // If root is null, no subtree can match (subRoot is guaranteed non-empty)
        if (root == null) {
            return false;
        }
        
        // Check if current subtree matches subRoot
        if (isSameTree(root, subRoot)) {
            return true;
        }
        
        // Recursively check left and right subtrees
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }
    
    /**
     * Helper method to check if two trees are identical
     * Same as LeetCode 100. Same Tree
     */
    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    
    /**
     * Approach 2: Serialization with String Contains
     * Time: O(m×n) due to string contains, Space: O(m + n)
     * Algorithm:
     * 1. Serialize both trees to strings with delimiters
     * 2. Check if serialized subRoot is substring of serialized root
     * 3. Use special markers for null nodes to ensure structure preservation
     */
    public boolean isSubtreeSerialization(TreeNode root, TreeNode subRoot) {
        String rootSerialized = serializeTree(root);
        String subRootSerialized = serializeTree(subRoot);
        return rootSerialized.contains(subRootSerialized);
    }
    
    /**
     * Serialize tree using preorder traversal with null markers
     */
    private String serializeTree(TreeNode node) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(node, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null,");
            return;
        }
        
        // Use # as value delimiter to handle multi-digit numbers
        sb.append("#").append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }
    
    /**
     * Approach 3: Serialization with KMP Algorithm
     * Time: O(m + n), Space: O(m + n)
     * More efficient string matching using Knuth-Morris-Pratt algorithm
     */
    public boolean isSubtreeKMP(TreeNode root, TreeNode subRoot) {
        String rootStr = serializeTree(root);
        String subStr = serializeTree(subRoot);
        return kmpSearch(rootStr, subStr) != -1;
    }
    
    /**
     * KMP algorithm for efficient string searching
     */
    private int kmpSearch(String text, String pattern) {
        if (pattern.isEmpty()) return 0;
        
        int[] lps = computeLPS(pattern);
        int i = 0; // index for text
        int j = 0; // index for pattern
        
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }
            
            if (j == pattern.length()) {
                return i - j; // pattern found
            } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        
        return -1; // pattern not found
    }
    
    /**
     * Compute Longest Prefix Suffix array for KMP
     */
    private int[] computeLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0; // length of previous longest prefix suffix
        int i = 1;
        
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
    
    /**
     * Approach 4: Iterative DFS using Stack
     * Time: O(m×n), Space: O(h)
     * Uses stack to simulate recursion
     */
    public boolean isSubtreeIterative(TreeNode root, TreeNode subRoot) {
        if (root == null) {
            return false;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            
            // Check if current node's subtree matches subRoot
            if (isSameTreeIterative(node, subRoot)) {
                return true;
            }
            
            // Push children to stack
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        
        return false;
    }
    
    /**
     * Iterative version of isSameTree using stack
     */
    private boolean isSameTreeIterative(TreeNode p, TreeNode q) {
        Stack<TreeNode[]> stack = new Stack<>();
        stack.push(new TreeNode[]{p, q});
        
        while (!stack.isEmpty()) {
            TreeNode[] nodes = stack.pop();
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
            
            stack.push(new TreeNode[]{node1.right, node2.right});
            stack.push(new TreeNode[]{node1.left, node2.left});
        }
        
        return true;
    }
    
    /**
     * Approach 5: Enhanced DFS with Early Optimization
     * Adds size check optimization to avoid unnecessary comparisons
     */
    public boolean isSubtreeOptimized(TreeNode root, TreeNode subRoot) {
        // Precompute sizes for optimization
        int rootSize = countNodes(root);
        int subRootSize = countNodes(subRoot);
        
        // If subRoot is larger, it can't be a subtree
        if (subRootSize > rootSize) {
            return false;
        }
        
        return isSubtreeHelper(root, subRoot, rootSize, subRootSize);
    }
    
    private boolean isSubtreeHelper(TreeNode root, TreeNode subRoot, int rootSize, int subRootSize) {
        if (root == null) {
            return false;
        }
        
        // Only check if sizes are compatible
        if (countNodes(root) >= subRootSize && isSameTree(root, subRoot)) {
            return true;
        }
        
        return isSubtreeHelper(root.left, subRoot, rootSize, subRootSize) ||
               isSubtreeHelper(root.right, subRoot, rootSize, subRootSize);
    }
    
    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    
    /**
     * Approach 6: Merkle Tree Hashing
     * Time: O(m + n), Space: O(m + n)
     * Uses cryptographic hashing to compare tree structures
     */
    public boolean isSubtreeMerkle(TreeNode root, TreeNode subRoot) {
        Map<TreeNode, String> merkleRoot = new HashMap<>();
        computeMerkle(root, merkleRoot);
        String subRootHash = computeMerkle(subRoot, merkleRoot);
        
        return checkMerkleSubtree(root, subRootHash, merkleRoot);
    }
    
    private String computeMerkle(TreeNode node, Map<TreeNode, String> merkleMap) {
        if (node == null) {
            return "null";
        }
        
        String leftHash = computeMerkle(node.left, merkleMap);
        String rightHash = computeMerkle(node.right, merkleMap);
        
        // Create hash combining value and children hashes
        String hash = node.val + "#" + leftHash + "#" + rightHash;
        String merkleHash = Integer.toHexString(hash.hashCode());
        merkleMap.put(node, merkleHash);
        
        return merkleHash;
    }
    
    private boolean checkMerkleSubtree(TreeNode node, String targetHash, Map<TreeNode, String> merkleMap) {
        if (node == null) {
            return false;
        }
        
        if (merkleMap.get(node).equals(targetHash)) {
            return true;
        }
        
        return checkMerkleSubtree(node.left, targetHash, merkleMap) ||
               checkMerkleSubtree(node.right, targetHash, merkleMap);
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
     * Visualize subtree checking process
     */
    public void visualizeSubtreeCheck(TreeNode root, TreeNode subRoot, String approach) {
        System.out.println("\n" + approach + " Subtree Check:");
        System.out.println("Main Tree:");
        printTree(root, 0);
        System.out.println("Subtree to Find:");
        printTree(subRoot, 0);
        
        boolean result;
        switch (approach) {
            case "Recursive":
                result = isSubtree(root, subRoot);
                break;
            case "Serialization":
                result = isSubtreeSerialization(root, subRoot);
                break;
            case "KMP":
                result = isSubtreeKMP(root, subRoot);
                break;
            default:
                result = isSubtree(root, subRoot);
        }
        
        System.out.println("Result: " + (result ? "FOUND" : "NOT FOUND"));
        
        if (approach.equals("Recursive")) {
            System.out.println("\nDetailed Search Process:");
            visualizeRecursiveProcess(root, subRoot, "root");
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
     * Visualize recursive search process step by step
     */
    private boolean visualizeRecursiveProcess(TreeNode root, TreeNode subRoot, String position) {
        System.out.println("Checking node at " + position + ": " + 
                          (root == null ? "null" : root.val));
        
        if (root == null) {
            System.out.println("  Node is null → skip");
            return false;
        }
        
        // Check if current subtree matches
        System.out.println("  Comparing with subRoot...");
        boolean currentMatch = visualizeSameTreeCheck(root, subRoot, "current");
        
        if (currentMatch) {
            System.out.println("  *** MATCH FOUND at " + position + " ***");
            return true;
        }
        
        System.out.println("  No match at " + position + ", checking children...");
        
        boolean leftFound = visualizeRecursiveProcess(root.left, subRoot, position + ".left");
        if (leftFound) {
            return true;
        }
        
        boolean rightFound = visualizeRecursiveProcess(root.right, subRoot, position + ".right");
        return rightFound;
    }
    
    private boolean visualizeSameTreeCheck(TreeNode p, TreeNode q, String context) {
        if (p == null && q == null) {
            System.out.println("    " + context + ": Both null → MATCH");
            return true;
        }
        
        if (p == null || q == null) {
            System.out.println("    " + context + ": Structure mismatch → NO MATCH");
            return false;
        }
        
        System.out.println("    " + context + ": Values " + p.val + " vs " + q.val);
        
        if (p.val != q.val) {
            System.out.println("    " + context + ": Values different → NO MATCH");
            return false;
        }
        
        System.out.println("    " + context + ": Values match, checking children...");
        
        boolean leftMatch = visualizeSameTreeCheck(p.left, q.left, context + ".left");
        boolean rightMatch = visualizeSameTreeCheck(p.right, q.right, context + ".right");
        
        boolean result = leftMatch && rightMatch;
        System.out.println("    " + context + " final: " + (result ? "MATCH" : "NO MATCH"));
        return result;
    }
    
    /**
     * Find and report all matching subtrees
     */
    public void findAllSubtrees(TreeNode root, TreeNode subRoot) {
        System.out.println("\nFinding All Matching Subtrees:");
        List<TreeNode> matches = new ArrayList<>();
        findAllSubtreesHelper(root, subRoot, matches, "root");
        
        if (matches.isEmpty()) {
            System.out.println("No matching subtrees found");
        } else {
            System.out.println("Found " + matches.size() + " matching subtree(s):");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println("Match " + (i + 1) + " at node: " + matches.get(i).val);
                printTree(matches.get(i), 2);
            }
        }
    }
    
    private void findAllSubtreesHelper(TreeNode root, TreeNode subRoot, List<TreeNode> matches, String position) {
        if (root == null) {
            return;
        }
        
        if (isSameTree(root, subRoot)) {
            matches.add(root);
            System.out.println("Found match at " + position + " (node: " + root.val + ")");
        }
        
        findAllSubtreesHelper(root.left, subRoot, matches, position + ".left");
        findAllSubtreesHelper(root.right, subRoot, matches, position + ".right");
    }
    
    /**
     * Compare tree properties for analysis
     */
    public void analyzeTrees(TreeNode root, TreeNode subRoot) {
        System.out.println("\nTree Analysis:");
        
        int rootSize = countNodes(root);
        int subRootSize = countNodes(subRoot);
        int rootHeight = calculateHeight(root);
        int subRootHeight = calculateHeight(subRoot);
        
        System.out.println("Main Tree - Nodes: " + rootSize + ", Height: " + rootHeight);
        System.out.println("Sub Tree  - Nodes: " + subRootSize + ", Height: " + subRootHeight);
        
        if (subRootSize > rootSize) {
            System.out.println("Subtree is larger than main tree → CANNOT be subtree");
        } else if (subRootHeight > rootHeight) {
            System.out.println("Subtree is taller than main tree → CANNOT be subtree");
        } else {
            System.out.println("Size and height compatible → POSSIBLE subtree");
        }
        
        // Check if subRoot is the entire tree
        if (rootSize == subRootSize) {
            System.out.println("Subtree has same size as main tree → Could be entire tree");
        }
    }
    
    private int calculateHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Subtree of Another Tree");
        System.out.println("===============================\n");
        
        // Test Case 1: Example from problem (subtree exists)
        System.out.println("Test 1: Subtree exists");
        Integer[] nodes1 = {3,4,5,1,2};
        Integer[] subNodes1 = {4,1,2};
        TreeNode root1 = solution.buildTree(nodes1);
        TreeNode subRoot1 = solution.buildTree(subNodes1);
        
        boolean result1a = solution.isSubtree(root1, subRoot1);
        boolean result1b = solution.isSubtreeSerialization(root1, subRoot1);
        boolean result1c = solution.isSubtreeKMP(root1, subRoot1);
        boolean expected1 = true;
        
        System.out.println("Recursive:      " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Serialization:  " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("KMP:            " + result1c + " - " + 
                         (result1c == expected1 ? "PASSED" : "FAILED"));
        
        solution.visualizeSubtreeCheck(root1, subRoot1, "Recursive");
        
        // Test Case 2: Example from problem (subtree doesn't exist)
        System.out.println("\nTest 2: Subtree doesn't exist (extra node)");
        Integer[] nodes2 = {3,4,5,1,2,null,null,null,null,0};
        Integer[] subNodes2 = {4,1,2};
        TreeNode root2 = solution.buildTree(nodes2);
        TreeNode subRoot2 = solution.buildTree(subNodes2);
        
        boolean result2 = solution.isSubtree(root2, subRoot2);
        System.out.println("Subtree missing: " + result2 + " - " + 
                         (result2 == false ? "PASSED" : "FAILED"));
        
        solution.visualizeSubtreeCheck(root2, subRoot2, "Recursive");
        solution.findAllSubtrees(root2, subRoot2);
        
        // Test Case 3: Entire tree is subtree
        System.out.println("\nTest 3: Entire tree is subtree");
        Integer[] nodes3 = {1,2,3};
        Integer[] subNodes3 = {1,2,3};
        TreeNode root3 = solution.buildTree(nodes3);
        TreeNode subRoot3 = solution.buildTree(subNodes3);
        
        boolean result3 = solution.isSubtree(root3, subRoot3);
        System.out.println("Entire tree: " + result3 + " - " + 
                         (result3 == true ? "PASSED" : "FAILED"));
        
        // Test Case 4: Single node subtree
        System.out.println("\nTest 4: Single node subtree");
        Integer[] nodes4 = {1,2,3,4,5};
        Integer[] subNodes4 = {3};
        TreeNode root4 = solution.buildTree(nodes4);
        TreeNode subRoot4 = solution.buildTree(subNodes4);
        
        boolean result4 = solution.isSubtree(root4, subRoot4);
        System.out.println("Single node: " + result4 + " - " + 
                         (result4 == true ? "PASSED" : "FAILED"));
        
        // Test Case 5: Subtree not found
        System.out.println("\nTest 5: Subtree not found");
        Integer[] nodes5 = {1,2,3};
        Integer[] subNodes5 = {4,5,6};
        TreeNode root5 = solution.buildTree(nodes5);
        TreeNode subRoot5 = solution.buildTree(subNodes5);
        
        boolean result5 = solution.isSubtree(root5, subRoot5);
        System.out.println("Not found: " + result5 + " - " + 
                         (result5 == false ? "PASSED" : "FAILED"));
        
        solution.analyzeTrees(root5, subRoot5);
        
        // Test Case 6: Complex tree with multiple occurrences
        System.out.println("\nTest 6: Complex tree with pattern");
        Integer[] nodes6 = {1,2,3,4,5,4,5,6,7,8,9};
        Integer[] subNodes6 = {4,5,6};
        TreeNode root6 = solution.buildTree(nodes6);
        TreeNode subRoot6 = solution.buildTree(subNodes6);
        
        boolean result6 = solution.isSubtree(root6, subRoot6);
        System.out.println("Complex pattern: " + result6 + " - " + 
                         (result6 == true ? "PASSED" : "FAILED"));
        
        solution.findAllSubtrees(root6, subRoot6);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SUBTREE OF ANOTHER TREE ALGORITHM EXPLANATION");
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
        
        // Create large tree with repeating pattern
        TreeNode largeTree = createTreeWithPattern(1000);
        TreeNode subTree = createTreeWithPattern(100); // Smaller subtree
        
        // Create trees where subtree doesn't exist
        TreeNode noMatchTree = createBalancedTree(12);
        TreeNode noMatchSub = createBalancedTree(3);
        // Modify to ensure no match
        if (noMatchSub != null) noMatchSub.val = -999;
        
        // Test with matching subtree
        System.out.println("\nMatching Subtree Performance:");
        
        long startTime = System.nanoTime();
        boolean result1 = solution.isSubtree(largeTree, subTree);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result2 = solution.isSubtreeKMP(largeTree, subTree);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result3 = solution.isSubtreeOptimized(largeTree, subTree);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Recursive:  " + time1 + " ns → " + result1);
        System.out.println("KMP:        " + time2 + " ns → " + result2);
        System.out.println("Optimized:  " + time3 + " ns → " + result3);
        
        // Test with no matching subtree
        System.out.println("\nNo Matching Subtree Performance:");
        
        startTime = System.nanoTime();
        boolean result4 = solution.isSubtree(noMatchTree, noMatchSub);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result5 = solution.isSubtreeKMP(noMatchTree, noMatchSub);
        long time5 = System.nanoTime() - startTime;
        
        System.out.println("Recursive:  " + time4 + " ns → " + result4);
        System.out.println("KMP:        " + time5 + " ns → " + result5);
    }
    
    /**
     * Create a tree with repeating pattern for testing
     */
    private static TreeNode createTreeWithPattern(int size) {
        if (size <= 0) return null;
        return createPatternHelper(1, size, 1);
    }
    
    private static TreeNode createPatternHelper(int value, int remaining, int level) {
        if (remaining <= 0) return null;
        
        TreeNode node = new TreeNode(value);
        int leftSize = Math.min(remaining - 1, (int)Math.pow(2, level) - 1);
        
        node.left = createPatternHelper((value * 2) % 100, leftSize / 2, level + 1);
        node.right = createPatternHelper((value * 2 + 1) % 100, leftSize / 2, level + 1);
        
        return node;
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
        System.out.println("     isSubtree(root, subRoot):");
        System.out.println("       if root == null: return false");
        System.out.println("       if isSameTree(root, subRoot): return true");
        System.out.println("       return isSubtree(root.left, subRoot) OR isSubtree(root.right, subRoot)");
        System.out.println("   Time: O(m×n) - For each of m nodes, potentially compare n nodes");
        System.out.println("   Space: O(h) - Recursion stack depth");
        System.out.println("   Pros: Simple, intuitive, easy to understand");
        System.out.println("   Cons: O(m×n) worst-case time complexity");
        
        System.out.println("\n2. SERIALIZATION + KMP:");
        System.out.println("   Algorithm:");
        System.out.println("     1. Serialize both trees to strings with delimiters");
        System.out.println("     2. Use KMP algorithm to check if subRoot string is substring of root string");
        System.out.println("   Time: O(m + n) - Linear time string matching");
        System.out.println("   Space: O(m + n) - Storage for serialized trees");
        System.out.println("   Pros: Optimal time complexity for large trees");
        System.out.println("   Cons: More complex implementation, requires careful serialization");
        
        System.out.println("\n3. KEY INSIGHTS:");
        System.out.println("   - A subtree includes the node AND all descendants");
        System.out.println("   - The entire tree is a subtree of itself");
        System.out.println("   - Both structure AND values must match exactly");
        System.out.println("   - Empty tree cannot be a subtree (constraints guarantee non-empty)");
        
        System.out.println("\n4. OPTIMIZATION TECHNIQUES:");
        System.out.println("   - Precompute tree sizes to avoid impossible matches");
        System.out.println("   - Use Merkle hashing for O(1) subtree comparisons");
        System.out.println("   - Early termination when match found");
        System.out.println("   - Breadth-first search for shallow subtrees");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR SUBTREE PROBLEM:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with recursive DFS: most intuitive");
        System.out.println("   - Mention serialization + KMP for follow-up optimization");
        System.out.println("   - Discuss trade-offs between different approaches");
        System.out.println("   - Recommended order: Recursive → Serialization → KMP");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Definition: node + all descendants = subtree");
        System.out.println("   - Entire tree is subtree of itself");
        System.out.println("   - Both structure and values must match");
        System.out.println("   - Time/space complexity trade-offs");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Forgetting that entire tree is a valid subtree");
        System.out.println("   - Not handling null cases properly");
        System.out.println("   - Confusing subtree with subset of nodes");
        System.out.println("   - Using == instead of .equals for tree comparison");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - How to optimize for large trees? (KMP, Merkle hashing)");
        System.out.println("   - What if trees have duplicate values?");
        System.out.println("   - How to find ALL matching subtrees?");
        System.out.println("   - How to handle subtree deletion?");
        
        System.out.println("\n5. Related Problems:");
        System.out.println("   - 100. Same Tree");
        System.out.println("   - 101. Symmetric Tree");
        System.out.println("   - 437. Path Sum III");
        System.out.println("   - 652. Find Duplicate Subtrees");
        System.out.println("   - 1367. Linked List in Binary Tree");
        
        System.out.println("\n6. Code Pattern to Remember:");
        System.out.println("   public boolean isSubtree(TreeNode root, TreeNode subRoot) {");
        System.out.println("     if (root == null) return false;");
        System.out.println("     if (isSameTree(root, subRoot)) return true;");
        System.out.println("     return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);");
        System.out.println("   }");
        System.out.println("   private boolean isSameTree(TreeNode p, TreeNode q) {");
        System.out.println("     if (p == null && q == null) return true;");
        System.out.println("     if (p == null || q == null) return false;");
        System.out.println("     if (p.val != q.val) return false;");
        System.out.println("     return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);");
        System.out.println("   }");
        
        System.out.println("\n7. Real-world Applications:");
        System.out.println("   - Code plagiarism detection (AST comparison)");
        System.out.println("   - File system directory structure comparison");
        System.out.println("   - DNA sequence pattern matching");
        System.out.println("   - Computer vision (template matching)");
        System.out.println("   - Database query optimization");
    }
}
