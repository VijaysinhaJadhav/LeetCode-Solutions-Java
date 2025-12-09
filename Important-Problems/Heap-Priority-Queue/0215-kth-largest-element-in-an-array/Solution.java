
## Solution.java

```java
/**
 * 215. Kth Largest Element in an Array
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 * 
 * Key Insights:
 * 1. kth largest = (n-k)th smallest in 0-indexed array
 * 2. Quickselect algorithm provides O(n) average time complexity
 * 3. Use random pivot to avoid worst-case O(n²) performance
 * 4. Partition algorithm places pivot in correct position
 * 
 * Approach (Quickselect):
 * 1. Convert kth largest to (n-k)th smallest
 * 2. Use partition algorithm to place pivot in correct position
 * 3. Recursively search in the partition containing kth element
 * 4. Use random pivot selection for better average performance
 * 
 * Time Complexity: O(n) average, O(n²) worst case
 * Space Complexity: O(1) average, O(n) worst case (recursion stack)
 * 
 * Tags: Array, Divide and Conquer, Sorting, Heap, Quickselect
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Quickselect Algorithm (Recommended)
     * O(n) average time, O(1) space
     */
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        // Convert kth largest to (n-k)th smallest
        return quickselect(nums, 0, n - 1, n - k);
    }
    
    private int quickselect(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }
        
        // Random pivot for better average performance
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);
        
        pivotIndex = partition(nums, left, right, pivotIndex);
        
        if (k == pivotIndex) {
            return nums[k];
        } else if (k < pivotIndex) {
            return quickselect(nums, left, pivotIndex - 1, k);
        } else {
            return quickselect(nums, pivotIndex + 1, right, k);
        }
    }
    
    private int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];
        // Move pivot to end
        swap(nums, pivotIndex, right);
        
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }
        
        // Move pivot to its final place
        swap(nums, storeIndex, right);
        return storeIndex;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Approach 2: Min-Heap (Priority Queue)
     * O(n log k) time, O(k) space
     * Maintain a min-heap of size k
     */
    public int findKthLargestMinHeap(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // Remove smallest element
            }
        }
        
        return minHeap.peek();
    }
    
    /**
     * Approach 3: Max-Heap (Priority Queue)
     * O(n log n) time, O(n) space
     * Convert to max-heap and remove k-1 largest elements
     */
    public int findKthLargestMaxHeap(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        for (int num : nums) {
            maxHeap.offer(num);
        }
        
        // Remove k-1 largest elements
        for (int i = 0; i < k - 1; i++) {
            maxHeap.poll();
        }
        
        return maxHeap.peek();
    }
    
    /**
     * Approach 4: Sorting
     * O(n log n) time, O(1) space if sorting in-place
     * Simplest approach but not optimal
     */
    public int findKthLargestSorting(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
    
    /**
     * Approach 5: Counting Sort
     * O(n + m) time, O(m) space where m is range of values
     * Only works when value range is limited
     */
    public int findKthLargestCountingSort(int[] nums, int k) {
        int minVal = Integer.MAX_VALUE;
        int maxVal = Integer.MIN_VALUE;
        
        // Find range of values
        for (int num : nums) {
            minVal = Math.min(minVal, num);
            maxVal = Math.max(maxVal, num);
        }
        
        // Create count array
        int[] count = new int[maxVal - minVal + 1];
        for (int num : nums) {
            count[num - minVal]++;
        }
        
        // Find kth largest by counting from the end
        int remaining = k;
        for (int i = count.length - 1; i >= 0; i--) {
            remaining -= count[i];
            if (remaining <= 0) {
                return i + minVal;
            }
        }
        
        return -1; // Should not reach here
    }
    
    /**
     * Approach 6: Three-way Partition Quickselect (Handles Duplicates)
     * O(n) average time, O(1) space
     * Better handling of duplicate elements
     */
    public int findKthLargestThreeWay(int[] nums, int k) {
        int n = nums.length;
        return quickselectThreeWay(nums, 0, n - 1, n - k);
    }
    
    private int quickselectThreeWay(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }
        
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);
        int pivotValue = nums[pivotIndex];
        
        // Three-way partition: [left, lt] < pivot, [lt+1, gt-1] == pivot, [gt, right] > pivot
        int lt = left;
        int gt = right;
        int i = left;
        
        while (i <= gt) {
            if (nums[i] < pivotValue) {
                swap(nums, lt++, i++);
            } else if (nums[i] > pivotValue) {
                swap(nums, i, gt--);
            } else {
                i++;
            }
        }
        
        // Now: [left, lt-1] < pivot, [lt, gt] == pivot, [gt+1, right] > pivot
        if (k < lt) {
            return quickselectThreeWay(nums, left, lt - 1, k);
        } else if (k > gt) {
            return quickselectThreeWay(nums, gt + 1, right, k);
        } else {
            return nums[k]; // k is in the pivot range
        }
    }
    
    /**
     * Approach 7: Iterative Quickselect
     * O(n) average time, O(1) space
     * Avoids recursion stack overhead
     */
    public int findKthLargestIterative(int[] nums, int k) {
        int n = nums.length;
        int targetIndex = n - k;
        int left = 0;
        int right = n - 1;
        
        Random random = new Random();
        
        while (left <= right) {
            int pivotIndex = partition(nums, left, right, 
                left + random.nextInt(right - left + 1));
            
            if (pivotIndex == targetIndex) {
                return nums[targetIndex];
            } else if (pivotIndex < targetIndex) {
                left = pivotIndex + 1;
            } else {
                right = pivotIndex - 1;
            }
        }
        
        return -1; // Should not reach here
    }
    
    /**
     * Helper method to visualize the quickselect process
     */
    public void visualizeQuickselect(int[] nums, int k, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Finding " + k + "th largest element");
        
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
        
        if ("Quickselect".equals(approach)) {
            visualizeQuickselectProcess(numsCopy, k);
        } else if ("Min-Heap".equals(approach)) {
            visualizeMinHeapProcess(numsCopy, k);
        }
    }
    
    private void visualizeQuickselectProcess(int[] nums, int k) {
        int n = nums.length;
        int targetIndex = n - k;
        
        System.out.println("\nQuickselect Process:");
        System.out.println("Target index: " + targetIndex + " (0-indexed)");
        System.out.println("Step | Left | Right | Pivot | Array State");
        System.out.println("-----|------|-------|-------|------------");
        
        quickselectWithVisualization(nums, 0, n - 1, targetIndex, 1);
    }
    
    private int quickselectWithVisualization(int[] nums, int left, int right, int k, int step) {
        if (left == right) {
            System.out.printf("%4d | %4d | %5d | %5s | [FOUND] %d at index %d%n",
                step, left, right, "N/A", nums[left], left);
            return nums[left];
        }
        
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);
        int pivotValue = nums[pivotIndex];
        
        System.out.printf("%4d | %4d | %5d | %5d | ", step, left, right, pivotValue);
        System.out.print("Before partition: " + Arrays.toString(Arrays.copyOfRange(nums, left, right + 1)));
        
        pivotIndex = partitionWithVisualization(nums, left, right, pivotIndex, step);
        
        System.out.print(" → After: " + Arrays.toString(Arrays.copyOfRange(nums, left, right + 1)));
        System.out.println();
        
        if (k == pivotIndex) {
            System.out.printf("%4s | %4s | %5s | %5s | [FOUND] Result: %d%n",
                "FINAL", "N/A", "N/A", "N/A", nums[k]);
            return nums[k];
        } else if (k < pivotIndex) {
            return quickselectWithVisualization(nums, left, pivotIndex - 1, k, step + 1);
        } else {
            return quickselectWithVisualization(nums, pivotIndex + 1, right, k, step + 1);
        }
    }
    
    private int partitionWithVisualization(int[] nums, int left, int right, int pivotIndex, int step) {
        int pivotValue = nums[pivotIndex];
        swap(nums, pivotIndex, right);
        
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }
        
        swap(nums, storeIndex, right);
        return storeIndex;
    }
    
    private void visualizeMinHeapProcess(int[] nums, int k) {
        System.out.println("\nMin-Heap Process:");
        System.out.println("Maintaining min-heap of size " + k);
        System.out.println("Step | Element | Heap State | Action");
        System.out.println("-----|---------|------------|--------");
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int step = 1;
        
        for (int num : nums) {
            minHeap.offer(num);
            System.out.printf("%4d | %7d | %10s | ", step, num, minHeap.toString());
            
            if (minHeap.size() > k) {
                int removed = minHeap.poll();
                System.out.printf("Removed %d (heap size > %d)%n", removed, k);
            } else {
                System.out.printf("Added (heap size = %d)%n", minHeap.size());
            }
            step++;
        }
        
        System.out.println("Final result: " + minHeap.peek());
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Kth Largest Element in Array:");
        System.out.println("=====================================\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: Standard example [3,2,1,5,6,4], k=2");
        int[] nums1 = {3,2,1,5,6,4};
        int k1 = 2;
        int expected1 = 5;
        testImplementation(solution, nums1, k1, expected1, "Quickselect");
        
        // Test case 2: With duplicates
        System.out.println("\nTest 2: With duplicates [3,2,3,1,2,4,5,5,6], k=4");
        int[] nums2 = {3,2,3,1,2,4,5,5,6};
        int k2 = 4;
        int expected2 = 4;
        testImplementation(solution, nums2, k2, expected2, "Quickselect");
        
        // Test case 3: k = 1 (largest element)
        System.out.println("\nTest 3: k=1 (largest element) [1,2,3,4,5], k=1");
        int[] nums3 = {1,2,3,4,5};
        int k3 = 1;
        int expected3 = 5;
        testImplementation(solution, nums3, k3, expected3, "Quickselect");
        
        // Test case 4: k = n (smallest element)
        System.out.println("\nTest 4: k=n (smallest element) [1,2,3,4,5], k=5");
        int[] nums4 = {1,2,3,4,5};
        int k4 = 5;
        int expected4 = 1;
        testImplementation(solution, nums4, k4, expected4, "Quickselect");
        
        // Test case 5: All same elements
        System.out.println("\nTest 5: All same elements [7,7,7,7], k=2");
        int[] nums5 = {7,7,7,7};
        int k5 = 2;
        int expected5 = 7;
        testImplementation(solution, nums5, k5, expected5, "Quickselect");
        
        // Test case 6: Single element
        System.out.println("\nTest 6: Single element [5], k=1");
        int[] nums6 = {5};
        int k6 = 1;
        int expected6 = 5;
        testImplementation(solution, nums6, k6, expected6, "Quickselect");
        
        // Test case 7: Negative numbers
        System.out.println("\nTest 7: Negative numbers [-1,-2,-3,-4,-5], k=2");
        int[] nums7 = {-1,-2,-3,-4,-5};
        int k7 = 2;
        int expected7 = -2;
        testImplementation(solution, nums7, k7, expected7, "Quickselect");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: QUICKSELECT");
        System.out.println("=".repeat(70));
        
        explainQuickselect(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, int[] nums, int k, 
                                         int expected, String approach) {
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
        
        long startTime = System.nanoTime();
        int result = 0;
        switch (approach) {
            case "Quickselect":
                result = solution.findKthLargest(numsCopy, k);
                break;
            case "Min-Heap":
                result = solution.findKthLargestMinHeap(numsCopy, k);
                break;
            case "Max-Heap":
                result = solution.findKthLargestMaxHeap(numsCopy, k);
                break;
            case "Sorting":
                result = solution.findKthLargestSorting(numsCopy, k);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        boolean passed = (result == expected);
        System.out.printf("%s: Expected=%d, Got=%d, Time=%,d ns - %s%n",
                approach, expected, result, time, (passed ? "PASSED" : "FAILED"));
        
        if (!passed) {
            System.out.println("  Input: nums=" + Arrays.toString(nums) + ", k=" + k);
        }
        
        // Visualization for interesting cases
        if (passed && nums.length <= 10) {
            solution.visualizeQuickselect(nums, k, approach);
        }
    }
    
    private static void comparePerformance(Solution solution) {
        int size = 10000;
        int[] largeArray = generateLargeArray(size, 42);
        int k = size / 2;
        
        System.out.println("Performance test with " + size + " elements, k=" + k + ":");
        
        // Test Quickselect
        int[] arr1 = Arrays.copyOf(largeArray, largeArray.length);
        long startTime = System.nanoTime();
        solution.findKthLargest(arr1, k);
        long time1 = System.nanoTime() - startTime;
        
        // Test Min-Heap
        int[] arr2 = Arrays.copyOf(largeArray, largeArray.length);
        startTime = System.nanoTime();
        solution.findKthLargestMinHeap(arr2, k);
        long time2 = System.nanoTime() - startTime;
        
        // Test Sorting
        int[] arr3 = Arrays.copyOf(largeArray, largeArray.length);
        startTime = System.nanoTime();
        solution.findKthLargestSorting(arr3, k);
        long time3 = System.nanoTime() - startTime;
        
        // Test Counting Sort (if applicable)
        long time4 = 0;
        if (isSuitableForCountingSort(largeArray)) {
            int[] arr4 = Arrays.copyOf(largeArray, largeArray.length);
            startTime = System.nanoTime();
            solution.findKthLargestCountingSort(arr4, k);
            time4 = System.nanoTime() - startTime;
        }
        
        System.out.printf("Quickselect:     %,12d ns%n", time1);
        System.out.printf("Min-Heap:        %,12d ns%n", time2);
        System.out.printf("Sorting:         %,12d ns%n", time3);
        if (isSuitableForCountingSort(largeArray)) {
            System.out.printf("Counting Sort:   %,12d ns%n", time4);
        } else {
            System.out.println("Counting Sort:   (not suitable - large value range)");
        }
    }
    
    private static int[] generateLargeArray(int size, int seed) {
        int[] arr = new int[size];
        Random random = new Random(seed);
        
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(10000); // Limited range for some tests
        }
        
        return arr;
    }
    
    private static boolean isSuitableForCountingSort(int[] arr) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for (int num : arr) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        
        // Suitable if range is reasonable
        return (max - min) <= 100000;
    }
    
    private static void explainQuickselect(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("Quickselect is a selection algorithm to find the kth smallest/largest");
        System.out.println("element in an unordered list. It is related to the quicksort algorithm.");
        System.out.println();
        System.out.println("For kth largest, we convert it to (n-k)th smallest in 0-indexed array.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Choose a pivot element randomly (for average O(n) performance)");
        System.out.println("2. Partition the array around the pivot:");
        System.out.println("   - Elements < pivot go to left");
        System.out.println("   - Elements > pivot go to right");
        System.out.println("   - Pivot ends up in its correct sorted position");
        System.out.println("3. If pivot position == target index, return pivot");
        System.out.println("4. If target index < pivot position, search left partition");
        System.out.println("5. If target index > pivot position, search right partition");
        
        System.out.println("\nWhy it works:");
        System.out.println("- Each partition step places the pivot in its final sorted position");
        System.out.println("- We only recurse into the partition containing our target element");
        System.out.println("- Random pivot selection avoids worst-case O(n²) performance");
        System.out.println("- On average, each step reduces problem size by half → O(n)");
        
        System.out.println("\nExample Walkthrough: [3,2,1,5,6,4], k=2 (find 2nd largest)");
        int[] example = {3,2,1,5,6,4};
        solution.visualizeQuickselect(example, 2, "Quickselect");
        
        System.out.println("\nTime Complexity Analysis:");
        System.out.println("- Average case: O(n)");
        System.out.println("  T(n) = T(n/2) + O(n) → O(n) by master theorem");
        System.out.println("- Worst case: O(n²) (if bad pivots chosen consistently)");
        System.out.println("  But random pivot makes this very unlikely");
        System.out.println("- Space Complexity: O(1) average, O(n) worst case (recursion stack)");
    }
    
    private static void checkAllImplementations(Solution solution) {
        Object[][][] testCases = {
            {{3,2,1,5,6,4}, {2}},          // Standard
            {{3,2,3,1,2,4,5,5,6}, {4}},    // Duplicates
            {{1,2,3,4,5}, {1}},            // k=1
            {{1,2,3,4,5}, {5}},            // k=n
            {{7,7,7,7}, {2}},              // All same
            {{5}, {1}},                    // Single
            {{-1,-2,-3,-4,-5}, {2}}       // Negative
        };
        
        int[] expected = {5, 4, 5, 1, 7, 5, -2};
        
        String[] methods = {
            "Quickselect",
            "Min-Heap", 
            "Max-Heap",
            "Sorting",
            "Counting Sort",
            "Three-way",
            "Iterative"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = (int[]) testCases[i][0];
            int k = ((int[]) testCases[i][1])[0];
            
            System.out.printf("\nTest case %d: nums=%s, k=%d (expected: %d)%n",
                    i + 1, Arrays.toString(nums), k, expected[i]);
            
            int[] results = new int[methods.length];
            results[0] = solution.findKthLargest(nums.clone(), k);
            results[1] = solution.findKthLargestMinHeap(nums.clone(), k);
            results[2] = solution.findKthLargestMaxHeap(nums.clone(), k);
            results[3] = solution.findKthLargestSorting(nums.clone(), k);
            results[4] = solution.findKthLargestCountingSort(nums.clone(), k);
            results[5] = solution.findKthLargestThreeWay(nums.clone(), k);
            results[6] = solution.findKthLargestIterative(nums.clone(), k);
            
            boolean caseConsistent = true;
            for (int j = 0; j < methods.length; j++) {
                boolean correct = (results[j] == expected[i]);
                System.out.printf("  %-15s: %d %s%n", methods[j], results[j],
                        correct ? "✓" : "✗ (expected " + expected[i] + ")");
                if (!correct) {
                    caseConsistent = false;
                    allConsistent = false;
                }
            }
            
            if (!caseConsistent) {
                System.out.println("  INCONSISTENT RESULTS!");
            }
        }
        
        System.out.println("\nOverall consistency: " + (allConsistent ? "ALL PASSED ✓" : "SOME FAILED ✗"));
        
        // Algorithm comparison table
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON SUMMARY");
        System.out.println("=".repeat(70));
        
        printAlgorithmComparison();
    }
    
    private static void printAlgorithmComparison() {
        System.out.println("\n1. QUICKSELECT (RECOMMENDED):");
        System.out.println("   Time: O(n) average, O(n²) worst case");
        System.out.println("   Space: O(1) average, O(n) worst case");
        System.out.println("   Pros:");
        System.out.println("     - Optimal average time complexity");
        System.out.println("     - In-place algorithm");
        System.out.println("     - No extra data structures needed");
        System.out.println("   Cons:");
        System.out.println("     - Worst case O(n²) (mitigated by random pivot)");
        System.out.println("     - Recursive implementation");
        System.out.println("   Use when: General case, optimal average performance needed");
        
        System.out.println("\n2. MIN-HEAP (PRIORITY QUEUE):");
        System.out.println("   Time: O(n log k)");
        System.out.println("   Space: O(k)");
        System.out.println("   Pros:");
        System.out.println("     - Guaranteed O(n log k) time");
        System.out.println("     - Simple to implement and understand");
        System.out.println("     - Good for small k");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(k) extra space");
        System.out.println("     - Slower than quickselect for large k");
        System.out.println("   Use when: k is small, simplicity is priority");
        
        System.out.println("\n3. MAX-HEAP:");
        System.out.println("   Time: O(n log n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Very simple implementation");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Less efficient than other methods");
        System.out.println("     - Uses O(n) space");
        System.out.println("   Use when: Code simplicity over performance");
        
        System.out.println("\n4. SORTING:");
        System.out.println("   Time: O(n log n)");
        System.out.println("   Space: O(1) if in-place");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple");
        System.out.println("     - One line implementation");
        System.out.println("   Cons:");
        System.out.println("     - Not optimal time complexity");
        System.out.println("     - Sorts entire array unnecessarily");
        System.out.println("   Use when: Quick implementation, small arrays");
        
        System.out.println("\n5. COUNTING SORT:");
        System.out.println("   Time: O(n + m) where m is value range");
        System.out.println("   Space: O(m)");
        System.out.println("   Pros:");
        System.out.println("     - Very fast when value range is small");
        System.out.println("     - Linear time for limited ranges");
        System.out.println("   Cons:");
        System.out.println("     - Only works for limited value ranges");
        System.out.println("     - Uses O(m) extra space");
        System.out.println("   Use when: Value range is known and limited");
        
        System.out.println("\n6. THREE-WAY QUICKSELECT:");
        System.out.println("   Time: O(n) average");
        System.out.println("   Space: O(1) average");
        System.out.println("   Pros:");
        System.out.println("     - Better handling of duplicate elements");
        System.out.println("     - Same complexity as standard quickselect");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("   Use when: Many duplicate elements expected");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with quickselect - it's the optimal solution");
        System.out.println("2. Explain the conversion: kth largest = (n-k)th smallest");
        System.out.println("3. Mention random pivot selection for average O(n) performance");
        System.out.println("4. Discuss alternative approaches and their trade-offs");
        System.out.println("5. Handle edge cases: k=1, k=n, duplicates, single element");
        System.out.println("6. Consider the constraints (value range, array size)");
        System.out.println("=".repeat(70));
    }
}
