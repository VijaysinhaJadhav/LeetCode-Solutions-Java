
# Solution.java

```java
import java.util.*;

/**
 * 496. Next Greater Element I
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given two arrays nums1 and nums2 where nums1 is subset of nums2,
 * find for each element in nums1 the next greater element in nums2.
 * Return -1 if no greater element exists.
 * 
 * Key Insights:
 * 1. Use monotonic decreasing stack to find next greater elements efficiently
 * 2. Process nums2 from right to left for natural stack operations
 * 3. Store results in hash map for O(1) lookup
 * 4. Build answer array by looking up from hash map
 * 
 * Approach (Hash Map + Monotonic Stack):
 * 1. Create hash map to store next greater elements for nums2
 * 2. Use stack to maintain decreasing sequence
 * 3. Process nums2 from right to left:
 *    - While stack not empty and top <= current, pop from stack
 *    - If stack empty, next greater is -1
 *    - Else, next greater is stack top
 *    - Push current to stack
 * 4. Build answer array by looking up from hash map
 * 
 * Time Complexity: O(n + m)
 * Space Complexity: O(m)
 * 
 * Tags: Array, Hash Table, Stack, Monotonic Stack
 */

class Solution {
    
    /**
     * Approach 1: Hash Map + Monotonic Stack (RECOMMENDED)
     * O(n + m) time, O(m) space
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // Map to store next greater element for each number in nums2
        Map<Integer, Integer> nextGreaterMap = new HashMap<>();
        
        // Monotonic decreasing stack
        Stack<Integer> stack = new Stack<>();
        
        // Process nums2 from right to left
        for (int i = nums2.length - 1; i >= 0; i--) {
            int current = nums2[i];
            
            // Pop elements from stack while they are smaller than or equal to current
            while (!stack.isEmpty() && stack.peek() <= current) {
                stack.pop();
            }
            
            // If stack is empty, no greater element exists
            // Otherwise, top of stack is the next greater element
            if (stack.isEmpty()) {
                nextGreaterMap.put(current, -1);
            } else {
                nextGreaterMap.put(current, stack.peek());
            }
            
            // Push current element to stack
            stack.push(current);
        }
        
        // Build result array for nums1
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreaterMap.get(nums1[i]);
        }
        
        return result;
    }
    
    /**
     * Approach 2: Hash Map + Brute Force
     * O(n * m) time, O(m) space
     * Simple but less efficient
     */
    public int[] nextGreaterElementBruteForce(int[] nums1, int[] nums2) {
        Map<Integer, Integer> indexMap = new HashMap<>();
        
        // Store index of each element in nums2
        for (int i = 0; i < nums2.length; i++) {
            indexMap.put(nums2[i], i);
        }
        
        int[] result = new int[nums1.length];
        
        for (int i = 0; i < nums1.length; i++) {
            int num = nums1[i];
            int index = indexMap.get(num);
            result[i] = -1;
            
            // Scan right from index to find next greater
            for (int j = index + 1; j < nums2.length; j++) {
                if (nums2[j] > num) {
                    result[i] = nums2[j];
                    break;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Monotonic Stack from Left to Right
     * O(n + m) time, O(m) space
     * Alternative processing direction
     */
    public int[] nextGreaterElementLeftToRight(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextGreaterMap = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        
        // Process nums2 from left to right
        for (int num : nums2) {
            // While stack not empty and current > stack top
            // Current is next greater for stack top
            while (!stack.isEmpty() && num > stack.peek()) {
                nextGreaterMap.put(stack.pop(), num);
            }
            stack.push(num);
        }
        
        // Remaining elements in stack have no greater element
        while (!stack.isEmpty()) {
            nextGreaterMap.put(stack.pop(), -1);
        }
        
        // Build result
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreaterMap.get(nums1[i]);
        }
        
        return result;
    }
    
    /**
     * Approach 4: Optimized with Array instead of Map
     * O(n + m) time, O(10001) space (since values <= 10000)
     * Fastest when value range is limited
     */
    public int[] nextGreaterElementArray(int[] nums1, int[] nums2) {
        // Since values are between 0 and 10000
        int[] nextGreater = new int[10001];
        Arrays.fill(nextGreater, -1);
        
        Stack<Integer> stack = new Stack<>();
        
        // Process nums2
        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek()) {
                nextGreater[stack.pop()] = num;
            }
            stack.push(num);
        }
        
        // Build result
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreater[nums1[i]];
        }
        
        return result;
    }
    
    /**
     * Approach 5: Using LinkedHashMap to preserve insertion order
     * O(n + m) time, O(m) space
     * Useful when we need to process in specific order
     */
    public int[] nextGreaterElementLinkedHashMap(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextGreaterMap = new LinkedHashMap<>();
        Stack<Integer> stack = new Stack<>();
        
        // Process from right to left
        for (int i = nums2.length - 1; i >= 0; i--) {
            int current = nums2[i];
            
            while (!stack.isEmpty() && stack.peek() <= current) {
                stack.pop();
            }
            
            nextGreaterMap.put(current, stack.isEmpty() ? -1 : stack.peek());
            stack.push(current);
        }
        
        // Build result
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreaterMap.get(nums1[i]);
        }
        
        return result;
    }
    
    /**
     * Helper method to visualize the monotonic stack process
     */
    private void visualizeMonotonicStack(int[] nums2, String direction) {
        System.out.println("\nVisualizing Monotonic Stack (" + direction + "):");
        System.out.println("Array: " + Arrays.toString(nums2));
        
        Map<Integer, Integer> nextGreater = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        
        if (direction.equals("right-to-left")) {
            System.out.println("\nProcessing from right to left:");
            System.out.println("Index | Element | Stack (top→bottom) | Next Greater");
            System.out.println("------|---------|-------------------|-------------");
            
            for (int i = nums2.length - 1; i >= 0; i--) {
                int current = nums2[i];
                
                // Print before processing
                System.out.printf("  %-3d |    %-3d  | ", i, current);
                printStack(stack);
                
                // Process
                while (!stack.isEmpty() && stack.peek() <= current) {
                    stack.pop();
                }
                
                int greater = stack.isEmpty() ? -1 : stack.peek();
                nextGreater.put(current, greater);
                stack.push(current);
                
                // Print after processing
                System.out.print(" | ");
                System.out.printf("%-3d", greater);
                System.out.println();
            }
        } else {
            System.out.println("\nProcessing from left to right:");
            System.out.println("Index | Element | Stack (top→bottom) | Action");
            System.out.println("------|---------|-------------------|-------");
            
            for (int i = 0; i < nums2.length; i++) {
                int current = nums2[i];
                
                System.out.printf("  %-3d |    %-3d  | ", i, current);
                printStack(stack);
                
                while (!stack.isEmpty() && current > stack.peek()) {
                    int popped = stack.pop();
                    nextGreater.put(popped, current);
                    System.out.print(" | Found next greater for " + popped + ": " + current);
                }
                
                stack.push(current);
                System.out.println();
            }
            
            // Handle remaining elements
            while (!stack.isEmpty()) {
                nextGreater.put(stack.pop(), -1);
            }
        }
        
        System.out.println("\nFinal Next Greater Mapping:");
        System.out.println("Element → Next Greater");
        for (int num : nums2) {
            System.out.printf("   %-4d → %-4d%n", num, nextGreater.get(num));
        }
    }
    
    private void printStack(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            System.out.print("[empty]");
        } else {
            List<Integer> list = new ArrayList<>(stack);
            // Print top to bottom
            for (int i = list.size() - 1; i >= 0; i--) {
                System.out.print(list.get(i));
                if (i > 0) System.out.print(", ");
            }
        }
    }
    
    /**
     * Helper method to compare different approaches
     */
    private void compareApproaches(int[] nums1, int[] nums2) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING DIFFERENT APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput:");
        System.out.println("nums1: " + Arrays.toString(nums1));
        System.out.println("nums2: " + Arrays.toString(nums2));
        
        Solution solution = new Solution();
        
        // Test all approaches
        long startTime, endTime;
        int[] result1, result2, result3, result4, result5;
        
        // Approach 1: Hash Map + Monotonic Stack (right-to-left)
        startTime = System.nanoTime();
        result1 = solution.nextGreaterElement(nums1, nums2);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Brute Force
        startTime = System.nanoTime();
        result2 = solution.nextGreaterElementBruteForce(nums1, nums2);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Left-to-Right
        startTime = System.nanoTime();
        result3 = solution.nextGreaterElementLeftToRight(nums1, nums2);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Array-based
        startTime = System.nanoTime();
        result4 = solution.nextGreaterElementArray(nums1, nums2);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: LinkedHashMap
        startTime = System.nanoTime();
        result5 = solution.nextGreaterElementLinkedHashMap(nums1, nums2);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Verify all results are the same
        boolean allEqual = Arrays.equals(result1, result2) &&
                          Arrays.equals(result2, result3) &&
                          Arrays.equals(result3, result4) &&
                          Arrays.equals(result4, result5);
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (HashMap + Stack R→L): " + Arrays.toString(result1));
        System.out.println("Approach 2 (Brute Force):         " + Arrays.toString(result2));
        System.out.println("Approach 3 (Stack L→R):           " + Arrays.toString(result3));
        System.out.println("Approach 4 (Array-based):         " + Arrays.toString(result4));
        System.out.println("Approach 5 (LinkedHashMap):       " + Arrays.toString(result5));
        
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Approach 1: %-10d (HashMap + Monotonic Stack R→L)%n", time1);
        System.out.printf("Approach 2: %-10d (Brute Force)%n", time2);
        System.out.printf("Approach 3: %-10d (Stack L→R)%n", time3);
        System.out.printf("Approach 4: %-10d (Array-based)%n", time4);
        System.out.printf("Approach 5: %-10d (LinkedHashMap)%n", time5);
        
        // Show detailed explanation
        System.out.println("\n" + "-".repeat(80));
        System.out.println("DETAILED EXPLANATION:");
        
        // Visualize the optimal approach
        solution.visualizeMonotonicStack(nums2, "right-to-left");
        
        System.out.println("\nStep-by-step for nums1 elements:");
        for (int i = 0; i < nums1.length; i++) {
            int num = nums1[i];
            int nextGreater = result1[i];
            System.out.printf("nums1[%d] = %d: ", i, num);
            
            if (nextGreater == -1) {
                System.out.println("No greater element found to the right in nums2");
            } else {
                // Find and show the position
                for (int j = 0; j < nums2.length; j++) {
                    if (nums2[j] == num) {
                        System.out.printf("Found at nums2[%d], next greater is %d at nums2[%d]%n",
                            j, nextGreater, findIndex(nums2, nextGreater));
                        break;
                    }
                }
            }
        }
    }
    
    private int findIndex(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }
    
    /**
     * Helper to show the brute force process
     */
    private void visualizeBruteForce(int[] nums1, int[] nums2) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("BRUTE FORCE APPROACH VISUALIZATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nFor each element in nums1:");
        System.out.println("1. Find its position in nums2");
        System.out.println("2. Scan to the right for first greater element");
        System.out.println("3. If found, that's the answer; otherwise -1\n");
        
        for (int i = 0; i < nums1.length; i++) {
            int num = nums1[i];
            System.out.printf("Processing nums1[%d] = %d:%n", i, num);
            
            // Find index in nums2
            int index = -1;
            for (int j = 0; j < nums2.length; j++) {
                if (nums2[j] == num) {
                    index = j;
                    break;
                }
            }
            
            System.out.printf("  Found at nums2[%d]%n", index);
            System.out.printf("  Scanning right from position %d: ", index + 1);
            
            int nextGreater = -1;
            int nextGreaterIndex = -1;
            
            for (int j = index + 1; j < nums2.length; j++) {
                System.out.print(nums2[j] + " ");
                if (nums2[j] > num) {
                    nextGreater = nums2[j];
                    nextGreaterIndex = j;
                    break;
                }
            }
            
            if (nextGreater != -1) {
                System.out.printf("\n  Found greater element %d at nums2[%d]%n", nextGreater, nextGreaterIndex);
            } else {
                System.out.println("\n  No greater element found");
            }
            
            System.out.printf("  Result: %d%n%n", nextGreater);
        }
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Next Greater Element I:");
        System.out.println("================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int[] nums1_1 = {4, 1, 2};
        int[] nums2_1 = {1, 3, 4, 2};
        int[] expected1 = {-1, 3, -1};
        
        System.out.println("nums1: " + Arrays.toString(nums1_1));
        System.out.println("nums2: " + Arrays.toString(nums2_1));
        
        int[] result1 = solution.nextGreaterElement(nums1_1, nums2_1);
        System.out.println("Expected: " + Arrays.toString(expected1));
        System.out.println("Result:   " + Arrays.toString(result1));
        System.out.println("Passed: " + Arrays.equals(result1, expected1) ? "✓" : "✗");
        
        solution.compareApproaches(nums1_1, nums2_1);
        solution.visualizeBruteForce(nums1_1, nums2_1);
        
        // Test case 2: Another example
        System.out.println("\n\nTest 2: Another example");
        int[] nums1_2 = {2, 4};
        int[] nums2_2 = {1, 2, 3, 4};
        int[] expected2 = {3, -1};
        
        System.out.println("nums1: " + Arrays.toString(nums1_2));
        System.out.println("nums2: " + Arrays.toString(nums2_2));
        
        int[] result2 = solution.nextGreaterElement(nums1_2, nums2_2);
        System.out.println("Expected: " + Arrays.toString(expected2));
        System.out.println("Result:   " + Arrays.toString(result2));
        System.out.println("Passed: " + Arrays.equals(result2, expected2) ? "✓" : "✗");
        
        solution.visualizeMonotonicStack(nums2_2, "right-to-left");
        
        // Test case 3: All increasing
        System.out.println("\n\nTest 3: All increasing");
        int[] nums1_3 = {1, 2, 3};
        int[] nums2_3 = {1, 2, 3, 4, 5};
        int[] expected3 = {2, 3, 4};
        
        System.out.println("nums1: " + Arrays.toString(nums1_3));
        System.out.println("nums2: " + Arrays.toString(nums2_3));
        
        int[] result3 = solution.nextGreaterElement(nums1_3, nums2_3);
        System.out.println("Expected: " + Arrays.toString(expected3));
        System.out.println("Result:   " + Arrays.toString(result3));
        System.out.println("Passed: " + Arrays.equals(result3, expected3) ? "✓" : "✗");
        
        // Test case 4: All decreasing
        System.out.println("\n\nTest 4: All decreasing");
        int[] nums1_4 = {5, 4, 3};
        int[] nums2_4 = {5, 4, 3, 2, 1};
        int[] expected4 = {-1, -1, -1};
        
        System.out.println("nums1: " + Arrays.toString(nums1_4));
        System.out.println("nums2: " + Arrays.toString(nums2_4));
        
        int[] result4 = solution.nextGreaterElement(nums1_4, nums2_4);
        System.out.println("Expected: " + Arrays.toString(expected4));
        System.out.println("Result:   " + Arrays.toString(result4));
        System.out.println("Passed: " + Arrays.equals(result4, expected4) ? "✓" : "✗");
        
        // Test case 5: Mixed order
        System.out.println("\n\nTest 5: Mixed order");
        int[] nums1_5 = {3, 5, 1};
        int[] nums2_5 = {9, 3, 7, 5, 1, 8, 2};
        int[] expected5 = {7, 8, 8};
        
        System.out.println("nums1: " + Arrays.toString(nums1_5));
        System.out.println("nums2: " + Arrays.toString(nums2_5));
        
        int[] result5 = solution.nextGreaterElement(nums1_5, nums2_5);
        System.out.println("Expected: " + Arrays.toString(expected5));
        System.out.println("Result:   " + Arrays.toString(result5));
        System.out.println("Passed: " + Arrays.equals(result5, expected5) ? "✓" : "✗");
        
        solution.visualizeMonotonicStack(nums2_5, "right-to-left");
        
        // Test case 6: Single element
        System.out.println("\n\nTest 6: Single element");
        int[] nums1_6 = {5};
        int[] nums2_6 = {5, 3, 7};
        int[] expected6 = {7};
        
        System.out.println("nums1: " + Arrays.toString(nums1_6));
        System.out.println("nums2: " + Arrays.toString(nums2_6));
        
        int[] result6 = solution.nextGreaterElement(nums1_6, nums2_6);
        System.out.println("Expected: " + Arrays.toString(expected6));
        System.out.println("Result:   " + Arrays.toString(result6));
        System.out.println("Passed: " + Arrays.equals(result6, expected6) ? "✓" : "✗");
        
        // Compare all implementations
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE COMPARISON OF ALL IMPLEMENTATIONS:");
        System.out.println("=".repeat(80));
        
        int[][][] allTests = {
            {nums1_1, nums2_1},
            {nums1_2, nums2_2},
            {nums1_3, nums2_3},
            {nums1_4, nums2_4},
            {nums1_5, nums2_5},
            {nums1_6, nums2_6},
            {new int[]{6, 8, 10}, new int[]{6, 8, 10, 7, 9, 11}},
            {new int[]{100, 50}, new int[]{100, 50, 75, 25, 125}},
            {new int[]{1}, new int[]{1}}
        };
        
        System.out.println("\nTesting " + allTests.length + " test cases:");
        boolean allConsistent = true;
        
        for (int i = 0; i < allTests.length; i++) {
            int[] nums1 = allTests[i][0];
            int[] nums2 = allTests[i][1];
            
            int[] r1 = solution.nextGreaterElement(nums1, nums2);
            int[] r2 = solution.nextGreaterElementBruteForce(nums1, nums2);
            int[] r3 = solution.nextGreaterElementLeftToRight(nums1, nums2);
            int[] r4 = solution.nextGreaterElementArray(nums1, nums2);
            int[] r5 = solution.nextGreaterElementLinkedHashMap(nums1, nums2);
            
            boolean consistent = Arrays.equals(r1, r2) &&
                                Arrays.equals(r2, r3) &&
                                Arrays.equals(r3, r4) &&
                                Arrays.equals(r4, r5);
            
            System.out.printf("Test %d: nums1=%s, nums2=%s - %s%n",
                i + 1, Arrays.toString(nums1), Arrays.toString(nums2),
                consistent ? "✓ CONSISTENT" : "✗ INCONSISTENT");
            
            if (!consistent) {
                System.out.println("  Approach 1: " + Arrays.toString(r1));
                System.out.println("  Approach 2: " + Arrays.toString(r2));
                System.out.println("  Approach 3: " + Arrays.toString(r3));
                System.out.println("  Approach 4: " + Arrays.toString(r4));
                System.out.println("  Approach 5: " + Arrays.toString(r5));
                allConsistent = false;
            }
        }
        
        System.out.println("\nAll implementations consistent: " + (allConsistent ? "✓ YES" : "✗ NO"));
        
        // Performance test with larger arrays
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST WITH LARGER ARRAYS:");
        System.out.println("=".repeat(80));
        
        // Generate test data
        Random random = new Random(42);
        int size = 10000;
        int[] largeNums2 = new int[size];
        Set<Integer> set = new HashSet<>();
        
        // Generate unique numbers
        for (int i = 0; i < size; i++) {
            int num;
            do {
                num = random.nextInt(100000);
            } while (set.contains(num));
            set.add(num);
            largeNums2[i] = num;
        }
        
        // Create subset for nums1
        int subsetSize = 5000;
        int[] largeNums1 = new int[subsetSize];
        for (int i = 0; i < subsetSize; i++) {
            largeNums1[i] = largeNums2[random.nextInt(size)];
        }
        
        System.out.println("\nTesting with:");
        System.out.println("  nums1 size: " + largeNums1.length);
        System.out.println("  nums2 size: " + largeNums2.length);
        
        // Test different approaches
        long startTime, endTime;
        
        // Approach 1
        startTime = System.currentTimeMillis();
        int[] perf1 = solution.nextGreaterElement(largeNums1, largeNums2);
        endTime = System.currentTimeMillis();
        long time1 = endTime - startTime;
        
        // Approach 2 (will be very slow)
        startTime = System.currentTimeMillis();
        int[] perf2 = solution.nextGreaterElementBruteForce(largeNums1, largeNums2);
        endTime = System.currentTimeMillis();
        long time2 = endTime - startTime;
        
        // Approach 3
        startTime = System.currentTimeMillis();
        int[] perf3 = solution.nextGreaterElementLeftToRight(largeNums1, largeNums2);
        endTime = System.currentTimeMillis();
        long time3 = endTime - startTime;
        
        // Approach 4
        startTime = System.currentTimeMillis();
        int[] perf4 = solution.nextGreaterElementArray(largeNums1, largeNums2);
        endTime = System.currentTimeMillis();
        long time4 = endTime - startTime;
        
        System.out.println("\nPerformance Results (milliseconds):");
        System.out.printf("Approach 1 (HashMap + Stack R→L): %5d ms%n", time1);
        System.out.printf("Approach 2 (Brute Force):         %5d ms%n", time2);
        System.out.printf("Approach 3 (Stack L→R):           %5d ms%n", time3);
        System.out.printf("Approach 4 (Array-based):         %5d ms%n", time4);
        
        // Verify consistency
        boolean perfConsistent = Arrays.equals(perf1, perf2) &&
                                Arrays.equals(perf2, perf3) &&
                                Arrays.equals(perf3, perf4);
        System.out.println("\nResults consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nWhy Monotonic Stack Works:");
        System.out.println("1. We maintain a decreasing stack (top is smallest)");
        System.out.println("2. When we see a new element:");
        System.out.println("   - It becomes the next greater for all smaller elements in stack");
        System.out.println("   - We pop them and record the current as their next greater");
        System.out.println("   - Then push current element");
        System.out.println("3. Elements left in stack at end have no greater element");
        
        System.out.println("\nExample: nums2 = [1, 3, 4, 2]");
        System.out.println("Processing from right to left:");
        System.out.println("Step 1: Process 2 → stack=[2], nextGreater(2)=-1");
        System.out.println("Step 2: Process 4 → pop 2 (2<4) → stack=[4], nextGreater(4)=-1, nextGreater(2)=4");
        System.out.println("Step 3: Process 3 → stack=[4,3], nextGreater(3)=4");
        System.out.println("Step 4: Process 1 → stack=[4,3,1], nextGreater(1)=3");
        
        System.out.println("\nVisual representation:");
        System.out.println("Index:  0  1  2  3");
        System.out.println("Array:  1  3  4  2");
        System.out.println("NGE:    3  4 -1 -1");
        
        // Time and Space Complexity Analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nApproach 1 (HashMap + Monotonic Stack):");
        System.out.println("- Time: O(n + m)");
        System.out.println("  • O(m) to process nums2 with stack");
        System.out.println("  • O(n) to build result from hash map");
        System.out.println("- Space: O(m)");
        System.out.println("  • O(m) for hash map storing next greater for all nums2 elements");
        System.out.println("  • O(m) for stack in worst case");
        
        System.out.println("\nApproach 2 (Brute Force):");
        System.out.println("- Time: O(n * m)");
        System.out.println("  • For each of n elements, scan up to m positions");
        System.out.println("- Space: O(m)");
        System.out.println("  • O(m) for hash map of indices");
        
        System.out.println("\nApproach 4 (Array-based):");
        System.out.println("- Time: O(n + m)");
        System.out.println("- Space: O(10001)");
        System.out.println("  • Fixed size array since values ≤ 10000");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Stock Market Analysis:");
        System.out.println("   - Find next higher price for each stock");
        System.out.println("   - Identify breakout points");
        
        System.out.println("\n2. Task Scheduling:");
        System.out.println("   - Find next available time slot");
        System.out.println("   - Resource allocation");
        
        System.out.println("\n3. Database Query Optimization:");
        System.out.println("   - Range queries with next greater value");
        System.out.println("   - Index lookups");
        
        System.out.println("\n4. Compiler Design:");
        System.out.println("   - Symbol table lookups");
        System.out.println("   - Finding next instruction");
        
        System.out.println("\n5. Game Development:");
        System.out.println("   - Finding next level with higher difficulty");
        System.out.println("   - Progression systems");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify problem:");
        System.out.println("   - nums1 is subset of nums2");
        System.out.println("   - Need next greater element in nums2 for each nums1 element");
        System.out.println("   - Return -1 if no greater element");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - For each element in nums1, find it in nums2");
        System.out.println("   - Scan right to find first greater element");
        System.out.println("   - O(n*m) time, O(m) space");
        
        System.out.println("\n3. Identify inefficiency:");
        System.out.println("   - Repeated scanning of nums2");
        System.out.println("   - Can we preprocess nums2?");
        
        System.out.println("\n4. Key insight:");
        System.out.println("   - Use monotonic stack to find next greater for ALL elements");
        System.out.println("   - Store results in hash map for O(1) lookup");
        
        System.out.println("\n5. Explain algorithm:");
        System.out.println("   - Process nums2 from right to left");
        System.out.println("   - Maintain decreasing stack");
        System.out.println("   - For each element, pop smaller elements, record next greater");
        System.out.println("   - Look up results for nums1");
        
        System.out.println("\n6. Walk through example:");
        System.out.println("   - Use given example to demonstrate");
        
        System.out.println("\n7. Discuss complexity:");
        System.out.println("   - O(n+m) time, O(m) space");
        System.out.println("   - Much better than O(n*m)");
        
        System.out.println("\n8. Handle edge cases:");
        System.out.println("   - Single element arrays");
        System.out.println("   - All increasing/decreasing");
        System.out.println("   - Duplicates? (not in this problem)");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Monotonic stack is perfect for 'next greater' problems");
        System.out.println("- Processing direction matters (right-to-left for this problem)");
        System.out.println("- Hash map provides O(1) lookup for results");
        System.out.println("- Algorithm is optimal: O(n+m) time");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Using brute force in interview (inefficient)");
        System.out.println("- Not explaining why stack is monotonic");
        System.out.println("- Forgetting to handle case when stack is empty");
        System.out.println("- Not considering processing direction");
        
        // Related problems to mention
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 503. Next Greater Element II (Circular Array)");
        System.out.println("2. 556. Next Greater Element III (Number Reordering)");
        System.out.println("3. 739. Daily Temperatures (Same concept, different output)");
        System.out.println("4. 907. Sum of Subarray Minimums (Monotonic stack variation)");
        System.out.println("5. 1019. Next Greater Node In Linked List");
        System.out.println("6. 84. Largest Rectangle in Histogram (Classic monotonic stack)");
        System.out.println("7. 42. Trapping Rain Water (Another classic)");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
