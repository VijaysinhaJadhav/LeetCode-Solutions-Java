# 706. Design HashMap

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Linked List, Design, Hash Function  
**Companies:** Amazon, Microsoft, Apple, Google, Bloomberg, Oracle, Adobe

[LeetCode Link](https://leetcode.com/problems/design-hashmap/)

Design a HashMap without using any built-in hash table libraries.

Implement the `MyHashMap` class:

- `MyHashMap()` initializes the object with an empty map.
- `void put(int key, int value)` inserts a `(key, value)` pair into the HashMap. If the `key` already exists in the map, update the corresponding `value`.
- `int get(int key)` returns the `value` to which the specified `key` is mapped, or `-1` if this map contains no mapping for the `key`.
- `void remove(key)` removes the `key` and its corresponding `value` if the map contains the mapping for the `key`.

**Example 1:**

Input

["MyHashMap", "put", "put", "get", "get", "put", "get", "remove", "get"]

[[], [1, 1], [2, 2], [1], [3], [2, 1], [2], [2], [2]]

Output

[null, null, null, 1, -1, null, 1, null, -1]

Explanation

MyHashMap myHashMap = new MyHashMap();

myHashMap.put(1, 1); // The map is now [[1,1]]

myHashMap.put(2, 2); // The map is now [[1,1], [2,2]]

myHashMap.get(1); // return 1, The map is now [[1,1], [2,2]]

myHashMap.get(3); // return -1 (i.e., not found), The map is now [[1,1], [2,2]]

myHashMap.put(2, 1); // The map is now [[1,1], [2,1]] (update the existing value)

myHashMap.get(2); // return 1, The map is now [[1,1], [2,1]]

myHashMap.remove(2); // remove the mapping for 2, The map is now [[1,1]]

myHashMap.get(2); // return -1 (i.e., not found), The map is now [[1,1]]


**Constraints:**
- `0 <= key, value <= 10^6`
- At most `10^4` calls will be made to `put`, `get`, and `remove`.

**Follow-up:** Please do not use the built-in HashMap library.

## 🧠 Thought Process

### Initial Thoughts
- Need to design a hash map from scratch
- Key operations: put, get, remove
- Handle collisions (multiple keys mapping to same bucket)
- Choose appropriate data structure for buckets

### Key Insights
1. **Hash Function**: Simple modulo operation works well
2. **Collision Handling**: 
   - Separate chaining with linked lists
   - Separate chaining with arrays
   - Open addressing (linear probing, quadratic probing)
3. **Load Factor**: Resize when load factor exceeds threshold
4. **Bucket Size**: Choose prime number to reduce collisions

### Approach Selection
**Chosen Approach:** Separate Chaining with Linked Lists  
**Why this approach?** 
- Simple to implement
- Handles collisions gracefully
- Good average-case performance
- Easy to understand and debug

## ⚡ Complexity Analysis
- **Time Complexity:** 
  - Average case: O(1) for all operations
  - Worst case: O(n) when all keys hash to same bucket
- **Space Complexity:** O(n + m) where n is number of elements, m is number of buckets

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Using a prime number for bucket size helps distribute keys more evenly

Linked list approach handles collisions by chaining

Need to handle key updates in put operation

Remove operation needs to find and delete the node

🔗 Related Problems
Design HashSet

LRU Cache

Insert Delete GetRandom O(1)
