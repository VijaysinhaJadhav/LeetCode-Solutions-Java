# 1095. Find in Mountain Array

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Array, Binary Search, Interactive  
**Companies:** Google, Amazon, Microsoft, Facebook, Bloomberg

[LeetCode Link](https://leetcode.com/problems/find-in-mountain-array/)

*(This problem is an interactive problem.)*

You may recall that an array `arr` is a **mountain array** if and only if:

- `arr.length >= 3`
- There exists some `i` with `0 < i < arr.length - 1` such that:
  - `arr[0] < arr[1] < ... < arr[i - 1] < arr[i]`
  - `arr[i] > arr[i + 1] > ... > arr[arr.length - 1]`

Given a mountain array `mountainArr`, return the **minimum** index such that `mountainArr.get(index) == target`. If such an index does not exist, return `-1`.

**You cannot access the mountain array directly.** Instead, you must interact with the `MountainArray` interface:

- `MountainArray.get(k)` returns the element of the array at index `k` (0-indexed).
- `MountainArray.length()` returns the length of the array.

**Submissions making more than 100 calls to `MountainArray.get` will be judged *Wrong Answer*. Also, any solutions that attempt to circumvent the judge will result in disqualification.**

**Example 1:**

Input: array = [1,2,3,4,5,3,1], target = 3

Output: 2

Explanation: 3 exists in the array, at index 2 and 5. Return the minimum index 2.


**Example 2:**

Input: array = [0,1,2,4,2,1], target = 3

Output: -1

Explanation: 3 does not exist in the array, so we return -1.


**Example 3:**

Input: array = [1,5,2], target = 2

Output: 2


**Constraints:**
- `3 <= mountain_arr.length() <= 10^4`
- `0 <= target <= 10^9`
- `0 <= mountain_arr.get(index) <= 10^9`

## 🧠 Thought Process

### Initial Thoughts
- We have a mountain array (increasing then decreasing)
- We need to find target with minimum index
- Limited to 100 API calls (critical constraint)
- Need efficient algorithm with minimal get() calls

### Key Insights
1. **Three Phase Approach**:
   - Find the peak element (maximum point)
   - Search in the increasing part (left of peak)
   - If not found, search in the decreasing part (right of peak)

2. **Binary Search Optimization**:
   - Use binary search to find peak (O(log n) calls)
   - Use binary search in both ascending and descending parts
   - Total calls: ~3 * log n (within 100 calls for n ≤ 10,000)

3. **Mountain Array Properties**:
   - Strictly increasing until peak
   - Strictly decreasing after peak
   - No duplicates in strict mountain arrays

### Approach Selection
**Chosen Approach:** Triple Binary Search  
**Why this approach?** 
- O(log n) time complexity
- Minimizes API calls (critical constraint)
- Handles all edge cases properly
- Efficient and optimal

## ⚡ Complexity Analysis
- **Time Complexity:** O(log n) - Three binary searches
- **Space Complexity:** O(1) - Only using constant extra space
- **API Calls:** ~3 * log₂(10,000) ≈ 40 calls (well under 100 limit)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to find the peak first using binary search

Then search left (ascending) part for target

If not found, search right (descending) part

Always return the minimum index when target appears in both sides

Carefully manage the API call count

🔗 Related Problems
Peak Index in a Mountain Array

Find Peak Element

Search in Rotated Sorted Array

Search in Rotated Sorted Array II


