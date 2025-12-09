
## Solution.java

```java
/**
 * 143. Reorder List
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given the head of a singly linked list, reorder it to:
 * L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → …
 * 
 * Key Insights:
 * 1. Three-step approach: find middle, reverse second half, merge alternately
 * 2. Use slow/fast pointers to find middle efficiently
 * 3. Reverse the second half in-place
 * 4. Merge two halves by alternating nodes
 * 5. Must handle even and odd length lists
 * 
 * Approach (Three-Step In-place - RECOMMENDED):
 * 1. Find middle using slow/fast pointers
 * 2. Reverse the second half of the list
 * 3. Merge first half and reversed second half alternately
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Linked List, Two Pointers, Stack
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
     * Approach 1: Three-Step In-place Reordering - RECOMMENDED
     * O(n) time, O(1) space - Most efficient
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return; // No reordering needed for 0, 1, or 2 nodes
        }
        
        // Step 1: Find the middle of the list
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Step 2: Reverse the second half
        ListNode secondHalf = reverseList(slow.next);
        slow.next = null; // Split the list into two halves
        
        // Step 3: Merge the two halves alternately
        mergeAlternate(head, secondHalf);
    }
    
    /**
     * Helper method to reverse a linked list
     */
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        return prev;
    }
    
    /**
     * Helper method to merge two lists alternately
     */
    private void mergeAlternate(ListNode first, ListNode second) {
        while (second != null) {
            ListNode firstNext = first.next;
            ListNode secondNext = second.next;
            
            first.next = second;
            second.next = firstNext;
            
            first = firstNext;
            second = secondNext;
        }
    }
    
    /**
     * Approach 2: Using Stack
     * O(n) time, O(n) space - Simple but uses extra space
     */
    public void reorderListStack(ListNode head) {
        if (head == null || head.next == null) return;
        
        Stack<ListNode> stack = new Stack<>();
        ListNode current = head;
        
        // Push all nodes to stack
        while (current != null) {
            stack.push(current);
            current = current.next;
        }
        
        current = head;
        int size = stack.size();
        
        // Reorder by alternating between beginning and end
        for (int i = 0; i < size / 2; i++) {
            ListNode end = stack.pop();
            ListNode next = current.next;
            
            current.next = end;
            end.next = next;
            current = next;
        }
        
        // Set the last node's next to null
        if (current != null) {
            current.next = null;
        }
    }
    
    /**
     * Approach 3: Recursive (Inefficient for large lists)
     * O(n²) time, O(n) space - Not recommended for large lists
     */
    public void reorderListRecursive(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        
        // Find the second last and last nodes
        ListNode prev = head;
        while (prev.next.next != null) {
            prev = prev.next;
        }
        
        // Move last node after head
        ListNode last = prev.next;
        prev.next = null;
        
        ListNode next = head.next;
        head.next = last;
        last.next = next;
        
        // Recursively reorder the remaining list
        reorderListRecursive(next);
    }
    
    /**
     * Approach 4: Three-Step with Detailed Visualization
     * Same as Approach 1 but with step-by-step visualization
     */
    public void reorderListVisual(ListNode head) {
        System.out.println("Initial list: " + listToString(head));
        
        if (head == null || head.next == null || head.next.next == null) {
            System.out.println("No reordering needed");
            return;
        }
        
        // Step 1: Find middle
        System.out.println("\nStep 1: Find middle using slow/fast pointers");
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        System.out.println("Middle node: " + slow.val);
        
        // Step 2: Reverse second half
        System.out.println("\nStep 2: Reverse second half");
        ListNode secondHalf = reverseListVisual(slow.next);
        slow.next = null; // Split the list
        
        System.out.println("First half: " + listToString(head));
        System.out.println("Reversed second half: " + listToString(secondHalf));
        
        // Step 3: Merge alternately
        System.out.println("\nStep 3: Merge alternately");
        mergeAlternateVisual(head, secondHalf);
        
        System.out.println("Final reordered list: " + listToString(head));
    }
    
    private ListNode reverseListVisual(ListNode head) {
        System.out.println("Reversing: " + listToString(head));
        ListNode prev = null;
        ListNode current = head;
        int step = 1;
        
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
            System.out.println("  Step " + step + ": " + listToString(prev));
            step++;
        }
        
        return prev;
    }
    
    private void mergeAlternateVisual(ListNode first, ListNode second) {
        ListNode firstPtr = first;
        ListNode secondPtr = second;
        int step = 1;
        
        while (secondPtr != null) {
            ListNode firstNext = firstPtr.next;
            ListNode secondNext = secondPtr.next;
            
            firstPtr.next = secondPtr;
            secondPtr.next = firstNext;
            
            System.out.println("  Step " + step + ":");
            System.out.println("    Connect " + firstPtr.val + " -> " + secondPtr.val);
            System.out.println("    Connect " + secondPtr.val + " -> " + 
                             (firstNext == null ? "null" : firstNext.val));
            System.out.println("    Current list: " + listToString(first));
            
            firstPtr = firstNext;
            secondPtr = secondNext;
            step++;
        }
    }
    
    /**
     * Approach 5: Using ArrayList for Index Access
     * O(n) time, O(n) space - Simple but uses extra space
     */
    public void reorderListArrayList(ListNode head) {
        if (head == null) return;
        
        List<ListNode> nodes = new ArrayList<>();
        ListNode current = head;
        
        // Store all nodes in ArrayList
        while (current != null) {
            nodes.add(current);
            current = current.next;
        }
        
        int left = 0;
        int right = nodes.size() - 1;
        
        // Reorder using two pointers
        while (left < right) {
            nodes.get(left).next = nodes.get(right);
            left++;
            
            if (left == right) break;
            
            nodes.get(right).next = nodes.get(left);
            right--;
        }
        
        // Set the last node's next to null
        nodes.get(left).next = null;
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
     * Helper method to get list as array for verification
     */
    private int[] listToArray(ListNode head) {
        List<Integer> result = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            result.add(current.val);
            current = current.next;
        }
        return result.stream().mapToInt(i -> i).toArray();
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] testData) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Test data: " + java.util.Arrays.toString(testData));
        System.out.println("List length: " + testData.length);
        System.out.println("=================================");
        
        long startTime, endTime;
        
        // Three-step in-place approach
        ListNode head1 = createList(testData);
        startTime = System.nanoTime();
        reorderList(head1);
        endTime = System.nanoTime();
        System.out.printf("Three-Step In-place: %8d ns%n", (endTime - startTime));
        
        // Stack approach
        ListNode head2 = createList(testData);
        startTime = System.nanoTime();
        reorderListStack(head2);
        endTime = System.nanoTime();
        System.out.printf("Stack Approach:      %8d ns%n", (endTime - startTime));
        
        // ArrayList approach
        ListNode head3 = createList(testData);
        startTime = System.nanoTime();
        reorderListArrayList(head3);
        endTime = System.nanoTime();
        System.out.printf("ArrayList Approach:  %8d ns%n", (endTime - startTime));
        
        // Verify all produce same result
        boolean allEqual = compareLists(head1, head2) && compareLists(head1, head3);
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Reorder List:");
        System.out.println("=====================");
        
        // Test case 1: Even length list
        System.out.println("\nTest 1: Even length list [1,2,3,4]");
        int[] test1 = {1, 2, 3, 4};
        ListNode head1 = solution.createList(test1);
        System.out.println("Original: " + solution.listToString(head1));
        
        solution.reorderList(head1);
        System.out.println("Reordered: " + solution.listToString(head1));
        
        int[] expected1 = {1, 4, 2, 3};
        ListNode expectedHead1 = solution.createList(expected1);
        boolean test1Pass = solution.compareLists(head1, expectedHead1);
        System.out.println("Test 1: " + (test1Pass ? "PASSED" : "FAILED"));
        
        // Test case 2: Odd length list
        System.out.println("\nTest 2: Odd length list [1,2,3,4,5]");
        int[] test2 = {1, 2, 3, 4, 5};
        ListNode head2 = solution.createList(test2);
        System.out.println("Original: " + solution.listToString(head2));
        
        solution.reorderList(head2);
        System.out.println("Reordered: " + solution.listToString(head2));
        
        int[] expected2 = {1, 5, 2, 4, 3};
        ListNode expectedHead2 = solution.createList(expected2);
        boolean test2Pass = solution.compareLists(head2, expectedHead2);
        System.out.println("Test 2: " + (test2Pass ? "PASSED" : "FAILED"));
        
        // Test case 3: Two nodes
        System.out.println("\nTest 3: Two nodes [1,2]");
        int[] test3 = {1, 2};
        ListNode head3 = solution.createList(test3);
        System.out.println("Original: " + solution.listToString(head3));
        
        solution.reorderList(head3);
        System.out.println("Reordered: " + solution.listToString(head3));
        
        int[] expected3 = {1, 2};
        ListNode expectedHead3 = solution.createList(expected3);
        boolean test3Pass = solution.compareLists(head3, expectedHead3);
        System.out.println("Test 3: " + (test3Pass ? "PASSED" : "FAILED"));
        
        // Test case 4: Single node
        System.out.println("\nTest 4: Single node [1]");
        int[] test4 = {1};
        ListNode head4 = solution.createList(test4);
        System.out.println("Original: " + solution.listToString(head4));
        
        solution.reorderList(head4);
        System.out.println("Reordered: " + solution.listToString(head4));
        
        boolean test4Pass = head4 != null && head4.val == 1 && head4.next == null;
        System.out.println("Test 4: " + (test4Pass ? "PASSED" : "FAILED"));
        
        // Test case 5: Compare all approaches
        System.out.println("\nTest 5: Verify all approaches produce same result");
        int[] test5 = {1, 2, 3, 4, 5, 6};
        
        ListNode head5a = solution.createList(test5);
        ListNode head5b = solution.createList(test5);
        ListNode head5c = solution.createList(test5);
        
        solution.reorderList(head5a);
        solution.reorderListStack(head5b);
        solution.reorderListArrayList(head5c);
        
        boolean allEqual = solution.compareLists(head5a, head5b) && 
                          solution.compareLists(head5a, head5c);
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
        
        // Visualization test
        System.out.println("\nTest 6: Step-by-step visualization");
        int[] test6 = {1, 2, 3, 4, 5};
        ListNode head6 = solution.createList(test6);
        solution.reorderListVisual(head6);
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison - Small List");
        int[] smallTest = new int[100];
        for (int i = 0; i < smallTest.length; i++) {
            smallTest[i] = i + 1;
        }
        solution.compareApproaches(smallTest);
        
        System.out.println("\nTest 8: Performance Comparison - Large List");
        int[] largeTest = new int[5000];
        for (int i = 0; i < largeTest.length; i++) {
            largeTest[i] = i + 1;
        }
        solution.compareApproaches(largeTest);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nThree-Step In-place Approach (RECOMMENDED):");
        System.out.println("Step 1: Find Middle using Slow/Fast Pointers");
        System.out.println("  - Slow moves 1 step, fast moves 2 steps");
        System.out.println("  - When fast reaches end, slow is at middle");
        System.out.println("  - Example: [1->2->3->4->5], slow stops at 3");
        
        System.out.println("\nStep 2: Reverse Second Half");
        System.out.println("  - Reverse from slow.next to end");
        System.out.println("  - Split list: first = [1->2->3], second = [5->4]");
        System.out.println("  - Standard linked list reversal");
        
        System.out.println("\nStep 3: Merge Alternately");
        System.out.println("  - Take one node from first half, one from second half");
        System.out.println("  - Connect them alternately");
        System.out.println("  - 1->5->2->4->3");
        
        System.out.println("\nWhy this works:");
        System.out.println("1. Finding middle splits list into two equal parts");
        System.out.println("2. Reversing second half gives us access from end");
        System.out.println("3. Alternating merge creates the required pattern");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nThree-Step In-place Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Find middle: O(n)");
        System.out.println("  - Reverse second half: O(n)");
        System.out.println("  - Merge: O(n)");
        System.out.println("  - Total: O(n)");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only constant extra space for pointers");
        
        System.out.println("\nStack Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Push all nodes: O(n)");
        System.out.println("  - Pop and reorder: O(n)");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Stack stores all nodes");
        
        System.out.println("\nArrayList Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Store nodes: O(n)");
        System.out.println("  - Reorder with two pointers: O(n)");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - ArrayList stores all nodes");
        
        System.out.println("\nRecursive Approach:");
        System.out.println("Time Complexity: O(n²)");
        System.out.println("  - Each recursive call processes one pair");
        System.out.println("  - Finding last node takes O(n) each time");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Recursion stack depth O(n)");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with three-step in-place approach");
        System.out.println("2. Explain each step clearly:");
        System.out.println("   - Find middle (slow/fast pointers)");
        System.out.println("   - Reverse second half");
        System.out.println("   - Merge alternately");
        System.out.println("3. Handle edge cases: 0, 1, or 2 nodes");
        System.out.println("4. Mention alternative approaches (stack, arraylist)");
        System.out.println("5. Discuss time and space complexity");
        System.out.println("6. Consider drawing diagrams for each step");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("- Not splitting the list correctly at middle");
        System.out.println("- Forgetting to set slow.next = null after finding middle");
        System.out.println("- Incorrect pointer manipulation during merge");
        System.out.println("- Not handling odd/even length lists correctly");
        System.out.println("- Creating cycles in the list during reordering");
        
        System.out.println("\nAll tests completed!");
    }
}
