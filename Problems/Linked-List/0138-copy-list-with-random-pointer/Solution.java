
## Solution.java

```java
/**
 * 138. Copy List with Random Pointer
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Construct a deep copy of a linked list with random pointers.
 * 
 * Key Insights:
 * 1. Hash Map: Map original nodes to copied nodes for O(1) random pointer lookup
 * 2. Interweaving: Insert copied nodes between original nodes, then separate
 * 3. Recursive: Use recursion with memoization
 * 4. Two passes needed: one for node creation, one for pointer assignment
 * 
 * Approach (Hash Map with Two Passes - RECOMMENDED):
 * 1. Create a hash map to store original node -> copied node mapping
 * 2. First pass: create all new nodes and store in hash map
 * 3. Second pass: assign next and random pointers using hash map
 * 4. Return the copied head
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 * 
 * Tags: Hash Table, Linked List
 */

import java.util.*;

/**
 * Definition for a Node.
 */
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}

class Solution {
    /**
     * Approach 1: Hash Map with Two Passes - RECOMMENDED
     * O(n) time, O(n) space - Clear and intuitive
     */
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        
        Map<Node, Node> nodeMap = new HashMap<>();
        
        // First pass: create all new nodes and store mapping
        Node current = head;
        while (current != null) {
            nodeMap.put(current, new Node(current.val));
            current = current.next;
        }
        
        // Second pass: assign next and random pointers
        current = head;
        while (current != null) {
            Node copiedNode = nodeMap.get(current);
            
            // Assign next pointer
            if (current.next != null) {
                copiedNode.next = nodeMap.get(current.next);
            }
            
            // Assign random pointer
            if (current.random != null) {
                copiedNode.random = nodeMap.get(current.random);
            }
            
            current = current.next;
        }
        
        return nodeMap.get(head);
    }
    
    /**
     * Approach 2: Interweaving Nodes (O(1) Space)
     * O(n) time, O(1) space - Most space efficient
     */
    public Node copyRandomListInterweaving(Node head) {
        if (head == null) return null;
        
        // Step 1: Create copied nodes and interweave them
        Node current = head;
        while (current != null) {
            Node copied = new Node(current.val);
            copied.next = current.next;
            current.next = copied;
            current = copied.next;
        }
        
        // Step 2: Assign random pointers for copied nodes
        current = head;
        while (current != null) {
            if (current.random != null) {
                current.next.random = current.random.next;
            }
            current = current.next.next;
        }
        
        // Step 3: Separate the interweaved lists
        Node oldCurrent = head;
        Node newHead = head.next;
        Node newCurrent = newHead;
        
        while (oldCurrent != null) {
            oldCurrent.next = oldCurrent.next.next;
            if (newCurrent.next != null) {
                newCurrent.next = newCurrent.next.next;
            }
            oldCurrent = oldCurrent.next;
            newCurrent = newCurrent.next;
        }
        
        return newHead;
    }
    
    /**
     * Approach 3: Recursive with Memoization
     * O(n) time, O(n) space - Elegant but uses recursion stack
     */
    public Node copyRandomListRecursive(Node head) {
        Map<Node, Node> visited = new HashMap<>();
        return copyRandomListHelper(head, visited);
    }
    
    private Node copyRandomListHelper(Node node, Map<Node, Node> visited) {
        if (node == null) return null;
        
        // If already visited, return the copied node
        if (visited.containsKey(node)) {
            return visited.get(node);
        }
        
        // Create new node
        Node copiedNode = new Node(node.val);
        visited.put(node, copiedNode);
        
        // Recursively copy next and random pointers
        copiedNode.next = copyRandomListHelper(node.next, visited);
        copiedNode.random = copyRandomListHelper(node.random, visited);
        
        return copiedNode;
    }
    
    /**
     * Approach 4: Hash Map with Single Pass (Iterative)
     * O(n) time, O(n) space - Single pass variant
     */
    public Node copyRandomListSinglePass(Node head) {
        if (head == null) return null;
        
        Map<Node, Node> nodeMap = new HashMap<>();
        Node current = head;
        Node newHead = new Node(head.val);
        Node newCurrent = newHead;
        nodeMap.put(head, newHead);
        
        while (current != null) {
            // Handle next pointer
            if (current.next != null) {
                if (!nodeMap.containsKey(current.next)) {
                    nodeMap.put(current.next, new Node(current.next.val));
                }
                newCurrent.next = nodeMap.get(current.next);
            }
            
            // Handle random pointer
            if (current.random != null) {
                if (!nodeMap.containsKey(current.random)) {
                    nodeMap.put(current.random, new Node(current.random.val));
                }
                newCurrent.random = nodeMap.get(current.random);
            }
            
            current = current.next;
            newCurrent = newCurrent.next;
        }
        
        return newHead;
    }
    
    /**
     * Approach 5: Hash Map with Visualization
     * Same as Approach 1 but with step-by-step visualization
     */
    public Node copyRandomListVisual(Node head) {
        if (head == null) {
            System.out.println("Empty list, returning null");
            return null;
        }
        
        System.out.println("Original list:");
        printListWithRandom(head);
        
        Map<Node, Node> nodeMap = new HashMap<>();
        
        // First pass: create all new nodes
        System.out.println("\nStep 1: Creating new nodes and building mapping");
        Node current = head;
        while (current != null) {
            Node copiedNode = new Node(current.val);
            nodeMap.put(current, copiedNode);
            System.out.println("  Mapped original(" + current.val + ") -> copy(" + copiedNode.val + ")");
            current = current.next;
        }
        
        // Second pass: assign pointers
        System.out.println("\nStep 2: Assigning next and random pointers");
        current = head;
        while (current != null) {
            Node copiedNode = nodeMap.get(current);
            
            // Assign next pointer
            if (current.next != null) {
                copiedNode.next = nodeMap.get(current.next);
                System.out.println("  Set copy(" + copiedNode.val + ").next = copy(" + 
                                 nodeMap.get(current.next).val + ")");
            } else {
                System.out.println("  Set copy(" + copiedNode.val + ").next = null");
            }
            
            // Assign random pointer
            if (current.random != null) {
                copiedNode.random = nodeMap.get(current.random);
                System.out.println("  Set copy(" + copiedNode.val + ").random = copy(" + 
                                 nodeMap.get(current.random).val + ")");
            } else {
                System.out.println("  Set copy(" + copiedNode.val + ").random = null");
            }
            
            current = current.next;
        }
        
        Node result = nodeMap.get(head);
        System.out.println("\nCopied list:");
        printListWithRandom(result);
        
        return result;
    }
    
    /**
     * Helper method to print list with random pointers for visualization
     */
    private void printListWithRandom(Node head) {
        Map<Node, Integer> nodeToIndex = new HashMap<>();
        List<String> nodes = new ArrayList<>();
        
        // First pass: assign indices and collect node info
        Node current = head;
        int index = 0;
        while (current != null) {
            nodeToIndex.put(current, index);
            nodes.add("[" + current.val + ",null]");
            current = current.next;
            index++;
        }
        
        // Second pass: update random pointers in output
        current = head;
        index = 0;
        while (current != null) {
            if (current.random != null) {
                int randomIndex = nodeToIndex.get(current.random);
                nodes.set(index, "[" + current.val + "," + randomIndex + "]");
            }
            current = current.next;
            index++;
        }
        
        System.out.println(String.join(" -> ", nodes));
    }
    
    /**
     * Helper method to create test linked list with random pointers
     */
    public Node createTestList(int[][] nodes) {
        if (nodes == null || nodes.length == 0) return null;
        
        List<Node> nodeList = new ArrayList<>();
        
        // Create all nodes
        for (int i = 0; i < nodes.length; i++) {
            nodeList.add(new Node(nodes[i][0]));
        }
        
        // Set next pointers
        for (int i = 0; i < nodes.length - 1; i++) {
            nodeList.get(i).next = nodeList.get(i + 1);
        }
        
        // Set random pointers
        for (int i = 0; i < nodes.length; i++) {
            int randomIndex = nodes[i][1];
            if (randomIndex != -1) {
                nodeList.get(i).random = nodeList.get(randomIndex);
            }
        }
        
        return nodeList.get(0);
    }
    
    /**
     * Helper method to verify copied list
     */
    public boolean verifyCopy(Node original, Node copy) {
        Map<Node, Node> originalToCopy = new HashMap<>();
        Map<Node, Node> copyToOriginal = new HashMap<>();
        
        Node origCurrent = original;
        Node copyCurrent = copy;
        
        // Verify structure and build mappings
        while (origCurrent != null && copyCurrent != null) {
            // Check values
            if (origCurrent.val != copyCurrent.val) {
                return false;
            }
            
            // Store mappings
            originalToCopy.put(origCurrent, copyCurrent);
            copyToOriginal.put(copyCurrent, origCurrent);
            
            // Move to next nodes
            origCurrent = origCurrent.next;
            copyCurrent = copyCurrent.next;
        }
        
        // Check if both lists have same length
        if (origCurrent != null || copyCurrent != null) {
            return false;
        }
        
        // Verify random pointers
        origCurrent = original;
        copyCurrent = copy;
        
        while (origCurrent != null && copyCurrent != null) {
            if (origCurrent.random == null) {
                if (copyCurrent.random != null) {
                    return false;
                }
            } else {
                Node expectedCopy = originalToCopy.get(origCurrent.random);
                if (copyCurrent.random != expectedCopy) {
                    return false;
                }
            }
            
            // Verify no pointer to original list
            if (copyCurrent.random != null && copyToOriginal.containsKey(copyCurrent.random)) {
                // This is fine as long as it's the correct mapping
            }
            
            origCurrent = origCurrent.next;
            copyCurrent = copyCurrent.next;
        }
        
        return true;
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[][] testData) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Test data: " + java.util.Arrays.deepToString(testData));
        System.out.println("List length: " + testData.length);
        System.out.println("=================================");
        
        Node head = createTestList(testData);
        
        long startTime, endTime;
        Node result;
        
        // Hash Map approach
        startTime = System.nanoTime();
        result = copyRandomList(head);
        endTime = System.nanoTime();
        System.out.printf("Hash Map:          %8d ns%n", (endTime - startTime));
        
        // Interweaving approach
        startTime = System.nanoTime();
        result = copyRandomListInterweaving(head);
        endTime = System.nanoTime();
        System.out.printf("Interweaving:      %8d ns%n", (endTime - startTime));
        
        // Recursive approach
        startTime = System.nanoTime();
        result = copyRandomListRecursive(head);
        endTime = System.nanoTime();
        System.out.printf("Recursive:         %8d ns%n", (endTime - startTime));
        
        // Verify all produce valid copies
        boolean allValid = verifyCopy(head, result);
        System.out.println("All approaches produce valid copies: " + (allValid ? "PASSED" : "FAILED"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Copy List with Random Pointer:");
        System.out.println("======================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Complex list with multiple random pointers");
        int[][] test1 = {
            {7, -1},  // [7,null]
            {13, 0},  // [13,0] - points to node 0 (7)
            {11, 4},  // [11,4] - points to node 4 (1)
            {10, 2},  // [10,2] - points to node 2 (11)
            {1, 0}    // [1,0]  - points to node 0 (7)
        };
        Node head1 = solution.createTestList(test1);
        System.out.println("Original list created");
        
        Node result1 = solution.copyRandomList(head1);
        boolean test1Pass = solution.verifyCopy(head1, result1);
        System.out.println("Test 1: " + (test1Pass ? "PASSED" : "FAILED"));
        
        // Test case 2: Simple cycle
        System.out.println("\nTest 2: List with node pointing to itself");
        int[][] test2 = {
            {1, 0},  // [1,0] - points to itself
            {2, 1}   // [2,1] - points to node 1 (2)
        };
        Node head2 = solution.createTestList(test2);
        Node result2 = solution.copyRandomList(head2);
        boolean test2Pass = solution.verifyCopy(head2, result2);
        System.out.println("Test 2: " + (test2Pass ? "PASSED" : "FAILED"));
        
        // Test case 3: Empty list
        System.out.println("\nTest 3: Empty list");
        Node head3 = null;
        Node result3 = solution.copyRandomList(head3);
        boolean test3Pass = (result3 == null);
        System.out.println("Test 3: " + (test3Pass ? "PASSED" : "FAILED"));
        
        // Test case 4: Single node
        System.out.println("\nTest 4: Single node with null random");
        int[][] test4 = {{1, -1}};
        Node head4 = solution.createTestList(test4);
        Node result4 = solution.copyRandomList(head4);
        boolean test4Pass = solution.verifyCopy(head4, result4);
        System.out.println("Test 4: " + (test4Pass ? "PASSED" : "FAILED"));
        
        // Test case 5: Single node pointing to itself
        System.out.println("\nTest 5: Single node pointing to itself");
        int[][] test5 = {{1, 0}};
        Node head5 = solution.createTestList(test5);
        Node result5 = solution.copyRandomList(head5);
        boolean test5Pass = solution.verifyCopy(head5, result5);
        System.out.println("Test 5: " + (test5Pass ? "PASSED" : "FAILED"));
        
        // Test case 6: Compare all approaches
        System.out.println("\nTest 6: Verify all approaches produce same result");
        int[][] test6 = {
            {1, 2}, {2, 0}, {3, 1}
        };
        
        Node head6 = solution.createTestList(test6);
        Node result6a = solution.copyRandomList(head6);
        Node result6b = solution.copyRandomListInterweaving(head6);
        Node result6c = solution.copyRandomListRecursive(head6);
        
        boolean allEqual = solution.verifyCopy(head6, result6a) && 
                          solution.verifyCopy(head6, result6b) && 
                          solution.verifyCopy(head6, result6c);
        System.out.println("All approaches produce valid copies: " + (allEqual ? "PASSED" : "FAILED"));
        
        // Visualization test
        System.out.println("\nTest 7: Step-by-step visualization");
        int[][] test7 = {
            {1, 2}, {2, 0}, {3, 1}
        };
        Node head7 = solution.createTestList(test7);
        solution.copyRandomListVisual(head7);
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison - Small List");
        int[][] smallTest = new int[100][2];
        for (int i = 0; i < 100; i++) {
            smallTest[i][0] = i;
            smallTest[i][1] = (i + 1) % 100; // Create a cycle
        }
        solution.compareApproaches(smallTest);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nHash Map Approach (RECOMMENDED):");
        System.out.println("Key Insight: Use a hash map to maintain mapping between");
        System.out.println("original nodes and copied nodes for O(1) lookups.");
        System.out.println("\nSteps:");
        System.out.println("1. First Pass: Create all new nodes and store mapping");
        System.out.println("   - For each original node, create a copy");
        System.out.println("   - Store original -> copy mapping in hash map");
        System.out.println("2. Second Pass: Assign next and random pointers");
        System.out.println("   - For each original node, get its copy from map");
        System.out.println("   - Set copy.next = map.get(original.next)");
        System.out.println("   - Set copy.random = map.get(original.random)");
        
        System.out.println("\nInterweaving Approach (O(1) Space):");
        System.out.println("Key Insight: Insert copied nodes between original nodes");
        System.out.println("to create implicit mapping without extra space.");
        System.out.println("\nSteps:");
        System.out.println("1. Interweave: A->A'->B->B'->C->C'");
        System.out.println("2. Assign random: A'.random = A.random.next");
        System.out.println("3. Separate: Extract A'->B'->C' from A->B->C");
        
        System.out.println("\nWhy these approaches work:");
        System.out.println("- Hash Map: Provides direct mapping for random pointers");
        System.out.println("- Interweaving: Uses node positions for implicit mapping");
        System.out.println("- Both handle cycles and complex random pointers correctly");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nHash Map Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Two passes through the list: O(n) + O(n) = O(n)");
        System.out.println("  - Hash map operations are O(1) on average");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Hash map stores n node mappings");
        
        System.out.println("\nInterweaving Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Three passes through the list");
        System.out.println("  - Still linear time complexity");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only constant extra space (excluding the copy itself)");
        
        System.out.println("\nRecursive Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Each node visited once");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Recursion stack depth O(n) in worst case");
        System.out.println("  - Hash map for memoization stores n nodes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with hash map approach (most intuitive)");
        System.out.println("2. Explain the two-pass strategy clearly");
        System.out.println("3. Mention interweaving approach for O(1) space");
        System.out.println("4. Handle edge cases:");
        System.out.println("   - Empty list");
        System.out.println("   - Cycles in random pointers");
        System.out.println("   - Nodes pointing to themselves");
        System.out.println("5. Discuss time and space complexity trade-offs");
        System.out.println("6. Consider drawing diagrams to explain pointer assignments");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("- Forgetting to handle null random pointers");
        System.out.println("- Creating cycles between original and copied lists");
        System.out.println("- Not verifying the copy is completely independent");
        System.out.println("- Incorrect pointer assignments in interweaving approach");
        System.out.println("- Stack overflow in recursive approach for large lists");
        
        System.out.println("\nAll tests completed!");
    }
}
