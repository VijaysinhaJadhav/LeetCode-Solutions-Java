# 1863. Sum of All Subset XOR Totals

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Math, Backtracking, Bit Manipulation, Combinatorics, Enumeration  
**Companies:** Google, Amazon, Microsoft, Adobe

[LeetCode Link](https://leetcode.com/problems/sum-of-all-subset-xor-totals/)

The **XOR total** of an array is defined as the bitwise `XOR` of all its elements, or `0` if the array is empty.

- For example, the **XOR total** of the array `[2,5,6]` is `2 XOR 5 XOR 6 = 1`.

Given an array `nums`, return the sum of all **XOR totals** for every subset of `nums`. 

**Note:** Subsets with the same elements should be counted multiple times.

**Example 1:**

Input: nums = [1,3]

Output: 6

Explanation: The 4 subsets of [1,3] are:

The empty subset has an XOR total of 0.

[1] has an XOR total of 1.

[3] has an XOR total of 3.

[1,3] has an XOR total of 1 XOR 3 = 2.

0 + 1 + 3 + 2 = 6


**Example 2:**

Input: nums = [5,1,6]

Output: 28

Explanation: The 8 subsets of [5,1,6] are:

The empty subset has an XOR total of 0.

[5] has an XOR total of 5.

[1] has an XOR total of 1.

[6] has an XOR total of 6.

[5,1] has an XOR total of 5 XOR 1 = 4.

[5,6] has an XOR total of 5 XOR 6 = 3.

[1,6] has an XOR total of 1 XOR 6 = 7.

[5,1,6] has an XOR total of 5 XOR 1 XOR 6 = 2.

0 + 5 + 1 + 6 + 4 + 3 + 7 + 2 = 28


**Example 3:**

Input: nums = [3,4,5,6,7,8]

Output: 480


**Constraints:**
- `1 <= nums.length <= 12`
- `1 <= nums[i] <= 20`

## 🧠 Thought Process

### Initial Thoughts
- Need to compute XOR of all subsets and sum them up
- Multiple approaches: backtracking, bit manipulation, mathematical
- Since n ≤ 12, O(2^n) solutions are acceptable

### Key Insights
1. **Backtracking**: Generate all subsets and compute their XOR
2. **Bit Manipulation**: Use bitmask to represent subsets
3. **Mathematical**: Each bit contributes based on its frequency in subsets
4. **The key insight**: Each element appears in exactly 2^(n-1) subsets

### Approach Selection
**Chosen Approach:** Mathematical Bit Analysis  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Most efficient and elegant
- Leverages combinatorial properties

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only constant extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Each bit position contributes independently to the final sum

For the i-th bit, if k numbers have this bit set, then 2^(n-1) subsets will have this bit in their XOR

The total sum is the OR of all numbers multiplied by 2^(n-1)

This works because XOR is linear and bits are independent

🔗 Related Problems
Subsets

Subsets II

Count Number of Maximum Bitwise-OR Subsets

Maximum Good People Based on Statements

Find Minimum Diameter After Merging Two Trees
