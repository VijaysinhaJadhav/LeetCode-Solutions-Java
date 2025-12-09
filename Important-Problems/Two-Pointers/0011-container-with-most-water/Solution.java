/**
 * 11. Container With Most Water
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are given an integer array height of length n. There are n vertical lines drawn such that 
 * the two endpoints of the ith line are (i, 0) and (i, height[i]).
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 * Return the maximum amount of water a container can store.
 * 
 * Key Insights:
 * 1. Use two pointers starting from both ends of the array
 * 2. Area = min(height[left], height[right]) * (right - left)
 * 3. Always move the pointer with smaller height inward
 * 4. This greedy approach ensures we don't miss the maximum area
 * 
 * Approach (Two Pointers - RECOMMENDED):
 * 1. Initialize left = 0, right = n-1, maxArea = 0
 * 2. While left < right:
 *    - Calculate current area
 *    - Update maxArea if current area is larger
 *    - Move the pointer with smaller height inward
 * 3. Return maxArea
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Two Pointers, Greedy
 */

class Solution {
    /**
     * Approach 1: Two Pointers (Greedy) - RECOMMENDED
     * O(n) time, O(1) space - Most efficient and optimal
     */
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            // Calculate current area
            int currentArea = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, currentArea);
            
            // Move the pointer with smaller height
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
    
    /**
     * Approach 2: Two Pointers with Early Optimization
     * O(n) time, O(1) space - Optimized version
     */
    public int maxAreaOptimized(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            int h = Math.min(height[left], height[right]);
            int currentArea = h * (right - left);
            maxArea = Math.max(maxArea, currentArea);
            
            // Skip all smaller heights from left
            while (left < right && height[left] <= h) {
                left++;
            }
            // Skip all smaller heights from right
            while (left < right && height[right] <= h) {
                right--;
            }
        }
        
        return maxArea;
    }
    
    /**
     * Approach 3: Two Pointers with While Loop
     * O(n) time, O(1) space - Alternative implementation
     */
    public int maxAreaWhileLoop(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;
        
        while (left < right) {
            int width = right - left;
            
            if (height[left] < height[right]) {
                maxArea = Math.max(maxArea, height[left] * width);
                left++;
            } else {
                maxArea = Math.max(maxArea, height[right] * width);
                right--;
            }
        }
        
        return maxArea;
    }
    
    /**
     * Approach 4: Brute Force (For Comparison)
     * O(n²) time, O(1) space - Inefficient but complete
     */
    public int maxAreaBruteForce(int[] height) {
        int maxArea = 0;
        int n = height.length;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int currentArea = Math.min(height[i], height[j]) * (j - i);
                maxArea = Math.max(maxArea, currentArea);
            }
        }
        
        return maxArea;
    }
    
    /**
     * Approach 5: Two Pointers with Explicit Conditions
     * O(n) time, O(1) space - More explicit conditions
     */
    public int maxAreaExplicit(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;
        
        while (left < right) {
            int leftHeight = height[left];
            int rightHeight = height[right];
            int width = right - left;
            
            // Calculate area
            int area = Math.min(leftHeight, rightHeight) * width;
            maxArea = Math.max(maxArea, area);
            
            // Decide which pointer to move
            if (leftHeight < rightHeight) {
                left++;
            } else if (leftHeight > rightHeight) {
                right--;
            } else {
                // When heights are equal, move both or either
                left++;
                right--;
            }
        }
        
        return maxArea;
    }
    
    /**
     * Approach 6: Recursive Two Pointers
     * O(n) time, O(n) stack space - Elegant but uses stack space
     */
    public int maxAreaRecursive(int[] height) {
        return maxAreaHelper(height, 0, height.length - 1, 0);
    }
    
    private int maxAreaHelper(int[] height, int left, int right, int maxArea) {
        if (left >= right) {
            return maxArea;
        }
        
        int currentArea = Math.min(height[left], height[right]) * (right - left);
        maxArea = Math.max(maxArea, currentArea);
        
        if (height[left] < height[right]) {
            return maxAreaHelper(height, left + 1, right, maxArea);
        } else {
            return maxAreaHelper(height, left, right - 1, maxArea);
        }
    }
    
    /**
     * Helper method to visualize the two pointers process
     */
    private void visualizeContainer(int[] height, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Height array: " + java.util.Arrays.toString(height));
        
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Height[left] | Height[right] | Width | Area | Max Area | Action");
        System.out.println("-----|------|-------|--------------|---------------|-------|------|----------|--------");
        
        while (left < right) {
            int leftHeight = height[left];
            int rightHeight = height[right];
            int width = right - left;
            int area = Math.min(leftHeight, rightHeight) * width;
            maxArea = Math.max(maxArea, area);
            
            String action;
            if (leftHeight < rightHeight) {
                action = "Move left++ (left height smaller)";
            } else {
                action = "Move right-- (right height smaller or equal)";
            }
            
            System.out.printf("%4d | %4d | %5d | %12d | %13d | %5d | %4d | %8d | %s%n",
                            step, left, right, leftHeight, rightHeight, width, area, maxArea, action);
            
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
            step++;
        }
        
        System.out.println("\nFinal Maximum Area: " + maxArea);
        
        // Visualize the container graphically
        visualizeGraphically(height, maxArea);
    }
    
    private void visualizeGraphically(int[] height, int maxArea) {
        System.out.println("\nGraphical Representation:");
        int maxHeight = 0;
        for (int h : height) {
            maxHeight = Math.max(maxHeight, h);
        }
        
        // Find the container that gives max area
        int left = 0, right = height.length - 1;
        int bestLeft = 0, bestRight = 0;
        int currentMax = 0;
        
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            if (area > currentMax) {
                currentMax = area;
                bestLeft = left;
                bestRight = right;
            }
            
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        // Print the visualization
        for (int h = maxHeight; h >= 0; h--) {
            System.out.printf("%2d | ", h);
            for (int i = 0; i < height.length; i++) {
                if (height[i] > h) {
                    System.out.print("█ ");
                } else if (i >= bestLeft && i <= bestRight && h <= Math.min(height[bestLeft], height[bestRight])) {
                    System.out.print("~ ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        
        // Print x-axis
        System.out.print("   ");
        for (int i = 0; i < height.length; i++) {
            System.out.print("--");
        }
        System.out.println("\n   ");
        for (int i = 0; i < height.length; i++) {
            System.out.printf("%2d", i);
        }
        System.out.println();
        
        System.out.println("\nMaximum container: lines " + bestLeft + " and " + bestRight);
        System.out.println("Height: " + Math.min(height[bestLeft], height[bestRight]) + ", Width: " + (bestRight - bestLeft));
        System.out.println("Area: " + maxArea);
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(int[] height, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        long startTime = System.nanoTime();
        int result1 = maxArea(height);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result2 = maxAreaOptimized(height);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result3 = maxAreaWhileLoop(height);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result4 = maxAreaBruteForce(height);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result5 = maxAreaExplicit(height);
        long time5 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result6 = maxAreaRecursive(height);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("Two Pointers (Standard): %d ns%n", time1);
        System.out.printf("Two Pointers (Optimized): %d ns%n", time2);
        System.out.printf("Two Pointers (While Loop): %d ns%n", time3);
        System.out.printf("Brute Force: %d ns%n", time4);
        System.out.printf("Two Pointers (Explicit): %d ns%n", time5);
        System.out.printf("Recursive: %d ns%n", time6);
        
        // Verify all produce same result
        boolean allEqual = result1 == result2 && result1 == result3 && 
                          result1 == result4 && result1 == result5 && result1 == result6;
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Container With Most Water Solution:");
        System.out.println("===========================================");
        
        // Test case 1: Standard case
        System.out.println("\nTest 1: Standard case");
        int[] height1 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int expected1 = 49;
        
        int result1a = solution.maxArea(height1);
        int result1b = solution.maxAreaOptimized(height1);
        int result1c = solution.maxAreaWhileLoop(height1);
        
        System.out.println("Two Pointers: " + result1a + " - " + (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1b + " - " + (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("While Loop: " + result1c + " - " + (result1c == expected1 ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeContainer(height1, "Test 1 - Standard Case");
        
        // Test case 2: Two elements
        System.out.println("\nTest 2: Two elements");
        int[] height2 = {1, 1};
        int expected2 = 1;
        
        int result2a = solution.maxArea(height2);
        System.out.println("Two elements: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Increasing heights
        System.out.println("\nTest 3: Increasing heights");
        int[] height3 = {1, 2, 3, 4, 5};
        int expected3 = 6; // height[0]=1 and height[4]=5, area = 1*4 = 4; OR height[2]=3 and height[4]=5, area = 3*2 = 6
        int result3a = solution.maxArea(height3);
        System.out.println("Increasing: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Decreasing heights
        System.out.println("\nTest 4: Decreasing heights");
        int[] height4 = {5, 4, 3, 2, 1};
        int expected4 = 6; // height[0]=5 and height[2]=3, area = 3*2 = 6
        int result4a = solution.maxArea(height4);
        System.out.println("Decreasing: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: All same heights
        System.out.println("\nTest 5: All same heights");
        int[] height5 = {3, 3, 3, 3, 3};
        int expected5 = 12; // height[0] and height[4], area = 3*4 = 12
        int result5a = solution.maxArea(height5);
        System.out.println("All same: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Valley pattern
        System.out.println("\nTest 6: Valley pattern");
        int[] height6 = {1, 2, 1};
        int expected6 = 2; // height[0] and height[2], area = 1*2 = 2
        int result6a = solution.maxArea(height6);
        System.out.println("Valley: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large numbers
        System.out.println("\nTest 7: Large numbers");
        int[] height7 = {10, 1, 1, 1, 1, 1, 10};
        int expected7 = 60; // height[0] and height[6], area = 10*6 = 60
        int result7a = solution.maxArea(height7);
        System.out.println("Large numbers: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(height1, "Small Input (9 elements)");
        
        // Medium input performance
        int[] mediumHeight = new int[1000];
        java.util.Random random = new java.util.Random(42);
        for (int i = 0; i < mediumHeight.length; i++) {
            mediumHeight[i] = random.nextInt(100);
        }
        solution.comparePerformance(mediumHeight, "Medium Input (1000 elements)");
        
        // Large input performance
        int[] largeHeight = new int[10000];
        for (int i = 0; i < largeHeight.length; i++) {
            largeHeight[i] = random.nextInt(1000);
        }
        solution.comparePerformance(largeHeight, "Large Input (10000 elements)");
        
        // Worst-case performance for brute force
        int[] worstCaseHeight = new int[100];
        for (int i = 0; i < worstCaseHeight.length; i++) {
            worstCaseHeight[i] = i;
        }
        solution.comparePerformance(worstCaseHeight, "Worst Case for Brute Force (100 elements)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Two Pointers (Greedy) - RECOMMENDED:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Start with pointers at both ends");
        System.out.println("     - Calculate area and update maximum");
        System.out.println("     - Move pointer with smaller height inward");
        System.out.println("     - Continue until pointers meet");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Elegant greedy strategy");
        System.out.println("     - Handles large input sizes efficiently");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of greedy proof");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Two Pointers (Optimized):");
        System.out.println("   Time: O(n) - Single pass with early skipping");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Same as standard two pointers");
        System.out.println("     - Skip all smaller heights after moving pointer");
        System.out.println("     - Reduces number of iterations");
        System.out.println("   Pros:");
        System.out.println("     - Potential performance improvement");
        System.out.println("     - Fewer iterations in some cases");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("     - Marginal improvement in practice");
        System.out.println("   Best for: Performance-critical applications");
        
        System.out.println("\n3. Two Pointers (While Loop):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Alternative implementation using while loop");
        System.out.println("     - Same greedy strategy");
        System.out.println("     - Different code structure");
        System.out.println("   Pros:");
        System.out.println("     - Alternative coding style");
        System.out.println("     - Same efficiency as standard");
        System.out.println("   Cons:");
        System.out.println("     - No significant advantage");
        System.out.println("   Best for: Preference for while loops");
        
        System.out.println("\n4. Brute Force (NOT RECOMMENDED):");
        System.out.println("   Time: O(n²) - Check all possible pairs");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Check every possible pair of lines");
        System.out.println("     - Calculate area for each pair");
        System.out.println("     - Track maximum area");
        System.out.println("   Pros:");
        System.out.println("     - Simple and straightforward");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Extremely inefficient for large inputs");
        System.out.println("     - Doesn't scale beyond small arrays");
        System.out.println("   Best for: Demonstration only, very small inputs");
        
        System.out.println("\n5. Two Pointers (Explicit):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Same algorithm as standard");
        System.out.println("     - More explicit variable naming");
        System.out.println("     - Handles equal heights explicitly");
        System.out.println("   Pros:");
        System.out.println("     - Clear variable names");
        System.out.println("     - Explicit handling of edge cases");
        System.out.println("   Cons:");
        System.out.println("     - More verbose");
        System.out.println("   Best for: Learning and clarity");
        
        System.out.println("\n6. Recursive Two Pointers:");
        System.out.println("   Time: O(n) - Equivalent to iterative");
        System.out.println("   Space: O(n) - Stack space for recursion");
        System.out.println("   How it works:");
        System.out.println("     - Recursive version of two pointers");
        System.out.println("     - Base case: pointers cross");
        System.out.println("     - Recursive case: move smaller pointer");
        System.out.println("   Pros:");
        System.out.println("     - Elegant recursive formulation");
        System.out.println("     - Good for learning recursion");
        System.out.println("   Cons:");
        System.out.println("     - Stack space overhead");
        System.out.println("     - Risk of stack overflow for large inputs");
        System.out.println("   Best for: Educational purposes, small inputs");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY THE GREEDY APPROACH WORKS:");
        System.out.println("1. The area is limited by the shorter line");
        System.out.println("2. Moving the longer pointer inward cannot increase the area");
        System.out.println("3. Moving the shorter pointer might find a taller line");
        System.out.println("4. This ensures we explore all potential maximum areas");
        System.out.println("5. The algorithm is guaranteed to find the global maximum");
        System.out.println("6. Time complexity is optimal O(n)");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL FORMULATION:");
        System.out.println("1. Area between lines i and j: min(height[i], height[j]) * (j - i)");
        System.out.println("2. The two pointers approach efficiently searches this space");
        System.out.println("3. At each step, we eliminate one pointer while preserving optimality");
        System.out.println("4. The greedy choice property holds for this problem");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. Two elements: Simple calculation");
        System.out.println("2. All same heights: Maximum width gives maximum area");
        System.out.println("3. Increasing heights: Middle elements might form larger containers");
        System.out.println("4. Decreasing heights: Similar to increasing case");
        System.out.println("5. Valley pattern: Ends might form the best container");
        System.out.println("6. Large numbers: Integer arithmetic handles correctly");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Two Pointers approach (it's the expected solution)");
        System.out.println("2. Explain WHY moving the shorter pointer is optimal");
        System.out.println("3. Mention time/space complexity: O(n)/O(1)");
        System.out.println("4. Discuss the greedy proof intuition");
        System.out.println("5. Handle edge cases in reasoning");
        System.out.println("6. Write clean code with good variable names");
        System.out.println("7. Consider mentioning the brute force for comparison");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Using O(n²) brute force for large inputs");
        System.out.println("2. Moving the wrong pointer (should move shorter one)");
        System.out.println("3. Not initializing maxArea to 0");
        System.out.println("4. Incorrect area calculation");
        System.out.println("5. Not handling two-element case correctly");
        System.out.println("6. Forgetting the width is (j - i) not absolute value");
        System.out.println("=".repeat(70));
        
        // Extension to related problems
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EXTENSION: TRAPPING RAIN WATER");
        System.out.println("Similar problem but different constraints:");
        System.out.println("1. Container With Most Water: Any two lines can form container");
        System.out.println("2. Trapping Rain Water: Water trapped between multiple lines");
        System.out.println("3. Different algorithms: Two pointers vs dynamic programming");
        System.out.println("4. Both use the concept of water containment");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
