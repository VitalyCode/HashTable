import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String source = "CACABABABCCCAABAC";
        System.out.println(hasRepeats(source, 4)); // true, т.к. "ABAB" повторяется дважды
        System.out.println(hasRepeats(source, 5)); // false, никаких повторений подстрок длины 5 нет
    }

    public static boolean hasRepeats(String source, int size) {
        Set<LazyString> seenSlices = new HashSet<>();
        LazyString currentSlice = null;

        for (int i = 0; i <= source.length() - size; ++i) {
            LazyString slice;
            if (currentSlice == null) {

                slice = new LazyString(source, i, i + size);
            } else {

                slice = currentSlice.shiftRight();
            }

            if (!seenSlices.add(slice)) {
                return true;
            }

            currentSlice = slice;
        }

        return false;
    }
}

class LazyString {
    private final String source;
    private final int start;
    private final int end;
    private int hash;

    public LazyString(String source, int start, int end) {
        this.source = source;
        this.start = start;
        this.end = end;

        int sum = 0;
        for (int i = start; i < end; ++i) {
            sum += source.charAt(i);
        }
        this.hash = sum;
    }

    public LazyString shiftRight() {
        LazyString next = new LazyString(this.source, this.start + 1, this.end + 1);

        next.hash = this.hash - source.charAt(start) + source.charAt(end);
        return next;
    }

    public int length() {
        return end - start;
    }

    public boolean equals(LazyString other) {
        if (other == null || length() != other.length())
            return false;

        for (int i = 0; i < length(); ++i) {
            if (source.charAt(start + i) != other.source.charAt(other.start + i))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LazyString) {
            return equals((LazyString) obj);
        }
        return false;
    }
}
