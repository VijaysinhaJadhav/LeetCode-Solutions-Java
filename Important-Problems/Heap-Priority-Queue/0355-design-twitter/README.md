# 355. Design Twitter

## 📋 Problem Statement
**Difficulty:** Medium  
**Topics:** Hash Table, Linked List, Design, Heap (Priority Queue)  
**Companies:** Twitter, Google, Amazon, Microsoft, Facebook, Apple, Bloomberg

[LeetCode Link](https://leetcode.com/problems/design-twitter/)

Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, and is able to see the 10 most recent tweets in the user's news feed.

Implement the `Twitter` class:

- `Twitter()` Initializes your twitter object.
- `void postTweet(int userId, int tweetId)` Composes a new tweet with ID `tweetId` by the user `userId`. Each call to this function will be made with a unique `tweetId`.
- `List<Integer> getNewsFeed(int userId)` Retrieves the 10 most recent tweet IDs in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user themselves. Tweets must be **ordered from most recent to least recent**.
- `void follow(int followerId, int followeeId)` The user with ID `followerId` started following the user with ID `followeeId`.
- `void unfollow(int followerId, int followeeId)` The user with ID `followerId` started unfollowing the user with ID `followeeId`.

**Example 1:**

Input

["Twitter", "postTweet", "getNewsFeed", "follow", "postTweet", "getNewsFeed", "unfollow", "getNewsFeed"]

[[], [1, 5], [1], [1, 2], [2, 6], [1], [1, 2], [1]]

Output

[null, null, [5], null, null, [6, 5], null, [5]]

Explanation

Twitter twitter = new Twitter();

twitter.postTweet(1, 5); // User 1 posts a new tweet (id = 5).

twitter.getNewsFeed(1); // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]

twitter.follow(1, 2); // User 1 follows user 2.

twitter.postTweet(2, 6); // User 2 posts a new tweet (id = 6).

twitter.getNewsFeed(1); // User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.

twitter.unfollow(1, 2); // User 1 unfollows user 2.

twitter.getNewsFeed(1); // User 1's news feed should return a list with 1 tweet id -> [5], since user 1 is no longer following user 2.


**Constraints:**
- `1 <= userId, followerId, followeeId <= 500`
- `0 <= tweetId <= 10^4`
- All the tweets have **unique** IDs.
- At most `3 * 10^4` calls will be made to `postTweet`, `getNewsFeed`, `follow`, and `unfollow`.

## 🧠 Thought Process

### Initial Thoughts
- Need to design data structures to store tweets, followers, and user relationships
- Tweets need to be ordered by time (most recent first)
- News feed shows tweets from user + followed users
- Need efficient retrieval of top 10 most recent tweets

### Key Insights
1. **Tweet Storage**: Each tweet needs timestamp for ordering
2. **User Relationships**: Follow/unfollow operations need to be O(1)
3. **News Feed Generation**: Need to merge tweets from multiple users
4. **Efficiency**: getNewsFeed is called frequently, needs to be efficient

### Approach Selection
**Chosen Approach:** HashMap + Linked List + Max Heap  
**Why this approach?** 
- O(1) for follow/unfollow/postTweet
- O(k log n) for getNewsFeed where k is number of followed users
- Scalable design
- Clean separation of concerns

## ⚡ Complexity Analysis
- **postTweet**: O(1)
- **getNewsFeed**: O(k log k) where k is number of followed users
- **follow/unfollow**: O(1)
- **Space Complexity**: O(n + m) where n is tweets, m is follow relationships

## 🔍 Solution Code

```java
// See Solution.java for the complete implementation

📝 Notes
Use global timestamp to order tweets

Each user maintains their own tweet list (linked list for O(1) insertion)

Follow relationships stored in hash sets

News feed uses max heap to get most recent tweets

Always include user's own tweets in their feed

🔗 Related Problems
Merge k Sorted Lists

Top K Frequent Words

Time Based Key-Value Store

Design A Leaderboard
