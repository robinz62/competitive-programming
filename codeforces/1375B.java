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
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            int[][] a = new int[n][];
            for (int i = 0; i < n; i++) {
                a[i] = ril(m);
            }
            boolean ok = true;
            if (a[0][0] > 2 || a[n-1][0] > 2 || a[0][m-1] > 2 || a[n-1][m-1] > 2) ok = false;
            for (int i = 1; ok && i < n-1; i++) {
                if (a[i][0] > 3) ok = false;
                if (a[i][m-1] > 3) ok = false;
            }
            for (int j = 1; ok && j < m-1; j++) {
                if (a[0][j] > 3) ok = false;
                if (a[n-1][j] > 3) ok = false;
            }
            for (int i = 1; ok && i < n-1; i++) {
                for (int j = 1; ok && j < m-1; j++) {
                    if (a[i][j] > 4) ok = false;
                }
            }
            if (!ok) {
                pw.println("NO");
                continue;
            }
            pw.println("YES");
            a[0][0] = a[n-1][0] = a[0][m-1] = a[n-1][m-1] = 2;
            for (int i = 1; i < n-1; i++) {
                a[i][0] = a[i][m-1] = 3;
            }
            for (int j = 1; j < m-1; j++) {
                a[0][j] = a[n-1][j] = 3;
            }
            for (int i = 1; i < n-1; i++) {
                for (int j = 1; j < m-1; j++) {
                    a[i][j] = 4;
                }
            }
            for (int[] row : a) {
                for (int x : row) {
                    pw.print(x + " ");
                }
                pw.println();
            }
        }
    }
}