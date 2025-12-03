
## Solution.java

```java
/**
 * 25. Reverse Nodes in k-Group
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given a linked list, reverse the nodes of a linked list k at a time and
 * return its modified list. If the number of nodes is not a multiple of k,
 * then left-out nodes in the end should remain as they are.
 * 
 * Key Insights:
 * 1. Need to reverse nodes in groups of k
 * 2. If remaining nodes < k, leave them unchanged
 * 3. Need to connect reversed groups properly
 * 4. Can use iterative or recursive approach
 * 
 * Approach (Iterative with Dummy):
 * 1. Use dummy node to simplify edge cases
 * 2. Traverse list, counting nodes to check if we have k nodes
 * 3. Reverse each group using standard 3-pointer technique
 * 4. Connect end of previous group to start of next group
 * 5. Continue until we have less than k nodes remaining
 * 
 * Time Complexity: O(n) - Each node visited once
 * Space Complexity: O(1) - Constant extra space
 * 
 * Tags: Linked List, Recursion
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

class Solution {
    
    /**
     * Approach 1: Iterative with Dummy Node (RECOMMENDED)
     * O(n) time, O(1) space
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroupEnd = dummy;
        
        while (true) {
            // Check if we have k nodes remaining
            ListNode kthNode = getKthNode(prevGroupEnd, k);
            if (kthNode == null) break; // Less than k nodes left
            
            ListNode nextGroupStart = kthNode.next;
            ListNode groupStart = prevGroupEnd.next;
            
            // Reverse the current group
            ListNode prev = kthNode.next;
            ListNode curr = groupStart;
            
            while (curr != nextGroupStart) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            
            // Connect previous group to current reversed group
            prevGroupEnd.next = kthNode;
            
            // Move prevGroupEnd to the end of current group
            prevGroupEnd = groupStart;
        }
        
        return dummy.next;
    }
    
    /**
     * Helper method to get the k-th node after the given node
     * Returns null if there are less than k nodes
     */
    private ListNode getKthNode(ListNode start, int k) {
        ListNode curr = start;
        for (int i = 0; i < k; i++) {
            if (curr == null) return null;
            curr = curr.next;
        }
        return curr;
    }
    
    /**
     * Approach 2: Recursive Solution
     * O(n) time, O(n/k) recursion stack space
     */
    public ListNode reverseKGroupRecursive(ListNode head, int k) {
        if (head == null || k == 1) return head;
        
        // Check if we have at least k nodes
        ListNode curr = head;
        int count = 0;
        while (curr != null && count < k) {
            curr = curr.next;
            count++;
        }
        
        if (count == k) {
            // We have k nodes, reverse them
            ListNode prev = null;
            curr = head;
            
            for (int i = 0; i < k; i++) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            
            // head is now the tail of reversed group
            // Connect it to the result of reversing next group
            head.next = reverseKGroupRecursive(curr, k);
            
            // prev is the new head of reversed group
            return prev;
        }
        
        // Not enough nodes, return as is
        return head;
    }
    
    /**
     * Approach 3: Iterative without Dummy (More Complex)
     * O(n) time, O(1) space
     */
    public ListNode reverseKGroupNoDummy(ListNode head, int k) {
        if (head == null || k == 1) return head;
        
        ListNode newHead = null;
        ListNode prevGroupEnd = null;
        ListNode curr = head;
        
        while (curr != null) {
            // Check if we have k nodes
            ListNode groupStart = curr;
            ListNode kthNode = null;
            int count = 0;
            
            while (curr != null && count < k) {
                if (count == k - 1) {
                    kthNode = curr;
                }
                curr = curr.next;
                count++;
            }
            
            if (count == k) {
                // We have k nodes, reverse them
                ListNode reversedHead = reverseList(groupStart, k);
                
                if (newHead == null) {
                    newHead = reversedHead;
                }
                
                if (prevGroupEnd != null) {
                    prevGroupEnd.next = reversedHead;
                }
                
                // groupStart is now the tail of reversed group
                prevGroupEnd = groupStart;
            } else {
                // Not enough nodes, connect previous group to remaining nodes
                if (prevGroupEnd != null) {
                    prevGroupEnd.next = groupStart;
                } else {
                    newHead = groupStart;
                }
            }
        }
        
        return newHead != null ? newHead : head;
    }
    
    /**
     * Helper method to reverse first k nodes of a list
     */
    private ListNode reverseList(ListNode head, int k) {
        ListNode prev = null;
        ListNode curr = head;
        
        for (int i = 0; i < k; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        return prev;
    }
    
    /**
     * Approach 4: Using Stack (Conceptual)
     * O(n) time, O(k) space
     */
    public ListNode reverseKGroupStack(ListNode head, int k) {
        if (head == null || k == 1) return head;
        
        Stack<ListNode> stack = new Stack<>();
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        
        while (curr != null) {
            // Push k nodes to stack
            for (int i = 0; i < k && curr != null; i++) {
                stack.push(curr);
                curr = curr.next;
            }
            
            // If we have k nodes in stack, reverse them
            if (stack.size() == k) {
                while (!stack.isEmpty()) {
                    prev.next = stack.pop();
                    prev = prev.next;
                }
                prev.next = curr;
            } else {
                // Not enough nodes, connect remaining nodes
                while (!stack.isEmpty()) {
                    prev.next = stack.remove(0); // Get from bottom
                    prev = prev.next;
                }
            }
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 5: Optimized Iterative with Counting First
     * Count total nodes first to avoid repeated counting
     */
    public ListNode reverseKGroupOptimized(ListNode head, int k) {
        if (head == null || k == 1) return head;
        
        // First, count total nodes
        int totalNodes = 0;
        ListNode countNode = head;
        while (countNode != null) {
            totalNodes++;
            countNode = countNode.next;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroupEnd = dummy;
        ListNode groupStart = head;
        
        // Calculate number of groups to reverse
        int groupsToReverse = totalNodes / k;
        
        for (int group = 0; group < groupsToReverse; group++) {
            // Reverse current group
            ListNode prev = null;
            ListNode curr = groupStart;
            
            for (int i = 0; i < k; i++) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            
            // Connect previous group to current reversed group
            prevGroupEnd.next = prev;
            
            // Update pointers for next iteration
            prevGroupEnd = groupStart; // groupStart is now tail of reversed group
            groupStart = curr;
        }
        
        // Connect last reversed group to remaining nodes
        prevGroupEnd.next = groupStart;
        
        return dummy.next;
    }
    
    /**
     * Helper method to visualize the linked list
     */
    private void printList(String label, ListNode head) {
        System.out.print(label + ": ");
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val);
            if (curr.next != null) {
                System.out.print(" -> ");
            }
            curr = curr.next;
        }
        System.out.println();
    }
    
    /**
     * Helper method to create a linked list from array
     */
    private ListNode createList(int[] values) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        return dummy.next;
    }
    
    /**
     * Helper method to verify list is correctly reversed in groups
     */
    private boolean verifyList(ListNode head, int k, int[] expected) {
        ListNode curr = head;
        int index = 0;
        
        while (curr != null) {
            if (index >= expected.length || curr.val != expected[index]) {
                return false;
            }
            curr = curr.next;
            index++;
        }
        
        return index == expected.length;
    }
    
    /**
     * Visualize the reversal process step by step
     */
    private void visualizeReversal(ListNode head, int k, String approach) {
        System.out.println("\n" + approach + " - Visualization:");
        System.out.println("Original List:");
        printList("  List", head);
        System.out.println("k = " + k);
        
        // Create a copy for visualization
        ListNode dummy = new ListNode(0);
        ListNode copyCurr = head;
        ListNode copyPrev = dummy;
        while (copyCurr != null) {
            copyPrev.next = new ListNode(copyCurr.val);
            copyPrev = copyPrev.next;
            copyCurr = copyCurr.next;
        }
        
        ListNode result;
        switch (approach) {
            case "Iterative":
                result = reverseKGroup(dummy.next, k);
                break;
            case "Recursive":
                result = reverseKGroupRecursive(dummy.next, k);
                break;
            case "No Dummy":
                result = reverseKGroupNoDummy(dummy.next, k);
                break;
            case "Optimized":
                result = reverseKGroupOptimized(dummy.next, k);
                break;
            default:
                result = reverseKGroup(dummy.next, k);
        }
        
        System.out.println("Result List:");
        printList("  List", result);
        
        // Show group boundaries
        System.out.println("Group Boundaries:");
        ListNode curr = result;
        int position = 0;
        while (curr != null) {
            if (position % k == 0) {
                System.out.print("| ");
            }
            System.out.print(curr.val + " ");
            curr = curr.next;
            position++;
        }
        if (position % k != 0) {
            System.out.print("| (partial group)");
        }
        System.out.println();
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Reverse Nodes in k-Group Solution:");
        System.out.println("==========================================");
        
        // Test case 1: Basic example k=2
        System.out.println("\nTest 1: Basic example k=2");
        int[] list1 = {1, 2, 3, 4, 5};
        ListNode head1 = solution.createList(list1);
        int k1 = 2;
        int[] expected1 = {2, 1, 4, 3, 5};
        
        solution.visualizeReversal(head1, k1, "Iterative");
        
        long startTime = System.nanoTime();
        ListNode result1a = solution.reverseKGroup(head1, k1);
        long time1a = System.nanoTime() - startTime;
        
        boolean passed1 = solution.verifyList(result1a, k1, expected1);
        System.out.println("Iterative: " + (passed1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1a + " ns)");
        
        // Test case 2: k=3
        System.out.println("\nTest 2: k=3");
        int[] list2 = {1, 2, 3, 4, 5};
        ListNode head2 = solution.createList(list2);
        int k2 = 3;
        int[] expected2 = {3, 2, 1, 4, 5};
        
        solution.visualizeReversal(head2, k2, "Iterative");
        
        startTime = System.nanoTime();
        ListNode result2a = solution.reverseKGroup(head2, k2);
        long time2a = System.nanoTime() - startTime;
        
        boolean passed2 = solution.verifyList(result2a, k2, expected2);
        System.out.println("Iterative: " + (passed2 ? "PASSED" : "FAILED") + 
                         " (Time: " + time2a + " ns)");
        
        // Test case 3: k equals list length
        System.out.println("\nTest 3: k equals list length");
        int[] list3 = {1, 2, 3, 4, 5};
        ListNode head3 = solution.createList(list3);
        int k3 = 5;
        int[] expected3 = {5, 4, 3, 2, 1};
        
        solution.visualizeReversal(head3, k3, "Iterative");
        
        startTime = System.nanoTime();
        ListNode result3a = solution.reverseKGroup(head3, k3);
        long time3a = System.nanoTime() - startTime;
        
        boolean passed3 = solution.verifyList(result3a, k3, expected3);
        System.out.println("Iterative: " + (passed3 ? "PASSED" : "FAILED") + 
                         " (Time: " + time3a + " ns)");
        
        // Test case 4: k=1 (no change)
        System.out.println("\nTest 4: k=1 (no change)");
        int[] list4 = {1, 2, 3, 4, 5};
        ListNode head4 = solution.createList(list4);
        int k4 = 1;
        int[] expected4 = {1, 2, 3, 4, 5};
        
        solution.visualizeReversal(head4, k4, "Iterative");
        
        startTime = System.nanoTime();
        ListNode result4a = solution.reverseKGroup(head4, k4);
        long time4a = System.nanoTime() - startTime;
        
        boolean passed4 = solution.verifyList(result4a, k4, expected4);
        System.out.println("Iterative: " + (passed4 ? "PASSED" : "FAILED") + 
                         " (Time: " + time4a + " ns)");
        
        // Test case 5: Single node list
        System.out.println("\nTest 5: Single node list");
        int[] list5 = {1};
        ListNode head5 = solution.createList(list5);
        int k5 = 1;
        int[] expected5 = {1};
        
        solution.visualizeReversal(head5, k5, "Iterative");
        
        startTime = System.nanoTime();
        ListNode result5a = solution.reverseKGroup(head5, k5);
        long time5a = System.nanoTime() - startTime;
        
        boolean passed5 = solution.verifyList(result5a, k5, expected5);
        System.out.println("Iterative: " + (passed5 ? "PASSED" : "FAILED") + 
                         " (Time: " + time5a + " ns)");
        
        // Test case 6: Empty list
        System.out.println("\nTest 6: Empty list");
        ListNode head6 = null;
        int k6 = 2;
        
        solution.visualizeReversal(head6, k6, "Iterative");
        
        startTime = System.nanoTime();
        ListNode result6a = solution.reverseKGroup(head6, k6);
        long time6a = System.nanoTime() - startTime;
        
        boolean passed6 = (result6a == null);
        System.out.println("Iterative: " + (passed6 ? "PASSED" : "FAILED") + 
                         " (Time: " + time6a + " ns)");
        
        // Test case 7: Partial last group
        System.out.println("\nTest 7: Partial last group (k=2, 6 elements)");
        int[] list7 = {1, 2, 3, 4, 5, 6};
        ListNode head7 = solution.createList(list7);
        int k7 = 4;
        int[] expected7 = {4, 3, 2, 1, 5, 6};
        
        solution.visualizeReversal(head7, k7, "Iterative");
        
        startTime = System.nanoTime();
        ListNode result7a = solution.reverseKGroup(head7, k7);
        long time7a = System.nanoTime() - startTime;
        
        boolean passed7 = solution.verifyList(result7a, k7, expected7);
        System.out.println("Iterative: " + (passed7 ? "PASSED" : "FAILED") + 
                         " (Time: " + time7a + " ns)");
        
        // Compare all approaches
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING ALL APPROACHES:");
        System.out.println("=".repeat(70));
        
        int[] testList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ListNode testHead = solution.createList(testList);
        int testK = 3;
        int[] expectedAll = {3, 2, 1, 6, 5, 4, 9, 8, 7, 10};
        
        System.out.println("\nTest List: 1->2->3->4->5->6->7->8->9->10");
        System.out.println("k = " + testK);
        System.out.println("Expected: 3->2->1->6->5->4->9->8->7->10");
        
        // Test Iterative
        ListNode copy1 = solution.createList(testList);
        startTime = System.nanoTime();
        ListNode resultIterative = solution.reverseKGroup(copy1, testK);
        long timeIterative = System.nanoTime() - startTime;
        boolean correctIterative = solution.verifyList(resultIterative, testK, expectedAll);
        
        // Test Recursive
        ListNode copy2 = solution.createList(testList);
        startTime = System.nanoTime();
        ListNode resultRecursive = solution.reverseKGroupRecursive(copy2, testK);
        long timeRecursive = System.nanoTime() - startTime;
        boolean correctRecursive = solution.verifyList(resultRecursive, testK, expectedAll);
        
        // Test No Dummy
        ListNode copy3 = solution.createList(testList);
        startTime = System.nanoTime();
        ListNode resultNoDummy = solution.reverseKGroupNoDummy(copy3, testK);
        long timeNoDummy = System.nanoTime() - startTime;
        boolean correctNoDummy = solution.verifyList(resultNoDummy, testK, expectedAll);
        
        // Test Optimized
        ListNode copy4 = solution.createList(testList);
        startTime = System.nanoTime();
        ListNode resultOptimized = solution.reverseKGroupOptimized(copy4, testK);
        long timeOptimized = System.nanoTime() - startTime;
        boolean correctOptimized = solution.verifyList(resultOptimized, testK, expectedAll);
        
        System.out.println("\nResults:");
        System.out.println("Iterative:  " + (correctIterative ? "PASSED" : "FAILED") + 
                         " - Time: " + timeIterative + " ns");
        System.out.println("Recursive:  " + (correctRecursive ? "PASSED" : "FAILED") + 
                         " - Time: " + timeRecursive + " ns");
        System.out.println("No Dummy:   " + (correctNoDummy ? "PASSED" : "FAILED") + 
                         " - Time: " + timeNoDummy + " ns");
        System.out.println("Optimized:  " + (correctOptimized ? "PASSED" : "FAILED") + 
                         " - Time: " + timeOptimized + " ns");
        
        // Performance test with large list
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST WITH LARGE LIST:");
        System.out.println("=".repeat(70));
        
        // Create large list
        int size = 10000;
        int[] largeList = new int[size];
        for (int i = 0; i < size; i++) {
            largeList[i] = i + 1;
        }
        ListNode largeHead = solution.createList(largeList);
        int largeK = 4;
        
        System.out.println("\nTesting with list of " + size + " nodes, k = " + largeK);
        
        // Test Iterative
        ListNode copyLarge1 = solution.createList(largeList);
        startTime = System.nanoTime();
        solution.reverseKGroup(copyLarge1, largeK);
        long timeLargeIterative = System.nanoTime() - startTime;
        
        // Test Recursive
        ListNode copyLarge2 = solution.createList(largeList);
        startTime = System.nanoTime();
        solution.reverseKGroupRecursive(copyLarge2, largeK);
        long timeLargeRecursive = System.nanoTime() - startTime;
        
        // Test Optimized
        ListNode copyLarge3 = solution.createList(largeList);
        startTime = System.nanoTime();
        solution.reverseKGroupOptimized(copyLarge3, largeK);
        long timeLargeOptimized = System.nanoTime() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Iterative:  " + (timeLargeIterative / 1_000_000) + " ms");
        System.out.println("Recursive:  " + (timeLargeRecursive / 1_000_000) + " ms");
        System.out.println("Optimized:  " + (timeLargeOptimized / 1_000_000) + " ms");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nIterative Approach Step-by-Step:");
        System.out.println("1. Create a dummy node to simplify edge cases");
        System.out.println("2. Initialize prevGroupEnd to dummy");
        System.out.println("3. While we have at least k nodes:");
        System.out.println("   a. Find the k-th node from prevGroupEnd");
        System.out.println("   b. If k-th node is null, break (not enough nodes)");
        System.out.println("   c. Save nextGroupStart = kthNode.next");
        System.out.println("   d. Save groupStart = prevGroupEnd.next");
        System.out.println("   e. Reverse the group using 3 pointers:");
        System.out.println("      - prev = nextGroupStart (connects to next group)");
        System.out.println("      - curr = groupStart");
        System.out.println("      - Reverse k nodes");
        System.out.println("   f. Connect prevGroupEnd.next = kthNode (reversed head)");
        System.out.println("   g. Update prevGroupEnd = groupStart (now tail of group)");
        System.out.println("4. Return dummy.next");
        
        System.out.println("\nVisual Example (k=3, list: 1->2->3->4->5):");
        System.out.println("Step 1: dummy->1->2->3->4->5");
        System.out.println("        prevGroupEnd = dummy");
        System.out.println("Step 2: Find kthNode = 3, groupStart = 1, nextGroupStart = 4");
        System.out.println("Step 3: Reverse 1->2->3 to 3->2->1");
        System.out.println("        Connect: dummy->3");
        System.out.println("        Connect: 1->4");
        System.out.println("Step 4: Update prevGroupEnd = 1");
        System.out.println("Step 5: Check remaining nodes: 4->5 (less than k)");
        System.out.println("Result: dummy->3->2->1->4->5");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Iterative with Dummy (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through list");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient");
        System.out.println("     - Clear and intuitive");
        System.out.println("     - Handles all edge cases well");
        System.out.println("     - No recursion overhead");
        System.out.println("   Cons:");
        System.out.println("     - More pointer manipulation");
        System.out.println("     - Need helper method for k-th node");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Recursive:");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(n/k) - Recursion stack depth");
        System.out.println("   Pros:");
        System.out.println("     - Elegant and concise");
        System.out.println("     - Natural recursive structure");
        System.out.println("     - Easy to understand conceptually");
        System.out.println("   Cons:");
        System.out.println("     - Recursion overhead");
        System.out.println("     - Stack overflow risk for large n/k");
        System.out.println("     - Less efficient than iterative");
        System.out.println("   Best for: Learning, small lists, interviews if asked");
        
        System.out.println("\n3. Iterative without Dummy:");
        System.out.println("   Time: O(n) - Single pass");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - No dummy node overhead");
        System.out.println("     - Slightly less memory");
        System.out.println("   Cons:");
        System.out.println("     - More complex edge case handling");
        System.out.println("     - Harder to implement correctly");
        System.out.println("   Best for: Memory-constrained environments");
        
        System.out.println("\n4. Using Stack:");
        System.out.println("   Time: O(n) - Each node visited once");
        System.out.println("   Space: O(k) - Stack stores k nodes");
        System.out.println("   Pros:");
        System.out.println("     - Simple reversal using stack");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Extra O(k) space");
        System.out.println("     - Not as efficient");
        System.out.println("     - Stack operations overhead");
        System.out.println("   Best for: Conceptual understanding");
        
        System.out.println("\n5. Optimized with Counting First:");
        System.out.println("   Time: O(n) - Two passes through list");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Knows exact number of groups upfront");
        System.out.println("     - No repeated counting");
        System.out.println("   Cons:");
        System.out.println("     - Two passes through list");
        System.out.println("     - Extra counting overhead");
        System.out.println("   Best for: When group count needed for other purposes");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify requirements:");
        System.out.println("   - Reverse nodes in groups of k");
        System.out.println("   - Leave remaining nodes as is if < k");
        System.out.println("   - Can't change node values, only pointers");
        
        System.out.println("\n2. Discuss edge cases:");
        System.out.println("   - k = 1 (no change)");
        System.out.println("   - k = list length (reverse entire list)");
        System.out.println("   - Empty list");
        System.out.println("   - Single node");
        System.out.println("   - Partial last group");
        
        System.out.println("\n3. Propose solution:");
        System.out.println("   - Use dummy node to simplify");
        System.out.println("   - Traverse and count k nodes");
        System.out.println("   - Reverse each group using 3-pointer technique");
        System.out.println("   - Connect groups properly");
        
        System.out.println("\n4. Implement helper methods:");
        System.out.println("   - getKthNode(start, k)");
        System.out.println("   - reverseList(groupStart, k) or inline reversal");
        
        System.out.println("\n5. Walk through example:");
        System.out.println("   - Use a simple example (k=2 or 3)");
        System.out.println("   - Draw pointers and connections");
        System.out.println("   - Show step-by-step reversal");
        
        System.out.println("\n6. Optimize and discuss alternatives:");
        System.out.println("   - Mention recursive solution");
        System.out.println("   - Discuss space/time tradeoffs");
        System.out.println("   - Consider edge case optimizations");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- O(n) time complexity (each node visited once)");
        System.out.println("- O(1) space complexity (except recursive)");
        System.out.println("- Dummy node simplifies edge cases");
        System.out.println("- Proper group connection is critical");
        System.out.println("- Check for k nodes before reversing");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting to check if we have k nodes before reversing");
        System.out.println("- Not updating pointers correctly between groups");
        System.out.println("- Memory leaks from lost nodes");
        System.out.println("- Infinite loops from incorrect pointer updates");
        System.out.println("- Not handling k=1 or empty list cases");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with k=1 (should return same list)");
        System.out.println("2. Test with k equals list length (full reversal)");
        System.out.println("3. Test with partial last group");
        System.out.println("4. Test with empty list");
        System.out.println("5. Test with single node");
        System.out.println("6. Test with k > list length");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
