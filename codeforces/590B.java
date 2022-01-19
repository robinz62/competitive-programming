import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] xyxy = ril(4);
        int x1 = xyxy[0];
        int y1 = xyxy[1];
        int x2 = xyxy[2];
        int y2 = xyxy[3];
        int[] vt = ril(2);
        int v_max = vt[0];
        int t = vt[1];

        int[] v = ril(2);
        int vx = v[0];
        int vy = v[1];
        int[] w = ril(2);
        int wx = w[0];
        int wy = w[1];

        // Case 1: can we get to target <= t.
        int dx = x2 - x1;
        int dy = y2 - y1;
        double zx = (double) dx / t - vx;
        double zy = (double) dy / t - vy;
        if (zx * zx + zy * zy <= v_max * v_max) {
            double l = 0;
            double r = t;
            double best = Double.MAX_VALUE;
            for (int i = 0; i < 100; i++) {
                double m = (l + r) / 2;
                zx = (double) dx / m - vx;
                zy = (double) dy / m - vy;
                if (zx * zx + zy * zy <= v_max * v_max) {
                    best = m;
                    r = m;
                } else {
                    l = m;
                }
            }
            printDouble(best);
            return;
        }

        // Case 2: get as far as possible in first phase. This is a circular region, offset
        // by the wind speed: t * (vx, vy).
        double x_offseted = (double) vx * t + x1;
        double y_offseted = (double) vy * t + y1;
        double radius = (double) v_max * t;
        double l = 0;
        double r = Integer.MAX_VALUE;  // this is probably big enough.
        double best = Double.MAX_VALUE;
        for (int i = 0; i < 100; i++) {
            double m = (l + r) / 2;
            double radius2 = (double) v_max * m;
            double x_offseted2 = x2 - (double) wx * m;
            double y_offseted2 = y2 - (double) wy * m;
            double DX = x_offseted2 - x_offseted;
            double DY = y_offseted2 - y_offseted;
            double D = Math.sqrt(DX * DX + DY * DY);
            if (radius + radius2 >= D) {
                best = m;
                r = m;
            } else {
                l = m;
            }
        }
        printDouble(t + best);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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
        pw.printf("%.16f", d);
    }
}