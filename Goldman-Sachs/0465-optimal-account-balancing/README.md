# 465. Optimal Account Balancing

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Backtracking, Dynamic Programming, Bit Manipulation, State Compression  
**Companies:** Uber, Google, Amazon, Microsoft, Apple  

[LeetCode Link](https://leetcode.com/problems/optimal-account-balancing/)

You are given an array of transactions where `transactions[i] = [from_i, to_i, amount_i]` indicates that the person with `ID = from_i` gave `amount_i` dollars to the person with `ID = to_i`.

Return the **minimum number of transactions** required to settle all debts so that everyone's balance becomes zero.

**Example 1:**

Input: transactions = [[0,1,10], [2,0,5]]
Output: 2
Explanation:
Person #0 gave person #1 $10.
Person #2 gave person #0 $5.
Two transactions are needed. One way: person #1 pays person #0 and #2 $5 each.


**Example 2:**

Input: transactions = [[0,1,10], [1,0,1], [1,2,5], [2,0,5]]
Output: 1
Explanation:
Person #0 gave person #1 $10.
Person #1 gave person #0 $1.
Person #1 gave person #2 $5.
Person #2 gave person #0 $5.
Therefore, person #1 only needs to give person #0 $4, and all debt is settled.


**Constraints:**
- `1 <= transactions.length <= 8`
- `transactions[i].length == 3`
- `0 <= from_i, to_i < 12`
- `from_i != to_i`
- `1 <= amount_i <= 100`

## 🧠 Thought Process

### Problem Understanding
We need to find the minimum number of new transactions to settle all debts. The key insight: we only care about each person's **net balance**, not the individual transactions.

### Key Insights
1. **Net Balance Focus**: Calculate each person's net balance (money they owe or are owed) [citation:1]
2. **Zero-Sum Groups**: People whose balances sum to zero can settle internally
3. **Group Size Property**: A group of `k` people with zero sum can be settled in `k-1` transactions
4. **Small Constraints**: At most 8 transactions, so at most 16 people with non-zero balances [citation:2]

### Approach Selection
**Chosen Approach:** Backtracking DFS (Greedy Pairing)
**Why this approach?**
- O(n!) complexity is acceptable given constraints (n ≤ 16)
- Simple and intuitive implementation
- Uses pruning for efficiency

**Alternative Approaches:**
- **DP with Bitmask**: More advanced, O(3^n) complexity
- **Bitmask Subset DP**: O(3^n) using state compression [citation:10]

## ⚡ Complexity Analysis
- **Time Complexity:** O(n!) where n = number of people with non-zero balances
- **Space Complexity:** O(n) for recursion stack

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Only people with non-zero balances need to be considered

When settling, always pair a debtor with a creditor (balances of opposite signs)

The greedy approach of trying all pairs works due to small constraints

DP with bitmask can optimize for larger inputs

🔗 Related Problems
Remove Boxes (similar state compression DP)
Partition to K Equal Sum Subsets
Minimum Number of Work Sessions to Finish the Tasks
