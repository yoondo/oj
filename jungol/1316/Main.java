import java.util.*;

public class Main {
    static final int MAX_SIZE = 88888888;

    static int n;
    static Index sortedIndex;

    private static int solve(Index[] visitingIndexes, int numOfVisitingIndexes) {
        int numOfNextIndexes = 0;
        Index[] nextIndexes = new Index[MAX_SIZE];
        for (int i = 0; i < numOfVisitingIndexes; i++) {
            Index index = visitingIndexes[i];

            if (index.isVisited()) {
                continue;
            }

            if (index.isSame(sortedIndex)) {
                return 0;
            }

            index.markAsVisited();

            numOfNextIndexes += decompose(index, nextIndexes, numOfNextIndexes);
        }

        if (numOfNextIndexes > 0) {
            int result = solve(nextIndexes, numOfNextIndexes);
            if (result >= 0) {
                return result + 1;
            }
        }

        return -1;
    }

    private static int decompose(Index index, Index[] nextIndexes, int numOfNextIndexes) {
        int count = 0;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                nextIndexes[numOfNextIndexes + count++] = index.flip(j, i);
            }
        }

        return count;
    }

    private static class Index {
        final static boolean[] visitedIndexes = new boolean[MAX_SIZE];

        final int n;
        final int[] index;
        final int key;

        public Index(int[] index, int n) {
            this.index = index;
            this.n = n;
            this.key = generateKey(index, n);
        }

        public Index flip(int start, int length) {
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

        public boolean isVisited() {
            return visitedIndexes[key];
        }

        public void markAsVisited() {
            visitedIndexes[key] = true;
        }

        public boolean isSame(Index index) {
            for (int i = 0; i < n; i++) {
                if (this.index[i] != index.index[i]) {
                    return false;
                }
            }

            return true;
        }

        private static int generateKey(int[] index, int n) {
            int key = 0;
            for (int i = 0; i < n; i++) {
                int temp = index[i];
                for (int j = 0; j < i; j++) {
                    temp *= 10;
                }
                key += temp;
            }

            return key;
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

        Index[] nextIndexes = new Index[1];
        nextIndexes[0] = new Index(index, n);

        System.out.println(solve(nextIndexes, 1));
    }
}