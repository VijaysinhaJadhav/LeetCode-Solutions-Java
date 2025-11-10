
## Solution.java

```java
/**
 * 94. Binary Tree Inorder Traversal
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given the root of a binary tree, return the inorder traversal of its nodes' values.
 * 
 * Key Insights:
 * 1. Inorder traversal: left -> root -> right
 * 2. Multiple approaches: recursive, iterative, Morris traversal
 * 3. Recursive is simplest but may cause stack overflow for deep trees
 * 4. Iterative uses stack to simulate recursion
 * 5. Morris traversal provides O(1) space complexity
 * 
 * Approaches:
 * 1. Recursive - Simple, follows definition directly
 * 2. Iterative with Stack - Practical, avoids recursion limits
 * 3. Morris Traversal - O(1) space, modifies tree temporarily
 * 
 * Time Complexity: O(n) for all approaches
 * Space Complexity: 
 *   - Recursive: O(h) where h is tree height
 *   - Iterative: O(h) where h is tree height  
 *   - Morris: O(1)
 * 
 * Tags: Stack, Tree, Depth-First Search, Binary Tree
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
     * Approach 1: Recursive Solution - RECOMMENDED for simplicity
     * Time: O(n), Space: O(h) where h is tree height
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }
    
    private void inorderRecursive(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // Traverse left subtree
        inorderRecursive(node.left, result);
        
        // Visit current node
        result.add(node.val);
        
        // Traverse right subtree
        inorderRecursive(node.right, result);
    }
    
    /**
     * Approach 2: Iterative Solution using Stack - RECOMMENDED for interviews
     * Time: O(n), Space: O(h) where h is tree height
     */
    public List<Integer> inorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // Go to the leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Process the node
            current = stack.pop();
            result.add(current.val);
            
            // Move to right subtree
            current = current.right;
        }
        
        return result;
    }
    
    /**
     * Approach 3: Morris Inorder Traversal - O(1) space
     * Time: O(n), Space: O(1)
     * Algorithm:
     * 1. Initialize current as root
     * 2. While current is not null:
     *    a. If current has no left child:
     *       - Add current to result
     *       - Go to right child
     *    b. Else:
     *       - Find inorder predecessor (rightmost node in left subtree)
     *       - If predecessor's right is null:
     *           * Set predecessor's right to current (create thread)
     *           * Move to left child
     *       - Else (thread already exists):
     *           * Restore tree by setting predecessor's right to null
     *           * Add current to result
     *           * Move to right child
     */
    public List<Integer> inorderTraversalMorris(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                // No left child, visit current and go right
                result.add(current.val);
                current = current.right;
            } else {
                // Find inorder predecessor
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // Create thread to current
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Thread already exists, restore tree and visit current
                    predecessor.right = null;
                    result.add(current.val);
                    current = current.right;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: Iterative with Visited Set - Alternative iterative approach
     * Time: O(n), Space: O(n)
     */
    public List<Integer> inorderTraversalIterativeVisited(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        Set<TreeNode> visited = new HashSet<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            
            // Go left until we can't
            if (node.left != null && !visited.contains(node.left)) {
                stack.push(node.left);
                continue;
            }
            
            // Process current node if not visited
            if (!visited.contains(node)) {
                result.add(node.val);
                visited.add(node);
            }
            
            // Go right if possible
            if (node.right != null && !visited.contains(node.right)) {
                stack.push(node.right);
            } else {
                stack.pop();
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Universal Iterative Traversal - Can be adapted for pre/post order
     * Time: O(n), Space: O(n)
     */
    public List<Integer> inorderTraversalUniversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<Object> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            Object obj = stack.pop();
            
            if (obj instanceof TreeNode) {
                TreeNode node = (TreeNode) obj;
                // Push in reverse order of desired processing
                if (node.right != null) stack.push(node.right);
                stack.push(node.val); // Mark node as to be processed
                if (node.left != null) stack.push(node.left);
            } else {
                // It's a value to be added to result
                result.add((Integer) obj);
            }
        }
        
        return result;
    }
    
    /**
     * Helper method to build a binary tree from array (Level-order)
     * Useful for testing
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
     * Visualize traversal process for educational purposes
     */
    public void visualizeTraversal(TreeNode root, String approach) {
        System.out.println("\n" + approach + " Traversal Visualization:");
        System.out.println("Tree Structure:");
        printTree(root, 0);
        
        List<Integer> result;
        switch (approach) {
            case "Recursive":
                result = inorderTraversal(root);
                break;
            case "Iterative":
                result = inorderTraversalIterative(root);
                break;
            case "Morris":
                result = inorderTraversalMorris(root);
                break;
            default:
                result = inorderTraversal(root);
        }
        
        System.out.println("Inorder Result: " + result);
        System.out.println("Traversal Order: " + getTraversalOrder(root));
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
    
    private String getTraversalOrder(TreeNode root) {
        if (root == null) return "Empty tree";
        
        StringBuilder sb = new StringBuilder();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        int step = 1;
        
        System.out.println("\nStep-by-step process:");
        System.out.println("Step | Action           | Stack Contents      | Current | Result");
        System.out.println("-----|------------------|---------------------|---------|--------");
        
        List<Integer> result = new ArrayList<>();
        
        while (current != null || !stack.isEmpty()) {
            // Go to leftmost node
            while (current != null) {
                stack.push(current);
                System.out.printf("%4d | Push %-11d | %-19s | %7s | %s%n", 
                    step++, current.val, stackToString(stack), "null", result);
                current = current.left;
            }
            
            // Process node
            current = stack.pop();
            result.add(current.val);
            System.out.printf("%4d | Process %-8d | %-19s | %7d | %s%n", 
                step++, current.val, stackToString(stack), current.val, result);
            
            // Move to right
            current = current.right;
            if (current != null) {
                System.out.printf("%4d | Go right to %-4d | %-19s | %7d | %s%n", 
                    step++, current.val, stackToString(stack), current.val, result);
            }
        }
        
        return "Complete";
    }
    
    private String stackToString(Stack<TreeNode> stack) {
        if (stack.isEmpty()) return "[]";
        List<String> elements = new ArrayList<>();
        for (TreeNode node : stack) {
            elements.add(String.valueOf(node.val));
        }
        return elements.toString();
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Binary Tree Inorder Traversal");
        System.out.println("=====================================\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test 1: Example [1,null,2,3]");
        Integer[] nodes1 = {1, null, 2, 3};
        TreeNode root1 = solution.buildTree(nodes1);
        
        List<Integer> result1a = solution.inorderTraversal(root1);
        List<Integer> result1b = solution.inorderTraversalIterative(root1);
        List<Integer> result1c = solution.inorderTraversalMorris(root1);
        List<Integer> expected1 = Arrays.asList(1, 3, 2);
        
        System.out.println("Recursive:  " + result1a + " - " + 
                         (result1a.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Iterative:  " + result1b + " - " + 
                         (result1b.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Morris:     " + result1c + " - " + 
                         (result1c.equals(expected1) ? "PASSED" : "FAILED"));
        
        solution.visualizeTraversal(root1, "Iterative");
        
        // Test Case 2: Empty tree
        System.out.println("\nTest 2: Empty tree");
        TreeNode root2 = null;
        List<Integer> result2a = solution.inorderTraversal(root2);
        List<Integer> expected2 = Arrays.asList();
        System.out.println("Empty tree: " + result2a + " - " + 
                         (result2a.equals(expected2) ? "PASSED" : "FAILED"));
        
        // Test Case 3: Single node
        System.out.println("\nTest 3: Single node [1]");
        Integer[] nodes3 = {1};
        TreeNode root3 = solution.buildTree(nodes3);
        List<Integer> result3a = solution.inorderTraversal(root3);
        List<Integer> expected3 = Arrays.asList(1);
        System.out.println("Single node: " + result3a + " - " + 
                         (result3a.equals(expected3) ? "PASSED" : "FAILED"));
        
        // Test Case 4: Complete binary tree
        System.out.println("\nTest 4: Complete binary tree [1,2,3,4,5,6,7]");
        Integer[] nodes4 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root4 = solution.buildTree(nodes4);
        List<Integer> result4a = solution.inorderTraversal(root4);
        List<Integer> expected4 = Arrays.asList(4, 2, 5, 1, 6, 3, 7);
        System.out.println("Complete tree: " + result4a + " - " + 
                         (result4a.equals(expected4) ? "PASSED" : "FAILED"));
        
        solution.visualizeTraversal(root4, "Iterative");
        
        // Test Case 5: Left-skewed tree
        System.out.println("\nTest 5: Left-skewed tree [1,2,null,3,null,4]");
        Integer[] nodes5 = {1, 2, null, 3, null, 4};
        TreeNode root5 = solution.buildTree(nodes5);
        List<Integer> result5a = solution.inorderTraversal(root5);
        List<Integer> expected5 = Arrays.asList(4, 3, 2, 1);
        System.out.println("Left-skewed: " + result5a + " - " + 
                         (result5a.equals(expected5) ? "PASSED" : "FAILED"));
        
        // Test Case 6: Right-skewed tree
        System.out.println("\nTest 6: Right-skewed tree [1,null,2,null,3,null,4]");
        Integer[] nodes6 = {1, null, 2, null, 3, null, 4};
        TreeNode root6 = solution.buildTree(nodes6);
        List<Integer> result6a = solution.inorderTraversal(root6);
        List<Integer> expected6 = Arrays.asList(1, 2, 3, 4);
        System.out.println("Right-skewed: " + result6a + " - " + 
                         (result6a.equals(expected6) ? "PASSED" : "FAILED"));
        
        // Test Case 7: Complex tree
        System.out.println("\nTest 7: Complex tree [5,3,7,2,4,6,8,1,null,null,null,null,null,null,9]");
        Integer[] nodes7 = {5, 3, 7, 2, 4, 6, 8, 1, null, null, null, null, null, null, 9};
        TreeNode root7 = solution.buildTree(nodes7);
        List<Integer> result7a = solution.inorderTraversal(root7);
        List<Integer> expected7 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("Complex tree: " + result7a + " - " + 
                         (result7a.equals(expected7) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INORDER TRAVERSAL ALGORITHM EXPLANATION");
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
        
        // Create a balanced tree with 1000 nodes
        TreeNode largeTree = createBalancedTree(1000);
        
        // Warm up
        solution.inorderTraversal(largeTree);
        solution.inorderTraversalIterative(largeTree);
        solution.inorderTraversalMorris(largeTree);
        
        // Test recursive
        long startTime = System.nanoTime();
        List<Integer> result1 = solution.inorderTraversal(largeTree);
        long time1 = System.nanoTime() - startTime;
        
        // Test iterative
        startTime = System.nanoTime();
        List<Integer> result2 = solution.inorderTraversalIterative(largeTree);
        long time2 = System.nanoTime() - startTime;
        
        // Test Morris
        startTime = System.nanoTime();
        List<Integer> result3 = solution.inorderTraversalMorris(largeTree);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Performance Results (1000 nodes):");
        System.out.println("Recursive:  " + time1 + " ns");
        System.out.println("Iterative:  " + time2 + " ns");
        System.out.println("Morris:     " + time3 + " ns");
        
        // Verify all produce same result
        boolean allEqual = result1.equals(result2) && result1.equals(result3);
        System.out.println("All approaches consistent: " + allEqual);
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
     * Detailed algorithm explanations
     */
    private static void explainAlgorithms() {
        System.out.println("\n1. RECURSIVE APPROACH:");
        System.out.println("   Algorithm:");
        System.out.println("     inorder(node):");
        System.out.println("       if node is null: return");
        System.out.println("       inorder(node.left)");
        System.out.println("       visit(node)");
        System.out.println("       inorder(node.right)");
        System.out.println("   Pros: Simple, intuitive, follows definition directly");
        System.out.println("   Cons: Stack overflow for very deep trees, O(h) space");
        
        System.out.println("\n2. ITERATIVE APPROACH (with Stack):");
        System.out.println("   Algorithm:");
        System.out.println("     current = root");
        System.out.println("     while current != null OR stack not empty:");
        System.out.println("       while current != null:");
        System.out.println("         push current to stack");
        System.out.println("         current = current.left");
        System.out.println("       current = pop from stack");
        System.out.println("       visit current");
        System.out.println("       current = current.right");
        System.out.println("   Pros: No recursion stack overflow, O(h) space");
        System.out.println("   Cons: More complex than recursive");
        
        System.out.println("\n3. MORRIS TRAVERSAL (O(1) space):");
        System.out.println("   Algorithm:");
        System.out.println("     current = root");
        System.out.println("     while current != null:");
        System.out.println("       if no left child:");
        System.out.println("         visit current, go right");
        System.out.println("       else:");
        System.out.println("         find inorder predecessor");
        System.out.println("         if no thread:");
        System.println("           create thread, go left");
        System.out.println("         else:");
        System.out.println("           remove thread, visit current, go right");
        System.out.println("   Pros: O(1) space, no stack");
        System.out.println("   Cons: Modifies tree temporarily, more complex");
        
        System.out.println("\n4. TRAVERSAL ORDERS SUMMARY:");
        System.out.println("   Preorder:  root -> left -> right");
        System.out.println("   Inorder:   left -> root -> right");
        System.out.println("   Postorder: left -> right -> root");
        System.out.println("   Level-order: level by level (BFS)");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR TREE TRAVERSAL:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with recursive: simplest to implement");
        System.out.println("   - Then iterative: demonstrates stack understanding");
        System.out.println("   - Mention Morris: shows advanced knowledge");
        System.out.println("   - Recommended order: Recursive → Iterative → Morris");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Time complexity: O(n) for all approaches");
        System.out.println("   - Space complexity differences");
        System.out.println("   - When to use each approach");
        System.out.println("   - Real-world applications");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Forgetting base case in recursion");
        System.out.println("   - Incorrect stack operations in iterative");
        System.out.println("   - Infinite loops in Morris traversal");
        System.out.println("   - Not handling null cases");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - Implement preorder/postorder traversal");
        System.out.println("   - Implement level-order traversal (BFS)");
        System.out.println("   - Find kth smallest element in BST");
        System.out.println("   - Validate BST using inorder traversal");
        System.out.println("   - Serialize/deserialize binary tree");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - BST operations (inorder gives sorted order)");
        System.out.println("   - Expression tree evaluation");
        System.out.println("   - File system traversal");
        System.out.println("   - Database indexing");
        System.out.println("   - Compiler syntax tree processing");
        
        System.out.println("\n6. Practice Problems:");
        System.out.println("   - 144. Binary Tree Preorder Traversal");
        System.out.println("   - 145. Binary Tree Postorder Traversal");
        System.out.println("   - 102. Binary Tree Level Order Traversal");
        System.out.println("   - 173. Binary Search Tree Iterator");
        System.out.println("   - 230. Kth Smallest Element in a BST");
        System.out.println("   - 98. Validate Binary Search Tree");
    }
}
