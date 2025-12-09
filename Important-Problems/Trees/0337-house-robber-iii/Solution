
## Solution.java

```java
/**
 * 337. House Robber III
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * The thief has found himself a new place for his thievery again. 
 * There is only one entrance to this area, called the root.
 * Besides the root, each house has one and only one parent house. 
 * After a tour, the smart thief realized that all houses in this place form a binary tree. 
 * It will automatically contact the police if two directly-linked houses are broken into on the same night.
 * Given the root of the binary tree, return the maximum amount of money the thief can rob without alerting the police.
 * 
 * Key Insights:
 * 1. Cannot rob two directly connected houses (parent and child)
 * 2. For each node, we have two choices: rob or skip
 * 3. If we rob current node, we cannot rob its children
 * 4. If we skip current node, we can choose best from children (rob or skip)
 * 5. Use postorder traversal to build solution bottom-up
 * 
 * Approach (DFS with DP):
 * 1. For each node, return int[2] where:
 *    - result[0] = max money if we ROB current node
 *    - result[1] = max money if we SKIP current node
 * 2. rob = node.val + left[1] + right[1]
 * 3. skip = max(left[0], left[1]) + max(right[0], right[1])
 * 4. Return [rob, skip] for each node
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(h)
 * 
 * Tags: Tree, DFS, Dynamic Programming, Binary Tree
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
     * Approach 1: DFS with DP (Postorder Traversal) - RECOMMENDED
     * O(n) time, O(h) space
     */
    public int rob(TreeNode root) {
        int[] result = dfs(root);
        return Math.max(result[0], result[1]);
    }
    
    private int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        
        // Postorder traversal: left -> right -> root
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);
        
        // If we rob current node, we cannot rob its children
        int rob = node.val + left[1] + right[1];
        
        // If we skip current node, we can choose to rob or skip children
        int skip = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        
        return new int[]{rob, skip};
    }
    
    /**
     * Approach 2: DFS with Memoization (Alternative DP)
     * O(n) time, O(n) space
     */
    private Map<TreeNode, Integer> robMemo = new HashMap<>();
    private Map<TreeNode, Integer> skipMemo = new HashMap<>();
    
    public int robMemoization(TreeNode root) {
        return Math.max(robHelper(root, true), robHelper(root, false));
    }
    
    private int robHelper(TreeNode node, boolean canRob) {
        if (node == null) {
            return 0;
        }
        
        if (canRob && robMemo.containsKey(node)) {
            return robMemo.get(node);
        }
        if (!canRob && skipMemo.containsKey(node)) {
            return skipMemo.get(node);
        }
        
        int result;
        if (canRob) {
            // Option 1: Rob this house, skip children
            int rob = node.val + robHelper(node.left, false) + robHelper(node.right, false);
            // Option 2: Skip this house, can rob children
            int skip = robHelper(node.left, true) + robHelper(node.right, true);
            result = Math.max(rob, skip);
            robMemo.put(node, result);
        } else {
            // Cannot rob this house, can rob children
            result = robHelper(node.left, true) + robHelper(node.right, true);
            skipMemo.put(node, result);
        }
        
        return result;
    }
    
    /**
     * Approach 3: BFS Level Order (Alternative approach)
     * O(n) time, O(n) space
     * Treats problem similar to House Robber I on each level
     */
    public int robBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        List<Integer> levelSums = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        // Calculate sum for each level
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int levelSum = 0;
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                levelSum += current.val;
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            levelSums.add(levelSum);
        }
        
        // Now solve as House Robber I on level sums
        return robHouse1(levelSums);
    }
    
    private int robHouse1(List<Integer> nums) {
        if (nums.size() == 0) return 0;
        if (nums.size() == 1) return nums.get(0);
        
        int prev2 = 0;
        int prev1 = nums.get(0);
        
        for (int i = 1; i < nums.size(); i++) {
            int current = Math.max(prev1, prev2 + nums.get(i));
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    /**
     * Approach 4: DFS with Global Variables (Educational)
     * O(n) time, O(h) space
     */
    public int robGlobalVars(TreeNode root) {
        int[] result = robSub(root);
        return Math.max(result[0], result[1]);
    }
    
    private int[] robSub(TreeNode node) {
        // result[0] = including node, result[1] = excluding node
        int[] result = new int[2];
        if (node == null) {
            return result;
        }
        
        int[] left = robSub(node.left);
        int[] right = robSub(node.right);
        
        // Include node: node value + excluded left + excluded right
        result[0] = node.val + left[1] + right[1];
        
        // Exclude node: take maximum from left and right (can include or exclude children)
        result[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        
        return result;
    }
    
    /**
     * Approach 5: Brute Force with Recursion (Time Limit Exceeded for large trees)
     * O(2^n) time, O(h) space - Not recommended
     */
    public int robBruteForce(TreeNode root) {
        return Math.max(robInclude(root), robExclude(root));
    }
    
    private int robInclude(TreeNode node) {
        if (node == null) return 0;
        
        // If we include this node, we cannot include its children
        return node.val + robExclude(node.left) + robExclude(node.right);
    }
    
    private int robExclude(TreeNode node) {
        if (node == null) return 0;
        
        // If we exclude this node, we can choose to include or exclude children
        return robBruteForce(node.left) + robBruteForce(node.right);
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
        
        System.out.println("Testing House Robber III:");
        System.out.println("=========================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        Integer[] values1 = {3, 2, 3, null, 3, null, 1};
        TreeNode root1 = solution.buildTree(values1);
        
        long startTime = System.nanoTime();
        int result1a = solution.rob(root1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.robMemoization(root1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.robBFS(root1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.robGlobalVars(root1);
        long time1d = System.nanoTime() - startTime;
        
        int expected1 = 7;
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1; // Note: BFS approach may not always work
        boolean test1d = result1d == expected1;
        
        System.out.println("DFS DP: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Memoization: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("BFS Level: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Global Vars: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        System.out.println("\nTree visualization for Test 1:");
        solution.printTree(root1);
        System.out.println("Optimal robbery: 3 (root) + 3 (left-right) + 1 (right-right) = 7");
        System.out.println("Cannot rob both 3 (root) and 2 (left) or 3 (root) and 3 (right)");
        
        // Test case 2: Example 2
        System.out.println("\nTest 2: Example 2");
        Integer[] values2 = {3, 4, 5, 1, 3, null, 1};
        TreeNode root2 = solution.buildTree(values2);
        int result2 = solution.rob(root2);
        int expected2 = 9;
        System.out.println("Example 2: " + result2 + " - " + 
                         (result2 == expected2 ? "PASSED" : "FAILED"));
        
        System.out.println("\nTree visualization for Test 2:");
        solution.printTree(root2);
        System.out.println("Optimal robbery: 4 (left) + 5 (right) = 9");
        System.out.println("Cannot rob 3 (root) with either 4 (left) or 5 (right)");
        
        // Test case 3: Single node
        System.out.println("\nTest 3: Single node");
        TreeNode root3 = new TreeNode(5);
        int result3 = solution.rob(root3);
        System.out.println("Single node: " + result3 + " - " + 
                         (result3 == 5 ? "PASSED" : "FAILED"));
        
        // Test case 4: Two levels - rob root
        System.out.println("\nTest 4: Two levels - rob root");
        TreeNode root4 = new TreeNode(3);
        root4.left = new TreeNode(2);
        root4.right = new TreeNode(2);
        int result4 = solution.rob(root4);
        System.out.println("Two levels rob root: " + result4 + " - " + 
                         (result4 == 3 ? "PASSED" : "FAILED"));
        
        // Test case 5: Two levels - rob children
        System.out.println("\nTest 5: Two levels - rob children");
        TreeNode root5 = new TreeNode(1);
        root5.left = new TreeNode(5);
        root5.right = new TreeNode(5);
        int result5 = solution.rob(root5);
        System.out.println("Two levels rob children: " + result5 + " - " + 
                         (result5 == 10 ? "PASSED" : "FAILED"));
        
        // Test case 6: Three levels complex
        System.out.println("\nTest 6: Three levels complex");
        TreeNode root6 = new TreeNode(3);
        root6.left = new TreeNode(4);
        root6.right = new TreeNode(5);
        root6.left.left = new TreeNode(1);
        root6.left.right = new TreeNode(3);
        root6.right.right = new TreeNode(1);
        int result6 = solution.rob(root6);
        int expected6 = 9; // 4 + 5 = 9
        System.out.println("Three levels complex: " + result6 + " - " + 
                         (result6 == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: All nodes can be robbed (alternate levels)
        System.out.println("\nTest 7: Alternate levels robbery");
        TreeNode root7 = new TreeNode(1);
        root7.left = new TreeNode(2);
        root7.right = new TreeNode(3);
        root7.left.left = new TreeNode(4);
        root7.left.right = new TreeNode(5);
        root7.right.left = new TreeNode(6);
        root7.right.right = new TreeNode(7);
        int result7 = solution.rob(root7);
        // Best: 1 (root) + 4 + 5 + 6 + 7 = 23
        // Or: 2 + 3 = 5 (worse)
        // Or: 4 + 5 + 6 + 7 = 22 (worse than 23)
        System.out.println("Alternate levels: " + result7 + " - " + 
                         (result7 == 23 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  DFS DP: " + time1a + " ns");
        System.out.println("  Memoization: " + time1b + " ns");
        System.out.println("  BFS Level: " + time1c + " ns");
        System.out.println("  Global Vars: " + time1d + " ns");
        
        // Test brute force on small tree
        System.out.println("\nTest 8: Brute force (small tree)");
        TreeNode root8 = new TreeNode(3);
        root8.left = new TreeNode(2);
        root8.right = new TreeNode(3);
        int result8 = solution.robBruteForce(root8);
        System.out.println("Brute force small: " + result8 + " - " + 
                         (result8 == 3 ? "PASSED" : "FAILED"));
        
        // Algorithm explanation with visualization
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION WITH EXAMPLE:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nTree: [3,2,3,null,3,null,1]");
        System.out.println("    3");
        System.out.println("   / \\");
        System.out.println("  2   3");
        System.out.println("   \\   \\");
        System.out.println("    3   1");
        
        System.out.println("\nDFS DP Approach:");
        System.out.println("For each node, return [rob, skip]");
        System.out.println("\nLeaf nodes:");
        System.out.println("Node 3 (left-right): rob=3, skip=0 → [3, 0]");
        System.out.println("Node 1 (right-right): rob=1, skip=0 → [1, 0]");
        
        System.out.println("\nLevel 2:");
        System.out.println("Node 2: rob = 2 + skip(left) + skip(right) = 2 + 0 + 3 = 5");
        System.out.println("         skip = max(left) + max(right) = max(0,0) + max(3,0) = 3");
        System.out.println("         → [5, 3]");
        
        System.out.println("Node 3 (right): rob = 3 + skip(left) + skip(right) = 3 + 0 + 0 = 3");
        System.out.println("               skip = max(left) + max(right) = max(0,0) + max(1,0) = 1");
        System.out.println("               → [3, 1]");
        
        System.out.println("\nRoot:");
        System.out.println("Node 3: rob = 3 + skip(left) + skip(right) = 3 + 3 + 1 = 7");
        System.out.println("        skip = max(left) + max(right) = max(5,3) + max(3,1) = 5 + 3 = 8");
        System.out.println("        → [7, 8]");
        
        System.out.println("\nResult: max(7, 8) = 7");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. DFS with DP (RECOMMENDED):");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(h) - Recursion stack height");
        System.out.println("   How it works:");
        System.out.println("     - Postorder traversal (left → right → root)");
        System.out.println("     - Each node returns [rob, skip] values");
        System.out.println("     - rob = node.val + left.skip + right.skip");
        System.out.println("     - skip = max(left.rob, left.skip) + max(right.rob, right.skip)");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Clean and intuitive implementation");
        System.out.println("     - No extra data structures needed");
        System.out.println("   Cons:");
        System.out.println("     - Recursion stack for deep trees");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Memoization Approach:");
        System.out.println("   Time: O(n) - Each node computed once");
        System.out.println("   Space: O(n) - HashMap storage");
        System.out.println("   How it works:");
        System.out.println("     - Stores computed results in HashMaps");
        System.out.println("     - Separate maps for rob and skip cases");
        System.out.println("     - Avoids recomputation of subproblems");
        System.out.println("   Pros:");
        System.out.println("     - Explicit memoization");
        System.out.println("     - Easy to understand caching");
        System.out.println("   Cons:");
        System.out.println("     - Extra O(n) space for HashMaps");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: When explicit caching is preferred");
        
        System.out.println("\n3. BFS Level Order:");
        System.out.println("   Time: O(n) - Two passes through tree");
        System.out.println("   Space: O(n) - Queue and list storage");
        System.out.println("   How it works:");
        System.out.println("     - Treats problem as House Robber I on levels");
        System.out.println("     - Sums values at each level");
        System.out.println("     - Solves using standard House Robber I algorithm");
        System.out.println("   Pros:");
        System.out.println("     - Leverages known House Robber I solution");
        System.out.println("     - Intuitive level-based approach");
        System.out.println("   Cons:");
        System.out.println("     - DOESN'T ALWAYS WORK CORRECTLY");
        System.out.println("     - Adjacent levels may not be directly connected");
        System.out.println("     - Misses optimal solutions that skip multiple levels");
        System.out.println("   Best for: Educational purposes (to understand limitations)");
        
        System.out.println("\n4. Brute Force:");
        System.out.println("   Time: O(2^n) - Exponential time");
        System.out.println("   Space: O(h) - Recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Tries all possible combinations");
        System.out.println("     - Recursively computes include/exclude cases");
        System.out.println("   Pros:");
        System.out.println("     - Simple to understand");
        System.out.println("     - Guaranteed to find optimal solution");
        System.out.println("   Cons:");
        System.out.println("     - Extremely slow for large trees");
        System.out.println("     - Time limit exceeded for n > 20-30");
        System.out.println("   Best for: Small trees, understanding the problem");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DYNAMIC PROGRAMMING ON TREES PATTERN:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nGeneral Pattern for Tree DP Problems:");
        System.out.println("1. Use postorder traversal (process children before parent)");
        System.out.println("2. Define state for each node (what information to return)");
        System.out.println("3. Combine children's states to compute parent's state");
        System.out.println("4. Handle base cases (null nodes)");
        System.out.println("5. Return the final result from root");
        
        System.out.println("\nCommon Tree DP Problems:");
        System.out.println("- House Robber III (this problem)");
        System.out.println("- Binary Tree Maximum Path Sum");
        System.out.println("- Longest Path in Tree");
        System.out.println("- Tree Diameter");
        System.out.println("- Tree Coloring Problems");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with DFS DP Approach:");
        System.out.println("   - Most interviewers expect this solution");
        System.out.println("   - Explain the O(n) time and O(h) space complexity");
        System.out.println("   - Clearly define what [rob, skip] represents");
        
        System.out.println("\n2. Explain the Recursive Relation:");
        System.out.println("   - rob = node.val + left.skip + right.skip");
        System.out.println("   - skip = max(left.rob, left.skip) + max(right.rob, right.skip)");
        System.out.println("   - Why this works: optimal substructure property");
        
        System.out.println("\n3. Walk Through an Example:");
        System.out.println("   - Use a small tree to demonstrate");
        System.out.println("   - Show how values propagate from leaves to root");
        System.out.println("   - Verify the result makes sense");
        
        System.out.println("\n4. Discuss Edge Cases:");
        System.out.println("   - Single node tree");
        System.out.println("   - Empty tree (though constraints say n >= 1)");
        System.out.println("   - Skewed trees");
        System.out.println("   - Trees where robbing alternate levels is optimal");
        
        System.out.println("\n5. Mention Alternatives:");
        System.out.println("   - Memoization approach");
        System.out.println("   - Why BFS level approach doesn't always work");
        System.out.println("   - Brute force and its limitations");
        
        System.out.println("\nAll tests completed!");
    }
}
