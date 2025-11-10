
## Solution.java

```java
/**
 * 1448. Count Good Nodes in Binary Tree
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a binary tree root, a node X in the tree is named good if in the path 
 * from root to X there are no nodes with a value greater than X.
 * Return the number of good nodes in the binary tree.
 * 
 * Key Insights:
 * 1. Root is always a good node
 * 2. Need to track maximum value along each path
 * 3. DFS is natural for tracking path-specific information
 * 4. Update maximum when visiting child nodes
 * 
 * Approach (DFS with Maximum Tracking):
 * 1. Start DFS from root with initial maximum = Integer.MIN_VALUE
 * 2. At each node, compare node value with current maximum
 * 3. If node value >= current maximum, count it as good and update maximum
 * 4. Recursively process left and right children with updated maximum
 * 5. Return total count
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(h) where h is tree height
 * 
 * Tags: Tree, DFS, Binary Tree
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
     * Approach 1: DFS Recursive - RECOMMENDED
     * O(n) time, O(h) space
     */
    public int goodNodes(TreeNode root) {
        return dfs(root, Integer.MIN_VALUE);
    }
    
    private int dfs(TreeNode node, int currentMax) {
        if (node == null) {
            return 0;
        }
        
        int count = 0;
        
        // Check if current node is good
        if (node.val >= currentMax) {
            count++;
            currentMax = node.val;
        }
        
        // Recursively count good nodes in left and right subtrees
        count += dfs(node.left, currentMax);
        count += dfs(node.right, currentMax);
        
        return count;
    }
    
    /**
     * Approach 2: BFS Iterative with Custom Class
     * O(n) time, O(n) space
     */
    public int goodNodesBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int count = 0;
        Queue<NodeInfo> queue = new LinkedList<>();
        queue.offer(new NodeInfo(root, Integer.MIN_VALUE));
        
        while (!queue.isEmpty()) {
            NodeInfo current = queue.poll();
            TreeNode node = current.node;
            int currentMax = current.maxSoFar;
            
            // Check if current node is good
            if (node.val >= currentMax) {
                count++;
                currentMax = node.val;
            }
            
            // Add children to queue with updated maximum
            if (node.left != null) {
                queue.offer(new NodeInfo(node.left, currentMax));
            }
            if (node.right != null) {
                queue.offer(new NodeInfo(node.right, currentMax));
            }
        }
        
        return count;
    }
    
    private class NodeInfo {
        TreeNode node;
        int maxSoFar;
        
        NodeInfo(TreeNode node, int maxSoFar) {
            this.node = node;
            this.maxSoFar = maxSoFar;
        }
    }
    
    /**
     * Approach 3: DFS Iterative with Stack
     * O(n) time, O(h) space
     */
    public int goodNodesDFSStack(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int count = 0;
        Stack<NodeInfo> stack = new Stack<>();
        stack.push(new NodeInfo(root, Integer.MIN_VALUE));
        
        while (!stack.isEmpty()) {
            NodeInfo current = stack.pop();
            TreeNode node = current.node;
            int currentMax = current.maxSoFar;
            
            // Check if current node is good
            if (node.val >= currentMax) {
                count++;
                currentMax = node.val;
            }
            
            // Push right child first (so left is processed first in LIFO)
            if (node.right != null) {
                stack.push(new NodeInfo(node.right, currentMax));
            }
            if (node.left != null) {
                stack.push(new NodeInfo(node.left, currentMax));
            }
        }
        
        return count;
    }
    
    /**
     * Approach 4: Morris Traversal (Threaded Binary Tree)
     * O(n) time, O(1) space - but modifies tree temporarily
     */
    public int goodNodesMorris(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int count = 0;
        TreeNode current = root;
        int currentMax = Integer.MIN_VALUE;
        
        while (current != null) {
            if (current.left == null) {
                // Process current node
                if (current.val >= currentMax) {
                    count++;
                    currentMax = current.val;
                }
                current = current.right;
            } else {
                // Find inorder predecessor
                TreeNode predecessor = current.left;
                int steps = 1;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                    steps++;
                }
                
                if (predecessor.right == null) {
                    // Create temporary link
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Remove temporary link and process current node
                    predecessor.right = null;
                    
                    // Process current node
                    if (current.val >= currentMax) {
                        count++;
                        currentMax = current.val;
                    }
                    current = current.right;
                }
            }
        }
        
        return count;
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
        
        System.out.println("Testing Count Good Nodes in Binary Tree:");
        System.out.println("========================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Example from problem");
        Integer[] values1 = {3, 1, 4, 3, null, 1, 5};
        TreeNode root1 = solution.buildTree(values1);
        
        long startTime = System.nanoTime();
        int result1a = solution.goodNodes(root1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.goodNodesBFS(root1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.goodNodesDFSStack(root1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.goodNodesMorris(root1);
        long time1d = System.nanoTime() - startTime;
        
        int expected1 = 4;
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        
        System.out.println("DFS Recursive: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("BFS: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("DFS Stack: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Morris: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the tree and good nodes
        System.out.println("\nTree visualization for Test 1:");
        solution.printTree(root1);
        System.out.println("Good nodes explanation:");
        System.out.println("  Root (3) is always good");
        System.out.println("  Node 4 (path 3->4): 4 >= max(3) = GOOD");
        System.out.println("  Node 5 (path 3->4->5): 5 >= max(3,4) = GOOD");
        System.out.println("  Node 3 (path 3->1->3): 3 >= max(3,1) = GOOD");
        System.out.println("  Node 1 (path 3->1): 1 < max(3) = NOT GOOD");
        System.out.println("  Node 1 (path 3->4->1): 1 < max(3,4) = NOT GOOD");
        
        // Test case 2: Single node
        System.out.println("\nTest 2: Single node");
        TreeNode root2 = new TreeNode(1);
        int result2 = solution.goodNodes(root2);
        System.out.println("Single node: " + result2 + " - " + 
                         (result2 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 3: All nodes are good (increasing values)
        System.out.println("\nTest 3: All nodes are good");
        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(2);
        root3.right = new TreeNode(3);
        root3.left.left = new TreeNode(4);
        int result3 = solution.goodNodes(root3);
        System.out.println("All good nodes: " + result3 + " - " + 
                         (result3 == 4 ? "PASSED" : "FAILED"));
        
        // Test case 4: Only root is good (decreasing values)
        System.out.println("\nTest 4: Only root is good");
        TreeNode root4 = new TreeNode(5);
        root4.left = new TreeNode(4);
        root4.right = new TreeNode(3);
        root4.left.left = new TreeNode(2);
        int result4 = solution.goodNodes(root4);
        System.out.println("Only root good: " + result4 + " - " + 
                         (result4 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 5: Mixed case
        System.out.println("\nTest 5: Mixed case");
        TreeNode root5 = new TreeNode(3);
        root5.left = new TreeNode(1);
        root5.right = new TreeNode(4);
        root5.left.left = new TreeNode(3);
        root5.right.left = new TreeNode(1);
        root5.right.right = new TreeNode(5);
        int result5 = solution.goodNodes(root5);
        System.out.println("Mixed case: " + result5 + " - " + 
                         (result5 == 4 ? "PASSED" : "FAILED"));
        
        // Test case 6: Negative values
        System.out.println("\nTest 6: Negative values");
        TreeNode root6 = new TreeNode(-1);
        root6.left = new TreeNode(-2);
        root6.right = new TreeNode(-3);
        root6.left.left = new TreeNode(0); // This becomes good when path max is -2
        int result6 = solution.goodNodes(root6);
        System.out.println("Negative values: " + result6 + " - " + 
                         (result6 == 2 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  DFS Recursive: " + time1a + " ns");
        System.out.println("  BFS: " + time1b + " ns");
        System.out.println("  DFS Stack: " + time1c + " ns");
        System.out.println("  Morris: " + time1d + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DFS RECURSIVE ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("A node is 'good' if its value is >= maximum value in its path from root.");
        System.out.println("We track the current maximum while traversing the tree.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Start from root with initial maximum = Integer.MIN_VALUE");
        System.out.println("2. At each node:");
        System.out.println("   - If node.val >= currentMax: count++ and update currentMax");
        System.out.println("   - Recursively process left and right children with updated currentMax");
        System.out.println("3. Return total count");
        
        System.out.println("\nWhy it works:");
        System.out.println("- Each path is processed independently");
        System.out.println("- Maximum value is maintained along each path");
        System.out.println("- DFS naturally tracks path-specific state");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. DFS Recursive (RECOMMENDED):");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(h) - Recursion stack height");
        System.out.println("   How it works:");
        System.out.println("     - Recursive DFS with maximum tracking");
        System.out.println("     - Natural for tree problems");
        System.out.println("   Pros:");
        System.out.println("     - Clean and intuitive");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Recursion stack space");
        System.out.println("     - Stack overflow for very deep trees");
        System.out.println("   Best for: Interview settings, balanced trees");
        
        System.out.println("\n2. BFS Iterative:");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(n) - Queue storage");
        System.out.println("   How it works:");
        System.out.println("     - Level-order traversal with custom class");
        System.out.println("     - Tracks node and current maximum");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack concerns");
        System.out.println("     - Level-by-level processing");
        System.out.println("   Cons:");
        System.out.println("     - More memory for queue");
        System.out.println("     - Custom class overhead");
        System.out.println("   Best for: Very deep trees, when avoiding recursion");
        
        System.out.println("\n3. DFS Iterative with Stack:");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(h) - Stack height");
        System.out.println("   How it works:");
        System.out.println("     - Explicit stack instead of recursion");
        System.out.println("     - Same logic as recursive DFS");
        System.out.println("   Pros:");
        System.out.println("     - Avoids recursion stack limits");
        System.out.println("     - More control over traversal");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Still uses O(h) space");
        System.out.println("   Best for: Deep trees, when explicit stack preferred");
        
        System.out.println("\n4. Morris Traversal:");
        System.out.println("   Time: O(n) - Each node visited 2-3 times");
        System.out.println("   Space: O(1) - No extra space (modifies tree)");
        System.out.println("   How it works:");
        System.out.println("     - Uses threaded binary tree concept");
        System.out.println("     - Creates temporary links for traversal");
        System.out.println("   Pros:");
        System.out.println("     - Constant space complexity");
        System.out.println("     - No recursion or stack");
        System.out.println("   Cons:");
        System.out.println("     - Modifies tree temporarily");
        System.out.println("     - More complex implementation");
        System.out.println("     - Harder to understand");
        System.out.println("   Best for: Memory-constrained environments");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Root is always a good node (no nodes before it)");
        System.out.println("2. If all values are distinct and increasing, all nodes are good");
        System.out.println("3. If all values are distinct and decreasing, only root is good");
        System.out.println("4. The problem is about path maxima, not tree structure");
        System.out.println("5. Each path from root to leaf is considered independently");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with DFS Recursive - it's the most intuitive");
        System.out.println("2. Explain why we track maximum along the path");
        System.out.println("3. Mention that root is always good");
        System.out.println("4. Discuss time and space complexity clearly");
        System.out.println("5. Consider edge cases: single node, negative values");
        System.out.println("6. Be prepared to discuss alternative approaches");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
