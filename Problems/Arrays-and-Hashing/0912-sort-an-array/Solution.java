/**
 * 912. Sort an Array
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of integers nums, sort the array in ascending order and return it.
 * You must solve the problem without using any built-in functions in O(n log(n)) 
 * time complexity and with the smallest space complexity possible.
 * 
 * Key Insights:
 * 1. Multiple O(n log n) algorithms: Merge Sort, Quick Sort, Heap Sort
 * 2. Merge Sort: Stable, O(n log n) time, O(n) space
 * 3. Quick Sort: O(n log n) average, O(n²) worst case, O(log n) space  
 * 4. Heap Sort: O(n log n) time, O(1) space but not stable
 * 5. Counting Sort: O(n + k) for limited range
 * 
 * Approach (Merge Sort):
 * 1. Divide array into two halves recursively
 * 2. Sort each half recursively
 * 3. Merge the two sorted halves
 * 
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 * 
 * Tags: Array, Divide and Conquer, Sorting, Heap, Merge Sort, Quick Sort
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Merge Sort (RECOMMENDED)
     * Stable, guaranteed O(n log n) time complexity
     */
    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }
    
    private void mergeSort(int[] nums, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Sort first and second halves
            mergeSort(nums, left, mid);
            mergeSort(nums, mid + 1, right);
            
            // Merge the sorted halves
            merge(nums, left, mid, right);
        }
    }
    
    private void merge(int[] nums, int left, int mid, int right) {
        // Create temporary arrays
        int[] leftArray = Arrays.copyOfRange(nums, left, mid + 1);
        int[] rightArray = Arrays.copyOfRange(nums, mid + 1, right + 1);
        
        int i = 0, j = 0, k = left;
        
        // Merge the temporary arrays back into nums[left..right]
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                nums[k++] = leftArray[i++];
            } else {
                nums[k++] = rightArray[j++];
            }
        }
        
        // Copy remaining elements
        while (i < leftArray.length) {
            nums[k++] = leftArray[i++];
        }
        while (j < rightArray.length) {
            nums[k++] = rightArray[j++];
        }
    }
    
    /**
     * Approach 2: Quick Sort
     * O(n log n) average, O(n²) worst case, O(log n) space
     */
    public int[] sortArrayQuickSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }
    
    private void quickSort(int[] nums, int low, int high) {
        if (low < high) {
            // Partition the array and get pivot index
            int pivotIndex = partition(nums, low, high);
            
            // Recursively sort elements before and after partition
            quickSort(nums, low, pivotIndex - 1);
            quickSort(nums, pivotIndex + 1, high);
        }
    }
    
    private int partition(int[] nums, int low, int high) {
        // Choose rightmost element as pivot
        int pivot = nums[high];
        
        // Index of smaller element (indicates right position of pivot)
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (nums[j] <= pivot) {
                i++;
                swap(nums, i, j);
            }
        }
        
        // Place pivot in correct position
        swap(nums, i + 1, high);
        return i + 1;
    }
    
    /**
     * Approach 3: Randomized Quick Sort
     * Avoids worst-case O(n²) by random pivot selection
     */
    public int[] sortArrayRandomizedQuickSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        randomizedQuickSort(nums, 0, nums.length - 1);
        return nums;
    }
    
    private void randomizedQuickSort(int[] nums, int low, int high) {
        if (low < high) {
            int pivotIndex = randomizedPartition(nums, low, high);
            randomizedQuickSort(nums, low, pivotIndex - 1);
            randomizedQuickSort(nums, pivotIndex + 1, high);
        }
    }
    
    private int randomizedPartition(int[] nums, int low, int high) {
        // Randomly select pivot and swap with last element
        Random random = new Random();
        int randomIndex = low + random.nextInt(high - low + 1);
        swap(nums, randomIndex, high);
        
        return partition(nums, low, high);
    }
    
    /**
     * Approach 4: Heap Sort
     * O(n log n) time, O(1) space, but not stable
     */
    public int[] sortArrayHeapSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        heapSort(nums);
        return nums;
    }
    
    private void heapSort(int[] nums) {
        int n = nums.length;
        
        // Build max heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }
        
        // One by one extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            swap(nums, 0, i);
            
            // Call max heapify on the reduced heap
            heapify(nums, i, 0);
        }
    }
    
    private void heapify(int[] nums, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        // If left child is larger than root
        if (left < n && nums[left] > nums[largest]) {
            largest = left;
        }
        
        // If right child is larger than largest so far
        if (right < n && nums[right] > nums[largest]) {
            largest = right;
        }
        
        // If largest is not root
        if (largest != i) {
            swap(nums, i, largest);
            
            // Recursively heapify the affected sub-tree
            heapify(nums, n, largest);
        }
    }
    
    /**
     * Approach 5: Counting Sort
     * O(n + k) time, O(k) space - only works for limited range
     * Suitable for this problem due to constraint: -50000 <= nums[i] <= 50000
     */
    public int[] sortArrayCountingSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        // Find range of numbers
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        
        // Create count array
        int range = max - min + 1;
        int[] count = new int[range];
        
        // Count frequencies
        for (int num : nums) {
            count[num - min]++;
        }
        
        // Modify count array to store cumulative counts
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array
        int[] output = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            output[count[nums[i] - min] - 1] = nums[i];
            count[nums[i] - min]--;
        }
        
        return output;
    }
    
    /**
     * Approach 6: Iterative Merge Sort (Bottom-up)
     * O(n log n) time, O(n) space, avoids recursion overhead
     */
    public int[] sortArrayIterativeMergeSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        
        int n = nums.length;
        
        // For current size of subarrays to be merged
        // curr_size varies from 1 to n/2
        for (int currSize = 1; currSize < n; currSize = 2 * currSize) {
            // Pick starting point of different subarrays of current size
            for (int leftStart = 0; leftStart < n - 1; leftStart += 2 * currSize) {
                // Find ending point of left subarray
                // mid+1 is starting point of right
                int mid = Math.min(leftStart + currSize - 1, n - 1);
                int rightEnd = Math.min(leftStart + 2 * currSize - 1, n - 1);
                
                // Merge subarrays nums[leftStart...mid] & nums[mid+1...rightEnd]
                iterativeMerge(nums, leftStart, mid, rightEnd);
            }
        }
        
        return nums;
    }
    
    private void iterativeMerge(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        while (j <= right) {
            temp[k++] = nums[j++];
        }
        
        // Copy back to original array
        System.arraycopy(temp, 0, nums, left, temp.length);
    }
    
    /**
     * Utility method to swap elements in array
     */
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Helper method to verify if array is sorted
     */
    private boolean isSorted(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Helper method to print array
     */
    private void printArray(int[] nums, String label) {
        System.out.print(label + ": [");
        for (int i = 0; i < Math.min(nums.length, 20); i++) {
            System.out.print(nums[i]);
            if (i < Math.min(nums.length, 20) - 1) {
                System.out.print(", ");
            }
        }
        if (nums.length > 20) {
            System.out.print("...");
        }
        System.out.println("]");
    }
    
    /**
     * Performance test helper
     */
    private void printPerformanceResult(String algorithm, long timeNs, boolean sorted) {
        System.out.println("  " + algorithm + ": " + timeNs + " ns " + (sorted ? "✓" : "✗"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Sort an Array Solution:");
        System.out.println("================================");
        
        // Test case 1: Simple case
        System.out.println("\nTest 1: Simple case");
        int[] nums1 = {5, 2, 3, 1};
        int[] expected1 = {1, 2, 3, 5};
        
        int[] nums1a = nums1.clone();
        int[] nums1b = nums1.clone();
        int[] nums1c = nums1.clone();
        int[] nums1d = nums1.clone();
        int[] nums1e = nums1.clone();
        
        long startTime = System.nanoTime();
        int[] result1a = solution.sortArray(nums1a);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1b = solution.sortArrayQuickSort(nums1b);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1c = solution.sortArrayRandomizedQuickSort(nums1c);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1d = solution.sortArrayHeapSort(nums1d);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1e = solution.sortArrayCountingSort(nums1e);
        long time1e = System.nanoTime() - startTime;
        
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("Expected: " + Arrays.toString(expected1));
        
        boolean test1a = Arrays.equals(result1a, expected1);
        boolean test1b = Arrays.equals(result1b, expected1);
        boolean test1c = Arrays.equals(result1c, expected1);
        boolean test1d = Arrays.equals(result1d, expected1);
        boolean test1e = Arrays.equals(result1e, expected1);
        
        System.out.println("Merge Sort: " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Quick Sort: " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Randomized Quick Sort: " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Heap Sort: " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Counting Sort: " + (test1e ? "PASSED" : "FAILED"));
        
        // Test case 2: Array with duplicates
        System.out.println("\nTest 2: Array with duplicates");
        int[] nums2 = {5, 1, 1, 2, 0, 0};
        int[] expected2 = {0, 0, 1, 1, 2, 5};
        
        int[] result2a = solution.sortArray(nums2.clone());
        int[] result2b = solution.sortArrayQuickSort(nums2.clone());
        int[] result2c = solution.sortArrayRandomizedQuickSort(nums2.clone());
        int[] result2d = solution.sortArrayHeapSort(nums2.clone());
        int[] result2e = solution.sortArrayCountingSort(nums2.clone());
        
        boolean test2a = Arrays.equals(result2a, expected2);
        boolean test2b = Arrays.equals(result2b, expected2);
        boolean test2c = Arrays.equals(result2c, expected2);
        boolean test2d = Arrays.equals(result2d, expected2);
        boolean test2e = Arrays.equals(result2e, expected2);
        
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("Expected: " + Arrays.toString(expected2));
        System.out.println("Merge Sort: " + (test2a ? "PASSED" : "FAILED"));
        System.out.println("Quick Sort: " + (test2b ? "PASSED" : "FAILED"));
        System.out.println("Randomized Quick Sort: " + (test2c ? "PASSED" : "FAILED"));
        System.out.println("Heap Sort: " + (test2d ? "PASSED" : "FAILED"));
        System.out.println("Counting Sort: " + (test2e ? "PASSED" : "FAILED"));
        
        // Test case 3: Already sorted array
        System.out.println("\nTest 3: Already sorted array");
        int[] nums3 = {1, 2, 3, 4, 5};
        int[] expected3 = {1, 2, 3, 4, 5};
        
        int[] result3a = solution.sortArray(nums3.clone());
        boolean test3a = Arrays.equals(result3a, expected3);
        System.out.println("Already sorted - Merge Sort: " + (test3a ? "PASSED" : "FAILED"));
        
        // Test case 4: Reverse sorted array
        System.out.println("\nTest 4: Reverse sorted array");
        int[] nums4 = {5, 4, 3, 2, 1};
        int[] expected4 = {1, 2, 3, 4, 5};
        
        int[] result4a = solution.sortArray(nums4.clone());
        boolean test4a = Arrays.equals(result4a, expected4);
        System.out.println("Reverse sorted - Merge Sort: " + (test4a ? "PASSED" : "FAILED"));
        
        // Test case 5: Single element
        System.out.println("\nTest 5: Single element");
        int[] nums5 = {42};
        int[] expected5 = {42};
        
        int[] result5a = solution.sortArray(nums5.clone());
        boolean test5a = Arrays.equals(result5a, expected5);
        System.out.println("Single element - Merge Sort: " + (test5a ? "PASSED" : "FAILED"));
        
        // Test case 6: Empty array
        System.out.println("\nTest 6: Empty array");
        int[] nums6 = {};
        int[] expected6 = {};
        
        int[] result6a = solution.sortArray(nums6.clone());
        boolean test6a = Arrays.equals(result6a, expected6);
        System.out.println("Empty array - Merge Sort: " + (test6a ? "PASSED" : "FAILED"));
        
        // Performance comparison for large array
        System.out.println("\nTest 7: Performance comparison (large array)");
        int[] nums7 = new int[10000];
        Random random = new Random(42);
        for (int i = 0; i < nums7.length; i++) {
            nums7[i] = random.nextInt(100000) - 50000; // Range: -50000 to 50000
        }
        
        int[] nums7a = nums7.clone();
        int[] nums7b = nums7.clone();
        int[] nums7c = nums7.clone();
        int[] nums7d = nums7.clone();
        int[] nums7e = nums7.clone();
        int[] nums7f = nums7.clone();
        
        startTime = System.nanoTime();
        int[] result7a = solution.sortArray(nums7a);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7b = solution.sortArrayQuickSort(nums7b);
        long time7b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7c = solution.sortArrayRandomizedQuickSort(nums7c);
        long time7c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7d = solution.sortArrayHeapSort(nums7d);
        long time7d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7e = solution.sortArrayCountingSort(nums7e);
        long time7e = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7f = solution.sortArrayIterativeMergeSort(nums7f);
        long time7f = System.nanoTime() - startTime;
        
        System.out.println("Large array (10000 elements):");
        solution.printPerformanceResult("Merge Sort", time7a, solution.isSorted(result7a));
        solution.printPerformanceResult("Quick Sort", time7b, solution.isSorted(result7b));
        solution.printPerformanceResult("Randomized Quick Sort", time7c, solution.isSorted(result7c));
        solution.printPerformanceResult("Heap Sort", time7d, solution.isSorted(result7d));
        solution.printPerformanceResult("Counting Sort", time7e, solution.isSorted(result7e));
        solution.printPerformanceResult("Iterative Merge Sort", time7f, solution.isSorted(result7f));
        
        // Verify all approaches produce the same sorted result
        boolean allEqual = Arrays.equals(result7a, result7b) &&
                          Arrays.equals(result7a, result7c) &&
                          Arrays.equals(result7a, result7d) &&
                          Arrays.equals(result7a, result7e) &&
                          Arrays.equals(result7a, result7f);
        System.out.println("All approaches produce same result: " + allEqual);
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE SORTING ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Merge Sort (RECOMMENDED for this problem):");
        System.out.println("   Time: O(n log n) - Guaranteed worst-case performance");
        System.out.println("   Space: O(n) - Requires temporary array for merging");
        System.out.println("   Stability: Stable - Equal elements maintain relative order");
        System.out.println("   Pros:");
        System.out.println("     - Consistent O(n log n) performance");
        System.out.println("     - Stable sorting");
        System.out.println("     - Good for linked lists and external sorting");
        System.out.println("   Cons:");
        System.out.println("     - Requires O(n) extra space");
        System.out.println("     - Not in-place");
        System.out.println("   Best for: General purpose, when stability matters");
        
        System.out.println("\n2. Quick Sort:");
        System.out.println("   Time: O(n log n) average, O(n²) worst case");
        System.out.println("   Space: O(log n) - Recursion stack space");
        System.out.println("   Stability: Not stable");
        System.out.println("   Pros:");
        System.out.println("     - Very fast in practice (good cache performance)");
        System.out.println("     - In-place sorting");
        System.out.println("     - Low overhead");
        System.out.println("   Cons:");
        System.out.println("     - Worst-case O(n²) performance");
        System.out.println("     - Not stable");
        System.out.println("   Best for: General purpose when worst-case unlikely");
        
        System.out.println("\n3. Randomized Quick Sort:");
        System.out.println("   Time: O(n log n) average, O(n²) worst case (very unlikely)");
        System.out.println("   Space: O(log n) - Recursion stack space");
        System.out.println("   Stability: Not stable");
        System.out.println("   Pros:");
        System.out.println("     - Avoids worst-case scenarios through randomization");
        System.out.println("     - Fast in practice");
        System.out.println("   Cons:");
        System.out.println("     - Still theoretically O(n²) worst case");
        System.out.println("   Best for: Practical applications where worst-case is rare");
        
        System.out.println("\n4. Heap Sort:");
        System.out.println("   Time: O(n log n) - Guaranteed");
        System.out.println("   Space: O(1) - In-place sorting");
        System.out.println("   Stability: Not stable");
        System.out.println("   Pros:");
        System.out.println("     - Guaranteed O(n log n) performance");
        System.out.println("     - In-place with O(1) space");
        System.out.println("     - Good for memory-constrained environments");
        System.out.println("   Cons:");
        System.out.println("     - Not stable");
        System.out.println("     - Poor cache performance");
        System.out.println("   Best for: Memory-constrained systems, embedded systems");
        
        System.out.println("\n5. Counting Sort:");
        System.out.println("   Time: O(n + k) where k is range of input");
        System.out.println("   Space: O(k) - Additional array for counts");
        System.out.println("   Stability: Stable (when implemented carefully)");
        System.out.println("   Pros:");
        System.out.println("     - Very fast when k is small compared to n");
        System.out.println("     - Stable version possible");
        System.out.println("   Cons:");
        System.out.println("     - Only works for integer inputs with limited range");
        System.out.println("     - Requires O(k) extra space");
        System.out.println("   Best for: Integer sorting with known limited range");
        
        System.out.println("\n6. Iterative Merge Sort:");
        System.out.println("   Time: O(n log n) - Same as recursive version");
        System.out.println("   Space: O(n) - Requires temporary array");
        System.out.println("   Stability: Stable");
        System.out.println("   Pros:");
        System.out.println("     - Avoids recursion overhead and stack limits");
        System.out.println("     - Same benefits as merge sort");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex implementation");
        System.out.println("   Best for: Very large arrays where recursion depth is concern");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Merge Sort - it's the safest choice");
        System.out.println("2. Mention Quick Sort as alternative but discuss worst-case");
        System.out.println("3. Discuss Heap Sort for its O(1) space advantage");
        System.out.println("4. Consider Counting Sort if range is limited (like this problem)");
        System.out.println("5. Be prepared to implement any O(n log n) algorithm");
        System.out.println("6. Practice drawing the recursion tree for Merge/Quick Sort");
        System.out.println("=".repeat(80));
        
        System.out.println("\nAll tests completed!");
    }
}
