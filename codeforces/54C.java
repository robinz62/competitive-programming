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
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        long[][] intervals = new long[n][];
        for (int i = 0; i < n; i++) intervals[i] = rll(2);
        int k = ri();
        int need = (n * k + 99) / 100;

        double[] probabilities = new double[n];
        for (int i = 0; i < n; i++) probabilities[i] = probabilityDigitOne(intervals[i][0], intervals[i][1]);

        // dp[i][j] is the probability that of intervals[0..i], j of them are
        // equal to 1.
        double[][] dp = new double[n][n+1];
        dp[0][0] = 1.0 - probabilities[0];
        dp[0][1] = probabilities[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i-1][0] * (1.0 - probabilities[i]);
            for (int j = 1; j <= n; j++) {
                dp[i][j] = probabilities[i] * dp[i-1][j-1] + (1 - probabilities[i]) * dp[i-1][j];
            }
        }

        double ans = 0;
        for (int j = need; j <= n; j++) {
            ans += dp[n-1][j];
        }
        printDouble(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    // Returns the probability that a number in [l, r] has first digit 1.
    double probabilityDigitOne(long l, long r) {
        long count = 0;
        long pow10 = 1;
        while (pow10 <= r) {
            long lower = Math.max(l, pow10);
            if (r <= pow10 * 2 - 1) {
                count += r - lower + 1;
                break;
            }
            count += Math.max(pow10 * 2 - 1 - lower + 1, 0);
            pow10 *= 10;
        }
        return (double) count / (r - l + 1);
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