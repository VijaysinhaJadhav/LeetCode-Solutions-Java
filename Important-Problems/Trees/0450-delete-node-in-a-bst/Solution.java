
## Solution.java

```java
/**
 * 450. Delete Node in a BST
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a root node reference of a BST and a key, delete the node with the given key in the BST.
 * Return the root node reference (possibly updated) of the BST.
 * 
 * Key Insights:
 * 1. Three cases for deletion:
 *    - Case 1: Node has no children (leaf) → simply remove
 *    - Case 2: Node has one child → replace with child
 *    - Case 3: Node has two children → 
 *        a) Find inorder successor (smallest in right subtree) OR
 *        b) Find inorder predecessor (largest in left subtree)
 *        c) Replace node value with successor/predecessor value
 *        d) Recursively delete the successor/predecessor
 * 2. Maintain BST property throughout deletion
 * 3. If key not found, return original tree
 * 
 * Approaches:
 * 1. Recursive Approach - RECOMMENDED for interviews
 * 2. Iterative Approach - More space efficient
 * 3. Successor-based Deletion - Uses inorder successor
 * 4. Predecessor-based Deletion - Uses inorder predecessor
 * 
 * Time Complexity: O(h) where h is tree height
 * Space Complexity: O(h) for recursive, O(1) for iterative
 * 
 * Tags: Tree, Binary Search Tree, Binary Tree
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
     * Approach 1: Recursive Approach - RECOMMENDED for interviews
     * Time: O(h), Space: O(h)
     * Algorithm:
     * 1. Search for the node to delete
     * 2. Handle three cases:
     *    - No children: return null
     *    - One child: return the non-null child
     *    - Two children: find inorder successor, copy value, delete successor
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        
        // Search for the node to delete
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // Node to delete found
            // Case 1: No children (leaf node)
            if (root.left == null && root.right == null) {
                return null;
            }
            // Case 2: One child
            else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            // Case 3: Two children
            else {
                // Find inorder successor (smallest in right subtree)
                TreeNode successor = findMin(root.right);
                // Copy successor value to current node
                root.val = successor.val;
                // Delete the successor
                root.right = deleteNode(root.right, successor.val);
            }
        }
        
        return root;
    }
    
    /**
     * Helper method to find the minimum value node in a BST
     */
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    /**
     * Approach 2: Recursive with Predecessor
     * Uses inorder predecessor instead of successor for two-child case
     */
    public TreeNode deleteNodePredecessor(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        
        if (key < root.val) {
            root.left = deleteNodePredecessor(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNodePredecessor(root.right, key);
        } else {
            // Node to delete found
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                // Use inorder predecessor (largest in left subtree)
                TreeNode predecessor = findMax(root.left);
                root.val = predecessor.val;
                root.left = deleteNodePredecessor(root.left, predecessor.val);
            }
        }
        
        return root;
    }
    
    /**
     * Helper method to find the maximum value node in a BST
     */
    private TreeNode findMax(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    /**
     * Approach 3: Iterative Approach
     * Time: O(h), Space: O(1)
     * More complex but avoids recursion stack
     */
    public TreeNode deleteNodeIterative(TreeNode root, int key) {
        TreeNode current = root;
        TreeNode parent = null;
        
        // Search for the node to delete and track its parent
        while (current != null && current.val != key) {
            parent = current;
            if (key < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        // Key not found
        if (current == null) {
            return root;
        }
        
        // Case 1: No children
        if (current.left == null && current.right == null) {
            if (parent == null) {
                // Deleting the root node
                return null;
            } else if (parent.left == current) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        // Case 2: One child
        else if (current.left == null) {
            if (parent == null) {
                return current.right;
            } else if (parent.left == current) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else if (current.right == null) {
            if (parent == null) {
                return current.left;
            } else if (parent.left == current) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        }
        // Case 3: Two children
        else {
            // Find inorder successor and its parent
            TreeNode successor = current.right;
            TreeNode successorParent = current;
            
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            
            // Copy successor value
            current.val = successor.val;
            
            // Delete the successor (which has at most one right child)
            if (successorParent.left == successor) {
                successorParent.left = successor.right;
            } else {
                successorParent.right = successor.right;
            }
        }
        
        return root;
    }
    
    /**
     * Approach 4: Enhanced Recursive with Detailed Cases
     * Provides more explicit handling of different cases
     */
    public TreeNode deleteNodeEnhanced(TreeNode root, int key) {
        if (root == null) return null;
        
        if (key < root.val) {
            root.left = deleteNodeEnhanced(root.left, key);
            return root;
        } else if (key > root.val) {
            root.right = deleteNodeEnhanced(root.right, key);
            return root;
        } else {
            // Case 1: Leaf node
            if (root.left == null && root.right == null) {
                return null;
            }
            // Case 2: Only right child
            else if (root.left == null) {
                return root.right;
            }
            // Case 3: Only left child
            else if (root.right == null) {
                return root.left;
            }
            // Case 4: Two children - choose successor or predecessor
            else {
                // Option 1: Use successor (smallest in right subtree)
                TreeNode successor = findMin(root.right);
                root.val = successor.val;
                root.right = deleteNodeEnhanced(root.right, successor.val);
                return root;
                
                // Option 2: Use predecessor (largest in left subtree)
                // TreeNode predecessor = findMax(root.left);
                // root.val = predecessor.val;
                // root.left = deleteNodeEnhanced(root.left, predecessor.val);
                // return root;
            }
        }
    }
    
    /**
     * Approach 5: Deletion with Balance Consideration
     * Tries to choose successor/predecessor based on subtree heights
     */
    public TreeNode deleteNodeBalanced(TreeNode root, int key) {
        if (root == null) return null;
        
        if (key < root.val) {
            root.left = deleteNodeBalanced(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNodeBalanced(root.right, key);
        } else {
            // Node to delete found
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            
            // Two children: choose replacement based on subtree heights
            int leftHeight = getHeight(root.left);
            int rightHeight = getHeight(root.right);
            
            if (leftHeight > rightHeight) {
                // Use predecessor from left subtree
                TreeNode predecessor = findMax(root.left);
                root.val = predecessor.val;
                root.left = deleteNodeBalanced(root.left, predecessor.val);
            } else {
                // Use successor from right subtree
                TreeNode successor = findMin(root.right);
                root.val = successor.val;
                root.right = deleteNodeBalanced(root.right, successor.val);
            }
        }
        
        return root;
    }
    
    private int getHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
    
    /**
     * Approach 6: Deletion with Path Tracking (for visualization)
     */
    public DeletionResult deleteNodeWithPath(TreeNode root, int key) {
        List<TreeNode> searchPath = new ArrayList<>();
        TreeNode result = deleteNodeWithPathHelper(root, key, searchPath);
        return new DeletionResult(result, searchPath);
    }
    
    private TreeNode deleteNodeWithPathHelper(TreeNode root, int key, List<TreeNode> path) {
        if (root == null) {
            return null;
        }
        
        path.add(root);
        
        if (key < root.val) {
            root.left = deleteNodeWithPathHelper(root.left, key, path);
        } else if (key > root.val) {
            root.right = deleteNodeWithPathHelper(root.right, key, path);
        } else {
            // Node found - mark for deletion
            path.add(new TreeNode(-1)); // Marker for deletion point
            
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode successor = findMin(root.right);
                root.val = successor.val;
                root.right = deleteNodeWithPathHelper(root.right, successor.val, path);
            }
        }
        
        return root;
    }
    
    /**
     * Helper class for Approach 6
     */
    private class DeletionResult {
        TreeNode root;
        List<TreeNode> searchPath;
        
        DeletionResult(TreeNode root, List<TreeNode> searchPath) {
            this.root = root;
            this.searchPath = searchPath;
        }
    }
    
    /**
     * Helper method to build a BST from array (Level-order)
     */
    public TreeNode buildBST(Integer[] nodes) {
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
     * Helper method to convert BST to list (In-order traversal)
     */
    public List<Integer> bstToList(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }
    
    private void inOrderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inOrderTraversal(node.left, result);
        result.add(node.val);
        inOrderTraversal(node.right, result);
    }
    
    /**
     * Visualize deletion process
     */
    public void visualizeDeletion(TreeNode root, int key, String approach) {
        System.out.println("\n" + approach + " Deletion Process:");
        System.out.println("Original BST (In-order): " + bstToList(root));
        System.out.println("Key to delete: " + key);
        System.out.println("Tree Structure Before:");
        printTree(root, 0);
        
        TreeNode result;
        switch (approach) {
            case "Recursive":
                result = deleteNode(root, key);
                break;
            case "Iterative":
                result = deleteNodeIterative(root, key);
                break;
            case "WithPath":
                DeletionResult deletionResult = deleteNodeWithPath(root, key);
                result = deletionResult.root;
                System.out.println("Search Path: " + getPathValues(deletionResult.searchPath));
                break;
            default:
                result = deleteNode(root, key);
        }
        
        System.out.println("Tree Structure After:");
        if (result != null) {
            printTree(result, 0);
            System.out.println("BST After Deletion (In-order): " + bstToList(result));
            
            // Verify BST property is maintained
            boolean isValid = isValidBST(result);
            System.out.println("BST Property Maintained: " + isValid);
        } else {
            System.out.println("Empty tree");
        }
        
        if (approach.equals("Recursive")) {
            System.out.println("\nStep-by-Step Deletion Process:");
            visualizeRecursiveProcess(root, key, "root");
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
    
    private List<Integer> getPathValues(List<TreeNode> path) {
        List<Integer> values = new ArrayList<>();
        for (TreeNode node : path) {
            values.add(node.val);
        }
        return values;
    }
    
    /**
     * Visualize recursive deletion process step by step
     */
    private TreeNode visualizeRecursiveProcess(TreeNode root, int key, String position) {
        System.out.println("Processing node at " + position + ": " + 
                          (root == null ? "null" : root.val));
        
        if (root == null) {
            System.out.println("  Node is null → return null");
            return null;
        }
        
        if (key < root.val) {
            System.out.println("  Key " + key + " < " + root.val + " → go LEFT");
            root.left = visualizeRecursiveProcess(root.left, key, position + ".left");
            System.out.println("  Return to " + position + " with left child updated");
            return root;
        } else if (key > root.val) {
            System.out.println("  Key " + key + " > " + root.val + " → go RIGHT");
            root.right = visualizeRecursiveProcess(root.right, key, position + ".right");
            System.out.println("  Return to " + position + " with right child updated");
            return root;
        } else {
            System.out.println("  *** NODE FOUND FOR DELETION ***");
            
            // Case 1: No children
            if (root.left == null && root.right == null) {
                System.out.println("  Case 1: Leaf node → return null");
                return null;
            }
            // Case 2: One child
            else if (root.left == null) {
                System.out.println("  Case 2: Only right child → return right child: " + root.right.val);
                return root.right;
            } else if (root.right == null) {
                System.out.println("  Case 2: Only left child → return left child: " + root.left.val);
                return root.left;
            }
            // Case 3: Two children
            else {
                System.out.println("  Case 3: Two children");
                System.out.println("    Finding inorder successor...");
                TreeNode successor = findMin(root.right);
                System.out.println("    Successor found: " + successor.val);
                System.out.println("    Copying successor value to current node");
                root.val = successor.val;
                System.out.println("    Deleting successor from right subtree");
                root.right = visualizeRecursiveProcess(root.right, successor.val, position + ".right");
                System.out.println("  Return updated node: " + root.val);
                return root;
            }
        }
    }
    
    /**
     * Validate BST property
     */
    private boolean isValidBST(TreeNode root) {
        return validateBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean validateBSTHelper(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validateBSTHelper(node.left, min, node.val) && 
               validateBSTHelper(node.right, node.val, max);
    }
    
    /**
     * Analyze deletion impact on tree properties
     */
    public void analyzeDeletionImpact(TreeNode original, TreeNode afterDeletion, int key) {
        System.out.println("\nDeletion Impact Analysis:");
        
        if (original == null) {
            System.out.println("Original tree was empty");
            return;
        }
        
        boolean keyExists = findNode(original, key) != null;
        System.out.println("Key " + key + " exists in original tree: " + keyExists);
        
        if (!keyExists) {
            System.out.println("No deletion performed - trees should be identical");
            return;
        }
        
        int originalHeight = getHeight(original);
        int newHeight = getHeight(afterDeletion);
        int originalSize = countNodes(original);
        int newSize = countNodes(afterDeletion);
        
        System.out.println("Tree Properties:");
        System.out.println("  Original - Height: " + originalHeight + ", Nodes: " + originalSize);
        System.out.println("  New      - Height: " + newHeight + ", Nodes: " + newSize);
        System.out.println("  Height change: " + (newHeight - originalHeight));
        System.out.println("  Size change: " + (newSize - originalSize));
        
        // Check BST property
        System.out.println("BST property maintained: " + isValidBST(afterDeletion));
    }
    
    private TreeNode findNode(TreeNode root, int key) {
        if (root == null) return null;
        if (root.val == key) return root;
        if (key < root.val) return findNode(root.left, key);
        return findNode(root.right, key);
    }
    
    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    
    /**
     * Test different deletion scenarios
     */
    public void testDeletionScenarios() {
        System.out.println("\nDeletion Scenario Tests:");
        
        // Scenario 1: Delete leaf node
        System.out.println("\n1. Delete leaf node (key=2):");
        Integer[] tree1 = {5,3,6,2,4,null,7};
        TreeNode root1 = buildBST(tree1);
        visualizeDeletion(root1, 2, "Recursive");
        
        // Scenario 2: Delete node with one child
        System.out.println("\n2. Delete node with one child (key=6):");
        Integer[] tree2 = {5,3,6,2,4,null,7};
        TreeNode root2 = buildBST(tree2);
        visualizeDeletion(root2, 6, "Recursive");
        
        // Scenario 3: Delete node with two children
        System.out.println("\n3. Delete node with two children (key=3):");
        Integer[] tree3 = {5,3,6,2,4,null,7};
        TreeNode root3 = buildBST(tree3);
        visualizeDeletion(root3, 3, "Recursive");
        
        // Scenario 4: Delete root node
        System.out.println("\n4. Delete root node (key=5):");
        Integer[] tree4 = {5,3,6,2,4,null,7};
        TreeNode root4 = buildBST(tree4);
        visualizeDeletion(root4, 5, "Recursive");
        
        // Scenario 5: Key not found
        System.out.println("\n5. Delete non-existent key (key=10):");
        Integer[] tree5 = {5,3,6,2,4,null,7};
        TreeNode root5 = buildBST(tree5);
        visualizeDeletion(root5, 10, "Recursive");
    }
    
    /**
     * Compare different deletion strategies
     */
    public void compareDeletionStrategies(TreeNode original, int key) {
        System.out.println("\nDeletion Strategy Comparison:");
        
        if (findNode(original, key) == null) {
            System.out.println("Key not found - no deletion performed");
            return;
        }
        
        // Make copies for different strategies
        TreeNode root1 = copyTree(original);
        TreeNode root2 = copyTree(original);
        TreeNode root3 = copyTree(original);
        
        long startTime = System.nanoTime();
        TreeNode result1 = deleteNode(root1, key);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result2 = deleteNodePredecessor(root2, key);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result3 = deleteNodeIterative(root3, key);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Successor Recursive: " + time1 + " ns, Height: " + getHeight(result1));
        System.out.println("Predecessor Recursive: " + time2 + " ns, Height: " + getHeight(result2));
        System.out.println("Iterative: " + time3 + " ns, Height: " + getHeight(result3));
        
        // Check if all produce valid BSTs
        System.out.println("All valid BSTs: " + 
            (isValidBST(result1) && isValidBST(result2) && isValidBST(result3)));
        
        // Check if all produce same in-order traversal
        List<Integer> list1 = bstToList(result1);
        List<Integer> list2 = bstToList(result2);
        List<Integer> list3 = bstToList(result3);
        System.out.println("All same in-order: " + 
            (list1.equals(list2) && list2.equals(list3)));
    }
    
    private TreeNode copyTree(TreeNode root) {
        if (root == null) return null;
        TreeNode newNode = new TreeNode(root.val);
        newNode.left = copyTree(root.left);
        newNode.right = copyTree(root.right);
        return newNode;
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing BST Deletion");
        System.out.println("====================\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test 1: Delete node with two children");
        Integer[] nodes1 = {5,3,6,2,4,null,7};
        TreeNode root1 = solution.buildBST(nodes1);
        int key1 = 3;
        
        TreeNode result1a = solution.deleteNode(copyTree(root1), key1);
        TreeNode result1b = solution.deleteNodeIterative(copyTree(root1), key1);
        
        List<Integer> expected1 = Arrays.asList(2,4,5,6,7);
        List<Integer> result1aList = solution.bstToList(result1a);
        List<Integer> result1bList = solution.bstToList(result1b);
        
        System.out.println("Recursive:  " + result1aList + " - " + 
                         (result1aList.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Iterative:  " + result1bList + " - " + 
                         (result1bList.equals(expected1) ? "PASSED" : "FAILED"));
        
        solution.visualizeDeletion(root1, key1, "Recursive");
        solution.analyzeDeletionImpact(root1, result1a, key1);
        
        // Test Case 2: Delete leaf node
        System.out.println("\nTest 2: Delete leaf node");
        Integer[] nodes2 = {5,3,6,2,4,null,7};
        TreeNode root2 = solution.buildBST(nodes2);
        int key2 = 2;
        
        TreeNode result2 = solution.deleteNode(root2, key2);
        List<Integer> result2List = solution.bstToList(result2);
        System.out.println("Leaf deletion: " + result2List + " - " + 
                         (result2List.equals(Arrays.asList(3,4,5,6,7)) ? "PASSED" : "FAILED"));
        
        // Test Case 3: Delete node with one child
        System.out.println("\nTest 3: Delete node with one child");
        Integer[] nodes3 = {5,3,6,2,4,null,7};
        TreeNode root3 = solution.buildBST(nodes3);
        int key3 = 6;
        
        TreeNode result3 = solution.deleteNode(root3, key3);
        List<Integer> result3List = solution.bstToList(result3);
        System.out.println("One-child deletion: " + result3List + " - " + 
                         (result3List.equals(Arrays.asList(2,3,4,5,7)) ? "PASSED" : "FAILED"));
        
        // Test Case 4: Delete root node
        System.out.println("\nTest 4: Delete root node");
        Integer[] nodes4 = {5,3,6,2,4,null,7};
        TreeNode root4 = solution.buildBST(nodes4);
        int key4 = 5;
        
        TreeNode result4 = solution.deleteNode(root4, key4);
        List<Integer> result4List = solution.bstToList(result4);
        System.out.println("Root deletion - valid BST: " + solution.isValidBST(result4));
        
        // Test Case 5: Key not found
        System.out.println("\nTest 5: Delete non-existent key");
        Integer[] nodes5 = {5,3,6,2,4,null,7};
        TreeNode root5 = solution.buildBST(nodes5);
        int key5 = 10;
        
        TreeNode result5 = solution.deleteNode(root5, key5);
        List<Integer> result5List = solution.bstToList(result5);
        List<Integer> originalList = solution.bstToList(root5);
        System.out.println("Non-existent key: " + result5List + " - " + 
                         (result5List.equals(originalList) ? "PASSED" : "FAILED"));
        
        // Test different scenarios
        solution.testDeletionScenarios();
        
        // Compare strategies
        System.out.println("\nTest 6: Compare deletion strategies");
        Integer[] nodes6 = {50,30,70,20,40,60,80,10,25,35,45,55,65,75,85};
        TreeNode root6 = solution.buildBST(nodes6);
        int key6 = 30;
        solution.compareDeletionStrategies(root6, key6);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BST DELETION ALGORITHM EXPLANATION");
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
        TreeNode largeBST = createLargeBST(10000);
        int deleteKey = 5000; // Key to delete
        
        // Test recursive approach
        long startTime = System.nanoTime();
        TreeNode result1 = solution.deleteNode(copyTree(largeBST), deleteKey);
        long time1 = System.nanoTime() - startTime;
        
        // Test iterative approach
        startTime = System.nanoTime();
        TreeNode result2 = solution.deleteNodeIterative(copyTree(largeBST), deleteKey);
        long time2 = System.nanoTime() - startTime;
        
        System.out.println("Large BST Deletion Performance:");
        System.out.println("Recursive:  " + time1 + " ns, Height: " + solution.getHeight(result1));
        System.out.println("Iterative:  " + time2 + " ns, Height: " + solution.getHeight(result2));
        
        // Test worst-case (deleting from skewed tree)
        TreeNode skewedBST = createSkewedBST(1000, true); // Right-skewed
        int skewedDelete = 500;
        
        startTime = System.nanoTime();
        solution.deleteNode(copyTree(skewedBST), skewedDelete);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Skewed BST Deletion: " + time3 + " ns");
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
    
    /**
     * Create a skewed BST for testing
     */
    private static TreeNode createSkewedBST(int size, boolean rightSkewed) {
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
    
    private static TreeNode copyTree(TreeNode root) {
        if (root == null) return null;
        TreeNode newNode = new TreeNode(root.val);
        newNode.left = copyTree(root.left);
        newNode.right = copyTree(root.right);
        return newNode;
    }
    
    /**
     * Detailed algorithm explanations
     */
    private static void explainAlgorithms() {
        System.out.println("\n1. RECURSIVE APPROACH (RECOMMENDED):");
        System.out.println("   Three Cases for Deletion:");
        System.out.println("   Case 1: Leaf Node (no children)");
        System.out.println("     - Simply remove the node → return null");
        System.out.println("   Case 2: One Child");
        System.out.println("     - Replace node with its only child → return the child");
        System.out.println("   Case 3: Two Children");
        System.out.println("     - Find inorder successor (smallest in right subtree)");
        System.out.println("     - Copy successor value to current node");
        System.out.println("     - Recursively delete the successor from right subtree");
        System.out.println("   Time: O(h), Space: O(h) for recursion stack");
        
        System.out.println("\n2. ITERATIVE APPROACH:");
        System.out.println("   Algorithm:");
        System.out.println("     - Search for node while tracking parent");
        System.out.println("     - Handle three cases with explicit parent updates");
        System.out.println("     - For two-child case: find successor and update links");
        System.out.println("   Advantages:");
        System.out.println("     - O(1) space complexity");
        System.out.println("     - No recursion stack overhead");
        System.out.println("   Disadvantages:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Requires careful parent tracking");
        
        System.out.println("\n3. KEY CONCEPTS:");
        System.out.println("   - Inorder Successor: smallest node in right subtree");
        System.out.println("   - Inorder Predecessor: largest node in left subtree");
        System.out.println("   - BST Property: maintained throughout deletion");
        System.out.println("   - Multiple valid deletion results possible");
        
        System.out.println("\n4. WHY SUCCESSOR/PREDECESSOR WORKS:");
        System.out.println("   - Successor is the next node in inorder traversal");
        System.out.println("   - Predecessor is the previous node in inorder traversal");
        System.out.println("   - Replacing with either maintains BST property");
        System.out.println("   - Successor has at most one right child (easy to delete)");
        System.out.println("   - Predecessor has at most one left child (easy to delete)");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR BST DELETION:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with recursive approach (most intuitive)");
        System.out.println("   - Mention iterative approach for follow-up");
        System.out.println("   - Discuss both successor and predecessor options");
        System.out.println("   - Recommended order: Recursive → Iterative");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Three cases: no children, one child, two children");
        System.out.println("   - Inorder successor for two-child case");
        System.out.println("   - BST property maintenance");
        System.out.println("   - Time complexity: O(h)");
        System.out.println("   - Space complexity trade-offs");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Forgetting to handle all three cases");
        System.out.println("   - Not maintaining BST property");
        System.out.println("   - Incorrect successor/predecessor selection");
        System.out.println("   - Not handling empty tree case");
        System.out.println("   - Infinite recursion in two-child case");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - What if we use predecessor instead of successor?");
        System.out.println("   - How to make deletion more balanced?");
        System.out.println("   - What's the time complexity in worst case?");
        System.out.println("   - How to handle duplicate values?");
        System.out.println("   - How does this compare to AVL tree deletion?");
        
        System.out.println("\n5. Related Problems:");
        System.out.println("   - 701. Insert into a Binary Search Tree");
        System.out.println("   - 700. Search in a Binary Search Tree");
        System.out.println("   - 98. Validate Binary Search Tree");
        System.out.println("   - 99. Recover Binary Search Tree");
        System.out.println("   - 270. Closest Binary Search Tree Value");
        
        System.out.println("\n6. Code Pattern to Remember:");
        System.out.println("   public TreeNode deleteNode(TreeNode root, int key) {");
        System.out.println("     if (root == null) return null;");
        System.out.println("     if (key < root.val) {");
        System.out.println("       root.left = deleteNode(root.left, key);");
        System.out.println("     } else if (key > root.val) {");
        System.out.println("       root.right = deleteNode(root.right, key);");
        System.out.println("     } else {");
        System.out.println("       // Case 1 & 2: 0 or 1 child");
        System.out.println("       if (root.left == null) return root.right;");
        System.out.println("       if (root.right == null) return root.left;");
        System.out.println("       // Case 3: 2 children");
        System.out.println("       TreeNode min = findMin(root.right);");
        System.out.println("       root.val = min.val;");
        System.out.println("       root.right = deleteNode(root.right, min.val);");
        System.out.println("     }");
        System.out.println("     return root;");
        System.out.println("   }");
        
        System.out.println("\n7. Real-world Applications:");
        System.out.println("   - Database record deletion with indexed fields");
        System.out.println("   - File system directory removal");
        System.out.println("   - Cache eviction policies");
        System.out.println("   - Symbol table operations in compilers");
        System.out.println("   - Routing table updates in networks");
    }
}
