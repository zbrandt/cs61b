import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    class Node {
        Node prev;
        T item;
        Node next;

        public Node() {
            this.prev = this;
            this.next = this;
        }

        public Node(Node prev, T item, Node next) {
            this.prev = prev;
            prev.next = this;
            this.item = item;
            this.next = next;
            next.prev = this;
        }
    }

    int size;
    Node sentinel;

    public LinkedListDeque61B() {
        this.size = 0;
        this.sentinel = new Node();
    }

    @Override
    public void addFirst(T x) {
        Node old = this.sentinel.next;
        this.sentinel.next = new Node(this.sentinel, x, old);
        this.size++;
    }

    @Override
    public void addLast(T x) {
        Node old = this.sentinel.prev;
        this.sentinel.prev = new Node(old, x, this.sentinel);
        this.size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        for (int i = 0; i < this.size(); i++) {
            Node node = this.sentinel.next;
            for (int j = 0; j < i; j++) {
                node = node.next;
            }
            returnList.add(node.item);
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (this.size == 0) {
            return null;
        } else if (this.size == 1) {
            Node old = this.sentinel.next;
            this.sentinel.next = this.sentinel;
            this.sentinel.prev = this.sentinel;
            old.prev = null;
            old.next = null;
            this.size--;
            return old.item;
        } else {
            Node old = this.sentinel.next;
            Node second = this.sentinel.next.next; // set second's previous
            this.sentinel.next = second;
            second.prev = this.sentinel;
            old.prev = null;
            old.next = null;
            this.size--;
            return old.item;
        }
    }

    @Override
    public T removeLast() {
        if (this.size == 0) {
            return null;
        } else if (this.size == 1) {
            Node old = this.sentinel.prev;
            this.sentinel.next = this.sentinel;
            this.sentinel.prev = this.sentinel;
            old.prev = null;
            old.next = null;
            this.size--;
            return old.item;
        } else {
            Node old = this.sentinel.prev;
            Node penultimate = this.sentinel.prev.prev; // set penultimate's next
            this.sentinel.prev = penultimate;
            penultimate.next = this.sentinel;
            old.next = null;
            old.prev = null;
            this.size--;
            return old.item;
        }
    }

    @Override
    public T get(int index) {
        if (index >= this.size() || index < 0) {
            return null;
        } else {
            Node node = this.sentinel.next;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node.item;
        }
    }

    private T helper(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return helper(node.next, index - 1);
    }

    @Override
    public T getRecursive(int index) {
        if (index >= this.size() || index < 0) {
            return null;
        } else {
            return helper(this.sentinel.next, index);
        }
    }

    // returns an iterator
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private int position;

        public LinkedListIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            T returnItem = get(position);
            position += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof LinkedListDeque61B otherSet) {
            if (this.size != otherSet.size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!get(i).equals(otherSet.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return toList().toString();
    }
}
