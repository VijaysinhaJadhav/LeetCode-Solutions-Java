
## Solution.java

```java
/**
 * 21. Merge Two Sorted Lists
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Merge two sorted linked lists and return it as a sorted list.
 * The list should be made by splicing together the nodes of the first two lists.
 * 
 * Key Insights:
 * 1. Use a dummy node to simplify edge cases and avoid special handling for head
 * 2. Compare nodes from both lists and attach the smaller one to the merged list
 * 3. When one list is exhausted, attach the remaining nodes from the other list
 * 4. Multiple approaches: iterative, recursive, in-place
 * 
 * Approach (Iterative with Dummy Node - RECOMMENDED):
 * 1. Create a dummy node and a current pointer
 * 2. While both lists have nodes:
 *    - Compare current nodes from both lists
 *    - Attach the smaller node to the merged list
 *    - Move the pointer of the chosen list forward
 * 3. Attach remaining nodes from the non-empty list
 * 4. Return dummy.next (the actual head)
 * 
 * Time Complexity: O(n + m)
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
     * Approach 1: Iterative with Dummy Node - RECOMMENDED
     * O(n + m) time, O(1) space - Most efficient and readable
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Create a dummy node to simplify edge cases
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        // Compare nodes from both lists and attach the smaller one
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        // Attach remaining nodes from the non-empty list
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }
        
        return dummy.next; // Skip the dummy node
    }
    
    /**
     * Approach 2: Recursive - Elegant but uses O(n + m) stack space
     * O(n + m) time, O(n + m) space due to recursion stack
     */
    public ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
        // Base cases
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        
        // Compare and recursively merge
        if (list1.val <= list2.val) {
            list1.next = mergeTwoListsRecursive(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsRecursive(list1, list2.next);
            return list2;
        }
    }
    
    /**
     * Approach 3: Iterative without Dummy Node
     * O(n + m) time, O(1) space - More complex edge case handling
     */
    public ListNode mergeTwoListsNoDummy(ListNode list1, ListNode list2) {
        // Handle empty lists
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        
        // Determine the head of the merged list
        ListNode head, current;
        if (list1.val <= list2.val) {
            head = list1;
            list1 = list1.next;
        } else {
            head = list2;
            list2 = list2.next;
        }
        current = head;
        
        // Merge the remaining nodes
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        // Attach remaining nodes
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }
        
        return head;
    }
    
    /**
     * Approach 4: In-place Merging (Modifies input lists)
     * O(n + m) time, O(1) space - Memory efficient but modifies input
     */
    public ListNode mergeTwoListsInPlace(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        
        // Ensure list1 starts with the smaller value
        if (list1.val > list2.val) {
            ListNode temp = list1;
            list1 = list2;
            list2 = temp;
        }
        
        ListNode head = list1;
        
        while (list1 != null && list2 != null) {
            ListNode prev = null;
            
            // Traverse list1 until we find a node larger than list2's current node
            while (list1 != null && list1.val <= list2.val) {
                prev = list1;
                list1 = list1.next;
            }
            
            // Insert list2's node between prev and list1
            prev.next = list2;
            
            // Swap list1 and list2 to continue
            ListNode temp = list1;
            list1 = list2;
            list2 = temp;
        }
        
        return head;
    }
    
    /**
     * Approach 5: Iterative with Detailed Visualization
     * Same as Approach 1 but with step-by-step visualization
     */
    public ListNode mergeTwoListsVisual(ListNode list1, ListNode list2) {
        System.out.println("Initial lists:");
        System.out.println("List1: " + listToString(list1));
        System.out.println("List2: " + listToString(list2));
        
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        int step = 1;
        
        while (list1 != null && list2 != null) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  Current merged: " + listToString(dummy.next));
            System.out.println("  List1 current: " + (list1 == null ? "null" : list1.val));
            System.out.println("  List2 current: " + (list2 == null ? "null" : list2.val));
            
            if (list1.val <= list2.val) {
                System.out.println("  Choose list1 node: " + list1.val);
                current.next = list1;
                list1 = list1.next;
            } else {
                System.out.println("  Choose list2 node: " + list2.val);
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
            step++;
        }
        
        // Attach remaining nodes
        if (list1 != null) {
            System.out.println("\nAttaching remaining list1: " + listToString(list1));
            current.next = list1;
        } else {
            System.out.println("\nAttaching remaining list2: " + listToString(list2));
            current.next = list2;
        }
        
        System.out.println("\nFinal merged list: " + listToString(dummy.next));
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
    public void compareApproaches(int[] list1Data, int[] list2Data) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("List1: " + java.util.Arrays.toString(list1Data));
        System.out.println("List2: " + java.util.Arrays.toString(list2Data));
        System.out.println("List1 length: " + list1Data.length);
        System.out.println("List2 length: " + list2Data.length);
        System.out.println("=================================");
        
        ListNode list1 = createList(list1Data);
        ListNode list2 = createList(list2Data);
        
        long startTime, endTime;
        ListNode result;
        
        // Iterative with dummy
        startTime = System.nanoTime();
        result = mergeTwoLists(list1, list2);
        endTime = System.nanoTime();
        System.out.printf("Iterative (dummy): %8d ns%n", (endTime - startTime));
        
        // Reset lists
        list1 = createList(list1Data);
        list2 = createList(list2Data);
        
        // Recursive
        startTime = System.nanoTime();
        result = mergeTwoListsRecursive(list1, list2);
        endTime = System.nanoTime();
        System.out.printf("Recursive:         %8d ns%n", (endTime - startTime));
        
        // Reset lists
        list1 = createList(list1Data);
        list2 = createList(list2Data);
        
        // Iterative without dummy
        startTime = System.nanoTime();
        result = mergeTwoListsNoDummy(list1, list2);
        endTime = System.nanoTime();
        System.out.printf("Iterative (no dummy):%6d ns%n", (endTime - startTime));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Merge Two Sorted Lists:");
        System.out.println("===============================");
        
        // Test case 1: Normal case
        System.out.println("\nTest 1: Normal case");
        int[] list1Data1 = {1, 2, 4};
        int[] list2Data1 = {1, 3, 4};
        ListNode list1_1 = solution.createList(list1Data1);
        ListNode list2_1 = solution.createList(list2Data1);
        
        ListNode result1 = solution.mergeTwoLists(list1_1, list2_1);
        System.out.println("List1: 1 -> 2 -> 4");
        System.out.println("List2: 1 -> 3 -> 4");
        System.out.println("Merged: " + solution.listToString(result1));
        
        int[] expected1 = {1, 1, 2, 3, 4, 4};
        ListNode expected1List = solution.createList(expected1);
        boolean test1Pass = solution.compareLists(result1, expected1List);
        System.out.println("Test 1: " + (test1Pass ? "PASSED" : "FAILED"));
        
        // Test case 2: One empty list
        System.out.println("\nTest 2: One empty list");
        int[] list1Data2 = {};
        int[] list2Data2 = {0};
        ListNode list1_2 = solution.createList(list1Data2);
        ListNode list2_2 = solution.createList(list2Data2);
        
        ListNode result2 = solution.mergeTwoLists(list1_2, list2_2);
        System.out.println("List1: null");
        System.out.println("List2: 0");
        System.out.println("Merged: " + solution.listToString(result2));
        
        boolean test2Pass = result2 != null && result2.val == 0 && result2.next == null;
        System.out.println("Test 2: " + (test2Pass ? "PASSED" : "FAILED"));
        
        // Test case 3: Both empty lists
        System.out.println("\nTest 3: Both empty lists");
        int[] list1Data3 = {};
        int[] list2Data3 = {};
        ListNode list1_3 = solution.createList(list1Data3);
        ListNode list2_3 = solution.createList(list2Data3);
        
        ListNode result3 = solution.mergeTwoLists(list1_3, list2_3);
        System.out.println("List1: null");
        System.out.println("List2: null");
        System.out.println("Merged: " + solution.listToString(result3));
        
        boolean test3Pass = result3 == null;
        System.out.println("Test 3: " + (test3Pass ? "PASSED" : "FAILED"));
        
        // Test case 4: Lists of different lengths
        System.out.println("\nTest 4: Lists of different lengths");
        int[] list1Data4 = {1, 5, 9};
        int[] list2Data4 = {2, 3, 4, 6, 7, 8};
        ListNode list1_4 = solution.createList(list1Data4);
        ListNode list2_4 = solution.createList(list2Data4);
        
        ListNode result4 = solution.mergeTwoLists(list1_4, list2_4);
        System.out.println("List1: 1 -> 5 -> 9");
        System.out.println("List2: 2 -> 3 -> 4 -> 6 -> 7 -> 8");
        System.out.println("Merged: " + solution.listToString(result4));
        
        int[] expected4 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ListNode expected4List = solution.createList(expected4);
        boolean test4Pass = solution.compareLists(result4, expected4List);
        System.out.println("Test 4: " + (test4Pass ? "PASSED" : "FAILED"));
        
        // Test case 5: Compare all approaches
        System.out.println("\nTest 5: Verify all approaches produce same result");
        int[] list1Data5 = {1, 3, 5};
        int[] list2Data5 = {2, 4, 6};
        
        ListNode list1_5a = solution.createList(list1Data5);
        ListNode list2_5a = solution.createList(list2Data5);
        ListNode result5a = solution.mergeTwoLists(list1_5a, list2_5a);
        
        ListNode list1_5b = solution.createList(list1Data5);
        ListNode list2_5b = solution.createList(list2Data5);
        ListNode result5b = solution.mergeTwoListsRecursive(list1_5b, list2_5b);
        
        ListNode list1_5c = solution.createList(list1Data5);
        ListNode list2_5c = solution.createList(list2Data5);
        ListNode result5c = solution.mergeTwoListsNoDummy(list1_5c, list2_5c);
        
        boolean allEqual = solution.compareLists(result5a, result5b) && 
                          solution.compareLists(result5a, result5c);
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
        
        // Visualization test
        System.out.println("\nTest 6: Step-by-step visualization");
        int[] list1Data6 = {1, 3, 5};
        int[] list2Data6 = {2, 4};
        ListNode list1_6 = solution.createList(list1Data6);
        ListNode list2_6 = solution.createList(list2Data6);
        solution.mergeTwoListsVisual(list1_6, list2_6);
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison - Small Lists");
        int[] smallList1 = {1, 3, 5, 7, 9};
        int[] smallList2 = {2, 4, 6, 8, 10};
        solution.compareApproaches(smallList1, smallList2);
        
        System.out.println("\nTest 8: Performance Comparison - Large Lists");
        int[] largeList1 = new int[1000];
        int[] largeList2 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeList1[i] = i * 2;       // Even numbers
            largeList2[i] = i * 2 + 1;   // Odd numbers
        }
        solution.compareApproaches(largeList1, largeList2);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nIterative Approach with Dummy Node (RECOMMENDED):");
        System.out.println("1. Create a dummy node to simplify edge cases");
        System.out.println("2. Use a current pointer to build the merged list");
        System.out.println("3. While both lists have nodes:");
        System.out.println("   - Compare current nodes from both lists");
        System.out.println("   - Attach the smaller node to the merged list");
        System.out.println("   - Move the pointer of the chosen list forward");
        System.out.println("4. Attach remaining nodes from the non-empty list");
        System.out.println("5. Return dummy.next (skip the dummy node)");
        
        System.out.println("\nVisual example for [1->3->5] and [2->4]:");
        System.out.println("Step 1: Compare 1 and 2 -> attach 1, move list1");
        System.out.println("Step 2: Compare 3 and 2 -> attach 2, move list2");
        System.out.println("Step 3: Compare 3 and 4 -> attach 3, move list1");
        System.out.println("Step 4: Compare 5 and 4 -> attach 4, move list2");
        System.out.println("Step 5: List2 exhausted -> attach remaining list1 (5)");
        System.out.println("Result: 1->2->3->4->5");
        
        System.out.println("\nRecursive Approach:");
        System.out.println("Base cases:");
        System.out.println("  - If list1 is null, return list2");
        System.out.println("  - If list2 is null, return list1");
        System.out.println("Recursive case:");
        System.out.println("  - If list1.val <= list2.val:");
        System.out.println("      list1.next = merge(list1.next, list2)");
        System.out.println("      return list1");
        System.out.println("  - Else:");
        System.out.println("      list2.next = merge(list1, list2.next)");
        System.out.println("      return list2");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nIterative Approach:");
        System.out.println("Time Complexity: O(n + m)");
        System.out.println("  - We visit each node exactly once");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only constant extra space for pointers");
        
        System.out.println("\nRecursive Approach:");
        System.out.println("Time Complexity: O(n + m)");
        System.out.println("  - Each node is processed once");
        System.out.println("Space Complexity: O(n + m)");
        System.out.println("  - Recursion stack depth equals total nodes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with iterative approach with dummy node (most practical)");
        System.out.println("2. Explain why dummy node simplifies edge cases");
        System.out.println("3. Handle all edge cases: empty lists, different lengths");
        System.out.println("4. Mention recursive approach as alternative");
        System.out.println("5. Discuss time and space complexity");
        System.out.println("6. Consider drawing diagram to explain pointer movements");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("- Forgetting to handle empty list cases");
        System.out.println("- Not moving pointers correctly after attaching nodes");
        System.out.println("- Forgetting to attach remaining nodes at the end");
        System.out.println("- Stack overflow in recursive approach for long lists");
        
        System.out.println("\nAll tests completed!");
    }
}
