import java.util.Scanner;

public class Main {
    static int n;
    static int[] series;

    private static int solve() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                continue;
            }

            if (series[i] < series[i - 1]) {
                if (i == 1) {
                    count++;
                } else {
                    count += 2;
                }
            }
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