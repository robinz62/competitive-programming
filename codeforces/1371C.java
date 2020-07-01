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
            long[] abnm = rll(4);
            long a = abnm[0];
            long b = abnm[1];
            long n = abnm[2];
            long m = abnm[3];
            if (a < b) {
                long temp = a;
                a = b;
                b = temp;
            }
            if (a >= b) {
                long init = Math.min(a - b, n);
                a -= init;
                n -= init;
                // now either a == b or we have used up all person1
                if (n == 0) {
                    if (Math.min(a, b) >= m) pw.println("Yes");
                    else pw.println("No");
                    continue;
                }
                // we still have n person1's left
                long pairs = Math.min(n, m);
                if (pairs > Math.min(a, b)) {
                    pw.println("No");
                    continue;
                }
                a -= pairs;
                b -= pairs;
                n -= pairs;
                m -= pairs;
                if (n == 0) {
                    if (Math.min(a, b) >= m) pw.println("Yes");
                    else pw.println("No");
                    continue;
                }
                // m == 0
                if (a + b >= n) pw.println("Yes");
                else pw.println("No");
            }
        }
    }
}