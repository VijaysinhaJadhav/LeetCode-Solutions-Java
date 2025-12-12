# 1094. Car Pooling

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Prefix Sum, Sorting, Simulation, Heap (Priority Queue)  
**Companies:** Google, Amazon, Microsoft, Meta, Uber, Lyft, DoorDash

[LeetCode Link](https://leetcode.com/problems/car-pooling/)

There is a car with `capacity` empty seats. The vehicle only drives east (i.e., it cannot turn around and drive west).

You are given the integer `capacity` and an array `trips` where `trips[i] = [numPassengersi, fromi, toi]` indicates that the `i-th` trip has `numPassengersi` passengers and the locations to pick them up and drop them off are `fromi` and `toi` respectively. The locations are given as the number of kilometers due east from the car's initial location.

Return `true` if it is possible to pick up and drop off all passengers for all the given trips, or `false` otherwise.

**Example 1:**

Input: trips = [[2,1,5],[3,3,7]], capacity = 4

Output: false

Explanation:

At mile 1, 2 passengers get on.

At mile 3, 3 passengers get on (total 5 passengers).

At mile 5, 2 passengers get off (remaining 3 passengers).

At mile 7, 3 passengers get off.

The car never has more than 4 passengers, but at mile 3 there are 5 passengers.

**Example 2:**

Input: trips = [[2,1,5],[3,3,7]], capacity = 5

Output: true

**Example 3:**

Input: trips = [[2,1,5],[3,5,7]], capacity = 3

Output: true


**Constraints:**
- `1 <= trips.length <= 1000`
- `trips[i].length == 3`
- `1 <= numPassengersi <= 100`
- `0 <= fromi < toi <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Need to track passenger count at each location
- Similar to interval scheduling with capacity constraint
- Multiple approaches: timeline array, sweep line, heap

### Key Insights
1. **Timeline Array**: Create array of size 1001 (max location), add passengers at pickup, subtract at dropoff
2. **Prefix Sum**: Calculate running total of passengers at each point
3. **Heap Approach**: Use min-heap to track when passengers get off
4. **Sweep Line**: Process events sorted by location

### Approach Selection
**Chosen Approach:** Timeline Array with Prefix Sum  
**Why this approach?** 
- O(n + L) time where L = max location (1000)
- O(L) space, simple and efficient
- Easy to understand and implement
- Works well within constraints

## ⚡ Complexity Analysis
- **Time Complexity:** O(n + L) where n = trips.length, L = max location (1000)
- **Space Complexity:** O(L) for timeline array

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Since toi ≤ 1000, we can use fixed-size array

Add passengers at pickup location, subtract at dropoff location

Calculate running sum, check if exceeds capacity at any point

Alternative heap approach more efficient for sparse events

🔗 Related Problems
Meeting Rooms II

Merge Intervals

My Calendar III

Corporate Flight Bookings
