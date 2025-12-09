
## Solution.java

```java
/**
 * 124. Binary Tree Maximum Path Sum
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given the root of a binary tree, return the maximum path sum of any non-empty path.
 * A path is a sequence of nodes where each pair of adjacent nodes has an edge connecting them.
 * 
 * Key Insights:
 * 1. Use post-order DFS to process children before parent
 * 2. For each node, calculate maximum contribution to parent: node.val + max(left, right)
 * 3. Update global maximum considering path through node: node.val + left + right
 * 4. Handle negative values by comparing with 0
 * 5. A path can be linear (for parent's use) or arching (through node connecting both children)
 * 
 * Approach (Post-order DFS):
 * 1. Traverse tree in post-order (left, right, root)
 * 2. For each node, compute maximum single path contribution
 * 3. Update global maximum with path through current node
 * 4. Return maximum single path contribution to parent
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(h) where h is tree height
 * 
 * Tags: Dynamic Programming, Tree, DFS, Binary Tree
 */

import java.util.*;

// TreeNode definition
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
    private int maxSum;
    
    /**
     * Approach 1: Post-order DFS with Global Variable (Recommended)
     * O(n) time, O(h) space
     */
    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        maxGain(root);
        return maxSum;
    }
    
    private int maxGain(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        // Recursively get maximum gains from left and right subtrees
        // Compare with 0 to handle negative contributions
        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);
        
        // The price to start a new path where node is the highest point
        // This is the arching path: left -> node -> right
        int priceNewPath = node.val + leftGain + rightGain;
        
        // Update global maximum
        maxSum = Math.max(maxSum, priceNewPath);
        
        // For recursion, return the maximum gain if we continue the same path
        // This is the linear path for parent's use: node + max(left, right)
        return node.val + Math.max(leftGain, rightGain);
    }
    
    /**
     * Approach 2: DFS with Return Object
     * O(n) time, O(h) space
     * Uses custom return type to avoid global variable
     */
    public int maxPathSumReturnObject(TreeNode root) {
        Result result = maxPathHelper(root);
        return result.maxPathSum;
    }
    
    private Result maxPathHelper(TreeNode node) {
        if (node == null) {
            return new Result(Integer.MIN_VALUE, 0);
        }
        
        // Recursively get results from left and right subtrees
        Result left = maxPathHelper(node.left);
        Result right = maxPathHelper(node.right);
        
        // Maximum single path contribution (for parent's use)
        int maxSinglePath = Math.max(
            Math.max(left.maxSinglePath, right.maxSinglePath) + node.val,
            node.val
        );
        
        // Maximum arching path through current node
        int maxThroughNode = Math.max(
            left.maxSinglePath + node.val + right.maxSinglePath,
            maxSinglePath
        );
        
        // Overall maximum path sum
        int maxPathSum = Math.max(
            Math.max(left.maxPathSum, right.maxPathSum),
            maxThroughNode
        );
        
        return new Result(maxPathSum, maxSinglePath);
    }
    
    class Result {
        int maxPathSum;      // Maximum path sum in subtree
        int maxSinglePath;   // Maximum single path ending at root of subtree
        
        Result(int maxPathSum, int maxSinglePath) {
            this.maxPathSum = maxPathSum;
            this.maxSinglePath = maxSinglePath;
        }
    }
    
    /**
     * Approach 3: Iterative Post-order Traversal
     * O(n) time, O(n) space
     * Uses stack for iterative traversal
     */
    public int maxPathSumIterative(TreeNode root) {
        if (root == null) return 0;
        
        Map<TreeNode, Integer> maxSinglePath = new HashMap<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode lastVisited = null;
        TreeNode node = root;
        int maxSum = Integer.MIN_VALUE;
        
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                TreeNode peekNode = stack.peek();
                if (peekNode.right != null && lastVisited != peekNode.right) {
                    node = peekNode.right;
                } else {
                    // Process node
                    TreeNode current = stack.pop();
                    
                    int leftMax = maxSinglePath.getOrDefault(current.left, 0);
                    int rightMax = maxSinglePath.getOrDefault(current.right, 0);
                    
                    // Only positive contributions
                    leftMax = Math.max(leftMax, 0);
                    rightMax = Math.max(rightMax, 0);
                    
                    // Update global maximum
                    maxSum = Math.max(maxSum, current.val + leftMax + rightMax);
                    
                    // Maximum single path contribution
                    int currentMax = current.val + Math.max(leftMax, rightMax);
                    maxSinglePath.put(current, currentMax);
                    
                    lastVisited = current;
                }
            }
        }
        
        return maxSum;
    }
    
    /**
     * Approach 4: Brute Force (Naive)
     * O(n²) time, O(h) space
     * For each node, consider it as root and find maximum path
     */
    public int maxPathSumBruteForce(TreeNode root) {
        if (root == null) return 0;
        
        // Consider each node as the highest point in path
        int[] maxSum = {Integer.MIN_VALUE};
        traverseAllNodes(root, maxSum);
        return maxSum[0];
    }
    
    private void traverseAllNodes(TreeNode node, int[] maxSum) {
        if (node == null) return;
        
        // Calculate maximum path with current node as highest point
        int currentMax = calculateMaxPathThroughNode(node);
        maxSum[0] = Math.max(maxSum[0], currentMax);
        
        traverseAllNodes(node.left, maxSum);
        traverseAllNodes(node.right, maxSum);
    }
    
    private int calculateMaxPathThroughNode(TreeNode node) {
        if (node == null) return Integer.MIN_VALUE;
        
        int leftMax = maxSinglePath(node.left);
        int rightMax = maxSinglePath(node.right);
        
        leftMax = Math.max(leftMax, 0);
        rightMax = Math.max(rightMax, 0);
        
        return node.val + leftMax + rightMax;
    }
    
    private int maxSinglePath(TreeNode node) {
        if (node == null) return 0;
        
        int left = Math.max(maxSinglePath(node.left), 0);
        int right = Math.max(maxSinglePath(node.right), 0);
        
        return node.val + Math.max(left, right);
    }
    
    /**
     * Approach 5: Modified DFS with Path Tracking
     * O(n) time, O(h) space
     * Tracks actual path for visualization
     */
    public int maxPathSumWithPath(TreeNode root) {
        PathResult result = maxPathWithPathHelper(root);
        return result.maxSum;
    }
    
    private PathResult maxPathWithPathHelper(TreeNode node) {
        if (node == null) {
            return new PathResult(Integer.MIN_VALUE, 0, new ArrayList<>(), new ArrayList<>());
        }
        
        PathResult left = maxPathWithPathHelper(node.left);
        PathResult right = maxPathWithPathHelper(node.right);
        
        // Calculate single path contributions (comparing with 0)
        int leftSingle = Math.max(left.singlePathSum, 0);
        int rightSingle = Math.max(right.singlePathSum, 0);
        
        // Single path for parent
        int singlePathSum = node.val + Math.max(leftSingle, rightSingle);
        List<Integer> singlePath = new ArrayList<>();
        singlePath.add(node.val);
        
        if (leftSingle > rightSingle && leftSingle > 0) {
            singlePath.addAll(left.singlePath);
        } else if (rightSingle > 0) {
            singlePath.addAll(right.singlePath);
        }
        
        // Path through current node
        int throughNodeSum = node.val + leftSingle + rightSingle;
        List<Integer> throughNodePath = new ArrayList<>();
        if (leftSingle > 0) {
            throughNodePath.addAll(left.singlePath);
        }
        throughNodePath.add(node.val);
        if (rightSingle > 0) {
            // Reverse right path to maintain order
            List<Integer> rightPath = new ArrayList<>(right.singlePath);
            Collections.reverse(rightPath);
            throughNodePath.addAll(rightPath);
        }
        
        // Determine maximum path sum
        int maxSum = Math.max(Math.max(left.maxSum, right.maxSum), throughNodeSum);
        List<Integer> maxPath = throughNodePath;
        
        if (left.maxSum > maxSum) {
            maxSum = left.maxSum;
            maxPath = left.maxPath;
        }
        if (right.maxSum > maxSum) {
            maxSum = right.maxSum;
            maxPath = right.maxPath;
        }
        
        return new PathResult(maxSum, singlePathSum, singlePath, maxPath);
    }
    
    class PathResult {
        int maxSum;
        int singlePathSum;
        List<Integer> singlePath;
        List<Integer> maxPath;
        
        PathResult(int maxSum, int singlePathSum, List<Integer> singlePath, List<Integer> maxPath) {
            this.maxSum = maxSum;
            this.singlePathSum = singlePathSum;
            this.singlePath = singlePath;
            this.maxPath = maxPath;
        }
    }
    
    /**
     * Helper method to build tree from array (for testing)
     */
    public TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode current = queue.poll();
            
            // Left child
            if (i < values.length && values[i] != null) {
                current.left = new TreeNode(values[i]);
                queue.offer(current.left);
            }
            i++;
            
            // Right child
            if (i < values.length && values[i] != null) {
                current.right = new TreeNode(values[i]);
                queue.offer(current.right);
            }
            i++;
        }
        
        return root;
    }
    
    /**
     * Helper method to visualize tree structure
     */
    public void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        List<List<String>> treeLines = new ArrayList<>();
        buildTreeVisualization(root, treeLines, 0, 0, getTreeHeight(root));
        
        for (List<String> line : treeLines) {
            for (String node : line) {
                System.out.print(node);
            }
            System.out.println();
        }
    }
    
    private void buildTreeVisualization(TreeNode node, List<List<String>> treeLines, 
                                      int level, int position, int totalLevels) {
        if (level == treeLines.size()) {
            treeLines.add(new ArrayList<>());
        }
        
        List<String> currentLevel = treeLines.get(level);
        
        // Fill with spaces if needed
        while (currentLevel.size() <= position) {
            currentLevel.add("    ");
        }
        
        if (node == null) {
            currentLevel.set(position, "  • ");
        } else {
            currentLevel.set(position, String.format("%3d ", node.val));
            
            // Calculate positions for children
            int offset = (int) Math.pow(2, totalLevels - level - 2);
            if (offset < 1) offset = 1;
            
            buildTreeVisualization(node.left, treeLines, level + 1, position - offset, totalLevels);
            buildTreeVisualization(node.right, treeLines, level + 1, position + offset, totalLevels);
        }
    }
    
    private int getTreeHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getTreeHeight(node.left), getTreeHeight(node.right));
    }
    
    /**
     * Helper method to visualize the DFS process
     */
    public void visualizeMaxPathSum(TreeNode root, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Tree Structure:");
        printTree(root);
        
        if ("DFS Recursive".equals(approach)) {
            visualizeDFSRecursive(root);
        } else if ("Return Object".equals(approach)) {
            visualizeReturnObject(root);
        }
    }
    
    private void visualizeDFSRecursive(TreeNode root) {
        System.out.println("\nPost-order DFS Process:");
        System.out.println("Node | LeftGain | RightGain | NewPath | ReturnToParent | GlobalMax");
        System.out.println("-----|----------|-----------|---------|----------------|----------");
        
        int[] globalMax = {Integer.MIN_VALUE};
        dfsWithVisualization(root, globalMax, 0);
        
        System.out.println("Final Maximum Path Sum: " + globalMax[0]);
    }
    
    private int dfsWithVisualization(TreeNode node, int[] globalMax, int depth) {
        if (node == null) {
            return 0;
        }
        
        String indent = "  ".repeat(depth);
        
        // Recursively process children
        int leftGain = dfsWithVisualization(node.left, globalMax, depth + 1);
        int rightGain = dfsWithVisualization(node.right, globalMax, depth + 1);
        
        // Compare with 0 to handle negative contributions
        leftGain = Math.max(leftGain, 0);
        rightGain = Math.max(rightGain, 0);
        
        // Calculate new path through current node
        int priceNewPath = node.val + leftGain + rightGain;
        
        // Update global maximum
        globalMax[0] = Math.max(globalMax[0], priceNewPath);
        
        // Calculate return value for parent
        int returnToParent = node.val + Math.max(leftGain, rightGain);
        
        // Print current step
        System.out.printf("%s%d | %8d | %9d | %7d | %14d | %9d%n",
            indent, node.val, leftGain, rightGain, priceNewPath, returnToParent, globalMax[0]);
        
        return returnToParent;
    }
    
    private void visualizeReturnObject(TreeNode root) {
        System.out.println("\nDFS with Return Object Process:");
        System.out.println("Node | SinglePath | ThroughNode | MaxSum");
        System.out.println("-----|------------|-------------|-------");
        
        PathResult result = maxPathWithVisualization(root);
        System.out.println("Final Maximum Path Sum: " + result.maxSum);
        System.out.println("Maximum Path: " + result.maxPath);
    }
    
    private PathResult maxPathWithVisualization(TreeNode node) {
        if (node == null) {
            return new PathResult(Integer.MIN_VALUE, 0, new ArrayList<>(), new ArrayList<>());
        }
        
        PathResult left = maxPathWithVisualization(node.left);
        PathResult right = maxPathWithVisualization(node.right);
        
        // Calculate single path contributions
        int leftSingle = Math.max(left.singlePathSum, 0);
        int rightSingle = Math.max(right.singlePathSum, 0);
        
        // Single path for parent
        int singlePathSum = node.val + Math.max(leftSingle, rightSingle);
        
        // Path through current node
        int throughNodeSum = node.val + leftSingle + rightSingle;
        
        // Determine maximum path sum
        int maxSum = Math.max(Math.max(left.maxSum, right.maxSum), throughNodeSum);
        
        System.out.printf("%4d | %10d | %11d | %6d%n",
            node.val, singlePathSum, throughNodeSum, maxSum);
        
        // For simplicity, return basic result
        return new PathResult(maxSum, singlePathSum, new ArrayList<>(), new ArrayList<>());
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Binary Tree Maximum Path Sum:");
        System.out.println("====================================\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: Standard example [1,2,3]");
        Integer[] values1 = {1,2,3};
        TreeNode root1 = solution.buildTree(values1);
        int expected1 = 6;
        testImplementation(solution, root1, expected1, "DFS Recursive");
        
        // Test case 2: Complex example with negative values
        System.out.println("\nTest 2: Complex example [-10,9,20,null,null,15,7]");
        Integer[] values2 = {-10,9,20,null,null,15,7};
        TreeNode root2 = solution.buildTree(values2);
        int expected2 = 42;
        testImplementation(solution, root2, expected2, "DFS Recursive");
        
        // Test case 3: Single node
        System.out.println("\nTest 3: Single node [5]");
        Integer[] values3 = {5};
        TreeNode root3 = solution.buildTree(values3);
        int expected3 = 5;
        testImplementation(solution, root3, expected3, "DFS Recursive");
        
        // Test case 4: All negative values
        System.out.println("\nTest 4: All negative values [-2,-1,-3]");
        Integer[] values4 = {-2,-1,-3};
        TreeNode root4 = solution.buildTree(values4);
        int expected4 = -1;
        testImplementation(solution, root4, expected4, "DFS Recursive");
        
        // Test case 5: Complex tree
        System.out.println("\nTest 5: Complex tree [5,4,8,11,null,13,4,7,2,null,null,null,1]");
        Integer[] values5 = {5,4,8,11,null,13,4,7,2,null,null,null,1};
        TreeNode root5 = solution.buildTree(values5);
        int expected5 = 48;
        testImplementation(solution, root5, expected5, "DFS Recursive");
        
        // Test case 6: Linear tree
        System.out.println("\nTest 6: Linear tree [1,2,null,3,null,4]");
        Integer[] values6 = {1,2,null,3,null,4};
        TreeNode root6 = solution.buildTree(values6);
        int expected6 = 10;
        testImplementation(solution, root6, expected6, "DFS Recursive");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: POST-ORDER DFS");
        System.out.println("=".repeat(70));
        
        explainPostOrderDFS(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, TreeNode root, 
                                         int expected, String approach) {
        System.out.println("Tree:");
        solution.printTree(root);
        
        long startTime = System.nanoTime();
        int result = 0;
        switch (approach) {
            case "DFS Recursive":
                result = solution.maxPathSum(root);
                break;
            case "Return Object":
                result = solution.maxPathSumReturnObject(root);
                break;
            case "Iterative":
                result = solution.maxPathSumIterative(root);
                break;
            case "Brute Force":
                result = solution.maxPathSumBruteForce(root);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        boolean passed = (result == expected);
        System.out.printf("%s: Expected=%d, Got=%d, Time=%,d ns - %s%n",
                approach, expected, result, time, (passed ? "PASSED" : "FAILED"));
        
        // Visualization for interesting cases
        if (passed && getTreeSize(root) <= 15) {
            solution.visualizeMaxPathSum(root, approach);
        }
    }
    
    private static int getTreeSize(TreeNode root) {
        if (root == null) return 0;
        return 1 + getTreeSize(root.left) + getTreeSize(root.right);
    }
    
    private static void comparePerformance(Solution solution) {
        // Create a large tree for performance testing
        TreeNode largeTree = createLargeTree(15); // ~32,000 nodes
        
        System.out.println("Performance test with large tree (~32,000 nodes):");
        
        // Test DFS Recursive
        long startTime = System.nanoTime();
        solution.maxPathSum(largeTree);
        long time1 = System.nanoTime() - startTime;
        
        // Test Return Object
        startTime = System.nanoTime();
        solution.maxPathSumReturnObject(largeTree);
        long time2 = System.nanoTime() - startTime;
        
        // Test Iterative
        startTime = System.nanoTime();
        solution.maxPathSumIterative(largeTree);
        long time3 = System.nanoTime() - startTime;
        
        // Skip Brute Force for large tree (too slow)
        
        System.out.printf("DFS Recursive:  %,12d ns%n", time1);
        System.out.printf("Return Object:  %,12d ns%n", time2);
        System.out.printf("Iterative:      %,12d ns%n", time3);
        System.out.println("Brute Force:    (skipped - too slow)");
    }
    
    private static TreeNode createLargeTree(int levels) {
        if (levels <= 0) return null;
        TreeNode root = new TreeNode(1);
        createChildren(root, levels - 1, 2);
        return root;
    }
    
    private static void createChildren(TreeNode node, int levelsRemaining, int value) {
        if (levelsRemaining <= 0) return;
        
        node.left = new TreeNode(value);
        node.right = new TreeNode(value + 1);
        
        createChildren(node.left, levelsRemaining - 1, value + 2);
        createChildren(node.right, levelsRemaining - 1, value + 3);
    }
    
    private static void explainPostOrderDFS(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("For each node, we need to consider two types of paths:");
        System.out.println("1. Linear path: A path that can be extended to parent (single branch)");
        System.out.println("2. Arching path: A path that goes through the node connecting both children");
        System.out.println();
        System.out.println("The arching path cannot be extended to parent, so we only return");
        System.out.println("the linear path contribution to the parent.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Traverse tree in post-order (left, right, root)");
        System.out.println("2. For each node:");
        System.out.println("   - Get maximum gains from left and right subtrees (compared with 0)");
        System.out.println("   - Calculate arching path: node.val + leftGain + rightGain");
        System.out.println("   - Update global maximum with arching path");
        System.out.println("   - Return linear path: node.val + max(leftGain, rightGain) to parent");
        
        System.out.println("\nWhy it works:");
        System.out.println("- Post-order ensures we process children before parent");
        System.out.println("- Comparing with 0 handles negative contributions effectively");
        System.out.println("- Global maximum tracks the best arching path found so far");
        System.out.println("- Return value provides what parent needs for its calculation");
        
        System.out.println("\nExample Walkthrough: [-10,9,20,null,null,15,7]");
        Integer[] example = {-10,9,20,null,null,15,7};
        TreeNode root = solution.buildTree(example);
        solution.visualizeMaxPathSum(root, "DFS Recursive");
        
        System.out.println("\nTime Complexity: O(n) - Visit each node once");
        System.out.println("Space Complexity: O(h) - Recursion stack height");
        System.out.println("  - Worst case (skewed): O(n)");
        System.out.println("  - Best case (balanced): O(log n)");
    }
    
    private static void checkAllImplementations(Solution solution) {
        Object[][][] testCases = {
            {{1,2,3}, {6}},                    // Standard
            {{-10,9,20,null,null,15,7}, {42}}, // Complex
            {{5}, {5}},                        // Single node
            {{-2,-1,-3}, {-1}},               // All negative
            {{1,-2,3}, {4}},                  // Mixed
            {{-3}, {-3}}                      // Single negative
        };
        
        int[] expected = {6, 42, 5, -1, 4, -3};
        
        String[] methods = {
            "DFS Recursive",
            "Return Object", 
            "Iterative",
            "Brute Force",
            "With Path"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            Integer[] values = (Integer[]) testCases[i][0];
            int expectedVal = ((int[]) testCases[i][1])[0];
            TreeNode root = solution.buildTree(values);
            
            System.out.printf("\nTest case %d: %s (expected: %d)%n",
                    i + 1, Arrays.toString(values), expectedVal);
            
            int[] results = new int[methods.length];
            results[0] = solution.maxPathSum(solution.buildTree(values));
            results[1] = solution.maxPathSumReturnObject(solution.buildTree(values));
            results[2] = solution.maxPathSumIterative(solution.buildTree(values));
            results[3] = solution.maxPathSumBruteForce(solution.buildTree(values));
            results[4] = solution.maxPathSumWithPath(solution.buildTree(values));
            
            boolean caseConsistent = true;
            for (int j = 0; j < methods.length; j++) {
                boolean correct = (results[j] == expectedVal);
                System.out.printf("  %-15s: %d %s%n", methods[j], results[j],
                        correct ? "✓" : "✗ (expected " + expectedVal + ")");
                if (!correct) {
                    caseConsistent = false;
                    allConsistent = false;
                }
            }
            
            if (!caseConsistent) {
                System.out.println("  INCONSISTENT RESULTS!");
            }
        }
        
        System.out.println("\nOverall consistency: " + (allConsistent ? "ALL PASSED ✓" : "SOME FAILED ✗"));
        
        // Algorithm comparison table
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON SUMMARY");
        System.out.println("=".repeat(70));
        
        printAlgorithmComparison();
    }
    
    private static void printAlgorithmComparison() {
        System.out.println("\n1. DFS RECURSIVE (RECOMMENDED):");
        System.out.println("   Time: O(n) - Linear time");
        System.out.println("   Space: O(h) - Recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - Most intuitive and elegant");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Uses global variable or parameter passing");
        System.out.println("   Use when: Interview setting, optimal solution needed");
        
        System.out.println("\n2. RETURN OBJECT APPROACH:");
        System.out.println("   Time: O(n) - Linear time");
        System.out.println("   Space: O(h) - Recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - No global variables");
        System.out.println("     - Clear separation of concerns");
        System.out.println("     - Returns all needed information");
        System.out.println("   Cons:");
        System.out.println("     - More complex return type");
        System.out.println("     - Slightly more code");
        System.out.println("   Use when: Prefer pure functions without globals");
        
        System.out.println("\n3. ITERATIVE APPROACH:");
        System.out.println("   Time: O(n) - Linear time");
        System.out.println("   Space: O(n) - Stack and map storage");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack overflow risk");
        System.out.println("     - Explicit stack management");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Uses more memory");
        System.out.println("   Use when: Very deep trees, avoiding recursion");
        
        System.out.println("\n4. BRUTE FORCE:");
        System.out.println("   Time: O(n²) - Quadratic time");
        System.out.println("   Space: O(h) - Recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - Simple to understand");
        System.out.println("     - Straightforward implementation");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large trees");
        System.out.println("     - Redundant calculations");
        System.out.println("   Use when: Understanding the problem concept");
        
        System.out.println("\n5. WITH PATH TRACKING:");
        System.out.println("   Time: O(n) - Linear time");
        System.out.println("   Space: O(h) - Recursion stack and path storage");
        System.out.println("   Pros:");
        System.out.println("     - Tracks actual maximum path");
        System.out.println("     - Useful for visualization");
        System.out.println("   Cons:");
        System.out.println("     - More memory for path storage");
        System.out.println("     - More complex implementation");
        System.out.println("   Use when: Need to know the actual path, not just sum");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with explaining the two types of paths (linear and arching)");
        System.out.println("2. Describe the post-order DFS approach clearly");
        System.out.println("3. Emphasize handling negative values (comparing with 0)");
        System.out.println("4. Implement the recursive solution with global maximum");
        System.out.println("5. Discuss time/space complexity and edge cases");
        System.out.println("6. Consider mentioning alternative approaches");
        System.out.println("=".repeat(70));
    }
}
