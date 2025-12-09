
## Solution.java

```java
/**
 * 2. Add Two Numbers
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order, and each node contains a single digit.
 * Add the two numbers and return the sum as a linked list.
 * 
 * Key Insights:
 * 1. Similar to elementary addition: add digits from right to left
 * 2. Handle carry-over between digit additions
 * 3. Process until both lists are exhausted and carry is 0
 * 4. Use dummy node to simplify head management
 * 5. Handle different list lengths by treating null as 0
 * 
 * Approach (Iterative with Dummy Node - RECOMMENDED):
 * 1. Initialize dummy node, current pointer, and carry = 0
 * 2. While l1 != null OR l2 != null OR carry != 0:
 *    - Get values from l1 and l2 (0 if null)
 *    - Calculate sum = val1 + val2 + carry
 *    - Create new node with sum % 10
 *    - Update carry = sum / 10
 *    - Move pointers forward
 * 3. Return dummy.next
 * 
 * Time Complexity: O(max(m, n))
 * Space Complexity: O(max(m, n))
 * 
 * Tags: Linked List, Math, Recursion
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
     * O(max(m, n)) time, O(max(m, n)) space - Most efficient and clean
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int carry = 0;
        
        while (l1 != null || l2 != null || carry != 0) {
            // Get values from current nodes (0 if node is null)
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;
            
            // Calculate sum and carry
            int sum = val1 + val2 + carry;
            carry = sum / 10;
            int digit = sum % 10;
            
            // Create new node and move current pointer
            current.next = new ListNode(digit);
            current = current.next;
            
            // Move list pointers forward if not null
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 2: Iterative without Dummy Node
     * O(max(m, n)) time, O(max(m, n)) space - More complex edge case handling
     */
    public ListNode addTwoNumbersNoDummy(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        
        ListNode head = null;
        ListNode current = null;
        int carry = 0;
        
        while (l1 != null || l2 != null || carry != 0) {
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;
            
            int sum = val1 + val2 + carry;
            carry = sum / 10;
            int digit = sum % 10;
            
            ListNode newNode = new ListNode(digit);
            
            if (head == null) {
                head = newNode;
                current = head;
            } else {
                current.next = newNode;
                current = current.next;
            }
            
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        return head;
    }
    
    /**
     * Approach 3: Recursive
     * O(max(m, n)) time, O(max(m, n)) space - Elegant but uses recursion stack
     */
    public ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2) {
        return addTwoNumbersRecursive(l1, l2, 0);
    }
    
    private ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2, int carry) {
        // Base case: both lists null and no carry
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }
        
        int val1 = (l1 != null) ? l1.val : 0;
        int val2 = (l2 != null) ? l2.val : 0;
        
        int sum = val1 + val2 + carry;
        int digit = sum % 10;
        int newCarry = sum / 10;
        
        ListNode result = new ListNode(digit);
        
        ListNode next1 = (l1 != null) ? l1.next : null;
        ListNode next2 = (l2 != null) ? l2.next : null;
        
        result.next = addTwoNumbersRecursive(next1, next2, newCarry);
        
        return result;
    }
    
    /**
     * Approach 4: Convert to Numbers then Back (Not Recommended)
     * O(m + n) time, O(max(m, n)) space - Risk of integer overflow
     */
    public ListNode addTwoNumbersConvert(ListNode l1, ListNode l2) {
        long num1 = listToNumber(l1);
        long num2 = listToNumber(l2);
        long sum = num1 + num2;
        
        return numberToList(sum);
    }
    
    private long listToNumber(ListNode head) {
        long num = 0;
        long multiplier = 1;
        ListNode current = head;
        
        while (current != null) {
            num += current.val * multiplier;
            multiplier *= 10;
            current = current.next;
        }
        
        return num;
    }
    
    private ListNode numberToList(long num) {
        if (num == 0) {
            return new ListNode(0);
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (num > 0) {
            int digit = (int)(num % 10);
            current.next = new ListNode(digit);
            current = current.next;
            num /= 10;
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 5: Iterative with Visualization
     * Same as Approach 1 but with step-by-step visualization
     */
    public ListNode addTwoNumbersVisual(ListNode l1, ListNode l2) {
        System.out.println("Adding two numbers:");
        System.out.println("List 1: " + listToString(l1));
        System.out.println("List 2: " + listToString(l2));
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int carry = 0;
        int step = 1;
        
        while (l1 != null || l2 != null || carry != 0) {
            System.out.println("\nStep " + step + ":");
            
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;
            
            System.out.println("  Values: " + val1 + " + " + val2 + " + carry(" + carry + ")");
            
            int sum = val1 + val2 + carry;
            carry = sum / 10;
            int digit = sum % 10;
            
            System.out.println("  Sum: " + sum + ", Digit: " + digit + ", New Carry: " + carry);
            
            current.next = new ListNode(digit);
            current = current.next;
            
            System.out.println("  Current result: " + listToString(dummy.next));
            
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
            step++;
        }
        
        System.out.println("\nFinal result: " + listToString(dummy.next));
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
     * Helper method to get list as number (for verification)
     */
    private long listToLong(ListNode head) {
        long num = 0;
        long multiplier = 1;
        ListNode current = head;
        
        while (current != null) {
            num += current.val * multiplier;
            multiplier *= 10;
            current = current.next;
        }
        
        return num;
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] list1Data, int[] list2Data) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("List 1: " + java.util.Arrays.toString(list1Data));
        System.out.println("List 2: " + java.util.Arrays.toString(list2Data));
        System.out.println("List 1 as number: " + listToLong(createList(list1Data)));
        System.out.println("List 2 as number: " + listToLong(createList(list2Data)));
        System.out.println("=================================");
        
        ListNode l1 = createList(list1Data);
        ListNode l2 = createList(list2Data);
        
        long startTime, endTime;
        ListNode result;
        
        // Iterative with dummy
        startTime = System.nanoTime();
        result = addTwoNumbers(l1, l2);
        endTime = System.nanoTime();
        System.out.printf("Iterative (dummy): %8d ns%n", (endTime - startTime));
        
        // Reset lists
        l1 = createList(list1Data);
        l2 = createList(list2Data);
        
        // Recursive
        startTime = System.nanoTime();
        result = addTwoNumbersRecursive(l1, l2);
        endTime = System.nanoTime();
        System.out.printf("Recursive:         %8d ns%n", (endTime - startTime));
        
        // Verify both produce same result
        boolean sameResult = compareLists(
            addTwoNumbers(createList(list1Data), createList(list2Data)),
            addTwoNumbersRecursive(createList(list1Data), createList(list2Data))
        );
        System.out.println("Both approaches produce same result: " + (sameResult ? "PASSED" : "FAILED"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Add Two Numbers:");
        System.out.println("========================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: 342 + 465 = 807");
        int[] list1Data1 = {2, 4, 3}; // 342
        int[] list2Data1 = {5, 6, 4}; // 465
        ListNode l1_1 = solution.createList(list1Data1);
        ListNode l2_1 = solution.createList(list2Data1);
        
        ListNode result1 = solution.addTwoNumbers(l1_1, l2_1);
        System.out.println("List 1: " + solution.listToString(l1_1) + " (342)");
        System.out.println("List 2: " + solution.listToString(l2_1) + " (465)");
        System.out.println("Result: " + solution.listToString(result1) + " (807)");
        
        int[] expected1 = {7, 0, 8}; // 807
        ListNode expected1List = solution.createList(expected1);
        boolean test1Pass = solution.compareLists(result1, expected1List);
        System.out.println("Test 1: " + (test1Pass ? "PASSED" : "FAILED"));
        
        // Test case 2: Different lengths
        System.out.println("\nTest 2: 999 + 1 = 1000");
        int[] list1Data2 = {9, 9, 9}; // 999
        int[] list2Data2 = {1};       // 1
        ListNode l1_2 = solution.createList(list1Data2);
        ListNode l2_2 = solution.createList(list2Data2);
        
        ListNode result2 = solution.addTwoNumbers(l1_2, l2_2);
        System.out.println("List 1: " + solution.listToString(l1_2) + " (999)");
        System.out.println("List 2: " + solution.listToString(l2_2) + " (1)");
        System.out.println("Result: " + solution.listToString(result2) + " (1000)");
        
        int[] expected2 = {0, 0, 0, 1}; // 1000
        ListNode expected2List = solution.createList(expected2);
        boolean test2Pass = solution.compareLists(result2, expected2List);
        System.out.println("Test 2: " + (test2Pass ? "PASSED" : "FAILED"));
        
        // Test case 3: Zeros
        System.out.println("\nTest 3: 0 + 0 = 0");
        int[] list1Data3 = {0};
        int[] list2Data3 = {0};
        ListNode l1_3 = solution.createList(list1Data3);
        ListNode l2_3 = solution.createList(list2Data3);
        
        ListNode result3 = solution.addTwoNumbers(l1_3, l2_3);
        System.out.println("List 1: " + solution.listToString(l1_3) + " (0)");
        System.out.println("List 2: " + solution.listToString(l2_3) + " (0)");
        System.out.println("Result: " + solution.listToString(result3) + " (0)");
        
        boolean test3Pass = result3 != null && result3.val == 0 && result3.next == null;
        System.out.println("Test 3: " + (test3Pass ? "PASSED" : "FAILED"));
        
        // Test case 4: Large numbers with carry
        System.out.println("\nTest 4: 9999999 + 9999 = 10009998");
        int[] list1Data4 = {9, 9, 9, 9, 9, 9, 9}; // 9999999
        int[] list2Data4 = {9, 9, 9, 9};          // 9999
        ListNode l1_4 = solution.createList(list1Data4);
        ListNode l2_4 = solution.createList(list2Data4);
        
        ListNode result4 = solution.addTwoNumbers(l1_4, l2_4);
        System.out.println("List 1: " + solution.listToString(l1_4) + " (9999999)");
        System.out.println("List 2: " + solution.listToString(l2_4) + " (9999)");
        System.out.println("Result: " + solution.listToString(result4) + " (10009998)");
        
        int[] expected4 = {8, 9, 9, 9, 0, 0, 0, 1}; // 10009998
        ListNode expected4List = solution.createList(expected4);
        boolean test4Pass = solution.compareLists(result4, expected4List);
        System.out.println("Test 4: " + (test4Pass ? "PASSED" : "FAILED"));
        
        // Test case 5: Compare all approaches
        System.out.println("\nTest 5: Verify all approaches produce same result");
        int[] list1Data5 = {2, 4, 3};
        int[] list2Data5 = {5, 6, 4};
        
        ListNode l1_5a = solution.createList(list1Data5);
        ListNode l2_5a = solution.createList(list2Data5);
        ListNode result5a = solution.addTwoNumbers(l1_5a, l2_5a);
        
        ListNode l1_5b = solution.createList(list1Data5);
        ListNode l2_5b = solution.createList(list2Data5);
        ListNode result5b = solution.addTwoNumbersRecursive(l1_5b, l2_5b);
        
        ListNode l1_5c = solution.createList(list1Data5);
        ListNode l2_5c = solution.createList(list2Data5);
        ListNode result5c = solution.addTwoNumbersNoDummy(l1_5c, l2_5c);
        
        boolean allEqual = solution.compareLists(result5a, result5b) && 
                          solution.compareLists(result5a, result5c);
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
        
        // Visualization test
        System.out.println("\nTest 6: Step-by-step visualization");
        int[] list1Data6 = {9, 9, 9};
        int[] list2Data6 = {1};
        ListNode l1_6 = solution.createList(list1Data6);
        ListNode l2_6 = solution.createList(list2Data6);
        solution.addTwoNumbersVisual(l1_6, l2_6);
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison - Small Numbers");
        int[] smallList1 = {1, 2, 3, 4, 5};
        int[] smallList2 = {6, 7, 8, 9};
        solution.compareApproaches(smallList1, smallList2);
        
        System.out.println("\nTest 8: Performance Comparison - Large Numbers");
        int[] largeList1 = new int[100];
        int[] largeList2 = new int[100];
        for (int i = 0; i < 100; i++) {
            largeList1[i] = 9;
            largeList2[i] = 1;
        }
        solution.compareApproaches(largeList1, largeList2);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nIterative with Dummy Node (RECOMMENDED):");
        System.out.println("Key Insight: Process digits from least significant to most significant,");
        System.out.println("similar to how we do addition by hand.");
        System.out.println("\nSteps:");
        System.out.println("1. Initialize dummy node, current pointer, and carry = 0");
        System.out.println("2. While either list has nodes OR carry is not 0:");
        System.out.println("   - Get current digits (0 if list is exhausted)");
        System.out.println("   - Calculate sum = digit1 + digit2 + carry");
        System.out.println("   - Create new node with sum % 10");
        System.out.println("   - Update carry = sum / 10");
        System.out.println("   - Move all pointers forward");
        System.out.println("3. Return dummy.next");
        
        System.out.println("\nExample: 342 + 465 = 807");
        System.out.println("Step 1: 2 + 5 + 0 = 7, digit=7, carry=0");
        System.out.println("Step 2: 4 + 6 + 0 = 10, digit=0, carry=1");
        System.out.println("Step 3: 3 + 4 + 1 = 8, digit=8, carry=0");
        System.out.println("Result: 7 -> 0 -> 8 (807)");
        
        System.out.println("\nWhy this approach works:");
        System.out.println("- Reverse order means we process least significant digits first");
        System.out.println("- Carry is properly propagated to more significant digits");
        System.out.println("- Dummy node simplifies handling of the result head");
        System.out.println("- Handles different length lists and final carry automatically");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nIterative Approach:");
        System.out.println("Time Complexity: O(max(m, n))");
        System.out.println("  - We process each node of the longer list");
        System.out.println("  - Each node is processed exactly once");
        System.out.println("Space Complexity: O(max(m, n))");
        System.out.println("  - We create a new list for the result");
        System.out.println("  - The result list has at most max(m, n) + 1 nodes");
        
        System.out.println("\nRecursive Approach:");
        System.out.println("Time Complexity: O(max(m, n))");
        System.out.println("  - Same number of operations as iterative approach");
        System.out.println("Space Complexity: O(max(m, n))");
        System.out.println("  - Result list: O(max(m, n))");
        System.out.println("  - Recursion stack: O(max(m, n))");
        
        System.out.println("\nConvert to Numbers Approach:");
        System.out.println("Time Complexity: O(m + n)");
        System.out.println("  - Two passes to convert lists to numbers");
        System.out.println("  - One pass to convert result back to list");
        System.out.println("Space Complexity: O(max(m, n))");
        System.out.println("  - Risk of integer overflow for large numbers");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with iterative approach with dummy node");
        System.out.println("2. Explain the addition process clearly:");
        System.out.println("   - Process digits from right to left");
        System.out.println("   - Handle carry-over between digits");
        System.out.println("   - Treat null nodes as 0");
        System.out.println("3. Handle edge cases:");
        System.out.println("   - Different length lists");
        System.out.println("   - Final carry (e.g., 999 + 1 = 1000)");
        System.out.println("   - Zero inputs");
        System.out.println("4. Mention alternative approaches");
        System.out.println("5. Discuss time and space complexity");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("- Forgetting the final carry (e.g., 999 + 1)");
        System.out.println("- Not handling different length lists correctly");
        System.out.println("- Creating cycles in the result list");
        System.out.println("- Modifying the input lists");
        System.out.println("- Integer overflow in conversion approach");
        
        System.out.println("\nAll tests completed!");
    }
}
