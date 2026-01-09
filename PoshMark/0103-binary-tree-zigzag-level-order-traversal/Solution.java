
# Solution.java

```java
import java.util.*;

/**
 * 103. Binary Tree Zigzag Level Order Traversal
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given binary tree root, return zigzag level order traversal.
 * Level 0: left→right, Level 1: right→left, alternating.
 * 
 * Key Insights:
 * 1. Modified BFS with level tracking
 * 2. Reverse level result for odd levels
 * 3. Alternative: two stacks for natural zigzag
 * 4. Can use deque for efficient reversal
 * 
 * Approach 1: BFS with Level Reversal (RECOMMENDED)
 * O(n) time, O(n) space
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
     * Approach 1: BFS with Level Tracking and Reversal (RECOMMENDED)
     * Time: O(n), Space: O(n)
     * Standard BFS but reverse list for odd levels
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true; // Direction flag
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                
                // Add children to queue for next level
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            // Reverse level if direction is right-to-left
            if (!leftToRight) {
                Collections.reverse(level);
            }
            
            result.add(level);
            leftToRight = !leftToRight; // Toggle direction
        }
        
        return result;
    }
    
    /**
     * Approach 2: Two Stacks
     * Time: O(n), Space: O(n)
     * More natural for zigzag pattern
     */
    public List<List<Integer>> zigzagLevelOrderTwoStacks(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> currentLevel = new Stack<>();
        Stack<TreeNode> nextLevel = new Stack<>();
        currentLevel.push(root);
        boolean leftToRight = true;
        
        while (!currentLevel.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int levelSize = currentLevel.size();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = currentLevel.pop();
                level.add(node.val);
                
                // Add children based on current direction
                if (leftToRight) {
                    // Left to right: push left then right to next level
                    if (node.left != null) nextLevel.push(node.left);
                    if (node.right != null) nextLevel.push(node.right);
                } else {
                    // Right to left: push right then left to next level
                    if (node.right != null) nextLevel.push(node.right);
                    if (node.left != null) nextLevel.push(node.left);
                }
            }
            
            result.add(level);
            
            // Swap stacks and toggle direction
            Stack<TreeNode> temp = currentLevel;
            currentLevel = nextLevel;
            nextLevel = temp;
            leftToRight = !leftToRight;
        }
        
        return result;
    }
    
    /**
     * Approach 3: BFS with Deque
     * Time: O(n), Space: O(n)
     * Uses deque for efficient reversal
     */
    public List<List<Integer>> zigzagLevelOrderDeque(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        boolean leftToRight = true;
        
        while (!deque.isEmpty()) {
            int levelSize = deque.size();
            List<Integer> level = new ArrayList<>();
            
            if (leftToRight) {
                // Left to right: process from front, add children to back
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = deque.pollFirst();
                    level.add(node.val);
                    
                    // Add children in order (left then right)
                    if (node.left != null) deque.offerLast(node.left);
                    if (node.right != null) deque.offerLast(node.right);
                }
            } else {
                // Right to left: process from back, add children to front
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = deque.pollLast();
                    level.add(node.val);
                    
                    // Add children in reverse order (right then left)
                    if (node.right != null) deque.offerFirst(node.right);
                    if (node.left != null) deque.offerFirst(node.left);
                }
            }
            
            result.add(level);
            leftToRight = !leftToRight;
        }
        
        return result;
    }
    
    /**
     * Approach 4: DFS with Level Tracking
     * Time: O(n), Space: O(n) for recursion stack
     * Pre-order traversal with level parameter
     */
    public List<List<Integer>> zigzagLevelOrderDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }
    
    private void dfs(TreeNode node, int level, List<List<Integer>> result) {
        if (node == null) return;
        
        // Create new level list if needed
        if (level >= result.size()) {
            result.add(new LinkedList<>());
        }
        
        List<Integer> levelList = result.get(level);
        
        // Add node value based on level direction
        if (level % 2 == 0) {
            // Even level: left to right (add to end)
            levelList.add(node.val);
        } else {
            // Odd level: right to left (add to beginning)
            levelList.add(0, node.val);
        }
        
        // Recursively process children
        dfs(node.left, level + 1, result);
        dfs(node.right, level + 1, result);
    }
    
    /**
     * Approach 5: BFS with Index Calculation
     * Time: O(n), Space: O(n)
     * Add elements to appropriate position in level list
     */
    public List<List<Integer>> zigzagLevelOrderIndex(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            Integer[] levelArray = new Integer[levelSize];
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Calculate index based on direction
                int index = leftToRight ? i : levelSize - 1 - i;
                levelArray[index] = node.val;
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(Arrays.asList(levelArray));
            leftToRight = !leftToRight;
        }
        
        return result;
    }
    
    /**
     * Approach 6: BFS with LinkedList for Level
     * Time: O(n), Space: O(n)
     * Uses LinkedList for efficient addition at both ends
     */
    public List<List<Integer>> zigzagLevelOrderLinkedList(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            LinkedList<Integer> level = new LinkedList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if (leftToRight) {
                    level.addLast(node.val);
                } else {
                    level.addFirst(node.val);
                }
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(level);
            leftToRight = !leftToRight;
        }
        
        return result;
    }
    
    /**
     * Helper: Create a binary tree from array (LeetCode style)
     */
    public TreeNode createTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (i < values.length) {
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
     * Helper: Print tree in level order
     */
    public void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        System.out.println("Tree structure:");
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    System.out.print("null ");
                } else {
                    System.out.print(node.val + " ");
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Helper: Visualize the zigzag traversal
     */
    public void visualizeZigzag(TreeNode root) {
        System.out.println("\nZigzag Level Order Traversal Visualization:");
        
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        // Print tree structure
        printTree(root);
        
        // Perform zigzag traversal
        List<List<Integer>> result = zigzagLevelOrder(root);
        
        System.out.println("\nZigzag Level Order Traversal:");
        for (int level = 0; level < result.size(); level++) {
            List<Integer> levelValues = result.get(level);
            String direction = (level % 2 == 0) ? "left→right" : "right←left";
            System.out.printf("Level %d (%s): %s%n", level, direction, levelValues);
        }
        
        // Show step-by-step process
        System.out.println("\nStep-by-step BFS with reversal:");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;
        int level = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            List<TreeNode> nextLevelNodes = new ArrayList<>();
            
            System.out.printf("\nLevel %d processing (%s):%n", level, 
                leftToRight ? "left→right" : "right←left");
            System.out.println("Queue size at start: " + levelSize);
            
            // Process current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                System.out.printf("  Processing node %d%n", node.val);
                
                // Collect children for next level
                if (node.left != null) nextLevelNodes.add(node.left);
                if (node.right != null) nextLevelNodes.add(node.right);
            }
            
            // Print current level before/after reversal
            System.out.printf("  Level values before direction: %s%n", currentLevel);
            if (!leftToRight) {
                Collections.reverse(currentLevel);
                System.out.printf("  Level values after reversal: %s%n", currentLevel);
            }
            
            // Add children to queue for next level
            for (TreeNode node : nextLevelNodes) {
                queue.offer(node);
            }
            System.out.printf("  Added %d nodes to queue for next level%n", nextLevelNodes.size());
            
            leftToRight = !leftToRight;
            level++;
        }
        
        // Show final result
        System.out.println("\nFinal zigzag traversal:");
        for (int i = 0; i < result.size(); i++) {
            System.out.printf("Level %d: %s%n", i, result.get(i));
        }
    }
    
    /**
     * Helper: Explain the algorithm and patterns
     */
    public void explainAlgorithm() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ZIGZAG TRAVERSAL ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nProblem Definition:");
        System.out.println("Traverse binary tree level by level, alternating direction:");
        System.out.println("Level 0: left to right (normal)");
        System.out.println("Level 1: right to left (reversed)");
        System.out.println("Level 2: left to right (normal)");
        System.out.println("Level 3: right to left (reversed)");
        System.out.println("... and so on");
        
        System.out.println("\nKey Observations:");
        System.out.println("1. It's level-order traversal (BFS) with alternating direction");
        System.out.println("2. Even levels (0, 2, 4...): left→right");
        System.out.println("3. Odd levels (1, 3, 5...): right→left");
        System.out.println("4. Need to track which level we're processing");
        
        System.out.println("\nApproach 1: BFS with Level Reversal (Recommended):");
        System.out.println("Steps:");
        System.out.println("1. Standard BFS with queue");
        System.out.println("2. For each level:");
        System.out.println("   a. Process all nodes at current level");
        System.out.println("   b. Store values in list");
        System.out.println("   c. If level is odd, reverse the list");
        System.out.println("   d. Add list to result");
        System.out.println("3. Toggle direction flag for next level");
        
        System.out.println("\nApproach 2: Two Stacks:");
        System.out.println("Steps:");
        System.out.println("1. Use two stacks: currentLevel and nextLevel");
        System.out.println("2. For left→right level:");
        System.out.println("   a. Pop from currentLevel stack");
        System.out.println("   b. Push left then right to nextLevel");
        System.out.println("3. For right←left level:");
        System.out.println("   a. Pop from currentLevel stack");
        System.out.println("   b. Push right then left to nextLevel");
        System.out.println("4. Swap stacks after each level");
        
        System.out.println("\nExample: root = [3,9,20,null,null,15,7]");
        System.out.println("Tree:");
        System.out.println("      3");
        System.out.println("     / \\");
        System.out.println("    9  20");
        System.out.println("       / \\");
        System.out.println("      15  7");
        
        System.out.println("\nTraversal process:");
        System.out.println("Level 0 (left→right): Process node 3 → [3]");
        System.out.println("Level 1 (right←left): Process nodes 20,9 → reverse → [20,9]");
        System.out.println("Level 2 (left→right): Process nodes 15,7 → [15,7]");
        System.out.println("Result: [[3],[20,9],[15,7]]");
        
        System.out.println("\nTime Complexity: O(n)");
        System.out.println("- Each node visited exactly once");
        System.out.println("- Reversal operation is O(k) for level of size k");
        System.out.println("- Total reversal cost: O(n) across all levels");
        
        System.out.println("\nSpace Complexity: O(n)");
        System.out.println("- Queue can hold up to n/2 nodes (last level)");
        System.out.println("- Output stores all n nodes");
        System.out.println("- Total: O(n)");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES TESTING:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. Empty tree:");
        TreeNode root1 = null;
        List<List<Integer>> result1 = solution.zigzagLevelOrder(root1);
        System.out.println("   Result: " + result1 + " (should be [])");
        
        System.out.println("\n2. Single node tree:");
        TreeNode root2 = new TreeNode(1);
        List<List<Integer>> result2 = solution.zigzagLevelOrder(root2);
        System.out.println("   Result: " + result2 + " (should be [[1]])");
        
        System.out.println("\n3. Left-skewed tree:");
        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(2);
        root3.left.left = new TreeNode(3);
        root3.left.left.left = new TreeNode(4);
        List<List<Integer>> result3 = solution.zigzagLevelOrder(root3);
        System.out.println("   Result: " + result3);
        System.out.println("   Expected: [[1],[2],[3],[4]]");
        
        System.out.println("\n4. Right-skewed tree:");
        TreeNode root4 = new TreeNode(1);
        root4.right = new TreeNode(2);
        root4.right.right = new TreeNode(3);
        root4.right.right.right = new TreeNode(4);
        List<List<Integer>> result4 = solution.zigzagLevelOrder(root4);
        System.out.println("   Result: " + result4);
        System.out.println("   Expected: [[1],[2],[3],[4]]");
        
        System.out.println("\n5. Perfect binary tree (height 2):");
        TreeNode root5 = new TreeNode(1);
        root5.left = new TreeNode(2);
        root5.right = new TreeNode(3);
        root5.left.left = new TreeNode(4);
        root5.left.right = new TreeNode(5);
        root5.right.left = new TreeNode(6);
        root5.right.right = new TreeNode(7);
        List<List<Integer>> result5 = solution.zigzagLevelOrder(root5);
        System.out.println("   Result: " + result5);
        System.out.println("   Expected: [[1],[3,2],[4,5,6,7]]");
        
        System.out.println("\n6. Example from problem:");
        Integer[] values6 = {3,9,20,null,null,15,7};
        TreeNode root6 = solution.createTree(values6);
        List<List<Integer>> result6 = solution.zigzagLevelOrder(root6);
        System.out.println("   Result: " + result6);
        System.out.println("   Expected: [[3],[20,9],[15,7]]");
        
        System.out.println("\n7. Tree with null values in middle:");
        TreeNode root7 = new TreeNode(1);
        root7.left = new TreeNode(2);
        root7.right = new TreeNode(3);
        root7.left.right = new TreeNode(4);
        root7.right.left = new TreeNode(5);
        List<List<Integer>> result7 = solution.zigzagLevelOrder(root7);
        System.out.println("   Result: " + result7);
        System.out.println("   Expected: [[1],[3,2],[4,5]]");
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(TreeNode root, String testCase) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES - " + testCase + ":");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\nTest Case: " + testCase);
        if (root != null) {
            System.out.println("Tree:");
            solution.printTree(root);
        }
        
        long startTime, endTime;
        List<List<Integer>> result1, result2, result3, result4, result5, result6;
        
        // Approach 1: BFS with Level Reversal
        startTime = System.nanoTime();
        result1 = solution.zigzagLevelOrder(root);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Two Stacks
        startTime = System.nanoTime();
        result2 = solution.zigzagLevelOrderTwoStacks(root);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Deque
        startTime = System.nanoTime();
        result3 = solution.zigzagLevelOrderDeque(root);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: DFS
        startTime = System.nanoTime();
        result4 = solution.zigzagLevelOrderDFS(root);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: BFS with Index
        startTime = System.nanoTime();
        result5 = solution.zigzagLevelOrderIndex(root);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Approach 6: BFS with LinkedList
        startTime = System.nanoTime();
        result6 = solution.zigzagLevelOrderLinkedList(root);
        endTime = System.nanoTime();
        long time6 = endTime - startTime;
        
        System.out.println("\nResults (all should be identical):");
        boolean allEqual = result1.equals(result2) && result2.equals(result3) &&
                          result3.equals(result4) && result4.equals(result5) &&
                          result5.equals(result6);
        
        System.out.println("All results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        if (!allEqual) {
            System.out.println("\nDifferences found!");
            System.out.println("Approach 1 (BFS+Reversal): " + result1);
            System.out.println("Approach 2 (Two Stacks):   " + result2);
            System.out.println("Approach 3 (Deque):        " + result3);
            System.out.println("Approach 4 (DFS):          " + result4);
            System.out.println("Approach 5 (BFS+Index):    " + result5);
            System.out.println("Approach 6 (BFS+LinkedList): " + result6);
        } else {
            System.out.println("\nZigzag Traversal Result:");
            for (int i = 0; i < result1.size(); i++) {
                String direction = (i % 2 == 0) ? "left→right" : "right←left";
                System.out.printf("Level %d (%s): %s%n", i, direction, result1.get(i));
            }
        }
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("BFS with Reversal:     %-10d (Recommended)%n", time1);
        System.out.printf("Two Stacks:            %-10d (Natural zigzag)%n", time2);
        System.out.printf("Deque:                 %-10d (Efficient reversal)%n", time3);
        System.out.printf("DFS:                   %-10d (Recursive)%n", time4);
        System.out.printf("BFS with Index:        %-10d (Index calculation)%n", time5);
        System.out.printf("BFS with LinkedList:   %-10d (Efficient add)%n", time6);
        
        // Visualize for small trees
        if (countNodes(root) <= 15) {
            System.out.println("\n" + "-".repeat(80));
            solution.visualizeZigzag(root);
        }
    }
    
    private int countNodes(TreeNode root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
    
    /**
     * Helper: Analyze complexity and trade-offs
     */
    public void analyzeComplexity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS AND TRADE-OFFS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Time Complexity:");
        System.out.println("   All approaches: O(n) where n = number of nodes");
        System.out.println("   - Each node visited exactly once");
        System.out.println("   - Additional operations per node are O(1)");
        System.out.println("   - Reversal/insertion operations add constant factor");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   a. BFS with Reversal: O(n)");
        System.out.println("      - Queue: O(w) where w = max width ≈ n/2");
        System.out.println("      - Output: O(n)");
        System.out.println("   b. Two Stacks: O(n)");
        System.out.println("      - Two stacks: O(w) each");
        System.out.println("      - Output: O(n)");
        System.out.println("   c. Deque: O(n)");
        System.out.println("      - Deque: O(w)");
        System.out.println("      - Output: O(n)");
        System.out.println("   d. DFS: O(n)");
        System.out.println("      - Recursion stack: O(h) where h = height");
        System.out.println("      - Output: O(n)");
        
        System.out.println("\n3. Trade-offs:");
        System.out.println("   - BFS with Reversal:");
        System.out.println("     + Simple and intuitive");
        System.out.println("     + Easy to implement");
        System.out.println("     - Reversal adds overhead");
        System.out.println("   - Two Stacks:");
        System.out.println("     + Natural for zigzag pattern");
        System.out.println("     + No reversal needed");
        System.out.println("     - Two data structures to manage");
        System.out.println("   - Deque:");
        System.out.println("     + Efficient add/remove from both ends");
        System.out.println("     + Clean implementation");
        System.out.println("     - Less intuitive for beginners");
        System.out.println("   - DFS:");
        System.out.println("     + Simple recursive solution");
        System.out.println("     + No queue management");
        System.out.println("     - Recursion depth limit for tall trees");
        
        System.out.println("\n4. Which approach to use:");
        System.out.println("   - Interview: BFS with Reversal (most common)");
        System.out.println("   - Production: Any O(n) approach based on context");
        System.out.println("   - Memory constrained: BFS with Index (no reversal)");
        System.out.println("   - Readability: BFS with Reversal");
    }
    
    /**
     * Helper: Show related problems
     */
    public void showRelatedProblems() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 102. Binary Tree Level Order Traversal:");
        System.out.println("   - Standard level-order traversal (BFS)");
        System.out.println("   - Foundation for zigzag traversal");
        
        System.out.println("\n2. 107. Binary Tree Level Order Traversal II:");
        System.out.println("   - Level-order from bottom to top");
        System.out.println("   - Reverse the result of 102");
        
        System.out.println("\n3. 199. Binary Tree Right Side View:");
        System.out.println("   - Only rightmost node at each level");
        System.out.println("   - Also uses level-order traversal");
        
        System.out.println("\n4. 637. Average of Levels in Binary Tree:");
        System.out.println("   - Average value at each level");
        System.out.println("   - Level-order traversal with averaging");
        
        System.out.println("\n5. 515. Find Largest Value in Each Tree Row:");
        System.out.println("   - Maximum value at each level");
        System.out.println("   - Level-order traversal with max tracking");
        
        System.out.println("\n6. 116. Populating Next Right Pointers in Each Node:");
        System.out.println("   - Connect nodes at same level");
        System.out.println("   - Modified level-order traversal");
        
        System.out.println("\n7. 117. Populating Next Right Pointers in Each Node II:");
        System.out.println("   - Same as 116 but for any binary tree");
        
        System.out.println("\nCommon Pattern:");
        System.out.println("All are variations of level-order traversal (BFS)");
        System.out.println("Key techniques:");
        System.out.println("- Queue for BFS");
        System.out.println("- Level size tracking");
        System.out.println("- Processing all nodes at current level");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. File System Navigation:");
        System.out.println("   - Directory tree traversal");
        System.out.println("   - Alternating display patterns");
        System.out.println("   - Tree visualization tools");
        
        System.out.println("\n2. Organizational Charts:");
        System.out.println("   - Company hierarchy display");
        System.out.println("   - Alternating color schemes for levels");
        System.out.println("   - Management reporting");
        
        System.out.println("\n3. Game Development:");
        System.out.println("   - Game tree traversal (chess, checkers)");
        System.out.println("   - AI decision trees");
        System.out.println("   - Pathfinding algorithms");
        
        System.out.println("\n4. Document Processing:");
        System.out.println("   - Table of contents generation");
        System.out.println("   - Document outline traversal");
        System.out.println("   - XML/HTML tree processing");
        
        System.out.println("\n5. Network Routing:");
        System.out.println("   - Network topology traversal");
        System.out.println("   - Routing table organization");
        System.out.println("   - Hierarchical network design");
        
        System.out.println("\n6. Database Systems:");
        System.out.println("   - B-tree traversal");
        System.out.println("   - Index structure navigation");
        System.out.println("   - Query optimization");
        
        System.out.println("\n7. UI/UX Design:");
        System.out.println("   - Tree view components");
        System.out.println("   - Nested menu systems");
        System.out.println("   - Alternating row colors in tree displays");
    }
    
    /**
     * Main method with comprehensive tests
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Binary Tree Zigzag Level Order Traversal:");
        System.out.println("=========================================");
        
        // Explain the algorithm
        solution.explainAlgorithm();
        
        // Test edge cases
        solution.testEdgeCases();
        
        // Example 1 from problem
        System.out.println("\n\nExample 1 from problem:");
        Integer[] values1 = {3,9,20,null,null,15,7};
        TreeNode root1 = solution.createTree(values1);
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(3),
            Arrays.asList(20,9),
            Arrays.asList(15,7)
        );
        
        System.out.println("\nTree:");
        solution.printTree(root1);
        
        List<List<Integer>> result1 = solution.zigzagLevelOrder(root1);
        System.out.println("\nExpected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + result1.equals(expected1));
        
        // Example 2 from problem
        System.out.println("\n\nExample 2 from problem:");
        TreeNode root2 = new TreeNode(1);
        List<List<Integer>> expected2 = Arrays.asList(Arrays.asList(1));
        
        List<List<Integer>> result2 = solution.zigzagLevelOrder(root2);
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + result2.equals(expected2));
        
        // Example 3 from problem
        System.out.println("\n\nExample 3 from problem:");
        TreeNode root3 = null;
        List<List<Integer>> expected3 = Arrays.asList();
        
        List<List<Integer>> result3 = solution.zigzagLevelOrder(root3);
        System.out.println("Expected: " + expected3
