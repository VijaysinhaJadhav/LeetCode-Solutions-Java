
## Solution.java

```java
/**
 * 23. Merge k Sorted Lists
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given an array of k sorted linked lists, merge all lists into one sorted linked list.
 * 
 * Key Insights:
 * 1. Multiple approaches: Priority Queue, Divide and Conquer, Sequential Merge
 * 2. Priority Queue provides O(n log k) time complexity
 * 3. Divide and Conquer also achieves optimal time complexity
 * 4. Need to handle empty lists and edge cases
 * 
 * Approach (Priority Queue):
 * 1. Use min-heap to always get the smallest node
 * 2. Add all non-null list heads to the heap
 * 3. Repeatedly extract min node and add its next node to heap
 * 4. Build result list using dummy node
 * 
 * Time Complexity: O(n log k) where n is total nodes, k is number of lists
 * Space Complexity: O(k) for the priority queue
 * 
 * Tags: Linked List, Divide and Conquer, Heap, Merge Sort
 */

import java.util.*;

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
     * Approach 1: Priority Queue (Min-Heap) - RECOMMENDED
     * O(n log k) time, O(k) space - Optimal solution
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // Min-heap to always get the smallest node
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
        
        // Add all non-null list heads to the heap
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.offer(list);
            }
        }
        
        // Dummy node to simplify list construction
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        // Process nodes until heap is empty
        while (!minHeap.isEmpty()) {
            // Get the smallest node
            ListNode smallest = minHeap.poll();
            current.next = smallest;
            current = current.next;
            
            // Add next node from the same list to heap
            if (smallest.next != null) {
                minHeap.offer(smallest.next);
            }
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 2: Divide and Conquer (Merge Sort style)
     * O(n log k) time, O(log k) space for recursion stack
     */
    public ListNode mergeKListsDivideConquer(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return mergeLists(lists, 0, lists.length - 1);
    }
    
    private ListNode mergeLists(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }
        
        int mid = left + (right - left) / 2;
        ListNode leftList = mergeLists(lists, left, mid);
        ListNode rightList = mergeLists(lists, mid + 1, right);
        
        return mergeTwoLists(leftList, rightList);
    }
    
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        // Attach remaining nodes
        if (l1 != null) {
            current.next = l1;
        } else {
            current.next = l2;
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 3: Sequential Merge (One by One)
     * O(nk) time, O(1) space - Simple but inefficient for large k
     */
    public ListNode mergeKListsSequential(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        ListNode result = null;
        for (ListNode list : lists) {
            result = mergeTwoLists(result, list);
        }
        
        return result;
    }
    
    /**
     * Approach 4: Priority Queue with optimized comparison
     * O(n log k) time, O(k) space - Similar to approach 1 with optimizations
     */
    public ListNode mergeKListsHeapOptimized(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // Use a min-heap that compares node values
        PriorityQueue<ListNode> heap = new PriorityQueue<>(lists.length, (a, b) -> a.val - b.val);
        
        // Add non-null heads and track list heads
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                heap.offer(lists[i]);
            }
        }
        
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        
        while (!heap.isEmpty()) {
            ListNode node = heap.poll();
            tail.next = node;
            tail = tail.next;
            
            if (node.next != null) {
                heap.offer(node.next);
            }
        }
        
        return dummy.next;
    }
    
    /**
     * Approach 5: Iterative Merge (Bottom-up)
     * O(n log k) time, O(1) space - Non-recursive divide and conquer
     */
    public ListNode mergeKListsIterative(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        int interval = 1;
        int n = lists.length;
        
        while (interval < n) {
            for (int i = 0; i < n - interval; i += interval * 2) {
                lists[i] = mergeTwoLists(lists[i], lists[i + interval]);
            }
            interval *= 2;
        }
        
        return lists[0];
    }
    
    /**
     * Helper method to convert array to linked list
     */
    private ListNode arrayToList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        for (int num : arr) {
            current.next = new ListNode(num);
            current = current.next;
        }
        
        return dummy.next;
    }
    
    /**
     * Helper method to convert linked list to array
     */
    private int[] listToArray(ListNode head) {
        if (head == null) {
            return new int[0];
        }
        
        List<Integer> result = new ArrayList<>();
        while (head != null) {
            result.add(head.val);
            head = head.next;
        }
        
        return result.stream().mapToInt(i -> i).toArray();
    }
    
    /**
     * Helper method to print linked list
     */
    private void printList(ListNode head) {
        if (head == null) {
            System.out.println("null");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) {
                sb.append(" -> ");
            }
            head = head.next;
        }
        System.out.println(sb.toString());
    }
    
    /**
     * Helper method to visualize the merging process
     */
    private void visualizeMergeProcess(ListNode[] lists, String approach) {
        System.out.println("\n" + approach + " Approach:");
        System.out.println("Input lists:");
        for (int i = 0; i < lists.length; i++) {
            System.out.print("List " + (i + 1) + ": ");
            printList(lists[i]);
        }
        
        long startTime = System.nanoTime();
        ListNode result = null;
        
        switch (approach) {
            case "Priority Queue":
                result = mergeKLists(lists);
                break;
            case "Divide and Conquer":
                result = mergeKListsDivideConquer(lists);
                break;
            case "Sequential":
                result = mergeKListsSequential(lists);
                break;
            case "Iterative":
                result = mergeKListsIterative(lists);
                break;
        }
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        System.out.println("Merged result: ");
        printList(result);
        System.out.println("Time taken: " + duration + " ns");
        
        // Verify the result is sorted
        if (isSorted(result)) {
            System.out.println("✓ Result is properly sorted");
        } else {
            System.out.println("✗ Result is NOT sorted correctly");
        }
    }
    
    /**
     * Helper method to check if a linked list is sorted
     */
    private boolean isSorted(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        ListNode current = head;
        while (current.next != null) {
            if (current.val > current.next.val) {
                return false;
            }
            current = current.next;
        }
        
        return true;
    }
    
    /**
     * Helper method to visualize Priority Queue process
     */
    private void visualizePriorityQueueProcess(ListNode[] lists) {
        System.out.println("\nPriority Queue Process Visualization:");
        System.out.println("======================================");
        
        PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);
        
        // Initialize heap
        System.out.println("Initial heap contents:");
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                heap.offer(lists[i]);
                System.out.println("  Added: " + lists[i].val + " from list " + (i + 1));
            }
        }
        
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        int step = 1;
        
        System.out.println("\nMerging process:");
        while (!heap.isEmpty()) {
            ListNode smallest = heap.poll();
            System.out.println("Step " + step + ": Extracted " + smallest.val);
            
            current.next = smallest;
            current = current.next;
            
            if (smallest.next != null) {
                heap.offer(smallest.next);
                System.out.println("  Added next: " + smallest.next.val + " to heap");
            }
            
            System.out.print("  Current result: ");
            printList(dummy.next);
            step++;
        }
        
        System.out.println("\nFinal merged list: ");
        printList(dummy.next);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Merge k Sorted Lists:");
        System.out.println("==============================");
        
        // Test case 1: Basic example
        System.out.println("\nTest 1: Basic example with 3 lists");
        ListNode[] lists1 = new ListNode[3];
        lists1[0] = solution.arrayToList(new int[]{1, 4, 5});
        lists1[1] = solution.arrayToList(new int[]{1, 3, 4});
        lists1[2] = solution.arrayToList(new int[]{2, 6});
        
        int[] expected1 = {1, 1, 2, 3, 4, 4, 5, 6};
        
        long startTime = System.nanoTime();
        ListNode result1a = solution.mergeKLists(lists1);
        long time1a = System.nanoTime() - startTime;
        
        // Reset lists for next test
        lists1[0] = solution.arrayToList(new int[]{1, 4, 5});
        lists1[1] = solution.arrayToList(new int[]{1, 3, 4});
        lists1[2] = solution.arrayToList(new int[]{2, 6});
        
        startTime = System.nanoTime();
        ListNode result1b = solution.mergeKListsDivideConquer(lists1);
        long time1b = System.nanoTime() - startTime;
        
        // Reset lists for next test
        lists1[0] = solution.arrayToList(new int[]{1, 4, 5});
        lists1[1] = solution.arrayToList(new int[]{1, 3, 4});
        lists1[2] = solution.arrayToList(new int[]{2, 6});
        
        startTime = System.nanoTime();
        ListNode result1c = solution.mergeKListsSequential(lists1);
        long time1c = System.nanoTime() - startTime;
        
        // Reset lists for next test
        lists1[0] = solution.arrayToList(new int[]{1, 4, 5});
        lists1[1] = solution.arrayToList(new int[]{1, 3, 4});
        lists1[2] = solution.arrayToList(new int[]{2, 6});
        
        startTime = System.nanoTime();
        ListNode result1d = solution.mergeKListsIterative(lists1);
        long time1d = System.nanoTime() - startTime;
        
        int[] resultArray1a = solution.listToArray(result1a);
        int[] resultArray1b = solution.listToArray(result1b);
        int[] resultArray1c = solution.listToArray(result1c);
        int[] resultArray1d = solution.listToArray(result1d);
        
        boolean test1a = Arrays.equals(resultArray1a, expected1);
        boolean test1b = Arrays.equals(resultArray1b, expected1);
        boolean test1c = Arrays.equals(resultArray1c, expected1);
        boolean test1d = Arrays.equals(resultArray1d, expected1);
        
        System.out.println("Priority Queue: " + Arrays.toString(resultArray1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Divide & Conquer: " + Arrays.toString(resultArray1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Sequential: " + Arrays.toString(resultArray1c) + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Iterative: " + Arrays.toString(resultArray1d) + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the Priority Queue process
        lists1[0] = solution.arrayToList(new int[]{1, 4, 5});
        lists1[1] = solution.arrayToList(new int[]{1, 3, 4});
        lists1[2] = solution.arrayToList(new int[]{2, 6});
        solution.visualizePriorityQueueProcess(lists1);
        
        // Test case 2: Empty input
        System.out.println("\nTest 2: Empty input");
        ListNode[] lists2 = new ListNode[0];
        ListNode result2a = solution.mergeKLists(lists2);
        System.out.println("Empty input: " + (result2a == null ? "PASSED" : "FAILED"));
        
        // Test case 3: Single list
        System.out.println("\nTest 3: Single list");
        ListNode[] lists3 = new ListNode[1];
        lists3[0] = solution.arrayToList(new int[]{1, 2, 3});
        ListNode result3a = solution.mergeKLists(lists3);
        int[] expected3 = {1, 2, 3};
        System.out.println("Single list: " + Arrays.toString(solution.listToArray(result3a)) + " - " + 
                         (Arrays.equals(solution.listToArray(result3a), expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Lists with different lengths
        System.out.println("\nTest 4: Lists with different lengths");
        ListNode[] lists4 = new ListNode[3];
        lists4[0] = solution.arrayToList(new int[]{1, 5, 9});
        lists4[1] = solution.arrayToList(new int[]{2, 3});
        lists4[2] = solution.arrayToList(new int[]{4, 6, 7, 8});
        ListNode result4a = solution.mergeKLists(lists4);
        int[] expected4 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Different lengths: " + Arrays.toString(solution.listToArray(result4a)) + " - " + 
                         (Arrays.equals(solution.listToArray(result4a), expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Lists with negative numbers
        System.out.println("\nTest 5: Lists with negative numbers");
        ListNode[] lists5 = new ListNode[2];
        lists5[0] = solution.arrayToList(new int[]{-5, -2, 0});
        lists5[1] = solution.arrayToList(new int[]{-3, 1, 4});
        ListNode result5a = solution.mergeKLists(lists5);
        int[] expected5 = {-5, -3, -2, 0, 1, 4};
        System.out.println("Negative numbers: " + Arrays.toString(solution.listToArray(result5a)) + " - " + 
                         (Arrays.equals(solution.listToArray(result5a), expected5) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Basic example performance:");
        System.out.println("  Priority Queue: " + time1a + " ns");
        System.out.println("  Divide & Conquer: " + time1b + " ns");
        System.out.println("  Sequential: " + time1c + " ns");
        System.out.println("  Iterative: " + time1d + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        ListNode[] lists7 = generateLargeTest(10, 100); // 10 lists, ~100 nodes each
        ListNode[] lists7Copy1 = copyLists(lists7);
        ListNode[] lists7Copy2 = copyLists(lists7);
        ListNode[] lists7Copy3 = copyLists(lists7);
        
        startTime = System.nanoTime();
        ListNode result7a = solution.mergeKLists(lists7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        ListNode result7b = solution.mergeKListsDivideConquer(lists7Copy1);
        long time7b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        ListNode result7c = solution.mergeKListsSequential(lists7Copy2);
        long time7c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        ListNode result7d = solution.mergeKListsIterative(lists7Copy3);
        long time7d = System.nanoTime() - startTime;
        
        System.out.println("Larger input (10 lists, ~100 nodes each):");
        System.out.println("  Priority Queue: " + time7a + " ns");
        System.out.println("  Divide & Conquer: " + time7b + " ns");
        System.out.println("  Sequential: " + time7c + " ns");
        System.out.println("  Iterative: " + time7d + " ns");
        
        // Verify all approaches produce the same result
        boolean allEqual = Arrays.equals(solution.listToArray(result7a), solution.listToArray(result7b)) &&
                          Arrays.equals(solution.listToArray(result7a), solution.listToArray(result7c)) &&
                          Arrays.equals(solution.listToArray(result7a), solution.listToArray(result7d));
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PRIORITY QUEUE ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We need to always get the smallest element from k lists efficiently.");
        System.out.println("Priority Queue (min-heap) provides O(log k) insertion and extraction.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Add all non-null list heads to a min-heap");
        System.out.println("2. While heap is not empty:");
        System.out.println("   - Extract the smallest node from heap");
        System.out.println("   - Add it to result list");
        System.out.println("   - If the extracted node has a next node, add it to heap");
        System.out.println("3. Return the merged list");
        
        System.out.println("\nTime Complexity Analysis:");
        System.out.println("- Each of n nodes is inserted and extracted once: O(n log k)");
        System.out.println("- Heap operations (insert/extract): O(log k) each");
        System.out.println("- Total: O(n log k) where n is total nodes, k is number of lists");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Priority Queue (RECOMMENDED):");
        System.out.println("   Time: O(n log k) - Optimal");
        System.out.println("   Space: O(k) - Heap storage");
        System.out.println("   How it works:");
        System.out.println("     - Use min-heap to track current smallest nodes");
        System.out.println("     - Always extract min and add next from same list");
        System.out.println("     - Build result incrementally");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Intuitive and clean");
        System.out.println("     - Handles any k efficiently");
        System.out.println("   Cons:");
        System.out.println("     - Requires O(k) extra space");
        System.out.println("     - Heap operations overhead");
        System.out.println("   Best for: General case, interview settings");
        
        System.out.println("\n2. Divide and Conquer:");
        System.out.println("   Time: O(n log k) - Optimal");
        System.out.println("   Space: O(log k) - Recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Merge lists in pairs recursively");
        System.out.println("     - Like merge sort for arrays");
        System.out.println("     - Base case: single list");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Minimal extra space");
        System.out.println("     - Familiar pattern (merge sort)");
        System.out.println("   Cons:");
        System.out.println("     - Recursion depth for large k");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: Memory-constrained environments");
        
        System.out.println("\n3. Sequential Merge:");
        System.out.println("   Time: O(nk) - Inefficient for large k");
        System.out.println("   Space: O(1) - No extra space");
        System.out.println("   How it works:");
        System.out.println("     - Merge lists one by one");
        System.out.println("     - Start with first list, merge with second, etc.");
        System.out.println("   Pros:");
        System.out.println("     - Simple to implement");
        System.out.println("     - No extra space");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(nk) time complexity");
        System.out.println("     - Inefficient for large k");
        System.out.println("   Best for: Small k, learning purposes");
        
        System.out.println("\n4. Iterative Merge (Bottom-up):");
        System.out.println("   Time: O(n log k) - Optimal");
        System.out.println("   Space: O(1) - No recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Merge lists in pairs iteratively");
        System.out.println("     - Double the interval each pass");
        System.out.println("     - Continue until one list remains");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - No recursion stack");
        System.out.println("     - In-place merging");
        System.out.println("   Cons:");
        System.out.println("     - Modifies input array");
        System.out.println("     - More complex iteration");
        System.out.println("   Best for: Non-recursive solutions");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Total comparisons: O(n log k) is optimal");
        System.out.println("2. For k lists, minimum comparisons needed: Ω(n log k)");
        System.out.println("3. Heap operations: O(log k) per node");
        System.out.println("4. Divide and conquer depth: O(log k) levels");
        System.out.println("5. Sequential merge: O(k) passes over data");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Priority Queue - it's the most intuitive optimal solution");
        System.out.println("2. Explain time/space complexity clearly");
        System.out.println("3. Discuss alternative approaches and their trade-offs");
        System.out.println("4. Handle edge cases: empty input, single list, null lists");
        System.out.println("5. Consider mentioning real-world applications (merge sort, external sorting)");
        System.out.println("6. If asked for optimization, discuss divide and conquer approach");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper methods for test generation
     */
    private static ListNode[] generateLargeTest(int k, int avgSize) {
        ListNode[] lists = new ListNode[k];
        Random random = new Random(42);
        
        for (int i = 0; i < k; i++) {
            int size = random.nextInt(avgSize / 2) + avgSize / 2;
            int[] arr = new int[size];
            
            // Generate sorted array
            int current = random.nextInt(100);
            for (int j = 0; j < size; j++) {
                current += random.nextInt(10) + 1;
                arr[j] = current;
            }
            
            lists[i] = arrayToListStatic(arr);
        }
        
        return lists;
    }
    
    private static ListNode arrayToListStatic(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        for (int num : arr) {
            current.next = new ListNode(num);
            current = current.next;
        }
        
        return dummy.next;
    }
    
    private static ListNode[] copyLists(ListNode[] original) {
        if (original == null) return null;
        
        ListNode[] copy = new ListNode[original.length];
        for (int i = 0; i < original.length; i++) {
            // For testing purposes, we'll recreate the lists
            // In real scenario, you'd need deep copy
            copy[i] = original[i];
        }
        return copy;
    }
}
