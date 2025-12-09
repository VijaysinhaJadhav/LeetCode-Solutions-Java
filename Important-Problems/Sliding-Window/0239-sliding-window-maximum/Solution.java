
## Solution.java

```java
/**
 * 239. Sliding Window Maximum
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given an array nums and a sliding window of size k moving from left to right,
 * find the maximum element in each window position.
 * 
 * Key Insights:
 * 1. Monotonic Deque: Maintain indices in decreasing order of values
 * 2. Heap Approach: Use max heap but need lazy deletion
 * 3. Dynamic Programming: Precompute left and right maximums
 * 4. The challenge is to efficiently track maximum as window slides
 * 
 * Approach (Monotonic Deque):
 * 1. Use deque to store indices of array elements
 * 2. Maintain decreasing order in deque (front has max)
 * 3. Remove elements from back that are smaller than current
 * 4. Remove elements from front that are out of window
 * 5. Add current index to deque
 * 6. Add front element to result when window size reached
 * 
 * Time Complexity: O(n) - Each element processed at most twice
 * Space Complexity: O(k) - Deque size at most k
 * 
 * Tags: Array, Queue, Sliding Window, Heap, Monotonic Queue
 */

import java.util.*;

public class Solution {
    
    /**
     * Approach 1: Monotonic Deque - RECOMMENDED
     * O(n) time, O(k) space - Optimal solution
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // Store indices
        
        for (int i = 0; i < n; i++) {
            // Remove indices that are out of current window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            
            // Remove from back while current element is greater
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            
            // Add current index to deque
            deque.offerLast(i);
            
            // Add to result when window size is reached
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: Max Heap with Lazy Deletion
     * O(n log n) time, O(n) space - Simple but less efficient
     */
    public int[] maxSlidingWindowHeap(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n - k + 1];
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]); // max heap
        
        for (int i = 0; i < n; i++) {
            // Add current element with its index
            heap.offer(new int[]{nums[i], i});
            
            // Remove elements that are out of current window
            while (!heap.isEmpty() && heap.peek()[1] <= i - k) {
                heap.poll();
            }
            
            // Add to result when window size is reached
            if (i >= k - 1) {
                result[i - k + 1] = heap.peek()[0];
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Dynamic Programming (Left and Right Arrays)
     * O(n) time, O(n) space - Clever partitioning approach
     */
    public int[] maxSlidingWindowDP(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n - k + 1];
        
        // Precompute left and right maximums
        int[] left = new int[n];
        int[] right = new int[n];
        
        left[0] = nums[0];
        right[n - 1] = nums[n - 1];
        
        for (int i = 1; i < n; i++) {
            // Left array: maximum from block start to current
            if (i % k == 0) {
                left[i] = nums[i]; // Start of new block
            } else {
                left[i] = Math.max(left[i - 1], nums[i]);
            }
            
            // Right array: maximum from current to block end
            int j = n - i - 1;
            if ((j + 1) % k == 0) {
                right[j] = nums[j]; // End of block
            } else {
                right[j] = Math.max(right[j + 1], nums[j]);
            }
        }
        
        // Compute result using left and right arrays
        for (int i = 0; i < n - k + 1; i++) {
            // The maximum is max(right[i], left[i + k - 1])
            result[i] = Math.max(right[i], left[i + k - 1]);
        }
        
        return result;
    }
    
    /**
     * Approach 4: Brute Force (For comparison - NOT RECOMMENDED)
     * O(n*k) time, O(1) space - Only for educational purposes
     */
    public int[] maxSlidingWindowBruteForce(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n - k + 1];
        
        for (int i = 0; i <= n - k; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            result[i] = max;
        }
        
        return result;
    }
    
    /**
     * Approach 5: Monotonic Deque with Detailed Comments
     * O(n) time, O(k) space - Same as approach 1 with more explanation
     */
    public int[] maxSlidingWindowDetailed(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // Store indices of elements
        
        for (int i = 0; i < n; i++) {
            // Step 1: Remove indices that are out of the current window
            // The window is [i-k+1, i], so remove indices < i-k+1
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            
            // Step 2: Remove from the back while the current element is greater
            // This maintains the decreasing order in deque
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            
            // Step 3: Add current element's index to the deque
            deque.offerLast(i);
            
            // Step 4: The front of deque is the maximum for current window
            // Start adding to result once we have the first complete window
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        
        return result;
    }
    
    /**
     * Helper method to visualize the sliding window process
     */
    public void visualizeSlidingWindow(int[] nums, int k) {
        System.out.println("\nSliding Window Maximum Visualization:");
        System.out.println("nums = " + Arrays.toString(nums) + ", k = " + k);
        System.out.println();
        
        int n = nums.length;
        Deque<Integer> deque = new ArrayDeque<>();
        int[] result = new int[n - k + 1];
        
        System.out.println("Step | Index | Window | Deque (indices) | Deque (values) | Action");
        System.out.println("-----|-------|--------|-----------------|----------------|--------");
        
        for (int i = 0; i < n; i++) {
            // Current window
            int windowStart = Math.max(0, i - k + 1);
            int windowEnd = i;
            String window = i < k - 1 ? "Incomplete" : 
                           Arrays.toString(Arrays.copyOfRange(nums, i - k + 1, i + 1));
            
            // Remove out-of-window indices
            String action = "";
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                int removed = deque.pollFirst();
                action += "Remove front (out): index " + removed + " value " + nums[removed] + ". ";
            }
            
            // Remove smaller elements from back
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                int removed = deque.pollLast();
                action += "Remove back (smaller): index " + removed + " value " + nums[removed] + ". ";
            }
            
            // Add current
            deque.offerLast(i);
            action += "Add current: index " + i + " value " + nums[i] + ". ";
            
            // Get current deque state
            List<Integer> dequeIndices = new ArrayList<>(deque);
            List<Integer> dequeValues = new ArrayList<>();
            for (int idx : dequeIndices) {
                dequeValues.add(nums[idx]);
            }
            
            // Add to result if window complete
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
                action += "Window complete -> Max = " + nums[deque.peekFirst()];
            }
            
            System.out.printf("%4d | %5d | %-6s | %-15s | %-14s | %s%n",
                i + 1, i, window, dequeIndices, dequeValues, action);
        }
        
        System.out.println("\nFinal Result: " + Arrays.toString(result));
    }
    
    /**
     * Helper method to compare all approaches
     */
    public void compareApproaches(int[] nums, int k) {
        System.out.println("\nComparing All Approaches:");
        System.out.println("nums = " + Arrays.toString(nums) + ", k = " + k);
        System.out.println("=".repeat(70));
        
        long startTime, endTime;
        int[] result;
        
        // Approach 1: Monotonic Deque
        startTime = System.nanoTime();
        result = maxSlidingWindow(nums, k);
        endTime = System.nanoTime();
        System.out.println("1. Monotonic Deque:");
        System.out.println("   Result: " + Arrays.toString(result));
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 2: Max Heap
        startTime = System.nanoTime();
        result = maxSlidingWindowHeap(nums, k);
        endTime = System.nanoTime();
        System.out.println("2. Max Heap:");
        System.out.println("   Result: " + Arrays.toString(result));
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 3: Dynamic Programming
        startTime = System.nanoTime();
        result = maxSlidingWindowDP(nums, k);
        endTime = System.nanoTime();
        System.out.println("3. Dynamic Programming:");
        System.out.println("   Result: " + Arrays.toString(result));
        System.out.println("   Time: " + (endTime - startTime) + " ns");
        
        // Approach 4: Brute Force (only for small inputs)
        if (nums.length <= 100) {
            startTime = System.nanoTime();
            result = maxSlidingWindowBruteForce(nums, k);
            endTime = System.nanoTime();
            System.out.println("4. Brute Force:");
            System.out.println("   Result: " + Arrays.toString(result));
            System.out.println("   Time: " + (endTime - startTime) + " ns");
        }
        
        // Verify all approaches produce same result
        int[] result1 = maxSlidingWindow(nums, k);
        int[] result2 = maxSlidingWindowHeap(nums, k);
        int[] result3 = maxSlidingWindowDP(nums, k);
        boolean allEqual = Arrays.equals(result1, result2) && Arrays.equals(result1, result3);
        System.out.println("All approaches produce same result: " + (allEqual ? "✓" : "✗"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Sliding Window Maximum:");
        System.out.println("================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] nums1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int k1 = 3;
        int[] expected1 = {3, 3, 5, 5, 6, 7};
        
        int[] result1 = solution.maxSlidingWindow(nums1, k1);
        System.out.println("Input: nums = " + Arrays.toString(nums1) + ", k = " + k1);
        System.out.println("Expected: " + Arrays.toString(expected1));
        System.out.println("Result: " + Arrays.toString(result1));
        System.out.println("Test 1: " + (Arrays.equals(expected1, result1) ? "✓ PASSED" : "✗ FAILED"));
        
        // Visualize the sliding window process
        solution.visualizeSlidingWindow(nums1, k1);
        
        // Test case 2: Single element
        System.out.println("\nTest 2: Single element");
        int[] nums2 = {1};
        int k2 = 1;
        int[] expected2 = {1};
        int[] result2 = solution.maxSlidingWindow(nums2, k2);
        System.out.println("Single element: " + (Arrays.equals(expected2, result2) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 3: Decreasing sequence
        System.out.println("\nTest 3: Decreasing sequence");
        int[] nums3 = {7, 6, 5, 4, 3, 2, 1};
        int k3 = 3;
        int[] expected3 = {7, 6, 5, 4, 3};
        int[] result3 = solution.maxSlidingWindow(nums3, k3);
        System.out.println("Decreasing sequence: " + (Arrays.equals(expected3, result3) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 4: Increasing sequence
        System.out.println("\nTest 4: Increasing sequence");
        int[] nums4 = {1, 2, 3, 4, 5, 6, 7};
        int k4 = 3;
        int[] expected4 = {3, 4, 5, 6, 7};
        int[] result4 = solution.maxSlidingWindow(nums4, k4);
        System.out.println("Increasing sequence: " + (Arrays.equals(expected4, result4) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 5: All same elements
        System.out.println("\nTest 5: All same elements");
        int[] nums5 = {2, 2, 2, 2, 2};
        int k5 = 3;
        int[] expected5 = {2, 2, 2};
        int[] result5 = solution.maxSlidingWindow(nums5, k5);
        System.out.println("All same elements: " + (Arrays.equals(expected5, result5) ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 6: Window size equals array size
        System.out.println("\nTest 6: Window size equals array size");
        int[] nums6 = {1, 3, 2, 5, 4};
        int k6 = 5;
        int[] expected6 = {5};
        int[] result6 = solution.maxSlidingWindow(nums6, k6);
        System.out.println("Window equals array: " + (Arrays.equals(expected6, result6) ? "✓ PASSED" : "✗ FAILED"));
        
        // Compare all approaches
        System.out.println("\nPerformance Comparison:");
        solution.compareApproaches(nums1, k1);
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        int[] nums7 = generateTestArray(10000);
        int k7 = 100;
        
        long startTime = System.nanoTime();
        int[] result7 = solution.maxSlidingWindow(nums7, k7);
        long endTime = System.nanoTime();
        System.out.println("Large input (10000 elements, k=100): " + (endTime - startTime) + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MONOTONIC DEQUE ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We maintain a deque that stores indices in decreasing order of their values.");
        System.out.println("The front of deque always contains the maximum for the current window.");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. Decreasing order ensures front is maximum");
        System.out.println("2. Removing smaller elements from back maintains order");
        System.out.println("3. Removing out-of-window indices keeps deque relevant");
        System.out.println("4. Each element is processed at most twice (added and removed)");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Remove from front: indices that are out of current window");
        System.out.println("2. Remove from back: indices with values smaller than current");
        System.out.println("3. Add current index to back of deque");
        System.out.println("4. Front of deque is maximum for current complete window");
        
        System.out.println("\nTime Complexity: O(n)");
        System.out.println("- Each element is added to deque once: O(n)");
        System.out.println("- Each element is removed from deque at most once: O(n)");
        System.out.println("- Total: O(2n) = O(n)");
        
        System.out.println("\nSpace Complexity: O(k)");
        System.out.println("- Deque stores at most k elements");
        System.out.println("- In worst case (decreasing sequence), deque stores all k elements");
        
        // Comparison with other approaches
        System.out.println("\n" + "=".repeat(70));
        System.out.println("APPROACH COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Monotonic Deque (RECOMMENDED):");
        System.out.println("   Time: O(n) - Optimal");
        System.out.println("   Space: O(k) - Efficient");
        System.out.println("   Pros: Fastest, elegant, handles all cases");
        System.out.println("   Cons: Requires understanding of monotonic queues");
        
        System.out.println("\n2. Max Heap:");
        System.out.println("   Time: O(n log n) - Slower");
        System.out.println("   Space: O(n) - More memory");
        System.out.println("   Pros: Simple to implement, intuitive");
        System.out.println("   Cons: Slower due to heap operations, lazy deletion");
        
        System.out.println("\n3. Dynamic Programming:");
        System.out.println("   Time: O(n) - Optimal");
        System.out.println("   Space: O(n) - More memory");
        System.out.println("   Pros: No complex data structures");
        System.out.println("   Cons: Less intuitive, requires partitioning logic");
        
        System.out.println("\n4. Brute Force:");
        System.out.println("   Time: O(n*k) - Too slow for large inputs");
        System.out.println("   Space: O(1) - Minimal memory");
        System.out.println("   Pros: Very simple");
        System.out.println("   Cons: Only for very small inputs");
        
        // Edge cases and handling
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. k = 1: Return the original array");
        System.out.println("2. k = n: Return array with single maximum element");
        System.out.println("3. All elements same: Return array of same values");
        System.out.println("4. Strictly increasing: Maximum is always the rightmost");
        System.out.println("5. Strictly decreasing: Maximum is always the leftmost");
        System.out.println("6. Large k close to n: Deque remains small");
        System.out.println("7. Small k: Frequent updates to deque");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with brute force discussion (for completeness)");
        System.out.println("2. Explain why O(n*k) is insufficient for large inputs");
        System.out.println("3. Propose heap approach and discuss its limitations");
        System.out.println("4. Introduce monotonic deque as optimal solution");
        System.out.println("5. Draw the deque operations step by step");
        System.out.println("6. Handle all edge cases explicitly");
        System.out.println("7. Discuss time/space complexity thoroughly");
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to generate test arrays
     */
    private static int[] generateTestArray(int size) {
        Random random = new Random(42);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(20001) - 10000; // Range: -10000 to 10000
        }
        return arr;
    }
}
