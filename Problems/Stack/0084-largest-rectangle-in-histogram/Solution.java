
## Solution.java

```java
/**
 * 84. Largest Rectangle in Histogram
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given an array of integers heights representing the histogram's bar height where 
 * the width of each bar is 1, return the area of the largest rectangle in the histogram.
 * 
 * Key Insights:
 * 1. For each bar, the largest rectangle that includes that bar has height = bar's height
 * 2. The width extends until we find a smaller bar on left and right
 * 3. Use monotonic stack to find next smaller element on both sides efficiently
 * 4. Area = height[i] * (right_smaller - left_smaller - 1)
 * 
 * Approach (Monotonic Stack):
 * 1. Use stack to maintain indices of bars in increasing height order
 * 2. When current bar is smaller than stack top, it's the right boundary for stack top
 * 3. Left boundary is the next index in stack after popping
 * 4. Calculate area for each popped bar
 * 5. Add sentinel values to handle boundaries
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 * 
 * Tags: Array, Stack, Monotonic Stack
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Monotonic Stack (Optimal) - RECOMMENDED
     * O(n) time, O(n) space
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        if (n == 0) return 0;
        
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        
        for (int i = 0; i <= n; i++) {
            // Current height: if i == n, use 0 as sentinel value
            int currentHeight = (i == n) ? 0 : heights[i];
            
            // Pop from stack while current height is smaller than stack top height
            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                // Width extends from the next index in stack to current index - 1
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }
        
        return maxArea;
    }
    
    /**
     * Approach 2: Monotonic Stack with Explicit Left/Right Boundaries
     * O(n) time, O(n) space
     * Precompute left and right boundaries for each bar
     */
    public int largestRectangleAreaWithBoundaries(int[] heights) {
        int n = heights.length;
        if (n == 0) return 0;
        
        int[] leftBoundary = new int[n];  // Index of first smaller bar on left
        int[] rightBoundary = new int[n]; // Index of first smaller bar on right
        Stack<Integer> stack = new Stack<>();
        
        // Compute left boundaries
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            leftBoundary[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        
        stack.clear();
        
        // Compute right boundaries
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            rightBoundary[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        
        // Calculate maximum area
        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            int width = rightBoundary[i] - leftBoundary[i] - 1;
            maxArea = Math.max(maxArea, heights[i] * width);
        }
        
        return maxArea;
    }
    
    /**
     * Approach 3: Divide and Conquer
     * O(n log n) average case, O(n²) worst case
     * Find minimum height in range and recursively solve left and right
     */
    public int largestRectangleAreaDivideConquer(int[] heights) {
        return calculateArea(heights, 0, heights.length - 1);
    }
    
    private int calculateArea(int[] heights, int start, int end) {
        if (start > end) return 0;
        if (start == end) return heights[start];
        
        // Find index of minimum height in current range
        int minIndex = start;
        for (int i = start + 1; i <= end; i++) {
            if (heights[i] < heights[minIndex]) {
                minIndex = i;
            }
        }
        
        // Calculate area with minimum height as the bar
        int areaWithMin = heights[minIndex] * (end - start + 1);
        
        // Calculate maximum area in left and right subarrays
        int leftArea = calculateArea(heights, start, minIndex - 1);
        int rightArea = calculateArea(heights, minIndex + 1, end);
        
        return Math.max(areaWithMin, Math.max(leftArea, rightArea));
    }
    
    /**
     * Approach 4: Optimized Divide and Conquer with Segment Tree
     * O(n log n) time, O(n) space
     * Uses segment tree to find minimum efficiently
     */
    public int largestRectangleAreaSegmentTree(int[] heights) {
        if (heights.length == 0) return 0;
        SegmentTree st = new SegmentTree(heights);
        return calculateAreaWithSegmentTree(heights, st, 0, heights.length - 1);
    }
    
    private int calculateAreaWithSegmentTree(int[] heights, SegmentTree st, int start, int end) {
        if (start > end) return 0;
        if (start == end) return heights[start];
        
        int minIndex = st.query(heights, start, end);
        int areaWithMin = heights[minIndex] * (end - start + 1);
        
        int leftArea = calculateAreaWithSegmentTree(heights, st, start, minIndex - 1);
        int rightArea = calculateAreaWithSegmentTree(heights, st, minIndex + 1, end);
        
        return Math.max(areaWithMin, Math.max(leftArea, rightArea));
    }
    
    /**
     * Segment Tree helper class for efficient range minimum queries
     */
    class SegmentTree {
        private int[] tree;
        private int n;
        
        public SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];
            buildTree(arr, 0, 0, n - 1);
        }
        
        private void buildTree(int[] arr, int treeIndex, int left, int right) {
            if (left == right) {
                tree[treeIndex] = left;
                return;
            }
            
            int mid = left + (right - left) / 2;
            buildTree(arr, 2 * treeIndex + 1, left, mid);
            buildTree(arr, 2 * treeIndex + 2, mid + 1, right);
            
            int leftMinIndex = tree[2 * treeIndex + 1];
            int rightMinIndex = tree[2 * treeIndex + 2];
            tree[treeIndex] = arr[leftMinIndex] <= arr[rightMinIndex] ? leftMinIndex : rightMinIndex;
        }
        
        public int query(int[] arr, int queryLeft, int queryRight) {
            return query(arr, 0, 0, n - 1, queryLeft, queryRight);
        }
        
        private int query(int[] arr, int treeIndex, int left, int right, int queryLeft, int queryRight) {
            if (queryLeft > right || queryRight < left) return -1;
            if (queryLeft <= left && queryRight >= right) return tree[treeIndex];
            
            int mid = left + (right - left) / 2;
            int leftIndex = query(arr, 2 * treeIndex + 1, left, mid, queryLeft, queryRight);
            int rightIndex = query(arr, 2 * treeIndex + 2, mid + 1, right, queryLeft, queryRight);
            
            if (leftIndex == -1) return rightIndex;
            if (rightIndex == -1) return leftIndex;
            return arr[leftIndex] <= arr[rightIndex] ? leftIndex : rightIndex;
        }
    }
    
    /**
     * Approach 5: Brute Force
     * O(n²) time, O(1) space
     * For each bar, expand left and right to find boundaries
     */
    public int largestRectangleAreaBruteForce(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        
        for (int i = 0; i < n; i++) {
            int left = i;
            int right = i;
            
            // Expand left
            while (left >= 0 && heights[left] >= heights[i]) {
                left--;
            }
            
            // Expand right
            while (right < n && heights[right] >= heights[i]) {
                right++;
            }
            
            int width = right - left - 1;
            maxArea = Math.max(maxArea, heights[i] * width);
        }
        
        return maxArea;
    }
    
    /**
     * Approach 6: Optimized Brute Force with Precomputation
     * O(n²) time but more efficient than basic brute force
     */
    public int largestRectangleAreaOptimizedBrute(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        
        for (int i = 0; i < n; i++) {
            int minHeight = Integer.MAX_VALUE;
            for (int j = i; j < n; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                maxArea = Math.max(maxArea, minHeight * (j - i + 1));
            }
        }
        
        return maxArea;
    }
    
    /**
     * Helper method to visualize the histogram and rectangle areas
     */
    private void visualizeHistogram(int[] heights, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Heights: " + Arrays.toString(heights));
        
        int maxHeight = Arrays.stream(heights).max().getAsInt();
        
        // Print histogram
        for (int level = maxHeight; level >= 1; level--) {
            System.out.printf("%2d | ", level);
            for (int i = 0; i < heights.length; i++) {
                if (heights[i] >= level) {
                    System.out.print("██ ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        
        // Print indices
        System.out.print("   | ");
        for (int i = 0; i < heights.length; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();
        
        // Calculate and show areas for each bar using monotonic stack
        if ("Monotonic Stack".equals(approach)) {
            visualizeMonotonicStack(heights);
        }
    }
    
    /**
     * Visualize the monotonic stack process step by step
     */
    private void visualizeMonotonicStack(int[] heights) {
        System.out.println("\nMonotonic Stack Process:");
        System.out.println("Index | Height | Stack State | Action | Current Max Area");
        System.out.println("------|--------|-------------|--------|-----------------");
        
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;
        
        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];
            String action = "Push " + i;
            
            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int poppedIndex = stack.pop();
                int height = heights[poppedIndex];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                int area = height * width;
                maxArea = Math.max(maxArea, area);
                
                action = "Pop " + poppedIndex + ", Area=" + height + "*" + width + "=" + area;
                System.out.printf("%5d | %6d | %11s | %-25s | %d%n", 
                    i, currentHeight, stack.toString(), action, maxArea);
            }
            
            stack.push(i);
            if (i < n) {
                System.out.printf("%5d | %6d | %11s | %-25s | %d%n", 
                    i, currentHeight, stack.toString(), action, maxArea);
            }
        }
        
        System.out.println("Final Maximum Area: " + maxArea);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Largest Rectangle in Histogram:");
        System.out.println("======================================\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: Standard example [2,1,5,6,2,3]");
        int[] heights1 = {2,1,5,6,2,3};
        int expected1 = 10;
        testImplementation(solution, heights1, expected1, "Monotonic Stack");
        
        // Test case 2: Simple case
        System.out.println("\nTest 2: Simple case [2,4]");
        int[] heights2 = {2,4};
        int expected2 = 4;
        testImplementation(solution, heights2, expected2, "Monotonic Stack");
        
        // Test case 3: All increasing
        System.out.println("\nTest 3: All increasing [1,2,3,4,5]");
        int[] heights3 = {1,2,3,4,5};
        int expected3 = 9; // 3*3 = 9
        testImplementation(solution, heights3, expected3, "Monotonic Stack");
        
        // Test case 4: All decreasing
        System.out.println("\nTest 4: All decreasing [5,4,3,2,1]");
        int[] heights4 = {5,4,3,2,1};
        int expected4 = 9; // 3*3 = 9
        testImplementation(solution, heights4, expected4, "Monotonic Stack");
        
        // Test case 5: All same height
        System.out.println("\nTest 5: All same height [3,3,3,3]");
        int[] heights5 = {3,3,3,3};
        int expected5 = 12; // 3*4 = 12
        testImplementation(solution, heights5, expected5, "Monotonic Stack");
        
        // Test case 6: Single bar
        System.out.println("\nTest 6: Single bar [5]");
        int[] heights6 = {5};
        int expected6 = 5;
        testImplementation(solution, heights6, expected6, "Monotonic Stack");
        
        // Test case 7: Empty array
        System.out.println("\nTest 7: Empty array");
        int[] heights7 = {};
        int expected7 = 0;
        testImplementation(solution, heights7, expected7, "Monotonic Stack");
        
        // Test case 8: Large values
        System.out.println("\nTest 8: Large values [10000,10000,10000]");
        int[] heights8 = {10000,10000,10000};
        int expected8 = 30000;
        testImplementation(solution, heights8, expected8, "Monotonic Stack");
        
        // Test case 9: Complex pattern
        System.out.println("\nTest 9: Complex pattern [6,2,5,4,5,1,6]");
        int[] heights9 = {6,2,5,4,5,1,6};
        int expected9 = 12;
        testImplementation(solution, heights9, expected9, "Monotonic Stack");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: MONOTONIC STACK APPROACH");
        System.out.println("=".repeat(70));
        
        explainMonotonicStackApproach(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, int[] heights, int expected, String approach) {
        int[] heightsCopy = Arrays.copyOf(heights, heights.length);
        
        long startTime = System.nanoTime();
        int result = 0;
        switch (approach) {
            case "Monotonic Stack":
                result = solution.largestRectangleArea(heightsCopy);
                break;
            case "With Boundaries":
                result = solution.largestRectangleAreaWithBoundaries(heightsCopy);
                break;
            case "Divide and Conquer":
                result = solution.largestRectangleAreaDivideConquer(heightsCopy);
                break;
            case "Brute Force":
                result = solution.largestRectangleAreaBruteForce(heightsCopy);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        boolean passed = (result == expected);
        System.out.printf("%s: Expected=%d, Got=%d, Time=%,d ns - %s%n",
                approach, expected, result, time, (passed ? "PASSED" : "FAILED"));
        
        if (!passed) {
            System.out.println("  Input: " + Arrays.toString(heights));
        }
        
        // Visualization for interesting cases
        if (passed && heights.length <= 10) {
            solution.visualizeHistogram(heights, approach);
        }
    }
    
    private static void comparePerformance(Solution solution) {
        int size = 1000;
        int[] testArray = generateTestArray(size);
        
        System.out.println("\nPerformance test with " + size + " elements:");
        
        // Test Monotonic Stack
        int[] arr1 = Arrays.copyOf(testArray, testArray.length);
        long startTime = System.nanoTime();
        solution.largestRectangleArea(arr1);
        long time1 = System.nanoTime() - startTime;
        
        // Test With Boundaries
        int[] arr2 = Arrays.copyOf(testArray, testArray.length);
        startTime = System.nanoTime();
        solution.largestRectangleAreaWithBoundaries(arr2);
        long time2 = System.nanoTime() - startTime;
        
        // Test Divide and Conquer (skip for large arrays if too slow)
        long time3 = 0;
        if (size <= 100) {
            int[] arr3 = Arrays.copyOf(testArray, testArray.length);
            startTime = System.nanoTime();
            solution.largestRectangleAreaDivideConquer(arr3);
            time3 = System.nanoTime() - startTime;
        }
        
        // Test Brute Force (skip for large arrays)
        long time4 = 0;
        if (size <= 100) {
            int[] arr4 = Arrays.copyOf(testArray, testArray.length);
            startTime = System.nanoTime();
            solution.largestRectangleAreaBruteForce(arr4);
            time4 = System.nanoTime() - startTime;
        }
        
        System.out.printf("Monotonic Stack:      %,12d ns%n", time1);
        System.out.printf("With Boundaries:      %,12d ns%n", time2);
        if (size <= 100) {
            System.out.printf("Divide and Conquer:   %,12d ns%n", time3);
            System.out.printf("Brute Force:          %,12d ns%n", time4);
        } else {
            System.out.println("Divide and Conquer:   (skipped - too slow)");
            System.out.println("Brute Force:          (skipped - too slow)");
        }
    }
    
    private static int[] generateTestArray(int size) {
        int[] arr = new int[size];
        Random random = new Random(42);
        
        // Generate realistic histogram data with some patterns
        for (int i = 0; i < size; i++) {
            // Create some peaks and valleys
            double x = i * 2 * Math.PI / (size / 8);
            arr[i] = (int)(Math.abs(Math.sin(x) * 10) + random.nextInt(5));
        }
        
        return arr;
    }
    
    private static void explainMonotonicStackApproach(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("For each bar, the largest rectangle that includes that bar extends");
        System.out.println("until it encounters a smaller bar on left and right.");
        System.out.println("Width = right_smaller_index - left_smaller_index - 1");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize stack and maxArea = 0");
        System.out.println("2. Iterate through each bar (including sentinel at end):");
        System.out.println("   - While stack not empty and current height < stack top height:");
        System.out.println("       * Pop from stack (this bar's right boundary is current index)");
        System.out.println("       * Height = heights[popped_index]");
        System.out.println("       * Width = stack empty ? i : i - stack.peek() - 1");
        System.out.println("       * Update maxArea = max(maxArea, height * width)");
        System.out.println("   - Push current index to stack");
        System.out.println("3. Return maxArea");
        
        System.out.println("\nWhy it works:");
        System.out.println("- Stack maintains indices of bars in increasing height order");
        System.out.println("- When we pop, we've found the right boundary for that bar");
        System.out.println("- The left boundary is the next index in the stack");
        System.out.println("- Sentinel value (0) at end ensures all bars get popped and processed");
        
        System.out.println("\nExample Walkthrough: [2,1,5,6,2,3]");
        int[] example = {2,1,5,6,2,3};
        solution.visualizeHistogram(example, "Monotonic Stack");
        
        System.out.println("\nTime Complexity: O(n) - Each element pushed and popped once");
        System.out.println("Space Complexity: O(n) - Stack storage");
    }
    
    private static void checkAllImplementations(Solution solution) {
        int[][] testCases = {
            {2,1,5,6,2,3},
            {2,4},
            {1,2,3,4,5},
            {5,4,3,2,1},
            {3,3,3,3},
            {5},
            {}
        };
        
        int[] expected = {10, 4, 9, 9, 12, 5, 0};
        
        String[] methods = {
            "Monotonic Stack",
            "With Boundaries", 
            "Divide and Conquer",
            "Brute Force",
            "Optimized Brute",
            "Segment Tree"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.printf("\nTest case %d: %s (expected: %d)%n",
                    i + 1, Arrays.toString(testCases[i]), expected[i]);
            
            int[] results = new int[methods.length];
            results[0] = solution.largestRectangleArea(testCases[i].clone());
            results[1] = solution.largestRectangleAreaWithBoundaries(testCases[i].clone());
            results[2] = solution.largestRectangleAreaDivideConquer(testCases[i].clone());
            results[3] = solution.largestRectangleAreaBruteForce(testCases[i].clone());
            results[4] = solution.largestRectangleAreaOptimizedBrute(testCases[i].clone());
            results[5] = solution.largestRectangleAreaSegmentTree(testCases[i].clone());
            
            boolean caseConsistent = true;
            for (int j = 0; j < methods.length; j++) {
                boolean correct = (results[j] == expected[i]);
                System.out.printf("  %-20s: %d %s%n", methods[j], results[j],
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
        System.out.println("\n1. MONOTONIC STACK (RECOMMENDED):");
        System.out.println("   Time: O(n) - Each element processed once");
        System.out.println("   Space: O(n) - Stack storage");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient solution");
        System.out.println("     - Elegant use of stack properties");
        System.out.println("     - Handles all cases optimally");
        System.out.println("   Cons:");
        System.out.println("     - Algorithm can be tricky to understand initially");
        System.out.println("   Use when: Optimal solution needed");
        
        System.out.println("\n2. WITH BOUNDARIES (Two Pass):");
        System.out.println("   Time: O(n) - Two passes with stack");
        System.out.println("   Space: O(n) - Two additional arrays");
        System.out.println("   Pros:");
        System.out.println("     - More intuitive than single stack approach");
        System.out.println("     - Explicit left/right boundary calculation");
        System.out.println("   Cons:");
        System.out.println("     - Uses more space");
        System.out.println("     - Two passes instead of one");
        System.out.println("   Use when: Learning the concept");
        
        System.out.println("\n3. DIVIDE AND CONQUER:");
        System.out.println("   Time: O(n log n) average, O(n²) worst case");
        System.out.println("   Space: O(log n) for recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive recursive approach");
        System.out.println("     - Good for understanding the problem");
        System.out.println("   Cons:");
        System.out.println("     - Worst case O(n²) for sorted arrays");
        System.out.println("     - Not optimal for large inputs");
        System.out.println("   Use when: Teaching recursive thinking");
        
        System.out.println("\n4. BRUTE FORCE:");
        System.out.println("   Time: O(n²) - Quadratic time");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Very simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large inputs");
        System.out.println("   Use when: Understanding the problem concept");
        
        System.out.println("\n5. SEGMENT TREE (Optimized Divide and Conquer):");
        System.out.println("   Time: O(n log n) - Guaranteed");
        System.out.println("   Space: O(n) - Segment tree storage");
        System.out.println("   Pros:");
        System.out.println("     - Guaranteed O(n log n) performance");
        System.out.println("     - Good theoretical foundation");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Still slower than stack approach");
        System.out.println("   Use when: Learning segment trees");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with brute force approach to show understanding");
        System.out.println("2. Then discuss the monotonic stack intuition");
        System.out.println("3. Implement the stack solution with sentinel values");
        System.out.println("4. Walk through an example to demonstrate correctness");
        System.out.println("5. Discuss time/space complexity trade-offs");
        System.out.println("6. Mention related problems (Maximal Rectangle, etc.)");
        System.out.println("=".repeat(70));
    }
}
