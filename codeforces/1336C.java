import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 998244353;

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

    long[] powers;
    void solve() throws IOException {
        powers = new long[3001];
        powers[0] = 1;
        for (int i = 1; i <= 3000; i++) powers[i] = powers[i-1] * 2 % MOD;

        char[] S = rs();
        char[] T = rs();
        int n = S.length;
        int m = T.length;

        // dp[i][j] is number of ways for S[0..i] to match T[j..]
        long[][] dp = new long[n][m];
        for (int j = 0; j < m; j++) {
            if (S[0] == T[j]) dp[0][j] = 2;
        }

        for (int i = 1; i < n; i++) {
            for (int j = m-1; j >= 0; j--) {
                if (S[i] == T[j]) {  // push char i to the front
                    if (j == m-1) dp[i][j] += powers[i];
                    else dp[i][j] += dp[i-1][j+1];
                }
                if (i+j >= m || S[i] == T[j + i]) {  // push char i to the back
                    dp[i][j] += dp[i-1][j];
                }
                dp[i][j] %= MOD;
            }
        }

        long ans = 0;
        for (int i = m-1; i < n; i++) {
            ans = (ans + dp[i][0]) % MOD;
        }
        pw.println(ans);
    }
}