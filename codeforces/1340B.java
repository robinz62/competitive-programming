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
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(br.readLine(), 2);
        }
        // dp[i][k] is max i can make with digits a[i..n-1] and k segments
        // dp[n][.] is a dummy number for easier base case
        int[][] dp = new int[n+1][k+1];
        Arrays.fill(dp[n], -1);
        dp[n][0] = 0;
        for (int i = n-1; i >= 0; i--) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = -1;
                for (int d = 0; d <= 9; d++) {
                    int dsegment = digitToSegment[d];
                    if ((dsegment | a[i]) != dsegment) continue;
                    int need = Integer.bitCount(dsegment ^ a[i]);
                    if (j >= need && dp[i+1][j-need] != -1) dp[i][j] = d;
                }
            }
        }
        if (dp[0][k] == -1) pw.println("-1");
        else {
            for (int i = 0; i < n; i++) {
                pw.print(dp[i][k]);
                int need = Integer.bitCount(digitToSegment[dp[i][k]] ^ a[i]);
                k -= need;
            }
            pw.println();
        }
    }

    static int[] digitToSegment = new int[]{
        0b1110111,
        0b0010010,
        0b1011101,
        0b1011011,
        0b0111010,
        0b1101011,
        0b1101111,
        0b1010010,
        0b1111111,
        0b1111011,
    };
}