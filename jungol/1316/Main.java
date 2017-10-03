import java.util.*;

public class Main {
    static final int MAX_SIZE = 88888888;

    static int n;
    static Index sortedIndex;

    private static int solve(List<Index> visitingIndexes) {
        List<Index> nextIndexes = new ArrayList<>();
        for (Index index : visitingIndexes) {
            if (index.isVisited()) {
                continue;
            }

            if (index.isSame(sortedIndex)) {
                return 0;
            }

            index.markAsVisited();

            decompose(index, nextIndexes);
        }

        if (!nextIndexes.isEmpty()) {
            int result = solve(nextIndexes);
            if (result >= 0) {
                return result + 1;
            }
        }

        return -1;
    }

    private static void decompose(Index index, List<Index> nextIndexes) {
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                nextIndexes.add(index.flip(j, i));
            }
        }
    }

    private static class Index {
        final static Set<Index> visitedIndexes = new HashSet<>();

        final int n;
        final int[] index;

        Index(int[] index, int n) {
            this.index = index;
            this.n = n;
        }

        Index flip(int start, int length) {
            int[] newIndex = new int[n];

            for (int i = 0; i < start; i++) {
                newIndex[i] = index[i];
            }

            for (int i = 0; i < length; i++) {
                newIndex[start + i] = index[start + length - 1 - i];
            }

            for (int i = start + length; i < n; i++) {
                newIndex[i] = index[i];
            }

            return new Index(newIndex, n);
        }

        boolean isSame(Index that) {
            for (int i = 0; i < n; i++) {
                if (this.index[i] != that.index[i]) {
                    return false;
                }
            }

            return true;
        }

        boolean isVisited() {
            return visitedIndexes.contains(this);
        }

        void markAsVisited() {
            visitedIndexes.add(this);
        }

        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            if (!super.equals(object)) return false;

            Index index1 = (Index) object;

            if (!java.util.Arrays.equals(index, index1.index)) return false;

            return true;
        }

        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + Arrays.hashCode(index);
            return result;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        int[] elems = new int[n];
        for (int i = 0; i < n; i++) {
            elems[i] = in.nextInt();
        }

        int[] index = new int[n];
        int[] sortedIndex = new int[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
            sortedIndex[i] = i;
        }

        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j > i; j--) {
                if (elems[j - 1] > elems[j]) {
                    int temp = elems[j];
                    elems[j] = elems[j - 1];
                    elems[j - 1] = temp;
                    temp = sortedIndex[j];
                    sortedIndex[j] = sortedIndex[j - 1];
                    sortedIndex[j - 1] = temp;
                }
            }
        }

        Main.sortedIndex = new Index(sortedIndex, n);

        List<Index> nextIndexes = new ArrayList<>();
        nextIndexes.add(new Index(index, n));

        System.out.println(solve(nextIndexes));
    }
}