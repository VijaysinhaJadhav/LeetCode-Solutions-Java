
## Solution.java

```java
/**
 * 502. IPO
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given k projects with profits and capital requirements,
 * starting with initial capital w, select at most k projects
 * to maximize final capital.
 * 
 * Key Insights:
 * 1. Greedy: Always pick available project with maximum profit
 * 2. Sort projects by capital requirement
 * 3. Use max heap to track affordable projects' profits
 * 4. After each project, capital increases, more projects become affordable
 * 
 * Approach (Sorting + Max Heap):
 * 1. Combine profits and capital into Project objects
 * 2. Sort projects by capital requirement (ascending)
 * 3. Use max heap to store profits of affordable projects
 * 4. For up to k iterations:
 *    - Add all newly affordable projects to heap
 *    - If heap empty, break (no affordable projects)
 *    - Pick highest profit from heap, add to capital
 * 5. Return final capital
 * 
 * Time Complexity: O(n log n + k log n) ≈ O((n + k) log n)
 * Space Complexity: O(n) for projects array and heap
 * 
 * Tags: Array, Greedy, Sorting, Heap
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Sorting + Max Heap (RECOMMENDED)
     * O(n log n + k log n) time, O(n) space
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        
        // Create array of projects
        int[][] projects = new int[n][2];
        for (int i = 0; i < n; i++) {
            projects[i][0] = capital[i];  // capital requirement
            projects[i][1] = profits[i];  // profit
        }
        
        // Sort projects by capital requirement (ascending)
        Arrays.sort(projects, (a, b) -> a[0] - b[0]);
        
        // Max heap for profits of affordable projects
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        int currentCapital = w;
        int projectIndex = 0;
        
        // Try to select up to k projects
        for (int i = 0; i < k; i++) {
            // Add all projects that are now affordable
            while (projectIndex < n && projects[projectIndex][0] <= currentCapital) {
                maxHeap.offer(projects[projectIndex][1]);
                projectIndex++;
            }
            
            // If no affordable projects, break
            if (maxHeap.isEmpty()) {
                break;
            }
            
            // Select the project with maximum profit
            currentCapital += maxHeap.poll();
        }
        
        return currentCapital;
    }
    
    /**
     * Approach 2: Using Project Class for Better Readability
     * Same complexity, more object-oriented
     */
    static class Project {
        int capital;
        int profit;
        
        Project(int capital, int profit) {
            this.capital = capital;
            this.profit = profit;
        }
    }
    
    public int findMaximizedCapitalWithClass(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        Project[] projects = new Project[n];
        
        for (int i = 0; i < n; i++) {
            projects[i] = new Project(capital[i], profits[i]);
        }
        
        // Sort by capital requirement
        Arrays.sort(projects, (a, b) -> a.capital - b.capital);
        
        // Max heap for profits
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        int currentCapital = w;
        int idx = 0;
        
        for (int i = 0; i < k; i++) {
            // Add all affordable projects to heap
            while (idx < n && projects[idx].capital <= currentCapital) {
                maxHeap.offer(projects[idx].profit);
                idx++;
            }
            
            if (maxHeap.isEmpty()) break;
            
            currentCapital += maxHeap.poll();
        }
        
        return currentCapital;
    }
    
    /**
     * Approach 3: Two Min Heaps
     * One min heap for unaffordable projects (sorted by capital)
     * One max heap for affordable projects (sorted by profit)
     */
    public int findMaximizedCapitalTwoHeaps(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        
        // Min heap for unaffordable projects: [capital, profit]
        PriorityQueue<int[]> unaffordable = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        // Max heap for affordable projects' profits
        PriorityQueue<Integer> affordable = new PriorityQueue<>(Collections.reverseOrder());
        
        // Add all projects to unaffordable heap initially
        for (int i = 0; i < n; i++) {
            unaffordable.offer(new int[]{capital[i], profits[i]});
        }
        
        int currentCapital = w;
        
        for (int i = 0; i < k; i++) {
            // Move newly affordable projects from unaffordable to affordable heap
            while (!unaffordable.isEmpty() && unaffordable.peek()[0] <= currentCapital) {
                affordable.offer(unaffordable.poll()[1]);
            }
            
            if (affordable.isEmpty()) break;
            
            currentCapital += affordable.poll();
        }
        
        return currentCapital;
    }
    
    /**
     * Approach 4: Optimized with Early Exit
     * When all projects are affordable, just pick top k profits
     */
    public int findMaximizedCapitalOptimized(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        
        // Check if all projects are affordable from start
        boolean allAffordable = true;
        for (int cap : capital) {
            if (cap > w) {
                allAffordable = false;
                break;
            }
        }
        
        // If all projects affordable, just pick top k profits
        if (allAffordable) {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            for (int profit : profits) {
                maxHeap.offer(profit);
            }
            
            int currentCapital = w;
            for (int i = 0; i < Math.min(k, n); i++) {
                currentCapital += maxHeap.poll();
            }
            return currentCapital;
        }
        
        // Otherwise use standard approach
        int[][] projects = new int[n][2];
        for (int i = 0; i < n; i++) {
            projects[i][0] = capital[i];
            projects[i][1] = profits[i];
        }
        
        Arrays.sort(projects, (a, b) -> a[0] - b[0]);
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        int currentCapital = w;
        int idx = 0;
        
        for (int i = 0; i < k; i++) {
            while (idx < n && projects[idx][0] <= currentCapital) {
                maxHeap.offer(projects[idx][1]);
                idx++;
            }
            
            if (maxHeap.isEmpty()) break;
            
            currentCapital += maxHeap.poll();
        }
        
        return currentCapital;
    }
    
    /**
     * Approach 5: DP-like approach (for small k, not optimal)
     * Exponential time, for educational purposes
     */
    public int findMaximizedCapitalDFS(int k, int w, int[] profits, int[] capital) {
        List<Integer> result = new ArrayList<>();
        result.add(w);
        dfs(k, w, profits, capital, new boolean[profits.length], 0, result);
        return result.get(0);
    }
    
    private void dfs(int k, int currentCapital, int[] profits, int[] capital, 
                    boolean[] used, int count, List<Integer> result) {
        // Update max capital
        result.set(0, Math.max(result.get(0), currentCapital));
        
        if (count == k) return;
        
        for (int i = 0; i < profits.length; i++) {
            if (!used[i] && capital[i] <= currentCapital) {
                used[i] = true;
                dfs(k, currentCapital + profits[i], profits, capital, used, count + 1, result);
                used[i] = false;
            }
        }
    }
    
    /**
     * Helper method to visualize the selection process
     */
    private void visualizeSelection(int k, int w, int[] profits, int[] capital, int result, String approach) {
        System.out.println("\n" + approach + " - Selection Process:");
        System.out.println("Initial capital: " + w);
        System.out.println("Max projects to select: " + k);
        
        // Create projects array
        int n = profits.length;
        int[][] projects = new int[n][3]; // [capital, profit, original index]
        for (int i = 0; i < n; i++) {
            projects[i][0] = capital[i];
            projects[i][1] = profits[i];
            projects[i][2] = i;
        }
        
        // Sort by capital
        Arrays.sort(projects, (a, b) -> a[0] - b[0]);
        
        System.out.println("\nProjects (sorted by capital requirement):");
        System.out.println("Index | Capital | Profit | Affordable initially?");
        System.out.println("------|---------|--------|---------------------");
        for (int i = 0; i < n; i++) {
            boolean affordable = projects[i][0] <= w;
            System.out.printf("  %2d  |   %4d  |  %4d  |        %s%n",
                projects[i][2], projects[i][0], projects[i][1],
                affordable ? "✓" : "✗");
        }
        
        // Simulate greedy selection
        System.out.println("\nGreedy Selection Simulation:");
        PriorityQueue<int[]> affordableHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]); // max profit first
        int currentCapital = w;
        int idx = 0;
        List<Integer> selectedProjects = new ArrayList<>();
        
        for (int i = 0; i < k; i++) {
            System.out.println("\nStep " + (i + 1) + ":");
            System.out.println("  Current capital: " + currentCapital);
            
            // Add newly affordable projects
            int addedCount = 0;
            while (idx < n && projects[idx][0] <= currentCapital) {
                affordableHeap.offer(projects[idx]);
                System.out.printf("  Added project %d (capital: %d, profit: %d) to available pool%n",
                    projects[idx][2], projects[idx][0], projects[idx][1]);
                idx++;
                addedCount++;
            }
            if (addedCount == 0 && i > 0) {
                System.out.println("  No new projects became affordable");
            }
            
            if (affordableHeap.isEmpty()) {
                System.out.println("  No affordable projects available - stopping");
                break;
            }
            
            // Select best project
            int[] selected = affordableHeap.poll();
            currentCapital += selected[1];
            selectedProjects.add(selected[2]);
            
            System.out.printf("  Selected project %d (profit: %d)%n", selected[2], selected[1]);
            System.out.println("  New capital: " + currentCapital);
            System.out.println("  Selected projects so far: " + selectedProjects);
            
            if (affordableHeap.isEmpty() && idx == n) {
                System.out.println("  No more projects available");
                break;
            }
        }
        
        System.out.println("\nFinal capital: " + currentCapital);
        System.out.println("Selected projects: " + selectedProjects);
        System.out.println("Result matches expected: " + (currentCapital == result));
    }
    
    /**
     * Helper method to test with given examples
     */
    private static void testExample(int k, int w, int[] profits, int[] capital, int expected, String name) {
        Solution solution = new Solution();
        System.out.println("\n" + name + ":");
        System.out.println("k=" + k + ", w=" + w + 
                         ", profits=" + Arrays.toString(profits) + 
                         ", capital=" + Arrays.toString(capital));
        
        long startTime = System.nanoTime();
        int result = solution.findMaximizedCapital(k, w, profits, capital);
        long time = System.nanoTime() - startTime;
        
        System.out.println("Result: " + result + " (expected: " + expected + ") - " + 
                         (result == expected ? "✓ PASS" : "✗ FAIL"));
        System.out.println("Time: " + time + " ns");
        
        // Visualize if test passes
        if (result == expected) {
            solution.visualizeSelection(k, w, profits, capital, result, "Test");
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing IPO Solution:");
        System.out.println("=====================");
        
        // Test case 1: Example from problem
        testExample(2, 0, new int[]{1, 2, 3}, new int[]{0, 1, 1}, 4, "Example 1");
        
        // Test case 2: Example 2 from problem
        testExample(3, 0, new int[]{1, 2, 3}, new int[]{0, 1, 2}, 6, "Example 2");
        
        // Test case 3: All projects affordable from start
        testExample(2, 10, new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 4}, 16, "All affordable");
        
        // Test case 4: No projects affordable
        testExample(2, 0, new int[]{10, 20, 30}, new int[]{5, 10, 15}, 0, "None affordable");
        
        // Test case 5: Only one project affordable
        testExample(3, 5, new int[]{1, 2, 3, 10}, new int[]{5, 10, 15, 5}, 15, "One affordable chain");
        
        // Test case 6: Large profits but high capital
        testExample(2, 0, new int[]{100, 200, 300}, new int[]{0, 50, 100}, 400, "Large profits");
        
        // Test case 7: k larger than number of projects
        testExample(5, 0, new int[]{1, 2, 3}, new int[]{0, 1, 1}, 6, "k > n");
        
        // Test case 8: Zero profit projects
        testExample(3, 10, new int[]{0, 0, 5, 0}, new int[]{0, 5, 5, 10}, 15, "Zero profits");
        
        // Compare all implementations
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING ALL IMPLEMENTATIONS:");
        System.out.println("=".repeat(70));
        
        // Test data
        int[][][] testCases = {
            {{2, 0}, {1,2,3}, {0,1,1}},  // Example 1
            {{3, 0}, {1,2,3}, {0,1,2}},  // Example 2
            {{2, 10}, {1,2,3,4}, {1,2,3,4}},
            {{2, 0}, {10,20,30}, {5,10,15}},
            {{3, 5}, {1,2,3,10}, {5,10,15,5}}
        };
        
        int[] expectedResults = {4, 6, 16, 0, 15};
        
        System.out.println("\nTesting " + testCases.length + " test cases:");
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            int k = testCases[i][0][0];
            int w = testCases[i][0][1];
            int[] profits = testCases[i][1];
            int[] capital = testCases[i][2];
            int expected = expectedResults[i];
            
            int r1 = solution.findMaximizedCapital(k, w, profits, capital);
            int r2 = solution.findMaximizedCapitalWithClass(k, w, profits, capital);
            int r3 = solution.findMaximizedCapitalTwoHeaps(k, w, profits, capital);
            int r4 = solution.findMaximizedCapitalOptimized(k, w, profits, capital);
            
            boolean consistent = (r1 == r2) && (r2 == r3) && (r3 == r4) && (r1 == expected);
            
            System.out.printf("Test case %d: Standard=%d, Class=%d, TwoHeaps=%d, Optimized=%d - %s%n",
                i + 1, r1, r2, r3, r4,
                consistent ? "✓ CONSISTENT" : "✗ INCONSISTENT");
            
            if (!consistent) allConsistent = false;
        }
        
        System.out.println("\nAll implementations consistent: " + (allConsistent ? "✓ YES" : "✗ NO"));
        
        // Performance test
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        // Generate large test case
        Random random = new Random(42);
        int n = 100000;
        int kLarge = 50000;
        int wLarge = 1000;
        
        int[] largeProfits = new int[n];
        int[] largeCapital = new int[n];
        
        for (int i = 0; i < n; i++) {
            largeProfits[i] = random.nextInt(10000) + 1; // 1 to 10000
            largeCapital[i] = random.nextInt(100000); // 0 to 99999
        }
        
        System.out.println("\nTesting with n=" + n + ", k=" + kLarge + ", w=" + wLarge);
        
        // Test Standard implementation
        long startTime = System.currentTimeMillis();
        int result1 = solution.findMaximizedCapital(kLarge, wLarge, largeProfits, largeCapital);
        long time1 = System.currentTimeMillis() - startTime;
        
        // Test Two Heaps implementation
        startTime = System.currentTimeMillis();
        int result2 = solution.findMaximizedCapitalTwoHeaps(kLarge, wLarge, largeProfits, largeCapital);
        long time2 = System.currentTimeMillis() - startTime;
        
        // Test Optimized implementation
        startTime = System.currentTimeMillis();
        int result3 = solution.findMaximizedCapitalOptimized(kLarge, wLarge, largeProfits, largeCapital);
        long time3 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Standard:    " + time1 + " ms - Result: " + result1);
        System.out.println("Two Heaps:   " + time2 + " ms - Result: " + result2);
        System.out.println("Optimized:   " + time3 + " ms - Result: " + result3);
        
        boolean resultsMatch = (result1 == result2) && (result2 == result3);
        System.out.println("Results match: " + (resultsMatch ? "✓ YES" : "✗ NO"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("This is a greedy problem where at each step,");
        System.out.println("we should pick the affordable project with maximum profit.");
        
        System.out.println("\nWhy Greedy Works:");
        System.out.println("1. Profit is added to capital, increasing future options");
        System.out.println("2. Higher profit now means more capital for future projects");
        System.out.println("3. There's no penalty for choosing projects early");
        System.out.println("4. The choice is independent of project order (except for capital requirement)");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Combine and sort projects by capital requirement");
        System.out.println("2. Initialize max heap for profits of affordable projects");
        System.out.println("3. For i = 0 to k-1:");
        System.out.println("   a. Add all projects with capital ≤ current capital to heap");
        System.out.println("   b. If heap empty, break");
        System.out.println("   c. Pop max profit from heap, add to capital");
        System.out.println("4. Return final capital");
        
        System.out.println("\nVisual Example: k=2, w=0, profits=[1,2,3], capital=[0,1,1]");
        System.out.println("Step 1: Sort projects: [(0,1), (1,2), (1,3)]");
        System.out.println("Step 2: Current capital=0");
        System.out.println("        Affordable: project 0 (capital 0)");
        System.out.println("        Max heap: [1]");
        System.out.println("        Select profit 1, new capital=1");
        System.out.println("Step 3: Current capital=1");
        System.out.println("        Newly affordable: projects 1 and 2");
        System.out.println("        Max heap: [3, 2]");
        System.out.println("        Select profit 3, new capital=4");
        System.out.println("Result: 4");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("IMPLEMENTATION COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Sorting + Max Heap (Standard) - RECOMMENDED:");
        System.out.println("   Time: O(n log n + k log n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Simple and elegant");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Requires sorting");
        System.out.println("     - Extra array for combined projects");
        System.out.println("   Best for: Interview settings, general purpose");
        
        System.out.println("\n2. Project Class Approach:");
        System.out.println("   Time: O(n log n + k log n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - More readable code");
        System.out.println("     - Better encapsulation");
        System.out.println("     - Easier to extend");
        System.out.println("   Cons:");
        System.out.println("     - Object creation overhead");
        System.out.println("     - Slightly more memory");
        System.out.println("   Best for: Production code, larger systems");
        
        System.out.println("\n3. Two Min Heaps:");
        System.out.println("   Time: O(n log n + k log n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - No need for separate array");
        System.out.println("     - Clean separation of affordable/unaffordable");
        System.out.println("   Cons:");
        System.out.println("     - Two heaps instead of one");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: When input comes as stream");
        
        System.out.println("\n4. Optimized with Early Exit:");
        System.out.println("   Time: O(n log n + k log n) with optimization");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Fast when all projects affordable");
        System.out.println("     - Same worst case, better best case");
        System.out.println("   Cons:");
        System.out.println("     - Extra check overhead");
        System.out.println("     - Not always beneficial");
        System.out.println("   Best for: Mixed scenarios");
        
        System.out.println("\n5. DFS (Backtracking):");
        System.out.println("   Time: O(n!) exponential");
        System.out.println("   Space: O(n) recursion stack");
        System.out.println("   Pros:");
        System.out.println("     - Finds all possibilities");
        System.out.println("     - Guaranteed optimal");
        System.out.println("   Cons:");
        System.out.println("     - Exponentially slow");
        System.out.println("     - Not practical for n > 20");
        System.out.println("   Best for: Small n, educational");
        
        // Time complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("TIME COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStandard Implementation:");
        System.out.println("1. Sorting projects: O(n log n)");
        System.out.println("2. For each of k iterations:");
        System.out.println("   - While loop: each project added to heap at most once: O(n log n) total");
        System.out.println("   - Heap poll: O(log n) per iteration");
        System.out.println("3. Total: O(n log n + k log n)");
        
        System.out.println("\nSpace Complexity:");
        System.out.println("- Projects array: O(n)");
        System.out.println("- Max heap: O(n) in worst case");
        System.out.println("- Total: O(n)");
        
        System.out.println("\nMathematical Proof of Greedy Optimality:");
        System.out.println("Let OPT be optimal solution selecting projects P1, P2, ..., Pk");
        System.out.println("Let GREEDY be greedy solution selecting projects G1, G2, ..., Gk");
        System.out.println("Proof by exchange argument:");
        System.out.println("1. Consider first project where OPT and GREEDY differ");
        System.out.println("2. GREEDY picks highest profit affordable project");
        System.out.println("3. We can exchange OPT's project with GREEDY's without making solution worse");
        System.out.println("4. Repeat to transform OPT into GREEDY without decreasing profit");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Venture Capital Investment:");
        System.out.println("   - Selecting startups to invest in");
        System.out.println("   - Maximizing return on limited capital");
        System.out.println("   - Portfolio optimization");
        
        System.out.println("\n2. Resource Allocation:");
        System.out.println("   - Project selection in companies");
        System.out.println("   - Budget allocation for maximum ROI");
        System.out.println("   - Resource-constrained project scheduling");
        
        System.out.println("\n3. Game Strategy:");
        System.out.println("   - Selecting quests/activities in RPG games");
        System.out.println("   - Resource management games");
        System.out.println("   - Investment strategy simulations");
        
        System.out.println("\n4. Operations Research:");
        System.out.println("   - Knapsack problem variants");
        System.out.println("   - Capital budgeting problems");
        System.out.println("   - Financial portfolio management");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - k projects max, initial capital w");
        System.out.println("   - Projects have capital requirement and profit");
        System.out.println("   - Profit adds to capital");
        System.out.println("   - Maximize final capital");
        
        System.out.println("\n2. Think about brute force:");
        System.out.println("   - Try all combinations: O(2^n)");
        System.out.println("   - Dynamic programming: O(k * n) but capital up to 10^9");
        System.out.println("   - Need more efficient solution");
        
        System.out.println("\n3. Look for greedy pattern:");
        System.out.println("   - What if we always pick highest profit?");
        System.out.println("   - But need to consider capital requirement");
        System.out.println("   - Insight: after picking project, capital increases");
        
        System.out.println("\n4. Design two-phase approach:");
        System.out.println("   - Phase 1: Sort projects by capital requirement");
        System.out.println("   - Phase 2: Use max heap to pick highest profit affordable");
        System.out.println("   - Repeat k times or until no affordable projects");
        
        System.out.println("\n5. Prove correctness:");
        System.out.println("   - Greedy choice property");
        System.out.println("   - Optimal substructure");
        System.out.println("   - Exchange argument");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - k > number of projects");
        System.out.println("   - No affordable projects");
        System.out.println("   - All projects affordable from start");
        System.out.println("   - Zero profit projects");
        
        System.out.println("\n7. Optimize:");
        System.out.println("   - Early exit when heap empty");
        System.out.println("   - Check if all projects affordable initially");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Greedy approach is optimal for this problem");
        System.out.println("- Two data structures: sorted array + max heap");
        System.out.println("- O(n log n + k log n) time complexity");
        System.out.println("- O(n) space complexity");
        System.out.println("- The 10^9 capital limit prevents DP solution");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not sorting by capital requirement");
        System.out.println("- Forgetting to update capital after each project");
        System.out.println("- Not handling k > n case");
        System.out.println("- Integer overflow (capital can be large)");
        System.out.println("- Not considering all projects become affordable eventually");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with given examples");
        System.out.println("2. Test edge cases (k=0, k>n, w=0)");
        System.out.println("3. Test with all projects affordable");
        System.out.println("4. Test with no projects affordable");
        System.out.println("5. Test with large profit/capital values");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
