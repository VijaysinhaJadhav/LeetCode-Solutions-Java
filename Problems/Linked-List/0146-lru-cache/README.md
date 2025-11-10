# 146. LRU Cache

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, Linked List, Design, Doubly-Linked List  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber, Oracle, TikTok

[LeetCode Link](https://leetcode.com/problems/lru-cache/)

Design a data structure that follows the constraints of a **Least Recently Used (LRU) cache**.

Implement the `LRUCache` class:

- `LRUCache(int capacity)` Initialize the LRU cache with **positive** size `capacity`.
- `int get(int key)` Return the value of the `key` if the key exists, otherwise return `-1`.
- `void put(int key, int value)` Update the value of the `key` if the `key` exists. Otherwise, add the `key-value` pair to the cache. If the number of keys exceeds the `capacity` from this operation, **evict** the least recently used key.

The functions `get` and `put` must each run in **O(1)** average time complexity.

**Example 1:**

Input

["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]

[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]

Output

[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation

LRUCache lRUCache = new LRUCache(2);

lRUCache.put(1, 1); // cache is {1=1}

lRUCache.put(2, 2); // cache is {1=1, 2=2}

lRUCache.get(1); // return 1

lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}

lRUCache.get(2); // returns -1 (not found)

lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}

lRUCache.get(1); // return -1 (not found)

lRUCache.get(3); // return 3

lRUCache.get(4); // return 4


**Constraints:**
- `1 <= capacity <= 3000`
- `0 <= key <= 10^4`
- `0 <= value <= 10^5`
- At most `2 * 10^5` calls will be made to `get` and `put`.

## 🧠 Thought Process

### Initial Thoughts
- Need O(1) time complexity for both get and put operations
- LRU eviction policy requires tracking usage order
- Need efficient way to move elements to front when accessed

### Key Insights
1. **HashMap for O(1) access**: Store key-value pairs for direct access
2. **Doubly Linked List for O(1) rearrangement**: Maintain usage order
3. **Combined approach**: HashMap points to nodes in linked list
4. **When accessing an element**: Move it to front (most recently used)
5. **When adding new element**: Add to front, remove from end if capacity exceeded

### Approach Selection
**Chosen Approach:** HashMap + Doubly Linked List  
**Why this approach?** 
- O(1) time complexity for both operations
- Efficient rearrangement of usage order
- Meets all problem constraints
- Industry standard for LRU implementation

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) for both get and put operations
- **Space Complexity:** O(capacity) - stores up to capacity elements

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The doubly linked list helps maintain the order of usage

HashMap provides O(1) access to any node

When a key is accessed, it's moved to the head (most recent)

When capacity is exceeded, the tail (least recent) is removed

Dummy head and tail nodes simplify edge case handling

🔗 Related Problems
LFU Cache

Design In-Memory File System

Design Search Autocomplete System

Max Stack
