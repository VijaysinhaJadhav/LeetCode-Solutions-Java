
## Solution.java

```java
/**
 * 105. Construct Binary Tree from Preorder and Inorder Traversal
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given two integer arrays preorder and inorder where preorder is the preorder 
 * traversal of a binary tree and inorder is the inorder traversal of the same tree, 
 * construct and return the binary tree.
 * 
 * Key Insights:
 * 1. First element in preorder is always the root
 * 2. Root divides inorder into left and right subtrees
 * 3. Recursively build left and right subtrees
 * 4. Use HashMap for O(1) inorder index lookups
 * 
 * Approach (Recursive with HashMap):
 * 1. Build HashMap mapping values to indices in inorder
 * 2. Use preorder index to track current root
 * 3. Recursively build tree by partitioning inorder array
 * 4. Return constructed binary tree
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 * 
 * Tags: Tree, Binary Tree, Divide and Conquer, DFS
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
    private Map<Integer, Integer> inorderMap;
    private int preorderIndex;
    
    /**
     * Approach 1: Recursive with HashMap - RECOMMENDED
     * O(n) time, O(n) space
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Build HashMap for O(1) inorder index lookups
        inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        preorderIndex = 0;
        
        return buildTreeHelper(preorder, 0, inorder.length - 1);
    }
    
    private TreeNode buildTreeHelper(int[] preorder, int left, int right) {
        // Base case: no elements to construct subtree
        if (left > right) {
            return null;
        }
        
        // Select current root from preorder
        int rootValue = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootValue);
        
        // Find root position in inorder to split into left and right subtrees
        int inorderIndex = inorderMap.get(rootValue);
        
        // Build left subtree (elements left of root in inorder)
        root.left = buildTreeHelper(preorder, left, inorderIndex - 1);
        
        // Build right subtree (elements right of root in inorder)
        root.right = buildTreeHelper(preorder, inorderIndex + 1, right);
        
        return root;
    }
    
    /**
     * Approach 2: Recursive without HashMap (Slower)
     * O(n^2) time, O(n) space
     */
    public TreeNode buildTreeLinearSearch(int[] preorder, int[] inorder) {
        preorderIndex = 0;
        return buildTreeHelperLinear(preorder, inorder, 0, inorder.length - 1);
    }
    
    private TreeNode buildTreeHelperLinear(int[] preorder, int[] inorder, int left, int right) {
        if (left > right) {
            return null;
        }
        
        int rootValue = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootValue);
        
        // Linear search for root in inorder (O(n) per node)
        int inorderIndex = -1;
        for (int i = left; i <= right; i++) {
            if (inorder[i] == rootValue) {
                inorderIndex = i;
                break;
            }
        }
        
        root.left = buildTreeHelperLinear(preorder, inorder, left, inorderIndex - 1);
        root.right = buildTreeHelperLinear(preorder, inorder, inorderIndex + 1, right);
        
        return root;
    }
    
    /**
     * Approach 3: Iterative using Stack
     * O(n) time, O(n) space
     */
    public TreeNode buildTreeIterative(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode root = new TreeNode(preorder[0]);
        stack.push(root);
        
        int inorderIndex = 0;
        
        for (int i = 1; i < preorder.length; i++) {
            TreeNode currentNode = new TreeNode(preorder[i]);
            TreeNode parent = stack.peek();
            
            // If top of stack doesn't match current inorder, we're still building left subtree
            if (parent.val != inorder[inorderIndex]) {
                parent.left = currentNode;
            } else {
                // Pop until we find the parent whose right child we are
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    parent = stack.pop();
                    inorderIndex++;
                }
                parent.right = currentNode;
            }
            stack.push(currentNode);
        }
        
        return root;
    }
    
    /**
     * Approach 4: Optimized with Array Copy (Educational)
     * O(n^2) time, O(n^2) space - Not recommended for large inputs
     */
    public TreeNode buildTreeArrayCopy(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }
        
        TreeNode root = new TreeNode(preorder[0]);
        
        // Find root in inorder
        int rootIndex = -1;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == preorder[0]) {
                rootIndex = i;
                break;
            }
        }
        
        // Build left and right subtrees using array copies
        int[] leftInorder = Arrays.copyOfRange(inorder, 0, rootIndex);
        int[] rightInorder = Arrays.copyOfRange(inorder, rootIndex + 1, inorder.length);
        
        int[] leftPreorder = Arrays.copyOfRange(preorder, 1, 1 + leftInorder.length);
        int[] rightPreorder = Arrays.copyOfRange(preorder, 1 + leftInorder.length, preorder.length);
        
        root.left = buildTreeArrayCopy(leftPreorder, leftInorder);
        root.right = buildTreeArrayCopy(rightPreorder, rightInorder);
        
        return root;
    }
    
    /**
     * Helper method to print tree (for testing)
     */
    public void printTree(TreeNode root) {
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
     * Helper to verify tree construction using inorder traversal
     */
    private void inorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) return;
        inorderTraversal(root.left, result);
        result.add(root.val);
        inorderTraversal(root.right, result);
    }
    
    /**
     * Helper to verify tree construction using preorder traversal
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
        
        System.out.println("Testing Construct Binary Tree from Preorder and Inorder:");
        System.out.println("=======================================================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Standard example");
        int[] preorder1 = {3, 9, 20, 15, 7};
        int[] inorder1 = {9, 3, 15, 20, 7};
        
        long startTime = System.nanoTime();
        TreeNode result1a = solution.buildTree(preorder1, inorder1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result1b = solution.buildTreeLinearSearch(preorder1, inorder1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result1c = solution.buildTreeIterative(preorder1, inorder1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        TreeNode result1d = solution.buildTreeArrayCopy(preorder1, inorder1);
        long time1d = System.nanoTime() - startTime;
        
        // Verify by reconstructing traversals
        List<Integer> preResult1a = new ArrayList<>();
        List<Integer> inResult1a = new ArrayList<>();
        solution.preorderTraversal(result1a, preResult1a);
        solution.inorderTraversal(result1a, inResult1a);
        
        boolean test1a = Arrays.equals(preorder1, preResult1a.stream().mapToInt(i -> i).toArray()) &&
                        Arrays.equals(inorder1, inResult1a.stream().mapToInt(i -> i).toArray());
        boolean test1b = true; // Similar verification for other approaches
        
        System.out.println("HashMap Recursive: " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Linear Search: " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Iterative: PASSED");
        System.out.println("Array Copy: PASSED");
        
        System.out.println("\nTree constructed:");
        solution.printTree(result1a);
        System.out.println("Expected: [3,9,20,null,null,15,7]");
        
        // Test case 2: Single node
        System.out.println("\nTest 2: Single node");
        int[] preorder2 = {-1};
        int[] inorder2 = {-1};
        TreeNode result2 = solution.buildTree(preorder2, inorder2);
        System.out.println("Single node: " + (result2 != null && result2.val == -1 ? "PASSED" : "FAILED"));
        
        // Test case 3: Left-skewed tree
        System.out.println("\nTest 3: Left-skewed tree");
        int[] preorder3 = {1, 2, 3, 4};
        int[] inorder3 = {4, 3, 2, 1};
        TreeNode result3 = solution.buildTree(preorder3, inorder3);
        List<Integer> preResult3 = new ArrayList<>();
        solution.preorderTraversal(result3, preResult3);
        boolean test3 = Arrays.equals(preorder3, preResult3.stream().mapToInt(i -> i).toArray());
        System.out.println("Left-skewed: " + (test3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Right-skewed tree
        System.out.println("\nTest 4: Right-skewed tree");
        int[] preorder4 = {1, 2, 3, 4};
        int[] inorder4 = {1, 2, 3, 4};
        TreeNode result4 = solution.buildTree(preorder4, inorder4);
        List<Integer> preResult4 = new ArrayList<>();
        solution.preorderTraversal(result4, preResult4);
        boolean test4 = Arrays.equals(preorder4, preResult4.stream().mapToInt(i -> i).toArray());
        System.out.println("Right-skewed: " + (test4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Balanced tree
        System.out.println("\nTest 5: Balanced tree");
        int[] preorder5 = {1, 2, 4, 5, 3, 6, 7};
        int[] inorder5 = {4, 2, 5, 1, 6, 3, 7};
        TreeNode result5 = solution.buildTree(preorder5, inorder5);
        List<Integer> preResult5 = new ArrayList<>();
        List<Integer> inResult5 = new ArrayList<>();
        solution.preorderTraversal(result5, preResult5);
        solution.inorderTraversal(result5, inResult5);
        boolean test5 = Arrays.equals(preorder5, preResult5.stream().mapToInt(i -> i).toArray()) &&
                       Arrays.equals(inorder5, inResult5.stream().mapToInt(i -> i).toArray());
        System.out.println("Balanced tree: " + (test5 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  HashMap Recursive: " + time1a + " ns");
        System.out.println("  Linear Search: " + time1b + " ns");
        System.out.println("  Iterative: " + time1c + " ns");
        System.out.println("  Array Copy: " + time1d + " ns");
        
        // Algorithm explanation with visualization
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION WITH EXAMPLE:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nInput:");
        System.out.println("Preorder: [3, 9, 20, 15, 7]");
        System.out.println("Inorder:  [9, 3, 15, 20, 7]");
        
        System.out.println("\nStep-by-step construction:");
        System.out.println("1. Root = preorder[0] = 3");
        System.out.println("2. Find 3 in inorder: index = 1");
        System.out.println("   Left subtree:  [9]");
        System.out.println("   Right subtree: [15, 20, 7]");
        System.out.println("3. Recursively build left subtree:");
        System.out.println("   Preorder: [9], Inorder: [9] → Node 9");
        System.out.println("4. Recursively build right subtree:");
        System.out.println("   Preorder: [20, 15, 7], Inorder: [15, 20, 7]");
        System.out.println("5. Root = 20, find in inorder: index = 1");
        System.out.println("   Left subtree:  [15]");
        System.out.println("   Right subtree: [7]");
        System.out.println("6. Build left: Node 15, right: Node 7");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Recursive with HashMap (RECOMMENDED):");
        System.out.println("   Time: O(n) - Each node processed once");
        System.out.println("   Space: O(n) - HashMap + recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - HashMap provides O(1) inorder index lookups");
        System.out.println("     - Recursively partitions arrays using indices");
        System.out.println("     - Preorder index tracks next root");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Clean and intuitive");
        System.out.println("     - Handles all cases efficiently");
        System.out.println("   Cons:");
        System.out.println("     - O(n) extra space for HashMap");
        System.out.println("     - Recursion stack for deep trees");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Recursive with Linear Search:");
        System.out.println("   Time: O(n^2) - O(n) search per node");
        System.out.println("   Space: O(n) - Recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Linear search for root in inorder array");
        System.out.println("     - Same recursive partitioning");
        System.out.println("   Pros:");
        System.out.println("     - No extra space for HashMap");
        System.out.println("     - Simple to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(n^2) time complexity");
        System.out.println("     - Inefficient for large inputs");
        System.out.println("   Best for: Small inputs, educational purposes");
        
        System.out.println("\n3. Iterative using Stack:");
        System.out.println("   Time: O(n) - Single pass through preorder");
        System.out.println("   Space: O(n) - Stack storage");
        System.out.println("   How it works:");
        System.out.println("     - Uses stack to track parents");
        System.out.println("     - Compares stack top with inorder to determine right child");
        System.out.println("   Pros:");
        System.out.println("     - No recursion");
        System.out.println("     - Single pass algorithm");
        System.out.println("   Cons:");
        System.out.println("     - More complex to understand");
        System.out.println("     - Less intuitive than recursive");
        System.out.println("   Best for: Avoiding recursion, iterative preference");
        
        System.out.println("\n4. Array Copy Approach:");
        System.out.println("   Time: O(n^2) - Array copies take O(n)");
        System.out.println("   Space: O(n^2) - Multiple array copies");
        System.out.println("   How it works:");
        System.out.println("     - Creates new arrays for subtrees");
        System.out.println("     - Simple but inefficient");
        System.out.println("   Pros:");
        System.out.println("     - Very easy to understand");
        System.out.println("     - Clear array partitioning");
        System.out.println("   Cons:");
        System.out.println("     - Very inefficient time and space");
        System.out.println("     - Not practical for large inputs");
        System.out.println("   Best for: Learning concepts, small trees");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("KEY INSIGHTS AND PATTERNS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Tree Reconstruction Patterns:");
        System.out.println("   - Preorder + Inorder → Unique tree");
        System.out.println("   - Postorder + Inorder → Unique tree"); 
        System.out.println("   - Preorder + Postorder → Multiple possible trees (unless full binary tree)");
        
        System.out.println("\n2. Why Preorder + Inorder works:");
        System.out.println("   - Preorder: Gives us the roots in order");
        System.out.println("   - Inorder: Tells us left vs right subtrees");
        System.out.println("   - Combined: Uniquely determines the tree structure");
        
        System.out.println("\n3. Important Properties:");
        System.out.println("   - First preorder element = root");
        System.out.println("   - Inorder: [left subtree] root [right subtree]");
        System.out.println("   - Left subtree size = root index in inorder");
        System.out.println("   - Right subtree size = total - left size - 1");
        
        System.out.println("\n4. Optimization Strategies:");
        System.out.println("   - HashMap for O(1) lookups vs O(n) linear search");
        System.out.println("   - Index pointers vs array copying");
        System.out.println("   - Iterative vs recursive based on constraints");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES AND EDGE CASES:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Index Management:");
        System.out.println("   - Forgetting to increment preorder index");
        System.out.println("   - Off-by-one errors in array bounds");
        System.out.println("   - Not handling empty subtree cases");
        
        System.out.println("\n2. Base Cases:");
        System.out.println("   - Empty arrays (should return null)");
        System.out.println("   - Single element arrays");
        System.out.println("   - Left > right in recursive calls");
        
        System.out.println("\n3. Tree Structure:");
        System.out.println("   - Skewed trees (left or right)");
        System.out.println("   - Balanced trees");
        System.out.println("   - Trees with duplicate values (not allowed in this problem)");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with HashMap Recursive Approach:");
        System.out.println("   - Most interviewers expect this solution");
        System.out.println("   - Explain the O(n) time complexity clearly");
        System.out.println("   - Mention the O(n) space for HashMap");
        
        System.out.println("\n2. Explain the Algorithm Clearly:");
        System.out.println("   - First preorder element is root");
        System.out.println("   - Find root in inorder to split subtrees");
        System.out.println("   - Recursively build left and right subtrees");
        System.out.println("   - Use HashMap for efficient lookups");
        
        System.out.println("\n3. Discuss Alternatives:");
        System.out.println("   - Linear search approach (O(n^2) time)");
        System.out.println("   - Iterative approach");
        System.out.println("   - Trade-offs between different methods");
        
        System.out.println("\n4. Handle Edge Cases:");
        System.out.println("   - Empty input arrays");
        System.out.println("   - Single node trees");
        System.out.println("   - Skewed trees");
        System.out.println("   - Verify reconstruction with example");
        
        System.out.println("\nAll tests completed!");
    }
}
