# 706. Design HashMap

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Hash Table, Linked List, Design, Hash Function  
**Companies:** Amazon, Google, Microsoft, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/design-hashmap/)

Design a HashMap without using any built-in hash table libraries.

Implement the `MyHashMap` class:

- `MyHashMap()` initializes the object with an empty map.
- `void put(int key, int value)` inserts a `(key, value)` pair into the HashMap. If the `key` already exists in the map, update the corresponding `value`.
- `int get(int key)` returns the `value` to which the specified `key` is mapped, or `-1` if this map contains no mapping for the `key`.
- `void remove(int key)` removes the `key` and its corresponding `value` if the map contains the mapping for the `key`.

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

myHashMap.get(1); // return 1

myHashMap.get(3); // return -1 (not found)

myHashMap.put(2, 1); // The map is now [[1,1], [2,1]] (update existing value)

myHashMap.get(2); // return 1

myHashMap.remove(2); // remove the mapping for 2, The map is now [[1,1]]

myHashMap.get(2); // return -1 (not found)


**Constraints:**
- `0 <= key, value <= 10^6`
- At most `10^4` calls will be made to `put`, `get`, and `remove`.

## 🧠 Thought Process

### Initial Thoughts
- Need to design a HashMap from scratch without using built-in collections
- Must handle key-value pairs with efficient put, get, and remove operations
- Keys and values can range from 0 to 1,000,000
- Need to handle key collisions and updates

### Key Insights
1. **Array-based approach**: Since key range is limited (0-10^6), we can use an integer array of size 1,000,001 with -1 as "not found" indicator
2. **Hashing with chaining**: More practical approach using buckets and linked lists for collision resolution
3. **Entry class needed**: For chaining approach, need a class to store key-value pairs
4. **Update semantics**: put should update value if key already exists

### Approach Selection
**Chosen Approach:** Hashing with Chaining (More Practical)  
**Why this approach?** 
- More realistic implementation of a HashMap
- Handles collisions properly
- Memory efficient for sparse data
- Demonstrates fundamental hashing concepts

**Alternative Approach:** Array-based (Simple but Memory Intensive)
- Direct indexing with array of size 1,000,001
- O(1) operations but high memory usage
- Not practical for real-world scenarios

## ⚡ Complexity Analysis
- **Time Complexity:** O(n/b) average case, O(n) worst case for all operations, where n is number of elements and b is number of buckets
- **Space Complexity:** O(n + b) where n is data size, b is bucket count

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Using 1000 buckets provides good distribution for up to 10^4 operations

The Entry class is essential for storing key-value pairs in chains

Initialize array with -1 to handle "key not found" case

The chaining approach is more educational and practical

🔗 Related Problems
705. Design HashSet

146. LRU Cache

380. Insert Delete GetRandom O(1)
