# 1029. Two City Scheduling

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Array, Greedy, Sorting  
**Companies:** Amazon, Google, Microsoft, Bloomberg, Uber

[LeetCode Link](https://leetcode.com/problems/two-city-scheduling/)

A company is planning to interview `2n` people. Given the array `costs` where `costs[i] = [aCost_i, bCost_i]`, the cost of flying the `i`-th person to city `a` is `aCost_i`, and the cost of flying the `i`-th person to city `b` is `bCost_i`.

Return *the minimum cost to fly every person to a city* such that exactly `n` people arrive in each city.

**Example 1:**

Input: costs = [[10,20],[30,200],[400,50],[30,20]]

Output: 110

Explanation:

First person goes to city A for cost 10.

Second person goes to city A for cost 30.

Third person goes to city B for cost 50.

Fourth person goes to city B for cost 20.

Total cost = 10 + 30 + 50 + 20 = 110


**Example 2:**

Input: costs = [[259,770],[448,54],[926,667],[184,139],[840,118],[577,469]]

Output: 1859


**Example 3:**

Input: costs = [[515,563],[451,713],[537,709],[343,819],[855,779],[457,60],[650,359],[631,42]]

Output: 3086


**Constraints:**
- `2 * n == costs.length`
- `2 <= costs.length <= 100`
- `1 <= aCost_i, bCost_i <= 1000`

## 🧠 Thought Process

### Initial Thoughts
- Need to send exactly n people to city A and n people to city B
- For each person, we have two choices: send to A or send to B
- Need to minimize total cost while maintaining balance

### Key Insights
1. **Cost Difference Approach**: Calculate difference `costA - costB` for each person
2. **Sorting Strategy**: Sort people by their cost difference
3. **Greedy Assignment**: 
   - First n people with most negative difference (cheaper to go to A) → send to A
   - Last n people with most positive difference (cheaper to go to B) → send to B
4. **Alternative**: Can also think as "how much extra we pay if we choose A over B"

### Approach Selection
**Chosen Approach:** Greedy with Sorting by Cost Difference  
**Why this approach?** 
- O(n log n) time complexity
- O(1) space (or O(n) if we don't modify input)
- Simple and intuitive
- Guarantees optimal solution

## ⚡ Complexity Analysis
- **Time Complexity:** O(n log n) - Sorting dominates
- **Space Complexity:** O(1) or O(n) depending on implementation

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Sort by costA - costB difference

Negative difference means cheaper to go to city A

Positive difference means cheaper to go to city B

Send first half to A, second half to B

🔗 Related Problems
Candy

Queue Reconstruction by Height

Assign Cookies

Task Scheduler

Advantage Shuffle
