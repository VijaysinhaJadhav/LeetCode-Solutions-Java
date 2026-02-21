# 721. Accounts Merge

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Hash Table, String, Depth-First Search, Breadth-First Search, Union Find  
**Companies:** Facebook (Meta), Amazon, Google, Microsoft, Nutanix, Bloomberg  

[LeetCode Link](https://leetcode.com/problems/accounts-merge/)

Given a list of `accounts` where each element `accounts[i]` is a list of strings, where the first element `accounts[i][0]` is a **name**, and the rest of the elements are **emails** representing emails of the account.

We would like to merge these accounts. Two accounts definitely belong to the same person if there is **some common email** to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails **in sorted order**. The accounts themselves can be returned in **any order**.

**Example 1:**

Input: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],

["John","johnsmith@mail.com","john00@mail.com"],

["Mary","mary@mail.com"],

["John","johnnybravo@mail.com"]]

Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],

["Mary","mary@mail.com"],

["John","johnnybravo@mail.com"]]

Explanation:

The first and second John's are the same person as they have the common email "johnsmith@mail.com".

The third John and Mary are different people as none of their email addresses are used by other accounts.


**Example 2:**

Input: accounts = [["Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"],

["Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"],

["Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"],

["Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"],

["Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"]]

Output: [["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],

["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],

["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],

["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],

["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]]


**Constraints:**
- `1 <= accounts.length <= 1000`
- `2 <= accounts[i].length <= 10`
- `1 <= accounts[i][j].length <= 30`
- `accounts[i][0]` consists of English letters.
- `accounts[i][j]` (for j > 0) is a valid email.

## 🧠 Thought Process

### Problem Understanding
We have multiple accounts, each with a name and multiple emails. If two accounts share at least one email, they belong to the same person. We need to merge such accounts and output each person's unique emails in sorted order.

### Key Insights
1. **Graph Problem**: Accounts are connected through shared emails – this forms connected components [citation:3]
2. **Two Possible Approaches**:
   - **Union-Find (Disjoint Set Union)**: Merge accounts based on email connections [citation:4][citation:5]
   - **DFS/BFS**: Build an email graph and traverse connected components [citation:7]
3. **Name Handling**: Same name doesn't guarantee same person, but merged accounts will have the same name
4. **Email Mapping**: Need to track which account each email belongs to

### Approach Selection
**Chosen Approach:** Union-Find (Disjoint Set Union)  
**Why this approach?**
- Classic solution for connectivity problems [citation:4][citation:8]
- Efficient O(α(n)) operations with path compression
- Clean separation of concerns (union operations, then result building)
- Handles up to 1000 accounts efficiently

**Alternative Approaches:**
- **DFS/BFS**: Build graph of emails, traverse to find connected components [citation:7]
- **HashMap + Sorting**: Group emails by name first (less accurate)

## ⚡ Complexity Analysis
- **Time Complexity:** O(NK log(NK)) where N = number of accounts, K = max emails per account
  - Building data structures: O(NK)
  - Union operations: O(α(N)) ≈ O(1)
  - Sorting emails: O(M log M) where M = total unique emails
- **Space Complexity:** O(NK) for storing email mappings and parent arrays

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Union-Find is the most efficient and intuitive solution 

Key steps:

Map each email to an account index
Union accounts that share emails
Group emails by root account
Sort emails and format output
Path compression in find() optimizes performance

Use TreeSet for automatic sorting, or sort after collection 

🔗 Related Problems
Number of Islands (DFS/BFS connectivity)
Number of Provinces (Union-Find)
Redundant Connection (Union-Find)
Number of Connected Components in an Undirected Graph
Longest Consecutive Sequence (Union-Find)
