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
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int[] a = ril(n);
            int[] b = ril(n);

            if (n == 1) {
                pw.println(0);
                continue;
            }

            // Want to minimize sum of products a[i] * a[j]
            // dp[i][p] is the best answer for [0..i] where the prefix sum for a is p.

            int[][] dp = new int[n+1][100 * n + 1];
            for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
            dp[0][0] = 0;

            int totalsum = 0;
            for (int i = 0; i < n; i++) {
                for (int aprefix = 0; aprefix < dp[0].length; aprefix++) {
                    if (dp[i][aprefix] == Integer.MAX_VALUE) continue;

                    // don't swap
                    int cand = dp[i][aprefix] + a[i] * aprefix + b[i] * (totalsum - aprefix);
                    dp[i+1][aprefix + a[i]] = Math.min(dp[i+1][aprefix + a[i]], cand);

                    // swap
                    if (a[i] != b[i]) {
                        cand = dp[i][aprefix] + b[i] * aprefix + a[i] * (totalsum - aprefix);
                        dp[i+1][aprefix + b[i]] = Math.min(dp[i+1][aprefix + b[i]], cand);
                    }
                }

                totalsum += a[i] + b[i];
            }

            int ans = Integer.MAX_VALUE;
            for (int x : dp[n]) ans = Math.min(ans, x);
            ans *= 2;
            
            for (int ai : a) ans += ai * ai * (n-1);
            for (int bi : b) ans += bi * bi * (n-1);
            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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