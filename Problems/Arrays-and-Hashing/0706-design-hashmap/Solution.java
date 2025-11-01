/**
 * 706. Design HashMap
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Design a HashMap without using any built-in hash table libraries.
 * Implement MyHashMap class with void put(key, value), int get(key), and void remove(key) methods.
 * 
 * Key Insights:
 * 1. Need to handle key-value pairs (not just keys like HashSet)
 * 2. Must handle collisions - multiple keys hashing to same bucket
 * 3. put should update value if key exists
 * 4. get returns -1 if key not found
 * 5. Chaining with linked lists is a practical approach
 * 
 * Approach:
 * 1. Use an array of linked lists (buckets) for storage
 * 2. Define an Entry class to store key-value pairs
 * 3. Hash function: key % NUMBER_OF_BUCKETS
 * 4. For put: find bucket, search for key, update if exists or add new entry
 * 5. For get: find bucket, search for key, return value or -1
 * 6. For remove: find bucket, search for key, remove entry if found
 * 
 * Time Complexity: O(n/b) average case for operations
 * Space Complexity: O(n + b) where n is data size, b is bucket count
 * 
 * Tags: Hash Table, Design, Linked List
 */

import java.util.LinkedList;

/**
 * Entry class to store key-value pairs in the hash map
 */
class Entry {
    int key;
    int value;
    
    public Entry(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

/**
 * Main HashMap implementation using chaining
 */
class MyHashMap {
    private static final int BUCKETS = 1000;
    private LinkedList<Entry>[] buckets;
    
    /**
     * Initialize your data structure here.
     */
    @SuppressWarnings("unchecked")
    public MyHashMap() {
        buckets = new LinkedList[BUCKETS];
        for (int i = 0; i < BUCKETS; i++) {
            buckets[i] = new LinkedList<>();
        }
    }
    
    /**
     * Hash function to determine bucket index
     * Uses modulo operation for even distribution
     */
    private int hash(int key) {
        return key % BUCKETS;
    }
    
    /**
     * value will always be non-negative.
     */
    public void put(int key, int value) {
        int index = hash(key);
        LinkedList<Entry> bucket = buckets[index];
        
        // Check if key already exists in bucket
        for (Entry entry : bucket) {
            if (entry.key == key) {
                // Update existing key
                entry.value = value;
                return;
            }
        }
        
        // Key doesn't exist, add new entry
        bucket.add(new Entry(key, value));
    }
    
    /**
     * Returns the value to which the specified key is mapped,
     * or -1 if this map contains no mapping for the key
     */
    public int get(int key) {
        int index = hash(key);
        LinkedList<Entry> bucket = buckets[index];
        
        for (Entry entry : bucket) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        
        return -1; // Key not found
    }
    
    /**
     * Removes the mapping of the specified value key
     * if this map contains a mapping for the key
     */
    public void remove(int key) {
        int index = hash(key);
        LinkedList<Entry> bucket = buckets[index];
        
        // Find and remove the entry with the given key
        for (Entry entry : bucket) {
            if (entry.key == key) {
                bucket.remove(entry);
                return;
            }
        }
    }
}

/**
 * Alternative Implementation: Array-based (Simple but Memory Intensive)
 * Suitable for constrained environments where key range is limited
 */
class MyHashMapArray {
    private static final int SIZE = 1000001;
    private int[] map;
    
    public MyHashMapArray() {
        map = new int[SIZE];
        // Initialize with -1 to indicate "key not found"
        for (int i = 0; i < SIZE; i++) {
            map[i] = -1;
        }
    }
    
    public void put(int key, int value) {
        map[key] = value;
    }
    
    public int get(int key) {
        return map[key];
    }
    
    public void remove(int key) {
        map[key] = -1;
    }
}

/**
 * Enhanced HashMap with better remove performance using iterator
 */
class MyHashMapEnhanced {
    private static final int BUCKETS = 1000;
    private LinkedList<Entry>[] buckets;
    
    @SuppressWarnings("unchecked")
    public MyHashMapEnhanced() {
        buckets = new LinkedList[BUCKETS];
        for (int i = 0; i < BUCKETS; i++) {
            buckets[i] = new LinkedList<>();
        }
    }
    
    private int hash(int key) {
        return key % BUCKETS;
    }
    
    public void put(int key, int value) {
        int index = hash(key);
        LinkedList<Entry> bucket = buckets[index];
        
        for (Entry entry : bucket) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }
        bucket.add(new Entry(key, value));
    }
    
    public int get(int key) {
        int index = hash(key);
        LinkedList<Entry> bucket = buckets[index];
        
        for (Entry entry : bucket) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return -1;
    }
    
    /**
     * Enhanced remove using iterator to avoid ConcurrentModificationException
     * and improve performance during iteration
     */
    public void remove(int key) {
        int index = hash(key);
        LinkedList<Entry> bucket = buckets[index];
        
        // Use iterator for safe removal during iteration
        var iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            if (entry.key == key) {
                iterator.remove();
                return;
            }
        }
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */

/**
 * Test class to verify the HashMap implementation
 */
public class Solution {
    /**
     * Helper method to print test results
     */
    private static void printTestResult(String testName, boolean passed) {
        System.out.println(testName + ": " + (passed ? "PASSED" : "FAILED"));
    }
    
    /**
     * Test cases to verify the HashMap implementation
     */
    public static void main(String[] args) {
        System.out.println("Testing Design HashMap Solution:");
        System.out.println("=================================");
        
        // Test 1: Basic operations
        System.out.println("\nTest 1: Basic Operations (Chaining Implementation)");
        MyHashMap hashMap = new MyHashMap();
        
        hashMap.put(1, 1);
        hashMap.put(2, 2);
        
        int val1 = hashMap.get(1);    // Expected: 1
        int val2 = hashMap.get(3);    // Expected: -1
        hashMap.put(2, 1);            // Update existing key
        int val3 = hashMap.get(2);    // Expected: 1
        hashMap.remove(2);            // Remove key
        int val4 = hashMap.get(2);    // Expected: -1
        
        boolean test1Passed = (val1 == 1) && (val2 == -1) && (val3 == 1) && (val4 == -1);
        printTestResult("Basic put/get/remove operations", test1Passed);
        
        // Test 2: Collision handling
        System.out.println("\nTest 2: Collision Handling");
        MyHashMap collisionMap = new MyHashMap();
        
        // These keys will hash to the same bucket (1 % 1000 = 1, 1001 % 1000 = 1)
        collisionMap.put(1, 10);
        collisionMap.put(1001, 20);
        collisionMap.put(2001, 30);
        
        boolean test2Passed = collisionMap.get(1) == 10 && 
                             collisionMap.get(1001) == 20 && 
                             collisionMap.get(2001) == 30;
        printTestResult("Keys with same hash code", test2Passed);
        
        // Update one key in collision chain
        collisionMap.put(1001, 25);
        boolean test2bPassed = collisionMap.get(1001) == 25 && collisionMap.get(1) == 10;
        printTestResult("Update key in collision chain", test2bPassed);
        
        // Remove from collision chain
        collisionMap.remove(1001);
        boolean test2cPassed = collisionMap.get(1001) == -1 && 
                              collisionMap.get(1) == 10 && 
                              collisionMap.get(2001) == 30;
        printTestResult("Remove from collision chain", test2cPassed);
        
        // Test 3: Array-based implementation
        System.out.println("\nTest 3: Array-based Implementation");
        MyHashMapArray arrayMap = new MyHashMapArray();
        
        arrayMap.put(1, 100);
        arrayMap.put(1000000, 999);  // Maximum key
        
        boolean test3Passed = arrayMap.get(1) == 100 && 
                             arrayMap.get(1000000) == 999 && 
                             arrayMap.get(500000) == -1;
        printTestResult("Array implementation basic operations", test3Passed);
        
        arrayMap.remove(1);
        boolean test3bPassed = arrayMap.get(1) == -1 && arrayMap.get(1000000) == 999;
        printTestResult("Array implementation remove", test3bPassed);
        
        // Test 4: Enhanced implementation
        System.out.println("\nTest 4: Enhanced Implementation");
        MyHashMapEnhanced enhancedMap = new MyHashMapEnhanced();
        
        // Add multiple keys that will collide
        for (int i = 0; i < 10; i++) {
            enhancedMap.put(i * 1000, i * 100);  // All hash to bucket 0
        }
        
        // Verify all keys are present
        boolean allPresent = true;
        for (int i = 0; i < 10; i++) {
            if (enhancedMap.get(i * 1000) != i * 100) {
                allPresent = false;
                break;
            }
        }
        printTestResult("Enhanced - multiple collisions", allPresent);
        
        // Remove some keys
        enhancedMap.remove(0);
        enhancedMap.remove(5000);
        boolean removeTest = enhancedMap.get(0) == -1 && 
                           enhancedMap.get(5000) == -1 && 
                           enhancedMap.get(1000) == 100;
        printTestResult("Enhanced - remove from collisions", removeTest);
        
        // Test 5: Edge cases
        System.out.println("\nTest 5: Edge Cases");
        MyHashMap edgeMap = new MyHashMap();
        
        // Key 0
        edgeMap.put(0, 123);
        boolean edge1 = edgeMap.get(0) == 123;
        edgeMap.remove(0);
        boolean edge2 = edgeMap.get(0) == -1;
        
        // Value 0
        edgeMap.put(5, 0);
        boolean edge3 = edgeMap.get(5) == 0;
        
        printTestResult("Edge cases (key 0, value 0)", edge1 && edge2 && edge3);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("- Chaining approach: O(n/b) operations, memory efficient");
        System.out.println("- Array approach: O(1) operations, 4MB memory (int[1000001])");
        System.out.println("- Enhanced chaining: Better removal with iterator");
        System.out.println("- For interview: Chaining approach is preferred (demonstrates understanding)");
        
        System.out.println("\nAll tests completed!");
    }
}
