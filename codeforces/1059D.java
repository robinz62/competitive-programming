import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        int[][] lairs = new int[n][];
        boolean hasP = false;
        boolean hasN = false;
        double l = 1e7;
        double r = -1e7;
        for (int i = 0; i < n; i++) {
            lairs[i] = ril(2);
            if (lairs[i][1] > 0) hasP = true;
            else hasN = true;
            l = Math.min(l, lairs[i][0]);
            r = Math.max(r, lairs[i][0]);
        }
        if (hasP && hasN) {
            pw.println("-1");
            return;
        }
        if (hasN) {
            for (int[] lair : lairs) lair[1] = -lair[1];
        }

        // iterations
        double ans = Double.MAX_VALUE;
        for (int it = 0; it < 100; it++) {
            double m1 = l + (r - l) / 3;
            double m2 = r - (r - l) / 3;
            double rm1 = rad(lairs, m1);
            double rm2 = rad(lairs, m2);
            ans = Math.min(ans, Math.min(rm1, rm2));
            if (rm1 < rm2) {
                r = m2;
            } else {
                l = m1;
            }
        }
        printDouble(ans);
    }

    double rad(int[][] lairs, double mid) {
        double r = 0;
        for (int[] l : lairs) {
            double x = Math.abs(mid - l[0]);
            double y = l[1];
            double rcand = x * x + y * y;
            rcand /= 2 * y;
            r = Math.max(r, rcand);
        }
        return r;
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
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
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
        pw.printf("%f", d);
    }
}