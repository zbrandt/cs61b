package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private int capacity;
    private double loadFactor;
    private int size = 0;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.loadFactor = loadFactor;
        this.capacity = initialCapacity;
        this.buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i += 1) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    @Override
    public void put(K key, V value) {
        if ((double) this.size / this.buckets.length >= this.loadFactor) {
            resize();
        }
        int i = Math.floorMod(key.hashCode(), this.buckets.length);
        if (containsKey(key)) {
            for (Node n : this.buckets[i]) {
                if (n.key.equals(key)) {
                    n.value = value;
                }
            }
        } else {
            this.buckets[i].add(new Node(key, value));
            this.size += 1;
        }
    }

    private void resize() {
        int oldSize = this.size;
        Collection<Node>[] oldBuckets = this.buckets;
        this.buckets = new Collection[this.buckets.length * 2];
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = createBucket();
        }
        for (Collection<Node> c : oldBuckets) {
            for (Node n : c) {
                put(n.key, n.value);
            }
        }
        this.size = oldSize;
    }

    @Override
    public V get(K key) {
        int i = Math.floorMod(key.hashCode(), this.buckets.length);
        for (Node n : this.buckets[i]) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int i = Math.floorMod(key.hashCode(), this.buckets.length);
        for (Node n : this.buckets[i]) {
            if (n.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.buckets = new Collection[this.capacity];
        for (int i = 0; i < this.capacity; i += 1) {
            this.buckets[i] = createBucket();
        }
        this.size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
