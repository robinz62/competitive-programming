import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   int overflow
    //   array out of bounds
    //   special cases e.g. n=1?
    void solve() throws IOException {
        int[] RGB = ril(3);
        int[] r = ril(RGB[0]);
        int[] g = ril(RGB[1]);
        int[] b = ril(RGB[2]);
        sort(r);
        sort(g);
        sort(b);
        long[][][] dp = new long[r.length+1][g.length+1][b.length+1];
        for (int i = 0; i <= r.length; i++) {
            int rVal = i-1>=0 ? r[i-1] : -1;
            for (int j = 0; j <= g.length; j++) {
                int gVal = j-1>=0 ? g[j-1] : -1;
                for (int k = 0; k <= b.length; k++) {
                    int bVal = k-1>= 0 ? b[k-1] : -1;
                    if (i-1 >= 0) dp[i][j][k] = Math.max(dp[i][j][k], dp[i-1][j][k]);
                    if (j-1 >= 0) dp[i][j][k] = Math.max(dp[i][j][k], dp[i][j-1][k]);
                    if (k-1 >= 0) dp[i][j][k] = Math.max(dp[i][j][k], dp[i][j][k-1]);
                    if (i-1 >= 0 && j-1 >= 0) dp[i][j][k] = Math.max(dp[i][j][k], rVal * gVal + dp[i-1][j-1][k]);
                    if (i-1 >= 0 && k-1 >= 0) dp[i][j][k] = Math.max(dp[i][j][k], rVal * bVal + dp[i-1][j][k-1]);
                    if (j-1 >= 0 && k-1 >= 0) dp[i][j][k] = Math.max(dp[i][j][k], gVal * bVal + dp[i][j-1][k-1]);
                }
            }
        }
        pw.println(dp[r.length][g.length][b.length]);
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

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Arrays.sort(A);
    }

    void sort(int[] A) {
        Arrays.sort(A);
    }

    int max(int a, int b) {
        return a >= b ? a : b;
    }

    int min(int a, int b) {
        return a <= b ? a : b;
    }
}