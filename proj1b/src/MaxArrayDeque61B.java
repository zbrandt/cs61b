import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    Comparator<T> comp;

    public MaxArrayDeque61B(Comparator<T> c) {
        comp = c;
    }

    public T max() {
        if (size() == 0) {
            return null;
        }
        T max = get(0);
        for (int i = 0; i < size(); i++) {
            if (comp.compare(max, get(i)) < 0) {
                max = get(i);
            }
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if (size() == 0) {
            return null;
        }
        T max = get(0);
        for (int i = 0; i < size(); i++) {
            if (c.compare(max, get(i)) < 0) {
                max = get(i);
            }
        }
        return max;
    }
}
