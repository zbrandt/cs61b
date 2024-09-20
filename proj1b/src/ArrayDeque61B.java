import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size += 1;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = x;
        nextLast = Math.floorMod(nextLast + 1, items.length);
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        int i = nextFirst + 1;
        while (returnList.size() < size) {
            if (i == items.length) {
                i = 0;
            }
            returnList.add(items[i]);
            i++;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size > 0) {
            int i = Math.floorMod(nextFirst + 1, items.length);
            nextFirst = i;
            size -= 1;
            T x = items[i];
            if ((double) size / items.length < 0.25 && items.length >= 16) {
                resize(items.length / 2);
            }
            return x;
        } else {
            return null;
        }
    }

    @Override
    public T removeLast() {
        if (size > 0) {
            int i = Math.floorMod(nextLast - 1, items.length);
            nextLast = i;
            size -= 1;
            T x = items[i];
            if ((double) size / items.length < 0.25 && items.length >= 16) {
                resize(items.length / 2);
            }
            return x;
        } else {
            return null;
        }
    }

    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        }
        int i = Math.floorMod(nextFirst + 1 + index, items.length);
        return items[i];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    // use get and a for loop, copy items into a new larger items object
    public void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        for (int i = 0; i < size; i++) {
            int j = Math.floorMod(nextFirst + 1 + i, a.length);
            a[j] = this.get(i);
        }
        items = a;
        nextFirst = Math.floorMod(nextFirst, a.length);
        nextLast = Math.floorMod(nextFirst + 1 + size, a.length);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int position;

        public ArrayIterator() {
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
        if (other instanceof ArrayDeque61B otherSet) {
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
