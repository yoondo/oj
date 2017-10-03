import java.util.Scanner;

public class Main {
    static int n;
    static int[] series;

    private static int solve() {
        int count = 0;
        boolean asc = true;
        for (int i = 1; i < n; i++) {
            if (i == 1) {
                asc = series[0] < series[1];
            }

            if (asc) {
                if (series[i - 1] > series[i]) {
                    count++;
                    asc = false;
                }
            } else {
                if (series[i - 1] < series[i]) {
                    count++;
                    asc = true;
                }
            }
        }

        if (!asc) {
            count++;
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        series = new int[n];
        for (int i = 0; i < n; i++) {
            series[i] = in.nextInt();
        }

        System.out.println(solve());
    }
}