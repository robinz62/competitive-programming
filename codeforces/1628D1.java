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
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nmk = ril(3);
            int n = nmk[0];
            int m = nmk[1];
            int k = nmk[2];

            // Shouldn't matter what k is.
            // Answer is probably dp[n][m] * k.
            if (k == 0) {
                pw.println("0");
                continue;
            }

            double[][] dp = new double[n+1][m+1];
            long[][] ans = new long[n+1][m+1];
            for (int i = 1; i <= n; i++) {
                dp[i][0] = 0;
                ans[i][0] = 0;
            }
            for (int i = 1; i <= n; i++) {
                if (i <= m) {
                    dp[i][i] = i;
                    ans[i][i] = i;
                }
            }

            long inv2 = modInverseFermat(2, MOD);
            
            for (int i = 2; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if (i == j) continue;
                    double delta = (dp[i-1][j] - dp[i-1][j-1]) / 2.0;
                    long delta2 = ans[i-1][j] + (MOD - ans[i-1][j-1]);
                    if (delta2 >= MOD) delta2 -= MOD;
                    delta2 = delta2 * inv2 % MOD;
                    delta = Math.max(delta, 0);
                    delta = Math.min(delta, 1);
                    double a = dp[i-1][j] - delta;
                    double b = dp[i-1][j-1] + delta;
                    if (a >= b) {
                        dp[i][j] = a;
                        ans[i][j] = ans[i-1][j] + (MOD - delta2);
                        if (ans[i][j] >= MOD) ans[i][j] -= MOD;
                    } else {
                        dp[i][j] = b;
                        ans[i][j] = ans[i-1][j-1] + delta2;
                        if (ans[i][j] >= MOD) ans[i][j] -= MOD;
                    }
                }
            }
            // for (double[] row : dp) pw.println(Arrays.toString(row));
            pw.println(ans[n][m] * k % MOD);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public static long modPow(long base, long exponent, long m) {
        long ans = 1;
        base = base % m;
        while (exponent > 0) {
            if ((exponent & 1) == 1) ans = (ans * base) % m;
            exponent >>= 1;
            base = (base * base) % m;
        } 
        return ans;
    }

    // Computes a^(-1) mod m, the modular inverse of a (modulo m). This
    // algorithm is based on Fermat's little theorem. m must be prime. O(log m).
    public static long modInverseFermat(long a, long m) {
        return modPow(a, m - 2, m);
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