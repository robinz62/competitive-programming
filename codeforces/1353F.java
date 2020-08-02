import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 1000000007;

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
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
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

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            long[][] A = new long[n][];
            for (int i = 0; i < n; i++) {
                A[i] = rll(m);
            }

            long best = Long.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    long start = A[i][j] - (i + j);
                    if (start > A[0][0]) continue;
                    long ret = works(A, start);
                    if (ret != Long.MAX_VALUE) {
                        best = Math.min(best, ret);
                    }
                }
            }
            pw.println(best);
        }
    }

    long works(long[][] A, long start) {
        long[][] dp = new long[A.length][A[0].length];
        dp[0][0] = A[0][0] - start;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (i == 0 && j == 0) continue;
                dp[i][j] = Long.MAX_VALUE;
                long expectHeight = start + i + j;
                if (A[i][j] < expectHeight) {
                    continue;
                }
                if (i-1 >= 0 && dp[i-1][j] != Long.MAX_VALUE) {
                    dp[i][j] = Math.min(dp[i][j], A[i][j] - expectHeight + dp[i-1][j]);
                }
                if (j-1 >= 0 && dp[i][j-1] != Long.MAX_VALUE) {
                    dp[i][j] = Math.min(dp[i][j], A[i][j] - expectHeight + dp[i][j-1]);
                }
            }
        }
        return dp[A.length-1][A[0].length-1];
    }
}