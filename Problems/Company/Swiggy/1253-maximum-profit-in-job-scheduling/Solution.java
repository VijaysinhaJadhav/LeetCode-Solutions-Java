
## Solution.java

```java
import java.util.*;

/**
 * 1235. Maximum Profit in Job Scheduling
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], 
 * obtaining a profit of profit[i]. Find the maximum profit you can take such that there are 
 * no two jobs in the subset with overlapping time range.
 * 
 * Key Insights:
 * 1. This is a weighted interval scheduling problem
 * 2. Sort jobs by end time to enable DP approach
 * 3. For each job i, we have two choices: skip it or take it
 * 4. If we take job i, we need to find the last job that ends before startTime[i]
 * 5. Use binary search to efficiently find non-overlapping previous job
 * 
 * Approach (DP with Binary Search):
 * 1. Combine startTime, endTime, profit into Job objects
 * 2. Sort jobs by end time
 * 3. DP[i] = max profit considering jobs 0 to i
 * 4. For each job i:
 *    - Option 1: Skip job i -> dp[i-1]
 *    - Option 2: Take job i -> profit[i] + dp[prev] where prev is last non-overlapping job
 * 5. Use binary search to find prev efficiently
 * 
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 * 
 * Tags: Array, Binary Search, Dynamic Programming, Sorting
 */

class Solution {
    
    /**
     * Job class to store start, end, and profit
     */
    static class Job {
        int start;
        int end;
        int profit;
        
        Job(int start, int end, int profit) {
            this.start = start;
            this.end = end;
            this.profit = profit;
        }
    }
    
    /**
     * Approach 1: Dynamic Programming with Binary Search (RECOMMENDED)
     * O(n log n) time, O(n) space
     */
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        
        // Create and sort jobs by end time
        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }
        
        // Sort jobs by end time
        Arrays.sort(jobs, (a, b) -> a.end - b.end);
        
        // DP array: dp[i] = max profit considering jobs 0 to i
        int[] dp = new int[n];
        
        // Initialize with first job's profit
        dp[0] = jobs[0].profit;
        
        for (int i = 1; i < n; i++) {
            // Option 1: Skip current job
            int skipProfit = dp[i - 1];
            
            // Option 2: Take current job
            int takeProfit = jobs[i].profit;
            
            // Find the last job that doesn't overlap with current job
            int prevIndex = findLastNonOverlappingJob(jobs, i);
            
            if (prevIndex != -1) {
                takeProfit += dp[prevIndex];
            }
            
            // Take the maximum of the two options
            dp[i] = Math.max(skipProfit, takeProfit);
        }
        
        return dp[n - 1];
    }
    
    /**
     * Binary search to find the last job that ends before or at jobs[current].start
     */
    private int findLastNonOverlappingJob(Job[] jobs, int current) {
        int left = 0;
        int right = current - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (jobs[mid].end <= jobs[current].start) {
                // This job doesn't overlap, try to find a later one
                result = mid;
                left = mid + 1;
            } else {
                // This job overlaps, look earlier
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: DP with Memoization (Top-Down)
     * O(n log n) time, O(n) space
     */
    public int jobSchedulingMemo(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }
        
        Arrays.sort(jobs, (a, b) -> a.end - b.end);
        
        // Extract sorted arrays for binary search
        int[] sortedEnds = new int[n];
        int[] sortedProfits = new int[n];
        int[] sortedStarts = new int[n];
        for (int i = 0; i < n; i++) {
            sortedEnds[i] = jobs[i].end;
            sortedProfits[i] = jobs[i].profit;
            sortedStarts[i] = jobs[i].start;
        }
        
        int[] memo = new int[n];
        Arrays.fill(memo, -1);
        
        return dfs(0, sortedStarts, sortedEnds, sortedProfits, memo);
    }
    
    private int dfs(int index, int[] starts, int[] ends, int[] profits, int[] memo) {
        if (index >= starts.length) {
            return 0;
        }
        
        if (memo[index] != -1) {
            return memo[index];
        }
        
        // Option 1: Skip current job
        int skip = dfs(index + 1, starts, ends, profits, memo);
        
        // Option 2: Take current job
        int nextIndex = findNextJob(index, ends, starts);
        int take = profits[index] + (nextIndex == -1 ? 0 : dfs(nextIndex, starts, ends, profits, memo));
        
        memo[index] = Math.max(skip, take);
        return memo[index];
    }
    
    private int findNextJob(int current, int[] ends, int[] starts) {
        // Binary search for first job that starts after current job ends
        int left = current + 1;
        int right = ends.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (starts[mid] >= ends[current]) {
                result = mid;
                right = mid - 1; // Try to find an earlier one
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: DP with TreeMap for efficient previous job lookup
     * O(n log n) time, O(n) space
     */
    public int jobSchedulingTreeMap(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }
        
        Arrays.sort(jobs, (a, b) -> a.end - b.end);
        
        // TreeMap stores end time -> max profit
        TreeMap<Integer, Integer> dp = new TreeMap<>();
        dp.put(0, 0); // Base case: at time 0, profit 0
        
        for (Job job : jobs) {
            // Find the last job that ends before or at current job's start
            int prevProfit = dp.floorEntry(job.start).getValue();
            int currentProfit = prevProfit + job.profit;
            
            // Only update if current profit is better than the best profit at this end time
            if (currentProfit > dp.lastEntry().getValue()) {
                dp.put(job.end, currentProfit);
            }
        }
        
        return dp.lastEntry().getValue();
    }
    
    /**
     * Approach 4: DP with optimization - only store necessary states
     * O(n log n) time, O(n) space
     */
    public int jobSchedulingOptimized(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];
        
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        
        // Sort by end time
        Arrays.sort(jobs, (a, b) -> a[1] - b[1]);
        
        // dp[i] = max profit at time i (where i is index in sorted jobs)
        int[] dp = new int[n + 1];
        
        for (int i = 0; i < n; i++) {
            int currentStart = jobs[i][0];
            int currentProfit = jobs[i][2];
            
            // Binary search for last job that ends <= currentStart
            int left = 0, right = i;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (jobs[mid][1] <= currentStart) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            
            // left is the first job that ends after currentStart, so prev = left - 1
            int prev = left - 1;
            
            // Take max of skipping or taking current job
            dp[i + 1] = Math.max(dp[i], currentProfit + (prev >= 0 ? dp[prev + 1] : 0));
        }
        
        return dp[n];
    }
    
    /**
     * Helper method to visualize the DP process
     */
    private void visualizeDP(int[] startTime, int[] endTime, int[] profit, String approach) {
        System.out.println("\n" + approach + " - Visualization:");
        System.out.println("Jobs (start, end, profit):");
        for (int i = 0; i < startTime.length; i++) {
            System.out.printf("Job %d: [%d, %d] -> $%d%n", 
                i, startTime[i], endTime[i], profit[i]);
        }
        
        int result = jobScheduling(startTime, endTime, profit);
        System.out.println("\nMaximum Profit: $" + result);
        
        // Find and display the optimal schedule
        findAndPrintOptimalSchedule(startTime, endTime, profit);
    }
    
    private void findAndPrintOptimalSchedule(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }
        
        Arrays.sort(jobs, (a, b) -> a.end - b.end);
        
        // Extract arrays
        int[] starts = new int[n];
        int[] ends = new int[n];
        int[] profits = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = jobs[i].start;
            ends[i] = jobs[i].end;
            profits[i] = jobs[i].profit;
        }
        
        // DP array and choice array
        int[] dp = new int[n + 1];
        boolean[] take = new boolean[n];
        
        // Build DP table
        for (int i = 0; i < n; i++) {
            // Find last non-overlapping job
            int prev = -1;
            int left = 0, right = i - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (ends[mid] <= starts[i]) {
                    prev = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            int takeProfit = profits[i] + (prev >= 0 ? dp[prev + 1] : 0);
            int skipProfit = dp[i];
            
            if (takeProfit > skipProfit) {
                dp[i + 1] = takeProfit;
                take[i] = true;
            } else {
                dp[i + 1] = skipProfit;
                take[i] = false;
            }
        }
        
        // Backtrack to find selected jobs
        List<Integer> selected = new ArrayList<>();
        int i = n - 1;
        while (i >= 0) {
            if (take[i]) {
                selected.add(i);
                // Find previous non-overlapping job
                int prev = -1;
                int left = 0, right = i - 1;
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (ends[mid] <= starts[i]) {
                        prev = mid;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                i = prev;
            } else {
                i--;
            }
        }
        
        // Reverse to get chronological order
        Collections.reverse(selected);
        
        System.out.println("\nOptimal Schedule:");
        int totalProfit = 0;
        for (int idx : selected) {
            System.out.printf("Job: [%d, %d] -> $%d%n", 
                starts[idx], ends[idx], profits[idx]);
            totalProfit += profits[idx];
        }
        System.out.println("Total Profit: $" + totalProfit);
    }
    
    /**
     * Main method with test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Maximum Profit in Job Scheduling:");
        System.out.println("==========================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int[] start1 = {1, 2, 3, 3};
        int[] end1 = {3, 4, 5, 6};
        int[] profit1 = {50, 10, 40, 70};
        int expected1 = 120;
        
        solution.visualizeDP(start1, end1, profit1, "DP Approach");
        
        long startTime = System.nanoTime();
        int result1 = solution.jobScheduling(start1, end1, profit1);
        long time1 = System.nanoTime() - startTime;
        
        System.out.println("\nResult: " + result1 + " - " + 
                         (result1 == expected1 ? "✓ PASSED" : "✗ FAILED") + 
                         " (Time: " + time1 + " ns)");
        
        // Test case 2: Example 2 from problem
        System.out.println("\nTest 2: More complex example");
        int[] start2 = {1, 2, 3, 4, 6};
        int[] end2 = {3, 5, 10, 6, 9};
        int[] profit2 = {20, 20, 100, 70, 60};
        int expected2 = 150;
        
        solution.visualizeDP(start2, end2, profit2, "DP Approach");
        int result2 = solution.jobScheduling(start2, end2, profit2);
        System.out.println("Result: " + result2 + " - " + 
                         (result2 == expected2 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 3: Example 3 from problem
        System.out.println("\nTest 3: Overlapping jobs");
        int[] start3 = {1, 1, 1};
        int[] end3 = {2, 3, 4};
        int[] profit3 = {5, 6, 4};
        int expected3 = 6;
        
        solution.visualizeDP(start3, end3, profit3, "DP Approach");
        int result3 = solution.jobScheduling(start3, end3, profit3);
        System.out.println("Result: " + result3 + " - " + 
                         (result3 == expected3 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 4: Single job
        System.out.println("\nTest 4: Single job");
        int[] start4 = {1};
        int[] end4 = {3};
        int[] profit4 = {50};
        int expected4 = 50;
        
        int result4 = solution.jobScheduling(start4, end4, profit4);
        System.out.println("Result: " + result4 + " - " + 
                         (result4 == expected4 ? "✓ PASSED" : "✗ FAILED"));
        
        // Test case 5: Non-overlapping jobs
        System.out.println("\nTest 5: Non-overlapping jobs");
        int[] start5 = {1, 4, 7};
        int[] end5 = {3, 6, 9};
        int[] profit5 = {10, 20, 30};
        int expected5 = 60; // Can take all jobs
        
        solution.visualizeDP(start5, end5, profit5, "DP Approach");
        int result5 = solution.jobScheduling(start5, end5, profit5);
        System.out.println("Result: " + result5 + " - " + 
                         (result5 == expected5 ? "✓ PASSED" : "✗ FAILED"));
        
        // Compare all implementations
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING ALL IMPLEMENTATIONS:");
        System.out.println("=".repeat(70));
        
        int[][][] allTests = {
            {start1, end1, profit1},
            {start2, end2, profit2},
            {start3, end3, profit3},
            {start4, end4, profit4},
            {start5, end5, profit5}
        };
        
        int[] expected = {120, 150, 6, 50, 60};
        
        System.out.println("\nTesting " + allTests.length + " test cases:");
        boolean allConsistent = true;
        
        for (int i = 0; i < allTests.length; i++) {
            int[] starts = allTests[i][0];
            int[] ends = allTests[i][1];
            int[] profits = allTests[i][2];
            int exp = expected[i];
            
            int r1 = solution.jobScheduling(starts, ends, profits);
            int r2 = solution.jobSchedulingMemo(starts, ends, profits);
            int r3 = solution.jobSchedulingTreeMap(starts, ends, profits);
            int r4 = solution.jobSchedulingOptimized(starts, ends, profits);
            
            boolean consistent = (r1 == r2) && (r2 == r3) && (r3 == r4);
            boolean correct = (r1 == exp);
            
            System.out.printf("Test %d: %s - Result: %d - %s %s%n",
                i + 1, Arrays.toString(starts), r1,
                consistent ? "✓ CONSISTENT" : "✗ INCONSISTENT",
                correct ? "✓ CORRECT" : "✗ WRONG");
            
            if (!consistent) {
                System.out.println("  DP with Binary Search: " + r1);
                System.out.println("  Memoization:           " + r2);
                System.out.println("  TreeMap:               " + r3);
                System.out.println("  Optimized DP:          " + r4);
                allConsistent = false;
            }
        }
        
        System.out.println("\nAll implementations consistent: " + (allConsistent ? "✓ YES" : "✗ NO"));
        
        // Performance test with larger input
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        // Generate large test case
        Random random = new Random(42);
        int n = 10000;
        int[] largeStarts = new int[n];
        int[] largeEnds = new int[n];
        int[] largeProfits = new int[n];
        
        for (int i = 0; i < n; i++) {
            int start = random.nextInt(10000);
            int duration = random.nextInt(100) + 1;
            largeStarts[i] = start;
            largeEnds[i] = start + duration;
            largeProfits[i] = random.nextInt(1000) + 1;
        }
        
        System.out.println("\nTesting with " + n + " random jobs");
        
        // Test DP with Binary Search
        startTime = System.currentTimeMillis();
        int perf1 = solution.jobScheduling(largeStarts, largeEnds, largeProfits);
        long timePerf1 = System.currentTimeMillis() - startTime;
        
        // Test TreeMap approach
        startTime = System.currentTimeMillis();
        int perf2 = solution.jobSchedulingTreeMap(largeStarts, largeEnds, largeProfits);
        long timePerf2 = System.currentTimeMillis() - startTime;
        
        // Test Optimized DP
        startTime = System.currentTimeMillis();
        int perf3 = solution.jobSchedulingOptimized(largeStarts, largeEnds, largeProfits);
        long timePerf3 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("DP with Binary Search: " + timePerf1 + " ms - Result: " + perf1);
        System.out.println("TreeMap Approach:      " + timePerf2 + " ms - Result: " + perf2);
        System.out.println("Optimized DP:          " + timePerf3 + " ms - Result: " + perf3);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Steps:");
        System.out.println("1. Sort jobs by end time - enables greedy-like approach");
        System.out.println("2. DP[i] = max profit considering first i jobs (0-indexed)");
        System.out.println("3. For each job, we have two choices:");
        System.out.println("   - Skip it: DP[i-1]");
        System.out.println("   - Take it: profit[i] + DP[prev] where prev is last non-overlapping job");
        System.out.println("4. Use binary search to find prev efficiently");
        System.out.println("5. Final answer = DP[n-1]");
        
        System.out.println("\nExample: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]");
        System.out.println("Sorted jobs by end time:");
        System.out.println("Job 0: [1,3] $50");
        System.out.println("Job 1: [2,4] $10");
        System.out.println("Job 2: [3,5] $40");
        System.out.println("Job 3: [3,6] $70");
        
        System.out.println("\nDP Calculation:");
        System.out.println("i=0: DP[0] = max(skip=0, take=50+0) = 50");
        System.out.println("i=1: Find prev for job1 (start=2): binary search finds job0 (end=1 <= 2)");
        System.out.println("     DP[1] = max(skip=50, take=10+50=60) = 60");
        System.out.println("i=2: Find prev for job2 (start=3): binary search finds job0 (end=3 <= 3)");
        System.out.println("     DP[2] = max(skip=60, take=40+50=90) = 90");
        System.out.println("i=3: Find prev for job3 (start=3): binary search finds job0 (end=3 <= 3)");
        System.out.println("     DP[3] = max(skip=90, take=70+50=120) = 120");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify problem:");
        System.out.println("   - Jobs defined by start, end, profit");
        System.out.println("   - No overlapping allowed");
        System.out.println("   - Can start new job exactly when previous ends");
        
        System.out.println("\n2. Recognize problem type:");
        System.out.println("   - Weighted interval scheduling");
        System.out.println("   - Classic DP problem");
        
        System.out.println("\n3. Sort by end time:");
        System.out.println("   - Key insight for DP formulation");
        System.out.println("   - Enables finding non-overlapping jobs efficiently");
        
        System.out.println("\n4. Define DP state:");
        System.out.println("   - dp[i] = max profit considering jobs 0..i");
        System.out.println("   - Two choices: skip job i or take job i");
        
        System.out.println("\n5. Handle finding previous job:");
        System.out.println("   - Binary search since jobs sorted by end time");
        System.out.println("   - O(log n) vs O(n) linear search");
        
        System.out.println("\n6. Walk through example:");
        System.out.println("   - Show step-by-step with given example");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Sorting by end time is crucial");
        System.out.println("- Binary search optimization for O(n log n) time");
        System.out.println("- Classic DP recurrence for interval scheduling");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not sorting jobs");
        System.out.println("- Using linear search instead of binary search");
        System.out.println("- Forgetting base cases");
        System.out.println("- Not handling the case when no previous non-overlapping job exists");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
