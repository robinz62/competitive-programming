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
        int n = 300000;

        // long[] dp = new long[n+1];
        // dp[0] = 0;
        // dp[1] = 2;

        long[] pow2 = new long[n+1];
        pow2[0] = 1;
        for (int i = 1; i <= n; i++) pow2[i] = pow2[i-1] * 2 % MOD;
        long[] invpow2 = new long[n+1];
        for (int i = 0; i <= n; i++) invpow2[i] = modInverseFermat(pow2[i], MOD);
        // long[][] choose = new long[n+1][n+1];
        // choose[0][0] = 1;
        // for (int i = 1; i <= n; i++) {
        //     choose[i][0] = 1;
        //     for (int j = 1; j <= i; j++) {
        //         choose[i][j] = choose[i-1][j] + choose[i-1][j-1];
        //         if (choose[i][j] >= MOD) choose[i][j] -= MOD;
        //     }
        // }

        // for (int i = 2; i <= n; i++) {
        //     long a = invpow2[i];
        //     long d = 1 + (MOD - invpow2[i]);
        //     if (d >= MOD) d -= MOD;
        //     d = modInverseFermat(d, MOD);

        //     long sum = 1;
        //     for (int j = 0; j < i; j++) {
        //         long b = choose[i][j];
        //         long c = dp[j] + 1;
        //         if (c >= MOD) c -= MOD;
        //         sum += b* c % MOD;
        //         if (sum >= MOD) sum -= MOD;
        //     }

        //     dp[i] = a * sum % MOD * d % MOD;
        // }

        // long[] fact = new long[MAX_N + 1];
        // fact[0] = 1;
        // for (int i = 1; i <= MAX_N; i++) fact[i] = fact[i-1] * i % MOD;
        // long[] invfact = new long[MAX_N + 1];
        // for (int i = 0; i <= MAX_N; i++) invfact = modInverseFermat(fact[i], MOD);
        long[] inv = new long[n+1];
        for (int i = 0; i <= n; i++) inv[i] = modInverseFermat(i, MOD);

        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            n = ri();
            // if (n > 1000) pw.println("-1");
            // else pw.println(dp[n]);

            // Found this online, no idea how it works.
            // E_n = sum from i = 1 to n of (-1^(i+1)) (n choose i) (1 - q^i)^-1 (where q = 1 - p)
            long magic = 0;
            long choose = 1;  // (n choose 0)
            for (int i = 1; i <= n; i++) {
                choose = choose * inv[i] % MOD * (n - i + 1) % MOD;
                long b = 1 + (MOD - invpow2[i]);
                if (b >= MOD) b -= MOD;
                b = modInverseFermat(b, MOD);
                long c = choose * b % MOD;
                if ((i + 1) % 2 == 0) {
                    magic += c;
                    if (magic >= MOD) magic -= MOD;
                } else {
                    magic += (MOD - c);
                    if (magic >= MOD) magic -= MOD;
                }
            }

            pw.println(magic);
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