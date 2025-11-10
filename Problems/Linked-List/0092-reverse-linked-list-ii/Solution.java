
## Solution.java

```java
/**
 * 92. Reverse Linked List II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Reverse a linked list from position left to position right.
 * 
 * Key Insights:
 * 1. Use dummy node to handle case when reversing starts from head
 * 2. Find the node before the sublist (left-1 position)
 * 3. Reverse the sublist using standard reversal technique
 * 4. Connect the reversed sublist back to the main list
 * 5. Handle edge cases: left=1, right=n, left=right
 * 
 * Approach (Iterative with Dummy Node - RECOMMENDED):
 * 1. Create dummy node pointing to head
 * 2. Find node before sublist (prev)
 * 3. Reverse sublist from left to right
 * 4. Connect prev to new head of reversed sublist
 * 5. Connect tail of reversed sublist to node after sublist
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Linked List
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
     * Approach 1: Iterative with Dummy Node - RECOMMENDED
     * O(n) time, O(1) space - Most efficient and clean
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) {
            return head;
        }
        
        // Create dummy node to handle case when left = 1
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Step 1: Find the node before the sublist (prev)
        ListNode prev = dummy;
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }
        
        // Step 2: Reverse the sublist
        ListNode current = prev.next;
        ListNode next = null;
        ListNode reversedTail = current; // This will be the tail after reversal
        
        // Standard linked list reversal for the sublist
        ListNode newHead = null;
        for (int i = left; i <= right; i++) {
            next = current.next;
            current.next = newHead;
            newHead = current;
            current = next;
        }
        
        // Step 3: Connect the reversed sublist back to the main list
        prev.next = newHead;           // Connect prev to new head of reversed sublist
        reversedTail.next = current;   // Connect tail of reversed sublist to node after sublist
        
        return dummy.next;
    }
    
    /**
     * Approach 2: Iterative with Pointer Tracking
     * O(n) time, O(1) space - More explicit pointer tracking
     */
    public ListNode reverseBetweenExplicit(ListNode head, int left, int right) {
        if (head == null || left == right) {
            return head;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Find the node before the sublist
        ListNode beforeSublist = dummy;
        for (int i = 1; i < left; i++) {
            beforeSublist = beforeSublist.next;
        }
        
        // Start reversing from the first node of sublist
        ListNode sublistStart = beforeSublist.next;
        ListNode current = sublistStart;
        ListNode prev = null;
        
        // Reverse the sublist
        for (int i = left; i <= right; i++) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        // Connect the parts
        beforeSublist.next = prev;        // Connect before sublist to new head
        sublistStart.next = current;      // Connect original start (now tail) to remaining list
        
        return dummy.next;
    }
    
    /**
     * Approach 3: Recursive Approach
     * O(n) time, O(n) space - Elegant but uses recursion stack
     */
    public ListNode reverseBetweenRecursive(ListNode head, int left, int right) {
        if (left == 1) {
            return reverseFirstN(head, right);
        }
        
        head.next = reverseBetweenRecursive(head.next, left - 1, right - 1);
        return head;
    }
    
    private ListNode successor = null;
    
    private ListNode reverseFirstN(ListNode head, int n) {
        if (n == 1) {
            successor = head.next;
            return head;
        }
        
        ListNode last = reverseFirstN(head.next, n - 1);
        head.next.next = head;
        head.next = successor;
        
        return last;
    }
    
    /**
     * Approach 4: Stack-based Approach
     * O(n) time, O(n) space - Simple but uses extra space
     */
    public ListNode reverseBetweenStack(ListNode head, int left, int right) {
        if (head == null || left == right) {
            return head;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Find node before sublist
        ListNode beforeSublist = dummy;
        for (int i = 1; i < left; i++) {
            beforeSublist = beforeSublist.next;
        }
        
        // Push sublist nodes to stack
        Stack<ListNode> stack = new Stack<>();
        ListNode current = beforeSublist.next;
        for (int i = left; i <= right; i++) {
            stack.push(current);
            current = current.next;
        }
        
        // Pop from stack to reverse order
        ListNode afterSublist = current;
        current = beforeSublist;
        while (!stack.isEmpty()) {
            current.next = stack.pop();
            current = current.next;
        }
        
        // Connect to remaining list
        current.next = afterSublist;
        
        return dummy.next;
    }
    
    /**
     * Approach 5: In-place with Visualization
     * Same as Approach 1 but with step-by-step visualization
     */
    public ListNode reverseBetweenVisual(ListNode head, int left, int right) {
        System.out.println("Original list: " + listToString(head));
        System.out.println("Reverse from position " + left + " to " + right);
        
        if (head == null || left == right) {
            System.out.println("No reversal needed");
            return head;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Step 1: Find node before sublist
        System.out.println("\nStep 1: Find node before sublist (position " + (left-1) + ")");
        ListNode prev = dummy;
        for (int i = 1; i < left; i++) {
            prev = prev.next;
            System.out.println("  prev moves to node with value: " + prev.val);
        }
        System.out.println("Found node before sublist: " + prev.val);
        
        // Step 2: Reverse the sublist
        System.out.println("\nStep 2: Reverse sublist from position " + left + " to " + right);
        ListNode current = prev.next;
        ListNode next = null;
        ListNode reversedTail = current;
        ListNode newHead = null;
        
        System.out.println("Starting reversal with current = " + current.val);
        
        for (int i = left; i <= right; i++) {
            next = current.next;
            current.next = newHead;
            newHead = current;
            current = next;
            
            System.out.println("  After step " + (i - left + 1) + ":");
            System.out.println("    newHead = " + (newHead == null ? "null" : newHead.val));
            System.out.println("    current = " + (current == null ? "null" : current.val));
            System.out.println("    Reversed portion: " + listToString(newHead));
        }
        
        // Step 3: Connect the parts
        System.out.println("\nStep 3: Connect reversed sublist back to main list");
        System.out.println("  Connect prev (" + prev.val + ") -> newHead (" + newHead.val + ")");
        prev.next = newHead;
        
        System.out.println("  Connect reversedTail (" + reversedTail.val + ") -> current (" + 
                         (current == null ? "null" : current.val) + ")");
        reversedTail.next = current;
        
        System.out.println("\nFinal list: " + listToString(dummy.next));
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
    public void compareApproaches(int[] testData, int left, int right) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("List: " + java.util.Arrays.toString(testData));
        System.out.println("Left: " + left + ", Right: " + right);
        System.out.println("List length: " + testData.length);
        System.out.println("=================================");
        
        ListNode head = createList(testData);
        
        long startTime, endTime;
        ListNode result;
        
        // Iterative with dummy
        startTime = System.nanoTime();
        result = reverseBetween(head, left, right);
        endTime = System.nanoTime();
        System.out.printf("Iterative (dummy): %8d ns%n", (endTime - startTime));
        
        // Reset list
        head = createList(testData);
        
        // Explicit pointers
        startTime = System.nanoTime();
        result = reverseBetweenExplicit(head, left, right);
        endTime = System.nanoTime();
        System.out.printf("Explicit Pointers: %8d ns%n", (endTime - startTime));
        
        // Stack approach
        startTime = System.nanoTime();
        result = reverseBetweenStack(head, left, right);
        endTime = System.nanoTime();
        System.out.printf("Stack Approach:    %8d ns%n", (endTime - startTime));
        
        // Verify all produce same result
        boolean allEqual = compareLists(
            reverseBetween(createList(testData), left, right),
            reverseBetweenExplicit(createList(testData), left, right)
        ) && compareLists(
            reverseBetween(createList(testData), left, right),
            reverseBetweenStack(createList(testData), left, right)
        );
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Reverse Linked List II:");
        System.out.println("===============================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Reverse middle sublist [1,2,3,4,5], left=2, right=4");
        int[] test1 = {1, 2, 3, 4, 5};
        ListNode head1 = solution.createList(test1);
        ListNode result1 = solution.reverseBetween(head1, 2, 4);
        System.out.println("Original: 1 -> 2 -> 3 -> 4 -> 5");
        System.out.println("Expected: 1 -> 4 -> 3 -> 2 -> 5");
        System.out.println("Actual:   " + solution.listToString(result1));
        
        int[] expected1 = {1, 4, 3, 2, 5};
        ListNode expectedHead1 = solution.createList(expected1);
        boolean test1Pass = solution.compareLists(result1, expectedHead1);
        System.out.println("Test 1: " + (test1Pass ? "PASSED" : "FAILED"));
        
        // Test case 2: Reverse from head
        System.out.println("\nTest 2: Reverse from head [1,2,3,4,5], left=1, right=3");
        int[] test2 = {1, 2, 3, 4, 5};
        ListNode head2 = solution.createList(test2);
        ListNode result2 = solution.reverseBetween(head2, 1, 3);
        System.out.println("Original: 1 -> 2 -> 3 -> 4 -> 5");
        System.out.println("Expected: 3 -> 2 -> 1 -> 4 -> 5");
        System.out.println("Actual:   " + solution.listToString(result2));
        
        int[] expected2 = {3, 2, 1, 4, 5};
        ListNode expectedHead2 = solution.createList(expected2);
        boolean test2Pass = solution.compareLists(result2, expectedHead2);
        System.out.println("Test 2: " + (test2Pass ? "PASSED" : "FAILED"));
        
        // Test case 3: Reverse to tail
        System.out.println("\nTest 3: Reverse to tail [1,2,3,4,5], left=3, right=5");
        int[] test3 = {1, 2, 3, 4, 5};
        ListNode head3 = solution.createList(test3);
        ListNode result3 = solution.reverseBetween(head3, 3, 5);
        System.out.println("Original: 1 -> 2 -> 3 -> 4 -> 5");
        System.out.println("Expected: 1 -> 2 -> 5 -> 4 -> 3");
        System.out.println("Actual:   " + solution.listToString(result3));
        
        int[] expected3 = {1, 2, 5, 4, 3};
        ListNode expectedHead3 = solution.createList(expected3);
        boolean test3Pass = solution.compareLists(result3, expectedHead3);
        System.out.println("Test 3: " + (test3Pass ? "PASSED" : "FAILED"));
        
        // Test case 4: Single node reversal
        System.out.println("\nTest 4: Single node [5], left=1, right=1");
        int[] test4 = {5};
        ListNode head4 = solution.createList(test4);
        ListNode result4 = solution.reverseBetween(head4, 1, 1);
        System.out.println("Original: 5");
        System.out.println("Expected: 5");
        System.out.println("Actual:   " + solution.listToString(result4));
        
        boolean test4Pass = result4 != null && result4.val == 5 && result4.next == null;
        System.out.println("Test 4: " + (test4Pass ? "PASSED" : "FAILED"));
        
        // Test case 5: Two nodes, reverse both
        System.out.println("\nTest 5: Two nodes [1,2], left=1, right=2");
        int[] test5 = {1, 2};
        ListNode head5 = solution.createList(test5);
        ListNode result5 = solution.reverseBetween(head5, 1, 2);
        System.out.println("Original: 1 -> 2");
        System.out.println("Expected: 2 -> 1");
        System.out.println("Actual:   " + solution.listToString(result5));
        
        int[] expected5 = {2, 1};
        ListNode expectedHead5 = solution.createList(expected5);
        boolean test5Pass = solution.compareLists(result5, expectedHead5);
        System.out.println("Test 5: " + (test5Pass ? "PASSED" : "FAILED"));
        
        // Test case 6: Compare all approaches
        System.out.println("\nTest 6: Verify all approaches produce same result");
        int[] test6 = {1, 2, 3, 4, 5};
        
        ListNode head6a = solution.createList(test6);
        ListNode head6b = solution.createList(test6);
        ListNode head6c = solution.createList(test6);
        ListNode head6d = solution.createList(test6);
        
        ListNode result6a = solution.reverseBetween(head6a, 2, 4);
        ListNode result6b = solution.reverseBetweenExplicit(head6b, 2, 4);
        ListNode result6c = solution.reverseBetweenStack(head6c, 2, 4);
        ListNode result6d = solution.reverseBetweenRecursive(head6d, 2, 4);
        
        boolean allEqual = solution.compareLists(result6a, result6b) && 
                          solution.compareLists(result6a, result6c) &&
                          solution.compareLists(result6a, result6d);
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
        
        // Visualization test
        System.out.println("\nTest 7: Step-by-step visualization");
        int[] test7 = {1, 2, 3, 4, 5};
        ListNode head7 = solution.createList(test7);
        solution.reverseBetweenVisual(head7, 2, 4);
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison - Small List");
        int[] smallTest = new int[100];
        for (int i = 0; i < 100; i++) {
            smallTest[i] = i + 1;
        }
        solution.compareApproaches(smallTest, 25, 75);
        
        System.out.println("\nTest 9: Performance Comparison - Large List");
        int[] largeTest = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeTest[i] = i + 1;
        }
        solution.compareApproaches(largeTest, 200, 800);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nIterative with Dummy Node (RECOMMENDED):");
        System.out.println("Key Insight: Break the problem into three parts:");
        System.out.println("1. Find the node before the sublist (prev)");
        System.out.println("2. Reverse the sublist using standard reversal");
        System.out.println("3. Connect the reversed sublist back to main list");
        
        System.out.println("\nDetailed Steps:");
        System.out.println("Step 1: Create dummy node and find prev");
        System.out.println("  - Dummy node handles case when left = 1");
        System.out.println("  - Move prev pointer left-1 times");
        System.out.println("Step 2: Reverse sublist from left to right");
        System.out.println("  - Use standard three-pointer reversal");
        System.out.println("  - Track the original start (will be tail after reversal)");
        System.out.println("Step 3: Connect the parts");
        System.out.println("  - prev.next = new head of reversed sublist");
        System.out.println("  - original_start.next = node after sublist");
        
        System.out.println("\nExample: [1->2->3->4->5], left=2, right=4");
        System.out.println("Step 1: prev = node 1");
        System.out.println("Step 2: Reverse 2->3->4 to get 4->3->2");
        System.out.println("Step 3: Connect 1->4 and 2->5");
        System.out.println("Result: 1->4->3->2->5");
        
        System.out.println("\nWhy this approach works:");
        System.out.println("- Dummy node simplifies edge case when reversing from head");
        System.out.println("- Standard reversal technique is well-understood");
        System.out.println("- Clear pointer management prevents errors");
        System.out.println("- Handles all edge cases gracefully");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nIterative Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Single pass through the list");
        System.out.println("  - We traverse to find prev: O(left)");
        System.out.println("  - We reverse sublist: O(right-left)");
        System.out.println("  - Total: O(n)");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only constant extra space for pointers");
        
        System.out.println("\nRecursive Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Each node processed once");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Recursion stack depth O(n) in worst case");
        
        System.out.println("\nStack Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Two passes through sublist");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Stack stores sublist nodes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with iterative approach with dummy node");
        System.out.println("2. Explain the three-step process clearly:");
        System.out.println("   - Find node before sublist");
        System.out.println("   - Reverse the sublist");
        System.out.println("   - Connect the parts");
        System.out.println("3. Handle edge cases:");
        System.out.println("   - Reversing from head (left=1)");
        System.out.println("   - Reversing to tail (right=n)");
        System.out.println("   - Single node reversal");
        System.out.println("   - left = right (no reversal needed)");
        System.out.println("4. Mention alternative approaches");
        System.out.println("5. Discuss time and space complexity");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("- Not using dummy node for head reversal case");
        System.out.println("- Incorrect pointer connections after reversal");
        System.out.println("- Not tracking the original start of sublist");
        System.out.println("- Off-by-one errors in position counting");
        System.out.println("- Creating cycles in the list");
        
        System.out.println("\nAll tests completed!");
    }
}
