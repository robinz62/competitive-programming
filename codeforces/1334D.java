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
            long[] nlr = rll(3);
            long n = nlr[0];
            long l = nlr[1];
            long r = nlr[2];
            long sector = 1;
            long idx = 1;
            while (idx + (n - sector) * 2 < l) {
                idx = idx + (n - sector) * 2;
                sector++;
            }

            boolean left;
            // j represents the value in the pair that isn't the sector value
            long j = sector + (l - idx) / 2 + 1;
            if ((l - idx) % 2 == 0) {
                left = true;
            } else {
                left = false;
            }
            idx = l;
            while (idx <= r) {
                if (idx == n * (n - 1) + 1) {
                    pw.print("1 ");
                    break;
                }
                long val = left ? sector : j;
                pw.print(val + " ");
                if (j == n && !left) {
                    sector++;
                    j = sector + 1;
                } else if (!left) {
                    j++;
                }
                left = !left;
                idx++;
            }
            pw.println();
        }
    }
}