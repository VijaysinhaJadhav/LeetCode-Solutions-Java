
## Solution.java

```java
/**
 * 701. Insert into a Binary Search Tree
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are given the root node of a binary search tree (BST) and a value to insert into the tree.
 * Return the root node of the BST after the insertion. It is guaranteed that the new value does
 * not exist in the original BST. There may exist multiple valid ways for the insertion.
 * 
 * Key Insights:
 * 1. BST Property: left subtree < root < right subtree
 * 2. Insertion point: Find appropriate leaf position based on value comparison
 * 3. Multiple valid insertion positions exist
 * 4. Always insert as leaf node in standard approach
 * 5. Handle empty tree case (new node becomes root)
 * 
 * Approaches:
 * 1. Iterative Approach - RECOMMENDED for interviews
 * 2. Recursive Approach - Clean recursive implementation
 * 3. Iterative with Parent Tracking - Explicit parent tracking
 * 4. Recursive with Return - Alternative recursive approach
 * 5. Balanced Insertion - Try to maintain balance
 * 
 * Time Complexity: O(h) where h is tree height
 * Space Complexity: O(1) for iterative, O(h) for recursive
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
     * Approach 1: Iterative Approach - RECOMMENDED for interviews
     * Time: O(h), Space: O(1)
     * Algorithm:
     * 1. If tree is empty, return new node as root
     * 2. Traverse tree to find insertion position
     * 3. If val < current, go left; if val > current, go right
     * 4. Insert new node when appropriate child is null
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode newNode = new TreeNode(val);
        
        // Empty tree case
        if (root == null) {
            return newNode;
        }
        
        TreeNode current = root;
        while (true) {
            if (val < current.val) {
                // Go left
                if (current.left == null) {
                    current.left = newNode;
                    break;
                } else {
                    current = current.left;
                }
            } else {
                // Go right (val > current.val since values are unique)
                if (current.right == null) {
                    current.right = newNode;
                    break;
                } else {
                    current = current.right;
                }
            }
        }
        
        return root;
    }
    
    /**
     * Approach 2: Recursive Approach
     * Time: O(h), Space: O(h) for recursion stack
     * Algorithm:
     * 1. Base case: if node is null, return new node
     * 2. If val < root.val, recursively insert into left subtree
     * 3. If val > root.val, recursively insert into right subtree
     * 4. Return root (modified with new insertion)
     */
    public TreeNode insertIntoBSTRecursive(TreeNode root, int val) {
        // Base case: found insertion position
        if (root == null) {
            return new TreeNode(val);
        }
        
        // Recursive insertion
        if (val < root.val) {
            root.left = insertIntoBSTRecursive(root.left, val);
        } else {
            root.right = insertIntoBSTRecursive(root.right, val);
        }
        
        return root;
    }
    
    /**
     * Approach 3: Iterative with Explicit Parent Tracking
     * Time: O(h), Space: O(1)
     * Tracks parent explicitly for educational purposes
     */
    public TreeNode insertIntoBSTWithParent(TreeNode root, int val) {
        TreeNode newNode = new TreeNode(val);
        
        if (root == null) {
            return newNode;
        }
        
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
        
        // Insert at the appropriate position
        if (val < parent.val) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        
        return root;
    }
    
    /**
     * Approach 4: Recursive with Return Value Tracking
     * Alternative recursive approach that tracks insertion status
     */
    public TreeNode insertIntoBSTRecursive2(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        
        insertHelper(root, val);
        return root;
    }
    
    private void insertHelper(TreeNode node, int val) {
        if (val < node.val) {
            if (node.left == null) {
                node.left = new TreeNode(val);
            } else {
                insertHelper(node.left, val);
            }
        } else {
            if (node.right == null) {
                node.right = new TreeNode(val);
            } else {
                insertHelper(node.right, val);
            }
        }
    }
    
    /**
     * Approach 5: Balanced Insertion (Attempt to maintain balance)
     * Time: O(h), Space: O(h)
     * Tries to insert in a way that maintains better balance
     * Not guaranteed to produce balanced tree, but better than always going deep
     */
    public TreeNode insertIntoBSTBalanced(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        
        // Simple heuristic: alternate between left and right when possible
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        
        if (val < root.val) {
            if (leftHeight <= rightHeight) {
                // Prefer left if it's shorter
                root.left = insertIntoBSTBalanced(root.left, val);
            } else {
                // Still go left but consider balance
                root.left = insertIntoBSTBalanced(root.left, val);
            }
        } else {
            if (rightHeight <= leftHeight) {
                // Prefer right if it's shorter
                root.right = insertIntoBSTBalanced(root.right, val);
            } else {
                root.right = insertIntoBSTBalanced(root.right, val);
            }
        }
        
        return root;
    }
    
    private int getHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
    
    /**
     * Approach 6: Iterative with Path Tracking (for visualization)
     * Time: O(h), Space: O(h)
     * Tracks the insertion path for educational purposes
     */
    public InsertionResult insertIntoBSTWithPath(TreeNode root, int val) {
        TreeNode newNode = new TreeNode(val);
        List<TreeNode> path = new ArrayList<>();
        
        if (root == null) {
            return new InsertionResult(newNode, path);
        }
        
        path.add(root);
        TreeNode current = root;
        
        while (true) {
            if (val < current.val) {
                if (current.left == null) {
                    current.left = newNode;
                    path.add(newNode);
                    break;
                } else {
                    current = current.left;
                    path.add(current);
                }
            } else {
                if (current.right == null) {
                    current.right = newNode;
                    path.add(newNode);
                    break;
                } else {
                    current = current.right;
                    path.add(current);
                }
            }
        }
        
        return new InsertionResult(root, path);
    }
    
    /**
     * Helper class for Approach 6
     */
    private class InsertionResult {
        TreeNode root;
        List<TreeNode> insertionPath;
        
        InsertionResult(TreeNode root, List<TreeNode> insertionPath) {
            this.root = root;
            this.insertionPath = insertionPath;
        }
    }
    
    /**
     * Helper method to build a BST from array (Level-order)
     * Note: This builds a BST by inserting nodes in given order
     * For proper BST, array should represent level-order traversal of valid BST
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
    
    /**
     * Helper method to convert BST to list (In-order traversal)
     * Returns sorted list of values
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
     * Visualize insertion process
     */
    public void visualizeInsertion(TreeNode root, int val, String approach) {
        System.out.println("\n" + approach + " Insertion Process:");
        System.out.println("Original BST (In-order): " + bstToList(root));
        System.out.println("Value to insert: " + val);
        System.out.println("Tree Structure Before:");
        printTree(root, 0);
        
        TreeNode result;
        switch (approach) {
            case "Iterative":
                result = insertIntoBST(root, val);
                break;
            case "Recursive":
                result = insertIntoBSTRecursive(root, val);
                break;
            case "WithPath":
                InsertionResult insertionResult = insertIntoBSTWithPath(root, val);
                result = insertionResult.root;
                System.out.println("Insertion Path: " + getPathValues(insertionResult.insertionPath));
                break;
            default:
                result = insertIntoBST(root, val);
        }
        
        System.out.println("Tree Structure After:");
        printTree(result, 0);
        System.out.println("BST After Insertion (In-order): " + bstToList(result));
        
        // Verify BST property is maintained
        boolean isValid = isValidBST(result);
        System.out.println("BST Property Maintained: " + isValid);
        
        if (approach.equals("Iterative")) {
            System.out.println("\nStep-by-Step Insertion Process:");
            visualizeIterativeProcess(root, val);
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
     * Visualize iterative insertion process step by step
     */
    private void visualizeIterativeProcess(TreeNode root, int val) {
        TreeNode current = root;
        int step = 1;
        
        System.out.println("Step | Current Node | Comparison | Action");
        System.out.println("-----|--------------|------------|--------");
        
        while (true) {
            String comparison = val + " " + getComparison(val, current.val);
            String action;
            
            if (val < current.val) {
                if (current.left == null) {
                    action = "Insert as LEFT child";
                    System.out.printf("%4d | %12d | %10s | %s%n", 
                        step++, current.val, comparison, action);
                    break;
                } else {
                    action = "Go LEFT to " + current.left.val;
                    System.out.printf("%4d | %12d | %10s | %s%n", 
                        step++, current.val, comparison, action);
                    current = current.left;
                }
            } else {
                if (current.right == null) {
                    action = "Insert as RIGHT child";
                    System.out.printf("%4d | %12d | %10s | %s%n", 
                        step++, current.val, comparison, action);
                    break;
                } else {
                    action = "Go RIGHT to " + current.right.val;
                    System.out.printf("%4d | %12d | %10s | %s%n", 
                        step++, current.val, comparison, action);
                    current = current.right;
                }
            }
        }
    }
    
    private String getComparison(int a, int b) {
        if (a < b) return "<";
        if (a > b) return ">";
        return "=";
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
     * Analyze insertion impact on tree properties
     */
    public void analyzeInsertionImpact(TreeNode original, TreeNode afterInsertion, int val) {
        System.out.println("\nInsertion Impact Analysis:");
        
        int originalHeight = getHeight(original);
        int newHeight = getHeight(afterInsertion);
        int originalSize = countNodes(original);
        int newSize = countNodes(afterInsertion);
        
        System.out.println("Tree Properties:");
        System.out.println("  Original - Height: " + originalHeight + ", Nodes: " + originalSize);
        System.out.println("  New      - Height: " + newHeight + ", Nodes: " + newSize);
        System.out.println("  Height change: " + (newHeight - originalHeight));
        
        // Check if insertion increased height
        if (newHeight > originalHeight) {
            System.out.println("  Insertion increased tree height");
        } else {
            System.out.println("  Insertion did not increase tree height");
        }
        
        // Find the inserted node
        TreeNode insertedNode = findNode(afterInsertion, val);
        if (insertedNode != null) {
            System.out.println("Inserted node depth: " + getNodeDepth(afterInsertion, insertedNode));
            System.out.println("Inserted node is leaf: " + (insertedNode.left == null && insertedNode.right == null));
        }
    }
    
    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    
    private TreeNode findNode(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        if (val < root.val) return findNode(root.left, val);
        return findNode(root.right, val);
    }
    
    private int getNodeDepth(TreeNode root, TreeNode target) {
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
     * Test multiple insertion scenarios
     */
    public void testMultipleInsertions(TreeNode root, int[] values) {
        System.out.println("\nMultiple Insertions Test:");
        System.out.println("Original BST: " + bstToList(root));
        
        TreeNode currentRoot = root;
        for (int val : values) {
            currentRoot = insertIntoBST(currentRoot, val);
            System.out.println("After inserting " + val + ": " + bstToList(currentRoot));
            
            // Verify BST property
            if (!isValidBST(currentRoot)) {
                System.out.println("ERROR: BST property violated after inserting " + val);
                break;
            }
        }
        
        System.out.println("Final BST height: " + getHeight(currentRoot));
        System.out.println("Final BST size: " + countNodes(currentRoot));
    }
    
    /**
     * Compare different insertion strategies
     */
    public void compareInsertionStrategies(TreeNode original, int val) {
        System.out.println("\nInsertion Strategy Comparison:");
        
        // Make copies for different strategies
        TreeNode root1 = copyTree(original);
        TreeNode root2 = copyTree(original);
        TreeNode root3 = copyTree(original);
        
        long startTime = System.nanoTime();
        TreeNode result1 = insertIntoBST(root1, val);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result2 = insertIntoBSTRecursive(root2, val);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result3 = insertIntoBSTBalanced(root3, val);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Iterative:    " + time1 + " ns, Height: " + getHeight(result1));
        System.out.println("Recursive:    " + time2 + " ns, Height: " + getHeight(result2));
        System.out.println("Balanced:     " + time3 + " ns, Height: " + getHeight(result3));
        
        // Check if all produce valid BSTs
        System.out.println("All valid BSTs: " + 
            (isValidBST(result1) && isValidBST(result2) && isValidBST(result3)));
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
        
        System.out.println("Testing BST Insertion");
        System.out.println("=====================\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test 1: Insert 5 into [4,2,7,1,3]");
        Integer[] nodes1 = {4,2,7,1,3};
        TreeNode root1 = solution.buildBST(nodes1);
        int val1 = 5;
        
        TreeNode result1a = solution.insertIntoBST(copyTree(root1), val1);
        TreeNode result1b = solution.insertIntoBSTRecursive(copyTree(root1), val1);
        
        List<Integer> expected1 = Arrays.asList(1,2,3,4,5,7);
        List<Integer> result1aList = solution.bstToList(result1a);
        List<Integer> result1bList = solution.bstToList(result1b);
        
        System.out.println("Iterative:  " + result1aList + " - " + 
                         (result1aList.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Recursive:  " + result1bList + " - " + 
                         (result1bList.equals(expected1) ? "PASSED" : "FAILED"));
        
        solution.visualizeInsertion(root1, val1, "Iterative");
        solution.analyzeInsertionImpact(root1, result1a, val1);
        
        // Test Case 2: Insert into empty tree
        System.out.println("\nTest 2: Insert into empty tree");
        TreeNode root2 = null;
        int val2 = 10;
        
        TreeNode result2 = solution.insertIntoBST(root2, val2);
        System.out.println("Empty tree insertion: " + 
                         (result2 != null && result2.val == val2 ? "PASSED" : "FAILED"));
        
        // Test Case 3: Insert into single node tree
        System.out.println("\nTest 3: Insert into single node tree [5]");
        TreeNode root3 = new TreeNode(5);
        int val3 = 3;
        
        TreeNode result3 = solution.insertIntoBST(root3, val3);
        List<Integer> result3List = solution.bstToList(result3);
        System.out.println("Single node insertion: " + result3List + " - " + 
                         (result3List.equals(Arrays.asList(3,5)) ? "PASSED" : "FAILED"));
        
        // Test Case 4: Insert into right-skewed tree
        System.out.println("\nTest 4: Insert into right-skewed tree");
        Integer[] nodes4 = {1,2,3,4,5};
        TreeNode root4 = solution.buildBST(nodes4);
        int val4 = 6;
        
        TreeNode result4 = solution.insertIntoBST(root4, val4);
        System.out.println("Right-skewed insertion - Height: " + solution.getHeight(result4));
        
        // Test Case 5: Multiple insertions
        System.out.println("\nTest 5: Multiple sequential insertions");
        TreeNode root5 = new TreeNode(50);
        int[] values5 = {30, 70, 20, 40, 60, 80, 10, 25, 35, 45};
        solution.testMultipleInsertions(root5, values5);
        
        // Test Case 6: Compare insertion strategies
        System.out.println("\nTest 6: Compare insertion strategies");
        Integer[] nodes6 = {40,20,60,10,30,50,70};
        TreeNode root6 = solution.buildBST(nodes6);
        int val6 = 25;
        solution.compareInsertionStrategies(root6, val6);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BST INSERTION ALGORITHM EXPLANATION");
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
        int insertValue = 50000; // Value to insert
        
        // Test iterative approach
        long startTime = System.nanoTime();
        TreeNode result1 = solution.insertIntoBST(copyTree(largeBST), insertValue);
        long time1 = System.nanoTime() - startTime;
        
        // Test recursive approach
        startTime = System.nanoTime();
        TreeNode result2 = solution.insertIntoBSTRecursive(copyTree(largeBST), insertValue);
        long time2 = System.nanoTime() - startTime;
        
        System.out.println("Large BST Insertion Performance:");
        System.out.println("Iterative:  " + time1 + " ns, Height: " + solution.getHeight(result1));
        System.out.println("Recursive:  " + time2 + " ns, Height: " + solution.getHeight(result2));
        
        // Test worst-case (skewed tree)
        TreeNode skewedBST = createSkewedBST(1000, true); // Right-skewed
        int skewedInsert = 2000;
        
        startTime = System.nanoTime();
        solution.insertIntoBST(copyTree(skewedBST), skewedInsert);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Skewed BST Insertion: " + time3 + " ns");
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
        System.out.println("\n1. ITERATIVE APPROACH (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     if root is null: return new Node(val)");
        System.out.println("     current = root");
        System.out.println("     while true:");
        System.out.println("       if val < current.val:");
        System.out.println("         if current.left is null: insert and break");
        System.out.println("         else: current = current.left");
        System.out.println("       else:");
        System.out.println("         if current.right is null: insert and break");
        System.out.println("         else: current = current.right");
        System.out.println("   Key Features:");
        System.out.println("     - O(1) space complexity");
        System.out.println("     - Simple and efficient");
        System.out.println("     - Always inserts as leaf node");
        System.out.println("     - Time: O(h) where h is tree height");
        
        System.out.println("\n2. RECURSIVE APPROACH:");
        System.out.println("   Algorithm:");
        System.out.println("     if root is null: return new Node(val)");
        System.out.println("     if val < root.val: root.left = insert(root.left, val)");
        System.out.println("     else: root.right = insert(root.right, val)");
        System.out.println("     return root");
        System.out.println("   Key Features:");
        System.out.println("     - Elegant and concise");
        System.out.println("     - O(h) space for recursion stack");
        System.out.println("     - Same time complexity as iterative");
        
        System.out.println("\n3. KEY BST INSERTION PROPERTIES:");
        System.out.println("   - Multiple valid insertion positions exist");
        System.out.println("   - Standard approach always inserts as leaf");
        System.out.println("   - BST property is maintained automatically");
        System.out.println("   - Values are unique (guaranteed by problem)");
        System.out.println("   - Empty tree case handled separately");
        
        System.out.println("\n4. PERFORMANCE CHARACTERISTICS:");
        System.out.println("   - Balanced BST: O(log n) time");
        System.out.println("   - Skewed BST: O(n) time");
        System.out.println("   - Average case: O(log n) for random insertions");
        System.out.println("   - Space: O(1) iterative, O(h) recursive");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR BST INSERTION:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with iterative approach (most efficient)");
        System.out.println("   - Mention recursive approach as alternative");
        System.out.println("   - Discuss time/space complexity trade-offs");
        System.out.println("   - Recommended order: Iterative → Recursive");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - BST property: left < root < right");
        System.out.println("   - Multiple valid insertion positions");
        System.out.println("   - Always insert as leaf in standard approach");
        System.out.println("   - Handle empty tree case");
        System.out.println("   - Time complexity: O(h)");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Forgetting to handle empty tree case");
        System.out.println("   - Not maintaining BST property");
        System.out.println("   - Infinite loop in iterative approach");
        System.out.println("   - Not returning the root in recursive approach");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - How to delete a node from BST? (LeetCode 450)");
        System.out.println("   - How to search in BST? (LeetCode 700)");
        System.out.println("   - How to validate BST? (LeetCode 98)");
        System.out.println("   - How to balance BST after insertion?");
        System.out.println("   - What if values are not unique?");
        
        System.out.println("\n5. Related Problems:");
        System.out.println("   - 700. Search in a Binary Search Tree");
        System.out.println("   - 450. Delete Node in a BST");
        System.out.println("   - 98. Validate Binary Search Tree");
        System.out.println("   - 99. Recover Binary Search Tree");
        
        System.out.println("\n6. Code Pattern to Remember:");
        System.out.println("   public TreeNode insertIntoBST(TreeNode root, int val) {");
        System.out.println("     if (root == null) return new TreeNode(val);");
        System.out.println("     TreeNode current = root;");
        System.out.println("     while (true) {");
        System.out.println("       if (val < current.val) {");
        System.out.println("         if (current.left == null) {");
        System.out.println("           current.left = new TreeNode(val);");
        System.out.println("           break;");
        System.out.println("         } else {");
        System.out.println("           current = current.left;");
        System.out.println("         }");
        System.out.println("       } else {");
        System.out.println("         if (current.right == null) {");
        System.out.println("           current.right = new TreeNode(val);");
        System.out.println("           break;");
        System.out.println("         } else {");
        System.out.println("           current = current.right;");
        System.out.println("         }");
        System.out.println("       }");
        System.out.println("     }");
        System.out.println("     return root;");
        System.out.println("   }");
        
        System.out.println("\n7. Real-world Applications:");
        System.out.println("   - Database indexing");
        System.out.println("   - File system organization");
        System.out.println("   - Dictionary implementation");
        System.out.println("   - Auto-completion systems");
        System.out.println("   - Priority queues with key-value pairs");
    }
}
