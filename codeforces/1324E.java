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
        int[] nhlr = ril(4);
        int n = nhlr[0];
        int h = nhlr[1];
        int l = nhlr[2];
        int r = nhlr[3];
        int[] a = ril(n);
        int[][] dp = new int[n][h];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
        dp[0][a[0]] = a[0] >= l && a[0] <= r ? 1 : 0;
        dp[0][a[0]-1] = a[0]-1 >= l && a[0]-1 <= r ? 1 : 0;
        for (int i = 1; i < n; i++) {
            for (int prev = 0; prev < h; prev++) {
                if (dp[i-1][prev] == -1) continue;
                int t1 = (prev+a[i])%h;
                int t2 = (prev+a[i]-1)%h;
                dp[i][t1] = Math.max(dp[i][t1], t1 >= l && t1 <= r ? dp[i-1][prev]+1 : dp[i-1][prev]);
                dp[i][t2] = Math.max(dp[i][t2], t2 >= l && t2 <= r ? dp[i-1][prev]+1 : dp[i-1][prev]);
            }
        }
        int ans = 0;
        for (int i = 0; i < h; i++) ans = Math.max(ans, dp[n-1][i]);
        pw.println(ans);
    }
}