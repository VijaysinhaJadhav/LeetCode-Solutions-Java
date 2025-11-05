# 271. Encode and Decode Strings

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** String, Design, Data Stream  
**Companies:** Google, Amazon, Microsoft, Facebook, Apple, LinkedIn

[LeetCode Link](https://leetcode.com/problems/encode-and-decode-strings/)

Design an algorithm to encode a list of strings to a single string. The encoded string is then sent over the network and is decoded back to the original list of strings.

Please implement `encode` and `decode` methods.

**Example 1:**

Input: ["hello", "world"]

Output: ["hello", "world"]

Explanation:

One possible encode method is: "5:hello5:world"


**Example 2:**

Input: [""]

Output: [""]


**Constraints:**
- `0 <= strs.length <= 200`
- `0 <= strs[i].length <= 200`
- `strs[i]` contains any possible characters out of 256 valid ASCII characters.

## 🧠 Thought Process

### Initial Thoughts
- Need to serialize a list of strings into a single string
- Must be able to deserialize back to the original list
- The challenge is handling any characters including delimiters
- Multiple encoding strategies with different trade-offs

### Key Insights
1. **Length Prefix**: Encode each string with its length followed by delimiter and content
2. **Escape Characters**: Use escape sequences for special characters
3. **Non-ASCII Delimiters**: Use Unicode characters unlikely to appear in input
4. **Chunked Transfer**: Similar to HTTP chunked encoding

### Approach Selection
**Chosen Approach:** Length Prefix with Format "length:string"  
**Why this approach?** 
- Simple and efficient
- Handles any ASCII characters
- Easy to implement and understand
- Similar to real-world protocols like HTTP

## ⚡ Complexity Analysis
- **Time Complexity:** O(n) for both encode and decode, where n is total characters
- **Space Complexity:** O(n) for the encoded string

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
The length prefix approach is robust and handles empty strings

Using a non-numeric delimiter (":") makes parsing straightforward

For production use, consider error handling and validation

The solution assumes well-formed input as per problem constraints

🔗 Related Problems
297. Serialize and Deserialize Binary Tree

535. Encode and Decode TinyURL

428. Serialize and Deserialize N-ary Tree

443. String Compression
