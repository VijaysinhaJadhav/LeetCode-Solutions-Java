
## Solution.java

```java
/**
 * 230. Kth Smallest Element in a BST
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given the root of a binary search tree, and an integer k, 
 * return the kth smallest value (1-indexed) of all the values in the tree.
 * 
 * Key Insights:
 * 1. Inorder traversal of BST gives sorted sequence
 * 2. Can stop early when kth element is found
 * 3. Iterative DFS uses O(h) space and can be efficient for small k
 * 4. For frequent queries, augment tree with subtree sizes
 * 
 * Approach (Iterative Inorder):
 * 1. Use stack to perform iterative inorder traversal
 * 2. Go left until reaching null, then process node
 * 3. Decrement k when processing each node
 * 4. Return when k becomes 0
 * 
 * Time Complexity: O(h + k)
 * Space Complexity: O(h)
 * 
 * Tags: Tree, BST, DFS, Inorder Traversal
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
     * Approach 1: Iterative Inorder Traversal - RECOMMENDED
     * O(h + k) time, O(h) space
     */
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // Go to the leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Process current node
            current = stack.pop();
            k--;
            
            // If we've found the kth smallest element
            if (k == 0) {
                return current.val;
            }
            
            // Move to right subtree
            current = current.right;
        }
        
        return -1; // Should not reach here if k is valid
    }
    
    /**
     * Approach 2: Recursive Inorder Traversal
     * O(n) time, O(h) space
     */
    private int count = 0;
    private int result = 0;
    
    public int kthSmallestRecursive(TreeNode root, int k) {
        count = k;
        inorderRecursive(root);
        return result;
    }
    
    private void inorderRecursive(TreeNode node) {
        if (node == null) {
            return;
        }
        
        inorderRecursive(node.left);
        
        count--;
        if (count == 0) {
            result = node.val;
            return;
        }
        
        inorderRecursive(node.right);
    }
    
    /**
     * Approach 3: Binary Search with Node Counting
     * O(n) time worst case, O(h) space
     */
    public int kthSmallestBinarySearch(TreeNode root, int k) {
        int leftCount = countNodes(root.left);
        
        if (k <= leftCount) {
            // kth smallest is in left subtree
            return kthSmallestBinarySearch(root.left, k);
        } else if (k == leftCount + 1) {
            // Current node is the kth smallest
            return root.val;
        } else {
            // kth smallest is in right subtree
            return kthSmallestBinarySearch(root.right, k - leftCount - 1);
        }
    }
    
    private int countNodes(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    
    /**
     * Approach 4: Morris Traversal (Constant Space)
     * O(n) time, O(1) space - but modifies tree temporarily
     */
    public int kthSmallestMorris(TreeNode root, int k) {
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                // Process current node
                k--;
                if (k == 0) {
                    return current.val;
                }
                current = current.right;
            } else {
                // Find inorder predecessor
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // Create temporary link
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Remove temporary link and process current node
                    predecessor.right = null;
                    k--;
                    if (k == 0) {
                        return current.val;
                    }
                    current = current.right;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 5: Augmented Tree (For Follow-up)
     * Build time: O(n), Query time: O(h), Space: O(n)
     */
    class AugmentedTreeNode {
        int val;
        AugmentedTreeNode left;
        AugmentedTreeNode right;
        int leftCount; // Number of nodes in left subtree
        
        AugmentedTreeNode(int val) {
            this.val = val;
            this.leftCount = 0;
        }
    }
    
    private AugmentedTreeNode buildAugmentedTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        AugmentedTreeNode augRoot = new AugmentedTreeNode(root.val);
        augRoot.left = buildAugmentedTree(root.left);
        augRoot.right = buildAugmentedTree(root.right);
        augRoot.leftCount = countNodes(root.left);
        
        return augRoot;
    }
    
    public int kthSmallestAugmented(TreeNode root, int k) {
        AugmentedTreeNode augRoot = buildAugmentedTree(root);
        return findKthAugmented(augRoot, k);
    }
    
    private int findKthAugmented(AugmentedTreeNode node, int k) {
        if (node == null) {
            return -1;
        }
        
        if (k <= node.leftCount) {
            // kth smallest is in left subtree
            return findKthAugmented(node.left, k);
        } else if (k == node.leftCount + 1) {
            // Current node is the kth smallest
            return node.val;
        } else {
            // kth smallest is in right subtree
            return findKthAugmented(node.right, k - node.leftCount - 1);
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
        
        System.out.println("Testing Kth Smallest Element in BST:");
        System.out.println("=====================================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        Integer[] values1 = {3, 1, 4, null, 2};
        TreeNode root1 = solution.buildTree(values1);
        int k1 = 1;
        
        long startTime = System.nanoTime();
        int result1a = solution.kthSmallest(root1, k1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.kthSmallestRecursive(root1, k1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.kthSmallestBinarySearch(root1, k1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.kthSmallestMorris(root1, k1);
        long time1d = System.nanoTime() - startTime;
        
        int expected1 = 1;
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        
        System.out.println("Iterative Inorder: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Recursive Inorder: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Binary Search: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Morris: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the tree and traversal
        System.out.println("\nTree visualization for Test 1:");
        solution.printTree(root1);
        System.out.println("Inorder traversal: 1, 2, 3, 4");
        System.out.println("1st smallest element: 1");
        
        // Test case 2: Example 2
        System.out.println("\nTest 2: Complex tree");
        Integer[] values2 = {5, 3, 6, 2, 4, null, null, 1};
        TreeNode root2 = solution.buildTree(values2);
        int k2 = 3;
        int result2 = solution.kthSmallest(root2, k2);
        int expected2 = 3;
        System.out.println("Complex tree k=3: " + result2 + " - " + 
                         (result2 == expected2 ? "PASSED" : "FAILED"));
        
        System.out.println("\nTree visualization for Test 2:");
        solution.printTree(root2);
        System.out.println("Inorder traversal: 1, 2, 3, 4, 5, 6");
        System.out.println("3rd smallest element: 3");
        
        // Test case 3: Single node
        System.out.println("\nTest 3: Single node");
        TreeNode root3 = new TreeNode(5);
        int result3 = solution.kthSmallest(root3, 1);
        System.out.println("Single node k=1: " + result3 + " - " + 
                         (result3 == 5 ? "PASSED" : "FAILED"));
        
        // Test case 4: k = n (largest element)
        System.out.println("\nTest 4: Largest element");
        Integer[] values4 = {4, 2, 6, 1, 3, 5, 7};
        TreeNode root4 = solution.buildTree(values4);
        int nodeCount = 7;
        int result4 = solution.kthSmallest(root4, nodeCount);
        System.out.println("Largest element (k=" + nodeCount + "): " + result4 + " - " + 
                         (result4 == 7 ? "PASSED" : "FAILED"));
        
        // Test case 5: Complete BST
        System.out.println("\nTest 5: Complete BST");
        TreeNode root5 = new TreeNode(4);
        root5.left = new TreeNode(2);
        root5.right = new TreeNode(6);
        root5.left.left = new TreeNode(1);
        root5.left.right = new TreeNode(3);
        root5.right.left = new TreeNode(5);
        root5.right.right = new TreeNode(7);
        
        // Test all k values
        boolean allPassed5 = true;
        int[] expectedValues = {1, 2, 3, 4, 5, 6, 7};
        for (int k = 1; k <= 7; k++) {
            int result = solution.kthSmallest(root5, k);
            if (result != expectedValues[k-1]) {
                allPassed5 = false;
                System.out.println("Failed for k=" + k + ", expected " + expectedValues[k-1] + ", got " + result);
            }
        }
        System.out.println("Complete BST all k values: " + (allPassed5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Left-skewed tree
        System.out.println("\nTest 6: Left-skewed tree");
        TreeNode root6 = new TreeNode(5);
        root6.left = new TreeNode(4);
        root6.left.left = new TreeNode(3);
        root6.left.left.left = new TreeNode(2);
        root6.left.left.left.left = new TreeNode(1);
        int result6 = solution.kthSmallest(root6, 2);
        System.out.println("Left-skewed k=2: " + result6 + " - " + 
                         (result6 == 2 ? "PASSED" : "FAILED"));
        
        // Test case 7: Right-skewed tree
        System.out.println("\nTest 7: Right-skewed tree");
        TreeNode root7 = new TreeNode(1);
        root7.right = new TreeNode(2);
        root7.right.right = new TreeNode(3);
        root7.right.right.right = new TreeNode(4);
        root7.right.right.right.right = new TreeNode(5);
        int result7 = solution.kthSmallest(root7, 4);
        System.out.println("Right-skewed k=4: " + result7 + " - " + 
                         (result7 == 4 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  Iterative Inorder: " + time1a + " ns");
        System.out.println("  Recursive Inorder: " + time1b + " ns");
        System.out.println("  Binary Search: " + time1c + " ns");
        System.out.println("  Morris: " + time1d + " ns");
        
        // Test augmented tree for follow-up scenario
        System.out.println("\nTest 8: Augmented Tree (Follow-up scenario)");
        int result8 = solution.kthSmallestAugmented(root2, k2);
        System.out.println("Augmented tree k=3: " + result8 + " - " + 
                         (result8 == expected2 ? "PASSED" : "FAILED"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ITERATIVE INORDER ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Inorder traversal of BST visits nodes in ascending order.");
        System.out.println("We can stop the traversal when we reach the kth element.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Use stack to simulate recursion");
        System.out.println("2. Push all left nodes onto stack (go to leftmost)");
        System.out.println("3. Pop from stack (process current smallest)");
        System.out.println("4. Decrement k, return if k == 0");
        System.out.println("5. Move to right subtree");
        System.out.println("6. Repeat until kth element found");
        
        System.out.println("\nWhy O(h + k) time?");
        System.out.println("- O(h) to reach leftmost node");
        System.out.println("- O(k) to process k elements");
        System.out.println("- Efficient when k is small compared to n");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Iterative Inorder (RECOMMENDED):");
        System.out.println("   Time: O(h + k) - Optimal for small k");
        System.out.println("   Space: O(h) - Stack height");
        System.out.println("   How it works:");
        System.out.println("     - Simulates inorder traversal with stack");
        System.out.println("     - Stops early when kth element found");
        System.out.println("   Pros:");
        System.out.println("     - Best average-case performance");
        System.out.println("     - Intuitive and easy to implement");
        System.out.println("     - No recursion stack overflow");
        System.out.println("   Cons:");
        System.out.println("     - Still uses O(h) space");
        System.out.println("   Best for: General case, interview settings");
        
        System.out.println("\n2. Recursive Inorder:");
        System.out.println("   Time: O(n) worst case");
        System.out.println("   Space: O(h) - Recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Classic recursive inorder traversal");
        System.out.println("     - Uses global variables to track count");
        System.out.println("   Pros:");
        System.out.println("     - Simple and clean implementation");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Cannot stop early in some implementations");
        System.out.println("     - Recursion stack overflow risk");
        System.out.println("   Best for: Small trees, educational purposes");
        
        System.out.println("\n3. Binary Search with Node Counting:");
        System.out.println("   Time: O(n) worst case, O(h) average");
        System.out.println("   Space: O(h) - Recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Counts nodes in left subtree");
        System.out.println("     - Uses BST property to narrow search");
        System.out.println("   Pros:");
        System.out.println("     - No full traversal needed");
        System.out.println("     - Leverages BST structure");
        System.out.println("   Cons:");
        System.out.println("     - O(n) worst case for counting");
        System.out.println("     - Inefficient for balanced trees");
        System.out.println("   Best for: When tree is augmented with counts");
        
        System.out.println("\n4. Morris Traversal:");
        System.out.println("   Time: O(n)");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Uses threaded binary tree concept");
        System.out.println("     - Creates temporary links for traversal");
        System.out.println("   Pros:");
        System.out.println("     - Constant space complexity");
        System.out.println("     - No stack or recursion");
        System.out.println("   Cons:");
        System.out.println("     - Modifies tree temporarily");
        System.out.println("     - More complex implementation");
        System.out.println("     - Harder to understand");
        System.out.println("   Best for: Memory-constrained environments");
        
        System.out.println("\n5. Augmented Tree (For Follow-up):");
        System.out.println("   Build time: O(n)");
        System.out.println("   Query time: O(h)");
        System.out.println("   Space: O(n) - Extra storage for counts");
        System.out.println("   How it works:");
        System.out.println("     - Stores left subtree size in each node");
        System.out.println("     - Uses binary search on augmented tree");
        System.out.println("   Pros:");
        System.out.println("     - O(h) query time after preprocessing");
        System.out.println("     - Optimal for frequent queries");
        System.out.println("   Cons:");
        System.out.println("     - O(n) preprocessing time and space");
        System.out.println("     - Complex to maintain with insertions/deletions");
        System.out.println("   Best for: Frequent kth smallest queries with modifications");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("FOLLOW-UP SOLUTION ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nScenario: BST is modified often (insert/delete) and");
        System.out.println("we need to find kth smallest frequently.");
        
        System.out.println("\nSolution: Augment BST with subtree sizes");
        System.out.println("1. Each node stores: val, left, right, leftCount");
        System.out.println("2. leftCount = number of nodes in left subtree");
        System.out.println("3. Maintain counts during insert/delete operations");
        System.out.println("4. Query kth smallest in O(h) time");
        
        System.out.println("\nInsertion/Deletion Maintenance:");
        System.out.println("- Insert: Update leftCount for all ancestors when going left");
        System.out.println("- Delete: Similar updates, handle rebalancing if needed");
        System.out.println("- Complexity: O(h) for updates, same as regular BST operations");
        
        System.out.println("\nTrade-offs:");
        System.out.println("+ O(h) query time instead of O(h + k)");
        System.out.println("+ Optimal for frequent queries");
        System.out.println("- Extra O(n) space for counts");
        System.out.println("- More complex implementation");
        System.out.println("- Maintenance overhead for modifications");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with iterative inorder - it's the expected solution");
        System.out.println("2. Explain O(h + k) time complexity clearly");
        System.out.println("3. Mention BST property and inorder traversal");
        System.out.println("4. Discuss alternative approaches briefly");
        System.out.println("5. For follow-up, discuss augmented tree approach");
        System.out.println("6. Consider edge cases: k=1, k=n, single node tree");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
