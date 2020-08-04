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
        int[][][] dp = new int[31][31][51];
        dp[1][1][1] = 0;
        for (int i = 1; i <= 30; i++) {
            for (int j = 1; j <= 30; j++) {
                for (int k = 1; k <= 50; k++) {
                    if (k > i * j) continue;
                    if (i * j == k) continue;
                    dp[i][j][k] = Integer.MAX_VALUE;
                    for (int x = 1; x <= i - 1; x++) {  // length of top split
                        for (int k1 = 0; k1 <= k; k1++)  {  // how many the top handles
                            if (k1 > x * j || k-k1 > (i-x) * j) continue;
                            dp[i][j][k] = Math.min(dp[i][j][k], dp[x][j][k1] + dp[i-x][j][k-k1] + j * j);
                        }
                    }
                    for (int y = 1; y <= j - 1; y++) {  // length of left split
                        for (int k1 = 0; k1 <= k; k1++)  {  // how many the left handles
                            if (k1 > i * y || k-k1 > i * (j-y)) continue;
                            dp[i][j][k] = Math.min(dp[i][j][k], dp[i][y][k1] + dp[i][j-y][k-k1] + i * i);
                        }
                    }
                }
            }
        }

        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nmk = ril(3);
            int n = nmk[0];
            int m = nmk[1];
            int k = nmk[2];
            pw.println(dp[n][m][k]);
        }
    }
}