
## Solution.java

```java
/**
 * 98. Validate Binary Search Tree
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 * 
 * Key Insights:
 * 1. BST property: left < parent < right for ALL ancestors, not just immediate parent
 * 2. Range-based approach: track valid (min, max) range for each node
 * 3. Inorder traversal of BST produces sorted array
 * 4. Handle edge cases with Integer.MIN_VALUE and Integer.MAX_VALUE using Long
 * 
 * Approach (DFS Range Validation):
 * 1. Start with root and range (Long.MIN_VALUE, Long.MAX_VALUE)
 * 2. Check if current node value is within valid range
 * 3. Recursively validate left subtree with range (min, node.val)
 * 4. Recursively validate right subtree with range (node.val, max)
 * 5. Return true only if all conditions are satisfied
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(h) where h is tree height
 * 
 * Tags: Tree, DFS, BST, Binary Tree
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
     * Approach 1: DFS with Range Validation - RECOMMENDED
     * O(n) time, O(h) space
     */
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean validate(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        
        // Check if current node value is within valid range
        if (node.val <= min || node.val >= max) {
            return false;
        }
        
        // Validate left subtree: all values must be < current node value
        // Validate right subtree: all values must be > current node value
        return validate(node.left, min, node.val) && 
               validate(node.right, node.val, max);
    }
    
    /**
     * Approach 2: Inorder Traversal Iterative
     * O(n) time, O(h) space
     */
    public boolean isValidBSTInorder(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        Integer prev = null;
        
        while (current != null || !stack.isEmpty()) {
            // Go to the leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Process current node
            current = stack.pop();
            
            // Check if current value is greater than previous
            if (prev != null && current.val <= prev) {
                return false;
            }
            
            prev = current.val;
            current = current.right;
        }
        
        return true;
    }
    
    /**
     * Approach 3: Inorder Traversal Recursive
     * O(n) time, O(h) space
     */
    public boolean isValidBSTInorderRecursive(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        
        // Check if inorder traversal is strictly increasing
        for (int i = 1; i < inorder.size(); i++) {
            if (inorder.get(i) <= inorder.get(i - 1)) {
                return false;
            }
        }
        
        return true;
    }
    
    private void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        inorderTraversal(node.left, result);
        result.add(node.val);
        inorderTraversal(node.right, result);
    }
    
    /**
     * Approach 4: Inorder Traversal with Previous Pointer (Optimized)
     * O(n) time, O(h) space - avoids storing entire inorder list
     */
    private TreeNode prev;
    
    public boolean isValidBSTInorderOptimized(TreeNode root) {
        prev = null;
        return inorderCheck(root);
    }
    
    private boolean inorderCheck(TreeNode node) {
        if (node == null) {
            return true;
        }
        
        // Check left subtree
        if (!inorderCheck(node.left)) {
            return false;
        }
        
        // Check current node
        if (prev != null && node.val <= prev.val) {
            return false;
        }
        prev = node;
        
        // Check right subtree
        return inorderCheck(node.right);
    }
    
    /**
     * Approach 5: BFS with Custom Class
     * O(n) time, O(n) space
     */
    public boolean isValidBSTBFS(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Queue<NodeRange> queue = new LinkedList<>();
        queue.offer(new NodeRange(root, Long.MIN_VALUE, Long.MAX_VALUE));
        
        while (!queue.isEmpty()) {
            NodeRange current = queue.poll();
            TreeNode node = current.node;
            long min = current.min;
            long max = current.max;
            
            // Check current node
            if (node.val <= min || node.val >= max) {
                return false;
            }
            
            // Add children to queue with updated ranges
            if (node.left != null) {
                queue.offer(new NodeRange(node.left, min, node.val));
            }
            if (node.right != null) {
                queue.offer(new NodeRange(node.right, node.val, max));
            }
        }
        
        return true;
    }
    
    private class NodeRange {
        TreeNode node;
        long min;
        long max;
        
        NodeRange(TreeNode node, long min, long max) {
            this.node = node;
            this.min = min;
            this.max = max;
        }
    }
    
    /**
     * Helper method to build tree from array (for testing)
     */
    private TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int index = 1;
        
        while (!queue.isEmpty() && index < values.length) {
            TreeNode current = queue.poll();
            
            // Left child
            if (index < values.length && values[index] != null) {
                current.left = new TreeNode(values[index]);
                queue.offer(current.left);
            }
            index++;
            
            // Right child
            if (index < values.length && values[index] != null) {
                current.right = new TreeNode(values[index]);
                queue.offer(current.right);
            }
            index++;
        }
        
        return root;
    }
    
    /**
     * Helper method to visualize tree structure
     */
    private void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        List<String> result = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<String> levelNodes = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                if (current != null) {
                    levelNodes.add(String.valueOf(current.val));
                    queue.offer(current.left);
                    queue.offer(current.right);
                } else {
                    levelNodes.add("null");
                }
            }
            
            // Check if all are null (last level)
            boolean allNull = levelNodes.stream().allMatch("null"::equals);
            if (!allNull) {
                result.add(String.join(", ", levelNodes));
            }
        }
        
        System.out.println("Tree levels:");
        for (String level : result) {
            System.out.println("  " + level);
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Validate Binary Search Tree:");
        System.out.println("====================================");
        
        // Test case 1: Valid BST
        System.out.println("\nTest 1: Valid BST");
        Integer[] values1 = {2, 1, 3};
        TreeNode root1 = solution.buildTree(values1);
        
        long startTime = System.nanoTime();
        boolean result1a = solution.isValidBST(root1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.isValidBSTInorder(root1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1c = solution.isValidBSTInorderRecursive(root1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1d = solution.isValidBSTBFS(root1);
        long time1d = System.nanoTime() - startTime;
        
        boolean expected1 = true;
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        
        System.out.println("DFS Range: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Inorder Iterative: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Inorder Recursive: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the tree
        System.out.println("\nTree visualization for Test 1:");
        solution.printTree(root1);
        System.out.println("BST validation explanation:");
        System.out.println("  Root (2): valid range (-∞, ∞)");
        System.out.println("  Left (1): valid range (-∞, 2) - 1 is within range");
        System.out.println("  Right (3): valid range (2, ∞) - 3 is within range");
        
        // Test case 2: Invalid BST (Example 2)
        System.out.println("\nTest 2: Invalid BST");
        Integer[] values2 = {5, 1, 4, null, null, 3, 6};
        TreeNode root2 = solution.buildTree(values2);
        boolean result2 = solution.isValidBST(root2);
        boolean expected2 = false;
        System.out.println("Invalid BST: " + result2 + " - " + 
                         (result2 == expected2 ? "PASSED" : "FAILED"));
        
        System.out.println("\nTree visualization for Test 2:");
        solution.printTree(root2);
        System.out.println("BST violation explanation:");
        System.out.println("  Root (5): valid");
        System.out.println("  Left (1): valid range (-∞, 5)");
        System.out.println("  Right (4): valid range (5, ∞) - BUT 4 < 5, so INVALID");
        System.out.println("  The right child should be > parent, but 4 < 5");
        
        // Test case 3: Single node
        System.out.println("\nTest 3: Single node");
        TreeNode root3 = new TreeNode(1);
        boolean result3 = solution.isValidBST(root3);
        System.out.println("Single node: " + result3 + " - " + 
                         (result3 == true ? "PASSED" : "FAILED"));
        
        // Test case 4: Left child equals parent (invalid)
        System.out.println("\nTest 4: Left child equals parent");
        TreeNode root4 = new TreeNode(2);
        root4.left = new TreeNode(2);
        boolean result4 = solution.isValidBST(root4);
        System.out.println("Left child equals parent: " + result4 + " - " + 
                         (result4 == false ? "PASSED" : "FAILED"));
        
        // Test case 5: Right child equals parent (invalid)
        System.out.println("\nTest 5: Right child equals parent");
        TreeNode root5 = new TreeNode(2);
        root5.right = new TreeNode(2);
        boolean result5 = solution.isValidBST(root5);
        System.out.println("Right child equals parent: " + result5 + " - " + 
                         (result5 == false ? "PASSED" : "FAILED"));
        
        // Test case 6: Complex valid BST
        System.out.println("\nTest 6: Complex valid BST");
        TreeNode root6 = new TreeNode(10);
        root6.left = new TreeNode(5);
        root6.right = new TreeNode(15);
        root6.left.left = new TreeNode(3);
        root6.left.right = new TreeNode(7);
        root6.right.left = new TreeNode(12);
        root6.right.right = new TreeNode(18);
        boolean result6 = solution.isValidBST(root6);
        System.out.println("Complex valid BST: " + result6 + " - " + 
                         (result6 == true ? "PASSED" : "FAILED"));
        
        // Test case 7: Complex invalid BST
        System.out.println("\nTest 7: Complex invalid BST");
        TreeNode root7 = new TreeNode(10);
        root7.left = new TreeNode(5);
        root7.right = new TreeNode(15);
        root7.left.left = new TreeNode(3);
        root7.left.right = new TreeNode(11); // Invalid: 11 > 10
        boolean result7 = solution.isValidBST(root7);
        System.out.println("Complex invalid BST: " + result7 + " - " + 
                         (result7 == false ? "PASSED" : "FAILED"));
        
        // Test case 8: Edge case with Integer bounds
        System.out.println("\nTest 8: Edge case with Integer bounds");
        TreeNode root8 = new TreeNode(Integer.MAX_VALUE);
        boolean result8 = solution.isValidBST(root8);
        System.out.println("Integer.MAX_VALUE node: " + result8 + " - " + 
                         (result8 == true ? "PASSED" : "FAILED"));
        
        // Test case 9: Another edge case
        System.out.println("\nTest 9: Another edge case");
        TreeNode root9 = new TreeNode(Integer.MIN_VALUE);
        root9.right = new TreeNode(Integer.MAX_VALUE);
        boolean result9 = solution.isValidBST(root9);
        System.out.println("MIN_VALUE -> MAX_VALUE: " + result9 + " - " + 
                         (result9 == true ? "PASSED" : "FAILED"));
        
        // Test case 10: Invalid due to grandparent constraint
        System.out.println("\nTest 10: Invalid due to grandparent constraint");
        TreeNode root10 = new TreeNode(10);
        root10.left = new TreeNode(5);
        root10.right = new TreeNode(15);
        root10.right.left = new TreeNode(6); // Invalid: 6 < 10
        root10.right.right = new TreeNode(20);
        boolean result10 = solution.isValidBST(root10);
        System.out.println("Grandparent violation: " + result10 + " - " + 
                         (result10 == false ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  DFS Range: " + time1a + " ns");
        System.out.println("  Inorder Iterative: " + time1b + " ns");
        System.out.println("  Inorder Recursive: " + time1c + " ns");
        System.out.println("  BFS: " + time1d + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DFS RANGE VALIDATION ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("In a BST, each node must be within a specific range determined by its ancestors.");
        System.out.println("For any node, all left descendants must be < node.val and all right descendants must be > node.val.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Start with root and range (Long.MIN_VALUE, Long.MAX_VALUE)");
        System.out.println("2. Check if current node value is within (min, max)");
        System.out.println("3. Validate left subtree with range (min, node.val)");
        System.out.println("4. Validate right subtree with range (node.val, max)");
        System.out.println("5. Return true only if all conditions are satisfied");
        
        System.out.println("\nWhy use Long instead of Integer?");
        System.out.println("- To handle edge cases where node values are Integer.MIN_VALUE or Integer.MAX_VALUE");
        System.out.println("- Example: If root value is Integer.MAX_VALUE, right child would need to be > Integer.MAX_VALUE (impossible)");
        System.out.println("- Using Long ensures we can represent the full range");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. DFS Range Validation (RECOMMENDED):");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(h) - Recursion stack height");
        System.out.println("   How it works:");
        System.out.println("     - Tracks valid range for each node based on ancestors");
        System.out.println("     - Directly implements BST definition");
        System.out.println("   Pros:");
        System.out.println("     - Most intuitive approach");
        System.out.println("     - Handles all edge cases correctly");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("   Cons:");
        System.out.println("     - Recursion stack space");
        System.out.println("     - Need to handle integer bounds carefully");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Inorder Traversal Iterative:");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(h) - Stack height");
        System.out.println("   How it works:");
        System.out.println("     - Performs inorder traversal using stack");
        System.out.println("     - Checks if sequence is strictly increasing");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack overflow risk");
        System.out.println("     - Clear mathematical property (sorted order)");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Still uses O(h) space");
        System.out.println("   Best for: When avoiding recursion, educational purposes");
        
        System.out.println("\n3. Inorder Traversal Recursive:");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(h + n) - Recursion stack + list storage");
        System.out.println("   How it works:");
        System.out.println("     - Collects inorder traversal in list");
        System.out.println("     - Checks if list is sorted");
        System.out.println("   Pros:");
        System.out.println("     - Simple to understand");
        System.out.println("     - Clear verification step");
        System.out.println("   Cons:");
        System.out.println("     - O(n) extra space for list");
        System.out.println("     - Two passes (traversal + verification)");
        System.out.println("   Best for: Learning purposes, small trees");
        
        System.out.println("\n4. BFS with Range Validation:");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(n) - Queue storage");
        System.out.println("   How it works:");
        System.out.println("     - Level-order traversal with range tracking");
        System.out.println("     - Uses custom class to store node and its valid range");
        System.out.println("   Pros:");
        System.out.println("     - No recursion");
        System.out.println("     - Level-by-level processing");
        System.out.println("   Cons:");
        System.out.println("     - More memory for queue");
        System.out.println("     - Custom class overhead");
        System.out.println("   Best for: Very deep trees, when avoiding recursion");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES AND PITFALLS:");
        System.out.println("1. Only checking immediate children instead of entire subtree");
        System.out.println("2. Using <= or >= instead of strict inequalities");
        System.out.println("3. Not handling integer overflow with MIN_VALUE/MAX_VALUE");
        System.out.println("4. Forgetting that BST requires STRICTLY increasing inorder traversal");
        System.out.println("5. Assuming only parent constraints instead of ancestor constraints");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with DFS Range approach - it's the most intuitive");
        System.out.println("2. Clearly explain why you're using Long instead of Integer");
        System.out.println("3. Mention the BST definition and why range approach works");
        System.out.println("4. Discuss time and space complexity clearly");
        System.out.println("5. Consider edge cases: single node, duplicate values, integer bounds");
        System.out.println("6. Be prepared to discuss alternative approaches (inorder)");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
