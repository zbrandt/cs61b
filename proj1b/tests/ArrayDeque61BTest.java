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
     void testAdd() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();

         // add first from empty
         d.addFirst(1);
         assertThat(d.get(0) == 1).isTrue();

         // add first non empty
         d.addFirst(2);
         assertThat(d.get(0) == 2).isTrue();

         // add first trigger resize
         d.addFirst(3);
         d.addFirst(4);
         d.addFirst(5);
         d.addFirst(6);
         d.addFirst(7);
         d.addFirst(8);
         d.addFirst(9);
         assertThat(d.get(0) == 9).isTrue();

         ArrayDeque61B<Integer> f = new ArrayDeque61B<>();

         // add last from empty
         f.addLast(1);
         assertThat(f.get(f.size() - 1) == 1).isTrue();

         // add last nonempty
         f.addLast(2);
         assertThat(f.get(f.size() - 1) == 2).isTrue();

         // add last trigger resize
         f.addLast(3);
         f.addLast(4);
         f.addLast(5);
         f.addLast(6);
         f.addLast(7);
         f.addLast(8);
         f.addLast(9);
         assertThat(f.get(f.size() - 1) == 9).isTrue();
     }

     @Test
     void testAddAfterRemove() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();
         d.addFirst(1);

         // add first after remove to empty
         d.removeFirst();
         d.addFirst(1);
         assertThat(d.get(0) == 1).isTrue();

         // add last after remove to empty
         d.removeLast();
         d.addLast(1);
         assertThat(d.get(d.size() - 1) == 1).isTrue();
     }

     @Test
     void testRemove() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();
         for (int i = 1; i <= 17; i++) {
             d.addFirst(i);
         }

         // remove first trigger resize
         d.removeFirst();
         assertThat(d.get(0) == 16).isTrue();

         ArrayDeque61B<Integer> f = new ArrayDeque61B<>();
         f.addFirst(3);
         f.addFirst(2);
         f.addFirst(1);

         // remove first
         f.removeFirst();
         assertThat(f.get(0) == 2).isTrue();

         // remove first to one
         f.removeFirst();
         assertThat(f.get(0) == 3).isTrue();

         // remove first to empty
         f.removeFirst();
         assertThat(f.isEmpty()).isTrue();

         ArrayDeque61B<Integer> g = new ArrayDeque61B<>();
         for (int i = 1; i <= 17; i++) {
             g.addLast(i);
         }

         // remove last trigger resize
         g.removeLast();
         assertThat(g.get(g.size() - 1) == 16).isTrue();

         ArrayDeque61B<Integer> h = new ArrayDeque61B<>();
         h.addLast(1);
         h.addLast(2);
         h.addLast(3);

         // remove last
         h.removeLast();
         assertThat(h.get(h.size() - 1) == 2).isTrue();

         // remove last to one
         h.removeLast();
         assertThat(h.get(h.size() - 1) == 1).isTrue();

         // remove last to empty
         h.removeLast();
         assertThat(h.isEmpty()).isTrue();

     }

     @Test
     void testGet() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();

         // get valid
         d.addFirst(1);
         assertThat(d.get(0) == 1).isTrue();

         // get out of bonds negative
         assertThat(d.get(-1) == null).isTrue();

         // get out of bonds large
         assertThat(d.get(1) == null).isTrue();
     }

     @Test
     void testSize() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();

         // size
         assertThat(d.size() == 0).isTrue();

         // size after remove from empty
         d.removeFirst();
         assertThat(d.size() == 0).isTrue();

         // size after remove to empty
         d.addFirst(1);
         d.removeFirst();
         assertThat(d.size() == 0).isTrue();
     }

     @Test
     void testIsEmpty() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();
         assertThat(d.size() == 0).isTrue();
         assertThat(d.isEmpty()).isTrue();

         d.addFirst(1);
         assertThat(d.size() == 1).isTrue();
         assertThat(d.isEmpty()).isFalse();
     }

     @Test
     void testToList() {
         ArrayDeque61B<Integer> d = new ArrayDeque61B<>();

         // to list empty
         assertThat(d.toList().isEmpty()).isTrue();

         // to list nonempty
         d.addFirst(1);
         d.addLast(2);
         d.addFirst(3);
         assertThat(d.toList()).containsExactly(3, 1, 2).inOrder();
     }

//     @Test
//     void testAdvancedResize() {
//         // resize up and resize down
//     }

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
