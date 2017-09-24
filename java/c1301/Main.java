package c1301;

import java.util.Scanner;

/**
 * http://www.jungol.co.kr/bbs/board.php?bo_table=pbank&wr_id=584&sca=4040
 * <p>
 * 오늘은 농부 창호의 소들이 소풍을 가는 날이다! 창호에겐 K(1≤K≤100) 마리의 소들이 있다.
 * 각 소들은 N(1≤N≤1,000)개의 목장 중에 한 곳에 있다. 그리고 목장 사이를 이동하기 위해서는 길을 따라 이동해야 한다. M(1≤M≤10,000)개의 단방향 길이 존재한다.
 * 각 소들은 길을 따라 이동하여 다른 목장으로 갈 수 있다. 그러나 길이 없어서 도달하지 못하는 목장이 있을 수 있다. 모든 소들이 모일 수 있는 목장을 찾아야 한다.
 * <p>
 * K, N, M이 입력된다. 다음 K줄에 걸쳐 각 소들이 위치한 목장의 번호가 주어진다.
 * 다음 M개의 줄에 걸쳐 각 길의 출발 목장과 도착 목장의 번호를 입력한다.
 * <p>
 * 2 4 4
 * 2
 * 3
 * 1 2
 * 1 4
 * 2 3
 * 3 4
 * <p>
 * 모든 소들이 집합할 수 있는 목장의 수를 출력한다.
 * 2
 */
public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int k, n, m;
        k = cin.nextInt();
        n = cin.nextInt();
        m = cin.nextInt();

        int[] locations = new int[k];
        for (int i = 0; i < k; i++) {
            locations[i] = cin.nextInt() - 1;
        }

        int[][] map = new int[n][n];
        for (int i = 0; i < m; i++) {
            int a = cin.nextInt();
            int b = cin.nextInt();
            map[a - 1][b - 1] = 1;
        }

        int[] visitedLocations = new int[n];
        for (int location : locations) {
            int[] visitingLocations = new int[n];
            for (int i = 0; i < n; i++) {
                if (map[location][i] == 1) {
                    visitingLocations[i] = 1;
                }
            }

            map[location][location] = 1;

            while (isNotEmpty(visitingLocations)) {
                visitingLocations = getNextLocations(location, visitedLocations, visitingLocations, map, n);
            }

            visitedLocations[location] = 1;
        }

        int[] unreachables = new int[n];
        for (int location : locations) {
            for (int i = 0; i < n; i++) {
                if (map[location][i] == 0) {
                    unreachables[i] = 1;
                }
            }
        }

        int count = 0;
        for (int unreachable : unreachables) {
            if (unreachable == 0) {
                count++;
            }
        }

        System.out.println(count);
    }

    public static int[] getNextLocations(int location, int[] visitedLocation, int[] visitingLocations, int[][] map, int n) {
        int[] nextLocations = new int[n];
        for (int i = 0; i < n; i++) {
            if (visitingLocations[i] == 0 || i == location) {
                continue;
            }

            if (visitedLocation[i] == 1) {
                for (int j = 0; j < n; j++) {
                    if (map[i][j] == 1) {
                        map[location][j] = 1;
                    }
                }

                continue;
            }

            map[location][i] = 1;

            for (int j = 0; j < n; j++) {
                if (map[i][j] == 1) {
                    nextLocations[j] = 1;
                }
            }
        }

        return nextLocations;
    }

    public static boolean isNotEmpty(int[] array) {
        for (int elem : array) {
            if (elem == 1) {
                return true;
            }
        }

        return false;
    }
}
