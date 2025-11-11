
## Solution.java

```java
/**
 * 703. Kth Largest Element in a Stream
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Design a class to find the kth largest element in a stream. 
 * Implement KthLargest class:
 * - KthLargest(int k, int[] nums) Initializes the object with k and stream nums.
 * - int add(int val) Appends integer val to stream and returns kth largest element.
 * 
 * Key Insights:
 * 1. Use min-heap to track k largest elements
 * 2. kth largest element is the smallest in min-heap of k largest elements
 * 3. Maintain heap size = k by removing smallest when size > k
 * 4. Efficient O(log k) add operations
 * 
 * Approach (Min-Heap):
 * 1. Initialize min-heap with capacity k
 * 2. Add initial elements, maintaining size k
 * 3. For add(val): 
 *    - Add val to heap
 *    - If heap size > k, remove smallest
 *    - Return heap top (kth largest)
 * 
 * Time Complexity:
 * - Initialization: O(n log k)
 * - Add: O(log k)
 * Space Complexity: O(k)
 * 
 * Tags: Design, Heap, Priority Queue, Data Stream
 */

import java.util.*;

/**
 * Primary Solution: Min-Heap Approach
 */
class KthLargest {
    private final int k;
    private final PriorityQueue<Integer> minHeap;

    /**
     * Approach 1: Min-Heap (Priority Queue) - RECOMMENDED
     * O(n log k) initialization, O(log k) add, O(k) space
     */
    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.minHeap = new PriorityQueue<>(k);
        
        // Add initial elements to the heap
        for (int num : nums) {
            add(num);
        }
    }
    
    public int add(int val) {
        // Add the new value to the heap
        minHeap.offer(val);
        
        // If heap size exceeds k, remove the smallest element
        // This maintains exactly k largest elements in the heap
        if (minHeap.size() > k) {
            minHeap.poll();
        }
        
        // The top of min-heap is the kth largest element
        return minHeap.peek();
    }
}

/**
 * Alternative Approach 2: Max-Heap with Size Control
 * Less efficient but demonstrates alternative thinking
 */
class KthLargestMaxHeap {
    private final int k;
    private final PriorityQueue<Integer> maxHeap;
    private final List<Integer> allElements;

    /**
     * Approach 2: Max-Heap with Full Storage
     * O(n log n) initialization, O(n log n) add, O(n) space
     * Not recommended for large streams
     */
    public KthLargestMaxHeap(int k, int[] nums) {
        this.k = k;
        this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        this.allElements = new ArrayList<>();
        
        // Add all initial elements
        for (int num : nums) {
            allElements.add(num);
            maxHeap.offer(num);
        }
    }
    
    public int add(int val) {
        allElements.add(val);
        maxHeap.offer(val);
        
        // Create temporary list to find kth largest
        List<Integer> temp = new ArrayList<>(allElements);
        Collections.sort(temp, Collections.reverseOrder());
        
        return temp.get(k - 1);
    }
}

/**
 * Alternative Approach 3: Balanced BST Simulation
 * Using TreeSet for educational purposes
 */
class KthLargestTreeSet {
    private final int k;
    private final TreeSet<Integer> treeSet;
    private final List<Integer> elements;

    /**
     * Approach 3: TreeSet Approach
     * O(n log n) initialization, O(n log n) add, O(n) space
     * Demonstrates BST alternative
     */
    public KthLargestTreeSet(int k, int[] nums) {
        this.k = k;
        this.treeSet = new TreeSet<>(Collections.reverseOrder());
        this.elements = new ArrayList<>();
        
        for (int num : nums) {
            elements.add(num);
            treeSet.add(num);
        }
    }
    
    public int add(int val) {
        elements.add(val);
        treeSet.add(val);
        
        // Get kth largest by iterating
        Iterator<Integer> iterator = treeSet.iterator();
        int count = 0;
        int result = 0;
        while (iterator.hasNext() && count < k) {
            result = iterator.next();
            count++;
        }
        return result;
    }
}

/**
 * Alternative Approach 4: Quick Select (Inefficient for streams)
 * For educational comparison
 */
class KthLargestQuickSelect {
    private final int k;
    private List<Integer> elements;

    /**
     * Approach 4: QuickSelect Approach
     * O(n) average initialization, O(n) average add, O(n) space
     * Inefficient for frequent adds
     */
    public KthLargestQuickSelect(int k, int[] nums) {
        this.k = k;
        this.elements = new ArrayList<>();
        for (int num : nums) {
            elements.add(num);
        }
    }
    
    public int add(int val) {
        elements.add(val);
        return quickSelect(elements, elements.size() - k);
    }
    
    private int quickSelect(List<Integer> list, int k) {
        return quickSelectHelper(list, 0, list.size() - 1, k);
    }
    
    private int quickSelectHelper(List<Integer> list, int left, int right, int k) {
        if (left == right) {
            return list.get(left);
        }
        
        int pivotIndex = partition(list, left, right);
        
        if (k == pivotIndex) {
            return list.get(k);
        } else if (k < pivotIndex) {
            return quickSelectHelper(list, left, pivotIndex - 1, k);
        } else {
            return quickSelectHelper(list, pivotIndex + 1, right, k);
        }
    }
    
    private int partition(List<Integer> list, int left, int right) {
        int pivot = list.get(right);
        int i = left;
        
        for (int j = left; j < right; j++) {
            if (list.get(j) <= pivot) {
                Collections.swap(list, i, j);
                i++;
            }
        }
        Collections.swap(list, i, right);
        return i;
    }
}

/**
 * Alternative Approach 5: Two Heaps (Min + Max)
 * More complex but demonstrates heap combinations
 */
class KthLargestTwoHeaps {
    private final int k;
    private PriorityQueue<Integer> minHeap; // stores k largest elements
    private PriorityQueue<Integer> maxHeap; // stores remaining elements

    /**
     * Approach 5: Two Heaps Approach
     * O(n log k) initialization, O(log k) add, O(n) space
     * More complex but educational
     */
    public KthLargestTwoHeaps(int k, int[] nums) {
        this.k = k;
        this.minHeap = new PriorityQueue<>(k);
        this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        for (int num : nums) {
            add(num);
        }
    }
    
    public int add(int val) {
        // Add to minHeap if it has space or val is larger than minHeap's smallest
        if (minHeap.size() < k) {
            minHeap.offer(val);
        } else if (val > minHeap.peek()) {
            maxHeap.offer(minHeap.poll());
            minHeap.offer(val);
        } else {
            maxHeap.offer(val);
        }
        
        return minHeap.peek();
    }
}

/**
 * Comprehensive Test Suite
 */
public class Solution {
    
    /**
     * Test method to verify KthLargest implementations
     */
    public static void testKthLargest() {
        System.out.println("Testing Kth Largest Element in a Stream:");
        System.out.println("=========================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Example from problem statement");
        int k1 = 3;
        int[] nums1 = {4, 5, 8, 2};
        int[] adds1 = {3, 5, 10, 9, 4};
        int[] expected1 = {4, 5, 5, 8, 8};
        
        testImplementation("Min-Heap", k1, nums1, adds1, expected1);
        testImplementation("Two-Heaps", k1, nums1, adds1, expected1);
        
        // Test case 2: Empty initial array
        System.out.println("\nTest 2: Empty initial array");
        int k2 = 1;
        int[] nums2 = {};
        int[] adds2 = {-3, -2, -4, 0, 4};
        int[] expected2 = {-3, -2, -2, 0, 4};
        
        testImplementation("Min-Heap", k2, nums2, adds2, expected2);
        
        // Test case 3: k = 1 (always return largest)
        System.out.println("\nTest 3: k = 1 (always largest)");
        int k3 = 1;
        int[] nums3 = {5};
        int[] adds3 = {10, 3, 8, 15, 1};
        int[] expected3 = {10, 10, 10, 15, 15};
        
        testImplementation("Min-Heap", k3, nums3, adds3, expected3);
        
        // Test case 4: All elements same
        System.out.println("\nTest 4: All elements same");
        int k4 = 2;
        int[] nums4 = {3, 3, 3};
        int[] adds4 = {3, 3, 3};
        int[] expected4 = {3, 3, 3};
        
        testImplementation("Min-Heap", k4, nums4, adds4, expected4);
        
        // Test case 5: k equals array length
        System.out.println("\nTest 5: k equals array length");
        int k5 = 4;
        int[] nums5 = {1, 2, 3, 4};
        int[] adds5 = {5, 6, 0};
        int[] expected5 = {2, 3, 3};
        
        testImplementation("Min-Heap", k5, nums5, adds5, expected5);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        performanceTest();
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        explainMinHeapApproach();
    }
    
    /**
     * Helper method to test a specific implementation
     */
    private static void testImplementation(String approach, int k, int[] nums, int[] adds, int[] expected) {
        System.out.println("\nTesting " + approach + " approach:");
        
        KthLargest kthLargest;
        switch (approach) {
            case "Min-Heap":
                kthLargest = new KthLargest(k, nums);
                break;
            case "Two-Heaps":
                kthLargest = new KthLargestTwoHeaps(k, nums);
                break;
            default:
                kthLargest = new KthLargest(k, nums);
        }
        
        boolean allPassed = true;
        for (int i = 0; i < adds.length; i++) {
            int result = kthLargest.add(adds[i]);
            boolean passed = (result == expected[i]);
            allPassed &= passed;
            System.out.printf("  add(%d) = %d, expected = %d - %s%n", 
                            adds[i], result, expected[i], passed ? "PASS" : "FAIL");
        }
        
        System.out.println("Overall: " + (allPassed ? "PASSED" : "FAILED"));
    }
    
    /**
     * Performance test for different implementations
     */
    private static void performanceTest() {
        int k = 100;
        int[] nums = new int[10000];
        Random random = new Random(42);
        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextInt(10000);
        }
        
        int[] testAdds = new int[1000];
        for (int i = 0; i < testAdds.length; i++) {
            testAdds[i] = random.nextInt(10000);
        }
        
        // Test Min-Heap approach
        long startTime = System.nanoTime();
        KthLargest minHeap = new KthLargest(k, nums);
        for (int add : testAdds) {
            minHeap.add(add);
        }
        long minHeapTime = System.nanoTime() - startTime;
        
        // Test Two-Heaps approach
        startTime = System.nanoTime();
        KthLargestTwoHeaps twoHeaps = new KthLargestTwoHeaps(k, nums);
        for (int add : testAdds) {
            twoHeaps.add(add);
        }
        long twoHeapsTime = System.nanoTime() - startTime;
        
        System.out.printf("Min-Heap: %d ns%n", minHeapTime);
        System.out.printf("Two-Heaps: %d ns%n", twoHeapsTime);
        System.out.printf("Min-Heap is %.2fx faster%n", (double)twoHeapsTime / minHeapTime);
    }
    
    /**
     * Detailed explanation of the min-heap approach
     */
    private static void explainMinHeapApproach() {
        System.out.println("\nMIN-HEAP APPROACH EXPLANATION:");
        System.out.println("==============================");
        
        System.out.println("\nKey Insight:");
        System.out.println("To find the kth largest element, we only need to track");
        System.out.println("the k largest elements. The kth largest is the smallest");
        System.out.println("among these k largest elements.");
        
        System.out.println("\nWhy Min-Heap?");
        System.out.println("- Min-heap gives O(1) access to the smallest element");
        System.out.println("- We can maintain exactly k elements efficiently");
        System.out.println("- Insertion and deletion are O(log k) operations");
        
        System.out.println("\nStep-by-step Example:");
        System.out.println("k = 3, initial: [4, 5, 8, 2]");
        System.out.println("Heap after initialization: [4, 5, 8] (min-heap order)");
        System.out.println("  - Smallest element (4) is the 3rd largest");
        System.out.println("\nadd(3):");
        System.out.println("  - Add 3 to heap → [3, 4, 5, 8]");
        System.out.println("  - Remove smallest (3) → [4, 5, 8]");
        System.out.println("  - Return 4 (heap top)");
        System.out.println("\nadd(5):");
        System.out.println("  - Add 5 to heap → [4, 5, 5, 8]");
        System.out.println("  - Remove smallest (4) → [5, 5, 8]");
        System.out.println("  - Return 5 (heap top)");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Initialization: O(n log k) - process n elements, each O(log k)");
        System.out.println("- Add operation: O(log k) - heap insertion/removal");
        System.out.println("- Space: O(k) - only store k elements");
        
        System.out.println("\nComparison with Other Approaches:");
        System.out.println("1. Sorting after each add: O(n log n) per add - TOO SLOW");
        System.out.println("2. Max-Heap with all elements: O(n) space, O(n log n) add");
        System.out.println("3. QuickSelect: O(n) average per add - good for few adds");
        System.out.println("4. Min-Heap: O(log k) per add - BEST for frequent adds");
    }
    
    /**
     * Main method to run all tests
     */
    public static void main(String[] args) {
        testKthLargest();
        
        // Additional educational examples
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDUCATIONAL EXAMPLES:");
        System.out.println("=".repeat(70));
        
        demonstrateHeapBehavior();
        compareApproaches();
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Demonstrate how the min-heap maintains k largest elements
     */
    private static void demonstrateHeapBehavior() {
        System.out.println("\nDemonstrating Min-Heap Behavior:");
        System.out.println("k = 3, initial: [10, 20, 30, 40]");
        
        KthLargest kth = new KthLargest(3, new int[]{10, 20, 30, 40});
        System.out.println("Initial heap state: [20, 30, 40] in min-heap order");
        System.out.println("3rd largest = " + kth.add(0)); // Should return 20
        
        System.out.println("\nAdding 25:");
        System.out.println("Heap becomes: [20, 25, 30, 40]");
        System.out.println("Remove smallest (20) → [25, 30, 40]");
        System.out.println("3rd largest = " + kth.add(25)); // Should return 25
        
        System.out.println("\nAdding 50:");
        System.out.println("Heap becomes: [25, 30, 40, 50]");
        System.out.println("Remove smallest (25) → [30, 40, 50]");
        System.out.println("3rd largest = " + kth.add(50)); // Should return 30
    }
    
    /**
     * Compare different approaches for various use cases
     */
    private static void compareApproaches() {
        System.out.println("\nApproach Comparison for Different Use Cases:");
        System.out.println("============================================");
        
        System.out.println("\n1. Frequent Adds, Large k:");
        System.out.println("   Min-Heap: O(log k) per add - BEST");
        System.out.println("   Two-Heaps: O(log k) per add - GOOD");
        System.out.println("   QuickSelect: O(n) per add - POOR");
        
        System.out.println("\n2. Infrequent Adds, Small n:");
        System.out.println("   Min-Heap: O(log k) per add - GOOD");
        System.out.println("   Sorting: O(n log n) per add - ACCEPTABLE");
        System.out.println("   QuickSelect: O(n) per add - GOOD");
        
        System.out.println("\n3. Memory Constrained:");
        System.out.println("   Min-Heap: O(k) space - BEST");
        System.out.println("   Two-Heaps: O(n) space - POOR");
        System.out.println("   QuickSelect: O(n) space - POOR");
        
        System.out.println("\n4. Real-time Requirements:");
        System.out.println("   Min-Heap: Predictable O(log k) - BEST");
        System.out.println("   QuickSelect: Unpredictable O(n) - POOR");
        System.out.println("   Two-Heaps: Predictable O(log k) - GOOD");
        
        System.out.println("\nRecommendation:");
        System.out.println("For most practical scenarios, the Min-Heap approach");
        System.out.println("provides the best balance of performance and simplicity.");
    }
}

/**
 * Additional utility class for heap visualization
 */
class HeapVisualizer {
    public static void visualizeHeap(PriorityQueue<Integer> heap) {
        List<Integer> elements = new ArrayList<>(heap);
        Collections.sort(elements);
        System.out.println("Heap contents (min to max): " + elements);
        if (!elements.isEmpty()) {
            System.out.println("Min element (kth largest): " + elements.get(0));
        }
    }
}
