# 705. Design HashSet

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Hash Table, Linked List, Design, Hash Function  
**Companies:** Amazon, Google, Microsoft, Apple

[LeetCode Link](https://leetcode.com/problems/design-hashset/)

Design a HashSet without using any built-in hash table libraries.

Implement `MyHashSet` class:

- `void add(key)` Inserts the value `key` into the HashSet.
- `bool contains(key)` Returns whether the value `key` exists in the HashSet or not.
- `void remove(key)` Removes the value `key` in the HashSet. If `key` does not exist in the HashSet, do nothing.

**Example 1:**

Input
["MyHashSet", "add", "add", "contains", "contains", "add", "contains", "remove", "contains"]
[[], [1], [2], [1], [3], [2], [2], [2], [2]]
Output
[null, null, null, true, false, null, true, null, false]

Explanation
MyHashSet myHashSet = new MyHashSet();
myHashSet.add(1); // set = [1]
myHashSet.add(2); // set = [1, 2]
myHashSet.contains(1); // return True
myHashSet.contains(3); // return False
myHashSet.add(2); // set = [1, 2]
myHashSet.contains(2); // return True
myHashSet.remove(2); // set = [1]
myHashSet.contains(2); // return False


**Constraints:**
- `0 <= key <= 10^6`
- At most `10^4` calls will be made to `add`, `remove`, and `contains`.

## 🧠 Thought Process

### Initial Thoughts
- Need to design a HashSet from scratch without using built-in collections
- Must handle key operations: add, remove, contains efficiently
- Keys can range from 0 to 1,000,000
- Need to consider time and space complexity trade-offs

### Key Insights
1. **Array-based approach**: Since key range is limited (0-10^6), we can use a boolean array of size 1,000,001
2. **Hashing with chaining**: More memory-efficient approach using buckets and linked lists
3. **Load factor considerations**: For chaining approach, need to choose appropriate number of buckets
4. **Hash function**: Simple modulo operation works well for distribution

### Approach Selection
**Chosen Approach:** Array-based (Simple and Efficient)  
**Why this approach?** 
- Keys are constrained to 0-1,000,000 range
- O(1) time complexity for all operations
- Simple implementation with minimal code
- Memory usage is acceptable (1MB for boolean array)

**Alternative Approach:** Hashing with Chaining  
- More memory efficient for sparse data
- Better for unbounded key ranges
- More complex implementation

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) for all operations (add, remove, contains)
- **Space Complexity:** O(1) - fixed size array regardless of input (1,000,001 elements)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The array-based approach is optimal given the constraints

Using boolean array saves space compared to integer array

For unbounded key ranges, consider hashing with chaining approach

The simplicity makes this solution easy to understand and maintain

🔗 Related Problems
706. Design HashMap

380. Insert Delete GetRandom O(1)

146. LRU Cache
