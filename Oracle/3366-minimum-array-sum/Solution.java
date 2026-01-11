---

# Solution.java

```java
import java.util.Arrays;

/**
 * 3366. Minimum Array Sum
 * * Difficulty: Hard
 * * Strategy: Top-down Dynamic Programming with Memoization.
 * We explore all 4 possibilities for each number: None, Op1, Op2, or Both.
 */
class Solution {
    private int[][][] memo;
    private int[] nums;
    private int k;

    public int minArraySum(int[] nums, int k, int op1, int op2) {
        this.nums = nums;
        this.k = k;
        int n = nums.length;
        // memo[index][remaining_op1][remaining_op2]
        this.memo = new int[n][op1 + 1][op2 + 1];
        
        for (int[][] layer : memo) {
            for (int[] row : layer) {
                Arrays.fill(row, -1);
            }
        }

        return solve(0, op1, op2);
    }

    private int solve(int idx, int o1, int o2) {
        if (idx == nums.length) {
            return 0;
        }
        if (memo[idx][o1][o2] != -1) {
            return memo[idx][o1][o2];
        }

        int val = nums[idx];
        
        // Option 1: Do nothing to this number
        int res = val + solve(idx + 1, o1, o2);

        // Option 2: Apply Op 1 only (Divide by 2)
        if (o1 > 0) {
            int newVal = (val + 1) / 2;
            res = Math.min(res, newVal + solve(idx + 1, o1 - 1, o2));
        }

        // Option 3: Apply Op 2 only (Subtract k)
        if (o2 > 0 && val >= k) {
            int newVal = val - k;
            res = Math.min(res, newVal + solve(idx + 1, o1, o2 - 1));
        }

        // Option 4: Apply both Op 1 and Op 2
        if (o1 > 0 && o2 > 0) {
            // Case A: Op 1 then Op 2
            int op1ThenOp2 = (val + 1) / 2;
            if (op1ThenOp2 >= k) {
                res = Math.min(res, (op1ThenOp2 - k) + solve(idx + 1, o1 - 1, o2 - 1));
            }

            // Case B: Op 2 then Op 1
            if (val >= k) {
                int op2ThenOp1 = (val - k + 1) / 2;
                res = Math.min(res, op2ThenOp1 + solve(idx + 1, o1 - 1, o2 - 1));
            }
        }

        return memo[idx][o1][o2] = res;
    }
}
