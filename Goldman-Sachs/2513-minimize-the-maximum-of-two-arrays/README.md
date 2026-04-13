# 2513. Minimize the Maximum of Two Arrays

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Math, Binary Search, Number Theory  
**Companies:** Google, Amazon, Microsoft  

[LeetCode Link](https://leetcode.com/problems/minimize-the-maximum-of-two-arrays/)

We have two arrays `arr1` and `arr2` that are initially empty. You need to add positive integers to these arrays such that:

- `arr1` contains **unique** integers.
- `arr2` contains **unique** integers.
- `arr1` and `arr2` are **disjoint** (no common integers).
- The total number of integers in `arr1` is `uniqueCnt1`.
- The total number of integers in `arr2` is `uniqueCnt2`.
- **No integer in `arr1` is divisible by `divisor1`**.
- **No integer in `arr2` is divisible by `divisor2`**.

Return the **minimum possible maximum integer** in either array.

**Example 1:**

Input: divisor1 = 2, divisor2 = 3, uniqueCnt1 = 1, uniqueCnt2 = 2
Output: 4
Explanation:
arr1 = [1] (1 is not divisible by 2)
arr2 = [2,3] (2 is not divisible by 3, 3 is not divisible by 3)
Maximum = 4


**Example 2:**

Input: divisor1 = 2, divisor2 = 4, uniqueCnt1 = 3, uniqueCnt2 = 2
Output: 6


**Constraints:**
- `2 <= divisor1, divisor2 <= 10^5`
- `1 <= uniqueCnt1, uniqueCnt2 < 2^31`

## 🧠 Thought Process

### Problem Understanding
We need to find the smallest number `maxVal` such that we can select:
- `uniqueCnt1` numbers ≤ `maxVal` that are NOT divisible by `divisor1`
- `uniqueCnt2` numbers ≤ `maxVal` that are NOT divisible by `divisor2`
- All selected numbers are distinct (no overlap between the two arrays)

### Key Insights
1. **Counting Available Numbers**: For a given `maxVal`, we can count:
   - Numbers not divisible by `d1` = `maxVal - maxVal / d1`
   - Numbers not divisible by `d2` = `maxVal - maxVal / d2`
2. **Overlap Problem**: Numbers that are divisible by **both** `d1` and `d2` cannot be used by either array
3. **LCM for Overlap**: Numbers divisible by `lcm(d1, d2)` are divisible by both
4. **Binary Search**: The answer is monotonic - if we can achieve with `maxVal`, we can also achieve with larger values

### Approach Selection
**Chosen Approach:** Binary Search with Mathematical Counting  
**Why this approach?**
- O(log answer) time complexity
- O(1) space complexity
- Efficient for large constraints

## ⚡ Complexity Analysis
- **Time Complexity:** O(log(answer)) ≈ O(log(2×10^9)) ≈ 31 iterations
- **Space Complexity:** O(1)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use binary search on the maximum value

For a given mid, compute available numbers for each array

Subtract overlap (numbers that are divisible by both divisors)

Check if we can select required counts

🔗 Related Problems
Nth Magical Number
Ugly Number III
Magnetic Force Between Two Balls
Minimum Speed to Arrive on Time
