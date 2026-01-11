
# Solution.java

```java
import java.util.*;

/**
 * 845. Longest Mountain in Array
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find longest contiguous subarray that forms a mountain:
 * strictly increasing to a peak, then strictly decreasing.
 * 
 * Key Insights:
 * 1. Mountain must have both increasing and decreasing parts
 * 2. Peak cannot be at boundaries
 * 3. No equal adjacent elements allowed
 * 4. Minimum length is 3
 */
class Solution {
    
    /**
     * Approach 1: Two Pass with Up/Down Counts (Recommended)
     * Time: O(n), Space: O(n)
     * Count increasing and decreasing sequences from each direction
     */
    public int longestMountain(int[] arr) {
        int n = arr.length;
        if (n < 3) return 0;
        
        int[] up = new int[n];  // Length of increasing sequence ending at i
        int[] down = new int[n]; // Length of decreasing sequence starting at i
        
        // Forward pass: count increasing sequences
        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[i - 1]) {
                up[i] = up[i - 1] + 1;
            }
        }
        
        // Backward pass: count decreasing sequences
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                down[i] = down[i + 1] + 1;
            }
        }
        
        // Find maximum mountain length
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            if (up[i] > 0 && down[i] > 0) {
                maxLen = Math.max(maxLen, up[i] + down[i] + 1);
            }
        }
        
        return maxLen;
    }
    
    /**
     * Approach 2: One Pass with State Machine
     * Time: O(n), Space: O(1)
     * Track state: increasing, decreasing, or flat
     */
    public int longestMountain2(int[] arr) {
        int n = arr.length;
        if (n < 3) return 0;
        
        int maxLen = 0;
        int up = 0;     // Length of increasing part
        int down = 0;   // Length of decreasing part
        
        for (int i = 1; i < n; i++) {
            if (down > 0 && arr[i] > arr[i - 1] || arr[i] == arr[i - 1]) {
                // Reset if:
                // 1. We were going down and now going up (new mountain)
                // 2. Equal elements (break mountain)
                up = down = 0;
            }
            
            if (arr[i] > arr[i - 1]) {
                up++;
            }
            if (arr[i] < arr[i - 1]) {
                down++;
            }
            
            // Mountain found: has both up and down parts
            if (up > 0 && down > 0) {
                maxLen = Math.max(maxLen, up + down + 1);
            }
        }
        
        return maxLen;
    }
    
    /**
     * Approach 3: Two Pointers - Expand from Each Peak
     * Time: O(n), Space: O(1)
     * For each potential peak, expand left and right
     */
    public int longestMountain3(int[] arr) {
        int n = arr.length;
        if (n < 3) return 0;
        
        int maxLen = 0;
        
        for (int i = 1; i < n - 1; i++) {
            // Check if arr[i] is a peak
            if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                // Expand left
                int left = i - 1;
                while (left > 0 && arr[left] > arr[left - 1]) {
                    left--;
                }
                
                // Expand right
                int right = i + 1;
                while (right < n - 1 && arr[right] > arr[right + 1]) {
                    right++;
                }
                
                // Calculate mountain length
                int mountainLen = right - left + 1;
                maxLen = Math.max(maxLen, mountainLen);
                
                // Skip to end of this mountain for efficiency
                i = right;
            }
        }
        
        return maxLen;
    }
    
    /**
     * Approach 4: Sliding Window
     * Time: O(n), Space: O(1)
     * Maintain a window that forms a valid mountain
     */
    public int longestMountain4(int[] arr) {
        int n = arr.length;
        if (n < 3) return 0;
        
        int maxLen = 0;
        int start = 0;
        
        while (start < n) {
            int end = start;
            
            // Find increasing sequence
            if (end + 1 < n && arr[end] < arr[end + 1]) {
                // Walk up
                while (end + 1 < n && arr[end] < arr[end + 1]) {
                    end++;
                }
                
                // Check if we have a peak
                if (end + 1 < n && arr[end] > arr[end + 1]) {
                    // Walk down
                    while (end + 1 < n && arr[end] > arr[end + 1]) {
                        end++;
                    }
                    
                    // Update max length
                    maxLen = Math.max(maxLen, end - start + 1);
                }
            }
            
            // Move start forward (skip if no progress)
            start = Math.max(end, start + 1);
        }
        
        return maxLen;
    }
    
    /**
     * Approach 5: Dynamic Programming with State Tracking
     * Time: O(n), Space: O(1)
     * Track three states: up, down, and result
     */
    public int longestMountain5(int[] arr) {
        int n = arr.length;
        if (n < 3) return 0;
        
        int up = 0, down = 0, res = 0;
        
        for (int i = 1; i < n; i++) {
            // Reset if plateau or direction change from down to up
            if (down > 0 && arr[i] > arr[i - 1] || arr[i] == arr[i - 1]) {
                up = down = 0;
            }
            
            // Count up
            if (arr[i] > arr[i - 1]) {
                up++;
            }
            
            // Count down
            if (arr[i] < arr[i - 1]) {
                down++;
            }
            
            // Update result if we have a valid mountain
            if (up > 0 && down > 0) {
                res = Math.max(res, up + down + 1);
            }
        }
        
        return res;
    }
    
    /**
     * Helper: Check if subarray is a valid mountain
     */
    private boolean isValidMountain(int[] arr, int start, int end) {
        if (end - start + 1 < 3) return false;
        
        // Find peak
        int peak = start;
        for (int i = start; i <= end; i++) {
            if (arr[i] > arr[peak]) {
                peak = i;
            }
        }
        
        // Peak cannot be at edges
        if (peak == start || peak == end) return false;
        
        // Check strictly increasing to peak
        for (int i = start; i < peak; i++) {
            if (arr[i] >= arr[i + 1]) return false;
        }
        
        // Check strictly decreasing from peak
        for (int i = peak; i < end; i++) {
            if (arr[i] <= arr[i + 1]) return false;
        }
        
        return true;
    }
    
    /**
     * Helper: Visualize mountains in array
     */
    public void visualizeMountains(int[] arr) {
        System.out.println("\nArray Visualization:");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("\nPotential Mountains:");
        
        int n = arr.length;
        List<int[]> mountains = new ArrayList<>();
        
        // Find all mountains using Approach 1
        int[] up = new int[n];
        int[] down = new int[n];
        
        // Forward pass
        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[i - 1]) {
                up[i] = up[i - 1] + 1;
            }
        }
        
        // Backward pass
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                down[i] = down[i + 1] + 1;
            }
        }
        
        // Collect mountains
        for (int i = 0; i < n; i++) {
            if (up[i] > 0 && down[i] > 0) {
                int start = i - up[i];
                int end = i + down[i];
                int length = end - start + 1;
                mountains.add(new int[]{start, end, length});
                System.out.printf("  Mountain from index %d to %d: ", start, end);
                for (int j = start; j <= end; j++) {
                    System.out.print(arr[j]);
                    if (j < end) System.out.print(", ");
                }
                System.out.printf(" (length: %d)%n", length);
            }
        }
        
        if (mountains.isEmpty()) {
            System.out.println("  No mountains found");
        } else {
            // Find longest
            int maxLen = 0;
            int[] longest = null;
            for (int[] m : mountains) {
                if (m[2] > maxLen) {
                    maxLen = m[2];
                    longest = m;
                }
            }
            
            System.out.println("\nLongest Mountain:");
            System.out.printf("  Indices: [%d, %d]%n", longest[0], longest[1]);
            System.out.printf("  Length: %d%n", longest[2]);
            System.out.print("  Values: [");
            for (int i = longest[0]; i <= longest[1]; i++) {
                System.out.print(arr[i]);
                if (i < longest[1]) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            // Test cases with expected results
            {2,1,4,7,3,2,5},     // Example 1, expected: 5
            {2,2,2},             // Example 2, expected: 0
            {0,1,2,3,4,5,4,3,2,1,0}, // Perfect mountain, expected: 11
            {0,1,0},             // Simple mountain, expected: 3
            {0,1,2,3,2,1},       // Mountain, expected: 6
            {3,2,1,0,1,2,3},     // Valley, expected: 0
            {0,0,1,0,0},         // Small mountain in middle, expected: 3
            {1,2,3,2,1,2,3,2,1}, // Multiple mountains, expected: 5
            {2,3,3,2,1},         // Plateau at peak, expected: 0
            {1,2,3,4,5},         // Only increasing, expected: 0
            {5,4,3,2,1},         // Only decreasing, expected: 0
            {1,2,1,2,1,2,1},     // Multiple peaks, expected: 3
            {0,2,0,2,0}          // Alternating, expected: 3
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        int[][] testCases = generateTestCases();
        int[] expected = {5, 0, 11, 3, 6, 0, 3, 5, 0, 0, 0, 3, 3};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.printf("\nTest %d: %s%n", 
                i + 1, Arrays.toString(testCases[i]));
            
            int result1 = longestMountain(testCases[i]);
            int result2 = longestMountain2(testCases[i]);
            int result3 = longestMountain3(testCases[i]);
            int result4 = longestMountain4(testCases[i]);
            int result5 = longestMountain5(testCases[i]);
            
            boolean allMatch = result1 == expected[i] && 
                              result2 == expected[i] &&
                              result3 == expected[i] &&
                              result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - All methods return: " + expected[i]);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize for small arrays
            if (testCases[i].length <= 15) {
                visualizeMountains(testCases[i]);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Visualize algorithm steps
     */
    public void visualizeAlgorithm(int[] arr) {
        System.out.println("\nAlgorithm Visualization:");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("\nStep-by-step (Approach 1):");
        
        int n = arr.length;
        int[] up = new int[n];
        int[] down = new int[n];
        
        System.out.println("\n1. Forward pass - count increasing sequences:");
        System.out.println("   i | arr[i] | up[i] | Condition");
        System.out.println("   ---|--------|-------|-----------");
        System.out.printf("   %d |   %d    |   %d   | Start%n", 0, arr[0], up[0]);
        
        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[i - 1]) {
                up[i] = up[i - 1] + 1;
                System.out.printf("   %d |   %d    |   %d   | arr[%d] > arr[%d] ✓%n", 
                    i, arr[i], up[i], i, i-1);
            } else {
                System.out.printf("   %d |   %d    |   %d   | arr[%d] ≤ arr[%d] ✗%n", 
                    i, arr[i], up[i], i, i-1);
            }
        }
        
        System.out.println("\n2. Backward pass - count decreasing sequences:");
        System.out.println("   i | arr[i] | down[i] | Condition");
        System.out.println("   ---|--------|---------|-----------");
        System.out.printf("   %d |   %d    |   %d     | Start%n", n-1, arr[n-1], down[n-1]);
        
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                down[i] = down[i + 1] + 1;
                System.out.printf("   %d |   %d    |   %d     | arr[%d] > arr[%d] ✓%n", 
                    i, arr[i], down[i], i, i+1);
            } else {
                System.out.printf("   %d |   %d    |   %d     | arr[%d] ≤ arr[%d] ✗%n", 
                    i, arr[i], down[i], i, i+1);
            }
        }
        
        System.out.println("\n3. Find mountains (up[i] > 0 && down[i] > 0):");
        System.out.println("   i | arr[i] | up[i] | down[i] | Mountain Length | Max");
        System.out.println("   ---|--------|-------|---------|-----------------|-----");
        
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            if (up[i] > 0 && down[i] > 0) {
                int mountainLen = up[i] + down[i] + 1;
                maxLen = Math.max(maxLen, mountainLen);
                System.out.printf("   %d |   %d    |   %d   |   %d     |      %d         | %d%n",
                    i, arr[i], up[i], down[i], mountainLen, maxLen);
            } else {
                System.out.printf("   %d |   %d    |   %d   |   %d     |      -         | %d%n",
                    i, arr[i], up[i], down[i], maxLen);
            }
        }
        
        System.out.println("\nFinal result: " + maxLen);
    }
    
    /**
     * Helper: Explain mountain conditions
     */
    public void explainMountainConditions() {
        System.out.println("\nMountain Conditions:");
        System.out.println("====================");
        
        System.out.println("\nA valid mountain array must satisfy:");
        System.out.println("1. Length ≥ 3");
        System.out.println("2. There exists a peak index i where:");
        System.out.println("   - All elements before i are strictly increasing");
        System.out.println("   - All elements after i are strictly decreasing");
        System.out.println("   - Peak cannot be at first or last position");
        
        System.out.println("\nExamples of valid mountains:");
        System.out.println("  [1, 3, 5, 4, 2]     ✓ Peak at index 2");
        System.out.println("  [0, 2, 4, 3, 1, 0]  ✓ Peak at index 2");
        System.out.println("  [1, 2, 1]           ✓ Minimal mountain");
        
        System.out.println("\nExamples of invalid mountains:");
        System.out.println("  [1, 2, 3, 4]        ✗ No decreasing part");
        System.out.println("  [4, 3, 2, 1]        ✗ No increasing part");
        System.out.println("  [1, 2, 2, 1]        ✗ Plateau at peak");
        System.out.println("  [1, 3, 2, 4, 3]     ✗ Multiple peaks");
        System.out.println("  [1, 2]              ✗ Too short");
        
        System.out.println("\nEdge cases:");
        System.out.println("  [2, 2, 2]           ✗ All equal - no mountain");
        System.out.println("  [1, 2, 3, 2, 3, 2, 1] ✗ Multiple mountains");
        System.out.println("  [0, 1, 2, 1, 2, 1, 0] ✗ W-shaped");
        
        System.out.println("\nKey Observations:");
        System.out.println("1. Strictly increasing/decreasing means no equal adjacent elements");
        System.out.println("2. Mountain must have both up and down slopes");
        System.out.println("3. Peak is the maximum element in the mountain");
        System.out.println("4. Subarray must be contiguous");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=======================");
        
        // Generate large test case
        Random rand = new Random(42);
        int n = 10000;
        int[] arr = new int[n];
        
        // Create array with multiple mountains
        int val = 0;
        for (int i = 0; i < n; i++) {
            if (rand.nextDouble() < 0.3 && i > 0 && i < n - 1) {
                // Create a peak
                arr[i] = val + rand.nextInt(10) + 5;
                val = arr[i];
            } else if (rand.nextDouble() < 0.5) {
                // Increasing
                val += rand.nextInt(3) + 1;
                arr[i] = val;
            } else {
                // Decreasing
                val = Math.max(0, val - (rand.nextInt(3) + 1));
                arr[i] = val;
            }
        }
        
        System.out.println("Testing with " + n + " elements");
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: Two Pass with arrays
        long start = System.currentTimeMillis();
        results[0] = longestMountain(arr);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: One Pass State Machine
        start = System.currentTimeMillis();
        results[1] = longestMountain2(arr);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Two Pointers
        start = System.currentTimeMillis();
        results[2] = longestMountain3(arr);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Sliding Window
        start = System.currentTimeMillis();
        results[3] = longestMountain4(arr);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: DP with State Tracking
        start = System.currentTimeMillis();
        results[4] = longestMountain5(arr);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                     | Time (ms) | Result | Correct?");
        System.out.println("---------------------------|-----------|--------|---------");
        System.out.printf("1. Two Pass with arrays    | %9d | %6d | %s%n",
            times[0], results[0], "✓ (baseline)");
        System.out.printf("2. One Pass State Machine  | %9d | %6d | %s%n",
            times[1], results[1], results[1] == results[0] ? "✓" : "✗");
        System.out.printf("3. Two Pointers           | %9d | %6d | %s%n",
            times[2], results[2], results[2] == results[0] ? "✓" : "✗");
        System.out.printf("4. Sliding Window         | %9d | %6d | %s%n",
            times[3], results[3], results[3] == results[0] ? "✓" : "✗");
        System.out.printf("5. DP State Tracking      | %9d | %6d | %s%n",
            times[4], results[4], results[4] == results[0] ? "✓" : "✗");
        
        System.out.println("\nObservations:");
        System.out.println("1. All methods are O(n) time complexity");
        System.out.println("2. Method 2 (One Pass) is most space efficient (O(1))");
        System.out.println("3. Method 1 uses O(n) space but is easiest to understand");
        System.out.println("4. All methods produce same correct result");
    }
    
    /**
     * Helper: Mountain array variations
     */
    public void mountainVariations() {
        System.out.println("\nMountain Array Variations:");
        System.out.println("==========================");
        
        System.out.println("\n1. Valid Mountain Array (LeetCode 941):");
        System.out.println("   Check if entire array is a mountain");
        System.out.println("   Simpler: just check conditions");
        
        System.out.println("\n2. Peak Index in Mountain Array (LeetCode 852):");
        System.out.println("   Find the peak index in a mountain array");
        System.out.println("   Binary search works since array is mountain");
        
        System.out.println("\n3. Find in Mountain Array (LeetCode 1095):");
        System.out.println("   Search for target in mountain array");
        System.out.println("   Binary search on both sides of peak");
        
        System.out.println("\n4. Minimum Removals to Make Mountain (LeetCode 1671):");
        System.out.println("   Remove minimum elements to make mountain");
        System.out.println("   DP with LIS from both ends");
        
        System.out.println("\n5. Longest Mountain in 2D Array:");
        System.out.println("   2D version - find longest mountain path");
        System.out.println("   Graph traversal with height constraints");
        
        System.out.println("\nCommon Techniques:");
        System.out.println("- Two passes from left and right");
        System.out.println("- State machines for one-pass solutions");
        System.out.println("- Binary search for peak finding");
        System.out.println("- Dynamic programming for optimization");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify mountain definition:");
        System.out.println("   - Strictly increasing then strictly decreasing");
        System.out.println("   - No equal adjacent elements");
        System.out.println("   - Minimum length 3");
        System.out.println("   - Peak cannot be at boundaries");
        
        System.out.println("\n2. Start with examples:");
        System.out.println("   - Provided examples");
        System.out.println("   - Simple cases: [1,2,1], [1,2,3,2,1]");
        System.out.println("   - Edge cases: all equal, only increasing");
        
        System.out.println("\n3. Propose brute force:");
        System.out.println("   - Check all subarrays O(n³)");
        System.out.println("   - For each subarray, verify mountain conditions");
        System.out.println("   - Mention it's too slow for constraints");
        
        System.out.println("\n4. Optimize with DP:");
        System.out.println("   - Count increasing sequences from left");
        System.out.println("   - Count decreasing sequences from right");
        System.out.println("   - Combine at each position");
        
        System.out.println("\n5. One-pass optimization:");
        System.out.println("   - State machine: track up/down counts");
        System.out.println("   - Reset on plateau or direction change");
        System.out.println("   - Update max when both up and down > 0");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - Array length < 3");
        System.out.println("   - All equal elements");
        System.out.println("   - Only increasing/decreasing");
        System.out.println("   - Multiple mountains");
        
        System.out.println("\n7. Discuss complexity:");
        System.out.println("   - Time: O(n) for all optimal solutions");
        System.out.println("   - Space: O(1) for one-pass, O(n) for DP");
        
        System.out.println("\n8. Test thoroughly:");
        System.out.println("   - Provided examples");
        System.out.println("   - Edge cases");
        System.out.println("   - Random large cases");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void realWorldApplications() {
        System.out.println("\nReal-world Applications:");
        System.out.println("========================");
        
        System.out.println("\n1. Stock Price Analysis:");
        System.out.println("   - Find periods of growth then decline");
        System.out.println("   - Identify peak performance periods");
        
        System.out.println("\n2. Signal Processing:");
        System.out.println("   - Detect pulse shapes in signals");
        System.out.println("   - Find peaks in waveform data");
        
        System.out.println("\n3. Terrain Analysis:");
        System.out.println("   - Identify mountain ranges in elevation data");
        System.out.println("   - Find longest continuous ascent/descent");
        
        System.out.println("\n4. Economic Indicators:");
        System.out.println("   - Detect boom-bust cycles");
        System.out.println("   - Find growth peaks in time series");
        
        System.out.println("\n5. Sports Analytics:");
        System.out.println("   - Identify performance peaks");
        System.out.println("   - Find improvement then decline patterns");
        
        System.out.println("\n6. Medical Data:");
        System.out.println("   - Detect peaks in vital signs");
        System.out.println("   - Identify response patterns to stimuli");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("845. Longest Mountain in Array");
        System.out.println("==============================");
        
        // Explain mountain conditions
        solution.explainMountainConditions();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Visualize algorithm
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Algorithm Visualization:");
        System.out.println("=".repeat(80));
        
        // Example 1
        System.out.println("\nExample 1: [2,1,4,7,3,2,5]");
        solution.visualizeAlgorithm(new int[]{2,1,4,7,3,2,5});
        
        // Example with multiple mountains
        System.out.println("\n" + "-".repeat(80));
        System.out.println("\nExample: [1,2,1,2,3,2,1,2,1]");
        solution.visualizeAlgorithm(new int[]{1,2,1,2,3,2,1,2,1});
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Mountain variations
        System.out.println("\n" + "=".repeat(80));
        solution.mountainVariations();
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(80));
        solution.realWorldApplications();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation (One Pass - O(1) Space):");
        System.out.println("""
class Solution {
    public int longestMountain(int[] arr) {
        int n = arr.length;
        if (n < 3) return 0;
        
        int maxLen = 0;
        int up = 0, down = 0;
        
        for (int i = 1; i < n; i++) {
            // Reset if plateau or direction change from down to up
            if (down > 0 && arr[i] > arr[i - 1] || arr[i] == arr[i - 1]) {
                up = down = 0;
            }
            
            // Count increasing sequence
            if (arr[i] > arr[i - 1]) {
                up++;
            }
            
            // Count decreasing sequence
            if (arr[i] < arr[i - 1]) {
                down++;
            }
            
            // Update max if we have a valid mountain
            if (up > 0 && down > 0) {
                maxLen = Math.max(maxLen, up + down + 1);
            }
        }
        
        return maxLen;
    }
}
            """);
        
        System.out.println("\nAlternative (Two Pass - Easy to Understand):");
        System.out.println("""
class Solution {
    public int longestMountain(int[] arr) {
        int n = arr.length;
        if (n < 3) return 0;
        
        int[] up = new int[n];
        int[] down = new int[n];
        
        // Count increasing sequences
        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[i - 1]) {
                up[i] = up[i - 1] + 1;
            }
        }
        
        // Count decreasing sequences
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                down[i] = down[i + 1] + 1;
            }
        }
        
        // Find maximum mountain
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            if (up[i] > 0 && down[i] > 0) {
                maxLen = Math.max(maxLen, up[i] + down[i] + 1);
            }
        }
        
        return maxLen;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Mountain must have both increasing and decreasing parts");
        System.out.println("2. Strictly monotonic: no equal adjacent elements");
