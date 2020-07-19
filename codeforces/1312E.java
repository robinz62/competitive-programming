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
        int n = ri();
        int[] a = ril(n);
        
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
        for (int i = 0; i < n; i++) dp[i][i] = a[i];
        for (int i = 0; i < n - 1; i++) if (a[i] == a[i+1]) dp[i][i+1] = a[i]+1;
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i+len-1;
                for (int m = i; m < j; m++) {
                    if (dp[i][m] != -1 && dp[m+1][j] != -1 && dp[i][m] == dp[m+1][j]) {
                        dp[i][j] = dp[i][m]+1;
                        break;
                    }
                }
            }
        }

        int[][] dp2 = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(dp2[i], Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) dp2[i][i] = 1;
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i+len-1;
                if (dp[i][j] != -1) {
                    dp2[i][j] = 1;
                    continue;
                }
                for (int m = i; m < j; m++) {
                    dp2[i][j] = Math.min(dp2[i][j], dp2[i][m] + dp2[m+1][j]);
                }
            }
        }

        pw.println(dp2[0][n-1]);
    }
}