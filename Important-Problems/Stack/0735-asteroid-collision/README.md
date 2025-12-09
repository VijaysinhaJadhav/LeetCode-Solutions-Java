# 735. Asteroid Collision

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Stack, Simulation  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/asteroid-collision/)

We are given an array `asteroids` of integers representing asteroids in a row. For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.

**Example 1:**

Input: asteroids = [5,10,-5]

Output: [5,10]

Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.


**Example 2:**

Input: asteroids = [8,-8]

Output: []

Explanation: The 8 and -8 collide exploding each other.


**Example 3:**

Input: asteroids = [10,2,-5]

Output: [10]

Explanation: The 2 and -5 collide resulting in -5. The 10 and -5 collide resulting in 10.


**Constraints:**
- `2 <= asteroids.length <= 10^4`
- `-1000 <= asteroids[i] <= 1000`
- `asteroids[i] != 0`

## 🧠 Thought Process

### Initial Thoughts
- Need to simulate asteroid collisions
- Stack is perfect for tracking asteroids that might collide
- Only collisions happen when right-moving asteroid is followed by left-moving asteroid
- Multiple approaches with different implementations

### Key Insights
1. **Stack Simulation**: Use stack to track surviving asteroids
2. **Collision Conditions**: Only positive then negative asteroids collide
3. **Size Comparison**: Smaller asteroid explodes, equal sizes both explode
4. **The key insight**: Process asteroids left to right, handle collisions with stack

### Approach Selection
**Chosen Approach:** Stack Simulation  
**Why this approach?** 
- O(n) time complexity
- O(n) space complexity
- Intuitive and matches the problem's natural flow
- Handles all collision scenarios efficiently

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each asteroid processed once
- **Space Complexity:** O(n) - Stack storage for surviving asteroids

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Stack naturally handles the collision scenarios

Only need to handle cases where positive asteroid is followed by negative

Process asteroids sequentially, resolve collisions immediately

The result is the asteroids remaining in the stack

🔗 Related Problems
Asteroid Collision

Remove All Adjacent Duplicates In String

Make The String Great

Remove All Adjacent Duplicates in String II

Minimum Deletions to Make Array Beautiful
