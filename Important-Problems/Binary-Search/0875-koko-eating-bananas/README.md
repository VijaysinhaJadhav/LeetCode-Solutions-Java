# 875. Koko Eating Bananas

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Binary Search  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Uber, Apple

[LeetCode Link](https://leetcode.com/problems/koko-eating-bananas/)

Koko loves to eat bananas. There are `n` piles of bananas, the `i-th` pile has `piles[i]` bananas. The guards have gone and will come back in `h` hours.

Koko can decide her bananas-per-hour eating speed of `k`. Each hour, she chooses some pile of bananas and eats `k` bananas from that pile. If the pile has less than `k` bananas, she eats all of them instead and will not eat any more bananas during this hour.

Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.

Return the minimum integer `k` such that she can eat all the bananas within `h` hours.

**Example 1:**

Input: piles = [3,6,7,11], h = 8

Output: 4


**Example 2:**

Input: piles = [30,11,23,4,20], h = 5

Output: 30


**Example 3:**

Input: piles = [30,11,23,4,20], h = 6

Output: 23


**Constraints:**
- `1 <= piles.length <= 10^4`
- `piles.length <= h <= 10^9`
- `1 <= piles[i] <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- Koko needs to eat all bananas in `h` hours
- Each hour, she can eat at most `k` bananas from one pile
- Need to find minimum `k` that allows her to finish in time
- Large constraints suggest binary search solution

### Key Insights
1. **Search Space**: `k` ranges from 1 to max(piles) (she never needs to eat faster than the largest pile)
2. **Feasibility Function**: For a given `k`, calculate total hours needed and check if ≤ `h`
3. **Binary Search**: Search for the minimum `k` that satisfies the time constraint
4. **Monotonic Property**: If `k` works, all larger `k` will also work

### Approach Selection
**Chosen Approach:** Binary Search on Answer  
**Why this approach?** 
- O(n log m) time complexity where m is max(piles)
- Efficient for large constraints (up to 10^9)
- Naturally fits the "minimum k that satisfies condition" pattern
- More efficient than linear search O(n * m)

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log m) where n is piles.length and m is max(piles)
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to recognize this as a binary search problem on the possible eating speeds

The feasibility function calculates total hours needed for a given eating speed

Use ceiling division: hours = (pile + k - 1) / k

Search for the smallest k that satisfies hours ≤ h

🔗 Related Problems
Capacity To Ship Packages Within D Days

Split Array Largest Sum

Minimum Number of Days to Make m Bouquets

Find the Smallest Divisor Given a Threshold
