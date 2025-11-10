
## Solution.java

```java
/**
 * 141. Linked List Cycle
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given head, the head of a linked list, determine if the linked list has a cycle.
 * 
 * Key Insights:
 * 1. Hash Table: Store visited nodes, O(n) time and space
 * 2. Floyd's Cycle-Finding: Two pointers (slow and fast), O(n) time, O(1) space
 * 3. If fast pointer reaches null, no cycle
 * 4. If fast pointer meets slow pointer, cycle exists
 * 
 * Approach (Floyd's Cycle-Finding - RECOMMENDED):
 * 1. Initialize slow = head, fast = head
 * 2. While fast != null and fast.next != null:
 *    - Move slow one step: slow = slow.next
 *    - Move fast two steps: fast = fast.next.next
 *    - If slow == fast, return true (cycle detected)
 * 3. Return false (no cycle)
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Linked List, Two Pointers, Hash Table
 */

/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class Solution {
    /**
     * Approach 1: Floyd's Cycle-Finding Algorithm (Two Pointers) - RECOMMENDED
     * O(n) time, O(1) space - Most efficient
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;          // Move slow pointer one step
            fast = fast.next.next;     // Move fast pointer two steps
            
            if (slow == fast) {
                return true;           // Cycle detected
            }
        }
        
        return false;                  // No cycle
    }
    
    /**
     * Approach 2: Hash Table (HashSet) Approach
     * O(n) time, O(n) space - Simple but uses extra space
     */
    public boolean hasCycleHashSet(ListNode head) {
        if (head == null) return false;
        
        Set<ListNode> visited = new HashSet<>();
        ListNode current = head;
        
        while (current != null) {
            if (visited.contains(current)) {
                return true;  // Cycle detected
            }
            visited.add(current);
            current = current.next;
        }
        
        return false;  // No cycle
    }
    
    /**
     * Approach 3: Floyd's with Detailed Comments
     * Same algorithm with more explanatory comments
     */
    public boolean hasCycleDetailed(ListNode head) {
        // Empty list or single node without cycle
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;  // Tortoise - moves one step at a time
        ListNode fast = head;  // Hare - moves two steps at a time
        
        // We check both fast and fast.next because fast moves two steps
        while (fast != null && fast.next != null) {
            slow = slow.next;          // Tortoise takes one step
            fast = fast.next.next;     // Hare takes two steps
            
            // If they meet, there must be a cycle
            if (slow == fast) {
                return true;
            }
        }
        
        // If fast reaches null, there's no cycle
        return false;
    }
    
    /**
     * Approach 4: Modified Floyd's with Different Starting Point
     * Some implementations start with slow and fast already moved
     */
    public boolean hasCycleModified(ListNode head) {
        if (head == null) return false;
        
        ListNode slow = head;
        ListNode fast = head.next;  // Start fast one step ahead
        
        while (slow != fast) {
            // If fast reaches end, no cycle
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // If we break out of loop, slow == fast, so cycle exists
        return true;
    }
    
    /**
     * Approach 5: Marking Visited Nodes (Destructive)
     * O(n) time, O(1) space but modifies the list
     * Not recommended for real applications
     */
    public boolean hasCycleMarking(ListNode head) {
        if (head == null) return false;
        
        ListNode current = head;
        
        while (current != null) {
            // If we find a marked node, cycle exists
            if (current.val == Integer.MIN_VALUE) {
                return true;
            }
            // Mark the node by changing its value
            current.val = Integer.MIN_VALUE;
            current = current.next;
        }
        
        return false;
    }
    
    /**
     * Approach 6: Floyd's with Visualization
     * Includes step-by-step visualization of pointer movements
     */
    public boolean hasCycleVisual(ListNode head) {
        if (head == null || head.next == null) {
            System.out.println("Empty list or single node without cycle");
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        int step = 1;
        
        System.out.println("Starting cycle detection...");
        System.out.println("Initial: slow = " + slow.val + ", fast = " + fast.val);
        
        while (fast != null && fast.next != null) {
            System.out.println("\nStep " + step + ":");
            
            // Move pointers
            slow = slow.next;
            System.out.println("  Slow moves to: " + (slow == null ? "null" : slow.val));
            
            fast = fast.next.next;
            System.out.println("  Fast moves to: " + (fast == null ? "null" : fast.val));
            
            if (slow == fast) {
                System.out.println("  CYCLE DETECTED! Slow and fast met at node: " + slow.val);
                return true;
            }
            
            step++;
            
            // Safety check to prevent infinite loops in visualization
            if (step > 100) {
                System.out.println("  Safety break: too many steps, likely no cycle");
                break;
            }
        }
        
        System.out.println("\nNo cycle detected. Fast reached null.");
        return false;
    }
    
    /**
     * Helper method to create a linked list with cycle for testing
     * @param values Array of node values
     * @param pos Position where tail connects to (0-indexed), -1 for no cycle
     * @return Head of the created linked list
     */
    public ListNode createLinkedListWithCycle(int[] values, int pos) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        ListNode cycleNode = null;
        
        // Create all nodes
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
            
            // Mark the cycle node if we're at the specified position
            if (i == pos) {
                cycleNode = current;
            }
        }
        
        // Create cycle if pos is valid
        if (pos >= 0 && pos < values.length) {
            if (cycleNode == null && pos == 0) {
                cycleNode = head;
            }
            current.next = cycleNode;
            System.out.println("Created cycle: tail connects to node at position " + pos + " (value: " + cycleNode.val + ")");
        } else {
            System.out.println("No cycle created (pos = " + pos + ")");
        }
        
        return head;
    }
    
    /**
     * Helper method to detect cycle and provide analysis
     */
    public void analyzeLinkedList(ListNode head) {
        System.out.println("\nLinked List Analysis:");
        
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        
        // Try to detect cycle with visualization
        boolean hasCycle = hasCycleVisual(head);
        
        // Additional analysis
        if (hasCycle) {
            System.out.println("✓ Cycle confirmed in the linked list");
            
            // Find cycle length and start node (for educational purposes)
            ListNode cycleStart = detectCycleStart(head);
            if (cycleStart != null) {
                System.out.println("Cycle starts at node with value: " + cycleStart.val);
            }
        } else {
            System.out.println("✗ No cycle in the linked list");
            
            // Calculate list length
            int length = getListLength(head);
            System.out.println("List length: " + length);
        }
    }
    
    /**
     * Helper method to detect cycle start node (Problem 142 solution)
     */
    private ListNode detectCycleStart(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;
        
        // First phase: detect if cycle exists
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        
        if (!hasCycle) {
            return null;
        }
        
        // Second phase: find cycle start
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;
    }
    
    /**
     * Helper method to get list length (for non-cyclic lists)
     */
    private int getListLength(ListNode head) {
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] testData, int pos) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Test data: " + java.util.Arrays.toString(testData));
        System.out.println("Cycle position: " + pos);
        System.out.println("List length: " + testData.length);
        System.out.println("=================================");
        
        ListNode head = createLinkedListWithCycle(testData, pos);
        
        long startTime, endTime;
        boolean result;
        
        // Floyd's algorithm
        startTime = System.nanoTime();
        result = hasCycle(head);
        endTime = System.nanoTime();
        System.out.printf("Floyd's Algorithm: %8d ns, Result: %b%n", (endTime - startTime), result);
        
        // Reset list
        head = createLinkedListWithCycle(testData, pos);
        
        // Hash set approach
        startTime = System.nanoTime();
        result = hasCycleHashSet(head);
        endTime = System.nanoTime();
        System.out.printf("HashSet Approach:  %8d ns, Result: %b%n", (endTime - startTime), result);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Linked List Cycle Detection:");
        System.out.println("====================================");
        
        // Test case 1: Cycle exists
        System.out.println("\nTest 1: Cycle exists [3,2,0,-4] with pos=1");
        int[] test1 = {3, 2, 0, -4};
        ListNode head1 = solution.createLinkedListWithCycle(test1, 1);
        boolean result1 = solution.hasCycle(head1);
        System.out.println("Expected: true, Actual: " + result1 + " - " + 
                         (result1 ? "PASSED" : "FAILED"));
        solution.analyzeLinkedList(head1);
        
        // Test case 2: Cycle at head
        System.out.println("\nTest 2: Cycle at head [1,2] with pos=0");
        int[] test2 = {1, 2};
        ListNode head2 = solution.createLinkedListWithCycle(test2, 0);
        boolean result2 = solution.hasCycle(head2);
        System.out.println("Expected: true, Actual: " + result2 + " - " + 
                         (result2 ? "PASSED" : "FAILED"));
        solution.analyzeLinkedList(head2);
        
        // Test case 3: No cycle
        System.out.println("\nTest 3: No cycle [1] with pos=-1");
        int[] test3 = {1};
        ListNode head3 = solution.createLinkedListWithCycle(test3, -1);
        boolean result3 = solution.hasCycle(head3);
        System.out.println("Expected: false, Actual: " + result3 + " - " + 
                         (!result3 ? "PASSED" : "FAILED"));
        solution.analyzeLinkedList(head3);
        
        // Test case 4: Empty list
        System.out.println("\nTest 4: Empty list");
        ListNode head4 = null;
        boolean result4 = solution.hasCycle(head4);
        System.out.println("Expected: false, Actual: " + result4 + " - " + 
                         (!result4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single node with cycle (pointing to itself)
        System.out.println("\nTest 5: Single node with cycle [1] with pos=0");
        int[] test5 = {1};
        ListNode head5 = solution.createLinkedListWithCycle(test5, 0);
        boolean result5 = solution.hasCycle(head5);
        System.out.println("Expected: true, Actual: " + result5 + " - " + 
                         (result5 ? "PASSED" : "FAILED"));
        solution.analyzeLinkedList(head5);
        
        // Test case 6: Compare all approaches
        System.out.println("\nTest 6: Verify all approaches produce same result");
        int[] test6 = {1, 2, 3, 4, 5};
        ListNode head6a = solution.createLinkedListWithCycle(test6, 2);
        ListNode head6b = solution.createLinkedListWithCycle(test6, 2);
        ListNode head6c = solution.createLinkedListWithCycle(test6, 2);
        
        boolean result6a = solution.hasCycle(head6a);
        boolean result6b = solution.hasCycleHashSet(head6b);
        boolean result6c = solution.hasCycleDetailed(head6c);
        
        boolean allEqual = (result6a == result6b) && (result6a == result6c);
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
        
        // Visualization test
        System.out.println("\nTest 7: Step-by-step visualization");
        int[] test7 = {1, 2, 3, 4, 5};
        ListNode head7 = solution.createLinkedListWithCycle(test7, 2);
        solution.hasCycleVisual(head7);
        
        // Performance comparison
        System.out.println("\nTest 8: Performance Comparison - Small List with Cycle");
        int[] smallTest = new int[100];
        for (int i = 0; i < smallTest.length; i++) {
            smallTest[i] = i;
        }
        solution.compareApproaches(smallTest, 50);
        
        System.out.println("\nTest 9: Performance Comparison - Large List without Cycle");
        int[] largeTest = new int[10000];
        for (int i = 0; i < largeTest.length; i++) {
            largeTest[i] = i;
        }
        solution.compareApproaches(largeTest, -1);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nFloyd's Cycle-Finding Algorithm (Two Pointers):");
        System.out.println("Also known as the 'tortoise and hare' algorithm");
        System.out.println("Key Idea: If there's a cycle, the fast pointer will");
        System.out.println("eventually lap the slow pointer and they will meet.");
        
        System.out.println("\nMathematical Intuition:");
        System.out.println("Let:");
        System.out.println("  k = distance from head to cycle start");
        System.out.println("  m = distance from cycle start to meeting point");
        System.out.println("  n = cycle length");
        System.out.println("When slow and fast meet:");
        System.out.println("  Slow distance = k + m");
        System.out.println("  Fast distance = k + m + t*n (t is some integer)");
        System.out.println("Since fast moves twice as fast:");
        System.out.println("  2(k + m) = k + m + t*n");
        System.out.println("  k + m = t*n");
        System.out.println("This proves they must meet inside the cycle.");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. If no cycle: fast reaches null quickly");
        System.out.println("2. If cycle exists: fast enters cycle first");
        System.out.println("3. Once both in cycle, fast gains 1 step per iteration");
        System.out.println("4. Eventually fast catches up to slow");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nFloyd's Algorithm:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - In worst case, fast pointer traverses list twice");
        System.out.println("  - Linear time complexity");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only two pointers used");
        System.out.println("  - Constant space regardless of input size");
        
        System.out.println("\nHashSet Approach:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Each node visited once");
        System.out.println("  - HashSet operations are O(1) on average");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Stores all nodes in worst case");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with Floyd's algorithm (most efficient)");
        System.out.println("2. Explain the two-pointer approach clearly");
        System.out.println("3. Handle edge cases: empty list, single node");
        System.out.println("4. Mention HashSet approach as alternative");
        System.out.println("5. Discuss time and space complexity");
        System.out.println("6. Consider drawing diagram to explain pointer movements");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("- Not checking both fast and fast.next in while condition");
        System.out.println("- Forgetting edge cases (empty list, single node)");
        System.out.println("- Infinite loop in cyclic lists with incorrect termination");
        System.out.println("- Modifying the list when using marking approach");
        
        System.out.println("\nAll tests completed!");
    }
}
