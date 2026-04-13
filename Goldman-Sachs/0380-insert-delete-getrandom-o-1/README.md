# 380. Insert Delete GetRandom O(1)

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, Design, Randomized  
**Companies:** Amazon, Google, Microsoft, Facebook, Apple, Bloomberg, Uber  

[LeetCode Link](https://leetcode.com/problems/insert-delete-getrandom-o1/)

Implement the `RandomizedSet` class:

- `RandomizedSet()` Initializes the `RandomizedSet` object.
- `bool insert(int val)` Inserts an item `val` into the set if not present. Returns `true` if the item was not present, `false` otherwise.
- `bool remove(int val)` Removes an item `val` from the set if present. Returns `true` if the item was present, `false` otherwise.
- `int getRandom()` Returns a random element from the current set of elements. Each element must have the **same probability** of being returned.

You must implement the functions of the class such that each function works in **average O(1)** time complexity.

**Example 1:**

Input
["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
[[], [1], [2], [2], [], [1], [2], []]

Output
[null, true, false, true, 2, true, false, 2]

Explanation
RandomizedSet randomizedSet = new RandomizedSet();
randomizedSet.insert(1); // Inserts 1 to the set. Returns true as 1 was inserted successfully.
randomizedSet.remove(2); // Returns false as 2 does not exist in the set.
randomizedSet.insert(2); // Inserts 2 to the set, returns true. Set now contains [1,2].
randomizedSet.getRandom(); // getRandom() should return either 1 or 2 randomly.
randomizedSet.remove(1); // Removes 1 from the set, returns true. Set now contains [2].
randomizedSet.insert(2); // 2 was already in the set, so return false.
randomizedSet.getRandom(); // Since 2 is the only number in the set, getRandom() will always return 2.


**Constraints:**
- `-2^31 <= val <= 2^31 - 1`
- At most `2 * 10^5` calls will be made to `insert`, `remove`, and `getRandom`.

## 🧠 Thought Process

### Problem Understanding
We need a data structure that supports:
- Insert (if not exists)
- Delete (if exists)
- Get random element with uniform probability
- All operations in O(1) average time

### Key Insights
1. **HashMap**: O(1) lookup for insert/remove
2. **ArrayList**: O(1) random access for getRandom
3. **Combined Approach**: Store values in ArrayList, map value → index in HashMap
4. **Swap Trick**: When removing, swap with last element to maintain O(1) deletion

### Approach Selection
**Chosen Approach:** HashMap + ArrayList  
**Why this approach?**
- O(1) average for all operations
- ArrayList provides O(1) random access
- HashMap provides O(1) lookup by value
- Swap with last element avoids shifting

**Alternative Approaches:**
- **HashSet + Random sampling**: O(n) for getRandom
- **Tree-based structures**: O(log n) operations

## ⚡ Complexity Analysis
- **Time Complexity:** O(1) average for insert, remove, getRandom
- **Space Complexity:** O(n) where n = number of elements

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use ArrayList to store values and HashMap to store value → index

For remove, get index from map, swap with last element, remove last

For getRandom, use Random.nextInt(list.size())

Update map after swap to reflect new index

🔗 Related Problems
Insert Delete GetRandom O(1) (this problem)
Insert Delete GetRandom O(1) - Duplicates allowed
Random Pick with Blacklist
Random Flip Matrix
Random Pick Index
