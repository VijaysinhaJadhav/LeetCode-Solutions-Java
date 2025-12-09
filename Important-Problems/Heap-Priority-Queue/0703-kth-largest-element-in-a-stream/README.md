# 703. Kth Largest Element in a Stream

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Tree, Design, Binary Search Tree, Heap (Priority Queue), Binary Tree, Data Stream  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Adobe

[LeetCode Link](https://leetcode.com/problems/kth-largest-element-in-a-stream/)

Design a class to find the `kth` largest element in a stream. Note that it is the `kth` largest element in the sorted order, not the `kth` distinct element.

Implement `KthLargest` class:
- `KthLargest(int k, int[] nums)` Initializes the object with the integer `k` and the stream of integers `nums`.
- `int add(int val)` Appends the integer `val` to the stream and returns the element representing the `kth` largest element in the stream.

**Example 1:**

Input:

["KthLargest", "add", "add", "add", "add", "add"]

[[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]

Output:

[null, 4, 5, 5, 8, 8]

Explanation:

KthLargest kthLargest = new KthLargest(3, [4, 5, 8, 2]);

kthLargest.add(3); // return 4

kthLargest.add(5); // return 5

kthLargest.add(10); // return 5

kthLargest.add(9); // return 8

kthLargest.add(4); // return 8


**Constraints:**
- `1 <= k <= 10^4`
- `0 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `-10^4 <= val <= 10^4`
- At most `10^4` calls will be made to `add`.
- It is guaranteed that there will be at least `k` elements in the array when you search for the `kth` element.

## 🧠 Thought Process

### Initial Thoughts
- Need to efficiently find kth largest element after each addition
- Multiple approaches with different time/space trade-offs
- Key insight: Only need to track k largest elements, not the entire stream

### Key Insights
1. **Min-Heap Approach**: Maintain a min-heap of size k containing the k largest elements
2. **Heap Property**: The smallest element in the min-heap is the kth largest overall
3. **Efficient Updates**: When adding new element, compare with heap's minimum
4. **BST Alternative**: Can use balanced BST, but heap is simpler and efficient

### Approach Selection
**Chosen Approach:** Min-Heap (Priority Queue)  
**Why this approach?** 
- O(log k) time for add operations
- O(n log k) time for initialization
- O(k) space complexity
- Simple and efficient implementation
- Well-suited for stream processing

## ⚡ Complexity Analysis
- **Initialization:** O(n log k) - Building heap with n elements
- **Add Operation:** O(log k) - Heap insertion and removal
- **Space Complexity:** O(k) - Only store k elements

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use a min-heap to always have access to the kth largest element at the top

Maintain heap size exactly at k by removing smallest element when exceeding size

The kth largest element is always the smallest element in our min-heap of k largest elements

For initialization, add all elements but only keep k largest

🔗 Related Problems
Kth Largest Element in an Array

Find Median from Data Stream

Top K Frequent Elements

K Closest Points to Origin

Last Stone Weight
