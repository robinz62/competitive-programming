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

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            long[] a = new long[n];
            long[] b = new long[n];
            for (int i = 0; i < n; i++) {
                long[] ab = rll(2);
                a[i] = ab[0];
                b[i] = ab[1];
            }
            // c[i] = a[i] - b[i-1]
            long[] c = new long[n];
            c[0] = Math.max(0, a[0] - b[n-1]);
            for (int i = 1; i < n; i++) {
                c[i] = Math.max(a[i] - b[i-1], 0);
            }
            long sumc = 0;
            for (long ci : c) sumc += ci;
            long ans = Long.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                ans = Math.min(ans, a[i] + sumc - c[i]);
            }
            pw.println(ans);
        }
    }
}