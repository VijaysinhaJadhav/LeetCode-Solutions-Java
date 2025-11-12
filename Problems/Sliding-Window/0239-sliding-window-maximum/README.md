# 239. Sliding Window Maximum

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Queue, Sliding Window, Heap (Priority Queue), Monotonic Queue  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber, LinkedIn, Oracle

[LeetCode Link](https://leetcode.com/problems/sliding-window-maximum/)

You are given an array of integers `nums`, and there is a sliding window of size `k` which is moving from the very left of the array to the very right. You can only see the `k` numbers in the window. Each time the sliding window moves right by one position.

Return the max sliding window.

**Example 1:**

Input: nums = [1,3,-1,-3,5,3,6,7], k = 3

Output: [3,3,5,5,6,7]

Explanation:

Window position Max

[1 3 -1] -3 5 3 6 7 3

1 [3 -1 -3] 5 3 6 7 3

1 3 [-1 -3 5] 3 6 7 5

1 3 -1 [-3 5 3] 6 7 5

1 3 -1 -3 [5 3 6] 7 6

1 3 -1 -3 5 [3 6 7] 7


**Example 2:**

Input: nums = [1], k = 1

Output: [1]


**Constraints:**
- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`
- `1 <= k <= nums.length`

## 🧠 Thought Process

### Initial Thoughts
- Need to find maximum in each sliding window of size k
- Multiple approaches with different time complexities
- The challenge is to do it efficiently for large inputs (up to 10^5)

### Key Insights
1. **Monotonic Deque Approach**: Maintain decreasing order in deque for O(n) solution
2. **Heap Approach**: Use max heap but need to handle elements going out of window
3. **Dynamic Programming**: Left and right arrays for O(n) solution
4. **All approaches must handle the sliding nature efficiently**

### Approach Selection
**Chosen Approach:** Monotonic Deque  
**Why this approach?** 
- O(n) time complexity - optimal
- O(k) space complexity
- Naturally handles the sliding window maximum
- Elegant and efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) where n is the length of nums
- **Space Complexity:** O(k) for the deque

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Maintain a deque that stores indices in decreasing order of values

Remove from back while current element is greater than deque's back

Remove from front if index is out of current window

The front of deque always contains the maximum for current window

🔗 Related Problems
Sliding Window Median

Find Median from Data Stream

Moving Average from Data Stream

Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit

Constrained Subsequence Sum
