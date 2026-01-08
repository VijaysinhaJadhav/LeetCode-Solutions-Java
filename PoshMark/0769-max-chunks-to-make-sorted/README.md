# 769. Max Chunks To Make Sorted

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Stack, Greedy, Sorting  
**Companies:** Google, Amazon, Microsoft, Bloomberg, Adobe, Uber, Apple

[LeetCode Link](https://leetcode.com/problems/max-chunks-to-make-sorted/)

You are given an integer array `arr` of length `n` that represents a permutation of the integers in the range `[0, n - 1]`.

We split `arr` into some number of **chunks** (i.e., partitions), and individually sort each chunk. After concatenating them, the result should equal the sorted array.

Return *the largest number of chunks we can make to sort the array*.

**Example 1:**

Input: arr = [4,3,2,1,0]
Output: 1
Explanation:
Splitting into two or more chunks will not return the required result.
For example, splitting into [4, 3], [2, 1, 0] will result in [3, 4, 0, 1, 2], which isn't sorted.
Only one chunk will result in the sorted array.


**Example 2:**

Input: arr = [1,0,2,3,4]
Output: 4
Explanation:
We can split into two chunks such as [1, 0], [2, 3, 4].
However, splitting into [1, 0], [2], [3], [4] is the highest number of chunks possible.


**Constraints:**
- `n == arr.length`
- `1 <= n <= 10`
- `0 <= arr[i] < n`
- All the elements of `arr` are **unique**.

**Follow-up:** What if the array contains elements beyond the range [0, n-1]? (See LeetCode 768)

## 🧠 Thought Process

### Initial Thoughts
- We have a permutation of [0, n-1]
- We can split into chunks, sort each chunk, concatenate to get sorted array
- Need to find maximum number of chunks
- Each chunk must contain all numbers in some range when sorted

### Key Insights
1. **Observation:** For a valid chunk, all numbers in the chunk must be exactly the numbers in some contiguous range
   - Example: If we have [1, 0, 2], when sorted it becomes [0, 1, 2] which is exactly the range [0, 2]
2. **Max-Min Property:** A chunk from index i to j is valid if:
   - `max(arr[i..j]) = j` and `min(arr[i..j]) = i`
   - Because after sorting, the chunk should contain exactly numbers i to j
3. **Prefix Max Tracking:** 
   - As we iterate through array, track current maximum
   - When `max_so_far == current_index`, we can end a chunk here
   - This works because array contains 0 to n-1
4. **Mathematical Insight:** 
   - When we've seen all numbers 0 through i at position i, we can make a cut
   - The permutation property ensures numbers are unique

### Approach Selection
**Chosen Approach:** Greedy with Max Tracking  
**Why this approach?** 
- O(n) time complexity
- O(1) space complexity
- Simple and elegant
- Based on mathematical property of permutations

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Single pass through the array
- **Space Complexity:** O(1) - Only a few variables needed

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Key insight: When current maximum equals current index, we can end a chunk

Works because array is permutation of [0, n-1]

Each chunk will contain exactly the numbers needed for its position

Greedy approach gives optimal solution

🔗 Related Problems
768. Max Chunks To Make Sorted II (Harder version)

56. Merge Intervals

763. Partition Labels

921. Minimum Add to Make Parentheses Valid

735. Asteroid Collision

853. Car Fleet
