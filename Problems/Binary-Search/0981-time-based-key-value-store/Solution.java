
## Solution.java

```java
/**
 * 981. Time Based Key-Value Store
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Design a time-based key-value data structure that can store multiple values 
 * for the same key at different timestamps and retrieve the key's value at a certain timestamp.
 * 
 * Key Insights:
 * 1. Use HashMap: key -> list of (timestamp, value) pairs
 * 2. Timestamps are strictly increasing, so lists are naturally sorted
 * 3. For get(key, timestamp): use binary search to find largest timestamp <= given timestamp
 * 4. Binary search can be implemented using Collections.binarySearch or custom implementation
 * 
 * Approach:
 * 1. HashMap<String, List<Pair<Integer, String>>> for storage
 * 2. set(): simply append to the list (O(1) amortized)
 * 3. get(): binary search on the list for the key to find appropriate timestamp
 * 
 * Time Complexity:
 *   set: O(1) average
 *   get: O(log n) where n = number of timestamps for the key
 * Space Complexity: O(n) where n = total number of set operations
 * 
 * Tags: Hash Table, Binary Search, Design
 */

import java.util.*;

class TimeMap {
    /**
     * Approach 1: HashMap with Binary Search - RECOMMENDED
     * Efficient and clean implementation
     */
    
    // Inner class to store timestamp-value pairs
    class TimeValue {
        int timestamp;
        String value;
        
        TimeValue(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }
    
    private Map<String, List<TimeValue>> store;

    public TimeMap() {
        store = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        store.putIfAbsent(key, new ArrayList<>());
        store.get(key).add(new TimeValue(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        if (!store.containsKey(key)) {
            return "";
        }
        
        List<TimeValue> list = store.get(key);
        
        // Binary search to find the largest timestamp <= target timestamp
        int left = 0;
        int right = list.size() - 1;
        String result = "";
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            TimeValue tv = list.get(mid);
            
            if (tv.timestamp == timestamp) {
                return tv.value;
            } else if (tv.timestamp < timestamp) {
                result = tv.value; // Potential result
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
}

/**
 * Alternative implementations with different approaches
 */
class TimeMapOptimized {
    /**
     * Approach 2: Using Collections.binarySearch
     * Leverages Java's built-in binary search
     */
    
    class TimeValue implements Comparable<TimeValue> {
        int timestamp;
        String value;
        
        TimeValue(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
        
        @Override
        public int compareTo(TimeValue other) {
            return Integer.compare(this.timestamp, other.timestamp);
        }
    }
    
    private Map<String, List<TimeValue>> store;

    public TimeMapOptimized() {
        store = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        store.putIfAbsent(key, new ArrayList<>());
        store.get(key).add(new TimeValue(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        if (!store.containsKey(key)) {
            return "";
        }
        
        List<TimeValue> list = store.get(key);
        
        // Create a dummy TimeValue for search
        TimeValue searchKey = new TimeValue(timestamp, "");
        
        // Binary search returns: index if found, or (-insertion_point - 1) if not found
        int index = Collections.binarySearch(list, searchKey);
        
        if (index >= 0) {
            // Exact timestamp found
            return list.get(index).value;
        } else {
            // No exact match, calculate insertion point
            int insertionPoint = -index - 1;
            if (insertionPoint == 0) {
                // All timestamps are greater than target
                return "";
            }
            // Return the value at insertionPoint - 1 (largest timestamp <= target)
            return list.get(insertionPoint - 1).value;
        }
    }
}

class TimeMapTreeMap {
    /**
     * Approach 3: Using TreeMap for each key
     * Simpler but may use more memory
     */
    
    private Map<String, TreeMap<Integer, String>> store;

    public TimeMapTreeMap() {
        store = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        store.putIfAbsent(key, new TreeMap<>());
        store.get(key).put(timestamp, value);
    }
    
    public String get(String key, int timestamp) {
        if (!store.containsKey(key)) {
            return "";
        }
        
        TreeMap<Integer, String> treeMap = store.get(key);
        // floorKey returns the greatest key less than or equal to the given key
        Integer floorKey = treeMap.floorKey(timestamp);
        
        return floorKey == null ? "" : treeMap.get(floorKey);
    }
}

class TimeMapCustomBinarySearch {
    /**
     * Approach 4: Custom binary search with helper method
     * More modular and testable
     */
    
    class TimeValue {
        int timestamp;
        String value;
        
        TimeValue(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }
    
    private Map<String, List<TimeValue>> store;

    public TimeMapCustomBinarySearch() {
        store = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        store.putIfAbsent(key, new ArrayList<>());
        store.get(key).add(new TimeValue(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        if (!store.containsKey(key)) {
            return "";
        }
        
        List<TimeValue> list = store.get(key);
        int index = binarySearch(list, timestamp);
        
        return index == -1 ? "" : list.get(index).value;
    }
    
    private int binarySearch(List<TimeValue> list, int timestamp) {
        int left = 0;
        int right = list.size() - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            TimeValue tv = list.get(mid);
            
            if (tv.timestamp <= timestamp) {
                result = mid; // Potential result
                left = mid + 1; // Try to find larger timestamp that's still <= target
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
}

class TimeMapWithPairs {
    /**
     * Approach 5: Using Pair class from javafx.util (if available)
     * Alternative implementation using Pair
     */
    
    private Map<String, List<Pair<Integer, String>>> store;

    public TimeMapWithPairs() {
        store = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        store.putIfAbsent(key, new ArrayList<>());
        store.get(key).add(new Pair<>(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        if (!store.containsKey(key)) {
            return "";
        }
        
        List<Pair<Integer, String>> list = store.get(key);
        int left = 0;
        int right = list.size() - 1;
        String result = "";
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            Pair<Integer, String> pair = list.get(mid);
            
            if (pair.getKey() == timestamp) {
                return pair.getValue();
            } else if (pair.getKey() < timestamp) {
                result = pair.getValue();
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    // Simple Pair implementation since javafx.util.Pair might not be available
    class Pair<K, V> {
        private K key;
        private V value;
        
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() { return key; }
        public V getValue() { return value; }
    }
}

/**
 * Test class to verify all implementations
 */
public class Solution {
    /**
     * Helper method to test a TimeMap implementation
     */
    private static void testTimeMap(String implName, Runnable test) {
        System.out.println("\nTesting " + implName + ":");
        System.out.println("=" + "=".repeat(implName.length() + 9));
        test.run();
    }
    
    /**
     * Helper method to visualize the operations
     */
    private static void visualizeOperations() {
        System.out.println("\nOperation Visualization:");
        System.out.println("========================");
        
        TimeMap tm = new TimeMap();
        
        // Simulate operations with visualization
        String[] keys = {"foo", "bar", "foo", "baz", "foo"};
        String[] values = {"value1", "value2", "value3", "value4", "value5"};
        int[] timestamps = {1, 2, 3, 4, 5};
        
        System.out.println("Set Operations:");
        for (int i = 0; i < keys.length; i++) {
            tm.set(keys[i], values[i], timestamps[i]);
            System.out.printf("  set(%s, %s, %d)%n", keys[i], values[i], timestamps[i]);
        }
        
        System.out.println("\nInternal Storage Structure:");
        // Note: In actual implementation, we'd need to expose the store for visualization
        System.out.println("  HashMap<String, List<TimeValue>>");
        System.out.println("  foo: [(1,value1), (3,value3), (5,value5)]");
        System.out.println("  bar: [(2,value2)]");
        System.out.println("  baz: [(4,value4)]");
        
        System.out.println("\nGet Operations:");
        int[] testTimestamps = {0, 1, 2, 3, 4, 5, 6};
        for (int ts : testTimestamps) {
            String result = tm.get("foo", ts);
            System.out.printf("  get(foo, %d) = \"%s\"%n", ts, result);
        }
    }
    
    /**
     * Performance comparison for different implementations
     */
    private static void compareImplementations() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=======================");
        
        int numOperations = 10000;
        
        // Test HashMap + Binary Search
        long startTime = System.nanoTime();
        TimeMap tm1 = new TimeMap();
        for (int i = 0; i < numOperations; i++) {
            tm1.set("key" + (i % 100), "value" + i, i);
        }
        for (int i = 0; i < numOperations; i++) {
            tm1.get("key" + (i % 100), i);
        }
        long time1 = System.nanoTime() - startTime;
        
        // Test TreeMap implementation
        startTime = System.nanoTime();
        TimeMapTreeMap tm2 = new TimeMapTreeMap();
        for (int i = 0; i < numOperations; i++) {
            tm2.set("key" + (i % 100), "value" + i, i);
        }
        for (int i = 0; i < numOperations; i++) {
            tm2.get("key" + (i % 100), i);
        }
        long time2 = System.nanoTime() - startTime;
        
        System.out.printf("HashMap + Binary Search: %8d ns%n", time1);
        System.out.printf("TreeMap implementation:  %8d ns%n", time2);
        System.out.printf("Ratio: %.2fx%n", (double) time2 / time1);
    }
    
    /**
     * Comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("Testing Time Based Key-Value Store:");
        System.out.println("===================================");
        
        // Test case 1: Basic functionality
        testTimeMap("Basic TimeMap", () -> {
            TimeMap tm = new TimeMap();
            
            tm.set("foo", "bar", 1);
            System.out.println("set(foo, bar, 1)");
            
            String result1 = tm.get("foo", 1);
            System.out.println("get(foo, 1) = " + result1 + " - " + 
                             ("bar".equals(result1) ? "PASSED" : "FAILED"));
            
            String result2 = tm.get("foo", 3);
            System.out.println("get(foo, 3) = " + result2 + " - " + 
                             ("bar".equals(result2) ? "PASSED" : "FAILED"));
            
            tm.set("foo", "bar2", 4);
            System.out.println("set(foo, bar2, 4)");
            
            String result3 = tm.get("foo", 4);
            System.out.println("get(foo, 4) = " + result3 + " - " + 
                             ("bar2".equals(result3) ? "PASSED" : "FAILED"));
            
            String result4 = tm.get("foo", 5);
            System.out.println("get(foo, 5) = " + result4 + " - " + 
                             ("bar2".equals(result4) ? "PASSED" : "FAILED"));
        });
        
        // Test case 2: Non-existent key
        testTimeMap("Non-existent key", () -> {
            TimeMap tm = new TimeMap();
            
            String result = tm.get("nonexistent", 1);
            System.out.println("get(nonexistent, 1) = \"" + result + "\" - " + 
                             ("".equals(result) ? "PASSED" : "FAILED"));
        });
        
        // Test case 3: Multiple values for same key
        testTimeMap("Multiple values per key", () -> {
            TimeMap tm = new TimeMap();
            
            tm.set("key", "value1", 1);
            tm.set("key", "value2", 3);
            tm.set("key", "value3", 5);
            
            System.out.println("get(key, 0) = \"" + tm.get("key", 0) + "\" - " + 
                             ("".equals(tm.get("key", 0)) ? "PASSED" : "FAILED"));
            System.out.println("get(key, 1) = \"" + tm.get("key", 1) + "\" - " + 
                             ("value1".equals(tm.get("key", 1)) ? "PASSED" : "FAILED"));
            System.out.println("get(key, 2) = \"" + tm.get("key", 2) + "\" - " + 
                             ("value1".equals(tm.get("key", 2)) ? "PASSED" : "FAILED"));
            System.out.println("get(key, 3) = \"" + tm.get("key", 3) + "\" - " + 
                             ("value2".equals(tm.get("key", 3)) ? "PASSED" : "FAILED"));
            System.out.println("get(key, 4) = \"" + tm.get("key", 4) + "\" - " + 
                             ("value2".equals(tm.get("key", 4)) ? "PASSED" : "FAILED"));
            System.out.println("get(key, 5) = \"" + tm.get("key", 5) + "\" - " + 
                             ("value3".equals(tm.get("key", 5)) ? "PASSED" : "FAILED"));
            System.out.println("get(key, 6) = \"" + tm.get("key", 6) + "\" - " + 
                             ("value3".equals(tm.get("key", 6)) ? "PASSED" : "FAILED"));
        });
        
        // Test case 4: Multiple keys
        testTimeMap("Multiple keys", () -> {
            TimeMap tm = new TimeMap();
            
            tm.set("key1", "value1", 1);
            tm.set("key2", "value2", 2);
            tm.set("key1", "value1_updated", 3);
            tm.set("key2", "value2_updated", 4);
            
            System.out.println("get(key1, 2) = \"" + tm.get("key1", 2) + "\" - " + 
                             ("value1".equals(tm.get("key1", 2)) ? "PASSED" : "FAILED"));
            System.out.println("get(key1, 4) = \"" + tm.get("key1", 4) + "\" - " + 
                             ("value1_updated".equals(tm.get("key1", 4)) ? "PASSED" : "FAILED"));
            System.out.println("get(key2, 3) = \"" + tm.get("key2", 3) + "\" - " + 
                             ("value2".equals(tm.get("key2", 3)) ? "PASSED" : "FAILED"));
            System.out.println("get(key2, 5) = \"" + tm.get("key2", 5) + "\" - " + 
                             ("value2_updated".equals(tm.get("key2", 5)) ? "PASSED" : "FAILED"));
        });
        
        // Test case 5: Edge cases
        testTimeMap("Edge cases", () -> {
            TimeMap tm = new TimeMap();
            
            // Empty store
            System.out.println("Empty store - get(any, any) = \"" + tm.get("any", 1) + "\" - " + 
                             ("".equals(tm.get("any", 1)) ? "PASSED" : "FAILED"));
            
            // Single entry
            tm.set("single", "value", 100);
            System.out.println("Single entry - get(single, 50) = \"" + tm.get("single", 50) + "\" - " + 
                             ("".equals(tm.get("single", 50)) ? "PASSED" : "FAILED"));
            System.out.println("Single entry - get(single, 100) = \"" + tm.get("single", 100) + "\" - " + 
                             ("value".equals(tm.get("single", 100)) ? "PASSED" : "FAILED"));
            System.out.println("Single entry - get(single, 150) = \"" + tm.get("single", 150) + "\" - " + 
                             ("value".equals(tm.get("single", 150)) ? "PASSED" : "FAILED"));
        });
        
        // Visualization
        visualizeOperations();
        
        // Performance comparison
        compareImplementations();
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insights:");
        System.out.println("1. We need to store multiple values for the same key at different timestamps");
        System.out.println("2. Timestamps are strictly increasing, making lists naturally sorted");
        System.out.println("3. For get operations, we need the largest timestamp <= target timestamp");
        System.out.println("4. This is a perfect use case for binary search");
        
        System.out.println("\nData Structure Choice:");
        System.out.println("HashMap<String, List<TimeValue>>");
        System.out.println("  - HashMap provides O(1) average access to lists for each key");
        System.out.println("  - List stores (timestamp, value) pairs in increasing timestamp order");
        System.out.println("  - Binary search on the list gives O(log n) get operations");
        
        System.out.println("\nBinary Search Strategy:");
        System.out.println("We want the largest timestamp <= target timestamp");
        System.out.println("Approach:");
        System.out.println("  1. If exact timestamp found, return that value");
        System.out.println("  2. Otherwise, track the largest timestamp we've seen that's <= target");
        System.out.println("  3. When binary search ends, return the tracked value");
        
        System.out.println("\nAlternative: TreeMap Approach");
        System.out.println("HashMap<String, TreeMap<Integer, String>>");
        System.out.println("  - TreeMap provides floorKey() method that does exactly what we need");
        System.out.println("  - Simpler implementation but may use more memory");
        System.out.println("  - O(log n) for both set and get operations");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nHashMap + Binary Search Approach:");
        System.out.println("Time Complexity:");
        System.out.println("  set(key, value, timestamp): O(1) average");
        System.out.println("    - HashMap put: O(1) average");
        System.out.println("    - List append: O(1) amortized");
        System.out.println("  get(key, timestamp): O(log n)");
        System.out.println("    - HashMap get: O(1) average");
        System.out.println("    - Binary search on list: O(log n)");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - n = total number of set operations");
        
        System.out.println("\nTreeMap Approach:");
        System.out.println("Time Complexity:");
        System.out.println("  set(key, value, timestamp): O(log n)");
        System.out.println("    - HashMap put: O(1) average");
        System.out.println("    - TreeMap put: O(log n)");
        System.out.println("  get(key, timestamp): O(log n)");
        System.out.println("    - HashMap get: O(1) average");
        System.out.println("    - TreeMap floorKey: O(log n)");
        System.out.println("Space Complexity: O(n)");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start by clarifying requirements:");
        System.out.println("   - Multiple values per key at different timestamps");
        System.out.println("   - Timestamps are strictly increasing");
        System.out.println("   - Need largest timestamp <= target");
        
        System.out.println("2. Propose HashMap + List approach first");
        System.out.println("   - Explain why lists are sorted (strictly increasing timestamps)");
        System.out.println("   - Mention binary search for O(log n) get operations");
        
        System.out.println("3. Implement the solution clearly");
        System.out.println("   - Define TimeValue inner class");
        System.out.println("   - Implement binary search correctly");
        
        System.out.println("4. Discuss alternatives:");
        System.out.println("   - TreeMap approach (simpler but O(log n) for set)");
        System.out.println("   - Collections.binarySearch approach");
        
        System.out.println("5. Analyze time and space complexity");
        System.out.println("6. Handle edge cases (empty store, non-existent keys)");
        
        System.out.println("\nAll tests completed!");
    }
}
