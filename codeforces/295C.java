import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nk = ril(2);
        int n = nk[0];
        k = nk[1];

        int[] w = ril(n);
        for (int i = 0; i < n; i++) w[i] = w[i] / 50;
        k /= 50;

        int count1 = 0;
        int count2 = 0;
        for (int wi : w) if (wi == 1) count1++; else count2++;

        choose = new int[n+1][n+1];
        choose[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            choose[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                choose[i][j] = choose[i-1][j] + choose[i-1][j-1];
                if (choose[i][j] >= MOD) choose[i][j] -= MOD;
            }
        }

        dp = new int[n+1][n+1][2];
        ways = new int[n+1][n+1][2];
        visited = new int[n+1][n+1][2];

        Deque<int[]> q = new ArrayDeque<>();
        q.addLast(new int[]{count1, count2, 0});
        visited[count1][count2][0] = 1;
        ways[count1][count2][0] = 1;
        int dist = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] u = q.removeFirst();
                int l1 = u[0];
                int l2 = u[1];
                int r1 = count1 - l1;
                int r2 = count2 - l2;
                int side = u[2];
                if (l1 == 0 && l2 == 0) {
                    pw.println(dp[0][0][1]);
                    pw.println(ways[0][0][1]);
                    return;
                }
                visited[l1][l2][side] = 2;

                if (side == 0) {
                    for (int take2 = 0; take2 <= l2; take2++) {
                        if (take2 * 2 > k) break;
                        for (int take1 = 0; take1 <= l1; take1++) {
                            if (take1 == 0 && take2 == 0) continue;  // must be nonempty
                            if (take2 * 2 + take1 > k) break;
                            if (visited[l1-take1][l2-take2][1] == 2) continue;
                            if (visited[l1-take1][l2-take2][1] == 0) {
                                q.addLast(new int[]{l1-take1, l2-take2, 1});
                                visited[l1-take1][l2-take2][1] = 1;
                            }
                            dp[l1-take1][l2-take2][1] = dist+1;
                            ways[l1-take1][l2-take2][1] += (int) ((long) ways[l1][l2][side] * choose[l1][take1] % MOD * choose[l2][take2] % MOD);
                            ways[l1-take1][l2-take2][1] %= MOD;
                        }
                    }
                } else {
                    for (int take2 = 0; take2 <= r2; take2++) {
                        if (take2 * 2 > k) break;
                        for (int take1 = 0; take1 <= r1; take1++) {
                            if (take1 == 0 && take2 == 0) continue;  // must be nonempty
                            if (take2 * 2 + take1 > k) break;
                            if (visited[l1+take1][l2+take2][0] == 2) continue;
                            if (visited[l1+take1][l2+take2][0] == 0) {
                                q.addLast(new int[]{l1+take1, l2+take2, 0});
                                visited[l1+take1][l2+take2][0] = 1;
                            }
                            dp[l1+take1][l2+take2][0] = dist+1;
                            ways[l1+take1][l2+take2][0] += (int) ((long) ways[l1][l2][side] * choose[r1][take1] % MOD * choose[r2][take2] % MOD);
                            ways[l1+take1][l2+take2][0] %= MOD;
                        }
                    }
                }

            }
            dist++;
        }

        pw.println("-1");
        pw.println("0");
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int k;

    int[][][] dp;
    int[][][] ways;
    int[][][] visited;  // 0 = unvisited, 1 = enqueued, 2 = completely done

    int[][] choose;

    // Template code below

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.solve();
        m.close();
    }

    void close() throws IOException {
        pw.flush();
        pw.close();
        br.close();
    }

    int ri() throws IOException {
        return Integer.parseInt(br.readLine().trim());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine().trim());
    }

    int[] ril(int n) throws IOException {
        int[] nums = new int[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            int x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    long[] rll(int n) throws IOException {
        long[] nums = new long[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            long x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    int[] rkil() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return rll(x);
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}