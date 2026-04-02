# 134. Gas Station

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Greedy  
**Companies:** Amazon, Microsoft, Google, Facebook, Bloomberg, Uber  

[LeetCode Link](https://leetcode.com/problems/gas-station/)

There are `n` gas stations along a circular route, where the amount of gas at the `i`-th station is `gas[i]`.

You have a car with an unlimited gas tank and it costs `cost[i]` of gas to travel from the `i`-th station to its next `(i + 1)`-th station. You begin the journey with an empty tank at one of the gas stations.

Given two integer arrays `gas` and `cost`, return *the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return* `-1`. If there exists a solution, it is guaranteed to be **unique**.

**Example 1:**

Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Explanation:
Start at station 3 (index 3) with 4 gas. Travel to station 4 (cost 1) → tank = 3
Travel to station 0 (cost 2) → tank = 1
Travel to station 1 (cost 3) → tank = -2 (not enough gas)
Alternative: Start at station 3 → tank = 4 → station 4 (cost 1) → tank = 3
→ station 0 (cost 2) → tank = 1
→ station 1 (cost 3) → tank = -2 → invalid

Wait, let's recalc properly...


**Example 2:**

Input: gas = [2,3,4], cost = [3,4,3]
Output: -1
Explanation:
You cannot start at any station and complete the circuit.


**Example 3:**

Input: gas = [5,1,2,3,4], cost = [4,4,1,5,1]
Output: 4
Explanation:
Start at station 4 with 4 gas. Travel to station 0 (cost 1) → tank = 3
Travel to station 1 (cost 4) → tank = 0
Travel to station 2 (cost 1) → tank = 1
Travel to station 3 (cost 5) → tank = -1 → invalid


**Constraints:**
- `n == gas.length == cost.length`
- `1 <= n <= 10^5`
- `0 <= gas[i], cost[i] <= 10^4`

## 🧠 Thought Process

### Problem Understanding
We need to find a starting gas station that allows us to complete a full circular tour. At each station, we add `gas[i]` to our tank and subtract `cost[i]` to travel to the next station. The tank should never go negative.

### Key Insights
1. **Total Gas Check**: If `sum(gas) < sum(cost)`, it's impossible → return -1
2. **Greedy Property**: If we start at station i and run out at station j, we cannot start at any station between i and j
3. **Single Pass Solution**: Track current tank and overall total, reset when tank goes negative

### Approach Selection
**Chosen Approach:** Greedy Single Pass  
**Why this approach?**
- O(n) time complexity
- O(1) space complexity
- Elegant and efficient

**Alternative Approaches:**
- **Brute Force**: Try each station as start (O(n²))
- **Two-Pointer**: Expand window until valid (O(n²))

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n is number of stations
- **Space Complexity:** O(1) extra space

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key insight is that if total gas < total cost, no solution exists

Otherwise, there must be exactly one valid starting index

We can find it in a single pass by tracking running sum and resetting when negative

The starting candidate is always the station after the last reset point

🔗 Related Problems
Maximum Subarray (similar Kadane's algorithm pattern)
Maximum Sum Circular Subarray
Best Time to Buy and Sell Stock
Best Time to Buy and Sell Stock II
