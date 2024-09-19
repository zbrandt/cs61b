import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }
     @Test
     void addFirstAndLast() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();
         d.addFirst(1);
         d.addFirst(2);
         d.addLast(3);
         assertThat(d.get(2) == 3).isTrue();
     }

     @Test
     void isEmptyAndSize() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();
         assertThat(d.size() == 0).isTrue();
         assertThat(d.isEmpty()).isTrue();

         d.addFirst(1);
         assertThat(d.size() == 1).isTrue();
         assertThat(d.isEmpty()).isFalse();
     }

     @Test
     void toList() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();
         d.addFirst(1);
         d.addLast(2);
         d.addFirst(3);
         assertThat(d.toList()).containsExactly(3, 1, 2).inOrder();
     }

     @Test
     void removeFirstAndLast() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();
         d.addFirst(1);
         d.addLast(2);
         d.addFirst(3);

         d.removeLast();
         assertThat(d.toList()).containsExactly(3, 1).inOrder();
         assertThat(d.size() == 2).isTrue();
         d.removeFirst();
         assertThat(d.toList()).containsExactly(1).inOrder();
         assertThat(d.size() == 1).isTrue();
     }

     @Test
    void resize() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();
         d.addFirst(1);
         d.addLast(2);
         d.addFirst(3);
         d.addFirst(4);
         d.addFirst(5);
         d.addFirst(6);
         d.addFirst(7);
         d.addFirst(8);
         d.addFirst(9);
         assertThat(d.toList()).containsExactly(9, 8, 7, 6, 5, 4, 3, 1, 2).inOrder();

     }

     @Test
    public void addLastTestBasicWithoutToList() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1).containsExactly("front", "middle", "back");
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void testToString() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        String str = lld1.toString();
        assertThat(str).isEqualTo("[front, middle, back]");

    }

}
