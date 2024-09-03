import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int sum = 0;
        for (Integer i : L) {
            sum += i;
        }

        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evens = new ArrayList<>();
        for (Integer i : L) {
            if (i % 2 == 0) {
                evens.add(i);
            }
        }

        return evens;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> common = new ArrayList<>();
        int size = Math.max(L1.size(), L2.size());
        for (int i = 0; i < size; i++) {
            int n = L1.get(i);
            if (L2.contains(n)) {
                common.add(n);
            }
        }

        return common;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int count = 0;
        for (String s : words) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == c) {
                    count++;
                }
            }
        }
        
        return count;
    }
}
