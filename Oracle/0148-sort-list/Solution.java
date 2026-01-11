
# Solution.java

```java
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

import java.util.*;

/**
 * 148. Sort List
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Sort a linked list in O(n log n) time using constant space complexity.
 * 
 * Key Insights:
 * 1. Merge sort is ideal for linked lists
 * 2. Bottom-up merge sort provides O(1) space
 * 3. Need to carefully handle pointers when splitting and merging
 * 4. Use slow/fast pointer technique to find middle
 */
class Solution {
    
    /**
     * Approach 1: Bottom-up Merge Sort (Recommended for follow-up)
     * Time: O(n log n), Space: O(1)
     * Iterative approach, no recursion stack
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        // Get length of list
        int length = getLength(head);
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Bottom-up merge sort
        for (int step = 1; step < length; step *= 2) {
            ListNode prev = dummy;
            ListNode curr = dummy.next;
            
            while (curr != null) {
                ListNode left = curr;
                ListNode right = split(left, step);
                curr = split(right, step);
                prev = merge(left, right, prev);
            }
        }
        
        return dummy.next;
    }
    
    /**
     * Helper: Get length of linked list
     */
    private int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }
    
    /**
     * Helper: Split list after k nodes, return head of second part
     */
    private ListNode split(ListNode head, int k) {
        if (head == null) return null;
        
        // Move k-1 steps
        for (int i = 1; i < k && head.next != null; i++) {
            head = head.next;
        }
        
        ListNode second = head.next;
        head.next = null; // Cut the connection
        return second;
    }
    
    /**
     * Helper: Merge two sorted lists, append to prev, return new prev
     */
    private ListNode merge(ListNode l1, ListNode l2, ListNode prev) {
        ListNode curr = prev;
        
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        
        // Append remaining nodes
        if (l1 != null) {
            curr.next = l1;
        } else {
            curr.next = l2;
        }
        
        // Move curr to the end
        while (curr.next != null) {
            curr = curr.next;
        }
        
        return curr;
    }
    
    /**
     * Approach 2: Top-down Recursive Merge Sort
     * Time: O(n log n), Space: O(log n) for recursion stack
     * More intuitive but uses extra space
     */
    public ListNode sortListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        // Find middle using slow/fast pointers
        ListNode mid = findMiddle(head);
        ListNode right = mid.next;
        mid.next = null; // Split the list
        
        // Recursively sort both halves
        ListNode leftSorted = sortListRecursive(head);
        ListNode rightSorted = sortListRecursive(right);
        
        // Merge sorted halves
        return mergeTwoLists(leftSorted, rightSorted);
    }
    
    /**
     * Helper: Find middle node using slow/fast pointers
     */
    private ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // For even number of nodes, return first middle
        return prev;
    }
    
    /**
     * Helper: Merge two sorted lists (standard approach)
     */
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        
        // Append remaining nodes
        if (l1 != null) {
            curr.next = l1;
        } else {
            curr.next = l2;
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 3: Convert to Array, Sort, Rebuild
     * Time: O(n log n), Space: O(n)
     * Simple but uses extra space, doesn't meet follow-up
     */
    public ListNode sortListArray(ListNode head) {
        if (head == null) return null;
        
        // Convert to array
        List<Integer> values = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }
        
        // Sort array
        Collections.sort(values);
        
        // Rebuild linked list
        ListNode dummy = new ListNode(0);
        curr = dummy;
        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 4: Insertion Sort (for small lists)
     * Time: O(n^2), Space: O(1)
     * Not efficient for large lists but simple
     */
    public ListNode sortListInsertion(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode dummy = new ListNode(0);
        ListNode curr = head;
        
        while (curr != null) {
            ListNode next = curr.next;
            ListNode prev = dummy;
            
            // Find insertion position
            while (prev.next != null && prev.next.val < curr.val) {
                prev = prev.next;
            }
            
            // Insert curr between prev and prev.next
            curr.next = prev.next;
            prev.next = curr;
            
            curr = next;
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 5: Quick Sort (Lomuto partition)
     * Time: O(n log n) average, O(n^2) worst, Space: O(log n)
     * Not ideal for linked lists, included for completeness
     */
    public ListNode sortListQuickSort(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // Use tail for partition
        ListNode tail = getTail(head);
        return quickSort(head, tail);
    }
    
    private ListNode quickSort(ListNode head, ListNode tail) {
        if (head == null || head == tail || head == tail.next) return head;
        
        ListNode[] partition = partition(head, tail);
        ListNode pivot = partition[0];
        ListNode left = partition[1];
        ListNode right = partition[2];
        
        // If left part exists, sort it
        if (left != pivot) {
            ListNode temp = left;
            while (temp.next != pivot) {
                temp = temp.next;
            }
            temp.next = null;
            
            left = quickSort(left, temp);
            
            // Reconnect
            temp = getTail(left);
            if (temp != null) {
                temp.next = pivot;
            }
        }
        
        // Sort right part
        pivot.next = quickSort(pivot.next, right);
        
        return left != null ? left : pivot;
    }
    
    private ListNode[] partition(ListNode head, ListNode tail) {
        ListNode pivot = tail;
        ListNode left = new ListNode(0); // dummy for left
        ListNode right = new ListNode(0); // dummy for right
        ListNode leftCurr = left;
        ListNode rightCurr = right;
        ListNode curr = head;
        
        while (curr != tail) {
            if (curr.val < pivot.val) {
                leftCurr.next = curr;
                leftCurr = leftCurr.next;
            } else {
                rightCurr.next = curr;
                rightCurr = rightCurr.next;
            }
            curr = curr.next;
        }
        
        // Connect lists
        leftCurr.next = pivot;
        rightCurr.next = null;
        pivot.next = right.next;
        
        return new ListNode[]{pivot, left.next, right.next};
    }
    
    private ListNode getTail(ListNode head) {
        if (head == null) return null;
        while (head.next != null) {
            head = head.next;
        }
        return head;
    }
    
    /**
     * Helper: Print linked list
     */
    private String listToString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append("->");
            head = head.next;
        }
        return sb.toString();
    }
    
    /**
     * Helper: Create linked list from array
     */
    private ListNode createList(int[] nums) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int num : nums) {
            curr.next = new ListNode(num);
            curr = curr.next;
        }
        return dummy.next;
    }
    
    /**
     * Helper: Check if list is sorted
     */
    private boolean isSorted(ListNode head) {
        if (head == null) return true;
        ListNode curr = head;
        while (curr.next != null) {
            if (curr.val > curr.next.val) return false;
            curr = curr.next;
        }
        return true;
    }
    
    /**
     * Helper: Compare two lists
     */
    private boolean compareLists(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
            if (l1.val != l2.val) return false;
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1 == null && l2 == null;
    }
    
    /**
     * Helper: Visualize the sorting process
     */
    public void visualizeSort(ListNode head, String method) {
        System.out.println("\nSorting using " + method + ":");
        System.out.println("Original: " + listToString(head));
        
        ListNode sorted = null;
        switch (method) {
            case "bottom-up":
                sorted = sortList(head);
                break;
            case "recursive":
                sorted = sortListRecursive(head);
                break;
            case "array":
                sorted = sortListArray(head);
                break;
            case "insertion":
                sorted = sortListInsertion(head);
                break;
            case "quicksort":
                sorted = sortListQuickSort(head);
                break;
        }
        
        System.out.println("Sorted:   " + listToString(sorted));
        System.out.println("Is sorted? " + isSorted(sorted));
    }
    
    /**
     * Helper: Show step-by-step bottom-up merge sort
     */
    public void visualizeBottomUp(int[] nums) {
        System.out.println("\nBottom-up Merge Sort Step-by-Step:");
        System.out.println("===================================");
        
        ListNode head = createList(nums);
        System.out.println("Initial list: " + listToString(head));
        
        if (head == null || head.next == null) {
            System.out.println("List is already sorted (0 or 1 element)");
            return;
        }
        
        int length = getLength(head);
        System.out.println("Length: " + length);
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        for (int step = 1; step < length; step *= 2) {
            System.out.println("\nStep size: " + step);
            ListNode prev = dummy;
            ListNode curr = dummy.next;
            int mergeCount = 0;
            
            while (curr != null) {
                mergeCount++;
                System.out.println("\nMerge #" + mergeCount + ":");
                
                // Get left part
                ListNode left = curr;
                System.out.println("  Left part: " + listToString(left, step));
                
                // Split to get right part
                ListNode right = split(left, step);
                System.out.println("  Right part: " + listToString(right, step));
                
                // Get next starting point
                curr = split(right, step);
                
                // Merge and update
                System.out.println("  Merging left and right...");
                prev = merge(left, right, prev);
                System.out.println("  Result so far: " + listToString(dummy.next));
            }
        }
        
        System.out.println("\nFinal sorted list: " + listToString(dummy.next));
    }
    
    private String listToString(ListNode head, int limit) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while (head != null && count < limit) {
            sb.append(head.val);
            if (head.next != null && count < limit - 1) sb.append("->");
            head = head.next;
            count++;
        }
        if (head != null) sb.append("...");
        return sb.toString();
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            {}, // empty
            {1}, // single
            {1, 2}, // already sorted
            {2, 1}, // reverse sorted
            {4, 2, 1, 3}, // example 1
            {-1, 5, 3, 4, 0}, // example 2
            {5, 4, 3, 2, 1}, // descending
            {1, 3, 5, 2, 4, 6}, // interleaved
            {3, 1, 4, 1, 5, 9, 2, 6}, // with duplicates
            {10, -3, 0, 5, -8, 2} // negative numbers
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        int[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.printf("\nTest Case %d: %s%n", 
                i + 1, Arrays.toString(testCases[i]));
            
            // Create list
            ListNode head = createList(testCases[i]);
            
            // Test all approaches
            ListNode result1 = sortList(head);
            ListNode head2 = createList(testCases[i]);
            ListNode result2 = sortListRecursive(head2);
            ListNode head3 = createList(testCases[i]);
            ListNode result3 = sortListArray(head3);
            ListNode head4 = createList(testCases[i]);
            ListNode result4 = sortListInsertion(head4);
            
            boolean allSorted = isSorted(result1) && isSorted(result2) && 
                              isSorted(result3) && isSorted(result4);
            boolean allEqual = compareLists(result1, result2) && 
                             compareLists(result2, result3) && 
                             compareLists(result3, result4);
            
            if (allSorted && allEqual) {
                System.out.println("✓ PASS");
                passed++;
            } else {
                System.out.println("✗ FAIL");
                System.out.println("  Bottom-up: " + listToString(result1) + 
                                 " (sorted: " + isSorted(result1) + ")");
                System.out.println("  Recursive: " + listToString(result2) + 
                                 " (sorted: " + isSorted(result2) + ")");
                System.out.println("  Array:     " + listToString(result3) + 
                                 " (sorted: " + isSorted(result3) + ")");
                System.out.println("  Insertion: " + listToString(result4) + 
                                 " (sorted: " + isSorted(result4) + ")");
            }
            
            // Show quicksort for small cases
            if (testCases[i].length <= 10) {
                ListNode head5 = createList(testCases[i]);
                ListNode result5 = sortListQuickSort(head5);
                System.out.println("  Quicksort: " + listToString(result5) + 
                                 " (sorted: " + isSorted(result5) + ")");
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=======================");
        
        // Generate large test case
        Random rand = new Random(42);
        int n = 50000;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = rand.nextInt(200000) - 100000; // -100000 to 100000
        }
        
        System.out.println("Testing with " + n + " nodes:");
        
        // Test each approach (skip insertion for large n)
        long[] times = new long[5];
        boolean[] sorted = new boolean[5];
        
        // Approach 1: Bottom-up
        ListNode head1 = createList(nums);
        long start = System.currentTimeMillis();
        ListNode result1 = sortList(head1);
        times[0] = System.currentTimeMillis() - start;
        sorted[0] = isSorted(result1);
        
        // Approach 2: Recursive
        ListNode head2 = createList(nums);
        start = System.currentTimeMillis();
        ListNode result2 = sortListRecursive(head2);
        times[1] = System.currentTimeMillis() - start;
        sorted[1] = isSorted(result2);
        
        // Approach 3: Array
        ListNode head3 = createList(nums);
        start = System.currentTimeMillis();
        ListNode result3 = sortListArray(head3);
        times[2] = System.currentTimeMillis() - start;
        sorted[2] = isSorted(result3);
        
        // Approach 4: Insertion (skip for large n)
        times[3] = -1;
        sorted[3] = false;
        
        // Approach 5: Quicksort
        ListNode head5 = createList(nums);
        start = System.currentTimeMillis();
        ListNode result5 = sortListQuickSort(head5);
        times[4] = System.currentTimeMillis() - start;
        sorted[4] = isSorted(result5);
        
        System.out.println("\nApproach           | Time (ms) | Sorted? | Space Complexity");
        System.out.println("-------------------|-----------|---------|------------------");
        System.out.printf("Bottom-up Merge    | %9d | %7s | O(1)%n", 
            times[0], sorted[0] ? "✓" : "✗");
        System.out.printf("Recursive Merge    | %9d | %7s | O(log n)%n", 
            times[1], sorted[1] ? "✓" : "✗");
        System.out.printf("Array Sort         | %9d | %7s | O(n)%n", 
            times[2], sorted[2] ? "✓" : "✗");
        System.out.printf("Insertion Sort     | %9s | %7s | O(1) (too slow)%n", 
            "N/A", "N/A");
        System.out.printf("Quick Sort         | %9d | %7s | O(log n)%n", 
            times[4], sorted[4] ? "✓" : "✗");
        
        System.out.println("\nKey Observations:");
        System.out.println("1. Bottom-up merge sort is fastest for large lists");
        System.out.println("2. Recursive merge sort has recursion overhead");
        System.out.println("3. Array approach uses more memory but fast due to cache");
        System.out.println("4. Insertion sort is O(n²), infeasible for large n");
        System.out.println("5. Quick sort can degrade to O(n²) for sorted/reverse input");
    }
    
    /**
     * Helper: Explain merge sort algorithm
     */
    public void explainMergeSort() {
        System.out.println("\nMerge Sort for Linked Lists:");
        System.out.println("=============================");
        
        System.out.println("\nWhy Merge Sort?");
        System.out.println("1. O(n log n) worst-case time complexity");
        System.out.println("2. Stable sort (preserves relative order of equal elements)");
        System.out.println("3. Works well with linked lists (no random access needed)");
        System.out.println("4. Can be implemented with O(1) space for linked lists");
        
        System.out.println("\nKey Operations:");
        System.out.println("1. Split: Divide list into two halves");
        System.out.println("   - Use slow/fast pointer technique");
        System.out.println("   - Slow moves 1 step, fast moves 2 steps");
        System.out.println("   - When fast reaches end, slow is at middle");
        
        System.out.println("\n2. Merge: Combine two sorted lists");
        System.out.println("   - Use dummy node to simplify edge cases");
        System.out.println("   - Compare heads of both lists");
        System.out.println("   - Append smaller node to result");
        System.out.println("   - Continue until both lists exhausted");
        
        System.out.println("\nTwo Approaches:");
        System.out.println("1. Top-down (Recursive):");
        System.out.println("   - Split recursively until single nodes");
        System.out.println("   - Merge on the way back up");
        System.out.println("   - Space: O(log n) recursion stack");
        
        System.out.println("\n2. Bottom-up (Iterative):");
        System.out.println("   - Start with 1-element sublists");
        System.out.println("   - Merge adjacent sublists");
        System.out.println("   - Double sublist size each iteration");
        System.out.println("   - Space: O(1)");
        
        System.out.println("\nExample: Sorting [4, 2, 1, 3]");
        System.out.println("Top-down approach:");
        System.out.println("  Split: [4, 2, 1, 3] → [4, 2] and [1, 3]");
        System.out.println("  Split: [4, 2] → [4] and [2]");
        System.out.println("  Merge: [4] and [2] → [2, 4]");
        System.out.println("  Split: [1, 3] → [1] and [3]");
        System.out.println("  Merge: [1] and [3] → [1, 3]");
        System.out.println("  Merge: [2, 4] and [1, 3] → [1, 2, 3, 4]");
        
        System.out.println("\nBottom-up approach:");
        System.out.println("  Step=1: Merge [4]&[2]→[2,4], [1]&[3]→[1,3]");
        System.out.println("  Step=2: Merge [2,4]&[1,3]→[1,2,3,4]");
    }
    
    /**
     * Helper: Common mistakes and pitfalls
     */
    public void showCommonMistakes() {
        System.out.println("\nCommon Mistakes:");
        System.out.println("=================");
        
        System.out.println("\n1. Forgetting to cut links when splitting:");
        System.out.println("   ❌ Not setting middle.next = null");
        System.out.println("   ✅ Must disconnect halves before recursive calls");
        
        System.out.println("\n2. Incorrect middle finding:");
        System.out.println("   ❌ Using only slow pointer without prev");
        System.out.println("   ✅ Need prev to disconnect at middle");
        System.out.println("   Example: For [1,2,3,4], middle should be node 2");
        
        System.out.println("\n3. Not handling empty/single-element lists:");
        System.out.println("   ❌ Base case missing");
        System.out.println("   ✅ if (head == null || head.next == null) return head;");
        
        System.out.println("\n4. Memory leak in merge:");
        System.out.println("   ❌ Not updating pointers correctly");
        System.out.println("   ✅ Always advance pointers after appending");
        
        System.out.println("\n5. Stack overflow for large lists (recursive):");
        System.out.println("   ❌ Using recursion for 50,000+ nodes");
        System.out.println("   ✅ Use iterative bottom-up approach");
        
        System.out.println("\n6. Not meeting O(1) space requirement:");
        System.out.println("   ❌ Using recursion (O(log n) stack space)");
        System.out.println("   ❌ Converting to array (O(n) space)");
        System.out.println("   ✅ Bottom-up merge sort uses O(1) space");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Time complexity? (O(n log n) required)");
        System.out.println("   - Space complexity? (O(1) for follow-up)");
        System.out.println("   - Can we modify the list in place? (Yes)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Convert to array, sort, rebuild (O(n log n), O(n) space)");
        System.out.println("   - Mention this but note space issue");
        
        System.out.println("\n3. Propose merge sort:");
        System.out.println("   - Explain why it's good for linked lists");
        System.out.println("   - Discuss top-down vs bottom-up");
        System.out.println("   - Implement findMiddle() and merge() helpers");
        
        System.out.println("\n4. Handle edge cases:");
        System.out.println("   - Empty list");
        System.out.println("   - Single element list");
        System.out.println("   - Already sorted list");
        System.out.println("   - Reverse sorted list");
        
        System.out.println("\n5. Optimize for follow-up:");
        System.out.println("   - Explain bottom-up approach");
        System.out.println("   - Show O(1) space complexity");
        System.out.println("   - Walk through example");
        
        System.out.println("\n6. Discuss alternatives:");
        System.out.println("   - Quick sort: O(n log n) average, O(n²) worst");
        System.out.println("   - Insertion sort: O(n²), only for small lists");
        System.out.println("   - Heap sort: Not efficient for linked lists");
        
        System.out.println("\n7. Test with examples:");
        System.out.println("   - [4,2,1,3] → [1,2,3,4]");
        System.out.println("   - [-1,5,3,4,0] → [-1,0,3,4,5]");
        System.out.println("   - [] → []");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("148. Sort List");
        System.out.println("==============");
        
        // Explain algorithm
        solution.explainMergeSort();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Visualize examples
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Visualizing Examples:");
        System.out.println("=".repeat(80));
        
        // Example 1
        System.out.println("\nExample 1: [4,2,1,3]");
        ListNode head1 = solution.createList(new int[]{4,2,1,3});
        solution.visualizeSort(head1, "bottom-up");
        
        // Example 2
        System.out.println("\nExample 2: [-1,5,3,4,0]");
        ListNode head2 = solution.createList(new int[]{-1,5,3,4,0});
        solution.visualizeSort(head2, "recursive");
        
        // Step-by-step visualization
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Step-by-step Bottom-up Merge Sort:");
        System.out.println("=".repeat(80));
        solution.visualizeBottomUp(new int[]{4,2,1,3,6,5});
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Common mistakes
        System.out.println("\n" + "=".repeat(80));
        solution.showCommonMistakes();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // Get length
        int len = 0;
        ListNode curr = head;
        while (curr != null) {
            len++;
            curr = curr.next;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Bottom-up merge sort
        for (int step = 1; step < len; step *= 2) {
            ListNode prev = dummy;
            curr = dummy.next;
            
            while (curr != null) {
                ListNode left = curr;
                ListNode right = split(left, step);
                curr = split(right, step);
                prev = merge(left, right, prev);
            }
        }
        
        return dummy.next;
    }
    
    private ListNode split(ListNode head, int step) {
        if (head == null) return null;
        for (int i = 1; i < step && head.next != null; i++) {
            head = head.next;
        }
        ListNode right = head.next;
        head.next = null;
        return right;
    }
    
    private ListNode merge(ListNode l1, ListNode l2, ListNode prev) {
        ListNode curr = prev;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        curr.next = (l1 != null) ? l1 : l2;
        while (curr.next != null) curr = curr.next;
        return curr;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Use bottom-up merge sort for O(1) space");
        System.out.println("2. Split and merge operations are O(n) each");
        System.out.println("3. Total time complexity: O(n log n)");
        System.out.println("4. Handle edge cases: empty list, single node");
        System.out.println("5. Follow-up requirement met: O(1) space");
    }
}
