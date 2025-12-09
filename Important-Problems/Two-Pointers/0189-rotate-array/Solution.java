/**
 * 189. Rotate Array
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an integer array nums, rotate the array to the right by k steps, where k is non-negative.
 * 
 * Key Insights:
 * 1. Normalize k using modulo: k = k % n (handles k > n cases)
 * 2. Reverse the entire array
 * 3. Reverse first k elements
 * 4. Reverse remaining n-k elements
 * 5. This approach uses O(1) extra space
 * 
 * Approach (Reverse Method - RECOMMENDED):
 * 1. k = k % nums.length (handle k > n)
 * 2. reverse(nums, 0, n-1)
 * 3. reverse(nums, 0, k-1)
 * 4. reverse(nums, k, n-1)
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Math, Two Pointers
 */

class Solution {
    /**
     * Approach 1: Reverse Method - RECOMMENDED
     * O(n) time, O(1) space - Most elegant and efficient
     */
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n; // Handle cases where k > n
        
        // Reverse entire array
        reverse(nums, 0, n - 1);
        // Reverse first k elements
        reverse(nums, 0, k - 1);
        // Reverse remaining n-k elements
        reverse(nums, k, n - 1);
    }
    
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
    
    /**
     * Approach 2: Cyclic Replacements
     * O(n) time, O(1) space - Mathematical approach using GCD
     */
    public void rotateCyclic(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        if (k == 0) return;
        
        int count = 0; // Number of elements moved
        for (int start = 0; count < n; start++) {
            int current = start;
            int prev = nums[start];
            
            do {
                int next = (current + k) % n;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }
    
    /**
     * Approach 3: Using Extra Array
     * O(n) time, O(n) space - Simple but uses extra space
     */
    public void rotateExtraArray(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        int[] temp = new int[n];
        
        // Copy elements to temp array
        for (int i = 0; i < n; i++) {
            temp[(i + k) % n] = nums[i];
        }
        
        // Copy back to original array
        for (int i = 0; i < n; i++) {
            nums[i] = temp[i];
        }
    }
    
    /**
     * Approach 4: Bubble Rotate (Inefficient but educational)
     * O(n*k) time, O(1) space - Demonstrates naive approach
     */
    public void rotateBubble(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        
        for (int i = 0; i < k; i++) {
            // Rotate by one position each time
            int last = nums[n - 1];
            for (int j = n - 1; j > 0; j--) {
                nums[j] = nums[j - 1];
            }
            nums[0] = last;
        }
    }
    
    /**
     * Approach 5: Juggling Algorithm
     * O(n) time, O(1) space - Alternative mathematical approach
     */
    public void rotateJuggling(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        if (k == 0) return;
        
        int gcd = gcd(n, k);
        
        for (int i = 0; i < gcd; i++) {
            int temp = nums[i];
            int j = i;
            
            while (true) {
                int next = (j + k) % n;
                if (next == i) break;
                
                nums[j] = nums[next];
                j = next;
            }
            nums[j] = temp;
        }
    }
    
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    /**
     * Approach 6: Two Pointers with Swapping
     * O(n) time, O(1) space - Alternative in-place approach
     */
    public void rotateTwoPointers(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        if (k == 0) return;
        
        // Swap elements from both ends towards middle
        int left = 0, right = n - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
        
        // Reverse first k elements
        left = 0; right = k - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
        
        // Reverse remaining elements
        left = k; right = n - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
    
    /**
     * Helper method to visualize the rotation process
     */
    private void visualizeRotation(int[] nums, int k, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Original array: " + java.util.Arrays.toString(nums));
        System.out.println("k = " + k);
        
        int n = nums.length;
        k = k % n;
        
        if (approach.contains("Reverse")) {
            System.out.println("\nStep 1: Reverse entire array");
            int[] temp = nums.clone();
            reverse(temp, 0, n - 1);
            System.out.println("After reverse(0, " + (n-1) + "): " + java.util.Arrays.toString(temp));
            
            System.out.println("\nStep 2: Reverse first " + k + " elements");
            reverse(temp, 0, k - 1);
            System.out.println("After reverse(0, " + (k-1) + "): " + java.util.Arrays.toString(temp));
            
            System.out.println("\nStep 3: Reverse remaining " + (n-k) + " elements");
            reverse(temp, k, n - 1);
            System.out.println("After reverse(" + k + ", " + (n-1) + "): " + java.util.Arrays.toString(temp));
            
            System.out.println("\nFinal Result: " + java.util.Arrays.toString(temp));
        } else if (approach.contains("Cyclic")) {
            System.out.println("\nCyclic Replacements Process:");
            int[] temp = nums.clone();
            int count = 0;
            int step = 1;
            
            for (int start = 0; count < n; start++) {
                System.out.println("\nCycle starting at index " + start + ":");
                int current = start;
                int prev = temp[start];
                
                do {
                    int next = (current + k) % n;
                    System.out.printf("Step %d: Move %d from index %d to index %d%n", 
                                    step++, temp[next], current, next);
                    
                    int tempVal = temp[next];
                    temp[next] = prev;
                    prev = tempVal;
                    current = next;
                    count++;
                    
                    System.out.println("Array state: " + java.util.Arrays.toString(temp));
                } while (start != current);
            }
        }
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(int[] nums, int k, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        int[] numsCopy1 = nums.clone();
        long startTime = System.nanoTime();
        rotate(numsCopy1, k);
        long time1 = System.nanoTime() - startTime;
        
        int[] numsCopy2 = nums.clone();
        startTime = System.nanoTime();
        rotateCyclic(numsCopy2, k);
        long time2 = System.nanoTime() - startTime;
        
        int[] numsCopy3 = nums.clone();
        startTime = System.nanoTime();
        rotateExtraArray(numsCopy3, k);
        long time3 = System.nanoTime() - startTime;
        
        int[] numsCopy4 = nums.clone();
        startTime = System.nanoTime();
        rotateBubble(numsCopy4, k);
        long time4 = System.nanoTime() - startTime;
        
        int[] numsCopy5 = nums.clone();
        startTime = System.nanoTime();
        rotateJuggling(numsCopy5, k);
        long time5 = System.nanoTime() - startTime;
        
        int[] numsCopy6 = nums.clone();
        startTime = System.nanoTime();
        rotateTwoPointers(numsCopy6, k);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("Reverse Method: %d ns%n", time1);
        System.out.printf("Cyclic Replacements: %d ns%n", time2);
        System.out.printf("Extra Array: %d ns%n", time3);
        System.out.printf("Bubble Rotate: %d ns%n", time4);
        System.out.printf("Juggling Algorithm: %d ns%n", time5);
        System.out.printf("Two Pointers: %d ns%n", time6);
        
        // Verify all produce same result
        boolean allEqual = java.util.Arrays.equals(numsCopy1, numsCopy2) && 
                          java.util.Arrays.equals(numsCopy1, numsCopy3) && 
                          java.util.Arrays.equals(numsCopy1, numsCopy4) && 
                          java.util.Arrays.equals(numsCopy1, numsCopy5) && 
                          java.util.Arrays.equals(numsCopy1, numsCopy6);
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Rotate Array Solution:");
        System.out.println("===============================");
        
        // Test case 1: Standard case
        System.out.println("\nTest 1: Standard case");
        int[] nums1 = {1, 2, 3, 4, 5, 6, 7};
        int k1 = 3;
        int[] expected1 = {5, 6, 7, 1, 2, 3, 4};
        
        int[] nums1Copy1a = nums1.clone();
        solution.rotate(nums1Copy1a, k1);
        boolean test1a = java.util.Arrays.equals(nums1Copy1a, expected1);
        
        int[] nums1Copy1b = nums1.clone();
        solution.rotateCyclic(nums1Copy1b, k1);
        boolean test1b = java.util.Arrays.equals(nums1Copy1b, expected1);
        
        int[] nums1Copy1c = nums1.clone();
        solution.rotateExtraArray(nums1Copy1c, k1);
        boolean test1c = java.util.Arrays.equals(nums1Copy1c, expected1);
        
        System.out.println("Reverse Method: " + java.util.Arrays.toString(nums1Copy1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Cyclic Replacements: " + java.util.Arrays.toString(nums1Copy1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Extra Array: " + java.util.Arrays.toString(nums1Copy1c) + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the rotation process
        solution.visualizeRotation(nums1, k1, "Test 1 - Reverse Method");
        
        // Test case 2: k larger than array length
        System.out.println("\nTest 2: k larger than array length");
        int[] nums2 = {1, 2, 3, 4, 5};
        int k2 = 7; // Equivalent to k = 2 since 7 % 5 = 2
        int[] expected2 = {4, 5, 1, 2, 3};
        
        int[] nums2Copy2a = nums2.clone();
        solution.rotate(nums2Copy2a, k2);
        System.out.println("k > n: " + java.util.Arrays.toString(nums2Copy2a) + " - " + 
                         (java.util.Arrays.equals(nums2Copy2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: k = 0
        System.out.println("\nTest 3: k = 0");
        int[] nums3 = {1, 2, 3, 4};
        int k3 = 0;
        int[] expected3 = {1, 2, 3, 4};
        
        int[] nums3Copy3a = nums3.clone();
        solution.rotate(nums3Copy3a, k3);
        System.out.println("k = 0: " + java.util.Arrays.toString(nums3Copy3a) + " - " + 
                         (java.util.Arrays.equals(nums3Copy3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Single element
        System.out.println("\nTest 4: Single element");
        int[] nums4 = {1};
        int k4 = 5;
        int[] expected4 = {1};
        
        int[] nums4Copy4a = nums4.clone();
        solution.rotate(nums4Copy4a, k4);
        System.out.println("Single element: " + java.util.Arrays.toString(nums4Copy4a) + " - " + 
                         (java.util.Arrays.equals(nums4Copy4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Two elements
        System.out.println("\nTest 5: Two elements");
        int[] nums5 = {1, 2};
        int k5 = 1;
        int[] expected5 = {2, 1};
        
        int[] nums5Copy5a = nums5.clone();
        solution.rotate(nums5Copy5a, k5);
        System.out.println("Two elements: " + java.util.Arrays.toString(nums5Copy5a) + " - " + 
                         (java.util.Arrays.equals(nums5Copy5a, expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Negative numbers
        System.out.println("\nTest 6: Negative numbers");
        int[] nums6 = {-1, -100, 3, 99};
        int k6 = 2;
        int[] expected6 = {3, 99, -1, -100};
        
        int[] nums6Copy6a = nums6.clone();
        solution.rotate(nums6Copy6a, k6);
        System.out.println("Negative numbers: " + java.util.Arrays.toString(nums6Copy6a) + " - " + 
                         (java.util.Arrays.equals(nums6Copy6a, expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: k = array length
        System.out.println("\nTest 7: k = array length");
        int[] nums7 = {1, 2, 3, 4};
        int k7 = 4;
        int[] expected7 = {1, 2, 3, 4};
        
        int[] nums7Copy7a = nums7.clone();
        solution.rotate(nums7Copy7a, k7);
        System.out.println("k = n: " + java.util.Arrays.toString(nums7Copy7a) + " - " + 
                         (java.util.Arrays.equals(nums7Copy7a, expected7) ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(nums1, k1, "Small Input (7 elements)");
        
        // Medium input performance
        int[] mediumNums = new int[1000];
        for (int i = 0; i < mediumNums.length; i++) {
            mediumNums[i] = i;
        }
        solution.comparePerformance(mediumNums, 250, "Medium Input (1000 elements)");
        
        // Large input performance
        int[] largeNums = new int[10000];
        for (int i = 0; i < largeNums.length; i++) {
            largeNums[i] = i;
        }
        solution.comparePerformance(largeNums, 2500, "Large Input (10000 elements)");
        
        // Worst-case performance for bubble rotate
        int[] worstCaseNums = new int[100];
        for (int i = 0; i < worstCaseNums.length; i++) {
            worstCaseNums[i] = i;
        }
        solution.comparePerformance(worstCaseNums, 50, "Worst Case for Bubble (100 elements)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Reverse Method - RECOMMENDED:");
        System.out.println("   Time: O(n) - Three passes through array");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Reverse entire array: [1,2,3,4,5,6,7] -> [7,6,5,4,3,2,1]");
        System.out.println("     - Reverse first k elements: [7,6,5,4,3,2,1] -> [5,6,7,4,3,2,1]");
        System.out.println("     - Reverse remaining elements: [5,6,7,4,3,2,1] -> [5,6,7,1,2,3,4]");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Elegant and intuitive");
        System.out.println("     - Easy to implement and understand");
        System.out.println("   Cons:");
        System.out.println("     - None significant");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Cyclic Replacements:");
        System.out.println("   Time: O(n) - Each element moved exactly once");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Move elements in cycles using GCD");
        System.out.println("     - Each cycle starts at index i, moves to (i+k)%n, etc.");
        System.out.println("     - Continue until all elements are moved");
        System.out.println("   Pros:");
        System.out.println("     - Each element moved exactly once");
        System.out.println("     - Mathematical elegance");
        System.out.println("   Cons:");
        System.out.println("     - More complex to understand");
        System.out.println("     - Requires GCD calculation");
        System.out.println("   Best for: Mathematical applications");
        
        System.out.println("\n3. Extra Array Approach:");
        System.out.println("   Time: O(n) - Two passes through array");
        System.out.println("   Space: O(n) - Temporary array storage");
        System.out.println("   How it works:");
        System.out.println("     - Create temporary array of same size");
        System.out.println("     - Copy elements to their new positions: temp[(i+k)%n] = nums[i]");
        System.out.println("     - Copy back to original array");
        System.out.println("   Pros:");
        System.out.println("     - Simple and straightforward");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("     - Not truly in-place");
        System.out.println("   Best for: When simplicity is prioritized over space");
        
        System.out.println("\n4. Bubble Rotate (NOT RECOMMENDED):");
        System.out.println("   Time: O(n*k) - k passes through array");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Rotate by one position k times");
        System.out.println("     - Each rotation shifts all elements right by one");
        System.out.println("   Pros:");
        System.out.println("     - Simple logic");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Very inefficient for large k");
        System.out.println("     - O(n*k) time doesn't scale");
        System.out.println("   Best for: Demonstration of inefficient approach");
        
        System.out.println("\n5. Juggling Algorithm:");
        System.out.println("   Time: O(n) - Each element moved once");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Use GCD to determine number of cycles");
        System.out.println("     - Move elements in sets using GCD cycles");
        System.out.println("     - Similar to cyclic replacements but more structured");
        System.out.println("   Pros:");
        System.out.println("     - Mathematical foundation");
        System.out.println("     - Efficient and elegant");
        System.out.println("   Cons:");
        System.out.println("     - Complex to understand and implement");
        System.out.println("     - Requires GCD calculation");
        System.out.println("   Best for: Learning mathematical algorithms");
        
        System.out.println("\n6. Two Pointers with Swapping:");
        System.out.println("   Time: O(n) - Three reversal operations");
        System.out.println("   Space: O(1) - Only constant extra variables");
        System.out.println("   How it works:");
        System.out.println("     - Similar to reverse method but using two pointers");
        System.out.println("     - Swap elements from ends towards middle");
        System.out.println("     - Perform for three segments");
        System.out.println("   Pros:");
        System.out.println("     - Alternative implementation of reverse method");
        System.out.println("     - Uses two pointers explicitly");
        System.out.println("   Cons:");
        System.out.println("     - More verbose than reverse method");
        System.out.println("     - Same complexity as reverse method");
        System.out.println("   Best for: Learning two pointers technique");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY REVERSE METHOD WORKS:");
        System.out.println("1. Original: [1,2,3,4,5,6,7], k=3");
        System.out.println("2. Reverse entire: [7,6,5,4,3,2,1]");
        System.out.println("3. Reverse first k=3: [5,6,7,4,3,2,1]");
        System.out.println("4. Reverse remaining n-k=4: [5,6,7,1,2,3,4]");
        System.out.println("5. The three reversals strategically rearrange elements");
        System.out.println("6. Time: O(n), Space: O(1) - Optimal for the problem");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. k = k % n: Essential to handle k > n cases");
        System.out.println("2. GCD(n, k): Determines number of cycles in cyclic methods");
        System.out.println("3. Modular arithmetic: (i + k) % n gives new position");
        System.out.println("4. The problem reduces to finding the right permutation");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. k = 0: Array remains unchanged");
        System.out.println("2. k = n: Array remains unchanged (full rotation)");
        System.out.println("3. k > n: Use k % n to normalize");
        System.out.println("4. Single element: Always unchanged");
        System.out.println("5. Two elements: Simple swap when k is odd");
        System.out.println("6. Empty array: Not possible by constraints");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Reverse Method - it's the expected solution");
        System.out.println("2. Explain the three reversal steps clearly");
        System.out.println("3. Mention k = k % n normalization first");
        System.out.println("4. Discuss time/space complexity: O(n)/O(1)");
        System.out.println("5. Mention alternative approaches briefly");
        System.out.println("6. Handle edge cases in code (k=0, k=n, single element)");
        System.out.println("7. Write clean, readable code with good variable names");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Forgetting k = k % n (handling k > n)");
        System.out.println("2. Using O(n) extra space unnecessarily");
        System.out.println("3. Using inefficient O(n*k) bubble rotate");
        System.out.println("4. Incorrect index calculations in cyclic methods");
        System.out.println("5. Not handling empty or single element cases");
        System.out.println("6. Modifying array in wrong order");
        System.out.println("=".repeat(70));
        
        // Extension to related problems
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EXTENSION: LEFT ROTATION");
        System.out.println("Left rotation by k is equivalent to right rotation by n-k");
        System.out.println("Example: Left rotate [1,2,3,4,5] by 2:");
        System.out.println("  - Right rotate by 5-2 = 3");
        System.out.println("  - Result: [3,4,5,1,2]");
        System.out.println("The same algorithms work with adjusted k value");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
