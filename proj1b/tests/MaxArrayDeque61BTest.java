import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    private static class IntegerSizeComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer integer, Integer t1) {
            return integer - t1;
        }
    }



    @Test
    public void testMaxArrayDeque() {

        // max default and max nonempty
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");

        // max empty
        MaxArrayDeque61B<Integer> d = new MaxArrayDeque61B<>(new IntegerSizeComparator());
        assertThat(d.max()).isEqualTo(null);

        // max different comparator
        MaxArrayDeque61B<Integer> f = new MaxArrayDeque61B<>(new IntegerSizeComparator());
        f.addFirst(1);
        f.addFirst(2);
        assertThat(f.max()).isEqualTo(2);
    }
    
}
