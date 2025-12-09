# 1011. Capacity To Ship Packages Within D Days

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Uber, Apple

[LeetCode Link](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/)

A conveyor belt has packages that must be shipped from one port to another within `days` days.

The `i-th` package on the conveyor belt has a weight of `weights[i]`. Each day, we load the ship with packages on the conveyor belt (in the order given by `weights`). We may not load more weight than the maximum weight capacity of the ship.

Return the least weight capacity of the ship that will result in all the packages being shipped within `days` days.

**Example 1:**

Input: weights = [1,2,3,4,5,6,7,8,9,10], days = 5

Output: 15

Explanation:

A ship capacity of 15 is the minimum to ship all packages in 5 days like this:

1st day: 1, 2, 3, 4, 5

2nd day: 6, 7

3rd day: 8

4th day: 9

5th day: 10


**Example 2:**

Input: weights = [3,2,2,4,1,4], days = 3

Output: 6

Explanation:

A ship capacity of 6 is the minimum to ship all packages in 3 days like this:

1st day: 3, 2

2nd day: 2, 4

3rd day: 1, 4


**Example 3:**

Input: weights = [1,2,3,1,1], days = 4

Output: 3

Explanation:

1st day: 1

2nd day: 2

3rd day: 3

4th day: 1, 1


**Constraints:**
- `1 <= days <= weights.length <= 5 * 10^4`
- `1 <= weights[i] <= 500`

## 🧠 Thought Process

### Initial Thoughts
- Need to find minimum ship capacity to ship all packages in given days
- Packages must be shipped in order (cannot reorder)
- Each day, we load as many packages as possible without exceeding capacity
- Large constraints suggest binary search solution

### Key Insights
1. **Search Space**: Capacity ranges from max(weights) to sum(weights)
   - Lower bound: ship must handle heaviest package
   - Upper bound: ship could carry all packages at once
2. **Feasibility Function**: For a given capacity, calculate days needed and check if ≤ days
3. **Binary Search**: Search for minimum capacity that satisfies the days constraint
4. **Monotonic Property**: If capacity works, all larger capacities will also work

### Approach Selection
**Chosen Approach:** Binary Search on Answer  
**Why this approach?** 
- O(n log s) time complexity where n is weights.length and s is sum(weights)
- Efficient for large constraints (up to 5 * 10^4 packages)
- Naturally fits the "minimum capacity that satisfies condition" pattern
- More efficient than linear search O(n * s)

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log s) where n is weights.length and s is sum(weights)
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to recognize this as a binary search problem on the possible capacities

The feasibility function calculates days needed for a given capacity by greedy loading

Search for the smallest capacity that satisfies days ≤ given days

Similar pattern to Koko Eating Bananas (LeetCode 875)

🔗 Related Problems
Koko Eating Bananas

Split Array Largest Sum

Minimum Number of Days to Make m Bouquets

Find the Smallest Divisor Given a Threshold


