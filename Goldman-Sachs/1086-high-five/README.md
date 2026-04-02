# 1086. High Five

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Sorting, Heap (Priority Queue)  
**Companies:** Amazon, Google, Microsoft, Uber, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/high-five/)

Given a list of `items` where `items[i] = [ID_i, score_i]` represents the score of the student with ID `ID_i`, return the **average** of the **top five scores** for each student. The average should be calculated using **integer division** (floor).

Return the result as an array of `[ID, average]` pairs sorted by ID in **ascending order**.

**Example 1:**

Input: items = [[1,91],[1,92],[2,93],[2,97],[1,60],[2,77],[1,65],[1,87],[1,100],[2,100],[2,76]]
Output: [[1,87],[2,88]]
Explanation:
Student 1: top five scores = [100,92,91,87,65] → average = 87
Student 2: top five scores = [100,97,93,77,76] → average = 88


**Example 2:**

Input: items = [[1,100],[7,100],[1,100],[7,100],[1,100],[7,100],[1,100],[7,100],[1,100],[7,100]]
Output: [[1,100],[7,100]]


**Constraints:**
- `1 <= items.length <= 1000`
- `items[i].length == 2`
- `1 <= ID_i <= 1000`
- `0 <= score_i <= 100`
- For each student, there are at least 5 scores.

## 🧠 Thought Process

### Problem Understanding
We need to:
1. Group scores by student ID
2. For each student, take only the top 5 scores
3. Calculate the average using integer division (floor)
4. Return results sorted by ID

### Key Insights
1. **Grouping**: Use a map from student ID to a data structure storing scores
2. **Top K Scores**: Use a min-heap to keep only the top 5 scores efficiently
3. **Sorting**: After grouping, sort results by ID
4. **Integer Division**: Use `sum / 5` (floor division)

### Approach Selection
**Chosen Approach:** HashMap + Min-Heap  
**Why this approach?**
- O(n log 5) = O(n) time complexity
- Maintains only top 5 scores per student
- Efficient memory usage

**Alternative Approaches:**
- **HashMap + List + Sorting**: Sort all scores per student (O(n log n))
- **PriorityQueue for each student**: Clean and efficient

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log 5) = O(n) where n = number of scores
- **Space Complexity:** O(m × 5) = O(m) where m = number of students

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use PriorityQueue (min-heap) to keep only top 5 scores

When heap size > 5, remove the smallest element

After processing, heap contains the top 5 scores

Integer division automatically floors the result

🔗 Related Problems
Top K Frequent Elements
Kth Largest Element in a Stream
K Closest Points to Origin
Last Stone Weight
Kth Largest Element in an Array
