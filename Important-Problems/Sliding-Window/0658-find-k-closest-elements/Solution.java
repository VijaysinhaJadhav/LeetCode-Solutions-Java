/**
 * 658. Find K Closest Elements
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a sorted integer array arr, two integers k and x, 
 * return the k closest integers to x in the array.
 * The result should also be sorted in ascending order.
 * 
 * Key Insights:
 * 1. Array is sorted, enabling binary search
 * 2. Need to handle ties (choose smaller element when distances are equal)
 * 3. Multiple efficient approaches exist
 * 4. The optimal approach uses binary search to find the left boundary
 * 
 * Approach (Binary Search for Left Bound):
 * 1. Use binary search to find the starting index of the k closest elements
 * 2. Compare arr[mid] and arr[mid + k] to determine which side is closer to x
 * 3. Narrow search space until we find the optimal left boundary
 * 4. Return k elements starting from the found left boundary
 * 
 * Time Complexity: O(log (n - k) + k)
 * Space Complexity: O(1) excluding output
 * 
 * Tags: Array, Two Pointers, Binary Search, Sliding Window, Sorting
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Binary Search for Left Bound - RECOMMENDED
     * O(log (n - k) + k) time, O(1) space - Optimal solution
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int left = 0;
        int right = arr.length - k; // The left boundary can be at most arr.length - k
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Compare arr[mid] and arr[mid + k] to determine which side to move
            // We want to find the starting point where the k elements are closest to x
            if (x - arr[mid] > arr[mid + k] - x) {
                // If x is closer to arr[mid + k], move left to mid + 1
                left = mid + 1;
            } else {
                // If x is closer to arr[mid] or equal, move right to mid
                right = mid;
            }
        }
        
        // Build result list
        List<Integer> result = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }
        return result;
    }
    
    /**
     * Approach 2: Two Pointers from Closest Element
     * O(log n + k) time, O(1) space - Find closest then expand
     */
    public List<Integer> findClosestElementsTwoPointers(int[] arr, int k, int x) {
        // First, find the index of the closest element using binary search
        int closestIndex = findClosestIndex(arr, x);
        
        // Initialize two pointers
        int left = closestIndex;
        int right = closestIndex;
        
        // Expand the window to include k elements
        while (right - left + 1 < k) {
            if (left == 0) {
                right++;
            } else if (right == arr.length - 1) {
                left--;
            } else {
                // Compare left-1 and right+1 to see which is closer
                if (Math.abs(arr[left - 1] - x) <= Math.abs(arr[right + 1] - x)) {
                    left--;
                } else {
                    right++;
                }
            }
        }
        
        // Build result list
        List<Integer> result = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            result.add(arr[i]);
        }
        return result;
    }
    
    private int findClosestIndex(int[] arr, int x) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == x) {
                return mid;
            } else if (arr[mid] < x) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // If exact match not found, determine which of the two candidates is closer
        if (left >= arr.length) return arr.length - 1;
        if (right < 0) return 0;
        
        return Math.abs(arr[left] - x) < Math.abs(arr[right] - x) ? left : right;
    }
    
    /**
     * Approach 3: Sliding Window
     * O(n) time, O(1) space - Simple sliding window approach
     */
    public List<Integer> findClosestElementsSlidingWindow(int[] arr, int k, int x) {
        int left = 0;
        int right = arr.length - 1;
        
        // Shrink the window from both ends until we have k elements
        while (right - left + 1 > k) {
            // Remove the element that's farther from x
            if (Math.abs(arr[left] - x) > Math.abs(arr[right] - x)) {
                left++;
            } else {
                right--;
            }
        }
        
        // Build result list
        List<Integer> result = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            result.add(arr[i]);
        }
        return result;
    }
    
    /**
     * Approach 4: Custom Sorting with Priority Queue
     * O(n log k) time, O(k) space - Uses max heap
     */
    public List<Integer> findClosestElementsHeap(int[] arr, int k, int x) {
        // Max heap based on distance to x (farthest elements at top)
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1]) {
                return b[1] - a[1]; // Compare by distance (max heap)
            } else {
                return b[0] - a[0]; // Compare by value if distances are equal
            }
        });
        
        for (int num : arr) {
            int distance = Math.abs(num - x);
            maxHeap.offer(new int[]{num, distance});
            
            // Maintain only k elements in heap
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        
        // Extract elements from heap and sort them
        List<Integer> result = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            result.add(maxHeap.poll()[0]);
        }
        Collections.sort(result);
        return result;
    }
    
    /**
     * Approach 5: Binary Search with Custom Comparator
     * O(log n + k log k) time, O(k) space - Alternative binary search approach
     */
    public List<Integer> findClosestElementsCustomSort(int[] arr, int k, int x) {
        // Find the position where x would be inserted
        int index = Arrays.binarySearch(arr, x);
        if (index < 0) {
            index = -index - 1; // Convert to insertion point
        }
        
        // Define search boundaries
        int left = Math.max(0, index - k);
        int right = Math.min(arr.length - 1, index + k - 1);
        
        // Collect candidate elements
        List<Integer> candidates = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            candidates.add(arr[i]);
        }
        
        // Sort by distance to x, then by value
        Collections.sort(candidates, (a, b) -> {
            int distA = Math.abs(a - x);
            int distB = Math.abs(b - x);
            if (distA != distB) {
                return distA - distB;
            } else {
                return a - b;
            }
        });
        
        // Take first k elements and sort them
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(candidates.get(i));
        }
        Collections.sort(result);
        return result;
    }
    
    /**
     * Helper method to visualize the binary search algorithm
     */
    private void visualizeBinarySearch(int[] arr, int k, int x) {
        System.out.println("\nBinary Search for Left Bound Visualization:");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("k: " + k + ", x: " + x);
        
        int left = 0;
        int right = arr.length - k;
        
        System.out.println("\nStep | Left | Right | Mid | arr[mid] | arr[mid+k] | Comparison | Action");
        System.out.println("-----|------|-------|-----|----------|------------|------------|--------");
        
        int step = 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int leftDist = Math.abs(arr[mid] - x);
            int rightDist = Math.abs(arr[mid + k] - x);
            String comparison = "|" + arr[mid] + "-" + x + "| vs |" + arr[mid + k] + "-" + x + "|";
            String action;
            
            if (x - arr[mid] > arr[mid + k] - x) {
                action = "Move left to " + (mid + 1) + " (x closer to arr[" + (mid + k) + "])";
                left = mid + 1;
            } else {
                action = "Move right to " + mid + " (x closer to arr[" + mid + "])";
                right = mid;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %8d | %10d | %-10s | %s%n",
                            step++, left, right, mid, arr[mid], arr[mid + k], comparison, action);
        }
        
        List<Integer> result = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }
        
        System.out.println("\nFinal left boundary: " + left);
        System.out.println("Result window: indices " + left + " to " + (left + k - 1));
        System.out.println("Result: " + result);
        
        // Calculate distances for verification
        System.out.println("\nDistance verification:");
        for (int i = left; i < left + k; i++) {
            System.out.println("arr[" + i + "] = " + arr[i] + ", distance = " + Math.abs(arr[i] - x));
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Find K Closest Elements:");
        System.out.println("=================================");
        
        // Test case 1: Standard example (x in middle)
        System.out.println("\nTest 1: Standard example (x in middle)");
        int[] arr1 = {1, 2, 3, 4, 5};
        int k1 = 4;
        int x1 = 3;
        List<Integer> expected1 = Arrays.asList(1, 2, 3, 4);
        
        long startTime = System.nanoTime();
        List<Integer> result1a = solution.findClosestElements(arr1, k1, x1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result1b = solution.findClosestElementsTwoPointers(arr1, k1, x1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result1c = solution.findClosestElementsSlidingWindow(arr1, k1, x1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result1d = solution.findClosestElementsHeap(arr1, k1, x1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result1e = solution.findClosestElementsCustomSort(arr1, k1, x1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = result1a.equals(expected1);
        boolean test1b = result1b.equals(expected1);
        boolean test1c = result1c.equals(expected1);
        boolean test1d = result1d.equals(expected1);
        boolean test1e = result1e.equals(expected1);
        
        System.out.println("Binary Search: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Two Pointers: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Sliding Window: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Heap: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        System.out.println("Custom Sort: " + result1e + " - " + (test1e ? "PASSED" : "FAILED"));
        
        // Visualize the binary search algorithm
        solution.visualizeBinarySearch(arr1, k1, x1);
        
        // Test case 2: x smaller than all elements
        System.out.println("\nTest 2: x smaller than all elements");
        int[] arr2 = {1, 2, 3, 4, 5};
        int k2 = 4;
        int x2 = -1;
        List<Integer> expected2 = Arrays.asList(1, 2, 3, 4);
        
        List<Integer> result2a = solution.findClosestElements(arr2, k2, x2);
        System.out.println("x smaller than all: " + result2a + " - " + 
                         (result2a.equals(expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: x larger than all elements
        System.out.println("\nTest 3: x larger than all elements");
        int[] arr3 = {1, 2, 3, 4, 5};
        int k3 = 4;
        int x3 = 10;
        List<Integer> expected3 = Arrays.asList(2, 3, 4, 5);
        
        List<Integer> result3a = solution.findClosestElements(arr3, k3, x3);
        System.out.println("x larger than all: " + result3a + " - " + 
                         (result3a.equals(expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: k equals array length
        System.out.println("\nTest 4: k equals array length");
        int[] arr4 = {1, 2, 3, 4, 5};
        int k4 = 5;
        int x4 = 3;
        List<Integer> expected4 = Arrays.asList(1, 2, 3, 4, 5);
        
        List<Integer> result4a = solution.findClosestElements(arr4, k4, x4);
        System.out.println("k equals length: " + result4a + " - " + 
                         (result4a.equals(expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Tie breaking (choose smaller element)
        System.out.println("\nTest 5: Tie breaking (choose smaller element)");
        int[] arr5 = {1, 2, 3, 4, 5};
        int k5 = 2;
        int x5 = 3;
        List<Integer> expected5 = Arrays.asList(2, 3); // Not [3,4] because 2 is smaller than 4
        
        List<Integer> result5a = solution.findClosestElements(arr5, k5, x5);
        System.out.println("Tie breaking: " + result5a + " - " + 
                         (result5a.equals(expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Exact match exists
        System.out.println("\nTest 6: Exact match exists");
        int[] arr6 = {1, 2, 3, 4, 5};
        int k6 = 3;
        int x6 = 3;
        List<Integer> expected6 = Arrays.asList(2, 3, 4);
        
        List<Integer> result6a = solution.findClosestElements(arr6, k6, x6);
        System.out.println("Exact match: " + result6a + " - " + 
                         (result6a.equals(expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Large array with negative numbers
        System.out.println("\nTest 7: Large array with negative numbers");
        int[] arr7 = {-5, -3, -1, 1, 3, 5, 7, 9};
        int k7 = 4;
        int x7 = 0;
        List<Integer> expected7 = Arrays.asList(-3, -1, 1, 3);
        
        List<Integer> result7a = solution.findClosestElements(arr7, k7, x7);
        System.out.println("Negative numbers: " + result7a + " - " + 
                         (result7a.equals(expected7) ? "PASSED" : "FAILED"));
        
        // Test case 8: Single element array
        System.out.println("\nTest 8: Single element array");
        int[] arr8 = {5};
        int k8 = 1;
        int x8 = 3;
        List<Integer> expected8 = Arrays.asList(5);
        
        List<Integer> result8a = solution.findClosestElements(arr8, k8, x8);
        System.out.println("Single element: " + result8a + " - " + 
                         (result8a.equals(expected8) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Binary Search: " + time1a + " ns");
        System.out.println("  Two Pointers: " + time1b + " ns");
        System.out.println("  Sliding Window: " + time1c + " ns");
        System.out.println("  Heap: " + time1d + " ns");
        System.out.println("  Custom Sort: " + time1e + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[] largeArr = new int[10000];
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = i * 2; // Even numbers: 0, 2, 4, 6, ...
        }
        int k10 = 100;
        int x10 = 5000;
        
        startTime = System.nanoTime();
        List<Integer> result10a = solution.findClosestElements(largeArr, k10, x10);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result10b = solution.findClosestElementsTwoPointers(largeArr, k10, x10);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result10c = solution.findClosestElementsSlidingWindow(largeArr, k10, x10);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (10,000 elements, k=100):");
        System.out.println("  Binary Search: " + time10a + " ns, Result size: " + result10a.size());
        System.out.println("  Two Pointers: " + time10b + " ns, Result size: " + result10b.size());
        System.out.println("  Sliding Window: " + time10c + " ns, Result size: " + result10c.size());
        
        // Verify all approaches produce the same result
        boolean allEqual = result10a.equals(result10b) && result10a.equals(result10c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Edge case: All elements equal
        System.out.println("\nTest 11: All elements equal");
        int[] arr11 = {5, 5, 5, 5, 5};
        int k11 = 3;
        int x11 = 3;
        List<Integer> expected11 = Arrays.asList(5, 5, 5);
        
        List<Integer> result11a = solution.findClosestElements(arr11, k11, x11);
        System.out.println("All elements equal: " + result11a + " - " + 
                         (result11a.equals(expected11) ? "PASSED" : "FAILED"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(80));
        System.out.println("BINARY SEARCH FOR LEFT BOUND EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nKey Insight:");
        System.out.println("We don't need to find the exact position of x.");
        System.out.println("Instead, we find the left boundary of the optimal k-length window.");
        System.out.println("We compare arr[mid] and arr[mid + k] to decide which direction to go.");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. The optimal window must be a contiguous subarray of length k");
        System.out.println("2. We binary search for the starting index of this window");
        System.out.println("3. At each step, we compare the leftmost and rightmost candidates");
        System.out.println("4. If x is closer to arr[mid + k], we move left to mid + 1");
        System.out.println("5. Otherwise, we move right to mid (including the case of ties)");
        System.out.println("6. This ensures we find the leftmost optimal window");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Binary Search for Left Bound (RECOMMENDED):");
        System.out.println("   Time: O(log (n - k) + k) - Binary search + building result");
        System.out.println("   Space: O(1) excluding output");
        System.out.println("   How it works:");
        System.out.println("     - Binary search for the left boundary of result window");
        System.out.println("     - Compare arr[mid] and arr[mid + k] to decide direction");
        System.out.println("     - Return k elements starting from found boundary");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity O(log n + k)");
        System.out.println("     - O(1) space complexity");
        System.out.println("     - Elegant and efficient");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of the comparison logic");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Two Pointers from Closest Element:");
        System.out.println("   Time: O(log n + k) - Find closest + expand window");
        System.out.println("   Space: O(1) excluding output");
        System.out.println("   How it works:");
        System.out.println("     - Binary search to find closest element to x");
        System.out.println("     - Expand left and right pointers to include k elements");
        System.out.println("     - Choose closer element when expanding");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive approach");
        System.out.println("     - Good time complexity");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex implementation");
        System.out.println("     - Needs careful boundary handling");
        System.out.println("   Best for: Learning purposes, when clarity is important");
        
        System.out.println("\n3. Sliding Window:");
        System.out.println("   Time: O(n) - Shrink window from both ends");
        System.out.println("   Space: O(1) excluding output");
        System.out.println("   How it works:");
        System.out.println("     - Start with entire array as window");
        System.out.println("     - Remove farther element from ends until window size is k");
        System.out.println("     - Compare leftmost and rightmost elements");
        System.out.println("   Pros:");
        System.out.println("     - Simple and easy to understand");
        System.out.println("     - No binary search needed");
        System.out.println("   Cons:");
        System.out.println("     - O(n) time, not optimal for large arrays");
        System.out.println("     - Less efficient than binary search approaches");
        System.out.println("   Best for: Small arrays, educational purposes");
        
        System.out.println("\n4. Heap (Priority Queue) Approach:");
        System.out.println("   Time: O(n log k) - Process each element with heap operations");
        System.out.println("   Space: O(k) - Heap storage");
        System.out.println("   How it works:");
        System.out.println("     - Use max heap to maintain k closest elements");
        System.out.println("     - Compare by distance, then by value for ties");
        System.out.println("     - Sort the result at the end");
        System.out.println("   Pros:");
        System.out.println("     - Works for unsorted arrays");
        System.out.println("     - Demonstrates heap usage");
        System.out.println("   Cons:");
        System.out.println("     - O(n log k) time, not optimal");
        System.out.println("     - O(k) extra space");
        System.out.println("   Best for: Unsorted arrays, learning heap techniques");
        
        System.out.println("\n5. Custom Sort Approach:");
        System.out.println("   Time: O(log n + k log k) - Binary search + sorting");
        System.out.println("   Space: O(k) - Candidate list storage");
        System.out.println("   How it works:");
        System.out.println("     - Find insertion point of x using binary search");
        System.out.println("     - Collect 2k candidate elements around insertion point");
        System.out.println("     - Sort by distance, take first k, sort by value");
        System.out.println("   Pros:");
        System.out.println("     - Straightforward implementation");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(k log k) additional time for sorting");
        System.out.println("     - O(k) extra space");
        System.out.println("   Best for: When simplicity is prioritized over optimality");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. The result must be a contiguous subarray due to sorted property");
        System.out.println("2. When distances are equal, the smaller element is preferred");
        System.out.println("3. The optimal window minimizes the maximum distance to x");
        System.out.println("4. Binary search works because the 'closeness' function is unimodal");
        System.out.println("5. The comparison x - arr[mid] > arr[mid + k] - x handles ties correctly");
        System.out.println("=".repeat(80));
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Binary Search for Left Bound - it's the optimal solution");
        System.out.println("2. Explain the comparison logic clearly: why we compare arr[mid] and arr[mid+k]");
        System.out.println("3. Mention the time complexity O(log n + k) meets follow-up requirement");
        System.out.println("4. Handle edge cases: x outside array bounds, k equals array length");
        System.out.println("5. Discuss tie-breaking rule (choose smaller element)");
        System.out.println("6. Consider mentioning alternative approaches if time permits");
        System.out.println("=".repeat(80));
        
        System.out.println("\nAll tests completed!");
    }
}
