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
            int[] nk = ril(2);
            int n = nk[0];
            int k = nk[1];
            int[] a = ril(n);
            if (a.length == 1 && a[0] == k) {
                pw.println("yes");
                continue;
            }
            boolean exists = false;
            for (int i = 0; i < a.length; i++) {
                if (a[i] < k) a[i] = -1;
                else if (a[i] == k) {
                    a[i] = 0;
                    exists = true;
                } else a[i] = 1;
            }
            if (!exists) {
                pw.println("no");
                continue;
            }
            boolean possible = false;
            for (int i = 0; !possible && i < n - 1; i++) {
                if (a[i] == 0 && a[i+1] >= 0 || a[i+1] == 0 && a[i] >= 0) possible = true;
            }
            for (int i = 0; !possible && i < n - 2; i++) {
                int x = 0;
                if (a[i] >= 0) x++;
                if (a[i+1] >= 0) x++;
                if (a[i+2] >= 0) x++;
                if (x >= 2) possible = true;
            }
            pw.println(possible ? "yes" : "no");
        }
    }
}