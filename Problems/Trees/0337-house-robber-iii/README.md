# 337. House Robber III

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Tree, Depth-First Search, Dynamic Programming, Binary Tree  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg, Uber, Apple

[LeetCode Link](https://leetcode.com/problems/house-robber-iii/)

The thief has found himself a new place for his thievery again. There is only one entrance to this area, called the `root`.

Besides the `root`, each house has one and only one parent house. After a tour, the smart thief realized that all houses in this place form a binary tree. It will automatically contact the police if **two directly-linked houses are broken into on the same night**.

Given the `root` of the binary tree, return *the maximum amount of money the thief can rob without alerting the police*.

**Example 1:**

Input: root = [3,2,3,null,3,null,1]

Output: 7

Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.

3

/

2 3

\ \

3 1


**Example 2:**

Input: root = [3,4,5,1,3,null,1]

Output: 9

Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.

3

/

4 5

/ \ \

1 3 1


**Constraints:**
- The number of nodes in the tree is in the range `[1, 10^4]`.
- `0 <= Node.val <= 10^4`

## 🧠 Thought Process

### Initial Thoughts
- Cannot rob two directly connected houses (parent and child)
- Need to consider all possible combinations of robbing/not robbing each house
- Tree structure suggests DFS approach
- At each node, we have two choices: rob or not rob

### Key Insights
1. **Dynamic Programming on Trees**: Each node returns two values - rob this node, or skip this node
2. **Postorder Traversal**: Need results from children before making decision at parent
3. **State Definition**: For each node, calculate [rob, skip] where:
   - `rob` = node.val + skip left child + skip right child
   - `skip` = max(left child choices) + max(right child choices)
4. **Optimal Substructure**: Solution can be built from solutions of subtrees

### Approach Selection
**Chosen Approach:** DFS with DP (Postorder Traversal)  
**Why this approach?** 
- O(n) time complexity - visits each node once
- O(h) space complexity - recursion stack height
- Clean and intuitive implementation
- Naturally handles the constraint

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) - Each node is visited exactly once
- **Space Complexity:** O(h) - Where h is the height of the tree (recursion stack)

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The key is to return both possibilities (rob/skip) from each recursive call

When robbing a node, you cannot rob its direct children

When skipping a node, you can choose the best option from children

The solution uses postorder traversal (left → right → root)

🔗 Related Problems
House Robber

House Robber II

Paint House

Best Time to Buy and Sell Stock

Binary Tree Maximum Path Sum
