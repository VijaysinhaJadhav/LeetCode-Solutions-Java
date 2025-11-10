# 981. Time Based Key-Value Store

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, String, Binary Search, Design  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Apple, Uber, Twitter

[LeetCode Link](https://leetcode.com/problems/time-based-key-value-store/)

Design a time-based key-value data structure that can store multiple values for the same key at different time stamps and retrieve the key's value at a certain timestamp.

Implement the `TimeMap` class:
- `TimeMap()` Initializes the object.
- `void set(String key, String value, int timestamp)` Stores the key `key` with the value `value` at the given time `timestamp`.
- `String get(String key, int timestamp)` Returns a value such that `set` was called previously with `timestamp_prev <= timestamp`. If there are multiple such values, it returns the value associated with the largest `timestamp_prev`. If there are no values, it returns `""`.

**Example 1:**

Input:

["TimeMap", "set", "get", "get", "set", "get", "get"]

[[], ["foo", "bar", 1], ["foo", 1], ["foo", 3], ["foo", "bar2", 4], ["foo", 4], ["foo", 5]]

Output:

[null, null, "bar", "bar", null, "bar2", "bar2"]

Explanation:

TimeMap timeMap = new TimeMap();

timeMap.set("foo", "bar", 1); // store the key "foo" and value "bar" along with timestamp = 1.

timeMap.get("foo", 1); // return "bar"

timeMap.get("foo", 3); // return "bar", since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 is "bar".

timeMap.set("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4.

timeMap.get("foo", 4); // return "bar2"

timeMap.get("foo", 5); // return "bar2"


**Constraints:**
- `1 <= key.length, value.length <= 100`
- `key` and `value` consist of lowercase English letters and digits.
- `1 <= timestamp <= 10^7`
- All the timestamps `timestamp` of `set` are strictly increasing.
- At most `2 * 10^5` calls will be made to `set` and `get`.

## 🧠 Thought Process

### Initial Thoughts
- Need to store multiple values for the same key at different timestamps
- Timestamps are strictly increasing, which is important for optimization
- For `get`, need to find the value with largest timestamp ≤ given timestamp
- This suggests using binary search for efficient retrieval

### Key Insights
1. **Data Structure**: Use HashMap with key → list of (timestamp, value) pairs
2. **Binary Search**: Since timestamps are strictly increasing, the list is sorted
3. **Largest ≤ timestamp**: Need to find the rightmost element ≤ target timestamp
4. **Efficiency**: Binary search gives O(log n) for get operations

### Approach Selection
**Chosen Approach:** HashMap + Binary Search  
**Why this approach?** 
- O(1) average time for set operations
- O(log n) time for get operations (n = number of timestamps for that key)
- Efficient for the constraints (2 * 10^5 operations)
- Leverages the strictly increasing timestamp property

## ⚡ Complexity Analysis
- **Time Complexity:**
  - `set`: O(1) average case
  - `get`: O(log n) where n is number of timestamps for the key
- **Space Complexity:** O(n) where n is total number of set operations

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key insight is that timestamps are strictly increasing, making the lists naturally sorted

Binary search is perfect for finding the largest timestamp ≤ target

Can use Collections.binarySearch or implement custom binary search

The problem combines hash maps with binary search on sorted lists

🔗 Related Problems
Online Election

Snapshot Array

Design Underground System

Design Parking System
