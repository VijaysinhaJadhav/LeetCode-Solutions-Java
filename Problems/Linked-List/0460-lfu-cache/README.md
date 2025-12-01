# 460. LFU Cache

## 📋 Problem Statement
**Difficulty:** Hard  
**Topics:** Hash Table, Linked List, Design, Doubly-Linked List  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/lfu-cache/)

Design and implement a data structure for a **Least Frequently Used (LFU)** cache.

Implement the `LFUCache` class:

- `LFUCache(int capacity)` Initializes the object with the `capacity` of the data structure.
- `int get(int key)` Gets the value of the `key` if the `key` exists in the cache. Otherwise, returns `-1`.
- `void put(int key, int value)` Update the value of the `key` if present, or inserts the `key` if not already present. When the cache reaches its `capacity`, it should invalidate and remove the **least frequently used** key before inserting a new item. In case of a tie (i.e., two or more keys with the same frequency), the **least recently used** key would be invalidated.

To determine the least frequently used key, a **use counter** is maintained for each key in the cache. The key with the smallest use counter is the least frequently used key.

When a key is first inserted into the cache, its use counter is set to `1` (due to the `put` operation). The use counter for a key in the cache is incremented either a `get` or `put` operation is called on it.

The functions `get` and `put` must each run in **O(1)** average time complexity.

**Example 1:**

Input:

["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]

[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]

Output:

[null, null, null, 1, null, -1, 3, null, -1, 3, 4]

Explanation:

LFUCache lfu = new LFUCache(2);

lfu.put(1, 1); // cache=[1,_], cnt(1)=1

lfu.put(2, 2); // cache=[2,1], cnt(2)=1, cnt(1)=1

lfu.get(1); // return 1, cache=[1,2], cnt(2)=1, cnt(1)=2

lfu.put(3, 3); // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.

// cache=[3,1], cnt(3)=1, cnt(1)=2

lfu.get(2); // return -1 (not found)

lfu.get(3); // return 3, cache=[3,1], cnt(3)=2, cnt(1)=2

lfu.put(4, 4); // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.

// cache=[4,3], cnt(4)=1, cnt(3)=2

lfu.get(1); // return -1 (not found)

lfu.get(3); // return 3, cache=[3,4], cnt(3)=3, cnt(4)=1

lfu.get(4); // return 4, cache=[3,4], cnt(3)=3, cnt(4)=2


**Constraints:**
- `0 <= capacity <= 10^4`
- `0 <= key <= 10^5`
- `0 <= value <= 10^9`
- At most `2 * 10^5` calls will be made to `get` and `put`.

## 🧠 Thought Process

### Initial Thoughts
- Need O(1) time for both get and put operations
- Must track both frequency and recency of usage
- When capacity is full, need to evict LFU item (LRU tiebreaker)
- Need efficient data structures to maintain ordering

### Key Insights
1. **Double HashMap + Doubly Linked List Design**:
   - One HashMap for key → node mapping (for O(1) access)
   - One HashMap for frequency → DLL (for O(1) frequency group access)
   - Each frequency level maintains its own LRU order using DLL

2. **Frequency Groups**:
   - Group nodes by their frequency count
   - Each group maintains LRU order (most recent at head, least recent at tail)
   - When frequency increases, node moves to next frequency group

3. **Min Frequency Tracking**:
   - Track current minimum frequency to quickly find LFU items
   - Update min frequency when groups become empty

### Approach Selection
**Chosen Approach:** HashMap + Doubly Linked List Groups  
**Why this approach?** 
- O(1) time for get and put operations
- Efficient frequency and recency tracking
- Clean separation of concerns
- Scalable and maintainable

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) for both get() and put()
- **Space Complexity:** O(capacity) for storing cache elements

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key challenge is maintaining O(1) operations while tracking both frequency and recency

Using frequency groups with DLLs allows efficient promotion of nodes

Always update min frequency when groups become empty

Handle capacity = 0 edge case

🔗 Related Problems
LRU Cache

All O(1) Data Structure

Design In-Memory File System

Design Search Autocomplete System

