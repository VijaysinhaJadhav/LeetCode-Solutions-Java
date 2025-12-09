# 705. Design HashSet

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Linked List, Design, Hash Function  
**Companies:** Amazon, Microsoft, Google, Apple, Bloomberg, Adobe

[LeetCode Link](https://leetcode.com/problems/design-hashset/)

Design a HashSet without using any built-in hash table libraries.

Implement the `MyHashSet` class:

- `MyHashSet()` initializes the object with an empty set.
- `void add(key)` inserts the value `key` into the HashSet.
- `bool contains(key)` returns whether the value `key` exists in the HashSet or not.
- `void remove(key)` removes the value `key` in the HashSet. If `key` does not exist in the HashSet, do nothing.

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

myHashSet.contains(3); // return False, (not found)

myHashSet.add(2); // set = [1, 2]

myHashSet.contains(2); // return True

myHashSet.remove(2); // set = [1]

myHashSet.contains(2); // return False, (already removed)


**Constraints:**
- `0 <= key <= 10^6`
- At most `10^4` calls will be made to `add`, `remove`, and `contains`.

**Follow-up:** Could you solve the problem without using the built-in HashSet library?

## 🧠 Thought Process

### Initial Thoughts
- Need to design a hash set from scratch
- Key operations: add, contains, remove
- Handle collisions (multiple keys mapping to same bucket)
- Choose appropriate data structure for buckets

### Key Insights
1. **Hash Function**: Simple modulo operation works well
2. **Collision Handling**: 
   - Separate chaining with linked lists
   - Separate chaining with arrays
   - Open addressing (linear probing)
3. **Load Factor**: Resize when load factor exceeds threshold
4. **Bucket Size**: Choose prime number to reduce collisions
5. **Simplification**: Only need to store keys, not key-value pairs

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

Add operation needs to check for duplicates

Remove operation needs to find and delete the node

🔗 Related Problems
Design HashMap

Contains Duplicate

Single Number
