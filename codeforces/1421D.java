import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            long[] xy = rll(2);
            long x = xy[0];
            long y = xy[1];
            long[] cost = rll(6);
            long[][] dirs = new long[][]{
                {1, 1}, {0, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, 0}
            };

            if (x == 0 && y == 0) {
                pw.println("0");
                continue;
            }
            
            // ATTEMPT 1: conjecture that at most 2 directions ever needed
            // INCORRECT?
            long ans = Long.MAX_VALUE;
            // 1 direction
            for (int i = 0; i < 6; i++) {
                long a = dirs[i][0];
                long c = dirs[i][1];
                if (x != 0) {
                    if (a == 0) continue;
                    long times = x / a;
                    if (c * times != y) continue;
                    if (times < 0) continue;
                    ans = Math.min(ans, times * cost[i]);
                } else {
                    if (c == 0) continue;
                    long times = y / c;
                    if (a * times != x) continue;
                    if (times < 0) continue;
                    ans = Math.min(ans, times * cost[i]);
                }
            }

            for (int i = 0; i < 6; i++) {
                long a = dirs[i][0];
                long c = dirs[i][1];
                for (int j = 0; j < 6; j++) {
                    if (i == j) continue;
                    long b = dirs[j][0];
                    long d = dirs[j][1];
                    long det = a*d - b*c;
                    if (det == 0) continue;

                    long A = x * d - y * b;
                    long B = -x * c + a * y;
                    if (A % det != 0 || B % det != 0) continue;
                    A /= det;
                    B /= det;
                    if (A < 0 || B < 0) continue;
                    ans = Math.min(ans, A * cost[i] + B * cost[j]);
                }
            }
            pw.println(ans);
        }
    }

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
}