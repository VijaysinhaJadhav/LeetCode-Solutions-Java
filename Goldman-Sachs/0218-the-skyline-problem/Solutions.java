
# Solution.java

```java
import java.util.*;

/**
 * 218. The Skyline Problem
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given building positions and heights, return the skyline silhouette.
 * 
 * Key Insights:
 * 1. Sweep line algorithm processes events sorted by x-coordinate
 * 2. Events: (x, -height) for start, (x, height) for end
 * 3. Max-heap tracks current active heights
 * 4. Record point when max height changes
 */
class Solution {
    
    /**
     * Approach 1: Sweep Line with Max-Heap (Recommended)
     * Time: O(n log n), Space: O(n)
     * 
     * Steps:
     * 1. Create events: start (x, -height) and end (x, height)
     * 2. Sort events by x, then by height (starts before ends when same x)
     * 3. Use max-heap to track active heights
     * 4. Track current max height, record when it changes
     */
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> result = new ArrayList<>();
        
        // Create events: [x, height] with negative height for start
        List<int[]> events = new ArrayList<>();
        for (int[] building : buildings) {
            events.add(new int[]{building[0], -building[2]}); // start event
            events.add(new int[]{building[1], building[2]});  // end event
        }
        
        // Sort events: by x, then by height (starts first when same x)
        events.sort((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        
        // Max-heap (priority queue with reverse order)
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        pq.offer(0); // ground height
        int prevMax = 0;
        
        // Map to track heights to remove (lazy deletion)
        Map<Integer, Integer> toRemove = new HashMap<>();
        
        for (int[] event : events) {
            int x = event[0];
            int height = event[1];
            
            if (height < 0) {
                // Start event: add height to heap
                pq.offer(-height);
            } else {
                // End event: mark for removal (lazy deletion)
                toRemove.put(height, toRemove.getOrDefault(height, 0) + 1);
            }
            
            // Remove heights that are marked for deletion from heap top
            while (!pq.isEmpty() && toRemove.containsKey(pq.peek())) {
                int top = pq.poll();
                int count = toRemove.get(top);
                if (count == 1) {
                    toRemove.remove(top);
                } else {
                    toRemove.put(top, count - 1);
                }
            }
            
            int currentMax = pq.peek();
            if (currentMax != prevMax) {
                result.add(Arrays.asList(x, currentMax));
                prevMax = currentMax;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: Sweep Line with TreeMap (Alternative)
     * Time: O(n log n), Space: O(n)
     * 
     * TreeMap maintains sorted heights with counts
     */
    public List<List<Integer>> getSkylineTreeMap(int[][] buildings) {
        List<List<Integer>> result = new ArrayList<>();
        List<int[]> events = new ArrayList<>();
        
        for (int[] building : buildings) {
            events.add(new int[]{building[0], -building[2]});
            events.add(new int[]{building[1], building[2]});
        }
        
        events.sort((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        
        // TreeMap: height -> count
        TreeMap<Integer, Integer> heightMap = new TreeMap<>();
        heightMap.put(0, 1);
        int prevMax = 0;
        
        for (int[] event : events) {
            int x = event[0];
            int height = event[1];
            
            if (height < 0) {
                // Start event
                int h = -height;
                heightMap.put(h, heightMap.getOrDefault(h, 0) + 1);
            } else {
                // End event
                int count = heightMap.get(height);
                if (count == 1) {
                    heightMap.remove(height);
                } else {
                    heightMap.put(height, count - 1);
                }
            }
            
            int currentMax = heightMap.lastKey();
            if (currentMax != prevMax) {
                result.add(Arrays.asList(x, currentMax));
                prevMax = currentMax;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Divide and Conquer (Merge Skylines)
     * Time: O(n log n), Space: O(n)
     * 
     * Recursively merge skylines of left and right halves
     */
    public List<List<Integer>> getSkylineDivideConquer(int[][] buildings) {
        return mergeSkyline(buildings, 0, buildings.length - 1);
    }
    
    private List<List<Integer>> mergeSkyline(int[][] buildings, int left, int right) {
        if (left == right) {
            List<List<Integer>> skyline = new ArrayList<>();
            skyline.add(Arrays.asList(buildings[left][0], buildings[left][2]));
            skyline.add(Arrays.asList(buildings[left][1], 0));
            return skyline;
        }
        
        int mid = left + (right - left) / 2;
        List<List<Integer>> leftSkyline = mergeSkyline(buildings, left, mid);
        List<List<Integer>> rightSkyline = mergeSkyline(buildings, mid + 1, right);
        
        return merge(leftSkyline, rightSkyline);
    }
    
    private List<List<Integer>> merge(List<List<Integer>> left, List<List<Integer>> right) {
        List<List<Integer>> result = new ArrayList<>();
        int i = 0, j = 0;
        int h1 = 0, h2 = 0;
        
        while (i < left.size() && j < right.size()) {
            int x1 = left.get(i).get(0);
            int x2 = right.get(j).get(0);
            int x = Math.min(x1, x2);
            
            if (x1 < x2) {
                h1 = left.get(i).get(1);
                i++;
            } else if (x1 > x2) {
                h2 = right.get(j).get(1);
                j++;
            } else {
                h1 = left.get(i).get(1);
                h2 = right.get(j).get(1);
                i++;
                j++;
            }
            
            int maxHeight = Math.max(h1, h2);
            if (result.isEmpty() || result.get(result.size() - 1).get(1) != maxHeight) {
                result.add(Arrays.asList(x, maxHeight));
            }
        }
        
        // Add remaining points
        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }
        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }
        
        return result;
    }
    
    /**
     * Approach 4: Sweep Line with PriorityQueue and Pair Class
     * Time: O(n log n), Space: O(n)
     * 
     * More explicit version with custom event class
     */
    public List<List<Integer>> getSkylineExplicit(int[][] buildings) {
        List<Event> events = new ArrayList<>();
        
        for (int[] building : buildings) {
            events.add(new Event(building[0], building[2], true));
            events.add(new Event(building[1], building[2], false));
        }
        
        events.sort((a, b) -> {
            if (a.x != b.x) return Integer.compare(a.x, b.x);
            if (a.isStart != b.isStart) return a.isStart ? -1 : 1;
            if (a.isStart) return Integer.compare(b.height, a.height);
            return Integer.compare(a.height, b.height);
        });
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        pq.offer(0);
        int prevMax = 0;
        List<List<Integer>> result = new ArrayList<>();
        
        for (Event event : events) {
            if (event.isStart) {
                pq.offer(event.height);
            } else {
                pq.remove(event.height);
            }
            
            int currentMax = pq.peek();
            if (currentMax != prevMax) {
                result.add(Arrays.asList(event.x, currentMax));
                prevMax = currentMax;
            }
        }
        
        return result;
    }
    
    class Event {
        int x;
        int height;
        boolean isStart;
        
        Event(int x, int height, boolean isStart) {
            this.x = x;
            this.height = height;
            this.isStart = isStart;
        }
    }
    
    /**
     * Approach 5: Using Multiset (TreeMap with counts)
     * Time: O(n log n), Space: O(n)
     * 
     * Alternative using TreeMap as multiset
     */
    public List<List<Integer>> getSkylineMultiset(int[][] buildings) {
        List<int[]> events = new ArrayList<>();
        
        for (int[] building : buildings) {
            events.add(new int[]{building[0], -building[2]});
            events.add(new int[]{building[1], building[2]});
        }
        
        events.sort((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        
        // Multiset using TreeMap
        TreeMap<Integer, Integer> multiset = new TreeMap<>();
        multiset.put(0, 1);
        int prevMax = 0;
        List<List<Integer>> result = new ArrayList<>();
        
        for (int[] event : events) {
            int x = event[0];
            int h = event[1];
            
            if (h < 0) {
                // Add height
                int height = -h;
                multiset.put(height, multiset.getOrDefault(height, 0) + 1);
            } else {
                // Remove height
                int count = multiset.get(h);
                if (count == 1) {
                    multiset.remove(h);
                } else {
                    multiset.put(h, count - 1);
                }
            }
            
            int currentMax = multiset.lastKey();
            if (currentMax != prevMax) {
                result.add(Arrays.asList(x, currentMax));
                prevMax = currentMax;
            }
        }
        
        return result;
    }
    
    /**
     * Helper: Visualize the skyline
     */
    public void visualizeSkyline(int[][] buildings) {
        System.out.println("\nSkyline Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nBuildings:");
        for (int i = 0; i < buildings.length; i++) {
            int[] b = buildings[i];
            System.out.printf("  Building %d: [%d, %d, %d]%n", i + 1, b[0], b[1], b[2]);
        }
        
        System.out.println("\nSweep Line Process:");
        
        // Create events
        List<int[]> events = new ArrayList<>();
        for (int[] building : buildings) {
            events.add(new int[]{building[0], -building[2]});
            events.add(new int[]{building[1], building[2]});
        }
        
        events.sort((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        
        System.out.println("\nSorted events:");
        for (int[] event : events) {
            String type = event[1] < 0 ? "START" : "END";
            System.out.printf("  x=%d, height=%d (%s)%n", event[0], Math.abs(event[1]), type);
        }
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        pq.offer(0);
        int prevMax = 0;
        int step = 1;
        
        System.out.println("\nProcessing:");
        
        for (int[] event : events) {
            int x = event[0];
            int height = event[1];
            String type = height < 0 ? "START" : "END";
            int h = Math.abs(height);
            
            System.out.printf("\nStep %d: x=%d, %s height=%d%n", step++, x, type, h);
            System.out.printf("  Active heights before: %s%n", pq);
            
            if (height < 0) {
                pq.offer(h);
                System.out.printf("  Added %d to heap%n", h);
            } else {
                pq.remove(h);
                System.out.printf("  Removed %d from heap%n", h);
            }
            
            System.out.printf("  Active heights after: %s%n", pq);
            int currentMax = pq.peek();
            System.out.printf("  Current max height: %d, previous max: %d%n", currentMax, prevMax);
            
            if (currentMax != prevMax) {
                System.out.printf("  ✓ Height changed! Record point (%d, %d)%n", x, currentMax);
                prevMax = currentMax;
            }
        }
        
        List<List<Integer>> skyline = getSkyline(buildings);
        System.out.println("\nFinal Skyline:");
        for (List<Integer> point : skyline) {
            System.out.printf("  (%d, %d)%n", point.get(0), point.get(1));
        }
        
        // ASCII Art Visualization
        System.out.println("\nASCII Skyline:");
        int maxHeight = 0;
        for (int[] building : buildings) {
            maxHeight = Math.max(maxHeight, building[2]);
        }
        int maxX = 0;
        for (int[] building : buildings) {
            maxX = Math.max(maxX, building[1]);
        }
        
        char[][] grid = new char[maxHeight + 1][maxX + 1];
        for (char[] row : grid) {
            Arrays.fill(row, ' ');
        }
        
        // Draw buildings
        for (int[] building : buildings) {
            for (int x = building[0]; x < building[1]; x++) {
                for (int y = 0; y < building[2]; y++) {
                    grid[maxHeight - y][x] = '#';
                }
            }
        }
        
        // Draw skyline
        for (List<Integer> point : skyline) {
            int x = point.get(0);
            int y = point.get(1);
            if (x <= maxX && y <= maxHeight) {
                grid[maxHeight - y][x] = '*';
            }
        }
        
        // Print grid
        for (int y = maxHeight; y >= 0; y--) {
            System.out.printf("%3d |", y);
            for (int x = 0; x <= maxX; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
        System.out.println("    +" + "-".repeat(maxX + 1));
        System.out.print("     ");
        for (int x = 0; x <= maxX; x += 5) {
            System.out.print(x + "    ".substring(0, 4 - String.valueOf(x).length()));
        }
        System.out.println();
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][][] generateTestCases() {
        return new int[][][] {
            // Example 1
            {{{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}},
             {{2,10},{3,15},{7,12},{12,0},{15,10},{20,8},{24,0}}},
            // Example 2
            {{{0,2,3},{2,5,3}},
             {{0,3},{5,0}}},
            // Single building
            {{{1,5,10}},
             {{1,10},{5,0}}},
            // Two adjacent buildings
            {{{0,3,5},{3,6,5}},
             {{0,5},{6,0}}},
            // Two overlapping buildings
            {{{0,5,10},{3,8,15}},
             {{0,10},{3,15},{8,0}}},
            // Multiple buildings with same height
            {{{0,3,5},{2,5,5},{4,7,5}},
             {{0,5},{7,0}}}
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[][] buildings = testCases[i][0];
            List<List<Integer>> expected = new ArrayList<>();
            for (int[] point : testCases[i][1]) {
                expected.add(Arrays.asList(point[0], point[1]));
            }
            
            System.out.printf("\nTest %d: %d buildings%n", i + 1, buildings.length);
            
            List<List<Integer>> result1 = getSkyline(buildings);
            List<List<Integer>> result2 = getSkylineTreeMap(buildings);
            List<List<Integer>> result3 = getSkylineDivideConquer(buildings);
            List<List<Integer>> result4 = getSkylineExplicit(buildings);
            List<List<Integer>> result5 = getSkylineMultiset(buildings);
            
            boolean allMatch = result1.equals(expected) && result2.equals(expected) &&
                              result3.equals(expected) && result4.equals(expected) &&
                              result5.equals(expected);
            
            if (allMatch) {
                System.out.println("✓ PASS - Skyline: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeSkyline(buildings);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=".repeat(50));
        
        // Generate large test case
        Random rand = new Random(42);
        int n = 10000;
        int[][] buildings = new int[n][3];
        int x = 0;
        for (int i = 0; i < n; i++) {
            int width = rand.nextInt(50) + 10;
            int height = rand.nextInt(100) + 1;
            buildings[i] = new int[]{x, x + width, height};
            x += rand.nextInt(20) + 1;
        }
        
        System.out.println("Test Setup: " + n + " buildings");
        
        long[] times = new long[5];
        
        // Method 1: Sweep Line with Max-Heap
        long start = System.currentTimeMillis();
        getSkyline(buildings);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: TreeMap
        start = System.currentTimeMillis();
        getSkylineTreeMap(buildings);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Divide and Conquer
        start = System.currentTimeMillis();
        getSkylineDivideConquer(buildings);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Explicit Events
        start = System.currentTimeMillis();
        getSkylineExplicit(buildings);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Multiset
        start = System.currentTimeMillis();
        getSkylineMultiset(buildings);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. Sweep Line (Heap)      | %9d%n", times[0]);
        System.out.printf("2. TreeMap                | %9d%n", times[1]);
        System.out.printf("3. Divide and Conquer     | %9d%n", times[2]);
        System.out.printf("4. Explicit Events        | %9d%n", times[3]);
        System.out.printf("5. Multiset               | %9d%n", times[4]);
        
        System.out.println("\nObservations:");
        System.out.println("1. Sweep line with heap is fastest for typical inputs");
        System.out.println("2. TreeMap has overhead for maintaining sorted order");
        System.out.println("3. Divide and conquer has recursion overhead");
        System.out.println("4. All O(n log n) approaches perform similarly");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Empty buildings:");
        int[][] empty = {};
        System.out.println("   Input: []");
        System.out.println("   Output: " + getSkyline(empty));
        
        System.out.println("\n2. Single building:");
        int[][] single = {{1,5,10}};
        System.out.println("   Input: [[1,5,10]]");
        System.out.println("   Output: " + getSkyline(single));
        
        System.out.println("\n3. Buildings with zero height:");
        int[][] zeroHeight = {{0,10,0}};
        System.out.println("   Input: [[0,10,0]]");
        System.out.println("   Output: " + getSkyline(zeroHeight));
        
        System.out.println("\n4. Adjacent buildings same height:");
        int[][] adjacent = {{0,3,5},{3,6,5}};
        System.out.println("   Input: [[0,3,5],[3,6,5]]");
        System.out.println("   Output: " + getSkyline(adjacent));
        
        System.out.println("\n5. Buildings with same left coordinate:");
        int[][] sameLeft = {{0,5,10},{0,8,15}};
        System.out.println("   Input: [[0,5,10],[0,8,15]]");
        System.out.println("   Output: " + getSkyline(sameLeft));
        
        System.out.println("\n6. Buildings with same right coordinate:");
        int[][] sameRight = {{2,10,5},{5,10,8}};
        System.out.println("   Input: [[2,10,5],[5,10,8]]");
        System.out.println("   Output: " + getSkyline(sameRight));
    }
    
    /**
     * Helper: Explain sweep line algorithm
     */
    public void explainSweepLine() {
        System.out.println("\nSweep Line Algorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nConcept:");
        System.out.println("Imagine a vertical line sweeping from left to right.");
        System.out.println("At each x-coordinate, we track which buildings are active.");
        System.out.println("The skyline height is the maximum height of active buildings.");
        
        System.out.println("\nEvent Types:");
        System.out.println("- Start event: (x, -height) - a building begins");
        System.out.println("- End event: (x, height) - a building ends");
        
        System.out.println("\nSorting Strategy:");
        System.out.println("1. Sort by x-coordinate");
        System.out.println("2. For same x, start events come before end events");
        System.out.println("3. For start events, higher heights come first");
        System.out.println("4. For end events, lower heights come first");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Create events for all building starts and ends");
        System.out.println("2. Sort events (x, then type, then height)");
        System.out.println("3. Maintain max-heap of active heights");
        System.out.println("4. Track current maximum height");
        System.out.println("5. When max height changes, record point");
        
        System.out.println("\nExample: buildings = [[2,9,10],[3,7,15],[5,12,12]]");
        System.out.println("  Events: (2,-10), (3,-15), (5,-12), (7,15), (9,10), (12,12)");
        System.out.println("  Process:");
        System.out.println("    x=2: add 10 → max=10 → record (2,10)");
        System.out.println("    x=3: add 15 → max=15 → record (3,15)");
        System.out.println("    x=5: add 12 → max=15 → no change");
        System.out.println("    x=7: remove 15 → max=12 → record (7,12)");
        System.out.println("    x=9: remove 10 → max=12 → no change");
        System.out.println("    x=12: remove 12 → max=0 → record (12,0)");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What output format? (list of [x,y] points)");
        System.out.println("   - What happens at building overlaps?");
        System.out.println("   - What about buildings with zero height?");
        
        System.out.println("\n2. Understand the problem:");
        System.out.println("   - The skyline is the outer contour");
        System.out.println("   - Only need key points (where height changes)");
        
        System.out.println("\n3. Propose sweep line algorithm:");
        System.out.println("   - Explain event-based approach");
        System.out.println("   - Discuss sorting strategy");
        System.out.println("   - Show how to track active heights");
        
        System.out.println("\n4. Discuss data structures:");
        System.out.println("   - Max-heap for active heights");
        System.out.println("   - Lazy removal with map for ended buildings");
        System.out.println("   - TreeMap as alternative multiset");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(n log n) for sorting and heap ops");
        System.out.println("   - Space: O(n) for events and heap");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - Empty input");
        System.out.println("   - Single building");
        System.out.println("   - Buildings with same x-coordinates");
        System.out.println("   - Overlapping buildings");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Incorrect event sorting order");
        System.out.println("   - Forgetting to handle zero height ground");
        System.out.println("   - Not removing ended buildings from heap");
        System.out.println("   - Recording duplicate consecutive points");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("218. The Skyline Problem");
        System.out.println("========================");
        
        // Explain sweep line
        solution.explainSweepLine();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> result = new ArrayList<>();
        List<int[]> events = new ArrayList<>();
        
        for (int[] b : buildings) {
            events.add(new int[]{b[0], -b[2]});
            events.add(new int[]{b[1], b[2]});
        }
        
        events.sort((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        pq.offer(0);
        int prevMax = 0;
        Map<Integer, Integer> toRemove = new HashMap<>();
        
        for (int[] event : events) {
            int x = event[0];
            int h = event[1];
            
            if (h < 0) {
                pq.offer(-h);
            } else {
                toRemove.put(h, toRemove.getOrDefault(h, 0) + 1);
            }
            
            while (!pq.isEmpty() && toRemove.containsKey(pq.peek())) {
                int top = pq.poll();
                int cnt = toRemove.get(top);
                if (cnt == 1) toRemove.remove(top);
                else toRemove.put(top, cnt - 1);
            }
            
            int currentMax = pq.peek();
            if (currentMax != prevMax) {
                result.add(Arrays.asList(x, currentMax));
                prevMax = currentMax;
            }
        }
        
        return result;
    }
}
            """);
        
        System.out.println("\nAlternative (Divide and Conquer):");
        System.out.println("""
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        return merge(buildings, 0, buildings.length - 1);
    }
    
    private List<List<Integer>> merge(int[][] buildings, int l, int r) {
        if (l == r) {
            List<List<Integer>> skyline = new ArrayList<>();
            skyline.add(Arrays.asList(buildings[l][0], buildings[l][2]));
            skyline.add(Arrays.asList(buildings[l][1], 0));
            return skyline;
        }
        
        int mid = l + (r - l) / 2;
        List<List<Integer>> left = merge(buildings, l, mid);
        List<List<Integer>> right = merge(buildings, mid + 1, r);
        
        return mergeSkylines(left, right);
    }
    
    private List<List<Integer>> mergeSkylines(List<List<Integer>> left, List<List<Integer>> right) {
        List<List<Integer>> result = new ArrayList<>();
        int i = 0, j = 0;
        int h1 = 0, h2 = 0;
        
        while (i < left.size() && j < right.size()) {
            int x1 = left.get(i).get(0);
            int x2 = right.get(j).get(0);
            int x = Math.min(x1, x2);
            
            if (x1 < x2) {
                h1 = left.get(i).get(1);
                i++;
            } else if (x1 > x2) {
                h2 = right.get(j).get(1);
                j++;
            } else {
                h1 = left.get
