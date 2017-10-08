import java.util.Scanner;

/**
 * (규칙 1) 점 (x,y)가 S에 속해 있다면, 점 (x+1,y+1)을 S에 추가한다.
 *  - where x < p, (x - y) == (p - q)
 * (규칙 2) 점 (x,y)가 S에 속해 있고, x와 y가 모두 짝수이면, 점 (x/2, y/2)를 S에 추가한다.
 *  - where x % 2 == y % 2, (x - y) / 2 == (p - q)
 * (규칙 3) 두 점 (x,y)와 (y,z)가 S에 속해 있다면, 점 (x,z)를 S에 추가한다.
 *  - 결국 규칙 1/2를 통해 가장 작은 '차이값'을 찾아낸 후, 해당 값을 n번 곱해서 (p - q)에 도달할 수 있는지 확인하는 문제
 */
public class Main {
    static int x;
    static int y;
    static int diff;

    static int getMinimumDifference(int x, int y) {
        int diff = x - y;
        while (diff != 0 && x % 2 == y % 2 && x > 0 && y > 0) {
            if (x % 2 == 0) {
                x /= 2;
                y /= 2;
            } else {
                x = (x + 1) / 2;
                y = (y + 1) / 2;
            }

            diff /= 2;
        }

        return diff;
    }

    static boolean solve(int p, int q) {
        int r = p - q;
        if ((diff > 0 && r < 0) || (diff < 0 && r > 0) || (r == 0 && diff != 0) || (r != 0 && diff == 0)) {
            return false;
        }

        return r == diff || r % diff == 0;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        x = in.nextInt();
        y = in.nextInt();

        diff = getMinimumDifference(x, y);

        boolean[] results = new boolean[5];
        for (int i = 0; i < 5; i++) {
            results[i] = solve(in.nextInt(), in.nextInt());
        }

        for (int i = 0; i < 5; i++) {
            if(results[i]) {
                System.out.println("Y");
            } else {
                System.out.println("N");
            }
        }
    }
}
