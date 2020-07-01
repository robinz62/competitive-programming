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
            int[] n = ril(3);
            boolean zero = false;
            if (n[0] > 0) {
                for (int i = 0; i < n[0] + 1; i++) {
                    pw.print("0");
                }
                zero = false;
            }
            if (n[2] > 0) {
                for (int i = 0; i < n[2] + 1; i++) {
                    pw.print("1");
                }
                if (n[0] > 0) n[1]--;
                zero = true;
            }
            if (n[0] == 0 && n[2] == 0) {
                pw.print(zero ? "0" : "1");
                zero = !zero;
            }
            for (int i = 0; i < n[1]; i++) {
                if (zero) pw.print("0");
                else pw.print("1");
                zero = !zero;
            }
            pw.println();
        }
    }
}