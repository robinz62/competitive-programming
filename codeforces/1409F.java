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
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        char[] s = rs();
        char[] t = rs();
        char a = t[0];
        char b = t[1];

        if (a == b) {
            int bad = 0;
            for (int c : s) if (c != a) bad++;
            int good = n - bad + Math.min(bad, k);
            pw.println(good * (good-1) / 2);
            return;
        }

        // dp[i][j][k] is max value for s[0..i] using j operations and there are
        // k occurrences of a.
        int[][][] dp = new int[n][k+2][n+1];
        for (int i = 0; i < n; i++) for (int j = 0; j <= k; j++) for (int c = 0; c <= n; c++) dp[i][j][c] = Integer.MIN_VALUE;
        
        if (s[0] == a) {
            dp[0][0][1] = 0;
            dp[0][1][0] = 0;
        } else if (s[0] == b) {
            dp[0][0][0] = 0;
            dp[0][1][1] = 0;
        } else {
            dp[0][0][0] = 0;
            dp[0][1][0] = 0;
            dp[0][1][1] = 0;
        }

        for (int i = 1; i < n; i++) {
            for (int ki = 0; ki <= Math.min(k, i+1); ki++) {
                for (int c = 0; c <= i+1; c++) {
                    if (s[i] == a) {
                        // do nothing or switch to b
                        if (c-1 >= 0) dp[i][ki][c] = Math.max(dp[i][ki][c], dp[i-1][ki][c-1]);
                        if (ki-1 >= 0) dp[i][ki][c] = Math.max(dp[i][ki][c], dp[i-1][ki-1][c] + c);
                    } else if (s[i] == b) {
                        // do nothing, or switch to a
                        dp[i][ki][c] = dp[i-1][ki][c] + c;
                        if (ki-1>=0 && c-1>=0) dp[i][ki][c] = Math.max(dp[i][ki][c], dp[i-1][ki-1][c-1]);
                    } else {
                        // do nothing, switch to a, or switch to b
                        dp[i][ki][c] = dp[i-1][ki][c];
                        if (ki-1>=0 && c-1>=0) dp[i][ki][c] = Math.max(dp[i][ki][c], dp[i-1][ki-1][c-1]);
                        if (ki-1>=0) dp[i][ki][c] = Math.max(dp[i][ki][c], dp[i-1][ki-1][c] + c);
                    }
                }
            }
        }

        int ans = 0;
        for (int ki = 0; ki <= k; ki++) {
            for (int c = 0; c <= n; c++) {
                ans = Math.max(ans, dp[n-1][ki][c]);
            }
        }
        pw.println(ans);
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