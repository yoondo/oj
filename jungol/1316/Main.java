import java.util.*;

/**
 * http://www.jungol.co.kr/bbs/board.php?bo_table=pbank&sca=4040
 * <p>
 * 결국 이번에도 다음과 같은 이슈로 어려움을 겪음
 * - 반복되는 메모리 할당에 따른 시간 지연
 * - 결과 판단 시점을 최대한 앞당기지 못하고 다음 단계로 지연
 * - 이런 이슈로 Collection을 사용해 해결해야 했음 - 훨씬 더 나은 대안이 있을 듯
 */
public class Main {
    static final int MAX_SIZE = 88888888;

    static int n;
    static Index sortedIndex;

    private static int solve(Set<Index> visitingIndexes) {
        Set<Index> nextIndexes = new HashSet<>();
        for (Index index : visitingIndexes) {
            if (explore(index, nextIndexes)) {
                return 1;
            }
        }

        if (!nextIndexes.isEmpty()) {
            int result = solve(nextIndexes);
            if (result >= 0) {
                return result + 1;
            }
        }

        return -1;
    }

    private static boolean explore(Index index, Set<Index> nextIndexes) {
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                Index nextIndex = index.flip(j, i);

                if (nextIndex.isVisited()) {
                    continue;
                }

                if (nextIndex.isSame(sortedIndex)) {
                    return true;
                }

                nextIndexes.add(nextIndex);
                nextIndex.markAsVisited();
            }
        }

        return false;
    }

    private static class Index {
        final static boolean[] visitedIndexes = new boolean[MAX_SIZE];

        final int n;
        final int[] index;
        final int key;

        Index(int[] index, int n) {
            this.index = index;
            this.n = n;
            this.key = generateKey(index, n);
        }

        Index flip(int start, int length) {
            int[] newIndex = new int[n];

            System.arraycopy(index, 0, newIndex, 0, start);
            System.arraycopy(index, start + length, newIndex, start + length, n - (start + length));

            for (int i = 0; i < length; i++) {
                newIndex[start + i] = index[start + length - 1 - i];
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
            return visitedIndexes[key];
        }

        void markAsVisited() {
            visitedIndexes[key] = true;
        }

        public boolean equals(Object object) {
            return isSame((Index) object);
        }

        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + Arrays.hashCode(index);
            return result;
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

        Index initialIndex = new Index(index, n);
        Set<Index> nextIndexes = new HashSet<>();
        nextIndexes.add(initialIndex);

        if (initialIndex.isSame(Main.sortedIndex)) {
            System.out.println(0);
            return;
        }

        System.out.println(solve(nextIndexes));
    }
}