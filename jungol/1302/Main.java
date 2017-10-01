import java.util.Scanner;

/**
 * http://www.jungol.co.kr/bbs/board.php?bo_table=pbank&wr_id=585&sca=4040
 * http://main.edu.pl/en/archive/oi/14/grz
 */
public class Main {
    static int n;
    static int[][] map;
    static int[][] visitedPositions;
    static int upCount, downCount;

    private static void solve() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (visitedPositions[i][j] == 0) {
                    int[][] nextPositions = new int[n][n];
                    nextPositions[i][j] = 1;
                    Result result = new Result();
                    move(nextPositions, map[i][j], result);
                    if (result.up > 0 && result.down == 0) {
                        upCount++;
                    } else if (result.up == 0 && result.down > 0) {
                        downCount++;
                    }
                }
            }
        }
    }

    private static void move(int[][] visitingPositions, int height, Result result) {
        int numOfNextPositions = 0;
        int[][] nextPositions = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (visitingPositions[i][j] == 0 || visitedPositions[i][j] == 1) {
                    continue;
                }

                visitedPositions[i][j] = 1;

                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if ((k == i && l == j) || k < 0 || l < 0 || n <= k || n <= l) {
                            continue;
                        }

                        if (map[k][l] == height) {
                            nextPositions[k][l] = 1;
                            numOfNextPositions++;
                            continue;
                        }

                        if (!result.isCompleted()) {
                            if (map[k][l] < height) {
                                result.down++;
                            } else if (height < map[k][l]) {
                                result.up++;
                            }
                        }
                    }
                }
            }
        }

        if (numOfNextPositions == 0) {
            return;
        }

        move(nextPositions, height, result);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        n = in.nextInt();
        map = new int[n][n];
        visitedPositions = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = in.nextInt();
            }
        }

        upCount = downCount = 0;

        solve();

        System.out.println(downCount + " " + upCount);
    }

    private static class Result {
        int up = 0;
        int down = 0;

        boolean isCompleted() {
            return up > 0 && down > 0;
        }
    }
}