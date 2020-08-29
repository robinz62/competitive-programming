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
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            int[] a = ril(n-1);
            int[] levels = new int[n];
            levels[0] = 1;
            for (int ai : a) levels[ai]++;

            // Corner case
            boolean foundEmpty = false;
            boolean done = false;
            for (int i = 0; i < n; i++) {
                if (levels[i] == 0) {
                    foundEmpty = true;
                } else {
                    if (foundEmpty) done = true;
                }
            }
            if (done) {
                pw.println("0");
                continue;
            }

            long extra = 0;
            for (int x : levels) extra += (long) x * (x-1) / 2;
            if (extra < m - (n-1)) {
                pw.println("0");
                continue;
            }

            long ans = 1;
            for (int i = 1; i < n && levels[i] != 0; i++) {
                ans *= modPow(levels[i-1], levels[i], MOD);
                ans %= MOD;
            }
            ans *= choose(extra, m - (n-1));
            ans %= MOD;
            pw.println(ans);
        }
    }

    long choose(long n, long k) {
        long ans = 1;
        for (long i = n; i > n - k; i--) ans = ans * i % MOD;
        long[] fact = new long[(int)k+1];
        fact[0] = 1;
        for (int i = 1; i <= k; i++) fact[i] = fact[i-1] * i % MOD;
        return ans * modInverseFermat(fact[(int)k], MOD) % MOD;
    }

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
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    int max(int a, int b) {
        return a >= b ? a : b;
    }

    int min(int a, int b) {
        return a <= b ? a : b;
    }
}