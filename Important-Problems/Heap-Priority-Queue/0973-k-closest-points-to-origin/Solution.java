
## Solution.java

```java
/**
 * 973. K Closest Points to Origin
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of points where points[i] = [xi, yi] and an integer k,
 * return the k closest points to the origin (0, 0).
 * 
 * Key Insights:
 * 1. Use squared distance to avoid expensive sqrt operation
 * 2. Max-heap of size k maintains k closest points
 * 3. When heap size > k, remove point with largest distance
 * 4. Alternative: QuickSelect for O(n) average time
 * 
 * Approach (Max-Heap):
 * 1. Calculate squared distance for each point
 * 2. Use max-heap based on distance
 * 3. Maintain heap size = k
 * 4. Return all elements from heap
 * 
 * Time Complexity: O(n log k)
 * Space Complexity: O(k)
 * 
 * Tags: Array, Heap, Priority Queue, Geometry, QuickSelect
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Max-Heap (Priority Queue) - RECOMMENDED
     * O(n log k) time, O(k) space
     */
    public int[][] kClosest(int[][] points, int k) {
        // Max-heap based on distance (largest distance at top)
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(getDistanceSquared(b), getDistanceSquared(a))
        );
        
        for (int[] point : points) {
            maxHeap.offer(point);
            
            // Maintain heap size = k
            if (maxHeap.size() > k) {
                maxHeap.poll(); // Remove point with largest distance
            }
        }
        
        // Convert heap to result array
        int[][] result = new int[k][2];
        int index = 0;
        while (!maxHeap.isEmpty()) {
            result[index++] = maxHeap.poll();
        }
        
        return result;
    }
    
    /**
     * Approach 2: Sorting with Custom Comparator
     * O(n log n) time, O(log n) space for sorting
     */
    public int[][] kClosestSorting(int[][] points, int k) {
        // Sort points based on squared distance
        Arrays.sort(points, (a, b) -> 
            Integer.compare(getDistanceSquared(a), getDistanceSquared(b))
        );
        
        // Return first k points
        return Arrays.copyOfRange(points, 0, k);
    }
    
    /**
     * Approach 3: QuickSelect (Hoare's Selection Algorithm)
     * O(n) average time, O(1) space
     */
    public int[][] kClosestQuickSelect(int[][] points, int k) {
        quickSelect(points, 0, points.length - 1, k);
        return Arrays.copyOfRange(points, 0, k);
    }
    
    private void quickSelect(int[][] points, int left, int right, int k) {
        if (left >= right) return;
        
        int pivotIndex = partition(points, left, right);
        
        if (pivotIndex == k) {
            return;
        } else if (pivotIndex < k) {
            quickSelect(points, pivotIndex + 1, right, k);
        } else {
            quickSelect(points, left, pivotIndex - 1, k);
        }
    }
    
    private int partition(int[][] points, int left, int right) {
        // Use middle element as pivot for better performance
        int pivotIndex = left + (right - left) / 2;
        int pivotDist = getDistanceSquared(points[pivotIndex]);
        
        // Move pivot to end
        swap(points, pivotIndex, right);
        
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (getDistanceSquared(points[i]) < pivotDist) {
                swap(points, storeIndex, i);
                storeIndex++;
            }
        }
        
        // Move pivot to final position
        swap(points, storeIndex, right);
        return storeIndex;
    }
    
    private void swap(int[][] points, int i, int j) {
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }
    
    /**
     * Approach 4: Min-Heap (Alternative Heap Approach)
     * O(n log n) time, O(n) space - Less efficient
     */
    public int[][] kClosestMinHeap(int[][] points, int k) {
        // Min-heap with all points
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(getDistanceSquared(a), getDistanceSquared(b))
        );
        
        for (int[] point : points) {
            minHeap.offer(point);
        }
        
        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll();
        }
        
        return result;
    }
    
    /**
     * Approach 5: Binary Search with Counting
     * O(n log W) time where W is distance range, O(n) space
     */
    public int[][] kClosestBinarySearch(int[][] points, int k) {
        // Precompute distances
        int[] distances = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            distances[i] = getDistanceSquared(points[i]);
        }
        
        // Binary search for the k-th smallest distance
        int low = 0, high = (int) 2e8; // Max possible squared distance
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (countPointsWithinDistance(points, distances, mid) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        
        // Collect k closest points
        int[][] result = new int[k][2];
        int index = 0;
        for (int i = 0; i < points.length && index < k; i++) {
            if (getDistanceSquared(points[i]) <= low) {
                result[index++] = points[i];
            }
        }
        
        return result;
    }
    
    private int countPointsWithinDistance(int[][] points, int[] distances, int threshold) {
        int count = 0;
        for (int dist : distances) {
            if (dist <= threshold) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Helper method to calculate squared distance (avoids sqrt)
     */
    private int getDistanceSquared(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }
    
    /**
     * Helper method to calculate actual distance (for display purposes)
     */
    private double getActualDistance(int[] point) {
        return Math.sqrt(point[0] * point[0] + point[1] * point[1]);
    }
    
    /**
     * Helper method to print points with distances
     */
    private void printPointsWithDistances(int[][] points) {
        System.out.println("Points with distances:");
        for (int[] point : points) {
            double dist = getActualDistance(point);
            System.out.printf("  [%d, %d] -> distance: %.2f (squared: %d)%n", 
                            point[0], point[1], dist, getDistanceSquared(point));
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing K Closest Points to Origin:");
        System.out.println("====================================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        int[][] points1 = {{1, 3}, {-2, 2}};
        int k1 = 1;
        
        long startTime = System.nanoTime();
        int[][] result1a = solution.kClosest(points1, k1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result1b = solution.kClosestSorting(points1, k1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result1c = solution.kClosestQuickSelect(points1, k1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result1d = solution.kClosestMinHeap(points1, k1);
        long time1d = System.nanoTime() - startTime;
        
        System.out.println("Input points:");
        solution.printPointsWithDistances(points1);
        System.out.println("k = " + k1);
        
        boolean test1a = Arrays.deepEquals(result1a, new int[][]{{-2, 2}});
        boolean test1b = Arrays.deepEquals(result1b, new int[][]{{-2, 2}});
        boolean test1c = Arrays.deepEquals(result1c, new int[][]{{-2, 2}});
        boolean test1d = Arrays.deepEquals(result1d, new int[][]{{-2, 2}});
        
        System.out.println("Max-Heap: " + Arrays.deepToString(result1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Sorting: " + Arrays.deepToString(result1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("QuickSelect: " + Arrays.deepToString(result1c) + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Min-Heap: " + Arrays.deepToString(result1d) + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Test case 2: Example 2
        System.out.println("\nTest 2: Example 2");
        int[][] points2 = {{3, 3}, {5, -1}, {-2, 4}};
        int k2 = 2;
        int[][] result2 = solution.kClosest(points2, k2);
        
        System.out.println("Input points:");
        solution.printPointsWithDistances(points2);
        System.out.println("k = " + k2);
        System.out.println("Result: " + Arrays.deepToString(result2));
        
        // Verify result contains correct points (order doesn't matter)
        Set<String> expectedSet = new HashSet<>();
        expectedSet.add(Arrays.toString(new int[]{3, 3}));
        expectedSet.add(Arrays.toString(new int[]{-2, 4}));
        
        Set<String> resultSet = new HashSet<>();
        for (int[] point : result2) {
            resultSet.add(Arrays.toString(point));
        }
        
        boolean test2 = resultSet.equals(expectedSet);
        System.out.println("Example 2: " + (test2 ? "PASSED" : "FAILED"));
        
        // Test case 3: All points same distance
        System.out.println("\nTest 3: All points same distance");
        int[][] points3 = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int k3 = 2;
        int[][] result3 = solution.kClosest(points3, k3);
        System.out.println("All same distance - returned " + result3.length + " points: " + (result3.length == k3 ? "PASSED" : "FAILED"));
        
        // Test case 4: k equals array length
        System.out.println("\nTest 4: k equals array length");
        int[][] points4 = {{1, 1}, {2, 2}, {3, 3}};
        int k4 = 3;
        int[][] result4 = solution.kClosest(points4, k4);
        System.out.println("k = n: " + (result4.length == k4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single point
        System.out.println("\nTest 5: Single point");
        int[][] points5 = {{0, 0}};
        int k5 = 1;
        int[][] result5 = solution.kClosest(points5, k5);
        boolean test5 = Arrays.deepEquals(result5, new int[][]{{0, 0}});
        System.out.println("Single point: " + (test5 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  Max-Heap: " + time1a + " ns");
        System.out.println("  Sorting: " + time1b + " ns");
        System.out.println("  QuickSelect: " + time1c + " ns");
        System.out.println("  Min-Heap: " + time1d + " ns");
        
        // Performance test with larger input
        System.out.println("\nPerformance Test with 1000 points, k=10:");
        int[][] largePoints = generateRandomPoints(1000);
        
        startTime = System.nanoTime();
        solution.kClosest(largePoints, 10);
        long heapTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.kClosestSorting(largePoints, 10);
        long sortTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.kClosestQuickSelect(largePoints, 10);
        long quickSelectTime = System.nanoTime() - startTime;
        
        System.out.println("Max-Heap: " + heapTime + " ns");
        System.out.println("Sorting: " + sortTime + " ns");
        System.out.println("QuickSelect: " + quickSelectTime + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MAX-HEAP ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        solution.explainMaxHeapApproach();
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON:");
        System.out.println("=".repeat(70));
        
        solution.compareApproaches();
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Generate random points for performance testing
     */
    private static int[][] generateRandomPoints(int n) {
        int[][] points = new int[n][2];
        Random random = new Random(42);
        for (int i = 0; i < n; i++) {
            points[i][0] = random.nextInt(20001) - 10000; // -10000 to 10000
            points[i][1] = random.nextInt(20001) - 10000;
        }
        return points;
    }
    
    /**
     * Detailed explanation of max-heap approach
     */
    private void explainMaxHeapApproach() {
        System.out.println("\nWhy Max-Heap for K Closest?");
        System.out.println("1. We want to maintain the k smallest distances");
        System.out.println("2. Max-heap gives O(1) access to the largest of k elements");
        System.out.println("3. When we find a point closer than the current kth closest,");
        System.out.println("   we remove the farthest and add the new closer point");
        System.out.println("4. This ensures we always have the k closest points");
        
        System.out.println("\nStep-by-step Example:");
        System.out.println("Points: [[1,3], [-2,2]], k=1");
        System.out.println("Distances: [1,3] -> 10, [-2,2] -> 8");
        System.out.println("1. Add [1,3] to heap: heap = [[1,3]]");
        System.out.println("2. Add [-2,2] to heap: heap = [[1,3], [-2,2]]");
        System.out.println("3. Heap size (2) > k (1), remove largest: remove [1,3]");
        System.out.println("4. Final heap: [[-2,2]] -> Result: [[-2,2]]");
        
        System.out.println("\nWhy Squared Distance?");
        System.out.println("- Actual distance = sqrt(x² + y²)");
        System.out.println("- sqrt is expensive to compute");
        System.out.println("- For comparison, x² + y² preserves order");
        System.out.println("- So we can avoid sqrt and save computation");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n log k) - n points, each heap operation O(log k)");
        System.out.println("- Space: O(k) - only store k points in heap");
        System.out.println("- Best when k << n (common in practice)");
    }
    
    /**
     * Compare different approaches
     */
    private void compareApproaches() {
        System.out.println("\n1. Max-Heap (RECOMMENDED):");
        System.out.println("   Time: O(n log k)");
        System.out.println("   Space: O(k)");
        System.out.println("   Pros: Efficient when k is small, simple implementation");
        System.out.println("   Cons: Slower when k is close to n");
        System.out.println("   Best for: General case, especially when k << n");
        
        System.out.println("\n2. Sorting:");
        System.out.println("   Time: O(n log n)");
        System.out.println("   Space: O(log n) for sorting");
        System.out.println("   Pros: Simple, good for small n");
        System.out.println("   Cons: Inefficient for large n when k is small");
        System.out.println("   Best for: Small inputs or when k ≈ n");
        
        System.out.println("\n3. QuickSelect:");
        System.out.println("   Time: O(n) average, O(n²) worst case");
        System.out.println("   Space: O(1)");
        System.out.println("   Pros: Optimal average time, in-place");
        System.out.println("   Cons: Worst case O(n²), more complex");
        System.out.println("   Best for: Average-case performance, memory constraints");
        
        System.out.println("\n4. Min-Heap:");
        System.out.println("   Time: O(n log n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros: Simple to understand");
        System.out.println("   Cons: Less efficient than max-heap");
        System.out.println("   Best for: Educational purposes");
        
        System.out.println("\n5. Binary Search:");
        System.out.println("   Time: O(n log W) where W is distance range");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros: Good theoretical bounds");
        System.out.println("   Cons: Complex implementation, constant factors");
        System.out.println("   Best for: Theoretical interest, specific constraints");
        
        System.out.println("\nRecommendation by Use Case:");
        System.out.println("- Interview: Max-Heap (most expected)");
        System.out.println("- Production: Max-Heap for k << n, QuickSelect otherwise");
        System.out.println("- Memory constrained: QuickSelect");
        System.out.println("- Simple solution: Sorting");
    }
}

/**
 * Additional utility class for point operations
 */
class PointUtils {
    /**
     * Calculate Euclidean distance between two points
     */
    public static double distance(int[] p1, int[] p2) {
        return Math.sqrt(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2));
    }
    
    /**
     * Calculate squared distance (for comparison)
     */
    public static int squaredDistance(int[] p1, int[] p2) {
        return (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);
    }
    
    /**
     * Check if two point arrays are equal (order doesn't matter)
     */
    public static boolean arePointSetsEqual(int[][] points1, int[][] points2) {
        if (points1.length != points2.length) return false;
        
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        
        for (int[] point : points1) set1.add(Arrays.toString(point));
        for (int[] point : points2) set2.add(Arrays.toString(point));
        
        return set1.equals(set2);
    }
}
