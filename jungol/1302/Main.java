import java.util.Scanner;

/**
 * http://www.jungol.co.kr/bbs/board.php?bo_table=pbank&wr_id=585&sca=4040
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
                        if (k == i && j == l) {
                            continue;
                        }

                        if (explore(k, l, nextPositions, height, result)) {
                            numOfNextPositions++;
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

    private static boolean explore(int i, int j, int[][] nextPositions, int height, Result result) {
        if (i < 0 || j < 0 || n <= i || n <= j) {
            return false;
        }

        if (map[i][j] == height) {
            nextPositions[i][j] = 1;
            return true;
        }

        if (map[i][j] < height) {
            result.down++;
        } else if (height < map[i][j]) {
            result.up++;
        }

        return false;
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

        boolean isDraw() {
            return (up > 0 && down > 0) || (up == 0 && down == 0);
        }
    }
}