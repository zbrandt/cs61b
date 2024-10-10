import edu.princeton.cs.algs4.BST;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        Node(K key, V value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;
    private int size;

    public BSTMap() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public void put(K key, V value) {
        this.root = putHelper(this.root, key, value);
    }

    private Node putHelper(Node n, K k, V v) {
        if (n == null) {
            this.size += 1;
            return new Node(k, v, null, null);
        }
        if (k.equals(n.key)) {
            n.value = v;
        } else if (k.compareTo(n.key) > 0) {
            n.right = putHelper(n.right, k, v);
        } else if (k.compareTo(n.key) < 0) {
            n.left = putHelper(n.left, k, v);
        }
        return n;
    }

    @Override
    public V get(K key) {
        return getHelper(this.root, key);
    }

    private V getHelper(Node n, K k) {
        if (n == null) {
            return null;
        }
        if (k.equals(n.key)) {
            return n.value;
        } else if (k.compareTo(n.key) < 0) {
            return getHelper(n.left, k);
        } else {
            return getHelper(n.right, k);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return null != get(key);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public Set<K> keySet() {
        // return Set.of();
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        // return null;
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        // return null;
        throw new UnsupportedOperationException();
    }
}
