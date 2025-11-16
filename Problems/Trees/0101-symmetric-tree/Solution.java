
## Solution.java

```java
/**
 * 101. Symmetric Tree
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given the root of a binary tree, check whether it is a mirror of itself 
 * (i.e., symmetric around its center).
 * 
 * Key Insights:
 * 1. A tree is symmetric if the left subtree is a mirror of the right subtree
 * 2. Compare corresponding nodes: left.left with right.right AND left.right with right.left
 * 3. Both recursive and iterative approaches work well
 * 4. Handle null cases carefully
 * 
 * Approach (Recursive DFS):
 * 1. Start with root's left and right children
 * 2. Recursively compare outer pairs and inner pairs
 * 3. Base cases: both null (true), one null (false), values not equal (false)
 * 
 * Time Complexity: O(n) where n is number of nodes
 * Space Complexity: O(h) where h is tree height (recursion stack)
 * 
 * Tags: Tree, DFS, BFS, Binary Tree
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
    /**
     * Approach 1: Recursive DFS (Recommended)
     * O(n) time, O(h) space
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isMirror(root.left, root.right);
    }
    
    private boolean isMirror(TreeNode left, TreeNode right) {
        // Base cases
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        
        // Recursively check outer pairs and inner pairs
        return isMirror(left.left, right.right) && isMirror(left.right, right.left);
    }
    
    /**
     * Approach 2: Iterative BFS using Queue
     * O(n) time, O(n) space
     * Level-order traversal comparing nodes in pairs
     */
    public boolean isSymmetricIterativeBFS(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);
        
        while (!queue.isEmpty()) {
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            
            // Both null - continue
            if (left == null && right == null) {
                continue;
            }
            
            // One null or values not equal - not symmetric
            if (left == null || right == null || left.val != right.val) {
                return false;
            }
            
            // Add outer pair and inner pair
            queue.offer(left.left);
            queue.offer(right.right);
            queue.offer(left.right);
            queue.offer(right.left);
        }
        
        return true;
    }
    
    /**
     * Approach 3: Iterative DFS using Stack
     * O(n) time, O(n) space
     * Depth-first comparison using stack
     */
    public boolean isSymmetricIterativeDFS(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root.left);
        stack.push(root.right);
        
        while (!stack.isEmpty()) {
            TreeNode right = stack.pop();
            TreeNode left = stack.pop();
            
            // Both null - continue
            if (left == null && right == null) {
                continue;
            }
            
            // One null or values not equal - not symmetric
            if (left == null || right == null || left.val != right.val) {
                return false;
            }
            
            // Push pairs in specific order for comparison
            stack.push(left.left);
            stack.push(right.right);
            stack.push(left.right);
            stack.push(right.left);
        }
        
        return true;
    }
    
    /**
     * Approach 4: Level Order Traversal with Validation
     * O(n) time, O(n) space
     * Check if each level is symmetric
     */
    public boolean isSymmetricLevelOrder(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> levelValues = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if (node == null) {
                    levelValues.add(null);
                } else {
                    levelValues.add(node.val);
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            
            // Check if current level is symmetric
            if (!isListSymmetric(levelValues)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isListSymmetric(List<Integer> list) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            if (list.get(left) != list.get(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    /**
     * Approach 5: Modified Preorder Traversal
     * O(n) time, O(h) space
     * Compare normal preorder with mirrored preorder
     */
    public boolean isSymmetricPreorder(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        List<Integer> leftPreorder = new ArrayList<>();
        List<Integer> rightPreorder = new ArrayList<>();
        
        preorderLeft(root.left, leftPreorder);
        preorderRight(root.right, rightPreorder);
        
        return leftPreorder.equals(rightPreorder);
    }
    
    private void preorderLeft(TreeNode node, List<Integer> result) {
        if (node == null) {
            result.add(null);
            return;
        }
        result.add(node.val);
        preorderLeft(node.left, result);
        preorderLeft(node.right, result);
    }
    
    private void preorderRight(TreeNode node, List<Integer> result) {
        if (node == null) {
            result.add(null);
            return;
        }
        result.add(node.val);
        preorderRight(node.right, result);
        preorderRight(node.left, result);
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
            currentLevel.add("   ");
        }
        
        if (node == null) {
            currentLevel.set(position, " • ");
        } else {
            currentLevel.set(position, String.format("%2d ", node.val));
            
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
     * Helper method to visualize the mirror comparison process
     */
    private void visualizeMirrorComparison(TreeNode root, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Tree Structure:");
        printTree(root);
        
        if ("Recursive DFS".equals(approach)) {
            visualizeRecursiveDFS(root);
        } else if ("Iterative BFS".equals(approach)) {
            visualizeIterativeBFS(root);
        }
    }
    
    private void visualizeRecursiveDFS(TreeNode root) {
        System.out.println("\nRecursive DFS Mirror Comparison:");
        System.out.println("Comparing left and right subtrees recursively...");
        
        boolean result = isSymmetricWithTrace(root);
        System.out.println("Final Result: " + result);
    }
    
    private boolean isSymmetricWithTrace(TreeNode root) {
        if (root == null) return true;
        return isMirrorWithTrace(root.left, root.right, "Root");
    }
    
    private boolean isMirrorWithTrace(TreeNode left, TreeNode right, String path) {
        System.out.printf("Comparing at %s: ", path);
        
        if (left == null && right == null) {
            System.out.println("Both null → TRUE");
            return true;
        }
        if (left == null || right == null) {
            System.out.printf("One null (left=%s, right=%s) → FALSE%n", 
                left == null ? "null" : left.val, 
                right == null ? "null" : right.val);
            return false;
        }
        if (left.val != right.val) {
            System.out.printf("Values different (%d vs %d) → FALSE%n", left.val, right.val);
            return false;
        }
        
        System.out.printf("Values equal (%d), checking children...%n", left.val);
        
        boolean outer = isMirrorWithTrace(left.left, right.right, path + ".outer");
        boolean inner = isMirrorWithTrace(left.right, right.left, path + ".inner");
        
        boolean result = outer && inner;
        System.out.printf("%s: outer=%s, inner=%s → %s%n", path, outer, inner, result);
        return result;
    }
    
    private void visualizeIterativeBFS(TreeNode root) {
        System.out.println("\nIterative BFS Mirror Comparison:");
        System.out.println("Using queue to compare nodes level by level...");
        
        if (root == null) {
            System.out.println("Empty tree → TRUE");
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);
        int step = 1;
        
        while (!queue.isEmpty()) {
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            
            System.out.printf("Step %d: Comparing ", step++);
            
            if (left == null && right == null) {
                System.out.println("both null → CONTINUE");
                continue;
            }
            
            if (left == null || right == null) {
                System.out.printf("one null (left=%s, right=%s) → FALSE%n",
                    left == null ? "null" : left.val,
                    right == null ? "null" : right.val);
                System.out.println("Final Result: false");
                return;
            }
            
            System.out.printf("(%d vs %d) ", left.val, right.val);
            
            if (left.val != right.val) {
                System.out.println("→ FALSE");
                System.out.println("Final Result: false");
                return;
            }
            
            System.out.println("→ EQUAL, adding children to queue");
            
            // Add outer pair and inner pair
            queue.offer(left.left);
            queue.offer(right.right);
            queue.offer(left.right);
            queue.offer(right.left);
        }
        
        System.out.println("All nodes matched successfully!");
        System.out.println("Final Result: true");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Symmetric Tree:");
        System.out.println("======================\n");
        
        // Test case 1: Symmetric tree
        System.out.println("Test 1: Symmetric tree [1,2,2,3,4,4,3]");
        Integer[] values1 = {1,2,2,3,4,4,3};
        TreeNode root1 = solution.buildTree(values1);
        boolean expected1 = true;
        testImplementation(solution, root1, expected1, "Recursive DFS");
        
        // Test case 2: Asymmetric tree
        System.out.println("\nTest 2: Asymmetric tree [1,2,2,null,3,null,3]");
        Integer[] values2 = {1,2,2,null,3,null,3};
        TreeNode root2 = solution.buildTree(values2);
        boolean expected2 = false;
        testImplementation(solution, root2, expected2, "Recursive DFS");
        
        // Test case 3: Single node
        System.out.println("\nTest 3: Single node [1]");
        Integer[] values3 = {1};
        TreeNode root3 = solution.buildTree(values3);
        boolean expected3 = true;
        testImplementation(solution, root3, expected3, "Recursive DFS");
        
        // Test case 4: Empty tree
        System.out.println("\nTest 4: Empty tree []");
        TreeNode root4 = null;
        boolean expected4 = true;
        testImplementation(solution, root4, expected4, "Recursive DFS");
        
        // Test case 5: Asymmetric values
        System.out.println("\nTest 5: Asymmetric values [1,2,3]");
        Integer[] values5 = {1,2,3};
        TreeNode root5 = solution.buildTree(values5);
        boolean expected5 = false;
        testImplementation(solution, root5, expected5, "Recursive DFS");
        
        // Test case 6: Complex symmetric
        System.out.println("\nTest 6: Complex symmetric tree");
        Integer[] values6 = {1,2,2,3,4,4,3,5,6,7,8,8,7,6,5};
        TreeNode root6 = solution.buildTree(values6);
        boolean expected6 = true;
        testImplementation(solution, root6, expected6, "Recursive DFS");
        
        // Test case 7: Complex asymmetric
        System.out.println("\nTest 7: Complex asymmetric tree");
        Integer[] values7 = {1,2,2,3,4,4,3,5,6,7,8,8,7,6,9};
        TreeNode root7 = solution.buildTree(values7);
        boolean expected7 = false;
        testImplementation(solution, root7, expected7, "Recursive DFS");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: RECURSIVE DFS APPROACH");
        System.out.println("=".repeat(70));
        
        explainRecursiveDFS(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, TreeNode root, 
                                         boolean expected, String approach) {
        long startTime = System.nanoTime();
        boolean result = false;
        switch (approach) {
            case "Recursive DFS":
                result = solution.isSymmetric(root);
                break;
            case "Iterative BFS":
                result = solution.isSymmetricIterativeBFS(root);
                break;
            case "Iterative DFS":
                result = solution.isSymmetricIterativeDFS(root);
                break;
            case "Level Order":
                result = solution.isSymmetricLevelOrder(root);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        boolean passed = (result == expected);
        System.out.printf("%s: Expected=%s, Got=%s, Time=%,d ns - %s%n",
                approach, expected, result, time, (passed ? "PASSED" : "FAILED"));
        
        // Visualization for interesting cases
        if (passed && root != null && getNodeCount(root) <= 15) {
            solution.visualizeMirrorComparison(root, approach);
        }
    }
    
    private static int getNodeCount(TreeNode root) {
        if (root == null) return 0;
        return 1 + getNodeCount(root.left) + getNodeCount(root.right);
    }
    
    private static void comparePerformance(Solution solution) {
        // Create a large symmetric tree for performance testing
        TreeNode largeTree = createLargeSymmetricTree(12); // 2^12 - 1 nodes
        
        System.out.println("Performance test with large symmetric tree (~4095 nodes):");
        
        // Test Recursive DFS
        long startTime = System.nanoTime();
        solution.isSymmetric(largeTree);
        long time1 = System.nanoTime() - startTime;
        
        // Test Iterative BFS
        startTime = System.nanoTime();
        solution.isSymmetricIterativeBFS(largeTree);
        long time2 = System.nanoTime() - startTime;
        
        // Test Iterative DFS
        startTime = System.nanoTime();
        solution.isSymmetricIterativeDFS(largeTree);
        long time3 = System.nanoTime() - startTime;
        
        // Test Level Order
        startTime = System.nanoTime();
        solution.isSymmetricLevelOrder(largeTree);
        long time4 = System.nanoTime() - startTime;
        
        System.out.printf("Recursive DFS:  %,12d ns%n", time1);
        System.out.printf("Iterative BFS:  %,12d ns%n", time2);
        System.out.printf("Iterative DFS:  %,12d ns%n", time3);
        System.out.printf("Level Order:    %,12d ns%n", time4);
    }
    
    private static TreeNode createLargeSymmetricTree(int levels) {
        if (levels <= 0) return null;
        TreeNode root = new TreeNode(1);
        createSymmetricChildren(root, levels - 1, 2);
        return root;
    }
    
    private static void createSymmetricChildren(TreeNode node, int levelsRemaining, int value) {
        if (levelsRemaining <= 0) return;
        
        node.left = new TreeNode(value);
        node.right = new TreeNode(value);
        
        createSymmetricChildren(node.left, levelsRemaining - 1, value + 1);
        createSymmetricChildren(node.right, levelsRemaining - 1, value + 1);
    }
    
    private static void explainRecursiveDFS(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("A tree is symmetric if the left subtree is a mirror of the right subtree.");
        System.out.println("This means we need to compare:");
        System.out.println("  - left.left with right.right (outer pairs)");
        System.out.println("  - left.right with right.left (inner pairs)");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Start with root's left and right children");
        System.out.println("2. Base cases:");
        System.out.println("   - Both null → return true");
        System.out.println("   - One null → return false");
        System.out.println("   - Values not equal → return false");
        System.out.println("3. Recursively check:");
        System.out.println("   - isMirror(left.left, right.right) AND");
        System.out.println("   - isMirror(left.right, right.left)");
        
        System.out.println("\nWhy it works:");
        System.out.println("- The mirror property requires corresponding nodes to match");
        System.out.println("- Outer nodes must mirror each other across the center");
        System.out.println("- Inner nodes must mirror each other across the center");
        System.out.println("- The recursion naturally handles this nested mirroring");
        
        System.out.println("\nExample Walkthrough: [1,2,2,3,4,4,3]");
        Integer[] example = {1,2,2,3,4,4,3};
        TreeNode root = solution.buildTree(example);
        solution.visualizeMirrorComparison(root, "Recursive DFS");
        
        System.out.println("\nTime Complexity: O(n) - Visit each node once");
        System.out.println("Space Complexity: O(h) - Recursion stack height");
        System.out.println("  - Best case (balanced): O(log n)");
        System.out.println("  - Worst case (skewed): O(n)");
    }
    
    private static void checkAllImplementations(Solution solution) {
        Object[][][] testCases = {
            {{1,2,2,3,4,4,3}, {true}},   // Symmetric
            {{1,2,2,null,3,null,3}, {false}}, // Asymmetric
            {{1}, {true}},                // Single node
            {{}, {true}},                 // Empty
            {{1,2,3}, {false}},           // Asymmetric values
            {{1,2,2,3,null,null,3}, {false}} // Structural asymmetry
        };
        
        boolean[] expected = {true, false, true, true, false, false};
        
        String[] methods = {
            "Recursive DFS",
            "Iterative BFS", 
            "Iterative DFS",
            "Level Order",
            "Preorder"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            Integer[] values = (Integer[]) testCases[i][0];
            TreeNode root = solution.buildTree(values);
            
            System.out.printf("\nTest case %d: %s (expected: %s)%n",
                    i + 1, Arrays.toString(values), expected[i]);
            
            boolean[] results = new boolean[methods.length];
            results[0] = solution.isSymmetric(root);
            results[1] = solution.isSymmetricIterativeBFS(root);
            results[2] = solution.isSymmetricIterativeDFS(root);
            results[3] = solution.isSymmetricLevelOrder(root);
            results[4] = solution.isSymmetricPreorder(root);
            
            boolean caseConsistent = true;
            for (int j = 0; j < methods.length; j++) {
                boolean correct = (results[j] == expected[i]);
                System.out.printf("  %-15s: %-5s %s%n", methods[j], results[j],
                        correct ? "✓" : "✗ (expected " + expected[i] + ")");
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
        System.out.println("\n1. RECURSIVE DFS (RECOMMENDED):");
        System.out.println("   Time: O(n) - Visit each node once");
        System.out.println("   Space: O(h) - Recursion stack height");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and elegant");
        System.out.println("     - Easy to understand and implement");
        System.out.println("     - Naturally expresses the mirror concept");
        System.out.println("   Cons:");
        System.out.println("     - Stack overflow for very deep trees");
        System.out.println("   Use when: Interview setting, balanced trees");
        
        System.out.println("\n2. ITERATIVE BFS (QUEUE):");
        System.out.println("   Time: O(n) - Visit each node once");
        System.out.println("   Space: O(n) - Queue storage");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack overflow risk");
        System.out.println("     - Level-by-level comparison");
        System.out.println("   Cons:");
        System.out.println("     - Uses more memory for wide trees");
        System.out.println("   Use when: Very deep trees, avoiding recursion");
        
        System.out.println("\n3. ITERATIVE DFS (STACK):");
        System.out.println("   Time: O(n) - Visit each node once");
        System.out.println("   Space: O(n) - Stack storage");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack overflow");
        System.out.println("     - Depth-first like recursive approach");
        System.out.println("   Cons:");
        System.out.println("     - Similar to recursive but more code");
        System.out.println("   Use when: Prefer iterative over recursive");
        
        System.out.println("\n4. LEVEL ORDER TRAVERSAL:");
        System.out.println("   Time: O(n) - Visit each node once");
        System.out.println("   Space: O(n) - Queue and list storage");
        System.out.println("   Pros:");
        System.out.println("     - Explicit level-by-level checking");
        System.out.println("     - Easy to understand level symmetry");
        System.out.println("   Cons:");
        System.out.println("     - Most memory intensive");
        System.out.println("   Use when: Need to verify level symmetry explicitly");
        
        System.out.println("\n5. PREORDER TRAVERSAL:");
        System.out.println("   Time: O(n) - Two traversals");
        System.out.println("   Space: O(n) - Store traversal results");
        System.out.println("   Pros:");
        System.out.println("     - Interesting alternative approach");
        System.out.println("     - Good for learning traversal variations");
        System.out.println("   Cons:");
        System.out.println("     - Inefficient memory usage");
        System.out.println("   Use when: Exploring different approaches");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with recursive approach - it's the most intuitive");
        System.out.println("2. Explain the mirror concept clearly");
        System.out.println("3. Handle all base cases (null checks)");
        System.out.println("4. Mention iterative approaches as alternatives");
        System.out.println("5. Discuss time/space complexity trade-offs");
        System.out.println("6. Consider edge cases: single node, empty tree, asymmetric structure");
        System.out.println("=".repeat(70));
    }
}
