# 997. Find the Town Judge

## 📋 Problem Statement
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Graph  
**Companies:** Amazon, Google, Microsoft, Facebook, Bloomberg

[LeetCode Link](https://leetcode.com/problems/find-the-town-judge/)

In a town, there are `n` people labeled from `1` to `n`. There is a rumor that one of these people is secretly the town judge.

If the town judge exists, then:
1. The town judge trusts nobody.
2. Everybody (except for the town judge) trusts the town judge.
3. There is exactly one person that satisfies properties 1 and 2.

You are given an array `trust` where `trust[i] = [aᵢ, bᵢ]` representing that the person labeled `aᵢ` trusts the person labeled `bᵢ`.

Return the label of the town judge if the town judge exists and can be identified, or return `-1` otherwise.

**Example 1:**

Input: n = 2, trust = [[1,2]]

Output: 2

Explanation: Person 1 trusts person 2, but person 2 trusts nobody.


**Example 2:**

Input: n = 3, trust = [[1,3],[2,3]]

Output: 3

Explanation: Person 1 and 2 trust person 3, but person 3 trusts nobody.


**Example 3:**

Input: n = 3, trust = [[1,3],[2,3],[3,1]]

Output: -1

Explanation: Person 3 trusts person 1, violating condition 1.


**Constraints:**
- `1 <= n <= 1000`
- `0 <= trust.length <= 10^4`
- `trust[i].length == 2`
- All the pairs of `trust` are **unique**.
- `aᵢ != bᵢ`
- `1 <= aᵢ, bᵢ <= n`

## 🧠 Thought Process

### Initial Thoughts
- Need to find a person who is trusted by everyone but trusts nobody
- This is a graph problem where trust relationships form edges
- Can model as directed graph: a → b means a trusts b
- The judge should have in-degree = n-1 and out-degree = 0

### Key Insights
1. **Degree Counting**: Track in-degree (trusted by) and out-degree (trusts others)
2. **Judge Conditions**: 
   - In-degree = n-1 (trusted by everyone else)
   - Out-degree = 0 (trusts nobody)
3. **Single Candidate**: Only one person can satisfy both conditions

### Approach Selection
**Chosen Approach:** Degree Counting with Arrays  
**Why this approach?** 
- O(n + t) time complexity where t is trust array length
- O(n) space complexity
- Simple and efficient
- Directly implements the problem conditions

## ⚡ Complexity Analysis
- **Time Complexity:** O(n + t) - Process n people and t trust relationships
- **Space Complexity:** O(n) - Two arrays of size n+1

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use two arrays: trustedBy (in-degree) and trustsOthers (out-degree)

The judge must have trustedBy[i] = n-1 and trustsOthers[i] = 0

Only one person can satisfy both conditions

Handle edge case where n=1 and trust array is empty

🔗 Related Problems
Find the Celebrity

Find Center of Star Graph

Minimum Number of Vertices to Reach All Nodes

Keys and Rooms

Number of Provinces
