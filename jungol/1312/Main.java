import java.util.Scanner;

public class Main {
    static int n;
    static int[] kaist = new int[1000];
    static int[] postech = new int[1000];

    private static int solve() {
        int result = 0;

        int pn = 0;
        for (int kn = 0; kn < n && pn < n; kn++) {
            for (int j = n - 1; kn < j; j--) {
                if (kaist[j - 1] < kaist[j]) {
                    swap(kaist, j - 1, j);
                }
            }

            do {
                for (int j = n - 1; pn < j; j--) {
                    if (postech[j - 1] < postech[j]) {
                        swap(postech, j - 1, j);
                    }
                }

                if (kaist[kn] > postech[pn++]) {
                    result += kaist[kn];
                    break;
                }
            } while (pn < n);
        }

        return result;
    }

    private static void swap(int[] elems, int i, int j) {
        int temp = elems[i];
        elems[i] = elems[j];
        elems[j] = temp;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int s = in.nextInt();
        int[] result = new int[s];
        for (int i = 0; i < s; i++) {
            n = in.nextInt();

            for (int j = 0; j < n; j++) {
                kaist[j] = in.nextInt();
            }

            for (int j = 0; j < n; j++) {
                postech[j] = in.nextInt();
            }

            result[i] = solve();
        }

        for (int i = 0; i < s; i++) {
            System.out.println(result[i]);
        }
    }
}