# 1046. Last Stone Weight

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Heap (Priority Queue), Simulation  
**Companies:** Amazon, Google, Microsoft, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/last-stone-weight/)

You are given an array of integers `stones` where `stones[i]` is the weight of the `i-th` stone.

We are playing a game with the stones. On each turn, we choose the **two heaviest** stones and smash them together. Suppose the heaviest two stones have weights `x` and `y` with `x <= y`. The result of this smash is:
- If `x == y`, both stones are destroyed
- If `x != y`, the stone of weight `x` is destroyed, and the stone of weight `y` has new weight `y - x`

At the end of the game, there is **at most one** stone left. Return the weight of this stone or `0` if there are no stones left.

**Example 1:**

Input: stones = [2,7,4,1,8,1]

Output: 1

Explanation:

We combine 7 and 8 to get 1 so the array becomes [2,4,1,1,1],

then we combine 2 and 4 to get 2 so the array becomes [2,1,1,1],

then we combine 2 and 1 to get 1 so the array becomes [1,1,1],

then we combine 1 and 1 to get 0 so the array becomes [1], then that's the answer.


**Example 2:**

Input: stones = [1]

Output: 1


**Constraints:**
- `1 <= stones.length <= 30`
- `1 <= stones[i] <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Need to repeatedly find and process the two largest stones
- After each operation, we may need to insert a new stone (if weights are different)
- This suggests using a data structure that efficiently supports getting max elements

### Key Insights
1. **Max-Heap Approach**: Use a max-heap to efficiently get the two largest stones
2. **Simulation**: Repeatedly extract top two stones, process them, and insert result if needed
3. **Termination**: Continue until 0 or 1 stone remains
4. **Alternative**: Can also use sorting, but heap is more efficient for repeated operations

### Approach Selection
**Chosen Approach:** Max-Heap (Priority Queue)  
**Why this approach?** 
- O(n log n) time complexity
- O(n) space complexity
- Efficiently handles repeated max operations
- Clean and intuitive simulation

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Each stone is processed and heap operations are O(log n)
- **Space Complexity:** O(n) - For the heap storage

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a max-heap (implemented via PriorityQueue with reverse order)

While heap has more than 1 stone, repeatedly smash the two largest

If weights are different, insert the difference back into heap

Return the last stone or 0 if no stones remain

🔗 Related Problems
Kth Largest Element in a Stream

Kth Largest Element in an Array

Find Median from Data Stream

Meeting Rooms II

K Closest Points to Origin
