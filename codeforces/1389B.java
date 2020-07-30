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
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nkz = ril(3);
            int n = nkz[0];
            int k = nkz[1];
            int z = nkz[2];
            int[] a = ril(n);
            long[] prefix = new long[n];
            prefix[0] = a[0];
            long[] consecs = new long[n-1];
            for (int i = 0; i < n - 1; i++) consecs[i] = a[i] + a[i+1];
            long[] consecsPrefix = new long[n-1];
            consecsPrefix[0] = consecs[0];
            for (int i = 1; i < n - 1; i++) consecsPrefix[i] = Math.max(consecsPrefix[i-1], consecs[i]);
            for (int i = 1; i < n; i++) prefix[i] = prefix[i-1] + a[i];
            long[] maxPrefix = new long[n];
            maxPrefix[0] = a[0];
            for (int i = 1; i < n; i++) maxPrefix[i] = Math.max(maxPrefix[i-1], a[i]);
            long ans = 0;
            for (int zi = 0; zi <= z; zi++) {
                // go right on last
                if (k - zi * 2 >= 1) {
                    long basesum = prefix[k - zi * 2];
                    long consec = consecsPrefix[Math.max(0, k - zi * 2 - 1)];
                    ans = Math.max(ans, basesum + consec * zi);
                }
                // end on left 1
                if (zi >= 1 && k - zi * 2 >= 0 && k - zi * 2 + 1 < n) {
                    long basesum = prefix[k - zi * 2 + 1];
                    long consec = zi - 1 <= 0 ? 0 : consecsPrefix[k - zi * 2];
                    ans = Math.max(ans, basesum + consec * (zi - 1) + a[k - zi * 2]);
                    // pw.println(zi + " " + ans);
                }
            }
            pw.println(ans);
        }
    }
}