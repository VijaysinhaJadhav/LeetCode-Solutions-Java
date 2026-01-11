# 2954. Count the Number of Infection Sequences

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Math, Combinatorics, Dynamic Programming, Sorting  
**Companies:** Google, Meta, Amazon, Uber, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/count-the-number-of-infection-sequences/)

You are given an integer `n` and a **0-indexed** integer array `sick` which is **sorted in increasing order**.

There are `n` children standing in a queue with positions `0` to `n - 1`. Initially, the `sick` children are infected with a disease. An infected child at position `i` can spread the disease to another child at position `j` if at least one of the following is true:

- `j = i - 1` and `j >= 0`
- `j = i + 1` and `j < n`

The infection spreads **sequentially** until no more children can be infected. A child that gets infected remains infected throughout the process.

Return the **total number of distinct sequences** in which all `n` children can get infected. Since the answer may be large, return it modulo `10^9 + 7`.

Two sequences are considered different if at least one child gets infected at a different time.

**Example 1:**

Input: n = 5, sick = [0,4]
Output: 4
Explanation:
Children at positions 1, 2, and 3 are not infected initially.
The sequences of infections are:

[0,1,2,3,4] → infection starts from 0, spreads to 1, then 2, then 3, then 4

[0,1,2,4,3] → infection starts from 0, spreads to 1, then 2, then 4, then 3

[0,1,4,2,3] → infection starts from 0, spreads to 1, then 4, then 2, then 3

[4,3,2,1,0] → infection starts from 4, spreads to 3, then 2, then 1, then 0


**Example 2:**

Input: n = 4, sick = [1]
Output: 3
Explanation:
Children at positions 0, 2, and 3 are not infected initially.
The sequences are:

[1,0,2,3] → infection starts from 1, spreads to 0, then 2, then 3

[1,2,0,3] → infection starts from 1, spreads to 2, then 0, then 3

[1,2,3,0] → infection starts from 1, spreads to 2, then 3, then 0


**Constraints:**
- `2 <= n <= 10^5`
- `1 <= sick.length <= n - 1`
- `0 <= sick[i] <= n - 1`
- `sick` is sorted in increasing order.
- The input is generated such that at least one child is not infected at the start.

## 🧠 Thought Process

### Problem Understanding
We have a line of `n` children with some initially infected. The infection spreads to adjacent children. We need to count all possible sequences of infections until all children are infected.

### Key Insights
1. **Combinatorial Nature**: This is a combinatorial counting problem
2. **Segments**: The initially healthy children form segments between infected children
3. **Independent Segments**: Each segment of healthy children between infected ones evolves independently
4. **Two-Sided Infection**: For internal segments (between two infected), infection can come from both sides
5. **One-Sided Infection**: For edge segments (at ends), infection can only come from one side
6. **Catalan-like Numbers**: The count for a segment of length L with infection from both sides is related to binomial coefficients
7. **Multiplying Results**: Final answer = product of counts for all segments × permutations of infection times

### Mathematical Formulation
For a segment of length `L` with infection from both sides:
- Number of ways = `(L!)/((L/2)! * (L/2)!)` = binomial(L, L/2) but with timing considerations
- Actually: `C(L, k)` where k is number infected from left side (0 to L)
- Sum over k = 2^L, but with timing constraints

Correct formula: For segment length `L` infected from both sides:
- Count = `sum_{k=0}^{L} C(L, k) = 2^L` (each child can be infected from left or right)

But wait - this doesn't account for timing! Actually need to consider:
- Each infection time must be increasing
- Infection from left and right interleave

Actually: For segment length `L` infected from both ends:
- Number of sequences = number of ways to interleave L infections from two sources
- = number of permutations of L items where relative order within each source is fixed
- = `L! / (k! * (L-k)!)` where k infected from left, sum over k = `2^L` (WRONG - need different reasoning)

Let's think differently: Each healthy child in internal segment has 2 choices of infection source (left or right neighbor), and infection times must be consistent.

Actually known result: For internal segment of length L, count = `2^{L-1}` (proof: last child infected must have neighbor already infected)

For edge segment of length L (only one infection source), count = 1 (infection must spread sequentially)

### Final Formula
Let segments = healthy children between infected:
- For internal segment of length L: ways = `2^{L-1}`
- For edge segment of length L: ways = 1

Total ways = `∏ (segment_ways) × (total_infections! / ∏ (segment_length!))`

But actually need: product of segment ways × ways to interleave infections from different segments.

Actually: Total ways = `(m! / (∏ segment_length!)) × ∏ (2^{L-1} for internal segments)`

Where m = total healthy children = n - len(sick)

### Approach Selection
**Chosen Approach**: Combinatorics with factorial precomputation and modular arithmetic
**Why this approach?**
- O(n) time complexity
- Handles up to 10^5 constraints
- Based on mathematical derivation

## ⚡ Complexity Analysis
- **Time Complexity**: O(n) for precomputing factorials and processing segments
- **Space Complexity**: O(n) for factorial arrays

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
This is a combinatorial problem requiring careful counting

The key insight is that segments between infected children evolve independently

Need to handle modular arithmetic properly (mod 1e9+7)

Edge cases: segments at ends vs internal segments

🔗 Related Problems
Number of Ways to Reach a Position After Exactly k Steps

Unique Paths

Unique Binary Search Trees

Kth Smallest Instructions

Number of Ways to Rearrange Sticks With K Sticks Visible
