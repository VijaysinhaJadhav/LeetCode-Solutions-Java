# 1325. Delete Leaves With a Given Value

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Tree, Depth-First Search, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook

[LeetCode Link](https://leetcode.com/problems/delete-leaves-with-a-given-value/)

Given a binary tree `root` and an integer `target`, delete all the **leaf nodes** with value `target`.

Note that once you delete a leaf node with value `target`, if its parent node becomes a leaf node and has the value `target`, it should also be deleted (you need to continue doing that until you cannot).

**Example 1:**

Input: root = [1,2,3,2,null,2,4], target = 2

Output: [1,null,3,null,4]

Explanation:

1 1

/ \

2 3 → 3

\ \

2 4 4


**Example 2:**

Input: root = [1,3,3,3,2], target = 3

Output: [1,3,null,null,2]

Explanation:

1 1

/ \ /

3 3 → 3

/ \ /

3 2 2


**Example 3:**

Input: root = [1,2,null,2,null,2], target = 2

Output: [1]

Explanation:

1 1

/ /

2 →

/

2

/

2


**Constraints:**
- The number of nodes in the tree is in the range `[1, 3000]`.
- `1 <= Node.val, target <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Need to delete leaf nodes with target value
- After deletion, parent might become a new leaf that also needs deletion
- This suggests a bottom-up approach (postorder traversal)
- Need to handle the case where entire tree gets deleted

### Key Insights
1. **Postorder Traversal**: Process children before parent (bottom-up)
2. **Leaf Identification**: A node becomes a leaf when both children are null (or deleted)
3. **Recursive Deletion**: Delete node if it becomes a leaf with target value after processing children
4. **Multiple Passes**: May need multiple deletions as parents become new leaves

### Approach Selection
**Chosen Approach:** Recursive Postorder Traversal  
**Why this approach?** 
- O(n) time complexity - visits each node once
- O(h) space complexity - recursion stack height
- Naturally handles the cascading deletion requirement
- Clean and intuitive implementation

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each node is visited exactly once
- **Space Complexity:** O(h) - Where h is the height of the tree (recursion stack)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to process children first, then check if current node should be deleted

A node should be deleted if it becomes a leaf (both children null) and has target value

Return null to delete a node, otherwise return the node itself

The solution handles cascading deletions naturally through recursion

🔗 Related Problems
Binary Tree Pruning

Insufficient Nodes in Root to Leaf Paths

Delete Nodes And Return Forest

Find Elements in a Contaminated Binary Tree
