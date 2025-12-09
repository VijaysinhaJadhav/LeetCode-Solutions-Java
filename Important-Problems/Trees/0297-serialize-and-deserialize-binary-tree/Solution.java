
## Solution.java

```java
/**
 * 297. Serialize and Deserialize Binary Tree
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Design an algorithm to serialize and deserialize a binary tree.
 * 
 * Key Insights:
 * 1. Multiple approaches: Preorder DFS, Level-order BFS, Postorder, Inorder+Preorder
 * 2. Need to represent null nodes to preserve tree structure
 * 3. Preorder DFS with null markers is simple and efficient
 * 4. Level-order BFS is more intuitive and similar to LeetCode format
 * 
 * Approach (Preorder DFS):
 * 1. Serialize: Preorder traversal with "X" for null nodes, comma-separated
 * 2. Deserialize: Split string and rebuild tree recursively using preorder
 * 
 * Time Complexity: O(n) for both operations
 * Space Complexity: O(n) for recursion stack and string storage
 * 
 * Tags: Tree, DFS, BFS, Design, String
 */

import java.util.*;
import java.util.LinkedList;

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class Codec {

    /**
     * Approach 1: Preorder DFS with Null Markers - RECOMMENDED
     * O(n) time, O(n) space - Clean recursive solution
     */
    
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("X,");
            return;
        }
        
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<String> nodes = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserializeHelper(nodes);
    }
    
    private TreeNode deserializeHelper(Queue<String> nodes) {
        String val = nodes.poll();
        if (val.equals("X")) {
            return null;
        }
        
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = deserializeHelper(nodes);
        node.right = deserializeHelper(nodes);
        return node;
    }
    
    /**
     * Approach 2: Level-order BFS (LeetCode format)
     * O(n) time, O(n) space - Intuitive level-by-level approach
     */
    public String serializeBFS(TreeNode root) {
        if (root == null) return "";
        
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("X,");
            } else {
                sb.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        
        return sb.toString();
    }
    
    public TreeNode deserializeBFS(String data) {
        if (data.isEmpty()) return null;
        
        String[] values = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        queue.offer(root);
        
        for (int i = 1; i < values.length; i++) {
            TreeNode parent = queue.poll();
            
            // Left child
            if (!values[i].equals("X")) {
                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                parent.left = left;
                queue.offer(left);
            }
            
            // Right child
            i++;
            if (i < values.length && !values[i].equals("X")) {
                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                parent.right = right;
                queue.offer(right);
            }
        }
        
        return root;
    }
    
    /**
     * Approach 3: Postorder DFS
     * O(n) time, O(n) space - Alternative DFS approach
     */
    public String serializePostorder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializePostorderHelper(root, sb);
        return sb.toString();
    }
    
    private void serializePostorderHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("X,");
            return;
        }
        
        serializePostorderHelper(node.left, sb);
        serializePostorderHelper(node.right, sb);
        sb.append(node.val).append(",");
    }
    
    public TreeNode deserializePostorder(String data) {
        Stack<String> stack = new Stack<>();
        stack.addAll(Arrays.asList(data.split(",")));
        return deserializePostorderHelper(stack);
    }
    
    private TreeNode deserializePostorderHelper(Stack<String> stack) {
        String val = stack.pop();
        if (val.equals("X")) {
            return null;
        }
        
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.right = deserializePostorderHelper(stack);
        node.left = deserializePostorderHelper(stack);
        return node;
    }
    
    /**
     * Approach 4: JSON-like format (Human readable)
     * O(n) time, O(n) space - Easy to debug
     */
    public String serializeJSON(TreeNode root) {
        if (root == null) return "null";
        
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"val\":").append(root.val).append(",");
        sb.append("\"left\":").append(serializeJSON(root.left)).append(",");
        sb.append("\"right\":").append(serializeJSON(root.right));
        sb.append("}");
        return sb.toString();
    }
    
    public TreeNode deserializeJSON(String data) {
        return deserializeJSONHelper(data, new int[1]);
    }
    
    private TreeNode deserializeJSONHelper(String data, int[] index) {
        if (data.startsWith("null", index[0])) {
            index[0] += 4;
            return null;
        }
        
        // Skip "{"
        index[0]++;
        
        // Skip "\"val\":"
        index[0] += 6;
        int valStart = index[0];
        while (Character.isDigit(data.charAt(index[0])) || data.charAt(index[0]) == '-') {
            index[0]++;
        }
        int val = Integer.parseInt(data.substring(valStart, index[0]));
        
        // Skip ",\"left\":"
        index[0] += 9;
        TreeNode left = deserializeJSONHelper(data, index);
        
        // Skip ",\"right\":"
        index[0] += 10;
        TreeNode right = deserializeJSONHelper(data, index);
        
        // Skip "}"
        index[0]++;
        
        TreeNode node = new TreeNode(val);
        node.left = left;
        node.right = right;
        return node;
    }
    
    /**
     * Approach 5: Binary format (Compact)
     * O(n) time, O(n) space - More efficient for storage
     */
    public String serializeBinary(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeBinaryHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeBinaryHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append('0');
            return;
        }
        
        sb.append('1');
        // Convert integer to 4-character hex representation
        String hex = String.format("%04x", node.val & 0xFFFF);
        sb.append(hex);
        serializeBinaryHelper(node.left, sb);
        serializeBinaryHelper(node.right, sb);
    }
    
    public TreeNode deserializeBinary(String data) {
        int[] index = new int[1];
        return deserializeBinaryHelper(data, index);
    }
    
    private TreeNode deserializeBinaryHelper(String data, int[] index) {
        char type = data.charAt(index[0]++);
        if (type == '0') {
            return null;
        }
        
        String hex = data.substring(index[0], index[0] + 4);
        index[0] += 4;
        int val = Integer.parseInt(hex, 16);
        // Handle negative numbers (two's complement)
        if (val > 32767) val -= 65536;
        
        TreeNode node = new TreeNode(val);
        node.left = deserializeBinaryHelper(data, index);
        node.right = deserializeBinaryHelper(data, index);
        return node;
    }
}

/**
 * Helper class for testing and visualization
 */
class TreeUtils {
    /**
     * Create a binary tree from array (LeetCode format)
     */
    public static TreeNode createTree(Integer[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;
        
        while (!queue.isEmpty() && i < arr.length) {
            TreeNode node = queue.poll();
            
            // Left child
            if (i < arr.length && arr[i] != null) {
                node.left = new TreeNode(arr[i]);
                queue.offer(node.left);
            }
            i++;
            
            // Right child
            if (i < arr.length && arr[i] != null) {
                node.right = new TreeNode(arr[i]);
                queue.offer(node.right);
            }
            i++;
        }
        
        return root;
    }
    
    /**
     * Convert tree to array (LeetCode format)
     */
    public static Integer[] treeToArray(TreeNode root) {
        if (root == null) return new Integer[0];
        
        List<Integer> result = new ArrayList<>();
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
            i--;
        }
        
        return result.subList(0, i + 1).toArray(new Integer[0]);
    }
    
    /**
     * Print tree in visual format
     */
    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("null");
            return;
        }
        
        List<List<String>> lines = new ArrayList<>();
        List<TreeNode> level = new ArrayList<>();
        List<TreeNode> next = new ArrayList<>();
        
        level.add(root);
        int nn = 1;
        int widest = 0;
        
        while (nn != 0) {
            List<String> line = new ArrayList<>();
            nn = 0;
            
            for (TreeNode node : level) {
                if (node == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = String.valueOf(node.val);
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();
                    
                    next.add(node.left);
                    next.add(node.right);
                    
                    if (node.left != null) nn++;
                    if (node.right != null) nn++;
                }
            }
            
            if (widest % 2 == 1) widest++;
            lines.add(line);
            List<TreeNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }
        
        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;
            
            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);
                    
                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }
            
            // print line of numbers
            for (int j = 0; j < line.size(); j++) {
                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);
                
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            
            perpiece /= 2;
        }
    }
    
    /**
     * Check if two trees are identical
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}

/**
 * Test class with comprehensive test cases
 */
public class Solution {
    public static void main(String[] args) {
        Codec codec = new Codec();
        
        System.out.println("Testing Serialize and Deserialize Binary Tree:");
        System.out.println("==============================================");
        
        // Test all serialization approaches
        testApproaches(codec);
        
        // Performance comparison
        testPerformance(codec);
        
        // Edge cases
        testEdgeCases(codec);
        
        System.out.println("\nAll tests completed!");
    }
    
    private static void testApproaches(Codec codec) {
        System.out.println("\n1. Testing Different Serialization Approaches:");
        System.out.println("=".repeat(50));
        
        // Test tree 1: Basic tree
        TreeNode tree1 = TreeUtils.createTree(new Integer[]{1, 2, 3, null, null, 4, 5});
        System.out.println("Test 1: Basic tree [1,2,3,null,null,4,5]");
        testSerializationApproach(codec, tree1, "Preorder DFS");
        
        // Test tree 2: Complete binary tree
        TreeNode tree2 = TreeUtils.createTree(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        System.out.println("\nTest 2: Complete binary tree [1,2,3,4,5,6,7]");
        testSerializationApproach(codec, tree2, "Preorder DFS");
        
        // Test tree 3: Skewed tree
        TreeNode tree3 = TreeUtils.createTree(new Integer[]{1, 2, null, 3, null, 4});
        System.out.println("\nTest 3: Left-skewed tree [1,2,null,3,null,4]");
        testSerializationApproach(codec, tree3, "Preorder DFS");
        
        // Test tree 4: Single node
        TreeNode tree4 = TreeUtils.createTree(new Integer[]{42});
        System.out.println("\nTest 4: Single node [42]");
        testSerializationApproach(codec, tree4, "Preorder DFS");
        
        // Test tree 5: Tree with negative numbers
        TreeNode tree5 = TreeUtils.createTree(new Integer[]{-10, 5, -20, null, null, 15, 25});
        System.out.println("\nTest 5: Tree with negative numbers [-10,5,-20,null,null,15,25]");
        testSerializationApproach(codec, tree5, "Preorder DFS");
    }
    
    private static void testSerializationApproach(Codec codec, TreeNode original, String approach) {
        System.out.println("Original tree:");
        TreeUtils.printTree(original);
        
        // Test Preorder DFS
        String serialized = codec.serialize(original);
        TreeNode deserialized = codec.deserialize(serialized);
        boolean success1 = TreeUtils.isSameTree(original, deserialized);
        System.out.println("Preorder DFS:");
        System.out.println("  Serialized: " + serialized);
        System.out.println("  Success: " + (success1 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test BFS
        String serializedBFS = codec.serializeBFS(original);
        TreeNode deserializedBFS = codec.deserializeBFS(serializedBFS);
        boolean success2 = TreeUtils.isSameTree(original, deserializedBFS);
        System.out.println("Level-order BFS:");
        System.out.println("  Serialized: " + serializedBFS);
        System.out.println("  Success: " + (success2 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test Postorder
        String serializedPost = codec.serializePostorder(original);
        TreeNode deserializedPost = codec.deserializePostorder(serializedPost);
        boolean success3 = TreeUtils.isSameTree(original, deserializedPost);
        System.out.println("Postorder DFS:");
        System.out.println("  Serialized: " + serializedPost);
        System.out.println("  Success: " + (success3 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test JSON
        String serializedJSON = codec.serializeJSON(original);
        TreeNode deserializedJSON = codec.deserializeJSON(serializedJSON);
        boolean success4 = TreeUtils.isSameTree(original, deserializedJSON);
        System.out.println("JSON format:");
        System.out.println("  Serialized: " + serializedJSON);
        System.out.println("  Success: " + (success4 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test Binary
        String serializedBinary = codec.serializeBinary(original);
        TreeNode deserializedBinary = codec.deserializeBinary(serializedBinary);
        boolean success5 = TreeUtils.isSameTree(original, deserializedBinary);
        System.out.println("Binary format:");
        System.out.println("  Serialized: " + serializedBinary);
        System.out.println("  Size: " + serializedBinary.length() + " chars");
        System.out.println("  Success: " + (success5 ? "✓ PASSED" : "✗ FAILED"));
        
        System.out.println("Deserialized tree:");
        TreeUtils.printTree(deserialized);
    }
    
    private static void testPerformance(Codec codec) {
        System.out.println("\n2. Performance Comparison:");
        System.out.println("=".repeat(50));
        
        // Generate a larger tree for performance testing
        TreeNode largeTree = generateLargeTree(1000);
        
        long startTime, endTime;
        
        // Test Preorder DFS
        startTime = System.nanoTime();
        String serialized1 = codec.serialize(largeTree);
        TreeNode deserialized1 = codec.deserialize(serialized1);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Test BFS
        startTime = System.nanoTime();
        String serialized2 = codec.serializeBFS(largeTree);
        TreeNode deserialized2 = codec.deserializeBFS(serialized2);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Test Postorder
        startTime = System.nanoTime();
        String serialized3 = codec.serializePostorder(largeTree);
        TreeNode deserialized3 = codec.deserializePostorder(serialized3);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        System.out.println("Performance for tree with ~1000 nodes:");
        System.out.println("Preorder DFS: " + time1 + " ns");
        System.out.println("  Serialized size: " + serialized1.length() + " chars");
        System.out.println("Level-order BFS: " + time2 + " ns");
        System.out.println("  Serialized size: " + serialized2.length() + " chars");
        System.out.println("Postorder DFS: " + time3 + " ns");
        System.out.println("  Serialized size: " + serialized3.length() + " chars");
        
        // Verify all produce correct results
        boolean allCorrect = TreeUtils.isSameTree(largeTree, deserialized1) &&
                            TreeUtils.isSameTree(largeTree, deserialized2) &&
                            TreeUtils.isSameTree(largeTree, deserialized3);
        System.out.println("All approaches produce correct results: " + (allCorrect ? "✓" : "✗"));
    }
    
    private static void testEdgeCases(Codec codec) {
        System.out.println("\n3. Edge Cases:");
        System.out.println("=".repeat(50));
        
        // Test null tree
        System.out.println("Test 1: Null tree");
        TreeNode nullTree = null;
        String serializedNull = codec.serialize(nullTree);
        TreeNode deserializedNull = codec.deserialize(serializedNull);
        System.out.println("  Serialized: \"" + serializedNull + "\"");
        System.out.println("  Success: " + (deserializedNull == null ? "✓ PASSED" : "✗ FAILED"));
        
        // Test single node
        System.out.println("\nTest 2: Single node with value 0");
        TreeNode singleNode = new TreeNode(0);
        String serializedSingle = codec.serialize(singleNode);
        TreeNode deserializedSingle = codec.deserialize(serializedSingle);
        boolean singleSuccess = deserializedSingle != null && 
                               deserializedSingle.val == 0 &&
                               deserializedSingle.left == null && 
                               deserializedSingle.right == null;
        System.out.println("  Serialized: " + serializedSingle);
        System.out.println("  Success: " + (singleSuccess ? "✓ PASSED" : "✗ FAILED"));
        
        // Test large values
        System.out.println("\nTest 3: Large and negative values");
        TreeNode valueTree = TreeUtils.createTree(new Integer[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0});
        String serializedValues = codec.serialize(valueTree);
        TreeNode deserializedValues = codec.deserialize(serializedValues);
        boolean valueSuccess = deserializedValues != null &&
                              deserializedValues.val == Integer.MAX_VALUE &&
                              deserializedValues.left != null &&
                              deserializedValues.left.val == Integer.MIN_VALUE;
        System.out.println("  Serialized: " + serializedValues);
        System.out.println("  Success: " + (valueSuccess ? "✓ PASSED" : "✗ FAILED"));
        
        // Test very deep tree
        System.out.println("\nTest 4: Very deep tree (stress test)");
        TreeNode deepTree = generateDeepTree(20); // 20 levels deep
        String serializedDeep = codec.serialize(deepTree);
        TreeNode deserializedDeep = codec.deserialize(serializedDeep);
        boolean deepSuccess = TreeUtils.isSameTree(deepTree, deserializedDeep);
        System.out.println("  Serialized length: " + serializedDeep.length() + " chars");
        System.out.println("  Success: " + (deepSuccess ? "✓ PASSED" : "✗ FAILED"));
    }
    
    /**
     * Generate a large balanced tree for performance testing
     */
    private static TreeNode generateLargeTree(int nodeCount) {
        if (nodeCount <= 0) return null;
        
        TreeNode root = new TreeNode(1);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int count = 1;
        
        while (count < nodeCount && !queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (count < nodeCount) {
                node.left = new TreeNode(++count);
                queue.offer(node.left);
            }
            
            if (count < nodeCount) {
                node.right = new TreeNode(++count);
                queue.offer(node.right);
            }
        }
        
        return root;
    }
    
    /**
     * Generate a very deep tree for stress testing
     */
    private static TreeNode generateDeepTree(int depth) {
        if (depth <= 0) return null;
        
        TreeNode root = new TreeNode(1);
        TreeNode current = root;
        
        for (int i = 2; i <= depth; i++) {
            current.left = new TreeNode(i);
            current = current.left;
        }
        
        return root;
    }
}
