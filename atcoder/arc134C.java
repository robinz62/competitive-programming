import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 998244353;

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
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];  // <= 200
        int[] a = ril(n);
        
        long countOthers = 0;
        for (int ai : a) countOthers += ai;
        countOthers -= a[0];
        if ((long) a[0] - countOthers - k < 0) {
            pw.println("0");
            return;
        }
        long excess = (long) a[0] - countOthers - k;

        long[] fact = new long[k];
        fact[0] = 1;
        for (int i = 1; i < k; i++) fact[i] = fact[i-1] * i % MOD;
        long[] invFact = new long[k];
        for (int i = 0; i < k; i++) invFact[i] = modInverseFermat(fact[i], MOD);

        // For i > 1, this is the prefix product [a[i], a[i]+k-1]
        long[][] factorials = new long[n][k];
        a[0] = (int) excess;
        for (int i = 0; i < n; i++) {
            factorials[i][0] = 1;
            if (1 < k) factorials[i][1] = a[i] + 1;
            for (int j = 2; j < k; j++) {
                factorials[i][j] = factorials[i][j-1] * (a[i] + j) % MOD;
            }
        }

        long ans = 1;
        for (int i = 0; i < n; i++) {
            long c = factorials[i][k-1] * invFact[k-1] % MOD;
            ans = ans * c % MOD;
        }

        pw.println(ans);
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