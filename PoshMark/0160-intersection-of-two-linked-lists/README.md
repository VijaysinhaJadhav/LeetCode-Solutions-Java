# 160. Intersection of Two Linked Lists

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Hash Table, Linked List, Two Pointers  
**Companies:** Amazon, Microsoft, Apple, Google, Facebook, Bloomberg, Adobe, Uber, Oracle, TikTok

[LeetCode Link](https://leetcode.com/problems/intersection-of-two-linked-lists/)

Given the heads of two singly linked-lists `headA` and `headB`, return the node at which the two lists intersect. If the two linked lists have no intersection at all, return `null`.

The test cases are generated such that there are no cycles anywhere in the entire linked structure.

**Note** that the linked lists must **retain their original structure** after the function returns.

**Custom Judge:**
The inputs to the judge are given as follows (your program is **not** given these inputs):
- `intersectVal` - The value of the node where the intersection occurs (0 if no intersection).
- `listA` - The first linked list.
- `listB` - The second linked list.
- `skipA` - The number of nodes to skip ahead in listA to get to the intersection node.
- `skipB` - The number of nodes to skip ahead in listB to get to the intersection node.

The judge will then create the linked structure based on these inputs and pass the two heads, `headA` and `headB` to your program. If you correctly return the intersected node, then your solution will be **accepted**.

**Example 1:**

Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
Output: Intersected at '8'
Explanation: The intersected node's value is 8.
A: 4→1→8→4→5
B: 5→6→1→8→4→5
↑ intersection


**Example 2:**

Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
Output: Intersected at '2'
Explanation: The intersected node's value is 2.
A: 1→9→1→2→4
B: 3→2→4
↑ intersection


**Example 3:**

Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
Output: No intersection
Explanation: The two lists do not intersect.
A: 2→6→4
B: 1→5


**Constraints:**
- The number of nodes of `listA` is in the `m`.
- The number of nodes of `listB` is in the `n`.
- `1 <= m, n <= 3 * 10^4`
- `0 <= Node.val <= 10^5`
- `0 <= skipA <= m`
- `0 <= skipB <= n`
- `intersectVal` is `0` if `listA` and `listB` do not intersect.
- `intersectVal == listA[skipA] == listB[skipB]` if `listA` and `listB` intersect.

**Follow up:** Could you write a solution that runs in `O(m + n)` time and use only `O(1)` memory?

## 🧠 Thought Process

### Initial Thoughts
- Need to find where two linked lists merge (if they do)
- Cannot modify the lists (must retain original structure)
- Multiple approaches with different trade-offs

### Key Insights
1. **Brute Force:**
   - For each node in listA, check all nodes in listB
   - O(m*n) time, O(1) space - too slow for constraints
2. **Hash Set:**
   - Traverse listA, store nodes in hash set
   - Traverse listB, check if node in set
   - O(m+n) time, O(m) or O(n) space
3. **Two Pointers (Optimal):**
   - Two pointers start at headA and headB
   - When one reaches end, continue from other list's head
   - If they meet, that's intersection; if both reach end, no intersection
   - O(m+n) time, O(1) space
4. **Length Difference:**
   - Calculate lengths of both lists
   - Move longer list's pointer ahead by difference
   - Then move both pointers together to find intersection
   - O(m+n) time, O(1) space

### Approach Selection
**Primary Approach:** Two Pointers (Cycle Detection Style)  
**Why this approach?** 
- O(m+n) time complexity
- O(1) space complexity (meets follow-up requirement)
- Elegant and clever
- No need to calculate lengths

**Alternative:** Length Difference Approach  
**Why this approach?**
- Also O(m+n) time, O(1) space
- More intuitive for some people
- Explicit length calculation

## ⚡ Complexity Analysis
- **Time Complexity:** O(m+n) - Each list traversed at most twice
- **Space Complexity:** O(1) - Only a few pointers

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

  GNU nano 8.1                                                                         README.md                                                                         Modified
Explanation: The two lists do not intersect.
A: 2→6→4
B: 1→5


**Constraints:**
- The number of nodes of `listA` is in the `m`.
- The number of nodes of `listB` is in the `n`.
- `1 <= m, n <= 3 * 10^4`
- `0 <= Node.val <= 10^5`
- `0 <= skipA <= m`
- `0 <= skipB <= n`
- `intersectVal` is `0` if `listA` and `listB` do not intersect.
- `intersectVal == listA[skipA] == listB[skipB]` if `listA` and `listB` intersect.

**Follow up:** Could you write a solution that runs in `O(m + n)` time and use only `O(1)` memory?

##  M-  Thought Process

### Initial Thoughts
- Need to find where two linked lists merge (if they do)
- Cannot modify the lists (must retain original structure)
- Multiple approaches with different trade-offs

### Key Insights
1. **Brute Force:**
   - For each node in listA, check all nodes in listB
   - O(m*n) time, O(1) space - too slow for constraints
2. **Hash Set:**
   - Traverse listA, store nodes in hash set
   - Traverse listB, check if node in set
   - O(m+n) time, O(m) or O(n) space
3. **Two Pointers (Optimal):**
   - Two pointers start at headA and headB
   - When one reaches end, continue from other list's head
   - If they meet, that's intersection; if both reach end, no intersection
   - O(m+n) time, O(1) space
4. **Length Difference:**
   - Calculate lengths of both lists
   - Move longer list's pointer ahead by difference
   - Then move both pointers together to find intersection
   - O(m+n) time, O(1) space

### Approach Selection
**Primary Approach:** Two Pointers (Cycle Detection Style)
**Why this approach?**
- O(m+n) time complexity
- O(1) space complexity (meets follow-up requirement)
- Elegant and clever
- No need to calculate lengths

**Alternative:** Length Difference Approach
**Why this approach?**
- Also O(m+n) time, O(1) space
- More intuitive for some people
- Explicit length calculation

## ⚡ Complexity Analysis
- **Time Complexity:** O(m+n) - Each list traversed at most twice
- **Space Complexity:** O(1) - Only a few pointers

##  ~M Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Two pointers technique is elegant: a+b = b+a

If no intersection, both pointers reach null at same time

Works because total distance traveled is same for both pointers

Handle edge cases: empty lists, single node lists

🔗 Related Problems
141. Linked List Cycle

142. Linked List Cycle II

206. Reverse Linked List

21. Merge Two Sorted Lists

234. Palindrome Linked List

876. Middle of the Linked List
