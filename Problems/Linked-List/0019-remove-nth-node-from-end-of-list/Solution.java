
## Solution.java

```java
/**
 * 19. Remove Nth Node From End of List
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 * 
 * Key Insights:
 * 1. Two pass: find length first, then remove (L-n+1)th node
 * 2. One pass two pointers: use fast and slow pointers with n gap
 * 3. Use dummy node to handle edge case of removing head
 * 4. Move fast n steps ahead, then move both until fast reaches end
 * 
 * Approach (One Pass Two Pointers with Dummy - RECOMMENDED):
 * 1. Create dummy node pointing to head
 * 2. Initialize fast and slow pointers at dummy
 * 3. Move fast pointer n steps ahead
 * 4. Move both pointers until fast reaches last node
 * 5. Slow will be at (n+1)th node from end
 * 6. Remove slow.next (the nth node from end)
 * 7. Return dummy.next
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Linked List, Two Pointers
 */

/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    /**
     * Approach 1: One Pass Two Pointers with Dummy Node - RECOMMENDED
     * O(n) time, O(1) space - Most efficient and clean
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Create a dummy node that points to head
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        // Move fast pointer n steps ahead
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        
        // Move both pointers until fast reaches end
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        // Remove the nth node from end (slow.next)
        slow.next = slow.next.next;
        
        return dummy.next;
    }
    
    /**
     * Approach 2: Two Pass - Find Length First
     * O(n) time, O(1) space - Simple but two passes
     */
    public ListNode removeNthFromEndTwoPass(ListNode head, int n) {
        // First pass: find length of list
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        
        // Calculate position from beginning
        int positionFromStart = length - n;
        
        // If removing head node
        if (positionFromStart == 0) {
            return head.next;
        }
        
        // Second pass: find node before target and remove
        current = head;
        for (int i = 0; i < positionFromStart - 1; i++) {
            current = current.next;
        }
        
        // Remove the target node
        current.next = current.next.next;
        
        return head;
    }
    
    /**
     * Approach 3: Two Pointers without Dummy Node
     * O(n) time, O(1) space - More complex edge case handling
     */
    public ListNode removeNthFromEndNoDummy(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;
        
        // Move fast n steps ahead
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        
        // If fast is null, we're removing the head
        if (fast == null) {
            return head.next;
        }
        
        // Move both until fast reaches last node
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        // Remove the target node
        slow.next = slow.next.next;
        
        return head;
    }
    
    /**
     * Approach 4: Using Stack
     * O(n) time, O(n) space - Simple but uses extra space
     */
    public ListNode removeNthFromEndStack(ListNode head, int n) {
        if (head == null) return null;
        
        Stack<ListNode> stack = new Stack<>();
        ListNode current = head;
        
        // Push all nodes to stack
        while (current != null) {
            stack.push(current);
            current = current.next;
        }
        
        // Pop n nodes to find the target
        for (int i = 0; i < n; i++) {
            stack.pop();
        }
        
        // If stack is empty, we're removing head
        if (stack.isEmpty()) {
            return head.next;
        }
        
        // Get the node before target and remove target
        ListNode prev = stack.peek();
        prev.next = prev.next.next;
        
        return head;
    }
    
    /**
     * Approach 5: Recursive Approach
     * O(n) time, O(n) space - Elegant but uses recursion stack
     */
    public ListNode removeNthFromEndRecursive(ListNode head, int n) {
        // Use a wrapper to track position from end
        int[] position = {0};
        return removeHelper(head, n, position);
    }
    
    private ListNode removeHelper(ListNode node, int n, int[] position) {
        if (node == null) {
            return null;
        }
        
        // Recursively go to end
        node.next = removeHelper(node.next, n, position);
        
        // On way back, increment position
        position[0]++;
        
        // If this is the node to remove
        if (position[0] == n) {
            return node.next;
        }
        
        return node;
    }
    
    /**
     * Approach 6: Two Pointers with Visualization
     * Same as Approach 1 but with step-by-step visualization
     */
    public ListNode removeNthFromEndVisual(ListNode head, int n) {
        System.out.println("Initial list: " + listToString(head));
        System.out.println("Removing " + n + "th node from end");
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        System.out.println("\nStep 1: Move fast pointer " + (n + 1) + " steps ahead");
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
            System.out.println("  Fast at: " + (fast == null ? "null" : fast.val));
        }
        
        System.out.println("\nStep 2: Move both pointers until fast reaches end");
        int step = 1;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
            System.out.println("  Step " + step + ": Fast=" + 
                             (fast == null ? "null" : fast.val) + 
                             ", Slow=" + slow.val);
            step++;
        }
        
        System.out.println("\nStep 3: Remove slow.next (node with value: " + 
                         (slow.next == null ? "null" : slow.next.val) + ")");
        slow.next = slow.next.next;
        
        System.out.println("Final list: " + listToString(dummy.next));
        return dummy.next;
    }
    
    /**
     * Helper method to convert linked list to string for visualization
     */
    private String listToString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        ListNode current = head;
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        return sb.length() == 0 ? "null" : sb.toString();
    }
    
    /**
     * Helper method to create linked list from array
     */
    private ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }
    
    /**
     * Helper method to compare two linked lists
     */
    private boolean compareLists(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
            if (l1.val != l2.val) {
                return false;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1 == null && l2 == null;
    }
    
    /**
     * Helper method to get list length
     */
    private int getListLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] testData, int n) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Test data: " + java.util.Arrays.toString(testData));
        System.out.println("n: " + n);
        System.out.println("List length: " + testData.length);
        System.out.println("=================================");
        
        long startTime, endTime;
        ListNode result;
        
        // Two pointers with dummy
        ListNode head1 = createList(testData);
        startTime = System.nanoTime();
        result = removeNthFromEnd(head1, n);
        endTime = System.nanoTime();
        System.out.printf("Two Pointers (dummy): %8d ns%n", (endTime - startTime));
        
        // Two pass approach
        ListNode head2 = createList(testData);
        startTime = System.nanoTime();
        result = removeNthFromEndTwoPass(head2, n);
        endTime = System.nanoTime();
        System.out.printf("Two Pass:            %8d ns%n", (endTime - startTime));
        
        // Stack approach
        ListNode head3 = createList(testData);
        startTime = System.nanoTime();
        result = removeNthFromEndStack(head3, n);
        endTime = System.nanoTime();
        System.out.printf("Stack:               %8d ns%n", (endTime - startTime));
        
        // Verify all produce same result
        boolean allEqual = compareLists(head1, head2) && compareLists(head1, head3);
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Remove Nth Node From End of List:");
        System.out.println("=========================================");
        
        // Test case 1: Remove from middle
        System.out.println("\nTest 1: Remove 2nd from end [1,2,3,4,5]");
        int[] test1 = {1, 2, 3, 4, 5};
        ListNode head1 = solution.createList(test1);
        System.out.println("Original: " + solution.listToString(head1));
        
        ListNode result1 = solution.removeNthFromEnd(head1, 2);
        System.out.println("After removing 2nd from end: " + solution.listToString(result1));
        
        int[] expected1 = {1, 2, 3, 5};
        ListNode expectedHead1 = solution.createList(expected1);
        boolean test1Pass = solution.compareLists(result1, expectedHead1);
        System.out.println("Test 1: " + (test1Pass ? "PASSED" : "FAILED"));
        
        // Test case 2: Remove head
        System.out.println("\nTest 2: Remove head (5th from end) [1,2,3,4,5]");
        int[] test2 = {1, 2, 3, 4, 5};
        ListNode head2 = solution.createList(test2);
        System.out.println("Original: " + solution.listToString(head2));
        
        ListNode result2 = solution.removeNthFromEnd(head2, 5);
        System.out.println("After removing head: " + solution.listToString(result2));
        
        int[] expected2 = {2, 3, 4, 5};
        ListNode expectedHead2 = solution.createList(expected2);
        boolean test2Pass = solution.compareLists(result2, expectedHead2);
        System.out.println("Test 2: " + (test2Pass ? "PASSED" : "FAILED"));
        
        // Test case 3: Remove tail
        System.out.println("\nTest 3: Remove tail (1st from end) [1,2,3,4,5]");
        int[] test3 = {1, 2, 3, 4, 5};
        ListNode head3 = solution.createList(test3);
        System.out.println("Original: " + solution.listToString(head3));
        
        ListNode result3 = solution.removeNthFromEnd(head3, 1);
        System.out.println("After removing tail: " + solution.listToString(result3));
        
        int[] expected3 = {1, 2, 3, 4};
        ListNode expectedHead3 = solution.createList(expected3);
        boolean test3Pass = solution.compareLists(result3, expectedHead3);
        System.out.println("Test 3: " + (test3Pass ? "PASSED" : "FAILED"));
        
        // Test case 4: Single node
        System.out.println("\nTest 4: Remove from single node [1]");
        int[] test4 = {1};
        ListNode head4 = solution.createList(test4);
        System.out.println("Original: " + solution.listToString(head4));
        
        ListNode result4 = solution.removeNthFromEnd(head4, 1);
        System.out.println("After removal: " + solution.listToString(result4));
        
        boolean test4Pass = result4 == null;
        System.out.println("Test 4: " + (test4Pass ? "PASSED" : "FAILED"));
        
        // Test case 5: Two nodes, remove tail
        System.out.println("\nTest 5: Remove from two nodes [1,2]");
        int[] test5 = {1, 2};
        ListNode head5 = solution.createList(test5);
        System.out.println("Original: " + solution.listToString(head5));
        
        ListNode result5 = solution.removeNthFromEnd(head5, 1);
        System.out.println("After removing tail: " + solution.listToString(result5));
        
        boolean test5Pass = result5 != null && result5.val == 1 && result5.next == null;
        System.out.println("Test 5: " + (test5Pass ? "PASSED" : "FAILED"));
        
        // Test case 6: Compare all approaches
        System.out.println("\nTest 6: Verify all approaches produce same result");
        int[] test6 = {1, 2, 3, 4, 5};
        
        ListNode head6a = solution.createList(test6);
        ListNode head6b = solution.createList(test6);
        ListNode head6c = solution.createList(test6);
        ListNode head6d = solution.createList(test6);
        
        ListNode result6a = solution.removeNthFromEnd(head6a, 2);
        ListNode result6b = solution.removeNthFromEndTwoPass(head6b, 2);
        ListNode result6c = solution.removeNthFromEndStack(head6c, 2);
        ListNode result6d = solution.removeNthFromEndRecursive(head6d, 2);
        
        boolean allEqual = solution.compareLists(result6a, result6b) && 
                          solution.compareLists(result6a, result6c) &&
                          solution.compareLists(result6a, result6d);
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
        
        // Visualization test
        System.out.println("\nTest 7: Step-by-step visualization");
        int[] test7 = {1, 2, 3, 4, 5};
        ListNode head7 = solution.createList(test7);
        solution.removeNthFromEndVisual(head7, 2);
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison - Small List");
        int[] smallTest = new int[100];
        for (int i = 0; i < smallTest.length; i++) {
            smallTest[i] = i + 1;
        }
        solution.compareApproaches(smallTest, 50);
        
        System.out.println("\nTest 9: Performance Comparison - Large List");
        int[] largeTest = new int[10000];
        for (int i = 0; i < largeTest.length; i++) {
            largeTest[i] = i + 1;
        }
        solution.compareApproaches(largeTest, 5000);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nTwo Pointers with Dummy Node (RECOMMENDED):");
        System.out.println("Key Insight: Maintain a gap of n nodes between fast and slow pointers");
        System.out.println("Steps:");
        System.out.println("1. Create dummy node pointing to head (handles head removal)");
        System.out.println("2. Move fast pointer n+1 steps from dummy");
        System.out.println("3. Move both pointers until fast reaches null");
        System.out.println("4. Slow will be at node before target");
        System.out.println("5. Remove slow.next");
        
        System.out.println("\nWhy move fast n+1 steps?");
        System.out.println("We want slow to stop at the node BEFORE the target");
        System.out.println("With n+1 gap, when fast reaches end, slow is at (n+1)th from end");
        System.out.println("Example: [1->2->3->4->5], n=2");
        System.out.println("  Fast moves 3 steps: 1->2->3->4");
        System.out.println("  Move both: fast=null, slow=3");
        System.out.println("  Remove slow.next (4)");
        System.out.println("  Result: 1->2->3->5");
        
        System.out.println("\nMathematical Proof:");
        System.out.println("Let L = list length");
        System.out.println("Target position from start: L - n + 1");
        System.out.println("We want to find node at position: L - n");
        System.out.println("Fast moves n+1 steps, then both move (L - n - 1) steps");
        System.out.println("Slow position: 1 + (L - n - 1) = L - n ✓");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nTwo Pointers with Dummy:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Single pass through the list");
        System.out.println("  - Fast pointer traverses entire list");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only constant extra space for pointers");
        
        System.out.println("\nTwo Pass Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Two passes: O(n) + O(n) = O(n)");
        System.out.println("  - Still linear time");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only constant extra space");
        
        System.out.println("\nStack Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Push all nodes: O(n)");
        System.out.println("  - Pop n nodes: O(n)");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Stack stores all nodes");
        
        System.out.println("\nRecursive Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Each node visited once");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Recursion stack depth O(n)");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with two pointers with dummy node approach");
        System.out.println("2. Explain why dummy node helps with head removal");
        System.out.println("3. Clearly describe the n+1 gap strategy");
        System.out.println("4. Handle all edge cases:");
        System.out.println("   - Removing head");
        System.out.println("   - Single node list");
        System.out.println("   - Valid n range (1 <= n <= length)");
        System.out.println("5. Mention alternative approaches");
        System.out.println("6. Discuss time and space complexity");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("- Not handling head removal case");
        System.out.println("- Incorrect gap between pointers (should be n+1)");
        System.out.println("- Not checking for null pointers");
        System.out.println("- Forgetting to return dummy.next instead of head");
        System.out.println("- Off-by-one errors in pointer movement");
        
        System.out.println("\nAll tests completed!");
    }
}
