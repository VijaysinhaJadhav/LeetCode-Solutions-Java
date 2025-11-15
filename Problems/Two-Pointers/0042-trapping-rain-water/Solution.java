
## Solution.java

```java
/**
 * 42. Trapping Rain Water
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given n non-negative integers representing an elevation map, compute how much water it can trap after raining.
 * 
 * Key Insights:
 * 1. Water trapped at position i depends on the maximum heights to its left and right
 * 2. Water at i = min(leftMax, rightMax) - height[i] (if positive)
 * 3. Two pointer approach efficiently tracks left and right max while traversing
 * 4. Always process the side with smaller current max height
 * 
 * Approach (Two Pointers):
 * 1. Initialize left and right pointers at both ends
 * 2. Track leftMax and rightMax as we move pointers
 * 3. For the smaller max side, calculate trapped water and move pointer
 * 4. Continue until pointers meet
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Two Pointers, Dynamic Programming, Stack
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Two Pointers (Optimal) - RECOMMENDED
     * O(n) time, O(1) space
     */
    public int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int water = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                // Process left side
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;
            } else {
                // Process right side
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }
        
        return water;
    }
    
    /**
     * Approach 2: Dynamic Programming (Precomputation)
     * O(n) time, O(n) space
     * Precompute leftMax and rightMax arrays
     */
    public int trapDP(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        
        // Compute left maximums
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        
        // Compute right maximums
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        
        // Calculate trapped water
        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        
        return water;
    }
    
    /**
     * Approach 3: Stack (Monotonic Decreasing)
     * O(n) time, O(n) space
     * Uses stack to track bars in decreasing order
     */
    public int trapStack(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        Stack<Integer> stack = new Stack<>();
        int water = 0;
        
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int bottom = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                int distance = i - left - 1;
                int boundedHeight = Math.min(height[i], height[left]) - height[bottom];
                water += distance * boundedHeight;
            }
            stack.push(i);
        }
        
        return water;
    }
    
    /**
     * Approach 4: Brute Force
     * O(n²) time, O(1) space
     * For each position, find left and right max
     */
    public int trapBruteForce(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int water = 0;
        int n = height.length;
        
        for (int i = 0; i < n; i++) {
            int leftMax = 0;
            int rightMax = 0;
            
            // Find left max
            for (int j = 0; j <= i; j++) {
                leftMax = Math.max(leftMax, height[j]);
            }
            
            // Find right max
            for (int j = i; j < n; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }
            
            water += Math.min(leftMax, rightMax) - height[i];
        }
        
        return water;
    }
    
    /**
     * Approach 5: Optimized Brute Force
     * O(n²) time, O(1) space but more efficient than basic brute force
     */
    public int trapOptimizedBruteForce(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int water = 0;
        int n = height.length;
        
        for (int i = 1; i < n - 1; i++) {
            int leftMax = 0;
            int rightMax = 0;
            
            // Find left max (from 0 to i)
            for (int j = 0; j <= i; j++) {
                leftMax = Math.max(leftMax, height[j]);
            }
            
            // Find right max (from i to n-1)
            for (int j = i; j < n; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }
            
            water += Math.min(leftMax, rightMax) - height[i];
        }
        
        return water;
    }
    
    /**
     * Approach 6: Two Pass with Constant Space (Variant of DP)
     * O(n) time, O(1) space
     * Finds the maximum height and processes left and right parts
     */
    public int trapTwoPass(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int n = height.length;
        
        // Find index of maximum height
        int maxIndex = 0;
        for (int i = 1; i < n; i++) {
            if (height[i] > height[maxIndex]) {
                maxIndex = i;
            }
        }
        
        int water = 0;
        
        // Process left part (from left to maxIndex)
        int leftMax = 0;
        for (int i = 0; i < maxIndex; i++) {
            if (height[i] > leftMax) {
                leftMax = height[i];
            } else {
                water += leftMax - height[i];
            }
        }
        
        // Process right part (from right to maxIndex)
        int rightMax = 0;
        for (int i = n - 1; i > maxIndex; i--) {
            if (height[i] > rightMax) {
                rightMax = height[i];
            } else {
                water += rightMax - height[i];
            }
        }
        
        return water;
    }
    
    /**
     * Helper method to visualize the trapping process
     */
    private void visualizeTrapping(int[] height, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Elevation: " + Arrays.toString(height));
        
        int n = height.length;
        int[] waterLevel = new int[n];
        int totalWater = 0;
        
        if ("Two Pointers".equals(approach)) {
            int left = 0, right = n - 1;
            int leftMax = 0, rightMax = 0;
            
            System.out.println("Step | Left | Right | LeftMax | RightMax | Water | Action");
            System.out.println("-----|------|-------|---------|----------|-------|--------");
            
            int step = 1;
            while (left <= right) {
                String action;
                if (height[left] < height[right]) {
                    if (height[left] >= leftMax) {
                        leftMax = height[left];
                        action = "Update leftMax";
                    } else {
                        int water = leftMax - height[left];
                        totalWater += water;
                        waterLevel[left] = water;
                        action = "Add water: " + water;
                    }
                    System.out.printf("%4d | %4d | %5d | %7d | %8d | %5d | %s%n",
                            step++, left, right, leftMax, rightMax, totalWater, action);
                    left++;
                } else {
                    if (height[right] >= rightMax) {
                        rightMax = height[right];
                        action = "Update rightMax";
                    } else {
                        int water = rightMax - height[right];
                        totalWater += water;
                        waterLevel[right] = water;
                        action = "Add water: " + water;
                    }
                    System.out.printf("%4d | %4d | %5d | %7d | %8d | %5d | %s%n",
                            step++, left, right, leftMax, rightMax, totalWater, action);
                    right--;
                }
            }
        } else if ("Dynamic Programming".equals(approach)) {
            int[] leftMax = new int[n];
            int[] rightMax = new int[n];
            
            leftMax[0] = height[0];
            for (int i = 1; i < n; i++) {
                leftMax[i] = Math.max(leftMax[i - 1], height[i]);
            }
            
            rightMax[n - 1] = height[n - 1];
            for (int i = n - 2; i >= 0; i--) {
                rightMax[i] = Math.max(rightMax[i + 1], height[i]);
            }
            
            System.out.println("Index | Height | LeftMax | RightMax | Min(L,R) | Water");
            System.out.println("------|--------|---------|----------|----------|------");
            
            for (int i = 0; i < n; i++) {
                int minMax = Math.min(leftMax[i], rightMax[i]);
                int water = minMax - height[i];
                waterLevel[i] = water;
                totalWater += water;
                System.out.printf("%5d | %6d | %7d | %8d | %8d | %5d%n",
                        i, height[i], leftMax[i], rightMax[i], minMax, water);
            }
        }
        
        // Visual representation
        System.out.println("\nVisual Representation:");
        int maxHeight = Arrays.stream(height).max().getAsInt();
        
        for (int level = maxHeight; level >= 0; level--) {
            System.out.printf("%2d | ", level);
            for (int i = 0; i < n; i++) {
                if (height[i] >= level) {
                    System.out.print("██ ");
                } else if (height[i] + waterLevel[i] >= level) {
                    System.out.print("~~ "); // Water
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        
        System.out.print("   | ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println("\nTotal Water Trapped: " + totalWater);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Trapping Rain Water Solution:");
        System.out.println("=====================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example [0,1,0,2,1,0,1,3,2,1,2,1]");
        int[] height1 = {0,1,0,2,1,0,1,3,2,1,2,1};
        int expected1 = 6;
        testImplementation(solution, height1, expected1, "Two Pointers");
        
        // Test case 2: Another example
        System.out.println("\nTest 2: [4,2,0,3,2,5]");
        int[] height2 = {4,2,0,3,2,5};
        int expected2 = 9;
        testImplementation(solution, height2, expected2, "Two Pointers");
        
        // Test case 3: No trapping possible
        System.out.println("\nTest 3: No trapping [1,2,3,4,5]");
        int[] height3 = {1,2,3,4,5};
        int expected3 = 0;
        testImplementation(solution, height3, expected3, "Two Pointers");
        
        // Test case 4: All same height
        System.out.println("\nTest 4: All same [3,3,3,3]");
        int[] height4 = {3,3,3,3};
        int expected4 = 0;
        testImplementation(solution, height4, expected4, "Two Pointers");
        
        // Test case 5: Single peak
        System.out.println("\nTest 5: Single peak [0,0,5,0,0]");
        int[] height5 = {0,0,5,0,0};
        int expected5 = 0;
        testImplementation(solution, height5, expected5, "Two Pointers");
        
        // Test case 6: Valley
        System.out.println("\nTest 6: Valley [5,0,5]");
        int[] height6 = {5,0,5};
        int expected6 = 5;
        testImplementation(solution, height6, expected6, "Two Pointers");
        
        // Test case 7: Small array
        System.out.println("\nTest 7: Small array [1,0,1]");
        int[] height7 = {1,0,1};
        int expected7 = 1;
        testImplementation(solution, height7, expected7, "Two Pointers");
        
        // Test case 8: Empty array
        System.out.println("\nTest 8: Empty array");
        int[] height8 = {};
        int expected8 = 0;
        testImplementation(solution, height8, expected8, "Two Pointers");
        
        // Test case 9: Large array
        System.out.println("\nTest 9: Large array performance");
        int[] height9 = generateLargeTestArray(1000);
        long startTime = System.nanoTime();
        int result9 = solution.trap(height9);
        long time9 = System.nanoTime() - startTime;
        System.out.println("Large array (1000 elements): " + result9 + " units, Time: " + time9 + " ns");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation with visualization
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: TWO POINTERS APPROACH");
        System.out.println("=".repeat(70));
        
        explainTwoPointersApproach(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, int[] height, int expected, String approach) {
        int[] heightCopy = Arrays.copyOf(height, height.length);
        
        long startTime = System.nanoTime();
        int result = 0;
        switch (approach) {
            case "Two Pointers":
                result = solution.trap(heightCopy);
                break;
            case "Dynamic Programming":
                result = solution.trapDP(heightCopy);
                break;
            case "Stack":
                result = solution.trapStack(heightCopy);
                break;
            case "Brute Force":
                result = solution.trapBruteForce(heightCopy);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        boolean passed = (result == expected);
        System.out.printf("%s: Expected=%d, Got=%d, Time=%,d ns - %s%n",
                approach, expected, result, time, (passed ? "PASSED" : "FAILED"));
        
        if (!passed) {
            System.out.println("  Input: " + Arrays.toString(height));
        }
        
        // Visualization for interesting cases
        if (passed && height.length <= 12) {
            solution.visualizeTrapping(height, approach);
        }
    }
    
    private static void comparePerformance(Solution solution) {
        int size = 10000;
        int[] testArray = generateLargeTestArray(size);
        
        System.out.println("\nPerformance test with " + size + " elements:");
        
        // Test Two Pointers
        int[] arr1 = Arrays.copyOf(testArray, testArray.length);
        long startTime = System.nanoTime();
        solution.trap(arr1);
        long time1 = System.nanoTime() - startTime;
        
        // Test Dynamic Programming
        int[] arr2 = Arrays.copyOf(testArray, testArray.length);
        startTime = System.nanoTime();
        solution.trapDP(arr2);
        long time2 = System.nanoTime() - startTime;
        
        // Test Stack
        int[] arr3 = Arrays.copyOf(testArray, testArray.length);
        startTime = System.nanoTime();
        solution.trapStack(arr3);
        long time3 = System.nanoTime() - startTime;
        
        // Test Brute Force (skip for large arrays as it's too slow)
        long time4 = 0;
        if (size <= 1000) {
            int[] arr4 = Arrays.copyOf(testArray, testArray.length);
            startTime = System.nanoTime();
            solution.trapBruteForce(arr4);
            time4 = System.nanoTime() - startTime;
        }
        
        System.out.printf("Two Pointers:        %,12d ns%n", time1);
        System.out.printf("Dynamic Programming: %,12d ns%n", time2);
        System.out.printf("Stack:               %,12d ns%n", time3);
        if (size <= 1000) {
            System.out.printf("Brute Force:         %,12d ns%n", time4);
        } else {
            System.out.println("Brute Force:         (skipped - too slow)");
        }
    }
    
    private static int[] generateLargeTestArray(int size) {
        int[] arr = new int[size];
        Random random = new Random(42);
        
        // Generate realistic elevation data
        for (int i = 0; i < size; i++) {
            // Create some peaks and valleys
            double x = i * 2 * Math.PI / (size / 4);
            arr[i] = (int)(Math.abs(Math.sin(x) * 10) + random.nextInt(5));
        }
        
        return arr;
    }
    
    private static void explainTwoPointersApproach(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("Water trapped at position i = min(max_left, max_right) - height[i]");
        System.out.println("The two pointer approach efficiently tracks leftMax and rightMax while traversing.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize left=0, right=n-1, leftMax=0, rightMax=0, water=0");
        System.out.println("2. While left < right:");
        System.out.println("   - If height[left] < height[right]:");
        System.out.println("       * If height[left] >= leftMax: update leftMax");
        System.out.println("       * Else: add (leftMax - height[left]) to water");
        System.out.println("       * Move left pointer right");
        System.out.println("   - Else:");
        System.out.println("       * If height[right] >= rightMax: update rightMax");
        System.out.println("       * Else: add (rightMax - height[right]) to water");
        System.out.println("       * Move right pointer left");
        
        System.out.println("\nWhy it works:");
        System.out.println("- We always process the side with smaller current height");
        System.out.println("- This ensures that for the current position, the other side has at least the current max height");
        System.out.println("- The trapped water is determined by the smaller of the two max heights");
        System.out.println("- By moving the pointer with smaller height, we guarantee we won't miss any water");
        
        System.out.println("\nExample Walkthrough: [0,1,0,2,1,0,1,3,2,1,2,1]");
        int[] example = {0,1,0,2,1,0,1,3,2,1,2,1};
        solution.visualizeTrapping(example, "Two Pointers");
        
        System.out.println("\nTime Complexity: O(n) - Single pass through array");
        System.out.println("Space Complexity: O(1) - Only constant extra space");
    }
    
    private static void checkAllImplementations(Solution solution) {
        int[][] testCases = {
            {0,1,0,2,1,0,1,3,2,1,2,1},
            {4,2,0,3,2,5},
            {1,2,3,4,5},
            {3,3,3,3},
            {5,0,5},
            {1,0,1},
            {}
        };
        
        int[] expected = {6, 9, 0, 0, 5, 1, 0};
        
        String[] methods = {
            "Two Pointers",
            "Dynamic Programming", 
            "Stack",
            "Brute Force",
            "Optimized Brute Force",
            "Two Pass"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.printf("\nTest case %d: %s (expected: %d)%n",
                    i + 1, Arrays.toString(testCases[i]), expected[i]);
            
            int[] results = new int[methods.length];
            results[0] = solution.trap(testCases[i].clone());
            results[1] = solution.trapDP(testCases[i].clone());
            results[2] = solution.trapStack(testCases[i].clone());
            results[3] = solution.trapBruteForce(testCases[i].clone());
            results[4] = solution.trapOptimizedBruteForce(testCases[i].clone());
            results[5] = solution.trapTwoPass(testCases[i].clone());
            
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
        System.out.println("\n1. TWO POINTERS (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient in time and space");
        System.out.println("     - Elegant and intuitive once understood");
        System.out.println("     - Best for interviews");
        System.out.println("   Cons:");
        System.out.println("     - Logic can be tricky to understand initially");
        System.out.println("   Use when: Optimal solution needed");
        
        System.out.println("\n2. DYNAMIC PROGRAMMING:");
        System.out.println("   Time: O(n) - Three passes");
        System.out.println("   Space: O(n) - Two additional arrays");
        System.out.println("   Pros:");
        System.out.println("     - Very intuitive and easy to understand");
        System.out.println("     - Clear separation of concerns");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("   Use when: Clarity is more important than space");
        
        System.out.println("\n3. STACK APPROACH:");
        System.out.println("   Time: O(n) - Single pass with stack operations");
        System.out.println("   Space: O(n) - Stack storage");
        System.out.println("   Pros:");
        System.out.println("     - Good for understanding monotonic stacks");
        System.out.println("     - Can be extended to 2D cases");
        System.out.println("   Cons:");
        System.out.println("     - Less intuitive than DP");
        System.out.println("     - Uses O(n) space");
        System.out.println("   Use when: Learning about monotonic stacks");
        
        System.out.println("\n4. BRUTE FORCE:");
        System.out.println("   Time: O(n²) - Quadratic time");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Very simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large inputs");
        System.out.println("   Use when: Understanding the problem concept");
        
        System.out.println("\n5. TWO PASS:");
        System.out.println("   Time: O(n) - Two passes");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Good balance of efficiency and simplicity");
        System.out.println("     - No extra arrays needed");
        System.out.println("   Cons:");
        System.out.println("     - Requires finding max element first");
        System.out.println("   Use when: Want simplicity with good performance");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with explaining the water trapping formula");
        System.out.println("2. Mention brute force approach first (shows understanding)");
        System.out.println("3. Then optimize to DP approach (shows problem-solving)");
        System.out.println("4. Finally present two pointers (shows mastery)");
        System.out.println("5. Discuss time/space trade-offs for each approach");
        System.out.println("=".repeat(70));
    }
}
