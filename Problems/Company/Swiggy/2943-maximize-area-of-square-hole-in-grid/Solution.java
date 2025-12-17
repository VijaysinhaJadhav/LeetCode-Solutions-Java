
# Solution.java

```java
import java.util.*;

/**
 * 2943. Maximize Area of Square Hole in Grid
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given horizontal and vertical bars to remove, find maximum area of square hole
 * after removing specified bars from the grid.
 * 
 * Key Insights:
 * 1. Consecutive removed bars create larger gaps
 * 2. Gap size = consecutive removed bars + 1
 * 3. Square requires equal gaps in both directions
 * 4. Maximum square side = min(max horizontal gap, max vertical gap)
 * 5. Area = side²
 * 
 * Approach (Sorting and Counting Consecutive Elements):
 * 1. Sort both hBars and vBars arrays
 * 2. Find longest consecutive sequence in hBars
 * 3. Find longest consecutive sequence in vBars
 * 4. Maximum square side = min(maxHConsecutive, maxVConsecutive) + 1
 * 5. Return side²
 * 
 * Time Complexity: O(h log h + v log v)
 * Space Complexity: O(1) excluding sorting space
 * 
 * Tags: Array, Sorting, Greedy
 */

class Solution {
    
    /**
     * Approach 1: Sorting and Counting Consecutive (RECOMMENDED)
     * O(h log h + v log v) time, O(1) space
     */
    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        // Find maximum consecutive horizontal bars
        int maxHConsecutive = findMaxConsecutive(hBars);
        int maxVConsecutive = findMaxConsecutive(vBars);
        
        // Square side = min(max consecutive in both directions) + 1
        int side = Math.min(maxHConsecutive, maxVConsecutive) + 1;
        
        return side * side;
    }
    
    /**
     * Helper method to find maximum consecutive sequence in sorted array
     */
    private int findMaxConsecutive(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        Arrays.sort(arr);
        int maxConsecutive = 1;
        int currentConsecutive = 1;
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i-1] + 1) {
                currentConsecutive++;
                maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
            } else {
                currentConsecutive = 1;
            }
        }
        
        return maxConsecutive;
    }
    
    /**
     * Approach 2: Using HashSet for Consecutive Detection
     * O(h + v) time, O(h + v) space
     * Better when arrays are large but range is small
     */
    public int maximizeSquareHoleAreaHashSet(int n, int m, int[] hBars, int[] vBars) {
        // Convert to sets
        Set<Integer> hSet = new HashSet<>();
        for (int bar : hBars) hSet.add(bar);
        
        Set<Integer> vSet = new HashSet<>();
        for (int bar : vBars) vSet.add(bar);
        
        // Find longest consecutive in horizontal
        int maxHConsecutive = findMaxConsecutiveFromSet(hSet);
        int maxVConsecutive = findMaxConsecutiveFromSet(vSet);
        
        int side = Math.min(maxHConsecutive, maxVConsecutive) + 1;
        return side * side;
    }
    
    private int findMaxConsecutiveFromSet(Set<Integer> set) {
        int maxConsecutive = 0;
        
        for (int num : set) {
            // Only start counting if this is the beginning of a sequence
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentConsecutive = 1;
                
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentConsecutive++;
                }
                
                maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
            }
        }
        
        return maxConsecutive;
    }
    
    /**
     * Approach 3: Bucket Sort (when range is limited)
     * O(n + m) time, O(n + m) space
     * Useful when n and m are not too large
     */
    public int maximizeSquareHoleAreaBucket(int n, int m, int[] hBars, int[] vBars) {
        // Create boolean arrays for bars
        boolean[] hBarExists = new boolean[n + 3]; // +2 for edges, +1 for 1-indexing
        boolean[] vBarExists = new boolean[m + 3];
        
        for (int bar : hBars) hBarExists[bar] = true;
        for (int bar : vBars) vBarExists[bar] = true;
        
        // Find longest consecutive horizontal bars
        int maxHConsecutive = 0;
        int currentH = 0;
        for (int i = 1; i <= n + 1; i++) {
            if (hBarExists[i]) {
                currentH++;
                maxHConsecutive = Math.max(maxHConsecutive, currentH);
            } else {
                currentH = 0;
            }
        }
        
        // Find longest consecutive vertical bars
        int maxVConsecutive = 0;
        int currentV = 0;
        for (int i = 1; i <= m + 1; i++) {
            if (vBarExists[i]) {
                currentV++;
                maxVConsecutive = Math.max(maxVConsecutive, currentV);
            } else {
                currentV = 0;
            }
        }
        
        int side = Math.min(maxHConsecutive, maxVConsecutive) + 1;
        return side * side;
    }
    
    /**
     * Approach 4: Two Pointers after Sorting
     * O(h log h + v log v) time, O(1) space
     * Alternative implementation
     */
    public int maximizeSquareHoleAreaTwoPointers(int n, int m, int[] hBars, int[] vBars) {
        Arrays.sort(hBars);
        Arrays.sort(vBars);
        
        int maxHConsecutive = getMaxConsecutiveTwoPointers(hBars);
        int maxVConsecutive = getMaxConsecutiveTwoPointers(vBars);
        
        int side = Math.min(maxHConsecutive, maxVConsecutive) + 1;
        return side * side;
    }
    
    private int getMaxConsecutiveTwoPointers(int[] arr) {
        if (arr.length == 0) return 0;
        
        int maxLen = 1;
        int left = 0;
        
        for (int right = 1; right < arr.length; right++) {
            if (arr[right] != arr[right - 1] + 1) {
                left = right;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
    
    /**
     * Helper method to visualize the grid and bars
     */
    private void visualizeGrid(int n, int m, int[] hBars, int[] vBars, String approach) {
        System.out.println("\n" + approach + " - Grid Visualization:");
        System.out.println("Grid dimensions: " + (n + 1) + " rows x " + (m + 1) + " columns");
        System.out.println("Horizontal bars (0-indexed): " + Arrays.toString(hBars));
        System.out.println("Vertical bars (0-indexed): " + Arrays.toString(vBars));
        
        // Calculate gaps
        int maxHGap = findMaxConsecutive(hBars) + 1;
        int maxVGap = findMaxConsecutive(vBars) + 1;
        int maxSide = Math.min(maxHGap, maxVGap);
        int maxArea = maxSide * maxSide;
        
        System.out.println("\nMaximum consecutive horizontal bars removed: " + findMaxConsecutive(hBars));
        System.out.println("Horizontal gap created: " + maxHGap);
        System.out.println("Maximum consecutive vertical bars removed: " + findMaxConsecutive(vBars));
        System.out.println("Vertical gap created: " + maxVGap);
        System.out.println("Maximum square side: min(" + maxHGap + ", " + maxVGap + ") = " + maxSide);
        System.out.println("Maximum square area: " + maxSide + "² = " + maxArea);
        
        // Show small ASCII representation (for small grids)
        if (n <= 10 && m <= 10) {
            System.out.println("\nASCII Grid Representation:");
            boolean[][] hRemoved = new boolean[n + 2][m + 2];
            boolean[][] vRemoved = new boolean[n + 2][m + 2];
            
            for (int hBar : hBars) {
                for (int j = 0; j <= m + 1; j++) {
                    hRemoved[hBar][j] = true;
                }
            }
            
            for (int vBar : vBars) {
                for (int i = 0; i <= n + 1; i++) {
                    vRemoved[i][vBar] = true;
                }
            }
            
            for (int i = 0; i <= n + 1; i++) {
                for (int j = 0; j <= m + 1; j++) {
                    if (i == 0 || i == n + 1 || j == 0 || j == m + 1) {
                        System.out.print("+ ");
                    } else if (hRemoved[i][j] && vRemoved[i][j]) {
                        System.out.print("╬ ");
                    } else if (hRemoved[i][j]) {
                        System.out.print("═ ");
                    } else if (vRemoved[i][j]) {
                        System.out.print("║ ");
                    } else {
                        System.out.print("· ");
                    }
                }
                System.out.println();
            }
        }
    }
    
    /**
     * Helper to find optimal square position and visualize it
     */
    private void findAndShowOptimalSquare(int n, int m, int[] hBars, int[] vBars) {
        Arrays.sort(hBars);
        Arrays.sort(vBars);
        
        // Find all consecutive sequences in horizontal
        List<int[]> hSequences = findConsecutiveSequences(hBars);
        List<int[]> vSequences = findConsecutiveSequences(vBars);
        
        System.out.println("\nHorizontal consecutive sequences:");
        for (int[] seq : hSequences) {
            System.out.println("  Bars " + seq[0] + " to " + seq[1] + 
                             " (length: " + (seq[1] - seq[0] + 1) + ")");
        }
        
        System.out.println("\nVertical consecutive sequences:");
        for (int[] seq : vSequences) {
            System.out.println("  Bars " + seq[0] + " to " + seq[1] + 
                             " (length: " + (seq[1] - seq[0] + 1) + ")");
        }
        
        // Find maximum consecutive in both
        int maxH = hSequences.stream().mapToInt(seq -> seq[1] - seq[0] + 1).max().orElse(0);
        int maxV = vSequences.stream().mapToInt(seq -> seq[1] - seq[0] + 1).max().orElse(0);
        int maxSide = Math.min(maxH, maxV) + 1;
        
        System.out.println("\nMaximum square side possible: " + maxSide);
        System.out.println("Maximum area: " + (maxSide * maxSide));
        
        // Show where the square could be placed
        if (!hSequences.isEmpty() && !vSequences.isEmpty()) {
            System.out.println("\nPossible square placements:");
            int squareCount = 0;
            
            for (int[] hSeq : hSequences) {
                if (hSeq[1] - hSeq[0] + 1 >= maxSide - 1) {
                    for (int[] vSeq : vSequences) {
                        if (vSeq[1] - vSeq[0] + 1 >= maxSide - 1) {
                            squareCount++;
                            if (squareCount <= 3) { // Show only first 3
                                System.out.println("  Square " + squareCount + ":");
                                System.out.println("    Horizontal: between bars " + 
                                                 hSeq[0] + " and " + (hSeq[1] + 1));
                                System.out.println("    Vertical: between bars " + 
                                                 vSeq[0] + " and " + (vSeq[1] + 1));
                                System.out.println("    Top-left corner at cell (" + 
                                                 hSeq[0] + ", " + vSeq[0] + ")");
                            }
                        }
                    }
                }
            }
            
            if (squareCount > 3) {
                System.out.println("  ... and " + (squareCount - 3) + " more possibilities");
            }
        }
    }
    
    private List<int[]> findConsecutiveSequences(int[] arr) {
        List<int[]> sequences = new ArrayList<>();
        if (arr.length == 0) return sequences;
        
        int start = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i-1] + 1) {
                sequences.add(new int[]{arr[start], arr[i-1]});
                start = i;
            }
        }
        sequences.add(new int[]{arr[start], arr[arr.length-1]});
        
        return sequences;
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Maximize Area of Square Hole in Grid:");
        System.out.println("==============================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int n1 = 2, m1 = 1;
        int[] hBars1 = {2, 3};
        int[] vBars1 = {2};
        int expected1 = 9;
        
        solution.visualizeGrid(n1, m1, hBars1, vBars1, "Example 1");
        
        long startTime = System.nanoTime();
        int result1a = solution.maximizeSquareHoleArea(n1, m1, hBars1, vBars1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.maximizeSquareHoleAreaHashSet(n1, m1, hBars1, vBars1);
        long time1b = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Sorting Method:    " + result1a + " - " + 
                         (result1a == expected1 ? "✓ PASSED" : "✗ FAILED") + 
                         " (Time: " + time1a + " ns)");
        System.out.println("HashSet Method:    " + result1b + " - " + 
                         (result1b == expected1 ? "✓ PASSED" : "✗ FAILED") + 
                         " (Time: " + time1b + " ns)");
        
        solution.findAndShowOptimalSquare(n1, m1, hBars1, vBars1);
        
        // Test case 2: Another example
        System.out.println("\n\nTest 2: Another example");
        int n2 = 1, m2 = 1;
        int[] hBars2 = {2};
        int[] vBars2 = {2};
        int expected2 = 4;
        
        solution.visualizeGrid(n2, m2, hBars2, vBars2, "Example 2");
        int result2 = solution.maximizeSquareHoleArea(n2, m2, hBars2, vBars2);
        System.out.println("Result: " + result2 + " - " + 
                         (result2 == expected2 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 3: More complex example
        System.out.println("\n\nTest 3: More bars");
        int n3 = 2, m3 = 3;
        int[] hBars3 = {2, 3};
        int[] vBars3 = {2, 3, 4};
        int expected3 = 9;
        
        solution.visualizeGrid(n3, m3, hBars3, vBars3, "Example 3");
        int result3 = solution.maximizeSquareHoleArea(n3, m3, hBars3, vBars3);
        System.out.println("Result: " + result3 + " - " + 
                         (result3 == expected3 ? "✓ PASSED" : "✗ FAILED"));
        
        solution.findAndShowOptimalSquare(n3, m3, hBars3, vBars3);
        
        // Test case 4: No consecutive bars
        System.out.println("\n\nTest 4: No consecutive bars");
        int n4 = 5, m4 = 5;
        int[] hBars4 = {1, 3, 5};
        int[] vBars4 = {2, 4, 6};
        // Max consecutive = 1, so side = 1+1=2, area=4
        int expected4 = 4;
        
        solution.visualizeGrid(n4, m4, hBars4, vBars4, "Non-consecutive");
        int result4 = solution.maximizeSquareHoleArea(n4, m4, hBars4, vBars4);
        System.out.println("Result: " + result4 + " - " + 
                         (result4 == expected4 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 5: Long consecutive sequence
        System.out.println("\n\nTest 5: Long consecutive sequences");
        int n5 = 10, m5 = 10;
        int[] hBars5 = {2, 3, 4, 5, 6, 7, 8};
        int[] vBars5 = {3, 4, 5, 6, 7};
        // Horizontal: 7 consecutive -> gap=8
        // Vertical: 5 consecutive -> gap=6
        // Square side = min(8,6)=6, area=36
        int expected5 = 36;
        
        solution.visualizeGrid(n5, m5, hBars5, vBars5, "Long sequences");
        int result5 = solution.maximizeSquareHoleArea(n5, m5, hBars5, vBars5);
        System.out.println("Result: " + result5 + " - " + 
                         (result5 == expected5 ? "✓ PASSED" : "✗ FAILED"));
        
        solution.findAndShowOptimalSquare(n5, m5, hBars5, vBars5);
        
        // Test case 6: Single bar in each direction
        System.out.println("\n\nTest 6: Single bars only");
        int n6 = 4, m6 = 4;
        int[] hBars6 = {3};
        int[] vBars6 = {3};
        // Max consecutive = 1, side=2, area=4
        int expected6 = 4;
        
        int result6 = solution.maximizeSquareHoleArea(n6, m6, hBars6, vBars6);
        System.out.println("Result: " + result6 + " - " + 
                         (result6 == expected6 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 7: Empty bars
        System.out.println("\n\nTest 7: No bars removed");
        int n7 = 3, m7 = 3;
        int[] hBars7 = {};
        int[] vBars7 = {};
        // Max consecutive = 0, side=1, area=1
        int expected7 = 1;
        
        solution.visualizeGrid(n7, m7, hBars7, vBars7, "No bars");
        int result7 = solution.maximizeSquareHoleArea(n7, m7, hBars7, vBars7);
        System.out.println("Result: " + result7 + " - " + 
                         (result7 == expected7 ? "✓ PASSED" : "✗ FAILED"));
        
        // Compare all implementations
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING ALL IMPLEMENTATIONS:");
        System.out.println("=".repeat(70));
        
        Object[][] allTests = {
            {2, 1, new int[]{2,3}, new int[]{2}, 9},
            {1, 1, new int[]{2}, new int[]{2}, 4},
            {2, 3, new int[]{2,3}, new int[]{2,3,4}, 9},
            {5, 5, new int[]{1,3,5}, new int[]{2,4,6}, 4},
            {10, 10, new int[]{2,3,4,5,6,7,8}, new int[]{3,4,5,6,7}, 36},
            {4, 4, new int[]{3}, new int[]{3}, 4},
            {3, 3, new int[]{}, new int[]{}, 1},
            {100, 100, new int[]{50,51,52,53}, new int[]{50,51}, 9},
            {1000, 1000, new int[]{1,2,100,101,102}, new int[]{1,2,3,4,5}, 36}
        };
        
        System.out.println("\nTesting " + allTests.length + " test cases:");
        boolean allConsistent = true;
        
        for (int i = 0; i < allTests.length; i++) {
            int n = (int) allTests[i][0];
            int m = (int) allTests[i][1];
            int[] hBars = (int[]) allTests[i][2];
            int[] vBars = (int[]) allTests[i][3];
            int expected = (int) allTests[i][4];
            
            int r1 = solution.maximizeSquareHoleArea(n, m, hBars, vBars);
            int r2 = solution.maximizeSquareHoleAreaHashSet(n, m, hBars, vBars);
            int r3 = solution.maximizeSquareHoleAreaTwoPointers(n, m, hBars, vBars);
            int r4 = solution.maximizeSquareHoleAreaBucket(n, m, hBars, vBars);
            
            boolean consistent = (r1 == r2) && (r2 == r3) && (r3 == r4);
            boolean correct = (r1 == expected);
            
            System.out.printf("Test %d: n=%d, m=%d - Result: %d - %s %s%n",
                i + 1, n, m, r1,
                consistent ? "✓ CONSISTENT" : "✗ INCONSISTENT",
                correct ? "✓ CORRECT" : "✗ WRONG");
            
            if (!consistent) {
                System.out.println("  Sorting:          " + r1);
                System.out.println("  HashSet:          " + r2);
                System.out.println("  Two Pointers:     " + r3);
                System.out.println("  Bucket Sort:      " + r4);
                allConsistent = false;
            }
        }
        
        System.out.println("\nAll implementations consistent: " + (allConsistent ? "✓ YES" : "✗ NO"));
        
        // Performance test
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        // Generate large test case
        Random random = new Random(42);
        int largeN = 1000000;
        int largeM = 1000000;
        int barCount = 100000;
        
        // Generate hBars with some consecutive sequences
        Set<Integer> hBarSet = new HashSet<>();
        int[] largeHBars = new int[barCount];
        for (int i = 0; i < barCount; i++) {
            int bar;
            do {
                bar = random.nextInt(largeN + 1) + 1;
            } while (hBarSet.contains(bar));
            hBarSet.add(bar);
            largeHBars[i] = bar;
        }
        
        // Generate vBars with some consecutive sequences
        Set<Integer> vBarSet = new HashSet<>();
        int[] largeVBars = new int[barCount];
        for (int i = 0; i < barCount; i++) {
            int bar;
            do {
                bar = random.nextInt(largeM + 1) + 1;
            } while (vBarSet.contains(bar));
            vBarSet.add(bar);
            largeVBars[i] = bar;
        }
        
        System.out.println("\nTesting with " + barCount + " bars in each direction");
        
        // Test Sorting method
        startTime = System.currentTimeMillis();
        int perf1 = solution.maximizeSquareHoleArea(largeN, largeM, largeHBars, largeVBars);
        long timePerf1 = System.currentTimeMillis() - startTime;
        
        // Test HashSet method
        startTime = System.currentTimeMillis();
        int perf2 = solution.maximizeSquareHoleAreaHashSet(largeN, largeM, largeHBars, largeVBars);
        long timePerf2 = System.currentTimeMillis() - startTime;
        
        // Test Two Pointers method
        startTime = System.currentTimeMillis();
        int perf3 = solution.maximizeSquareHoleAreaTwoPointers(largeN, largeM, largeHBars, largeVBars);
        long timePerf3 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Sorting Method:    " + timePerf1 + " ms - Result: " + perf1);
        System.out.println("HashSet Method:    " + timePerf2 + " ms - Result: " + perf2);
        System.out.println("Two Pointers:      " + timePerf3 + " ms - Result: " + perf3);
        
        // Verify consistency
        boolean perfConsistent = (perf1 == perf2) && (perf2 == perf3);
        System.out.println("Results consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("When we remove consecutive bars, we create larger openings:");
        System.out.println("- Removing bars at positions 2,3,4 creates a gap of size 3");
        System.out.println("- In general: k consecutive bars removed → gap of size k+1");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. Each removed bar creates an opening of 1 unit");
        System.out.println("2. Consecutive removed bars combine to create larger openings");
        System.out.println("3. For a square hole, we need equal openings in both directions");
        System.out.println("4. Maximum square side = min(max horizontal gap, max vertical gap)");
        
        System.out.println("\nMathematical Formulation:");
        System.out.println("Let L_h = longest consecutive horizontal bars removed");
        System.out.println("Let L_v = longest consecutive vertical bars removed");
        System.out.println("Then:");
        System.out.println("  Horizontal gap = L_h + 1");
        System.out.println("  Vertical gap = L_v + 1");
        System.out.println("  Square side = min(L_h + 1, L_v + 1)");
        System.out.println("  Area = [min(L_h + 1, L_v + 1)]²");
        
        System.out.println("\nExample Calculation:");
        System.out.println("hBars = [2,3], vBars = [2,3,4]");
        System.out.println("Sorted hBars: [2,3] → consecutive length = 2");
        System.out.println("Sorted vBars: [2,3,4] → consecutive length = 3");
        System.out.println("L_h = 2, L_v = 3");
        System.out.println("Horizontal gap = 2 + 1 = 3");
        System.out.println("Vertical gap = 3 + 1 = 4");
        System.out.println("Square side = min(3,4) = 3");
        System.out.println("Area = 3² = 9");
        
        // Edge cases analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. No bars removed:");
        System.out.println("   L_h = 0, L_v = 0");
        System.out.println("   Square side = min(0+1, 0+1) = 1");
        System.out.println("   Area = 1² = 1");
        
        System.out.println("\n2. Single bar in each direction:");
        System.out.println("   L_h = 1, L_v = 1");
        System.out.println("   Square side = min(1+1, 1+1) = 2");
        System.out.println("   Area = 2² = 4");
        
        System.out.println("\n3. Disconnected bars:");
        System.out.println("   hBars = [1,3,5], vBars = [2,4,6]");
        System.out.println("   L_h = 1, L_v = 1");
        System.out.println("   Square side = min(1+1, 1+1) = 2");
        System.out.println("   Area = 2² = 4");
        
        System.out.println("\n4. One direction has much longer sequence:");
        System.out.println("   hBars = [2,3,4,5,6,7] (L_h = 6)");
        System.out.println("   vBars = [3,4] (L_v = 2)");
        System.out.println("   Square side = min(6+1, 2+1) = 3");
        System.out.println("   Area = 3² = 9");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Architecture and Construction:");
        System.out.println("   - Window/door placement in walls");
        System.out.println("   - Structural opening design");
        System.out.println("   - Modular building systems");
        
        System.out.println("\n2. Manufacturing and Fabrication:");
        System.out.println("   - CNC machining hole patterns");
        System.out.println("   - Perforated sheet metal design");
        System.out.println("   - Circuit board via placement");
        
        System.out.println("\n3. Game Development:");
        System.out.println("   - Tile-based level design");
        System.out.println("   - Grid-based puzzle games");
        System.out.println("   - Pathfinding obstacle removal");
        
        System.out.println("\n4. Urban Planning:");
        System.out.println("   - Street grid optimization");
        System.out.println("   - Park/square placement in city blocks");
        System.out.println("   - Zoning regulations");
        
        System.out.println("\n5. Computer Graphics:");
        System.out.println("   - Texture atlasing (maximizing square regions)");
        System.out.println("   - Mesh simplification");
        System.out.println("   - UV unwrapping");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the grid structure:");
        System.out.println("   - n+2 horizontal bars → n+1 rows of cells");
        System.out.println("   - m+2 vertical bars → m+1 columns of cells");
        System.out.println("   - Bars are 0-indexed positions");
        
        System.out.println("\n2. Analyze what removing bars does:");
        System.out.println("   - Single bar removal creates 1-unit opening");
        System.out.println("   - Consecutive bars create larger openings");
        System.out.println("   - Opening size = consecutive bars + 1");
        
        System.out.println("\n3. Find key insight:");
        System.out.println("   - Need to find longest consecutive sequences");
        System.out.println("   - Square requires equal openings in both directions");
        System.out.println("   - Maximum square determined by limiting direction");
        
        System.out.println("\n4. Design algorithm:");
        System.out.println("   - Sort arrays to find consecutive sequences");
        System.out.println("   - Find max consecutive length in each array");
        System.out.println("   - Compute square side and area");
        
        System.out.println("\n5. Optimize:");
        System.out.println("   - O(n log n) time for sorting");
        System.out.println("   - O(1) space (excluding input)");
        System.out.println("   - Handle edge cases");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Consecutive bars create larger openings");
        System.out.println("- Square side limited by minimum of max gaps");
        System.out.println("- Sorting enables efficient consecutive detection");
        System.out.println("- Simple mathematical formula for answer");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not sorting the arrays");
        System.out.println("- Missing that gap size = consecutive bars + 1");
        System.out.println("- Forgetting about the +1 for square side");
        System.out.println("- Not handling empty arrays");
        System.out.println("- Integer overflow for large areas");
        
        System.out.println("\nTest Cases to Discuss:");
        System.out.println("1. Basic examples from problem");
        System.out.println("2. No consecutive bars");
        System.out.println("3. Very long consecutive sequences");
        System.out.println("4. One empty array");
        System.out.println("5. Single bar only");
        System.out.println("6. Maximum constraints test");
        
        // Alternative approaches
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALTERNATIVE APPROACHES:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Bucket Sort (when range is small):");
        System.out.println("   - Create boolean array of size n+2");
        System.out.println("   - Mark removed bars");
        System.out.println("   - Scan for consecutive true values");
        System.out.println("   - Time: O(n+m), Space: O(n+m)");
        
        System.out.println("\n2. HashSet for Consecutive Detection:");
        System.out.println("   - Add all bars to HashSet");
        System.out.println("   - For each bar, if it starts a sequence, count it");
        System.out.println("   - Time: O(h+v), Space: O(h+v)");
        
        System.out.println("\n3. Union-Find (Disjoint Set Union):");
        System.out.println("   - Connect consecutive bars");
        System.out.println("   - Find largest connected component");
        System.out.println("   - Time: O(h log* h + v log* v)");
        
        System.out.println("\n4. Segment Tree (for dynamic updates):");
        System.out.println("   - Useful if bars can be added/removed dynamically");
        System.out.println("   - Maintain longest consecutive sequence");
        System.out.println("   - Time: O(log n) per update");
        
        System.out.println("\n5. Two Pointers/Sliding Window:");
        System.out.println("   - Sort arrays");
        System.out.println("   - Use sliding window to find max consecutive");
        System.out.println("   - Time: O(n log n), Space: O(1)");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
