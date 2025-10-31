/**
 * 705. Design HashSet
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Design a HashSet without using any built-in hash table libraries.
 * Implement MyHashSet class with void add(key), bool contains(key), and void remove(key) methods.
 * 
 * Key Insights:
 * 1. Keys are constrained to range [0, 1000000]
 * 2. Can use a simple boolean array for O(1) operations
 * 3. Array size of 1000001 covers all possible keys
 * 4. Alternative approach would be hashing with chaining for unbounded keys
 * 
 * Approach:
 * 1. Use a boolean array where index represents the key
 * 2. add(key): set array[key] = true
 * 3. remove(key): set array[key] = false  
 * 4. contains(key): return array[key]
 * 
 * Time Complexity: O(1) for all operations
 * Space Complexity: O(1) - fixed size array
 * 
 * Tags: Hash Table, Design, Array
 */

import java.util.LinkedList;

class MyHashSet {
    private boolean[] set;
    
    /**
     * Initialize your data structure here.
     */
    public MyHashSet() {
        // Create array of size 1000001 to cover keys 0 to 1000000
        set = new boolean[1000001];
    }
    
    /**
     * Inserts the value key into the HashSet
     * 
     * @param key the value to be added
     */
    public void add(int key) {
        set[key] = true;
    }
    
    /**
     * Removes the value key from the HashSet
     * If key doesn't exist, does nothing
     * 
     * @param key the value to be removed
     */
    public void remove(int key) {
        set[key] = false;
    }
    
    /**
     * Returns true if this set contains the specified element
     * 
     * @param key the value to check for existence
     * @return true if the set contains key, false otherwise
     */
    public boolean contains(int key) {
        return set[key];
    }
}

/**
 * Alternative Implementation: Hashing with Chaining
 * More memory efficient for sparse data, works for unbounded key ranges
 */

class MyHashSetWithChaining {
    private static final int BUCKETS = 1000;
    private LinkedList<Integer>[] buckets;
    
    /**
     * Initialize your data structure here.
     */
    @SuppressWarnings("unchecked")
    public MyHashSetWithChaining() {
        buckets = new LinkedList[BUCKETS];
        for (int i = 0; i < BUCKETS; i++) {
            buckets[i] = new LinkedList<>();
        }
    }
    
    /**
     * Hash function to determine bucket index
     */
    private int hash(int key) {
        return key % BUCKETS;
    }
    
    public void add(int key) {
        int index = hash(key);
        if (!buckets[index].contains(key)) {
            buckets[index].add(key);
        }
    }
    
    public void remove(int key) {
        int index = hash(key);
        // Use Integer.valueOf to avoid removing by index
        buckets[index].remove(Integer.valueOf(key));
    }
    
    public boolean contains(int key) {
        int index = hash(key);
        return buckets[index].contains(key);
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */

/**
 * Test class to verify the HashSet implementation
 */
public class Solution {
    /**
     * Helper method to print test results
     */
    private static void printTestResult(String testName, boolean passed) {
        System.out.println(testName + ": " + (passed ? "PASSED" : "FAILED"));
    }
    
    /**
     * Test cases to verify the HashSet implementation
     */
    public static void main(String[] args) {
        System.out.println("Testing Design HashSet Solution:");
        System.out.println("=================================");
        
        // Test 1: Basic operations
        System.out.println("\nTest 1: Basic Operations");
        MyHashSet hashSet = new MyHashSet();
        
        hashSet.add(1);
        hashSet.add(2);
        
        boolean test1a = hashSet.contains(1); // Expected: true
        boolean test1b = hashSet.contains(3); // Expected: false
        hashSet.add(2);
        boolean test1c = hashSet.contains(2); // Expected: true
        hashSet.remove(2);
        boolean test1d = hashSet.contains(2); // Expected: false
        
        printTestResult("Add and contains(1)", test1a);
        printTestResult("Contains non-existent(3)", !test1b);
        printTestResult("Duplicate add contains(2)", test1c);
        printTestResult("Remove and contains(2)", !test1d);
        
        // Test 2: Edge cases
        System.out.println("\nTest 2: Edge Cases");
        MyHashSet hashSet2 = new MyHashSet();
        
        // Test with key 0
        hashSet2.add(0);
        boolean test2a = hashSet2.contains(0);
        hashSet2.remove(0);
        boolean test2b = !hashSet2.contains(0);
        
        // Test with maximum key
        hashSet2.add(1000000);
        boolean test2c = hashSet2.contains(1000000);
        hashSet2.remove(1000000);
        boolean test2d = !hashSet2.contains(1000000);
        
        printTestResult("Key 0 operations", test2a && test2b);
        printTestResult("Key 1000000 operations", test2c && test2d);
        
        // Test 3: Multiple operations
        System.out.println("\nTest 3: Multiple Operations");
        MyHashSet hashSet3 = new MyHashSet();
        
        // Add multiple keys
        for (int i = 0; i < 100; i += 2) {
            hashSet3.add(i);
        }
        
        // Verify even numbers are present, odd numbers are not
        boolean allTestsPassed = true;
        for (int i = 0; i < 100; i++) {
            boolean shouldExist = (i % 2 == 0);
            if (hashSet3.contains(i) != shouldExist) {
                allTestsPassed = false;
                break;
            }
        }
        
        // Remove some keys
        for (int i = 0; i < 50; i += 2) {
            hashSet3.remove(i);
        }
        
        // Verify removal worked
        for (int i = 0; i < 50; i++) {
            if (hashSet3.contains(i)) {
                allTestsPassed = false;
                break;
            }
        }
        
        printTestResult("Multiple add/remove operations", allTestsPassed);
        
        // Test 4: Alternative implementation (Chaining)
        System.out.println("\nTest 4: Alternative Implementation (Chaining)");
        MyHashSetWithChaining chainingSet = new MyHashSetWithChaining();
        
        chainingSet.add(1);
        chainingSet.add(1001); // This should go to same bucket as 1 (1 % 1000 = 1, 1001 % 1000 = 1)
        chainingSet.add(2001);
        
        boolean test4a = chainingSet.contains(1);
        boolean test4b = chainingSet.contains(1001);
        boolean test4c = chainingSet.contains(2001);
        boolean test4d = !chainingSet.contains(3);
        
        chainingSet.remove(1001);
        boolean test4e = !chainingSet.contains(1001) && chainingSet.contains(1) && chainingSet.contains(2001);
        
        printTestResult("Chaining - basic operations", test4a && test4b && test4c && test4d);
        printTestResult("Chaining - remove from chain", test4e);
        
        System.out.println("\nAll tests completed!");
        
        // Performance comparison note
        System.out.println("\nPerformance Note:");
        System.out.println("- Array approach: O(1) all operations, fixed memory");
        System.out.println("- Chaining approach: O(n/bucket) operations, dynamic memory");
        System.out.println("- For constrained key range [0, 10^6], array approach is optimal");
    }
}
