
## Solution.java

```java
/**
 * 226. Invert Binary Tree
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given the root of a binary tree, invert the tree, and return its root.
 * 
 * Key Insights:
 * 1. Invert means swap left and right children for every node
 * 2. Multiple approaches: recursive DFS, iterative DFS, BFS
 * 3. All approaches have O(n) time complexity
 * 4. Space complexity depends on tree height
 * 
 * Approaches:
 * 1. Recursive DFS - Most intuitive and concise
 * 2. Iterative BFS - Level by level processing
 * 3. Iterative DFS - Stack-based approach
 * 4. Preorder/Postorder variations
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
     * 1. Base case: if node is null, return null
     * 2. Recursively invert left subtree
     * 3. Recursively invert right subtree  
     * 4. Swap left and right children
     * 5. Return current node
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        // Recursively invert subtrees
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        
        // Swap left and right children
        root.left = right;
        root.right = left;
        
        return root;
    }
    
    /**
     * Approach 2: Recursive DFS (Postorder variation)
     * Same complexity, different order of operations
     */
    public TreeNode invertTreePostorder(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        // Swap first, then recurse
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        
        invertTreePostorder(root.left);
        invertTreePostorder(root.right);
        
        return root;
    }
    
    /**
     * Approach 3: Iterative BFS (Level Order) - RECOMMENDED for interviews
     * Time: O(n), Space: O(w) where w is maximum level width
     * Algorithm:
     * 1. Use queue for level order traversal
     * 2. For each node, swap its left and right children
     * 3. Add children to queue for processing
     */
    public TreeNode invertTreeBFS(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            // Swap left and right children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            // Add children to queue
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        
        return root;
    }
    
    /**
     * Approach 4: Iterative DFS using Stack
     * Time: O(n), Space: O(h) where h is tree height
     * Algorithm:
     * 1. Use stack for DFS traversal
     * 2. For each node, swap left and right children
     * 3. Push children to stack
     */
    public TreeNode invertTreeDFS(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            
            // Swap left and right children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            // Push children to stack
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        
        return root;
    }
    
    /**
     * Approach 5: Iterative DFS (Preorder variation)
     * Similar to stack approach but different pushing order
     */
    public TreeNode invertTreeDFSPreorder(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            
            // Swap left and right children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            // Push right first, then left (for preorder)
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        
        return root;
    }
    
    /**
     * Approach 6: Morris Traversal - O(1) space
     * Time: O(n), Space: O(1)
     * Most complex approach, modifies tree temporarily
     */
    public TreeNode invertTreeMorris(TreeNode root) {
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                // No left child, just move to right
                current = current.right;
            } else {
                // Find inorder predecessor
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // Create thread and move left
                    predecessor.right = current;
                    
                    // Swap children before moving
                    TreeNode temp = current.left;
                    current.left = current.right;
                    current.right = temp;
                    
                    current = current.left;
                } else {
                    // Remove thread and move right
                    predecessor.right = null;
                    current = current.right;
                }
            }
        }
        
        return root;
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
     * Helper method to convert tree to level-order array
     */
    public List<Integer> treeToArray(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                result.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                result.add(null);
            }
        }
        
        // Remove trailing nulls
        int i = result.size() - 1;
        while (i >= 0 && result.get(i) == null) {
            result.remove(i);
            i--;
        }
        
        return result;
    }
    
    /**
     * Visualize the inversion process
     */
    public void visualizeInversion(TreeNode root, String approach) {
        System.out.println("\n" + approach + " Inversion Process:");
        System.out.println("Original Tree:");
        printTree(root, 0);
        
        TreeNode inverted;
        switch (approach) {
            case "Recursive":
                inverted = invertTree(root);
                break;
            case "BFS":
                inverted = invertTreeBFS(root);
                break;
            case "DFS":
                inverted = invertTreeDFS(root);
                break;
            default:
                inverted = invertTree(root);
        }
        
        System.out.println("Inverted Tree:");
        printTree(inverted, 0);
        
        // Restore original tree for next test
        invertTree(inverted);
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
     * Visualize BFS inversion step by step
     */
    public void visualizeBFSProcess(TreeNode root) {
        System.out.println("\nBFS Inversion Step-by-Step:");
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        TreeNode workingRoot = buildTree(treeToArray(root).toArray(new Integer[0])); // Copy
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(workingRoot);
        int step = 1;
        
        System.out.println("Initial Tree:");
        printTree(workingRoot, 0);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            System.out.println("\nStep " + step++ + ": Processing node " + node.val);
            
            // Swap children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            System.out.println("Swapped children of node " + node.val);
            printTree(workingRoot, 0);
            
            // Add children to queue
            if (node.left != null) {
                queue.offer(node.left);
                System.out.println("Added left child " + node.left.val + " to queue");
            }
            if (node.right != null) {
                queue.offer(node.right);
                System.out.println("Added right child " + node.right.val + " to queue");
            }
        }
    }
    
    /**
     * Verify inversion by checking specific properties
     */
    public boolean verifyInversion(TreeNode original, TreeNode inverted) {
        if (original == null && inverted == null) return true;
        if (original == null || inverted == null) return false;
        if (original.val != inverted.val) return false;
        
        // Check if children are swapped
        boolean leftMatch = verifyInversion(original.left, inverted.right);
        boolean rightMatch = verifyInversion(original.right, inverted.left);
        
        return leftMatch && rightMatch;
    }
    
    /**
     * Comprehensive test suite
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Binary Tree Inversion");
        System.out.println("=============================\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test 1: Complete binary tree [4,2,7,1,3,6,9]");
        Integer[] nodes1 = {4,2,7,1,3,6,9};
        TreeNode root1 = solution.buildTree(nodes1);
        
        TreeNode result1a = solution.invertTree(root1);
        TreeNode result1b = solution.invertTreeBFS(solution.buildTree(nodes1));
        TreeNode result1c = solution.invertTreeDFS(solution.buildTree(nodes1));
        
        List<Integer> expected1 = Arrays.asList(4,7,2,9,6,3,1);
        List<Integer> result1aArray = solution.treeToArray(result1a);
        List<Integer> result1bArray = solution.treeToArray(result1b);
        List<Integer> result1cArray = solution.treeToArray(result1c);
        
        System.out.println("Recursive: " + result1aArray + " - " + 
                         (result1aArray.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("BFS:       " + result1bArray + " - " + 
                         (result1bArray.equals(expected1) ? "PASSED" : "FAILED"));
        System.out.println("DFS:       " + result1cArray + " - " + 
                         (result1cArray.equals(expected1) ? "PASSED" : "FAILED"));
        
        // Verify inversion property
        TreeNode original1 = solution.buildTree(nodes1);
        TreeNode inverted1 = solution.invertTree(original1);
        boolean verified1 = solution.verifyInversion(solution.buildTree(nodes1), inverted1);
        System.out.println("Inversion verified: " + verified1);
        
        solution.visualizeInversion(solution.buildTree(nodes1), "Recursive");
        
        // Test Case 2: Empty tree
        System.out.println("\nTest 2: Empty tree");
        TreeNode root2 = null;
        TreeNode result2 = solution.invertTree(root2);
        System.out.println("Empty tree: " + (result2 == null ? "PASSED" : "FAILED"));
        
        // Test Case 3: Single node
        System.out.println("\nTest 3: Single node [1]");
        Integer[] nodes3 = {1};
        TreeNode root3 = solution.buildTree(nodes3);
        TreeNode result3 = solution.invertTree(root3);
        List<Integer> result3Array = solution.treeToArray(result3);
        System.out.println("Single node: " + result3Array + " - " + 
                         (result3Array.equals(Arrays.asList(1)) ? "PASSED" : "FAILED"));
        
        // Test Case 4: Left-skewed tree
        System.out.println("\nTest 4: Left-skewed tree [1,2,null,3]");
        Integer[] nodes4 = {1,2,null,3};
        TreeNode root4 = solution.buildTree(nodes4);
        TreeNode result4 = solution.invertTree(root4);
        List<Integer> result4Array = solution.treeToArray(result4);
        List<Integer> expected4 = Arrays.asList(1,null,2,null,null,null,3);
        // Clean expected array
        expected4.removeIf(val -> val == null);
        expected4.add(1); expected4.add(null); expected4.add(2); expected4.add(null); expected4.add(null); expected4.add(null); expected4.add(3);
        expected4.removeIf(val -> val == null);
        System.out.println("Left-skewed: " + result4Array);
        
        // Test Case 5: Right-skewed tree
        System.out.println("\nTest 5: Right-skewed tree [1,null,2,null,3]");
        Integer[] nodes5 = {1,null,2,null,3};
        TreeNode root5 = solution.buildTree(nodes5);
        TreeNode result5 = solution.invertTree(root5);
        List<Integer> result5Array = solution.treeToArray(result5);
        System.out.println("Right-skewed: " + result5Array);
        
        // Test Case 6: Complex tree
        System.out.println("\nTest 6: Complex tree [1,2,3,4,5,6,7,8,9,10]");
        Integer[] nodes6 = {1,2,3,4,5,6,7,8,9,10};
        TreeNode root6 = solution.buildTree(nodes6);
        
        TreeNode result6a = solution.invertTree(root6);
        TreeNode result6b = solution.invertTreeBFS(solution.buildTree(nodes6));
        
        boolean sameResult = solution.treeToArray(result6a).equals(solution.treeToArray(result6b));
        System.out.println("Complex tree - All approaches same: " + sameResult);
        
        solution.visualizeBFSProcess(solution.buildTree(nodes6));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BINARY TREE INVERSION ALGORITHM EXPLANATION");
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
        solution.invertTree(largeTree);
        solution.invertTreeBFS(largeTree);
        solution.invertTreeDFS(largeTree);
        
        // Test recursive
        long startTime = System.nanoTime();
        TreeNode result1 = solution.invertTree(largeTree);
        long time1 = System.nanoTime() - startTime;
        
        // Test BFS
        startTime = System.nanoTime();
        TreeNode result2 = solution.invertTreeBFS(largeTree);
        long time2 = System.nanoTime() - startTime;
        
        // Test DFS
        startTime = System.nanoTime();
        TreeNode result3 = solution.invertTreeDFS(largeTree);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("Performance Results (1000 nodes):");
        System.out.println("Recursive: " + time1 + " ns");
        System.out.println("BFS:       " + time2 + " ns");
        System.out.println("DFS:       " + time3 + " ns");
        
        // Verify all produce same result
        List<Integer> result1Array = solution.treeToArray(result1);
        List<Integer> result2Array = solution.treeToArray(result2);
        List<Integer> result3Array = solution.treeToArray(result3);
        boolean allEqual = result1Array.equals(result2Array) && result1Array.equals(result3Array);
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
        System.out.println("\n1. RECURSIVE DFS APPROACH (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     invertTree(node):");
        System.out.println("       if node is null: return null");
        System.out.println("       left = invertTree(node.left)");
        System.out.println("       right = invertTree(node.right)");
        System.out.println("       node.left = right, node.right = left");
        System.out.println("       return node");
        System.out.println("   Pros: Simple, elegant, easy to understand");
        System.out.println("   Cons: Recursion stack for deep trees");
        
        System.out.println("\n2. ITERATIVE BFS APPROACH (RECOMMENDED):");
        System.out.println("   Algorithm:");
        System.out.println("     queue = [root]");
        System.out.println("     while queue not empty:");
        System.out.println("       node = queue.dequeue()");
        System.out.println("       swap node.left and node.right");
        System.out.println("       enqueue node.left and node.right if not null");
        System.out.println("   Pros: Easy to visualize, level-by-level processing");
        System.out.println("   Cons: Uses queue, O(w) space where w is level width");
        
        System.out.println("\n3. ITERATIVE DFS APPROACH:");
        System.out.println("   Algorithm:");
        System.out.println("     stack = [root]");
        System.out.println("     while stack not empty:");
        System.out.println("       node = stack.pop()");
        System.out.println("       swap node.left and node.right");
        System.out.println("       push node.left and node.right if not null");
        System.out.println("   Pros: Similar to recursive but avoids recursion limits");
        System.out.println("   Cons: Uses stack, O(h) space");
        
        System.out.println("\n4. KEY INSIGHTS:");
        System.out.println("   - All approaches visit each node exactly once: O(n) time");
        System.out.println("   - Space complexity depends on tree structure");
        System.out.println("   - The operation is symmetric: inverting twice gives original");
        System.out.println("   - Works for any binary tree structure");
        
        System.out.println("\n5. REAL-WORLD APPLICATIONS:");
        System.out.println("   - Image processing (flipping images)");
        System.out.println("   - Game development (mirroring game worlds)");
        System.out.println("   - UI development (flipping layouts)");
        System.out.println("   - Data structure transformations");
        System.out.println("   - Algorithm testing and verification");
    }
    
    /**
     * Interview strategy and tips
     */
    private static void explainInterviewStrategy() {
        System.out.println("\nINTERVIEW STRATEGY FOR BINARY TREE INVERSION:");
        
        System.out.println("\n1. Which Approach to Use:");
        System.out.println("   - Start with recursive: most intuitive and concise");
        System.out.println("   - Then BFS iterative: demonstrates level-order thinking");
        System.out.println("   - Mention DFS iterative: shows stack-based approach");
        System.out.println("   - Recommended order: Recursive → BFS → DFS");
        
        System.out.println("\n2. Key Points to Discuss:");
        System.out.println("   - Time complexity: O(n) for all approaches");
        System.out.println("   - Space complexity: O(h) for recursive/DFS, O(w) for BFS");
        System.out.println("   - Why the problem is fundamental to tree operations");
        System.out.println("   - Real-world applications of tree inversion");
        
        System.out.println("\n3. Common Mistakes:");
        System.out.println("   - Forgetting to handle null cases");
        System.out.println("   - Modifying the original tree unintentionally");
        System.out.println("   - Incorrect swapping logic");
        System.out.println("   - Not returning the root node");
        
        System.out.println("\n4. Follow-up Questions to Expect:");
        System.out.println("   - What if we want to invert only at certain levels?");
        System.out.println("   - How to verify if two trees are mirrors?");
        System.out.println("   - Can we invert in-place without extra space?");
        System.out.println("   - What about N-ary trees?");
        System.out.println("   - How to serialize the inverted tree?");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - Mirroring binary structures");
        System.out.println("   - Game development transformations");
        System.out.println("   - UI layout mirroring");
        System.out.println("   - Data structure optimization");
        System.out.println("   - Algorithm visualization");
        
        System.out.println("\n6. Practice Problems:");
        System.out.println("   - 100. Same Tree");
        System.out.println("   - 101. Symmetric Tree");
        System.out.println("   - 104. Maximum Depth of Binary Tree");
        System.out.println("   - 110. Balanced Binary Tree");
        System.out.println("   - 111. Minimum Depth of Binary Tree");
        System.out.println("   - 617. Merge Two Binary Trees");
        
        System.out.println("\n7. Code Patterns to Remember:");
        System.out.println("   Recursive:");
        System.out.println("     if (root == null) return null;");
        System.out.println("     TreeNode left = invertTree(root.left);");
        System.out.println("     TreeNode right = invertTree(root.right);");
        System.out.println("     root.left = right; root.right = left;");
        System.out.println("     return root;");
        System.out.println("   BFS:");
        System.out.println("     Queue<TreeNode> queue = new LinkedList<>();");
        System.out.println("     queue.offer(root);");
        System.out.println("     while (!queue.isEmpty()) {");
        System.out.println("       TreeNode node = queue.poll();");
        System.out.println("       TreeNode temp = node.left;");
        System.out.println("       node.left = node.right; node.right = temp;");
        System.out.println("       if (node.left != null) queue.offer(node.left);");
        System.out.println("       if (node.right != null) queue.offer(node.right);");
        System.out.println("     }");
    }
}
