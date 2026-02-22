
# Solution.java

```java
import java.util.*;

/**
 * 1631. Path With Minimum Effort
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find path from top-left to bottom-right minimizing the maximum height difference
 * between consecutive cells.
 * 
 * Key Insights:
 * 1. This is a min-max path problem (minimize the maximum edge weight)
 * 2. Dijkstra can be adapted with max() instead of sum()
 * 3. Binary search + BFS is another elegant solution
 * 4. Union-Find with sorted edges also works
 */
class Solution {
    
    /**
     * Approach 1: Dijkstra's Algorithm (Modified) - RECOMMENDED
     * Time: O(rows × cols × log(rows × cols)), Space: O(rows × cols)
     * 
     * Use priority queue to always expand the cell with smallest current max effort
     */
    public int minimumEffortPath(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;
        
        // Directions: up, down, left, right
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        // Min heap: [effort, row, col]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0, 0}); // start with 0 effort
        
        // Track minimum effort to reach each cell
        int[][] dist = new int[rows][cols];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int effort = curr[0];
            int row = curr[1];
            int col = curr[2];
            
            // If we reached destination, return effort
            if (row == rows - 1 && col == cols - 1) {
                return effort;
            }
            
            // If current effort is greater than recorded, skip
            if (effort > dist[row][col]) {
                continue;
            }
            
            // Explore neighbors
            for (int[] dir : dirs) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    // Calculate effort for this step
                    int newEffort = Math.max(effort, 
                        Math.abs(heights[newRow][newCol] - heights[row][col]));
                    
                    // If better path found, update and add to queue
                    if (newEffort < dist[newRow][newCol]) {
                        dist[newRow][newCol] = newEffort;
                        pq.offer(new int[]{newEffort, newRow, newCol});
                    }
                }
            }
        }
        
        return 0; // Should never reach here
    }
    
    /**
     * Approach 2: Binary Search + BFS
     * Time: O(log(MAX) × rows × cols), Space: O(rows × cols)
     * 
     * Binary search on the maximum allowed effort, check if path exists with BFS
     */
    public int minimumEffortPathBinarySearch(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;
        
        // Binary search bounds
        int left = 0;
        int right = 0;
        
        // Find maximum possible effort (max height - min height)
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int[] row : heights) {
            for (int h : row) {
                min = Math.min(min, h);
                max = Math.max(max, h);
            }
        }
        right = max - min;
        
        int result = right;
        
        // Binary search
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canReachWithEffort(heights, mid)) {
                result = mid;
                right = mid - 1; // Try smaller effort
            } else {
                left = mid + 1; // Need larger effort
            }
        }
        
        return result;
    }
    
    private boolean canReachWithEffort(int[][] heights, int maxEffort) {
        int rows = heights.length;
        int cols = heights[0].length;
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        boolean[][] visited = new boolean[rows][cols];
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int row = curr[0];
            int col = curr[1];
            
            if (row == rows - 1 && col == cols - 1) {
                return true;
            }
            
            for (int[] dir : dirs) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && 
                    !visited[newRow][newCol]) {
                    
                    int diff = Math.abs(heights[newRow][newCol] - heights[row][col]);
                    
                    if (diff <= maxEffort) {
                        visited[newRow][newCol] = true;
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
        }
        
        return false;
    }
    
    /**
     * Approach 3: Union-Find with Sorted Edges
     * Time: O(rows × cols × log(rows × cols)), Space: O(rows × cols)
     * 
     * Sort edges by effort, connect cells until start and end are connected
     */
    public int minimumEffortPathUnionFind(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;
        
        // Create list of all edges
        List<Edge> edges = new ArrayList<>();
        
        // Horizontal edges
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                int effort = Math.abs(heights[i][j] - heights[i][j + 1]);
                edges.add(new Edge(i * cols + j, i * cols + j + 1, effort));
            }
        }
        
        // Vertical edges
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols; j++) {
                int effort = Math.abs(heights[i][j] - heights[i + 1][j]);
                edges.add(new Edge(i * cols + j, (i + 1) * cols + j, effort));
            }
        }
        
        // Sort edges by effort
        Collections.sort(edges, (a, b) -> a.effort - b.effort);
        
        // Union-Find
        UnionFind uf = new UnionFind(rows * cols);
        
        int start = 0;
        int end = rows * cols - 1;
        
        // Process edges in ascending order
        for (Edge edge : edges) {
            uf.union(edge.u, edge.v);
            
            if (uf.find(start) == uf.find(end)) {
                return edge.effort;
            }
        }
        
        return 0;
    }
    
    class Edge {
        int u, v, effort;
        
        Edge(int u, int v, int effort) {
            this.u = u;
            this.v = v;
            this.effort = effort;
        }
    }
    
    class UnionFind {
        int[] parent;
        int[] rank;
        
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX == rootY) return;
            
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
    
    /**
     * Approach 4: BFS + DP (Dynamic Programming alternative)
     * Time: O(rows × cols × maxHeight), Space: O(rows × cols)
     * 
     * Use BFS with relaxation - can revisit cells if we find better effort
     */
    public int minimumEffortPathBFSDP(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int[][] dist = new int[rows][cols];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int row = curr[0];
            int col = curr[1];
            
            for (int[] dir : dirs) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    int newEffort = Math.max(dist[row][col], 
                        Math.abs(heights[newRow][newCol] - heights[row][col]));
                    
                    if (newEffort < dist[newRow][newCol]) {
                        dist[newRow][newCol] = newEffort;
                        queue.offer(new int[]{newRow, newCol}); // Re-explore
                    }
                }
            }
        }
        
        return dist[rows - 1][cols - 1];
    }
    
    /**
     * Approach 5: 0-1 BFS (if efforts were binary, not applicable here)
     * For demonstration - not optimal for this problem
     */
    public int minimumEffortPathZeroOne(int[][] heights) {
        // Not applicable as efforts can be > 1
        return minimumEffortPath(heights);
    }
    
    /**
     * Helper: Visualize the path with minimum effort
     */
    public void visualizePath(int[][] heights) {
        System.out.println("\nPath Visualization:");
        System.out.println("====================");
        
        int rows = heights.length;
        int cols = heights[0].length;
        
        System.out.println("Heights grid:");
        for (int[] row : heights) {
            System.out.println(Arrays.toString(row));
        }
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int[][] dist = new int[rows][cols];
        int[][] prev = new int[rows][cols];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        for (int[] row : prev) {
            Arrays.fill(row, -1);
        }
        dist[0][0] = 0;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0, 0}); // [effort, row, col]
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int effort = curr[0];
            int row = curr[1];
            int col = curr[2];
            
            if (effort > dist[row][col]) continue;
            
            for (int i = 0; i < dirs.length; i++) {
                int newRow = row + dirs[i][0];
                int newCol = col + dirs[i][1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    int newEffort = Math.max(effort, 
                        Math.abs(heights[newRow][newCol] - heights[row][col]));
                    
                    if (newEffort < dist[newRow][newCol]) {
                        dist[newRow][newCol] = newEffort;
                        prev[newRow][newCol] = row * cols + col; // Store previous cell
                        pq.offer(new int[]{newEffort, newRow, newCol});
                    }
                }
            }
        }
        
        // Reconstruct path
        List<int[]> path = new ArrayList<>();
        int r = rows - 1;
        int c = cols - 1;
        while (r != 0 || c != 0) {
            path.add(0, new int[]{r, c});
            int prevVal = prev[r][c];
            if (prevVal == -1) break;
            r = prevVal / cols;
            c = prevVal % cols;
        }
        path.add(0, new int[]{0, 0});
        
        System.out.println("\nMinimum effort: " + dist[rows - 1][cols - 1]);
        System.out.println("Path (row, col):");
        for (int i = 0; i < path.size(); i++) {
            int[] cell = path.get(i);
            System.out.printf("  [%d,%d]", cell[0], cell[1]);
            if (i < path.size() - 1) {
                int nextRow = path.get(i + 1)[0];
                int nextCol = path.get(i + 1)[1];
                int diff = Math.abs(heights[nextRow][nextCol] - heights[cell[0]][cell[1]]);
                System.out.printf(" -> (diff: %d)", diff);
            }
        }
        System.out.println();
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][][] generateTestCases() {
        return new int[][][] {
            // Example 1
            {
                {1, 2, 2},
                {3, 8, 2},
                {5, 3, 5}
            },
            // Example 2
            {
                {1, 2, 3},
                {3, 8, 4},
                {5, 3, 5}
            },
            // Example 3
            {
                {1, 2, 1, 1, 1},
                {1, 2, 1, 2, 1},
                {1, 2, 1, 2, 1},
                {1, 2, 1, 2, 1},
                {1, 1, 1, 2, 1}
            },
            // Single cell
            {{5}},
            // 1×N grid
            {{1, 2, 3, 4, 5}},
            // N×1 grid
            {{1}, {2}, {3}, {4}, {5}},
            // All same heights
            {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
            },
            // Large differences
            {
                {1, 1000000},
                {1000000, 1}
            }
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        int[][][] testCases = generateTestCases();
        int[] expected = {2, 1, 0, 0, 1, 1, 0, 999999};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[][] heights = testCases[i];
            System.out.printf("\nTest %d:%n", i + 1);
            System.out.println("Grid: " + Arrays.deepToString(heights));
            
            int result1 = minimumEffortPath(heights);
            int result2 = minimumEffortPathBinarySearch(heights);
            int result3 = minimumEffortPathUnionFind(heights);
            int result4 = minimumEffortPathBFSDP(heights);
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Minimum effort: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Dijkstra: " + result1);
                System.out.println("  Binary Search: " + result2);
                System.out.println("  Union-Find: " + result3);
                System.out.println("  BFS+DP: " + result4);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizePath(heights);
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
        System.out.println("=======================");
        
        // Create a larger test case
        int rows = 100;
        int cols = 100;
        int[][] heights = new int[rows][cols];
        Random rand = new Random(42);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                heights[i][j] = rand.nextInt(1000000) + 1;
            }
        }
        
        System.out.println("Test Setup: " + rows + "×" + cols + " grid");
        
        long[] times = new long[4];
        int[] results = new int[4];
        
        // Method 1: Dijkstra
        long start = System.currentTimeMillis();
        results[0] = minimumEffortPath(heights);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Binary Search + BFS
        start = System.currentTimeMillis();
        results[1] = minimumEffortPathBinarySearch(heights);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Union-Find
        start = System.currentTimeMillis();
        results[2] = minimumEffortPathUnionFind(heights);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: BFS + DP
        start = System.currentTimeMillis();
        results[3] = minimumEffortPathBFSDP(heights);
        times[3] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Dijkstra               | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Binary Search + BFS    | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. Union-Find             | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. BFS + DP               | %9d | %6d%n", times[3], results[3]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] && results[2] == results[3];
        System.out.println("\nAll results match: " + allMatch);
        
        System.out.println("\nObservations:");
        System.out.println("1. Dijkstra is fastest for typical cases");
        System.out.println("2. Binary Search is consistent but may need multiple BFS passes");
        System.out.println("3. Union-Find is efficient but requires edge creation");
        System.out.println("4. BFS+DP may need multiple passes but is simple to implement");
    }
    
    /**
     * Helper: Explain min-max path concept
     */
    public void explainMinMaxPath() {
        System.out.println("\nMin-Max Path Explanation:");
        System.out.println("==========================");
        
        System.out.println("\nWhat is a min-max path problem?");
        System.out.println("- Instead of minimizing the sum of edge weights (shortest path)");
        System.out.println("- We minimize the MAXIMUM edge weight along the path");
        System.out.println("- Goal: Find path where the worst step is as small as possible");
        
        System.out.println("\nWhy Dijkstra works with modification:");
        System.out.println("Standard Dijkstra: dist[v] = min(dist[v], dist[u] + weight(u,v))");
        System.out.println("Modified Dijkstra: dist[v] = min(dist[v], max(dist[u], weight(u,v)))");
        
        System.out.println("\nExample:");
        System.out.println("Path: [A] --3--> [B] --5--> [C]");
        System.out.println("Standard shortest path sum: 3 + 5 = 8");
        System.out.println("Min-max path max: max(3,5) = 5");
        
        System.out.println("\nKey properties:");
        System.out.println("1. The min-max value is monotonic - if a path exists with effort ≤ X,");
        System.out.println("   it also exists with effort ≤ Y for any Y ≥ X");
        System.out.println("2. This monotonicity enables binary search solution");
        System.out.println("3. The problem is essentially finding the threshold where start and end connect");
    }
    
    /**
     * Helper: Edge cases testing
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Single cell grid:");
        int[][] grid1 = {{5}};
        int result1 = minimumEffortPath(grid1);
        System.out.println("   Grid: [[5]]");
        System.out.println("   Result: " + result1 + " (expected: 0)");
        
        System.out.println("\n2. 1×N grid:");
        int[][] grid2 = {{1, 2, 3, 4, 5}};
        int result2 = minimumEffortPath(grid2);
        System.out.println("   Grid: " + Arrays.deepToString(grid2));
        System.out.println("   Result: " + result2 + " (expected: 1)");
        
        System.out.println("\n3. N×1 grid:");
        int[][] grid3 = {{1}, {2}, {3}, {4}, {5}};
        int result3 = minimumEffortPath(grid3);
        System.out.println("   Grid: " + Arrays.deepToString(grid3));
        System.out.println("   Result: " + result3 + " (expected: 1)");
        
        System.out.println("\n4. All same heights:");
        int[][] grid4 = {{1, 1}, {1, 1}};
        int result4 = minimumEffortPath(grid4);
        System.out.println("   Grid: " + Arrays.deepToString(grid4));
        System.out.println("   Result: " + result4 + " (expected: 0)");
        
        System.out.println("\n5. Large height difference:");
        int[][] grid5 = {{1, 1000000}, {1000000, 1}};
        int result5 = minimumEffortPath(grid5);
        System.out.println("   Grid: " + Arrays.deepToString(grid5));
        System.out.println("   Result: " + result5);
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify the problem:");
        System.out.println("   - What's being minimized? (max step, not total)");
        System.out.println("   - Movement allowed? (4-directional)");
        System.out.println("   - Cell revisit allowed? (yes, but path can't repeat cells logically)");
        
        System.out.println("\n2. Recognize the pattern:");
        System.out.println("   - Min-max path problem");
        System.out.println("   - Similar to 'Swim in Rising Water' (LeetCode 778)");
        
        System.out.println("\n3. Start with brute force:");
        System.out.println("   - Try all paths? Exponential, infeasible");
        System.out.println("   - Acknowledge need for optimized approach");
        
        System.out.println("\n4. Propose Dijkstra adaptation:");
        System.out.println("   - Explain modification to handle max instead of sum");
        System.out.println("   - Walk through example");
        
        System.out.println("\n5. Discuss binary search approach:");
        System.out.println("   - Monotonic property enables binary search");
        System.out.println("   - Check feasibility with BFS/DFS");
        System.out.println("   - Time complexity: O(log(MAX) × rows × cols)");
        
        System.out.println("\n6. Mention Union-Find alternative:");
        System.out.println("   - Sort edges by weight, connect until start and end connect");
        System.out.println("   - Good for understanding graph connectivity");
        
        System.out.println("\n7. Analyze complexity:");
        System.out.println("   - Dijkstra: O(rows × cols × log(rows × cols))");
        System.out.println("   - Binary Search: O(log(MAX) × rows × cols)");
        System.out.println("   - Union-Find: O(rows × cols × log(rows × cols))");
        
        System.out.println("\n8. Handle edge cases:");
        System.out.println("   - Single cell");
        System.out.println("   - Linear grids (1×N or N×1)");
        System.out.println("   - All equal heights");
        System.out.println("   - Large height differences");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("1631. Path With Minimum Effort");
        System.out.println("===============================");
        
        // Explain min-max path concept
        solution.explainMinMaxPath();
        
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
        
        System.out.println("\nRecommended Implementation (Dijkstra):");
        System.out.println("""
class Solution {
    public int minimumEffortPath(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;
        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0, 0});
        
        int[][] dist = new int[rows][cols];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[0][0] = 0;
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int effort = curr[0];
            int row = curr[1];
            int col = curr[2];
            
            if (row == rows-1 && col == cols-1) return effort;
            if (effort > dist[row][col]) continue;
            
            for (int[] dir : dirs) {
                int nr = row + dir[0];
                int nc = col + dir[1];
                
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    int newEffort = Math.max(effort, 
                        Math.abs(heights[nr][nc] - heights[row][col]));
                    
                    if (newEffort < dist[nr][nc]) {
                        dist[nr][nc] = newEffort;
                        pq.offer(new int[]{newEffort, nr, nc});
                    }
                }
            }
        }
        
        return 0;
    }
}
            """);
        
        System.out.println("\nAlternative Implementation (Binary Search + BFS):");
        System.out.println("""
class Solution {
    public int minimumEffortPath(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;
        
        int left = 0;
        int right = 0;
        for (int[] row : heights) {
            for (int h : row) {
                right = Math.max(right, h);
            }
        }
        right -= 1; // Adjust based on min
        
        int result = right;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canReach(heights, mid)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    private boolean canReach(int[][] heights, int maxEffort) {
        int rows = heights.length;
        int cols = heights[0].length;
        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            
            if (r == rows-1 && c == cols-1) return true;
            
            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];
                
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && !visited[nr][nc]) {
                    int diff = Math.abs(heights[nr][nc] - heights[r][c]);
                    if (diff <= maxEffort) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
        
        return false;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. This is a min-max path problem, not shortest path");
        System.out.println("2. Dijkstra adaptation with max() is optimal");
        System.out.println("3. Binary search + BFS is intuitive due to monotonic property");
        System.out.println("4. Union-Find with sorted edges also works well");
        System.out.println("5. Time complexity: O(rows × cols × log(rows × cols))");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you modify for diagonal movement?");
        System.out.println("2. How to find the actual path, not just effort?");
        System.out.println("3. What if you can revisit cells?");
        System.out.println("4. How to handle 3D grids?");
        System.out.println("5. How would you parallelize the solution?");
    }
}
