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
        long[] nm = rll(2);
        long n = nm[0];
        long m = nm[1];
        long ans = 0;
        if (m > n) ans = (m-n)%MOD * (n%MOD) % MOD;
        m = Math.min(m, n-1);
        if (m == 0) {
            pw.println(ans);
            return;
        }
        ans += (n%MOD) * (m%MOD) % MOD;
        ans %= MOD;

        long sub = 0;

        long i = 1;
        while (i * i <= n && i <= m) {
            sub = (sub + (i%MOD) * (n/i%MOD) % MOD) % MOD;
            i++;
        }

        for (long x = 1; x * x <= n; x++) {
            long l = Math.max(n / (x+1) + 1, i);
            long r = Math.min(m, n / x);
            long len = r - l + 1;
            if (len <= 0) continue;

            long pair = l + r;
            if (len % 2 == 0) len /= 2;
            else pair /= 2;
            long sum = (pair%MOD) * (len%MOD) % MOD;
            sub = (sub + sum*x%MOD) % MOD;
        }

        ans = (ans + MOD - sub) % MOD;

        pw.println(ans);
    }
}