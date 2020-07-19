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
            long[] lrm = rll(3);
            long l = lrm[0];
            long r = lrm[1];
            long m = lrm[2];

            for (long a = l; a <= r; a++) {
                long rem = m % a;
                if (m / a > 0 && rem >= l - r && rem <= r - l) {
                    if (rem >= 0) {
                        long b = l + rem;
                        long c = l;
                        pw.println(a + " " + b + " " + c);
                        break;
                    } else {
                        long b = l;
                        long c = l + Math.abs(rem);
                        pw.println(a + " " + b + " " + c);
                        break;
                    }
                }
                rem = m % a - a;
                if (rem >= l - r && rem <= r - l) {
                    if (rem >= 0) {
                        long b = l + rem;
                        long c = l;
                        pw.println(a + " " + b + " " + c);
                        break;
                    } else {
                        long b = l;
                        long c = l + Math.abs(rem);
                        pw.println(a + " " + b + " " + c);
                        break;
                    }
                }
            }
        }
    }
}