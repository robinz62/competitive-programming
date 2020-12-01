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
            int[] nx = ril(2);
            int n = nx[0];
            int X = nx[1];
            int[] a = ril(n);
            int[][][] dp = new int[n][501][501];
            // dp[i][j][k] is min cost to make a[i..n-1] all >= j when x is equal to k
            
            // Default to INF
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= 500; j++) {
                    for (int k = 0; k <= 500; k++) {
                        dp[i][j][k] = Integer.MAX_VALUE;
                    }
                }
            }
            for (int x = 0; x <= 500; x++) {
                // do operation
                if (a[n-1] > x) {
                    for (int j = x; j >= 0; j--) {
                        dp[n-1][j][x] = Math.min(dp[n-1][j][x], 1);
                    }
                }
                // no operations
                for (int j = a[n-1]; j >= 0; j--) {
                    dp[n-1][j][x] = Math.min(dp[n-1][j][x], 0);
                }
            }

            for (int i = n-2; i >= 0; i--) {
                for (int x = 0; x <= 500; x++) {
                    // use operation here
                    if (a[i] > x) {
                        for (int j = x; j >= 0; j--) {
                            int sub = dp[i+1][x][a[i]];
                            if (sub != Integer.MAX_VALUE) dp[i][j][x] = Math.min(dp[i][j][x], 1 + dp[i+1][x][a[i]]);
                        }
                    }
                    // don't use
                    int sub = dp[i+1][a[i]][x];
                    for (int j = a[i]; j >= 0; j--) {
                        if (sub != Integer.MAX_VALUE) dp[i][j][x] = Math.min(dp[i][j][x], sub);
                    }
                }
            }

            int ans = Integer.MAX_VALUE;
            for (int j = 0; j <= 500; j++) {
                ans = Math.min(ans, dp[0][j][X]);
            }
            pw.println(ans == Integer.MAX_VALUE ? "-1" : ans);
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

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}