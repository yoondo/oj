import java.util.Scanner;

/**
 * http://olympiads.win.tue.nl/ioi/ioi98/contest/day2/polygon/dossier.pdf
 * <p>
 * - 메모리 펑션(DP)을 고민하기에 앞서 재귀호출로써의 로직을 분명히 세우는 데 집중해야 한다.
 * - 기본적인 해결법과 DP적 고려를 함께 진행하다 보면 사고의 효율이 떨어질 때가 많다.
 * - 문제의 의미에 대해서도 로직 설계와 분리해 가장 먼저 신중히 짚고 넘어가야 한다.
 * - 문제 이해의 측면에서 이번의 경우 음수/양수 값과 +/* 연산의 관계를 고려할 때 min/max가 필요함을 너무 늦게 발견했다.
 */
public class Main {
    static int n;
    static int[] v;
    static String[][] ops;
    static Result[][] cache;

    static Result solve(int v1, int v2) {
        Result result = cache[v1][v2];
        if (result != null) {
            return result;
        }

        if (v1 == v2) {
            result = new Result(v[v1], v[v2]);
        } else if (v1 + 1 == v2 || (v1 == n - 1 && v2 == 0)) {
            result = calc(v1, v2);
        } else {
            int i = v1;
            while(i != v2) {
                int j = i == n - 1 ? 0 : i + 1;
                Result r1 = solve(v1, i);
                Result r2 = solve(j, v2);
                result = calc(r1, r2, result, ops[i][j]);

                if (i == n - 1) {
                    i = 0;
                } else {
                    i++;
                }
            }
        }

        cache[v1][v2] = result;
        return result;
    }

    static Result calc(int v1, int v2) {
        int r;
        switch(ops[v1][v2]) {
            case "t":
                r = v[v1] + v[v2];
                break;
            default:
                r = v[v1] * v[v2];
        }

        return new Result(r, r);
    }

    static Result calc(Result r1, Result r2, Result p, String op) {
        int min;
        int max;
        switch (op) {
            case "t":
                min = r1.min + r2.min;
                max = r1.max + r2.max;
                break;
            default:
                min = max = r1.min * r2.min;

                int r = r1.min * r2.max;
                min = getMin(min, r);
                max = getMax(max, r);

                r = r1.max * r2.min;
                min = getMin(min, r);
                max = getMax(max, r);

                r = r1.max * r2.max;
                min = getMin(min, r);
                max = getMax(max, r);
        }

        if (p == null) {
            return new Result(min, max);
        } else {
            return new Result(getMin(min, p.min), getMax(max, p.max));
        }
    }

    static int getMin(int a, int b) {
        return a < b ? a : b;
    }

    static int getMax(int a, int b) {
        return a < b ? b : a;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        n = in.nextInt();
        v = new int[n];
        ops = new String[n][n];
        cache = new Result[n][n];

        for (int i = 0; i < n; i++) {
            if (i == 0) {
                ops[0][n - 1] = ops[n - 1][0] = in.next();
            } else {
                ops[i][i - 1] = ops[i - 1][i] = in.next();
            }

            v[i] = in.nextInt();
        }

        int max = solve(0, n - 1).max;
        for(int i = 1; i < n; i++) {
            max = getMax(max, solve(i, i - 1).max);
        }

        System.out.println(max);
    }

    static class Result {
        int min;
        int max;

        Result() {
        }

        Result(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
}
