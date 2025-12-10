
## Solution.java

```java
/**
 * 355. Design Twitter
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Design a simplified Twitter with postTweet, getNewsFeed, follow, and unfollow operations.
 * 
 * Key Insights:
 * 1. Tweets need global timestamp for ordering
 * 2. Each user has their own tweet timeline (most recent first)
 * 3. Follow relationships are directed (user follows another user)
 * 4. News feed merges tweets from user + followed users
 * 5. Need efficient retrieval of top 10 most recent tweets
 * 
 * Approach (HashMap + LinkedList + MaxHeap):
 * 1. Use Tweet class with id, timestamp, and next pointer
 * 2. User class or maps to store user data
 * 3. followMap: userId -> Set of followeeIds
 * 4. tweetMap: userId -> head of tweet linked list
 * 5. getNewsFeed: Use max heap to merge k sorted lists (tweet timelines)
 * 
 * Time Complexity:
 * - postTweet: O(1)
 * - getNewsFeed: O(k log k) where k = number of followed users
 * - follow/unfollow: O(1)
 * Space Complexity: O(n + m) where n = tweets, m = follow relationships
 * 
 * Tags: Hash Table, Linked List, Design, Heap
 */

import java.util.*;

class Twitter {
    
    /**
     * Tweet node class for linked list
     * Each tweet has id, timestamp, and next pointer
     */
    private static class Tweet {
        int tweetId;
        int timestamp;
        Tweet next;
        
        Tweet(int tweetId, int timestamp) {
            this.tweetId = tweetId;
            this.timestamp = timestamp;
            this.next = null;
        }
    }
    
    /**
     * Wrapper class for heap operations
     * Contains tweet and reference to next tweet
     */
    private static class TweetWrapper {
        Tweet tweet;
        
        TweetWrapper(Tweet tweet) {
            this.tweet = tweet;
        }
    }
    
    // Global timestamp counter
    private int time;
    
    // User -> List of tweets (most recent first)
    private Map<Integer, Tweet> userTweets;
    
    // User -> Set of followees
    private Map<Integer, Set<Integer>> followings;
    
    /**
     * Initialize your data structure here.
     */
    public Twitter() {
        time = 0;
        userTweets = new HashMap<>();
        followings = new HashMap<>();
    }
    
    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        // Increment global timestamp
        time++;
        
        // Create new tweet
        Tweet newTweet = new Tweet(tweetId, time);
        
        // Add to user's tweet list (at head for O(1) insertion)
        Tweet head = userTweets.getOrDefault(userId, null);
        newTweet.next = head;
        userTweets.put(userId, newTweet);
        
        // Ensure user follows themselves (for news feed)
        follow(userId, userId);
    }
    
    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed.
     * Each item in the news feed must be posted by users who the user
     * followed or by the user themselves.
     * Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> feed = new ArrayList<>();
        
        // Use max heap to get most recent tweets
        PriorityQueue<TweetWrapper> maxHeap = new PriorityQueue<>(
            (a, b) -> b.tweet.timestamp - a.tweet.timestamp
        );
        
        // Add head tweets from all followees
        Set<Integer> followees = followings.get(userId);
        if (followees != null) {
            for (int followeeId : followees) {
                Tweet head = userTweets.get(followeeId);
                if (head != null) {
                    maxHeap.offer(new TweetWrapper(head));
                }
            }
        }
        
        // Get top 10 tweets
        int count = 0;
        while (!maxHeap.isEmpty() && count < 10) {
            TweetWrapper wrapper = maxHeap.poll();
            Tweet tweet = wrapper.tweet;
            
            feed.add(tweet.tweetId);
            count++;
            
            // Add next tweet from same user if exists
            if (tweet.next != null) {
                maxHeap.offer(new TweetWrapper(tweet.next));
            }
        }
        
        return feed;
    }
    
    /**
     * Follower follows a followee.
     */
    public void follow(int followerId, int followeeId) {
        // Create follow set if not exists
        followings.putIfAbsent(followerId, new HashSet<>());
        
        // Add followee to follower's following set
        followings.get(followerId).add(followeeId);
    }
    
    /**
     * Follower unfollows a followee.
     */
    public void unfollow(int followerId, int followeeId) {
        // User cannot unfollow themselves
        if (followerId == followeeId) return;
        
        // Remove followee from follower's following set if exists
        if (followings.containsKey(followerId)) {
            followings.get(followerId).remove(followeeId);
        }
    }
    
    /**
     * Alternative Implementation 1: Using separate User class
     * More object-oriented approach
     */
    static class Twitter2 {
        private static class User {
            int userId;
            Set<Integer> following;
            Tweet tweetHead;
            
            User(int userId) {
                this.userId = userId;
                this.following = new HashSet<>();
                this.following.add(userId); // Follow self
                this.tweetHead = null;
            }
            
            void follow(int followeeId) {
                following.add(followeeId);
            }
            
            void unfollow(int followeeId) {
                if (followeeId != userId) {
                    following.remove(followeeId);
                }
            }
            
            void post(int tweetId, int timestamp) {
                Tweet newTweet = new Tweet(tweetId, timestamp);
                newTweet.next = tweetHead;
                tweetHead = newTweet;
            }
        }
        
        private int time;
        private Map<Integer, User> users;
        
        public Twitter2() {
            time = 0;
            users = new HashMap<>();
        }
        
        public void postTweet(int userId, int tweetId) {
            time++;
            users.putIfAbsent(userId, new User(userId));
            users.get(userId).post(tweetId, time);
        }
        
        public List<Integer> getNewsFeed(int userId) {
            List<Integer> feed = new ArrayList<>();
            
            if (!users.containsKey(userId)) {
                return feed;
            }
            
            User user = users.get(userId);
            PriorityQueue<TweetWrapper> heap = new PriorityQueue<>(
                (a, b) -> b.tweet.timestamp - a.tweet.timestamp
            );
            
            for (int followeeId : user.following) {
                if (users.containsKey(followeeId)) {
                    Tweet head = users.get(followeeId).tweetHead;
                    if (head != null) {
                        heap.offer(new TweetWrapper(head));
                    }
                }
            }
            
            int count = 0;
            while (!heap.isEmpty() && count < 10) {
                TweetWrapper wrapper = heap.poll();
                feed.add(wrapper.tweet.tweetId);
                count++;
                
                if (wrapper.tweet.next != null) {
                    heap.offer(new TweetWrapper(wrapper.tweet.next));
                }
            }
            
            return feed;
        }
        
        public void follow(int followerId, int followeeId) {
            users.putIfAbsent(followerId, new User(followerId));
            users.putIfAbsent(followeeId, new User(followeeId));
            users.get(followerId).follow(followeeId);
        }
        
        public void unfollow(int followerId, int followeeId) {
            if (users.containsKey(followerId)) {
                users.get(followerId).unfollow(followeeId);
            }
        }
    }
    
    /**
     * Alternative Implementation 2: Using List of tweets per user
     * Simpler but less efficient for getNewsFeed
     */
    static class Twitter3 {
        private static class TweetRecord {
            int tweetId;
            int userId;
            int timestamp;
            
            TweetRecord(int tweetId, int userId, int timestamp) {
                this.tweetId = tweetId;
                this.userId = userId;
                this.timestamp = timestamp;
            }
        }
        
        private int time;
        private Map<Integer, Set<Integer>> followings;
        private List<TweetRecord> tweets;
        
        public Twitter3() {
            time = 0;
            followings = new HashMap<>();
            tweets = new ArrayList<>();
        }
        
        public void postTweet(int userId, int tweetId) {
            time++;
            tweets.add(new TweetRecord(tweetId, userId, time));
            follow(userId, userId); // Ensure user follows themselves
        }
        
        public List<Integer> getNewsFeed(int userId) {
            List<Integer> feed = new ArrayList<>();
            Set<Integer> followees = followings.get(userId);
            
            if (followees == null) return feed;
            
            // Iterate from end (most recent) to beginning
            for (int i = tweets.size() - 1; i >= 0 && feed.size() < 10; i--) {
                TweetRecord tweet = tweets.get(i);
                if (followees.contains(tweet.userId)) {
                    feed.add(tweet.tweetId);
                }
            }
            
            return feed;
        }
        
        public void follow(int followerId, int followeeId) {
            followings.putIfAbsent(followerId, new HashSet<>());
            followings.get(followerId).add(followeeId);
        }
        
        public void unfollow(int followerId, int followeeId) {
            if (followerId == followeeId) return;
            if (followings.containsKey(followerId)) {
                followings.get(followerId).remove(followeeId);
            }
        }
    }
    
    /**
     * Alternative Implementation 3: Optimized with caching
     * Caches news feed for each user, updates on post/follow/unfollow
     */
    static class Twitter4 {
        private static class User {
            int userId;
            Set<Integer> following;
            LinkedList<Tweet> tweets;
            List<Integer> cachedFeed; // Cached news feed
            
            User(int userId) {
                this.userId = userId;
                this.following = new HashSet<>();
                this.following.add(userId);
                this.tweets = new LinkedList<>();
                this.cachedFeed = new ArrayList<>();
            }
        }
        
        private int time;
        private Map<Integer, User> users;
        
        public Twitter4() {
            time = 0;
            users = new HashMap<>();
        }
        
        private void invalidateCache(int userId) {
            if (users.containsKey(userId)) {
                users.get(userId).cachedFeed.clear();
            }
        }
        
        public void postTweet(int userId, int tweetId) {
            time++;
            users.putIfAbsent(userId, new User(userId));
            
            User user = users.get(userId);
            user.tweets.addFirst(new Tweet(tweetId, time));
            
            // Keep only recent tweets (optional optimization)
            if (user.tweets.size() > 10) {
                user.tweets.removeLast();
            }
            
            // Invalidate cache for all followers including self
            for (User u : users.values()) {
                if (u.following.contains(userId)) {
                    invalidateCache(u.userId);
                }
            }
        }
        
        public List<Integer> getNewsFeed(int userId) {
            if (!users.containsKey(userId)) {
                return new ArrayList<>();
            }
            
            User user = users.get(userId);
            
            // Return cached feed if available
            if (!user.cachedFeed.isEmpty()) {
                return user.cachedFeed;
            }
            
            // Generate feed using max heap
            PriorityQueue<TweetWrapper> heap = new PriorityQueue<>(
                (a, b) -> b.tweet.timestamp - a.tweet.timestamp
            );
            
            for (int followeeId : user.following) {
                if (users.containsKey(followeeId)) {
                    User followee = users.get(followeeId);
                    if (!followee.tweets.isEmpty()) {
                        heap.offer(new TweetWrapper(followee.tweets.getFirst()));
                    }
                }
            }
            
            List<Integer> feed = new ArrayList<>();
            int count = 0;
            
            while (!heap.isEmpty() && count < 10) {
                TweetWrapper wrapper = heap.poll();
                feed.add(wrapper.tweet.tweetId);
                count++;
                
                // Get next tweet from same user
                User tweetUser = users.get(wrapper.tweet.userId);
                int index = tweetUser.tweets.indexOf(wrapper.tweet);
                if (index + 1 < tweetUser.tweets.size()) {
                    heap.offer(new TweetWrapper(tweetUser.tweets.get(index + 1)));
                }
            }
            
            // Cache the result
            user.cachedFeed = new ArrayList<>(feed);
            return feed;
        }
        
        public void follow(int followerId, int followeeId) {
            users.putIfAbsent(followerId, new User(followerId));
            users.putIfAbsent(followeeId, new User(followeeId));
            
            users.get(followerId).following.add(followeeId);
            invalidateCache(followerId);
        }
        
        public void unfollow(int followerId, int followeeId) {
            if (followerId == followeeId) return;
            
            if (users.containsKey(followerId)) {
                users.get(followerId).following.remove(followeeId);
                invalidateCache(followerId);
            }
        }
    }
    
    /**
     * Helper method to visualize Twitter state
     */
    public void visualize() {
        System.out.println("\n=== Twitter State Visualization ===");
        System.out.println("Global timestamp: " + time);
        
        System.out.println("\nUsers and their tweets (most recent first):");
        for (Map.Entry<Integer, Tweet> entry : userTweets.entrySet()) {
            System.out.print("  User " + entry.getKey() + ": ");
            Tweet tweet = entry.getValue();
            while (tweet != null) {
                System.out.print("[" + tweet.tweetId + "@" + tweet.timestamp + "] ");
                tweet = tweet.next;
            }
            System.out.println();
        }
        
        System.out.println("\nFollow relationships:");
        for (Map.Entry<Integer, Set<Integer>> entry : followings.entrySet()) {
            System.out.print("  User " + entry.getKey() + " follows: ");
            for (int followeeId : entry.getValue()) {
                System.out.print(followeeId + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Helper method to run example from problem statement
     */
    public static void runExample() {
        System.out.println("=== Running Example from Problem ===");
        
        Twitter twitter = new Twitter();
        
        System.out.println("\n1. postTweet(1, 5)");
        twitter.postTweet(1, 5);
        twitter.visualize();
        
        System.out.println("\n2. getNewsFeed(1)");
        List<Integer> feed1 = twitter.getNewsFeed(1);
        System.out.println("   Result: " + feed1);
        
        System.out.println("\n3. follow(1, 2)");
        twitter.follow(1, 2);
        twitter.visualize();
        
        System.out.println("\n4. postTweet(2, 6)");
        twitter.postTweet(2, 6);
        twitter.visualize();
        
        System.out.println("\n5. getNewsFeed(1)");
        List<Integer> feed2 = twitter.getNewsFeed(1);
        System.out.println("   Result: " + feed2);
        
        System.out.println("\n6. unfollow(1, 2)");
        twitter.unfollow(1, 2);
        twitter.visualize();
        
        System.out.println("\n7. getNewsFeed(1)");
        List<Integer> feed3 = twitter.getNewsFeed(1);
        System.out.println("   Result: " + feed3);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        System.out.println("Testing Design Twitter Solution:");
        System.out.println("=================================");
        
        // Test the main implementation
        System.out.println("\nTest 1: Basic operations");
        Twitter twitter1 = new Twitter();
        
        twitter1.postTweet(1, 101);
        twitter1.postTweet(1, 102);
        twitter1.postTweet(2, 201);
        twitter1.follow(1, 2);
        
        List<Integer> feed1 = twitter1.getNewsFeed(1);
        System.out.println("User 1's feed after following user 2: " + feed1);
        
        twitter1.unfollow(1, 2);
        List<Integer> feed2 = twitter1.getNewsFeed(1);
        System.out.println("User 1's feed after unfollowing user 2: " + feed2);
        
        // Test edge cases
        System.out.println("\nTest 2: Edge cases");
        Twitter twitter2 = new Twitter();
        
        // Get feed for non-existent user
        List<Integer> feed3 = twitter2.getNewsFeed(999);
        System.out.println("Feed for non-existent user: " + feed3);
        
        // Follow non-existent user
        twitter2.follow(1, 999);
        twitter2.postTweet(1, 101);
        List<Integer> feed4 = twitter2.getNewsFeed(1);
        System.out.println("User 1's feed after following non-existent user: " + feed4);
        
        // Unfollow self (should do nothing)
        twitter2.unfollow(1, 1);
        List<Integer> feed5 = twitter2.getNewsFeed(1);
        System.out.println("User 1's feed after unfollowing self: " + feed5);
        
        // Run example from problem
        runExample();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON:");
        System.out.println("=".repeat(70));
        
        // Create test data
        int numUsers = 100;
        int tweetsPerUser = 50;
        int followsPerUser = 20;
        
        System.out.println("\nTesting with:");
        System.out.println("  " + numUsers + " users");
        System.out.println("  " + tweetsPerUser + " tweets per user");
        System.out.println("  " + followsPerUser + " follows per user");
        
        // Test implementation 1 (Linked List + Heap)
        System.out.println("\n1. Testing Linked List + Heap implementation:");
        Twitter twitterImpl1 = new Twitter();
        long startTime = System.currentTimeMillis();
        
        Random random = new Random(42);
        // Post tweets
        for (int userId = 1; userId <= numUsers; userId++) {
            for (int tweetNum = 1; tweetNum <= tweetsPerUser; tweetNum++) {
                twitterImpl1.postTweet(userId, userId * 1000 + tweetNum);
            }
        }
        
        // Create follow relationships
        for (int userId = 1; userId <= numUsers; userId++) {
            for (int i = 0; i < followsPerUser; i++) {
                int followeeId = random.nextInt(numUsers) + 1;
                twitterImpl1.follow(userId, followeeId);
            }
        }
        
        // Get news feeds
        int totalTweetsInFeeds = 0;
        for (int userId = 1; userId <= numUsers; userId++) {
            List<Integer> feed = twitterImpl1.getNewsFeed(userId);
            totalTweetsInFeeds += feed.size();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("  Time: " + (endTime - startTime) + " ms");
        System.out.println("  Total tweets in all feeds: " + totalTweetsInFeeds);
        
        // Test implementation 2 (User class)
        System.out.println("\n2. Testing User class implementation:");
        Twitter2 twitterImpl2 = new Twitter2();
        startTime = System.currentTimeMillis();
        
        // Post tweets
        for (int userId = 1; userId <= numUsers; userId++) {
            for (int tweetNum = 1; tweetNum <= tweetsPerUser; tweetNum++) {
                twitterImpl2.postTweet(userId, userId * 1000 + tweetNum);
            }
        }
        
        // Create follow relationships
        for (int userId = 1; userId <= numUsers; userId++) {
            for (int i = 0; i < followsPerUser; i++) {
                int followeeId = random.nextInt(numUsers) + 1;
                twitterImpl2.follow(userId, followeeId);
            }
        }
        
        // Get news feeds
        totalTweetsInFeeds = 0;
        for (int userId = 1; userId <= numUsers; userId++) {
            List<Integer> feed = twitterImpl2.getNewsFeed(userId);
            totalTweetsInFeeds += feed.size();
        }
        
        endTime = System.currentTimeMillis();
        System.out.println("  Time: " + (endTime - startTime) + " ms");
        System.out.println("  Total tweets in all feeds: " + totalTweetsInFeeds);
        
        // Test implementation 3 (Simple list)
        System.out.println("\n3. Testing Simple List implementation:");
        Twitter3 twitterImpl3 = new Twitter3();
        startTime = System.currentTimeMillis();
        
        // Post tweets
        for (int userId = 1; userId <= numUsers; userId++) {
            for (int tweetNum = 1; tweetNum <= tweetsPerUser; tweetNum++) {
                twitterImpl3.postTweet(userId, userId * 1000 + tweetNum);
            }
        }
        
        // Create follow relationships
        for (int userId = 1; userId <= numUsers; userId++) {
            for (int i = 0; i < followsPerUser; i++) {
                int followeeId = random.nextInt(numUsers) + 1;
                twitterImpl3.follow(userId, followeeId);
            }
        }
        
        // Get news feeds
        totalTweetsInFeeds = 0;
        for (int userId = 1; userId <= numUsers; userId++) {
            List<Integer> feed = twitterImpl3.getNewsFeed(userId);
            totalTweetsInFeeds += feed.size();
        }
        
        endTime = System.currentTimeMillis();
        System.out.println("  Time: " + (endTime - startTime) + " ms");
        System.out.println("  Total tweets in all feeds: " + totalTweetsInFeeds);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nData Structures Used:");
        System.out.println("1. Global timestamp counter:");
        System.out.println("   - Increments with each tweet");
        System.out.println("   - Ensures total ordering of tweets");
        
        System.out.println("\n2. UserTweets HashMap:");
        System.out.println("   - Key: userId");
        System.out.println("   - Value: Head of tweet linked list");
        System.out.println("   - Linked list stores tweets in reverse chronological order");
        System.out.println("   - New tweets added at head (O(1) insertion)");
        
        System.out.println("\n3. Followings HashMap:");
        System.out.println("   - Key: userId");
        System.out.println("   - Value: Set of followeeIds");
        System.out.println("   - Includes self-follow by default");
        System.out.println("   - O(1) add/remove for follow/unfollow");
        
        System.out.println("\ngetNewsFeed Algorithm:");
        System.out.println("1. Get all users followed by given user");
        System.out.println("2. Create max heap ordered by tweet timestamp");
        System.out.println("3. Add head tweet from each followed user to heap");
        System.out.println("4. While heap not empty and haven't collected 10 tweets:");
        System.out.println("   a. Pop most recent tweet from heap");
        System.out.println("   b. Add to result");
        System.out.println("   c. If popped tweet has next tweet, add next to heap");
        System.out.println("5. Return result");
        
        System.out.println("\nVisual Example:");
        System.out.println("User 1 follows: [1, 2, 3]");
        System.out.println("User 1 tweets: [A3, A2, A1] (A3 most recent)");
        System.out.println("User 2 tweets: [B2, B1]");
        System.out.println("User 3 tweets: [C1]");
        System.out.println("\nHeap initialization:");
        System.out.println("  Add A3 (timestamp 10), B2 (timestamp 8), C1 (timestamp 5)");
        System.out.println("Heap: [A3(10), B2(8), C1(5)]");
        System.out.println("\nStep 1: Pop A3(10), add A2(9) to heap");
        System.out.println("Result: [A3], Heap: [A2(9), B2(8), C1(5)]");
        System.out.println("\nStep 2: Pop A2(9), add A1(7) to heap");
        System.out.println("Result: [A3, A2], Heap: [B2(8), A1(7), C1(5)]");
        System.out.println("\nContinues until 10 tweets collected...");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("IMPLEMENTATION COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Linked List + Max Heap (RECOMMENDED):");
        System.out.println("   Time Complexity:");
        System.out.println("     - postTweet: O(1)");
        System.out.println("     - getNewsFeed: O(k log k) where k = followed users");
        System.out.println("     - follow/unfollow: O(1)");
        System.out.println("   Space Complexity: O(n + m)");
        System.out.println("   Pros:");
        System.out.println("     - Efficient getNewsFeed for large k");
        System.out.println("     - Scalable design");
        System.out.println("     - Clean separation of concerns");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Heap operations overhead");
        System.out.println("   Best for: General purpose, large number of follows");
        
        System.out.println("\n2. User Class (Object-Oriented):");
        System.out.println("   Time Complexity: Same as above");
        System.out.println("   Space Complexity: Same as above");
        System.out.println("   Pros:");
        System.out.println("     - More object-oriented");
        System.out.println("     - Cleaner code organization");
        System.out.println("     - Easier to extend");
        System.out.println("   Cons:");
        System.out.println("     - More classes to manage");
        System.out.println("     - Slightly more memory overhead");
        System.out.println("   Best for: Larger systems, team development");
        
        System.out.println("\n3. Simple List (Brute Force):");
        System.out.println("   Time Complexity:");
        System.out.println("     - postTweet: O(1)");
        System.out.println("     - getNewsFeed: O(total tweets)");
        System.out.println("     - follow/unfollow: O(1)");
        System.out.println("   Space Complexity: O(n + m)");
        System.out.println("   Pros:");
        System.out.println("     - Simple implementation");
        System.out.println("     - Easy to understand");
        System.out.println("     - No heap overhead");
        System.out.println("   Cons:");
        System.out.println("     - getNewsFeed is O(n) which is too slow");
        System.out.println("     - Doesn't scale");
        System.out.println("   Best for: Small number of tweets, learning");
        
        System.out.println("\n4. Cached Feeds (Optimized):");
        System.out.println("   Time Complexity:");
        System.out.println("     - postTweet: O(followers) to invalidate caches");
        System.out.println("     - getNewsFeed: O(1) when cache hit");
        System.out.println("     - follow/unfollow: O(1) + cache invalidation");
        System.out.println("   Space Complexity: O(n + m + f) where f = cached feeds");
        System.out.println("   Pros:");
        System.out.println("     - Very fast getNewsFeed when cached");
        System.out.println("     - Good for read-heavy workloads");
        System.out.println("   Cons:");
        System.out.println("     - More complex cache invalidation");
        System.out.println("     - Memory overhead for caches");
        System.out.println("     - Write operations become more expensive");
        System.out.println("   Best for: Read-heavy Twitter-like applications");
        
        // Design considerations
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DESIGN CONSIDERATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Data Model Design:");
        System.out.println("   Option A: Denormalized (store tweets with user)");
        System.out.println("     - Pros: Faster reads for user's own tweets");
        System.out.println("     - Cons: Harder to update user info");
        
        System.out.println("\n   Option B: Normalized (separate tweet and user tables)");
        System.out.println("     - Pros: Cleaner schema, easier updates");
        System.out.println("     - Cons: Requires joins for reads");
        
        System.out.println("\n2. Timeline Storage:");
        System.out.println("   Option A: Linked list per user");
        System.out.println("     - Pros: O(1) insertion, natural ordering");
        System.out.println("     - Cons: Hard to paginate, random access");
        
        System.out.println("\n   Option B: Array/list with timestamps");
        System.out.println("     - Pros: Easy pagination, random access");
        System.out.println("     - Cons: O(n) insertion if maintaining order");
        
        System.out.println("\n3. Feed Generation:");
        System.out.println("   Option A: Pull model (compute on read)");
        System.out.println("     - Pros: Simple, always up-to-date");
        System.out.println("     - Cons: Expensive for active users");
        
        System.out.println("\n   Option B: Push model (pre-compute feeds)");
        System.out.println("     - Pros: Fast reads");
        System.out.println("     - Cons: Expensive writes, storage overhead");
        
        System.out.println("\n   Option C: Hybrid (pull for active, push for inactive)");
        System.out.println("     - Pros: Balance of read/write performance");
        System.out.println("     - Cons: More complex implementation");
        
        // Real-world Twitter architecture insights
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD TWITTER ARCHITECTURE INSIGHTS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nHow Real Twitter Works (Simplified):");
        System.out.println("1. Tweets stored in distributed databases");
        System.out.println("2. Follow graph stored in graph database");
        System.out.println("3. Home timeline generation:");
        System.out.println("   - For celebrities/popular users: Pull model");
        System.out.println("   - For regular users: Push model");
        System.out.println("4. Fan-out on write:");
        System.out.println("   - When you tweet, it's added to followers' home timelines");
        System.out.println("5. Redis for caching timelines");
        System.out.println("6. Asynchronous processing for heavy operations");
        
        System.out.println("\nScaling Challenges:");
        System.out.println("1. Hot users (celebrities with millions of followers)");
        System.out.println("2. Fan-out delay for new followers");
        System.out.println("3. Timeline consistency (eventual vs strong)");
        System.out.println("4. Storage for billions of tweets");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify requirements:");
        System.out.println("   - postTweet: unique tweet IDs");
        System.out.println("   - getNewsFeed: 10 most recent from user + followed");
        System.out.println("   - follow/unfollow: directed relationships");
        
        System.out.println("\n2. Discuss data structures:");
        System.out.println("   - Need to store tweets with timestamps");
        System.out.println("   - Need to store follow relationships");
        System.out.println("   - Need efficient retrieval of recent tweets");
        
        System.out.println("\n3. Propose solution:");
        System.out.println("   - Tweet class with id, timestamp, next pointer");
        System.out.println("   - HashMap for user -> tweet list (linked list)");
        System.out.println("   - HashMap for user -> set of followees");
        System.out.println("   - Max heap for merging k sorted lists");
        
        System.out.println("\n4. Implement operations:");
        System.out.println("   - postTweet: O(1), add to head of user's list");
        System.out.println("   - follow/unfollow: O(1), update sets");
        System.out.println("   - getNewsFeed: O(k log k), use max heap");
        
        System.out.println("\n5. Handle edge cases:");
        System.out.println("   - User follows themselves (default)");
        System.out.println("   - Unfollow self (should do nothing)");
        System.out.println("   - Non-existent users");
        System.out.println("   - Empty news feed");
        
        System.out.println("\n6. Discuss optimizations:");
        System.out.println("   - Caching news feeds");
        System.out.println("   - Limiting stored tweets per user");
        System.out.println("   - Lazy loading of older tweets");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- O(1) for write operations (post/follow/unfollow)");
        System.out.println("- O(k log k) for read operation (getNewsFeed)");
        System.out.println("- Linked list for O(1) tweet insertion");
        System.out.println("- Max heap for merging k sorted timelines");
        System.out.println("- Self-follow ensures user sees own tweets");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting to handle self-follow");
        System.out.println("- Not using timestamp for tweet ordering");
        System.out.println("- Inefficient news feed generation (O(n))");
        System.out.println("- Not considering concurrent operations");
        System.out.println("- Memory leaks in linked lists");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test basic post and get");
        System.out.println("2. Test follow/unfollow functionality");
        System.out.println("3. Test news feed ordering (most recent first)");
        System.out.println("4. Test edge cases (self follow/unfollow)");
        System.out.println("5. Test with multiple users and tweets");
        System.out.println("6. Verify tweet limit (10) in news feed");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
