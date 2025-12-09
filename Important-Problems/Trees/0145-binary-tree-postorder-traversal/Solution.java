
## Solution.java

```java
/**
 * 145. Binary Tree Postorder Traversal
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given the root of a binary tree, return the postorder traversal of its nodes' values.
 * 
 * Key Insights:
 * 1. Postorder traversal: left -> right -> root
 * 2. Most challenging traversal to implement iteratively
 * 3. Multiple approaches: recursive, iterative (two stacks), iterative (one stack), Morris
 * 4. Two-stack approach: reverse preorder (root->right->left) gives postorder when reversed
 * 5. One-stack approach: track last visited node to determine when to process current node
 * 
 * Approaches:
 * 1. Recursive - Simple, follows definition directly
 * 2. Iterative with Two Stacks - Most intuitive iterative approach
 * 3. Iterative with One Stack - More space efficient
 * 4. Morris Traversal - O(1) space, most complex
 * 
 * Time Complexity: O(n) for all approaches
 * Space Complexity: 
 *   - Recursive: O(h) where h is tree height
 *   - Two Stacks: O(n) 
 *   - One Stack: O(h) where h is tree height
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
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderRecursive(root, result);
        return result;
    }
    
    private void postorderRecursive(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // Traverse left subtree
        postorderRecursive(node.left, result);
        
        // Traverse right subtree
        postorderRecursive(node.right, result);
        
        // Visit current node last (root)
        result.add(node.val);
    }
    
    /**
     * Approach 2: Iterative with Two Stacks - RECOMMENDED for interviews
     * Time: O(n), Space: O(n)
     * Algorithm:
     * 1. Push root to stack1
     * 2. While stack1 is not empty:
     *    a. Pop node from stack1 and push to stack2
     *    b. Push left then right children to stack1
     * 3. Stack2 now contains nodes in reverse postorder
     * 4. Pop from stack2 to get postorder
     * 
     * Why it works: We're doing reverse preorder (root->right->left) 
     * and then reversing to get postorder (left->right->root)
     */
    public List<Integer> postorderTraversalTwoStacks(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            
            // Push left first, then right (so right is processed first in stack1)
            if (node.left != null) {
                stack1.push(node.left);
            }
            if (node.right != null) {
                stack1.push(node.right);
            }
        }
        
        // Stack2 now has nodes in reverse postorder
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().val);
        }
        
        return result;
    }
    
    /**
     * Approach 3: Iterative with One Stack - Space efficient
     * Time: O(n), Space: O(h) where h is tree height
     * Algorithm:
     * 1. Use current pointer and stack
     * 2. Track last visited node to know when we're coming back from right subtree
     * 3. If coming from left and right exists, process right first
     * 4. If coming from right or no right, process current node
     */
    public List<Integer> postorderTraversalOneStack(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        
        while (current != null || !stack.isEmpty()) {
            // Go to leftmost node
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                TreeNode peekNode = stack.peek();
                
                // If right child exists and hasn't been visited, process right
                if (peekNode.right != null && lastVisited != peekNode.right) {
                    current = peekNode.right;
                } else {
                    // Process the node
                    result.add(peekNode.val);
                    lastVisited = stack.pop();
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: Morris Postorder Traversal - O(1) space
     * Time: O(n), Space: O(1)
     * Most complex approach - modifies tree temporarily
     * Algorithm uses reverse processing of certain paths
     */
    public List<Integer> postorderTraversalMorris(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        TreeNode dummy = new TreeNode(0);
        dummy.left = root;
        TreeNode current = dummy;
        
        while (current != null) {
            if (current.left == null) {
                current = current.right;
            } else {
                // Find inorder predecessor
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // Create thread
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Thread exists - reverse process nodes
                    reverseAdd(current.left, predecessor, result);
                    predecessor.right = null;
                    current = current.right;
                }
            }
        }
        
        return result;
    }
    
    private void reverseAdd(TreeNode from, TreeNode to, List<Integer> result) {
        reverse(from, to);
        
        // Add nodes in reverse order
        TreeNode node = to;
        while (true) {
            result.add(node.val);
            if (node == from) break;
            node = node.right;
        }
        
        reverse(to, from);
    }
    
    private void reverse(TreeNode from, TreeNode to) {
        if (from == to) return;
        
        TreeNode prev = from;
        TreeNode current = from.right;
        
        while (prev != to) {
            TreeNode next = current.right;
            current.right = prev;
            prev = current;
            current = next;
        }
    }
    
    /**
     * Approach 5: Universal Iterative Traversal - Easy to adapt for all orders
     * Time: O(n), Space: O(n)
     * Uses stack of objects to track nodes and processing state
     */
    public List<Integer> postorderTraversalUniversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<Object> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            Object obj = stack.pop();
            
            if (obj instanceof TreeNode) {
                TreeNode node = (TreeNode) obj;
                // Push in processing order: node value, then right, then left
                stack.push(node.val);
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
            } else {
                // It's a value to be added to result
                result.add((Integer) obj);
            }
        }
        
        return result;
    }
    
    /**
     * Approach 6: Iterative with Deque - Alternative two-stack approach
     * Time: O(n), Space: O(n)
     */
    public List<Integer> postorderTraversalDeque(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Deque<TreeNode> stack = new LinkedList<>();
        Deque<TreeNode> output = new LinkedList<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            output.push(node);
            
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        
        while (!output.isEmpty()) {
            result.add(output.pop().val);
        }
        
        return result;
    }
    
    /**
     * Approach 7: Modified Preorder then Reverse
     * Time: O(n), Space: O(h)
     * Do modified preorder (root->right->left) and reverse result
     */
    public List<Integer> postorderTraversalReversePreorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            
            // Push left first, then right (for reverse preorder)
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        
        // Reverse the result to get postorder
        Collections.reverse(result);
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
        System.out.println("\n" + approach + " Postorder Traversal Visualization:");
        System.out.println("Tree Structure:");
        printTree(root, 0);
        
        List<Integer> result;
        switch (approach) {
            case "Recursive":
                result = postorderTraversal(root);
                break;
            case "TwoStacks":
                result = postorderTraversalTwoStacks(root);
                break;
            case "OneStack":
                result = postorderTraversalOneStack(root);
                break;
            case "Morris":
                result = postorderTraversalMorris(root);
                break;
            default:
                result = postorderTraversal(root);
        }
        
        System.out.println("Postorder Result: " + result);
        
        if (approach.equals("TwoStacks")) {
            System.out.println("Step-by-step two-stack process:");
            visualizeTwoStacksProcess(root);
        } else if (approach.equals("OneStack")) {
            System.out.println("Step-by-step one-stack process:");
            visualizeOneStackProcess(root);
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
    
    private void visualizeTwoStacksProcess(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        int step = 1;
        
        System.out.println("Step | Action           | Stack1 Contents   | Stack2 Contents   | Result");
        System.out.println("-----|------------------|-------------------|-------------------|--------");
        
        System.out.printf("%4d | Push root %-7d | %-17s | %-17s | %s%n", 
            step++, root.val, stackToString(stack1), "[]", result);
        
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            
            System.out.printf("%4d | Move %-11d | %-17s | %-17s | %s%n", 
                step++, node.val, stackToString(stack1), stackToString(stack2), result);
            
            // Push children
            if (node.left != null) {
                stack1.push(node.left);
                System.out.printf("%4d | Push left %-6d | %-17s | %-17s | %s%n", 
                    step++, node.left.val, stackToString(stack1), stackToString(stack2), result);
            }
            
            if (node.right != null) {
                stack1.push(node.right);
                System.out.printf("%4d | Push right %-5d | %-17s | %-17s | %s%n", 
                    step++, node.right.val, stackToString(stack1), stackToString(stack2), result);
            }
        }
        
        // Process stack2
        while (!stack2.isEmpty()) {
            TreeNode node = stack2.pop();
            result.add(node.val);
            System.out.printf("%4d | Pop & add %-6d | %-17s | %-17s | %s%n", 
                step++, node.val, "[]", stackToString(stack2), result);
        }
    }
    
    private void visualizeOneStackProcess(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        int step = 1;
        
        System.out.println("Step | Action           | Stack Contents    | Current | LastVisited | Result");
        System.out.println("-----|------------------|-------------------|---------|-------------|--------");
        
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                System.out.printf("%4d | Push %-11d | %-17s | %7d | %11s | %s%n", 
                    step++, current.val, stackToString(stack), current.val, 
                    lastVisited == null ? "null" : lastVisited.val, result);
                current = current.left;
            } else {
                TreeNode peekNode = stack.peek();
                
                if (peekNode.right != null && lastVisited != peekNode.right) {
                    current = peekNode.right;
                    System.out.printf("%4d | Go right to %-4d | %-17s | %7d | %11s | %s%n", 
                        step++, current.val, stackToString(stack), current.val,
                        lastVisited == null ? "null" : lastVisited.val, result);
                } else {
                    result.add(peekNode.val);
                    lastVisited = stack.pop();
                    System.out.printf("%4d | Process %-8d | %-17s | %7s | %11d | %s%n", 
                        step++, peekNode.val, stackToString(stack), "null",
                        lastVisited.val, result);
                }
            }
        }
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
     * Compare different traversal orders for the same tree
     */
    public void compareTraversalOrders(TreeNode root) {
        System.out.println("\nComparison of Tree Traversal Orders:");
        System.out.println("Tree Structure:");
        printTree(root, 0);
        
        List<Integer> preorder = preorderTraversal(root);
        List<Integer> inorder = inorderTraversal(root);
        List<Integer> postorder = postorderTraversal(root);
        
        System.out.println("Preorder (Root->Left->Right):  " + preorder);
        System.out.println("Inorder (Left->Root->Right):   " + inorder);
        System.out.println("Postorder (Left->Right->Root): " + postorder);
        
        // Demonstrate tree deletion pattern (postorder is ideal)
        System.out.println("\nApplication: Tree Deletion");
        System.out.println("Postorder is perfect for deletion: children before parent!");
    }
    
    // Helper method for preorder traversal (for comparison)
    private List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderRecursive(root, result);
        return result;
    }
    
    private void preorderRecursive(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderRecursive(node.left, result);
        preorderRecursive(node.right, result);
    }
    
    // Helper method for inorder traversal (for comparison)
    private List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }
    
    private void inorderRecursive(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderRecursive(node.left, result);
        result.add(node.val);
        inorderRecursive(node.right, result);
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Binary Tree Postorder Traversal");
        System.out.println("=======================================\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test 1: Example [1,null,2,3]");
        Integer[] nodes1 = {1, null, 2, 3};
        TreeNode root1 = solution.buildTree(nodes1);
        
        List<Integer> result1a = solution.postorderTraversal(root1);
        List<Integer> result1b = solution.postorderTraversalTwoStacks(root1);
        List<Integer> result1c = solution.postorderTraversalOneStack(root1);
        List<Integer> result1d = solution.postorderTraversalMorris(root1);
        List<Integer> expected1 = Arrays.asList(3, 2, 1);
        
        System.out.println("Recursive:   " + result1a + " - " + 
                         (result1a.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Two Stacks:  " + result1b + " - " + 
                         (result1b.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("One Stack:   " + result1c + " - " + 
                         (result1c.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("Morris:      " + result1d + " - " + 
                         (result1d.equals(expected1) ? "PASSED" : "FAILED"));
        
        solution.visualizeTraversal(root1, "TwoStacks");
        
        // Test Case 2: Empty tree
        System.out.println("\nTest 2: Empty tree");
        TreeNode root2 = null;
        List<Integer> result2a = solution.postorderTraversal(root2);
        List<Integer> expected2 = Arrays.asList();
        System.out.println("Empty tree: " + result2a + " - " + 
                         (result2a.equals(expected2) ? "PASSED" : "FAILED"));
        
        // Test Case 3: Single node
        System.out.println("\nTest 3: Single node [1]");
        Integer[] nodes3 = {1};
        TreeNode root3 = solution.buildTree(nodes3);
        List<Integer> result3a = solution.postorderTraversal(root3);
        List<Integer> expected3 = Arrays.asList(1);
        System.out.println("Single node: " + result3a + " - " + 
                         (result3a.equals(expected3) ? "PASSED" : "FAILED"));
        
        // Test Case 4: Complete binary tree
        System.out.println("\nTest 4: Complete binary tree [1,2,3,4,5,6,7]");
        Integer[] nodes4 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root4 = solution.buildTree(nodes4);
        List<Integer> result4a = solution.postorderTraversal(root4);
        List<Integer> expected4 = Arrays.asList(4, 5, 2, 6, 7, 3, 1);
        System.out.println("Complete tree: " + result4a + " - " + 
                         (result4a.equals(expected4) ? "PASSED" : "FAILED"));
        
        solution.visualizeTraversal(root4, "OneStack");
        
        // Test Case 5: Left-skewed tree
        System.out.println("\nTest 5: Left-skewed tree [1,2,null,3,null,4]");
        Integer[] nodes5 = {1, 2, null, 3, null, 4};
        TreeNode root5 = solution.buildTree(nodes5);
        List<Integer> result5a = solution.postorderTraversal(root5);
        List<Integer> expected5 = Arrays.asList(4, 3, 2, 1);
        System.out.println("Left-skewed: " + result5a + " - " + 
                         (result5a.equals(expected5) ? "PASSED" : "FAILED"));
        
        // Test Case 6: Right-skewed tree
        System.out.println("\nTest 6: Right-skewed tree [1,null,2,null,3,null,4]");
        Integer[] nodes6 = {1, null, 2, null, 3, null, 4};
        TreeNode root6 = solution.buildTree(nodes6);
        List<Integer> result6a = solution.postorderTraversal(root6);
        List<Integer> expected6 = Arrays.asList(4, 3, 2, 1);
        System.out.println("Right-skewed: " + result6a + " - " + 
                         (result6a.equals(expected6) ? "PASSED" : "FAILED"));
        
        // Test Case 7: Complex tree
        System.out.println("\nTest 7: Complex tree [5,3,7,2,4,6,8,1,null,null,null,null,null,null,9]");
        Integer[] nodes7 = {5, 3, 7, 2, 4, 6, 8, 1, null, null, null, null, null, null, 9};
        TreeNode root7 = solution.buildTree(nodes7);
        List<Integer> result7a = solution.postorderTraversal(root7);
        List<Integer> expected7 = Arrays.asList(1, 2, 4, 3, 6, 9, 8, 7, 5);
        System.out.println("Complex tree: " + result7a + " - " + 
                         (result7a.equals(expected7) ? "PASSED" : "FAILED"));
        
        // Compare traversal orders
        solution.compareTraversalOrders(root7);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("POSTORDER TRAVERSAL ALGORITHM EXPLANATION");
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
        solution.postorderTraversal(largeTree);
        solution.postorderTraversalTwoStacks(largeTree);
        solution.postorderTraversalOneStack(largeTree);
        solution.postorderTraversalMorris(largeTree);
        
        // Test recursive
        long startTime = System.nanoTime();
        List<Integer> result1 = solution.postorderTraversal(largeTree);
        long time1 = System.nanoTime() - startTime;
        
        // Test two stacks
        startTime = System.nanoTime();
        List<Integer> result2 = solution.postorderTraversalTwoStacks(largeTree);
        long time2 = System.nanoTime() - startTime;
        
        // Test one stack
        startTime = System.nanoTime();
        List<Integer> result3 = solution.postorderTraversalOneStack(largeTree);
        long time3 = System.nanoTime() - startTime;
        
        // Test Morris
        startTime = System.nanoTime();
        List<Integer> result4 = solution.postorderTraversalMorris(largeTree);
        long time4 = System.nanoTime() - startTime;
        
        System.out.println("Performance Results (1000 nodes):");
        System.out.println("Recursive:   " + time1 + " ns");
        System.out.println("Two Stacks:  " + time2 + " ns");
        System.out.println("One Stack:   " + time3 + " ns");
        System.out.println("Morris:      " + time4 + " ns");
        
        // Verify all produce same result
        boolean allEqual = result1.equals(result2) && result1.equals(result3) && result1.equals(result4);
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
        System.out.println("     postorder(node):");
        System.out.println("       if node is null: return");
        System.out.println("       postorder(node.left)");
        System.out.println("       postorder(node.right)");
        System.out.println("       visit(node)");
        System.out.println("   Pros: Simple, intuitive, follows definition directly");
        System.out.println("   Cons: Stack overflow for very deep trees, O(h) space");
        
        System.out.println("\n2. TWO-STACK APPROACH (RECOMMENDED for interviews):");
        System.out.println("   Algorithm:");
        System.out.println("     stack1.push(root)");
        System.out.println("     while stack1 not empty:");
        System.out.println("       node = stack1.pop()");
        System.out.println("       stack2.push(node)");
        System.out.println("       push node.left to stack1");
        System.out.println("       push node.right to stack1");
        System.out.println("     while stack2 not empty:");
        System.out.println("       visit stack2.pop()");
        System.out.println("   Why it works: Reverse preorder (root->right->left) gives postorder when reversed");
        System.out.println("   Pros: Easy to understand and implement");
        System.out.println("   Cons: Uses two stacks, O(n) space");
        
        System.out.println("\n3. ONE-STACK APPROACH:");
        System.out.println("   Algorithm:");
        System.out.println("     current = root, lastVisited = null");
        System.out.println("     while current != null OR stack not empty:");
        System.out.println("       if current != null:");
        System.out.println("         push current, current = current.left");
        System.out.println("       else:");
        System.out.println("         peekNode = stack.peek()");
        System.out.println("         if peekNode.right exists and not visited:");
        System.out.println("           current = peekNode.right");
        System.out.println("         else:");
        System.out.println("           visit peekNode, lastVisited = stack.pop()");
        System.out.println("   Pros: More space efficient than two stacks");
        System.out.println("   Cons: More complex logic");
        
        System.out.println("\n4. MORRIS TRAVERSAL (O(1) space):");
        System.out.println("   Algorithm:");
        System.out.println("     Uses threading and reverse processing");
        System.out.println("     Most complex but demonstrates deep understanding");
        System.out.println("   Pros: O(1) space, no stack");
        System.out.println("   Cons: Modifies tree temporarily, very complex implementation");
        
        System.out.println("\n5. TRAVERSAL ORDERS SUMMARY:");
        System.out.println("   Preorder:  root -> left -> right");
        System.out.println("   Inorder:   left -> root -> right");
        System.out.println("   Postorder: left -> right -> root");
        
        System.out.println("\n6. REAL-WORLD APPLICATIONS OF POSTORDER:");
        System.out.println("   - Tree deletion (children before parent)");
        System.out.println("   - Expression tree evaluation (postfix notation)");
        System.out.println("   - Memory management (free children before parent)");
        System.out.println("   - File system cleanup (subdirectories before parent)");
        System.out.println("   - Garbage collection in programming languages");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR POSTORDER TRAVERSAL:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with recursive: simplest to implement");
        System.out.println("   - Then two-stack iterative: most intuitive iterative approach");
        System.out.println("   - Mention one-stack: shows deeper understanding");
        System.out.println("   - Morris: only if specifically asked for O(1) space");
        System.out.println("   - Recommended order: Recursive → Two Stacks → One Stack → Morris");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Why postorder is most challenging iteratively");
        System.out.println("   - Two-stack approach: reverse preorder trick");
        System.out.println("   - One-stack approach: lastVisited tracking");
        System.out.println("   - Real-world applications (deletion, expression evaluation)");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Forgetting to push left before right in two-stack approach");
        System.out.println("   - Incorrect lastVisited logic in one-stack approach");
        System.out.println("   - Infinite loops in Morris traversal");
        System.out.println("   - Not handling null cases properly");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - Implement preorder/inorder traversal");
        System.out.println("   - Construct tree from inorder + postorder");
        System.out.println("   - Delete a binary tree (perfect for postorder)");
        System.out.println("   - Evaluate expression tree");
        System.out.println("   - Implement N-ary tree postorder traversal");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - Memory deallocation in C++ destructors");
        System.out.println("   - File system cleanup operations");
        System.out.println("   - Expression evaluation in calculators");
        System.out.println("   - Garbage collection algorithms");
        System.out.println("   - Database transaction rollbacks");
        
        System.out.println("\n6. Practice Problems:");
        System.out.println("   - 94. Binary Tree Inorder Traversal");
        System.out.println("   - 144. Binary Tree Preorder Traversal");
        System.out.println("   - 106. Construct Binary Tree from Inorder and Postorder");
        System.out.println("   - 590. N-ary Tree Postorder Traversal");
        System.out.println("   - 226. Invert Binary Tree (can use postorder)");
        System.out.println("   - 104. Maximum Depth of Binary Tree");
        
        System.out.println("\n7. Code Patterns to Remember:");
        System.out.println("   Two Stacks:");
        System.out.println("     stack1.push(root);");
        System.out.println("     while (!stack1.isEmpty()) {");
        System.out.println("       node = stack1.pop();");
        System.out.println("       stack2.push(node);");
        System.out.println("       if (node.left != null) stack1.push(node.left);");
        System.out.println("       if (node.right != null) stack1.push(node.right);");
        System.out.println("     }");
        System.out.println("     while (!stack2.isEmpty()) result.add(stack2.pop().val);");
    }
}
