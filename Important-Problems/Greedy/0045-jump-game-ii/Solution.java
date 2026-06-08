
## Solution.java

```java
/**
 * 45. Jump Game II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find minimum number of jumps to reach the last index.
 * 
 * Key Insights:
 * 1. Greedy BFS: maintain current end and farthest reachable.
 * 2. Each time we reach current end, we must take a new jump.
 * 3. O(n) time, O(1) space.
 */
class Solution {
    
    /**
     * Approach 1: Greedy BFS (Recommended)
     * Time: O(n), Space: O(1)
     */
    public int jump(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;
        
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;
        
        for (int i = 0; i < n - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
                if (currentEnd >= n - 1) break;
            }
        }
        
        return jumps;
    }
    
    /**
     * Approach 2: DP (O(n²) – for understanding)
     * Time: O(n²), Space: O(n)
     */
    public int jumpDP(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 1; i < n; i++) dp[i] = Integer.MAX_VALUE;
        
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= nums[i] && i + j < n; j++) {
                dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
            }
        }
        return dp[n - 1];
    }
    
    /**
     * Approach 3: BFS (explicit level order)
     * Time: O(n), Space: O(n) for queue in worst case
     */
    public int jumpBFS(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;
        
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        int jumps = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int idx = queue.poll();
                if (idx == n - 1) return jumps;
                int maxStep = nums[idx];
                for (int step = 1; step <= maxStep && idx + step < n; step++) {
                    if (!visited[idx + step]) {
                        visited[idx + step] = true;
                        queue.offer(idx + step);
                    }
                }
            }
            jumps++;
        }
        return -1;
    }
    
    /**
     * Helper: Visualize the greedy process
     */
    public void visualizeJumps(int[] nums) {
        System.out.println("\nJump Game II Visualization:");
        System.out.println("=".repeat(60));
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        
        int n = nums.length;
        if (n == 1) {
            System.out.println("Already at last index → 0 jumps");
            return;
        }
        
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;
        
        System.out.println("\nGreedy process:");
        for (int i = 0; i < n - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            System.out.printf("Index %d (value %d): farthest = %d%n", i, nums[i], farthest);
            
            if (i == currentEnd) {
                jumps++;
                System.out.printf("  → Jump %d: reach up to index %d%n", jumps, farthest);
                currentEnd = farthest;
                if (currentEnd >= n - 1) {
                    System.out.println("  → Reached end! Total jumps = " + jumps);
                    break;
                }
            }
        }
    }
    
    /**
     * Helper: Run test cases
     */
    public void runTestCases() {
        System.out.println("\nRunning Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][] tests = {
            {2,3,1,1,4},
            {2,3,0,1,4},
            {1,2,3},
            {1,1,1,1},
            {0},
            {10,9,8,7,6,5,4,3,2,1,0}
        };
        int[] expected = {2,2,2,3,0,1};
        
        for (int i = 0; i < tests.length; i++) {
            int res = jump(tests[i]);
            System.out.printf("Test %d: %s → %d (expected %d) %s%n",
                i+1, java.util.Arrays.toString(tests[i]), res, expected[i], res == expected[i] ? "✓" : "✗");
        }
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("45. Jump Game II");
        System.out.println("================");
        
        solution.visualizeJumps(new int[]{2,3,1,1,4});
        solution.runTestCases();
    }
}
