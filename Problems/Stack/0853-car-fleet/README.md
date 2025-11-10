# 853. Car Fleet

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Stack, Sorting, Monotonic Stack  
**Companies:** Amazon, Google, Microsoft, Uber, Bloomberg, Apple

[LeetCode Link](https://leetcode.com/problems/car-fleet/)

There are `n` cars going to the same destination along a one-lane road. The destination is `target` miles away.

You are given two integer arrays `position` and `speed`, both of length `n`, where `position[i]` is the position (in miles) of the `i-th` car and `speed[i]` is the speed (in miles per hour) of the `i-th` car.

A car can never pass another car ahead of it, but it can catch up to it and drive bumper to bumper at the same speed. The faster car will slow down to match the slower car's speed. The distance between these two cars is ignored (i.e., they are assumed to have the same position).

A car fleet is some non-empty set of cars driving at the same position and same speed. Note that a single car is also a car fleet.

If a car catches up to a car fleet right at the destination point, it will still be considered as one car fleet.

Return the number of car fleets that will arrive at the destination.

**Example 1:**

Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]

Output: 3

Explanation:

The cars starting at 10 (speed 2) and 8 (speed 4) become a fleet, meeting each other at 12.

The car starting at 0 (speed 1) does not catch any other car, so it's a fleet by itself.

The cars starting at 5 (speed 1) and 3 (speed 3) become a fleet, meeting at 6. Then they move as a fleet at speed 1 until they reach 12.

So there are 3 fleets.


**Example 2:**

Input: target = 10, position = [3], speed = [3]

Output: 1


**Example 3:**

Input: target = 100, position = [0,2,4], speed = [4,2,1]

Output: 1

Explanation:

The cars starting at 0 (speed 4) and 2 (speed 2) become a fleet at position 4.

The fleet then meets the car starting at 4 (speed 1) at position 4, forming one fleet.

So there is only 1 fleet.


**Constraints:**
- `n == position.length == speed.length`
- `1 <= n <= 10^5`
- `0 < target <= 10^6`
- `0 <= position[i] < target`
- `1 <= speed[i] <= 10^6`

## 🧠 Thought Process

### Initial Thoughts
- Need to determine how many car fleets form by the time they reach destination
- Cars cannot pass each other, so order matters based on initial position
- Multiple approaches with different time/space trade-offs

### Key Insights
1. **Sorting**: Process cars from closest to farthest from target
2. **Time to Target**: Calculate time needed for each car to reach target
3. **Monotonic Stack**: Use stack to track fleets based on arrival times
4. **The key insight**: If a car behind takes less time than car ahead, they form a fleet

### Approach Selection
**Chosen Approach:** Sorting + Monotonic Stack  
**Why this approach?** 
- O(n log n) time complexity (due to sorting)
- O(n) space complexity
- Intuitive and handles all cases correctly
- Efficient for large input constraints

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Dominated by sorting
- **Space Complexity:** O(n) - Storage for cars and stack
- **Sorting:** O(n log n), Processing: O(n)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort cars by position in descending order (closest to target first)

Calculate time needed to reach target for each car

Use stack to maintain fleet leaders based on arrival time

If car behind arrives faster, it joins the fleet ahead

🔗 Related Problems
Car Fleet II

Daily Temperatures

Online Stock Span

Largest Rectangle in Histogram

Trapping Rain Water







