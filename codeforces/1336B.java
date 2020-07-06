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
            int[] rgb = ril(3);
            int nr = rgb[0];
            int ng = rgb[1];
            int nb = rgb[2];
            int[] r = ril(nr);
            int[] g = ril(ng);
            int[] b = ril(nb);
            Arrays.sort(r);
            Arrays.sort(g);
            Arrays.sort(b);
            long ans = Long.MAX_VALUE;
            // r middle
            for (int ri : r) {
                int idx = Arrays.binarySearch(g, ri);
                int gl = idx < 0 ? -idx-1-1 : idx;
                int gr = idx < 0 ? -idx-1 : idx;
                idx = Arrays.binarySearch(b, ri);
                int bl = idx < 0 ? -idx-1-1 : idx;
                int br = idx < 0 ? -idx-1 : idx;
                if (gl >= 0 && br < b.length) ans = Math.min(ans, dist(ri, g[gl], b[br]));
                if (bl >= 0 && gr < g.length) ans = Math.min(ans, dist(ri, b[bl], g[gr]));
            }
            // g middle
            int[] temp = r;
            r = g;
            g = temp;
            for (int ri : r) {
                int idx = Arrays.binarySearch(g, ri);
                int gl = idx < 0 ? -idx-1-1 : idx;
                int gr = idx < 0 ? -idx-1 : idx;
                idx = Arrays.binarySearch(b, ri);
                int bl = idx < 0 ? -idx-1-1 : idx;
                int br = idx < 0 ? -idx-1 : idx;
                if (gl >= 0 && br < b.length) ans = Math.min(ans, dist(ri, g[gl], b[br]));
                if (bl >= 0 && gr < g.length) ans = Math.min(ans, dist(ri, b[bl], g[gr]));
            }
            // b middle
            temp = r;
            r = b;
            b = temp;
            for (int ri : r) {
                int idx = Arrays.binarySearch(g, ri);
                int gl = idx < 0 ? -idx-1-1 : idx;
                int gr = idx < 0 ? -idx-1 : idx;
                idx = Arrays.binarySearch(b, ri);
                int bl = idx < 0 ? -idx-1-1 : idx;
                int br = idx < 0 ? -idx-1 : idx;
                if (gl >= 0 && br < b.length) ans = Math.min(ans, dist(ri, g[gl], b[br]));
                if (bl >= 0 && gr < g.length) ans = Math.min(ans, dist(ri, b[bl], g[gr]));
            }
            pw.println(ans);
        }
    }

    long dist(int a, int b, int c) {
        long d1 = (long) (a - b) * (a - b);
        long d2 = (long) (b - c) * (b - c);
        long d3 = (long) (c - a) * (c - a);
        return d1 + d2 + d3;
    }
}