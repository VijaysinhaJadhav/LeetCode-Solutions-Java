
# Solution.java

```java
import java.util.*;

/**
 * 160. Intersection of Two Linked Lists
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Find intersection node of two singly linked lists, or null if no intersection.
 * Must retain original structure, O(m+n) time, O(1) space.
 * 
 * Key Insights:
 * 1. Two pointers technique: a+b = b+a path equality
 * 2. If lists intersect, pointers meet at intersection
 * 3. If no intersection, both reach null at same time
 * 4. Alternative: calculate length difference first
 * 
 * Approach 1: Two Pointers (Optimal - RECOMMENDED)
 * O(m+n) time, O(1) space
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
     * Approach 1: Two Pointers (Optimal - RECOMMENDED)
     * Time: O(m+n), Space: O(1)
     * Key insight: a+b = b+a for path length
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        
        ListNode ptrA = headA;
        ListNode ptrB = headB;
        
        // Traverse both lists
        // When ptrA reaches end, continue from headB
        // When ptrB reaches end, continue from headA
        // If they meet, that's intersection
        // If both become null, no intersection
        
        while (ptrA != ptrB) {
            // Move ptrA to next or to headB if at end
            ptrA = (ptrA == null) ? headB : ptrA.next;
            
            // Move ptrB to next or to headA if at end
            ptrB = (ptrB == null) ? headA : ptrB.next;
        }
        
        // Either intersection node or null (if no intersection)
        return ptrA;
    }
    
    /**
     * Approach 2: Length Difference Calculation
     * Time: O(m+n), Space: O(1)
     * Calculate lengths, move longer list pointer ahead by difference
     */
    public ListNode getIntersectionNodeLength(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        
        // Calculate lengths of both lists
        int lenA = getLength(headA);
        int lenB = getLength(headB);
        
        // Move pointer of longer list ahead by difference
        ListNode ptrA = headA;
        ListNode ptrB = headB;
        
        if (lenA > lenB) {
            for (int i = 0; i < lenA - lenB; i++) {
                ptrA = ptrA.next;
            }
        } else {
            for (int i = 0; i < lenB - lenA; i++) {
                ptrB = ptrB.next;
            }
        }
        
        // Now both pointers are at same distance from end
        // Move together until they meet or reach end
        while (ptrA != null && ptrB != null && ptrA != ptrB) {
            ptrA = ptrA.next;
            ptrB = ptrB.next;
        }
        
        return ptrA; // either intersection or null
    }
    
    private int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }
    
    /**
     * Approach 3: Hash Set (Brute Force with memoization)
     * Time: O(m+n), Space: O(m) or O(n)
     * Store nodes of one list in hash set, check other list
     */
    public ListNode getIntersectionNodeHashSet(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        
        Set<ListNode> nodes = new HashSet<>();
        
        // Add all nodes from listA to hash set
        ListNode current = headA;
        while (current != null) {
            nodes.add(current);
            current = current.next;
        }
        
        // Check listB for first node in set
        current = headB;
        while (current != null) {
            if (nodes.contains(current)) {
                return current;
            }
            current = current.next;
        }
        
        return null; // No intersection
    }
    
    /**
     * Approach 4: Brute Force (Naive)
     * Time: O(m*n), Space: O(1)
     * Check each node in listA against all nodes in listB
     */
    public ListNode getIntersectionNodeBruteForce(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        
        ListNode currentA = headA;
        
        while (currentA != null) {
            ListNode currentB = headB;
            while (currentB != null) {
                if (currentA == currentB) {
                    return currentA;
                }
                currentB = currentB.next;
            }
            currentA = currentA.next;
        }
        
        return null;
    }
    
    /**
     * Approach 5: Cycle Detection Inspired
     * Time: O(m+n), Space: O(1)
     * Connect tail of listA to headB, find cycle start
     */
    public ListNode getIntersectionNodeCycle(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        
        // Find tail of listA and connect to headB
        ListNode tailA = headA;
        while (tailA.next != null) {
            tailA = tailA.next;
        }
        
        // Create a cycle by connecting tailA to headB
        ListNode originalTailNext = tailA.next;
        tailA.next = headB;
        
        // Use Floyd's cycle detection to find intersection
        ListNode intersection = findCycleStart(headA);
        
        // Restore original structure
        tailA.next = originalTailNext;
        
        return intersection;
    }
    
    private ListNode findCycleStart(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        
        // Find meeting point
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        
        // No cycle
        if (fast == null || fast.next == null) {
            return null;
        }
        
        // Find start of cycle
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;
    }
    
    /**
     * Approach 6: Reverse Lists (Modifies structure - not recommended)
     * Time: O(m+n), Space: O(1)
     * Reverse both lists, compare from end
     * Not suitable as it modifies original structure
     */
    public ListNode getIntersectionNodeReverse(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        
        // Reverse both lists
        ListNode reversedA = reverseList(headA);
        ListNode reversedB = reverseList(headB);
        
        // Compare from beginning (which was end)
        ListNode ptrA = reversedA;
        ListNode ptrB = reversedB;
        ListNode lastCommon = null;
        
        while (ptrA != null && ptrB != null && ptrA == ptrB) {
            lastCommon = ptrA;
            ptrA = ptrA.next;
            ptrB = ptrB.next;
        }
        
        // Restore original lists
        reverseList(reversedA);
        reverseList(reversedB);
        
        return lastCommon;
    }
    
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
     * Helper: Create two intersecting linked lists for testing
     */
    public ListNode[] createIntersectingLists(int[] listAValues, int[] listBValues, 
                                            int[] commonValues, int skipA, int skipB) {
        // Create common part
        ListNode commonHead = null;
        ListNode commonTail = null;
        if (commonValues != null && commonValues.length > 0) {
            commonHead = new ListNode(commonValues[0]);
            commonTail = commonHead;
            for (int i = 1; i < commonValues.length; i++) {
                commonTail.next = new ListNode(commonValues[i]);
                commonTail = commonTail.next;
            }
        }
        
        // Create list A
        ListNode headA = null;
        ListNode tailA = null;
        if (listAValues != null && listAValues.length > 0) {
            headA = new ListNode(listAValues[0]);
            tailA = headA;
            for (int i = 1; i < skipA; i++) {
                tailA.next = new ListNode(listAValues[i]);
                tailA = tailA.next;
            }
            // Attach common part
            if (commonHead != null) {
                tailA.next = commonHead;
            }
        } else if (commonHead != null) {
            headA = commonHead;
        }
        
        // Create list B
        ListNode headB = null;
        ListNode tailB = null;
        if (listBValues != null && listBValues.length > 0) {
            headB = new ListNode(listBValues[0]);
            tailB = headB;
            for (int i = 1; i < skipB; i++) {
                tailB.next = new ListNode(listBValues[i]);
                tailB = tailB.next;
            }
            // Attach common part
            if (commonHead != null) {
                tailB.next = commonHead;
            }
        } else if (commonHead != null) {
            headB = commonHead;
        }
        
        return new ListNode[]{headA, headB, commonHead};
    }
    
    /**
     * Helper: Print linked list
     */
    public void printList(ListNode head, String label) {
        System.out.print(label + ": ");
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" → ");
            }
            current = current.next;
        }
        System.out.println(" → NULL");
    }
    
    /**
     * Helper: Visualize the two pointers algorithm
     */
    public void visualizeTwoPointers(ListNode headA, ListNode headB) {
        System.out.println("\nTwo Pointers Algorithm Visualization:");
        
        printList(headA, "List A");
        printList(headB, "List B");
        
        ListNode ptrA = headA;
        ListNode ptrB = headB;
        int step = 1;
        
        System.out.println("\nStep-by-step traversal:");
        System.out.printf("%-6s %-15s %-15s %-20s\n", 
            "Step", "Pointer A", "Pointer B", "Action");
        System.out.println("-".repeat(60));
        
        while (ptrA != ptrB) {
            System.out.printf("%-6d ", step++);
            
            // Display current positions
            String posA = (ptrA == null) ? "null" : "Node(" + ptrA.val + ")";
            String posB = (ptrB == null) ? "null" : "Node(" + ptrB.val + ")";
            System.out.printf("%-15s %-15s ", posA, posB);
            
            // Display action
            if (ptrA == ptrB) {
                System.out.print("Pointers equal → FOUND intersection");
            } else {
                if (ptrA == null) {
                    System.out.print("ptrA at end → move to headB");
                } else {
                    System.out.print("ptrA move next");
                }
                
                System.out.print(", ");
                
                if (ptrB == null) {
                    System.out.print("ptrB at end → move to headA");
                } else {
                    System.out.print("ptrB move next");
                }
            }
            
            System.out.println();
            
            // Move pointers
            ptrA = (ptrA == null) ? headB : ptrA.next;
            ptrB = (ptrB == null) ? headA : ptrB.next;
            
            // Safety check
            if (step > 100) {
                System.out.println("... (stopping to prevent infinite loop)");
                break;
            }
        }
        
        System.out.printf("%-6d ", step);
        String posA = (ptrA == null) ? "null" : "Node(" + ptrA.val + ")";
        String posB = (ptrB == null) ? "null" : "Node(" + ptrB.val + ")";
        System.out.printf("%-15s %-15s ", posA, posB);
        
        if (ptrA == null && ptrB == null) {
            System.out.println("Both null → NO intersection");
        } else {
            System.out.println("Pointers equal → FOUND intersection at Node(" + ptrA.val + ")");
        }
        
        // Explain why it works
        System.out.println("\nWhy this works:");
        System.out.println("Path for ptrA: A + B (all nodes before intersection + other list)");
        System.out.println("Path for ptrB: B + A (all nodes before intersection + other list)");
        System.out.println("Total distance is same for both pointers: lengthA + lengthB");
        System.out.println("If intersection exists, they meet at intersection node");
        System.out.println("If no intersection, both reach null at same time");
    }
    
    /**
     * Helper: Explain the two pointers algorithm
     */
    public void explainTwoPointersAlgorithm() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TWO POINTERS ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nKey Insight:");
        System.out.println("If two lists intersect, the path lengths are:");
        System.out.println("List A: X + Z (X = nodes before intersection, Z = common part)");
        System.out.println("List B: Y + Z (Y = nodes before intersection, Z = common part)");
        System.out.println("\nIf we traverse A then B: (X + Z) + Y = X + Y + Z");
        System.out.println("If we traverse B then A: (Y + Z) + X = X + Y + Z");
        System.out.println("Both paths have same total length!");
        
        System.out.println("\nAlgorithm:");
        System.out.println("1. Initialize two pointers: ptrA at headA, ptrB at headB");
        System.out.println("2. Traverse both lists:");
        System.out.println("   - Move ptrA forward; when it reaches end, continue from headB");
        System.out.println("   - Move ptrB forward; when it reaches end, continue from headA");
        System.out.println("3. Continue until:");
        System.out.println("   - Pointers meet → intersection node found");
        System.out.println("   - Both become null → no intersection");
        
        System.out.println("\nExample:");
        System.out.println("List A: 4→1→8→4→5 (intersection at 8)");
        System.out.println("List B: 5→6→1→8→4→5");
        System.out.println("\nPath for ptrA: 4→1→8→4→5→5→6→1→8");
        System.out.println("Path for ptrB: 5→6→1→8→4→5→4→1→8");
        System.out.println("Both meet at Node(8) ✓");
        
        System.out.println("\nWhy it works mathematically:");
        System.out.println("Let a = length of listA before intersection");
        System.out.println("Let b = length of listB before intersection");
        System.out.println("Let c = length of common part");
        System.out.println("Total distance for ptrA: a + c + b");
        System.out.println("Total distance for ptrB: b + c + a");
        System.out.println("These are equal, so pointers align at intersection");
        
        System.out.println("\nEdge Cases:");
        System.out.println("1. No intersection:");
        System.out.println("   Both pointers traverse m+n nodes and become null together");
        System.out.println("2. Lists of equal length:");
        System.out.println("   Works the same, no length adjustment needed");
        System.out.println("3. One list empty:");
        System.out.println("   Pointer from empty list starts at other list's head");
        System.out.println("   No intersection possible unless both empty (null == null)");
        System.out.println("4. Intersection at head:");
        System.out.println("   Pointers meet immediately at head");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES TESTING:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. Both lists null:");
        ListNode result1 = solution.getIntersectionNode(null, null);
        System.out.println("   Result: " + result1 + " (should be null)");
        
        System.out.println("\n2. One list null:");
        ListNode listA2 = new ListNode(1);
        ListNode result2 = solution.getIntersectionNode(listA2, null);
        System.out.println("   Result: " + result2 + " (should be null)");
        
        System.out.println("\n3. No intersection, same length:");
        ListNode listA3 = new ListNode(1);
        listA3.next = new ListNode(2);
        listA3.next.next = new ListNode(3);
        
        ListNode listB3 = new ListNode(4);
        listB3.next = new ListNode(5);
        listB3.next.next = new ListNode(6);
        
        ListNode result3 = solution.getIntersectionNode(listA3, listB3);
        System.out.println("   Result: " + result3 + " (should be null)");
        
        System.out.println("\n4. No intersection, different lengths:");
        ListNode listA4 = new ListNode(1);
        listA4.next = new ListNode(2);
        
        ListNode listB4 = new ListNode(3);
        listB4.next = new ListNode(4);
        listB4.next.next = new ListNode(5);
        
        ListNode result4 = solution.getIntersectionNode(listA4, listB4);
        System.out.println("   Result: " + result4 + " (should be null)");
        
        System.out.println("\n5. Intersection at head (same list):");
        ListNode common5 = new ListNode(1);
        common5.next = new ListNode(2);
        common5.next.next = new ListNode(3);
        
        ListNode result5 = solution.getIntersectionNode(common5, common5);
        System.out.println("   Result: Node(" + result5.val + ") (should be Node(1))");
        
        System.out.println("\n6. Intersection in middle:");
        // Create: A: 1→2→3→4, B: 5→3→4, intersect at 3
        ListNode common6 = new ListNode(3);
        common6.next = new ListNode(4);
        
        ListNode listA6 = new ListNode(1);
        listA6.next = new ListNode(2);
        listA6.next.next = common6;
        
        ListNode listB6 = new ListNode(5);
        listB6.next = common6;
        
        ListNode result6 = solution.getIntersectionNode(listA6, listB6);
        System.out.println("   Result: Node(" + result6.val + ") (should be Node(3))");
        
        System.out.println("\n7. Intersection at tail:");
        // Create: A: 1→2→3, B: 4→5→3, intersect at 3
        ListNode common7 = new ListNode(3);
        
        ListNode listA7 = new ListNode(1);
        listA7.next = new ListNode(2);
        listA7.next.next = common7;
        
        ListNode listB7 = new ListNode(4);
        listB7.next = new ListNode(5);
        listB7.next.next = common7;
        
        ListNode result7 = solution.getIntersectionNode(listA7, listB7);
        System.out.println("   Result: Node(" + result7.val + ") (should be Node(3))");
        
        System.out.println("\n8. One list contains the other:");
        // Create: A: 1→2→3→4→5, B: 3→4→5
        ListNode common8 = new ListNode(3);
        common8.next = new ListNode(4);
        common8.next.next = new ListNode(5);
        
        ListNode listA8 = new ListNode(1);
        listA8.next = new ListNode(2);
        listA8.next.next = common8;
        
        ListNode result8 = solution.getIntersectionNode(listA8, common8);
        System.out.println("   Result: Node(" + result8.val + ") (should be Node(3))");
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(ListNode headA, ListNode headB, String testCase) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES - " + testCase + ":");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\nTest Case: " + testCase);
        if (headA != null && headB != null) {
            System.out.print("List A: ");
            solution.printList(headA, "");
            System.out.print("List B: ");
            solution.printList(headB, "");
        }
        
        long startTime, endTime;
        ListNode result1, result2, result3, result4, result5, result6;
        
        // Approach 1: Two Pointers
        startTime = System.nanoTime();
        result1 = solution.getIntersectionNode(headA, headB);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Length Difference
        startTime = System.nanoTime();
        result2 = solution.getIntersectionNodeLength(headA, headB);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Hash Set
        startTime = System.nanoTime();
        result3 = solution.getIntersectionNodeHashSet(headA, headB);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Brute Force (skip for large lists)
        if (getLength(headA) * getLength(headB) < 1000) {
            startTime = System.nanoTime();
            result4 = solution.getIntersectionNodeBruteForce(headA, headB);
            endTime = System.nanoTime();
            long time4 = endTime - startTime;
        } else {
            result4 = null;
        }
        
        // Approach 5: Cycle Detection
        startTime = System.nanoTime();
        result5 = solution.getIntersectionNodeCycle(headA, headB);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Approach 6: Reverse (not recommended, modifies structure)
        // Skip this as it modifies the lists
        
        System.out.println("\nResults:");
        System.out.println("Two Pointers:      " + nodeToString(result1));
        System.out.println("Length Difference: " + nodeToString(result2));
        System.out.println("Hash Set:          " + nodeToString(result3));
        if (result4 != null) {
            System.out.println("Brute Force:       " + nodeToString(result4));
        }
        System.out.println("Cycle Detection:   " + nodeToString(result5));
        
        boolean allEqual = (result1 == result2) && (result2 == result3) && 
                          (result3 == result5) && 
                          (result4 == null || result4 == result1);
        
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Two Pointers:      %-10d (O(m+n) time, O(1) space)%n", time1);
        System.out.printf("Length Difference: %-10d (O(m+n) time, O(1) space)%n", time2);
        System.out.printf("Hash Set:          %-10d (O(m+n) time, O(m) space)%n", time3);
        if (result4 != null) {
            System.out.printf("Brute Force:       %-10d (O(m*n) time, O(1) space)%n", time4);
        }
        System.out.printf("Cycle Detection:   %-10d (O(m+n) time, O(1) space)%n", time5);
        
        // Visualize two pointers for small cases
        if (getLength(headA) <= 10 && getLength(headB) <= 10) {
            System.out.println("\n" + "-".repeat(80));
            solution.visualizeTwoPointers(headA, headB);
        }
    }
    
    private String nodeToString(ListNode node) {
        if (node == null) return "null";
        return "Node(" + node.val + ")";
    }
    
    private int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }
    
    /**
     * Helper: Analyze complexity and trade-offs
     */
    public void analyzeComplexity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS AND TRADE-OFFS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Time Complexity Comparison:");
        System.out.println("   Algorithm        | Best  | Worst  | Space  | Notes");
        System.out.println("   -----------------|-------|--------|--------|-----------------");
        System.out.println("   Brute Force      | O(1)  | O(m*n) | O(1)   | Too slow for large lists");
        System.out.println("   Hash Set         | O(m)  | O(m+n) | O(m)   | Simple but uses O(m) space");
        System.out.println("   Two Pointers     | O(1)  | O(m+n) | O(1)   | Optimal, elegant");
        System.out.println("   Length Difference| O(1)  | O(m+n) | O(1)   | Also optimal, more intuitive");
        System.out.println("   Cycle Detection  | O(1)  | O(m+n) | O(1)   | Clever but modifies structure");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   - Two Pointers & Length Difference: O(1) - only pointers");
        System.out.println("   - Hash Set: O(m) or O(n) - stores nodes");
        System.out.println("   - Brute Force: O(1) - no extra data structures");
        System.out.println("   - Cycle Detection: O(1) but modifies structure temporarily");
        
        System.out.println("\n3. When to use each approach:");
        System.out.println("   - Interview: Two Pointers (demonstrates cleverness)");
        System.out.println("   - Production: Two Pointers or Length Difference (both efficient)");
        System.out.println("   - Debugging: Hash Set (simpler to understand)");
        System.out.println("   - Never use: Brute Force (too slow for constraints)");
        
        System.out.println("\n4. Real-world considerations:");
        System.out.println("   - Two Pointers is cache-friendly (sequential access)");
        System.out.println("   - Hash Set has overhead for object creation");
        System.out.println("   - For very large lists, O(1) space is important");
        System.out.println("   - Two Pointers works without knowing lengths");
    }
    
    /**
     * Helper: Show related problems
     */
    public void showRelatedProblems() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 141. Linked List Cycle:");
        System.out.println("   - Detect if linked list has cycle");
        System.out.println("   - Uses Floyd's Tortoise and Hare algorithm");
        
        System.out.println("\n2. 142. Linked List Cycle II:");
        System.out.println("   - Find start node of cycle");
        System.out.println("   - Extension of cycle detection");
        
        System.out.println("\n3. 206. Reverse Linked List:");
        System.out.println("   - Basic linked list reversal");
        System.out.println("   - Fundamental linked list operation");
        
        System.out.println("\n4. 21. Merge Two Sorted Lists:");
        System.out.println("   - Merge two sorted linked lists");
        System.out.println("   - Another two-pointer linked list problem");
        
        System.out.println("\n5. 234. Palindrome Linked List:");
        System.out.println("   - Check if linked list is palindrome");
        System.out.println("   - Uses slow/fast pointers and reversal");
        
        System.out.println("\n6. 876. Middle of the Linked List:");
        System.out.println("   - Find middle node of linked list");
        System.out.println("   - Uses slow/fast pointers");
        
        System.out.println("\n7. 19. Remove Nth Node From End of List:");
        System.out.println("   - Remove node from end");
        System.out.println("   - Uses two pointers with offset");
        
        System.out.println("\nCommon Pattern:");
        System.out.println("Many linked list problems use two pointers:");
        System.out.println("- Slow/fast pointers (Floyd's algorithm)");
        System.out.println("- Offset pointers (n-th from end)");
        System.out.println.- Parallel traversal (intersection, merge)");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Version Control Systems (Git):");
        System.out.println("   - Finding common ancestor of two branches");
        System.out.println("   - Merge conflict resolution");
        System.out.println("   - Three-way merge algorithms");
        
        System.out.println("\n2. Social Networks:");
        System.out.println("   - Finding mutual friends/connections");
        System.out.println("   - Six degrees of separation");
        System.out.println("   - Friend suggestion algorithms");
        
        System.out.println("\n3. File Systems:");
        System.out.println("   - Finding common parent directory");
        System.out.println("  
