# 374. Guess Number Higher or Lower

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Binary Search, Interactive  
**Companies:** Google, Amazon, Microsoft, Apple, Bloomberg, Facebook

[LeetCode Link](https://leetcode.com/problems/guess-number-higher-or-lower/)

We are playing the Guess Game. The game is as follows:

I pick a number from `1` to `n`. You have to guess which number I picked.

Every time you guess wrong, I will tell you whether the number I picked is higher or lower than your guess.

You call a pre-defined API `int guess(int num)` which returns three possible results:
- `-1`: Your guess is higher than the number I picked (i.e., `num > pick`)
- `1`: Your guess is lower than the number I picked (i.e., `num < pick`)
- `0`: Your guess is equal to the number I picked (i.e., `num == pick`)

Return the number that I picked.

**Example 1:**

Input: n = 10, pick = 6

Output: 6


**Example 2:**

Input: n = 1, pick = 1

Output: 1


**Example 3:**

Input: n = 2, pick = 1

Output: 1


**Constraints:**
- `1 <= n <= 2^31 - 1`
- `1 <= pick <= n`

## 🧠 Thought Process

### Initial Thoughts
- We need to find a number between 1 and n
- We have an API that tells us if our guess is too high, too low, or correct
- The constraints (up to 2^31-1) require an efficient algorithm
- Binary search is perfect for this scenario

### Key Insights
1. **Binary Search**: The problem is essentially searching in a sorted range [1, n]
2. **API Guidance**: The `guess` API tells us which direction to search
3. **Avoid Overflow**: Use `mid = left + (right - left) / 2` to prevent integer overflow
4. **Termination Condition**: Stop when `guess(mid) == 0`

### Approach Selection
**Chosen Approach:** Binary Search  
**Why this approach?** 
- O(log n) time complexity handles the large constraint efficiently
- Simple and intuitive implementation
- Naturally fits the problem structure
- Prevents the O(n) worst-case of linear search

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) - Binary search halves the search space each iteration
- **Space Complexity:** O(1) - Only constant extra space used

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to use binary search with the guess API as the comparison function

Handle the integer overflow carefully with proper mid calculation

The problem demonstrates how binary search can be applied to interactive problems

🔗 Related Problems
First Bad Version

Search Insert Position

Binary Search

Sqrt(x)
