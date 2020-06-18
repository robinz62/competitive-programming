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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int[] nk = readIntLine();
        int n = nk[0];
        int k = nk[1];
        if (k > n) {
            pw.println(0);
            return;
        }
        long[] f = new long[n+1];
        f[0] = 1;
        for (int i = 1; i <= n; i++) {
            f[i] = f[i-1] * i % MOD;
        }
        long ans = 0;
        long modInvKf = modInverseGcd(f[k-1], MOD);
        for (int i = 1; i <= n && n / i >= k; i++) {
            long add = f[n / i - 1] * modInvKf % MOD * modInverseGcd(f[n / i - k], MOD) % MOD;
            ans = (ans + add) % MOD;
        }
        pw.println(ans);
    }

    public static long modInverseGcd(long a, long m) {
        if (m == 1) return 0;

        long m0 = m;
        long y = 0;
        long x = 1;
        while (a > 1) {
            long q = a / m;
            long t = m;
            m = a % m;
            a = t;
            t = y;
            y = x - q * y;
            x = t;
        }
        if (x < 0) x += m0;
        return x; 
    }
}