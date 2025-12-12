# 295. Find Median from Data Stream

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Two Pointers, Design, Sorting, Heap (Priority Queue), Data Stream  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/find-median-from-data-stream/)

The **median** is the middle value in an ordered integer list. If the size of the list is even, there is no middle value, and the median is the mean of the two middle values.

- For example, for `arr = [2,3,4]`, the median is `3`.
- For example, for `arr = [2,3]`, the median is `(2 + 3) / 2 = 2.5`.

Implement the MedianFinder class:

- `MedianFinder()` initializes the `MedianFinder` object.
- `void addNum(int num)` adds the integer `num` from the data stream to the data structure.
- `double findMedian()` returns the median of all elements so far. Answers within `10^-5` of the actual answer will be accepted.

**Example 1:**

Input

["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]

[[], [1], [2], [], [3], []]

Output

[null, null, null, 1.5, null, 2.0]

Explanation

MedianFinder medianFinder = new MedianFinder();

medianFinder.addNum(1); // arr = [1]

medianFinder.addNum(2); // arr = [1, 2]

medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)

medianFinder.addNum(3); // arr[1, 2, 3]

medianFinder.findMedian(); // return 2.0


**Constraints:**
- `-10^5 <= num <= 10^5`
- There will be at least one element in the data structure before calling `findMedian`.
- At most `5 * 10^4` calls will be made to `addNum` and `findMedian`.

## 🧠 Thought Process

### Initial Thoughts
- Need to maintain running median of a data stream
- Median requires ordered data for O(1) access to middle elements
- Sorting after each addition is O(n log n) - too slow
- Need more efficient data structure

### Key Insights
1. **Two Heaps Approach**:
   - Max heap for lower half (contains smaller numbers)
   - Min heap for upper half (contains larger numbers)
   - Balance heaps so their sizes differ by at most 1
   - Median is either top of larger heap or average of both tops

2. **Invariant**:
   - All elements in maxHeap ≤ all elements in minHeap
   - |size(maxHeap) - size(minHeap)| ≤ 1
   - If total elements odd, median is top of larger heap
   - If total elements even, median is average of both tops

### Approach Selection
**Chosen Approach:** Two Heaps (Max Heap + Min Heap)  
**Why this approach?** 
- O(log n) for addNum (heap insertion)
- O(1) for findMedian (heap peek)
- Elegant and efficient solution
- Handles all cases correctly

## ⚡ Complexity Analysis
- **addNum:** O(log n) - heap insertion
- **findMedian:** O(1) - heap peek operations
- **Space Complexity:** O(n) - storing all elements

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use Collections.reverseOrder() for max heap in Java

Always maintain size balance: maxHeap can have at most one more element than minHeap

When adding new number, compare with heap tops to decide where to add

Handle integer overflow when calculating average

🔗 Related Problems
Sliding Window Median

Median of Two Sorted Arrays

Kth Largest Element in a Stream

Moving Average from Data Stream
