
## Solution.java

```java
/**
 * 206. Reverse Linked List
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given the head of a singly linked list, reverse the list and return the reversed list.
 * 
 * Key Insights:
 * 1. Use three pointers: prev, current, and next to reverse links iteratively
 * 2. Recursive approach: reverse the rest and adjust current node's pointers
 * 3. Stack approach: push all nodes and pop to create reversed list (extra space)
 * 4. Must handle edge cases: empty list, single node list
 * 
 * Approach (Iterative - RECOMMENDED):
 * 1. Initialize prev = null, current = head
 * 2. While current != null:
 *    - Store next node: next = current.next
 *    - Reverse link: current.next = prev
 *    - Move pointers: prev = current, current = next
 * 3. Return prev (new head)
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Linked List, Recursion
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
     * Approach 1: Iterative Pointer Reversal - RECOMMENDED
     * O(n) time, O(1) space - Most efficient
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode next = current.next; // Store next node
            current.next = prev;          // Reverse the link
            prev = current;               // Move prev forward
            current = next;               // Move current forward
        }
        
        return prev; // New head of reversed list
    }
    
    /**
     * Approach 2: Recursive - Elegant but uses O(n) stack space
     * O(n) time, O(n) space due to recursion stack
     */
    public ListNode reverseListRecursive(ListNode head) {
        // Base case: empty list or single node
        if (head == null || head.next == null) {
            return head;
        }
        
        // Reverse the rest of the list
        ListNode newHead = reverseListRecursive(head.next);
        
        // Adjust pointers for current node
        head.next.next = head;
        head.next = null;
        
        return newHead;
    }
    
    /**
     * Approach 3: Recursive with Helper Method
     * More explicit recursive implementation
     */
    public ListNode reverseListRecursiveHelper(ListNode head) {
        return reverseListRecursiveHelper(head, null);
    }
    
    private ListNode reverseListRecursiveHelper(ListNode current, ListNode prev) {
        if (current == null) {
            return prev;
        }
        
        ListNode next = current.next;
        current.next = prev;
        return reverseListRecursiveHelper(next, current);
    }
    
    /**
     * Approach 4: Using Stack (Not Recommended - extra space)
     * O(n) time, O(n) space - Simple but inefficient
     */
    public ListNode reverseListStack(ListNode head) {
        if (head == null) return null;
        
        Stack<ListNode> stack = new Stack<>();
        ListNode current = head;
        
        // Push all nodes to stack
        while (current != null) {
            stack.push(current);
            current = current.next;
        }
        
        // Pop nodes to create reversed list
        ListNode newHead = stack.pop();
        current = newHead;
        
        while (!stack.isEmpty()) {
            current.next = stack.pop();
            current = current.next;
        }
        
        current.next = null; // Important: set last node's next to null
        return newHead;
    }
    
    /**
     * Approach 5: Iterative with Detailed Comments
     * Same as Approach 1 but with more detailed comments
     */
    public ListNode reverseListDetailed(ListNode head) {
        // If list is empty or has only one node, return as is
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode prev = null;    // Previous node starts as null
        ListNode current = head; // Current node starts at head
        
        while (current != null) {
            // Step 1: Save the next node before we break the link
            ListNode nextTemp = current.next;
            
            // Step 2: Reverse the link - point current to previous
            current.next = prev;
            
            // Step 3: Move prev to current (prev becomes the new head so far)
            prev = current;
            
            // Step 4: Move current to the saved next node
            current = nextTemp;
        }
        
        // When loop ends, prev is the new head of reversed list
        return prev;
    }
    
    /**
     * Approach 6: In-place with Visualization
     * Includes visualization of each step
     */
    public ListNode reverseListVisual(ListNode head) {
        System.out.println("Initial list: " + listToString(head));
        
        ListNode prev = null;
        ListNode current = head;
        int step = 1;
        
        while (current != null) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  prev = " + (prev == null ? "null" : prev.val));
            System.out.println("  current = " + current.val);
            
            ListNode next = current.next;
            System.out.println("  next = " + (next == null ? "null" : next.val));
            
            // Reverse the link
            current.next = prev;
            System.out.println("  Reversed: " + current.val + " -> " + 
                             (prev == null ? "null" : prev.val));
            
            // Move pointers
            prev = current;
            current = next;
            
            System.out.println("  After move:");
            System.out.println("    prev = " + (prev == null ? "null" : prev.val));
            System.out.println("    current = " + (current == null ? "null" : current.val));
            System.out.println("  Current list state: " + listToString(prev));
            
            step++;
        }
        
        System.out.println("\nFinal reversed list: " + listToString(prev));
        return prev;
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
        return sb.toString();
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
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] testData) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Test data: " + java.util.Arrays.toString(testData));
        System.out.println("List length: " + testData.length);
        System.out.println("=================================");
        
        ListNode head = createList(testData);
        
        long startTime, endTime;
        ListNode result;
        
        // Iterative approach
        startTime = System.nanoTime();
        result = reverseList(head);
        endTime = System.nanoTime();
        System.out.printf("Iterative:        %8d ns%n", (endTime - startTime));
        
        // Reset list
        head = createList(testData);
        
        // Recursive approach
        startTime = System.nanoTime();
        result = reverseListRecursive(head);
        endTime = System.nanoTime();
        System.out.printf("Recursive:        %8d ns%n", (endTime - startTime));
        
        // Reset list
        head = createList(testData);
        
        // Stack approach
        startTime = System.nanoTime();
        result = reverseListStack(head);
        endTime = System.nanoTime();
        System.out.printf("Stack:            %8d ns%n", (endTime - startTime));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Reverse Linked List:");
        System.out.println("============================");
        
        // Test case 1: Normal case
        System.out.println("\nTest 1: Normal case [1,2,3,4,5]");
        int[] test1 = {1, 2, 3, 4, 5};
        ListNode head1 = solution.createList(test1);
        ListNode result1 = solution.reverseList(head1);
        System.out.println("Original: 1 -> 2 -> 3 -> 4 -> 5");
        System.out.println("Reversed: " + solution.listToString(result1));
        
        int[] expected1 = {5, 4, 3, 2, 1};
        ListNode expectedHead1 = solution.createList(expected1);
        boolean test1Pass = solution.compareLists(result1, expectedHead1);
        System.out.println("Test 1: " + (test1Pass ? "PASSED" : "FAILED"));
        
        // Test case 2: Two nodes
        System.out.println("\nTest 2: Two nodes [1,2]");
        int[] test2 = {1, 2};
        ListNode head2 = solution.createList(test2);
        ListNode result2 = solution.reverseList(head2);
        System.out.println("Original: 1 -> 2");
        System.out.println("Reversed: " + solution.listToString(result2));
        
        int[] expected2 = {2, 1};
        ListNode expectedHead2 = solution.createList(expected2);
        boolean test2Pass = solution.compareLists(result2, expectedHead2);
        System.out.println("Test 2: " + (test2Pass ? "PASSED" : "FAILED"));
        
        // Test case 3: Single node
        System.out.println("\nTest 3: Single node [1]");
        int[] test3 = {1};
        ListNode head3 = solution.createList(test3);
        ListNode result3 = solution.reverseList(head3);
        System.out.println("Original: 1");
        System.out.println("Reversed: " + solution.listToString(result3));
        
        boolean test3Pass = result3 != null && result3.val == 1 && result3.next == null;
        System.out.println("Test 3: " + (test3Pass ? "PASSED" : "FAILED"));
        
        // Test case 4: Empty list
        System.out.println("\nTest 4: Empty list []");
        ListNode head4 = null;
        ListNode result4 = solution.reverseList(head4);
        System.out.println("Original: null");
        System.out.println("Reversed: " + solution.listToString(result4));
        
        boolean test4Pass = result4 == null;
        System.out.println("Test 4: " + (test4Pass ? "PASSED" : "FAILED"));
        
        // Test case 5: Compare all approaches
        System.out.println("\nTest 5: Verify all approaches produce same result");
        int[] test5 = {1, 2, 3, 4, 5};
        
        ListNode head5a = solution.createList(test5);
        ListNode result5a = solution.reverseList(head5a);
        
        ListNode head5b = solution.createList(test5);
        ListNode result5b = solution.reverseListRecursive(head5b);
        
        ListNode head5c = solution.createList(test5);
        ListNode result5c = solution.reverseListStack(head5c);
        
        boolean allEqual = solution.compareLists(result5a, result5b) && 
                          solution.compareLists(result5a, result5c);
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
        
        // Visualization test
        System.out.println("\nTest 6: Step-by-step visualization");
        int[] test6 = {1, 2, 3};
        ListNode head6 = solution.createList(test6);
        solution.reverseListVisual(head6);
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison - Small List");
        int[] smallTest = new int[100];
        for (int i = 0; i < smallTest.length; i++) {
            smallTest[i] = i;
        }
        solution.compareApproaches(smallTest);
        
        System.out.println("\nTest 8: Performance Comparison - Large List");
        int[] largeTest = new int[5000];
        for (int i = 0; i < largeTest.length; i++) {
            largeTest[i] = i;
        }
        solution.compareApproaches(largeTest);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nIterative Approach (RECOMMENDED):");
        System.out.println("We use three pointers: prev, current, and next");
        System.out.println("At each step:");
        System.out.println("  1. Save next node: next = current.next");
        System.out.println("  2. Reverse link: current.next = prev");
        System.out.println("  3. Move prev to current: prev = current");
        System.out.println("  4. Move current to next: current = next");
        System.out.println("Visual example for [1->2->3->4->5]:");
        System.out.println("  Step 1: prev=null, current=1, next=2");
        System.out.println("           Reverse: 1->null");
        System.out.println("           Move: prev=1, current=2");
        System.out.println("  Step 2: prev=1, current=2, next=3");
        System.out.println("           Reverse: 2->1");
        System.out.println("           Move: prev=2, current=3");
        System.out.println("  Continue until current=null");
        System.out.println("  Final: prev=5 (new head)");
        
        System.out.println("\nRecursive Approach:");
        System.out.println("Base case: if head is null or single node, return head");
        System.out.println("Recursive case:");
        System.out.println("  1. Reverse the rest: newHead = reverseList(head.next)");
        System.out.println("  2. Adjust pointers: head.next.next = head");
        System.out.println("  3. Set head.next = null");
        System.out.println("  4. Return newHead");
        System.out.println("Visual example for [1->2->3]:");
        System.out.println("  reverseList(1):");
        System.out.println("    reverseList(2):");
        System.out.println("      reverseList(3): return 3");
        System.out.println("      2.next.next = 2 (3->2)");
        System.out.println("      2.next = null");
        System.out.println("    1.next.next = 1 (2->1)");
        System.out.println("    1.next = null");
        System.out.println("    Return 3");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nIterative Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Single pass through the list");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only constant extra space for pointers");
        
        System.out.println("\nRecursive Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Each node is processed once");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Recursion stack depth equals list length");
        
        System.out.println("\nStack Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Two passes through the list");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Stack stores all nodes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with iterative approach (most efficient)");
        System.out.println("2. Explain the three-pointer technique clearly");
        System.out.println("3. Handle edge cases: empty list, single node");
        System.out.println("4. Mention recursive approach as alternative");
        System.out.println("5. Discuss time and space complexity");
        System.out.println("6. Write clean code with good variable names");
        System.out.println("7. Consider drawing diagram to explain pointer movements");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("- Forgetting to handle empty list case");
        System.out.println("- Losing reference to next node before reversing");
        System.out.println("- Not setting the last node's next to null in stack approach");
        System.out.println("- Infinite recursion in recursive approach");
        
        System.out.println("\nAll tests completed!");
    }
}
