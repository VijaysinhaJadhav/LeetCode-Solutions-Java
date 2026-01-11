# 3366. Minimum Array Sum

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Dynamic Programming  
**Companies:** Recent Online Assessments  

[LeetCode Link](https://leetcode.com/problems/minimum-array-sum/)

You are given an integer array `nums` and three integers `k`, `op1`, and `op2`.

You can perform the following operations on `nums`:

1. **Operation 1:** Choose an index `i` and replace `nums[i]` with `ceil(nums[i] / 2)`. You can perform this operation at most `op1` times in total, and at most **once** per index.
2. **Operation 2:** Choose an index `i` and replace `nums[i]` with `nums[i] - k` if `nums[i] >= k`. You can perform this operation at most `op2` times in total, and at most **once** per index.

**Note:** Both operations can be applied to the same index, but each operation can be applied at most once per index. If both operations are applied, they can be applied in **any order**.

Return the **minimum possible sum** of all elements in `nums` after performing at most `op1` and `op2` operations.

**Example 1:**
Input: nums = [2,8,3], k = 2, op1 = 1, op2 = 1
Output: 6
Explanation:
- Apply Op 1 to 8: `ceil(8/2) = 4`. Array becomes [2, 4, 3].
- Apply Op 2 to 3: `3 - 2 = 1`. Array becomes [2, 4, 1].
- Total sum: 2 + 4 + 1 = 7.
- *Better strategy:* Apply Op 1 to 8 (8 -> 4) and Op 2 to 4 (4 -> 2). Sum: 2 + 2 + 3 = 7.
- *Optimal strategy:* Apply Op 2 to 8 (8 -> 6) and Op 1 to 6 (6 -> 3). Sum: 2 + 3 + 3 = 8. (Wait, let's re-calculate).
- Correct optimal: 2 -> 2, 8 -> (op1) 4 -> (op2) 2, 3 -> 3. Sum = 2+2+2 = 6. (Actually, applying op1 then op2 on 8 gives 2. 2+2+2 = 6).

**Example 2:**
Input: nums = [2,4,3], k = 3, op1 = 2, op2 = 1
Output: 3
Explanation:
- Apply Op 1 to 2: 2 -> 1.
- Apply Op 1 to 4: 4 -> 2.
- Apply Op 2 to 3: 3 -> 0.
- Sum: 1 + 2 + 0 = 3.

**Constraints:**
- `1 <= nums.length <= 100`
- `0 <= nums[i] <= 10^5`
- `0 <= k <= 10^5`
- `0 <= op1, op2 <= nums.length`

---

## 🧠 Thought Process

### Problem Understanding
We want to minimize the sum of an array by applying two types of reductions to each element. Since each operation can only be used once per element, there are four choices for every number $nums[i]$:
1. Do nothing.
2. Apply **Op 1** only: $ceil(nums[i] / 2)$
3. Apply **Op 2** only: $nums[i] - k$ (if $nums[i] \geq k$)
4. Apply **Both**:
    - Case A: Op 1 then Op 2: $ceil(nums[i] / 2) - k$ (if $ceil(nums[i] / 2) \geq k$)
    - Case B: Op 2 then Op 1: $ceil((nums[i] - k) / 2)$ (if $nums[i] \geq k$)

### Key Insights
1. **Order Matters:** When applying both operations, the order can change the result. For example, if $nums[i] = 7$ and $k = 2$:
   - Op 1 then 2: $ceil(7/2) = 4 \to 4-2 = 2$.
   - Op 2 then 1: $7-2 = 5 \to ceil(5/2) = 3$.
   - To minimize, we'd pick the first order.
2. **Independence of Elements:** The decision made for $nums[i]$ only affects the remaining counts of `op1` and `op2`. This suggests **Dynamic Programming**.
3. **DP State:** We need to keep track of the current index and how many operations of each type we have left.
   - `dp[i][o1][o2]` = Minimum sum of suffix `nums[i...n-1]` given `o1` Op1 and `o2` Op2 available.

### Approach Selection
**Chosen Approach**: 3D Dynamic Programming (Top-Down with Memoization).
**Why?**
- The constraints are small ($N, op1, op2 \leq 100$), so $100 \times 100 \times 100 = 1,000,000$ states is perfectly acceptable.
- Top-down is easier to implement for this logic because of the conditional "Both Operations" branching.

## ⚡ Complexity Analysis
- **Time Complexity**: $O(n \cdot op1 \cdot op2)$ - Each state is visited once and takes $O(1)$ to compute.
- **Space Complexity**: $O(n \cdot op1 \cdot op2)$ - To store the memoization table.

---

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 NotesAl
ways check if $nums[i] \geq k$ before attempting Op 2.
When applying both, take the min of (Op1 then Op2) and (Op2 then Op1).
The ceil(x/2) can be written as (x + 1) / 2 in integer arithmetic.
🔗 Related Problems2-
Keys Keyboard
Out of Boundary Paths
Knight Dialer
