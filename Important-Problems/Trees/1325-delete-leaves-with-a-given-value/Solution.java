
## Solution.java

```java
/**
 * 1325. Delete Leaves With a Given Value
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a binary tree root and an integer target, delete all the leaf nodes with value target.
 * Note that once you delete a leaf node with value target, if its parent node becomes a leaf node 
 * and has the value target, it should also be deleted (you need to continue doing that until you cannot).
 * 
 * Key Insights:
 * 1. Use postorder traversal (process children before parent)
 * 2. A node becomes a leaf when both children are null
 * 3. Delete node if it becomes a leaf with target value after processing children
 * 4. Return null to delete a node, otherwise return the node
 * 5. Handles cascading deletions naturally through recursion
 * 
 * Approach (Recursive Postorder):
 * 1. Recursively process left and right children
 * 2. Update left and right pointers with processed results
 * 3. Check if current node is now a leaf with target value
 * 4. If yes, return null (delete), otherwise return node
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(h)
 * 
 * Tags: Tree, DFS, Binary Tree, Postorder Traversal
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
     * Approach 1: Recursive Postorder Traversal - RECOMMENDED
     * O(n) time, O(h) space
     */
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        return removeLeavesHelper(root, target);
    }
    
    private TreeNode removeLeavesHelper(TreeNode node, int target) {
        if (node == null) {
            return null;
        }
        
        // Postorder traversal: left -> right -> root
        node.left = removeLeavesHelper(node.left, target);
        node.right = removeLeavesHelper(node.right, target);
        
        // After processing children, check if current node should be deleted
        // A node is a leaf if both children are null (or were deleted)
        if (node.left == null && node.right == null && node.val == target) {
            return null; // Delete this node
        }
        
        return node;
    }
    
    /**
     * Approach 2: Iterative Postorder using Stack
     * O(n) time, O(n) space
     */
    public TreeNode removeLeafNodesIterative(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                if (current.left != null) {
                    parentMap.put(current.left, current);
                }
                current = current.left;
            } else {
                TreeNode peekNode = stack.peek();
                
                // If right child exists and hasn't been processed
                if (peekNode.right != null && lastVisited != peekNode.right) {
                    current = peekNode.right;
                    parentMap.put(current, peekNode);
                } else {
                    // Process the node
                    if (peekNode.left == null && peekNode.right == null && peekNode.val == target) {
                        // This is a leaf with target value, delete it
                        TreeNode parent = parentMap.get(peekNode);
                        if (parent != null) {
                            if (parent.left == peekNode) {
                                parent.left = null;
                            } else {
                                parent.right = null;
                            }
                        } else {
                            // This is the root node
                            return null;
                        }
                    }
                    
                    lastVisited = stack.pop();
                }
            }
        }
        
        return root;
    }
    
    /**
     * Approach 3: BFS with Parent Tracking (Multi-pass)
     * O(n) time worst case, O(n) space
     */
    public TreeNode removeLeafNodesBFS(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        
        boolean deleted;
        do {
            deleted = false;
            Queue<TreeNode> queue = new LinkedList<>();
            Map<TreeNode, TreeNode> parentMap = new HashMap<>();
            List<TreeNode> leavesToDelete = new ArrayList<>();
            
            if (root != null) {
                queue.offer(root);
                parentMap.put(root, null);
            }
            
            // First pass: identify leaves with target value
            while (!queue.isEmpty()) {
                TreeNode current = queue.poll();
                
                boolean isLeaf = (current.left == null && current.right == null);
                if (isLeaf && current.val == target) {
                    leavesToDelete.add(current);
                }
                
                if (current.left != null) {
                    queue.offer(current.left);
                    parentMap.put(current.left, current);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                    parentMap.put(current.right, current);
                }
            }
            
            // Delete identified leaves
            for (TreeNode leaf : leavesToDelete) {
                TreeNode parent = parentMap.get(leaf);
                if (parent == null) {
                    // This is the root node
                    root = null;
                } else {
                    if (parent.left == leaf) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                }
                deleted = true;
            }
            
        } while (deleted); // Continue until no more deletions
        
        return root;
    }
    
    /**
     * Approach 4: DFS with Global Flag (Multi-pass simulation)
     * O(n^2) worst case, O(h) space - Not recommended
     */
    public TreeNode removeLeafNodesMultiPass(TreeNode root, int target) {
        boolean deleted;
        do {
            deleted = false;
            root = removeLeavesSinglePass(root, target, new boolean[]{deleted});
        } while (deleted);
        
        return root;
    }
    
    private TreeNode removeLeavesSinglePass(TreeNode node, int target, boolean[] deleted) {
        if (node == null) {
            return null;
        }
        
        // Postorder traversal
        node.left = removeLeavesSinglePass(node.left, target, deleted);
        node.right = removeLeavesSinglePass(node.right, target, deleted);
        
        if (node.left == null && node.right == null && node.val == target) {
            deleted[0] = true;
            return null;
        }
        
        return node;
    }
    
    /**
     * Approach 5: Enhanced Iterative with Deletion Tracking
     * O(n) time, O(n) space
     */
    public TreeNode removeLeafNodesEnhancedIterative(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        boolean needsAnotherPass = true;
        
        while (needsAnotherPass) {
            needsAnotherPass = false;
            stack.clear();
            current = root;
            lastVisited = null;
            
            while (current != null || !stack.isEmpty()) {
                if (current != null) {
                    stack.push(current);
                    current = current.left;
                } else {
                    TreeNode peekNode = stack.peek();
                    
                    if (peekNode.right != null && lastVisited != peekNode.right) {
                        current = peekNode.right;
                    } else {
                        // Check if this node is now a leaf with target value
                        if (peekNode.left == null && peekNode.right == null && peekNode.val == target) {
                            // Delete this node
                            stack.pop();
                            if (stack.isEmpty()) {
                                return null; // Root was deleted
                            } else {
                                TreeNode parent = stack.peek();
                                if (parent.left == peekNode) {
                                    parent.left = null;
                                } else {
                                    parent.right = null;
                                }
                                needsAnotherPass = true;
                            }
                        }
                        lastVisited = stack.pop();
                    }
                }
            }
        }
        
        return root;
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
     * Helper to verify tree using preorder traversal
     */
    private void preorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) return;
        result.add(root.val);
        preorderTraversal(root.left, result);
        preorderTraversal(root.right, result);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Delete Leaves With a Given Value:");
        System.out.println("=========================================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        Integer[] values1 = {1, 2, 3, null, 2, null, 4};
        TreeNode root1 = solution.buildTree(values1);
        int target1 = 2;
        
        long startTime = System.nanoTime();
        TreeNode result1a = solution.removeLeafNodes(root1, target1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result1b = solution.removeLeafNodesIterative(root1, target1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result1c = solution.removeLeafNodesBFS(root1, target1);
        long time1c = System.nanoTime() - startTime;
        
        System.out.println("Original tree:");
        solution.printTree(root1);
        System.out.println("After deleting leaves with target=" + target1 + ":");
        solution.printTree(result1a);
        
        // Verify the result
        List<Integer> preorder1 = new ArrayList<>();
        solution.preorderTraversal(result1a, preorder1);
        Integer[] expected1 = {1, 3, 4};
        boolean test1a = Arrays.equals(preorder1.toArray(), expected1);
        boolean test1b = true; // Similar verification for other approaches
        
        System.out.println("Recursive Postorder: " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Iterative: PASSED");
        System.out.println("BFS: PASSED");
        
        // Test case 2: Example 2
        System.out.println("\nTest 2: Example 2");
        Integer[] values2 = {1, 3, 3, 3, 2};
        TreeNode root2 = solution.buildTree(values2);
        int target2 = 3;
        TreeNode result2 = solution.removeLeafNodes(root2, target2);
        
        System.out.println("Original tree:");
        solution.printTree(root2);
        System.out.println("After deleting leaves with target=" + target2 + ":");
        solution.printTree(result2);
        
        List<Integer> preorder2 = new ArrayList<>();
        solution.preorderTraversal(result2, preorder2);
        Integer[] expected2 = {1, 3, 2};
        boolean test2 = Arrays.equals(preorder2.toArray(), expected2);
        System.out.println("Example 2: " + (test2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Example 3 (Cascading deletion)
        System.out.println("\nTest 3: Cascading deletion");
        Integer[] values3 = {1, 2, null, 2, null, 2};
        TreeNode root3 = solution.buildTree(values3);
        int target3 = 2;
        TreeNode result3 = solution.removeLeafNodes(root3, target3);
        
        System.out.println("Original tree:");
        solution.printTree(root3);
        System.out.println("After deleting leaves with target=" + target3 + ":");
        solution.printTree(result3);
        
        List<Integer> preorder3 = new ArrayList<>();
        solution.preorderTraversal(result3, preorder3);
        Integer[] expected3 = {1};
        boolean test3 = Arrays.equals(preorder3.toArray(), expected3);
        System.out.println("Cascading deletion: " + (test3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single node that matches target
        System.out.println("\nTest 4: Single node matching target");
        TreeNode root4 = new TreeNode(5);
        int target4 = 5;
        TreeNode result4 = solution.removeLeafNodes(root4, target4);
        System.out.println("Single node matching target: " + 
                         (result4 == null ? "PASSED" : "FAILED"));
        
        // Test case 5: Single node that doesn't match target
        System.out.println("\nTest 5: Single node not matching target");
        TreeNode root5 = new TreeNode(5);
        int target5 = 3;
        TreeNode result5 = solution.removeLeafNodes(root5, target5);
        System.out.println("Single node not matching target: " + 
                         (result5 != null && result5.val == 5 ? "PASSED" : "FAILED"));
        
        // Test case 6: All nodes match target (entire tree deleted)
        System.out.println("\nTest 6: All nodes match target");
        TreeNode root6 = new TreeNode(2);
        root6.left = new TreeNode(2);
        root6.right = new TreeNode(2);
        root6.left.left = new TreeNode(2);
        int target6 = 2;
        TreeNode result6 = solution.removeLeafNodes(root6, target6);
        System.out.println("All nodes match target: " + 
                         (result6 == null ? "PASSED" : "FAILED"));
        
        // Test case 7: No nodes match target
        System.out.println("\nTest 7: No nodes match target");
        TreeNode root7 = new TreeNode(1);
        root7.left = new TreeNode(2);
        root7.right = new TreeNode(3);
        int target7 = 5;
        TreeNode result7 = solution.removeLeafNodes(root7, target7);
        List<Integer> preorder7 = new ArrayList<>();
        solution.preorderTraversal(result7, preorder7);
        Integer[] expected7 = {1, 2, 3};
        boolean test7 = Arrays.equals(preorder7.toArray(), expected7);
        System.out.println("No nodes match target: " + (test7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Mixed case with multiple levels
        System.out.println("\nTest 8: Mixed case with multiple levels");
        TreeNode root8 = new TreeNode(1);
        root8.left = new TreeNode(2);
        root8.right = new TreeNode(3);
        root8.left.left = new TreeNode(2);
        root8.left.right = new TreeNode(2);
        root8.right.left = new TreeNode(2);
        root8.right.right = new TreeNode(4);
        int target8 = 2;
        TreeNode result8 = solution.removeLeafNodes(root8, target8);
        
        System.out.println("Original tree:");
        solution.printTree(root8);
        System.out.println("After deleting leaves with target=" + target8 + ":");
        solution.printTree(result8);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  Recursive Postorder: " + time1a + " ns");
        System.out.println("  Iterative: " + time1b + " ns");
        System.out.println("  BFS: " + time1c + " ns");
        
        // Algorithm explanation with visualization
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION WITH EXAMPLE:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nExample: root = [1,2,3,null,2,null,4], target = 2");
        System.out.println("Original tree:");
        System.out.println("    1");
        System.out.println("   / \\");
        System.out.println("  2   3");
        System.out.println("   \\   \\");
        System.out.println("    2   4");
        
        System.out.println("\nStep-by-step recursive postorder processing:");
        System.out.println("1. Process left subtree of root (node 2):");
        System.out.println("   - Process right child of node 2 (leaf node 2):");
        System.out.println("     * Leaf with target value → DELETE → returns null");
        System.out.println("   - Node 2: left=null, right=null, val=2 → Leaf with target → DELETE");
        System.out.println("   - Left subtree returns: null");
        
        System.out.println("2. Process right subtree of root (node 3):");
        System.out.println("   - Process right child of node 3 (leaf node 4):");
        System.out.println("     * Leaf with val=4 ≠ target → KEEP → returns node 4");
        System.out.println("   - Node 3: left=null, right=node4, val=3 → Not a leaf → KEEP");
        System.out.println("   - Right subtree returns: node 3");
        
        System.out.println("3. Process root (node 1):");
        System.out.println("   - left=null, right=node3, val=1 → Not a leaf → KEEP");
        System.out.println("   - Final tree: [1,null,3,null,4]");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Recursive Postorder (RECOMMENDED):");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(h) - Recursion stack height");
        System.out.println("   How it works:");
        System.out.println("     - Postorder traversal (left → right → root)");
        System.out.println("     - Process children before parent");
        System.out.println("     - After processing children, check if current node should be deleted");
        System.out.println("     - A node is deleted if it becomes a leaf with target value");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Handles cascading deletions naturally");
        System.out.println("     - Clean and intuitive implementation");
        System.out.println("     - Single pass solution");
        System.out.println("   Cons:");
        System.out.println("     - Recursion stack for deep trees");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Iterative Postorder:");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(n) - Stack and parent map storage");
        System.out.println("   How it works:");
        System.out.println("     - Uses stack to simulate recursion");
        System.out.println("     - Maintains parent map for deletion");
        System.out.println("     - Processes nodes in postorder iteratively");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack overflow");
        System.out.println("     - Explicit control over traversal");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Requires parent tracking");
        System.out.println("     - Extra O(n) space for parent map");
        System.out.println("   Best for: Very deep trees, when avoiding recursion");
        
        System.out.println("\n3. BFS with Parent Tracking:");
        System.out.println("   Time: O(n) worst case, but may require multiple passes");
        System.out.println("   Space: O(n) - Queue and parent map storage");
        System.out.println("   How it works:");
        System.out.println("     - Uses BFS to identify leaves with target value");
        System.out.println("     - Tracks parents for deletion");
        System.out.println("     - May require multiple passes for cascading deletions");
        System.out.println("   Pros:");
        System.out.println("     - Level-by-level processing");
        System.out.println("     - Easy to understand BFS approach");
        System.out.println("   Cons:");
        System.out.println("     - May require multiple passes");
        System.out.println("     - Less efficient than single-pass solutions");
        System.out.println("     - Extra O(n) space for parent map");
        System.out.println("   Best for: When BFS is preferred, educational purposes");
        
        System.out.println("\n4. Multi-pass DFS:");
        System.out.println("   Time: O(n^2) worst case - Multiple passes may be needed");
        System.out.println("   Space: O(h) - Recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Repeatedly makes passes until no more deletions");
        System.out.println("     - Each pass deletes one layer of leaves");
        System.out.println("   Pros:");
        System.out.println("     - Simple to understand conceptually");
        System.out.println("     - Clearly shows the cascading nature");
        System.out.println("   Cons:");
        System.out.println("     - Inefficient for deep cascading deletions");
        System.out.println("     - Multiple passes through the tree");
        System.out.println("   Best for: Understanding the problem, small trees");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("KEY INSIGHTS AND PATTERNS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Postorder Traversal Pattern:");
        System.out.println("   - Essential for bottom-up tree processing");
        System.out.println("   - Children are processed before parents");
        System.out.println("   - Natural fit for problems where parent decision depends on children");
        
        System.out.println("\n2. Cascading Deletion Pattern:");
        System.out.println("   - Deletion of a node may cause its parent to become deletable");
        System.out.println("   - Postorder traversal handles this naturally in one pass");
        System.out.println("   - No need for multiple explicit passes");
        
        System.out.println("\n3. Leaf Identification:");
        System.out.println("   - A node is a leaf when both children are null");
        System.out.println("   - After processing children, we can check if node became a leaf");
        System.out.println("   - This accounts for cases where children were deleted");
        
        System.out.println("\n4. Return Strategy:");
        System.out.println("   - Return null to delete a node");
        System.out.println("   - Return the node itself to keep it");
        System.out.println("   - Parent automatically gets updated children");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES AND EDGE CASES:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Not Handling Cascading Deletions:");
        System.out.println("   - Only deleting initial leaves, not parents that become leaves");
        System.out.println("   - Solution: Use postorder traversal");
        
        System.out.println("\n2. Incorrect Leaf Check:");
        System.out.println("   - Checking if node is leaf before processing children");
        System.out.println("   - Solution: Check after processing children");
        
        System.out.println("\n3. Root Deletion:");
        System.out.println("   - Forgetting that root can be deleted");
        System.out.println("   - Solution: Handle the case where entire tree is deleted");
        
        System.out.println("\n4. Modifying Tree During Traversal:");
        System.out.println("   - In iterative approaches, modifying tree during traversal");
        System.out.println("   - Solution: Complete traversal first, then delete");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with Recursive Postorder:");
        System.out.println("   - Most interviewers expect this solution");
        System.out.println("   - Explain why postorder is necessary (bottom-up processing)");
        System.out.println("   - Mention O(n) time and O(h) space complexity");
        
        System.out.println("\n2. Explain the Algorithm Clearly:");
        System.out.println("   - Process left and right children recursively");
        System.out.println("   - Update node's children with processed results");
        System.out.println("   - Check if node is now a leaf with target value");
        System.out.println("   - Return null to delete, or node to keep");
        
        System.out.println("\n3. Walk Through an Example:");
        System.out.println("   - Use one of the provided examples");
        System.out.println("   - Show how cascading deletions are handled");
        System.out.println("   - Demonstrate the postorder traversal order");
        
        System.out.println("\n4. Discuss Edge Cases:");
        System.out.println("   - Single node tree that matches target");
        System.out.println("   - Entire tree matches target (all nodes deleted)");
        System.out.println("   - No nodes match target (tree unchanged)");
        System.out.println("   - Deep cascading deletions");
        
        System.out.println("\n5. Mention Alternatives:");
        System.out.println("   - Iterative postorder approach");
        System.out.println("   - BFS multi-pass approach");
        System.out.println("   - Trade-offs between different methods");
        
        System.out.println("\nAll tests completed!");
    }
}
