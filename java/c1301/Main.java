package c1301;

import java.util.Scanner;

/**
 * http://www.jungol.co.kr/bbs/board.php?bo_table=pbank&wr_id=584&sca=4040
 */
public class Main {
    static int k, n, m;
    static int[] startLocations;
    static int[][] map;
    static int[] numOfVisits;

    public static void main(String[] args) {
        init();

        for (int startLocation : startLocations) {
            int[] visitedLocations = new int[n];
            visitedLocations[startLocation] = 1;
            numOfVisits[startLocation]++;

            int numOfNextLocations = 0;
            int[] nextLocations = new int[n];
            for (int i = 0; i < n; i++) {
                if (startLocation != i && map[startLocation][i] == 1) {
                    numOfNextLocations++;
                    nextLocations[i] = 1;
                }
            }

            visit(numOfNextLocations, nextLocations, visitedLocations);
        }

        int count = 0;
        for (int numOfVisit : numOfVisits) {
            if (numOfVisit == k) {
                count++;
            }
        }

        System.out.println(count);
    }

    private static void visit(int numOfVisitingLocations, int[] visitingLocations, int[] visitedLocations) {
        if (numOfVisitingLocations == 0) {
            return;
        }

        int numOfNextLocations = 0;
        int[] nextLocations = new int[n];
        for (int i = 0; i < n; i++) {
            if (visitingLocations[i] == 0 || visitedLocations[i] == 1) {
                continue;
            }

            visitedLocations[i] = 1;
            numOfVisits[i]++;

            for (int j = 0; j < n; j++) {
                if (visitedLocations[j] == 0 && map[i][j] == 1) {
                    numOfNextLocations++;
                    nextLocations[j] = 1;
                }
            }
        }

        visit(numOfNextLocations, nextLocations, visitedLocations);
    }

    private static void init() {
        Scanner cin = new Scanner(System.in);

        k = cin.nextInt();
        n = cin.nextInt();
        m = cin.nextInt();

        startLocations = new int[k];
        for (int i = 0; i < k; i++) {
            startLocations[i] = cin.nextInt() - 1;
        }

        map = new int[n][n];
        for (int i = 0; i < m; i++) {
            int a = cin.nextInt();
            int b = cin.nextInt();
            map[a - 1][b - 1] = 1;
        }

        numOfVisits = new int[n];
    }
}
